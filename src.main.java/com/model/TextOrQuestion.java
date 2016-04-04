package com.model;

public class TextOrQuestion {
	
	public static int count =0;
	
	private int questionNumber;
	
	private String questionType;
	
	private String text;
	
	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TextOrQuestion getQuestionObject(int questionNo) {
		int count= ParsedCSVData.getQuestions().size();
		for(int i=0;i < count; i++) {
			if(ParsedCSVData.getQuestions().get(i).getQuestionNumber() == questionNo ) {
				return ParsedCSVData.getQuestions().get(i);
			}
		}
		return null;
	}
	
	public TextOrQuestion(String type,  String text) {
		this.questionNumber = count++;
		this.questionType = type;
		this.text = text;	
	}
	
}
