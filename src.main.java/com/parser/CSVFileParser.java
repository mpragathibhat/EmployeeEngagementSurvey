package com.parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.model.Employee;
import com.model.ParsedCSVData;
import com.model.ResponseSurvey;
import com.model.Survey;
import com.model.TextOrQuestion;

public class CSVFileParser {
	
	//CSV file header
    private static final String [] FILE_HEADER_MAPPING = {"theme","type","text"};
    //survey attributes
    private static final String SURVEY_THEME = "theme";
    private static final String SURVEY_QUES_TYPE = "type";
    private static final String SURVEY_TEXT = "text";
    
    

	public static void readCSVFile(File fileName, boolean hasHeader) {
    	FileReader fileReader = null;
        
        CSVParser csvFileParser = null;
        
        if(hasHeader) {
        	parseSurveyDataFile(fileName, fileReader, csvFileParser);
        } else {
        	parseSurveyResultFile(fileName, fileReader, csvFileParser);
        }
    }

    /**
     * Parsing of the CSV Survey Data File into the Model Object Survey, Employee and TextOrQuestions Model
     * 
     * @param fileName
     * @param fileReader
     * @param csvFileParser
     */
	private static void parseSurveyDataFile(File fileName, FileReader fileReader, CSVParser csvFileParser) {
		//Create the CSVFormat object with the header mapping
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
        try {
             
            //initialize FileReader object
            fileReader = new FileReader(fileName);
             
            //initialize CSVParser object
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
             
            parseSurveyData(csvFileParser);
             
        } 
        catch (Exception e) {
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
            	if(fileReader != null) {
                 fileReader.close();
            	} if(csvFileParser!=null)
            		csvFileParser.close();
            } catch (IOException e) {
                System.out.println("Error while closing fileReader/csvFileParser !!!");
                e.printStackTrace();
            }
        }
	}
	
	/**
     * Parsing of the CSV Survey Data File into the Model inner object SurveyData
     * 
     * @param fileName
     * @param fileReader
     * @param csvFileParser
     */
	private static void parseSurveyResultFile(File fileName, FileReader fileReader, CSVParser csvFileParser) {
        CSVFormat csvFileFormat = CSVFormat.DEFAULT;
        try {
             
            //initialize FileReader object
            fileReader = new FileReader(fileName);
             
            //initialize CSVParser object
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            
            parseSurveyResult(csvFileParser);
            
             
        } 
        catch (Exception e) {
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
                System.out.println("Error while closing fileReader/csvFileParser !!!");
                e.printStackTrace();
            }
        }
	}


    /**
     * parses the csv files SurveyData into the Survey and TextOrQuestion Object
     * @param csvFileParser
     * @throws IOException
     */
	private static void parseSurveyData(CSVParser csvFileParser) throws IOException {
		//Get a list of CSV file records
		List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
		
		//Create a new Map of Theme to Question survey Map and  List TextOrQuestions to be filled by CSV file data 
		initializeParsedCSVDataSurveyQuesList();
		//Read the CSV file records starting from the second record to skip the header
		for (int i = 1; i < csvRecords.size(); i++) {
		    CSVRecord record = csvRecords.get(i);
		    //Create and fill survey Map and  List TextOrQuestions 
		    setSurveyData(record);
		}
	}
	
	/**
     * parses the csv files SurveyData into the SurveyDataList Object
     * @param csvFileParser
     * @throws IOException
     */
	private static void parseSurveyResult(CSVParser csvFileParser) throws IOException {
		//Get a list of CSV file records
		List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
		
		//Read Response records to form Employee and responseObject
		//Read the CSV file records 
		extractSurveyResponseToSurveyList(csvRecords);
	}

	private static void extractSurveyResponseToSurveyList(List<CSVRecord> csvRecords) {
		for (int i = 0; i < csvRecords.size(); i++) {
		    CSVRecord record = csvRecords.get(i);
		    //parse through the responses to form employee object list and responseSurvey Map
		    formSurveyResponse(record);
		}
	}

	private static void formSurveyResponse(CSVRecord record) {
		
		int numberOfRecords = record.size();
		Employee emp = new Employee();
		//parse through the responses of the employee for every text/question in survey 
		//response present from index 3. Every text response corresponds to one SurveyResponse Object
		for(int index=3;index < numberOfRecords; index++){
			//Create Employee Object for every response row
			//The question number would be index-3 as the question starts from the 3rdzero bsed index
			int surveyTextNumber = index -3;
			formEmployeeParsingRecord(record, emp, index);
		    initializeParsedCSVDataEmployeeList();
		    initializeParsedCSVDataResponseSurvey();
		    
		    
		    //Check os the ResponseMap already has an ResponseObj with question number seen in csv file
		    ResponseSurvey responseSurveyObj = ParsedCSVData.getRespSurveys().get(surveyTextNumber);
		    //Create new object if not present
		    if(responseSurveyObj == null) {
		    	responseSurveyObj = new ResponseSurvey();
		    	//add question No
		    	responseSurveyObj.setQuestionNumber(surveyTextNumber);
		    }
		    //For the Question no add the ResponseData i.e Employee Number and his answer for survey
		    responseSurveyObj.addResponseData(emp.getEmployeeNumber(),record.get(index));
		    //Add to the Map of ResponseSurvey
		    ParsedCSVData.getRespSurveys().put(surveyTextNumber,responseSurveyObj);
		}
		//Add employee to Employee List
		ParsedCSVData.getAllEmployees().add(emp);
	}

	// fill the employee object from the CSVRecord
	private static void formEmployeeParsingRecord(CSVRecord record, Employee emp, int index) {
		//0 corresponds to EmailId
		emp.setEmailId(record.get(0));
		//1 corresponds to Employee Id which can be blank
		if(record.get(1) != null) {
			emp.setEmpId(record.get(1));	
		}
		//2 corresponds to submitted Date which can be blank
		String submittedDate = record.get(2);
		if(submittedDate != null && submittedDate.trim().length() != 0) {
			try {
				emp.setSubmittedDate(convertStringtoDate(submittedDate));
			} catch (ParseException e) {
				System.out.println("unsupported date format " + submittedDate);
				e.printStackTrace();
			}
		}
	}

	private static void initializeParsedCSVDataResponseSurvey() {
		if(ParsedCSVData.getRespSurveys() == null) {
			ParsedCSVData.setRespSurveys(new HashMap<Integer,ResponseSurvey>());
		}
		
	}

	private static void initializeParsedCSVDataEmployeeList() {
		if(ParsedCSVData.getAllEmployees() == null) {
			ParsedCSVData.setAllEmployees(new ArrayList<Employee>());
		}
	}

	private static Date convertStringtoDate(String submittedDate) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		//formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		return formatter.parse(submittedDate.substring(0,submittedDate.indexOf('+')));
	}
	
    
    /**
     * Sets the CSVRecord content into Survey Map and TextOrQuestion List object
     * @param record
     */
    private static void setSurveyData(CSVRecord record) {
    	initializeParsedCSVDataSurveyQuesList();
    	String theme = record.get(SURVEY_THEME);
    	//Survey Map<String theme, Survey> 
    	Survey surveyThemeObj = ParsedCSVData.getSurveys().get(theme);
    	//check if Survey Object with the found theme is already present in the map
    	if(surveyThemeObj == null) {
    		//Create the Survey object and assign the new them found in csv survey file
    		surveyThemeObj = new Survey();
    		surveyThemeObj.setTheme(theme);
    	}
    	
    	String type = record.get(SURVEY_QUES_TYPE);
    	String text = record.get(SURVEY_TEXT);
    	//Create a new TextOrQuestion object for the question found in survey file
    	TextOrQuestion question = new TextOrQuestion(type,  text);
    	//The new question found in the survey file is added to the Survey Object, we group the questions with same theme together
    	surveyThemeObj.addQuestionNumber(question.getQuestionNumber());
    	//Add to Survey Map
    	ParsedCSVData.getSurveys().put(theme,surveyThemeObj);
    	//Add Question/Text to TextOrQuestion List
    	ParsedCSVData.getQuestions().add(question);
    	
    }

	private static void initializeParsedCSVDataSurveyQuesList() {
		if(ParsedCSVData.getQuestions() == null) {
    		ParsedCSVData.setQuestions(new ArrayList<TextOrQuestion>());    	
    	}
    	if(ParsedCSVData.getSurveys() == null) {
    		ParsedCSVData.setSurveys(new HashMap<String,Survey>());    	
    	}
	}
	


}
