<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL TSG Dataset Download Services : TSG file download</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL TSG Dataset Download Services : TSG file download</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>This service prepares TSG files from NVCL database datasets and makes them available for download.</p>
  <p>Usage: downloadtsg.html?parameter1=value&amp;parameter2=value&amp;.... </p>

  <table class="usageTable"><tr><th>Parameter</th><th>Description</th><th>Required</th><th>Example values</th></tr>
  <tr><td>email</td><td>User's email address to identify the user and for sending job status update emails</td><td>yes</td><td>user@example.com</td></tr>
  <tr><td>datasetid</td><td>GUID dataset identifier of the dataset to be prepared</td><td>yes</td><td>6dd70215-fe38-457c-be42-3b165fd98c7</td></tr>
  <tr><td>linescan</td><td>Prepare linescan imagery with this dataset.  Setting this to 'no' will reduce the size of the download significantly but users will not be able to see the highest resolution images</td><td>no, Default value=yes</td><td>yes or no</td></tr>
  <tr><td>forcerecreate</td><td>Force the service to delete the cached version of this dataset and recreate it.  Use this if there is a problem with the dataset or cached version is stale</td><td>no, Default value=no</td><td>yes or no</td></tr>
  </table>
  <h3>Example Queries:</h3>
  <p>A standard query : downloadtsg.html?email=user@example.com&amp;datasetid=6dd70215-fe38-457c-be42-3b165fd98c7</p>
  <p>A query which excludes linescan : downloadtsg.html?email=user@example.com&amp;datasetid=6dd70215-fe38-457c-be42-3b165fd98c7&amp;linescan=no</p>
  <p>A query which forces regeneration : downloadtsg.html?email=user@example.com&amp;datasetid=6dd70215-fe38-457c-be42-3b165fd98c7&amp;forecerecreate=yes</p>
  <p>A query which excludes linescan and forces regeneration : downloadtsg.html?email=user@example.com&amp;datasetid=6dd70215-fe38-457c-be42-3b165fd98c7&amp;linescan=no&amp;forecerecreate=yes</p>
  </body>
</html>