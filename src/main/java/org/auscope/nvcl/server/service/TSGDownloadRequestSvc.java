package org.auscope.nvcl.server.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.io.FileUtils;
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
 * Before the actual download, it will check if the download.cache is yes or no
 * download.cache=yes, check if requested dataset exists in ftp/http server root path,
 * skip download and jump to create reply message in JMS reply queue.
 * download.cache=no, perform download, zip cache folder, move to ftp/http server root 
 * path, if similar dataset.zip exists, overwrite the file, finally removed 
 * cache folder
 * 
 *  
 * @author Florence Tan
 *
 */
public class TSGDownloadRequestSvc {
	
	private static final Logger logger = LogManager.getLogger(TSGDownloadRequestSvc.class);
	
	public void processRequest(MessageVo messageVo) {
		logger.debug("in TSGDonwloadRequestSvc.processRequest...");
		logger.debug("Check if requested dataset exists in download dir before download process start ........");
		String donwloadURL		= config.getDownloadURL();
		String downloadRootPath = config.getDownloadRootPath();
		String fileName 		= messageVo.getScriptFileNameNoExt();
		File dir = new File(downloadRootPath);
		File fullpath = new File(dir, fileName + ".zip");
		logger.debug("fullpath : " + fullpath);

		// proceed to download only if the dataset has not been downloaded before
		if (!fullpath.exists()) { 
			logger.debug("File does not exists, proceed to actual download...");
			messageVo.setResultfromcache(false);
			//messageVo.setTSGDatasetID(config.getTSGdatasetid());
			messageVo = exeRequest(messageVo);
		} else {
			fullpath.setLastModified(System.currentTimeMillis());
			messageVo.setStatus("Success");
			messageVo.setDescription(donwloadURL + fileName + ".zip" );
			messageVo.setResultfromcache(true);
			//messageVo.setTSGDatasetID(config.getTSGdatasetid());
			logger.debug("File exists, skip download... create reply message....");
		}

		
		//finally, create reply message
		// create another message in the tsgdownload.reply.queue with correlation id
		// same as the request message id
		try {			
	        ReferenceHolderMessagePostProcessor messagePostProcessor = new ReferenceHolderMessagePostProcessor();
	        this.jmsTemplate.setTimeToLive(((long)this.config.getMsgTimetoLiveDays())*86400000);
	        this.jmsTemplate.setExplicitQosEnabled(true);
	        this.jmsTemplate.convertAndSend(this.destination, messageVo, messagePostProcessor);
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
	  * exeRequest perform actual TSG Download process, check download status and 
	  * set download status to messageVo
	  * 
	  * @param	messaveVo	message value object 
	  */
	private MessageVo exeRequest(MessageVo messageVo) {
		logger.debug("Start TSGDownload process ........");			
		int exitVal = nvclDownloadSvc.execTSGDownload(
				config.getTsgExePath(), 
				config.getTsgScriptPath()+messageVo.getScriptFileNameNoExt()+".txt");
		//exit value 0 = success 
		if (exitVal == 0 ) {		
			String downloadCachePath = config.getDownloadCachePath();
			String fileName 		 = messageVo.getScriptFileNameNoExt();
			String downloadRootPath  = config.getDownloadRootPath();
			String downloadURL		 = config.getDownloadURL();
			// zip the folder that consists the downloaded file
			File foldertocompress = new File (downloadCachePath+fileName);
			int zipFolder = nvclDownloadSvc.zipFolder(downloadCachePath,fileName);
			if ( zipFolder == 0 ) {
				File preparedzip = new File(downloadCachePath+fileName+".zip");
				File downloadfile= new File(downloadRootPath + fileName+".zip");
				try {
					Files.move(preparedzip.toPath(), downloadfile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					FileUtils.deleteDirectory(foldertocompress);
					messageVo.setStatus("Success");
					messageVo.setDescription(downloadURL + fileName + ".zip");
				}
				catch (IOException e) {
					logger.error("IOException occured while moving file to download folder: " + e);
					messageVo.setStatus("Failed");
					messageVo.setDescription("Failed to move zip file to target root path");
				}
			} else {
				messageVo.setStatus("Failed");
				messageVo.setDescription("Failed on compress downloaded dataset to zip file");
			}
			
		} else {
			messageVo.setStatus("Failed");
			messageVo.setDescription("Failed on tsg dataset download");
		}
		logger.debug("TSGDownload process done... ");
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
	        	msgtext="This is an automated email from the National Virtual Core Library Download Service.\n\nThe TSG dataset you requested :" + messageVo.getScriptFileNameNoExt() + " is ready for download.  " + config.getDownloadURL()+messageVo.getScriptFileNameNoExt()+".zip .  This file will remain available for download for "+ this.config.getMsgTimetoLiveDays() +" days.\n\nTo view the content of these files you will need \"The Spectral Geologist Viewer\" available at http://www.thespectralgeologist.com ";
	        	if(messageVo.getResultfromcache()){
	        		msgtext+="\n\nThis file was recovered from cache.  If you believe it is stale you can force the service to regenerate it by clicking this link: "+config.getWebappURL()+"downloadtsg.html?datasetid="+messageVo.gettSGDatasetID()+"&email="+messageVo.getRequestorEmail()+"&linescan="+(messageVo.getRequestLS()?"yes":"no")+"&forcerecreate=yes .  Note: this can take some time and may not be possible if you or another user is currenly downloading the cached file";
	        	}
	        	msgtext+="\n\n If you have any comments, suggestions or issues with the download please reply to this email.";
	        	msg.setText(msgtext);
	        }
	        else{
				if (messageVo.getAutoCacheJob()){
					msg.setSubject("The NVCL DataServices auto caching job tried to generate a dataset but it failed");
					msg.setTo(config.getSysAdminEmail());
					msg.setText(
							"This is an automated email from your NVCL Data Services application.\n\nThe auto caching service tried to generate TSG dataset: "
									+ messageVo.gettSGDatasetID()
									+ " but it failed.  Please check the server logs to determine what went wrong.  Each download job should generate a new log file named: "
									+ messageVo.gettSGDatasetID() + "###.log.  \n\nFor support email cg-admin@csiro.au.");
					this.config.addItemtoAutoCacheFailedDatasetsList(messageVo.gettSGDatasetID());
				}
				else {
					msg.setSubject("NVCL Download preparation failed");
					msg.setBcc(config.getSysAdminEmail());
					msg.setText("This is an automated email from the National Virtual Core Library Download Service.\n\nYour request for TSG dataset "+messageVo.gettSGDatasetID()+" has failed.  Please reply to this email for support.");
				}
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
	
	private NVCLDownloadSvc nvclDownloadSvc;
	public void setNvclDownloadSvc(NVCLDownloadSvc nvclDownloadSvc) {
		this.nvclDownloadSvc = nvclDownloadSvc;
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
