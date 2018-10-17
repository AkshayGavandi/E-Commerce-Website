<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Customer</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div align="right">
	<a href = "${contextPath}/">Logout</a>&emsp;
	</div>	
	<h1>View Customers</h1>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<table border="1">
    <tr>
	    <th>ID</th>
	    <th>UserName</th>
	    <th>Email</th>
	    <th>Status</th>
	    <th>Password</th>
	</tr>
    <c:forEach items="${requestScope.customerList}" var="p">
    	<tr>
    		<td><c:out value="${p.getId()}"/></td>
    		<td><c:out value="${p.getUserName()}"/></td>
    		<td><c:out value="${p.getUserEmail()}"/></td>
    		<td><c:out value="${p.getStatus()}"/></td>
    		<td><c:out value="${p.getPassword()}"/></td>
    		<td><a href="${pageContext.request.contextPath}/manager/managecustomer/editcustomer.htm?id=<c:out value="${p.getId()}"/>&name=<c:out value="${p.getUserName()}"/>&email=<c:out value="${p.getUserEmail()}"/>&password=<c:out value="${p.getPassword()}"/>&status=<c:out value="${p.getStatus()}"/>">Edit</a></td>
    	</tr>
    </c:forEach>
    </table>
</body>
</html>