package com.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParsedCSVData {
	
	public static Map<String,Survey>  getSurveys() {
		return surveys;
	}

	public static void setSurveys(Map<String,Survey> surveys) {
		ParsedCSVData.surveys = surveys;
	}

	public static List<TextOrQuestion> getQuestions() {
		return questions;
	}

	public static void setQuestions(List<TextOrQuestion> questions) {
		ParsedCSVData.questions = questions;
	}

	public static List<Employee> getAllEmployees() {
		return allEmployees;
	}

	public static void setAllEmployees(List<Employee> allEmployees) {
		ParsedCSVData.allEmployees = allEmployees;
	}

	public static Map<Integer,ResponseSurvey> getRespSurveys() {
		return respSurveys;
	}

	public static void setRespSurveys(Map<Integer,ResponseSurvey> respSurveys) {
		ParsedCSVData.respSurveys = respSurveys;
	}

	private static Map<String,Survey> surveys;
	
	private static List<TextOrQuestion> questions;
	
	private static List<Employee> allEmployees;
	
	private static Map<Integer,ResponseSurvey> respSurveys;
	
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

	public static ResponseSurvey getResponseSurveyObj(int questionNumber) {
		return ParsedCSVData.respSurveys.get(questionNumber);
	}

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
