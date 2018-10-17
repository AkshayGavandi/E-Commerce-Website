<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Products</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div align="right">
	<a href="${pageContext.request.contextPath}/customer/products/viewcart.htm?action=view">View Cart</a>&emsp;
	<a href = "${contextPath}/">Logout</a>&emsp;
	</div>	
	<h1>Your Products</h1>
	
	<table border="1">
    <tr>
	    <th>Code</th>
	    <th>Name</th>
	    <th>Price</th>
	    <th></th>
	  
	</tr>
    <c:forEach items="${requestScope.productList}" var="p">
    	<tr>
    		<td><c:out value="${p.getCode()}"/></td>
    		<td><c:out value="${p.getName()}"/></td>
    		<td><c:out value="${p.getPrice()}"/></td>
    		<td><a href="${pageContext.request.contextPath}/customer/products/viewcart.htm?code=<c:out value="${p.getCode()}"/>&name=<c:out value="${p.getName()}"/>&price=<c:out value="${p.getPrice()}"/>&action=add">Add to Cart</a></td>
    	</tr>
    </c:forEach>
    </table>
    
    
</body>
</html>