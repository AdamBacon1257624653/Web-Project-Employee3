<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'srefer.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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
  <%--  <script type="text/javascript">
  	var isRight=false;
  	inputWrong=function(){
		if(!isRight){
			document.getElementById("a").style.display="inherit";
			document.getElementById("btn").innerHTML="无错误信息";
		}else{
			document.getElementById("a").style.display="none";
			document.getElementById("btn").innerHTML="有错误信息";
		}
		isRight=!isRight;
	};
  </script>--%>
  <body>
  
    <%--  <h3 align="center">工资信息查询</h3>
    <table align="center" cellspacing="0" cellpadding="12" border="1" id="alltable">
    工资号；${sessionScope.session_user.salno}
                 员工名；${sessionScope.session_user.name}
                  基本工资；${sessionScope.session_user.bassal}
                 奖励；${sessionScope.session_user.bonus}
                发工资时间；${sessionScope.session_user.saldate}
                请假时长；${sessionScope.session_user.leaveworkTime}
                工作时长；${sessionScope.session_user.workTime}
                扣除工资；${sessionScope.session_user.deduct}
                实际工资； ${sessionScope.session_user.factsal}
                
                <button id="btn" onclick="inputWrong()">有错误信息</button>
	<div id="a">
		<form method="post" action="<c:url value='/EmpServlet'/>">
			<input type="hidden" name="method" value="submitWrong"/>
			<label>错误信息：</label> <input type="text" name="wrongMessage">
			<input type="submit" value="提交">
		</form>
	</div>--%>
  </body>
</html>
