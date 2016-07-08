package org.auscope.nvcl.server.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.auscope.nvcl.server.dao.DomainDataDao;
import org.auscope.nvcl.server.dao.DownSampledClassDataDao;
import org.auscope.nvcl.server.dao.DownSampledFloatDataDao;
import org.auscope.nvcl.server.vo.DomainDataCollectionVo;
import org.auscope.nvcl.server.vo.BinnedClassDataVo;
import org.auscope.nvcl.server.vo.AveragedFloatDataVo;
import org.auscope.nvcl.server.vo.UnitTestVo;

/**
 * Test calling DownSampledClassDataDao & DownSampledFloatDataDao
 *
 * @author Florence Tan
 */
public class DAOTest {

    int logType;
    private static ApplicationContext ctx;
    private static DownSampledClassDataDao downSampledClassDataDao;
    private static DownSampledFloatDataDao downSampledFloatDataDao;
    private static DomainDataDao domainDataDao;
    private static UnitTestVo unittestVo;

    @BeforeClass
    public static void setup() throws Exception {
        /**
         * load applicationContext.xml
         */
        ctx = new ClassPathXmlApplicationContext(
                "file:src/main/webapp/WEB-INF/applicationContext.xml");
        downSampledClassDataDao = (DownSampledClassDataDao) ctx.getBean("downSampledClassDataDao");
        downSampledFloatDataDao = (DownSampledFloatDataDao) ctx.getBean("downSampledFloatDataDao");
        domainDataDao = (DomainDataDao) ctx.getBean("domainDataDao");
        unittestVo = (UnitTestVo) ctx.getBean("unittestVo");
    }

    @AfterClass
    public static void tearDown() {
        // do nothing
    }

    /**
     * Test DownSampledClassDataDao
     *
     * @throws IOException
     */
    @Test
    public void testDownSampledClassDataDao() throws IOException {

        // retrieve log id with lot type = 1 from unittest.properties
        final String testLogIdType1 = unittestVo.getTestLogIdType1();

        // test DownSampledClassDataDao and see if it returns an ArrayList
         ArrayList<BinnedClassDataVo> outs = downSampledClassDataDao.execute(
                testLogIdType1, 0, 99999, 1,5);
        assertEquals(outs instanceof ArrayList, true);

    }

    /**
     * Test GDSLVType2Dao
     *
     * @throws IOException
     */
    @Test
    public void testDownSampledFloatDataDao() throws IOException {

        // retrieve log id with lot type = 2 from unittest.properties
        final String testLogIdType2 = unittestVo.getTestLogIdType2();

        // test DownSampledFloatDataDao and see if it returns an ArrayList
        ArrayList<AveragedFloatDataVo> outs = downSampledFloatDataDao.execute(
                testLogIdType2, 0, 99999, 1);
        assertEquals(outs instanceof ArrayList, true);

    }



    /**
     * Test GetDomainDataDao
     *
     * @throws IOException
     */
    @Test
    public void testGetDomainDataDao() throws IOException {

        // retrieve domainlog_id for a specified tray image log id from unittest.properties
        final String testDomainlogId = unittestVo.getDomainlogId();

        // test getDomainDataDao and see if it returns a Linked Hash Map
        DomainDataCollectionVo outs = domainDataDao.execute(
                testDomainlogId);
        assertEquals(outs.getDomainDataCollection() instanceof ArrayList, true);

    }


}
