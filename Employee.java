package com.dao;

public class Employee {
	private String nameEmp;
	private String phone;
	private int ageEmp;
	public Employee(String nameEmp, String phone, int ageEmp) {
		super();
		this.nameEmp = nameEmp;
		this.phone = phone;
		this.ageEmp = ageEmp;
	}
	public String getNameEmp() {
		return nameEmp;
	}
	public void setNameEmp(String nameEmp) {
		this.nameEmp = nameEmp;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getAgeEmp() {
		return ageEmp;
	}
	public void setAgeEmp(int ageEmp) {
		this.ageEmp = ageEmp;
	}
	
	@Override
	public String toString(){
		return "Name is"+this.getNameEmp()+"\nPhone is"+this.getPhone()+"\n Age is"+this.getAgeEmp();
	}

}
