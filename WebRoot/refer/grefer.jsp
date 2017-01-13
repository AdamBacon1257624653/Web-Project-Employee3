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
    
    <title>Grefer</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


 
  </head>
  
  <body  style="background: rgb(237 ,224 ,230);">
  <h1 style="text-align: center;">Department Query</h1>
                <%-- 部门号；${sessionScope.session_user.name}
                部门名；${sessionScope.session_user.name}
                员工名；${sessionScope.session_user.name}
                部门职务 ；${sessionScope.session_user.name}
                部门数量；${sessionScope.session_user.name}--%>
  </body>
</html>
