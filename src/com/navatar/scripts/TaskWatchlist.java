package com.navatar.scripts;

import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.removeNumbersFromString;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonVariables.Smoke_TWContact1FName;
import static com.navatar.generic.CommonVariables.Smoke_TWContact1LName;
import static com.navatar.generic.CommonVariables.Smoke_TWContact2FName;
import static com.navatar.generic.CommonVariables.Smoke_TWContact2LName;
import static com.navatar.generic.CommonVariables.Smoke_TWContact3FName;
import static com.navatar.generic.CommonVariables.Smoke_TWContact3LName;
import static com.navatar.generic.CommonVariables.Smoke_TWContact4FName;
import static com.navatar.generic.CommonVariables.Smoke_TWContact4LName;
import static com.navatar.generic.CommonVariables.Smoke_TWContact5FName;
import static com.navatar.generic.CommonVariables.Smoke_TWContact5LName;
import static com.navatar.generic.CommonVariables.Smoke_TWContact1RecordType;
import static com.navatar.generic.CommonVariables.Smoke_TWContact2RecordType;
import static com.navatar.generic.CommonVariables.Smoke_TWContact3RecordType;
import static com.navatar.generic.CommonVariables.Smoke_TWContact4RecordType;
import static com.navatar.generic.CommonVariables.Smoke_TWContact5RecordType;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskFund1InvestmentCategory;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskFund1Name;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskFund1RecordType;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskFund1Type;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskFund2InvestmentCategory;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskFund2Name;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskFund2RecordType;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskFund2Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.navatar.generic.CommonVariables.Smoke_TWINS1Name;
import static com.navatar.generic.CommonVariables.Smoke_TWINS1RecordType;
import static com.navatar.generic.CommonVariables.Smoke_TWINS2Name;
import static com.navatar.generic.CommonVariables.Smoke_TWINS2RecordType;
import static com.navatar.generic.CommonVariables.Smoke_TWINS3Name;
import static com.navatar.generic.CommonVariables.Smoke_TWINS3RecordType;
import static com.navatar.generic.CommonVariables.Smoke_TWINS4Name;
import static com.navatar.generic.CommonVariables.Smoke_TWINS4RecordType;
import static com.navatar.generic.CommonVariables.Smoke_TWINS5Name;
import static com.navatar.generic.CommonVariables.Smoke_TWINS5RecordType;
import static com.navatar.generic.CommonVariables.Smoke_TWINS1Status;
import static com.navatar.generic.CommonVariables.Smoke_TWINS2Status;
import static com.navatar.generic.CommonVariables.Smoke_TWINS3Status;
import static com.navatar.generic.CommonVariables.Smoke_TWINS4Status;
import static com.navatar.generic.CommonVariables.Smoke_TWINS5Status;
import static com.navatar.generic.CommonVariables.adminPassword;
import static com.navatar.generic.CommonVariables.appName;
import static com.navatar.generic.CommonVariables.crmUser1EmailID;
import static com.navatar.generic.CommonVariables.crmUser1FirstName;
import static com.navatar.generic.CommonVariables.crmUser1LastName;
import static com.navatar.generic.CommonVariables.crmUserLience;
import static com.navatar.generic.CommonVariables.crmUserProfile;
import static com.navatar.generic.CommonVariables.gmailUserName;
import static com.navatar.generic.CommonVariables.superAdminUserName;
import static com.navatar.generic.CommonVariables.tabCustomObjField;
import static com.navatar.generic.CommonVariables.tabObj1;
import static com.navatar.generic.CommonVariables.tabObj2;
import static com.navatar.generic.CommonVariables.tabObj3;
import static com.navatar.generic.CommonVariables.tabCustomObj;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.ProjectName;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.objectFeatureName;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class TaskWatchlist extends BaseLib{
	@Parameters({ "projectName"})
	@Test
	public void TWtc001_1_CreateCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",excelLabel.User_Last_Name);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		boolean flag = false;
		for (int i = 0; i < 3; i++) {
			try {
				if (home.clickOnSetUpLink()) {
					flag = true;
					parentWindow = switchOnWindow(driver);
					if (parentWindow == null) {
						sa.assertTrue(false,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User1");
						log(LogStatus.SKIP,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User1",
								YesNo.Yes);
						exit("No new window is open after click on setup link in lighting mode so cannot create CRM User1");
					}
					if (setup.createPEUser( crmUser1FirstName, UserLastName, emailId, crmUserLience,
							crmUserProfile)) {
						log(LogStatus.INFO, "CRM User is created Successfully: " + crmUser1FirstName + " " + UserLastName, YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User1",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",
								excelLabel.User_Last_Name);
						flag = true;
						break;

					}
					driver.close();
					driver.switchTo().window(parentWindow);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {

			if (setup.installedPackages(crmUser1FirstName, UserLastName)) {
				appLog.info("PE Package is installed Successfully in CRM User: " + crmUser1FirstName + " "
						+ UserLastName);

			} else {
				appLog.error(
						"Not able to install PE package in CRM User1: " + crmUser1FirstName + " " + UserLastName);
				sa.assertTrue(false,
						"Not able to install PE package in CRM User1: " + crmUser1FirstName + " " + UserLastName);
				log(LogStatus.ERROR,
						"Not able to install PE package in CRM User1: " + crmUser1FirstName + " " + UserLastName,
						YesNo.Yes);
			}

		}else{

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

		}

		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
		String passwordResetLink=null;
		try {
			passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
					ExcelUtils.readDataFromPropertyFile("gmailUserName"),
					ExcelUtils.readDataFromPropertyFile("gmailPassword"));
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		appLog.info("ResetLinkIs: " + passwordResetLink);
		driver.get(passwordResetLink);
		if (lp.setNewPassword()) {
			appLog.info("Password is set successfully for CRM User1: " + crmUser1FirstName + " " + UserLastName );
		} else {
			appLog.info("Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void TWtc001_2_Prerequisite(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		for (String[] userPass : userAndPassword) {
			lp.CRMLogin(userPass[0], userPass[1], appName);


			String addRemoveTabName="";
			String tab1="";
			if (tabObj1.equalsIgnoreCase("Entity")){
				tab1="Entitie";
			}
			else{
				tab1=tabObj1;
			}
			addRemoveTabName=tab1+"s,"+tabObj2+"s,"+tabObj3+"s,"+tabCustomObj+"s,"+"Tasks"+",Recycle Bin"+",Navatar Setup";
			if (lp.addTab_Lighting( addRemoveTabName, 5)) {
				log(LogStatus.INFO,"Tab added : "+addRemoveTabName,YesNo.No);
			} else {
				log(LogStatus.FAIL,"Tab not added : "+addRemoveTabName,YesNo.No);
				sa.assertTrue(false, "Tab not added : "+addRemoveTabName);
			}		



			ThreadSleep(5000);
			lp.CRMlogout();
			closeBrowser();
			config(ExcelUtils.readDataFromPropertyFile("Browser"));
			lp = new LoginPageBusinessLayer(driver);

		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void TWtc001_3_AddListView(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);


		String[] tabs= {tabObj1,tabObj2,tabObj3,tabCustomObj};
		TabName[] tab= {TabName.Object1Tab,TabName.Object2Tab,TabName.Object3Tab,TabName.TestCustomObjectTab};
		int i=0;
		for (TabName t:tab) {

			if (lp.clickOnTab(projectName, t)) {	
				if (lp.addAutomationAllListView(projectName, tabs[i], 10)) {
					log(LogStatus.INFO,"list view added on "+tabs[i],YesNo.No);
				} else {
					log(LogStatus.FAIL,"list view could not added on "+tabs[i],YesNo.Yes);
					sa.assertTrue(false, "list view could not added on "+tabs[i]);
				}
			} else {
				log(LogStatus.FAIL,"could not click on "+tabs[i],YesNo.Yes);
				sa.assertTrue(false, "could not click on "+tabs[i]);
			}
			i++;
			ThreadSleep(5000);
		}


		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void TWtc002_1_CreatePreconditionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String status1=null;
		String[][] EntityOrAccounts = {
				{ Smoke_TWINS1Name, Smoke_TWINS1RecordType ,Smoke_TWINS1Status}
				, {Smoke_TWINS2Name,Smoke_TWINS2RecordType ,Smoke_TWINS2Status},
				{Smoke_TWINS3Name,Smoke_TWINS3RecordType ,Smoke_TWINS3Status},
				{Smoke_TWINS4Name,Smoke_TWINS4RecordType ,Smoke_TWINS4Status}
		};
		for (String[] accounts : EntityOrAccounts) {/*
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				status1=accounts[2];
				if (ip.createEntityOrAccount(projectName, value, type, new String[][] {{PageLabel.Status.toString(),status1}}, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		*/}

		String fname="";
		String lname="";
		String mailID="";
		String ins="";
		String title=null;
		String[][] contactsInfo = { { Smoke_TWContact1FName, Smoke_TWContact1LName, Smoke_TWINS1Name,
			Smoke_TWContact1RecordType,null}
		, {Smoke_TWContact2FName,Smoke_TWContact2LName,Smoke_TWINS1Name,
			"Contact",null},
		{Smoke_TWContact3FName,Smoke_TWContact3LName,Smoke_TWINS2Name,
				"Contact",null},
		{Smoke_TWContact4FName,Smoke_TWContact4LName,Smoke_TWINS2Name,
					"Contact",null},
		{Smoke_TWContact5FName,Smoke_TWContact5LName,Smoke_TWINS3Name,
						"Contact",null}
		};
		int i=1;
		String recType;
		for (String[] contacts : contactsInfo) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
				fname = contacts[0];
				lname = contacts[1];
				ins=contacts[2];
				recType=contacts[3];
				title=contacts[4];
				mailID=	lp.generateRandomEmailId(gmailUserName);
				ExcelUtils.writeData(taskWatchlistFilePath, mailID, "Contacts", excelLabel.Variable_Name, "TWCON"+i,excelLabel.Contact_EmailId);

				if (cp.createContact(projectName, fname, lname, ins, mailID,recType, null, null, CreationPage.ContactPage, title)) {
					log(LogStatus.INFO,"successfully Created Contact : "+fname+" "+lname,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Contact : "+fname+" "+lname);
					log(LogStatus.SKIP,"Not Able to Create Contact: "+fname+" "+lname,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
			}
			i++;
		}

		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void TWtc003_1_AddWatchlistField(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
		String parentID=null;
		String mode="Lightning";
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("Task Layout");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put(PageLabel.Watchlist.toString(), PageLabel.Meeting_Type.toString());
			List<String> abc = setup.DragNDrop("", mode, object.Task, objectFeatureName.pageLayouts, layoutName, sourceANDDestination);
			ThreadSleep(10000);
			if (!abc.isEmpty()) {
				log(LogStatus.FAIL, "field not added/already present 1", YesNo.Yes);
				sa.assertTrue(false, "field not added/already present 1");
			}else{
				log(LogStatus.INFO, "field added/already present 1", YesNo.Yes);
			}
			driver.close();
			driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot add field", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot add field");
			}
		}
		else {
			log(LogStatus.FAIL, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
	
	
	}
	
}
