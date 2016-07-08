package org.auscope.nvcl.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jfree.data.xy.IntervalXYDataset;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.vo.DatasetCollectionVo;
import org.auscope.nvcl.server.vo.DatasetVo;
import org.auscope.nvcl.server.vo.ImageDataVo;
import org.auscope.nvcl.server.vo.ImageLogCollectionVo;
import org.auscope.nvcl.server.vo.ImageLogVo;
import org.auscope.nvcl.server.vo.LogCollectionVo;
import org.auscope.nvcl.server.vo.LogDetailsVo;
import org.auscope.nvcl.server.vo.LogVo;
import org.auscope.nvcl.server.vo.UnitTestVo;

/**
 * Test Service class to see if the data access object
 * (org.auscope.nvcl.server.dao) method works
 *
 * @author Florence Tan
 */
public class NVCLDataSvcTest {

    private static final Logger logger = LogManager.getLogger(NVCLDataSvcTest.class);
    int logType;
    private static ApplicationContext ctx;
    private static NVCLDataSvc nvclDataSvc;
    private static UnitTestVo unittestVo;

    @BeforeClass
    public static void setup() throws Exception {
        /**
         * load applicationContext.xml
         */
        // ApplicationContext ctx = new
        // ClassPathXmlApplicationContext("applicationContext.xml");
        ctx = new ClassPathXmlApplicationContext(
                "file:src/main/webapp/WEB-INF/applicationContext.xml");
        nvclDataSvc = (NVCLDataSvc) ctx.getBean("nvclDataSvc");
        unittestVo = (UnitTestVo) ctx.getBean("unittestVo");

    }

    @AfterClass
    public static void tearDown() {
        // do nothing
    }

    /**
     * Test Database Connection
     *
     * @throws IOException
     */
    @Test
    public void testDbConn() throws IOException {

        // test if connection to db success
       // int dbconn = 0;
//        try {
//            dbconn = nvclDataSvc.getDBConn();
//        } catch (CannotGetJdbcConnectionException e) {
//            logger.error("CannotGetJdbcConnectionException : " + e);
//        }
//        assertTrue("Record count greater than zero", dbconn == 1);

        try {
            nvclDataSvc.getLogDetails("123");
        } catch (DataAccessException e) {
            logger.error("DataAccessException : " + e);
        } catch (SQLException e) {
            logger.error("SQLException : " + e);
        }

    }

    /**
     * Test getLogCollectioin() function
     *
     * @throws IOException
     */
    @Test
    public void testGetLogCollection() throws IOException {

        // retrieve test dataset id from unittest.properties
        final String testDatasetId = unittestVo.getTestDatasetId();

        // test getLogCollection function and see if it reutns an Array List of
        // LogCollectionVo
        LogCollectionVo logList = null;
        logList = nvclDataSvc.getLogCollection(testDatasetId);
        for (Iterator<LogVo> it = logList.getLogCollection().iterator(); it.hasNext();) {
            assertEquals(it.next() instanceof LogVo, true);
        }

        // test getLogCollection function with mosaicsvc="yes" and see if it
        // returns an Array List of LogColectionMosaicVo object
        ImageLogCollectionVo mosaicList = null;
        mosaicList = nvclDataSvc.getImageLogCollection(testDatasetId);
        for (Iterator<ImageLogVo> it = mosaicList.getimageLogCollection().iterator(); it.hasNext();) {
            assertEquals(it.next() instanceof ImageLogVo, true);
        }

    }

    /**
     * Test getDatasetConnection function
     *
     * @throws IOException
     */
    @Test
    public void testGetDatasetCollection() throws IOException {

        // retrieve test hole identifier and testDatasetID from
        // unittest.properties
        // rules for a success test - hole identifier and dataset id are from
        // the same record of PUBLISHEDDATASETS table
        final String testHoleIdentifier = unittestVo.getTestHoleIdentifier();
        final String testDatasetId = unittestVo.getTestDatasetId();

        // test getDatasetCollection function and see if it returns an Array
        // List
        DatasetCollectionVo datasetList = nvclDataSvc.getDatasetCollection(testHoleIdentifier);
        for (Iterator<DatasetVo> it1 = datasetList.getDatasetCollection().iterator(); it1.hasNext();) {
            // extract dataset id and name array list
            DatasetVo datasetCol = it1.next();
            String datasetId = datasetCol.getDatasetID();
            assertEquals(datasetId, testDatasetId);
        }

    }

