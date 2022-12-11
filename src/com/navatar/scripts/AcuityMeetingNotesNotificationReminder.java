
package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;

import com.navatar.pageObjects.LoginPageBusinessLayer;

import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.OutlookPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;

import com.relevantcodes.extentreports.LogStatus;

public class AcuityMeetingNotesNotificationReminder extends BaseLib {

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc045_1_CreateCRMUser1(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		String profile = "Deal Only";

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
					if (setup.createPEUser(crmUser1FirstName, UserLastName, emailId, crmUserLience, profile, "")) {
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
			} catch (Exception e) {
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {

			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				switchToDefaultContent(driver);
				CommonLib.ThreadSleep(5000);
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
//		driver.switchTo().window(parentWindow);
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
		String passwordResetLink = null;
		try {
			passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
					ExcelUtils.readDataFromPropertyFile("gmailUserName"),
					ExcelUtils.readDataFromPropertyFile("gmailPassword"));
		} catch (InterruptedException e2) {
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
	public void AcuityMNNRTc045_2_CreateCRMUser2(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser2LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		String profile = "PE Standard User";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
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
					if (setup.createPEUser(crmUser2FirstName, UserLastName, emailId, crmUserLience, profile, "")) {
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
			} catch (Exception e) {
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {

			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				switchToDefaultContent(driver);
				CommonLib.ThreadSleep(5000);
				if (setup.installedPackages(crmUser2FirstName, UserLastName)) {
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
	public void AcuityMNNRTc045_3_CreateCRMUser3(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser3LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		String profile = "IR Only";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
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
					if (setup.createPEUser(crmUser3FirstName, UserLastName, emailId, crmUserLience, profile, "")) {
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
			} catch (Exception e) {
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {

			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				switchToDefaultContent(driver);
				CommonLib.ThreadSleep(5000);
				if (setup.installedPackages(crmUser3FirstName, UserLastName)) {
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
	public void AcuityMNNRTc045_4_CreateCRMUser4(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser4LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		String profile = "PE Standard User";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
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
					if (setup.createPEUser(crmUser4FirstName, UserLastName, emailId, crmUserLience, profile, "")) {
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
			} catch (Exception e) {
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {

			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				switchToDefaultContent(driver);
				CommonLib.ThreadSleep(5000);
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
	public void AcuityMNNRTc045_CreateTheRecordsFromDataSheetAndVerifyInTheOrg(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);

		String[] fundNames = "Mutual Fund<Break>Sumo Kind Fund".split("<Break>", -1);
		String[] fundTypes = "Fund<Break>Fund".split("<Break>", -1);
		String[] investmentCategories = "Fund<Break>Fund".split("<Break>", -1);
		String otherLabelFields = null;
		String otherLabelValues = null;

		String[] fundraisingNames = "FC Fundraising<Break>Sumo Kind Fundraising".split("<Break>", -1);
		String[] fundraisingsFundName = "Mutual Fund<Break>Sumo Kind Fund".split("<Break>", -1);
		String[] fundraisingsInstitutionName = "Acc 4<Break>Acc 12".split("<Break>", -1);

		String dealRecordTypes = null;
		String[] dealName = "Demo Deal<Break>Sumo Kind".split("<Break>", -1);
		String[] dealCompany = "Acc 7<Break>Sumo Kind".split("<Break>", -1);
		String[] dealStage = "Deal Received<Break>NDA Signed".split("<Break>", -1);

		String tabName = "Test1 Custom Objects";
		String textBoxRecordLabel = "Test1 Custom Object Name";
		String[] textBoxRecordNames = "Golden Ret<Break>Pothoscust<Break>custareca<Break>Custom Object 1.1<Break>Custom Object 1.2<Break>Custom Object 1.3"
				.split("<Break>", -1);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		int fundStatus = 0;
		int fundLoopCount = 0;
		for (String fundName : fundNames) {

			log(LogStatus.INFO, "---------Now Going to Create Fund Named: " + fundName + "---------", YesNo.No);
			if (fund.clickOnTab(environment, mode, TabName.FundsTab)) {

				if (fund.createFund(projectName, fundName, fundTypes[fundLoopCount],
						investmentCategories[fundLoopCount], otherLabelFields, otherLabelValues)) {
					appLog.info("Fund is created Successfully: " + fundName);
					fundStatus++;

				} else {
					appLog.error("Not able to click on fund: " + fundName);
					sa.assertTrue(false, "Not able to click on fund: " + fundName);
					log(LogStatus.ERROR, "Not able to click on fund: " + fundName, YesNo.Yes);
				}
			} else {
				appLog.error("Not able to click on Fund tab so cannot create Fund: " + fundName);
				sa.assertTrue(false, "Not able to click on Fund tab so cannot create Fund: " + fundName);
			}
			ThreadSleep(2000);
			fundLoopCount++;

		}

		if (fundStatus == fundLoopCount) {
			int fundraisingLoopCount = 0;
			for (String fundraisingName : fundraisingNames) {
				log(LogStatus.INFO, "---------Now Going to Create Fundraising Named: " + fundraisingName + "---------",
						YesNo.No);
				if (BP.clickOnTab(environment, mode, TabName.FundraisingsTab)) {

					if (fr.createFundRaising(environment, "Lightning", fundraisingName,
							fundraisingsFundName[fundraisingLoopCount],
							fundraisingsInstitutionName[fundraisingLoopCount], null, null, null, null, null, null,
							null)) {
						appLog.info("fundraising is created : " + fundraisingName);
					} else {
						appLog.error("Not able to create fundraising: " + fundraisingName);
						sa.assertTrue(false, "Not able to create fundraising: " + fundraisingName);
					}

				} else {
					appLog.error(
							"Not able to click on fundraising tab so cannot create fundraising: " + fundraisingName);
					sa.assertTrue(false,
							"Not able to click on fundraising tab so cannot create fundraising: " + fundraisingName);
				}
				ThreadSleep(2000);

				fundraisingLoopCount++;

			}

		} else {
			appLog.error("No Fund is created, So not able to Create Fundraising: " + fundraisingNames);
			sa.assertTrue(false, "No Fund is created, So not able to Create Fundraising: " + fundraisingNames);
		}

		log(LogStatus.INFO, "---------Now Going to Create " + tabObj4 + "---------", YesNo.No);
		for (int i = 0; i < dealName.length; i++) {
			if (lp.clickOnTab(projectName, tabObj4)) {
				log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
				ThreadSleep(3000);
				if (dp.createDeal(dealRecordTypes, dealName[i], dealCompany[i], dealStage[i], null, null)) {
					log(LogStatus.INFO, dealName[i] + " deal has been created", YesNo.No);

				} else {
					log(LogStatus.ERROR, dealName[i] + " deal is not created", YesNo.No);
					sa.assertTrue(false, dealName[i] + " deal is not created");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
			}
		}

		for (String textBoxRecordName : textBoxRecordNames) {

			if (BP.createRecordForCustomObject(projectName, tabName, textBoxRecordLabel, textBoxRecordName)) {
				log(LogStatus.INFO, "Record: " + textBoxRecordName + " has been Created under: " + tabName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Record: " + textBoxRecordName + " has not been Created under: " + tabName,
						YesNo.No);
				sa.assertTrue(false, "Record: " + textBoxRecordName + " has not been Created under: " + tabName);
			}
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc046_VerifyTheUIOfMeetingNotesPopUpFromAddNoteButtonPlacedOnAcuityTabInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String task1ButtonName = "Task";

		String task1SubjectName = "";
		String task1Notes = "";

		String getAdvanceDueDate = "";
		String priority = "Normal";
		String status = "Not Started";

		String taskSectionSubject = "";
		String taskSectionStatus = "Not Started";
		String taskSectionDueDateOnly = "";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1TaskSection = { { "Subject", taskSectionSubject },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", taskSectionStatus },
				{ "Due Date Only", taskSectionDueDateOnly } };

		List<String> expectedFooterList = new ArrayList<String>();
		expectedFooterList.add("Cancel");
		expectedFooterList.add("Save");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Verify UI of Task: " + task1SubjectName
				+ " in Activity Timeline Section---------", YesNo.No);

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();

			WebElement ele;
			if (npbl.createNavPopUpMinimizeButton(5) != null) {
				CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
			}
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Create.toString(), action.BOOLEAN,
					30)) {
				log(LogStatus.INFO, "Able to Click on " + task1ButtonName + " Going to click on : "
						+ NavigationMenuItems.Create.toString() + " for creation ", YesNo.No);
				ele = npbl.getNavigationLabel(projectName, task1ButtonName, action.BOOLEAN, 10);
				if (ele != null && CommonLib.click(driver, ele, task1ButtonName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on " + task1ButtonName + " so going for creation", YesNo.No);
					String url = getURL(driver, 10);

					ThreadSleep(10000);

					String headerName = CommonLib.getText(driver, BP.notePopUpHeading(), "Heading", action.BOOLEAN);
					String expectedHeaderName = "Note";
					if (expectedHeaderName.equals(headerName)) {
						log(LogStatus.INFO, "PopUp Name has been verified to: " + headerName, YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "PopUp Name has been not been verified, Expected: " + expectedHeaderName
								+ " but Actual: " + headerName, YesNo.No);
						sa.assertTrue(false, "PopUp Name has been not been verified, Expected: " + expectedHeaderName
								+ " but Actual: " + headerName);
					}

					if (BP.notePopUpCrossButton(7) != null) {
						log(LogStatus.INFO, "Cross Button is visible in " + expectedHeaderName + " Popup", YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "Cross Button is not visible in " + expectedHeaderName + " Popup",
								YesNo.No);
						sa.assertTrue(false, "Cross Button is not visible in " + expectedHeaderName + " Popup");
					}

					if (BP.notePopUpAddMoreButton(7) != null) {
						log(LogStatus.INFO, "Add More Button is present in " + expectedHeaderName + " Popup", YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "Add More Button is not present in " + expectedHeaderName + " Popup",
								YesNo.No);
						sa.assertTrue(false, "Add More Button is not present in " + expectedHeaderName + " Popup");
					}

					List<String> actualFooterList = BP.notePopUpFooterButtons().stream()
							.map(x -> CommonLib.getText(driver, x, "Footer", action.BOOLEAN))
							.collect(Collectors.toList());

					if (actualFooterList.containsAll(expectedFooterList)) {
						log(LogStatus.INFO, "Footer List Matched: " + expectedFooterList, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Footer List not Matched, Expected: " + expectedFooterList + ", Actual: "
								+ expectedFooterList, YesNo.No);
						sa.assertTrue(false, "Footer List not Matched, Expected: " + expectedFooterList + ", Actual: "
								+ expectedFooterList);
					}

					ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
							.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
									task1AdvancedSection, task1TaskSection);
					if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
						log(LogStatus.INFO,
								"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
										+ NotesPopUpPrefilledNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
										+ NotesPopUpPrefilledNegativeResult);
					}

				} else {
					log(LogStatus.ERROR,
							"Not Able to Click on " + task1ButtonName + " so cannot create data related to this ",
							YesNo.Yes);
					sa.assertTrue(false,
							"Not Able to Click on " + task1ButtonName + " so cannot create data related to this ");

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + task1ButtonName + " for creation ", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + task1ButtonName + " for creation ");

			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc047_CreateATaskAndAddTheNotesFromEditCommentButtonOfTaskLayout(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("1"));

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Send Invoice";
		String task1Notes = "";
		String relatedTo = "Con 1<break>con 2<break>con 3<break>Sumo Logic<break>Houlihan Lokey<break>Vertica";
		String priority = "Normal";
		String status = "Not Started";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };
		String task1ButtonName = "Task";
		String recordName = "Sumo Logic";
		String recordType = "Company";
		String updatedCommentOfTask = "This is to notify that @ con4, @con5 should be in loop";
		String[] relatedToVerify = "Con 1<break>con 2<break>+5".split("<break>");
		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", updatedCommentOfTask },
				{ "Related_To", relatedTo } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);
		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, null)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntractionAndAbleToEditCommentsOfTask(driver,
								task1SubjectName, updatedCommentOfTask)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page and is able to edit the comment : "
									+ updatedCommentOfTask + "------", YesNo.No);

							CommonLib.refresh(driver);
							ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
									task1SubjectName, updatedCommentOfTask, true, false, relatedToVerify, null);
							if (updatedresult.isEmpty()) {
								log(LogStatus.PASS,
										"------" + task1SubjectName + " record has been verified on intraction------",
										YesNo.No);

								String url2 = getURL(driver, 10);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

									ThreadSleep(10000);
									ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
													task1BasicSectionVerification, task1AdvancedSection, null);
									if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
										log(LogStatus.INFO,
												"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
														+ NotesPopUpPrefilledNegativeResultUpdated,
												YesNo.No);
										sa.assertTrue(false,
												"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
														+ NotesPopUpPrefilledNegativeResultUpdated);
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR, "------" + task1SubjectName
										+ " record is not verified on intraction, Reason: " + updatedresult + "------",
										YesNo.No);
								sa.assertTrue(false, "------" + task1SubjectName
										+ " record is not verified on intraction, Reason: " + updatedresult + "------");
							}
						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is either not able to redirect to Task Detail Page or is not able to edit the comment : "
									+ updatedCommentOfTask + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is either not able to redirect to Task Detail Page or is not able to edit the comment : "
									+ updatedCommentOfTask + "------");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc048_CreateATaskAndAddTheNotesAndVerifySuggestedTagPopUp(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("0"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Send Letter";
		String task1Notes = "This is to check Con 2, Con 3, Logic should be the part of the deal kind";
		String relatedTo = "Vertica";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[] SuggestedTags = "con 2==Contact<break>con 3==Contact<break>Sumo Logic==Firm<break>Sumo Kind==Firm<break>Sumo Kind==Deal"
				.split("<break>", -1);

		String[] relatedToArray = new String[SuggestedTags.length + relatedTo.split("<break>", -1).length];

		int relatedToLoop = 0;
		int suggestedLoop = 0;
		for (String related : relatedTo.split("<break>", -1)) {
			relatedToArray[relatedToLoop] = related;
			relatedToLoop++;
		}
		for (String suggestedTag : SuggestedTags) {
			relatedToArray[relatedToLoop + suggestedLoop] = suggestedTag.split("==", -1)[0];
			suggestedLoop++;

		}

		String recordName = "Vertica";
		String recordType = "Company";
		String updatedNotesOfTask = "This is to notify that Areca and Arrow should be in loop of vertica";
		String[] relatedToVerify = "con 2<break>con 3<break>+5".split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "con 14<break>con 34".split("<break>", -1);
		String[] updatedRelatedToVerify = "con 2<break>con 3<break>+7".split("<break>");

		String[] updatedRelatedToArray = new String[relatedToArray.length + updatedSuggestedTags.length];

		int updatedrelatedToLoop = 0;
		int updatedsuggestedLoop = 0;
		for (String related : relatedToArray) {
			updatedRelatedToArray[updatedrelatedToLoop] = related;
			updatedrelatedToLoop++;
		}
		for (String suggestedTag : updatedSuggestedTags) {
			updatedRelatedToArray[updatedrelatedToLoop + updatedsuggestedLoop] = suggestedTag;
			updatedsuggestedLoop++;

		}

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */
		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimelineAlsoVerifyUIOfSuggestedTag(projectName, true, task1ButtonName,
					task1BasicSection, task1AdvancedSection, null, SuggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1SubjectName, task1Notes,
											relatedToArray);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

											String url2 = getURL(driver, 10);

											if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
													"Edit Note Button of: " + task1SubjectName,
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

												ThreadSleep(10000);
												ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
														.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
																task1SubjectName, updatedNotesOfTask,
																updatedRelatedToArray);
												if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
													log(LogStatus.INFO,
															"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
															YesNo.No);

												} else {
													log(LogStatus.ERROR,
															"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason:"
																	+ NotesPopUpPrefilledNegativeResultUpdated,
															YesNo.No);
													sa.assertTrue(false,
															"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason:"
																	+ NotesPopUpPrefilledNegativeResultUpdated);
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
												sa.assertTrue(false, "Not able to click on Edit Note button");
											}

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}
							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc049_CreateATaskWithoutMeetingNotesAndTagFromInteractionSectionByClickingOnEditNotesButton(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("-1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Introduction";
		String task1Notes = "";
		String relatedTo = "con 5<break>con 6<break>Sumo Logic<break>Vertica<break>Demo Deal<break>Mutual Fund";
		String[] relatedToArray = relatedTo.split("<break>", -1);
		String priority = "Normal";
		String status = "Completed";
		String task1ButtonName = "Task";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "con 5";

		String updatedNotesOfTask = "areca  moss fundraising should be tagged";
		String[] relatedToVerify = "con 5<break>con 6<break>+5".split("<break>");
		String[] updatedRelatedToVerify = "con 5<break>con 6<break>+10".split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "All Records Select".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, null)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1SubjectName, task1Notes,
											relatedToArray);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);
								sa.assertTrue(true,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}
							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value",
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc050_CreateATaskWithoutMeetingNotesAndAddTheNotesFromEditButton(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("-1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Sales Meeting";
		String task1Notes = "";
		String relatedTo = "Jhon<break>con 10<break>Sumo Logic";
		String priority = "Normal";
		String status = "Completed";
		String task1ButtonName = "Task";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Jhon";

		String updatedNotesOfTask = "Golden Ret";
		String[] relatedToVerify = "Jhon<break>con 10<break>+2".split("<break>");
		String[] updatedRelatedToVerify = "Jhon<break>con 10<break>+3".split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "Golden Ret".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, null)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntractionAndAbleToClickOnEditButtonInTaskDetailPage(
								driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page and clicked on Edit Button"
									+ "------", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(10000);

								if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null, null,
										updatedSuggestedTags, null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

										if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
												"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
													YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdateBasicSection, task1AdvancedSection, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);
												CommonLib.ThreadSleep(3000);
												driver.close();
												CommonLib.ThreadSleep(3000);

												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											}

										} else {
											log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
													YesNo.Yes);
											driver.close();

											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										}

									} else {
										log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
									}
									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true, false,
											updatedRelatedToVerify, null);
									if (updatedresult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " record has been verified on intraction------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------",
												YesNo.No);
										sa.assertTrue(false,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------");
									}

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc051_CreateATaskWithMeetingNotesAndAddTheNotesFromEditButtonOfTaskLayout(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("2"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Marketing Strategy";
		String task1Notes = "We as an organization need to have certain strategy towards our marketing approch with Vertica and sumo logic Firm";
		String relatedTo = "Max<break>Jhon<break>con 11";
		String priority = "Normal";
		String status = "Not Started";
		String task1ButtonName = "Task";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };
		String[] suggestedTags = "Vertica<break>Sumo Logic".split("<break>", -1);

		String RelatedToVerify = relatedTo + "<break>" + "Vertica<break>Sumo Logic";
		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", RelatedToVerify } };

		String recordName = "Max";

		String updatedNotesOfTask = "keep in loop  con 4, con 5";
		String[] relatedToVerify = "Max<break>Jhon<break>+4".split("<break>");
		String[] updatedRelatedToVerify = "Max<break>Jhon<break>+6".split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "con 4<break>con 5".split("<break>", -1);

		String updatedRelatedToVerifyInNotes = RelatedToVerify + "<break>" + "con 4<break>con 5";
		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntractionAndAbleToClickOnEditButtonInTaskDetailPage(
								driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page and clicked on Edit Button"
									+ "------", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(10000);

								if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null, null,
										updatedSuggestedTags, null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

										if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
												"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
													YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdatedBasicSectionVerification, task1AdvancedSection,
															null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);
												CommonLib.ThreadSleep(3000);
												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											}

										} else {
											log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
													YesNo.Yes);
											driver.close();

											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										}

									} else {
										log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true, false,
											updatedRelatedToVerify, null);
									if (updatedresult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " record has been verified on intraction------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------",
												YesNo.No);
										sa.assertTrue(false,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------");
									}

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc053_CreateATaskWithMeetingNotesAndUpdateTheNotesFromEditNoteButtonOnInteractionSectionOfAcuityTab(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Send Quote";
		String task1Notes = "unicorn";
		String relatedTo = "Maxtra<break>Martha<break>Jhon<break>con 11<break>Sumo Logic<break>Vertica";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };
		String[] suggestedTags = null;

		String recordName = "Martha";

		String updatedNotesOfTask = "Palm areca";
		String[] relatedToVerify = "Martha<break>Jhon<break>+5".split("<break>");
		String[] updatedRelatedToVerify = "Martha<break>Jhon<break>+7".split("<break>");

		String updatedRelatedTo = relatedTo + "<break>" + "Palm<break>areca";
		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[] updatedSuggestedTags = "All Records Select".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------");
										}

										String url2 = getURL(driver, 10);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdateBasicSectionVerification, task1AdvancedSection,
															null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}
							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value",
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc054_CreateATaskAndVerifyTaskHyperlinkUpdateTheTaskSubjectAndVerifyTheTaskHyperlink(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Send Notice";
		String task1UpdatedSubjectName = "Send Notice updated";
		String task1Notes = "";

		String relatedTo = "Acc 3<break>Martha<break>Echo<break>Alexa<break>Green Pothos<break>areca";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";
		String getAdvanceDueDateInTaskSection = "";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] tasksSectionVerificationData = { { "Subject", task1SubjectName },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Due Date Only", getAdvanceDueDateInTaskSection } };

		String[][] followUpTask1AdvancedSectionVerificationInNotesPopup = {
				{ "Due Date Only", getAdvanceDueDateInTaskSection },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1UpdatedSubjectName },
				{ "Notes", task1Notes }, { "Related_To", relatedTo } };

		String[] suggestedTags = null;

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] relatedToVerify = "Martha<break>areca<break>+5".split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Task: " + task1UpdatedSubjectName + " and followUp task: "
				+ task1SubjectName + " in Activity Timeline Section---------", YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();

			if (BP.createTasksWithVerificationOfFollowUpTaskSubjectNameAfterClickThenAgainUpdateTaskNameandVerifyFollowUpTaskSubjectName(
					projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection, suggestedTags,
					tasksSectionVerificationData, task1UpdatedSubjectName)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1UpdatedSubjectName + " and followUp task: "
				+ task1SubjectName + " in Interaction Section---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDateInTaskSection, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}

					ArrayList<String> result2 = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1UpdatedSubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result2.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1UpdatedSubjectName + " record has been verified on intraction------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "------" + task1UpdatedSubjectName
								+ " record is not verified on intraction, Reason: " + result2 + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1UpdatedSubjectName
								+ " record is not verified on intraction, Reason: " + result2 + "------");
					}

					String url = getURL(driver, 10);

					if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
							"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

						ThreadSleep(10000);
						ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
								.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
										followUpTask1AdvancedSectionVerificationInNotesPopup, null);
						if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"Notes Popup has been verified and Notes popup is opening in same page with prefilled value for: "
											+ task1SubjectName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value for: "
											+ task1SubjectName + ", Reason: " + NotesPopUpPrefilledNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value for: "
											+ task1SubjectName + ", Reason: " + NotesPopUpPrefilledNegativeResult);
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Edit Note button of: " + task1SubjectName, YesNo.No);
						sa.assertTrue(false, "Not able to click on Edit Note button of: " + task1SubjectName);
					}

					String url2 = getURL(driver, 10);

					if (click(driver, BP.editButtonOnInteractionCard(task1UpdatedSubjectName, 20),
							"Edit Note Button of: " + task1UpdatedSubjectName, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

						ThreadSleep(10000);
						ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
								.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
										task1UpdateBasicSectionVerification, task1AdvancedSection, null);
						if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
							log(LogStatus.INFO,
									"Notes Popup has been verified and Notes popup is opening in same page with prefilled value for: "
											+ task1UpdatedSubjectName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value for: "
											+ task1UpdatedSubjectName + ", Reason: "
											+ NotesPopUpPrefilledNegativeResultUpdated,
									YesNo.No);
							sa.assertTrue(false,
									"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value for: "
											+ task1UpdatedSubjectName + ", Reason: "
											+ NotesPopUpPrefilledNegativeResultUpdated);
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Edit Note button of " + task1UpdatedSubjectName,
								YesNo.No);
						sa.assertTrue(false, "Not able to click on Edit Note button of " + task1UpdatedSubjectName);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc055_CreateATaskWithMeetingNotesAndUpdateTheNotesForFollowUpTasksWhenCreatedMultipleFollowUpTasks(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("2"));

		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AMNNR_001", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "SSend Notice";
		String task1Notes = "echo alexa Green pothos areca";
		String relatedTo = "Acc 3<break>Martha";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";
		String[] suggestedTags = "".split("<break>", -1);

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";
		String task1UpdateTaskSection1Subject = "SSend Notice Follow up 1";
		String task1UpdateTaskSection1DueDateOnly = AdvanceDueDate;
		String task1UpdateTaskSection2Subject = "SSend Notice Follow up 2";
		String task1UpdateTaskSection2DueDateOnly = AdvanceDueDate;
		String task1UpdateTaskSection3Subject = "SSend Notice Follow up 3";
		String task1UpdateTaskSection3DueDateOnly = AdvanceDueDate;

		String updatedNotesOfTask = "Follow up task As Send Notice Updated for Con 6,Con 7, Con 8, Acc 4";
		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[][] task1UpdateTaskSection1 = { { "Subject", task1UpdateTaskSection1Subject },
				{ "Due Date Only", task1UpdateTaskSection1DueDateOnly } };
		String[][] task1UpdateTaskSection2 = { { "Subject", task1UpdateTaskSection2Subject },
				{ "Due Date Only", task1UpdateTaskSection2DueDateOnly } };
		String[][] task1UpdateTaskSection3 = { { "Subject", task1UpdateTaskSection3Subject },
				{ "Due Date Only", task1UpdateTaskSection3DueDateOnly } };
		String[][][] task1UpdateTaskSection = { task1UpdateTaskSection1, task1UpdateTaskSection2,
				task1UpdateTaskSection3 };

		String[] relatedToVerify = ("Martha<break>" + crmUser1FirstName + " " + crmUser1LastName + "<break>+1")
				.split("<break>");
		String[] updatedRelatedToVerify = "Martha<break>con 6<break>+5".split("<break>");

		String updatedRelatedTo = relatedTo + "<break>" + "con 6<break>con 7<break>con 8<break>Acc 4";

		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };

		String[][] followUptask1BasicSectionVerification = { { "Subject", task1UpdateTaskSection1Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[][] followUptask2BasicSectionVerification = { { "Subject", task1UpdateTaskSection2Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[][] followUptask3BasicSectionVerification = { { "Subject", task1UpdateTaskSection3Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };

		String[][] followUptask1AdvanceSectionVerification = { { "Due Date Only", task1UpdateTaskSection1DueDateOnly },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] followUptask2AdvanceSectionVerification = { { "Due Date Only", task1UpdateTaskSection2DueDateOnly },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] followUptask3AdvanceSectionVerification = { { "Due Date Only", task1UpdateTaskSection3DueDateOnly },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[] updatedSuggestedTags = "con 6<break>con 7<break>con 8<break>Acc 4".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName,
											task1UpdateBasicSection, null, task1UpdateTaskSection, updatedSuggestedTags,
											null, false, false)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult1 = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult1.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult1 + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult1 + "------");
										}

										ArrayList<String> updatedresult2 = BP.verifyRecordOnInteractionCard(
												task1UpdateTaskSection1DueDateOnly, null,
												task1UpdateTaskSection1Subject, updatedNotesOfTask, true, false,
												updatedRelatedToVerify, null);
										if (updatedresult2.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1UpdateTaskSection1Subject
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1UpdateTaskSection1Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult2 + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1UpdateTaskSection1Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult2 + "------");
										}

										ArrayList<String> updatedresult3 = BP.verifyRecordOnInteractionCard(
												task1UpdateTaskSection2DueDateOnly, null,
												task1UpdateTaskSection2Subject, updatedNotesOfTask, true, false,
												updatedRelatedToVerify, null);
										if (updatedresult3.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1UpdateTaskSection2Subject
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1UpdateTaskSection2Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult3 + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1UpdateTaskSection2Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult3 + "------");
										}

										ArrayList<String> updatedresult4 = BP.verifyRecordOnInteractionCard(
												task1UpdateTaskSection3DueDateOnly, null,
												task1UpdateTaskSection3Subject, updatedNotesOfTask, true, false,
												updatedRelatedToVerify, null);
										if (updatedresult4.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1UpdateTaskSection3Subject
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1UpdateTaskSection3Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult4 + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1UpdateTaskSection3Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult4 + "------");
										}

										String url2 = getURL(driver, 10);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated1 = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdateBasicSectionVerification, task1AdvancedSection,
															null);
											if (NotesPopUpPrefilledNegativeResultUpdated1.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated1,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated1);
											}
										} else {
											log(LogStatus.ERROR,
													"Not able to click on Edit Note button " + task1SubjectName,
													YesNo.No);
											sa.assertTrue(false,
													"Not able to click on Edit Note button " + task1SubjectName);
										}

										CommonLib.refresh(driver);
										if (click(driver,
												BP.editButtonOnInteractionCard(task1UpdateTaskSection1Subject, 20),
												"Edit Note Button of: " + task1UpdateTaskSection1Subject,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated2 = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															followUptask1BasicSectionVerification,
															followUptask1AdvanceSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated2.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated2,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated2);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button :"
													+ task1UpdateTaskSection1Subject, YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button :"
													+ task1UpdateTaskSection1Subject);
										}

										CommonLib.refresh(driver);
										if (click(driver,
												BP.editButtonOnInteractionCard(task1UpdateTaskSection2Subject, 20),
												"Edit Note Button of: " + task1UpdateTaskSection2Subject,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated3 = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															followUptask2BasicSectionVerification,
															followUptask2AdvanceSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated3.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated3,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated3);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button "
													+ task1UpdateTaskSection2Subject, YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button "
													+ task1UpdateTaskSection2Subject);
										}

										CommonLib.refresh(driver);
										if (click(driver,
												BP.editButtonOnInteractionCard(task1UpdateTaskSection3Subject, 20),
												"Edit Note Button of: " + task1UpdateTaskSection3Subject,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated4 = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															followUptask3BasicSectionVerification,
															followUptask3AdvanceSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated4.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated4,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated4);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button "
													+ task1UpdateTaskSection3Subject, YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button "
													+ task1UpdateTaskSection3Subject);
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}
							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value",
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc056_ClickOnTheTaskSubjectFromInteractionSectionAndAddTheNotesByClickingOnTagButton(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_001", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "SSend Notice";
		String task1Notes = "Follow up task As Send Notice Updated for Con 6,Con 7, Con 8, Acc 4";
		String relatedTo = "Acc 3<break>Martha";

		String verificationRelatedTo = relatedTo + "<break>" + "con 6<break>con 7<break>con 8<break>Acc 4";

		String priority = "Normal";
		String status = "In Progress";

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Martha<break>con 6<break>+5".split("<break>");
		String updatedRelatedTo = "Mutual Fund<break>FC Fundraising<break>Acc 1";

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String relatedToNotContains = crmUser2FirstName + " " + crmUser2LastName;
		String[][] relatedAssociationNotContains = { { "Related_To", relatedToNotContains } };

		String updatedRelatedToVerifyInNotes = verificationRelatedTo + "<break>" + updatedRelatedTo;

		String[][] task1UpdatedBasicSection = { { "Related_To", updatedRelatedTo } };
		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", updatedRelatedToVerifyInNotes } };

		String[] updatedRelatedToVerify = "Martha<break>con 6<break>+8".split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntractionAndAbleToClickOnEditButtonInTaskDetailPage(
								driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page and clicked on Edit Button"
									+ "------", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(10000);

								ArrayList<String> verifyRelatedToNotContainsNegativeResults = BP
										.verifyRelatedToNotContains(relatedAssociationNotContains);
								if (verifyRelatedToNotContainsNegativeResults.isEmpty()) {
									log(LogStatus.INFO,
											"RelatedTo Association Not Contains some Records has been verified",
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"RelatedTo Association Not Contains some Records has not been verified, Reason: "
													+ verifyRelatedToNotContainsNegativeResults,
											YesNo.No);
									sa.assertTrue(false,
											"RelatedTo Association Not Contains some Records has not been verified, Reason: "
													+ verifyRelatedToNotContainsNegativeResults);
								}
								refresh(driver);
								ThreadSleep(10000);

								if (BP.updateActivityTimelineRecord(projectName, task1UpdatedBasicSection, null, null,
										updatedSuggestedTags, null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

										if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
												"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
													YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdatedBasicSectionVerification,
															task1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);
												CommonLib.ThreadSleep(3000);
												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											}

										} else {
											log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
													YesNo.Yes);
											driver.close();

											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										}

									} else {
										log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, null, task1SubjectName, task1Notes, true, false,
											updatedRelatedToVerify, null);
									if (updatedresult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " record has been verified on intraction------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------",
												YesNo.No);
										sa.assertTrue(false,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------");
									}

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc057_CreateATaskWithMeetingNotesAndUpdateTheSameWithRelatedRecordRemoveAndThenVerify(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));

		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AMNNR_002", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task for the day";
		String task1Notes = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String relatedTo = "Con 1<break>con 2<break>Acc 3<break>Maxtra";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";
		String[] suggestedTags = "con 4<break>con 5<break>Demo Deal".split("<break>", -1);

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String verificationRelatedTo = relatedTo + "<break>" + "con 4<break>con 5<break>Demo Deal";

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>");

		String[] updatedRemoveRelatedAssociation = "Maxtra<break>Demo Deal".split("<break>", -1);

		String[] updatedSuggestedTags = null;

		String verificationUpdatedRelatedTo = "Con 1<break>con 2<break>Acc 3<break>con 4<break>con 5";

		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationUpdatedRelatedTo } };

		String[] updatedRelatedToVerifyInInteraction = "Con 1<break>con 2<break>+4".split("<break>");

		/*
		 * String[][] task1UpdatedBasicSectionVerification = { { "Subject",
		 * task1SubjectName }, { "Notes", task1Notes }, { "Related_To",
		 * updatedRelatedToVerifyInNotes } };
		 */

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(10000);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

									if (BP.updateActivityTimelineRecord(projectName, null, null, null,
											updatedSuggestedTags, updatedRemoveRelatedAssociation)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

											if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
													"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
														YesNo.No);

												String url2 = getURL(driver, 10);

												ThreadSleep(10000);
												ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
														.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
																task1UpdatedBasicSectionVerification,
																task1AdvancedSectionVerification, null);
												if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
													log(LogStatus.INFO,
															"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
															YesNo.No);

												} else {
													log(LogStatus.ERROR,
															"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																	+ NotesPopUpPrefilledNegativeResultUpdated,
															YesNo.No);
													sa.assertTrue(false,
															"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																	+ NotesPopUpPrefilledNegativeResultUpdated);

												}

											} else {
												log(LogStatus.ERROR,
														"Not Able Click on Edit button in Down Arrow Button",
														YesNo.Yes);

											}

										} else {
											log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);

										}

										CommonLib.refresh(driver);
										CommonLib.ThreadSleep(5000);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, task1Notes, true, false,
												updatedRelatedToVerifyInInteraction, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button for task: " + task1SubjectName,
									YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button for task: " + task1SubjectName);
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc058_CreateATaskAndTagContactsAccountsWhichAreNotCreatedInTheOrg(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Test";
		String task1Notes = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String relatedTo = "Con 1<break>con 2<break>Acc 3<break>Maxtra";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";
		String[] suggestedTags = "con 4<break>con 5<break>Demo Deal".split("<break>", -1);
		String verificationRelatedTo = relatedTo + "<break>" + "con 4<break>con 5<break>Demo Deal";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>");

		String relatedToNotContains = "Contact Invalid<break>Account Invalid";
		String[][] relatedAssociationNotContains = { { "Related_To", relatedToNotContains } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									ArrayList<String> verifyRelatedToNotContainsNegativeResults = BP
											.verifyRelatedToNotContains(relatedAssociationNotContains);
									if (verifyRelatedToNotContainsNegativeResults.isEmpty()) {
										log(LogStatus.INFO,
												"RelatedTo Association Not Contains some Records has been verified",
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"RelatedTo Association Not Contains some Records has not been verified, Reason: "
														+ verifyRelatedToNotContainsNegativeResults,
												YesNo.No);
										sa.assertTrue(false,
												"RelatedTo Association Not Contains some Records has not been verified, Reason: "
														+ verifyRelatedToNotContainsNegativeResults);
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

								refresh(driver);
								ThreadSleep(10000);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

									String url2 = getURL(driver, 10);

									ThreadSleep(10000);
									ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
													task1BasicSectionVerification, task1AdvancedSectionVerification,
													null);
									if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
										log(LogStatus.INFO,
												"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
														+ NotesPopUpPrefilledNegativeResultUpdated,
												YesNo.No);
										sa.assertTrue(false,
												"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
														+ NotesPopUpPrefilledNegativeResultUpdated);

									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, null, task1SubjectName, task1Notes, true, false,
											RelatedToVerifyInInteraction, null);
									if (updatedresult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " record has been verified on intraction------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------",
												YesNo.No);
										sa.assertTrue(false,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc059_CreateATaskAndTagContact1to50InCommentSectionAndCheckInPopUpContact1To50ShouldGetDisplayWithoutAnyContactGetsMissout(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task bulk contact";
		String task1Notes = "";
		String relatedTo = "Max<break>Martha<break>Acc 3<break>Maxtra";

		String priority = "Normal";
		String status = "Completed";
		String task1ButtonName = "Task";
		String[] suggestedTags = null;
		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Max<break>Martha<break>+3".split("<break>");

		String updatedNotesOfTask = "Con 1, con 2, con 3, con 4, con 5, con 6, con 7, con 8, con 9, con 10, con 11, con 12, con 13, con 14, con 15, con 16, con 17, con 18, con 19, con 20, con 21, con 22, con 23, con 24, con 25, con 26, con 27, con 28, con 29, con 30, con 31, con 32, con 33, con 34, con 35, con 36, con 37, con 38, con 39, con 40, con 41, con 42, con 43, con 44, con 45, con 46, con 47, con 48, con 49, con 50";

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "Con 1<break>con 2<break>con 3<break>con 4<break>con 5<break>con 6<break>con 7<break>con 8<break>con 9<break>con 10<break>con 11<break>con 12<break>con 13<break>con 14<break>con 15<break>con 16<break>con 17<break>con 18<break>con 19<break>con 20<break>con 21<break>con 22<break>con 23<break>con 24<break>con 25<break>con 26<break>con 27<break>con 28<break>con 29<break>con 30<break>con 31<break>con 32<break>con 33<break>con 34<break>con 35<break>con 36<break>con 37<break>con 38<break>con 39<break>con 40<break>con 41<break>con 42<break>con 43<break>con 44<break>con 45<break>con 46<break>con 47<break>con 48"
				.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = "Max<break>Martha<break>+51".split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = updatedSuggestedTags;

		String updatedRelatedToVerifyInNotes = (relatedTo + "<break>"
				+ "Con 1<break>con 2<break>con 3<break>con 4<break>con 5<break>con 6<break>con 7<break>con 8<break>con 9<break>con 10<break>con 11<break>con 12<break>con 13<break>con 14<break>con 15<break>con 16<break>con 17<break>con 18<break>con 19<break>con 20<break>con 21<break>con 22<break>con 23<break>con 24<break>con 25<break>con 26<break>con 27<break>con 28<break>con 29<break>con 30<break>con 31<break>con 32<break>con 33<break>con 34<break>con 35<break>con 36<break>con 37<break>con 38<break>con 39<break>con 40<break>con 41<break>con 42<break>con 43<break>con 44<break>con 45<break>con 46<break>con 47<break>con 48");

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc061_CreateATaskAndTag13RecordsForRelatedAssociationAnd50Contacts(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task bulk Firm contact";
		String task1Notes = "";
		String relatedTo = "Max<break>Martha<break>Vertica<break>Maxtra";

		String priority = "Normal";
		String status = "Completed";
		String task1ButtonName = "Task";
		String[] suggestedTags = null;
		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Vertica";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Max<break>Martha<break>+3".split("<break>");

		String updatedNotesOfTask = "Con 1, con 2, con 3, con 4, con 5, con 6, con 7, con 8, con 9, con 10, con 11, con 12, con 13, con 14, con 15, con 16, con 17, con 18, con 19, con 20, con 21, con 22, con 23, con 24, con 25, con 26, con 27, con 28, con 29, con 30, con 31, con 32, con 33, con 34, con 35, con 36, con 37, con 38, con 39, con 40, con 41, con 42, con 43, con 44, con 45, con 46, con 47, con 48, con 49, con 50, Acc 1, Acc 2, Acc 3, Acc 4, Acc 5, Acc 6, Acc 7, Acc 8, Acc 9, Acc 10, Acc 11, Acc 12, Acc 13";

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "Con 1<break>con 2<break>con 3<break>con 4<break>con 5<break>con 6<break>con 7<break>con 8<break>con 9<break>con 10<break>con 11<break>con 12<break>con 13<break>con 14<break>con 15<break>con 16<break>con 17<break>con 18<break>con 19<break>con 20<break>con 21<break>con 22<break>con 23<break>con 24<break>con 25<break>con 26<break>con 27<break>con 28<break>con 29<break>con 30<break>con 31<break>con 32<break>con 33<break>con 34<break>con 35<break>con 36<break>con 37<break>con 38<break>con 39<break>con 40<break>con 41<break>con 42<break>con 43<break>con 44<break>con 45<break>con 46<break>con 47<break>con 48<break>Acc 1<break>Acc 2<break>Acc 3<break>Acc 4<break>Acc 5<break>Acc 6<break>Acc 7<break>Acc 8<break>Acc 9<break>Acc 10<break>Acc 11<break>Acc 12<break>Acc 13"
				.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = "Max<break>Martha<break>+64".split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = updatedSuggestedTags;

		String updatedRelatedToVerifyInNotes = (relatedTo + "<break>"
				+ "Con 1<break>con 2<break>con 3<break>con 4<break>con 5<break>con 6<break>con 7<break>con 8<break>con 9<break>con 10<break>con 11<break>con 12<break>con 13<break>con 14<break>con 15<break>con 16<break>con 17<break>con 18<break>con 19<break>con 20<break>con 21<break>con 22<break>con 23<break>con 24<break>con 25<break>con 26<break>con 27<break>con 28<break>con 29<break>con 30<break>con 31<break>con 32<break>con 33<break>con 34<break>con 35<break>con 36<break>con 37<break>con 38<break>con 39<break>con 40<break>con 41<break>con 42<break>con 43<break>con 44<break>con 45<break>con 46<break>con 47<break>con 48<break>Acc 1<break>Acc 2<break>Acc 3<break>Acc 4<break>Acc 5<break>Acc 6<break>Acc 7<break>Acc 8<break>Acc 9<break>Acc 10<break>Acc 11<break>Acc 12<break>Acc 13");

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc062_CreateATaskWithMeetingNotesByTaggingCustomObjectsInIt(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Custom Object";
		String task1Notes = "Send the quotation to Martha, jhon, con 11 and Custom Object 1.1 belonging to the Firm Nexus, Custom Object 1.2";
		String relatedTo = "Martha<break>Jhon<break>con 11<break>Sumo Logic<break>Vertica";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";
		String[] suggestedTags = "Custom Object 1.1<break>Custom Object 1.2<break>Custom Object 1.3".split("<break>",
				-1);
		String verificationRelatedTo = relatedTo + "<break>"
				+ "Custom Object 1.1<break>Custom Object 1.2<break>Custom Object 1.3";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Martha";

		String[] RelatedToVerifyInInteraction = "Martha<break>Jhon<break>+7".split("<break>", -1);

		String updatedNotesOfTask = "keep in loop  con 4, con 5 Acc 5, Custom Object 1.2, Custom object 1.3";

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "con 4<break>con 5<break>Acc 5".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = "Martha<break>Jhon<break>+10".split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = updatedSuggestedTags;

		String updatedRelatedToVerifyInNotes = (verificationRelatedTo + "<break>" + "con 4<break>con 5<break>Acc 5");

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc063_VerifyRemovingSomeOfTheTaggedFromNotesPopUpAndVerifyTheSameInInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Demo";
		String task1Notes = "Follow up with Contacts con 4, con 5 about Demo Deal";
		String relatedTo = "Con 1<break>con 2<break>Acc 3<break>Maxtra";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";
		String[] suggestedTags = "con 4<break>con 5<break>Demo Deal".split("<break>", -1);
		String verificationRelatedTo = relatedTo + "<break>" + "con 4<break>con 5<break>Demo Deal";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>", -1);

		String updatedNotesOfTask = null;

		String[][] task1UpdateBasicSection = null;
		String[] updatedSuggestedTags = "".split("<break>", -1);
		String[] updatedRemoveRelatedAssociation = "Maxtra<break>Demo Deal".split("<break>", -1);
		String[] updatedRelatedToVerifyInInteraction = "Con 1<break>con 2<break>+4".split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = "Acc 3<break>con 4<break>con 5".split("<break>", -1);

		String updatedRelatedToVerifyInNotes = "Con 1<break>con 2<break>Acc 3<break>con 4<break>con 5";

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, updatedRemoveRelatedAssociation)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc064_VerifyChangingTheStatusOfTaskFromAdvancedSectionOfNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("0"));
		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AMNNR_003", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Advance";
		String task1Notes = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String relatedTo = "Maxjonic<break>Maxtra<break>Con 1<break>con 2";

		String priority = "Normal";
		String status = "Not Started";
		String task1ButtonName = "Task";
		String[] suggestedTags = "con 4<break>con 5<break>Demo Deal".split("<break>", -1);
		String verificationRelatedTo = relatedTo + "<break>" + "con 4<break>con 5<break>Demo Deal";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Maxjonic";
		String recordType = "Intermediary";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>", -1);

		String updatedNotesOfTask = task1Notes;
		String updatedStatus = "Completed";
		String[][] task1UpdateBasicSection = null;
		String[][] task1UpdateAdvancedSection = { { "Status", updatedStatus } };

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal"
				.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", updatedStatus },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc065_VerifyChangingTheDueDateToFutureOfTaskFromAdvancedSectionOfNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_003", excelLabel.Advance_Due_Date);
		String getUpdatedAdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("1"));
		ExcelUtils.writeData(AcuityDataSheetFilePath, getUpdatedAdvanceDueDate, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_004", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Advance";
		String task1Notes = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String relatedTo = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal";

		String priority = "Normal";
		String status = "Completed";

		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Maxjonic";
		String recordType = "Intermediary";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String[][] task1UpdateBasicSection = null;
		String[][] task1UpdateAdvancedSection = { { "Due Date Only", getUpdatedAdvanceDueDate } };

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal"
				.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getUpdatedAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getUpdatedAdvanceDueDate, null, task1SubjectName,
													updatedNotesOfTask, true, false,
													updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc066_VerifyChangingTheAssigneeOfTaskFromAdvancedSectionOfNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_004", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Advance";
		String task1Notes = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String relatedTo = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal";

		String priority = "Normal";
		String status = "Completed";

		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Maxjonic";
		String recordType = "Intermediary";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String[][] task1UpdateBasicSection = null;
		String[][] task1UpdateAdvancedSection = { { "Assigned To ID", crmUser2FirstName + " " + crmUser2LastName } };

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal"
				.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser2FirstName + " " + crmUser2LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc067_VerifyChangingTheSubjectOfTaskFromNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_004", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Advance";
		String task1Notes = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String relatedTo = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal";

		String priority = "Normal";
		String status = "Completed";

		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser2FirstName + " " + crmUser2LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Maxjonic";
		String recordType = "Intermediary";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String task1UpdatedSubjectName = "Task Advance Updated";
		String[][] task1UpdateBasicSection = { { "Subject", task1UpdatedSubjectName } };
		;
		String[][] task1UpdateAdvancedSection = null;

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal"
				.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1UpdatedSubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser2FirstName + " " + crmUser2LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1UpdatedSubjectName, 20),
												"Edit Note Button of: " + task1UpdatedSubjectName,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);

											String xPath = "//a[@class=\"interaction_sub subject_text\" and text()='"
													+ task1SubjectName + "']";
											WebElement ele = CommonLib.FindElement(driver, xPath, "Subject",
													action.SCROLLANDBOOLEAN, 15);
											String subName = getText(driver, ele, "Subject", action.SCROLLANDBOOLEAN);
											if (task1SubjectName.equals(subName)) {
												log(LogStatus.INFO, "Verified: After Update the Name of Subject to "
														+ task1UpdatedSubjectName + ", Previous Named: "
														+ task1SubjectName + " Interaction card should not be there",
														YesNo.No);
											} else {
												log(LogStatus.ERROR, "After Update the Name of Subject to "
														+ task1UpdatedSubjectName + ", Previous Named: "
														+ task1SubjectName
														+ " Interaction card is showing, which should not be there",
														YesNo.No);
												result.add("After Update the Name of Subject to "
														+ task1UpdatedSubjectName + ", Previous Named: "
														+ task1SubjectName
														+ " Interaction card is showing, which should not be there");
											}

											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1UpdatedSubjectName,
													updatedNotesOfTask, true, false,
													updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button of Task: "
													+ task1UpdatedSubjectName, YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button of Task: "
													+ task1UpdatedSubjectName);
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc068_VerifyWhenTheOrgHasSameDealNameAsOfTheCompanyNameAndIsTaggedInTheTask(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("0"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Task TQW";
		String task1Notes = "Areca";
		String relatedTo = "Acc 12";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";

		String[] SuggestedTags = "areca".split("<break>", -1);

		String verificationRelatedTo = relatedTo + "<break>" + "areca";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 12";
		String recordType = "Institution";

		String[] RelatedToVerifyInInteraction = ("areca<break>" + crmUser1FirstName + " " + crmUser1LastName
				+ "<break>+1").split("<break>", -1);

		String updatedNotesOfTask = " Sumo Kind";

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };

		String[] updatedSuggestedTags = "Sumo Kind==Firm<break>Sumo Kind==Deal<break>Sumo Kind==Contact<break>Sumo Kind Fund==Fund<break>Sumo Kind Fundraising==Fundraising"
				.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = "areca<break>Sumo Kind<break>+6".split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = null;

		String updatedRelatedToVerifyInNotes = ("Acc 12==Firm<break>areca==Contact" + "<break>"
				+ "Sumo Kind==Firm<break>Sumo Kind==Deal<break>Sumo Kind==Contact<break>Sumo Kind Fund==Fund<break>Sumo Kind Fundraising==Fundraising");

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */
		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, SuggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecordForMultipleFollowUpTasksAndVerifyUIOfSuggestedTags(
											projectName, task1UpdateBasicSection, null, null, updatedSuggestedTags,
											null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc069_VerifyWhenUserTagsAccountsAndContactsInNotesTextAreaAndClicksOnCloseButtonOrCrossIcon(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Demo 3";
		String task1Notes = "";
		String relatedTo = "Acc 3<break>Maxtra<break>Con 1<break>con 2<break>Demo Deal";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";
		String[] suggestedTags = null;
		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+4".split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String updatedRelatedToInNotes = "con 6<break>con 7";
		String[][] task1UpdateBasicSection = { { "Related_To", updatedRelatedToInNotes } };

		String[] updatedSuggestedTags = null;

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = "Acc 3<break>Maxtra<break>Demo Deal".split("<break>",
				-1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName,
											task1UpdateBasicSection, null, null, updatedSuggestedTags, null, true,
											false)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName,
											task1UpdateBasicSection, null, null, updatedSuggestedTags, null, false,
											true)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc070_VerifyWhenEditButtonIsClickedForTheTaskHavingFollowUpTaskAndIsDeletedFromTaskDetailPage(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String task1SubjectName = "SSend Notice";

		String recordName = "Acc 3";
		String recordType = "Company";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Note PopUp Section---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(task1SubjectName)) {
						log(LogStatus.INFO, "Task Detail Page has been open for Record: " + task1SubjectName, YesNo.No);

						if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

							if (click(driver, taskBP.buttonInTheDownArrowList("Delete", 20),
									"Delete Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Delete Button in  Down Arrow Button", YesNo.No);

								if (click(driver, taskBP.taskDeleteConfirmButton(15),
										"Delete Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Delete Confirm Button", YesNo.No);

									if (taskBP.taskDeletedMsg(15) != null) {
										log(LogStatus.INFO, "Task Delete Msg displayed, So Task has been deleted",
												YesNo.No);
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

										log(LogStatus.INFO,
												"---------Now Going to Verify Task: " + task1SubjectName
														+ " is present or not in Interaction Section---------",
												YesNo.No);
										if (lp.clickOnTab(projectName, tabObj1)) {

											log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

											if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
													TabName.InstituitonsTab, recordType, recordName, 30)) {
												log(LogStatus.INFO, recordName + " record of record type " + recordType
														+ " has been open", YesNo.No);
												ThreadSleep(4000);
												if (BP.clicktabOnPage("Acuity")) {
													log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

													if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
															task1SubjectName)) {
														log(LogStatus.INFO,
																"Verified: Task: " + task1SubjectName
																		+ " is not present there after delete",
																YesNo.No);

													} else {
														log(LogStatus.ERROR, "Task: " + task1SubjectName
																+ " is present there after delete", YesNo.Yes);
														sa.assertTrue(false, "Task: " + task1SubjectName
																+ " is present there after delete");
														driver.close();
														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());

													}

												} else {
													log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
													sa.assertTrue(false, "Not able to click on Acuity Tab");
												}

											} else {
												log(LogStatus.ERROR, "Not able to open " + recordName
														+ " record of record type " + recordType, YesNo.No);
												sa.assertTrue(false, "Not able to open " + recordName
														+ " record of record type " + recordType);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
											sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
										}

									} else {
										log(LogStatus.ERROR, "Task Delete Msg not display, So Task not gets deleted",
												YesNo.Yes);
										sa.assertTrue(false, "Task Delete Msg not display, So Task not gets deleted");
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
									}

								} else {
									log(LogStatus.ERROR, "Not ABle to Click on Delete Confirm Button", YesNo.Yes);
									sa.assertTrue(false, "Not ABle to Click on Delete Confirm Button");
									driver.close();
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								}

							} else {
								log(LogStatus.ERROR, "Not Able Click on Delete button in Down Arrow Button", YesNo.Yes);
								sa.assertTrue(false, "Not Able Click on Delete button in Down Arrow Button");
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

						} else {
							log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
							sa.assertTrue(false, "Not Able Click on Down Arrow Button");
							driver.close();
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						}

					} else {
						log(LogStatus.ERROR, "Task Detail Page has not open for Record: " + task1SubjectName,
								YesNo.Yes);
						sa.assertTrue(false, "Task Detail Page has not open for Record: " + task1SubjectName);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else

		{
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc071_VerifyWhenTheRemovedTaskIsRestoredAndItsImpactOnInteractionSection(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String recordName = "Acc 3";
		String recordType = "Company";

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_001", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "SSend Notice";
		String updatedNotesOfTask = "Follow up task As Send Notice Updated for Con 6,Con 7, Con 8, Acc 4";
		String relatedTo = "Acc 3<break>Martha";

		String priority = "Normal";
		String status = "In Progress";

		String verificationRelatedTo = relatedTo + "<break>" + "con 6<break>con 7<break>con 8<break>Acc 4";
		String updatedRelatedTo = "Mutual Fund<break>FC Fundraising<break>Acc 1";
		String updatedRelatedToVerifyInNotes = verificationRelatedTo + "<break>" + updatedRelatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", updatedNotesOfTask },
				{ "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] listViewSheetData = {
				{ "user 1", "Recycle Bin", task1SubjectName, "All users can see this list view", "My recycle bin",
						"Record Name", "equals", task1SubjectName, "TextBox" } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		WebElement ele;
		String recycleTab = lp.getTabName(projectName, TabName.RecycleBinTab);
		if (lp.openAppFromAppLauchner(60, recycleTab)) {

			CommonLib.refresh(driver);

			for (String[] row : listViewSheetData) {

				if (lp.addListView(projectName, row, 10)) {
					log(LogStatus.INFO, "list view added on " + row[1], YesNo.No);
					ele = lp.getCheckboxOfRestoreItemOnRecycleBin(projectName, task1SubjectName, 30);
					if (clickUsingJavaScript(driver, ele, "Check box against : " + task1SubjectName, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on checkbox for " + task1SubjectName, YesNo.No);
						;
						ele = lp.getRestoreButtonOnRecycleBin(projectName, 30);
						if (clickUsingJavaScript(driver, ele, "Restore Button : " + task1SubjectName, action.BOOLEAN)) {
							ThreadSleep(10000);
							log(LogStatus.INFO, "Click on Restore Button for " + task1SubjectName, YesNo.No);
							sa.assertTrue(true, "Task has been restore from the Recycle bin");

							CommonLib.switchToDefaultContent(driver);
							ThreadSleep(2000);

							log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjectName
									+ " in Note PopUp Section---------", YesNo.No);
							if (lp.clickOnTab(projectName, tabObj1)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

								if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
										recordType, recordName, 30)) {
									log(LogStatus.INFO,
											recordName + " record of record type " + recordType + " has been open",
											YesNo.No);
									ThreadSleep(4000);
									if (BP.clicktabOnPage("Acuity")) {
										log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

										if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
												task1SubjectName)) {
											log(LogStatus.INFO,
													"Task Detail Page has been open for Record: " + task1SubjectName,
													YesNo.No);

											if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

												if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
														"Delete Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
															YesNo.No);

													String url2 = getURL(driver, 10);

													ThreadSleep(10000);
													ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
															.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
																	task1BasicSectionVerification,
																	task1AdvancedSectionVerification, null);
													if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
														log(LogStatus.INFO,
																"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
																YesNo.No);
														CommonLib.ThreadSleep(3000);
														driver.close();
														CommonLib.ThreadSleep(3000);

														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());

													} else {
														log(LogStatus.ERROR,
																"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																		+ NotesPopUpPrefilledNegativeResultUpdated,
																YesNo.No);
														sa.assertTrue(false,
																"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																		+ NotesPopUpPrefilledNegativeResultUpdated);

														driver.close();
														CommonLib.ThreadSleep(3000);
														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());

													}

												} else {
													log(LogStatus.ERROR,
															"Not Able Click on Edit button in Down Arrow Button",
															YesNo.Yes);
													sa.assertTrue(false,
															"Not Able Click on Edit button in Down Arrow Button");
													driver.close();
													driver.switchTo().window(
															driver.getWindowHandles().stream().findFirst().get());
												}

											} else {
												log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
												sa.assertTrue(false, "Not Able Click on Down Arrow Button");
												driver.close();
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());
											}

										} else {
											log(LogStatus.ERROR,
													"Task Detail Page has not open for Record: " + task1SubjectName,
													YesNo.Yes);
											sa.assertTrue(false,
													"Task Detail Page has not open for Record: " + task1SubjectName);
										}

									} else {
										log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Acuity Tab");
									}

								} else {
									log(LogStatus.ERROR,
											"Not able to open " + recordName + " record of record type " + recordType,
											YesNo.No);
									sa.assertTrue(false,
											"Not able to open " + recordName + " record of record type " + recordType);
								}
							} else

							{
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
							}

						} else {

							log(LogStatus.ERROR, "Not Able to Click on Restore Button for " + task1SubjectName,
									YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Restore Button for " + task1SubjectName);
						}

					} else {

						log(LogStatus.ERROR, "Not Able to Click on checkbox for " + task1SubjectName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on checkbox for " + task1SubjectName);
					}
				}

				else {
					log(LogStatus.FAIL, "list view could not added on " + row[1], YesNo.Yes);
					sa.assertTrue(false, "list view could not added on " + row[1]);
				}
			}

		} else {
			log(LogStatus.ERROR, "Not Able to open the Recycle been tab", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the Recycle been tab");

		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	/*
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * AcuityMNNRTc075_VerifyWhen2000TasksAreUploadedAndImpactOnInteractionSection(
	 * String projectName) throws IOException {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
	 * 
	 * String task1SubjectName = "SSend Notice";
	 * 
	 * String recordName = "Acc 13"; String recordType = "Intermediary"; String
	 * dueDate; String task; String description; ArrayList<String> dueDateList = new
	 * ArrayList<String>(); ArrayList<String> taskList = new ArrayList<String>();
	 * ArrayList<String> descriptionList = new ArrayList<String>();
	 * ArrayList<String> userList = new ArrayList<String>(); String userName =
	 * crmUser1FirstName + " " + crmUser1LastName;
	 * 
	 * FileInputStream fis = null; Workbook wb = null; try { fis = new
	 * FileInputStream(new File(AcuityDataSheetFilePath)); wb =
	 * WorkbookFactory.create(fis); } catch (EncryptedDocumentException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch
	 * (InvalidFormatException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } for (int i = 1; i > 0; i++) { dueDate =
	 * ExcelUtils.readData(wb, "Bulk Task", i, 0); if (dueDate == null) {
	 * appLog.info("Done with the filters"); break; } dueDate.trim(); task =
	 * ExcelUtils.readData(wb, "Bulk Task", i, 1); description =
	 * ExcelUtils.readData(wb, "Bulk Task", i, 2);
	 * 
	 * task.trim(); description.trim();
	 * 
	 * dueDateList.add(dueDate); taskList.add(task);
	 * descriptionList.add(description); userList.add(userName);
	 * 
	 * }
	 * 
	 * fis.close();
	 * 
	 * 
	 * String dueDateArray[] = dueDateList.toArray(new String[dueDateList.size()]);
	 * String taskArray[] = taskList.toArray(new String[taskList.size()]); String
	 * descriptionArray[] = descriptionList.toArray(new
	 * String[descriptionList.size()]); String userArray[] = userList.toArray(new
	 * String[userList.size()]);
	 * 
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword);
	 * 
	 * 
	 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
	 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
	 * excelLabel.Advance_Due_Date);
	 * 
	 * 
	 * log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjectName
	 * + " in Note PopUp Section---------", YesNo.No); if
	 * (lp.clickOnTab(projectName, tabObj1)) {
	 * 
	 * log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
	 * 
	 * if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
	 * TabName.InstituitonsTab, recordType, recordName, 30)) { log(LogStatus.INFO,
	 * recordName + " record of record type " + recordType + " has been open",
	 * YesNo.No); ThreadSleep(4000); if (BP.clicktabOnPage("Acuity")) {
	 * log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
	 * 
	 * if (click(driver, BP.getViewAllBtnOnIntration(20),
	 * "View All button on Interaction", action.SCROLLANDBOOLEAN)) {
	 * log(LogStatus.INFO, "Clicked on View all button on Interaction card",
	 * YesNo.No);
	 * 
	 * CommonLib.ThreadSleep(15000); ArrayList<String> result =
	 * BP.verifyRecordsonInteractionsViewAllPopup(null, dueDateList, taskList,
	 * descriptionList, userList); if (result.isEmpty()) { log(LogStatus.INFO,
	 * "All records on View All popup of Interaction card have been verified",
	 * YesNo.No); } else { log(LogStatus.ERROR,
	 * "All records on View All popup of Interaction card are not verified",
	 * YesNo.No); sa.assertTrue(false,
	 * "All records on View All popup of Interaction card are not verified"); }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Not able to click on View all button on Interaction card", YesNo.No);
	 * sa.assertTrue(false,
	 * "Not able to click on View all button on Interaction card"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
	 * sa.assertTrue(false, "Not able to click on Acuity Tab"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Not able to open " + recordName +
	 * " record of record type " + recordType, YesNo.No); sa.assertTrue(false,
	 * "Not able to open " + recordName + " record of record type " + recordType); }
	 * } else
	 * 
	 * { log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
	 * sa.assertTrue(false, "Not able to click on Tab : " + tabObj1); }
	 * 
	 * ThreadSleep(5000); lp.CRMlogout(); sa.assertAll(); }
	 */
	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc078_VerifyTheImpactOnNoteTaggingWhenPEStandardUserProfileObjectPermissionIsRevokedAndUserTriesToTagTheObject(
			String projectName) {
		CustomObjPageBusinessLayer co = new CustomObjPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String[] userTypesToGivePermissions = { "PE Standard User" };
		String[][] objectAndPermissionAndGivenOrGivenNot = {
				{ "Deals", "Read", PermissionType.removePermission.toString() },
				{ "Accounts", "Read", PermissionType.removePermission.toString() },
				{ "Contacts", "Read", PermissionType.removePermission.toString() } };

		String[][] objectAndPermissionAndGivenOrGivenNotRevertBack = {
				{ "Deals", "Create<break>Delete",
						PermissionType.givePermission.toString() + "<break>"
								+ PermissionType.givePermission.toString() },
				{ "Accounts", "Create<break>Delete",
						PermissionType.givePermission.toString() + "<break>"
								+ PermissionType.givePermission.toString() },
				{ "Contacts", "Create<break>Delete", PermissionType.givePermission.toString() + "<break>"
						+ PermissionType.givePermission.toString() } };

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("0"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task permission";
		String task1Notes = "Max and  Martha";
		String relatedTo = "Mutual Fund";

		String verificationRelatedTo = relatedTo;

		String status = "In Progress";
		String task1ButtonName = "Task";
		String[] suggestedTags = "".split("<break>", -1);

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status } };

		String relatedToNotContains = "Sumo Logic<break>Vertica<break>Demo Deal";
		String[][] relatedAssociationNotContains = { { "Related_To", relatedToNotContains } };

		boolean flag1 = false;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		if (co.objectPermissionGivenOrRemove(objectAndPermissionAndGivenOrGivenNot, userTypesToGivePermissions)) {
			flag1 = true;
		}

		lp.CRMlogout();

		CommonLib.ThreadSleep(3000);

		if (flag1) {

			lp.CRMLogin(crmUser1EmailID, adminPassword);
			if (npbl.createNavPopUpMinimizeButton(5) != null) {
				CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
			}
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Create.toString(), action.BOOLEAN,
					30)) {
				log(LogStatus.INFO, "Able to Click on " + task1ButtonName + " Going to click on : "
						+ NavigationMenuItems.Create.toString() + " for creation ", YesNo.No);
				WebElement ele = npbl.getNavigationLabel(projectName, task1ButtonName, action.BOOLEAN, 10);
				if (ele != null && CommonLib.click(driver, ele, task1ButtonName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on " + task1ButtonName + " so going for creation", YesNo.No);

					CommonLib.ThreadSleep(3000);
					ArrayList<String> verifyRelatedToNotContainsNegativeResults = BP
							.verifyRelatedToNotContains(relatedAssociationNotContains);
					if (verifyRelatedToNotContainsNegativeResults.isEmpty()) {
						log(LogStatus.INFO, "RelatedTo Association Not Contains some Records has been verified",
								YesNo.No);

						CommonLib.refresh(driver);
						if (lp.clickOnTab(projectName, TabName.HomeTab)) {
							log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
							home.notificationPopUpClose();
							CommonLib.ThreadSleep(4000);
							if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection,
									task1AdvancedSection, null, suggestedTags)) {
								log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

								log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjectName

										+ " in Interaction Section---------", YesNo.No);
								if (BP.openAppFromAppLauchner("Tasks", 50)) {

									log(LogStatus.INFO, "Tasks" + " has been open from the App launcher", YesNo.No);

									if (BP.clickOnAlreadyCreatedItem(projectName, TabName.TaskTab, task1SubjectName,
											30)) {
										log(LogStatus.INFO, task1SubjectName + " record has been open", YesNo.No);
										ThreadSleep(4000);
										CommonLib.refresh(driver);

										if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

											if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
													"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
														YesNo.No);

												String url = getURL(driver, 10);

												if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
														"Edit Note Button of: " + task1SubjectName,
														action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

													ThreadSleep(10000);
													ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
															.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url,
																	task1BasicSectionVerification,
																	task1AdvancedSectionVerification, null);
													if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
														log(LogStatus.INFO,
																"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
																YesNo.No);

													} else {
														log(LogStatus.ERROR,
																"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value",
																YesNo.No);
														sa.assertTrue(false,
																"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value");
													}
												} else {
													log(LogStatus.ERROR, "Not able to click on Edit Note button",
															YesNo.No);
													sa.assertTrue(false, "Not able to click on Edit Note button");
												}

											} else {
												log(LogStatus.ERROR,
														"Not Able Click on Edit button in Down Arrow Button",
														YesNo.Yes);
												sa.assertTrue(false,
														"Not Able Click on Edit button in Down Arrow Button");

											}

										} else {
											log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
											sa.assertTrue(false, "Not Able Click on Down Arrow Button");
										}

									} else {
										log(LogStatus.ERROR, "Not able to open " + task1SubjectName + " record",
												YesNo.No);
										sa.assertTrue(false, "Not able to open " + task1SubjectName + " record");
									}
								} else {
									log(LogStatus.ERROR, "Not able to Open Tasks Tab", YesNo.No);
									sa.assertTrue(false, "Not able to Open Tasks Tab");
								}

							} else {
								log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
								sa.assertTrue(false, "Activity timeline record is not created");
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
							log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR,
								"RelatedTo Association Not Contains some Records has not been verified, Reason: "
										+ verifyRelatedToNotContainsNegativeResults,
								YesNo.No);
						sa.assertTrue(false,
								"RelatedTo Association Not Contains some Records has not been verified, Reason: "
										+ verifyRelatedToNotContainsNegativeResults);
					}

				} else {
					log(LogStatus.ERROR,
							"Not Able to Click on " + task1ButtonName + " so cannot create data related to this ",
							YesNo.Yes);
					sa.assertTrue(false,
							"Not Able to Click on " + task1ButtonName + " so cannot create data related to this ");

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + task1ButtonName + " for creation ", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + task1ButtonName + " for creation ");

			}

			lp.CRMlogout();
			CommonLib.ThreadSleep(6000);
			lp.CRMLogin(superAdminUserName, adminPassword, appName);
			if (co.objectPermissionGivenOrRemove(objectAndPermissionAndGivenOrGivenNotRevertBack,
					userTypesToGivePermissions)) {
				log(LogStatus.INFO, "Permissions has been revert backed", YesNo.No);

			} else {
				log(LogStatus.ERROR, "Permissions has not been revert backed", YesNo.Yes);
				sa.assertTrue(false, "Permissions has not been revert backed");
			}
			lp.CRMlogout();

		} else {
			log(LogStatus.ERROR,
					"As the Permission not removed, So not able to create and verify the Task with its details in Note popup",
					YesNo.Yes);
			sa.assertTrue(false,
					"As the Permission not removed, So not able to create and verify the Task with its details in Note popup");
		}

		sa.assertAll();
		CommonLib.ThreadSleep(3000);
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc077_VerifyTheImpactOnNoteWhenFieldLabelsAreChangedFromBackend(String projectName) {
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String[] tabNames = { "Activities" };
		String[][] labelsWithValues2d = { { "Subject<break>Subject updated", "Status<break>Status updated",
				"Due Date Only<break>Due Date Only updated", "Priority<break>Priority updated" } };

		String[][] labelsWithValues2dRevertBack = { { "Subject<break>Subject", "Status<break>Status",
				"Due Date Only<break>Due Date Only", "Priority<break>Priority" } };

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_002", excelLabel.Advance_Due_Date);
		String getAdvanceDueDateExisting = AdvanceDueDate;
		AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectNameVerify = "Task for the day";
		String task1NotesVerify = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+4".split("<break>");
		String verificationUpdatedRelatedTo = "Con 1<break>con 2<break>Acc 3<break>con 4<break>con 5";

		String priorityVerify = "Normal";
		String statusVerify = "In Progress";

		String[][] task1UpdatedBasicSectionVerificationExisting = { { "Subject updated", task1SubjectNameVerify },
				{ "Notes", task1NotesVerify }, { "Related_To", verificationUpdatedRelatedTo } };
		String[][] task1AdvancedSectionVerificationExisting = { { "Due Date Only updated", getAdvanceDueDateExisting },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status updated", statusVerify },
				{ "Priority updated", priorityVerify } };

		String recordNameExisting = "Acc 5";
		String recordTypeExisting = "Intermediary";

		String recordName = "Vertica";
		String recordType = "Company";

		String task1SubjectName = "Task Field label change";
		String task1Notes = "Max and  Martha";
		String relatedTo = "Sumo Logic<break>Vertica<break>Demo Deal<break>Litz";

		String verificationRelatedTo = relatedTo + "<break>" + "Martha<break>Max";

		String status = "In Progress";
		String task1ButtonName = "Task";
		String[] suggestedTags = "Martha<break>Max".split("<break>", -1);
		String[] newlyRelatedToVerifyInInteraction = "Max<break>Martha<break>+5".split("<break>");

		String[][] task1BasicSection = { { "Subject updated", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only updated", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status updated", status } };

		String[][] task1BasicSectionVerification = { { "Subject updated", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only updated", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status updated", status } };

		String[] labelAndValueSeprateByBreak = { "Assigned To" + "<break>" + crmUser1FirstName + " " + crmUser1LastName,
				"Status updated" + "<break>" + status, "Subject updated" + "<break>" + task1SubjectName,
				"Due Date Only updated" + "<break>" + getAdvanceDueDate,
				"Related Associations" + "<break>" + "Sumo Logic, Vertica, Demo Deal" };

		boolean flag1 = false;

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		if (sp.renameLabelsOfFields(driver, tabNames, labelsWithValues2d, 20)) {
			flag1 = true;
		}

		lp.CRMlogout();

		CommonLib.ThreadSleep(3000);

		if (flag1) {

			lp.CRMLogin(crmUser1EmailID, adminPassword);

			log(LogStatus.INFO,
					"---------Now Going to Verify Task: " + task1SubjectNameVerify + " in Interaction Section---------",
					YesNo.No);
			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordTypeExisting,
						recordNameExisting, 30)) {
					log(LogStatus.INFO,
							recordNameExisting + " record of record type " + recordTypeExisting + " has been open",
							YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
						ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDateExisting, null,
								task1SubjectNameVerify, task1NotesVerify, true, false, RelatedToVerifyInInteraction,
								null);
						if (result.isEmpty()) {
							log(LogStatus.PASS,
									"------" + task1SubjectNameVerify + " record has been verified on intraction------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + task1SubjectNameVerify
											+ " record is not verified on intraction, Reason: " + result + "------",
									YesNo.No);
							sa.assertTrue(false, "------" + task1SubjectNameVerify
									+ " record is not verified on intraction, Reason: " + result + "------");
						}
						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(8000);

						if (clickUsingJavaScript(driver, BP.editButtonOnInteractionCard(task1SubjectNameVerify, 20),
								"Edit Note Button of: " + task1SubjectNameVerify, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button for: " + task1SubjectNameVerify, YesNo.No);

							String url2 = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
											task1UpdatedBasicSectionVerificationExisting,
											task1AdvancedSectionVerificationExisting, null);
							if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResultUpdated,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResultUpdated);

							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button for: " + task1SubjectNameVerify,
									YesNo.No);
							sa.assertTrue(false,
									"Not able to click on Edit Note button for: " + task1SubjectNameVerify);
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR,
							"Not able to open " + recordNameExisting + " record of record type " + recordTypeExisting,
							YesNo.No);
					sa.assertTrue(false,
							"Not able to open " + recordNameExisting + " record of record type " + recordTypeExisting);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
			}

			CommonLib.refresh(driver);
			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				home.notificationPopUpClose();
				if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection,
						task1AdvancedSection, null, suggestedTags)) {
					log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

					log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjectName
							+ " in Interaction Section---------", YesNo.No);
					if (lp.clickOnTab(projectName, tabObj1)) {

						log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
						CommonLib.ThreadSleep(3000);
						CommonLib.refresh(driver);
						if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType,
								recordName, 30)) {
							log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open",
									YesNo.No);
							ThreadSleep(4000);
							if (BP.clicktabOnPage("Acuity")) {
								log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
								ArrayList<String> result2 = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
										task1SubjectName, task1Notes, true, false, newlyRelatedToVerifyInInteraction,
										null);
								if (result2.isEmpty()) {
									log(LogStatus.PASS, "------" + task1SubjectName
											+ " record has been verified on intraction------", YesNo.No);

								} else {
									log(LogStatus.ERROR, "------" + task1SubjectName
											+ " record is not verified on intraction, Reason: " + result2 + "------",
											YesNo.No);
									sa.assertTrue(false, "------" + task1SubjectName
											+ " record is not verified on intraction, Reason: " + result2 + "------");
								}

								CommonLib.refresh(driver);
								CommonLib.ThreadSleep(8000);
								if (clickUsingJavaScript(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

									String url2 = getURL(driver, 10);

									ThreadSleep(10000);
									ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
													task1BasicSectionVerification, task1AdvancedSectionVerification,
													null);
									if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
										log(LogStatus.INFO,
												"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
														+ NotesPopUpPrefilledNegativeResultUpdated,
												YesNo.No);
										sa.assertTrue(false,
												"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
														+ NotesPopUpPrefilledNegativeResultUpdated);

									}

								} else {
									log(LogStatus.ERROR,
											"Not able to click on Edit Note button for: " + task1SubjectName, YesNo.No);
									sa.assertTrue(false,
											"Not able to click on Edit Note button for: " + task1SubjectName);
								}

								CommonLib.refresh(driver);
								CommonLib.ThreadSleep(8000);
								if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(task1SubjectName)) {
									log(LogStatus.INFO,
											"Task Detail Page has been open for Record: " + task1SubjectName, YesNo.No);

									CommonLib.ThreadSleep(8000);
									List<String> taskDetailPageNegativeResult = BP
											.fieldValueVerification(labelAndValueSeprateByBreak);

									if (taskDetailPageNegativeResult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " labels and their values in Detail page has been verified------",
												YesNo.No);
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

									} else {
										log(LogStatus.ERROR, "------" + task1SubjectName
												+ " labels and their values in Detail page has not been verified, Reason: "
												+ taskDetailPageNegativeResult + "------", YesNo.No);
										sa.assertTrue(false, "------" + task1SubjectName
												+ " labels and their values in Detail page has not been verified, Reason: "
												+ taskDetailPageNegativeResult + "------");
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
									}

								} else {
									log(LogStatus.ERROR,
											"Task Detail Page has not open for Record: " + task1SubjectName, YesNo.Yes);
									sa.assertTrue(false,
											"Task Detail Page has not open for Record: " + task1SubjectName);
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
								sa.assertTrue(false, "Not able to click on Acuity Tab");
							}

						} else {
							log(LogStatus.ERROR,
									"Not able to open " + recordName + " record of record type " + recordType,
									YesNo.No);
							sa.assertTrue(false,
									"Not able to open " + recordName + " record of record type " + recordType);
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
						sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
					}

				} else {
					log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
					sa.assertTrue(false, "Activity timeline record is not created");
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			lp.CRMlogout();

			CommonLib.ThreadSleep(6000);
			lp.CRMLogin(superAdminUserName, adminPassword, appName);

			if (sp.renameLabelsOfFields(driver, tabNames, labelsWithValues2dRevertBack, 20)) {
				log(LogStatus.INFO, "Renaming Labels has been Revert Back", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Renaming Labels has not been Revert Back", YesNo.No);
				sa.assertTrue(false, "Renaming Labels has not been Revert Back");
			}

			lp.CRMlogout();

		} else {
			log(LogStatus.ERROR,
					"Label Names not Updated, So not able to verify existing Note popup of task and create new task",
					YesNo.No);
			sa.assertTrue(false,
					"Label Names not Updated, So not able to verify existing Note popup of task and create new task");
		}
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc079_VerifyTheImpactOfNoteTaggingWhenTaskPermissionIsRevokedForTheUser(String projectName) {
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);

		String task1ButtonName = "Task";
		String[] userTypesToGivePermissions = { "PE Standard User" };
		String[][] objectAndPermissionAndGivenOrGivenNot = {
				{ "Edit Tasks", PermissionType.removePermission.toString() } };
		String[][] objectAndPermissionAndGivenOrGivenNotRevertBack = {
				{ "Edit Tasks", PermissionType.givePermission.toString() } };

		boolean flag1 = false;

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		if (sp.permissionChangeOfGeneralAndAdministrative(objectAndPermissionAndGivenOrGivenNot,
				userTypesToGivePermissions)) {
			flag1 = true;
		}

		lp.CRMlogout();

		CommonLib.ThreadSleep(3000);

		if (flag1) {

			lp.CRMLogin(crmUser1EmailID, adminPassword);
			if (npbl.createNavPopUpMinimizeButton(5) != null) {
				CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
			}
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Create.toString(), action.BOOLEAN,
					30)) {
				log(LogStatus.INFO, "Able to Click on " + task1ButtonName + " Going to click on : "
						+ NavigationMenuItems.Create.toString() + " for creation ", YesNo.No);

				if (!npbl.navList().isEmpty()) {
					if (!npbl.navList().contains(task1ButtonName)) {
						log(LogStatus.INFO,
								"Button: " + task1ButtonName + " is disappear after Unchecked the Edit Tasks Option",
								YesNo.No);
					} else {
						log(LogStatus.ERROR, "Button: " + task1ButtonName
								+ " is not disappear after Unchecked the Edit Tasks Option", YesNo.Yes);
						sa.assertTrue(false, "Button: " + task1ButtonName
								+ " is not disappear after Unchecked the Edit Tasks Option");
					}

				} else {
					log(LogStatus.ERROR,
							"Either taking time to load or No Nav list there, So no able to veify Task Button",
							YesNo.Yes);
					sa.assertTrue(false,
							"Not Able to Click on " + task1ButtonName + " so cannot create data related to this ");

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + task1ButtonName + " for creation ", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + task1ButtonName + " for creation ");

			}

			lp.CRMlogout();

			CommonLib.ThreadSleep(6000);
			lp.CRMLogin(superAdminUserName, adminPassword, appName);
			if (sp.permissionChangeOfGeneralAndAdministrative(objectAndPermissionAndGivenOrGivenNotRevertBack,
					userTypesToGivePermissions)) {
				log(LogStatus.INFO, "Edit Tasks Permission has been Revert Back", YesNo.No);
			} else {

				log(LogStatus.ERROR, "Edit Tasks Permission has not been Revert Back", YesNo.Yes);
				sa.assertTrue(false, "Edit Tasks Permission has not been Revert Back");
			}

			lp.CRMlogout();
		} else {
			log(LogStatus.ERROR, "As the Permission not removed, So not able to verify Task Button in Navigation Pane",
					YesNo.Yes);
			sa.assertTrue(false, "As the Permission not removed, So not able to verify Task Button in Navigation Pane");
		}

		sa.assertAll();
		CommonLib.ThreadSleep(3000);
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc080_VerifyTheUIOfNotesPopUpFromNavigationPanelInCaseOfCall(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String task1ButtonName = "Call";

		String task1SubjectName = "Call";
		String task1Notes = "";

		String getAdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+08:00", "M/d/yyyy", Integer.parseInt("0"));

		String priority = "Normal";
		String status = "Completed";

		String taskSectionSubject = "Call";
		String taskSectionStatus = "Not Started";
		String taskSectionDueDateOnly = "";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1TaskSection = { { "Subject", taskSectionSubject },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", taskSectionStatus },
				{ "Due Date Only", taskSectionDueDateOnly } };

		List<String> expectedFooterList = new ArrayList<String>();
		expectedFooterList.add("Cancel");
		expectedFooterList.add("Save");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Verify UI of Task: " + task1SubjectName
				+ " in Activity Timeline Section---------", YesNo.No);

		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();

			WebElement ele;
			if (npbl.createNavPopUpMinimizeButton(5) != null) {
				CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
			}
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Create.toString(), action.BOOLEAN,
					30)) {
				log(LogStatus.INFO, "Able to Click on " + task1ButtonName + " Going to click on : "
						+ NavigationMenuItems.Create.toString() + " for creation ", YesNo.No);
				ele = npbl.getNavigationLabel(projectName, task1ButtonName, action.BOOLEAN, 10);
				if (ele != null && CommonLib.click(driver, ele, task1ButtonName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on " + task1ButtonName + " so going for creation", YesNo.No);
					String url = getURL(driver, 10);

					ThreadSleep(10000);

					String headerName = CommonLib.getText(driver, BP.notePopUpHeading(), "Heading", action.BOOLEAN);
					String expectedHeaderName = "Note";
					if (expectedHeaderName.equals(headerName)) {
						log(LogStatus.INFO, "PopUp Name has been verified to: " + headerName, YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "PopUp Name has been not been verified, Expected: " + expectedHeaderName
								+ " but Actual: " + headerName, YesNo.No);
						sa.assertTrue(false, "PopUp Name has been not been verified, Expected: " + expectedHeaderName
								+ " but Actual: " + headerName);
					}

					if (BP.notePopUpCrossButton(7) != null) {
						log(LogStatus.INFO, "Cross Button is visible in " + expectedHeaderName + " Popup", YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "Cross Button is not visible in " + expectedHeaderName + " Popup",
								YesNo.No);
						sa.assertTrue(false, "Cross Button is not visible in " + expectedHeaderName + " Popup");
					}

					if (BP.notePopUpAddMoreButton(7) != null) {
						log(LogStatus.INFO, "Add More Button is present in " + expectedHeaderName + " Popup", YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "Add More Button is not present in " + expectedHeaderName + " Popup",
								YesNo.No);
						sa.assertTrue(false, "Add More Button is not present in " + expectedHeaderName + " Popup");
					}

					List<String> actualFooterList = BP.notePopUpFooterButtons().stream()
							.map(x -> CommonLib.getText(driver, x, "Footer", action.BOOLEAN))
							.collect(Collectors.toList());

					if (actualFooterList.containsAll(expectedFooterList)) {
						log(LogStatus.INFO, "Footer List Matched: " + expectedFooterList, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Footer List not Matched, Expected: " + expectedFooterList + ", Actual: "
								+ expectedFooterList, YesNo.No);
						sa.assertTrue(false, "Footer List not Matched, Expected: " + expectedFooterList + ", Actual: "
								+ expectedFooterList);
					}

					ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
							.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
									task1AdvancedSection, task1TaskSection);
					if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
						log(LogStatus.INFO,
								"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
										+ NotesPopUpPrefilledNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
										+ NotesPopUpPrefilledNegativeResult);
					}

				} else {
					log(LogStatus.ERROR,
							"Not Able to Click on " + task1ButtonName + " so cannot create data related to this ",
							YesNo.Yes);
					sa.assertTrue(false,
							"Not Able to Click on " + task1ButtonName + " so cannot create data related to this ");

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + task1ButtonName + " for creation ", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + task1ButtonName + " for creation ");

			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc081_CreateACallAndAddTheNotesFromEditCommentButtonOfTaskLayout(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_017",
		 * excelLabel.Advance_Due_Date);
		 */

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Send Invoice Call";
		String task1Notes = "";
		String relatedTo = "Con 1<break>con 2<break>con 3<break>Sumo Logic<break>Houlihan Lokey<break>Vertica";
		String priority = "Normal";
		String status = "Not Started";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };
		String task1ButtonName = "Call";
		String recordName = "Sumo Logic";
		String recordType = "Company";
		String updatedCommentOfTask = "This is to notify that @ con4, @con5 should be in loop";
		String[] relatedToVerify = "Con 1<break>con 2<break>+5".split("<break>");

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", updatedCommentOfTask },
				{ "Related_To", relatedTo } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);
		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, null)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntractionAndAbleToEditCommentsOfTask(driver,
								task1SubjectName, updatedCommentOfTask)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page and is able to edit the comment : "
									+ updatedCommentOfTask + "------", YesNo.No);

							CommonLib.refresh(driver);
							ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
									task1SubjectName, updatedCommentOfTask, true, false, relatedToVerify, null);
							if (updatedresult.isEmpty()) {
								log(LogStatus.PASS,
										"------" + task1SubjectName + " record has been verified on intraction------",
										YesNo.No);

								String url2 = getURL(driver, 10);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

									ThreadSleep(10000);
									ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
													task1BasicSectionVerification, task1AdvancedSection, null);
									if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
										log(LogStatus.INFO,
												"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
														+ NotesPopUpPrefilledNegativeResultUpdated,
												YesNo.No);
										sa.assertTrue(false,
												"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
														+ NotesPopUpPrefilledNegativeResultUpdated);
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR, "------" + task1SubjectName
										+ " record is not verified on intraction, Reason: " + updatedresult + "------",
										YesNo.No);
								sa.assertTrue(false, "------" + task1SubjectName
										+ " record is not verified on intraction, Reason: " + updatedresult + "------");
							}
						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is either not able to redirect to Task Detail Page or is not able to edit the comment : "
									+ updatedCommentOfTask + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is either not able to redirect to Task Detail Page or is not able to edit the comment : "
									+ updatedCommentOfTask + "------");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc082_CreateACallAndAddTheNotesAndVerifySuggestedTagPopUp(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Send Letter Call";
		String task1Notes = "This is to check Con 2, Con 3, Logic should be the part of the deal kind";
		String relatedTo = "Vertica";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Call";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[] SuggestedTags = "con 2==Contact<break>con 3==Contact<break>Sumo Logic==Firm<break>Sumo Kind==Firm<break>Sumo Kind==Deal"
				.split("<break>", -1);
		String[] relatedToArray = new String[SuggestedTags.length + relatedTo.split("<break>", -1).length];

		int relatedToLoop = 0;
		int suggestedLoop = 0;
		for (String related : relatedTo.split("<break>", -1)) {
			relatedToArray[relatedToLoop] = related;
			relatedToLoop++;
		}
		for (String suggestedTag : SuggestedTags) {
			relatedToArray[relatedToLoop + suggestedLoop] = suggestedTag.split("==", -1)[0];
			suggestedLoop++;

		}

		String recordName = "Vertica";
		String recordType = "Company";
		String updatedNotesOfTask = "This is to notify that Areca and Arrow should be in loop of vertica";
		String[] relatedToVerify = "con 2<break>con 3<break>+5".split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "con 14<break>con 34".split("<break>", -1);
		String[] updatedRelatedToVerify = "con 2<break>con 3<break>+7".split("<break>");

		String[] updatedRelatedToArray = new String[relatedToArray.length + updatedSuggestedTags.length];

		int updatedrelatedToLoop = 0;
		int updatedsuggestedLoop = 0;
		for (String related : relatedToArray) {
			updatedRelatedToArray[updatedrelatedToLoop] = related;
			updatedrelatedToLoop++;
		}
		for (String suggestedTag : updatedSuggestedTags) {
			updatedRelatedToArray[updatedrelatedToLoop + updatedsuggestedLoop] = suggestedTag;
			updatedsuggestedLoop++;

		}

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */
		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimelineAlsoVerifyUIOfSuggestedTag(projectName, true, task1ButtonName,
					task1BasicSection, task1AdvancedSection, null, SuggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1SubjectName, task1Notes,
											relatedToArray);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

											String url2 = getURL(driver, 10);

											if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
													"Edit Note Button of: " + task1SubjectName,
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

												ThreadSleep(10000);
												ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
														.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
																task1SubjectName, updatedNotesOfTask,
																updatedRelatedToArray);
												if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
													log(LogStatus.INFO,
															"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
															YesNo.No);

												} else {
													log(LogStatus.ERROR,
															"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason:"
																	+ NotesPopUpPrefilledNegativeResultUpdated,
															YesNo.No);
													sa.assertTrue(false,
															"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason:"
																	+ NotesPopUpPrefilledNegativeResultUpdated);
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
												sa.assertTrue(false, "Not able to click on Edit Note button");
											}

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}
							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc083_CreateACallWithoutMeetingNotesAndTagFromInteractionSectionByClickingOnEditNotesButton(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Introduction Call";
		String task1Notes = "";
		String relatedTo = "con 5<break>con 6<break>Sumo Logic<break>Vertica<break>Demo Deal<break>Mutual Fund";
		String[] relatedToArray = relatedTo.split("<break>", -1);
		String priority = "Normal";
		String status = "Completed";
		String task1ButtonName = "Call";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "con 5";

		String updatedNotesOfTask = "areca  moss fundraising should be tagged";
		String[] relatedToVerify = "con 5<break>con 6<break>+5".split("<break>");
		String[] updatedRelatedToVerify = "con 5<break>con 6<break>+10".split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "All Records Select".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, null)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1SubjectName, task1Notes,
											relatedToArray);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);
								sa.assertTrue(true,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}
							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value",
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc084_CreateACallWithoutMeetingNotesAndAddTheNotesFromEditButton(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("2"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Sales Meeting Call";
		String task1Notes = "";
		String relatedTo = "Jhon<break>con 10<break>Sumo Logic";
		String priority = "Normal";
		String status = "Completed";
		String task1ButtonName = "Call";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Jhon";

		String updatedNotesOfTask = "Golden Ret";
		String[] relatedToVerify = "Jhon<break>con 10<break>+2".split("<break>");
		String[] updatedRelatedToVerify = "Jhon<break>con 10<break>+3".split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "Golden Ret".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, null)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntractionAndAbleToClickOnEditButtonInTaskDetailPage(
								driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page and clicked on Edit Button"
									+ "------", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(10000);

								if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null, null,
										updatedSuggestedTags, null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

										if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
												"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
													YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdateBasicSection, task1AdvancedSection, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);
												CommonLib.ThreadSleep(3000);
												driver.close();
												CommonLib.ThreadSleep(3000);

												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											}

										} else {
											log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
													YesNo.Yes);
											driver.close();

											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										}

									} else {
										log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
									}
									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true, false,
											updatedRelatedToVerify, null);
									if (updatedresult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " record has been verified on intraction------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------",
												YesNo.No);
										sa.assertTrue(false,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------");
									}

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc085_CreateACallWithMeetingNotesAndAddTheNotesFromEditButtonOfTaskLayOut(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Marketing Strategy Call";
		String task1Notes = "We as an organization need to have certain strategy towards our marketing approch with Vertica and sumo logic Firm";
		String relatedTo = "Max<break>Jhon<break>con 11";
		String priority = "Normal";
		String status = "Not Started";
		String task1ButtonName = "Call";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };
		String[] suggestedTags = "Vertica<break>Sumo Logic".split("<break>", -1);

		String RelatedToVerify = relatedTo + "<break>" + "Vertica<break>Sumo Logic";
		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", RelatedToVerify } };

		String recordName = "Max";

		String updatedNotesOfTask = "keep in loop  con 4, con 5";
		String[] relatedToVerify = "Max<break>Jhon<break>+4".split("<break>");
		String[] updatedRelatedToVerify = "Max<break>Jhon<break>+6".split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "con 4<break>con 5".split("<break>", -1);

		String updatedRelatedToVerifyInNotes = RelatedToVerify + "<break>" + "con 4<break>con 5";
		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntractionAndAbleToClickOnEditButtonInTaskDetailPage(
								driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page and clicked on Edit Button"
									+ "------", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(10000);

								if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null, null,
										updatedSuggestedTags, null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

										if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
												"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
													YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdatedBasicSectionVerification, task1AdvancedSection,
															null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);
												CommonLib.ThreadSleep(3000);
												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											}

										} else {
											log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
													YesNo.Yes);
											driver.close();

											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										}

									} else {
										log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true, false,
											updatedRelatedToVerify, null);
									if (updatedresult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " record has been verified on intraction------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------",
												YesNo.No);
										sa.assertTrue(false,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------");
									}

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc087_CreateACallWithMeetingNotesAndUpdateTheNotesFromEditNoteButtonOnInteractionSectionOfAcuityTab(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Send Quote Call";
		String task1Notes = "unicorn";
		String relatedTo = "Maxtra<break>Martha<break>Jhon<break>con 11<break>Sumo Logic<break>Vertica";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Call";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };
		String[] suggestedTags = null;

		String recordName = "Martha";

		String updatedNotesOfTask = "Palm areca";
		String[] relatedToVerify = "Martha<break>Jhon<break>+5".split("<break>");
		String[] updatedRelatedToVerify = "Martha<break>Jhon<break>+7".split("<break>");

		String updatedRelatedTo = relatedTo + "<break>" + "Palm<break>areca";
		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[] updatedSuggestedTags = "All Records Select".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------");
										}

										String url2 = getURL(driver, 10);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdateBasicSectionVerification, task1AdvancedSection,
															null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}
							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value",
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc088_CreateACallAndVerifyTaskHyperlinkUpdateTheTaskSubjectAndVerifyTheCallHyperlink(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("5"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Send Notice Call";
		String task1UpdatedSubjectName = "Send Notice updated Call";
		String task1Notes = "";

		String relatedTo = "Acc 3<break>Martha<break>Echo<break>Alexa<break>Green Pothos<break>areca";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Call";
		String getAdvanceDueDateInTaskSection = "";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] tasksSectionVerificationData = { { "Subject", task1SubjectName },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Due Date Only", getAdvanceDueDateInTaskSection } };

		String[][] followUpTask1AdvancedSectionVerificationInNotesPopup = {
				{ "Due Date Only", getAdvanceDueDateInTaskSection },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1UpdatedSubjectName },
				{ "Notes", task1Notes }, { "Related_To", relatedTo } };

		String[] suggestedTags = null;

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] relatedToVerify = "Martha<break>areca<break>+5".split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Task: " + task1UpdatedSubjectName + " and followUp task: "
				+ task1SubjectName + " in Activity Timeline Section---------", YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();

			if (BP.createTasksWithVerificationOfFollowUpTaskSubjectNameAfterClickThenAgainUpdateTaskNameandVerifyFollowUpTaskSubjectName(
					projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection, suggestedTags,
					tasksSectionVerificationData, task1UpdatedSubjectName)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1UpdatedSubjectName + " and followUp task: "
				+ task1SubjectName + " in Interaction Section---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					ArrayList<String> result2 = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1UpdatedSubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result2.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1UpdatedSubjectName + " record has been verified on intraction------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "------" + task1UpdatedSubjectName
								+ " record is not verified on intraction, Reason: " + result2 + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1UpdatedSubjectName
								+ " record is not verified on intraction, Reason: " + result2 + "------");
					}

					CommonLib.refresh(driver);

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(task1SubjectName)) {
						log(LogStatus.INFO, "Task Detail Page has been open for Record: " + task1SubjectName, YesNo.No);
						if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

							if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
									"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);

								String url = getURL(driver, 10);

								ThreadSleep(10000);
								ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
										.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
												followUpTask1AdvancedSectionVerificationInNotesPopup, null);
								if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
									log(LogStatus.INFO,
											"Notes Popup has been verified and Notes popup is opening in same page with prefilled value for: "
													+ task1SubjectName,
											YesNo.No);
									CommonLib.ThreadSleep(3000);
									driver.close();
									CommonLib.ThreadSleep(3000);

									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								} else {
									log(LogStatus.ERROR,
											"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value for: "
													+ task1SubjectName + ", Reason: "
													+ NotesPopUpPrefilledNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value for: "
													+ task1SubjectName + ", Reason: "
													+ NotesPopUpPrefilledNegativeResult);

									driver.close();
									CommonLib.ThreadSleep(3000);
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								}

							} else {
								log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button", YesNo.Yes);
								driver.close();

								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

						} else {
							log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
							driver.close();
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						}
					} else {
						log(LogStatus.ERROR, "Task Detail Page has not open for Record: " + task1SubjectName,
								YesNo.Yes);
						sa.assertTrue(false, "Task Detail Page has not open for Record: " + task1SubjectName);
					}

					String url2 = getURL(driver, 10);

					if (click(driver, BP.editButtonOnInteractionCard(task1UpdatedSubjectName, 20),
							"Edit Note Button of: " + task1UpdatedSubjectName, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

						ThreadSleep(10000);
						ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
								.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
										task1UpdateBasicSectionVerification, task1AdvancedSection, null);
						if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
							log(LogStatus.INFO,
									"Notes Popup has been verified and Notes popup is opening in same page with prefilled value for: "
											+ task1UpdatedSubjectName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value for: "
											+ task1UpdatedSubjectName + ", Reason: "
											+ NotesPopUpPrefilledNegativeResultUpdated,
									YesNo.No);
							sa.assertTrue(false,
									"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value for: "
											+ task1UpdatedSubjectName + ", Reason: "
											+ NotesPopUpPrefilledNegativeResultUpdated);
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Edit Note button of " + task1UpdatedSubjectName,
								YesNo.No);
						sa.assertTrue(false, "Not able to click on Edit Note button of " + task1UpdatedSubjectName);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc089_CreateACallWithMeetingNotesAndUpdateTheNotesForFollowUpTasksWhenCreatedMultipleFollowUpTasks(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("6"));
		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AMNNR_005", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "SSend Notice Call";
		String task1Notes = "echo alexa Green pothos areca";
		String relatedTo = "Acc 3<break>Martha";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Call";
		String[] suggestedTags = "".split("<break>", -1);

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";
		String task1UpdateTaskSection1Subject = "SSend Notice Follow up 1 Call";
		String task1UpdateTaskSection1DueDateOnly = AdvanceDueDate;
		String task1UpdateTaskSection2Subject = "SSend Notice Follow up 2 Call";
		String task1UpdateTaskSection2DueDateOnly = AdvanceDueDate;
		String task1UpdateTaskSection3Subject = "SSend Notice Follow up 3 Call";
		String task1UpdateTaskSection3DueDateOnly = AdvanceDueDate;

		String updatedNotesOfTask = "Follow up task As Send Notice Updated for Con 6,Con 7, Con 8, Acc 4";
		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[][] task1UpdateTaskSection1 = { { "Subject", task1UpdateTaskSection1Subject },
				{ "Due Date Only", task1UpdateTaskSection1DueDateOnly } };
		String[][] task1UpdateTaskSection2 = { { "Subject", task1UpdateTaskSection2Subject },
				{ "Due Date Only", task1UpdateTaskSection2DueDateOnly } };
		String[][] task1UpdateTaskSection3 = { { "Subject", task1UpdateTaskSection3Subject },
				{ "Due Date Only", task1UpdateTaskSection3DueDateOnly } };
		String[][][] task1UpdateTaskSection = { task1UpdateTaskSection1, task1UpdateTaskSection2,
				task1UpdateTaskSection3 };

		String[] relatedToVerify = ("Martha<break>" + crmUser1FirstName + " " + crmUser1LastName + "<break>+1")
				.split("<break>");
		String[] updatedRelatedToVerify = "Martha<break>con 6<break>+5".split("<break>");

		String updatedRelatedTo = relatedTo + "<break>" + "con 6<break>con 7<break>con 8<break>Acc 4";

		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };

		String[][] followUptask1BasicSectionVerification = { { "Subject", task1UpdateTaskSection1Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[][] followUptask2BasicSectionVerification = { { "Subject", task1UpdateTaskSection2Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[][] followUptask3BasicSectionVerification = { { "Subject", task1UpdateTaskSection3Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };

		String[][] followUptask1AdvanceSectionVerification = { { "Due Date Only", task1UpdateTaskSection1DueDateOnly },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] followUptask2AdvanceSectionVerification = { { "Due Date Only", task1UpdateTaskSection2DueDateOnly },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] followUptask3AdvanceSectionVerification = { { "Due Date Only", task1UpdateTaskSection3DueDateOnly },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[] updatedSuggestedTags = "con 6<break>con 7<break>con 8<break>Acc 4".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName,
											task1UpdateBasicSection, null, task1UpdateTaskSection, updatedSuggestedTags,
											null, false, false)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult1 = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult1.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult1 + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult1 + "------");
										}

										ArrayList<String> updatedresult2 = BP.verifyRecordOnInteractionCard(
												task1UpdateTaskSection1DueDateOnly, null,
												task1UpdateTaskSection1Subject, updatedNotesOfTask, true, false,
												updatedRelatedToVerify, null);
										if (updatedresult2.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1UpdateTaskSection1Subject
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1UpdateTaskSection1Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult2 + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1UpdateTaskSection1Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult2 + "------");
										}

										ArrayList<String> updatedresult3 = BP.verifyRecordOnInteractionCard(
												task1UpdateTaskSection2DueDateOnly, null,
												task1UpdateTaskSection2Subject, updatedNotesOfTask, true, false,
												updatedRelatedToVerify, null);
										if (updatedresult3.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1UpdateTaskSection2Subject
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1UpdateTaskSection2Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult3 + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1UpdateTaskSection2Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult3 + "------");
										}

										ArrayList<String> updatedresult4 = BP.verifyRecordOnInteractionCard(
												task1UpdateTaskSection3DueDateOnly, null,
												task1UpdateTaskSection3Subject, updatedNotesOfTask, true, false,
												updatedRelatedToVerify, null);
										if (updatedresult4.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1UpdateTaskSection3Subject
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1UpdateTaskSection3Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult4 + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1UpdateTaskSection3Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult4 + "------");
										}

										String url2 = getURL(driver, 10);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated1 = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdateBasicSectionVerification, task1AdvancedSection,
															null);
											if (NotesPopUpPrefilledNegativeResultUpdated1.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated1,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated1);
											}
										} else {
											log(LogStatus.ERROR,
													"Not able to click on Edit Note button " + task1SubjectName,
													YesNo.No);
											sa.assertTrue(false,
													"Not able to click on Edit Note button " + task1SubjectName);
										}

										CommonLib.refresh(driver);
										if (click(driver,
												BP.editButtonOnInteractionCard(task1UpdateTaskSection1Subject, 20),
												"Edit Note Button of: " + task1UpdateTaskSection1Subject,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated2 = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															followUptask1BasicSectionVerification,
															followUptask1AdvanceSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated2.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated2,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated2);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button :"
													+ task1UpdateTaskSection1Subject, YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button :"
													+ task1UpdateTaskSection1Subject);
										}

										CommonLib.refresh(driver);
										if (click(driver,
												BP.editButtonOnInteractionCard(task1UpdateTaskSection2Subject, 20),
												"Edit Note Button of: " + task1UpdateTaskSection2Subject,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated3 = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															followUptask2BasicSectionVerification,
															followUptask2AdvanceSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated3.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated3,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated3);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button "
													+ task1UpdateTaskSection2Subject, YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button "
													+ task1UpdateTaskSection2Subject);
										}

										CommonLib.refresh(driver);
										if (click(driver,
												BP.editButtonOnInteractionCard(task1UpdateTaskSection3Subject, 20),
												"Edit Note Button of: " + task1UpdateTaskSection3Subject,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated4 = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															followUptask3BasicSectionVerification,
															followUptask3AdvanceSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated4.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated4,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated4);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button "
													+ task1UpdateTaskSection3Subject, YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button "
													+ task1UpdateTaskSection3Subject);
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}
							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value",
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc090_ClickOnTheCallSubjectFromInteractionSectionAndAddTheNotesByClickingOnTagButton(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_005", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "SSend Notice Call";
		String task1Notes = "Follow up task As Send Notice Updated for Con 6,Con 7, Con 8, Acc 4";
		String relatedTo = "Acc 3<break>Martha";

		String verificationRelatedTo = relatedTo + "<break>" + "con 6<break>con 7<break>con 8<break>Acc 4";

		String priority = "Normal";
		String status = "In Progress";

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Martha<break>con 6<break>+5".split("<break>");
		String updatedRelatedTo = "Mutual Fund<break>FC Fundraising<break>Acc 1";

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String relatedToNotContains = crmUser2FirstName + " " + crmUser2LastName;
		String[][] relatedAssociationNotContains = { { "Related_To", relatedToNotContains } };

		String updatedRelatedToVerifyInNotes = verificationRelatedTo + "<break>" + updatedRelatedTo;

		String[][] task1UpdatedBasicSection = { { "Related_To", updatedRelatedTo } };
		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", updatedRelatedToVerifyInNotes } };

		String[] updatedRelatedToVerify = "Martha<break>con 6<break>+8".split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntractionAndAbleToClickOnEditButtonInTaskDetailPage(
								driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page and clicked on Edit Button"
									+ "------", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(10000);

								ArrayList<String> verifyRelatedToNotContainsNegativeResults = BP
										.verifyRelatedToNotContains(relatedAssociationNotContains);
								if (verifyRelatedToNotContainsNegativeResults.isEmpty()) {
									log(LogStatus.INFO,
											"RelatedTo Association Not Contains some Records has been verified",
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"RelatedTo Association Not Contains some Records has not been verified, Reason: "
													+ verifyRelatedToNotContainsNegativeResults,
											YesNo.No);
									sa.assertTrue(false,
											"RelatedTo Association Not Contains some Records has not been verified, Reason: "
													+ verifyRelatedToNotContainsNegativeResults);
								}
								refresh(driver);
								ThreadSleep(10000);

								if (BP.updateActivityTimelineRecord(projectName, task1UpdatedBasicSection, null, null,
										updatedSuggestedTags, null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

										if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
												"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
													YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdatedBasicSectionVerification,
															task1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);
												CommonLib.ThreadSleep(3000);
												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											}

										} else {
											log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
													YesNo.Yes);
											driver.close();

											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										}

									} else {
										log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, null, task1SubjectName, task1Notes, true, false,
											updatedRelatedToVerify, null);
									if (updatedresult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " record has been verified on intraction------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------",
												YesNo.No);
										sa.assertTrue(false,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------");
									}

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc091_CreateACallWithMeetingNotesAndUpdateTheSameWithRelatedRecordRemoveAndThenVerify(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("7"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task for the day Call";
		String task1Notes = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String relatedTo = "Con 1<break>con 2<break>Acc 3<break>Maxtra";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Call";
		String[] suggestedTags = "con 4<break>con 5<break>Demo Deal".split("<break>", -1);

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String verificationRelatedTo = relatedTo + "<break>" + "con 4<break>con 5<break>Demo Deal";

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>");

		String[] updatedRemoveRelatedAssociation = "Maxtra<break>Demo Deal".split("<break>", -1);

		String[] updatedSuggestedTags = null;

		String verificationUpdatedRelatedTo = "Con 1<break>con 2<break>Acc 3<break>con 4<break>con 5";

		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationUpdatedRelatedTo } };

		String[] updatedRelatedToVerifyInInteraction = "Con 1<break>con 2<break>+4".split("<break>");

		/*
		 * String[][] task1UpdatedBasicSectionVerification = { { "Subject",
		 * task1SubjectName }, { "Notes", task1Notes }, { "Related_To",
		 * updatedRelatedToVerifyInNotes } };
		 */

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(10000);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

									if (BP.updateActivityTimelineRecord(projectName, null, null, null,
											updatedSuggestedTags, updatedRemoveRelatedAssociation)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

											if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
													"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
														YesNo.No);

												String url2 = getURL(driver, 10);

												ThreadSleep(10000);
												ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
														.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
																task1UpdatedBasicSectionVerification,
																task1AdvancedSectionVerification, null);
												if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
													log(LogStatus.INFO,
															"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
															YesNo.No);

												} else {
													log(LogStatus.ERROR,
															"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																	+ NotesPopUpPrefilledNegativeResultUpdated,
															YesNo.No);
													sa.assertTrue(false,
															"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																	+ NotesPopUpPrefilledNegativeResultUpdated);

												}

											} else {
												log(LogStatus.ERROR,
														"Not Able Click on Edit button in Down Arrow Button",
														YesNo.Yes);

											}

										} else {
											log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);

										}

										CommonLib.refresh(driver);
										CommonLib.ThreadSleep(5000);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, task1Notes, true, false,
												updatedRelatedToVerifyInInteraction, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button for task: " + task1SubjectName,
									YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button for task: " + task1SubjectName);
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc092_CreateACallAndTagContactsAccountsWhichAreNotCreatedInTheOrg(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("8"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Test Call";
		String task1Notes = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String relatedTo = "Con 1<break>con 2<break>Acc 3<break>Maxtra";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Call";
		String[] suggestedTags = "con 4<break>con 5<break>Demo Deal".split("<break>", -1);
		String verificationRelatedTo = relatedTo + "<break>" + "con 4<break>con 5<break>Demo Deal";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>");

		String relatedToNotContains = "Contact Invalid<break>Account Invalid";
		String[][] relatedAssociationNotContains = { { "Related_To", relatedToNotContains } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									ArrayList<String> verifyRelatedToNotContainsNegativeResults = BP
											.verifyRelatedToNotContains(relatedAssociationNotContains);
									if (verifyRelatedToNotContainsNegativeResults.isEmpty()) {
										log(LogStatus.INFO,
												"RelatedTo Association Not Contains some Records has been verified",
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"RelatedTo Association Not Contains some Records has not been verified, Reason: "
														+ verifyRelatedToNotContainsNegativeResults,
												YesNo.No);
										sa.assertTrue(false,
												"RelatedTo Association Not Contains some Records has not been verified, Reason: "
														+ verifyRelatedToNotContainsNegativeResults);
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

								refresh(driver);
								ThreadSleep(10000);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

									String url2 = getURL(driver, 10);

									ThreadSleep(10000);
									ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
													task1BasicSectionVerification, task1AdvancedSectionVerification,
													null);
									if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
										log(LogStatus.INFO,
												"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
														+ NotesPopUpPrefilledNegativeResultUpdated,
												YesNo.No);
										sa.assertTrue(false,
												"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
														+ NotesPopUpPrefilledNegativeResultUpdated);

									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, null, task1SubjectName, task1Notes, true, false,
											RelatedToVerifyInInteraction, null);
									if (updatedresult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " record has been verified on intraction------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------",
												YesNo.No);
										sa.assertTrue(false,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc093_CreateACallAndTagContact1to50InCommentSectionAndCheckInPopUpContact1To50ShouldGetDisplayWithoutAnyContactGetsMissout(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("8"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task bulk contact Call";
		String task1Notes = "";
		String relatedTo = "Max<break>Martha<break>Acc 3<break>Maxtra";

		String priority = "Normal";
		String status = "Completed";
		String task1ButtonName = "Call";
		String[] suggestedTags = null;
		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Max<break>Martha<break>+3".split("<break>");

		String updatedNotesOfTask = "Con 1, con 2, con 3, con 4, con 5, con 6, con 7, con 8, con 9, con 10, con 11, con 12, con 13, con 14, con 15, con 16, con 17, con 18, con 19, con 20, con 21, con 22, con 23, con 24, con 25, con 26, con 27, con 28, con 29, con 30, con 31, con 32, con 33, con 34, con 35, con 36, con 37, con 38, con 39, con 40, con 41, con 42, con 43, con 44, con 45, con 46, con 47, con 48, con 49, con 50";

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "Con 1<break>con 2<break>con 3<break>con 4<break>con 5<break>con 6<break>con 7<break>con 8<break>con 9<break>con 10<break>con 11<break>con 12<break>con 13<break>con 14<break>con 15<break>con 16<break>con 17<break>con 18<break>con 19<break>con 20<break>con 21<break>con 22<break>con 23<break>con 24<break>con 25<break>con 26<break>con 27<break>con 28<break>con 29<break>con 30<break>con 31<break>con 32<break>con 33<break>con 34<break>con 35<break>con 36<break>con 37<break>con 38<break>con 39<break>con 40<break>con 41<break>con 42<break>con 43<break>con 44<break>con 45<break>con 46<break>con 47<break>con 48"
				.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = "Max<break>Martha<break>+51".split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = updatedSuggestedTags;

		String updatedRelatedToVerifyInNotes = (relatedTo + "<break>"
				+ "Con 1<break>con 2<break>con 3<break>con 4<break>con 5<break>con 6<break>con 7<break>con 8<break>con 9<break>con 10<break>con 11<break>con 12<break>con 13<break>con 14<break>con 15<break>con 16<break>con 17<break>con 18<break>con 19<break>con 20<break>con 21<break>con 22<break>con 23<break>con 24<break>con 25<break>con 26<break>con 27<break>con 28<break>con 29<break>con 30<break>con 31<break>con 32<break>con 33<break>con 34<break>con 35<break>con 36<break>con 37<break>con 38<break>con 39<break>con 40<break>con 41<break>con 42<break>con 43<break>con 44<break>con 45<break>con 46<break>con 47<break>con 48");

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc095_CreateACallAndTag13RecordsForRelatedAssociationAnd50Contacts(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("8"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task bulk Firm contact Call";
		String task1Notes = "";
		String relatedTo = "Max<break>Martha<break>Vertica<break>Maxtra";

		String priority = "Normal";
		String status = "Completed";
		String task1ButtonName = "Call";
		String[] suggestedTags = null;
		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Vertica";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Max<break>Martha<break>+3".split("<break>");

		String updatedNotesOfTask = "Con 1, con 2, con 3, con 4, con 5, con 6, con 7, con 8, con 9, con 10, con 11, con 12, con 13, con 14, con 15, con 16, con 17, con 18, con 19, con 20, con 21, con 22, con 23, con 24, con 25, con 26, con 27, con 28, con 29, con 30, con 31, con 32, con 33, con 34, con 35, con 36, con 37, con 38, con 39, con 40, con 41, con 42, con 43, con 44, con 45, con 46, con 47, con 48, con 49, con 50, Acc 1, Acc 2, Acc 3, Acc 4, Acc 5, Acc 6, Acc 7, Acc 8, Acc 9, Acc 10, Acc 11, Acc 12, Acc 13";

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "Con 1<break>con 2<break>con 3<break>con 4<break>con 5<break>con 6<break>con 7<break>con 8<break>con 9<break>con 10<break>con 11<break>con 12<break>con 13<break>con 14<break>con 15<break>con 16<break>con 17<break>con 18<break>con 19<break>con 20<break>con 21<break>con 22<break>con 23<break>con 24<break>con 25<break>con 26<break>con 27<break>con 28<break>con 29<break>con 30<break>con 31<break>con 32<break>con 33<break>con 34<break>con 35<break>con 36<break>con 37<break>con 38<break>con 39<break>con 40<break>con 41<break>con 42<break>con 43<break>con 44<break>con 45<break>con 46<break>con 47<break>con 48<break>Acc 1<break>Acc 2<break>Acc 3<break>Acc 4<break>Acc 5<break>Acc 6<break>Acc 7<break>Acc 8<break>Acc 9<break>Acc 10<break>Acc 11<break>Acc 12<break>Acc 13"
				.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = "Max<break>Martha<break>+64".split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = updatedSuggestedTags;

		String updatedRelatedToVerifyInNotes = (relatedTo + "<break>"
				+ "Con 1<break>con 2<break>con 3<break>con 4<break>con 5<break>con 6<break>con 7<break>con 8<break>con 9<break>con 10<break>con 11<break>con 12<break>con 13<break>con 14<break>con 15<break>con 16<break>con 17<break>con 18<break>con 19<break>con 20<break>con 21<break>con 22<break>con 23<break>con 24<break>con 25<break>con 26<break>con 27<break>con 28<break>con 29<break>con 30<break>con 31<break>con 32<break>con 33<break>con 34<break>con 35<break>con 36<break>con 37<break>con 38<break>con 39<break>con 40<break>con 41<break>con 42<break>con 43<break>con 44<break>con 45<break>con 46<break>con 47<break>con 48<break>Acc 1<break>Acc 2<break>Acc 3<break>Acc 4<break>Acc 5<break>Acc 6<break>Acc 7<break>Acc 8<break>Acc 9<break>Acc 10<break>Acc 11<break>Acc 12<break>Acc 13");

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc096_CreateACallWithMeetingNotesByTaggingCustomObjectsInIt(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("8"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Custom Object Call";
		String task1Notes = "Send the quotation to Martha, jhon, con 11 and Custom Object 1.1 belonging to the Firm Nexus, Custom Object 1.2";
		String relatedTo = "Martha<break>Jhon<break>con 11<break>Sumo Logic<break>Vertica";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Call";
		String[] suggestedTags = "Custom Object 1.1<break>Custom Object 1.2<break>Custom Object 1.3".split("<break>",
				-1);
		String verificationRelatedTo = relatedTo + "<break>"
				+ "Custom Object 1.1<break>Custom Object 1.2<break>Custom Object 1.3";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Martha";

		String[] RelatedToVerifyInInteraction = "Martha<break>Jhon<break>+7".split("<break>", -1);

		String updatedNotesOfTask = "keep in loop  con 4, con 5 Acc 5, Custom Object 1.2, Custom object 1.3";

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "con 4<break>con 5<break>Acc 5".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = "Martha<break>Jhon<break>+10".split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = updatedSuggestedTags;

		String updatedRelatedToVerifyInNotes = (verificationRelatedTo + "<break>" + "con 4<break>con 5<break>Acc 5");

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc097_VerifyRemovingSomeOfTheTaggedFromNotesPopUpOfCallAndVerifyTheSameInInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("9"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Demo Call";
		String task1Notes = "Follow up with Contacts con 4, con 5 about Demo Deal";
		String relatedTo = "Con 1<break>con 2<break>Acc 3<break>Maxtra";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Call";
		String[] suggestedTags = "con 4<break>con 5<break>Demo Deal".split("<break>", -1);
		String verificationRelatedTo = relatedTo + "<break>" + "con 4<break>con 5<break>Demo Deal";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>", -1);

		String updatedNotesOfTask = null;

		String[][] task1UpdateBasicSection = null;
		String[] updatedSuggestedTags = "".split("<break>", -1);
		String[] updatedRemoveRelatedAssociation = "Maxtra<break>Demo Deal".split("<break>", -1);
		String[] updatedRelatedToVerifyInInteraction = "Con 1<break>con 2<break>+4".split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = "Acc 3<break>con 4<break>con 5".split("<break>", -1);

		String updatedRelatedToVerifyInNotes = "Con 1<break>con 2<break>Acc 3<break>con 4<break>con 5";

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, updatedRemoveRelatedAssociation)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc098_VerifyChangingTheStatusOfCallFromAdvancedSectionOfNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("0"));
		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AMNNR_006", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Advance Call";
		String task1Notes = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String relatedTo = "Maxjonic<break>Maxtra<break>Con 1<break>con 2";

		String priority = "Normal";
		String status = "Not Started";
		String task1ButtonName = "Call";
		String[] suggestedTags = "con 4<break>con 5<break>Demo Deal".split("<break>", -1);
		String verificationRelatedTo = relatedTo + "<break>" + "con 4<break>con 5<break>Demo Deal";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Maxjonic";
		String recordType = "Intermediary";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>", -1);

		String updatedNotesOfTask = task1Notes;
		String updatedStatus = "Completed";
		String[][] task1UpdateBasicSection = null;
		String[][] task1UpdateAdvancedSection = { { "Status", updatedStatus } };

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal"
				.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", updatedStatus },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc099_VerifyChangingTheDueDateToFutureOfCallFromAdvancedSectionOfNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_006", excelLabel.Advance_Due_Date);
		String getUpdatedAdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("1"));

		ExcelUtils.writeData(AcuityDataSheetFilePath, getUpdatedAdvanceDueDate, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_007", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Advance Call";
		String task1Notes = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String relatedTo = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal";

		String priority = "Normal";
		String status = "Completed";

		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Maxjonic";
		String recordType = "Intermediary";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String[][] task1UpdateBasicSection = null;
		String[][] task1UpdateAdvancedSection = { { "Due Date Only", getUpdatedAdvanceDueDate } };

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal"
				.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getUpdatedAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getUpdatedAdvanceDueDate, null, task1SubjectName,
													updatedNotesOfTask, true, false,
													updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc100_VerifyChangingTheAssigneeOfCallFromAdvancedSectionOfNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_007", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Advance Call";
		String task1Notes = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String relatedTo = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal";

		String priority = "Normal";
		String status = "Completed";

		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Maxjonic";
		String recordType = "Intermediary";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String[][] task1UpdateBasicSection = null;
		String[][] task1UpdateAdvancedSection = { { "Assigned To ID", crmUser2FirstName + " " + crmUser2LastName } };

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal"
				.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser2FirstName + " " + crmUser2LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc101_VerifyChangingTheSubjectOfCallFromNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_007", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Advance Call";
		String task1Notes = "Follow up with Contacts Con 4, Con 5 about demo deal";
		String relatedTo = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal";

		String priority = "Normal";
		String status = "Completed";

		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser2FirstName + " " + crmUser2LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Maxjonic";
		String recordType = "Intermediary";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+6".split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String task1UpdatedSubjectName = "Task Advance Updated Call";
		String[][] task1UpdateBasicSection = { { "Subject", task1UpdatedSubjectName } };
		;
		String[][] task1UpdateAdvancedSection = null;

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = "Maxjonic<break>Maxtra<break>con 4<break>con 5<break>Demo Deal"
				.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1UpdatedSubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser2FirstName + " " + crmUser2LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1UpdatedSubjectName, 20),
												"Edit Note Button of: " + task1UpdatedSubjectName,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);

											String xPath = "//a[@class=\"interaction_sub subject_text\" and text()='"
													+ task1SubjectName + "']";
											WebElement ele = CommonLib.FindElement(driver, xPath, "Subject",
													action.SCROLLANDBOOLEAN, 15);
											String subName = getText(driver, ele, "Subject", action.SCROLLANDBOOLEAN);
											if (task1SubjectName.equals(subName)) {
												log(LogStatus.INFO, "Verified: After Update the Name of Subject to "
														+ task1UpdatedSubjectName + ", Previous Named: "
														+ task1SubjectName + " Interaction card should not be there",
														YesNo.No);
											} else {
												log(LogStatus.ERROR, "After Update the Name of Subject to "
														+ task1UpdatedSubjectName + ", Previous Named: "
														+ task1SubjectName
														+ " Interaction card is showing, which should not be there",
														YesNo.No);
												result.add("After Update the Name of Subject to "
														+ task1UpdatedSubjectName + ", Previous Named: "
														+ task1SubjectName
														+ " Interaction card is showing, which should not be there");
											}

											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1UpdatedSubjectName,
													updatedNotesOfTask, true, false,
													updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button of Task: "
													+ task1UpdatedSubjectName, YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button of Task: "
													+ task1UpdatedSubjectName);
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc102_VerifyWhenTheOrgHasSameDealNameAsOfTheCompanyNameAndIsTaggedInTheCall(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("8"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Task TQW Call";
		String task1Notes = "Areca";
		String relatedTo = "Acc 12";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Call";

		String[] SuggestedTags = "areca".split("<break>", -1);

		String verificationRelatedTo = relatedTo + "<break>" + "areca";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 12";
		String recordType = "Institution";

		String[] RelatedToVerifyInInteraction = ("areca<break>" + crmUser1FirstName + " " + crmUser1LastName
				+ "<break>+1").split("<break>", -1);

		String updatedNotesOfTask = " Sumo Kind";

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };

		String[] updatedSuggestedTags = "Sumo Kind==Firm<break>Sumo Kind==Deal<break>Sumo Kind==Contact<break>Sumo Kind Fund==Fund<break>Sumo Kind Fundraising==Fundraising"
				.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = "areca<break>Sumo Kind<break>+6".split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = null;

		String updatedRelatedToVerifyInNotes = ("Acc 12==Firm<break>areca==Contact" + "<break>"
				+ "Sumo Kind==Firm<break>Sumo Kind==Deal<break>Sumo Kind==Contact<break>Sumo Kind Fund==Fund<break>Sumo Kind Fundraising==Fundraising");

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */
		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, SuggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecordForMultipleFollowUpTasksAndVerifyUIOfSuggestedTags(
											projectName, task1UpdateBasicSection, null, null, updatedSuggestedTags,
											null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc103_VerifyWhenUserTagsAccountsAndContactsInNotesTextAreaAndClicksOnCloseButtonOrCrossIconInCaseOfCall(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("9"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = "Task Demo 3 Call";
		String task1Notes = "";
		String relatedTo = "Acc 3<break>Maxtra<break>Con 1<break>con 2<break>Demo Deal";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Call";
		String[] suggestedTags = null;
		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] RelatedToVerifyInInteraction = "Con 1<break>con 2<break>+4".split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String updatedRelatedToInNotes = "con 6<break>con 7";
		String[][] task1UpdateBasicSection = { { "Related_To", updatedRelatedToInNotes } };

		String[] updatedSuggestedTags = null;

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = "Acc 3<break>Maxtra<break>Demo Deal".split("<break>",
				-1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, suggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		CommonLib.refresh(driver);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Edit Mode of Task Detail Page---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName,
											task1UpdateBasicSection, null, null, updatedSuggestedTags, null, true,
											false)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

								refresh(driver);

								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName,
											task1UpdateBasicSection, null, null, updatedSuggestedTags, null, false,
											true)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(10000);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
													false, updatedRelatedToVerifyInInteraction,
													updatedRelatedAssociationVerifyInInteraction);
											if (updatedresult.isEmpty()) {
												log(LogStatus.PASS,
														"------" + task1SubjectName
																+ " record has been verified on intraction------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------",
														YesNo.No);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc104_VerifyWhenEditButtonIsClickedForTheCallHavingFollowUpTaskAndIsDeletedFromTaskDetailPage(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String task1SubjectName = "SSend Notice Call";

		String recordName = "Acc 3";
		String recordType = "Company";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Note PopUp Section---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(task1SubjectName)) {
						log(LogStatus.INFO, "Task Detail Page has been open for Record: " + task1SubjectName, YesNo.No);

						if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

							if (click(driver, taskBP.buttonInTheDownArrowList("Delete", 20),
									"Delete Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Delete Button in  Down Arrow Button", YesNo.No);

								if (click(driver, taskBP.taskDeleteConfirmButton(15),
										"Delete Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Delete Confirm Button", YesNo.No);

									if (taskBP.taskDeletedMsg(15) != null) {
										log(LogStatus.INFO, "Task Delete Msg displayed, So Task has been deleted",
												YesNo.No);
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

										log(LogStatus.INFO,
												"---------Now Going to Verify Task: " + task1SubjectName
														+ " is present or not in Interaction Section---------",
												YesNo.No);
										if (lp.clickOnTab(projectName, tabObj1)) {

											log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

											if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
													TabName.InstituitonsTab, recordType, recordName, 30)) {
												log(LogStatus.INFO, recordName + " record of record type " + recordType
														+ " has been open", YesNo.No);
												ThreadSleep(4000);
												if (BP.clicktabOnPage("Acuity")) {
													log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

													if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
															task1SubjectName)) {
														log(LogStatus.INFO,
																"Verified: Task: " + task1SubjectName
																		+ " is not present there after delete",
																YesNo.No);

													} else {
														log(LogStatus.ERROR, "Task: " + task1SubjectName
																+ " is present there after delete", YesNo.Yes);
														sa.assertTrue(false, "Task: " + task1SubjectName
																+ " is present there after delete");
														driver.close();
														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());

													}

												} else {
													log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
													sa.assertTrue(false, "Not able to click on Acuity Tab");
												}

											} else {
												log(LogStatus.ERROR, "Not able to open " + recordName
														+ " record of record type " + recordType, YesNo.No);
												sa.assertTrue(false, "Not able to open " + recordName
														+ " record of record type " + recordType);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
											sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
										}

									} else {
										log(LogStatus.ERROR, "Task Delete Msg not display, So Task not gets deleted",
												YesNo.Yes);
										sa.assertTrue(false, "Task Delete Msg not display, So Task not gets deleted");
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
									}

								} else {
									log(LogStatus.ERROR, "Not ABle to Click on Delete Confirm Button", YesNo.Yes);
									sa.assertTrue(false, "Not ABle to Click on Delete Confirm Button");
									driver.close();
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								}

							} else {
								log(LogStatus.ERROR, "Not Able Click on Delete button in Down Arrow Button", YesNo.Yes);
								sa.assertTrue(false, "Not Able Click on Delete button in Down Arrow Button");
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

						} else {
							log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
							sa.assertTrue(false, "Not Able Click on Down Arrow Button");
							driver.close();
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						}

					} else {
						log(LogStatus.ERROR, "Task Detail Page has not open for Record: " + task1SubjectName,
								YesNo.Yes);
						sa.assertTrue(false, "Task Detail Page has not open for Record: " + task1SubjectName);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else

		{
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc105_VerifyWhenTheRemovedCallIsRestoredAndItsImpactOnInteractionSection(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String recordName = "Acc 3";
		String recordType = "Company";

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_005", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "SSend Notice Call";
		String updatedNotesOfTask = "Follow up task As Send Notice Updated for Con 6,Con 7, Con 8, Acc 4";
		String relatedTo = "Acc 3<break>Martha";

		String priority = "Normal";
		String status = "In Progress";
		String verificationRelatedTo = relatedTo + "<break>" + "con 6<break>con 7<break>con 8<break>Acc 4";
		String updatedRelatedTo = "Mutual Fund<break>FC Fundraising<break>Acc 1";
		String updatedRelatedToVerifyInNotes = verificationRelatedTo + "<break>" + updatedRelatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", updatedNotesOfTask },
				{ "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] task1AdvancedSectionVerification = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] listViewSheetData = {
				{ "user 1", "Recycle Bin", task1SubjectName, "All users can see this list view", "My recycle bin",
						"Record Name", "equals", task1SubjectName, "TextBox" } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		WebElement ele;
		String recycleTab = lp.getTabName(projectName, TabName.RecycleBinTab);
		if (lp.openAppFromAppLauchner(60, recycleTab)) {

			CommonLib.refresh(driver);

			for (String[] row : listViewSheetData) {

				if (lp.addListView(projectName, row, 10)) {
					log(LogStatus.INFO, "list view added on " + row[1], YesNo.No);
					ele = lp.getCheckboxOfRestoreItemOnRecycleBin(projectName, task1SubjectName, 30);
					if (clickUsingJavaScript(driver, ele, "Check box against : " + task1SubjectName, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on checkbox for " + task1SubjectName, YesNo.No);
						;
						ele = lp.getRestoreButtonOnRecycleBin(projectName, 30);
						if (clickUsingJavaScript(driver, ele, "Restore Button : " + task1SubjectName, action.BOOLEAN)) {
							ThreadSleep(10000);
							log(LogStatus.INFO, "Click on Restore Button for " + task1SubjectName, YesNo.No);
							sa.assertTrue(true, "Task has been restore from the Recycle bin");

							CommonLib.switchToDefaultContent(driver);
							ThreadSleep(2000);

							log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjectName
									+ " in Note PopUp Section---------", YesNo.No);
							if (lp.clickOnTab(projectName, tabObj1)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

								if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
										recordType, recordName, 30)) {
									log(LogStatus.INFO,
											recordName + " record of record type " + recordType + " has been open",
											YesNo.No);
									ThreadSleep(4000);
									if (BP.clicktabOnPage("Acuity")) {
										log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

										if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
												task1SubjectName)) {
											log(LogStatus.INFO,
													"Task Detail Page has been open for Record: " + task1SubjectName,
													YesNo.No);

											if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

												if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
														"Delete Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
															YesNo.No);

													String url2 = getURL(driver, 10);

													ThreadSleep(10000);
													ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
															.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
																	task1BasicSectionVerification,
																	task1AdvancedSectionVerification, null);
													if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
														log(LogStatus.INFO,
																"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
																YesNo.No);
														CommonLib.ThreadSleep(3000);
														driver.close();
														CommonLib.ThreadSleep(3000);

														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());

													} else {
														log(LogStatus.ERROR,
																"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																		+ NotesPopUpPrefilledNegativeResultUpdated,
																YesNo.No);
														sa.assertTrue(false,
																"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																		+ NotesPopUpPrefilledNegativeResultUpdated);

														driver.close();
														CommonLib.ThreadSleep(3000);
														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());

													}

												} else {
													log(LogStatus.ERROR,
															"Not Able Click on Edit button in Down Arrow Button",
															YesNo.Yes);
													sa.assertTrue(false,
															"Not Able Click on Edit button in Down Arrow Button");
													driver.close();
													driver.switchTo().window(
															driver.getWindowHandles().stream().findFirst().get());
												}

											} else {
												log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
												sa.assertTrue(false, "Not Able Click on Down Arrow Button");
												driver.close();
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());
											}

										} else {
											log(LogStatus.ERROR,
													"Task Detail Page has not open for Record: " + task1SubjectName,
													YesNo.Yes);
											sa.assertTrue(false,
													"Task Detail Page has not open for Record: " + task1SubjectName);
										}

									} else {
										log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Acuity Tab");
									}

								} else {
									log(LogStatus.ERROR,
											"Not able to open " + recordName + " record of record type " + recordType,
											YesNo.No);
									sa.assertTrue(false,
											"Not able to open " + recordName + " record of record type " + recordType);
								}
							} else

							{
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
							}

						} else {

							log(LogStatus.ERROR, "Not Able to Click on Restore Button for " + task1SubjectName,
									YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Restore Button for " + task1SubjectName);
						}

					} else {

						log(LogStatus.ERROR, "Not Able to Click on checkbox for " + task1SubjectName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on checkbox for " + task1SubjectName);
					}
				}

				else {
					log(LogStatus.FAIL, "list view could not added on " + row[1], YesNo.Yes);
					sa.assertTrue(false, "list view could not added on " + row[1]);
				}
			}

		} else {
			log(LogStatus.ERROR, "Not Able to open the Recycle been tab", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the Recycle been tab");

		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc108_CreateAFollowUpTaskFromCallDetailPageAndVerifyItsDetailsOnTaskDetailPage(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String task1SubjectNameNavigation = "Task bulk contact Call";
		String task1SubjectName = "Task bulk contact Call Follow Up";
		String task1Notes = "";
		String relatedTo = "Max";

		String recordName = "con 15";
		String recordNameVerify = "Max";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[] labelAndValueSeprateByBreak = { "Assigned To" + "<break>" + crmUser1FirstName + " " + crmUser1LastName,
				"Subject" + "<break>" + task1SubjectName,

				"Name" + "<break>" + relatedTo };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(task1SubjectNameNavigation)) {
						log(LogStatus.INFO, "Task Detail Page has been open for Record: " + task1SubjectNameNavigation,
								YesNo.No);

						if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

							if (click(driver, taskBP.buttonInTheDownArrowList("Create Follow-Up Task", 20),
									"Create Follow-Up Task Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Create Follow-Up Task Button in  Down Arrow Button",
										YesNo.No);

								CommonLib.ThreadSleep(5000);
								if (BP.updateActivityTimelineRecord(projectName, task1BasicSection, null, null, null,
										null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);
									driver.close();
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

									if (lp.clickOnTab(projectName, tabObj2)) {

										log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

										if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
												recordNameVerify, 30)) {
											log(LogStatus.INFO, recordNameVerify + " record has been open", YesNo.No);
											ThreadSleep(4000);
											if (BP.clicktabOnPage("Acuity")) {
												log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

												if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
														task1SubjectName)) {
													log(LogStatus.INFO, "Task Detail Page has been open for Record: "
															+ task1SubjectName, YesNo.No);

													CommonLib.ThreadSleep(8000);
													List<String> taskDetailPageNegativeResult = BP
															.fieldValueVerification(labelAndValueSeprateByBreak);

													if (taskDetailPageNegativeResult.isEmpty()) {
														log(LogStatus.PASS, "------" + task1SubjectName
																+ " labels and their values in Detail page has been verified------",
																YesNo.No);
														driver.close();
														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());

													} else {
														log(LogStatus.ERROR, "------" + task1SubjectName
																+ " labels and their values in Detail page has not been verified, Reason: "
																+ taskDetailPageNegativeResult + "------", YesNo.No);
														sa.assertTrue(false, "------" + task1SubjectName
																+ " labels and their values in Detail page has not been verified, Reason: "
																+ taskDetailPageNegativeResult + "------");
														driver.close();
														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());
													}

												} else {
													log(LogStatus.ERROR, "Task Detail Page has not open for Record: "
															+ task1SubjectName, YesNo.Yes);
													sa.assertTrue(false, "Task Detail Page has not open for Record: "
															+ task1SubjectName);
												}

											} else {
												log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
												sa.assertTrue(false, "Not able to click on Acuity Tab");
											}

										} else {
											log(LogStatus.ERROR, "Not able to open " + recordNameVerify + " record",
													YesNo.No);
											sa.assertTrue(false, "Not able to open " + recordNameVerify + " record");
										}
									} else {
										log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
										sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
									}

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");

									driver.close();
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
								}

							} else {
								log(LogStatus.ERROR,
										"Not Able Click on Create Follow-Up Task button in Down Arrow Button",
										YesNo.Yes);
								sa.assertTrue(false,
										"Not Able Click on Create Follow-Up Task button in Down Arrow Button");
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

						} else {
							log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
							sa.assertTrue(false, "Not Able Click on Down Arrow Button");
							driver.close();
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						}

					} else {
						log(LogStatus.ERROR, "Task Detail Page has not open for Record: " + task1SubjectNameNavigation,
								YesNo.Yes);
						sa.assertTrue(false, "Task Detail Page has not open for Record: " + task1SubjectNameNavigation);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc003_CreateAnEventAndVerifyTheNotificationOnRecordPageAndHomePage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Seminar1.0";
		/*
		 * String eventAttendees =
		 * "Dealroom1.3+Max@gmail.com<Break>Dealroom1.3+Martha@gmail.com" + "<Break>" +
		 * crmUser2EmailID;
		 */
		String eventAttendees = "dealroom@navatargroup.com";
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 1);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 1);

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";
		String recordName = "Max";
		String relatedToNotContains = crmUser2FirstName + " " + crmUser2LastName;
		String[][] relatedAssociationNotContains = { { "Related_To", relatedToNotContains } };
		String descriptionBoxOnInteractonCard = "";
		String dateToVerifyOnInteractionCard = startDate + ", " + startTime;
