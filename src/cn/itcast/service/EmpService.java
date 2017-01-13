package cn.itcast.service;

import java.util.List;

import cn.itcast.dao.EmpDao;
import cn.itcast.domain.database.Employee;

//User业务层
public class EmpService {
	private EmpDao empDao = new EmpDao();
	int empLikePageSize = 5;

	/**
	 * 注册功能(Sign up)
	 * 
	 * @param form
	 *            表单封装好的对象
	 * @throws EmpException
	 */
	public Integer regist(Employee form) throws EmpException {
		// 校验用户名(在数据库中查找)
		Employee user = empDao.findByName(form.getName());
		if (user != null)
			throw new EmpException("用户已被注册！");
		// 插入到数据库中
		return empDao.saveOrUpdate(form);
	}

	/**
	 * 登录功能(Log in)
	 * 
	 * @param form
	 *            表单封装好的对象
	 * @return 表单查询出来的详细信息
	 * @throws EmpException
	 */
	public Employee login(Employee form) throws EmpException {
		/**
		 * 使用username查询，得到user，如果user为null，抛出异常（用户名不存在）
		 * 比较form和user密码，若不同，抛出异常（密码错误），返回user
		 */
		Employee emp = empDao.findByName(form.getName());
		if (emp == null)
			throw new EmpException("用户名不存在");
		if (!emp.getPassword().equals(form.getPassword()))
			throw new EmpException("密码错误!");
		return emp;
	}

	/**
	 * 分页查询(Paginated Query)
	 * 
	 * @param pageIndex
	 *            第几页（页的索引）从1开始
	 * @return
	 */
	public List<Employee> queryByPage(int pageIndex) {
		List<Employee> employeeList = empDao.queryByPage(pageIndex, 5);
		for (Employee employee : employeeList) {
			if (employee.getDepartment() != null) {
				employee.getDepartment().setEmployees(null);
			}
		}
		return employeeList;
	}

	/**
	 * 获取总页数(Retrieve total page amount)
	 * 
	 * @return
	 */
	public Integer queryPageCount() {
		return (int) Math.ceil(empDao.queryTotalSize() * 1.0d / 5d);
	}

	/**
	 * 提交错误数据(Submit wrong data)
	 * 
	 * @param emp
	 *            需要提交的对象
	 * @param wrongMessage
	 *            需要更新的错误数据
	 */
	public void updateEmployee(Employee emp) {
		empDao.saveOrUpdate(emp);
	}

	/**
	 * 查询所有员工(Query all employees)
	 */
	public List<Employee> queryAll() {
		return empDao.queryAll();
	}

	public List<Employee> queryWrongEmployees() {
		return empDao.queryWrongEmployees();
	}

	/**
	 * 根据id进行查询(Paginated query according to id)
	 * 
	 * @param id
	 * @return
	 */
	public Employee findById(String id) {
		return empDao.findById(id);
	}

	public Integer deleteById(String id) {
		return empDao.deleteById(id);
	}

	/**
	 * 模糊查询(Like query)
	 * 
	 * @return 模糊查询的员工集(A collection of employees)
	 */
	public List<Employee> LikeQueryByPage(Integer pageIndex, String keyWord) {
		List<Employee> employeeList = empDao.LikeQueryByPage(pageIndex,
				empLikePageSize, keyWord);
		for (Employee employee : employeeList) {
			if (employee.getDepartment() != null) {
				employee.getDepartment().setEmployees(null);
			}
		}
		return employeeList;
	}

	/**
	 * 模糊查询要展示的页数(Like query page amount)
	 * 
	 * @param keyWord
	 *            模糊查询的关键字
	 * @return 页数
	 */
	public Integer LikeQuerySize(String keyWord) {
		Integer count = empDao.LikeQuerySize(keyWord);
		Integer pageCount = count == null ? 0 : (int) Math.ceil(count * 1.0d
				/ empLikePageSize);
		return pageCount;
	}
}
