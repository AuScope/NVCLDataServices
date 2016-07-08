<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services : Image Carousel Service</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Image Carousel Service</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>The Image Carousel service will return images stored within the database in a carousel. It requires a logid as the mandatory URL parameter. The logid can be obtained through the <a href="getLogCollection.html">getLogCollection</a> service with mosaicsvc set to yes.</p>
  <p>There are four types of logid available for request:</p>
      <ol>
      <li>Mosaic : logid of this type will return a single mosaic image with all the tray thumbnail images grouped as a single image
      <li>Tray Thumbnail Images : logid of this type will return one or more tray thumbnail image(s) depend on the startsampleno and endsampleno specified
      <li>Tray Images : logid of this type will return one or more tray image(s) depending on the startsampleno and endsampleno specified
      <li>Imagery : logid of this type will return one or more big resolution image(s) depend on the startsampleno and endsampleno specified
    </ol>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>logid</td><td>obtained through calling the getLogCollection service with URL parameter mosaicsvc=yes</td><td>yes</td><td>63d31981-bd0c-44dd-a118-6a3406c5d68</td></tr>
  <tr><td>sampleno</td><td>the sample number of the image which will be the starting point for you carousel</td><td>no, Default value=0</td><td>0</td></tr>
  </table>
  <h3>Example Queries:</h3>
  <p>A standard query for Mosaic: imageCarousel.html?logid=63d31981-bd0c-44dd-a118-6a3406c5d68</p>
  <p>A query for Tray Thumbnail Images: imageCarousel.html?logid=e8218563-e5af-48ac-a643-3f639a11c3f</p>
  <p>A query for Tray Images starting from sample number 2: imageCarousel.html?logid=05a35054-da3e-46ac-9a57-81b6afdf0c9&amp;sampleno=2</p>
  <p>A query for Linescan Imagery Images: imageCarousel.html?logid=fae8f90d-2015-4200-908a-b30da787f01</p>

  </body>
</html>