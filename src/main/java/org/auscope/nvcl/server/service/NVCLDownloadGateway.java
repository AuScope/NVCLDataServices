package org.auscope.nvcl.server.service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.service.SpringFrameworkJmsSender.ReferenceHolderMessagePostProcessor;
import org.auscope.nvcl.server.vo.MessageVo;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConversionException;

/*
 * TSGDownloadGateway will create a JMS queue in the specified destination in 
 * ActiveMQ using JmsTemplate.
 *  
 * Author : Florence Tan
 */

public class NVCLDownloadGateway {
	
	private static final Logger logger = LogManager.getLogger(NVCLDownloadGateway.class);
   
	
	/** 
	 * The createTSGDownloadReqMsg method create new message in the specified destination by 
	 * call up JmsTemplate's convertAndSend() method which will automatically calls 
	 * the NVCLDownloadMessageConverter.toMessage() method that convert the java object 
	 * into message before sending the message to the destination.
	 * 
	 * Note : NVCLDownloadMessageConverter has been auto wired into JmsTemplate in 
	 *        applicationContext.xml
	 * 
	 * @param configVo	ConfigVo object that consists of the configuration information
	 * 					needed for trigger the tsg download using tsg exe program
	 */ 
	public String createTSGDownloadReqMsg(final MessageVo tsgreqmessage)   {
		
		
		try {
	        ReferenceHolderMessagePostProcessor messagePostProcessor = new ReferenceHolderMessagePostProcessor();
	        this.jmsTemplate.convertAndSend(this.destination, tsgreqmessage, messagePostProcessor);        	        
	        Message sentMessage = messagePostProcessor.getSentMessage();
	        logger.debug("Generated JMSMessageID : " + sentMessage.getJMSMessageID());
	        return sentMessage.getJMSMessageID();
		} catch (JMSException jmse) {
			logger.error("JMSException : " + jmse);
		} catch (MessageConversionException  mce) {
			logger.error("MessageConversionException : " + mce);
		}
		return null;		
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
