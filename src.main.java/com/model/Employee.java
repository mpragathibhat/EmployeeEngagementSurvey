package com.model;

import java.util.Date;
import java.util.List;

public class Employee {
	
	public Employee() {
		employeeNumber = count++;
	}
	private static int count = 0;
	
	public static void resetCount() {
		count =0 ;
	}
	
	public int getEmployeeNumber() {
		return employeeNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	private int employeeNumber;
	
	private String emailId;
	
	private String empId;
	
	private Date submittedDate;
    
	public boolean hasEmployeeSubmittedSurvey(){
		if(this.submittedDate == null ){
			return false;
		}
		return true;
	}
	
	public List<Employee> getEmployees() {
		return null;
	}
	
	public Employee getEmployee(int empNum) {
		return null;
	}
}
