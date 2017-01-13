package cn.itcast.domain.database;

import java.io.Serializable;
import java.util.Set;

public class Department implements Serializable {
	private static final long serialVersionUID = 822387008687575625L;

	private Integer id;
	private String name;
	private Set<Employee> employees;

	private Integer empCount;//记录员工数量(Record Employee count)

	public Department() {
		super();
	}

	public Department(Integer id, String name, Set<Employee> employees) {
		super();
		this.id = id;
		this.name = name;
		this.employees = employees;
	}
	
	public void setEmpCount(Integer empCount) {
		this.empCount = empCount;
	}

	public Integer getEmpCount() {
		return empCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}

}
