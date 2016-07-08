<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services :: Get Spectral Log Collection</title>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Get Spectral Log Collection</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>getSpectralLog service will generate a list of spectral logs in xml format. It requires a datasetid as the mandatory URL parameter. The datasetid can be obtained through the <a href="getDatasetCollection.html">getDatasetCollection</a> service.</p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>datasetid</td><td>obtained through calling the getDatasetCollection service</td><td>yes</td><td>6dd70215-fe38-457c-be42-3b165fd98c7</td></tr>
  
  </table>
  <h3>Example Queries:</h3>
  <p>A stadard query for spectral log ids: getspectrallogs.html?datasetid=6dd70215-fe38-457c-be42-3b165fd98c7</p>
  </body>
</html>