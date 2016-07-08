<%@ include file="include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>NVCL Data Services : Image Carousel Service</title>
<link rel="stylesheet" href="style/style.css" type="text/css" />
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" type="text/javascript"></script>
	  <style>
  .carousel-inner > .item > img,
  .carousel-inner > .item > a > img {
      margin: auto;
  }
  .carousel {
    position: absolute;
    width: 100%;
}
	.carousel-control {
	width:5%;
	}
	.caroimage {
	width:90%;
	}
  </style>

</head>
<body class="NVCLDSBody">

	<div id="myCarousel" class="carousel" data-interval="false" data-wrap="false" >

		<div class="carousel-inner" role="listbox">
			<c:forEach var="entry" items="${msgMap['images']}" varStatus = "status">
			<div class="item ${msgMap['sampleno']==entry.sampleNo ? 'active' : ''}">
				<img ${(((msgMap['sampleno']-entry.sampleNo)>0 ? (msgMap['sampleno']-entry.sampleNo) : (entry.sampleNo - msgMap['sampleno'])) <4) ? 'src' : 'lazy-load-src'}="${entry.URL}" class="caroimage" alt="${entry.sampleNo}">
			</div>
		</c:forEach>
		</div>

		<!-- Left and right controls -->
		<a class="left carousel-control" href="#myCarousel" role="button"
			data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		</a> <a class="right carousel-control" href="#myCarousel" role="button"
			data-slide="next"> <span
			class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
	</div>
	<script>
	
	checkprevnext = function() {
		var $this = $("#myCarousel");
		$this.children('.carousel-control').show();
		if($('#myCarousel .carousel-inner .item:first').hasClass('active')) {
    	    $this.children('.left.carousel-control').hide();
    	  }
		if($('#myCarousel .carousel-inner .item:last').hasClass('active')) {
    	    $this.children('.right.carousel-control').hide();
    	  }
	    $activeItem = $('.active.item', this);

	    $itemlist = $('.item', this);
	    
	    
		var i = Math.max($activeItem.index()-3,0);
		var lastindex = $activeItem.index()+3

	    for (; i<=lastindex;i++){
	    	var $item = $($itemlist[i]);
		    var $img = $item.find('img')
		    var src = $img.attr('lazy-load-src');

		    if (typeof src !== "undefined" && src != "") {
		    	$img.attr('src', src)
		    	$img.attr('lazy-load-src', '');
		    }
		    $item = $item.next('.item');
	    }
		
		};

		checkprevnext ();

		$("#myCarousel").on("slid.bs.carousel", "", checkprevnext );
		showspectra = function(event) {
			var $this = $('#myCarousel .carousel-inner .active.item img');
			var parentOffset = $this.parent().offset();
			var offset = $this.offset();
			var x = Math.round(event.pageX - parentOffset.left);
			var y = Math.round(event.pageY - parentOffset.top);
	    	var height =$this.height();
	    	var width = $this.width();
	    	var src = $this.prop('src');
	    	var logidindex = src.indexOf('logid=');
	    	var logid = src.slice(logidindex);
	    	var andpos = logid.indexOf('&');
	    	logid = logid.substr(0,andpos);
	    	var traysamplenumber = src.slice(src.indexOf('sampleno='));
	    	traysamplenumber = traysamplenumber.substr(9,traysamplenumber.length);
	    	$.getJSON( "trayImageSampleLocate.html?"+logid+"&sampleno="+traysamplenumber+"&pixelx="+x+"&pixely="+y+"&imgwidth="+width+"&imgheight="+height, function( data ) {
		    	var specsamplenumber = data["sampleno"];
		    	$.getJSON( "getDatasetID.html?"+logid,function( dsdata ) {
			    	var dsid = dsdata["datasetid"];
					$.getJSON("getspectrallogs.html?datasetid="+dsid+"&outputformat=json",function(speclogsdata){
						var swirspectra="";
						var tirspectra="";
						$.each(speclogsdata["SpectralLogCollection"],function( key, value ) {
							if(value["logName"] == "Reflectance") {
								swirspectra=value["logID"];
							}
							else if (value["logName"] == "Base Refl") {
								tirspectra=value["logID"];
							}
						});
						var allspectra = swirspectra + (tirspectra.length>0 ? ","+ tirspectra:"");
						window.location.href = "plotspectra.html?speclogid="+allspectra+"&startsampleno="+Math.max((specsamplenumber-4),0)+"&endsampleno="+(specsamplenumber+4);
					});
	    	});
	    	});
	    };

		$(".caroimage").on("click", "", showspectra );
		
    </script>
</body>
</html>