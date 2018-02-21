package org.auscope.nvcl.server.web.controllers;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.jms.Destination;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.service.NVCLDataSvc;
import org.auscope.nvcl.server.service.NVCLDownloadGateway;
import org.auscope.nvcl.server.service.NVCLDownloadSvc;
import org.auscope.nvcl.server.util.Utility;
import org.auscope.nvcl.server.vo.AlgorithmCollectionVo;
import org.auscope.nvcl.server.vo.AveragedFloatDataVo;
import org.auscope.nvcl.server.vo.BinnedClassDataVo;
import org.auscope.nvcl.server.vo.ClassDataVo;
import org.auscope.nvcl.server.vo.ClassificationsCollectionVo;
import org.auscope.nvcl.server.vo.ConfigVo;
import org.auscope.nvcl.server.vo.DatasetCollectionVo;
import org.auscope.nvcl.server.vo.DatasetVo;
import org.auscope.nvcl.server.vo.DomainDataCollectionVo;
import org.auscope.nvcl.server.vo.DomainDataVo;
import org.auscope.nvcl.server.vo.FeatureCollectionVo;
import org.auscope.nvcl.server.vo.FloatDataVo;
import org.auscope.nvcl.server.vo.ImageDataURLVo;
import org.auscope.nvcl.server.vo.ImageDataVo;
import org.auscope.nvcl.server.vo.ImageLogCollectionVo;
import org.auscope.nvcl.server.vo.ImageLogVo;
import org.auscope.nvcl.server.vo.LogCollectionVo;
import org.auscope.nvcl.server.vo.LogDetailsVo;
import org.auscope.nvcl.server.vo.LogExtentsVo;
import org.auscope.nvcl.server.vo.MaskDataVo;
import org.auscope.nvcl.server.vo.MessageVo;
import org.auscope.nvcl.server.vo.ScannedBoreholeVo;
import org.auscope.nvcl.server.vo.SpectralDataCollectionVo;
import org.auscope.nvcl.server.vo.SpectralDataVo;
import org.auscope.nvcl.server.vo.SpectralLogCollectionVo;
import org.auscope.nvcl.server.vo.SpectralLogVo;
import org.auscope.nvcl.server.vo.TraySectionsVo;
import org.auscope.nvcl.server.vo.TsgParamVo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.drew.imaging.ImageProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import au.com.bytecode.opencsv.CSVWriter;

/**
 * Controller that handles all {@link Menu}-related requests
 *
 * @author Florence Tan
 * @author Peter Warren (CSIRO Earth Science and Resource Engineering)
 */

@Controller
public class MenuController {

	private static final Logger logger = LogManager.getLogger(MenuController.class);

	@Autowired
	@Qualifier(value = "createConfig")
	private ConfigVo configVo;

	@Autowired
	@Qualifier(value = "nvclDownloadGateway")
	private NVCLDownloadGateway nvclDownloadGateway;

	@Autowired
	@Qualifier(value = "jmsTemplate")
	private JmsTemplate jmsTemplate;

	@Autowired
	@Qualifier(value = "tsgRequestDestination")
	private Destination tsgReqDestination;

	@Autowired
	@Qualifier(value = "tsgReplyDestination")
	private Destination tsgReplyDestination;

	@Autowired
	@Qualifier(value = "wfsRequestDestination")
	private Destination wfsReqDestination;

	@Autowired
	@Qualifier(value = "wfsReplyDestination")
	private Destination wfsReplyDestination;

	@Autowired
	@Qualifier(value = "nvclDataSvc")
	private NVCLDataSvc nvclDataSvc;

	
	@Autowired
	@Qualifier(value = "nvclDownloadSvc")
	private NVCLDownloadSvc nvclDownloadSvc;
	
	@Autowired
	@Qualifier(value = "marshaller")
	private Jaxb2Marshaller marshaller;


