<%@page import="net.sf.json.JSONArray"%>
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
    
    <title>My JSP 'wrongrefer.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  		<% 
		    Map<String,Province> provinceMap=Utils.getProvinceMap();
			List<Province> provinces=new ArrayList<Province>();
			for(String key:provinceMap.keySet()){
				provinces.add(provinceMap.get(key));
			}
			String provincesStr= JSONArray.fromObject(provinces).toString();
			pageContext.setAttribute("provincesStr", provincesStr);
 		%>
  <body>
  	<h3 align="center">Submit Error Info</h3>
    <table align="center" cellspacing="0" cellpadding="12" border="1" id="wrongtable">
    	<thead>
    		<td align="center">Name</td>
    		<td align="center">Gender</td>
    		<td align="center">Age</td>
    		<td align="center">Address</td>
    		<td align="center">Tel No</td>
    		<td align="center">Work Days</td>
    		<td align="center">Leave Days</td>
    		<td align="center">Salary</td>
    		<td align="center">Department</td>
    		<td align="center">Error Info</td>
    	</thead>
		<tbody>
			<c:forEach items="${sessionScope.wrongEmployees }" var="emp">
				<tr id="${emp.id }" >
					<td class="name" cellname="name"  align="center">${emp.name}</td>
					<td class="sex" cellname="sex"  align="center">${emp.sex}</td>
					<td class="age" cellname="age"  align="center">${emp.age}</td>
					<td class="place" cellname="place"  align="center">${emp.province}${emp.city}</td>
					<td class="phone" cellname="phone"  align="center">${emp.phone}</td>
					<td class="dutytime" cellname="dutytime"  align="center">${emp.dutytime}</td>
					<td class="offtime" cellname="offtime"  align="center">${emp.offtime}</td>
					<td class="salary" cellname="salary"  align="center">${emp.salary}</td>
					<c:choose>
						<c:when test="${empty emp.department }">
							<td cellname="department" align="center">No Error Info</td>
						</c:when>
						<c:otherwise>
							<td cellname="department" align="center">${emp.department.name}</td>
						</c:otherwise>
					</c:choose>
					<td class="wrongMessage" cellname="wrongMessage"  align="center" style="color:red">${emp.wrongMessage}</td>
				</tr>
			</c:forEach>
		</tbody>
    </table>
  </body>
  	  <script type="text/javascript">
  			var provincesStr='<%=(String)pageContext.getAttribute("provincesStr") %>';
  	  </script>
  	  <script type="text/javascript" src="jses/jquery-1.4.2.js"></script>
	  <script type="text/javascript" src="jses/wrongrefer.js"></script>
</html>
