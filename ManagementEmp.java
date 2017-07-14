package com.dao;

import java.util.ArrayList;
import java.util.Scanner;

public class ManagementEmp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("Nhap ten muon tim");
		String name = input.nextLine();
		/*System.out.println("Nhap Phone");
		String phone = input.nextLine();
		System.out.println("Nhap Age");
		int age = input.nextInt();
		
		Employee emp1 = new Employee(name, phone, age);
		DataAccessFromDB db = new DataAccessFromDB();
		db.addNewEmployee(emp1);
		System.out.println("ok");*/
		DataAccessFromDB db = new DataAccessFromDB();
		ArrayList<Employee> ds = db.getDanhSach(name);
		for (Employee item : ds) {
			System.out.println(item.toString());
			
		}
		
		

	}

}
