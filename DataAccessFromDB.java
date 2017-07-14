package com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataAccessFromDB {
	Connection sqlcon = null;
	Statement stmt = null;
	PreparedStatement pstmt= null;
	CallableStatement callstmt = null;
	ResultSet rs = null;
	ArrayList<Employee> ds;
	
	/**
	 * connect to database 
	 */
	public Connection getConnect(){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			sqlcon = DriverManager.getConnection("" +
					"jdbc:sqlserver://localhost:1433;databaseName=EmployeeDB; username=sa;" +
					" password=hoaphuc123!");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sqlcon;
	}
	
	/**
	 * add new employee use Statement interface
	 */
	public void addNewEmployee(Employee emp){
		String sql = "Insert into Employee values('"+emp.getNameEmp()+"','"+emp.getPhone()+"',"+emp.getAgeEmp()+")";
		try {
			stmt=getConnect().createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * delete employee use prepareStatement
	 * @throws SQLException 
	 */
	public void deleteEmployee(String name) throws SQLException{
				String sql = "delete from Employee where Name=?";
		try {
			getConnect().setAutoCommit(false);

			pstmt=getConnect().prepareStatement(sql);
			pstmt.setString(1, name);
			//pstmt.setString(2, x);
			pstmt.executeUpdate();
			getConnect().commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getConnect().rollback();
		}
		getConnect().setAutoCommit(true);
	}
	
	public ArrayList<Employee> getDanhSach(String name){
		try {
			callstmt= getConnect().prepareCall("{call sp_danhsach(?)}");
			callstmt = getConnect().pr
			callstmt.setString(1, name);
			rs = callstmt.executeQuery();
			Employee emp;
			ds = new ArrayList<>();
			while(rs.next()){
				emp = new Employee(rs.getString("Name"), rs.getString("Phone"), rs.getInt("Age"));
				ds.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ds;
		
	}
public void updateUseRs(){
		
		try {
			stmt = getConnect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery("Select EmpName, Phone, Tuoi from Employees");
			
			rs.beforeFirst();
			//STEP 7: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				int newAge = rs.getInt("Tuoi") + 2;
				rs.updateDouble( "Tuoi", newAge );
				rs.updateRow();
			}
			System.out.println("List result set showing new ages...");
			//printRs(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addUseRs() {
		try {
			stmt = getConnect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery("Select EmpName, Phone, Tuoi from Employees");
			System.out.println("Inserting a new record...");
			getConnect().setAutoCommit(false);
		      rs.moveToInsertRow();
		      
		      rs.updateInt("id",104);
		      rs.updateString("first","John");
		      rs.updateString("last","Paul");
		      rs.updateInt("age",40);
		      getConnect().commit();
		      
		      //Commit row
		      rs.insertRow();

		      System.out.println("List result set showing new set...");
		      printRs(rs);
		      getConnect().setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				getConnect().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		
	}
	public void delUseRs() throws SQLException {
		try {
		getConnect().setAutoCommit(false);
			stmt = getConnect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery("Select EmpName, Phone, Tuoi from Employees ");
			 rs.absolute( 2 );
		      System.out.println("List the record before deleting...");
		      //Retrieve by column name
		      int id  = rs.getInt("id");
		      int age = rs.getInt("age");
		      String first = rs.getString("first");
		      String last = rs.getString("last");

		      //Display values
		      System.out.print("ID: " + id);
		      System.out.print(", Age: " + age);
		      System.out.print(", First: " + first);
		      System.out.println(", Last: " + last);

		     //Delete row
		      rs.deleteRow();
		      System.out.println("List result set after deleting one records...");
		      printRs(rs);
		      getConnect().commit();
		} catch (SQLException e) {
			getConnect().rollback();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void printRs(ResultSet rs) throws SQLException{
	      //Ensure we start with first row
	      rs.beforeFirst();
	      while(rs.next()){
	         //Retrieve by column name
	         String empName  = rs.getString("EmpName");
	         String phone = rs.getString("Phone");
	         int tuoi = rs.getInt("Tuoi");
	         
	         //Display values
	         System.out.print("Employee Name: " + empName);
	         System.out.print(", Phone: " + phone);
	         System.out.print(", Age: " + tuoi);
	     }
	     System.out.println();
	   }
}
