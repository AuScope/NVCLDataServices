<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services :: Plot Multiple Scalars Service</title>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Plot Multiple Scalars Service</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>plotmultiscalars service is one of the service added in version 2.0.0 of NVCL Data Services. The difference between plotscalar and plotmultiscalars lies on it's output, the previous generate the output as an PNG format image and the later generate a HTML output with img tag embed with the actual request to the plotscalar request. It requires a logid as the mandatory URL parameter and able to take up to maximum of 6 logids. The logid can be obtained through the <a href="getLogCollection.html">getLogCollection</a> service with mosaicsvc URL parameter set to 'no'. </p>

  <p>Usage: plotmultiscalars.html?parameter1=value&amp;parameter2=value&amp;.... </p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>logid</td><td>obtained through calling the getLogCollection service, with mosaicsvc URL parameter set to 'no' and up to 6 logid parameters are allowed</td><td>yes</td><td>84346b38-5002-412e-8450-6ba79d38c94</td></tr>
  <tr><td>startdepth</td><td>the start depth of a borehole collar</td><td>no, Default value=0</td><td>0</td></tr>
  <tr><td>enddepth</td><td>the end depth of a borehole collar</td><td>no, Default value=99999</td><td>19</td></tr>
  <tr><td>samplinginterval</td><td>the interval of the sampling</td><td>no, Default value=1</td><td>1</td></tr>
  <tr><td>width</td><td>the width of the image in pixel</td><td>no, Default value=300</td><td>300</td></tr>
  <tr><td>height</td><td>the height of the image in pixel</td><td>no, Default value=600</td><td>600</td></tr>
  <tr><td>graphtype</td><td>an integer range from 1 to 3, 1=Stacked Bar Chart, 2=Scattered Chart, 3=Line Chart</td><td>no, Default value=1</td><td>1</td></tr>
  <tr><td>legend</td><td>value=yes or no, if yes - indicate to show the legend, default to yes</td><td>no, Default value=1</td><td>1</td></tr>
  </table>
  <h3>Example Queries:</h3>
    <p>A standard query for single Stacked Bar Chart : plotmultiscalars.html?logid=84346b38-5002-412e-8450-6ba79d38c94</p>
    <p>A standard query for multiple Stacked Bar Chart : plotmultiscalars.html?logid=84346b38-5002-412e-8450-6ba79d38c94&amp;logid=77d0b966-d936-4b9d-bc5c-4b2b12bf1c5&amp;logid=a455603e-17a9-4ba0-bc6b-4866f14a58e</p>
  </body>
</html>