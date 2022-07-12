package com.navatar.scripts;

import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.removeNumbersFromString;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonVariables.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.FieldAndRelationshipPageBusinessLayer;
import com.navatar.pageObjects.FirmPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class PEFSTG extends BaseLib {

	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc001_CreateCRMUser(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",
				excelLabel.User_Last_Name);
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
					if (setup.createPEUser(crmUser1FirstName, UserLastName, emailId, crmUserLience, crmUserProfile)) {
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

		closeBrowser();
		driver.switchTo().window(parentWindow);
		lp.CRMlogout();
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
	public void PEFSTGTc002_VerifyAppsOrLightningPages(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		if (home.verifyPECloudOnHomePage()) {
			log(LogStatus.PASS, "PE Cloud is availale on the home page", YesNo.No);
			sa.assertTrue(true, "PE Cloud is availale on the home page");
		} else {
			log(LogStatus.FAIL, "PE Cloud is not availale on the home page", YesNo.Yes);
			sa.assertTrue(false, "PE Cloud is not availale on the home page");
		}

		if (home.VerifyOnlyPECloudOnLauncher()) {
			log(LogStatus.PASS,
					"PE Could is appearing on the App Launcher page and No other Apps is Appearing on the View All of App Launcher",
					YesNo.No);
			sa.assertTrue(true,
					"PE Could is appearing on the App Launcher page and No other Apps is Appearing on the App Launcher");
		} else {
			log(LogStatus.FAIL,
					"Either PE Could is not appearing on the App Launcher page Or other Apps are Appearing on the View All of App Launcher",
					YesNo.Yes);
			sa.assertTrue(false,
					"Either PE Could is not appearing on the App Launcher page Or other Apps are Appearing on the View All of App Launcher");
		}
		CommonLib.refresh(driver);
		if (home.VerifyOnlyPECloudOnViewAll()) {
			log(LogStatus.PASS,
					"PE Could is appearing on the View All of App Launcher page and No other Apps is Appearing on the View All of App Launcher",
					YesNo.No);
			sa.assertTrue(true,
					"PE Could is appearing on the View All of App Launcher page and No other Apps is Appearing on the View All of App Launcher");
		} else {
			log(LogStatus.FAIL,
					"Either PE Could is not appearing on the View All of App Launcher page Or other Apps are Appearing on the View All of App Launcher",
					YesNo.Yes);
			sa.assertTrue(false,
					"Either PE Could is not appearing on the View All of App Launcher page Or other Apps are Appearing on the View All of App Launcher");
		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc003_VerifyTabsOnHomePage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		ArrayList<String> tabsForAdmin = new ArrayList<String>();
		tabsForAdmin.add("Home");
		tabsForAdmin.add("Firms");
		tabsForAdmin.add("Contacts");
		tabsForAdmin.add("Deals");
		tabsForAdmin.add("Deal Center");
		tabsForAdmin.add("Fundraisings");
		tabsForAdmin.add("Funds");
		tabsForAdmin.add("Fund Center");
		tabsForAdmin.add("Reports");
		tabsForAdmin.add("Dashboards");
		tabsForAdmin.add("Navatar Setup");

		ArrayList<String> result = home.verifyHomePageTabs(tabsForAdmin);
		if (result.isEmpty()) {
			log(LogStatus.PASS, "Homepage tabs has been verified " + tabsForAdmin, YesNo.Yes);
			sa.assertTrue(true, "Homepage tabs has been verified " + tabsForAdmin);

		} else {
			log(LogStatus.FAIL, "Homepage tabs is not matched " + result, YesNo.Yes);
			sa.assertTrue(false, "Homepage tabs is not matched " + result);
		}

		lp.CRMlogout();
		ThreadSleep(12000);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ArrayList<String> tabsForuser = new ArrayList<String>();
		tabsForuser.add("Home");
		tabsForuser.add("Firms");
		tabsForuser.add("Contacts");
		tabsForuser.add("Deals");
		tabsForuser.add("Deal Center");
		tabsForuser.add("Fundraisings");
		tabsForuser.add("Funds");
		tabsForuser.add("Fund Center");
		tabsForuser.add("Reports");
		tabsForuser.add("Dashboards");

		ArrayList<String> resultuser = home.verifyHomePageTabs(tabsForuser);
		if (resultuser.isEmpty()) {
			log(LogStatus.PASS, "Homepage tabs has been verified " + tabsForuser, YesNo.Yes);
			sa.assertTrue(true, "Homepage tabs has been verified " + tabsForuser);

		} else {
			log(LogStatus.FAIL, "Homepage tabs is not matched " + resultuser, YesNo.Yes);
			sa.assertTrue(false, "Homepage tabs is not matched " + resultuser);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc004_VerifyRecordTypeOnFirm(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		FirmPageBusinessLayer fb = new FirmPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {

			String parentWindowID = switchOnWindow(driver);
			if (parentWindowID == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create App Page");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create App Page",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create App Page");
			}

			if (setup.searchStandardOrCustomObject(projectName, mode, object.Firm)) {

				if (setup.clickOnObjectFeature(projectName, mode, object.Firm, ObjectFeatureName.recordTypes)) {
					ArrayList<String> recordName = new ArrayList<String>();
					recordName.add("Advisor");
					recordName.add("Company");
					recordName.add("Fund Manager");
					recordName.add("Fund Managers Fund");
					recordName.add("Individual Investor");
					recordName.add("Institution");
					recordName.add("Intermediary");
					recordName.add("Lender");
					recordName.add("Limited Partner");
					recordName.add("Portfolio Company");

					ArrayList<String> activeName = new ArrayList<String>();
					activeName.add("True");
					activeName.add("True");
					activeName.add("False");
					activeName.add("False");
					activeName.add("False");
					activeName.add("True");
					activeName.add("True");
					activeName.add("True");
					activeName.add("True");
					activeName.add("True");

					ArrayList<String> result = setup.verifyRecordTypeAndActivityStatusOnCompanyObject(recordName,
							activeName);
					if (result.isEmpty()) {
						log(LogStatus.INFO,
								"Record Name and Activity Status has been verified " + recordName + activeName,
								YesNo.Yes);
						sa.assertTrue(true,
								"Record Name and Activity Status has been verified " + recordName + activeName);
					} else {
						log(LogStatus.ERROR, "Either Record name or Activity status is not matched " + result,
								YesNo.Yes);
						sa.assertTrue(false, "Either Record name or Activity status is not matched " + result);
					}
				} else {
					log(LogStatus.FAIL, "Not able to click on the Object feature name", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on the Object feature name");
				}
			} else {
				log(LogStatus.FAIL, "Not able to search the Standard or Custom Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to search the Standard or Custom Object");

			}

			driver.close();
			driver.switchTo().window(parentWindowID);
		} else {
			log(LogStatus.FAIL, "Not able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not able to open the setup page");
		}
		lp.CRMlogout();
		ThreadSleep(12000);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ArrayList<String> data = new ArrayList<String>();

		data.add("Company");
		data.add("Advisor");
		data.add("Institution");
		data.add("Intermediary");
		data.add("Lender");
		data.add("Limited Partner");
		data.add("Portfolio Company");

		if (BP.clickOnTab(projectName, "Firms")) {

			ArrayList<String> result = fb.verifyFirmRecordType(data);

			if (result.isEmpty()) {
				log(LogStatus.PASS, "Record type name has been verified " + data, YesNo.Yes);
				sa.assertTrue(true, "Record type name has been verified " + data);
			} else {
				log(LogStatus.PASS, "Record type name is not matched " + result, YesNo.Yes);
				sa.assertTrue(true, "Record type name is not matched " + result);
			}
		} else {
			log(LogStatus.FAIL, "Not able to open the Tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to open the Tab");
		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc005_VerifyDescriptionOfFirmRecordTypes(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		FirmPageBusinessLayer fb = new FirmPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {

			String parentWindowID = switchOnWindow(driver);
			if (parentWindowID == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create App Page");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create App Page",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create App Page");
			}

			if (setup.searchStandardOrCustomObject(projectName, mode, object.Firm)) {

				if (setup.clickOnObjectFeature(projectName, mode, object.Firm, ObjectFeatureName.recordTypes)) {
					ArrayList<String> description = new ArrayList<String>();
					description.add(
							"An Advisor is a consulting or advisory firm that supports deal processes but is not an Intermediary.");
					description.add(
							"A Company is an operating asset that is a potential acquisition / investment target.");
					description.add("An Institution is an existing or prospective investor.");
					description.add(
							"An Intermediary is a financial services firm that negotiates sell-side or buy-side transactions (e.g. investment banks, brokerages, advisors etc.)");
					description.add(
							"A Lender is an investment firm specializing in lending or debt investments (e.g., mezzanine or distressed debt funds).");
					description
							.add("A Limited Partner is the legal entity an Institution uses to invest in your funds.");
					description.add(
							"A Portfolio Company is an operating asset that is currently or historically part of your firms portfolio.");

					ArrayList<String> recordName = new ArrayList<String>();
					recordName.add("Advisor");
					recordName.add("Company");
					recordName.add("Institution");
					recordName.add("Intermediary");
					recordName.add("Lender");
					recordName.add("Limited Partner");
					recordName.add("Portfolio Company");

					ArrayList<String> result = setup.verifyDescriptionOnFirm(recordName, description);
					if (result.isEmpty()) {
						log(LogStatus.INFO, "Description has been verified " + description, YesNo.Yes);
						sa.assertTrue(true, "Description has been verified " + description);
					} else {
						log(LogStatus.ERROR, "Description is not matched " + result, YesNo.Yes);
						sa.assertTrue(false, "Description is not matched " + result);
					}
				} else {
					log(LogStatus.FAIL, "Not able to click on the Object feature name", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on the Object feature name");
				}
			} else {
				log(LogStatus.FAIL, "Not able to search the Standard or Custom Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to search the Standard or Custom Object");

			}

			driver.close();
			driver.switchTo().window(parentWindowID);
		} else {
			log(LogStatus.FAIL, "Not able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not able to open the setup page");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc006_VerifyDefaultRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		FirmPageBusinessLayer fb = new FirmPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		String parentWindowID = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {

			parentWindowID = switchOnWindow(driver);
			if (parentWindowID == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create App Page");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create App Page",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create App Page");
			}

			if (setup.searchStandardOrCustomObject(projectName, mode, object.Profiles)) {
				log(LogStatus.PASS, "Profile page has been open", YesNo.No);

				if (setup.VerifyDefaultRecordTypeForObject("System Administrator", "Company")) {
					log(LogStatus.PASS, "Default record company has been verified for System Administrator", YesNo.Yes);
					sa.assertTrue(true, "Default record company has been verified for System Administrator");
				} else {
					log(LogStatus.FAIL, "Default record company is not verified for System Administrator", YesNo.Yes);
					sa.assertTrue(false, "Default record company is not verified for System Administrator");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open the Profile page", YesNo.Yes);
				sa.assertTrue(false, "Not able to open the Profile page");
			}

			CommonLib.switchToDefaultContent(driver);
			ThreadSleep(2000);
			CommonLib.refresh(driver);
			ThreadSleep(2000);
			if (setup.searchStandardOrCustomObject(projectName, mode, object.Profiles)) {
				log(LogStatus.PASS, "Profile page has been open", YesNo.No);

				if (setup.VerifyDefaultRecordTypeForObject("Standard User", "Company")) {
					log(LogStatus.PASS, "Default record company has been verified for Standard User", YesNo.Yes);
					sa.assertTrue(true, "Default record company has been verified for Standard User");
				} else {
					log(LogStatus.ERROR, "Default record company is not verified for Standard User", YesNo.Yes);
					sa.assertTrue(false, "Default record company is not verified for Standard User");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open the Profile page", YesNo.Yes);
				sa.assertTrue(false, "Not able to open the Profile page");
			}
		} else {
			log(LogStatus.ERROR, "Not able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not able to open the setup page");
		}

		driver.close();
		driver.switchTo().window(parentWindowID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc008_VerifyListViewsOnFirmObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ArrayList<String> listview = new ArrayList<String>();
		listview.add("All Advisors");
		listview.add("All Companies");
		listview.add("All Firms");
		listview.add("All Institutions");
		listview.add("All Intermediaries");
		listview.add("All Lenders");
		listview.add("All Limited Partners");
		listview.add("All Portfolio Companies");
		listview.add("Recently Viewed  (Pinned list)");
		listview.add("Recently Viewed Firms");

		if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
			ArrayList<String> result = FB.verifyListViewOfFirm(listview);
			if (result.isEmpty()) {
				log(LogStatus.PASS, "All list view has been matched on the firm tab " + listview, YesNo.No);
				sa.assertTrue(true, "All list view has been matched on the firm tab " + listview);
			} else {
				log(LogStatus.ERROR, "List view is not matched on the firm tab " + result, YesNo.No);
				sa.assertTrue(false, "List view is not matched on the firm tab " + result);
			}
		} else {
			sa.assertTrue(false, "Not able to clicked on Firms Tab");
			log(LogStatus.SKIP, "Not Able to clicked on Firms Tab", YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc009_VerifyFieldsOnListViewsOnFirmObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ArrayList<String> listview = new ArrayList<String>();
		listview.add("All Advisors");
		listview.add("All Companies");
		listview.add("All Firms");
		listview.add("All Institutions");
		listview.add("All Intermediaries");
		listview.add("All Lenders");
		listview.add("All Limited Partners");
		listview.add("All Portfolio Companies");
		listview.add("Recently Viewed  (Pinned list)");
		listview.add("Recently Viewed Firms");

		String[][] fieldData = { { "Legal Name", "Entity Type", "City", "State", "Website" },
				{ "Legal Name", "Entity Type", "Tier", "Status", "Industry", "City", "State", "Website" },
				{ "Legal Name", "Entity Type", "Record Type", "City", "State", "Website" } };

		if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);

		} else {
			sa.assertTrue(false, "Not able to clicked on Firms Tab");
			log(LogStatus.SKIP, "Not Able to clicked on Firms Tab", YesNo.Yes);
		}
	}

	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc0010_VerifyFiltersOnListViews(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String[] listViewName = PEFSTG_10_ListViewName.split("<BreakOn>");
		String[] filterValue = PEFSTG_10_FilterValue.split("<BreakOn>");
		String[] filter = PEFSTG_10_Filter.split("<BreakOn>");
		String[] field = PEFSTG_10_Field.split("<BreakOn>");
		String[] operators = PEFSTG_10_Operators.split("<BreakOn>");
		String[] Filter_Condition = PEFSTG_10_FilterCondition.split("<BreakOn>");

		if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
			ArrayList<String> result = FB.verifyFilterOnListView(listViewName, filter, field, operators, filterValue,
					Filter_Condition);
			if (result.isEmpty()) {
				log(LogStatus.PASS, "Filter has been verified on the List view " + listViewName, YesNo.No);
				sa.assertTrue(true, "Filter has been verified on the List view " + listViewName);
			} else {
				log(LogStatus.FAIL, "Filter is not verified on the List view " + result, YesNo.Yes);
				sa.assertTrue(false, "Filter is not verified on the List view " + result);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on the firm tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on the firm tab");
		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc0011_VerifyEntityTypeFieldOnFirmObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer FRB = new FieldAndRelationshipPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		if (home.clickOnSetUpLink()) {

			String parentWindowID = switchOnWindow(driver);
			if (parentWindowID == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create App Page");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create App Page",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create App Page");
			}

			if (setup.searchStandardOrCustomObject(projectName, mode, object.Firm)) {

				if (setup.clickOnObjectFeature(projectName, mode, object.Firm,
						ObjectFeatureName.FieldAndRelationShip)) {
					log(LogStatus.INFO, "Clicked on the Field and Relationship object feature name", YesNo.No);

					if (FRB.verifyField("Entity Type")) {
						log(LogStatus.FAIL, "Entity type field is visible on the firm object", YesNo.No);
						sa.assertTrue(true, "Entity type field is visible on the firm object");

					} else {
						log(LogStatus.ERROR, "Entity type field is not visible on the firm object", YesNo.Yes);
						sa.assertTrue(false, "Entity type field is not visible on the firm object");
					}

				} else {
					log(LogStatus.ERROR, "Could not click on the Field and Relationship object feature name",
							YesNo.Yes);
					sa.assertTrue(false, "Could not click on the Field and Relationship object feature name");
				}
			} else {
				log(LogStatus.ERROR, "Could not click on the Firm Object manager name", YesNo.Yes);
				sa.assertTrue(false, "Could not click on the Firm Object manager name");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on the setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on the setup link");
		}

	}

	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTcS002_VerifyFiltersOnListViews(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);

		String[] listViewName = "Recently Viewed (Pinned list)<BreakOn>All Contacts<BreakOn>My Call List<BreakOn>Recently Viewed Contacts"
				.split("<BreakOn>");
		String[] listViews;
		String[] filterValue;
		String[] filter;
		String[] field;
		String[] operators;
		String[] Filter_Condition;
		List<String> listViewNames = new ArrayList<String>();

		String[][] listViewSheetData = {
				{ PEFSTGLV_1_Member, PEFSTGLV_1_TabName, PEFSTGLV_1_ListViewName, PEFSTGLV_1_Filter, PEFSTGLV_1_Field,
						PEFSTGLV_1_Operators, PEFSTGLV_1_FilterValue, PEFSTGLV_1_FilterCondition },
				{ PEFSTGLV_2_Member, PEFSTGLV_2_TabName, PEFSTGLV_2_ListViewName, PEFSTGLV_2_Filter, PEFSTGLV_2_Field,
						PEFSTGLV_2_Operators, PEFSTGLV_2_FilterValue, PEFSTGLV_2_FilterCondition },
				{ PEFSTGLV_3_Member, PEFSTGLV_3_TabName, PEFSTGLV_3_ListViewName, PEFSTGLV_3_Filter, PEFSTGLV_3_Field,
						PEFSTGLV_3_Operators, PEFSTGLV_3_FilterValue, PEFSTGLV_3_FilterCondition } };

		for (String[] row : listViewSheetData) {

			listViews = row[2].split("<BreakOn>");
			filterValue = row[6].split("<BreakOn>");
			filter = row[3].split("<BreakOn>");
			field = row[4].split("<BreakOn>");
			operators = row[5].split("<BreakOn>");
			Filter_Condition = row[7].split("<BreakOn>");
			listViewNames = Arrays.asList(listViewName);

			if (row[0].trim().equalsIgnoreCase("User1")) {

				lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
			} else if (row[0].trim().equalsIgnoreCase("User2")) {
				lp.CRMlogout();
				lp.CRMLogin(crmUser2EmailID, adminPassword, appName);
			} else if (row[0].trim().equalsIgnoreCase("admin")) {
				lp.CRMlogout();
				lp.CRMLogin(superAdminUserName, adminPassword, appName);
			}

			if (lp.clickOnTab(projectName, row[1])) {
				log(LogStatus.INFO, "Click on Tab : " + row[1], YesNo.No);

				List<String> notVerifiedListViews = FB.verifyListView(listViewNames);
				if (notVerifiedListViews.isEmpty()) {
					log(LogStatus.PASS, "List Views Presence has been verified: " + listViewNames, YesNo.No);

				} else {
					log(LogStatus.FAIL, "List Views Presence has not been verified: " + notVerifiedListViews.toString()
							+ " So not able to verify Filters for that List Views", YesNo.Yes);
					sa.assertTrue(false, "List Views Presence has not been verified: " + notVerifiedListViews.toString()
							+ " So not able to verify Filters for that List Views");
				}

				List<String> notVerifiedListViewsFilters = FB.verifyFilterOnListView(listViews, filter, field,
						operators, filterValue, Filter_Condition);

				if (notVerifiedListViewsFilters.isEmpty()) {
					log(LogStatus.PASS, "Filters of List Views has been verified: " + row[2], YesNo.No);

				} else {
					log(LogStatus.FAIL, "Filters of List Views has not been verified for : "
							+ notVerifiedListViewsFilters.toString(), YesNo.Yes);
					sa.assertTrue(false, "Filters of List Views has not been verified for : "
							+ notVerifiedListViewsFilters.toString());
				}
			}

			else {
				log(LogStatus.FAIL, "could not click on " + row[1], YesNo.Yes);
				sa.assertTrue(false, "could not click on " + row[1]);
			}

			ThreadSleep(5000);
			lp.CRMlogout();
		}

		sa.assertAll();

	}

}
