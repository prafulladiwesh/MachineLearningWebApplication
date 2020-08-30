<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<spring:url value="/resources/mystyle.css" var="myCSS" />
<spring:url value="/resources/myjs.js" var="myJS" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link href="${myCSS}" type="text/css" rel="stylesheet" />
<script src="${myJS}"></script>
<title>Home</title>
</head>
<body>
	<div style="text-align: center;">
		<h1>Flight Customer Forecast System</h1>
		<h3>Upload CSV File</h3>
		<form action="/SpringMVCApplication/uploadFile" method="POST"
			enctype="multipart/form-data">
			<table>
				<tr>
					<td><label>Upload file</label></td>
					<td><input type="file" name="file" required="true"></td>
				</tr>
				<tr>
					<td><label>File Name:</label></td>
					<td>
					<input type="text" name="name"></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Submit"></td>
				</tr>
			</table>
		</form>
		<div id="jsMessage"></div>
	</div>
</body>
</html>