/**
 * 
 */
package com.navatar.generic;

import static com.navatar.generic.BaseLib.testCasesFilePath;
import static com.navatar.generic.BaseLib.phase1DataSheetFilePath;
import static com.navatar.generic.CommonLib.getDateAccToTimeZone;
import static com.navatar.generic.CommonLib.previousOrForwardDate;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.scripts.Module5New;
import com.navatar.scripts.Module8;
import com.navatar.scripts.PECloudSmoke;
import com.navatar.scripts.PECloudSmoke2;
import com.navatar.scripts.Module6;
import com.navatar.scripts.Module7;
import com.navatar.scripts.Module1;
import com.navatar.scripts.Module2;
import com.navatar.scripts.Module3New;
import com.navatar.scripts.Module4;


/**
 * @author Ankur Rana
 * 	
 */
public class CommonVariables {
//	public static String abc;

	public static double dealReceivedScore=1.0,NDASignedScore=1.0,managementMeetingScore=3.0,ioiScore=3.0,loiScore=5.0;
 	public static double dueDiligenceScore=5.0,closedScore=5.0,declinedDeadScore=5.0,parkedScore=5.0;
 	
	public static String URL,todaysDate,todaysDate1,tomorrowsDate,todaysDateEurope,todaysDateddmm,todaysDateSingleDigit,todaysDateNewZealand,yesterdaysDate;
	public static String browserToLaunch;
	public static String Smoke_TWINS1Name,Smoke_TWINS1RecordType,Smoke_TWINS1Status;
	public static String Smoke_CDINS1Name,Smoke_CDINS1RecordType,Smoke_CDINS1Status;
	public static String Smoke_TWINS2Name,Smoke_TWINS2RecordType,Smoke_TWINS2Status;
	public static String Smoke_CDINS2Name,Smoke_CDINS2RecordType,Smoke_CDINS2Status;
	public static String Smoke_CDINS3Name,Smoke_CDINS3RecordType,Smoke_CDINS3Status;
	public static String Smoke_TWINS3Name,Smoke_TWINS3RecordType,Smoke_TWINS3Status;
	public static String Smoke_TWINS4Name,Smoke_TWINS4RecordType,Smoke_TWINS4Status;
	public static String Smoke_TWINS5Name,Smoke_TWINS5RecordType,Smoke_TWINS5Status;
	public static String Smoke_HSRINS1Name,Smoke_HSRINS1RecordType,Smoke_HSRDINS1Status;
	public static String Smoke_HSRINS2Name,Smoke_HSRINS2RecordType,Smoke_HSRDINS2Status;
	public static String Smoke_HSRINS3Name,Smoke_HSRINS3RecordType,Smoke_HSRDINS3Status;
	public static String Smoke_HSRINS4Name,Smoke_HSRINS4RecordType,Smoke_HSRDINS4Status;
	public static String Smoke_HSRINS5Name,Smoke_HSRINS5RecordType,Smoke_HSRDINS5Status;
	public static double averageDealQualityScore,dealQualityScore;
	public static int totalDealsshown;
	public static String Smoke_TWContact1FName,Smoke_TWContact1LName,Smoke_TWContact1EmailID,Smoke_TWContact1RecordType;
	public static String Smoke_TWContact2FName,Smoke_TWContact2LName,Smoke_TWContact2EmailID,Smoke_TWContact2RecordType;
	public static String Smoke_TWContact3FName,Smoke_TWContact3LName,Smoke_TWContact3EmailID,Smoke_TWContact3RecordType;
	public static String Smoke_TWContact4FName,Smoke_TWContact4LName,Smoke_TWContact4EmailID,Smoke_TaskContact4UpdatedName,Smoke_TWContact4RecordType;
	public static String Smoke_TWContact5FName,Smoke_TWContact5LName,Smoke_TWContact5EmailID,Smoke_TWContact5RecordType;

	public static String appName,AppDeveloperName,AppDescription;

	public static String Smoke_HSRPipeline1Name,Smoke_HSRPipeline1Stage;
	public static String Smoke_HSRPipeline2Name,Smoke_HSRPipeline2Stage;
	public static String Smoke_HSRPipeline3Name,Smoke_HSRPipeline3Stage;
	public static String Smoke_HSRPipeline4Name,Smoke_HSRPipeline4Stage;
	public static String Smoke_HSRPipeline5Name,Smoke_HSRPipeline5Stage;
	public static String Smoke_HSRPipeline6Name,Smoke_HSRPipeline6Stage;
	
	public static String Smoke_HSRContact1FName,Smoke_HSRContact1LName,Smoke_HSRContact1EmailID;
	public static String Smoke_HSRContact2FName,Smoke_HSRContact2LName,Smoke_HSRContact2EmailID;
	public static String Smoke_HSRContact3FName,Smoke_HSRContact3LName,Smoke_HSRContact3EmailID;
	public static String superAdminUserName,superAdminRegistered,adminPassword;
	public static String AdminUserFirstName,AdminUserLastName,AdminUserEmailID;
	public static String crmUser1FirstName,crmUser1LastName,crmUser1EmailID,crmUserProfile,crmUserLience;
	public static String crmUser2FirstName,crmUser2LastName,crmUser2EmailID;
	public static String gmailUserName,gmailUserName2,gmailPassword;
	
	public static String tabCustomObj,tabCustomObjField,tabCustomObjAPIName;
	public static String tabObj1,tabObj2,tabObj3,tabObj4,tabObj5,tabObj6,tabObj7,tabObj8Coverage;
	
	public static String ToggleLP1;
	public static String TogglePartnerShip1;
	public static String TogglePartnerShip2;
	public static String M4Ins1,M4Ins1RecordType,M4Deal1;
	public static String M4Contact1FName,M4Contact1LName,M4Contact1EmailID,M4Contact1RecordType,M4Contact1Title;
	public static String M4Contact2FName,M4Contact2LName,M4Contact2EmailID,M4Contact2Title,M4Contact2Email;
	public static String M4Contact8FName,M4Contact8LName,M4Contact8Title;
	
	public static String environment,mode;
	public static String TWTask1Subject,TWTask2Subject,TWTask3Subject,TWTask4Subject,TWTask5Subject,TWTask6Subject,TWTask8Subject,TWTaskCR1Subject,TWTaskUpdateLabelSubject;
	public static String ToggleFund1,ToggleFund1Type,ToggleFund1Category,ToggleFund1RecordType;
	public static String ToggleFund2,ToggleFund2Type,ToggleFund2Category,ToggleFund2RecordType;
	public static String ToggleOpenQA1ID,ToggleOpenQA1RequestedDate,ToggleOpenQA1Request,ToggleOpenQA1Status;
	public static String ToggleClosedQA1ID,ToggleClosedQA1RequestedDate,ToggleClosedQA1Request,ToggleClosedQA1Status;
	
	public static String M4MarketingEvent1Name,M4MarketingEvent1Date,M4MarketingEvent1RecordType,M4MarketingEvent1Organizer;
	public static String M4MarketingEvent2Name,M4MarketingEvent2Date,M4MarketingEvent2RecordType;
	public static String M4MarketingEvent3Name,M4MarketingEvent3Date,M4MarketingEvent3RecordType;
	public static String M4MarketingEvent4Name,M4MarketingEvent4Date,M4MarketingEvent4RecordType;
	public static String M4MarketingEvent5Name,M4MarketingEvent5Date,M4MarketingEvent5RecordType;
	public static String M4MarketingEvent6Name,M4MarketingEvent6Date,M4MarketingEvent6RecordType;
	public static String M4MarketingEvent11Name,M4MarketingEvent12Name,M4MarketingEvent13Name;
	public static String M4MarketingEvent14Name,M4MarketingEvent15Name,M4MarketingEvent16Name;
	public static String M4MarketingEvent17Name,M4MarketingEvent18Name,M4MarketingEvent19Name;
	public static String M4MarketingEvent20Name,M4MarketingEvent20RecordType,M4MarketingEvent20Organizer;
	public static String M4MarketingEvent21Name,M4MarketingEvent21RecordType,M4MarketingEvent21Organizer;
	public static String M4SDGAction1Name,M4SDGAction1Order,M4SDGAction1Type,M4SDGAction1Event,M4SDGAction1EventPayload;
	public static String M4SDGAction2Name,M4SDGAction2Order,M4SDGAction2Type,M4SDGAction2Event,M4SDGAction2EventPayload;
	
	public static String M4Attendee10Status,M4DTField2;
	public static String TechonlogyCoverage,TechonlogyCoverageRecordType;
	public static String FS_MarketingEvent1Name,FS_MarketingEvent1Date,FS_MarketingEvent1RecordType,FS_MarketingEvent1Organizer;
	

	public static String FS_Object1,FS_Object2,FS_Object3,FS_Object4,FS_Object5,FS_FieldSetLabel1,FS_FieldSetLabel2,FS_FieldSetLabel3,FS_FieldSetLabel4,FS_FieldSetLabel5,
						FS_NameSpacePrefix1,FS_NameSpacePrefix2,FS_NameSpacePrefix3,FS_NameSpacePrefix4,FS_NameSpacePrefix5,FS_FieldsName1,FS_FieldsName2,FS_FieldsName3,FS_FieldsName4,FS_FieldsName5,FS_ExtraFieldsName1;
	
	public static String FS_Ins1,FS_Ins1RecordType,FS_Ins2,FS_Ins2RecordType,FS_LP1,FS_LP1RecordType;
	public static String FS_Con1_FName,FS_Con1_LName,FS_Con1_Email,FS_Con1_Phone,FS_Con1_RecordType;
	public static String FS_DealName1,FS_Deal1CompanyName,FS_Deal1Stage,FS_Deal1SourceContact,FS_Deal1SourceFirm,FS_Deal1RecordType;
	public static String FS_DealName2,FS_Deal2CompanyName,FS_Deal2Stage;
	public static String FS_DealName3,FS_Deal3CompanyName,FS_Deal3Stage;
	
	public static String FC_Object1,FC_FieldLabelName1,FC_Length1,FC_FieldType1,FC_FieldLabel1SubString;
	
	public static String FC_Object2,FC_FieldLabelName2,FC_Length2,FC_FieldType2;
	
	public static String FC_Object3,FC_FieldLabelName3,FC_Length3,FC_FieldType3;
	
	public static String FC_Object4,FC_FieldLabelName4,FC_Length4,FC_FieldType4;
	
	public static String FC_Object5,FC_FieldLabelName5,FC_Length5,FC_FieldType5;
	
	public static String FC_Object6,FC_FieldLabelName6,FC_Length6,FC_FieldType6;
	
	public static String FC_Object7,FC_FieldLabelName7,FC_Length7,FC_FieldType7;
	
	public static String FC_Object8,FC_FieldLabelName8,FC_Length8,FC_FieldType8;
	
	public static String FC_Object9,FC_FieldLabelName9,FC_Length9,FC_FieldType9;
	
	public static String FC_Object10,FC_FieldLabelName10,FC_Length10,FC_FieldType10;
	
	public static String FC_Object11,FC_FieldLabelName11,FC_Length11,FC_FieldType11;
	
	public static String FC_Object12,FC_FieldLabelName12,FC_Length12,FC_FieldType12;
	
	public static String FC_Object13,FC_FieldLabelName13,FC_Length13,FC_FieldType13;
	
	public static String FC_Object14,FC_FieldLabelName14,FC_Length14,FC_FieldType14;
	
	public static String FC_Object15,FC_FieldLabelName15,FC_Length15,FC_FieldType15;
	
	public static String FC_Object16,FC_FieldLabelName16,FC_Length16,FC_FieldType16;
	
	public static String FC_Object17,FC_FieldLabelName17,FC_Length17,FC_FieldType17;
	
	public static String FC_Object18,FC_FieldLabelName18,FC_Length18,FC_FieldType18;
	
	public static String FC_Object19,FC_FieldLabelName19,FC_Length19,FC_FieldType19;
	
	public static String FC_Object20,FC_FieldLabelName20,FC_Length20,FC_FieldType20;
	
	public static String FC_Object21,FC_FieldLabelName21,FC_Length21,FC_FieldType21;
	
	public static String FC_Object22,FC_FieldLabelName22,FC_Length22,FC_FieldType22;
	
	public static String FC_Object23,FC_FieldLabelName23,FC_Length23,FC_FieldType23;
	
	public static String FC_Object24,FC_FieldLabelName24,FC_Length24,FC_FieldType24;
	
	public static String FC_Object25,FC_FieldLabelName25,FC_Length25,FC_FieldType25;
	
	public static String FC_Object26,FC_FieldLabelName26,FC_Length26,FC_FieldType26;
	
	public static String FC_Object27,FC_FieldLabelName27,FC_Length27,FC_FieldType27;
	
	public static String FC_Object28,FC_FieldLabelName28,FC_Length28,FC_FieldType28;
	
	public static String FC_Object29,FC_FieldLabelName29,FC_Length29,FC_FieldType29;
	
	public static String FC_Object30,FC_FieldLabelName30,FC_Length30,FC_FieldType30;
	
	public static String FC_Object31,FC_FieldLabelName31,FC_Length31,FC_FieldType31;
	
	public static String FC_Object32,FC_FieldLabelName32,FC_FieldType32;
	public static String FC_Object33,FC_FieldLabelName33,FC_FieldType33;
	public static String FC_Object34,FC_FieldLabelName34,FC_FieldType34;
	

	public static String FS_Fund1,FS_Fund1RecordType,FS_Fund1Type,FS_Fund1InvestmentCategory,FS_Fund2,FS_Fund2RecordType,FS_Fund2Type,FS_Fund2InvestmentCategory;
	
	public static String FS_PartnerShip1,FS_PartnerShip2;
	
	public static String FS_CommitmentID1,FS_CommitmentAmount1,FS_FinalCommitmentDate1,FS_CommitmentID2,FS_CommitmentAmount2,FS_FinalCommitmentDate2;
	
	
	public static String SDG;
	
	public static String Sdg1Name,Sdg1TagName,Sdg1ObjectName;
	public static String M4Sdg1Name,M4Sdg1TagName,M4Sdg1ObjectName,M4Sdg1ParentName;
	public static String M4Sdg2Name,M4Sdg2TagName,M4Sdg2ObjectName,M4Sdg2ParentName;
	public static String M4Sdg3Name,M4Sdg3TagName,M4Sdg3ObjectName,M4Sdg3ParentName;
	
	public static String ActiveDealToggleButton;
	
	public static String ToggleCheck1TabName,ToggleCheck1ItemName,ToggleCheck1RelatedTab,ToggleCheck1ToggleButtons,ToggleCheck1ColumnName;
	public static String ToggleCheck2TabName,ToggleCheck2ItemName,ToggleCheck2RelatedTab,ToggleCheck2ToggleButtons,ToggleCheck2ColumnName;
	public static String ToggleCheck3TabName,ToggleCheck3ItemName,ToggleCheck3RelatedTab,ToggleCheck3ToggleButtons,ToggleCheck3ColumnName;
		
