/**
 * 
 */
package com.navatar.generic;

import static com.navatar.generic.BaseLib.testCasesFilePath;
import static com.navatar.generic.BaseLib.pahse1DataSheetFilePath;
import static com.navatar.generic.CommonLib.getDateAccToTimeZone;
import static com.navatar.generic.EnumConstants.*;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.scripts.Toggle;

import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.scripts.FieldSet;
import com.navatar.scripts.TaskWatchlist;


/**
 * @author Ankur Rana
 * 	
 */
public class CommonVariables {
//	public static String abc;

	public static String URL,todaysDate;
	public static String browserToLaunch;
	public static String Smoke_TWINS1Name,Smoke_TWINS1RecordType,Smoke_TWINS1Status;
	public static String Smoke_CDINS1Name,Smoke_CDINS1RecordType,Smoke_CDINS1Status;
	public static String Smoke_TWINS2Name,Smoke_TWINS2RecordType,Smoke_TWINS2Status;
	public static String Smoke_CDINS2Name,Smoke_CDINS2RecordType,Smoke_CDINS2Status;
	public static String Smoke_TWINS3Name,Smoke_TWINS3RecordType,Smoke_TWINS3Status;
	public static String Smoke_TWINS4Name,Smoke_TWINS4RecordType,Smoke_TWINS4Status;
	public static String Smoke_TWINS5Name,Smoke_TWINS5RecordType,Smoke_TWINS5Status;
	public static String Smoke_TWContact1FName,Smoke_TWContact1LName,Smoke_TWContact1EmailID,Smoke_TWContact1RecordType;
	public static String Smoke_TWContact2FName,Smoke_TWContact2LName,Smoke_TWContact2EmailID,Smoke_TWContact2RecordType;
	public static String Smoke_TWContact3FName,Smoke_TWContact3LName,Smoke_TWContact3EmailID,Smoke_TWContact3RecordType;
	public static String Smoke_TWContact4FName,Smoke_TWContact4LName,Smoke_TWContact4EmailID,Smoke_TaskContact4UpdatedName,Smoke_TWContact4RecordType;
	public static String Smoke_TWContact5FName,Smoke_TWContact5LName,Smoke_TWContact5EmailID,Smoke_TWContact5RecordType;
	
	public static String appName;
	public static String superAdminUserName,superAdminRegistered,adminPassword;
	public static String AdminUserFirstName,AdminUserLastName,AdminUserEmailID;
	public static String crmUser1FirstName,crmUser1LastName,crmUser1EmailID,crmUserProfile,crmUserLience;
	public static String gmailUserName,gmailUserName2,gmailPassword;
	
	public static String tabCustomObj,tabCustomObjField;
	public static String tabObj1,tabObj2,tabObj3,tabObj4;
	
	public static String ToggleIns1,ToggleIns1RecordType;

	public static String environment,mode;
	public static String TWTask1Subject,TWTask2Subject,TWTask3Subject,TWTask4Subject,TWTask5Subject,TWTask6Subject,TWTask8Subject,TWTaskCR1Subject,TWTaskUpdateLabelSubject;
	public static String ToggleFund1,ToggleFund1Type,ToggleFund1Category,ToggleFund1RecordType;
	public static String ToggleFund2,ToggleFund2Type,ToggleFund2Category,ToggleFund2RecordType;
	public static String ToggleDeal1,ToggleDeal1CompanyName,ToggleDeal1RecordType,ToggleDeal1Stage;
	public static String ToggleOpenQA1ID,ToggleOpenQA1RequestedDate,ToggleOpenQA1Request,ToggleOpenQA1Status;
	public static String ToggleClosedQA1ID,ToggleClosedQA1RequestedDate,ToggleClosedQA1Request,ToggleClosedQA1Status;
	
	public static String ToggleMarketingEvent1Name,ToggleMarketingEvent1Date,ToggleMarketingEvent1RecordType,ToggleMarketingEvent1Organizer;
	

	public static String FS_Object1,FS_Object2,FS_Object3,FS_FieldSetLabel1,FS_FieldSetLabel2,FS_FieldSetLabel3,
						FS_NameSpacePrefix1,FS_NameSpacePrefix2,FS_NameSpacePrefix3,FS_FieldsName1,FS_FieldsName2,FS_FieldsName3,FS_ExtraFieldsName1;
	
	public static String FS_Ins1,FS_Ins1RecordType,FS_Ins2,FS_Ins2RecordType,FS_LP1,FS_LP1RecordType;
	public static String FS_Con1_FName,FS_Con1_LName,FS_Con1_Email,FS_Con1_Phone,FS_Con1_RecordType;
	public static String FS_DealName1,FS_Deal1CompanyName,FS_Deal1Stage,FS_Deal1SourceContact,FS_Deal1SourceFirm,FS_Deal1RecordType;
	
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

