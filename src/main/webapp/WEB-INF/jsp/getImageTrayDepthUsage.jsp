<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services :: Get Image Tray Service</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Get Image Tray Service</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>getImageTrayDepth service is one of the new service added in version 2.0.0 of NVCL Data Services. It will generate a list of image tray collection with start and end depth values for each image tray. It requires a logid as the mandatory URL parameter. The logid can be obtained through the <a href="getLogCollection.html">getLogCollection</a> service with mosaicsvc set to yes.</p>

  <p>getImageTrayDepth.html?parameter1=value</p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>logid</td><td>obtained through calling the getLogCollection service with mosaicsvc set to yes, select the LogId with LogName equal Tray Thumbnail Images or Tray Images</td><td>yes</td><td>05a35054-da3e-46ac-9a57-81b6afdf0c9</td></tr>
  </table>
  <h3>Example Queries:</h3>
  <p>A standard query for ImageTrayDepth : getImageTrayDepth.html?logid=05a35054-da3e-46ac-9a57-81b6afdf0c9</p>

  </body>
</html>