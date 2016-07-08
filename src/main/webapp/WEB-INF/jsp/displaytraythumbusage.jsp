<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services :: Display Tray Images Service</title>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Display Tray Images Service</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>Given a valid image log identifier and sample number, this service pulls the corresponding image from the database and returns it as a JPEG.</p>
  <p>Usage: Display_Tray_Thumb.html?parameter1=value&amp;parameter2=value</p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>logid</td><td>obtained through calling the getLogCollection service, with mosaicsvc URL parameter set to yes.</td><td>yes</td><td>63d31981-bd0c-44dd-a118-6a3406c5d68</td></tr>
  <tr><td>sampleno</td><td>sample number of the image to retrieve from database (0 based index)</td><td>yes</td><td>0</td></tr>
  </table>
  <h3>Example Queries:</h3>
  <p>A stadard query for retrieve mosaic image from database: Display_Tray_Thumb.html?logid=63d31981-bd0c-44dd-a118-6a3406c5d68&amp;sampleno=0</p>
  <p>A stadard query for retrieve a single tray thumbnail image from database: Display_Tray_Thumb.html?logid=e8218563-e5af-48ac-a643-3f639a11c3f&amp;sampleno=4</p>
  </body>
</html>