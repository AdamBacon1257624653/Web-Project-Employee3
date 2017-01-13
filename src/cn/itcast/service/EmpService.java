package cn.itcast.service;

import java.util.List;

import cn.itcast.dao.EmpDao;
import cn.itcast.domain.database.Employee;

//Userҵ���
public class EmpService {
	private EmpDao empDao = new EmpDao();
	int empLikePageSize = 5;

	/**
	 * ע�Ṧ��(Sign up)
	 * 
	 * @param form
	 *            ����װ�õĶ���
	 * @throws EmpException
	 */
	public Integer regist(Employee form) throws EmpException {
		// У���û���(�����ݿ��в���)
		Employee user = empDao.findByName(form.getName());
		if (user != null)
			throw new EmpException("�û��ѱ�ע�ᣡ");
		// ���뵽���ݿ���
		return empDao.saveOrUpdate(form);
	}

	/**
	 * ��¼����(Log in)
	 * 
	 * @param form
	 *            ����װ�õĶ���
	 * @return ����ѯ��������ϸ��Ϣ
	 * @throws EmpException
	 */
	public Employee login(Employee form) throws EmpException {
		/**
		 * ʹ��username��ѯ���õ�user�����userΪnull���׳��쳣���û��������ڣ�
		 * �Ƚ�form��user���룬����ͬ���׳��쳣��������󣩣�����user
		 */
		Employee emp = empDao.findByName(form.getName());
		if (emp == null)
			throw new EmpException("�û���������");
		if (!emp.getPassword().equals(form.getPassword()))
			throw new EmpException("�������!");
		return emp;
	}

	/**
	 * ��ҳ��ѯ(Paginated Query)
	 * 
	 * @param pageIndex
	 *            �ڼ�ҳ��ҳ����������1��ʼ
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
	 * ��ȡ��ҳ��(Retrieve total page amount)
	 * 
	 * @return
	 */
	public Integer queryPageCount() {
		return (int) Math.ceil(empDao.queryTotalSize() * 1.0d / 5d);
	}

	/**
	 * �ύ��������(Submit wrong data)
	 * 
	 * @param emp
	 *            ��Ҫ�ύ�Ķ���
	 * @param wrongMessage
	 *            ��Ҫ���µĴ�������
	 */
	public void updateEmployee(Employee emp) {
		empDao.saveOrUpdate(emp);
	}

	/**
	 * ��ѯ����Ա��(Query all employees)
	 */
	public List<Employee> queryAll() {
		return empDao.queryAll();
	}

	public List<Employee> queryWrongEmployees() {
		return empDao.queryWrongEmployees();
	}

	/**
	 * ����id���в�ѯ(Paginated query according to id)
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
	 * ģ����ѯ(Like query)
	 * 
	 * @return ģ����ѯ��Ա����(A collection of employees)
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
	 * ģ����ѯҪչʾ��ҳ��(Like query page amount)
	 * 
	 * @param keyWord
	 *            ģ����ѯ�Ĺؼ���
	 * @return ҳ��
	 */
	public Integer LikeQuerySize(String keyWord) {
		Integer count = empDao.LikeQuerySize(keyWord);
		Integer pageCount = count == null ? 0 : (int) Math.ceil(count * 1.0d
				/ empLikePageSize);
		return pageCount;
	}
}
