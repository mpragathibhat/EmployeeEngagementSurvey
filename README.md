# EmployeeEngagementSurvey
EES Summary Report
The application a Survey csv file and a Survey response csv file as input through console.
The File is the absolute filepath
The Summary output too is on the Console.

The input files are parsed and stored in local in memory using user defined model classes described below

Survey 
----------
Theme
List of QuestionNumbers

TextOrQuestions
----------------
Question Number
Question Type
Question Text

Employee
------------
Employee Number
Employee Id
Employee EmailId
SubmittedDate

ResponseSurvey
--------------
Question Number
List <ReponseData>

ResponseSurvey.ResponseData
--------------------------
Employee Number
Text/Question Response


The csv input files is parsed and stored as
Map<String,Surveys>  ---- Map Theme to Survey Questions
List<TextOrQuestion>  ------- All the questions/text in the survey file
List<Employee>        -------- All the employee participants in input file
Map<int,ResponseSurvey>    ------------- Map of QuestionNo to Ech employee responses to question

The above classes would make analysis of survey and generation of Survey report enhancements easy
