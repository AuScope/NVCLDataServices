package org.auscope.nvcl.server.service;

import javax.jms.Destination;

import org.auscope.nvcl.server.vo.ConfigVo;
import org.auscope.nvcl.server.vo.MessageVo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.MailSender;


/*
 * Junit test for TSGDownloadRequestSvc.java
 * This test will test the ProcessRequest() method in TSGDownloadRequestSvc.java, it will simulate a request message and run the
 * ProcessRequest() method to carry out actual download, compress downloaded file, moving zip file to download path and
 * clearing the cache if tsg.cache is no.
 * Author : Florence Tan
 */

public class TSGDownloadRequestSvcTest {

	private static ApplicationContext ctx;
	//private static TSGDownloadGateway tsgDownloadGateway = new TSGDownloadGateway();

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
	 * test process tsgdownload request
	 *
	 */
	@Test
	public void testProcessRequest() throws Exception {

    	System.out.println("start testing ProcessRequest() method....");
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		Destination destination = (Destination) ctx.getBean("tsgReplyDestination");
		ConfigVo configVo = (ConfigVo) ctx.getBean("createConfig");
		configVo.setRequestLS(true);
		configVo.setTSGdatasetid("6dd70215-fe38-457c-be42-3b165fd98c7");
		MessageVo messageVo = new MessageVo();
		messageVo.setRequestorEmail("peter.warren@csiro.au");
		messageVo.setScriptFileNameNoExt("scriptfiletest");
		messageVo.setTsgScriptPath("C:\\NVCL\\TSG\\script\\");
		messageVo.setTsgExePath("C:\\Program Files\\The Spectral Geologist\\tsgeol7.exe");
		messageVo.setDownloadCachePath("C:\\NVCL\\cache\\");
		messageVo.setDownloadURL("http://localhost:88/TSGDownload/");
		messageVo.setDownloadRootPath("C:\\NVCL\\download\\");
		TSGDownloadRequestSvc tsgDownloadRequestSvc = new TSGDownloadRequestSvc();

		tsgDownloadRequestSvc.setJmsTemplate(jmsTemplate);
		tsgDownloadRequestSvc.setDestination(destination);
		tsgDownloadRequestSvc.setMailSender((MailSender)ctx.getBean("mailSender"));
		tsgDownloadRequestSvc.setConfig(configVo);
		tsgDownloadRequestSvc.processRequest(messageVo);

		System.out.println("message created... testing end ....");
	}
}
