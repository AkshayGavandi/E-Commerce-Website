<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div align="right">
	<a href="${pageContext.request.contextPath}/customer/products.htm">Back to Products</a>&emsp;
	<a href = "${contextPath}/">Logout</a>&emsp;
	</div>	

	<h2>Cart</h2>
    
    <table border="1">
    <tr>
	    <th>Code</th>
	    <th>Name</th>
	    <th>Price</th>
	    <th></th>
	    
	</tr>
    <c:forEach items="${requestScope.cartList}" var="p">
    	<tr>
    		<td><c:out value="${p.getCode()}"/></td>
    		<td><c:out value="${p.getName()}"/></td>
    		<td><c:out value="${p.getPrice()}"/></td>
    		
    		<td><a href="${pageContext.request.contextPath}/customer/products/viewcart/removesuccess.htm?code=<c:out value="${p.getCode()}"/>">Remove</a></td>
    	</tr>
    </c:forEach>
    </table>
    
    <c:forEach items="${requestScope.cartList}" var="currentItem" varStatus="stat">
  		<c:set var="orderlist" value="${stat.first ? '' : orderlist} ${currentItem}" />
  		
	</c:forEach>
    
    <br></br/>
    
    
    <a href="${pageContext.request.contextPath}/customer/products/viewcart/orderplaced.htm?orderlist=<c:out value="${orderlist}"/>">Place Order</a>
    
</body>
</html>