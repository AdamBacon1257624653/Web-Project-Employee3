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
    
    <title>My JSP 'd-add.jsp' starting page</title>
    
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
	<script type="text/javascript" src="jses/addDept.js"></script>
  <body>
 <h2 style="text-align: left;">Add Department</h2>
	Department Name：<input type="text" id="name" value="${form.dname}"/>
	<span style="color: red; font-weight: 900" class="namespan"></span>
	<br/><br/>
	<input type="button" id="addBtn" value="Add"/>
  </body>
</html>
