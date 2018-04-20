<%@ include file="include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>NVCL Data Services : Image Carousel Service</title>
<link rel="stylesheet" href="style/style.css" type="text/css" />

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">

<script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

	  <style>
  .carousel-inner > .carousel-item > img,
  .carousel-inner > .carousel-item > a > img {
      margin: auto;
  }
  .carousel {
    position: absolute;
    width: 90%;
}
	.carousel-control-prev {
	width:5%;
	}
	.carousel-control-next {
	width:5%;
	right:-10%;
	}
	.caroimage {
	width:100%;
	}
	.carousel-inner {
	    left:5%;
	}
	.NVCLDSBody {
	background-color:black;
	}
  </style>

</head>
<body class="NVCLDSBody" style="overflow-y: scroll;overflow-x: hidden;">

	<div id="myCarousel" class="carousel" data-interval="false" data-wrap="false" >

		<div class="carousel-inner" role="listbox">
			<c:forEach var="entry" items="${msgMap['images']}" varStatus = "status">
			<div class="carousel-item ${msgMap['sampleno']==entry.sampleNo ? 'active' : ''}">
				<img ${(((msgMap['sampleno']-entry.sampleNo)>0 ? (msgMap['sampleno']-entry.sampleNo) : (entry.sampleNo - msgMap['sampleno'])) <2) ? 'src' : 'lazyloadsrc'}="${entry.URL}" class="caroimage" alt="${entry.sampleNo}">
			</div>
		</c:forEach>
		</div>

		<!-- Left and right controls -->
		<a class="left carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
		  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
		  <span class="sr-only">Previous</span>
		</a> 
		<a class="right carousel-control-next" href="#myCarousel" role="button" data-slide="next">
		  <span	class="carousel-control-next-icon" aria-hidden="true"></span>
	      <span class="sr-only">Next</span>
		</a>
	</div>
	<br>
	<div id="plotcontainer"></div>
	
	<script>
	var colors= ['lightblue', 'lightcoral', 'lightcyan', 'lightgray', 'lightgreen','lightpink', 'lightyellow'];
	var dragging=false;
	var dragoriginx=0;
	var dragoriginy=0;
	var dragorigintop=0;
	var dragoriginleft=0;
	var movingdiv=null;
	var counter=0;
	var zindex=99;
	
	checkprevnext = function() {
		var $this = $("#myCarousel");
	//	$this.children('.carousel-control').show();
		if($('#myCarousel .carousel-inner .carousel-item:first').hasClass('active')) {
    	    $this.children('.carousel-control-prev').hide();
    	  }
		else $this.children('.carousel-control-prev').show();
		if($('#myCarousel .carousel-inner .carousel-item:last').hasClass('active')) {
    	    $this.children('.carousel-control-next').hide();
    	  }
		else $this.children('.carousel-control-next').show();
	    $activeItem = $('.active.carousel-item', this);

	    $itemlist = $('.carousel-item', this);
	    
	    
		var i = Math.max($activeItem.index()-3,0);
		var lastindex = $activeItem.index()+3

	    for (; i<=lastindex;i++){
	    	var $item = $($itemlist[i]);
		    var $img = $item.find('img')
		    var src = $img.attr('lazyloadsrc');

		    if (typeof src !== "undefined" && src != "") {
		    	$img.attr('src', src)
		    	$img.attr('lazyloadsrc', '');
		    }
		    $item = $item.next('.carousel-item');
	    }
		
		};

		checkprevnext ();


		function dragstart(ev)
		{
		    dragging=true;
		    if (!ev) ev=window.event;
		    var div = ev.target.parentNode;
		    movingdiv=div
		    dragoriginx = ev.clientX;
		    dragoriginy = ev.clientY;
		    dragoriginleft= parseInt(div.style.left);
		    dragorigintop= parseInt(div.style.top);
		    window.onmousemove = dragmove;
		    window.onmouseup = dragstop;
		    if (ev.stopPropagation) 
		    {
		        ev.stopPropagation();
		        ev.preventDefault();
		    }
		    else
		    {
		        ev.cancelBubble = true;
		        ev.returnValue = false;
		    }
		}

		function dragmove(ev)
		{
		    if (dragging==false) return;
		    if (!ev) ev=window.event;
		    ev.cancelBubble = true;
			if (ev.stopPropagation) ev.stopPropagation();
		    var div = movingdiv;
		    div.style.left=dragoriginleft+ev.clientX-dragoriginx+"px";
		    div.style.top=dragorigintop+ev.clientY-dragoriginy+"px";
		    zindex++;
		    div.style.zIndex=zindex;
		}

		function dragstop(ev)
		{
		    dragging=false;
		    document.onmousemove = null;
		    document.onmouseup = null;
		    movingdiv=null;
		}
		
		function closeplot(counter)
		{
			//var div = ev.srcElement.parentNode.parentNode.parentNode;
			$('#plot'+counter+'container').remove();
			$("#marker"+counter).remove();
		}
		
		function clearmap()
		{
			$('#plotcontainer').empty();
		}
		
		$("#myCarousel").on("slid.bs.carousel", "", checkprevnext );
		$("#myCarousel").on("slid.bs.carousel", "", clearmap );
		showspectra = function(event) {
			var $this = $('#myCarousel .carousel-inner .active.carousel-item img');
			var parentOffset = $this.parent().offset();
			var offset = $this.offset();
			var x = Math.round(event.pageX - offset.left);
			var y = Math.round(event.pageY - offset.top);
	    	var height =Math.round($this.height());
	    	var width = Math.round($this.width());
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
						$.get("plotspectra.html?speclogid="+allspectra+"&startsampleno="+Math.max((specsamplenumber-1),0)+"&endsampleno="+(specsamplenumber+1)+"&width=500&height=300",function(plothtml){
							counter++;
							if($("#plotspectra"+Math.max((specsamplenumber-1),0)).length!=0){
								var plotspecwid="#plotspectra"+Math.max((specsamplenumber-1),0);
								var plotspeccontainer=$(plotspecwid).parents(".floatingGraph");
								var currentcounter= plotspeccontainer.attr('id').replace("plot","").replace("container","");
								$("#marker"+currentcounter).remove();
								plotspeccontainer.remove();
							}
							$('<div id="marker'+counter+'" style="px;background:'+colors[counter%7]+'" class="circle"><div class="flabel-center">'+counter+'</div></div><div id="plot'+counter+'container" class="floatingGraph" ><div onmousedown="dragstart(event);" style="width:100%;background:'+colors[counter%7]+';text-align:center;" >Move<div style="float:right;"><span onclick=closeplot('+counter+'); class="m-1"><i class="fa fa-window-close fa-lg" style="margin:3px"></i></span></div></div>'+plothtml+'</div>').appendTo($('#plotcontainer'))

							$("#marker"+counter).css({left:(event.pageX-13),top:(event.pageY-13)});
							$("#plot"+counter+"container").css({top: event.pageY, left: event.pageX, position:'absolute'});
							$("#plot"+counter+"container").css({display:'block'});
							
							});
						
					});
	    	});
	    	});
	    };

		$(".caroimage").on("click", "", showspectra );
		


		$(window).resize(function() {clearmap();});


		
    </script>
</body>
</html>