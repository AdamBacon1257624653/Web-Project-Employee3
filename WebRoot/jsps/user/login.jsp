<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Log in</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->



<style type="text/css">
	body {
	    background: #F0F8FF; 
    }
    </style>
  </head>
  
  <body>
  <h1>Welcome to Login</h1>
<p style="color: red; font-weight: 900">${msg }</p>
<form action="<c:url value='/UserServlet'/>" method="post">
     <input type="hidden" name="method" value="login"/>
	Employee Name：<input type="text" name="name" value="${form.name }"/><br/><br/>
	Password：<input type="password" name="password" value="${form.password }"/><br/><br/>
	<input type="submit" value="login" style="width:100"/>&nbsp;&nbsp;&nbsp;
	<input type="reset" value="reset" style="width:100"/>
</form>
  </body>
</html>
