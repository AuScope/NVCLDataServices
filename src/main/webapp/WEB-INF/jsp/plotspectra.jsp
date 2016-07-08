
<%@ include file="include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Spectral Data Plot</title>

<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"	type="text/javascript"></script>
<script src="script/d3.min.js"></script>
<script src="script/rickshaw.min.js"></script>
<link rel="stylesheet" href="style/rickshaw.min.css">

	<script type="text/javascript">
$.ajaxTransport("+*", function(options, originalOptions, jqXHR){
    // Test for the conditions that mean we can/want to send/receive blobs or arraybuffers - we need XMLHttpRequest
    // level 2 (so feature-detect against window.FormData), feature detect against window.Blob or window.ArrayBuffer,
    // and then check to see if the dataType is blob/arraybuffer or the data itself is a Blob/ArrayBuffer
    if (window.FormData && ((options.dataType && (options.dataType == 'blob' || options.dataType == 'arraybuffer'))
        || (options.data && ((window.Blob && options.data instanceof Blob)
            || (window.ArrayBuffer && options.data instanceof ArrayBuffer)))
        ))
    {
        return {
            /**
             * Return a transport capable of sending and/or receiving blobs - in this case, we instantiate
             * a new XMLHttpRequest and use it to actually perform the request, and funnel the result back
             * into the jquery complete callback (such as the success function, done blocks, etc.)
             *
             * @param headers
             * @param completeCallback
             */
            send: function(headers, completeCallback){
                var xhr = new XMLHttpRequest(),
                    url = options.url || window.location.href,
                    type = options.type || 'GET',
                    dataType = options.dataType || 'text',
                    data = options.data || null,
                    async = options.async || true;
 
                xhr.addEventListener('load', function(){
                    var res = {};
 
                    res[dataType] = xhr.response;
                    completeCallback(xhr.status, xhr.statusText, res, xhr.getAllResponseHeaders());
                });
 
                xhr.open(type, url, async);
                xhr.responseType = dataType;
                xhr.send(data);
            },
            abort: function(){
                jqXHR.abort();
            }
        };
    }
});
function drawgraph(k,wavelengths) {
	return function(result){
	var data = [];
	var floats = new Float32Array(result);
	var spectracount = result.byteLength/(4*wavelengths.length);
	var palette = new Rickshaw.Color.Palette( { scheme: 'munin' } );
	for (var j=0;j<spectracount;j++)
	{
		var d1 =[];
		
		for (var i = 0; i < wavelengths.length; i++) {
			d1.push({x:parseFloat(wavelengths[i]), y:floats[(j*wavelengths.length)+i]});
		}
		data.push({data:d1,name:"Reflectance Sample "+((j-Math.floor(spectracount/2))>0? "+"+ (j-Math.floor(spectracount/2)):(j-Math.floor(spectracount/2))) ,color:palette.color()});
	}
	var graph = new Rickshaw.Graph( {
		element: document.getElementById("chart"+k),
		width: 1460,
		height: 800,
		renderer: 'line',
		series: data,
		min: 'auto'
	} );

	var y_axis = new Rickshaw.Graph.Axis.Y( {
		graph: graph
	} );
	var x_axis = new Rickshaw.Graph.Axis.X( {
        graph: graph
    } );
	var legend = new Rickshaw.Graph.Legend( {
        element: document.querySelector("#legend"+k),
        graph: graph
    } );
	var shelving = new Rickshaw.Graph.Behavior.Series.Toggle( {
		graph: graph,
		legend: legend
	} );
	var hoverDetail = new Rickshaw.Graph.HoverDetail( {
		graph: graph,
		xFormatter: function(x,y) {
			return x.toString();
		}
	} );
	graph.render();
	}
}

	$(function() {
		$.ajaxSetup({
		    async: false
		});
		var qd = {};
		location.search.substr(1).split("&").forEach(function(item) {var k = item.split("=")[0], v = item.split("=")[1]; v = v && decodeURIComponent(v); (k in qd) ? qd[k].push(v) : qd[k] = [v]})
		
		var specids = qd.speclogid[0].split(",");
		var k;
		for (k=0; k<specids.length;k++)
		{

			var wavelengths;
			$('body').append("<div id=\"chart_container"+k+"\" class=\"chart_container\"><div id=\"chart"+k+"\" class=\"chart\"></div><div id=\"legend"+k+"\" class=\"legend\"></div></div>");
			
			$.getJSON("getSpectralLogSamplingPoints.html?speclogid="+specids[k],function(specwvldata) {
				wavelengths = specwvldata["wavelengths"].split(",");
			
			$.ajax({
					url: "getspectraldata.html?speclogid="+specids[k]+"&startsampleno="+qd.startsampleno+"&endsampleno="+qd.endsampleno,
					type: "GET",
					crossDomain: true,
					responseType:"arraybuffer",
					dataType:"arraybuffer",
					processData: false,
					success: drawgraph(k,wavelengths)});
			});
		}
	});

	</script>
<style>
.chart_container
{
	position: relative;
    width: 1460px;
}
.legend
{
position:absolute;
top:10;
right:10;
}
</style>
</head>
<body>
</body>
</html>