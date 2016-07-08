<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Dataset Download Services : WFS file download</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Dataset Download Services : WFS file download</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>This Service prepares xml datsets from NVCL geoserver instances and makes them available for download.</p>
  <p>Usage: downloadwfs.html?parameter1=value&amp;parameter2=value&amp;.... </p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>email</td><td>User's email address to identify the user and for sending job status update emails</td><td>yes</td><td>user@example.com</td></tr>
  <tr><td>boreholeid</td><td>The gml feature identifier of the dataset to be prepared</td><td>yes</td><td>sa.samplingfeaturecollection.6dd70215-fe38-457c-be42-3b165fd98c7</td></tr>
  <tr><td>typename</td><td>The type name of the gml feature to prepare</td><td>no, Default value=sa:SamplingFeatureCollection</td><td>sa:SamplingFeatureCollection</td></tr>
  <tr><td>forcerecreate</td><td>Force the service to delete the cached version of this dataset and recreate it.  Use this if there is a problem with the dataset or cached version is stale</td><td>no, Default value=no</td><td>yes or no</td></tr>
  </table>
  <h3>Example Queries:</h3>
  <p>A standard query : downloadwfs.html?email=user@example.com&amp;boreholeid=sa.samplingfeaturecollection.6dd70215-fe38-457c-be42-3b165fd98c7</p>
  <p>A query which forces regeneration : downloadwfs.html?email=user@example.com&amp;boreholeid=sa.samplingfeaturecollection.6dd70215-fe38-457c-be42-3b165fd98c7&amp;forecerecreate=yes</p>
  </body>
</html>