/**
 * 
 */
package com.navatar.generic;

import static com.navatar.generic.BaseLib.testCasesFilePath;
import static com.navatar.generic.BaseLib.toggleFilePath;
import static com.navatar.generic.CommonLib.getDateAccToTimeZone;
import static com.navatar.generic.BaseLib.taskWatchlistFilePath;
import static com.navatar.generic.BaseLib.fieldSetFilePath;
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
	public static String Smoke_TWINS2Name,Smoke_TWINS2RecordType,Smoke_TWINS2Status;
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
	public static String TWTask1Subject,TWTask2Subject,TWTask3Subject,TWTask4Subject,TWTask5Subject,TWTask6Subject,TWTask8Subject,TWTaskCR1Subject;
	public static String ToggleFund1,ToggleFund1Type,ToggleFund1Category,ToggleFund1RecordType;
	public static String ToggleFund2,ToggleFund2Type,ToggleFund2Category,ToggleFund2RecordType;
	public static String ToggleDeal1,ToggleDeal1CompanyName,ToggleDeal1RecordType,ToggleDeal1Stage;
	

	public static String FS_Object1,FS_Object2,FS_Object3,FS_FieldSetLabel1,FS_FieldSetLabel2,FS_FieldSetLabel3,
						FS_NameSpacePrefix1,FS_NameSpacePrefix2,FS_NameSpacePrefix3,FS_FieldsName1,FS_FieldsName2,FS_FieldsName3;
	
	public static String FS_Ins1;
	public static String FS_Con1_FName,FS_Con1_LastName,FS_Con1_Email,FS_Con1_Phone;
	public static String FS_DealName1,FS_DealStage1,FS_DealSourceContact;
	

	public static String SDG;
	
	public static String Sdg1Name,Sdg1TagName,Sdg1ObjectName;
		
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
			
			ToggleIns1=ExcelUtils.readData(toggleFilePath,"EntityorAccount",excelLabel.Variable_Name, "TOGGLEINS1", excelLabel.Institutions_Name);
			ToggleIns1RecordType=ExcelUtils.readData(toggleFilePath,"EntityorAccount",excelLabel.Variable_Name, "TOGGLEINS1", excelLabel.Record_Type);
			
			ToggleFund1=ExcelUtils.readData(toggleFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Fund_Name);
			ToggleFund1Type=ExcelUtils.readData(toggleFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Fund_Type);
			ToggleFund1Category=ExcelUtils.readData(toggleFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Fund_InvestmentCategory);
			ToggleFund1RecordType=ExcelUtils.readData(toggleFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND1", excelLabel.Record_Type);
			
			ToggleFund2=ExcelUtils.readData(toggleFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Fund_Name);
			ToggleFund2Type=ExcelUtils.readData(toggleFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Fund_Type);
			ToggleFund2Category=ExcelUtils.readData(toggleFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Fund_InvestmentCategory);
			ToggleFund1RecordType=ExcelUtils.readData(toggleFilePath,"Fund",excelLabel.Variable_Name, "TOGGLEFUND2", excelLabel.Record_Type);
			
			ToggleDeal1=ExcelUtils.readData(toggleFilePath,"Deal",excelLabel.Variable_Name, "TOGGLEDEAL1", excelLabel.Deal_Name);
			ToggleDeal1CompanyName=ToggleIns1;
			ToggleDeal1RecordType=ExcelUtils.readData(toggleFilePath,"Deal",excelLabel.Variable_Name, "TOGGLEDEAL1", excelLabel.Record_Type);
			ToggleDeal1Stage=ExcelUtils.readData(toggleFilePath,"Deal",excelLabel.Variable_Name, "TOGGLEDEAL1", excelLabel.Stage);
			
			Sdg1Name=ExcelUtils.readData(toggleFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.SDG_Name);
			Sdg1TagName=ExcelUtils.readData(toggleFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.SDG_Tag);;
			Sdg1ObjectName=ExcelUtils.readData(toggleFilePath,"CustomSDG",excelLabel.Variable_Name, "TOGGLESDG1", excelLabel.sObjectName);
			
			
			
			
			
		}
		else if(obj instanceof TaskWatchlist){
			Smoke_TWINS1Name=ExcelUtils.readData(taskWatchlistFilePath,"EntityorAccount",excelLabel.Variable_Name, "TWINS1", excelLabel.Institutions_Name);
			Smoke_TWINS1RecordType=ExcelUtils.readData(taskWatchlistFilePath,"EntityorAccount",excelLabel.Variable_Name, "TWINS1", excelLabel.Record_Type);
			Smoke_TWINS1Status=ExcelUtils.readData(taskWatchlistFilePath,"EntityorAccount",excelLabel.Variable_Name, "TWINS1", excelLabel.Status);
			
			// TASK INS2..............
			Smoke_TWINS2Name=ExcelUtils.readData(taskWatchlistFilePath,"EntityorAccount",excelLabel.Variable_Name, "TWINS2", excelLabel.Institutions_Name);
			Smoke_TWINS2RecordType=ExcelUtils.readData(taskWatchlistFilePath,"EntityorAccount",excelLabel.Variable_Name, "TWINS2", excelLabel.Record_Type);
			Smoke_TWINS2Status=ExcelUtils.readData(taskWatchlistFilePath,"EntityorAccount",excelLabel.Variable_Name, "TWINS2", excelLabel.Status);
			
			// TASK INS3..............
			Smoke_TWINS3Name=ExcelUtils.readData(taskWatchlistFilePath,"EntityorAccount",excelLabel.Variable_Name, "TWINS3", excelLabel.Institutions_Name);
			Smoke_TWINS3RecordType=ExcelUtils.readData(taskWatchlistFilePath,"EntityorAccount",excelLabel.Variable_Name, "TWINS3", excelLabel.Record_Type);
			Smoke_TWINS3Status=ExcelUtils.readData(taskWatchlistFilePath,"EntityorAccount",excelLabel.Variable_Name, "TWINS3", excelLabel.Status);
			
			// TASK INS4..............
			Smoke_TWINS4Name=ExcelUtils.readData(taskWatchlistFilePath,"EntityorAccount",excelLabel.Variable_Name, "TWINS4", excelLabel.Institutions_Name);
			Smoke_TWINS4RecordType=ExcelUtils.readData(taskWatchlistFilePath,"EntityorAccount",excelLabel.Variable_Name, "TWINS4", excelLabel.Record_Type);
			Smoke_TWINS4Status=ExcelUtils.readData(taskWatchlistFilePath,"EntityorAccount",excelLabel.Variable_Name, "TWINS4", excelLabel.Status);
			
			// TASK Contact1..............
			Smoke_TWContact1FName=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON1", excelLabel.Contact_FirstName);
			Smoke_TWContact1LName=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON1", excelLabel.Contact_LastName);
			Smoke_TWContact1EmailID=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON1", excelLabel.Contact_EmailId);
			Smoke_TWContact1RecordType=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON1", excelLabel.Record_Type);
			
			// TASK Contact2..............
			Smoke_TWContact2FName=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON2", excelLabel.Contact_FirstName);
			Smoke_TWContact2LName=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON2", excelLabel.Contact_LastName);
			Smoke_TWContact2EmailID=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON2", excelLabel.Contact_EmailId);
			Smoke_TWContact2RecordType=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON2", excelLabel.Record_Type);
			
			// TASK Contact3..............
			Smoke_TWContact3FName=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON3", excelLabel.Contact_FirstName);
			Smoke_TWContact3LName=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON3", excelLabel.Contact_LastName);
			Smoke_TWContact3EmailID=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON3", excelLabel.Contact_EmailId);
			Smoke_TWContact3RecordType=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON3", excelLabel.Record_Type);
			
			// TASK Contact4..............
			Smoke_TWContact4FName=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON4", excelLabel.Contact_FirstName);
			Smoke_TWContact4LName=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON4", excelLabel.Contact_LastName);
			Smoke_TWContact4EmailID=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON4", excelLabel.Contact_EmailId);
			Smoke_TWContact4RecordType=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON4", excelLabel.Record_Type);
			
			// TASK Contact5..............
			Smoke_TWContact5FName=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON5", excelLabel.Contact_FirstName);
			Smoke_TWContact5LName=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON5", excelLabel.Contact_LastName);
			Smoke_TWContact5EmailID=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON5", excelLabel.Contact_EmailId);
			Smoke_TWContact5RecordType=ExcelUtils.readData(taskWatchlistFilePath,"Contacts",excelLabel.Variable_Name, "TWCON5", excelLabel.Record_Type);
			TWTask1Subject=ExcelUtils.readData(taskWatchlistFilePath,"Task",excelLabel.Variable_Name, "TWTask1", excelLabel.Subject);
			TWTask2Subject=ExcelUtils.readData(taskWatchlistFilePath,"Task",excelLabel.Variable_Name, "TWTask2", excelLabel.Subject);
			TWTask3Subject=ExcelUtils.readData(taskWatchlistFilePath,"Task",excelLabel.Variable_Name, "TWTask3", excelLabel.Subject);
			TWTask4Subject=ExcelUtils.readData(taskWatchlistFilePath,"Task",excelLabel.Variable_Name, "TWTask4", excelLabel.Subject);
			TWTask5Subject=ExcelUtils.readData(taskWatchlistFilePath,"Task",excelLabel.Variable_Name, "TWTask5", excelLabel.Subject);
			TWTask6Subject=ExcelUtils.readData(taskWatchlistFilePath,"Task",excelLabel.Variable_Name, "TWTask6", excelLabel.Subject);
			TWTask8Subject=ExcelUtils.readData(taskWatchlistFilePath,"Task",excelLabel.Variable_Name, "TWTask7", excelLabel.Subject);
			TWTaskCR1Subject=ExcelUtils.readData(taskWatchlistFilePath,"Task",excelLabel.Variable_Name, "TWTask8", excelLabel.Subject);
			
		}else if(obj instanceof FieldSet){
			
			FS_Object1=ExcelUtils.readData(fieldSetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.Object_Name);
			FS_Object2=ExcelUtils.readData(fieldSetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.Object_Name);
			FS_Object3=ExcelUtils.readData(fieldSetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.Object_Name);
			
			FS_FieldSetLabel1=ExcelUtils.readData(fieldSetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.Field_Set_Label);
			FS_FieldSetLabel2=ExcelUtils.readData(fieldSetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.Field_Set_Label);
			FS_FieldSetLabel3=ExcelUtils.readData(fieldSetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.Field_Set_Label);
			
			FS_NameSpacePrefix1=ExcelUtils.readData(fieldSetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.NameSpace_PreFix);
			FS_NameSpacePrefix2=ExcelUtils.readData(fieldSetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.NameSpace_PreFix);
			FS_NameSpacePrefix3=ExcelUtils.readData(fieldSetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.NameSpace_PreFix);
			
			FS_FieldsName1=ExcelUtils.readData(fieldSetFilePath,"FieldSet",excelLabel.Variable_Name, "FS1", excelLabel.Fields_Name);
			FS_FieldsName2=ExcelUtils.readData(fieldSetFilePath,"FieldSet",excelLabel.Variable_Name, "FS2", excelLabel.Fields_Name);
			FS_FieldsName3=ExcelUtils.readData(fieldSetFilePath,"FieldSet",excelLabel.Variable_Name, "FS3", excelLabel.Fields_Name);
			
			
			FS_Con1_FName=ExcelUtils.readData(fieldSetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Contact_FirstName);
			FS_Con1_LastName=ExcelUtils.readData(fieldSetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Contact_LastName);
			FS_Con1_Email=ExcelUtils.readData(fieldSetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Contact_EmailId);
			FS_Con1_Phone=ExcelUtils.readData(fieldSetFilePath,"Contacts",excelLabel.Variable_Name, "C1", excelLabel.Phone);
			
			FS_Ins1=ExcelUtils.readData(fieldSetFilePath,"Entities",excelLabel.Variable_Name, "Ins1", excelLabel.Institutions_Name);
			
			FS_DealName1= ExcelUtils.readData(fieldSetFilePath,"Deal",excelLabel.Variable_Name, "Deal1", excelLabel.Deal_Name);
			FS_DealStage1=ExcelUtils.readData(fieldSetFilePath,"Deal",excelLabel.Variable_Name, "Deal1", excelLabel.Stage);
			FS_DealSourceContact=ExcelUtils.readData(fieldSetFilePath,"Deal",excelLabel.Variable_Name, "Deal1", excelLabel.Source_Firm);
			
		}
		 System.err.println("");
		AppListeners.appLog.info("Done with intialization. Enjoy the show.\nTotal Time Taken: "+((System.currentTimeMillis()-StartTime)/1000)+" seconds.");
		
	}
	
}
