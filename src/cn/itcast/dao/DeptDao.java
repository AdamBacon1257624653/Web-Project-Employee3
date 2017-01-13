package cn.itcast.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.itcast.Util.SessionUtils;
import cn.itcast.domain.database.Department;
import cn.itcast.jdbc.TxQueryRunner;

public class DeptDao {
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * �������еĲ�����Ϣ
	 * Query all department information
	 * 
	 * @return
	 */
	public List<Department> queryAll() {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List<Department> departmentList = session.createQuery(
					"FROM Department").list();
			for (Department department : departmentList) {
				Hibernate.initialize(department.getEmployees());
			}
			transaction.commit();
			return departmentList;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	/**
	 * ����Idɾ�����ż�¼
	 * Remove department records according to department number
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteById(Integer id) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Integer count = session
					.createQuery("DELETE Department d WHERE d.id=?")
					.setParameter(0, id).executeUpdate();
			transaction.commit();
			return count;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	/**
	 * �������²�����Ϣ
	 * Save or update department information
	 * 
	 * @param department
	 * @return
	 */
	public Integer saveOrUpdate(Department department) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(department);
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
	 * �������Id
	 * Retrieve maximum id
	 * 
	 * @return
	 */
	public Integer queryMaxId() {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Integer maxId = (Integer) session.createQuery(
					"SELECT MAX(id) FROM Department").uniqueResult();
			transaction.commit();
			return maxId;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}


	/**
	 * ���沿����Ϣ
	 * Save department information
	 * @param department
	 * @return
	 */
	public Integer Save(Department department) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(department);
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
	 * ģ����ҳ��ѯ
	 * Blur pagination query
	 * 
	 * @param pageIndex
	 *            �ڼ�ҳ
	 * @param pageSize
	 *            ÿҳ��ʾ�ļ�¼��
	 * @param keyWord
	 *            ģ����ѯ�Ĺؼ���
	 * @return
	 */
	public List<Department> LikeQueryByPage(int pageIndex, int pageSize,
			String keyWord) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List<Department> departmentList = session
					.createQuery("FROM Department d WHERE d.name LIKE ?")//
					.setParameter(0, "%" + keyWord + "%")//
					.setFirstResult((pageIndex - 1) * pageSize)
					.setMaxResults(pageSize).list();
			for (Department department : departmentList) {
				Hibernate.initialize(department.getEmployees());// ִ�з������ݿ�
			}
			transaction.commit();
			return departmentList;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	/**
	 * ģ����ѯ����
	 * Retrieve line count
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
							"SELECT COUNT(*) FROM Department d WHERE d.name LIKE ?")//
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
