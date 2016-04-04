package com.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.model.ParsedCSVData;


public class CSVFileParserTest {
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadCSVFile() {
		ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("survey-1.csv").getFile());
        System.out.println(file.getAbsolutePath());
        CSVFileParser.readCSVFile(file.getAbsolutePath(), true);
			 ParsedCSVData.getQuestions();
			 ParsedCSVData.getSurveys();
	         assertEquals(2,ParsedCSVData.getSurveys().size());
	         int count = ParsedCSVData.getSurveys().size();
	         //int noOfQues[] = new int[count];
	         //noOfQues[count] = ParsedCSVData.getSurveys().values().getQuestionNumber().size();
	         //int expected[] = {3,2};
	         
	         //assertEquals(expected,noOfQues);
			ParsedCSVData.clear();
	}
	
	@Test
	public void test1ReadCSVFile() {
		ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("survey-2.csv").getFile());
        System.out.println(file.getAbsolutePath());
		CSVFileParser.readCSVFile(file.getAbsolutePath(), true);
	    assertEquals(1,ParsedCSVData.getSurveys().size());
	    assertEquals(5,ParsedCSVData.getQuestions().size());
		
		
	}
	
	@Test
	public void test3ReadCSVFile() {
		ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("survey-1-responses.csv").getFile());
        File file1 = new File(classLoader.getResource("survey-1.csv").getFile());
        System.out.println(file.getAbsolutePath());
        CSVFileParser.readCSVFile(file1.getAbsolutePath(), true);
		CSVFileParser.readCSVFile(file.getAbsolutePath(), false);
		assertEquals(6,ParsedCSVData.getAllEmployees().size());
		assertEquals(5,ParsedCSVData.getRespSurveys().size());
		
		
	}

}
