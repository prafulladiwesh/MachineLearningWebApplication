<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<spring:url value="/resources/mystyle.css" var="myCSS" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link href="${myCSS}" type="text/css" rel="stylesheet" />
<title>Results</title>
</head>
<body>
	<h1 style="text-align: center;">Prediction Output</h1>
	<div id="formBorder">
		<table style="text-align: center;">
			<tr>
				<td>${result}<br><br></td>
			</tr>
			<tr>
				<td><a href="https://chart-studio.plotly.com/~prafulladiwesh/16/">Show Visual Plot</a></td>
			</tr>
		</table>
	</div>
</body>
</html>