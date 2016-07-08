<%@ include file="include.jsp"%>
<html>
<head>
<title>NVCL Data Services : Scalar Tray Map Service</title>
<link rel="stylesheet" href="style/style.css" type="text/css"/>
</head>


<body >

	
<img class="pixelated" src="/gettraymap.html?logid=${msgMap['logid']}&trayindex=${msgMap['trayindex']}" style="width:${msgMap['width']};height:${msgMap['height']};image-rendering:-moz-crisp-edges">
			
	
</body>
</html>