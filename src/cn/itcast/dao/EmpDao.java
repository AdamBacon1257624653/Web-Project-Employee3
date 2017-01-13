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

//user�־ò�
public class EmpDao {
	private QueryRunner qr = new TxQueryRunner();
	private String table_emp = "emp";

	/**
	 * ���û�����ѯ
	 * Query by name
	 * 
	 * @param Name
	 *            Employee Name
	 * @return Ա������
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
	 * ��ʼ��нˮ��Ϣ(Load salary information)
	 * 
	 * @param session
	 *            ���ӵ�session
	 * @param employee
	 *            ��Ҫ��ȫ�Ķ���
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
	 * ���Ա��(����Employee)(Add Employee)
	 * 
	 * @param employee
	 * @return <li>1:�޸ĳɹ�.<li>0:�޸�ʧ��
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
	 * ��ѯ���ݿ����ܼ�¼��(Retrieve total amount of database)
	 * 
	 * @return �ܼ�¼��
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
	 * ��ҳ��ѯ(Paginated Query)
	 * 
	 * @param pageIndex
	 *            ҳ��
	 * @param pageSize
	 *            һҳ����ʾ������
	 * @return Ա������
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
				Hibernate.initialize(employee.getDepartment());// ִ�з������ݿ�
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
	 * ����id������Ա��(Query Employee By ID)
	 * 
	 * @param id
	 *            Ա��id
	 * @return ���ز��ҳɹ���Ա��
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
	 * ��ѯ����Ա��(Retrieve all employees)
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
				Hibernate.initialize(employee.getDepartment());// ��ʼ��������󣬱���������
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
	 * ��ѯ�ύ����Ա��(Query all employees who submitted wrong information)
	 * 
	 * @return �������Ŀ(Wrong amount)
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
				Hibernate.initialize(employee.getDepartment());// ��ʼ��������󣬱���������
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
	 * ģ����ҳ��ѯ(Blur paginated query)
	 * 
	 * @param pageIndex
	 *            �ڼ�ҳ
	 * @param pageSize
	 *            ÿҳ��ʾ�ļ�¼��
	 * @param keyWord
	 *            ģ����ѯ�Ĺؼ���
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
				Hibernate.initialize(employee.getDepartment());// ִ�з������ݿ�
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
	 * ģ����ѯ����(Blur paginated query line amount)
	 * 
	 * @param keyWord
	 *            ģ����ѯ�Ĺؼ���
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
