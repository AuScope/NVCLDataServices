<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Download Services : TSG file Download Requests Status Page</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL TSG Dataset Download Services : TSG file download</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>This service displays the status of past TSG file download requests for users.</p>
  <p>Usage: checktsgstatus.html?email=value</p>

<p>This service takes a single parameter which is the email address of the user.<p>

  <h3>Example Queries:</h3>
  <p>checktsgstatus.html?email=user@example.com</p>
  </body>
</html>