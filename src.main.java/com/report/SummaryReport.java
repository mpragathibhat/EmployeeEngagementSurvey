package com.report;

import java.util.Map;
import java.util.Set;

import com.model.ParsedCSVData;
import com.model.ResponseSurvey;

public class SummaryReport {
	
	private static int totalParticipants;
	
	public static int getTotalParticipants() {
		totalParticipants = ParsedCSVData.getAllEmployees().size();
		return totalParticipants;
	}



	public static double getPercentageOfParticipation() {
		percentageOfParticipation = calculatePercentageOfParticipation();
		return percentageOfParticipation;
	}

	private static double calculatePercentageOfParticipation() {
			int total = getTotalParticipants();
			int noOfParticipantsSubmittedSurvey = ParsedCSVData.getNoOfParticipantsSubmittedSurvey();
			
			return (double)(noOfParticipantsSubmittedSurvey *100.00 / total);
		
	}


	public static Map<String, Double> getAvgRatingOfEachQuestion() {
		avgRatingOfEachQuestion = calculateAvgPerQuestion();
		return avgRatingOfEachQuestion;
	}

	private static Map<String, Double> calculateAvgPerQuestion() {
		return ResponseSurvey.calculateAvgRatingPerQuestion();
	}
	
	public static void diplaySummaryReportonConsole() {
		System.out.println("Summary of the Survey Result is: ");
		System.out.println("Number of Participants in the survey :  " + getTotalParticipants());
		System.out.println("Percentage of prticipant in survey : " + calculatePercentageOfParticipation());
		System.out.println("Average Rating by the participant in the Survey  :");
		getAvgRatingOfEachQuestion();
		Set<Map.Entry<String, Double>> avgQuestionRatingentrySet = avgRatingOfEachQuestion.entrySet();
		for(Map.Entry<String, Double> entry : avgQuestionRatingentrySet) {
			System.out.printf("%-180s %.2f %n ", entry.getKey(), entry.getValue());
		}
		
	}

	private static  double percentageOfParticipation;
	
    private static Map<String,Double>	avgRatingOfEachQuestion;
    
    

}
