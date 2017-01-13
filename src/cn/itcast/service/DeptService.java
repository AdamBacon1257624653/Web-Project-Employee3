package cn.itcast.service;

import java.util.List;

import cn.itcast.dao.DeptDao;
import cn.itcast.domain.database.Department;

public class DeptService {
	private DeptDao dao = new DeptDao();
	private Integer deptLikePageSize = 5;

	public List<Department> queryAllDepartments() {
		return dao.queryAll();
	}

	/**
	 * 根据Id删除某条记录(Delete one record according to id)
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteById(Integer id) {
		return dao.deleteById(id);
	}

	/**
	 * 保存或更新部门信息(Save or update department information)
	 * 
	 * @param department
	 * @return
	 */
	public Integer saveOrUpdate(Department department) {
		return dao.saveOrUpdate(department);
	}

	/**
	 * 保存部门信息(Save department information)
	 * 
	 * @param department
	 * @return
	 */
	public Integer Save(Department department) {
		return dao.Save(department);
	}

	/**
	 * 查找所有部門的最大Id(Query maximum department id)
	 * 
	 * @return
	 */
	public Integer queryMaxId() {
		return dao.queryMaxId();
	}

	/**
	 * 模糊查询(Like query)
	 * 
	 * @return 模糊查询的员工集(A Collection of employees)
	 */
	public List<Department> LikeQueryByPage(Integer pageIndex, String keyWord) {
		List<Department> departmentList = dao.LikeQueryByPage(pageIndex,
				deptLikePageSize, keyWord);
		for (Department department : departmentList) {
			if (department.getEmployees() != null) {
				department.setEmpCount(department.getEmployees().size());
				department.setEmployees(null);
			}
		}
		return departmentList;
	}

	/**
	 * 模糊查询要展示的页数(Like query the amount of pages)
	 * 
	 * @param keyWord
	 *            模糊查询的关键字
	 * @return 页数(Page Amount)
	 */
	public Integer LikeQuerySize(String keyWord) {
		Integer count = dao.LikeQuerySize(keyWord);
		Integer pageCount = count == null ? 0 : (int) Math.ceil(count * 1.0d
				/ deptLikePageSize);
		return pageCount;
	}
}
