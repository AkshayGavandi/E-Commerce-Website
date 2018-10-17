<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Customer ${id}</title>
<script type="text/javascript">
      function validate()
      {
         if( document.myForm.status.value == "" ||
         isNaN( document.myForm.status.value ) )
         {
            alert( "Please enter numbers only" );
            document.myForm.status.focus() ;
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
	<form action="${contextPath}/manager/managecustomer/editcustomer/customer-edit-success.htm" method="POST" name="myForm" onsubmit="return(validate());">
		<table>
		<tr>
		    <td>User ID:</td>
		    <td><input type="text" name="id" size="30" value="${id}" required="required" readonly="readonly"/></td>
		</tr>
		<tr>
		    <td>Customer Name:</td>
		    <td><input type="text" name="name" size="30" value="${name}" required="required" /></td>
		</tr> 
		<tr>
		    <td>Email address:</td>
		    <td><input type="text" name="email" size="30" value="${email}" required="required"/></td>
		</tr>
		<tr>
		    <td>Status:</td>
		    <td><input type="text" name="status" size="30" value="${status}" required="required" /></td>
		</tr> 
		<tr>
		    <td>Password:</td>
		    <td><input type="text" name="password" size="30" value="${password}" required="required"/></td>
		</tr>
		
		<tr>
		    <td colspan="2"><input type="submit" value="Save" /></td>
		</tr>
				
		</table>
	</form>
</body>
</html>