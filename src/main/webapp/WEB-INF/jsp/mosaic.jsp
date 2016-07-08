<%@ include file="include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
  <head>
    <title>NVCL Data Services :: Mosaic Web Service</title>
    <link rel="stylesheet" href="style/style.css" type="text/css"/>
    <script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"	type="text/javascript"></script>
<style>
.pixelated {
  image-rendering:optimizeSpeed;             /* Legal fallback */
  image-rendering:-moz-crisp-edges;          /* Firefox        */
  image-rendering:-o-crisp-edges;            /* Opera          */
  image-rendering:-webkit-optimize-contrast; /* Safari         */
  image-rendering:optimize-contrast;         /* CSS3 Proposed  */
  image-rendering:crisp-edges;               /* CSS4 Proposed  */
  image-rendering:pixelated;                 /* CSS4 Proposed  */
  -ms-interpolation-mode:nearest-neighbor;   /* IE8+           */
}

.NVCLMosaicCell {
	display:block;
	float:left;
}

.NVCLMosaicCellContent {
	display:table;
	table-layout:fixed;
	width:100%;
	height: 100%;
}

.NVCLMosaicCellDepths {
	vertical-align:middle;
	display: table-cell;
	width:33%;
}

.NVCLMosaicCellPara {
	text-align: center;
	margin: 0;
}

.NVCLMosaicCellImg {
	display:table-cell;
	height: 100%;
}

.NVCLMosaicImage {
	display: block;
	width: 100%;

}
</style>

  </head>

  <body>
    <c:out value="${imageURL}" escapeXml="false" />
    
<script type ="text/javascript">
    
 
	// the bellow is required to scale the tray map correctly in broswers that dont interpret the 100% height correctly   		
	function resizeimgs() {
     	$('.pixelated').width($('.NVCLMosaicImage').width());
     	$('.NVCLMosaicCellContent').height($('.NVCLMosaicImage').height());
	}

	if ($('.pixelated').height()<=16){
		$( window ).resize(resizeimgs);
		$( window ).load(resizeimgs);
	}
   
    
    </script>
  </body>
</html>
