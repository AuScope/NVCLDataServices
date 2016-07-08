<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services : get Spectral Log Sampling Points</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : get Spectral Log Sampling Points</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>the getSpectralLogSamplingPoints service will get the sampling points (wavelengths) of a spectral log. It requires a spectral log id. Spectrallogids can be obtained from the <a href="getspectrallogs.html">getspectrallogs</a> service.</p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>speclogid</td><td>obtained through calling the getLogCollection service</td><td>yes</td><td>3923f5f9-08f3-415f-8503-7578ab1d784</td></tr>
  
  </table>
  <h3>Example Queries:</h3>
  <p>A query for the spectral sampling points of the 3923f5f9-08f3-415f-8503-7578ab1d784 spectral log : getSpectralLogSamplingPoints.html?speclogid=3923f5f9-08f3-415f-8503-7578ab1d784</p>
  </body>
</html>