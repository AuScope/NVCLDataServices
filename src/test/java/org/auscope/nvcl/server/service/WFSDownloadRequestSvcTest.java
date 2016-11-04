package org.auscope.nvcl.server.service;

import javax.jms.Destination;

import org.auscope.nvcl.server.vo.ConfigVo;
import org.auscope.nvcl.server.vo.MessageVo;
import org.auscope.nvcl.server.vo.UnitTestVo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.MailSender;

/*
 * Junit test for WFSDownloadRequestSvc.java
 * This test will test the ProcessRequest() method in WFSDownloadRequestSvc.java, it will simulate a request message and run the
 * ProcessRequest() method to carry out actual download, compress downloaded file, moving zip file to download path and
 * clearing the cache if download.cache is no.
 * Author : Florence Tan
 */

public class WFSDownloadRequestSvcTest {

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
    	UnitTestVo testconfig = (UnitTestVo) ctx.getBean("unittestVo");
		ConfigVo configVo = (ConfigVo) ctx.getBean("createConfig");
		configVo.setDownloadCachePath("C:\\NVCL\\cache\\");
		configVo.setDownloadURL("http://localhost:80/NVCLDownload/");
		configVo.setDownloadRootPath("C:\\NVCL\\download\\");
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		Destination destination = (Destination) ctx.getBean("wfsReplyDestination");
		MessageVo wfsreqessage = new MessageVo();
		wfsreqessage.setRequestorEmail(testconfig.getRequestorEmail());
		wfsreqessage.setFeatureTypeName("gsml:Borehole");
		wfsreqessage.setScriptFileNameNoExt("scriptfiletest");
		wfsreqessage.setRequestorEmail(testconfig.getRequestorEmail());
		wfsreqessage.setBoreholeid("PIRSA_DH_NO_141126");
		WFSDownloadRequestSvc wfsDownloadRequestSvc = new WFSDownloadRequestSvc();

		wfsDownloadRequestSvc.setJmsTemplate(jmsTemplate);
		wfsDownloadRequestSvc.setDestination(destination);
		wfsDownloadRequestSvc.setMailSender((MailSender)ctx.getBean("mailSender"));
		wfsDownloadRequestSvc.setConfig(configVo);
		wfsDownloadRequestSvc.processRequest(wfsreqessage);

		System.out.println("message created... testing end ....");
	}
}
