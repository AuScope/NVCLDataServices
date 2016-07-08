<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services :: Get Tray Map HTML tag</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Get Tray Map HTML tag </h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>This service will return an html tag with fixed width and height to embed a tray scalar data map into another page. It requires a logid id, a 0 based tray index, map width and map height. The logid can be obtained through the <a href="getLogCollection.html">getLogCollection</a> service.</p>
<p>This service is specifically for fixed CSS width and height html tags.  If you want the raw image data use the <a href="gettraymap.html">gettraymap service</a> and set the width/height in the client. </p>
  <p>Usage: gettraymaphtml.html?parameter1=value&amp;parameter2=value&amp;...</p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>logid</td><td>obtained through calling the getLogCollection service</td><td>yes</td><td>a4760237-da5e-4fcc-81ff-0c330f36e8d</td></tr>
  <tr><td>width</td><td>CSS width of the tray map to be created</td><td>yes</td><td>200</td></tr>
  <tr><td>height</td><td>CSS height of the tray map to be created</td><td>yes</td><td>150</td></tr>
  <tr><td>trayindex</td><td>0 based tray index to select the tray you're interested in</td><td>yes</td><td>0</td></tr>
  </table>
  <h3>Example Queries:</h3>
  <p>A stadard tray map: gettraymaphtml.html?logid=a4760237-da5e-4fcc-81ff-0c330f36e8d&amp;width=200&amp;height=150&amp;trayindex=0</p>


  </body>
</html>