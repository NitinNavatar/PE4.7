/**
 * 
 */
package com.navatar.generic;

import static com.navatar.generic.BaseLib.testCasesFilePath;
import static com.navatar.generic.BaseLib.phase1DataSheetFilePath;
import static com.navatar.generic.CommonLib.getDateAccToTimeZone;
import static com.navatar.generic.EnumConstants.*;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.scripts.Module5;

import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.scripts.FieldSet;
import com.navatar.scripts.Module2;
import com.navatar.scripts.Module4;


/**
 * @author Ankur Rana
 * 	
 */
public class CommonVariables {
//	public static String abc;

	public static String URL,todaysDate,todaysDateSingleDigit;
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
	
	public static String Smoke_HSRContact1FName,Smoke_HSRContact1LName,Smoke_HSRContact1EmailID;
	public static String Smoke_HSRContact2FName,Smoke_HSRContact2LName,Smoke_HSRContact2EmailID;
	public static String Smoke_HSRContact3FName,Smoke_HSRContact3LName,Smoke_HSRContact3EmailID;
	public static String superAdminUserName,superAdminRegistered,adminPassword;
	public static String AdminUserFirstName,AdminUserLastName,AdminUserEmailID;
	public static String crmUser1FirstName,crmUser1LastName,crmUser1EmailID,crmUserProfile,crmUserLience;
	public static String gmailUserName,gmailUserName2,gmailPassword;
	
	public static String tabCustomObj,tabCustomObjField;
	public static String tabObj1,tabObj2,tabObj3,tabObj4,tabObj5,tabObj6,tabObj7;
	
	public static String ToggleIns1,ToggleIns1RecordType;
	public static String M4Ins1,M4Ins1RecordType;
	public static String M4Contact1FName,M4Contact1LName,M4Contact1EmailID,M4Contact1RecordType,M4Contact1Title;
	
	public static String environment,mode;
	public static String TWTask1Subject,TWTask2Subject,TWTask3Subject,TWTask4Subject,TWTask5Subject,TWTask6Subject,TWTask8Subject,TWTaskCR1Subject,TWTaskUpdateLabelSubject;
	public static String ToggleFund1,ToggleFund1Type,ToggleFund1Category,ToggleFund1RecordType;
	public static String ToggleFund2,ToggleFund2Type,ToggleFund2Category,ToggleFund2RecordType;
	public static String ToggleDeal1,ToggleDeal1CompanyName,ToggleDeal1RecordType,ToggleDeal1Stage;
	public static String ToggleOpenQA1ID,ToggleOpenQA1RequestedDate,ToggleOpenQA1Request,ToggleOpenQA1Status;
	public static String ToggleClosedQA1ID,ToggleClosedQA1RequestedDate,ToggleClosedQA1Request,ToggleClosedQA1Status;
	
	public static String ToggleMarketingEvent1Name,ToggleMarketingEvent1Date,ToggleMarketingEvent1RecordType,ToggleMarketingEvent1Organizer;
	public static String M4MarketingEvent1Name,M4MarketingEvent1Date,M4MarketingEvent1RecordType,M4MarketingEvent1Organizer;
	
	public static String FS_MarketingEvent1Name,FS_MarketingEvent1Date,FS_MarketingEvent1RecordType,FS_MarketingEvent1Organizer;
	

	public static String FS_Object1,FS_Object2,FS_Object3,FS_FieldSetLabel1,FS_FieldSetLabel2,FS_FieldSetLabel3,
						FS_NameSpacePrefix1,FS_NameSpacePrefix2,FS_NameSpacePrefix3,FS_FieldsName1,FS_FieldsName2,FS_FieldsName3,FS_ExtraFieldsName1;
	
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
	

	public static String FS_Fund1,FS_Fund1Type,FS_Fund1InvestmentCategory,FS_Fund2,FS_Fund2Type,FS_Fund2InvestmentCategory;
	
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
//	/**
//	 * 
//	 */
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
		todaysDateSingleDigit=getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/YYYY");
		
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

			
			tabCustomObj=ExcelUtils.readDataFromPropertyFile("CustomTabName");
			tabObj1=ExcelUtils.readDataFromPropertyFile("Object1");
			tabObj2=ExcelUtils.readDataFromPropertyFile("Object2");
			tabObj3=ExcelUtils.readDataFromPropertyFile("Object3");
			tabObj4=ExcelUtils.readDataFromPropertyFile("Object4");
			
