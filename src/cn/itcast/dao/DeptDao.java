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
	 * 查找所有的部门信息
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
	 * 根据Id删除部门记录
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
	 * 保存或更新部门信息
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
	 * 查找最大Id
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
	 * 保存部门信息
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
	 * 模糊分页查询
	 * Blur pagination query
	 * 
	 * @param pageIndex
	 *            第几页
	 * @param pageSize
	 *            每页显示的记录数
	 * @param keyWord
	 *            模糊查询的关键字
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
				Hibernate.initialize(department.getEmployees());// 执行访问数据库
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
	 * 模糊查询行数
	 * Retrieve line count
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
