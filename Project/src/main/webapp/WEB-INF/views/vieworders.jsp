<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Orders</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div align="right">
	<a href = "${contextPath}/">Logout</a>&emsp;
	</div>	
	<h1>Review Orders</h1>
	
	<table border="1">
    <tr>
	    <th>ID</th>
	    <th>Order Items</th>
	    <th>Status</th>
	    <th></th>
	  
	</tr>
	
    <c:forEach items="${requestScope.orderlist}" var="p">
    	<tr>
    		<td><c:out value="${p.getOrderNumber()}"/></td>
    		<td><c:out value="${p.getOrderItems()}"/></td>
    		<td><c:out value="${p.getStatus()}"/></td>
    		<td><a href="${pageContext.request.contextPath}/manager/vieworders/approveorder.htm?id=<c:out value="${p.getOrderNumber()}"/>">Approve</a></td>
    	</tr>
    </c:forEach>
    </table>
    
</body>
</html>