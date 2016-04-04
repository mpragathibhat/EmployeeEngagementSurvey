package com.console;

import java.io.File;
import java.util.Scanner;

import com.parser.CSVFileParser;
import com.report.SummaryReport;

public class consoleInputReader {

	private static final int MAX_NUMBER_OF_RETRY = 5;

	public static void main(String[] args) {
		int retryAttemp =0;
		Scanner scanner = new Scanner(System.in);
		String displayMsg = "Enter a Survey input file name : ";
		retryAttemp = getFilePathFromCommandLine( scanner,displayMsg,true);
		handleErrorOnMaxRetryAttemp(retryAttemp);
		
		displayMsg = "Enter a Survey results input file name : ";
		retryAttemp = getFilePathFromCommandLine( scanner,displayMsg,false);
		handleErrorOnMaxRetryAttemp(retryAttemp);
		SummaryReport.diplaySummaryReportonConsole();
		}

	private static void handleErrorOnMaxRetryAttemp(int retryAttemp) {
		if(retryAttemp > MAX_NUMBER_OF_RETRY) {
			System.out.println("Invalid file Name");
			System.out.println("Maximum retries exceeded, Please try later ... ");
			System.exit(1);
		}
	}

	private static int getFilePathFromCommandLine(Scanner scanner, String msg,boolean isSurveyFile ) {
		int retryAttemp = 0;
		while(retryAttemp < MAX_NUMBER_OF_RETRY) {
			
			System.out.println(msg);
			System.out.flush();
			String filename = scanner.nextLine();
			File file = new File(filename);
			if(file.exists()) {
				CSVFileParser.readCSVFile(file.getAbsolutePath(), isSurveyFile);
				retryAttemp =0;
				break;
			} else {
				System.out.println("The file name entered is inValid Please re-enter");
				retryAttemp++;
			}
		}
		return retryAttemp;
	}

	

}
