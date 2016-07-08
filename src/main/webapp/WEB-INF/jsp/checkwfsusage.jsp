<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Download Services : WFS Download Requests Status Page</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body  class="NVCLDSBody">
  <h2 class="NVCLDSh2">NVCL Download Services : WFS Download Requests Status Page</h2>
  <h2><font color="red">Error: <c:out value="${errmsg}"/></font></h2>
  <p>This service displays the status of past WFS file download requests for users.</p>
  <p>Usage: checkwfsstatus.html?email=value</p>

<p>This service takes a single parameter which is the email address of the user.<p>

  <h3>Example Queries:</h3>
  <p>checkwfsstatus.html?email=user@example.com</p>
  </body>
</html>