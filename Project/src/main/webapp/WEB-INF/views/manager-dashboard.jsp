<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manager Dashboard</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<div align="right">
	<a href = "${contextPath}/">Logout</a>&emsp;
	</div>	

	<h1>Manager Dashboard</h1>
	<p>Welcome ${userName}</p><br><br>
	<p>${sessionScope.Role} Role<p>

	<a href = "${contextPath}/manager/manageproduct.htm">Manage Product</a>&emsp;<br><br>
	<a href = "${contextPath}/manager/managecustomer.htm">Manage Customer</a>&emsp;<br><br>
	<a href = "${contextPath}/manager/vieworders.htm">View Orders</a>&emsp;<br><br>
</body>
</html>