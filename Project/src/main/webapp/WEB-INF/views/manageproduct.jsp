<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Products</title>
</head>
<body>
	
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<div align="right">
	<a href = "${contextPath}/">Logout</a>&emsp;
	</div>	
	<h1>Manage Products</h1>
	
	<a href = "${contextPath}/manager/manageproduct/addproduct.htm">Create Product</a><br></br>
	<a href = "${contextPath}/manager/manageproduct/editproduct.htm">Edit Product</a>
	
</body>
</html>