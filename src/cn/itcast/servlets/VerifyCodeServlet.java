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

		// У����֤�룬��session�л�ȡ��ȷ��У���룬�ӱ��л�ȡ�û���д��У���룬���бȽ�
		// �����ͬ���������У����򱣴������Ϣ��requset���У�ת����regist��jsp
		String sessionCode = (String) request.getSession().getAttribute(
				"session_vode");
		String paramCode = request.getParameter("verifyCode");

		if (!paramCode.equalsIgnoreCase(sessionCode)) {
			request.setAttribute("msg", "��֤�����");
			request.getRequestDispatcher("jsps/user/regist.jsp").forward(
					request, response);
			return;
		}

		// ��ȡ�����ݣ�������������
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		// У���û����������Ƿ���ȷ
		if (!"itcast".equalsIgnoreCase(name)) {

			Cookie cookie = new Cookie("uname", name);// ����cookie
			cookie.setMaxAge(60 * 60 * 24);// ����cookie����Ϊ1
			response.addCookie(cookie);// ����cookie

			// ����ɹ��������û���Ϣ��session�У��ض���msg��jsp
			HttpSession session = request.getSession();
			session.setAttribute("name", name);
			response.sendRedirect("/employee/jsps/msg.jsp");

		} else {// ���ʧ�ܣ����������Ϣ��request���У�ת����login��jsp��
			request.setAttribute("msg", "�û������������");
			RequestDispatcher qr = request
					.getRequestDispatcher("/jsps/user/regist.jsp");// �õ�ת����
			qr.forward(request, response);// ת��

		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * ����ͼƬ������ͼƬ�ϵ��ı���session���У� ��ͼƬ��Ӧ���ͻ�
		 */
		VerifyCode vc = new VerifyCode();
		BufferedImage image = vc.getImage();
		request.getSession().setAttribute("session_vode", vc.getText());
		VerifyCode.output(image, response.getOutputStream());
	}

}
