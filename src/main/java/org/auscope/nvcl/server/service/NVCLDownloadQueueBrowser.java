package org.auscope.nvcl.server.service;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.auscope.nvcl.server.vo.JMSMessageVo;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.SessionCallback;

/**
 * A service that read messages in a JMS queue filter by JMSCorrelationID (same as requestor's email address).  
 * Store the message detail into JMSMessageVo object and return a list of JMSMessageVo.
 *
 * @author Florence Tan
 */

public class NVCLDownloadQueueBrowser {
	
	private static final Logger logger = LogManager.getLogger(NVCLDownloadQueueBrowser.class);

	public List<JMSMessageVo> browseQueueMessages(final String email, final Destination destination) {
	
		
		List<JMSMessageVo> msgList = (ArrayList<JMSMessageVo>) this.jmsTemplate.execute(new SessionCallback<List<JMSMessageVo>>() {

			public List<JMSMessageVo> doInJms(Session session) throws JMSException {
				int count = 0;
				List<JMSMessageVo> msgList = new ArrayList<JMSMessageVo>();
				logger.debug("browse message in : " + destination);						 
				//QueueBrowser browser = session.createBrowser((Queue) destination);				
				//String msgSelector = "JMSMessageID  = 'ID:WALLABY-KH-3623-1255588969815-0:0:1:4:1'";
				String msgSelector = "JMSCorrelationID  = '" + email + "'";
				QueueBrowser browser = session.createBrowser((Queue)destination,msgSelector);		
				Enumeration<?> messages = browser.getEnumeration();
				while (messages.hasMoreElements()) {
						JMSMessageVo jmsMsgVo = new JMSMessageVo();
						count++;
						Message message = (Message) messages.nextElement();
						//logger.debug("Message " + count + " : " + message);
						if (message instanceof MapMessage) {
							MapMessage mapMessage = (MapMessage) message;
							//convert long to date
							long timestamp = mapMessage.getJMSTimestamp();
							Date date = new Date(timestamp);
							DateFormat df = DateFormat.getDateTimeInstance();
							String newtimestamp = df.format(date); 
							jmsMsgVo.setJMSTimestamp(newtimestamp);
							jmsMsgVo.setJMSMsgID(mapMessage.getJMSMessageID());
							jmsMsgVo.setJMSCorrelationID(mapMessage.getJMSCorrelationID());
							jmsMsgVo.setStatus(mapMessage.getString("status"));
							jmsMsgVo.setDescription(mapMessage.getString("description"));
							jmsMsgVo.setResultfromcache(mapMessage.getBoolean("resultfromcache"));
							jmsMsgVo.setTsgdatasetid(mapMessage.getString("Tsgdatasetid"));
							jmsMsgVo.setRequestLS(mapMessage.getBoolean("requestLS"));
							jmsMsgVo.setBoreholeid(mapMessage.getString("boreholeid"));
							jmsMsgVo.setTypename(mapMessage.getString("typename"));
							
						}
						msgList.add(0,jmsMsgVo);
				}
				if ( count > 0 ) {
					return msgList;
				} else {
					return null;
				}
			}			
		}, true);
		
		return msgList;
	}
	
	//Injects JmsTemplate
	private JmsTemplate jmsTemplate;
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	

	
}
