/*package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.SmokeCommonVariables.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.remote.server.handler.SwitchToWindow;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.android.dx.gen.Local;
import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.ActivityTypes;
import com.navatar.generic.EnumConstants.AddProspectsTab;
import com.navatar.generic.EnumConstants.AddressAction;
import com.navatar.generic.EnumConstants.CheckBox;
import com.navatar.generic.EnumConstants.ClickOrCheckEnableDisableCheckBox;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTab;
import com.navatar.generic.EnumConstants.OfficeLocationLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.RelatedList;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.objectFeatureName;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavatarSetUpPageErrorMessage;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.navatar.pageObjects.TaskPagePageErrorMessage;
import com.relevantcodes.extentreports.LogStatus;

import bsh.org.objectweb.asm.Label;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.BaseLib.testCasesFilePath;
import static com.navatar.generic.CommonLib.*;
public class SmokeTestCases extends BaseLib {

	String passwordResetLink = null;

	Scanner scn = new Scanner(System.in);
	// uncheck in Session settings Enable secure and persistent browser caching to improve performance
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc001_createCRMUser(String projectName) {
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
	public void AASmokeTc002_1_Prerequisite(String projectName) {
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
	public void AASmokeTc002_2_AddListView(String projectName) {
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
	public void AASmokeTc003_1_CreatePreconditionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String website=null;
		String[][] EntityOrAccounts = {
				{ Smoke_TaskINS1Name, Smoke_TaskINS1RecordType ,null}
				, {Smoke_TaskINS2Name,Smoke_TaskINS2RecordType ,null},
				{Smoke_TaskINS3Name,Smoke_TaskINS3RecordType ,null},
				{Smoke_TaskINS4Name,Smoke_TaskINS4RecordType ,null},
				{Smoke_TaskINS5Name,Smoke_TaskINS5RecordType ,Smoke_TaskINS5Website},
		};
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				website=accounts[2];
				if (ip.createEntityOrAccount(projectName, value, type, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}

		String fname="";
		String lname="";
		String mailID="";
		String ins="";
		String title=null;
		String[][] contactsInfo = { { Smoke_TaskContact1FName, Smoke_TaskContact1LName, Smoke_TaskINS1Name,
			Smoke_TaskContact1RecordType,null}
		, {Smoke_TaskContact2FName,Smoke_TaskContact2LName,Smoke_TaskINS1Name,
			Smoke_TaskContact2RecordType,null},
		{Smoke_TaskContact3FName,Smoke_TaskContact3LName,Smoke_TaskINS2Name,
				Smoke_TaskContact3RecordType,null},
		{Smoke_TaskContact4FName,Smoke_TaskContact4LName,Smoke_TaskINS2Name,
					Smoke_TaskContact4RecordType,Smoke_TaskContact4Title},
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
				ExcelUtils.writeData(testCasesFilePath, mailID, "Contacts", excelLabel.Variable_Name, "AATASKC"+i,excelLabel.Contact_EmailId);

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

		String[][] fundsOrDeals = {{Smoke_TaskFund1Name,Smoke_TaskFund1Type,Smoke_TaskFund1InvestmentCategory,Smoke_TaskFund1RecordType},
				{Smoke_TaskFund2Name,Smoke_TaskFund2Type,Smoke_TaskFund2InvestmentCategory,Smoke_TaskFund2RecordType}};
		for (String[] funds : fundsOrDeals) {
			if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object3Tab,YesNo.No);	

				if (ProjectName.MNA.toString().equals(projectName)) {

					if (fp.createDealMNA(projectName,funds[3],funds[0], "Prospect", "Prospect",null, 15)) {
						log(LogStatus.INFO,"Created Fund/Deal : "+funds[0],YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Fund/Deal  : "+funds[0]);
						log(LogStatus.SKIP,"Not Able to Create Fund/Deal  : "+funds[0],YesNo.Yes);
					}

				} else {

					if (fp.createFundPE(projectName, funds[0], funds[3], funds[1], funds[2], null, 15)) {
						log(LogStatus.INFO,"Created Fund/Deal : "+funds[0],YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Fund/Deal  : "+funds[0]);
						log(LogStatus.SKIP,"Not Able to Create Fund/Deal  : "+funds[0],YesNo.Yes);
					}
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
			}
		}
		String[][] customObjValues = {{taskCustomObj1Name,taskCustomObj1RecordType},{taskCustomObj2Name,taskCustomObj2RecordType}};
		for (String[] val : customObjValues) {
			if (lp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.TestCustomObjectTab,YesNo.No);	


				if (cop.createRecord(projectName, val[1], tabCustomObjField, val[0], false)) {
					log(LogStatus.INFO,"successfully Created custom record : "+val[0],YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create custom record  : "+val[0]);
					log(LogStatus.SKIP,"Not Able to Create custom record  : "+val[0],YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab,YesNo.Yes);
			}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc003_2_createDataRelaedToMeeting(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String value="";
		String type="";
		String[][] EntityOrAccounts = {
				{ Smoke_MTINS1Name, Smoke_MTINS1RecordType }
				, {Smoke_MTINS2Name,Smoke_MTINS2RecordType},
				{Smoke_MTINS3Name,Smoke_MTINS3RecordType},
				{Smoke_MTINS4Name,Smoke_MTINS4RecordType},
				{Smoke_MTINS5Name,Smoke_MTINS5RecordType},
				{Smoke_MTINS8Name,Smoke_MTINS8RecordType},
				{Smoke_MTINS9Name,Smoke_MTINS9RecordType},
				{Smoke_WATCHINS1Name,Smoke_WATCHINS1RecordType},
				{Smoke_WATCHINS2Name,Smoke_WATCHINS2RecordType}
		};
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	


				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, value, type, null,  15)) {
					log(LogStatus.INFO,"Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}
		
		String[][] EntityOrAccounts1 = {
				
				{Smoke_MTINS6Name,Smoke_MTINS6RecordType,Smoke_MTINS6Status},
				{Smoke_MTINS7Name,Smoke_MTINS7RecordType,Smoke_MTINS7Status}
		};

			String status1;
			for (String[] accounts : EntityOrAccounts1) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				 
				value = accounts[0];
				type = accounts[1];
				status1=accounts[2];
				if (ip.createEntityOrAccount(projectName, value, type, new String[][] {{PageLabel.Status.toString(),status1}},  15)) {
					log(LogStatus.INFO,"Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}
		

		String fname="";
		String lname="";
		String mailID="";
		String ins="";
		String recordType="";
		String[][] contactsInfo = { { Smoke_MTContact1FName, Smoke_MTContact1LName, Smoke_MTContact1INSName,
			Smoke_MTContact1EmailID ,Smoke_MTContact1RecordType}
		, {Smoke_MTContact2FName,Smoke_MTContact2LName,Smoke_MTContact2INSName,
			Smoke_MTContact2EmailID,Smoke_MTContact2RecordType},
		{Smoke_MTContact3FName,Smoke_MTContact3LName,Smoke_MTContact3INSName,
			Smoke_MTContact3EmailID,Smoke_MTContact3RecordType},
		{Smoke_MTContact4FName,Smoke_MTContact4LName,Smoke_MTContact4INSName,
			Smoke_MTContact4EmailID,Smoke_MTContact4RecordType},
		{Smoke_MTContact5FName,Smoke_MTContact5LName,Smoke_MTContact5INSName,
			Smoke_MTContact5EmailID,Smoke_MTContact5RecordType},
		{Smoke_WATCHContact1FName,Smoke_WATCHContact1LName,Smoke_WATCHContact1INSName,
			Smoke_MTContact5EmailID,Smoke_WATCHContact1RecordType},
		{Smoke_WATCHContact2FName,Smoke_WATCHContact2LName,Smoke_WATCHContact2INSName,
			Smoke_MTContact5EmailID,Smoke_WATCHContact2RecordType},
		{Smoke_WATCHContact3FName,Smoke_WATCHContact3LName,Smoke_WATCHContact3INSName,
			Smoke_MTContact5EmailID,Smoke_WATCHContact3RecordType}
		};
		int i=1,k=1;
		for (String[] contacts : contactsInfo) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
				fname = contacts[0];
				lname = contacts[1];
				ins=contacts[2];
				recordType=contacts[4];
				mailID=	lp.generateRandomEmailId(gmailUserName);
				if (k<=5)
				ExcelUtils.writeData(testCasesFilePath, mailID, "Contacts", excelLabel.Variable_Name, "AAMTC"+i,excelLabel.Contact_EmailId);
				else
					ExcelUtils.writeData(testCasesFilePath, mailID, "Contacts", excelLabel.Variable_Name, "WATCHCON"+i,excelLabel.Contact_EmailId);
					
				if (cp.createContact(projectName, fname, lname, ins, mailID,recordType, null, 15)) {
					log(LogStatus.INFO,"Created Contact : "+fname+" "+lname,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Contact : "+fname+" "+lname);
					log(LogStatus.SKIP,"Not Able to Create Contact: "+fname+" "+lname,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
			}
			if (k==5)
				i=0;
			i++;
			k++;
		}


		String[][] fundsOrDeals = {{Smoke_MTFund1Name,Smoke_MTFund1Type,Smoke_MTFund1InvestmentCategory,Smoke_MTFund1RecordType},
				{Smoke_MTFund2Name,Smoke_MTFund2Type,Smoke_MTFund2InvestmentCategory,Smoke_MTFund2RecordType}};
		for (String[] funds : fundsOrDeals) {
			if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object3Tab,YesNo.No);	

				if (ProjectName.MNA.toString().equals(projectName)) {

					if (fp.createDealMNA(projectName, "Sell-side Deal",funds[0], "Prospect", "Prospect",null, 15)) {
						log(LogStatus.INFO,"Created Fund/Deal : "+funds[0],YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Fund/Deal  : "+funds[0]);
						log(LogStatus.SKIP,"Not Able to Create Fund/Deal  : "+funds[0],YesNo.Yes);
					}

				} else {

					if (fp.createFundPE(projectName, funds[0], funds[3], funds[1], funds[2], null, 15)) {
						log(LogStatus.INFO,"Not Able to Create Fund/Deal : "+funds[0],YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Fund/Deal  : "+funds[0]);
						log(LogStatus.SKIP,"Not Able to Create Fund/Deal  : "+funds[0],YesNo.Yes);
					}

				}



			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
			}
		}


		String[][] customObjValues = {{meetingCustomObj1Name,meetingCustomObj1RecordType},{meetingCustomObj2Name,meetingCustomObj2RecordType},{WATCHCustomObj1Name,WATCHCustomObj1RecordType}};
		for (String[] val : customObjValues) {
			if (lp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.TestCustomObjectTab,YesNo.No);	

				if (cop.createRecord(projectName, val[1], tabCustomObjField, val[0], false)) {
					log(LogStatus.INFO,"successfully Created custom record : "+val[0],YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create custom record  : "+val[0]);
					log(LogStatus.SKIP,"Not Able to Create custom record  : "+val[0],YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
			}
		}




		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc004_VerifyActivitiesTimeline(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		TabName tabName[]= {TabName.Object1Tab,TabName.Object2Tab,TabName.Object3Tab,TabName.TestCustomObjectTab};
		PageName pName[]= {PageName.Object1Page,PageName.Object2Page,PageName.Object3Page,PageName.TestCustomObjectPage,};
		String records[]= {Smoke_TaskINS1Name,Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName,Smoke_TaskFund1Name,taskCustomObj1Name};
		for (int i=0;i<tabName.length;i++) {
			if (ip.clickOnTab(projectName, tabName[i])) {
				if (ip.clickOnAlreadyCreatedItem(projectName, tabName[i], records[i], 10)) {
					if ( ip.getRelatedTab(projectName, PageName.Object1Page, RelatedTab.Meetings, 5)==null) {
						log(LogStatus.INFO, "successfully verified absence of meetings tab", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "meetings tab is present but it should not be", YesNo.Yes);
						sa.assertTrue(false,"meetings tab is present but it should not be");
					}
					if ( ip.getRelatedTab(projectName, PageName.Object1Page, RelatedTab.Activities, 5)==null) {
						log(LogStatus.INFO, "successfully verified absence of Activities tab", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "Activities tab is present but it should not be", YesNo.Yes);
						sa.assertTrue(false,"Activities tab is present but it should not be");
					}

					if ( lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, 10)!=null) {
						log(LogStatus.INFO, "successfully verified log a call button on activity timeline", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify log a call button on activity timeline", YesNo.Yes);
						sa.assertTrue(false, "could not verify log a call button on activity timeline");
					}
					clickUsingJavaScript(driver, lp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.SCROLLANDBOOLEAN);

					if ( lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Task_with_Multiple_Associations, 10)!=null) {
						log(LogStatus.INFO, "successfully verified New_Task_with_Multiple button on activity timeline", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify New_Task_with_Multiple button on activity timeline", YesNo.Yes);
						sa.assertTrue(false, "could not verify New_Task_with_Multiple button on activity timeline");
					}
					clickUsingJavaScript(driver, lp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.SCROLLANDBOOLEAN);

					if (lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Meeting, 10)!=null) {
						log(LogStatus.INFO, "successfully verified New_Meeting button on activity timeline", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify New_Meeting button on activity timeline", YesNo.Yes);
						sa.assertTrue(false, "could not verify New_Meeting button on activity timeline");
					}
					clickUsingJavaScript(driver, lp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.SCROLLANDBOOLEAN);

					WebElement ele=null;
					if (lp.getnextStepsHead(projectName, 10)!=null) {
						log(LogStatus.INFO, "successfully verified next steps heading on activity timeline", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify next steps heading button on activity timeline", YesNo.Yes);
						sa.assertTrue(false, "could not verify next steps heading button on activity timeline");
					}
					if (lp.getpastActivitiesHead(projectName, 10)!=null) {
						log(LogStatus.INFO, "successfully verified past activities heading on activity timeline", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify past activities heading on activity timeline", YesNo.Yes);
						sa.assertTrue(false, "could not verify past activities heading on activity timeline");
					}
					ele=lp.getnextStepsMessage(projectName, 10);
					if (ele!=null) {
						if (ele.getText().trim().contains(BasePageErrorMessage.nextStepsMessage1) && 
								ele.getText().trim().contains(BasePageErrorMessage.nextStepsMessage2) && 
								ele.getText().trim().contains(BasePageErrorMessage.nextStepsMessage3)) {
							log(LogStatus.INFO, "successfully verified next step message on activity timeline", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify next step message on activity timeline", YesNo.Yes);
							sa.assertTrue(false, "could not verify next step message on activity timeline");
						}
					}else {
						log(LogStatus.ERROR, "could not find next step message on activity timeline", YesNo.Yes);
						sa.assertTrue(false, "could not find next step message on activity timeline");
					}
					ele=lp.getpastActivitiesMessage(projectName, 10);
					if (ele!=null) {
						if (ele.getText().trim().contains(BasePageErrorMessage.pastActivityMessage1) && 
								ele.getText().trim().contains(BasePageErrorMessage.pastActivityMessage2)&&
								ele.getText().trim().contains(BasePageErrorMessage.pastActivityMessage3)&& 
								ele.getText().trim().contains(BasePageErrorMessage.pastActivityMessage4)&& 
								ele.getText().trim().contains(BasePageErrorMessage.pastActivityMessage5)) {
							log(LogStatus.INFO, "successfully verified past activities message on activity timeline", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify past activities message on activity timeline", YesNo.Yes);
							sa.assertTrue(false, "could not verify past activities message on activity timeline");
						}
					}else {
						log(LogStatus.ERROR, "could not find past activities message on activity timeline", YesNo.Yes);
						sa.assertTrue(false, "could not find past activities message on activity timeline");
					}
					if (lp.moreStepsBtn(projectName, EnableDisable.Disable, 10)!=null) {
						log(LogStatus.INFO, "successfully verified more steps button on activity timeline", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify more steps button on activity timeline", YesNo.Yes);
						sa.assertTrue(false, "could not verify more steps button on activity timeline");
					}
					if (lp.loadMorePastActivitiesBtn(projectName, EnableDisable.Disable, 10)!=null) {
						log(LogStatus.INFO, "successfully verified loadMorePastActivities button on activity timeline", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verifyloadMorePastActivities button on activity timeline", YesNo.Yes);
						sa.assertTrue(false, "could not verify loadMorePastActivities button on activity timeline");
					}
					if (!lp.clickOnShowMoreActionDownArrow(projectName, pName[i], ShowMoreActionDropDownList.LogCaLLWithMultiple, 5)) {
						log(LogStatus.INFO, "successfully verified absence of LogCaLLWithMultiple tab", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "LogCaLLWithMultiple tab is present but it should not be", YesNo.Yes);
						sa.assertTrue(false,"LogCaLLWithMultiple tab is present but it should not be");
					}
					if (!lp.clickOnShowMoreActionDownArrow(projectName, pName[i], ShowMoreActionDropDownList.New_Meeting, 5)) {
						log(LogStatus.INFO, "successfully verified absence of New_Meeting tab", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "New_Meeting tab is present but it should not be", YesNo.Yes);
						sa.assertTrue(false,"New_Meeting tab is present but it should not be");
					}
					if (!lp.clickOnShowMoreActionDownArrow(projectName, pName[i], ShowMoreActionDropDownList.NewTaskWithMultiple, 5)) {
						log(LogStatus.INFO, "successfully verified absence of NewTaskWithMultiple tab", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "NewTaskWithMultiple tab is present but it should not be", YesNo.Yes);
						sa.assertTrue(false,"NewTaskWithMultiple tab is present but it should not be");
					}
				}else {
					log(LogStatus.ERROR, "could not click on "+Smoke_TaskINS1Name, YesNo.Yes);
					sa.assertTrue(false,"could not click on "+Smoke_TaskINS1Name);
				}
			}
			else {
				log(LogStatus.ERROR, "could not click on tab "+tabName[i], YesNo.Yes);
				sa.assertTrue(false,"could not click on tab "+tabName[i]);
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc005_VerifyUIOfActivities(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String parentId="";
		String range="All Time,Today,Last 7 Days,Last 30 Days,Last 60 Days,Last 90 Days,Year To Date";
		String columns=TaskPageFields.Date.toString()+","+
				TaskPageFields.Name.toString()+","+
				TaskPageFields.Contact_Name.toString()+","+
				TaskPageFields.Related_To.toString()+","+
				TaskPageFields.Status.toString()+","+
				TaskPageFields.Owner.toString()+","+
				PageLabel.Meeting_Type.toString()+","+
				TaskPageFields.Activity.toString()+","+
				TaskPageFields.Comments.toString()+","+
				TaskPageFields.Date.toString();
		columns=columns.replace("_", " ");
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS1Name, 15)) {
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentId=switchOnWindow(driver);
					if (parentId!=null) {
						if (ip.getwrenchIcon(projectName, 20)!=null) {

							log(LogStatus.INFO,"successfully verified wrenchIcon in task", YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not verify wrenchIcon in task", YesNo.Yes);
							sa.assertTrue(false,"could not verify wrenchIcon in task");
						}
						if (ip.getrefreshButton(projectName, 20)!=null) {

							log(LogStatus.INFO,"successfully verified refreshButton in task", YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not verify refreshButton in task", YesNo.Yes);
							sa.assertTrue(false,"could not verify refreshButton in task");
						}
						if (ip.getadvancedFilterImg(projectName, 20)!=null) {
							log(LogStatus.INFO,"successfully verified advancedFilter in task window", YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not verify advancedFilter in task window", YesNo.Yes);
							sa.assertTrue(false,"could not verify advancedFilter in task window");
						}
						if (ip.getrangeDropdown(projectName, 20)!=null) {

							log(LogStatus.INFO,"successfully verified rangeDropdown in task window", YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not verify rangeDropdown in task window", YesNo.Yes);
							sa.assertTrue(false,"could not verify rangeDropdown in task window");
						}
						if (isSelected(driver, ip.getactivitiesCheckbox(projectName, 10), "activities checkbox")) {
							log(LogStatus.INFO,"successfully verified checked activities checkbox in task window", YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not verify checked activities checkbox in task window", YesNo.Yes);
							sa.assertTrue(false,"could not verify checked activities checkbox in task window");
						}
						if (!isSelected(driver, ip.getattachmentCheckbox(projectName, 10), "attachmentCheckbox")){
							log(LogStatus.INFO,"attachmentCheckbox unchecked as expected", YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"attachmentCheckbox is checked but it should not be", YesNo.Yes);
							sa.assertTrue(false,"attachmentCheckbox is checked but it should not be");
						}
						if (ip.verifyAllColumnsInTask(projectName, columns)) {
							log(LogStatus.INFO,"successfully verified all columns in task window", YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not verify all columns in task window", YesNo.Yes);
							sa.assertTrue(false,"could not verify all columns in task window");
						}
						List<String> s=compareMultipleList(driver, range, allOptionsInDropDrop(driver, ip.getrangeDropdown(projectName, 10), "range dropdown"));
						if (s.isEmpty()) {
							log(LogStatus.INFO,"successfully verified all dropdown", YesNo.No);
						}
						else {
							for (String str:s) {
								log(LogStatus.ERROR, "not found in dropdown: "+str,YesNo.Yes);
								sa.assertTrue(false,  "not found in dropdown: "+str);
							}
						}
						scrollDownThroughWebelement(driver, ip.getsearchTextboxActivities(projectName, 20), "search box");
						ThreadSleep(3000);
						WebElement ele=BaseLib.edriver.findElement(By.cssSelector("#searchiconid"));
						clickUsingJavaScript(edriver, ele, "search");
						ThreadSleep(3000);
						if (isAlertPresent(driver)) {
							String ms=switchToAlertAndGetMessage(driver, 10, action.GETTEXT);
							switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
							if (ms.contains(BasePageErrorMessage.alertMsgWithoutEnteringValue)) {
								log(LogStatus.INFO, "successfully verified empty search error message",  YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "not verified empty search error message"+ms,YesNo.Yes);
								sa.assertTrue(false,  "not verified empty search error message"+ms);
							}
						}
						else {
							log(LogStatus.ERROR, "alert not present",YesNo.Yes);
							sa.assertTrue(false,  "alert not present");
						}

						if (sendKeys(driver, ip.getsearchTextboxActivities(projectName, 20), "a", "search textbox", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(3000);
							ele=BaseLib.edriver.findElement(By.cssSelector("#searchiconid"));
							clickUsingJavaScript(edriver, ele, "search");
							ThreadSleep(3000);
							if (isAlertPresent(driver)) {
								String ms=switchToAlertAndGetMessage(driver, 10, action.GETTEXT);
								switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
								if (ms.contains(BasePageErrorMessage.lessThanTwoChars)) {
									log(LogStatus.INFO, "successfully verified 2 chars error message",  YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "not verified 2 chars error message"+ms,YesNo.Yes);
									sa.assertTrue(false,  "not verified 2 chars error message"+ms);
								}
							}
							else {
								log(LogStatus.ERROR, "alert not present",YesNo.Yes);
								sa.assertTrue(false,  "alert not present");
							}

							if (click(driver, ip.getsearchCrossActivities(projectName, 20), "cross icon", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "successfully clicked search cross icom",  YesNo.No);
								String text=getText(driver,  ip.getsearchTextboxActivities(projectName, 20), "search textbox", action.BOOLEAN);
								if (text.equalsIgnoreCase("")) {
									log(LogStatus.INFO, "successfully verified empty search textbox",  YesNo.No);
									
								}else {
									log(LogStatus.ERROR, "could not verify empty search textbox. found: "+text,YesNo.Yes);
									sa.assertTrue(false, "could not verify empty search textbox. found: "+text);
								}
							}else {
								log(LogStatus.ERROR, "search cross not clickable",YesNo.Yes);
								sa.assertTrue(false,  "search cross not clickable");
							}
						}
						driver.close();
						driver.switchTo().window(parentId);
					}else {
						log(LogStatus.ERROR,"could not find new window to switch", YesNo.Yes);
						sa.assertTrue(false,"could not find new window to switch");
					}
				}else {
					log(LogStatus.ERROR,"could not click on load past activities", YesNo.Yes);
					sa.assertTrue(false,"could not click on load past activities");
				}
			}
			else {
				log(LogStatus.ERROR,"account/entity not found: "+Smoke_TaskINS1Name, YesNo.Yes);
				sa.assertTrue(false,"account/entity not found: "+Smoke_TaskINS1Name);
			}
		}
		else {
			log(LogStatus.ERROR,"account/entity tab not clickable ", YesNo.Yes);
			sa.assertTrue(false,"account/entity tab not clickable ");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc006_VerifyUIOfActivitiesColumnsToDisplay(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String columns=TaskPageFields.Date.toString()+","+
				TaskPageFields.Name.toString()+","+
				"Name ID,"+
				"Related To ID,"+
				TaskPageFields.Status.toString()+","+
				TaskPageFields.Owner.toString()+","+
				PageLabel.Meeting_Type.toString()+","+
				TaskPageFields.Activity.toString();
		columns=columns.replace("_", " ");
		String[] selectedGrid=columns.split(",");
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS1Name, 20)) {
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					String parentId=switchOnWindow(driver);
					if (parentId!=null) {
					if (click(driver, ip.getwrenchIcon(projectName, 20), "columns to display", action.SCROLLANDBOOLEAN)) {
						
						if (ip.getsearchTextboxColToDisplay(projectName, 20)!=null) {
							log(LogStatus.INFO, "successfully found searchTextbox", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "not found searchTextbox", YesNo.Yes);
							sa.assertTrue(false, "not found searchTextbox");
						}
						if (ip.getCancelButtonOnColumnsToDisplay(projectName, 20)!=null) {
							log(LogStatus.INFO, "successfully found CancelButton", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "not found CancelButton", YesNo.Yes);
							sa.assertTrue(false, "not found CancelButton");
						}
						
						if (ip.getrevertToDefaultColToDisplay(projectName, EnableDisable.Disable, 10)!=null) {
							log(LogStatus.INFO, "successfully found revert Disable", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "not found revert Disable", YesNo.Yes);
							sa.assertTrue(false, "not found revert Disable");
						}
						if (ip.getApplyColToDisplay(projectName, EnableDisable.Disable, 10)!=null) {
							log(LogStatus.INFO, "successfully found apply Disable", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "not found apply Disable", YesNo.Yes);
							sa.assertTrue(false, "not found apply Disable");
						}
						
						if (ip.getMoveUpColToDisplay(projectName, EnableDisable.Disable, 10)!=null) {
							log(LogStatus.INFO, "successfully found MoveUp Disable", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "not found MoveUp Disable", YesNo.Yes);
							sa.assertTrue(false, "not found MoveUp Disable");
						}
						if (ip.getMoveDownColToDisplay(projectName, EnableDisable.Disable, 10)!=null) {
							log(LogStatus.INFO, "successfully found MoveDown Disable", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "not found MoveDown Disable", YesNo.Yes);
							sa.assertTrue(false, "not found MoveDown Disable");
						}
						if (ip.getMoveTopColToDisplay(projectName, EnableDisable.Disable, 10)!=null) {
							log(LogStatus.INFO, "successfully found MoveTop Disable", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "not found MoveTop Disable", YesNo.Yes);
							sa.assertTrue(false, "not found MoveTop Disable");
						}
						if (ip.getMoveBottomColToDisplay(projectName, EnableDisable.Disable, 10)!=null) {
							log(LogStatus.INFO, "successfully found MoveBottom Disable", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "not found MoveBottom Disable", YesNo.Yes);
							sa.assertTrue(false, "not found MoveBottom Disable");
						}
						for (int i = 0;i<selectedGrid.length;i++) {
							if (ip.verifySelectedColumnInColumnsToDisplayGrid(projectName,PageName.Object1Page, selectedGrid[i], 20)) {
								log(LogStatus.INFO, "successfully found "+selectedGrid[i], YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "not found "+selectedGrid[i], YesNo.Yes);
								sa.assertTrue(false, "not found "+selectedGrid[i]);
							}
						}
						if (click(driver, ip.getsearchIconColToDisplay(projectName, 20), "search icon", action.SCROLLANDBOOLEAN)) {
							if (getText(driver, ip.getpleaseEnterAValueMsg(projectName, 10), "please enter a valuye", action.BOOLEAN)
									.contains(BasePageErrorMessage.alertMsgWithoutEnteringValue)) {
								log(LogStatus.INFO, "successfully verified enter a value error message", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify enter a value error message", YesNo.Yes);
								sa.assertTrue(false, "could not verify enter a value error message");
							}
						}
						else {
							log(LogStatus.ERROR, "search icon not clickable", YesNo.Yes);
							sa.assertTrue(false, "search icon not clickable");
						}

						click(driver, ip.getCancelButtonOnColumnsToDisplay(projectName, 20), "cancel", action.BOOLEAN);
					}
					else {
						log(LogStatus.ERROR, "could not click wrench columns to display", YesNo.Yes);
						sa.assertTrue(false, "could not click wrench columns to display");
					}
					if (click(driver, ip.getwrenchIcon(projectName, 20), "columns to display", action.SCROLLANDBOOLEAN)) {
						click(driver, ip.getcrossIconColToDisplay(projectName, 20), "columns to display cross icon", action.SCROLLANDBOOLEAN);
						ThreadSleep(3000);
						if (ip.getcrossIconColToDisplay(projectName, 5)==null) {
							log(LogStatus.ERROR, "successfully verified closing of column to display", YesNo.Yes);
							
						}else {
							log(LogStatus.ERROR, "after clicking cross icon, column to display is still open", YesNo.Yes);
							sa.assertTrue(false, "after clicking cross icon, column to display is still open");
						}
					}
					else {
						log(LogStatus.ERROR, "could not click wrench columns to display", YesNo.Yes);
						sa.assertTrue(false, "could not click wrench columns to display");
					}
					driver.close();
					driver.switchTo().window(parentId);
					}
					else {
						log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
						sa.assertTrue(false, "could not find new window to switch");
					}
				}
				else {
					log(LogStatus.ERROR, "could not click load more activities, so cannot verify activities", YesNo.Yes);
					sa.assertTrue(false, "could not click load more activities, so cannot verify activities");
				}
			}
			else {
				log(LogStatus.ERROR, "could not find entity "+Smoke_TaskINS1Name, YesNo.Yes);
				sa.assertTrue(false, "could not find entity "+Smoke_TaskINS1Name);
			}
		}
		else {
			log(LogStatus.ERROR, "entity or account tab not clickable", YesNo.Yes);
			sa.assertTrue(false, "entity or account tab not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc007_VerifyUIOfActivitiesAdvancedFilter(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String[] dd= {"Owner","Status","Type"};
		String ownerDD= "All Activities,My Activities";
		String typeDD="Tasks and Events,Tasks,Events";
		String statusDD="Completed activities,Open and Completed activities,Open activities";
		String[] allElements= {ownerDD,statusDD,typeDD};
		String filterDD="Field,Operator,Value";
		WebElement dropdown=null;
		String current=null;
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS1Name, 20)) {
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					String parentId=switchOnWindow(driver);
					if (parentId!=null) {
						if (click(driver, ip.getadvancedFilterToggle(projectName, 20), "advanced filter toggle link", action.SCROLLANDBOOLEAN)) {
							for (String drop:dd) {
								if (ip.getAdvancedFilteDropdowns(projectName, drop, 20)!=null) {
									log(LogStatus.INFO, "successfully verified dropdown "+drop+" on advanced filter", YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "dropdown "+drop+" not found on advanced filter", YesNo.No);
									sa.assertTrue(false,"dropdown "+drop+" not found on advanced filter");
								}
							}
							for (int i=0;i<dd.length;i++) {
								dropdown=ip.getAdvancedFilteDropdowns(projectName, dd[i], 10);
								scrollDownThroughWebelement(driver, dropdown ,dd[i]+" dd");
								current=allElements[i].split(",")[0];
								if (getSelectedOptionOfDropDown(driver, dropdown, dd[i]+" dd", "text").trim().contains(current)) {
									log(LogStatus.INFO, "successfully verified currently selected option "+current+" of "+dd[i], YesNo.No);
								}
								else {
									log(LogStatus.ERROR,  "could not verify currently selected option "+current+" of "+dd[i], YesNo.No);
									sa.assertTrue(false, "could not verify currently selected option "+current+"of "+dd[i]);
								}
								List<WebElement> e=allOptionsInDropDrop(driver,dropdown, dd[i]+" dd");
								List<String> com=compareMultipleList(driver,allElements[i], e);
								if (com.isEmpty()) {
									log(LogStatus.INFO, "successfully verified all options in owner dropdown",YesNo.No);
								}
								else {
									for (String s:com) {
										log(LogStatus.ERROR, "dropdown element "+s+"not found in "+dd[i], YesNo.No);
										sa.assertTrue(false, "dropdown element "+s+"not found in "+dd[i]);
									}
								}
							}
							if (ip.getaddRowFilter(projectName, 10)!=null) {
								log(LogStatus.INFO, "successfully verified add Row link", YesNo.No);
							}
							else {
								log(LogStatus.ERROR,  "could not verify add Row link", YesNo.Yes);
								sa.assertTrue(false, "could not verify add Row link");
							}
							if (ip.getfilterLogicLink(projectName, 10)!=null) {
								log(LogStatus.INFO, "successfully verified filterLogic link", YesNo.No);
							}
							else {
								log(LogStatus.ERROR,  "could not verify filterLogic link", YesNo.Yes);
								sa.assertTrue(false, "could not verify filterLogic link");
							}

							if (ip.clearApplyButtonOnAdvancedFilter(projectName, "clear",10)!=null) {
								log(LogStatus.INFO, "successfully verified filter clear link", YesNo.No);
							}
							else {
								log(LogStatus.ERROR,  "could not verify filter clear link", YesNo.Yes);
								sa.assertTrue(false, "could not verify filter clear link");
							}

							if (ip.clearApplyButtonOnAdvancedFilter(projectName,"apply", 10)!=null) {
								log(LogStatus.INFO, "successfully verified filter apply link", YesNo.No);
							}
							else {
								log(LogStatus.ERROR,  "could not verify filter apply link", YesNo.Yes);
								sa.assertTrue(false, "could not verify filter apply link");
							}
							if (ip.getsearchActAttach(projectName, 10)!=null) {
								log(LogStatus.INFO, "successfully verified search activities/attachment", YesNo.No);
							}
							else {
								log(LogStatus.ERROR,  "could not verify search activities/attachment", YesNo.Yes);
								sa.assertTrue(false, "could not verify search activities/attachment");
							}
							if (compareMultipleList(driver, filterDD, ip.getfilterHeadings(projectName,10 )).isEmpty()) {
								log(LogStatus.INFO, "successfully verified filter headings", YesNo.No);
							}
							else {
								log(LogStatus.ERROR,  "could not verify filter headings", YesNo.Yes);
								sa.assertTrue(false, "could not verify filter headings");
							}
							scrollDownThroughWebelement(driver,  ip.getsearchActAttach(projectName, 10),  "search activities/attachment");
							if (click(driver, ip.getsearchActAttach(projectName, 10), "search activities/attachment", action.SCROLLANDBOOLEAN)) {
								if (click(driver, ip.getinsuffCrossButton(projectName, 10), "cross", action.BOOLEAN)) {
									log(LogStatus.INFO, "successfully closed filter search activities/attachment", YesNo.No);
								}
								else {
									log(LogStatus.ERROR,  "could not close filter search activities/attachment", YesNo.Yes);
									sa.assertTrue(false, "could not close filter search activities/attachment");
								}
							}
							else {
								log(LogStatus.ERROR,  "could not click search activities/attachment", YesNo.Yes);
								sa.assertTrue(false, "could not click search activities/attachment");
							}
							scrollDownThroughWebelement(driver,  ip.getsearchActAttach(projectName, 10),  "search activities/attachment");

							if (click(driver, ip.getsearchActAttach(projectName, 10), "search activities/attachment", action.SCROLLANDBOOLEAN)) {
								if (ip.getinsufficientErrorMsg(projectName, 10).getText().trim().contains(BasePageErrorMessage.insufficientPopup1) &&
										ip.getinsufficientErrorMsg(projectName, 10).getText().trim().contains(BasePageErrorMessage.insufficientPopup2)) {
									log(LogStatus.INFO, "successfully verified insufficient error message", YesNo.No);
								}
								else {
									log(LogStatus.ERROR,  "could not verify insufficient error messag", YesNo.Yes);
									sa.assertTrue(false, "could not verify insufficient error messag");
								}
								if (click(driver, ip.getinsuffOKButton(projectName, 10), "ok", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "successfully closed filter search activities/attachment", YesNo.No);
								}
								else {
									log(LogStatus.ERROR,  "could not close filter search activities/attachment", YesNo.Yes);
									sa.assertTrue(false, "could not close filter search activities/attachment");
								}
							}
							else {
								log(LogStatus.ERROR,  "could not click search activities/attachment", YesNo.Yes);
								sa.assertTrue(false, "could not click search activities/attachment");
							}
						}else {
							log(LogStatus.ERROR,  "could not click on advanced filter toggle link", YesNo.Yes);
							sa.assertTrue(false, "could not click on advanced filter toggle link");
						}
						driver.close();
						driver.switchTo().window(parentId);
					}else {
						log(LogStatus.ERROR,  "could not find new window to switch", YesNo.Yes);
						sa.assertTrue(false, "could not find new window to switch");
					}
				}
				else {
					log(LogStatus.ERROR,  "could not click on load more activities button", YesNo.Yes);
					sa.assertTrue(false, "could not click on load more activities button");
				}
			}
			else  {
				log(LogStatus.ERROR,  "could not click on created entity/account", YesNo.Yes);
				sa.assertTrue(false, "could not click on created entity/account");
			}

		}
		else {
			log(LogStatus.ERROR,  "entity/account tab not clickable", YesNo.Yes);
			sa.assertTrue(false, "entity/account tab not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc008_1_verifyErrorMessageCreatingNewTaskMultipleAssociation(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ActivityRelatedLabel[] fields= {ActivityRelatedLabel.Assigned_To,ActivityRelatedLabel.Status,ActivityRelatedLabel.Priority,ActivityRelatedLabel.Subject};
		ActivityTimeLineItem atli[]= {ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations,ActivityTimeLineItem.New_Task_with_Multiple_Associations};
		TabName tabName[]= {TabName.Object2Tab,TabName.Object1Tab};
		PageName pageName[]= {PageName.Object2Page,PageName.Object1Page};
		String records[]= {Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName,Smoke_TaskINS1Name};
		for (int i=0;i<tabName.length;i++) {
			if (lp.clickOnTab(projectName,tabName[i])) {
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName[i],records[i] , 10)) {
					WebElement ele=cp.getActivityTimeLineItem(projectName,pageName[i],atli[i] , 10);
					if (clickUsingJavaScript(driver, ele, atli[i].toString(), action.BOOLEAN)) {
						ele=ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20);
						for (int j = 0;j<4;j++) {
							sendKeys(driver,ele , Keys.BACK_SPACE+"", "subject", action.SCROLLANDBOOLEAN);
						}
							
						ele=ip.geDropdownOnTaskPopUp(projectName, PageLabel.Priority.toString(), action.SCROLLANDBOOLEAN, 10);
						if (click(driver, ele, "Drop Down : "+PageLabel.Priority.toString(),action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on "+PageLabel.Priority.toString()+" Drop Down", YesNo.Yes);	
							ThreadSleep(2000);

							if (ip.SelectDropDownValue(projectName, pageName[i], PageLabel.Priority.toString(), "--None--", action.SCROLLANDBOOLEAN, 10)) {
								log(LogStatus.INFO, "Selected : "+Smoke_Task3Priority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);	
								ThreadSleep(1000);

							} else {
								log(LogStatus.ERROR, "Not ABle to Select : "+Smoke_Task3Priority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);
								sa.assertTrue(false,"Not ABle to Select : "+Smoke_Task3Priority+" For Label : "+PageLabel.Priority.toString() );
							}
						}
						ele=ip.getstatusDropdownInCreateNewTask(projectName, 20);
						if (click(driver, ele, "Drop Down : "+PageLabel.Status.toString(),action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on "+PageLabel.Status.toString()+" Drop Down", YesNo.Yes);	
							ThreadSleep(2000);

							if (ip.SelectDropDownValue(projectName, pageName[i], PageLabel.Status.toString(), "--None--", action.SCROLLANDBOOLEAN, 10)) {
								log(LogStatus.INFO, "Selected : None For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
								ThreadSleep(1000);

							} else {
								log(LogStatus.ERROR, "Not ABle to Select :  For Label : "+PageLabel.Status.toString(), YesNo.Yes);
								sa.assertTrue(false,"Not ABle to Select :  For Label : "+PageLabel.Status.toString() );
							}
						}
						ele=ip.getCrossButtonForAlreadySelectedItem(projectName, pageName[i],ActivityRelatedLabel.Assigned_To.toString()
								, false, crmUser1FirstName+" "+crmUser1LastName, action.SCROLLANDBOOLEAN, 10);
						scrollDownThroughWebelement(driver,ele, "cross");
						if (clickUsingJavaScript(driver, ele,"cross", action.SCROLLANDBOOLEAN)) {
							if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName, 10), "custom tab save", action.SCROLLANDBOOLEAN)) {
								for (ActivityRelatedLabel f:fields) {
									ele=ip.returnErrorMessageBelowField(projectName, f, 10);
									if (ele!=null) {
										scrollDownThroughWebelement(driver, ele, "error message");
										String s=ele.getText().trim();
										if (s.contains(BasePageBusinessLayer.incomepleteField)) {
											log(LogStatus.INFO, "successfully found incomplete error message for "+f, YesNo.No);
										}
										else {
											log(LogStatus.ERROR,  "could not verify incomplete error message for "+f, YesNo.Yes);
											sa.assertTrue(false, "could not verify incomplete error message for "+f);
										}
									}
									else {
										log(LogStatus.ERROR,  "incomplete error message not present for "+f, YesNo.Yes);
										sa.assertTrue(false, "incomplete error message not present for "+f);
									}
								}
							}
							else {
								log(LogStatus.ERROR,  "could not click save btn", YesNo.Yes);
								sa.assertTrue(false, "could not click save btn");
							}
							String labelWithComma = PageLabel.Assigned_To.toString()+", "+PageLabel.Status.toString()+", "+PageLabel.Subject.toString()+", "+PageLabel.Priority.toString();
							ip.pageErrorOnTaskPopUp(projectName, pageName[i], RelatedTab.Activities, labelWithComma, 10);
					
							if (click(driver, cp.getcrossIcon(projectName, 10), "new task/call cross icon", action.SCROLLANDBOOLEAN)) {

							}else {
								log(LogStatus.ERROR,  "could not click cross btn for new task/call", YesNo.Yes);
								sa.assertTrue(false, "could not click cross btn for new task/call");
							}
						}
						else {
							log(LogStatus.ERROR,  "could not click cross btn for assigned to", YesNo.Yes);
							sa.assertTrue(false, "could not click cross btn for assigned to");
						}
					}else {
						log(LogStatus.ERROR,  "could not click on "+atli[i], YesNo.Yes);
						sa.assertTrue(false, "could not click on "+atli[i]);
					}
				}
				else {
					log(LogStatus.ERROR,  "could not click on "+records[i], YesNo.Yes);
					sa.assertTrue(false, "could not click on "+records[i]);
				}
			}
			else {
				log(LogStatus.ERROR,  "could not click on tab "+tabName[i], YesNo.Yes);
				sa.assertTrue(false, "could not click on tab "+tabName[i]);
			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc008_2_AddMeetingTypeField(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String parentID=null;
		String mode="Lightning";
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("Task Layout");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put(PageLabel.Meeting_Type.toString(), PageLabel.Assigned_To.toString());
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
		lp.switchToLighting();
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc008_3_AddDueDataAndStatusFieldGlobalActionLogCall(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String parentID=null;
		String mode="Lightning";
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("Log a Call");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put(PageLabel.Due_Date.toString(),PageLabel.Subject.toString());
			sourceANDDestination.put(PageLabel.Status.toString(),PageLabel.Subject.toString());
			
			List<String> abc = setup.DragNDrop("", mode, object.Global_Actions, objectFeatureName.pageLayouts, layoutName, sourceANDDestination);
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
				log(LogStatus.FAIL, "could not find new window to switch, so cannot add fields", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot add fields");
			}
		}else {
			log(LogStatus.FAIL, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
		
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("New Task");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put(PageLabel.Comments.toString(),PageLabel.Subject.toString());
			sourceANDDestination.put(PageLabel.Priority.toString(),PageLabel.Subject.toString());
			sourceANDDestination.put(PageLabel.Meeting_Type.toString(),PageLabel.Subject.toString());
			
			List<String> abc = setup.DragNDrop("", mode, object.Global_Actions, objectFeatureName.pageLayouts, layoutName, sourceANDDestination);
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
				log(LogStatus.FAIL, "could not find new window to switch, so cannot add fields", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot add fields");
			}
		}else {
			log(LogStatus.FAIL, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
		
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("New Event");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put(PageLabel.Description.toString(),PageLabel.Subject.toString());
			
			List<String> abc = setup.DragNDrop("", mode, object.Global_Actions, objectFeatureName.pageLayouts, layoutName, sourceANDDestination);
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
				log(LogStatus.FAIL, "could not find new window to switch, so cannot add fields", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot add fields");
			}
		}else {
			log(LogStatus.FAIL, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc009_verifyNewTaskMultipleAssociation_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String[] taskUIdata= {"","--None--",Smoke_TaskINS1Name,"","",Smoke_Task2Priority,"",Status.Not_Started.toString()};
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String date=todaysDate;
		
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab)+","+ip.getTabName(projectName, TabName.TestCustomObjectTab);
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS1Name, 20)) {
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {
					if (ip.verifyUIOfCreateNewTaskWindow(projectName,crmUser1FirstName+" "+crmUser1LastName,taskUIdata,false)) {
						log(LogStatus.INFO, "successfully verified create new task ui", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify create new task ui", YesNo.Yes);
						sa.assertTrue(false, "could not verify create new task ui");
					}
					if (clickUsingJavaScript(driver, ip.getcrossIcon(projectName, 20), "cross")) {
						if (ip.getcancelButton(projectName, 5)==null) {
							log(LogStatus.INFO, "new task window is successfully closed", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "new task window is not closed", YesNo.Yes);
							sa.assertTrue(false,   "new task window is not closed");
						}
					}
					else {
						log(LogStatus.ERROR, "could not click on cross icon", YesNo.Yes);
						sa.assertTrue(false,  "could not click on cross icon");
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,   "could not click on new task button");
				}
				clickUsingJavaScript(driver, lp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.SCROLLANDBOOLEAN);

				ThreadSleep(2000);
				
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {
					if (clickUsingJavaScript(driver, ip.getcancelButton(projectName, 20),  "cancel button")) {
						if (ip.getcancelButton(projectName, 10)==null) {
							log(LogStatus.INFO, "new task window is successfully closed", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "new task window is not closed", YesNo.Yes);
							sa.assertTrue(false,   "new task window is not closed");
						}
					}
					else {
						log(LogStatus.ERROR, "could not click on cross icon", YesNo.Yes);
						sa.assertTrue(false,   "could not click on cross icon");
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,   "could not click on new task button");
				}
				clickUsingJavaScript(driver, lp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.SCROLLANDBOOLEAN);

				ThreadSleep(2000);
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {
					scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
					if (clickUsingJavaScript(driver, ip.getrelatedAssociationsdropdownButton(projectName, 10), "dropdown button for related associations")) {
						List<String> l=compareMultipleListContainsByTitle(driver, tabs, ip.listOfObjectsInRelatedAssctions(projectName));
						if (l.isEmpty()) {
							log(LogStatus.INFO, "successfully verified presence of all tabs "+tabs, YesNo.No);
						}
						else {
							for (String a:l) {
								log(LogStatus.ERROR,"not found "+a,  YesNo.Yes);
								sa.assertTrue(false, "not found "+a);
							}
						}
						l.clear();
						l=compareMultipleListContainsByTitle(driver,ip.getTabName(projectName, TabName.Object2Tab).toString(), ip.listOfObjectsInRelatedAssctions(projectName));
						if (l.isEmpty()) {
							log(LogStatus.ERROR, "tab "+TabName.Object2Tab.toString()+" is present but should not be", YesNo.Yes);
							sa.assertTrue(false, "tab "+TabName.Object2Tab.toString()+" is present but should not be");
						}
						else {
							for (String a:l) {
								log(LogStatus.INFO,"not found "+a+" as expected",  YesNo.Yes);

							}
						}
						//3
						boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.SKIP,"Selected "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name,YesNo.Yes);

						}

						//remove icon
						if (click(driver, ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), true, Smoke_TaskINS1Name, action.SCROLLANDBOOLEAN, 10), "remove button", action.BOOLEAN)) {
							log(LogStatus.SKIP,"clicked on remove icon",YesNo.No);

						}
						else {
							log(LogStatus.ERROR, "remove Button is not clickable", YesNo.Yes);
							sa.assertTrue(false, "remove Button is not clickable");
						}
						flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.Object1Tab, Smoke_TaskINS3Name, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.SKIP,"Selected "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						ThreadSleep(3000);
						flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.Object3Tab, Smoke_TaskFund1Name, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.SKIP,"Selected "+Smoke_TaskFund1Name+" For Label "+PageLabel.Name,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+Smoke_TaskFund1Name+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskFund1Name+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, taskCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.SKIP,"Selected "+taskCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+taskCustomObj1Name+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+taskCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}

						if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), Smoke_Task2Subject, "Subject", action.SCROLLANDBOOLEAN)) {
							if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), todaysDate, "due date", action.SCROLLANDBOOLEAN)) {
								if (sendKeys(driver, ip.getcommentsTextBox(projectName, 10), Smoke_Task2Comment, "comments", action.SCROLLANDBOOLEAN)) {
									if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
									}
									else {
										log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
										sa.assertTrue(false,"save button is not clickable so task not created" );
									}
									ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask2", excelLabel.Due_Date);
								}
								else {
									log(LogStatus.ERROR, "comments textbox is not visible so task could not be created", YesNo.Yes);
									sa.assertTrue(false,"comments textbox is not visible so task could not be created" );
								}
							}
							else {
								log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
								sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
							}
						}
						else {
							log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
						}
						ele=lp.getnextStepsMessage(projectName, 10);
						if (ele!=null) {
							if (ele.getText().trim().contains(BasePageErrorMessage.nextStepsMessage1) && 
									ele.getText().trim().contains(BasePageErrorMessage.nextStepsMessage2) && 
									ele.getText().trim().contains(BasePageErrorMessage.nextStepsMessage3)) {
								log(LogStatus.INFO, "successfully verified next step message on activity timeline", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify next step message on activity timeline", YesNo.Yes);
								sa.assertTrue(false, "could not verify next step message on activity timeline");
							}
						}else {
							log(LogStatus.ERROR, "could not find next step message on activity timeline", YesNo.Yes);
							sa.assertTrue(false, "could not find next step message on activity timeline");
						}
					}
					else {
						log(LogStatus.ERROR, "not able to click on dropdown of related associations so cannot check objects", YesNo.Yes);
						sa.assertTrue(false,"not able to click on dropdown of related associations so cannot check objects" );
					}
				}
				else {
					log(LogStatus.ERROR, "not able to click on new task button", YesNo.Yes);
					sa.assertTrue(false,"not able to click on new task button" );
				}
			}
			else {
				log(LogStatus.ERROR, "not able to click on created institution", YesNo.Yes);
				sa.assertTrue(false, "not able to click on created institution" );
			}
		}
		else {
			log(LogStatus.ERROR, "not able to click on institution tab", YesNo.Yes);
			sa.assertTrue(false, "not able to click on institution tab" );
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc009_verifyNewTaskMultipleAssociation_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String status1=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Status);
		List<String> taskstd1=new LinkedList<String>();
		taskstd1.add(Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
		taskstd1.add(Smoke_TaskINS3Name+" +2");
		taskstd1.add(status1);
		taskstd1.add(crmUser1FirstName+" "+crmUser1LastName);
		taskstd1.add("");
		taskstd1.add(Activity.Task.toString());
		taskstd1.add(Links.View.toString());	
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Due_Date);
		String msg1="";
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS3Name, 10)) {
				msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
				msg1+=" about "+Smoke_TaskFund1Name+" and "+taskCustomObj1Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, null,Smoke_Task2Subject, msg1, date,false,"", false, "", 10);
				
			}
			else {
				log(LogStatus.ERROR, "inst is not found, so could not verify activities task data", YesNo.Yes);
				sa.assertTrue(false,"inst is not found, so could not verify activities task data" );
			}
		}
		else {
			log(LogStatus.ERROR, "inst/account tab is not clickable, so could not verify activities task data", YesNo.Yes);
			sa.assertTrue(false,"inst/account tab is not clickable, so could not verify activities task data" );
		}
		String msg="";
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 10)) {
				msg=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
				msg+=" about "+Smoke_TaskINS3Name+" , "+Smoke_TaskFund1Name+" and "+taskCustomObj1Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, null,Smoke_Task2Subject, msg, date,false, "",false, "", 10);
				if (lp.clickOnRecordOnNextStepsOrPastActivities(projectName, Smoke_TaskFund1Name, Smoke_Task2Subject)) {
					//on fund page
					msg=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
					msg+=" about "+Smoke_TaskINS3Name+" and "+taskCustomObj1Name;
					lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, null,Smoke_Task2Subject, msg, date,false, "",false, "", 10);
					if (lp.clickOnRecordOnNextStepsOrPastActivities(projectName, taskCustomObj1Name, Smoke_Task2Subject)) {
						//on custom obj page
						msg=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
						msg+=" about "+Smoke_TaskINS3Name+" and "+Smoke_TaskFund1Name;
						lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, null,Smoke_Task2Subject, msg, date,false, "",false, "", 10);
						if (lp.clickOnRecordOnNextStepsOrPastActivities(projectName, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, Smoke_Task2Subject)) {
							//on contact page
							msg=BasePageErrorMessage.UpcomingTaskMsg(null, null, 0);
							msg+=" about "+Smoke_TaskINS3Name+" , "+Smoke_TaskFund1Name+" and "+taskCustomObj1Name;
							lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, null,Smoke_Task2Subject, msg,date,false, "",false, "", 10);
							
						}else {
							log(LogStatus.ERROR, "could not click on "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+"so could not verify activities task data", YesNo.Yes);
							sa.assertTrue(false,"could not click on "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+"so could not verify activities task data" );
						}
						
						
					}else {
						log(LogStatus.ERROR, "could not click on "+taskCustomObj1Name+"so could not verify activities task data", YesNo.Yes);
						sa.assertTrue(false,"could not click on "+taskCustomObj1Name+"so could not verify activities task data" );
				}
				}else {
					log(LogStatus.ERROR, "could not click on "+Smoke_TaskFund1Name+"so could not verify activities task data", YesNo.Yes);
					sa.assertTrue(false,"could not click on "+Smoke_TaskFund1Name+"so could not verify activities task data" );
				}
			}
			else {
				log(LogStatus.ERROR, "inst is not found, so could not verify activities task data", YesNo.Yes);
				sa.assertTrue(false,"inst is not found, so could not verify activities task data" );
			}
		}
		else {
			log(LogStatus.ERROR, "inst/account tab is not clickable, so could not verify activities task data", YesNo.Yes);
			sa.assertTrue(false,"inst/account tab is not clickable, so could not verify activities task data" );
		}

		
		lp.CRMlogout();
		sa.assertAll();
	}


	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc010_VerifyNewTaskButtonFromContactDetailPageWithBlankNameAndRelatedAssociation(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		WebElement ele ;
		String parentID=null,secondWindow=null;
		String date=tomorrowsDate;
		
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New task button on Sub Tab : "+RelatedTab.Activities,YesNo.Yes);
					ThreadSleep(2000);

					ele=cp.getmeetingTypeDropdown(projectName, 20);
					if (ele!=null) {
						log(LogStatus.INFO, "Meeting Type Label is Present",YesNo.No);	
					} else {
						sa.assertTrue(false,"Meeting Type Label Should be Present");
						log(LogStatus.ERROR, "Meeting Type Label Should be Present",YesNo.Yes);

					}

					List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),false,action.SCROLLANDBOOLEAN, 15);
					if (!eleList.isEmpty() && eleList.size()==1) {
						log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Name.toString(),YesNo.No);	
					} else {
						sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
						log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

					}


					ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), false,contactName, action.SCROLLANDBOOLEAN, 15);
					if (ele!=null) {
						log(LogStatus.INFO, contactName+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	

						if (clickUsingJavaScript(driver, ele, "Cross Button For "+contactName, action.BOOLEAN)) {
							log(LogStatus.INFO,"Clicked on Cross Button For "+contactName,YesNo.Yes);
							ThreadSleep(2000);
						}
						else {
							sa.assertTrue(false,"Not Able to Click on Cross Button For "+contactName);
							log(LogStatus.SKIP,"Not Able to Click on Cross Button For "+contactName,YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,contactName+" not Found For Label "+PageLabel.Name.toString());
						log(LogStatus.ERROR, contactName+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

					}

					eleList = cp.getAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(),true, action.SCROLLANDBOOLEAN, 5);
					if (eleList.isEmpty()) {
						log(LogStatus.INFO, "Label field is blank for "+PageLabel.Related_Associations.toString(),YesNo.No);	
					} else {
						sa.assertTrue(false,"Label field sholud be blank for "+PageLabel.Related_Associations.toString());
						log(LogStatus.ERROR, "Label field sholud be blank for "+PageLabel.Related_Associations.toString(),YesNo.Yes);

					}
					boolean flag=false;
					// New Contact Button For Name Label
					ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.Object2Page, PageLabel.Name.toString(), action.SCROLLANDBOOLEAN,15);
					if (clickUsingJavaScript(driver, ele, "Name Text Label", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Enter Value to Name Text Box",YesNo.Yes);	
						ThreadSleep(1000);
						ele = cp.getNewButtonElementFromTask(projectName, PageName.Object2Page, PlusNewButton.ContactNewButton, PageLabel.Name.toString(), action.SCROLLANDBOOLEAN, 15);
						if (clickUsingJavaScript(driver, ele, "New Contact Button for Label : "+PageLabel.Name.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Clicked on New Contact Button for "+PageLabel.Name.toString(),YesNo.No);	
							ThreadSleep(1000);
							parentID = switchOnWindow(driver);
							if (parentID!=null) {
								log(LogStatus.INFO,"New window is open Going to verify New Contact PopUP Landing Page ",YesNo.Yes);
								ThreadSleep(1000);
								String mailID=	lp.generateRandomEmailId(gmailUserName);
								ExcelUtils.writeData(testCasesFilePath, mailID, "Contacts", excelLabel.Variable_Name, "AATASKC5",excelLabel.Contact_EmailId);

								if(cp.createContactWithoutNew(projectName, Smoke_TaskContact5FName, Smoke_TaskContact5LName, Smoke_TaskContact5INSName, mailID, Smoke_TaskContact5RecordType, null, null, CreationPage.ContactPage)) {
									log(LogStatus.INFO, "successfully created new contact "+Smoke_TaskContact5FName+" "+Smoke_TaskContact5LName, YesNo.Yes);

								}
								else {
									log(LogStatus.ERROR, "could not create new dummy contact", YesNo.Yes);
									sa.assertTrue(false, "could not create new dummy contact" );
								}

								driver.close();
								driver.switchTo().window(parentID);
							} else {
								sa.assertTrue(false,"No new window is open so cannot verify New Contact PopUP Landing Page");
								log(LogStatus.SKIP,"No new window is open so cannot verify New Contact PopUP Landing Page",YesNo.Yes);
							}
						} else {
							sa.assertTrue(false,"Not Able to Click on New Contact Button for "+PageLabel.Name.toString());
							log(LogStatus.SKIP,"Not Able to Click on New Contact Button for "+PageLabel.Name.toString(),YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"Not Able to Enter Value to Name Text Box");
						log(LogStatus.SKIP,"Not Able to Enter Value to Name Text Box",YesNo.Yes);	
					}



					// New Account Button For Related Associations Label 
					switchToDefaultContent(driver);
					ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN,15);
					if (clickUsingJavaScript(driver, ele, "Related Associations Text Label", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Enter Value to Related Associations Text Box",YesNo.Yes);	
						ThreadSleep(1000);
						ele = cp.getNewButtonElementFromTask(projectName, PageName.Object2Page, PlusNewButton.EntityOrAccountNewButton, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 15);
						if (clickUsingJavaScript(driver, ele, "New Entity/Account Button for Label : "+PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Clicked on New Entity/Account Button for "+PageLabel.Related_Associations.toString(),YesNo.No);	
							ThreadSleep(1000);
							parentID = switchOnWindow(driver);
							if (parentID!=null) {
								log(LogStatus.INFO,"New window is open Going to verify New Entity/Account PopUP Landing Page ",YesNo.Yes);
								ThreadSleep(1000);
								//click next button if present(record type), if not then check header
								click(driver, cp.getContinueOrNextButton(projectName, 5), "next button", action.SCROLLANDBOOLEAN);
								ele = cp.getHeaderTextForPage(projectName, PageName.Object1PagePopup, cp.getNewButtonFromTask(projectName, PlusNewButton.EntityOrAccountNewButton), action.BOOLEAN, 10);
								if (ele!=null) {
									log(LogStatus.INFO,"New Entity/Account PopUp is opened",YesNo.No);	
								} else {
									sa.assertTrue(false,"New Entity/Account PopUp is not opened");
									log(LogStatus.SKIP,"New Entity/Account PopUp is not opened",YesNo.Yes);
								}
								driver.close();
								driver.switchTo().window(parentID);
							} else {
								sa.assertTrue(false,"No new window is open so cannot verify New Entity/Account PopUP Landing Page");
								log(LogStatus.SKIP,"No new window is open so cannot verify New Entity/Account PopUP Landing Page",YesNo.Yes);
							}
						} else {
							sa.assertTrue(false,"Not Able to Click on New Entity/Account Button for "+PageLabel.Related_Associations.toString());
							log(LogStatus.SKIP,"Not Able to Click on New Entity/Account Button for "+PageLabel.Related_Associations.toString(),YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"Not Able to Enter Value to Related Associations Text Box");
						log(LogStatus.SKIP,"Not Able to Enter Value to Related Associations Text Box",YesNo.Yes);	
					}


					// New Test Custom Object Button For Related Associations Label 
					switchToDefaultContent(driver);
					if (cp.SelectRelatedAssociationsdropdownButton(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, action.SCROLLANDBOOLEAN, 20)) {
						log(LogStatus.SKIP,"Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
						ThreadSleep(2000);	

						ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN,15);
						if (clickUsingJavaScript(driver, ele, "Related Associations Text Label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Enter Value to Related Associations Text Box",YesNo.Yes);	
							ThreadSleep(1000);
							ele = cp.getNewButtonElementFromTask(projectName, PageName.Object2Page, PlusNewButton.TestCustomObjectNewButton, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 15);
							if (clickUsingJavaScript(driver, ele, "New Test Object Button for Label : "+PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Clicked on New Test Object Button for "+PageLabel.Related_Associations.toString(),YesNo.No);	
								ThreadSleep(1000);
								parentID = switchOnWindow(driver);
								if (parentID!=null) {
									log(LogStatus.INFO,"New window is open Going to verify Test Object  PopUP Landing Page ",YesNo.Yes);
									ThreadSleep(1000);
									if (cop.createRecord(projectName, taskCustomObj3RecordType, tabCustomObj, taskCustomObj3Name, true)) {
										log(LogStatus.INFO,"New record successfully created "+taskCustomObj3Name,YesNo.No);	

									} else {
										sa.assertTrue(false,"could not create new record for test custom object");
										log(LogStatus.SKIP,"could not create new record for test custom object",YesNo.Yes);
									}
									ele = cp.getHeaderTextForPage(projectName, PageName.Object2Page, cp.getNewButtonFromTask(projectName, PlusNewButton.TestCustomObjectNewButton), action.BOOLEAN, 10);
										if (ele!=null) {
											log(LogStatus.INFO,"New Test Object PopUp is opened",YesNo.No);	
										} else {
											sa.assertTrue(false,"New Test Object PopUp is not opened");
											log(LogStatus.SKIP,"New Test Object PopUp is not opened",YesNo.Yes);
										}
									driver.close();
									driver.switchTo().window(parentID);
								} else {
									sa.assertTrue(false,"No new window is open so cannot verify New Test Object PopUP Landing Page");
									log(LogStatus.SKIP,"No new window is open so cannot verify New Test Object PopUP Landing Page",YesNo.Yes);
								}
							} else {
								sa.assertTrue(false,"Not Able to Click on New Test Object Button for "+PageLabel.Related_Associations.toString());
								log(LogStatus.SKIP,"Not Able to Click on New Test Object Button for "+PageLabel.Related_Associations.toString(),YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"Not Able to Enter Value to Related Associations Text Box");
							log(LogStatus.SKIP,"Not Able to Enter Value to Related Associations Text Box",YesNo.Yes);	
						}

					} else {
						sa.assertTrue(false,"Not Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations);
						log(LogStatus.SKIP,"Not Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
					}


					//  

					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_Task3Subject, "Subject", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
						ThreadSleep(1000);
						if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), tomorrowsDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
							ThreadSleep(1000);
							ele=cp.geDropdownOnTaskPopUp(projectName, PageLabel.Priority.toString(), action.SCROLLANDBOOLEAN, 10);
							if (click(driver, ele, "Drop Down : "+PageLabel.Priority.toString(),action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on "+PageLabel.Priority.toString()+" Drop Down", YesNo.Yes);	
								ThreadSleep(2000);

								if (cp.SelectDropDownValue(projectName, PageName.Object2Page, PageLabel.Priority.toString(), Smoke_Task3Priority, action.SCROLLANDBOOLEAN, 10)) {
									log(LogStatus.INFO, "Selected : "+Smoke_Task3Priority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);	
									ThreadSleep(1000);

								} else {
									log(LogStatus.ERROR, "Not ABle to Select : "+Smoke_Task3Priority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);
									sa.assertTrue(false,"Not ABle to Select : "+Smoke_Task3Priority+" For Label : "+PageLabel.Priority.toString() );
								}

								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask3", excelLabel.Due_Date);
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
									ThreadSleep(2000);
									ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_Task3Subject, action.BOOLEAN, 10);

									String[][] fieldsWithValues= {{PageLabel.Subject.toString(),Smoke_Task3Subject},
											{PageLabel.Name.toString(),""},
											{PageLabel.Due_Date.toString(),date},
											{PageLabel.Priority.toString(),Smoke_Task3Priority},
											{PageLabel.Related_Associations.toString(),"Assign Multiple Associations"}};

									if (clickUsingJavaScript(driver, ele, Smoke_Task3Subject, action.BOOLEAN)) {
										ThreadSleep(10000);
										if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.SCROLLANDBOOLEAN, 30)) {
											log(LogStatus.INFO, "successfully verified all labels on task page", YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "could not verif all labels on task page", YesNo.Yes);
											sa.assertTrue(false,"could not verif all labels on task page");
										}
									} else {
										log(LogStatus.ERROR, "created task not clickable", YesNo.Yes);
										sa.assertTrue(false,"created task not clickable");

									}
								}
								else {
									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not created" );
								}

							} else {
								log(LogStatus.ERROR, "Click on "+PageLabel.Priority.toString()+" Drop Down", YesNo.Yes);
								sa.assertTrue(false,"Click on "+PageLabel.Priority.toString()+" Drop Down" );
							}
						}else {
							log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
						}
					}
					else {
						log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
					}


				} else {
					sa.assertTrue(false,"Not Able to Click on New Meeting button on Sub Tab : "+RelatedTab.Meetings);
					log(LogStatus.SKIP,"Not Able to Click on New Meeting button on Sub Tab : "+RelatedTab.Meetings,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact Not Found : "+contactName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc011_VerifyEditTask_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		ShowMoreAction sma[]= {ShowMoreAction.Edit,ShowMoreAction.Delete,ShowMoreAction.FollowUpTask,ShowMoreAction.Show_More};
		String olddate=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Due_Date);
		String date=tomorrowsDate;
		
		String[] taskUIdata= {Smoke_Task2Subject,"--None--",Smoke_TaskINS3Name+","+Smoke_TaskFund1Name+","+taskCustomObj1Name,Smoke_Task2Comment,olddate,Smoke_Task2Priority,Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,Status.Not_Started.toString()};
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cop.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 10)) {
				log(LogStatus.INFO,"Clicked on custom : "+taskCustomObj1Name,YesNo.No);
				ThreadSleep(1000);
				WebElement ele=null;
				ip.clickOnShowMoreActionForTaskOnly(projectName, PageName.Object2Page, Smoke_Task2Subject);
				for (int i=0;i<sma.length;i++) {
					ele=ip.findActionDropdownElement(projectName, PageName.Object2Page, Smoke_Task2Subject, sma[i]);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of "+sma[i], YesNo.No);
					}
					else {
						log(LogStatus.INFO, "not present: "+sma[i], YesNo.No);
						sa.assertTrue(false, "not present: "+sma[i]);
					}
					
				}
				ip.clickOnShowMoreActionForTaskOnly(projectName, PageName.Object2Page, Smoke_Task2Subject);
				ThreadSleep(2000);
				ele=ip.selectShowMoreActionForTaskInNextSteps(projectName, PageName.Object2Page, Smoke_Task2Subject, ShowMoreAction.Edit);
					if (ele!=null) {
						if (click(driver, ele, "task page", action.BOOLEAN)) {
							flag=true;
						}
						else {
							log(LogStatus.ERROR, "could not click on task link",YesNo.Yes);
							sa.assertTrue(false, "could not click on task link");
						}
					}else {
						log(LogStatus.ERROR, "task link not found",YesNo.Yes);
						sa.assertTrue(false, "task link not found");
					}
					if(flag) {
							
							if (ip.verifyUIOfCreateNewTaskWindow(projectName,crmUser1FirstName+" "+crmUser1LastName,taskUIdata,false )) {
								log(LogStatus.INFO, "successfully verified task ui edit mode", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify create new task ui", YesNo.Yes);
								sa.assertTrue(false, "could not verify create new task ui");
							}
							if (ip.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(), false, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.SCROLLANDBOOLEAN, 15)) {
								log(LogStatus.INFO, "clicked on cross icon of contact 3",YesNo.No);	

							} else {
								sa.assertTrue(false,"could not click on cross icon of contact 3");
								log(LogStatus.ERROR, "could not click on cross icon of contact 3",YesNo.Yes);

							}
							if (ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(), TabName.TaskTab, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, action.SCROLLANDBOOLEAN, 15)) {
								log(LogStatus.INFO, "successfully selected contact 4 for name field", YesNo.No);
							} else {
								sa.assertTrue(false,"could not select contact 4 for name field");
								log(LogStatus.ERROR, "could not select contact 4 for name field",YesNo.Yes);
							}
							//cross button to ins 3 in RA
							if (ip.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Related_Associations.toString(), false, Smoke_TaskINS3Name, action.SCROLLANDBOOLEAN, 15)) {
								log(LogStatus.INFO, "clicked on cross icon of ins 3", YesNo.No);

							} else {
								sa.assertTrue(false,"could not click on cross icon of ins 3");
								log(LogStatus.ERROR, "could not click on cross icon of ins 3",YesNo.Yes);
							}
							//cross button to fund/deal1 in RA
							if (ip.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Related_Associations.toString(), false, Smoke_TaskFund1Name, action.SCROLLANDBOOLEAN, 15)) {
								log(LogStatus.INFO, "clicked on cross icon of "+Smoke_TaskFund1Name, YesNo.No);

							} else {
								sa.assertTrue(false,"could not click on cross icon of "+Smoke_TaskFund1Name);
								log(LogStatus.ERROR, "could not click on cross icon of "+Smoke_TaskFund1Name,YesNo.Yes);
							}
							//cross button to test custom obj 1 in RA
							if (ip.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Related_Associations.toString(), false, taskCustomObj1Name, action.SCROLLANDBOOLEAN, 15)) {
								log(LogStatus.INFO, "clicked on cross icon of "+taskCustomObj1Name, YesNo.No);

							} else {
								sa.assertTrue(false,"could not click on cross icon of "+taskCustomObj1Name);
								log(LogStatus.ERROR, "could not click on cross icon of "+taskCustomObj1Name,YesNo.Yes);
							}
							//select test custom obj 2 in RA
							if (ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab,taskCustomObj2Name, action.SCROLLANDBOOLEAN, 15)) {
								log(LogStatus.INFO, "successfully selected taskCustomObj2Name for Related_Associations field", YesNo.No);
							} else {
								sa.assertTrue(false,"could not select taskCustomObj2Name for Related_Associations field");
								log(LogStatus.ERROR, "could not select taskCustomObj2Name for Related_Associations field",YesNo.Yes);
							}
							
							if (tp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Assigned_To.toString(),false, crmUser1FirstName+" "+crmUser1LastName, action.SCROLLANDBOOLEAN, 15)) {
								log(LogStatus.INFO, "Clicked on Cross Button against : "+crmUser1LastName+" For Label : "+PageLabel.Assigned_To.toString(),YesNo.No);	
								
								ThreadSleep(2000);
								// Assigned To
								String owner = AdminUserFirstName+" "+AdminUserLastName;
								flag=tp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(), TabName.TaskTab, owner, action.BOOLEAN, 10);		
								if (flag) {
									log(LogStatus.INFO,"Selected "+owner+" For  Drown Down Value For Label "+PageLabel.Assigned_To,YesNo.No);
									ThreadSleep(1000);
								}
								else {
									sa.assertTrue(false,"could not select "+owner+" for assigned to field");
									log(LogStatus.ERROR, "could not select "+owner+" for assigned to field",YesNo.Yes);
								}
							}else {
								sa.assertTrue(false,"could not click cross on "+ crmUser1FirstName+" "+crmUser1LastName+" for assigned to field");
								log(LogStatus.ERROR, "could not click cross on "+ crmUser1FirstName+" "+crmUser1LastName+" for assigned to field",YesNo.Yes);
							}
							if (ip.selectDropDownValueonTaskPopUp(projectName, PageName.TestCustomObjectPage, PageLabel.Priority.toString(),Smoke_Task2UpdatedPriority, action.SCROLLANDBOOLEAN, 15)) {
								log(LogStatus.INFO,"Selected "+Smoke_Task2UpdatedPriority+" For  drop Down Value For Label "+PageLabel.Priority,YesNo.No);
							}
							else {
								sa.assertTrue(false,"could not click on "+ Smoke_Task2UpdatedPriority+" for Priority field");
								log(LogStatus.ERROR, "could not click on "+ Smoke_Task2UpdatedPriority+" for Priority field",YesNo.Yes);
							}
							if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), tomorrowsDate, "due date", action.SCROLLANDBOOLEAN)) {
								if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_Task2UpdatedSubject,  PageLabel.Subject.toString(), action.SCROLLANDBOOLEAN)) {
									ExcelUtils.writeData(tomorrowsDate, "Task", excelLabel.Variable_Name, "AATask2", excelLabel.Due_Date);

									if (click(driver, ip.getCustomTabSaveBtn(projectName, 10),"save", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "successfully clicked on save and edited task", YesNo.No);

									}
									else {
										sa.assertTrue(false,"could not click cross on save, so cannot edit task");
										log(LogStatus.ERROR, "could not click cross on save, so cannot edit task",YesNo.Yes);
									}
								}
								else {
									sa.assertTrue(false,"subject field is visible, so cannot edit task");
									log(LogStatus.ERROR, "subject field is visible, so cannot edit task",YesNo.Yes);
								}
							}else {
								sa.assertTrue(false,"due date field is visible, so cannot edit task");
								log(LogStatus.ERROR, "due date field is visible, so cannot edit task",YesNo.Yes);
							}
						
						
					}
					else {
						sa.assertTrue(false,"task link on custom object page not clickable, so cannot edit task");
						log(LogStatus.ERROR, "task link on custom object page not clickable, so cannot edit task",YesNo.Yes);
					}
				
			}else {
				log(LogStatus.ERROR, "could not find new custom object record on custom tab",YesNo.Yes);
				sa.assertTrue(false, "could not find new custom object record on customtab");
			}
		}else {
			log(LogStatus.ERROR, "custom object tab is not clickable",YesNo.Yes);
			sa.assertTrue(false, "custom object tab is not clickable");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc011_VerifyEditTask_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Due_Date);
		WebElement ele=null;
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS3Name, 10)) {
				log(LogStatus.INFO,"Clicked on custom : "+Smoke_TaskINS3Name,YesNo.No);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.SubjectLink, 5);
				if (ele==null) {
					log(LogStatus.INFO, "successfully verified absence of task",YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "task link found, but it should not be present",YesNo.Yes);
					sa.assertTrue(false, "task link found, but it should not be present");
				}
			}else {
				log(LogStatus.ERROR, "could not find new "+Smoke_TaskINS3Name+" on Object1Tab tab",YesNo.Yes);
				sa.assertTrue(false, "could not find new "+Smoke_TaskINS3Name+" on Object1Tab tab");
			}
		}else {
			log(LogStatus.ERROR,"Object1Tab tab is not clickable",YesNo.Yes);
			sa.assertTrue(false,"Object1Tab tab is not clickable");
		}

		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS1Name, 10)) {
				log(LogStatus.INFO,"Clicked on custom : "+Smoke_TaskINS1Name,YesNo.No);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.SubjectLink, 5);
				if (ele==null) {
					log(LogStatus.INFO, "successfully verified absence of task",YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "task link found, but it should not be present",YesNo.Yes);
					sa.assertTrue(false, "task link found, but it should not be present");
				}
			}else {
				log(LogStatus.ERROR, "could not find new "+Smoke_TaskINS1Name+" on Object1Tab tab",YesNo.Yes);
				sa.assertTrue(false, "could not find new "+Smoke_TaskINS1Name+" on Object1Tab tab");
			}
		}
		else {
			log(LogStatus.ERROR,"Object1Tab tab is not clickable",YesNo.Yes);
			sa.assertTrue(false,"Object1Tab tab is not clickable");
		}
		if (ip.clickOnTab(projectName, TabName.Object3Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object3Tab,YesNo.No);
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object3Tab, Smoke_TaskFund1Name, 10)) {
				log(LogStatus.INFO,"Clicked on custom : "+Smoke_TaskFund1Name,YesNo.No);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.SubjectLink, 5);
				if (ele==null) {
					log(LogStatus.INFO, "successfully verified absence of task",YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "task link found, but it should not be present",YesNo.Yes);
					sa.assertTrue(false, "task link found, but it should not be present");
				}
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new "+Smoke_TaskFund1Name+" on Object1Tab tab",YesNo.Yes);
				sa.assertTrue(false, "could not find new "+Smoke_TaskFund1Name+" on Object1Tab tab");
			}
		}
		else {
			log(LogStatus.ERROR,"Object3Tab tab is not clickable",YesNo.Yes);
			sa.assertTrue(false,"Object3Tab tab is not clickable");
		}
		
		if (ip.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.TestCustomObjectTab,YesNo.No);
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, taskCustomObj1Name, 10)) {
				log(LogStatus.INFO,"Clicked on custom : "+taskCustomObj1Name,YesNo.No);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.SubjectLink, 5);
					
				if (ele==null) {
						log(LogStatus.INFO, "successfully verified absence of task",YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "task link found, but it should not be present",YesNo.Yes);
						sa.assertTrue(false, "task link found, but it should not be present");
					}
					switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find taskCustomObj1Name on TestCustomObjectTab tab",YesNo.Yes);
				sa.assertTrue(false, "could not find taskCustomObj1Name on TestCustomObjectTab tab");
			}
		}
		else {
			log(LogStatus.ERROR,"TestCustomObjectTab tab is not clickable",YesNo.Yes);
			sa.assertTrue(false,"TestCustomObjectTab tab is not clickable");
		}
		String relatedAssoctaion=tp.Comment(projectName, PageLabel.Related_Associations, taskCustomObj2Name);
		//			String relatedContactValue= tp.Comment(projectName, PageLabel.Related_Contacts, contact1Name)+", "+contact3Name;
					String comment = relatedAssoctaion+"\n"+TaskPagePageErrorMessage.Dots+"\n"+Smoke_Task2Comment;
		
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 10)) {
				log(LogStatus.INFO,"Clicked on custom : "+Smoke_TaskContact4LName,YesNo.No);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.SubjectLink, 5);
				
				if (ele==null) {
					click(driver, lp.moreStepsBtn(projectName, EnableDisable.Enable, 10), "more steps", action.SCROLLANDBOOLEAN);
				}
				String msg1=BasePageErrorMessage.UpcomingTaskMsg(AdminUserFirstName+" "+AdminUserLastName, null, 0);
				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, null,Smoke_Task2UpdatedSubject, msg1, date,false,"", false, "", 10);
				
				ele=lp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page, ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task2UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task2UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task2UpdatedSubject);
				}
				ele=lp.selectShowMoreActionForTaskInNextSteps(projectName, PageName.Object2Page, Smoke_Task2UpdatedSubject, ShowMoreAction.Show_More);
				if (click(driver, ele, "show more", action.SCROLLANDBOOLEAN)) {
					String[][] fieldsWithValues= {{PageLabel.Subject.toString(),Smoke_Task2UpdatedSubject},
							{PageLabel.Assigned_To.toString(),AdminUserFirstName+" "+AdminUserLastName},
							{PageLabel.Name.toString(),Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName},
							{PageLabel.Status.toString(),Status.Not_Started.toString()},
							{PageLabel.Due_Date.toString(),date},
							{PageLabel.Priority.toString(),Smoke_Task2UpdatedPriority},
							{PageLabel.Comments.toString(),comment},
							{PageLabel.Related_Associations.toString(),taskCustomObj2Name}};
					String parentId=switchOnWindow(driver);
					
					if (parentId!=null) {
						ThreadSleep(10000);
						if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.SCROLLANDBOOLEAN, 30)) {
							log(LogStatus.INFO, "successfully verified all labels on task page", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verif all labels on task page", YesNo.Yes);
							sa.assertTrue(false,"could not verif all labels on task page");
						}
						//please keep pagename as object1
						ShowMoreActionDropDownList smaddl[] = {ShowMoreActionDropDownList.Change_Date,ShowMoreActionDropDownList.Change_Priority,
								ShowMoreActionDropDownList.Change_Status,ShowMoreActionDropDownList.Edit_Comments};
						List<WebElement> li=null;
						if (ip.clickOnShowMoreDropdownOnly(projectName, PageName.Object1Page)) {
							for (ShowMoreActionDropDownList s:smaddl) {
								String actionDropDown = s.toString().replace("_", " ");
								String xpath ="//li/a[@title='"+actionDropDown+"']";
								
								li=FindElements(driver, xpath,actionDropDown );
								if (li!=null) {
									if (li.size()>1) {
										log(LogStatus.ERROR, "there are more than 1 dropdown element of "+actionDropDown, YesNo.Yes);
										sa.assertTrue(false, "there are more than 1 dropdown element of "+actionDropDown);
									}
									else if(li.size()==1) {
										log(LogStatus.INFO, "successfully verified single presence of "+actionDropDown+" on task page", YesNo.No);

									}
									else if(li.size()==0) {
										log(LogStatus.ERROR, "there is no dropdown element of "+actionDropDown, YesNo.Yes);
										sa.assertTrue(false, "there is no dropdown element of "+actionDropDown);
									}
								}
								else{
									log(LogStatus.ERROR, "could not find "+actionDropDown, YesNo.Yes);
									sa.assertTrue(false, "could not find "+actionDropDown);
								}
							}
						}
						else{
							log(LogStatus.ERROR, "could not click on show more dropdown", YesNo.Yes);
							sa.assertTrue(false, "could not click on show more dropdown");
						}
						driver.close();
						driver.switchTo().window(parentId);
					} else {
						log(LogStatus.ERROR, "not found new window after clicking on show more", YesNo.Yes);
						sa.assertTrue(false,"not found new window after clicking on show more");

					}
				} else {
					log(LogStatus.ERROR, "could not click on show more link, so cannot verify task on task page", YesNo.Yes);
					sa.assertTrue(false,"could not click on show more link, so cannot verify task on task page");

				}
			}else {
				log(LogStatus.ERROR, "could not find contact 3 on Object2Tab tab",YesNo.Yes);
				sa.assertTrue(false, "could not find contact 3 on Object2Tab tab");
			}
		}
		else {
			log(LogStatus.ERROR,"Object2Tab tab is not clickable",YesNo.Yes);
			sa.assertTrue(false,"Object2Tab tab is not clickable");
		}
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 10)) {
				log(LogStatus.INFO,"Clicked on custom : "+Smoke_TaskINS2Name,YesNo.No);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.SubjectLink, 5);
				
				if (ele==null) {
					click(driver, lp.moreStepsBtn(projectName, EnableDisable.Enable, 10), "more steps", action.SCROLLANDBOOLEAN);
				}
				String msg1=BasePageErrorMessage.UpcomingTaskMsg(AdminUserFirstName+" "+AdminUserLastName,  Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, null,Smoke_Task2UpdatedSubject, msg1, date,false,"", false, "", 10);
				
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.SubjectLink, 10);
				ThreadSleep(2000);
				if (click(driver, ele, Smoke_Task2UpdatedSubject, action.SCROLLANDBOOLEAN)) {
					String[][] fieldsWithValues= {{PageLabel.Subject.toString(),Smoke_Task2UpdatedSubject},
							{PageLabel.Assigned_To.toString(),AdminUserFirstName+" "+AdminUserLastName},
							{PageLabel.Name.toString(),Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName},
							{PageLabel.Status.toString(),Status.Not_Started.toString()},
							{PageLabel.Due_Date.toString(),date},
							{PageLabel.Priority.toString(),Smoke_Task2UpdatedPriority},
							{PageLabel.Comments.toString(),comment},
							{PageLabel.Related_Associations.toString(),taskCustomObj2Name}};
					if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.SCROLLANDBOOLEAN, 30)) {
						log(LogStatus.INFO, "successfully verified all labels on task page", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verif all labels on task page", YesNo.Yes);
						sa.assertTrue(false,"could not verif all labels on task page");
					}
				}
			}else {
				log(LogStatus.ERROR, "could not find ins 2 on Object1Tab",YesNo.Yes);
				sa.assertTrue(false, "could not find ins 2 on Object1Tab");
			}
		}
		else {
			log(LogStatus.ERROR,"Object1Tab is not clickable",YesNo.Yes);
			sa.assertTrue(false,"Object1Tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc012_CreateStandardTaskAndVerifyCreatedTaskInActivitiesTab_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		System.out.println(Smoke_Task2UpdatedSubject);
		System.out.println(Smoke_Task2UpdatedPriority);
		WebElement ele ;
		String date=todaysDateSingleDateSingleMonth;
				

		if (click(driver, gp.getGlobalActionIcon(projectName, 15), "Global Action Related item", action.BOOLEAN)) {
			log(LogStatus.INFO,"Clicked on Global Action Related item",YesNo.No);
			if (clickUsingJavaScript(driver, gp.getActionItem(projectName, GlobalActionItem.New_Task, 15), "New_Task Link", action.BOOLEAN)) {
				log(LogStatus.INFO,"Clicked on log call Link",YesNo.Yes);
				ThreadSleep(2000);	

				ele = cp.getHeaderTextForPage(projectName, PageName.NewTaskPopUP,PageLabel.New_Task.toString(), action.BOOLEAN, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
				} else {
					sa.assertTrue(false,"New Task PopUp is not opened");
					log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
				}

				ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TaskPage, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN,5);

				if (ele==null) {
					log(LogStatus.INFO,"Related Association Field is absent as expected",YesNo.No);	
				} else {
					sa.assertTrue(false,"Related Association Field is present, but it should not be present");
					log(LogStatus.SKIP,"Related Association Field is present, but it should not be present",YesNo.Yes);
				}
				// subject
				ele=gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Task, PageLabel.Subject.toString(),10);
				
				
				if (sendKeys(driver, ele, Smoke_TaskSTD1Subject, "Subject", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
					ThreadSleep(1000);

					// Due Date
					if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), todaysDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
						ThreadSleep(1000);


						// comment 
						if (sendKeys(driver, ip.getcommentsTextBox(projectName, 10), Smoke_TaskSTD1Comment, "comments", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.ERROR, "entered value in Comment Text Area", YesNo.Yes);	
						}
						else {
							log(LogStatus.ERROR, "comments textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"comments textbox is not visible so task could not be created" );
						}



						// Name
						ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TaskPage, PageLabel.Name.toString(), action.SCROLLANDBOOLEAN,15);
						if (sendKeys(driver, ele, contactName,"Name Text Label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Enter Value to Name Text Box : "+contactName,YesNo.Yes);	
							ThreadSleep(1000);
							ele =  cp.getContactNameOrRelatedAssociationNameOnTask(projectName, PageName.TaskPage, PageLabel.Name.toString(),contactName, action.SCROLLANDBOOLEAN,15);
							if (click(driver, ele, "Select Name From Label : "+PageLabel.Name, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Clicked on : "+contactName,YesNo.Yes);
							} else {
								sa.assertTrue(false,"Not Able to Click on : "+contactName);
								log(LogStatus.SKIP,"Not Able to Click on : "+contactName,YesNo.Yes);	
							}

						}else {
							sa.assertTrue(false,"Not Able to Enter Value to Name Text Box : "+contactName);
							log(LogStatus.SKIP,"Not Able to Enter Value to Name Text Box : "+contactName,YesNo.Yes);	
						}



						// Related To
						click(driver, ip.getrelatedAssociationsdropdownButton(projectName, PageName.TaskPage,PageLabel.Related_To.toString()
								, action.SCROLLANDBOOLEAN, 10),"dropdown button", action.SCROLLANDBOOLEAN);
						if (cp.SelectRelatedAssociationsdropdownButton(projectName, PageName.TaskPage, PageLabel.Related_To.toString(), TabName.TestCustomObjectTab, action.SCROLLANDBOOLEAN, 20)) {
							log(LogStatus.INFO,"Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
							ThreadSleep(2000);	

							ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TaskPage, PageLabel.Related_To.toString(), action.SCROLLANDBOOLEAN,15);
							if (sendKeys(driver, ele,taskCustomObj1Name, "Related To Text Label", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Enter Value to Related To Text Box : "+taskCustomObj1Name,YesNo.Yes);	
								ThreadSleep(1000);

								ele =  cp.getContactNameOrRelatedAssociationNameOnTask(projectName, PageName.TaskPage, PageLabel.Related_To.toString(),taskCustomObj1Name, action.SCROLLANDBOOLEAN,15);
								if (click(driver, ele, "Select TestCustomObject From Label : "+PageLabel.Related_To, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"Clicked on : "+taskCustomObj1Name,YesNo.Yes);
								} else {
									sa.assertTrue(false,"Not Able to Click on : "+taskCustomObj1Name);
									log(LogStatus.SKIP,"Not Able to Click on : "+taskCustomObj1Name,YesNo.Yes);	
								}


							}else {
								sa.assertTrue(false,"Not Able to Enter Value to Related To Text Box : "+meetingCustomObj1Name);
								log(LogStatus.SKIP,"Not Able to Enter Value to Related To Text Box : "+meetingCustomObj1Name,YesNo.Yes);	
							}

						} else {
							sa.assertTrue(false,"Not Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
						}

						if (clickUsingJavaScript(driver, gp.getSaveButtonForEvent(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task : "+Smoke_TaskSTD1Subject,  YesNo.Yes);
							ThreadSleep(1000);
							ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask9", excelLabel.Due_Date);

							ThreadSleep(1000);
							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=Smoke_TaskSTD1Subject;
								if (actualValue.contains(expectedValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);
								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
								ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_TaskSTD1Subject, action.BOOLEAN, 10);

								if (clickUsingJavaScript(driver, ele, Smoke_TaskSTD1Subject, action.BOOLEAN)) {
									log(LogStatus.ERROR, "Clicked on  : "+Smoke_TaskSTD1Subject, YesNo.Yes);
									String[][] fieldsWithValues= {{PageLabel.Subject.toString(),Smoke_TaskSTD1Subject},
											{PageLabel.Due_Date.toString(),date},
											{PageLabel.Related_To.toString(),taskCustomObj1Name},
											{PageLabel.Comments.toString(),Smoke_TaskSTD1Comment},
											{PageLabel.Name.toString(),contactName},
											{PageLabel.Related_Associations.toString(),"Assign Multiple Associations"}};

									if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10)) {
										log(LogStatus.INFO, "successfully verified "+Smoke_TaskSTD1Subject+" on task page", YesNo.No);
									}
									else {
										log(LogStatus.ERROR, "Not Able to verify "+Smoke_TaskSTD1Subject+" on task page", YesNo.Yes);
										sa.assertTrue(false,"Not Able to verify "+Smoke_TaskSTD1Subject+" on task page");
									}

								}else {
									sa.assertTrue(false,"could not click on toast message, so cannot verify task detail page");
									log(LogStatus.SKIP,"could not click on toast message, so cannot verify task detail page",YesNo.Yes);

								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}
							//ele=tp.getTaskNameLinkInSideMMenu(projectName, Smoke_TaskSTD1Subject, 15);
							ThreadSleep(1000);


						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created : "+Smoke_TaskSTD1Subject, YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created : "+Smoke_TaskSTD1Subject );
						}

					}else {
						log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
					}
				}
				else {
					log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
					sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on New Task Button for show more action");
				log(LogStatus.SKIP,"Not Able to Click on New Task Button for show more action",YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TaskTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TaskTab,YesNo.Yes);
		}
		
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc012_CreateStandardTaskAndVerifyCreatedTaskInActivitiesTab_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName=Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Due_Date);
		String task2Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Due_Date);
		WebElement ele=null;
		if (lp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, taskCustomObj1Name, 10)) {
				
				click(driver, lp.moreStepsBtn(projectName, EnableDisable.Enable, 10), "more steps", action.SCROLLANDBOOLEAN);
				
				String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				//msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, taskCustomObj1Name,Smoke_TaskSTD1Subject,msg1, date, false,"",false, "", 10);
				
				
			}
			else {
				log(LogStatus.ERROR, "not found "+taskCustomObj1Name,YesNo.Yes);
				sa.assertTrue(false,"not found "+taskCustomObj1Name);
			}
		}else {
			log(LogStatus.ERROR, "TestCustomObjectTab tab not clickable",YesNo.Yes);
			sa.assertTrue(false, "TestCustomObjectTab tab not clickable");
		}
		ele=null;
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 10)) {
				//click more steps if task is not present
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_TaskSTD1Subject, SubjectElement.SubjectLink, 5);

				if (ele==null) {
					click(driver, lp.moreStepsBtn(projectName, EnableDisable.Enable, 10), "more steps", action.SCROLLANDBOOLEAN);
				}
				//click more steps if task is not present
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.SubjectLink, 5);

				if (ele==null) {
					click(driver, lp.moreStepsBtn(projectName, EnableDisable.Enable, 10), "more steps", action.SCROLLANDBOOLEAN);
				}
				String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, null, 0);
				msg1+=" about "+taskCustomObj1Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page,null, Smoke_TaskSTD1Subject,msg1, date, false,"",false, "", 10);

				msg1=BasePageErrorMessage.UpcomingTaskMsg( AdminUserFirstName+" "+AdminUserLastName,null, 0);
				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page,null, Smoke_Task2UpdatedSubject,msg1,task2Date, false,"",false, "", 10);

				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task2UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task2UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task2UpdatedSubject);
				}

				//expand all
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Expand_All, 10);
				if (click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Expand_All,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Expand_All);
					log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Expand_All,YesNo.Yes);

				}
				//verify task with description
				msg1=BasePageErrorMessage.UpcomingTaskMsg(null, null, 0);
				msg1+=" about "+taskCustomObj1Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page,null, Smoke_TaskSTD1Subject,msg1, date, false,"",true, Smoke_TaskSTD1Comment, 10);

				msg1=BasePageErrorMessage.UpcomingTaskMsg( AdminUserFirstName+" "+AdminUserLastName,null, 0);
				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, null,Smoke_Task2UpdatedSubject,msg1, task2Date, false,"",true, Smoke_Task2Comment, 10);
				//verify collapse all is present and click
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Collapse_All, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified collapse all is present", YesNo.No);
					if (click(driver, ele, ActivityTimeLineItem.Collapse_All.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Collapse_All,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Collapse_All);
						log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Collapse_All,YesNo.Yes);

					}
				}else {
					sa.assertTrue(false,"Not Able to find "+ActivityTimeLineItem.Collapse_All);
					log(LogStatus.ERROR, "Not Able to find "+ActivityTimeLineItem.Collapse_All,YesNo.Yes);

				}

				//verify expand all is present
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Expand_All, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified Expand_All is present after clicking collape", YesNo.No);
				}else {
					sa.assertTrue(false,"Not Able to find "+ActivityTimeLineItem.Expand_All);
					log(LogStatus.ERROR, "Not Able to find "+ActivityTimeLineItem.Expand_All,YesNo.Yes);

				}
			}
			else {
				log(LogStatus.ERROR, "not found "+Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName,YesNo.Yes);
				sa.assertTrue(false,"not found "+Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName);
			}
		}else {
			log(LogStatus.ERROR, "Object2Tab tab not clickable",YesNo.Yes);
			sa.assertTrue(false, "Object2Tab tab not clickable");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc013_VerifyAssignMultipleAssociationsLinkForStandardTAsk_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		WebElement ele ;
		String parentID;
		String actualValue;
		boolean flag=false;
		//String date=todaysDateSingleDateSingleMonth;
		String task9Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Due_Date);
		
		String[] taskUIdata= {Smoke_TaskSTD1Subject,"--None--","",Smoke_TaskSTD1Comment,task9Date,Priority.Normal.toString(),contactName,Status.Not_Started.toString()};
		
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_TaskSTD1Subject, SubjectElement.SubjectLink, 5);

				if (ele!=null) {
					log(LogStatus.INFO,Smoke_TaskSTD1Subject+" link present on Sub Tab : "+RelatedTab.Meetings,YesNo.Yes);	
					
					//if (clickUsingActionClass(driver, ele)) {
					if (click(driver, ele, Smoke_TaskSTD1Subject, action.BOOLEAN)) {
						log(LogStatus.INFO,"Clicked on : "+Smoke_TaskSTD1Subject,YesNo.No);
						ThreadSleep(1000);
						ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_TaskSTD1Subject, action.BOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO,"Landing Page Verified for : "+Smoke_TaskSTD1Subject,YesNo.No);	
						} else {
							sa.assertTrue(false,"Landing Page Not Verified for : "+Smoke_TaskSTD1Subject);
							log(LogStatus.SKIP,"Landing Page Not Verified for : "+Smoke_TaskSTD1Subject,YesNo.Yes);
						}
						for (int k = 0; k < 3; k++) {

							switchToDefaultContent(driver);
							switchToFrame(driver, 60, tp.getTaskPageFrame(projectName,60));

							ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, TaskPagePageErrorMessage.RelatedAssociationText, action.BOOLEAN, 10);
							if (click(driver, ele, TaskPagePageErrorMessage.RelatedAssociationText, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Clicked on Link :  "+TaskPagePageErrorMessage.RelatedAssociationText,YesNo.No);	

								parentID=switchOnWindow(driver);
								if (parentID!=null) {
									log(LogStatus.INFO,"Switch To Edit Task Window",YesNo.No);	
									ThreadSleep(2000);
									
									ele = tp.getTaskPoUpEditHeader(projectName, 10);
									if (ele!=null) {
										log(LogStatus.INFO,"Edit Header Ele Found",YesNo.No);	
										actualValue=ele.getText().trim();

										actualValue = ele.getText().trim();
										String expectedValue=PageLabel.Edit.toString()+" "+Smoke_TaskSTD1Subject;
										if (actualValue.contains(PageLabel.Edit.toString()) && actualValue.contains(Smoke_TaskSTD1Subject)) {
											log(LogStatus.INFO,expectedValue+" matched msg for Edit PopUp", YesNo.No);

										} else {
											log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched msg for Edit PopUp", YesNo.Yes);
											BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched msg for Edit PopUp");
										}

									} else {
										sa.assertTrue(false,"Edit Header Ele Not Found");
										log(LogStatus.SKIP,"Edit Header Ele Not Found",YesNo.Yes);
									}


									if (k==0) {
										if (ip.verifyUIOfCreateNewTaskWindow(projectName,crmUser1FirstName+" "+crmUser1LastName,taskUIdata,false)) {
											log(LogStatus.INFO, "successfully verified log a call ui", YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "could not verify log a call ui", YesNo.Yes);
											sa.assertTrue(false, "could not verify log a call ui");
										}
										String val=getValueFromElementUsingJavaScript(driver, ip.getLabelTextBox(projectName, PageName.TaskPage.toString(), TaskPageFields.Related_To.toString(), 10), TaskPageFields.Related_To.toString());
										if (val.equalsIgnoreCase(taskCustomObj1Name)) {
											log(LogStatus.INFO, "successfully verified "+taskCustomObj1Name+" in label: "+ TaskPageFields.Related_To, YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "could not verify"+taskCustomObj1Name+" in label: "+ TaskPageFields.Related_To, YesNo.Yes);
											sa.assertTrue(false, "could not verify"+taskCustomObj1Name+" in label: "+ TaskPageFields.Related_To);
										}
										ele=tp.getcrossIcon(projectName, 5);
										
									} else if(k==1) {
										ele=tp.getCancelButton(projectName, 5);
									}

									if (k==0 || k==1) {
										if (clickUsingJavaScript(driver, ele, "Cross/Cancel Button",action.BOOLEAN)) {
											log(LogStatus.INFO,"Clicked on Cross/Cancel Button",YesNo.No);	
											flag=true;
										} else {
											sa.assertTrue(false,"Not Able to Click on Cross/Cancel Button");
											log(LogStatus.SKIP,"Not Able to Click on Cross/Cancel Button",YesNo.Yes);
										}	

										if (flag) {
											log(LogStatus.INFO,"No Error",YesNo.No);
										} else {
											sa.assertTrue(false,"Some Error");
											log(LogStatus.SKIP,"Some Error",YesNo.Yes);
										}
										flag=false;
										try {
											driver.switchTo().window(parentID);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}

									if(k==2){


										flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.Object1Tab, Smoke_TaskINS5Name, action.SCROLLANDBOOLEAN, 10);		
										if (flag) {
											log(LogStatus.SKIP,"Selected "+Smoke_TaskINS5Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.Object1Tab)+" For Label "+PageLabel.Related_Associations,YesNo.No);

										} else {
											sa.assertTrue(false,"Not Able to Select "+Smoke_TaskINS5Name+" For Drown Down Value : "+cp.getTabName(projectName, TabName.Object1Tab)+" For Label "+PageLabel.Related_Associations);
											log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskINS5Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.Object1Tab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

										}

										flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.Object3Tab, Smoke_TaskFund2Name, action.SCROLLANDBOOLEAN, 10);		
										if (flag) {
											log(LogStatus.SKIP,"Selected "+Smoke_TaskFund2Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.Object3Tab)+" For Label "+PageLabel.Related_Associations,YesNo.No);

										} else {
											sa.assertTrue(false,"Not Able to Select "+Smoke_TaskFund2Name+" For Drown Down Value : "+cp.getTabName(projectName, TabName.Object3Tab)+" For Label "+PageLabel.Related_Associations);
											log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskFund2Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.Object3Tab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

										}

										flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, taskCustomObj2Name, action.SCROLLANDBOOLEAN, 10);		
										if (flag) {
											log(LogStatus.SKIP,"Selected "+taskCustomObj2Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.No);

										} else {
											sa.assertTrue(false,"Not Able to Select "+taskCustomObj2Name+" For Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations);
											log(LogStatus.SKIP,"Not Able to Select "+taskCustomObj2Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

										}

										// Subject

										if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_TaskSTD1UpdatedSubject, "Subject", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
											ThreadSleep(1000);

											//  Priority

											ele=cp.getDropdownOnTaskPopUp(projectName, PageName.Object2Page,PageLabel.Priority.toString(), action.SCROLLANDBOOLEAN, 10);
											if (click(driver, ele, "Drop Down : "+PageLabel.Priority.toString(),action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "Select/Click on "+PageLabel.Priority.toString()+" Drop Down", YesNo.Yes);	
												ThreadSleep(2000);

												if (cp.SelectDropDownValue(projectName, PageName.Object2Page, PageLabel.Priority.toString(), Smoke_TaskSTD1UpdatedPriority, action.SCROLLANDBOOLEAN, 10)) {
													log(LogStatus.ERROR, "Selected : "+Smoke_TaskSTD1UpdatedPriority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);	
													ThreadSleep(1000);

												} else {
													log(LogStatus.ERROR, "Not ABle to Select : "+Smoke_TaskSTD1UpdatedPriority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);
													sa.assertTrue(false,"Not ABle to Select : "+Smoke_TaskSTD1UpdatedPriority+" For Label : "+PageLabel.Priority.toString() );
												}

											} else {
												log(LogStatus.ERROR, "Click on "+PageLabel.Priority.toString()+" Drop Down", YesNo.Yes);
												sa.assertTrue(false,"Click on "+PageLabel.Priority.toString()+" Drop Down" );
											}

											// comment 

											if (sendKeys(driver, ip.getcommentsTextBox(projectName, 10), Smoke_TaskSTD1UpdatedComment, "comments", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "entered value in Comment Text Area", YesNo.Yes);	
											}
											else {
												log(LogStatus.ERROR, "comments textbox is not visible so task could not be created", YesNo.Yes);
												sa.assertTrue(false,"comments textbox is not visible so task could not be created" );
											}

											System.err.println(">>>>>>>>>>>>>>>>>>>>>>Save Button<<<<<<<<<<<<<<<<<<<<<<<<<");
											// 
											if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {

												log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
												flag=true;
												ThreadSleep(2000);

												ele = cp.getCreatedConfirmationMsg(projectName, 15);
												if (ele!=null) {
													actualValue = ele.getText().trim();
													String expectedValue=tp.taskCreatesMsg(projectName, Smoke_TaskSTD1UpdatedSubject);
													if (actualValue.equals(expectedValue)) {
														log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

													} else {
														log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
														BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
													}
												} else {
													sa.assertTrue(false,"Created Task Msg Ele not Found");
													log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

												}

												switchToDefaultContent(driver);
												switchToFrame(driver, 60, tp.getFrame(PageName.TaskPage,60));

												ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_TaskFund2Name, action.BOOLEAN, 10);
												if (click(driver, ele, Smoke_TaskFund1Name, action.BOOLEAN)) {
													ThreadSleep(5000);
													ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_TaskFund2Name, action.BOOLEAN, 10);
													if (ele!=null) {
														log(LogStatus.INFO, "successfully verified opening of "+Smoke_TaskFund2Name+" detail page", YesNo.No);
													}
													else {
														log(LogStatus.ERROR, "fund detail page could not be opened", YesNo.Yes);
														sa.assertTrue(false,"fund detail page could not be opened" );
													}
													driver.navigate().back();
												}
												else {
													log(LogStatus.ERROR, "fund/deal name is not clickable", YesNo.Yes);
													sa.assertTrue(false,"fund/deal name is not clickable" );

												}
												String[][] fieldsWithValues= {{PageLabel.Subject.toString(),Smoke_TaskSTD1UpdatedSubject},
														{PageLabel.Priority.toString(),Smoke_TaskSTD1UpdatedPriority}};

												tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

												// Related Association
												ele = tp.getLabelForTaskInViewMode(projectName, PageName.TaskPage, PageLabel.Related_Associations.toString(), action.BOOLEAN, 10);
												if (ele!=null) {
													actualValue = ele.getText().trim();
													log(LogStatus.INFO,"Actual Value For Related Association Link : "+actualValue, YesNo.No);
													String expectedValue=Smoke_TaskINS5Name+", "+Smoke_TaskFund2Name+", "+taskCustomObj2Name;
													if (actualValue.equals(expectedValue)) {
														log(LogStatus.INFO,expectedValue+" matched FOR Related Association Label", YesNo.No);

													} else {
														log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Related Association Label", YesNo.Yes);
														BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Related Association LABEL");
													}

													// Comment
													ele = tp.getLabelForTaskInViewMode(projectName, PageName.TaskPage, PageLabel.Comments.toString(), action.BOOLEAN, 10);
													if (ele!=null) {
														actualValue = ele.getText().trim();
														log(LogStatus.INFO,"Actual Value For Comment : "+actualValue, YesNo.No);
														String[] commentValue = {expectedValue,TaskPagePageErrorMessage.Dots,Smoke_TaskSTD1UpdatedComment};
														for (String comment : commentValue) {
															if (actualValue.contains(comment)) {
																log(LogStatus.INFO,comment+" matched for Comment Label", YesNo.No);

															} else {
																log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched for Comment Label", YesNo.Yes);
																BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched for Comment Label");
															}
														}
													} else {
														sa.assertTrue(false,"Comment Label Ele not Found");
														log(LogStatus.SKIP,"Comment Label Ele not Found",YesNo.Yes);
													}

												} else {
													sa.assertTrue(false,"Comment Label Ele not Found");
													log(LogStatus.SKIP,"Comment Label Ele not Found",YesNo.Yes);

												}
												System.err.println(">>>>>>>>>>>>>>>>>>>>>>Save222222222222222 Button<<<<<<<<<<<<<<<<<<<<<<<<<");
												// 	
											}

											else {
												log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
												sa.assertTrue(false,"save button is not clickable so task not created" );
											}



										}
										else {
											log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
											sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
										}


									driver.close();
									driver.switchTo().window(parentID);
									}
								}
								else {
									sa.assertTrue(false,"new window not found, so cannot assign multiple assoc");
									log(LogStatus.SKIP,"new window not found, so cannot assign multiple assoc",YesNo.Yes);
								}
								
							

							} else {
								sa.assertTrue(false,"Not Able to Click on Link :  "+TaskPagePageErrorMessage.RelatedAssociationText);
								log(LogStatus.SKIP,"Not Able to Click on Link :  "+TaskPagePageErrorMessage.RelatedAssociationText,YesNo.Yes);
							}

						}
						
						refresh(driver);
						switchToFrame(driver, 60, tp.getTaskPageFrame(projectName,60));

						ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_TaskFund2Name, action.BOOLEAN, 10);
						if (ele!=null) {
							if (click(driver, ele, Smoke_TaskFund2Name, action.SCROLLANDBOOLEAN)) {
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
								msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+taskCustomObj2Name;
								lp.verifyActivityAtNextStep2(projectName, PageName.Object3Page,null, Smoke_TaskSTD1UpdatedSubject,msg1,task9Date, false,"",false, "", 10);
								
							}else {
								sa.assertTrue(false,"Not Able to Click on Link :  "+Smoke_TaskFund2Name);
								log(LogStatus.SKIP,"Not Able to Click on Link :  "+Smoke_TaskFund2Name,YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"Not Able to find Link :  "+Smoke_TaskFund2Name);
							log(LogStatus.SKIP,"Not Able to find Link :  "+Smoke_TaskFund2Name,YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,"Not Able to Click on : "+Smoke_TaskSTD1Subject);
						log(LogStatus.SKIP,"Not Able to Click on : "+Smoke_TaskSTD1Subject,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,Smoke_TaskSTD1Subject+" link not present on Sub Tab : "+RelatedTab.Meetings);
					log(LogStatus.SKIP,Smoke_TaskSTD1Subject+" link not present on Sub Tab : "+RelatedTab.Meetings,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact Not Found : "+contactName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc013_VerifyAssignMultipleAssociationsLinkForStandardTAsk_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		WebElement ele=null;
		String task9Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Due_Date);
		
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS5Name, 20)) {
				log(LogStatus.INFO,"Clicked on Object 1: "+Smoke_TaskINS5Name,YesNo.No);
				String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,null, Smoke_TaskSTD1UpdatedSubject,msg1,task9Date, false,"",false, "", 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_TaskSTD1UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject);
				}
			}else {
				sa.assertTrue(false,"Object1 Not Found : "+Smoke_TaskINS5Name);
				log(LogStatus.SKIP,"Object1  Not Found : "+Smoke_TaskINS5Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		if (cp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.TestCustomObjectTab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, taskCustomObj2Name, 20)) {
				log(LogStatus.INFO,"Clicked on Test Custom Object : "+taskCustomObj2Name,YesNo.No);
				ThreadSleep(1000);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}
				String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.TestCustomObjectPage,taskCustomObj2Name, Smoke_TaskSTD1UpdatedSubject,msg1, task9Date, false,"",false, "", 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,ActivityType.Next, Smoke_TaskSTD1UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject);
				}

				msg1=BasePageErrorMessage.UpcomingTaskMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.TestCustomObjectPage,taskCustomObj2Name, Smoke_Task2UpdatedSubject,msg1,Smoke_Task2DueDate, false,"",false, "", 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task2UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task2UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task2UpdatedSubject);
				}
			} else {
				sa.assertTrue(false,"Test custom object Not Found : "+taskCustomObj2Name);
				log(LogStatus.SKIP,"Test custom object  Not Found : "+taskCustomObj2Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab,YesNo.Yes);
		}

		if (cp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.TestCustomObjectTab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, taskCustomObj1Name, 20)) {
				log(LogStatus.INFO,"Clicked on Test Custom Object : "+taskCustomObj1Name,YesNo.No);
				ThreadSleep(1000);
				String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+Smoke_TaskINS5Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.TestCustomObjectPage,taskCustomObj1Name, Smoke_TaskSTD1UpdatedSubject,msg1, task9Date, false,"",false, "", 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,ActivityType.Next, Smoke_TaskSTD1UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject);
				}
			} else {
				sa.assertTrue(false,"Test custom object Not Found : "+taskCustomObj1Name);
				log(LogStatus.SKIP,"Test custom object  Not Found : "+taskCustomObj1Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab,YesNo.Yes);
		}


		switchToDefaultContent(driver);
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 20)) {
				log(LogStatus.INFO,"Clicked on Entity/Account : "+Smoke_TaskINS2Name,YesNo.No);
				ThreadSleep(1000);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}
				String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_TaskSTD1UpdatedSubject,msg1, task9Date, false,"",false, "", 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, Smoke_TaskSTD1UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject);
				}

			} else {
				sa.assertTrue(false,"Entity/Account Not Found : "+Smoke_TaskINS2Name);
				log(LogStatus.SKIP,"Entity/Account  Not Found : "+Smoke_TaskINS2Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}
				String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, null, 0);
				msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.TestCustomObjectPage,contactName, Smoke_TaskSTD1UpdatedSubject,msg1, task9Date, false,"",false, "", 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_TaskSTD1UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject);
				}

			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact  Not Found : "+contactName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
}


	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc014_VerifyNewTaskAtPackageObjectFundOrDeal_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		WebElement ele ;
		boolean flag=false;
		List<String> taskstd1=new LinkedList<String>();
		taskstd1.add(contactName);
		taskstd1.add(Smoke_TaskFund1Name);
		taskstd1.add(Status.Not_Started.toString().replace("_", " "));
		taskstd1.add(owner);
		taskstd1.add("");
		taskstd1.add(Activity.Task.toString());
		taskstd1.add(Links.View.toString());	

		if (cp.clickOnTab(projectName, TabName.Object3Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object3Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object3Tab, Smoke_TaskFund1Name, 20)) {
				log(LogStatus.INFO,"Clicked on Fund/Deal : "+Smoke_TaskFund1Name,YesNo.No);
				ThreadSleep(1000);

				if (clickUsingJavaScript(driver, cp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Task_with_Multiple_Associations, 10), "New task Button", action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New task button on Sub Tab : "+RelatedTab.Activities,YesNo.Yes);
					ThreadSleep(2000);	

					ele = cp.getHeaderTextForPage(projectName, PageName.Object2Page,PageLabel.New_Task.toString(), action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
					} else {
						sa.assertTrue(false,"New Task PopUp is not opened");
						log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
					}

					ele = cp.getRelatedAssciationDefaultSelectedDropDown(projectName, TabName.Object1Tab, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 20);
					if (ele!=null) {
						log(LogStatus.INFO, "Default Selected For "+PageLabel.Related_Associations.toString()+" Verified",YesNo.No);	


					} else {
						sa.assertTrue(false,"Default Selected object For "+PageLabel.Related_Associations.toString()+" Not Verified");
						log(LogStatus.ERROR, "Default Selected object For "+PageLabel.Related_Associations.toString()+" Not Verified",YesNo.Yes);

					}

					List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.Object3Page, PageLabel.Related_Associations.toString(),false, action.SCROLLANDBOOLEAN, 15);
					if (!eleList.isEmpty() && eleList.size()==1) {
						log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Related_Associations.toString(),YesNo.No);	

						ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(),false, Smoke_TaskFund1Name, action.SCROLLANDBOOLEAN, 15);
						if (ele!=null) {
							log(LogStatus.INFO, Smoke_TaskFund1Name+" is prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.No);	


						} else {
							sa.assertTrue(false,Smoke_TaskFund1Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString());
							log(LogStatus.ERROR, Smoke_TaskFund1Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.Yes);

						}

					} else {
						sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
						log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

					}


					flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, contactName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+contactName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+contactName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+contactName+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					ele=cp.getDropdownOnTaskPopUp(projectName, PageName.Object2Page,PageLabel.Meeting_Type.toString(), action.SCROLLANDBOOLEAN, 10);
					if (click(driver, ele, "Drop Down : "+PageLabel.Meeting_Type.toString(),action.SCROLLANDBOOLEAN)) {
						log(LogStatus.ERROR, "Select/Click on "+PageLabel.Meeting_Type.toString()+" Drop Down", YesNo.Yes);	
						ThreadSleep(2000);

						if (cp.SelectDropDownValue(projectName, PageName.Object2Page, PageLabel.Meeting_Type.toString(), Smoke_BoardMeetingTaskMeetingType, action.SCROLLANDBOOLEAN, 10)) {
							log(LogStatus.ERROR, "Selected : "+Smoke_BoardMeetingTaskMeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.Yes);	
							ThreadSleep(1000);
						}
						else {
							sa.assertTrue(false,"Not Able to Select "+Smoke_BoardMeetingTaskMeetingType+" For Label "+PageLabel.Meeting_Type);
							log(LogStatus.SKIP,"Not Able to Select "+Smoke_BoardMeetingTaskMeetingType+" For Label "+PageLabel.Meeting_Type,YesNo.Yes);

						}
					}	else {
						sa.assertTrue(false,"Not Able to click dropdown "+PageLabel.Meeting_Type);
						log(LogStatus.SKIP,"Not Able to click dropdown "+PageLabel.Meeting_Type,YesNo.Yes);

					}


					// Subject

					if (sendKeys(driver, cp.getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_BoardMeetingTaskSubject, "Subject", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
						ThreadSleep(1000);

						//  Priority

						appLog.info(">>>>>");
						// 
						if (clickUsingJavaScript(driver, cp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);


							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, Smoke_BoardMeetingTaskSubject);
								if (actualValue.contains(expectedValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}
							appLog.info(">>>>>");
							// 
							refresh(driver);
							
							ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
							if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
								log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
							} else {
								log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

							}
							String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
							//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
							lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskFund1Name, Smoke_BoardMeetingTaskSubject,msg1, DueDate.No_due_date.toString(), false,"",false, "", 10);


						}
						else {
							log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
						}

					}else {
						log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
					}


				} else {
					sa.assertTrue(false,"Not Able to Click on New task button on Sub Tab : "+RelatedTab.Meetings);
					log(LogStatus.SKIP,"Not Able to Click on New task button on Sub Tab : "+RelatedTab.Meetings,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Fund/Deal Not Found : "+Smoke_TaskFund1Name);
				log(LogStatus.SKIP,"Fund/Deal  Not Found : "+Smoke_TaskFund1Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc014_VerifyNewTaskAtPackageObjectFundOrDeal_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		WebElement ele ;
		String task2Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Due_Date);
		String task9Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Due_Date);
		
		List<String> taskstd1=new LinkedList<String>();
		taskstd1.add(contactName);
		taskstd1.add(taskCustomObj2Name);
		taskstd1.add(Status.Not_Started.toString().replace("_", " "));
		taskstd1.add(AdminUserFirstName+" "+AdminUserLastName);
		taskstd1.add("");
		taskstd1.add(Activity.Task.toString());
		taskstd1.add(Links.View.toString());
		List<String> taskstd2=new LinkedList<String>();
		taskstd2.add(contactName);
		taskstd2.add(taskCustomObj1Name+" +3");
		taskstd2.add(Status.Not_Started.toString().replace("_", " "));
		taskstd2.add(owner);
		taskstd2.add("");
		taskstd2.add(Activity.Task.toString());
		taskstd2.add(Links.View.toString());
		List<String> taskstd3=new LinkedList<String>();
		taskstd3.add(Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
		taskstd3.add(Smoke_TaskFund1Name);
		taskstd3.add(Status.Not_Started.toString().replace("_", " "));
		taskstd3.add(owner);
		taskstd3.add(Smoke_BoardMeetingTaskMeetingType);
		taskstd3.add(Activity.Task.toString());
		taskstd3.add(Links.View.toString());
		String value="Open and Completed activities";
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 20)) {
				log(LogStatus.INFO,"Clicked on Entity/Account : "+Smoke_TaskINS2Name,YesNo.No);
				ThreadSleep(1000);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}
				String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_TaskSTD1UpdatedSubject,msg1, task9Date, false,"",false, "", 10);
				
				msg1=BasePageErrorMessage.UpcomingTaskMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_Task2UpdatedSubject,msg1,task2Date, false,"",false, "", 10);
				
				msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
				msg1+=" about "+Smoke_TaskFund1Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_BoardMeetingTaskSubject,msg1, DueDate.No_due_date.toString(), false,"",false, "", 10);
				
				String parentID=null;
				//load more past activity
				if (cp.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						if (click(driver, cp.getadvancedFilterImg(projectName, 10), "advanced filter", action.BOOLEAN)) {
							ele=cp.getAdvancedFilteDropdowns(projectName, PageLabel.Status.toString(), 10);
							if (selectVisibleTextFromDropDown(driver, ele, "status", value)) {
								ele=cp.clearApplyButtonOnAdvancedFilter(projectName, "Apply", 10);
								if (click(driver, ele, "apply", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully clicked on apply button",YesNo.No);
									cp.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, task2Date, Smoke_Task2UpdatedSubject, taskstd1, action.BOOLEAN, 15);
									

									cp.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, task9Date, Smoke_TaskSTD1UpdatedSubject, taskstd2, action.BOOLEAN, 15);
									

									cp.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, "", Smoke_BoardMeetingTaskSubject, taskstd3, action.BOOLEAN, 15);
									
									
								}else {
									sa.assertTrue(false,"could not click on apply button");
									log(LogStatus.SKIP,"could not click on apply button",YesNo.Yes);
								}
							} else {
								sa.assertTrue(false,"status dropdown Not Found ");
								log(LogStatus.SKIP,"status dropdown Not Found ",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"advanced filter img Not Found ");
							log(LogStatus.SKIP,"advanced filter img Not Found ",YesNo.Yes);
						}
						driver.close();
						driver.switchTo().window(parentID);
					}
					else {
						sa.assertTrue(false,"could not find new window, so cannot verify activities");
						log(LogStatus.SKIP,"could not find new window, so cannot verify activities",YesNo.Yes);
				
					}
				}else {
					sa.assertTrue(false,"could not click on view all on load more past activities");
					log(LogStatus.SKIP,"could not click on view all on load more past activities",YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Entity/Account Not Found : "+Smoke_TaskINS2Name);
				log(LogStatus.SKIP,"Entity/Account  Not Found : "+Smoke_TaskINS2Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc015_VerifyNewTaskAtCustomObjectPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String task9Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Due_Date);
		String task2Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Due_Date);
		String contactName = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		WebElement ele ;
		boolean flag=false;
		if (cp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.TestCustomObjectTab,YesNo.No);
			ThreadSleep(1000);
			if (cop.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, taskCustomObj2Name, 10)){
				log(LogStatus.INFO,"Clicked on custom tab record : "+taskCustomObj2Name,YesNo.No);
				ThreadSleep(1000);
					if (clickUsingJavaScript(driver, cp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Task_with_Multiple_Associations, 10), "New task Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Clicked on New task button on Sub Tab : "+RelatedTab.Activities,YesNo.Yes);
						ThreadSleep(2000);	

						ele = cp.getHeaderTextForPage(projectName, PageName.TestCustomObjectPage,PageLabel.New_Task.toString(), action.BOOLEAN, 30);
						if (ele!=null) {
							log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
						} else {
							sa.assertTrue(false,"New Task PopUp is not opened");
							log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
						}

						ele = cp.getRelatedAssciationDefaultSelectedDropDown(projectName, TabName.Object1Tab, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 20);
						if (ele!=null) {
							log(LogStatus.INFO, "Default Selected For "+PageLabel.Related_Associations.toString()+" Verified",YesNo.No);	


						} else {
							sa.assertTrue(false,"Default Selected object For "+PageLabel.Related_Associations.toString()+" Not Verified");
							log(LogStatus.ERROR, "Default Selected object For "+PageLabel.Related_Associations.toString()+" Not Verified",YesNo.Yes);

						}

						List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(),false, action.SCROLLANDBOOLEAN, 15);
						if (!eleList.isEmpty() && eleList.size()==1) {
							log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Related_Associations.toString(),YesNo.No);	

							ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(),false, taskCustomObj2Name, action.SCROLLANDBOOLEAN, 15);
							if (ele!=null) {
								log(LogStatus.INFO, taskCustomObj2Name+" is prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.No);	


							} else {
								sa.assertTrue(false,taskCustomObj2Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString());
								log(LogStatus.ERROR, taskCustomObj2Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.Yes);

							}

						} else {
							sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
							log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

						}

						// Assigned_To Cross Button Click
						if (tp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(),false, owner, action.SCROLLANDBOOLEAN, 15)) {
							log(LogStatus.INFO, "Clicked on Cross Button against : "+owner+" For Label : "+PageLabel.Assigned_To.toString(),YesNo.No);	
							
							ThreadSleep(2000);
							// Assigned To
							owner = AdminUserFirstName+" "+AdminUserLastName;
							flag=tp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(), TabName.TestCustomObjectTab, owner, action.BOOLEAN, 10);		
							if (flag) {
								log(LogStatus.INFO,"Selected "+owner+" For  Drown Down Value For Label "+PageLabel.Assigned_To,YesNo.No);
								ThreadSleep(1000);
								
							} else {
								sa.assertTrue(false,"could not select admin name in "+PageLabel.Assigned_To.toString());
								log(LogStatus.ERROR, "could not select admin name in "+PageLabel.Assigned_To.toString(),YesNo.Yes);

							}
						}
						 else {
								sa.assertTrue(false,"cross button could not be clicked on "+PageLabel.Assigned_To.toString());
								log(LogStatus.ERROR, "cross button could not be clicked on "+PageLabel.Assigned_To.toString(),YesNo.Yes);

							}
						
						//name
						
						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(), TabName.Object2Tab, contactName, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.SKIP,"Selected "+contactName+" For Label "+PageLabel.Name,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+contactName+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"Not Able to Select "+contactName+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						//meeting type
						ele=cp.getDropdownOnTaskPopUp(projectName, PageName.Object2Page,PageLabel.Meeting_Type.toString(), action.SCROLLANDBOOLEAN, 10);
						if (click(driver, ele, "Drop Down : "+PageLabel.Meeting_Type.toString(),action.SCROLLANDBOOLEAN)) {
							log(LogStatus.ERROR, "Select/Click on "+PageLabel.Meeting_Type.toString()+" Drop Down", YesNo.Yes);	
							ThreadSleep(2000);

							if (cp.SelectDropDownValue(projectName, PageName.Object2Page, PageLabel.Meeting_Type.toString(), Smoke_ClientMeetingTaskMeetingType, action.SCROLLANDBOOLEAN, 10)) {
								log(LogStatus.ERROR, "Selected : "+Smoke_ClientMeetingTaskMeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.Yes);	
								ThreadSleep(1000);
							}
							else {
								sa.assertTrue(false,"Not Able to Select "+Smoke_ClientMeetingTaskMeetingType+" For Label "+PageLabel.Meeting_Type);
								log(LogStatus.SKIP,"Not Able to Select "+Smoke_ClientMeetingTaskMeetingType+" For Label "+PageLabel.Meeting_Type,YesNo.Yes);

							}
						}	else {
							sa.assertTrue(false,"Not Able to click dropdown "+PageLabel.Meeting_Type);
							log(LogStatus.SKIP,"Not Able to click dropdown "+PageLabel.Meeting_Type,YesNo.Yes);

						}


						// Subject
						ele= cp.getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(),20);
						scrollDownThroughWebelement(driver, ele, "subject");
						if (sendKeys(driver,ele, Smoke_ClientMeetingTaskSubject, PageLabel.Subject.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
							ThreadSleep(1000);

							appLog.info(">>>>>");
							if (clickUsingJavaScript(driver, cp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully created task",  YesNo.Yes);


								ele = cp.getCreatedConfirmationMsg(projectName, 15);
								if (ele!=null) {
									String actualValue = ele.getText().trim();
									String expectedValue=tp.taskCreatesMsg(projectName, Smoke_ClientMeetingTaskSubject);
									if (actualValue.contains(expectedValue)) {
										log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

									} else {
										log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
										BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
									}
								} else {
									sa.assertTrue(false,"Created Task Msg Ele not Found");
									log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

								}
								appLog.info(">>>>>");
							//	 
								switchToDefaultContent(driver);
								refresh(driver);
								ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
								if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
									log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
								} else {
									sa.assertTrue(false,"Not Able to Click on More Steps");
									log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

								}
								String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
								msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
								lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,taskCustomObj2Name, Smoke_TaskSTD1UpdatedSubject,msg1, task9Date, false,"",false, "", 10);
								ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_TaskSTD1UpdatedSubject, SubjectElement.RedFlag, 10);
								if (ele!=null) {
									log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.Yes);
									sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject);
								}
								
								msg1=BasePageErrorMessage.UpcomingTaskMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
								//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
								lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,taskCustomObj2Name, Smoke_Task2UpdatedSubject,msg1, task2Date, false,"",false, "", 10);
								ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.RedFlag, 10);
								if (ele!=null) {
									log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task2UpdatedSubject,YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task2UpdatedSubject,YesNo.Yes);
									sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task2UpdatedSubject);
								}
								
								msg1=BasePageErrorMessage.UpcomingTaskMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
								//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
								lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,taskCustomObj2Name, Smoke_ClientMeetingTaskSubject,msg1, DueDate.No_due_date.toString(), false,"",false, "", 10);
								
							}
							else {
								log(LogStatus.ERROR, "save button is not clickable so task could not be created", YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task could not be created" );
							}

						}else {
							log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
						}


					} else {
						sa.assertTrue(false,"Not Able to Click on New task button on Sub Tab : "+RelatedTab.Activities);
						log(LogStatus.SKIP,"Not Able to Click on New task button on Sub Tab : "+RelatedTab.Activities,YesNo.Yes);
					}


			} else {
				sa.assertTrue(false,"Fund/Deal Not Found : "+Smoke_TaskFund1Name);
				log(LogStatus.SKIP,"Fund/Deal  Not Found : "+Smoke_TaskFund1Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc016_AddMeetingTypeInTaskCompactLayout(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String parentID=null;
		boolean flag=false;
		String nameOfLayout="New CL";
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(99);
		nameOfLayout+=randomInt;
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Task )) {
					if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Task, objectFeatureName.compactLayouts)) {
						if (sp.clickOnAlreadyCreatedLayout("System Default")) {
							switchToFrame(driver, 10, sp.getFrame(PageName.CompactLayout, 10));
							if (click(driver, sp.getcloneButton(projectName, 10), "clone", action.BOOLEAN)) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.CompactLayout, 10));
								if (sendKeys(driver, sp.getFieldLabelTextBox(10),nameOfLayout, "label", action.BOOLEAN)) {
									if (selectVisibleTextFromDropDown(driver, sp.getavailableFieldsLayout(10), "available fields", "Meeting Type")) {
										if (click(driver, sp.getaddButtonForFieldsLayout(10), "add", action.BOOLEAN)) {
											if (click(driver,sp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
													flag=true;
													log(LogStatus.INFO, "successfully verified creation of layout", YesNo.No);
											}
											else {
												log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
												sa.assertTrue(false, "save button is not clickable");
											}
										}else {
											log(LogStatus.ERROR, "add button is not clickable, so cannot add required fields to layout", YesNo.Yes);
											sa.assertTrue(false, "add button is not clickable, so cannot add required fields to layout");
										}
									}else {
										log(LogStatus.ERROR, "available fields dropdown is not visible, so cannot add required fields to layout", YesNo.Yes);
										sa.assertTrue(false, "available fields dropdown is not visible, so cannot add required fields to layout");
									}
								}else {
									log(LogStatus.ERROR, "label field textbox is not visible, so cannot write name of layout", YesNo.Yes);
									sa.assertTrue(false, "label field textbox is not visible, so cannot write name of layout");
								}
							}else {
								log(LogStatus.ERROR, "clone button is not clickable", YesNo.Yes);
								sa.assertTrue(false, "clone button is not clickable");
							}
						}else {
							log(LogStatus.ERROR, "system default layout is not clickable", YesNo.Yes);
							sa.assertTrue(false, "system default layout is not clickable");
						}
						switchToDefaultContent(driver);
						String ofn=objectFeatureName.compactLayouts.toString();
						WebElement ele=null;
						if (flag) {
							ele=isDisplayed(driver, FindElement(driver, "//a[contains(text(),'"+ofn+"')]", "", action.BOOLEAN,20), "visibility",20,ofn+" feature link");
							if(ele!=null) {
								if(click(driver, ele, ofn+" object feature link", action.SCROLLANDBOOLEAN)) {
									if (click(driver, sp.getcompactLayoutAssignmentLight(projectName, 10), "compact layout", action.SCROLLANDBOOLEAN)) {
										switchToFrame(driver, 10, sp.getFrame(PageName.CompactLayout, 10));
										if (click(driver, sp.geteditAssignment(projectName, 10), "edit", action.SCROLLANDBOOLEAN)) {
											if (selectVisibleTextFromDropDown(driver, sp.getdefaultCompactLayoutDropdown(projectName, 10), "default layout", nameOfLayout)) {
												if (click(driver, sp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
													ThreadSleep(5000);
												}else {
													log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
													sa.assertTrue(false, "save button is not clickable");
												}
											}else {
												log(LogStatus.ERROR, "default compact layout dropdown is not visible, so cannot assign layout", YesNo.Yes);
												sa.assertTrue(false, "default compact layout dropdown is not visible, so cannot assign layout");
											}
										}else {
											log(LogStatus.ERROR, "edit assignment button is not clickable", YesNo.Yes);
											sa.assertTrue(false, "edit assignment button is not clickable");
										}
									}else {
										log(LogStatus.ERROR, "compact layout assignment button is not clickable", YesNo.Yes);
										sa.assertTrue(false, "compact layout assignment button is not clickable");
									}
									switchToDefaultContent(driver);
								}
							}else {
								appLog.error("Not able to click on object feature "+ofn);
								sa.assertTrue(false, "Not able to click on object feature ");
								
							}
						}else {
							appLog.error("could not create layout, so will not edit assignment");
							sa.assertTrue(false, "could not create layout, so will not edit assignment");
						}

					}else {
						log(LogStatus.ERROR, "object feature "+objectFeatureName.compactLayouts+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+objectFeatureName.compactLayouts+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "task object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "task object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc017_VerifyAddedFieldsInActivityCompactLayout(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele=null;
		String task2Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask2", excelLabel.Due_Date);
		String task9Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask9", excelLabel.Due_Date);
		
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (lp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 10)) {
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
				if (click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Expand_All,YesNo.No);	


				} else {
					sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Expand_All);
					log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Expand_All,YesNo.Yes);

				} 
				String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
				msg1+=" about "+Smoke_TaskFund1Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_BoardMeetingTaskSubject,msg1, DueDate.No_due_date.toString(), true,Smoke_BoardMeetingTaskMeetingType,true, "", 10);

				msg1=BasePageErrorMessage.UpcomingTaskMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_ClientMeetingTaskSubject,msg1, DueDate.No_due_date.toString(), true,Smoke_ClientMeetingTaskMeetingType,true, "", 10);

				msg1=BasePageErrorMessage.UpcomingTaskMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_Task2UpdatedSubject,msg1, task2Date, false,"",true, Smoke_Task2Comment, 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, Smoke_Task2UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task2UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task2UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task2UpdatedSubject);
				}
				msg1=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_TaskSTD1UpdatedSubject,msg1, task9Date, false,"",true, Smoke_TaskSTD1UpdatedComment, 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, Smoke_TaskSTD1UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_TaskSTD1UpdatedSubject);
				}
			} else {
				sa.assertTrue(false,"entity/inst Not Found : "+Smoke_TaskINS2Name);
				log(LogStatus.SKIP,"entity/inst Not Found : "+Smoke_TaskINS2Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc018_RemoveContactFromName(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele=null;
		String contact3Name=Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String contact1Name=Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		if (sendKeys(driver, lp.getsearchSalesforce(projectName, 10), Smoke_BoardMeetingTaskSubject, "search", action.BOOLEAN)) {
			String xpath="//input[contains(@title,'Search')]/..//ul//li//span[@title='"+Smoke_BoardMeetingTaskSubject+"']";
			ele=FindElement(driver, xpath, "board meeting", action.SCROLLANDBOOLEAN, 10);
			if (click(driver, ele,Smoke_BoardMeetingTaskSubject , action.BOOLEAN)) {
				ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_BoardMeetingTaskSubject, action.BOOLEAN, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Landing Page Verified for : "+Smoke_BoardMeetingTaskSubject,YesNo.No);	
				} else {
					sa.assertTrue(false,"Landing Page Not Verified for : "+Smoke_BoardMeetingTaskSubject);
					log(LogStatus.SKIP,"Landing Page Not Verified for : "+Smoke_BoardMeetingTaskSubject,YesNo.Yes);
				}
				String fieldsWithValues[][]= {{PageLabel.Name.toString(),contact3Name}};
				if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.SCROLLANDBOOLEAN, 30)) {
					log(LogStatus.INFO, "successfully verified all labels on task page", YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verif all labels on task page", YesNo.Yes);
					sa.assertTrue(false,"could not verif all labels on task page");
				}
				ele=tp.getEditButton(projectName, 10);
				if (click(driver, ele, "edit", action.SCROLLANDBOOLEAN)) {
					// Due Date
					if (sendKeys(driver, lp.getdueDateTextBoxInNewTask(projectName, 20), todaysDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
						ThreadSleep(1000);
					}
					else {
						log(LogStatus.ERROR, "could not Enter value to Due Date Text Box", YesNo.Yes);
						sa.assertTrue(false,"could not Enter value to Due Date Text Box");
								
					}
					ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), false,contact3Name, action.SCROLLANDBOOLEAN, 15);
					if (ele!=null) {
						log(LogStatus.INFO, contact3Name+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	

						if (clickUsingJavaScript(driver, ele, "Cross Button For "+contact3Name, action.BOOLEAN)) {
							log(LogStatus.INFO,"Clicked on Cross Button For "+contact3Name,YesNo.Yes);
							ThreadSleep(2000);
						}
						else {
							sa.assertTrue(false,"Not Able to Click on Cross Button For "+contact3Name);
							log(LogStatus.SKIP,"Not Able to Click on Cross Button For "+contact3Name,YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,contact3Name+" not Found For Label "+PageLabel.Name.toString());
						log(LogStatus.ERROR, contact3Name+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

					}
					
					boolean flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.TestCustomObjectTab, contact1Name, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.INFO,"Selected contact1 For  Drown Down Value :  For Label "+PageLabel.Name,YesNo.No);
					}
					else {
						sa.assertTrue(false,contact1Name+" not clicked for Label "+PageLabel.Name.toString());
						log(LogStatus.ERROR, contact1Name+" not clicked for Label "+PageLabel.Name.toString(),YesNo.Yes);
	
					}
					int i=2;
					if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						while (isDisplayed(driver, lp.getCustomTabSaveBtn(projectName,20), "visibility", 10, "save")!=null) {
							clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN);
							log(LogStatus.INFO, "clicked save "+i+" times", YesNo.No);
							i++;
						}
						ExcelUtils.writeData(todaysDate, "Task", excelLabel.Variable_Name, "AATask10", excelLabel.Due_Date);

						log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
						flag=true;
						String fieldsWithValues1[][]= {{PageLabel.Name.toString(),contact1Name}};
						if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues1, action.SCROLLANDBOOLEAN, 30)) {
							log(LogStatus.INFO, "successfully verified all labels on task page after editing", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verif all labels on task page after editing", YesNo.Yes);
							sa.assertTrue(false,"could not verif all labels on task page after editing");
						}
					}else {
						log(LogStatus.ERROR, "could not click save button on task page for editing", YesNo.Yes);
						sa.assertTrue(false,"could not click save button on task page for editing");
					}
				}else {
					log(LogStatus.ERROR, "could not click edit button on task page for editing", YesNo.Yes);
					sa.assertTrue(false,"could not click edit button on task page for editing");
				}
			}else {
				log(LogStatus.ERROR, "could not click task name on search", YesNo.Yes);
				sa.assertTrue(false,"could not click task name on search");
			}
		}else {
			log(LogStatus.ERROR, "search salesforce textbox is not visible", YesNo.Yes);
			sa.assertTrue(false,"search salesforce textbox is not visible");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc019_RemoveContactFromName(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele=null;
		String contact4Name=Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		String contact1Name=Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		if (sendKeys(driver, lp.getsearchSalesforce(projectName, 10), Smoke_BoardMeetingTaskSubject, "search", action.BOOLEAN)) {
			String xpath="//input[contains(@title,'Search')]/..//ul//li//span[@title='"+Smoke_BoardMeetingTaskSubject+"']";
			ele=FindElement(driver, xpath, "board meeting", action.SCROLLANDBOOLEAN, 10);
			if (click(driver, ele,Smoke_BoardMeetingTaskSubject , action.BOOLEAN)) {
				ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_BoardMeetingTaskSubject, action.BOOLEAN, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Landing Page Verified for : "+Smoke_BoardMeetingTaskSubject,YesNo.No);	
				} else {
					sa.assertTrue(false,"Landing Page Not Verified for : "+Smoke_BoardMeetingTaskSubject);
					log(LogStatus.SKIP,"Landing Page Not Verified for : "+Smoke_BoardMeetingTaskSubject,YesNo.Yes);
				}
				String fieldsWithValues[][]= {{PageLabel.Name.toString(),contact1Name}};
				if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.SCROLLANDBOOLEAN, 30)) {
					log(LogStatus.INFO, "successfully verified all labels on task page", YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verif all labels on task page", YesNo.Yes);
					sa.assertTrue(false,"could not verif all labels on task page");
				}
				ele=tp.getEditButton(projectName, 10);
				if (click(driver, ele, "edit", action.SCROLLANDBOOLEAN)) {
					
					ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), false,contact1Name, action.SCROLLANDBOOLEAN, 15);
					if (ele!=null) {
						log(LogStatus.INFO, contact1Name+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	

						if (clickUsingJavaScript(driver, ele, "Cross Button For "+contact1Name, action.BOOLEAN)) {
							log(LogStatus.INFO,"Clicked on Cross Button For "+contact1Name,YesNo.Yes);
							ThreadSleep(2000);
						}
						else {
							sa.assertTrue(false,"Not Able to Click on Cross Button For "+contact1Name);
							log(LogStatus.SKIP,"Not Able to Click on Cross Button For "+contact1Name,YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,contact1Name+" not Found For Label "+PageLabel.Name.toString());
						log(LogStatus.ERROR, contact1Name+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

					}
					
					boolean flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.TestCustomObjectTab, contact4Name, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.INFO,"Selected contact1 For  Drown Down Value :  For Label "+PageLabel.Name,YesNo.No);
					}
					else {
						sa.assertTrue(false,contact4Name+" not clicked for Label "+PageLabel.Name.toString());
						log(LogStatus.ERROR, contact4Name+" not clicked for Label "+PageLabel.Name.toString(),YesNo.Yes);
	
					}
					int i=2;
					if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
						
						ThreadSleep(2000);
						while (isDisplayed(driver, lp.getCustomTabSaveBtn(projectName,20), "visibility", 10, "save")!=null) {
							clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN);
							log(LogStatus.INFO, "clicked save "+i+" times", YesNo.No);
							i++;
						}
						log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
						flag=true;
						String fieldsWithValues1[][]= {{PageLabel.Name.toString(),contact4Name}};
						if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues1, action.SCROLLANDBOOLEAN, 30)) {
							log(LogStatus.INFO, "successfully verified all labels on task page after editing", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verif all labels on task page after editing", YesNo.Yes);
							sa.assertTrue(false,"could not verif all labels on task page after editing");
						}
					}else {
						log(LogStatus.ERROR, "could not click save button on task page for editing", YesNo.Yes);
						sa.assertTrue(false,"could not click save button on task page for editing");
					}
				}else {
					log(LogStatus.ERROR, "could not click edit button on task page for editing", YesNo.Yes);
					sa.assertTrue(false,"could not click edit button on task page for editing");
				}
			}else {
				log(LogStatus.ERROR, "could not click task name on search", YesNo.Yes);
				sa.assertTrue(false,"could not click task name on search");
			}
		}else {
			log(LogStatus.ERROR, "search salesforce textbox is not visible", YesNo.Yes);
			sa.assertTrue(false,"search salesforce textbox is not visible");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc020_VerifyLogACallWithMultipleAssociation_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		String date=todaysDate;
		String[] taskUIdata= {Activity.Call.toString(),"--None--",Smoke_TaskINS1Name,"",date,Priority.Normal.toString(),"",Status.Completed.toString()};
		WebElement ele=null;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab)+","+ip.getTabName(projectName, TabName.TestCustomObjectTab);
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS1Name, 20)) {
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, 10);
					if (clickUsingJavaScript(driver,ele, "log call", action.SCROLLANDBOOLEAN)) {
						if (ip.verifyUIOfCreateNewTaskWindow(projectName,crmUser1FirstName+" "+crmUser1LastName,taskUIdata,false)) {
							log(LogStatus.INFO, "successfully verified log a call ui", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify log a call ui", YesNo.Yes);
							sa.assertTrue(false, "could not verify log a call ui");
						}
						if (clickUsingJavaScript(driver, ip.getcrossIcon(projectName, 20), "cross")) {
							if (ip.getcancelButton(projectName, 10)==null) {
								log(LogStatus.INFO, "new task window is successfully closed", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "new task window is not closed", YesNo.Yes);
								sa.assertTrue(false,   "new task window is not closed");
							}
						}
						else {
							log(LogStatus.ERROR, "could not click on cross icon", YesNo.Yes);
							sa.assertTrue(false,  "could not click on cross icon");
						}
					}
					else {
						log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
						sa.assertTrue(false,   "could not click on new task button");
					}
					clickUsingJavaScript(driver,lp. getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);

					ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, 10);
					if (clickUsingJavaScript(driver,ele,ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations.toString(), action.SCROLLANDBOOLEAN)) {
						if (clickUsingJavaScript(driver, ip.getcancelButton(projectName, 20),  "cancel button")) {
							if (ip.getcancelButton(projectName, 10)==null) {
								log(LogStatus.INFO, "new task window is successfully closed", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "new task window is not closed", YesNo.Yes);
								sa.assertTrue(false,   "new task window is not closed");
							}
						}
						else {
							log(LogStatus.ERROR, "could not click on cross icon", YesNo.Yes);
							sa.assertTrue(false,   "could not click on cross icon");
						}
					}
					else {
						log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
						sa.assertTrue(false,   "could not click on new task button");
					}
					clickUsingJavaScript(driver,lp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);

					ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, 10);
					if (clickUsingJavaScript(driver,ele, "new task", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						if (clickUsingJavaScript(driver, ip.getrelatedAssociationsdropdownButton(projectName, 10), "dropdown button for related associations")) {
							List<String> l=compareMultipleListContainsByTitle(driver, tabs, ip.listOfObjectsInRelatedAssctions(projectName));
							if (l.isEmpty()) {
								log(LogStatus.INFO, "successfully verified presence of all tabs "+tabs, YesNo.No);
							}
							else {
								for (String a:l) {
									log(LogStatus.ERROR,"not found "+a,  YesNo.Yes);
									sa.assertTrue(false, "not found "+a);
								}
							}
							l.clear();
							l=compareMultipleListContainsByTitle(driver,ip.getTabName(projectName, TabName.Object2Tab).toString(), ip.listOfObjectsInRelatedAssctions(projectName));
							if (l.isEmpty()) {
								log(LogStatus.ERROR, "tab "+TabName.Object2Tab.toString()+" is present but should not be", YesNo.Yes);
								sa.assertTrue(false, "tab "+TabName.Object2Tab.toString()+" is present but should not be");
							}
							else {
								for (String a:l) {
									log(LogStatus.INFO,"not found "+a+" as expected",  YesNo.Yes);

								}
							}
							//3
							boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name,YesNo.Yes);

							}

							//remove icon
							if (click(driver, ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), true, Smoke_TaskINS1Name, action.SCROLLANDBOOLEAN, 10), "remove button", action.BOOLEAN)) {
									log(LogStatus.INFO,"Selected remove button for "+Smoke_TaskINS1Name,YesNo.No);

							}
							else {
								log(LogStatus.ERROR, "remove Button is not clickable", YesNo.Yes);
								sa.assertTrue(false, "remove Button is not clickable");
							}
							flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.Object1Tab, Smoke_TaskINS3Name, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name,YesNo.Yes);

							}
							ThreadSleep(3000);
							flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.Object3Tab, Smoke_TaskFund1Name, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+Smoke_TaskFund1Name+" For Label "+PageLabel.Name,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+Smoke_TaskFund1Name+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskFund1Name+" For Label "+PageLabel.Name,YesNo.Yes);

							}
							flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, taskCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+taskCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+taskCustomObj1Name+" For Label "+PageLabel.Related_Associations);
								log(LogStatus.SKIP,"Not Able to Select "+taskCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

							}
							ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(),true, Smoke_TaskFund1Name, action.SCROLLANDBOOLEAN, 5);
							if (ele!=null) {
								log(LogStatus.INFO, Smoke_TaskFund1Name+"successfully added For Label "+PageLabel.Related_Associations.toString(),YesNo.No);	
							} else {
								sa.assertTrue(false,Smoke_TaskFund1Name+" not added For Label "+PageLabel.Related_Associations.toString());
								log(LogStatus.ERROR, Smoke_TaskFund1Name+" not added For Label "+PageLabel.Related_Associations.toString(),YesNo.Yes);

							}
							if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), Smoke_Task1LogACallSubject, "Subject", action.SCROLLANDBOOLEAN)) {
								ele=cp.geDropdownOnTaskPopUp(projectName, PageLabel.Priority.toString(), action.SCROLLANDBOOLEAN, 10);
								if (click(driver, ele, "Drop Down : "+PageLabel.Priority.toString(),action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on "+PageLabel.Priority.toString()+" Drop Down", YesNo.Yes);	
									ThreadSleep(2000);

									if (cp.SelectDropDownValue(projectName, PageName.Object2Page, PageLabel.Priority.toString(), Smoke_Task1LogACallPriority, action.SCROLLANDBOOLEAN, 10)) {
										log(LogStatus.INFO, "Selected : "+Smoke_Task1LogACallPriority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);	
										ThreadSleep(1000);

									} else {
										log(LogStatus.ERROR, "Not ABle to Select : "+Smoke_Task1LogACallPriority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);
										sa.assertTrue(false,"Not ABle to Select : "+Smoke_Task1LogACallPriority+" For Label : "+PageLabel.Priority.toString() );
									}
									if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 10), todaysDate, "date", action.SCROLLANDBOOLEAN)) {
										if (sendKeys(driver, ip.getcommentsTextBox(projectName, 10), Smoke_Task1LogACallComment, "comments", action.SCROLLANDBOOLEAN)) {
											appLog.info(">>>>>");
											//scn.next();
											if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO,"successfully created call",  YesNo.Yes);
											}
											else {
												log(LogStatus.ERROR, "save button is not clickable so call not created", YesNo.Yes);
												sa.assertTrue(false,"save button is not clickable so call not created" );
											}
											ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask12", excelLabel.Due_Date);
											refresh(driver);
											ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Past, Smoke_Task1LogACallSubject, SubjectElement.SubjectLink, 5);
											if (ele==null) {
												log(LogStatus.INFO, "successfully verified absence of call "+Smoke_Task1LogACallSubject,YesNo.No);
											}
											else {
												log(LogStatus.ERROR, "could not verify absence of call "+Smoke_Task1LogACallSubject,YesNo.Yes);
												sa.assertTrue(false, "could not verify absence of call "+Smoke_Task1LogACallSubject);
											}
										}
										else {
											log(LogStatus.ERROR, "comments textbox is not visible so task could not be created", YesNo.Yes);
											sa.assertTrue(false,"comments textbox is not visible so task could not be created" );
										}
									}
									else {
										log(LogStatus.ERROR, "due date textbox is not visible so task could not be created", YesNo.Yes);
										sa.assertTrue(false,"due date textbox is not visible so task could not be created" );
									}
								}
								else {
									log(LogStatus.ERROR, "priority dropdown is not clickable so task could not be created", YesNo.Yes);
									sa.assertTrue(false,"priority dropdown is not clickable so task could not be created" );
								}
							}
							else {
								log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
								sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
							}
						}
						else {
							log(LogStatus.ERROR, "not able to click on dropdown of related associations so cannot check objects", YesNo.Yes);
							sa.assertTrue(false,"not able to click on dropdown of related associations so cannot check objects" );
						}
					}
					else {
						log(LogStatus.ERROR, "not able to click on new call button", YesNo.Yes);
						sa.assertTrue(false,"not able to click on new call button" );
					}

				
			}
			else {
				log(LogStatus.ERROR, "not able to click on created institution", YesNo.Yes);
				sa.assertTrue(false, "not able to click on created institution" );
			}
		}
		else {
			log(LogStatus.ERROR, "not able to click on institution tab", YesNo.Yes);
			sa.assertTrue(false, "not able to click on institution tab" );
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc020_VerifyLogACallWithMultipleAssociation_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		List<String> taskstd1=new LinkedList<String>();
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Due_Date);
		System.out.println(date);
		taskstd1.add(Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
		taskstd1.add(Smoke_TaskINS3Name+" +2");
		taskstd1.add(Status.Completed.toString());
		taskstd1.add(crmUser1FirstName+" "+crmUser1LastName);
		taskstd1.add("");
		taskstd1.add(Activity.Call.toString());
		taskstd1.add(Links.View.toString());
		String msg1="";
		String clickOnRecord[]= {Smoke_TaskINS2Name,Smoke_TaskFund1Name,taskCustomObj1Name,Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName};
		PageName pName[]= {PageName.Object1Page,PageName.Object3Page,PageName.TestCustomObjectPage,PageName.Object2Page};
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS3Name, 10)) {

				msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
				msg1+=" about "+Smoke_TaskFund1Name+" and "+taskCustomObj1Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS3Name, Smoke_Task1LogACallSubject,msg1, date, false,"",false, "", 10);
				
					ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, Smoke_BoardMeetingTaskSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_BoardMeetingTaskSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_BoardMeetingTaskSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_BoardMeetingTaskSubject);
				}

			}
			else {
				log(LogStatus.ERROR, "inst is not found, so could not verify activities task data", YesNo.Yes);
				sa.assertTrue(false,"inst is not found, so could not verify activities task data" );
			}
		}
		else {
			log(LogStatus.ERROR, "inst/account tab is not clickable, so could not verify activities task data", YesNo.Yes);
			sa.assertTrue(false,"inst/account tab is not clickable, so could not verify activities task data" );
		}
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 10)) {
				
				for (int i=0;i<4;i++) {
					if (i==3)
						msg1=BasePageErrorMessage.LoggedCallMsg(null, null, 0);

					else
						msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);

					if (i!=0) {
						if (lp.clickOnRecordOnNextStepsOrPastActivities(projectName, clickOnRecord[i], Smoke_Task1LogACallSubject)) {

						}else {
							log(LogStatus.ERROR, "could not click on of task "+clickOnRecord[i],YesNo.Yes);
							sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_BoardMeetingTaskSubject);
						}
					}
					if ((i==0) || (i==3))
						msg1+=" about "+Smoke_TaskINS3Name+" , "+Smoke_TaskFund1Name+" and "+taskCustomObj1Name;
					else if(i==1)
						msg1+=" about "+Smoke_TaskINS3Name+" and "+taskCustomObj1Name;
					else if(i==2)
						msg1+=" about "+Smoke_TaskINS3Name+" and "+Smoke_TaskFund1Name;
					lp.verifyActivityAtPastStep2(projectName, pName[i],clickOnRecord[i], Smoke_Task1LogACallSubject,msg1, date, false,"",false, "", 10);
				
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, Smoke_BoardMeetingTaskSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_BoardMeetingTaskSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_BoardMeetingTaskSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_BoardMeetingTaskSubject);
				}
				}
				
				String parentID=null;
				//load more past activity
				if (cp.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						ip.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, date, Smoke_Task1LogACallSubject, taskstd1, action.BOOLEAN, 15);
						ThreadSleep(5000);
						driver.close();
						driver.switchTo().window(parentID);
					}else {
						log(LogStatus.ERROR, "no new window found, so cannot verify activities grid", YesNo.Yes);
						sa.assertTrue(false,"no new window found, so cannot verify activities grid" );
					}
				}else {
					log(LogStatus.ERROR, "view all link could not be clicked", YesNo.Yes);
					sa.assertTrue(false,"view all link could not be clicked" );
				}
			}
			else {
				log(LogStatus.ERROR, "inst2 is not found, so could not verify activities task data", YesNo.Yes);
				sa.assertTrue(false,"inst2 is not found, so could not verify activities task data" );
			}
		}
		else {
			log(LogStatus.ERROR, "inst/account tab is not clickable, so could not verify activities task data", YesNo.Yes);
			sa.assertTrue(false,"inst/account tab is not clickable, so could not verify activities task data" );
		}
		
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc021_VerifyLogACallButtonFromContactDetailPageWithBlankNameAndRelatedAssociation(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		WebElement ele ;
		String parentID=null;
		String date=tomorrowsDate;
		
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, 10);
				
					if (clickUsingJavaScript(driver, ele, "log call Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Clicked on log a call button on Sub Tab : "+RelatedTab.Activities,YesNo.Yes);
						ThreadSleep(2000);
					
						ele=cp.getmeetingTypeDropdown(projectName, 20);
						if (ele!=null) {
							log(LogStatus.INFO, "Meeting Type Label is Present",YesNo.No);	
						} else {
							sa.assertTrue(false,"Meeting Type Label Should be Present");
							log(LogStatus.ERROR, "Meeting Type Label Should be Present",YesNo.Yes);

						}

						List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),false,action.SCROLLANDBOOLEAN, 15);
						if (!eleList.isEmpty() && eleList.size()==1) {
							log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Name.toString(),YesNo.No);	
						} else {
							sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
							log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

						}


						ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), false,contactName, action.SCROLLANDBOOLEAN, 15);
						if (ele!=null) {
							log(LogStatus.INFO, contactName+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	

							if (clickUsingJavaScript(driver, ele, "Cross Button For "+contactName, action.BOOLEAN)) {
								log(LogStatus.INFO,"Clicked on Cross Button For "+contactName,YesNo.Yes);
								ThreadSleep(2000);
							}
							else {
								sa.assertTrue(false,"Not Able to Click on Cross Button For "+contactName);
								log(LogStatus.SKIP,"Not Able to Click on Cross Button For "+contactName,YesNo.Yes);
							}

						} else {
							sa.assertTrue(false,contactName+" not Found For Label "+PageLabel.Name.toString());
							log(LogStatus.ERROR, contactName+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

						}

						eleList = cp.getAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(),true, action.SCROLLANDBOOLEAN, 5);
						if (eleList.isEmpty()) {
							log(LogStatus.INFO, "Label field is blank for "+PageLabel.Related_Associations.toString(),YesNo.No);	
						} else {
							sa.assertTrue(false,"Label field sholud be blank for "+PageLabel.Related_Associations.toString());
							log(LogStatus.ERROR, "Label field sholud be blank for "+PageLabel.Related_Associations.toString(),YesNo.Yes);

						}

						// New Contact Button For Name Label
						ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.Object2Page, PageLabel.Name.toString(), action.SCROLLANDBOOLEAN,15);
						if (clickUsingJavaScript(driver, ele, "Name Text Label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Enter Value to Name Text Box",YesNo.Yes);	
							ThreadSleep(1000);
							ele = cp.getNewButtonElementFromTask(projectName, PageName.Object2Page, PlusNewButton.ContactNewButton, PageLabel.Name.toString(), action.SCROLLANDBOOLEAN, 15);
							if (clickUsingJavaScript(driver, ele, "New Contact Button for Label : "+PageLabel.Name.toString(), action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Clicked on New Contact Button for "+PageLabel.Name.toString(),YesNo.No);	
								ThreadSleep(1000);
								parentID = switchOnWindow(driver);
								if (parentID!=null) {
									log(LogStatus.INFO,"New window is open Going to verify New Contact PopUP Landing Page ",YesNo.Yes);
									ThreadSleep(1000);
									//click next button if present(record type), if not then check header
									click(driver, cp.getContinueOrNextButton(projectName, 5), "next button", action.SCROLLANDBOOLEAN);
									ele = cp.getHeaderTextForPage(projectName, PageName.Object1PagePopup, cp.getNewButtonFromTask(projectName, PlusNewButton.ContactNewButton), action.BOOLEAN, 10);
									if (ele!=null) {
										log(LogStatus.INFO,"New Contact PopUp is opened",YesNo.No);	
									} else {
										sa.assertTrue(false,"New Contact PopUp is not opened");
										log(LogStatus.SKIP,"New Contact PopUp is not opened",YesNo.Yes);
									}
									driver.close();
									driver.switchTo().window(parentID);
								} else {
									sa.assertTrue(false,"No new window is open so cannot verify New Contact PopUP Landing Page");
									log(LogStatus.SKIP,"No new window is open so cannot verify New Contact PopUP Landing Page",YesNo.Yes);
								}
							} else {
								sa.assertTrue(false,"Not Able to Click on New Contact Button for "+PageLabel.Name.toString());
								log(LogStatus.SKIP,"Not Able to Click on New Contact Button for "+PageLabel.Name.toString(),YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"Not Able to Enter Value to Name Text Box");
							log(LogStatus.SKIP,"Not Able to Enter Value to Name Text Box",YesNo.Yes);	
						}
						
						
						
						// New Account Button For Related Associations Label 
						switchToDefaultContent(driver);
						ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN,15);
						if (clickUsingJavaScript(driver, ele, "Related Associations Text Label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Enter Value to Related Associations Text Box",YesNo.Yes);	
							ThreadSleep(1000);
							ele = cp.getNewButtonElementFromTask(projectName, PageName.Object2Page, PlusNewButton.EntityOrAccountNewButton, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 15);
							if (clickUsingJavaScript(driver, ele, "New Entity/Account Button for Label : "+PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Clicked on New Entity/Account Button for "+PageLabel.Related_Associations.toString(),YesNo.No);	
								ThreadSleep(1000);
								parentID = switchOnWindow(driver);
								if (parentID!=null) {
									log(LogStatus.INFO,"New window is open Going to verify New Entity/Account PopUP Landing Page ",YesNo.Yes);
									ThreadSleep(1000);
									//click next button if present(record type), if not then check header
									click(driver, cp.getContinueOrNextButton(projectName, 5), "next button", action.SCROLLANDBOOLEAN);
									ele = cp.getHeaderTextForPage(projectName, PageName.Object1PagePopup, cp.getNewButtonFromTask(projectName, PlusNewButton.EntityOrAccountNewButton), action.BOOLEAN, 10);
									if (ele!=null) {
										log(LogStatus.INFO,"New Entity/Account PopUp is opened",YesNo.No);	
									} else {
										sa.assertTrue(false,"New Entity/Account PopUp is not opened");
										log(LogStatus.SKIP,"New Entity/Account PopUp is not opened",YesNo.Yes);
									}
									driver.close();
									driver.switchTo().window(parentID);
								} else {
									sa.assertTrue(false,"No new window is open so cannot verify New Entity/Account PopUP Landing Page");
									log(LogStatus.SKIP,"No new window is open so cannot verify New Entity/Account PopUP Landing Page",YesNo.Yes);
								}
							} else {
								sa.assertTrue(false,"Not Able to Click on New Entity/Account Button for "+PageLabel.Related_Associations.toString());
								log(LogStatus.SKIP,"Not Able to Click on New Entity/Account Button for "+PageLabel.Related_Associations.toString(),YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"Not Able to Enter Value to Related Associations Text Box");
							log(LogStatus.SKIP,"Not Able to Enter Value to Related Associations Text Box",YesNo.Yes);	
						}
						

						// New Test Custom Object Button For Related Associations Label 
						switchToDefaultContent(driver);
						if (cp.SelectRelatedAssociationsdropdownButton(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, action.SCROLLANDBOOLEAN, 20)) {
							log(LogStatus.SKIP,"Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
							ThreadSleep(2000);	
							
							ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN,15);
							if (clickUsingJavaScript(driver, ele, "Related Associations Text Label", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Enter Value to Related Associations Text Box",YesNo.Yes);	
								ThreadSleep(1000);
								ele = cp.getNewButtonElementFromTask(projectName, PageName.Object2Page, PlusNewButton.TestCustomObjectNewButton, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 15);
								if (clickUsingJavaScript(driver, ele, "New Test Object Button for Label : "+PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"Clicked on New Test Object Button for "+PageLabel.Related_Associations.toString(),YesNo.No);	
									ThreadSleep(1000);
									parentID = switchOnWindow(driver);
									if (parentID!=null) {
										log(LogStatus.INFO,"New window is open Going to verify Test Object  PopUP Landing Page ",YesNo.Yes);
										ThreadSleep(1000);
										//click next button if present(record type), if not then check header
										click(driver, cp.getContinueOrNextButton(projectName, 5), "next button", action.SCROLLANDBOOLEAN);
									
										ele = cp.getHeaderTextForPage(projectName, PageName.Object1PagePopup, cp.getNewButtonFromTask(projectName, PlusNewButton.TestCustomObjectNewButton), action.BOOLEAN, 10);
										if (ele!=null) {
											log(LogStatus.INFO,"New Test Object PopUp is opened",YesNo.No);	
										} else {
											sa.assertTrue(false,"New Test Object PopUp is not opened");
											log(LogStatus.SKIP,"New Test Object PopUp is not opened",YesNo.Yes);
										}
										driver.close();
										driver.switchTo().window(parentID);
									} else {
										sa.assertTrue(false,"No new window is open so cannot verify New Test Object PopUP Landing Page");
										log(LogStatus.SKIP,"No new window is open so cannot verify New Test Object PopUP Landing Page",YesNo.Yes);
									}
								} else {
									sa.assertTrue(false,"Not Able to Click on New Test Object Button for "+PageLabel.Related_Associations.toString());
									log(LogStatus.SKIP,"Not Able to Click on New Test Object Button for "+PageLabel.Related_Associations.toString(),YesNo.Yes);
								}
							}else {
								sa.assertTrue(false,"Not Able to Enter Value to Related Associations Text Box");
								log(LogStatus.SKIP,"Not Able to Enter Value to Related Associations Text Box",YesNo.Yes);	
							}
							
						} else {
							sa.assertTrue(false,"Not Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
						}
						
						
						//  

						if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_Task2LogACallSubject, "Subject", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
							ThreadSleep(1000);
							if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), tomorrowsDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
								ThreadSleep(1000);
								ele=cp.geDropdownOnTaskPopUp(projectName, PageLabel.Priority.toString(), action.SCROLLANDBOOLEAN, 10);
								if (click(driver, ele, "Drop Down : "+PageLabel.Priority.toString(),action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on "+PageLabel.Priority.toString()+" Drop Down", YesNo.Yes);	
									ThreadSleep(2000);
									
									if (cp.SelectDropDownValue(projectName, PageName.Object2Page, PageLabel.Priority.toString(), Smoke_Task2LogACallPriority, action.SCROLLANDBOOLEAN, 10)) {
										log(LogStatus.INFO, "Selected : "+Smoke_Task2LogACallPriority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);	
										ThreadSleep(1000);
										
									} else {
										log(LogStatus.ERROR, "Not ABle to Select : "+Smoke_Task2LogACallPriority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);
										sa.assertTrue(false,"Not ABle to Select : "+Smoke_Task2LogACallPriority+" For Label : "+PageLabel.Priority.toString() );
									}
									appLog.info(">>>>");
									//scn.next();
									if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
										ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask13", excelLabel.Due_Date);
										log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
										ThreadSleep(2000);
										ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_Task2LogACallSubject, action.BOOLEAN, 10);
										
												String[][] fieldsWithValues= {{PageLabel.Subject.toString(),Smoke_Task2LogACallSubject},
												{PageLabel.Name.toString(),""},
												{PageLabel.Due_Date.toString(),date},
												{PageLabel.Priority.toString(),Smoke_Task2LogACallPriority},
												{PageLabel.Related_Associations.toString(),"Assign Multiple Associations"}};
										
										if (clickUsingJavaScript(driver, ele, Smoke_Task2LogACallSubject, action.BOOLEAN)) {
											ThreadSleep(10000);
											if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.SCROLLANDBOOLEAN, 30)) {
												log(LogStatus.INFO, "successfully verified all labels on task page", YesNo.No);
											}
											else {
												log(LogStatus.ERROR, "could not verif all labels on task page", YesNo.Yes);
												sa.assertTrue(false,"could not verif all labels on task page");
											}
										} else {
											log(LogStatus.ERROR, "created task not clickable", YesNo.Yes);
											sa.assertTrue(false,"created task not clickable");
									
										}
									}
									else {
										log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
										sa.assertTrue(false,"save button is not clickable so task not created" );
									}
									
								} else {
									log(LogStatus.ERROR, "Click on "+PageLabel.Priority.toString()+" Drop Down", YesNo.Yes);
									sa.assertTrue(false,"Click on "+PageLabel.Priority.toString()+" Drop Down" );
								}
							}else {
								log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
								sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
							}
						}
						else {
							log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
						}


					} else {
						sa.assertTrue(false,"Not Able to Click on log call button on Sub Tab : "+RelatedTab.Activities);
						log(LogStatus.SKIP,"Not Able to Click on log call button on Sub Tab : "+RelatedTab.Activities,YesNo.Yes);
					}

			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact Not Found : "+contactName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc022_VerifyEditLogACall_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		String olddate=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Due_Date);
		String date=tomorrowsDate;
		WebElement ele=null;
		ShowMoreAction sma[]= {ShowMoreAction.Edit,ShowMoreAction.Delete,ShowMoreAction.FollowUpTask,ShowMoreAction.Show_More};
		String[] taskUIdata= {Smoke_Task1LogACallSubject,"--None--",Smoke_TaskINS3Name+","+Smoke_TaskFund1Name+","+taskCustomObj1Name,Smoke_Task1LogACallComment,olddate,Smoke_Task1LogACallPriority,Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,Status.Completed.toString()};
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cop.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 10)) {
				log(LogStatus.INFO,"Clicked on custom : "+taskCustomObj1Name,YesNo.No);
				ThreadSleep(1000);
				ThreadSleep(2000);
				ip.clickOnShowMoreActionForTaskOnly(projectName, PageName.Object2Page, Smoke_Task1LogACallSubject);
				for (int i=0;i<sma.length;i++) {
					ele=ip.findActionDropdownElement(projectName, PageName.Object2Page, Smoke_Task1LogACallSubject, sma[i]);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of "+sma[i], YesNo.No);
					}
					else {
						log(LogStatus.INFO, "not present: "+sma[i], YesNo.No);
						sa.assertTrue(false, "not present: "+sma[i]);
					}

				}
				ip.clickOnShowMoreActionForTaskOnly(projectName, PageName.Object2Page, Smoke_Task1LogACallSubject);
				ThreadSleep(2000);
				ele=ip.selectShowMoreActionForTaskInNextSteps(projectName, PageName.Object2Page, Smoke_Task1LogACallSubject, ShowMoreAction.Edit);
				if (ele!=null) {
					if (click(driver, ele, "task page", action.BOOLEAN)) {
						flag=true;
					}
					else {
						log(LogStatus.ERROR, "could not click on task link",YesNo.Yes);
						sa.assertTrue(false, "could not click on task link");
					}
				}else {
					log(LogStatus.ERROR, "task link not found",YesNo.Yes);
					sa.assertTrue(false, "task link not found");
				}
				if(flag) {

					if (ip.verifyUIOfCreateNewTaskWindow(projectName,crmUser1FirstName+" "+crmUser1LastName,taskUIdata,false )) {
						log(LogStatus.INFO, "successfully verified task ui edit mode", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify create new task ui", YesNo.Yes);
						sa.assertTrue(false, "could not verify create new task ui");
					}
					if (ip.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(), false, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.SCROLLANDBOOLEAN, 15)) {
						log(LogStatus.INFO, "clicked on cross icon of contact 3",YesNo.No);	

					} else {
						sa.assertTrue(false,"could not click on cross icon of contact 3");
						log(LogStatus.ERROR, "could not click on cross icon of contact 3",YesNo.Yes);

					}
					if (ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(), TabName.TaskTab, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, action.SCROLLANDBOOLEAN, 15)) {
						log(LogStatus.INFO, "successfully selected contact 4 for name field", YesNo.No);
					} else {
						sa.assertTrue(false,"could not select contact 4 for name field");
						log(LogStatus.ERROR, "could not select contact 4 for name field",YesNo.Yes);
					}
					//cross button to ins 3 in RA
					if (ip.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Related_Associations.toString(), true, Smoke_TaskINS3Name, action.SCROLLANDBOOLEAN, 15)) {
						log(LogStatus.INFO, "clicked on cross icon of ins 3", YesNo.No);

					} else {
						sa.assertTrue(false,"could not click on cross icon of ins 3");
						log(LogStatus.ERROR, "could not click on cross icon of ins 3",YesNo.Yes);
					}
					//cross button to fund/deal1 in RA
					if (ip.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Related_Associations.toString(), false, Smoke_TaskFund1Name, action.SCROLLANDBOOLEAN, 15)) {
						log(LogStatus.INFO, "clicked on cross icon of "+Smoke_TaskFund1Name, YesNo.No);

					} else {
						sa.assertTrue(false,"could not click on cross icon of "+Smoke_TaskFund1Name);
						log(LogStatus.ERROR, "could not click on cross icon of "+Smoke_TaskFund1Name,YesNo.Yes);
					}
					//cross button to test custom obj 1 in RA
					if (ip.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Related_Associations.toString(), false, taskCustomObj1Name, action.SCROLLANDBOOLEAN, 15)) {
						log(LogStatus.INFO, "clicked on cross icon of "+taskCustomObj1Name, YesNo.No);

					} else {
						sa.assertTrue(false,"could not click on cross icon of "+taskCustomObj1Name);
						log(LogStatus.ERROR, "could not click on cross icon of "+taskCustomObj1Name,YesNo.Yes);
					}
					//select test custom obj 2 in RA
					if (ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab,taskCustomObj2Name, action.SCROLLANDBOOLEAN, 15)) {
						log(LogStatus.INFO, "successfully selected taskCustomObj2Name for Related_Associations field", YesNo.No);
					} else {
						sa.assertTrue(false,"could not select taskCustomObj2Name for Related_Associations field");
						log(LogStatus.ERROR, "could not select taskCustomObj2Name for Related_Associations field",YesNo.Yes);
					}

					if (tp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Assigned_To.toString(),false, crmUser1FirstName+" "+crmUser1LastName, action.SCROLLANDBOOLEAN, 15)) {
						log(LogStatus.INFO, "Clicked on Cross Button against : "+crmUser1LastName+" For Label : "+PageLabel.Assigned_To.toString(),YesNo.No);	

						ThreadSleep(2000);
						// Assigned To
						String owner = AdminUserFirstName+" "+AdminUserLastName;
						flag=tp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(), TabName.TaskTab, owner, action.BOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+owner+" For  Drown Down Value For Label "+PageLabel.Assigned_To,YesNo.No);
							ThreadSleep(1000);
						}
						else {
							sa.assertTrue(false,"could not select "+owner+" for assigned to field");
							log(LogStatus.ERROR, "could not select "+owner+" for assigned to field",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"could not click cross on "+ crmUser1FirstName+" "+crmUser1LastName+" for assigned to field");
						log(LogStatus.ERROR, "could not click cross on "+ crmUser1FirstName+" "+crmUser1LastName+" for assigned to field",YesNo.Yes);
					}
					if (ip.selectDropDownValueonTaskPopUp(projectName, PageName.TestCustomObjectPage, PageLabel.Priority.toString(),Smoke_Task1LogACallUpdatedPriority, action.SCROLLANDBOOLEAN, 15)) {
						log(LogStatus.INFO,"Selected "+Smoke_Task1LogACallUpdatedPriority+" For  drop Down Value For Label "+PageLabel.Priority,YesNo.No);
					}
					else {
						sa.assertTrue(false,"could not click on "+ Smoke_Task1LogACallUpdatedPriority+" for Priority field");
						log(LogStatus.ERROR, "could not click on "+ Smoke_Task1LogACallUpdatedPriority+" for Priority field",YesNo.Yes);
					}
					if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), tomorrowsDate, "due date", action.SCROLLANDBOOLEAN)) {
						if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_Task1LogACallUpdatedSubject,  PageLabel.Subject.toString(), action.SCROLLANDBOOLEAN)) {
							if (click(driver, ip.getCustomTabSaveBtn(projectName, 10),"save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "successfully clicked on save and edited task", YesNo.No);
								ExcelUtils.writeData(tomorrowsDate, "Task", excelLabel.Variable_Name, "AATask12", excelLabel.Due_Date);
								
										String relatedAssoctaion=tp.Comment(projectName, PageLabel.Related_Associations, taskCustomObj2Name);
										String comment = relatedAssoctaion+"\n"+TaskPagePageErrorMessage.Dots+"\n"+Smoke_Task1LogACallComment;

										String[][] fieldsWithValues= {{PageLabel.Assigned_To.toString(),AdminUserFirstName+" "+AdminUserLastName},
												{PageLabel.Subject.toString(),Smoke_Task1LogACallUpdatedSubject},
												{PageLabel.Name.toString(), Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName},
												{PageLabel.Due_Date.toString(),date},
												{PageLabel.Comments.toString(),comment},
												{PageLabel.Priority.toString(),Smoke_Task1LogACallUpdatedPriority},
												{PageLabel.Related_Associations.toString(),taskCustomObj2Name}};

											ThreadSleep(10000);
											if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.SCROLLANDBOOLEAN, 30)) {
												log(LogStatus.INFO, "successfully verified all labels on task page", YesNo.No);
											}
											else {
												log(LogStatus.ERROR, "could not verif all labels on task page", YesNo.Yes);
												sa.assertTrue(false,"could not verif all labels on task page");
											}
								 
							}
							else {
								sa.assertTrue(false,"could not click cross on save, so cannot edit task");
								log(LogStatus.ERROR, "could not click cross on save, so cannot edit task",YesNo.Yes);
							}
						}
						else {
							sa.assertTrue(false,"subject field is visible, so cannot edit task");
							log(LogStatus.ERROR, "subject field is visible, so cannot edit task",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"due date field is visible, so cannot edit task");
						log(LogStatus.ERROR, "due date field is visible, so cannot edit task",YesNo.Yes);
					}


				}
				else {
					sa.assertTrue(false,"task link on custom object page not clickable, so cannot edit task");
					log(LogStatus.ERROR, "task link on custom object page not clickable, so cannot edit task",YesNo.Yes);
				}

			}else {
				log(LogStatus.ERROR, "could not find new custom object record on custom tab",YesNo.Yes);
				sa.assertTrue(false, "could not find new custom object record on customtab");
			}
		}else {
			log(LogStatus.ERROR, "custom object tab is not clickable",YesNo.Yes);
			sa.assertTrue(false, "custom object tab is not clickable");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc022_VerifyEditLogACall_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		String parentID=null;
		List<String> taskstd1=new LinkedList<String>();
		String task12Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Due_Date);
		
		taskstd1.add(Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
		taskstd1.add(Smoke_TaskINS3Name+" +2");
		taskstd1.add(Status.Completed.toString());
		taskstd1.add(crmUser1FirstName+" "+crmUser1LastName);
		taskstd1.add("");
		taskstd1.add(Activity.Call.toString());
		taskstd1.add(Links.View.toString());
		WebElement ele=null;
		TabName tabName[]= {TabName.Object1Tab,TabName.Object1Tab,TabName.Object3Tab,TabName.TestCustomObjectTab,TabName.Object2Tab,TabName.Object2Tab};
		PageName pageName[]= {PageName.Object1Page,PageName.Object1Page,PageName.Object3Page,PageName.TestCustomObjectPage,PageName.Object2Page,PageName.Object2Page};
		String records[]= {Smoke_TaskINS3Name,Smoke_TaskINS1Name,Smoke_TaskFund1Name,taskCustomObj1Name,Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName};
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		for (int i=0;i<tabName.length;i++) {
			if (lp.clickOnTab(projectName, tabName[i])) {
				if (ip.clickOnAlreadyCreatedItem(projectName, tabName[i], records[i], 10)) {

					ele = ip.getElementForActivityTimeLineTask(projectName, pageName[i],ActivityType.Past, Smoke_Task1LogACallUpdatedSubject, SubjectElement.SubjectLink, 5);
					if (ele==null) {
						log(LogStatus.INFO, "successfully verified absence of "+Smoke_Task1LogACallUpdatedSubject, YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "task "+Smoke_Task1LogACallUpdatedSubject +"is present but it should not be on "+pageName[i], YesNo.Yes);
						sa.assertTrue(false,"task "+Smoke_Task1LogACallUpdatedSubject +"is present but it should not be on "+pageName[i]);
					}


				}
				else {
					log(LogStatus.ERROR, records[i]+" is not found, so could not verify activities task data", YesNo.Yes);
					sa.assertTrue(false,records[i]+" is not found, so could not verify activities task data" );
				}
			}
			else {
				log(LogStatus.ERROR, tabName[i]+" is not clickable, so could not verify activities task data", YesNo.Yes);
				sa.assertTrue(false,tabName[i]+" is not clickable, so could not verify activities task data" );
			}
		}
		

		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab,  Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 10)) {
				String msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName,null, 0);

				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, Smoke_Task1LogACallUpdatedSubject,msg1, task12Date, false,"",false, "", 10);

				ele = ip.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task1LogACallUpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject);
				}
				ele=ip.selectShowMoreActionForTaskInNextSteps(projectName, PageName.Object2Page, Smoke_Task1LogACallUpdatedSubject, ShowMoreAction.Show_More);
				if (ele!=null) {
					if (click(driver, ele, "show more", action.BOOLEAN)) {
						parentID=switchOnWindow(driver);
						if (parentID!=null) {
							String relatedAssoctaion=tp.Comment(projectName, PageLabel.Related_Associations, taskCustomObj2Name);
							String comment = relatedAssoctaion+"\n"+TaskPagePageErrorMessage.Dots+"\n"+Smoke_Task1LogACallComment;

							String[][] fieldsWithValues= {{PageLabel.Assigned_To.toString(),AdminUserFirstName+" "+AdminUserLastName},
									{PageLabel.Subject.toString(),Smoke_Task1LogACallUpdatedSubject},
									{PageLabel.Name.toString(), Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName},
									{PageLabel.Due_Date.toString(),tomorrowsDate},
									{PageLabel.Comments.toString(),comment},
									{PageLabel.Priority.toString(),Smoke_Task1LogACallUpdatedPriority},
									{PageLabel.Related_Associations.toString(),taskCustomObj2Name}};

							ThreadSleep(10000);
							if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.SCROLLANDBOOLEAN, 30)) {
								log(LogStatus.INFO, "successfully verified all labels on task page", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verif all labels on task page", YesNo.Yes);
								sa.assertTrue(false,"could not verif all labels on task page");
							}
							driver.close();
							driver.switchTo().window(parentID);
						}else {
							log(LogStatus.ERROR, "could not find new window to switch",YesNo.Yes);
							sa.assertTrue(false, "could not find new window to switch");
						}
					}
					else {
						log(LogStatus.ERROR, "could not click on task link",YesNo.Yes);
						sa.assertTrue(false, "could not click on task link");
					}
				}else {
					log(LogStatus.ERROR, "task link not found",YesNo.Yes);
					sa.assertTrue(false, "task link not found");
				}
			}
			else {
				log(LogStatus.ERROR, "contact4 is not found, so could not verify activities task data", YesNo.Yes);
				sa.assertTrue(false,"contact4 is not found, so could not verify activities task data" );
			}
		}
		else {
			log(LogStatus.ERROR, "Object2Tab tab is not clickable, so could not verify activities task data", YesNo.Yes);
			sa.assertTrue(false,"Object2Tab tab is not clickable, so could not verify activities task data" );
		}
		
	
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 10)) {

				String msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName,Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);

				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, Smoke_Task1LogACallUpdatedSubject,msg1, task12Date, false,"",false, "", 10);
				ele = ip.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task1LogACallUpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject);
				}
				ele = ip.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task1LogACallUpdatedSubject, SubjectElement.SubjectLink, 10);
				scrollDownThroughWebelement(driver, ele, Smoke_Task1LogACallUpdatedSubject);
				if (click(driver, ele, Smoke_Task1LogACallUpdatedSubject, action.SCROLLANDBOOLEAN)) {
					

						String relatedAssoctaion=tp.Comment(projectName, PageLabel.Related_Associations, taskCustomObj2Name);
						String comment = relatedAssoctaion+"\n"+TaskPagePageErrorMessage.Dots+"\n"+Smoke_Task1LogACallComment;

						String[][] fieldsWithValues= {{PageLabel.Assigned_To.toString(),AdminUserFirstName+" "+AdminUserLastName},
								{PageLabel.Subject.toString(),Smoke_Task1LogACallUpdatedSubject},
								{PageLabel.Name.toString(), Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName},
								{PageLabel.Due_Date.toString(),tomorrowsDate},
								{PageLabel.Comments.toString(),comment},
								{PageLabel.Priority.toString(),Smoke_Task1LogACallUpdatedPriority},
								{PageLabel.Related_Associations.toString(),taskCustomObj2Name}};

						ThreadSleep(10000);
						if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.SCROLLANDBOOLEAN, 30)) {
							log(LogStatus.INFO, "successfully verified all labels on task page", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verif all labels on task page", YesNo.Yes);
							sa.assertTrue(false,"could not verif all labels on task page");
						}
					
				}else {
					log(LogStatus.ERROR, "could not click on task subject link", YesNo.Yes);
					sa.assertTrue(false,"could not click on task subject link" );
				}
			}
			else {
				log(LogStatus.ERROR, Smoke_TaskINS2Name+" is not found, so could not verify activities task data", YesNo.Yes);
				sa.assertTrue(false,Smoke_TaskINS2Name+" is not found, so could not verify activities task data" );
			}
		}
		else {
			log(LogStatus.ERROR, TabName.Object1Tab+" is not clickable, so could not verify activities task data", YesNo.Yes);
			sa.assertTrue(false,TabName.Object1Tab+" is not clickable, so could not verify activities task data" );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc023_CreateStandardCallAndVerifyCreatedCallInActivitiesTab_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		System.out.println(Smoke_Task2UpdatedSubject);
		System.out.println(Smoke_Task2UpdatedPriority);
		WebElement ele ;
		String date=todaysDate;
		
		if (click(driver, gp.getGlobalActionIcon(projectName, 15), "Global Action Related item", action.BOOLEAN)) {
			log(LogStatus.INFO,"Clicked on Global Action Related item",YesNo.No);
			if (clickUsingJavaScript(driver, gp.getActionItem(projectName, GlobalActionItem.Log_a_Call, 15), "log call Link", action.BOOLEAN)) {
				log(LogStatus.INFO,"Clicked on log call Link",YesNo.Yes);
				ThreadSleep(2000);	

				ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TaskPage, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN,5);

				if (ele==null) {
					log(LogStatus.INFO,"Related Association Field is not present",YesNo.No);	
				} else {
					sa.assertTrue(false,"Related Association Field should not be present");
					log(LogStatus.SKIP,"Related Association Field should not be present",YesNo.Yes);
				}

				// subject
				ele=gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.Log_a_Call, PageLabel.Subject.toString(),10);
				
				try {
					ele.sendKeys(" ");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (sendKeys(driver, ele, Smoke_TaskSTDLogACall1Subject, "Subject", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
					ThreadSleep(1000);

					// Due Date
					if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), todaysDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
						ThreadSleep(1000);

						// comment 
						if (sendKeys(driver, ip.getcommentsTextBox(projectName, 10), Smoke_TaskSTDLogACall1Comment, "comments", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.ERROR, "entered value in Comment Text Area", YesNo.Yes);	
						}
						else {
							log(LogStatus.ERROR, "comments textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"comments textbox is not visible so task could not be created" );
						}


						ele=ip.getDropdownOnTaskPopUp(projectName, PageName.NewEventPopUp, PageLabel.Status.toString(), action.SCROLLANDBOOLEAN, 10);
						if (click(driver, ele, "Drop Down : "+PageLabel.Status.toString(),action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on "+PageLabel.Status.toString()+" Drop Down", YesNo.Yes);	
							ThreadSleep(2000);
							
							if (ip.SelectDropDownValue(projectName, PageName.NewEventPopUp, PageLabel.Status.toString(), Smoke_TaskSTDLogACall1Status, action.SCROLLANDBOOLEAN, 10)) {
								log(LogStatus.INFO, "Selected : None For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
								ThreadSleep(1000);
								
							} else {
								log(LogStatus.ERROR, "Not ABle to Select :  For Label : "+PageLabel.Status.toString(), YesNo.Yes);
								sa.assertTrue(false,"Not ABle to Select :  For Label : "+PageLabel.Status.toString() );
							}
						}
						// Name
						ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TaskPage, PageLabel.Name.toString(), action.SCROLLANDBOOLEAN,15);
						if (sendKeys(driver, ele, contactName,"Name Text Label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Enter Value to Name Text Box : "+contactName,YesNo.Yes);	
							ThreadSleep(1000);
							ele =  cp.getContactNameOrRelatedAssociationNameOnTask(projectName, PageName.TaskPage, PageLabel.Name.toString(),contactName, action.SCROLLANDBOOLEAN,15);
							if (click(driver, ele, "Select Name From Label : "+PageLabel.Name, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Clicked on : "+contactName,YesNo.Yes);
							} else {
								sa.assertTrue(false,"Not Able to Click on : "+contactName);
								log(LogStatus.SKIP,"Not Able to Click on : "+contactName,YesNo.Yes);	
							}

						}else {
							sa.assertTrue(false,"Not Able to Enter Value to Name Text Box : "+contactName);
							log(LogStatus.SKIP,"Not Able to Enter Value to Name Text Box : "+contactName,YesNo.Yes);	
						}



						// Related To
						click(driver, ip.getrelatedAssociationsdropdownButton(projectName, PageName.TaskPage,PageLabel.Related_To.toString()
								, action.SCROLLANDBOOLEAN, 10),"dropdown button", action.SCROLLANDBOOLEAN);
						if (cp.SelectRelatedAssociationsdropdownButton(projectName, PageName.TaskPage, PageLabel.Related_To.toString(), TabName.TestCustomObjectTab, action.SCROLLANDBOOLEAN, 20)) {
							log(LogStatus.INFO,"Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
							ThreadSleep(2000);	

							ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TaskPage, PageLabel.Related_To.toString(), action.SCROLLANDBOOLEAN,15);
							if (sendKeys(driver, ele,taskCustomObj1Name, "Related To Text Label", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Enter Value to Related To Text Box : "+taskCustomObj1Name,YesNo.Yes);	
								ThreadSleep(1000);

								ele =  cp.getContactNameOrRelatedAssociationNameOnTask(projectName, PageName.TaskPage, PageLabel.Related_To.toString(),taskCustomObj1Name, action.SCROLLANDBOOLEAN,15);
								if (click(driver, ele, "Select TestCustomObject From Label : "+PageLabel.Related_To, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"Clicked on : "+taskCustomObj1Name,YesNo.Yes);
								} else {
									sa.assertTrue(false,"Not Able to Click on : "+taskCustomObj1Name);
									log(LogStatus.SKIP,"Not Able to Click on : "+taskCustomObj1Name,YesNo.Yes);	
								}


							}else {
								sa.assertTrue(false,"Not Able to Enter Value to Related To Text Box : "+taskCustomObj1Name);
								log(LogStatus.SKIP,"Not Able to Enter Value to Related To Text Box : "+taskCustomObj1Name,YesNo.Yes);	
							}

						} else {
							sa.assertTrue(false,"Not Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
						}
						appLog.info(">>>");
						//scn.next();
						if (clickUsingJavaScript(driver, gp.getSaveButtonForEvent(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task : "+Smoke_TaskSTDLogACall1Subject,  YesNo.Yes);
							ThreadSleep(1000);
							ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask15", excelLabel.Due_Date);
							if (ip.clickOnTab(projectName, TabName.TaskTab)) {
								log(LogStatus.INFO, "Clicked on Refesh Button", YesNo.No);	
								ThreadSleep(1000);
								ele=tp.getTaskNameLinkInSideMMenu(projectName, Smoke_TaskSTDLogACall1Subject, 15);
								
								if (click(driver, ele, Smoke_TaskSTDLogACall1Subject, action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on "+Smoke_TaskSTDLogACall1Subject+" on SideMenu", YesNo.No);	
									ThreadSleep(1000);
									
									String[][] fieldsWithValues= {{PageLabel.Subject.toString(),Smoke_TaskSTDLogACall1Subject},
											{PageLabel.Due_Date.toString(),date},
											{PageLabel.Related_To.toString(),taskCustomObj1Name},
											{PageLabel.Comments.toString(),Smoke_TaskSTDLogACall1Comment},
											{PageLabel.Name.toString(),contactName},
											{PageLabel.Related_Associations.toString(),"Assign Multiple Associations"}};
									
									if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10)) {
										log(LogStatus.INFO, "successfully verified "+Smoke_TaskSTDLogACall1Subject+" on task page", YesNo.No);
									}
									else {
										log(LogStatus.ERROR, "Not Able to verify "+Smoke_TaskSTDLogACall1Subject+" on task page", YesNo.Yes);
										sa.assertTrue(false,"Not Able to verify "+Smoke_TaskSTDLogACall1Subject+" on task page");
									}
								} else {
									log(LogStatus.ERROR, "Not Able to Click on "+Smoke_TaskSTDLogACall1Subject+" on SideMenu", YesNo.Yes);
									sa.assertTrue(false,"Not Able to Click on "+Smoke_TaskSTDLogACall1Subject+" on SideMenu");
								}
								
							} else {
								log(LogStatus.ERROR, "Not Able to Click on task tab", YesNo.Yes);
								sa.assertTrue(false,"Not Able to Click on task tab" );
							}
							
						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created : "+Smoke_TaskSTDLogACall1Subject, YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created : "+Smoke_TaskSTDLogACall1Subject );
						}

					}else {
						log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
					}
				}
				else {
					log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
					sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on New Task Button for show more action");
				log(LogStatus.SKIP,"Not Able to Click on New Task Button for show more action",YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TaskTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TaskTab,YesNo.Yes);
		}
		
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc023_CreateStandardCallAndVerifyCreatedCallInActivitiesTab_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		List<String> taskstd1=new LinkedList<String>();
		taskstd1.add(Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName);
		taskstd1.add(taskCustomObj1Name);
		taskstd1.add(Smoke_TaskSTDLogACall1Status);
		taskstd1.add(crmUser1FirstName+" "+crmUser1LastName);
		taskstd1.add("");
		taskstd1.add(Activity.Call.toString());
		taskstd1.add(Links.View.toString());
		WebElement ele=null;
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Due_Date);
		String task12Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Due_Date);
		if (ip.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, taskCustomObj1Name, 10)) {
				
					
				String msg1=BasePageErrorMessage.LoggedCallMsg(null,Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);

				//msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.TestCustomObjectPage,Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, Smoke_TaskSTDLogACall1Subject,msg1,date, false,"",false, "", 10);
					
				
			}
			else {
				log(LogStatus.ERROR, "created custom record1 is not found, so could not verify activities task data", YesNo.Yes);
				sa.assertTrue(false,"created custom record1 is not found, so could not verify activities task data" );
			}
		}
		else {
			log(LogStatus.ERROR, "custom tab is not clickable, so could not verify activities task data", YesNo.Yes);
			sa.assertTrue(false,"custom tab is not clickable, so could not verify activities task data" );
		}
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 10)) {
				String msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName,null,0);

				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, Smoke_Task1LogACallUpdatedSubject,msg1, task12Date, false,"",false, "", 10);
				ele = ip.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task1LogACallUpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject);
				}
				
				msg1=BasePageErrorMessage.LoggedCallMsg(null,null, 0);

				msg1+=" about "+taskCustomObj1Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, Smoke_TaskSTDLogACall1Subject,msg1, date, false,"",false, "", 10);
				
				//expand all
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Expand_All, 10);
				if (click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Expand_All,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Expand_All);
					log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Expand_All,YesNo.Yes);

				}
				//verify task with description
				msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName,null,0);

				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, Smoke_Task1LogACallUpdatedSubject,msg1, task12Date, false,"",true, Smoke_Task1LogACallComment, 10);
				
				msg1=BasePageErrorMessage.LoggedCallMsg(null,null, 0);

				msg1+=" about "+taskCustomObj1Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, Smoke_TaskSTDLogACall1Subject,msg1, date, false,"",true, Smoke_TaskSTDLogACall1Comment, 10);
				
				//verify collapse all is present and click
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Collapse_All, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified collapse all is present", YesNo.No);
					if (click(driver, ele, ActivityTimeLineItem.Collapse_All.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Collapse_All,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Collapse_All);
						log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Collapse_All,YesNo.Yes);

					}
				}else {
					sa.assertTrue(false,"Not Able to find "+ActivityTimeLineItem.Collapse_All);
					log(LogStatus.ERROR, "Not Able to find "+ActivityTimeLineItem.Collapse_All,YesNo.Yes);

				}

				//verify expand all is present
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Expand_All, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified Expand_All is present after clicking collape", YesNo.No);
				}else {
					sa.assertTrue(false,"Not Able to find "+ActivityTimeLineItem.Expand_All);
					log(LogStatus.ERROR, "Not Able to find "+ActivityTimeLineItem.Expand_All,YesNo.Yes);

				}
				//now every task is collapse
				//verify task specific expand
				ele = ip.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task1LogACallUpdatedSubject, SubjectElement.ExpandIcon, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of expand icon of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.No);
					if (click(driver, ele, "expand task", action.BOOLEAN)) {
						//verify task with description
						msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName,null,0);

						msg1+=" about "+taskCustomObj2Name;
						lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, Smoke_Task1LogACallUpdatedSubject,msg1, task12Date, false,"",true, Smoke_Task1LogACallComment, 10);
						
						ele = ip.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task1LogACallUpdatedSubject, SubjectElement.CollapseIcon, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of collapse icon of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.No);
						}else {
							log(LogStatus.ERROR, "could not verify presence of collapse icon", YesNo.Yes);
							sa.assertTrue(false,"could not verify presence of collapse icon" );
						}
					}else {
						log(LogStatus.ERROR, "could not click on expand icon, so cannot verify description", YesNo.Yes);
						sa.assertTrue(false,"could not click on expand icon, so cannot verify description" );
					}
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of expand icon of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of expand icon of task "+Smoke_Task1LogACallUpdatedSubject);
				}
			}
			else {
				log(LogStatus.ERROR, "contact4 is not found, so could not verify activities task data", YesNo.Yes);
				sa.assertTrue(false,"contact4 is not found, so could not verify activities task data" );
			}
		}
		else {
			log(LogStatus.ERROR, "Object2Tab is not clickable, so could not verify activities task data", YesNo.Yes);
			sa.assertTrue(false,"Object2Tab is not clickable, so could not verify activities task data" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	
	public void AASmokeTc024_VerifyAssignMultipleAssociationsLinkForStandardCall_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		WebElement ele ;
		String parentID;
		String actualValue;
		boolean flag=false;
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Due_Date);
		
		String[] taskUIdata= {Smoke_TaskSTDLogACall1Subject,"--None--","",Smoke_TaskSTDLogACall1Comment,date,Smoke_TaskSTDLogACall1Priority,Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName,Status.Not_Started.toString()};
		
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_TaskSTDLogACall1Subject, SubjectElement.SubjectLink, 5);

				if (ele!=null) {
					log(LogStatus.INFO,Smoke_TaskSTDLogACall1Subject+" link present on Sub Tab : "+RelatedTab.Meetings,YesNo.Yes);	
					scrollDownThroughWebelement(driver, ele,Smoke_TaskSTDLogACall1Subject );
					//if (clickUsingActionClass(driver, ele)) {
					appLog.info(">>>>>");
					//scn.next();
						if (click(driver, ele, Smoke_TaskSTDLogACall1Subject, action.BOOLEAN)) {
						log(LogStatus.INFO,"Clicked on : "+Smoke_TaskSTDLogACall1Subject,YesNo.No);
						ThreadSleep(1000);
						ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_TaskSTDLogACall1Subject, action.BOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO,"Landing Page Verified for : "+Smoke_TaskSTDLogACall1Subject,YesNo.No);	
						} else {
							sa.assertTrue(false,"Landing Page Not Verified for : "+Smoke_TaskSTDLogACall1Subject);
							log(LogStatus.SKIP,"Landing Page Not Verified for : "+Smoke_TaskSTDLogACall1Subject,YesNo.Yes);
						}
						for (int k = 0; k < 3; k++) {

							switchToDefaultContent(driver);
							switchToFrame(driver, 60, tp.getTaskPageFrame(projectName,60));

							ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, TaskPagePageErrorMessage.RelatedAssociationText, action.BOOLEAN, 10);
							if (click(driver, ele, TaskPagePageErrorMessage.RelatedAssociationText, action.BOOLEAN)) {
								log(LogStatus.INFO,"Clicked on Link :  "+TaskPagePageErrorMessage.RelatedAssociationText,YesNo.No);	

								parentID=switchOnWindow(driver);
								if (parentID!=null) {
									log(LogStatus.INFO,"Switch To Edit Task Window",YesNo.No);	
									ThreadSleep(2000);

									ele = tp.getTaskPoUpEditHeader(projectName, 10);
									if (ele!=null) {
										log(LogStatus.INFO,"Edit Header Ele Found",YesNo.No);	
										actualValue=ele.getText().trim();

										actualValue = ele.getText().trim();
										String expectedValue=PageLabel.Edit.toString()+" "+Smoke_TaskSTDLogACall1Subject;
										if (actualValue.contains(PageLabel.Edit.toString()) && actualValue.contains(Smoke_TaskSTDLogACall1Subject)) {
											log(LogStatus.INFO,expectedValue+" matched msg for Edit PopUp", YesNo.No);

										} else {
											log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched msg for Edit PopUp", YesNo.Yes);
											BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched msg for Edit PopUp");
										}

									} else {
										sa.assertTrue(false,"Edit Header Ele Not Found");
										log(LogStatus.SKIP,"Edit Header Ele Not Found",YesNo.Yes);
									}


									if (k==0) {
										
										if (ip.verifyUIOfCreateNewTaskWindow(projectName,crmUser1FirstName+" "+crmUser1LastName,taskUIdata,false)) {
											log(LogStatus.INFO, "successfully verified create new task ui", YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "could not verify create new task ui", YesNo.Yes);
											sa.assertTrue(false, "could not verify create new task ui");
										}
										ele=tp.getcrossIcon(projectName, 5);
									} else if(k==1) {
										ele=tp.getCancelButton(projectName, 5);
									}

									if (k==0 || k==1) {
										if (clickUsingJavaScript(driver, ele, "Cross/Cancel Button",action.BOOLEAN)) {
											log(LogStatus.INFO,"Clicked on Cross/Cancel Button",YesNo.No);	
											flag=true;
										} else {
											sa.assertTrue(false,"Not Able to Click on Cross/Cancel Button");
											log(LogStatus.SKIP,"Not Able to Click on Cross/Cancel Button",YesNo.Yes);
										}	

										if (flag) {
											log(LogStatus.INFO,"No Error",YesNo.No);
										} else {
											sa.assertTrue(false,"Some Error");
											log(LogStatus.SKIP,"Some Error",YesNo.Yes);
										}
										flag=false;
										try {
											driver.switchTo().window(parentID);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}

									if(k==2){


										flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.Object1Tab, Smoke_TaskINS5Name, action.SCROLLANDBOOLEAN, 10);		
										if (flag) {
											log(LogStatus.SKIP,"Selected "+Smoke_TaskINS5Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.Object1Tab)+" For Label "+PageLabel.Related_Associations,YesNo.No);

										} else {
											sa.assertTrue(false,"Not Able to Select "+Smoke_TaskINS5Name+" For Drown Down Value : "+cp.getTabName(projectName, TabName.Object1Tab)+" For Label "+PageLabel.Related_Associations);
											log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskINS5Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.Object1Tab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

										}

										flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.Object3Tab, Smoke_TaskFund2Name, action.SCROLLANDBOOLEAN, 10);		
										if (flag) {
											log(LogStatus.SKIP,"Selected "+Smoke_TaskFund2Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.Object3Tab)+" For Label "+PageLabel.Related_Associations,YesNo.No);

										} else {
											sa.assertTrue(false,"Not Able to Select "+Smoke_TaskFund2Name+" For Drown Down Value : "+cp.getTabName(projectName, TabName.Object3Tab)+" For Label "+PageLabel.Related_Associations);
											log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskFund2Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.Object3Tab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

										}

										flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, taskCustomObj2Name, action.SCROLLANDBOOLEAN, 10);		
										if (flag) {
											log(LogStatus.SKIP,"Selected "+taskCustomObj2Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.No);

										} else {
											sa.assertTrue(false,"Not Able to Select "+taskCustomObj2Name+" For Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations);
											log(LogStatus.SKIP,"Not Able to Select "+taskCustomObj2Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

										}

										// Subject

										if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_TaskSTDLogACall1UpdatedSubject, "Subject", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
											ThreadSleep(1000);

											//  Priority

											ele=cp.getDropdownOnTaskPopUp(projectName, PageName.Object2Page,PageLabel.Priority.toString(), action.SCROLLANDBOOLEAN, 10);
											if (click(driver, ele, "Drop Down : "+PageLabel.Priority.toString(),action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "Select/Click on "+PageLabel.Priority.toString()+" Drop Down", YesNo.Yes);	
												ThreadSleep(2000);

												if (cp.SelectDropDownValue(projectName, PageName.Object2Page, PageLabel.Priority.toString(), Smoke_TaskSTDLogACall1UpdatedPriority, action.SCROLLANDBOOLEAN, 10)) {
													log(LogStatus.ERROR, "Selected : "+Smoke_TaskSTD1UpdatedPriority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);	
													ThreadSleep(1000);

												} else {
													log(LogStatus.ERROR, "Not ABle to Select : "+Smoke_TaskSTD1UpdatedPriority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);
													sa.assertTrue(false,"Not ABle to Select : "+Smoke_TaskSTD1UpdatedPriority+" For Label : "+PageLabel.Priority.toString() );
												}

											} else {
												log(LogStatus.ERROR, "Click on "+PageLabel.Priority.toString()+" Drop Down", YesNo.Yes);
												sa.assertTrue(false,"Click on "+PageLabel.Priority.toString()+" Drop Down" );
											}

											// comment 

											if (sendKeys(driver, ip.getcommentsTextBox(projectName, 10), Smoke_TaskSTDLogACall1UpdatedComment, "comments", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "entered value in Comment Text Area", YesNo.Yes);	
											}
											else {
												log(LogStatus.ERROR, "comments textbox is not visible so task could not be created", YesNo.Yes);
												sa.assertTrue(false,"comments textbox is not visible so task could not be created" );
											}

											System.err.println(">>>>>>>>>>>>>>>>>>>>>>Save Button<<<<<<<<<<<<<<<<<<<<<<<<<");
											// 
											if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {

												log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
												flag=true;
												ThreadSleep(2000);

												ele = cp.getCreatedConfirmationMsg(projectName, 15);
												if (ele!=null) {
													actualValue = ele.getText().trim();
													String expectedValue=tp.taskCreatesMsg(projectName, Smoke_TaskSTD1UpdatedSubject);
													if (actualValue.equals(expectedValue)) {
														log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

													} else {
														log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
														BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
													}
												} else {
													sa.assertTrue(false,"Created Task Msg Ele not Found");
													log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

												}

												switchToDefaultContent(driver);
												switchToFrame(driver, 60, tp.getFrame(PageName.TaskPage,60));

												ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_TaskFund2Name, action.BOOLEAN, 10);
												if (click(driver, ele, Smoke_TaskFund1Name, action.BOOLEAN)) {
													ThreadSleep(5000);
													ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_TaskFund2Name, action.BOOLEAN, 10);
													if (ele!=null) {
														log(LogStatus.INFO, "successfully verified opening of "+Smoke_TaskFund2Name+" detail page", YesNo.No);
													}
													else {
														log(LogStatus.ERROR, "fund detail page could not be opened", YesNo.Yes);
														sa.assertTrue(false,"fund detail page could not be opened" );
													}
													driver.navigate().back();
												}
												else {
													log(LogStatus.ERROR, "fund/deal name is not clickable", YesNo.Yes);
													sa.assertTrue(false,"fund/deal name is not clickable" );

												}
												String[][] fieldsWithValues= {{PageLabel.Subject.toString(),Smoke_TaskSTDLogACall1UpdatedSubject},
														{PageLabel.Priority.toString(),Smoke_TaskSTDLogACall1UpdatedPriority}};

												tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

												// Related Association
												ele = tp.getLabelForTaskInViewMode(projectName, PageName.TaskPage, PageLabel.Related_Associations.toString(), action.BOOLEAN, 10);
												if (ele!=null) {
													actualValue = ele.getText().trim();
													log(LogStatus.INFO,"Actual Value For Related Association Link : "+actualValue, YesNo.No);
													String expectedValue=Smoke_TaskINS5Name+", "+Smoke_TaskFund2Name+", "+taskCustomObj2Name;
													if (actualValue.equals(expectedValue)) {
														log(LogStatus.INFO,expectedValue+" matched FOR Related Association Label", YesNo.No);

													} else {
														log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Related Association Label", YesNo.Yes);
														BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Related Association LABEL");
													}

													// Comment
													ele = tp.getLabelForTaskInViewMode(projectName, PageName.TaskPage, PageLabel.Comments.toString(), action.BOOLEAN, 10);
													if (ele!=null) {
														actualValue = ele.getText().trim();
														log(LogStatus.INFO,"Actual Value For Comment : "+actualValue, YesNo.No);
														String[] commentValue = {expectedValue,TaskPagePageErrorMessage.Dots,Smoke_TaskSTDLogACall1UpdatedComment};
														for (String comment : commentValue) {
															if (actualValue.contains(comment)) {
																log(LogStatus.INFO,comment+" matched for Comment Label", YesNo.No);

															} else {
																log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched for Comment Label", YesNo.Yes);
																BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched for Comment Label");
															}
														}
													} else {
														sa.assertTrue(false,"Comment Label Ele not Found");
														log(LogStatus.SKIP,"Comment Label Ele not Found",YesNo.Yes);
													}

												} else {
													sa.assertTrue(false,"Comment Label Ele not Found");
													log(LogStatus.SKIP,"Comment Label Ele not Found",YesNo.Yes);

												}
												System.err.println(">>>>>>>>>>>>>>>>>>>>>>Save222222222222222 Button<<<<<<<<<<<<<<<<<<<<<<<<<");
												// 	
											}

											else {
												log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
												sa.assertTrue(false,"save button is not clickable so task not created" );
											}



										}
										else {
											log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
											sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
										}


									driver.close();
									driver.switchTo().window(parentID);
									}
								}
								else {
									sa.assertTrue(false,"new window not found, so cannot assign multiple assoc");
									log(LogStatus.SKIP,"new window not found, so cannot assign multiple assoc",YesNo.Yes);
								}
								
							

							} else {
								sa.assertTrue(false,"Not Able to Click on Link :  "+TaskPagePageErrorMessage.RelatedAssociationText);
								log(LogStatus.SKIP,"Not Able to Click on Link :  "+TaskPagePageErrorMessage.RelatedAssociationText,YesNo.Yes);
							}

						}
						
						refresh(driver);
						switchToFrame(driver, 60, tp.getTaskPageFrame(projectName,60));
						String task15Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Due_Date);
						
						ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_TaskFund2Name, action.BOOLEAN, 10);
						if (ele!=null) {
							if (click(driver, ele, Smoke_TaskFund2Name, action.SCROLLANDBOOLEAN)) {
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								String msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
								msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+taskCustomObj2Name;
								lp.verifyActivityAtPastStep2(projectName, PageName.Object3Page,null, Smoke_TaskSTDLogACall1UpdatedSubject,msg1, task15Date, false,"",false, "", 10);
								
							}else {
								sa.assertTrue(false,"Not Able to Click on Link :  "+Smoke_TaskFund2Name);
								log(LogStatus.SKIP,"Not Able to Click on Link :  "+Smoke_TaskFund2Name,YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"Not Able to find Link :  "+Smoke_TaskFund2Name);
							log(LogStatus.SKIP,"Not Able to find Link :  "+Smoke_TaskFund2Name,YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,"Not Able to Click on : "+Smoke_TaskSTD1Subject);
						log(LogStatus.SKIP,"Not Able to Click on : "+Smoke_TaskSTD1Subject,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,Smoke_TaskSTD1Subject+" link not present on Sub Tab : "+RelatedTab.Meetings);
					log(LogStatus.SKIP,Smoke_TaskSTD1Subject+" link not present on Sub Tab : "+RelatedTab.Meetings,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact Not Found : "+contactName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc024_VerifyAssignMultipleAssociationsLinkForStandardCall_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Due_Date);
		String task12Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Due_Date);
		
		WebElement ele=null;
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS5Name, 20)) {
				log(LogStatus.INFO,"Clicked on Object 1: "+Smoke_TaskINS5Name,YesNo.No);
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object1Page, ActivityTimeLineItem.Expand_All, 10);
				if (click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Expand_All,YesNo.No);	
					String msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
					msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
					lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,null, Smoke_TaskSTDLogACall1UpdatedSubject,msg1, date, false,"",true, Smoke_TaskSTDLogACall1UpdatedComment, 10);

				} else {
					sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Expand_All);
					log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Expand_All,YesNo.Yes);
					
				}
			}else {
				sa.assertTrue(false,"Object1 Not Found : "+Smoke_TaskINS5Name);
				log(LogStatus.SKIP,"Object1  Not Found : "+Smoke_TaskINS5Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		if (cp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.TestCustomObjectTab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, taskCustomObj2Name, 20)) {
				log(LogStatus.INFO,"Clicked on Test Custom Object : "+taskCustomObj2Name,YesNo.No);
				ThreadSleep(1000);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}
				String msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.TestCustomObjectPage,taskCustomObj2Name, Smoke_TaskSTDLogACall1UpdatedSubject,msg1, date, false,"",false, "", 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,ActivityType.Past, Smoke_TaskSTDLogACall1UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_TaskSTDLogACall1UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_TaskSTDLogACall1UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_TaskSTDLogACall1UpdatedSubject);
				}

				msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.TestCustomObjectPage,taskCustomObj2Name, Smoke_Task1LogACallUpdatedSubject,msg1, task12Date, false,"",false, "", 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,ActivityType.Past, Smoke_Task1LogACallUpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject);
				}
			} else {
				sa.assertTrue(false,"Test custom object Not Found : "+taskCustomObj2Name);
				log(LogStatus.SKIP,"Test custom object  Not Found : "+taskCustomObj2Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab,YesNo.Yes);
		}

		if (cp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.TestCustomObjectTab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, taskCustomObj1Name, 20)) {
				log(LogStatus.INFO,"Clicked on Test Custom Object : "+taskCustomObj1Name,YesNo.No);
				ThreadSleep(1000);
				String msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+Smoke_TaskINS5Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.TestCustomObjectPage,taskCustomObj1Name, Smoke_TaskSTDLogACall1UpdatedSubject,msg1, date, false,"",false, "", 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,ActivityType.Past, Smoke_TaskSTDLogACall1UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_TaskSTDLogACall1UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_TaskSTDLogACall1UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_TaskSTDLogACall1UpdatedSubject);
				}
			} else {
				sa.assertTrue(false,"Test custom object Not Found : "+taskCustomObj1Name);
				log(LogStatus.SKIP,"Test custom object  Not Found : "+taskCustomObj1Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab,YesNo.Yes);
		}


		switchToDefaultContent(driver);
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 20)) {
				log(LogStatus.INFO,"Clicked on Entity/Account : "+Smoke_TaskINS2Name,YesNo.No);
				ThreadSleep(1000);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}
				String msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_TaskSTDLogACall1UpdatedSubject,msg1, date, false,"",false, "", 10);

				
				msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,taskCustomObj2Name, Smoke_Task1LogACallUpdatedSubject,msg1, task12Date, false,"",false, "", 10);
			} else {
				sa.assertTrue(false,"Entity/Account Not Found : "+Smoke_TaskINS2Name);
				log(LogStatus.SKIP,"Entity/Account  Not Found : "+Smoke_TaskINS2Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}
				String msg1=BasePageErrorMessage.LoggedCallMsg(null, null, 0);
				msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,contactName, Smoke_TaskSTDLogACall1UpdatedSubject,msg1, date, false,"",false, "", 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_TaskSTDLogACall1UpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_TaskSTDLogACall1UpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_TaskSTDLogACall1UpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_TaskSTDLogACall1UpdatedSubject);
				}
				msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName, null, 0);
				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,taskCustomObj2Name, Smoke_Task1LogACallUpdatedSubject,msg1, task12Date, false,"",false, "", 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task1LogACallUpdatedSubject, SubjectElement.RedFlag, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject);
				}

			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact  Not Found : "+contactName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc025_VerifyNewLogACallAtPackageObjectFundOrDeal_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		WebElement ele ;
		boolean flag=false;
	
		RelatedTab relatedTab = RelatedTab.Activities;
		String date=todaysDate;
		
		if (cp.clickOnTab(projectName, TabName.Object3Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object3Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object3Tab, Smoke_TaskFund1Name, 20)) {
				log(LogStatus.INFO,"Clicked on Fund/Deal : "+Smoke_TaskFund1Name,YesNo.No);
				ThreadSleep(1000);
					if (clickUsingJavaScript(driver, cp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, 10), "New task Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Clicked on New task button on Sub Tab : "+RelatedTab.Activities,YesNo.Yes);
						ThreadSleep(2000);	

						ele = cp.getHeaderTextForPage(projectName, PageName.Object3Page,PageLabel.New_Task.toString(), action.BOOLEAN, 30);
						if (ele!=null) {
							log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
						} else {
							sa.assertTrue(false,"New Task PopUp is not opened");
							log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
						}

						ele = cp.getRelatedAssciationDefaultSelectedDropDown(projectName, TabName.Object1Tab, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 20);
						if (ele!=null) {
							log(LogStatus.INFO, "Default Selected For "+PageLabel.Related_Associations.toString()+" Verified",YesNo.No);	


						} else {
							sa.assertTrue(false,"Default Selected object For "+PageLabel.Related_Associations.toString()+" Not Verified");
							log(LogStatus.ERROR, "Default Selected object For "+PageLabel.Related_Associations.toString()+" Not Verified",YesNo.Yes);

						}

						List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.Object3Page, PageLabel.Related_Associations.toString(),false, action.SCROLLANDBOOLEAN, 15);
						if (!eleList.isEmpty() && eleList.size()==1) {
							log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Related_Associations.toString(),YesNo.No);	

							ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(),false, Smoke_TaskFund1Name, action.SCROLLANDBOOLEAN, 15);
							if (ele!=null) {
								log(LogStatus.INFO, Smoke_TaskFund1Name+" is prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.No);	


							} else {
								sa.assertTrue(false,Smoke_TaskFund1Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString());
								log(LogStatus.ERROR, Smoke_TaskFund1Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.Yes);

							}

						} else {
							sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
							log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

						}

						sendKeys(driver, cp.getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_CallSubject, "Subject", action.SCROLLANDBOOLEAN);
							log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
							ThreadSleep(1000);

							//  Priority

					
						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, contactName, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.SKIP,"Selected "+contactName+" For Label "+PageLabel.Name,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+contactName+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"Not Able to Select "+contactName+" For Label "+PageLabel.Name,YesNo.Yes);

						}

						ele=cp.getDropdownOnTaskPopUp(projectName, PageName.Object2Page,PageLabel.Meeting_Type.toString(), action.SCROLLANDBOOLEAN, 10);
						if (click(driver, ele, "Drop Down : "+PageLabel.Meeting_Type.toString(),action.SCROLLANDBOOLEAN)) {
							log(LogStatus.ERROR, "Select/Click on "+PageLabel.Meeting_Type.toString()+" Drop Down", YesNo.Yes);	
							ThreadSleep(2000);

							if (cp.SelectDropDownValue(projectName, PageName.Object2Page, PageLabel.Meeting_Type.toString(), Smoke_CallMeetingType, action.SCROLLANDBOOLEAN, 10)) {
								log(LogStatus.ERROR, "Selected : "+Smoke_CallMeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.Yes);	
								ThreadSleep(1000);

							} else {
								sa.assertTrue(false,"Not Able to Select "+Smoke_CallMeetingType+" For Label "+PageLabel.Meeting_Type);
								log(LogStatus.SKIP,"Not Able to Select "+Smoke_CallMeetingType+" For Label "+PageLabel.Meeting_Type,YesNo.Yes);

							}
						}else {
							sa.assertTrue(false,"Not Able to find Label "+PageLabel.Meeting_Type);
							log(LogStatus.SKIP,"Not Able to find Label "+PageLabel.Meeting_Type,YesNo.Yes);

						}
						
						if (sendKeys(driver, cp.getdueDateTextBoxInNewTask(projectName, 20), "", PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
							ThreadSleep(1000);

						}

							appLog.info(">>>>>");
							if (clickUsingJavaScript(driver, cp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully created task",  YesNo.Yes);


								ele = cp.getCreatedConfirmationMsg(projectName, 15);
								if (ele!=null) {
									String actualValue = ele.getText().trim();
									String expectedValue=tp.taskCreatesMsg(projectName, Smoke_CallSubject);
									if (actualValue.contains(expectedValue)) {
										log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

									} else {
										log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
										BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
									}
								} else {
									sa.assertTrue(false,"Created Task Msg Ele not Found");
									log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

								}
								String msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
								//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
								lp.verifyActivityAtPastStep2(projectName, PageName.TestCustomObjectPage,taskCustomObj2Name, Smoke_CallSubject,msg1, DueDate.No_due_date.toString(), false,"",false, "", 10);
							}
							else {
								log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
								sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
							}

					} else {
						sa.assertTrue(false,"Not Able to Click on New Meeting button on Sub Tab : "+RelatedTab.Meetings);
						log(LogStatus.SKIP,"Not Able to Click on New Meeting button on Sub Tab : "+RelatedTab.Meetings,YesNo.Yes);
					}



			} else {
				sa.assertTrue(false,"Fund/Deal Not Found : "+Smoke_TaskFund1Name);
				log(LogStatus.SKIP,"Fund/Deal  Not Found : "+Smoke_TaskFund1Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc025_VerifyNewLogACallAtPackageObjectFundOrDeal_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		WebElement ele ;
		boolean flag=false;
		List<String> taskstd1=new LinkedList<String>();
		taskstd1.add(contactName);
		taskstd1.add(Smoke_TaskFund1Name);
		taskstd1.add(Status.Completed.toString().replace("_", " "));
		taskstd1.add(owner);
		taskstd1.add(Smoke_CallMeetingType);
		taskstd1.add(Activity.Call.toString());
		taskstd1.add(Links.View.toString());	
		RelatedTab relatedTab = RelatedTab.Activities;
		String task15Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Due_Date);
		String task12Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Due_Date);
		
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 20)) {
				log(LogStatus.INFO,"Clicked on Entity/Account : "+Smoke_TaskINS2Name,YesNo.No);
				ThreadSleep(1000);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}
				String msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_Task1LogACallUpdatedSubject,msg1, task12Date, false,"",false, "", 10);
				
				msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" , "+ Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_TaskSTDLogACall1UpdatedSubject,msg1, task15Date, false,"",false, "", 10);
				
				msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
				msg1+=" about "+ Smoke_TaskFund1Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_CallSubject,msg1, DueDate.No_due_date.toString(), false,"",false, "", 10);
				String parentID=null;
				if (cp.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						cp.verifyingRelatedTabData2(projectName, PageName.Object1Page, relatedTab.Activities, "", Smoke_CallSubject, taskstd1, action.BOOLEAN, 10);
					} else {
						sa.assertTrue(false,"new window not found after clicking view all on page "+Smoke_TaskINS2Name);
						log(LogStatus.SKIP,"new window not found after clicking view all on page "+Smoke_TaskINS2Name,YesNo.Yes);
					}
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					sa.assertTrue(false,"not able to click on view all on page "+Smoke_TaskINS2Name);
					log(LogStatus.SKIP,"not able to click on view all on page "+Smoke_TaskINS2Name,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Entity/Account Not Found : "+Smoke_TaskINS2Name);
				log(LogStatus.SKIP,"Entity/Account  Not Found : "+Smoke_TaskINS2Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc026_VerifyLogaCallAtCustomObjectPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		String task17Date=todaysDate;
		
		WebElement ele ;
		boolean flag=false;
		List<String> taskstd1=new LinkedList<String>();
		taskstd1.add(contactName);
		taskstd1.add(taskCustomObj2Name);
		taskstd1.add(Status.Completed.toString());
		taskstd1.add(AdminUserFirstName+" "+AdminUserLastName);
		taskstd1.add(Smoke_TaskSendLetterMeetingType);
		taskstd1.add(Activity.Call.toString());
		taskstd1.add(Links.View.toString());	
		RelatedTab relatedTab = RelatedTab.Activities;
		String task15Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Due_Date);
		String task12Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Due_Date);
		
		if (cp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.TestCustomObjectTab,YesNo.No);
			ThreadSleep(1000);
			if (cop.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, taskCustomObj2Name, 10)){
				log(LogStatus.INFO,"Clicked on custom tab record : "+taskCustomObj2Name,YesNo.No);
				ThreadSleep(1000);
				refresh(driver);

				if (clickUsingJavaScript(driver,cp.getActivityTimeLineItem(projectName, PageName.TestCustomObjectPage, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, 10), "Log call Button", action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New task button on Sub Tab : "+RelatedTab.Activities,YesNo.Yes);
					ThreadSleep(2000);	

					ele = cp.getHeaderTextForPage(projectName, PageName.TestCustomObjectPage,PageLabel.New_Task.toString(), action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
					} else {
						sa.assertTrue(false,"New Task PopUp is not opened");
						log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
					}

					ele = cp.getRelatedAssciationDefaultSelectedDropDown(projectName, TabName.Object1Tab, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 20);
					if (ele!=null) {
						log(LogStatus.INFO, "Default Selected For "+PageLabel.Related_Associations.toString()+" Verified",YesNo.No);	


					} else {
						sa.assertTrue(false,"Default Selected object For "+PageLabel.Related_Associations.toString()+" Not Verified");
						log(LogStatus.ERROR, "Default Selected object For "+PageLabel.Related_Associations.toString()+" Not Verified",YesNo.Yes);

					}

					List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(),false, action.SCROLLANDBOOLEAN, 15);
					if (!eleList.isEmpty() && eleList.size()==1) {
						log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Related_Associations.toString(),YesNo.No);	

						ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(),false, taskCustomObj2Name, action.SCROLLANDBOOLEAN, 15);
						if (ele!=null) {
							log(LogStatus.INFO, taskCustomObj2Name+" is prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.No);	


						} else {
							sa.assertTrue(false,taskCustomObj2Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString());
							log(LogStatus.ERROR, taskCustomObj2Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.Yes);

						}

					} else {
						sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
						log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

					}

					// Assigned_To Cross Button Click
					if (tp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(),false, owner, action.SCROLLANDBOOLEAN, 15)) {
						log(LogStatus.INFO, "Clicked on Cross Button against : "+owner+" For Label : "+PageLabel.Assigned_To.toString(),YesNo.No);	

						ThreadSleep(2000);
						// Assigned To
						owner = AdminUserFirstName+" "+AdminUserLastName;
						flag=tp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(), TabName.TestCustomObjectTab, owner, action.BOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+owner+" For  Drown Down Value For Label "+PageLabel.Assigned_To,YesNo.No);
							ThreadSleep(1000);

						} else {
							sa.assertTrue(false,"could not select admin name in "+PageLabel.Assigned_To.toString());
							log(LogStatus.ERROR, "could not select admin name in "+PageLabel.Assigned_To.toString(),YesNo.Yes);

						}
					}
					else {
						sa.assertTrue(false,"cross button could not be clicked on "+PageLabel.Assigned_To.toString());
						log(LogStatus.ERROR, "cross button could not be clicked on "+PageLabel.Assigned_To.toString(),YesNo.Yes);

					}

					//name

					flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(), TabName.Object2Tab, contactName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+contactName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+contactName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+contactName+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					ele=cp.getDropdownOnTaskPopUp(projectName, PageName.Object2Page,PageLabel.Meeting_Type.toString(), action.SCROLLANDBOOLEAN, 10);
					if (click(driver, ele, "Drop Down : "+PageLabel.Meeting_Type.toString(),action.SCROLLANDBOOLEAN)) {
						log(LogStatus.ERROR, "Select/Click on "+PageLabel.Meeting_Type.toString()+" Drop Down", YesNo.Yes);	
						ThreadSleep(2000);

						if (cp.SelectDropDownValue(projectName, PageName.Object2Page, PageLabel.Meeting_Type.toString(), Smoke_TaskSendLetterMeetingType, action.SCROLLANDBOOLEAN, 10)) {
							log(LogStatus.ERROR, "Selected : "+Smoke_TaskSendLetterMeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.Yes);	
							ThreadSleep(1000);


						} else {
							sa.assertTrue(false,"meeting type could not be selected ");
							log(LogStatus.ERROR, "meeting type could not be selected ",YesNo.Yes);

						}
					} else {
						sa.assertTrue(false,"meeting type could not be selected ");
						log(LogStatus.ERROR, "meeting type could not be selected ",YesNo.Yes);

					}
					// Subject
					ele= cp.getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(),20);
					scrollDownThroughWebelement(driver, ele, "subject");
					if (sendKeys(driver,ele, Smoke_TaskSendLetterSubject, PageLabel.Subject.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
						ThreadSleep(1000);
						if (sendKeys(driver, cp.getcommentsTextBox(projectName, 10), Smoke_TaskSendLetterComment, "comments", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to comments Text Box "+Smoke_TaskSendLetterComment, YesNo.Yes);

						}else {
							sa.assertTrue(false,"comments text box is not visible");
							log(LogStatus.ERROR,"comments text box is not visible",YesNo.Yes);

						}
						appLog.info(">>>>>");
						//scn.next();
						if (clickUsingJavaScript(driver, cp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);

							ExcelUtils.writeData(task17Date, "Task", excelLabel.Variable_Name, "AATask17", excelLabel.Due_Date);

							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, Smoke_TaskSendLetterSubject);
								if (actualValue.contains(expectedValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}
							appLog.info(">>>>>");
							//	 
							String msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
							//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
							lp.verifyActivityAtPastStep2(projectName, PageName.TestCustomObjectPage,taskCustomObj2Name, Smoke_Task1LogACallUpdatedSubject,msg1, task12Date, false,"",false, "", 10);
							ele = tp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,ActivityType.Past, Smoke_Task1LogACallUpdatedSubject, SubjectElement.RedFlag, 10);
							if (ele!=null) {
								log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject,YesNo.Yes);
								sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task1LogACallUpdatedSubject);
							}

							msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
							//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
							lp.verifyActivityAtPastStep2(projectName, PageName.TestCustomObjectPage,taskCustomObj2Name, Smoke_TaskSendLetterSubject,msg1,task17Date, false,"",false, "", 10);


							msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
							msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
							lp.verifyActivityAtPastStep2(projectName, PageName.TestCustomObjectPage,taskCustomObj2Name, Smoke_TaskSTDLogACall1UpdatedSubject,msg1, task15Date, false,"",false, "", 10);

							ele = tp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,ActivityType.Past, Smoke_TaskSTDLogACall1UpdatedSubject, SubjectElement.RedFlag, 10);
							if (ele!=null) {
								log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_TaskSTDLogACall1UpdatedSubject,YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_TaskSTDLogACall1UpdatedSubject,YesNo.Yes);
								sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_TaskSTDLogACall1UpdatedSubject);
							}

							String parentID=null;
							if (cp.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
								parentID=switchOnWindow(driver);
								if (parentID!=null) {

									if (click(driver, cp.getadvancedFilterImg(projectName, 10), "advanced filter", action.BOOLEAN)) {
										ele=cp.getAdvancedFilteDropdowns(projectName, PageLabel.Status.toString(), 10);
										if (selectVisibleTextFromDropDown(driver, ele, "status", "Open and Completed activities")) {
											ele=cp.clearApplyButtonOnAdvancedFilter(projectName, "Apply", 10);
											if (click(driver, ele, "apply", action.SCROLLANDBOOLEAN)) {
												if (sendKeys(driver, cp.getsearchTextboxActivities(projectName, 10),Smoke_TaskSendLetterSubject , Smoke_TaskSendLetterSubject, action.BOOLEAN)) {
													if (cp.getsearchCrossActivities(projectName, 10)!=null) {
														log(LogStatus.INFO, "search cross icon is successfully present after entering text", YesNo.No);
													}else {
														log(LogStatus.ERROR, "related tab activities is not clickable so cannot verify task", YesNo.Yes);
														sa.assertTrue(false,"related tab activities is not clickable so cannot verify task" );
													}
													if (click(driver, cp.getsearchIconActivities(projectName, 10), "search icon activities", action.SCROLLANDBOOLEAN)) {
														cp.verifyingRelatedTabData2(projectName, PageName.TestCustomObjectPage, relatedTab, task17Date, Smoke_TaskSendLetterSubject, taskstd1, action.BOOLEAN, 15);

													}else {
														log(LogStatus.ERROR, "search icon is not clickable, so cannot verify search functionality", YesNo.Yes);
														sa.assertTrue(false,"search icon is not clickable, so cannot verify search functionality" );
													}
												}else {
													sa.assertTrue(false,"search tetbox is not visible on page");
													log(LogStatus.SKIP,"search tetbox is not visible on page",YesNo.Yes);
												}
											}else {
												sa.assertTrue(false,"apply button is not clickable on page");
												log(LogStatus.SKIP,"apply button is not clickable on page",YesNo.Yes);
											}
										}else {
											sa.assertTrue(false,"status dropdown is not visible on page");
											log(LogStatus.SKIP,"status dropdown is not visible on page",YesNo.Yes);
										}
									}else {
										sa.assertTrue(false,"advanced filter toggle is not clickable");
										log(LogStatus.SKIP,"advanced filter toggle is not clickable",YesNo.Yes);
									}
								} else {
									sa.assertTrue(false,"new window not found after clicking view all on page "+Smoke_TaskINS2Name);
									log(LogStatus.SKIP,"new window not found after clicking view all on page "+Smoke_TaskINS2Name,YesNo.Yes);
								}
								driver.close();
								driver.switchTo().window(parentID);
							}else {
								sa.assertTrue(false,"not able to click on view all on page "+Smoke_TaskINS2Name);
								log(LogStatus.SKIP,"not able to click on view all on page "+Smoke_TaskINS2Name,YesNo.Yes);
							}
						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task could not be created" );
						}

					}else {
						log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
					}


				} else {
					sa.assertTrue(false,"Not Able to Click on New task button on Sub Tab : "+RelatedTab.Activities);
					log(LogStatus.SKIP,"Not Able to Click on New task button on Sub Tab : "+RelatedTab.Activities,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Fund/Deal Not Found : "+Smoke_TaskFund1Name);
				log(LogStatus.SKIP,"Fund/Deal  Not Found : "+Smoke_TaskFund1Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	
	public void AASmokeTc027_CreateNewTaskWithDateBeforeAndAfter(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String[] taskUIdata= {"","--None--",Smoke_TaskINS1Name,"","",Smoke_Task2Priority,"",Status.Not_Started.toString()};
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String dateMinusOne=previousOrForwardDate(-1, "M/d/YYYY");
		String dateMinusFive=previousOrForwardDate(-5, "M/d/YYYY");
		
		ExcelUtils.writeData(dateMinusOne, "Task", excelLabel.Variable_Name, "AATask32", excelLabel.Due_Date);
		ExcelUtils.writeData(dateMinusFive, "Task", excelLabel.Variable_Name, "AATask33", excelLabel.Due_Date);
		
		String task32Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask32", excelLabel.Due_Date);
		
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab)+","+ip.getTabName(projectName, TabName.TestCustomObjectTab);
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 20)) {
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {

					if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), dateMinusOne, "due date", action.SCROLLANDBOOLEAN)) {
						if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), Smoke_A1TaskSubject, "Subject", action.SCROLLANDBOOLEAN)) {
							if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully created task",  YesNo.Yes);

							}else {
								log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
								sa.assertTrue(false,   "save button is not clickable");
							}
						}else {
							log(LogStatus.ERROR, "subject textbox is not visible", YesNo.Yes);
							sa.assertTrue(false,   "subject textbox is not visible");
						}
					}else {
						log(LogStatus.ERROR, "due date textbox is not visible", YesNo.Yes);
						sa.assertTrue(false,   "due date textbox is not visible");
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,   "could not click on new task button");
				}
				clickUsingJavaScript(driver, ip.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {

					if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), dateMinusFive, "due date", action.SCROLLANDBOOLEAN)) {
						if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), Smoke_A2TaskSubject, "Subject", action.SCROLLANDBOOLEAN)) {

							if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully created task",  YesNo.Yes);

							}else {
								log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
								sa.assertTrue(false,   "save button is not clickable");
							}
						}else {
							log(LogStatus.ERROR, "subject textbox is not visible", YesNo.Yes);
							sa.assertTrue(false,   "subject textbox is not visible");
						}
					}else {
						log(LogStatus.ERROR, "due date textbox is not visible", YesNo.Yes);
						sa.assertTrue(false,   "due date textbox is not visible");
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,   "could not click on new task button");
				}

				String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, null, 0);
				//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS1Name, Smoke_A2TaskSubject,msg1, dateMinusFive, false,"",false, "", 10);

				msg1=BasePageErrorMessage.UpcomingTaskMsg(null, null, 0);
				//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS1Name, Smoke_A1TaskSubject,msg1,task32Date, false,"",false, "", 10);

				 ele = ip.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, Smoke_A1TaskSubject, SubjectElement.ExpandIcon, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of expand icon of task "+Smoke_A1TaskSubject,YesNo.No);
					if (clickUsingJavaScript(driver, ele, "expand task", action.BOOLEAN)) {
						msg1=BasePageErrorMessage.UpcomingTaskMsg(null, null, 0);
						//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
						lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS1Name, Smoke_A2TaskSubject,msg1,dateMinusFive, false,"",false, "", 10);

						msg1=BasePageErrorMessage.UpcomingTaskMsg(null, null, 0);
						//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
						lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS1Name, Smoke_A1TaskSubject,msg1,task32Date, false,"",true, "", 10);
					}else {
						log(LogStatus.ERROR, "could not click on expand icon", YesNo.Yes);
						sa.assertTrue(false,   "could not click on expand icon");
					}
				}else {
					log(LogStatus.ERROR, "expand icon is not visible", YesNo.Yes);
					sa.assertTrue(false,   "expand icon is not visible");
				}
			}else {
				log(LogStatus.ERROR,Smoke_TaskINS2Name+ "is not found on tab", YesNo.Yes);
				sa.assertTrue(false,  Smoke_TaskINS2Name+ "is not found on tab");
			}
		}
		else {
			log(LogStatus.ERROR,TabName.Object1Tab+ "is not found", YesNo.Yes);
			sa.assertTrue(false,  TabName.Object1Tab+ "is not found");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc028_CreateLogCallWithDateBeforeAndAfter(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String[] taskUIdata= {"","--None--",Smoke_TaskINS1Name,"","",Smoke_Task2Priority,"",Status.Not_Started.toString()};
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String dateMinusOne=previousOrForwardDate(-1, "M/d/YYYY");
		String dateMinusFive=previousOrForwardDate(-5, "M/d/YYYY");
		
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab)+","+ip.getTabName(projectName, TabName.TestCustomObjectTab);
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 20)) {
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations.toString(), action.BOOLEAN)) {

					if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), dateMinusOne, "due date", action.SCROLLANDBOOLEAN)) {
						if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), Smoke_R1CallSubject, "Subject", action.SCROLLANDBOOLEAN)) {
							if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
								ExcelUtils.writeData(dateMinusOne, "Task", excelLabel.Variable_Name, "AATask34", excelLabel.Due_Date);

							}else {
								log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
								sa.assertTrue(false,   "save button is not clickable");
							}
						}else {
							log(LogStatus.ERROR, "subject textbox is not visible", YesNo.Yes);
							sa.assertTrue(false,   "subject textbox is not visible");
						}
					}else {
						log(LogStatus.ERROR, "due date textbox is not visible", YesNo.Yes);
						sa.assertTrue(false,   "due date textbox is not visible");
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,   "could not click on new task button");
				}
				clickUsingJavaScript(driver, ip.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {

					if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), dateMinusFive, "due date", action.SCROLLANDBOOLEAN)) {
						if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), Smoke_R2CallSubject, "Subject", action.SCROLLANDBOOLEAN)) {

							if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
								ExcelUtils.writeData(dateMinusOne, "Task", excelLabel.Variable_Name, "AATask35", excelLabel.Due_Date);

							}else {
								log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
								sa.assertTrue(false,   "save button is not clickable");
							}
						}else {
							log(LogStatus.ERROR, "subject textbox is not visible", YesNo.Yes);
							sa.assertTrue(false,   "subject textbox is not visible");
						}
					}else {
						log(LogStatus.ERROR, "due date textbox is not visible", YesNo.Yes);
						sa.assertTrue(false,   "due date textbox is not visible");
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,   "could not click on new task button");
				}

				String task15Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask15", excelLabel.Due_Date);
				String task12Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask12", excelLabel.Due_Date);
				String task17Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask17", excelLabel.Due_Date);
				String task34Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask34", excelLabel.Due_Date);
				
				String msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_Task1LogACallUpdatedSubject,msg1, task12Date, false,"",false, "", 10);

				msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
				msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_TaskSendLetterSubject,msg1,task17Date, false,"",false, "", 10);
				
				msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
				msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_TaskSTDLogACall1UpdatedSubject,msg1,task15Date, false,"",false, "", 10);
				
				msg1=BasePageErrorMessage.LoggedCallMsg(null, null, 0);
				//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_R1CallSubject,msg1,task34Date, false,"",false, "", 10);
				
				msg1=BasePageErrorMessage.LoggedCallMsg(null, null, 0);
				//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_R2CallSubject,msg1,dateMinusFive, false,"",false, "", 10);
				
				msg1=BasePageErrorMessage.LoggedCallMsg(null,  Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
				msg1+=" about "+Smoke_TaskFund1Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_CallSubject,msg1,DueDate.No_due_date.toString(), false,"",false, "", 10);
	
				/////////////////////////////////////Expand All/////////////////////////////////////
				ele = lp.getActivityTimeLineItem2(projectName, PageName.Object1Page, ActivityTimeLineItem.Expand_All, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of expand all of task ",YesNo.No);
					if (click(driver, ele, "expand task", action.BOOLEAN)) {
						msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
						msg1+=" about "+taskCustomObj2Name;
						lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_Task1LogACallUpdatedSubject,msg1, task12Date, false,"",true, Smoke_Task1LogACallComment, 10);

						msg1=BasePageErrorMessage.LoggedCallMsg(AdminUserFirstName+" "+AdminUserLastName, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
						msg1+=" about "+taskCustomObj2Name;
						lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_TaskSendLetterSubject,msg1,task17Date, true,Smoke_TaskSendLetterMeetingType,true, Smoke_TaskSendLetterComment, 10);
					
						msg1=BasePageErrorMessage.LoggedCallMsg(null, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, 0);
						msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" , "+Smoke_TaskFund2Name+" and "+taskCustomObj2Name;
						lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_TaskSTDLogACall1UpdatedSubject,msg1,task15Date, false,"",true, Smoke_TaskSTDLogACall1UpdatedComment, 10);
						
						msg1=BasePageErrorMessage.LoggedCallMsg(null, null, 0);
						//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
						lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_R1CallSubject,msg1,task34Date, false,"",true, "", 10);
						
						msg1=BasePageErrorMessage.LoggedCallMsg(null, null, 0);
						//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
						lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_R2CallSubject,msg1,dateMinusFive, false,"",true, "", 10);
						
						msg1=BasePageErrorMessage.LoggedCallMsg(null,  Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0);
						msg1+=" about "+Smoke_TaskFund1Name;
						lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_CallSubject,msg1,DueDate.No_due_date.toString(), true,Smoke_CallMeetingType,true, "", 10);
			
					}else {
						log(LogStatus.ERROR, "could not click on expand icon", YesNo.Yes);
						sa.assertTrue(false,   "could not click on expand icon");
					}
				}else {
					log(LogStatus.ERROR, "expand icon is not visible", YesNo.Yes);
					sa.assertTrue(false,   "expand icon is not visible");
				}
			}else {
				log(LogStatus.ERROR,Smoke_TaskINS2Name+ "is not found on tab", YesNo.Yes);
				sa.assertTrue(false,  Smoke_TaskINS2Name+ "is not found on tab");
			}
		}
		else {
			log(LogStatus.ERROR,TabName.Object1Tab+ "is not found", YesNo.Yes);
			sa.assertTrue(false,  TabName.Object1Tab+ "is not found");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc029_VerifyAvailabilityOfTaskInPastAndCallInNext(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String[] taskUIdata= {"","--None--",Smoke_TaskINS1Name,"","",Smoke_Task2Priority,"",Status.Not_Started.toString()};
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String dateMinusOne=previousOrForwardDate(-1, "M/d/YYYY");
		String dateMinusFive=previousOrForwardDate(-5, "M/d/YYYY");
		String task34Date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask34", excelLabel.Due_Date);
		
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab)+","+ip.getTabName(projectName, TabName.TestCustomObjectTab);
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 20)) {
				WebElement ele=lp.selectShowMoreActionForTaskInNextSteps(projectName, PageName.Object1Page, Smoke_R1CallSubject, ShowMoreAction.Edit);
				if (clickUsingJavaScript(driver, ele, "edit button", action.BOOLEAN)) {
					if (lp.selectDropDownValueonTaskPopUp(projectName, PageName.Object1Page, PageLabel.Status.toString(), Smoke_R1CallStatus, action.BOOLEAN, 10)) {

						if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully edited call",  YesNo.Yes);
							refresh(driver);
							ThreadSleep(2000);
							ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
							if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
								log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
							} else {
								sa.assertTrue(false,"Not Able to Click on More Steps");
								log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

							}
							
							String msg1=BasePageErrorMessage.LoggedCallMsg(null, null, 0);
							//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
							lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_R1CallSubject,msg1,task34Date, false,"",false, "", 10);
							
						}else {
							log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
							sa.assertTrue(false,   "save button is not clickable");
						}	
					}else {
						log(LogStatus.ERROR, "could not select status dropdown value", YesNo.Yes);
						sa.assertTrue(false,   "could not select status dropdown value");
					}
				}else {
					log(LogStatus.ERROR, "edit button is not clickable", YesNo.Yes);
					sa.assertTrue(false,   "edit button is not clickable");
				}
				ele=lp.selectShowMoreActionForTaskInNextSteps(projectName, PageName.Object1Page, Smoke_A2TaskSubject, ShowMoreAction.Edit);
				ThreadSleep(3000);
				if (clickUsingJavaScript(driver, ele, "edit button", action.SCROLLANDBOOLEAN)) {
					if (lp.selectDropDownValueonTaskPopUp(projectName, PageName.Object1Page, PageLabel.Status.toString(), Smoke_A2TaskStatus, action.BOOLEAN, 10)) {

						if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully edited task",  YesNo.Yes);
							refresh(driver);
							ThreadSleep(2000);
							String msg1=BasePageErrorMessage.OldTaskMsg(null, null, 0);
							//msg1+=" about "+taskCustomObj1Name+" , "+Smoke_TaskINS5Name+" and "+Smoke_TaskFund2Name;
							lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,Smoke_TaskINS2Name, Smoke_A2TaskSubject,msg1,dateMinusFive, false,"",false, "", 10);
							
						}else {
							log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
							sa.assertTrue(false,   "save button is not clickable");
						}	
					}else {
						log(LogStatus.ERROR, "could not select status dropdown value", YesNo.Yes);
						sa.assertTrue(false,   "could not select status dropdown value");
					}
				}else {
					log(LogStatus.ERROR, "edit button is not clickable", YesNo.Yes);
					sa.assertTrue(false,   "edit button is not clickable");
				}
			}else {
				log(LogStatus.ERROR,Smoke_TaskINS2Name+ "is not found on tab", YesNo.Yes);
				sa.assertTrue(false,  Smoke_TaskINS2Name+ "is not found on tab");
			}
		}
		else {
			log(LogStatus.ERROR,TabName.Object1Tab+ "is not found", YesNo.Yes);
			sa.assertTrue(false,  TabName.Object1Tab+ "is not found");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc030_VerifyMouseOverOnPopup(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String account="",owner="";
		if (projectName.equalsIgnoreCase(ProjectName.PE.toString())) {
			account=ActivityPopupFields.Legal_Name.toString();
			owner=ActivityPopupFields.Institution_Owner.toString();
		}else if (projectName.equalsIgnoreCase(ProjectName.MNA.toString())){
			account=ActivityPopupFields.Account_Name.toString();
			owner=ActivityPopupFields.Primary_Coverage_Officer.toString();
		}else if (projectName.equalsIgnoreCase(ProjectName.PEEdge.toString())) {
			account=ActivityPopupFields.Firm.toString();
			owner=ActivityPopupFields.Entity_Owner.toString();
		}
		String[][] h1= {{ActivityPopupFields.Company_Name.toString(),"Navatar Group"},{ActivityPopupFields.Active.toString(),"checked"}};
		String[][] h2= {{ActivityPopupFields.Title.toString(),"Advisor"},{account,Smoke_TaskINS2Name},
				{ActivityPopupFields.Phone.toString(),""},{ActivityPopupFields.Email.toString(),Smoke_TaskContact4EmailID}};
		String[][] h3= {{ActivityPopupFields.Type.toString(),""},{ActivityPopupFields.Phone.toString(),""},
				{owner,crmUser1FirstName+" "+crmUser1LastName},{ActivityPopupFields.Website.toString(),"www.facebook.com"}};
		
		String labelName="";
		String labelValue="";
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 20)) {
				WebElement ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}

				if (ip.mouseHoverOnRecordsOnTask(projectName, AdminUserFirstName+" "+AdminUserLastName, Smoke_Task2UpdatedSubject)) {
					for (int i =0;i<h1.length;i++) {
						labelName=h1[i][0];
						labelValue=h1[i][1];
						if (ip.FieldValueVerificationOnMouseOverPopup(projectName, AdminUserFirstName+" "+AdminUserLastName , labelName, labelValue)) {
							log(LogStatus.INFO,"successfully verified "+labelName+" with "+labelValue, YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not verify "+labelName+" with "+labelValue, YesNo.No);
							sa.assertTrue(false, "could not verify "+labelName+" with "+labelValue);
						}
					}

					if (click(driver, ip.crossIconForActivityMouseoverPopup(projectName,  AdminUserFirstName+" "+AdminUserLastName), "cross icon", action.BOOLEAN)) {
						log(LogStatus.INFO,"successfully clicked on cross icon", YesNo.No);
					}
					else {
						log(LogStatus.ERROR,"could not clicked on cross icon", YesNo.No);
						sa.assertTrue(false, "could not clicked on cross icon");
					}
				}else {
					log(LogStatus.ERROR,"could not mouse hover on "+AdminUserFirstName+" "+AdminUserLastName, YesNo.No);
					sa.assertTrue(false, "could not mouse hover on "+AdminUserFirstName+" "+AdminUserLastName);
				}

				if (ip.mouseHoverOnRecordsOnTask(projectName, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, Smoke_TaskSTD1UpdatedSubject)) {
					for (int i =0;i<h2.length;i++) {
						labelName=h2[i][0];
						labelValue=h2[i][1];
						if (ip.FieldValueVerificationOnMouseOverPopup(projectName, Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName , labelName, labelValue)) {
							log(LogStatus.INFO,"successfully verified "+labelName+" with "+labelValue, YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not verify "+labelName+" with "+labelValue, YesNo.No);
							sa.assertTrue(false, "could not verify "+labelName+" with "+labelValue);
						}
					}

					if (click(driver, ip.crossIconForActivityMouseoverPopup(projectName,  Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName), "cross icon", action.BOOLEAN)) {
						log(LogStatus.INFO,"successfully clicked on cross icon", YesNo.No);
					}
					else {
						log(LogStatus.ERROR,"could not clicked on cross icon", YesNo.No);
						sa.assertTrue(false, "could not clicked on cross icon");
					}
				}else {
					log(LogStatus.ERROR,"could not mouse hover on "+Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, YesNo.No);
					sa.assertTrue(false, "could not mouse hover on "+Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName);
				}
				if (ip.mouseHoverOnRecordsOnTask(projectName,Smoke_TaskINS5Name , Smoke_TaskSTD1UpdatedSubject)) {
					for (int i =0;i<h3.length;i++) {
						labelName=h3[i][0];
						labelValue=h3[i][1];
						if (ip.FieldValueVerificationOnMouseOverPopup(projectName, Smoke_TaskINS5Name , labelName, labelValue)) {
							log(LogStatus.INFO,"successfully verified "+labelName+" with "+labelValue, YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not verify "+labelName+" with "+labelValue, YesNo.No);
							sa.assertTrue(false, "could not verify "+labelName+" with "+labelValue);
						}
					}

					if (click(driver, ip.crossIconForActivityMouseoverPopup(projectName, Smoke_TaskINS5Name), "cross icon", action.BOOLEAN)) {
						log(LogStatus.INFO,"successfully clicked on cross icon", YesNo.No);
					}
					else {
						log(LogStatus.ERROR,"could not clicked on cross icon", YesNo.No);
						sa.assertTrue(false, "could not clicked on cross icon");
					}
				}else {
					log(LogStatus.ERROR,"could not mouse hover on "+Smoke_TaskINS5Name, YesNo.No);
					sa.assertTrue(false, "could not mouse hover on "+Smoke_TaskINS5Name);
				}
				if (ip.mouseHoverOnRecordsOnTask(projectName,Smoke_TaskFund2Name , Smoke_TaskSTD1UpdatedSubject)) {
				
					if (click(driver, ip.crossIconForActivityMouseoverPopup(projectName, Smoke_TaskFund2Name), "cross icon", action.BOOLEAN)) {
						log(LogStatus.INFO,"successfully verified "+Smoke_TaskFund2Name+" on mouse hover window and clicked on cross icon", YesNo.No);
					}
					else {
						log(LogStatus.ERROR,"could not clicked on cross icon", YesNo.No);
					}
				}else {
					log(LogStatus.ERROR,"could not mouse hover on "+Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, YesNo.No);
					sa.assertTrue(false, "could not mouse hover on "+Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName);
				}
				if (ip.mouseHoverOnRecordsOnTask(projectName,taskCustomObj2Name , Smoke_TaskSTD1UpdatedSubject)) {
					

					if (click(driver, ip.crossIconForActivityMouseoverPopup(projectName, taskCustomObj2Name), "cross icon", action.BOOLEAN)) {
						log(LogStatus.INFO,"successfully verified heading "+taskCustomObj2Name+" and clicked on cross icon", YesNo.No);
					}
					else {
						log(LogStatus.ERROR,"could not clicked on cross icon", YesNo.No);
					}
				}else {
					log(LogStatus.ERROR,"could not mouse hover on "+Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName, YesNo.No);
					sa.assertTrue(false, "could not mouse hover on "+Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName);
				}

			}else {
				log(LogStatus.ERROR,"could not click on "+Smoke_TaskINS2Name, YesNo.No);
				sa.assertTrue(false, "could not click on "+Smoke_TaskINS2Name);
			}
		}else {
			log(LogStatus.ERROR,"could not click on "+TabName.Object1Tab, YesNo.No);
			sa.assertTrue(false, "could not click on "+TabName.Object1Tab);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc031_VerifyDeleteButtonInShowMoreActions(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		String date=todaysDate;
		
		WebElement ele ;
		boolean flag=false;
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cop.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 10)){
				log(LogStatus.INFO,"Clicked on custom tab record : "+Smoke_TaskINS2Name,YesNo.No);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);
				}
				click(driver, ele, "more steps", action.BOOLEAN);
				for (int i =0;i<3;i++) {
					ele=cp.selectShowMoreActionForTaskInNextSteps(projectName, PageName.Object1Page, Smoke_R1CallSubject, ShowMoreAction.Delete);
					if (clickUsingJavaScript(driver, ele, "delete")) {
						if (cp.getDeleteButtonOnDeletePopUp(projectName, 10)!=null) {
							log(LogStatus.INFO, "successfully verified presence of delete button", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, " delete button not present on delete popup", YesNo.Yes);
							sa.assertTrue(false, " delete button not present on delete popup");
						}
						if (cp.getCancelButtonOnDeletePopUp(projectName, 10)!=null) {
							log(LogStatus.INFO, "successfully verified presence of cancel button", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, " delete button not present on cancel popup", YesNo.Yes);
							sa.assertTrue(false, " delete button not present on cancel popup");
						}
						if (i==0) {
							if (cp.getdeleteTaskText(projectName, 10).getText().trim().contains(BasePageErrorMessage.deleteTaskMessage)
									&& cp.getdeleteTaskText(projectName, 10).getText().trim().contains(Activity.Task.toString())) {
								log(LogStatus.INFO, "successfully verified delete popup text", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify delete popup text", YesNo.Yes);
								sa.assertTrue(false, "could not verify delete popup text");
							}
							if (click(driver, cp.getcrossIcon(projectName, 10), "cross icon", action.BOOLEAN)) {
								log(LogStatus.INFO, "successfully closed delete popup", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, " cross button not present on delete popup", YesNo.Yes);
								sa.assertTrue(false, " cross button not present on delete popup");
							}
						}
						else if (i==1) {
							if (click(driver, cp.getCancelButtonOnDeletePopUp(projectName, 10), "cross icon", action.BOOLEAN)) {
								log(LogStatus.INFO, "successfully closed delete popup", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, " cancel button not present on delete popup", YesNo.Yes);
								sa.assertTrue(false, " cancel button not present on delete popup");
							}
						}
						else if (i==2) {
							if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 10), "cross icon", action.BOOLEAN)) {
								log(LogStatus.INFO, "successfully deleted call", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, " delete button could not be clicked", YesNo.Yes);
								sa.assertTrue(false, " delete button could not be clicked");
							}
						}
					}else {
						log(LogStatus.ERROR, "show more delete button could not be clicked", YesNo.Yes);
						sa.assertTrue(false, "show more delete button could not be clicked");
					}
				}
				refresh(driver);
				ThreadSleep(2000);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);
				}
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,ActivityType.Past, Smoke_R1CallSubject, SubjectElement.SubjectLink, 5);
				if (ele==null) {
					log(LogStatus.INFO, "successfully verified absence of task "+Smoke_R1CallSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "task is present but it should not be"+Smoke_R1CallSubject,YesNo.Yes);
					sa.assertTrue(false, "task is present but it should not be"+Smoke_R1CallSubject);
				}
				String parentID=null;
				if (cp.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						if (click(driver, cp.getadvancedFilterImg(projectName, 10), "advanced filter", action.BOOLEAN)) {
							ele=cp.getAdvancedFilteDropdowns(projectName, PageLabel.Status.toString(), 10);
							if (selectVisibleTextFromDropDown(driver, ele, "status", "Open and Completed activities")) {
								ele=cp.clearApplyButtonOnAdvancedFilter(projectName, "Apply", 10);
								if (click(driver, ele, "apply", action.SCROLLANDBOOLEAN)) {
									ele = cp.getAllLinksOnSubTab(projectName, PageName.Object2Page, RelatedTab.Activities, Smoke_R1CallSubject, action.BOOLEAN, 5);
									if (ele==null) {
										log(LogStatus.INFO, "successfully verified absence of "+Smoke_R1CallSubject, YesNo.No);
									} else {
										sa.assertTrue(false,"task should not be present, but it is "+Smoke_R1CallSubject);
										log(LogStatus.SKIP,"task should not be present, but it is "+Smoke_R1CallSubject,YesNo.Yes);
									}
								} else {
									sa.assertTrue(false,"apply button is not clickable");
									log(LogStatus.SKIP,"apply button is not clickable",YesNo.Yes);
								}
							} else {
								sa.assertTrue(false,"could not select open and completed act in status dropdown");
								log(LogStatus.SKIP,"could not select open and completed act in status dropdown",YesNo.Yes);
							}
						} else {
							sa.assertTrue(false,"advanced filter img is not clickable");
							log(LogStatus.SKIP,"advanced filter img is not clickable",YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"new window not found after clicking view all on page "+Smoke_TaskINS2Name);
						log(LogStatus.SKIP,"new window not found after clicking view all on page "+Smoke_TaskINS2Name,YesNo.Yes);
					}
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					sa.assertTrue(false,"load more past activities could not be clicked, so cannot verify deletion of task");
					log(LogStatus.SKIP,"load more past activities could not be clicked, so cannot verify deletion of task",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"could not find : "+Smoke_TaskINS2Name);
				log(LogStatus.SKIP,"could not find : "+Smoke_TaskINS2Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"object 1 tab is not clickable");
			log(LogStatus.SKIP,"object 1 tab is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc032_VerifyFollowUpTaskButtonInShowMoreActions(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		String date=todaysDate;
		String taskSubject=Smoke_A1TaskSubject+" - Follow Up";
		WebElement ele ;
		boolean flag=false;
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cop.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS2Name, 10)){
				log(LogStatus.INFO,"Clicked on custom tab record : "+Smoke_TaskINS2Name,YesNo.No);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);
				}
				click(driver, ele, "more steps", action.BOOLEAN);
				ele=ip.selectShowMoreActionForTaskInNextSteps(projectName, PageName.Object1Page, Smoke_A1TaskSubject, ShowMoreAction.FollowUpTask);
				String parentID=null;
				if (clickUsingJavaScript(driver, ele, "follow up task")) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						ele = cp.getHeaderTextForPage(projectName, PageName.Object3Page,PageLabel.New_Task.toString(), action.BOOLEAN, 30);
						if (ele!=null) {
							log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
						} else {
							sa.assertTrue(false,"New Task PopUp is not opened");
							log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
						}
					String name=getValueFromElementUsingJavaScript(driver, ip.getLabelTextBox(projectName, PageName.TaskPage.toString(),PageLabel.Subject.toString(),20), "subject");
					if (name.contains(Smoke_A1TaskSubject)) {
						log(LogStatus.INFO, "successfully verified subject textbox", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify subject textbox, found: "+name, YesNo.No);
						sa.assertTrue(false,"could not verify subject textbox, found: "+name);
					}
					if (ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Name.toString(), TabName.TaskTab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.SCROLLANDBOOLEAN, 15)) {
						log(LogStatus.INFO, "successfully selected contact 4 for name field", YesNo.No);
					} else {
						sa.assertTrue(false,"could not select contact 4 for name field");
						log(LogStatus.ERROR, "could not select contact 4 for name field",YesNo.Yes);
					}
					ele=ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), PageLabel.Subject.toString(),20);
					ele.sendKeys(" ");
					if (sendKeys(driver,ele , taskSubject,  PageLabel.Subject.toString(), action.SCROLLANDBOOLEAN)) {
						if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName, 10),"save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "successfully clicked on save button and created new task", YesNo.No);
						} else {
							sa.assertTrue(false,"could not click on save button");
							log(LogStatus.ERROR, "could not click on save button",YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"subject textbox is not visible");
						log(LogStatus.ERROR, "subject textbox is not visible",YesNo.Yes);
					}
					ThreadSleep(5000);
					ele = cp.getCreatedConfirmationMsg(projectName, 15);
					
					if (ele!=null) {
						String actualValue = ele.getText().trim();
						String expectedValue=taskSubject;
						if (actualValue.contains(expectedValue)) {
							log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);
						} else {
							log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
						}
					}else {
						sa.assertTrue(false,"task creation message could not be found");
						log(LogStatus.ERROR, "task creation message could not be found",YesNo.Yes);
					}
					driver.close();
					driver.switchTo().window(parentID);
					}
				} else {
					sa.assertTrue(false,"follow up task button could not be clicked, so cannot create task");
					log(LogStatus.ERROR, "follow up task button could not be clicked, so cannot create task",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"could not click on item : "+Smoke_TaskINS2Name);
				log(LogStatus.ERROR,"could not click on item : "+Smoke_TaskINS2Name,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"object 1 tab is not clickable");
			log(LogStatus.ERROR, "object 1 tab is not clickable",YesNo.Yes);
		}
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cop.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 10)){
				log(LogStatus.INFO,"Clicked on custom tab record : "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,YesNo.No);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);
				}
				String msg1=BasePageErrorMessage.UpcomingTaskMsg(null, null, 0);
				//msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page,Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, Smoke_A1TaskSubject+" - Follow Up",msg1, DueDate.No_due_date.toString(), false,"",false, "", 10);
			}else {
				sa.assertTrue(false,"could not click on item : "+ Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
				log(LogStatus.ERROR,"could not click on item : "+ Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"object 2 tab is not clickable");
			log(LogStatus.ERROR, "object 2 tab is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc033_CreateEventsWithMeetingTypeAction(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag1=false,flag2=false;
		String contactName= Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		TabName tabName;
		String relatedValue;
		String meetingType="--None--";
		String startDate = null,startDateToWrite=null,startingTime="";
		String endDate = null,endDateToWrite=null,endTime="";
		String meetingEventSubject = null;
		String desc=null;

		for (int k =0; k < 7; k++) {
			
			
			if (click(driver, gp.getGlobalActionIcon(projectName, 15), "Global Action Related item", action.BOOLEAN)) {
				log(LogStatus.INFO,"Clicked on Global Action Related item",YesNo.No);
				if (clickUsingJavaScript(driver, gp.getActionItem(projectName, GlobalActionItem.New_Event, 15), "New Event Link", action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New Event Link",YesNo.Yes);
					ThreadSleep(2000);	

					click(driver, gp.getMaximizeIcon(projectName, 15), "Maximize Icon", action.BOOLEAN);
					if (k<=2) {
						startDate = tomorrowsDate;
						endDate = dayAfterTomorrowsDate;
						startDateToWrite=tomorrowsDate;
						endDateToWrite=dayAfterTomorrowsDate;
						
					} else if(k==6){
						startDate=todaysDate;
						endDate=todaysDate;
						startDateToWrite=todaysDateSingleDateSingleMonth;
						endDateToWrite=todaysDateSingleDateSingleMonth;
					}
					
					else {
						startDate = dayBeforeYesterdaysDate;
						endDate = yesterdaysDate;
						startDateToWrite=dayBeforeYesterdaysDate;
						endDateToWrite=yesterdaysDate;
					}
					
					if (k==0 || k==3) {
						tabName=TabName.Object1Tab;
						relatedValue = Smoke_TaskINS4Name;
					} else if(k==1 || k==4 || k==6) {
						tabName=TabName.Object3Tab;
						relatedValue = Smoke_TaskFund1Name;
					}else {
						tabName=TabName.TestCustomObjectTab;
						relatedValue = taskCustomObj1Name;
					}
					meetingEventSubject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.Subject);
					desc=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.Description);
					ExcelUtils.writeData(startDateToWrite, "Events", excelLabel.Variable_Name, "AATASKUPEV"+(k+1), excelLabel.Start_Date);
					ExcelUtils.writeData(endDateToWrite, "Events", excelLabel.Variable_Name, "AATASKUPEV"+(k+1), excelLabel.End_Date);
					startingTime=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.Start_Time);
					endTime=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.End_Time);
					
					String[][] event1 = {{PageLabel.Subject.toString(),meetingEventSubject},
							{PageLabel.Start_Date.toString(),startDate},{PageLabel.Start_Time.toString(),startingTime},
							{PageLabel.End_Date.toString(),endDate},{PageLabel.End_Time.toString(),endTime},
							{PageLabel.Name.toString(),contactName},{PageLabel.Description.toString(),desc}};
					String[][] event2 = {
							{PageLabel.Start_Date.toString(),startDate},{PageLabel.Start_Time.toString(),startingTime},
							{PageLabel.End_Time.toString(),endTime}
							};

					flag1=gp.enterValueForNewEvent(projectName, GlobalActionItem.New_Event, event1, 10);
					if (k==6)
						flag1=gp.enterValueForNewEvent(projectName, GlobalActionItem.New_Event, event2, 10);
					
					clickUsingJavaScript(driver, lp.getrelatedAssociationsdropdownButton(projectName, PageName.TaskPage,PageLabel.Related_To.toString()
							, action.BOOLEAN, 10),"dropdown button", action.BOOLEAN);
					
					flag2 = gp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewEventPopUp, PageLabel.Related_To.toString(), tabName, relatedValue, action.SCROLLANDBOOLEAN,10);		
					
					appLog.info("using related value : "+relatedValue);
					appLog.info(">>>>");
				//	 
					if (flag1) {
						if (flag2) {
							log(LogStatus.INFO,"Selected "+relatedValue+" For Label "+PageLabel.Related_To,YesNo.No);
							if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Click on Save Button For Event : "+meetingEventSubject,YesNo.No);		
								ThreadSleep(500);
							}
							else {
								sa.assertTrue(false,"Not Able to Click on Save Button For Event : "+meetingEventSubject);
								log(LogStatus.SKIP,"Not Able to Click on Save Button For Event : "+meetingEventSubject,YesNo.Yes);	
							}

						} else {
							BaseLib.sa.assertTrue(false,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_To);
							log(LogStatus.ERROR,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_To,YesNo.Yes);

						}
					}else {
						BaseLib.sa.assertTrue(false,"Event can not created as Some data is not entered : "+meetingEventSubject);
						log(LogStatus.ERROR,"Event can not created as Some data is not entered : "+meetingEventSubject,YesNo.Yes);
					}


				} else {
					sa.assertTrue(false,"Not Able to Click on New Event Link");
					log(LogStatus.SKIP,"Not Able to Click on New Event Link",YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Global Action Related item");
				log(LogStatus.SKIP,"Not Able to Click on Global Action Related item",YesNo.Yes);
			}
			
		}
	

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc033_CreateEventsWithMeetingTypeImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName= Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		WebElement ele=null;
		String owner=crmUser1FirstName+" "+crmUser1LastName;
		String meetingEventSubject=null,desc=null,startTime="",endTime="",relatedValue="",msg1="",due="",startDate="",endDate="";
		String dateMinusTwo=previousOrForwardDate(-2, "M/d/YYYY");
		List<String> meetingEvent1=new LinkedList<String>();
		meetingEvent1.add(contactName);
		meetingEvent1.add(Smoke_TaskINS4Name);
		meetingEvent1.add("");
		meetingEvent1.add(owner);
		meetingEvent1.add("");
		meetingEvent1.add(Activity.Event.toString());
		meetingEvent1.add(Links.View.toString());	
		
		List<String> meetingEvent2=new LinkedList<String>();
		meetingEvent2.add(contactName);
		meetingEvent2.add(Smoke_TaskFund1Name);
		meetingEvent2.add("");
		meetingEvent2.add(owner);
		meetingEvent2.add("");
		meetingEvent2.add(Activity.Event.toString());
		meetingEvent2.add(Links.View.toString());
		
		List<String> meetingEvent3=new LinkedList<String>();
		meetingEvent3.add(contactName);
		meetingEvent3.add(taskCustomObj1Name);
		meetingEvent3.add("");
		meetingEvent3.add(owner);
		meetingEvent3.add("");
		meetingEvent3.add(Activity.Event.toString());
		meetingEvent3.add(Links.View.toString());
		
		List<String> meetingEvent4=new LinkedList<String>();
		meetingEvent4.add(contactName);
		meetingEvent4.add(Smoke_TaskINS4Name);
		meetingEvent4.add("");
		meetingEvent4.add(owner);
		meetingEvent4.add("");
		meetingEvent4.add(Activity.Event.toString());
		meetingEvent4.add(Links.View.toString());
		
		List<String> meetingEvent5=new LinkedList<String>();
		meetingEvent5.add(contactName);
		meetingEvent5.add(Smoke_TaskFund1Name);
		meetingEvent5.add("");
		meetingEvent5.add(owner);
		meetingEvent5.add("");
		meetingEvent5.add(Activity.Event.toString());
		meetingEvent5.add(Links.View.toString());
		
		
		List<String> meetingEvent6=new LinkedList<String>();
		meetingEvent6.add(contactName);
		meetingEvent6.add(taskCustomObj1Name);
		meetingEvent6.add("");
		meetingEvent6.add(owner);
		meetingEvent6.add("");
		meetingEvent6.add(Activity.Event.toString());
		meetingEvent6.add(Links.View.toString());
		
		ActivityType at=null;
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 10)){
				log(LogStatus.INFO,"Clicked on custom tab record : "+contactName,YesNo.No);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);
				}
				
				for (int k=0;k<7;k++) {
					meetingEventSubject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.Subject);
					desc=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.Description);
					startTime=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.Start_Time);
					endTime=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.End_Time);
					startDate=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.Start_Date);
					endDate=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.End_Date);
					
					if (k==0 || k==3) {
						relatedValue = Smoke_TaskINS4Name;
					} else if(k==1 || k==4 || k==6) {
						relatedValue = Smoke_TaskFund1Name;
					}else {
						relatedValue = taskCustomObj1Name;
					}
					if (k<=2) {
						due=startTime+" | "+DueDate.Tomorrow;
						at=ActivityType.Next;
					}
					else if(k==6) {
						due=startTime+" | "+DueDate.Today;
						at=ActivityType.Next;
					}
					else {
						due=startTime+" | "+findThreeLetterMonthName(dateMinusTwo);
						at=ActivityType.Past;
					}
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,at, meetingEventSubject, SubjectElement.ExpandIcon, 10);
				if (click(driver, ele, "expand", action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on expand",YesNo.No);	
				
					if (k<=2 || k==6) {
						msg1=BasePageErrorMessage.UpcomingEventMsg(null, null, 0);
						msg1+=" about "+relatedValue;

						lp.verifyActivityAtNextStep1(projectName, PageName.Object2Page,contactName, meetingEventSubject,msg1, due, false,"",true, desc, 10);
					}
					else {
						msg1=BasePageErrorMessage.OldEventMsg(null, null, 0);
						msg1+=" about "+relatedValue;

						lp.verifyActivityAtPastStep1(projectName, PageName.Object2Page,contactName, meetingEventSubject,msg1, due, false,"",true, desc, 10);
					}
					lp.verifyStartandEndTimeOnActivityTimeline(projectName, PageName.Object2Page, meetingEventSubject, at, startDate+" "+startTime, endDate+" "+endTime);
				} else {
					sa.assertTrue(false,"Not Able to Click on expand");
					log(LogStatus.ERROR, "Not Able to Click on expand",YesNo.Yes);
				}
				}
				String parentID=null;
				
				if ( lp.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object2Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
							String startDate4=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV4", excelLabel.Start_Date);
							String startDate5=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV5", excelLabel.Start_Date);
							String startDate6=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV6", excelLabel.Start_Date);
							
						
						cp.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, startDate4, taskCompletedEvent1Subject, meetingEvent4, action.BOOLEAN, 15);
						cp.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, startDate5, taskCompletedEvent2Subject, meetingEvent5, action.BOOLEAN, 15);
						
						cp.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, startDate6, taskCompletedEvent3Subject, meetingEvent6, action.BOOLEAN, 15);
					driver.close();
					driver.switchTo().window(parentID);
					}
					else {
					sa.assertTrue(false,"could not find new window after clicking on view all");
					log(LogStatus.ERROR, "could not find new window after clicking on view all",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"could not click on on view all");
					log(LogStatus.ERROR, "could not click on on view all",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"could not find contact 1 on contact tab");
				log(LogStatus.ERROR, "could not find contact 1 on contact tab",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"object 2 tab is not clickable");
			log(LogStatus.ERROR, "object 2 tab is not clickable",YesNo.Yes);
		}
		
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TaskINS1Name, 10)){
				log(LogStatus.INFO,"Clicked on custom tab record : "+Smoke_TaskINS1Name,YesNo.No);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);
				}
				for (int k=0;k<7;k++) {
					meetingEventSubject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.Subject);
					desc=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.Description);
					startTime=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.Start_Time);
					endTime=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.End_Time);
					startDate=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.Start_Date);
					endDate=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV"+(k+1), excelLabel.End_Date);
					
					if (k==0 || k==3) {
						relatedValue = Smoke_TaskINS4Name;
					} else if(k==1 || k==4 || k==6) {
						relatedValue = Smoke_TaskFund1Name;
					}else {
						relatedValue = taskCustomObj1Name;
					}
					if (k<=2) {
						due=startTime+" | "+DueDate.Tomorrow;
						at=ActivityType.Next;
					}
					else if(k==6) {
						due=startTime+" | "+DueDate.Today;
						at=ActivityType.Next;
					}
					else {
						due=startTime+" | "+findThreeLetterMonthName(dateMinusTwo);
						at=ActivityType.Past;
					}
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,at, meetingEventSubject, SubjectElement.ExpandIcon, 10);
				if (click(driver, ele, "expand", action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on expand",YesNo.No);	
				
					if (k<=2 || k==6) {
						msg1=BasePageErrorMessage.UpcomingEventMsg(null, contactName, 0);
						msg1+=" about "+relatedValue;

						lp.verifyActivityAtNextStep1(projectName, PageName.Object1Page,Smoke_TaskINS1Name, meetingEventSubject,msg1, due, false,"",true, desc, 10);
					}
					else {
						msg1=BasePageErrorMessage.OldEventMsg(null, contactName, 0);
						msg1+=" about "+relatedValue;

						lp.verifyActivityAtPastStep1(projectName, PageName.Object1Page,Smoke_TaskINS1Name, meetingEventSubject,msg1, due, false,"",true, desc, 10);
					}
					lp.verifyStartandEndTimeOnActivityTimeline(projectName, PageName.Object1Page, meetingEventSubject, at, startDate+" "+startTime, endDate+" "+endTime);
				} else {
					sa.assertTrue(false,"Not Able to Click on expand");
					log(LogStatus.ERROR, "Not Able to Click on expand",YesNo.Yes);
				}
				}
				
				String parentID=null;
				
				if ( lp.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object2Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
							String startDate4=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV4", excelLabel.Start_Date);
							String startDate5=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV5", excelLabel.Start_Date);
							String startDate6=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV6", excelLabel.Start_Date);
							
						
						cp.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, startDate4, taskCompletedEvent1Subject, meetingEvent4, action.BOOLEAN, 15);
						cp.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, startDate5, taskCompletedEvent2Subject, meetingEvent5, action.BOOLEAN, 15);
						
						cp.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, startDate6, taskCompletedEvent3Subject, meetingEvent6, action.BOOLEAN, 15);
					driver.close();
					driver.switchTo().window(parentID);
					}
					else {
						sa.assertTrue(false,"could not find new window after clicking on view all");
						log(LogStatus.ERROR, "could not find new window after clicking on view all",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"could not click on view all");
					log(LogStatus.ERROR, "could not click on view all",YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"could not click on ins 1, so cannot verify events");
				log(LogStatus.ERROR, "could not click on ins 1, so cannot verify events",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"could not click on tab object1");
			log(LogStatus.ERROR, "could not click on tab object1",YesNo.Yes);
		}
		
		if (cp.clickOnTab(projectName, TabName.Object3Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object3Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object3Tab, Smoke_TaskFund1Name, 10)){
				log(LogStatus.INFO,"Clicked on custom tab record : "+Smoke_TaskFund1Name,YesNo.No);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);
				}
				String var[]= {"AATASKUPEV2","AATASKUPEV7","AATASKUPEV5"};
				for (int k=0;k<var.length;k++) {
					meetingEventSubject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,var[k], excelLabel.Subject);
					desc=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,var[k], excelLabel.Description);
					startTime=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,var[k], excelLabel.Start_Time);
					endTime=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,var[k], excelLabel.End_Time);
					startDate=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,var[k], excelLabel.Start_Date);
					endDate=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,var[k], excelLabel.End_Date);
					
					if (k==0) {
						due=startTime+" | "+DueDate.Tomorrow;
						at=ActivityType.Next;
					}
					else if(k==1) {
						due=startTime+" | "+DueDate.Today;
						at=ActivityType.Next;
					}
					else {
						due=startTime+" | "+findThreeLetterMonthName(dateMinusTwo);
						at=ActivityType.Past;
					}
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,at, meetingEventSubject, SubjectElement.ExpandIcon, 10);
				if (click(driver, ele, "expand", action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on expand",YesNo.No);	
				
					if (k<=1) {
						msg1=BasePageErrorMessage.UpcomingEventMsg(null, contactName, 0);
						//msg1+=" about "+relatedValue;

						lp.verifyActivityAtNextStep1(projectName, PageName.Object1Page,Smoke_TaskINS1Name, meetingEventSubject,msg1, due, false,"",true, desc, 10);
					}
					else {
						msg1=BasePageErrorMessage.OldEventMsg(null, contactName, 0);
						//msg1+=" about "+relatedValue;

						lp.verifyActivityAtPastStep1(projectName, PageName.Object1Page,Smoke_TaskINS1Name, meetingEventSubject,msg1, due, false,"",true, desc, 10);
					}
					lp.verifyStartandEndTimeOnActivityTimeline(projectName, PageName.Object1Page, meetingEventSubject, at, startDate+" "+startTime, endDate+" "+endTime);
				} else {
					sa.assertTrue(false,"Not Able to Click on expand");
					log(LogStatus.ERROR, "Not Able to Click on expand",YesNo.Yes);
				}
				}
				String parentID=null;
				
				if ( lp.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object2Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
							String startDate5=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV5", excelLabel.Start_Date);
						
						cp.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, startDate5, taskCompletedEvent2Subject, meetingEvent5, action.BOOLEAN, 15);
						
					driver.close();
					driver.switchTo().window(parentID);
					}
					else {
						sa.assertTrue(false,"could not find new window after clicking on view all");
						log(LogStatus.ERROR, "could not find new window after clicking on view all",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"could not click on view all");
					log(LogStatus.ERROR, "could not click on view all",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"could not click on "+Smoke_TaskFund1Name);
				log(LogStatus.ERROR, "could not click on "+Smoke_TaskFund1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"could not click on "+TabName.Object3Tab);
			log(LogStatus.ERROR, "could not click on "+TabName.Object3Tab,YesNo.Yes);
		}
		
		if (cp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.TestCustomObjectTab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, taskCustomObj1Name, 10)){
				log(LogStatus.INFO,"Clicked on custom tab record : "+taskCustomObj1Name,YesNo.No);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);
				}
				String var[]= {"AATASKUPEV3","AATASKUPEV6"};
				for (int k=0;k<var.length;k++) {
					meetingEventSubject=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,var[k], excelLabel.Subject);
					desc=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,var[k], excelLabel.Description);
					startTime=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,var[k], excelLabel.Start_Time);
					endTime=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,var[k], excelLabel.End_Time);
					startDate=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,var[k], excelLabel.Start_Date);
					endDate=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,var[k], excelLabel.End_Date);
					
					if (k==0) {
						due=startTime+" | "+DueDate.Tomorrow;
						at=ActivityType.Next;
					}
					
					else {
						due=startTime+" | "+findThreeLetterMonthName(dateMinusTwo);
						at=ActivityType.Past;
					}
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,at, meetingEventSubject, SubjectElement.ExpandIcon, 10);
				if (click(driver, ele, "expand", action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on expand",YesNo.No);	
				
					if (k==0) {
						msg1=BasePageErrorMessage.UpcomingEventMsg(null, contactName, 0);
						lp.verifyActivityAtNextStep1(projectName, PageName.TestCustomObjectPage,taskCustomObj1Name, meetingEventSubject,msg1, due, false,"",true, desc, 10);
					}
					else {
						msg1=BasePageErrorMessage.OldEventMsg(null, contactName, 0);
						lp.verifyActivityAtPastStep1(projectName, PageName.TestCustomObjectPage,taskCustomObj1Name, meetingEventSubject,msg1, due, false,"",true, desc, 10);
					}
					lp.verifyStartandEndTimeOnActivityTimeline(projectName, PageName.TestCustomObjectPage, meetingEventSubject, at, startDate+" "+startTime, endDate+" "+endTime);
				} else {
					sa.assertTrue(false,"Not Able to Click on expand");
					log(LogStatus.ERROR, "Not Able to Click on expand",YesNo.Yes);
				}
				}
				
				String parentID=null,secondWindow=null;
				boolean flag=false;
				if ( lp.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object2Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						String startDate6=ExcelUtils.readData(testCasesFilePath, "Events", excelLabel.Variable_Name,"AATASKUPEV6", excelLabel.Start_Date);
						
						cp.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, startDate6, taskCompletedEvent3Subject, meetingEvent6, action.BOOLEAN, 15);
					
						ele = cp.getAllLinksOnSubTab(projectName, PageName.Object2Page, RelatedTab.Activities, taskCompletedEvent3Subject, action.BOOLEAN, 5);
						secondWindow=driver.getWindowHandle();
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of "+taskCompletedEvent3Subject, YesNo.No);

							if (click(driver, ele, taskCompletedEvent3Subject, action.BOOLEAN)) {
								ThreadSleep(3000);
								for (String win:driver.getWindowHandles()) {
									if (!win.equals(parentID) && (!win.equals(secondWindow))) {
										driver.switchTo().window(win);
										flag=true;
										break;
									}
								}
								if (flag) {
									if (cp.verifyEvent(projectName, taskCompletedEvent3Subject, 10)!=null) {
										log(LogStatus.INFO, "successfully verified completed event detail page", YesNo.No);
	
									}
									else {
										log(LogStatus.ERROR,"send letter task is present on activities grid but it should not be" , YesNo.Yes);
										sa.assertTrue(false, "send letter task is present on activities grid but it should not be");

									}
									if (FindElement(driver, "//span[text()='Related Associations']", "related associations label", action.BOOLEAN, 5)==null) {
										log(LogStatus.INFO, "successfully verified absence of RA label on event detail page", YesNo.No);
										
									}
									else {
										log(LogStatus.ERROR,"RA label is present, but it should not be on event detail page" , YesNo.Yes);
										sa.assertTrue(false, "RA label is present, but it should not be on event detail page");

									}
								driver.close();
								driver.switchTo().window(secondWindow);
								}else {
									log(LogStatus.ERROR,"could not find new window, so cannot verify event detail page" , YesNo.Yes);
									sa.assertTrue(false, "could not find new window, so cannot verify event detail page");

								}
							}
							else {
								log(LogStatus.ERROR,"event is not clickable, so cannot verify event detail page" , YesNo.Yes);
								sa.assertTrue(false, "event is not clickable, so cannot verify event detail page");

							}
						}else {
							log(LogStatus.ERROR,"event is not present so cannot verify event clicking" , YesNo.Yes);
							sa.assertTrue(false, "event is not present so cannot verify event clicking");

						}
						driver.close();
					driver.switchTo().window(parentID);
					}
					else {
						sa.assertTrue(false,"could not find new window after clicking on view all");
						log(LogStatus.ERROR, "could not find new window after clicking on view all",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"could not click on view all");
					log(LogStatus.ERROR, "could not click on view all",YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"could not click on cust obj 1, so cannot verify events");
				log(LogStatus.ERROR, "could not click on cust obj 1, so cannot verify events",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"could not click on custom tab");
			log(LogStatus.ERROR, "could not click on custom tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test	
	public void AASmokeTc034_VerifyColumnsToDisplay_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		String parentID=null;
		String[] finalOrder= {TaskPageFields.Name.toString(),"Name ID",TaskPageFields.Date.toString(),"Related To ID",
				TaskPageFields.Activity.toString(),TaskPageFields.Owner.toString(),TaskPageFields.Priority.toString(),PageLabel.Meeting_Type.toString()};
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						if (click(driver,cp.getwrenchIcon(projectName, 10), "columns to display", action.BOOLEAN)) {
							sendKeys(driver, ip.getsearchTextboxColToDisplay(projectName, 10), Keys.PAGE_DOWN+"", "page down", action.BOOLEAN);
							if (sendKeys(driver, cp.getsearchTextboxColToDisplay(projectName, 10),ActivityRelatedLabel.Priority.toString(), 
									"columns to display", action.BOOLEAN)) {
								if (click(driver, cp.getsearchIconColToDisplay(projectName, 10), "search icon", action.BOOLEAN)) {
									if (cp.verifyAvailableColumnInColumnsToDisplayGrid(projectName, PageName.Object2Page,TaskPageFields.Priority.toString() ,20)) {
										log(LogStatus.INFO, "successfully verified priority field in available dropdown",YesNo.No);
									}else {
										log(LogStatus.ERROR, "could not verify priority field in available dropdown",YesNo.Yes);
										sa.assertTrue(false, "could not verify priority field in available dropdown");
									}
									if (cp.getsearchCrossColToDisplay(projectName, 10)!=null) {
										log(LogStatus.INFO, "search cross icon is successfully verified",YesNo.No);
									}else {
										log(LogStatus.ERROR, "search cross icon is not visible on col to display",YesNo.Yes);
										sa.assertTrue(false, "search cross icon is not visible on col to display");
									}
									if (click(driver, cp.getsearchCrossColToDisplay(projectName, 10), "cross", action.BOOLEAN)) {
										if (getText(driver, cp.getsearchTextboxColToDisplay(projectName, 10), "search textbox", action.BOOLEAN).equalsIgnoreCase("")) {
											log(LogStatus.INFO, "verified empty search textbox", YesNo.No);
										}else {
											log(LogStatus.ERROR, "could not verify empty search textbox after clicking cross icon",YesNo.Yes);
											sa.assertTrue(false, "could not verify empty search textbox after clicking cross icon");
										}
									}else {
										log(LogStatus.ERROR, "search cross icon is not clickable",YesNo.Yes);
										sa.assertTrue(false, "search cross icon is not clickable");
									}

								}else {
									log(LogStatus.ERROR, "search icon is not clickable, so cannot find priority in available fields",YesNo.Yes);
									sa.assertTrue(false, "search icon is not clickable, so cannot find priority in available fields");
								}
							}else {
								log(LogStatus.ERROR, "search textbox is not visible, so cannot find priority in available fields",YesNo.Yes);
								sa.assertTrue(false, "search textbox is not visible, so cannot find priority in available fields");
							}
							if (sendKeys(driver, cp.getsearchTextboxColToDisplay(projectName, 10),ActivityRelatedLabel.Priority.toString(), 
									"columns to display", action.SCROLLANDBOOLEAN)) {
								if (click(driver, cp.getsearchIconColToDisplay(projectName, 10), "search icon", action.BOOLEAN)) {
									if (cp.verifyAvailableColumnInColumnsToDisplayGrid(projectName, PageName.Object2Page,TaskPageFields.Priority.toString(),20 )) {
										if (clickUsingJavaScript(driver, ip.getmoveToSelectedColToDisplay(projectName,10),"right button")) {
											if (cp.verifySelectedColumnInColumnsToDisplayGrid(projectName, PageName.Object2Page,TaskPageFields.Priority.toString(), 20 )) {
												log(LogStatus.INFO, "successfully verified priority in slected grid", YesNo.Yes);
											}
											else {
												log(LogStatus.ERROR, "so cannot verify priority in selected field",YesNo.Yes);
												sa.assertTrue(false, "so cannot verify priority in selected field");
											}
										}else {
											log(LogStatus.ERROR, "move to selected icon is not clickable, so cannot move priority to selected",YesNo.Yes);
											sa.assertTrue(false, "move to selected icon is not clickable, so cannot move priority to selected");
										}
									}
									else {
										log(LogStatus.ERROR, "priorty field is not clickable, so cannot move priority to selected",YesNo.Yes);
										sa.assertTrue(false, "priorty field is not clickable, so cannot move priority to selected");
									}
								}else {
									log(LogStatus.ERROR, "search icon is not clickable, so cannot move priority to selected",YesNo.Yes);
									sa.assertTrue(false, "search icon is not clickable, so cannot move priority to selected");
								}
							}
							else {
								log(LogStatus.ERROR, "search textbox is not visible, so cannot move priority to selected",YesNo.Yes);
								sa.assertTrue(false, "search textbox is not visible, so cannot move priority to selected");
							}
							click(driver, ip.getsearchCrossColToDisplay(projectName, 10), "search cross", action.BOOLEAN);
							if (cp.verifySelectedColumnInColumnsToDisplayGrid(projectName, PageName.Object2Page,TaskPageFields.Status.toString(), 20 )) {
								if (clickUsingJavaScript(driver, cp.getmoveToAvailableColToDisplay(projectName, 10), "move to left", action.BOOLEAN)) {
									if (!cp.verifySelectedColumnInColumnsToDisplayGrid(projectName, PageName.Object2Page,TaskPageFields.Status.toString(), 5 )) {
										log(LogStatus.INFO, "successfully verfied absent status in selected fields", YesNo.No);
									}else {
										log(LogStatus.ERROR, "status is present in selected fields, but it should not be",YesNo.Yes);
										sa.assertTrue(false, "status is present in selected fields, but it should not be");
									}
								}else {
									log(LogStatus.ERROR, "move To Available is not clickable",YesNo.Yes);
									sa.assertTrue(false, "move To Available is not clickable");
								}
							}else {
								log(LogStatus.ERROR, "could not click Status in selected column, so cannot move",YesNo.Yes);
								sa.assertTrue(false, "could not click Status in selected column, so cannot move");
							}

							ThreadSleep(5000);
							//scrollDownThroughWebelement(driver, ip.getApplyColToDisplay(projectName, EnableDisable.Enable, 10), "apply");
							if (cp.verifySelectedColumnInColumnsToDisplayGrid(projectName, PageName.Object2Page,TaskPageFields.Name.toString(), 20 )) {
								ThreadSleep(3000);
								if (click(driver, cp.getMoveTopColToDisplay(projectName, EnableDisable.Enable, 10), "top", action.BOOLEAN)) {
									log(LogStatus.ERROR, "clicked on top button",YesNo.Yes);
								}else {
									log(LogStatus.ERROR, "move top icon is not clickable",YesNo.Yes);
									sa.assertTrue(false, "move top icon is not clickable");
								}
							}else {
								log(LogStatus.ERROR, "could not click Priority in selected column",YesNo.Yes);
								sa.assertTrue(false, "could not click Priority in selected column");
							}
							if (cp.verifySelectedColumnInColumnsToDisplayGrid(projectName, PageName.Object2Page,PageLabel.Meeting_Type.toString(), 20 )) {
								if (click(driver, cp.getMoveBottomColToDisplay(projectName, EnableDisable.Enable, 10), "bottom", action.BOOLEAN)) {

								}else {
									log(LogStatus.ERROR, "move bottom icon is not clickable",YesNo.Yes);
									sa.assertTrue(false, "move bottom icon is not clickable");
								}
							}else {
								log(LogStatus.ERROR, "could not click Meeting_Type in selected column",YesNo.Yes);
								sa.assertTrue(false, "could not click Meeting_Type in selected column");
							}
							if (cp.verifySelectedColumnInColumnsToDisplayGrid(projectName, PageName.Object2Page,TaskPageFields.Date.toString(), 20 )) {
								if (click(driver, cp.getMoveDownColToDisplay(projectName, EnableDisable.Enable, 10), "down", action.BOOLEAN)) {

								}else {
									log(LogStatus.ERROR, "move down icon is not clickable",YesNo.Yes);
									sa.assertTrue(false, "move down icon is not clickable");
								}
							}else {
								log(LogStatus.ERROR, "could not click Date in selected column",YesNo.Yes);
								sa.assertTrue(false, "could not click Date in selected column");
							}
							if (cp.verifySelectedColumnInColumnsToDisplayGrid(projectName, PageName.Object2Page,TaskPageFields.Activity.toString(), 20 )) {
								if (click(driver, cp.getMoveUpColToDisplay(projectName, EnableDisable.Enable, 10), "up", action.BOOLEAN)) {
									List<WebElement> eles=ip.getSelectedFieldsList(projectName, 10);
									for (int i=0;i<finalOrder.length;i++) {
										if (eles.get(i).getText().trim().contains(finalOrder[i])) {
											log(LogStatus.INFO, "successfully verified "+eles.get(i).getText()+" in correct order", YesNo.No);
										}
										else {
											log(LogStatus.ERROR, eles.get(i).getText()+"found wrong w.r.t to "+finalOrder[i],YesNo.Yes);
											sa.assertTrue(false, eles.get(i).getText()+"found wrong w.r.t to "+finalOrder[i]);
										}
									}
									if (click(driver, ip.getApplyColToDisplay(projectName, EnableDisable.Enable, 10), "apply", action.BOOLEAN)) {
										log(LogStatus.INFO, "successfully changed columns through cols to display popup", YesNo.No);
									}else {
										log(LogStatus.ERROR, "could not click apply button",YesNo.Yes);
										sa.assertTrue(false, "could not click apply button");
									}

									List<String> s=compareMultipleList(driver,ActivityRelatedLabel.Priority.toString()  , ip.getHeadersInTaskGrid(projectName, 10));
									if (s.isEmpty()) {
										log(LogStatus.INFO, "successfully verified priority present on grid", YesNo.No);
									}
									else {
										log(LogStatus.ERROR, "not found priority on task grid",YesNo.Yes);
										sa.assertTrue(false, "not found priority ot task grid");
									}
									s=compareMultipleList(driver,ActivityRelatedLabel.Status.toString()  , ip.getHeadersInTaskGrid(projectName, 10));
									if (!s.isEmpty()) {
										log(LogStatus.INFO, "successfully verified absence of status on grid", YesNo.No);
									}
									else {
										log(LogStatus.ERROR, "found status on task grid, but it should not be",YesNo.Yes);
										sa.assertTrue(false, "found status on task grid, but it should not be");
									}
								}else {
									log(LogStatus.ERROR, "move up icon is not clickable",YesNo.Yes);
									sa.assertTrue(false, "move up icon is not clickable");
								}
							}else {
								log(LogStatus.ERROR, "could not click activity in selected column",YesNo.Yes);
								sa.assertTrue(false, "could not click activity in selected column");
							}
						}else {
							log(LogStatus.ERROR, "wrench icon not clickable",YesNo.Yes);
							sa.assertTrue(false, "wrench icon not clickable");
						}
						driver.close();
						driver.switchTo().window(parentID);
					}else {
						log(LogStatus.ERROR, "could not find new window, so cannot verify columns to display",YesNo.Yes);
						sa.assertTrue(false, "could not find new window, so cannot verify columns to display");
					}
				}else {
					log(LogStatus.ERROR, "activities related tab not clickable",YesNo.Yes);
					sa.assertTrue(false, "activities related tab not clickable");
				}
			}else {
				log(LogStatus.ERROR, "not found contact"+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName,YesNo.Yes);
				sa.assertTrue(false,"not found contact"+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName);
			}
		}
		else {
			log(LogStatus.ERROR, "contact tab not clickable",YesNo.Yes);
			sa.assertTrue(false, "contact tab not clickable");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc034_VerifyColumnsToDisplay_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String parentID=null;
		TabName[] tab= {TabName.Object1Tab,TabName.Object3Tab,TabName.TestCustomObjectTab};
		String records[]= {Smoke_TaskINS1Name,Smoke_TaskFund1Name,taskCustomObj1Name};
		for (int i = 0;i<tab.length;i++) {
			if (lp.clickOnTab(projectName, tab[i])) {
				if (ip.clickOnAlreadyCreatedItem(projectName, tab[i], records[i], 10)) {
					if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
						parentID=switchOnWindow(driver);
						if (parentID!=null) {
							log(LogStatus.INFO,"Clicked on view all : ",YesNo.No);
							ThreadSleep(2000);
							List<String> s=compareMultipleList(driver,ActivityRelatedLabel.Priority.toString()  , ip.getHeadersInTaskGrid(projectName, 10));
							if (s.isEmpty()) {
								log(LogStatus.INFO, "successfully verified priority present on grid", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "not found priority on task grid",YesNo.Yes);
								sa.assertTrue(false, "not found priority ot task grid");
							}
							s=compareMultipleList(driver,ActivityRelatedLabel.Status.toString()  , ip.getHeadersInTaskGrid(projectName, 10));
							if (!s.isEmpty()) {
								log(LogStatus.INFO, "successfully verified absence of status on grid", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "found status on task grid, but it should not be",YesNo.Yes);
								sa.assertTrue(false, "found status on task grid, but it should not be");
							}
							driver.close();
							driver.switchTo().window(parentID);
						}
					}
					else {
						log(LogStatus.ERROR, "load more past activities view all is not clickable, so cannot verify columns to display",YesNo.Yes);
						sa.assertTrue(false, "load more past activities view all is not clickable, so cannot verify columns to display");
					}
				}
				else {
					log(LogStatus.ERROR, "not found "+records[i],YesNo.Yes);
					sa.assertTrue(false,"not found "+records[i]);
				}
			}
			else {
				log(LogStatus.ERROR, tab[i]+" tab not clickable",YesNo.Yes);
				sa.assertTrue(false, tab[i]+" tab not clickable");
			}

		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc035_VerifyRevertToDefaultsColumnsToDisplay_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String parentID=null;
		String[] finalOrder= {TaskPageFields.Date.toString(),TaskPageFields.Name.toString(),"Name ID","Related To ID",
		TaskPageFields.Status.toString(),TaskPageFields.Owner.toString(),PageLabel.Meeting_Type.toString(),TaskPageFields.Activity.toString()};
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName, 10)) {
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						if (click(driver, ip.getwrenchIcon(projectName, 10), "wrench icon", action.BOOLEAN)) {
							sendKeys(driver, ip.getsearchTextboxColToDisplay(projectName, 10), Keys.PAGE_DOWN+"", "scroll down", action.BOOLEAN);
							if (click(driver, ip.getrevertToDefaultColToDisplay(projectName, EnableDisable.Enable, 10), "revert to defaults", action.BOOLEAN)) {
								List<WebElement> eles=ip.getSelectedFieldsList(projectName, 10);
								for (int i=0;i<finalOrder.length;i++) {
									if (eles.get(i).getText().trim().contains(finalOrder[i])) {
										log(LogStatus.INFO, "successfully verified "+eles.get(i).getText()+" in correct order", YesNo.No);
									}
									else {
										log(LogStatus.ERROR, eles.get(i).getText()+"found wrong w.r.t to "+finalOrder[i],YesNo.Yes);
										sa.assertTrue(false, eles.get(i).getText()+"found wrong w.r.t to "+finalOrder[i]);
									}
								}
								if (click(driver, ip.getApplyColToDisplay(projectName, EnableDisable.Enable, 10), "apply", action.BOOLEAN)) {
									log(LogStatus.INFO, "successfully reverted to default settings",YesNo.No);

									List<String> s=compareMultipleList(driver,ActivityRelatedLabel.Priority.toString()  , ip.getHeadersInTaskGrid(projectName, 10));
									if (!s.isEmpty()) {
										log(LogStatus.INFO, "priority absent on grid as expected", YesNo.No);
									}
									else {
										log(LogStatus.ERROR, "found priority on task grid, but it should not be",YesNo.Yes);
										sa.assertTrue(false, "found priority on task grid, but it should not be");
									}
									s=compareMultipleList(driver,ActivityRelatedLabel.Status.toString()  , ip.getHeadersInTaskGrid(projectName, 10));
									if (s.isEmpty()) {
										log(LogStatus.INFO, "successfully verified presence of status on grid", YesNo.No);
									}
									else {
										log(LogStatus.ERROR, "not found status on task grid",YesNo.Yes);
										sa.assertTrue(false, "not found status on task grid");
									}

								}
								else {
									log(LogStatus.ERROR, "could not click on apply button", YesNo.Yes);
									sa.assertTrue(false, "could not click on apply button");
								}
							}else {
								log(LogStatus.ERROR, "could not click on revertToDefault button", YesNo.Yes);
								sa.assertTrue(false, "could not click on revertToDefault button");
							}
						}else {
							log(LogStatus.ERROR, "could not click on wrench icon", YesNo.Yes);
							sa.assertTrue(false, "could not click on wrench icon");
						}
						driver.close();
						driver.switchTo().window(parentID);

					}else {
						log(LogStatus.ERROR, "could not find new window to switch so cannot verify columns to display", YesNo.Yes);
						sa.assertTrue(false, "could not find new window to switch so cannot verify columns to display");
					}
				}else {
					log(LogStatus.ERROR, "could not click on load more activities view all link, so cannot verify columns to display", YesNo.Yes);
					sa.assertTrue(false, "could not click on load more activities view all link, so cannot verify columns to display");
				}
			}else {
				log(LogStatus.ERROR, "not found contact"+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName,YesNo.Yes);
				sa.assertTrue(false,"not found contact"+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName);
			}
		}
		else {
			log(LogStatus.ERROR, "contact tab not clickable",YesNo.Yes);
			sa.assertTrue(false, "contact tab not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc035_VerifyRevertToDefaultsColumnsToDisplay_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String parentID=null;
		TabName[] tab= {TabName.Object1Tab,TabName.Object3Tab,TabName.TestCustomObjectTab};
		String records[]= {Smoke_TaskINS1Name,Smoke_TaskFund1Name,taskCustomObj1Name};
		for (int i=0;i<tab.length;i++) {
		if (lp.clickOnTab(projectName, tab[i])) {
			if (ip.clickOnAlreadyCreatedItem(projectName, tab[i], records[i], 10)) {
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						log(LogStatus.INFO,"Clicked on view all : ",YesNo.No);
						ThreadSleep(2000);
							List<String> s=compareMultipleList(driver,ActivityRelatedLabel.Priority.toString()  , ip.getHeadersInTaskGrid(projectName, 10));
					if (!s.isEmpty()) {
						log(LogStatus.INFO, "priority absent on grid as expected", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "found priority on task grid, but it should not be",YesNo.Yes);
						sa.assertTrue(false, "found priority on task grid, but it should not be");
					}
					s=compareMultipleList(driver,ActivityRelatedLabel.Status.toString()  , ip.getHeadersInTaskGrid(projectName, 10));
					if (s.isEmpty()) {
						log(LogStatus.INFO, "successfully verified presence of status on grid", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "not found status on task grid",YesNo.Yes);
						sa.assertTrue(false, "not found status on task grid");
					}
					driver.close();
					driver.switchTo().window(parentID);
					}else {
						log(LogStatus.ERROR, "new window not found, so cannot verify coumns to display",YesNo.Yes);
						sa.assertTrue(false, "new window not found, so cannot verify coumns to display");
					}
				}else {
					log(LogStatus.ERROR, "load more past activities view all is not clickable, so cannot verify columns to display",YesNo.Yes);
					sa.assertTrue(false, "load more past activities view all is not clickable, so cannot verify columns to display");
				}
			}
			else {
				log(LogStatus.ERROR, "not found "+records[i],YesNo.Yes);
				sa.assertTrue(false,"not found "+records[i]);
			}
		}
		else {
			log(LogStatus.ERROR, tab[i]+" tab not clickable",YesNo.Yes);
			sa.assertTrue(false, tab[i]+" tab not clickable");
		}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc036_VerifyRangPickListActivities(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask17", excelLabel.Due_Date);
		List<String> taskstd1=new LinkedList<String>();
		taskstd1.add(Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
		taskstd1.add(taskCustomObj2Name);
		taskstd1.add(Status.Completed.toString());
		taskstd1.add(AdminUserFirstName+" "+AdminUserLastName);
		taskstd1.add(Smoke_TaskSendLetterMeetingType);
		taskstd1.add(Activity.Call.toString());
		taskstd1.add(Links.View.toString());	
		RelatedTab relatedTab = RelatedTab.Activities;
		String parentID=null;
		if (lp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, taskCustomObj2Name, 10)) {
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						if (selectVisibleTextFromDropDown(driver, lp.getrangeDropdown(projectName, 10), "range", "Today")) {
							List<WebElement> datesFromPage=bp.listOfDatesOnActivitiesRelatedTab(projectName, 20);
							if (!datesFromPage.isEmpty()) {
								boolean flag=true;String value="";
								for (WebElement ele:datesFromPage) {
									value=ele.getText().trim();
									if (ip.verifyDate(todaysDate, value))
										log(LogStatus.INFO, "date present is todays date", YesNo.No);
									else
									{
										log(LogStatus.ERROR, "date is not todays date, it is "+ele.getText().trim(), YesNo.Yes);
										sa.assertTrue(false,  "date is not todays date, it is "+ele.getText().trim());
									}
								}
							}else
								{
								log(LogStatus.ERROR, "date is not visible", YesNo.Yes);
								sa.assertTrue(false,  "date is not visible");
								}
							if (ip.verifyDate(todaysDate, date)){
								log(LogStatus.INFO, "Task created Today : "+Smoke_TaskSendLetterSubject, YesNo.No);
								bp.verifyingRelatedTabData2(projectName, PageName.TestCustomObjectPage, relatedTab, date, Smoke_TaskSendLetterSubject, taskstd1, action.BOOLEAN, 15);
							}
							else
							{
								log(LogStatus.ERROR, Smoke_TaskSendLetterSubject+" : created on Date  : "+date+" therfore should not be available on Activity Grid", YesNo.Yes);
								WebElement ele = bp.getLinkOnSubTab(projectName, PageName.TestCustomObjectPage, RelatedTab.Activities, Smoke_TaskSendLetterSubject,"View", null, action.BOOLEAN, 10);
								if (ele==null) {
									log(LogStatus.ERROR, Smoke_TaskSendLetterSubject+" : created on Date  : "+date+" and is not available on Activity Grid", YesNo.Yes);

								} else {
									log(LogStatus.ERROR, Smoke_TaskSendLetterSubject+" : created on Date  : "+date+" therfore should not be available on Activity Grid", YesNo.Yes);
									sa.assertTrue(false,  Smoke_TaskSendLetterSubject+" : created on Date  : "+date+" therfore should not be available on Activity Grid");
								}
							}
							
						}else{
							log(LogStatus.ERROR, "range dropdown is not visible", YesNo.Yes);
							sa.assertTrue(false,  "range dropdown is not visible");
						}
						driver.close();
						driver.switchTo().window(parentID);
					}
					else{
						log(LogStatus.ERROR, "could not find new window so cannot verify range picklist", YesNo.Yes);
						sa.assertTrue(false,  "could not find new window so cannot verify range picklist");
					}
				}else{
					log(LogStatus.ERROR, "view all cannot be clicked so cannot verify range dropdown", YesNo.Yes);
					sa.assertTrue(false,  "view all cannot be clicked so cannot verify range dropdown");
				}
			}else{
				log(LogStatus.ERROR, taskCustomObj2Name+" is not found on custom object page", YesNo.Yes);
				sa.assertTrue(false,  taskCustomObj2Name+" is not found on custom object page");
			}
		}else{
			log(LogStatus.ERROR, "custom object tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,  "custom object tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc037_VerifyAdditionalFilterActivities(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask17", excelLabel.Due_Date);
		
		List<String> taskstd1=new LinkedList<String>();
		taskstd1.add(Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
		taskstd1.add(taskCustomObj2Name);
		taskstd1.add(Status.Completed.toString());
		taskstd1.add(AdminUserFirstName+" "+AdminUserLastName);
		taskstd1.add(Smoke_TaskSendLetterMeetingType);
		taskstd1.add(Activity.Call.toString());
		taskstd1.add(Links.View.toString());	
		RelatedTab relatedTab = RelatedTab.Activities;
		WebElement ele=null;
		String parentID=null;
		String statusValue="Completed activities",typeVale="Tasks and Events",ownerValue="My Activities";
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 10)) {
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						if (click(driver, ip.getadvancedFilterImg(projectName, 10), "advanced filter", action.SCROLLANDBOOLEAN)) {
						ele= bp.getAdvancedFilteDropdowns(projectName, PageLabel.Status.toString(),
								10);
						scrollDownThroughWebelement(driver, ele,  "status dropdown");
						if (selectVisibleTextFromDropDown(driver,ele, "status dropdown", statusValue)) {
							log(LogStatus.INFO, "successfully selected status dropdown value "+statusValue, YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not select status dropdown" , YesNo.Yes);
							sa.assertTrue(false, "could not select status dropdown");

						}
						ele=bp.getAdvancedFilteDropdowns(projectName, PageLabel.Type.toString(),
								10);
						scrollDownThroughWebelement(driver, ele, "Type dropdown" );
						if (selectVisibleTextFromDropDown(driver,ele , "Type dropdown", typeVale)) {
							log(LogStatus.INFO, "successfully selected type dropdown value "+typeVale, YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not select type dropdown" , YesNo.Yes);
							sa.assertTrue(false, "could not select type dropdown");

						}
						if (click(driver, bp.clearApplyButtonOnAdvancedFilter(projectName, "apply", 10), "apply", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on apply button of advanced filter", YesNo.No);
							bp.verifyingRelatedTabData2(projectName, PageName.Object2Page, relatedTab, date, Smoke_TaskSendLetterSubject, taskstd1, action.BOOLEAN, 15);

						}
						else {
							log(LogStatus.ERROR,"could not click apply button" , YesNo.Yes);
							sa.assertTrue(false, "could not click apply button");

						}
					}else {
						log(LogStatus.ERROR,"could not advanced filter toggle button" , YesNo.Yes);
						sa.assertTrue(false, "could not advanced filter toggle button");

					}
					if (click(driver, ip.getadvancedFilterImg(projectName, 10), "advanced filter", action.SCROLLANDBOOLEAN)) {
						ele=bp.getAdvancedFilteDropdowns(projectName, PageLabel.Owner.toString(),
								10);
						scrollDownThroughWebelement(driver, ele, "Owner dropdown" );
						
						if (selectVisibleTextFromDropDown(driver,ele , "Owner dropdown", ownerValue)) {
							log(LogStatus.INFO, "successfully selected owner dropdown value "+ownerValue, YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not select owner dropdown" , YesNo.Yes);
							sa.assertTrue(false, "could not select owner dropdown");

						}if (click(driver, bp.clearApplyButtonOnAdvancedFilter(projectName, "apply", 10), "apply", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on apply button of advanced filter", YesNo.No);
							ThreadSleep(5000);
							ele = bp.getAllLinksOnSubTab(projectName, PageName.Object2Page, RelatedTab.Activities, Smoke_TaskSendLetterSubject, action.BOOLEAN, 5);
							if (ele==null) {
								log(LogStatus.INFO, "successfully verified absence of send letter subject", YesNo.No);

							}else {
								log(LogStatus.ERROR,"send letter task is present on activities grid but it should not be" , YesNo.Yes);
								sa.assertTrue(false, "send letter task is present on activities grid but it should not be");

							}
						}
						else {
							log(LogStatus.ERROR,"could not click apply button" , YesNo.Yes);
							sa.assertTrue(false, "could not click apply button");

						}
						
					}else {
						log(LogStatus.ERROR,"could not advanced filter toggle button" , YesNo.Yes);
						sa.assertTrue(false, "could not advanced filter toggle button");
					}
					driver.close();
					driver.switchTo().window(parentID);
					}else{
						log(LogStatus.ERROR, "could not find new window, so cannot verify additional filter", YesNo.Yes);
						sa.assertTrue(false,  "could not find new window, so cannot verify additional filter");
					}
				}else{
					log(LogStatus.ERROR, "view all cannot be clicked, so cannot verify additional filter", YesNo.Yes);
					sa.assertTrue(false,  "view all cannot be clicked, so cannot verify additional filter");
				}
			}else{
				log(LogStatus.ERROR, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" is not found on contact page", YesNo.Yes);
				sa.assertTrue(false,  Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" is not found on contact page");
			}
		}else{
			log(LogStatus.ERROR, "contact tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,  "contact tab is not clickable");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc038_VerifyAddRowAddFilterLogicAndClearButtonFunctionalityAtAdditionalFilter(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		WebElement ele ;
		RelatedTab[] relatabTabs = {RelatedTab.Activities};

		String field="";
		String operator="";
		String value="";
		switchToDefaultContent(driver);
		String parentID=null;
		String Xpath =null;
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
					
						ThreadSleep(10000);
						//	 
						if (click(driver, ip.getadvancedFilterToggle(projectName, 20), "advanced filter toggle link", action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on advanced filter toggle link on Sub Tab",YesNo.No);		
							ThreadSleep(500);

							// Add Row Link and Cross Icon 
							if(click(driver, cp.getAddRowLink(projectName,10), "Add Row Link", action.BOOLEAN)) {
								log(LogStatus.INFO,"Clicked on Add row link",YesNo.No);
								ThreadSleep(1000);

								if (!cp.getRowRemoveIcon().isEmpty() && cp.getRowRemoveIcon().size()==1) {
									log(LogStatus.PASS,"1 Row Added",YesNo.Yes);

									if(click(driver, cp.getRowRemoveIcon().get(0), "remove icon", action.BOOLEAN)) {
										log(LogStatus.INFO,"clicked on remove row link",YesNo.No);
										ThreadSleep(1000);
										if (cp.getRowRemoveIcon1(projectName, 3)==null) {
											log(LogStatus.PASS,"Row Removed",YesNo.Yes);
										} else {
											log(LogStatus.FAIL,"Row Not Removed",YesNo.Yes);
											sa.assertTrue(false, "Row Not Removed");
										}

									}else {
										appLog.error("Not able to click on remove row link so cannot check remove functionality");
										log(LogStatus.FAIL,"Not able to click on remove row link so cannot check remove functionality",YesNo.Yes);
										sa.assertTrue(false,"Not able to click on remove row link so cannot check remove functionality");
									}
									ThreadSleep(2000);

								} else {
									log(LogStatus.FAIL,"1 Row Not Added",YesNo.Yes);
									sa.assertTrue(false, "1 Row Not Added");
								}


							}else {
								appLog.error("Not able to click on Add row link so cannot check remove functionality");
								log(LogStatus.FAIL,"Not able to click on Add row link so cannot check remove functionality",YesNo.Yes);
								sa.assertTrue(false,"Not able to click on Add row link so cannot check remove functionality");
							}

							// Filter Logic Link Functionality 
							ele=cp.getfilterLogicLink(projectName, 10);
							if(ele!=null) {
								log(LogStatus.INFO,"find add filter logic Link",YesNo.No);	
								if(click(driver, ele, "add Filter Logic Button", action.BOOLEAN)) {

									log(LogStatus.INFO,"clicked on add Filter Logic Button",YesNo.No);	
									ThreadSleep(2000);


									ele=cp.getFilterLogicInputBox(projectName, 10)	;
									if(ele!=null) {
										log(LogStatus.INFO,"find add filter logic text box",YesNo.No);	
									}else {
										log(LogStatus.FAIL,"Not able find add filter logic text box",YesNo.Yes);
										sa.assertTrue(false, "Not able find add filter logic text box  ");
									}

									String secondWindow=driver.getWindowHandle();

									if (click(driver, cp.getInfoLink(projectName,10), "Info Link", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO,"click on Info Link",YesNo.No);
										ThreadSleep(2000);
										boolean flag=false;
										for (String win:driver.getWindowHandles()) {
											if (!win.equals(parentID) && (!win.equals(secondWindow))) {
												driver.switchTo().window(win);
												ThreadSleep(5000);
												flag=true;
												break;
											}
										}
										if (flag) {

											log(LogStatus.INFO,"New Window is open",YesNo.No);
											Xpath = "//h1//*[text()='Add Filter Logic']";
											ele=isDisplayed(driver,FindElement(driver, Xpath, "Add filter logic Window", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
											if(ele!=null) {
												log(LogStatus.INFO,"Add Filter Logic Window is open",YesNo.No);	
											}else {
												log(LogStatus.FAIL,"Add Filter Logic Window is not open",YesNo.Yes);
												sa.assertTrue(false, "Add Filter Logic Window is not open");
											}
											driver.close();
											driver.switchTo().window(secondWindow);
										} else {
											log(LogStatus.FAIL,"Bug-----No New Window is open on Clicking Info Link",YesNo.Yes);
											sa.assertTrue(false, "Bug-----No New Window is open on Clicking Info Link");
										}
									} else {
										log(LogStatus.FAIL,"Not able to click on Info Link",YesNo.Yes);
										sa.assertTrue(false, "Not able to click on Info Link");
									}


									// 2 row added and Clear Filter Logic Functionality
									for (int j = 0; j < 2; j++) {
										if(click(driver, cp.getAddRowLink(projectName,10), "Add Row Link", action.BOOLEAN)) {
											log(LogStatus.INFO,"Clicked on Add row link : "+j,YesNo.No);
											ThreadSleep(2000);
										}else {
											log(LogStatus.FAIL,"Not able to click on Add row link "+j,YesNo.Yes);
											sa.assertTrue(false,"Not able to click on Add row link "+j);
										}
									}

									if (!cp.getRowRemoveIcon().isEmpty() && cp.getRowRemoveIcon().size()==2) {
										log(LogStatus.PASS,"2 Row Added",YesNo.Yes);
									}else {
										log(LogStatus.FAIL,"2 Row Not Added",YesNo.Yes);
										sa.assertTrue(false, "2 Row Not Added");	
									}

									if (click(driver, cp.clearApplyButtonOnAdvancedFilter(projectName, "clear",10), "Clear Btn", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO,"Click on Clear Button on Filter Logic",YesNo.No);


										if (cp.getRowRemoveIcon1(projectName, 3)==null) {
											log(LogStatus.PASS,"All New Added Row Removed after Click on Clear Button",YesNo.Yes);
										} else {
											log(LogStatus.FAIL,"All New Added Row Not Removed after Click on Clear Button",YesNo.Yes);
											sa.assertTrue(false, "All New Added Row Not Removed after Click on Clear Button");
										}


									} else {
										log(LogStatus.FAIL,"Not able to click on Clear Button on  Filter Logic",YesNo.Yes);
										sa.assertTrue(false, "Not able to click on Clear Button on Filter Logic");
									}


								}else {
									log(LogStatus.FAIL,"Not able to click on add filter logic ",YesNo.Yes);
									sa.assertTrue(false, "Not able to click on add filter logic ");

								}
							}else {
								log(LogStatus.FAIL,"Not able find add filter logic link ",YesNo.Yes);
								sa.assertTrue(false, "Not able find add filter logic link ");

							}

							// FilteR Logic Check

							for (int j = 0; j < 2; j++) {
								if(click(driver, cp.getAddRowLink(projectName,10), "Add Row Link", action.BOOLEAN)) {
									log(LogStatus.INFO,"For Filter Check Clicked on Add row link : "+j,YesNo.No);
									ThreadSleep(2000);
								}else {
									log(LogStatus.FAIL,"For Filter Check Not able to click on Add row link "+j,YesNo.Yes);
									sa.assertTrue(false,"For Filter Check Not able to click on Add row link "+j);
								}
							}

							getSystemDate("");
							String[][] fieldsOperatorValues= {{"Activity:Date","equals",todaysDate},
									{"Activity:Priority","equals","High, Normal, Low"},
									{"Activity:Subject","not equal to",""}};

							for (int j = 0; j < fieldsOperatorValues.length; j++) {

								field=fieldsOperatorValues[j][0];
								operator=fieldsOperatorValues[j][1];
								value=fieldsOperatorValues[j][2];
								log(LogStatus.INFO,"Field : "+field+"\t Operator : "+operator+"\t Value : "+value,YesNo.No);
								if (cp.setFieldValue(projectName, PageName.Object2Page, RelatedTab.Activities, field, j+1)) {
									log(LogStatus.INFO,"Field Set    : "+field,YesNo.No);

									if (cp.setOperatorValue(projectName, PageName.Object2Page, RelatedTab.Activities, operator, j+1)) {
										log(LogStatus.INFO,"Opeartor Set : "+operator,YesNo.No);

										if (cp.setCriterionValue(projectName, PageName.Object2Page, RelatedTab.Activities, "", value, j+1)) {
											log(LogStatus.INFO,"Value Set    : "+value,YesNo.No);
										} else {
											sa.assertTrue(false,"Not Able Set Value : "+value);
											log(LogStatus.SKIP,"Not Able Set Value : "+value,YesNo.Yes);	
											break;
										}
									} else {
										sa.assertTrue(false,"Not Able Set Operator : "+operator);
										log(LogStatus.SKIP,"Not Able Set Operator : "+operator,YesNo.Yes);
										break;
									}
								} else {
									sa.assertTrue(false,"Not Able Set Field : "+field);
									log(LogStatus.SKIP,"Not Able Set Field : "+field,YesNo.Yes);
									break;
								}
							}

							ele=cp.getfilterLogicLink(projectName, 10);
							if(click(driver, ele, "Add Filter Logic Link", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Click on add filter logic Link",YesNo.No);	

								ele=cp.getFilterLogicInputBox(projectName, 10);
								String filterLogicCondition = "(1 OR 2 ) OR 3";
								if(sendKeys(driver, ele,filterLogicCondition, "filter logic text box", action.SCROLLANDBOOLEAN)) {
									appLog.info("pass value in filter logic text box : "+filterLogicCondition);
									if (click(driver, cp.clearApplyButtonOnAdvancedFilter(projectName, "Apply",10), "Clear Btn", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO,"Click on Apply Button on Filter Logic",YesNo.No);
										ThreadSleep(5000);

										ele = cp.getAllLinksOnSubTab(projectName, PageName.Object2Page, RelatedTab.Meetings, Smoke_TaskSendLetterSubject, action.BOOLEAN, 10);
										if (ele!=null) {
											log(LogStatus.INFO,Smoke_TaskSendLetterSubject+" link present after applying Filter on Sub Tab : "+RelatedTab.Meetings,YesNo.Yes);		
										} else {
											sa.assertTrue(false,Smoke_TaskSendLetterSubject+" link not present after applying Filter on Sub Tab : "+RelatedTab.Meetings);
											log(LogStatus.SKIP,Smoke_TaskSendLetterSubject+" link not present after applying Filter on Sub Tab : "+RelatedTab.Meetings,YesNo.Yes);
										}

									}else {
										log(LogStatus.FAIL,"Not able to click on Apply Button on  Filter Logic",YesNo.Yes);
										sa.assertTrue(false, "Not able to click on Apply Button Filter Logic");	
									}

								}else {
									appLog.error("Not able to pass value on  filter logic text box "+filterLogicCondition);
									log(LogStatus.SKIP,"Not able to pass value on  filter logic text box "+filterLogicCondition,YesNo.Yes);
								}
							}else {
								appLog.error("Not able to click on Filter logic link");
								log(LogStatus.SKIP,"Not able to click on Filter logic link",YesNo.Yes);
							}

						}else {
							sa.assertTrue(false,"Not Able to Click on advanced filter toggle link on Sub Tab");
							log(LogStatus.SKIP,"Not Able to Click on advanced filter toggle link on Sub Tab",YesNo.Yes);		
						}
						driver.close();
						driver.switchTo().window(parentID);
					} else {
						sa.assertTrue(false,"Not Able to find new window after clicking view all for "+contactName);
						log(LogStatus.SKIP,"Not Able to find new window after clicking view all for "+contactName,YesNo.Yes);
					}

				}else {
					sa.assertTrue(false,"not able to click on view all, so cannot verify filter functionalities");
					log(LogStatus.SKIP,"not able to click on view all, so cannot verify filter functionalities",YesNo.Yes);		
				}

			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact  Not Found : "+contactName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();


	}
	

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc039_VerifySearchActivitiesAttachmentSettings(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		Set<String> dropdownNames= new HashSet<String>();
		dropdownNames.add("Owner");
		dropdownNames.add("Type");
		dropdownNames.add("Status");
		int j=0;
		String parentID=null;
		String subjectValue=Smoke_TaskSendLetterSubject,priorityValue=Smoke_TaskSendLetterPriority;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "clicked on tab "+TabName.Object2Tab, YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 10)) {
				log(LogStatus.INFO, "clicked on contact "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, YesNo.No);
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						if(click(driver, cp.getadvancedFilterImg(projectName, 10), "advanced filter img", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on advanced filter ", YesNo.No);
							for (int i=0;i<3;i++) {

								if (click(driver, cp.getsearchActAttach(projectName,10), "search activities/attach", action.BOOLEAN)) {
									Set<String> selectedoptions= new HashSet<String>();
									List<WebElement> dropdowns=cp.getDropdownListOnSearchActivitiesAttachmentSettings(projectName, 10);
									if (i==0) {
										for (WebElement e:dropdowns) {
											selectedoptions.add(getSelectedOptionOfDropDown(driver, e, "dropdown "+j, "text"));
											j++;
										}
										if(dropdownNames.equals(selectedoptions)) {
											log(LogStatus.INFO, dropdownNames+" successfully verified selected options of dropdowns "+selectedoptions, YesNo.No);
										}
										else {
											log(LogStatus.ERROR,dropdownNames+" could not be verified with currently selected options of dropdowns "+selectedoptions , YesNo.Yes);
											sa.assertTrue(false, dropdownNames+" could not be verified with currently selected options of dropdowns "+selectedoptions);

										}
										if (ip.getCustomTabSaveBtn(projectName,10)!=null) {
											log(LogStatus.INFO, " successfully verified save button", YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "save button could not be verified", YesNo.Yes);
											sa.assertTrue(false,  "save button could not be verified");
										}
										if (ip.getcancelForSearchActivitiesAttachment(projectName, 10)!=null) {
											log(LogStatus.INFO, " successfully verified cancel button", YesNo.No);

										}else {
											log(LogStatus.ERROR, "cancel button could not be verified", YesNo.Yes);
											sa.assertTrue(false,  "cancel button could not be verified");
										}
										if (click(driver, cp.getcrossIconForSearchActivitiesAttachment(projectName, 10), "cross icon", action.BOOLEAN)) {
											log(LogStatus.INFO, " successfully clicked cross button", YesNo.No);
										}else {
											log(LogStatus.ERROR, "cross button could not be clicked", YesNo.Yes);
											sa.assertTrue(false,  "cross button could not be clicked");
										}

									}
									else if(i==1) {
										if (click(driver, cp.getcancelForSearchActivitiesAttachment(projectName, 10), "cancel", action.BOOLEAN)) {
											log(LogStatus.INFO, " successfully clicked cancel button", YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "cancel button could not be clicked", YesNo.Yes);
											sa.assertTrue(false,  "cancel button could not be clicked");
										}

									}
									else {
										//+ icon below type field
										if (sendKeys(driver, cp.getTextBoxInSearchActAttachSettings(projectName, LeftRight.Left, 1, 10), 
												PageLabel.Subject.toString(), "search textbox", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "passed "+PageLabel.Subject.toString()+" in new field textbox", YesNo.No);
											if (cp.selectSuggestionInTextboxInSearchActAttach(projectName, PageLabel.Subject.toString(), 10)) {
												log(LogStatus.INFO, "successfully selected "+PageLabel.Subject.toString(), YesNo.No);
											}
											else {
												log(LogStatus.ERROR, "could not select "+PageLabel.Subject.toString(),  YesNo.Yes);
												sa.assertTrue(false,  "could not select "+PageLabel.Subject.toString());
											}
										}
										else {
											log(LogStatus.ERROR, "textbox for field adding is not visible", YesNo.Yes);
											sa.assertTrue(false,  "textbox for field adding is not visible");
										}
										if (click(driver, cp.getPlusIconInSearchActAttach(projectName, LeftRight.Left, 1, 10), "plus icon below type", action.BOOLEAN)) {
											if (sendKeys(driver, cp.getTextBoxInSearchActAttachSettings(projectName, LeftRight.Left, 2, 10), 
													PageLabel.Priority.toString(), "search textbox", action.SCROLLANDBOOLEAN)) {
												if (cp.selectSuggestionInTextboxInSearchActAttach(projectName, PageLabel.Priority.toString(), 10)) {
													log(LogStatus.INFO, "successfully selected "+PageLabel.Priority.toString(), YesNo.No);
												}
												else {
													log(LogStatus.ERROR, "could not select "+PageLabel.Priority.toString(),  YesNo.Yes);
													sa.assertTrue(false,  "could not select "+PageLabel.Priority.toString());
												}
											}
											else {
												log(LogStatus.ERROR, "textbox for field adding is not visible", YesNo.Yes);
												sa.assertTrue(false,  "textbox for field adding is not visible");
											}
										}
										else {
											log(LogStatus.ERROR, "+ icon not clickable so could not add priority", YesNo.Yes);
											sa.assertTrue(false,  "+ icon not clickable so could not add priority");
										}
										clickUsingJavaScript(driver, cp.getCustomTabSaveBtn(projectName,10),"save");
									}
								}
								else {
									log(LogStatus.ERROR, "search settings icon not clickable so could not add fields", YesNo.Yes);
									sa.assertTrue(false,  "search settings icon not clickable so could not add fields");
								}
							}
							if (sendKeys(driver, ip.getAdvancedFilteDropdowns(projectName, PageLabel.Subject.toString(), 10),
									subjectValue, "subject on advanced filter", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "passed "+subjectValue+" in subject textbox", YesNo.No);

								if (selectVisibleTextFromDropDown(driver, ip.getAdvancedFilteDropdowns(projectName, PageLabel.Priority.toString()
										, 10), "priority dropdown", priorityValue)) {
									log(LogStatus.INFO, "passed "+priorityValue+" in Priority Dropdowns", YesNo.No);
									if (click(driver, cp.clearApplyButtonOnAdvancedFilter(projectName, "apply", 10), "apply", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "clicked apply button", YesNo.No);

										WebElement ele = cp.getAllLinksOnSubTab(projectName, PageName.Object2Page, RelatedTab.Activities, Smoke_TaskSendLetterSubject, action.BOOLEAN, 10);
										if (ele!=null) {
											log(LogStatus.INFO, "successfully verified "+Smoke_TaskSendLetterSubject+" after changing filter", YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "could not verify "+Smoke_TaskSendLetterSubject+" on grid after filter", YesNo.Yes);
											sa.assertTrue(false,  "could not verify "+Smoke_TaskSendLetterSubject+" on grid after filter");

										}
										ThreadSleep(5000);
									}else {
										log(LogStatus.ERROR, "apply button not clickable", YesNo.Yes);
										sa.assertTrue(false,  "apply button not clickable");
									}
								}else {
									log(LogStatus.ERROR, "subject textbox not visible onadvanced filter", YesNo.Yes);
									sa.assertTrue(false,  "subject textbox not visible onadvanced filter");
								}
							}else {
								log(LogStatus.ERROR, "priority dropdpown not visible onadvanced filter", YesNo.Yes);
								sa.assertTrue(false,  "priority dropdpown not visible onadvanced filter");
							}
						}
						else {
							log(LogStatus.ERROR, "advanced filter icon not clickable so could not add fields", YesNo.Yes);
							sa.assertTrue(false,  "advanced filter icon not clickable so could not add fields");
						}
						driver.close();
						driver.switchTo().window(parentID);
					}
					else {
						log(LogStatus.ERROR, "could not find new window to switch, so cannot verify search activities and attachment",YesNo.Yes);
						sa.assertTrue(false, "could not find new window to switch, so cannot verify search activities and attachment");
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on view all, so cannot verify search activities and attachment",YesNo.Yes);
					sa.assertTrue(false,"could not click on view all, so cannot verify search activities and attachment");
				}
			}
			else {
				log(LogStatus.ERROR, "not found contact 3 name",YesNo.Yes);
				sa.assertTrue(false,"not found contact 3 name");
			}
		}
		else {
			log(LogStatus.ERROR, "contact tab not clickable",YesNo.Yes);
			sa.assertTrue(false, "contact tab not clickable");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc040_VerifySearchActivitiesAttachmentRevertToDefault(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		Set<String> dropdownNames= new HashSet<String>();
		dropdownNames.add("Owner");
		dropdownNames.add("Type");
		dropdownNames.add("Status");
		String parentID=null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "clicked on tab "+TabName.Object2Tab, YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 10)) {
				log(LogStatus.INFO, "clicked on contact "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, YesNo.No);
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						if(click(driver, cp.getadvancedFilterImg(projectName, 10), "advanced filter img", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on advanced filter ", YesNo.No);
							if (click(driver, cp.getsearchActAttach(projectName,10), "search activities/attach", action.BOOLEAN)) {
								scrollDownThroughWebelement(driver, cp.revertToDefaultsSearchActivitiesAttachment(projectName, 
										EnableDisable.Enable, 10), "revert to default button");
								if (cp.revertToDefaultsSearchActivitiesAttachment(projectName, EnableDisable.Enable, 10)!=null) {
									log(LogStatus.INFO, "successfully verified revert to default button", YesNo.No);

								}else {
									log(LogStatus.ERROR, "could not verified revert to default button", YesNo.Yes);
									sa.assertTrue(false, "could not verified revert to default button");
								}
								if (click(driver, cp.getCrossIconOfTextboxInSearchActivitiesAttachment(projectName, LeftRight.Left
										, 2, 10), "cross icon beside textbox", action.SCROLLANDBOOLEAN)) {
									if (click(driver, cp.revertToDefaultsSearchActivitiesAttachment(projectName, EnableDisable.Enable,
											10), "revert to default", action.SCROLLANDBOOLEAN)) {
										if (cp.getWarningPopupSearchActivitiesAttachmentYesOrNo(projectName, YesNo.No, 10)!=null) {
											log(LogStatus.INFO, "successfully verified revert to default yes button", YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "could not verified revert to default yes button", YesNo.Yes);
											sa.assertTrue(false, "could not verified revert to default yes button");
										}
										if (cp.getWarningPopupSearchActivitiesAttachmentYesOrNo(projectName, YesNo.Yes, 10)!=null) {
											log(LogStatus.INFO, "successfully verified revert to default no button", YesNo.No);

										}else {
											log(LogStatus.ERROR, "could not verified revert to default no button", YesNo.Yes);
											sa.assertTrue(false, "could not verified revert to default no button");
										}
										if (cp.getrevertToDefaultErrorPopup(projectName, 10)!=null) {
											if (cp.getrevertToDefaultErrorPopup(projectName,10).getText().trim().contains(BasePageErrorMessage.revertToDefaultError1) &&
													cp.getrevertToDefaultErrorPopup(projectName,10).getText().trim().contains(BasePageErrorMessage.revertToDefaultError2)) {
												log(LogStatus.INFO, "successfully verified revert to default error message", YesNo.No);
											}
											else {
												log(LogStatus.ERROR, "could not verified revert to default error message", YesNo.Yes);
												sa.assertTrue(false, "could not verified revert to default error message");

											}
										}else {
											log(LogStatus.ERROR, "not present revert to default error message", YesNo.Yes);
											sa.assertTrue(false, "not present revert to default error message");

										}
										if (click(driver, cp.revertToDefaultsSearchActivitiesAttachment(projectName, EnableDisable.Enable, 
												10), "revert to default", action.SCROLLANDBOOLEAN)) {
											if (click(driver, cp.getWarningPopupSearchActivitiesAttachmentYesOrNo(projectName, YesNo.No, 10), "n", action.BOOLEAN)) {
												log(LogStatus.INFO, "successfully clicked revert to default yes button", YesNo.No);

											}else {
												log(LogStatus.ERROR, "could not click revert to default error message", YesNo.Yes);
												sa.assertTrue(false, "could not click revert to default error message");

											}
										}
										else {
											log(LogStatus.ERROR, "could not click revert to default button", YesNo.Yes);
											sa.assertTrue(false, "could not click revert to default button");

										}
									}
									else {
										log(LogStatus.ERROR, "could not click revert to default button", YesNo.Yes);
										sa.assertTrue(false, "could not click revert to default button");

									}
								}else {
									log(LogStatus.ERROR, "could not click cross icon in front of priority dropdown", YesNo.Yes);
									sa.assertTrue(false, "could not click cross icon in front of priority dropdown");

								}
								if (click(driver, cp.revertToDefaultsSearchActivitiesAttachment(projectName, EnableDisable.Enable, 
										10), "revert to default", action.SCROLLANDBOOLEAN)) {
									if (click(driver, cp.getWarningPopupSearchActivitiesAttachmentYesOrNo(projectName, YesNo.No, 10), "no", action.BOOLEAN)) {
										log(LogStatus.INFO, "successfully clicked revert to default yes button", YesNo.No);

									}else {
										log(LogStatus.ERROR, "could not click revert to default error message", YesNo.Yes);
										sa.assertTrue(false, "could not click revert to default error message");

									}
								}
								else {
									log(LogStatus.ERROR, "could not click revert to default button", YesNo.Yes);
									sa.assertTrue(false, "could not click revert to default button");

								}
								if (click(driver, cp.revertToDefaultsSearchActivitiesAttachment(projectName, EnableDisable.Enable, 
										10), "revert to default", action.SCROLLANDBOOLEAN)) {
									if (click(driver, cp.getWarningPopupSearchActivitiesAttachmentYesOrNo(projectName, YesNo.Yes, 10), "yes", action.BOOLEAN)) {
										log(LogStatus.INFO, "successfully clicked revert to default yes button", YesNo.No);

									}else {
										log(LogStatus.ERROR, "could not click revert to default error message", YesNo.Yes);
										sa.assertTrue(false, "could not click revert to default error message");

									}
								}
								else {
									log(LogStatus.ERROR, "could not click revert to default button", YesNo.Yes);
									sa.assertTrue(false, "could not click revert to default button");

								}
								//verify absence of subject and priority
								scrollDownThroughWebelement(driver, cp.getAdvancedFilteDropdowns(projectName, PageLabel.Owner.toString(),10), "advanced filter page");
								if (cp.getAdvancedFilteDropdowns(projectName, PageLabel.Subject.toString(), 5)==null) {
									log(LogStatus.INFO, "successfully verified absence of Subject textbox", YesNo.No);

								}else {
									log(LogStatus.ERROR, "Subject textbox is present, but it should not be", YesNo.Yes);
									sa.assertTrue(false, "Subject textbox is present, but it should not be");

								}
								if (cp.getAdvancedFilteDropdowns(projectName, PageLabel.Priority.toString(), 5)==null) {
									log(LogStatus.INFO, "successfully verified absence of Priority Dropdowns", YesNo.No);

								}else {
									log(LogStatus.ERROR, "Priority Dropdowns is present, but it should not be", YesNo.Yes);
									sa.assertTrue(false, "Priority Dropdowns is present, but it should not be");

								}
							}else {
								log(LogStatus.ERROR, "could not click search activities settings icon", YesNo.Yes);
								sa.assertTrue(false, "could not click search activities settings icon");

							}
						}else {
							log(LogStatus.ERROR, "could not click advanced filter img", YesNo.Yes);
							sa.assertTrue(false, "could not click advanced filter img");
						}
						driver.close();
						driver.switchTo().window(parentID);
					}
					else {
						log(LogStatus.ERROR, "could not find new window to switch",YesNo.Yes);
						sa.assertTrue(false, "could not find new window to switch");
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on view all button so cannot verify revert to default for search act and attach",YesNo.Yes);
					sa.assertTrue(false, "could not click on view all button so cannot verify revert to default for search act and attach");
				}
			}
			else {
				log(LogStatus.ERROR, "not found contact 3 name",YesNo.Yes);
				sa.assertTrue(false,"not found contact 3 name");
			}
		}
		else {
			log(LogStatus.ERROR, "contact tab not clickable",YesNo.Yes);
			sa.assertTrue(false, "contact tab not clickable");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc041_VerifySendAnEmailWithAttachment_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String attachmentPath= System.getProperty("user.dir")+"\\UploadFiles\\tc039\\Testing.pdf";
		String date=todaysDateSingleDateSingleMonth;
		
		System.out.println(attachmentPath);
		lp.switchToClassic();
		String mode="Classic",environment="";
		if (cp.clickOnTab(projectName,environment, mode,TabName.Object2Tab)) {
			log(LogStatus.INFO, "clicked on tab "+TabName.Object2Tab, YesNo.No);
			if (cp.clickOnCreatedContact(environment, mode, Smoke_TaskContact3FName, Smoke_TaskContact3LName)) {
			log(LogStatus.INFO, "clicked on contact "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, YesNo.No);
				if (click(driver, cp.sendAnEmailButtonOnActivityHistory(projectName, 10), "send an email", action.SCROLLANDBOOLEAN)) {
					if (cp.sendEmail(mode,projectName, Smoke_Task20Subject, Smoke_Task20Comment, true, attachmentPath, 20)) {
						log(LogStatus.INFO, "successfully sent email to "+ Smoke_TaskContact3FName+" "+ Smoke_TaskContact3LName, YesNo.No);
						ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask20", excelLabel.Due_Date);
						
					}
					else {
						log(LogStatus.ERROR, "could not send email", YesNo.Yes);
						sa.assertTrue(false, "could not send email");
					}
				}else {
					log(LogStatus.ERROR, "send email button is not clickable on contact page, could not send email", YesNo.Yes);
					sa.assertTrue(false, "send email button is not clickable on contact page, could not send email");
				}
			}else {
				log(LogStatus.ERROR,  Smoke_TaskContact3FName+" "+ Smoke_TaskContact3LName+" is not found on contact page, could not send email", YesNo.Yes);
				sa.assertTrue(false, Smoke_TaskContact3FName+" "+ Smoke_TaskContact3LName+" is not found on contact page, could not send email");
			}
		}else {
			log(LogStatus.ERROR, "contact tab is not clickable, so could not send email", YesNo.Yes);
			sa.assertTrue(false, "contact tab is not clickable, so could not send email");
		}
		switchToDefaultContent(driver);
		lp.switchToLighting();
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc041_VerifySendAnEmailWithAttachment_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		List<String> taskstd1=new LinkedList<String>();
		String username=crmUser1FirstName+" "+crmUser1LastName;
		taskstd1.add(Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
		taskstd1.add("");
		taskstd1.add(Smoke_Task20Status);
		taskstd1.add(username);
		taskstd1.add("");
		taskstd1.add(Activity.Email.toString());
		taskstd1.add(Links.View.toString());	
		List<String> taskstd2=new LinkedList<String>();
		taskstd2.add("");
		taskstd2.add(Smoke_Task20Subject);
		taskstd2.add("");
		taskstd2.add(username);
		taskstd2.add("");
		taskstd2.add("");
		taskstd2.add("");	
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask20", excelLabel.Due_Date);
		String fileName="Testing.pdf";
		String parentID=null, thirdWindow=null,secondWindow=null;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		TabName[] tn= {TabName.Object2Tab,TabName.Object1Tab};
		WebElement ele=null;String contact=Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		PageName pn[]= {PageName.Object2Page,PageName.Object1Page};
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "clicked on tab "+TabName.Object2Tab, YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab,contact, 10)) {
				log(LogStatus.INFO, "clicked on contact "+contact, YesNo.No);
				ele = lp.getActivityTimeLineItem2(projectName, PageName.Object1Page, ActivityTimeLineItem.Expand_All, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of expand all of task ",YesNo.No);
					if (click(driver, ele, "expand task", action.BOOLEAN)) {

						String msg1=BasePageErrorMessage.MailSentMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0, false);
						//msg1+=" about "+taskCustomObj2Name;
						lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,Smoke_TaskINS2Name, Smoke_Task20Subject,msg1,date, false,"",false, "", 10);
						ele = lp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task20Subject, SubjectElement.Attachment, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of attachment icon"+Smoke_Task20Subject, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not find attachment icon for "+Smoke_Task20Subject, YesNo.Yes);
							sa.assertTrue(false, "could not find attachment icon for "+Smoke_Task20Subject);
						}
						lp.verifyFromAddressToAddressTextBodyOnActivityTimeline(projectName,  PageName.Object2Page, Smoke_Task20Subject, ActivityType.Past, username, contact, Smoke_Task20Comment,true);
						ele=lp.clickHereToRespondLink(projectName, ActivityType.Past, Smoke_Task20Subject);
						if (ele!=null) {
							if (click(driver, ele, "click here to respond", action.SCROLLANDBOOLEAN)) {
								parentID=switchOnWindow(driver);
								if (parentID!=null) {
									if (lp.returnEmailHeadingOnPage(projectName, Smoke_Task20Subject)!=null) {
										log(LogStatus.INFO, "successfully verified email page of "+Smoke_Task20Subject, YesNo.No);
									}
									else {
										log(LogStatus.ERROR, "could not verify email page of "+Smoke_Task20Subject, YesNo.Yes);
										sa.assertTrue(false, "could not verify email page of "+Smoke_Task20Subject);
									}
									driver.close();
									driver.switchTo().window(parentID);
								}else {
									log(LogStatus.ERROR, "could not find new window after clicking on click here to respond", YesNo.Yes);
									sa.assertTrue(false, "could not find new window after clicking on click here to respond");
								}
							}else {
								log(LogStatus.ERROR, "could not find click here to respond", YesNo.Yes);
								sa.assertTrue(false, "could not find click here to respond");
							}
						}else {
							log(LogStatus.ERROR, "could not find click here to respond is not clickable", YesNo.Yes);
							sa.assertTrue(false, "could not find click here to respond is not clickable");
						}
						if (click(driver, lp.linkOnArticleSectionOnActivity(projectName, Smoke_Task20Subject, username), username, action.BOOLEAN)) {
							ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, username, action.BOOLEAN, 10);
							if (ele!=null) {
								log(LogStatus.INFO,"Landing Page Verified for : "+username,YesNo.No);	
							} else {
								sa.assertTrue(false,"Landing Page Not Verified for : "+username);
								log(LogStatus.SKIP,"Landing Page Not Verified for : "+username,YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"could not click on "+username);
							log(LogStatus.SKIP,"could not click on "+username,YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "could not click expand all", YesNo.Yes);
						sa.assertTrue(false, "could not click expand all");
					}
				}else {
					log(LogStatus.ERROR, "expand all not found", YesNo.Yes);
					sa.assertTrue(false, "expand all not found");
				}
			}
			else {
				log(LogStatus.ERROR, "could not click on : "+contact, YesNo.Yes);
				sa.assertTrue(false, "could not click on : "+contact);

			}
		}
		else {
			log(LogStatus.ERROR, "object 2 tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "object 2 tab is not clickable");

		}

		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "clicked on tab "+TabName.Object2Tab, YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab,contact, 10)) {
				log(LogStatus.INFO, "clicked on contact "+contact, YesNo.No);
				ele = lp.getActivityTimeLineItem2(projectName, PageName.Object1Page, ActivityTimeLineItem.Expand_All, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of expand all of task ",YesNo.No);
					if (click(driver, ele, "expand task", action.BOOLEAN)) {
						if (click(driver, lp.linkOnArticleSectionOnActivity(projectName, Smoke_Task20Subject, contact), contact, action.BOOLEAN)) {
							ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, contact, action.BOOLEAN, 10);
							if (ele!=null) {
								log(LogStatus.INFO,"Landing Page Verified for : "+contact,YesNo.No);	
							} else {
								sa.assertTrue(false,"Landing Page Not Verified for : "+contact);
								log(LogStatus.SKIP,"Landing Page Not Verified for : "+contact,YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"could not click on "+username);
							log(LogStatus.SKIP,"could not click on "+username,YesNo.Yes);
						}
						if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object2Page, "View All", 10)) {
							parentID=switchOnWindow(driver);
							if (parentID!=null) {

								lp.verifyingRelatedTabData2(projectName,PageName.Object2Page, RelatedTab.Activities, date,"Email: "+Smoke_Task20Subject, taskstd1, action.BOOLEAN, 15);

								if (click(driver,cp.getLinkOnSubTab(projectName, PageName.Object2Page, RelatedTab.Activities, Smoke_Task20Subject,
										"View", null, action.BOOLEAN, 10),"view", action.BOOLEAN)) {
									ThreadSleep(3000);
									if (cp.getcommentsTextOnPopup(projectName, 10)!=null) {
										String text=cp.getcommentsTextOnPopup(projectName, 10).getText().trim();
										scrollDownThroughWebelement(driver, ip.getOkButtonRAorComment(projectName,"Comment", 10), "ok");
										appLog.info(text);
										if (text.contains(Smoke_TaskContact3EmailID)) {
											log(LogStatus.INFO, "successfully verified presence of "+Smoke_TaskContact3EmailID, YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "contact 3 email id not present on comments popup for 'to' "+Smoke_TaskContact3EmailID, YesNo.Yes);
											sa.assertTrue(false, "contact 3 email id not present on comments popup for 'to' "+Smoke_TaskContact3EmailID);
										}
										if (text.contains(crmUser1EmailID)) {
											log(LogStatus.INFO, "successfully verified presence of "+crmUser1EmailID, YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "user1 email id not present on comments popup on bcc "+crmUser1EmailID, YesNo.Yes);
											sa.assertTrue(false, "user1 email id not present on comments popup on bcc "+crmUser1EmailID);
										}
										if (text.contains("Attachment: "+fileName)) {
											log(LogStatus.INFO, "successfully verified presence of attachment "+fileName, YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "attachment name not present on comments popup", YesNo.Yes);
											sa.assertTrue(false, "attachment name not present on comments popup");
										}
										if (text.contains("Subject: "+Smoke_Task20Subject)) {
											log(LogStatus.INFO, "successfully verified presence of Subject "+Smoke_Task20Subject, YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "Subject not present on comments popup", YesNo.Yes);
											sa.assertTrue(false, "Subject not present on comments popup");
										}
										if (text.contains(Smoke_Task20Comment)) {
											log(LogStatus.INFO, "successfully verified presence of body "+Smoke_Task20Comment, YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "body text not present on comments popup", YesNo.Yes);
											sa.assertTrue(false, "body text not present on comments popup");
										}
									}else {
										log(LogStatus.ERROR, "comment text popup is not visible", YesNo.Yes);
										sa.assertTrue(false, "comment text popup is not visible");
									}
									click(driver, ip.getOkButtonRAorComment(projectName,"Comment", 10), "ok icon", action.SCROLLANDBOOLEAN);
								}
								else {
									log(LogStatus.ERROR, "view link is not clickable, so cannot verify comments popup", YesNo.Yes);
									sa.assertTrue(false, "view link is not clickable, so cannot verify comments popup");

								}




								if (!isSelected(driver, ip.getattachmentCheckbox(projectName, 10), "attachments")) {
									click(driver,ip.getattachmentCheckbox(projectName, 10) , "attachments checkbox", action.BOOLEAN);
									log(LogStatus.INFO, "attachments checkbox is now checked", YesNo.No);
								}
								else {
									log(LogStatus.INFO, "attachments checkbox is already checked", YesNo.No);

								}
								lp.verifyingRelatedTabData2(projectName,PageName.Object2Page, RelatedTab.Activities, date,fileName, taskstd2, action.BOOLEAN, 15);

								ele = cp.getAllLinksOnSubTab(projectName, PageName.Object2Page, RelatedTab.Activities,Smoke_Task20Subject, action.BOOLEAN, 10);

								boolean flag=false;
								secondWindow=driver.getWindowHandle();
								if (click(driver, ele, "email subject", action.BOOLEAN)) {
									for (String win:driver.getWindowHandles()) {
										if (!win.equals(parentID) && (!win.equals(secondWindow))) {
											driver.switchTo().window(win);
											ThreadSleep(5000);
											flag=true;
											break;
										}
									}
									if (flag) {
										ThreadSleep(5000);
										thirdWindow=driver.getWindowHandle();
										if (clickUsingJavaScript(driver, ip.clickOnDocumentOnEmailPage(projectName, fileName, 10), "file name : "+fileName, action.BOOLEAN)) {
											for (String win:driver.getWindowHandles()) {
												if (!win.equals(parentID) && (!win.equals(secondWindow) && (!win.equals(thirdWindow)))) {
													driver.switchTo().window(win);
													ThreadSleep(5000);
													flag=true;
													break;
												}
											}
											if (flag) {
												log(LogStatus.INFO, "successfully verified document opening", YesNo.No);
											}
											else {
												log(LogStatus.ERROR, "new window is opened, but document is not verified on", YesNo.Yes);
												sa.assertTrue(false, "new window is opened, but document is not verified");
											}
											driver.close();
											driver.switchTo().window(thirdWindow);
										}
										else {
											log(LogStatus.ERROR, "could not click on document name on email page", YesNo.Yes);
											sa.assertTrue(false, "could not click on document name on email page");
										}
										driver.close();
										driver.switchTo().window(secondWindow);
									}
									else {
										log(LogStatus.ERROR, "new window not found", YesNo.Yes);
										sa.assertTrue(false, "new window not found");

									}
								}
								else {
									log(LogStatus.ERROR, "email subject is not clickable, so cannot verify email document", YesNo.Yes);
									sa.assertTrue(false, "email subject is not clickable, so cannot verify email document");
								}
								driver.close();
								driver.switchTo().window(parentID);
							}else {
								log(LogStatus.ERROR, "could not find new window to switch after clicking view all", YesNo.Yes);
								sa.assertTrue(false, "could not find new window to switch after clicking view all");
							}
						}else {
							log(LogStatus.ERROR, "could not click on view all", YesNo.Yes);
							sa.assertTrue(false, "could not click on view all");
						}
					}else {
						log(LogStatus.ERROR, "could not click expand all", YesNo.Yes);
						sa.assertTrue(false, "could not click expand all");
					}
				}else {
					log(LogStatus.ERROR, "expand all not found", YesNo.Yes);
					sa.assertTrue(false, "expand all not found");
				}
			}
			else {
				log(LogStatus.ERROR, "could not click on : "+contact, YesNo.Yes);
				sa.assertTrue(false, "could not click on : "+contact);

			}
		}
		else {
			log(LogStatus.ERROR, "object 2 tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "object 2 tab is not clickable");

		}
		
		
		
		
		
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO, "clicked on tab "+TabName.Object1Tab, YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab,Smoke_TaskINS2Name, 10)) {
				log(LogStatus.INFO, "clicked on contact "+Smoke_TaskINS2Name, YesNo.No);
				ele = lp.getActivityTimeLineItem2(projectName, PageName.Object1Page, ActivityTimeLineItem.Expand_All, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of expand all of task ",YesNo.No);
					if (click(driver, ele, "expand task", action.BOOLEAN)) {

						String msg1=BasePageErrorMessage.MailSentMsg(null, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 0, false);
						//msg1+=" about "+taskCustomObj2Name;
						lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,Smoke_TaskINS2Name, Smoke_Task20Subject,msg1, date, false,"",false, "", 10);
						ele = lp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task20Subject, SubjectElement.Attachment, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of attachment icon"+Smoke_Task20Subject, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not find attachment icon for "+Smoke_Task20Subject, YesNo.Yes);
							sa.assertTrue(false, "could not find attachment icon for "+Smoke_Task20Subject);
						}
						lp.verifyFromAddressToAddressTextBodyOnActivityTimeline(projectName,  PageName.Object2Page, Smoke_Task20Subject, ActivityType.Past, username, contact, Smoke_Task20Comment,true);
						ele=lp.clickHereToRespondLink(projectName, ActivityType.Past, Smoke_Task20Subject);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of click here to respond"+Smoke_Task20Subject, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not find click here to respond for "+Smoke_Task20Subject, YesNo.Yes);
							sa.assertTrue(false, "could not find click here to respond for "+Smoke_Task20Subject);
						}
						
						
						if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object2Page, "View All", 10)) {
						

						parentID=switchOnWindow(driver);
						if (parentID!=null) {

							lp.verifyingRelatedTabData2(projectName,PageName.Object2Page, RelatedTab.Activities, date,"Email: "+Smoke_Task20Subject, taskstd1, action.BOOLEAN, 15);





							if (!isSelected(driver, ip.getattachmentCheckbox(projectName, 10), "attachments")) {
								click(driver,ip.getattachmentCheckbox(projectName, 10) , "attachments checkbox", action.BOOLEAN);
								log(LogStatus.INFO, "attachments checkbox is now checked", YesNo.No);
							}
							else {
								log(LogStatus.INFO, "attachments checkbox is already checked", YesNo.No);

							}
							lp.verifyingRelatedTabData2(projectName,PageName.Object2Page, RelatedTab.Activities, date,fileName, taskstd2, action.BOOLEAN, 15);

							ele = cp.getAllLinksOnSubTab(projectName, PageName.Object2Page, RelatedTab.Activities,Smoke_Task20Subject, action.BOOLEAN, 10);

							boolean flag=false;
							secondWindow=driver.getWindowHandle();
							if (click(driver, ele, "email subject", action.BOOLEAN)) {
								for (String win:driver.getWindowHandles()) {
									if (!win.equals(parentID) && (!win.equals(secondWindow))) {
										driver.switchTo().window(win);
										ThreadSleep(5000);
										flag=true;
										break;
									}
								}
								if (flag) {
									ThreadSleep(5000);
									thirdWindow=driver.getWindowHandle();
									if (clickUsingJavaScript(driver, ip.clickOnDocumentOnEmailPage(projectName, fileName, 10), "file name : "+fileName, action.BOOLEAN)) {
										for (String win:driver.getWindowHandles()) {
											if (!win.equals(parentID) && (!win.equals(secondWindow) && (!win.equals(thirdWindow)))) {
												driver.switchTo().window(win);
												ThreadSleep(5000);
												flag=true;
												break;
											}
										}
										if (flag) {
											log(LogStatus.INFO, "successfully verified document opening", YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "new window is opened, but document is not verified on", YesNo.Yes);
											sa.assertTrue(false, "new window is opened, but document is not verified");
										}
										driver.close();
										driver.switchTo().window(thirdWindow);
									}
									else {
										log(LogStatus.ERROR, "could not click on document name on email page", YesNo.Yes);
										sa.assertTrue(false, "could not click on document name on email page");
									}
									driver.close();
									driver.switchTo().window(secondWindow);
								}
								else {
									log(LogStatus.ERROR, "new window not found", YesNo.Yes);
									sa.assertTrue(false, "new window not found");

								}
							}
							else {
								log(LogStatus.ERROR, "email subject is not clickable, so cannot verify email document", YesNo.Yes);
								sa.assertTrue(false, "email subject is not clickable, so cannot verify email document");
							}
							driver.close();
							driver.switchTo().window(parentID);
						}else {
							log(LogStatus.ERROR, "could not find new window to switch after clicking view all", YesNo.Yes);
							sa.assertTrue(false, "could not find new window to switch after clicking view all");
						}
						}else {
							log(LogStatus.ERROR, "could not click on view all", YesNo.Yes);
							sa.assertTrue(false, "could not click on view all");
						}
					}else {
						log(LogStatus.ERROR, "expand button is not clickable", YesNo.Yes);
						sa.assertTrue(false, "expand button is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "expand all button is not visible", YesNo.Yes);
					sa.assertTrue(false, "expand all button is not visible");
				}
			}else {
				log(LogStatus.ERROR, Smoke_TaskINS2Name+" is not found, so cannot verify activities", YesNo.Yes);
				sa.assertTrue(false, Smoke_TaskINS2Name+" is not found, so cannot verify activities");
			}
		}else {
			log(LogStatus.ERROR, TabName.Object1Tab+" is not found, so cannot verify activities", YesNo.Yes);
			sa.assertTrue(false, TabName.Object1Tab+" is not found, so cannot verify activities");
		}
					
		lp.CRMlogout();
		sa.assertAll();
	}
		

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc042_VerifySendListEmailWithAttachment_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
			
		String date=todaysDateSingleDateSingleMonth;
		String attachmentPath= System.getProperty("user.dir")+"\\UploadFiles\\tc040\\Testing List Email.docx";
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele=null;
		PageName pn[]= {PageName.Object2Page,PageName.Object1Page};
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "clicked on tab "+TabName.Object2Tab, YesNo.No);
			if (cp.sendListEmail(projectName, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+","+Smoke_TaskContact2FName+" "
					+Smoke_TaskContact2LName, Smoke_TestListEmailSubject, Smoke_TestListEmailComment, attachmentPath)) {
				log(LogStatus.INFO, "successfully sent email", YesNo.No);
				ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask25", excelLabel.Due_Date);
				
			}
			else {
				log(LogStatus.ERROR, "could not send email through send list email button", YesNo.Yes);
				sa.assertTrue(false, "could not send email through send list email button" );
			}
		}else {
			log(LogStatus.ERROR, "tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "tab is not clickable" );
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		

	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc042_VerifySendListEmailWithAttachment_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		List<String> taskstd1=new LinkedList<String>();
		taskstd1.add(Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
		taskstd1.add(Smoke_TestListEmailSubject);
		taskstd1.add(Status.Completed.toString());
		taskstd1.add(crmUser1FirstName+" "+crmUser1LastName);
		taskstd1.add("");
		taskstd1.add(Activity.ListEmail.toString());
		taskstd1.add(Links.View.toString());	
		List<String> taskstd2=new LinkedList<String>();
		taskstd2.add(Smoke_TaskContact2FName+" "+Smoke_TaskContact2LName);
		taskstd2.add(Smoke_TestListEmailSubject);
		taskstd2.add(Status.Completed.toString());
		taskstd2.add(crmUser1FirstName+" "+crmUser1LastName);
		taskstd2.add("");
		taskstd2.add(Activity.ListEmail.toString());
		taskstd2.add(Links.View.toString());	
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask25", excelLabel.Due_Date);
		
		String attachmentPath= System.getProperty("user.dir")+"\\UploadFiles\\tc040\\Testing List Email.docx";
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele=null;
		String parentID=null, secondWindow;
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "clicked on tab "+TabName.Object2Tab, YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab,Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName , 10)) {
				log(LogStatus.INFO, "clicked on contact "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, YesNo.No);
				
				ele = cp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Expand_All, 10);
				
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of expand all of task ",YesNo.No);
					if (click(driver, ele, "expand task", action.BOOLEAN)) {
					}
					else {
						log(LogStatus.ERROR, "could not click on expand all", YesNo.No);
						sa.assertTrue(false, "could not click on expand all");
					}
				}
				else {
					log(LogStatus.ERROR, "expand all is not present", YesNo.No);
					sa.assertTrue(false, "expand all is not present");
				}
				String msg1=BasePageErrorMessage.MailSentMsg(null, null, 0, true);
				//msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,Smoke_TaskINS2Name, Smoke_TestListEmailSubject,msg1, date, false,"",false, "", 10);
				lp.verifyListEmailActivityTimeline(projectName, PageName.Object2Page, Smoke_TestListEmailSubject, ActivityType.Past, date, 2, Smoke_TestListEmailStatus, Smoke_TestListEmailComment);
				
				
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object2Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {

						lp.verifyingRelatedTabData2(projectName,PageName.Object2Page, RelatedTab.Activities, date,"List Email: "+ Smoke_TestListEmailSubject, taskstd1, action.BOOLEAN, 15);
					
					ele=ip.getLinkOnSubTab(projectName, PageName.Object2Page, RelatedTab.Activities, Smoke_TestListEmailSubject,
						"View",null , action.BOOLEAN, 10);
					if (click(driver, ele, "view", action.BOOLEAN)) {
						ele=cp.getcommentsTextOnPopup(projectName, 5);
						if (ele==null) {
							log(LogStatus.INFO, "successfullu verified empty comments text", YesNo.No);

						}
						else {
							String text=cp.getcommentsTextOnPopup(projectName, 5).getText().trim();
							if (text.equalsIgnoreCase("")) {
								log(LogStatus.INFO, "successfullu verified empty comments text", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify empty comments text", YesNo.No);
								sa.assertTrue(false, "could not verify empty comments text");
							}
						}
						click(driver, ip.getCrossIconRAorComment(projectName,"Comment", 10), "ok icon", action.BOOLEAN);
							
					}else {
						log(LogStatus.ERROR, "view link is not clickable, so cannot verify comments", YesNo.No);
						sa.assertTrue(false, "view link is not clickable, so cannot verify comments");
					}
				ele = cp.getAllLinksOnSubTab(projectName, PageName.Object2Page, RelatedTab.Activities, Smoke_TestListEmailSubject, action.BOOLEAN, 10);
				boolean flag=false;
				secondWindow=driver.getWindowHandle();
				if (click(driver, ele, "email subject", action.BOOLEAN)) {
					for (String win:driver.getWindowHandles()) {
						if (!win.equals(parentID) && (!win.equals(secondWindow))) {
							driver.switchTo().window(win);
							ThreadSleep(5000);
							flag=true;
							break;
						}
					}
					if (flag) {
						ThreadSleep(5000);
						if (cp.getHeaderTextForPage(projectName, PageName.ListEmail, Smoke_TestListEmailSubject, action.BOOLEAN, 10)!=null) {
							
							log(LogStatus.INFO, "successfullu verified list email detail page", YesNo.No);
						}else {
							log(LogStatus.ERROR, "could not verify list email detail page", YesNo.No);
							sa.assertTrue(false, "could not verify list email detail page");
						}
						driver.close();
						driver.switchTo().window(secondWindow);
					}else {
						log(LogStatus.ERROR, "new window could not be opened", YesNo.No);
						sa.assertTrue(false, "new window could not be opened");
					}
				}else {
					log(LogStatus.ERROR, "email subject link could not be opened", YesNo.No);
					sa.assertTrue(false, "email subject link could not be opened");
				}
				driver.close();
				driver.switchTo().window(parentID);
				}else {
					log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch");
				}
				}
					else {
						log(LogStatus.ERROR, "view all is not clickable", YesNo.Yes);
						sa.assertTrue(false, "view all is not clickable");
					}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName +" on object 2 tab", YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName +" on object 2 tab");
			}
		}else {
			log(LogStatus.ERROR, "contact tab is not clickable, so cannot verify email document", YesNo.Yes);
			sa.assertTrue(false, "contact tab is not clickable, so cannot verify email document");
		}
		
		switchToDefaultContent(driver);
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "clicked on tab "+TabName.Object2Tab, YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab,Smoke_TaskContact2FName+" "+Smoke_TaskContact2LName , 10)) {
				log(LogStatus.INFO, "clicked on contact "+Smoke_TaskContact2FName+" "+Smoke_TaskContact2LName, YesNo.No);
				
				ele = cp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Expand_All, 10);
				
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of expand all of task ",YesNo.No);
					if (click(driver, ele, "expand task", action.BOOLEAN)) {
					}
					else {
						log(LogStatus.ERROR, "could not click on expand all", YesNo.No);
						sa.assertTrue(false, "could not click on expand all");
					}
				}
				else {
					log(LogStatus.ERROR, "expand all is not present", YesNo.No);
					sa.assertTrue(false, "expand all is not present");
				}
				String msg1=BasePageErrorMessage.MailSentMsg(null, null, 0, true);
				//msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,Smoke_TaskINS2Name, Smoke_TestListEmailSubject,msg1, date, false,"",false, "", 10);
				lp.verifyListEmailActivityTimeline(projectName, PageName.Object2Page, Smoke_TestListEmailSubject, ActivityType.Past, date, 2, Smoke_TestListEmailStatus, Smoke_TestListEmailComment);
				
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object2Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {

						lp.verifyingRelatedTabData2(projectName,PageName.Object2Page, RelatedTab.Activities, date,"List Email: "+ Smoke_TestListEmailSubject, taskstd2, action.BOOLEAN, 15);
						driver.close();
						driver.switchTo().window(parentID);
					}else {
						log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
						sa.assertTrue(false, "could not find new window to switch");
					}
				}else {
					log(LogStatus.ERROR, "view all is not clickable", YesNo.Yes);
					sa.assertTrue(false, "view all is not clickable");
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TaskContact2FName+" "+Smoke_TaskContact2LName +" on object 2 tab", YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TaskContact2FName+" "+Smoke_TaskContact2LName +" on object 2 tab");
			}
		}else {
			log(LogStatus.ERROR, "contact tab is not clickable, so cannot verify email document", YesNo.Yes);
			sa.assertTrue(false, "contact tab is not clickable, so cannot verify email document");
		}
		
		switchToDefaultContent(driver);
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO, "clicked on tab "+TabName.Object1Tab, YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab,Smoke_TaskINS2Name , 10)) {
				log(LogStatus.INFO, "clicked on entity/account "+Smoke_TaskINS2Name, YesNo.No);


				ele = cp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Expand_All, 10);
				
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of expand all of task ",YesNo.No);
					if (click(driver, ele, "expand task", action.BOOLEAN)) {
					}
					else {
						log(LogStatus.ERROR, "could not click on expand all", YesNo.No);
						sa.assertTrue(false, "could not click on expand all");
					}
				}
				else {
					log(LogStatus.ERROR, "expand all is not present", YesNo.No);
					sa.assertTrue(false, "expand all is not present");
				}
				String msg1=BasePageErrorMessage.MailSentMsg(null, null, 0, true);
				//msg1+=" about "+taskCustomObj2Name;
				lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,Smoke_TaskINS2Name, Smoke_TestListEmailSubject,msg1, date, false,"",false, "", 10);
				lp.verifyListEmailActivityTimeline(projectName, PageName.Object2Page, Smoke_TestListEmailSubject, ActivityType.Past, date, 2, Smoke_TestListEmailStatus, Smoke_TestListEmailComment);

				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object2Page, "View All", 10)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {

						lp.verifyingRelatedTabData2(projectName,PageName.Object2Page, RelatedTab.Activities, date,"List Email: "+ Smoke_TestListEmailSubject, taskstd1, action.BOOLEAN, 15);

						ele = cp.getAllLinksOnSubTab(projectName, PageName.Object2Page, RelatedTab.Activities,Smoke_TestListEmailSubject, action.BOOLEAN, 10);
						secondWindow=driver.getWindowHandle();
						boolean flag=false;
						if (click(driver, ele, "email subject", action.BOOLEAN)) {
							for (String win:driver.getWindowHandles()) {
								if (!win.equals(parentID) && (!win.equals(secondWindow))) {
									driver.switchTo().window(win);
									ThreadSleep(5000);
									flag=true;
									break;
								}
							}
							if (flag) {
								ThreadSleep(5000);
								if (cp.getHeaderTextForPage(projectName, PageName.ListEmail, Smoke_TestListEmailSubject, action.BOOLEAN, 10)!=null) {

									log(LogStatus.INFO, "successfullu verified list email detail page", YesNo.No);
								}else {
									log(LogStatus.ERROR, "could not verify list email detail page", YesNo.No);
									sa.assertTrue(false, "could not verify list email detail page");
								}
								driver.close();
								driver.switchTo().window(secondWindow);
							}else {
								log(LogStatus.ERROR, "new window could not be opened", YesNo.No);
								sa.assertTrue(false, "new window could not be opened");
							}
						}else {
							log(LogStatus.ERROR, "email subject link could not be opened", YesNo.No);
							sa.assertTrue(false, "email subject link could not be opened");
						}
						driver.close();
						driver.switchTo().window(parentID);
					}else {
						log(LogStatus.ERROR, "could not find new window to switch", YesNo.No);
						sa.assertTrue(false, "could not find new window to switch");
					}
				}else {
					log(LogStatus.ERROR, "view all is not clickable", YesNo.No);
					sa.assertTrue(false, "view all is not clickable");
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TaskINS2Name +" on object 2 tab", YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TaskINS2Name +" on object 2 tab");
			}
		}else {
			log(LogStatus.ERROR, "entity/account tab is not clickable, so cannot verify email document", YesNo.Yes);
			sa.assertTrue(false, "entity/account tab is not clickable, so cannot verify email document");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	
	//	
	////////////////////////////////////// Azhar /////////////////////////////
	
	@Parameters({ "projectName"})
	@Test

	public void AASmokeTc043_VerifyNewMeetingButtonFromContactDetailPageWithBlankNameAndRelatedAssociation(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_MTContact3FName+" "+Smoke_MTContact3LName;
		WebElement ele ;
		String parentID=null;

		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Meeting, 10);
				ThreadSleep(2000);
				if (mouseOverOperation(driver, ele)) {
					log(LogStatus.INFO,"Mouse hover on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);
					ThreadSleep(2000);
					
					ele = lp.getNewMeetingNewTabIconAfterMouseHover(projectName, 15);
					if (ele==null) {
						log(LogStatus.INFO,"Tab Icon is not Present After Mouse hover on New Meeting button",YesNo.Yes);
					} else {
						sa.assertTrue(false,"Tab Icon Should not Present After Mouse hover on New Meeting button");
						log(LogStatus.SKIP,"Tab Icon Should not Present After Mouse hover on New Meeting button",YesNo.Yes);

					}
					
					
				} else {
					sa.assertTrue(false,"Not Able to Mouse hover on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting);
					log(LogStatus.SKIP,"Not Able to Mouse hover on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);

				}
				
				ThreadSleep(1000);
				clickUsingJavaScript(driver, lp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);

				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Meeting, 10);

				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Meeting.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);
					ThreadSleep(2000);
			    	//					scn.nextLine();
					ele=cp.getmeetingTypeDropdown(projectName, 20);
					if (ele!=null) {
						log(LogStatus.INFO, "Meeting Type Label is Present",YesNo.No);	
					} else {
						sa.assertTrue(false,"Meeting Type Label Should be Present");
						log(LogStatus.ERROR, "Meeting Type Label Should be Present",YesNo.Yes);

					}

					List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),false, action.SCROLLANDBOOLEAN, 15);
					if (!eleList.isEmpty() && eleList.size()==1) {
						log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Name.toString(),YesNo.No);	
					} else {
						sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
						log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

					}


					ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),false, contactName, action.SCROLLANDBOOLEAN, 15);
					if (ele!=null) {
						log(LogStatus.INFO, contactName+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	

						if (clickUsingJavaScript(driver, ele, "Cross Button For "+contactName, action.BOOLEAN)) {
							log(LogStatus.INFO,"Clicked on Cross Button For "+contactName,YesNo.Yes);
							ThreadSleep(2000);
						}
						else {
							sa.assertTrue(false,"Not Able to Click on Cross Button For "+contactName);
							log(LogStatus.SKIP,"Not Able to Click on Cross Button For "+contactName,YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,contactName+" not Found For Label "+PageLabel.Name.toString());
						log(LogStatus.ERROR, contactName+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

					}

					eleList = cp.getAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(),false, action.SCROLLANDBOOLEAN, 1);
					if (eleList.isEmpty()) {
						log(LogStatus.INFO, "Label field is blank for "+PageLabel.Related_Associations.toString(),YesNo.No);	
					} else {
						sa.assertTrue(false,"Label field sholud be blank for "+PageLabel.Related_Associations.toString());
						log(LogStatus.ERROR, "Label field sholud be blank for "+PageLabel.Related_Associations.toString(),YesNo.Yes);

					}

					// New Contact Button For Name Label
					ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.Object2Page, PageLabel.Name.toString(), action.SCROLLANDBOOLEAN,15);
					if (clickUsingJavaScript(driver, ele, "Name Text Label", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Enter Value to Name Text Box",YesNo.Yes);	
						ThreadSleep(1000);
						ele = cp.getNewButtonElementFromTask(projectName, PageName.Object2Page, PlusNewButton.ContactNewButton, PageLabel.Name.toString(), action.SCROLLANDBOOLEAN, 15);
						if (clickUsingJavaScript(driver, ele, "New Contact Button for Label : "+PageLabel.Name.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Clicked on New Contact Button for "+PageLabel.Name.toString(),YesNo.No);	
							ThreadSleep(1000);
							parentID = switchOnWindow(driver);
							if (parentID!=null) {
								log(LogStatus.INFO,"New window is open Going to verify New Contact PopUP Landing Page ",YesNo.Yes);
								ThreadSleep(1000);
								//click next button if present(record type), if not then check header
								click(driver, cp.getContinueOrNextButton(projectName, 5), "next button", action.SCROLLANDBOOLEAN);

								ele = cp.getHeaderTextForPage(projectName, PageName.Object1PagePopup, cp.getNewButtonFromTask(projectName, PlusNewButton.ContactNewButton), action.BOOLEAN, 10);
								if (ele!=null) {
									log(LogStatus.INFO,"New Contact PopUp is opened",YesNo.No);	
								} else {
									sa.assertTrue(false,"New Contact PopUp is not opened");
									log(LogStatus.SKIP,"New Contact PopUp is not opened",YesNo.Yes);
								}
								driver.close();
								driver.switchTo().window(parentID);
							} else {
								sa.assertTrue(false,"No new window is open so cannot verify New Contact PopUP Landing Page");
								log(LogStatus.SKIP,"No new window is open so cannot verify New Contact PopUP Landing Page",YesNo.Yes);
							}
						} else {
							sa.assertTrue(false,"Not Able to Click on New Contact Button for "+PageLabel.Name.toString());
							log(LogStatus.SKIP,"Not Able to Click on New Contact Button for "+PageLabel.Name.toString(),YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"Not Able to Enter Value to Name Text Box");
						log(LogStatus.SKIP,"Not Able to Enter Value to Name Text Box",YesNo.Yes);	
					}



					// New Account Button For Related Associations Label 
					switchToDefaultContent(driver);
					ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN,15);
					if (clickUsingJavaScript(driver, ele, "Related Associations Text Label", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Enter Value to Related Associations Text Box",YesNo.Yes);	
						ThreadSleep(1000);

						ele = cp.getNewButtonElementFromTask(projectName, PageName.Object2Page, PlusNewButton.EntityOrAccountNewButton, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 15);
						if (clickUsingJavaScript(driver, ele, "New Entity/Account Button for Label : "+PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Clicked on New Entity/Account Button for "+PageLabel.Related_Associations.toString(),YesNo.No);	
							ThreadSleep(1000);
							parentID = switchOnWindow(driver);
							if (parentID!=null) {
								log(LogStatus.INFO,"New window is open Going to verify New Entity/Account PopUP Landing Page ",YesNo.Yes);
								ThreadSleep(1000);
								//click next button if present(record type), if not then check header
								click(driver, cp.getContinueOrNextButton(projectName, 5), "next button", action.SCROLLANDBOOLEAN);

								ele = cp.getHeaderTextForPage(projectName, PageName.Object1PagePopup, cp.getNewButtonFromTask(projectName, PlusNewButton.EntityOrAccountNewButton), action.BOOLEAN, 10);
								if (ele!=null) {
									log(LogStatus.INFO,"New Entity/Account PopUp is opened",YesNo.No);	
								} else {
									sa.assertTrue(false,"New Entity/Account PopUp is not opened");
									log(LogStatus.SKIP,"New Entity/Account PopUp is not opened",YesNo.Yes);
								}
								driver.close();
								driver.switchTo().window(parentID);
							} else {
								sa.assertTrue(false,"No new window is open so cannot verify New Entity/Account PopUP Landing Page");
								log(LogStatus.SKIP,"No new window is open so cannot verify New Entity/Account PopUP Landing Page",YesNo.Yes);
							}
						} else {
							sa.assertTrue(false,"Not Able to Click on New Entity/Account Button for "+PageLabel.Related_Associations.toString());
							log(LogStatus.SKIP,"Not Able to Click on New Entity/Account Button for "+PageLabel.Related_Associations.toString(),YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"Not Able to Enter Value to Related Associations Text Box");
						log(LogStatus.SKIP,"Not Able to Enter Value to Related Associations Text Box",YesNo.Yes);	
					}


					// New Test Custom Object Button For Related Associations Label 
					switchToDefaultContent(driver);
					if (cp.SelectRelatedAssociationsdropdownButton(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, action.SCROLLANDBOOLEAN, 20)) {
						log(LogStatus.SKIP,"Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
						ThreadSleep(2000);	

						ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN,15);
						if (clickUsingJavaScript(driver, ele, "Related Associations Text Label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Enter Value to Related Associations Text Box",YesNo.Yes);	
							ThreadSleep(1000);
							ele = cp.getNewButtonElementFromTask(projectName, PageName.Object2Page, PlusNewButton.TestCustomObjectNewButton, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 15);
							if (clickUsingJavaScript(driver, ele, "New Test Object Button for Label : "+PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Clicked on New Test Object Button for "+PageLabel.Related_Associations.toString(),YesNo.No);	
								ThreadSleep(1000);
								parentID = switchOnWindow(driver);
								if (parentID!=null) {
									log(LogStatus.INFO,"New window is open Going to verify Test Object  PopUP Landing Page ",YesNo.Yes);
									ThreadSleep(1000);
									click(driver, cp.getContinueOrNextButton(projectName, 5), "next button", action.SCROLLANDBOOLEAN);

									ele = cp.getHeaderTextForPage(projectName, PageName.Object1PagePopup, cp.getNewButtonFromTask(projectName, PlusNewButton.TestCustomObjectNewButton), action.BOOLEAN, 10);
									if (ele!=null) {
										log(LogStatus.INFO,"New Test Object PopUp is opened",YesNo.No);	
									} else {
										sa.assertTrue(false,"New Test Object PopUp is not opened");
										log(LogStatus.SKIP,"New Test Object PopUp is not opened",YesNo.Yes);
									}
									driver.close();
									driver.switchTo().window(parentID);
								} else {
									sa.assertTrue(false,"No new window is open so cannot verify New Test Object PopUP Landing Page");
									log(LogStatus.SKIP,"No new window is open so cannot verify New Test Object PopUP Landing Page",YesNo.Yes);
								}
							} else {
								sa.assertTrue(false,"Not Able to Click on New Test Object Button for "+PageLabel.Related_Associations.toString());
								log(LogStatus.SKIP,"Not Able to Click on New Test Object Button for "+PageLabel.Related_Associations.toString(),YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"Not Able to Enter Value to Related Associations Text Box");
							log(LogStatus.SKIP,"Not Able to Enter Value to Related Associations Text Box",YesNo.Yes);	
						}

					} else {
						sa.assertTrue(false,"Not Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations);
						log(LogStatus.SKIP,"Not Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
					}


					//  

					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_Meeting2Subject, "Subject", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
						ThreadSleep(1000);
						if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), tomorrowsDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
							ThreadSleep(1000);
							ele=cp.geDropdownOnTaskPopUp(projectName, PageLabel.Priority.toString(), action.SCROLLANDBOOLEAN, 10);
							if (click(driver, ele, "Drop Down : "+PageLabel.Priority.toString(),action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "Not Able to Click on "+PageLabel.Priority.toString()+" Drop Down", YesNo.Yes);	
								ThreadSleep(2000);

								if (cp.SelectDropDownValue(projectName, PageName.Object2Page, PageLabel.Priority.toString(), Smoke_Meeting2Priority, action.SCROLLANDBOOLEAN, 10)) {
									log(LogStatus.ERROR, "Selected : "+Smoke_Meeting2Priority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);	
									ThreadSleep(1000);

								} else {
									log(LogStatus.ERROR, "Not ABle to Select : "+Smoke_Meeting2Priority+" For Label : "+PageLabel.Priority.toString(), YesNo.Yes);
									sa.assertTrue(false,"Not ABle to Select : "+Smoke_Meeting2Priority+" For Label : "+PageLabel.Priority.toString() );
								}


								boolean flag = cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, contactName, action.SCROLLANDBOOLEAN, 10);		
								if (flag) {
									log(LogStatus.SKIP,"Selected "+contactName+" For Label "+PageLabel.Name,YesNo.No);

								} else {
									sa.assertTrue(false,"Not Able to Select "+contactName+" For Label "+PageLabel.Name);
									log(LogStatus.SKIP,"Not Able to Select "+contactName+" For Label "+PageLabel.Name,YesNo.Yes);

								}

								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
									ExcelUtils.writeData(tomorrowsDate, "Task", excelLabel.Variable_Name, "AAMeetingTask2", excelLabel.Due_Date);


									ThreadSleep(2000);

									ele = cp.getCreatedConfirmationMsg(projectName, 15);
									if (ele!=null) {
										String actualValue = ele.getText().trim();
										String expectedValue=tp.taskCreatesMsg(projectName, Smoke_Meeting2Subject);
										if (actualValue.contains(expectedValue)) {
											log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

										} else {
											log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
											BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
										}
									} else {
										sa.assertTrue(false,"Created Task Msg Ele not Found");
										log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

									}

									ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_Meeting2Subject, action.BOOLEAN, 10);

									if (clickUsingJavaScript(driver, ele, Smoke_Meeting2Subject, action.BOOLEAN)) {
										log(LogStatus.ERROR, "Clicked on  : "+Smoke_Meeting2Subject, YesNo.Yes);
										ThreadSleep(2000);
										String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AAMeetingTask2", excelLabel.Due_Date);
										refresh(driver);
										ThreadSleep(5000);
										String[][] fieldsWithValues= {{PageLabel.Subject.toString(),Smoke_Meeting2Subject},
												{PageLabel.Name.toString(),contactName},
												{PageLabel.Due_Date.toString(),date},
												{PageLabel.Priority.toString(),Smoke_Meeting2Priority},
												{PageLabel.Related_Associations.toString(),"Assign Multiple Associations"},
												};

										tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
										if (tp.isCommentsEmpty(projectName,10)) {
											log(LogStatus.INFO, "successfully verified empty comments field", YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "could not verify empty comments field", YesNo.No);
											sa.assertTrue(false, "could not verify empty comments field");
										}


									} else {
										log(LogStatus.ERROR, "Not Able to Click : "+Smoke_Meeting2Subject, YesNo.Yes);
										sa.assertTrue(false,"Not Able to Click : "+Smoke_Meeting2Subject );
									}
								}
								else {
									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not created" );
								}

							} else {
								log(LogStatus.ERROR, "Click on "+PageLabel.Priority.toString()+" Drop Down", YesNo.Yes);
								sa.assertTrue(false,"Click on "+PageLabel.Priority.toString()+" Drop Down" );
							}
						}else {
							log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
						}
					}
					else {
						log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
					}


				} else {
					sa.assertTrue(false,"Not Able to Click on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting);
					log(LogStatus.SKIP,"Not Able to Click on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact Not Found : "+contactName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}


		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc044_VerifyNewMeetingButtonAtPackageObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_MTContact3FName+" "+Smoke_MTContact3LName;
		WebElement ele ;
		boolean flag=false;


		if (cp.clickOnTab(projectName, TabName.Object3Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object3Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object3Tab, Smoke_MTFund1Name, 20)) {
				log(LogStatus.INFO,"Clicked on Fund/Deal : "+Smoke_MTFund1Name,YesNo.No);
				ThreadSleep(1000);


				ele = lp.getActivityTimeLineItem(projectName, PageName.Object3Page, ActivityTimeLineItem.New_Meeting, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Meeting.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);
					ThreadSleep(2000);	
					//					scn.nextLine();
					ele = cp.getHeaderTextForPage(projectName, PageName.Object2Page,PageLabel.New_Task.toString(), action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
					} else {
						sa.assertTrue(false,"New Task PopUp is not opened");
						log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
					}

					ele = cp.getRelatedAssciationDefaultSelectedDropDown(projectName, TabName.Object1Tab, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 20);
					if (ele!=null) {
						log(LogStatus.INFO, "Default Selected For "+PageLabel.Related_Associations.toString()+" Verified",YesNo.No);	


					} else {
						sa.assertTrue(false,"Default Selected For "+PageLabel.Related_Associations.toString()+" Not Verified");
						log(LogStatus.ERROR, "Default Selected For "+PageLabel.Related_Associations.toString()+" Not Verified",YesNo.Yes);

					}

					List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(),false, action.SCROLLANDBOOLEAN, 15);
					if (!eleList.isEmpty() && eleList.size()==1) {
						log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Related_Associations.toString(),YesNo.No);	

						ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(),false, Smoke_MTFund1Name, action.SCROLLANDBOOLEAN, 15);
						if (ele!=null) {
							log(LogStatus.INFO, Smoke_MTFund1Name+" is prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.No);	


						} else {
							sa.assertTrue(false,Smoke_MTFund1Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString());
							log(LogStatus.ERROR, Smoke_MTFund1Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.Yes);

						}

					} else {
						sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
						log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

					}


					flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, contactName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+contactName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+contactName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+contactName+" For Label "+PageLabel.Name,YesNo.Yes);

					}


					// Subject

					if (sendKeys(driver, cp.getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_FinalDiscussionTaskSubject, "Subject", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
						ThreadSleep(1000);

						//  Priority

						ele=cp.getDropdownOnTaskPopUp(projectName, PageName.Object2Page,PageLabel.Meeting_Type.toString(), action.SCROLLANDBOOLEAN, 10);
						if (click(driver, ele, "Drop Down : "+PageLabel.Meeting_Type.toString(),action.SCROLLANDBOOLEAN)) {
							log(LogStatus.ERROR, "Select/Click on "+PageLabel.Meeting_Type.toString()+" Drop Down", YesNo.Yes);	
							ThreadSleep(2000);

							if (cp.SelectDropDownValue(projectName, PageName.Object2Page, PageLabel.Meeting_Type.toString(), Smoke_FinalDiscussionTaskMeetingType, action.SCROLLANDBOOLEAN, 10)) {
								log(LogStatus.ERROR, "Selected : "+Smoke_FinalDiscussionTaskMeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.Yes);	
								ThreadSleep(1000);




								if (clickUsingJavaScript(driver, cp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);


									ele = cp.getCreatedConfirmationMsg(projectName, 15);
									if (ele!=null) {
										String actualValue = ele.getText().trim();
										String expectedValue=tp.taskCreatesMsg(projectName, Smoke_FinalDiscussionTaskSubject);
										if (actualValue.contains(expectedValue)) {
											log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

										} else {
											log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
											BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
										}
									} else {
										sa.assertTrue(false,"Created Task Msg Ele not Found");
										log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

									}

									clickUsingJavaScript(driver, cp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);

									ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
									if (click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN)) {
										log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Expand_All,YesNo.No);	


									} else {
										sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Expand_All);
										log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Expand_All,YesNo.Yes);

									} 

									ThreadSleep(2000);
									//					scn.nextLine();
									ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, Smoke_FinalDiscussionTaskSubject, SubjectElement.SubjectLink, 10);
									String taskMessage = BasePageErrorMessage.UpcomingTaskMsg+" "+contactName;				

									if (ele!=null) {
										log(LogStatus.INFO,Smoke_FinalDiscussionTaskSubject+" link present on Activity Timeline For : "+Smoke_MTFund1Name,YesNo.No);	
										lp.verifyActivityAtNextStep2(projectName, PageName.Object3Page, Smoke_MTFund1Name,Smoke_FinalDiscussionTaskSubject, taskMessage, DueDate.No_due_date.toString(), true, Smoke_FinalDiscussionTaskMeetingType, true, "", 10);
									} else {
										sa.assertTrue(false,Smoke_FinalDiscussionTaskSubject+" link Should be present on Activity Timeline For : "+Smoke_MTFund1Name);
										log(LogStatus.SKIP,Smoke_FinalDiscussionTaskSubject+" link Should be present on Activity Timeline For : "+Smoke_MTFund1Name,YesNo.Yes);
									}
									

								}
								else {
									log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
									sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
								}



							} else {
								log(LogStatus.ERROR, "Not ABle to Select : "+Smoke_FinalDiscussionTaskMeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.Yes);
								sa.assertTrue(false,"Not ABle to Select : "+Smoke_FinalDiscussionTaskMeetingType+" For Label : "+PageLabel.Meeting_Type.toString() );
							}

						} else {
							log(LogStatus.ERROR, "Click on "+PageLabel.Meeting_Type.toString()+" Drop Down", YesNo.Yes);
							sa.assertTrue(false,"Click on "+PageLabel.Meeting_Type.toString()+" Drop Down" );
						}
					}else {
						log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
					}


				} else {
					sa.assertTrue(false,"Not Able to Click on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting);
					log(LogStatus.SKIP,"Not Able to Click on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);
				}



			} else {
				sa.assertTrue(false,"Fund/Deal Not Found : "+Smoke_MTFund1Name);
				log(LogStatus.SKIP,"Fund/Deal  Not Found : "+Smoke_MTFund1Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc045_VerifyNewMeetingAtTestCustomObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_MTContact3FName+" "+Smoke_MTContact3LName;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		WebElement ele ;
		boolean flag=false;

		if (tp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.TestCustomObjectTab,YesNo.No);
			ThreadSleep(1000);
			if (tp.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, meetingCustomObj2Name, 20)) {
				log(LogStatus.INFO,"Clicked on Test Custom Object : "+meetingCustomObj2Name,YesNo.No);
				ThreadSleep(1000);
				//					scn.nextLine();
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Meeting, 10);

				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Meeting.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);
					ThreadSleep(2000);	
					//					scn.nextLine();
					// PopUp Verification 
					ele = tp.getHeaderTextForPage(projectName, PageName.TestCustomObjectPage,PageLabel.New_Task.toString(), action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
						String value = ele.getText().trim();
						System.err.println("VALUE : "+value);
						System.err.println(PageLabel.New_Task.toString());
						if (value.contains(PageLabel.New_Task.toString().replace("_", " "))) {
							log(LogStatus.INFO,"New Task Text is Verified",YesNo.No);		
						} else {
							sa.assertTrue(false,"New Task Text is not Verified");
							log(LogStatus.SKIP,"New Task Text is not Verified",YesNo.Yes);
						}
						System.err.println("Value : "+value);

					} else {
						sa.assertTrue(false,"New Task PopUp is not opened");
						log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
					}

					// Related_Associations Deafult Verification
					ele = tp.getRelatedAssciationDefaultSelectedDropDown(projectName, TabName.Object1Tab, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 20);
					if (ele!=null) {
						log(LogStatus.INFO, "Default Selected : "+tp.getTabName(projectName, TabName.TestCustomObjectTab)+" fOR "+PageLabel.Related_Associations.toString()+" Verified",YesNo.No);	


					} else {
						sa.assertTrue(false,"Default Selected : "+tp.getTabName(projectName, TabName.TestCustomObjectTab)+" fOR "+PageLabel.Related_Associations.toString()+" Not Verified");
						log(LogStatus.ERROR, "Default Selected : "+tp.getTabName(projectName, TabName.TestCustomObjectTab)+" fOR "+PageLabel.Related_Associations.toString()+" Not Verified",YesNo.Yes);

					}

					// Related_Associations Deafult Selected Item Verification
					List<WebElement> eleList = tp.getAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(),false, action.SCROLLANDBOOLEAN, 15);
					if (!eleList.isEmpty() && eleList.size()==1) {
						log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Related_Associations.toString(),YesNo.No);	

						ele=tp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(),false, meetingCustomObj2Name, action.SCROLLANDBOOLEAN, 15);
						if (ele!=null) {
							log(LogStatus.INFO, meetingCustomObj2Name+" is prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.No);	


						} else {
							sa.assertTrue(false,meetingCustomObj2Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString());
							log(LogStatus.ERROR, meetingCustomObj2Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.Yes);

						}

					} else {
						sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
						log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

					}


					// Assigned_To Cross Button Click
					if (tp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(),false, owner, action.SCROLLANDBOOLEAN, 15)) {
						log(LogStatus.INFO, "Clicked on Cross Button against : "+owner+" For Label : "+PageLabel.Assigned_To.toString(),YesNo.No);	

						ThreadSleep(2000);
						// Assigned To
						owner = AdminUserFirstName+" "+AdminUserLastName;
						flag=tp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(), TabName.TestCustomObjectTab, owner, action.BOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+owner+" For  Drown Down Value For Label "+PageLabel.Assigned_To,YesNo.No);
							ThreadSleep(1000);

							// Name
							flag=tp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(), TabName.Object2Tab, contactName, action.BOOLEAN, 10);		
							if (flag) {
								log(LogStatus.INFO,"Selected "+contactName+" For Label "+PageLabel.Name,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+contactName+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"Not Able to Select "+contactName+" For Label "+PageLabel.Name,YesNo.Yes);

							}


							// Subject

							String[][] dropDownLabelWithValues = {{PageLabel.Meeting_Type.toString(),Smoke_DealEvalutionMeetingType}};
							if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.TestCustomObjectPage, Smoke_DealEvalutionMeetingSubject, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {
								log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
								ThreadSleep(1000);


								//	 
								if (clickUsingJavaScript(driver, tp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);


									ele = tp.getCreatedConfirmationMsg(projectName, 15);
									if (ele!=null) {
										String actualValue = ele.getText().trim();
										String expectedValue=tp.taskCreatesMsg(projectName, Smoke_DealEvalutionMeetingSubject);
										if (actualValue.contains(expectedValue)) {
											log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

										} else {
											log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
											BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
										}
									} else {
										sa.assertTrue(false,"Created Task Msg Ele not Found");
										log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

									}

									clickUsingJavaScript(driver, tp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);

									
									ele = tp.getActivityTimeLineItem2(projectName, PageName.TestCustomObjectPage, ActivityTimeLineItem.Expand_All, 10);
									if (click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN)) {
										log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Expand_All,YesNo.No);	


									} else {
										sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Expand_All);
										log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Expand_All,YesNo.Yes);

									}
									
									//					scn.nextLine();
									
									ele = tp.getElementForActivityTimeLineTask(projectName, PageName.TestCustomObjectPage,ActivityType.Next, Smoke_DealEvalutionMeetingSubject, SubjectElement.SubjectLink, 10);
									String taskMessage = owner+" "+BasePageErrorMessage.UpcomingTaskMsg2+" "+contactName;				

									if (ele!=null) {
										log(LogStatus.INFO,Smoke_DealEvalutionMeetingSubject+" link present on Activity Timeline For : "+meetingCustomObj2Name,YesNo.No);	
										lp.verifyActivityAtNextStep2(projectName, PageName.TestCustomObjectPage, meetingCustomObj2Name,Smoke_DealEvalutionMeetingSubject, taskMessage, DueDate.No_due_date.toString(), true, Smoke_DealEvalutionMeetingType, true, "", 10);
									} else {
										sa.assertTrue(false,Smoke_DealEvalutionMeetingSubject+" link Should be present on Activity Timeline For : "+meetingCustomObj2Name);
										log(LogStatus.SKIP,Smoke_DealEvalutionMeetingSubject+" link Should be present on Activity Timeline For : "+meetingCustomObj2Name,YesNo.Yes);
									}



								}
								else {
									log(LogStatus.ERROR, "Save Button is not visible so task could not be created", YesNo.Yes);
									sa.assertTrue(false,"Save Button is not visible so task could not be created" );
								}


							}else {
								log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
								sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
							}



						} else {
							sa.assertTrue(false,"Not Able to Select "+owner+" For Drown Down Value For Label "+PageLabel.Assigned_To);
							log(LogStatus.SKIP,"Not Able to Select "+owner+" For  Drown Down Value For Label "+PageLabel.Assigned_To,YesNo.Yes);

						}

					} else {
						sa.assertTrue(false,"Not Able to Click on Cross Button against : "+owner+" For Label : "+PageLabel.Assigned_To.toString());
						log(LogStatus.ERROR, "Not Able to Click on Cross Button against : "+owner+" For Label : "+PageLabel.Assigned_To.toString(),YesNo.Yes);

					}





				} else {
					sa.assertTrue(false,"Not Able to Click on Activity Timeline : "+ActivityTimeLineItem.New_Meeting);
					log(LogStatus.SKIP,"Not Able to Click on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);
				}



			} else {
				sa.assertTrue(false,"Test Custom Object Not Found : "+meetingCustomObj2Name);
				log(LogStatus.SKIP,"Test Custom Object  Not Found : "+meetingCustomObj2Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc046_VerifyNewMeetingFromEntityDetailPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele ;
		boolean flag=false;
		String dateValue = tomorrowsDate;
		String objectValue = Smoke_MTINS2Name;
		if (tp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (tp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, objectValue, 20)) {
				log(LogStatus.INFO,"Clicked on  : "+objectValue,YesNo.No);
				ThreadSleep(1000);
				//					scn.nextLine();
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Meeting, 10);
				
				ThreadSleep(2000);
				if (mouseOverOperation(driver, ele)) {
					log(LogStatus.INFO,"Mouse hover on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);
					ThreadSleep(2000);
					
					ele = lp.getNewMeetingNewTabIconAfterMouseHover(projectName, 15);
					if (ele==null) {
						log(LogStatus.INFO,"Tab Icon is not Present After Mouse hover on New Meeting button",YesNo.Yes);
					} else {
						sa.assertTrue(false,"Tab Icon Should not Present After Mouse hover on New Meeting button");
						log(LogStatus.SKIP,"Tab Icon Should not Present After Mouse hover on New Meeting button",YesNo.Yes);

					}
					
					
				} else {
					sa.assertTrue(false,"Not Able to Mouse hover on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting);
					log(LogStatus.SKIP,"Not Able to Mouse hover on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);

				}
				ThreadSleep(1000);
				clickUsingJavaScript(driver, lp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Meeting, 10);
				
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Meeting.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);
					ThreadSleep(2000);
					//					scn.nextLine();

					// PopUp Verification 
					ele = tp.getHeaderTextForPage(projectName, PageName.Object1Page,PageLabel.New_Task.toString(), action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
						String value = ele.getText().trim();
						System.err.println("VALUE : "+value);
						System.err.println(PageLabel.New_Task.toString());
						if (value.contains(PageLabel.New_Task.toString().replace("_", " "))) {
							log(LogStatus.INFO,"New Task Text is Verified",YesNo.No);		
						} else {
							sa.assertTrue(false,"New Task Text is not Verified");
							log(LogStatus.SKIP,"New Task Text is not Verified",YesNo.Yes);
						}
						System.err.println("Value : "+value);

					} else {
						sa.assertTrue(false,"New Task PopUp is not opened");
						log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
					}

					// Related_Associations Deafult Verification
					
					ele = tp.getRelatedAssciationDefaultSelectedDropDown(projectName, TabName.Object1Tab, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 20);
					if (ele!=null) {
						log(LogStatus.INFO, "Default Selected : "+tp.getTabName(projectName, TabName.Object1Tab)+" fOR "+PageLabel.Related_Associations.toString()+" Verified",YesNo.No);	


					} else {
						sa.assertTrue(false,"Default Selected : "+tp.getTabName(projectName, TabName.Object1Tab)+" fOR "+PageLabel.Related_Associations.toString()+" Not Verified");
						log(LogStatus.ERROR, "Default Selected : "+tp.getTabName(projectName, TabName.Object1Tab)+" fOR "+PageLabel.Related_Associations.toString()+" Not Verified",YesNo.Yes);

					}

					// Related_Associations Deafult Selected Item Verification
					
					List<WebElement> eleList = tp.getAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(),false, action.SCROLLANDBOOLEAN, 15);
					if (!eleList.isEmpty() && eleList.size()==1) {
						log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Related_Associations.toString(),YesNo.No);	

						ele=tp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(),false, objectValue, action.SCROLLANDBOOLEAN, 15);
						if (ele!=null) {
							log(LogStatus.INFO, objectValue+" is prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.No);	


						} else {
							sa.assertTrue(false,objectValue+" is not prefilled for Label "+PageLabel.Related_Associations.toString());
							log(LogStatus.ERROR, objectValue+" is not prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.Yes);

						}

					} else {
						sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
						log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

					}


					if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), dateValue, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
						ThreadSleep(1000);
					}else {
						log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
					}

					// Smoke_EntityMeeting1Subject

					String[][] dropDownLabelWithValues = {{PageLabel.Meeting_Type.toString(),Smoke_EntityMeeting1Type},
							{PageLabel.Priority.toString(),Smoke_EntityMeeting1Priority}};
					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.Object1Page, Smoke_EntityMeeting1Subject, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {
						log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
						ThreadSleep(1000);
						//	 
						ThreadSleep(2000);
						if (clickUsingJavaScript(driver, tp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
							ExcelUtils.writeData(dateValue, "Task", excelLabel.Variable_Name, "AATask29", excelLabel.Due_Date);


							ele = tp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, Smoke_EntityMeeting1Subject);
								if (actualValue.contains(expectedValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}

							clickUsingJavaScript(driver, tp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);

							
							ele = tp.getActivityTimeLineItem2(projectName, PageName.Object1Page, ActivityTimeLineItem.Refresh, 10);
							if (click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN)) {
								log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Refresh,YesNo.No);	


							} else {
								sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Refresh);
								log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Refresh,YesNo.Yes);

							}
							
							ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
							click(driver, ele, "More Steps" ,action.BOOLEAN);
							
							ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
							click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
						
							

							tp.verifyActivityAtNextStep2(projectName, PageName.Object1Page, objectValue,Smoke_EntityMeeting1Subject, BasePageErrorMessage.UpcomingTaskMsg3, dateValue, true, Smoke_EntityMeeting1Type, true, "", 10);

						}
						else {
							log(LogStatus.ERROR, "Save Button is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"Save Button is not visible so task could not be created" );
						}


					}else {
						log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
					}




					// Smoke_EntityMeeting2Subject
					//					scn.nextLine();
					String[][] dropDownLabelWithValues1 = {{PageLabel.Meeting_Type.toString(),Smoke_EntityMeeting2Type},
							{PageLabel.Status.toString(),Smoke_EntityMeeting2Status}};
					
					ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Meeting, 10);
					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Meeting.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Clicked on New Meeting button on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);
						ThreadSleep(2000);
						//					scn.nextLine();

						if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), dateValue, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
							ThreadSleep(1000);
						}else {
							log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
						}
						
						if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.Object1Page, Smoke_EntityMeeting2Subject, dropDownLabelWithValues1, action.SCROLLANDBOOLEAN, 10)) {
							log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
							ThreadSleep(1000);

							
							//	 
							ThreadSleep(2000);
							if (clickUsingJavaScript(driver, tp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
								ExcelUtils.writeData(dateValue, "Task", excelLabel.Variable_Name, "AATask30", excelLabel.Due_Date);


								ele = tp.getCreatedConfirmationMsg(projectName, 15);
								if (ele!=null) {
									String actualValue = ele.getText().trim();
									String expectedValue=tp.taskCreatesMsg(projectName, Smoke_EntityMeeting2Subject);
									if (actualValue.contains(expectedValue)) {
										log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

									} else {
										log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
										BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
									}
								} else {
									sa.assertTrue(false,"Created Task Msg Ele not Found");
									log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

								}

								ele = tp.getActivityTimeLineItem2(projectName, PageName.Object1Page, ActivityTimeLineItem.Refresh, 10);
								if (click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN)) {
									log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Refresh,YesNo.No);	


								} else {
									sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Refresh);
									log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Refresh,YesNo.Yes);

								}
							//	tp.verifyActivityAtPastStep(projectName, PageName.Object1Page, Smoke_EntityMeeting2Subject,BasePageErrorMessage.PastTask, DueDate.Tomorrow, Smoke_EntityMeeting2Type, "", 10);

								ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
								click(driver, ele, "More Steps" ,action.BOOLEAN);
								
								ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
								click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
								
							
								
								tp.verifyActivityAtPastStep2(projectName, PageName.Object1Page,objectValue, Smoke_EntityMeeting2Subject,BasePageErrorMessage.PastTask, dateValue, true,Smoke_EntityMeeting2Type, true,"", 10);


							}
							else {
								log(LogStatus.ERROR, "Save Button is not visible so task could not be created", YesNo.Yes);
								sa.assertTrue(false,"Save Button is not visible so task could not be created" );
							}


						}else {
							log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
						}
						
						} else {
						sa.assertTrue(false,"Not Able to Click on Activity Timeline : "+ActivityTimeLineItem.New_Meeting);
						log(LogStatus.SKIP,"Not Able to Click on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);
					}

					



				} else {
					sa.assertTrue(false,"Not Able to Click on Activity Timeline : "+ActivityTimeLineItem.New_Meeting);
					log(LogStatus.SKIP,"Not Able to Click on Activity Timeline : "+ActivityTimeLineItem.New_Meeting,YesNo.Yes);
				}



			} else {
				sa.assertTrue(false," Not Found : "+objectValue);
				log(LogStatus.SKIP,"  Not Found : "+objectValue,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		ThreadSleep(20000);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc047_ScenariosRelatedWithCompletingTaskStatus(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName = Smoke_MTContact3FName+" "+Smoke_MTContact3LName;
		WebElement ele ;
		String parentID=null;
		String owner = AdminUserFirstName+" "+AdminUserLastName;
		
		String crmUserName = crmUser1FirstName+" "+crmUser1LastName;
		String adminUserName = AdminUserFirstName+" "+AdminUserLastName;

		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(10000);
		
				
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				click(driver, ele, "More Steps" ,action.BOOLEAN);
				
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
				click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
				
				//					scn.nextLine();
				
				String taskMessage = owner+" "+BasePageErrorMessage.UpcomingTaskMsg4+" "+meetingCustomObj2Name;	
				tp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, contactName,Smoke_DealEvalutionMeetingSubject, taskMessage, DueDate.No_due_date.toString(), true, Smoke_DealEvalutionMeetingType, true, "", 10);

				taskMessage = BasePageErrorMessage.UpcomingTaskMsgAbout+" "+Smoke_MTFund1Name;	
				tp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, contactName,Smoke_FinalDiscussionTaskSubject, taskMessage, DueDate.No_due_date.toString(), true, Smoke_FinalDiscussionTaskMeetingType, true, "", 10);

	
				
				//					scn.nextLine();
				
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_FinalDiscussionTaskSubject, SubjectElement.CheckBox, 10);
				if (click(driver, ele, "Check Box : "+Smoke_FinalDiscussionTaskSubject, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO," Click on Check Box : "+Smoke_FinalDiscussionTaskSubject,YesNo.No);
					
					//					scn.nextLine();
					ThreadSleep(5000);
					
					ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_FinalDiscussionTaskSubject, SubjectElement.StrikedText, 10);
					if (ele!=null) {
						log(LogStatus.INFO,SubjectElement.StrikedText+" Element Found For "+Smoke_FinalDiscussionTaskSubject,YesNo.No);
						ThreadSleep(5000);
						
						String actualValue = ele.getAttribute("class");
						if (actualValue.contains(BasePageErrorMessage.StrikedText)) {
							log(LogStatus.INFO,Smoke_FinalDiscussionTaskSubject+" has been striked",YesNo.No);

						}else {
							sa.assertTrue(false,Smoke_FinalDiscussionTaskSubject+" has not been striked");
							log(LogStatus.SKIP,Smoke_FinalDiscussionTaskSubject+" has not been striked",YesNo.Yes);
						}
						
					} else {
						sa.assertTrue(false,SubjectElement.StrikedText+" Element Not Found For "+Smoke_FinalDiscussionTaskSubject);
						log(LogStatus.SKIP,SubjectElement.StrikedText+" Element Not  Found For "+Smoke_FinalDiscussionTaskSubject,YesNo.Yes);
					}
					
					
					ele = tp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Refresh, 10);
					if (click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Refresh,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Refresh);
						log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Refresh,YesNo.Yes);

					}
					

					ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
					click(driver, ele, "More Steps" ,action.BOOLEAN);
					
					ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
					click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
					
					//					scn.nextLine();
					
			
					//tp.verifyActivityAtPastStep(projectName, PageName.Object2Page, Smoke_FinalDiscussionTaskSubject,BasePageErrorMessage.UpcomingTaskMsg, DueDate.No_due_date, Smoke_FinalDiscussionTaskMeetingType, "", 10);
					taskMessage = BasePageErrorMessage.YouHadATaskAbout+" "+Smoke_MTFund1Name;	
					tp.verifyActivityAtPastStep2(projectName, PageName.Object2Page,contactName, Smoke_FinalDiscussionTaskSubject,taskMessage, DueDate.No_due_date.toString(),true, Smoke_FinalDiscussionTaskMeetingType, true,"", 10);

					ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_FinalDiscussionTaskSubject, SubjectElement.SubjectLink, 10);
					if (click(driver, ele, "Link : "+Smoke_FinalDiscussionTaskSubject, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO," Click on Link : "+Smoke_FinalDiscussionTaskSubject,YesNo.No);
						
						ThreadSleep(2000);
				
						
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),Smoke_FinalDiscussionTaskSubject},
													{PageLabel.Status.toString(),Status.Completed.toString()}};

						tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

						
					} else {
						sa.assertTrue(false,"Not Able to Click on Link : "+Smoke_FinalDiscussionTaskSubject);
						log(LogStatus.SKIP,"Not Able to Click on Link : "+Smoke_FinalDiscussionTaskSubject,YesNo.Yes);
					}
					
					
				} else {
					sa.assertTrue(false,"Not Able to Click on Check Box : "+Smoke_FinalDiscussionTaskSubject);
					log(LogStatus.SKIP,"Not Able to Click on Check Box : "+Smoke_FinalDiscussionTaskSubject,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact Not Found : "+contactName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}


		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc048_1_CreateStandardTaskAfterEnablingSetting_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contact4Name = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		String contact1Name = Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		String meetingCustomObject1=taskCustomObj1Name;
		String[] contacts = {contact4Name,contact1Name};
		boolean flag = false;
		String actualValue="";
		String expectedValue="";
		String date=todaysDate;
		ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask14", excelLabel.Due_Date);
		
		WebElement ele ; 
		if (cp.clickOnTab(projectName, TabName.TaskTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.TaskTab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnShowMoreActionDownArrow(projectName, PageName.TaskPage, ShowMoreActionDropDownList.New_Task, 15)) {
				log(LogStatus.INFO,"Clicked on New Task Button for show more action",YesNo.No);
				ThreadSleep(1000);

				ele = cp.getHeaderTextForPage(projectName, PageName.NewTaskPopUP,PageLabel.New_Task.toString(), action.BOOLEAN, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
				} else {
					sa.assertTrue(false,"New Task PopUp is not opened");
					log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
				}

				ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TaskPage, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN,5);

				if (ele==null) {
					log(LogStatus.INFO,"Related Association Field is not present",YesNo.No);	
				} else {
					sa.assertTrue(false,"Related Association Field should not be present");
					log(LogStatus.SKIP,"Related Association Field should not be present",YesNo.Yes);
				}

				// subject
				if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_STDTask2OnSubject, "Subject", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
					ThreadSleep(1000);

					// Due Date
					if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), todaysDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
						ThreadSleep(1000);

						// comment 
						if (sendKeys(driver, ip.getcommentsTextBox(projectName, 10), Smoke_STDTask2OnComment, "comments", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.ERROR, "entered value in Comment Text Area", YesNo.Yes);	
						}
						else {
							log(LogStatus.ERROR, "comments textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"comments textbox is not visible so task could not be created" );
						}

						for (String relatedContact : contacts) {

							flag = cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Name.toString(), TabName.TaskTab, relatedContact, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+relatedContact+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.TaskTab)+" For Label "+PageLabel.Name,YesNo.No);

								ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Name.toString(),true, relatedContact, action.SCROLLANDBOOLEAN, 5);
								if (ele!=null) {
									log(LogStatus.INFO, relatedContact+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	
								} else {
									sa.assertTrue(false,relatedContact+" not Found For Label "+PageLabel.Name.toString());
									log(LogStatus.ERROR, relatedContact+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

								}



							} else {
								sa.assertTrue(false,"Not Able to Select "+relatedContact+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"Not Able to Select "+relatedContact+" For Label "+PageLabel.Name,YesNo.Yes);

							}

						}


						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Related_To.toString(), TabName.TestCustomObjectTab, taskCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.SKIP,"Selected "+taskCustomObj1Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_To,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+taskCustomObj1Name+" For Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_To);
							log(LogStatus.SKIP,"Not Able to Select "+taskCustomObj1Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_To,YesNo.Yes);

						}

						 
						if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task : "+Smoke_STDTask2OnSubject,  YesNo.Yes);
							ThreadSleep(3000);

							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								actualValue = ele.getText().trim();
								expectedValue=tp.taskCreatesMsg(projectName, Smoke_STDTask2OnSubject);
								if (actualValue.contains(expectedValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : \n"+actualValue+" Expected : \n"+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : \n"+actualValue+" Expected : \n"+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}

							ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask14", excelLabel.Due_Date);
							ThreadSleep(2000);
							refresh(driver);
							ThreadSleep(5000);
							

						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created : "+Smoke_STDTask2OnSubject, YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created : "+Smoke_STDTask2OnSubject );
						}



					}else {
						log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
					}
				}
				else {
					log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
					sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on New Task Button for show more action");
				log(LogStatus.SKIP,"Not Able to Click on New Task Button for show more action",YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TaskTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TaskTab,YesNo.Yes);
		}

		 
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc048_2_VerifyCreateStandardTaskAfterEnablingSetting_Impact(String projectName) {

		String date=todaysDate;
		ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask14", excelLabel.Due_Date);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);WebElement ele ;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		
		String contact4Name = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		String contact1Name = Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		
		String[] contacts = {contact4Name,contact1Name};
		
		String task1 = Smoke_STDTask2OnSubject;
		String description = Smoke_STDTask2OnComment;
		String taskMessage="";
		date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask14", excelLabel.Due_Date);

		for (String contactName : contacts) {
			switchToDefaultContent(driver);
			if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
					log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
					ThreadSleep(1000);
						

					ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
					click(driver, ele, "More Steps" ,action.BOOLEAN);

					ThreadSleep(2000);

					ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
					click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
					
					ThreadSleep(5000);
					taskMessage=BasePageErrorMessage.UpcomingTaskMsg+" "+contact4Name+" and 1 other about "+taskCustomObj1Name;
					tp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, contactName,task1, taskMessage, date, false, "", true, description, 10);

				

				} else {
					sa.assertTrue(false,"Contact Not Found : "+contactName);
					log(LogStatus.SKIP,"Contact  Not Found : "+contactName,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
			}

			switchToDefaultContent(driver);
		}
		
		String meetingCustomObject1=taskCustomObj1Name;
		switchToDefaultContent(driver);
		if (cp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.TestCustomObjectTab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, meetingCustomObject1, 20)) {
				log(LogStatus.INFO,"Clicked on custom object: "+meetingCustomObject1,YesNo.No);
				ThreadSleep(1000);
				
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				click(driver, ele, "More Steps" ,action.BOOLEAN);

				ThreadSleep(2000);

				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
				click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
				
				ThreadSleep(5000);
				taskMessage=BasePageErrorMessage.UpcomingTaskMsg+" "+contact4Name+" and 1 other";
				tp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, meetingCustomObject1,task1, taskMessage, date, false, "", true, description, 10);
				
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task1, SubjectElement.SubjectLink, 10);
				if (clickUsingJavaScript(driver, ele, "Link : "+task1, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO," Click on Link : "+task1,YesNo.No);
					
					ThreadSleep(2000);
					ThreadSleep(1000);
					date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask14", excelLabel.Due_Date);


					///////////////////////////////////////////////////////////////
					
					String relatedContactValue= tp.Comment(projectName, PageLabel.Related_Contacts, contact1Name);
				//	String comment = relatedContactValue+"\n"+TaskPagePageErrorMessage.Dots+"\n"+Smoke_STDTask2OnComment;
					String comment = Smoke_STDTask2OnComment;

					String[][] fieldsWithValues= {
							{PageLabel.Subject.toString(),Smoke_STDTask2OnSubject},
							{PageLabel.Due_Date.toString(),date},
							{PageLabel.Related_To.toString(),taskCustomObj1Name},
							{PageLabel.Name.toString(),contact4Name+"\n + "+1},
							{PageLabel.Related_Contacts.toString(),contact1Name},
							{PageLabel.Comments.toString(),comment},
							{PageLabel.Related_Associations.toString(),"Assign Multiple Associations"}};

					tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);


					
				} else {
					sa.assertTrue(false,"Not Able to Click on Link : "+task1);
					log(LogStatus.SKIP,"Not Able to Click on Link : "+task1,YesNo.Yes);
				}

					
			} else {
				sa.assertTrue(false,"custom object Not Found : "+meetingCustomObject1);
				log(LogStatus.SKIP,"custom object  Not Found : "+meetingCustomObject1,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab,YesNo.Yes);
		}
		
		
		switchToDefaultContent(driver);
		 
		lp.CRMlogout();
		sa.assertAll();


	}
	
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc049_1_VerifyAssignMultipleAssociationLinkAfterEnablingSetting_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contact4Name = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		String contact1Name = Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		String contact3Name = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String[] contacts = {contact4Name,contact1Name};

		WebElement ele ;
		String parentID=null;
		String actualValue;
		boolean flag=false;

		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contact4Name, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contact4Name,YesNo.No);
				ThreadSleep(1000);

				ThreadSleep(2000);
				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				if (click(driver, ele, "More Steps" ,action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on More Steps",YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Click on More Steps");
					log(LogStatus.ERROR, "Not Able to Click on More Steps",YesNo.Yes);

				}
				ThreadSleep(2000);

				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_STDTask2OnSubject, SubjectElement.SubjectLink, 10);

				if (ele!=null) {
					log(LogStatus.INFO,Smoke_STDTask2OnSubject+" link present on Activity Timeline : ",YesNo.Yes);	

					if (clickUsingJavaScript(driver, ele, Smoke_STDTask2OnSubject, action.BOOLEAN)) {
						log(LogStatus.INFO,"Clicked on : "+Smoke_STDTask2OnSubject,YesNo.No);
						ThreadSleep(1000);
						//					scn.nextLine();
						ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, Smoke_STDTask2OnSubject, action.BOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO,"Landing Page Verified for : "+Smoke_STDTask2OnSubject,YesNo.No);	
						} else {
							sa.assertTrue(false,"Landing Page Not Verified for : "+Smoke_STDTask2OnSubject);
							log(LogStatus.SKIP,"Landing Page Not Verified for : "+Smoke_STDTask2OnSubject,YesNo.Yes);
						}
						switchToDefaultContent(driver);
						switchToFrame(driver, 60, tp.getTaskPageFrame(projectName,60));

						ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, TaskPagePageErrorMessage.RelatedAssociationText, action.BOOLEAN, 10);
						if (click(driver, ele, TaskPagePageErrorMessage.RelatedAssociationText, action.BOOLEAN)) {
							log(LogStatus.INFO,"Clicked on Link :  "+TaskPagePageErrorMessage.RelatedAssociationText,YesNo.No);	

							parentID=switchOnWindow(driver);

							if (parentID!=null) {
								log(LogStatus.INFO,"Switch To Edit Task Window",YesNo.No);	
								ThreadSleep(5000);

								ele = tp.getTaskPoUpEditHeader(projectName, 10);
								if (ele!=null) {
									log(LogStatus.INFO,"Edit Header Ele Found",YesNo.No);	
									actualValue=ele.getText().trim();
									String expectedValue=PageLabel.Edit.toString()+" "+Smoke_STDTask2OnSubject;
									// && actualValue.contains(Smoke_STDTask2OnSubject))
									if (actualValue.contains(PageLabel.Edit.toString())) {
										log(LogStatus.INFO,expectedValue+" matched msg for Edit PopUp", YesNo.No);

									} else {
										log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched msg for Edit PopUp", YesNo.Yes);
										BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched msg for Edit PopUp");
									}

								} else {
									sa.assertTrue(false,"Edit Header Ele Not Found");
									log(LogStatus.SKIP,"Edit Header Ele Not Found",YesNo.Yes);
								}
								System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..");



								List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),true, action.SCROLLANDBOOLEAN, 15);
								if (!eleList.isEmpty() && eleList.size()==2) {
									log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Name.toString(),YesNo.No);	
								} else {
									sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
									log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

								}

								for (String relatedContact : contacts) {

									ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),true, relatedContact, action.SCROLLANDBOOLEAN, 5);
									if (ele!=null) {
										log(LogStatus.INFO, relatedContact+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	
									} else {
										sa.assertTrue(false,relatedContact+" not Found For Label "+PageLabel.Name.toString());
										log(LogStatus.ERROR, relatedContact+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

									}
								}



								flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.Object1Tab, Smoke_TaskINS3Name, action.SCROLLANDBOOLEAN, 10);		
								if (flag) {
									log(LogStatus.INFO,"Selected "+Smoke_TaskINS3Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.Object1Tab)+" For Label "+PageLabel.Related_Associations,YesNo.No);

								} else {
									sa.assertTrue(false,"Not Able to Select "+Smoke_TaskINS3Name+" For Drown Down Value : "+cp.getTabName(projectName, TabName.Object1Tab)+" For Label "+PageLabel.Related_Associations);
									log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskINS3Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.Object1Tab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

								}

								flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.Object3Tab, Smoke_TaskFund1Name, action.SCROLLANDBOOLEAN, 10);		
								if (flag) {
									log(LogStatus.INFO,"Selected "+Smoke_TaskFund1Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.Object3Tab)+" For Label "+PageLabel.Related_Associations,YesNo.No);

								} else {
									sa.assertTrue(false,"Not Able to Select "+Smoke_TaskFund1Name+" For Drown Down Value : "+cp.getTabName(projectName, TabName.Object3Tab)+" For Label "+PageLabel.Related_Associations);
									log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskFund1Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.Object3Tab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

								}

								flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, taskCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
								if (flag) {
									log(LogStatus.INFO,"Selected "+taskCustomObj1Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.No);

								} else {
									sa.assertTrue(false,"Not Able to Select "+taskCustomObj1Name+" For Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations);
									log(LogStatus.SKIP,"Not Able to Select "+taskCustomObj1Name+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.TestCustomObjectTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

								}

								flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, contact3Name, action.SCROLLANDBOOLEAN, 10);		
								if (flag) {
									log(LogStatus.SKIP,"Selected "+contact3Name+" For  Drown Down Value : "+" For Label "+PageLabel.Related_Associations,YesNo.No);

								} else {
									sa.assertTrue(false,"Not Able to Select "+contact3Name+"For Label "+PageLabel.Name);
									log(LogStatus.SKIP,"Not Able to Select "+contact3Name+" For Label"+PageLabel.Name,YesNo.Yes);

								}


								System.err.println(">>>>>>>>>>>>>>>>>>>>>>Save Button<<<<<<<<<<<<<<<<<<<<<<<<<");

								flag=false;
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
									flag=true;
									ele = cp.getAnyMsg(projectName, tp.taskSavedMsg(projectName, Smoke_STDTask2OnSubject), 20);
									if (ele!=null) {
										log(LogStatus.SKIP,"Saved Task Msg Ele Found and Verified",YesNo.No);

									} else {
										sa.assertTrue(false,"Saved Task Msg Ele not Found");
										log(LogStatus.SKIP,"Saved Task Msg Ele not Found",YesNo.Yes);

									}

									ThreadSleep(3000);
									String relatedAssoctaion=tp.Comment(projectName, PageLabel.Related_Associations, Smoke_TaskINS3Name)+", "+Smoke_TaskFund1Name+", "+taskCustomObj1Name;
									String relatedContactValue= tp.Comment(projectName, PageLabel.Related_Contacts, contact1Name)+", "+contact3Name;
									String comment = relatedContactValue+"\n"+relatedAssoctaion+"\n"+TaskPagePageErrorMessage.Dots+"\n"+Smoke_STDTask2OnComment;

									String[][] fieldsWithValues= {
											{PageLabel.Subject.toString(),Smoke_STDTask2OnSubject},
											{PageLabel.Due_Date.toString(),Smoke_STDTask2OnDate},
											{PageLabel.Related_To.toString(),taskCustomObj1Name},
											{PageLabel.Related_Associations.toString(),Smoke_TaskINS3Name+", "+Smoke_TaskFund1Name+", "+taskCustomObj1Name},
											{PageLabel.Name.toString(),contact4Name+"\n + "+2},
											{PageLabel.Related_Contacts.toString(),contact1Name+", "+contact3Name},
											{PageLabel.Comments.toString(),comment}};

									tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);	

									System.err.println(">>>>>>>>>>>>>>>>>>>>>>Save222222222222222 Button<<<<<<<<<<<<<<<<<<<<<<<<<");

								}

								else {
									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not created" );
								}

								driver.close();
								driver.switchTo().window(parentID);
							}else {

							}


						} else {
							sa.assertTrue(false,"Not Able to Click on Link :  "+TaskPagePageErrorMessage.RelatedAssociationText);
							log(LogStatus.SKIP,"Not Able to Click on Link :  "+TaskPagePageErrorMessage.RelatedAssociationText,YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,"Not Able to Click on : "+Smoke_STDTask2OnSubject);
						log(LogStatus.SKIP,"Not Able to Click on : "+Smoke_STDTask2OnSubject,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,Smoke_STDTask2OnSubject+" link not present on Activity Timeline : ");
					log(LogStatus.SKIP,Smoke_STDTask2OnSubject+" link not present on Activity Timeline : ",YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Contact Not Found : "+contact4Name);
				log(LogStatus.SKIP,"Contact Not Found : "+contact4Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);

		//		if (flag) {
		//			
		//			String relatedAssoctaion=tp.Comment(projectName, PageLabel.Related_Associations, Smoke_TaskINS3Name)+", "+Smoke_TaskFund1Name+", "+taskCustomObj1Name;
		//			String relatedContactValue= tp.Comment(projectName, PageLabel.Related_Contacts, contact1Name)+", "+contact3Name;
		//			String comment = relatedContactValue+"\n"+relatedAssoctaion+"\n"+TaskPagePageErrorMessage.Dots+"\n"+Smoke_STDTask2OnComment;
		//
		//			String[][] fieldsWithValues= {
		//					{PageLabel.Subject.toString(),Smoke_STDTask2OnSubject},
		//					{PageLabel.Due_Date.toString(),Smoke_STDTask2OnDate},
		//					{PageLabel.Related_To.toString(),taskCustomObj1Name},
		//					{PageLabel.Related_Associations.toString(),Smoke_TaskINS3Name+", "+Smoke_TaskFund1Name+", "+taskCustomObj1Name},
		//					{PageLabel.Name.toString(),contact4Name+"\n + "+2},
		//					{PageLabel.Related_Contacts.toString(),contact1Name+", "+contact3Name},
		//					{PageLabel.Comments.toString(),comment}};
		//
		//			tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);	
		//
		//			System.err.println(">>>>>>>>>>>>>>>>>>>>>>Save222222222222222 Button<<<<<<<<<<<<<<<<<<<<<<<<<");
		//			 	
		//		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc049_2_VerifyAssignMultipleAssociationLinkAfterEnablingSetting_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		WebElement ele ;

		String contact4Name = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		String contact3Name = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String contact1Name = Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;

		String task1 = Smoke_STDTask2OnSubject;
		String taskDescription = Smoke_STDTask2OnComment;
		String taskMessage ="";

		String entity=Smoke_TaskINS2Name;

		taskMessage = BasePageErrorMessage.UpcomingTaskMsg+" "+contact4Name+" and 2 others about "+taskCustomObj1Name+" , "+Smoke_TaskINS3Name+" and "+Smoke_TaskFund1Name;

		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, entity, 20)) {
				log(LogStatus.INFO,"Clicked on Entity/Account : "+entity,YesNo.No);
				ThreadSleep(1000);


				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				click(driver, ele, "More Steps" ,action.BOOLEAN);
				
				ThreadSleep(2000);
				
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
				click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
				
				ThreadSleep(2000);

				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, task1, SubjectElement.SubjectLink, 10);
				if (ele!=null) {
					log(LogStatus.INFO,task1+" link present on Activity Timeline : ",YesNo.Yes);		
					tp.verifyActivityAtNextStep2(projectName, PageName.Object1Page, null,task1, taskMessage, Smoke_STDTask2OnDate, false, "", true, taskDescription, 10);

				} else {
					sa.assertTrue(false,task1+" link not present on Activity Timeline : "+" for : "+entity);
					log(LogStatus.SKIP,task1+" link not present on Activity Timeline : "+" for : "+entity,YesNo.Yes);
				}		

			} else {
				sa.assertTrue(false,"Entity/Account Not Found : "+entity);
				log(LogStatus.SKIP,"Entity/Account  Not Found : "+entity,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contact3Name, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contact3Name,YesNo.No);
				ThreadSleep(1000);


				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				click(driver, ele, "More Steps" ,action.BOOLEAN);
				
				ThreadSleep(2000);
				
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
				click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);

				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task1, SubjectElement.SubjectLink, 10);
				if (ele!=null) {
					log(LogStatus.INFO,task1+" link present on Activity Timeline : ",YesNo.Yes);		
					tp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, null,task1, taskMessage, Smoke_STDTask2OnDate, false, "", true, taskDescription, 10);

				} else {
					sa.assertTrue(false,task1+" link not present on Activity Timeline : "+" for : "+contact3Name);
					log(LogStatus.SKIP,task1+" link not present on Activity Timeline : "+" for : "+contact3Name,YesNo.Yes);
				}	

			} else {
				sa.assertTrue(false,"Contact Not Found : "+contact3Name);
				log(LogStatus.SKIP,"Contact  Not Found : "+contact3Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contact1Name, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contact1Name,YesNo.No);
				ThreadSleep(1000);

				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				click(driver, ele, "More Steps" ,action.BOOLEAN);
				
				ThreadSleep(2000);
				
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
				click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
				
				ThreadSleep(2000);
				
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task1, SubjectElement.SubjectLink, 10);
				if (ele!=null) {
					log(LogStatus.INFO,task1+" link present on Activity Timeline : ",YesNo.Yes);		
					tp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, null,task1, taskMessage, Smoke_STDTask2OnDate, false, "", true, taskDescription, 10);

				} else {
					sa.assertTrue(false,task1+" link not present on Activity Timeline : "+" for : "+contact1Name);
					log(LogStatus.SKIP,task1+" link not present on Activity Timeline : "+" for : "+contact1Name,YesNo.Yes);
				}	

			} else {
				sa.assertTrue(false,"Contact Not Found : "+contact1Name);
				log(LogStatus.SKIP,"Contact  Not Found : "+contact1Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc050_1_verifyNewTaskMultipleAssociation_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		String[] taskUIdata= {"","--None--","","","",Smoke_Task2Priority,Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,Status.Not_Started.toString()};
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String date=todaysDate;
		
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab)+","+ip.getTabName(projectName, TabName.TestCustomObjectTab);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 20)) {
				WebElement ele;
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Task_with_Multiple_Associations, 10);
					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.SCROLLANDBOOLEAN)) {
						if (ip.verifyUIOfCreateNewTaskWindow(projectName,crmUser1FirstName+" "+crmUser1LastName,taskUIdata,false)) {
							log(LogStatus.INFO, "successfully verified create new task ui", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify create new task ui", YesNo.Yes);
							sa.assertTrue(false, "could not verify create new task ui");
						}
						
						ele= lp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TaskPage, PageLabel.Related_Contacts.toString(), action.SCROLLANDBOOLEAN,5);

						if (ele==null) {
							log(LogStatus.INFO,"Related Contact Field is not present",YesNo.No);	
						} else {
							sa.assertTrue(false,"Related Contact Field should not be present");
							log(LogStatus.SKIP,"Related Contact Field should not be present",YesNo.Yes);
						}
					}
					else {
						log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
						sa.assertTrue(false,   "could not click on new task button");
					}
					if (clickUsingJavaScript(driver, ip.getcancelButton(projectName, 20),  "cancel button")) {
					}
					else {
						log(LogStatus.ERROR, "could not click on cross icon", YesNo.Yes);
						sa.assertTrue(false,   "could not click on cross icon");
					}
					refresh(driver);
					ThreadSleep(4000);
					ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Task_with_Multiple_Associations, 10);
					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
						if (clickUsingJavaScript(driver, ip.getrelatedAssociationsdropdownButton(projectName, 10), "dropdown button for related associations")) {
							List<String> l=compareMultipleListContainsByTitle(driver, tabs, ip.listOfObjectsInRelatedAssctions(projectName));
							if (l.isEmpty()) {
								log(LogStatus.INFO, "successfully verified presence of all tabs "+tabs, YesNo.No);
							}
							else {
								for (String a:l) {
									log(LogStatus.ERROR,"not found "+a,  YesNo.Yes);
									sa.assertTrue(false, "not found "+a);
								}
							}
							l.clear();
							l=compareMultipleListContainsByTitle(driver,ip.getTabName(projectName, TabName.Object2Tab).toString(), ip.listOfObjectsInRelatedAssctions(projectName));
							if (l.isEmpty()) {
								log(LogStatus.ERROR, "tab "+TabName.Object2Tab.toString()+" is present but should not be", YesNo.Yes);
								sa.assertTrue(false, "tab "+TabName.Object2Tab.toString()+" is present but should not be");
							}
							else {
								for (String a:l) {
									log(LogStatus.INFO,"not found "+a+" as expected",  YesNo.Yes);

								}
							}
							
							ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),false, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.SCROLLANDBOOLEAN, 15);
							if (ele!=null) {
								log(LogStatus.INFO, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	

								if (clickUsingJavaScript(driver, ele, "Cross Button For "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.BOOLEAN)) {
									log(LogStatus.INFO,"Clicked on Cross Button For "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,YesNo.Yes);
									ThreadSleep(2000);
								}
								else {
									sa.assertTrue(false,"Not Able to Click on Cross Button For "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
									log(LogStatus.SKIP,"Not Able to Click on Cross Button For "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,YesNo.Yes);
								}

							} else {
								sa.assertTrue(false,Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" not Found For Label "+PageLabel.Name.toString());
								log(LogStatus.ERROR, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

							}
							
							//3
							 boolean flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" For Label "+PageLabel.Name,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

							}
							ele= ip.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.Object2Page,  PageLabel.Name.toString(), action.SCROLLANDBOOLEAN,10);
							if (ele!=null) {
								if (sendKeys(driver, ele,Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName, "Related To Text Label", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"Enter Value to name Text Box : "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName,YesNo.No);	
									ThreadSleep(1000);

									ele=ip.getContactNameWithInstOrRelatedAssociationNameOnTask(projectName,  PageName.Object2Page, PageLabel.Name.toString(),
											Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName, Smoke_TaskINS1Name, action.SCROLLANDBOOLEAN, 10);
									if (ele!=null) {
										if (clickUsingJavaScript(driver, ele, "contact name ", action.BOOLEAN)) {
											log(LogStatus.SKIP,"Selected "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name,YesNo.No);

										} else {
											sa.assertTrue(false,"Not Able to Select "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name);
											log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);

										}
									}else {
										sa.assertTrue(false,"Not found "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name);
										log(LogStatus.SKIP,"Not found "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);

									}
								}else {
									sa.assertTrue(false,"Not visible textbox of For Label "+PageLabel.Name);
									log(LogStatus.SKIP,"Not visible textbox of For Label "+PageLabel.Name,YesNo.Yes);

								}
							}
							else {
								sa.assertTrue(false,"Not present on page: textbox of For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"Not present on page: textbox of For Label "+PageLabel.Name,YesNo.Yes);

							}

					
							
							
							ele = ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName, action.SCROLLANDBOOLEAN, 10);
							if (ele!=null) {
								log(LogStatus.INFO, "successfully verified presence of "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" in name field",YesNo.No);
							} else {
								sa.assertTrue(false,"not found "+ Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"not found "+ Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);

							}
							
							ele = ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.SCROLLANDBOOLEAN, 10);
							if (ele!=null) {
								log(LogStatus.INFO, "successfully verified presence of "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" in name field",YesNo.No);
							} else {
								sa.assertTrue(false,"not found "+ Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"not found "+ Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

							}
							
							flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.Object1Tab, Smoke_TaskINS3Name, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+Smoke_TaskINS3Name+" For Label "+PageLabel.Related_Associations,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+Smoke_TaskINS3Name+" For Label "+PageLabel.Related_Associations);
								log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskINS3Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

							}
							ThreadSleep(3000);
							flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.Object3Tab, Smoke_TaskFund1Name, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+Smoke_TaskFund1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+Smoke_TaskFund1Name+" For Label "+PageLabel.Related_Associations);
								log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskFund1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

							}
							flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, taskCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+taskCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+taskCustomObj1Name+" For Label "+PageLabel.Related_Associations);
								log(LogStatus.SKIP,"Not Able to Select "+taskCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

							}
							
							if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), Smoke_Task2MultipleSubject, "Subject", action.SCROLLANDBOOLEAN)) {
								if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), todaysDate, "due date", action.SCROLLANDBOOLEAN)) {
									if (sendKeys(driver, ip.getcommentsTextBox(projectName, 10), Smoke_Task2MultipleComment, "comments", action.SCROLLANDBOOLEAN)) {
										if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
											ThreadSleep(5000);
											
											clickUsingJavaScript(driver, cp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);

											ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
											click(driver, ele, "More Steps" ,action.BOOLEAN);
											
											ThreadSleep(2000);
											
											ele = cp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Expand_All, 10);
											click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
											
											String taskMessage = BasePageErrorMessage.UpcomingTaskMsg+" "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" and 1 other about "+Smoke_TaskINS3Name+" , "+Smoke_TaskFund1Name+" and "+taskCustomObj1Name;

											ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_Task2MultipleSubject, SubjectElement.SubjectLink, 10);
											if (ele!=null) {
												log(LogStatus.INFO,Smoke_Task2MultipleSubject+" link present on Activity Timeline : ",YesNo.Yes);		
												cp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,Smoke_Task2MultipleSubject, taskMessage, todaysDate, false, "", true, Smoke_Task2MultipleComment, 10);

											} else {
												sa.assertTrue(false,Smoke_Task2MultipleSubject+" link not present on Activity Timeline : "+" for : "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
												log(LogStatus.SKIP,Smoke_Task2MultipleSubject+" link not present on Activity Timeline : "+" for : "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,YesNo.Yes);
											}	
											
										}
										else {
											log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
											sa.assertTrue(false,"save button is not clickable so task not created" );
										}
										ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask16", excelLabel.Due_Date);
									}
									else {
										log(LogStatus.ERROR, "comments textbox is not visible so task could not be created", YesNo.Yes);
										sa.assertTrue(false,"comments textbox is not visible so task could not be created" );
									}
								}
								else {
									log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
									sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
								}
							}
							else {
								log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
								sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
							}
							
						}
						else {
							log(LogStatus.ERROR, "not able to click on dropdown of related associations so cannot check objects", YesNo.Yes);
							sa.assertTrue(false,"not able to click on dropdown of related associations so cannot check objects" );
						}
					}
					else {
						log(LogStatus.ERROR, "not able to click on new task button", YesNo.Yes);
						sa.assertTrue(false,"not able to click on new task button" );
					}

				
			}
			else {
				log(LogStatus.ERROR, "not able to click on : "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, YesNo.Yes);
				sa.assertTrue(false, "not able to click on : "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName );
			}
		}
		else {
			log(LogStatus.ERROR, "not able to click on Contact tab", YesNo.Yes);
			sa.assertTrue(false, "not able to click on Contact tab" );
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc050_2_verifyNewTaskMultipleAssociation_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contact3Name = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String contact1Name = Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		WebElement ele ;

		TabName[] tabNames = {TabName.Object1Tab,TabName.Object3Tab,TabName.TestCustomObjectTab,TabName.Object2Tab,TabName.Object2Tab};

		String[] names = {Smoke_TaskINS3Name,Smoke_TaskFund1Name,taskCustomObj1Name,contact3Name,contact1Name};

		TabName tabName ;
		int i=0;
		for (String name : names) {
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);

					ThreadSleep(2000);

					ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
					click(driver, ele, "More Steps" ,action.BOOLEAN);
					
					ThreadSleep(2000);
					
					ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
					click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
					
					ThreadSleep(7000);
					String taskMessage="";
					if (i==0) {
						taskMessage = BasePageErrorMessage.UpcomingTaskMsg+" "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" and 1 other about "+Smoke_TaskFund1Name+" and "+taskCustomObj1Name;;
						
					} else if(i==1) {
						taskMessage = BasePageErrorMessage.UpcomingTaskMsg+" "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" and 1 other about "+Smoke_TaskINS3Name+" and "+taskCustomObj1Name;;
						
					} else if(i==2) {
						taskMessage = BasePageErrorMessage.UpcomingTaskMsg+" "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" and 1 other about "+Smoke_TaskINS3Name+" and "+Smoke_TaskFund1Name;
						
					}else {
						taskMessage = BasePageErrorMessage.UpcomingTaskMsg+" "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" and 1 other about "+Smoke_TaskINS3Name+" , "+Smoke_TaskFund1Name+" and "+taskCustomObj1Name;;
					}
					
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_Task2MultipleSubject, SubjectElement.SubjectLink, 10);
					if (ele!=null) {
						log(LogStatus.INFO,Smoke_Task2MultipleSubject+" link present on Activity Timeline : ",YesNo.Yes);		
						cp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, null,Smoke_Task2MultipleSubject, taskMessage, Smoke_Task2MultipleDueDate, false, "", true, Smoke_Task2MultipleComment, 10);

					} else {
						sa.assertTrue(false,Smoke_Task2MultipleSubject+" link not present on Activity Timeline : "+" for : "+name);
						log(LogStatus.SKIP,Smoke_Task2MultipleSubject+" link not present on Activity Timeline : "+" for : "+name,YesNo.Yes);
					}

				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
		
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc051_1_VerifyingEditingaMultiTaggedTask2_Action(String projectName) {
			SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
			LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
			HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
			ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
			InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
			TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
			lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
			String contact3Name = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
			String contact1Name = Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
			String contact4Name = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
	
			String crmUserName = crmUser1FirstName+" "+crmUser1LastName;
			String adminUerName = AdminUserFirstName+" "+AdminUserLastName;
	
			String value="";
			boolean flag=false;
			List<String> items = new LinkedList<String>();		
			WebElement ele;
			String task1 = Smoke_Task2MultipleSubject;	
			String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask16", excelLabel.Due_Date);
	
	
			String customObject1=taskCustomObj1Name;
			switchToDefaultContent(driver);
			if (cp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
				log(LogStatus.INFO,"Clicked on Tab : "+TabName.TestCustomObjectTab,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, customObject1, 20)) {
					log(LogStatus.INFO,"Clicked on custom object: "+customObject1,YesNo.No);
					ThreadSleep(1000);
					ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
					click(driver, ele, "More Steps" ,action.BOOLEAN);
					
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task1, SubjectElement.SubjectLink, 10);
	
					if (clickUsingJavaScript(driver, ele, task1, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on : "+task1+ " on Activity Timeline",YesNo.No);		
						ThreadSleep(6000);
						//					scn.nextLine();
						ele = cp.getEditButton(projectName, 10);
						if (click(driver, ele, "Edit Button ", action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on Edit Button : "+task1,YesNo.No);	
	
							//1st assertion
	
							String[] taskUIdata= {task1,"--None--",Smoke_TaskINS3Name+","+Smoke_TaskFund1Name+","+taskCustomObj1Name,Smoke_Task2MultipleComment,todaysDate,Smoke_Task2MultiplePriority,Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+","+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName,Smoke_Task2MultipleStatus};
	
							if (ip.verifyUIOfCreateNewTaskWindow(projectName,crmUserName,taskUIdata,true )) {
								log(LogStatus.INFO, "successfully verified task ui edit mode", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify create new task ui", YesNo.Yes);
								sa.assertTrue(false, "could not verify create new task ui");
							}
	
							//2nd Assertion Name
	
							items.add(contact3Name);
							items.add(contact1Name);
							items.add(contact4Name);
							for (int i = 0; i < items.size(); i++) {
								value=items.get(i);
								if (value.equals(contact4Name)) {
									flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(), TabName.TestCustomObjectTab, value, action.SCROLLANDBOOLEAN, 10);		
									if (flag) {
										log(LogStatus.INFO,"Selected "+value+" For  Drown Down Value :  For Label "+PageLabel.Name,YesNo.No);
	
										List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(),true, action.SCROLLANDBOOLEAN, 15);
										if (!eleList.isEmpty() && eleList.size()==1) {
											log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Name.toString(),YesNo.No);	
										} else {
											sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
											log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);
	
										}
	
										ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(),true, value, action.SCROLLANDBOOLEAN, 5);
										if (ele!=null) {
											log(LogStatus.INFO, value+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	
										} else {
											sa.assertTrue(false,value+" not Found For Label "+PageLabel.Name.toString());
											log(LogStatus.ERROR, value+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);
	
										}
	
	
	
									} else {
										sa.assertTrue(false,"Not Able to Select "+value+" For Drown Down Value :  For Label "+PageLabel.Name);
										log(LogStatus.SKIP,"Not Able to Select "+value+" For  Drown Down Value : For Label "+PageLabel.Name,YesNo.Yes);
	
									}
								}else {
									if (tp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(),true, value, action.SCROLLANDBOOLEAN, 5)) {
										log(LogStatus.INFO, "Clicked on Cross Button against : "+value+" For Label : "+PageLabel.Name.toString(),YesNo.No);	
									} else {
										sa.assertTrue(false,"Not Able to Click on Cross Button against : "+value+" For Label : "+PageLabel.Name.toString());
										log(LogStatus.ERROR, "Not Able to Click on Cross Button against : "+value+" For Label : "+PageLabel.Name.toString(),YesNo.Yes);
									}
	
								}
							}
	
	
							//3rd Assertion Related_Associations
							items=new LinkedList<String>();
							items.add(Smoke_TaskINS3Name);
							items.add(Smoke_TaskFund1Name);
							items.add(taskCustomObj1Name);
							items.add(taskCustomObj2Name);
							for (int i = 0; i < items.size(); i++) {
								value=items.get(i);
								if (value.equals(taskCustomObj2Name)) {
									flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, value, action.SCROLLANDBOOLEAN, 10);		
									if (flag) {
										log(LogStatus.INFO,"Selected "+value+" For  Drown Down Value :  For Label "+PageLabel.Related_Associations,YesNo.No);
	
										List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(),true, action.SCROLLANDBOOLEAN, 15);
										if (!eleList.isEmpty() && eleList.size()==1) {
											log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Related_Associations.toString(),YesNo.No);	
										} else {
											sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Related_Associations.toString());
											log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Related_Associations.toString(),YesNo.Yes);
	
										}
	
										ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(),true, value, action.SCROLLANDBOOLEAN, 5);
										if (ele!=null) {
											log(LogStatus.INFO, value+" Found For Label "+PageLabel.Related_Associations.toString(),YesNo.No);	
										} else {
											sa.assertTrue(false,value+" not Found For Label "+PageLabel.Related_Associations.toString());
											log(LogStatus.ERROR, value+" not Found For Label "+PageLabel.Related_Associations.toString(),YesNo.Yes);
	
										}
	
	
	
									} else {
										sa.assertTrue(false,"Not Able to Select "+value+" For Drown Down Value :  For Label "+PageLabel.Related_Associations);
										log(LogStatus.SKIP,"Not Able to Select "+value+" For  Drown Down Value : For Label "+PageLabel.Related_Associations,YesNo.Yes);
	
									}
								}else {
									if (tp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(),true, value, action.SCROLLANDBOOLEAN, 5)) {
										log(LogStatus.INFO, "Clicked on Cross Button against : "+value+" For Label : "+PageLabel.Related_Associations.toString(),YesNo.No);	
									} else {
										sa.assertTrue(false,"Not Able to Click on Cross Button against : "+value+" For Label : "+PageLabel.Related_Associations.toString());
										log(LogStatus.ERROR, "Not Able to Click on Cross Button against : "+value+" For Label : "+PageLabel.Related_Associations.toString(),YesNo.Yes);
									}
	
								}
							}
	
	
							// 4TH ASSERTION
	
							items=new LinkedList<String>();
							items.add(crmUserName);
							items.add(adminUerName);
							for (int i = 0; i < items.size(); i++) {
								value=items.get(i);
								if (value.equals(adminUerName)) {
	
	
									ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(), action.BOOLEAN,5);
									if (click(driver, ele, "Assigned To Text Box", action.BOOLEAN)) {
										log(LogStatus.INFO,"Clicked on  Assigned To Text Box : ",YesNo.No);	
										ThreadSleep(1000);
	
										ele =  cp.getContactNameOrRelatedAssociationNameOnTask(projectName,  PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(),crmUserName, action.BOOLEAN,5);
										if (ele!=null) {
											log(LogStatus.INFO,crmUserName+" is available at Assigned Field",YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false,crmUserName+" is not available at Assigned Field");
											log(LogStatus.SKIP,crmUserName+" is not available at Assigned Field",YesNo.Yes);	
										}
	
									}else {
										sa.assertTrue(false,"Not Able to Click Assigned To Text Box : ");
										log(LogStatus.SKIP,"Not Able to Click Value to Assigned To Text Box : ",YesNo.Yes);	
									}
									flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(), TabName.TestCustomObjectTab, value, action.SCROLLANDBOOLEAN, 10);		
									if (flag) {
										log(LogStatus.INFO,"Selected "+value+" For  Drown Down Value :  For Label "+PageLabel.Assigned_To,YesNo.No);
	
										List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(),false, action.SCROLLANDBOOLEAN, 15);
										if (!eleList.isEmpty() && eleList.size()==1) {
											log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Assigned_To.toString(),YesNo.No);	
										} else {
											sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Assigned_To.toString());
											log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Assigned_To.toString(),YesNo.Yes);
	
										}
	
										ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(),false, value, action.SCROLLANDBOOLEAN, 5);
										if (ele!=null) {
											log(LogStatus.INFO, value+" Found For Label "+PageLabel.Assigned_To.toString(),YesNo.No);	
										} else {
											sa.assertTrue(false,value+" not Found For Label "+PageLabel.Assigned_To.toString());
											log(LogStatus.ERROR, value+" not Found For Label "+PageLabel.Assigned_To.toString(),YesNo.Yes);
	
										}
	
	
	
									} else {
										sa.assertTrue(false,"Not Able to Select "+value+" For Drown Down Value :  For Label "+PageLabel.Assigned_To);
										log(LogStatus.SKIP,"Not Able to Select "+value+" For  Drown Down Value : For Label "+PageLabel.Assigned_To,YesNo.Yes);
	
									}
								}else {
									if (tp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(),false, value, action.SCROLLANDBOOLEAN, 5)) {
										log(LogStatus.INFO, "Clicked on Cross Button against : "+value+" For Label : "+PageLabel.Assigned_To.toString(),YesNo.No);	
									} else {
										sa.assertTrue(false,"Not Able to Click on Cross Button against : "+value+" For Label : "+PageLabel.Assigned_To.toString());
										log(LogStatus.ERROR, "Not Able to Click on Cross Button against : "+value+" For Label : "+PageLabel.Assigned_To.toString(),YesNo.Yes);
									}
	
								}
							}
	
	
							// 5th Assertion
							if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_Task2MultipleUpdatedSubject,  PageLabel.Subject.toString(), action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Entered Value on Subject Field: "+Smoke_Task2MultipleUpdatedSubject, YesNo.No);	
								if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), tomorrowsDate, "due date", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Entered Value on Date Field: "+tomorrowsDate, YesNo.No);
	
									flag=false;
									if (click(driver, ip.getCustomTabSaveBtn(projectName, 10),"save", action.SCROLLANDBOOLEAN)) {
										ThreadSleep(5000);
										flag=true;
										log(LogStatus.INFO, "successfully clicked on save and edited task", YesNo.No);
										date=tomorrowsDate;
										ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask16", excelLabel.Due_Date);
	
	
									}
									else {
										sa.assertTrue(false,"could not click cross on save, so cannot edit task");
										log(LogStatus.ERROR, "could not click cross on save, so cannot edit task",YesNo.Yes);
									}
								}else {
									sa.assertTrue(false,"due date field is visible, so cannot edit task");
									log(LogStatus.ERROR, "due date field is visible, so cannot edit task",YesNo.Yes);
								}
							}
							else {
								sa.assertTrue(false,"subject field is visible, so cannot edit task");
								log(LogStatus.ERROR, "subject field is visible, so cannot edit task",YesNo.Yes);
							}
	
	
	
						} else {
							sa.assertTrue(false,"Not Able to Click on Edit Button : "+task1);
							log(LogStatus.SKIP,"Not Able to Click on Edit Button : "+task1,YesNo.Yes);
						}
	
					} else {
						sa.assertTrue(false,"Not Able to Click on : "+task1+ " on Activity Timeline");
						log(LogStatus.SKIP,"Not Able to Click on : "+task1+ " on Activity Timeline",YesNo.Yes);
					}
	
	
	
				} else {
					sa.assertTrue(false,"custom object Not Found : "+customObject1);
					log(LogStatus.SKIP,"custom object  Not Found : "+customObject1,YesNo.Yes);
				}
	
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab,YesNo.Yes);
			}
			switchToDefaultContent(driver);
			if (flag) {
	
	
						String relatedAssoctaion=tp.Comment(projectName, PageLabel.Related_Associations, taskCustomObj2Name);
						String comment = relatedAssoctaion+"\n"+TaskPagePageErrorMessage.Dots+"\n"+Smoke_Task2MultipleComment;
	
						String[][] fieldsWithValues= {{PageLabel.Assigned_To.toString(),adminUerName},
								{PageLabel.Status.toString(),Smoke_Task2MultipleStatus},
								{PageLabel.Subject.toString(),Smoke_Task2MultipleUpdatedSubject},
								{PageLabel.Name.toString(), Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName},
								{PageLabel.Due_Date.toString(),date},
								{PageLabel.Meeting_Type.toString(),""},
								{PageLabel.Related_Associations.toString(),taskCustomObj2Name},
								{PageLabel.Priority.toString(),Smoke_Task2MultiplePriority},
								{PageLabel.Comments.toString(),comment}};
						//   {PageLabel.Related_Contacts.toString(),""},
						ThreadSleep(10000);
						if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.SCROLLANDBOOLEAN, 5)) {
							log(LogStatus.INFO, "successfully verified all labels on task page", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verif all labels on task page", YesNo.Yes);
							sa.assertTrue(false,"could not verif all labels on task page");
						}
	
		
						if (tp.isRelatedContactEmpty(projectName, 10)) {
						log(LogStatus.INFO,"Related Contact Field Verified is empty For : "+Smoke_Task2MultipleUpdatedSubject,YesNo.No);	
					} else {
						sa.assertTrue(false,"Related Contact Field Not Verified and shoud be empty For : "+Smoke_Task2MultipleUpdatedSubject);
						log(LogStatus.SKIP,"Related Contact Field Not Verified and shoud be empty For : "+Smoke_Task2MultipleUpdatedSubject,YesNo.Yes);
					}
	
	
	
			}
	
	
			switchToDefaultContent(driver);
			lp.CRMlogout();
			sa.assertAll();
		}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc051_2_VerifyingEditingaMultiTaggedTask2_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contact3Name = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String contact1Name = Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		String contact4Name = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		String owner = AdminUserFirstName+" "+AdminUserLastName;
		WebElement ele = null ;
		String parentID=null;

		TabName[] tabNames = {TabName.Object1Tab,TabName.Object1Tab,TabName.Object3Tab,TabName.TestCustomObjectTab,
				TabName.Object2Tab,TabName.Object2Tab,TabName.Object2Tab,TabName.Object1Tab,TabName.TestCustomObjectTab};

		String[] names = {Smoke_TaskINS3Name,Smoke_TaskINS1Name,Smoke_TaskFund1Name,taskCustomObj1Name,
				contact3Name,contact1Name,contact4Name,Smoke_TaskINS2Name,taskCustomObj2Name};

		TabName tabName ;
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(1000);
					
					ThreadSleep(2000);
					
					ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
					click(driver, ele, "More Steps" ,action.BOOLEAN);
					
					ThreadSleep(2000);
					
					ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
					click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
					
					ThreadSleep(2000);
					String taskMessage="";
					
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, Smoke_Task2MultipleUpdatedSubject, SubjectElement.SubjectLink, 10);
					
						if (i<6) {

							if (ele==null) {
								log(LogStatus.INFO,Smoke_Task2MultipleUpdatedSubject+" link is not present on Activity Timeline For : "+name,YesNo.No);	

							} else {
								sa.assertTrue(false,Smoke_Task2MultipleUpdatedSubject+" link Should not  present on Activity Timeline For : "+name);
								log(LogStatus.SKIP,Smoke_Task2MultipleUpdatedSubject+" link Should not  present on Activity Timeline For : "+name,YesNo.Yes);
							}

						} else {
							
							if (i==6) {
								taskMessage = owner+" "+BasePageErrorMessage.UpcomingTaskMsg1+" about "+taskCustomObj2Name;;
								
							} else if(i==7) {
								taskMessage = owner+" "+BasePageErrorMessage.UpcomingTaskMsg1+" with "+contact4Name+" about "+taskCustomObj2Name;;
								
							}else {
								taskMessage = owner+" "+BasePageErrorMessage.UpcomingTaskMsg1+" with "+contact4Name;
							}
							

							if (ele!=null) {
								log(LogStatus.INFO,Smoke_Task2MultipleUpdatedSubject+" link present on Activity Timeline For : "+name,YesNo.No);	
								cp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, name,Smoke_Task2MultipleUpdatedSubject, taskMessage, Smoke_Task2MultipleDueDate, false, "", true, Smoke_Task2MultipleComment, 10);

							} else {
								sa.assertTrue(false,Smoke_Task2MultipleUpdatedSubject+" link Should be present on Activity Timeline For : "+name);
								log(LogStatus.SKIP,Smoke_Task2MultipleUpdatedSubject+" link Should be present on Activity Timeline For : "+name,YesNo.Yes);
							}
						}

				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc052_1_verifyLogACallMultipleAssociation_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String date=todaysDate;
		String[] taskUIdata= {Activity.Call.toString(),"--None--","","",date,Priority.Normal.toString(),Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,Status.Completed.toString()};
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab)+","+ip.getTabName(projectName, TabName.TestCustomObjectTab);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, 20)) {
				WebElement ele;
				//					scn.nextLine();
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations.toString(), action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					//					scn.nextLine();
					if (ip.verifyUIOfCreateNewTaskWindow(projectName,crmUser1FirstName+" "+crmUser1LastName,taskUIdata,false)) {
						log(LogStatus.INFO, "successfully verified create  Log_a_Call_with_Multiple_Associations ui", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify create  Log_a_Call_with_Multiple_Associations ui", YesNo.Yes);
						sa.assertTrue(false, "could not verify create  Log_a_Call_with_Multiple_Associations ui");
					}
					
					ele= lp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TaskPage, PageLabel.Related_Contacts.toString(), action.SCROLLANDBOOLEAN,5);

					if (ele==null) {
						log(LogStatus.INFO,"Related Contact Field is not present",YesNo.No);	
					} else {
						sa.assertTrue(false,"Related Contact Field should not be present");
						log(LogStatus.SKIP,"Related Contact Field should not be present",YesNo.Yes);
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on "+ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, YesNo.Yes);
					sa.assertTrue(false,   "could not click on "+ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations);
				}
				if (clickUsingJavaScript(driver, ip.getcancelButton(projectName, 20),  "cancel button")) {
				}
				else {
					log(LogStatus.ERROR, "could not click on cross icon", YesNo.Yes);
					sa.assertTrue(false,   "could not click on cross icon");
				}
				refresh(driver);
				ThreadSleep(4000);
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations.toString(), action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
						if (clickUsingJavaScript(driver, ip.getrelatedAssociationsdropdownButton(projectName, 10), "dropdown button for related associations")) {
							List<String> l=compareMultipleListContainsByTitle(driver, tabs, ip.listOfObjectsInRelatedAssctions(projectName));
							if (l.isEmpty()) {
								log(LogStatus.INFO, "successfully verified presence of all tabs "+tabs, YesNo.No);
							}
							else {
								for (String a:l) {
									log(LogStatus.ERROR,"not found "+a,  YesNo.Yes);
									sa.assertTrue(false, "not found "+a);
								}
							}
							l.clear();
							l=compareMultipleListContainsByTitle(driver,ip.getTabName(projectName, TabName.Object2Tab).toString(), ip.listOfObjectsInRelatedAssctions(projectName));
							if (l.isEmpty()) {
								log(LogStatus.ERROR, "tab "+TabName.Object2Tab.toString()+" is present but should not be", YesNo.Yes);
								sa.assertTrue(false, "tab "+TabName.Object2Tab.toString()+" is present but should not be");
							}
							else {
								for (String a:l) {
									log(LogStatus.INFO,"not found "+a+" as expected",  YesNo.Yes);

								}
							}
							
							ele=ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),false, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.SCROLLANDBOOLEAN, 15);
							if (ele!=null) {
								log(LogStatus.INFO, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	

								if (clickUsingJavaScript(driver, ele, "Cross Button For "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.BOOLEAN)) {
									log(LogStatus.INFO,"Clicked on Cross Button For "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,YesNo.Yes);
									ThreadSleep(2000);
								}
								else {
									sa.assertTrue(false,"Not Able to Click on Cross Button For "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
									log(LogStatus.SKIP,"Not Able to Click on Cross Button For "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,YesNo.Yes);
								}

							} else {
								sa.assertTrue(false,Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" not Found For Label "+PageLabel.Name.toString());
								log(LogStatus.ERROR, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

							}
							
							//3
							boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" For Label "+PageLabel.Name,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

							}
							ele= ip.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.Object1Page,  PageLabel.Name.toString(), action.SCROLLANDBOOLEAN,10);
							if (ele!=null) {
								if (sendKeys(driver, ele,Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName, "Related To Text Label", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"Enter Value to name Text Box : "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName,YesNo.No);	
									ThreadSleep(1000);

									ele=ip.getContactNameWithInstOrRelatedAssociationNameOnTask(projectName,  PageName.Object1Page, PageLabel.Name.toString(),
											Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName, Smoke_TaskINS1Name, action.SCROLLANDBOOLEAN, 10);
									if (ele!=null) {
										if (clickUsingJavaScript(driver, ele, "contact name ", action.BOOLEAN)) {
											log(LogStatus.SKIP,"Selected "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name,YesNo.No);

										} else {
											sa.assertTrue(false,"Not Able to Select "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name);
											log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);

										}
									}else {
										sa.assertTrue(false,"Not found "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name);
										log(LogStatus.SKIP,"Not found "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);

									}
								}else {
									sa.assertTrue(false,"Not visible textbox of For Label "+PageLabel.Name);
									log(LogStatus.SKIP,"Not visible textbox of For Label "+PageLabel.Name,YesNo.Yes);

								}
							}
							else {
								sa.assertTrue(false,"Not present on page: textbox of For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"Not present on page: textbox of For Label "+PageLabel.Name,YesNo.Yes);

							}

							
							ele = ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), true, Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName, action.SCROLLANDBOOLEAN, 10);
							if (ele!=null) {
								log(LogStatus.INFO, "successfully verified presence of "+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" in name field",YesNo.No);
							} else {
								sa.assertTrue(false,"not found "+ Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"not found "+ Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);

							}
							
							ele = ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, action.SCROLLANDBOOLEAN, 10);
							if (ele!=null) {
								log(LogStatus.INFO, "successfully verified presence of "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" in name field",YesNo.No);
							} else {
								sa.assertTrue(false,"not found "+ Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"not found "+ Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

							}
							
							flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.Object1Tab, Smoke_TaskINS3Name, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskINS3Name+" For Label "+PageLabel.Name,YesNo.Yes);

							}
							ThreadSleep(3000);
							flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.Object3Tab, Smoke_TaskFund1Name, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+Smoke_TaskFund1Name+" For Label "+PageLabel.Name,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+Smoke_TaskFund1Name+" For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"Not Able to Select "+Smoke_TaskFund1Name+" For Label "+PageLabel.Name,YesNo.Yes);

							}
							flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, taskCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.SKIP,"Selected "+taskCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);

							} else {
								sa.assertTrue(false,"Not Able to Select "+taskCustomObj1Name+" For Label "+PageLabel.Related_Associations);
								log(LogStatus.SKIP,"Not Able to Select "+taskCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

							}
							
							if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), Smoke_Task2LogACallNewSubject, "Subject", action.SCROLLANDBOOLEAN)) {
								if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), todaysDate, "due date", action.SCROLLANDBOOLEAN)) {
									if (sendKeys(driver, ip.getcommentsTextBox(projectName, 10), Smoke_Task2LogACallNewComment, "comments", action.SCROLLANDBOOLEAN)) {
										appLog.info(">>>");
										if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
											ThreadSleep(5000);
											clickUsingJavaScript(driver, lp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);
											ThreadSleep(2000);
											ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
											click(driver, ele, "More Steps" ,action.BOOLEAN);
											
											ThreadSleep(2000);
											
											ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
											click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
											
											
											String taskMessage = BasePageErrorMessage.LoggedACall+" "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" and 1 other about "+Smoke_TaskINS3Name+" , "+Smoke_TaskFund1Name+" and "+taskCustomObj1Name;

											ele = ip.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task2LogACallNewSubject, SubjectElement.SubjectLink, 10);
											if (ele!=null) {
												log(LogStatus.INFO,Smoke_Task2LogACallNewSubject+" link present on Activity Timeline : ",YesNo.Yes);		
												ip.verifyActivityAtPastStep2(projectName, PageName.Object2Page, Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,Smoke_Task2LogACallNewSubject, taskMessage, date, false, "", true, Smoke_Task2LogACallNewComment, 10);

											} else {
												sa.assertTrue(false,Smoke_Task2LogACallNewSubject+" link not present on Activity Timeline : "+" for : "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName);
												log(LogStatus.SKIP,Smoke_Task2LogACallNewSubject+" link not present on Activity Timeline : "+" for : "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName,YesNo.Yes);
											}
										}
										else {
											log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
											sa.assertTrue(false,"save button is not clickable so task not created" );
										}
										ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask18", excelLabel.Due_Date);
									}
									else {
										log(LogStatus.ERROR, "comments textbox is not visible so task could not be created", YesNo.Yes);
										sa.assertTrue(false,"comments textbox is not visible so task could not be created" );
									}
								}
								else {
									log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
									sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
								}
							}
							else {
								log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
								sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
							}
						
						}
						else {
							log(LogStatus.ERROR, "not able to click on dropdown of related associations so cannot check objects", YesNo.Yes);
							sa.assertTrue(false,"not able to click on dropdown of related associations so cannot check objects" );
						}
					}
					else {
						log(LogStatus.ERROR, "not able to click on new task button", YesNo.Yes);
						sa.assertTrue(false,"not able to click on new task button" );
					}

				
			}
			else {
				log(LogStatus.ERROR, "not able to click on "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName, YesNo.Yes);
				sa.assertTrue(false, "not able to click on "+Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName );
			}
		}
		else {
			log(LogStatus.ERROR, "not able to click on contact tab", YesNo.Yes);
			sa.assertTrue(false, "not able to click on contact tab" );
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc052_2_verifyLogACallMultipleAssociation_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contact3Name = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String contact1Name = Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		String owner = AdminUserFirstName+" "+AdminUserLastName;
		WebElement ele = null ;

		List<String> taskstd1=new LinkedList<String>();
		taskstd1.add(Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+" +1");
		taskstd1.add(Smoke_TaskINS3Name+" +2");
		taskstd1.add(Status.Completed.toString());
		taskstd1.add(crmUser1FirstName+" "+crmUser1LastName);
		taskstd1.add("");
		taskstd1.add(Activity.Call.toString());
		taskstd1.add(Links.View.toString());	
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Due_Date);

		
		TabName[] tabNames = {TabName.Object1Tab,TabName.Object3Tab,TabName.TestCustomObjectTab,TabName.Object2Tab,TabName.Object2Tab};

		String[] names = {Smoke_TaskINS3Name,Smoke_TaskFund1Name,taskCustomObj1Name,contact1Name,contact3Name};

		TabName tabName ;
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(2000);

					
					ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
					click(driver, ele, "More Steps" ,action.BOOLEAN);
					
					ThreadSleep(2000);
					
					ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
					click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
					
					ThreadSleep(2000);
					
					String taskMessage="";
					if (i==0) {
						taskMessage = BasePageErrorMessage.LoggedACall+" "+contact3Name+" and 1 other about "+Smoke_TaskFund1Name+" and "+taskCustomObj1Name;
					} else if(i==1) {
						taskMessage = BasePageErrorMessage.LoggedACall+" "+contact3Name+" and 1 other about "+Smoke_TaskINS3Name+" and "+taskCustomObj1Name;	
					}else if(i==2) {
						taskMessage = BasePageErrorMessage.LoggedACall+" "+contact3Name+" and 1 other about "+Smoke_TaskINS3Name+" and "+Smoke_TaskFund1Name;
					}else {
						taskMessage = BasePageErrorMessage.LoggedACall+" "+contact3Name+" and 1 other about "+Smoke_TaskINS3Name+" , "+Smoke_TaskFund1Name+" and "+taskCustomObj1Name;

					}


					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task2LogACallNewSubject, SubjectElement.SubjectLink, 10);


					if (ele!=null) {
						log(LogStatus.INFO,Smoke_Task2LogACallNewSubject+" link present on Activity Timeline For : "+name,YesNo.No);	
						ip.verifyActivityAtPastStep2(projectName, PageName.Object2Page, name,Smoke_Task2LogACallNewSubject, taskMessage, Smoke_Task2LogACallNewDate, false, "", true, Smoke_Task2LogACallNewComment, 10);
					} else {
						sa.assertTrue(false,Smoke_Task2LogACallNewSubject+" link Should be present on Activity Timeline For : "+name);
						log(LogStatus.SKIP,Smoke_Task2LogACallNewSubject+" link Should be present on Activity Timeline For : "+name,YesNo.Yes);
					}
					
					if (name.equals(contact3Name)) {

						if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {

							log(LogStatus.INFO,"click on View All Link on Load More Past Activities For : "+name,YesNo.No);

							String parentId=switchOnWindow(driver);
							if (parentId!=null) {	
								ThreadSleep(3000);
								
								ip.verifyingRelatedTabData2(projectName, PageName.Object2Page, RelatedTab.Activities, date, Smoke_Task2LogACallNewSubject, taskstd1, action.BOOLEAN, 10);

								driver.close();
								driver.switchTo().window(parentId);
							}else {
								log(LogStatus.ERROR,  "could not find new window to switch after clicking on View All Link on Load More Past Activities For : "+name, YesNo.Yes);
								sa.assertTrue(false, "could not find new window to switch after clicking on View All Link on Load More Past Activities For : "+name);
							}

						}else {
							sa.assertTrue(false,"Not Able to click on View All Link on Load More Past Activities For : "+name);
							log(LogStatus.SKIP,"Not Able to click on View All Link on Load More Past Activities For : "+name,YesNo.Yes);

						}

					}
					
				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc053_1_VerifyingEditingaMultiTaggedTask2LogACall_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contact3Name = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String contact1Name = Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		String contact4Name = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;

		String crmUserName = crmUser1FirstName+" "+crmUser1LastName;
		String adminUerName = AdminUserFirstName+" "+AdminUserLastName;

		String value="";
		boolean flag=false;
		List<String> items = new LinkedList<String>();		
		WebElement ele;
		String task1 = Smoke_Task2LogACallNewSubject;	
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Due_Date);


		String customObject1=taskCustomObj1Name;
		switchToDefaultContent(driver);
		if (cp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.TestCustomObjectTab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.TestCustomObjectTab, customObject1, 20)) {
				log(LogStatus.INFO,"Clicked on custom object: "+customObject1,YesNo.No);
				ThreadSleep(1000);
				refresh(driver);
						ThreadSleep(10000);
					 //					scn.nextLine();
						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task1, SubjectElement.SubjectLink, 10);

						
					if (clickUsingJavaScript(driver, ele, task1, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on : "+task1+ " on Activity Timeline",YesNo.No);		
						ThreadSleep(20000);
						 //					scn.nextLine();
						ele = cp.getEditButton(projectName, 10);
						if (click(driver, ele, "Edit Button ", action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on Edit Button : "+task1,YesNo.No);	
							ThreadSleep(20000);
							
							String[] taskUIdata= {task1,"--None--",Smoke_TaskINS3Name+","+Smoke_TaskFund1Name+","+taskCustomObj1Name,Smoke_Task2LogACallNewComment,Smoke_Task2LogACallNewDate,Smoke_Task2LogACallNewPriority,Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName+","+Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName,Status.Completed.toString()};
							
							if (ip.verifyUIOfCreateNewTaskWindow(projectName,crmUserName,taskUIdata,true )) {
								log(LogStatus.INFO, "successfully verified task ui edit mode", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify create new task ui", YesNo.Yes);
								sa.assertTrue(false, "could not verify create new task ui");
							}
							
							// Assertion Name

							items.add(contact3Name);
							items.add(contact1Name);
							items.add(contact4Name);
							for (int i = 0; i < items.size(); i++) {
								value=items.get(i);
								if (value.equals(contact4Name)) {
									flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(), TabName.TestCustomObjectTab, value, action.SCROLLANDBOOLEAN, 10);		
									if (flag) {
										log(LogStatus.INFO,"Selected "+value+" For  Drown Down Value :  For Label "+PageLabel.Name,YesNo.No);

										List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(),true, action.SCROLLANDBOOLEAN, 15);
										if (!eleList.isEmpty() && eleList.size()==1) {
											log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Name.toString(),YesNo.No);	
										} else {
											sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
											log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

										}

										ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(),true, value, action.SCROLLANDBOOLEAN, 5);
										if (ele!=null) {
											log(LogStatus.INFO, value+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	
										} else {
											sa.assertTrue(false,value+" not Found For Label "+PageLabel.Name.toString());
											log(LogStatus.ERROR, value+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

										}



									} else {
										sa.assertTrue(false,"Not Able to Select "+value+" For Drown Down Value :  For Label "+PageLabel.Name);
										log(LogStatus.SKIP,"Not Able to Select "+value+" For  Drown Down Value : For Label "+PageLabel.Name,YesNo.Yes);

									}
								}else {
									if (tp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Name.toString(),true, value, action.SCROLLANDBOOLEAN, 5)) {
										log(LogStatus.INFO, "Clicked on Cross Button against : "+value+" For Label : "+PageLabel.Name.toString(),YesNo.No);	
									} else {
										sa.assertTrue(false,"Not Able to Click on Cross Button against : "+value+" For Label : "+PageLabel.Name.toString());
										log(LogStatus.ERROR, "Not Able to Click on Cross Button against : "+value+" For Label : "+PageLabel.Name.toString(),YesNo.Yes);
									}

								}
							}


							//3rd Assertion Related_Associations
							items=new LinkedList<String>();
							items.add(Smoke_TaskINS3Name);
							items.add(Smoke_TaskFund1Name);
							items.add(taskCustomObj1Name);
							items.add(taskCustomObj2Name);
							for (int i = 0; i < items.size(); i++) {
								value=items.get(i);
								if (value.equals(taskCustomObj2Name)) {
									flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, value, action.SCROLLANDBOOLEAN, 10);		
									if (flag) {
										log(LogStatus.INFO,"Selected "+value+" For  Drown Down Value :  For Label "+PageLabel.Related_Associations,YesNo.No);

										List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(),true, action.SCROLLANDBOOLEAN, 15);
										if (!eleList.isEmpty() && eleList.size()==1) {
											log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Related_Associations.toString(),YesNo.No);	
										} else {
											sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Related_Associations.toString());
											log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Related_Associations.toString(),YesNo.Yes);

										}

										ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(),true, value, action.SCROLLANDBOOLEAN, 5);
										if (ele!=null) {
											log(LogStatus.INFO, value+" Found For Label "+PageLabel.Related_Associations.toString(),YesNo.No);	
										} else {
											sa.assertTrue(false,value+" not Found For Label "+PageLabel.Related_Associations.toString());
											log(LogStatus.ERROR, value+" not Found For Label "+PageLabel.Related_Associations.toString(),YesNo.Yes);

										}



									} else {
										sa.assertTrue(false,"Not Able to Select "+value+" For Drown Down Value :  For Label "+PageLabel.Related_Associations);
										log(LogStatus.SKIP,"Not Able to Select "+value+" For  Drown Down Value : For Label "+PageLabel.Related_Associations,YesNo.Yes);

									}
								}else {
									if (tp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Related_Associations.toString(),true, value, action.SCROLLANDBOOLEAN, 5)) {
										log(LogStatus.INFO, "Clicked on Cross Button against : "+value+" For Label : "+PageLabel.Related_Associations.toString(),YesNo.No);	
									} else {
										sa.assertTrue(false,"Not Able to Click on Cross Button against : "+value+" For Label : "+PageLabel.Related_Associations.toString());
										log(LogStatus.ERROR, "Not Able to Click on Cross Button against : "+value+" For Label : "+PageLabel.Related_Associations.toString(),YesNo.Yes);
									}

								}
							}


							// 4TH ASSERTION

							items=new LinkedList<String>();
							items.add(crmUserName);
							items.add(adminUerName);
							for (int i = 0; i < items.size(); i++) {
								value=items.get(i);
								if (value.equals(adminUerName)) {


									ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(), action.BOOLEAN,5);
									if (click(driver, ele, "Assigned To Text Box", action.BOOLEAN)) {
										log(LogStatus.INFO,"Clicked on  Assigned To Text Box : ",YesNo.No);	
										ThreadSleep(1000);

										ele =  cp.getContactNameOrRelatedAssociationNameOnTask(projectName,  PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(),crmUserName, action.BOOLEAN,5);
										if (ele!=null) {
											log(LogStatus.INFO,crmUserName+" is available at Assigned Field",YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false,crmUserName+" is not available at Assigned Field");
											log(LogStatus.SKIP,crmUserName+" is not available at Assigned Field",YesNo.Yes);	
										}

									}else {
										sa.assertTrue(false,"Not Able to Click Assigned To Text Box : ");
										log(LogStatus.SKIP,"Not Able to Click Value to Assigned To Text Box : ",YesNo.Yes);	
									}
									flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(), TabName.TestCustomObjectTab, value, action.SCROLLANDBOOLEAN, 10);		
									if (flag) {
										log(LogStatus.INFO,"Selected "+value+" For  Drown Down Value :  For Label "+PageLabel.Assigned_To,YesNo.No);

										List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(),false, action.SCROLLANDBOOLEAN, 15);
										if (!eleList.isEmpty() && eleList.size()==1) {
											log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Assigned_To.toString(),YesNo.No);	
										} else {
											sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Assigned_To.toString());
											log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Assigned_To.toString(),YesNo.Yes);

										}

										ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(),false, value, action.SCROLLANDBOOLEAN, 5);
										if (ele!=null) {
											log(LogStatus.INFO, value+" Found For Label "+PageLabel.Assigned_To.toString(),YesNo.No);	
										} else {
											sa.assertTrue(false,value+" not Found For Label "+PageLabel.Assigned_To.toString());
											log(LogStatus.ERROR, value+" not Found For Label "+PageLabel.Assigned_To.toString(),YesNo.Yes);

										}



									} else {
										sa.assertTrue(false,"Not Able to Select "+value+" For Drown Down Value :  For Label "+PageLabel.Assigned_To);
										log(LogStatus.SKIP,"Not Able to Select "+value+" For  Drown Down Value : For Label "+PageLabel.Assigned_To,YesNo.Yes);

									}
								}else {
									if (tp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(),false, value, action.SCROLLANDBOOLEAN, 5)) {
										log(LogStatus.INFO, "Clicked on Cross Button against : "+value+" For Label : "+PageLabel.Assigned_To.toString(),YesNo.No);	
									} else {
										sa.assertTrue(false,"Not Able to Click on Cross Button against : "+value+" For Label : "+PageLabel.Assigned_To.toString());
										log(LogStatus.ERROR, "Not Able to Click on Cross Button against : "+value+" For Label : "+PageLabel.Assigned_To.toString(),YesNo.Yes);
									}

								}
							}


							// 5th Assertion
							if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), PageLabel.Subject.toString(),20), Smoke_Task2LogACallNewUpdatedSubject,  PageLabel.Subject.toString(), action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Entered Value on Subject Field: "+Smoke_Task2LogACallNewUpdatedSubject, YesNo.No);	
								if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), tomorrowsDate, "due date", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Entered Value on Date Field: "+tomorrowsDate, YesNo.No);
									 ThreadSleep(2000);
									 
									 if (ip.selectDropDownValueonTaskPopUp(projectName, PageName.NewTaskPage, PageLabel.Priority.toString(), Smoke_Task2LogACallNewUpdatedPriority, action.SCROLLANDBOOLEAN, 10)) {
											log(LogStatus.INFO, "Selected : "+Smoke_Task2LogACallNewUpdatedPriority+" For Label : "+PageLabel.Priority, YesNo.No);	
											ThreadSleep(1000);	

										}else {
											log(LogStatus.ERROR, "Not Able to Select : "+Smoke_Task2LogACallNewUpdatedPriority+" For Label : "+PageLabel.Priority, YesNo.Yes);	
											BaseLib.sa.assertTrue(false, "Not Able to Select : "+Smoke_Task2LogACallNewUpdatedPriority+" For Label : "+PageLabel.Priority);	
										}
									 ThreadSleep(2000);
									flag=false;
									if (click(driver, ip.getCustomTabSaveBtn(projectName, 10),"save", action.SCROLLANDBOOLEAN)) {
										flag=true;
										log(LogStatus.INFO, "successfully clicked on save and edited task", YesNo.No);
										date=tomorrowsDate;
										ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask18", excelLabel.Due_Date);

									}
									else {
										sa.assertTrue(false,"could not click cross on save, so cannot edit task");
										log(LogStatus.ERROR, "could not click cross on save, so cannot edit task",YesNo.Yes);
									}
								}else {
									sa.assertTrue(false,"due date field is visible, so cannot edit task");
									log(LogStatus.ERROR, "due date field is visible, so cannot edit task",YesNo.Yes);
								}
							}
							else {
								sa.assertTrue(false,"subject field is visible, so cannot edit task");
								log(LogStatus.ERROR, "subject field is visible, so cannot edit task",YesNo.Yes);
							}



						} else {
							sa.assertTrue(false,"Not Able to Click on Edit Button : "+task1);
							log(LogStatus.SKIP,"Not Able to Click on Edit Button : "+task1,YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,"Not Able to Click on : "+task1+ " on Activity Timeline");
						log(LogStatus.SKIP,"Not Able to Click on : "+task1+ " on Activity Timeline",YesNo.Yes);
					}




			} else {
				sa.assertTrue(false,"custom object Not Found : "+customObject1);
				log(LogStatus.SKIP,"custom object  Not Found : "+customObject1,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TestCustomObjectTab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		if (flag) {

			String relatedAssoctaion=tp.Comment(projectName, PageLabel.Related_Associations, taskCustomObj2Name);
			String comment = relatedAssoctaion+"\n"+TaskPagePageErrorMessage.Dots+"\n"+Smoke_Task2LogACallNewComment;

			String[][] fieldsWithValues= {{PageLabel.Assigned_To.toString(),adminUerName},
					{PageLabel.Status.toString(),Status.Completed.toString()},
					{PageLabel.Subject.toString(),Smoke_Task2LogACallNewUpdatedSubject},
					{PageLabel.Name.toString(), Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName},
					{PageLabel.Due_Date.toString(),date},
					{PageLabel.Meeting_Type.toString(),""},
					{PageLabel.Related_Associations.toString(),taskCustomObj2Name},
					{PageLabel.Priority.toString(),Smoke_Task2LogACallNewUpdatedPriority},
					{PageLabel.Comments.toString(),comment}};
			//   {PageLabel.Related_Contacts.toString(),""},
			ThreadSleep(10000);
			if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.SCROLLANDBOOLEAN, 5)) {
				log(LogStatus.INFO, "successfully verified all labels on task page", YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "could not verif all labels on task page", YesNo.Yes);
				sa.assertTrue(false,"could not verif all labels on task page");
			}

			if (tp.isRelatedContactEmpty(projectName, 10)) {
				log(LogStatus.SKIP,"Related Contact Field Verified is empty For : "+Smoke_Task2LogACallNewUpdatedSubject,YesNo.No);	
			} else {
				sa.assertTrue(false,"Related Contact Field Not Verified and shoud be empty For : "+Smoke_Task2LogACallNewUpdatedSubject);
				log(LogStatus.SKIP,"Related Contact Field Not Verified and shoud be empty For : "+Smoke_Task2LogACallNewUpdatedSubject,YesNo.Yes);
			}
			
		}
		
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc053_2_VerifyingEditingaMultiTaggedTask2LogACall_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contact3Name = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		String contact1Name = Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		String owner = AdminUserFirstName+" "+AdminUserLastName;
		WebElement ele = null ;
	
		String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Due_Date);

		String contact4Name = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;

		String parentID=null;

		TabName[] tabNames = {TabName.TestCustomObjectTab,TabName.Object2Tab,TabName.Object1Tab,TabName.Object1Tab,TabName.Object1Tab,TabName.Object3Tab,TabName.TestCustomObjectTab,
				TabName.Object2Tab,TabName.Object2Tab};

		String[] names = {taskCustomObj2Name,contact4Name,Smoke_TaskINS2Name,Smoke_TaskINS3Name,Smoke_TaskINS1Name,Smoke_TaskFund1Name,taskCustomObj1Name,
				contact3Name,contact1Name};
		TabName tabName ;
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(2000);

					ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
					click(driver, ele, "More Steps" ,action.BOOLEAN);
					
					ThreadSleep(2000);
					
					ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
					click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);

					ThreadSleep(7000);
					String taskMessage="";
					if (i==0) {
						taskMessage = owner+" "+BasePageErrorMessage.LoggedACall1+" "+contact4Name;
					} else if(i==1) {
						taskMessage = owner+" "+BasePageErrorMessage.LoggedACallAbout+" "+taskCustomObj2Name;	
					}else if(i==2) {
						taskMessage = owner+" "+BasePageErrorMessage.LoggedACall1+" "+contact4Name+" about "+taskCustomObj2Name;
					}

					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task2LogACallNewUpdatedSubject, SubjectElement.SubjectLink, 10);
					scrollDownThroughWebelement(driver, ele, Smoke_Task2LogACallNewUpdatedSubject);
					if (i<3) {
						if (ele!=null) {
							log(LogStatus.INFO,Smoke_Task2LogACallNewUpdatedSubject+" link present on Activity Timeline For : "+name,YesNo.No);	
							ip.verifyActivityAtPastStep2(projectName, PageName.Object2Page, name,Smoke_Task2LogACallNewUpdatedSubject, taskMessage, Smoke_Task2LogACallNewDate, false, "", true, Smoke_Task2LogACallNewComment, 10);
						} else {
							sa.assertTrue(false,Smoke_Task2LogACallNewUpdatedSubject+" link Should be present on Activity Timeline For : "+name);
							log(LogStatus.SKIP,Smoke_Task2LogACallNewUpdatedSubject+" link Should be present on Activity Timeline For : "+name,YesNo.Yes);
						}
					} else {
						if (ele==null) {
							log(LogStatus.INFO,Smoke_Task2LogACallNewUpdatedSubject+" link is not present on Activity Timeline For : "+name,YesNo.No);	
						} else {
							sa.assertTrue(false,Smoke_Task2LogACallNewUpdatedSubject+" link Should not present on Activity Timeline For : "+name);
							log(LogStatus.SKIP,Smoke_Task2LogACallNewUpdatedSubject+" link Should not present on Activity Timeline For : "+name,YesNo.Yes);
						}
					}




				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
		
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc054_VerifyNewMeetingActionAfterEnablingTasksandEventsSetting(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contact3Name = Smoke_MTContact3FName+" "+Smoke_MTContact3LName;
		String contact1Name = Smoke_MTContact1FName+" "+Smoke_MTContact1LName;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		WebElement ele ;
		String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/YYYY");
		boolean flag=false;

		String contactNames[] = {contact3Name,contact1Name};
		RelatedTab relatedTab = RelatedTab.Meetings;

		if (cp.clickOnTab(projectName, TabName.Object3Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object3Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object3Tab, Smoke_MTFund1Name, 20)) {
				log(LogStatus.INFO,"Clicked on Fund/Deal : "+Smoke_MTFund1Name,YesNo.No);
				ThreadSleep(1000);
				refresh(driver);
				//					scn.nextLine();
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Meeting, 10);

				if (clickUsingJavaScript(driver, cp.getNewMeetingButton(projectName, 60), "New Meeting Button", action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New Meeting button on Activity TimeLine",YesNo.Yes);
					ThreadSleep(2000);	
					//					scn.nextLine();
					ele = cp.getHeaderTextForPage(projectName, PageName.Object2Page,PageLabel.New_Task.toString(), action.BOOLEAN, 20);
					if (ele!=null) {
						log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
					} else {
						sa.assertTrue(false,"New Task PopUp is not opened");
						log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
					}

					ele = cp.getRelatedAssciationDefaultSelectedDropDown(projectName, TabName.Object1Tab, PageLabel.Related_Associations.toString(), action.SCROLLANDBOOLEAN, 20);
					if (ele!=null) {
						log(LogStatus.INFO, "Default Selected For "+PageLabel.Related_Associations.toString()+" Verified",YesNo.No);	


					} else {
						sa.assertTrue(false,"Default Selected For "+PageLabel.Related_Associations.toString()+" Not Verified");
						log(LogStatus.ERROR, "Default Selected For "+PageLabel.Related_Associations.toString()+" Not Verified",YesNo.Yes);

					}

					List<WebElement> eleList = cp.getAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(),false, action.SCROLLANDBOOLEAN, 15);
					if (!eleList.isEmpty() && eleList.size()==1) {
						log(LogStatus.INFO, "Label field is filled with value for "+PageLabel.Related_Associations.toString(),YesNo.No);	

						ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(),false, Smoke_MTFund1Name, action.SCROLLANDBOOLEAN, 15);
						if (ele!=null) {
							log(LogStatus.INFO, Smoke_MTFund1Name+" is prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.No);	


						} else {
							sa.assertTrue(false,Smoke_MTFund1Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString());
							log(LogStatus.ERROR, Smoke_MTFund1Name+" is not prefilled for Label "+PageLabel.Related_Associations.toString(),YesNo.Yes);

						}

					} else {
						sa.assertTrue(false,"Label field sholud be filled with value for "+PageLabel.Name.toString());
						log(LogStatus.ERROR, "Label field sholud be filled with value for "+PageLabel.Name.toString(),YesNo.Yes);

					}


					for (String  contactName : contactNames) {

						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, contactName, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.SKIP,"Selected "+contactName+" For Label "+PageLabel.Name,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+contactName+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"Not Able to Select "+contactName+" For Label "+PageLabel.Name,YesNo.Yes);

						}

					}


					// Subject

					String[][] dropDownLabelWithValues = {{PageLabel.Meeting_Type.toString(),Smoke_DealClosureMeetingType}};
					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.TestCustomObjectPage, Smoke_DealClosureMeetingSubject, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {
						log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);

						if (clickUsingJavaScript(driver, cp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);


							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, Smoke_DealClosureMeetingSubject);
								if (actualValue.contains(expectedValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}

							clickUsingJavaScript(driver, lp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.SCROLLANDBOOLEAN);

							ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
							click(driver, ele, "More Steps" ,action.BOOLEAN);
							ThreadSleep(2000);
							
							ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
							click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
							ThreadSleep(2000);
							
							ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, Smoke_DealClosureMeetingSubject, SubjectElement.SubjectLink, 10);
							scrollDownThroughWebelement(driver, ele, Smoke_DealClosureMeetingSubject);
							String taskMessage = BasePageErrorMessage.UpcomingTaskMsg+" "+contact3Name+" and 1 other";			

							if (ele!=null) {
								log(LogStatus.INFO,Smoke_DealClosureMeetingSubject+" link present on Activity Timeline For : "+Smoke_MTFund1Name,YesNo.No);	
								lp.verifyActivityAtNextStep2(projectName, PageName.Object3Page, Smoke_MTFund1Name,Smoke_DealClosureMeetingSubject, taskMessage, DueDate.No_due_date.toString(), true, Smoke_DealClosureMeetingType, true, "", 10);
							} else {
								sa.assertTrue(false,Smoke_DealClosureMeetingSubject+" link Should be present on Activity Timeline For : "+Smoke_MTFund1Name);
								log(LogStatus.SKIP,Smoke_DealClosureMeetingSubject+" link Should be present on Activity Timeline For : "+Smoke_MTFund1Name,YesNo.Yes);
							}



						}
						else {
							log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
						}


					}

					else {
						log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
					}




				} else {
					sa.assertTrue(false,"Not Able to Click on New Meeting button on Activity TimeLine");
					log(LogStatus.SKIP,"Not Able to Click on New Meeting button on Activity TimeLine",YesNo.Yes);
				}





			} else {
				sa.assertTrue(false,"Fund/Deal Not Found : "+Smoke_MTFund1Name);
				log(LogStatus.SKIP,"Fund/Deal  Not Found : "+Smoke_MTFund1Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc055_1_RenameRecordAndVerifyActivitySearchgrid_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String owner = AdminUserFirstName+" "+AdminUserLastName;
		WebElement ele = null ;
		String parentID=null;
		String value="";



		String contact4Name = Smoke_TaskContact4FName+" "+Smoke_TaskContact4LName;
		String contact4UpdatedName = Smoke_TaskContact4UpdatedName;
		String taskCustomObj2UdatedName = taskCustomObj2UpdatedName;

		TabName[] tabNames = {TabName.Object2Tab,TabName.TestCustomObjectTab,TabName.Object2Tab};
		String[] names = {contact4Name,taskCustomObj2Name};

		TabName tabName ;
		int i=0;
		boolean flag=false;;
		for (String name : names) {
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(3000);
					refresh(driver);
					ThreadSleep(3000);

					if (i<2) {

						if (i==0) {
							cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object2Page);
							ele = cp.actionDropdownElement(projectName, PageName.Object2Page, ShowMoreActionDropDownList.Edit, 10);

						} else if(i==1) {
							cp.clickOnShowMoreDropdownOnly(projectName, PageName.TestCustomObjectPage);
							ele = cp.actionDropdownElement(projectName, PageName.TestCustomObjectPage, ShowMoreActionDropDownList.Edit, 10);

						}

						if (click(driver, ele, "Edit Button ", action.BOOLEAN)) {
							if (i==0) {
								ele=cp.getContactLastName(projectName, 60);	
								value = Smoke_TaskContact4LName+"UP";
							}else if(i==1) {
								ele = cop.getFieldTextBox(5);	
								value = taskCustomObj2UdatedName;
							}


							if (sendKeys(driver, ele, value+"\t", " Name",action.BOOLEAN)) {
								log(LogStatus.INFO,"Send Values For  : "+name,YesNo.No);	

								if (clickUsingJavaScript(driver, cp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"Clicked on Save Buttonb to Updated For : "+name,  YesNo.No);


								}else {
									log(LogStatus.ERROR, "Not Able to Click on Save Button For : "+name, YesNo.Yes);
									sa.assertTrue(false,"Not Able to Click on Save Button For : "+name);
								}


							} else {
								sa.assertTrue(false,"Not Able to Send Values For  : "+name);
								log(LogStatus.SKIP,"Not Able to Send Values For  : "+name,YesNo.Yes);
							}

						} else {
							sa.assertTrue(false,"Not Able to Click on Edit Button For : "+name);
							log(LogStatus.SKIP,"Not Able to Click on Edit Button For : "+name,YesNo.Yes);
						}

					}




				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc055_2_RenameRecordAndVerifyActivitySearchgrid_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String owner = AdminUserFirstName+" "+AdminUserLastName;
		WebElement ele = null ;
		String parentID=null;
		String value="";

		String contact4UpdatedName = Smoke_TaskContact4UpdatedName;
		String taskCustomObj2UdatedName = taskCustomObj2UpdatedName;

		TabName[] tabNames = {TabName.Object2Tab,TabName.TestCustomObjectTab};
		String[] names = {contact4UpdatedName,taskCustomObj2UdatedName};

		TabName tabName ;
		int i=0;
		boolean flag=false;;
		for (String name : names) {
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(3000);
					refresh(driver);
					ThreadSleep(3000);

					if (i==0) {


						ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
						click(driver, ele, "More Steps" ,action.BOOLEAN);
						ThreadSleep(2000);
						
						ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
						click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
						ThreadSleep(2000);


						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task2LogACallNewUpdatedSubject, SubjectElement.RedFlag, 10);
						scrollDownThroughWebelement(driver, ele, Smoke_Task2LogACallNewUpdatedSubject);

						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task2LogACallNewUpdatedSubject,YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task2LogACallNewUpdatedSubject,YesNo.Yes);
							sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task2LogACallNewUpdatedSubject);
						}
						
						if (ele!=null) {
							log(LogStatus.INFO,Smoke_Task2LogACallNewUpdatedSubject+" link present on Activity Timeline For : "+name,YesNo.No);	
							String taskMessage = owner+" "+BasePageErrorMessage.LoggedACallAbout+" "+taskCustomObj2UdatedName;	
							ip.verifyActivityAtPastStep2(projectName, PageName.Object2Page, name,Smoke_Task2LogACallNewUpdatedSubject, taskMessage, Smoke_Task2LogACallNewDate, false, "", true, Smoke_Task2LogACallNewComment, 10);
							ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task2LogACallNewUpdatedSubject, SubjectElement.SubjectLink, 10);
							
							if (click(driver, ele, Smoke_Task2LogACallNewUpdatedSubject, action.BOOLEAN)) {
								log(LogStatus.INFO,"Clicked on : "+Smoke_Task2LogACallNewUpdatedSubject,YesNo.No);
								ThreadSleep(10000);


								String relatedAssoctaion=tp.Comment(projectName, PageLabel.Related_Associations, taskCustomObj2UdatedName);
								String comment = relatedAssoctaion+"\n"+TaskPagePageErrorMessage.Dots+"\n"+Smoke_Task2LogACallNewComment;

								String[][] fieldsWithValues= {{PageLabel.Assigned_To.toString(),owner},
										{PageLabel.Status.toString(),Status.Completed.toString()},
										{PageLabel.Subject.toString(),Smoke_Task2LogACallNewUpdatedSubject},
										{PageLabel.Name.toString(),contact4UpdatedName},
										{PageLabel.Due_Date.toString(),Smoke_Task2LogACallNewDate},
										{PageLabel.Meeting_Type.toString(),""},
										{PageLabel.Related_Associations.toString(),taskCustomObj2UdatedName},
										{PageLabel.Priority.toString(),Smoke_Task2LogACallNewUpdatedPriority},
										{PageLabel.Comments.toString(),comment}};

								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);	


							} else {
								sa.assertTrue(false,"Not Able to Click on : "+Smoke_Task2LogACallNewUpdatedSubject);
								log(LogStatus.SKIP,"Not Able to Click on : "+Smoke_Task2LogACallNewUpdatedSubject,YesNo.Yes);
							}

						} else {
							sa.assertTrue(false,Smoke_Task2LogACallNewUpdatedSubject+" link Should be present on Activity Timeline For : "+name);
							log(LogStatus.SKIP,Smoke_Task2LogACallNewUpdatedSubject+" link Should be present on Activity Timeline For : "+name,YesNo.Yes);

						}



					}else {
						

						ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
						click(driver, ele, "More Steps" ,action.BOOLEAN);
						ThreadSleep(2000);
						
						ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
						click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
						ThreadSleep(2000);

						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task2LogACallNewUpdatedSubject, SubjectElement.RedFlag, 10);
						scrollDownThroughWebelement(driver, ele, Smoke_Task2LogACallNewUpdatedSubject);


						if (ele!=null) {
							log(LogStatus.INFO,Smoke_Task2LogACallNewUpdatedSubject+" red flag present on Activity Timeline For : "+name,YesNo.No);	
							String taskMessage = owner+" "+BasePageErrorMessage.LoggedACall1+" "+contact4UpdatedName;	
							ip.verifyActivityAtPastStep2(projectName, PageName.Object2Page, name,Smoke_Task2LogACallNewUpdatedSubject, taskMessage, Smoke_Task2LogACallNewDate, false, "", true, Smoke_Task2LogACallNewComment, 10);

						
						if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {

							log(LogStatus.INFO,"click on View All Link on Load More Past Activities For : "+name,YesNo.No);

							String parentId=switchOnWindow(driver);
							if (parentId!=null) {	
								ThreadSleep(3000);
								
								List<String> taskstd1=new LinkedList<String>();
								taskstd1.add(Smoke_TaskContact4UpdatedName);
								taskstd1.add(taskCustomObj2UdatedName);
								taskstd1.add(Status.Completed.toString());
								taskstd1.add(AdminUserFirstName+" "+AdminUserLastName);
								taskstd1.add("");
								taskstd1.add(Activity.Call.toString());
								taskstd1.add(Links.View.toString());	
								String date=ExcelUtils.readData(testCasesFilePath, "Task", excelLabel.Variable_Name,"AATask18", excelLabel.Due_Date);
								ip.verifyingRelatedTabData2(projectName, PageName.TestCustomObjectPage, RelatedTab.Activities, date, Smoke_Task2LogACallNewUpdatedSubject, taskstd1, action.BOOLEAN, 10);
	
								driver.close();
								driver.switchTo().window(parentId);
							}else {
								log(LogStatus.ERROR,  "could not find new window to switch after clicking on View All Link on Load More Past Activities For : "+name, YesNo.Yes);
								sa.assertTrue(false, "could not find new window to switch after clicking on View All Link on Load More Past Activities For : "+name);
							}

						}else {
							sa.assertTrue(false,"Not Able to click on View All Link on Load More Past Activities For : "+name);
							log(LogStatus.SKIP,"Not Able to click on View All Link on Load More Past Activities For : "+name,YesNo.Yes);

						}
						
						} else {
							sa.assertTrue(false,Smoke_Task2LogACallNewUpdatedSubject+" link Should be present on Activity Timeline For : "+name);
							log(LogStatus.SKIP,Smoke_Task2LogACallNewUpdatedSubject+" link Should be present on Activity Timeline For : "+name,YesNo.Yes);

						}
					}

				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc056_DeleteRecordAndVerifyActivitySearchgrid(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		WebElement ele = null ;
		String parentID=null;
		String value="";
		boolean flag=false;


		String contact1Name = Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		String contact4UpdatedName = Smoke_TaskContact4UpdatedName;
		String taskCustomObj2UdatedName = taskCustomObj2UpdatedName;
		String task=Smoke_Task2LogACallNewUpdatedSubject;
		String owner = AdminUserFirstName+" "+AdminUserLastName;
	

		TabName tabName = TabName.Object2Tab ;
		String name=contact4UpdatedName;
		int i=0;

//////////////////////////////////////////////////// Edit Task /////////////////////////////////////////////////////////////////
		
		if (cp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
				log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
				ThreadSleep(1000);

				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				click(driver, ele, "More Steps" ,action.BOOLEAN);
				ThreadSleep(2000);


				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task2LogACallNewUpdatedSubject, SubjectElement.SubjectLink, 10);
				scrollDownThroughWebelement(driver, ele, Smoke_Task2LogACallNewUpdatedSubject);

	
				if (ele!=null) {
					log(LogStatus.INFO,Smoke_Task2LogACallNewUpdatedSubject+" link present on Activity Timeline For : "+name,YesNo.No);	
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task2LogACallNewUpdatedSubject, SubjectElement.SubjectLink, 10);
					
					flag =	click(driver, ele, Smoke_Task2LogACallNewUpdatedSubject, action.BOOLEAN);
					if (flag) {
						log(LogStatus.INFO,"Clicked on : "+Smoke_Task2LogACallNewUpdatedSubject,YesNo.No);
						ThreadSleep(10000);
						//					scn.nextLine();
						ele = cp.getEditButton(projectName, 10);
						if (click(driver, ele, "Edit Button ", action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on Edit Button : "+Smoke_Task2LogACallNewUpdatedSubject,YesNo.No);	
							ThreadSleep(20000);
							
							flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.TestCustomObjectTab, contact1Name, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.INFO,"Selected "+contact1Name+" For  Drown Down Value :  For Label "+PageLabel.Name,YesNo.No);
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"Clicked on Save Button : "+task,  YesNo.Yes);
									ThreadSleep(1000);
									
									int m=1;
									while (isDisplayed(driver, lp.getCustomTabSaveBtn(projectName,10), "visibility", 10, "save")!=null) {
										clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,10), "save", action.SCROLLANDBOOLEAN);
										log(LogStatus.INFO, "clicked save "+m+" times", YesNo.No);
										m++;
									}

									String[][] fieldsWithValues= {{PageLabel.Related_Contacts.toString(),contact1Name}};
									tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);	

								}
								else {
									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not created" );
								}
							} else {
								sa.assertTrue(false,"Not Able to Select "+contact1Name+" For Drown Down Value :  For Label "+PageLabel.Name);
								log(LogStatus.SKIP,"Not Able to Select "+contact1Name+" For  Drown Down Value : For Label "+PageLabel.Name,YesNo.Yes);

							}
							

						} else {
							sa.assertTrue(false,"Not Able to Click on Edit Button : "+Smoke_Task2LogACallNewUpdatedSubject);
							log(LogStatus.SKIP,"Not Able to Click on Edit Button : "+Smoke_Task2LogACallNewUpdatedSubject,YesNo.Yes);
						}


					} else {
						sa.assertTrue(false,"Not Able to Click on : "+Smoke_Task2LogACallNewUpdatedSubject);
						log(LogStatus.SKIP,"Not Able to Click on : "+Smoke_Task2LogACallNewUpdatedSubject,YesNo.Yes);
					}


				} else {
					sa.assertTrue(false,Smoke_Task2LogACallNewUpdatedSubject+" link Should be present on Activity Timeline For : "+name);
					log(LogStatus.SKIP,Smoke_Task2LogACallNewUpdatedSubject+" link Should be present on Activity Timeline For : "+name,YesNo.Yes);

				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
				log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
		}

/////////////////////////////////////////////////////  Delete Operation /////////////////////////////////////////////
		
		TabName[] tabNames = {TabName.Object2Tab,TabName.TestCustomObjectTab};
		PageName pName[]= {PageName.Object2Page,PageName.TestCustomObjectPage};
		String[] names = {contact4UpdatedName,taskCustomObj2UdatedName};
		for (i=0; i < names.length; i++) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(1000);

					cp.clickOnShowMoreDropdownOnly(projectName, pName[i]);
					log(LogStatus.INFO,"Able to Click on Show more Icon : "+tabName+" For : "+name,YesNo.No);
					ThreadSleep(500);
					ele = cp.actionDropdownElement(projectName, pName[i], ShowMoreActionDropDownList.Delete, 15);
					 if (ele==null) {
						 ele =cp.getDeleteButton(projectName, 30);
					} 
					
					 if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete more Icon : "+tabName+" For : "+name,YesNo.No);
						ThreadSleep(1000);
						if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on Delete PoPup : "+tabName+" For : "+name,YesNo.No);
							ThreadSleep(10000);
							if (cp.clickOnTab(projectName, tabName)) {
								log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
								ThreadSleep(1000);
								if (!cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 10)) {
									log(LogStatus.INFO,"Item has been Deleted after delete operation  : "+name+" For : "+tabName,YesNo.No);

								}else {
									sa.assertTrue(false,"Item has not been Deleted after delete operation  : "+name+" For : "+tabName);
									log(LogStatus.SKIP,"Item has not been Deleted after delete operation  : "+name+" For : "+tabName,YesNo.Yes);
								}

							}else {
								sa.assertTrue(false,"Not Able to Click on Tab after delete : "+tabName+" For : "+name);
								log(LogStatus.SKIP,"Not Able to Click on Tab after delete : "+tabName+" For : "+name,YesNo.Yes);	
							}
						} else {
							sa.assertTrue(false,"Not Able to Click on Delete button on Delete PoPup : "+tabName+" For : "+name);
							log(LogStatus.SKIP,"Not Able to Click on Delete button on Delete PoPup : "+tabName+" For : "+name,YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,"Not Able to Click on Delete more Icon : "+tabName+" For : "+name);
						log(LogStatus.SKIP,"Not Able to Click on Delete more Icon : "+tabName+" For : "+name,YesNo.Yes);
					}



				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}

		}
		
		//////////////////////////////////////////////////Verification////////////////////////////////////////////////////////
		
		tabName = TabName.Object2Tab ;
		 name=contact1Name;
		
		if (cp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
				log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
				ThreadSleep(1000);

				ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
				click(driver, ele, "More Steps" ,action.BOOLEAN);
				ThreadSleep(2000);
				
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
				click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
				ThreadSleep(2000);


				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, Smoke_Task2LogACallNewUpdatedSubject, SubjectElement.RedFlag, 10);
				scrollDownThroughWebelement(driver, ele, Smoke_Task2LogACallNewUpdatedSubject);

				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of red flag of task "+Smoke_Task2LogACallNewUpdatedSubject,YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not verify presence of red flag of task "+Smoke_Task2LogACallNewUpdatedSubject,YesNo.Yes);
					sa.assertTrue(false, "could not verify presence of red flag of task "+Smoke_Task2LogACallNewUpdatedSubject);
				}
				
				
				if (ele!=null) {
					log(LogStatus.INFO,Smoke_Task2LogACallNewUpdatedSubject+" link present on Activity Timeline For : "+name,YesNo.No);	
					String taskMessage = BasePageErrorMessage.YouLoggedACall;	
					ip.verifyActivityAtPastStep2(projectName, PageName.Object2Page, name,Smoke_Task2LogACallNewUpdatedSubject, taskMessage, Smoke_Task2LogACallNewDate, false, "", true, Smoke_Task2LogACallNewComment, 10);

				
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {

					log(LogStatus.INFO,"click on View All Link on Load More Past Activities For : "+name,YesNo.No);

					String parentId=switchOnWindow(driver);
					if (parentId!=null) {	
						ThreadSleep(3000);
						
						List<String> taskstd1=new LinkedList<String>();
						taskstd1.add(contact1Name);
						taskstd1.add("");
						taskstd1.add(Status.Completed.toString());
						taskstd1.add(AdminUserFirstName+" "+AdminUserLastName);
						taskstd1.add("");
						taskstd1.add(Activity.Call.toString());
						taskstd1.add(Links.View.toString());	
						log(LogStatus.INFO,"Going for Related Tab Data Verification after Edit Task and Delete Operation on Object"+task,YesNo.Yes);
						ip.verifyingRelatedTabData2(projectName, PageName.Object1Page, RelatedTab.Activities, Smoke_Task2LogACallNewDate, Smoke_Task2LogACallNewUpdatedSubject, taskstd1, action.BOOLEAN, 10);
						log(LogStatus.INFO,"Related Tab Data Verification done after Edit Task and Delete Operation on Object"+task,YesNo.Yes);

						driver.close();
						driver.switchTo().window(parentId);
					}else {
						log(LogStatus.ERROR,  "could not find new window to switch after clicking on View All Link on Load More Past Activities For : "+name, YesNo.Yes);
						sa.assertTrue(false, "could not find new window to switch after clicking on View All Link on Load More Past Activities For : "+name);
					}

				}else {
					sa.assertTrue(false,"Not Able to click on View All Link on Load More Past Activities For : "+name);
					log(LogStatus.SKIP,"Not Able to click on View All Link on Load More Past Activities For : "+name,YesNo.Yes);

				}
				
				} else {
					sa.assertTrue(false,Smoke_Task2LogACallNewUpdatedSubject+" link Should be present on Activity Timeline For : "+name);
					log(LogStatus.SKIP,Smoke_Task2LogACallNewUpdatedSubject+" link Should be present on Activity Timeline For : "+name,YesNo.Yes);

				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
				log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	
	}
	
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc057_1_DeleteActivityAndVerifyActivitySearchgrid_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		WebElement ele = null ;
		String parentID=null;
		String value="";
		boolean flag=false;
		String task="";


		String contact1Name = Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName;
		String[] actionNames = {contact1Name,Smoke_MTFund1Name};
		TabName[] tabNames = {TabName.Object2Tab,TabName.Object3Tab};
		RelatedTab relatedTab=null;

		TabName tabName ;
		int i=0;
		for (String name : actionNames) {
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					
					ThreadSleep(1000);
					ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
					click(driver, ele, "More Steps" ,action.BOOLEAN);
					ThreadSleep(2000);
					
					if (i==0) {

						task=Smoke_Task2LogACallNewUpdatedSubject;
						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task, SubjectElement.SubjectLink, 10);

					} else {

						task=Smoke_DealClosureMeetingSubject;
						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);

					}

					if (ele!=null) {
						log(LogStatus.INFO,task+" link present on Activity TimeLine  For : "+name,YesNo.No);
						ThreadSleep(1000);
						flag =		click(driver, ele, task, action.BOOLEAN);
						ThreadSleep(1000);
						
						if (flag) {
							log(LogStatus.INFO,"Clicked on : "+task,YesNo.No);
							ThreadSleep(1000);
							//					scn.nextLine();
							ele = cp.getHeaderTextForPage(projectName, PageName.TaskPage, task, action.BOOLEAN, 10);
	                        if (ele!=null) {
	                            log(LogStatus.INFO,"Landing Page Verified for : "+task,YesNo.No);   
	                        } else {
	                            sa.assertTrue(false,"Landing Page Not Verified for : "+task);
	                            log(LogStatus.SKIP,"Landing Page Not Verified for : "+task,YesNo.Yes);
	                        }

							if (click(driver, cp.getDeleteButton(projectName, 5), "Delete Button", action.BOOLEAN)) {
								log(LogStatus.INFO,"Able to Click on Delete button : "+tabName+" For : "+name+" for : "+task,YesNo.No);
								ThreadSleep(2000);

								if (click(driver, cp.getDeleteButtonPopUp(projectName, 5), "Delete Button", action.BOOLEAN)) {
									log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+tabName+" For : "+name+" for : "+task,YesNo.No);
									ThreadSleep(2000);
								} else {
									sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+tabName+" For : "+name+" for : "+task);
									log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+tabName+" For : "+name+" for : "+task,YesNo.Yes);
								}

							} else {
								sa.assertTrue(false,"Not Able to Click on Delete button : "+tabName+" For : "+name+" for : "+task);
								log(LogStatus.SKIP,"Not Able to Click on Delete button : "+tabName+" For : "+name+" for : "+task,YesNo.Yes);
							}



						} else {
							sa.assertTrue(false,"Not Able to Click on : "+task);
							log(LogStatus.SKIP,"Not Able to Click on : "+task,YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,task+" link Should be present on Activity TimeLine  For : "+name);
						log(LogStatus.SKIP,task+" link Should be present on Activity TimeLine  For : "+name,YesNo.Yes);
					}



				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc057_2_DeleteActivityAndVerifyActivitySearchgrid_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		WebElement ele = null ;
		String task=Smoke_DealClosureMeetingSubject;


		String[] impactedContact = {Smoke_TaskContact1FName+" "+Smoke_TaskContact1LName,Smoke_MTContact3FName+" "+Smoke_MTContact3LName,Smoke_MTContact1FName+" "+Smoke_MTContact1LName,Smoke_MTFund1Name};
		TabName[] tabNames = {TabName.Object2Tab,TabName.Object2Tab,TabName.Object2Tab,TabName.Object3Tab};

		TabName tabName ;
		int i=0;

		switchToDefaultContent(driver);
		log(LogStatus.INFO,"Going to Check on Contact Page after deleteting : "+task,YesNo.No);
		for (int j = 0; j < impactedContact.length; j++) {
			switchToDefaultContent(driver);
			tabName  = tabNames[j];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+impactedContact[j],YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, impactedContact[j], 20)) {
					log(LogStatus.INFO,"Clicked on  : "+impactedContact[j]+" For : "+tabName,YesNo.No);
					ThreadSleep(1000);

					ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
					click(driver, ele, "More Steps" ,action.BOOLEAN);
					
					
					if (j==0) {

						task=Smoke_Task2LogACallNewUpdatedSubject;
						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task, SubjectElement.SubjectLink, 10);

					} else {

						task=Smoke_DealClosureMeetingSubject;
						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);

					}

					if (ele==null) {
						log(LogStatus.INFO,task+" link is not present on Activity TimeLine  For : "+impactedContact[j],YesNo.No);
						ThreadSleep(1000);} else {
							sa.assertTrue(false,task+" link Should not be present on Activity TimeLine  For : "+impactedContact[j]);
							log(LogStatus.SKIP,task+" link Should not be present on Activity TimeLine  For : "+impactedContact[j],YesNo.Yes);
						}

					if (Smoke_MTFund1Name.equals(impactedContact[j])) {

						if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {

							log(LogStatus.INFO,"click on View All Link on Load More Past Activities For : "+impactedContact[j],YesNo.No);

							String parentId=switchOnWindow(driver);
							if (parentId!=null) {	
								ThreadSleep(3000);

								if (click(driver, ip.getadvancedFilterToggle(projectName, 20), "advanced filter toggle link", action.SCROLLANDBOOLEAN)) {
									ThreadSleep(2000);
									log(LogStatus.INFO,"successfully clicked on advanced filter toggle link",YesNo.No);
									ele=cp.getAdvancedFilteDropdowns(projectName, PageLabel.Status.toString(), 10);
									String value = "Open and Completed activities";
									if (selectVisibleTextFromDropDown(driver, ele, "status", value )) {
										ele=cp.clearApplyButtonOnAdvancedFilter(projectName, "Apply", 10);
										if (click(driver, ele, "apply", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO,"successfully clicked on apply button",YesNo.No);
										}else {
											sa.assertTrue(false,"Not Able to Click on Apply Button on Search Activities and Attachments For : "+impactedContact[j]);
											log(LogStatus.SKIP,"Not Able to Click on Apply Button on Search Activities and Attachments For : "+impactedContact[j],YesNo.Yes);

										}
									}else {
										sa.assertTrue(false,"Not Able to select status : "+value);
										log(LogStatus.SKIP,"Not Able to select status : "+value,YesNo.Yes);

									}
									ThreadSleep(3000);
								 ele=ip.getAllLinksOnSubTab(projectName, PageName.TestCustomObjectPage, RelatedTab.Activities, task, action.SCROLLANDBOOLEAN, 10);
								

									if (ele==null) {
										log(LogStatus.INFO,task+" link is not present on Search Activities and Attachments For : "+impactedContact[j],YesNo.No);
									} else {
										sa.assertTrue(false,task+" link Should not be present on Search Activities and Attachments For : "+impactedContact[j]);
										log(LogStatus.SKIP,task+"  link is Should not be present on Search Activities and Attachments For : "+impactedContact[j],YesNo.Yes);
									}
								}else {
									log(LogStatus.ERROR,  "could not click on advanced filter toggle link", YesNo.Yes);
									sa.assertTrue(false, "could not click on advanced filter toggle link");
								}
								

								driver.close();
								driver.switchTo().window(parentId);
							}else {
								log(LogStatus.ERROR,  "could not find new window to switch after clicking on View All Link on Load More Past Activities For : "+impactedContact[j], YesNo.Yes);
								sa.assertTrue(false, "could not find new window to switch after clicking on View All Link on Load More Past Activities For : "+impactedContact[j]);
							}

						}else {
							sa.assertTrue(false,"Not Able to click on View All Link on Load More Past Activities For : "+impactedContact[j]);
							log(LogStatus.SKIP,"Not Able to click on View All Link on Load More Past Activities For : "+impactedContact[j],YesNo.Yes);

						}

					}


				} else {
					sa.assertTrue(false,"Item Not Found : "+impactedContact[j]+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+impactedContact[j]+" For : "+tabName,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+impactedContact[j]);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+impactedContact[j],YesNo.Yes);
			}


			switchToDefaultContent(driver);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc058_1_RestoreStandardTaskSTTaskfrmContactFromRecycleBin_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		WebElement ele = null ;
	
		String contact4UpdatedName = Smoke_TaskContact4UpdatedName;
		String taskCustomObj2UdatedName = taskCustomObj2UpdatedName;
		
		TabName[] tabNames = {TabName.RecycleBinTab,TabName.RecycleBinTab};
		String[] names = {contact4UpdatedName,taskCustomObj2UdatedName};
		
		TabName tabName ;
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20);
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(2000);
					
					 ele=cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, name, 10);
					 if (clickUsingJavaScript(driver, ele, "Check box against : "+name, action.BOOLEAN)) {
						 log(LogStatus.INFO,"Click on checkbox for "+name,YesNo.No);
						 
						 ThreadSleep(1000);
							//					scn.nextLine();
						 ele=cp.getRestoreButtonOnRecycleBin(projectName, 10);
						 if (clickUsingJavaScript(driver, ele, "Restore Button : "+name, action.BOOLEAN)) {
							 log(LogStatus.INFO,"Click on Restore Button for "+name,YesNo.No);
							 ThreadSleep(1000);
//							 
//							 ele = cp.getAnyMsg(projectName, tp.restoreItemMsg(projectName, name), 20);
//								if (ele!=null) {
//									log(LogStatus.SKIP,"Restore Msg Ele Found and Verified : "+name,YesNo.No);
//
//								} else {
//									sa.assertTrue(false,"Restore Task Msg Ele not Found : "+name);
//									log(LogStatus.SKIP,"Restore Task Msg Ele not Found : "+name,YesNo.Yes);
//
//								}
								
						} else {
							sa.assertTrue(false,"Not Able to Click on Restore Button for "+name);
							log(LogStatus.SKIP,"Not Able to Click on Restore Button for "+name,YesNo.Yes);
						}
						 
					} else {
						sa.assertTrue(false,"Not Able to Click on checkbox for "+name);
						log(LogStatus.SKIP,"Not Able to Click on checkbox for "+name,YesNo.Yes);
					}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc058_2_VerifySortingInActivitiesTab(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String navatarCTCon1 = Smoke_TaskContact4UpdatedName;
		
		WebElement ele ;
		String parentID=null;
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, navatarCTCon1, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+navatarCTCon1,YesNo.No);
				ThreadSleep(1000);
				
				if (ip.checkForActivitiesCount(projectName, PageName.Object1Page, "8", true, true, action.BOOLEAN, 10)) {
					log(LogStatus.INFO,"8 Activity Count VERIFY with Checked and Disabled",YesNo.No);
				} else {
					sa.assertTrue(false,"8 Activity Count NOT VERIFY with Checked and Disabled");
					log(LogStatus.SKIP,"8 Activity Count NOT VERIFY with Checked and Disabled",YesNo.Yes);
				}
				
				if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {
					ThreadSleep(1000);
					log(LogStatus.INFO,"click on View All Link on Load More Past Activities",YesNo.No);

					String parentId=switchOnWindow(driver);
					if (parentId!=null) {	
						ThreadSleep(3000);

						if (click(driver, ip.getadvancedFilterToggle(projectName, 20), "advanced filter toggle link", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(2000);
							log(LogStatus.INFO,"successfully clicked on advanced filter toggle link",YesNo.No);
							ele=cp.getAdvancedFilteDropdowns(projectName, PageLabel.Status.toString(), 10);
							String value = "Open and Completed activities";
							if (selectVisibleTextFromDropDown(driver, ele, "status", value )) {
								ele=cp.clearApplyButtonOnAdvancedFilter(projectName, "Apply", 10);
								if (click(driver, ele, "apply", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully clicked on apply button",YesNo.No);
									ThreadSleep(2000);
									
									SoftAssert saa = cp.performSortingCheckOnRelatedTab(projectName, PageName.Object2Page, RelatedTab.Activities);
									sa.combineAssertions(saa);
									
								}else {
									sa.assertTrue(false,"Not Able to Click on Apply Button on Search Activities and Attachments");
									log(LogStatus.SKIP,"Not Able to Click on Apply Button on Search Activities and Attachments",YesNo.Yes);

								}
							}else {
								sa.assertTrue(false,"Not Able to select status : "+value);
								log(LogStatus.SKIP,"Not Able to select status : "+value,YesNo.Yes);

							}


						}else {
							log(LogStatus.ERROR,  "could not click on advanced filter toggle link", YesNo.Yes);
							sa.assertTrue(false, "could not click on advanced filter toggle link");
						}
						

						driver.close();
						driver.switchTo().window(parentId);
					}else {
						log(LogStatus.ERROR,  "could not find new window to switch after clicking on View All Link on Load More Past Activities", YesNo.Yes);
						sa.assertTrue(false, "could not find new window to switch after clicking on View All Link on Load More Past Activities");
					}

				}else {
					sa.assertTrue(false,"Not Able to click on View All Link on Load More Past Activities");
					log(LogStatus.SKIP,"Not Able to click on View All Link on Load More Past Activities",YesNo.Yes);

				}
				
				

			} else {
				sa.assertTrue(false,"Contact Not Found : "+navatarCTCon1);
				log(LogStatus.SKIP,"Contact Not Found : "+navatarCTCon1,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc059_VerifyFilterAvailableInActivityTimeline(String projectName) {
		
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String navatarCTCon1 = Smoke_TaskContact3FName+" "+Smoke_TaskContact3LName;
		
		WebElement ele ;
		String parentID=null;
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, navatarCTCon1, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+navatarCTCon1,YesNo.No);
				ThreadSleep(1000);
				//					scn.nextLine();
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Filter, 10);
				if (click(driver, ele, ActivityTimeLineItem.Filter.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Filter,YesNo.No);	
					ThreadSleep(1000);
					//					scn.nextLine();
					cp.verifyUIOfActivityTimeLineFilter(projectName);
					
					ele = cp.getCancelBtnAtActivityTimeLineFilterPopuP(projectName, 10);
					if (click(driver, ele, "cancel Btn At ActivityTimeLine Filter PopuP", action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on cancel Btn At ActivityTimeLine Filter PopuP",YesNo.No);
						ThreadSleep(5000);
						ele = cp.getCancelBtnAtActivityTimeLineFilterPopuP(projectName, 5);
						if (ele==null) {
							log(LogStatus.ERROR, "ActivityTimeLine Filter PopuP is closed after clicking Cancel Btn",YesNo.No);
						} else {
							sa.assertTrue(false,"ActivityTimeLine Filter PopuP is not closed after clicking Cancel Btn");
							log(LogStatus.ERROR, "ActivityTimeLine Filter PopuP is not closed after clicking Cancel Btn",YesNo.Yes);
						}
						
						ele = tp.getActivityTimeLineItem2(projectName, PageName.Object1Page, ActivityTimeLineItem.Filter, 10);
						if (click(driver, ele, ActivityTimeLineItem.Filter.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.Filter,YesNo.No);	
							ThreadSleep(3000);
							ActivityTypes[] activityTypes= {ActivityTypes.Logged_Calls,ActivityTypes.Email,ActivityTypes.Tasks};
							cp.clickonRadioCheckBoxOnActivityTimeLineFilter(projectName, DateRange.Last_7_Days, ActivitiesToShow.All_Activities, activityTypes);
							
							ele = cp.getApplyBtnAtActivityTimeLineFilterPopuP(projectName, 10);
							if (click(driver, ele, "Apply Btn At ActivityTimeLine Filter PopuP", action.BOOLEAN)) {
								log(LogStatus.INFO, "Click on Apply Btn At ActivityTimeLine Filter PopuP",YesNo.No);
								ThreadSleep(5000);
								
								

									String task = Smoke_STDTask2OnSubject;
									ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
									if (ele!=null) {
										log(LogStatus.INFO,task+" link present on Next Activity Timeline For : "+navatarCTCon1,YesNo.No);	
									} else {
										sa.assertTrue(false,task+" link Should be present on Next Activity Timeline For : "+navatarCTCon1);
										log(LogStatus.SKIP,task+" link Should be present onNext  Activity Timeline For : "+navatarCTCon1,YesNo.Yes);
									}
									
									 task = Smoke_Task20Subject;
									ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task, SubjectElement.SubjectLink, 10);
									if (ele!=null) {
										log(LogStatus.INFO,task+" link present on Past Activity Timeline For : "+navatarCTCon1,YesNo.No);	
									} else {
										sa.assertTrue(false,task+" link Should be present on Past Activity Timeline For : "+navatarCTCon1);
										log(LogStatus.SKIP,task+" link Should be present on Past  Activity Timeline For : "+navatarCTCon1,YesNo.Yes);
									}
									
									 task = Smoke_TaskSendLetterSubject;
									ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task, SubjectElement.SubjectLink, 10);
									if (ele!=null) {
										log(LogStatus.INFO,task+" link present on Past Activity Timeline For : "+navatarCTCon1,YesNo.No);	
									} else {
										sa.assertTrue(false,task+" link Should be present on Past Activity Timeline For : "+navatarCTCon1);
										log(LogStatus.SKIP,task+" link Should be present on Past  Activity Timeline For : "+navatarCTCon1,YesNo.Yes);
									}
									

								//					scn.nextLine();
							} else {
								sa.assertTrue(false,"Not Able to Click on Apply Btn At ActivityTimeLine Filter PopuP");
								log(LogStatus.ERROR, "Not Able to Click on Apply Btn At ActivityTimeLine Filter PopuP",YesNo.Yes);
							}
							
						}else {
							sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Filter);
							log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Filter,YesNo.Yes);

						}
						
					} else {
						sa.assertTrue(false,"Not Able to Click on cancel Btn At ActivityTimeLine Filter PopuP");
						log(LogStatus.ERROR, "Not Able to Click on cancel Btn At ActivityTimeLine Filter PopuP",YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on "+ActivityTimeLineItem.Filter);
					log(LogStatus.ERROR, "Not Able to Click on "+ActivityTimeLineItem.Filter,YesNo.Yes);

				}
				
				
				

			} else {
				sa.assertTrue(false,"Contact Not Found : "+navatarCTCon1);
				log(LogStatus.SKIP,"Contact Not Found : "+navatarCTCon1,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}

		ThreadSleep(5000);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc060_1_DisableRollUpActivitiesToContactPrimaryInstitutionSetting(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String contactName = Smoke_MTContact5FName+" "+Smoke_MTContact5LName;
		String task = Smoke_DealProgressReviewMeetingSubject;
		WebElement ele ;

		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);
				//					scn.nextLine();
				
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Task_with_Multiple_Associations, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New task Multiple Asociation Button",  YesNo.Yes);
					//					scn.nextLine();
					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), task, "Subject", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

						boolean flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, meetingCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+meetingCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);

							if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
								ThreadSleep(1000);
								ele = cp.getCreatedConfirmationMsg(projectName, 15);
								if (ele!=null) {
									String actualValue = ele.getText().trim();
									String expectedValue=tp.taskCreatesMsg(projectName, task);
									if (actualValue.contains(expectedValue)) {
										log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

									} else {
										log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
										BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
									}
								} else {
									sa.assertTrue(false,"Created Task Msg Ele not Found");
									log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

								}

								clickUsingJavaScript(driver, cp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);
								
								ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
								click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
								ThreadSleep(2000);
								
								ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
								String taskMessage = BasePageErrorMessage.UpcomingTaskMsgAbout+" "+meetingCustomObj1Name;				

								if (ele!=null) {
									log(LogStatus.INFO,task+" link present on Activity Timeline For : "+contactName,YesNo.No);	
									lp.verifyActivityAtNextStep2(projectName, PageName.Object3Page, contactName,task, taskMessage, DueDate.No_due_date.toString(), false, "", false, "", 10);
								} else {
									sa.assertTrue(false,task+" link Should be present on Activity Timeline For : "+contactName);
									log(LogStatus.SKIP,task+" link Should be present on Activity Timeline For : "+contactName,YesNo.Yes);
								}

							}
							else {
								log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not created" );
							}

						} else {
							sa.assertTrue(false,"Not Able to Select "+meetingCustomObj1Name+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+meetingCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}


					}
					else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}


				}
				else {
					log(LogStatus.ERROR, "not able to click on new task button", YesNo.Yes);
					sa.assertTrue(false,"not able to click on new task button" );
				}


			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact Not Found : "+contactName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		String entity = Smoke_MTINS6Name;
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, entity, 20)) {
				log(LogStatus.INFO,"Clicked on  : "+entity,YesNo.No);
				ThreadSleep(1000);
				
				ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
				click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
					String taskMessage = BasePageErrorMessage.UpcomingTaskMsg+" "+contactName+" about "+meetingCustomObj1Name;			

					if (ele!=null) {
						log(LogStatus.INFO,task+" link present on Activity Timeline For : "+entity,YesNo.No);	
						lp.verifyActivityAtNextStep2(projectName, PageName.Object3Page, entity,task, taskMessage, DueDate.No_due_date.toString(), false, "", false, "", 10);
					} else {
						sa.assertTrue(false,task+" link Should be present on Activity Timeline For : "+entity);
						log(LogStatus.SKIP,task+" link Should be present on Activity Timeline For : "+entity,YesNo.Yes);
					}

			} else {
				sa.assertTrue(false," Not Found : "+entity);
				log(LogStatus.SKIP," Not Found : "+entity,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc060_2_DisableRollUpActivitiesToContactPrimaryInstitutionSetting(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		NavatarSetupPageBusinessLayer nsp = new NavatarSetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String parentID=null;
		//lp.switchToClassic();
		String mode="Lightning";
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "New Window is Open",YesNo.Yes);
				if(setup.searchStandardOrCustomObject("", mode, object.Activity_Setting)) {
					log(LogStatus.INFO, "Able to Search/Click on "+object.Activity_Setting,YesNo.No);	
					ThreadSleep(10000);
					switchToFrame(driver, 30, setup.getEditPageLayoutFrame_Lighting(60));

					/////////////////
					String checkBox = NavatarSetUpPageErrorMessage.RollUpActivitiesToContactPrimaryAccount;
					if (isSelected(driver,nsp.getRollUpActivitiesToContactPrimaryAccountCheckBox(projectName, 10),"CheckBox For : "+checkBox)) {
						log(LogStatus.INFO, "Checked "+checkBox, YesNo.No);
						log(LogStatus.INFO, "Going to UnCheck : "+checkBox, YesNo.No);

						if (click(driver,nsp.getRollUpActivitiesToContactPrimaryAccountCheckBox(projectName, 10),"CheckBox For : "+checkBox, action.BOOLEAN)) {
							appLog.error("Clicked on checkBox:  "+checkBox);
							ThreadSleep(2000);
							if (click(driver,nsp.getSubmitBtn(projectName, 10),"CheckBox For : "+checkBox, action.BOOLEAN)) {
								appLog.error("Clicked on Submit Btn");
								ThreadSleep(2000);
								refresh(driver);
								switchToDefaultContent(driver);
								//////////////////


								if(setup.searchStandardOrCustomObject("", mode, object.Activity_Setting)) {
									log(LogStatus.INFO, "Able to Search/Click on "+object.Activity_Setting,YesNo.No);	
									switchToDefaultContent(driver);
									ThreadSleep(5000);
									switchToFrame(driver, 30, setup.getEditPageLayoutFrame_Lighting(60));
									if (!isSelected(driver,nsp.getRollUpActivitiesToContactPrimaryAccountCheckBox(projectName, 10),"CheckBox For : "+checkBox)) {
										appLog.info("Checked is Unchecked : "+checkBox);
									} else {
										appLog.error("CheckBox Should be UnChecked : "+checkBox);
										sa.assertTrue(false,"CheckBox Should be UnChecked : "+checkBox);
										log(LogStatus.FAIL,"CheckBox Should be UnChecked : "+checkBox,YesNo.Yes);
									}


								}else {
									log(LogStatus.ERROR, "Not Able to Search/Click on "+object.Activity_Setting,YesNo.Yes);
									sa.assertTrue(false, "Not Able to Search/Click on "+object.Activity_Setting);	
								}



							} else {
								appLog.error("Not Able to Click on Submit Btn");
								sa.assertTrue(false, "Not Able to Click on Submit Btn");
								log(LogStatus.FAIL, "Not Able to Click on Submit Btn",YesNo.Yes);
							}


						} else {
							appLog.error("Not Able to Click on Checkbox : "+checkBox);
							sa.assertTrue(false, "Not Able to Click on Checkbox : "+checkBox);
							log(LogStatus.FAIL, "Not Able to Click on Checkbox : "+checkBox,YesNo.Yes);
						}

					} else {


						appLog.info(checkBox+" already UnChecked");	
						sa.assertTrue(false, checkBox+" already UnChecked");
						log(LogStatus.SKIP, checkBox+" already UnChecked", YesNo.Yes);


					}

					///////////////////
				}else {
					log(LogStatus.ERROR, "Not Able to Search/Click on "+object.Activity_Setting,YesNo.Yes);
					sa.assertTrue(false, "Not Able to Search/Click on "+object.Activity_Setting);	
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR, "No New Window is Open",YesNo.Yes);
				sa.assertTrue(false, "No New Window is Open");
			}
			
		}else {
			log(LogStatus.ERROR, "Not Able to click on Setup",YesNo.Yes);
			sa.assertTrue(false, "Not Able to click on Setup");	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
}
		
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc060_3_DisableRollUpActivitiesToContactPrimaryInstitutionSetting(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String task = Smoke_DealProgressReviewMeetingSubject;
		WebElement ele ;
		String contactName = Smoke_MTContact5FName+" "+Smoke_MTContact5LName;

		switchToDefaultContent(driver);
		String entity = Smoke_MTINS6Name;
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, entity, 20)) {
				log(LogStatus.INFO,"Clicked on  : "+entity,YesNo.No);
				ThreadSleep(1000);
			
				ele = cp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
				click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
				String taskMessage = BasePageErrorMessage.UpcomingTaskMsg+" "+contactName+" about "+meetingCustomObj1Name;			

				if (ele!=null) {
					log(LogStatus.INFO,task+" link present on Activity Timeline For : "+entity,YesNo.No);	
					lp.verifyActivityAtNextStep2(projectName, PageName.Object3Page, entity,task, taskMessage, DueDate.No_due_date.toString(), false, "", false, "", 10);
				} else {
					sa.assertTrue(false,task+" link Should be present on Activity Timeline For : "+entity);
					log(LogStatus.SKIP,task+" link Should be present on Activity Timeline For : "+entity,YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false," Not Found : "+entity);
				log(LogStatus.SKIP," Not Found : "+entity,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc061_VerifyNewTaskwithMultipleAssociationsButtonAndSettingImpact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String contactName = Smoke_MTContact5FName+" "+Smoke_MTContact5LName;
		String task = Smoke_S1TestSubject;
		WebElement ele ;

		String parentID=null;
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);

				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Task_with_Multiple_Associations, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New task with multiple association Button",  YesNo.Yes);
					ThreadSleep(1000);

					ele = cp.getHeaderTextForPage(projectName, PageName.Object2Page,PageLabel.New_Task.toString(), action.BOOLEAN, 20);
					if (ele!=null) {
						log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
					} else {
						sa.assertTrue(false,"New Task PopUp is not opened");
						log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
					}

					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), task, "Subject", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

						boolean flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, meetingCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+meetingCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);

							if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
								ThreadSleep(1000);


								ele = cp.getCreatedConfirmationMsg(projectName, 15);
								if (ele!=null) {
									String actualValue = ele.getText().trim();
									String expectedValue=tp.taskCreatesMsg(projectName, task);
									if (actualValue.contains(expectedValue)) {
										log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

									} else {
										log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
										BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
									}
								} else {
									sa.assertTrue(false,"Created Task Msg Ele not Found");
									log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

								}
								

								clickUsingJavaScript(driver, cp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);
								
								ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
								click(driver, ele, "More Steps" ,action.BOOLEAN);
								ThreadSleep(2000);
								
								ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
								click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
								ThreadSleep(2000);
								
								ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
								String taskMessage = BasePageErrorMessage.UpcomingTaskMsgAbout+" "+meetingCustomObj1Name;				

								if (ele!=null) {
									log(LogStatus.INFO,task+" link present on Activity Timeline For : "+contactName,YesNo.No);	
									lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, contactName,task, taskMessage, DueDate.No_due_date.toString(), false, "", false, "", 10);
								} else {
									sa.assertTrue(false,task+" link Should be present on Activity Timeline For : "+contactName);
									log(LogStatus.SKIP,task+" link Should be present on Activity Timeline For : "+contactName,YesNo.Yes);
								}


							}
							else {
								log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not created" );
							}

						} else {
							sa.assertTrue(false,"Not Able to Select "+meetingCustomObj1Name+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+meetingCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}


					}
					else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}


				}
				else {
					log(LogStatus.ERROR, "not able to click on new task button", YesNo.Yes);
					sa.assertTrue(false,"not able to click on new task button" );
				}

			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact Not Found : "+contactName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		String entity = Smoke_MTINS6Name;
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, entity, 20)) {
				log(LogStatus.INFO,"Clicked on  : "+entity,YesNo.No);
				ThreadSleep(1000);
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
				
				if (ele==null) {
					log(LogStatus.INFO,task+" link is not present on Activity Timeline For : "+entity,YesNo.No);	
					} else {
					sa.assertTrue(false,task+" link Should not be present on Activity Timeline For : "+entity);
					log(LogStatus.SKIP,task+" link Should not be present on Activity Timeline For : "+entity,YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false," Not Found : "+entity);
				log(LogStatus.SKIP," Not Found : "+entity,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc062_VerifyLogACallWithMultipleAssociationsButtonAndSettingImpact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String contactName = Smoke_MTContact5FName+" "+Smoke_MTContact5LName;
		String task = Smoke_S2TestSubject;
		String date = todaysDate;
		ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask23", excelLabel.Due_Date);
		WebElement ele ;

		String parentID=null;
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);
				
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Clicked on Log A Call Multiple Asociation Button",  YesNo.Yes);
						ThreadSleep(1000);

						ele = cp.getHeaderTextForPage(projectName, PageName.Object2Page,PageLabel.New_Task.toString(), action.BOOLEAN, 20);
						if (ele!=null) {
							log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
						} else {
							sa.assertTrue(false,"New Task PopUp is not opened");
							log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
						}

						try {
							ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20).clear();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), task, "Subject", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

							boolean flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, meetingCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.INFO,"Selected "+meetingCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);

								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
									ThreadSleep(1000);


									ele = cp.getCreatedConfirmationMsg(projectName, 15);
									if (ele!=null) {
										String actualValue = ele.getText().trim();
										String expectedValue=tp.taskCreatesMsg(projectName, task);
										if (actualValue.contains(expectedValue)) {
											log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

										} else {
											log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
											BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
										}
									} else {
										sa.assertTrue(false,"Created Task Msg Ele not Found");
										log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

									}
									
									clickUsingJavaScript(driver, cp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);
									
									ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
									click(driver, ele, "More Steps" ,action.BOOLEAN);
									ThreadSleep(2000);
									
									ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
									click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
									ThreadSleep(2000);
									
									ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task, SubjectElement.SubjectLink, 10);
									String taskMessage = BasePageErrorMessage.YouLoggedACallAbout+" "+meetingCustomObj1Name;				

									if (ele!=null) {
										log(LogStatus.INFO,task+" link present on Activity Timeline For : "+contactName,YesNo.No);	
										lp.verifyActivityAtPastStep2(projectName, PageName.Object2Page, contactName,task, taskMessage, date, false, "", false, "", 10);
									} else {
										sa.assertTrue(false,task+" link Should be present on Activity Timeline For : "+contactName);
										log(LogStatus.SKIP,task+" link Should be present on Activity Timeline For : "+contactName,YesNo.Yes);
									}


								}
								else {
									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not created" );
								}

							} else {
								sa.assertTrue(false,"Not Able to Select "+meetingCustomObj1Name+" For Label "+PageLabel.Related_Associations);
								log(LogStatus.SKIP,"Not Able to Select "+meetingCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

							}


						}
						else {
							log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
						}


					}
					else {
						log(LogStatus.ERROR, "not able to click on Log A Call button", YesNo.Yes);
						sa.assertTrue(false,"not able to click on Log A Call button" );
					}


			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact Not Found : "+contactName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		String entity = Smoke_MTINS6Name;
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, entity, 20)) {
				log(LogStatus.INFO,"Clicked on  : "+entity,YesNo.No);
				ThreadSleep(1000);
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Past, task, SubjectElement.SubjectLink, 10);
				
				if (ele==null) {
					log(LogStatus.INFO,task+" link is not present on Activity Timeline For : "+entity,YesNo.No);	
					} else {
					sa.assertTrue(false,task+" link Should not be present on Activity Timeline For : "+entity);
					log(LogStatus.SKIP,task+" link Should not be present on Activity Timeline For : "+entity,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false," Not Found : "+entity);
				log(LogStatus.SKIP," Not Found : "+entity,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc063_VerifyNewMeetingButtonAndSettingImpact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String contactName = Smoke_MTContact5FName+" "+Smoke_MTContact5LName;
		String task = Smoke_S3TestSubject;
		WebElement ele ;

		String parentID=null;
		if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object2Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, contactName, 20)) {
				log(LogStatus.INFO,"Clicked on Contact : "+contactName,YesNo.No);
				ThreadSleep(1000);
				
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Meeting, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Meeting.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New Meeting Button",  YesNo.Yes);
						ThreadSleep(1000);

						ele = cp.getHeaderTextForPage(projectName, PageName.Object2Page,PageLabel.New_Task.toString(), action.BOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
						} else {
							sa.assertTrue(false,"New Task PopUp is not opened");
							log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
						}

						String[][] dropDownLabelWithValues = {{PageLabel.Meeting_Type.toString(),Smoke_S3TestMeetingType}};
						if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.Object2Page, task, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {	log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

							boolean flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, meetingCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
							if (flag) {
								log(LogStatus.INFO,"Selected "+meetingCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);

								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
									ThreadSleep(1000);


									ele = cp.getCreatedConfirmationMsg(projectName, 15);
									if (ele!=null) {
										String actualValue = ele.getText().trim();
										String expectedValue=tp.taskCreatesMsg(projectName, task);
										if (actualValue.contains(expectedValue)) {
											log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

										} else {
											log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
											BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
										}
									} else {
										sa.assertTrue(false,"Created Task Msg Ele not Found");
										log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

									}

									clickUsingJavaScript(driver, cp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);
									
									ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
									click(driver, ele, "More Steps" ,action.BOOLEAN);
									ThreadSleep(2000);
									
									ele = tp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Expand_All, 10);
									click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
									ThreadSleep(2000);
									
									ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
									String taskMessage = BasePageErrorMessage.UpcomingTaskMsgAbout+" "+meetingCustomObj1Name;				

									if (ele!=null) {
										log(LogStatus.INFO,task+" link present on Activity Timeline For : "+contactName,YesNo.No);	
										lp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, contactName,task, taskMessage, DueDate.No_due_date.toString(), false, "", false, "", 10);
									} else {
										sa.assertTrue(false,task+" link Should be present on Activity Timeline For : "+contactName);
										log(LogStatus.SKIP,task+" link Should be present on Activity Timeline For : "+contactName,YesNo.Yes);
									}

								}
								else {
									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not created" );
								}

							} else {
								sa.assertTrue(false,"Not Able to Select "+meetingCustomObj1Name+" For Label "+PageLabel.Related_Associations);
								log(LogStatus.SKIP,"Not Able to Select "+meetingCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

							}


						}
						else {
							log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
						}


					}
					else {
						log(LogStatus.ERROR, "not able to click on New Meeting button", YesNo.Yes);
						sa.assertTrue(false,"not able to click on New Meeting button" );
					}


			} else {
				sa.assertTrue(false,"Contact Not Found : "+contactName);
				log(LogStatus.SKIP,"Contact Not Found : "+contactName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		String entity = Smoke_MTINS6Name;
		if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object1Tab,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, entity, 20)) {
				log(LogStatus.INFO,"Clicked on  : "+entity,YesNo.No);
				ThreadSleep(1000);
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
				
				if (ele==null) {
					log(LogStatus.INFO,task+" link is not present on Activity Timeline For : "+entity,YesNo.No);	
					} else {
					sa.assertTrue(false,task+" link Should not be present on Activity Timeline For : "+entity);
					log(LogStatus.SKIP,task+" link Should not be present on Activity Timeline For : "+entity,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false," Not Found : "+entity);
				log(LogStatus.SKIP," Not Found : "+entity,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc064_EnableContactTransferSettingFromNavatarSetup(String projectName) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer ctt = new NavatarSetupPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		NavatarSetupPageBusinessLayer nsp = new NavatarSetupPageBusinessLayer(driver);
		String environment="";
		String mode="";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		appLog.info("Login with User");
		


		//  office location 
		
		if (bp.clickOnTab(projectName, TabName.NavatarSetup)) {
			appLog.info("Clicked on Navatar Set Up Tab");
			if (ctt.clickOnNavatarSetupSideMenusTab(projectName, NavatarSetupSideMenuTab.OfficeLocations)) {
				appLog.error("Clicked on Office Locations Tab");

				// 1st
				ThreadSleep(2000);
				if (!isSelected(driver, ctt.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,
						NavatarSetupSideMenuTab.OfficeLocations, EditViewMode.View, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled CheckBox")) {
					log(LogStatus.INFO, "Enable Office Locations is Unchecked", YesNo.No);
				} else {
					log(LogStatus.SKIP, "Enable Office Locations is Already checked Going to Uncheck Office ocation", YesNo.Yes);
					
					if (click(driver, ctt.getEditButtonforNavatarSetUpSideMenuTab(projectName,
							NavatarSetupSideMenuTab.OfficeLocations, 10), "Edit Button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button for Office Locations Tab", YesNo.No);
						ThreadSleep(2000);
						
						if (click(driver,
								ctt.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(projectName,
										NavatarSetupSideMenuTab.OfficeLocations, EditViewMode.Edit, 10),
								"ChecK Box", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Enable office Location CheckBox", YesNo.No);
							ThreadSleep(2000);
							// 3rd
							if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(projectName,
									NavatarSetupSideMenuTab.OfficeLocations, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
								ThreadSleep(10000);
								log(LogStatus.INFO, "Clicked on Save Button for Office Locations Tab", YesNo.No);

								if (!isSelected(driver,
										ctt.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,
												NavatarSetupSideMenuTab.OfficeLocations, EditViewMode.View, ClickOrCheckEnableDisableCheckBox.Click, 10),
										"Enabled CheckBox")) {
									log(LogStatus.INFO, "Enable Office Locations is UnChecked", YesNo.No);
								} else {
									sa.assertTrue(false, "Enable Office Locations Should be Unchecked");
									log(LogStatus.SKIP, "Enable Office Locations Should be Unchecked", YesNo.Yes);
								}

							} else {
								sa.assertTrue(false, "Not Able to Click on Save Button for Office Locations Tab");
								log(LogStatus.SKIP, "Not Able to Click on Save Button for Office Locations Tab", YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Enable office Location CheckBox");
							log(LogStatus.SKIP, "Not Able to Click on Enable office Location CheckBox", YesNo.Yes);
						}
						
						}else {
							sa.assertTrue(false, "Not Able to Click on Edit Button for Office Locations Tab");
							log(LogStatus.SKIP, "Not Able to Click on Edit Button for Office Locations Tab", YesNo.Yes);	
						}
				}
				
				
				
				
			} else {

				sa.assertTrue(false, "Not Able to Click on Office Locations Tab");
				log(LogStatus.SKIP, "Not Able to Click on Office Locations Tab", YesNo.Yes);
			}
			
		} else {
		appLog.error("Not Able to Click on Navatar Set Up Tab");
		sa.assertTrue(false, "Not Able to Click on Navatar Set Up Tab");
		log(LogStatus.SKIP, "Not Able to Click on Navatar Set Up Tab", YesNo.Yes);
	}
	

		// Contact Transfer
		switchToDefaultContent(driver);
		if (bp.clickOnTab(projectName, TabName.NavatarSetup)) {
			appLog.info("Clicked on Navatar Set Up Tab");

			if (ctt.clickOnNavatarSetupSideMenusTab(projectName, NavatarSetupSideMenuTab.ContactTransfer)) {
				appLog.error("Clicked on Contact Transfer Tab");
				if (click(driver, ctt.getEditButtonforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, 10), "Edit Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);
					ThreadSleep(2000);
					if (!isSelected(driver, ctt.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled CheckBox")) {

						log(LogStatus.INFO, "Enable Contact Transfer is Unchecked", YesNo.No);

						if (click(driver,ctt.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, 10),"Contact Trasfer CheckBox", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Enable Contact Transfer Box Checkbox", YesNo.No);
							ThreadSleep(2000);
							String keepActivitiesDefaultValue = ctt.keepActivitiesValue(projectName, KeepActivityEnum.OldInstitutionOnly);
							String selectIncludeActivitiesValue = ctt.includeActivitiesValue(projectName, InculdeActivityEnum.ContactOnly);
							if (selectVisibleTextFromDropDown(driver,ctt.getKeepActivitiesAtSelectList(projectName, EditViewMode.Edit, 10),keepActivitiesDefaultValue, keepActivitiesDefaultValue)) {
								log(LogStatus.INFO, "Selected Keep Activities related to : " + keepActivitiesDefaultValue,YesNo.No);
								ThreadSleep(1000);
								
								if (selectVisibleTextFromDropDown(driver,ctt.getIncludeActivitiesSelectList(projectName, EditViewMode.Edit, 10),selectIncludeActivitiesValue, selectIncludeActivitiesValue)) {
									log(LogStatus.INFO, "Selected Include Activities related to : " + keepActivitiesDefaultValue,YesNo.No);
									ThreadSleep(1000);
									
									if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(projectName, NavatarSetupSideMenuTab.ContactTransfer, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
										ThreadSleep(5000);
										log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
										ThreadSleep(10000);
											SoftAssert tsa = ctt.verifyingContactTransferTab(projectName, EditViewMode.View,CheckBox.Checked, keepActivitiesDefaultValue, selectIncludeActivitiesValue);
											sa.combineAssertions(tsa);
										
									} else {
										sa.assertTrue(false, "Not Able to Click on Save Button");
										log(LogStatus.SKIP, "Not Able to Click on Save Button", YesNo.Yes);
									}
									
									
								} else {
									sa.assertTrue(false,"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue);
									log(LogStatus.SKIP,"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue,YesNo.Yes);

								}
									
								
								
							} else {
								sa.assertTrue(false,"Not Able to Select Keep Activities related to : " + keepActivitiesDefaultValue);
								log(LogStatus.SKIP,"Not Able to Select Keep Activities related to : " + keepActivitiesDefaultValue,YesNo.Yes);

							}


						} else {
							sa.assertTrue(false, "Not Able to Click on Enable Contact Transfer Checkbox");
							log(LogStatus.SKIP, "Not Able to Click on Enable Contact Transfer Checkbox", YesNo.Yes);
						}

					} else {
						log(LogStatus.SKIP, "Enable Contact Transfer is Already checked", YesNo.Yes);
						
						String keepActivitiesDefaultValue = ctt.keepActivitiesValue(projectName, KeepActivityEnum.OldInstitutionOnly);
						String selectIncludeActivitiesValue = ctt.includeActivitiesValue(projectName, InculdeActivityEnum.ContactOnly);
						if (selectVisibleTextFromDropDown(driver,ctt.getKeepActivitiesAtSelectList(projectName, EditViewMode.Edit, 10),keepActivitiesDefaultValue, keepActivitiesDefaultValue)) {
							log(LogStatus.INFO, "Selected Keep Activities related to : " + keepActivitiesDefaultValue,YesNo.No);
							ThreadSleep(1000);
							
							if (selectVisibleTextFromDropDown(driver,ctt.getIncludeActivitiesSelectList(projectName, EditViewMode.Edit, 10),selectIncludeActivitiesValue, selectIncludeActivitiesValue)) {
								log(LogStatus.INFO, "Selected Include Activities related to : " + keepActivitiesDefaultValue,YesNo.No);
								ThreadSleep(1000);
								
								if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(projectName, NavatarSetupSideMenuTab.ContactTransfer, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
									ThreadSleep(5000);
									log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
									ThreadSleep(10000);
										SoftAssert tsa = ctt.verifyingContactTransferTab(projectName, EditViewMode.View,CheckBox.Checked, keepActivitiesDefaultValue, selectIncludeActivitiesValue);
										sa.combineAssertions(tsa);
									
								} else {
									sa.assertTrue(false, "Not Able to Click on Save Button");
									log(LogStatus.SKIP, "Not Able to Click on Save Button", YesNo.Yes);
								}
								
								
							} else {
								sa.assertTrue(false,"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue);
								log(LogStatus.SKIP,"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue,YesNo.Yes);

							}
								
							
							
						} else {
							sa.assertTrue(false,"Not Able to Select Keep Activities related to : " + keepActivitiesDefaultValue);
							log(LogStatus.SKIP,"Not Able to Select Keep Activities related to : " + keepActivitiesDefaultValue,YesNo.Yes);

						}
						
					}

				} else {
					appLog.error("Not Able to Click on Edit Button");
					sa.assertTrue(false, "Not Able to Click on Edit Button");
					log(LogStatus.SKIP, "Not Able to Click on Edit Button", YesNo.Yes);
				}
			} else {
				appLog.error("Not Able to Click on Contact Transfer Tab");
				sa.assertTrue(false, "Not Able to Click on Contact Transfer Tab");
				log(LogStatus.SKIP, "Not Able to Click on Contact Transfer Tab", YesNo.Yes);
			}
			
			

		} else {
			appLog.error("Not Able to Click on Navatar Set Up Tab");
			sa.assertTrue(false, "Not Able to Click on Navatar Set Up Tab");
			log(LogStatus.SKIP, "Not Able to Click on Navatar Set Up Tab", YesNo.Yes);
		}
		
		// Roll up activities to a contact's primary account
		switchToDefaultContent(driver);
		String parentID=null;
		//lp.switchToClassic();
		 mode="Lightning";
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "New Window is Open",YesNo.Yes);
				if(setup.searchStandardOrCustomObject("", mode, object.Activity_Setting)) {
					log(LogStatus.INFO, "Able to Search/Click on "+object.Activity_Setting,YesNo.No);	
					ThreadSleep(30000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 10, setup.getEditPageLayoutFrame_Lighting(10));

					String checkBox = NavatarSetUpPageErrorMessage.RollUpActivitiesToContactPrimaryAccount;
					if (isSelected(driver,nsp.getRollUpActivitiesToContactPrimaryAccountCheckBox(projectName, 10),"CheckBox For : "+checkBox)) {
						appLog.info("checkBox is already Checked  : "+checkBox);
						log(LogStatus.INFO,"checkBox is already Checked  : "+checkBox,YesNo.No);
					} else {
						
						log(LogStatus.INFO,"Going to Check CheckBox For : "+checkBox,YesNo.No);
						

						if (click(driver,nsp.getRollUpActivitiesToContactPrimaryAccountCheckBox(projectName, 10),"CheckBox For : "+checkBox, action.BOOLEAN)) {
							appLog.error("Clicked on checkBox:  "+checkBox);
							ThreadSleep(2000);
							 
							if (click(driver,nsp.getSubmitBtn(projectName, 10),"CheckBox For : "+checkBox, action.BOOLEAN)) {
								appLog.info("Clicked on Submit Btn");
								ThreadSleep(7000);
								
								refresh(driver);
								switchToDefaultContent(driver);
								//////////////////


								if(setup.searchStandardOrCustomObject("", mode, object.Activity_Setting)) {
									log(LogStatus.INFO, "Able to Search/Click on "+object.Activity_Setting,YesNo.No);	
									switchToDefaultContent(driver);
									ThreadSleep(5000);
									switchToFrame(driver, 10, setup.getEditPageLayoutFrame_Lighting(10));
									if (nsp.getRollUpActivitiesToContactPrimaryAccountCheckBox(projectName, 10)!=null) {
										appLog.info("checkBox is Checked  : "+checkBox);
									} else {
										appLog.error("CheckBox Shoulb be Checked : "+checkBox);
										sa.assertTrue(false,"CheckBox Shoulb be Checked : "+checkBox);
										log(LogStatus.FAIL,"CheckBox Shoulb be Checked : "+checkBox,YesNo.Yes);
									}


								}else {
									log(LogStatus.ERROR, "Not Able to Search/Click on "+object.Activity_Setting,YesNo.Yes);
									sa.assertTrue(false, "Not Able to Search/Click on "+object.Activity_Setting);	
								}
								
								
								
							} else {
								appLog.error("Not Able to Click on Submit Btn");
								sa.assertTrue(false, "Not Able to Click on Submit Btn");
								log(LogStatus.FAIL, "Not Able to Click on Submit Btn",YesNo.Yes);
							}
							

						} else {
							appLog.error("Not Able to Click on Checkbox : "+checkBox);
							sa.assertTrue(false, "Not Able to Click on Checkbox : "+checkBox);
							log(LogStatus.FAIL, "Not Able to Click on Checkbox : "+checkBox,YesNo.Yes);
						}
						
					}
					
					
				}else {
					log(LogStatus.ERROR, "Not Able to Search/Click on "+object.Activity_Setting,YesNo.Yes);
					sa.assertTrue(false, "Not Able to Search/Click on "+object.Activity_Setting);	
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR, "No New Window is Open",YesNo.Yes);
				sa.assertTrue(false, "No New Window is Open");
			}
			
		}else {
			log(LogStatus.ERROR, "Not Able to click on Setup",YesNo.Yes);
			sa.assertTrue(false, "Not Able to click on Setup");	
		}
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		ThreadSleep(3000);
		sa.assertAll();
		appLog.info("Pass");
	}
	
	@Parameters({"projectName"})
	@Test
	public void AASmokeTc065_1_TransferContactFromCTAccount1ToCTAccount2_Action(String projectName) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		appLog.info("Login with User");
		appLog.info("Going on Contact Tab");
		TabName tabName = TabName.Object2Tab;
		String navatarCTCon1= Smoke_MTContact5FName+" "+Smoke_MTContact5LName;
		if (bp.clickOnTab(projectName, tabName)) {
			if (cp.clickOnAlreadyCreatedItem(projectName, tabName, navatarCTCon1, 20)) {
				log(LogStatus.INFO, "Click on Created Contact : " + navatarCTCon1, YesNo.No);
				ThreadSleep(2000);
				if (cp.clickOnShowMoreActionDownArrow(projectName, PageName.Object2Page, ShowMoreActionDropDownList.Contact_Transfer, 10)) {
					log(LogStatus.INFO, "Clicked on Contact Transfer", YesNo.No);	

					if (cp.enteringValueforLegalNameOnContactTransferPage(projectName, Smoke_MTINS7Name, 10)) {
						log(LogStatus.PASS, "Able to Transfer Contact", YesNo.No);
						ThreadSleep(2000);
						refresh(driver);

						if (cp.fieldValueVerification(projectName, PageName.Object2Page, PageLabel.Account_Name, Smoke_MTINS7Name, 5)) {
							log(LogStatus.PASS, "Label Verified after contact Transfer", YesNo.Yes);	
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Label Not Verified after contact Transfer");
							log(LogStatus.FAIL, "Label Not Verified after contact Transfer", YesNo.Yes);
						}

						WebElement ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
						click(driver, ele, "More Steps" ,action.BOOLEAN);
						ThreadSleep(5000);

						ActivityType[] activities = {ActivityType.Next,ActivityType.Past,ActivityType.Next};
						int i=0;
						String meetingSubject;
						for (ActivityType activityType : activities) {

							;
							if (i==0) {
								meetingSubject=Smoke_S1TestSubject;	
							}else if(i==1){
								meetingSubject=Smoke_S2TestSubject;		
							}else {
								meetingSubject=Smoke_S3TestSubject;		
							}

							ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,activityType, meetingSubject, SubjectElement.SubjectLink, 10);
							if (ele!=null) {
								log(LogStatus.INFO,meetingSubject+" link present on Activity TimeLine : "+activityType+" For : "+navatarCTCon1,YesNo.No);	
							} else {
								sa.assertTrue(false,meetingSubject+" link Should be present on Activity TimeLine : "+activityType+" For : "+navatarCTCon1);
								log(LogStatus.SKIP,meetingSubject+" link Should be present on Activity TimeLine : "+activityType+" For : "+navatarCTCon1,YesNo.Yes);
							}
							i++;
							switchToDefaultContent(driver);
						}
					} else {
						sa.assertTrue(false, "Not Able to Transfer Contact");
						log(LogStatus.FAIL, "Not Able to Transfer Contact", YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Transfer");
					log(LogStatus.SKIP, "Not Able to Click on Contact Transfer", YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + navatarCTCon1);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + navatarCTCon1,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		appLog.info("Pass");
	}
		
	@Parameters({"projectName"})
	@Test
	public void AASmokeTc065_2_TransferContactFromCTAccount1ToCTAccount2_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele ;

		String meetingSubject="";


		TabName tabName =TabName.Object1Tab;
		String ctAccount ;
		String ctAccount1 = Smoke_MTINS6Name;
		String ctAccount2 = Smoke_MTINS7Name;
		int i=0;
		for (int j = 0; j < 2; j++) {
			
			if (j==0) {
				ctAccount = ctAccount1;
			} else {
				ctAccount = ctAccount2;
			}
		
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+ctAccount,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, ctAccount, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+ctAccount+" For : "+tabName,YesNo.No);
					ThreadSleep(1000);

					ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
					click(driver, ele, "More Steps" ,action.BOOLEAN);
					
					ThreadSleep(5000);
					
					meetingSubject=Smoke_S1TestSubject;	
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, meetingSubject, SubjectElement.SubjectLink, 10);
					if (ele!=null) {
						log(LogStatus.INFO,meetingSubject+" link present on Activity Timeline For : "+ctAccount,YesNo.No);	
					} else {
						sa.assertTrue(false,meetingSubject+" link Should be present on Activity Timeline For : "+ctAccount);
						log(LogStatus.SKIP,meetingSubject+" link Should be present on Activity Timeline For : "+ctAccount,YesNo.Yes);
					}

					meetingSubject=Smoke_S3TestSubject;	
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, meetingSubject, SubjectElement.SubjectLink, 10);
					if (ele!=null) {
						log(LogStatus.INFO,meetingSubject+" link present on Next Activity Timeline For : "+ctAccount,YesNo.No);	
					} else {
						sa.assertTrue(false,meetingSubject+" link Should be present on Next Activity Timeline For : "+ctAccount);
						log(LogStatus.SKIP,meetingSubject+" link Should be present onNext  Activity Timeline For : "+ctAccount,YesNo.Yes);
					}


					meetingSubject=Smoke_S2TestSubject;	
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, meetingSubject, SubjectElement.SubjectLink, 10);
					if (ele!=null) {
						log(LogStatus.INFO,meetingSubject+" link present on Past Activity Timeline For : "+ctAccount,YesNo.No);	
					} else {
						sa.assertTrue(false,meetingSubject+" link Should be present on Past Activity Timeline For : "+ctAccount);
						log(LogStatus.SKIP,meetingSubject+" link Should be present on Past Activity Timeline For : "+ctAccount,YesNo.Yes);
					}





					switchToDefaultContent(driver);

				} else {
					sa.assertTrue(false,"Item Not Found : "+ctAccount+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+ctAccount+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+ctAccount);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+ctAccount,YesNo.Yes);
			}
			
			
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc066_ChangeTheContactTransferSettingsToOldandNewInstitutionAndContactOnly(String projectName) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer ctt = new NavatarSetupPageBusinessLayer(driver);
		String environment="";
		String mode="";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		appLog.info("Login with User");
		if (bp.clickOnTab(projectName, TabName.NavatarSetup)) {
			appLog.info("Clicked on Navatar Set Up Tab");

			if (ctt.clickOnNavatarSetupSideMenusTab(projectName, NavatarSetupSideMenuTab.ContactTransfer)) {
				appLog.error("Clicked on Contact Transfer Tab");
				if (click(driver, ctt.getEditButtonforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, 10), "Edit Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);
					ThreadSleep(2000);
					//	String keepActivitiesDefaultValue = "Old and New Accounts";
					String keepActivitiesDefaultValue = ctt.keepActivitiesValue(projectName, KeepActivityEnum.OldAndNewInstitutionBoth);
				//	String selectIncludeActivitiesValue="Contact Only";
					String selectIncludeActivitiesValue = ctt.includeActivitiesValue(projectName, InculdeActivityEnum.ContactOnly);
					if (selectVisibleTextFromDropDown(driver,ctt.getKeepActivitiesAtSelectList(projectName, EditViewMode.Edit, 10),keepActivitiesDefaultValue, keepActivitiesDefaultValue)) {
						log(LogStatus.INFO, "Selected Keep Activities related to : " + keepActivitiesDefaultValue,YesNo.No);
						ThreadSleep(1000);
						if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(projectName, NavatarSetupSideMenuTab.ContactTransfer, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
							ThreadSleep(5000);
							log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
							ThreadSleep(10000);
								SoftAssert tsa = ctt.verifyingContactTransferTab(projectName, EditViewMode.View,CheckBox.Checked, keepActivitiesDefaultValue, selectIncludeActivitiesValue);
								sa.combineAssertions(tsa);
							
						} else {
							sa.assertTrue(false, "Not Able to Click on Save Button");
							log(LogStatus.SKIP, "Not Able to Click on Save Button", YesNo.Yes);
						}
						
					} else {
						sa.assertTrue(false,
								"Not Able to Select Keep Activities related to : " + keepActivitiesDefaultValue);
						log(LogStatus.SKIP,
								"Not Able to Select Keep Activities related to : " + keepActivitiesDefaultValue,
								YesNo.Yes);

					}
					

				} else {
					appLog.error("Not Able to Click on Edit Button");
					sa.assertTrue(false, "Not Able to Click on Edit Button");
					log(LogStatus.SKIP, "Not Able to Click on Edit Button", YesNo.Yes);
				}
			} else {
				appLog.error("Not Able to Click on Contact Transfer Tab");
				sa.assertTrue(false, "Not Able to Click on Contact Transfer Tab");
				log(LogStatus.SKIP, "Not Able to Click on Contact Transfer Tab", YesNo.Yes);
			}

		} else {
			appLog.error("Not Able to Click on Navatar Set Up Tab");
			sa.assertTrue(false, "Not Able to Click on Navatar Set Up Tab");
			log(LogStatus.SKIP, "Not Able to Click on Navatar Set Up Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		
		sa.assertAll();
		appLog.info("Pass");
	}
	
	@Parameters({"projectName"})
	@Test
	public void AASmokeTc067_1_TransferContactFromCTAccount2ToCTAccount3_Action(String projectName) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		appLog.info("Login with User");
		appLog.info("Going on Contact Tab");
		TabName tabName = TabName.Object2Tab;
		String navatarCTCon1= Smoke_MTContact5FName+" "+Smoke_MTContact5LName;
		if (bp.clickOnTab(projectName, tabName)) {
			if (cp.clickOnAlreadyCreatedItem(projectName, tabName, navatarCTCon1, 20)) {
				log(LogStatus.INFO, "Click on Created Contact : " + navatarCTCon1, YesNo.No);
				ThreadSleep(2000);
				if (cp.clickOnShowMoreActionDownArrow(projectName, PageName.Object2Page, ShowMoreActionDropDownList.Contact_Transfer, 10)) {
					log(LogStatus.INFO, "Clicked on Contact Transfer", YesNo.No);	
					ThreadSleep(1000);
					
					
					if (cp.enteringValueforLegalNameOnContactTransferPage(projectName, Smoke_MTINS8Name, 10)) {
						log(LogStatus.PASS, "Able to Transfer Contact", YesNo.No);
						ThreadSleep(2000);
						refresh(driver);
						
						if (cp.fieldValueVerification(projectName, PageName.Object2Page, PageLabel.Account_Name, Smoke_MTINS8Name, 5)) {
							log(LogStatus.PASS, "Label Verified after contact Transfer", YesNo.Yes);	
						} else {
							sa.assertTrue(false, "Label Not Verified after contact Transfer");
							log(LogStatus.FAIL, "Label Not Verified after contact Transfer", YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Transfer Contact");
						log(LogStatus.FAIL, "Not Able to Transfer Contact", YesNo.Yes);
					}
					
					


				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Transfer");
					log(LogStatus.SKIP, "Not Able to Click on Contact Transfer", YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + navatarCTCon1);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + navatarCTCon1,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		appLog.info("Pass");
	}
		
	@Parameters({"projectName"})
	@Test
	public void AASmokeTc067_2_TransferContactFromCTAccount2ToCTAccount3_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele ;

		String meetingSubject="";


		TabName tabName =TabName.Object1Tab;
		String ctAccount2 = Smoke_MTINS7Name;
		String ctAccount3 = Smoke_MTINS8Name;
		String[] accounts = {ctAccount2,ctAccount3};
		ActivityType[] activities = {ActivityType.Next,ActivityType.Past,ActivityType.Next};
		int j=0;
		int i=0;
		for (String account : accounts) {
			
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+account,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, account, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+account+" For : "+tabName,YesNo.No);
					ThreadSleep(1000);
					
					ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
					click(driver, ele, "More Steps" ,action.BOOLEAN);
					
					ThreadSleep(5000);
					
						
					
					for (ActivityType activityType : activities) {
						
							if (i==0) {
								meetingSubject=Smoke_S1TestSubject;	
							}else if(i==1){
								meetingSubject=Smoke_S2TestSubject;		
							}else {
								meetingSubject=Smoke_S3TestSubject;		
							}


							ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,activityType, meetingSubject, SubjectElement.SubjectLink, 10);
							
							if (j==0) {
								
								if (ele==null) {
									log(LogStatus.INFO,meetingSubject+" link is not present on Activity TimeLine : "+activityType+" For : "+account,YesNo.No);	
								} else {
									sa.assertTrue(false,meetingSubject+" link Should not present on Activity TimeLine : "+activityType+" For : "+account);
									log(LogStatus.SKIP,meetingSubject+" link Should not present on Activity TimeLine : "+activityType+" For : "+account,YesNo.Yes);
								}
								
							} else {

								if (ele!=null) {
									log(LogStatus.INFO,meetingSubject+" link present on Activity TimeLine : "+activityType+" For : "+account,YesNo.No);	
								} else {
									sa.assertTrue(false,meetingSubject+" link Should be present on Activity TimeLine : "+activityType+" For : "+account);
									log(LogStatus.SKIP,meetingSubject+" link Should be present on Activity TimeLine : "+activityType+" For : "+account,YesNo.Yes);
								}
								
							}
						
						i++;
						switchToDefaultContent(driver);
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+account+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+account+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+account);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+account,YesNo.Yes);
			}
			switchToDefaultContent(driver);
			j++;
			i=0;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc068_ChangeTheContactTransferSettingsToOldInstitutionOnlyAndContactandInstitutionOnly(String projectName) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer ctt = new NavatarSetupPageBusinessLayer(driver);
		String environment="";
		String mode="";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		appLog.info("Login with User");
		if (bp.clickOnTab(projectName, TabName.NavatarSetup)) {
			appLog.info("Clicked on Navatar Set Up Tab");

			if (ctt.clickOnNavatarSetupSideMenusTab(projectName, NavatarSetupSideMenuTab.ContactTransfer)) {
				appLog.error("Clicked on Contact Transfer Tab");
				if (click(driver, ctt.getEditButtonforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, 10), "Edit Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);
					ThreadSleep(2000);
				
				//	String keepActivitiesDefaultValue = "Old Account Only";
				//	String selectIncludeActivitiesValue="Contact and Account";
					String keepActivitiesDefaultValue = ctt.keepActivitiesValue(projectName, KeepActivityEnum.OldInstitutionOnly);
					String selectIncludeActivitiesValue = ctt.includeActivitiesValue(projectName, InculdeActivityEnum.ContactAndInstitution);
					if (selectVisibleTextFromDropDown(driver,ctt.getKeepActivitiesAtSelectList(projectName, EditViewMode.Edit, 10),keepActivitiesDefaultValue, keepActivitiesDefaultValue)) {
						log(LogStatus.INFO, "Selected Keep Activities related to : " + keepActivitiesDefaultValue,YesNo.No);
						ThreadSleep(1000);
						
						if (selectVisibleTextFromDropDown(driver,ctt.getIncludeActivitiesSelectList(projectName, EditViewMode.Edit, 10),selectIncludeActivitiesValue, selectIncludeActivitiesValue)) {
							log(LogStatus.INFO, "Selected Include Activities related to : " + keepActivitiesDefaultValue,YesNo.No);
							ThreadSleep(1000);
							
							if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(projectName, NavatarSetupSideMenuTab.ContactTransfer, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
								ThreadSleep(5000);
								log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
								ThreadSleep(10000);
									SoftAssert tsa = ctt.verifyingContactTransferTab(projectName, EditViewMode.View,CheckBox.Checked, keepActivitiesDefaultValue, selectIncludeActivitiesValue);
									sa.combineAssertions(tsa);
								
							} else {
								sa.assertTrue(false, "Not Able to Click on Save Button");
								log(LogStatus.SKIP, "Not Able to Click on Save Button", YesNo.Yes);
							}
							
							
						} else {
							sa.assertTrue(false,"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue);
							log(LogStatus.SKIP,"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue,YesNo.Yes);

						}
							
						
						
					} else {
						sa.assertTrue(false,"Not Able to Select Keep Activities related to : " + keepActivitiesDefaultValue);
						log(LogStatus.SKIP,"Not Able to Select Keep Activities related to : " + keepActivitiesDefaultValue,YesNo.Yes);

					}
					

				} else {
					appLog.error("Not Able to Click on Edit Button");
					sa.assertTrue(false, "Not Able to Click on Edit Button");
					log(LogStatus.SKIP, "Not Able to Click on Edit Button", YesNo.Yes);
				}
			} else {
				appLog.error("Not Able to Click on Contact Transfer Tab");
				sa.assertTrue(false, "Not Able to Click on Contact Transfer Tab");
				log(LogStatus.SKIP, "Not Able to Click on Contact Transfer Tab", YesNo.Yes);
			}

		} else {
			appLog.error("Not Able to Click on Navatar Set Up Tab");
			sa.assertTrue(false, "Not Able to Click on Navatar Set Up Tab");
			log(LogStatus.SKIP, "Not Able to Click on Navatar Set Up Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		ThreadSleep(3000);
		sa.assertAll();
		appLog.info("Pass");
	}
	
	@Parameters({"projectName"})
	@Test
	public void AASmokeTc069_1_TransferContactFromCTAccount3ToCTAccount4_Action(String projectName) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		appLog.info("Login with User");
		appLog.info("Going on Contact Tab");
		TabName tabName = TabName.Object2Tab;
		String navatarCTCon1= Smoke_MTContact5FName+" "+Smoke_MTContact5LName;
		if (bp.clickOnTab(projectName, tabName)) {
			if (cp.clickOnAlreadyCreatedItem(projectName, tabName, navatarCTCon1, 20)) {
				log(LogStatus.INFO, "Click on Created Contact : " + navatarCTCon1, YesNo.No);
				ThreadSleep(2000);
				if (cp.clickOnShowMoreActionDownArrow(projectName, PageName.Object2Page, ShowMoreActionDropDownList.Contact_Transfer, 10)) {
					log(LogStatus.INFO, "Clicked on Contact Transfer", YesNo.No);	
					ThreadSleep(1000);
					
					
					if (cp.enteringValueforLegalNameOnContactTransferPage(projectName, Smoke_MTINS9Name, 10)) {
						log(LogStatus.PASS, "Able to Transfer Contact", YesNo.No);
						ThreadSleep(2000);
						refresh(driver);
						
						if (cp.fieldValueVerification(projectName, PageName.Object2Page, PageLabel.Account_Name, Smoke_MTINS9Name, 5)) {
							log(LogStatus.PASS, "Label Verified after contact Transfer", YesNo.Yes);	
						} else {
							sa.assertTrue(false, "Label Not Verified after contact Transfer");
							log(LogStatus.FAIL, "Label Not Verified after contact Transfer", YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Transfer Contact");
						log(LogStatus.FAIL, "Not Able to Transfer Contact", YesNo.Yes);
					}
					
					


				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Transfer");
					log(LogStatus.SKIP, "Not Able to Click on Contact Transfer", YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + navatarCTCon1);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + navatarCTCon1,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		appLog.info("Pass");
	}
		
	@Parameters({"projectName"})
	@Test
	public void AASmokeTc069_2_TransferContactFromCTAccount3ToCTAccount4_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele ;

		String meetingSubject="";


		TabName tabName =TabName.Object1Tab;
		String ctAccount3 = Smoke_MTINS8Name;
		String ctAccount4 = Smoke_MTINS9Name;
		String[] accounts = {ctAccount3,ctAccount4};
		ActivityType[] activities = {ActivityType.Next,ActivityType.Past,ActivityType.Next};
		int j=0;
		int i=0;
		for (String account : accounts) {
			
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+account,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, account, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+account+" For : "+tabName,YesNo.No);
					ThreadSleep(1000);
					
					ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
					click(driver, ele, "More Steps" ,action.BOOLEAN);
					
					ThreadSleep(5000);
					
						
					
					for (ActivityType activityType : activities) {
						
							if (i==0) {
								meetingSubject=Smoke_S1TestSubject;	
							}else if(i==1){
								meetingSubject=Smoke_S2TestSubject;		
							}else {
								meetingSubject=Smoke_S3TestSubject;		
							}


							ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,activityType, meetingSubject, SubjectElement.SubjectLink, 10);
							
							if (Smoke_MTINS8Name.equals(account)) {
								
								if (ele==null) {
									log(LogStatus.INFO,meetingSubject+" link is not present on Activity TimeLine : "+activityType+" For : "+account,YesNo.No);	
								} else {
									sa.assertTrue(false,meetingSubject+" link Should not present on Activity TimeLine : "+activityType+" For : "+account);
									log(LogStatus.SKIP,meetingSubject+" link Should not present on Activity TimeLine : "+activityType+" For : "+account,YesNo.Yes);
								}
								
							} else {

								if (ele!=null) {
									log(LogStatus.INFO,meetingSubject+" link present on Activity TimeLine : "+activityType+" For : "+account,YesNo.No);	
								} else {
									sa.assertTrue(false,meetingSubject+" link Should be present on Activity TimeLine : "+activityType+" For : "+account);
									log(LogStatus.SKIP,meetingSubject+" link Should be present on Activity TimeLine : "+activityType+" For : "+account,YesNo.Yes);
								}
								
							}
						
						i++;
						switchToDefaultContent(driver);
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+account+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+account+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+account);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+account,YesNo.Yes);
			}
			switchToDefaultContent(driver);
			j++;
			i=0;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
    @Test
    public void AASmokeTc075_AddLastTouchPointField(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String parentID=null;
		//lp.switchToClassic();
		String mode="Lightning";
		String fieldValue = "";
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				List<String> layoutName = new ArrayList<String>();
				layoutName.add("Contact Layout");
				HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
				fieldValue="Last Touch Point";
				sourceANDDestination.put(fieldValue, PageLabel.Name.toString());
				//scn.next();
				List<String> abc = setup.DragNDrop("", mode, object.Contact, objectFeatureName.pageLayouts, layoutName, sourceANDDestination);
				ThreadSleep(10000);
				if (!abc.isEmpty()) {
					log(LogStatus.FAIL, "field not added/already present : "+fieldValue, YesNo.Yes);
					sa.assertTrue(false, "field not added/already present : "+fieldValue);
				}else{
					log(LogStatus.INFO, "field added/already present : "+fieldValue, YesNo.Yes);
				}

				fieldValue="Is_Touchpoint";
				layoutName = new ArrayList<String>();
				layoutName.add("Task Layout");
				sourceANDDestination = new HashMap<String, String>();
				sourceANDDestination.put(fieldValue, PageLabel.Name.toString());
				//scn.next();
				abc = setup.DragNDrop("", mode, object.Task, objectFeatureName.pageLayouts, layoutName, sourceANDDestination);
				ThreadSleep(10000);
				if (!abc.isEmpty()) {
					log(LogStatus.FAIL, "field not added/already present : "+fieldValue, YesNo.Yes);
					sa.assertTrue(false, "field not added/already present : "+fieldValue);
				}else{
					log(LogStatus.INFO, "field added/already present : "+fieldValue, YesNo.Yes);
				}


			}
			driver.close();
			driver.switchTo().window(parentID);
		}else {
			log(LogStatus.FAIL, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
 
	@Parameters({ "projectName"})
    @Test
    public void AASmokeTc076_1_CreateStandardTaskforContactAndVerifyLastTouchpointOnContactDetailpage_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;
		boolean flag=false;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		String task=Smoke_STTaskFromContactSubject;
		
		if (click(driver, gp.getGlobalActionIcon(projectName, 15), "Global Action Related item", action.BOOLEAN)) {
			log(LogStatus.INFO,"Clicked on Global Action Related item",YesNo.No);
			//					scn.nextLine();
			if (clickUsingJavaScript(driver, gp.getActionItem(projectName, GlobalActionItem.New_Task, 15), "New Task Link", action.BOOLEAN)) {
				log(LogStatus.INFO,"Clicked on New Event Link",YesNo.Yes);
				ThreadSleep(2000);	

				click(driver, gp.getMaximizeIcon(projectName, 15), "Maximize Icon", action.BOOLEAN);

				String[][] event1 = {{PageLabel.Subject.toString(),task},
						{PageLabel.Meeting_Type.toString(),Smoke_STTaskFromContactMeetingType},
						{PageLabel.Status.toString(),Smoke_STTaskFromContactStatus},
						{PageLabel.Due_Date.toString(),yesterdaysDate},
						{PageLabel.Name.toString(),watchListContact1},
						{PageLabel.Name.toString(),watchListContact2},
						{PageLabel.Name.toString(),watchListContact3}};


				ExcelUtils.writeData(yesterdaysDate, "Task", excelLabel.Variable_Name, "AATask36", excelLabel.Due_Date);
				boolean flag1 = gp.enterValueForNewEvent(projectName, GlobalActionItem.New_Task , event1, 10);

				clickUsingJavaScript(driver, lp.getrelatedAssociationsdropdownButton(projectName, PageName.TaskPage,PageLabel.Related_To.toString(), action.BOOLEAN, 10),"dropdown button", action.BOOLEAN);
						
				TabName tabName = TabName.TestCustomObjectTab;
				String relatedValue = WATCHCustomObj1Name;
				flag = gp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewEventPopUp, PageLabel.Related_To.toString(), tabName, relatedValue, action.SCROLLANDBOOLEAN,10);	
				if (flag) {
					log(LogStatus.INFO,"Selected "+relatedValue+" For Label "+PageLabel.Related_To,YesNo.No);
				} else {
					sa.assertTrue(false,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_To);
					log(LogStatus.SKIP,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_To,YesNo.Yes);

				}


				if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
					ThreadSleep(1000);
					
					WebElement ele = cp.getCreatedConfirmationMsg(projectName, 15);
					if (ele!=null) {
						String actualValue = ele.getText().trim();
						String expectedValue=tp.taskCreatesMsg(projectName, task);
						if (actualValue.contains(expectedValue)) {
							log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

						} else {
							log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
						}
					} else {
						sa.assertTrue(false,"Created Task Msg Ele not Found");
						log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

					}
					
				}
				
				else {
					sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
				}




			} else {
				sa.assertTrue(false,"Not Able to Click on New Task Link");
				log(LogStatus.SKIP,"Not Able to Click on New Task Link",YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Global Action Related item");
			log(LogStatus.SKIP,"Not Able to Click on Global Action Related item",YesNo.Yes);
		}
		
	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc076_2_CreateStandardTaskforContactAndVerifyLastTouchpointOnContactDetailpage_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
	
		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;
		
		TabName[] tabNames = {TabName.Object2Tab,TabName.Object2Tab,TabName.Object2Tab};
		String[] names = {watchListContact1,watchListContact2,watchListContact3};
		
		TabName tabName ;
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(2000);
					 ele=cp.getlastTouchPointValue(projectName, 10);
					String value="";
					if (ele!=null) {
						value=ele.getText().trim();
						if (value.isEmpty() || value.equals("")) {
							log(LogStatus.INFO,"successfully verified last touch point date For : "+name, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "last touch point value is not matched For : "+name, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+name );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+name, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+name );
					}



				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}
		
		String name = Smoke_STTaskFromContactSubject;
		tabName = TabName.TaskTab;
		if (cp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
			ThreadSleep(1000);
			ele=tp.getTaskNameLinkInSideMMenu(projectName, name, 15);

			if (click(driver, ele, name, action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on "+name+" on Task SideMenu", YesNo.No);	
				ThreadSleep(1000);

				ele = cp.getIsTouchPoinCheckBox(projectName,PageName.TaskPage, 10);
				if (ele!=null) {
					log(LogStatus.SKIP,"Is_Touchpoint Label is present for : "+name,YesNo.No);

					System.err.println(">>>>>>>>>>> "+ele.getAttribute("class"));
					if (ele.getAttribute("class").trim().equalsIgnoreCase("checked")) {
						log(LogStatus.SKIP,"Is_Touchpoint is checked for : "+name,YesNo.No);
					} else {
						log(LogStatus.ERROR, "Is_Touchpoint should be checked for : "+name, YesNo.Yes);
						sa.assertTrue(false,"Is_Touchpoint should be checked for : "+name );
					}
					
				} else {
					log(LogStatus.ERROR, "Is_Touchpoint should be present for : "+name, YesNo.Yes);
					sa.assertTrue(false,"Is_Touchpoint should be present for : "+name );

				}
				

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+name+" on Task SideMenu", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+name+" on Task SideMenu");
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
    @Test
    public void AASmokeTc077_1_CreateMultitaggedTaskforContactAndVerifyLastTouchpointOnContactDetailPage_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;
		boolean flag=false;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		String task=Smoke_MTGTaskFromContactSubject;
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, watchListContact1, 20)) {
				WebElement ele;
				//					scn.nextLine();
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Task_with_Multiple_Associations, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					//					scn.nextLine();

					ele = cp.getHeaderTextForPage(projectName, PageName.Object2Page,PageLabel.New_Task.toString(), action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
					} else {
						sa.assertTrue(false,"New Task PopUp is not opened");
						log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
					}

					
					ele = ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, watchListContact1, action.SCROLLANDBOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of "+watchListContact1+" in name field",YesNo.No);
					} else {
						sa.assertTrue(false,"not found "+ watchListContact1+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"not found "+ watchListContact1+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, watchListContact2, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+watchListContact2+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+watchListContact2+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+watchListContact2+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, watchListContact3, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+watchListContact3+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+watchListContact3+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+watchListContact3+" For Label "+PageLabel.Name,YesNo.Yes);

					}



					String[][] dropDownLabelWithValues = {{PageLabel.Meeting_Type.toString(),Smoke_MTGTaskFromContactMeetingType},
							{PageLabel.Status.toString(),Smoke_MTGTaskFromContactStatus}};

					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.Object2Page, task, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {	
						
						log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

						if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), dayBeforeYesterdaysDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
							ThreadSleep(1000);
						}else {
							log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
						}
						ExcelUtils.writeData(dayBeforeYesterdaysDate, "Task", excelLabel.Variable_Name, "AATask37", excelLabel.Due_Date);

						
					flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, WATCHCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.INFO,"Selected "+WATCHCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);
						
						ThreadSleep(2000);
						if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
							ThreadSleep(1000);


							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, task);
								if (actualValue.contains(expectedValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}


						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created" );
						}

					} else {
						sa.assertTrue(false,"Not Able to Select "+WATCHCustomObj1Name+" For Label "+PageLabel.Related_Associations);
						log(LogStatus.SKIP,"Not Able to Select "+WATCHCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

					}


					}else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on "+ActivityTimeLineItem.New_Task, YesNo.Yes);
					sa.assertTrue(false,   "could not click on "+ActivityTimeLineItem.New_Task);
				}

			}else {
				log(LogStatus.ERROR, "not able to click on "+watchListContact1, YesNo.Yes);
				sa.assertTrue(false, "not able to click on "+watchListContact1);
			}
		}
		else {
			log(LogStatus.ERROR, "not able to click on contact tab", YesNo.Yes);
			sa.assertTrue(false, "not able to click on contact tab" );
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc077_2_CreateMultitaggedTaskforContactAndVerifyLastTouchpointOnContactDetailPage_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
	
		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;
		
		TabName[] tabNames = {TabName.Object2Tab,TabName.Object2Tab,TabName.Object2Tab};
		String[] names = {watchListContact1,watchListContact2,watchListContact3};
		
		TabName tabName ;
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(2000);
					 ele=cp.getlastTouchPointValue(projectName, 10);
					String value="";
					if (ele!=null) {
						value=ele.getText().trim();
						if (value.isEmpty() || value.equals("")) {
							log(LogStatus.INFO,"successfully verified last touch point date For : "+name, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "last touch point value is not matched For : "+name, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+name );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+name, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+name );
					}



				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}


		String name = Smoke_MTGTaskFromContactSubject;
		tabName = TabName.TaskTab;
		if (cp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
			ThreadSleep(1000);
			ele=tp.getTaskNameLinkInSideMMenu(projectName, name, 15);

			if (click(driver, ele, name, action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on "+name+" on Task SideMenu", YesNo.No);	
				ThreadSleep(1000);

				ele = cp.getIsTouchPoinCheckBox(projectName,PageName.TaskPage, 10);
				if (ele!=null) {
					log(LogStatus.SKIP,"Is_Touchpoint Label is present for : "+name,YesNo.No);

					System.err.println(">>>>>>>>>>> "+ele.getAttribute("class"));
					if (ele.getAttribute("class").trim().equalsIgnoreCase("checked")) {
						log(LogStatus.SKIP,"Is_Touchpoint is checked for : "+name,YesNo.No);
					} else {
						log(LogStatus.ERROR, "Is_Touchpoint should be checked for : "+name, YesNo.Yes);
						sa.assertTrue(false,"Is_Touchpoint should be checked for : "+name );
					}
					
				} else {
					log(LogStatus.ERROR, "Is_Touchpoint should be present for : "+name, YesNo.Yes);
					sa.assertTrue(false,"Is_Touchpoint should be present for : "+name );

				}
				
				

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+name+" on Task SideMenu", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+name+" on Task SideMenu");
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
		}
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc078_1_UpdateStatusToCompletedInStandardAndMultitaggedTask_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;

		TabName[] tabNames = {TabName.TaskTab};
		String[] taskNames = {Smoke_STTaskFromContactSubject};
		PageName[] pageNames = {PageName.NewEventPopUp};

		TabName tabName ;
		int i=0;
		String value ="Completed" ;
		for (String name : taskNames) {
			name=taskNames[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				ThreadSleep(1000);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, name, 15);
				click(driver, ele, name, action.BOOLEAN);
				refresh(driver);
				ThreadSleep(5000);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, name, 15);
				if (click(driver, ele, name, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+name+" on Task SideMenu", YesNo.No);	
					ThreadSleep(1000);
					//					scn.nextLine();
					ele = cp.getIsTouchPoinCheckBox(projectName,PageName.TaskPage, 10);
					if (ele!=null) {
						log(LogStatus.SKIP,"Is_Touchpoint Label is present for : "+name,YesNo.No);

						System.err.println(">>>>>>>>>>> "+ele.getAttribute("class"));
						if (ele.getAttribute("class").trim().equalsIgnoreCase("checked")) {
							log(LogStatus.SKIP,"Is_Touchpoint is checked for : "+name,YesNo.No);
						} else {
							log(LogStatus.ERROR, "Is_Touchpoint should be checked for : "+name, YesNo.Yes);
							sa.assertTrue(false,"Is_Touchpoint should be checked for : "+name );
						}
						
					} else {
						log(LogStatus.ERROR, "Is_Touchpoint should be present for : "+name, YesNo.Yes);
						sa.assertTrue(false,"Is_Touchpoint should be present for : "+name );

					}
					
						
					if (click(driver, tp.getEditButton(projectName, 10), name, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+name, YesNo.No);	
						ThreadSleep(1000);

						if (tp.selectDropDownValueonTaskPopUp(projectName, pageNames[i], PageLabel.Status.toString(), value, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+value+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
							ThreadSleep(1000);	


							if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated  task : "+name,  YesNo.Yes);

								ThreadSleep(5000);
								
								ele = cp.getIsTouchPoinCheckBox(projectName,PageName.TaskPage, 10);
								if (ele!=null) {
									log(LogStatus.SKIP,"Is_Touchpoint Label is present for : "+name,YesNo.No);

									System.err.println(">>>>>>>>>>> "+ele.getAttribute("class"));
									if (ele.getAttribute("class").trim().equalsIgnoreCase("checked")) {
										log(LogStatus.SKIP,"Is_Touchpoint is checked for : "+name,YesNo.No);
									} else {
										log(LogStatus.ERROR, "Is_Touchpoint should be checked for : "+name, YesNo.Yes);
										sa.assertTrue(false,"Is_Touchpoint should be checked for : "+name );
									}
									
								} else {
									log(LogStatus.ERROR, "Is_Touchpoint should be present for : "+name, YesNo.Yes);
									sa.assertTrue(false,"Is_Touchpoint should be present for : "+name );

								}
								
								
								
								String[][] fieldsWithValues= {{PageLabel.Status.toString(),value}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);	

								
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+name, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+name );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+value+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+value+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+name, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+name);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+name+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+name+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc078_2_UpdateStatusToCompletedInStandardAndMultitaggedTask_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;

		TabName[] tabNames = {TabName.TaskTab};
		String[] taskNames = {Smoke_MTGTaskFromContactSubject};
		PageName[] pageNames = {PageName.Object2Page};

		TabName tabName ;
		int i=0;
		String value ="Completed" ;
		for (String name : taskNames) {
			name=taskNames[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				ThreadSleep(1000);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, name, 15);
				click(driver, ele, name, action.BOOLEAN);
				ThreadSleep(5000);
				refresh(driver);
				ThreadSleep(5000);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, name, 15);
				if (click(driver, ele, name, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+name+" on Task SideMenu", YesNo.No);	
					ThreadSleep(1000);
					//					scn.nextLine();
					ele = cp.getIsTouchPoinCheckBox(projectName,PageName.TaskPage, 10);
					if (ele!=null) {
						log(LogStatus.SKIP,"Is_Touchpoint Label is present for : "+name,YesNo.No);

						System.err.println(">>>>>>>>>>> "+ele.getAttribute("class"));
						if (ele.getAttribute("class").trim().equalsIgnoreCase("checked")) {
							log(LogStatus.SKIP,"Is_Touchpoint is checked for : "+name,YesNo.No);
						} else {
							log(LogStatus.ERROR, "Is_Touchpoint should be checked for : "+name, YesNo.Yes);
							sa.assertTrue(false,"Is_Touchpoint should be checked for : "+name );
						}
						
					} else {
						log(LogStatus.ERROR, "Is_Touchpoint should be present for : "+name, YesNo.Yes);
						sa.assertTrue(false,"Is_Touchpoint should be present for : "+name );

					}
					
						
					if (click(driver, tp.getEditButton(projectName, 10), name, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+name, YesNo.No);	
						ThreadSleep(1000);

						if (tp.selectDropDownValueonTaskPopUp(projectName, pageNames[i], PageLabel.Status.toString(), value, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+value+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
							ThreadSleep(1000);	


							if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated  task : "+name,  YesNo.Yes);

								ele = cp.getAnyMsg(projectName, tp.taskSavedMsg(projectName, name), 10);
								if (ele!=null) {
									log(LogStatus.SKIP,"Saved Task Msg Ele Found and Verified : "+name,YesNo.No);

								} else {
									sa.assertTrue(false,"Saved Task Msg Ele not Found : "+name);
									log(LogStatus.SKIP,"Saved Task Msg Ele not Found : "+name,YesNo.Yes);

								}
								
								ThreadSleep(5000);
								
								ele = cp.getIsTouchPoinCheckBox(projectName,PageName.TaskPage, 10);
								if (ele!=null) {
									log(LogStatus.SKIP,"Is_Touchpoint Label is present for : "+name,YesNo.No);

									System.err.println(">>>>>>>>>>> "+ele.getAttribute("class"));
									if (ele.getAttribute("class").trim().equalsIgnoreCase("checked")) {
										log(LogStatus.SKIP,"Is_Touchpoint is checked for : "+name,YesNo.No);
									} else {
										log(LogStatus.ERROR, "Is_Touchpoint should be checked for : "+name, YesNo.Yes);
										sa.assertTrue(false,"Is_Touchpoint should be checked for : "+name );
									}
									
								} else {
									log(LogStatus.ERROR, "Is_Touchpoint should be present for : "+name, YesNo.Yes);
									sa.assertTrue(false,"Is_Touchpoint should be present for : "+name );

								}
								
								
								
								String[][] fieldsWithValues= {{PageLabel.Status.toString(),value}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);	

								
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+name, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+name );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+value+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+value+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+name, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+name);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+name+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+name+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc078_3_UpdateStatusToCompletedInStandardAndMultitaggedTask_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
	
		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;
		
		TabName[] tabNames = {TabName.Object2Tab,TabName.Object2Tab,TabName.Object2Tab};
		String[] names = {watchListContact1,watchListContact2,watchListContact3};
		
		TabName tabName ;
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(2000);
					 try {
						ele=cp.getlastTouchPointValue(projectName, 10);
						String value="";
						if (ele!=null) {
							value=ele.getText().trim();
							if (cp.verifyDate(Smoke_STTaskFromContactDueDate, value)) {
								log(LogStatus.INFO,"successfully verified last touch point date For : "+name, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "last touch point value is not matched For : "+name, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+name );
							}
						}else {
							log(LogStatus.ERROR, "last touch point value is not visible For : "+name, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not visible For : "+name );
						}
					} catch (Exception e) {
						log(LogStatus.ERROR, "Exception in last touch point value For : "+name, YesNo.Yes);
						sa.assertTrue(false,"Exception in last touch point value For : "+name );
					}



				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc079_1_DeleteStandardTaskSTTaskfrmContactAndVerifyImpactonLastTouchpoint_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;


		TabName[] tabNames = {TabName.TaskTab};
		String[] taskNames = {Smoke_STTaskFromContactSubject};

		TabName tabName ;
		int i=0;
		for (String name : taskNames) {
			name=taskNames[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				ThreadSleep(1000);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, name, 15);

				if (click(driver, ele, name, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+name+" on Task SideMenu", YesNo.No);	
					ThreadSleep(5000);
					refresh(driver);
					ThreadSleep(5000);
					//					scn.nextLine();
					if (click(driver, tp.getDeleteButton(projectName, 10), name, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Delete Button For : "+name, YesNo.No);	
						ThreadSleep(1000);

						if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on Delete PoPup : "+tabName+" For : "+name,YesNo.No);
							ThreadSleep(1000);

//							ele = cp.getAnyMsg(projectName, tp.taskDeletedMsg(projectName, name), 10);
//							if (ele!=null) {
//								log(LogStatus.SKIP,"Delete Task Msg Ele Found and Verified : "+name,YesNo.No);
//
//							} else {
//								sa.assertTrue(false,"Delete Task Msg Ele not Found : "+name);
//								log(LogStatus.SKIP,"Delete Task Msg Ele not Found : "+name,YesNo.Yes);
//
//							}
							
						} else {
							sa.assertTrue(false,"Not Able to Click on Delete button on Delete PoPup : "+tabName+" For : "+name);
							log(LogStatus.SKIP,"Not Able to Click on Delete button on Delete PoPup : "+tabName+" For : "+name,YesNo.Yes);
						}



					} else {
						log(LogStatus.ERROR, "Not Able to Click on Delete Button For : "+name, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Delete Button For : "+name);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+name+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+name+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}

		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc079_2_DeleteStandardTaskSTTaskfrmContactAndVerifyImpactonLastTouchpoint_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
	
		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;
		
		TabName[] tabNames = {TabName.Object2Tab,TabName.Object2Tab,TabName.Object2Tab};
		String[] names = {watchListContact1,watchListContact2,watchListContact3};
		
		TabName tabName ;
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(2000);
					 try {
						ele=cp.getlastTouchPointValue(projectName, 10);
						String value="";
						if (ele!=null) {
							value=ele.getText().trim();
							if (cp.verifyDate(Smoke_MTGTaskFromContactDueDate, value)) {
								log(LogStatus.INFO,"successfully verified last touch point date For : "+name, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "last touch point value is not matched For : "+name, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+name );
							}
						}else {
							log(LogStatus.ERROR, "last touch point value is not visible For : "+name, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not visible For : "+name );
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log(LogStatus.ERROR, "Exception in last touch point value For : "+name, YesNo.Yes);
						sa.assertTrue(false,"Exception in last touch point value For : "+name );
					}



				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc080_1_UpdateDueDateForMTGTaskAndVerifyImpactOnLastTouchPoint_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;

		TabName[] tabNames = {TabName.TaskTab};
		String[] taskNames = {Smoke_MTGTaskFromContactSubject};

		TabName tabName ;
		int i=0;
		String dueDate = todaysDate;
		for (String name : taskNames) {
			name=taskNames[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				ThreadSleep(1000);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, name, 15);

				if (click(driver, ele, name, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+name+" on Task SideMenu", YesNo.No);	
					ThreadSleep(1000);

					if (click(driver, tp.getEditButton(projectName, 10), name, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+name, YesNo.No);	
						ThreadSleep(1000);

						
						if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), dueDate+"\t", PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box for : "+name, YesNo.Yes);
							ThreadSleep(1000);	


							if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated  task : "+name,  YesNo.Yes);

								ele = cp.getAnyMsg(projectName, tp.taskSavedMsg(projectName, name), 20);
								if (ele!=null) {
									log(LogStatus.SKIP,"Saved Task Msg Ele Found and Verified : "+name,YesNo.No);

								} else {
									sa.assertTrue(false,"Saved Task Msg Ele not Found : "+name);
									log(LogStatus.SKIP,"Saved Task Msg Ele not Found : "+name,YesNo.Yes);

								}
								
								ThreadSleep(5000);
								
								
								String[][] fieldsWithValues= {{PageLabel.Due_Date.toString(),dueDate}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);	

								
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+name, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+name );
							}

						}else {
							log(LogStatus.ERROR, "duedate textbox is not visible so task could not be Upadted : "+name, YesNo.Yes);
							sa.assertTrue(false,"duedate textbox is not visible so task could not be Upadted : "+name );
					
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+name, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+name);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+name+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+name+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			refresh(driver);
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc080_2_UpdateDueDateForMTGTaskAndVerifyImpactOnLastTouchPoint_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
	
		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;
		
		TabName[] tabNames = {TabName.Object2Tab,TabName.Object2Tab,TabName.Object2Tab};
		String[] names = {watchListContact1,watchListContact2,watchListContact3};
		
		TabName tabName ;
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(2000);
					try {
					 ele=cp.getlastTouchPointValue(projectName, 10);
					String value="";
					if (ele!=null) {
						value=ele.getText().trim();
						if (cp.verifyDate(todaysDate, value)) {
							log(LogStatus.INFO,"successfully verified last touch point date For : "+name, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "last touch point value is not matched For : "+name, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+name );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+name, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+name );
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log(LogStatus.ERROR, "Exception in last touch point value For : "+name, YesNo.Yes);
					sa.assertTrue(false,"Exception in last touch point value For : "+name );
				}



				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc081_1_UpdateStatusToNotStartedInMTGTaskAndVerifyImpactOnLastTouchPoint_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;

		TabName[] tabNames = {TabName.TaskTab};
		String[] taskNames = {Smoke_MTGTaskFromContactSubject};

		TabName tabName ;
		int i=0;
		String value ="Not Started" ;
		for (String name : taskNames) {
			name=taskNames[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				ThreadSleep(1000);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, name, 15);

				if (click(driver, ele, name, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+name+" on Task SideMenu", YesNo.No);	
					ThreadSleep(1000);

						
					if (click(driver, tp.getEditButton(projectName, 10), name, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+name, YesNo.No);	
						ThreadSleep(1000);

						if (tp.selectDropDownValueonTaskPopUp(projectName, PageName.Object1Page, PageLabel.Status.toString(), value, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+value+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
							ThreadSleep(1000);	


							if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								
								
								ele = cp.getAnyMsg(projectName, tp.taskSavedMsg(projectName, name), 20);
								if (ele!=null) {
									log(LogStatus.SKIP,"Saved Task Msg Ele Found and Verified : "+name,YesNo.No);

								} else {
									sa.assertTrue(false,"Saved Task Msg Ele not Found : "+name);
									log(LogStatus.SKIP,"Saved Task Msg Ele not Found : "+name,YesNo.Yes);

								}
								ThreadSleep(2000);
								int k=2;
								while (isDisplayed(driver, lp.getCustomTabSaveBtn(projectName,20), "visibility", 10, "save")!=null) {
									clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN);
									log(LogStatus.INFO, "clicked save "+k+" times", YesNo.No);
									k++;
								}
								log(LogStatus.INFO,"successfully Updated  task : "+name,  YesNo.Yes);

								
								String[][] fieldsWithValues= {{PageLabel.Status.toString(),value}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);	

								
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+name, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+name );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+value+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+value+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+name, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+name);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+name+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+name+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			refresh(driver);
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc081_2_UpdateStatusToNotStartedInMTGTaskAndVerifyImpactOnLastTouchPoint_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
	
		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;
		
		TabName[] tabNames = {TabName.Object2Tab,TabName.Object2Tab,TabName.Object2Tab};
		String[] names = {watchListContact1,watchListContact2,watchListContact3};
		
		TabName tabName ;
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(2000);
					try {
					 ele=cp.getlastTouchPointValue(projectName, 10);
					String value="";
					if (ele!=null) {
						value=ele.getText().trim();
						if (value.isEmpty() || value.equals("")) {
							log(LogStatus.INFO,"successfully verified last touch point date For : "+name, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "last touch point value is not matched For : "+name, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+name );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+name, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+name );
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log(LogStatus.ERROR, "Exception in last touch point value For : "+name, YesNo.Yes);
					sa.assertTrue(false,"Exception in last touch point value For : "+name );
				}



				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc082_1_RestoreStandardTaskSTTaskfrmContactFromRecycleBin_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		WebElement ele = null ;
	
		
		TabName[] tabNames = {TabName.RecycleBinTab};
		String[] names = {Smoke_STTaskFromContactSubject};
		
		TabName tabName ;
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20);
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(2000);
					
					 ele=cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, name, 10);
					 if (clickUsingJavaScript(driver, ele, "Check box against : "+name, action.BOOLEAN)) {
						 log(LogStatus.INFO,"Click on checkbox for "+name,YesNo.No);
						 
						 ThreadSleep(1000);
							
						 ele=cp.getRestoreButtonOnRecycleBin(projectName, 10);
						 if (clickUsingJavaScript(driver, ele, "Restore Button : "+name, action.BOOLEAN)) {
							 log(LogStatus.INFO,"Click on Restore Button for "+name,YesNo.No);
							 ThreadSleep(1000);
							 
//							 ele = cp.getAnyMsg(projectName, tp.restoreItemMsg(projectName, name), 20);
//								if (ele!=null) {
//									log(LogStatus.SKIP,"Restore Msg Ele Found and Verified : "+name,YesNo.No);
//
//								} else {
//									sa.assertTrue(false,"Restore Task Msg Ele not Found : "+name);
//									log(LogStatus.SKIP,"Restore Task Msg Ele not Found : "+name,YesNo.Yes);
//
//								}
								
						} else {
							sa.assertTrue(false,"Not Able to Click on Restore Button for "+name);
							log(LogStatus.SKIP,"Not Able to Click on Restore Button for "+name,YesNo.Yes);
						}
						 
					} else {
						sa.assertTrue(false,"Not Able to Click on checkbox for "+name);
						log(LogStatus.SKIP,"Not Able to Click on checkbox for "+name,YesNo.Yes);
					}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc082_2_RestoreStandardTaskSTTaskfrmContactFromRecycleBin_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
	
		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;
		
		TabName[] tabNames = {TabName.Object2Tab,TabName.Object2Tab,TabName.Object2Tab};
		String[] names = {watchListContact1,watchListContact2,watchListContact3};
		
		TabName tabName ;
		String value="";
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(2000);
					try {
					 ele=cp.getlastTouchPointValue(projectName, 10);
						
						if (ele!=null) {
							value=ele.getText().trim();
							if (cp.verifyDate(Smoke_STTaskFromContactDueDate, value)) {
								log(LogStatus.INFO,"successfully verified last touch point date For : "+name, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "last touch point value is not matched For : "+name, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+name );
							}
						}else {
							log(LogStatus.ERROR, "last touch point value is not visible For : "+name, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not visible For : "+name );
						}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log(LogStatus.ERROR, "Exception in last touch point value For : "+name, YesNo.Yes);
					sa.assertTrue(false,"Exception in last touch point value For : "+name );
				}
					



				} else {
					sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
    @Test
    public void AASmokeTc083_1_CreateLogAcallWithMultipleAssociationAndVerifyLastTouchpoint_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;
		boolean flag=false;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		String task=Smoke_LAC1Subject;
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, watchListContact1, 20)) {
				WebElement ele;
				//					scn.nextLine();
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations.toString(), action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					//					scn.nextLine();

					ele = cp.getHeaderTextForPage(projectName, PageName.Object2Page,PageLabel.New_Task.toString(), action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
					} else {
						sa.assertTrue(false,"New Task PopUp is not opened");
						log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
					}

					
					ele = ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, watchListContact1, action.SCROLLANDBOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of "+watchListContact1+" in name field",YesNo.No);
					} else {
						sa.assertTrue(false,"not found "+ watchListContact1+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"not found "+ watchListContact1+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, watchListContact2, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+watchListContact2+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+watchListContact2+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+watchListContact2+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, watchListContact3, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+watchListContact3+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+watchListContact3+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+watchListContact3+" For Label "+PageLabel.Name,YesNo.Yes);

					}



					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.Object2Page, task, null, action.SCROLLANDBOOLEAN, 10)) {	
						
						log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

						if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), dayAfterTomorrowsDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
							ThreadSleep(1000);
						}else {
							log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
						}
						ExcelUtils.writeData(dayAfterTomorrowsDate, "Task", excelLabel.Variable_Name, "AATask38", excelLabel.Due_Date);

						
					flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, WATCHCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.INFO,"Selected "+WATCHCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);
						
						ThreadSleep(2000);
						if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
							ThreadSleep(1000);


							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, task);
								if (actualValue.contains(expectedValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}


						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created" );
						}

					} else {
						sa.assertTrue(false,"Not Able to Select "+WATCHCustomObj1Name+" For Label "+PageLabel.Related_Associations);
						log(LogStatus.SKIP,"Not Able to Select "+WATCHCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

					}


					}else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on "+ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, YesNo.Yes);
					sa.assertTrue(false,   "could not click on "+ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations);
				}

			}else {
				log(LogStatus.ERROR, "not able to click on "+watchListContact1, YesNo.Yes);
				sa.assertTrue(false, "not able to click on "+watchListContact1);
			}
		}
		else {
			log(LogStatus.ERROR, "not able to click on contact tab", YesNo.Yes);
			sa.assertTrue(false, "not able to click on contact tab" );
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc083_2_CreateLogAcallWithMultipleAssociationAndVerifyLastTouchpoint_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;

		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;

		TabName[] tabNames = {TabName.Object2Tab,TabName.Object2Tab,TabName.Object2Tab,TabName.TaskTab};
		String[] names = {watchListContact1,watchListContact2,watchListContact3,Smoke_LAC1Subject};

		TabName tabName ;
		String value="";
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (name.equals(Smoke_LAC1Subject)) {

					ele=tp.getTaskNameLinkInSideMMenu(projectName, name, 15);

					if (click(driver, ele, name, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on "+name+" on Task SideMenu", YesNo.No);	
						ThreadSleep(1000);

						ThreadSleep(5000);

						ele = cp.getIsTouchPoinCheckBox(projectName,PageName.TaskPage, 10);
						if (ele!=null) {
							log(LogStatus.SKIP,"Is_Touchpoint Label is present for : "+name,YesNo.No);

							System.err.println(">>>>>>>>>>> "+ele.getAttribute("class"));
							if (ele.getAttribute("class").trim().equalsIgnoreCase("checked")) {
								log(LogStatus.SKIP,"Is_Touchpoint is checked for : "+name,YesNo.No);
							} else {
								log(LogStatus.ERROR, "Is_Touchpoint should be checked for : "+name, YesNo.Yes);
								sa.assertTrue(false,"Is_Touchpoint should be checked for : "+name );
							}

						} else {
							log(LogStatus.ERROR, "Is_Touchpoint should be present for : "+name, YesNo.Yes);
							sa.assertTrue(false,"Is_Touchpoint should be present for : "+name );

						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+name+" on Task SideMenu", YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+name+" on Task SideMenu");
					}

				} else {
					if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
						log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
						ThreadSleep(2000);
						try {
						ele=cp.getlastTouchPointValue(projectName, 10);

						if (ele!=null) {
							value=ele.getText().trim();
							if (cp.verifyDate(Smoke_LAC1DueDate, value)) {
								log(LogStatus.INFO,"successfully verified last touch point date For : "+name, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "last touch point value is not matched For : "+name, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+name );
							}
						}else {
							log(LogStatus.ERROR, "last touch point value is not visible For : "+name, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not visible For : "+name );
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log(LogStatus.ERROR, "Exception in last touch point value For : "+name, YesNo.Yes);
						sa.assertTrue(false,"Exception in last touch point value For : "+name );
					}

					} else {
						sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
						log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
					}
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
    @Test
    public void AASmokeTc084_1_CreateNewTaskWithMultipleAssociationAndVerifyLastTouchPoint_Action(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;
		boolean flag=false;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		String task=Smoke_TaskMTA1Subject;
		String date = previousOrForwardDate(3, "MM/dd/YYYY");
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, watchListContact1, 20)) {
				WebElement ele;
				//					scn.nextLine();
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations.toString(), action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					//					scn.nextLine();

					ele = cp.getHeaderTextForPage(projectName, PageName.Object2Page,PageLabel.New_Task.toString(), action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
					} else {
						sa.assertTrue(false,"New Task PopUp is not opened");
						log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
					}

					
					ele = ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, watchListContact1, action.SCROLLANDBOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of "+watchListContact1+" in name field",YesNo.No);
					} else {
						sa.assertTrue(false,"not found "+ watchListContact1+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"not found "+ watchListContact1+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, watchListContact2, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+watchListContact2+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+watchListContact2+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+watchListContact2+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), TabName.Object2Tab, watchListContact3, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+watchListContact3+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+watchListContact3+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+watchListContact3+" For Label "+PageLabel.Name,YesNo.Yes);

					}



					String[][] dropDownLabelWithValues = {{PageLabel.Meeting_Type.toString(),Smoke_TaskMTA1MeetingType}};

					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.Object2Page, task, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {	
						
						log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

						if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), date, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
							ThreadSleep(1000);
						}else {
							log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
						}
						ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask39", excelLabel.Due_Date);

						
					flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, WATCHCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.INFO,"Selected "+WATCHCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);
						
						ThreadSleep(2000);
						if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
							ThreadSleep(1000);


							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, task);
								if (actualValue.contains(expectedValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}


						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created" );
						}

					} else {
						sa.assertTrue(false,"Not Able to Select "+WATCHCustomObj1Name+" For Label "+PageLabel.Related_Associations);
						log(LogStatus.SKIP,"Not Able to Select "+WATCHCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

					}


					}else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on "+ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations, YesNo.Yes);
					sa.assertTrue(false,   "could not click on "+ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations);
				}

			}else {
				log(LogStatus.ERROR, "not able to click on "+watchListContact1, YesNo.Yes);
				sa.assertTrue(false, "not able to click on "+watchListContact1);
			}
		}
		else {
			log(LogStatus.ERROR, "not able to click on contact tab", YesNo.Yes);
			sa.assertTrue(false, "not able to click on contact tab" );
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc084_2_CreateNewTaskWithMultipleAssociationAndVerifyLastTouchPoint_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;

		String watchListContact1 = Smoke_WATCHContact1FName+" "+Smoke_WATCHContact1LName;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;

		TabName[] tabNames = {TabName.Object2Tab,TabName.Object2Tab,TabName.Object2Tab,TabName.TaskTab};
		String[] names = {watchListContact1,watchListContact2,watchListContact3,Smoke_TaskMTA1Subject};

		TabName tabName ;
		String value="";
		int i=0;
		for (String name : names) {
			name=names[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				if (name.equals(Smoke_TaskMTA1Subject)) {

					ele=tp.getTaskNameLinkInSideMMenu(projectName, name, 15);

					if (click(driver, ele, name, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on "+name+" on Task SideMenu", YesNo.No);	
						ThreadSleep(1000);

						ThreadSleep(5000);

						ele = cp.getIsTouchPoinCheckBox(projectName,PageName.TaskPage, 10);
						if (ele!=null) {
							log(LogStatus.SKIP,"Is_Touchpoint Label is present for : "+name,YesNo.No);

							System.err.println(">>>>>>>>>>> "+ele.getAttribute("class"));
							if (ele.getAttribute("class").trim().equalsIgnoreCase("checked")) {
								log(LogStatus.SKIP,"Is_Touchpoint is checked for : "+name,YesNo.No);
							} else {
								log(LogStatus.ERROR, "Is_Touchpoint should be checked for : "+name, YesNo.Yes);
								sa.assertTrue(false,"Is_Touchpoint should be checked for : "+name );
							}

						} else {
							log(LogStatus.ERROR, "Is_Touchpoint should be present for : "+name, YesNo.Yes);
							sa.assertTrue(false,"Is_Touchpoint should be present for : "+name );

						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+name+" on Task SideMenu", YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+name+" on Task SideMenu");
					}

				} else {
					if (cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20)) {
						log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
						ThreadSleep(2000);
						//					scn.nextLine();
						try {
						ele=cp.getlastTouchPointValue(projectName, 10);

						if (ele!=null) {
							value=ele.getText().trim();
							if (cp.verifyDate(Smoke_TaskMTA1DueDate, value)) {
								log(LogStatus.INFO,"successfully verified last touch point date For : "+name, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "last touch point value is not matched For : "+name, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+name );
							}
						}else {
							log(LogStatus.ERROR, "last touch point value is not visible For : "+name, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not visible For : "+name );
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log(LogStatus.ERROR, "Exception in last touch point value For : "+name, YesNo.Yes);
						sa.assertTrue(false,"Exception in last touch point value For : "+name );
					}

					} else {
						sa.assertTrue(false,"Item Not Found : "+name+" For : "+tabName);
						log(LogStatus.SKIP,"Item Not Found : "+name+" For : "+tabName,YesNo.Yes);
					}
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AASmokeTc085_1_CreateOneMeetingTypeOption_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String parentID=null;
		boolean flag=false;
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			//					scn.nextLine();
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Activity )) {
					//					scn.nextLine();
					if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Activity, objectFeatureName.FieldAndRelationShip)) {
						//					scn.nextLine();
						if (sp.clickOnAlreadyCreatedLayout("Meeting Type")) {
							//					scn.nextLine();
							switchToFrame(driver, 10, sp.getFrame(PageName.MeetingType, 10));
							if (click(driver, sp.getNewButtonOnMeetingType(projectName, 10), "New Button on Meeting Type", action.BOOLEAN)) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.AddPickListMeetingType, 10));
								if (sendKeys(driver, sp.getMeetingTypePickListValuesTextArea(projectName,10),CreatedMeetingTypeOption, "PickList Textarea ", action.BOOLEAN)) {
											if (click(driver,sp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
												log(LogStatus.INFO, "Clicked on save button ", YesNo.No);
												ThreadSleep(5000);
												flag=true;
											}
											else {
												log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
												sa.assertTrue(false, "save button is not clickable");
											}
										
								
								}else {
									log(LogStatus.ERROR, "PickList Textarea is not visible, so cannot create meeting type option : "+CreatedMeetingTypeOption, YesNo.Yes);
									sa.assertTrue(false, "PickList Textarea is not visible, so cannot create meeting type option : "+CreatedMeetingTypeOption);
								}
							}else {
								log(LogStatus.ERROR, "New button is not clickable", YesNo.Yes);
								sa.assertTrue(false, "New button is not clickable");
							}
						}else {
							log(LogStatus.ERROR, "Meeting Type is not clickable", YesNo.Yes);
							sa.assertTrue(false, "Meeting Type layout is not clickable");
						}
						switchToDefaultContent(driver);
						

					}else {
						log(LogStatus.ERROR, "object feature "+objectFeatureName.FieldAndRelationShip+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+objectFeatureName.FieldAndRelationShip+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Activity object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Activity object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
    @Test
    public void AASmokeTc085_2_CreateOneMeetingTypeOption_Impact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		String watchListContact1 = Smoke_WATCHINS2Name;
		String watchListContact2 = Smoke_WATCHContact2FName+" "+Smoke_WATCHContact2LName;
		String watchListContact3 = Smoke_WATCHContact3FName+" "+Smoke_WATCHContact3LName;
		boolean flag=false;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, watchListContact1, 20)) {
				WebElement ele;
				//					scn.nextLine();
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Task_with_Multiple_Associations, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					//					scn.nextLine();

					ele = lp.getDropdownOnTaskPopUp(projectName, PageName.Object1Page, PageLabel.Meeting_Type.toString(), action.BOOLEAN, 20);
					if (clickUsingJavaScript(driver, ele, PageLabel.Meeting_Type.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on Drop Down Label : "+PageLabel.Meeting_Type.toString(), YesNo.No);
						ThreadSleep(2000);
						String dropDownValues="--None--,Board Meeting,Management Meeting,Monday Morning Meeting,"+CreatedMeetingTypeOption;
						compareMultipleList(driver, dropDownValues, lp.DropDownValueList(projectName,  PageName.Object2Page, PageLabel.Meeting_Type.toString(), action.BOOLEAN, 10));

					} else {
						log(LogStatus.ERROR, "Not ABle to Click on Drop Down Label : "+PageLabel.Meeting_Type.toString(), YesNo.Yes);
						sa.assertTrue(false,"Not ABle to Click on Drop Down Label : "+PageLabel.Meeting_Type.toString());

					}
				}
				else {
					log(LogStatus.ERROR, "could not click on "+ActivityTimeLineItem.New_Task_with_Multiple_Associations, YesNo.Yes);
					sa.assertTrue(false,   "could not click on "+ActivityTimeLineItem.New_Task_with_Multiple_Associations);
				}

			}else {
				log(LogStatus.ERROR, "not able to click on "+watchListContact1, YesNo.Yes);
				sa.assertTrue(false, "not able to click on "+watchListContact1);
			}
		}
		else {
			log(LogStatus.ERROR, "not able to click on Entity/Institution tab", YesNo.Yes);
			sa.assertTrue(false, "not able to click on Entity/Institution tab" );
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}
	
	@Parameters({ "projectName"})
    @Test
    public void AASmokeTc086_CreateMeetingWithNewlyAddedMeetingTypeAndVerifyItsImpact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		String watchListINS2 = Smoke_WATCHINS2Name;
		boolean flag=false;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String task=Smoke_MTTest1Subject;
		String date = todaysDate;
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, watchListINS2, 20)) {
				WebElement ele;
				//					scn.nextLine();
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object1Page, ActivityTimeLineItem.New_Meeting, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Meeting.toString(), action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					//					scn.nextLine();

					ele = cp.getHeaderTextForPage(projectName, PageName.Object2Page,PageLabel.New_Task.toString(), action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO,"New Task PopUp is opened",YesNo.No);	
					} else {
						sa.assertTrue(false,"New Task PopUp is not opened");
						log(LogStatus.SKIP,"New Task PopUp is not opened",YesNo.Yes);
					}


					ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(),true, watchListINS2, action.SCROLLANDBOOLEAN, 5);
					if (ele!=null) {
						log(LogStatus.INFO, watchListINS2+" Found For Label "+PageLabel.Related_Associations.toString(),YesNo.No);	
					} else {
						sa.assertTrue(false,watchListINS2+" not Found For Label "+PageLabel.Related_Associations.toString());
						log(LogStatus.ERROR, watchListINS2+" not Found For Label "+PageLabel.Related_Associations.toString(),YesNo.Yes);

					}
					
					String[][] dropDownLabelWithValues = {{PageLabel.Meeting_Type.toString(),Smoke_MTTest1MeetingType},
							{PageLabel.Status.toString(),Smoke_MTTest1Status}};

					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.Object2Page, task, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {	

						log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

						if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), date, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
							ThreadSleep(1000);
						}else {
							log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
						}
						ExcelUtils.writeData(date, "Task", excelLabel.Variable_Name, "AATask40", excelLabel.Due_Date);


						flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.Object3Tab, Smoke_MTFund2Name, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+Smoke_MTFund2Name+" For Label "+PageLabel.Related_Associations,YesNo.No);
							ThreadSleep(2000);

						} else {
							sa.assertTrue(false,"Not Able to Select "+Smoke_MTFund2Name+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+Smoke_MTFund2Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}


						flag = ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, WATCHCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+WATCHCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+WATCHCustomObj1Name+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+WATCHCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}

						ThreadSleep(2000);
						if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
							ThreadSleep(1000);


							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, task);
								if (actualValue.contains(expectedValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}

							clickUsingJavaScript(driver, cp.getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);

							ele = tp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Expand_All, 10);
							click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
							ThreadSleep(5000);
							
							ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Past, task, SubjectElement.SubjectLink, 10);
							String taskMessage = BasePageErrorMessage.YouHadATaskAbout+" "+Smoke_MTFund2Name+" and "+WATCHCustomObj1Name;				

							if (ele!=null) {
								log(LogStatus.INFO,task+" link present on Activity Timeline For : "+watchListINS2,YesNo.No);	
								lp.verifyActivityAtPastStep2(projectName, PageName.Object1Page, watchListINS2,task, taskMessage, date, true, Smoke_MTTest1MeetingType, true, "", 10);
							} else {
								sa.assertTrue(false,task+" link Should be present on Activity Timeline For : "+watchListINS2);
								log(LogStatus.SKIP,task+" link Should be present on Activity Timeline For : "+watchListINS2,YesNo.Yes);
							}


							if (ip.clickOnLoadMorePastActivitiesMoreDropdown(projectName, PageName.Object1Page, "View All", 10)) {

								log(LogStatus.INFO,"click on View All Link on Load More Past Activities For : "+watchListINS2,YesNo.No);

								String parentId=switchOnWindow(driver);
								if (parentId!=null) {	
									ThreadSleep(3000);

									List<String> taskstd1=new LinkedList<String>();
									taskstd1.add("");
									taskstd1.add(watchListINS2+" +2");
									taskstd1.add(Status.Completed.toString());
									taskstd1.add(crmUser1FirstName+" "+crmUser1LastName);
									taskstd1.add(Smoke_MTTest1MeetingType);
									taskstd1.add(Activity.Task.toString());
									taskstd1.add(Links.View.toString());	
									log(LogStatus.INFO,"Going for Related Tab Data Verification for "+task,YesNo.Yes);
									ip.verifyingRelatedTabData2(projectName, PageName.Object1Page, RelatedTab.Activities, date, task, taskstd1, action.BOOLEAN, 10);
									log(LogStatus.INFO,"Related Tab Data Verification done for "+task,YesNo.Yes);

									driver.close();
									driver.switchTo().window(parentId);
								}else {
									log(LogStatus.ERROR,  "could not find new window to switch after clicking on View All Link on Load More Past Activities For : "+watchListINS2, YesNo.Yes);
									sa.assertTrue(false, "could not find new window to switch after clicking on View All Link on Load More Past Activities For : "+watchListINS2);
								}

							}else {
								sa.assertTrue(false,"Not Able to click on View All Link on Load More Past Activities For : "+watchListINS2);
								log(LogStatus.SKIP,"Not Able to click on View All Link on Load More Past Activities For : "+watchListINS2,YesNo.Yes);

							}
						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created" );
						}
					}else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}


				}
				else {
					log(LogStatus.ERROR, "could not click on "+ActivityTimeLineItem.New_Meeting, YesNo.Yes);
					sa.assertTrue(false,   "could not click on "+ActivityTimeLineItem.New_Meeting);
				}

			}else {
				log(LogStatus.ERROR, "not able to click on "+watchListINS2, YesNo.Yes);
				sa.assertTrue(false, "not able to click on "+watchListINS2);
			}
		}
		else {
			log(LogStatus.ERROR, "not able to click on Entity/Institution tab", YesNo.Yes);
			sa.assertTrue(false, "not able to click on Entity/Institution tab" );
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}
	
	
	
}
	
	*/