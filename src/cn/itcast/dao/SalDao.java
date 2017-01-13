package cn.itcast.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.itcast.Util.SessionUtils;
import cn.itcast.domain.database.Sal;
import cn.itcast.jdbc.TxQueryRunner;

public class SalDao {
	private QueryRunner qr = new TxQueryRunner();
	private String countSql = "SELECT COUNT(*)"//
			+ " FROM sal s,emp e"//
			+ " WHERE e.salary BETWEEN s.losal AND s.hisal"//
			+ " AND s.salno=?";
	private String likeCountSql = "SELECT COUNT(*)"
								  +" FROM sal s"
								  +" WHERE s.salname LIKE ?";
	/**
	 * �������е�Salary��Ϣ(Retrieve all Salary information)
	 * 
	 * @return
	 */
	public List<Sal> queryAll() {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List<Sal> salList = session.createQuery("FROM Sal").list();
			for (Sal sal : salList) {
				Number empCount = (Number) session.createSQLQuery(countSql)
						.setParameter(0, sal.getSalno()).uniqueResult();
				sal.setEmpCount(empCount.intValue());
			}
			transaction.commit();
			return salList;
		} catch (RuntimeException e) {
			transaction.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	/**
	 * ����Idɾ��нˮ��¼(Delete salary information according to salary id)
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteById(Integer id) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Integer count = session.createQuery("DELETE Sal s WHERE s.salno=?")
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
	 * ��������нˮ��Ϣ(Save or update salary information)
	 * 
	 * @param salary
	 * @return
	 */
	public Integer saveOrUpdate(Sal sal) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(sal);
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
	 * �������Id(Query maximum id)
	 * 
	 * @return
	 */
	public Integer queryMaxId() {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Integer maxId = (Integer) session.createQuery(
					"SELECT MAX(salno) FROM Sal").uniqueResult();
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
	 * ����нˮ��Ϣ(Save salary information)
	 * 
	 * @param sal
	 * @return
	 */
	public Integer Save(Sal sal) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(sal);
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
	public List<Sal> LikeQueryByPage(int pageIndex, int pageSize, String keyWord) {
		Session session = SessionUtils.getsSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List<Sal> salList = session
					.createQuery("FROM Sal s WHERE s.salname LIKE ?")//
					.setParameter(0, "%" + keyWord + "%")//
					.setFirstResult((pageIndex - 1) * pageSize)//
					.setMaxResults(pageSize).list();
			for (Sal sal : salList) {
				Number empCount = (Number) session.createSQLQuery(countSql)
						.setParameter(0, sal.getSalno()).uniqueResult();
				sal.setEmpCount(empCount.intValue());
			}
			transaction.commit();
			return salList;
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
			Number count = (Number) session.createSQLQuery(likeCountSql)//
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
