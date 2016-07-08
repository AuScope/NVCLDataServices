package org.auscope.nvcl.server.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.jms.Destination;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.auscope.nvcl.server.vo.ConfigVo;
import org.auscope.nvcl.server.vo.JMSMessageVo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/*
 * Junit test for NVCLDownloadGateway.java
 * 
 * This test will create two new messages, one in tsgdownload.request.queue and 
 * another in wfsdownload.request.queue
 * 
 * Author : Florence Tan
 */

public class NVCLDownloadGatewayTest {

	private static final Logger logger = LogManager.getLogger(NVCLDownloadGatewayTest.class);
	private static ApplicationContext ctx;
	
	@BeforeClass
	public static void setup() throws Exception {
		/**
		 * load applicationContext.xml
		 */
		ctx = new ClassPathXmlApplicationContext("file:src/main/webapp/WEB-INF/applicationContext.xml");		
	}

    @AfterClass
    public static void tearDown() {
    	//do nothing
    }
	
	/*
	 * test sending message to Message Container (tsgdownload.request.queue)
	 * 
	 */
	@Test
	public void testSendTSGDownloadInfo() throws Exception {
		
    	logger.info("send message to tsgdownload.request.queue testing start....");
    	//create new message
		ConfigVo configVo = (ConfigVo) ctx.getBean("createConfig");
		configVo.setScriptFileNameNoExt("scriptfiletest");
		configVo.setRequestorEmail("florence.tan@csiro.au");
		configVo.setRequestType("TSG");
		configVo.setRequestLS(true);
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		Destination tsgReqDestination = (Destination) ctx.getBean("tsgRequestDestination");
		NVCLDownloadGateway nvclDownloadGateway = (NVCLDownloadGateway) ctx.getBean("nvclDownloadGateway");
		nvclDownloadGateway.setJmsTemplate(jmsTemplate);
		nvclDownloadGateway.setDestination(tsgReqDestination);
		nvclDownloadGateway.createTSGDownloadReqMsg(configVo);
		//browse message(s) in the queue
		NVCLDownloadSvc nvclTSGDownloadSvc = new NVCLDownloadSvc();
		Destination tsgRepDestination = (Destination) ctx.getBean("tsgReplyDestination");
		Map<String, Object> msgMap = nvclTSGDownloadSvc.browseMessage("florence.tan@csiro.au",jmsTemplate,tsgReqDestination,tsgRepDestination);
		ArrayList<?> reqMsgList = (ArrayList<?>) msgMap.get("request");
		ArrayList<?> repMsgList = (ArrayList<?>) msgMap.get("reply");
		if (reqMsgList == null) {
			logger.info("request queue is null");
		} else {
			for (Iterator<?> it1 = reqMsgList.iterator(); it1.hasNext();) {
				JMSMessageVo jmsMsgVo = (JMSMessageVo) it1.next();
				logger.info("message id : " + jmsMsgVo.getJMSMsgID());
				logger.info("message status : " + jmsMsgVo.getStatus());
				logger.info("message description : " + jmsMsgVo.getDescription());
			}
		}
		if (repMsgList == null) {
			logger.info("reply queue is null");
		} else {
			for (Iterator<?> it1 = repMsgList.iterator(); it1.hasNext();) {
				JMSMessageVo jmsMsgVo = (JMSMessageVo) it1.next();
				logger.info("-------------------------------------------------");
				logger.info("timestamp : " + jmsMsgVo.getJMSTimestamp());
				logger.info("message id : " + jmsMsgVo.getJMSMsgID());
				logger.info("message status : " + jmsMsgVo.getStatus());
				logger.info("message description : " + jmsMsgVo.getDescription());
				logger.info("-------------------------------------------------");
			}
		}
	}
	
	/*
	 * test sending message to Message Container (wfsdownload.request.queue)
	 * 
	 */
	@Test
	public void testSendWFSDownloadInfo() throws Exception {
		
    	logger.info("send message to wfsdownload.request.queue testing start....");
    	//create new message
		ConfigVo configVo = (ConfigVo) ctx.getBean("createConfig");
		configVo.setRequestorEmail("florence.tan@csiro.au");
		configVo.setRequestType("WFS");
		configVo.setGeoserverUrl("http://apacsrv3.arrc.csiro.au/nvcl1/wfs");
		//configVo.setServiceUrl("http://apacsrv3.arrc.csiro.au/nvcl2/wfs");
		configVo.setTypeName("gsml:Borehole");
		configVo.setBoreholeID("PIRSA_DH_NO_141126");
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		Destination wfsReqDestination = (Destination) ctx.getBean("wfsRequestDestination");
		NVCLDownloadGateway nvclDownloadGateway = (NVCLDownloadGateway) ctx.getBean("nvclDownloadGateway");
		nvclDownloadGateway.setJmsTemplate(jmsTemplate);
		nvclDownloadGateway.setDestination(wfsReqDestination);
		nvclDownloadGateway.createWFSDownloadReqMsg(configVo);
		//browse message(s) in the queue
		NVCLDownloadSvc nvclTSGDownloadSvc = new NVCLDownloadSvc();
		Destination wfsRepDestination = (Destination) ctx.getBean("wfsReplyDestination");
		Map<String, Object> msgMap = nvclTSGDownloadSvc.browseMessage("florence.tan@csiro.au",jmsTemplate,wfsReqDestination,wfsRepDestination);
		ArrayList<?> reqMsgList = (ArrayList<?>) msgMap.get("request");
		ArrayList<?> repMsgList = (ArrayList<?>) msgMap.get("reply");
		if (reqMsgList == null) {
			logger.info("request queue is null");
		} else {
			for (Iterator<?> it1 = reqMsgList.iterator(); it1.hasNext();) {
				JMSMessageVo jmsMsgVo = (JMSMessageVo) it1.next();
				logger.info("message id : " + jmsMsgVo.getJMSMsgID());
				logger.info("message status : " + jmsMsgVo.getStatus());
				logger.info("message description : " + jmsMsgVo.getDescription());
			}
		}
		if (repMsgList == null) {
			logger.info("reply queue is null");
		} else {
			for (Iterator<?> it1 = repMsgList.iterator(); it1.hasNext();) {
				JMSMessageVo jmsMsgVo = (JMSMessageVo) it1.next();
				logger.info("-------------------------------------------------");
				logger.info("timestamp : " + jmsMsgVo.getJMSTimestamp());
				logger.info("message id : " + jmsMsgVo.getJMSMsgID());
				logger.info("message status : " + jmsMsgVo.getStatus());
				logger.info("message description : " + jmsMsgVo.getDescription());
				logger.info("-------------------------------------------------");
			}
		}
	}
}
