<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'sadd.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <h2 style="text-align:left;">Add Salary</h2>
   <form action="<c:url value='/SalServlet'/>" method="post">
    <input type="hidden" name="method" value="dadd"/>
	Salary No:<input type="text" name="id" value="${form.id}"/>
	<span style="color: red; font-weight: 900" class="id"></span>&nbsp;&nbsp;&nbsp;
	Employee Name<input type="text" name="name" value="${form.name}"/>
	<span style="color: red; font-weight: 900" class="name"></span><br/><br/>
	Department Name<input type="text" name="name" value="${form.dname}"/>
	<span style="color: red; font-weight: 900" class="dname"></span>&nbsp;&nbsp;&nbsp;
	Basic Salary：<input type="text" name="id" value="${form.bassal}"/>
	<span style="color: red; font-weight: 900" class="bassal"></span>
	<br/><br/>
	Rewards:<input type="text" name="id" value="${form.bonus}"/>
	<span style="color: red; font-weight: 900" class="bonus"></span>&nbsp;&nbsp;&nbsp;
	Salary Time：<input type="text" name="id" value="${form.saldate}"/>
	<span style="color: red; font-weight: 900" class="saldate"></span>
	<br/><br/>
	Leave Days：<input type="text" name="id" value="${form.leaveworkTime}"/>
	<span style="color: red; font-weight: 900" class="leaveworkTime"></span>&nbsp;&nbsp;&nbsp;
	Work days：<input type="text" name="id" value="${form.workTime}"/>
	<span style="color: red; font-weight: 900" class="workTime"></span>
	<br/><br/>
	Deduction：<input type="text" name="id" value="${form.deduct}"/>
	<span style="color: red; font-weight: 900" class="deduct"></span>&nbsp;&nbsp;&nbsp;
	Final Salary：<input type="text" name="id" value="${form.factsal}"/>
	<span style="color: red; font-weight: 900" class="factsal"></span>
	<br/><br/> 
	<input class="btn" type="button" value="Add" style="width:100"/>&nbsp;&nbsp;&nbsp;
	<input type="reset" value="Reset" style="width:100"/>
	</form>
  </body>
</html>
