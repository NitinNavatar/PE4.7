/**
 * 
 */
package com.navatar.generic;

import static com.navatar.generic.CommonLib.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.BasePageErrorMessage;
//import com.navatar.scripts.SmokeTestCases;

import static com.navatar.generic.BaseLib.*;



public class SmokeCommonVariables {
	
	public static String appName,appVersion;
	public static String superAdminUserName,superAdminRegistered,adminPassword,OrganizationName;
	public static String AdminUserFirstName,AdminUserLastName,AdminUserEmailID;
	public static String crmUser1FirstName,crmUser1LastName,crmUser1EmailID,crmUserProfile,crmUserLience;
	public static String crmUser3FirstName,crmUser3LastName,crmUser3EmailID,crmUser3Profile,crmUser3Lience;
	public static String gmailUserName,gmailUserName2,gmailPassword;
	

	public static String SmokeINS1, SmokeINS1_RecordType;
	public static String SmokeINS2, SmokeINS2_RecordType, SmokeINS2_Street, SmokeINS2_City, SmokeINS2_State,
			SmokeINS2_Postal_Code, SmokeINS2_Country, SmokeINS2_Phone, SmokeINS2_Fax;
	public static String SmokeINS3, SmokeINS3_RecordType;
	public static String SmokeINS4, SmokeINS4_RecordType, SmokeINS4_Street, SmokeINS4_City, SmokeINS4_State,
			SmokeINS4_Postal_Code, SmokeINS4_Country, SmokeINS4_Phone, SmokeINS4_Fax;
	public static String SmokeINS5, SmokeINS5_RecordType;
	public static String SmokeINDINV1, SmokeINDINV1_RecordType;
	public static String SmokeINDINV2, SmokeINDINV2_RecordType;
	public static String SmokeINDINV3, SmokeINDINV3_RecordType;
	public static String SmokeINDINV4, SmokeINDINV4_RecordType, SmokeINDINV4_Description, SmokeINDINV4_InstitutionType,
			SmokeINDINV4_FundPreferences, SmokeINDINV4_IndustryPreferences, SmokeINDINV4_Street, SmokeINDINV4_City,
			SmokeINDINV4_State, SmokeINDINV4_PostalCode, SmokeINDINV4_Country, SmokeINDINV4_ShippingStreet,
			SmokeINDINV4_ShippingCity, SmokeINDINV4_ShippingState, SmokeINDINV4_ShippingZip,
			SmokeINDINV4_ShippingCountry, SmokeINDINV4_Phone, SmokeINDINV4_Fax;
	public static String SmokeINDINV5, SmokeINDINV5_RecordType, SmokeINDINV5_Description, SmokeINDINV5_InstitutionType,
			SmokeINDINV5_FundPreferences, SmokeINDINV5_IndustryPreferences, SmokeINDINV5_Street, SmokeINDINV5_City,
			SmokeINDINV5_State, SmokeINDINV5_PostalCode, SmokeINDINV5_Country, SmokeINDINV5_ShippingStreet,
			SmokeINDINV5_ShippingCity, SmokeINDINV5_ShippingState, SmokeINDINV5_ShippingZip,
			SmokeINDINV5_ShippingCountry, SmokeINDINV5_Phone;
	public static String SmokeINDINV6, SmokeINDINV6_RecordType, SmokeINDINV6_Description, SmokeINDINV6_InstitutionType,
			SmokeINDINV6_FundPreferences, SmokeINDINV6_IndustryPreferences, SmokeINDINV6_Street, SmokeINDINV6_City,
			SmokeINDINV6_State, SmokeINDINV6_PostalCode, SmokeINDINV6_Country, SmokeINDINV6_ShippingStreet,
			SmokeINDINV6_ShippingCity, SmokeINDINV6_ShippingState, SmokeINDINV6_ShippingZip,
			SmokeINDINV6_ShippingCountry, SmokeINDINV6_Phone;
	public static String SmokeINDINV7, SmokeINDINV7_RecordType, SmokeINDINV7_Description, SmokeINDINV7_InstitutionType,
			SmokeINDINV7_FundPreferences, SmokeINDINV7_IndustryPreferences, SmokeINDINV7_Street, SmokeINDINV7_City,
			SmokeINDINV7_State, SmokeINDINV7_PostalCode, SmokeINDINV7_Country, SmokeINDINV7_ShippingStreet,
			SmokeINDINV7_ShippingCity, SmokeINDINV7_ShippingState, SmokeINDINV7_ShippingZip,
			SmokeINDINV7_ShippingCountry, SmokeINDINV7_Phone, SmokeINDINV7_Website, SmokeINDINV7_Fax;

	public static String SmokeCOM1,SmokeCOM1_RecordType;
	public static String SmokeCOM2,SmokeCOM2_RecordType;
	
	public static String SmokeC1_FName,SmokeC1_LName,SmokeC1_EmailID,SmokeC1_RecordType;
	public static String SmokeC2_FName,SmokeC2_LName,SmokeC2_EmailID,SmokeC2_RecordType;
	public static String SmokeC3_FName,SmokeC3_LName,SmokeC3_EmailID,SmokeC3_RecordType; 
	public static String SmokeC4_FName,SmokeC4_LName,SmokeC4_EmailID,SmokeC4_RecordType;
	public static String SmokeC5_FName,SmokeC5_LName,SmokeC5_EmailID,SmokeC5_RecordType;
	public static String SmokeC6_FName,SmokeC6_LName,SmokeC6_EmailID,SmokeC6_Other_Street,SmokeC6_Other_City,
	SmokeC6_Other_State,SmokeC6_Other_Zip,SmokeC6_Other_Country,SmokeC6_RecordType;
	
	public static String SmokeC7_FName, SmokeC7_LName, SmokeC7_EmailID, SmokeC7_Description, SmokeC7_MailingStreet,
			SmokeC7_MailingCity, SmokeC7_MailingState, SmokeC7_MailingCountry, SmokeC7_OtherStreet, SmokeC7_OtherCity,
			SmokeC7_OtherState, SmokeC7_OtherCountry, SmokeC7_OtherZip, SmokeC7_Phone, SmokeC7_Fax, SmokeC7_Mobile,
			SmokeC7_Assistant, SmokeC7_Asst_Phone, SmokeC7_MailingZip;
	public static String SmokeC8_FName, SmokeC8_LName, SmokeC8_EmailID, SmokeC8_Description, SmokeC8_MailingStreet,
			SmokeC8_MailingCity, SmokeC8_MailingState, SmokeC8_MailingCountry, SmokeC8_OtherStreet, SmokeC8_OtherCity,
			SmokeC8_OtherState, SmokeC8_OtherCountry, SmokeC8_OtherZip, SmokeC8_Phone, SmokeC8_Fax, SmokeC8_Mobile,
			SmokeC8_Assistant, SmokeC8_Asst_Phone, SmokeC8_MailingZip;
	public static String SmokeC9_FName, SmokeC9_LName, SmokeC9_EmailID, SmokeC9_Description, SmokeC9_MailingStreet,
			SmokeC9_MailingCity, SmokeC9_MailingState, SmokeC9_MailingCountry, SmokeC9_OtherStreet, SmokeC9_OtherCity,
			SmokeC9_OtherState, SmokeC9_OtherCountry, SmokeC9_OtherZip, SmokeC9_Phone, SmokeC9_Fax, SmokeC9_Mobile,
			SmokeC9_Assistant, SmokeC9_Asst_Phone, SmokeC9_MailingZip;
	public static String SmokeC10_FName, SmokeC10_LName, SmokeC10_EmailID, SmokeC10_Description, SmokeC10_MailingStreet,
			SmokeC10_MailingCity, SmokeC10_MailingState, SmokeC10_MailingCountry, SmokeC10_OtherStreet,
			SmokeC10_OtherCity, SmokeC10_OtherState, SmokeC10_OtherCountry, SmokeC10_OtherZip, SmokeC10_Phone,
			SmokeC10_Fax, SmokeC10_Mobile, SmokeC10_Assistant, SmokeC10_Asst_Phone, SmokeC10_MailingZip,
			SmokeC10_Preferred_Mode_of_Contact;

	public static String Smoke_MI1;
	public static String Smoke_MI2;
	public static String Smoke_MI3;	
	
	public static String Smoke_FR1,SmokeFR1_Investment_Likely_Amount;
	public static String Smoke_FR2,SmokeFR2_Investment_Likely_Amount;
	public static String Smoke_FR3,SmokeFR3_Investment_Likely_Amount;
	public static String Smoke_FR4,SmokeFR4_Investment_Likely_Amount;
	public static String Smoke_FR5,SmokeFR5_Investment_Likely_Amount;
	public static String Smoke_FR6,SmokeFR6_Investment_Likely_Amount;
	
	public static String SmokeFRC1_ID,SmokeFRC1_FRName,SmokeFRC1_Role,SmokeFRC1_Primary,SmokeFRC1_INS1;
	public static String SmokeFRC3_ID,SmokeFRC3_FRName,SmokeFRC3_Role,SmokeFRC3_Primary,SmokeFRC3_INDINV1;
	public static String SmokeFRC4_ID,SmokeFRC4_FRName,SmokeFRC4_Role,SmokeFRC4_Primary,SmokeFRC4_INDINV1;
	public static String SmokeFRC5_ID,SmokeFRC5_FRName,SmokeFRC5_Role,SmokeFRC5_Primary,SmokeFRC5_INDINV1;
	public static String SmokeFRC6_ID,SmokeFRC6_FRName,SmokeFRC6_Role,SmokeFRC6_Primary,SmokeFRC6_INDINV1;
	public static String SmokeFRC7_ID,SmokeFRC7_FRName,SmokeFRC7_Role,SmokeFRC7_Primary,SmokeFRC7_INDINV1;
	public static String SmokeFRC8_ID,SmokeFRC8_FRName,SmokeFRC8_Role,SmokeFRC8_Primary,SmokeFRC8_INDINV1;
	public static String SmokeFRC9_ID,SmokeFRC9_FRName,SmokeFRC9_Role,SmokeFRC9_Primary,SmokeFRC9_INDINV1;
	
	public static String Smoke_COMM1,SmokeCOMM1_CommitmentAmount,SmokeCOMM1_partnerType,SmokeCOMM1_TaxForms,SmokeCOMM1_FinalCommitmentDate,SmokeCOMM1_ID;
	public static String Smoke_COMM2,SmokeCOMM2_CommitmentAmount,SmokeCOMM2_partnerType,SmokeCOMM2_TaxForms,SmokeCOMM2_FinalCommitmentDate,SmokeCOMM2_ID;
	public static String Smoke_COMM3,SmokeCOMM3_CommitmentAmount,SmokeCOMM3_partnerType,SmokeCOMM3_TaxForms,SmokeCOMM3_FinalCommitmentDate,SmokeCOMM3_ID,SmokeCoMM3_PlacementFee;
	public static String Smoke_COMM4,SmokeCOMM4_CommitmentAmount,SmokeCOMM4_partnerType,SmokeCOMM4_TaxForms,SmokeCOMM4_FinalCommitmentDate,SmokeCOMM4_ID,SmokeCOMM4_PlacementFee;
	public static String Smoke_COMM5,SmokeCOMM5_CommitmentAmount,SmokeCOMM5_partnerType,SmokeCOMM5_TaxForms,SmokeCOMM5_FinalCommitmentDate,SmokeCOMM5_ID;
	public static String Smoke_COMM6,SmokeCOMM6_CommitmentAmount,SmokeCOMM6_partnerType,SmokeCOMM6_TaxForms,SmokeCOMM6_FinalCommitmentDate,SmokeCOMM6_ID;
	public static String Smoke_COMM7,SmokeCOMM7_CommitmentAmount,SmokeCOMM7_partnerType,SmokeCOMM7_TaxForms,SmokeCOMM7_FinalCommitmentDate,SmokeCOMM7_ID;
	public static String Smoke_COMM8,SmokeCOMM8_CommitmentAmount,SmokeCOMM8_partnerType,SmokeCOMM8_TaxForms,SmokeCOMM8_FinalCommitmentDate,SmokeCOMM8_ID,SmokeCOMM8_TotalAmountCalled, SmokeCOMM8_TotalAmountReceived,SmokeCOMM8_TotalUncalledAmount,SmokeCOMM8_TotalCommitmentDue,SmokeCOMM8_CommitmentCalled,SmokeCOMM8_CalledDue,SmokeCOMM8_TotalDist,SmokeCOMM8_CapitalReturnedRecallable,SmokeCOMM8_CapitalReturnedNonRecallable;
	
