package cn.itcast.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.itcast.Util.SessionUtils;
import cn.itcast.domain.database.Employee;
import cn.itcast.domain.database.Sal;
import cn.itcast.jdbc.TxQueryRunner;

//user持久层
public class EmpDao {
	private QueryRunner qr = new TxQueryRunner();
	private String table_emp = "emp";

	/**
	 * 按用户名查询
	 * Query by name
	 * 
	 * @param Name
	 *            Employee Name
	 * @return 员工对象
	 * 			Employee instance
	 */
	public Employee findByName(String Name) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Employee employee = (Employee) session
					.createQuery("FROM Employee e WHERE e.name=?")
					.setParameter(0, Name).uniqueResult();
			if (employee != null) {
				Hibernate.initialize(employee.getDepartment());
				initSalary(session, employee);
			}
			transaction.commit();
			return employee;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	/**
	 * 初始化薪水信息(Load salary information)
	 * 
	 * @param session
	 *            连接的session
	 * @param employee
	 *            需要补全的对象
	 */
	private void initSalary(Session session, Employee employee) {
		String salSql = "SELECT s.* "//
				+ " FROM sal s,(SELECT * FROM emp WHERE id=?) e "//
				+ " WHERE e.salary BETWEEN s.losal AND s.hisal"//
				+ " GROUP BY e.id";//
		Query query = session.createSQLQuery(salSql)//
				.addEntity("s", Sal.class)//
				.setParameter(0, employee.getId());//
		Sal sal = (Sal) query.uniqueResult();
		employee.setSal(sal);
	}

	/**
	 * 添加员工(插入Employee)(Add Employee)
	 * 
	 * @param employee
	 * @return <li>1:修改成功.<li>0:修改失败
	 */
	public Integer saveOrUpdate(Employee employee) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(employee);
			transaction.commit();
			return 1;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}

	/**
	 * 查询数据库中总记录数(Retrieve total amount of database)
	 * 
	 * @return 总记录数
	 */
	public Integer queryTotalSize() {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			String hql = "SELECT COUNT(*) FROM Employee";
			Number countNum = (Number) session.createQuery(hql).uniqueResult();
			Integer count = countNum.intValue();
			transaction.commit();
			return count;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}

	/**
	 * 分页查询(Paginated Query)
	 * 
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            一页所显示的行数
	 * @return 员工集合
	 */
	public List<Employee> queryByPage(int pageIndex, int pageSize) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List<Employee> employeeList = session.createQuery("FROM Employee")
					.setFirstResult((pageIndex - 1) * pageSize)
					.setMaxResults(pageSize).list();
			for (Employee employee : employeeList) {
				Hibernate.initialize(employee.getDepartment());// 执行访问数据库
				initSalary(session, employee);
			}
			transaction.commit();
			return employeeList;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	/**
	 * 根据id来查找员工(Query Employee By ID)
	 * 
	 * @param id
	 *            员工id
	 * @return 返回查找成功的员工
	 */
	public Employee findById(String id) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, id);
			if (employee != null) {
				Hibernate.initialize(employee.getDepartment());
			}
			transaction.commit();
			return employee;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	/**
	 * 查询所有员工(Retrieve all employees)
	 * 
	 * @return
	 */
	public List<Employee> queryAll() {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List<Employee> employeeList = session.createQuery("FROM Employee")
					.list();
			for (Employee employee : employeeList) {
				Hibernate.initialize(employee.getDepartment());// 初始化代理对象，避免懒加载
			}
			transaction.commit();
			return employeeList;
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * 查询提交错误员工(Query all employees who submitted wrong information)
	 * 
	 * @return 错误的数目(Wrong amount)
	 */
	public List<Employee> queryWrongEmployees() {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List<Employee> employeeList = session
					.createQuery("FROM Employee WHERE isRight=?")
					.setParameter(0, 0).list();
			for (Employee employee : employeeList) {
				Hibernate.initialize(employee.getDepartment());// 初始化代理对象，避免懒加载
			}
			transaction.commit();
			return employeeList;
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public Integer deleteById(String id) {
		String sql = "DELETE FROM " + table_emp + " WHERE id=?";
		try {
			return qr.update(sql, id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 模糊分页查询(Blur paginated query)
	 * 
	 * @param pageIndex
	 *            第几页
	 * @param pageSize
	 *            每页显示的记录数
	 * @param keyWord
	 *            模糊查询的关键字
	 * @return
	 */
	public List<Employee> LikeQueryByPage(int pageIndex, int pageSize,
			String keyWord) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List<Employee> employeeList = session
					.createQuery("FROM Employee e WHERE e.name LIKE ?")
					//
					.setParameter(0, "%" + keyWord + "%")
					//
					.setFirstResult((pageIndex - 1) * pageSize)
					.setMaxResults(pageSize).list();
			for (Employee employee : employeeList) {
				Hibernate.initialize(employee.getDepartment());// 执行访问数据库
				initSalary(session, employee);
			}
			transaction.commit();
			return employeeList;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	/**
	 * 模糊查询行数(Blur paginated query line amount)
	 * 
	 * @param keyWord
	 *            模糊查询的关键字
	 * @return
	 */
	public Integer LikeQuerySize(String keyWord) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Number count = (Number) session
					.createQuery(
							"SELECT COUNT(*) FROM Employee e WHERE e.name LIKE ?")//
					.setParameter(0, "%" + keyWord + "%")//
					.uniqueResult();
			transaction.commit();
			return count.intValue();
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
}
