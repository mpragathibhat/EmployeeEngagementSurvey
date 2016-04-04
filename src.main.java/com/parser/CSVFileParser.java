package com.parser;

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
    
    

	public static void readCSVFile(String fileName, boolean hasHeader) {
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
	private static void parseSurveyDataFile(String fileName, FileReader fileReader, CSVParser csvFileParser) {
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
	private static void parseSurveyResultFile(String fileName, FileReader fileReader, CSVParser csvFileParser) {
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
     * parses the csv files SurveyData into the Survey, Employee and TextOrQuestion Object
     * @param csvFileParser
     * @throws IOException
     */
	private static void parseSurveyData(CSVParser csvFileParser) throws IOException {
		//Get a list of CSV file records
		List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
		
		//Create a new list of survey and TextOrQuestions to be filled by CSV file data 
		initializeParsedCSVDataSurveyQuesList();
		//Read the CSV file records starting from the second record to skip the header
		for (int i = 1; i < csvRecords.size(); i++) {
		    CSVRecord record = csvRecords.get(i);
		    //Create a new SurveyData object and fill his data
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
		    //parse through the responses to form employee object list and responseSurvey Object list
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
			
			int surveyTextNumber = index -3;
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
		    initializeParsedCSVDataEmployeeList();
		    initializeParsedCSVDataResponseSurvey();
		    
		    
		    //Create a responseSurvey Object if not present with question number
		    ResponseSurvey responseSurveyObj = ParsedCSVData.getResponseSurveyObj(surveyTextNumber);
		    if(responseSurveyObj == null) {
		    	responseSurveyObj = new ResponseSurvey();
		    	responseSurveyObj.setQuestionNumber(surveyTextNumber);
		    }
		    //index corresponds to the answer to text at index -3 in SurveyList Object
		    responseSurveyObj.addResponseData(emp.getEmployeeNumber(),record.get(index));
		    ParsedCSVData.getRespSurveys().put(surveyTextNumber,responseSurveyObj);
		}
		ParsedCSVData.getAllEmployees().add(emp);
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
     * Sets the CSVRecord content into surveyData object
     * @param record
     */
    private static void setSurveyData(CSVRecord record) {
    	initializeParsedCSVDataSurveyQuesList();
    	String theme = record.get(SURVEY_THEME);
    	Survey surveyThemeObj = ParsedCSVData.getSurveys().get(theme);
    	if(surveyThemeObj == null) {
    		surveyThemeObj = new Survey();
    		surveyThemeObj.setTheme(theme);
    	}
    	
    	String type = record.get(SURVEY_QUES_TYPE);
    	String text = record.get(SURVEY_TEXT);
    	TextOrQuestion question = new TextOrQuestion(type,  text);
    	surveyThemeObj.addQuestionNumber(question.getQuestionNumber());
    	ParsedCSVData.getSurveys().put(theme,surveyThemeObj);
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
