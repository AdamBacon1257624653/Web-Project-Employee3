<%@page import="net.sf.json.JSONArray"%>
<%@page import="cn.itcast.domain.Province"%>
<%@page import="cn.itcast.Util.Utils"%>
<%@page import="cn.itcast.domain.Page"%>
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
    
    <title>My JSP 'all.jsp' starting page</title>
    
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
		background: #FFFAF0;
	}
	</style>
  </head>
  <body>
    <h3 align="center">All Employees</h3>
    <table align="center" cellspacing="0" cellpadding="12" border="1" id="alltable">
    	<thead>
    		<td  align="center">Name</td>
    		<td  align="center">Gender</td>
    		<td  align="center">Age</td>
    		<td  align="center">Address</td>
    		<td  align="center">Tel no</td>
    		<td  align="center">Working Days</td>
    		<td  align="center">Leave Days</td>
    		<td  align="center">Salary</td>
			<td  align="center">Department</td>
			<td  align="center">Salary Rank</td>
			<td  align="center">Delete</td>
    	</thead>
		<tbody>
			<c:forEach items="${sessionScope.page.pageEmployees }" var="emp">
				<tr id="${emp.id }" >
					<td cellname="name" align="center">${emp.name}</td>
					<td cellname="sex" align="center">${emp.sex}</td>
					<td cellname="age" align="center">${emp.age}</td>
					<td cellname="place" pro="${emp.province}" city="${emp.city}" align="center">${emp.province}${emp.city}</td>
					<td cellname="phone" align="center">${emp.phone}</td>
					<td cellname="dutytime" align="center">${emp.dutytime}</td>
					<td cellname="offtime" align="center">${emp.offtime}</td>
					<td cellname="salary" align="center">${emp.salary}</td>
					<c:choose>
						<c:when test="${empty emp.department }">
							<td cellname="department" align="center">No result</td>
						</c:when>
						<c:otherwise>
							<td cellname="department" align="center">${emp.department.name}</td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${empty emp.sal }">
							<td cellname="salname" align="center">No result</td>
						</c:when>
						<c:otherwise>
							<td cellname="salname" align="center">${emp.sal.salname}</td>
						</c:otherwise>
					</c:choose>
					<td cellname="delete" align="center"><label style="color:blue" class="delete">Delete</label></td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
	<br/>
	<div id="pageindexdiv" align="center">
	</div>
  </body>
  <% 
	    Map<String,Province> provinceMap=Utils.getProvinceMap();
		List<Province> provinces=new ArrayList<Province>();
		for(String key:provinceMap.keySet()){
			provinces.add(provinceMap.get(key));
		}
		String provincesStr= JSONArray.fromObject(provinces).toString();
		pageContext.setAttribute("provincesStr", provincesStr);
  %>
  <script type="text/javascript">
  	var pageCount='<%=((Page)request.getSession().getAttribute("page")).getPageCount()%>';
  	var currentpage='<%=((Page)request.getSession().getAttribute("page")).getCurrentPage()%>';
  	var provincesStr='<%=(String)pageContext.getAttribute("provincesStr") %>';
  	var linkClr="#0000FF";
	var disabledClr="#000000";
	var pagedivinnerhtml="<span class='pagechange' id='previousspan' style='color:"+linkClr+"; font-size:20; text-decoration:underline'>上一页</span>&nbsp;&nbsp;&nbsp;";
	for(var i=1;i<=pageCount;i++){
		var clr=disabledClr;
		if(i==1){
			clr=linkClr;
		}
		pagedivinnerhtml+="<label class='pagechange' pagename='pagename' id='page"+i+"' style='color:"+clr+"; font-size:20; text-decoration:underline'>"+i+"</label>&nbsp;&nbsp;&nbsp;";
	}
	pagedivinnerhtml+="<span class='pagechange' id='nextspan' style='color:"+linkClr+"; font-size:20; text-decoration:underline'>下一页</span>";
	document.getElementById("pageindexdiv").innerHTML+=pagedivinnerhtml;
	//增加当前所显示的页面
	function addCurrentPage(){
		currentpage++;
	}
	//减小当前所显示的页面
	function minusCurrentPage(){
		currentpage--;
	}
	//重置表格
	function resetTable(){
	}
  </script>
  <script type="text/javascript" src="jses/jquery-1.4.2.js"></script>
  <script type="text/javascript" src="jses/my.js"></script>
</html>