	@RequestMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response) {

		return "index";
	}

	@RequestMapping("/monitorJVM.html")
	public ModelAndView monitorJVM() {
		logger.info("Returning monitorJVM view");
		ModelAndView mav = new ModelAndView("monitorJVM");
		return mav;
	}

	/**
	 * Handling error code 403 and error code 500
	 *
	 * @return
	 */
	@RequestMapping("/error_page.html")
	public ModelAndView access_error() {
		String errMsg = "Please contact the system administrator.";
		return new ModelAndView("error_page", "errmsg", errMsg);
	}

	@RequestMapping("/testlinks.html")
	public String testlinks(HttpServletRequest request, HttpServletResponse response) {

		return "testlinks";
	}

	/**
	 * Handling request when getDatasetCollection.html is called. Validate the
	 * URL parameter, get the list of dataset id and dataset name from DATASETS
	 * table and display the list as an xml output
	 *
	 * @param request
	 *            holeIdentifier (String): mandatory parameter for retrieving
	 *            the list of dataset id and name that associate with this
	 *            holeIdentifier
	 * @param response
	 *            response the request with a list of logs id and name in xml
	 *            format
	 * @return datasetCollection a StringBuffer that hold a list of xml tag
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	@RequestMapping("/getDatasetCollection.html")
	public ModelAndView datasetCollectionHandler(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "holeidentifier") String holeIdentifier) throws ServletException,
			IOException, SQLException, ParserConfigurationException, TransformerException {

		// mandatory field : holeIdentifier - validate if holeIdentifier is null
		if (Utility.stringIsBlankorNull(holeIdentifier)) {
			String errMsg = "holeidentifier is not valid.";
			return new ModelAndView("getDatasetCollectionUsage", "errmsg", errMsg);
		}

		// process list of dataset id and name
		logger.debug("processing list of dataset and name ...");

		DatasetCollectionVo datasetList = nvclDataSvc.getDatasetCollection(holeIdentifier);

		for (Iterator<DatasetVo> it2 = datasetList.getDatasetCollection().iterator(); it2.hasNext();) {
			DatasetVo dataset = it2.next();
			dataset.setSpectralLogCollection(nvclDataSvc.getSpectralLogCollection(dataset.getDatasetID()));
			dataset.setImageLogCollection(nvclDataSvc.getImageLogCollection(dataset.getDatasetID()));
			dataset.setLogCollection(nvclDataSvc.getLogCollection(dataset.getDatasetID()));
			dataset.setProfLogCollection(nvclDataSvc.getProfLogCollection(dataset.getDatasetID()));
		}
		response.setContentType("text/xml");

		this.marshaller.marshal(datasetList, new StreamResult(response.getOutputStream()));

		return null;

	}

	/**
	 * Handling request when getLogCollection.html is called. Validate the URL
	 * parameter, get the list of logs id and logs name from LOGS table and
	 * display the list as an xml output
	 *
	 * @param request
	 *            datasetid (String): mandatory parameter for retrieving the
	 *            list of logs id and name that associate with this datasetID
	 * @param response
	 *            response the request with a list of logs id and name in xml
	 *            format
	 * @return logCollection a StringBuffer that hold a list of xml tag
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 * @throws JAXBException
	 */

	@RequestMapping("/getLogCollection.html")
	public ModelAndView logCollectionHandler(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "datasetid") String datasetId,
			@RequestParam(required = false, value = "mosaicsvc", defaultValue = "no") String mosaicSvc)
			throws ServletException, IOException, SQLException {

		// mandatory field : datasetid
		// ensure datasetid is not null, empty or missing
		if (!Utility.isAlphanumericOrHyphen(datasetId)) {
			String errMsg = "datasetid is not valid.";
			return new ModelAndView("getLogCollectionUsage", "errmsg", errMsg);
		}

		// optional field : mosaicsvc - if exists, indicate whether
		// getLogCollection for mosaic service only

		if (Utility.stringIsBlankorNull(mosaicSvc) || !(mosaicSvc.equals("yes") || mosaicSvc.equals("no"))) {
			String errMsg = "mosaicSvc must be either yes or no.";
			return new ModelAndView("getLogCollectionUsage", "errmsg", errMsg);
		}

		logger.info("datasetID : " + datasetId);
		logger.info("get image log collection : " + mosaicSvc);

		// process list of logs id and name
		logger.debug("start processing...");

		if (mosaicSvc.equals("yes")) {
			ImageLogCollectionVo imglogList = nvclDataSvc.getImageLogCollection(datasetId);

			response.setContentType("text/xml");

			this.marshaller.marshal(imglogList, new StreamResult(response.getOutputStream()));
		} else {
			LogCollectionVo logList = nvclDataSvc.getLogCollection(datasetId);

			response.setContentType("text/xml");

			this.marshaller.marshal(logList, new StreamResult(response.getOutputStream()));
		}

		return null;

	}

	/**
	 * Handling request when mosaic.html or mosaicthumbnail.html is called. The
	 * mosaic handler will get the list of sample numbers from IMAGELOGDATA
	 * table and map the sample number into a list of img tag that point to
	 * Display_Tray_Thumb.html If it is mosaictraythumbnail.html, extra url link
	 * will be embeded to each thumbnail tray to retrieve the big tray image.
	 *
	 * @param request
	 *            logid (String) : mandatory parameter for retrieving the sample
	 *            datasetid (String) : optional, used only by
	 *            mosaicthumbnail.html number width (int) : number of column for
	 *            displaying the images (optional) startsampleno (int): starting
	 *            range of sample number (optional) endsampleno (int): ending
	 *            range of sample number (optional) *
	 * @return out a StringBuffer that hold a list of img tag pointing to
	 *         Display_Tray_Thumb.html
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	@RequestMapping("/mosaic*.html")
	public ModelAndView mosaicHandler(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "logid") String logId,
			@RequestParam(required = false, value = "datasetid") String datasetId,
			@RequestParam(required = false, value = "width", defaultValue = "3") Integer colWidth,
			@RequestParam(required = false, value = "startsampleno", defaultValue = "0") Integer startSampleNo,
			@RequestParam(required = false, value = "endsampleno", defaultValue = "999999") Integer endSampleNo,
			@RequestParam(required = false, value = "showdepths", defaultValue = "false") Boolean showdepths,
			@RequestParam(required = false, value = "scalarids", defaultValue = "") List<String> scalarids)
			throws ServletException, IOException, SQLException {

		// getting the URL details
		String scheme = request.getScheme(); // http
		String serverName = request.getServerName(); // hostname.com
		int serverPort = request.getServerPort(); // 80
		String contextPath = request.getContextPath(); // mywebapp
		String srvroot = scheme + "://" + serverName + ":" + serverPort + contextPath;
		String trayThumbnailLogId = null;
		String trayLogId = null;

		// ensure datasetid or logid is not null, empty or missing
		if (!Utility.isAlphanumericOrHyphen(logId) && !Utility.isAlphanumericOrHyphen(datasetId)) {

			String errMsg = "either datasetid or logid must be specified and be a valid database identifier.";
			return new ModelAndView("mosaicusage", "errmsg", errMsg);
		}

		// validate if sampleno is null or empty or missing, if true, redirect
		if (startSampleNo == null || startSampleNo < 0) {
			String errMsg = "startsampleno must be a non-negative integer or undefined";
			return new ModelAndView("mosaicusage", "errmsg", errMsg);
		}

		// validate if sampleno is null or empty or missing, if true, redirect
		if (endSampleNo == null || endSampleNo < 0) {
			String errMsg = "endsampleno must be a non-negative integer or undefined";
			return new ModelAndView("mosaicusage", "errmsg", errMsg);
		}
		String domainlogId = null;
		if (datasetId != null) {
			ImageLogCollectionVo imglogList = nvclDataSvc.getImageLogCollection(datasetId);
			for (Iterator<ImageLogVo> it2 = imglogList.getimageLogCollection().iterator(); it2.hasNext();) {
				ImageLogVo logCollectionMosaicVo = it2.next();
				if (logCollectionMosaicVo.getLogName().equals("Tray Thumbnail Images"))
					trayThumbnailLogId = logCollectionMosaicVo.getLogID();
				else if (logCollectionMosaicVo.getLogName().equals("Tray Images"))
					trayLogId = logCollectionMosaicVo.getLogID();
			}
			if (!Utility.isAlphanumericOrHyphen(trayThumbnailLogId) || !Utility.isAlphanumericOrHyphen(trayLogId)) {
				String errMsg = "tray images could not be found for this datasetid";
				return new ModelAndView("mosaicusage", "errmsg", errMsg);
			}
			logId = trayThumbnailLogId;
			domainlogId = nvclDataSvc.getImageDomainlogId(logId);
		} else {
			domainlogId = nvclDataSvc.getImageDomainlogId(logId);
			// check this is a tray domain based image log. Scalar try maps
			// cannot be generated on other domains.
			if (domainlogId == null || !nvclDataSvc.getLogDetails(domainlogId).getLogName().equals("Tray Domain")) {
				scalarids.clear();
			} else {
				datasetId = nvclDataSvc.getLogDetails(logId).getDatasetID();
				ImageLogCollectionVo imglogList = nvclDataSvc.getImageLogCollection(datasetId);
				for (Iterator<ImageLogVo> it2 = imglogList.getimageLogCollection().iterator(); it2.hasNext();) {
					ImageLogVo logCollectionMosaicVo = it2.next();
					if (logCollectionMosaicVo.getLogName().equals("Tray Images"))
						trayLogId = logCollectionMosaicVo.getLogID();
				}
				if (trayLogId == logId)
					trayLogId = null;
			}
		}

		StringBuffer imageURL = new StringBuffer("<div class=\"NVCLMosaicContainer\" >");
		// getting list of sequence sample number based on the start and end
		// sample number range
		ArrayList<ImageDataVo> sampleNoList = (ArrayList<ImageDataVo>) nvclDataSvc.getSampleNo(logId, startSampleNo,
				endSampleNo);

		if (sampleNoList.isEmpty()) {
			logger.error("Empty sample no list !!");
			String errMsg = "There are no images available in this dataset or log id";
			return new ModelAndView("mosaicusage", "errmsg", errMsg);
		}

		DomainDataCollectionVo domainDataList = null;
		if (domainlogId != null) {
			domainDataList = nvclDataSvc.getDomainData(domainlogId);
		}
		int i = 0;
		colWidth = min(sampleNoList.size(), colWidth);
		for (Iterator<ImageDataVo> it1 = sampleNoList.iterator(); it1.hasNext();) {
			// extract sample number from the array list
			ImageDataVo imageDataVo = it1.next();
			// by default display 3 image per row
			if (i != 0 && (i % colWidth == 0)) {
				imageURL.append("<div style=\"clear:both;\"></div>");
			}

			imageURL.append("<div class=\"NVCLMosaicCell\" style=\"max-width: " + 100 / colWidth + "%\">");

			String titletext = "Core Image";

			Float startdepth = null, enddepth = null;
			if (domainDataList != null && domainDataList.getDomainDataCollection().size() > 0) {
				for (Iterator<DomainDataVo> it2 = domainDataList.getDomainDataCollection().iterator(); it2.hasNext();) {
					// extract sampleNo, startValue and endValue from the array
					// list
					DomainDataVo domainDataVo = it2.next();
					if (domainDataVo.getSampleNo() == imageDataVo.getSampleNo()) {
						startdepth = domainDataVo.getStartValue();
						enddepth = domainDataVo.getEndValue();
						break;
					}
				}
			}
			if (showdepths || scalarids.size() > 0)
				imageURL.append("<div class=\"NVCLMosaicCellContent\">");

			if (startdepth != null && enddepth != null) {
				if (Float.compare(startdepth, enddepth) == 0) {
					if (showdepths)
						imageURL.append("<div class=\"NVCLMosaicCellDepths\" ><p class=\"NVCLMosaicCellPara\" >"
								+ String.format("%.3f", startdepth) + "m</p></div>");
					titletext += " at depth " + String.format("%.3f", startdepth) + "m";
				} else {
					if (showdepths)
						imageURL.append("<div class=\"NVCLMosaicCellDepths\" ><p class=\"NVCLMosaicCellPara\" >"
								+ String.format("%.3f", startdepth) + "m - " + String.format("%.3f", enddepth)
								+ "m</p></div>");
					titletext += " for depth range " + String.format("%.3f", startdepth) + "m - "
							+ String.format("%.3f", enddepth) + "m";
				}
			}

			imageURL.append("<div class=\"NVCLMosaicCellImg\" >");

			if (trayLogId != null) {
				imageURL.append("<a href=\"" + srvroot + "/imageCarousel.html?logid=" + trayLogId + "&sampleno="
						+ imageDataVo.getSampleNo() + "\" target=\"_blank\">" + "<img title=\"" + titletext
						+ "\" class=\"NVCLMosaicImage\" " + "src=\"" + srvroot + "/Display_Tray_Thumb.html?logid="
						+ logId + "&sampleno=" + imageDataVo.getSampleNo() + "\" alt=\"Core Image\" ></a></div>");

			} else {
				imageURL.append("<img title=\"" + titletext + "\" class=\"NVCLMosaicImage\" " + "src=\"" + srvroot
						+ "/Display_Tray_Thumb.html?logid=" + logId + "&sampleno=" + imageDataVo.getSampleNo()
						+ "\" alt=\"Core Image\" ></div>");

			}
			for (Iterator<String> it3 = scalarids.iterator(); it3.hasNext();) {
				imageURL.append("<div class=\"NVCLMosaicCellImg\" ><img class=\"pixelated\" src=\"" + srvroot
						+ "/gettraymap.html?logid=" + it3.next() + "&trayindex=" + imageDataVo.getSampleNo()
						+ "\" style=\"display: block;  height:100%;width:100%;\" ></div>");
			}

			if (showdepths || scalarids.size() > 0)
				imageURL.append("</div>");

			imageURL.append("</div>");

			i++;
		}

		imageURL.append("</div>");

		return new ModelAndView("mosaic", "imageURL", imageURL);
	}

	/**
	 * Handling request when Display_Tray_Thumb.html is called. Validate the URL
	 * parameters, call the dao services in NVCLChartSvc class to retrieve the
	 * thumbnail image from IMAGELOGS table
	 *
	 * @param request
	 *            logid (String) : first primary key for retrieving the tray
	 *            thumbnail
	 * @param request
	 *            samplenumber (int) : second primary key for retrieving the
	 *            tray thumbnail
	 * @param response
	 *            response the request with an image as Blob output
	 * @return out chart in JPEG format
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	@RequestMapping("/Display_Tray_Thumb.html")
	public ModelAndView TrayThumbHandler(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "logid") String logId,
			@RequestParam(required = false, value = "sampleno") Integer sampleNo) throws ServletException, IOException,
			SQLException {

		// mandatory field : logid
		// ensure logid is not null, empty or missing
		if (!Utility.isAlphanumericOrHyphen(logId)) {
			String errMsg = "Image Logid is not valid.";
			return new ModelAndView("displaytraythumbusage", "errmsg", errMsg);
		}

		// validate if sampleno is null or empty or missing, if true, redirect
		if (sampleNo == null || sampleNo < 0) {
			String errMsg = "sampleno must be a non-negative integer.";
			return new ModelAndView("displaytraythumbusage", "errmsg", errMsg);
		}

		ImageDataVo imagedata = nvclDataSvc.getImageData(logId, sampleNo);
		logger.debug("processing the blob data...");
		Blob imgData = imagedata.getImgData();

		response.setContentType("image/jpeg");

		int imgLength = (int) imgData.length();

		response.setContentLength(imgLength);

		response.getOutputStream().write(imgData.getBytes(1, imgLength));

		return null;
	}

	/**
	 * Handling request when getDatasetCollection.html is called. Validate the
	 * URL parameter, get the list of dataset id and dataset name from DATASETS
	 * table and display the list as an xml output
	 *
	 * @param request
	 *            holeIdentifier (String): mandatory parameter for retrieving
	 *            the list of dataset id and name that associate with this
	 *            holeIdentifier
	 * @param response
	 *            response the request with a list of logs id and name in xml
	 *            format
	 * @return datasetCollection a StringBuffer that hold a list of xml tag
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	@RequestMapping("/getImageTrayDepth.html")
	public ModelAndView imageTrayDepthHandler(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "logid") String logId) throws ServletException, IOException,
			SQLException {

		// mandatory field : logid
		// ensure logid is not null, empty or missing
		if (!Utility.isAlphanumericOrHyphen(logId)) {
			String errMsg = "Image Logid is not valid.";
			return new ModelAndView("getImageTrayDepthUsage", "errmsg", errMsg);
		}

		String domainlogId = null;

		// get domainlog_id based on the image tray log id
		try {
			domainlogId = nvclDataSvc.getImageDomainlogId(logId);
		} catch (DataAccessException e) {
			logger.error("DataAccessException : " + e);
			String errMsg = "A valid image tray logid must be provided for this service to function.";
			return new ModelAndView("getImageTrayDepthUsage", "errmsg", errMsg);
		}

		// get start and end depth for all trays based on the tray image domain
		// log id
		DomainDataCollectionVo domainDataList = nvclDataSvc.getDomainData(domainlogId);

		response.setContentType("text/xml");
		this.marshaller.marshal(domainDataList, new StreamResult(response.getOutputStream()));

		return null;

	}

	/**
	 * Handling request when plotscalar.html is called. Will create a chart
	 * (e.g. stacked bar chart or scattered chart or line chart) and return the
	 * chart as an image/PNG format chart in the http response
	 *
	 * @param request
	 *            input url parameters :
	 *            <ul>
	 *            <li>logId (String) : log id, mandatory
	 *            <li>width (int) : image width in pixels, default to 300 if
	 *            null
	 *            <li>height (int) : image height in pixels, default to 600 if
	 *            null
	 *            <li>samplinginterval (float) : double precision floating point
	 *            number representing the sampling interval to plot over
	 *            (defaults to 1m intervals if null)
	 *            <li>startdepth (float) : start depth to plot from, default to
	 *            0 if null
	 *            <li>enddepth (float) : end depth to plot to, default to 999999
	 *            if null
	 *            <li>graphtype (int) : 1 = Stacked bar chart, 2 = scattered
	 *            chart, 3 = line chart, default to 1
	 *            </ul>
	 *
	 * @return image/PNG Return a chart in image/PNG format
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/plotscalar.html")
	public ModelAndView DisplayChartHandler(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "logid") String logId,
			@RequestParam(required = false, value = "samplinginterval", defaultValue = "1.0") Float samplingInterval,
			@RequestParam(required = false, value = "startdepth", defaultValue = "0") Float startDepth,
			@RequestParam(required = false, value = "enddepth", defaultValue = "999999") Float endDepth,
			@RequestParam(required = false, value = "width", defaultValue = "300") Integer width,
			@RequestParam(required = false, value = "height", defaultValue = "600") Integer height,
			@RequestParam(required = false, value = "graphtype", defaultValue = "1") Integer graphType,
			@RequestParam(required = false, value = "legend", defaultValue = "1") Integer legend)
			throws ServletException, IOException, Exception {

		// mandatory field : logid
		// ensure logid is not null, empty or missing
		if (!Utility.isAlphanumericOrHyphen(logId)) {
			String errMsg = "Logid is not valid.";
			return new ModelAndView("plotscalarusage", "errmsg", errMsg);
		}

		try {

			// generate domainDepthRange based log id - used to scale the height
			// of the graph
			float[] domainChartDepthRange = nvclDataSvc.getDomainChartDepthRange(logId);

			// getting the correct start and end depth if specified in the
			// request
			float startRange = max(domainChartDepthRange[0], startDepth);
			float endRange = min(domainChartDepthRange[1], endDepth);

			LogDetailsVo logDetail = nvclDataSvc.getLogDetails(logId);

			OutputStream bufferStream = response.getOutputStream();
			JFreeChart chart = null;
			switch (graphType) {
			/************
			 * Bar Chart
			 ************/
			case 1:
				switch (logDetail.getLogType()) {
				case 1:
					ArrayList ds = nvclDataSvc.getdownSampledClassData(logId, startDepth, endDepth, samplingInterval,
							graphType);

					DefaultTableXYDataset dataSet = (DefaultTableXYDataset) ds.get(0);
					@SuppressWarnings("unchecked")
					ArrayList<Integer> arrSeriesColor = (ArrayList<Integer>) ds.get(1);

					chart = nvclDataSvc.createStackedXYBarChart(dataSet, logDetail.getLogName(), arrSeriesColor,
							samplingInterval, startRange, endRange, legend);

					break;

				case 2:
					logger.debug("graphtype == 1, logType == 1, generate Bar Chart...");
					ArrayList ds2 = nvclDataSvc.getdownSampledFloatDataDao(logId, startDepth, endDepth,
							samplingInterval, graphType);

					IntervalXYDataset dsInterval = (IntervalXYDataset) ds2.get(0);
					chart = nvclDataSvc.createXYBarChart(dsInterval, logDetail.getLogName(), samplingInterval,
							startRange, endRange, legend);

					break;
				}
				break;

			/************
			 * Scattered Chart
			 ************/
			case 2:
				switch (logDetail.getLogType()) {
				case 1:
					logger.debug("graphtype == 3, logType == 1, generate multiple series Scattered Chart...");
					ArrayList ds = nvclDataSvc.getdownSampledClassData(logId, startDepth, endDepth, samplingInterval,
							graphType);

					XYSeriesCollection dataSet = (XYSeriesCollection) ds.get(0);
					@SuppressWarnings("unchecked")
					ArrayList<Integer> arrSeriesColor = (ArrayList<Integer>) ds.get(1);

					chart = nvclDataSvc.createScatterChart(dataSet, logDetail.getLogName(), arrSeriesColor,
							samplingInterval, startRange, endRange, legend);

					break;

				case 2:
					logger.debug("graphtype == 3, logType == 2, generate Scattered Chart...");
					ArrayList ds2 = nvclDataSvc.getdownSampledFloatDataDao(logId, startDepth, endDepth,
							samplingInterval, graphType);

					XYSeriesCollection dsInterval = (XYSeriesCollection) ds2.get(0);
					chart = nvclDataSvc.createScatterChart(dsInterval, logDetail.getLogName(), samplingInterval,
							startRange, endRange, legend);

					break;
				}
				break;

			/************
			 * Line Chart
			 ************/
			case 3:
				switch (logDetail.getLogType()) {
				case 1:
					logger.debug("graphtype == 3, logType == 1, generate Multiple Lines Chart...");
					ArrayList ds = nvclDataSvc.getdownSampledClassData(logId, startDepth, endDepth, samplingInterval,
							graphType);
					XYSeriesCollection dataSet = (XYSeriesCollection) ds.get(0);
					@SuppressWarnings("unchecked")
					ArrayList<Integer> arrSeriesColor = (ArrayList<Integer>) ds.get(1);

					chart = nvclDataSvc.createLineChart(dataSet, logDetail.getLogName(), arrSeriesColor,
							samplingInterval, startRange, endRange, legend);
					break;

				case 2:
					logger.debug("graphtype == 3, logType ==2, generate Line Chart...");
					ArrayList ds2 = nvclDataSvc.getdownSampledFloatDataDao(logId, startDepth, endDepth,
							samplingInterval, graphType);

					XYSeriesCollection dsInterval = (XYSeriesCollection) ds2.get(0);
					chart = nvclDataSvc.createLineChart(dsInterval, logDetail.getLogName(), samplingInterval,
							startRange, endRange, legend);
					break;
				}
				break;
			default:
				String errMsg = "graphType must be 1, 2 or 3";
				return new ModelAndView("plotscalarusage", "errmsg", errMsg);
			}

			response.setContentType("image/PNG");
			ChartUtilities.writeChartAsPNG(bufferStream, chart, width, height);
			return null;
		} catch (DataAccessException dataerr) {
			logger.error("DataAccessException : " + dataerr);
			String errMsg = "A valid logid must be provided for this service to function.";
			return new ModelAndView("plotscalarusage", "errmsg", errMsg);
		} catch (SQLException sqlerr) {
			logger.error("SQLException : " + sqlerr);
			String errMsg = "There is error with the database connection, please contact the administrator.";
			return new ModelAndView("error_page", "errmsg", errMsg);
		} catch (Exception e) {
			logger.error("Exception : " + e);
			return new ModelAndView("error_page", "errmsg", e.getMessage());
		}
	}

	/**
	 * Handling request when plotmultiscalar.html is called. Will create a
	 * composition of HTML images that represent a set of URI chart parameters
	 * within a <div> tag - which make a request to plotscalars.html to retrieve
	 * the actual images from database.
	 *
	 * @param request
	 *            input url parameters :
	 *            <ul>
	 *            <li>_logIdList (String List) : mandatory, maximum up to 6 log
	 *            id
	 *            <li>width (int) : image width in pixels, default to 300 if
	 *            null
	 *            <li>height (int) : image height in pixels, default to 600 if
	 *            null
	 *            <li>samplinginterval (float) : double precision floating point
	 *            number representing the sampling interval to plot over
	 *            (defaults to 1m intervals if null)
	 *            <li>startdepth (float) : start depth to plot from, default to
	 *            0 if null
	 *            <li>enddepth (float) : end depth to plot to, default to 999999
	 *            if null
	 *            <li>graphtype (int) : 1 = Stacked bar chart, 2 = scattered
	 *            chart, 3 = line chart, default to 1
	 *            </ul>
	 *
	 * @return ModelAndView Return a list of image urls (range from 1 - 6) that
	 *         will make the actual request to plot the chart
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/plotmultiscalars.html")
	public ModelAndView chartHandler(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "logid") String[] logIdList,
			@RequestParam(required = false, value = "samplinginterval", defaultValue = "1.0") Float samplingInterval,
			@RequestParam(required = false, value = "startdepth", defaultValue = "0") Float startDepth,
			@RequestParam(required = false, value = "enddepth", defaultValue = "999999") Float endDepth,
			@RequestParam(required = false, value = "width", defaultValue = "300") Integer width,
			@RequestParam(required = false, value = "height", defaultValue = "600") Integer height,
			@RequestParam(required = false, value = "graphtype", defaultValue = "1") Integer graphType,
			@RequestParam(required = false, value = "legend", defaultValue = "1") Integer legend)
			throws ServletException, IOException, Exception {

		// getting the URL details
		String scheme = request.getScheme(); // http
		String serverName = request.getServerName(); // hostname.com
		int serverPort = request.getServerPort(); // 80
		String contextPath = request.getContextPath(); // mywebapp
		String srvroot = scheme + "://" + serverName + ":" + serverPort + contextPath;
		StringBuffer imageUrlList = new StringBuffer();

		// mandatory field : logid
		// ensure logid is not null, empty or missing
		if (logIdList == null || logIdList.length == 0) {
			String errMsg = "A valid logid must be provided for this service to function.";
			return new ModelAndView("plotmultiscalarsusage", "errmsg", errMsg);
		}
		if (logIdList.length > 6) {
			String errMsg = "A Maximum of 6 scalars are allowed per request.";
			return new ModelAndView("plotmultiscalarsusage", "errmsg", errMsg);
		}
		for (int i = 0; i < logIdList.length; i++) {
			if (!Utility.isAlphanumericOrHyphen(logIdList[i])) {
				String errMsg = "Logid " + (i + 1) + " is not valid.";
				return new ModelAndView("plotmultiscalarsusage", "errmsg", errMsg);
			}
		}
		if (samplingInterval == null)
			samplingInterval = 1.0F;
		if (startDepth == null)
			startDepth = 0F;
		if (endDepth == null)
			endDepth = 999999F;
		if (width == null)
			width = 300;
		if (height == null)
			height = 600;
		if (graphType == null)
			graphType = 1;
		if (legend == null)
			legend = 1;

		/**
		 * Create a composition of HTML images that represent a set of URI chart
		 * parameters within a <div> tag.
		 */
		imageUrlList.append("<div>");

		for (int i = 0; i < logIdList.length; i++) {

			/*
			 * Create a collection of URL logs requests within a html div
			 * container.
			 */
			imageUrlList.append("<img src=\'" + srvroot + "/plotscalar.html" + "?logid=" + logIdList[i]
					+ "&startdepth=" + startDepth + "&enddepth=" + endDepth + "&samplinginterval=" + samplingInterval
					+ "&graphtype=" + graphType + "&width=" + width + "&height=" + height + "&legend=" + legend
					+ "' style='border-width:0px;'>");
		}

		logger.debug(imageUrlList.toString());

		return new ModelAndView("plotmultiscalars", "imageURL", imageUrlList);

	}

	/**
	 * Handling request when downloadscalars.html is called. Will make request
	 * to database to retrieve the scalars values, set to csv files, zip the csv
	 * files and response back as a zip file
	 *
	 * @param request
	 *            input url parameters :
	 *            <ul>
	 *            <li>logIdList (String List) : mandatory, maximum up to 6 log
	 *            ids
	 *            </ul>
	 *
	 * @return zip file containing csv files
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/downloadscalars.html")
	public ModelAndView DownloadScalarHandler(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "logid") String[] logIdList,
			@RequestParam(required = false, value = "startdepth") Float startdepth,
			@RequestParam(required = false, value = "enddepth") Float enddepth,
			@RequestParam(required = false, value = "outputformat", defaultValue = "csv") String outputformat)
			throws ServletException, IOException, Exception {

		// mandatory field : logid
		// ensure logid is not null, empty or missing
		if (logIdList == null || logIdList.length == 0) {
			String errMsg = "A valid logid must be provided for this service to function.";
			return new ModelAndView("downloadscalarsusage", "errmsg", errMsg);
		}
		for (int i = 0; i < logIdList.length; i++) {
			if (!Utility.isAlphanumericOrHyphen(logIdList[i])) {
				String errMsg = "Logid " + (i + 1) + " is not valid.";
				return new ModelAndView("downloadscalarsusage", "errmsg", errMsg);
			}
		}

		/**
		 * Data verification : a) check that the log id(s) can be found in LOGS
		 * table b) check that the log id(s) all having the same DOMAINLOG_ID c)
		 * check that the log id(s) will have only log type 1 or 2
		 */
		try {

			// a) check that the log id(s) can be found in LOGS table
			if (!nvclDataSvc.validateLogId(logIdList)) {
				String errMsg = "Log id (s) not found in the database.";
				return new ModelAndView("downloadscalarsusage", "errmsg", errMsg);
			}
			// b) check that the log id(s) all having the same DOMAINLOG_ID
			if (!nvclDataSvc.validateDomainlogId(logIdList)) {
				String errMsg = "Log ids are not part of the same dataset or are not on the same sampling domain";
				return new ModelAndView("downloadscalarsusage", "errmsg", errMsg);
			}

			// Retrieve log details and generate dynamic sql query for chosen
			// scalar(s)
			List<LogDetailsVo> logDetailsVoList = nvclDataSvc.getLogDetails(logIdList);
			Object[] params = new Object[logDetailsVoList.size() + 1 + (startdepth != null && enddepth != null ? 2 : 0)];
			String logId = null;
			String logName = null;
			String domainlogId = null;
			int logType = 0;
			int i = 0;
			String sqlSelectPart = "select DOMAINLOGDATA.STARTVALUE AS StartDepth, DOMAINLOGDATA.ENDVALUE as EndDepth";
			String sqlFromPart = "from DOMAINLOGDATA ";
			String sqlWherePart = "where DOMAINLOGDATA.LOG_ID=?";
			String finalSql = null;
			ArrayList<String> strKeysArr = new ArrayList<String>();

			strKeysArr.add("StartDepth");
			strKeysArr.add("EndDepth");

			for (Iterator it1 = logDetailsVoList.iterator(); it1.hasNext();) {
				i = i + 1;
				LogDetailsVo logDetailsVo = (LogDetailsVo) it1.next();
				logId = logDetailsVo.getLogId();
				logName = logDetailsVo.getLogName();
				domainlogId = logDetailsVo.getDomainlogId();
				logType = logDetailsVo.getLogType();
				if (logType == 5) {
					SpectralLogCollectionVo speclogs = nvclDataSvc
							.getSpectralLogCollection(logDetailsVo.getDatasetID());
					for (Iterator<SpectralLogVo> it2 = speclogs.getSpectralLogCollection().iterator(); it2.hasNext();) {
						SpectralLogVo speclog = it2.next();
						if (speclog.getLogID().equals(logId)) {
							for (int j = 0; j < speclog.getWavelengths().length; j++)
								strKeysArr.add(logName + speclog.getWavelengths()[j]);
						}
					}
				} else
					strKeysArr.add(logName.replaceAll("\\W", "_"));
				// c) check that the log id(s) will have only log type 1 or 2
				if (logType != 1 && logType != 2 && logType != 5 && logType != 6) {
					// throw new
					// Exception("Not all log id having logtype 1 or 2 !!");
					String errMsg = "All logids must have logtype 1, 2, 5 or 6 for this service to function.";
					return new ModelAndView("downloadscalarsusage", "errmsg", errMsg);
				}
				// initialize the variables
				if (i == 1) {
					params[0] = domainlogId;
				}

				switch (logDetailsVo.getLogType()) {
				case 1:
					sqlSelectPart += ", coalesce (classspec" + i + ".CLASSTEXT, class" + i + ".CLASSTEXT) as Scal" + i
							+ " ";
					sqlFromPart += " inner join CLASSLOGDATA result"
							+ i
							+ " on result"
							+ i
							+ ".SAMPLENUMBER=DOMAINLOGDATA.SAMPLENUMBER LEFT OUTER JOIN CLASSSPECIFICCLASSIFICATIONS classspec"
							+ i + " on result" + i + ".CLASSLOGVALUE = classspec" + i + ".INTINDEX and classspec" + i
							+ ".LOG_ID=result" + i + ".LOG_ID LEFT OUTER JOIN LOGS log" + i + " on result" + i
							+ ".log_id=log" + i + ".log_id LEFT OUTER JOIN CLASSIFICATIONS class" + i + " ON result"
							+ i + ".CLASSLOGVALUE = class" + i + ".INTINDEX and class" + i + ".ALGORITHMOUTPUT_ID=log"
							+ i + ".algorithmoutput_id";
					break;

				case 2:
					sqlSelectPart += ", result" + i + ".DECIMALVALUE as Scal" + i + " ";
					sqlFromPart += " inner join DECIMALLOGDATA result" + i + " on result" + i
							+ ".SAMPLENUMBER=DOMAINLOGDATA.SAMPLENUMBER";
					break;
				case 5:
					sqlSelectPart += ", result" + i + ".SPECTRALVALUES as Spec" + i + " ";
					sqlFromPart += " inner join SPECTRALLOGDATA result" + i + " on result" + i
							+ ".SAMPLENUMBER=DOMAINLOGDATA.SAMPLENUMBER";
					break;
				case 6:
					sqlSelectPart += ", result" + i + ".MASKVALUE as Scal" + i + " ";
					sqlFromPart += " inner join MASKLOGDATA result" + i + " on result" + i
							+ ".SAMPLENUMBER=DOMAINLOGDATA.SAMPLENUMBER";
					break;
				default:
					String errMsg = "Error !!  Logtype does not equal 1 or 2 !!";
					return new ModelAndView("error_page", "errmsg", errMsg);

				}

				sqlWherePart += " AND result" + i + ".LOG_ID = ?";
				params[i] = logId;

			}
			if (startdepth != null && enddepth != null) {
				sqlWherePart += " AND DOMAINLOGDATA.STARTVALUE > ? AND DOMAINLOGDATA.ENDVALUE < ?";
				params[logDetailsVoList.size() + 1] = startdepth;
				params[logDetailsVoList.size() + 2] = enddepth;
			}
			finalSql = sqlSelectPart + " " + sqlFromPart + " " + sqlWherePart + " order by DOMAINLOGDATA.samplenumber";

			// submit the sql to get the scalar details
			List<Map<String, Object>> scalarDetailList = nvclDataSvc.getScalarDetails(finalSql, params);

			if (outputformat.equals("csv")) {
				// set response as csv attachement
				response.setContentType("text/csv");
				response.setHeader("Content-Disposition", "attachment; filename=\"scalar.csv\"");

				// writing scalar details as csv file using opencsv lib
				// BufferedWriter out = new BufferedWriter(new
				// FileWriter("scalars.csv"));
				BufferedWriter out = new BufferedWriter(response.getWriter());
				CSVWriter writer = new CSVWriter(out, ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER,
						"\n");

				String[] strarray = new String[strKeysArr.size()];
				strKeysArr.toArray(strarray);
				writer.writeNext(strarray);

				for (Iterator it1 = scalarDetailList.iterator(); it1.hasNext();) {
					Map<String, Object> scalarDetailMap = (Map<String, Object>) it1.next();

					// get values object array and convert to string array
					Object[] objValuesArr = scalarDetailMap.values().toArray();
					String[] strValuesArr = new String[objValuesArr.length];
					for (int j = 0; j < strValuesArr.length; j++) {
						if (objValuesArr[j] != null) {
							if (objValuesArr[j] instanceof byte[]) {
								FloatBuffer fwhmfloatbuf = ByteBuffer.wrap((byte[]) objValuesArr[j])
										.order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer();
								float[] fwhmfloatArray = new float[fwhmfloatbuf.limit()];
								fwhmfloatbuf.get(fwhmfloatArray);
								strValuesArr[j] = Utility.floatArrayToString(fwhmfloatArray);
							} else
								strValuesArr[j] = objValuesArr[j].toString();
						} else {
							strValuesArr[j] = "null";
						}
					}

					writer.writeNext(strValuesArr);

				}

				writer.close();
			} else {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				new ObjectMapper().writeValue(response.getOutputStream(),scalarDetailList);
			}
			return null;

		} catch (Exception e) {
			logger.error("Exception occurred in DownloadScalarHandler : " + e);
			String errMsg = "Exception occurred : " + e;
			return new ModelAndView("error_page", "errmsg", errMsg);
		}
	}

	@RequestMapping("/checkscannedborehlelinks.html")
	public ModelAndView ScannedBoreholeLinks(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false, value = "serviceurl") String serviceurl,
			@RequestParam(required = false, value = "triggertsgdownloads", defaultValue = "false") Boolean triggerdownloads)
			throws ServletException, IOException, Exception {

		NameValuePair getrequest = new NameValuePair("request", "GetFeature");
		NameValuePair typeName = new NameValuePair("typeName", "nvcl:ScannedBoreholeCollection");
		NameValuePair version = new NameValuePair("version", "1.1.0");

		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod("http://" + serviceurl);
		method.setQueryString(new NameValuePair[] { getrequest, typeName, version });
		int statusCode = client.executeMethod(method);

		int totaldatasets = 0;
		FeatureCollectionVo validuris = new FeatureCollectionVo();
		FeatureCollectionVo invaliduris = new FeatureCollectionVo();
		if (statusCode == HttpStatus.SC_OK) {
			FeatureCollectionVo scannedboreholes = null;
			if (serviceurl.contains("www.mrt.tas.gov.au")) {
				// mrt have an old version of the scannedborehole collection
				// config so some xslt is required.
				String xslt = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:wfs=\"http://www.opengis.net/wfs\" xmlns:gml=\"http://www.opengis.net/gml\" xmlns:nvcl=\"http://www.auscope.org/nvcl\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" >"
						+ "<xsl:template match=\"/\"><wfs:FeatureCollection><xsl:attribute name=\"numberOfFeatures\"><xsl:value-of select=\"count(wfs:FeatureCollection/gml:featureMembers/nvcl:ScannedBoreholeCollection/nvcl:scannedBorehole)\" /></xsl:attribute><gml:featureMembers><xsl:for-each select=\"wfs:FeatureCollection/gml:featureMembers/nvcl:ScannedBoreholeCollection/nvcl:scannedBorehole\">"
						+ "<nvcl:ScannedBoreholeCollection><nvcl:scannedBorehole><xsl:attribute name=\"xlink:href\"><xsl:value-of select=\"@xlink:href\" />"
						+ "</xsl:attribute><xsl:attribute name=\"xlink:title\"><xsl:value-of select=\"@xlink:title\" /></xsl:attribute></nvcl:scannedBorehole></nvcl:ScannedBoreholeCollection>"
						+ "</xsl:for-each></gml:featureMembers></wfs:FeatureCollection></xsl:template></xsl:stylesheet>";

				TransformerFactory factory = TransformerFactory.newInstance();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				StreamResult resultstream = new StreamResult(baos);
				Transformer transformer = factory.newTransformer(new StreamSource(new StringReader(xslt)));
				transformer.transform(new StreamSource(method.getResponseBodyAsStream()), resultstream);
				StreamSource prossessedxml = new StreamSource(new StringReader(baos.toString()));
				scannedboreholes = (FeatureCollectionVo) marshaller.unmarshal(prossessedxml);
			} else
				scannedboreholes = (FeatureCollectionVo) marshaller.unmarshal(new StreamSource(method
						.getResponseBodyAsStream()));
			totaldatasets = scannedboreholes.getFeatures();
			for (int i = 0; i < scannedboreholes.getscannedboreholeCollection().size(); i++) {
				ScannedBoreholeVo borehole = scannedboreholes.getscannedboreholeCollection().get(i);
				try {
					String BoreholeID = borehole.getBoreholevo().getURI()
							.substring(borehole.getBoreholevo().getURI().lastIndexOf('/') + 1);
					String hostwgeoserver = serviceurl.substring(0, serviceurl.lastIndexOf('/'));
					String hostname = hostwgeoserver.substring(0, hostwgeoserver.lastIndexOf('/'));
					GetMethod DSIDLookup = new GetMethod("http://" + hostname
							+ "/NVCLDataServices/getDatasetCollection.html");
					DSIDLookup.setQueryString(new NameValuePair[] { new NameValuePair("holeidentifier", BoreholeID) });
					if (client.executeMethod(DSIDLookup) == HttpStatus.SC_OK) {
						DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
						Document document = builder.parse(DSIDLookup.getResponseBodyAsStream());
						XPath xpath = XPathFactory.newInstance().newXPath();

						NodeList listDatasets = document.getElementsByTagName("Dataset");
						for (int datasetsindex = 0; datasetsindex < listDatasets.getLength(); datasetsindex++) {
							if (xpath.evaluate("DatasetName", listDatasets.item(datasetsindex)).equals(
									borehole.getBoreholevo().getTitle())) {
								String datasetid = (String) xpath.evaluate("DatasetID",
										listDatasets.item(datasetsindex));
								borehole.setdSID(datasetid);
								break;
							}
						}
					}
					DSIDLookup.releaseConnection();
					GetMethod URILookup = new GetMethod();
					URILookup.setURI(new URI(borehole.getBoreholevo().getURI(), false));
					URILookup.setFollowRedirects(true);
					if (client.executeMethod(URILookup) == HttpStatus.SC_OK) {

						DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
						Document document = builder.parse(URILookup.getResponseBodyAsStream());
						XPath xpath = XPathFactory.newInstance().newXPath();

						String boreholename = (String) xpath.evaluate(
								"FeatureCollection//featureMembers//Borehole//name[2]//text()", document,
								XPathConstants.STRING);
						String latlong = (String) xpath
								.evaluate(
										"FeatureCollection//featureMembers//Borehole//collarLocation//BoreholeCollar//location//Point//pos//text()",
										document, XPathConstants.STRING);
						String features = (String) xpath.evaluate("FeatureCollection//@numberOfFeatures", document,
								XPathConstants.STRING);
						int featurecount = Integer.parseInt(features);
						String[] latlongs = latlong.split(" ");
						borehole.setName(boreholename);
						if (((String) xpath
								.evaluate(
										"FeatureCollection//featureMembers//Borehole//collarLocation//BoreholeCollar//location//Point//@srsName",
										document, XPathConstants.STRING))
								.equals("http://www.opengis.net/gml/srs/epsg.xml#8311")) {
							borehole.setLat(latlongs[1]);
							borehole.setLon(latlongs[0]);
						} else {
							borehole.setLat(latlongs[0]);
							borehole.setLon(latlongs[1]);
						}
						if (featurecount == 1) {
							validuris.getscannedboreholeCollection().add(borehole);
						} else {
							invaliduris.getscannedboreholeCollection().add(borehole);
						}
					} else {
						invaliduris.getscannedboreholeCollection().add(borehole);
					}
					URILookup.releaseConnection();
					if (triggerdownloads && Utility.isAlphanumericOrHyphen(borehole.getdSID()) && i >= 191 && i < 220) {
						GetMethod triggerTSGDownload = new GetMethod("http://" + hostname
								+ "/NVCLDownloadServices/downloadtsg.html");
						triggerTSGDownload.setQueryString(new NameValuePair[] {
								new NameValuePair("datasetid", borehole.getdSID()),
								new NameValuePair("email", "peter.warren@csiro.au") });
						client.executeMethod(triggerTSGDownload);
						triggerTSGDownload.releaseConnection();
					}
				} catch (Exception ex) {
					invaliduris.getscannedboreholeCollection().add(borehole);
				}
			}
		}
		StringBuilder alltables = new StringBuilder();
		if (invaliduris.getscannedboreholeCollection().size() > 0) {
			StringBuilder invalidboreholestable = new StringBuilder();
			Collections.sort(invaliduris.getscannedboreholeCollection());
			invalidboreholestable
					.append("<h2>Datasets with Invalid URIs</h2><table class=\"boreholetable\"><tr><th>Dataset Name</th><th>Borehole URI</th><th>Status</th><th>DataSet ID</th></tr>");
			for (int i = 0; i < invaliduris.getscannedboreholeCollection().size(); i++) {
				ScannedBoreholeVo borehole = invaliduris.getscannedboreholeCollection().get(i);
				invalidboreholestable.append("<tr><td>");
				invalidboreholestable.append(borehole.getBoreholevo().getTitle());
				invalidboreholestable.append("</td><td><a href=\"");
				invalidboreholestable.append(borehole.getBoreholevo().getURI());
				invalidboreholestable.append("\">");
				invalidboreholestable.append(borehole.getBoreholevo().getURI());
				invalidboreholestable.append("</a></td><td>INVALID</td><td>");
				invalidboreholestable.append(borehole.getdSID());
				invalidboreholestable.append("</td></tr>");
			}
			invalidboreholestable.append("</table>");
			alltables.append(invalidboreholestable);
		}
		int duplicates = 0;
		if (validuris.getscannedboreholeCollection().size() > 0) {
			StringBuilder validboreholestable = new StringBuilder();
			String lastboreholeuri = "";
			Collections.sort(validuris.getscannedboreholeCollection());
			validboreholestable
					.append("<h2>Datasets with Valid URIs</h2><table class=\"boreholetable\"><tr><th>Dataset Name</th><th>Borehole URI</th><th>Status</th><th>Borehole Name</th><th>Lat</th><th>Long</th><th>DataSet ID</th></tr>");
			for (int i = 0; i < validuris.getscannedboreholeCollection().size(); i++) {
				ScannedBoreholeVo borehole = validuris.getscannedboreholeCollection().get(i);
				validboreholestable.append("<tr><td>");
				validboreholestable.append(borehole.getBoreholevo().getTitle());
				validboreholestable.append("</td>");
				int rowspan;
				for (rowspan = 1; (i + rowspan) < validuris.getscannedboreholeCollection().size()
						&& borehole
								.getBoreholevo()
								.getURI()
								.compareTo(
										validuris.getscannedboreholeCollection().get(i + rowspan).getBoreholevo()
												.getURI()) == 0; rowspan++)
					;
				if (borehole.getBoreholevo().getURI().compareTo(lastboreholeuri) != 0) {
					validboreholestable.append("<td rowspan=\"");
					validboreholestable.append(rowspan);
					validboreholestable.append("\"><a href=\"");
					validboreholestable.append(borehole.getBoreholevo().getURI());
					validboreholestable.append("\">");
					validboreholestable.append(borehole.getBoreholevo().getURI());
					validboreholestable.append("</a></td>");
					validboreholestable.append("<td rowspan=\"");
					validboreholestable.append(rowspan);
					validboreholestable.append("\">VALID</td><td rowspan=\"");
					validboreholestable.append(rowspan);
					validboreholestable.append("\">");
					validboreholestable.append(borehole.getName());
					validboreholestable.append("</td><td rowspan=\"");
					validboreholestable.append(rowspan);
					validboreholestable.append("\">");
					validboreholestable.append(borehole.getLat());
					validboreholestable.append("</td><td rowspan=\"");
					validboreholestable.append(rowspan);
					validboreholestable.append("\">");
					validboreholestable.append(borehole.getLon());
					validboreholestable.append("</td>");
					duplicates = duplicates + rowspan - 1;
				}
				validboreholestable.append("<td>");
				validboreholestable.append(borehole.getdSID());
				validboreholestable.append("</td></tr>");
				lastboreholeuri = borehole.getBoreholevo().getURI();
			}
			validboreholestable.append("</table>");
			alltables.append(validboreholestable);
		}
		StringBuilder summary = new StringBuilder();
		summary.append("<h2>Summary</h2><table class=\"summarytable\"><tr><td>Total Hylogger Datasets :</td><td>");
		summary.append(totaldatasets);
		summary.append("</td></tr>");
		summary.append("<tr><td>Valid Borehole URIs :</td><td>");
		summary.append(validuris.getscannedboreholeCollection().size());
		summary.append("  (");
		summary.append(validuris.getscannedboreholeCollection().size() - duplicates);
		summary.append(" unique)");
		summary.append("</td></tr>");
		summary.append("<tr><td>Invalid Borehole URIs :</td><td>");
		summary.append(invaliduris.getscannedboreholeCollection().size());
		summary.append("</td></tr>");
		summary.append("</table>");

		summary.append(alltables);

		return new ModelAndView("checkURIs", "checkresults", summary.toString());
	}

	/**
	 * Handling request when downloadtsg.html is called. The following tasks
	 * will be handled: 1) Validate the URL parameters, set default parameter
	 * value 2) Create new script file, script file name =
	 * emailyyyymmddhhmmssms.txt 3) Create new directory, name same as script
	 * file name 4) Create new message JMS request queue 5) Back to user request
	 * page with new message ID created and link to check status of request
	 * 
	 * @param request
	 *            input url parameters :
	 *            <ul>
	 *            <li>email (String) : User's email address, mandatory field.
	 *            Set to as JMS message header Correlation ID field which later
	 *            as key for checking user request status
	 *            <li>datasetid (String) : Dataset ID (mandatory for
	 *            requestType=TSG) All the rest of the parameters below are
	 *            optional for requestType=TSG
	 *            <li>match_string (String) : Its value is part or all of a
	 *            proper drillhole name. The first dataset found to match in the
	 *            database is downloaded
	 *            <li>linescan (String) : yes or no. If no then the main image
	 *            component is not downloaded. The default is yes.
	 *            <li>spectra (String) : yes or no. If no then the spectral
	 *            component is not downloaded. The default is yes.
	 *            <li>profilometer (String) : yes or no. If no then the
	 *            profilometer component is not downloaded. The default is yes.
	 *            <li>traypics (String) : yes or no. If no then the individual
	 *            tray pictures are not downloaded. The default is yes.
	 *            <li>mospic (String) : yes or no. If no then the hole mosaic
	 *            picture is not downloaded. The default is yes.
	 *            <li>mappics (String) : yes or no. If no then the map pictures
	 *            are not downloaded. The default is yes.
	 *            </ul>
	 * 
	 * @param response
	 *            response the request with a message containing new queue
	 *            message id created and link to check download request status
	 * @return out A message
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/downloadtsg.html")
	public ModelAndView downloadtsgHandler(@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "datasetid", required = false) String datasetid,
			@RequestParam(value = "linescan", required = false, defaultValue = "yes") String linescan,
			@RequestParam(value = "forcerecreate", required = false, defaultValue = "no") String deletecache,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		TsgParamVo tsgParamVo = new TsgParamVo();
		MessageVo tsgreqmessage = new MessageVo();
		// set TSG as requestType to ConfigVo
		tsgreqmessage.setRequestType("TSG");

		// url parameters validation start

		// mandatory field : validate if email null or empty or missing

		if (!Utility.ValidateEmail(email)) {
			String errMsg = "A valid email address must be provided for this service to function.";
			return new ModelAndView("downloadtsgusage", "errmsg", errMsg);
		}

		// set requestor email into ConfigVo
		tsgreqmessage.setRequestorEmail(email);

		// mandatory field : validate mandatory field datasetid

		if (!Utility.isAlphanumericOrHyphen(datasetid)) {
			String errMsg = "A valid Dataset id must be provided for this service to function.";
			return new ModelAndView("downloadtsgusage", "errmsg", errMsg);
		}

		// end validating mandatory parameter for TSG Download

		// start validating optional url parameters for TSG Download
		// validate linescan, if null or empty or missing, set default to yes

		if (!Utility.stringIsBlankorNull(linescan) && linescan.toLowerCase().equals("no")) {
			linescan = "no";
			tsgreqmessage.setRequestLS(false);
		} else {
			linescan = "yes";
			tsgreqmessage.setRequestLS(true);
		}

		logger.debug("linescan = " + linescan);

		// validate recreate datafile option. This option overrides the built in
		// caching and forces the service to recreate the datafile.

		if (!Utility.stringIsBlankorNull(deletecache) && deletecache.toLowerCase().equals("yes")) {
			deletecache = "yes";
		} else
			deletecache = "no";

		// Set url parameters to URLParamVo object
		tsgParamVo.setDatasetid(datasetid);

		tsgParamVo.setEmail(email);
		tsgParamVo.setLinescan(linescan);

		// Reading configuration information from config.properties file and
		// create
		// script file
		logger.debug("Start generating script file...");

		String scriptFileNameNoExt = nvclDownloadSvc.createScriptFile(tsgParamVo);

		if (deletecache.equals("yes")) {
			File cachedfile = new File(configVo.getDownloadRootPath() + scriptFileNameNoExt + ".zip");
			if (cachedfile.exists() && !cachedfile.delete()) {
				logger.error("Force recreate dataset was called by a client but the file exists and couldn't be deleted.  Probably in use.");
				Map<String, String> msgMap = new HashMap<String, String>();
				msgMap.put("status", "CacheClearFail");
				msgMap.put("adminEmail", configVo.getSysAdminEmail());
				return new ModelAndView("downloadtsg", "msgMap", msgMap);
			}

		}

		String adminEmail = configVo.getSysAdminEmail();
		if (scriptFileNameNoExt.equals("fail")) {
			String errMsg = "Error occured while creating script file, please report " + "this problem to : "
					+ adminEmail;
			logger.error(errMsg);
			return new ModelAndView("error_page", "errmsg", errMsg);
		}
		tsgreqmessage.setScriptFileNameNoExt(scriptFileNameNoExt);
		tsgreqmessage.settSGDatasetID(datasetid);
		logger.debug("Script file created successfully ...");

		logger.debug("Start create JMS message ...");
		nvclDownloadGateway.setDestination(tsgReqDestination);
		String messageID = nvclDownloadGateway.createTSGDownloadReqMsg(tsgreqmessage);

		if (messageID == null) {
			logger.error("Failed creating JMS message in queue ...");
			// return to request page
			Map<String, String> msgMap = new HashMap<String, String>();
			msgMap.put("status", "fail");
			msgMap.put("adminEmail", adminEmail);
			return new ModelAndView("downloadtsg", "msgMap", msgMap);
		}

		logger.debug("JMS message created successfully ... ");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
		}
		return new ModelAndView("redirect:checktsgstatus.html?email=" + email);

	}

	/**
	 * Handling request when downloadwfs.html is called. The following tasks
	 * will be handled: 1) Validate the URL parameters, set default parameter
	 * value 2) Create new message JMS request queue 5) Back to user request
	 * page with new message ID created and link to check status of request
	 * 
	 * @param request
	 *            input url parameters :
	 *            <ul>
	 *            <li>email (String) : User's email address, mandatory field.
	 *            Set to as JMS message header Correlation ID field which later
	 *            as key for checking user request status
	 *            <li>boreholeid (String) : Borehole identifier - use as zip
	 *            file name and filter feature id
	 *            <li>wfsurl (String) : WFS url
	 *            <li>typename (String) : Feature type name
	 *            </ul>
	 * 
	 * @param response
	 *            response the request with a message containing new queue
	 *            message id created and link to check download request status
	 * @return out A message
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/downloadwfs.html")
	public ModelAndView downloadwfsHandler(@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "boreholeid", required = false) String boreholeid,
			@RequestParam(value = "typename", required = false) String typeName,
			@RequestParam(value = "forcerecreate", required = false, defaultValue = "no") String deletecache,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		MessageVo wfsreqmessage = new MessageVo();
		// set WFS as requestType
		wfsreqmessage.setRequestType("WFS");

		// url parameters validation start

		// validate mandatory field : requestor's email

		if (!Utility.ValidateEmail(email)) {
			String errMsg = "A valid email address must be provided for this service to function.";
			return new ModelAndView("downloadwfsusage", "errmsg", errMsg);
		}
		// set requestor email into ConfigVo
		wfsreqmessage.setRequestorEmail(email);

		// validate mandatory field : Borehole Identifier

		if (Utility.stringIsBlankorNull(boreholeid)) {
			String errMsg = "A valid boreholeid must be provided for this service to function.";
			return new ModelAndView("downloadwfsusage", "errmsg", errMsg);
		}

		// set boreholeid to use as file name for the O&M download
		wfsreqmessage.setBoreholeid(boreholeid);

		// validate mandatory field : type name

		if (Utility.stringIsBlankorNull(typeName)) {
			typeName = "sa:SamplingFeatureCollection";
		}

		// validate recreate datafile option. This option overrides the built in
		// caching and forces the service to recreate the datafile.
		if (!Utility.stringIsBlankorNull(deletecache) && deletecache.toLowerCase().equals("yes")) {
			File cachedfile = new File(configVo.getDownloadRootPath() + boreholeid + ".zip");
			if (cachedfile.exists() && !cachedfile.delete()) {
				logger.error("Force recreate dataset was called by a client but the file exists and couldn't be deleted.  Probably in use.");
				Map<String, String> msgMap = new HashMap<String, String>();
				msgMap.put("status", "CacheClearFail");
				msgMap.put("adminEmail", configVo.getSysAdminEmail());
				return new ModelAndView("downloadwfs", "msgMap", msgMap);
			}
		}

		// Set url parameters to both configVo
		wfsreqmessage.setFeatureTypeName(typeName);

		logger.debug("Start create JMS message ...");
		nvclDownloadGateway.setDestination(wfsReqDestination);
		String messageID = nvclDownloadGateway.createWFSDownloadReqMsg(wfsreqmessage);
		String adminEmail = configVo.getSysAdminEmail();
		// String webappURL = configVo.getWebappURL();

		if (messageID == null) {
			logger.error("Failed creating JMS message in queue ...");
			// return to request page
			Map<String, String> msgMap = new HashMap<String, String>();
			msgMap.put("status", "fail");
			msgMap.put("adminEmail", adminEmail);
			return new ModelAndView("downloadwfs", "msgMap", msgMap);
		}

		logger.debug("JMS message created successfully ... ");
		return new ModelAndView("redirect:checkwfsstatus.html?email=" + email);

	}

	/**
	 * Handling request when checktsgstatus.html is called. The following tasks
	 * will be handled: 1) Validate the URL parameters 2) Retrieve message from
	 * JMS queue (both request and reply queue) to determine the status of the
	 * user's request: a) If the message is available in request queue, tsg
	 * dataset download still in progress b) If the message is not in request
	 * queue and in reply queue, tsg dataset download completed. Check link to
	 * download from the message body.
	 * 
	 * @param request
	 *            input url parameters :
	 *            <ul>
	 *            <li>email (String) : user's email address, mandatory field.
	 *            Use as key to retrieve messages from the request and reply
	 *            queue
	 *            </ul>
	 * 
	 * @param response
	 *            response the request with an message output which consists of
	 *            the status of the request(s) if there is any
	 * @return out A message
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/checktsgstatus.html")
	public ModelAndView checkTsgStatusHandler(@RequestParam(value = "email", required = false) String email,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// url parameter validation
		// mandatory field : validate if email null or empty or missing, if
		// true, redirect to
		// error_page.jsp with error message
		// String email = null;
		// email = request.getParameter("email");
		if (!Utility.ValidateEmail(email)) {
			String errMsg = "A valid email address must be provided for this service to function.";
			return new ModelAndView("checktsgusage", "errmsg", errMsg);
		}

		// Retrieve messages from request and reply JMS queue
		logger.debug("email : " + email);
		// logger.debug("webappURL : " + webappURL);
		logger.debug("jmsTemplate : " + jmsTemplate);
		logger.debug("tsgReqDestination : " + tsgReqDestination);
		logger.debug("tsgReplyDestination : " + tsgReplyDestination);

		Map<String, Object> msgMap = nvclDownloadSvc.browseMessage(email, jmsTemplate, tsgReqDestination,
				tsgReplyDestination);
		return new ModelAndView("checktsgstatus", "msgMap", msgMap);

	}

	/**
	 * Handling request when checkwfsstatus.html is called. The following tasks
	 * will be handled: 1) Validate the URL parameters 2) Retrieve message from
	 * JMS queue (both request and reply queue) to determine the status of the
	 * user's request: a) If the message is available in request queue, tsg
	 * dataset download still in progress b) If the message is not in request
	 * queue and in reply queue, tsg dataset download completed. Check link to
	 * download from the message body. follow dataset id name .....
	 * 
	 * @param request
	 *            input url parameters :
	 *            <ul>
	 *            <li>email (String) : user's email address, mandatory field.
	 *            Use as key to retrieve messages from the request and reply
	 *            queue
	 *            </ul>
	 * 
	 * @param response
	 *            response the request with an message output which consists of
	 *            the status of the request(s) if there is any
	 * @return out A message
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/checkwfsstatus.html")
	public ModelAndView checkWfsStatusHandler(@RequestParam(value = "email", required = false) String email,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// url parameter validation
		// mandatory field : validate if email null or empty or missing

		if (!Utility.ValidateEmail(email)) {
			String errMsg = "A valid email address must be provided for this service to function.";
			return new ModelAndView("checkwfsusage", "errmsg", errMsg);
		}

		// Retrieve messages from request and reply JMS queue
		logger.debug("email : " + email);
		logger.debug("jmsTemplate : " + jmsTemplate);
		logger.debug("wfsReqDestination : " + wfsReqDestination);
		logger.debug("wfsReplyDestination : " + wfsReplyDestination);

		Map<String, Object> msgMap = nvclDownloadSvc.browseMessage(email, jmsTemplate, wfsReqDestination,
				wfsReplyDestination);
		return new ModelAndView("checkwfsstatus", "msgMap", msgMap);

	}

	@RequestMapping("/imageCarousel.html")
	public ModelAndView imageCarousel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "logid") String imglogId,
			@RequestParam(required = false, value = "sampleno", defaultValue = "0") Integer SampleNo)
			throws ServletException, IOException, SQLException {
		// mandatory field : imglogid
		// ensure logid is not null, empty or missing
		if (!Utility.isAlphanumericOrHyphen(imglogId)) {
			String errMsg = "Image Logid is not valid.";
			return new ModelAndView("imageCarouselusage", "errmsg", errMsg);
		}

		if (SampleNo == null || SampleNo < 0)
			SampleNo = 0;

		// only the first 1000 samples will be added to the carousel unless a
		// sample number over 1000 is requested
		// then

		String domainlogid = nvclDataSvc.getImageDomainlogId(imglogId);

		DomainDataCollectionVo sampleNoList = nvclDataSvc.getDomainData(domainlogid);

		// .getSampleNo(imglogId,
		// (SampleNo/1000)*1000, 100000);

		if (SampleNo > sampleNoList.getDomainDataCollection().get(sampleNoList.getDomainDataCollection().size() - 1)
				.getSampleNo())
			SampleNo = 0;

		String srvroot = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();

		Map<String, Object> msgMap = new HashMap<String, Object>();
		ArrayList<ImageDataURLVo> imgslist = new ArrayList<ImageDataURLVo>();
		for (Iterator<DomainDataVo> it1 = sampleNoList.getDomainDataCollection().iterator(); it1.hasNext();) {
			DomainDataVo imageDataVo = it1.next();
			ImageDataURLVo imgurl = new ImageDataURLVo();
			imgurl.setURL(srvroot + "/Display_Tray_Thumb.html?logid=" + imglogId + "&sampleno="
					+ imageDataVo.getSampleNo());
			imgurl.setSampleNo(imageDataVo.getSampleNo());
			imgslist.add(imgurl);
		}
		msgMap.put("images", imgslist);
		msgMap.put("sampleno", SampleNo);
		return new ModelAndView("imageCarousel", "msgMap", msgMap);

	}

	/**
	 * calls the dao services in NVCLdatatSvc class to retrieve spectral data
	 * from SPECTRALLOGSDATA table
	 *
	 * @param request
	 *            speclogid (String) : log identifier of the spectral log
	 * @param request
	 *            startsamplenumber (int) : start sample number of the spectra
	 *            required
	 * @param request
	 *            endsamplenumber (int) : start sample number of the spectra
	 *            required
	 * @param response
	 *            respond with the spectral data as binary arrays
	 * @return out chart in JPEG format
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	@RequestMapping("/getspectraldata.html")
	public ModelAndView getspectraldata(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "speclogid") List<String> speclogId,
			@RequestParam(required = false, value = "startsampleno", defaultValue = "0") Integer startsampleNo,
			@RequestParam(required = false, value = "endsampleno", defaultValue = "999999") Integer endsampleNo,
			@RequestParam(required = false, value = "outputformat", defaultValue = "binary") String outputformat)
			throws ServletException, IOException, SQLException {


		for (Iterator<String> it = speclogId.iterator(); it.hasNext();) {
			String speclogid = it.next();
			if (!Utility.isAlphanumericOrHyphen(speclogid)) {
				String errMsg = "Spectral logid is not valid.";
				return new ModelAndView("getspectraldatausage", "errmsg", errMsg);
			}
		}
		// validate if sampleno is null or empty or missing, if true, redirect
		if (startsampleNo == null || startsampleNo < 0) {
			String errMsg = "startsampleno must be a non-negative integer.";
			return new ModelAndView("getspectraldatausage", "errmsg", errMsg);
		}

		// validate if sampleno is null or empty or missing, if true, redirect
		if (endsampleNo == null || endsampleNo < 0) {
			String errMsg = "endsampleno must be a non-negative integer.";
			return new ModelAndView("getspectraldatausage", "errmsg", errMsg);
		}

		SpectralDataCollectionVo spectraldata = new SpectralDataCollectionVo(); 
		for (Iterator<String> it = speclogId.iterator(); it.hasNext();) {
			String speclogid = it.next();
			spectraldata.mergenewSpectraldata(nvclDataSvc.getSpectralData(speclogid, startsampleNo, endsampleNo).getSpectralDataCollection());
		}
		logger.debug("processing spectral data...");

		response.setHeader("Access-Control-Allow-Origin", "*");

		int totalbytes = 0;
		if (outputformat.equals("json")){
			response.setContentType("application/json");
			new ObjectMapper().writeValue(response.getOutputStream(),spectraldata.getSpectralDataCollection());
		}
		else {
			for (Iterator<SpectralDataVo> it1 = spectraldata.getSpectralDataCollection().iterator(); it1.hasNext();) {
				SpectralDataVo spectralDataVo = it1.next();
				response.getOutputStream().write(spectralDataVo.getSpectraldata());
				totalbytes += spectralDataVo.getSpectraldata().length;
			}
			response.setContentLength(totalbytes);
			response.setContentType("APPLICATION/OCTET-STREAM");
		}

		return null;
	}

	/**
	 * gets the list of spectral logs available for the dataset identified with
	 * dataset_id from the published logs table
	 *
	 * @param request
	 *            speclogid (String) : log identifier of the spectral log
	 * @param request
	 *            startsamplenumber (int) : start sample number of the spectra
	 *            required
	 * @param request
	 *            endsamplenumber (int) : start sample number of the spectra
	 *            required
	 * @param response
	 *            respond with the spectral data as binary arrays
	 * @return out chart in JPEG format
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	@RequestMapping("/getspectrallogs.html")
	public ModelAndView getspectrallogs(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "datasetid") String datasetId,
			@RequestParam(required = false, value = "outputformat") String outputformat) throws ServletException,
			IOException, SQLException {

		// mandatory field : datasetid
		// ensure datasetid is not null, empty or missing
		if (!Utility.isAlphanumericOrHyphen(datasetId)) {
			String errMsg = "datasetid is not valid.";
			return new ModelAndView("getspactrallogsUsage", "errmsg", errMsg);
		}
		if (!Utility.isAlphanumericOrHyphen(outputformat)) {
			outputformat = "xml";
		}

		logger.info("datasetID : " + datasetId);

		// process list of logs id and name
		logger.debug("start processing...");

		SpectralLogCollectionVo logList = nvclDataSvc.getSpectralLogCollection(datasetId);
		if (outputformat.equals("json")) {
			response.setContentType("application/json; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			new ObjectMapper().writeValue(response.getOutputStream(),logList );
		} else {
			response.setContentType("text/xml");
			this.marshaller.marshal(logList, new StreamResult(response.getOutputStream()));
		}
		return null;

	}

	@RequestMapping("/gettraymap.html")
	public ModelAndView getTrayMap(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "logid") String logid,
			@RequestParam(required = false, value = "trayindex") Integer trayindex) throws ServletException,
			IOException, SQLException {

		if (!Utility.isAlphanumericOrHyphen(logid)) {
			String errMsg = "logid is not valid.";
			return new ModelAndView("gettraymapUsage", "errmsg", errMsg);
		}

		if (trayindex == null || trayindex < 0) {
			String errMsg = "trayindex must be a non-negative integer.";
			return new ModelAndView("gettraymapusage", "errmsg", errMsg);
		}

		LogDetailsVo logdetails = nvclDataSvc.getLogDetails(logid);

		TraySectionsVo sections = nvclDataSvc.getTraySections(logdetails.getDatasetID(), trayindex);
		int sectioncount = sections.getSections().size();
		if (sectioncount <= 0) {
			String errMsg = "trayindex out of range or doesnt contain any sections.";
			return new ModelAndView("gettraymapusage", "errmsg", errMsg);
		}

		int startsampleno = sections.getSections().get(0).getStartsampleno();
		int endsampleno = sections.getSections().get(sectioncount - 1).getEndsampleno();

		int imgmaxwidth = 0;
		int imgheight = sectioncount;

		for (int i = 0; i < sectioncount; i++) {
			imgmaxwidth = max(sections.getSections().get(i).getEndsampleno()
					- sections.getSections().get(i).getStartsampleno() + 1, imgmaxwidth);
		}

		BufferedImage bm = new BufferedImage(imgmaxwidth, imgheight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = bm.createGraphics();

		graphics.setPaint(new Color(136, 136, 136));
		graphics.fillRect(0, 0, bm.getWidth(), bm.getHeight());

		if (logdetails.getLogType() == 1) {
			List<ClassDataVo> classdata = nvclDataSvc.getClassLogData(logid, startsampleno, endsampleno);

			int previoussectionlengthsum = 0;
			for (int i = 0; i < sectioncount; i++) {
				int sectionlength = sections.getSections().get(i).getEndsampleno()
						- sections.getSections().get(i).getStartsampleno() + 1;
				for (int j = 0; j < sectionlength; j++) {
					int bgr = classdata.get(previoussectionlengthsum + j).getColour();
					int red = (bgr >> 16) & 0xFF;
					int green = (bgr >> 8) & 0xFF;
					int blue = (bgr >> 0) & 0xFF;
					int out = (blue << 16) | (green << 8) | (red << 0);
					bm.setRGB(j, i, out);
				}
				previoussectionlengthsum += sectionlength;
			}
		} else if (logdetails.getLogType() == 2) {
			List<FloatDataVo> floatdata = nvclDataSvc.getFloatLogData(logid, startsampleno, endsampleno);

			float minvalue = Float.MAX_VALUE, maxvalue = 0;

			LogExtentsVo logextents = nvclDataSvc.getLogExtents(logid);

			minvalue = logextents.getMinvalue();
			maxvalue = logextents.getMaxvalue();

			int previoussectionlengthsum = 0;
			for (int i = 0; i < sectioncount; i++) {
				int sectionlength = sections.getSections().get(i).getEndsampleno()
						- sections.getSections().get(i).getStartsampleno() + 1;
				for (int j = 0; j < sectionlength; j++) {
					if (floatdata.get(previoussectionlengthsum + j).getvalue() == null)
						continue;
					int palletindex = (int) ((floatdata.get(previoussectionlengthsum + j).getvalue() - minvalue)
							/ (maxvalue - minvalue) * 255);
					if (palletindex < 0 || palletindex > 255)
						continue;
					bm.setRGB(j, i, Utility.pallet[palletindex]);
				}
				previoussectionlengthsum += sectionlength;
			}
		}

		else if (logdetails.getLogType() == 6) {
			List<MaskDataVo> maskdata = nvclDataSvc.getMaskLogData(logid, startsampleno, endsampleno);

			int previoussectionlengthsum = 0;
			for (int i = 0; i < sectioncount; i++) {
				int sectionlength = sections.getSections().get(i).getEndsampleno()
						- sections.getSections().get(i).getStartsampleno() + 1;
				for (int j = 0; j < sectionlength; j++) {
					if (maskdata.get(previoussectionlengthsum + j).getvalue() == null)
						continue;
					if (maskdata.get(previoussectionlengthsum + j).getvalue() == true)
						bm.setRGB(j, i, 65280);
					else
						bm.setRGB(j, i, 16711680);
				}
				previoussectionlengthsum += sectionlength;
			}
		}

		response.setContentType("image/bmp");

		ImageIO.write(bm, "bmp", response.getOutputStream());

		return null;
	}

	@RequestMapping("/gettraymaphtml.html")
	public ModelAndView getTrayMapHtml(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "logid") String logid,
			@RequestParam(required = false, value = "trayindex") Integer trayindex,
			@RequestParam(required = false, value = "width") Integer width,
			@RequestParam(required = false, value = "height") Integer height) throws ServletException, IOException,
			SQLException {

		if (!Utility.isAlphanumericOrHyphen(logid)) {
			String errMsg = "logid is not valid.";
			return new ModelAndView("gettraymaphtmlUsage", "errmsg", errMsg);
		}

		if (trayindex == null || trayindex < 0) {
			String errMsg = "trayindex must be a non-negative integer.";
			return new ModelAndView("gettraymaphtmlusage", "errmsg", errMsg);
		}

		if (width == null || width < 0) {
			String errMsg = "width must be a non-negative integer.";
			return new ModelAndView("gettraymaphtmlusage", "errmsg", errMsg);
		}

		if (height == null || height < 0) {
			String errMsg = "height must be a non-negative integer.";
			return new ModelAndView("gettraymaphtmlusage", "errmsg", errMsg);
		}

		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put("logid", logid);
		msgMap.put("trayindex", trayindex.toString());
		msgMap.put("width", width.toString());
		msgMap.put("height", height.toString());
		return new ModelAndView("gettraymaphtml", "msgMap", msgMap);
	}

	@RequestMapping("/getDownsampledData.html")
	public ModelAndView getDownsampledData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "logid") String logId,
			@RequestParam(required = false, value = "interval", defaultValue = "1.0") Float interval,
			@RequestParam(required = false, value = "startdepth", defaultValue = "0") Float startDepth,
			@RequestParam(required = false, value = "enddepth", defaultValue = "999999") Float endDepth,
			@RequestParam(required = false, value = "outputformat", defaultValue = "csv") String outputformat)
			throws ServletException, IOException, Exception {
		if (!Utility.isAlphanumericOrHyphen(logId)) {
			String errMsg = "Logid is not valid.";
			return new ModelAndView("getdownsampleddatausage", "errmsg", errMsg);
		}

		if (interval == null || interval < 0) {
			String errMsg = "interval must be a non-negative decimal number.";
			return new ModelAndView("getdownsampleddatausage", "errmsg", errMsg);
		}

		if (startDepth == null || startDepth < 0) {
			String errMsg = "startDepth must be a non-negative decimal number.";
			return new ModelAndView("getdownsampleddatausage", "errmsg", errMsg);
		}

		if (endDepth == null || endDepth < 0) {
			String errMsg = "endDepth must be a non-negative decimal number.";
			return new ModelAndView("getdownsampleddatausage", "errmsg", errMsg);
		}
		LogDetailsVo logDetail = nvclDataSvc.getLogDetails(logId);

		if (logDetail.getLogType() != 2 && logDetail.getLogType() != 1) {
			String errMsg = "only class and decimal type logs can be requested through this service.";
			return new ModelAndView("getdownsampleddatausage", "errmsg", errMsg);
		}

		switch (logDetail.getLogType()) {
		case 1:
			ArrayList<BinnedClassDataVo> ds = nvclDataSvc.getdownSampledClassData(logId, startDepth, endDepth,
					interval, 0.0F);
			if (outputformat.equals("csv")) {
				response.setContentType("text/csv");
				response.setHeader("Content-Disposition", "attachment; filename=\"scalar.csv\"");

				BufferedWriter out = new BufferedWriter(response.getWriter());
				CSVWriter writer = new CSVWriter(out, ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER,
						"\n");
				writer.writeNext(new String[] { "Depth", "Classification", "Colour", "Count" });
				for (Iterator<BinnedClassDataVo> it1 = ds.iterator(); it1.hasNext();) {
					BinnedClassDataVo item = it1.next();
					String[] itemstring = new String[] { Float.toString(item.getRoundedDepth()), item.getClassText(),
							Integer.toString(item.getColour()), Integer.toString(item.getClassCount()) };
					writer.writeNext(itemstring);
				}
				writer.close();
			} 
			else
			{
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				new ObjectMapper().writeValue(response.getOutputStream(),ds);
			}
			break;

		case 2:

			ArrayList<AveragedFloatDataVo> ds2 = nvclDataSvc.getdownSampledFloatDataDao(logId, startDepth, endDepth,
					interval);
			if (outputformat.equals("csv")) {
				response.setContentType("text/csv");
				response.setHeader("Content-Disposition", "attachment; filename=\"scalar.csv\"");

				BufferedWriter out = new BufferedWriter(response.getWriter());
				CSVWriter writer = new CSVWriter(out, ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER,
						"\n");
				writer.writeNext(new String[] { "Depth", "Value" });
				for (Iterator<AveragedFloatDataVo> it1 = ds2.iterator(); it1.hasNext();) {
					AveragedFloatDataVo item = it1.next();
					String[] itemstring = new String[] { Float.toString(item.getRoundedDepth()),
							Float.toString(item.getAverageValue()) };
					writer.writeNext(itemstring);
				}
				writer.close();
			} else {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				new ObjectMapper().writeValue(response.getOutputStream(),ds2);
			}
			break;
		}

		return null;
	}

	@RequestMapping("/trayImageSampleLocate.html")
	public ModelAndView trayImageSampleLocate(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "logid") String imglogId,
			@RequestParam(required = false, value = "pixelx") Integer pixelx,
			@RequestParam(required = false, value = "pixely") Integer pixely,
			@RequestParam(required = false, value = "imgwidth") Integer imgwidth,
			@RequestParam(required = false, value = "imgheight") Integer imgheight,
			@RequestParam(required = false, value = "sampleno", defaultValue = "0") Integer SampleNo)
			throws ServletException, IOException, SQLException, ImageProcessingException {
		if (!Utility.isAlphanumericOrHyphen(imglogId)) {
			String errMsg = "Image Logid is not valid.";
			return new ModelAndView("trayImageSampleLocateusage", "errmsg", errMsg);
		}

		if (SampleNo == null || SampleNo < 0)
			SampleNo = 0;
		if (pixelx == null || pixelx < 0) {
			String errMsg = "Pixel X value is not valid.";
			return new ModelAndView("trayImageSampleLocateusage", "errmsg", errMsg);
		}

		if (pixely == null || pixely < 0) {
			String errMsg = "Pixel Y value is not valid.";
			return new ModelAndView("trayImageSampleLocateusage", "errmsg", errMsg);
		}
		if (imgwidth == null || imgwidth < 0)
			imgwidth = 0;
		if (imgheight == null || imgheight < 0)
			imgheight = 0;

		Integer sampleno = nvclDataSvc.getSampleNumberfromTrayPictureXY(imglogId, SampleNo, pixelx, pixely, imgwidth,
				imgheight);
		if (sampleno != null) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode jo = mapper.createObjectNode();
			jo.put("sampleno", sampleno);
			mapper.writeValue(response.getOutputStream(),jo);
		}
		return null;
	}

	@RequestMapping("/plotspectra.html")
	public ModelAndView plotspectra(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "speclogid")  List<String> logId,
			@RequestParam(required = false, value = "startsampleno", defaultValue = "0") Integer startSampleNo,
			@RequestParam(required = false, value = "endsampleno", defaultValue = "0") Integer endSampleNo,
			@RequestParam(required = false, value = "height", defaultValue = "800") Integer height,
			@RequestParam(required = false, value = "width", defaultValue = "1460") Integer width)
			throws ServletException, IOException, SQLException, ImageProcessingException {

		for (Iterator<String> it = logId.iterator(); it.hasNext();) {
			String speclogid = it.next();
			if (!Utility.isAlphanumericOrHyphen(speclogid)) {
				String errMsg = "Spectral logid is not valid.";
				return new ModelAndView("plotscalarusage", "errmsg", errMsg);
			}
		}
		if (startSampleNo == null || startSampleNo < 0)
			startSampleNo = 0;

		String logidstring;
		
		logidstring ="['"+logId.get(0)+"'";
		if (logId.size()>1) logidstring+=",'"+logId.get(1)+"'";
		logidstring+="]";
		
		Map<String, Object> msgMap = new HashMap<String, Object>();
		msgMap.put("startSampleNo", startSampleNo);
		msgMap.put("endSampleNo", endSampleNo);
		msgMap.put("logId",logidstring);
		msgMap.put("height",height);
		msgMap.put("width",width);
		
		return new ModelAndView("plotspectra", "msgMap", msgMap);
	}

	@RequestMapping("/getDatasetID.html")
	public ModelAndView getDatasetID(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "logid") String logId) throws ServletException, IOException,
			SQLException, ImageProcessingException {
		if (!Utility.isAlphanumericOrHyphen(logId)) {
			String errMsg = "Logid is not valid.";
			return new ModelAndView("getDatasetIDusage", "errmsg", errMsg);
		}

		String datasetid = nvclDataSvc.getLogDetails(logId).getDatasetID();
		if (datasetid != null) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode jo = mapper.createObjectNode();
			jo.put("datasetid", datasetid);
			mapper.writeValue(response.getOutputStream(),jo);
		}
		return null;
	}

	@RequestMapping("/getSpectralLogSamplingPoints.html")
	public ModelAndView getSpectralLogSamplingPoints(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "speclogid") List<String> speclogIds) throws ServletException,
			IOException, SQLException, ImageProcessingException {
		if (speclogIds== null ){
			String errMsg = "Spectral logid is not valid.";
			return new ModelAndView("getSpectralLogSamplingPointsusage", "errmsg", errMsg);
		}
		for (Iterator<String> it = speclogIds.iterator(); it.hasNext();) {
			String speclogid = it.next();
			if (!Utility.isAlphanumericOrHyphen(speclogid)) {
				String errMsg = "Spectral logid is not valid.";
				return new ModelAndView("getSpectralLogSamplingPointsusage", "errmsg", errMsg);
			}
		}
		String wavelengths = new String();
		for (Iterator<String> it = speclogIds.iterator(); it.hasNext();) {
			String speclogid = it.next();
			SpectralLogCollectionVo speclogdetails = nvclDataSvc.getSpectralLogCollection(nvclDataSvc.getLogDetails(speclogid).getDatasetID());
			
			for (Iterator<SpectralLogVo> it1 = speclogdetails.getSpectralLogCollection().iterator(); it1.hasNext();) {
				SpectralLogVo speclog = it1.next();
				if (speclog.getLogID().equals(speclogid)) {
					wavelengths+=wavelengths.length()>0?","+speclog.getWavelengthsasString():speclog.getWavelengthsasString();
				}
			}
		}
		if(wavelengths.length()>0){
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode jo = mapper.createObjectNode();
			jo.put("wavelengths",wavelengths );
			mapper.writeValue(response.getOutputStream(),jo);
		}

		return null;
	}

	@RequestMapping("/getSampleNumberFromDepth.html")
	public ModelAndView getSampleNumberFromDepth(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "datasetid") String datasetid,
			@RequestParam(required = false, value = "depth") Float depth) throws ServletException, IOException,
			SQLException, ImageProcessingException {
		if (!Utility.isAlphanumericOrHyphen(datasetid)) {
			String errMsg = "Datasetid is not valid.";
			return new ModelAndView("getSampleNumberFromDepthusage", "errmsg", errMsg);
		}

		if (depth == null || depth < 0) {
			String errMsg = "depth is not valid.";
			return new ModelAndView("getSampleNumberFromDepthusage", "errmsg", errMsg);
		}
		Integer sampleno = nvclDataSvc.getSampleNumberFromDepth(datasetid, depth);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jo = mapper.createObjectNode();
		jo.put("sampleno", sampleno);
		mapper.writeValue(response.getOutputStream(),jo);

		return null;
	}

	@RequestMapping("/getAlgorithms.html")
	public ModelAndView getAlgorithms(
			@RequestParam(required = false, value = "outputformat", defaultValue = "xml") String outputformat,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		AlgorithmCollectionVo algcol = nvclDataSvc.getAlgorithmsCollection();
		if (outputformat.equals("json")) {
			response.setContentType("application/json");
			new ObjectMapper().writeValue(response.getOutputStream(),algcol);
			
		} else {
			response.setContentType("text/xml");
			this.marshaller.marshal(algcol, new StreamResult(response.getOutputStream()));
		}
		return null;
	}
	
	@RequestMapping("/getClassifications.html")
	public ModelAndView getClassifications(
			@RequestParam(required = false, value = "outputformat", defaultValue = "xml") String outputformat,
			@RequestParam(required = false, value = "logid") String logid,
			@RequestParam(required = false, value = "algorithmoutputid") Integer algoutid,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		if ((algoutid == null || algoutid < 0) && !Utility.isAlphanumericOrHyphen(logid) ) {
			String errMsg = "algoutid or logid required";
			return new ModelAndView("getClassificationsusage", "errmsg", errMsg);
		}
		ClassificationsCollectionVo classcol=null;
		if (algoutid != null && algoutid > 0) classcol = nvclDataSvc.getClassifications(algoutid);
		else classcol = nvclDataSvc.getClassifications(logid);

		if (classcol!=null){
			if (outputformat.equals("json")) {
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(),classcol);
			} else {
				response.setContentType("text/xml");
				this.marshaller.marshal(classcol, new StreamResult(response.getOutputStream()));
			}
		}
		return null;
	}
}
