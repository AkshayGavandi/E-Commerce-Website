<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Product</title>
</head>
<body>
	<h1>Add Product</h1>
	
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	
	<form action="${contextPath}/customer/managecustomerproduct/addcustomerproduct/addcpsuccess.htm" method="POST">
		<table>
		<tr>
		    <td>Product Code:</td>
		    <td><input type="text" name="code" size="30" required="required" /></td>
		</tr>
		<tr>
		    <td>Product Name:</td>
		    <td><input type="text" name="name" size="30" required="required" /></td>
		</tr>
		
		<tr>
		    <td>Price:</td>
		    <td><input type="text" name="price" size="30" required="required"/></td>
		</tr>
				
		</table>
		<input type="submit" value="Create">
	</form>
</body>
</html>