<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services</title>
        <link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  	<h2 class="NVCLDSh2">NVCL Data Services</h2>
  	<p>The NVCL Data Services provide a bundle of basic interfaces for requesting data and images from National Virtual Core Library datasets.  The output of these services is intended to drive web visualisations, image/data displays and downloads for use in data access portals</p>
  	<p>The interfaces provided are listed bellow.</p>
	<table class="usageTable"><tr><th>Name</th><th>Description</th><th>url</th></tr>
	<tr><td>Get Dataset Collection</td><td>Generate a list of dataset collection for a specified borehole in xml format.</td><td><a href="getDatasetCollection.html">getDatasetCollection.html</a></td></tr>
	<tr><td>Get Log Collection</td><td>Generate a list of logs collection (mosaic or scalar logs) in xml format.</td><td><a href="getLogCollection.html">getLogCollection.html</a></td></tr>
	<tr><td>Mosaic</td><td>Returns multiple images in a set in an a configurable arrangement.</td><td><a href="mosaic.html">mosaic.html</a></td></tr>
	<tr><td>Display Tray Images</td><td>Used by Mosaic and Mosaic Tray Thumbnail services to get the actual image from database and output as PNG format image..</td><td><a href="Display_Tray_Thumb.html">Display_Tray_Thumb.html</a></td></tr>
    <tr><td>Image Carousel</td><td>Display Images in an image carousel</td><td><a href="imageCarousel.html">imageCarousel.html</a></td></tr>
	<tr><td>Get Image Tray Depth</td><td>Generate a list of image tray collection with start and end depth values for each image tray in xml format.</td><td><a href="getImageTrayDepth.html">getImageTrayDepth.html</a></td></tr>
	<tr><td>Plot Scalar</td><td>Uses JFeeChart Java chart library to draw a plot of the product and return the plot as an image in PNG format.</td><td><a href="plotscalar.html">plotscalar.html</a></td></tr>
    <tr><td>Plot Multiple Scalars</td><td>Enable request to plot up to maximum 6 scalars at one time.</td><td><a href="plotmultiscalars.html">plotmultiscalars.html</a></td></tr>
    <tr><td>Download Scalars</td><td>Enables download of multiple raw scalar values in csv or json format.</td><td><a href="downloadscalars.html">downloadscalars.html</a></td></tr>
    <tr><td>Download a Down-Sampled Scalar</td><td>Enables the download of a single raw scalar in csv or json format.</td><td><a href="getDownsampledData.html">getDownsampledData.html</a></td></tr>    
    <tr><td>Tray scalar data map</td><td>This service will return the raw image data of a tray scalar data map</td><td><a href="gettraymap.html">gettraymap.html</a></td></tr>
    <tr><td>Tray scalar data map HTML tag</td><td>This service will return an HTML tag that wraps the above raw image tray map and gives it a fixed width and height</td><td><a href="gettraymaphtml.html">gettraymaphtml.html</a></td></tr>
    <tr><td>Get Spectral Logs</td><td>This service will get a list of spectral logs in xml format</td><td><a href="getspectrallogs.html">getspectrallogs.html</a></td></tr>
    <tr><td>Get Spectral Data</td><td>This service get spectral data in a binary chunk.</td><td><a href="getspectraldata.html">getspectraldata.html</a></td></tr>
    
    <tr><td>TSG file download</td><td>Prepares TSG files from NVCL database datasets</td><td><a href="downloadtsg.html">downloadtsg.html</a></td></tr>
	<tr><td>WFS file download</td><td>Prepares xml datsets from NVCL geoserver instances</td><td><a href="downloadwfs.html">downloadwfs.html</a></td></tr>
	<tr><td>TSG file Download Requests Status Page</td><td>Displays the status of past TSG file download requests for users</td><td><a href="checktsgstatus.html">checktsgstatus.html</a></td></tr>
	<tr><td>WFS file Download Requests Status Page</td><td>Displays the status of past WFS file download requests for users.</td><td><a href="checkwfsstatus.html">checkwfsstatus.html</a></td></tr>
    
    </table>
	<p>Click on the urls for more details, including usage instructions of each interface.</p>
	<p>Click <a href="https://twiki.auscope.org/wiki/CoreLibrary/WebServicesDevelopment">here</a> for more detail information about NVCL Web Services Development.</p>
	<p>Version 2.0.2</p>


  </body>
</html>