	public static String Smoke_P1;
	public static String Smoke_P2,SmokeP2_Fund_Investment_Category;
	public static String Smoke_P3;
	public static String Smoke_P4;
	
	public static String SmokeDD1_ID;
	public static String SmokeCC1_ID;
	public static String SmokeCC2_ID;
	public static String SmokeCC3_ID;
	public static String SmokeCC4_ID,SmokeCC5_ID;
	public static String SmokeDD1_FundName;
	public static String SmokeDD1_CallAmount;
	public static String SmokeDD1_CallDate;
	public static String SmokeDD1_DueDate;
	public static String SmokeDD1_AmountDue;
	public static String SmokeDD1_TotalCallAmountReceived;
	public static String SmokeDD1_CapitalAmount;
	public static String SmokeDD1_ManagementFee;
	public static String SmokeDD1_OtherFee;
	public static String SmokeFD1ID;
	public static String SmokeID1ID;
	public static String SmokeID2ID;
	public static String SmokeID3ID;
	public static String SmokeID4ID;
	public static String SmokeID5ID;
	public static String SmokeCC1Data[] = new String[11];
	public static String SmokeCC2Data[] = new String[11];
	public static String SmokeCC3Data[] = new String[11];
	public static String SmokeCC4Data[] = new String[11];
	public static String SmokeCC5Data[] = new String[11];
	
	public static String Smoke_NewTask1Subject;
	public static String Smoke_NewTask2Subject;
	
	public static String Smoke_NewEvent1Subject,Smoke_NewEvent1StartDate,Smoke_NewEvent1RelatedTo;
	public static String Smoke_NewEvent2Subject,Smoke_NewEvent2StartDate,Smoke_NewEvent2RelatedTo;
	
	public static String Smoke_CallLog1Subject,Smoke_CallLog1DueDate,Smoke_CallLog1RelatedTo;
	public static String Smoke_CallLog2Subject,Smoke_CallLog2DueDate,Smoke_CallLog2RelatedTo;
	
	public static String Smoke_LP1,SmokeLP1_Total_CoInvesment_Commitments,SmokeLP1_Total_Fund_Commitments;
	public static String Smoke_LP2,SmokeLP2_Total_CoInvesment_Commitments,SmokeLP2_Total_Fund_Commitments;
	public static String Smoke_LP3,SmokeLP3_BankName;
	public static String Smoke_LP4,SmokeLP4_Total_Fund_Commitments,SmokeLP4_Total_CoInvesment_Commitments;
	public static String Smoke_LP5;
	
	public static String Smoke_Fund1,SmokeFund1_Type,SmokeFund1_InvestmentCategory,SmokeFund1Target_Commitments,SmokeFund1_1st_ClosingDate,SmokeFund1_VintageYear,SmokeFund1_TotalCommitment;
	public static String Smoke_Fund2,SmokeFund2_Type,SmokeFund2_InvestmentCategory,SmokeFund2Target_Commitments,SmokeFund1_2st_ClosingDate,SmokeFund2_VintageYear,SmokeFund2_TotalCommitment;
	public static String Smoke_Fund3,SmokeFund3_Type,SmokeFund3_InvestmentCategory,SmokeFund3Target_Commitments,SmokeFund1_3st_ClosingDate,SmokeFund3_VintageYear,SmokeFund3_TotalCommitment;
	
	public static String Smoke_PL1Name,Smoke_PL1CompanyName,Smoke_PL1LastStageChangeDate,Smoke_PL1HighestStageReached,Smoke_PL1AgeOfCurrentStage,Smoke_PL1Stage,Smoke_PL1Source,Smoke_PL1SourceFirm,Smoke_PL1SourceContact_Name,Smoke_PL1FirstStageChanged,Smoke_PL1SecondStageChanged;

	public static String Smoke_PL2Name,Smoke_PL2CompanyName,Smoke_PL2Stage,Smoke_PL2Source,Smoke_PL2SourceFirm,Smoke_PL2SourceContact,Smoke_PL2Dealtype,Smoke_PL2Employees,Smoke_PL2SourceFirm_Website,Smoke_PL2SourceContact_Email,Smoke_PL2AgeOfCurrentStage;

	public static String Smoke_PL3Name,Smoke_PL3CompanyName,Smoke_PL3Stage,Smoke_PL3Source,Smoke_PL3SourceFirm,Smoke_PL3SourceContact_Fname,Smoke_PL3SourceContact_Lname;

	public static String Smoke_OFFLoc1Name,Smoke_OFFLoc1Street,Smoke_OFFLoc1City,Smoke_OFFLoc1StateProvince,Smoke_OFFLoc1Country,Smoke_OFFLoc1ZIP,Smoke_OFFLoc1OrgName,Smoke_OFFLoc1Phone,Smoke_OFFLoc1Fax,Smoke_OFFLoc1Primary,Smoke_OFFLoc1UpdatedPrimary;
	
	public static String Smoke_OFFLoc2Name,Smoke_OFFLoc2Street,Smoke_OFFLoc2City,Smoke_OFFLoc2StateProvince,Smoke_OFFLoc2Country,Smoke_OFFLoc2ZIP,Smoke_OFFLoc2OrgName,Smoke_OFFLoc2Phone,Smoke_OFFLoc2Fax,Smoke_OFFLoc2Primary,Smoke_OFFLoc2UpdatedPrimary;
	
	public static String Smoke_OFFLoc1UpdName,Smoke_OFFLoc1UpdStreet,Smoke_OFFLoc1UpdCity,Smoke_OFFLoc1UpdStateProvince,Smoke_OFFLoc1UpdCountry,Smoke_OFFLoc1UpdZIP,Smoke_OFFLoc1UpdOrgName,Smoke_OFFLoc1UpdPhone,Smoke_OFFLoc1UpdFax,Smoke_OFFLoc1UpdPrimary;
	
	
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
	
	public static String SmokeReportFolderName,SmokeReportName,SmokeReportType,SmokeReportShow,SmokeReportRange;
	public static String EmailTemplate1_Subject,EmailTemplate1_Body,EmailTemplate1_FolderName,EmailTemplate1_TemplateName,EmailTemplate1_TemplateDescription;


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
	public static String tabObj8Coverage,environment,mode,AdminUserProfile;
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


