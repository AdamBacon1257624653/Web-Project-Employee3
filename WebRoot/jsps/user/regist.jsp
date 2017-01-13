<%@page import="cn.itcast.domain.database.Employee"%>
<%@page import="cn.itcast.domain.Province"%>
<%@page import="cn.itcast.Util.Utils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Register</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	
	<script type="text/javascript">
function _change(){
   //Retrieve img element，Modify src to /employee3/VerifyCodeServlet
   var imgEle=document.getElementById("img");
   imgEle.src="/employee3/VerifyCodeServlet?a="+ new Date().getTime();
}
    
</script>
<style type="text/css">
	body {
	    background: #E6E6FA; 
    }
    </style>
  </head>
  	<script type="text/javascript" src="../../jses/jquery-1.4.2.js"></script>
	  <script type="text/javascript" src="../../jses/regist.js"></script>
  <body>
  <h1>Welcome to Register Page</h1>
 <%--
  Display errors
  
   --%>
   
   <%
    /*
    */
    String uname="";
    Cookie[] cs=request.getCookies();
    if(cs!=null){
    for(Cookie c:cs){
    if("uname".equals(c.getName())){
      uname=c.getValue();
    }
    }
    } 
     %>
    <%
    String message="";
    String msg=(String)request.getAttribute("msg"); 
    if(msg!=null){
         message=msg;
    }
     %>
     <!-- Province -->
   <%
   		Map<String,Province> provinceMap=Utils.getProvinceMap();
   		List<Province> provinces=new ArrayList<Province>();
   		for(String key:provinceMap.keySet()){
   			provinces.add(provinceMap.get(key));
   		}
   		pageContext.setAttribute("provinces", provinces);
   %>
<p style="color: red; font-weight: 900">${msg }</p>
<form action="<c:url value='/UserServlet'/>" method="post">
	<input type="hidden" name="method" value="regist"/>
	Name：<input type="text" name="name" value="${form.name}"/>
	<span style="color: red; font-weight: 900">${errors.name }</span>
	<br/><br/>
	Password：<input type="password" id="password" name="password" value="${form.password}"/>
	<span style="color: red; font-weight: 900">${errors.password }</span>
	<br/><br/>
	Gender：
		<input type="checkbox" name="sex" value="male" id="malecb" checked="true" onclick="malecheck()">&nbsp;Male&nbsp;
		<input type="checkbox" name="sex" value="female" id="femalecb" onclick="femalecheck()">&nbsp;Female
	<span style="color: red; font-weight: 900">${errors.sex }</span>
	<br/><br/>
	Age：<input type="text" id="age" name="age" value="${form.age}"/>
	<span style="color: red; font-weight: 900">${errors.age }</span><br/><br/>
	Address：Province<select id="province" name="province" value="${form.province}">
				<c:choose>
					<c:when test="${not empty form.province}">
						<option value="${form.province}">${form.province}</option>
					</c:when>
					<c:otherwise>
						<option value="">==Options==</option>
					</c:otherwise>
				</c:choose>
				<c:forEach items="${provinces}" var="province">
					<option value="${province.name}">${province.name}</option>
				</c:forEach>
	       </select>&nbsp;&nbsp;&nbsp;&nbsp;
	     Cities<select id="city" name="city" >
	             	<option value="${form.city}">${form.city}</option>
	      </select>
	      <span style="color: red; font-weight: 900">${errors.place }</span>
	     <br/><br/>
	Department：<select id="department" name="dno">
			<option value="0">Financial Department</option>
			<option value="1">Programmer Department</option>
			<option value="2">Phone Department</option>
			<option value="3">Sales Department</option>
			<option value="4">Marketing Department</option>
			<option value="5">IT Department</option>
		 </select>
		 <br/><br/>
	Phone Number：+86<input type="text" id="phone" name="phone" value="${form.phone}"/>
	<span style="color: red; font-weight: 900">${errors.phone }</span>
	<br/><br/>
	Verify Code:<input type="text" id="veriryCode" name="verifyCode" size="3"/>
              <img id="img" src="/employee3/VerifyCodeServlet"/>
              <a href="javascript:_change()">Refresh</a>
              <input id="verifyimage" value="" type="image"/>
              <span id="verifycodespan" style="color: red; font-weight: 900">${errors.verifycode }</span>
            </br><br/>
	<input type="submit" value="Register" style="width:100"/>&nbsp;&nbsp;&nbsp;
	<input type="reset" value="Reset" style="width:100"/>
</form>
  </body>
  <script type="text/javascript">
	  var malecb = document.getElementById("malecb");
	  var femalecb = document.getElementById("femalecb");
	  malecheck = function(){
	      if (!malecb.checked) {
	          malecb.checked = false;
	          femalecb.checked = true;
	      }
	      else {
	          malecb.checked = true;
	          femalecb.checked = false;
	      }
	  }
	  femalecheck = function(){
	      if (femalecheck.checked) {
	          malecb.checked = true;
	          femalecb.checked = false;
	      }
	      else {
	          malecb.checked = false;
	          femalecb.checked = true;
	      }
	  }
  </script>
</html>

