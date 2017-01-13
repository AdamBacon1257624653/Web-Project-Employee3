package cn.itcast.domain.database;

import java.io.Serializable;

public class Employee implements Serializable {
	private static final long serialVersionUID = 7184967523564688943L;
	// 对应数据库(Corresponds to database)
	private String id;// 主键(Primary key)
	private String name;// 用户名(User name)
	private String password;// 密码(Passowrd)
	private String sex;// 性别(Gender)
	private Integer age;// 年龄(age)
	private String province;
	private String city;
	private String phone;
	// 带默认的属性
	private Integer dutytime = 15;
	private Integer offtime = 0;
	private Integer salary = 3000;// 薪金
	private Integer isManager = 0;// 0:false,1:true
	private Integer isRight = 1;
	private String wrongMessage = "无";
	// 外键关联（Foreign key）
	private Department department;
	private Sal sal;

	public Employee() {
		super();
	}

	public Employee(String id, String name, String password, String sex,
			Integer age, String province, String city, String phone,
			Integer dutytime, Integer offtime, Integer salary,
			Integer isManager, Integer isRight, String wrongMessage,
			Department department, Sal sal) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.province = province;
		this.city = city;
		this.phone = phone;
		this.dutytime = dutytime;
		this.offtime = offtime;
		this.salary = salary;
		this.isManager = isManager;
		this.isRight = isRight;
		this.wrongMessage = wrongMessage;
		this.department = department;
		this.sal = sal;
	}

	public Sal getSal() {
		return sal;
	}

	public void setSal(Sal sal) {
		this.sal = sal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getDutytime() {
		return dutytime;
	}

	public void setDutytime(Integer dutytime) {
		this.dutytime = dutytime;
	}

	public Integer getOfftime() {
		return offtime;
	}

	public void setOfftime(Integer offtime) {
		this.offtime = offtime;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Integer getIsManager() {
		return isManager;
	}

	public void setIsManager(Integer isManager) {
		this.isManager = isManager;
	}

	public Integer getIsRight() {
		return isRight;
	}

	public void setIsRight(Integer isRight) {
		this.isRight = isRight;
	}

	public String getWrongMessage() {
		return wrongMessage;
	}

	public void setWrongMessage(String wrongMessage) {
		this.wrongMessage = wrongMessage;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", password="
				+ password + ", sex=" + sex + ", age=" + age + ", province="
				+ province + ", city=" + city + ", phone=" + phone
				+ ", dutytime=" + dutytime + ", offtime=" + offtime
				+ ", salary=" + salary + ", isManager=" + isManager
				+ ", isRight=" + isRight + ", wrongMessage=" + wrongMessage
				+ ", department=" + department + ", sal=" + sal + "]";
	}
}