	public static String FS_Fund1,FS_Fund1Type,FS_Fund1InvestmentCategory,FS_Fund2,FS_Fund2Type,FS_Fund2InvestmentCategory;
	
	public static String FS_PartnerShip1,FS_PartnerShip2;
	
	public static String FS_CommitmentID1,FS_CommitmentAmount1,FS_FinalCommitmentDate1,FS_CommitmentID2,FS_CommitmentAmount2,FS_FinalCommitmentDate2;
	
	
	public static String SDG;
	
	public static String Sdg1Name,Sdg1TagName,Sdg1ObjectName;
	
	public static String ActiveDealToggleButton;
	
	public static String ToggleCheck1TabName,ToggleCheck1ItemName,ToggleCheck1RelatedTab,ToggleCheck1ToggleButtons,ToggleCheck1ColumnName;
	public static String ToggleCheck2TabName,ToggleCheck2ItemName,ToggleCheck2RelatedTab,ToggleCheck2ToggleButtons,ToggleCheck2ColumnName;
	public static String ToggleCheck3TabName,ToggleCheck3ItemName,ToggleCheck3RelatedTab,ToggleCheck3ToggleButtons,ToggleCheck3ColumnName;
		
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
		tabCustomObjField=ExcelUtils.readDataFromPropertyFile("CustomTabFieldName");
		environment=ExcelUtils.readDataFromPropertyFile("Environment");
		mode=ExcelUtils.readDataFromPropertyFile("Mode");
		todaysDate=getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
		
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
			
			//****************************************************************	Toggle Variable **********************************************************//

			
			
