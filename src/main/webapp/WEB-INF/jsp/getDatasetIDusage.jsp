<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services : Get Dataset ID</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Get Dataset ID</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>the GetDatasetID service will return the datasetid for a provided log id. It requires an logid. Logids can be obtained from the <a href="getLogCollection.html">getLogCollection</a> service.</p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>logid</td><td>obtained through calling the getLogCollection service</td><td>yes</td><td>84346b38-5002-412e-8450-6ba79d38c94</td></tr>
  
  </table>
  <h3>Example Queries:</h3>
  <p>A query for the dataset id of the log with id 84346b38-5002-412e-8450-6ba79d38c94 : getDatasetID.html?logid=84346b38-5002-412e-8450-6ba79d38c94</p>
  </body>
</html>