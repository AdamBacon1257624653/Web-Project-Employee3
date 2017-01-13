package cn.itcast.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.database.Employee;
import cn.itcast.service.EmpService;

public class EmpServlet extends HttpServlet {

	private EmpService empService = new EmpService();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		// 提交错误信息功能
		Employee emp = (Employee) request.getSession().getAttribute(
				"session_user");
		String wrongMessage = request.getParameter("wrongMessage");
		// 补全
		emp.setWrongMessage(wrongMessage);// 设置错误信息
		emp.setIsRight(0);// 设置错误标识符
		empService.updateEmployee(emp);
		response.getWriter().write(
				"<script>alert('信息提交成功，反馈正在处理，请稍等.')</script>");
	}
}
