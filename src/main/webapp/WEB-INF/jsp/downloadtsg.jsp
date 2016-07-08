<%@ include file="include.jsp" %>
<html>
  <head><title>NVCL Download Services :: TSG Download Page</title></head>
  <link rel="stylesheet" href="style/style.css" type="text/css"/>
  <body class="NVCLDSBody">
	<table class="NVCLDSTable">
	<thead>
	<tr><th>Status : <c:choose>
			<c:when test="${msgMap['status'] == 'success'}">
			Request Submitted
			</c:when>
			<c:otherwise>
			Request Failed
			</c:otherwise>
			</c:choose>
	</thead>
	<tr><td>
		<c:choose>
			<c:when test="${msgMap['status'] == 'success'}">
				Your request has been submitted. The preparation of your data may take some time
		    	depending on the size of the dataset and the current workload of the service.
				<c:choose>
				<c:when test="${msgMap['sendEmails']}">
				Once its completed you should receive an email containing a link to the data file. If
				you do not receive an email you can manually check the status of your request <a href='<c:out value="${msgMap['webappURL']}"/>checktsgstatus.html?email=
				<c:out value="${msgMap['email']}"/>'>here</a>
				</c:when>
				<c:otherwise>
				You will need to manually check the status of your request <a href='<c:out value="${msgMap['webappURL']}"/>checktsgstatus.html?email=
				<c:out value="${msgMap['email']}"/>'>here</a>
				</c:otherwise>
				</c:choose>
				<br><br>If you have any comments, suggestions or issues with this service please email the administrator: (<c:out value="${msgMap['adminemail']}"/>)
				for assistance.
			</c:when>
			<c:when test="${msgMap['status'] == 'CacheClearFail'}" >
			Failed to delete cached file.  This can happen if the file is currently being downloaded. Please try again later or if the problem persists contact support at <c:out value="${msgMap['adminEmail']}"/>.
			</c:when>
			<c:otherwise>
				Your request has failed, please email the administrator: <c:out value="${msgMap['adminEmail']}"/>
				for assistance.
			</c:otherwise>
		</c:choose>
	</td></tr>
	</table>

  </body>
</html>