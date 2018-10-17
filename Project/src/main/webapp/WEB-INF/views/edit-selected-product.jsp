<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Product ${code}</title>
<script type="text/javascript">
      function validate()
      {
         if( document.myForm.price.value == "" ||
         isNaN( document.myForm.price.value ) )
         {
            alert( "Please enter numbers only" );
            document.myForm.price.focus() ;
            return false;
         }
      }
</script>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div align="right">
	<a href = "${contextPath}/">Logout</a>&emsp;
	</div>	
	<font color="red">${errorMessage}</font>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<form action="${contextPath}/manager/manageproduct/editproduct/edit-selected-product/product-edit-success.htm" method="POST" name="myForm" onsubmit="return(validate());">
		<table>
		<tr>
		    <td>Product Code:</td>
		    <td><input type="text" name="code" size="30" value="${code}" required="required" readonly="readonly"/></td>
		</tr>
		<tr>
		    <td>Product Name:</td>
		    <td><input type="text" name="name" size="30" value="${name}" required="required" /></td>
		</tr> 
		<tr>
		    <td>Price:</td>
		    <td><input type="text" name="price" size="30" value="${price}" required="required"/></td>
		</tr>
		
		<tr>
		    <td colspan="2"><input type="submit" value="Save" /></td>
		</tr>
				
		</table>
	</form>
</body>
</html>