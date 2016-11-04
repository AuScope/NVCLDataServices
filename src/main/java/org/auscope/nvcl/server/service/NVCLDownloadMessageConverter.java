package org.auscope.nvcl.server.service;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.auscope.nvcl.server.vo.MessageVo;
import org.auscope.nvcl.server.vo.ConfigVo;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/*
 * This class perform message conversion through Spring's MessageConverter interface.
 * The MessageConverter interface has only two methods that must be implemented:
 * <ol>
 * <li>toMessage() : for sending message, converts an object to a Message</li>
 * <li>fromMessage() : for receiving message, converts an incoming Message into an Object</li>
 * </ol>
 * This class has been wired into JmsTemplate's messageConverter property through 
 * applicationContext.xml 
 * 
 * Author : Florence Tan
 */

public class NVCLDownloadMessageConverter implements MessageConverter {

	private static final Logger logger = LogManager.getLogger(NVCLDownloadMessageConverter.class);

	public NVCLDownloadMessageConverter() {}

	
	/*
	 * Convert Message to Object.   
	 * In this application - will be triggered before a new message in tsgdownload.request.queue
	 * being processed by the TSGDownloadRequestSvc.processRequest(MessageVo messageVo) method.
	 * It will read the message from the message body and store them into messageVo object,
	 * pass it to processRequest(MessageVo messageVo) for processing.
	 */
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		
		logger.debug("converting message (" + message.getJMSMessageID() + ") to object... !");
		logger.debug("JMSCorrelationID : " + message.getJMSCorrelationID());
		
		if (!(message instanceof MapMessage)) {
			throw new MessageConversionException("Message isn't a MapMessage");
		}
		MapMessage mapMessage = (MapMessage) message;
		MessageVo messageVo = new MessageVo();
		if ( mapMessage.getString("requestType").equals("TSG") ) {
			messageVo.setScriptFileNameNoExt(mapMessage.getString("scriptFileNameNoExt"));
			messageVo.settSGDatasetID(mapMessage.getString("tsgdatasetid"));
			messageVo.setRequestLS(mapMessage.getBoolean("requestLS"));
		} else if  ( mapMessage.getString("requestType").equals("WFS") ) {
			messageVo.setBoreholeid(mapMessage.getString("boreholeId"));
			messageVo.setFeatureTypeName(mapMessage.getString("typeName"));
		}
		messageVo.setRequestorEmail(message.getJMSCorrelationID());
		return messageVo;
	}

	/*
	 * Converts Object (ConfigVo or MessageVo) to Message
	 * In this application, it will be triggered before creating new message in the
	 * specified request queue (convert ConfigVO object to message) and
	 * specified reply queue (convert MessageVo object to message). 
	 */
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		
		logger.debug("Converting object (" + object + ") to message ... !");
		
		if ( !(object instanceof MessageVo)) {
			throw new MessageConversionException("Object must be of type MessageVo");
		}
	
		MessageVo messageVo = (MessageVo) object;
		MapMessage message = session.createMapMessage();
		message.setJMSCorrelationID(messageVo.getRequestorEmail());
		message.setString("status", messageVo.getStatus());
		message.setString("description", messageVo.getDescription());
		message.setBoolean("resultfromcache", messageVo.getResultfromcache());
		message.setString("tsgdatasetid", messageVo.gettSGDatasetID());
		message.setBoolean("requestLS", messageVo.getRequestLS());
		message.setString("boreholeid",  messageVo.getBoreholeid());
		message.setString("typename",  messageVo.getFeatureTypeName());
		
		if ( messageVo.getRequestType().equals("TSG") ) {
			message.setString("description", "Request for dataset with ID: " + messageVo.getScriptFileNameNoExt() );
			message.setString("requestType", "TSG");
			message.setString("scriptFileNameNoExt", messageVo.getScriptFileNameNoExt());
		} else if ( messageVo.getRequestType().equals("WFS") ) {
			message.setString("description", "Request for O and M data with ID: " +messageVo.getBoreholeid() );
			message.setString("requestType","WFS");
			message.setString("boreholeId",messageVo.getBoreholeid());
		}
		
		return message;

	
	}
	
}
