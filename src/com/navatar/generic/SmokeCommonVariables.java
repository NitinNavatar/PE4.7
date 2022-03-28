/**
 * 
 */
package com.navatar.generic;

import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.List;

import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.BasePageErrorMessage;
//import com.navatar.scripts.SmokeTestCases;

import static com.navatar.generic.BaseLib.*;



public class SmokeCommonVariables {
	
	public static String appName,appVersion;
	public static String superAdminUserName,superAdminRegistered,adminPassword;
	public static String AdminUserFirstName,AdminUserLastName,AdminUserEmailID;
	public static String crmUser1FirstName,crmUser1LastName,crmUser1EmailID,crmUserProfile,crmUserLience;
	public static String crmUser3FirstName,crmUser3LastName,crmUser3EmailID,crmUser3Profile,crmUser3Lience;
	public static String gmailUserName,gmailUserName2,gmailPassword;
	
	public static String Smoke_TaskINS1Name,Smoke_TaskINS1RecordType;
	public static String Smoke_TaskINS2Name,Smoke_TaskINS2RecordType;
	public static String Smoke_TaskINS3Name,Smoke_TaskINS3RecordType;
	public static String Smoke_TaskINS4Name,Smoke_TaskINS4RecordType;
	public static String Smoke_TaskINS5Name,Smoke_TaskINS5RecordType,Smoke_TaskINS5Website;
	
	public static String Smoke_MTINS1Name,Smoke_MTINS1RecordType;
	public static String Smoke_MTINS2Name,Smoke_MTINS2RecordType;
	public static String Smoke_MTINS3Name,CommonVariables;
	public static String Smoke_MTINS4Name,Smoke_MTINS4RecordType;
	public static String Smoke_MTINS5Name,Smoke_MTINS5RecordType;
	public static String Smoke_MTINS6Name,Smoke_MTINS6RecordType,Smoke_MTINS6Status;
	public static String Smoke_MTINS7Name,Smoke_MTINS7RecordType,Smoke_MTINS7Status;
	public static String Smoke_MTINS8Name,Smoke_MTINS8RecordType;
	public static String Smoke_MTINS9Name,Smoke_MTINS9RecordType;
	
	public static String Smoke_TaskContact1FName,Smoke_TaskContact1LName,Smoke_TaskContact1INSName,Smoke_TaskContact1EmailID,Smoke_TaskContact1RecordType;
	public static String Smoke_TaskContact2FName,Smoke_TaskContact2LName,Smoke_TaskContact2INSName,Smoke_TaskContact2EmailID,Smoke_TaskContact2RecordType;
	public static String Smoke_TaskContact3FName,Smoke_TaskContact3LName,Smoke_TaskContact3INSName,Smoke_TaskContact3EmailID,Smoke_TaskContact3RecordType;
	public static String Smoke_TaskContact4FName,Smoke_TaskContact4LName,Smoke_TaskContact4INSName,Smoke_TaskContact4EmailID,Smoke_TaskContact4RecordType,Smoke_TaskContact4UpdatedName,Smoke_TaskContact4Title;
	public static String Smoke_TaskContact5FName,Smoke_TaskContact5LName,Smoke_TaskContact5INSName,Smoke_TaskContact5EmailID,Smoke_TaskContact5RecordType;
	
	public static String Smoke_MTContact1FName,Smoke_MTContact1LName,Smoke_MTContact1INSName,Smoke_MTContact1EmailID,Smoke_MTContact1RecordType;
	public static String Smoke_MTContact2FName,Smoke_MTContact2LName,Smoke_MTContact2INSName,Smoke_MTContact2EmailID,Smoke_MTContact2RecordType;
	public static String Smoke_MTContact3FName,Smoke_MTContact3LName,Smoke_MTContact3INSName,Smoke_MTContact3EmailID,Smoke_MTContact3RecordType;
	public static String Smoke_MTContact4FName,Smoke_MTContact4LName,Smoke_MTContact4INSName,Smoke_MTContact4EmailID,Smoke_MTContact4RecordType;
	public static String Smoke_MTContact5FName,Smoke_MTContact5LName,Smoke_MTContact5INSName,Smoke_MTContact5EmailID,Smoke_MTContact5RecordType;
	
	
	public static String Smoke_TaskFund1Name,Smoke_TaskFund1Type,Smoke_TaskFund1InvestmentCategory,Smoke_TaskFund1RecordType;
	public static String Smoke_TaskFund2Name,Smoke_TaskFund2Type,Smoke_TaskFund2InvestmentCategory,Smoke_TaskFund2RecordType;
	
	public static String Smoke_MTFund1Name,Smoke_MTFund1Type,Smoke_MTFund1InvestmentCategory,Smoke_MTFund1RecordType;
	public static String Smoke_MTFund2Name,Smoke_MTFund2Type,Smoke_MTFund2InvestmentCategory,Smoke_MTFund2RecordType;
	public static String tabCustomObj,tabCustomObjField;
	public static String tabObj1,tabObj2,tabObj3,tabObj4,tabObj5,tabObj6,tabObj7;
	public static String taskCustomObj1Name,taskCustomObj1RecordType;
	public static String taskCustomObj3Name,taskCustomObj3RecordType;
	public static String taskCustomObj2Name,taskCustomObj2RecordType,taskCustomObj2UpdatedName;
	public static String meetingCustomObj1Name,meetingCustomObj1RecordType;
	public static String meetingCustomObj2Name,meetingCustomObj2RecordType;
	public static String taskCompletedEvent1Subject,taskCompletedEvent2Subject,taskCompletedEvent3Subject;
	public static String taskUpcomingEvent1Subject,taskUpcomingEvent2Subject,taskUpcomingEvent3Subject;
	public static String meetingCompletedEvent1Subject,meetingCompletedEvent2Subject,meetingCompletedEvent3Subject;
	public static String meetingUpcomingEvent1Subject,meetingUpcomingEvent2Subject,meetingUpcomingEvent3Subject;
	public static String todaysDate,todaysDateSingleMonth,todaysDateSingleDate,todaysDateSingleDateSingleMonth;
	public static String yesterdaysDate,dayBeforeYesterdaysDate,tomorrowsDate,dayAfterTomorrowsDate;
	public static String startingTime,endTime;
	public static String meetingType;
	public static String Smoke_Task1LogACallSubject,Smoke_Task1LogACallPriority,Smoke_Task1LogACallComment,Smoke_Task1LogACallUpdatedSubject,Smoke_Task1LogACallUpdatedPriority,Smoke_TaskSTDLogACall1UpdatedPriority,Smoke_TaskSTDLogACall1UpdatedComment;
	public static String Smoke_Task2LogACallSubject,Smoke_Task2LogACallPriority,Smoke_Task2LogACallComment;
	public static String Smoke_TaskSTDLogACall1Subject,Smoke_TaskSTDLogACall1Priority,Smoke_TaskSTDLogACall1Comment,Smoke_TaskSTDLogACall1Status,Smoke_TaskSTDLogACall1UpdatedSubject;
	public static String Smoke_CallSubject,Smoke_CallMeetingType,eventMeetingType;

