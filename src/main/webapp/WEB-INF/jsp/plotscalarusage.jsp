<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Data Services :: Plot Scalar Service</title>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Data Services : Plot Scalar Service</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>plotscalar service uses JFeeChart Java chart library to draw a plot of the product and return the plot as an image in PNG format. It requires a logid as the mandatory URL parameter. The logid can be obtained through the <a href="getLogCollection.html">getLogCollection</a> service with mosaicsvc URL parameter set to 'no'.</p>

  <p>Usage: plotscalar.html?parameter1=value&amp;parameter2=value&amp;.... </p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>logid</td><td>obtained through calling the getLogCollection service with mosaicsvc URL parameter set to 'no'</td><td>yes</td><td>77d0b966-d936-4b9d-bc5c-4b2b12bf1c5</td></tr>
  <tr><td>startdepth</td><td>the start depth of a borehole collar</td><td>no, Default value=0</td><td>0</td></tr>
  <tr><td>enddepth</td><td>the end depth of a borehole collar</td><td>no, Default value=99999</td><td>19</td></tr>
  <tr><td>samplinginterval</td><td>the interval of the sampling</td><td>no, Default value=1</td><td>1</td></tr>
  <tr><td>width</td><td>the width of the image in pixel</td><td>no, Default value=300</td><td>300</td></tr>
  <tr><td>height</td><td>the height of the image in pixel</td><td>no, Default value=600</td><td>600</td></tr>
  <tr><td>graphtype</td><td>an integer range from 1 to 3, 1=Stacked Bar Chart, 2=Scattered Chart, 3=Line Chart</td><td>no, Default value=1</td><td>1</td></tr>
  <tr><td>legend</td><td>value=yes or no, if yes - indicate to show the legend, default to yes</td><td>no, Default value=1</td><td>1</td></tr>
  </table>
  <h3>Example Queries:</h3>
   <p>A standard query for Stacked Bar Chart : plotscalar.html?logid=77d0b966-d936-4b9d-bc5c-4b2b12bf1c5&amp;graphtype=1</p>
   <p>A standard query for Stacked Bar Chart without legend : plotscalar.html?logid=77d0b966-d936-4b9d-bc5c-4b2b12bf1c5&amp;graphtype=1&amp;legend=0</p>
   <p>A standard query for Scattered Chart: plotscalar.html?logid=77d0b966-d936-4b9d-bc5c-4b2b12bf1c5&amp;graphtype=2</p>
   <p>A standard query for Line Chart: plotscalar.html?logid=77d0b966-d936-4b9d-bc5c-4b2b12bf1c5&amp;graphtype=3</p>
  </body>
</html>