	public static String customObject="CustomObject:";
	public static String watchListSDG="Origination_Watchlist_AllWatchlist_Baseline";
	public static String watchListTitle="Watchlist";
	public static String dealTitle="Deals";
	
	
	/// New MOdule 3 Variable :
	

	public static String M3Fund1,M3Fund1Type,M3Fund1Category,M3Fund1RecordType;
	
	public static String SmokeReportFolderName,SmokeReportName,SmokeReportType,SmokeReportShow,SmokeReportRange;
	public static String SmokeReport2FolderName,SmokeReport2Name,SmokeReport2Type,SmokeReport2Show,SmokeReport2Range;

	
	public static String EmailTemplate1_Subject,EmailTemplate1_Body,EmailTemplate1_FolderName,EmailTemplate1_TemplateName,EmailTemplate1_TemplateDescription;

	public static String M3Task1Subject,M3Task1dueDate,M3Task1Status,M3Task1Priority;
	public static String M3Call1Subject,M3Cal11dueDate,M3Call1Status,M3Cal11Priority;
	public static String M3Meeting1Subject,M3Meeting1dueDate,M3Meeting1Status,M3Meeting1Priority,M3Meeting1MeetingType;
	
	public static String M3Deal1,M3Deal1CompanyName,M3Deal1RecordType,M3Deal1Stage;
	
	public static String M3Ins1,M3Ins1RecordType;
	public static String M3Ins2,M3Ins2RecordType;
	public static String M3Ins3,M3Ins3RecordType;
	public static String M3Ins4,M3Ins4RecordType;
	public static String M3Ins5,M3Ins5RecordType;
	public static String M3Ins6,M3Ins6RecordType,M3Ins6Parent;
	public static String M3Ins7,M3Ins7RecordType;
	public static String M3Ins8,M3Ins8RecordType,M3Ins8Parent;
	public static String M3Ins9,M3Ins9RecordType,M3Ins9Parent;
	public static String M3Ins10,M3Ins10RecordType;
	public static String M3Contact2FName,M3Contact2LName,M3Contact2EmailID,M3Contact2RecordType,M3Contact2Title;
	public static String M3Contact1FName,M3Contact1LName,M3Contact1EmailID,M3Contact1RecordType;
	public static String M3Contact3FName,M3Contact3LName,M3Contact3EmailID,M3Contact3RecordType;
	public static String M3Contact4FName,M3Contact4LName;
	public static String M3PartnerShip1;
	public static String M3FRName1;
	

	public static String M3TestCustomObj1Name,M3TestCustomObj1RecordType;
	
	// New Module 5 Variable
	public static String ToggleIns1,ToggleIns1RecordType;
	public static String ToggleContact1FName,ToggleContact1LName,ToggleContact1Inst,ToggleContact1EmailID,ToggleContact1RecordType;
	
	public static String ToggleDeal1,ToggleDeal1CompanyName,ToggleDeal1RecordType,ToggleDeal1Stage;
	public static String ToggleMarketingEvent1Name,ToggleMarketingEvent1Date,ToggleMarketingEvent1RecordType,ToggleMarketingEvent1Organizer;
	
	public static String M5Sdg1Name,M5Sdg1TagName,M5Sdg1ObjectName,M5Sdg1ParentName;
	public static String M5Sdg2Name,M5Sdg2TagName,M5Sdg2ObjectName,M5Sdg2ParentName;
	public static String M5Sdg3Name,M5Sdg3TagName,M5Sdg3ObjectName,M5Sdg3ParentName;
	public static String M5Sdg4Name,M5Sdg4TagName,M5Sdg4ObjectName,M5Sdg4ParentName;
	public static String M5Sdg5Name,M5Sdg5TagName,M5Sdg5ObjectName,M5Sdg5ParentName;
	
	
	// Module 6 Variable
	public static String M6Ins1,M6Ins2,M6Ins3,M6Ins6,M6Ins9,M6Ins10,M6Ins11,M6Ins12,M6Ins13,M6Ins14,M6Ins15,M6Ins16,M6Ins17,M6Ins18,M6Ins20,M6Ins21,M6Ins22,M6Ins1RecordType;
	public static String M6Deal1,M6Deal2,M6Deal3,M6Deal4,M6Deal5,M6Deal6,M6Deal7,M6Deal8,M6Deal9,M6Deal10,M6Deal8Stage,M6Deal7Stage,M6Deal11,M6Deal12,M6Deal13,M6Deal14,M6Deal15,M6Deal16,M6Deal17,M6Deal18,M6Deal20,M6Deal21,M6Deal22,M6Deal20Stage;
	public static String M6LSCIns1,M6LSCFund1,M6LSCFundraising1;
	// Module 8 variable :
	
	public static String M8DealName1,M8DealName2;
	public static String M8FRName1,M8FRName2;
	public static String M8CON1FName,M8CON1LName,M8CON1EmailID;
	public static String M8CON2FName,M8CON2LName,M8CON2EmailID;
	public static String M8CreateDealButtonName,M8DealEvent,M8DealActionOrder,M8DealActionType,M8DealEventPayLoad;
	
	public static String M8CreateFundRaisingButtonName,M8FundRaisingEvent,M8FundRaisingActionOrder,M8FundRaisingActionType,M8FundRaisingEventPayLoad;
	
	public static String M8CreateContactButtonName,M8ContactEvent,M8ContactActionOrder,M8ContactActionType,M8ContactEventPayLoad;
	
	public static String M8CallSubject;
	public static String M8SendLetterSubject;
	public static String M8SendQuoteSubject;
	public static String M8OtherSubject;
	public static String M8Einstein5Subject;
	public static String M8Einstein6Subject;
	public static String M8Einstein7Subject;
	public static String M8Einstein8Subject;
	public static String M8Einstein9Subject;
	public static String M8Einstein10Subject;
	public static String M8Einstein11Subject;
	public static String M8Einstein12Subject;
	public static String M8Einstein13Subject;
	public static String M8Einstein14Subject;
	public static String M8EinsteinEvent10Subject;
	// Module 7 
	
	public static String M7Ins1,M7Ins1RecordType;
	
	public static String M7Contact1FName,M7Contact1LName,M7Contact1EmailID,M7Contact1RecordType;
	public static String M7Contact2FName,M7Contact2LName,M7Contact2EmailID,M7Contact2RecordType;
	public static String M7Contact3FName,M7Contact3LName,M7Contact3EmailID,M7Contact3RecordType;
	public static String M7Contact4FName,M7Contact4LName,M7Contact4EmailID,M7Contact4RecordType;
	public static String M7Contact5FName,M7Contact5LName,M7Contact5EmailID,M7Contact5RecordType;
	public static String M7Contact6FName,M7Contact6LName,M7Contact6EmailID,M7Contact6RecordType;
	public static String M7Contact7FName,M7Contact7LName,M7Contact7EmailID,M7Contact7RecordType;
	public static String M7Contact8FName,M7Contact8LName,M7Contact8EmailID,M7Contact8RecordType;
	public static String M7Contact9FName,M7Contact9LName,M7Contact9EmailID,M7Contact9RecordType;
	public static String M7Contact10FName,M7Contact10LName,M7Contact10EmailID,M7Contact10RecordType;
	public static String M7Contact11FName,M7Contact11LName,M7Contact11EmailID,M7Contact11RecordType;
	public static String M7Contact12FName,M7Contact12LName,M7Contact12EmailID,M7Contact12RecordType;
	public static String M7Contact13FName,M7Contact13LName,M7Contact13EmailID,M7Contact13RecordType;
	public static String M7Contact14FName,M7Contact14LName,M7Contact14EmailID,M7Contact14RecordType;
	public static String M7Contact15FName,M7Contact15LName,M7Contact15EmailID,M7Contact15RecordType;
	
	
	public static String M7Task1Subject,M7Task1dueDate,M7Task1Status,M7Task1Priority,M7Task1MeetingType;
	public static String M7Task2Subject,M7Task2dueDate,M7Task2Status,M7Task2Priority,M7Task2MeetingType;
	public static String M7Task3Subject,M7Task3dueDate,M7Task3Status,M7Task3Priority,M7Task3MeetingType;
	public static String M7Task4Subject,M7Task4dueDate,M7Task4Status,M7Task4Priority,M7Task4MeetingType;
	public static String M7Task5Subject,M7Task5dueDate,M7Task5Status,M7Task5Priority,M7Task5MeetingType;
	public static String M7Task6Subject,M7Task6dueDate,M7Task6Status,M7Task6Priority,M7Task6MeetingType;
	public static String M7Task7Subject,M7Task7dueDate,M7Task7Status,M7Task7Priority,M7Task7MeetingType;
	public static String M7Task8Subject,M7Task8dueDate,M7Task8Status,M7Task8Priority,M7Task8MeetingType;
	public static String M7Task9Subject,M7Task9dueDate,M7Task9Status,M7Task9Priority,M7Task9MeetingType;
	public static String M7Task10Subject,M7Task10dueDate,M7Task10Status,M7Task10Priority,M7Task10MeetingType;
	public static String M7Task11Subject,M7Task11dueDate,M7Task11Status,M7Task11Priority,M7Task11MeetingType;
	public static String M7Task12Subject,M7Task12dueDate,M7Task12Status,M7Task12Priority,M7Task12MeetingType;
	public static String M7Task13Subject,M7Task13dueDate,M7Task13Status,M7Task13Priority,M7Task13MeetingType;
	public static String M7Task14Subject,M7Task14dueDate,M7Task14Status,M7Task14Priority,M7Task14MeetingType;
	public static String M7Task15Subject,M7Task15dueDate,M7Task15Status,M7Task15Priority,M7Task15MeetingType;
	public static String M7Task16Subject,M7Task16dueDate,M7Task16Status,M7Task16Priority,M7Task16MeetingType;
	public static String M7Task17Subject,M7Task17dueDate,M7Task17Status,M7Task17Priority,M7Task17MeetingType;
	public static String M7Task18Subject,M7Task18dueDate,M7Task18Status,M7Task18Priority,M7Task18MeetingType;
	

	public static String M7Event1Subject,M7Event1StartDate,M7Event1StartTime,M7Event1EndDate,M7Event1EndTime;
	public static String M7Event2Subject,M7Event2StartDate,M7Event2StartTime,M7Event2EndDate,M7Event2EndTime;
	public static String M7Event3Subject,M7Event3StartDate,M7Event3StartTime,M7Event3EndDate,M7Event3EndTime;
	public static String M7Event4Subject,M7Event4StartDate,M7Event4StartTime,M7Event4EndDate,M7Event4EndTime;
	public static String M7Event5Subject,M7Event5StartDate,M7Event5StartTime,M7Event5EndDate,M7Event5EndTime;
	public static String M7Event6Subject,M7Event6StartDate,M7Event6StartTime,M7Event6EndDate,M7Event6EndTime;
	public static String M7Event7Subject,M7Event7StartDate,M7Event7StartTime,M7Event7EndDate,M7Event7EndTime;
	
	
	//Module 8 
	public static String M8_Object1,M8_Object2,M8_Object3,M8_Object4,M8_Object5,M8_Object6;
	
	public static String M8_Object1FieldName,M8_Object2FieldName,M8_Object3FieldName,M8_Object4FieldName,M8_Object5FieldName,M8_Object6FieldName;
	
	
	// PE CLoud Smoke variable
	
	//ins
	public static String SMOKIns1InsName,SMOKIns1RecordType,SMOKIns1Phone;
	public static String SMOKIns2InsName,SMOKIns2RecordType,SMOKIns2Phone;
	public static String SMOKIns3InsName,SMOKIns3RecordType,SMOKIns3Phone;
	public static String SMOKIns4InsName,SMOKIns4RecordType,SMOKIns4Phone;
	public static String SMOKIns5InsName,SMOKIns5RecordType,SMOKIns5Phone;
	public static String SMOKIns6InsName,SMOKIns6RecordType,SMOKIns6Phone;
	public static String SMOKIns7InsName,SMOKIns7RecordType,SMOKIns7Phone;
	public static String SMOKIns8InsName,SMOKIns8RecordType,SMOKIns8Phone;
	public static String SMOKIns9InsName,SMOKIns9RecordType,SMOKIns9Phone;
	public static String SMOKIns10InsName,SMOKIns10RecordType,SMOKIns10Phone;
	public static String SMOKIns11InsName,SMOKIns11RecordType,SMOKIns11Phone,SMOKIns11ParentInstitution,SMOKIns11Status,SMOKIns11EntityType;
	public static String SMOKIns12InsName,SMOKIns12RecordType,SMOKIns12Phone,SMOKIns12ParentInstitution,SMOKIns12Status,SMOKIns12EntityType;
	public static String SMOKIns13InsName,SMOKIns13RecordType,SMOKIns13Phone,SMOKIns13ParentInstitution,SMOKIns13Status,SMOKIns13EntityType;
	public static String SMOKIns14InsName,SMOKIns14RecordType,SMOKIns14Phone,SMOKIns14ParentInstitution,SMOKIns14Status,SMOKIns14EntityType;
	public static String SMOKIns15InsName,SMOKIns15RecordType,SMOKIns15Phone,SMOKIns15ParentInstitution,SMOKIns15Status,SMOKIns15EntityType;
	public static String SMOKIns16InsName,SMOKIns16RecordType,SMOKIns16Phone,SMOKIns16ParentInstitution,SMOKIns16Status,SMOKIns16EntityType;
	public static String SMOKIns17InsName,SMOKIns17RecordType,SMOKIns17Phone,SMOKIns17ParentInstitution,SMOKIns17Status,SMOKIns17EntityType;
	public static String SMOKIns18InsName,SMOKIns18RecordType,SMOKIns18Phone,SMOKIns18ParentInstitution,SMOKIns18Status,SMOKIns18EntityType;
	public static String SMOKIns19InsName,SMOKIns19RecordType,SMOKIns19Phone,SMOKIns19ParentInstitution,SMOKIns19Status,SMOKIns19EntityType;
	public static String SMOKIns20InsName,SMOKIns20RecordType,SMOKIns20Phone,SMOKIns20ParentInstitution,SMOKIns20Status,SMOKIns20EntityType;
	