	public static String Smoke_Task3Priority,Smoke_Task2UpdatedSubject,Smoke_BoardMeetingTaskSubject,Smoke_BoardMeetingTaskMeetingType,Smoke_ClientMeetingTaskSubject,Smoke_ClientMeetingTaskMeetingType;

	public static String Smoke_LogACall1Subject,Smoke_LogACall1MeetingType,Smoke_Task2MeetingType,Smoke_LogACall1Date,Smoke_LogACall1Comment;

	public static String Smoke_Task1Subject,Smoke_Task1Date,Smoke_Task1MeetingType,Smoke_Task1Status,Smoke_Task1Comment,Smoke_Task1Priority,Smoke_Task1UpdatedPriority,Smoke_Task1UpdatedSubject;
	public static String Smoke_Task2Subject,Smoke_Task2Status,Smoke_Task3Subject,Smoke_Task2Comment,Smoke_Task2Priority,Smoke_Task2UpdatedPriority,Smoke_Task2DueDate;
	
	public static String Smoke_Meeting2Subject,Smoke_Meeting2Priority;
	public static String Smoke_TaskSendLetterSubject,Smoke_TaskSendLetterPriority,Smoke_TaskSendLetterComment,Smoke_TaskSendLetterMeetingType;
	public static String Smoke_Task20Subject,Smoke_Task20Comment,Smoke_Task20Status;
	public static String Smoke_Task2LogACallNewSubject,Smoke_Task2LogACallNewComment,Smoke_Task2LogACallNewPriority,Smoke_Task2LogACallNewStatus,Smoke_Task2LogACallNewDate,Smoke_Task2LogACallNewUpdatedSubject,Smoke_Task2LogACallNewUpdatedPriority;
	public static String Smoke_Task2MultipleSubject,Smoke_Task2MultiplePriority,Smoke_Task2MultipleComment,Smoke_Task2MultipleStatus,Smoke_Task2MultipleUpdatedSubject,Smoke_Task2MultipleDueDate;
	public static String Smoke_STDTask1Subject,Smoke_STDTask1MeetingType,Smoke_STDTask1DueDate,Smoke_STDTask1Comment,Smoke_STDTask1UpdatedSubject,Smoke_STDTask1UpdatedPriority,Smoke_STDTask1UpdatedComment;
	public static String Smoke_TaskSTD1Subject,Smoke_TaskSTD1MeetingType,Smoke_TaskSTD1DueDate,Smoke_TaskSTD1Comment,Smoke_TaskSTD1UpdatedSubject,Smoke_TaskSTD1UpdatedPriority,Smoke_TaskSTD1UpdatedComment;
	public static String Smoke_FinalDiscussionTaskSubject,Smoke_FinalDiscussionTaskMeetingType;
	public static String Smoke_DealEvalutionMeetingSubject,Smoke_DealEvalutionMeetingType;
	public static String Smoke_DealClosureMeetingSubject,Smoke_DealClosureMeetingType;
	public static String Smoke_DealProgressReviewMeetingSubject;
	public static String Smoke_S1TestSubject,Smoke_S1TestDueDate;
	public static String Smoke_S2TestSubject,Smoke_S2TestDueDate;
	public static String Smoke_S3TestSubject,Smoke_S3TestMeetingType,Smoke_S3TestDueDate;
	public static String Smoke_A1TaskSubject;
	public static String Smoke_A2TaskSubject,Smoke_A2TaskStatus;
	public static String Smoke_R1CallSubject,Smoke_R1CallStatus;
	public static String Smoke_R2CallSubject;

	public static String Smoke_DetailPageMeetingSubject,Smoke_DetailPageMeetingType;
	public static String Smoke_DetailPageNewTaskMultipleSubject;
	public static String Smoke_DetailPageLogACallMultipleSubject,Smoke_DetailPageLogACallMultipleDate;
	

	public static String Smoke_TestListEmailSubject,Smoke_TestListEmailComment,Smoke_TestListEmailStatus,Smoke_TestListEmailDate;

	
	public static String yesterday,tommorrow,dayBeforeYesterday,dayAfterTommorrow;
	
	public static String Smoke_STDTask2OnSubject,Smoke_STDTask2OnDate,Smoke_STDTask2OnComment;
	
	public static String Smoke_EntityMeeting1Subject,Smoke_EntityMeeting1Priority,Smoke_EntityMeeting1Type,Smoke_EntityMeeting1DueDate;
	
	public static String Smoke_EntityMeeting2Subject,Smoke_EntityMeeting2Status,Smoke_EntityMeeting2Type,Smoke_EntityMeeting2DueDate;
	
	public static String Smoke_AAField1DataType,Smoke_AAField1FieldLabel,Smoke_AAField1Length;
	public static String Smoke_AAField2DataType,Smoke_AAField2FieldLabel,Smoke_AAField2Length,Smoke_AAField2DecimalPlaces;
	public static String Smoke_AAField3DataType,Smoke_AAField3FieldLabel,Smoke_AAField3Options;
	
