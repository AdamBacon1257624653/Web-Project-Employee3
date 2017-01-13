package cn.itcast.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import cn.itcast.domain.database.Department;
import cn.itcast.service.DeptService;

public class DeptServlet extends HttpServlet {
	private DeptService service = new DeptService();

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 设置字体编码(Set encoding)
		 */
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String method = request.getParameter("method");
		if (method.equals("delete")) {
			delete(request, response);
		}
		if (method.equals("updateDpet")) {
			updateDpet(request, response);
		}
		if (method.equals("addDpet")) {
			addDpet(request, response);
		}
		if (method.equals("QueryLike")) {
			QueryLike(request, response);
		}
		
	}
	
	/**
	 * method:"QueryLike",
			currentpage:currentpage,
			keyword:keyword,
	 */

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
		List<Department> departments = service
				.LikeQueryByPage(currentPage, keyWord);
		Integer pageCount = service.LikeQuerySize(keyWord);
		String data = "0";
		if (departments != null && departments.size() > 0) {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig
					.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jsonConfig.setExcludes(new String[] { "handler",
					"hibernateLazyInitializer" });
			data = JSONArray.fromObject(departments, jsonConfig).toString();
		}
		try {
			response.getWriter().println(data+"//"+pageCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加新的部门(add a new department)
	 * @param request
	 * @param response
	 */
	private void addDpet(HttpServletRequest request,
			HttpServletResponse response) {
		String newdata = request.getParameter("newdata");
		Integer id=service.queryMaxId();
		Department department=new Department(++id, newdata, null);
		Integer data = 0;
		data = service.Save(department);
		try {
			response.getWriter().println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		reloadDepartmentList(request);
	}

	/**
     *  newdata: name,
	 */
	/**
	 * 部门更新(Update department information)
	 * 
	 * @param request
	 * @param response
	 */
	private void updateDpet(HttpServletRequest request,
			HttpServletResponse response) {
		String newdata = request.getParameter("newdata");
		Integer id = Integer.valueOf(request.getParameter("id"));
		Department department = new Department(id, newdata, null);
		Integer data = 0;
		data = service.saveOrUpdate(department);
		try {
			response.getWriter().println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		reloadDepartmentList(request);
	}

	/**
	 * method: "", newdata: $input.val(), : columnName, : id,
	 */
	/**
	 * 删除某一条记录(Delete one record)
	 * 
	 * @param request
	 * @param response
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		Integer data = 0;
		Integer id = Integer.valueOf(request.getParameter("id"));
		data = service.deleteById(id);
		try {
			response.getWriter().println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		reloadDepartmentList(request);
	}

	/**
	 * 重新加载DepartmentList(Reload departmentList)
	 * 
	 * @param request
	 */
	private void reloadDepartmentList(HttpServletRequest request) {
		List<Department> departmentList = service.queryAllDepartments();
		// 查找出所有的Department
		for (Department department : departmentList) {
			department.setEmpCount(department.getEmployees().size());
		}
		request.getSession().setAttribute("deptList", departmentList);
	}
}
