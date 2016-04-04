package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResponseSurvey {
	
	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public List<ResponseData> getResponseList() {
		if(responseList == null) {
			responseList = new ArrayList<ResponseData>();
		}
		return responseList;
	}
	
		
	public static Map<String, Double> calculateAvgRatingPerQuestion() {
		Iterator<TextOrQuestion> questIterator = ParsedCSVData.getQuestions().iterator();
		Map<String,Double> avgRatingForQuestion =new HashMap<String,Double>();
		while(questIterator.hasNext()) {
			TextOrQuestion txtOrQues = questIterator.next();
			if(txtOrQues.getQuestionType().equalsIgnoreCase("ratingquestion")){
				//avgRatingForQuestion = new HashMap<String,Double>();
				ResponseSurvey responseObj = ParsedCSVData.getRespSurveys().get(txtOrQues.getQuestionNumber());
				Iterator<ResponseData> respListIterator = responseObj.getResponseList().iterator();
				int sumOfRatingValues=0, totalNoOfQuestions=0;
				while(respListIterator.hasNext()) {
					ResponseData empNoResp = respListIterator.next();
					Employee emp = ParsedCSVData.getEmployeeForEmpNo(empNoResp.getEmployeeNumber());
					String ratingAnswers = empNoResp.getAnswer();
					if(ratingAnswers!=null && ratingAnswers.trim().length() > 0 && emp.hasEmployeeSubmittedSurvey()) {
						sumOfRatingValues +=Integer.parseInt(ratingAnswers);
						totalNoOfQuestions++;
					}
					
				}
				double avgRating = (double)(sumOfRatingValues)/(double)totalNoOfQuestions;
				/*int questionNo= txtOrQues.getQuestionNumber();
				Iterator<TextOrQuestion> quesIte = ParsedCSVData.getQuestions().iterator();
				String quesText = null;
				while(quesIte.hasNext()) {
					TextOrQuestion ques = quesIte.next();
					if(ques.getQuestionNumber() == questionNo) {
						quesText = ques.getText();
					}
				}*/
				avgRatingForQuestion.put(txtOrQues.getQuestionObject(txtOrQues.getQuestionNumber()).getText(), avgRating);
			}
		}
		return avgRatingForQuestion;
	}
	public void setResponseList(List<ResponseData> responseList) {
		this.responseList = responseList;
	}

	private int questionNumber;
	
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

	
	public void addResponseData(int employeeNumber, String answer) {
		this.getResponseList().add(new ResponseData(employeeNumber, answer));
		
	}
}
