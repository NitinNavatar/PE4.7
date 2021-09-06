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
	
	// Module 7 
	
	public static String M7Ins1,M7Ins1RecordType;
	
	public static String M7Contact1FName,M7Contact1LName,M7Contact1EmailID,M7Contact1RecordType;
	public static String M7Contact2FName,M7Contact2LName,M7Contact2EmailID,M7Contact2RecordType;
	public static String M7Contact3FName,M7Contact3LName,M7Contact3EmailID,M7Contact3RecordType;
	
	
	public static String M7Task1Subject,M7Task1dueDate,M7Task1Status,M7Task1Priority,M7Task1MeetingType;
	public static String M7Task2Subject,M7Task2dueDate,M7Task2Status,M7Task2Priority,M7Task2MeetingType;
	public static String M7Task3Subject,M7Task3dueDate,M7Task3Status,M7Task3Priority,M7Task3MeetingType;
	public static String M7Task4Subject,M7Task4dueDate,M7Task4Status,M7Task4Priority,M7Task4MeetingType;
	
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
			

			
		}

		System.err.println("");
		AppListeners.appLog.info("Done with intialization. Enjoy the show.\nTotal Time Taken: "+((System.currentTimeMillis()-StartTime)/1000)+" seconds.");

	}
	
}
