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
    
    <title>Employee Query System</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="../WEB-INF/jses/jquery.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


    <style type="text/css">
body {
	background-image: url(image/2.jpg);background-repeat:no-repeat;width:800px;height:850px; 
}
</style>
	<style type="text/css">
		div#a{
			display:none;
		}
    </style>
  </head>
  
  <body>
     <h1 style="text-align: center;">Employee Query System</h1>
  		Name：${sessionScope.session_user.name}</br>
		Gender：${sessionScope.session_user.sex}<br/>
		Age：${sessionScope.session_user.age}<br/>
		Address：${sessionScope.session_user.province}${sessionScope.session_user.city}<br/>
		Tel no：${sessionScope.session_user.phone}<br/>
		Salary：${sessionScope.session_user.salary}<br/>
                  
                 
	<button id="btn" onclick="inputWrong()">Error Info Exists</button>
	<div id="a">
		<form method="post" action="<c:url value='/EmpServlet'/>">
			<input type="hidden" name="method" value="submitWrong"/>
			<label>Error Info：</label> <input type="text" name="wrongMessage">
			<input type="submit" value="Submit">
		</form>
	</div>
  </body>
  <script type="text/javascript">
  	var isRight=false;
  	inputWrong=function(){
		if(!isRight){
			document.getElementById("a").style.display="inherit";
			document.getElementById("btn").innerHTML="No Error Info";
		}else{
			document.getElementById("a").style.display="none";
			document.getElementById("btn").innerHTML="Error Info Exists";
		}
		isRight=!isRight;
	};
  </script>
</html>
