package cn.itcast.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.Page;
import cn.itcast.domain.database.Department;
import cn.itcast.domain.database.Employee;
import cn.itcast.domain.database.Sal;
import cn.itcast.service.DeptService;
import cn.itcast.service.EmpException;
import cn.itcast.service.EmpService;
import cn.itcast.service.SalService;
import cn.itcast.servlet.BaseServlet;
import cn.itcast.utils.CommonUtils;

//User
public class UserServlet extends BaseServlet {
	private EmpService empService = new EmpService();
	private DeptService deptService = new DeptService();
	private SalService salService = new SalService();

	/**
	 * 退出功能
	 * Exit
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		return "r:/index.jsp";
	}

	/**
	 * 登录功能
	 * Login
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 封装表单数据到form中，输入校验，调用service完成激活，保存错误信息
		 * form到request，转发到login。jsp，保存用户信息到seession中，然后重定向到index.jsp
		 */
		Employee form = CommonUtils.toBean(request.getParameterMap(),
				Employee.class);
		try {
			Employee emp = empService.login(form);
			if (emp.getIsManager() == 1) {// Check if there is any errors when administrator logged in如果是管理员,检测是否有错误信息提交的情况
				List<Employee> wrongEmployees = empService
						.queryWrongEmployees();
				Page page = new Page(empService.queryByPage(1),
						empService.queryPageCount(), 1);
				request.getSession().setAttribute("wrongEmployees",
						wrongEmployees);
				request.getSession().setAttribute("wrongCount",
						wrongEmployees.size());
				request.getSession().setAttribute("page", page);
				loadDepartmentList(request);
				loadSalList(request);
			}
			request.getSession().setAttribute("session_user", emp);
			return "r:/index.jsp";
		} catch (EmpException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}
	}

	/**
	 * 加载部门信息
	 * Retrieve Department Information
	 * 
	 * @param request
	 */
	private void loadDepartmentList(HttpServletRequest request) {
		List<Department> departmentList = deptService.queryAllDepartments();
		// 查找出所有的Department(Query all department information)
		for (Department department : departmentList) {
			department.setEmpCount(department.getEmployees().size());
		}
		request.getSession().setAttribute("deptList", departmentList);
	}

	/**
	 * 加载薪水信息
	 * Retrieve Salary Information
	 * 
	 * @param request
	 */
	private void loadSalList(HttpServletRequest request) {
		List<Sal> salList = salService.queryAllSals();
		request.getSession().setAttribute("salList", salList);
	}

	/**
	 * 注册功能
	 * Register
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		/*
		 * 1，封装表单数据到form对象中 2，补全：uid，code
		 * 3，输入校验，保存错误信息，form到request域，转发到regis。jsp中
		 * 4，调用sevice方法完成注册，保存错误信息，form到request域，转发到regist。jsp
		 * 5，发邮件，保存成功信息转发到msg.jsp
		 */
		// 封装表单数据
		Employee form = CommonUtils.toBean(request.getParameterMap(),
				Employee.class);
		// 补全
		form.setDepartment(new Department(Integer.valueOf(request
				.getParameter("dno")), null, null));
		// 输入校验，创建一个map，用来封装错误信息，其中key为表单字段名称，值为错误信息
		Map<String, String> errors = new HashMap<String, String>();
		// 获取form中的username，password，email进行校验
		valiedateForm(form, request, errors);
		// 判断是否存在错误信息
		if (errors.size() > 0) {
			// 保存错误信息，保存表单数据，转发到regist.jsp中
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}

		// 调用service的regist（）方法
		try {
			empService.regist(form);
			// 执行到这里，说明userservlet执行成功，没有抛出异常
		} catch (EmpException e) {
			// 保存异常信息，保存form，转发到regist.jsp
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			// return "f:/jsps/user/regist.jsp";
			return "f:/index.jsp";

		}
		// 执行这里说明userservice执行成功没有抛出异常，保存成功信息，转发到msg.jsp
		request.setAttribute("msg", "Congratulations!");
		return "f:/jsps/msg.jsp";
	}

	/**
	 * 表单验证
	 * Verify Form
	 * 
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            请求
	 * @param errors
	 *            错误集合
	 */
	private void valiedateForm(Employee form, HttpServletRequest request,
			Map<String, String> errors) {
		// 姓名验证(Verify Name)
		String emp_name = form.getName();
		if (emp_name == null || emp_name.trim().isEmpty()) {
			errors.put("name", "User name can not be empty!");
		} else if (emp_name.length() < 2 || emp_name.length() > 10) {
			errors.put("name", "User name length is restricted: 3~10!");
		}

		// 密码验证(Password Verification)
		String password = form.getPassword();
		if (password == null || password.trim().isEmpty()) {
			errors.put("password", "Password can not be empty");
		} else if (password.length() < 3 || password.length() > 10) {
			errors.put("password", "Password length is restricted: 3~10!");
		}

		// 年龄验证(Age Verification)
		Integer age = form.getAge();
		if (age < 0 || age > 200) {
			errors.put("age", "Please type correct age (0~200)");
		}

		// 电话验证(Telphone Verification)
		String phone = form.getPhone();
		if (phone == null || phone.trim().isEmpty()) {
			errors.put("phone", "Tel no can not be empty");
		} else if (phone.length() != 11) {
			errors.put("phone", "Type correct phone number, please");
		}

		// 地方验证(Location Verification)
		String province = form.getProvince();
		String city = form.getCity();
		if (province == null || province.trim().isEmpty()) {
			errors.put("place", "Address Can not be Empty");
		} else if (city == null || city.trim().isEmpty()) {
			errors.put("place", "City Can not be Empty");
		}

		// 验证码验证(Verificatio of Verify Code)
		String verifyresult = (String) request.getSession().getAttribute(
				"verifyresult");
		String sessioncode = (String) request.getSession().getAttribute(
				"session_vode");
		String verifyCode = request.getParameter("verifyCode");
		if (sessioncode.trim().equalsIgnoreCase(verifyCode.trim())) {
			verifyresult = "1";
		}
		if (verifyresult==null) {
			errors.put("verifycode", "Verify Code Empty!");
		}else{
			if (verifyresult.equals("0")) {
				errors.put("verifycode", "Verify Code Error!");
			}
		}
	}
}
