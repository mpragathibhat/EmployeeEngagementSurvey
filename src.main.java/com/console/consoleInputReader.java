package com.console;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import com.parser.CSVFileParser;
import com.report.SummaryReport;

/**
 * Class to get the input Files from the console and generate summary report
 * @author Pbhat
 *
 */
public class consoleInputReader {
    /*
     * Constant for total number of retries
     */
	private static final int MAX_NUMBER_OF_RETRY = 5;
	private static final String INPUT_MSG = "surveyInputMsg";
	private static final String INPUT_RESPONSE_MSG = "surveyResponseInputMsg";
	private static String ERROR_INVLID_FILE="FileInputError";
	private static String MAX_RETRY="MaxRetryError";
	private static String INVALID_FILENAME="InvalidFileName";
	
	private static Properties property = new Properties();

	public static void main(String[] args) {
		consoleInputReader consoleReader = new consoleInputReader();
		consoleReader.consoleInputSurveyAndResponseFiles();
		//Display summary on Console
		SummaryReport.displaySummaryReportOnConsole();
		}

	/*
	 * Get the Filenames from console
	 */
	private void consoleInputSurveyAndResponseFiles() {
		int retryAttemp =0;
		Scanner scanner = new Scanner(System.in);
		//load properties file
		
		loadPropertyFile(property);
		//Message to be displayed on console
		boolean csvHsHeader = true;
		//get and parse the survey file
		retryAttemp = getFilePathFromCommandLine( scanner,property.getProperty(INPUT_MSG),csvHsHeader);
		//handle error on maxretry exceeds
		handleErrorOnMaxRetryAttemp(retryAttemp);
		
		//Response csv does not have header info
		csvHsHeader = false;
		//get and parse the survey response file
		retryAttemp = getFilePathFromCommandLine( scanner,property.getProperty(INPUT_RESPONSE_MSG),false);
		//handle error on maxretry exceeds
		handleErrorOnMaxRetryAttemp(retryAttemp);
	}

	
	private static void loadPropertyFile(Properties property) {
		try {
			property.load(consoleInputReader.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			System.out.println("Could not load the property file");
		}
	}

	
	private  void handleErrorOnMaxRetryAttemp(int retryAttemp) {
		if(retryAttemp > MAX_NUMBER_OF_RETRY) {
			System.out.println(property.get(ERROR_INVLID_FILE));
			System.out.println(property.getProperty(MAX_RETRY));
			System.exit(1);
		}
	}
 
	/*
	 *Get File name from input and parse them 
	 * 
	 */
	private  int getFilePathFromCommandLine(Scanner scanner, String msg,boolean isSurveyFile ) {
		int retryAttemp = 0;
		while(retryAttemp < MAX_NUMBER_OF_RETRY) {
			
			System.out.println(msg);
			System.out.flush();
			String filename = scanner.nextLine();
			File file = new File(filename);
			if(file.exists()) {
				CSVFileParser.readCSVFile(file, isSurveyFile);
				retryAttemp =0;
				break;
			} else {
				System.out.println(property.get(INVALID_FILENAME));
				retryAttemp++;
			}
		}
		return retryAttemp;
	}

	

}
