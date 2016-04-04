package com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store the all the questions to a particular theme from survey file
 * @author Pbhat
 *
 */
public class Survey {
	
	/**
	 * get Theme
	 * @return Theme
	 */
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * List of question Numbers for a theme
	 * @return List of question numbers
	 */
	public List<Integer> getQuestionNumber() {
		return questionNumber;
	}

	
	private String theme;
	
	private List<Integer> questionNumber;
	
	public void setQuestionNumber(List<Integer> questionNumber) {
		this.questionNumber = questionNumber;
	}
	
	/**
	 * Add a new question number to the theme
	 * @param number
	 */
	public void addQuestionNumber(int number){
		if(questionNumber == null) {
			questionNumber = new ArrayList<Integer>();
		}
		this.questionNumber.add(number);
	}

	public Survey() {
		
	}

	

}
