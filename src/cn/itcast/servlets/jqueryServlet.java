package cn.itcast.servlets;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import cn.itcast.Util.Utils;
import cn.itcast.commons.CommonUtils;
import cn.itcast.domain.City;
import cn.itcast.domain.Page;
import cn.itcast.domain.database.Department;
import cn.itcast.domain.database.Employee;
import cn.itcast.service.EmpService;

public class jqueryServlet extends HttpServlet {

	EmpService service = new EmpService();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * Set Encoding
		 */
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String method = request.getParameter("method");
		if (method.equals("updateWrong")) {
			String id = request.getParameter("id");
			String columnName = request.getParameter("columnName");
			Object data;
			if (columnName.equals("sex") || columnName.equals("place")
					|| columnName.equals("phone")) {
				String sexData = request.getParameter("newdata");
				data = sexData;
			} else {
				Integer newData = Integer.valueOf(request
						.getParameter("newdata"));
				data = newData;
			}
			updateWrong(request, response, data, columnName, id);
			/**
			 * Display modified data on system rebooting
			 * 再次启动时，显示修改后的数据
			 */
			Page page = (Page) request.getSession().getAttribute("page");
			page.setPageEmployees(service.queryByPage(1));
			request.getSession().setAttribute("page", page);
			List<Employee> wrongEmployees = service.queryWrongEmployees();
			request.getSession().setAttribute("wrongEmployees", wrongEmployees);
		}
		if (method.equals("delete")) {
			String id = request.getParameter("id");
			deleteById(request, response, id);
			List<Employee> allEmployees = service.queryAll();
			request.getSession().setAttribute("allEmployees", allEmployees);
			List<Employee> wrongEmployees = service.queryWrongEmployees();
			request.getSession().setAttribute("wrongEmployees", wrongEmployees);
		}
		if (method.equals("checkname")) {
			String data = request.getParameter("data").trim();
			if (data.length() < 2 || data.length() > 10) {
				response.getWriter().println("请输入长度为2~10的员工姓名");
			}
		}
		if (method.equals("checkpassword")) {
			String data = request.getParameter("data").trim();
			if (data.length() < 3 || data.length() > 10) {
				response.getWriter().println("请输入长度为3~10的密码");
			}
		}
		if (method.equals("checkage")) {
			Integer data = Integer.valueOf(request.getParameter("data").trim());
			if (data < 0 || data > 200) {
				response.getWriter().println("请输入长度正确0~200之间的年龄");
			}
		}
		if (method.equals("addEmp")) {
			addEmp(request, response);
		}
		// 省市联动
		if (method.equals("changeProvince")) {
			String provinceName = request.getParameter("provincename");
			changeCities(request, response, provinceName);
		}
		// 验证码
		if (method.equals("validateCode")) {
			String verifycodeInput = request.getParameter("verifycode");
			validateCode(request, response, verifycodeInput);
		}
		// 切换显示页
		if (method.equals("changePage")) {
			Integer currentpage = Integer.valueOf(request
					.getParameter("currentpage"));
			changePage(request, response, currentpage);
		}
		// 模糊查询
		if (method.equals("QueryLike")) {
			QueryLike(request, response);
		}
	}

	/**
	 * 模糊查询(Like query)
	 * 
	 * @param request
	 * @param response
	 */
	private void QueryLike(HttpServletRequest request,
			HttpServletResponse response) {
		String keyWord = request.getParameter("keyword");
		Integer currentPage = Integer.valueOf(request
				.getParameter("currentpage"));
		List<Employee> employees = service
				.LikeQueryByPage(currentPage, keyWord);
		Integer pageCount = service.LikeQuerySize(keyWord);
		String data = "0";
		if (employees != null && employees.size() > 0) {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig
					.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jsonConfig.setExcludes(new String[] { "handler",
					"hibernateLazyInitializer" });
			data = JSONArray.fromObject(employees, jsonConfig).toString();
		}
		try {
			response.getWriter().println(data+"//"+pageCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 切换页面显示(Switch page)
	 * 
	 * @param request
	 * @param response
	 * @param currentpage
	 *            需要切换的显示页数
	 */
	private void changePage(HttpServletRequest request,
			HttpServletResponse response, Integer currentpage) {
		try {
			List<Employee> pageEmployees = service.queryByPage(currentpage);
			String data = "";
			if (pageEmployees.size() > 0) {
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig
						.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 设置循环检测
				jsonConfig.setExcludes(new String[] { "handler",
						"hibernateLazyInitializer" });// 过滤hibernate的代理对象属性
				data = JSONArray.fromObject(pageEmployees, jsonConfig)
						.toString();
			} else {
				data = "0";
			}
			response.getWriter().println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证码的验证(Verification of verify code)
	 * 
	 * @param request
	 * @param response
	 * @param verifycodeInput
	 *            输入的验证码
	 */
	private void validateCode(HttpServletRequest request,
			HttpServletResponse response, String verifycodeInput) {
		String sessioncode = (String) request.getSession().getAttribute(
				"session_vode");
		String data = "0";
		if (sessioncode.trim().equalsIgnoreCase(verifycodeInput.trim())) {
			data = "1";
		}
		request.getSession().setAttribute("verifyresult", data);
		try {
			response.getWriter().println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 改变级联城市(Change cities)
	 * 
	 * @param request
	 * @param response
	 * @param provinceName
	 *            省份名
	 */
	private void changeCities(HttpServletRequest request,
			HttpServletResponse response, String provinceName) {
		List<City> cities = null;
		if (!provinceName.trim().isEmpty()) {
			cities = Utils.getProvinceMap().get(provinceName).getCities();
			try {
				response.getWriter().println(JSONArray.fromObject(cities));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 添加员工(Add employee)
	 * 
	 * @param request
	 * @param response
	 */
	private void addEmp(HttpServletRequest request, HttpServletResponse response) {
		// 封装表单数据
		Employee form = CommonUtils.toBean(request.getParameterMap(),
				Employee.class);
		// 补全
		form.setDepartment(new Department(Integer.valueOf(request
				.getParameter("dno")), null, null));
		try {
			Integer data = service.regist(form);
			response.getWriter().println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除一个用户(Ditch a user)
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	private void deleteById(HttpServletRequest request,
			HttpServletResponse response, String id) {
		Integer i = service.deleteById(id);
		try {
			response.getWriter().println(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理提交的错误信息(Handle submitted error information)
	 * 
	 * @param request
	 * @param response
	 * @param newData
	 *            新修改的数据
	 * @param columnName
	 *            修改的列明
	 * @param id
	 *            修改员工的id
	 */
	private void updateWrong(HttpServletRequest request,
			HttpServletResponse response, Object newData, String columnName,
			String id) {
		Employee emp = service.findById(id);
		try {
			if (columnName.equals("department")) {// 更新部门字段
				emp.getDepartment().setId(Integer.valueOf(newData.toString()));
			} else {
				if (columnName.equals("sex") || columnName.equals("phone")) {// 更新sex/phone字段
					PropertyDescriptor pd = new PropertyDescriptor(columnName,
							Employee.class);
					pd.getWriteMethod().invoke(emp, newData.toString());
				} else if (columnName.equals("place")) {
					String[] procity = newData.toString().split(":");
					String province = procity[0];
					String city = procity[1];
					emp.setProvince(province);
					emp.setCity(city);
				} else {// 更新整形数据
					PropertyDescriptor pd = new PropertyDescriptor(columnName,
							Employee.class);
					pd.getWriteMethod().invoke(emp,
							Integer.valueOf(newData.toString().trim()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 更改修改信息
		if (request.getSession().getAttribute("wrongEmployees") != null) {
			emp.setIsRight(1);
			emp.setWrongMessage("无");
		}
		service.updateEmployee(emp);
		/**
		 * 回显，以便查看是否已经成功修改
		 */
		if (request.getSession().getAttribute("wrongEmployees") != null) {
			List<Employee> wrongEmployees = service.queryWrongEmployees();
			request.getSession().setAttribute("wrongEmployees", wrongEmployees);
			Integer wrongCount = 0;
			if (wrongEmployees != null) {
				wrongCount = wrongEmployees.size();
			}
			request.getSession().setAttribute("wrongCount", wrongCount);
		}
	}
}