	public static String SmokeCTIns,SmokeCTInsRecordType;
	public static String SmokeCTIns1,SmokeCTIns1RecordType;
	public static String SmokeCTIns2,SmokeCTIns2RecordType;
	public static String SmokeCTContact1FName,SmokeCTContact1LName,SmokeCTContact1Inst,SmokeCTContact1EmailID,SmokeCTContact1RecordType;
	public static String SmokeCTTask1Subject,SmokeCTTask1dueDate,SmokeCTTask1Status,SmokeCTTask1Priority,SmokeCTTask1MeetingType;
	public static String SmokeCTLogACall1Subject,SmokeCTLogACall1dueDate,SmokeCTLogACall1Status,SmokeCTLogACall1Priority,SmokeCTLogACall1MeetingType,SmokeCTLogACall1Comment;
	public static String SmokeCTEvent1Subject,SmokeCTEvent1StartDate,SmokeCTEvent1StartTime,SmokeCTEvent1EndDate,SmokeCTEvent1EndTime,SmokeCTEvent1Location;
	
	
	//contactt
	public static String SMOKCon1FirstName,SMOKCon1LastName,SMOKCon1InstitutionName,SMOKCon1ContactEmail,SMOKCon1Phone,SMOKCon1Tier;
	public static String SMOKCon2FirstName,SMOKCon2LastName,SMOKCon2InstitutionName,SMOKCon2ContactEmail,SMOKCon2Phone,SMOKCon2Tier;
	public static String SMOKCon3FirstName,SMOKCon3LastName,SMOKCon3InstitutionName,SMOKCon3ContactEmail,SMOKCon3Phone,SMOKCon3Tier;
	public static String SMOKCon4FirstName,SMOKCon4LastName,SMOKCon4InstitutionName,SMOKCon4ContactEmail,SMOKCon4Phone,SMOKCon4Tier;
	public static String SMOKCon5FirstName,SMOKCon5LastName,SMOKCon5InstitutionName,SMOKCon5ContactEmail,SMOKCon5Phone,SMOKCon5Tier;
	public static String SMOKCon6FirstName,SMOKCon6LastName,SMOKCon6InstitutionName,SMOKCon6ContactEmail,SMOKCon6Phone,SMOKCon6Tier;
	public static String SMOKCon7FirstName,SMOKCon7LastName,SMOKCon7InstitutionName,SMOKCon7ContactEmail,SMOKCon7Phone,SMOKCon7Tier;
	public static String SMOKCon8FirstName,SMOKCon8LastName,SMOKCon8InstitutionName,SMOKCon8ContactEmail,SMOKCon8Phone,SMOKCon8Tier;
	public static String SMOKCon9FirstName,SMOKCon9LastName,SMOKCon9InstitutionName,SMOKCon9ContactEmail,SMOKCon9Phone,SMOKCon9Tier;

	
	//deal
	public static String SMOKDeal1DealName,SMOKDeal1CompanyName,SMOKDeal1Stage,SMOKDeal1SourceContact,SMOKDeal1SourceFirm,SMOKDeal1PipelineComments;
	public static String SMOKDeal2DealName,SMOKDeal2CompanyName,SMOKDeal2Stage,SMOKDeal2SourceContact,SMOKDeal2SourceFirm,SMOKDeal2LogInDate;
	public static String SMOKDeal3DealName,SMOKDeal3CompanyName,SMOKDeal3Stage,SMOKDeal3SourceContact,SMOKDeal3SourceFirm,SMOKDeal3LogInDate;
	public static String SMOKDeal4DealName,SMOKDeal4CompanyName,SMOKDeal4Stage,SMOKDeal4SourceContact,SMOKDeal4SourceFirm,SMOKDeal4LogInDate;
	public static String SMOKDeal5DealName,SMOKDeal5CompanyName,SMOKDeal5Stage,SMOKDeal5SourceContact,SMOKDeal5SourceFirm,SMOKDeal5LogInDate;
	public static String SMOKDeal6DealName,SMOKDeal6CompanyName,SMOKDeal6Stage,SMOKDeal6SourceContact,SMOKDeal6SourceFirm,SMOKDeal6LogInDate;
	public static String SMOKDeal7DealName,SMOKDeal7CompanyName,SMOKDeal7Stage,SMOKDeal7SourceContact,SMOKDeal7SourceFirm,SMOKDeal7LogInDate;
	public static String SMOKDeal8DealName,SMOKDeal8CompanyName,SMOKDeal8Stage,SMOKDeal8SourceContact,SMOKDeal8SourceFirm,SMOKDeal8LogInDate;

	
	//fund
	public static String SMOKFund1FundName,SMOKFund1FundType,SMOKFund1InvestmentCategory,SMOKFund1VintageYear,SMOKFund11stClosingDate;
	public static String SMOKFund2FundName,SMOKFund2FundType,SMOKFund2InvestmentCategory,SMOKFund2VintageYear;
	public static String SmokeFund3,SmokeFund3Type,SmokeFund3Category,SmokeFund3RecordType;

	
	//FR
	public static String SMOKFR1FundraisingName,SMOKFR1InstitutionName,SMOKFR1FundName,SMOKFR1Satge,SMOKFR1Closing,SMOKFR1InvestmentLikelyAmountMN,SMOKFR1Note;
	public static String SMOKFR2FundraisingName,SMOKFR2InstitutionName,SMOKFR2FundName;
	
	//task
	public static String SMOKTask1Status,SMOKTask1Subject,SMOKTask1Name,SMOKTask1DueDate;
	public static String SMOKTask2Status,SMOKTask2Subject,SMOKTask2Name,SMOKTask2DueDate;
	public static String SMOKTask3Status,SMOKTask3Subject,SMOKTask3Name,SMOKTask3DueDate;
	public static String SMOKTask4Status,SMOKTask4Subject,SMOKTask4Name,SMOKTask4DueDate;
	public static String SMOKTask5Status,SMOKTask5Subject,SMOKTask5Name,SMOKTask5DueDate;
	
	//event
	
	public static String SMOKEvent1StartDate,SMOKEvent1Subject,SMOKEvent1Name,SMOKEvent1EndDate;
	public static String SMOKEvent2StartDate,SMOKEvent2Subject,SMOKEvent2Name,SMOKEvent2EndDate;
	
	public static String SmokeDealIns1,SmokeDealIns1RecordType;
 	public static String SmokeDealIns2,SmokeDealIns2RecordType;
 	public static String SmokeDealContact1FName,SmokeDealContact1LName,SmokeDealContact1Inst,SmokeDealContact1EmailID,SmokeDealContact1RecordType;
 	public static String SmokeDeal1,SmokeDeal1CompanyName,SmokeDeal1RecordType,SmokeDeal1Stage;
 	
 	public static String SmokePFIns1,SmokePFIns1RecordType;
 	public static String SmokePFIns2,SmokePFIns2RecordType;
 	public static String SmokePFContact1FName,SmokePFContact1LName,SmokePFContact1Inst,SmokePFContact1EmailID,SmokePFContact1RecordType;
 	public static String SmokeDeal2,SmokeDeal2CompanyName,SmokeDeal2RecordType,SmokeDeal2Stage;
 	public static String SmokeFR1,SmokeFR1Fund,SmokeFR1LegalName,SmokeFR1Stage;
	
	
	
	public CommonVariables(Object obj) {
		//TODO Auto-generated constructor stub
		AppListeners.appLog.info("Kindly hold on starting variable intialization........");
		long StartTime = System.currentTimeMillis();
		URL = ExcelUtils.readDataFromPropertyFile("URL");
		browserToLaunch = ExcelUtils.readDataFromPropertyFile("Browser");
		appName=ExcelUtils.readDataFromPropertyFile("AppName");
		superAdminUserName=ExcelUtils.readDataFromPropertyFile("SuperAdminUsername");
		superAdminRegistered=ExcelUtils.readDataFromPropertyFile("SuperAdminRegistered");
		appName=ExcelUtils.readDataFromPropertyFile("AppName");
		tabCustomObj=ExcelUtils.readDataFromPropertyFile("CustomTabName");
		tabCustomObjAPIName=tabCustomObj.replace(" ", "_")+"__c";
		tabObj1=ExcelUtils.readDataFromPropertyFile("Object1");
		tabObj2=ExcelUtils.readDataFromPropertyFile("Object2");
		tabObj3=ExcelUtils.readDataFromPropertyFile("Object3");
		tabObj4=ExcelUtils.readDataFromPropertyFile("Object4");
		tabObj5=ExcelUtils.readDataFromPropertyFile("Object5");
		tabObj6=ExcelUtils.readDataFromPropertyFile("Object6");
		tabObj7=ExcelUtils.readDataFromPropertyFile("Object7");


		tabCustomObjField=ExcelUtils.readDataFromPropertyFile("CustomTabFieldName");
		environment=ExcelUtils.readDataFromPropertyFile("Environment");
		mode=ExcelUtils.readDataFromPropertyFile("Mode");
		todaysDate=getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
		todaysDateEurope=getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M.d.YYYY");
		todaysDateSingleDigit=getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/YYYY");
		todaysDateNewZealand=getDateAccToTimeZone(BasePageErrorMessage.NewZealandTimeZone, "M/d/YYYY");
		todaysDateddmm=getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "dd/MM/YYYY");

		tomorrowsDate=previousOrForwardDate(1, "M/d/YYYY");
		yesterdaysDate=previousOrForwardDate(-1, "M/d/YYYY");
		todaysDate1=previousOrForwardDate(0, "M/d/YYYY");

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

		crmUser2FirstName=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User2", excelLabel.User_First_Name);
		crmUser2LastName=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User2", excelLabel.User_Last_Name);
		crmUser2EmailID=ExcelUtils.readData(testCasesFilePath,"Users",excelLabel.Variable_Name, "User2", excelLabel.User_Email);

		tabCustomObjField=ExcelUtils.readDataFromPropertyFile("CustomTabFieldName");

		tabCustomObj=ExcelUtils.readDataFromPropertyFile("CustomTabName");
		tabObj1=ExcelUtils.readDataFromPropertyFile("Object1");
		tabObj2=ExcelUtils.readDataFromPropertyFile("Object2");
		tabObj3=ExcelUtils.readDataFromPropertyFile("Object3");
		tabObj4=ExcelUtils.readDataFromPropertyFile("Object4");
		tabObj8Coverage=ExcelUtils.readDataFromPropertyFile("Object8");

		SDG = "Sortable Data Grids";

		AppDeveloperName=ExcelUtils.readDataFromPropertyFile("AppDeveloperName");
		AppDescription=ExcelUtils.readDataFromPropertyFile("AppDescription");


		//****************************************************************	Toggle Variable **********************************************************//



