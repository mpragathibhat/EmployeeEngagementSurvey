package com.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Survey {
	
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public List<Integer> getQuestionNumber() {
		return questionNumber;
	}

	
	private String theme;
	
	private List<Integer> questionNumber;
	
	public void setQuestionNumber(List<Integer> questionNumber) {
		this.questionNumber = questionNumber;
	}
	public void addQuestionNumber(int number){
		if(questionNumber == null) {
			questionNumber = new ArrayList<Integer>();
		}
		this.questionNumber.add(number);
	}

	public Survey() {
		
	}

	public static Survey getThemeSurveyObject(List<Survey> surveys, String theme2) {
		Iterator<Survey> surveyIterator = surveys.iterator();
		while(surveyIterator.hasNext()) {
			Survey surObj = surveyIterator.next();
			if(surObj.theme.equalsIgnoreCase(theme2)) {
				return surObj;
			}
		}
		return null;
	}

}
