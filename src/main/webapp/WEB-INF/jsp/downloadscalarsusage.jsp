<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services :: Download Scalar Service</title>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Download Scalar Service</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>Given one or more valid scalar log identifiers, this service will pull the values within the logs from the database and build them into a comma separated values (csv) file for download.</p>

  <p>Usage: downloadscalars.html?parameter1=value&amp;parameter2=value&amp;.... </p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>logid</td><td>obtained through calling the getLogCollection service, multiple logids are allowed</td><td>yes</td><td>84346b38-5002-412e-8450-6ba79d38c94 </td></tr>
  <tr><td>startdepth</td><td>start of depth range to get data from in metres from borehole collar</td><td>no</td><td>0</td></tr>
  <tr><td>enddepth</td><td>end of depth range to get data from in metres from borehole collar</td><td>no</td><td>400</td></tr>
  <tr><td>outputformat</td><td>csv or json</td><td>no</td><td>json</td></tr>
  </table>
  <h3>Example Queries:</h3>
   <p>A standard query to download a single scalar csv file : downloadscalars.html?logid=84346b38-5002-412e-8450-6ba79d38c94</p>
   <p>A standard query to download multiple scalars csv file : downloadscalars.html?logid=84346b38-5002-412e-8450-6ba79d38c94&amp;logid=77d0b966-d936-4b9d-bc5c-4b2b12bf1c5&amp;logid=a455603e-17a9-4ba0-bc6b-4866f14a58e</p>
  </body>
</html>