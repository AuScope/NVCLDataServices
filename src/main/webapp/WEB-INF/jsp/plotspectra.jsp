
<%@ include file="include.jsp" %>
<%-- <%@ page contentType="text/html;charset=UTF-8" language="java" %> --%>

<!-- <html> -->
<!-- <head> -->
<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> -->
<!-- 	<title>Spectral Data Plot</title> -->

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

	//var floats = new Float32Array(result);
	var spectracount = result.length// result.byteLength/(4*wavelengths.length);
	var palette = new Rickshaw.Color.Palette( { scheme: 'munin' } );
	for (var j=0;j<spectracount;j++)
	{
		var d1 =[];
		
		for (var i = 0; i < result[j].floatspectraldata.length; i++) {
			d1.push({x:parseInt(wavelengths[i]), y: result[j].floatspectraldata[i]});
		}
		data.push({data:d1,name:"Reflectance Sample "+((j-Math.floor(spectracount/2))>0? "+"+ (j-Math.floor(spectracount/2)):(j-Math.floor(spectracount/2))) ,color:palette.color()});
	}
	var graph = new Rickshaw.Graph( {
		element: document.getElementById("chart"+${msgMap['startSampleNo']}+"_"+k),
		width: ${msgMap['width']},
		height: ${msgMap['height']},
		renderer: 'line',
		padding: {top: 0.05, left: 0.05, right: 0.05, bottom: 0.05},
		series: data,
		min: 'auto'
	} );

	var y_axis = new Rickshaw.Graph.Axis.Y( {
		graph: graph,
		tickFormat:d3.format('.2f')
	} );
	var x_axis = new Rickshaw.Graph.Axis.X( {
        graph: graph,
        tickFormat:d3.format('.0f')
    } );
	var legend = new Rickshaw.Graph.Legend( {
        element: document.querySelector("#legend"+${msgMap['startSampleNo']}+"_"+k),
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
		
		var specids= ${msgMap['logId']};

		var k;
		for (k=0; k<specids.length;k++)
		{

			var wavelengths;
			$('#plotspectra'+${msgMap['startSampleNo']}).append("<div id=\"chart_container"+${msgMap['startSampleNo']}+"_"+k+"\" class=\"chart_container\"><div id=\"chart"+${msgMap['startSampleNo']}+"_"+k+"\" class=\"chart\"></div><div id=\"legend"+${msgMap['startSampleNo']}+"_"+k+"\" class=\"legend\"></div></div>");
			
			$.getJSON("getSpectralLogSamplingPoints.html?speclogid="+specids[k],function(specwvldata) {
				wavelengths = specwvldata["wavelengths"].split(",");

				$.getJSON("getspectraldata.html?speclogid="+specids[k]+"&startsampleno="+${msgMap['startSampleNo']}+"&endsampleno="+${msgMap['endSampleNo']}+"&outputformat=json", drawgraph(k,wavelengths));
 			});
			
		}
	});

	</script>
<style>
.chart_container
{
	position: relative;
    width: ${msgMap['width']}px;
}
.legend
{
position:absolute;
top:10;
right:10;
display:none;
}
</style>
<div id="plotspectra${msgMap['startSampleNo']}"></div>

<!-- </head>
<body style="overflow:hidden">
</body>
</html>-->