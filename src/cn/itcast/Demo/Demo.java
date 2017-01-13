package cn.itcast.Demo;

import java.util.List;

import org.junit.Test;

import cn.itcast.dao.EmpDao;
import cn.itcast.domain.database.Employee;

public class Demo {
	private EmpDao empDao = new EmpDao();

	@Test
	public void fun() {
		List<Employee> emList = empDao.queryByPage(1, 4);
		for (Employee employee : emList) {
			System.out.println(employee);
		}
	}

}
