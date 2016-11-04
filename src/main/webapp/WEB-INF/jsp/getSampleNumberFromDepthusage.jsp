<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services : get Sample Number From Depth</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : get Sample Number From Depth</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>The getSampleNumberFromDepth service returns the sample number of the nearest sample to the depth value given. It requires an datasetid and a depth value. The datasetid can be obtained through the <a href="getDatasetCollection.html">getDatasetCollection</a> service.</p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>datasetid</td><td>obtained through calling the getDatasetCollection service</td><td>yes</td><td>6dd70215-fe38-457c-be42-3b165fd98c7</td></tr>
  <tr><td>depth</td><td>value in metres down hole</td><td>yes</td><td>201.5</td></tr>
  
  
  </table>
  <h3>Example Queries:</h3>
  <p>A query for the nearest sample number to 201.5 metres down hole in the 6dd70215-fe38-457c-be42-3b165fd98c7 dataset  : getSampleNumberFromDepth.html?datasetid=6dd70215-fe38-457c-be42-3b165fd98c7&amp;depth=201.5</p>
  </body>
</html>