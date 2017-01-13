<%@page import="cn.itcast.Util.Utils"%>
<%@page import="cn.itcast.domain.Province"%>
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
    
    <title>My JSP 'add.jsp' starting page</title>
    
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
			background: #F5F5F5;
		}
	</style>
	   <!-- 查询省市联动中的省 -->
   <%
   		Map<String,Province> provinceMap=Utils.getProvinceMap();
   		List<Province> provinces=new ArrayList<Province>();
   		for(String key:provinceMap.keySet()){
   			provinces.add(provinceMap.get(key));
   		}
   		pageContext.setAttribute("provinces", provinces);
   %>
  </head>
  	  <script type="text/javascript" src="jses/jquery-1.4.2.js"></script>
	  <script type="text/javascript" src="jses/add.js"></script>
  <body>
   <h2 style="text-align: left;">Add employee</h2>
   <form action="<c:url value=''/>" method="post">
	<input type="hidden" name="method" value="regist"/>
	Employee Name：<input type="text" id="name" name="name" value="${form.name}"/>
	<span style="color: red; font-weight: 900" class="name"></span>
	<br/><br/>
	Password：<input type="password" id="password" name="password" value="${form.password}"/>
	<span style="color: red; font-weight: 900" class="password"></span>
	<br/><br/>
	Gender：
		<input type="checkbox" name="sex" value="男" id="malecb" checked="true" onclick="malecheck()">&nbsp;Male&nbsp;
		<input type="checkbox" name="sex" value="女" id="femalecb" onclick="femalecheck()">&nbsp;Female
	<span style="color: red; font-weight: 900">${errors.sex }</span>
	<br/><br/>
	Age：<input type="text" id="age" name="age" value="${form.age}"/>
	<span style="color: red; font-weight: 900" class="age"></span>
	<br/><br/>
	Address：Provinces<select id="province" name="province" value="${form.place.province}">
				<c:choose>
					<c:when test="${not empty form.place.province}">
						<option value="${form.place.province}">${form.place.province}</option>
					</c:when>
					<c:otherwise>
						<option value="">==Options==</option>
					</c:otherwise>
				</c:choose>
				<c:forEach items="${provinces}" var="province">
					<option value="${province.name}">${province.name}</option>
				</c:forEach>
	       </select>&nbsp;&nbsp;&nbsp;&nbsp;
	             City<select id="city" name="city" >
	            <option value="${form.place.city}">${form.place.city}</option>
	      </select>
	      <span class="city"  style="color: red; font-weight: 900">${errors.place }</span>
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
	Tel：+86<input type="text" id="phone" name="phone" value="${form.phone}"/>
	<span class="phone" style="color: red; font-weight: 900">${errors.phone }</span>
	<br/><br/>
	<input class="btn" type="button" value="Add" style="width:100"/>&nbsp;&nbsp;&nbsp;
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
