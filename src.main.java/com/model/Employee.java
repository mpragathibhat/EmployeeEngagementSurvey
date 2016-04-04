package com.model;

import java.util.Date;

/**
 * Model Class to hold employee data who participated in survey 
 * @author PBhat
 *
 */
public class Employee {
	
	/**
	 * Constructor set Employee Number a unique per employee increment number 
	 */
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

	/**
	 * Unique number for employee incremented programmatically
	 */
	private int employeeNumber;
	
	/**
	 * Email Id from Survey input file
	 */
	private String emailId;
	
	/**
	 * Emp Id from Survey input file
	 */
	private String empId;
	
	/**
	 * Date to store survey submit time
	 */
	private Date submittedDate;
    
	/**
	 * To check if Employee hs submitted survey
	 * @return boolean true if employee has submitted survey else false
	 */
	public boolean hasEmployeeSubmittedSurvey(){
		if(this.submittedDate == null ){
			return false;
		}
		return true;
	}
	
	
}
