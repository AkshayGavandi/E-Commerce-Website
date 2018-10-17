<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>View Products</h1>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<table border="1">
    <tr>
	    <th>Code</th>
	    <th>Name</th>
	    <th>Price</th>
	    <th></th>
	</tr>
    <c:forEach items="${requestScope.productList}" var="p" varStatus="product">
    	<tr>
    		<td><c:out value="${p.getCode()}"/></td>
    		<td><c:out value="${p.getName()}"/></td>
    		<td><c:out value="${p.getPrice()}"/></td>
    		<td><a href="${pageContext.request.contextPath}/manager/manageproduct/editproduct/edit-selected-product.htm?code=<c:out value="${p.getCode()}"/>&name=<c:out value="${p.getName()}"/>&price=<c:out value="${p.getPrice()}"/>">Edit</a></td>
    	</tr>
    </c:forEach>
    </table>
</body>
</html>