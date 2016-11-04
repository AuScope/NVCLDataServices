<%@ include file="include.jsp" %>
<html>
  <head>
		<title>NVCL Download Services : TSG file Download Requests Status Page</title>
		<link rel="stylesheet" href="style/style.css" type="text/css"/>
  </head>
  <body class="NVCLDSBody">
    <h2 class="NVCLDSh2">NVCL Download Services : TSG file Download Requests Status Page</h2>

	<table class="NVCLDSTable">
	<thead>
	<tr><th>Timestamp</th><th>Status</th><th>Details</th><th></th></tr>
	</thead>
	<tbody>
	<c:forEach var="entry" items="${msgMap['request']}">
			<tr class="${rowStyle}">
				<td>${entry.JMSTimestamp}</td>
				<td>Queued</td>
				<td>${entry.description}</td>
				<td></td>
			</tr>
		</c:forEach>
		<c:forEach var="entry" items="${msgMap['reply']}" varStatus="rowCounter">
			<tr class="${rowStyle}">
				<td>${entry.JMSTimestamp}</td>
				<td>${entry.status}</td>
				<c:choose>
					<c:when test='${entry.status=="Success"}'>
						<td><a href="${entry.description}">${entry.tSGDatasetID}${entry.requestLS ? '' : '_NoLS'}.zip</a></td>
						<td>
						<c:choose>
							<c:when test='${entry.resultfromcache==true}'>
								This file was recovered from cache.  If you believe it is stale you can force the service to <a href="downloadtsg.html?datasetid=${entry.tSGDatasetID}&email=${param.email}&linescan=${entry.requestLS ? 'yes' : 'no'}&forcerecreate=yes">regenerate it.</a>
							</c:when>
						</c:choose>
						</td>
					</c:when>
					<c:otherwise>
						<td>${entry.description}</td><td></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
	</tbody>
    </table>

  </body>
</html>