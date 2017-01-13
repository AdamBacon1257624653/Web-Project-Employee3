package cn.itcast.domain.database;

import java.io.Serializable;
import java.util.Set;

public class Sal implements Serializable {
	private static final long serialVersionUID = 1560905489937963204L;

	private Integer salno;
	private Integer losal;
	private Integer hisal;
	private String salname;

	private Set<Employee> employees;
	private Integer empCount;

	public Sal() {
		super();
	}

	public Sal(Integer salno, Integer losal, Integer hisal, String salname,
			Set<Employee> employees) {
		super();
		this.salno = salno;
		this.losal = losal;
		this.hisal = hisal;
		this.salname = salname;
		this.employees = employees;
	}

	public Integer getEmpCount() {
		return empCount;
	}

	public void setEmpCount(Integer empCount) {
		this.empCount = empCount;
	}

	public Integer getSalno() {
		return salno;
	}

	public void setSalno(Integer salno) {
		this.salno = salno;
	}

	public Integer getLosal() {
		return losal;
	}

	public void setLosal(Integer losal) {
		this.losal = losal;
	}

	public Integer getHisal() {
		return hisal;
	}

	public void setHisal(Integer hisal) {
		this.hisal = hisal;
	}

	public String getSalname() {
		return salname;
	}

	public void setSalname(String salname) {
		this.salname = salname;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Sal [salno=" + salno + ", losal=" + losal + ", hisal=" + hisal
				+ ", salname=" + salname + "]";
	}
}
