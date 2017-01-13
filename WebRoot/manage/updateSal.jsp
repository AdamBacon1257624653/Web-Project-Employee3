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
    
    <title>My JSP 'salter.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
		<script type="text/javascript" src="jses/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="jses/updateSal.js"></script>  
  <body>
  <h2 style="text-align: center;">Employee Salary Info</h2>
    <table align="center" border="1" cellpadding="12" cellspacing="0">
    	<tr id="th" bordercolor="rgb(78,78,78)">
    	    <th>Salary Rank</th>
    		<th>Minimum Salary</th>
    		<th>Maximum Salary</th>
    		<th>Employee Count</th>
    		<th>Delete</th>
    	</tr>
         <c:forEach items="${salList }" var="sal">    
	    	<tr id="${sal.salno}" bordercolor="rgb(78,78,78)">
	    		<td align="center" class="name">${sal.salname }</td>
	    		<td align="center" class="losal">${sal.losal }</td>
	    		<td align="center" class="hisal">${sal.hisal }</td>
	    		<td align="center">${sal.empCount }Employees</td>
	    		<td align="center" class="delete" style="color:blue;font-weight:900">Delete</td>
	    	</tr>
		</c:forEach>
    </table>
  </body>
</html>
