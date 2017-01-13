package cn.itcast.domain;

import java.util.List;

import cn.itcast.domain.database.Employee;

public class Page {
	private List<Employee> pageEmployees;
	private Integer pageCount;
	private Integer currentPage;

	public Page() {
		super();
	}

	public Page(List<Employee> pageEmployees, Integer pageCount,
			Integer currentPage) {
		super();
		this.pageEmployees = pageEmployees;
		this.pageCount = pageCount;
		this.currentPage = currentPage;
	}

	public List<Employee> getPageEmployees() {
		return pageEmployees;
	}

	public void setPageEmployees(List<Employee> pageEmployees) {
		this.pageEmployees = pageEmployees;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	@Override
	public String toString() {
		return "Page [pageEmployees=" + pageEmployees + ", pageCount="
				+ pageCount + ", currentPage=" + currentPage + "]";
	}
}
