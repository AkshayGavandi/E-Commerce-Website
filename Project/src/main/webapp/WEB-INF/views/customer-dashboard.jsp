<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Customer Dashboard</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div align="right">
	<a href = "${contextPath}/">Logout</a>&emsp;
	</div>	
	<h1>Customer Dashboard</h1>
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<p>Welcome ${userName}</p>
	
	<a href = "${contextPath}/customer/products.htm">Browse Products</a><br></br>
	
</body>
</html>