			SDG = "Sortable Data Grids";
			
			AppDeveloperName="Navatar";
			AppDescription="Navatar Private Equity app – Lightning View(Edge)";
			
			
			//****************************************************************	Toggle Variable **********************************************************//

			
			
		if(obj instanceof Module5){
			
			ToggleIns1=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TOGGLEINS1", excelLabel.Institutions_Name);
			ToggleIns1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TOGGLEINS1", excelLabel.Record_Type);
			
			ToggleFund1=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Fund_Name);
			ToggleFund1Type=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Fund_Type);
			ToggleFund1Category=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Investment_Category);
			ToggleFund1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Record_Type);
			
			ToggleFund2=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Fund_Name);
			ToggleFund2Type=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Fund_Type);
			ToggleFund2Category=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Investment_Category);
			ToggleFund1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Record_Type);
			
			ToggleDeal1=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "TOGGLEDEAL1", excelLabel.Deal_Name);
			ToggleDeal1CompanyName=ToggleIns1;
			ToggleDeal1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "TOGGLEDEAL1", excelLabel.Record_Type);
			ToggleDeal1Stage=ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "TOGGLEDEAL1", excelLabel.Stage);
			
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
			
			
			ToggleMarketingEvent1Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "TOGGLEME1", excelLabel.Marketing_Event_Name);
			ToggleMarketingEvent1Date=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "TOGGLEME1", excelLabel.Date);
			ToggleMarketingEvent1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "TOGGLEME1", excelLabel.Record_Type);
			ToggleMarketingEvent1Organizer=ToggleIns1;
			
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
			
		}else if(obj instanceof FieldSet){
			
			FS_Object1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.Object_Name);
			FS_Object2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.Object_Name);
			FS_Object3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.Object_Name);
			FS_ExtraFieldsName1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.ExtraFieldsName);
			
			FS_FieldSetLabel1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.Field_Set_Label);
			FS_FieldSetLabel2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.Field_Set_Label);
			FS_FieldSetLabel3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.Field_Set_Label);
			
			FS_NameSpacePrefix1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.NameSpace_PreFix);
			FS_NameSpacePrefix2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.NameSpace_PreFix);
			FS_NameSpacePrefix3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.NameSpace_PreFix);
			
			FS_FieldsName1=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.Fields_Name);
			FS_FieldsName2=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.Fields_Name);
			FS_FieldsName3=ExcelUtils.readData(phase1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.Fields_Name);
			
			
			
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
			FS_Fund1InvestmentCategory=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund1", excelLabel.Investment_Category);
			
			
			FS_Fund2=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund2", excelLabel.Fund_Name);
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
			
			//Marketing Event 
			FS_MarketingEvent1Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "ME1", excelLabel.Marketing_Event_Name);
			FS_MarketingEvent1Date=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "ME1", excelLabel.Date);
			FS_MarketingEvent1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "ME1", excelLabel.Record_Type);
			FS_MarketingEvent1Organizer=FS_Ins2;
			
		}
		if(obj instanceof Module4){
			
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
			
			
			M4MarketingEvent1Name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME1", excelLabel.Marketing_Event_Name);
			M4MarketingEvent1Date=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME1", excelLabel.Date);
			M4MarketingEvent1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME1", excelLabel.Record_Type);
			M4MarketingEvent1Organizer=M4Ins1;
			M4Contact1FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON1", excelLabel.Contact_FirstName);
			M4Contact1LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON1", excelLabel.Contact_LastName);
			M4Contact1EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON1", excelLabel.Contact_EmailId);
			M4Contact1RecordType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON1", excelLabel.Record_Type);
			M4Contact1Title=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON1", excelLabel.Title);
			
		}
		 System.err.println("");
		AppListeners.appLog.info("Done with intialization. Enjoy the show.\nTotal Time Taken: "+((System.currentTimeMillis()-StartTime)/1000)+" seconds.");
		
	}
	
}
