package cn.itcast.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import cn.itcast.domain.database.Department;
import cn.itcast.domain.database.Employee;
import cn.itcast.domain.database.Sal;

public class SessionUtils {
	private static final SessionFactory sessionfactory = new Configuration()//
			.configure()// 读取默认配置文件,这儿的默认配置文件为hibernate.cfg.xml
			.addClass(Employee.class)//
			.addClass(Department.class)
			.addClass(Sal.class)//
			.buildSessionFactory();//

	/**
	 * 获取SessionFactory(Fetch SessionFactory)
	 * 
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
		return sessionfactory;
	}

	/**
	 * 获取Session(Fetch Session)
	 * 
	 * @return
	 */
	public static Session getsSession() {
		return sessionfactory.openSession();
	}
}