    /**
     * Test getdownSampledClassData function
     *
     * @throws IOException
     */
    @Test
    public void testDownSampledClassData() throws IOException {

        // retrieve test lot id from unittest.properties (test.LogID1 - with log
        // type=1)
        final String testLogIdType1 = unittestVo.getTestLogIdType1();

        // test getDatasetCollection function and see if it returns an Array
        // List
        ArrayList<Object> arrOutput = nvclDataSvc.getdownSampledClassData(testLogIdType1, 0,
                99999, 1, 1);
        // logger.debug("arrOutput : " + arrOutput);
        assertEquals(arrOutput instanceof List, true);

    }

    /**
     * Test getdownSampledFloatDataDao function
     *
     * @throws IOException
     */
    @Test
    public void testDownSampledFloatDataDao() throws IOException {

        // retrieve test lot id from unittest.properties (test.LogID2 - with log
        // type=2)
        final String testLogIdType2 = unittestVo.getTestLogIdType2();

        // test getDatasetCollection function and see if the returned object is
        // an Array List
        ArrayList<IntervalXYDataset> arrOutput = nvclDataSvc.getdownSampledFloatDataDao(testLogIdType2, 0,
                99999, 1, 1);
        // logger.debug("arrOutput : " + arrOutput);
        assertEquals(arrOutput instanceof List, true);

    }

    /**
     * Test getSampleNo function
     *
     * @throws IOException
     */
    @Test
    public void testGetSampleNo() throws IOException {

        // retrieve test lot id from unittest.properties
        final String testLogIDImageryLog = unittestVo.getTestLogIdImagery();

        // test getDatasetCollection function and see if the returned object is
        // an Array List
        List<ImageDataVo> listOutput = nvclDataSvc.getSampleNo(
                testLogIDImageryLog, 1, 10);
        // logger.debug("arrOutput : " + arrOutput);
        assertEquals(listOutput instanceof List, true);

    }


    /**
     * Test validateLogId function
     *
     * @throws IOException
     * @throws SQLException
     * @throws DataAccessException
     */
    @Test
    public void testValidateLogId() throws IOException, DataAccessException, SQLException {

        // retrieve log id list unittest.properties (test.logidlist)
        //   - list of log id with log type = 1 or 2 and with similar domainlog_id
        final String[] testLogIdList = unittestVo.getTestLogIdList();

        // test validateLogId function - return true if all the log ids exist in LOGS table
        boolean validateLogId = nvclDataSvc.validateLogId(testLogIdList);
        // logger.debug("arrOutput : " + arrOutput);
        assertTrue(validateLogId);

    }

    /**
     * Test validateDomainLogId function
     *
     * @throws IOException
     * @throws SQLException
     * @throws DataAccessException
     */
    @Test
    public void testValidateDomainLogId() throws IOException, DataAccessException, SQLException {

        // retrieve log id list unittest.properties (test.logidlist)
        //   - list of log id with log type = 1 or 2 and with similar domainlog_id
        final String[] testLogIdList = unittestVo.getTestLogIdList();

        // test validateDomainlogId function - return true if all the log ids having
        // similar domainlog_id else return false
        boolean validateDomainlogId = nvclDataSvc.validateDomainlogId(testLogIdList);
        // logger.debug("arrOutput : " + arrOutput);
        assertTrue(validateDomainlogId);

    }

    /**
     * Test getLogDetails(String[]) function
     *
     * @throws IOException
     * @throws SQLException
     * @throws DataAccessException
     */
    @Test
    public void testGetLogDetails() throws IOException, DataAccessException, SQLException {

        // retrieve log id list unittest.properties (test.logidlist)
        //   - list of log id with log type = 1 or 2 and with similar domainlog_id
        final String[] testLogIdList = unittestVo.getTestLogIdList();
        List<LogDetailsVo> logDetailsVoList = nvclDataSvc.getLogDetails(testLogIdList);
        assertEquals(logDetailsVoList instanceof List, true);

    }


    /**
     * Test getTrayImageDomainlogId(String) function
     *
     */
    @Test
    public void testGetTrayImageDomainlogId() {
        final String testLogId = unittestVo.getTestLogIdTrayImage();
        String domainlogId = null;
        try {
            domainlogId = nvclDataSvc.getImageDomainlogId(testLogId);
        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(domainlogId);

    }

}
