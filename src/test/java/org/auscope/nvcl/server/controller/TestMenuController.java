package org.auscope.nvcl.server.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
//import org.auscope.nvcl.server.service.NVCLDataSvc;
import org.auscope.nvcl.server.web.controllers.MenuController;

/**
 * The Class TestMenuController.
 *
 * @author Florence Tan
 */
public class TestMenuController extends TestCase {

    /** The mock context. */
    private Mockery context;
    /** The mock http request. */
    private HttpServletRequest mockHttpRequest;
    /** The mock http response. */
    private HttpServletResponse mockHttpResponse;
    /** The mock NVCLDataSvc . */
    //private NVCLDataSvc mockNvclDataSvc;
    /** The main menu controller. */
    private MenuController menuController;


    /**
     * Setup.
     */
    @Before
    public void setUp() {
        context = new Mockery() {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };

        mockHttpRequest = context.mock(HttpServletRequest.class);
        mockHttpResponse = context.mock(HttpServletResponse.class);
        //mockNvclDataSvc = context.mock(NVCLDataSvc.class);
        menuController = new MenuController();

    }


    /**
     * Test that maximum 6 Log IDs are allowed
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testPlotScalarWithMaximumLotIDs() throws ServletException,
            Exception {

        final String[] logIdList = { "logid1", "logid2", "logid3", "logid4", "logid5", "logid6", "logid7" };

        context.checking(new Expectations() {
            {
                oneOf(mockHttpRequest).getScheme();
                will(returnValue("http"));
                oneOf(mockHttpRequest).getServerName();
                will(returnValue("test.org"));
                oneOf(mockHttpRequest).getServerPort();
                will(returnValue(80));
                oneOf(mockHttpRequest).getContextPath();
                will(returnValue("/NVCLDataServices"));
                oneOf(mockHttpRequest).getServletPath();
                will(returnValue("chart.html"));
            }
        });

        ModelAndView result = menuController.chartHandler(mockHttpRequest,
                mockHttpResponse, logIdList, null, null, null, null, null,
                null, null);
        assertNotNull(result);
        assertEquals(result.getModel() instanceof ModelMap, true);
        assertTrue((Boolean) result.getViewName().equals("plotmultiscalarsusage"));

    }


    /**
     * Test that only Log ID is the only mandatory parameter for Plotscalar service and
     * the optional parameters will be set to default value (e.g.startdepth=0.0
     * edndepth=99999.0,samplinginterval=1.0,graphtype=1,width=300,height=600,legend=1)
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testPlotScalarWithMandatoryParameterOnly() throws ServletException,
            Exception {

        final String[] logIdList = { "logid1", "logid2", "logid3" };

        context.checking(new Expectations() {
            {
                oneOf(mockHttpRequest).getScheme();
                will(returnValue("http"));
                oneOf(mockHttpRequest).getServerName();
                will(returnValue("test.org"));
                oneOf(mockHttpRequest).getServerPort();
                will(returnValue(80));
                oneOf(mockHttpRequest).getContextPath();
                will(returnValue("/NVCLDataServices"));
                oneOf(mockHttpRequest).getServletPath();
                will(returnValue("chart.html"));
            }
        });

        ModelAndView result = menuController.chartHandler(mockHttpRequest,
                mockHttpResponse, logIdList, null, null, null, null, null,
                null, null);
        assertNotNull(result);
        assertEquals(result.getModel() instanceof ModelMap, true);
        String imageURL = result.getModel().get("imageURL").toString();
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "startdepth=0.0")==3);
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "enddepth=999999.0")==3);
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "samplinginterval=1.0")==3);
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "graphtype=1")==3);
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "width=300")==3);
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "height=600")==3);
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "legend=1")==3);
        assertTrue(imageURL.contains(logIdList[0]));
        assertTrue(imageURL.contains(logIdList[1]));
        assertTrue(imageURL.contains(logIdList[2]));
    }



    /**
     * Test to ensure the scalar chart plotting service (chart.html) return a
     * valid response
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testPlotScalarWithMandatoryAndOptionalParameters() throws ServletException, Exception {

        final String[] logIdList = { "logid1", "logid2", "logid3" };
        final float startDepth = 0;
        final float endDepth = 1000;
        final float samplingInterval = 2;
        final int width = 350;
        final int height = 650;
        final int graphType = 2;
        final int legend = 0;

        context.checking(new Expectations() {
            {
                oneOf(mockHttpRequest).getScheme();
                will(returnValue("http"));
                oneOf(mockHttpRequest).getServerName();
                will(returnValue("test.org"));
                oneOf(mockHttpRequest).getServerPort();
                will(returnValue(80));
                oneOf(mockHttpRequest).getContextPath();
                will(returnValue("/NVCLDataServices"));
                oneOf(mockHttpRequest).getServletPath();
                will(returnValue("chart.html"));
            }
        });

        ModelAndView result = menuController.chartHandler(mockHttpRequest,
                mockHttpResponse, logIdList, samplingInterval, startDepth,
                endDepth, width, height, graphType, legend);
        assertNotNull(result);
        assertEquals(result.getModel() instanceof ModelMap, true);
        String imageURL = result.getModel().get("imageURL").toString();
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "startdepth=0.0")==3);
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "enddepth=1000.0")==3);
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "samplinginterval=2.0")==3);
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "graphtype=2")==3);
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "width=350")==3);
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "height=650")==3);
        assertTrue(StringUtils.countOccurrencesOf(imageURL, "legend=0")==3);
        assertTrue(imageURL.contains(logIdList[0]));
        assertTrue(imageURL.contains(logIdList[1]));
        assertTrue(imageURL.contains(logIdList[2]));
    }






}