		if(obj instanceof Module5New){


			ToggleLP1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TOGGLELP1", excelLabel.Institutions_Name);
			TogglePartnerShip1=ExcelUtils.readData(phase1DataSheetFilePath,"Partnerships",excelLabel.Variable_Name, "TOGGLEPS1", excelLabel.PartnerShip_Name);
			TogglePartnerShip2=ExcelUtils.readData(phase1DataSheetFilePath,"Partnerships",excelLabel.Variable_Name, "TOGGLEPS2", excelLabel.PartnerShip_Name);

			ToggleFund1=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Fund_Name);
			ToggleFund1Type=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Fund_Type);
			ToggleFund1Category=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Investment_Category);
			ToggleFund1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Record_Type);

			ToggleFund2=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Fund_Name);
			ToggleFund2Type=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Fund_Type);
			ToggleFund2Category=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Investment_Category);
			ToggleFund2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Record_Type);


			Sdg1Name=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.SDG_Name);
			Sdg1TagName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.SDG_Tag);;
			Sdg1ObjectName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.sObjectName);

			ActiveDealToggleButton = "Active Deals with All Stages !@#$%^&*() @#$%^&*Deals";

			// ToggleOpenQA1ID,ToggleOpenQA1RequestedDate,ToggleOpenQA1Request,ToggleOpenQA1Status;

			ToggleOpenQA1ID=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "OPENQA1", excelLabel.Request_Tracker_ID);
			ToggleOpenQA1RequestedDate=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "OPENQA1", excelLabel.Date_Requested);;
			ToggleOpenQA1Request=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "OPENQA1", excelLabel.Request);
			ToggleOpenQA1Status=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "OPENQA1", excelLabel.Status);

			ToggleClosedQA1ID=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "CLOSEDQA1", excelLabel.Request_Tracker_ID);
			ToggleClosedQA1RequestedDate=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "CLOSEDQA1", excelLabel.Date_Requested);;
			ToggleClosedQA1Request=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "CLOSEDQA1", excelLabel.Request);
			ToggleClosedQA1Status=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "CLOSEDQA1", excelLabel.Status);



			// ToggleCheck1TabName,ToggleCheck1ItemName,ToggleCheck1RelatedTab,ToggleCheck1ToggleButtons;

			ToggleCheck1TabName=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC1", excelLabel.TabName);
			ToggleCheck1ItemName=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC1", excelLabel.Item);;
			ToggleCheck1RelatedTab=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC1", excelLabel.RelatedTab);
			ToggleCheck1ToggleButtons=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC1", excelLabel.ToggleButton);
			ToggleCheck1ColumnName=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC1", excelLabel.Column_Name);

			//ToggleCheck1ColumnName1,ToggleCheck1ColumnName2

			ToggleCheck2TabName=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC2", excelLabel.TabName);
			ToggleCheck2ItemName=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC2", excelLabel.Item);;
			ToggleCheck2RelatedTab=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC2", excelLabel.RelatedTab);
			ToggleCheck2ToggleButtons=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC2", excelLabel.ToggleButton);
			ToggleCheck2ColumnName=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC2", excelLabel.Column_Name);


			ToggleCheck3TabName=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC3", excelLabel.TabName);
			ToggleCheck3ItemName=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC3", excelLabel.Item);;
			ToggleCheck3RelatedTab=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC3", excelLabel.RelatedTab);
			ToggleCheck3ToggleButtons=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC3", excelLabel.ToggleButton);
			ToggleCheck3ColumnName=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC3", excelLabel.Column_Name);

			/// New Modulew 5


			M5Sdg1Name=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.SDG_Name);
			M5Sdg1TagName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.SDG_Tag);;
			M5Sdg1ObjectName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.sObjectName);
			M5Sdg1ParentName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.Parent_Field_Name);

			M5Sdg2Name=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG2", excelLabel.SDG_Name);
			M5Sdg2TagName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG2", excelLabel.SDG_Tag);;
			M5Sdg2ObjectName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG2", excelLabel.sObjectName);
			M5Sdg2ParentName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG2", excelLabel.Parent_Field_Name);

			M5Sdg3Name=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG3", excelLabel.SDG_Name);
			M5Sdg3TagName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG3", excelLabel.SDG_Tag);;
			M5Sdg3ObjectName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG3", excelLabel.sObjectName);
			M5Sdg3ParentName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG3", excelLabel.Parent_Field_Name);

			M5Sdg4Name=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG4", excelLabel.SDG_Name);
			M5Sdg4TagName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG4", excelLabel.SDG_Tag);;
			M5Sdg4ObjectName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG4", excelLabel.sObjectName);
			M5Sdg4ParentName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG4", excelLabel.Parent_Field_Name);

			M5Sdg5Name=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG5", excelLabel.SDG_Name);
			M5Sdg5TagName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG5", excelLabel.SDG_Tag);;
			M5Sdg5ObjectName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG5", excelLabel.sObjectName);
			M5Sdg5ParentName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG5", excelLabel.Parent_Field_Name);


			ToggleIns1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TOGGLEINS1", excelLabel.Institutions_Name);
			ToggleIns1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TOGGLEINS1", excelLabel.Record_Type);



			ToggleContact1FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M5CON1", excelLabel.Contact_FirstName);
			ToggleContact1LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M5CON1", excelLabel.Contact_LastName);
			ToggleContact1Inst=ToggleIns1;
			ToggleContact1EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M5CON1", excelLabel.Contact_EmailId);
			ToggleContact1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M5CON1", excelLabel.Record_Type);



			ToggleDeal1=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "TOGGLEDEAL1", excelLabel.Deal_Name);
			ToggleDeal1CompanyName=ToggleIns1;
			ToggleDeal1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "TOGGLEDEAL1", excelLabel.Record_Type);
			ToggleDeal1Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "TOGGLEDEAL1", excelLabel.Stage);


			ToggleMarketingEvent1Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "TOGGLEME1", excelLabel.Marketing_Event_Name);
			ToggleMarketingEvent1Date=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "TOGGLEME1", excelLabel.Date);
			ToggleMarketingEvent1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "TOGGLEME1", excelLabel.Record_Type);
			ToggleMarketingEvent1Organizer=ToggleIns1;


		}

		else if(obj instanceof Module2){
			Smoke_TWINS1Name=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS1", excelLabel.Institutions_Name);
			Smoke_TWINS1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS1", excelLabel.Record_Type);
			Smoke_TWINS1Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS1", excelLabel.Status);
			Smoke_CDINS1Name=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "CDINS1", excelLabel.Institutions_Name);
			Smoke_CDINS1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "CDINS1", excelLabel.Record_Type);
			Smoke_CDINS1Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "CDINS1", excelLabel.Status);
			Smoke_CDINS2Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "CDINS2", excelLabel.Status);

			// TASK INS2..............
			Smoke_TWINS2Name=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS2", excelLabel.Institutions_Name);
			Smoke_TWINS2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS2", excelLabel.Record_Type);
			Smoke_TWINS2Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS2", excelLabel.Status);
			Smoke_CDINS2Name=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "CDINS2", excelLabel.Institutions_Name);
			Smoke_CDINS2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "CDINS2", excelLabel.Record_Type);
			Smoke_CDINS3Name=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "CDINS3", excelLabel.Institutions_Name);
			Smoke_CDINS3RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "CDINS3", excelLabel.Record_Type);

			// TASK INS3..............
			Smoke_TWINS3Name=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS3", excelLabel.Institutions_Name);
			Smoke_TWINS3RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS3", excelLabel.Record_Type);
			Smoke_TWINS3Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS3", excelLabel.Status);

			// TASK INS4..............
			Smoke_TWINS4Name=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS4", excelLabel.Institutions_Name);
			Smoke_TWINS4RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS4", excelLabel.Record_Type);
			Smoke_TWINS4Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS4", excelLabel.Status);

			Smoke_HSRINS1Name=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "HSRINS1", excelLabel.Institutions_Name);
			Smoke_HSRINS1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "HSRINS1", excelLabel.Record_Type);

			Smoke_HSRINS2Name=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "HSRINS2", excelLabel.Institutions_Name);
			Smoke_HSRINS2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "HSRINS2", excelLabel.Record_Type);

			Smoke_HSRINS3Name=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "HSRINS3", excelLabel.Institutions_Name);
			Smoke_HSRINS3RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "HSRINS3", excelLabel.Record_Type);

			Smoke_HSRINS4Name=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "HSRINS4", excelLabel.Institutions_Name);
			Smoke_HSRINS4RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "HSRINS4", excelLabel.Record_Type);

			Smoke_HSRINS5Name=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "HSRINS5", excelLabel.Institutions_Name);
			Smoke_HSRINS5RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "HSRIN15", excelLabel.Record_Type);

			// TASK Contact1..............
			Smoke_TWContact1FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON1", excelLabel.Contact_FirstName);
			Smoke_TWContact1LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON1", excelLabel.Contact_LastName);
			Smoke_TWContact1EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON1", excelLabel.Contact_EmailId);
			Smoke_TWContact1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON1", excelLabel.Record_Type);
			Smoke_HSRContact1FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "HSRCON1", excelLabel.Contact_FirstName);
			Smoke_HSRContact1LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "HSRCON1", excelLabel.Contact_LastName);
			Smoke_HSRContact1EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "HSRCON1", excelLabel.Contact_EmailId);
			Smoke_HSRContact2FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "HSRCON2", excelLabel.Contact_FirstName);
			Smoke_HSRContact2LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "HSRCON2", excelLabel.Contact_LastName);
			Smoke_HSRContact2EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "HSRCON2", excelLabel.Contact_EmailId);
			Smoke_HSRContact3FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "HSRCON3", excelLabel.Contact_FirstName);
			Smoke_HSRContact3LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "HSRCON3", excelLabel.Contact_LastName);
			Smoke_HSRContact3EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "HSRCON3", excelLabel.Contact_EmailId);
			Smoke_HSRPipeline1Name= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP1", excelLabel.Deal_Name);
			Smoke_HSRPipeline1Stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP1", excelLabel.Stage);
			Smoke_HSRPipeline2Name= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP2", excelLabel.Deal_Name);
			Smoke_HSRPipeline2Stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP2", excelLabel.Stage);
			Smoke_HSRPipeline3Name= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP3", excelLabel.Deal_Name);
			Smoke_HSRPipeline3Stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP3", excelLabel.Stage);
			Smoke_HSRPipeline4Name= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP4", excelLabel.Deal_Name);
			Smoke_HSRPipeline4Stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP4", excelLabel.Stage);
			Smoke_HSRPipeline5Name= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP5", excelLabel.Deal_Name);
			Smoke_HSRPipeline5Stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP5", excelLabel.Stage);
			Smoke_HSRPipeline6Name= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP6", excelLabel.Deal_Name);
			Smoke_HSRPipeline6Stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP6", excelLabel.Stage);

			// TASK Contact2..............
			Smoke_TWContact2FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON2", excelLabel.Contact_FirstName);
			Smoke_TWContact2LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON2", excelLabel.Contact_LastName);
			Smoke_TWContact2EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON2", excelLabel.Contact_EmailId);
			Smoke_TWContact2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON2", excelLabel.Record_Type);

			// TASK Contact3..............
			Smoke_TWContact3FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON3", excelLabel.Contact_FirstName);
			Smoke_TWContact3LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON3", excelLabel.Contact_LastName);
			Smoke_TWContact3EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON3", excelLabel.Contact_EmailId);
			Smoke_TWContact3RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON3", excelLabel.Record_Type);

			// TASK Contact4..............
			Smoke_TWContact4FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON4", excelLabel.Contact_FirstName);
			Smoke_TWContact4LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON4", excelLabel.Contact_LastName);
			Smoke_TWContact4EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON4", excelLabel.Contact_EmailId);
			Smoke_TWContact4RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON4", excelLabel.Record_Type);

			// TASK Contact5..............
			Smoke_TWContact5FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON5", excelLabel.Contact_FirstName);
			Smoke_TWContact5LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON5", excelLabel.Contact_LastName);
			Smoke_TWContact5EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON5", excelLabel.Contact_EmailId);
			Smoke_TWContact5RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON5", excelLabel.Record_Type);
			TWTask1Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask1", excelLabel.Subject);
			TWTask2Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask2", excelLabel.Subject);
			TWTask3Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask3", excelLabel.Subject);
			TWTask4Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask4", excelLabel.Subject);
			TWTask5Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask5", excelLabel.Subject);
			TWTask6Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask6", excelLabel.Subject);
			TWTask8Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask7", excelLabel.Subject);
			TWTaskCR1Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask8", excelLabel.Subject);
			TWTaskUpdateLabelSubject=ExcelUtils.readData(phase1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask9", excelLabel.Subject);

		}else if(obj instanceof Module1){

			ToggleOpenQA1ID=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "OPENQA1", excelLabel.Request_Tracker_ID);
			ToggleOpenQA1RequestedDate=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "OPENQA1", excelLabel.Date_Requested);;
			ToggleOpenQA1Request=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "OPENQA1", excelLabel.Request);
			ToggleOpenQA1Status=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "OPENQA1", excelLabel.Status);

			ToggleClosedQA1ID=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "CLOSEDQA1", excelLabel.Request_Tracker_ID);
			ToggleClosedQA1RequestedDate=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "CLOSEDQA1", excelLabel.Date_Requested);;
			ToggleClosedQA1Request=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "CLOSEDQA1", excelLabel.Request);
			ToggleClosedQA1Status=ExcelUtils.readData(phase1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "CLOSEDQA1", excelLabel.Status);

			ToggleCheck2RelatedTab=ExcelUtils.readData(phase1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC2", excelLabel.RelatedTab);



			FS_Object1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.Object_Name);
			FS_Object2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.Object_Name);
			FS_Object3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.Object_Name);
			FS_Object4=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS4", excelLabel.Object_Name);
			FS_Object5=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS5", excelLabel.Object_Name);
			FS_ExtraFieldsName1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.ExtraFieldsName);

			FS_FieldSetLabel1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.Field_Set_Label);
			FS_FieldSetLabel2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.Field_Set_Label);
			FS_FieldSetLabel3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.Field_Set_Label);
			FS_FieldSetLabel4=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS4", excelLabel.Field_Set_Label);
			FS_FieldSetLabel5=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS5", excelLabel.Field_Set_Label);

			FS_NameSpacePrefix1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.NameSpace_PreFix);
			FS_NameSpacePrefix2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.NameSpace_PreFix);
			FS_NameSpacePrefix3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.NameSpace_PreFix);
			FS_NameSpacePrefix4=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS4", excelLabel.NameSpace_PreFix);
			FS_NameSpacePrefix5=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS5", excelLabel.NameSpace_PreFix);


			FS_FieldsName1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.Fields_Name);
			FS_FieldsName2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.Fields_Name);
			FS_FieldsName3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.Fields_Name);
			FS_FieldsName4=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS4", excelLabel.Fields_Name);
			FS_FieldsName5=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS5", excelLabel.Fields_Name);


			FS_Con1_FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Contact_FirstName);
			FS_Con1_LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Contact_LastName);
			FS_Con1_Email=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Contact_EmailId);
			FS_Con1_Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Phone);
			FS_Con1_RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Record_Type);

			FS_Ins1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "Ins1", excelLabel.Institutions_Name);
			FS_Ins1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "Ins1", excelLabel.Record_Type);

			FS_Ins2=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "Ins2", excelLabel.Institutions_Name);
			FS_Ins2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "Ins2", excelLabel.Record_Type);

			FS_LP1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "Ins3", excelLabel.Institutions_Name);
			FS_LP1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "Ins3", excelLabel.Record_Type);


			FS_Fund1=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund1", excelLabel.Fund_Name);
			FS_Fund1Type=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund1", excelLabel.Fund_Type);
			FS_Fund1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund1", excelLabel.Record_Type);
			FS_Fund1InvestmentCategory=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund1", excelLabel.Investment_Category);


			FS_Fund2=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund2", excelLabel.Fund_Name);
			FS_Fund2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund2", excelLabel.Record_Type);
			FS_Fund2Type=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund2", excelLabel.Fund_Type);
			FS_Fund2InvestmentCategory=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund2", excelLabel.Investment_Category);


			FS_PartnerShip1=ExcelUtils.readData(phase1DataSheetFilePath,"Partnerships",excelLabel.Variable_Name, "P1", excelLabel.PartnerShip_Name);
			FS_PartnerShip2=ExcelUtils.readData(phase1DataSheetFilePath,"Partnerships",excelLabel.Variable_Name, "P2", excelLabel.PartnerShip_Name);


			FS_CommitmentID1=ExcelUtils.readData(phase1DataSheetFilePath,"Commitments",excelLabel.Variable_Name, "Com1", excelLabel.Commitment_ID);
			FS_CommitmentAmount1=ExcelUtils.readData(phase1DataSheetFilePath,"Commitments",excelLabel.Variable_Name, "Com1", excelLabel.Commitment_Amount);
			FS_FinalCommitmentDate1=ExcelUtils.readData(phase1DataSheetFilePath,"Commitments",excelLabel.Variable_Name, "Com1", excelLabel.Final_Commitment_Date);

			FS_CommitmentID2=ExcelUtils.readData(phase1DataSheetFilePath,"Commitments",excelLabel.Variable_Name, "Com2", excelLabel.Commitment_ID);
			FS_CommitmentAmount2=ExcelUtils.readData(phase1DataSheetFilePath,"Commitments",excelLabel.Variable_Name, "Com2", excelLabel.Commitment_Amount);
			FS_FinalCommitmentDate2=ExcelUtils.readData(phase1DataSheetFilePath,"Commitments",excelLabel.Variable_Name, "Com2", excelLabel.Final_Commitment_Date);



			FS_DealName1= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "Deal1", excelLabel.Deal_Name);
			FS_Deal1CompanyName=FS_Ins1;
			FS_Deal1Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "Deal1", excelLabel.Stage);
			FS_Deal1SourceFirm=FS_Ins1;
			FS_Deal1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "Deal1", excelLabel.Record_Type);
			FS_Deal1SourceContact=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "Deal1", excelLabel.Source_Contact);

			FS_DealName2= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "Deal2", excelLabel.Deal_Name);
			FS_Deal2CompanyName=FS_Ins2;
			FS_Deal2Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "Deal2", excelLabel.Stage);

			FS_DealName3= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "Deal3", excelLabel.Deal_Name);
			FS_Deal3CompanyName=FS_Ins2;
			FS_Deal3Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "Deal3", excelLabel.Stage);

			FC_Object1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC1", excelLabel.Object_Name);
			FC_FieldLabelName1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC1", excelLabel.Field_Label);
			FC_Length1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC1", excelLabel.Length);
			FC_FieldType1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC1", excelLabel.Field_Type);
			FC_FieldLabel1SubString=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC1", excelLabel.FieldLabel_SubString);


			FC_Object2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC2", excelLabel.Object_Name);
			FC_FieldLabelName2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC2", excelLabel.Field_Label);
			FC_Length2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC2", excelLabel.Length);
			FC_FieldType2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC2", excelLabel.Field_Type);

			FC_Object3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC3", excelLabel.Object_Name);
			FC_FieldLabelName3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC3", excelLabel.Field_Label);
			FC_Length3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC3", excelLabel.Length);
			FC_FieldType3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC3", excelLabel.Field_Type);

			FC_Object4=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC4", excelLabel.Object_Name);
			FC_FieldLabelName4=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC4", excelLabel.Field_Label);
			FC_Length4=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC4", excelLabel.Length);
			FC_FieldType4=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC4", excelLabel.Field_Type);

			FC_Object5=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC5", excelLabel.Object_Name);
			FC_FieldLabelName5=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC5", excelLabel.Field_Label);
			FC_Length5=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC5", excelLabel.Length);
			FC_FieldType5=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC5", excelLabel.Field_Type);

			FC_Object6=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC6", excelLabel.Object_Name);
			FC_FieldLabelName6=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC6", excelLabel.Field_Label);
			FC_Length6=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC6", excelLabel.Length);
			FC_FieldType6=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC6", excelLabel.Field_Type);

			FC_Object7=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC7", excelLabel.Object_Name);
			FC_FieldLabelName7=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC7", excelLabel.Field_Label);
			FC_Length7=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC7", excelLabel.Length);
			FC_FieldType7=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC7", excelLabel.Field_Type);

			FC_Object8=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC8", excelLabel.Object_Name);
			FC_FieldLabelName8=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC8", excelLabel.Field_Label);
			FC_Length8=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC8", excelLabel.Length);
			FC_FieldType8=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC8", excelLabel.Field_Type);

			FC_Object9=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC9", excelLabel.Object_Name);
			FC_FieldLabelName9=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC9", excelLabel.Field_Label);
			FC_Length9=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC9", excelLabel.Length);
			FC_FieldType9=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC9", excelLabel.Field_Type);

			FC_Object10=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC10", excelLabel.Object_Name);
			FC_FieldLabelName10=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC10", excelLabel.Field_Label);
			FC_Length10=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC10", excelLabel.Length);
			FC_FieldType10=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC10", excelLabel.Field_Type);

			FC_Object11=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC11", excelLabel.Object_Name);
			FC_FieldLabelName11=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC11", excelLabel.Field_Label);
			FC_Length11=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC11", excelLabel.Length);
			FC_FieldType11=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC11", excelLabel.Field_Type);

			FC_Object12=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC12", excelLabel.Object_Name);
			FC_FieldLabelName12=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC12", excelLabel.Field_Label);
			FC_Length12=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC12", excelLabel.Length);
			FC_FieldType12=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC12", excelLabel.Field_Type);

			FC_Object13=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC13", excelLabel.Object_Name);
			FC_FieldLabelName13=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC13", excelLabel.Field_Label);
			FC_Length13=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC13", excelLabel.Length);
			FC_FieldType13=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC13", excelLabel.Field_Type);

			FC_Object14=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC14", excelLabel.Object_Name);
			FC_FieldLabelName14=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC14", excelLabel.Field_Label);
			FC_Length14=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC14", excelLabel.Length);
			FC_FieldType14=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC14", excelLabel.Field_Type);

			FC_Object15=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC15", excelLabel.Object_Name);
			FC_FieldLabelName15=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC15", excelLabel.Field_Label);
			FC_Length15=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC15", excelLabel.Length);
			FC_FieldType15=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC15", excelLabel.Field_Type);

			FC_Object16=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC16", excelLabel.Object_Name);
			FC_FieldLabelName16=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC16", excelLabel.Field_Label);
			FC_Length16=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC16", excelLabel.Length);
			FC_FieldType16=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC16", excelLabel.Field_Type);

			FC_Object17=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC17", excelLabel.Object_Name);
			FC_FieldLabelName17=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC17", excelLabel.Field_Label);
			FC_Length17=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC17", excelLabel.Length);
			FC_FieldType17=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC17", excelLabel.Field_Type);

			FC_Object18=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC18", excelLabel.Object_Name);
			FC_FieldLabelName18=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC18", excelLabel.Field_Label);
			FC_Length18=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC18", excelLabel.Length);
			FC_FieldType18=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC18", excelLabel.Field_Type);

			FC_Object19=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC19", excelLabel.Object_Name);
			FC_FieldLabelName19=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC19", excelLabel.Field_Label);
			FC_Length19=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC19", excelLabel.Length);
			FC_FieldType19=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC19", excelLabel.Field_Type);

			FC_Object20=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC20", excelLabel.Object_Name);
			FC_FieldLabelName20=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC20", excelLabel.Field_Label);
			FC_Length20=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC20", excelLabel.Length);
			FC_FieldType20=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC20", excelLabel.Field_Type);

			FC_Object21=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC21", excelLabel.Object_Name);
			FC_FieldLabelName21=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC21", excelLabel.Field_Label);
			FC_Length21=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC21", excelLabel.Length);
			FC_FieldType21=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC21", excelLabel.Field_Type);

			FC_Object22=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC22", excelLabel.Object_Name);
			FC_FieldLabelName22=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC22", excelLabel.Field_Label);
			FC_Length22=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC22", excelLabel.Length);
			FC_FieldType22=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC22", excelLabel.Field_Type);

			FC_Object23=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC23", excelLabel.Object_Name);
			FC_FieldLabelName23=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC23", excelLabel.Field_Label);
			FC_Length23=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC23", excelLabel.Length);
			FC_FieldType23=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC23", excelLabel.Field_Type);

			FC_Object24=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC24", excelLabel.Object_Name);
			FC_FieldLabelName24=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC24", excelLabel.Field_Label);
			FC_Length24=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC24", excelLabel.Length);
			FC_FieldType24=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC24", excelLabel.Field_Type);

			FC_Object25=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC25", excelLabel.Object_Name);
			FC_FieldLabelName25=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC25", excelLabel.Field_Label);
			FC_Length25=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC25", excelLabel.Length);
			FC_FieldType25=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC25", excelLabel.Field_Type);

			FC_Object26=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC26", excelLabel.Object_Name);
			FC_FieldLabelName26=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC26", excelLabel.Field_Label);
			FC_Length26=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC26", excelLabel.Length);
			FC_FieldType26=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC26", excelLabel.Field_Type);

			FC_Object27=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC27", excelLabel.Object_Name);
			FC_FieldLabelName27=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC27", excelLabel.Field_Label);
			FC_Length27=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC27", excelLabel.Length);
			FC_FieldType27=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC27", excelLabel.Field_Type);

			FC_Object28=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC28", excelLabel.Object_Name);
			FC_FieldLabelName28=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC28", excelLabel.Field_Label);
			FC_Length28=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC28", excelLabel.Length);
			FC_FieldType28=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC28", excelLabel.Field_Type);

			FC_Object29=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC29", excelLabel.Object_Name);
			FC_FieldLabelName29=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC29", excelLabel.Field_Label);
			FC_Length29=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC29", excelLabel.Length);
			FC_FieldType29=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC29", excelLabel.Field_Type);

			FC_Object30=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC30", excelLabel.Object_Name);
			FC_FieldLabelName30=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC30", excelLabel.Field_Label);
			FC_Length30=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC30", excelLabel.Length);
			FC_FieldType30=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC30", excelLabel.Field_Type);

			FC_Object31=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC31", excelLabel.Object_Name);
			FC_FieldLabelName31=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC31", excelLabel.Field_Label);
			FC_Length31=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC31", excelLabel.Length);
			FC_FieldType31=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC31", excelLabel.Field_Type);


			FC_Object32=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC32", excelLabel.Object_Name);
			FC_FieldLabelName32=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC32", excelLabel.Field_Label);
			FC_FieldType32=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC32", excelLabel.Field_Type);

			FC_Object33=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC33", excelLabel.Object_Name);
			FC_FieldLabelName33=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC33", excelLabel.Field_Label);
			FC_FieldType33=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC33", excelLabel.Field_Type);

			FC_Object34=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC34", excelLabel.Object_Name);
			FC_FieldLabelName34=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC34", excelLabel.Field_Label);
			FC_FieldType34=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC34", excelLabel.Field_Type);

			//Marketing Event 
			FS_MarketingEvent1Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "ME1", excelLabel.Marketing_Event_Name);
			FS_MarketingEvent1Date=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "ME1", excelLabel.Date);
			FS_MarketingEvent1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "ME1", excelLabel.Record_Type);
			FS_MarketingEvent1Organizer=FS_Ins2;

		} else if(obj instanceof Module4){

			M4Sdg1Name=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "M4SDG1", excelLabel.SDG_Name);
			M4Sdg1TagName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "M4SDG1", excelLabel.SDG_Tag);;
			M4Sdg1ObjectName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "M4SDG1", excelLabel.sObjectName);
			M4Sdg1ParentName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "M4SDG1", excelLabel.Parent_Field_Name);
			M4Sdg2Name=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "M4SDG2", excelLabel.SDG_Name);
			M4Sdg2TagName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "M4SDG2", excelLabel.SDG_Tag);;
			M4Sdg2ObjectName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "M4SDG2", excelLabel.sObjectName);
			M4Sdg2ParentName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "M4SDG2", excelLabel.Parent_Field_Name);
			M4Sdg3Name=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "M4SDG3", excelLabel.SDG_Name);
			M4Sdg3TagName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "M4SDG3", excelLabel.SDG_Tag);;
			M4Sdg3ObjectName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "M4SDG3", excelLabel.sObjectName);
			M4Sdg3ParentName=ExcelUtils.readData(phase1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "M4SDG3", excelLabel.Parent_Field_Name);
			M4Ins1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M4INS1", excelLabel.Institutions_Name);
			M4Deal1=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M4Deal1", excelLabel.Deal_Name);
			M4DTField2=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "DTField2", excelLabel.APIName);


			M4MarketingEvent1Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME1", excelLabel.Marketing_Event_Name);
			M4MarketingEvent1Date=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME1", excelLabel.Date);
			M4MarketingEvent1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME1", excelLabel.Record_Type);
			M4MarketingEvent1Organizer=M4Ins1;
			M4MarketingEvent2Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME2", excelLabel.Marketing_Event_Name);
			M4MarketingEvent2Date=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME2", excelLabel.Date);
			M4MarketingEvent2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME2", excelLabel.Record_Type);
			M4MarketingEvent3Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME3", excelLabel.Marketing_Event_Name);
			M4MarketingEvent3Date=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME3", excelLabel.Date);
			M4MarketingEvent3RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME3", excelLabel.Record_Type);
			M4MarketingEvent4Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME4", excelLabel.Marketing_Event_Name);
			M4MarketingEvent4Date=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME4", excelLabel.Date);
			M4MarketingEvent4RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME4", excelLabel.Record_Type);
			M4MarketingEvent5Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME5", excelLabel.Marketing_Event_Name);
			M4MarketingEvent5Date=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME5", excelLabel.Date);
			M4MarketingEvent5RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME5", excelLabel.Record_Type);
			M4MarketingEvent6Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME6", excelLabel.Marketing_Event_Name);
			M4MarketingEvent6Date=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME6", excelLabel.Date);
			M4MarketingEvent6RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME6", excelLabel.Record_Type);
			M4MarketingEvent11Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME11", excelLabel.Marketing_Event_Name);
			M4MarketingEvent12Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME12", excelLabel.Marketing_Event_Name);
			M4MarketingEvent13Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME13", excelLabel.Marketing_Event_Name);
			M4MarketingEvent14Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME14", excelLabel.Marketing_Event_Name);
			M4MarketingEvent15Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME15", excelLabel.Marketing_Event_Name);
			M4MarketingEvent16Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME16", excelLabel.Marketing_Event_Name);
			M4MarketingEvent17Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME17", excelLabel.Marketing_Event_Name);
			M4MarketingEvent18Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME18", excelLabel.Marketing_Event_Name);
			M4MarketingEvent19Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME19", excelLabel.Marketing_Event_Name);
			M4MarketingEvent20Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME20", excelLabel.Marketing_Event_Name);
			M4MarketingEvent20RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME20", excelLabel.Record_Type);
			M4MarketingEvent20Organizer=M4Ins1;
			M4MarketingEvent21Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME21", excelLabel.Marketing_Event_Name);
			M4MarketingEvent21RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME21", excelLabel.Record_Type);
			M4MarketingEvent21Organizer=M4Ins1;
			M4Attendee10Status=ExcelUtils.readData(phase1DataSheetFilePath,"Attendees",excelLabel.Variable_Name, "M4Att10", excelLabel.Status);

			M4Contact1FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON1", excelLabel.Contact_FirstName);
			M4Contact1LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON1", excelLabel.Contact_LastName);
			M4Contact1EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON1", excelLabel.Contact_EmailId);
			M4Contact1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON1", excelLabel.Record_Type);
			M4Contact1Title=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON1", excelLabel.Title);
			M4Contact2Title=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON2", excelLabel.Title);
			M4Contact2Email=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON2", excelLabel.Contact_EmailId);

			M4Contact2FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON2", excelLabel.Contact_FirstName);
			M4Contact2LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON2", excelLabel.Contact_LastName);
			M4Contact8FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON8", excelLabel.Contact_FirstName);
			M4Contact8LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON8", excelLabel.Contact_LastName);
			M4Contact8Title=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON8", excelLabel.Title);


			M4Contact2FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON2", excelLabel.Contact_FirstName);
			M4Contact2LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON2", excelLabel.Contact_LastName);

			M4SDGAction1Name=ExcelUtils.readData(phase1DataSheetFilePath,"SDGActions",excelLabel.Variable_Name, "SDGAction1", excelLabel.Name);
			M4SDGAction1Order=ExcelUtils.readData(phase1DataSheetFilePath,"SDGActions",excelLabel.Variable_Name, "SDGAction1", excelLabel.Action_Order);
			M4SDGAction1Event=ExcelUtils.readData(phase1DataSheetFilePath,"SDGActions",excelLabel.Variable_Name, "SDGAction1", excelLabel.Event);
			M4SDGAction1EventPayload=ExcelUtils.readData(phase1DataSheetFilePath,"SDGActions",excelLabel.Variable_Name, "SDGAction1", excelLabel.Event_Payload);
			M4SDGAction1Type=ExcelUtils.readData(phase1DataSheetFilePath,"SDGActions",excelLabel.Variable_Name, "SDGAction1", excelLabel.Action_Type);
			M4SDGAction2Name=ExcelUtils.readData(phase1DataSheetFilePath,"SDGActions",excelLabel.Variable_Name, "SDGAction2", excelLabel.Name);
			M4SDGAction2Order=ExcelUtils.readData(phase1DataSheetFilePath,"SDGActions",excelLabel.Variable_Name, "SDGAction2", excelLabel.Action_Order);
			M4SDGAction2Event=ExcelUtils.readData(phase1DataSheetFilePath,"SDGActions",excelLabel.Variable_Name, "SDGAction2", excelLabel.Event);
			M4SDGAction2EventPayload=ExcelUtils.readData(phase1DataSheetFilePath,"SDGActions",excelLabel.Variable_Name, "SDGAction2", excelLabel.Event_Payload);
			M4SDGAction2Type=ExcelUtils.readData(phase1DataSheetFilePath,"SDGActions",excelLabel.Variable_Name, "SDGAction2", excelLabel.Action_Type);

			TechonlogyCoverage=ExcelUtils.readData(phase1DataSheetFilePath,"Coverages",excelLabel.Variable_Name, "Coverage5", excelLabel.Coverage_Name);
			TechonlogyCoverageRecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Coverages",excelLabel.Variable_Name, "Coverage5", excelLabel.Record_Type);


		}else if(obj instanceof Module3New){


			M3TestCustomObj1Name=ExcelUtils.readData(phase1DataSheetFilePath, "Test Custom Object", excelLabel.Variable_Name,"M3CSTOBJ", excelLabel.Test_Custom_Object_Name);
			M3TestCustomObj1RecordType=ExcelUtils.readData(phase1DataSheetFilePath, "Test Custom Object", excelLabel.Variable_Name,"M3CSTOBJ", excelLabel.Record_Type);


			M3Fund1=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "M3FUND1", excelLabel.Fund_Name);
			M3Fund1Type=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "M3FUND1", excelLabel.Fund_Type);
			M3Fund1Category=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "M3FUND1", excelLabel.Investment_Category);
			M3Fund1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "M3FUND1", excelLabel.Record_Type);


			SmokeReportFolderName=ExcelUtils.readData(phase1DataSheetFilePath,"Report",excelLabel.Variable_Name, "SmokeReport1", excelLabel.Report_Folder_Name);
			SmokeReportName=ExcelUtils.readData(phase1DataSheetFilePath,"Report",excelLabel.Variable_Name, "SmokeReport1", excelLabel.Report_Name);	
			SmokeReportType=ExcelUtils.readData(phase1DataSheetFilePath,"Report",excelLabel.Variable_Name, "SmokeReport1", excelLabel.Select_Report_Type);
			SmokeReportShow=ExcelUtils.readData(phase1DataSheetFilePath,"Report",excelLabel.Variable_Name, "SmokeReport1", excelLabel.Show);
			SmokeReportRange=ExcelUtils.readData(phase1DataSheetFilePath,"Report",excelLabel.Variable_Name, "SmokeReport1", excelLabel.Range);

			SmokeReport2FolderName=ExcelUtils.readData(phase1DataSheetFilePath,"Report",excelLabel.Variable_Name, "SmokeReport2", excelLabel.Report_Folder_Name);
			SmokeReport2Name=ExcelUtils.readData(phase1DataSheetFilePath,"Report",excelLabel.Variable_Name, "SmokeReport2", excelLabel.Report_Name);	
			SmokeReport2Type=ExcelUtils.readData(phase1DataSheetFilePath,"Report",excelLabel.Variable_Name, "SmokeReport2", excelLabel.Select_Report_Type);
			SmokeReport2Show=ExcelUtils.readData(phase1DataSheetFilePath,"Report",excelLabel.Variable_Name, "SmokeReport2", excelLabel.Show);
			SmokeReport2Range=ExcelUtils.readData(phase1DataSheetFilePath,"Report",excelLabel.Variable_Name, "SmokeReport2", excelLabel.Range);

			//**********************************************************Email Template ******************************************************/

			EmailTemplate1_Subject = ExcelUtils.readData(phase1DataSheetFilePath, "CustomEmailFolder", excelLabel.Variable_Name,"EmailTemplate1", excelLabel.Subject);
			EmailTemplate1_Body = ExcelUtils.readData(phase1DataSheetFilePath, "CustomEmailFolder", excelLabel.Variable_Name,"EmailTemplate1", excelLabel.Email_Body);
			EmailTemplate1_FolderName = ExcelUtils.readData(phase1DataSheetFilePath, "CustomEmailFolder", excelLabel.Variable_Name,"EmailTemplate1", excelLabel.Email_Template_Folder_Label);
			EmailTemplate1_TemplateName = ExcelUtils.readData(phase1DataSheetFilePath, "CustomEmailFolder", excelLabel.Variable_Name,"EmailTemplate1", excelLabel.Email_Template_Name);
			EmailTemplate1_TemplateDescription = ExcelUtils.readData(phase1DataSheetFilePath, "CustomEmailFolder", excelLabel.Variable_Name,"EmailTemplate1", excelLabel.Description);


			M3Call1Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M3CALL1", excelLabel.Subject);
			M3Call1Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M3CALL1", excelLabel.Status);
			M3Cal11Priority=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M3CALL1", excelLabel.Priority);

			M3Task1Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M3TASK1", excelLabel.Subject);
			M3Task1Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M3TASK1", excelLabel.Status);
			M3Task1Priority=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M3TASK1", excelLabel.Priority);

			M3Meeting1Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M3MEETING1", excelLabel.Subject);
			M3Meeting1Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M3MEETING1", excelLabel.Status);
			M3Meeting1Priority=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M3MEETING1", excelLabel.Priority);
			M3Meeting1MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M3MEETING1", excelLabel.Meeting_Type);

			M3Contact1FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON1", excelLabel.Contact_FirstName);
			M3Contact1LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON1", excelLabel.Contact_LastName);
			M3Contact1EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON1", excelLabel.Contact_EmailId);
			M3Contact1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON1", excelLabel.Record_Type);

			M3Contact2FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON2", excelLabel.Contact_FirstName);
			M3Contact2LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON2", excelLabel.Contact_LastName);
			M3Contact2EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON2", excelLabel.Contact_EmailId);
			M3Contact2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON2", excelLabel.Record_Type);

			M3Contact3FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON3", excelLabel.Contact_FirstName);
			M3Contact3LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON3", excelLabel.Contact_LastName);
			M3Contact3EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON3", excelLabel.Contact_EmailId);
			M3Contact3RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON3", excelLabel.Record_Type);

			M3Contact4FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON4", excelLabel.Contact_FirstName);
			M3Contact4LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M3CON4", excelLabel.Contact_LastName);



			M3Ins1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS1", excelLabel.Institutions_Name);
			M3Ins1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS1", excelLabel.Record_Type);

			M3Ins2=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS2", excelLabel.Institutions_Name);
			M3Ins2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS2", excelLabel.Record_Type);

			M3Ins3=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS3", excelLabel.Institutions_Name);
			M3Ins3RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS3", excelLabel.Record_Type);

			M3Ins4=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS4", excelLabel.Institutions_Name);
			M3Ins4RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS4", excelLabel.Record_Type);

			M3Ins5=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS5", excelLabel.Institutions_Name);
			M3Ins5RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS5", excelLabel.Record_Type);

			M3Ins6=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS6", excelLabel.Institutions_Name);
			M3Ins6RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS6", excelLabel.Record_Type);
			M3Ins6Parent=M3Ins5;

			M3Ins7=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS7", excelLabel.Institutions_Name);
			M3Ins7RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS7", excelLabel.Record_Type);

			M3Ins8=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS8", excelLabel.Institutions_Name);
			M3Ins8RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS8", excelLabel.Record_Type);
			M3Ins8Parent=M3Ins3;

			M3Ins9=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS9", excelLabel.Institutions_Name);
			M3Ins9RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS9", excelLabel.Record_Type);
			M3Ins9Parent=M3Ins1;

			M3Ins10=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS10", excelLabel.Institutions_Name);
			M3Ins10RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M3INS10", excelLabel.Record_Type);


			M3FRName1=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "M3FR1", excelLabel.FundRaising_Name);

			M3PartnerShip1=ExcelUtils.readData(phase1DataSheetFilePath,"Partnerships",excelLabel.Variable_Name, "M3PS1", excelLabel.PartnerShip_Name);

			M3Deal1=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M3DEAL1", excelLabel.Deal_Name);
			M3Deal1CompanyName=M3Ins1;
			M3Deal1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M3DEAL1", excelLabel.Record_Type);
			M3Deal1Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M3DEAL1", excelLabel.Stage);



		}else if(obj instanceof Module8){

			M8DealName1=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M8Deal1", excelLabel.Deal_Name);
			M8DealName2=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M8Deal2", excelLabel.Deal_Name);
			M8FRName1=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "M8FR1", excelLabel.FundRaising_Name);
			M8FRName2=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "M8FR2", excelLabel.FundRaising_Name);
			M8CON1FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M8CON1", excelLabel.Contact_FirstName);
			M8CON1LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M8CON1", excelLabel.Contact_LastName);
			M8CON1EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M8CON1", excelLabel.Contact_EmailId);
			M8CON2FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M8CON2", excelLabel.Contact_FirstName);
			M8CON2LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M8CON2", excelLabel.Contact_LastName);
			M8CON2EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M8CON2", excelLabel.Contact_EmailId);


			M8CreateDealButtonName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M8Deal3", excelLabel.Deal_Name);
			M8DealEvent=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M8Deal3", excelLabel.Event);
			M8DealActionOrder=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M8Deal3", excelLabel.Action_Order);
			M8DealActionType=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M8Deal3", excelLabel.Action_Type);
			M8DealEventPayLoad=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M8Deal3", excelLabel.Event_PayLoad);

			M8CreateFundRaisingButtonName=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "M8FR3", excelLabel.FundRaising_Name);
			M8FundRaisingEvent=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "M8FR3", excelLabel.Event);
			M8FundRaisingActionOrder=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "M8FR3", excelLabel.Action_Order);
			M8FundRaisingActionType=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "M8FR3", excelLabel.Action_Type);
			M8FundRaisingEventPayLoad=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "M8FR3", excelLabel.Event_PayLoad);

			M8CreateContactButtonName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M8CON3", excelLabel.Contact_FirstName);
			M8ContactEvent=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M8CON3", excelLabel.Event);
			M8ContactActionOrder=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M8CON3", excelLabel.Action_Order);
			M8ContactActionType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M8CON3", excelLabel.Action_Type);
			M8ContactEventPayLoad=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M8CON3", excelLabel.Event_PayLoad);
			
			
			M8_Object1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "M8Field1", excelLabel.Field_Label);
			M8_Object1FieldName=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "M8Field1", excelLabel.Field_Type);
			
			M8_Object2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "M8Field2", excelLabel.Field_Label);
			M8_Object2FieldName=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "M8Field2", excelLabel.Field_Type);
			
			M8_Object3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "M8Field3", excelLabel.Field_Label);
			M8_Object3FieldName=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "M8Field3", excelLabel.Field_Type);
			
			M8_Object4=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "M8Field4", excelLabel.Field_Label);
			M8_Object4FieldName=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "M8Field4", excelLabel.Field_Type);
			
			M8_Object5=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "M8Field5", excelLabel.Field_Label);
			M8_Object5FieldName=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "M8Field5", excelLabel.Field_Type);
			
			M8_Object6=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "M8Field6", excelLabel.Field_Label);
			M8_Object6FieldName=ExcelUtils.readData(phase1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "M8Field6", excelLabel.Field_Type);
			

			M8CallSubject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task1", excelLabel.Subject);
			M8SendLetterSubject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task2", excelLabel.Subject);
			M8SendQuoteSubject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task3", excelLabel.Subject);
			M8OtherSubject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task4", excelLabel.Subject);
			M8Einstein5Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task5", excelLabel.Subject);
			M8Einstein6Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task6", excelLabel.Subject);
			M8Einstein7Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task7", excelLabel.Subject);
			M8Einstein8Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task8", excelLabel.Subject);
			M8Einstein9Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task9", excelLabel.Subject);
			M8Einstein10Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task10", excelLabel.Subject);
			M8Einstein11Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task11", excelLabel.Subject);
			M8Einstein12Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task12", excelLabel.Subject);
			M8Einstein13Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task13", excelLabel.Subject);
			M8Einstein14Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M8Task14", excelLabel.Subject);
			
			M8EinsteinEvent10Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M8Event10", excelLabel.Subject);
			
		}else if(obj instanceof Module6){
			M6Ins1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS1", excelLabel.Institutions_Name);
			M6Ins2=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS2", excelLabel.Institutions_Name);
			M6Ins3=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS3", excelLabel.Institutions_Name);
			M6Ins6=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS6", excelLabel.Institutions_Name);
			M6Ins9=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS9", excelLabel.Institutions_Name);
			M6Ins10=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS10", excelLabel.Institutions_Name);
			M6Ins11=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS11", excelLabel.Institutions_Name);
			M6Ins12=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS12", excelLabel.Institutions_Name);
			M6Ins13=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS13", excelLabel.Institutions_Name);
			M6Ins14=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS14", excelLabel.Institutions_Name);
			M6Ins15=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS15", excelLabel.Institutions_Name);
			M6Ins16=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS16", excelLabel.Institutions_Name);
			M6Ins17=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS17", excelLabel.Institutions_Name);
			M6Ins18=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS18", excelLabel.Institutions_Name);
			M6Ins20=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS20", excelLabel.Institutions_Name);
			M6Ins21=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS21", excelLabel.Institutions_Name);
			M6Ins22=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS22", excelLabel.Institutions_Name);
			M6LSCIns1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6LSCFINS1", excelLabel.Institutions_Name);
			M6Deal1=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal1", excelLabel.Deal_Name);
			M6Deal2=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal2", excelLabel.Deal_Name);
			M6Deal20Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal20", excelLabel.Stage);
			M6Deal10=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal10", excelLabel.Deal_Name);
			M6Deal11=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal11", excelLabel.Deal_Name);
			M6Deal12=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal12", excelLabel.Deal_Name);
			M6Deal13=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal13", excelLabel.Deal_Name);
			M6Deal14=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal14", excelLabel.Deal_Name);
			M6Deal15=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal15", excelLabel.Deal_Name);
			M6Deal16=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal16", excelLabel.Deal_Name);
			M6Deal17=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal17", excelLabel.Deal_Name);
			M6Deal18=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal18", excelLabel.Deal_Name);
			M6Deal3=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal3", excelLabel.Deal_Name);
			M6Deal4=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal4", excelLabel.Deal_Name);
			M6Deal5=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal5", excelLabel.Deal_Name);
			M6Deal6=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal6", excelLabel.Deal_Name);
			M6Deal7=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal7", excelLabel.Deal_Name);
			M6Deal7Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal7", excelLabel.Stage);
			M6Deal8=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal8", excelLabel.Deal_Name);
			M6Deal8Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal8", excelLabel.Stage);
			M6Deal9=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal9", excelLabel.Deal_Name);
			M6Deal20=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal20", excelLabel.Deal_Name);
			M6Deal21=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal21", excelLabel.Deal_Name);
			M6Deal22=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal22", excelLabel.Deal_Name);
			M6LSCFund1=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "M6LSCFFUND1", excelLabel.Fund_Name);
			M6LSCFundraising1=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "M6LSCFUNDR1", excelLabel.FundRaising_Name);

		}else if(obj instanceof Module7){


			M7Ins1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M7INS1", excelLabel.Institutions_Name);
			M7Ins1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M7INS1", excelLabel.Record_Type);

			M7Contact1FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON1", excelLabel.Contact_FirstName);
			M7Contact1LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON1", excelLabel.Contact_LastName);
			M7Contact1EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON1", excelLabel.Contact_EmailId);
			M7Contact1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON1", excelLabel.Record_Type);

			M7Contact2FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON2", excelLabel.Contact_FirstName);
			M7Contact2LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON2", excelLabel.Contact_LastName);
			M7Contact2EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON2", excelLabel.Contact_EmailId);
			M7Contact2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON2", excelLabel.Record_Type);

			M7Contact3FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON3", excelLabel.Contact_FirstName);
			M7Contact3LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON3", excelLabel.Contact_LastName);
			M7Contact3EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON3", excelLabel.Contact_EmailId);
			M7Contact3RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON3", excelLabel.Record_Type);

			M7Contact4FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON4", excelLabel.Contact_FirstName);
			M7Contact4LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON4", excelLabel.Contact_LastName);
			M7Contact4EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON4", excelLabel.Contact_EmailId);
			M7Contact4RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON4", excelLabel.Record_Type);

			M7Contact5FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON5", excelLabel.Contact_FirstName);
			M7Contact5LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON5", excelLabel.Contact_LastName);
			M7Contact5EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON5", excelLabel.Contact_EmailId);
			M7Contact5RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON5", excelLabel.Record_Type);

			M7Contact6FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON6", excelLabel.Contact_FirstName);
			M7Contact6LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON6", excelLabel.Contact_LastName);
			M7Contact6EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON6", excelLabel.Contact_EmailId);
			M7Contact6RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON6", excelLabel.Record_Type);

			M7Contact7FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON7", excelLabel.Contact_FirstName);
			M7Contact7LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON7", excelLabel.Contact_LastName);
			M7Contact7EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON7", excelLabel.Contact_EmailId);
			M7Contact7RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON7", excelLabel.Record_Type);

			M7Contact8FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON8", excelLabel.Contact_FirstName);
			M7Contact8LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON8", excelLabel.Contact_LastName);
			M7Contact8EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON8", excelLabel.Contact_EmailId);
			M7Contact8RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON8", excelLabel.Record_Type);

			M7Contact9FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON9", excelLabel.Contact_FirstName);
			M7Contact9LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON9", excelLabel.Contact_LastName);
			M7Contact9EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON9", excelLabel.Contact_EmailId);
			M7Contact9RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON9", excelLabel.Record_Type);

			M7Contact10FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON10", excelLabel.Contact_FirstName);
			M7Contact10LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON10", excelLabel.Contact_LastName);
			M7Contact10EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON10", excelLabel.Contact_EmailId);
			M7Contact10RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON10", excelLabel.Record_Type);

			M7Contact11FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON11", excelLabel.Contact_FirstName);
			M7Contact11LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON11", excelLabel.Contact_LastName);
			M7Contact11EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON11", excelLabel.Contact_EmailId);
			M7Contact11RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON11", excelLabel.Record_Type);

			M7Contact12FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON12", excelLabel.Contact_FirstName);
			M7Contact12LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON12", excelLabel.Contact_LastName);
			M7Contact12EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON12", excelLabel.Contact_EmailId);
			M7Contact12RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON12", excelLabel.Record_Type);

			M7Contact13FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON13", excelLabel.Contact_FirstName);
			M7Contact13LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON13", excelLabel.Contact_LastName);
			M7Contact13EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON13", excelLabel.Contact_EmailId);
			M7Contact13RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON13", excelLabel.Record_Type);

			M7Contact14FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON14", excelLabel.Contact_FirstName);
			M7Contact14LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON14", excelLabel.Contact_LastName);
			M7Contact14EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON14", excelLabel.Contact_EmailId);
			M7Contact14RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON14", excelLabel.Record_Type);

			M7Contact15FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON15", excelLabel.Contact_FirstName);
			M7Contact15LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON15", excelLabel.Contact_LastName);
			M7Contact15EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON15", excelLabel.Contact_EmailId);
			M7Contact15RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7CON15", excelLabel.Record_Type);

			
			M7Task1Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task1", excelLabel.Subject);
			M7Task1Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task1", excelLabel.Status);
			M7Task1dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task1", excelLabel.Due_Date);
			M7Task1MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task1", excelLabel.Meeting_Type);

			M7Task2Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task2", excelLabel.Subject);
			M7Task2Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task2", excelLabel.Status);
			M7Task2dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task2", excelLabel.Due_Date);
			M7Task2MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task2", excelLabel.Meeting_Type);

			M7Task3Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task3", excelLabel.Subject);
			M7Task3Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task3", excelLabel.Status);
			M7Task3dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task3", excelLabel.Due_Date);
			M7Task3MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task3", excelLabel.Meeting_Type);
			
			M7Task4Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task4", excelLabel.Subject);
			M7Task4Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task4", excelLabel.Status);
			M7Task4dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task4", excelLabel.Due_Date);
			M7Task4MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task4", excelLabel.Meeting_Type);
			
			M7Task5Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task5", excelLabel.Subject);
			M7Task5Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task5", excelLabel.Status);
			M7Task5dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task5", excelLabel.Due_Date);
			M7Task5MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task5", excelLabel.Meeting_Type);
			
			M7Task6Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task6", excelLabel.Subject);
			M7Task6Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task6", excelLabel.Status);
			M7Task6dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task6", excelLabel.Due_Date);
			M7Task6MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task6", excelLabel.Meeting_Type);
			
			M7Task7Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task7", excelLabel.Subject);
			M7Task7Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task7", excelLabel.Status);
			M7Task7dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task7", excelLabel.Due_Date);
			M7Task7MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task7", excelLabel.Meeting_Type);

			M7Task8Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task8", excelLabel.Subject);
			M7Task8Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task8", excelLabel.Status);
			M7Task8dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task8", excelLabel.Due_Date);
			M7Task8MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task8", excelLabel.Meeting_Type);
			
			M7Task9Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task9", excelLabel.Subject);
			M7Task9Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task9", excelLabel.Status);
			M7Task9dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task9", excelLabel.Due_Date);
			M7Task9MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task9", excelLabel.Meeting_Type);
			
			M7Task10Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task10", excelLabel.Subject);
			M7Task10Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task10", excelLabel.Status);
			M7Task10dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task10", excelLabel.Due_Date);
			M7Task10MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task10", excelLabel.Meeting_Type);
			
			M7Task11Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task11", excelLabel.Subject);
			M7Task11Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task11", excelLabel.Status);
			M7Task11dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task11", excelLabel.Due_Date);
			M7Task11MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task11", excelLabel.Meeting_Type);
			
			M7Task12Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task12", excelLabel.Subject);
			M7Task12Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task12", excelLabel.Status);
			M7Task12dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task12", excelLabel.Due_Date);
			M7Task12MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task12", excelLabel.Meeting_Type);
			
			M7Task13Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task13", excelLabel.Subject);
			M7Task13Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task13", excelLabel.Status);
			M7Task13dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task13", excelLabel.Due_Date);
			M7Task13MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task13", excelLabel.Meeting_Type);
			
			M7Task14Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task14", excelLabel.Subject);
			M7Task14Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task14", excelLabel.Status);
			M7Task14dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task14", excelLabel.Due_Date);
			M7Task14MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task14", excelLabel.Meeting_Type);

			M7Task15Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task15", excelLabel.Subject);
			M7Task15Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task15", excelLabel.Status);
			M7Task15dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task15", excelLabel.Due_Date);
			M7Task15MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task15", excelLabel.Meeting_Type);
			
			M7Task16Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task16", excelLabel.Subject);
			M7Task16Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task16", excelLabel.Status);
			M7Task16dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task16", excelLabel.Due_Date);
			M7Task16MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task16", excelLabel.Meeting_Type);
			
			M7Task17Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task17", excelLabel.Subject);
			M7Task17Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task17", excelLabel.Status);
			M7Task17dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task17", excelLabel.Due_Date);
			M7Task17MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task17", excelLabel.Meeting_Type);
			
			M7Task18Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task18", excelLabel.Subject);
			M7Task18Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task18", excelLabel.Status);
			M7Task18dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task18", excelLabel.Due_Date);
			M7Task18MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "M7Task18", excelLabel.Meeting_Type);
			
			
			M7Event1Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event1", excelLabel.Subject);
			M7Event1StartDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event1", excelLabel.Start_Date);
			M7Event1StartTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event1", excelLabel.Start_Time);
			M7Event1EndDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event1", excelLabel.End_Date);
			M7Event1EndTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event1", excelLabel.End_Time);
			
			M7Event2Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event2", excelLabel.Subject);
			M7Event2StartDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event2", excelLabel.Start_Date);
			M7Event2StartTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event2", excelLabel.Start_Time);
			M7Event2EndDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event2", excelLabel.End_Date);
			M7Event2EndTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event2", excelLabel.End_Time);
			
			M7Event3Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event3", excelLabel.Subject);
			M7Event3StartDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event3", excelLabel.Start_Date);
			M7Event3StartTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event3", excelLabel.Start_Time);
			M7Event3EndDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event3", excelLabel.End_Date);
			M7Event3EndTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event3", excelLabel.End_Time);
			
			M7Event4Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event4", excelLabel.Subject);
			M7Event4StartDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event4", excelLabel.Start_Date);
			M7Event4StartTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event4", excelLabel.Start_Time);
			M7Event4EndDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event4", excelLabel.End_Date);
			M7Event4EndTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event4", excelLabel.End_Time);
			
			M7Event5Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event5", excelLabel.Subject);
			M7Event5StartDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event5", excelLabel.Start_Date);
			M7Event5StartTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event5", excelLabel.Start_Time);
			M7Event5EndDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event5", excelLabel.End_Date);
			M7Event5EndTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event5", excelLabel.End_Time);
			
			M7Event6Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event6", excelLabel.Subject);
			M7Event6StartDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event6", excelLabel.Start_Date);
			M7Event6StartTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event6", excelLabel.Start_Time);
			M7Event6EndDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event6", excelLabel.End_Date);
			M7Event6EndTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event6", excelLabel.End_Time);
			
			M7Event7Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event7", excelLabel.Subject);
			M7Event7StartDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event7", excelLabel.Start_Date);
			M7Event7StartTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event7", excelLabel.Start_Time);
			M7Event7EndDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event7", excelLabel.End_Date);
			M7Event7EndTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "M7Event7", excelLabel.End_Time);
		
		}else if(obj instanceof PECloudSmoke2||obj instanceof PECloudSmoke){
		
			SMOKIns1InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS1", excelLabel.Institutions_Name);
			SMOKIns1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS1", excelLabel.Record_Type);

			SMOKIns2InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS2", excelLabel.Institutions_Name);
			SMOKIns2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS2", excelLabel.Record_Type);

			SMOKIns3InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS3", excelLabel.Institutions_Name);
			SMOKIns3RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS3", excelLabel.Record_Type);


			SMOKIns4InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS4", excelLabel.Institutions_Name);
			SMOKIns4RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS4", excelLabel.Record_Type);

			SMOKIns5InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS5", excelLabel.Institutions_Name);
			SMOKIns5RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS5", excelLabel.Record_Type);

			SMOKIns6InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS6", excelLabel.Institutions_Name);
			SMOKIns6RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS6", excelLabel.Record_Type);

			SMOKIns7InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS7", excelLabel.Institutions_Name);
			SMOKIns7RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS7", excelLabel.Record_Type);

			SMOKIns8InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS8", excelLabel.Institutions_Name);
			SMOKIns8RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS8", excelLabel.Record_Type);

			SMOKIns9InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS9", excelLabel.Institutions_Name);
			SMOKIns9RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS9", excelLabel.Record_Type);
			
			SMOKIns10InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS10", excelLabel.Institutions_Name);
			SMOKIns10RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS10", excelLabel.Record_Type);
			SMOKIns10Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS10", excelLabel.Phone);

			SMOKIns11InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS11", excelLabel.Institutions_Name);
			SMOKIns11RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS11", excelLabel.Record_Type);
			SMOKIns11Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS11", excelLabel.Phone);
			SMOKIns11Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS11", excelLabel.Status);
			SMOKIns11EntityType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS11", excelLabel.Entity_Type);
			SMOKIns11ParentInstitution=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS11", excelLabel.Parent_Institution);

			
			SMOKIns12InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS12", excelLabel.Institutions_Name);
			SMOKIns12RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS12", excelLabel.Record_Type);
			SMOKIns12Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS12", excelLabel.Phone);
			SMOKIns12Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS12", excelLabel.Status);
			SMOKIns12EntityType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS12", excelLabel.Entity_Type);
			SMOKIns12ParentInstitution=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS12", excelLabel.Parent_Institution);


			SMOKIns13InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS13", excelLabel.Institutions_Name);
			SMOKIns13RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS13", excelLabel.Record_Type);
			SMOKIns13Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS13", excelLabel.Phone);
			SMOKIns13Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS13", excelLabel.Status);
			SMOKIns13EntityType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS13", excelLabel.Entity_Type);
			SMOKIns13ParentInstitution=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS13", excelLabel.Parent_Institution);


			SMOKIns14InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS14", excelLabel.Institutions_Name);
			SMOKIns14RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS14", excelLabel.Record_Type);
			SMOKIns14Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS14", excelLabel.Phone);
			SMOKIns14Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS14", excelLabel.Status);
			SMOKIns14EntityType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS14", excelLabel.Entity_Type);
			SMOKIns14ParentInstitution=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS14", excelLabel.Parent_Institution);


			SMOKIns15InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS15", excelLabel.Institutions_Name);
			SMOKIns15RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS15", excelLabel.Record_Type);
			SMOKIns15Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS15", excelLabel.Phone);
			SMOKIns15Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS15", excelLabel.Status);
			SMOKIns15EntityType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS15", excelLabel.Entity_Type);
			SMOKIns15ParentInstitution=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS15", excelLabel.Parent_Institution);


			SMOKIns16InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS16", excelLabel.Institutions_Name);
			SMOKIns16RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS16", excelLabel.Record_Type);
			SMOKIns16Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS16", excelLabel.Phone);
			SMOKIns16Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS16", excelLabel.Status);
			SMOKIns16EntityType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS16", excelLabel.Entity_Type);
			SMOKIns16ParentInstitution=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS16", excelLabel.Parent_Institution);


			SMOKIns17InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS17", excelLabel.Institutions_Name);
			SMOKIns17RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS17", excelLabel.Record_Type);
			SMOKIns17Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS17", excelLabel.Phone);
			SMOKIns17Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS17", excelLabel.Status);
			SMOKIns17EntityType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS17", excelLabel.Entity_Type);
			SMOKIns17ParentInstitution=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS17", excelLabel.Parent_Institution);


			SMOKIns18InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS18", excelLabel.Institutions_Name);
			SMOKIns18RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS18", excelLabel.Record_Type);
			SMOKIns18Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS18", excelLabel.Phone);
			SMOKIns18Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS18", excelLabel.Status);
			SMOKIns18EntityType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS18", excelLabel.Entity_Type);
			SMOKIns18ParentInstitution=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS18", excelLabel.Parent_Institution);


			SMOKIns19InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS19", excelLabel.Institutions_Name);
			SMOKIns19RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS19", excelLabel.Record_Type);
			SMOKIns19Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS19", excelLabel.Phone);
			SMOKIns19Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS19", excelLabel.Status);
			SMOKIns19EntityType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS19", excelLabel.Entity_Type);
			SMOKIns19ParentInstitution=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS19", excelLabel.Parent_Institution);


			SMOKIns20InsName=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS20", excelLabel.Institutions_Name);
			SMOKIns20RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS20", excelLabel.Record_Type);
			SMOKIns20Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS20", excelLabel.Phone);
			SMOKIns20Status=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS20", excelLabel.Status);
			SMOKIns20EntityType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS20", excelLabel.Entity_Type);
			SMOKIns20ParentInstitution=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKINS20", excelLabel.Parent_Institution);


			
			
			
			
			////////////////////CT
			
			SmokeCTIns=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKECTINS", excelLabel.Institutions_Name);
			SmokeCTInsRecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKECTINS", excelLabel.Record_Type);

			SmokeCTIns1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKECTINS1", excelLabel.Institutions_Name);
			SmokeCTIns1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKECTINS1", excelLabel.Record_Type);

			SmokeCTIns2=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKECTINS2", excelLabel.Institutions_Name);
			SmokeCTIns2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKECTINS2", excelLabel.Record_Type);

			
			SmokeCTContact1FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKECTCON1", excelLabel.Contact_FirstName);
			SmokeCTContact1LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKECTCON1", excelLabel.Contact_LastName);
			SmokeCTContact1Inst=SmokeCTIns;
			SmokeCTContact1EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKECTCON1", excelLabel.Contact_EmailId);
			SmokeCTContact1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKECTCON1", excelLabel.Record_Type);

			SmokeCTTask1Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SmokeCTTask1", excelLabel.Subject);
			SmokeCTTask1Priority=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SmokeCTTask1", excelLabel.Priority);
			SmokeCTTask1dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SmokeCTTask1", excelLabel.Due_Date);
			
			SmokeCTLogACall1Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SmokeCTLogACall1", excelLabel.Subject);
			SmokeCTLogACall1Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SmokeCTLogACall1", excelLabel.Status);
			SmokeCTLogACall1dueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SmokeCTLogACall1", excelLabel.Due_Date);
			SmokeCTLogACall1MeetingType=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SmokeCTLogACall1", excelLabel.Meeting_Type);
			SmokeCTLogACall1Comment=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SmokeCTLogACall1", excelLabel.Comment);

			SmokeCTEvent1Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SmokeCTEvent1", excelLabel.Subject);
			SmokeCTEvent1StartDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SmokeCTEvent1", excelLabel.Start_Date);
			SmokeCTEvent1StartTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SmokeCTEvent1", excelLabel.Start_Time);
			SmokeCTEvent1EndDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SmokeCTEvent1", excelLabel.End_Date);
			SmokeCTEvent1EndTime=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SmokeCTEvent1", excelLabel.End_Time);
			SmokeCTEvent1Location=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SmokeCTEvent1", excelLabel.Location);

			
			///////////////////////

			//con
			SMOKCon1FirstName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON1", excelLabel.Contact_FirstName);
			SMOKCon1LastName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON1", excelLabel.Contact_LastName);
			SMOKCon1InstitutionName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON1", excelLabel.Institutions_Name);
			SMOKCon1Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON1", excelLabel.Phone);
			SMOKCon1Tier=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON1", excelLabel.Contact_Tier);

			SMOKCon2FirstName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON2", excelLabel.Contact_FirstName);
			SMOKCon2LastName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON2", excelLabel.Contact_LastName);
			SMOKCon2InstitutionName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON2", excelLabel.Institutions_Name);
			SMOKCon2Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON2", excelLabel.Phone);
			SMOKCon2Tier=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON2", excelLabel.Contact_Tier);

			
			SMOKCon3FirstName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON3", excelLabel.Contact_FirstName);
			SMOKCon3LastName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON3", excelLabel.Contact_LastName);
			SMOKCon3InstitutionName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON3", excelLabel.Institutions_Name);
			SMOKCon3Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON3", excelLabel.Phone);
			SMOKCon3Tier=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON3", excelLabel.Contact_Tier);

			
			SMOKCon4FirstName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON4", excelLabel.Contact_FirstName);
			SMOKCon4LastName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON4", excelLabel.Contact_LastName);
			SMOKCon4InstitutionName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON4", excelLabel.Institutions_Name);
			SMOKCon4Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON4", excelLabel.Phone);
			SMOKCon4Tier=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON4", excelLabel.Contact_Tier);

			
			SMOKCon5FirstName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON5", excelLabel.Contact_FirstName);
			SMOKCon5LastName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON5", excelLabel.Contact_LastName);
			SMOKCon5InstitutionName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON5", excelLabel.Institutions_Name);
			SMOKCon5Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON5", excelLabel.Phone);
			SMOKCon5Tier=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON5", excelLabel.Contact_Tier);

			
			SMOKCon6FirstName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON6", excelLabel.Contact_FirstName);
			SMOKCon6LastName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON6", excelLabel.Contact_LastName);
			SMOKCon6InstitutionName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON6", excelLabel.Institutions_Name);
			SMOKCon6Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON6", excelLabel.Phone);
			SMOKCon6Tier=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON6", excelLabel.Contact_Tier);

			
			SMOKCon7FirstName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON7", excelLabel.Contact_FirstName);
			SMOKCon7LastName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON7", excelLabel.Contact_LastName);
			SMOKCon7InstitutionName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON7", excelLabel.Institutions_Name);
			SMOKCon7Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON7", excelLabel.Phone);
			SMOKCon7Tier=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON7", excelLabel.Contact_Tier);

			SMOKCon8FirstName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON8", excelLabel.Contact_FirstName);
			SMOKCon8LastName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON8", excelLabel.Contact_LastName);
			SMOKCon8InstitutionName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON8", excelLabel.Institutions_Name);
			SMOKCon8Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON8", excelLabel.Phone);
			SMOKCon8Tier=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON8", excelLabel.Contact_Tier);

			SMOKCon9FirstName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON9", excelLabel.Contact_FirstName);
			SMOKCon9LastName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON9", excelLabel.Contact_LastName);
			SMOKCon9InstitutionName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON9", excelLabel.Institutions_Name);
			SMOKCon9Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON9", excelLabel.Phone);
			SMOKCon9Tier=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKCON9", excelLabel.Contact_Tier);

			
			//deal
			SMOKDeal1DealName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal1", excelLabel.Deal_Name);
			SMOKDeal1CompanyName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal1", excelLabel.Company_Name);
			SMOKDeal1SourceFirm=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal1", excelLabel.Source_Firm);
			SMOKDeal1Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal1", excelLabel.Stage);
			SMOKDeal1PipelineComments=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal1", excelLabel.Pipeline_Comments);

			SMOKDeal2DealName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal2", excelLabel.Deal_Name);
			SMOKDeal2CompanyName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal2", excelLabel.Company_Name);
			SMOKDeal2SourceFirm=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal2", excelLabel.Source_Firm);
			SMOKDeal2Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal", excelLabel.Stage);
			SMOKDeal2LogInDate=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal2", excelLabel.Log_In_Date);

			SMOKDeal3DealName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal3", excelLabel.Deal_Name);
			SMOKDeal3CompanyName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal3", excelLabel.Company_Name);
			SMOKDeal3SourceFirm=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal3", excelLabel.Source_Firm);
			SMOKDeal3Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal3", excelLabel.Stage);
			SMOKDeal3LogInDate=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal3", excelLabel.Log_In_Date);

			SMOKDeal4DealName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal4", excelLabel.Deal_Name);
			SMOKDeal4CompanyName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal4", excelLabel.Company_Name);
			SMOKDeal4SourceFirm=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal4", excelLabel.Source_Firm);
			SMOKDeal4Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal4", excelLabel.Stage);
			SMOKDeal4LogInDate=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal4", excelLabel.Log_In_Date);

			SMOKDeal5DealName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal5", excelLabel.Deal_Name);
			SMOKDeal5CompanyName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal5", excelLabel.Company_Name);
			SMOKDeal5SourceFirm=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal5", excelLabel.Source_Firm);
			SMOKDeal5Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal5", excelLabel.Stage);
			SMOKDeal5LogInDate=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal5", excelLabel.Log_In_Date);

			SMOKDeal6DealName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal6", excelLabel.Deal_Name);
			SMOKDeal6CompanyName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal6", excelLabel.Company_Name);
			SMOKDeal6SourceFirm=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal6", excelLabel.Source_Firm);
			SMOKDeal6Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal6", excelLabel.Stage);
			SMOKDeal6LogInDate=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal6", excelLabel.Log_In_Date);

			SMOKDeal7DealName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal7", excelLabel.Deal_Name);
			SMOKDeal7CompanyName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal7", excelLabel.Company_Name);
			SMOKDeal7SourceFirm=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal7", excelLabel.Source_Firm);
			SMOKDeal7Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal7", excelLabel.Stage);
			SMOKDeal7LogInDate=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal7", excelLabel.Log_In_Date);

			SMOKDeal8DealName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal8", excelLabel.Deal_Name);
			SMOKDeal8CompanyName=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal8", excelLabel.Company_Name);
			SMOKDeal8SourceFirm=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal8", excelLabel.Source_Firm);
			SMOKDeal8Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal8", excelLabel.Stage);
			SMOKDeal8LogInDate=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKDeal8", excelLabel.Log_In_Date);

			
			//fund
			SMOKFund1FundName=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "SMOKFund1", excelLabel.Fund_Name);
			SMOKFund1FundType=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "SMOKFund1", excelLabel.Fund_Type);
			SMOKFund1VintageYear=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "SMOKFund1", excelLabel.Vintage_Year);
			SMOKFund1InvestmentCategory=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "SMOKFund1", excelLabel.Investment_Category);

			SMOKFund2FundName=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "SMOKFund2", excelLabel.Fund_Name);
			SMOKFund2FundType=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "SMOKFund2", excelLabel.Fund_Type);
			SMOKFund2VintageYear=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "SMOKFund2", excelLabel.Vintage_Year);
			SMOKFund2InvestmentCategory=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "SMOKFund2", excelLabel.Investment_Category);

			//FR
			SMOKFR1FundraisingName=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "SMOKFR1", excelLabel.FundRaising_Name);
			SMOKFR1FundName=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "SMOKFR1", excelLabel.Fund_Name);
			SMOKFR1InstitutionName=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "SMOKFR1", excelLabel.Institutions_Name);
			SMOKFR1Satge=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "SMOKFR1", excelLabel.Stage);
			SMOKFR1Closing=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "SMOKFR1", excelLabel.Closing);
			SMOKFR1InvestmentLikelyAmountMN=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "SMOKFR1", excelLabel.Investment_Likely_Amount);
			SMOKFR1Note=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "SMOKFR1", excelLabel.Notes);
			
			SMOKFR2FundraisingName=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "SMOKFR2", excelLabel.FundRaising_Name);
			SMOKFR2FundName=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "SMOKFR2", excelLabel.Fund_Name);
			SMOKFR2InstitutionName=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "SMOKFR2", excelLabel.Institutions_Name);
			
			//task
			SMOKTask1Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask1", excelLabel.Status);
			SMOKTask1Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask1", excelLabel.Subject);
			SMOKTask1Name=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask1", excelLabel.Name);
			SMOKTask1DueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask1", excelLabel.Due_Date);
			
			SMOKTask2Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask2", excelLabel.Status);
			SMOKTask2Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask2", excelLabel.Subject);
			SMOKTask2Name=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask2", excelLabel.Name);
			SMOKTask2DueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask2", excelLabel.Due_Date);

			SMOKTask3Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask3", excelLabel.Status);
			SMOKTask3Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask3", excelLabel.Subject);
			SMOKTask3Name=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask3", excelLabel.Name);
			SMOKTask3DueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask3", excelLabel.Due_Date);

			SMOKTask4Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask4", excelLabel.Status);
			SMOKTask4Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask4", excelLabel.Subject);
			SMOKTask4Name=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask4", excelLabel.Name);
			SMOKTask4DueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask4", excelLabel.Due_Date);

			SMOKTask5Status=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask5", excelLabel.Status);
			SMOKTask5Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask5", excelLabel.Subject);
			SMOKTask5Name=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask5", excelLabel.Name);
			SMOKTask5DueDate=ExcelUtils.readData(phase1DataSheetFilePath,"Task1",excelLabel.Variable_Name, "SMOKTask5", excelLabel.Due_Date);



			//event
			SMOKEvent1Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SMOKEvent1", excelLabel.Subject);
			SMOKEvent1StartDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SMOKEvent1", excelLabel.Start_Date);
			SMOKEvent1Name=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SMOKEvent1", excelLabel.Name);
			SMOKEvent1EndDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SMOKEvent1", excelLabel.End_Date);

			SMOKEvent2Subject=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SMOKEvent2", excelLabel.Subject);
			SMOKEvent2StartDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SMOKEvent2", excelLabel.Start_Date);
			SMOKEvent2Name=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SMOKEvent2", excelLabel.Name);
			SMOKEvent2EndDate=ExcelUtils.readData(phase1DataSheetFilePath,"Events",excelLabel.Variable_Name, "SMOKEvent2", excelLabel.End_Date);

			
			SmokeDealIns1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKEDEALINS1", excelLabel.Institutions_Name);
 			SmokeDealIns1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKEDEALINS1", excelLabel.Record_Type);
 
 			SmokeDealIns2=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKEDEALINS2", excelLabel.Institutions_Name);
 			SmokeDealIns2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKEDEALINS2", excelLabel.Record_Type);
 
 			SmokePFIns1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKEPFINS1", excelLabel.Institutions_Name);
 			SmokePFIns1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKEPFINS1", excelLabel.Record_Type);
 
 			SmokePFIns2=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKEPFINS2", excelLabel.Institutions_Name);
 			SmokePFIns2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "SMOKEPFINS2", excelLabel.Record_Type);
 
 			
 			SmokeDealContact1FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEDEALCON1", excelLabel.Contact_FirstName);
 			SmokeDealContact1LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEDEALCON1", excelLabel.Contact_LastName);
 			SmokeDealContact1Inst=SmokeDealIns2;
 			SmokeDealContact1EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEDEALCON1", excelLabel.Contact_EmailId);
 			SmokeDealContact1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEDEALCON1", excelLabel.Record_Type);
 
 			SmokePFContact1FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEPFCON1", excelLabel.Contact_FirstName);
 			SmokePFContact1LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEPFCON1", excelLabel.Contact_LastName);
 			SmokePFContact1Inst=SmokePFIns2;
 			SmokePFContact1EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEPFCON1", excelLabel.Contact_EmailId);
 			SmokePFContact1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEPFCON1", excelLabel.Record_Type);
 
 			
 			SmokeDeal1=SmokeDealIns1+ "1121";
 			SmokeDeal1CompanyName=SmokeDealIns1;
 			SmokeDeal1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKEDEAL1", excelLabel.Record_Type);
 			SmokeDeal1Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKEDEAL1", excelLabel.Stage);
 
 			
 			SmokeDeal2=SmokePFIns1+ "2222";
 			SmokeDeal2CompanyName=SmokePFIns1;
 			SmokeDeal2RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKEDEAL2", excelLabel.Record_Type);
 			SmokeDeal2Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "SMOKEDEAL2", excelLabel.Stage);
 
 			
 			SmokeFund3=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "SMOKEFUND3", excelLabel.Fund_Name);
			SmokeFund3Type=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "SMOKEFUND3", excelLabel.Fund_Type);
			SmokeFund3Category=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "SMOKEFUND3", excelLabel.Investment_Category);
			SmokeFund3RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "SMOKEFUND3", excelLabel.Record_Type);

			SmokeFR1=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "SMOKEFR1", excelLabel.FundRaising_Name);
			SmokeFR1Fund=SmokeFund3;
			SmokeFR1LegalName=SmokeCTIns;
			SmokeFR1Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "SMOKEFR1", excelLabel.Stage);
			

		}

		System.err.println("");
		AppListeners.appLog.info("Done with intialization. Enjoy the show.\nTotal Time Taken: "+((System.currentTimeMillis()-StartTime)/1000)+" seconds.");

	}
	
}
