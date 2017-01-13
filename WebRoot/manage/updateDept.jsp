<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'alter.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="jses/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="jses/updateDept.js"></script>
  </head>
  <%
  		
  %>
  <body>
    <h3 align="center">Employees information in departmentss</h3>
    <table align="center" cellspacing="0" cellpadding="12" border="1" id="alltable">
    		<tr id="th" bordercolor="rgb(78,78,78)">
    		<th>Department Name</th>
    		<th>Employee Amount</th>
    		<th>Delete</th>
    	</tr>
    	<tbody>
	    	<c:forEach items="${deptList }" var="dept">     
		    	<tr id="${dept.id }" bordercolor="rgb(78,78,78)">
					<td cellname="name" align="center" class="name" >${dept.name}</td>
					<td align="center" >${dept.empCount}Employees</td>
					<td align="center" class="delete" style="color:blue;font-weight:900">Delete</td>
		    	</tr>
	       </c:forEach>
       </tbody>
    </table>
  </body>
</html>