		if(obj instanceof Toggle){
			
			ToggleIns1=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TOGGLEINS1", excelLabel.Institutions_Name);
			ToggleIns1RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TOGGLEINS1", excelLabel.Record_Type);
			
			ToggleFund1=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Fund_Name);
			ToggleFund1Type=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Fund_Type);
			ToggleFund1Category=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Investment_Category);
			ToggleFund1RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Record_Type);
			
			ToggleFund2=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Fund_Name);
			ToggleFund2Type=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Fund_Type);
			ToggleFund2Category=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Investment_Category);
			ToggleFund1RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Record_Type);
			
			ToggleDeal1=ExcelUtils.readData(pahse1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "TOGGLEDEAL1", excelLabel.Deal_Name);
			ToggleDeal1CompanyName=ToggleIns1;
			ToggleDeal1RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "TOGGLEDEAL1", excelLabel.Record_Type);
			ToggleDeal1Stage=ExcelUtils.readData(pahse1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "TOGGLEDEAL1", excelLabel.Stage);
			
			Sdg1Name=ExcelUtils.readData(pahse1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.SDG_Name);
			Sdg1TagName=ExcelUtils.readData(pahse1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.SDG_Tag);;
			Sdg1ObjectName=ExcelUtils.readData(pahse1DataSheetFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.sObjectName);
			
			ActiveDealToggleButton = "Active Deals with All Stages !@#$%^&*() @#$%^&*Deals";
			
			// ToggleOpenQA1ID,ToggleOpenQA1RequestedDate,ToggleOpenQA1Request,ToggleOpenQA1Status;
			
			ToggleOpenQA1ID=ExcelUtils.readData(pahse1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "OPENQA1", excelLabel.Request_Tracker_ID);
			ToggleOpenQA1RequestedDate=ExcelUtils.readData(pahse1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "OPENQA1", excelLabel.Date_Requested);;
			ToggleOpenQA1Request=ExcelUtils.readData(pahse1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "OPENQA1", excelLabel.Request);
			ToggleOpenQA1Status=ExcelUtils.readData(pahse1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "OPENQA1", excelLabel.Status);
			
			ToggleClosedQA1ID=ExcelUtils.readData(pahse1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "CLOSEDQA1", excelLabel.Request_Tracker_ID);
			ToggleClosedQA1RequestedDate=ExcelUtils.readData(pahse1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "CLOSEDQA1", excelLabel.Date_Requested);;
			ToggleClosedQA1Request=ExcelUtils.readData(pahse1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "CLOSEDQA1", excelLabel.Request);
			ToggleClosedQA1Status=ExcelUtils.readData(pahse1DataSheetFilePath,"DealRequestTracker",excelLabel.Variable_Name, "CLOSEDQA1", excelLabel.Status);
			
			
			ToggleMarketingEvent1Name=ExcelUtils.readData(pahse1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "TOGGLEME1", excelLabel.Marketing_Event_Name);
			ToggleMarketingEvent1Date=ExcelUtils.readData(pahse1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "TOGGLEME1", excelLabel.Date);
			ToggleMarketingEvent1RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "TOGGLEME1", excelLabel.Record_Type);
			ToggleMarketingEvent1Organizer=ToggleIns1;
			
			// ToggleCheck1TabName,ToggleCheck1ItemName,ToggleCheck1RelatedTab,ToggleCheck1ToggleButtons;
		
			ToggleCheck1TabName=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC1", excelLabel.TabName);
			ToggleCheck1ItemName=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC1", excelLabel.Item);;
			ToggleCheck1RelatedTab=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC1", excelLabel.RelatedTab);
			ToggleCheck1ToggleButtons=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC1", excelLabel.ToggleButton);
			ToggleCheck1ColumnName=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC1", excelLabel.Column_Name);
				
			//ToggleCheck1ColumnName1,ToggleCheck1ColumnName2
			
			ToggleCheck2TabName=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC2", excelLabel.TabName);
			ToggleCheck2ItemName=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC2", excelLabel.Item);;
			ToggleCheck2RelatedTab=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC2", excelLabel.RelatedTab);
			ToggleCheck2ToggleButtons=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC2", excelLabel.ToggleButton);
			ToggleCheck2ColumnName=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC2", excelLabel.Column_Name);
			
			
			ToggleCheck3TabName=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC3", excelLabel.TabName);
			ToggleCheck3ItemName=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC3", excelLabel.Item);;
			ToggleCheck3RelatedTab=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC3", excelLabel.RelatedTab);
			ToggleCheck3ToggleButtons=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC3", excelLabel.ToggleButton);
			ToggleCheck3ColumnName=ExcelUtils.readData(pahse1DataSheetFilePath,"ToggleButtonCheck",excelLabel.Variable_Name, "TBC3", excelLabel.Column_Name);
			
			
		}
		
		else if(obj instanceof TaskWatchlist){
			Smoke_TWINS1Name=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS1", excelLabel.Institutions_Name);
			Smoke_TWINS1RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS1", excelLabel.Record_Type);
			Smoke_TWINS1Status=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS1", excelLabel.Status);
			Smoke_CDINS1Name=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "CDINS1", excelLabel.Institutions_Name);
			Smoke_CDINS1RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "CDINS1", excelLabel.Record_Type);
			
			// TASK INS2..............
			Smoke_TWINS2Name=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS2", excelLabel.Institutions_Name);
			Smoke_TWINS2RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS2", excelLabel.Record_Type);
			Smoke_TWINS2Status=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS2", excelLabel.Status);
			Smoke_CDINS2Name=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "CDINS2", excelLabel.Institutions_Name);
			
			// TASK INS3..............
			Smoke_TWINS3Name=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS3", excelLabel.Institutions_Name);
			Smoke_TWINS3RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS3", excelLabel.Record_Type);
			Smoke_TWINS3Status=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS3", excelLabel.Status);
			
			// TASK INS4..............
			Smoke_TWINS4Name=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS4", excelLabel.Institutions_Name);
			Smoke_TWINS4RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS4", excelLabel.Record_Type);
			Smoke_TWINS4Status=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "TWINS4", excelLabel.Status);
			
			
			// TASK Contact1..............
			Smoke_TWContact1FName=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON1", excelLabel.Contact_FirstName);
			Smoke_TWContact1LName=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON1", excelLabel.Contact_LastName);
			Smoke_TWContact1EmailID=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON1", excelLabel.Contact_EmailId);
			Smoke_TWContact1RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON1", excelLabel.Record_Type);
			
			// TASK Contact2..............
			Smoke_TWContact2FName=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON2", excelLabel.Contact_FirstName);
			Smoke_TWContact2LName=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON2", excelLabel.Contact_LastName);
			Smoke_TWContact2EmailID=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON2", excelLabel.Contact_EmailId);
			Smoke_TWContact2RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON2", excelLabel.Record_Type);
			
			// TASK Contact3..............
			Smoke_TWContact3FName=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON3", excelLabel.Contact_FirstName);
			Smoke_TWContact3LName=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON3", excelLabel.Contact_LastName);
			Smoke_TWContact3EmailID=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON3", excelLabel.Contact_EmailId);
			Smoke_TWContact3RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON3", excelLabel.Record_Type);
			
			// TASK Contact4..............
			Smoke_TWContact4FName=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON4", excelLabel.Contact_FirstName);
			Smoke_TWContact4LName=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON4", excelLabel.Contact_LastName);
			Smoke_TWContact4EmailID=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON4", excelLabel.Contact_EmailId);
			Smoke_TWContact4RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON4", excelLabel.Record_Type);
			
			// TASK Contact5..............
			Smoke_TWContact5FName=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON5", excelLabel.Contact_FirstName);
			Smoke_TWContact5LName=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON5", excelLabel.Contact_LastName);
			Smoke_TWContact5EmailID=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON5", excelLabel.Contact_EmailId);
			Smoke_TWContact5RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "TWCON5", excelLabel.Record_Type);
			TWTask1Subject=ExcelUtils.readData(pahse1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask1", excelLabel.Subject);
			TWTask2Subject=ExcelUtils.readData(pahse1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask2", excelLabel.Subject);
			TWTask3Subject=ExcelUtils.readData(pahse1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask3", excelLabel.Subject);
			TWTask4Subject=ExcelUtils.readData(pahse1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask4", excelLabel.Subject);
			TWTask5Subject=ExcelUtils.readData(pahse1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask5", excelLabel.Subject);
			TWTask6Subject=ExcelUtils.readData(pahse1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask6", excelLabel.Subject);
			TWTask8Subject=ExcelUtils.readData(pahse1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask7", excelLabel.Subject);
			TWTaskCR1Subject=ExcelUtils.readData(pahse1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask8", excelLabel.Subject);
			TWTaskUpdateLabelSubject=ExcelUtils.readData(pahse1DataSheetFilePath,"Task",excelLabel.Variable_Name, "TWTask9", excelLabel.Subject);
			
		}else if(obj instanceof FieldSet){
			
			FS_Object1=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.Object_Name);
			FS_Object2=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.Object_Name);
			FS_Object3=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.Object_Name);
			FS_ExtraFieldsName1=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.ExtraFieldsName);
			
			FS_FieldSetLabel1=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.Field_Set_Label);
			FS_FieldSetLabel2=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.Field_Set_Label);
			FS_FieldSetLabel3=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.Field_Set_Label);
			
			FS_NameSpacePrefix1=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.NameSpace_PreFix);
			FS_NameSpacePrefix2=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.NameSpace_PreFix);
			FS_NameSpacePrefix3=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.NameSpace_PreFix);
			
			FS_FieldsName1=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.Fields_Name);
			FS_FieldsName2=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.Fields_Name);
			FS_FieldsName3=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.Fields_Name);
			
			
			
			FS_Con1_FName=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Contact_FirstName);
			FS_Con1_LName=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Contact_LastName);
			FS_Con1_Email=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Contact_EmailId);
			FS_Con1_Phone=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Phone);
			FS_Con1_RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Record_Type);
			
			FS_Ins1=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "Ins1", excelLabel.Institutions_Name);
			FS_Ins1RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "Ins1", excelLabel.Record_Type);
			
			FS_Ins2=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "Ins2", excelLabel.Institutions_Name);
			FS_Ins2RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "Ins2", excelLabel.Record_Type);
			
			FS_LP1=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "Ins3", excelLabel.Institutions_Name);
			FS_LP1RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "Ins3", excelLabel.Record_Type);
			
			
			FS_Fund1=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund1", excelLabel.Fund_Name);
			FS_Fund1Type=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund1", excelLabel.Fund_Type);
			FS_Fund1InvestmentCategory=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund1", excelLabel.Investment_Category);
			
			
			FS_Fund2=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund2", excelLabel.Fund_Name);
			FS_Fund2Type=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund2", excelLabel.Fund_Type);
			FS_Fund2InvestmentCategory=ExcelUtils.readData(pahse1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "Fund2", excelLabel.Investment_Category);
			
			
			FS_PartnerShip1=ExcelUtils.readData(pahse1DataSheetFilePath,"Partnerships",excelLabel.Variable_Name, "P1", excelLabel.PartnerShip_Name);
			FS_PartnerShip2=ExcelUtils.readData(pahse1DataSheetFilePath,"Partnerships",excelLabel.Variable_Name, "P2", excelLabel.PartnerShip_Name);
			
			
			FS_CommitmentID1=ExcelUtils.readData(pahse1DataSheetFilePath,"Commitments",excelLabel.Variable_Name, "Com1", excelLabel.Commitment_ID);
			FS_CommitmentAmount1=ExcelUtils.readData(pahse1DataSheetFilePath,"Commitments",excelLabel.Variable_Name, "Com1", excelLabel.Commitment_Amount);
			FS_FinalCommitmentDate1=ExcelUtils.readData(pahse1DataSheetFilePath,"Commitments",excelLabel.Variable_Name, "Com1", excelLabel.Final_Commitment_Date);
			
			FS_CommitmentID2=ExcelUtils.readData(pahse1DataSheetFilePath,"Commitments",excelLabel.Variable_Name, "Com2", excelLabel.Commitment_ID);
			FS_CommitmentAmount2=ExcelUtils.readData(pahse1DataSheetFilePath,"Commitments",excelLabel.Variable_Name, "Com2", excelLabel.Commitment_Amount);
			FS_FinalCommitmentDate2=ExcelUtils.readData(pahse1DataSheetFilePath,"Commitments",excelLabel.Variable_Name, "Com2", excelLabel.Final_Commitment_Date);
			
			
			
			FS_DealName1= ExcelUtils.readData(pahse1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "Deal1", excelLabel.Deal_Name);
			FS_Deal1CompanyName=FS_Ins1;
			FS_Deal1Stage=ExcelUtils.readData(pahse1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "Deal1", excelLabel.Stage);
			FS_Deal1SourceFirm=FS_Ins1;
			FS_Deal1RecordType=ExcelUtils.readData(pahse1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "Deal1", excelLabel.Record_Type);
			FS_Deal1SourceContact=ExcelUtils.readData(pahse1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "Deal1", excelLabel.Source_Contact);
			
			FC_Object1=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC1", excelLabel.Object_Name);
			FC_FieldLabelName1=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC1", excelLabel.Field_Label);
			FC_Length1=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC1", excelLabel.Length);
			FC_FieldType1=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC1", excelLabel.Field_Type);
			FC_FieldLabel1SubString=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC1", excelLabel.FieldLabel_SubString);
			
			
			FC_Object2=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC2", excelLabel.Object_Name);
			FC_FieldLabelName2=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC2", excelLabel.Field_Label);
			FC_Length2=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC2", excelLabel.Length);
			FC_FieldType2=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC2", excelLabel.Field_Type);
			
			FC_Object3=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC3", excelLabel.Object_Name);
			FC_FieldLabelName3=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC3", excelLabel.Field_Label);
			FC_Length3=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC3", excelLabel.Length);
			FC_FieldType3=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC3", excelLabel.Field_Type);
			
			FC_Object4=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC4", excelLabel.Object_Name);
			FC_FieldLabelName4=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC4", excelLabel.Field_Label);
			FC_Length4=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC4", excelLabel.Length);
			FC_FieldType4=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC4", excelLabel.Field_Type);
			
			FC_Object5=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC5", excelLabel.Object_Name);
			FC_FieldLabelName5=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC5", excelLabel.Field_Label);
			FC_Length5=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC5", excelLabel.Length);
			FC_FieldType5=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC5", excelLabel.Field_Type);
			
			FC_Object6=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC6", excelLabel.Object_Name);
			FC_FieldLabelName6=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC6", excelLabel.Field_Label);
			FC_Length6=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC6", excelLabel.Length);
			FC_FieldType6=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC6", excelLabel.Field_Type);
			
			FC_Object7=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC7", excelLabel.Object_Name);
			FC_FieldLabelName7=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC7", excelLabel.Field_Label);
			FC_Length7=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC7", excelLabel.Length);
			FC_FieldType7=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC7", excelLabel.Field_Type);
			
			FC_Object8=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC8", excelLabel.Object_Name);
			FC_FieldLabelName8=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC8", excelLabel.Field_Label);
			FC_Length8=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC8", excelLabel.Length);
			FC_FieldType8=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC8", excelLabel.Field_Type);
			
			FC_Object9=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC9", excelLabel.Object_Name);
			FC_FieldLabelName9=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC9", excelLabel.Field_Label);
			FC_Length9=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC9", excelLabel.Length);
			FC_FieldType9=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC9", excelLabel.Field_Type);
			
			FC_Object10=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC10", excelLabel.Object_Name);
			FC_FieldLabelName10=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC10", excelLabel.Field_Label);
			FC_Length10=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC10", excelLabel.Length);
			FC_FieldType10=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC10", excelLabel.Field_Type);
			
			FC_Object11=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC11", excelLabel.Object_Name);
			FC_FieldLabelName11=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC11", excelLabel.Field_Label);
			FC_Length11=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC11", excelLabel.Length);
			FC_FieldType11=ExcelUtils.readData(pahse1DataSheetFilePath,"FieldComponent",excelLabel.Variable_Name, "FC11", excelLabel.Field_Type);
			
		}
		 System.err.println("");
		AppListeners.appLog.info("Done with intialization. Enjoy the show.\nTotal Time Taken: "+((System.currentTimeMillis()-StartTime)/1000)+" seconds.");
		
	}
	
}
