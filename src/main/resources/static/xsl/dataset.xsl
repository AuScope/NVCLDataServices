<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" omit-xml-declaration="yes"/>

	<xsl:template match="/DatasetCollection/Dataset">
		<html>
			<head><title>NVCL Data: <xsl:value-of select="DatasetName" /></title>
			
				<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous"/>
				<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

				
				<script type="text/javascript" src="https://www.bing.com/api/maps/mapcontrol?callback=GetMap&amp;key=Ai63k9UsFBpMEP_8jghm-kjsSFw1zmGgXSpX8QBqLfSSnilSAeIq47ayJnZkb1R2" async="async" defer="defer"></script>
			
			</head>
			<body>
				<h1>NVCL Data: <xsl:value-of select="DatasetName" /></h1>
					<br/>


								<div class="col">
								<dl class="row" >
									<dt class="col-sm-4">DatasetID</dt>
									<dd class="col-sm-8">
										<xsl:value-of select="DatasetID" />
									</dd>
									<dt class="col-sm-4">boreholeURI</dt>
									<dd class="col-sm-8">
										<a><xsl:attribute name="href"><xsl:value-of select="boreholeURI" /></xsl:attribute><xsl:value-of select="boreholeURI" /></a>
									</dd>

									<dt class="col-sm-4">DatasetName</dt>
									<dd class="col-sm-8">
										<xsl:value-of select="DatasetName" />
									</dd>
									<dt class="col-sm-4">description</dt>
									<dd class="col-sm-8">
									<pre lang="xml"><xsl:value-of disable-output-escaping="yes" select="description" /></pre>
										
									</dd>
									<dt class="col-sm-4">createdDate</dt>
									<dd class="col-sm-8">
										<xsl:value-of select="createdDate" />
									</dd>
									<dt class="col-sm-4">modifiedDate</dt>
									<dd class="col-sm-8">
										<xsl:value-of select="modifiedDate" />
									</dd>
									<dt class="col-sm-4">trayID</dt>
									<dd class="col-sm-8">
										<xsl:value-of select="trayID" />
									</dd>
									<dt class="col-sm-4">sectionID</dt>
									<dd class="col-sm-8">
										<xsl:value-of select="sectionID" />
									</dd>
									<dt class="col-sm-4">domainID</dt>
									<dd class="col-sm-8">
										<xsl:value-of select="domainID" />
									</dd>
									<dt class="col-sm-4">Start Depth</dt>
									<dd class="col-sm-8">
										<xsl:value-of select="DepthRange/start" />
									</dd>
									<dt class="col-sm-4">End Depth</dt>
									<dd class="col-sm-8">
										<xsl:value-of select="DepthRange/end" />
									</dd>
									<dt class="col-sm-4">Images</dt>
									<dd class="col-sm-8">
										<dl class="row" >
										<dt class="col-sm-4">Full resolution</dt>
										<dd class="col-sm-8">
										<xsl:choose>
											<xsl:when  test="ImageLogs/Log[LogName = 'Imagery']">
										<xsl:value-of select="ImageLogs/Log[LogName = 'Imagery']/LogID" />&#160;&#160;<a><xsl:attribute name="href">./mosaic.html?logid=<xsl:value-of select="ImageLogs/Log[LogName = 'Imagery']/LogID" />&amp;startsampleno=0&amp;endsampleno=10&amp;width=1</xsl:attribute>First 10 full resolution images</a>
										</xsl:when >
										<xsl:otherwise>
										<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="red" class="bi bi-x" viewBox="0 0 16 16">
											<path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
										</svg>
										</xsl:otherwise>
										</xsl:choose>
										</dd>
										<dt class="col-sm-4">Tray Pictures</dt>
										<dd class="col-sm-8">
										<xsl:choose>
											<xsl:when  test="ImageLogs/Log[LogName ='Tray Images']">
										<xsl:value-of select="ImageLogs/Log[LogName ='Tray Images']/LogID" />&#160;&#160;<a><xsl:attribute name="href">./mosaic.html?datasetid=<xsl:value-of select="DatasetID" /></xsl:attribute>Full borehole Mosaic</a>
										</xsl:when >
										<xsl:otherwise>
										<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="red" class="bi bi-x" viewBox="0 0 16 16">
											<path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
										</svg>
										</xsl:otherwise>
										</xsl:choose>
										</dd>
										</dl>
									</dd>
									<dt class="col-sm-4">Spectra</dt>
									<dd class="col-sm-8">
									<dl class="row" >
									<dt class="col-sm-4">SWIR</dt>
									<dd class="col-sm-8">
									<xsl:choose>
										<xsl:when  test="SpectralLogs/SpectralLog[logName ='Reflectance']">
											<xsl:value-of select="SpectralLogs/SpectralLog[logName ='Reflectance']/logID" />&#160;&#160;<a><xsl:attribute name="href">./plotspectra.html?speclogid=<xsl:value-of select="SpectralLogs/SpectralLog[logName ='Reflectance']/logID" />&amp;startsampleno=0&amp;endsampleno=2</xsl:attribute>First 3 spectra</a>
										</xsl:when >
										<xsl:otherwise>
										<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="red" class="bi bi-x" viewBox="0 0 16 16">
											<path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
										</svg>
										</xsl:otherwise>
										</xsl:choose>
									</dd>
									<dt class="col-sm-4">Mid</dt>
									<dd class="col-sm-8">
									<xsl:choose>
										<xsl:when  test="SpectralLogs/SpectralLog[logName = 'Mir Reflectance']">
											<xsl:value-of select="SpectralLogs/SpectralLog[logName = 'Mir Reflectance']/logID" />&#160;&#160;<a><xsl:attribute name="href">./plotspectra.html?speclogid=<xsl:value-of select="SpectralLogs/SpectralLog[logName = 'Mid Reflectance']/logID" />&amp;startsampleno=0&amp;endsampleno=2</xsl:attribute>First 3 spectra</a>
										</xsl:when >
										<xsl:otherwise>
										<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="red" class="bi bi-x" viewBox="0 0 16 16">
											<path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
										</svg>
										</xsl:otherwise>
										</xsl:choose>
									</dd>
									<dt class="col-sm-4">Tir</dt>
									<dd class="col-sm-8">
									<xsl:choose>
										<xsl:when  test="SpectralLogs/SpectralLog[logName = 'Base Refl']">
											<xsl:value-of select="SpectralLogs/SpectralLog[logName = 'Base Refl']/logID" />&#160;&#160;<a><xsl:attribute name="href">./plotspectra.html?speclogid=<xsl:value-of select="SpectralLogs/SpectralLog[logName = 'Base Refl']/logID" />&amp;startsampleno=0&amp;endsampleno=2</xsl:attribute>First 3 spectra</a>
										</xsl:when >
										<xsl:otherwise>
										<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="red" class="bi bi-x" viewBox="0 0 16 16">
											<path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
										</svg>
										</xsl:otherwise>
										</xsl:choose>
									</dd>
									</dl>
									</dd>
									<dt class="col-sm-4">Scalars</dt>
									<dd class="col-sm-8">
									<xsl:choose>
										<xsl:when  test="count(Logs/Log) > 0 ">
											<xsl:value-of select="count(Logs/Log)" />
										</xsl:when >
										<xsl:otherwise>
										  <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="red" class="bi bi-x" viewBox="0 0 16 16">
											<path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
										</svg>
										</xsl:otherwise>
										</xsl:choose>
									</dd>

									<dt class="col-sm-4">Profilometer</dt>
									<dd class="col-sm-8">
									<xsl:choose>
										<xsl:when  test="ProfilometerLogs/ProfLog/logID">
											<xsl:value-of select="ProfilometerLogs/ProfLog/logID" />
										</xsl:when >
										<xsl:otherwise>
										  <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="red" class="bi bi-x" viewBox="0 0 16 16">
											<path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
										</svg>
										</xsl:otherwise>
										</xsl:choose>
									</dd>
									<dt class="col-sm-4">TSG File Bundle Available</dt>
									<dd class="col-sm-8">
									<xsl:choose>
										<xsl:when  test="downloadLink">
											<a><xsl:attribute name="href"><xsl:value-of select="downloadLink" /></xsl:attribute>Yes, download it</a>
										</xsl:when >
										<xsl:otherwise>
										  <a><xsl:attribute name="href">./downloadtsg.html?datasetid=<xsl:value-of select="DatasetID" />&amp;email=email@example.com</xsl:attribute>No, Request Download</a>
										</xsl:otherwise>
										</xsl:choose>
									</dd>
								</dl>
								</div>


			</body>

		</html>
	</xsl:template>

	<xsl:template match="word">
		<li><xsl:value-of select="."/></li>
	</xsl:template>

</xsl:stylesheet>