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
    
    <title>My JSP 'dquery.jsp' starting page</title>
    
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
	  <script type="text/javascript" src="jses/deptquery.js"></script>
  <body>
  	<form>
		Department Name Keyword：<input type="text" id="NameKeyWord"/>
		<span id="keywordError"  style="color: red; font-weight: 900"></span>
		<br/><br/>
		<input id="QueryBtn" type="button" value="Query" style="width:100"/>&nbsp;&nbsp;&nbsp;
		<input type="reset" value="Reset" style="width:100"/>
	</form>
  </body>
</html>