			environment=ExcelUtils.readDataFromPropertyFile("Environment");
			mode=ExcelUtils.readDataFromPropertyFile("Mode");
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
			tabObj8Coverage=ExcelUtils.readDataFromPropertyFile("Object8");
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
			OrganizationName=ExcelUtils.readData(smokeFilePath,"Users",excelLabel.Variable_Name, "AdminUser", excelLabel.Organization_Name);
			AdminUserProfile=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "AdminUser", excelLabel.User_Profile);


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
			FileInputStream fis = null;
			Workbook wb = null;
			
			
			switch (obj.getClass().getSimpleName()) {
			
			case "OldSmokeTestCases":
			case "PECloudSmoke":
			
				try {
					fis=new FileInputStream(new File(smokeFilePath));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					wb=WorkbookFactory.create(fis);
				} catch (EncryptedDocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			//****************************************************************	Institution,Individual Investor,Company **********************************************************//
			//INS1..............
			SmokeINS1=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS1", excelLabel.Institutions_Name);
			SmokeINS1_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS1", excelLabel.Record_Type);
			
			//INS2..............
			SmokeINS2=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS2", excelLabel.Institutions_Name);
			SmokeINS2_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS2", excelLabel.Record_Type);
			SmokeINS2_Street=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS2", excelLabel.Street);
			SmokeINS2_City=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS2", excelLabel.City);
			SmokeINS2_State=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS2", excelLabel.State);
			SmokeINS2_Postal_Code=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS2", excelLabel.Postal_Code);
			SmokeINS2_Country=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS2", excelLabel.Country);
			SmokeINS2_Phone=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS2", excelLabel.Phone);
			SmokeINS2_Fax=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS2", excelLabel.Fax);
			//INS3..............
			SmokeINS3=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS3", excelLabel.Institutions_Name);
			SmokeINS3_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS3", excelLabel.Record_Type);
			
			//INS4.............
			SmokeINS4=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS4", excelLabel.Institutions_Name);
			SmokeINS4_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS4", excelLabel.Record_Type);
			SmokeINS4_Street=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS4", excelLabel.Street);
			SmokeINS4_City=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS4", excelLabel.City);
			SmokeINS4_State=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS4", excelLabel.State);
			SmokeINS4_Postal_Code=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS4", excelLabel.Postal_Code);
			SmokeINS4_Country=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS4", excelLabel.Country);
			SmokeINS4_Phone=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS4", excelLabel.Phone);
			SmokeINS4_Fax=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS4", excelLabel.Fax);
			
			//INS5............
			SmokeINS5=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS5", excelLabel.Institutions_Name);
			SmokeINS5_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINS5", excelLabel.Record_Type);
			
			//INDINV1..........
			SmokeINDINV1=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV1", excelLabel.Institutions_Name);
			SmokeINDINV1_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV1", excelLabel.Record_Type);
			
			//INDINV2..........
			SmokeINDINV2=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV2", excelLabel.Institutions_Name);
			SmokeINDINV2_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV2", excelLabel.Record_Type);
			
			//INDINV3..........
			SmokeINDINV3=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV3", excelLabel.Institutions_Name);
			SmokeINDINV3_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV3", excelLabel.Record_Type);
			
			//INDINV4................
			SmokeINDINV4=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Institutions_Name);
			SmokeINDINV4_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Record_Type);
			SmokeINDINV4_Description=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Description);
			SmokeINDINV4_InstitutionType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Institution_Type);
			SmokeINDINV4_FundPreferences=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Fund_Preferences);
			SmokeINDINV4_IndustryPreferences=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Industry_Preferences);
			SmokeINDINV4_Street=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Street);
			SmokeINDINV4_City=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.City);
			SmokeINDINV4_State=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.State);
			SmokeINDINV4_PostalCode=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Postal_Code);
			SmokeINDINV4_Country=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Country);
			SmokeINDINV4_ShippingStreet=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Shipping_Street);
			SmokeINDINV4_ShippingCity=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Shipping_City);
			SmokeINDINV4_ShippingState=ExcelUtils.readData(smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Shipping_State);
			SmokeINDINV4_ShippingZip=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Shipping_Zip);
			SmokeINDINV4_ShippingCountry=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Shipping_Country);
			SmokeINDINV4_Phone=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Phone);
			SmokeINDINV4_Fax=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV4", excelLabel.Fax);
			
			//INDINV5................
			SmokeINDINV5=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Institutions_Name);
			SmokeINDINV5_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Record_Type);
			SmokeINDINV5_Description=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Description);
			SmokeINDINV5_InstitutionType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Institution_Type);
			SmokeINDINV5_FundPreferences=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Fund_Preferences);
			SmokeINDINV5_IndustryPreferences=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Industry_Preferences);
			SmokeINDINV5_Street=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Street);
			SmokeINDINV5_City=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.City);
			SmokeINDINV5_State=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.State);
			SmokeINDINV5_PostalCode=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Postal_Code);
			SmokeINDINV5_Country=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Country);
			SmokeINDINV5_ShippingStreet=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Shipping_Street);
			SmokeINDINV5_ShippingCity=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Shipping_City);
			SmokeINDINV5_ShippingState=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Shipping_State);
			SmokeINDINV5_ShippingZip=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Shipping_Zip);
			SmokeINDINV5_ShippingCountry=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Shipping_Country);
			SmokeINDINV5_Phone=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV5", excelLabel.Phone);
			
			//INDINV6................
			SmokeINDINV6=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Institutions_Name);
			SmokeINDINV6_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Record_Type);
			SmokeINDINV6_Description=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Description);
			SmokeINDINV6_InstitutionType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Institution_Type);
			SmokeINDINV6_FundPreferences=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Fund_Preferences);
			SmokeINDINV6_IndustryPreferences=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Industry_Preferences);
			SmokeINDINV6_Street=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Street);
			SmokeINDINV6_City=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.City);
			SmokeINDINV6_State=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.State);
			SmokeINDINV6_PostalCode=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Postal_Code);
			SmokeINDINV6_Country=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Country);
			SmokeINDINV6_ShippingStreet=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Shipping_Street);
			SmokeINDINV6_ShippingCity=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Shipping_City);
			SmokeINDINV6_ShippingState=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Shipping_State);
			SmokeINDINV6_ShippingZip=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Shipping_Zip);
			SmokeINDINV6_ShippingCountry=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Shipping_Country);
			SmokeINDINV6_Phone=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV6", excelLabel.Phone);
			
			//INDINV7................
			SmokeINDINV7=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Institutions_Name);
			SmokeINDINV7_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Record_Type);
			SmokeINDINV7_Description=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Description);
			SmokeINDINV7_InstitutionType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Institution_Type);
			SmokeINDINV7_FundPreferences=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Fund_Preferences);
			SmokeINDINV7_IndustryPreferences=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Industry_Preferences);
			SmokeINDINV7_Street=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Street);
			SmokeINDINV7_City=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.City);
			SmokeINDINV7_State=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.State);
			SmokeINDINV7_PostalCode=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Postal_Code);
			SmokeINDINV7_Country=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Country);
			SmokeINDINV7_ShippingStreet=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Shipping_Street);
			SmokeINDINV7_ShippingCity=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Shipping_City);
			SmokeINDINV7_ShippingState=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Shipping_State);
			SmokeINDINV7_ShippingZip=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Shipping_Zip);
			SmokeINDINV7_ShippingCountry=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Shipping_Country);
			SmokeINDINV7_Phone=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Phone);
			SmokeINDINV7_Website=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Website);
			SmokeINDINV7_Fax=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeINDINV7", excelLabel.Fax);
			
			

			//COM1............
			SmokeCOM1=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeCOM1", excelLabel.Institutions_Name);
			SmokeCOM1_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeCOM1", excelLabel.Record_Type);
			
			//COM2............
			SmokeCOM2=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeCOM2", excelLabel.Institutions_Name);
			SmokeCOM2_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Institutions",excelLabel.Variable_Name, "SmokeCOM2", excelLabel.Record_Type);
			
			
			//***************************************** Contacts *****************************************//
			//CON1........
			SmokeC1_FName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC1", excelLabel.Contact_FirstName);
			SmokeC1_LName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC1", excelLabel.Contact_LastName);
			SmokeC1_EmailID=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC1", excelLabel.Contact_EmailId);
			SmokeC1_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC1", excelLabel.Record_Type);
			
			//CON2.......
			SmokeC2_FName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC2", excelLabel.Contact_FirstName);
			SmokeC2_LName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC2", excelLabel.Contact_LastName);
			SmokeC2_EmailID=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC2", excelLabel.Contact_EmailId);
			SmokeC2_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC2", excelLabel.Record_Type);
			
			//CON3......
			SmokeC3_FName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC3", excelLabel.Contact_FirstName);
			SmokeC3_LName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC3", excelLabel.Contact_LastName);
			SmokeC3_EmailID=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC3", excelLabel.Contact_EmailId);
			SmokeC3_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC3", excelLabel.Record_Type);
			
			//CON4.....
			SmokeC4_FName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC4", excelLabel.Contact_FirstName);
			SmokeC4_LName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC4", excelLabel.Contact_LastName);
			SmokeC4_EmailID=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC4", excelLabel.Contact_EmailId);
			SmokeC4_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC4", excelLabel.Record_Type);
			
			//CON5.....
			SmokeC5_FName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC5", excelLabel.Contact_FirstName);
			SmokeC5_LName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC5", excelLabel.Contact_LastName);
			SmokeC5_EmailID=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC5", excelLabel.Contact_EmailId);
			SmokeC5_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC5", excelLabel.Record_Type);
			
			//CON6.....
			SmokeC6_FName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC6", excelLabel.Contact_FirstName);
			SmokeC6_LName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC6", excelLabel.Contact_LastName);
			SmokeC6_EmailID=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC6", excelLabel.Contact_EmailId);
			SmokeC6_Other_Street=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC6", excelLabel.Other_Street);
			SmokeC6_Other_City=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC6", excelLabel.Other_City);
			SmokeC6_Other_State=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC6", excelLabel.Other_State);
			SmokeC6_Other_Zip=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC6", excelLabel.Other_Zip);
			SmokeC6_Other_Country=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC6", excelLabel.Other_Country);
			SmokeC6_RecordType=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC6", excelLabel.Record_Type);
			
			//CON7..........
			SmokeC7_FName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Contact_FirstName);
			SmokeC7_LName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Contact_LastName);
			SmokeC7_EmailID=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Contact_EmailId);
			SmokeC7_Description=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Description);
			SmokeC7_MailingStreet=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Mailing_Street);
			SmokeC7_MailingCity=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Mailing_City);
			SmokeC7_MailingZip=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Mailing_Zip);
			SmokeC7_MailingState=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Mailing_State);
			SmokeC7_MailingCountry=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Mailing_Country);
			SmokeC7_OtherStreet=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Other_Street);
			SmokeC7_OtherCity=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Other_City);
			SmokeC7_OtherState=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Other_State);
			SmokeC7_OtherCountry=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Other_Country);
			SmokeC7_OtherZip=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Other_Zip);
			SmokeC7_Phone=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Business_Phone);
			SmokeC7_Fax=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Fax);
			SmokeC7_Mobile=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Mobile_Phone);
			SmokeC7_Assistant=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Assistant);
			SmokeC7_Asst_Phone=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC7", excelLabel.Asst_Phone);
			
			

			//CON8..........
			SmokeC8_FName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Contact_FirstName);
			SmokeC8_LName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Contact_LastName);
			SmokeC8_EmailID=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Contact_EmailId);
			SmokeC8_Description=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Description);
			SmokeC8_MailingStreet=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Mailing_Street);
			SmokeC8_MailingCity=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Mailing_City);
			SmokeC8_MailingState=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Mailing_State);
			SmokeC8_MailingCountry=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Mailing_Country);
			SmokeC8_MailingZip=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Mailing_Zip);
			SmokeC8_OtherStreet=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Other_Street);
			SmokeC8_OtherCity=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Other_City);
			SmokeC8_OtherState=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Other_State);
			SmokeC8_OtherCountry=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Other_Country);
			SmokeC8_OtherZip=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Other_Zip);
			SmokeC8_Phone=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Business_Phone);
			SmokeC8_Fax=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Fax);
			SmokeC8_Mobile=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Mobile_Phone);
			SmokeC8_Assistant=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Assistant);
			SmokeC8_Asst_Phone=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC8", excelLabel.Asst_Phone);
			
			//CON9..........
			SmokeC9_FName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Contact_FirstName);
			SmokeC9_LName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Contact_LastName);
			SmokeC9_EmailID=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Contact_EmailId);
			SmokeC9_Description=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Description);
			SmokeC9_MailingStreet=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Mailing_Street);
			SmokeC9_MailingCity=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Mailing_City);
			SmokeC9_MailingState=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Mailing_State);
			SmokeC9_MailingCountry=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Mailing_Country);
			SmokeC9_MailingZip=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Mailing_Zip);
			SmokeC9_OtherStreet=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Other_Street);
			SmokeC9_OtherCity=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Other_City);
			SmokeC9_OtherState=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Other_State);
			SmokeC9_OtherCountry=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Other_Country);
			SmokeC9_OtherZip=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Other_Zip);
			SmokeC9_Phone=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Business_Phone);
			SmokeC9_Fax=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Fax);
			SmokeC9_Mobile=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Mobile_Phone);
			SmokeC9_Assistant=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Assistant);
			SmokeC9_Asst_Phone=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC9", excelLabel.Asst_Phone);
			
			
			//CON10..........
			SmokeC10_FName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Contact_FirstName);
			SmokeC10_LName=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Contact_LastName);
			SmokeC10_EmailID=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Contact_EmailId);
			SmokeC10_Description=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Description);
			SmokeC10_MailingStreet=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Mailing_Street);
			SmokeC10_MailingCity=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Mailing_City);
			SmokeC10_MailingState=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Mailing_State);
			SmokeC10_MailingCountry=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Mailing_Country);
			SmokeC10_MailingZip=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Mailing_Zip);
			SmokeC10_OtherStreet=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Other_Street);
			SmokeC10_OtherCity=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Other_City);
			SmokeC10_OtherState=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Other_State);
			SmokeC10_OtherCountry=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Other_Country);
			SmokeC10_OtherZip=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Other_Zip);
			SmokeC10_Phone=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Business_Phone);
			SmokeC10_Fax=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Fax);
			SmokeC10_Mobile=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Mobile_Phone);
			SmokeC10_Assistant=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Assistant);
			SmokeC10_Asst_Phone=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Asst_Phone);
			SmokeC10_Preferred_Mode_of_Contact=ExcelUtils.readData(wb,smokeFilePath,"Contacts",excelLabel.Variable_Name, "SmokeC10", excelLabel.Preferred_Mode_of_Contact);
			
			
			//************************************************** Marketing Initiative******************************************//
			Smoke_MI1=ExcelUtils.readData(wb,smokeFilePath,"MI",excelLabel.Variable_Name, "SmokeMI1", excelLabel.Marketing_InitiativeName);
			Smoke_MI2=ExcelUtils.readData(wb,smokeFilePath,"MI",excelLabel.Variable_Name, "SmokeMI2", excelLabel.Marketing_InitiativeName);
			Smoke_MI3=ExcelUtils.readData(wb,smokeFilePath,"MI",excelLabel.Variable_Name, "SmokeMI3", excelLabel.Marketing_InitiativeName);
			
			//***************************************** Fund *************************************************//
			Smoke_Fund1=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund1", excelLabel.Fund_Name);
			SmokeFund1_Type=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund1", excelLabel.Fund_Type);
			SmokeFund1_InvestmentCategory=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund1", excelLabel.Investment_Category);
			SmokeFund1Target_Commitments= ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund1", excelLabel.Target_Commitments);
			//SmokeFund1_1st_ClosingDate=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund1",excelLabel.Frist_Closing_Date);
			SmokeFund1_VintageYear=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund1",excelLabel.Vintage_Year);
			
			Smoke_Fund2=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund2", excelLabel.Fund_Name);
			SmokeFund2_Type=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund2", excelLabel.Fund_Type);
			SmokeFund2_InvestmentCategory=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund2", excelLabel.Investment_Category);
			SmokeFund2Target_Commitments= ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund2", excelLabel.Target_Commitments);
			SmokeFund2_VintageYear=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund2",excelLabel.Vintage_Year);
			
			Smoke_Fund3=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund3", excelLabel.Fund_Name);
			SmokeFund3_Type=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund3", excelLabel.Fund_Type);
			SmokeFund3_InvestmentCategory=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund3", excelLabel.Investment_Category);
			SmokeFund3Target_Commitments= ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund3", excelLabel.Target_Commitments);
			SmokeFund3_VintageYear=ExcelUtils.readData(wb,smokeFilePath,"Funds",excelLabel.Variable_Name, "SmokeFund3",excelLabel.Vintage_Year);
			
			//**********************************************************FundRaising***************************/
			Smoke_FR1 = ExcelUtils.readData(wb,smokeFilePath,"Fundraisings",excelLabel.Variable_Name, "SmokeFR1", excelLabel.FundRaising_Name);
			Smoke_FR2 = ExcelUtils.readData(wb,smokeFilePath,"Fundraisings",excelLabel.Variable_Name, "SmokeFR2", excelLabel.FundRaising_Name);
			SmokeFR1_Investment_Likely_Amount=ExcelUtils.readData(wb,smokeFilePath,"Fundraisings",excelLabel.Variable_Name, "SmokeFR2", excelLabel.Investment_Likely_Amount);
			Smoke_FR3 = ExcelUtils.readData(wb,smokeFilePath,"Fundraisings",excelLabel.Variable_Name, "SmokeFR3", excelLabel.FundRaising_Name);
			SmokeFR3_Investment_Likely_Amount=ExcelUtils.readData(wb,smokeFilePath,"Fundraisings",excelLabel.Variable_Name, "SmokeFR3", excelLabel.Investment_Likely_Amount);
			Smoke_FR4 = ExcelUtils.readData(wb,smokeFilePath,"Fundraisings",excelLabel.Variable_Name, "SmokeFR4", excelLabel.FundRaising_Name);
			SmokeFR4_Investment_Likely_Amount=ExcelUtils.readData(wb,smokeFilePath,"Fundraisings",excelLabel.Variable_Name, "SmokeFR4", excelLabel.Investment_Likely_Amount);
			Smoke_FR5 = ExcelUtils.readData(wb,smokeFilePath,"Fundraisings",excelLabel.Variable_Name, "SmokeFR5", excelLabel.FundRaising_Name);
			SmokeFR5_Investment_Likely_Amount=ExcelUtils.readData(wb,smokeFilePath,"Fundraisings",excelLabel.Variable_Name, "SmokeFR5", excelLabel.Investment_Likely_Amount);
			Smoke_FR6 = ExcelUtils.readData(wb,smokeFilePath,"Fundraisings",excelLabel.Variable_Name, "SmokeFR6", excelLabel.FundRaising_Name);
			SmokeFR6_Investment_Likely_Amount=ExcelUtils.readData(wb,smokeFilePath,"Fundraisings",excelLabel.Variable_Name, "SmokeFR6", excelLabel.Investment_Likely_Amount);
			
			//****************************************FundRaising Contact**************************************************/
			SmokeFRC1_ID=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC1", excelLabel.Fundraising_Contact_ID);
			SmokeFRC1_Role=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC1", excelLabel.Role);
			SmokeFRC1_Primary=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC1", excelLabel.Primary);
			
			SmokeFRC3_ID=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC3", excelLabel.Fundraising_Contact_ID);
			SmokeFRC3_Role=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC3", excelLabel.Role);
			SmokeFRC3_Primary=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC3", excelLabel.Primary);
			
			SmokeFRC4_ID=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC4", excelLabel.Fundraising_Contact_ID);
			SmokeFRC4_Role=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC4", excelLabel.Role);
			SmokeFRC4_Primary=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC4", excelLabel.Primary);
			
			SmokeFRC5_ID=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC5", excelLabel.Fundraising_Contact_ID);
			SmokeFRC5_Role=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC5", excelLabel.Role);
			SmokeFRC5_Primary=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC5", excelLabel.Primary);
			
			SmokeFRC6_ID=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC6", excelLabel.Fundraising_Contact_ID);
			SmokeFRC6_Role=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC6", excelLabel.Role);
			SmokeFRC6_Primary=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC6", excelLabel.Primary);
			
			SmokeFRC7_ID=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC7", excelLabel.Fundraising_Contact_ID);
			SmokeFRC7_Role=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC7", excelLabel.Role);
			SmokeFRC7_Primary=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC7", excelLabel.Primary);
			
			SmokeFRC8_ID=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC8", excelLabel.Fundraising_Contact_ID);
			SmokeFRC8_Role=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC8", excelLabel.Role);
			SmokeFRC8_Primary=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC8", excelLabel.Primary);
			
			SmokeFRC9_ID=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC9", excelLabel.Fundraising_Contact_ID);
			SmokeFRC9_Role=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC9", excelLabel.Role);
			SmokeFRC9_Primary=ExcelUtils.readData(wb,smokeFilePath,"Fundraising Contacts",excelLabel.Variable_Name, "SmokeFRC9", excelLabel.Primary);
			
			
			//******************************************* Commitment ***************************************
			SmokeCOMM1_ID=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM1", excelLabel.Commitment_ID);
			SmokeCOMM1_CommitmentAmount=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM1", excelLabel.Commitment_Amount);
			SmokeCOMM1_partnerType=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM1", excelLabel.Partner_Type);
			SmokeCOMM1_TaxForms=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM1", excelLabel.Tax_Forms);
			SmokeCOMM1_FinalCommitmentDate=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM1", excelLabel.Final_Commitment_Date);
			
			
			
			SmokeCOMM2_ID=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM2", excelLabel.Commitment_ID);
			SmokeCOMM2_CommitmentAmount=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM2", excelLabel.Commitment_Amount);
			SmokeCOMM2_partnerType=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM2", excelLabel.Partner_Type);
			SmokeCOMM2_TaxForms=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM2", excelLabel.Tax_Forms);
			SmokeCOMM2_FinalCommitmentDate=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM2", excelLabel.Final_Commitment_Date);
			
			
			SmokeCOMM3_ID=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM3", excelLabel.Commitment_ID);
			SmokeCOMM3_CommitmentAmount=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM3", excelLabel.Commitment_Amount);
			SmokeCOMM3_partnerType=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM3", excelLabel.Partner_Type);
			SmokeCOMM3_TaxForms=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM3", excelLabel.Tax_Forms);
			SmokeCOMM3_FinalCommitmentDate=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM3", excelLabel.Final_Commitment_Date);
			SmokeCoMM3_PlacementFee=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM3", excelLabel.Placement_Fee);
			
			SmokeCOMM4_ID=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM4", excelLabel.Commitment_ID);
			SmokeCOMM4_CommitmentAmount=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM4", excelLabel.Commitment_Amount);
			SmokeCOMM4_partnerType=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM4", excelLabel.Partner_Type);
			SmokeCOMM4_TaxForms=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM4", excelLabel.Tax_Forms);
			SmokeCOMM4_FinalCommitmentDate=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM4", excelLabel.Final_Commitment_Date);
			SmokeCOMM4_PlacementFee=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM4", excelLabel.Placement_Fee);
			
			SmokeCOMM5_ID=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM5", excelLabel.Commitment_ID);
			SmokeCOMM5_CommitmentAmount=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM5", excelLabel.Commitment_Amount);
			SmokeCOMM5_partnerType=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM5", excelLabel.Partner_Type);
			SmokeCOMM5_TaxForms=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM5", excelLabel.Tax_Forms);
			SmokeCOMM5_FinalCommitmentDate=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM5", excelLabel.Final_Commitment_Date);
			
			SmokeCOMM6_ID=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM6", excelLabel.Commitment_ID);
			SmokeCOMM6_CommitmentAmount=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM6", excelLabel.Commitment_Amount);
			SmokeCOMM6_partnerType=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM6", excelLabel.Partner_Type);
			SmokeCOMM6_TaxForms=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM6", excelLabel.Tax_Forms);
			SmokeCOMM6_FinalCommitmentDate=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM6", excelLabel.Final_Commitment_Date);
			
			SmokeCOMM7_ID=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM7", excelLabel.Commitment_ID);
			SmokeCOMM7_CommitmentAmount=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM7", excelLabel.Commitment_Amount);
			SmokeCOMM7_partnerType=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM7", excelLabel.Partner_Type);
			SmokeCOMM7_TaxForms=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM7", excelLabel.Tax_Forms);
			SmokeCOMM7_FinalCommitmentDate=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM7", excelLabel.Final_Commitment_Date);
			
			SmokeCOMM8_ID=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM1", excelLabel.Commitment_ID);
			SmokeCOMM8_CommitmentAmount=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM8", excelLabel.Commitment_Amount);
			SmokeCOMM8_partnerType=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM8", excelLabel.Partner_Type);
			SmokeCOMM8_TaxForms=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM8", excelLabel.Tax_Forms);
			SmokeCOMM8_FinalCommitmentDate=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM1", excelLabel.Final_Commitment_Date);
			SmokeCOMM8_TotalAmountCalled=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM8", excelLabel.Total_Amount_Called);
			SmokeCOMM8_TotalAmountReceived=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM8", excelLabel.Total_Amount_Received);
			SmokeCOMM8_TotalUncalledAmount=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM8", excelLabel.Total_Uncalled_Amount);
			SmokeCOMM8_TotalCommitmentDue=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM8", excelLabel.Total_Commitment_Due);
			SmokeCOMM8_CommitmentCalled=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM8", excelLabel.Commitment_Called);
			SmokeCOMM8_CalledDue=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM8", excelLabel.Called_Due);
			SmokeCOMM8_TotalDist=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM8", excelLabel.TotalDistributions);
			SmokeCOMM8_CapitalReturnedRecallable=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM8", excelLabel.Capital_Returned_Recallable);
			SmokeCOMM8_CapitalReturnedNonRecallable=ExcelUtils.readData(wb,smokeFilePath, "Commitments", excelLabel.Variable_Name,"SmokeCOMM8", excelLabel.Capital_Returned_NonRecallable);
			
			//******************************PartnerShip*******************************//
			Smoke_P1=ExcelUtils.readData(wb,smokeFilePath, "Partnerships", excelLabel.Variable_Name,"SmokeP1", excelLabel.PartnerShip_Name);
			
			Smoke_P2=ExcelUtils.readData(wb,smokeFilePath, "Partnerships", excelLabel.Variable_Name,"SmokeP2", excelLabel.PartnerShip_Name);
			SmokeP2_Fund_Investment_Category=ExcelUtils.readData(wb,smokeFilePath, "Partnerships", excelLabel.Variable_Name,"SmokeP2", excelLabel.Fund_Investment_Category);
			Smoke_P3=ExcelUtils.readData(wb,smokeFilePath, "Partnerships", excelLabel.Variable_Name,"SmokeP3", excelLabel.PartnerShip_Name);
			Smoke_P4=ExcelUtils.readData(wb,smokeFilePath, "Partnerships", excelLabel.Variable_Name,"SmokeP4", excelLabel.PartnerShip_Name);
			

			//****************************************Drawdown and Capital Call**************************************************/
			SmokeDD1_ID=ExcelUtils.readData(wb,smokeFilePath,"FundDrawdown",excelLabel.Variable_Name, "DD1", excelLabel.DrawdownID);
			SmokeDD1_CallAmount=ExcelUtils.readData(wb,smokeFilePath,"FundDrawdown",excelLabel.Variable_Name, "DD1", excelLabel.CallAmount);
			SmokeDD1_FundName=ExcelUtils.readData(wb,smokeFilePath,"FundDrawdown",excelLabel.Variable_Name, "DD1", excelLabel.Fund_Name);
			SmokeDD1_CallDate=ExcelUtils.readData(wb,smokeFilePath,"FundDrawdown",excelLabel.Variable_Name, "DD1", excelLabel.CallDate);
			SmokeDD1_DueDate=ExcelUtils.readData(wb,smokeFilePath,"FundDrawdown",excelLabel.Variable_Name, "DD1", excelLabel.DueDate);
			SmokeDD1_AmountDue=ExcelUtils.readData(wb,smokeFilePath,"FundDrawdown",excelLabel.Variable_Name, "DD1", excelLabel.AmountDue);
			SmokeDD1_TotalCallAmountReceived=ExcelUtils.readData(wb,smokeFilePath,"FundDrawdown",excelLabel.Variable_Name, "DD1", excelLabel.Total_Call_Amount_Received);
			SmokeDD1_CapitalAmount=ExcelUtils.readData(wb,smokeFilePath,"FundDrawdown",excelLabel.Variable_Name, "DD1", excelLabel.CapitalAmount);
			SmokeDD1_ManagementFee=ExcelUtils.readData(wb,smokeFilePath,"FundDrawdown",excelLabel.Variable_Name, "DD1", excelLabel.ManagementFee);
			SmokeDD1_OtherFee=ExcelUtils.readData(wb,smokeFilePath,"FundDrawdown",excelLabel.Variable_Name, "DD1", excelLabel.OtherFee);
			SmokeCC1_ID=ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC1", excelLabel.CapitalCalllID);
			SmokeCC2_ID=ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC2", excelLabel.CapitalCalllID);
			SmokeCC3_ID=ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC3", excelLabel.CapitalCalllID);
			SmokeCC4_ID=ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC4", excelLabel.CapitalCalllID);
			SmokeCC5_ID=ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC5", excelLabel.CapitalCalllID);
			
			SmokeFD1ID = ExcelUtils.readData(wb,smokeFilePath,"FundDistribution",excelLabel.Variable_Name, "FD1", excelLabel.FundDistributionID);
			
			SmokeID1ID = ExcelUtils.readData(wb,smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID1", excelLabel.InvestorDistributionID);
			SmokeID2ID = ExcelUtils.readData(wb,smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID2", excelLabel.InvestorDistributionID);
			SmokeID3ID = ExcelUtils.readData(wb,smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID3", excelLabel.InvestorDistributionID);
			SmokeID4ID = ExcelUtils.readData(wb,smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID4", excelLabel.InvestorDistributionID);
			SmokeID5ID = ExcelUtils.readData(wb,smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID5", excelLabel.InvestorDistributionID);
			
			SmokeCC1Data[0] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC1", excelLabel.DrawdownID);
			SmokeCC1Data[1] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC1", excelLabel.CapitalAmount);
			SmokeCC1Data[2] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC1", excelLabel.ManagementFee);
			SmokeCC1Data[3] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC1", excelLabel.OtherFee);
			SmokeCC1Data[4] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC1", excelLabel.CallAmount);
			SmokeCC1Data[5] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC1", excelLabel.CallDate);
			SmokeCC1Data[6] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC1", excelLabel.DueDate);
			SmokeCC1Data[7] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC1", excelLabel.CallAmountReceived);
			SmokeCC1Data[8] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC1", excelLabel.ReceivedDate);
			SmokeCC1Data[9] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC1", excelLabel.AmountDue);
			SmokeCC1Data[10] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC1", excelLabel.Commitment_ID);
			
			SmokeCC2Data[0] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC2", excelLabel.DrawdownID);
			SmokeCC2Data[1] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC2", excelLabel.CapitalAmount);
			SmokeCC2Data[2] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC2", excelLabel.ManagementFee);
			SmokeCC2Data[3] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC2", excelLabel.OtherFee);
			SmokeCC2Data[4] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC2", excelLabel.CallAmount);
			SmokeCC2Data[5] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC2", excelLabel.CallDate);
			SmokeCC2Data[6] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC2", excelLabel.DueDate);
			SmokeCC2Data[7] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC2", excelLabel.CallAmountReceived);
			SmokeCC2Data[8] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC2", excelLabel.ReceivedDate);
			SmokeCC2Data[9] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC2", excelLabel.AmountDue);
			SmokeCC2Data[10] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC2", excelLabel.Commitment_ID);
			
			SmokeCC3Data[0] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC3", excelLabel.DrawdownID);
			SmokeCC3Data[1] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC3", excelLabel.CapitalAmount);
			SmokeCC3Data[2] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC3", excelLabel.ManagementFee);
			SmokeCC3Data[3] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC3", excelLabel.OtherFee);
			SmokeCC3Data[4] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC3", excelLabel.CallAmount);
			SmokeCC3Data[5] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC3", excelLabel.CallDate);
			SmokeCC3Data[6] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC3", excelLabel.DueDate);
			SmokeCC3Data[7] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC3", excelLabel.CallAmountReceived);
			SmokeCC3Data[8] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC3", excelLabel.ReceivedDate);
			SmokeCC3Data[9] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC3", excelLabel.AmountDue);
			SmokeCC3Data[10] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC3", excelLabel.Commitment_ID);
			
			SmokeCC4Data[0] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC4", excelLabel.DrawdownID);
			SmokeCC4Data[1] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC4", excelLabel.CapitalAmount);
			SmokeCC4Data[2] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC4", excelLabel.ManagementFee);
			SmokeCC4Data[3] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC4", excelLabel.OtherFee);
			SmokeCC4Data[4] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC4", excelLabel.CallAmount);
			SmokeCC4Data[5] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC4", excelLabel.CallDate);
			SmokeCC4Data[6] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC4", excelLabel.DueDate);
			SmokeCC4Data[7] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC4", excelLabel.CallAmountReceived);
			SmokeCC4Data[8] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC4", excelLabel.ReceivedDate);
			SmokeCC4Data[9] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC4", excelLabel.AmountDue);
			SmokeCC4Data[10] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC4", excelLabel.Commitment_ID);
			
			SmokeCC5Data[0] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC5", excelLabel.DrawdownID);
			SmokeCC5Data[1] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC5", excelLabel.CapitalAmount);
			SmokeCC5Data[2] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC5", excelLabel.ManagementFee);
			SmokeCC5Data[3] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC5", excelLabel.OtherFee);
			SmokeCC5Data[4] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC5", excelLabel.CallAmount);
			SmokeCC5Data[5] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC5", excelLabel.CallDate);
			SmokeCC5Data[6] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC5", excelLabel.DueDate);
			SmokeCC5Data[7] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC5", excelLabel.CallAmountReceived);
			SmokeCC5Data[8] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC5", excelLabel.ReceivedDate);
			SmokeCC5Data[9] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC5", excelLabel.AmountDue);
			SmokeCC5Data[10] = ExcelUtils.readData(wb,smokeFilePath,"CapitalCall",excelLabel.Variable_Name, "CC5", excelLabel.Commitment_ID);
			//****************************************************************************************************************/
			
			
			Smoke_NewTask1Subject = ExcelUtils.readData(wb,smokeFilePath,"Activities",excelLabel.Variable_Name, "SmokeNewTask1", excelLabel.Subject);
			
			Smoke_NewTask2Subject = ExcelUtils.readData(wb,smokeFilePath,"Activities",excelLabel.Variable_Name, "SmokeNewTask2", excelLabel.Subject);
			
			Smoke_NewEvent1Subject = ExcelUtils.readData(wb,smokeFilePath,"Activities",excelLabel.Variable_Name, "SmokeNewEvent1", excelLabel.Subject);
			Smoke_NewEvent1StartDate = ExcelUtils.readData(wb,smokeFilePath,"Activities",excelLabel.Variable_Name, "SmokeNewEvent1", excelLabel.Start);
			Smoke_NewEvent1RelatedTo = ExcelUtils.readData(wb,smokeFilePath,"Activities",excelLabel.Variable_Name, "SmokeNewEvent1", excelLabel.Related_To);
			
			Smoke_NewEvent2Subject = ExcelUtils.readData(wb,smokeFilePath,"Activities",excelLabel.Variable_Name, "SmokeNewEvent2", excelLabel.Subject);
			Smoke_NewEvent2StartDate = ExcelUtils.readData(wb,smokeFilePath,"Activities",excelLabel.Variable_Name, "SmokeNewEvent2", excelLabel.Start);
			Smoke_NewEvent2RelatedTo = SmokeINS4;
			
			Smoke_CallLog1Subject = ExcelUtils.readData(wb,smokeFilePath,"Activities",excelLabel.Variable_Name, "SmokeCallLogl1", excelLabel.Subject);
			Smoke_CallLog1DueDate = ExcelUtils.readData(wb,smokeFilePath,"Activities",excelLabel.Variable_Name, "SmokeCallLogl1", excelLabel.Due_Date);
			Smoke_CallLog1RelatedTo = ExcelUtils.readData(wb,smokeFilePath,"Activities",excelLabel.Variable_Name, "SmokeCallLogl1", excelLabel.Related_To);
			
			Smoke_CallLog2Subject = ExcelUtils.readData(wb,smokeFilePath,"Activities",excelLabel.Variable_Name, "SmokeCallLog2", excelLabel.Subject);
			Smoke_CallLog2DueDate = ExcelUtils.readData(wb,smokeFilePath,"Activities",excelLabel.Variable_Name, "SmokeCallLog2", excelLabel.Due_Date);
			Smoke_CallLog2RelatedTo = Smoke_Fund2;
			
			//****************************LP***********************************//
			
			Smoke_LP1=ExcelUtils.readData(wb,smokeFilePath, "Limited Partner", excelLabel.Variable_Name,"SmokeLP1", excelLabel.Limited_Partner);
			
			Smoke_LP2=ExcelUtils.readData(wb,smokeFilePath, "Limited Partner", excelLabel.Variable_Name,"SmokeLP2", excelLabel.Limited_Partner);
			Smoke_LP3=ExcelUtils.readData(wb,smokeFilePath, "Limited Partner", excelLabel.Variable_Name,"SmokeLP3", excelLabel.Limited_Partner);
			SmokeLP3_BankName=ExcelUtils.readData(wb,smokeFilePath, "Limited Partner", excelLabel.Variable_Name,"SmokeLP3", excelLabel.Bank_Name);
			
			Smoke_LP4=ExcelUtils.readData(wb,smokeFilePath, "Limited Partner", excelLabel.Variable_Name,"SmokeLP4", excelLabel.Limited_Partner);
			SmokeLP4_Total_Fund_Commitments=ExcelUtils.readData(wb,smokeFilePath, "Limited Partner", excelLabel.Variable_Name,"SmokeLP4", excelLabel.Total_Fund_Commitments);
			SmokeLP4_Total_CoInvesment_Commitments=ExcelUtils.readData(wb,smokeFilePath, "Limited Partner", excelLabel.Variable_Name,"SmokeLP4", excelLabel.Total_CoInvestment_Commitments);
			
			
			Smoke_LP5=ExcelUtils.readData(wb,smokeFilePath, "Limited Partner", excelLabel.Variable_Name,"SmokeLP5", excelLabel.Limited_Partner);
			
			SmokeLP1_Total_Fund_Commitments=ExcelUtils.readData(wb,smokeFilePath, "Limited Partner", excelLabel.Variable_Name,"SmokeLP1", excelLabel.Total_Fund_Commitments);
			SmokeLP1_Total_CoInvesment_Commitments=ExcelUtils.readData(wb,smokeFilePath, "Limited Partner", excelLabel.Variable_Name,"SmokeLP1", excelLabel.Total_CoInvestment_Commitments);
			SmokeLP2_Total_Fund_Commitments=ExcelUtils.readData(wb,smokeFilePath, "Limited Partner", excelLabel.Variable_Name,"SmokeLP2", excelLabel.Total_Fund_Commitments);
			SmokeLP2_Total_CoInvesment_Commitments=ExcelUtils.readData(wb,smokeFilePath, "Limited Partner", excelLabel.Variable_Name,"SmokeLP2", excelLabel.Total_CoInvestment_Commitments);
			
			//***************************************** PipeLine *************************************************//
			
			Smoke_PL2Name = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL2", excelLabel.Pipeline_Name);
			Smoke_PL2CompanyName = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL2", excelLabel.Company_Name);
			Smoke_PL2Stage = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL2", excelLabel.Stage);
			Smoke_PL2Source = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL2", excelLabel.Source);
			Smoke_PL2SourceFirm = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL2", excelLabel.Source_Firm);
			Smoke_PL2SourceContact = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL2", excelLabel.Source_Contact);
			Smoke_PL2Dealtype = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL2", excelLabel.Deal_Type);
			Smoke_PL2Employees = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL2", excelLabel.Employees);
			Smoke_PL2SourceFirm_Website = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL2", excelLabel.Website);
			Smoke_PL2SourceContact_Email = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL2", excelLabel.Email);
			Smoke_PL2AgeOfCurrentStage = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL2", excelLabel.Age_of_Current_Stage);
			
			Smoke_PL3Name = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL3", excelLabel.Pipeline_Name);
			Smoke_PL3CompanyName = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL3", excelLabel.Company_Name);
			Smoke_PL3Stage = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL3", excelLabel.Stage);
			Smoke_PL3Source = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL3", excelLabel.Source);
			Smoke_PL3SourceFirm = SmokeINDINV1;
			Smoke_PL3SourceContact_Fname = SmokeC3_FName;
			Smoke_PL3SourceContact_Lname=SmokeC3_LName;
			
			Smoke_PL1Name = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL1", excelLabel.Pipeline_Name);
			Smoke_PL1CompanyName = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL1", excelLabel.Company_Name);
			Smoke_PL1LastStageChangeDate = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL1", excelLabel.Last_Stage_Change_Date);
			Smoke_PL1HighestStageReached = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL1", excelLabel.Highest_Stage_Reached);
			Smoke_PL1AgeOfCurrentStage = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL1", excelLabel.Age_of_Current_Stage);
			Smoke_PL1Stage = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL1", excelLabel.Stage);
			Smoke_PL1Source = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL1", excelLabel.Source);
			Smoke_PL1SourceFirm = SmokeINS1;
			Smoke_PL1SourceContact_Name =ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL1", excelLabel.Source_Contact);
			Smoke_PL1FirstStageChanged = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL1", excelLabel.First_Stage_Changed);
			Smoke_PL1SecondStageChanged = ExcelUtils.readData(wb,smokeFilePath,"PipeLine",excelLabel.Variable_Name, "SmokePL1", excelLabel.Second_Stage_Changed);
			
			// office Location
			
			Smoke_OFFLoc1Name = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC1", excelLabel.Office_Location_Name);
			Smoke_OFFLoc1Street = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC1", excelLabel.Street);
			Smoke_OFFLoc1City = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC1", excelLabel.City);
			Smoke_OFFLoc1StateProvince = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC1", excelLabel.State_Province);
			Smoke_OFFLoc1Country = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC1", excelLabel.Country);
			Smoke_OFFLoc1ZIP = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC1", excelLabel.ZIP);
			Smoke_OFFLoc1OrgName = SmokeINS2;
			Smoke_OFFLoc1Phone = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC1", excelLabel.Phone);
			Smoke_OFFLoc1Fax =ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC1", excelLabel.Fax);
			Smoke_OFFLoc1Primary = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC1", excelLabel.Primary);
			Smoke_OFFLoc1UpdatedPrimary = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC1", excelLabel.Updated_Primary);
			
			Smoke_OFFLoc2Name = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC2", excelLabel.Office_Location_Name);
			Smoke_OFFLoc2Street = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC2", excelLabel.Street);
			Smoke_OFFLoc2City = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC2", excelLabel.City);
			Smoke_OFFLoc2StateProvince = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC2", excelLabel.State_Province);
			Smoke_OFFLoc2Country = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC2", excelLabel.Country);
			Smoke_OFFLoc2ZIP = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC2", excelLabel.ZIP);
			Smoke_OFFLoc2OrgName = SmokeINS2;
			Smoke_OFFLoc2Phone = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC2", excelLabel.Phone);
			Smoke_OFFLoc2Fax =ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC2", excelLabel.Fax);
			Smoke_OFFLoc2Primary = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC2", excelLabel.Primary);
			Smoke_OFFLoc2UpdatedPrimary = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC2", excelLabel.Updated_Primary);
			
			Smoke_OFFLoc1UpdName = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC3", excelLabel.Office_Location_Name);
			Smoke_OFFLoc1UpdStreet = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC3", excelLabel.Street);
			Smoke_OFFLoc1UpdCity = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC3", excelLabel.City);
			Smoke_OFFLoc1UpdStateProvince = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC3", excelLabel.State_Province);
			Smoke_OFFLoc1UpdCountry = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC3", excelLabel.Country);
			Smoke_OFFLoc1UpdZIP = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC3", excelLabel.ZIP);
			Smoke_OFFLoc1UpdOrgName = SmokeINS2;
			Smoke_OFFLoc1UpdPhone = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC3", excelLabel.Phone);
			Smoke_OFFLoc1UpdFax =ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC3", excelLabel.Fax);
			Smoke_OFFLoc1UpdPrimary = ExcelUtils.readData(wb,smokeFilePath,"Office Location",excelLabel.Variable_Name, "SmokeOFFLOC3", excelLabel.Primary);
			
			
			//**********************************************************Report ******************************************************/

			
			SmokeReportFolderName=ExcelUtils.readData(wb,smokeFilePath,"Report",excelLabel.Variable_Name, "SmokeReport1", excelLabel.Report_Folder_Name);
			SmokeReportName=ExcelUtils.readData(wb,smokeFilePath,"Report",excelLabel.Variable_Name, "SmokeReport1", excelLabel.Report_Name);	
			SmokeReportType=ExcelUtils.readData(wb,smokeFilePath,"Report",excelLabel.Variable_Name, "SmokeReport1", excelLabel.Select_Report_Type);
			SmokeReportShow=ExcelUtils.readData(wb,smokeFilePath,"Report",excelLabel.Variable_Name, "SmokeReport1", excelLabel.Show);
			SmokeReportRange=ExcelUtils.readData(wb,smokeFilePath,"Report",excelLabel.Variable_Name, "SmokeReport1", excelLabel.Range);

			//**********************************************************Email Template ******************************************************/
			
			EmailTemplate1_Subject = ExcelUtils.readData(wb,smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,"EmailTemplate1", excelLabel.Subject);
			EmailTemplate1_Body = ExcelUtils.readData(wb,smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,"EmailTemplate1", excelLabel.Email_Body);
			EmailTemplate1_FolderName = ExcelUtils.readData(wb,smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,"EmailTemplate1", excelLabel.Email_Template_Folder_Label);
			EmailTemplate1_TemplateName = ExcelUtils.readData(wb,smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,"EmailTemplate1", excelLabel.Email_Template_Name);
			EmailTemplate1_TemplateDescription = ExcelUtils.readData(wb,smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,"EmailTemplate1", excelLabel.Description);
			

			//****************************************************************	EntityorAccount **********************************************************//


			// TASK INS1..............
			Smoke_TaskINS1Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS1", excelLabel.Institutions_Name);
			Smoke_TaskINS1RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS1", excelLabel.Record_Type);

			// TASK INS2..............
			Smoke_TaskINS2Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS2", excelLabel.Institutions_Name);
			Smoke_TaskINS2RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS2", excelLabel.Record_Type);

			// TASK INS3..............
			Smoke_TaskINS3Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS3", excelLabel.Institutions_Name);
			Smoke_TaskINS3RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS3", excelLabel.Record_Type);

			// TASK INS4..............
			Smoke_TaskINS4Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS4", excelLabel.Institutions_Name);
			Smoke_TaskINS4RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS4", excelLabel.Record_Type);

			// TASK INS5..............
			Smoke_TaskINS5Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS5", excelLabel.Institutions_Name);
			Smoke_TaskINS5RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AATASKINS5", excelLabel.Record_Type);
			Smoke_TaskINS5Website="www.facebook.com";
			
			// MEETING INS1..............
			Smoke_MTINS1Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS1", excelLabel.Institutions_Name);
			Smoke_MTINS1RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS1", excelLabel.Record_Type);

			// MEETING INS2..............
			Smoke_MTINS2Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS2", excelLabel.Institutions_Name);
			Smoke_MTINS2RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS2", excelLabel.Record_Type);

			// MEETING INS3..............
			Smoke_MTINS3Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS3", excelLabel.Institutions_Name);

			// MEETING INS4..............
			Smoke_MTINS4Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS4", excelLabel.Institutions_Name);
			Smoke_MTINS4RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS4", excelLabel.Record_Type);

			// MEETING INS5..............
			Smoke_MTINS5Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS5", excelLabel.Institutions_Name);
			Smoke_MTINS5RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS5", excelLabel.Record_Type);

			// MEETING INS6..............
			Smoke_MTINS6Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS6", excelLabel.Institutions_Name);
			Smoke_MTINS6RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS6", excelLabel.Record_Type);
			Smoke_MTINS6Status="Under Evaluation";
			Smoke_MTINS7Status="Watchlist";
			// MEETING INS7..............
			Smoke_MTINS7Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS7", excelLabel.Institutions_Name);
			Smoke_MTINS7RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS7", excelLabel.Record_Type);

			// MEETING INS8..............
			Smoke_MTINS8Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS8", excelLabel.Institutions_Name);
			Smoke_MTINS8RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS8", excelLabel.Record_Type);

			// MEETING INS9..............
			Smoke_MTINS9Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS9", excelLabel.Institutions_Name);
			Smoke_MTINS9RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "AAMTINS9", excelLabel.Record_Type);

			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				wb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			try {
				fis=new FileInputStream(new File(testCasesFilePath));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				wb=WorkbookFactory.create(fis);
			} catch (EncryptedDocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//****************************************************************	Contact **********************************************************//

			// TASK Contact1..............
			Smoke_TaskContact1FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC1", excelLabel.Contact_FirstName);
			Smoke_TaskContact1LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC1", excelLabel.Contact_LastName);
			Smoke_TaskContact1INSName=Smoke_TaskINS1Name;
			Smoke_TaskContact1EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC1", excelLabel.Contact_EmailId);
			Smoke_TaskContact1RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC1", excelLabel.Record_Type);
			
			// TASK Contact2..............
			Smoke_TaskContact2FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC2", excelLabel.Contact_FirstName);
			Smoke_TaskContact2LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC2", excelLabel.Contact_LastName);
			Smoke_TaskContact2INSName=Smoke_TaskINS1Name;
			Smoke_TaskContact2EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC2", excelLabel.Contact_EmailId);
			Smoke_TaskContact2RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC2", excelLabel.Record_Type);
			
			// TASK Contact3..............
			Smoke_TaskContact3FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC3", excelLabel.Contact_FirstName);
			Smoke_TaskContact3LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC3", excelLabel.Contact_LastName);
			Smoke_TaskContact3INSName=Smoke_TaskINS2Name;
			Smoke_TaskContact3EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC3", excelLabel.Contact_EmailId);
			Smoke_TaskContact3RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC3", excelLabel.Record_Type);

			// TASK Contact4..............
			Smoke_TaskContact4FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC4", excelLabel.Contact_FirstName);
			Smoke_TaskContact4LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC4", excelLabel.Contact_LastName);
			Smoke_TaskContact4INSName=Smoke_TaskINS2Name;
			Smoke_TaskContact4EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC4", excelLabel.Contact_EmailId);
			Smoke_TaskContact4RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC4", excelLabel.Record_Type);
			Smoke_TaskContact4UpdatedName=Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName+"UP";
			Smoke_TaskContact4Title="Advisor";
			
			// TASK Contact5..............
			Smoke_TaskContact5FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC5", excelLabel.Contact_FirstName);
			Smoke_TaskContact5LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC5", excelLabel.Contact_LastName);
			Smoke_TaskContact5INSName=Smoke_TaskINS4Name;
			Smoke_TaskContact5EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC5", excelLabel.Contact_EmailId);
			Smoke_TaskContact5RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC5", excelLabel.Record_Type);
			
			// MT Contact1..............
			Smoke_MTContact1FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC1", excelLabel.Contact_FirstName);
			Smoke_MTContact1LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC1", excelLabel.Contact_LastName);
			Smoke_MTContact1INSName=Smoke_MTINS1Name;
			Smoke_MTContact1EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC1", excelLabel.Contact_EmailId);
			Smoke_MTContact1RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC1", excelLabel.Record_Type);

			// MT Contact2..............
			Smoke_MTContact2FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC2", excelLabel.Contact_FirstName);
			Smoke_MTContact2LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC2", excelLabel.Contact_LastName);
			Smoke_MTContact2INSName=Smoke_MTINS1Name;
			Smoke_MTContact2EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC2", excelLabel.Contact_EmailId);
			Smoke_MTContact2RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC2", excelLabel.Record_Type);

			// MT Contact3..............
			Smoke_MTContact3FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC3", excelLabel.Contact_FirstName);
			Smoke_MTContact3LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC3", excelLabel.Contact_LastName);
			Smoke_MTContact3INSName=Smoke_MTINS2Name;
			Smoke_MTContact3EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC3", excelLabel.Contact_EmailId);
			Smoke_MTContact3RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC3", excelLabel.Record_Type);

			// MT Contact4..............
			Smoke_MTContact4FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC4", excelLabel.Contact_FirstName);
			Smoke_MTContact4LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC4", excelLabel.Contact_LastName);
			Smoke_MTContact4INSName=Smoke_MTINS2Name;
			Smoke_MTContact4EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC4", excelLabel.Contact_EmailId);
			Smoke_MTContact4RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC4", excelLabel.Record_Type);

			// MT Contact5..............
			Smoke_MTContact5FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC5", excelLabel.Contact_FirstName);
			Smoke_MTContact5LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC5", excelLabel.Contact_LastName);
			Smoke_MTContact5INSName=Smoke_MTINS6Name;
			Smoke_MTContact5EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC5", excelLabel.Contact_EmailId);
			Smoke_MTContact5RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AAMTC5", excelLabel.Record_Type);


			//****************************************************************	FundorDeal **********************************************************//

			// Task Fund1..............
			Smoke_TaskFund1Name=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND1", excelLabel.Fund_Name);
			Smoke_TaskFund1Type=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND1", excelLabel.Fund_Type);
			Smoke_TaskFund1InvestmentCategory=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND1", excelLabel.Fund_InvestmentCategory);
			Smoke_TaskFund1RecordType=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND1", excelLabel.Record_Type);


			// Task Fund2..............
			Smoke_TaskFund2Name=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND2", excelLabel.Fund_Name);
			Smoke_TaskFund2Type=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND2", excelLabel.Fund_Type);
			Smoke_TaskFund2InvestmentCategory=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND2", excelLabel.Fund_InvestmentCategory);
			Smoke_TaskFund2RecordType=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AATASKFUND2", excelLabel.Record_Type);

			// MT Fund1..............
			Smoke_MTFund1Name=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND1", excelLabel.Fund_Name);
			Smoke_MTFund1Type=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND1", excelLabel.Fund_Type);
			Smoke_MTFund1InvestmentCategory=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND1", excelLabel.Fund_InvestmentCategory);
			Smoke_MTFund1RecordType=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND1", excelLabel.Record_Type);

			// MT Fund2..............
			Smoke_MTFund2Name=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND2", excelLabel.Fund_Name);
			Smoke_MTFund2Type=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND2", excelLabel.Fund_Type);
			Smoke_MTFund2InvestmentCategory=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND2", excelLabel.Fund_InvestmentCategory);
			Smoke_MTFund2RecordType=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "AAMTFUND2", excelLabel.Record_Type);



			//******************************************* Custom Objects ***********************************/

			taskCustomObj1Name=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AATASKOBJ1", excelLabel.Test_Custom_Object_Name);
			taskCustomObj1RecordType=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AATASKOBJ1", excelLabel.Record_Type);
			
			taskCustomObj2Name=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AATASKOBJ2", excelLabel.Test_Custom_Object_Name);
			taskCustomObj2RecordType=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AATASKOBJ2", excelLabel.Record_Type);
			taskCustomObj3Name=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AATASKOBJ3", excelLabel.Test_Custom_Object_Name);
			taskCustomObj3RecordType=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AATASKOBJ3", excelLabel.Record_Type);
			
			taskCustomObj2UpdatedName=taskCustomObj2Name+"UP";
			
			meetingCustomObj1Name=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AAMTOBJ1", excelLabel.Test_Custom_Object_Name);
			meetingCustomObj1RecordType=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AAMTOBJ1", excelLabel.Record_Type);
			
			meetingCustomObj2Name=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AAMTOBJ2", excelLabel.Test_Custom_Object_Name);
			meetingCustomObj2RecordType=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"AAMTOBJ2", excelLabel.Record_Type);
			
			
		

			//*********************************************Events**********************************************/
			taskUpcomingEvent1Subject=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV1", excelLabel.Subject);
			taskUpcomingEvent2Subject=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV2", excelLabel.Subject);
			taskUpcomingEvent3Subject=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV3", excelLabel.Subject);
			taskCompletedEvent1Subject=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV4", excelLabel.Subject);
			taskCompletedEvent2Subject=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV5", excelLabel.Subject);
			taskCompletedEvent3Subject=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV6", excelLabel.Subject);
			meetingUpcomingEvent1Subject=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV1", excelLabel.Subject);
			meetingUpcomingEvent2Subject=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV2", excelLabel.Subject);
			meetingUpcomingEvent3Subject=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV3", excelLabel.Subject);
			meetingCompletedEvent1Subject=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV4", excelLabel.Subject);
			meetingCompletedEvent2Subject=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV5", excelLabel.Subject);
			meetingCompletedEvent3Subject=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV6", excelLabel.Subject);
			startingTime=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV1", excelLabel.Start_Time);
			endTime=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV1", excelLabel.End_Time);
			eventMeetingType=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV1", excelLabel.Meeting_Type);

			
			
			tommorrow=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV1", excelLabel.Start_Date);
			dayAfterTommorrow=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV1", excelLabel.End_Date);
			dayBeforeYesterday=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV4", excelLabel.Start_Date);
			yesterday=ExcelUtils.readData(wb,testCasesFilePath, "Events", excelLabel.Variable_Name,"AAMTUPEV4", excelLabel.End_Date);
			
			
			//************************************************   TaSKS *************************************************//
			
			Smoke_Task1Subject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Subject);
			Smoke_Task1Date=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Due_Date);
			Smoke_Task1Status = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Status);
			Smoke_Task1MeetingType = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Meeting_Type);
			Smoke_Task1Comment=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Comment);
			Smoke_Task1Priority=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Priority);
			Smoke_Task1UpdatedPriority=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Updated_Priority);
			Smoke_Task1UpdatedSubject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask1", excelLabel.Updated_Subject);

			Smoke_LogACall1Subject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AACALL1", excelLabel.Subject);
			Smoke_LogACall1MeetingType = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AACALL1", excelLabel.Meeting_Type);
			Smoke_LogACall1Date=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AACALL1", excelLabel.Due_Date);
			Smoke_LogACall1Comment=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AACALL1", excelLabel.Comment);
			 
			Smoke_Task2Subject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Subject);
			Smoke_Task2Status = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Status);
			Smoke_Task2UpdatedSubject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Updated_Subject);
			Smoke_Task2MeetingType = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Meeting_Type);
			Smoke_Task2Comment=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Comment);
			Smoke_Task2Priority=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Priority);
			Smoke_Task2UpdatedPriority=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Updated_Priority);
			Smoke_Task2DueDate = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Due_Date);
			
			Smoke_Meeting2Subject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingTask2", excelLabel.Subject);
			Smoke_Meeting2Priority =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingTask2", excelLabel.Priority); 
			Smoke_Task3Subject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask3", excelLabel.Subject);
			Smoke_Task3Priority =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask3", excelLabel.Priority); 
			
			Smoke_STDTask1Subject=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Subject);
			Smoke_STDTask1MeetingType=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Meeting_Type);
			Smoke_STDTask1Comment=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Comment);
			Smoke_STDTask1DueDate=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Due_Date);
			Smoke_STDTask1UpdatedSubject=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Updated_Subject);
			Smoke_STDTask1UpdatedPriority=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Updated_Priority);
			Smoke_STDTask1UpdatedComment=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AASTDTask1", excelLabel.Updated_Comment);
			
			Smoke_TaskSTD1Subject=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Subject);
			Smoke_TaskSTD1MeetingType=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Meeting_Type);
			Smoke_TaskSTD1Comment=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Comment);
			Smoke_TaskSTD1DueDate=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Due_Date);
			Smoke_TaskSTD1UpdatedSubject=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Updated_Subject);
			Smoke_TaskSTD1UpdatedPriority=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Updated_Priority);
			Smoke_TaskSTD1UpdatedComment=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Updated_Comment);
			
			Smoke_BoardMeetingTaskSubject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask10", excelLabel.Subject);
			Smoke_BoardMeetingTaskMeetingType =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask10", excelLabel.Meeting_Type);
			Smoke_ClientMeetingTaskSubject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask11", excelLabel.Subject);
			Smoke_ClientMeetingTaskMeetingType =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask11", excelLabel.Meeting_Type);
			
			Smoke_Task1LogACallSubject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Subject);
			Smoke_Task1LogACallComment =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Comment);
			Smoke_Task1LogACallPriority =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Priority);
			Smoke_Task1LogACallUpdatedSubject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Updated_Subject);
			Smoke_Task1LogACallUpdatedPriority =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Updated_Priority);
			
			Smoke_Task2LogACallSubject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask13", excelLabel.Subject);
			Smoke_Task2LogACallComment =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask13", excelLabel.Comment);
			Smoke_Task2LogACallPriority =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask13", excelLabel.Priority);
			
			Smoke_TaskSTDLogACall1Subject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Subject);
			Smoke_TaskSTDLogACall1Comment =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Comment);
			Smoke_TaskSTDLogACall1Priority =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Priority);
			Smoke_TaskSTDLogACall1Status =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Status);
			Smoke_TaskSTDLogACall1UpdatedSubject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Updated_Subject);
			Smoke_TaskSTDLogACall1UpdatedPriority =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Updated_Priority);
			Smoke_TaskSTDLogACall1UpdatedComment =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Updated_Comment);
			
			Smoke_Task2MultipleSubject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Subject);
			Smoke_Task2MultiplePriority =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Priority);
			Smoke_Task2MultipleComment =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Comment);
			Smoke_Task2MultipleStatus=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Status);
			Smoke_FinalDiscussionTaskSubject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingFinalTask", excelLabel.Subject);
			Smoke_FinalDiscussionTaskMeetingType =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingFinalTask", excelLabel.Meeting_Type);
			Smoke_Task2MultipleUpdatedSubject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Updated_Subject);
			Smoke_Task2MultipleDueDate =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Due_Date);
			
			
			Smoke_TaskSendLetterSubject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask17", excelLabel.Subject);
			Smoke_TaskSendLetterPriority =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask17", excelLabel.Priority);
			Smoke_TaskSendLetterComment =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask17", excelLabel.Comment);
			Smoke_TaskSendLetterMeetingType =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask17", excelLabel.Meeting_Type);
			
			Smoke_Task2LogACallNewSubject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Subject);
			Smoke_Task2LogACallNewComment =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Comment);
			Smoke_Task20Subject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask20", excelLabel.Subject);
			Smoke_Task20Status =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask20", excelLabel.Status);
			Smoke_Task20Comment =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask20", excelLabel.Comment);
			
			Smoke_Task2LogACallNewSubject =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Subject);
			Smoke_Task2LogACallNewComment =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Comment);
			Smoke_Task2LogACallNewPriority =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Priority);
			Smoke_Task2LogACallNewDate =ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Due_Date);
			Smoke_Task2LogACallNewUpdatedSubject=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Updated_Subject);
			Smoke_Task2LogACallNewStatus=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Status);
			Smoke_Task2LogACallNewUpdatedPriority=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Updated_Priority);
			
			Smoke_DealEvalutionMeetingSubject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingTask3", excelLabel.Subject);
			Smoke_DealEvalutionMeetingType = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingTask3", excelLabel.Meeting_Type);
			Smoke_A1TaskSubject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask32", excelLabel.Subject);
			Smoke_A2TaskSubject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask33", excelLabel.Subject);
			Smoke_R1CallStatus = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask34", excelLabel.Status);
			
			Smoke_R1CallSubject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask34", excelLabel.Subject);
			Smoke_R2CallSubject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask35", excelLabel.Subject);
			Smoke_A2TaskStatus = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask33", excelLabel.Status);
			
			
			Smoke_STDTask2OnSubject=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask14", excelLabel.Subject);
			Smoke_STDTask2OnDate=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask14", excelLabel.Due_Date);
			Smoke_STDTask2OnComment=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask14", excelLabel.Comment);
			
			
			Smoke_DealClosureMeetingSubject= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask19", excelLabel.Subject);
			Smoke_DealClosureMeetingType = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask19", excelLabel.Meeting_Type);
			
			Smoke_DealProgressReviewMeetingSubject=  ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask21", excelLabel.Subject);
			
			Smoke_S1TestSubject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask22", excelLabel.Subject);
			Smoke_S1TestDueDate = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask22", excelLabel.Due_Date);
			
			
			Smoke_S2TestSubject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask23", excelLabel.Subject);
			Smoke_S2TestDueDate = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask23", excelLabel.Due_Date);
			
			
			Smoke_S3TestSubject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask24", excelLabel.Subject);
			Smoke_S3TestMeetingType = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask24", excelLabel.Meeting_Type);
			Smoke_S3TestDueDate = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask24", excelLabel.Due_Date);
			
			
			Smoke_TestListEmailSubject = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask25", excelLabel.Subject);
			Smoke_TestListEmailComment = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask25", excelLabel.Comment);
			Smoke_TestListEmailStatus = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask25", excelLabel.Status);
			Smoke_TestListEmailDate = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask25", excelLabel.Due_Date);
			
			
			Smoke_DetailPageMeetingSubject= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask26", excelLabel.Subject);
			Smoke_DetailPageMeetingType = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask26", excelLabel.Meeting_Type);
			
			Smoke_DetailPageNewTaskMultipleSubject= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask27", excelLabel.Subject);
			
			Smoke_DetailPageLogACallMultipleSubject= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask28", excelLabel.Subject);
			Smoke_DetailPageLogACallMultipleDate= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask28", excelLabel.Due_Date);
			

			Smoke_EntityMeeting1Subject=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask29", excelLabel.Subject);
			Smoke_EntityMeeting1Type=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask29", excelLabel.Meeting_Type);
			Smoke_EntityMeeting1DueDate=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask29", excelLabel.Due_Date);
			Smoke_EntityMeeting1Priority=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask29", excelLabel.Priority);
			
			Smoke_EntityMeeting2Subject=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask30", excelLabel.Subject);
			Smoke_EntityMeeting2Type=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask30", excelLabel.Meeting_Type);
			Smoke_EntityMeeting2DueDate=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask30", excelLabel.Due_Date);
			Smoke_EntityMeeting2Status=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask30", excelLabel.Status);
			Smoke_CallMeetingType=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask31", excelLabel.Meeting_Type);
			Smoke_CallSubject=ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask31", excelLabel.Subject);
			
			
		
			Smoke_AAField1DataType=ExcelUtils.readData(wb,testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field1", excelLabel.Data_Type);
			Smoke_AAField1FieldLabel=ExcelUtils.readData(wb,testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field1", excelLabel.Field_Label);
			Smoke_AAField1Length=ExcelUtils.readData(wb,testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field1", excelLabel.Length);
			
			
			Smoke_AAField2DataType=ExcelUtils.readData(wb,testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field2", excelLabel.Data_Type);
			Smoke_AAField2FieldLabel=ExcelUtils.readData(wb,testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field2", excelLabel.Field_Label);
			Smoke_AAField2Length=ExcelUtils.readData(wb,testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field2", excelLabel.Length);
			Smoke_AAField2DecimalPlaces=ExcelUtils.readData(wb,testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field2", excelLabel.Decimal_Places);
			
			Smoke_AAField3DataType=ExcelUtils.readData(wb,testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field3", excelLabel.Data_Type);
			Smoke_AAField3FieldLabel=ExcelUtils.readData(wb,testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field3", excelLabel.Field_Label);
			Smoke_AAField3Options=ExcelUtils.readData(wb,testCasesFilePath, "Fields", excelLabel.Variable_Name,"Field3", excelLabel.Options);
			
			
			Smoke_WATCHINS1Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS1", excelLabel.Institutions_Name);
			Smoke_WATCHINS1RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS1", excelLabel.Record_Type);

			Smoke_WATCHINS2Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS2", excelLabel.Institutions_Name);
			Smoke_WATCHINS2RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS2", excelLabel.Record_Type);

			
			Smoke_WATCHINS5Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS5", excelLabel.Institutions_Name);
			Smoke_WATCHINS5RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS5", excelLabel.Record_Type);

			Smoke_WATCHINS6Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS6", excelLabel.Institutions_Name);
			Smoke_WATCHINS6RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "WATCHINS6", excelLabel.Record_Type);

			Smoke_MAXDATAINS1Name=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Institutions_Name);
			Smoke_MAXDATAINS1RecordType=ExcelUtils.readData(wb,testCasesFilePath,"EntityorAccount",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Record_Type);

			Smoke_WATCHContact1FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON1", excelLabel.Contact_FirstName);
			Smoke_WATCHContact1LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON1", excelLabel.Contact_LastName);
			Smoke_WATCHContact1INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact1EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON1", excelLabel.Contact_EmailId);
			Smoke_WATCHContact1RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON1", excelLabel.Record_Type);

			Smoke_WATCHContact2FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON2", excelLabel.Contact_FirstName);
			Smoke_WATCHContact2LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON2", excelLabel.Contact_LastName);
			Smoke_WATCHContact2INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact2EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON2", excelLabel.Contact_EmailId);
			Smoke_WATCHContact2RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON2", excelLabel.Record_Type);

			Smoke_WATCHContact3FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON3", excelLabel.Contact_FirstName);
			Smoke_WATCHContact3LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON3", excelLabel.Contact_LastName);
			Smoke_WATCHContact3INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact3EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON3", excelLabel.Contact_EmailId);
			Smoke_WATCHContact3RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON3", excelLabel.Record_Type);

			Smoke_WATCHContact4FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON4", excelLabel.Contact_FirstName);
			Smoke_WATCHContact4LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON4", excelLabel.Contact_LastName);
			Smoke_WATCHContact4INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact4EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON4", excelLabel.Contact_EmailId);
			Smoke_WATCHContact4RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON4", excelLabel.Record_Type);

			Smoke_WATCHContact5FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON5", excelLabel.Contact_FirstName);
			Smoke_WATCHContact5LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON5", excelLabel.Contact_LastName);
			Smoke_WATCHContact5INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact5EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON5", excelLabel.Contact_EmailId);
			Smoke_WATCHContact5RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON5", excelLabel.Record_Type);

			Smoke_WATCHContact6FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON6", excelLabel.Contact_FirstName);
			Smoke_WATCHContact6LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON6", excelLabel.Contact_LastName);
			Smoke_WATCHContact6INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact6EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON6", excelLabel.Contact_EmailId);
			Smoke_WATCHContact6RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON6", excelLabel.Record_Type);

			Smoke_WATCHContact7FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON7", excelLabel.Contact_FirstName);
			Smoke_WATCHContact7LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON7", excelLabel.Contact_LastName);
			Smoke_WATCHContact7INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact7EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON7", excelLabel.Contact_EmailId);
			Smoke_WATCHContact7RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON7", excelLabel.Record_Type);

			Smoke_WATCHContact8FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON8", excelLabel.Contact_FirstName);
			Smoke_WATCHContact8LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON8", excelLabel.Contact_LastName);
			Smoke_WATCHContact8INSName=Smoke_WATCHINS1Name;
			Smoke_WATCHContact8EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON8", excelLabel.Contact_EmailId);
			Smoke_WATCHContact8RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "WATCHCON8", excelLabel.Record_Type);

			Smoke_MAXDATAContact1FName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Contact_FirstName);
			Smoke_MAXDATAContact1LName=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Contact_LastName);
			Smoke_MAXDATAContact1INSName=Smoke_MAXDATAINS1Name;
			Smoke_MAXDATAContact1EmailID=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Contact_EmailId);
			Smoke_MAXDATAContact1RecordType=ExcelUtils.readData(wb,testCasesFilePath,"Contacts",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Record_Type);

			Smoke_MAXDATAFund1Name=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Fund_Name);
			Smoke_MAXDATAFund1Type=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Fund_Type);
			Smoke_MAXDATAFund1InvestmentCategory=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Fund_InvestmentCategory);
			Smoke_MAXDATAFund1RecordType=ExcelUtils.readData(wb,testCasesFilePath,"FundorDeal",excelLabel.Variable_Name, "MAXDATA1", excelLabel.Record_Type);

			
			MAXDATACustomObj1Name=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"MAXDATA1", excelLabel.Test_Custom_Object_Name);
			MAXDATACustomObj1RecordType=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"MAXDATA1", excelLabel.Record_Type);
		
			WATCHCustomObj1Name=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"WATCH1", excelLabel.Test_Custom_Object_Name);
			WATCHCustomObj1RecordType=ExcelUtils.readData(wb,testCasesFilePath, "Test Custom Object", excelLabel.Variable_Name,"WATCH1", excelLabel.Record_Type);
		
			
			Smoke_STTaskFromContactSubject= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask36", excelLabel.Subject);
			Smoke_STTaskFromContactMeetingType = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask36", excelLabel.Meeting_Type);
			Smoke_STTaskFromContactStatus= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask36", excelLabel.Status);
			Smoke_STTaskFromContactDueDate = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask36", excelLabel.Due_Date);
			
			Smoke_MTGTaskFromContactSubject= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask37", excelLabel.Subject);
			Smoke_MTGTaskFromContactMeetingType = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask37", excelLabel.Meeting_Type);
			Smoke_MTGTaskFromContactStatus= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask37", excelLabel.Status);
			Smoke_MTGTaskFromContactDueDate = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask37", excelLabel.Due_Date);
			
			Smoke_LAC1Subject= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask38", excelLabel.Subject);
			Smoke_LAC1DueDate = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask38", excelLabel.Due_Date);
			
			
			Smoke_TaskMTA1Subject= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask39", excelLabel.Subject);
			Smoke_TaskMTA1MeetingType = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask39", excelLabel.Meeting_Type);
			Smoke_TaskMTA1DueDate = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask39", excelLabel.Due_Date);
		
			 CreatedMeetingTypeOption = "Test Meeting Type Activity Association";
			
			Smoke_MTTest1Subject= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask40", excelLabel.Subject);
			Smoke_MTTest1MeetingType = CreatedMeetingTypeOption;
			Smoke_MTTest1Status= ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask40", excelLabel.Status);
			Smoke_MTTest1DueDate = ExcelUtils.readData(wb,testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask40", excelLabel.Due_Date);
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				wb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			AppListeners.appLog.info("Done with intialization in Smoke Test Variable. Enjoy the show.\nTotal Time Taken: "+((System.currentTimeMillis()-StartTime)/1000)+" seconds.");
			
		//}
			break;
			
			default :
				AppListeners.appLog.info("Done with intialization in Smoke Test Variable. Enjoy the show.\nTotal Time Taken: "+((System.currentTimeMillis()-StartTime)/1000)+" seconds.");
				break;
			}

	}
		

	
}
