package com.report;

import java.util.Map;
import java.util.Set;

import com.model.ParsedCSVData;
import com.model.ResponseSurvey;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Class to generate the summary report for the Survey
 * @author Pbhat
 *
 */
public class SummaryReport {
	
	/**
	 * Total number of prticipants in survey
	 */
	private static int totalParticipants;
	
	/**
	 * Get the count of total participants in the survey
	 * @return int total Participants
	 */
	public static int getTotalParticipants() {
		if(ParsedCSVData.getAllEmployees() != null)
			totalParticipants = ParsedCSVData.getAllEmployees().size();
		return totalParticipants;
	}


    /**
     * Get percentage of particiption 
     * @return double percentage of particiption
     */
	public static double getPercentageOfParticipation() {
		percentageOfParticipation = calculatePercentageOfParticipation();
		return percentageOfParticipation;
	}

	 
	
	private static double calculatePercentageOfParticipation() {
			int total = getTotalParticipants();
			int noOfParticipantsSubmittedSurvey = ParsedCSVData.getNoOfParticipantsSubmittedSurvey();
			if(total == 0) 
				return 0;
			return (double)(noOfParticipantsSubmittedSurvey *100.00 / total);
		
	}

    /**
     * get Map of question to avgRating by all participants for that question
     * @return Map<String,Double> Map of question to avgRating by all participants for that question 
     */
	public static Map<String, Double> getAvgRatingOfEachQuestion() {
		avgRatingOfEachQuestion = calculateAvgPerQuestion();
		return avgRatingOfEachQuestion;
	}

	private static Map<String, Double> calculateAvgPerQuestion() {
		return ResponseSurvey.calculateAvgRatingPerQuestion();
	}
	
	/**
	 * Generate the final report to console
	 */
	public static void displaySummaryReportOnConsole() {
		System.out.format("%-100s %n%n", "Summary of the Survey Result is: ");
		System.out.format("%-100s %d%n","Number of Participants in the survey :  " , getTotalParticipants());
		System.out.format("%-100s %.2f%n%n","Percentage of prticipant in survey : " , calculatePercentageOfParticipation());
		System.out.format("%-100s %n%n" , "Average Rating by the participant in the Survey  :");
		getAvgRatingOfEachQuestion();
		Set<Map.Entry<String, Double>> avgQuestionRatingentrySet = avgRatingOfEachQuestion.entrySet();
		for(Map.Entry<String, Double> entry : avgQuestionRatingentrySet) {
			System.out.format("%-100s %.2f %n %n", WordUtils.wrap(entry.getKey().trim(), 95), entry.getValue());
		}
		
	}

	private static  double percentageOfParticipation;
	
    private static Map<String,Double>	avgRatingOfEachQuestion;
    
    

}
