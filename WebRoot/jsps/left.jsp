<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>left</title>
    <base target="body"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<%--<style type="text/css">
	body {
	    background: #F0F8FF; 
    }
		*{
			font-size:10pt;
			text-align: center;
		}
		div {
			background: #87CEFA; 
			margin: 3px; 
			padding: 3px;
		}
		a {
			text-decoration: none;
		}
	</style>
  </head>
  
  <body>
  <div style="font-size: 15pt;">
  <a href="<c:url value='/refer/add.jsp'/>" target="body">管理员管理系统</a>
</div>
<%--
<div>
	<a href="<c:url value=''/>">管理分类</a>
</div>


<div>
	<a href="<c:url value='/refer/grefer.jsp'/> "target="body">管理员管理</a>
</div><div>
	<a href="<c:url value=''/>">人力资源管理</a>
</div>
<div>
	<a href="<c:url value=''/>">客户信息</a>
</div>
--%>
<script type="text/javascript" src="<c:url value='/jses/mymenu.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/jses/mymenu.css'/>" type="text/css" media="all">
<script language="javascript">
/*
 * bar1：必须与对象名相同！不要问为什么！！！
          管理员管理系统：大标题
 */
var bar1 = new Q6MenuBar("bar1", "Administrator page");
function load() {
	/*
	设置本色方案！
	配置方案一共4种：0、1、2、3
	*/
	bar1.colorStyle = 1;
	/*
	指定图片目录
	*/
	bar1.config.imgDir = "<c:url value='/jses/image/'/>";
	/*
	菜单之间是否相互排斥
	*/
	bar1.config.radioButton=false;
	/*
	分类管理：指定要添加的菜单名称（如果这个名称的菜单已经存在，不会重复添加）
	查看分类：指定要添加的菜单项名称
	<c:url value='/adminjsps/admin/category/list.jsp'/>：指定菜单项时要请求的地址
	body：结果的显示框架页名称
	*/
	bar1.add("Employee Management", "Employee Query", "<c:url value='/manage/empquery.jsp'/>", "body");
	bar1.add("Employee Management", "Add Employee", "<c:url value='/refer/add.jsp'/>", "body");
	bar1.add("Employee Management", "Modify Employee", "<c:url value='/refer/all.jsp'/>", "body");

	bar1.add("Department Management", "Department Query", "<c:url value='/manage/queryDept.jsp'/>", "body");
	bar1.add("Department Management", "Add Department", "<c:url value='/manage/addDept.jsp'/>", "body");
	bar1.add("Department Management", "Modify Department", "<c:url value='/manage/updateDept.jsp'/>", "body");
	
	//bar1.add("Salary Management", "Salary Query", "<c:url value='/manage/squery.jsp'/>", "body");
	bar1.add("Salary Management", "Salary Rank Query", "<c:url value='/manage/querySal.jsp'/>", "body");
	//bar1.add("Salary Management", "Add Salary Rank", "<c:url value='/manage/querySal.jsp'/>", "body");
	bar1.add("Salary Management", "Modify Salary Rank", "<c:url value='/manage/updateSal.jsp'/>", "body");

	// 获取div元素
	var d = document.getElementById("jses");
	// 把菜单对象转换成字符串，赋给<div>元素做内容
	d.innerHTML = bar1.toString();
}
</script>

</head>

<body onload="load()"  style="margin: 0px;">
<div id="jses"></div>


</body>
</html>
