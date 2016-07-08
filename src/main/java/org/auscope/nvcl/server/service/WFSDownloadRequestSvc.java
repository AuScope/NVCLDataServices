package org.auscope.nvcl.server.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.auscope.nvcl.server.service.SpringFrameworkJmsSender.ReferenceHolderMessagePostProcessor;
import org.auscope.nvcl.server.vo.ConfigVo;
import org.auscope.nvcl.server.vo.MessageVo;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * TSTDownloadRequestSvc trigger actual services that perform the TSG dataset download,
 * zip the folder and move the folder to the ftp/http server root path for download
 * At the end, it will create a new message in the JMS reply queue with status of the
 * download in the message body.
 *  
 * @author Florence Tan
 *
 */
public class WFSDownloadRequestSvc {
	
	private static final Logger logger = LogManager.getLogger(WFSDownloadRequestSvc.class);
		
	private static NVCLDownloadSvc nvclDownloadSvc = new NVCLDownloadSvc();
	
	public void processRequest(MessageVo messageVo) {
		logger.debug("in processing WFSDownload Request...");
		logger.debug("Check if requested boreholeid exists in download dir before download process start ........");
		String donwloadURL		= messageVo.getDownloadURL();
		String downloadRootPath = messageVo.getDownloadRootPath();
		String fileName 		= messageVo.getBoreholeId();
		File dir = new File(downloadRootPath);
		File fullpath = new File(dir, fileName + ".zip");
		logger.debug("fullpath : " + fullpath);

		// proceed to download only if the dataset has not been downloaded before
		if (!fullpath.exists()) { 
			logger.debug("File does not exists, proceed to actual download...");
			messageVo.setResultfromcache(false);
			messageVo = exeRequest(messageVo);
		} else {
			fullpath.setLastModified(System.currentTimeMillis());
			messageVo.setStatus("Success");
			messageVo.setResultfromcache(true);
			messageVo.setDescription(donwloadURL + fileName + ".zip");
			logger.debug("File exists, skip O&M download... create reply message....");
		}
				
		
		// create another message in the tsgdownload.reply.queue with correlation id
		// same as the request message id
		try {
	        ReferenceHolderMessagePostProcessor messagePostProcessor = new ReferenceHolderMessagePostProcessor();
	        this.jmsTemplate.setTimeToLive(((long)this.config.getMsgTimetoLiveDays())*86400000);
	        this.jmsTemplate.setExplicitQosEnabled(true);
		    this.jmsTemplate.convertAndSend(destination, messageVo, messagePostProcessor);
		    Message sentMessage = messagePostProcessor.getSentMessage();		    
		    logger.debug("Generated JMSMessageID" + sentMessage.getJMSMessageID());
		    logger.debug("Generated JMSCorrelationID" + sentMessage.getJMSCorrelationID());
		} catch (JMSException jmse) {
			logger.error("JMSException : " + jmse);
		}
		
	    if (config.getSendEmails()==true) sendResultEmail(messageVo);
	    else logger.debug("Notification emails disabled, skipping email step.");
	    
	}
	
