<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" omit-xml-declaration="yes"/>

	<xsl:template match="/">
		<html>
			<head><title>NVCL Data</title></head>
			<body>
				<h1>NVCL Data</h1>
				<br/>

				<xsl:variable name="downloadlinks">
					<xsl:choose>
						<xsl:when  test="DatasetCollection/Dataset/downloadLink">
							<xsl:value-of select="1"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="0"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>

				
				<table class="table">
					<thead>
						<tr>
						<th scope="col">DatasetID</th>
						<th scope="col">boreholeURI</th>
						<th scope="col">DatasetName</th>
						<th scope="col">description</th>
						<th scope="col">createdDate</th>
						<th scope="col">modifiedDate</th>
						<th scope="col">trayID</th>
						<th scope="col">sectionID</th>
						<th scope="col">domainID</th>
						<xsl:if test="$downloadlinks=1">
							<th scope="col">TSG File Download</th>
						</xsl:if>
						</tr>
					</thead>
					<tbody>
						<xsl:for-each select="DatasetCollection/Dataset">
							<tr>
							<td ><a><xsl:attribute name="href">./getDatasetCollection.html?datasetid=<xsl:value-of select="DatasetID" />&amp;outputformat=html</xsl:attribute><xsl:value-of select="DatasetID" /></a></td>
							<td><xsl:value-of select="boreholeURI" /></td>
							<td><xsl:value-of select="DatasetName" /></td>
							<td><xsl:value-of select="description" /></td>
							<td><xsl:value-of select="createdDate" /></td>
							<td><xsl:value-of select="modifiedDate" /></td>
							<td><xsl:value-of select="trayID" /></td>
							<td><xsl:value-of select="sectionID" /></td>
							<td><xsl:value-of select="domainID" /></td>
							<td>
							<xsl:if test="$downloadlinks=1">
								<xsl:choose>
									<xsl:when  test="downloadLink">
										<a><xsl:attribute name="href"><xsl:value-of select="downloadLink" /></xsl:attribute>Download</a>
									</xsl:when>
									<xsl:otherwise>
										  <a><xsl:attribute name="href">./downloadtsg.html?datasetid=<xsl:value-of select="DatasetID" />&amp;email=email@example.com</xsl:attribute>Not available, Request Now</a>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:if>
							</td>
							</tr>
						</xsl:for-each>
					</tbody>
				</table>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="word">
		<li><xsl:value-of select="."/></li>
	</xsl:template>

</xsl:stylesheet>