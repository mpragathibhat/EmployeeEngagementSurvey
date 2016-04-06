package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class to store the question or Text response of Employee from survey response file
 * @author PBhat
 *
 */
public class ResponseSurvey {
	
	/**
	 * Unique number assigned to each question found in the order seen in csv file
	 * @return question number
	 */
	public int getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * set question Number 
	 * @param questionNumber
	 */
	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	/**
	 * Get list of EmpNo to his response for a question of responseSurvey question no
	 * @return List<ResponseData> list of EmpNo to his response for a question of responseSurvey question no
	 */
	public List<ResponseData> getResponseList() {
		if(responseList == null) {
			responseList = new ArrayList<ResponseData>();
		}
		return responseList;
	}
	
	/**
	 * Calculate avg rating provided by participant for each question in survey
	 * @return Map<String,Double> Map of text/Question to avg rating provided by participant
	 */
	public static Map<String, Double> calculateAvgRatingPerQuestion() {
		Iterator<TextOrQuestion> questIterator = ParsedCSVData.getQuestions().iterator();
		Map<String,Double> avgRatingForQuestion =new HashMap<String,Double>();
		while(questIterator.hasNext()) {
			TextOrQuestion txtOrQues = questIterator.next();
			//get only question of rating type to calculate its avg rating
			if(txtOrQues.getQuestionType().equalsIgnoreCase("ratingquestion")){
				//Get ResponseSurvey Object for that question
				ResponseSurvey responseObj = ParsedCSVData.getRespSurveys().get(txtOrQues.getQuestionNumber());
				//Get all the ResponseData i.e, empNo to Response rating List
				Iterator<ResponseData> respListIterator = responseObj.getResponseList().iterator();
				int sumOfRatingValues=0, totalNoOfQuestions=0;
				while(respListIterator.hasNext()) {
					ResponseData empNoResp = respListIterator.next();
					Employee emp = ParsedCSVData.getEmployeeForEmpNo(empNoResp.getEmployeeNumber());
					String ratingAnswers = empNoResp.getAnswer();
					if(ratingAnswers!=null && ratingAnswers.trim().length() > 0 && emp.hasEmployeeSubmittedSurvey()) {
						int rating = convertRatingToInt(ratingAnswers);
						if(rating != -1) {
							sumOfRatingValues +=rating;
							totalNoOfQuestions++;
						}
					}
					
				}
				double avgRating = (double)(sumOfRatingValues)/(double)totalNoOfQuestions;
				
				avgRatingForQuestion.put(txtOrQues.getQuestionObject(txtOrQues.getQuestionNumber()).getText(), avgRating);
			}
			
		}
		//If the responseData List size is greater than the Questions list size then there is some mismatch in input files provided
		//we could use this to warn the user after report generation currently not handled
		return avgRatingForQuestion;
	}

	private static int convertRatingToInt(String ratingAnswers) {
		int rating=0;	
	
		try {
			rating = Integer.parseInt(ratingAnswers);
		} catch(NumberFormatException ex) {
			//Some problem with input file provided I can mention that to user but donot want to stop the summary report generation
			//Could have an boolean to mention an error has happened and could mention a warning after report generation completion
			//currently not handled
			return -1;
		}
		return rating;
	}
	
	/**
	 * set ReponseData List
	 * @param responseList
	 */
	public void setResponseList(List<ResponseData> responseList) {
		this.responseList = responseList;
	}

	
	private int questionNumber;
	
	/**
	 * EmpNumber to the response provided by employee
	 * @author pBhat
	 *
	 */
	 class ResponseData {
		public int getEmployeeNumber() {
			return employeeNumber;
		}
		public String getAnswer() {
			return answer;
		}
		public ResponseData(int employeeNumber, String answer) {
			super();
			this.employeeNumber = employeeNumber;
			this.answer = answer;
		}
		private int employeeNumber;
		private String answer;
	}

	private List<ResponseData> responseList;

	/**
	 * Add response data
	 * @param employeeNumber
	 * @param answer
	 */
	public void addResponseData(int employeeNumber, String answer) {
		this.getResponseList().add(new ResponseData(employeeNumber, answer));
		
	}
}
