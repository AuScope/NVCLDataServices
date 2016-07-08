 <%@ include file="include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <title>NVCL Data Services :: Scanned borehole URI Checker</title>
<!--     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"> -->
<!--     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css"> -->
<!--     <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script> -->
<style>
table.boreholetable, table.boreholetable th, table.boreholetable td {
    border: 1px solid black;
    border-collapse:collapse;
    padding: 5px;
}
</style>
  </head>

  <body>
  <h1>NVCL Data Services :: Scanned borehole URI Checker</h1>
    <c:out value="${checkresults}" escapeXml="false" />

  </body>
</html>