	public static String Smoke_WATCHINS1Name,Smoke_WATCHINS1RecordType;
	public static String Smoke_WATCHINS2Name,Smoke_WATCHINS2RecordType;
	public static String Smoke_WATCHINS3Name,Smoke_WATCHINS3RecordType,Smoke_WATCHINS3Status;
	public static String Smoke_WATCHINS4Name,Smoke_WATCHINS4RecordType,Smoke_WATCHINS4Status;
	public static String Smoke_WATCHINS5Name,Smoke_WATCHINS5RecordType,Smoke_WATCHINS5Status;
	public static String Smoke_WATCHINS6Name,Smoke_WATCHINS6RecordType,Smoke_WATCHINS6Status;
	public static String Smoke_MAXDATAINS1Name,Smoke_MAXDATAINS1RecordType;
	
	public static String Smoke_WATCHContact1FName,Smoke_WATCHContact1LName,Smoke_WATCHContact1INSName,Smoke_WATCHContact1EmailID,Smoke_WATCHContact1RecordType;
	public static String Smoke_WATCHContact2FName,Smoke_WATCHContact2LName,Smoke_WATCHContact2INSName,Smoke_WATCHContact2EmailID,Smoke_WATCHContact2RecordType;
	public static String Smoke_WATCHContact3FName,Smoke_WATCHContact3LName,Smoke_WATCHContact3INSName,Smoke_WATCHContact3EmailID,Smoke_WATCHContact3RecordType;
	public static String Smoke_WATCHContact4FName,Smoke_WATCHContact4LName,Smoke_WATCHContact4INSName,Smoke_WATCHContact4EmailID,Smoke_WATCHContact4RecordType;
	public static String Smoke_WATCHContact5FName,Smoke_WATCHContact5LName,Smoke_WATCHContact5INSName,Smoke_WATCHContact5EmailID,Smoke_WATCHContact5RecordType;
	public static String Smoke_WATCHContact6FName,Smoke_WATCHContact6LName,Smoke_WATCHContact6INSName,Smoke_WATCHContact6EmailID,Smoke_WATCHContact6RecordType;
	public static String Smoke_WATCHContact7FName,Smoke_WATCHContact7LName,Smoke_WATCHContact7INSName,Smoke_WATCHContact7EmailID,Smoke_WATCHContact7RecordType;
	public static String Smoke_WATCHContact8FName,Smoke_WATCHContact8LName,Smoke_WATCHContact8INSName,Smoke_WATCHContact8EmailID,Smoke_WATCHContact8RecordType;
	public static String Smoke_MAXDATAContact1FName,Smoke_MAXDATAContact1LName,Smoke_MAXDATAContact1INSName,Smoke_MAXDATAContact1EmailID,Smoke_MAXDATAContact1RecordType;
	
	public static String Smoke_MAXDATAFund1Name,Smoke_MAXDATAFund1Type,Smoke_MAXDATAFund1InvestmentCategory,Smoke_MAXDATAFund1RecordType;
	
	public static String MAXDATACustomObj1Name,MAXDATACustomObj1RecordType;
	public static String WATCHCustomObj1Name,WATCHCustomObj1RecordType;
	
	public static String Smoke_STTaskFromContactSubject,Smoke_STTaskFromContactMeetingType,Smoke_STTaskFromContactDueDate,Smoke_STTaskFromContactStatus;
	
	public static String Smoke_MTGTaskFromContactSubject,Smoke_MTGTaskFromContactMeetingType,Smoke_MTGTaskFromContactDueDate,Smoke_MTGTaskFromContactStatus;
	
	public static String Smoke_LAC1Subject,Smoke_LAC1DueDate;

	public static String Smoke_TaskMTA1Subject,Smoke_TaskMTA1MeetingType,Smoke_TaskMTA1DueDate;
	
	public static String CreatedMeetingTypeOption;

