<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>top</title>
    
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
	    background: #87CEFA; 
    }
	a {
		text-transform:none;
		text-decoration:none;
	} 
	a:hover {
		text-decoration:underline;
	}
</style>
  </head>
  
  <body>
<h1 style="text-align: center;">Employee Management System</h1>
<div style="font-size: 15pt;">
   <c:choose>
        <c:when test="${sessionScope.session_user==null }">
         	 <a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent">Log in</a> |&nbsp; 
			 <a href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent">Sign up</a></br>
        </c:when>
        <c:when test="${sessionScope.session_user!=null }">
	        Hello：${sessionScope.session_user.name }  &nbsp;&nbsp;|&nbsp;&nbsp;
	        <c:if test="${sessionScope.session_user.isManager==0}">
	        <a href="<c:url value='/refer/refer.jsp'/>" target="body">Personal Information</a>&nbsp;&nbsp;|&nbsp;&nbsp;
	        </c:if>
			<c:if test="${sessionScope.session_user.isManager==1}">
				<a href="<c:url value='/refer/add.jsp'/>" target="body"></a>
				<a href="<c:url value='/refer/all.jsp'/>" target="body"></a>
			</c:if>
			<a href="<c:url value='/UserServlet?method=quit'/>" target="_parent">Exit</a><br/>
			<c:if test="${wrongCount>0 }"><%-- 员工是否有错误记录的检测 --%>
				<img src="../image/notification.png" width="25" height="20"/>
				<a id="wronga" href="<c:url value='/refer/wrongrefer.jsp'/>" target="body">
					${wrongCount}employees submitted error information, please check it</a>
			</c:if>
	    </c:when>
	</c:choose>
</div>
  </body>
</html>
