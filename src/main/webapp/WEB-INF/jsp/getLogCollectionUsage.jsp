<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services :: Get Log Collection</title>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Get Log Collection</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>getLogCollection service will generate a list of logs collection in xml format. It requires a datasetid as the mandatory URL parameter. The datasetid can be obtained through the <a href="getDatasetCollection.html">getDatasetCollection</a> service.</p>
  <p>Usage: getLogCollection.html?parameter1=value&amp;parameter2=value</p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>datasetid</td><td>obtained through calling the getDatasetCollection service</td><td>yes</td><td>6dd70215-fe38-457c-be42-3b165fd98c7</td></tr>
  <tr><td>mosaicsvc</td><td>value=yes or no, if "yes" - indicate if the getLogCollection service generate a list of log ids specifically for the use of Mosaic Service. Default value is 'no' .</td><td>no</td><td>yes</td></tr>

  </table>
  <h3>Example Queries:</h3>
  <p>A stadard query for mosaic log ids: getLogCollection.html?datasetid=6dd70215-fe38-457c-be42-3b165fd98c7&amp;mosaicsvc=yes</p>
  <p>A stadard query for scalar log ids: getLogCollection.html?datasetid=6dd70215-fe38-457c-be42-3b165fd98c7&amp;mosaicsvc=no</p>
  </body>
</html>