package cn.itcast.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.domain.VerifyCode;

public class VerifyCodeServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 校验验证码，从session中获取正确的校验码，从表单中获取用户填写的校验码，进行比较
		// 如果相同，向下运行，否则保存错误信息到requset域中，转发到regist。jsp
		String sessionCode = (String) request.getSession().getAttribute(
				"session_vode");
		String paramCode = request.getParameter("verifyCode");

		if (!paramCode.equalsIgnoreCase(sessionCode)) {
			request.setAttribute("msg", "验证码错误！");
			request.getRequestDispatcher("jsps/user/regist.jsp").forward(
					request, response);
			return;
		}

		// 获取表单数据，处理中文问题
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		// 校验用户名和密码是否正确
		if (!"itcast".equalsIgnoreCase(name)) {

			Cookie cookie = new Cookie("uname", name);// 创建cookie
			cookie.setMaxAge(60 * 60 * 24);// 设置cookie命长为1
			response.addCookie(cookie);// 保存cookie

			// 如果成功，保存用户信息到session中，重定向到msg。jsp
			HttpSession session = request.getSession();
			session.setAttribute("name", name);
			response.sendRedirect("/employee/jsps/msg.jsp");

		} else {// 如果失败，保存错误信息到request域中，转发到login。jsp；
			request.setAttribute("msg", "用户名或密码错误！");
			RequestDispatcher qr = request
					.getRequestDispatcher("/jsps/user/regist.jsp");// 得到转发器
			qr.forward(request, response);// 转发

		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * 生成图片，保存图片上的文本到session域中， 把图片响应给客户
		 */
		VerifyCode vc = new VerifyCode();
		BufferedImage image = vc.getImage();
		request.getSession().setAttribute("session_vode", vc.getText());
		VerifyCode.output(image, response.getOutputStream());
	}

}
