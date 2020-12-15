/**
 * 
 */
package com.navatar.generic;

import static com.navatar.generic.BaseLib.testCasesFilePath;
import static com.navatar.generic.BaseLib.toggleFilePath;
import static com.navatar.generic.EnumConstants.*;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.scripts.Toggle;

import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.scripts.TaskWatchlist;


/**
 * @author Ankur Rana
 * 	
 */
public class CommonVariables {
//	public static String abc;

	public static String URL;
	public static String browserToLaunch;
	public static String Smoke_TWINS1Name,Smoke_TWINS1RecordType;
	public static String Smoke_TWINS2Name,Smoke_TWINS2RecordType;
	public static String Smoke_TWINS3Name,Smoke_TWINS3RecordType;
	public static String Smoke_TWINS4Name,Smoke_TWINS4RecordType;
	public static String Smoke_TWINS5Name,Smoke_TWINS5RecordType,Smoke_TaskINS5Website;
	public static String Smoke_TWContact1FName,Smoke_TWContact1LName,Smoke_TWContact1EmailID;
	public static String Smoke_TWContact2FName,Smoke_TWContact2LName,Smoke_TWContact2EmailID;
	public static String Smoke_TWContact3FName,Smoke_TWContact3LName,Smoke_TWContact3EmailID;
	public static String Smoke_TWContact4FName,Smoke_TWContact4LName,Smoke_TWContact4EmailID,Smoke_TaskContact4UpdatedName;
	public static String Smoke_TWContact5FName,Smoke_TWContact5LName,Smoke_TWContact5EmailID;
	
	public static String appName;
	public static String superAdminUserName,superAdminRegistered,adminPassword;
	public static String AdminUserFirstName,AdminUserLastName,AdminUserEmailID;
	public static String crmUser1FirstName,crmUser1LastName,crmUser1EmailID,crmUserProfile,crmUserLience;
	public static String gmailUserName,gmailUserName2,gmailPassword;
	
	public static String tabCustomObj,tabCustomObjField;
	public static String tabObj1,tabObj2,tabObj3,tabObj4;
	
	public static String ToggleIns1,ToggleIns1RecordType;
	public static String ToggleFund1,ToggleFund1Type,ToggleFund1Category,ToggleFund1RecordType;
	public static String ToggleFund2,ToggleFund2Type,ToggleFund2Category,ToggleFund2RecordType;
	public static String ToggleDeal1,ToggleDeal1CompanyName,ToggleDeal1RecordType,ToggleDeal1Stage;
	
	
		
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
			
			
			
			
			
		}
		else if(obj instanceof TaskWatchlist){
			Smoke_TWINS1Name=ExcelUtils.readData(testCasesFilePath,"TaskWatchlist",excelLabel.Variable_Name, "TWINS1", excelLabel.Institutions_Name);
			Smoke_TWINS1RecordType=ExcelUtils.readData(testCasesFilePath,"TaskWatchlist",excelLabel.Variable_Name, "TWINS1", excelLabel.Record_Type);

			// TASK INS2..............
			Smoke_TWINS2Name=ExcelUtils.readData(testCasesFilePath,"TaskWatchlist",excelLabel.Variable_Name, "TWINS2", excelLabel.Institutions_Name);
			Smoke_TWINS2RecordType=ExcelUtils.readData(testCasesFilePath,"TaskWatchlist",excelLabel.Variable_Name, "TWINS2", excelLabel.Record_Type);

			// TASK INS3..............
			Smoke_TWINS3Name=ExcelUtils.readData(testCasesFilePath,"TaskWatchlist",excelLabel.Variable_Name, "TWINS3", excelLabel.Institutions_Name);
			Smoke_TWINS3RecordType=ExcelUtils.readData(testCasesFilePath,"TaskWatchlist",excelLabel.Variable_Name, "TWINS3", excelLabel.Record_Type);

			// TASK INS4..............
			Smoke_TWINS4Name=ExcelUtils.readData(testCasesFilePath,"TaskWatchlist",excelLabel.Variable_Name, "TWINS4", excelLabel.Institutions_Name);
			Smoke_TWINS4RecordType=ExcelUtils.readData(testCasesFilePath,"TaskWatchlist",excelLabel.Variable_Name, "TWINS4", excelLabel.Record_Type);

			// TASK INS5..............
			Smoke_TWINS5Name=ExcelUtils.readData(testCasesFilePath,"TaskWatchlist",excelLabel.Variable_Name, "TWINS5", excelLabel.Institutions_Name);
			Smoke_TWINS5RecordType=ExcelUtils.readData(testCasesFilePath,"TaskWatchlist",excelLabel.Variable_Name, "TWINS5", excelLabel.Record_Type);
			Smoke_TaskINS5Website="www.facebook.com";
			Smoke_TWContact1FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC1", excelLabel.Contact_FirstName);
			Smoke_TWContact1LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC1", excelLabel.Contact_LastName);
			Smoke_TWContact1EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC1", excelLabel.Contact_EmailId);
			
			// TASK Contact2..............
			Smoke_TWContact2FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC2", excelLabel.Contact_FirstName);
			Smoke_TWContact2LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC2", excelLabel.Contact_LastName);
			Smoke_TWContact2EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC2", excelLabel.Contact_EmailId);
			
			// TASK Contact3..............
			Smoke_TWContact3FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC3", excelLabel.Contact_FirstName);
			Smoke_TWContact3LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC3", excelLabel.Contact_LastName);
			Smoke_TWContact3EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC3", excelLabel.Contact_EmailId);

			// TASK Contact4..............
			Smoke_TWContact4FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC4", excelLabel.Contact_FirstName);
			Smoke_TWContact4LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC4", excelLabel.Contact_LastName);
			Smoke_TWContact4EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC4", excelLabel.Contact_EmailId);
			
			// TASK Contact5..............
			Smoke_TWContact5FName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC5", excelLabel.Contact_FirstName);
			Smoke_TWContact5LName=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC5", excelLabel.Contact_LastName);
			Smoke_TWContact5EmailID=ExcelUtils.readData(testCasesFilePath,"Contacts",excelLabel.Variable_Name, "AATASKC5", excelLabel.Contact_EmailId);
		
		}
		 System.err.println("");
		AppListeners.appLog.info("Done with intialization. Enjoy the show.\nTotal Time Taken: "+((System.currentTimeMillis()-StartTime)/1000)+" seconds.");
		
	}
	
}