//		String[] RelatedToVerifyInInteraction = "Martha<break>con 6<break>+5".split("<break>");
		/*
		 * String[] updatedRelatedAssociationVerifyInInteraction
		 * ="Con 1<break>con 2<break>con 3<break>con 4<break>con 5<break>con 6<break>con 7<break>con 8<break>con 9<break>con 10<break>con 11<break>con 12<break>con 13<break>con 14<break>con 15<break>con 16<break>con 17<break>con 18<break>con 19<break>con 20<break>con 21<break>con 22<break>con 23<break>con 24<break>con 25<break>con 26<break>con 27<break>con 28<break>con 29<break>con 30<break>con 31<break>con 32<break>con 33<break>con 34<break>con 35<break>con 36<break>con 37<break>con 38<break>con 39<break>con 40<break>con 41<break>con 42<break>con 43<break>con 44<break>con 45<break>con 46<break>con 47<break>con 48"
		 * .split("<break>", -1);
		 */

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			if (home.notificationHeader(15) == null) {
				log(LogStatus.INFO,
						"-----Verified: Notification Pop Up is not showing to Home Page after waiting for 15 seconds-----",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "-----Notification Pop Up is showing to Home Page-----", YesNo.No);
				sa.assertTrue(false, "-----Notification Pop Up is showing to Home Page-----");
			}

			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (lp.clickOnTab(projectName, TabName.HomeTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

					if (home.notificationHeader(10) == null) {
						log(LogStatus.INFO,
								"-----Verified: Notification Pop Up is not showing to Home Page after navigating to tab: "
										+ tabObj2 + " and then to Home Page -----",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"-----Notification Pop Up is showing to Home Page after navigating to tab: " + tabObj2
										+ " and then to Home Page -----",
								YesNo.No);
						sa.assertTrue(false,
								"-----Notification Pop Up is showing to Home Page after navigating to tab: " + tabObj2
										+ " and then to Home Page -----");
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
						List<String> notificationUiNegativeResult = BP
								.verifyNotificationUIWhenNoEventThereOnRecordDetailsPage();
						if (notificationUiNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified UI of Notification when No Record is there", YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Verified the UI of Notification when No Record is there, Reason: "
									+ notificationUiNegativeResult, YesNo.No);
							sa.assertTrue(false, "Not Verified the UI of Notification when No Record is there, Reason: "
									+ notificationUiNegativeResult);
						}

						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(5000);
						ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
								dateToVerifyOnInteractionCard, null, eventTitle, descriptionBoxOnInteractonCard, false,
								true, null, null);
						if (updatedresult.isEmpty()) {
							log(LogStatus.PASS, "------" + eventTitle + " record has been verified on intraction------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + eventTitle
									+ " record is not verified on intraction, Reason: " + updatedresult + "------",
									YesNo.No);
							sa.assertTrue(false, "------" + eventTitle
									+ " record is not verified on intraction, Reason: " + updatedresult + "------");
						}

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);

							if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);

								ThreadSleep(10000);
								ArrayList<String> verifyRelatedToNotContainsNegativeResults = BP
										.verifyRelatedToNotContains(relatedAssociationNotContains);
								if (verifyRelatedToNotContainsNegativeResults.isEmpty()) {
									log(LogStatus.INFO,
											"RelatedTo Association Not Contains some Records has been verified",
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"RelatedTo Association Not Contains some Records has not been verified, Reason: "
													+ verifyRelatedToNotContainsNegativeResults,
											YesNo.No);
									sa.assertTrue(false,
											"RelatedTo Association Not Contains some Records has not been verified, Reason: "
													+ verifyRelatedToNotContainsNegativeResults);
								}
							} else {
								log(LogStatus.ERROR,
										"Not Able Click on Edit button for Record Page of Event: " + eventTitle,
										YesNo.Yes);
								sa.assertTrue(false,
										"Not Able Click on Edit button for Record Page of Event: " + eventTitle);
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

						} else {
							log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
									YesNo.Yes);
							sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + recordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc004_CreateAnEventAndVerifyTheNotificationOnRecordPageAndHomePageForMultipleEventsBasedOnRecordPages(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitles[] = "Event 1.0<Section>Event 2.0".split("<Section>", -1);
		String eventAttendees[] = ("Dealroom1.3+James@gmail.com" + "<Break>" + crmUser2EmailID + "<Section>"
				+ "Dealroom1.3+Litz@gmail.com" + "<Break>" + crmUser2EmailID).split("<Section>", -1);
		String startDate[] = (CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -2) + "<Section>"
				+ CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -2)).split("<Section>", -1);
		String endDate[] = (CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1) + "<Section>"
				+ CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1)).split("<Section>", -1);

		String startTime[] = ("02:00 PM" + "<Section>" + "04:00 PM").split("<Section>", -1);
		String endTime[] = ("03:00 PM" + "<Section>" + "05:00 PM").split("<Section>", -1);
		String descriptionBox = null;

		String updatedRelatedTo[] = "Sumo Logic<break>Maxjonic<Section>Nexus<break>Demo Deal<break>Acc 1"
				.split("<Section>", -1);

		String[][] task1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = null;

		String contactRecordName = "Litz";

		String accountRecordName1 = "Maxjonic";
		String accountRecordType1 = "Intermediary";
		String accountRecordName2 = "Nexus";
		String accountRecordType2 = "Institution";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Event(s): " + eventTitles + " through Outlook---------",
				YesNo.No);

		Integer loopCount = 0;
		Integer status = 0;
		for (String eventTitle : eventTitles) {

			if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
					eventAttendees[loopCount], startDate[loopCount], endDate[loopCount], startTime[loopCount],
					endTime[loopCount], descriptionBox, false)) {
				log(LogStatus.INFO, "-----Event Created Msg is showing, So Event of Title: " + eventTitle
						+ " has been created-----", YesNo.No);

				status++;

			} else {
				log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been created-----", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been created-----");
			}

			loopCount++;
		}
		Integer loop2Count = 0;
		for (String eventTitle : eventTitles) {
			String[][] task1UpdateBasicSection = { { "Related_To", updatedRelatedTo[loop2Count] } };
			CommonLib.refresh(driver);
			if (CommonLib.click(driver, home.addNoteButtonOfEventInHomePageNotification(eventTitle, 20),
					"Add Note Button of Event: " + eventTitle, action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Add Note Button of Event: " + eventTitle, YesNo.No);
				if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, task1UpdateAdvancedSection,
						null, updatedSuggestedTags, null)) {
					log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle, YesNo.No);

				} else {
					log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle, YesNo.No);
					sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Add Noted Button of Event: " + eventTitle, YesNo.No);
				sa.assertTrue(false, "Not Able to Click on Add Noted Button of Event: " + eventTitle);
			}
			loop2Count++;
		}

		if (status.equals(loopCount)) {

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitles);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitles + " on Home Page",
							YesNo.No);

					List<String> notificationOptionsListInText = home.getNotificationOptions().stream()
							.map(x -> CommonLib.getText(driver, x, "Event Name", action.BOOLEAN))
							.collect(Collectors.toList());

					if (notificationOptionsListInText.indexOf(eventTitles[0]) > notificationOptionsListInText
							.indexOf(eventTitles[1])) {
						log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitles[0]
								+ " on Home Page comes under " + eventTitles[1], YesNo.No);
					} else {
						log(LogStatus.ERROR, "Notification for Event(s): " + eventTitles[0]
								+ " on Home Page not comes under " + eventTitles[1], YesNo.No);
						sa.assertTrue(false, "Notification for Event(s): " + eventTitles[0]
								+ " on Home Page not comes under " + eventTitles[1]);

					}

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitles + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitles + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event(s): " + eventTitles[0] + " in " + accountRecordName1
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType1,
						accountRecordName1, 30)) {
					log(LogStatus.INFO,
							accountRecordName1 + " record of record type " + accountRecordType1 + " has been open",
							YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitles[0]);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitles[0], YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitles[0] + ", Reason: "
									+ notificationNegativeResult, YesNo.No);
							sa.assertTrue(false, "Not Verified the Event(s) " + eventTitles[0] + ", Reason: "
									+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR,
							"Not able to open " + accountRecordName1 + " record of record type " + accountRecordType1,
							YesNo.No);
					sa.assertTrue(false,
							"Not able to open " + accountRecordName1 + " record of record type " + accountRecordType1);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event(s): " + eventTitles[1] + " in " + accountRecordName2
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType2,
						accountRecordName2, 30)) {
					log(LogStatus.INFO,
							accountRecordName2 + " record of record type " + accountRecordType2 + " has been open",
							YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitles[1]);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitles[1], YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitles[1] + ", Reason: "
									+ notificationNegativeResult, YesNo.No);
							sa.assertTrue(false, "Not Verified the Event(s) " + eventTitles[1] + ", Reason: "
									+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR,
							"Not able to open " + accountRecordName2 + " record of record type " + accountRecordType2,
							YesNo.No);
					sa.assertTrue(false,
							"Not able to open " + accountRecordName2 + " record of record type " + accountRecordType2);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event(s): " + eventTitles[1] + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitles[1]);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"Verified Notification contains for Event(s): " + eventTitles[1]
											+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page"
											+ contactRecordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitles[1] + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitles[1] + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

		}

		else {

			sa.assertTrue(false, "Some of the Given Events not gets created: " + eventTitles
					+ " , So not able to further continue to check on Notification Panes");
			log(LogStatus.SKIP, "Some of the Given Events not gets created: " + eventTitles
					+ " , So not able to further continue to check on Notification Panes", YesNo.Yes);

		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc005_CreateAnEventAndVerifyItOnNotificationOnRecordPageAndHomePage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Seminar";
		String eventAttendees = "Dealroom1.3+Max@gmail.com<Break>Dealroom1.3+Martha@gmail.com" + "<Break>"
				+ crmUser2EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";
		String recordName = "Martha";

		String dateToVerifyOnInteractionCard = startDate + ", " + startTime;
		String[] RelatedToVerifyInInteraction = null;

		String[] RelatedAssociationVerifyInInteraction = null;

		String eventTitleUpdated = "Seminar Done";
		String updatedRelatedTo = "Houlihan Lokey<break>Sumo Logic";

		String[][] task1UpdateBasicSection = { { "Subject", eventTitleUpdated }, { "Related_To", updatedRelatedTo } };
		String[][] task1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = null;
		String contactRecordName = recordName;
		String accountRecordName = "Houlihan Lokey";
		String accountRecordType = "Institution";

		String[][] event1BasicSectionVerificationInNotesPopup = { { "Subject", eventTitle },
				{ "Notes", descriptionBox }, { "Related_To", "" } };
		String[][] event1AdvancedSectionVerificationInNotesPopup = { { "Location", "" },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName },
				{ "Start Date Time<break>Date", startDate }, { "Start Date Time<break>Time", startTime },
				{ "End Date Time<break>Date", endDate }, { "End Date Time<break>Time", endTime } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle, YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + ", Reason: "
									+ notificationNegativeResult, YesNo.No);
							sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + ", Reason: "
									+ notificationNegativeResult);
						}

						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(5000);
						ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
								dateToVerifyOnInteractionCard, null, eventTitle, descriptionBox, false, true,
								RelatedToVerifyInInteraction, RelatedAssociationVerifyInInteraction);
						if (updatedresult.isEmpty()) {
							log(LogStatus.PASS, "------" + eventTitle + " record has been verified on intraction------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + eventTitle
									+ " record is not verified on intraction, Reason: " + updatedresult + "------",
									YesNo.No);
							sa.assertTrue(false, "------" + eventTitle
									+ " record is not verified on intraction, Reason: " + updatedresult + "------");
						}

						if (lp.clickOnTab(projectName, TabName.HomeTab)) {
							log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

							CommonLib.ThreadSleep(5000);
							List<String> notificationHomeNegativeResult2 = home.verifyNotificationOptions(eventTitle);
							if (notificationHomeNegativeResult2.isEmpty()) {
								log(LogStatus.INFO,
										"Verified Notification for Event(s): " + eventTitle + " on Home Page",
										YesNo.No);

								if (CommonLib.click(driver,
										home.addNoteButtonOfEventInHomePageNotification(eventTitle, 20),
										"Add Note Button of Event: " + eventTitle, action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Add Note Button of Event: " + eventTitle, YesNo.No);

									String url2 = getURL(driver, 10);
									ThreadSleep(10000);
									ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
													event1BasicSectionVerificationInNotesPopup,
													event1AdvancedSectionVerificationInNotesPopup, null);
									if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
										log(LogStatus.INFO,
												"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
														+ NotesPopUpPrefilledNegativeResultUpdated,
												YesNo.No);
										sa.assertTrue(false,
												"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
														+ NotesPopUpPrefilledNegativeResultUpdated);
									}

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS,
												"Activity timeline record has been Updated for: " + eventTitle,
												YesNo.No);

										if (lp.clickOnTab(projectName, tabObj2)) {

											log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

											if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
													recordName, 30)) {
												log(LogStatus.INFO, contactRecordName + " record has been open",
														YesNo.No);
												ThreadSleep(4000);
												if (BP.clicktabOnPage("Acuity")) {
													log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
													if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
															eventTitleUpdated)) {
														log(LogStatus.INFO,
																"Task Detail Page has been open for Record: "
																		+ eventTitleUpdated,
																YesNo.No);
														driver.close();
														CommonLib.ThreadSleep(3000);
														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());
													} else {
														log(LogStatus.ERROR,
																"Record Detail Page has not open for Record: "
																		+ eventTitleUpdated,
																YesNo.Yes);
														sa.assertTrue(false,
																"Record Detail Page has not open for Record: "
																		+ eventTitleUpdated);

													}

												} else {
													log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
													sa.assertTrue(false, "Not able to click on Acuity Tab");
												}

											} else {
												log(LogStatus.ERROR,
														"Not able to open " + contactRecordName + " record", YesNo.No);
												sa.assertTrue(false,
														"Not able to open " + contactRecordName + " record");
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
											sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
										}

										if (lp.clickOnTab(projectName, tabObj1)) {

											log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

											if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
													TabName.InstituitonsTab, accountRecordType, accountRecordName,
													30)) {
												log(LogStatus.INFO, accountRecordName + " record of record type "
														+ accountRecordType + " has been open", YesNo.No);
												ThreadSleep(4000);
												if (BP.clicktabOnPage("Acuity")) {
													log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

													if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
															eventTitleUpdated)) {
														log(LogStatus.INFO,
																"Task Detail Page has been open for Record: "
																		+ eventTitleUpdated,
																YesNo.No);
														driver.close();
														CommonLib.ThreadSleep(3000);
														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());
													} else {
														log(LogStatus.ERROR,
																"Record Detail Page has not open for Record: "
																		+ eventTitleUpdated,
																YesNo.Yes);
														sa.assertTrue(false,
																"Record Detail Page has not open for Record: "
																		+ eventTitleUpdated);

													}
												} else {
													log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
													sa.assertTrue(false, "Not able to click on Acuity Tab");
												}

											} else {
												log(LogStatus.ERROR,
														"Not able to open " + accountRecordName
																+ " record of record type " + accountRecordType,
														YesNo.No);
												sa.assertTrue(false, "Not able to open " + accountRecordName
														+ " record of record type " + accountRecordType);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
											sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
										}

									} else {
										log(LogStatus.FAIL,
												"Activity timeline record has not Updated for: " + eventTitle,
												YesNo.No);
										sa.assertTrue(false,
												"Activity timeline record has not Updated for: " + eventTitle);
									}

								} else {
									log(LogStatus.ERROR,
											"Not Able to Click on Add Noted Button of Event: " + eventTitle, YesNo.No);
									sa.assertTrue(false,
											"Not Able to Click on Add Noted Button of Event: " + eventTitle);
								}

							} else {
								log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
										+ "+ on Home Page, Reason: " + notificationHomeNegativeResult2, YesNo.No);
								sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
										+ "+ on Home Page, Reason: " + notificationHomeNegativeResult2);
							}
						} else {
							sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
							log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + recordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc006_LoginToTheOrgAsUser2AndVerifyTheNotificationPopUp(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitleUpdated = "Seminar Done";
		String contactRecordName = "Max";
		String contactRecordNameUser1User2AndAdminNotContain = "Con 1";

		String accountRecordName = "Sumo Logic";
		String accountRecordType = "Company";
		String accountRecordNameUser1User2AndAdminNotContain = "Acc 3";
		String accountRecordTypeUser1User2AndAdminNotContain = "Company";

		lp.CRMLogin(crmUser2EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Verify Event: " + eventTitleUpdated
						+ " on Home Page as well as on Detail Page in case of User 1: " + crmUser2EmailID + "---------",
				YesNo.No);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

			List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitleUpdated);
			if (notificationHomeNegativeResult.isEmpty()) {
				log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleUpdated + " on Home Page",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitleUpdated + "+ on Home Page, Reason: "
						+ notificationHomeNegativeResult, YesNo.No);
				sa.assertTrue(false, "Not Verified the Event(s) " + eventTitleUpdated + "+ on Home Page, Reason: "
						+ notificationHomeNegativeResult);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in " + contactRecordName
				+ " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsOnRecordDetailsPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleUpdated, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitleUpdated + ", Reason: "
								+ notificationNegativeResult, YesNo.No);
						sa.assertTrue(false, "Not Verified the Event(s) " + eventTitleUpdated + ", Reason: "
								+ notificationNegativeResult);
					}

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has been open for Record: " + eventTitleUpdated,
								YesNo.No);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					} else {
						log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitleUpdated,
								YesNo.Yes);
						sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitleUpdated);

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in " + accountRecordName
				+ " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType,
					accountRecordName, 30)) {
				log(LogStatus.INFO,
						accountRecordName + " record of record type " + accountRecordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsOnRecordDetailsPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleUpdated, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitleUpdated + ", Reason: "
								+ notificationNegativeResult, YesNo.No);
						sa.assertTrue(false, "Not Verified the Event(s) " + eventTitleUpdated + ", Reason: "
								+ notificationNegativeResult);
					}

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has been open for Record: " + eventTitleUpdated,
								YesNo.No);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					} else {
						log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitleUpdated,
								YesNo.Yes);
						sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitleUpdated);

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType,
						YesNo.No);
				sa.assertTrue(false,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in "
				+ contactRecordNameUser1User2AndAdminNotContain + " Record not contains in case of User 2---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					contactRecordNameUser1User2AndAdminNotContain, 30)) {
				log(LogStatus.INFO, contactRecordNameUser1User2AndAdminNotContain + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitleUpdated
								+ " in case of User 2 in Detail Page" + contactRecordNameUser1User2AndAdminNotContain,
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain in case of User 2 in Detail Page: "
										+ contactRecordNameUser1User2AndAdminNotContain + " , Reason: "
										+ notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain in case of User 2 in Detail Page: "
										+ contactRecordNameUser1User2AndAdminNotContain + " , Reason: "
										+ notificationNegativeResult);
					}

					if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has not been open for Record: " + eventTitleUpdated
								+ " in case of User 2 in Detail Page: " + contactRecordNameUser1User2AndAdminNotContain,
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of User 2 in Detail Page: " + contactRecordNameUser1User2AndAdminNotContain,
								YesNo.Yes);
						sa.assertTrue(false,
								"Event Record Detail Page  has opened for Record: " + eventTitleUpdated
										+ " in case of User 2 in Detail Page: "
										+ contactRecordNameUser1User2AndAdminNotContain);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordNameUser1User2AndAdminNotContain + " record",
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordNameUser1User2AndAdminNotContain + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Event: " + eventTitleUpdated + " in "
						+ accountRecordNameUser1User2AndAdminNotContain
						+ " Record not contains in Notification Pane in case of User 2---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					accountRecordTypeUser1User2AndAdminNotContain, accountRecordNameUser1User2AndAdminNotContain, 30)) {
				log(LogStatus.INFO, accountRecordNameUser1User2AndAdminNotContain + " record of record type "
						+ accountRecordTypeUser1User2AndAdminNotContain + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitleUpdated
								+ " in case of User 2 in Detail Page" + accountRecordNameUser1User2AndAdminNotContain,
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain i case of User 2 in Detail Page: "
										+ accountRecordNameUser1User2AndAdminNotContain + " , Reason: "
										+ notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain i case of User 2 in Detail Page: "
										+ accountRecordNameUser1User2AndAdminNotContain + " , Reason: "
										+ notificationNegativeResult);
					}

					if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has not been open for Record: " + eventTitleUpdated
								+ " in case of User 2 in Detail Page: " + accountRecordNameUser1User2AndAdminNotContain,
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of User 2 in Detail Page: " + accountRecordNameUser1User2AndAdminNotContain,
								YesNo.Yes);
						sa.assertTrue(false,
								"Event Record Detail Page  has opened for Record: " + eventTitleUpdated
										+ " in case of User 2 in Detail Page: "
										+ accountRecordNameUser1User2AndAdminNotContain);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + accountRecordNameUser1User2AndAdminNotContain
						+ " record of record type " + accountRecordTypeUser1User2AndAdminNotContain, YesNo.No);
				sa.assertTrue(false, "Not able to open " + accountRecordNameUser1User2AndAdminNotContain
						+ " record of record type " + accountRecordTypeUser1User2AndAdminNotContain);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated
				+ " on Home Page as well as on Detail Page in case of Admin: " + superAdminUserName + "---------",
				YesNo.No);

		lp.CRMLogin(superAdminUserName, adminPassword);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitleUpdated);
			if (notificationHomeNegativeResult.isEmpty()) {
				log(LogStatus.INFO,
						"Verified Notification for Event(s): " + eventTitleUpdated + " on Home Page not contains",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"The Event(s) " + eventTitleUpdated
								+ "+ on Home Page should not contain in case of Admin, Reason: "
								+ notificationHomeNegativeResult,
						YesNo.No);
				sa.assertTrue(false,
						"The Event(s) " + eventTitleUpdated
								+ "+ on Home Page should not contain in case of Admin, Reason: "
								+ notificationHomeNegativeResult);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in " + contactRecordName
				+ " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitleUpdated
								+ " in case of Admin in Detail Page" + contactRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain i case of Admin in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain i case of Admin in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult);
					}

					if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has not been open for Record: " + eventTitleUpdated
								+ " in case of Admin in Detail Page: " + contactRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of Admin in Detail Page: " + contactRecordName, YesNo.Yes);
						sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of Admin in Detail Page: " + contactRecordName);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in " + accountRecordName
				+ " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType,
					accountRecordName, 30)) {
				log(LogStatus.INFO,
						accountRecordName + " record of record type " + accountRecordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitleUpdated
								+ " in case of Admin in Detail Page" + accountRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain in case of Admin in Detail Page: " + accountRecordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain in case of Admin in Detail Page: " + accountRecordName
										+ " , Reason: " + notificationNegativeResult);
					}

					if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has not been open for Record: " + eventTitleUpdated
								+ " in case of Admin in Detail Page: " + accountRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of Admin in Detail Page: " + accountRecordName, YesNo.Yes);
						sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of Admin in Detail Page: " + accountRecordName);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType,
						YesNo.No);
				sa.assertTrue(false,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();

		lp.CRMLogin(crmUser3EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated
				+ " on Home Page as well as on Detail Page in case of User 3: " + crmUser3EmailID + " ---------",
				YesNo.No);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitleUpdated);
			if (notificationHomeNegativeResult.isEmpty()) {
				log(LogStatus.INFO,
						"Verified Notification for Event(s): " + eventTitleUpdated + " on Home Page not contains",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"The Event(s) " + eventTitleUpdated
								+ "+ on Home Page should not contain in case of User 3, Reason: "
								+ notificationHomeNegativeResult,
						YesNo.No);
				sa.assertTrue(false,
						"The Event(s) " + eventTitleUpdated
								+ "+ on Home Page should not contain in case of User 3, Reason: "
								+ notificationHomeNegativeResult);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in " + contactRecordName
				+ " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitleUpdated
								+ " in case of User 3 in Detail Page" + contactRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain i case of User 3 in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain i case of User 3 in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult);
					}

					if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has not been open for Record: " + eventTitleUpdated
								+ " in case of User 3 in Detail Page: " + contactRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of User 3 in Detail Page: " + contactRecordName, YesNo.Yes);
						sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of User 3 in Detail Page: " + contactRecordName);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in " + accountRecordName
				+ " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType,
					accountRecordName, 30)) {
				log(LogStatus.INFO,
						accountRecordName + " record of record type " + accountRecordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitleUpdated
								+ " in case of User 3 in Detail Page" + accountRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain in case of User 3 in Detail Page: " + accountRecordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain in case of User 3 in Detail Page: " + accountRecordName
										+ " , Reason: " + notificationNegativeResult);
					}

					if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has not been open for Record: " + eventTitleUpdated
								+ " in case of User 3 in Detail Page: " + accountRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of User 3 in Detail Page: " + accountRecordName, YesNo.Yes);
						sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of User 3 in Detail Page: " + accountRecordName);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType,
						YesNo.No);
				sa.assertTrue(false,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();

		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc007_UpdateAllEventFieldsOneByOneAndVerifyCustomNotification(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitleUpdated = "Seminar Done";
		String contactRecordName = "Martha";

		String accountRecordName = "Sumo Logic";
		String accountRecordType = "Company";

		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);
		startDate = CommonLib.convertDateFromOneFormatToAnother(startDate, "dd/MM/yyyy", "M/d/yyyy");
		endDate = CommonLib.convertDateFromOneFormatToAnother(endDate, "dd/MM/yyyy", "M/d/yyyy");

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";
		String[] labelAndValueSeprateByBreak = { "Assigned To" + "<break>" + crmUser1FirstName + " " + crmUser1LastName,
				"Subject" + "<break>" + eventTitleUpdated, "Name" + "<break>" + "" };

		String[][] event1BasicSectionVerificationInNotesPopup = { { "Subject", eventTitleUpdated }, { "Notes", "" },
				{ "Related_To", "" } };
		String[][] event1AdvancedSectionVerificationInNotesPopup = { { "Location", "" },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName },
				{ "Start Date Time<break>Date", startDate }, { "Start Date Time<break>Time", startTime },
				{ "End Date Time<break>Date", endDate }, { "End Date Time<break>Time", endTime } };

		String[][] event1UpdateBasicSection = { { "Related_To", "Vertica" } };
		String[][] event1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = null;
		String getAdvanceDueDate = startDate + ", " + startTime;
		String updatedNotesOfTask = "";
		String[] updatedRelatedToVerify = null;

		String updateStartDateOnHomepageNotePopup = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);
		String updateEndDateOnHomepageNotePopup = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0);
		String[][] event1UpdateBasicSectionOnHomepageNotePopup = {
				{ "Start Date Time<break>Date", updateStartDateOnHomepageNotePopup },
				{ "End Date Time<break>Date", updateEndDateOnHomepageNotePopup } };
		String[][] event1UpdateAdvancedSectionOnHomepageNotePopup = null;
		String[] eventupdatedSuggestedTagsOnHomepageNotePopup = null;
		String getAdvanceDueDate2 = updateStartDateOnHomepageNotePopup + ", " + startTime;
		String[][] event1UpdateBasicSectionOnHomepageNotePopup2 = { { "Location", "NY" } };
		String[][] event1UpdateAdvancedSectionOnHomepageNotePopup2 = null;
		String[] eventupdatedSuggestedTagsOnHomepageNotePopup2 = null;

		String recordName = "Vertica";
		String recordType = "Company";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated
				+ " on Home Page as well as on Detail Page ---------", YesNo.No);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has been open for Record: " + eventTitleUpdated,
								YesNo.No);

						CommonLib.ThreadSleep(8000);
						List<String> eventDetailPageNegativeResult = BP
								.fieldValueVerification(labelAndValueSeprateByBreak);

						if (eventDetailPageNegativeResult.isEmpty()) {
							log(LogStatus.PASS,
									"------" + eventTitleUpdated
											+ " labels and their values in Detail page has been verified------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + eventTitleUpdated
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ eventDetailPageNegativeResult + "------",
									YesNo.No);
							sa.assertTrue(false,
									"------" + eventTitleUpdated
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ eventDetailPageNegativeResult + "------");

						}

						if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);

							ThreadSleep(10000);
							String url2 = getURL(driver, 10);
							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
											event1BasicSectionVerificationInNotesPopup,
											event1AdvancedSectionVerificationInNotesPopup, null);
							if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								if (BP.updateActivityTimelineRecord(projectName, event1UpdateBasicSection,
										event1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
									log(LogStatus.PASS,
											"Activity timeline record has been Updated for: " + eventTitleUpdated,
											YesNo.No);

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);

									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, null, eventTitleUpdated, updatedNotesOfTask, false, true,
											updatedRelatedToVerify, null);
									if (updatedresult.isEmpty()) {
										log(LogStatus.PASS, "------" + eventTitleUpdated
												+ " record has been verified on intraction------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + eventTitleUpdated
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------",
												YesNo.No);
										sa.assertTrue(false,
												"------" + eventTitleUpdated
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------");
									}

									if (lp.clickOnTab(projectName, TabName.HomeTab)) {
										log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

										if (CommonLib.click(driver,
												home.eventLinkInHomePageNotification(eventTitleUpdated, 20),
												"Link of Event: " + eventTitleUpdated, action.BOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Link of Event: " + eventTitleUpdated,
													YesNo.No);

											if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO,
														"Clicked on Edit Button of Event: " + eventTitleUpdated,
														YesNo.No);

												if (BP.updateActivityTimelineRecord(projectName,
														event1UpdateBasicSectionOnHomepageNotePopup,
														event1UpdateAdvancedSectionOnHomepageNotePopup, null,
														eventupdatedSuggestedTagsOnHomepageNotePopup, null)) {
													log(LogStatus.PASS,
															"Activity timeline record has been Updated for: "
																	+ eventTitleUpdated,
															YesNo.No);
													if (lp.clickOnTab(projectName, tabObj1)) {

														log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

														if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
																TabName.InstituitonsTab, accountRecordType,
																accountRecordName, 30)) {
															log(LogStatus.INFO,
																	accountRecordName + " record of record type "
																			+ accountRecordType + " has been open",
																	YesNo.No);
															ThreadSleep(4000);
															if (BP.clicktabOnPage("Acuity")) {
																log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

																ArrayList<String> updatedresult2 = BP
																		.verifyRecordOnInteractionCard(
																				getAdvanceDueDate2, null,
																				eventTitleUpdated, updatedNotesOfTask,
																				false, true, updatedRelatedToVerify,
																				null);
																if (updatedresult2.isEmpty()) {
																	log(LogStatus.PASS, "------" + eventTitleUpdated
																			+ " record has been verified on intraction------",
																			YesNo.No);

																} else {
																	log(LogStatus.ERROR, "------" + eventTitleUpdated
																			+ " record is not verified on intraction, Reason: "
																			+ updatedresult2 + "------", YesNo.No);
																	sa.assertTrue(false, "------" + eventTitleUpdated
																			+ " record is not verified on intraction, Reason: "
																			+ updatedresult2 + "------");
																}

																if (lp.clickOnTab(projectName, TabName.HomeTab)) {
																	log(LogStatus.INFO,
																			"Click on Tab : " + TabName.HomeTab,
																			YesNo.No);
																	List<String> notificationHomeNegativeResult = home
																			.verifyNotificationOptions(
																					eventTitleUpdated);
																	if (notificationHomeNegativeResult.isEmpty()) {
																		log(LogStatus.INFO,
																				"Verified Notification for Event(s): "
																						+ eventTitleUpdated
																						+ " on Home Page",
																				YesNo.No);

																	} else {
																		log(LogStatus.ERROR,
																				"Not Verified the Event(s) "
																						+ eventTitleUpdated
																						+ "+ on Home Page, Reason: "
																						+ notificationHomeNegativeResult,
																				YesNo.No);
																		sa.assertTrue(false,
																				"Not Verified the Event(s) "
																						+ eventTitleUpdated
																						+ "+ on Home Page, Reason: "
																						+ notificationHomeNegativeResult);
																	}
																} else {
																	sa.assertTrue(false, "Not Able to Click on Tab : "
																			+ TabName.HomeTab);
																	log(LogStatus.SKIP, "Not Able to Click on Tab : "
																			+ TabName.HomeTab, YesNo.Yes);
																}

															} else {
																log(LogStatus.ERROR, "Not able to click on Acuity Tab",
																		YesNo.No);
																sa.assertTrue(false, "Not able to click on Acuity Tab");
															}

														} else {
															log(LogStatus.ERROR,
																	"Not able to open " + recordName
																			+ " record of record type " + recordType,
																	YesNo.No);
															sa.assertTrue(false, "Not able to open " + recordName
																	+ " record of record type " + recordType);
														}
													} else {
														log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1,
																YesNo.No);
														sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
													}

												} else {
													log(LogStatus.FAIL, "Activity timeline record has not Updated for: "
															+ eventTitleUpdated, YesNo.No);
													sa.assertTrue(false,
															"Activity timeline record has not Updated for: "
																	+ eventTitleUpdated);
												}

											} else {
												log(LogStatus.ERROR,
														"Not Able Click on Edit button for Record Page of Event: "
																+ eventTitleUpdated,
														YesNo.Yes);
												sa.assertTrue(false,
														"Not Able Click on Edit button for Record Page of Event: "
																+ eventTitleUpdated);
												driver.close();
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());
											}

										} else {
											log(LogStatus.ERROR,
													"Not Able to Click on Link of Event: " + eventTitleUpdated,
													YesNo.No);
											sa.assertTrue(false,
													"Not Able to Click on Link of Event: " + eventTitleUpdated);
										}

									} else {
										sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
										log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
									}

									if (lp.clickOnTab(projectName, TabName.HomeTab)) {
										log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

										if (CommonLib.click(driver,
												home.eventLinkInHomePageNotification(eventTitleUpdated, 20),
												"Link of Event: " + eventTitleUpdated, action.BOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Link of Event: " + eventTitleUpdated,
													YesNo.No);

											if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO,
														"Clicked on Edit Button of Event: " + eventTitleUpdated,
														YesNo.No);

												if (BP.updateActivityTimelineRecord(projectName,
														event1UpdateBasicSectionOnHomepageNotePopup2,
														event1UpdateAdvancedSectionOnHomepageNotePopup2, null,
														eventupdatedSuggestedTagsOnHomepageNotePopup2, null)) {
													log(LogStatus.PASS,
															"Activity timeline record has been Updated for: "
																	+ eventTitleUpdated,
															YesNo.No);
													if (lp.clickOnTab(projectName, tabObj1)) {

														log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

														if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
																TabName.InstituitonsTab, accountRecordType,
																accountRecordName, 30)) {
															log(LogStatus.INFO,
																	accountRecordName + " record of record type "
																			+ accountRecordType + " has been open",
																	YesNo.No);
															ThreadSleep(4000);
															if (BP.clicktabOnPage("Acuity")) {
																log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

																ArrayList<String> updatedresult2 = BP
																		.verifyRecordOnInteractionCard(
																				getAdvanceDueDate2, null,
																				eventTitleUpdated, updatedNotesOfTask,
																				false, true, updatedRelatedToVerify,
																				null);
																if (updatedresult2.isEmpty()) {
																	log(LogStatus.PASS, "------" + eventTitleUpdated
																			+ " record has been verified on intraction------",
																			YesNo.No);

																} else {
																	log(LogStatus.ERROR, "------" + eventTitleUpdated
																			+ " record is not verified on intraction, Reason: "
																			+ updatedresult2 + "------", YesNo.No);
																	sa.assertTrue(false, "------" + eventTitleUpdated
																			+ " record is not verified on intraction, Reason: "
																			+ updatedresult2 + "------");
																}

																if (lp.clickOnTab(projectName, TabName.HomeTab)) {
																	log(LogStatus.INFO,
																			"Click on Tab : " + TabName.HomeTab,
																			YesNo.No);
																	List<String> notificationHomeNegativeResult = home
																			.verifyNotificationOptions(
																					eventTitleUpdated);
																	if (notificationHomeNegativeResult.isEmpty()) {
																		log(LogStatus.INFO,
																				"Verified Notification for Event(s): "
																						+ eventTitleUpdated
																						+ " on Home Page",
																				YesNo.No);

																	} else {
																		log(LogStatus.ERROR,
																				"Not Verified the Event(s) "
																						+ eventTitleUpdated
																						+ "+ on Home Page, Reason: "
																						+ notificationHomeNegativeResult,
																				YesNo.No);
																		sa.assertTrue(false,
																				"Not Verified the Event(s) "
																						+ eventTitleUpdated
																						+ "+ on Home Page, Reason: "
																						+ notificationHomeNegativeResult);
																	}
																} else {
																	sa.assertTrue(false, "Not Able to Click on Tab : "
																			+ TabName.HomeTab);
																	log(LogStatus.SKIP, "Not Able to Click on Tab : "
																			+ TabName.HomeTab, YesNo.Yes);
																}

															} else {
																log(LogStatus.ERROR, "Not able to click on Acuity Tab",
																		YesNo.No);
																sa.assertTrue(false, "Not able to click on Acuity Tab");
															}

														} else {
															log(LogStatus.ERROR,
																	"Not able to open " + recordName
																			+ " record of record type " + recordType,
																	YesNo.No);
															sa.assertTrue(false, "Not able to open " + recordName
																	+ " record of record type " + recordType);
														}
													} else {
														log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1,
																YesNo.No);
														sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
													}

												} else {
													log(LogStatus.FAIL, "Activity timeline record has not Updated for: "
															+ eventTitleUpdated, YesNo.No);
													sa.assertTrue(false,
															"Activity timeline record has not Updated for: "
																	+ eventTitleUpdated);
												}

											} else {
												log(LogStatus.ERROR,
														"Not Able Click on Edit button for Record Page of Event: "
																+ eventTitleUpdated,
														YesNo.Yes);
												sa.assertTrue(false,
														"Not Able Click on Edit button for Record Page of Event: "
																+ eventTitleUpdated);
												driver.close();
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());
											}

										} else {
											log(LogStatus.ERROR,
													"Not Able to Click on Link of Event: " + eventTitleUpdated,
													YesNo.No);
											sa.assertTrue(false,
													"Not Able to Click on Link of Event: " + eventTitleUpdated);
										}

									} else {
										sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
										log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
									}

								} else {
									log(LogStatus.FAIL,
											"Activity timeline record has not Updated for: " + eventTitleUpdated,
											YesNo.No);
									sa.assertTrue(false,
											"Activity timeline record has not Updated for: " + eventTitleUpdated);
								}

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResultUpdated,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResultUpdated);
							}
						} else {
							log(LogStatus.ERROR,
									"Not Able Click on Edit button for Record Page of Event: " + eventTitleUpdated,
									YesNo.Yes);
							sa.assertTrue(false,
									"Not Able Click on Edit button for Record Page of Event: " + eventTitleUpdated);
							driver.close();
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						}

					} else {
						log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitleUpdated,
								YesNo.Yes);
						sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitleUpdated);

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc008_AddTheNotesFromInteractionnSectionAddNotesButtonAndVerifyTheNotification(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitleUpdated = "Seminar Done";
		String contactRecordName = "Max";

		String accountRecordName = "Houlihan Lokey";
		String accountRecordType = "Institution";

		String[][] event1BasicSectionVerificationInNotesPopup = { { "Location", "NY" } };
		String[][] event1AdvancedSectionVerificationInNotesPopup = null;
		String[][] event1UpdateBasicSection = {
				{ "Notes", "Lomez and Vertica Houlihan Lokey can be considered as good opportunity." } };
		String[][] event1UpdateAdvancedSection = null;
		String[] event1UpdatedSuggestedTags = "Vertica<break>Houlihan Lokey<break>Lomez".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated
				+ " on Home Page as well as on Detail Page ---------", YesNo.No);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResultOnContactDetailPage = BP
							.verifyNotificationOptionsOnRecordDetailsPage(eventTitleUpdated);
					if (notificationNegativeResultOnContactDetailPage.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleUpdated, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitleUpdated + ", Reason: "
								+ notificationNegativeResultOnContactDetailPage, YesNo.No);
						sa.assertTrue(false, "Not Verified the Event(s) " + eventTitleUpdated + ", Reason: "
								+ notificationNegativeResultOnContactDetailPage);
					}

					if (CommonLib.click(driver,
							home.addNoteButtonInNotificationOfRecordDetailPage(eventTitleUpdated, 20),
							"Add Note Button of Event: " + eventTitleUpdated, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Add Note Button of Event: " + eventTitleUpdated, YesNo.No);

						String url2 = getURL(driver, 10);
						ThreadSleep(10000);
						ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
								.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
										event1BasicSectionVerificationInNotesPopup,
										event1AdvancedSectionVerificationInNotesPopup, null);
						if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
							log(LogStatus.INFO,
									"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
											+ NotesPopUpPrefilledNegativeResultUpdated,
									YesNo.No);
							sa.assertTrue(false,
									"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
											+ NotesPopUpPrefilledNegativeResultUpdated);
						}

						if (BP.updateActivityTimelineRecord(projectName, event1UpdateBasicSection,
								event1UpdateAdvancedSection, null, event1UpdatedSuggestedTags, null)) {
							log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitleUpdated,
									YesNo.No);

							CommonLib.refresh(driver);

							List<String> notificationNegativeResultNotContainsOnContactRecordDetailPage = BP
									.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
							if (notificationNegativeResultNotContainsOnContactRecordDetailPage.isEmpty()) {
								log(LogStatus.INFO, "Verified Notification not contains Record(s): " + eventTitleUpdated
										+ " , after Add the Notes from Interaction Card", YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"Not Verified Notification doesn't contain record(s) : " + eventTitleUpdated
												+ ", after Add the Notes from Interaction Card, Reason: "
												+ notificationNegativeResultNotContainsOnContactRecordDetailPage,
										YesNo.No);
								sa.assertTrue(false,
										"Not Verified Notification doesn't contain record(s) : " + eventTitleUpdated
												+ ", after Add the Notes from Interaction Card, Reason: "
												+ notificationNegativeResultNotContainsOnContactRecordDetailPage);
							}

							if (lp.clickOnTab(projectName, tabObj1)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

								if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
										accountRecordType, accountRecordName, 30)) {
									log(LogStatus.INFO, accountRecordName + " record of record type "
											+ accountRecordType + " has been open", YesNo.No);
									ThreadSleep(4000);
									if (BP.clicktabOnPage("Acuity")) {
										log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

										if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
												eventTitleUpdated)) {
											log(LogStatus.INFO,
													"Event Detail Page has been open for Record: " + eventTitleUpdated,
													YesNo.No);
											driver.close();
											CommonLib.ThreadSleep(3000);
											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										} else {
											log(LogStatus.ERROR,
													"Record Detail Page has not open for Record: " + eventTitleUpdated,
													YesNo.Yes);
											sa.assertTrue(false,
													"Record Detail Page has not open for Record: " + eventTitleUpdated);

										}

										List<String> notificationNegativeResultNotContainsOnAccountRecordDetailPage = BP
												.verifyNotificationOptionsNotContainsInRecordDetailPage(
														eventTitleUpdated);
										if (notificationNegativeResultNotContainsOnAccountRecordDetailPage.isEmpty()) {
											log(LogStatus.INFO,
													"Verified Notification not contains Record(s): " + eventTitleUpdated
															+ " , after Add the Notes from Interaction Card",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"Not Verified Notification doesn't contain record(s) : "
															+ eventTitleUpdated
															+ ", after Add the Notes from Interaction Card, Reason: "
															+ notificationNegativeResultNotContainsOnAccountRecordDetailPage,
													YesNo.No);
											sa.assertTrue(false,
													"Not Verified Notification doesn't contain record(s) : "
															+ eventTitleUpdated
															+ ", after Add the Notes from Interaction Card, Reason: "
															+ notificationNegativeResultNotContainsOnAccountRecordDetailPage);
										}

									} else {
										log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Acuity Tab");
									}

								} else {
									log(LogStatus.ERROR, "Not able to open " + accountRecordName
											+ " record of record type " + accountRecordType, YesNo.No);
									sa.assertTrue(false, "Not able to open " + accountRecordName
											+ " record of record type " + accountRecordType);
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
							}

							if (lp.clickOnTab(projectName, TabName.HomeTab)) {
								log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
								List<String> notificationHomeNegativeResult = home
										.verifyNotificationOptionsNotContains(eventTitleUpdated);
								if (notificationHomeNegativeResult.isEmpty()) {
									log(LogStatus.INFO, "Verified Event(s): " + eventTitleUpdated
											+ " not contains in Notification pane in Home Page", YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Not Verified Event(s): " + eventTitleUpdated
													+ " not contains in Notification pane in Home Page, Reason: "
													+ notificationHomeNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"Not Verified Event(s): " + eventTitleUpdated
													+ " not contains in Notification pane in Home Page, Reason: "
													+ notificationHomeNegativeResult);
								}
							} else {
								sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
								log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
							}

						} else {
							log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitleUpdated,
									YesNo.No);
							sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitleUpdated);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Add Noted Button of Event: " + eventTitleUpdated,
								YesNo.No);
						sa.assertTrue(false, "Not Able to Click on Add Noted Button of Event: " + eventTitleUpdated);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc010_CreateEventAndAddTheNotesByLogingAsAttendeeUserAndVerifyTheReminderByLogingAsAssignedUser(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Deal Booking Event 1";
		String eventAttendees = "Dealroom1.3+James@gmail.com<Break>Dealroom1.3+Jhon@gmail.com" + "<Break>" + "<Break>"
				+ crmUser1EmailID + "<Break>" + crmUser2EmailID + "<Break>" + crmUser3EmailID + "<Break>"
				+ superAdminUserName;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String task1NotesUpdated = "As per your info tagging Account Houlihan Lokey, Acc 11 and Contact  Lomez too.";
		String[][] task1UpdateBasicSection = { { "Notes", task1NotesUpdated } };
		String[][] task1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = "Houlihan Lokey<break>Acc 11<break>Lomez".split("<break>", -1);
		String contactRecordName = "James";
		String accountRecordName = "Houlihan Lokey";
		String accountRecordType = "Institution";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			lp.CRMlogout();
			ThreadSleep(5000);

			lp.CRMLogin(crmUser2EmailID, adminPassword);

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

				CommonLib.ThreadSleep(5000);
				List<String> notificationHomeNegativeResult2 = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult2.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

					if (CommonLib.click(driver, home.addNoteButtonOfEventInHomePageNotification(eventTitle, 20),
							"Add Note Button of Event: " + eventTitle, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Add Note Button of Event: " + eventTitle, YesNo.No);

						if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
								task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
							log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle,
									YesNo.No);

							lp.CRMlogout();
							ThreadSleep(5000);
							lp.CRMLogin(crmUser1EmailID, adminPassword);

							log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
									+ contactRecordName + " Record---------", YesNo.No);
							if (lp.clickOnTab(projectName, tabObj2)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

								if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
										contactRecordName, 30)) {
									log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
									ThreadSleep(4000);
									if (BP.clicktabOnPage("Acuity")) {
										log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

										ArrayList<String> interactionResult = BP.verifyRecordOnInteractionCard(null,
												null, eventTitle, task1NotesUpdated, true, false, null, null);
										if (interactionResult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + eventTitle
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + eventTitle
															+ " record is not verified on intraction, Reason: "
															+ interactionResult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + eventTitle
															+ " record is not verified on intraction, Reason: "
															+ interactionResult + "------");
										}

										List<String> notificationNegativeResult = BP
												.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
										if (notificationNegativeResult.isEmpty()) {
											log(LogStatus.INFO,
													"Verified Notification not contains for Event(s): " + eventTitle
															+ " in case of Admin in Detail Page" + contactRecordName,
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "The Event(s) " + eventTitle
													+ " should not contain i case of Admin in Detail Page: "
													+ contactRecordName + " , Reason: " + notificationNegativeResult,
													YesNo.No);
											sa.assertTrue(false, "The Event(s) " + eventTitle
													+ " should not contain i case of Admin in Detail Page: "
													+ contactRecordName + " , Reason: " + notificationNegativeResult);
										}

										if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
											log(LogStatus.INFO,
													"Event Detail Page has not been open for Record: " + eventTitle
															+ " in case of Admin in Detail Page: " + contactRecordName,
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"Event Record Detail Page  has opened for Record: " + eventTitle
															+ " in case of Admin in Detail Page: " + contactRecordName,
													YesNo.Yes);
											sa.assertTrue(false,
													"Event Record Detail Page  has opened for Record: " + eventTitle
															+ " in case of Admin in Detail Page: " + contactRecordName);
											driver.close();
											CommonLib.ThreadSleep(3000);
											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										}

									} else {
										log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Acuity Tab");
									}

								} else {
									log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
									sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
							}

							log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
									+ accountRecordName + " Record---------", YesNo.No);
							if (lp.clickOnTab(projectName, tabObj1)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

								if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
										accountRecordType, accountRecordName, 30)) {
									log(LogStatus.INFO, accountRecordName + " record of record type "
											+ accountRecordType + " has been open", YesNo.No);
									ThreadSleep(4000);
									if (BP.clicktabOnPage("Acuity")) {
										log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

										ArrayList<String> interactionResult = BP.verifyRecordOnInteractionCard(null,
												null, eventTitle, task1NotesUpdated, true, false, null, null);
										if (interactionResult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + eventTitle
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + eventTitle
															+ " record is not verified on intraction, Reason: "
															+ interactionResult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + eventTitle
															+ " record is not verified on intraction, Reason: "
															+ interactionResult + "------");
										}

										List<String> notificationNegativeResult = BP
												.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
										if (notificationNegativeResult.isEmpty()) {
											log(LogStatus.INFO,
													"Verified Notification not contains for Event(s): " + eventTitle
															+ " in case of Admin in Detail Page" + accountRecordName,
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "The Event(s) " + eventTitle
													+ " should not contain in case of Admin in Detail Page: "
													+ accountRecordName + " , Reason: " + notificationNegativeResult,
													YesNo.No);
											sa.assertTrue(false, "The Event(s) " + eventTitle
													+ " should not contain in case of Admin in Detail Page: "
													+ accountRecordName + " , Reason: " + notificationNegativeResult);
										}

										if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
											log(LogStatus.INFO,
													"Event Detail Page has not been open for Record: " + eventTitle
															+ " in case of Admin in Detail Page: " + accountRecordName,
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"Event Record Detail Page  has opened for Record: " + eventTitle
															+ " in case of Admin in Detail Page: " + accountRecordName,
													YesNo.Yes);
											sa.assertTrue(false,
													"Event Record Detail Page  has opened for Record: " + eventTitle
															+ " in case of Admin in Detail Page: " + accountRecordName);
											driver.close();
											CommonLib.ThreadSleep(3000);
											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										}
									} else {
										log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Acuity Tab");
									}

								} else {
									log(LogStatus.ERROR, "Not able to open " + accountRecordName
											+ " record of record type " + accountRecordType, YesNo.No);
									sa.assertTrue(false, "Not able to open " + accountRecordName
											+ " record of record type " + accountRecordType);
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
							}

						} else {
							log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle,
									YesNo.No);
							sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Add Noted Button of Event: " + eventTitle, YesNo.No);
						sa.assertTrue(false, "Not Able to Click on Add Noted Button of Event: " + eventTitle);
					}

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc011_LoginAsUser1CreateTheEventByChangingTheAssignedToUserAndVerifyTheNotificationForTheDifferentUsersWhichAreNeitherAnAttendeeNorAsignedAsUser(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Opportunity 1";
		String eventAttendees = "Dealroom1.3+James@gmail.com";
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String contactRecordName = "James";
		String accountRecordName = "Vertica";
		String accountRecordType = "Company";
		String updatedRelatedToInNotes = "Maxjonic<break>Sumo Logic<break>Vertica<break>Demo Deal";
		String[][] task1UpdateBasicSection = { { "Related_To", updatedRelatedToInNotes } };
		String[][] task1UpdateAdvancedSection = { { "Assigned To ID", crmUser2FirstName + " " + crmUser2LastName } };
		String[] updatedSuggestedTags = null;

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

				CommonLib.ThreadSleep(5000);
				List<String> notificationHomeNegativeResult2 = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult2.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

					log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
							+ " Record---------", YesNo.No);
					if (lp.clickOnTab(projectName, tabObj2)) {

						log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

						if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName,
								30)) {
							log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
							ThreadSleep(4000);
							if (BP.clicktabOnPage("Acuity")) {
								log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

								if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
									log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle,
											YesNo.No);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS,
												"Activity timeline record has been Updated for: " + eventTitle,
												YesNo.No);

										log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
												+ accountRecordName + " Record---------", YesNo.No);
										if (lp.clickOnTab(projectName, tabObj1)) {

											log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

											if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
													TabName.InstituitonsTab, accountRecordType, accountRecordName,
													30)) {
												log(LogStatus.INFO, accountRecordName + " record of record type "
														+ accountRecordType + " has been open", YesNo.No);
												ThreadSleep(4000);
												if (BP.clicktabOnPage("Acuity")) {
													log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

													List<String> notificationNegativeResult = BP
															.verifyNotificationOptionsNotContainsInRecordDetailPage(
																	eventTitle);
													if (notificationNegativeResult.isEmpty()) {
														log(LogStatus.INFO,
																"Verified Notification not contains for Event(s): "
																		+ eventTitle
																		+ " in case of Admin in Detail Page"
																		+ contactRecordName,
																YesNo.No);

													} else {
														log(LogStatus.ERROR, "The Event(s) " + eventTitle
																+ " should not contain i case of Admin in Detail Page: "
																+ contactRecordName + " , Reason: "
																+ notificationNegativeResult, YesNo.No);
														sa.assertTrue(false, "The Event(s) " + eventTitle
																+ " should not contain i case of Admin in Detail Page: "
																+ contactRecordName + " , Reason: "
																+ notificationNegativeResult);
													}

													if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
															eventTitle)) {
														log(LogStatus.INFO,
																"Event Detail Page has been open for Record: "
																		+ eventTitle,
																YesNo.No);

													} else {
														log(LogStatus.ERROR,
																"Event Detail Page has not been open for Record: "
																		+ eventTitle + " in case of User 1 i.e.: "
																		+ crmUser1EmailID + " in Detail Page: "
																		+ contactRecordName,
																YesNo.Yes);
														sa.assertTrue(false,
																"Event Detail Page has not been open for Record: "
																		+ eventTitle + " in case of User 1 i.e.: "
																		+ crmUser1EmailID + " in Detail Page: "
																		+ contactRecordName);

													}
												} else {
													log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
													sa.assertTrue(false, "Not able to click on Acuity Tab");
												}

											} else {
												log(LogStatus.ERROR,
														"Not able to open " + accountRecordName
																+ " record of record type " + accountRecordType,
														YesNo.No);
												sa.assertTrue(false, "Not able to open " + accountRecordName
														+ " record of record type " + accountRecordType);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
											sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
										}

										log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
												+ contactRecordName + " Record---------", YesNo.No);
										if (lp.clickOnTab(projectName, tabObj2)) {

											log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

											if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
													contactRecordName, 30)) {
												log(LogStatus.INFO, contactRecordName + " record has been open",
														YesNo.No);
												ThreadSleep(4000);
												if (BP.clicktabOnPage("Acuity")) {
													log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

													List<String> notificationNegativeResult = BP
															.verifyNotificationOptionsNotContainsInRecordDetailPage(
																	eventTitle);
													if (notificationNegativeResult.isEmpty()) {
														log(LogStatus.INFO,
																"Verified Notification not contains for Event(s): "
																		+ eventTitle + " in case of User 1 i.e.: "
																		+ crmUser1EmailID + " in Detail Page"
																		+ contactRecordName,
																YesNo.No);

													} else {
														log(LogStatus.ERROR,
																"The Event(s) " + eventTitle
																		+ " should not contain i case of User 1 i.e.: "
																		+ crmUser1EmailID + " in Detail Page: "
																		+ contactRecordName + " , Reason: "
																		+ notificationNegativeResult,
																YesNo.No);
														sa.assertTrue(false,
																"The Event(s) " + eventTitle
																		+ " should not contain i case of User 1 i.e.: "
																		+ crmUser1EmailID + " in Detail Page: "
																		+ contactRecordName + " , Reason: "
																		+ notificationNegativeResult);
													}

													if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
															eventTitle)) {
														log(LogStatus.INFO,
																"Event Detail Page has been open for Record: "
																		+ eventTitle,
																YesNo.No);

													} else {
														log(LogStatus.ERROR,
																"Event Detail Page has not been open for Record: "
																		+ eventTitle + " in case of User 1 i.e.: "
																		+ crmUser1EmailID + " in Detail Page: "
																		+ contactRecordName,
																YesNo.Yes);
														sa.assertTrue(false,
																"Event Detail Page has not been open for Record: "
																		+ eventTitle + " in case of User 1 i.e.: "
																		+ crmUser1EmailID + " in Detail Page: "
																		+ contactRecordName);

													}

												} else {
													log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
													sa.assertTrue(false, "Not able to click on Acuity Tab");
												}

											} else {
												log(LogStatus.ERROR,
														"Not able to open " + contactRecordName + " record", YesNo.No);
												sa.assertTrue(false,
														"Not able to open " + contactRecordName + " record");
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
											sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
										}

										if (lp.clickOnTab(projectName, TabName.HomeTab)) {
											log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
											List<String> notificationHomeNegativeResult = home
													.verifyNotificationOptionsNotContains(eventTitle);
											if (notificationHomeNegativeResult.isEmpty()) {
												log(LogStatus.INFO,
														"Verified Notification for Event(s): " + eventTitle
																+ " on Home Page not contains in case of user 1 i.e.: "
																+ crmUser1EmailID,
														YesNo.No);

											} else {
												log(LogStatus.ERROR, "The Event(s) " + eventTitle
														+ "+ on Home Page should not contain in case of User 1 i.e.: "
														+ crmUser1EmailID + ", Reason: "
														+ notificationHomeNegativeResult, YesNo.No);
												sa.assertTrue(false, "The Event(s) " + eventTitle
														+ "+ on Home Page should not contain in case of User 1 i.e.: "
														+ crmUser1EmailID + ", Reason: "
														+ notificationHomeNegativeResult);
											}
										} else {
											sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
											log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab,
													YesNo.Yes);
										}

										lp.CRMlogout();
										ThreadSleep(5000);
										lp.CRMLogin(superAdminUserName, adminPassword, appName);

										List<String> notificationHomeNegativeResult = home
												.verifyNotificationOptionsNotContains(eventTitle);
										if (notificationHomeNegativeResult.isEmpty()) {
											log(LogStatus.INFO,
													"Verified Event(s): " + eventTitle
															+ " not contains in Notification pane in Home Page",
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "Not Verified Event(s): " + eventTitle
													+ " not contains in Notification pane in Home Page, Reason: "
													+ notificationHomeNegativeResult, YesNo.No);
											sa.assertTrue(false, "Not Verified Event(s): " + eventTitle
													+ " not contains in Notification pane in Home Page, Reason: "
													+ notificationHomeNegativeResult);
										}

									} else {
										log(LogStatus.FAIL,
												"Activity timeline record has not Updated for: " + eventTitle,
												YesNo.No);
										sa.assertTrue(false,
												"Activity timeline record has not Updated for: " + eventTitle);
									}
								} else {
									log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
											YesNo.Yes);
									sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);

								}

							} else {
								log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
								sa.assertTrue(false, "Not able to click on Acuity Tab");
							}

						} else {
							log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
							sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
						sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
					}

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc012_CreateTheEventWithStartDateInPastEndDateInFutureAndVerifyTheNotificationAndReminderForTheAttendeeUser(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Deal Closing";
		String eventAttendees = "Dealroom1.3+Max@gmail.com<Break>Dealroom1.3+Martha@gmail.com" + "<Break>"
				+ crmUser2EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 1);

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String contactRecordName = "Martha";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

				CommonLib.ThreadSleep(5000);
				List<String> notificationHomeNegativeResult2 = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult2.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

					log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
							+ " Record---------", YesNo.No);
					if (lp.clickOnTab(projectName, tabObj2)) {

						log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

						if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName,
								30)) {
							log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
							ThreadSleep(4000);
							if (BP.clicktabOnPage("Acuity")) {
								log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

								List<String> notificationNegativeResult = BP
										.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
								if (notificationNegativeResult.isEmpty()) {
									log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitle
											+ " in case of Admin in Detail Page" + contactRecordName, YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"The Event(s) " + eventTitle
													+ " should not contain i case of Admin in Detail Page: "
													+ contactRecordName + " , Reason: " + notificationNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"The Event(s) " + eventTitle
													+ " should not contain i case of Admin in Detail Page: "
													+ contactRecordName + " , Reason: " + notificationNegativeResult);
								}

								if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
									log(LogStatus.INFO,
											"Event Detail Page has been open for Record: " + eventTitle
													+ " in case of User 1 i.e.: " + crmUser1EmailID
													+ " in Detail Page: " + contactRecordName,
											YesNo.No);
									driver.close();
									CommonLib.ThreadSleep(3000);
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								} else {
									log(LogStatus.ERROR,
											"Event Detail Page has not been open for Record: " + eventTitle
													+ " in case of User 1 i.e.: " + crmUser1EmailID
													+ " in Detail Page: " + contactRecordName,
											YesNo.Yes);
									sa.assertTrue(false,
											"Event Detail Page has not been open for Record: " + eventTitle
													+ " in case of User 1 i.e.: " + crmUser1EmailID
													+ " in Detail Page: " + contactRecordName);

								}

								CommonLib.refresh(driver);
								ArrayList<String> result = BP.verifyRecordOnInteractionCard(null, null, eventTitle,
										null, false, true, null, null);
								if (result.isEmpty()) {
									log(LogStatus.PASS,
											"------" + eventTitle + " record has been verified on intraction------",
											YesNo.No);

								} else {
									log(LogStatus.ERROR, "------" + eventTitle
											+ " record is not verified on intraction, Reason: " + result + "------",
											YesNo.No);
									sa.assertTrue(false, "------" + eventTitle
											+ " record is not verified on intraction, Reason: " + result + "------");
								}

								lp.CRMlogout();
								ThreadSleep(5000);
								lp.CRMLogin(crmUser2EmailID, adminPassword);

								List<String> notificationHomeNegativeResult = home
										.verifyNotificationOptionsNotContains(eventTitle);
								if (notificationHomeNegativeResult.isEmpty()) {
									log(LogStatus.INFO, "Verified Event(s): " + eventTitle
											+ " not contains in Notification pane in Home Page", YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Not Verified Event(s): " + eventTitle
													+ " not contains in Notification pane in Home Page, Reason: "
													+ notificationHomeNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"Not Verified Event(s): " + eventTitle
													+ " not contains in Notification pane in Home Page, Reason: "
													+ notificationHomeNegativeResult);
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
								sa.assertTrue(false, "Not able to click on Acuity Tab");
							}

						} else {
							log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
							sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
						sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
					}

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc015_VerifyTheNotificationWhenOnlyRelatedAssociationTagged(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Deal Closing";
		String eventAttendees = "Dealroom1.3+Max@gmail.com<Break>Dealroom1.3+Martha@gmail.com" + "<Break>"
				+ crmUser2EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 1);

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String contactRecordName = "Martha";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

				CommonLib.ThreadSleep(5000);
				List<String> notificationHomeNegativeResult2 = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult2.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

					log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
							+ " Record---------", YesNo.No);
					if (lp.clickOnTab(projectName, tabObj2)) {

						log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

						if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName,
								30)) {
							log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
							ThreadSleep(4000);
							if (BP.clicktabOnPage("Acuity")) {
								log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

								List<String> notificationNegativeResult = BP
										.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
								if (notificationNegativeResult.isEmpty()) {
									log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitle
											+ " in case of Admin in Detail Page" + contactRecordName, YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"The Event(s) " + eventTitle
													+ " should not contain i case of Admin in Detail Page: "
													+ contactRecordName + " , Reason: " + notificationNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"The Event(s) " + eventTitle
													+ " should not contain i case of Admin in Detail Page: "
													+ contactRecordName + " , Reason: " + notificationNegativeResult);
								}

								if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
									log(LogStatus.INFO,
											"Event Detail Page has been open for Record: " + eventTitle
													+ " in case of User 1 i.e.: " + crmUser1EmailID
													+ " in Detail Page: " + contactRecordName,
											YesNo.No);
									driver.close();
									CommonLib.ThreadSleep(3000);
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								} else {
									log(LogStatus.ERROR,
											"Event Detail Page has not been open for Record: " + eventTitle
													+ " in case of User 1 i.e.: " + crmUser1EmailID
													+ " in Detail Page: " + contactRecordName,
											YesNo.Yes);
									sa.assertTrue(false,
											"Event Detail Page has not been open for Record: " + eventTitle
													+ " in case of User 1 i.e.: " + crmUser1EmailID
													+ " in Detail Page: " + contactRecordName);

								}

								CommonLib.refresh(driver);
								ArrayList<String> result = BP.verifyRecordOnInteractionCard(null, null, eventTitle,
										null, false, true, null, null);
								if (result.isEmpty()) {
									log(LogStatus.PASS,
											"------" + eventTitle + " record has been verified on intraction------",
											YesNo.No);

								} else {
									log(LogStatus.ERROR, "------" + eventTitle
											+ " record is not verified on intraction, Reason: " + result + "------",
											YesNo.No);
									sa.assertTrue(false, "------" + eventTitle
											+ " record is not verified on intraction, Reason: " + result + "------");
								}

								lp.CRMlogout();
								ThreadSleep(5000);
								lp.CRMLogin(crmUser2EmailID, adminPassword);

								List<String> notificationHomeNegativeResult = home
										.verifyNotificationOptionsNotContains(eventTitle);
								if (notificationHomeNegativeResult.isEmpty()) {
									log(LogStatus.INFO, "Verified Event(s): " + eventTitle
											+ " not contains in Notification pane in Home Page", YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Not Verified Event(s): " + eventTitle
													+ " not contains in Notification pane in Home Page, Reason: "
													+ notificationHomeNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"Not Verified Event(s): " + eventTitle
													+ " not contains in Notification pane in Home Page, Reason: "
													+ notificationHomeNegativeResult);
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
								sa.assertTrue(false, "Not able to click on Acuity Tab");
							}

						} else {
							log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
							sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
						sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
					}

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc017_VerifyTheNotificationWhenEventIsCreatedWithNeitherContactNorRelatedAssociationTagged(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Announcing RampUp 2022 speakers and more +1";
		String eventAttendees = crmUser4EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String contactRecordName = "Litz";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);
			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO,
							"Verified Notification for Event(s): " + eventTitle
									+ " on Home Page not contains in case of User 1 i.e.: " + crmUser1EmailID,
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 1 i.e.: "
									+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult,
							YesNo.No);
					sa.assertTrue(false,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 1 i.e.: "
									+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"Verified Notification not contains for Event(s): " + eventTitle
											+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page"
											+ contactRecordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"The Event(s) " + eventTitle + " should not contain in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"The Event(s) " + eventTitle + " should not contain in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			lp.CRMlogout();
			ThreadSleep(5000);

			lp.CRMLogin(crmUser4EmailID, adminPassword);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO,
							"Verified Notification for Event(s): " + eventTitle
									+ " on Home Page not contains in case of User 4 i.e.: " + crmUser4EmailID,
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 4 i.e.: "
									+ crmUser4EmailID + ", Reason: " + notificationHomeNegativeResult,
							YesNo.No);
					sa.assertTrue(false,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 4 i.e.: "
									+ crmUser4EmailID + ", Reason: " + notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityMNNRTc018_CreateTheEventWithAttendeesTaggedRemoveOneOfTheAttendeeAndVerifyTheNotificationBeforeAndAfterRemovingTheUserAsAttendee(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "This event will have your Business circles talking +3";
		String eventAttendees = "Dealroom1.3+Litz@gmail.com" + "<Break>" + crmUser2EmailID + "<Break>"
				+ crmUser3EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);

		String navigateStartDateForEdit = CommonLib.convertDateFromOneFormatToAnother(startDate, "dd/MM/yyyy",
				"m/d/yyyy");
		String navigateStartDateMonthYearForEdit = CommonLib.convertDateFromOneFormatToAnother(startDate, "m/d/yyyy",
				"mmmm yyyy");

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String contactRecordName = "Litz";

		String eventTitleUpdated = null;
		String eventAttendeesUpdated = null;
		String startDateUpdated = null;
		String endDateUpdated = null;
		String startTimeUpdated = null;
		String endTimeUpdated = null;
		String descriptionBoxUpdated = null;
		String eventAttendeesToRemove = crmUser2FirstName + " " + crmUser2LastName;

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);
			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO,
							"Verified Notification for Event(s): " + eventTitle + " on Home Page in case of User 1",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
							+ "+ on Home Page in case of User 1, Reason: " + notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
							+ "+ on Home Page in case of User 1, Reason: " + notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"Verified Notification contains for Event(s): " + eventTitle
											+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page"
											+ contactRecordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult);
						}

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);
							driver.close();
							CommonLib.ThreadSleep(3000);
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						} else {
							log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
									YesNo.Yes);
							sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);

						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			lp.CRMlogout();
			ThreadSleep(5000);

			lp.CRMLogin(crmUser2EmailID, adminPassword);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO,
							"Verified Notification for Event(s): " + eventTitle
									+ " on Home Page not contains in case of User 1 i.e.: " + crmUser1EmailID,
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 1 i.e.: "
									+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult,
							YesNo.No);
					sa.assertTrue(false,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 1 i.e.: "
									+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"Verified Notification contains for Event(s): " + eventTitle
											+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page"
											+ contactRecordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult);
						}

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);
							driver.close();
							CommonLib.ThreadSleep(3000);
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						} else {
							log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
									YesNo.Yes);
							sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);

						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			if (op.loginNavigateAndUpdateTheEvent(rgOutLookUser1Email, rgOutLookUser1Password, navigateStartDateForEdit,
					navigateStartDateMonthYearForEdit, eventTitle, false, eventTitleUpdated, eventAttendeesUpdated,
					startDateUpdated, endDateUpdated, startTimeUpdated, endTimeUpdated, descriptionBoxUpdated, false,
					eventAttendeesToRemove)) {
				log(LogStatus.INFO, "-----Event Updated Msg is showing, So Event of Title: " + eventTitle
						+ " has been Updated-----", YesNo.No);
				CommonLib.refresh(driver);
				if (lp.clickOnTab(projectName, TabName.HomeTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
					List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitle);
					if (notificationHomeNegativeResult.isEmpty()) {
						log(LogStatus.INFO,
								"Verified Notification for Event(s): " + eventTitle
										+ " on Home Page not contains in case of User 2 i.e.: " + crmUser2EmailID,
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitle
										+ "+ on Home Page should not contain in case of User 2 i.e.: " + crmUser2EmailID
										+ ", Reason: " + notificationHomeNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitle
										+ "+ on Home Page should not contain in case of User 2 i.e.: " + crmUser2EmailID
										+ ", Reason: " + notificationHomeNegativeResult);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
				}

				log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
						+ " Record not contains in case of User 2---------", YesNo.No);
				if (lp.clickOnTab(projectName, tabObj2)) {

					log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

					if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName,
							30)) {
						log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
						ThreadSleep(4000);
						if (BP.clicktabOnPage("Acuity")) {
							log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

							List<String> notificationNegativeResult = BP
									.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
							if (notificationNegativeResult.isEmpty()) {
								log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitle
										+ " in case of User 2 in Detail Page" + contactRecordName, YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"The Event(s) " + eventTitle
												+ " should not contain in case of User 2 in Detail Page: "
												+ contactRecordName + " , Reason: " + notificationNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"The Event(s) " + eventTitle
												+ " should not contain in case of User 2 in Detail Page: "
												+ contactRecordName + " , Reason: " + notificationNegativeResult);
							}

							if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
								log(LogStatus.INFO, "Event Detail Page has not been open for Record: " + eventTitle
										+ " in case of User 2 in Detail Page: " + contactRecordName, YesNo.No);

							} else {
								log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitle
										+ " in case of User 2 in Detail Page: " + contactRecordName, YesNo.Yes);
								sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitle
										+ " in case of User 2 in Detail Page: " + contactRecordName);
								driver.close();
								CommonLib.ThreadSleep(3000);
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
							sa.assertTrue(false, "Not able to click on Acuity Tab");
						}

					} else {
						log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
						sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
					sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
				}

			}

			else {
				log(LogStatus.ERROR, "-----Event Updated Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been Updated-----", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Event Updated Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been Updated-----");
			}

			lp.CRMlogout();
			ThreadSleep(5000);

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle
							+ " not contans in Home Page Notification Section in case of user 1 i.e.: "
							+ crmUser1EmailID + "---------",
					YesNo.No);
			lp.CRMLogin(crmUser1EmailID, adminPassword);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO,
							"Verified Notification for Event(s): " + eventTitle
									+ " on Home Page not contains in case of User 1 i.e.: " + crmUser1EmailID,
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 1 i.e.: "
									+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult,
							YesNo.No);
					sa.assertTrue(false,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 1 i.e.: "
									+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc019_CreateThePastDatedEventVerifyTheNotificationDeleteTheEventAndVerify(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String eventTitle = "Join all major influencers";
		String eventAttendees = "Dealroom1.3+Lomez@gmail.com<Break>" + crmUser1EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";
		String recordName = "Lomez";

		String updatedRelatedTo = "Sumo Logic";
		String[][] event1UpdateBasicSectionOnHomepageNotePopup = { { "Related_To", updatedRelatedTo } };
		String[][] event1UpdateAdvancedSectionOnHomepageNotePopup = null;
		String[] eventupdatedSuggestedTagsOnHomepageNotePopup = null;

		String accountRecordName = "Sumo Logic";
		String accountRecordType = "Company";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
				log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);

				if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button of Event: " + eventTitle, YesNo.No);

					if (BP.updateActivityTimelineRecord(projectName, event1UpdateBasicSectionOnHomepageNotePopup,
							event1UpdateAdvancedSectionOnHomepageNotePopup, null,
							eventupdatedSuggestedTagsOnHomepageNotePopup, null)) {
						log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle, YesNo.No);

						log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
								+ accountRecordName + " Record---------", YesNo.No);
						if (lp.clickOnTab(projectName, tabObj1)) {

							log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

							if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
									accountRecordType, accountRecordName, 30)) {
								log(LogStatus.INFO, accountRecordName + " record of record type " + accountRecordType
										+ " has been open", YesNo.No);
								ThreadSleep(4000);
								if (BP.clicktabOnPage("Acuity")) {
									log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

									List<String> notificationNegativeResult = BP
											.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
									if (notificationNegativeResult.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle,
												YesNo.No);

									} else {
										log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + ", Reason: "
												+ notificationNegativeResult, YesNo.No);
										sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + ", Reason: "
												+ notificationNegativeResult);
									}

									if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
										log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle,
												YesNo.No);
										driver.close();
										CommonLib.ThreadSleep(3000);
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
									} else {
										log(LogStatus.ERROR,
												"Record Detail Page has not open for Record: " + eventTitle, YesNo.Yes);
										sa.assertTrue(false,
												"Record Detail Page has not open for Record: " + eventTitle);

									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
									sa.assertTrue(false, "Not able to click on Acuity Tab");
								}

							} else {
								log(LogStatus.ERROR, "Not able to open " + accountRecordName + " record of record type "
										+ accountRecordType, YesNo.No);
								sa.assertTrue(false, "Not able to open " + accountRecordName + " record of record type "
										+ accountRecordType);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
							sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
						}

						CommonLib.refresh(driver);
						log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + recordName
								+ " Record---------", YesNo.No);
						if (lp.clickOnTab(projectName, tabObj2)) {

							log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

							if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName,
									30)) {
								log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
								ThreadSleep(4000);
								if (BP.clicktabOnPage("Acuity")) {
									log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

									List<String> notificationNegativeResult = BP
											.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
									if (notificationNegativeResult.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle,
												YesNo.No);

									} else {
										log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + ", Reason: "
												+ notificationNegativeResult, YesNo.No);
										sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + ", Reason: "
												+ notificationNegativeResult);
									}

									if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
										log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle,
												YesNo.No);

										if (click(driver, taskBP.deleteButtonInEventDetailPage(15),
												"Event Delete Button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Delete Button", YesNo.No);
											if (click(driver, taskBP.taskDeleteConfirmButton(15),
													"Delete Confirm Button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "Clicked on Delete Confirm Button", YesNo.No);

												if (taskBP.taskDeletedMsg(15) != null) {
													log(LogStatus.INFO,
															"Event Delete Msg displayed, So Event has been deleted",
															YesNo.No);
													driver.close();
													driver.switchTo().window(
															driver.getWindowHandles().stream().findFirst().get());
													CommonLib.refresh(driver);
													CommonLib.ThreadSleep(5000);

													log(LogStatus.INFO,
															"---------Now Going to Verify Event: " + eventTitle + " in "
																	+ accountRecordName + " Record---------",
															YesNo.No);
													if (lp.clickOnTab(projectName, tabObj1)) {

														log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

														if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
																TabName.InstituitonsTab, accountRecordType,
																accountRecordName, 30)) {
															log(LogStatus.INFO,
																	accountRecordName + " record of record type "
																			+ accountRecordType + " has been open",
																	YesNo.No);
															ThreadSleep(4000);
															if (BP.clicktabOnPage("Acuity")) {
																log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

																List<String> notificationNegativeResultAfterDeleteInFirm = BP
																		.verifyNotificationOptionsNotContainsInRecordDetailPage(
																				eventTitle);
																if (notificationNegativeResultAfterDeleteInFirm
																		.isEmpty()) {
																	log(LogStatus.INFO,
																			"Verified Notification not contains for Event(s): "
																					+ eventTitle
																					+ " in case of User 1 in Detail Page"
																					+ accountRecordName
																					+ " after delte the event",
																			YesNo.No);

																} else {
																	log(LogStatus.ERROR, "The Event(s) " + eventTitle
																			+ " should not contain in case of User 1 in Detail Page: "
																			+ accountRecordName
																			+ " after delete the event  , Reason: "
																			+ notificationNegativeResultAfterDeleteInFirm,
																			YesNo.No);
																	sa.assertTrue(false, "The Event(s) " + eventTitle
																			+ " should not contain in case of User 1 in Detail Page: "
																			+ accountRecordName
																			+ " after delete the event  , Reason: "
																			+ notificationNegativeResultAfterDeleteInFirm);
																}

																if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
																		eventTitle)) {
																	log(LogStatus.INFO,
																			"Task Detail Page has not been open for Record: "
																					+ eventTitle
																					+ " in case of User 1 in Detail Page: "
																					+ accountRecordName
																					+ " after delete the event",
																			YesNo.No);

																} else {
																	log(LogStatus.ERROR,
																			"Event Record Detail Page  has opened for Record: "
																					+ eventTitle
																					+ " in case of User 1 in Detail Page: "
																					+ accountRecordName
																					+ " after delete the event",
																			YesNo.Yes);
																	sa.assertTrue(false,
																			"Event Record Detail Page  has opened for Record: "
																					+ eventTitle
																					+ " in case of User 1 in Detail Page: "
																					+ accountRecordName
																					+ " after delete the event");
																	driver.close();
																	CommonLib.ThreadSleep(3000);
																	driver.switchTo().window(driver.getWindowHandles()
																			.stream().findFirst().get());
																}
															} else {
																log(LogStatus.ERROR, "Not able to click on Acuity Tab",
																		YesNo.No);
																sa.assertTrue(false, "Not able to click on Acuity Tab");
															}

														} else {
															log(LogStatus.ERROR, "Not able to open " + accountRecordName
																	+ " record of record type " + accountRecordType,
																	YesNo.No);
															sa.assertTrue(false, "Not able to open " + accountRecordName
																	+ " record of record type " + accountRecordType);
														}
													} else {
														log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1,
																YesNo.No);
														sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
													}

													List<String> notificationNegativeResultAfterDelete = BP
															.verifyNotificationOptionsNotContainsInRecordDetailPage(
																	eventTitle);
													if (notificationNegativeResultAfterDelete.isEmpty()) {
														log(LogStatus.INFO,
																"Verified Notification not contains for Event(s): "
																		+ eventTitle
																		+ " in case of User 1 in Detail Page"
																		+ recordName,
																YesNo.No);

													} else {
														log(LogStatus.ERROR, "The Event(s) " + eventTitle
																+ " should not contain in case of User 1 in Detail Page: "
																+ recordName + " , Reason: "
																+ notificationNegativeResultAfterDelete, YesNo.No);
														sa.assertTrue(false, "The Event(s) " + eventTitle
																+ " should not contain in case of User 1 in Detail Page: "
																+ recordName + " , Reason: "
																+ notificationNegativeResultAfterDelete);
													}

													if (lp.clickOnTab(projectName, TabName.HomeTab)) {
														log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab,
																YesNo.No);
														List<String> notificationHomeNegativeResult = home
																.verifyNotificationOptionsNotContains(eventTitle);
														if (notificationHomeNegativeResult.isEmpty()) {
															log(LogStatus.INFO, "Verified Notification for Event(s): "
																	+ eventTitle
																	+ " on Home Page not contains in case of User 1, i.e.: "
																	+ crmUser1EmailID, YesNo.No);

														} else {
															log(LogStatus.ERROR, "The Event(s) " + eventTitle
																	+ "+ on Home Page should not contain in case of User 1, i.e.: "
																	+ crmUser1EmailID + " ,Reason: "
																	+ notificationHomeNegativeResult, YesNo.No);
															sa.assertTrue(false, "The Event(s) " + eventTitle
																	+ "+ on Home Page should not contain in case of User 1, i.e.: "
																	+ crmUser1EmailID + " ,Reason: "
																	+ notificationHomeNegativeResult);
														}
													} else {
														sa.assertTrue(false,
																"Not Able to Click on Tab : " + TabName.HomeTab);
														log(LogStatus.SKIP,
																"Not Able to Click on Tab : " + TabName.HomeTab,
																YesNo.Yes);
													}

												} else {
													log(LogStatus.ERROR,
															"Event Delete Msg not display, So Event not gets deleted "
																	+ eventTitle,
															YesNo.Yes);
													sa.assertTrue(false,
															"Event Delete Msg not display, So Event not gets deleted "
																	+ eventTitle);
													driver.close();
													driver.switchTo().window(
															driver.getWindowHandles().stream().findFirst().get());
												}

											} else {
												log(LogStatus.ERROR,
														"Not ABle to Click on Delete Confirm Button in case of Event: "
																+ eventTitle,
														YesNo.Yes);
												sa.assertTrue(false,
														"Not ABle to Click on Delete Confirm Button in case of Event: "
																+ eventTitle);
												driver.close();
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											}

										} else {
											log(LogStatus.ERROR, "Not Able Click on Delete button", YesNo.Yes);
											sa.assertTrue(false, "Not Able Click on Delete button");
											driver.close();
											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										}

									} else {
										log(LogStatus.ERROR,
												"Record Detail Page has not open for Record: " + eventTitle, YesNo.Yes);
										sa.assertTrue(false,
												"Record Detail Page has not open for Record: " + eventTitle);

									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
									sa.assertTrue(false, "Not able to click on Acuity Tab");
								}

							} else {
								log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
								sa.assertTrue(false, "Not able to open " + recordName + " record");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
							sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
						}

					} else {
						log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle, YesNo.No);
						sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);
					}

				} else {
					log(LogStatus.ERROR, "Not Able Click on Edit button for Record Page of Event: " + eventTitle,
							YesNo.Yes);
					sa.assertTrue(false, "Not Able Click on Edit button for Record Page of Event: " + eventTitle);
					driver.close();
					driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
				}
			} else {
				log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle, YesNo.Yes);
				sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);

			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc020_RestoreTheDeletedEventAndVerifyTheNotificationForUser1(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Join all major influencers";

		String recordName = "Lomez";

		String[][] listViewSheetData = { { "user 1", "Recycle Bin", eventTitle, "All users can see this list view",
				"My recycle bin", "Record Name", "equals", eventTitle, "TextBox" } };
		String accountRecordName = "Sumo Logic";
		String accountRecordType = "Company";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		WebElement ele;
		String recycleTab = lp.getTabName(projectName, TabName.RecycleBinTab);
		if (lp.openAppFromAppLauchner(60, recycleTab)) {

			CommonLib.refresh(driver);

			for (String[] row : listViewSheetData) {

				if (lp.addListView(projectName, row, 10)) {
					log(LogStatus.INFO, "list view added on " + row[1], YesNo.No);
					ele = lp.getCheckboxOfRestoreItemOnRecycleBin(projectName, eventTitle, 30);
					if (clickUsingJavaScript(driver, ele, "Check box against : " + eventTitle, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on checkbox for " + eventTitle, YesNo.No);
						;
						ele = lp.getRestoreButtonOnRecycleBin(projectName, 30);
						if (clickUsingJavaScript(driver, ele, "Restore Button : " + eventTitle, action.BOOLEAN)) {
							ThreadSleep(10000);
							log(LogStatus.INFO, "Click on Restore Button for " + eventTitle, YesNo.No);
							sa.assertTrue(true, "Event has been restore from the Recycle bin");

							CommonLib.switchToDefaultContent(driver);
							ThreadSleep(2000);

							log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
									+ accountRecordName + " Record---------", YesNo.No);
							if (lp.clickOnTab(projectName, tabObj1)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

								if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
										accountRecordType, accountRecordName, 30)) {
									log(LogStatus.INFO, accountRecordName + " record of record type "
											+ accountRecordType + " has been open", YesNo.No);
									ThreadSleep(4000);
									if (BP.clicktabOnPage("Acuity")) {
										log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

										List<String> notificationNegativeResult = BP
												.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
										if (notificationNegativeResult.isEmpty()) {
											log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle,
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
													+ ", Reason: " + notificationNegativeResult, YesNo.No);
											sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
													+ ", Reason: " + notificationNegativeResult);
										}

										if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
											log(LogStatus.INFO,
													"Event Detail Page has been open for Record: " + eventTitle,
													YesNo.No);
											driver.close();
											CommonLib.ThreadSleep(3000);
											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										} else {
											log(LogStatus.ERROR,
													"Record Detail Page has not open for Record: " + eventTitle,
													YesNo.Yes);
											sa.assertTrue(false,
													"Record Detail Page has not open for Record: " + eventTitle);

										}
									} else {
										log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Acuity Tab");
									}

								} else {
									log(LogStatus.ERROR, "Not able to open " + accountRecordName
											+ " record of record type " + accountRecordType, YesNo.No);
									sa.assertTrue(false, "Not able to open " + accountRecordName
											+ " record of record type " + accountRecordType);
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
							}

							log(LogStatus.INFO, "---------Now Going to Verify Notification of Event: " + eventTitle
									+ " in Contact Record Page---------", YesNo.No);

							if (lp.clickOnTab(projectName, tabObj2)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

								if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName,
										30)) {
									log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
									ThreadSleep(4000);
									if (BP.clicktabOnPage("Acuity")) {
										log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

										List<String> notificationNegativeResult = BP
												.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
										if (notificationNegativeResult.isEmpty()) {
											log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle,
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
													+ ", Reason: " + notificationNegativeResult, YesNo.No);
											sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
													+ ", Reason: " + notificationNegativeResult);
										}

									} else {
										log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Acuity Tab");
									}

								} else {
									log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
									sa.assertTrue(false, "Not able to open " + recordName + " record");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
							}

							if (lp.clickOnTab(projectName, TabName.HomeTab)) {
								log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
								List<String> notificationHomeNegativeResult = home
										.verifyNotificationOptions(eventTitle);
								if (notificationHomeNegativeResult.isEmpty()) {
									log(LogStatus.INFO,
											"Verified Notification for Event(s): " + eventTitle + " on Home Page",
											YesNo.No);

								} else {
									log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
											+ "+ on Home Page, Reason: " + notificationHomeNegativeResult, YesNo.No);
									sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
											+ "+ on Home Page, Reason: " + notificationHomeNegativeResult);
								}
							} else {
								sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
								log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
							}

						} else {

							log(LogStatus.ERROR, "Not Able to Click on Restore Button for " + eventTitle, YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Restore Button for " + eventTitle);
						}

					} else {

						log(LogStatus.ERROR, "Not Able to Click on checkbox for " + eventTitle, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on checkbox for " + eventTitle);
					}
				}

				else {
					log(LogStatus.FAIL, "list view could not added on " + row[1], YesNo.Yes);
					sa.assertTrue(false, "list view could not added on " + row[1]);
				}
			}

		} else {
			log(LogStatus.ERROR, "Not Able to open the Recycle been tab", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the Recycle been tab");

		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityMNNRTc021_UpdateTheSubjectOfTheEventAndVerifyTheSameAtNotificationPopUpAsWellAsInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "This event will have your Business circles talking +3";

		String contactRecordName = "Litz";

		String eventTitleUpdated = "Updated This event will have your Business circles talking +3";
		String[][] event1UpdateBasicSectionOnHomepageNotePopup = { { "Subject", eventTitleUpdated } };
		String[][] event1UpdateAdvancedSectionOnHomepageNotePopup = null;
		String[] eventupdatedSuggestedTagsOnHomepageNotePopup = null;

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName + " Record---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
						log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);

						if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Edit Button of Event: " + eventTitle, YesNo.No);

							if (BP.updateActivityTimelineRecord(projectName,
									event1UpdateBasicSectionOnHomepageNotePopup,
									event1UpdateAdvancedSectionOnHomepageNotePopup, null,
									eventupdatedSuggestedTagsOnHomepageNotePopup, null)) {
								log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle,
										YesNo.No);

								CommonLib.refresh(driver);
								log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in "
										+ contactRecordName + " Record---------", YesNo.No);

								List<String> notificationNegativeResultForOldName = BP
										.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
								if (notificationNegativeResultForOldName.isEmpty()) {
									log(LogStatus.INFO,
											"Verified Notification not contains for Event(s): " + eventTitle
													+ " in case of User 1 in Detail Page" + contactRecordName,
											YesNo.No);

								} else {
									log(LogStatus.ERROR, "The Event(s) " + eventTitle
											+ " should not contain in case of User 1 in Detail Page: "
											+ contactRecordName + " , Reason: " + notificationNegativeResultForOldName,
											YesNo.No);
									sa.assertTrue(false, "The Event(s) " + eventTitle
											+ " should not contain in case of User 1 in Detail Page: "
											+ contactRecordName + " , Reason: " + notificationNegativeResultForOldName);
								}

								CommonLib.refresh(driver);

								List<String> notificationNegativeResult = BP
										.verifyNotificationOptionsOnRecordDetailsPage(eventTitleUpdated);
								if (notificationNegativeResult.isEmpty()) {
									log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleUpdated
											+ " in record page of: " + contactRecordName, YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Not Verified the Event(s) " + eventTitleUpdated + " in record page of: "
													+ contactRecordName + ", Reason: " + notificationNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"Not Verified the Event(s) " + eventTitleUpdated + " in record page of: "
													+ contactRecordName + ", Reason: " + notificationNegativeResult);
								}
								CommonLib.refresh(driver);

								CommonLib.ThreadSleep(5000);

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
									List<String> notificationHomeNegativeResultOldEventName = home
											.verifyNotificationOptionsNotContains(eventTitle);
									if (notificationHomeNegativeResultOldEventName.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle
												+ " on Home Page not contains", YesNo.No);

									} else {
										log(LogStatus.ERROR, "The Event(s) " + eventTitle
												+ "+ on Home Page should not contain in case of User 1, Reason: "
												+ notificationHomeNegativeResultOldEventName, YesNo.No);
										sa.assertTrue(false, "The Event(s) " + eventTitle
												+ "+ on Home Page should not contain in case of User 1, Reason: "
												+ notificationHomeNegativeResultOldEventName);
									}

								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
									List<String> notificationHomeNegativeResult = home
											.verifyNotificationOptions(eventTitle);
									if (notificationHomeNegativeResult.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleUpdated
												+ " on Home Page", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"Not Verified the Event(s) " + eventTitleUpdated
														+ "+ on Home Page, Reason: " + notificationHomeNegativeResult,
												YesNo.No);
										sa.assertTrue(false, "Not Verified the Event(s) " + eventTitleUpdated
												+ "+ on Home Page, Reason: " + notificationHomeNegativeResult);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

							} else {
								log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle,
										YesNo.No);
								sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);
							}

						} else {
							log(LogStatus.ERROR,
									"Not Able Click on Edit button for Record Page of Event: " + eventTitle, YesNo.Yes);
							sa.assertTrue(false,
									"Not Able Click on Edit button for Record Page of Event: " + eventTitle);
							driver.close();
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						}
					} else {
						log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle, YesNo.Yes);
						sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityMNNRTc022_VerifyTheCustomNotificationOnHomePageAsWellAsByClickingOnBellIconForExistingTask(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String task1SubjectName = "Task Demo 3";

		String contactRecordName = "Con 1";
		String recordName = "Acc 3";
		String recordType = "Company";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Verify Notification of Task: " + task1SubjectName + " in "
				+ contactRecordName + " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(task1SubjectName);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Task(s): " + task1SubjectName
								+ " in case of User 1 in Detail Page" + contactRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Task(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Task(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjectName + " in " + recordName
				+ " Record not contains in Notification Pane in case of User 1--------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(task1SubjectName);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Task(s): " + task1SubjectName
								+ " in case of User 1 in Detail Page" + recordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Task(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + recordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Task(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + recordName
										+ " , Reason: " + notificationNegativeResult);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(task1SubjectName);
			if (notificationHomeNegativeResult.isEmpty()) {
				log(LogStatus.INFO,
						"Verified Notification for Task(s): " + task1SubjectName + " on Home Page not contains",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"The Task(s) " + task1SubjectName
								+ "+ on Home Page should not contain in case of User 1, Reason: "
								+ notificationHomeNegativeResult,
						YesNo.No);
				sa.assertTrue(false,
						"The Task(s) " + task1SubjectName
								+ "+ on Home Page should not contain in case of User 1, Reason: "
								+ notificationHomeNegativeResult);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityMNNRTc023_VerifyTheCustomNotificationOnHomePageAsWellAsByClickingOnBellIconForExistingCall(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String task1SubjectName = "Task Demo 3 Call";

		String contactRecordName = "Con 1";
		String recordName = "Acc 3";
		String recordType = "Company";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Verify Notification of Call: " + task1SubjectName + " in "
				+ contactRecordName + " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(task1SubjectName);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Call(s): " + task1SubjectName
								+ " in case of User 1 in Detail Page" + contactRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Call(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Call(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Call: " + task1SubjectName + " in " + recordName
				+ " Record not contains in Notification Pane in case of User 1--------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(task1SubjectName);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Call(s): " + task1SubjectName
								+ " in case of User 1 in Detail Page" + recordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Call(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + recordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Call(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + recordName
										+ " , Reason: " + notificationNegativeResult);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(task1SubjectName);
			if (notificationHomeNegativeResult.isEmpty()) {
				log(LogStatus.INFO,
						"Verified Notification for Call(s): " + task1SubjectName + " on Home Page not contains",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"The Call(s) " + task1SubjectName
								+ "+ on Home Page should not contain in case of User 1, Reason: "
								+ notificationHomeNegativeResult,
						YesNo.No);
				sa.assertTrue(false,
						"The Call(s) " + task1SubjectName
								+ "+ on Home Page should not contain in case of User 1, Reason: "
								+ notificationHomeNegativeResult);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc025_AddTheNotesFromTheCustomBellIconAndVerifyTheNotificationBeforeAndAfterUpdatingThenotes(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Deal Closing";

		String contactRecordName = "Martha";

		String[][] event1BasicSectionVerificationInNotesPopup = { { "Subject", eventTitle }, { "Notes", "" } };
		String[][] event1AdvancedSectionVerificationInNotesPopup = null;

		String notesVerifyOnInteraction = "";
		String[] relatedToVerifyOnInteraction = null;
		String updatedNotesOfEvent = "Adding @Glomez @Jack in the loop";
		String[][] event1UpdateBasicSection = { { "Notes", updatedNotesOfEvent } };
		String[][] event1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = null;

		String accountRecordName = "Houlihan Lokey";
		String accountRecordType = "Institution";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------",
				YesNo.No);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					ArrayList<String> interactionResult = BP.verifyRecordOnInteractionCard(null, null, eventTitle,
							notesVerifyOnInteraction, false, true, relatedToVerifyOnInteraction, null);
					if (interactionResult.isEmpty()) {
						log(LogStatus.PASS, "------" + eventTitle + " record has been verified on intraction------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "------" + eventTitle + " record is not verified on intraction, Reason: "
								+ interactionResult + "------", YesNo.No);
						sa.assertTrue(false, "------" + eventTitle + " record is not verified on intraction, Reason: "
								+ interactionResult + "------");
					}

					if (CommonLib.clickUsingJavaScript(driver, BP.getNotificationIcon(), "NotificationIcon")) {

						log(LogStatus.INFO, "Clicked on Notification Icon", YesNo.No);
						if (CommonLib.click(driver, home.addNoteButtonInNotificationOfRecordDetailPage(eventTitle, 20),
								"Add Note Button of Event: " + eventTitle, action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Add Note Button of Event: " + eventTitle, YesNo.No);

							String url2 = getURL(driver, 10);
							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
											event1BasicSectionVerificationInNotesPopup,
											event1AdvancedSectionVerificationInNotesPopup, null);
							if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResultUpdated,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResultUpdated);
							}

							if (BP.updateActivityTimelineRecord(projectName, event1UpdateBasicSection,
									event1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
								log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle,
										YesNo.No);

								CommonLib.refresh(driver);
								ArrayList<String> updatedInteractionResult = BP.verifyRecordOnInteractionCard(null,
										null, eventTitle, updatedNotesOfEvent, true, false,
										relatedToVerifyOnInteraction, null);
								if (updatedInteractionResult.isEmpty()) {
									log(LogStatus.PASS,
											"------" + eventTitle + " record has been verified on intraction------",
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"------" + eventTitle + " record is not verified on intraction, Reason: "
													+ updatedInteractionResult + "------",
											YesNo.No);
									sa.assertTrue(false,
											"------" + eventTitle + " record is not verified on intraction, Reason: "
													+ updatedInteractionResult + "------");
								}

								CommonLib.refresh(driver);
								List<String> notificationNegativeResultInContactRecordPage = BP
										.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
								if (notificationNegativeResultInContactRecordPage.isEmpty()) {
									log(LogStatus.INFO,
											"Verified Notification not contains for Event(s): " + eventTitle
													+ " in case of User 1 in Detail Page" + contactRecordName,
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"The Event(s) " + eventTitle
													+ " should not contain in case of User 1 in Detail Page: "
													+ contactRecordName + " , Reason: "
													+ notificationNegativeResultInContactRecordPage,
											YesNo.No);
									sa.assertTrue(false,
											"The Event(s) " + eventTitle
													+ " should not contain in case of User 1 in Detail Page: "
													+ contactRecordName + " , Reason: "
													+ notificationNegativeResultInContactRecordPage);
								}

								log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
										+ accountRecordName + " Record---------", YesNo.No);
								if (lp.clickOnTab(projectName, tabObj1)) {

									log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

									if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
											accountRecordType, accountRecordName, 30)) {
										log(LogStatus.INFO, accountRecordName + " record of record type "
												+ accountRecordType + " has been open", YesNo.No);
										ThreadSleep(4000);
										if (BP.clicktabOnPage("Acuity")) {
											log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

											List<String> notificationNegativeResult = BP
													.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
											if (notificationNegativeResult.isEmpty()) {
												log(LogStatus.INFO,
														"Verified Notification not contains for Event(s): " + eventTitle
																+ " in case of User 1 in Detail Page"
																+ accountRecordName,
														YesNo.No);

											} else {
												log(LogStatus.ERROR, "The Event(s) " + eventTitle
														+ " should not contain in case of User 1 in Detail Page: "
														+ accountRecordName + " , Reason: "
														+ notificationNegativeResult, YesNo.No);
												sa.assertTrue(false, "The Event(s) " + eventTitle
														+ " should not contain in case of User 1 in Detail Page: "
														+ accountRecordName + " , Reason: "
														+ notificationNegativeResult);
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
											sa.assertTrue(false, "Not able to click on Acuity Tab");
										}

									} else {
										log(LogStatus.ERROR, "Not able to open " + accountRecordName
												+ " record of record type " + accountRecordType, YesNo.No);
										sa.assertTrue(false, "Not able to open " + accountRecordName
												+ " record of record type " + accountRecordType);
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
									sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
								}

								log(LogStatus.INFO,
										"---------Now Going to Verify Event: " + eventTitle
												+ " on Home Page in case of User 1: " + crmUser1EmailID + " ---------",
										YesNo.No);

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
									List<String> notificationHomeNegativeResult = home
											.verifyNotificationOptionsNotContains(eventTitle);
									if (notificationHomeNegativeResult.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle
												+ " on Home Page not contains", YesNo.No);

									} else {
										log(LogStatus.ERROR, "The Event(s) " + eventTitle
												+ "+ on Home Page should not contain in case of User 1, Reason: "
												+ notificationHomeNegativeResult, YesNo.No);
										sa.assertTrue(false, "The Event(s) " + eventTitle
												+ "+ on Home Page should not contain in case of User 1, Reason: "
												+ notificationHomeNegativeResult);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

								lp.CRMlogout();
								ThreadSleep(5000);

								lp.CRMLogin(crmUser2EmailID, adminPassword);

								log(LogStatus.INFO,
										"---------Now Going to Verify Event: " + eventTitle
												+ " on Home Page in case of User 2: " + crmUser2EmailID + " ---------",
										YesNo.No);

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
									List<String> notificationHomeNegativeResult = home
											.verifyNotificationOptionsNotContains(eventTitle);
									if (notificationHomeNegativeResult.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle
												+ " on Home Page not contains n case of User 1", YesNo.No);

									} else {
										log(LogStatus.ERROR, "The Event(s) " + eventTitle
												+ "+ on Home Page should not contain in case of User 2, Reason: "
												+ notificationHomeNegativeResult, YesNo.No);
										sa.assertTrue(false, "The Event(s) " + eventTitle
												+ "+ on Home Page should not contain in case of User 2, Reason: "
												+ notificationHomeNegativeResult);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

							} else {
								log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle,
										YesNo.No);
								sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);
							}

						} else {
							log(LogStatus.ERROR, "Not Able to Click on Add Noted Button of Event: " + eventTitle,
									YesNo.No);
							sa.assertTrue(false, "Not Able to Click on Add Noted Button of Event: " + eventTitle);
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Notification Icon", YesNo.No);
						sa.assertTrue(false, "Not Able to Click on Notification Icon");
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityMNNRTc026_CreateTheEventClickOnTheEventHeaderFromHomePageNotificationAndAddTheNotesAndVerifyTheNotificationPopUp(
			String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String eventTitle = "Marketing Webinar 2";
		String eventAttendees = "Dealroom1.3+James@gmail.com<Break>Dealroom1.3+Jhon@gmail.com" + "<Break>"
				+ crmUser2EmailID + "<Break>" + crmUser3EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String contactRecordName = "Jhon";
		String relateToVerifyOnNotesPopUp = null;
		String[][] event1BasicSectionVerificationInNotesPopup = { { "Subject", eventTitle }, { "Notes", "" },
				{ "Related_To", relateToVerifyOnNotesPopUp } };
		String[][] event1AdvancedSectionVerificationInNotesPopup = { { "Location", "" },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName },
				{ "Start Date Time<break>Date", startDate }, { "Start Date Time<break>Time", startTime },
				{ "End Date Time<break>Date", endDate }, { "End Date Time<break>Time", endTime } };

		String updatedNotesOfEvent = "Deal @sumo logic and @Max fund should be in loop";
		String[][] event1UpdateBasicSectionOnHomepageNotePopup = { { "Notes", updatedNotesOfEvent } };
		String[][] event1UpdateAdvancedSectionOnHomepageNotePopup = null;
		String[] eventupdatedSuggestedTagsOnHomepageNotePopup = null;
		Integer countIconBeforeUpdate = 0;
		Integer countIconAfterUpdate = 0;

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						String parentID = BP.notificationEventNavigateToDetailPageOrNot(eventTitle);
						if (parentID != null) {
							log(LogStatus.INFO, "-------Able to navigate to Event Detail Page after click on event: "
									+ eventTitle + " from Record Detail Page Notification-------", YesNo.No);
							driver.close();
							CommonLib.ThreadSleep(2000);
							driver.switchTo().window(parentID);
						} else {
							log(LogStatus.ERROR,
									"-------Not able to navigate to Event Detail Page after click on event: "
											+ eventTitle + " from Record Detail Page Notification-------",
									YesNo.No);
							sa.assertTrue(false,
									"-------Not able to navigate to Event Detail Page after click on event: "
											+ eventTitle + " from Record Detail Page Notification-------");
						}

						try {

							CommonLib.refresh(driver);
							countIconBeforeUpdate = Integer.valueOf(
									CommonLib.getText(driver, BP.countOfAcuityNotificationInRecordDetailPage(20),
											"Count Notification Icon", action.BOOLEAN));
						} catch (Exception e) {
							log(LogStatus.ERROR,
									"Exception Occured, Can not Convert " + CommonLib.getText(driver,
											BP.countOfAcuityNotificationInRecordDetailPage(20),
											"Count Notification Icon", action.BOOLEAN) + " into Int",
									YesNo.No);
							sa.assertTrue(false,
									"Exception Occured, Can not Convert " + CommonLib.getText(driver,
											BP.countOfAcuityNotificationInRecordDetailPage(20),
											"Count Notification Icon", action.BOOLEAN) + " into Int");
						}
						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle, YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + ", Reason: "
									+ notificationNegativeResult, YesNo.No);
							sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + ", Reason: "
									+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

				CommonLib.ThreadSleep(5000);
				List<String> notificationHomeNegativeResult2 = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult2.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

					if (lp.clickOnTab(projectName, TabName.HomeTab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

						if (CommonLib.click(driver, home.eventLinkInHomePageNotification(eventTitle, 20),
								"Link of Event: " + eventTitle, action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Link of Event: " + eventTitle, YesNo.No);

							if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Edit Button of Event: " + eventTitle, YesNo.No);

								String url2 = getURL(driver, 10);
								ThreadSleep(10000);
								ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
										.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
												event1BasicSectionVerificationInNotesPopup,
												event1AdvancedSectionVerificationInNotesPopup, null);
								if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
									log(LogStatus.INFO,
											"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
													+ NotesPopUpPrefilledNegativeResultUpdated,
											YesNo.No);
									sa.assertTrue(false,
											"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
													+ NotesPopUpPrefilledNegativeResultUpdated);
								}

								if (BP.updateActivityTimelineRecord(projectName,
										event1UpdateBasicSectionOnHomepageNotePopup,
										event1UpdateAdvancedSectionOnHomepageNotePopup, null,
										eventupdatedSuggestedTagsOnHomepageNotePopup, null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle,
											YesNo.No);

									log(LogStatus.INFO,
											"---------Now Going to Verify Event: " + eventTitle + "---------",
											YesNo.No);

									log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
											+ contactRecordName + " Record---------", YesNo.No);
									if (lp.clickOnTab(projectName, tabObj2)) {

										log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

										if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
												contactRecordName, 30)) {
											log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
											ThreadSleep(4000);
											if (BP.clicktabOnPage("Acuity")) {
												log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

												try {

													countIconAfterUpdate = Integer.valueOf(CommonLib.getText(driver,
															BP.countOfAcuityNotificationInRecordDetailPage(20),
															"Count Notification Icon", action.BOOLEAN));
													if (countIconBeforeUpdate.equals(countIconAfterUpdate + 1)) {
														log(LogStatus.INFO,
																"Verified Notification Icon Count before update event is greater by 1, before update event count: "
																		+ countIconBeforeUpdate
																		+ " & after update event count: "
																		+ countIconBeforeUpdate,
																YesNo.No);
													} else {
														log(LogStatus.ERROR,
																"Not Verified Notification Icon Count before update event is greater by 1, before update event count: "
																		+ countIconBeforeUpdate
																		+ " & after update event count: "
																		+ countIconBeforeUpdate,
																YesNo.No);
														sa.assertTrue(false,
																"Not Verified Notification Icon Count before update event is greater by 1, before update event count: "
																		+ countIconBeforeUpdate
																		+ " & after update event count: "
																		+ countIconBeforeUpdate);
													}

													if (BP.notificationIconCountAndActualCountMatchedOrNot()) {
														log(LogStatus.INFO, "Verified Count of Icon and Actual matched",
																YesNo.No);
													} else {
														log(LogStatus.ERROR, "Count of Icon and Actual not matched",
																YesNo.No);
														sa.assertTrue(false, "Count of Icon and Actual not matched");
													}

												} catch (Exception e) {
													log(LogStatus.ERROR, "Exception Occured, Can not Convert "
															+ CommonLib.getText(driver,
																	BP.countOfAcuityNotificationInRecordDetailPage(20),
																	"Count Notification Icon", action.BOOLEAN)
															+ " into Int", YesNo.No);
													sa.assertTrue(false, "Exception Occured, Can not Convert "
															+ CommonLib.getText(driver,
																	BP.countOfAcuityNotificationInRecordDetailPage(20),
																	"Count Notification Icon", action.BOOLEAN)
															+ " into Int");
												}
												List<String> notificationNegativeResult = BP
														.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
												if (notificationNegativeResult.isEmpty()) {
													log(LogStatus.INFO,
															"Verified Notification for Event(s): " + eventTitle,
															YesNo.No);

												} else {
													log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
															+ ", Reason: " + notificationNegativeResult, YesNo.No);
													sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
															+ ", Reason: " + notificationNegativeResult);
												}

											} else {
												log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
												sa.assertTrue(false, "Not able to click on Acuity Tab");
											}

										} else {
											log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record",
													YesNo.No);
											sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
										}
									} else {
										log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
										sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
									}

									if (lp.clickOnTab(projectName, TabName.HomeTab)) {
										log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
										List<String> notificationHomeNegativeResult = home
												.verifyNotificationOptionsNotContains(eventTitle);
										if (notificationHomeNegativeResult.isEmpty()) {
											log(LogStatus.INFO,
													"Verified Notification for Event(s): " + eventTitle
															+ " on Home Page not contains in case of User 1 i.e.: "
															+ crmUser1EmailID,
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "The Event(s) " + eventTitle
													+ "+ on Home Page should not contain in case of User 1 i.e.: "
													+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult,
													YesNo.No);
											sa.assertTrue(false, "The Event(s) " + eventTitle
													+ "+ on Home Page should not contain in case of User 1 i.e.: "
													+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult);
										}
									} else {
										sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
										log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
									}

									lp.CRMlogout();
									ThreadSleep(5000);
									lp.CRMLogin(crmUser2EmailID, adminPassword);

									log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle
											+ " in Interaction Section---------", YesNo.No);
									if (lp.clickOnTab(projectName, TabName.HomeTab)) {
										log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
										List<String> notificationHomeNegativeResult = home
												.verifyNotificationOptionsNotContains(eventTitle);
										if (notificationHomeNegativeResult.isEmpty()) {
											log(LogStatus.INFO,
													"Verified Notification for Event(s): " + eventTitle
															+ " on Home Page not contains in case of User 2 i.e.: "
															+ crmUser2EmailID,
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "The Event(s) " + eventTitle
													+ "+ on Home Page should not contain in case of User 2 i.e.: "
													+ crmUser2EmailID + ", Reason: " + notificationHomeNegativeResult,
													YesNo.No);
											sa.assertTrue(false, "The Event(s) " + eventTitle
													+ "+ on Home Page should not contain in case of User 2 i.e.: "
													+ crmUser2EmailID + ", Reason: " + notificationHomeNegativeResult);
										}
									} else {
										sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
										log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
									}

									lp.CRMlogout();
									ThreadSleep(5000);
									lp.CRMLogin(crmUser3EmailID, adminPassword);

									log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle
											+ " in Interaction Section---------", YesNo.No);
									if (lp.clickOnTab(projectName, TabName.HomeTab)) {
										log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
										List<String> notificationHomeNegativeResult = home
												.verifyNotificationOptionsNotContains(eventTitle);
										if (notificationHomeNegativeResult.isEmpty()) {
											log(LogStatus.INFO,
													"Verified Notification for Event(s): " + eventTitle
															+ " on Home Page not contains in case of User 1 i.e.: "
															+ crmUser3EmailID,
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "The Event(s) " + eventTitle
													+ "+ on Home Page should not contain in case of User 3 i.e.: "
													+ crmUser3EmailID + ", Reason: " + notificationHomeNegativeResult,
													YesNo.No);
											sa.assertTrue(false, "The Event(s) " + eventTitle
													+ "+ on Home Page should not contain in case of User 3 i.e.: "
													+ crmUser3EmailID + ", Reason: " + notificationHomeNegativeResult);
										}
									} else {
										sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
										log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
									}

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle,
											YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);
								}

							} else {
								log(LogStatus.ERROR,
										"Not Able Click on Edit button for Record Page of Event: " + eventTitle,
										YesNo.Yes);
								sa.assertTrue(false,
										"Not Able Click on Edit button for Record Page of Event: " + eventTitle);
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

						} else {
							log(LogStatus.ERROR, "Not Able to Click on Link of Event: " + eventTitle, YesNo.No);
							sa.assertTrue(false, "Not Able to Click on Link of Event: " + eventTitle);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
						log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityMNNRTc029_CreateTheEventByTaggingAttendeesMakeOneOfTheAttendeeInactiveAndVerifyTheNotificationPopUpOnceTheUserIsActive(
			String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);

		String eventTitle = "Mulesoft Connect";
		String eventAttendees = "Dealroom1.3+Lomez@gmail.com<Break>Dealroom1.3+Max@gmail.com<Break>Dealroom1.3+James@gmail.com"
				+ "<Break>" + crmUser2EmailID + "<Break>" + crmUser3EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";
		String recordName = "Max";

		String user3firstName = crmUser3FirstName;
		String User3LastName = crmUser3LastName;

		String user3EmailId = crmUser3EmailID;
		String[] labelAndValueSeprateByBreak = { "Attendees" + "<break>" + crmUser3FirstName + " " + crmUser3LastName };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			log(LogStatus.INFO, "---------Now Going to Verify Notification of Event: " + eventTitle
					+ " in Record Page: " + recordName + " in case of User 1 , i.e.: " + crmUser1EmailID + "---------",
					YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification contains for Event(s): " + eventTitle
									+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page" + recordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + recordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + recordName + " , Reason: "
											+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + recordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			lp.CRMlogout();
			ThreadSleep(5000);
			lp.CRMLogin(crmUser2EmailID, adminPassword);

			log(LogStatus.INFO, "---------Now Going to Verify Notification of Event: " + eventTitle
					+ " in Record Page: " + recordName + " in case of User 2 , i.e.: " + crmUser2EmailID + "---------",
					YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification contains for Event(s): " + eventTitle
									+ " in case of User 2 i.e.: " + crmUser2EmailID + " in Detail Page" + recordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitle + " in case of User 2 i.e.: "
											+ crmUser2EmailID + " in Detail Page: " + recordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitle + " in case of User 2 i.e.: "
											+ crmUser2EmailID + " in Detail Page: " + recordName + " , Reason: "
											+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + recordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			lp.CRMlogout();
			ThreadSleep(5000);
			lp.CRMLogin(crmUser3EmailID, adminPassword);

			log(LogStatus.INFO, "---------Now Going to Verify Notification of Event: " + eventTitle
					+ " in Record Page: " + recordName + " in case of User 3 , i.e.: " + crmUser3EmailID + "---------",
					YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification contains for Event(s): " + eventTitle
									+ " in case of User 3 i.e.: " + crmUser3EmailID + " in Detail Page" + recordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitle + " in case of User 3 i.e.: "
											+ crmUser3EmailID + " in Detail Page: " + recordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitle + " in case of User 3 i.e.: "
											+ crmUser3EmailID + " in Detail Page: " + recordName + " , Reason: "
											+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + recordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			lp.CRMlogout();
			ThreadSleep(5000);

			lp.CRMLogin(superAdminUserName, adminPassword, appName);
			boolean flag = false;
			if (home.clickOnSetUpLink()) {
				flag = true;
				String parentWindow = switchOnWindow(driver);
				if (parentWindow == null) {
					sa.assertTrue(false,
							"No new window is open after click on setup link in lighting mode so cannot Update CRM User3");
					log(LogStatus.SKIP,
							"No new window is open after click on setup link in lighting mode so cannot Update CRM User3",
							YesNo.Yes);
					exit("No new window is open after click on setup link in lighting mode so cannot Update CRM User3");
				}
				if (setup.userActiveOrInActive(user3firstName, User3LastName, user3EmailId,
						PermissionType.removePermission.toString())) {
					log(LogStatus.INFO, "CRM User has been updated Successfully and inactive: " + user3firstName + " "
							+ User3LastName, YesNo.No);

					flag = true;

				} else {
					log(LogStatus.ERROR, "CRM User is not updated and inactive" + user3firstName + " " + User3LastName,
							YesNo.No);
					sa.assertTrue(false,
							"CRM User is not updated and inactive " + user3firstName + " " + User3LastName);
				}
				driver.close();
				driver.switchTo().window(parentWindow);

			}

			if (flag == true) {

				lp.CRMlogout();
				ThreadSleep(5000);
				lp.CRMLogin(crmUser2EmailID, adminPassword);

				log(LogStatus.INFO,
						"---------Now Going to Verify Attendee User 3 in case in Event detail page in case of User 2: "
								+ crmUser2EmailID + "---------",
						YesNo.No);
				if (lp.clickOnTab(projectName, tabObj2)) {

					log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

					if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
						log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
						ThreadSleep(4000);
						if (BP.clicktabOnPage("Acuity")) {
							log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
							if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
								log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle,
										YesNo.No);

								CommonLib.ThreadSleep(8000);
								List<String> eventDetailPageNegativeResult = BP
										.fieldValueVerification(labelAndValueSeprateByBreak);

								if (eventDetailPageNegativeResult.isEmpty()) {
									log(LogStatus.PASS,
											"------" + eventTitle
													+ " labels and their values in Detail page has been verified------",
											YesNo.No);

								} else {
									log(LogStatus.ERROR, "------" + eventTitle
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ eventDetailPageNegativeResult + "------", YesNo.No);
									sa.assertTrue(false, "------" + eventTitle
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ eventDetailPageNegativeResult + "------");

								}

							} else {
								log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
										YesNo.Yes);
								sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
							sa.assertTrue(false, "Not able to click on Acuity Tab");
						}

					} else {
						log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
						sa.assertTrue(false, "Not able to open " + recordName + " record");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
					sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
				}

				lp.CRMlogout();
				ThreadSleep(5000);

				lp.CRMLogin(superAdminUserName, adminPassword, appName);
				boolean flag2 = false;
				if (home.clickOnSetUpLink()) {
					flag2 = true;
					String parentWindow = switchOnWindow(driver);
					if (parentWindow == null) {
						sa.assertTrue(false,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User1");
						log(LogStatus.SKIP,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User1",
								YesNo.Yes);
						exit("No new window is open after click on setup link in lighting mode so cannot create CRM User1");
					}
					if (setup.userActiveOrInActive(user3firstName, User3LastName, user3EmailId,
							PermissionType.givePermission.toString())) {
						log(LogStatus.INFO, "CRM User has been updated Successfully and inactive: " + user3firstName
								+ " " + User3LastName, YesNo.No);

						flag2 = true;

					} else {
						log(LogStatus.ERROR,
								"CRM User is not updated and inactive" + user3firstName + " " + User3LastName,
								YesNo.No);
						sa.assertTrue(false,
								"CRM User is not updated and inactive " + user3firstName + " " + User3LastName);
					}
					driver.close();
					driver.switchTo().window(parentWindow);

				}
				if (flag2) {

					lp.CRMlogout();
					ThreadSleep(5000);
					lp.CRMLogin(crmUser3EmailID, adminPassword);

					log(LogStatus.INFO,
							"---------Now Going to Verify Notification of Event: " + eventTitle + " in Record Page: "
									+ recordName + " in case of User 3 , i.e.: " + crmUser3EmailID + "---------",
							YesNo.No);
					if (lp.clickOnTab(projectName, tabObj2)) {

						log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

						if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
							log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
							ThreadSleep(4000);
							if (BP.clicktabOnPage("Acuity")) {
								log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

								List<String> notificationNegativeResult = BP
										.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
								if (notificationNegativeResult.isEmpty()) {
									log(LogStatus.INFO,
											"Verified Notification contains for Event(s): " + eventTitle
													+ " in case of User 3 i.e.: " + crmUser3EmailID + " in Detail Page"
													+ recordName,
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Not Verified Event(s) " + eventTitle + " in case of User 3 i.e.: "
													+ crmUser3EmailID + " in Detail Page: " + recordName + " , Reason: "
													+ notificationNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"Not Verified Event(s) " + eventTitle + " in case of User 3 i.e.: "
													+ crmUser3EmailID + " in Detail Page: " + recordName + " , Reason: "
													+ notificationNegativeResult);
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
								sa.assertTrue(false, "Not able to click on Acuity Tab");
							}

						} else {
							log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
							sa.assertTrue(false, "Not able to open " + recordName + " record");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
						sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
					}

				} else {
					log(LogStatus.ERROR, "Not able to active the User 3, i.e.: " + crmUser3EmailID, YesNo.No);
					sa.assertTrue(false, "Not able to active the User 3, i.e.: " + crmUser3EmailID);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Inactive the User 3, i.e.: " + crmUser3EmailID, YesNo.No);
				sa.assertTrue(false, "Not able to Inactive the User 3, i.e.: " + crmUser3EmailID);
			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc033_Create4EventsWithSameStartAndEndDateAndTimeAndVerifyTheNotificationPopUp(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitles[] = "Relive salesforce Live 1<break>Relive salesforce Live 2<break>Relive salesforce Live 3<break>Relive salesforce Live 4"
				.split("<break>", -1);
		String eventAttendees = "Dealroom1.3+James@gmail.com<Break>Dealroom1.3+Jhon@gmail.com" + "<Break>"
				+ crmUser1EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);

		String startTime = "10:00 PM";
		String endTime = "10:00 PM";
		String descriptionBox = "";

		String updatedRelatedTo = "Sumo Logic";
		String[][] task1UpdateBasicSection = { { "Related_To", updatedRelatedTo } };
		String[][] task1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = null;
		String contactRecordName = "James";

		String accountRecordName = "Sumo Logic";
		String accountRecordType = "Company";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Event(s): " + eventTitles + " through Outlook---------",
				YesNo.No);

		Integer loopCount = 0;
		Integer status = 0;
		for (String eventTitle : eventTitles) {

			if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
					eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
				log(LogStatus.INFO, "-----Event Created Msg is showing, So Event of Title: " + eventTitle
						+ " has been created-----", YesNo.No);

				status++;

			} else {
				log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been created-----", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been created-----");
			}

			loopCount++;
		}

		for (String eventTitle : eventTitles) {

			CommonLib.refresh(driver);
			if (CommonLib.click(driver, home.addNoteButtonOfEventInHomePageNotification(eventTitle, 20),
					"Add Note Button of Event: " + eventTitle, action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Add Note Button of Event: " + eventTitle, YesNo.No);
				if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, task1UpdateAdvancedSection,
						null, updatedSuggestedTags, null)) {
					log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle, YesNo.No);

				} else {
					log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle, YesNo.No);
					sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Add Noted Button of Event: " + eventTitle, YesNo.No);
				sa.assertTrue(false, "Not Able to Click on Add Noted Button of Event: " + eventTitle);
			}
		}

		if (status.equals(loopCount)) {

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitles);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitles + " on Home Page",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitles + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitles + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event(s): " + eventTitles + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitles);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"Verified Notification contains for Event(s): " + eventTitles
											+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page"
											+ contactRecordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitles + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitles + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event(s): " + eventTitles + " in " + accountRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType,
						accountRecordName, 30)) {
					log(LogStatus.INFO,
							accountRecordName + " record of record type " + accountRecordType + " has been open",
							YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitles);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitles, YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitles + ", Reason: "
									+ notificationNegativeResult, YesNo.No);
							sa.assertTrue(false, "Not Verified the Event(s) " + eventTitles + ", Reason: "
									+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR,
							"Not able to open " + accountRecordName + " record of record type " + accountRecordType,
							YesNo.No);
					sa.assertTrue(false,
							"Not able to open " + accountRecordName + " record of record type " + accountRecordType);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
			}

		}

		else {

			sa.assertTrue(false, "Some of the Given Events not gets created: " + eventTitles
					+ " , So not able to further continue to check on Notification Panes");
			log(LogStatus.SKIP, "Some of the Given Events not gets created: " + eventTitles
					+ " , So not able to further continue to check on Notification Panes", YesNo.Yes);

		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityMNNRTc035_VerifyTheNotificationPopUpWhenAttendeeNameIsModifiedAfterCreatingTheEvent(
			String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);

		String eventTitle = "This event will have your Business circles talking +4";
		String eventAttendees = "Dealroom1.3+Lomez@gmail.com<Break>Dealroom1.3+Max@gmail.com<Break>Dealroom1.3+James@gmail.com"
				+ "<Break>" + crmUser2EmailID + "<Break>" + crmUser3EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -1);

		String startTime = "06:00 PM";
		String endTime = "06:30 PM";
		String descriptionBox = "Revenue Grid Event";
		String contactRecordName = "Max";

		String user2firstName = crmUser2FirstName;
		String User2LastName = crmUser2LastName + "updated";

		String user2EmailId = crmUser2EmailID;
		String[] labelAndValueSeprateByBreak = { "Attendees" + "<break>" + user2firstName + " " + User2LastName };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			lp.CRMlogout();
			ThreadSleep(5000);
			lp.CRMLogin(crmUser2EmailID, adminPassword);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			lp.CRMlogout();
			ThreadSleep(5000);

			lp.CRMLogin(superAdminUserName, adminPassword, appName);
			boolean flag = false;
			if (home.clickOnSetUpLink()) {
				flag = true;
				String parentWindow = switchOnWindow(driver);
				if (parentWindow == null) {
					sa.assertTrue(false,
							"No new window is open after click on setup link in lighting mode so cannot Update CRM User2");
					log(LogStatus.SKIP,
							"No new window is open after click on setup link in lighting mode so cannot Update CRM User2",
							YesNo.Yes);
					exit("No new window is open after click on setup link in lighting mode so cannot Update CRM User2");
				}
				if (setup.editPEUserAndUpdateTheName(user2firstName, User2LastName, user2EmailId)) {
					log(LogStatus.INFO,
							"CRM User Name has been updated Successfully to: " + user2firstName + " " + User2LastName,
							YesNo.No);
					ExcelUtils.writeData(testCasesFilePath, user2firstName, "Users", excelLabel.Variable_Name, "User2",
							excelLabel.User_First_Name);
					ExcelUtils.writeData(testCasesFilePath, User2LastName, "Users", excelLabel.Variable_Name, "User2",
							excelLabel.User_Last_Name);

					flag = true;

				} else {
					log(LogStatus.ERROR, "CRM User Name has not been updated Successfully to: " + user2firstName + " "
							+ User2LastName, YesNo.No);
					sa.assertTrue(false, "CRM User Name has not been updated Successfully to: " + user2firstName + " "
							+ User2LastName);
				}
				driver.close();
				driver.switchTo().window(parentWindow);

			}

			if (flag == true) {

				lp.CRMlogout();
				ThreadSleep(5000);
				lp.CRMLogin(crmUser2EmailID, adminPassword);

				if (lp.clickOnTab(projectName, TabName.HomeTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
					List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
					if (notificationHomeNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
								+ notificationHomeNegativeResult, YesNo.No);
						sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
								+ notificationHomeNegativeResult);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
				}

				lp.CRMlogout();
				ThreadSleep(5000);
				lp.CRMLogin(crmUser1EmailID, adminPassword);

				if (lp.clickOnTab(projectName, TabName.HomeTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
					List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
					if (notificationHomeNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
								+ notificationHomeNegativeResult, YesNo.No);
						sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
								+ notificationHomeNegativeResult);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
				}

				log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
						+ " Record---------", YesNo.No);
				if (lp.clickOnTab(projectName, tabObj2)) {

					log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

					if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName,
							30)) {
						log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
						ThreadSleep(4000);
						if (BP.clicktabOnPage("Acuity")) {
							log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

							List<String> notificationNegativeResult = BP
									.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
							if (notificationNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Verified Notification contains for Event(s): " + eventTitle
												+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page"
												+ contactRecordName,
										YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
												+ crmUser1EmailID + " in Detail Page: " + contactRecordName
												+ " , Reason: " + notificationNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
												+ crmUser1EmailID + " in Detail Page: " + contactRecordName
												+ " , Reason: " + notificationNegativeResult);
							}

							if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
								log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle,
										YesNo.No);
								CommonLib.ThreadSleep(8000);
								List<String> eventDetailPageNegativeResult = BP
										.fieldValueVerification(labelAndValueSeprateByBreak);

								if (eventDetailPageNegativeResult.isEmpty()) {
									log(LogStatus.PASS,
											"------" + eventTitle
													+ " labels and their values in Detail page has been verified------",
											YesNo.No);

									driver.close();
									CommonLib.ThreadSleep(3000);

									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								} else {
									log(LogStatus.ERROR, "------" + eventTitle
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ eventDetailPageNegativeResult + "------", YesNo.No);
									sa.assertTrue(false, "------" + eventTitle
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ eventDetailPageNegativeResult + "------");

									driver.close();
									CommonLib.ThreadSleep(3000);

									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								}
							} else {
								log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitle
										+ " in Detail Page: " + contactRecordName, YesNo.Yes);
								sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitle
										+ " in Detail Page: " + contactRecordName);

							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
							sa.assertTrue(false, "Not able to click on Acuity Tab");
						}

					} else {
						log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
						sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
					sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
				}

			} else {
				log(LogStatus.ERROR,
						"CRM User Name has not been updated Successfully to: " + user2firstName + " " + User2LastName,
						YesNo.No);
				sa.assertTrue(false,
						"CRM User Name has not been updated Successfully to: " + user2firstName + " " + User2LastName);
			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityMNNRTc039_CreateAllDayEventAndVerifyTheNotification(String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Webinar 1 all  day";
		String eventAttendees = "Dealroom1.3+James@gmail.com<Break>Dealroom1.3+Lenis@gmail.com" + "<Break>"
				+ crmUser3EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0);

		String startTime = "";
		String endTime = "";
		String descriptionBox = "Revenue Grid Event";
		String contactRecordName = "Lenis";
		String[] labelAndValueSeprateByBreak = { "Subject" + "<break>" + eventTitle, "Start" + "<break>" + startDate,
				"End" + "<break>" + endDate, "Description" + "<break>" + "",
				"Attendees" + "<break>" + crmUser3FirstName + " " + crmUser3LastName,
				"Related Associations" + "<break>" + "", "Related Contacts" + "<break>" + "James, Lenis",
				"Name" + "<break>" + "  " };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, true)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO,
							"Verified Notification for Event(s): " + eventTitle
									+ " on Home Page not contains in case of user 1 i.e.: " + crmUser1EmailID,
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 1 i.e.: "
									+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult,
							YesNo.No);
					sa.assertTrue(false,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 1 i.e.: "
									+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitle
									+ " in case of User 1 in Detail Page" + contactRecordName, YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"The Event(s) " + eventTitle
											+ " should not contain in case of User 1 in Detail Page: "
											+ contactRecordName + " , Reason: " + notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"The Event(s) " + eventTitle
											+ " should not contain in case of User 1 in Detail Page: "
											+ contactRecordName + " , Reason: " + notificationNegativeResult);
						}

						CommonLib.refresh(driver);
						ArrayList<String> result = BP.verifyRecordOnInteractionCard(null, null, eventTitle, null, false,
								true, null, null);
						if (result.isEmpty()) {
							log(LogStatus.PASS, "------" + eventTitle + " record has been verified on intraction------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + eventTitle
									+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
							sa.assertTrue(false, "------" + eventTitle
									+ " record is not verified on intraction, Reason: " + result + "------");
						}

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);

							CommonLib.ThreadSleep(8000);
							List<String> eventDetailPageNegativeResult = BP
									.fieldValueVerification(labelAndValueSeprateByBreak);

							if (eventDetailPageNegativeResult.isEmpty()) {
								log(LogStatus.PASS,
										"------" + eventTitle
												+ " labels and their values in Detail page has been verified------",
										YesNo.No);
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							} else {
								log(LogStatus.ERROR, "------" + eventTitle
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ eventDetailPageNegativeResult + "------", YesNo.No);
								sa.assertTrue(false, "------" + eventTitle
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ eventDetailPageNegativeResult + "------");
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							}

						} else {
							log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
									YesNo.Yes);
							sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);
						}

						lp.CRMlogout();
						ThreadSleep(5000);
						lp.CRMLogin(crmUser3EmailID, adminPassword);

						List<String> notificationHomeNegativeResult = home
								.verifyNotificationOptionsNotContains(eventTitle);
						if (notificationHomeNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Event(s): " + eventTitle
									+ " not contains in Notification pane in Home Page", YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s): " + eventTitle
											+ " not contains in Notification pane in Home Page, Reason: "
											+ notificationHomeNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s): " + eventTitle
											+ " not contains in Notification pane in Home Page, Reason: "
											+ notificationHomeNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc037_EditEventAndAddNotesWithAlreadyTaggedContactFirmThenSuggestedPopUpNotCome(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitleExisting = "Relive salesforce Live 1";

		String updatedNotes = "Sumo Logic, James, Jhon";
		String[][] event1UpdateBasicSection = { { "Notes", updatedNotes } };
		String[][] event1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = "SuggestedPopUpShouldNotThere".split("<break>", -1);

		String accountRecordName = "Sumo Logic";
		String accountRecordType = "Company";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType,
					accountRecordName, 30)) {
				log(LogStatus.INFO,
						accountRecordName + " record of record type " + accountRecordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleExisting)) {
						log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitleExisting,
								YesNo.No);
						if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);

							if (BP.updateActivityTimelineRecord(projectName, event1UpdateBasicSection,
									event1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
								log(LogStatus.PASS, "Activity timeline record has been Updated for: "
										+ eventTitleExisting + " , also verified Suggested PopUp not Open", YesNo.No);

								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								CommonLib.refresh(driver);

								ArrayList<String> interactionResult = BP.verifyRecordOnInteractionCard(null, null,
										eventTitleExisting, null, true, false, null, null);
								if (interactionResult.isEmpty()) {
									log(LogStatus.PASS, "------" + eventTitleExisting
											+ " record has been verified on intraction------", YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"------" + eventTitleExisting
													+ " record is not verified on intraction, Reason: "
													+ interactionResult + "------",
											YesNo.No);
									sa.assertTrue(false,
											"------" + eventTitleExisting
													+ " record is not verified on intraction, Reason: "
													+ interactionResult + "------");
								}

								List<String> notificationNegativeResult = BP
										.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleExisting);
								if (notificationNegativeResult.isEmpty()) {
									log(LogStatus.INFO,
											"Verified Notification not contains for Event(s): " + eventTitleExisting
													+ " in case of User 1 in Detail Page" + accountRecordName,
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"The Event(s) " + eventTitleExisting
													+ " should not contain in case of User 1 in Detail Page: "
													+ accountRecordName + " , Reason: " + notificationNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"The Event(s) " + eventTitleExisting
													+ " should not contain in case of User 1 in Detail Page: "
													+ accountRecordName + " , Reason: " + notificationNegativeResult);
								}

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
									List<String> notificationHomeNegativeResult = home
											.verifyNotificationOptionsNotContains(eventTitleExisting);
									if (notificationHomeNegativeResult.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleExisting
												+ " on Home Page not contains", YesNo.No);

									} else {
										log(LogStatus.ERROR, "The Event(s) " + eventTitleExisting
												+ "+ on Home Page should not contain in case of User 1, Reason: "
												+ notificationHomeNegativeResult, YesNo.No);
										sa.assertTrue(false, "The Event(s) " + eventTitleExisting
												+ "+ on Home Page should not contain in case of User 1, Reason: "
												+ notificationHomeNegativeResult);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

								lp.CRMlogout();
								ThreadSleep(5000);
								lp.CRMLogin(crmUser2EmailID, adminPassword);

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
									List<String> notificationHomeNegativeResult = home
											.verifyNotificationOptionsNotContains(eventTitleExisting);
									if (notificationHomeNegativeResult.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleExisting
												+ " on Home Page not contains in case of User 2", YesNo.No);

									} else {
										log(LogStatus.ERROR, "The Event(s) " + eventTitleExisting
												+ "+ on Home Page should not contain in case of User 2, Reason: "
												+ notificationHomeNegativeResult, YesNo.No);
										sa.assertTrue(false, "The Event(s) " + eventTitleExisting
												+ "+ on Home Page should not contain in case of User 2, Reason: "
												+ notificationHomeNegativeResult);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

							} else {
								log(LogStatus.FAIL,
										"Activity timeline record has not Updated for: " + eventTitleExisting,
										YesNo.No);
								sa.assertTrue(false,
										"Activity timeline record has not Updated for: " + eventTitleExisting);
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								CommonLib.refresh(driver);
							}

						} else {
							log(LogStatus.ERROR,
									"Not Able Click on Edit button for Record Page of Event: " + eventTitleExisting,
									YesNo.Yes);
							sa.assertTrue(false,
									"Not Able Click on Edit button for Record Page of Event: " + eventTitleExisting);
							driver.close();
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						}

					} else {
						log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitleExisting,
								YesNo.Yes);
						sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitleExisting);

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType,
						YesNo.No);
				sa.assertTrue(false,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc113_CreateEventAndVerifyTheImpactOnSalesforceWhenSubjectEventStartAndEndDateTimeLocationAndDescriptionOfTheEventIsUpdatedInOutlook(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);

		String eventTitle = "Salesforce Event";
		String eventAttendees = "Dealroom1.3+Litz@gmail.com<Break>Dealroom1.3+Jhon@gmail.com" + "<Break>"
				+ crmUser1EmailID + "<Break>" + crmUser2EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 1);
		String endDateInAnotherForm1 = CommonLib.convertDateFromOneFormatToAnother(endDate, "dd/MM/yyyy", "m/d/yyyy");
		String endDateInAnotherForm2 = CommonLib.convertDateFromOneFormatToAnother(endDate, "dd/MM/yyyy", "MMMM yyyy");

		String startTime = "02:00 PM";
		String endTime = "09:00 PM";
		String descriptionBox = "Revenue Grid Event";

		String updatedEventName = "Salesforce Event Updated";
		String updatedEventAttendees = "Dealroom1.3+James@gmail.com";
		String updatedStartDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 1);
		String updatedEndDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 1);
		String updatedStartTime = "08:00 AM";
		String updatedEndTime = "10:00 AM";
		String updatedDescriptionBox = "";
		boolean updatedAllDayToggle = false;
		String updatedEventAttendeesToRemove = null;

		String recordName = "Litz";

		String[] labelAndValueSeprateByBreak = { "Subject" + "<break>" + updatedEventName,
				"Start" + "<break>" + updatedStartDate, "End" + "<break>" + updatedEndDate,
				"Description" + "<break>" + "", "Attendees" + "<break>" + crmUser3FirstName + " " + crmUser3LastName,
				"Related Associations" + "<break>" + "Sumo Logic, Vertica, Demo Deal",
				"Related Contacts" + "<break>" + "James, Jhon, Litz" };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			if (op.loginNavigateAndUpdateTheEvent(rgOutLookUser1Email, rgOutLookUser1Password, endDateInAnotherForm1,
					endDateInAnotherForm2, eventTitle, false, updatedEventName, updatedEventAttendees, updatedStartDate,
					updatedEndDate, updatedStartTime, updatedEndTime, updatedDescriptionBox, updatedAllDayToggle,
					updatedEventAttendeesToRemove)) {
				log(LogStatus.INFO, "-----Event Updated Msg is showing, So Event of Title: " + eventTitle
						+ " has been Updated-----", YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Verify Event: " + updatedEventName + " in Interaction Section---------",
						YesNo.No);
				if (lp.clickOnTab(projectName, tabObj2)) {

					log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

					if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
						log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
						ThreadSleep(4000);
						if (BP.clicktabOnPage("Acuity")) {
							log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

							if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
								log(LogStatus.INFO, "Event Detail Page has not been open for Record: " + eventTitle
										+ " in case of User 1 in Detail Page: " + recordName, YesNo.No);

							} else {
								log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitle
										+ " in case of User 1 in Detail Page: " + recordName, YesNo.Yes);
								sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitle
										+ " in case of User 1 in Detail Page: " + recordName);
								driver.close();
								CommonLib.ThreadSleep(3000);
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

							if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(updatedEventName)) {
								log(LogStatus.INFO, "Event Detail Page has been open for Record: " + updatedEventName,
										YesNo.No);

								CommonLib.ThreadSleep(8000);
								List<String> eventDetailPageNegativeResult = BP
										.fieldValueVerification(labelAndValueSeprateByBreak);

								if (eventDetailPageNegativeResult.isEmpty()) {
									log(LogStatus.PASS,
											"------" + updatedEventName
													+ " labels and their values in Detail page has been verified------",
											YesNo.No);

								} else {
									log(LogStatus.ERROR, "------" + updatedEventName
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ eventDetailPageNegativeResult + "------", YesNo.No);
									sa.assertTrue(false, "------" + updatedEventName
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ eventDetailPageNegativeResult + "------");

								}

							} else {
								log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + updatedEventName,
										YesNo.Yes);
								sa.assertTrue(false, "Record Detail Page has not open for Record: " + updatedEventName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
							sa.assertTrue(false, "Not able to click on Acuity Tab");
						}

					} else {
						log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
						sa.assertTrue(false, "Not able to open " + recordName + " record");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
					sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
				}

			} else {
				log(LogStatus.ERROR, "-----Event Updated Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been Updated-----", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Event Updated Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been Updated-----");
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityMNNRTc117_VerifyWhenPrivateEventIsCreatedInOutllookHavingOrganizerAsInvitee(String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Outlook Private Event";
		String eventAttendees = crmUser1EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0);

		String startTime = "08:00 AM";
		String endTime = "09:00 AM";
		String descriptionBox = "Revenue Grid Event";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);
			if (home.globalSearchAndNavigate(eventTitle, "Events", true)) {

				log(LogStatus.INFO, "-----Verified No Event named: " + eventTitle + " found in Events Object-----",
						YesNo.No);
			} else {

				log(LogStatus.ERROR, "-----Event named: " + eventTitle + " found in Event Object-----", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Event named: " + eventTitle + " found in Event Object-----");

			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityMNNRTc119_VerifyWhenEventIsCreatedInOutlookByTaggingTheContactsAndAccountNotCreatedInSalesforce(
			String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String eventTitle = "Outlook Event Test";
		String eventAttendees = crmUser1EmailID + "<Break>" + crmUser2EmailID + "<Break>" + "cont1.test@zxc.com"
				+ "<Break>" + "cont2+test@zxc.com";
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0);

		String startTime = "08:00 AM";
		String endTime = "09:00 AM";
		String descriptionBox = "Added the event in outlook to tag users and Contacts Con1";
		String contact1 = "cont1 test";
		String account1 = "zxc";
		String[] labelAndValueSeprateByBreak1 = { "Name" + "<break>" + contact1, "Legal Name" + "<break>" + account1,
				"Email" + "<break>" + "cont1.test@zxc.com" };

		String contact2 = "cont2 test";
		String account2 = "zxc";
		String[] labelAndValueSeprateByBreak2 = { "Name" + "<break>" + contact2, "Legal Name" + "<break>" + account2,
				"Email" + "<break>" + "cont2+test@zxc.com" };

		String[] labelAndValueSeprateByBreakInCaseOfEvent = {
				"Assigned To" + "<break>" + crmUser1FirstName + " " + crmUser1LastName,
				"Subject" + "<break>" + eventTitle, "Name" + "<break>" + contact1,
				"Related Associations" + "<break>" + "Assign Multiple Associations" };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);
			if (home.globalSearchAndNavigate(contact1, "Contacts", false)) {

				log(LogStatus.INFO, "-----Verified Contact named: " + contact1 + " found in Contacts Object-----",
						YesNo.No);

				if (BP.recordDetailPageHeader(contact1, 15) != null) {
					log(LogStatus.INFO, "Record Detail Page has Opened for Record: " + contact1, YesNo.No);
					CommonLib.ThreadSleep(8000);
					List<String> contactDetailPageNegativeResult = BP
							.fieldValueVerification(labelAndValueSeprateByBreak1);

					if (contactDetailPageNegativeResult.isEmpty()) {
						log(LogStatus.PASS,
								"------" + contact1 + " labels and their values in Detail page has been verified------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"------" + contact1
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ contactDetailPageNegativeResult + "------",
								YesNo.No);
						sa.assertTrue(false,
								"------" + contact1
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ contactDetailPageNegativeResult + "------");

					}
				} else {

					log(LogStatus.ERROR, "Record Detail Page has not Opened for Record: " + contact1, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Record Detail Page has not Opened for Record: " + contact1);

				}

			} else {

				log(LogStatus.ERROR, "-----Contact named: " + contact1 + " not found in Contacts Object-----",
						YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Contact named: " + contact1 + " not found in Contacts Object-----");

			}

			CommonLib.refresh(driver);
			if (home.globalSearchAndNavigate(contact2, "Contacts", false)) {

				log(LogStatus.INFO, "-----Verified Contact named: " + contact2 + " found in Contacts Object-----",
						YesNo.No);

				if (BP.recordDetailPageHeader(contact2, 15) != null) {
					log(LogStatus.INFO, "Record Detail Page has Opened for Record: " + contact2, YesNo.No);
					CommonLib.ThreadSleep(8000);
					List<String> contactDetailPageNegativeResult = BP
							.fieldValueVerification(labelAndValueSeprateByBreak2);

					if (contactDetailPageNegativeResult.isEmpty()) {
						log(LogStatus.PASS,
								"------" + contact2 + " labels and their values in Detail page has been verified------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"------" + contact2
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ contactDetailPageNegativeResult + "------",
								YesNo.No);
						sa.assertTrue(false,
								"------" + contact2
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ contactDetailPageNegativeResult + "------");

					}
				} else {

					log(LogStatus.ERROR, "Record Detail Page has not Opened for Record: " + contact2, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Record Detail Page has not Opened for Record: " + contact2);

				}

			} else {

				log(LogStatus.ERROR, "-----Contact named: " + contact2 + " not found in Contacts Object-----",
						YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Contact named: " + contact2 + " not found in Contacts Object-----");

			}

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in " + contact1 + " Record---------",
					YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contact1, 30)) {
					log(LogStatus.INFO, contact1 + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);
							CommonLib.ThreadSleep(8000);
							List<String> eventDetailPageNegativeResult = BP
									.fieldValueVerification(labelAndValueSeprateByBreakInCaseOfEvent);

							if (eventDetailPageNegativeResult.isEmpty()) {
								log(LogStatus.PASS,
										"------" + eventTitle
												+ " labels and their values in Detail page has been verified------",
										YesNo.No);

								driver.close();
								CommonLib.ThreadSleep(3000);

								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							} else {
								log(LogStatus.ERROR, "------" + eventTitle
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ eventDetailPageNegativeResult + "------", YesNo.No);
								sa.assertTrue(false, "------" + eventTitle
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ eventDetailPageNegativeResult + "------");

								driver.close();
								CommonLib.ThreadSleep(3000);

								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							}
						} else {
							log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitle
									+ " in Detail Page: " + contact1, YesNo.Yes);
							sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitle
									+ " in Detail Page: " + contact1);

						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contact1 + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contact1 + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityMNNRTc120_VerifyWhenEventIsCreatedInOutlookByTaggingTheContactsAndAccountWhereInOnlyAccountIsCreatedInSalesforceContact(
			String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String eventTitle = "Outlook Event Test Firm";
		String eventAttendees = crmUser1EmailID + "<Break>" + crmUser2EmailID + "<Break>" + "cont3.test@sumologic.com"
				+ "<Break>" + "cont4+test@sumologic.com";
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0);

		String startTime = "08:00 AM";
		String endTime = "09:00 AM";
		String descriptionBox = "Added the event in outlook to tag users and Contacts Con1";
		String contact1 = "cont3 test";
		String account1 = "Sumo Logic";
		String[] labelAndValueSeprateByBreak1 = { "Name" + "<break>" + contact1, "Legal Name" + "<break>" + account1,
				"Email" + "<break>" + "cont3.test@sumologic.com" };

		String contact2 = "cont4 test";
		String account2 = "Sumo Logic";
		String[] labelAndValueSeprateByBreak2 = { "Name" + "<break>" + contact2, "Legal Name" + "<break>" + account2,
				"Email" + "<break>" + "cont4+test@sumologic.com" };

		String[] labelAndValueSeprateByBreakInCaseOfEvent = {
				"Assigned To" + "<break>" + crmUser1FirstName + " " + crmUser1LastName,
				"Subject" + "<break>" + eventTitle, "Name" + "<break>" + contact1,
				"Related Associations" + "<break>" + "Assign Multiple Associations" };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);
			if (home.globalSearchAndNavigate(contact1, "Contacts", false)) {

				log(LogStatus.INFO, "-----Verified Contact named: " + contact1 + " found in Contacts Object-----",
						YesNo.No);

				if (BP.recordDetailPageHeader(contact1, 15) != null) {
					log(LogStatus.INFO, "Record Detail Page has Opened for Record: " + contact1, YesNo.No);
					CommonLib.ThreadSleep(8000);
					List<String> contactDetailPageNegativeResult = BP
							.fieldValueVerification(labelAndValueSeprateByBreak1);

					if (contactDetailPageNegativeResult.isEmpty()) {
						log(LogStatus.PASS,
								"------" + contact1 + " labels and their values in Detail page has been verified------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"------" + contact1
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ contactDetailPageNegativeResult + "------",
								YesNo.No);
						sa.assertTrue(false,
								"------" + contact1
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ contactDetailPageNegativeResult + "------");

					}
				} else {

					log(LogStatus.ERROR, "Record Detail Page has not Opened for Record: " + contact1, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Record Detail Page has not Opened for Record: " + contact1);

				}

			} else {

				log(LogStatus.ERROR, "-----Contact named: " + contact1 + " not found in Contacts Object-----",
						YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Contact named: " + contact1 + " not found in Contacts Object-----");

			}

			CommonLib.refresh(driver);
			if (home.globalSearchAndNavigate(contact2, "Contacts", false)) {

				log(LogStatus.INFO, "-----Verified Contact named: " + contact2 + " found in Contacts Object-----",
						YesNo.No);

				if (BP.recordDetailPageHeader(contact2, 15) != null) {
					log(LogStatus.INFO, "Record Detail Page has Opened for Record: " + contact2, YesNo.No);
					CommonLib.ThreadSleep(8000);
					List<String> contactDetailPageNegativeResult = BP
							.fieldValueVerification(labelAndValueSeprateByBreak2);

					if (contactDetailPageNegativeResult.isEmpty()) {
						log(LogStatus.PASS,
								"------" + contact2 + " labels and their values in Detail page has been verified------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"------" + contact2
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ contactDetailPageNegativeResult + "------",
								YesNo.No);
						sa.assertTrue(false,
								"------" + contact2
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ contactDetailPageNegativeResult + "------");

					}
				} else {

					log(LogStatus.ERROR, "Record Detail Page has not Opened for Record: " + contact2, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Record Detail Page has not Opened for Record: " + contact2);

				}

			} else {

				log(LogStatus.ERROR, "-----Contact named: " + contact2 + " not found in Contacts Object-----",
						YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Contact named: " + contact2 + " not found in Contacts Object-----");

			}

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in " + contact1 + " Record---------",
					YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contact1, 30)) {
					log(LogStatus.INFO, contact1 + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);
							CommonLib.ThreadSleep(8000);
							List<String> eventDetailPageNegativeResult = BP
									.fieldValueVerification(labelAndValueSeprateByBreakInCaseOfEvent);

							if (eventDetailPageNegativeResult.isEmpty()) {
								log(LogStatus.PASS,
										"------" + eventTitle
												+ " labels and their values in Detail page has been verified------",
										YesNo.No);

								driver.close();
								CommonLib.ThreadSleep(3000);

								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							} else {
								log(LogStatus.ERROR, "------" + eventTitle
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ eventDetailPageNegativeResult + "------", YesNo.No);
								sa.assertTrue(false, "------" + eventTitle
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ eventDetailPageNegativeResult + "------");

								driver.close();
								CommonLib.ThreadSleep(3000);

								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							}
						} else {
							log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitle
									+ " in Detail Page: " + contact1, YesNo.Yes);
							sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitle
									+ " in Detail Page: " + contact1);

						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contact1 + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contact1 + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	/*
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * AcuityMNNRTc121_VerifyTheImpactOnSalesforceCalendarWhenEventIsCancelledFromOutlook
	 * (String projectName) {
	 * 
	 * OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * 
	 * String eventTitle = "Outlook Event Test Cancel"; String eventAttendees =
	 * crmUser1EmailID + "<Break>" + crmUser2EmailID + "<Break>" +
	 * "Dealroom1.3+Max@gmail.com<Break>Dealroom1.3+Martha@gmail.com" + "<Break>" +
	 * "Dealroom1.3+Litz@gmail.com"; String startDate =
	 * CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0); String
	 * endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0);
	 * 
	 * String startTime = "08:00"; String endTime = "09:00"; String descriptionBox =
	 * "Added the event in outlook to tag users and Contacts Con1"; String
	 * endDateInAnotherForm1 = CommonLib.convertDateFromOneFormatToAnother(endDate,
	 * "dd/MM/yyyy", "m/d/yyyy"); String endDateInAnotherForm2 =
	 * CommonLib.convertDateFromOneFormatToAnother(endDate, "dd/MM/yyyy",
	 * "MMMM yyyy");
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword);
	 * 
	 * if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email,
	 * rgOutLookUser1Password, eventTitle, eventAttendees, startDate, endDate,
	 * startTime, endTime, descriptionBox, false)) { log(LogStatus.INFO,
	 * "-----Event Created Msg is showing, So Event of Title: " + eventTitle +
	 * " has been created-----", YesNo.No);
	 * 
	 * if (op.loginNavigateAndUpdateTheEvent(rgOutLookUser1Email,
	 * rgOutLookUser1Password, endDateInAnotherForm1, endDateInAnotherForm2,
	 * eventTitle, true, null, null, null, null, null, null, null, false, null)) {
	 * log(LogStatus.INFO, "-----Event Canceled Msg is showing, So Event of Title: "
	 * + eventTitle + " has been Canceled-----", YesNo.No);
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "-----Event Canceled Msg is not showing, So Event of Title: " + eventTitle +
	 * " has not been Canceled-----", YesNo.Yes); BaseLib.sa.assertTrue(false,
	 * "-----Event Canceled Msg is not showing, So Event of Title: " + eventTitle +
	 * " has not been Canceled-----"); }
	 * 
	 * }
	 * 
	 * else { log(LogStatus.ERROR,
	 * "-----Event Created Msg is not showing, So Event of Title: " + eventTitle +
	 * " has not been created-----", YesNo.Yes); BaseLib.sa.assertTrue(false,
	 * "-----Event Created Msg is not showing, So Event of Title: " + eventTitle +
	 * " has not been created-----"); }
	 * 
	 * ThreadSleep(5000); lp.CRMlogout(); sa.assertAll(); }
	 */

}