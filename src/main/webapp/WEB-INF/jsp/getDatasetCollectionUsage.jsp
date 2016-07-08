<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services :: Get Dataset Collection</title>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Get Dataset Collection</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>getDatasetCollection service will generate a list of dataset collection in xml format. It requires a holeidentifier as the mandatory URL parameter. The holeidentifier can be obtained from gml:id of either gsml:Borehole or gsmlp:BoreholeView WFS output (e.g. gsml.borehole.WTB5, take the WTB5 only).</p>
  <p>Usage: getDatasetCollection.html?parameter1=value</p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>holeidentifier</td><td>same as borehole id which can be obtained through the GeoServer WFS ouput</td><td>yes</td><td>WTB5</td></tr>
  </table>
  <h3>Example Queries:</h3>
  <p>A standard query : getDatasetCollection.html?holeidentifier=WTB5</p>
  </body>
</html>