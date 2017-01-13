package cn.itcast.domain;

import java.util.List;

public class Province {
	private Integer id;
	private String name;
	private List<City> cities;
	
	public Province(Integer id, String name, List<City> cities) {
		super();
		this.id = id;
		this.name = name;
		this.cities = cities;
	}
	
	public Province(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
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
	public Province() {
		super();
	}
	@Override
	public String toString() {
		return "Province [id=" + id + ", name=" + name + ", cities=" + cities
				+ "]";
	}
}
