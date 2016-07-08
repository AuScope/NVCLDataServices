<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2 class="NVCLDSh2">Test Links:</h2>
	<p>Note: These links are for developer testing and will not work if the datastore does not contain the WTB5 test dataset.</p>
	<p><a href="getDatasetCollection.html?holeidentifier=WTB5">Datasets for WTB5 hole</a></p>
	<p><a href="getLogCollection.html?datasetid=6dd70215-fe38-457c-be42-3b165fd98c7&mosaicsvc=yes">Image log ids</a></p>
	<p><a href="getLogCollection.html?datasetid=6dd70215-fe38-457c-be42-3b165fd98c7&mosaicsvc=no">Log ids</a></p>
	<p><a href="mosaic.html?logid=63d31981-bd0c-44dd-a118-6a3406c5d68">Mosaic</a></p>
	<p><a href="mosaic.html?logid=e8218563-e5af-48ac-a643-3f639a11c3f&width=2&startsampleno=0&endsampleno=3">4 Tray Thumbnail Images display in 2 column</a></p>
	<p><a href="mosaic.html?logid=05a35054-da3e-46ac-9a57-81b6afdf0c9&startsampleno=0&endsampleno=0">First Tray Picture</a></p>
	<p><a href="mosaic.html?logid=fae8f90d-2015-4200-908a-b30da787f01&startsampleno=0&endsampleno=10&width=1&showdepths=true">First 10 samples of linescan with depths shown</a></p>
	<p><a href="mosaictraythumbnail.html?datasetid=6dd70215-fe38-457c-be42-3b165fd98c7&logid=e8218563-e5af-48ac-a643-3f639a11c3f">generated mosaic with links</a></p>
	<p><a href="Display_Tray_Thumb.html?logid=63d31981-bd0c-44dd-a118-6a3406c5d68&amp;sampleno=0">Image from database</a></p>
    <p><a href="Display_Tray_Thumb.html?logid=e8218563-e5af-48ac-a643-3f639a11c3f&amp;sampleno=4">Ttray thumbnail image from database</a></p>
	<p><a href="getImageTrayDepth.html?logid=05a35054-da3e-46ac-9a57-81b6afdf0c9">Tray numbers with start and end depths</a></p>
	<p><a href="plotscalar.html?logid=77d0b966-d936-4b9d-bc5c-4b2b12bf1c5&graphtype=1">plot 1 scalar</a></p>
	<p><a href="plotscalar.html?logid=132558b8-184d-4c73-96a3-92bee7878ad">plot float type scalar</a></p>
	<p><a href="plotscalar.html?logid=77d0b966-d936-4b9d-bc5c-4b2b12bf1c5&graphtype=1&legend=0">plot without legend</a></p>
	<p><a href="plotscalar.html?logid=77d0b966-d936-4b9d-bc5c-4b2b12bf1c5&graphtype=3">plot as line chart</a></p>
	<p><a href="plotmultiscalars.html?logid=84346b38-5002-412e-8450-6ba79d38c94&logid=77d0b966-d936-4b9d-bc5c-4b2b12bf1c5&logid=a455603e-17a9-4ba0-bc6b-4866f14a58e&logid=132558b8-184d-4c73-96a3-92bee7878ad">plot multiple scalars</a></p>
	<p><a href="downloadscalars.html?logid=84346b38-5002-412e-8450-6ba79d38c94&logid=77d0b966-d936-4b9d-bc5c-4b2b12bf1c5&logid=a455603e-17a9-4ba0-bc6b-4866f14a58e&logid=132558b8-184d-4c73-96a3-92bee7878ad">Download scalars as csv</a></p>

</body>
</html>