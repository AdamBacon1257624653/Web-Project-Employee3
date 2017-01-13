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

import cn.itcast.commons.CommonUtils;
import cn.itcast.domain.database.Sal;
import cn.itcast.service.SalService;

public class SalServlet extends HttpServlet {
	private SalService service = new SalService();

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
		if (method.equals("updateSal")) {
			updateSal(request, response);
		}
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
		List<Sal> salList = service.LikeQueryByPage(currentPage, keyWord);
		Integer pageCount = service.LikeQuerySize(keyWord);
		String data = "0";
		if (salList != null && salList.size() > 0) {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig
					.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jsonConfig.setExcludes(new String[] { "handler",
					"hibernateLazyInitializer" });
			data = JSONArray.fromObject(salList, jsonConfig).toString();
			System.out.println(salList);
		}
		try {
			response.getWriter().println(data + "//" + pageCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 薪水更新(Salary update)
	 * 
	 * @param request
	 * @param response
	 */
	private void updateSal(HttpServletRequest request,
			HttpServletResponse response) {
		Sal sal = CommonUtils.toBean(request.getParameterMap(), Sal.class);
		Integer data = 0;
		data = service.saveOrUpdate(sal);
		try {
			response.getWriter().println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		reLoadSalList(request);
	}

	/**
	 * 删除某一条记录(Get rid of one record)
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
		reLoadSalList(request);
	}

	/**
	 * 重新加载SalList(Reload Sallist)
	 * 
	 * @param request
	 */
	private void reLoadSalList(HttpServletRequest request) {
		List<Sal> salList = service.queryAllSals();
		request.getSession().setAttribute("salList", salList);
	}
}
