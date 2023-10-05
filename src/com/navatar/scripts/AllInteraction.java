package com.navatar.scripts;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.ExcelUtils.readAllDataForAColumn;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.rgUserPassword;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;
import static com.navatar.generic.SmokeCommonVariables.rgOrgPassword;
import static com.navatar.generic.SmokeCommonVariables.rgContact1;
import static com.navatar.generic.SmokeCommonVariables.rgContact3;
import static com.navatar.generic.SmokeCommonVariables.rgContact2;
import static com.navatar.generic.SmokeCommonVariables.rgUser1;
import static com.navatar.generic.SmokeCommonVariables.rgUser2;
import static com.navatar.generic.SmokeCommonVariables.rgUser3;
import static com.navatar.generic.SmokeCommonVariables.rgContactPassword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import com.navatar.generic.*;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.Header;
import com.navatar.generic.EnumConstants.InstRecordType;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.MetaDataSetting;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.SortOrder;
import com.navatar.generic.EnumConstants.Stage;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.recordTypeLabel;
import com.navatar.pageObjects.*;
import com.relevantcodes.extentreports.LogStatus;

public class AllInteraction extends BaseLib {
	public static String erromessage = "No items to display";
	

	
	@Parameters({ "projectName" })
	@Test
	public void AITc001_1_createCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",
				excelLabel.User_Last_Name);
		lp.CRMLogin(superAdminUserName, adminPassword);
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
					if (setup.createPEUser(crmUser1FirstName, UserLastName, emailId, crmUserLience, crmUserProfile,
							null)) {
						log(LogStatus.INFO,
								"CRM User is created Successfully: " + crmUser1FirstName + " " + UserLastName,
								YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User1",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name,
								"User1", excelLabel.User_Last_Name);
						flag = true;
						break;

					}
					driver.close();
					driver.switchTo().window(parentWindow);

				}
			} catch (Exception e) { // TODO Auto-generated catch block
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {

			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
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
			}

		} else {

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

		}

		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
		String passwordResetLink = null;
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
			appLog.info("Password is set successfully for CRM User1: " + crmUser1FirstName + " " + UserLastName);
		} else {
			appLog.info("Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void AITc001_2_createCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser2LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User2   ",
				excelLabel.User_Last_Name);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag = false;
		for (int i = 0; i < 3; i++) {
			try {
				if (home.clickOnSetUpLink()) {
					flag = true;
					parentWindow = switchOnWindow(driver);
					if (parentWindow == null) {
						sa.assertTrue(false,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User2");
						log(LogStatus.SKIP,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User2",
								YesNo.Yes);
						exit("No new window is open after click on setup link in lighting mode so cannot create CRM User2");
					}
					if (setup.createPEUser(crmUser2FirstName, UserLastName, emailId, crmUserLience, crmUserProfile,
							null)) {
						log(LogStatus.INFO,
								"CRM User is created Successfully: " + crmUser2FirstName + " " + UserLastName,
								YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User2",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name,
								"User2", excelLabel.User_Last_Name);
						flag = true;
						break;

					}
					driver.close();
					driver.switchTo().window(parentWindow);

				}
			} catch (Exception e) { // TODO Auto-generated catch block
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {

			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				if (setup.installedPackages(crmUser1FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + crmUser2FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in CRM User2: " + crmUser2FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in CRM User2: " + crmUser2FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in CRM User2: " + crmUser2FirstName + " " + UserLastName,
							YesNo.Yes);
				}
			}

		} else {

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

		}

		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
		String passwordResetLink = null;
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
			appLog.info("Password is set successfully for CRM User2: " + crmUser2FirstName + " " + UserLastName);
		} else {
			appLog.info("Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void AITc001_3_createCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser3LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",
				excelLabel.User_Last_Name);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag = false;
		for (int i = 0; i < 3; i++) {
			try {
				if (home.clickOnSetUpLink()) {
					flag = true;
					parentWindow = switchOnWindow(driver);
					if (parentWindow == null) {
						sa.assertTrue(false,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User3");
						log(LogStatus.SKIP,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User3",
								YesNo.Yes);
						exit("No new window is open after click on setup link in lighting mode so cannot create CRM User3");
					}
					if (setup.createPEUser(crmUser2FirstName, UserLastName, emailId, crmUserLience, crmUserProfile,
							null)) {
						log(LogStatus.INFO,
								"CRM User is created Successfully: " + crmUser3FirstName + " " + UserLastName,
								YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User3",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name,
								"User3", excelLabel.User_Last_Name);
						flag = true;
						break;

					}
					driver.close();
					driver.switchTo().window(parentWindow);

				}
			} catch (Exception e) { // TODO Auto-generated catch block
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {

			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				if (setup.installedPackages(crmUser1FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + crmUser3FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in CRM User3: " + crmUser3FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in CRM User3: " + crmUser3FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in CRM User3: " + crmUser3FirstName + " " + UserLastName,
							YesNo.Yes);
				}
			}

		} else {

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

		}

		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
		String passwordResetLink = null;
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
			appLog.info("Password is set successfully for CRM User3: " + crmUser3FirstName + " " + UserLastName);
		} else {
			appLog.info("Password is not set for CRM User3: " + crmUser3FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User3: " + crmUser3FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User3: " + crmUser3FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void AITc001_4_createCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser4LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User4",
				excelLabel.User_Last_Name);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag = false;
		for (int i = 0; i < 3; i++) {
			try {
				if (home.clickOnSetUpLink()) {
					flag = true;
					parentWindow = switchOnWindow(driver);
					if (parentWindow == null) {
						sa.assertTrue(false,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User4");
						log(LogStatus.SKIP,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User4",
								YesNo.Yes);
						exit("No new window is open after click on setup link in lighting mode so cannot create CRM User4");
					}
					if (setup.createPEUser(crmUser2FirstName, UserLastName, emailId, crmUserLience, crmUserProfile,
							null)) {
						log(LogStatus.INFO,
								"CRM User is created Successfully: " + crmUser4FirstName + " " + UserLastName,
								YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User4",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name,
								"User4", excelLabel.User_Last_Name);
						flag = true;
						break;

					}
					driver.close();
					driver.switchTo().window(parentWindow);

				}
			} catch (Exception e) { // TODO Auto-generated catch block
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {

			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				if (setup.installedPackages(crmUser4FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + crmUser4FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in CRM User4: " + crmUser4FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in CRM User4: " + crmUser4FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in CRM User4: " + crmUser4FirstName + " " + UserLastName,
							YesNo.Yes);
				}
			}

		} else {

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

		}

		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
		String passwordResetLink = null;
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
			appLog.info("Password is set successfully for CRM User4: " + crmUser4FirstName + " " + UserLastName);
		} else {
			appLog.info("Password is not set for CRM User4: " + crmUser4FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User4: " + crmUser4FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User4: " + crmUser4FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AITc002_VerifyifTooltipisVisibleonEveryFieldandValue(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String ExpectedMsg = BP.SearchBox;
		String ExpectedErrorMsg = BP.acuityDefaultMessage;
		String expected[] = {Interactionslabel.Type.toString(),Interactionslabel.Date.toString(),Interactionslabel.Subject.toString(),
				Interactionslabel.Details.toString(),Interactionslabel.Participants.toString(),Interactionslabel.Tags.toString()};
		String actualDetail = null;
		
		if (fp.clickOnTab(environment, mode, TabName.Interactions)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Interactions, YesNo.No);
			ThreadSleep(3000);
			for(int i=0;i<expected.length;i++) {
				WebElement ele = BP.listOfIntrectionHeaderDetail(10).get(i);
				actualDetail = getText(driver, ele, "Intrection detail",action.SCROLLANDBOOLEAN);
				if(actualDetail.contains(expected[i])) {
				log(LogStatus.INFO,actualDetail+ " matches with " +expected[i],YesNo.No);
			
			}else {
				log(LogStatus.ERROR, actualDetail+"not matches with "+expected[i], YesNo.Yes);
				sa.assertTrue(false, actualDetail+"not matches with "+expected[i]);
			}
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Interactions + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Interactions + " tab");
		}
		
		String actualMsg  = BP.getsearchbox(10).getAttribute("placeholder");
		if (actualMsg.equals(ExpectedMsg)) {
									log(LogStatus.INFO,
											"Actual result " + actualMsg
													+ " of pop up has been matched with Expected result : " + ExpectedMsg,
											YesNo.No);
								} else {
									log(LogStatus.ERROR,
											"Actual result " + actualMsg
													+ " of pop up has been not matched with Expected result : "
													+ ExpectedMsg,
											YesNo.No);
									sa.assertTrue(false,"Actual result " + actualMsg
											+ " of pop up has been not matched with Expected result : "
											+ ExpectedMsg);
								}
		
		 String  errormsg  = BP.getErrorMsg(10).getText();
		 if(errormsg.contains(ExpectedErrorMsg)) {
				log(LogStatus.INFO,
						"Actual result " + ExpectedErrorMsg
								+ " of pop up has been matched with Expected result : " + ExpectedMsg
								+ " for Contact Name: ",YesNo.No);
	                } else {
		log(LogStatus.ERROR,
				"Actual result " + errormsg
						+ " of pop up has been not matched with Expected result : "
						+ ExpectedErrorMsg,
				YesNo.No);
		sa.assertTrue(false,"Actual result " + errormsg
				+ " of pop up has been not matched with Expected result : "
				+ ExpectedErrorMsg);
	}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	/*
	 * Upload the CSV files
	 * */
	
	@Test
	public void AITc00CSV_1_CreateAccountByApi() {

		String filePath =System.getProperty("user.dir")+"\\API Files\\All Intractions\\Account.xlsx";
		String sheetName="Account";
		new APIUtils(crmUser1EmailID).AccountObjectDataUpload(filePath, sheetName);

	}

	@Test
	public void AITc00CSV_2_CreateContactByApi() {

		String filePath =System.getProperty("user.dir")+"\\API Files\\All Intractions\\Contact.xlsx";
		String sheetName="Contact";
		new APIUtils(crmUser1EmailID).ContactObjectDataUpload(filePath, sheetName);

	}

 

	@Test
	public void AITc00CSV_3_CreateDealByApi() {

		String filePath =System.getProperty("user.dir")+"\\API Files\\All Intractions\\Deal.xlsx";
		String sheetName="Deal";
		new APIUtils(crmUser1EmailID).DealObjectDataUpload(filePath, sheetName);

	}
	
	@Test
	public void AITc00CSV_4_CreateFundByApi() {

		String filePath =System.getProperty("user.dir")+"\\API Files\\All Intractions\\Fund.xlsx";
		String sheetName="Fund";
		new APIUtils(crmUser1EmailID).FundObjectDataUpload(filePath, sheetName);

	}
	
	@Test
	public void AITc00CSV_5_CreateFundraisingByApi() {

		String filePath =System.getProperty("user.dir")+"\\API Files\\All Intractions\\Fundraising.xlsx";
		String sheetName="Fundraising";
		new APIUtils(crmUser1EmailID).FundraisingObjectDataUpload(filePath, sheetName);

	}
	
	@Test
	public void AITc00CSV_6_CreateThemeByApi() {

		String filePath =System.getProperty("user.dir")+"\\API Files\\All Intractions\\Theme.xlsx";
		String sheetName="Theme";
		new APIUtils(crmUser1EmailID).ThemeObjectDataUpload(filePath, sheetName);

	}
	
	@Test
	public void AITc00CSV_7_CreateEventByApi() {

		String filePath =System.getProperty("user.dir")+"\\API Files\\All Intractions\\Event.xlsx";
		String sheetName="Event";
		new APIUtils(crmUser1EmailID).EventObjectDataUpload(filePath, sheetName);

	}
	
	
	@Parameters({ "projectName"})
	@Test
	public void AITc003_CreateTask(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		

		String activityType1=AI_ATActivityType1;
		String taskSubject1=AI_ATSubject1;
		String taskRelatedTo1=AI_ATRelatedTo1;
		String taskNotes1=AI_ATNote1;
		String dueDateDay1=AI_ATDay1;
		String taskDueDate1 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDateDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"AI_001", excelLabel.Advance_Due_Date);
		String assignedTo1=crmUser1FirstName+" "+crmUser1LastName;
//		if(assignedTo1.equalsIgnoreCase("User1"))
//		{
//			assignedTo1=UserName1;
//		}
//		else if(assignedTo1.equalsIgnoreCase("Admin"))
//		{
//			assignedTo1=Admin;
//		}



		String activityType2=AI_ATActivityType2;
		String taskSubject2=AI_ATSubject2;
		String taskRelatedTo2=AI_ATRelatedTo2;
		String taskNotes2=AI_ATNote2;
		String dueDateDay2=AI_ATDay2;
		String taskDueDate2 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDateDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"AI_002", excelLabel.Advance_Due_Date);
		String assignedTo2=crmUser2FirstName+" "+crmUser2LastName;
//		if(assignedTo2.equalsIgnoreCase("User2"))
//		{
//			assignedTo2=UserName1;
//		}
//		else if(assignedTo2.equalsIgnoreCase("Admin"))
//		{
//			assignedTo2=Admin;
//		}

		String activityType3=AI_ATActivityType3;
		String taskSubject3=AI_ATSubject3;
		String taskRelatedTo3=AI_ATRelatedTo3;
		String taskNotes3=AI_ATNote3;
		String dueDateDay3=AI_ATDay3;
		String taskDueDate3 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDateDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"AI_003", excelLabel.Advance_Due_Date);
		String assignedTo3=crmUser1FirstName+" "+crmUser1LastName;
//		if(assignedTo3.equalsIgnoreCase("User1"))
//		{
//			assignedTo3=UserName1;
//		}
//		else if(assignedTo3.equalsIgnoreCase("Admin"))
//		{
//			assignedTo3=Admin;
//		}

		String activityType4=AI_ATActivityType4;
		String taskSubject4=AI_ATSubject4;
		String taskRelatedTo4=AI_ATRelatedTo4;
		String taskNotes4=AI_ATNote4;
		String dueDateDay4=AI_ATDay4;
		String taskDueDate4 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDateDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"AI_004", excelLabel.Advance_Due_Date);
		String assignedTo4=crmUser1FirstName+" "+crmUser1LastName;
//		if(assignedTo4.equalsIgnoreCase("User1"))
//		{
//			assignedTo4=UserName1;
//		}
//		else if(assignedTo4.equalsIgnoreCase("Admin"))
//		{
//			assignedTo4=Admin;
//		}


		String activityType5=AI_ATActivityType5;
		String taskSubject5=AI_ATSubject5;
		String taskRelatedTo5=AI_ATRelatedTo5;
		String taskNotes5=AI_ATNote5;
		String dueDateDay5=AI_ATDay5;
		String taskDueDate5 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDateDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"AI_005", excelLabel.Advance_Due_Date);
		String assignedTo5=crmUser1FirstName+" "+crmUser1LastName;
//		if(assignedTo5.equalsIgnoreCase("User1"))
//		{
//			assignedTo5=UserName1;
//		}
//		else if(assignedTo5.equalsIgnoreCase("Admin"))
//		{
//			assignedTo5=Admin;
//		}

		String activityType6=AI_ATActivityType6;
		String taskSubject6=AI_ATSubject6;
		String taskRelatedTo6=AI_ATRelatedTo6;
		String taskNotes6=AI_ATNote6;
		String dueDateDay6=AI_ATDay6;
		String taskDueDate6 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDateDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"AI_006", excelLabel.Advance_Due_Date);
		String assignedTo6=crmUser2FirstName+" "+crmUser2LastName;
//		if(assignedTo6.equalsIgnoreCase("User2"))
//		{
//			assignedTo6=UserName1;
//		}
//		else if(assignedTo6.equalsIgnoreCase("Admin"))
//		{
//			assignedTo6=Admin;
//		}

		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Due Date", taskDueDate1 }, {"Assigned To", assignedTo1} };

		String[][] basicsection2 = { { "Subject", taskSubject2 }, { "Notes", taskNotes2 }, { "Related_To", taskRelatedTo2 } };
		String[][] advanceSection2 = { { "Due Date", taskDueDate2 }, {"Assigned To", assignedTo2}};

		String[][] basicsection3 = { { "Subject", taskSubject3 }, { "Notes", taskNotes3 }, { "Related_To", taskRelatedTo3 } };
		String[][] advanceSection3 = { { "Due Date", taskDueDate3 }, {"Assigned To", assignedTo3}};

		String[][] basicsection4 = { { "Subject", taskSubject4 }, { "Notes", taskNotes4 }, { "Related_To", taskRelatedTo4 } };
		String[][] advanceSection4 = { { "Due Date", taskDueDate4 }, {"Assigned To", assignedTo4}};

		String[][] basicsection5 = { { "Subject", taskSubject5 }, { "Notes", taskNotes5 }, { "Related_To", taskRelatedTo5 } };
		String[][] advanceSection5 = { { "Due Date", taskDueDate5 }, {"Assigned To", assignedTo5}};

		String[][] basicsection6 = { { "Subject", taskSubject6 }, { "Notes", taskNotes6 }, { "Related_To", taskRelatedTo6 } };
		String[][] advanceSection6 = { { "Due Date", taskDueDate6 }, {"Assigned To", assignedTo6}};


		

		if (bp.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}	 

		if (bp.createActivityTimeline(projectName, true, activityType2, basicsection2, advanceSection2, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject2, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject2);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject2, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject2);
		}	

		if (bp.createActivityTimeline(projectName, true, activityType3, basicsection3, advanceSection3, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject3, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject3);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject3, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject3);
		}	

		if (bp.createActivityTimeline(projectName, true, activityType4, basicsection4, advanceSection4, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject4, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject4);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject4, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject4);
		}	

		if (bp.createActivityTimeline(projectName, true, activityType5, basicsection5, advanceSection5, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject5, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject5);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject5, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject5);
		}	

		if (bp.createActivityTimeline(projectName, true, activityType6, basicsection6, advanceSection6, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject6, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject6);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject6, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject6);
		}	

		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	
	@Parameters({ "projectName" })
	@Test
	public void AITc004_1_VerifySearchifsharingSettingwillbeprivateforuser(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Sharing_Settings.toString())) {
//					log(LogStatus.INFO, "click on Object : " + object.valueOf("Sharing Settings"), YesNo.No);
					ThreadSleep(2000);
					switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
					log(LogStatus.INFO,"Frame switch",YesNo.No);
					ThreadSleep(5000);
					if(clickUsingJavaScript(driver, sp.geteditbutton(30), "edit button", action.BOOLEAN))
						log(LogStatus.INFO, "able to click on edit button", YesNo.No);
					switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
					log(LogStatus.INFO,"Frame switch",YesNo.No);
					ThreadSleep(5000);
					if (selectVisibleTextFromDropDown(driver, sp.getactivityComponentDropdown(10),
							"acivity component dropdown", "Private")) {
						log(LogStatus.INFO,
								"Select custom field text in activity component dropdown in override setup page",
								YesNo.No);
						ThreadSleep(5000);
						if (clickUsingJavaScript(driver, sp.getCreateUserSaveBtn_Lighting(30), "Save Button",
								action.SCROLLANDBOOLEAN)) {
							appLog.info("clicked on save button");
							ThreadSleep(5000);
						} else {
							log(LogStatus.ERROR, "Not able to clicked on save button", YesNo.Yes);
							sa.assertTrue(false, "Not able to clicked on save button");
						}
						} else {
							log(LogStatus.ERROR, "Not able to Select custom field text in activity component dropdown in override setup page", YesNo.Yes);
							sa.assertTrue(false, "Not able to Select custom field text in activity component dropdown in override setup page");
						}
						driver.close();
						driver.switchTo().window(parentID);
					} else {
						log(LogStatus.ERROR,
								"Not able to select text: Custom Field in  setup component dropdown in override page",
								YesNo.Yes);
						sa.assertTrue(false,
								"Not able to select text: Custom Field in  setup component dropdown in override page");
					}

				} else {

					log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Sharing Settings"), YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Sharing Settings"));
				}
		} else {
			sa.assertTrue(false, "setup link is not clickable");
			log(LogStatus.SKIP, "setup link is not clickable", YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName" })
	@Test
	public void AITc004_2_VerifySearchifsharingSettingwillbeprivateforuser(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String expected[] = {AI_ATSubject1,AI_ATSubject2,AI_ATSubject6,AI_ATSubject3,AI_ATSubject4,AI_ATSubject5,"DanInteract Event1","DanInteract Event3"};
		String actualDetail = null;
		
		if (fp.clickOnTab(environment, mode, TabName.Interactions)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Interactions, YesNo.No);
			ThreadSleep(3000);
			for(int i=0;i<expected.length;i++) {
				WebElement ele = BP.listOfIntrectionDetail(10).get(i);
				actualDetail = getText(driver, ele, "Intrection detail",action.SCROLLANDBOOLEAN);
				if(actualDetail.contains(expected[i])) {
				log(LogStatus.INFO,actualDetail+ " matches with " +expected[i],YesNo.No);
			
			}else {
				log(LogStatus.ERROR, actualDetail+"not matches with "+expected[i], YesNo.Yes);
				sa.assertTrue(false, actualDetail+"not matches with "+expected[i]);
			}
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Interactions + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Interactions + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void AITc004_3_VerifySearchifsharingSettingwillbeprivateforuser(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Sharing_Settings.toString())) {
//					log(LogStatus.INFO, "click on Object : " + object.valueOf("Sharing Settings"), YesNo.No);
					ThreadSleep(2000);
					switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
					log(LogStatus.INFO,"Frame switch",YesNo.No);
					ThreadSleep(5000);
					if(clickUsingJavaScript(driver, sp.geteditbutton(30), "edit button", action.BOOLEAN))
						log(LogStatus.INFO, "able to click on edit button", YesNo.No);
					switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
					log(LogStatus.INFO,"Frame switch",YesNo.No);
					ThreadSleep(5000);
					if (selectVisibleTextFromDropDown(driver, sp.getactivityComponentDropdown(10),
							"acivity component dropdown", "Controlled by Parent")) {
						log(LogStatus.INFO,
								"Select custom field text in activity component dropdown in override setup page",
								YesNo.No);
						ThreadSleep(5000);
						if (clickUsingJavaScript(driver, sp.getCreateUserSaveBtn_Lighting(30), "Save Button",
								action.SCROLLANDBOOLEAN)) {
							appLog.info("clicked on save button");
							ThreadSleep(5000);
						} else {
							log(LogStatus.ERROR, "Not able to clicked on save button", YesNo.Yes);
							sa.assertTrue(false, "Not able to clicked on save button");
						}
						} else {
							log(LogStatus.ERROR, "Not able to Select custom field text in activity component dropdown in override setup page", YesNo.Yes);
							sa.assertTrue(false, "Not able to Select custom field text in activity component dropdown in override setup page");
						}
						driver.close();
						driver.switchTo().window(parentID);
					} else {
						log(LogStatus.ERROR,
								"Not able to select text: Custom Field in  setup component dropdown in override page",
								YesNo.Yes);
						sa.assertTrue(false,
								"Not able to select text: Custom Field in  setup component dropdown in override page");
					}

				} else {

					log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Sharing Settings"), YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Sharing Settings"));
				}
		} else {
			sa.assertTrue(false, "setup link is not clickable");
			log(LogStatus.SKIP, "setup link is not clickable", YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AITc005_VerifyResearchFunctionalityForValidData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		ThreadSleep(2000);
		List<String> list=new ArrayList<>();
		String ExpectedMsg = bp.ErrorMessageAcuity;
		
		if (fp.clickOnTab(environment, mode, TabName.Interactions)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Interactions, YesNo.No);
			ThreadSleep(3000);
			String[] searchValues = readAllDataForAColumn(ResearchDataSheetFilePath, "ALLInteraction" ,0,false).split("<break>");
		for(String searchValue : searchValues) {
//			for(int i =0; i <=30; i++) {
			String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"ALLInteraction",excelLabel.Variable_Name, searchValue, excelLabel.Name);
		    log(LogStatus.PASS, "WOrking for " + varibale, YesNo.Yes);
		    
		    String Categories =ExcelUtils.readData(BaseLib.ResearchDataSheetFilePath,"ALLInteraction",excelLabel.Variable_Name, searchValue, excelLabel.All_Categories);
		    log(LogStatus.PASS, "WOrking for " + searchValue, YesNo.Yes);
		    String [] expected = Categories.split("<Break>");
		    
		    if (sendKeys(driver, bp.getsearchIcon_Interaction(20), varibale + "\n", "Search Icon Text",
					action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+varibale, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+varibale);
		}
	
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ varibale + "---------",
				YesNo.No);

		if(bp.getNotext(5) != null){
			log(LogStatus.PASS, "There is no data retaled to " + varibale, YesNo.No);
	
		}  else if(varibale.length()<2 || varibale == null || varibale == " ") {
		 String  errormsg  = bp.getAllInteractionErrorMsg(10).getText();
		 errormsg.contains(ExpectedMsg);
				log(LogStatus.INFO,
						"Actual result " + errormsg
								+ " of pop up has been matched with Expected result : " + ExpectedMsg
								+ " for Contact Name: ",YesNo.No);
		}else if(varibale.length()>=2 ){
		list=	compareMultipleListWithBreak(driver,Categories, bp.listOfIntrectionDetail(10));

			if(list.isEmpty()) {
				
				log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			} else {
				log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
			}	
		}
		else 
			 {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ varibale + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ varibale + "---------");
				
		}
		
		refresh(driver);
		
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Interactions, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Interactions);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		
		}
	
	@Parameters({ "projectName" })
	@Test
	public void AITc006_VerifyInteractionsapppagewhenUpdatetheTaskandEventusingEditbutton(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String taskSubject1=AI_ATSubject1 +"Updated";
		String[][] basicsection = { { "Subject", taskSubject1 }};
		String event = "DanInteract Event1";
		String Updatedevent = event +"Updated";
		String[][] basicsection1 = { { "Subject", Updatedevent }};
		if (fp.clickOnTab(environment, mode, TabName.Interactions)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Interactions, YesNo.No);
			ThreadSleep(3000);
			if (fp.clickOnAlreadyCreatedItemInteraction(projectName, AI_ATSubject1, 30)) {
				log(LogStatus.INFO, "Click on Tab : " + AI_ATSubject1, YesNo.No);
				if (CommonLib.click(driver, BP.getPopUpEditbutton(30), "Edit button: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit button", YesNo.No);
					if (BP.updateActivityTimelineRecord(projectName, basicsection, null, null, null, null)) {
						log(LogStatus.PASS, "Name successfully upated of task :" + taskSubject1, YesNo.Yes);
					} else {

						sa.assertTrue(false, "Not Able to update Name  of task :" + taskSubject1);
						log(LogStatus.SKIP, "Not Able to update Name  of task :" + taskSubject1, YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Edit button : " + taskSubject1,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on Edit button : " + taskSubject1);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + AI_ATSubject1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + AI_ATSubject1 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Interactions, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Interactions);
		}
		refresh(driver);
		if (fp.clickOnTab(environment, mode, TabName.Interactions)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Interactions, YesNo.No);
			ThreadSleep(3000);
			if (fp.clickOnAlreadyCreatedItemInteraction(projectName, event, 30)) {
				log(LogStatus.INFO, "Click on Tab : " + event, YesNo.No);
				if (CommonLib.click(driver, BP.getPopUpEditbutton(30), "Edit button: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit button", YesNo.No);
					if (BP.updateActivityTimelineRecord(projectName, basicsection1, null, null, null, null)) {
						log(LogStatus.PASS, "Name successfully upated of task :" + Updatedevent, YesNo.Yes);
					} else {

						sa.assertTrue(false, "Not Able to update Name  of task :" + Updatedevent);
						log(LogStatus.SKIP, "Not Able to update Name  of task :" + Updatedevent, YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Edit button : " + event,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on Edit button : " + event);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + event + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + event + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Interactions, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Interactions);
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AITc007_VerifyResearchFunctionalityForValidData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		ThreadSleep(2000);
		List<String> list=new ArrayList<>();
		String ExpectedMsg = bp.ErrorMessageAcuity;
		
		if (fp.clickOnTab(environment, mode, TabName.Interactions)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Interactions, YesNo.No);
			ThreadSleep(3000);
			String[] searchValues = readAllDataForAColumn(ResearchDataSheetFilePath, "ALLInteraction1" ,0,false).split("<break>");
		for(String searchValue : searchValues) {
//			for(int i =0; i <=30; i++) {
			String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"ALLInteraction1",excelLabel.Variable_Name, searchValue, excelLabel.Name);
		    log(LogStatus.PASS, "WOrking for " + varibale, YesNo.Yes);
		    
		    String Categories =ExcelUtils.readData(BaseLib.ResearchDataSheetFilePath,"ALLInteraction1",excelLabel.Variable_Name, searchValue, excelLabel.All_Categories);
		    log(LogStatus.PASS, "WOrking for " + searchValue, YesNo.Yes);
		    String [] expected = Categories.split("<Break>");
		    
		    if (sendKeys(driver, bp.getsearchIcon_Interaction(20), varibale + "\n", "Search Icon Text",
					action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+varibale, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+varibale);
		}
	
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ varibale + "---------",
				YesNo.No);
	
		if(bp.getNotext(5) != null){
			log(LogStatus.PASS, "There is no data retaled to " + varibale, YesNo.No);
	
		}  else if(varibale.length()<2 || varibale == null || varibale == " ") {
		 String  errormsg  = bp.getAllInteractionErrorMsg(10).getText();
		 errormsg.contains(ExpectedMsg);
				log(LogStatus.INFO,
						"Actual result " + errormsg
								+ " of pop up has been matched with Expected result : " + ExpectedMsg
								+ " for Contact Name: ",YesNo.No);
		}else if(varibale.length()>=2 ){
		list=	compareMultipleListWithBreak(driver,Categories, bp.listOfIntrectionDetail(10));

			if(list!=null) {
				
				log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			} else {
				log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
			}	
		}
		else 
			 {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ varibale + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ varibale + "---------");
				
		}
		
		refresh(driver);
			
		
		
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Interactions, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Interactions);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		
		}
	
	@Parameters({ "projectName" })
	@Test
	public void AITc008_VerifyInteractionsapppagewhenUpdatetheTaskandEventusingEditbutton(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String task=AI_ATSubject1 +"Updated";
		String[][] basicsection = { { "Subject", AI_ATSubject1 }};
		String event = "DanInteract Event1";
		String Updatedevent = event +"Updated";
		String[][] basicsection1 = { { "Subject", event }};
		if (fp.clickOnTab(environment, mode, TabName.Interactions)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Interactions, YesNo.No);
			ThreadSleep(3000);
			if (fp.clickOnAlreadyCreatedItemInteraction(projectName, task, 30)) {
				log(LogStatus.INFO, "Click on Tab : " + task, YesNo.No);
				if (CommonLib.click(driver, BP.getPopUpEditbutton(30), "Edit button: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit button", YesNo.No);
					if (BP.updateActivityTimelineRecord(projectName, basicsection, null, null, null, null)) {
						log(LogStatus.PASS, "Name successfully upated of task :" + task, YesNo.Yes);
					} else {

						sa.assertTrue(false, "Not Able to update Name  of task :" + task);
						log(LogStatus.SKIP, "Not Able to update Name  of task :" + task, YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Edit button : " + task,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on Edit button : " + task);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + AI_ATSubject1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + AI_ATSubject1 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Interactions, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Interactions);
		}
		refresh(driver);
		if (fp.clickOnTab(environment, mode, TabName.Interactions)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Interactions, YesNo.No);
			ThreadSleep(3000);
			if (fp.clickOnAlreadyCreatedItemInteraction(projectName, Updatedevent, 30)) {
				log(LogStatus.INFO, "Click on Tab : " + Updatedevent, YesNo.No);
				if (CommonLib.click(driver, BP.getPopUpEditbutton(30), "Edit button: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit button", YesNo.No);
					if (BP.updateActivityTimelineRecord(projectName, basicsection1, null, null, null, null)) {
						log(LogStatus.PASS, "Name successfully upated of task :" + Updatedevent, YesNo.Yes);
					} else {

						sa.assertTrue(false, "Not Able to update Name  of task :" + Updatedevent);
						log(LogStatus.SKIP, "Not Able to update Name  of task :" + Updatedevent, YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Edit button : " + event,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on Edit button : " + event);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + event + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + event + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Interactions, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Interactions);
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void AITc011_VerifyDefaultSortingOrderOfGrid (String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		if (fp.clickOnTab(environment, mode, TabName.Interactions)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Interactions, YesNo.No);
			ThreadSleep(3000);
			List<WebElement> ele = BP.getDateAllInteraction(10);
			if (CommonLib.checkSortingforDate(driver, SortOrder.Decending, ele)) {
				log(LogStatus.PASS, "-----------Deal Column is in Descending Order By Default  --------------",
						YesNo.No);
				sa.assertTrue(true, "-----------Deal Column is in Descending Order By Default --------------");
			} else {
				log(LogStatus.ERROR, "-----------Deal Column is not in Descending Order By Default  --------------",
						YesNo.Yes);
				sa.assertTrue(false,
						"-----------Deal Column is not in Descending Order By Default  --------------");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Interactions, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Interactions);
		}
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();
		}
	
	@Parameters({ "projectName" })
	@Test
	public void AITc009_CreateTask(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

	


		String activityType1=AI_ATActivityType7;
		String taskSubject1=AI_ATSubject7;
		String taskRelatedTo1=AI_ATRelatedTo7;
		String taskNotes1=AI_ATNote7;
		String dueDateDay1=AI_ATDay7;
		String taskDueDate1 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDateDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"AI_007", excelLabel.Advance_Due_Date);
		String assignedTo1=crmUser1FirstName+" "+crmUser1LastName;
		
		String activityType2=AI_ATActivityType8;
		String taskSubject2=AI_ATSubject8;
		String taskRelatedTo2=AI_ATRelatedTo8;
		String taskNotes2=AI_ATNote8;
		String dueDateDay2=AI_ATDay8;
		String taskDueDate2 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDateDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"AI_008", excelLabel.Advance_Due_Date);
		String assignedTo2=crmUser1FirstName+" "+crmUser1LastName;
		
		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Due Date", taskDueDate1 }, {"Assigned To", assignedTo1} };
		
		String[][] basicsection2 = { { "Subject", taskSubject2 }, { "Notes", taskNotes2 }, { "Related_To", taskRelatedTo2 } };
		String[][] advanceSection2 = { { "Due Date", taskDueDate2 }, {"Assigned To", assignedTo2} };
	
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (bp.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}
		
		if (bp.createActivityTimeline(projectName, true, activityType2, basicsection2, advanceSection2, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject2, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject2);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject2, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject2);
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void AITc010_VerifyResearchFunctionalityForValidData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		ThreadSleep(2000);
	List<String> list=new ArrayList<>();
		String ExpectedMsg = bp.ErrorMessageAcuity;
		
		if (fp.clickOnTab(environment, mode, TabName.Interactions)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Interactions, YesNo.No);
			ThreadSleep(3000);
			String[] searchValues = readAllDataForAColumn(ResearchDataSheetFilePath, "ALLInteraction2" ,0,false).split("<break>");
		for(String searchValue : searchValues) {
//			for(int i =0; i <=30; i++) {
			String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"ALLInteraction2",excelLabel.Variable_Name, searchValue, excelLabel.Name);
		    log(LogStatus.PASS, "WOrking for " + varibale, YesNo.Yes);
		    
		    String Categories =ExcelUtils.readData(BaseLib.ResearchDataSheetFilePath,"ALLInteraction2",excelLabel.Variable_Name, searchValue, excelLabel.All_Categories);
		    log(LogStatus.PASS, "WOrking for " + searchValue, YesNo.Yes);
		    String [] expected = Categories.split("<Break>");
		    
		    if (sendKeys(driver, bp.getsearchIcon_Interaction(20), varibale + "\n", "Search Icon Text",
					action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+varibale, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+varibale);
		}
	
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ varibale + "---------",
				YesNo.No);
		if(bp.getNotext(5) != null){
			log(LogStatus.PASS, "There is no data retaled to " + varibale, YesNo.No);
	
		}  else if(varibale.length()<2 || varibale == null || varibale == " ") {
		 String  errormsg  = bp.getAllInteractionErrorMsg(10).getText();
		 errormsg.contains(ExpectedMsg);
				log(LogStatus.INFO,
						"Actual result " + errormsg
								+ " of pop up has been matched with Expected result : " + ExpectedMsg
								+ " for Contact Name: ",YesNo.No);
		}else if(varibale.length()>=2 ){
		list=	compareMultipleListWithBreak(driver,Categories, bp.listOfIntrectionDetail(10));
		

			if(list.isEmpty()) {
				
				log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			} else {
				log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
			}	
		}
		else 
			 {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ varibale + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ varibale + "---------");
				
		}
		
		refresh(driver);
			
		
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Interactions, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Interactions);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		
		}
	
	@Parameters({ "projectName"})
	@Test
	public void AITc012_1_VerifySearchTaskandEventisDeleted(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag = false;
		WebElement ele = null;
		String task = "DanInteract Event4";
		String task1 = AI_ATSubject8;
		if (home.globalSearchAndDeleteTaskorCall(task, "Events", false)) {

			log(LogStatus.INFO, "-----Verified Task named: " + task + " found and delete in Tasks Object-----",
					YesNo.No);
			flag = true;
		} else {

			log(LogStatus.ERROR, "-----Task named: " + task + " not deleted in Tasks Object-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Task named: " + task + " not deleted in Tasks Object-----");

		}
		refresh(driver);
		
		if (home.globalSearchAndDeleteTaskorCall(task1, "Tasks", false)) {

			log(LogStatus.INFO, "-----Verified Task named: " + task + " found and delete in Tasks Object-----",
					YesNo.No);
			flag = true;
		} else {

			log(LogStatus.ERROR, "-----Task named: " + task + " not deleted in Tasks Object-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Task named: " + task + " not deleted in Tasks Object-----");

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		
		}
	
	@Parameters({ "projectName"})
	@Test
	public void AITc012_2_VerifySearchTaskandEventisDeleted(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		ThreadSleep(2000);
	List<String> list=new ArrayList<>();
		String ExpectedMsg = bp.ErrorMessageAcuity;
		String subject = "DanInteract TaskDeal<break>DanInteract Event4";
		if (fp.clickOnTab(environment, mode, TabName.Interactions)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Interactions, YesNo.No);
			ThreadSleep(3000);
			String[] searchValues = subject.split("<break>");
		for(String searchValue : searchValues) {
		    log(LogStatus.PASS, "WOrking for " + searchValue, YesNo.Yes);
		    
		    String Categories =ExcelUtils.readData(BaseLib.ResearchDataSheetFilePath,"Others",excelLabel.Variable_Name, searchValue, excelLabel.Record_Type);
		    log(LogStatus.PASS, "WOrking for " + searchValue, YesNo.Yes);
		    String [] expected = Categories.split("<Break>");
		    
		    if (sendKeys(driver, bp.getsearchIcon_Interaction(20), searchValue + "\n", "Search Icon Text",
					action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
	
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
		if(bp.getNotext(5) != null){
			log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
	
		}  else if(searchValue.length()<2 || searchValue == null || searchValue == " ") {
		 String  errormsg  = bp.getAllInteractionErrorMsg(10).getText();
		 errormsg.contains(ExpectedMsg);
				log(LogStatus.INFO,
						"Actual result " + errormsg
								+ " of pop up has been matched with Expected result : " + ExpectedMsg
								+ " for Contact Name: ",YesNo.No);
		}else if(searchValue.length()>=2 ){
		list=	compareMultipleListWithBreak(driver,Categories, bp.listOfIntrectionDetail(10));
		

			if(list.isEmpty()) {
				
				log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			} else {
				log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
			}	
		}
		else 
			 {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
		
		refresh(driver);
			
		
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Interactions, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Interactions);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		
		}
	
	@Parameters({ "projectName"})
	@Test
	public void AITc013_1_VerifyImpactonTaskandeventswhenCRMuserwillgetInactive(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	
		String parentWindow = null;
		String userfirstname = crmUser2FirstName;
		String UserLastName = crmUser2LastName;
		String emailId = crmUser2EmailID;
		
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag = false;
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
					if (setup.editPEUser( userfirstname, UserLastName, emailId)) {
						log(LogStatus.INFO, "CRM User is updated Successfully: " + crmUser1FirstName + " " + UserLastName, YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User2",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User2",
								excelLabel.User_Last_Name);
					} else {
						appLog.error("CRM User not updated Successfully:  " + crmUser1FirstName + " " + UserLastName);
						flag = true;
					}

					
					driver.close();
					driver.switchTo().window(parentWindow);

				}
				switchToDefaultContent(driver);
				lp.CRMlogout();
				sa.assertAll();
				
				}
	@Parameters({ "projectName"})
	@Test
	public void AITc013_2_VerifyImpactonTaskandeventswhenCRMuserwillgetInactive(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String ATCERecord1 = "All Interactions";
		String[] subject ={"DanInteract Event2"};
		String[] icon ={null};
		String[] date ={null};
		String[] details ={null};
		String[][] participant1= {{crmUser2FirstName+" "+crmUser2LastName}};
	
		if (fp.clickOnTab(environment, mode, TabName.Interactions)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Interactions, YesNo.No);
			ThreadSleep(3000);
		
		ArrayList<String> result2=bp.verifyRecordsonInteractionsonAllInteraction(ATCERecord1,icon,date, subject, details, subject, participant1, null);
		if(result2.isEmpty())
		{
			log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
		}
		else
		{
			log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result2, YesNo.No);
			sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result2);
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Interactions, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Interactions);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		
		}
}

