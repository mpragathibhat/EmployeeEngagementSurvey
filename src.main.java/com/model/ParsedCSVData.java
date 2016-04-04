package com.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class that stores or cache the CSV input files to help generate summary
 * @author PBhat
 *
 */
public class ParsedCSVData {
	
	/**
	 * Get Theme to Survey Object
	 * @return Map<String,Survey> Theme to Survey Object
	 */
	public static Map<String,Survey>  getSurveys() {
		return surveys;
	}
    
	/**
	 * Set Theme to Survey Map
	 * @param surveys
	 */
	public static void setSurveys(Map<String,Survey> surveys) {
		ParsedCSVData.surveys = surveys;
	}

	/**
	 * Get List of all Text/Question in Survey
	 * @return List<TextOrQuestion> List of all Text/Question in Survey
	 */
	public static List<TextOrQuestion> getQuestions() {
		return questions;
	}
    
	/**
	 * Set List of all Text/Question in Survey
	 * @param questions
	 */
	public static void setQuestions(List<TextOrQuestion> questions) {
		ParsedCSVData.questions = questions;
	}

	/**
	 * get All Employee
	 * @return  List<Employee> All Employee
	 */
	public static List<Employee> getAllEmployees() {
		return allEmployees;
	}
    
	/**
	 * set All Employee
	 * @param allEmployees
	 */
	public static void setAllEmployees(List<Employee> allEmployees) {
		ParsedCSVData.allEmployees = allEmployees;
	}

	/**
	 * get QuestionNo to ResponseSurvey Object Map
	 * @return Map<Integer,ResponseSurvey> QuestionNo to ResponseSurvey Object Map
	 */
	public static Map<Integer,ResponseSurvey> getRespSurveys() {
		return respSurveys;
	}
    
	/**
	 * set QuestionNo to ResponseSurvey Object Map
	 * @param respSurveys
	 */
	public static void setRespSurveys(Map<Integer,ResponseSurvey> respSurveys) {
		ParsedCSVData.respSurveys = respSurveys;
	}

	private static Map<String,Survey> surveys;
	
	private static List<TextOrQuestion> questions;
	
	private static List<Employee> allEmployees;
	
	private static Map<Integer,ResponseSurvey> respSurveys;
	
	/**
	 * Clear all cache
	 */
	public static void clear(){
		if(surveys != null)
			surveys.clear();
		if(questions != null) 
			questions.clear();
		TextOrQuestion.count =0;
		if(allEmployees != null)
			allEmployees.clear();
		Employee.resetCount();
		if(respSurveys != null)
			respSurveys.clear();
	}

	/**
	 * Calculate total submitted Surveys
	 * @return int total submitted Surveys
	 */
	public static int getNoOfParticipantsSubmittedSurvey() {
		Iterator<Employee> empIterator = allEmployees.iterator();
		int submittedCount = 0;
		while(empIterator.hasNext()) {
			Employee emp = empIterator.next();
			if(emp.hasEmployeeSubmittedSurvey()) {
				submittedCount++;
			}
		}
		return submittedCount;
	}

	/**
	 * Get the TextOrQuestion Object with corresponding question no 
	 * @param questionNumber int
	 * @return TextOrQuestion
	 */
	public static TextOrQuestion getQuestionForEmpNumber(int questionNumber) {
		Iterator<TextOrQuestion> quesIterator = ParsedCSVData.getQuestions().iterator();
		while(quesIterator.hasNext()) {
			TextOrQuestion res = quesIterator.next();
			if(res.getQuestionNumber() == questionNumber){
				return res;
			}
		}
		return null;	
	}

	/**
	 * Employee object with employee number
	 * @param employeeNumber
	 * @return Employee
	 */
	public static Employee getEmployeeForEmpNo(int employeeNumber) {
		int count = ParsedCSVData.allEmployees.size();
		for(int i =0; i < count ;i++) {
			if(ParsedCSVData.allEmployees.get(i).getEmployeeNumber() == employeeNumber) {
				return ParsedCSVData.allEmployees.get(i);
			}
		}
		
		return null;
	}
	
	
	

}