	/**
	  * exeRequest perform actual Borehole O&M Download process, 
	  * check download status and set download status to messageVo
	  * 
	  * @param	messaveVo	message value object 
	  */
	private MessageVo exeRequest(MessageVo messageVo)  {
		logger.debug("Start WFSDownload process ........");

		//set all of the url parameters
		NameValuePair request  = new NameValuePair("request","GetFeature");
		NameValuePair typeName = new NameValuePair("typeName",messageVo.getTypeName());
		NameValuePair version = new NameValuePair("version","1.1.0");
		NameValuePair boreholeId = new NameValuePair("featureid",messageVo.getBoreholeId()); 	
		
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(config.getGeoserverUrl());
		method.setQueryString(new NameValuePair[]{request,typeName,boreholeId,version});
		
		String fileName 			= messageVo.getBoreholeId();
		//String downloadCache 		= messageVo.getDownloadCache();
		String downloadCachePath 	= messageVo.getDownloadCachePath();
		String downloadRootPath  	= messageVo.getDownloadRootPath();
		String downloadURL 			= messageVo.getDownloadURL();
			
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				logger.debug("execute success with statusCode : " + statusCode);
				//use getResponseBodyAsStream() rather than getResponseBody() since
				//the later can easily lead to out of memory conditions 
				Reader reader = new InputStreamReader(
						method.getResponseBodyAsStream(),method.getResponseCharSet());
			    BufferedReader bufferedreader = new BufferedReader(reader);			

				//create file from BufferedReader
				String xmlFile = nvclDownloadSvc.createXmlFile(messageVo,bufferedreader,fileName);
				File filetocompress = new File (downloadCachePath+fileName+".xml");
				if (!xmlFile.equals("fail")) {
					int zipFile = nvclDownloadSvc.zipFile(downloadCachePath,fileName);
					if ( zipFile == 0 ) {
						File preparedzip = new File(downloadCachePath+fileName+".zip");
						File downloadfile= new File(downloadRootPath + fileName+".zip");
						try {
							Files.move(preparedzip.toPath(), downloadfile.toPath(), StandardCopyOption.REPLACE_EXISTING);
							filetocompress.delete();
							messageVo.setStatus("Success");
							messageVo.setDescription(downloadURL + fileName + ".zip");
						}
						catch (IOException e) {
							logger.error("IOException occured while moving file to download folder : " + e);
							messageVo.setStatus("Failed");
							messageVo.setDescription("Failed to move zip file to target root path");
						}
					} else {
						messageVo.setStatus("Failed");
						messageVo.setDescription("Failed on compress downloaded O&M xml file to zip file");
					}
				} else {
					logger.debug("Failed on creating downloaded O&M to xml file");
					messageVo.setStatus("Failed");
					messageVo.setDescription("Failed on creating downloaded O&M to xml file");	
				}
			} else {
				logger.debug("execute failed with statusCode : " + statusCode);
				messageVo.setStatus("Failed");
				messageVo.setDescription("Response Status Code : " + statusCode);
			} 

		} catch (HttpException e) {
			messageVo.setStatus("Failed");
			messageVo.setDescription(e.toString());
		} catch (IOException e) {
			messageVo.setStatus("Failed");
			messageVo.setDescription(e.toString());
		} finally {
			method.releaseConnection();
		}
		return messageVo;
		
	}
	
	/**
	  * sends an email to the requestor's email address indicating success and providing a download link
	  * or in the case of failure describing next steps to request support.
	  * 
	  * @param	messaveVo	message value object 
	  */
	private void sendResultEmail(MessageVo messageVo) {
		
	    SimpleMailMessage msg = new SimpleMailMessage();
	    try {
	        msg.setTo(messageVo.getRequestorEmail());
	        
	        if(messageVo.getStatus().equals("Success")){
	        	String msgtext;
	        	msg.setSubject("NVCL Download ready");
	        	msgtext="This is an automated email from the National Virtual Core Library Download Service.\n\nThe Observations and Measurements representation of dataset with ID :" + messageVo.getBoreholeId() + " is ready to download here: " + messageVo.getDescription()+" This file will remain available for download for "+ this.config.getMsgTimetoLiveDays() +" days.";
	        	if(messageVo.getResultfromcache()){
	        		msgtext+="\n\nThis file was recovered from cache.  If you believe it is stale you can force the service to regenerate it by clicking this link: "+config.getWebappURL()+"downloadwfs.html?boreholeid="+messageVo.getBoreholeId()+"&email="+messageVo.getRequestorEmail()+"&typename="+messageVo.getTypeName()+"&forcerecreate=yes .  Note: this can take some time and may not be possible if you or another user is currenly downloading the cached file";
	        	}
	        	msgtext+="\n\n If you have any comments, suggestions or issues with the download please reply to this email."; 
	        	msg.setText(msgtext);
	        }
	        else{
	        	msg.setSubject("NVCL Download preparation failed");
	        	msg.setBcc(config.getSysAdminEmail());
	        	msg.setText("This is an automated email from the National Virtual Core Library Download Service.\n\nYour request for dataset "+messageVo.getBoreholeId()+" has failed.  Please reply to this email for support.");
	        }
	        logger.debug("Sending result email");
		
			msg.setFrom(config.getSysAdminEmail());
	
	        mailSender.send(msg);
	    }
	    catch(MailException ex)
	    {
	    	logger.debug("Send Email failed. Service not configured correctly or the email server is down");
	    }
	}
	
	private MailSender mailSender;
	public void setMailSender(MailSender mailSender) {
			this.mailSender = mailSender;
	}
	
	private ConfigVo config;
	public void setConfig(ConfigVo config) {
			this.config = config;
	}
	
	//Injects JmsTemplate
	private JmsTemplate jmsTemplate;
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	//Injects Destination
	private Destination destination;
	public void setDestination(Destination destination) {
		this.destination = destination;
	}

}