	public static String Smoke_MTTest1Subject,Smoke_MTTest1MeetingType,Smoke_MTTest1DueDate,Smoke_MTTest1Status;
	
	
	public SmokeCommonVariables(Object obj) {
		// TODO Auto-generated constructor stub
		long StartTime = System.currentTimeMillis();
		//if(obj instanceof SmokeTestCases){
			appName=ExcelUtils.readDataFromPropertyFile("AppName");
			appVersion=ExcelUtils.readDataFromPropertyFile("AppVersion");
			tabCustomObj=ExcelUtils.readDataFromPropertyFile("CustomTabName");
			tabObj1=ExcelUtils.readDataFromPropertyFile("Object1");
			tabObj2=ExcelUtils.readDataFromPropertyFile("Object2");
			tabObj3=ExcelUtils.readDataFromPropertyFile("Object3");
			tabObj4=ExcelUtils.readDataFromPropertyFile("Object4");
			tabObj5=ExcelUtils.readDataFromPropertyFile("Object5");
			tabObj6=ExcelUtils.readDataFromPropertyFile("Object6");
			tabObj7=ExcelUtils.readDataFromPropertyFile("Object7");
			tabCustomObjField=ExcelUtils.readDataFromPropertyFile("CustomTabFieldName");
			System.err.println("smokeExcelPathCommonVariable : "+testCasesFilePath);
			todaysDate=getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
			todaysDateSingleDate=getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/d/YYYY");
			todaysDateSingleDateSingleMonth=getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/YYYY");
			todaysDateSingleMonth=getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/dd/YYYY");
			yesterdaysDate=previousOrForwardDate(-1, "M/d/YYYY");
			dayBeforeYesterdaysDate=previousOrForwardDate(-2, "M/d/YYYY");
			tomorrowsDate=previousOrForwardDate(1, "M/d/YYYY");
			dayAfterTomorrowsDate=previousOrForwardDate(2, "M/d/YYYY");

			//****************************************************************	SuperAdmin And CRM User **********************************************************//

			superAdminUserName=ExcelUtils.readDataFromPropertyFile("SuperAdminUsername");
			superAdminRegistered=ExcelUtils.readDataFromPropertyFile("SuperAdminRegistered");
			
			AdminUserFirstName=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "AdminUser", excelLabel.User_First_Name);
			AdminUserLastName=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "AdminUser", excelLabel.User_Last_Name);
			AdminUserEmailID=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "AdminUser", excelLabel.User_Email);
		
			adminPassword=ExcelUtils.readDataFromPropertyFile("password");
			gmailUserName=ExcelUtils.readDataFromPropertyFile("gmailUserName");
			gmailUserName2=ExcelUtils.readDataFromPropertyFile("gmailUserName2");
			gmailPassword=ExcelUtils.readDataFromPropertyFile("gmailPassword");
			crmUser1FirstName=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User1", excelLabel.User_First_Name);
			crmUser1LastName=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User1", excelLabel.User_Last_Name);
			crmUser1EmailID=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User1", excelLabel.User_Email);
			crmUserProfile=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User1", excelLabel.User_Profile);
			crmUserLience=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User1", excelLabel.User_License);
			
			
			crmUser3FirstName=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User3", excelLabel.User_First_Name);
			crmUser3LastName=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User3", excelLabel.User_Last_Name);
			crmUser3EmailID=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User3", excelLabel.User_Email);
			crmUser3Profile=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User3", excelLabel.User_Profile);
			crmUser3Lience=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User3", excelLabel.User_License);


			//****************************************************************	EntityorAccount **********************************************************//


			// TASK INS1..............
			Smoke_TaskINS1Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS1", excelLabel.Institutions_Name);
			Smoke_TaskINS1RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS1", excelLabel.Record_Type);

			// TASK INS2..............
			Smoke_TaskINS2Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS2", excelLabel.Institutions_Name);
			Smoke_TaskINS2RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS2", excelLabel.Record_Type);

			// TASK INS3..............
			Smoke_TaskINS3Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS3", excelLabel.Institutions_Name);
			Smoke_TaskINS3RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS3", excelLabel.Record_Type);

			// TASK INS4..............
			Smoke_TaskINS4Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS4", excelLabel.Institutions_Name);
			Smoke_TaskINS4RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS4", excelLabel.Record_Type);

			// TASK INS5..............
			Smoke_TaskINS5Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS5", excelLabel.Institutions_Name);
			Smoke_TaskINS5RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS5", excelLabel.Record_Type);
			Smoke_TaskINS5Website="www.facebook.com";
			
			// MEETING INS1..............
			Smoke_MTINS1Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS1", excelLabel.Institutions_Name);
			Smoke_MTINS1RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS1", excelLabel.Record_Type);

			// MEETING INS2..............
			Smoke_MTINS2Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS2", excelLabel.Institutions_Name);
			Smoke_MTINS2RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS2", excelLabel.Record_Type);

			// MEETING INS3..............
			Smoke_MTINS3Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS3", excelLabel.Institutions_Name);

			// MEETING INS4..............
			Smoke_MTINS4Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS4", excelLabel.Institutions_Name);
			Smoke_MTINS4RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS4", excelLabel.Record_Type);

			// MEETING INS5..............
			Smoke_MTINS5Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS5", excelLabel.Institutions_Name);
			Smoke_MTINS5RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS5", excelLabel.Record_Type);

			// MEETING INS6..............
			Smoke_MTINS6Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS6", excelLabel.Institutions_Name);
			Smoke_MTINS6RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS6", excelLabel.Record_Type);
			Smoke_MTINS6Status="Under Evaluation";
			Smoke_MTINS7Status="Watchlist";
			// MEETING INS7..............
			Smoke_MTINS7Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS7", excelLabel.Institutions_Name);
			Smoke_MTINS7RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS7", excelLabel.Record_Type);

			// MEETING INS8..............
			Smoke_MTINS8Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS8", excelLabel.Institutions_Name);
			Smoke_MTINS8RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS8", excelLabel.Record_Type);

			// MEETING INS9..............
			Smoke_MTINS9Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS9", excelLabel.Institutions_Name);
			Smoke_MTINS9RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS9", excelLabel.Record_Type);


			//****************************************************************	Contact **********************************************************//

			// TASK Contact1..............
			Smoke_TaskContact1FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC1", excelLabel.Contact_FirstName);
			Smoke_TaskContact1LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC1", excelLabel.Contact_LastName);
			Smoke_TaskContact1INSName=Smoke_TaskINS1Name;
			Smoke_TaskContact1EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC1", excelLabel.Contact_EmailId);
			Smoke_TaskContact1RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC1", excelLabel.Record_Type);
			
			// TASK Contact2..............
			Smoke_TaskContact2FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC2", excelLabel.Contact_FirstName);
			Smoke_TaskContact2LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC2", excelLabel.Contact_LastName);
			Smoke_TaskContact2INSName=Smoke_TaskINS1Name;
			Smoke_TaskContact2EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC2", excelLabel.Contact_EmailId);
			Smoke_TaskContact2RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC2", excelLabel.Record_Type);
			
			// TASK Contact3..............
			Smoke_TaskContact3FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC3", excelLabel.Contact_FirstName);
			Smoke_TaskContact3LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC3", excelLabel.Contact_LastName);
			Smoke_TaskContact3INSName=Smoke_TaskINS2Name;
			Smoke_TaskContact3EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC3", excelLabel.Contact_EmailId);
			Smoke_TaskContact3RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC3", excelLabel.Record_Type);

			// TASK Contact4..............
			Smoke_TaskContact4FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC4", excelLabel.Contact_FirstName);
			Smoke_TaskContact4LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC4", excelLabel.Contact_LastName);
			Smoke_TaskContact4INSName=Smoke_TaskINS2Name;
			Smoke_TaskContact4EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC4", excelLabel.Contact_EmailId);
			Smoke_TaskContact4RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC4", excelLabel.Record_Type);
			Smoke_TaskContact4UpdatedName=Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName+"UP";
			Smoke_TaskContact4Title="Advisor";
			
			// TASK Contact5..............
			Smoke_TaskContact5FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC5", excelLabel.Contact_FirstName);
			Smoke_TaskContact5LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC5", excelLabel.Contact_LastName);
			Smoke_TaskContact5INSName=Smoke_TaskINS4Name;
			Smoke_TaskContact5EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC5", excelLabel.Contact_EmailId);
			Smoke_TaskContact5RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC5", excelLabel.Record_Type);
			
			// MT Contact1..............
			Smoke_MTContact1FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC1", excelLabel.Contact_FirstName);
			Smoke_MTContact1LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC1", excelLabel.Contact_LastName);
			Smoke_MTContact1INSName=Smoke_MTINS1Name;
			Smoke_MTContact1EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC1", excelLabel.Contact_EmailId);
			Smoke_MTContact1RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC1", excelLabel.Record_Type);

			// MT Contact2..............
			Smoke_MTContact2FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC2", excelLabel.Contact_FirstName);
			Smoke_MTContact2LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC2", excelLabel.Contact_LastName);
			Smoke_MTContact2INSName=Smoke_MTINS1Name;
			Smoke_MTContact2EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC2", excelLabel.Contact_EmailId);
			Smoke_MTContact2RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC2", excelLabel.Record_Type);

			// MT Contact3..............
			Smoke_MTContact3FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC3", excelLabel.Contact_FirstName);
			Smoke_MTContact3LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC3", excelLabel.Contact_LastName);
			Smoke_MTContact3INSName=Smoke_MTINS2Name;
			Smoke_MTContact3EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC3", excelLabel.Contact_EmailId);
			Smoke_MTContact3RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC3", excelLabel.Record_Type);

			// MT Contact4..............
			Smoke_MTContact4FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC4", excelLabel.Contact_FirstName);
			Smoke_MTContact4LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC4", excelLabel.Contact_LastName);
			Smoke_MTContact4INSName=Smoke_MTINS2Name;
			Smoke_MTContact4EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC4", excelLabel.Contact_EmailId);
			Smoke_MTContact4RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC4", excelLabel.Record_Type);

			// MT Contact5..............
			Smoke_MTContact5FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC5", excelLabel.Contact_FirstName);
			Smoke_MTContact5LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC5", excelLabel.Contact_LastName);
			Smoke_MTContact5INSName=Smoke_MTINS6Name;
			Smoke_MTContact5EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC5", excelLabel.Contact_EmailId);
			Smoke_MTContact5RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC5", excelLabel.Record_Type);


			//****************************************************************	FundorDeal **********************************************************//

			// Task Fund1..............
			Smoke_TaskFund1Name=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND1", excelLabel.Fund_Name);
			Smoke_TaskFund1Type=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND1", excelLabel.Fund_Type);
			Smoke_TaskFund1InvestmentCategory=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND1", excelLabel.Fund_InvestmentCategory);
			Smoke_TaskFund1RecordType=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND1", excelLabel.Record_Type);


			// Task Fund2..............
			Smoke_TaskFund2Name=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND2", excelLabel.Fund_Name);
			Smoke_TaskFund2Type=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND2", excelLabel.Fund_Type);
			Smoke_TaskFund2InvestmentCategory=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND2", excelLabel.Fund_InvestmentCategory);
			Smoke_TaskFund2RecordType=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND2", excelLabel.Record_Type);

			// MT Fund1..............
			Smoke_MTFund1Name=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND1", excelLabel.Fund_Name);
			Smoke_MTFund1Type=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND1", excelLabel.Fund_Type);
			Smoke_MTFund1InvestmentCategory=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND1", excelLabel.Fund_InvestmentCategory);
			Smoke_MTFund1RecordType=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND1", excelLabel.Record_Type);

			// MT Fund2..............
			Smoke_MTFund2Name=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND2", excelLabel.Fund_Name);
			Smoke_MTFund2Type=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND2", excelLabel.Fund_Type);
			Smoke_MTFund2InvestmentCategory=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND2", excelLabel.Fund_InvestmentCategory);
			Smoke_MTFund2RecordType=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND2", excelLabel.Record_Type);



			//******************************************* Custom Objects ***********************************/

			taskCustomObj1Name=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AATASKOBJ1", excelLabel.Test_Custom_Object_Name);
			taskCustomObj1RecordType=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AATASKOBJ1", excelLabel.Record_Type);
			
			taskCustomObj2Name=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AATASKOBJ2", excelLabel.Test_Custom_Object_Name);
			taskCustomObj2RecordType=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AATASKOBJ2", excelLabel.Record_Type);
			taskCustomObj3Name=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AATASKOBJ3", excelLabel.Test_Custom_Object_Name);
			taskCustomObj3RecordType=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AATASKOBJ3", excelLabel.Record_Type);
			
			taskCustomObj2UpdatedName=taskCustomObj2Name+"UP";
			
			meetingCustomObj1Name=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AAMTOBJ1", excelLabel.Test_Custom_Object_Name);
			meetingCustomObj1RecordType=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AAMTOBJ1", excelLabel.Record_Type);
			
			meetingCustomObj2Name=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AAMTOBJ2", excelLabel.Test_Custom_Object_Name);
			meetingCustomObj2RecordType=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AAMTOBJ2", excelLabel.Record_Type);
			
			
		

			//*********************************************Events**********************************************/
			taskUpcomingEvent1Subject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV1", excelLabel.Subject);
			taskUpcomingEvent2Subject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV2", excelLabel.Subject);
			taskUpcomingEvent3Subject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV3", excelLabel.Subject);
			taskCompletedEvent1Subject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV4", excelLabel.Subject);
			taskCompletedEvent2Subject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV5", excelLabel.Subject);
			taskCompletedEvent3Subject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV6", excelLabel.Subject);
			meetingUpcomingEvent1Subject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV1", excelLabel.Subject);
			meetingUpcomingEvent2Subject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV2", excelLabel.Subject);
			meetingUpcomingEvent3Subject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV3", excelLabel.Subject);
			meetingCompletedEvent1Subject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV4", excelLabel.Subject);
			meetingCompletedEvent2Subject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV5", excelLabel.Subject);
			meetingCompletedEvent3Subject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV6", excelLabel.Subject);
			startingTime=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV1", excelLabel.Start_Time);
			endTime=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV1", excelLabel.End_Time);
			eventMeetingType=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV1", excelLabel.Meeting_Type);

			
			
			tommorrow=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV1", excelLabel.Start_Date);
			dayAfterTommorrow=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV1", excelLabel.End_Date);
			dayBeforeYesterday=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV4", excelLabel.Start_Date);
			yesterday=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV4", excelLabel.End_Date);
			
			
			//************************************************   TaSKS *************************************************//
			
			Smoke_Task1Subject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Subject);
			Smoke_Task1Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Due_Date);
			Smoke_Task1Status = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Status);
			Smoke_Task1MeetingType = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Meeting_Type);
			Smoke_Task1Comment=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Comment);
			Smoke_Task1Priority=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Priority);
			Smoke_Task1UpdatedPriority=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Updated_Priority);
			Smoke_Task1UpdatedSubject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Updated_Subject);

			Smoke_LogACall1Subject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AACALL1", excelLabel.Subject);
			Smoke_LogACall1MeetingType = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AACALL1", excelLabel.Meeting_Type);
			Smoke_LogACall1Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AACALL1", excelLabel.Due_Date);
			Smoke_LogACall1Comment=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AACALL1", excelLabel.Comment);
			 
			Smoke_Task2Subject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Subject);
			Smoke_Task2Status = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Status);
			Smoke_Task2UpdatedSubject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Updated_Subject);
			Smoke_Task2MeetingType = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Meeting_Type);
			Smoke_Task2Comment=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Comment);
			Smoke_Task2Priority=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Priority);
			Smoke_Task2UpdatedPriority=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Updated_Priority);
			Smoke_Task2DueDate = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Due_Date);
			
			Smoke_Meeting2Subject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingTask2", excelLabel.Subject);
			Smoke_Meeting2Priority =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingTask2", excelLabel.Priority); 
			Smoke_Task3Subject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask3", excelLabel.Subject);
			Smoke_Task3Priority =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask3", excelLabel.Priority); 
			
			Smoke_STDTask1Subject=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Subject);
			Smoke_STDTask1MeetingType=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Meeting_Type);
			Smoke_STDTask1Comment=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Comment);
			Smoke_STDTask1DueDate=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Due_Date);
			Smoke_STDTask1UpdatedSubject=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Updated_Subject);
			Smoke_STDTask1UpdatedPriority=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Updated_Priority);
			Smoke_STDTask1UpdatedComment=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Updated_Comment);
			
			Smoke_TaskSTD1Subject=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Subject);
			Smoke_TaskSTD1MeetingType=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Meeting_Type);
			Smoke_TaskSTD1Comment=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Comment);
			Smoke_TaskSTD1DueDate=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Due_Date);
			Smoke_TaskSTD1UpdatedSubject=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Updated_Subject);
			Smoke_TaskSTD1UpdatedPriority=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Updated_Priority);
			Smoke_TaskSTD1UpdatedComment=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Updated_Comment);
			
			Smoke_BoardMeetingTaskSubject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask10", excelLabel.Subject);
			Smoke_BoardMeetingTaskMeetingType =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask10", excelLabel.Meeting_Type);
			Smoke_ClientMeetingTaskSubject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask11", excelLabel.Subject);
			Smoke_ClientMeetingTaskMeetingType =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask11", excelLabel.Meeting_Type);
			
			Smoke_Task1LogACallSubject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Subject);
			Smoke_Task1LogACallComment =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Comment);
			Smoke_Task1LogACallPriority =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Priority);
			Smoke_Task1LogACallUpdatedSubject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Updated_Subject);
			Smoke_Task1LogACallUpdatedPriority =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Updated_Priority);
			
			Smoke_Task2LogACallSubject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask13", excelLabel.Subject);
			Smoke_Task2LogACallComment =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask13", excelLabel.Comment);
			Smoke_Task2LogACallPriority =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask13", excelLabel.Priority);
			
			Smoke_TaskSTDLogACall1Subject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Subject);
			Smoke_TaskSTDLogACall1Comment =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Comment);
			Smoke_TaskSTDLogACall1Priority =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Priority);
			Smoke_TaskSTDLogACall1Status =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Status);
			Smoke_TaskSTDLogACall1UpdatedSubject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Updated_Subject);
			Smoke_TaskSTDLogACall1UpdatedPriority =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Updated_Priority);
			Smoke_TaskSTDLogACall1UpdatedComment =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Updated_Comment);
			
			Smoke_Task2MultipleSubject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Subject);
			Smoke_Task2MultiplePriority =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Priority);
			Smoke_Task2MultipleComment =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Comment);
			Smoke_Task2MultipleStatus=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Status);
			Smoke_FinalDiscussionTaskSubject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingFinalTask", excelLabel.Subject);
			Smoke_FinalDiscussionTaskMeetingType =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingFinalTask", excelLabel.Meeting_Type);
			Smoke_Task2MultipleUpdatedSubject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Updated_Subject);
			Smoke_Task2MultipleDueDate =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Due_Date);
			
			
			Smoke_TaskSendLetterSubject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask17", excelLabel.Subject);
			Smoke_TaskSendLetterPriority =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask17", excelLabel.Priority);
			Smoke_TaskSendLetterComment =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask17", excelLabel.Comment);
			Smoke_TaskSendLetterMeetingType =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask17", excelLabel.Meeting_Type);
			
			Smoke_Task2LogACallNewSubject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Subject);
			Smoke_Task2LogACallNewComment =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Comment);
			Smoke_Task20Subject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask20", excelLabel.Subject);
			Smoke_Task20Status =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask20", excelLabel.Status);
			Smoke_Task20Comment =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask20", excelLabel.Comment);
			
			Smoke_Task2LogACallNewSubject =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Subject);
			Smoke_Task2LogACallNewComment =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Comment);
			Smoke_Task2LogACallNewPriority =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Priority);
			Smoke_Task2LogACallNewDate =ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Due_Date);
			Smoke_Task2LogACallNewUpdatedSubject=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Updated_Subject);
			Smoke_Task2LogACallNewStatus=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Status);
			Smoke_Task2LogACallNewUpdatedPriority=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Updated_Priority);
			
			Smoke_DealEvalutionMeetingSubject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingTask3", excelLabel.Subject);
			Smoke_DealEvalutionMeetingType = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingTask3", excelLabel.Meeting_Type);
			Smoke_A1TaskSubject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask32", excelLabel.Subject);
			Smoke_A2TaskSubject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask33", excelLabel.Subject);
			Smoke_R1CallStatus = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask34", excelLabel.Status);
			
			Smoke_R1CallSubject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask34", excelLabel.Subject);
			Smoke_R2CallSubject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask35", excelLabel.Subject);
			Smoke_A2TaskStatus = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask33", excelLabel.Status);
			
			
			Smoke_STDTask2OnSubject=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask14", excelLabel.Subject);
			Smoke_STDTask2OnDate=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask14", excelLabel.Due_Date);
			Smoke_STDTask2OnComment=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask14", excelLabel.Comment);
			
			
			Smoke_DealClosureMeetingSubject= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask19", excelLabel.Subject);
			Smoke_DealClosureMeetingType = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask19", excelLabel.Meeting_Type);
			
			Smoke_DealProgressReviewMeetingSubject=  ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask21", excelLabel.Subject);
			
			Smoke_S1TestSubject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask22", excelLabel.Subject);
			Smoke_S1TestDueDate = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask22", excelLabel.Due_Date);
			
			
			Smoke_S2TestSubject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask23", excelLabel.Subject);
			Smoke_S2TestDueDate = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask23", excelLabel.Due_Date);
			
			
			Smoke_S3TestSubject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask24", excelLabel.Subject);
			Smoke_S3TestMeetingType = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask24", excelLabel.Meeting_Type);
			Smoke_S3TestDueDate = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask24", excelLabel.Due_Date);
			
			
			Smoke_TestListEmailSubject = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask25", excelLabel.Subject);
			Smoke_TestListEmailComment = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask25", excelLabel.Comment);
			Smoke_TestListEmailStatus = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask25", excelLabel.Status);
			Smoke_TestListEmailDate = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask25", excelLabel.Due_Date);
			
			
			Smoke_DetailPageMeetingSubject= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask26", excelLabel.Subject);
			Smoke_DetailPageMeetingType = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask26", excelLabel.Meeting_Type);
			
			Smoke_DetailPageNewTaskMultipleSubject= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask27", excelLabel.Subject);
			
			Smoke_DetailPageLogACallMultipleSubject= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask28", excelLabel.Subject);
			Smoke_DetailPageLogACallMultipleDate= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask28", excelLabel.Due_Date);
			

			Smoke_EntityMeeting1Subject=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask29", excelLabel.Subject);
			Smoke_EntityMeeting1Type=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask29", excelLabel.Meeting_Type);
			Smoke_EntityMeeting1DueDate=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask29", excelLabel.Due_Date);
			Smoke_EntityMeeting1Priority=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask29", excelLabel.Priority);
			
			Smoke_EntityMeeting2Subject=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask30", excelLabel.Subject);
			Smoke_EntityMeeting2Type=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask30", excelLabel.Meeting_Type);
			Smoke_EntityMeeting2DueDate=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask30", excelLabel.Due_Date);
			Smoke_EntityMeeting2Status=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask30", excelLabel.Status);
			Smoke_CallMeetingType=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask31", excelLabel.Meeting_Type);
			Smoke_CallSubject=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask31", excelLabel.Subject);
			
			
		
			Smoke_AAField1DataType=ExcelUtils.readData(testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field1", excelLabel.Data_Type);
			Smoke_AAField1FieldLabel=ExcelUtils.readData(testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field1", excelLabel.Field_Label);
			Smoke_AAField1Length=ExcelUtils.readData(testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field1", excelLabel.Length);
			
			
			Smoke_AAField2DataType=ExcelUtils.readData(testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field2", excelLabel.Data_Type);
			Smoke_AAField2FieldLabel=ExcelUtils.readData(testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field2", excelLabel.Field_Label);
			Smoke_AAField2Length=ExcelUtils.readData(testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field2", excelLabel.Length);
			Smoke_AAField2DecimalPlaces=ExcelUtils.readData(testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field2", excelLabel.Decimal_Places);
			
			Smoke_AAField3DataType=ExcelUtils.readData(testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field3", excelLabel.Data_Type);
			Smoke_AAField3FieldLabel=ExcelUtils.readData(testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field3", excelLabel.Field_Label);
			Smoke_AAField3Options=ExcelUtils.readData(testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field3", excelLabel.Options);
			
			
			Smoke_WATCHINS1Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS1", excelLabel.Institutions_Name);
			Smoke_WATCHINS1RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS1", excelLabel.Record_Type);

			Smoke_WATCHINS2Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS2", excelLabel.Institutions_Name);
			Smoke_WATCHINS2RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS2", excelLabel.Record_Type);

			
			Smoke_WATCHINS5Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS5", excelLabel.Institutions_Name);
			Smoke_WATCHINS5RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS5", excelLabel.Record_Type);

			Smoke_WATCHINS6Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS6", excelLabel.Institutions_Name);
			Smoke_WATCHINS6RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS6", excelLabel.Record_Type);

			Smoke_MAXDATAINS1Name=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Institutions_Name);
			Smoke_MAXDATAINS1RecordType=ExcelUtils.readData(testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Record_Type);

			Smoke_WATCHContact1FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON1", excelLabel.Contact_FirstName);
			Smoke_WATCHContact1LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON1", excelLabel.Contact_LastName);
			Smoke_WATCHContact1INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact1EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON1", excelLabel.Contact_EmailId);
			Smoke_WATCHContact1RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON1", excelLabel.Record_Type);

			Smoke_WATCHContact2FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON2", excelLabel.Contact_FirstName);
			Smoke_WATCHContact2LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON2", excelLabel.Contact_LastName);
			Smoke_WATCHContact2INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact2EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON2", excelLabel.Contact_EmailId);
			Smoke_WATCHContact2RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON2", excelLabel.Record_Type);

			Smoke_WATCHContact3FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON3", excelLabel.Contact_FirstName);
			Smoke_WATCHContact3LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON3", excelLabel.Contact_LastName);
			Smoke_WATCHContact3INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact3EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON3", excelLabel.Contact_EmailId);
			Smoke_WATCHContact3RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON3", excelLabel.Record_Type);

			Smoke_WATCHContact4FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON4", excelLabel.Contact_FirstName);
			Smoke_WATCHContact4LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON4", excelLabel.Contact_LastName);
			Smoke_WATCHContact4INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact4EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON4", excelLabel.Contact_EmailId);
			Smoke_WATCHContact4RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON4", excelLabel.Record_Type);

			Smoke_WATCHContact5FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON5", excelLabel.Contact_FirstName);
			Smoke_WATCHContact5LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON5", excelLabel.Contact_LastName);
			Smoke_WATCHContact5INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact5EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON5", excelLabel.Contact_EmailId);
			Smoke_WATCHContact5RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON5", excelLabel.Record_Type);

			Smoke_WATCHContact6FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON6", excelLabel.Contact_FirstName);
			Smoke_WATCHContact6LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON6", excelLabel.Contact_LastName);
			Smoke_WATCHContact6INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact6EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON6", excelLabel.Contact_EmailId);
			Smoke_WATCHContact6RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON6", excelLabel.Record_Type);

			Smoke_WATCHContact7FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON7", excelLabel.Contact_FirstName);
			Smoke_WATCHContact7LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON7", excelLabel.Contact_LastName);
			Smoke_WATCHContact7INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact7EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON7", excelLabel.Contact_EmailId);
			Smoke_WATCHContact7RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON7", excelLabel.Record_Type);

			Smoke_WATCHContact8FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON8", excelLabel.Contact_FirstName);
			Smoke_WATCHContact8LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON8", excelLabel.Contact_LastName);
			Smoke_WATCHContact8INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact8EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON8", excelLabel.Contact_EmailId);
			Smoke_WATCHContact8RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON8", excelLabel.Record_Type);

			Smoke_MAXDATAContact1FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Contact_FirstName);
			Smoke_MAXDATAContact1LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Contact_LastName);
			Smoke_MAXDATAContact1INSName=Smoke_MAXDATAINS1Name;
			Smoke_MAXDATAContact1EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Contact_EmailId);
			Smoke_MAXDATAContact1RecordType=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Record_Type);

			Smoke_MAXDATAFund1Name=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Fund_Name);
			Smoke_MAXDATAFund1Type=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Fund_Type);
			Smoke_MAXDATAFund1InvestmentCategory=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Fund_InvestmentCategory);
			Smoke_MAXDATAFund1RecordType=ExcelUtils.readData(testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Record_Type);

			
			MAXDATACustomObj1Name=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"MAXDATA1", excelLabel.Test_Custom_Object_Name);
			MAXDATACustomObj1RecordType=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"MAXDATA1", excelLabel.Record_Type);
		
			WATCHCustomObj1Name=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"WATCH1", excelLabel.Test_Custom_Object_Name);
			WATCHCustomObj1RecordType=ExcelUtils.readData(testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"WATCH1", excelLabel.Record_Type);
		
			
			Smoke_STTaskFromContactSubject= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask36", excelLabel.Subject);
			Smoke_STTaskFromContactMeetingType = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask36", excelLabel.Meeting_Type);
			Smoke_STTaskFromContactStatus= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask36", excelLabel.Status);
			Smoke_STTaskFromContactDueDate = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask36", excelLabel.Due_Date);
			
			Smoke_MTGTaskFromContactSubject= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask37", excelLabel.Subject);
			Smoke_MTGTaskFromContactMeetingType = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask37", excelLabel.Meeting_Type);
			Smoke_MTGTaskFromContactStatus= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask37", excelLabel.Status);
			Smoke_MTGTaskFromContactDueDate = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask37", excelLabel.Due_Date);
			
			Smoke_LAC1Subject= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask38", excelLabel.Subject);
			Smoke_LAC1DueDate = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask38", excelLabel.Due_Date);
			
			
			Smoke_TaskMTA1Subject= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask39", excelLabel.Subject);
			Smoke_TaskMTA1MeetingType = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask39", excelLabel.Meeting_Type);
			Smoke_TaskMTA1DueDate = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask39", excelLabel.Due_Date);
		
			 CreatedMeetingTypeOption = "Test Meeting Type Activity Association";
			
			Smoke_MTTest1Subject= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask40", excelLabel.Subject);
			Smoke_MTTest1MeetingType = CreatedMeetingTypeOption;
			Smoke_MTTest1Status= ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask40", excelLabel.Status);
			Smoke_MTTest1DueDate = ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask40", excelLabel.Due_Date);
				
			AppListeners.appLog.info("Done with intialization in Smoke Test Variable. Enjoy the show.\nTotal Time Taken: "+((System.currentTimeMillis()-StartTime)/1000)+" seconds.");
			
		//}

	}
		

	
}
