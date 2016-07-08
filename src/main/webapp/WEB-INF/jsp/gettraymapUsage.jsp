<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services :: Get Tray Map</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Get Tray Map</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>This service will return the raw image data of a tray scalar data map. It requires a logid id and a 0 based tray index. 
  The logid can be obtained through the <a href="getLogCollection.html">getLogCollection</a> service.</p>
  <p>A tray scalar data map shows the values of a class, decimal or mask type scalar as pixels arranged into the core trays dimensions.  
  This allows it to be displayed next to a tray thumbnail image to conveniently show the scalar's data trends within a tray.  
  The image will need to be scaled up to an appropriate size (e.g. the same size as the tray thumbnail).  
  To see how you would use this service to generate a display call the <a href="mosaic.html">mosiac</a> service with a valid scalarid.</p>
  <p>Usage: gettraymaphtml.html?parameter1=value&amp;parameter2=value&amp;...</p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>logid</td><td>obtained through calling the getLogCollection service</td><td>yes</td><td>a4760237-da5e-4fcc-81ff-0c330f36e8d</td></tr>
  <tr><td>trayindex</td><td>0 based tray index to select the tray you're interested in</td><td>yes</td><td>0</td></tr>
  </table>
  <h3>Example Queries:</h3>
  <p>A stadard tray map: gettraymap.html?logid=a4760237-da5e-4fcc-81ff-0c330f36e8d&amp;trayindex=0</p>

  </body>
</html>