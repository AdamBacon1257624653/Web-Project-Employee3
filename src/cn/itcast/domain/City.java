package cn.itcast.domain;

public class City {
	private Integer id;
	private String name;

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

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + "]";
	}

	public City() {
		super();
	}

	public City(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
