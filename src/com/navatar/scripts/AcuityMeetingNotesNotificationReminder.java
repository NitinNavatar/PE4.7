
package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.IconType;
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
	public void AcuityMNNRTc001_1_CreateCRMUser1(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
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
	public void AcuityMNNRTc001_2_CreateCRMUser2(String projectName) {
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
	public void AcuityMNNRTc001_3_CreateCRMUser3(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser3LastName);
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
	public void AcuityMNNRTc001_4_CreateCRMUser4(String projectName) {
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
	public void AcuityMNNRTc001_CreateTheRecordsFromDataSheetAndVerifyInTheOrg(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);

		String[] fundNames = AMNNR_FundNames1.split("<Break>", -1);
		String[] fundTypes = AMNNR_FundTypes1.split("<Break>", -1);
		String[] investmentCategories = AMNNR_FundInvestmentCategories1.split("<Break>", -1);
		String otherLabelFields = null;
		String otherLabelValues = null;

		String[] fundraisingNames = AMNNR_FundraisingNames1.split("<Break>", -1);
		String[] fundraisingsFundName = AMNNR_FundraisingFundName1.split("<Break>", -1);
		String[] fundraisingsInstitutionName = AMNNR_FundraisingInstitutionName1.split("<Break>", -1);

		String dealRecordTypes = null;
		String[] dealName = AMNNR_DealName1.split("<Break>", -1);
		String[] dealCompany = AMNNR_DealCompany1.split("<Break>", -1);
		String[] dealStage = AMNNR_DealStage1.split("<Break>", -1);

		String tabName = AMNNR_CustomObjectTab1;
		String textBoxRecordLabel = AMNNR_CustomObjectField1;
		String[] textBoxRecordNames = AMNNR_CustomObjectRecord1.split("<Break>", -1);

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
	public void AcuityMNNRTc002_VerifyTheUIOfMeetingNotesPopUpFromAddNoteButtonPlacedOnAcuityTabInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String task1ButtonName = AMNNR_ActivityType1;

		String task1SubjectName = "";
		String task1Notes = "";

		String getAdvanceDueDate = "";
		String priority = "Normal";
		String status = "Not Started";

		String taskSectionSubject = "";
		String taskSectionStatus = "Not Started";
		String taskSectionDueDateOnly = "";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1TaskSection = { { "Subject", taskSectionSubject },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", taskSectionStatus },
				{ "Due Date", taskSectionDueDateOnly } };

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

					String expectedHeaderName = "Task";
					if (BP.notePopUpHeading(expectedHeaderName, 15) != null) {
						log(LogStatus.INFO, "PopUp Name has been verified to: " + expectedHeaderName, YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "PopUp Name has been not been verified, Expected: " + expectedHeaderName,
								YesNo.No);
						sa.assertTrue(false, "PopUp Name has been not been verified, Expected: " + expectedHeaderName);
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
	public void AcuityMNNRTc003_CreateATaskAndAddTheNotesFromEditCommentButtonOfTaskLayout(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("1"));

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject2;
		String task1Notes = AMNNR_Notes2;
		String relatedTo = AMNNR_RelatedTo2;
		String priority = AMNNR_AdvancePriority2;
		String status = AMNNR_AdvanceStatus2;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };
		String task1ButtonName = AMNNR_ActivityType1;
		String recordName = AMNNR_FirmLegalName1;
		String recordType = AMNNR_FirmRecordType1;
		String updatedCommentOfTask = AMNNR_Notes3;
		String[] relatedToVerify = AMNNR_ATRelatedTo1.split("<break>");
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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
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

					if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

						log(LogStatus.INFO,
								"-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
								YesNo.No);

						if (taskBP.editCommentsIntask(task1SubjectName, updatedCommentOfTask)) {
							log(LogStatus.INFO, "Updated Comment of Task: " + task1SubjectName + " verified: "
									+ updatedCommentOfTask, YesNo.No);

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

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, IconType.Task, task1SubjectName,
												updatedCommentOfTask, true, false, relatedToVerify, null);
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

											ThreadSleep(1000);
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
							log(LogStatus.ERROR, "Updated Comment of Task: " + task1SubjectName + " is not verified: "
									+ updatedCommentOfTask, YesNo.No);
							sa.assertTrue(false, "Updated Comment of Task: " + task1SubjectName + " is not verified: "
									+ updatedCommentOfTask);

						}

					} else {

						log(LogStatus.ERROR, "-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
								YesNo.Yes);
						BaseLib.sa.assertTrue(false,
								"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

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
	public void AcuityMNNRTc004_CreateATaskAndAddTheNotesAndVerifySuggestedTagPopUp(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("0"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = AMNNR_Subject4;
		String task1Notes = AMNNR_Notes4;
		String relatedTo = AMNNR_RelatedTo4;

		String priority = AMNNR_AdvancePriority4;
		String status = AMNNR_AdvanceStatus4;
		String task1ButtonName = AMNNR_ActivityType1;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[] SuggestedTags = AMNNR_SuggestedTag4.split("<break>", -1);

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

		String recordName = AMNNR_FirmLegalName2;
		String recordType = AMNNR_FirmRecordType2;
		String updatedNotesOfTask = AMNNR_Notes5;
		String[] relatedToVerify = AMNNR_ATRelatedTo2.split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag5.split("<break>", -1);
		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo3.split("<break>");

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

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", AMNNR_SuggestedTag4 } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						ArrayList<String> subjectLinkPopUpNegativeResult = BP.verifySubjectLinkPopUpOnIntraction(driver,
								task1SubjectName, task1BasicSection, task1AdvancedSection, IconType.Task, PageName.AcuityDetails);

						if (subjectLinkPopUpNegativeResult.isEmpty()) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to open popup after click on it and verify its data" + "------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------",
									YesNo.Yes);
							sa.assertTrue(false,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------");

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
												getAdvanceDueDate, IconType.Task, task1SubjectName, updatedNotesOfTask,
												true, false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

											ArrayList<String> subjectLinkPopUpNegativeResultUpdated = BP
													.verifySubjectLinkPopUpOnIntraction(driver, task1SubjectName,
															updatedTask1BasicSectionVerification,
															updatedTask1AdvancedSectionVerification, IconType.Task, PageName.AcuityDetails);

											if (subjectLinkPopUpNegativeResultUpdated.isEmpty()) {
												log(LogStatus.PASS, "------" + task1SubjectName
														+ " record is able to open popup after click on it and verify its data"
														+ "------", YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"------" + task1SubjectName
																+ " record link popup is not verified, Reason: "
																+ subjectLinkPopUpNegativeResultUpdated + "------",
														YesNo.Yes);
												sa.assertTrue(false,
														"------" + task1SubjectName
																+ " record link popup is not verified, Reason: "
																+ subjectLinkPopUpNegativeResultUpdated + "------");

											}

											String url2 = getURL(driver, 10);

											if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
													"Edit Note Button of: " + task1SubjectName,
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

												ThreadSleep(10000);

												String expectedHeaderName = "Task";
												if (BP.notePopUpHeading(expectedHeaderName, 15) != null) {
													log(LogStatus.INFO,
															"PopUp Name has been verified to: " + expectedHeaderName,
															YesNo.No);
												}

												else {
													log(LogStatus.ERROR,
															"PopUp Name has been not been verified, Expected: "
																	+ expectedHeaderName,
															YesNo.No);
													sa.assertTrue(false,
															"PopUp Name has been not been verified, Expected: "
																	+ expectedHeaderName);
												}

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
	public void AcuityMNNRTc005_CreateATaskWithoutMeetingNotesAndTagFromInteractionSectionByClickingOnEditNotesButton(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("-1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = AMNNR_Subject6;
		String task1Notes = AMNNR_Notes6;
		String relatedTo = AMNNR_RelatedTo6;
		String[] relatedToArray = relatedTo.split("<break>", -1);
		String priority = AMNNR_AdvancePriority6;
		String status = AMNNR_AdvanceStatus6;
		String task1ButtonName = AMNNR_ActivityType1;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_Contact1;

		String updatedNotesOfTask = AMNNR_Notes7;
		String[] relatedToVerify = AMNNR_ATRelatedTo4.split("<break>");
		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo5.split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag7.split("<break>", -1);

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
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						ArrayList<String> subjectLinkPopUpNegativeResult = BP.verifySubjectLinkPopUpOnIntraction(driver,
								task1SubjectName, task1BasicSection, task1AdvancedSection, IconType.Task, PageName.AcuityDetails);

						if (subjectLinkPopUpNegativeResult.isEmpty()) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to open popup after click on it and verify its data" + "------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------",
									YesNo.Yes);
							sa.assertTrue(false,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------");

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
												getAdvanceDueDate, IconType.Task, task1SubjectName, updatedNotesOfTask,
												true, false, updatedRelatedToVerify, null);
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
	public void AcuityMNNRTc006_CreateATaskWithoutMeetingNotesAndAddTheNotesFromEditButton(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("-1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = AMNNR_Subject8;
		String task1Notes = AMNNR_Notes8;
		String relatedTo = AMNNR_RelatedTo8;
		String priority = AMNNR_AdvancePriority8;
		String status = AMNNR_AdvanceStatus8;
		String task1ButtonName = AMNNR_ActivityType1;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_Contact2;

		String updatedNotesOfTask = AMNNR_Notes9;
		String[] relatedToVerify = AMNNR_ATRelatedTo6.split("<break>");
		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo7.split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag9.split("<break>", -1);

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
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

							log(LogStatus.INFO,
									"-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
									YesNo.No);
							if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

								if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
										"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);

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
										ThreadSleep(2000);

										if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
												null, updatedSuggestedTags, null)) {
											log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

											CommonLib.refresh(driver);

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

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											if (lp.clickOnTab(projectName, tabObj2)) {

												log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

												if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
														TabName.ContactTab, recordName, 30)) {
													log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
													ThreadSleep(4000);
													if (BP.clicktabOnPage("Acuity")) {
														log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

														ArrayList<String> updatedresult = BP
																.verifyRecordOnInteractionCard(getAdvanceDueDate,
																		IconType.Task, task1SubjectName,
																		updatedNotesOfTask, true, false,
																		updatedRelatedToVerify, null);
														if (updatedresult.isEmpty()) {
															log(LogStatus.PASS, "------" + task1SubjectName
																	+ " record has been verified on intraction------",
																	YesNo.No);

														} else {
															log(LogStatus.ERROR, "------" + task1SubjectName
																	+ " record is not verified on intraction, Reason: "
																	+ updatedresult + "------", YesNo.No);
															sa.assertTrue(false, "------" + task1SubjectName
																	+ " record is not verified on intraction, Reason: "
																	+ updatedresult + "------");
														}

													} else {
														log(LogStatus.ERROR, "Not able to click on Acuity Tab",
																YesNo.No);
														sa.assertTrue(false, "Not able to click on Acuity Tab");
													}

												} else {
													log(LogStatus.ERROR, "Not able to open " + recordName + " record",
															YesNo.No);
													sa.assertTrue(false, "Not able to open " + recordName + " record");
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
												sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
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
									log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
											YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Not Able Click on Edit button in Down Arrow Button");

								}

							} else {
								log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Not Able Click on Down Arrow Button");

							}

						} else {

							log(LogStatus.ERROR,
									"-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
									YesNo.Yes);
							BaseLib.sa.assertTrue(false,
									"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

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
	public void AcuityMNNRTc007_CreateATaskWithMeetingNotesAndAddTheNotesFromEditButtonOfTaskLayout(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("2"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = AMNNR_Subject10;
		String task1Notes = AMNNR_Notes10;
		String relatedTo = AMNNR_RelatedTo10;
		String priority = AMNNR_AdvancePriority10;
		String status = AMNNR_AdvanceStatus10;
		String task1ButtonName = AMNNR_ActivityType1;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };
		String[] suggestedTags = AMNNR_SuggestedTag10.split("<break>", -1);

		String RelatedToVerify = relatedTo + AMNNR_RelatedTo11;
		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", RelatedToVerify } };

		String recordName = AMNNR_Contact3;

		String updatedNotesOfTask = AMNNR_Notes11;
		String[] relatedToVerify = AMNNR_ATRelatedTo8.split("<break>");
		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo9.split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag11.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = RelatedToVerify + AMNNR_RelatedTo12;
		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

							log(LogStatus.INFO,
									"-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
									YesNo.No);
							if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

								if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
										"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);

									String url = getURL(driver, 10);

									ThreadSleep(10000);
									ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url,
													task1BasicSectionVerification, task1AdvancedSection, null);
									if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
										log(LogStatus.INFO,
												"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
												YesNo.No);

										refresh(driver);
										ThreadSleep(10000);

										if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
												null, updatedSuggestedTags, null)) {
											log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

											CommonLib.refresh(driver);

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

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											if (lp.clickOnTab(projectName, tabObj2)) {

												log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

												if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
														TabName.ContactTab, recordName, 30)) {
													log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
													ThreadSleep(4000);
													if (BP.clicktabOnPage("Acuity")) {
														log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

														ArrayList<String> updatedresult = BP
																.verifyRecordOnInteractionCard(getAdvanceDueDate,
																		IconType.Task, task1SubjectName,
																		updatedNotesOfTask, true, false,
																		updatedRelatedToVerify, null);
														if (updatedresult.isEmpty()) {
															log(LogStatus.PASS, "------" + task1SubjectName
																	+ " record has been verified on intraction------",
																	YesNo.No);

														} else {
															log(LogStatus.ERROR, "------" + task1SubjectName
																	+ " record is not verified on intraction, Reason: "
																	+ updatedresult + "------", YesNo.No);
															sa.assertTrue(false, "------" + task1SubjectName
																	+ " record is not verified on intraction, Reason: "
																	+ updatedresult + "------");
														}

													} else {
														log(LogStatus.ERROR, "Not able to click on Acuity Tab",
																YesNo.No);
														sa.assertTrue(false, "Not able to click on Acuity Tab");
													}

												} else {
													log(LogStatus.ERROR, "Not able to open " + recordName + " record",
															YesNo.No);
													sa.assertTrue(false, "Not able to open " + recordName + " record");
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
												sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
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
									log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
											YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Not Able Click on Edit button in Down Arrow Button");

								}

							} else {
								log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Not Able Click on Down Arrow Button");

							}

						} else {

							log(LogStatus.ERROR,
									"-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
									YesNo.Yes);
							BaseLib.sa.assertTrue(false,
									"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

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
	public void AcuityMNNRTc008_CreateATaskWithMeetingNotesAndUpdateTheNotesFromEditNoteButtonOnInteractionSectionOfAcuityTab(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = AMNNR_Subject13;
		String task1Notes = AMNNR_Notes13;
		String relatedTo = AMNNR_RelatedTo13;

		String priority = AMNNR_AdvancePriority13;
		String status = AMNNR_AdvanceStatus13;
		String task1ButtonName = AMNNR_ActivityType1;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };
		String[] suggestedTags = null;

		String recordName = AMNNR_Contact4;

		String updatedNotesOfTask = AMNNR_Notes14;
		String[] relatedToVerify = AMNNR_ATRelatedTo10.split("<break>");
		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo11.split("<break>");

		String updatedRelatedTo = relatedTo + AMNNR_RelatedTo14;
		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag14.split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						ArrayList<String> subjectLinkPopUpNegativeResult = BP.verifySubjectLinkPopUpOnIntraction(driver,
								task1SubjectName, task1BasicSection, task1AdvancedSection, IconType.Task, PageName.AcuityDetails);

						if (subjectLinkPopUpNegativeResult.isEmpty()) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to open popup after click on it and verify its data" + "------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------",
									YesNo.Yes);
							sa.assertTrue(false,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------");

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
												getAdvanceDueDate, IconType.Task, task1SubjectName, updatedNotesOfTask,
												true, false, updatedRelatedToVerify, null);
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
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc009_CreateATaskAndVerifyTaskHyperlinkUpdateTheTaskSubjectAndVerifyTheTaskHyperlink(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = AMNNR_Subject15;
		String task1UpdatedSubjectName = AMNNR_Subject16;
		String task1Notes = AMNNR_Notes15;

		String relatedTo = AMNNR_RelatedTo15;

		String priority = AMNNR_AdvancePriority15;
		String status = AMNNR_AdvanceStatus15;
		String task1ButtonName = AMNNR_ActivityType1;
		String getAdvanceDueDateInTaskSection = "";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[][] tasksSectionVerificationData = { { "Subject", task1SubjectName },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Due Date", getAdvanceDueDateInTaskSection } };

		String[][] followUpTask1AdvancedSectionVerificationInNotesPopup = {
				{ "Due Date", getAdvanceDueDateInTaskSection }, { "User", crmUser1FirstName + " " + crmUser1LastName },
				{ "Status", "Not Started" }, { "Priority", priority } };

		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1UpdatedSubjectName },
				{ "Notes", task1Notes }, { "Related_To", relatedTo } };

		String[][] updateFollowUpTask1AdvancedSection = { { "Due Date", getAdvanceDueDate } };

		String[] suggestedTags = null;

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] relatedToVerify = AMNNR_ATRelatedTo12.split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Task: " + task1UpdatedSubjectName + " and followUp task: "
				+ task1SubjectName + " in Activity Timeline Section---------", YesNo.No);

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

					ArrayList<String> result2 = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
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

					ThreadSleep(10000);
					if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

						log(LogStatus.INFO,
								"-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
								YesNo.No);
						if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

							if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
									"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);

								String url = getURL(driver, 10);

								ThreadSleep(5000);
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
													+ task1SubjectName + ", Reason: "
													+ NotesPopUpPrefilledNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value for: "
													+ task1SubjectName + ", Reason: "
													+ NotesPopUpPrefilledNegativeResult);

								}
								CommonLib.refresh(driver);

								CommonLib.ThreadSleep(5000);

								if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName, null,
										updateFollowUpTask1AdvancedSection, null, null, null, false, false)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");
								}

							} else {
								log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button", YesNo.Yes);

								sa.assertTrue(false, "Not Able Click on Edit button in Down Arrow Button");

							}

						} else {
							log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
							sa.assertTrue(false, "Not Able Click on Down Arrow Button");

						}
					} else {

						log(LogStatus.ERROR, "-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
								YesNo.Yes);
						BaseLib.sa.assertTrue(false,
								"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

					}

					if (lp.clickOnTab(projectName, tabObj1)) {

						log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

						if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType,
								recordName, 30)) {
							log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open",
									YesNo.No);
							ThreadSleep(4000);
							if (BP.clicktabOnPage("Acuity")) {
								log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

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
									log(LogStatus.ERROR,
											"Not able to click on Edit Note button of " + task1UpdatedSubjectName,
											YesNo.No);
									sa.assertTrue(false,
											"Not able to click on Edit Note button of " + task1UpdatedSubjectName);
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
	public void AcuityMNNRTc010_CreateATaskWithMeetingNotesAndUpdateTheNotesForFollowUpTasksWhenCreatedMultipleFollowUpTasks(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("2"));

		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AMNNR_Activity017", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = AMNNR_Subject17;
		String task1Notes = AMNNR_Notes17;
		String relatedTo = AMNNR_RelatedTo17;

		String priority = AMNNR_AdvancePriority17;
		String status = AMNNR_AdvanceStatus17;
		String task1ButtonName = AMNNR_ActivityType1;
		String[] suggestedTags = "".split("<break>", -1);

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;
		String task1UpdateTaskSection1Subject = AMNNR_Subject18;
		String task1UpdateTaskSection1DueDateOnly = AdvanceDueDate;
		String task1UpdateTaskSection2Subject = AMNNR_Subject19;
		String task1UpdateTaskSection2DueDateOnly = AdvanceDueDate;
		String task1UpdateTaskSection3Subject = AMNNR_Subject20;
		String task1UpdateTaskSection3DueDateOnly = AdvanceDueDate;

		String updatedNotesOfTask = AMNNR_Notes21;
		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[][] task1UpdateTaskSection1 = { { "Subject", task1UpdateTaskSection1Subject },
				{ "Due Date", task1UpdateTaskSection1DueDateOnly } };
		String[][] task1UpdateTaskSection2 = { { "Subject", task1UpdateTaskSection2Subject },
				{ "Due Date", task1UpdateTaskSection2DueDateOnly } };
		String[][] task1UpdateTaskSection3 = { { "Subject", task1UpdateTaskSection3Subject },
				{ "Due Date", task1UpdateTaskSection3DueDateOnly } };
		String[][][] task1UpdateTaskSection = { task1UpdateTaskSection1, task1UpdateTaskSection2,
				task1UpdateTaskSection3 };

		String[] relatedToVerify = (crmUser1FirstName + " " + crmUser1LastName + AMNNR_ATRelatedTo13).split("<break>");
		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo14.split("<break>", -1);

		String updatedRelatedTo = relatedTo + AMNNR_RelatedTo21;

		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };

		String[][] followUptask1BasicSectionVerification = { { "Subject", task1UpdateTaskSection1Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[][] followUptask2BasicSectionVerification = { { "Subject", task1UpdateTaskSection2Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[][] followUptask3BasicSectionVerification = { { "Subject", task1UpdateTaskSection3Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };

		String[][] followUptask1AdvanceSectionVerification = { { "Due Date", task1UpdateTaskSection1DueDateOnly },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] followUptask2AdvanceSectionVerification = { { "Due Date", task1UpdateTaskSection2DueDateOnly },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] followUptask3AdvanceSectionVerification = { { "Due Date", task1UpdateTaskSection3DueDateOnly },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[] updatedSuggestedTags = AMNNR_SuggestedTag21.split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						ArrayList<String> subjectLinkPopUpNegativeResult = BP.verifySubjectLinkPopUpOnIntraction(driver,
								task1SubjectName, task1BasicSection, task1AdvancedSection, IconType.Task, PageName.AcuityDetails);

						if (subjectLinkPopUpNegativeResult.isEmpty()) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to open popup after click on it and verify its data" + "------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------",
									YesNo.Yes);
							sa.assertTrue(false,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------");

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
												getAdvanceDueDate, IconType.Task, task1SubjectName, updatedNotesOfTask,
												true, false, updatedRelatedToVerify, null);
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
												task1UpdateTaskSection2DueDateOnly, IconType.Task,
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
												task1UpdateTaskSection3DueDateOnly, IconType.Task,
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
	public void AcuityMNNRTc011_ClickOnTheTaskSubjectFromInteractionSectionAndAddTheNotesByClickingOnTagButton(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity017", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject17;
		String task1Notes = AMNNR_Notes21;
		String relatedTo = AMNNR_RelatedTo17;

		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo21;

		String priority = AMNNR_AdvancePriority17;
		String status = AMNNR_AdvanceStatus17;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo14.split("<break>");
		String updatedRelatedTo = AMNNR_RelatedTo22;

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String relatedToNotContains = crmUser2FirstName + " " + crmUser2LastName;
		String[][] relatedAssociationNotContains = { { "Related_To", relatedToNotContains } };

		String updatedRelatedToVerifyInNotes = verificationRelatedTo + "<break>" + updatedRelatedTo;

		String[][] task1UpdatedBasicSection = { { "Related_To", updatedRelatedTo } };
		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", updatedRelatedToVerifyInNotes } };

		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo15.split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

							log(LogStatus.INFO,
									"-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
									YesNo.No);
							if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

								if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
										"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);

									String url = getURL(driver, 10);

									ThreadSleep(10000);
									ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url,
													task1BasicSectionVerification, task1AdvancedSectionVerification,
													null);
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

									if (BP.updateActivityTimelineRecord(projectName, task1UpdatedBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

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

										if (lp.clickOnTab(projectName, tabObj1)) {

											log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

											if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
													TabName.InstituitonsTab, recordType, recordName, 30)) {
												log(LogStatus.INFO, recordName + " record of record type " + recordType
														+ " has been open", YesNo.No);
												ThreadSleep(4000);
												if (BP.clicktabOnPage("Acuity")) {
													log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

													ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
															getAdvanceDueDate, IconType.Task, task1SubjectName,
															task1Notes, true, false, updatedRelatedToVerify, null);
													if (updatedresult.isEmpty()) {
														log(LogStatus.PASS, "------" + task1SubjectName
																+ " record has been verified on intraction------",
																YesNo.No);

													} else {
														log(LogStatus.ERROR, "------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------", YesNo.No);
														sa.assertTrue(false, "------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
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
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
											YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Not Able Click on Edit button in Down Arrow Button");

								}

							} else {
								log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Not Able Click on Down Arrow Button");

							}

						} else {

							log(LogStatus.ERROR,
									"-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
									YesNo.Yes);
							BaseLib.sa.assertTrue(false,
									"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

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
	public void AcuityMNNRTc012_CreateATaskWithMeetingNotesAndUpdateTheSameWithRelatedRecordRemoveAndThenVerify(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));

		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AMNNR_Activity023", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject23;
		String task1Notes = AMNNR_Notes23;
		String relatedTo = AMNNR_RelatedTo23;

		String priority = AMNNR_AdvancePriority23;
		String status = AMNNR_AdvanceStatus23;
		String task1ButtonName = AMNNR_ActivityType1;
		String[] suggestedTags = AMNNR_SuggestedTag23.split("<break>", -1);

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo24;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo16.split("<break>");

		String[] updatedRemoveRelatedAssociation = AMNNR_RelatedTo25.split("<break>", -1);

		String[] updatedSuggestedTags = null;

		String verificationUpdatedRelatedTo = AMNNR_RelatedTo26;

		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationUpdatedRelatedTo } };

		String[] updatedRelatedToVerifyInInteraction = AMNNR_ATRelatedTo17.split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
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

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

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

											CommonLib.refresh(driver);
											CommonLib.ThreadSleep(5000);
											ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
													getAdvanceDueDate, IconType.Task, task1SubjectName, task1Notes,
													true, false, updatedRelatedToVerifyInInteraction, null);
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
	public void AcuityMNNRTc013_CreateATaskAndTagContactsAccountsWhichAreNotCreatedInTheOrg(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject27;
		String task1Notes = AMNNR_Notes27;
		String relatedTo = AMNNR_RelatedTo27;

		String priority = AMNNR_AdvancePriority27;
		String status = AMNNR_AdvanceStatus27;
		String task1ButtonName = AMNNR_ActivityType1;
		String[] suggestedTags = AMNNR_SuggestedTag27.split("<break>", -1);
		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo28;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo18.split("<break>");

		String relatedToNotContains = AMNNR_RelatedTo29;
		String[][] relatedAssociationNotContains = { { "Related_To", relatedToNotContains } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
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
											getAdvanceDueDate, IconType.Task, task1SubjectName, task1Notes, true, false,
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
	public void AcuityMNNRTc014_CreateATaskAndTagContact1to50InCommentSectionAndCheckInPopUpContact1To50ShouldGetDisplayWithoutAnyContactGetsMissout(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject30;
		String task1Notes = AMNNR_Notes30;
		String relatedTo = AMNNR_RelatedTo30;

		String priority = AMNNR_AdvancePriority30;
		String status = AMNNR_AdvanceStatus30;
		String task1ButtonName = AMNNR_ActivityType1;
		String[] suggestedTags = null;
		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo19.split("<break>");

		String updatedNotesOfTask = AMNNR_Notes31;

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag31.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = AMNNR_ATRelatedTo20.split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = updatedSuggestedTags;

		String updatedRelatedToVerifyInNotes = (relatedTo + AMNNR_RelatedTo31);

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
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
	public void AcuityMNNRTc015_CreateATaskAndTag13RecordsForRelatedAssociationAnd50Contacts(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject32;
		String task1Notes = AMNNR_Notes32;
		String relatedTo = AMNNR_RelatedTo32;

		String priority = AMNNR_AdvancePriority32;
		String status = AMNNR_AdvanceStatus32;
		String task1ButtonName = AMNNR_ActivityType1;
		String[] suggestedTags = null;
		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName2;
		String recordType = AMNNR_FirmRecordType2;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo21.split("<break>");

		String updatedNotesOfTask = AMNNR_Notes33;

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag33.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = AMNNR_ATRelatedTo22.split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = updatedSuggestedTags;

		String updatedRelatedToVerifyInNotes = (relatedTo + AMNNR_RelatedTo33);

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
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

										ArrayList<String> subjectLinkPopUpNegativeResult = BP
												.verifySubjectLinkPopUpOnIntraction(driver, task1SubjectName,
														updatedTask1BasicSectionVerification,
														updatedTask1AdvancedSectionVerification, IconType.Task, PageName.AcuityDetails);

										if (subjectLinkPopUpNegativeResult.isEmpty()) {
											log(LogStatus.PASS, "------" + task1SubjectName
													+ " record is able to open popup after click on it and verify its data"
													+ "------", YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record link popup is not verified, Reason: "
															+ subjectLinkPopUpNegativeResult + "------",
													YesNo.Yes);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record link popup is not verified, Reason: "
															+ subjectLinkPopUpNegativeResult + "------");

										}

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
													getAdvanceDueDate, IconType.Task, task1SubjectName,
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
	public void AcuityMNNRTc016_CreateATaskWithMeetingNotesByTaggingCustomObjectsInIt(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject34;
		String task1Notes = AMNNR_Notes34;
		String relatedTo = AMNNR_RelatedTo34;

		String priority = AMNNR_AdvancePriority34;
		String status = AMNNR_AdvanceStatus34;
		String task1ButtonName = AMNNR_ActivityType1;
		String[] suggestedTags = AMNNR_SuggestedTag34.split("<break>", -1);
		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo35;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_Contact4;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo23.split("<break>", -1);
		String[] RelatedAssociationVerifyInInteraction = (AMNNR_RelatedTo36).split("<break>", -1);

		String updatedNotesOfTask = AMNNR_Notes35;

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag35.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = AMNNR_ATRelatedTo24.split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = (AMNNR_RelatedTo37).split("<break>", -1);

		String updatedRelatedToVerifyInNotes = (verificationRelatedTo + AMNNR_RelatedTo38);

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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

					ArrayList<String> subjectLinkPopUpNegativeResultBeforeUpdate = BP
							.verifySubjectLinkPopUpOnIntraction(driver, task1SubjectName, task1BasicSectionVerification,
									task1AdvancedSectionVerification, IconType.Task, PageName.AcuityDetails);

					if (subjectLinkPopUpNegativeResultBeforeUpdate.isEmpty()) {
						log(LogStatus.PASS, "------" + task1SubjectName
								+ " record is able to open popup after click on it and verify its data" + "------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"------" + task1SubjectName + " record link popup is not verified, Reason: "
										+ subjectLinkPopUpNegativeResultBeforeUpdate + "------",
								YesNo.Yes);
						sa.assertTrue(false,
								"------" + task1SubjectName + " record link popup is not verified, Reason: "
										+ subjectLinkPopUpNegativeResultBeforeUpdate + "------");

					}

					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction,
							RelatedAssociationVerifyInInteraction);
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

										ArrayList<String> subjectLinkPopUpNegativeResult = BP
												.verifySubjectLinkPopUpOnIntraction(driver, task1SubjectName,
														updatedTask1BasicSectionVerification,
														updatedTask1AdvancedSectionVerification, IconType.Task, PageName.AcuityDetails);

										if (subjectLinkPopUpNegativeResult.isEmpty()) {
											log(LogStatus.PASS, "------" + task1SubjectName
													+ " record is able to open popup after click on it and verify its data"
													+ "------", YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record link popup is not verified, Reason: "
															+ subjectLinkPopUpNegativeResult + "------",
													YesNo.Yes);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record link popup is not verified, Reason: "
															+ subjectLinkPopUpNegativeResult + "------");

										}

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
													getAdvanceDueDate, IconType.Task, task1SubjectName,
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
	public void AcuityMNNRTc017_VerifyRemovingSomeOfTheTaggedFromNotesPopUpAndVerifyTheSameInInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject39;
		String task1Notes = AMNNR_Notes39;
		String relatedTo = AMNNR_RelatedTo39;

		String priority = AMNNR_AdvancePriority39;
		String status = AMNNR_AdvanceStatus39;
		String task1ButtonName = AMNNR_ActivityType1;
		String[] suggestedTags = AMNNR_SuggestedTag39.split("<break>", -1);
		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo40;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo25.split("<break>", -1);

		String updatedNotesOfTask = null;

		String[][] task1UpdateBasicSection = null;
		String[] updatedSuggestedTags = "".split("<break>", -1);
		String[] updatedRemoveRelatedAssociation = AMNNR_ARelatedAsso25.split("<break>", -1);
		String[] updatedRelatedToVerifyInInteraction = AMNNR_ATRelatedTo26.split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = AMNNR_ARelatedAsso26.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = AMNNR_RelatedTo41;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
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
													getAdvanceDueDate, IconType.Task, task1SubjectName,
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
	public void AcuityMNNRTc018_VerifyChangingTheStatusOfTaskFromAdvancedSectionOfNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("0"));
		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AMNNR_Activity042", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject42;
		String task1Notes = AMNNR_Notes42;
		String relatedTo = AMNNR_RelatedTo42;

		String priority = AMNNR_AdvancePriority42;
		String status = AMNNR_AdvanceStatus42;
		String task1ButtonName = AMNNR_ActivityType1;
		String[] suggestedTags = AMNNR_SuggestedTag42.split("<break>", -1);
		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo43;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName4;
		String recordType = AMNNR_FirmRecordType4;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo27.split("<break>", -1);

		String updatedNotesOfTask = task1Notes;
		String updatedStatus = AMNNR_AdvanceStatus43;
		String[][] task1UpdateBasicSection = null;
		String[][] task1UpdateAdvancedSection = { { "Status", updatedStatus } };

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = AMNNR_ARelatedAsso27.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", updatedStatus },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
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
													getAdvanceDueDate, IconType.Task, task1SubjectName,
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
	public void AcuityMNNRTc019_VerifyChangingTheDueDateToFutureOfTaskFromAdvancedSectionOfNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity042", excelLabel.Advance_Due_Date);
		String getUpdatedAdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("1"));
		ExcelUtils.writeData(AcuityDataSheetFilePath, getUpdatedAdvanceDueDate, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity043", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject42;
		String task1Notes = AMNNR_Notes42;
		String relatedTo = AMNNR_ARelatedAsso27;

		String priority = AMNNR_AdvancePriority42;
		String status = AMNNR_AdvanceStatus43;

		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName4;
		String recordType = AMNNR_FirmRecordType4;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo27.split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String[][] task1UpdateBasicSection = null;
		String[][] task1UpdateAdvancedSection = { { "Due Date", getUpdatedAdvanceDueDate } };

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = AMNNR_ARelatedAsso27.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date", getUpdatedAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
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
													getUpdatedAdvanceDueDate, IconType.Task, task1SubjectName,
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
	public void AcuityMNNRTc020_VerifyChangingTheAssigneeOfTaskFromAdvancedSectionOfNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity043", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject42;
		String task1Notes = AMNNR_Notes42;
		String relatedTo = AMNNR_ARelatedAsso27;

		String priority = AMNNR_AdvancePriority42;
		String status = AMNNR_AdvanceStatus43;

		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName4;
		String recordType = AMNNR_FirmRecordType4;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo27.split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String[][] task1UpdateBasicSection = null;
		String[][] task1UpdateAdvancedSection = { { "User", crmUser2FirstName + " " + crmUser2LastName } };

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = AMNNR_ARelatedAsso27.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser2FirstName + " " + crmUser2LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
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
													getAdvanceDueDate, IconType.Task, task1SubjectName,
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
	public void AcuityMNNRTc021_VerifyChangingTheSubjectOfTaskFromNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity043", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject42;
		String task1Notes = AMNNR_Notes42;
		String relatedTo = AMNNR_ARelatedAsso27;

		String priority = AMNNR_AdvancePriority42;
		String status = AMNNR_AdvanceStatus43;

		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser2FirstName + " " + crmUser2LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName4;
		String recordType = AMNNR_FirmRecordType4;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo27.split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String task1UpdatedSubjectName = AMNNR_Subject44;
		String[][] task1UpdateBasicSection = { { "Subject", task1UpdatedSubjectName } };

		String[][] task1UpdateAdvancedSection = null;

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = AMNNR_ARelatedAsso27.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1UpdatedSubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser2FirstName + " " + crmUser2LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
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

						} else {
							log(LogStatus.ERROR,
									"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
											+ NotesPopUpPrefilledNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
											+ NotesPopUpPrefilledNegativeResult);
						}

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
										"Edit Note Button of: " + task1UpdatedSubjectName, action.SCROLLANDBOOLEAN)) {
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

								} else {
									log(LogStatus.ERROR,
											"Not able to click on Edit Note button of Task: " + task1UpdatedSubjectName,
											YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button of Task: "
											+ task1UpdatedSubjectName);
								}

								CommonLib.refresh(driver);

								if (BP.subjectOfInteractionCard(task1SubjectName, 7) == null) {
									log(LogStatus.INFO,
											"Verified: After Update the Name of Subject to " + task1UpdatedSubjectName
													+ ", Previous Named: " + task1SubjectName
													+ " Interaction card should not be there",
											YesNo.No);
								} else {
									log(LogStatus.ERROR,
											"After Update the Name of Subject to " + task1UpdatedSubjectName
													+ ", Previous Named: " + task1SubjectName
													+ " Interaction card is showing, which should not be there",
											YesNo.No);
									sa.assertTrue(false,
											"After Update the Name of Subject to " + task1UpdatedSubjectName
													+ ", Previous Named: " + task1SubjectName
													+ " Interaction card is showing, which should not be there");
								}

								ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(getAdvanceDueDate,
										IconType.Task, task1UpdatedSubjectName, updatedNotesOfTask, true, false,
										updatedRelatedToVerifyInInteraction,
										updatedRelatedAssociationVerifyInInteraction);
								if (updatedresult.isEmpty()) {
									log(LogStatus.PASS, "------" + task1SubjectName
											+ " record has been verified on intraction------", YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"------" + task1SubjectName
													+ " record is not verified on intraction, Reason: " + updatedresult
													+ "------",
											YesNo.No);
									sa.assertTrue(false,
											"------" + task1SubjectName
													+ " record is not verified on intraction, Reason: " + updatedresult
													+ "------");
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
						log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
						sa.assertTrue(false, "Not able to click on Edit Note button");
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
	public void AcuityMNNRTc022_VerifyWhenTheOrgHasSameDealNameAsOfTheCompanyNameAndIsTaggedInTheTask(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("0"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = AMNNR_Subject45;
		String task1Notes = AMNNR_Notes45;
		String relatedTo = AMNNR_RelatedTo45;

		String priority = AMNNR_AdvancePriority45;
		String status = AMNNR_AdvanceStatus45;
		String task1ButtonName = AMNNR_ActivityType1;

		String[] SuggestedTags = AMNNR_SuggestedTag45.split("<break>", -1);

		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo46;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName5;
		String recordType = AMNNR_FirmRecordType5;

		String[] RelatedToVerifyInInteraction = (crmUser1FirstName + " " + crmUser1LastName + AMNNR_ATRelatedTo28)
				.split("<break>", -1);

		String updatedNotesOfTask = AMNNR_Notes46;

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };

		String[] updatedSuggestedTags = AMNNR_SuggestedTag46.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = AMNNR_ATRelatedTo29.split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = null;

		String updatedRelatedToVerifyInNotes = (AMNNR_RelatedTo47 + "<break>" + AMNNR_SuggestedTag46);

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
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

							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}

							refresh(driver);

							if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
									"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
								ThreadSleep(10000);

								if (BP.updateActivityTimelineRecordForMultipleFollowUpTasksAndVerifyUIOfSuggestedTags(
										projectName, task1UpdateBasicSection, null, null, updatedSuggestedTags, null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									ThreadSleep(10000);

									ArrayList<String> subjectLinkPopUpNegativeResult = BP
											.verifySubjectLinkPopUpOnIntraction(driver, task1SubjectName,
													updatedTask1BasicSectionVerification,
													updatedTask1AdvancedSectionVerification, IconType.Task, PageName.AcuityDetails);

									if (subjectLinkPopUpNegativeResult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " record is able to open popup after click on it and verify its data"
												+ "------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + task1SubjectName
														+ " record link popup is not verified, Reason: "
														+ subjectLinkPopUpNegativeResult + "------",
												YesNo.Yes);
										sa.assertTrue(false,
												"------" + task1SubjectName
														+ " record link popup is not verified, Reason: "
														+ subjectLinkPopUpNegativeResult + "------");

									}

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

									} else {
										log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
										sa.assertTrue(false, "Not able to click on Edit Note button");
									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, IconType.Task, task1SubjectName, updatedNotesOfTask,
											true, false, updatedRelatedToVerifyInInteraction,
											updatedRelatedAssociationVerifyInInteraction);
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
								log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
								sa.assertTrue(false, "Not able to click on Edit Note button");
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
	public void AcuityMNNRTc023_VerifyWhenUserTagsAccountsAndContactsInNotesTextAreaAndClicksOnCloseButtonOrCrossIcon(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject48;
		String task1Notes = AMNNR_Notes48;
		String relatedTo = AMNNR_RelatedTo48;

		String priority = AMNNR_AdvancePriority48;
		String status = AMNNR_AdvanceStatus48;
		String task1ButtonName = AMNNR_ActivityType1;
		String[] suggestedTags = null;
		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo30.split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String updatedRelatedToInNotes = AMNNR_RelatedTo49;
		String[][] task1UpdateBasicSection = { { "Related_To", updatedRelatedToInNotes } };

		String[] updatedSuggestedTags = null;

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = AMNNR_ARelatedAsso30.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);
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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Task,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
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

							if (BP.crossIconButtonInNotePopUp(5) != null) {
								click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
										action.SCROLLANDBOOLEAN);
							}

							if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
									"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

								ThreadSleep(1000);

								if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName,
										task1UpdateBasicSection, null, null, updatedSuggestedTags, null, true, false)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									if (BP.crossIconButtonInNotePopUp(5) != null) {
										click(driver, BP.crossIconButtonInNotePopUp(20),
												"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
									}

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

									} else {
										log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
										sa.assertTrue(false, "Not able to click on Edit Note button");
									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, IconType.Task, task1SubjectName, updatedNotesOfTask,
											true, false, updatedRelatedToVerifyInInteraction,
											updatedRelatedAssociationVerifyInInteraction);
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
								log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
								sa.assertTrue(false, "Not able to click on Edit Note button");
							}

							refresh(driver);

							if (BP.crossIconButtonInNotePopUp(5) != null) {
								click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
										action.SCROLLANDBOOLEAN);
							}
							if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
									"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
								ThreadSleep(1000);

								if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName,
										task1UpdateBasicSection, null, null, updatedSuggestedTags, null, false, true)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									ThreadSleep(1000);

									if (BP.crossIconButtonInNotePopUp(5) != null) {
										click(driver, BP.crossIconButtonInNotePopUp(20),
												"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
									}
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

									} else {
										log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
										sa.assertTrue(false, "Not able to click on Edit Note button");
									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, IconType.Task, task1SubjectName, updatedNotesOfTask,
											true, false, updatedRelatedToVerifyInInteraction,
											updatedRelatedAssociationVerifyInInteraction);
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
	public void AcuityMNNRTc024_VerifyWhenEditButtonIsClickedForTheTaskHavingFollowUpTaskAndIsDeletedFromTaskDetailPage(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String task1SubjectName = AMNNR_Subject17;

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Note PopUp Section---------", YesNo.No);

		if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

			log(LogStatus.INFO, "-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
					YesNo.No);

			if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

				if (click(driver, taskBP.buttonInTheDownArrowList("Delete", 20), "Delete Button in downArrowButton",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Delete Button in  Down Arrow Button", YesNo.No);

					if (click(driver, taskBP.taskDeleteConfirmButton(15), "Delete Button in downArrowButton",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Delete Confirm Button", YesNo.No);

						if (taskBP.taskDeletedMsg(15) != null) {
							log(LogStatus.INFO, "Task Delete Msg displayed, So Task has been deleted", YesNo.No);

							log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjectName
									+ " is present or not in Interaction Section---------", YesNo.No);
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

										if (!BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(
												task1SubjectName)) {
											log(LogStatus.INFO, "Verified: Task: " + task1SubjectName
													+ " is not present there after delete", YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"Task: " + task1SubjectName + " is present there after delete",
													YesNo.Yes);
											sa.assertTrue(false,
													"Task: " + task1SubjectName + " is present there after delete");

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
							log(LogStatus.ERROR, "Task Delete Msg not display, So Task not gets deleted", YesNo.Yes);
							sa.assertTrue(false, "Task Delete Msg not display, So Task not gets deleted");

						}

					} else {
						log(LogStatus.ERROR, "Not ABle to Click on Delete Confirm Button", YesNo.Yes);
						sa.assertTrue(false, "Not ABle to Click on Delete Confirm Button");

					}

				} else {
					log(LogStatus.ERROR, "Not Able Click on Delete button in Down Arrow Button", YesNo.Yes);
					sa.assertTrue(false, "Not Able Click on Delete button in Down Arrow Button");

				}

			} else {
				log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
				sa.assertTrue(false, "Not Able Click on Down Arrow Button");

			}

		} else {

			log(LogStatus.ERROR, "-----Task named: " + task1SubjectName + " not found in Tasks Object-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc025_VerifyWhenTheRemovedTaskIsRestoredAndItsImpactOnInteractionSection(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity017", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = AMNNR_Subject17;
		String updatedNotesOfTask = AMNNR_Notes21;
		String relatedTo = AMNNR_RelatedTo17;

		String priority = AMNNR_AdvancePriority17;
		String status = AMNNR_AdvanceStatus17;

		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo21;
		String updatedRelatedTo = AMNNR_RelatedTo22;
		String updatedRelatedToVerifyInNotes = verificationRelatedTo + "<break>" + updatedRelatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", updatedNotesOfTask },
				{ "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] listViewSheetData = { { AMNNR_ListViewMember1, AMNNR_ListViewTabName1, task1SubjectName,
				AMNNR_ListViewAccessibility1, AMNNR_ListViewFilter1, AMNNR_ListViewField1, AMNNR_ListViewOperators1,
				task1SubjectName, AMNNR_ListViewTextBoxType1 } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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

										if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(
												task1SubjectName)) {
											log(LogStatus.INFO, "Verified: Task: " + task1SubjectName
													+ " is present there after restore", YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"Task: " + task1SubjectName + " is not present there after restore",
													YesNo.Yes);
											sa.assertTrue(false, "Task: " + task1SubjectName
													+ " is not present there after restore");

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

							if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

								log(LogStatus.INFO,
										"-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
										YesNo.No);

								if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

									if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
											"Delete Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);

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
										log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
												YesNo.Yes);

									}

								} else {
									log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
									sa.assertTrue(false, "Not Able Click on Down Arrow Button");

								}

							} else {

								log(LogStatus.ERROR,
										"-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
										YesNo.Yes);
								BaseLib.sa.assertTrue(false,
										"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

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
	 * 
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
	public void AcuityMNNRTc026_VerifyTheImpactOnNoteWhenFieldLabelsAreChangedFromBackend(String projectName) {
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);

		String[] tabNames = { "Activities" };
		String[][] labelsWithValues2d = {
				"Subject<break>Subject updated<Section>Status<break>Status updated<Section>Due Date<break>Due Date updated<Section>Priority<break>Priority updated"
						.split("<Section>", -1) };

		String[][] labelsWithValues2dRevertBack = {
				"Subject<break>Subject<Section>Status<break>Status<Section>Due Date<break>Due Date<Section>Priority<break>Priority"
						.split("<Section>", -1) };

		String task1SubjectNameVerifyAfterLabelChange = "";
		String task1NotesVerifyAfterLabelChange = "";

		String getAdvanceDueDateVerifyAfterLabelChange = "";
		String priorityAfterLabelChange = "Normal";
		String statusAfterLabelChange = "Not Started";

		String taskSectionSubjectAfterLabelChange = "";
		String taskSectionStatusAfterLabelChange = "Not Started";
		String taskSectionDueDateOnlyAfterLabelChange = "";

		String[][] task1BasicSectionAfterLabelChange = { { "Subject updated", task1SubjectNameVerifyAfterLabelChange },
				{ "Notes", task1NotesVerifyAfterLabelChange } };

		String[][] task1AdvancedSectionAfterLabelChange = {
				{ "Due Date updated", getAdvanceDueDateVerifyAfterLabelChange },
				{ "Status updated", statusAfterLabelChange }, { "Priority updated", priorityAfterLabelChange } };

		String[][] task1TaskSectionAfterLabelChange = { { "Subject updated", taskSectionSubjectAfterLabelChange },
				{ "User", crmUser1FirstName + " " + crmUser1LastName },
				{ "Status updated", taskSectionStatusAfterLabelChange },
				{ "Due Date updated", taskSectionDueDateOnlyAfterLabelChange } };

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity023", excelLabel.Advance_Due_Date);
		String getAdvanceDueDateExisting = AdvanceDueDate;
		AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("3"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectNameVerify = AMNNR_Subject23;
		String task1NotesVerify = AMNNR_Notes23;
		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo17.split("<break>");
		String verificationUpdatedRelatedTo = AMNNR_RelatedTo26;

		String priorityVerify = AMNNR_AdvancePriority23;
		String statusVerify = AMNNR_AdvanceStatus23;

		String[][] task1UpdatedBasicSectionVerificationExisting = { { "Subject updated", task1SubjectNameVerify },
				{ "Notes", task1NotesVerify }, { "Related_To", verificationUpdatedRelatedTo } };
		String[][] task1AdvancedSectionVerificationExisting = { { "Due Date updated", getAdvanceDueDateExisting },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status updated", statusVerify },
				{ "Priority updated", priorityVerify } };

		String recordNameExisting = AMNNR_FirmLegalName6;
		String recordTypeExisting = AMNNR_FirmRecordType6;

		String recordName = AMNNR_FirmLegalName2;
		String recordType = AMNNR_FirmRecordType2;

		String task1SubjectName = AMNNR_Subject50;
		String task1Notes = AMNNR_Notes50;
		String relatedTo = AMNNR_RelatedTo50;

		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo51;

		String status = AMNNR_AdvanceStatus50;
		String task1ButtonName = AMNNR_ActivityType1;
		String[] suggestedTags = AMNNR_SuggestedTag50.split("<break>", -1);
		String[] newlyRelatedToVerifyInInteraction = AMNNR_ATRelatedTo31.split("<break>");

		String[][] task1BasicSection = { { "Subject updated", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date updated", getAdvanceDueDate }, { "Status updated", status } };

		String[][] task1BasicSectionVerification = { { "Subject updated", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date updated", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status updated", status } };

		String[] labelAndValueSeprateByBreak = { "User" + "<break>" + crmUser1FirstName + " " + crmUser1LastName,
				"Status updated" + "<break>" + status, "Subject updated" + "<break>" + task1SubjectName,
				"Due Date updated" + "<break>" + getAdvanceDueDate,
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

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				home.notificationPopUpClose();

				WebElement ele;
				if (npbl.createNavPopUpMinimizeButton(5) != null) {
					CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
				}
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Create.toString(),
						action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on " + task1ButtonName + " Going to click on : "
							+ NavigationMenuItems.Create.toString() + " for creation ", YesNo.No);
					ele = npbl.getNavigationLabel(projectName, task1ButtonName, action.BOOLEAN, 10);
					if (ele != null && CommonLib.click(driver, ele, task1ButtonName, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on " + task1ButtonName + " so going for creation", YesNo.No);
						String url = getURL(driver, 10);

						ThreadSleep(10000);

						String expectedHeaderName = "Task";
						if (BP.notePopUpHeading(expectedHeaderName, 15) != null) {
							log(LogStatus.INFO, "PopUp Name has been verified to: " + expectedHeaderName, YesNo.No);
						}

						else {
							log(LogStatus.ERROR,
									"PopUp Name has been not been verified, Expected: " + expectedHeaderName, YesNo.No);
							sa.assertTrue(false,
									"PopUp Name has been not been verified, Expected: " + expectedHeaderName);
						}

						ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
								.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionAfterLabelChange,
										task1AdvancedSectionAfterLabelChange, task1TaskSectionAfterLabelChange);
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
						ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDateExisting,
								IconType.Task, task1SubjectNameVerify, task1NotesVerify, true, false,
								RelatedToVerifyInInteraction, null);
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
						CommonLib.ThreadSleep(800);

						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
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
								ArrayList<String> result2 = BP.verifyRecordOnInteractionCard(getAdvanceDueDate,
										IconType.Task, task1SubjectName, task1Notes, true, false,
										newlyRelatedToVerifyInInteraction, null);
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
								CommonLib.ThreadSleep(800);
								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
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
								if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

									log(LogStatus.INFO, "-----Verified Task named: " + task1SubjectName
											+ " found in Tasks Object-----", YesNo.No);

									CommonLib.ThreadSleep(8000);
									List<String> taskDetailPageNegativeResult = BP
											.fieldValueVerification(labelAndValueSeprateByBreak);

									if (taskDetailPageNegativeResult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " labels and their values in Detail page has been verified------",
												YesNo.No);

									} else {
										log(LogStatus.ERROR, "------" + task1SubjectName
												+ " labels and their values in Detail page has not been verified, Reason: "
												+ taskDetailPageNegativeResult + "------", YesNo.No);
										sa.assertTrue(false, "------" + task1SubjectName
												+ " labels and their values in Detail page has not been verified, Reason: "
												+ taskDetailPageNegativeResult + "------");

									}

								} else {

									log(LogStatus.ERROR,
											"-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
											YesNo.Yes);
									BaseLib.sa.assertTrue(false,
											"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

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
	public void AcuityMNNRTc027_VerifyTheImpactOnNoteTaggingWhenPEStandardUserProfileObjectPermissionIsRevokedAndUserTriesToTagTheObject(
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

		String task1SubjectName = AMNNR_Subject52;
		String task1Notes = AMNNR_Notes52;
		String relatedTo = AMNNR_RelatedTo52;

		String verificationRelatedTo = relatedTo;

		String status = AMNNR_AdvanceStatus52;
		String task1ButtonName = AMNNR_ActivityType1;
		String[] suggestedTags = null;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", "Normal" } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Due Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status } };

		String relatedToNotContains = AMNNR_RelatedTo53;
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

								if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

									log(LogStatus.INFO, "-----Verified Task named: " + task1SubjectName
											+ " found in Tasks Object-----", YesNo.No);

									if (taskBP.buttonDisplayAsSplitView(8) != null) {
										CommonLib.click(driver, taskBP.buttonDisplayAsSplitView(8),
												"buttonDisplayAsSplitView", action.BOOLEAN);
										CommonLib.click(driver, taskBP.tableViewDropDownButton(8),
												"tableViewDropDownButton", action.BOOLEAN);

									}

									if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

										if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
												"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
													YesNo.No);

											String url = getURL(driver, 10);

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
											log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
													YesNo.Yes);
											sa.assertTrue(false, "Not Able Click on Edit button in Down Arrow Button");

										}

									} else {
										log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
										sa.assertTrue(false, "Not Able Click on Down Arrow Button");
									}

								} else {

									log(LogStatus.ERROR,
											"-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
											YesNo.Yes);
									BaseLib.sa.assertTrue(false,
											"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

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
	public void AcuityMNNRTc028_VerifyTheImpactOfNoteTaggingWhenTaskPermissionIsRevokedForTheUser(String projectName) {
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);

		String task1ButtonName = AMNNR_ActivityType1;
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
	public void AcuityMNNRTc029_VerifyTheUIOfNotesPopUpFromNavigationPanelInCaseOfCall(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String task1ButtonName = AMNNR_ActivityType54;

		String task1SubjectName = AMNNR_Subject54;
		String task1Notes = AMNNR_Notes54;

		String getAdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT-08:00", "M/d/yyyy", Integer.parseInt("0"));

		String priority = AMNNR_AdvancePriority54;
		String status = AMNNR_AdvanceStatus54;

		String taskSectionSubject = AMNNR_TaskSubject54;
		String taskSectionStatus = AMNNR_TaskStatus54;
		String taskSectionDueDateOnly = "";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String[][] task1TaskSection = { { "Subject", taskSectionSubject }, { "Status", taskSectionStatus },
				{ "Due Date", taskSectionDueDateOnly } };

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

					String expectedHeaderName = "Call Notes";
					if (BP.notePopUpHeading(expectedHeaderName, 15) != null) {
						log(LogStatus.INFO, "PopUp Name has been verified to: " + expectedHeaderName, YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "PopUp Name has been not been verified, Expected: " + expectedHeaderName,
								YesNo.No);
						sa.assertTrue(false, "PopUp Name has been not been verified, Expected: " + expectedHeaderName);
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
	public void AcuityMNNRTc030_CreateACallAndAddTheNotesFromEditCommentButtonOfTaskLayout(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject2 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes2;
		String relatedTo = AMNNR_RelatedTo2;
//		String priority = AMNNR_AdvancePriority2;
//		String status = AMNNR_AdvanceStatus2;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };
		String task1ButtonName = AMNNR_ActivityType54;
		String recordName = AMNNR_FirmLegalName1;
		String recordType = AMNNR_FirmRecordType1;
		String updatedCommentOfTask = AMNNR_Notes3;
		String[] relatedToVerify = AMNNR_ATRelatedTo1.split("<break>");
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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
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

					if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

						log(LogStatus.INFO,
								"-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
								YesNo.No);

						if (taskBP.editCommentsIntask(task1SubjectName, updatedCommentOfTask)) {
							log(LogStatus.INFO, "Updated Comment of Task: " + task1SubjectName + " verified: "
									+ updatedCommentOfTask, YesNo.No);

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

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, IconType.Call, task1SubjectName,
												updatedCommentOfTask, true, false, relatedToVerify, null);
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

										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}
										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(1000);
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
							log(LogStatus.ERROR, "Updated Comment of Task: " + task1SubjectName + " is not verified: "
									+ updatedCommentOfTask, YesNo.No);
							sa.assertTrue(false, "Updated Comment of Task: " + task1SubjectName + " is not verified: "
									+ updatedCommentOfTask);

						}

					} else {

						log(LogStatus.ERROR, "-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
								YesNo.Yes);
						BaseLib.sa.assertTrue(false,
								"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

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
	public void AcuityMNNRTc031_CreateACallAndAddTheNotesAndVerifySuggestedTagPopUp(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject4 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes4;
		String relatedTo = AMNNR_RelatedTo4;

		String priority = AMNNR_AdvancePriority4;
		String status = AMNNR_AdvanceStatus4;
		String task1ButtonName = AMNNR_ActivityType54;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String[] SuggestedTags = AMNNR_SuggestedTag4.split("<break>", -1);

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

		String recordName = AMNNR_FirmLegalName2;
		String recordType = AMNNR_FirmRecordType2;
		String updatedNotesOfTask = AMNNR_Notes5;
		String[] relatedToVerify = AMNNR_ATRelatedTo2.split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag5.split("<break>", -1);
		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo3.split("<break>");

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

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", AMNNR_SuggestedTag4 } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Date", getAdvanceDueDate } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						ArrayList<String> subjectLinkPopUpNegativeResult = BP.verifySubjectLinkPopUpOnIntraction(driver,
								task1SubjectName, task1BasicSection, task1AdvancedSection, IconType.Call, PageName.AcuityDetails);

						if (subjectLinkPopUpNegativeResult.isEmpty()) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to open popup after click on it and verify its data" + "------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------",
									YesNo.Yes);
							sa.assertTrue(false,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------");

						}
						String url = getURL(driver, 10);

						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(1000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1SubjectName, task1Notes,
											relatedToArray);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ArrayList<String> subjectLinkPopUpNegativeResultUpdated = BP
												.verifySubjectLinkPopUpOnIntraction(driver, task1SubjectName,
														updatedTask1BasicSectionVerification,
														updatedTask1AdvancedSectionVerification, IconType.Call, PageName.AcuityDetails);

										if (subjectLinkPopUpNegativeResultUpdated.isEmpty()) {
											log(LogStatus.PASS, "------" + task1SubjectName
													+ " record is able to open popup after click on it and verify its data"
													+ "------", YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record link popup is not verified, Reason: "
															+ subjectLinkPopUpNegativeResultUpdated + "------",
													YesNo.Yes);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record link popup is not verified, Reason: "
															+ subjectLinkPopUpNegativeResultUpdated + "------");

										}

										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, IconType.Call, task1SubjectName, updatedNotesOfTask,
												true, false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

											String url2 = getURL(driver, 10);

											if (BP.crossIconButtonInNotePopUp(5) != null) {
												click(driver, BP.crossIconButtonInNotePopUp(20),
														"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
											}
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
	public void AcuityMNNRTc032_CreateACallWithoutMeetingNotesAndTagFromInteractionSectionByClickingOnEditNotesButton(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject6 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes6;
		String relatedTo = AMNNR_RelatedTo6;
		String[] relatedToArray = relatedTo.split("<break>", -1);
//		String priority = AMNNR_AdvancePriority6;
//		String status = AMNNR_AdvanceStatus6;
		String task1ButtonName = AMNNR_ActivityType54;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String recordName = AMNNR_Contact1;

		String updatedNotesOfTask = AMNNR_Notes7;
		String[] relatedToVerify = AMNNR_ATRelatedTo4.split("<break>");
		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo5.split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag7.split("<break>", -1);

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
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						if (BP.verifySubjectLinkPopUpOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to open popup after click on it" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to open popup after click on it" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to open popup after click on it" + "------");
						}

						String url = getURL(driver, 10);

						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(1000);

							String expectedHeaderName = "Call Notes";
							if (BP.notePopUpHeading(expectedHeaderName, 15) != null) {
								log(LogStatus.INFO, "PopUp Name has been verified to: " + expectedHeaderName, YesNo.No);
							}

							else {
								log(LogStatus.ERROR,
										"PopUp Name has been not been verified, Expected: " + expectedHeaderName,
										YesNo.No);
								sa.assertTrue(false,
										"PopUp Name has been not been verified, Expected: " + expectedHeaderName);
							}
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
								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, IconType.Call, task1SubjectName, updatedNotesOfTask,
												true, false, updatedRelatedToVerify, null);
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
	public void AcuityMNNRTc033_CreateACallWithoutMeetingNotesAndAddTheNotesFromEditButton(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("2"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject8 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes8;
		String relatedTo = AMNNR_RelatedTo8;
//		String priority = AMNNR_AdvancePriority8;
//		String status = AMNNR_AdvanceStatus8;
		String task1ButtonName = AMNNR_ActivityType54;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String recordName = AMNNR_Contact2;

		String updatedNotesOfTask = AMNNR_Notes9;
		String[] relatedToVerify = AMNNR_ATRelatedTo6.split("<break>");
		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo7.split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag9.split("<break>", -1);

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
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

							log(LogStatus.INFO,
									"-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
									YesNo.No);
							if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

								if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
										"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);

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
										ThreadSleep(2000);

										if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
												null, updatedSuggestedTags, null)) {
											log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

											CommonLib.refresh(driver);

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

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											if (lp.clickOnTab(projectName, tabObj2)) {

												log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

												if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
														TabName.ContactTab, recordName, 30)) {
													log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
													ThreadSleep(4000);
													if (BP.clicktabOnPage("Acuity")) {
														log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

														ArrayList<String> updatedresult = BP
																.verifyRecordOnInteractionCard(getAdvanceDueDate,
																		IconType.Call, task1SubjectName,
																		updatedNotesOfTask, true, false,
																		updatedRelatedToVerify, null);
														if (updatedresult.isEmpty()) {
															log(LogStatus.PASS, "------" + task1SubjectName
																	+ " record has been verified on intraction------",
																	YesNo.No);

														} else {
															log(LogStatus.ERROR, "------" + task1SubjectName
																	+ " record is not verified on intraction, Reason: "
																	+ updatedresult + "------", YesNo.No);
															sa.assertTrue(false, "------" + task1SubjectName
																	+ " record is not verified on intraction, Reason: "
																	+ updatedresult + "------");
														}

													} else {
														log(LogStatus.ERROR, "Not able to click on Acuity Tab",
																YesNo.No);
														sa.assertTrue(false, "Not able to click on Acuity Tab");
													}

												} else {
													log(LogStatus.ERROR, "Not able to open " + recordName + " record",
															YesNo.No);
													sa.assertTrue(false, "Not able to open " + recordName + " record");
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
												sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
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
									log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
											YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Not Able Click on Edit button in Down Arrow Button");

								}

							} else {
								log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Not Able Click on Down Arrow Button");

							}

						} else {

							log(LogStatus.ERROR,
									"-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
									YesNo.Yes);
							BaseLib.sa.assertTrue(false,
									"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

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
	public void AcuityMNNRTc034_CreateACallWithMeetingNotesAndAddTheNotesFromEditButtonOfTaskLayOut(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject10 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes10;
		String relatedTo = AMNNR_RelatedTo10;
//		String priority = AMNNR_AdvancePriority10;
//		String status = AMNNR_AdvanceStatus10;
		String task1ButtonName = AMNNR_ActivityType54;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };
		String[] suggestedTags = AMNNR_SuggestedTag10.split("<break>", -1);

		String RelatedToVerify = relatedTo + AMNNR_RelatedTo11;
		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", RelatedToVerify } };

		String recordName = AMNNR_Contact3;

		String updatedNotesOfTask = AMNNR_Notes11;
		String[] relatedToVerify = AMNNR_ATRelatedTo8.split("<break>");
		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo9.split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag11.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = RelatedToVerify + AMNNR_RelatedTo12;
		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

							log(LogStatus.INFO,
									"-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
									YesNo.No);
							if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

								if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
										"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);

									String url = getURL(driver, 10);

									ThreadSleep(10000);
									ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url,
													task1BasicSectionVerification, task1AdvancedSection, null);
									if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
										log(LogStatus.INFO,
												"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
												YesNo.No);

										refresh(driver);
										ThreadSleep(10000);

										if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
												null, updatedSuggestedTags, null)) {
											log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

											CommonLib.refresh(driver);

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

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

											}

											if (lp.clickOnTab(projectName, tabObj2)) {

												log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

												if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
														TabName.ContactTab, recordName, 30)) {
													log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
													ThreadSleep(4000);
													if (BP.clicktabOnPage("Acuity")) {
														log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

														ArrayList<String> updatedresult = BP
																.verifyRecordOnInteractionCard(getAdvanceDueDate,
																		IconType.Call, task1SubjectName,
																		updatedNotesOfTask, true, false,
																		updatedRelatedToVerify, null);
														if (updatedresult.isEmpty()) {
															log(LogStatus.PASS, "------" + task1SubjectName
																	+ " record has been verified on intraction------",
																	YesNo.No);

														} else {
															log(LogStatus.ERROR, "------" + task1SubjectName
																	+ " record is not verified on intraction, Reason: "
																	+ updatedresult + "------", YesNo.No);
															sa.assertTrue(false, "------" + task1SubjectName
																	+ " record is not verified on intraction, Reason: "
																	+ updatedresult + "------");
														}

													} else {
														log(LogStatus.ERROR, "Not able to click on Acuity Tab",
																YesNo.No);
														sa.assertTrue(false, "Not able to click on Acuity Tab");
													}

												} else {
													log(LogStatus.ERROR, "Not able to open " + recordName + " record",
															YesNo.No);
													sa.assertTrue(false, "Not able to open " + recordName + " record");
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
												sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
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
									log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
											YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Not Able Click on Edit button in Down Arrow Button");

								}

							} else {
								log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Not Able Click on Down Arrow Button");

							}

						} else {

							log(LogStatus.ERROR,
									"-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
									YesNo.Yes);
							BaseLib.sa.assertTrue(false,
									"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

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
	public void AcuityMNNRTc035_CreateACallWithMeetingNotesAndUpdateTheNotesFromEditNoteButtonOnInteractionSectionOfAcuityTab(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("4"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject13 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes13;
		String relatedTo = AMNNR_RelatedTo13;

//		String priority = AMNNR_AdvancePriority13;
//		String status = AMNNR_AdvanceStatus13;
		String task1ButtonName = AMNNR_ActivityType54;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };
		String[] suggestedTags = null;

		String recordName = AMNNR_Contact4;

		String updatedNotesOfTask = AMNNR_Notes14;
		String[] relatedToVerify = AMNNR_ATRelatedTo10.split("<break>");
		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo11.split("<break>");

		String updatedRelatedTo = relatedTo + AMNNR_RelatedTo14;
		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag14.split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						ArrayList<String> subjectLinkPopUpNegativeResult = BP.verifySubjectLinkPopUpOnIntraction(driver,
								task1SubjectName, task1BasicSection, task1AdvancedSection, IconType.Call, PageName.AcuityDetails);

						if (subjectLinkPopUpNegativeResult.isEmpty()) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to open popup after click on it and verify its data" + "------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------",
									YesNo.Yes);
							sa.assertTrue(false,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------");

						}

						String url = getURL(driver, 10);

						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(1000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, IconType.Call, task1SubjectName, updatedNotesOfTask,
												true, false, updatedRelatedToVerify, null);
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

										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}
										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(1000);
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
	public void AcuityMNNRTc036_CreateACallAndVerifyTaskHyperlinkUpdateTheTaskSubjectAndVerifyTheCallHyperlink(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("5"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject15 + " " + AMNNR_ActivityType54;
		String task1UpdatedSubjectName = AMNNR_Subject16 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes15;

		String relatedTo = AMNNR_RelatedTo15;

		String priority = AMNNR_AdvancePriority15;
//		String status = AMNNR_AdvanceStatus15;
		String task1ButtonName = AMNNR_ActivityType54;
		String getAdvanceDueDateInTaskSection = "";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String[][] tasksSectionVerificationData = { { "Subject", task1SubjectName },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Date", getAdvanceDueDateInTaskSection } };

		String[][] followUpTask1AdvancedSectionVerificationInNotesPopup = { { "Date", getAdvanceDueDateInTaskSection },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1UpdatedSubjectName },
				{ "Notes", task1Notes }, { "Related_To", relatedTo } };

		String[][] updateFollowUpTask1AdvancedSection = { { "Due Date", getAdvanceDueDate } };

		String[] suggestedTags = null;

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] relatedToVerify = AMNNR_ATRelatedTo12.split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Task: " + task1UpdatedSubjectName + " and followUp task: "
				+ task1SubjectName + " in Activity Timeline Section---------", YesNo.No);

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

					ArrayList<String> result2 = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
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
					ThreadSleep(10000);
					if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

						log(LogStatus.INFO,
								"-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
								YesNo.No);
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

								}
								CommonLib.refresh(driver);

								CommonLib.ThreadSleep(5000);

								if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName, null,
										updateFollowUpTask1AdvancedSection, null, null, null, false, false)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");
								}

							} else {
								log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button", YesNo.Yes);

								sa.assertTrue(false, "Not Able Click on Edit button in Down Arrow Button");

							}

						} else {
							log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
							sa.assertTrue(false, "Not Able Click on Down Arrow Button");

						}
					} else {

						log(LogStatus.ERROR, "-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
								YesNo.Yes);
						BaseLib.sa.assertTrue(false,
								"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

					}

					if (lp.clickOnTab(projectName, tabObj1)) {

						log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

						if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType,
								recordName, 30)) {
							log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open",
									YesNo.No);
							ThreadSleep(4000);
							if (BP.clicktabOnPage("Acuity")) {
								log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

								String url2 = getURL(driver, 10);

								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								if (click(driver, BP.editButtonOnInteractionCard(task1UpdatedSubjectName, 20),
										"Edit Note Button of: " + task1UpdatedSubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

									ThreadSleep(1000);
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
									log(LogStatus.ERROR,
											"Not able to click on Edit Note button of " + task1UpdatedSubjectName,
											YesNo.No);
									sa.assertTrue(false,
											"Not able to click on Edit Note button of " + task1UpdatedSubjectName);
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
	public void AcuityMNNRTc037_CreateACallWithMeetingNotesAndUpdateTheNotesForFollowUpTasksWhenCreatedMultipleFollowUpTasks(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("6"));
		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AMNNR_Activity056", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject17 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes17;
		String relatedTo = AMNNR_RelatedTo17;

		String priority = AMNNR_AdvancePriority17;
//		String status = AMNNR_AdvanceStatus17;
		String task1ButtonName = AMNNR_ActivityType54;
		String[] suggestedTags = "".split("<break>", -1);

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;
		String task1UpdateTaskSection1Subject = AMNNR_Subject18 + " " + AMNNR_ActivityType54;
		String task1UpdateTaskSection1DueDateOnly = AdvanceDueDate;
		String task1UpdateTaskSection2Subject = AMNNR_Subject19 + " " + AMNNR_ActivityType54;
		String task1UpdateTaskSection2DueDateOnly = AdvanceDueDate;
		String task1UpdateTaskSection3Subject = AMNNR_Subject20 + " " + AMNNR_ActivityType54;
		String task1UpdateTaskSection3DueDateOnly = AdvanceDueDate;

		String updatedNotesOfTask = AMNNR_Notes21;
		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[][] task1UpdateTaskSection1 = { { "Subject", task1UpdateTaskSection1Subject },
				{ "Due Date", task1UpdateTaskSection1DueDateOnly } };
		String[][] task1UpdateTaskSection2 = { { "Subject", task1UpdateTaskSection2Subject },
				{ "Due Date", task1UpdateTaskSection2DueDateOnly } };
		String[][] task1UpdateTaskSection3 = { { "Subject", task1UpdateTaskSection3Subject },
				{ "Due Date", task1UpdateTaskSection3DueDateOnly } };
		String[][][] task1UpdateTaskSection = { task1UpdateTaskSection1, task1UpdateTaskSection2,
				task1UpdateTaskSection3 };

		String[] relatedToVerify = (crmUser1FirstName + " " + crmUser1LastName + AMNNR_ATRelatedTo13).split("<break>");
		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo14.split("<break>", -1);

		String updatedRelatedTo = relatedTo + AMNNR_RelatedTo21;

		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };

		String[][] followUptask1BasicSectionVerification = { { "Subject", task1UpdateTaskSection1Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[][] followUptask2BasicSectionVerification = { { "Subject", task1UpdateTaskSection2Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[][] followUptask3BasicSectionVerification = { { "Subject", task1UpdateTaskSection3Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };

		String[][] followUptask1AdvanceSectionVerification = { { "Due Date", task1UpdateTaskSection1DueDateOnly },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] followUptask2AdvanceSectionVerification = { { "Due Date", task1UpdateTaskSection2DueDateOnly },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] followUptask3AdvanceSectionVerification = { { "Due Date", task1UpdateTaskSection3DueDateOnly },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[] updatedSuggestedTags = AMNNR_SuggestedTag21.split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						ArrayList<String> subjectLinkPopUpNegativeResult = BP.verifySubjectLinkPopUpOnIntraction(driver,
								task1SubjectName, task1BasicSection, task1AdvancedSection, IconType.Call, PageName.AcuityDetails);

						if (subjectLinkPopUpNegativeResult.isEmpty()) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to open popup after click on it and verify its data" + "------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------",
									YesNo.Yes);
							sa.assertTrue(false,
									"------" + task1SubjectName + " record link popup is not verified, Reason: "
											+ subjectLinkPopUpNegativeResult + "------");

						}

						String url = getURL(driver, 10);

						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(1000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
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
												task1UpdateTaskSection1DueDateOnly, IconType.Task,
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
												task1UpdateTaskSection2DueDateOnly, IconType.Task,
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
												task1UpdateTaskSection3DueDateOnly, IconType.Task,
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

										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}
										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(1000);
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

										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}
										if (click(driver,
												BP.editButtonOnInteractionCard(task1UpdateTaskSection1Subject, 20),
												"Edit Note Button of: " + task1UpdateTaskSection1Subject,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(1000);
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
										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}
										if (click(driver,
												BP.editButtonOnInteractionCard(task1UpdateTaskSection2Subject, 20),
												"Edit Note Button of: " + task1UpdateTaskSection2Subject,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(1000);
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
										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}
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
	public void AcuityMNNRTc038_ClickOnTheCallSubjectFromInteractionSectionAndAddTheNotesByClickingOnTagButton(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity056", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = AMNNR_Subject17 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes21;
		String relatedTo = AMNNR_RelatedTo17;

		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo21;

//		String priority = AMNNR_AdvancePriority17;
//		String status = AMNNR_AdvanceStatus17;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo14.split("<break>");
		String updatedRelatedTo = AMNNR_RelatedTo22;

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String relatedToNotContains = crmUser2FirstName + " " + crmUser2LastName;
		String[][] relatedAssociationNotContains = { { "Related_To", relatedToNotContains } };

		String updatedRelatedToVerifyInNotes = verificationRelatedTo + "<break>" + updatedRelatedTo;

		String[][] task1UpdatedBasicSection = { { "Related_To", updatedRelatedTo } };
		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", updatedRelatedToVerifyInNotes } };

		String[] updatedRelatedToVerify = AMNNR_ATRelatedTo15.split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

							log(LogStatus.INFO,
									"-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
									YesNo.No);

							if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

								if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
										"Delete Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);

									String url = getURL(driver, 10);

									ThreadSleep(10000);
									ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url,
													task1BasicSectionVerification, task1AdvancedSectionVerification,
													null);
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

									refresh(driver);
									ThreadSleep(1000);

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
									ThreadSleep(1000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdatedBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

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

										CommonLib.refresh(driver);
										CommonLib.ThreadSleep(5000);

										if (lp.clickOnTab(projectName, tabObj1)) {

											log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

											if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
													TabName.InstituitonsTab, recordType, recordName, 30)) {
												log(LogStatus.INFO, recordName + " record of record type " + recordType
														+ " has been open", YesNo.No);
												ThreadSleep(4000);
												if (BP.clicktabOnPage("Acuity")) {
													log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

													ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
															getAdvanceDueDate, IconType.Call, task1SubjectName,
															task1Notes, true, false, updatedRelatedToVerify, null);
													if (updatedresult.isEmpty()) {
														log(LogStatus.PASS, "------" + task1SubjectName
																+ " record has been verified on intraction------",
																YesNo.No);

													} else {
														log(LogStatus.ERROR, "------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------", YesNo.No);
														sa.assertTrue(false, "------" + task1SubjectName
																+ " record is not verified on intraction, Reason: "
																+ updatedresult + "------");
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
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}

								} else {
									log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
											YesNo.Yes);

								}

							} else {
								log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
								sa.assertTrue(false, "Not Able Click on Down Arrow Button");

							}

						} else {

							log(LogStatus.ERROR,
									"-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
									YesNo.Yes);
							BaseLib.sa.assertTrue(false,
									"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

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
	public void AcuityMNNRTc039_CreateACallWithMeetingNotesAndUpdateTheSameWithRelatedRecordRemoveAndThenVerify(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("7"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject23 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes23;
		String relatedTo = AMNNR_RelatedTo23;

//		String priority = AMNNR_AdvancePriority23;
//		String status = AMNNR_AdvanceStatus23;
		String task1ButtonName = AMNNR_ActivityType54;
		String[] suggestedTags = AMNNR_SuggestedTag23.split("<break>", -1);

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo24;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo16.split("<break>");

		String[] updatedRemoveRelatedAssociation = AMNNR_RelatedTo25.split("<break>", -1);

		String[] updatedSuggestedTags = null;

		String verificationUpdatedRelatedTo = AMNNR_RelatedTo26;

		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationUpdatedRelatedTo } };

		String[] updatedRelatedToVerifyInInteraction = AMNNR_ATRelatedTo17.split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						String url = getURL(driver, 10);

						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(1000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(1000);

								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

									if (BP.updateActivityTimelineRecord(projectName, null, null, null,
											updatedSuggestedTags, updatedRemoveRelatedAssociation)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										CommonLib.ThreadSleep(3000);

										String url2 = getURL(driver, 10);

										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}
										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(1000);
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
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
										}

										CommonLib.refresh(driver);
										CommonLib.ThreadSleep(5000);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, IconType.Call, task1SubjectName, task1Notes, true,
												false, updatedRelatedToVerifyInInteraction, null);
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
	public void AcuityMNNRTc040_CreateACallAndTagContactsAccountsWhichAreNotCreatedInTheOrg(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("8"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject27 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes27;
		String relatedTo = AMNNR_RelatedTo27;

//		String priority = AMNNR_AdvancePriority27;
//		String status = AMNNR_AdvanceStatus27;
		String task1ButtonName = AMNNR_ActivityType54;
		String[] suggestedTags = AMNNR_SuggestedTag27.split("<break>", -1);
		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo28;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo18.split("<break>");

		String relatedToNotContains = AMNNR_RelatedTo29;
		String[][] relatedAssociationNotContains = { { "Related_To", relatedToNotContains } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
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

								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
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
								ThreadSleep(3000);
								String url2 = getURL(driver, 10);
								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(1000);
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
											getAdvanceDueDate, IconType.Call, task1SubjectName, task1Notes, true, false,
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
	public void AcuityMNNRTc041_CreateACallAndTagContact1to50InCommentSectionAndCheckInPopUpContact1To50ShouldGetDisplayWithoutAnyContactGetsMissout(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("8"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject30 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes30;
		String relatedTo = AMNNR_RelatedTo30;

//		String priority = AMNNR_AdvancePriority30;
//		String status = AMNNR_AdvanceStatus30;
		String task1ButtonName = AMNNR_ActivityType54;
		String[] suggestedTags = null;
		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo19.split("<break>");

		String updatedNotesOfTask = AMNNR_Notes31;

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag31.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = AMNNR_ATRelatedTo20.split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = updatedSuggestedTags;

		String updatedRelatedToVerifyInNotes = (relatedTo + AMNNR_RelatedTo31);

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(1000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(1000);
										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}
										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(1000);
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
													getAdvanceDueDate, IconType.Call, task1SubjectName,
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
	public void AcuityMNNRTc042_CreateACallAndTag13RecordsForRelatedAssociationAnd50Contacts(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("8"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject32 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes32;
		String relatedTo = AMNNR_RelatedTo32;

//		String priority = AMNNR_AdvancePriority32;
//		String status = AMNNR_AdvanceStatus32;
		String task1ButtonName = AMNNR_ActivityType54;
		String[] suggestedTags = null;
		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String recordName = AMNNR_FirmLegalName2;
		String recordType = AMNNR_FirmRecordType2;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo21.split("<break>");

		String updatedNotesOfTask = AMNNR_Notes33;

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag33.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = AMNNR_ATRelatedTo22.split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = updatedSuggestedTags;

		String updatedRelatedToVerifyInNotes = (relatedTo + AMNNR_RelatedTo33);

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(1000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(1000);

										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}

										ArrayList<String> subjectLinkPopUpNegativeResult = BP
												.verifySubjectLinkPopUpOnIntraction(driver, task1SubjectName,
														updatedTask1BasicSectionVerification,
														updatedTask1AdvancedSectionVerification, IconType.Call, PageName.AcuityDetails);

										if (subjectLinkPopUpNegativeResult.isEmpty()) {
											log(LogStatus.PASS, "------" + task1SubjectName
													+ " record is able to open popup after click on it and verify its data"
													+ "------", YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record link popup is not verified, Reason: "
															+ subjectLinkPopUpNegativeResult + "------",
													YesNo.Yes);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record link popup is not verified, Reason: "
															+ subjectLinkPopUpNegativeResult + "------");

										}

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
													getAdvanceDueDate, IconType.Call, task1SubjectName,
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
	public void AcuityMNNRTc043_CreateACallWithMeetingNotesByTaggingCustomObjectsInIt(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("8"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject34 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes34;
		String relatedTo = AMNNR_RelatedTo34;

//		String priority = AMNNR_AdvancePriority34;
//		String status = AMNNR_AdvanceStatus34;
		String task1ButtonName = AMNNR_ActivityType54;
		String[] suggestedTags = AMNNR_SuggestedTag34.split("<break>", -1);
		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo35;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String recordName = AMNNR_Contact4;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo23.split("<break>", -1);
		String[] RelatedAssociationVerifyInInteraction = (AMNNR_RelatedTo36).split("<break>", -1);

		String updatedNotesOfTask = AMNNR_Notes35;

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = AMNNR_SuggestedTag35.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = AMNNR_ATRelatedTo24.split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = (AMNNR_RelatedTo37).split("<break>", -1);

		String updatedRelatedToVerifyInNotes = (verificationRelatedTo + AMNNR_RelatedTo38);

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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

					ArrayList<String> subjectLinkPopUpNegativeResultBeforeUpdate = BP
							.verifySubjectLinkPopUpOnIntraction(driver, task1SubjectName, task1BasicSectionVerification,
									task1AdvancedSectionVerification, IconType.Call, PageName.AcuityDetails);

					if (subjectLinkPopUpNegativeResultBeforeUpdate.isEmpty()) {
						log(LogStatus.PASS, "------" + task1SubjectName
								+ " record is able to open popup after click on it and verify its data" + "------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"------" + task1SubjectName + " record link popup is not verified, Reason: "
										+ subjectLinkPopUpNegativeResultBeforeUpdate + "------",
								YesNo.Yes);
						sa.assertTrue(false,
								"------" + task1SubjectName + " record link popup is not verified, Reason: "
										+ subjectLinkPopUpNegativeResultBeforeUpdate + "------");

					}

					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction,
							RelatedAssociationVerifyInInteraction);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(1000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(1000);

										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}

										ArrayList<String> subjectLinkPopUpNegativeResult = BP
												.verifySubjectLinkPopUpOnIntraction(driver, task1SubjectName,
														updatedTask1BasicSectionVerification,
														updatedTask1AdvancedSectionVerification, IconType.Call, PageName.AcuityDetails);

										if (subjectLinkPopUpNegativeResult.isEmpty()) {
											log(LogStatus.PASS, "------" + task1SubjectName
													+ " record is able to open popup after click on it and verify its data"
													+ "------", YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record link popup is not verified, Reason: "
															+ subjectLinkPopUpNegativeResult + "------",
													YesNo.Yes);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record link popup is not verified, Reason: "
															+ subjectLinkPopUpNegativeResult + "------");

										}

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(1000);
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
													getAdvanceDueDate, IconType.Call, task1SubjectName,
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
	public void AcuityMNNRTc044_VerifyRemovingSomeOfTheTaggedFromNotesPopUpOfCallAndVerifyTheSameInInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("9"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject39 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes39;
		String relatedTo = AMNNR_RelatedTo39;

//		String priority = AMNNR_AdvancePriority39;
//		String status = AMNNR_AdvanceStatus39;
		String task1ButtonName = AMNNR_ActivityType54;
		String[] suggestedTags = AMNNR_SuggestedTag39.split("<break>", -1);
		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo40;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo25.split("<break>", -1);

		String updatedNotesOfTask = null;

		String[][] task1UpdateBasicSection = null;
		String[] updatedSuggestedTags = "".split("<break>", -1);
		String[] updatedRemoveRelatedAssociation = AMNNR_ARelatedAsso25.split("<break>", -1);
		String[] updatedRelatedToVerifyInInteraction = AMNNR_ATRelatedTo26.split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = AMNNR_ARelatedAsso26.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = AMNNR_RelatedTo41;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(1000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(1000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, updatedRemoveRelatedAssociation)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(1000);

										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}
										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(1000);
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
													getAdvanceDueDate, IconType.Call, task1SubjectName,
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
	public void AcuityMNNRTc045_VerifyChangingTheStatusOfCallFromAdvancedSectionOfNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("5"));
		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AMNNR_Activity057", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject42 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes42;
		String relatedTo = AMNNR_RelatedTo42;

		String priority = AMNNR_AdvancePriority42;
		String status = AMNNR_AdvanceStatus42;
		String task1ButtonName = AMNNR_ActivityType54;
		String[] suggestedTags = AMNNR_SuggestedTag42.split("<break>", -1);
		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo43;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String recordName = AMNNR_FirmLegalName4;
		String recordType = AMNNR_FirmRecordType4;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo27.split("<break>", -1);

		String updatedNotesOfTask = task1Notes;
//		String updatedStatus = AMNNR_AdvanceStatus43;
//		String[][] task1UpdateBasicSection = null;
//		String[][] task1UpdateAdvancedSection = { { "Status", updatedStatus } };

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = AMNNR_ARelatedAsso27.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(1000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);

								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								/*
								 * if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								 * "Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
								 * log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
								 * ThreadSleep(1000);
								 * 
								 * if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
								 * task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
								 * log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);
								 * 
								 * CommonLib.refresh(driver);
								 * 
								 * ThreadSleep(1000);
								 * 
								 * if (BP.crossIconButtonInNotePopUp(5) != null) { click(driver,
								 * BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
								 * action.SCROLLANDBOOLEAN); } if (click(driver,
								 * BP.editButtonOnInteractionCard(task1SubjectName, 20), "Edit Note Button of: "
								 * + task1SubjectName, action.SCROLLANDBOOLEAN)) { log(LogStatus.INFO,
								 * "clicked on Edit Note button", YesNo.No);
								 * 
								 * String url2 = getURL(driver, 10);
								 * 
								 * ThreadSleep(1000); ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated
								 * = BP .verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
								 * updatedTask1BasicSectionVerification,
								 * updatedTask1AdvancedSectionVerification, null); if
								 * (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) { log(LogStatus.INFO,
								 * "Notes Popup has been verified and Notes popup is opening in same page with prefilled value"
								 * , YesNo.No);
								 * 
								 * } else { log(LogStatus.ERROR,
								 * "Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
								 * + NotesPopUpPrefilledNegativeResultUpdated, YesNo.No); sa.assertTrue(false,
								 * "Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
								 * + NotesPopUpPrefilledNegativeResultUpdated);
								 * 
								 * }
								 * 
								 * CommonLib.refresh(driver); CommonLib.ThreadSleep(5000); ArrayList<String>
								 * updatedresult = BP.verifyRecordOnInteractionCard( getAdvanceDueDate,
								 * IconType.Call, task1SubjectName, updatedNotesOfTask, true, false,
								 * updatedRelatedToVerifyInInteraction,
								 * updatedRelatedAssociationVerifyInInteraction); if (updatedresult.isEmpty()) {
								 * log(LogStatus.PASS, "------" + task1SubjectName +
								 * " record has been verified on intraction------", YesNo.No);
								 * 
								 * } else { log(LogStatus.ERROR, "------" + task1SubjectName +
								 * " record is not verified on intraction, Reason: " + updatedresult + "------",
								 * YesNo.No); sa.assertTrue(false, "------" + task1SubjectName +
								 * " record is not verified on intraction, Reason: " + updatedresult +
								 * "------"); }
								 * 
								 * } else { log(LogStatus.ERROR, "Not able to click on Edit Note button",
								 * YesNo.No); sa.assertTrue(false, "Not able to click on Edit Note button"); }
								 * 
								 * } else { log(LogStatus.FAIL, "Activity timeline record has not Updated",
								 * YesNo.No); sa.assertTrue(false, "Activity timeline record has not Updated");
								 * }
								 * 
								 * } else { log(LogStatus.ERROR, "Not able to click on Edit Note button",
								 * YesNo.No); sa.assertTrue(false, "Not able to click on Edit Note button"); }
								 */

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
	public void AcuityMNNRTc046_VerifyChangingTheDueDateToFutureOfCallFromAdvancedSectionOfNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity057", excelLabel.Advance_Due_Date);
		String getUpdatedAdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("4"));

		ExcelUtils.writeData(AcuityDataSheetFilePath, getUpdatedAdvanceDueDate, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity058", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject42 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes42;
		String relatedTo = AMNNR_ARelatedAsso27;

//		String priority = AMNNR_AdvancePriority42;
//		String status = AMNNR_AdvanceStatus43;

		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String recordName = AMNNR_FirmLegalName4;
		String recordType = AMNNR_FirmRecordType4;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo27.split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String[][] task1UpdateBasicSection = null;
		String[][] task1UpdateAdvancedSection = { { "Date", getUpdatedAdvanceDueDate } };

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = AMNNR_ARelatedAsso27.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Date", getUpdatedAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
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

								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(1000);

										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}
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
													getUpdatedAdvanceDueDate, IconType.Call, task1SubjectName,
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
	public void AcuityMNNRTc047_VerifyChangingTheAssigneeOfCallFromAdvancedSectionOfNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity058", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject42 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes42;
		String relatedTo = AMNNR_ARelatedAsso27;

//		String priority = AMNNR_AdvancePriority42;
//		String status = AMNNR_AdvanceStatus43;

		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String recordName = AMNNR_FirmLegalName4;
		String recordType = AMNNR_FirmRecordType4;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo27.split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String[][] task1UpdateBasicSection = null;
		String[][] task1UpdateAdvancedSection = { { "User", crmUser2FirstName + " " + crmUser2LastName } };

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = AMNNR_ARelatedAsso27.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser2FirstName + " " + crmUser2LastName } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
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

								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									ThreadSleep(10000);

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);

										ThreadSleep(1000);
										if (BP.crossIconButtonInNotePopUp(5) != null) {
											click(driver, BP.crossIconButtonInNotePopUp(20),
													"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
										}

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
													getAdvanceDueDate, IconType.Call, task1SubjectName,
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
	public void AcuityMNNRTc048_VerifyChangingTheSubjectOfCallFromNotesPopUpAndItsEffectOnInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity058", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject42 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes42;
		String relatedTo = AMNNR_ARelatedAsso27;

//		String priority = AMNNR_AdvancePriority42;
//		String status = AMNNR_AdvanceStatus43;

		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser2FirstName + " " + crmUser2LastName } };

		String recordName = AMNNR_FirmLegalName4;
		String recordType = AMNNR_FirmRecordType4;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo27.split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String task1UpdatedSubjectName = AMNNR_Subject44 + " " + AMNNR_ActivityType54;
		String[][] task1UpdateBasicSection = { { "Subject", task1UpdatedSubjectName } };

		String[][] task1UpdateAdvancedSection = null;

		String[] updatedSuggestedTags = "".split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = AMNNR_ARelatedAsso27.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1UpdatedSubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser2FirstName + " " + crmUser2LastName } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
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
					if (BP.crossIconButtonInNotePopUp(5) != null) {
						click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
								action.SCROLLANDBOOLEAN);
					}

					if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
							"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

						String url = getURL(driver, 10);

						ThreadSleep(1000);
						ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
								.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
										task1AdvancedSectionVerification, null);
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

						refresh(driver);
						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
							ThreadSleep(10000);

							if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
									task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
								log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

								CommonLib.refresh(driver);

								ThreadSleep(1000);
								if (BP.crossIconButtonInNotePopUp(5) != null) {
									click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
											action.SCROLLANDBOOLEAN);
								}

								if (click(driver, BP.editButtonOnInteractionCard(task1UpdatedSubjectName, 20),
										"Edit Note Button of: " + task1UpdatedSubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

									String url2 = getURL(driver, 10);

									ThreadSleep(1000);
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

								} else {
									log(LogStatus.ERROR,
											"Not able to click on Edit Note button of Task: " + task1UpdatedSubjectName,
											YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button of Task: "
											+ task1UpdatedSubjectName);
								}

								CommonLib.refresh(driver);

								if (BP.subjectOfInteractionCard(task1SubjectName, 7) == null) {
									log(LogStatus.INFO,
											"Verified: After Update the Name of Subject to " + task1UpdatedSubjectName
													+ ", Previous Named: " + task1SubjectName
													+ " Interaction card should not be there",
											YesNo.No);
								} else {
									log(LogStatus.ERROR,
											"After Update the Name of Subject to " + task1UpdatedSubjectName
													+ ", Previous Named: " + task1SubjectName
													+ " Interaction card is showing, which should not be there",
											YesNo.No);
									sa.assertTrue(false,
											"After Update the Name of Subject to " + task1UpdatedSubjectName
													+ ", Previous Named: " + task1SubjectName
													+ " Interaction card is showing, which should not be there");
								}

								ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(getAdvanceDueDate,
										IconType.Call, task1UpdatedSubjectName, updatedNotesOfTask, true, false,
										updatedRelatedToVerifyInInteraction,
										updatedRelatedAssociationVerifyInInteraction);
								if (updatedresult.isEmpty()) {
									log(LogStatus.PASS, "------" + task1SubjectName
											+ " record has been verified on intraction------", YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"------" + task1SubjectName
													+ " record is not verified on intraction, Reason: " + updatedresult
													+ "------",
											YesNo.No);
									sa.assertTrue(false,
											"------" + task1SubjectName
													+ " record is not verified on intraction, Reason: " + updatedresult
													+ "------");
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
						log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
						sa.assertTrue(false, "Not able to click on Edit Note button");
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
	public void AcuityMNNRTc049_VerifyWhenTheOrgHasSameDealNameAsOfTheCompanyNameAndIsTaggedInTheCall(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("8"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject45 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes45;
		String relatedTo = AMNNR_RelatedTo45;

//		String priority = AMNNR_AdvancePriority45;
//		String status = AMNNR_AdvanceStatus45;
		String task1ButtonName = AMNNR_ActivityType54;

		String[] SuggestedTags = AMNNR_SuggestedTag45.split("<break>", -1);

		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo46;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String recordName = AMNNR_FirmLegalName5;
		String recordType = AMNNR_FirmRecordType5;

		String[] RelatedToVerifyInInteraction = (crmUser1FirstName + " " + crmUser1LastName + AMNNR_ATRelatedTo28)
				.split("<break>", -1);

		String updatedNotesOfTask = AMNNR_Notes46;

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };

		String[] updatedSuggestedTags = AMNNR_SuggestedTag46.split("<break>", -1);

		String[] updatedRelatedToVerifyInInteraction = AMNNR_ATRelatedTo29.split("<break>", -1);
		String[] updatedRelatedAssociationVerifyInInteraction = null;

		String updatedRelatedToVerifyInNotes = (AMNNR_RelatedTo47 + "<break>" + AMNNR_SuggestedTag46);

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
				YesNo.No);

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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.crossIconButtonInNotePopUp(5) != null) {
							click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
									action.SCROLLANDBOOLEAN);
						}
						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(1000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSectionVerification, null);
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

							refresh(driver);

							if (BP.crossIconButtonInNotePopUp(5) != null) {
								click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
										action.SCROLLANDBOOLEAN);
							}
							if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
									"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
								ThreadSleep(1000);

								if (BP.updateActivityTimelineRecordForMultipleFollowUpTasksAndVerifyUIOfSuggestedTags(
										projectName, task1UpdateBasicSection, null, null, updatedSuggestedTags, null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									ThreadSleep(1000);
									if (BP.crossIconButtonInNotePopUp(5) != null) {
										click(driver, BP.crossIconButtonInNotePopUp(20),
												"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
									}

									ArrayList<String> subjectLinkPopUpNegativeResult = BP
											.verifySubjectLinkPopUpOnIntraction(driver, task1SubjectName,
													updatedTask1BasicSectionVerification,
													updatedTask1AdvancedSectionVerification, IconType.Call, PageName.AcuityDetails);

									if (subjectLinkPopUpNegativeResult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " record is able to open popup after click on it and verify its data"
												+ "------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + task1SubjectName
														+ " record link popup is not verified, Reason: "
														+ subjectLinkPopUpNegativeResult + "------",
												YesNo.Yes);
										sa.assertTrue(false,
												"------" + task1SubjectName
														+ " record link popup is not verified, Reason: "
														+ subjectLinkPopUpNegativeResult + "------");

									}

									if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
											"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

										String url2 = getURL(driver, 10);

										ThreadSleep(1000);
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

									} else {
										log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
										sa.assertTrue(false, "Not able to click on Edit Note button");
									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, IconType.Call, task1SubjectName, updatedNotesOfTask,
											true, false, updatedRelatedToVerifyInInteraction,
											updatedRelatedAssociationVerifyInInteraction);
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
								log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
								sa.assertTrue(false, "Not able to click on Edit Note button");
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
	public void AcuityMNNRTc050_VerifyWhenUserTagsAccountsAndContactsInNotesTextAreaAndClicksOnCloseButtonOrCrossIconInCaseOfCall(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("9"));
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject48 + " " + AMNNR_ActivityType54;
		String task1Notes = AMNNR_Notes48;
		String relatedTo = AMNNR_RelatedTo48;

//		String priority = AMNNR_AdvancePriority48;
//		String status = AMNNR_AdvanceStatus48;
		String task1ButtonName = AMNNR_ActivityType54;
		String[] suggestedTags = null;
		String verificationRelatedTo = relatedTo;

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Date", getAdvanceDueDate } };

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", verificationRelatedTo } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String[] RelatedToVerifyInInteraction = AMNNR_ATRelatedTo30.split("<break>", -1);

		String updatedNotesOfTask = task1Notes;

		String updatedRelatedToInNotes = AMNNR_RelatedTo49;
		String[][] task1UpdateBasicSection = { { "Related_To", updatedRelatedToInNotes } };

		String[] updatedSuggestedTags = null;

		String[] updatedRelatedToVerifyInInteraction = RelatedToVerifyInInteraction;
		String[] updatedRelatedAssociationVerifyInInteraction = AMNNR_ARelatedAsso30.split("<break>", -1);

		String updatedRelatedToVerifyInNotes = verificationRelatedTo;

		String[][] updatedTask1BasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] updatedTask1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);
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
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, IconType.Call,
							task1SubjectName, task1Notes, true, false, RelatedToVerifyInInteraction, null);
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

					if (BP.crossIconButtonInNotePopUp(5) != null) {
						click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
								action.SCROLLANDBOOLEAN);
					}
					if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
							"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

						String url = getURL(driver, 10);

						ThreadSleep(1000);
						ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
								.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
										task1AdvancedSectionVerification, null);
						if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
									YesNo.No);

							refresh(driver);

							if (BP.crossIconButtonInNotePopUp(5) != null) {
								click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
										action.SCROLLANDBOOLEAN);
							}
							if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
									"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
								ThreadSleep(1000);

								if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName,
										task1UpdateBasicSection, null, null, updatedSuggestedTags, null, true, false)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									ThreadSleep(1000);

									if (BP.crossIconButtonInNotePopUp(5) != null) {
										click(driver, BP.crossIconButtonInNotePopUp(20),
												"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
									}
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

									} else {
										log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
										sa.assertTrue(false, "Not able to click on Edit Note button");
									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, IconType.Call, task1SubjectName, updatedNotesOfTask,
											true, false, updatedRelatedToVerifyInInteraction,
											updatedRelatedAssociationVerifyInInteraction);
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
								log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
								sa.assertTrue(false, "Not able to click on Edit Note button");
							}

							refresh(driver);

							if (BP.crossIconButtonInNotePopUp(5) != null) {
								click(driver, BP.crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp",
										action.SCROLLANDBOOLEAN);
							}
							if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
									"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
								ThreadSleep(1000);

								if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName,
										task1UpdateBasicSection, null, null, updatedSuggestedTags, null, false, true)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									ThreadSleep(1000);
									if (BP.crossIconButtonInNotePopUp(5) != null) {
										click(driver, BP.crossIconButtonInNotePopUp(20),
												"Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);
									}

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

									} else {
										log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
										sa.assertTrue(false, "Not able to click on Edit Note button");
									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, IconType.Call, task1SubjectName, updatedNotesOfTask,
											true, false, updatedRelatedToVerifyInInteraction,
											updatedRelatedAssociationVerifyInInteraction);
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
	public void AcuityMNNRTc051_VerifyWhenEditButtonIsClickedForTheCallHavingFollowUpTaskAndIsDeletedFromTaskDetailPage(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String task1SubjectName = AMNNR_Subject17 + " " + AMNNR_ActivityType54;

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjectName + " in Note PopUp Section---------", YesNo.No);

		if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

			log(LogStatus.INFO, "-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
					YesNo.No);

			if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

				if (click(driver, taskBP.buttonInTheDownArrowList("Delete", 20), "Delete Button in downArrowButton",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Delete Button in  Down Arrow Button", YesNo.No);

					if (click(driver, taskBP.taskDeleteConfirmButton(15), "Delete Button in downArrowButton",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Delete Confirm Button", YesNo.No);

						if (taskBP.taskDeletedMsg(15) != null) {
							log(LogStatus.INFO, "Task Delete Msg displayed, So Task has been deleted", YesNo.No);

							log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjectName
									+ " is present or not in Interaction Section---------", YesNo.No);
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

										if (!BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(
												task1SubjectName)) {
											log(LogStatus.INFO, "Verified: Task: " + task1SubjectName
													+ " is not present there after delete", YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"Task: " + task1SubjectName + " is present there after delete",
													YesNo.Yes);
											sa.assertTrue(false,
													"Task: " + task1SubjectName + " is present there after delete");

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
							log(LogStatus.ERROR, "Task Delete Msg not display, So Task not gets deleted", YesNo.Yes);
							sa.assertTrue(false, "Task Delete Msg not display, So Task not gets deleted");

						}

					} else {
						log(LogStatus.ERROR, "Not ABle to Click on Delete Confirm Button", YesNo.Yes);
						sa.assertTrue(false, "Not ABle to Click on Delete Confirm Button");

					}

				} else {
					log(LogStatus.ERROR, "Not Able Click on Delete button in Down Arrow Button", YesNo.Yes);
					sa.assertTrue(false, "Not Able Click on Delete button in Down Arrow Button");

				}

			} else {
				log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
				sa.assertTrue(false, "Not Able Click on Down Arrow Button");

			}

		} else {

			log(LogStatus.ERROR, "-----Task named: " + task1SubjectName + " not found in Tasks Object-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc052_VerifyWhenTheRemovedCallIsRestoredAndItsImpactOnInteractionSection(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String recordName = AMNNR_FirmLegalName3;
		String recordType = AMNNR_FirmRecordType3;

		String AdvanceDueDate = ExcelUtils.readData(AcuityDataSheetFilePath, "Activity Timeline",
				excelLabel.Variable_Name, "AMNNR_Activity056", excelLabel.Advance_Due_Date);
		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AMNNR_Subject17 + " " + AMNNR_ActivityType54;
		String updatedNotesOfTask = AMNNR_Notes21;
		String relatedTo = AMNNR_RelatedTo17;

//		String priority = AMNNR_AdvancePriority17;
//		String status = AMNNR_AdvanceStatus17;

		String verificationRelatedTo = relatedTo + AMNNR_RelatedTo21;
		String updatedRelatedTo = AMNNR_RelatedTo22;
		String updatedRelatedToVerifyInNotes = verificationRelatedTo + "<break>" + updatedRelatedTo;

		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", updatedNotesOfTask },
				{ "Related_To", updatedRelatedToVerifyInNotes } };

		String[][] task1AdvancedSectionVerification = { { "Date", getAdvanceDueDate },
				{ "User", crmUser1FirstName + " " + crmUser1LastName } };

		String[][] listViewSheetData = { { AMNNR_ListViewMember1, AMNNR_ListViewTabName1, task1SubjectName,
				AMNNR_ListViewAccessibility1, AMNNR_ListViewFilter1, AMNNR_ListViewField1, AMNNR_ListViewOperators1,
				task1SubjectName, AMNNR_ListViewTextBoxType1 } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

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

										if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(
												task1SubjectName)) {
											log(LogStatus.INFO, "Verified: Task: " + task1SubjectName
													+ " is present there after restore", YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"Task: " + task1SubjectName + " is not present there after restore",
													YesNo.Yes);
											sa.assertTrue(false, "Task: " + task1SubjectName
													+ " is not present there after restore");

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

							if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

								log(LogStatus.INFO,
										"-----Verified Task named: " + task1SubjectName + " found in Tasks Object-----",
										YesNo.No);

								if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

									if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
											"Delete Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);

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
										log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
												YesNo.Yes);

									}

								} else {
									log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
									sa.assertTrue(false, "Not Able Click on Down Arrow Button");

								}

							} else {

								log(LogStatus.ERROR,
										"-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
										YesNo.Yes);
								BaseLib.sa.assertTrue(false,
										"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

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
	public void AcuityMNNRTc053_CreateAFollowUpTaskFromCallDetailPageAndVerifyItsDetailsOnTaskDetailPage(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String task1SubjectNameNavigation = AMNNR_Subject30 + " " + AMNNR_ActivityType54;
		String task1SubjectName = AMNNR_Subject55;
		String task1Notes = AMNNR_Notes55;
		String relatedTo = AMNNR_RelatedTo55;

		String recordName = AMNNR_Contact5;
		String recordNameVerify = AMNNR_RelatedTo55;
		
		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("1"));

		String getAdvanceDueDate = AdvanceDueDate;
		String priority = "Normal";
		String status = "Not Started";


		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };
		
		String[][] task1AdvancedSection = { { "Due Date", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[] labelAndValueSeprateByBreak = { "User" + "<break>" + crmUser1FirstName + " " + crmUser1LastName,
				"Subject" + "<break>" + task1SubjectName,

				"Name" + "<break>" + relatedTo };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (home.globalSearchAndNavigate(task1SubjectNameNavigation, "Tasks", false)) {

			log(LogStatus.INFO,
					"-----Verified Task named: " + task1SubjectNameNavigation + " found in Tasks Object-----",
					YesNo.No);
			if (taskBP.clickOnRecordPageButtonOrInDownArrowButton("Create Follow-Up Task", 20)) {
				log(LogStatus.INFO, "Clicked on Create Follow-Up Task Button", YesNo.No);

				CommonLib.ThreadSleep(5000);
				if (BP.updateActivityTimelineRecord(projectName, task1BasicSection, task1AdvancedSection, null, null, null)) {
					log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

					if (lp.clickOnTab(projectName, tabObj2)) {

						log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

						if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordNameVerify,
								30)) {
							log(LogStatus.INFO, recordNameVerify + " record has been open", YesNo.No);
							ThreadSleep(4000);
							if (BP.clicktabOnPage("Acuity")) {
								log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

								if (home.globalSearchAndNavigate(task1SubjectName, "Tasks", false)) {

									log(LogStatus.INFO, "-----Verified Task named: " + task1SubjectName
											+ " found in Tasks Object-----", YesNo.No);

									CommonLib.ThreadSleep(8000);
									List<String> taskDetailPageNegativeResult = BP
											.fieldValueVerification(labelAndValueSeprateByBreak);

									if (taskDetailPageNegativeResult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " labels and their values in Detail page has been verified------",
												YesNo.No);

									} else {
										log(LogStatus.ERROR, "------" + task1SubjectName
												+ " labels and their values in Detail page has not been verified, Reason: "
												+ taskDetailPageNegativeResult + "------", YesNo.No);
										sa.assertTrue(false, "------" + task1SubjectName
												+ " labels and their values in Detail page has not been verified, Reason: "
												+ taskDetailPageNegativeResult + "------");

									}

								} else {

									log(LogStatus.ERROR,
											"-----Task named: " + task1SubjectName + " not found in Tasks Object-----",
											YesNo.Yes);
									BaseLib.sa.assertTrue(false,
											"-----Task named: " + task1SubjectName + " not found in Tasks Object-----");

								}

							} else {
								log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
								sa.assertTrue(false, "Not able to click on Acuity Tab");
							}

						} else {
							log(LogStatus.ERROR, "Not able to open " + recordNameVerify + " record", YesNo.No);
							sa.assertTrue(false, "Not able to open " + recordNameVerify + " record");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
						sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
					}

				} else {
					log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
					sa.assertTrue(false, "Activity timeline record has not Updated");

				}

			} else {
				log(LogStatus.ERROR, "Not Able Click on Create Follow-Up Task button", YesNo.Yes);
				sa.assertTrue(false, "Not Able Click on Create Follow-Up Task button");

			}

		} else {

			log(LogStatus.ERROR, "-----Task named: " + task1SubjectNameNavigation + " not found in Tasks Object-----",
					YesNo.Yes);
			BaseLib.sa.assertTrue(false,
					"-----Task named: " + task1SubjectNameNavigation + " not found in Tasks Object-----");

		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

}