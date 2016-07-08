package org.auscope.nvcl.server.vo;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.auscope.nvcl.server.vo.ConfigVo;

/**
 * Test CreateConfig class to test if the Spring container load the values from config.properties
 *
 * @author Florence Tan
 */

public class CreateConfigTest {

	int logType;
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

	@Test
	public void testCreaetConfig() throws Exception {

		ConfigVo configVo = (ConfigVo) ctx.getBean("createConfig");
		//display the config info
		configVo.displayConfig();

	}



}
