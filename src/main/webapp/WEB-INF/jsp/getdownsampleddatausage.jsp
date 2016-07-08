<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services :: Download Scalar Service</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Download Down-sampled Scalar Data Service</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>Given one valid scalar log identifier, this service will pull the values within the logs from the database, bin or average them over the specified interval and build them into a comma separated values (csv) or json file for download.</p>

  <p>Usage: getDownsampledData.html?parameter1=value&amp;parameter2=value&amp;.... </p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>logid</td><td>obtained through calling the getLogCollection service</td><td>yes</td><td>84346b38-5002-412e-8450-6ba79d38c94</td></tr>
  <tr><td>interval</td><td>size of interval to bin or average over</td><td>no</td><td>1.0</td></tr>
  <tr><td>startdepth</td><td>start of depth range to get data from in metres from borehole collar</td><td>no</td><td>0</td></tr>
  <tr><td>enddepth</td><td>end of depth range to get data from in metres from borehole collar</td><td>no</td><td>400</td></tr>
  <tr><td>outputformat</td><td>csv or json</td><td>no</td><td>json</td></tr>
  </table>
  <h3>Example Queries:</h3>
   <p>A standard query to download a single scalar csv file : getDownsampledData.html?logid=84346b38-5002-412e-8450-6ba79d38c94</p>
   <p>A standard query to download multiple scalars csv file : getDownsampledData.html?logid=84346b38-5002-412e-8450-6ba79d38c94&amp;outputformat=json</p>
  </body>
</html>