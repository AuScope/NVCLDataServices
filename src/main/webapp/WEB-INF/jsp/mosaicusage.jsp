<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services :: Mosaic Service</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Mosaic Service</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>Mosaic service will return either mosaic / tray thumbnail / tray / imagery images stored within the database. It requires either a logid or a dataset id as the mandatory parameter. The logid can be obtained through the <a href="getLogCollection.html">getLogCollection</a> service with mosaicsvc set to yes.  Tray thumbnail images will automatically add links to full size tray images</p>
  <p>There are four types of logs available:</p>
      <ol>
      <li>Mosaic : logs of this type will return a single mosaic image with all the tray thumbnail images grouped as a single image</li>
      <li>Tray Thumbnail Images : logs of this type will return one or more tray thumbnail image(s) depending on the startsampleno and endsampleno specified</li>
      <li>Tray Images : logs of this type will return one or more full size tray image(s) depending on the startsampleno and endsampleno specified</li>
      <li>Imagery : logs of this type will return one or more full resolution image(s) depending on the startsampleno and endsampleno specified</li>
    </ol>
  <p>Usage: mosaic.html?parameter1=value&amp;parameter2=value&amp;...</p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>datasetid</td><td>obtained through calling the getDatasetCollection service </td><td>yes, or a logid</td><td>6dd70215-fe38-457c-be42-3b165fd98c7 </td></tr>
  <tr><td>logid</td><td>obtained through calling the getLogCollection service with URL parameter mosaicsvc=yes</td><td>yes, or a datasetid</td><td>e8218563-e5af-48ac-a643-3f639a11c3f</td></tr>
  <tr><td>width</td><td>number of column the images are to be displayed</td><td>no, Default value=3</td><td>3</td></tr>
  <tr><td>startsampleno</td><td>the first sample image to be displayed</td><td>no, Default value=0</td><td>0</td></tr>
  <tr><td>endsampleno</td><td>the last sample image to be displayed</td><td>no, Default value=99999</td><td>10</td></tr>
  <tr><td>scalarids</td><td>data scalar log ids to plot as tray maps to the right of the images (only works for tray type scalars)</td><td>no</td><td>a4760237-da5e-4fcc-81ff-0c330f36e8d</td></tr>
  <tr><td>showdepths</td><td>Show depth ranges alongside thumbnail images</td><td>no, Default value=false</td><td>true</td></tr>
  </table>
  <h3>Example Queries:</h3>
  <p>A stadard query for Mosaic: mosaic.html?logid=63d31981-bd0c-44dd-a118-6a3406c5d68</p>
  <p>A query for 4 Tray Thumbnail Images display in 2 columns: mosaic.html?logid=e8218563-e5af-48ac-a643-3f639a11c3f&amp;width=2&amp;startsampleno=0&amp;endsampleno=3</p>
  <p>A query for the first Tray Images: mosaic.html?logid=05a35054-da3e-46ac-9a57-81b6afdf0c9&amp;startsampleno=0&amp;endsampleno=0</p>
  <p>A query for the first 10 Imagery Images: mosaic.html?logid=fae8f90d-2015-4200-908a-b30da787f01&amp;startsampleno=0&amp;endsampleno=10</p>

  </body>
</html>