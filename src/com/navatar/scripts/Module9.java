package com.navatar.scripts;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.FindElements;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.clickUsingJavaScript;
import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.isDisplayed;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.removeNumbersFromString;
import static com.navatar.generic.CommonLib.sendKeysAndPressEnter;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonVariables.*;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import java.sql.DriverAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.EnumConstants.Condition;
import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.DataImportType;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.FundPageFieldLabelText;
import com.navatar.generic.EnumConstants.HTMLTAG;
import com.navatar.generic.EnumConstants.IconType;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.ObjectName;
import com.navatar.generic.EnumConstants.ObjectType;
import com.navatar.generic.EnumConstants.PermissionType;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.ReportField;
import com.navatar.generic.EnumConstants.ReportFormatName;
import com.navatar.generic.EnumConstants.SDGCreationLabel;
import com.navatar.generic.EnumConstants.SDGGridName;
import com.navatar.generic.EnumConstants.SDGLabels;
import com.navatar.generic.EnumConstants.SortOrder;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.DataLoaderWizardPageBusinessLayer;
import com.navatar.pageObjects.EditPageBusinessLayer;
import com.navatar.pageObjects.FieldAndRelationshipPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.FundraisingsPageBusinessLayer;
import com.navatar.pageObjects.FundsPage;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePage;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.HomePageErrorMessage;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LightningAppBuilderPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.ReportsTab;
import com.navatar.pageObjects.ReportsTabBusinessLayer;
import com.navatar.pageObjects.SDGPageBusinessLayer;
import com.navatar.pageObjects.SDGPageErrorMessage;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class Module9 extends BaseLib {

	@Parameters({ "projectName" })

	@Test
	public void M9Tc001_1_CreateCRMUser1(String projectName) {
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
	public void M9Tc001_2_CreateCRMUser2(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser2LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName2);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User2",
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
								"No new window is open after click on setup link in lighting mode so cannot create CRM User2");
						log(LogStatus.SKIP,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User2",
								YesNo.Yes);
						exit("No new window is open after click on setup link in lighting mode so cannot create CRM User2");
					}
					if (setup.createPEUser(crmUser2FirstName, UserLastName, emailId, crmUserLience, crmUserProfile)) {
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
				if (setup.installedPackages(crmUser2FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + crmUser2FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in CRM User1: " + crmUser2FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in CRM User1: " + crmUser2FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in CRM User1: " + crmUser2FirstName + " " + UserLastName,
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
					ExcelUtils.readDataFromPropertyFile("gmailUserName2"),
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
	public void

			M9Tc001_3_createACustomObjectAndTabAndGivePermissions(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		DataLoaderWizardPageBusinessLayer dataload = new DataLoaderWizardPageBusinessLayer(driver);
		CustomObjPageBusinessLayer customObjPageBusinessLayer = new CustomObjPageBusinessLayer(driver);

		String[] userTypesToGivePermissions = { "PE Standard User" };

		customObjPageBusinessLayer.CreateACustomObject(projectName, M9FC_1_ObjectName, userTypesToGivePermissions);

	}

	@Parameters({ "projectName" })

	@Test
	public void

			M9Tc001_4_createACustomObjectAndTabAndGivePermissions(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		DataLoaderWizardPageBusinessLayer dataload = new DataLoaderWizardPageBusinessLayer(driver);
		CustomObjPageBusinessLayer customObjPageBusinessLayer = new CustomObjPageBusinessLayer(driver);

		String[] userTypesToGivePermissions = { "PE Standard User" };

		customObjPageBusinessLayer.CreateACustomObject(projectName, M9FC_10_ObjectName, userTypesToGivePermissions);

	}

	@Parameters({ "projectName" })

	@Test
	public void

			M9Tc001_5_createFieldsForCustomObjects(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		DataLoaderWizardPageBusinessLayer dataload = new DataLoaderWizardPageBusinessLayer(driver);
		CustomObjPageBusinessLayer customObjPageBusinessLayer = new CustomObjPageBusinessLayer(driver);

		String[][] labelAndValues = {
				{ M9FC_1_FieldType, M9FC_1_FieldLabel, excelLabel.Length.toString(), M9FC_1_FieldValues,
						M9FC_1_ObjectName },
				{ M9FC_2_FieldType, M9FC_2_FieldLabel, excelLabel.Length.toString(), M9FC_2_FieldValues,
						M9FC_2_ObjectName },
				{ M9FC_3_FieldType, M9FC_3_FieldLabel, excelLabel.Length.toString(), M9FC_3_FieldValues,
						M9FC_3_ObjectName },
				{ M9FC_4_FieldType, M9FC_4_FieldLabel, excelLabel.Length.toString(), M9FC_4_FieldValues,
						M9FC_4_ObjectName },
				{ M9FC_5_FieldType, M9FC_5_FieldLabel, excelLabel.Length.toString(), M9FC_5_FieldValues,
						M9FC_5_ObjectName },
				{ M9FC_6_FieldType, M9FC_6_FieldLabel, excelLabel.Related_To.toString(), M9FC_6_FieldValues,
						M9FC_6_ObjectName },
				{ M9FC_7_FieldType, M9FC_7_FieldLabel, excelLabel.Length.toString(), M9FC_7_FieldValues,
						M9FC_7_ObjectName },
				{ M9FC_8_FieldType, M9FC_8_FieldLabel, excelLabel.Length.toString(), M9FC_8_FieldValues,
						M9FC_8_ObjectName },
				{ M9FC_9_FieldType, M9FC_9_FieldLabel, excelLabel.Length.toString(), M9FC_9_FieldValues,
						M9FC_9_ObjectName },
				{ M9FC_10_FieldType, M9FC_10_FieldLabel, excelLabel.Related_To.toString(), M9FC_10_FieldValues,
						M9FC_10_ObjectName },
				{ M9FC_11_FieldType, M9FC_11_FieldLabel, excelLabel.Related_To.toString(), M9FC_11_FieldValues,
						M9FC_11_ObjectName } };

		setup.createFieldsForCustomObject(projectName, labelAndValues);

	}

	@Parameters({ "projectName" })

	@Test
	public void

			M9Tc001_6_verifyPreconditionRecordsDataImportWizardAdmin(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		DataLoaderWizardPageBusinessLayer dataload = new DataLoaderWizardPageBusinessLayer(driver);
		CustomObjPageBusinessLayer customObjPageBusinessLayer = new CustomObjPageBusinessLayer(driver);

		/*
		 * dataload.addFieldsToLayoutPageInAdminCase(M9AFTPL_1_FieldNames,
		 * M9AFTPL_1_PageLayoutName, M9AFTPL_1_ObjectName);
		 * dataload.addFieldsToLayoutPageInAdminCase(M9AFTPL_2_FieldNames,
		 * M9AFTPL_2_PageLayoutName, M9AFTPL_2_ObjectName);
		 * dataload.addFieldsToLayoutPageInAdminCase(M9AFTPL_3_FieldNames,
		 * M9AFTPL_3_PageLayoutName, M9AFTPL_3_ObjectName);
		 * dataload.addFieldsToLayoutPageInAdminCase(M9AFTPL_4_FieldNames,
		 * M9AFTPL_4_PageLayoutName, M9AFTPL_4_ObjectName);
		 * 
		 * dataload.addFieldsToLayoutPageInAdminCase(M9AFTPL_5_FieldNames,
		 * M9AFTPL_5_PageLayoutName, M9AFTPL_5_ObjectName);
		 * 
		 * dataload.addFieldsToLayoutPageInAdminCase(M9AFTPL_11_FieldNames,
		 * M9AFTPL_11_PageLayoutName, M9AFTPL_11_ObjectName);
		 * 
		 * if (dataload.dataImportWizardLightningMode(ObjectName.InstitutionAndContacts,
		 * ObjectType.Standard, M9AFTPL_1_FileName, DataImportType.AddNewRecords, "16",
		 * M9AFTPL_1_ObjectName)) {
		 * appLog.info("Parent Data is imported Successfully in " +
		 * ObjectName.InstitutionAndContacts); }
		 * 
		 * if (dataload.dataImportWizardLightningMode(ObjectName.InstitutionAndContacts,
		 * ObjectType.Standard, M9AFTPL_2_FileName, DataImportType.AddNewRecords, "12",
		 * M9AFTPL_2_ObjectName)) {
		 * appLog.info("Parent Data is imported Successfully in " +
		 * ObjectName.InstitutionAndContacts); }
		 * 
		 * if (dataload.dataImportWizardLightningMode(ObjectName.InstitutionAndContacts,
		 * ObjectType.Standard, M9AFTPL_3_FileName, DataImportType.AddNewRecords, "19",
		 * M9AFTPL_3_ObjectName)) {
		 * appLog.info("Parent Data is imported Successfully in " +
		 * ObjectName.InstitutionAndContacts); } if
		 * (dataload.dataImportWizardLightningMode(ObjectName.InstitutionAndContacts,
		 * ObjectType.Standard, M9AFTPL_4_FileName, DataImportType.AddNewRecords, "4",
		 * M9AFTPL_4_ObjectName)) {
		 * appLog.info("Parent Data is imported Successfully in " +
		 * ObjectName.InstitutionAndContacts); }
		 * 
		 * if (dataload.dataImportWizardLightningMode(ObjectName.InstitutionAndContacts,
		 * ObjectType.Standard, M9AFTPL_5_FileName, DataImportType.AddNewRecords, "40",
		 * M9AFTPL_5_ObjectName)) {
		 * appLog.info("Parent Data is imported Successfully in " +
		 * ObjectName.InstitutionAndContacts); }
		 * 
		 * if (dataload.dataImportWizardLightningMode(ObjectName.Funds,
		 * ObjectType.Custom, M9AFTPL_6_FileName, DataImportType.AddNewRecords, "52",
		 * M9AFTPL_6_ObjectName)) {
		 * appLog.info("Parent Data is imported Successfully in " + ObjectName.Funds); }
		 * 
		 * if (dataload.dataImportWizardLightningMode(ObjectName.Fundraisings,
		 * ObjectType.Custom, M9AFTPL_7_FileName, DataImportType.AddNewRecords, "42",
		 * M9AFTPL_7_ObjectName)) {
		 * appLog.info("Parent Data is imported Successfully in " +
		 * ObjectName.Fundraisings); } if
		 * (dataload.dataImportWizardLightningMode(ObjectName.Fund_Team,
		 * ObjectType.Custom, M9AFTPL_8_FileName, DataImportType.AddNewRecords, "10",
		 * M9AFTPL_8_ObjectName)) {
		 * appLog.info("Parent Data is imported Successfully in " +
		 * ObjectName.Fund_Team); } if
		 * (dataload.dataImportWizardLightningMode(ObjectName.Event_WorkAround,
		 * ObjectType.Custom, M9AFTPL_9_FileName, DataImportType.AddNewRecords, "14",
		 * M9AFTPL_9_ObjectName)) {
		 * appLog.info("Parent Data is imported Successfully in " +
		 * ObjectName.Event_WorkAround); }
		 * 
		 * if (dataload.dataImportWizardLightningMode(ObjectName.Sortable_Data_Grid,
		 * ObjectType.Custom, M9AFTPL_10_FileName, DataImportType.AddNewRecords, "13",
		 * M9AFTPL_10_ObjectName)) {
		 * appLog.info("Parent Data is imported Successfully in " +
		 * ObjectName.Sortable_Data_Grid); }
		 * 
		 * if
		 * (dataload.dataImportWizardLightningMode(ObjectName.Sortable_Data_Grid_Field,
		 * ObjectType.Custom, M9AFTPL_11_FileName, DataImportType.AddNewRecords, "81",
		 * M9AFTPL_11_ObjectName)) {
		 * appLog.info("Parent Data is imported Successfully in " +
		 * ObjectName.Sortable_Data_Grid_Field); } if
		 * (dataload.dataImportWizardLightningMode(ObjectName.Sortable_Data_Grid_Action,
		 * ObjectType.Custom, M9AFTPL_12_FileName, DataImportType.AddNewRecords, "4",
		 * M9AFTPL_12_ObjectName)) {
		 * appLog.info("Parent Data is imported Successfully in " +
		 * ObjectName.Sortable_Data_Grid_Action); }
		 */

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc001_7_AddListViews(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		String[][] listViewSheetData = {
				{ M9LV_1_Member, M9LV_1_TabName, M9LV_1_ListViewName, M9LV_1_ListAccessibility, M9LV_1_Filter,
						M9LV_1_Field, M9LV_1_Operators, M9LV_1_FilterValue },
				{ M9LV_2_Member, M9LV_2_TabName, M9LV_2_ListViewName, M9LV_2_ListAccessibility, M9LV_2_Filter,
						M9LV_2_Field, M9LV_2_Operators, M9LV_2_FilterValue },
				{ M9LV_3_Member, M9LV_3_TabName, M9LV_3_ListViewName, M9LV_3_ListAccessibility, M9LV_3_Filter,
						M9LV_3_Field, M9LV_3_Operators, M9LV_3_FilterValue },
				{ M9LV_4_Member, M9LV_4_TabName, M9LV_4_ListViewName, M9LV_4_ListAccessibility, M9LV_4_Filter,
						M9LV_4_Field, M9LV_4_Operators, M9LV_4_FilterValue } };

		for (String[] row : listViewSheetData) {

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
				if (lp.addAutomationAllListView(projectName, row, 10)) {
					log(LogStatus.INFO, "list view added on " + row[1], YesNo.No);
				} else {
					log(LogStatus.FAIL, "list view could not added on " + row[1], YesNo.Yes);
					sa.assertTrue(false, "list view could not added on " + row[1]);
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

	@Parameters("projectName")

	@Test
	public void M9Tc001_8_createCustomReports(String projectName) throws InterruptedException {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ReportsTabBusinessLayer report = new ReportsTabBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		ReportField[][] fields = { { ReportField.Fundraising_Name, ReportField.Fund_Name },
				{ ReportField.Fundraising_Name, ReportField.Fund_Name } };

		String datas[][] = {
				{ M9Report_1_ReportFolderName, M9Report_1_ReportName, M9Report_1_SelectReportType, M9Report_1_Show,

						M9Report_1_Range, M9Report_1_DateField, M9Report_1_FieldName, M9Report_1_Operator,
						M9Report_1_FieldValue },

				{ M9Report_2_ReportFolderName, M9Report_2_ReportName, M9Report_2_SelectReportType, M9Report_2_Show,

						M9Report_2_Range, M9Report_2_DateField, M9Report_2_FieldName, M9Report_2_Operator,
						M9Report_2_FieldValue } };

		int i = 0;
		for (String[] data : datas) {

			if (report.createCustomReportForFolderLightningMode(environment, mode, data[0], ReportFormatName.Null,
					data[1], data[1], data[2], fields[i], null, null, null, null, null)) {
				appLog.info("Custom Report is created succesdfully : " + data[1]);
				sa.assertTrue(true, "Custom Report is created succesdfully : " + data[1]);

				if (report.addFilterInCustomReportLightning(data[3], data[5], data[4], data[6], data[7], data[8])) {
					appLog.info("All Filters has been successfully applied to the report : " + data[1]);
					sa.assertTrue(true, "All filters added to Custom Report : " + data[1]);

				} else {
					appLog.error("Filters are not applied to Custom Report : " + data[1]);
					sa.assertTrue(false, "Not able to add filters to Custom Report : " + data[1]);
				}

			}

			else {
				appLog.error("Not able to create Custom Report : " + data[1]);
				sa.assertTrue(false, "Not able to create Custom Report : " + data[1]);
				log(LogStatus.ERROR, "Not able to create Custom Report : " + data[1], YesNo.Yes);
			}

			i++;
		}

		home.switchToLighting();

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc002_Create_Fund_First_SDG_Grid_New(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String[] fields = { SDGLabels.APIName.toString() + "," + SDGLabels.Override_Label.toString(),
				SDGLabels.APIName.toString() + "," + SDGLabels.Override_Label.toString() };
		String[] values = { M9SDGFieldValue_1_APIName, M9SDGFieldValue_1_OverrideLabel };

		// String[] values = { "Name,Fund Name", "navpeII__Fund_Type__c,Fund Type" };
		lp.searchAndClickOnApp(SDG, 30);

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);
			String sdgName = M9SDGFieldValue_1_SDGName;
			String sdgTag = M9SDGFieldValue_1_SDGTag;
			String[][] sdgLabels = { { SDGCreationLabel.SDG_Name.toString(), sdgName },
					{ SDGCreationLabel.SDG_Tag.toString(), sdgTag },
					{ SDGCreationLabel.sObjectName.toString(), M9SDGFieldValue_1_SDGSObjectName },
					{ SDGCreationLabel.Default_Sort.toString(), M9SDGFieldValue_1_SDGDefaultSort } };

			if (sdg.createCustomSDG(projectName, sdgName, sdgLabels, action.BOOLEAN, 20)) {
				log(LogStatus.PASS, "create/verify created SDG : " + sdgName, YesNo.No);
				int i = 0;
				for (String field : fields) {

					if (sdg.addFieldOnSDG(projectName, field, values[i])) {
						log(LogStatus.INFO, "Successfully added fields on " + sdgName, YesNo.Yes);

					} else {
						sa.assertTrue(false, "Not Able to add fields on SDG : " + sdgName);
						log(LogStatus.SKIP, "Not Able to add fields on SDG : " + sdgName, YesNo.Yes);
					}
					i++;
				}

			} else {
				sa.assertTrue(false, "Not Able to create/verify created SDG : " + sdgName);
				log(LogStatus.SKIP, "Not Able to create/verify created SDG : " + sdgName, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc003_ValidateEditAndErrorMsg_Fund_First_SDG_Grid(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;

		lp.searchAndClickOnApp(SDG, 30);

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);

			String[][] sdgLabels1 = { { SDGCreationLabel.Filter.toString(), M9_TC003_SDGFilter1 },
					{ SDGCreationLabel.List_View_Name.toString(), M9_TC003_SDGListViewName1 } };

			String[][] sdgLabels2 = { { SDGCreationLabel.Filter.toString(), M9_TC003_SDGFilter2 },
					{ SDGCreationLabel.List_View_Name.toString(), M9_TC003_SDGListViewName2 } };

			String[][] sdgLabels3 = { { SDGCreationLabel.Filter.toString(), M9_TC003_SDGFilter3 },
					{ SDGCreationLabel.List_View_Name.toString(), M9_TC003_SDGListViewName3 } };
			String[][][] sdgLabels = { sdgLabels1, sdgLabels2, sdgLabels3 };

			if (sdg.editCustomSDGandFoundErrorMsgAndAtLastWithoutError(projectName, sdgName, sdgLabels, action.BOOLEAN,
					20, SDGPageErrorMessage.SDGFilterAndListViewBothErrorMsg)) {
				log(LogStatus.PASS, "edit/verify created SDG : " + sdgName, YesNo.No);
				sa.assertTrue(true, "Able to edit SDG and Find error Msg for SDG : " + sdgName);

			} else {

				log(LogStatus.SKIP, "Not Able to edit/verify created SDG : " + sdgName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to edit SDG and Find error Msg for SDG " + sdgName);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc004_ValidateErrorMsg_Fund_First_SDG_Grid(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		lp.searchAndClickOnApp(SDG, 30);

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);
			String sdgName = M9_TC003_SDGName;
			String[][] sdgLabels1 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), M9_TC004_SDGParentFilterName1 } };
			String[][] sdgLabels2 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), M9_TC004_SDGParentFilterName2 } };
			String[][] sdgLabels3 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), M9_TC004_SDGParentFilterName3 } };
			String[][] sdgLabels4 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), M9_TC004_SDGParentFilterName4 } };
			String[][] sdgLabels5 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), M9_TC004_SDGParentFilterName5 } };
			String[][] sdgLabels6 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), M9_TC004_SDGParentFilterName6 } };
			String[][] sdgLabels7 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), M9_TC004_SDGParentFilterName7 } };
			String[][] sdgLabels8 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), M9_TC004_SDGParentFilterName8 } };

			String[][][] sdgLabels = { sdgLabels1, sdgLabels2, sdgLabels3, sdgLabels4, sdgLabels5, sdgLabels6,
					sdgLabels7, sdgLabels8 };

			if (sdg.editCustomSDGandFoundErrorMsg(projectName, sdgName, sdgLabels, action.BOOLEAN, 20,
					SDGPageErrorMessage.SDGParentFieldNameErrorMsg)) {
				log(LogStatus.PASS, "edit/verify created SDG : " + sdgName, YesNo.No);
				sa.assertTrue(true, "Able to edit SDG and Find error Msg for SDG : " + sdgName);

			} else {
				sa.assertTrue(false, "Not Able to edit/verify created SDG : " + sdgName);
				log(LogStatus.SKIP, "Not Able to edit/verify created SDG : " + sdgName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to edit SDG and Find error Msg for SDG " + sdgName);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc005_ValidateColumnsAndNoOfRecordsAndSDGComponent_Fund_First_SDG_Grid_InHomepage(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String[] fieldsInSDG = { M9_TC005_SDGField1, M9_TC005_SDGField2, M9_TC005_SDGField3, M9_TC005_SDGField4,
				M9_TC005_SDGField5, M9_TC005_SDGField6, M9_TC005_SDGField7, M9_TC005_SDGField8, M9_TC005_SDGField9,
				M9_TC005_SDGField10, M9_TC005_SDGField11, M9_TC005_SDGField12, M9_TC005_SDGField13, M9_TC005_SDGField14,
				M9_TC005_SDGField15, M9_TC005_SDGField16 };
		String TitleOfSDG = M9_TC003_SDGName;
		String dataProviderName = M9_TC005_SDGDataProviderName;
		int numberOfRecords = Integer.parseInt(M9_TC005_SDGNumberOfRecords);

		List<String> columnsInSDG = Arrays.asList(fieldsInSDG);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);
			if (alreadyAddedComponentToHomePage != null) {

				log(LogStatus.INFO, "------------Component Already Added to Home Page, So Not adding Component: "
						+ TitleOfSDG + "----------------", YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page, So Not adding Component: "
						+ TitleOfSDG + "---------------");
			}

			else {

				if (edit.clickOnEditPageLink()) {
					log(LogStatus.INFO, "Component Not Already Added to Home Page, So adding Component: " + TitleOfSDG,
							YesNo.No);
					if (edit.addSDGComponentToRefrencedComponentAndVerifyColumnsAndNoOfRecords(projectName,
							columnsInSDG, "Navatar SDG", TitleOfSDG, dataProviderName, numberOfRecords)) {
						log(LogStatus.INFO, "Component Added to Home Page: " + TitleOfSDG, YesNo.Yes);
						sa.assertTrue(true, "Component Added to Home Page: " + TitleOfSDG);
					}

					else {
						log(LogStatus.ERROR, "Component Not Able to Add to Home Page: " + TitleOfSDG, YesNo.Yes);
						sa.assertTrue(false, "Component Not Able to Add to Home Page: " + TitleOfSDG);
					}
				}

				else {
					log(LogStatus.ERROR,
							"Not able to click on edit page so cannot add Component to Home Page : " + TitleOfSDG,
							YesNo.Yes);
					sa.assertTrue(false,
							"Not able to click on edit page so cannot add Component to Home Page  : " + TitleOfSDG);
				}

			}

			lp.CRMlogout();
			sa.assertAll();
		}

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc006_ValidateMiscOptionsSDGComponent_Fund_First_SDG_Grid_InHomepage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String[] fieldsInSDG = { M9_TC005_SDGField1, M9_TC005_SDGField2, M9_TC005_SDGField3, M9_TC005_SDGField4,
				M9_TC005_SDGField5, M9_TC005_SDGField6, M9_TC005_SDGField7, M9_TC005_SDGField8, M9_TC005_SDGField9,
				M9_TC005_SDGField10, M9_TC005_SDGField11, M9_TC005_SDGField12, M9_TC005_SDGField13, M9_TC005_SDGField14,
				M9_TC005_SDGField15, M9_TC005_SDGField16 };
		String TitleOfSDG = M9_TC003_SDGName;
		int row = Integer.parseInt(M9_TC006_SDGRowNumberForTooltip);
		;

		List<String> columnsInSDG = Arrays.asList(fieldsInSDG);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Header: " + TitleOfSDG, action.SCROLLANDBOOLEAN, 10);
			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");

				if (edit.verifySDGExpandByDefault(TitleOfSDG)) {
					log(LogStatus.PASS, "-----------SDG: " + TitleOfSDG + " is Expanded By Default--------------",
							YesNo.No);
					sa.assertTrue(true, "-----------SDG: " + TitleOfSDG + " is Expanded By Default--------------");

				}

				else {
					log(LogStatus.FAIL, "-----------SDG: " + TitleOfSDG + " is not Expanded By Default--------------",
							YesNo.No);
					sa.assertTrue(false, "-----------SDG: " + TitleOfSDG + " is not Expanded By Default--------------");

				}
				if (edit.verifyColumnsOfSDG(TitleOfSDG, columnsInSDG)) {
					log(LogStatus.PASS, "-------Columns of SDG: " + TitleOfSDG + "are Matched--------", YesNo.No);
					sa.assertTrue(true, "-------Columns of SDG: " + TitleOfSDG + " are Matched-------");

				} else {
					log(LogStatus.FAIL, "------Columns of SDG: " + TitleOfSDG + " are not Matched------", YesNo.Yes);
					sa.assertTrue(false, "------Columns of SDG: " + TitleOfSDG + " are not Matched------");

				}

				if (edit.verifySDGTooltipForARecord(TitleOfSDG, row)) {
					log(LogStatus.PASS, "-----------Tooltips for all columns of a record no. " + row
							+ " matched of SDG: " + TitleOfSDG + "--------------", YesNo.No);
					sa.assertTrue(true, "-----------Tooltips for all columns of a record no. " + row
							+ " matched of SDG: " + TitleOfSDG + "--------------");

				}

				else {
					log(LogStatus.FAIL, "-----------Tooltips for all columns of a record no. " + row
							+ " Not matched of SDG: " + TitleOfSDG + "--------------", YesNo.No);
					sa.assertTrue(false, "-----------Tooltips for all columns of a record no. " + row
							+ " Not matched of SDG: " + TitleOfSDG + "--------------");

				}

				if (lp.clickOnTab(projectName, TabName.InstituitonsTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
					if (lp.clickOnTab(projectName, TabName.HomeTab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
						if (edit.verifyCollapseTooltipAFterGoingToInstitutionPageAndComingBack(TitleOfSDG)) {
							log(LogStatus.PASS,
									"-----Tooltip Equal to 'Collapse' after coming back to Homepage after going to Intitution Page---------",
									YesNo.No);
							sa.assertTrue(true,
									"-----Tooltip Equal to 'Collapse' after coming back to Homepage after going to Intitution Page---------");
						} else {
							log(LogStatus.FAIL,
									"-----Tooltip Not Equal to 'Collapse' after coming back to Homepage after going to Intitution Page---------",
									YesNo.No);
							sa.assertTrue(false,
									"-----Tooltip Not Equal to 'Collapse' after coming back to Homepage after going to Intitution Page---------");

						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.No);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
				}

				if (lp.switchToClassic()) {
					log(LogStatus.INFO, "----------Switched to Classic Mode-----", YesNo.No);
					if (lp.switchToLighting()) {
						log(LogStatus.INFO, "----------Switched to Lightning Mode-----", YesNo.No);
						if (edit.verifyCollapseTooltipAFterGoingToInstitutionPageAndComingBack(TitleOfSDG)) {
							log(LogStatus.PASS,
									"-----Tooltip Equal to 'Collapse' after coming back to Lightning after going to Classic Mode---------",
									YesNo.No);
							sa.assertTrue(true,
									"-----Tooltip Equal to 'Collapse' after coming back to Lightning after going to Classic Mode---------");
						} else {
							log(LogStatus.FAIL,
									"-----Tooltip Not Equal to 'Collapse' after coming back to Lightning after going to Classic Mode---------",
									YesNo.No);
							sa.assertTrue(false,
									"-----Tooltip Not Equal to 'Collapse' after coming back to Lightning after going to Classic Mode---------");

						}

					} else {
						log(LogStatus.ERROR, "Not Able to Switch to Lightning Mode", YesNo.No);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Switch to Classic Mode", YesNo.No);
				}

				if (edit.verifySDGTooltipForExpandAndCollapse(TitleOfSDG)) {
					log(LogStatus.PASS, "-----------Tooltip for Collapse/Expand after click matched of SDG: "
							+ TitleOfSDG + "--------------", YesNo.No);
					sa.assertTrue(true, "-----------Tooltip for Collapse/Expand after click matched of SDG: "
							+ TitleOfSDG + "--------------");

				}

				else {
					log(LogStatus.FAIL, "-----------Tooltips for Collapse/Expand after click Not matched of SDG: "
							+ TitleOfSDG + "--------------", YesNo.No);
					sa.assertTrue(true, "-----------Tooltips Collapse/Expand after click Not matched of SDG: "
							+ TitleOfSDG + "--------------");

				}

			}

			else {

				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

			lp.CRMlogout();
			sa.assertAll();
		}

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc007_ValidateOptionsWithAdminAndUserSDGComponent_Fund_First_SDG_Grid_InHomepage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String[] Emails = { superAdminUserName, crmUser2EmailID };

		for (String Email : Emails) {
			lp.CRMLogin(Email, adminPassword, appName);

			String TitleOfSDG = M9_TC003_SDGName;

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
				WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
						"Component Title ", action.SCROLLANDBOOLEAN, 10);

				if (alreadyAddedComponentToHomePage != null) {
					log(LogStatus.INFO,
							"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
							YesNo.Yes);
					sa.assertTrue(true,
							"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");

					CommonLib.ThreadSleep(8000);
					if (home.verifySDGExpandByDefault(TitleOfSDG)) {
						log(LogStatus.PASS, "-----------SDG: " + TitleOfSDG + " is Expanded By Default in case of "
								+ Email + "--------------", YesNo.No);
						sa.assertTrue(true, "-----------SDG: " + TitleOfSDG + " is Expanded By Default in case of "
								+ Email + "--------------");

					}

					else {
						log(LogStatus.FAIL, "-----------SDG: " + TitleOfSDG + " is not Expanded By Default in case of "
								+ Email + "--------------", YesNo.No);
						sa.assertTrue(false, "-----------SDG: " + TitleOfSDG + " is not Expanded By Default in case of "
								+ Email + "--------------");

					}

					if (home.verifyGearIconPresentAndVerifyTooltip(TitleOfSDG)) {
						log(LogStatus.PASS, "-----------Gear Icon Found and Tooltip Verified--------------", YesNo.No);
						sa.assertTrue(true, "-----------Gear Icon Found and Tooltip Verified--------------");

					}

					else {
						log(LogStatus.FAIL, "-----------Gear Icon Found and Tooltip not Verified--------------",
								YesNo.No);
						sa.assertTrue(false, "-----------Gear Icon Found and Tooltip not Verified--------------");

					}

				} else {
					log(LogStatus.ERROR,
							"-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------", YesNo.Yes);
					sa.assertTrue(false,
							"-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

				}

			}

			else {
				log(LogStatus.ERROR,
						"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------",
						YesNo.No);
				sa.assertTrue(false,
						"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

			}

			lp.CRMlogout();

		}
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc008_ValidateDefaultSortingAndCustomSortingSDGComponent_Fund_First_SDG_Grid_InHomepage(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		HomePage hp = new HomePage(driver);

		String[] fieldsInSDG = { M9_TC005_SDGField1, M9_TC005_SDGField2, M9_TC005_SDGField3, M9_TC005_SDGField4,
				M9_TC005_SDGField5, M9_TC005_SDGField6, M9_TC005_SDGField7 };
		String[] datefieldsInSDG = { M9_TC005_SDGField5 };

		List<String> columnInSDG = Arrays.asList(fieldsInSDG);
		List<String> dateColumnInSDG = Arrays.asList(datefieldsInSDG);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String TitleOfSDG = M9_TC003_SDGName;

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");

				if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {

					String pageSize = "100";
					if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

						log(LogStatus.PASS, "-----------Page Size has selected to" + pageSize + " --------------",
								YesNo.No);
						List<String> columnDataText = hp.columnData(TitleOfSDG, 2).stream().map(s -> s.getText())
								.collect(Collectors.toList()).stream().map(t -> t.trim()).collect(Collectors.toList());
						Collections.sort(columnDataText, String.CASE_INSENSITIVE_ORDER.reversed());
						System.out.println(
								"Descending with Data: " + columnDataText.size() + " Collections: " + columnDataText);

						if (CommonLib.checkSorting(driver, SortOrder.Decending, hp.columnData(TitleOfSDG, 2))) {
							log(LogStatus.PASS,
									"-----------Fund Name Column is in Descending Order By Default --------------",
									YesNo.No);
							sa.assertTrue(true,
									"-----------Fund Name Column is in Descending Order By Default --------------");

						}

						else {
							log(LogStatus.FAIL,
									"-----------Fund Name Column not in Descending Order By Default --------------",
									YesNo.No);
							sa.assertTrue(false,
									"-----------Fund Name Column not in Descending Order By Default --------------");

						}
					} else {
						log(LogStatus.FAIL, "-----------Not able to Select Page Size: " + pageSize + "--------------",
								YesNo.No);
						sa.assertTrue(false,
								"-----------Not able to Select Page Size: " + pageSize + " --------------");
					}

				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

				home.verifyColumnAscendingDescendingOrder(SDGGridName.Fund_First_SDG, columnInSDG, dateColumnInSDG);

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

		}

		else {
			log(LogStatus.ERROR,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------", YesNo.No);
			sa.assertTrue(false,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

		}
		sa.assertAll();
		lp.CRMlogout();

	}

	@Parameters({ "projectName" })

	@Test

	public void M9Tc009_ValidateNumberOfRecordsAfterEnterLitViewNameSDGComponent_Fund_First_SDG_Grid_InHomepage(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		HomePage hp = new HomePage(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String sdgName = M9_TC003_SDGName;

		int numberOfRecords = Integer.parseInt(M9_TC009_SDGNumberOfRecords);

		lp.searchAndClickOnApp(SDG, 30);

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);

			String[][] sdgLabels = { { SDGCreationLabel.List_View_Name.toString(), M9_TC009_SDGListViewName } };

			if (sdg.editCustomSDG(projectName, sdgName, sdgLabels, action.BOOLEAN, 20)) {
				log(LogStatus.PASS, "--------edit/verify SDG : " + sdgName + " -------------", YesNo.No);
				sa.assertTrue(true, "--------Able to edit SDG : " + sdgName + " --------------");

				if (lp.clickOnTab(projectName, TabName.HomeTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
					WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
							"Component Title ", action.SCROLLANDBOOLEAN, 10);

					if (alreadyAddedComponentToHomePage != null) {
						log(LogStatus.INFO,
								"------------Component Already Added to Home Page " + sdgName + "----------------",
								YesNo.Yes);
						sa.assertTrue(true,
								"------------Component Already Added to Home Page " + sdgName + "---------------");

						if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {

							String pageSize = "100";
							if (home.pageSizeSelect(sdgName, pageSize)) {

								log(LogStatus.PASS,
										"-----------Page Size has selected to" + pageSize + " --------------",
										YesNo.No);

								if (home.numberOfRecordsMatch(sdgName, numberOfRecords)) {
									log(LogStatus.INFO,
											"----------No. of Records Matched: " + numberOfRecords + "------------",
											YesNo.No);

								}

								else {
									log(LogStatus.FAIL, "---------No. of Records not Matched-------- ", YesNo.No);
									sa.assertTrue(false, "-----------No. of Records not Matched:  --------------");

								}
							} else {
								log(LogStatus.FAIL,
										"-----------Not able to Select Page Size: " + pageSize + "--------------",
										YesNo.No);
								sa.assertTrue(false,
										"-----------Not able to Select Page Size: " + pageSize + " --------------");
							}

						} else {
							log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
							sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
						}

					} else {
						log(LogStatus.ERROR,
								"-----------Component Not Added to Home Page: " + sdgName + " -------------",
								YesNo.Yes);
						sa.assertTrue(false,
								"-----------Component Not Added to Home Page: " + sdgName + " ------------");

					}

				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "----------Not Able to edit/verify created SDG : " + sdgName + " ------------");
				log(LogStatus.SKIP, "-----------Not Able to edit/verify created SDG : " + sdgName + " -------------",
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc010_ValidateNumberOfRecordsAfterEnterLitViewNameForUserSDGComponent_Fund_First_SDG_Grid_InHomepage(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		lp.CRMLogin(crmUser2EmailID, adminPassword, appName);

		String sdgName = M9_TC003_SDGName;
		int numberOfRecords = Integer.parseInt(M9_TC009_SDGNumberOfRecords);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");

				if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {

					String pageSize = "100";
					if (home.pageSizeSelect(sdgName, pageSize)) {

						log(LogStatus.PASS, "-----------Page Size has selected to" + pageSize + " --------------",
								YesNo.No);

						if (home.numberOfRecordsMatch(sdgName, numberOfRecords)) {
							log(LogStatus.INFO, "----------No. of Records Matched: " + numberOfRecords + "------------",
									YesNo.No);

						}

						else {
							log(LogStatus.FAIL, "---------No. of Records not Matched-------- ", YesNo.No);
							sa.assertTrue(false, "-----------No. of Records not Matched:  --------------");

						}
					} else {
						log(LogStatus.FAIL, "-----------Not able to Select Page Size: " + pageSize + "--------------",
								YesNo.No);
						sa.assertTrue(false,
								"-----------Not able to Select Page Size: " + pageSize + " --------------");
					}

				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc011_ValidateNumberOfRecordsAfterEnterInValidThenCorrectLitViewNameSDGComponent_Fund_First_SDG_Grid_InHomepage(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		HomePage hp = new HomePage(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String[][][] sdgLabels = { { { SDGCreationLabel.List_View_Name.toString(), M9_TC011_SDGListViewName1 } },
				{ { SDGCreationLabel.List_View_Name.toString(), M9_TC011_SDGListViewName2 } } };

		String sdgName = M9_TC003_SDGName;
		String[] expectedNumberOfRecords = M9_TC011_SDGNumberOfRecords.split("<break>");
		int i = 0;
		for (String[][] sdgLabel : sdgLabels) {
			lp.searchAndClickOnApp(SDG, 30);

			if (lp.clickOnTab(projectName, TabName.SDGTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);

				if (sdg.editCustomSDG(projectName, sdgName, sdgLabel, action.BOOLEAN, 20)) {
					log(LogStatus.PASS, "--------edit/verify SDG : " + sdgName + " -------------", YesNo.No);
					sa.assertTrue(true, "--------Able to edit SDG : " + sdgName + " --------------");

					if (lp.clickOnTab(projectName, TabName.HomeTab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
						WebElement alreadyAddedComponentToHomePage = FindElement(driver,
								"//a[text()='" + sdgName + "']", "Component Title ", action.SCROLLANDBOOLEAN, 10);

						if (alreadyAddedComponentToHomePage != null) {
							log(LogStatus.INFO,
									"------------Component Already Added to Home Page " + sdgName + "----------------",
									YesNo.Yes);
							sa.assertTrue(true,
									"------------Component Already Added to Home Page " + sdgName + "---------------");

							if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {

								String pageSize = "100";
								if (home.pageSizeSelect(sdgName, pageSize)) {

									log(LogStatus.PASS,
											"-----------Page Size has selected to" + pageSize + " --------------",
											YesNo.No);

									if (sdgLabel[0][1].equals(M9_TC011_SDGListViewName1)) {
										int numberOfRecords = Integer.parseInt(expectedNumberOfRecords[i]);
										if (home.numberOfRecordsMatch(sdgName, numberOfRecords)) {
											log(LogStatus.INFO,
													"----------No. of Records Matched: " + numberOfRecords
															+ "for List View Name: " + sdgLabel[0][1] + "------------",
													YesNo.No);

										}

										else {
											log(LogStatus.FAIL,
													"---------No. of Records not Matched " + numberOfRecords
															+ "for List View Name: " + sdgLabel[0][1] + "-------- ",
													YesNo.No);
											sa.assertTrue(false,
													"-----------No. of Records not Matched: " + numberOfRecords
															+ "for List View Name: " + sdgLabel[0][1]
															+ " --------------");

										}
									} else if (sdgLabel[0][1].equals(M9_TC011_SDGListViewName2)) {

										int numberOfRecords = Integer.parseInt(expectedNumberOfRecords[i]);
										if (home.numberOfRecordsMatch(sdgName, numberOfRecords)) {
											log(LogStatus.INFO,
													"----------No. of Records Matched: " + numberOfRecords
															+ "for List View Name: " + sdgLabel[0][1] + "------------",
													YesNo.No);

										}

										else {
											log(LogStatus.FAIL,
													"---------No. of Records not Matched " + numberOfRecords
															+ "for List View Name: " + sdgLabel[0][1] + "-------- ",
													YesNo.No);
											sa.assertTrue(false,
													"-----------No. of Records not Matched: " + numberOfRecords
															+ "for List View Name: " + sdgLabel[0][1]
															+ " --------------");

										}
									}
								} else {
									log(LogStatus.FAIL,
											"-----------Not able to Select Page Size: " + pageSize + "--------------",
											YesNo.No);
									sa.assertTrue(false,
											"-----------Not able to Select Page Size: " + pageSize + " --------------");
								}

							} else {
								log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
								sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
							}

						} else {
							log(LogStatus.ERROR,
									"-----------Component Not Added to Home Page: " + sdgName + " -------------",
									YesNo.Yes);
							sa.assertTrue(false,
									"-----------Component Not Added to Home Page: " + sdgName + " ------------");

						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
						log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
					}

				} else {
					sa.assertTrue(false,
							"----------Not Able to edit/verify created SDG : " + sdgName + " ------------");
					log(LogStatus.SKIP,
							"-----------Not Able to edit/verify created SDG : " + sdgName + " -------------",
							YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
			}
			i++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc012_DeleteListViewAndVerifySDGRecords(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		String[][] listViewSheetData = { { M9LV_3_Member, M9LV_3_TabName, M9LV_3_ListViewName } };
		String[][][] sdgLabelsPart2 = { { { SDGCreationLabel.List_View_Name.toString(), "" } },
				{ { SDGCreationLabel.List_View_Name.toString(), M9_TC009_SDGListViewName } } };
		String[] expectedNumberOfRecords = M9_TC012_SDGNumberOfRecords.split("<break>");
		for (String[] row : listViewSheetData) {

			if (row[0].trim().equalsIgnoreCase("User1")) {

				lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
			} else if (row[0].trim().equalsIgnoreCase("User2")) {
				lp.CRMlogout();
				lp.CRMLogin(crmUser2EmailID, adminPassword, appName);
			} else if (row[0].trim().equalsIgnoreCase("admin")) {
				lp.CRMlogout();
				lp.CRMLogin(superAdminUserName, adminPassword, appName);
			}

			String sdgName = M9_TC003_SDGName;

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
						"Component Title ", action.SCROLLANDBOOLEAN, 10);

				if (alreadyAddedComponentToHomePage != null) {
					log(LogStatus.INFO,
							"------------Component Already Added to Home Page " + sdgName + "----------------",
							YesNo.Yes);
					sa.assertTrue(true,
							"------------Component Already Added to Home Page " + sdgName + "---------------");

					if (lp.clickOnTab(projectName, row[1])) {
						if (home.deleteListView(projectName, row[2], 20)) {
							log(LogStatus.INFO,
									"------------List view " + row[2] + " deleted on " + row[1] + "-----------",
									YesNo.No);
							if (lp.clickOnTab(projectName, TabName.HomeTab)) {
								log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

								if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {

									String pageSize = "100";
									if (home.pageSizeSelect(sdgName, pageSize)) {

										log(LogStatus.PASS,
												"-----------Page Size has selected to" + pageSize + " --------------",
												YesNo.No);

										int numberOfRecords = Integer.parseInt(expectedNumberOfRecords[0]);
										if (home.numberOfRecordsMatch(sdgName, numberOfRecords)) {
											log(LogStatus.INFO, "----------No. of Records Matched: " + numberOfRecords
													+ "------------", YesNo.No);

										}

										else {
											log(LogStatus.FAIL, "---------No. of Records not Matched-------- ",
													YesNo.No);
											sa.assertTrue(false,
													"-----------No. of Records not Matched:  --------------");

										}
									} else {
										log(LogStatus.FAIL, "-----------Not able to Select Page Size: " + pageSize
												+ "--------------", YesNo.No);
										sa.assertTrue(false, "-----------Not able to Select Page Size: " + pageSize
												+ " --------------");
									}

								} else {
									log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------",
											YesNo.No);
									sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
								}
							} else {
								sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
								log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
							}
						} else {
							log(LogStatus.FAIL, "-------------List view " + row[2] + " could not deleted on " + row[1]
									+ "-----------", YesNo.Yes);
							sa.assertTrue(false, "-------------List view " + row[2] + " could not deleted on " + row[1]
									+ "-----------");
						}
					}

					else {
						log(LogStatus.FAIL, "could not click on " + row[1], YesNo.Yes);
						sa.assertTrue(false, "could not click on " + row[1]);
					}

					// 2nd Part

					int i = 1;
					for (String[][] sdgLabel : sdgLabelsPart2) {
						lp.searchAndClickOnApp(SDG, 30);

						if (lp.clickOnTab(projectName, TabName.SDGTab)) {
							log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);

							if (sdg.editCustomSDG(projectName, sdgName, sdgLabel, action.BOOLEAN, 20)) {
								log(LogStatus.PASS, "--------edit/verify SDG : " + sdgName + " -------------",
										YesNo.No);
								sa.assertTrue(true, "--------Able to edit SDG : " + sdgName + " --------------");

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

									if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {

										String pageSize = "100";
										if (home.pageSizeSelect(sdgName, pageSize)) {

											log(LogStatus.PASS, "-----------Page Size has selected to" + pageSize
													+ " --------------", YesNo.No);

											if (sdgLabel[0][1].equals("")) {

												int numberOfRecords = Integer.parseInt(expectedNumberOfRecords[i]);
												if (home.numberOfRecordsMatch(sdgName, numberOfRecords)) {
													log(LogStatus.INFO,
															"----------No. of Records Matched: " + numberOfRecords
																	+ "for List View Name: " + sdgLabel[0][1]
																	+ "------------",
															YesNo.No);

												}

												else {
													log(LogStatus.FAIL,
															"---------No. of Records not Matched " + numberOfRecords
																	+ "for List View Name: " + sdgLabel[0][1]
																	+ "-------- ",
															YesNo.No);
													sa.assertTrue(false,
															"-----------No. of Records not Matched: " + numberOfRecords
																	+ "for List View Name: " + sdgLabel[0][1]
																	+ " --------------");

												}

											}

											if (sdgLabel[0][1].equals(M9_TC009_SDGListViewName)) {

												int numberOfRecords = Integer.parseInt(expectedNumberOfRecords[i]);
												if (home.numberOfRecordsMatch(sdgName, numberOfRecords)) {
													log(LogStatus.INFO,
															"----------No. of Records Matched: " + numberOfRecords
																	+ "for List View Name: " + sdgLabel[0][1]
																	+ "------------",
															YesNo.No);

												}

												else {
													log(LogStatus.FAIL,
															"---------No. of Records not Matched " + numberOfRecords
																	+ "for List View Name: " + sdgLabel[0][1]
																	+ "-------- ",
															YesNo.No);
													sa.assertTrue(false,
															"-----------No. of Records not Matched: " + numberOfRecords
																	+ "for List View Name: " + sdgLabel[0][1]
																	+ " --------------");

												}

											}
										} else {
											log(LogStatus.FAIL, "-----------Not able to Select Page Size: " + pageSize
													+ "--------------", YesNo.No);
											sa.assertTrue(false, "-----------Not able to Select Page Size: " + pageSize
													+ " --------------");
										}

									} else {
										log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------",
												YesNo.No);
										sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
									}

								}

								else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

							} else {
								sa.assertTrue(false,
										"----------Not Able to edit/verify created SDG : " + sdgName + " ------------");
								log(LogStatus.SKIP, "-----------Not Able to edit/verify created SDG : " + sdgName
										+ " -------------", YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
							log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
						}
						i++;
					}

					// part 3

					String[][] sdgLabelsPart3 = { { SDGCreationLabel.List_View_Name.toString(), "" } };
					lp.searchAndClickOnApp(SDG, 30);

					if (lp.clickOnTab(projectName, TabName.SDGTab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);

						if (sdg.editCustomSDG(projectName, sdgName, sdgLabelsPart3, action.SCROLLANDBOOLEAN, 20)) {
							log(LogStatus.PASS, "--------edit/verify SDG : " + sdgName + " -------------", YesNo.No);
							sa.assertTrue(true, "--------Able to edit SDG : " + sdgName + " --------------");

						} else {
							sa.assertTrue(false,
									"----------Not Able to edit/verify created SDG : " + sdgName + " ------------");
							log(LogStatus.SKIP,
									"-----------Not Able to edit/verify created SDG : " + sdgName + " -------------",
									YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
						log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
							YesNo.Yes);
					sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			ThreadSleep(5000);
			lp.CRMlogout();
		}

		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc013_DeleteOneFundRecordAndVerifySDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;
		String fundNameToDelete = M9_TC013_FundName;

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");

				if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {

					String pageSize = "100";
					if (home.pageSizeSelect(sdgName, pageSize)) {

						log(LogStatus.PASS, "-----------Page Size has selected to" + pageSize + " --------------",
								YesNo.No);
						int noRecordsBeforeDelete = home.numberOfRecordsInComponent(sdgName);
						if (home.deleteSDGRecord(sdgName, fundNameToDelete)) {
							log(LogStatus.INFO, "------------Record Deleted Message Show for " + fundNameToDelete
									+ "----------------", YesNo.Yes);
							sa.assertTrue(true, "------------Record Deleted Message Show for " + fundNameToDelete
									+ "---------------");

							if (home.numberOfRecordsInComponent(sdgName) == noRecordsBeforeDelete - 1) {
								log(LogStatus.INFO,
										"------------Record Gets Deleted " + fundNameToDelete + "----------------",
										YesNo.Yes);
								sa.assertTrue(true,
										"------------Record Gets Deleted " + fundNameToDelete + "---------------");

							} else {
								log(LogStatus.ERROR,
										"-----------Record Not Gets Deleted " + fundNameToDelete + " ,Expected:"
												+ (noRecordsBeforeDelete - 1) + " " + ",But Actual:"
												+ home.numberOfRecordsInComponent(sdgName) + "-------------",
										YesNo.Yes);
								sa.assertTrue(false,
										"-----------Record Not Gets Deleted " + fundNameToDelete + " ,Expected:"
												+ (noRecordsBeforeDelete - 1) + " " + ",But Actual:"
												+ home.numberOfRecordsInComponent(sdgName) + "-------------");

							}

						} else {
							log(LogStatus.ERROR, "-----------Record Deleted Message Not Show for " + fundNameToDelete
									+ " -------------", YesNo.Yes);
							sa.assertTrue(false, "-----------Record Deleted Message Not Show for " + fundNameToDelete
									+ " ------------");

						}
					} else {
						log(LogStatus.FAIL, "-----------Not able to Select Page Size: " + pageSize + "--------------",
								YesNo.No);
						sa.assertTrue(false,
								"-----------Not able to Select Page Size: " + pageSize + " --------------");
					}

				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

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
	public void M9Tc014_CheckAllRowsCheckboxAndThenVerifySDGGrid(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePage hp = new HomePage(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;
		String fundNameDeleted = M9_TC013_FundName;
		int numberOfRecords = Integer.parseInt(M9_TC014_SDGNumberOfRecords);
		String[][] listViewSheetData = { { M9_TC014_ListViewMember, M9_TC014_ListViewTabName, M9_TC014_ListViewName,
				M9_TC014_ListViewAccessibility, M9_TC014_ListViewFilter, M9_TC014_ListViewField,
				M9_TC014_ListViewOperators, M9_TC014_ListViewFilterValue } };

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				BP.openAppFromAppLauchner(SDG, 30);

				if (lp.clickOnTab(projectName, TabName.SDGTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);
					if (sdg.editAllRowOnSDG(projectName, sdgName, Condition.SelectCheckbox)) {
						log(LogStatus.PASS, "--------edit/verify SDG : " + sdgName + " -------------", YesNo.No);
						sa.assertTrue(true, "--------Able to edit SDG : " + sdgName + " --------------");

						if (lp.clickOnTab(projectName, TabName.HomeTab)) {
							log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

							if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {

								String pageSize = "100";
								if (home.pageSizeSelect(sdgName, pageSize)) {

									log(LogStatus.PASS,
											"-----------Page Size has selected to" + pageSize + " --------------",
											YesNo.No);

									if (hp.fundNameElement(sdgName, fundNameDeleted) != null) {
										log(LogStatus.INFO, "Record Found " + fundNameDeleted, YesNo.No);

									} else {
										log(LogStatus.ERROR, "Record not Found: " + fundNameDeleted, YesNo.No);
										sa.assertTrue(false, "Record not Found: " + fundNameDeleted);
									}

								} else {
									log(LogStatus.FAIL,
											"-----------Not able to Select Page Size: " + pageSize + "--------------",
											YesNo.No);
									sa.assertTrue(false,
											"-----------Not able to Select Page Size: " + pageSize + " --------------");
								}

							} else {
								log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
								sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
							}

						}

						else {
							sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
							log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,
								"----------Not Able to edit/verify created SDG : " + sdgName + " ------------");
						log(LogStatus.SKIP,
								"-----------Not Able to edit/verify created SDG : " + sdgName + " -------------",
								YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		// 2nd Part(Unselect All Row Check box)
		BP.openAppFromAppLauchner(SDG, 50);

		if (sdg.editAllRowOnSDG(projectName, sdgName, Condition.UnSelectCheckbox)) {
			log(LogStatus.PASS, "--------edit/verify SDG : " + sdgName + " -------------", YesNo.No);
			sa.assertTrue(true, "--------Able to edit SDG : " + sdgName + " --------------");

		} else {
			sa.assertTrue(false, "----------Not Able to edit/verify created SDG : " + sdgName + " ------------");
			log(LogStatus.SKIP, "-----------Not Able to edit/verify created SDG : " + sdgName + " -------------",
					YesNo.Yes);
		}

		WebElement ele;
		String recycleTab = lp.getTabName(projectName, TabName.RecycleBinTab);
		if (lp.openAppFromAppLauchner(60, recycleTab)) {

			CommonLib.refresh(driver);

			for (String[] row : listViewSheetData) {

				if (lp.addListView(projectName, row, 10)) {
					log(LogStatus.INFO, "list view added on " + row[1], YesNo.No);
					ele = lp.getCheckboxOfRestoreItemOnRecycleBin(projectName, fundNameDeleted, 30);
					if (clickUsingJavaScript(driver, ele, "Check box against : " + fundNameDeleted, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on checkbox for " + fundNameDeleted, YesNo.No);
						;
						ele = lp.getRestoreButtonOnRecycleBin(projectName, 30);
						if (clickUsingJavaScript(driver, ele, "Restore Button : " + fundNameDeleted, action.BOOLEAN)) {
							ThreadSleep(10000);
							log(LogStatus.INFO, "Click on Restore Button for " + fundNameDeleted, YesNo.No);
							sa.assertTrue(true, "Contact has been restore from the Recycle bin");

							CommonLib.switchToDefaultContent(driver);
							ThreadSleep(2000);
							if (lp.clickOnTab(projectName, TabName.HomeTab)) {
								log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

								if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {

									String pageSize = "100";
									if (home.pageSizeSelect(sdgName, pageSize)) {

										log(LogStatus.PASS,
												"-----------Page Size has selected to" + pageSize + " --------------",
												YesNo.No);
										if (hp.fundNameElement(sdgName, fundNameDeleted) != null) {
											log(LogStatus.INFO, "Record Found After Restore: " + fundNameDeleted,
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "Record not Found After Restore: " + fundNameDeleted,
													YesNo.No);
											sa.assertTrue(false, "Record not Found After Restore: " + fundNameDeleted);
										}

									} else {
										log(LogStatus.FAIL, "-----------Not able to Select Page Size: " + pageSize
												+ "--------------", YesNo.No);
										sa.assertTrue(false, "-----------Not able to Select Page Size: " + pageSize
												+ " --------------");
									}

								} else {
									log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------",
											YesNo.No);
									sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
								}

							} else {
								sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
								log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
							}

						} else {

							log(LogStatus.ERROR, "Not Able to Click on Restore Button for " + fundNameDeleted,
									YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Restore Button for " + fundNameDeleted);
						}

					} else {

						log(LogStatus.ERROR, "Not Able to Click on checkbox for " + fundNameDeleted, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on checkbox for " + fundNameDeleted);
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
	public void M9Tc015_UpdateMyRecordsFieldWithRecordOwnerID(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		boolean flag = false;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;
		String globalFilterQuery = M9_TC015_GlobalFilterQuery;
		String[][] sdgLabels = { { SDGCreationLabel.My_Records.toString(), M9_TC015_MyRecords } };
		String pageSize = "100";

		String[] expectedNumberOfRecords = M9_TC015_SDGNumberOfRecords.split("<break>");
		int rowCountAfterFilter;
		int rowCountAfterFilterAdminCase = Integer.parseInt(expectedNumberOfRecords[0]);
		int rowCountAfterFilterUser1Case = Integer.parseInt(expectedNumberOfRecords[1]);
		int rowCountAfterFilterUser2Case = Integer.parseInt(expectedNumberOfRecords[2]);
		boolean globalFilterFlag = false;
		log(LogStatus.INFO, "--------Going to Add Edit My Records for the SDG : " + sdgName + " -------------",
				YesNo.No);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				lp.openAppFromAppLauchner(60, SDG);

				if (lp.clickOnTab(projectName, TabName.SDGTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);
					if (sdg.editCustomSDG(projectName, sdgName, sdgLabels, action.BOOLEAN, 20)) {
						log(LogStatus.PASS, "--------edit/verify SDG : " + sdgName + " -------------", YesNo.No);
						sa.assertTrue(true, "--------Able to edit SDG : " + sdgName + " --------------");
						flag = true;

					} else {
						sa.assertTrue(false,
								"----------Not Able to edit/verify created SDG : " + sdgName + " ------------");
						log(LogStatus.SKIP,
								"-----------Not Able to edit/verify created SDG : " + sdgName + " -------------",
								YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO, "--------Now, Going to Add Global Filter to the SDG : " + sdgName + " -------------",
				YesNo.No);
		if (flag) {
			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

				if (edit.customFilterComponent(25) == null) {

					log(LogStatus.INFO, "-------Global Filter not already been added in the " + TabName.HomeTab
							+ " ,So Going to Add-------", YesNo.No);

					if (edit.editPageAndAddFilter("Fund", globalFilterQuery, "", "", "", "",
							Condition.SelectCheckbox)) {
						log(LogStatus.INFO, "Global Filter Added to: " + TabName.HomeTab.toString(), YesNo.No);
						globalFilterFlag = true;

					} else {
						sa.assertTrue(false, "Global not Filter Added to: " + TabName.HomeTab.toString());
						log(LogStatus.FAIL, "Global not Filter Added to: " + TabName.HomeTab.toString(), YesNo.Yes);
					}

				} else {

					log(LogStatus.ERROR,
							"-------Global Filter already been added in the " + TabName.HomeTab + "-------", YesNo.Yes);
					globalFilterFlag = true;

				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		}

		else {
			sa.assertTrue(false,
					"Edit SDG Part not Completed, So Global filter not adding to :" + TabName.HomeTab.toString());
			log(LogStatus.SKIP,
					"Edit SDG Part not Completed, So Global filter not adding to :" + TabName.HomeTab.toString(),
					YesNo.Yes);
		}
		log(LogStatus.INFO, "--------Now, Going to Check Records after Global Filter Apply to the SDG : " + sdgName
				+ " -------------", YesNo.No);
		if (globalFilterFlag)

		{
			if (AppBuilder.selectFilter("Show", "My Records")) {
				log(LogStatus.INFO, "Filter has been selected: ", YesNo.No);
				CommonLib.ThreadSleep(8000);
				rowCountAfterFilter = home.numberOfRecords(sdgName, pageSize);

				if (rowCountAfterFilter != 0) {
					if (rowCountAfterFilter == rowCountAfterFilterAdminCase) {
						log(LogStatus.INFO, "-----------Row Count for Admin After filter Apply Matched: "
								+ rowCountAfterFilter + "-----------", YesNo.No);

					} else {
						log(LogStatus.ERROR, "--------Row Count for Admin After filter Apply not Matched, Expected: "
								+ rowCountAfterFilterAdminCase + " But found: " + rowCountAfterFilter + "-----------",
								YesNo.No);
						sa.assertTrue(false, "-----------Row Count for Admin After filter Apply not Matched, Expected: "
								+ rowCountAfterFilterAdminCase + " But found: " + rowCountAfterFilter + "-----------");
					}
				} else {
					log(LogStatus.ERROR, "Could not get the count of row ", YesNo.No);
					sa.assertTrue(false, "Could not get the count of row ");
				}
			} else {
				log(LogStatus.ERROR, "Could not Select the filter", YesNo.No);
				sa.assertTrue(false, "Could not Select the filter");
			}

			if (lp.CRMlogout()) {

				lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

				if (AppBuilder.selectFilter("Show", "My Records")) {
					log(LogStatus.INFO, "Filter has been selected: ", YesNo.No);
					CommonLib.ThreadSleep(8000);
					rowCountAfterFilter = home.numberOfRecords(sdgName, pageSize);

					if (rowCountAfterFilter != 0) {
						if (rowCountAfterFilter == rowCountAfterFilterUser1Case) {
							log(LogStatus.INFO, "-----------Row Count for User1 After filter Apply Matched: "
									+ rowCountAfterFilter + "-----------", YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"--------Row Count for User1 After filter Apply not Matched, Expected: "
											+ rowCountAfterFilterUser1Case + " But found: " + rowCountAfterFilter
											+ "-----------",
									YesNo.No);
							sa.assertTrue(false,
									"-----------Row Count for User1 After filter Apply not Matched, Expected: "
											+ rowCountAfterFilterUser1Case + " But found: " + rowCountAfterFilter
											+ "-----------");
						}
					} else {
						log(LogStatus.ERROR, "Could not get the count of row ", YesNo.No);
						sa.assertTrue(false, "Could not get the count of row ");
					}
				} else {
					log(LogStatus.ERROR, "Could not Select the filter", YesNo.No);
					sa.assertTrue(false, "Could not Select the filter");
				}
			}
			if (lp.CRMlogout()) {
				lp.CRMLogin(crmUser2EmailID, adminPassword, appName);

				if (AppBuilder.selectFilter("Show", "My Records")) {
					log(LogStatus.INFO, "Filter has been selected: ", YesNo.No);
					CommonLib.ThreadSleep(8000);

					if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {
						log(LogStatus.INFO, "SDG data has been expanded: " + sdgName, YesNo.No);

						CommonLib.ThreadSleep(5000);
						List<WebElement> records = FindElements(driver,
								"//a[text()='" + sdgName + "']/ancestor::article//tbody/tr", "Records");
						System.out.println("No. of Records Present: " + records.size());
						rowCountAfterFilter = records.size();
						if (rowCountAfterFilter != 0) {
							if (rowCountAfterFilter == rowCountAfterFilterUser2Case) {
								log(LogStatus.INFO, "-----------Row Count for User2 After filter Apply Matched: "
										+ rowCountAfterFilter + "-----------", YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"--------Row Count for User2 After filter Apply not Matched, Expected: "
												+ rowCountAfterFilterUser2Case + " But found: " + rowCountAfterFilter
												+ "-----------",
										YesNo.No);
								sa.assertTrue(false,
										"-----------Row Count for User2 After filter Apply not Matched, Expected: "
												+ rowCountAfterFilterUser2Case + " But found: " + rowCountAfterFilter
												+ "-----------");
							}
						} else {
							log(LogStatus.ERROR, "Could not get the count of row ", YesNo.No);
							sa.assertTrue(false, "Could not get the count of row ");
						}

					} else {
						log(LogStatus.ERROR, "Could not expand the SDG", YesNo.No);
						flag = false;
					}

				} else {
					log(LogStatus.ERROR, "Could not Select the filter", YesNo.No);
					sa.assertTrue(false, "Could not Select the filter");
				}
			}
		} else {
			sa.assertTrue(false, "Global Filter not Added, So Not able to check Filters for Admin and both Users in :"
					+ TabName.HomeTab.toString());
			log(LogStatus.SKIP, "Global Filter not Added, So Not able to check Filters for Admin and both Users in :"
					+ TabName.HomeTab.toString(), YesNo.Yes);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc016_UpdateMyRecordsFieldWithSomeRandomText(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		boolean flag = false;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;
		String[][] sdgLabels = { { SDGCreationLabel.My_Records.toString(), M9_TC016_MyRecords } };
		String pageSize = "100";
		int rowCountAfterFilter;
		int rowCountAfterFilterAdminCase = Integer.parseInt(M9_TC016_SDGNumberOfRecords);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				lp.openAppFromAppLauchner(60, SDG);

				if (lp.clickOnTab(projectName, TabName.SDGTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);
					if (sdg.editCustomSDG(projectName, sdgName, sdgLabels, action.BOOLEAN, 20)) {
						log(LogStatus.PASS, "--------edit/verify SDG : " + sdgName + " -------------", YesNo.No);
						sa.assertTrue(true, "--------Able to edit SDG : " + sdgName + " --------------");
						flag = true;

					} else {
						sa.assertTrue(false,
								"----------Not Able to edit/verify created SDG : " + sdgName + " ------------");
						log(LogStatus.SKIP,
								"-----------Not Able to edit/verify created SDG : " + sdgName + " -------------",
								YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		if (flag) {
			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				if (CommonLib.isElementPresent(edit.getcustomFilterComponent(50))) {
					log(LogStatus.INFO, "Filter has been added in the the SDG", YesNo.No);
					if (AppBuilder.selectFilter("Show", "My Records")) {
						log(LogStatus.INFO, "Filter has been selected: ", YesNo.No);
						CommonLib.ThreadSleep(8000);
						rowCountAfterFilter = home.numberOfRecords(sdgName, pageSize);

						if (rowCountAfterFilter != 0) {
							if (rowCountAfterFilter == rowCountAfterFilterAdminCase) {
								log(LogStatus.INFO, "-----------Row Count for Admin After filter Apply Matched: "
										+ rowCountAfterFilter + "-----------", YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"--------Row Count for Admin After filter Apply not Matched, Expected: "
												+ rowCountAfterFilterAdminCase + " But found: " + rowCountAfterFilter
												+ "-----------",
										YesNo.No);
								sa.assertTrue(false,
										"-----------Row Count for Admin After filter Apply not Matched, Expected: "
												+ rowCountAfterFilterAdminCase + " But found: " + rowCountAfterFilter
												+ "-----------");
							}
						} else {
							log(LogStatus.ERROR, "Could not get the count of row ", YesNo.No);
							sa.assertTrue(false, "Could not get the count of row ");
						}
					} else {
						log(LogStatus.ERROR, "Could not Select the filter", YesNo.No);
						sa.assertTrue(false, "Could not Select the filter");
					}

				} else {
					log(LogStatus.ERROR, "Filter is not added in the SDG", YesNo.Yes);
					sa.assertTrue(false, "Filter is not added in the SDG");

				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		}

		else {
			sa.assertTrue(false,
					"Edit SDG Part not Completed, So Global filter not Verifying to :" + TabName.HomeTab.toString());
			log(LogStatus.SKIP,
					"Edit SDG Part not Completed, So Global filter not Verifying to :" + TabName.HomeTab.toString(),
					YesNo.Yes);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc017_RemoveValueFromMyRecordsFieldAndListView(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		boolean flag = false;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;
		String[][] sdgLabels = { { SDGCreationLabel.My_Records.toString(), "" },
				{ SDGCreationLabel.List_View_Name.toString(), "" } };
		String pageSize = "100";
		int rowCountAfterFilter;
		int rowCountAfterFilterAdminCase = Integer.parseInt(M9_TC014_SDGNumberOfRecords);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				lp.openAppFromAppLauchner(60, SDG);

				if (lp.clickOnTab(projectName, TabName.SDGTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);
					if (sdg.editCustomSDG(projectName, sdgName, sdgLabels, action.BOOLEAN, 20)) {
						log(LogStatus.PASS, "--------edit/verify SDG : " + sdgName + " -------------", YesNo.No);
						sa.assertTrue(true, "--------Able to edit SDG : " + sdgName + " --------------");
						flag = true;

					} else {
						sa.assertTrue(false,
								"----------Not Able to edit/verify created SDG : " + sdgName + " ------------");
						log(LogStatus.SKIP,
								"-----------Not Able to edit/verify created SDG : " + sdgName + " -------------",
								YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		if (flag) {
			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				if (CommonLib.isElementPresent(edit.getcustomFilterComponent(50))) {
					log(LogStatus.INFO, "Filter has been added in the the SDG", YesNo.No);
					if (AppBuilder.selectFilter("Show", "My Records")) {
						log(LogStatus.INFO, "Filter has been selected: ", YesNo.No);
						CommonLib.ThreadSleep(8000);
						rowCountAfterFilter = home.numberOfRecords(sdgName, pageSize);

						if (rowCountAfterFilter != 0) {
							if (rowCountAfterFilter == rowCountAfterFilterAdminCase) {
								log(LogStatus.INFO, "-----------Row Count for Admin After filter Apply Matched: "
										+ rowCountAfterFilter + "-----------", YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"--------Row Count for Admin After filter Apply not Matched, Expected: "
												+ rowCountAfterFilterAdminCase + " But found: " + rowCountAfterFilter
												+ "-----------",
										YesNo.No);
								sa.assertTrue(false,
										"-----------Row Count for Admin After filter Apply not Matched, Expected: "
												+ rowCountAfterFilterAdminCase + " But found: " + rowCountAfterFilter
												+ "-----------");
							}
						} else {
							log(LogStatus.ERROR, "Could not get the count of row ", YesNo.No);
							sa.assertTrue(false, "Could not get the count of row ");
						}
					} else {
						log(LogStatus.ERROR, "Could not Select the filter", YesNo.No);
						sa.assertTrue(false, "Could not Select the filter");
					}

				} else {
					log(LogStatus.ERROR, "Filter is not added in the SDG", YesNo.Yes);
					sa.assertTrue(false, "Filter is not added in the SDG");

				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		}

		else {
			sa.assertTrue(false,
					"Edit SDG Part not Completed, So Global filter not Verifying to :" + TabName.HomeTab.toString());
			log(LogStatus.SKIP,
					"Edit SDG Part not Completed, So Global filter not Verifying to :" + TabName.HomeTab.toString(),
					YesNo.Yes);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc018_VerifySDGGridFieldsWithRedirectURLAndGrouping(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		FundsPage fundPage = new FundsPage(driver);
		ReportsTab reportPage = new ReportsTab(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;

		String[] fieldsInSDG = { M9_TC005_SDGField1, M9_TC005_SDGField2, M9_TC005_SDGField10, M9_TC005_SDGField11 };

		String[] fieldDataInSDG = M9_TC018_SDGFieldData.split("<break>");
		List<String> columnInSDG = Arrays.asList(fieldsInSDG);
		List<String> columnDataInSDG = Arrays.asList(fieldDataInSDG);
		columnDataInSDG.set(1, crmUser1FirstName + " " + crmUser1LastName);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {
					log(LogStatus.INFO, "Now Start Checking Redirect URL's for first 10 records of SDG: " + sdgName,
							YesNo.Yes);

					if (home.verifyColumnRecordsRedirecting(SDGGridName.Fund_First_SDG, columnInSDG)) {
						log(LogStatus.INFO, "Checked the Redirect URL for : " + sdgName, YesNo.Yes);
						int indexColumnDataFromList = 0;
						for (String columnName : columnInSDG) {
							if (columnName.equals("FUND NAME")) {
								String parentWindowId = home.verifyColumnRecordRedirectAndReturnNewWindowId(
										SDGGridName.Fund_First_SDG, columnName, columnDataInSDG,
										indexColumnDataFromList);
								if (parentWindowId != null) {
									log(LogStatus.INFO, "Redirected to New Tab for SDG : " + sdgName, YesNo.Yes);
									if (fundPage.getFundNameHeader(columnDataInSDG.get(indexColumnDataFromList),
											30) != null) {
										log(LogStatus.PASS, "Text Found on New Tab: "
												+ columnDataInSDG.get(indexColumnDataFromList), YesNo.No);
									} else {
										log(LogStatus.ERROR, "No Text Found on New Tab: "
												+ columnDataInSDG.get(indexColumnDataFromList), YesNo.No);
										sa.assertTrue(false, "No Text Found on New Tab: "
												+ columnDataInSDG.get(indexColumnDataFromList));
									}

									CommonLib.ThreadSleep(3000);
									driver.close();
									CommonLib.ThreadSleep(3000);
									driver.switchTo().window(parentWindowId);

								} else {
									log(LogStatus.FAIL, "Not Redirected to New Tab for SDG : " + sdgName, YesNo.No);
									sa.assertTrue(false, "Not Redirected to New Tab for SDG : " + sdgName);
								}

							}
							if (columnName.equals("OWNER NAME")) {
								String parentWindowId = home.verifyColumnRecordRedirectAndReturnNewWindowId(
										SDGGridName.Fund_First_SDG, columnName, columnDataInSDG,
										indexColumnDataFromList);
								if (parentWindowId != null) {
									log(LogStatus.INFO, "Redirected to New Tab for SDG : " + sdgName, YesNo.Yes);
									if (home.getUserNameHeader(crmUser1FirstName + " " + crmUser1LastName,
											30) != null) {
										log(LogStatus.PASS,
												"Text Found on New Tab: " + crmUser1FirstName + " " + crmUser1LastName,
												YesNo.No);
									} else {
										log(LogStatus.ERROR, "No Text Found on New Tab: " + crmUser1FirstName + " "
												+ crmUser1LastName, YesNo.No);
										sa.assertTrue(false, "No Text Found on New Tab: " + crmUser1FirstName + " "
												+ crmUser1LastName);
									}

									CommonLib.ThreadSleep(3000);
									driver.close();
									CommonLib.ThreadSleep(3000);
									driver.switchTo().window(parentWindowId);

								} else {
									log(LogStatus.FAIL, "Not Redirected to New Tab for SDG : " + sdgName, YesNo.No);
									sa.assertTrue(false, "Not Redirected to New Tab for SDG : " + sdgName);
								}
							}

							if (columnName.equals("#TARGET CLOSED")) {
								String parentWindowId = home.verifyColumnRecordRedirectAndReturnNewWindowId(
										SDGGridName.Fund_First_SDG, columnName, columnDataInSDG,
										indexColumnDataFromList);
								if (parentWindowId != null) {
									log(LogStatus.INFO, "Redirected to New Tab for SDG : " + sdgName, YesNo.Yes);
									if (CommonLib.switchToFrame(driver, 30, reportPage.reportViewerIFrame(30))) {
										if (reportPage.reportRecordsCount(25).getText()
												.equals(columnDataInSDG.get(indexColumnDataFromList))) {
											log(LogStatus.PASS, "Text Found on New Tab: "
													+ columnDataInSDG.get(indexColumnDataFromList), YesNo.No);
										} else {
											log(LogStatus.ERROR, "No Text Found on New Tab, Expected: "
													+ columnDataInSDG.get(indexColumnDataFromList) + " but Actual: "
													+ reportPage.reportRecordsCount(25).getText(), YesNo.No);
											sa.assertTrue(false, "No Text Found on New Tab, Expected: "
													+ columnDataInSDG.get(indexColumnDataFromList) + " but Actual: "
													+ reportPage.reportRecordsCount(25).getText());
										}
									}
									CommonLib.ThreadSleep(3000);
									driver.close();
									CommonLib.ThreadSleep(3000);
									driver.switchTo().window(parentWindowId);

								} else {
									log(LogStatus.FAIL, "Not Redirected to New Tab for SDG : " + sdgName, YesNo.No);
									sa.assertTrue(false, "Not Redirected to New Tab for SDG : " + sdgName);
								}
							}

							if (columnName.equals("#STAGE - INTERESTED")) {
								String parentWindowId = home.verifyColumnRecordRedirectAndReturnNewWindowId(
										SDGGridName.Fund_First_SDG, columnName, columnDataInSDG,
										indexColumnDataFromList);
								if (parentWindowId != null) {
									log(LogStatus.INFO, "Redirected to New Tab for SDG : " + sdgName, YesNo.Yes);
									if (CommonLib.switchToFrame(driver, 30, reportPage.reportViewerIFrame(30))) {
										if (reportPage.reportRecordsCount(25).getText()
												.equals(columnDataInSDG.get(indexColumnDataFromList))) {
											log(LogStatus.PASS, "Text Found on New Tab: "
													+ columnDataInSDG.get(indexColumnDataFromList), YesNo.No);
										} else {
											log(LogStatus.ERROR, "No Text Found on New Tab: "
													+ columnDataInSDG.get(indexColumnDataFromList) + " but Actual: "
													+ reportPage.reportRecordsCount(25).getText(), YesNo.No);
											sa.assertTrue(false, "No Text Found on New Tab, Expected: "
													+ columnDataInSDG.get(indexColumnDataFromList) + " but Actual: "
													+ reportPage.reportRecordsCount(25).getText());
										}
									}

									CommonLib.ThreadSleep(3000);
									driver.close();
									CommonLib.ThreadSleep(3000);
									driver.switchTo().window(parentWindowId);

								} else {
									log(LogStatus.FAIL, "Not Redirected to New Tab for SDG : " + sdgName, YesNo.No);
									sa.assertTrue(false, "Not Redirected to New Tab for SDG : " + sdgName);
								}
							}

							indexColumnDataFromList++;
						}
					} else {
						log(LogStatus.FAIL, "Not Checked the Redirect URL for : " + sdgName, YesNo.No);
						sa.assertTrue(false, "Not Checked the Redirect URL for : " + sdgName);
					}

				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

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
	public void M9Tc019_VerifyNavigationAfterClickingonLinks(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ReportsTab reportPage = new ReportsTab(driver);
		ReportsTabBusinessLayer reportBusinessLayer = new ReportsTabBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;

		String[] fieldsInSDG = { M9_TC005_SDGField11 };
		String[] fieldDataInSDG = { M9_TC018_SDGFieldData.split("<break>")[3] };
		String[] fieldsInReport = { ReportField.Fundraising_Name.toString(), ReportField.Fund_Name.toString() };
		String reportName = M9Report_1_ReportName;
		int reportRowsCount = Integer.parseInt(M9_TC019_SDGNumberOfRecords);

		List<String> columnInSDG = Arrays.asList(fieldsInSDG);
		List<String> columnDataInSDG = Arrays.asList(fieldDataInSDG);
		List<String> columnsInReport = Arrays.asList(fieldsInReport);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {
					log(LogStatus.INFO, "Now Start Checking Redirect URL's for first 10 records of SDG: " + sdgName,
							YesNo.Yes);

					if (home.verifyColumnRecordsRedirecting(SDGGridName.Fund_First_SDG, columnInSDG)) {
						log(LogStatus.INFO, "Checked the Redirect URL for : " + sdgName, YesNo.Yes);

						int indexColumnDataFromList = 0;
						for (String columnName : columnInSDG) {

							if (columnName.equals("#STAGE - INTERESTED")) {
								String parentWindowId = home.verifyColumnRecordRedirectAndReturnNewWindowId(
										SDGGridName.Fund_First_SDG, columnName, columnDataInSDG,
										indexColumnDataFromList);
								if (parentWindowId != null) {
									log(LogStatus.INFO, "Redirected to New Tab for SDG : " + sdgName, YesNo.Yes);
									if (CommonLib.switchToFrame(driver, 30, reportPage.reportViewerIFrame(30))) {
										if (reportBusinessLayer.reportVerification(reportName, reportRowsCount,
												columnsInReport)) {
											log(LogStatus.PASS, "Report Verified: " + reportName, YesNo.No);
										} else {
											log(LogStatus.ERROR, "Report Not Verified: " + reportName, YesNo.No);

										}
									}

									CommonLib.ThreadSleep(3000);
									driver.close();
									CommonLib.ThreadSleep(3000);
									driver.switchTo().window(parentWindowId);

								} else {
									log(LogStatus.FAIL, "Not Redirected to New Tab for SDG : " + sdgName, YesNo.No);
									sa.assertTrue(false, "Not Redirected to New Tab for SDG : " + sdgName);
								}
							}

						}

					} else {
						log(LogStatus.FAIL, "Not Checked the Redirect URL for : " + sdgName, YesNo.No);
						sa.assertTrue(false, "Not Checked the Redirect URL for : " + sdgName);
					}

				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

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
	public void M9Tc020_CreateOneListViewOnFundraisingsToValidateCount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer fundraising = new FundRaisingPageBusinessLayer(driver);

		String[][] listViewSheetData = { { M9_TC020_ListViewMember, M9_TC020_ListViewTabName, M9_TC020_ListViewName,
				M9_TC020_ListViewAccessibility, M9_TC020_ListViewFilter, M9_TC020_ListViewField,
				M9_TC020_ListViewOperators, M9_TC020_ListViewFilterValue } };
		int expectedNumberOfRecords = Integer.parseInt(M9_TC019_SDGNumberOfRecords);

		for (String[] row : listViewSheetData) {

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
				if (lp.addAutomationAllListView(projectName, row, 10)) {
					log(LogStatus.INFO, "list view added on " + row[1], YesNo.No);
					CommonLib.ThreadSleep(8000);
					int numberOfRecords = fundraising.recordsInListView().size();
					if (numberOfRecords > 0) {
						log(LogStatus.PASS, "More Than One Record are present", YesNo.No);
						CommonLib.ThreadSleep(5000);
						if (numberOfRecords == expectedNumberOfRecords) {
							log(LogStatus.PASS, "------Number of Records Matched, Expected: " + expectedNumberOfRecords
									+ " , Actual: " + numberOfRecords + "-------", YesNo.No);
						}

						else {
							log(LogStatus.ERROR, "------Number of Records Not Matched, Expected: "
									+ expectedNumberOfRecords + " But Actual: " + numberOfRecords + "------", YesNo.No);
							sa.assertTrue(false, "------Number of Records Not Matched, Expected: "
									+ expectedNumberOfRecords + " But Actual: " + numberOfRecords + "------");

						}

					} else {
						log(LogStatus.ERROR, "No Record present, So not able to continue", YesNo.No);
						sa.assertTrue(false, "No Record present, So not able to continue");

					}
				} else {
					log(LogStatus.FAIL, "list view could not added on " + row[1], YesNo.Yes);
					sa.assertTrue(false, "list view could not added on " + row[1]);
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

	@Parameters({ "projectName" })

	@Test
	public void M9Tc021_CheckSDGStandatdFilterFunctioanlityOnGroupingField(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;
		String groupingFilter = "Grouping Query";

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {
					log(LogStatus.INFO, "Verified SDG Grid: " + sdgName + " is Expanded By or Not ", YesNo.Yes);

					if (home.verifyFilterNotAvailable(sdgName, groupingFilter)) {
						log(LogStatus.INFO,
								"Filter: " + groupingFilter + " is not avaialble on Filter Grid of SDG: " + sdgName,
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Filter: " + groupingFilter + " is avaialble on Filter Grid of SDG: " + sdgName,
								YesNo.Yes);
						sa.assertTrue(false,
								"Filter: " + groupingFilter + " is avaialble on Filter Grid of SDG: " + sdgName);
					}

				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

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
	public void M9Tc022_VerifyMultipicklistFilterInSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;

		String[][] data = {
				{ M9_TC022_StandardFilterSearch1, M9_TC022_SDGNumberOfRecords1, M9_TC022_StandardFilterPickList1 },
				{ M9_TC022_StandardFilterSearch2, M9_TC022_SDGNumberOfRecords2, M9_TC022_StandardFilterPickList2 },
				{ M9_TC022_StandardFilterSearch3, M9_TC022_SDGNumberOfRecords3, M9_TC022_StandardFilterPickList3 },
				{ M9_TC022_StandardFilterSearch4, M9_TC022_SDGNumberOfRecords4, M9_TC022_StandardFilterPickList4 },
				{ M9_TC022_StandardFilterSearch5, M9_TC022_SDGNumberOfRecords5, M9_TC022_StandardFilterPickList5 },
				{ M9_TC022_StandardFilterSearch6, M9_TC022_SDGNumberOfRecords6, M9_TC022_StandardFilterPickList6 },
				{ M9_TC022_StandardFilterSearch7, M9_TC022_SDGNumberOfRecords7, M9_TC022_StandardFilterPickList7 } };

		String[] pickListOptions = M9_TC022_StandardFilterPickList8.split("<break>");
		String expectedByDefaultPickListOptionSelected = M9_TC022_StandardFilterPickList9;
		List<String> expectedDefaultPickListOptionSelected = Arrays.asList(expectedByDefaultPickListOptionSelected);
		List<String> expectedPickListOptionValues = Arrays.asList(pickListOptions);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {
					log(LogStatus.INFO,
							"Verified SDG Grid: " + sdgName + " is Expanded By Default or Not, if not Then Expand ",
							YesNo.Yes);
					if (click(driver, home.gtFilterButton(sdgName, 20), "Filter Button on SDG: " + sdgName,
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + sdgName, YesNo.No);

						if (home.VerifyMultipicklistFilterInSDG(sdgName, data, expectedPickListOptionValues,
								expectedDefaultPickListOptionSelected)) {
							log(LogStatus.INFO,
									"---------Verified Multipicklist Misc Options of SDG: " + sdgName + " ---------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "---------Not Verified Multipicklist Misc Options of SDG: " + sdgName
									+ " ---------", YesNo.Yes);
							sa.assertTrue(false, "---------Not Verified Multipicklist Misc Options of SDG: " + sdgName
									+ " ---------");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + sdgName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + sdgName);
					}

				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

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
	public void M9Tc023_VerifyMultipicklistFilterWithOtherfiltersInSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;
		String[][] data = {
				{ M9_TC023_StandardFilterSearch1, M9_TC023_SDGNumberOfRecords1, M9_TC023_StandardFilterPickList1 } };
		String[][] data2 = {
				{ M9_TC023_StandardFilterSearch2, M9_TC023_SDGNumberOfRecords2, M9_TC023_StandardFilterPickList2 } };
		String fundTypeFilterLabel = M9_TC023_StandardFilterLabel3;
		String fundTypeFilterValue = M9_TC023_StandardFilterPickList3;

		String sectorFilterLabel = M9_TC023_StandardFilterLabel4;
		String sectorFilterValue = M9_TC023_StandardFilterPickList4;

		String regionFilterLabel = M9_TC023_StandardFilterLabel5;
		String regionFilterValue = M9_TC023_StandardFilterPickList5;

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {
					log(LogStatus.INFO, "Verified SDG Grid: " + sdgName + " is Expanded By or Not ", YesNo.Yes);
					if (click(driver, home.gtFilterButton(sdgName, 20), "Filter Button on SDG: " + sdgName,
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + sdgName, YesNo.No);

						if (home.VerifySelectOptionInFilter(sdgName, fundTypeFilterLabel, fundTypeFilterValue)) {
							log(LogStatus.INFO, "---------Selected Value: " + fundTypeFilterValue + " from Filter: "
									+ fundTypeFilterLabel + " of SDG: " + sdgName + " ---------", YesNo.No);
							if (home.VerifyMultipicklistFilterSelectAndValues(sdgName, data)) {
								log(LogStatus.INFO, "---------Verified Multipicklist Misc Options of SDG: " + sdgName
										+ " ---------", YesNo.No);

							} else {
								log(LogStatus.ERROR, "---------Not Verified Multipicklist Misc Options of SDG: "
										+ sdgName + " ---------", YesNo.Yes);
								sa.assertTrue(false, "---------Not Verified Multipicklist Misc Options of SDG: "
										+ sdgName + " ---------");
							}
						} else {
							log(LogStatus.ERROR, "---------Not Able to Selected Value: " + fundTypeFilterValue
									+ " from Filter: " + fundTypeFilterLabel + " of SDG: " + sdgName + " ---------",
									YesNo.Yes);
							sa.assertTrue(false, "---------Not Able to Selected Value: " + fundTypeFilterValue
									+ " from Filter: " + fundTypeFilterLabel + " of SDG: " + sdgName + " ---------");
						}

						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(10000);
						if (click(driver, home.gtFilterButton(sdgName, 40), "Filter Button on SDG: " + sdgName,
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + sdgName, YesNo.No);
							if (home.VerifySelectOptionInFilter(sdgName, sectorFilterLabel, sectorFilterValue)) {
								log(LogStatus.INFO, "---------Selected Value: " + sectorFilterValue + " from Filter: "
										+ sectorFilterLabel + " of SDG: " + sdgName + " ---------", YesNo.No);
								if (home.VerifySelectOptionInFilter(sdgName, regionFilterLabel, regionFilterValue)) {
									log(LogStatus.INFO,
											"---------Selected Value: " + regionFilterValue + " from Filter: "
													+ regionFilterLabel + " of SDG: " + sdgName + " ---------",
											YesNo.No);
									if (home.VerifyMultipicklistFilterSelectAndValues(sdgName, data2)) {
										log(LogStatus.INFO, "---------Verified Multipicklist Misc Options of SDG: "
												+ sdgName + " ---------", YesNo.No);

									} else {
										log(LogStatus.ERROR, "---------Not Verified Multipicklist Misc Options of SDG: "
												+ sdgName + " ---------", YesNo.Yes);
										sa.assertTrue(false, "---------Not Verified Multipicklist Misc Options of SDG: "
												+ sdgName + " ---------");
									}
								} else {
									log(LogStatus.ERROR,
											"---------Not Able to Selected Value: " + regionFilterValue
													+ " from Filter: " + regionFilterLabel + " of SDG: " + sdgName
													+ " ---------",
											YesNo.Yes);
									sa.assertTrue(false,
											"---------Not Able to Selected Value: " + fundTypeFilterValue
													+ " from Filter: " + fundTypeFilterLabel + " of SDG: " + sdgName
													+ " ---------");
								}
							} else {
								log(LogStatus.ERROR, "---------Not Able to Selected Value: " + sectorFilterValue
										+ " from Filter: " + sectorFilterLabel + " of SDG: " + sdgName + " ---------",
										YesNo.Yes);
								sa.assertTrue(false, "---------Not Able to Selected Value: " + sectorFilterValue
										+ " from Filter: " + sectorFilterLabel + " of SDG: " + sdgName + " ---------");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + sdgName, YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + sdgName);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + sdgName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + sdgName);
					}

				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

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
	public void M9Tc024_EditSDGAndApplyMultipicklistFilter(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;
		String[][] data = {
				{ M9_TC024_StandardFilterSearch1, M9_TC024_SDGNumberOfRecords1, M9_TC024_StandardFilterPickList1 } };

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {
					log(LogStatus.INFO, "Verified SDG Grid: " + sdgName + " is Expanded By or Not ", YesNo.Yes);
					if (click(driver, home.gtFilterButton(sdgName, 20), "Filter Button on SDG: " + sdgName,
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + sdgName, YesNo.No);

						if (home.VerifyMultipicklistFilterSelectAndValues(sdgName, data)) {
							log(LogStatus.INFO,
									"---------Verified Multipicklist Misc Options of SDG: " + sdgName + " ---------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "---------Not Verified Multipicklist Misc Options of SDG: " + sdgName
									+ " ---------", YesNo.Yes);
							sa.assertTrue(false, "---------Not Verified Multipicklist Misc Options of SDG: " + sdgName
									+ " ---------");
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + sdgName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + sdgName);
					}

				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

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
	public void M9Tc025_VerifySDGFilterAtUserSide(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;
		String pageSize = "100";
		int expectedNumberOfRecords = Integer.parseInt(M9_TC025_SDGNumberOfRecords1);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {
					log(LogStatus.INFO, "Verified SDG Grid: " + sdgName + " is Expanded By or Not ", YesNo.Yes);
					int actualNumberOfRecords = home.numberOfRecords(sdgName, pageSize);
					if (actualNumberOfRecords == expectedNumberOfRecords) {
						log(LogStatus.INFO,
								"---------Number of Records Matched of SDG: " + sdgName + ",Expected: "
										+ expectedNumberOfRecords + " ,Actual: " + actualNumberOfRecords + " ---------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"---------Number of Records Not Matched of SDG: " + sdgName + ",Expected: "
										+ expectedNumberOfRecords + " ,Actual: " + actualNumberOfRecords + " ---------",
								YesNo.Yes);
						sa.assertTrue(false, "---------Number of Records Not Matched of SDG: " + sdgName + ",Expected: "
								+ expectedNumberOfRecords + " ,Actual: " + actualNumberOfRecords + " ---------");
					}

				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

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
	public void M9Tc026_VerifySortingOnMultiPicklistField(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String TitleOfSDG = M9_TC003_SDGName;
		String[][] sdgLabels = { { SDGCreationLabel.Default_Sort.toString(), M9_TC026_DefaultSort1 } };
		String[][] sdgLabels2 = { { SDGCreationLabel.Default_Sort.toString(), M9_TC026_DefaultSort2 } };
		String errorMsg = HomePageErrorMessage.customMPicklistErrorMsg;
		String parentId;
		String[] fieldsInSDG = { M9_TC005_SDGField8 };

		List<String> columnsInSDG = Arrays.asList(fieldsInSDG);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");

				parentId = home.ClickOnOpenSDGRecordAndSwitchToNewWindow(TitleOfSDG);
				if (parentId != null) {
					log(LogStatus.PASS,
							"-----------Switched to Open SDG Record Window of SDG: " + TitleOfSDG + "--------------",
							YesNo.No);

					if (sdg.editCustomSDGAfterClickOnOpenSDGRecordButton(projectName, TitleOfSDG, sdgLabels,
							action.SCROLLANDBOOLEAN, 30)) {
						log(LogStatus.PASS, "-----------Edit/Verify SDG: " + TitleOfSDG + "--------------", YesNo.No);
						driver.switchTo().window(parentId);
						CommonLib.refresh(driver);
						if (home.SDGErrorHeader(TitleOfSDG, 30).getText().equals(errorMsg)) {
							log(LogStatus.PASS,
									"-----------Error Msg Verified of SDG: " + TitleOfSDG + "Expected: " + errorMsg
											+ " , Actual: " + home.SDGErrorHeader(TitleOfSDG, 30).getText()
											+ "--------------",
									YesNo.No);
						}

						else {
							log(LogStatus.FAIL,
									"-----------Error Msg Not Verified of SDG: " + TitleOfSDG + "Expected: " + errorMsg
											+ " , Actual: " + home.SDGErrorHeader(TitleOfSDG, 30).getText()
											+ "--------------",
									YesNo.No);
							sa.assertTrue(false,
									"-----------Error Msg Not Verified of SDG: " + TitleOfSDG + "Expected: " + errorMsg
											+ " , Actual: " + home.SDGErrorHeader(TitleOfSDG, 30).getText()
											+ "--------------");

						}

					}

					else {
						log(LogStatus.FAIL, "-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------",
								YesNo.No);
						sa.assertTrue(false,
								"-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------");

					}

				}

				else {
					log(LogStatus.FAIL, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
							+ TitleOfSDG + "--------------", YesNo.No);
					sa.assertTrue(false, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
							+ TitleOfSDG + "--------------");

				}

				// 2nd Part

				if (parentId != null) {
					log(LogStatus.PASS, "-----------Now going to Switch to Open SDG Record Window of SDG: " + TitleOfSDG
							+ " for Default Value of Sorting --------------", YesNo.No);
					CommonLib.switchOnWindow(driver);

					log(LogStatus.PASS,
							"-----------Switched to Open SDG Record Window of SDG: " + TitleOfSDG + "--------------",
							YesNo.No);

					if (sdg.editCustomSDGAfterClickOnOpenSDGRecordButton(projectName, TitleOfSDG, sdgLabels2,
							action.SCROLLANDBOOLEAN, 30)) {
						log(LogStatus.PASS, "-----------Edit/Verify SDG: " + TitleOfSDG + "--------------", YesNo.No);
						driver.close();
						driver.switchTo().window(parentId);
						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(15000);
						if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
							log(LogStatus.PASS, "-----------SDG: " + TitleOfSDG
									+ " is Expanded By Default, If not Then Expand--------------", YesNo.No);
							if (home.verifyColumnAscendingDescendingOrderShouldNotWork(TitleOfSDG, columnsInSDG)) {
								log(LogStatus.PASS, "-----------Verified Sorting not working--------------", YesNo.No);

							}

							else {
								log(LogStatus.FAIL,
										"-----------Sorting Working, So Test cases going to Fail--------------",
										YesNo.No);
								sa.assertTrue(false,
										"-----------Sorting Working, So Test cases going to Fail--------------");

							}

						}

						else {
							log(LogStatus.FAIL,
									"-----------SDG: " + TitleOfSDG
											+ " is not Expanded By Default, Also Not able to Expand--------------",
									YesNo.No);
							sa.assertTrue(false, "-----------SDG: " + TitleOfSDG
									+ " is not Expanded By Default, Also Not able to Expand--------------");

						}

					}

					else {
						log(LogStatus.FAIL, "-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------",
								YesNo.No);
						sa.assertTrue(false,
								"-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------");

					}

				}

				else {
					log(LogStatus.FAIL, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
							+ TitleOfSDG + "--------------", YesNo.No);
					sa.assertTrue(false, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
							+ TitleOfSDG + "--------------");

				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

		}

		else {
			log(LogStatus.ERROR,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------", YesNo.No);
			sa.assertTrue(false,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

		}

		lp.CRMlogout();

		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc027_VerifyErrorMessageIfUseCommaInsteadOfSemiColonForFilterMultipicklistValue(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String sdgName = M9_TC003_SDGName;
		String[][] data = { M9_TC027_MPickListErrorMsgData1.split("<break>"),
				M9_TC027_MPickListErrorMsgData2.split("<break>"), M9_TC027_MPickListErrorMsgData3.split("<break>"),
				M9_TC027_MPickListErrorMsgData4.split("<break>") };

		String fieldLabelName = M9_TC027_MPickListErrorMsgData5;

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {
					log(LogStatus.INFO, "Verified SDG Grid: " + sdgName + " is Expanded By or Not ", YesNo.Yes);
					if (click(driver, home.gtFilterButton(sdgName, 20), "Filter Button on SDG: " + sdgName,
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + sdgName, YesNo.No);

						if (home.VerifyMultipicklistFilterSelectAndCommaValuesError(sdgName, data, fieldLabelName)) {
							log(LogStatus.INFO, "---------Verified Multipicklist Misc Error Messages of SDG: " + sdgName
									+ " ---------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "---------Not Verified Multipicklist Misc Error Messages of SDG: "
									+ sdgName + " ---------", YesNo.Yes);
							sa.assertTrue(false, "---------Not Verified Multipicklist Misc Error Messages of SDG: "
									+ sdgName + " ---------");
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + sdgName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + sdgName);
					}

				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

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
	public void M9Tc028_VerifyHighlightColorsInSDG(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);

		String TitleOfSDG = M9_TC003_SDGName;
		String[][][] sdgLabels3d = {
				{ { SDGCreationLabel.Highlight_Colors.toString(), M9_TC028_HighlightedColors1,
						M9_TC028_HighlightedColorsRGB1 } },
				{ { SDGCreationLabel.Highlight_Colors.toString(), M9_TC028_HighlightedColors2,
						M9_TC028_HighlightedColorsRGB2 } },
				{ { SDGCreationLabel.Highlight_Colors.toString(), M9_TC028_HighlightedColors3,
						M9_TC028_HighlightedColorsRGB3 } },
				{ { SDGCreationLabel.Highlight_Colors.toString(), M9_TC028_HighlightedColors4,
						M9_TC028_HighlightedColorsRGB4 } },
				{ { SDGCreationLabel.Highlight_Colors.toString(), M9_TC028_HighlightedColors5,
						M9_TC028_HighlightedColorsRGB5 } } };

		String parentId;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");

				if (home.ClickOnOpenSDGRecord(TitleOfSDG)) {

					for (String[][] sdgLabels : sdgLabels3d) {
						parentId = null;
						parentId = home.SwitchToWindow();
						if (parentId != null) {
							log(LogStatus.PASS, "-----------Switched to Open SDG Record Window of SDG: " + TitleOfSDG
									+ "--------------", YesNo.No);
							lp.CRMlogout();
							if (lp.CRMLogin(superAdminUserName, adminPassword, appName)) {
								if (lp.openAppFromAppLauchner(40, SDG)) {

									log(LogStatus.INFO, "Succesfully move to Tab : " + TabName.SDGTab, YesNo.No);

									if (sdg.editCustomSDG(projectName, TitleOfSDG, sdgLabels, action.SCROLLANDBOOLEAN,
											30)) {
										log(LogStatus.PASS,
												"-----------Edit/Verify SDG: " + TitleOfSDG + "--------------",
												YesNo.No);
										lp.CRMlogout();
										driver.switchTo().window(parentId);
										CommonLib.refresh(driver);
										CommonLib.ThreadSleep(8000);
										if (lp.CRMLogin(crmUser1EmailID, adminPassword, appName)) {
											log(LogStatus.INFO, "Logged in with : " + crmUser1EmailID, YesNo.No);
											CommonLib.ThreadSleep(10000);
											String computedStylePropertyScript = "return window.document.defaultView"
													+ ".getComputedStyle(arguments[0],null).getPropertyValue(arguments[1]);";

											String actualBorderRGBColor = ((JavascriptExecutor) driver)
													.executeScript(computedStylePropertyScript,
															home.sdgBorderElement(TitleOfSDG, 30), "background-color")
													.toString();
											String actualGridRGBColor = ((JavascriptExecutor) driver)
													.executeScript(computedStylePropertyScript,
															home.sdgGridElement(TitleOfSDG, 30), "background-color")
													.toString();

											String[] sdgBorderAndGridColors = sdgLabels[0][1].split(";");
											String[] sdgBorderAndGridRBGColors = sdgLabels[0][2].split(";");

											if (actualBorderRGBColor.equals(sdgBorderAndGridRBGColors[0])
													&& actualGridRGBColor.equals(sdgBorderAndGridRBGColors[1])) {
												log(LogStatus.PASS, "----------- Border & Grid Colors Verified of SDG: "
														+ TitleOfSDG + ", Border & Grid "
														+ Arrays.toString(sdgBorderAndGridColors) + "--------------",
														YesNo.No);
											}

											else {
												log(LogStatus.FAIL,
														"----------- Border & Grid Colors Not Verified of SDG: "
																+ TitleOfSDG + ", Border & Grid "
																+ Arrays.toString(sdgBorderAndGridColors)
																+ "--------------",
														YesNo.No);
												sa.assertTrue(false,
														"----------- Border & Grid Colors Not Verified of SDG: "
																+ TitleOfSDG + ", Border & Grid "
																+ Arrays.toString(sdgBorderAndGridColors)
																+ "--------------");

											}

										}

										else {
											log(LogStatus.FAIL, "-----------Not Able to Logged in with : "
													+ crmUser1EmailID + "--------------", YesNo.No);
											sa.assertTrue(false, "-----------Not Able to Logged in with : "
													+ crmUser1EmailID + "--------------");

										}
									}

									else {
										log(LogStatus.FAIL, "-----------Not Able to Edit/Verify SDG: " + TitleOfSDG
												+ "--------------", YesNo.No);
										sa.assertTrue(false, "-----------Not Able to Edit/Verify SDG: " + TitleOfSDG
												+ "--------------");

									}
								} else {
									sa.assertTrue(false, "Not Able move to Tab : " + TabName.SDGTab);
									log(LogStatus.FAIL, "Not Able move to Tab : " + TabName.SDGTab, YesNo.Yes);
								}

							} else {
								log(LogStatus.FAIL, "-----------Not Able to Logged in with : " + superAdminUserName
										+ "--------------", YesNo.No);
								sa.assertTrue(false, "-----------Not Able to Logged in with : " + superAdminUserName
										+ "--------------");
							}
						}

						else {
							log(LogStatus.FAIL, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
									+ TitleOfSDG + "--------------", YesNo.No);
							sa.assertTrue(false, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
									+ TitleOfSDG + "--------------");

						}

					}

					parentId = home.SwitchToWindow();
					driver.close();
					driver.switchTo().window(parentId);
				} else {
					log(LogStatus.FAIL,
							"-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG + "--------------",
							YesNo.No);
					sa.assertTrue(false,
							"-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG + "--------------");

				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

		}

		else {
			log(LogStatus.ERROR,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------", YesNo.No);
			sa.assertTrue(false,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

		}

		lp.CRMlogout();

		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc029_VerifyCheckRememberFilterCheckbox(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);

		String TitleOfSDG = M9_TC003_SDGName;
		String rememberFilterCheckBoxLabel = "Remember Filter";
		String fundTypeFilterLabel = M9_TC029_StandardFilterLabel1;
		String fundTypeFilterValue = M9_TC029_StandardFilterValue1;
		String sectorFilterLabel = M9_TC029_StandardFilterLabel2;
		String sectorFilterValue = M9_TC029_StandardFilterValue2;

		List<String> expectedDefaultFundTypeOptionSelected = Arrays.asList(fundTypeFilterValue);
		List<String> expectedDefaultSectorOptionSelected = Arrays.asList(sectorFilterValue);

		String parentId;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");

				if (home.ClickOnOpenSDGRecord(TitleOfSDG)) {

					parentId = null;
					parentId = home.SwitchToSDGWindow(TitleOfSDG);
					if (parentId != null) {
						log(LogStatus.PASS, "-----------Switched to Open SDG Record Window of SDG: " + TitleOfSDG
								+ "--------------", YesNo.No);

						log(LogStatus.INFO, "Succesfully move to Tab : " + TabName.SDGTab, YesNo.No);

						if (sdg.editCheckBoxOfSDGAfterClickOnOpenSDGRecord(projectName, TitleOfSDG,
								Condition.SelectCheckbox, rememberFilterCheckBoxLabel, 30)) {
							log(LogStatus.PASS, "-----------Edit/Verify SDG: " + TitleOfSDG + "--------------",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentId);
							CommonLib.refresh(driver);
							CommonLib.ThreadSleep(8000);
						}

						else {
							log(LogStatus.FAIL,
									"-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------",
									YesNo.No);
							sa.assertTrue(false,
									"-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------");

						} // 2nd Part

						driver.switchTo().window(parentId);
						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(8000);
						log(LogStatus.INFO, "---------Now Going to Select Filters in SDG: " + TitleOfSDG, YesNo.Yes);
						if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
							log(LogStatus.INFO, "Verified SDG Grid: " + TitleOfSDG
									+ " is Expanded By Default or Not, if not Then Expand ", YesNo.Yes);
							if (click(driver, home.gtFilterButton(TitleOfSDG, 20),
									"Filter Button on SDG: " + TitleOfSDG, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + TitleOfSDG, YesNo.No);

								if (home.VerifySelectOptionInFilter(TitleOfSDG, fundTypeFilterLabel,
										fundTypeFilterValue)) {
									log(LogStatus.INFO,
											"---------Selected Value: " + fundTypeFilterValue + " from Filter: "
													+ fundTypeFilterLabel + " of SDG: " + TitleOfSDG + " ---------",
											YesNo.No);
									if (home.VerifySelectOptionInFilter(TitleOfSDG, sectorFilterLabel,
											sectorFilterValue)) {
										log(LogStatus.INFO,
												"---------Selected Value: " + sectorFilterValue + " from Filter: "
														+ sectorFilterLabel + " of SDG: " + TitleOfSDG + " ---------",
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"---------Not Able to Selected Value: " + sectorFilterValue
														+ " from Filter: " + sectorFilterLabel + " of SDG: "
														+ TitleOfSDG + " ---------",
												YesNo.Yes);
										sa.assertTrue(false,
												"---------Not Able to Selected Value: " + sectorFilterValue
														+ " from Filter: " + sectorFilterLabel + " of SDG: "
														+ TitleOfSDG + " ---------");
									}
								} else {
									log(LogStatus.ERROR,
											"---------Not Able to Selected Value: " + fundTypeFilterValue
													+ " from Filter: " + fundTypeFilterLabel + " of SDG: " + TitleOfSDG
													+ " ---------",
											YesNo.Yes);
									sa.assertTrue(false,
											"---------Not Able to Selected Value: " + fundTypeFilterValue
													+ " from Filter: " + fundTypeFilterLabel + " of SDG: " + TitleOfSDG
													+ " ---------");
								}
							}

							else {
								log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + TitleOfSDG,
										YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + TitleOfSDG);
							}
						} else {
							log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
							sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
						}

						// 3rd Part
						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(10000);
						log(LogStatus.INFO, "---------Now Going to Verify Default Filters in SDG: " + TitleOfSDG
								+ " in case of Admin-----------", YesNo.Yes);
						if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
							log(LogStatus.INFO, "Verified SDG Grid: " + TitleOfSDG
									+ " is Expanded By Default or Not, if not Then Expand ", YesNo.Yes);
							if (click(driver, home.gtFilterButton(TitleOfSDG, 20),
									"Filter Button on SDG: " + TitleOfSDG, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + TitleOfSDG, YesNo.No);

								if (home.VerifySDGFilterSelectDefaultSelectedValue(TitleOfSDG, fundTypeFilterLabel,
										expectedDefaultFundTypeOptionSelected)) {
									log(LogStatus.INFO,
											"--------" + fundTypeFilterLabel + " Default Value: "
													+ expectedDefaultFundTypeOptionSelected + " Matched--------",
											YesNo.No);
									if (home.VerifySDGFilterSelectDefaultSelectedValue(TitleOfSDG, sectorFilterLabel,
											expectedDefaultSectorOptionSelected)) {
										log(LogStatus.INFO,
												"--------" + sectorFilterLabel + " Default Value: "
														+ expectedDefaultSectorOptionSelected + " Matched--------",
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"--------" + sectorFilterLabel + " Default Value: "
														+ expectedDefaultSectorOptionSelected + " Not Matched--------",
												YesNo.Yes);
										sa.assertTrue(false, "--------" + sectorFilterLabel + " Default Value: "
												+ expectedDefaultSectorOptionSelected + " Not Matched--------");
									}
								} else {
									log(LogStatus.ERROR,
											"--------" + fundTypeFilterLabel + " Default Value: "
													+ expectedDefaultFundTypeOptionSelected + " Not Matched--------",
											YesNo.Yes);
									sa.assertTrue(false, "--------" + fundTypeFilterLabel + " Default Value: "
											+ expectedDefaultFundTypeOptionSelected + " Not Matched--------");
								}
							}

							else {
								log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + TitleOfSDG,
										YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + TitleOfSDG);
							}
						} else {
							log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
							sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
						}

					}

					else {
						log(LogStatus.FAIL, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
								+ TitleOfSDG + "--------------", YesNo.No);
						sa.assertTrue(false, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
								+ TitleOfSDG + "--------------");

					}

				} else {
					log(LogStatus.FAIL,
							"-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG + "--------------",
							YesNo.No);
					sa.assertTrue(false,
							"-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG + "--------------");

				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

		}

		else {
			log(LogStatus.ERROR,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------", YesNo.No);
			sa.assertTrue(false,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

		}

		lp.CRMlogout();

		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc030_VerifyFilterAppliedFromAdminAtUser(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String TitleOfSDG = M9_TC003_SDGName;
		String fundTypeFilterLabel = M9_TC030_StandardFilterLabel1;
		String fundTypeFilterValue = M9_TC030_StandardFilterValue1;
		String sectorFilterLabel = M9_TC030_StandardFilterLabel2;
		String sectorFilterValue = M9_TC030_StandardFilterValue2;

		List<String> expectedDefaultFundTypeOptionSelected = Arrays.asList(fundTypeFilterValue);
		List<String> expectedDefaultSectorOptionSelected = Arrays.asList(sectorFilterValue);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");

				log(LogStatus.INFO, "---------Now Going to Verify Default Filters in SDG: " + TitleOfSDG
						+ " in case of User-----------", YesNo.Yes);
				if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
					log(LogStatus.INFO,
							"Verified SDG Grid: " + TitleOfSDG + " is Expanded By Default or Not, if not Then Expand ",
							YesNo.Yes);
					if (click(driver, home.gtFilterButton(TitleOfSDG, 20), "Filter Button on SDG: " + TitleOfSDG,
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + TitleOfSDG, YesNo.No);

						if (home.VerifySDGFilterSelectDefaultSelectedValue(TitleOfSDG, fundTypeFilterLabel,
								expectedDefaultFundTypeOptionSelected)) {
							log(LogStatus.INFO, "--------" + fundTypeFilterLabel + " Default Value: "
									+ expectedDefaultFundTypeOptionSelected + " Matched--------", YesNo.No);
							if (home.VerifySDGFilterSelectDefaultSelectedValue(TitleOfSDG, sectorFilterLabel,
									expectedDefaultSectorOptionSelected)) {
								log(LogStatus.INFO, "--------" + sectorFilterLabel + " Default Value: "
										+ expectedDefaultSectorOptionSelected + " Matched--------", YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"--------" + sectorFilterLabel + " Default Value: "
												+ expectedDefaultSectorOptionSelected + " Not Matched--------",
										YesNo.Yes);
								sa.assertTrue(false, "--------" + sectorFilterLabel + " Default Value: "
										+ expectedDefaultSectorOptionSelected + " Not Matched--------");
							}
						} else {
							log(LogStatus.ERROR,
									"--------" + fundTypeFilterLabel + " Default Value: "
											+ expectedDefaultFundTypeOptionSelected + " Not Matched--------",
									YesNo.Yes);
							sa.assertTrue(false, "--------" + fundTypeFilterLabel + " Default Value: "
									+ expectedDefaultFundTypeOptionSelected + " Not Matched--------");
						}
					}

					else {
						log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + TitleOfSDG, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + TitleOfSDG);
					}
				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

		}

		else {
			log(LogStatus.ERROR,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------", YesNo.No);
			sa.assertTrue(false,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

		}

		lp.CRMlogout();

		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc031_1_ValidateAddSDGComponent_Fund_First_SDG_Grid_New_InHomepage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		String TitleOfSDG = M9SDGFieldValue_1_SDGName;
		String DataProviderName = M9_TC031_1_SDGDataProviderName;
		String referencedComponentHeading = M9_TC031_1_ReferencedComponentHeading;

		if (lp.CRMLogin(superAdminUserName, adminPassword, appName)) {
			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
						"Component Title ", action.SCROLLANDBOOLEAN, 10);
				if (alreadyAddedComponentToHomePage != null) {

					log(LogStatus.INFO, "------------Component Already Added to Home Page, So Not adding Component: "
							+ TitleOfSDG + "----------------", YesNo.Yes);
					sa.assertTrue(true, "------------Component Already Added to Home Page, So Not adding Component: "
							+ TitleOfSDG + "---------------");
				}

				else {

					if (edit.addSDGComponentToRefrencedComponent(projectName, "Navatar SDG", TitleOfSDG,
							DataProviderName, referencedComponentHeading)) {
						log(LogStatus.INFO, "Added SDG: " + TitleOfSDG + " to the Home Page", YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Able to Added SDG: " + TitleOfSDG + " to the Home Page", YesNo.No);

					}

				}

			}

			else {
				log(LogStatus.ERROR,
						"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------",
						YesNo.No);
				sa.assertTrue(false,
						"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

			}
		}

		else {
			log(LogStatus.ERROR, "-----------Not Able to logged in to the App--------------", YesNo.No);
			sa.assertTrue(false, "-----------Not Able to logged in to the App--------------");

		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc031_2_CreateActionCreateFundAndCreateRecordFromIt(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		FundsPage fp = new FundsPage(driver);
		String pageSize = "100";
		String TitleOfSDG = M9SDGFieldValue_1_SDGName;

		String[] actions = { SDGLabels.Name.toString() + "<Break>" + SDGLabels.Event.toString() + "<Break>"
				+ SDGLabels.Event_Payload.toString() + "<Break>" + SDGLabels.Action_Type.toString() + "<Break>"
				+ SDGLabels.Action_Order.toString() };

		String[] values = { M9_TC031_2_SDGActionData };
		String actionButtonName = M9_TC031_2_SDGActionButton;
		boolean actionButtonCreateAndDisplayFlag = false;

		String[][] sdgLabels = { { FundPageFieldLabelText.Fund_Name.toString(), M9_TC031_2_FundName },
				{ FundPageFieldLabelText.Fund_Type.toString(), M9_TC031_2_FundType },
				{ FundPageFieldLabelText.Investment_Category.toString(), M9_TC031_2_InvestmentCategory } };
		String fundName = M9_TC031_2_FundName;

		String parentId;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");
				if (home.actionButtonAlreadyAddedOrNotOnSDG(TitleOfSDG, actions, values, 20, pageSize)) {
					log(LogStatus.INFO, "------------Action Button " + actionButtonName + " not Already Present to sdg "
							+ TitleOfSDG + "----------------", YesNo.Yes);

					if (home.ClickOnOpenSDGRecord(TitleOfSDG)) {

						parentId = null;
						parentId = home.SwitchToSDGWindow(TitleOfSDG);
						if (parentId != null) {
							log(LogStatus.PASS, "-----------Switched to Open SDG Record Window of SDG: " + TitleOfSDG
									+ "--------------", YesNo.No);

							log(LogStatus.INFO, "Succesfully move to Tab : " + TabName.SDGTab, YesNo.No);

							if (sdg.addMultipleActionsOnSDG(projectName, TitleOfSDG, actions, values)) {
								log(LogStatus.PASS, "-----------Add Actions in SDG: " + TitleOfSDG + "--------------",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentId);
								CommonLib.refresh(driver);
								CommonLib.ThreadSleep(8000);

								if (home.listButtonOnSDG(TitleOfSDG, actionButtonName, 30) != null) {
									log(LogStatus.INFO,
											"------------Action List Button: " + actionButtonName
													+ " is showing to SDG Grid " + TitleOfSDG + "----------------",
											YesNo.Yes);
									actionButtonCreateAndDisplayFlag = true;

								} else {
									log(LogStatus.FAIL,
											"------------Action List Button: " + actionButtonName
													+ " is not showing to SDG Grid " + TitleOfSDG + "----------------",
											YesNo.No);
									appLog.error("------------Action List Button: " + actionButtonName
											+ " is not showing to SDG Grid " + TitleOfSDG + "----------------");
									;
									sa.assertTrue(false, "------------Action List Button: " + actionButtonName
											+ " is not showing to SDG Grid " + TitleOfSDG + "----------------");

								}

							}

							else {
								log(LogStatus.FAIL,
										"-----------Not Able Add Actions in SDG: " + TitleOfSDG + "--------------",
										YesNo.No);
								sa.assertTrue(false,
										"-----------Not Able Add Actions in SDG: " + TitleOfSDG + "--------------");

							}

						}

						else {
							log(LogStatus.FAIL, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
									+ TitleOfSDG + "--------------", YesNo.No);
							sa.assertTrue(false, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
									+ TitleOfSDG + "--------------");

						}

					} else {
						log(LogStatus.FAIL, "-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG
								+ "--------------", YesNo.No);
						sa.assertTrue(false, "-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG
								+ "--------------");

					}
				} else {
					log(LogStatus.ERROR, "------------Action Button " + actionButtonName + " Already Present to sdg "
							+ TitleOfSDG + "----------------", YesNo.Yes);
					sa.assertTrue(false, "------------Action Button " + actionButtonName + " Already Present to sdg "
							+ TitleOfSDG + "----------------");

				}

				// 2nd Part

				if (lp.CRMlogout()) {
					log(LogStatus.PASS, "-----------Successfully Logged Out--------------", YesNo.No);
					if (lp.CRMLogin(crmUser1EmailID, adminPassword, appName)) {
						log(LogStatus.PASS,
								"-----------Successfully Logged In with Id: " + crmUser1EmailID + "--------------",
								YesNo.No);

						if (actionButtonCreateAndDisplayFlag
								|| home.listButtonOnSDG(TitleOfSDG, actionButtonName, 30) != null) {
							if (home.createFundThroughSDG(projectName, TitleOfSDG, actionButtonName, sdgLabels, 30)) {
								log(LogStatus.INFO, "Fund : " + fundName + " Created Through SDG: " + TitleOfSDG,
										YesNo.Yes);
								if (fp.fundNameHeader(fundName, 30) != null) {
									log(LogStatus.INFO, "Header Verified of Fund : " + fundName
											+ " Created Through SDG: " + TitleOfSDG, YesNo.Yes);
								} else {
									log(LogStatus.ERROR, "Header Not Verified of Fund : " + fundName
											+ " Created Through SDG: " + TitleOfSDG, YesNo.Yes);
									sa.assertTrue(false, "Header Not Verified of Fund : " + fundName
											+ " Created Through SDG: " + TitleOfSDG);

								}
							} else {
								log(LogStatus.ERROR,
										"Not Able to Create Fund : " + fundName + " Created Through SDG: " + TitleOfSDG,
										YesNo.Yes);
								sa.assertTrue(false, "Not Able to Create Fund : " + fundName + " Created Through SDG: "
										+ TitleOfSDG);

							}
						} else {
							log(LogStatus.ERROR,
									"-----------Action Button " + actionButtonName
											+ " is not showing, So not able to create Record for SDG: " + TitleOfSDG
											+ " -------------",
									YesNo.Yes);
							sa.assertTrue(false,
									"-----------Action Button " + actionButtonName
											+ " is not showing, So not able to create Record for SDG: " + TitleOfSDG
											+ " -------------");

						}
					} else {
						log(LogStatus.ERROR,
								"-----------Not Able to Logged in with Id: " + crmUser1EmailID + " -------------",
								YesNo.Yes);
						sa.assertTrue(false,
								"-----------Not Able to Logged in with Id: " + crmUser1EmailID + " -------------");

					}
				} else {
					log(LogStatus.ERROR, "-----------Not Able to LogOut -------------", YesNo.Yes);
					sa.assertTrue(false, "-----------Not Able to LogOut -------------");

				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

		}

		else {
			log(LogStatus.ERROR,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------", YesNo.No);
			sa.assertTrue(false,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

		}

		lp.CRMlogout();

		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc032_CreateActionEditAndEditUpdateRecordFromIt(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FundsPage fp = new FundsPage(driver);
		String TitleOfSDG = M9SDGFieldValue_1_SDGName;

		String[] actions = { SDGLabels.Name.toString() + "<Break>" + SDGLabels.Event.toString() + "<Break>"
				+ SDGLabels.Event_Payload.toString() + "<Break>" + SDGLabels.Action_Type.toString() + "<Break>"
				+ SDGLabels.Action_Order.toString() };
		String[] values = { M9_TC032_SDGActionData };
		String actionButtonName = M9_TC032_SDGActionButton;
		boolean actionButtonCreateAndDisplayFlag = false;

		String[][] sdgLabels = { { FundPageFieldLabelText.Fund_Name.toString(), M9_TC032_FundName } };
		String fundName = M9_TC031_2_FundName;
		String beforeUpdateFundName = fundName;

		String pageSize = "100";

		String parentId;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");
				if (home.actionButtonAlreadyAddedOrNotOnSDG(TitleOfSDG, actions, values, 20, pageSize)) {
					log(LogStatus.INFO, "------------Action Button " + actionButtonName + " not Already Present to sdg "
							+ TitleOfSDG + "----------------", YesNo.Yes);

					if (home.ClickOnOpenSDGRecord(TitleOfSDG)) {

						parentId = null;
						parentId = home.SwitchToSDGWindow(TitleOfSDG);
						if (parentId != null) {
							log(LogStatus.PASS, "-----------Switched to Open SDG Record Window of SDG: " + TitleOfSDG
									+ "--------------", YesNo.No);

							log(LogStatus.INFO, "Succesfully move to Tab : " + TabName.SDGTab, YesNo.No);

							if (sdg.addMultipleActionsOnSDG(projectName, TitleOfSDG, actions, values)) {
								log(LogStatus.PASS, "-----------Add Actions in SDG: " + TitleOfSDG + "--------------",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentId);
								CommonLib.refresh(driver);
								CommonLib.ThreadSleep(20000);
								int rowCountAfterFilter = home.numberOfRecords(TitleOfSDG, pageSize);
								if (home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName)
										.size() == rowCountAfterFilter) {
									log(LogStatus.INFO,
											"------------Action Row Buttons: " + actionButtonName
													+ " are showing to SDG Grid " + TitleOfSDG + "----------------",
											YesNo.Yes);
									actionButtonCreateAndDisplayFlag = true;

								} else {
									log(LogStatus.FAIL,
											"------------Action Row Buttons: " + actionButtonName
													+ " are not showing to SDG Grid " + TitleOfSDG + "Expected: "
													+ rowCountAfterFilter + " ,but Actual: "
													+ home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName).size()
													+ "----------------",
											YesNo.No);
									appLog.error("------------Action Row Buttons: " + actionButtonName
											+ " are not showing to SDG Grid " + TitleOfSDG + "Expected: "
											+ rowCountAfterFilter + " ,but Actual: "
											+ home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName).size()
											+ "----------------");
									;
									sa.assertTrue(false,
											"------------Action Row Buttons: " + actionButtonName
													+ " are not showing to SDG Grid " + TitleOfSDG + "Expected: "
													+ rowCountAfterFilter + " ,but Actual: "
													+ home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName).size()
													+ "----------------");

								}

							}

							else {
								log(LogStatus.FAIL,
										"-----------Not Able Add Actions in SDG: " + TitleOfSDG + "--------------",
										YesNo.No);
								sa.assertTrue(false,
										"-----------Not Able Add Actions in SDG: " + TitleOfSDG + "--------------");

							}

						}

						else {
							log(LogStatus.FAIL, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
									+ TitleOfSDG + "--------------", YesNo.No);
							sa.assertTrue(false, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
									+ TitleOfSDG + "--------------");

						}

					} else {
						log(LogStatus.FAIL, "-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG
								+ "--------------", YesNo.No);
						sa.assertTrue(false, "-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG
								+ "--------------");

					}
				} else {
					log(LogStatus.ERROR, "------------Action Button " + actionButtonName + " Already Present to sdg "
							+ TitleOfSDG + "----------------", YesNo.Yes);
					sa.assertTrue(false, "------------Action Button " + actionButtonName + " Already Present to sdg "
							+ TitleOfSDG + "----------------");

				}

				// 2nd Part

				if (lp.CRMlogout()) {
					log(LogStatus.PASS, "-----------Successfully Logged Out--------------", YesNo.No);
					if (lp.CRMLogin(crmUser1EmailID, adminPassword, appName)) {
						log(LogStatus.PASS,
								"-----------Successfully Logged In with Id: " + crmUser1EmailID + "--------------",
								YesNo.No);
						int rowCountAfterFilter = home.numberOfRecords(TitleOfSDG, pageSize);
						if (actionButtonCreateAndDisplayFlag || home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName)
								.size() == rowCountAfterFilter) {
							if (home.fundNameCorrespondingToSDG(TitleOfSDG, sdgLabels[0][1], 25) != null) {
								log(LogStatus.ERROR, "Updated Name Already Found : " + sdgLabels[0][1] + " on SDG: "
										+ TitleOfSDG + " So not able to Edit Fund", YesNo.Yes);
								sa.assertTrue(false, "Updated Name Already Found : " + sdgLabels[0][1] + " on SDG: "
										+ TitleOfSDG + " So not able to Edit Fund");

							} else {
								log(LogStatus.ERROR, "Updated Name not Found : " + sdgLabels[0][1] + " on SDG: "
										+ TitleOfSDG + "So Continuing Edit of Fund", YesNo.Yes);
								if (home.editFundThroughSDG(projectName, TitleOfSDG, actionButtonName, sdgLabels, 30,
										beforeUpdateFundName, pageSize)) {
									log(LogStatus.INFO, "Fund : " + fundName + " Edited Through SDG: " + TitleOfSDG,
											YesNo.Yes);
									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(20000);
									if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

										log(LogStatus.PASS,
												"-----------Page Size has selected to" + pageSize + " --------------",
												YesNo.No);
										if (home.fundNameCorrespondingToSDG(TitleOfSDG, sdgLabels[0][1], 25) != null) {
											log(LogStatus.INFO, "Updated Name Found : " + sdgLabels[0][1] + " on SDG: "
													+ TitleOfSDG, YesNo.Yes);
										} else {
											log(LogStatus.ERROR, "Updated Name not Found : " + sdgLabels[0][1]
													+ " on SDG: " + TitleOfSDG, YesNo.Yes);
											sa.assertTrue(false, "Updated Name not Found : " + sdgLabels[0][1]
													+ " on SDG: " + TitleOfSDG);

										}
									} else {
										log(LogStatus.FAIL, "-----------Not able to Select Page Size: " + pageSize
												+ "--------------", YesNo.No);
										sa.assertTrue(false, "-----------Not able to Select Page Size: " + pageSize
												+ " --------------");
									}
								} else {
									log(LogStatus.ERROR, "Not Able to Create Fund : " + fundName
											+ " Created Through SDG: " + TitleOfSDG, YesNo.Yes);
									sa.assertTrue(false, "Not Able to Create Fund : " + fundName
											+ " Created Through SDG: " + TitleOfSDG);

								}

							}
						} else {
							log(LogStatus.FAIL, "------------Action Row Buttons: " + actionButtonName
									+ " are not showing to SDG Grid " + TitleOfSDG + "Expected: " + rowCountAfterFilter
									+ " ,but Actual: " + home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName).size()
									+ "----------------", YesNo.No);
							appLog.error("------------Action Row Buttons: " + actionButtonName
									+ " are not showing to SDG Grid " + TitleOfSDG + "Expected: " + rowCountAfterFilter
									+ " ,but Actual: " + home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName).size()
									+ "----------------");
							;
							sa.assertTrue(false, "------------Action Row Buttons: " + actionButtonName
									+ " are not showing to SDG Grid " + TitleOfSDG + "Expected: " + rowCountAfterFilter
									+ " ,but Actual: " + home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName).size()
									+ "----------------");

						}
					} else {
						log(LogStatus.ERROR,
								"-----------Not Able to Logged in with Id: " + crmUser1EmailID + " -------------",
								YesNo.Yes);
						sa.assertTrue(false,
								"-----------Not Able to Logged in with Id: " + crmUser1EmailID + " -------------");

					}
				} else {
					log(LogStatus.ERROR, "-----------Not Able to LogOut -------------", YesNo.Yes);
					sa.assertTrue(false, "-----------Not Able to LogOut -------------");

				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

		}

		else {
			log(LogStatus.ERROR,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------", YesNo.No);
			sa.assertTrue(false,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc033_CreateActionDeleteAndDeleteRecordFromIt(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FundsPage fp = new FundsPage(driver);
		String TitleOfSDG = M9SDGFieldValue_1_SDGName;

		String[] actions = { SDGLabels.Name.toString() + "<Break>" + SDGLabels.Event.toString() + "<Break>"
				+ SDGLabels.Event_Payload.toString() + "<Break>" + SDGLabels.Action_Type.toString() + "<Break>"
				+ SDGLabels.Action_Order.toString() };
		String[] values = { M9_TC033_SDGActionData };
		String actionButtonName = M9_TC033_SDGActionButton;

		boolean actionButtonCreateAndDisplayFlag = false;

		String[][] sdgLabels = { { FundPageFieldLabelText.Fund_Name.toString(), M9_TC032_FundName } };
		String fundName = M9_TC032_FundName;
		// String fundName = "Investment Fund Updated";
		String afterUpdateFundName = fundName;

		String pageSize = "100";

		String parentId;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");
				if (home.actionButtonAlreadyAddedOrNotOnSDG(TitleOfSDG, actions, values, 20, pageSize)) {
					log(LogStatus.INFO, "------------Action Button " + actionButtonName + " not Already Present to sdg "
							+ TitleOfSDG + "----------------", YesNo.Yes);

					if (home.ClickOnOpenSDGRecord(TitleOfSDG)) {

						parentId = null;
						parentId = home.SwitchToSDGWindow(TitleOfSDG);
						if (parentId != null) {
							log(LogStatus.PASS, "-----------Switched to Open SDG Record Window of SDG: " + TitleOfSDG
									+ "--------------", YesNo.No);

							log(LogStatus.INFO, "Succesfully move to Tab : " + TabName.SDGTab, YesNo.No);

							if (sdg.addMultipleActionsOnSDG(projectName, TitleOfSDG, actions, values)) {
								log(LogStatus.PASS, "-----------Add Actions in SDG: " + TitleOfSDG + "--------------",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentId);
								CommonLib.refresh(driver);
								CommonLib.ThreadSleep(20000);
								int rowCountAfterFilter = home.numberOfRecords(TitleOfSDG, pageSize);
								if (home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName)
										.size() == rowCountAfterFilter) {
									log(LogStatus.INFO,
											"------------Action Row Buttons: " + actionButtonName
													+ " are showing to SDG Grid " + TitleOfSDG + "----------------",
											YesNo.Yes);
									actionButtonCreateAndDisplayFlag = true;

								} else {
									log(LogStatus.FAIL,
											"------------Action Row Buttons: " + actionButtonName
													+ " are not showing to SDG Grid " + TitleOfSDG + "Expected: "
													+ rowCountAfterFilter + " ,but Actual: "
													+ home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName).size()
													+ "----------------",
											YesNo.No);
									appLog.error("------------Action Row Buttons: " + actionButtonName
											+ " are not showing to SDG Grid " + TitleOfSDG + "Expected: "
											+ rowCountAfterFilter + " ,but Actual: "
											+ home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName).size()
											+ "----------------");
									;
									sa.assertTrue(false,
											"------------Action Row Buttons: " + actionButtonName
													+ " are not showing to SDG Grid " + TitleOfSDG + "Expected: "
													+ rowCountAfterFilter + " ,but Actual: "
													+ home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName).size()
													+ "----------------");

								}

							}

							else {
								log(LogStatus.FAIL,
										"-----------Not Able Add Actions in SDG: " + TitleOfSDG + "--------------",
										YesNo.No);
								sa.assertTrue(false,
										"-----------Not Able Add Actions in SDG: " + TitleOfSDG + "--------------");

							}

						}

						else {
							log(LogStatus.FAIL, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
									+ TitleOfSDG + "--------------", YesNo.No);
							sa.assertTrue(false, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
									+ TitleOfSDG + "--------------");

						}

					} else {
						log(LogStatus.FAIL, "-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG
								+ "--------------", YesNo.No);
						sa.assertTrue(false, "-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG
								+ "--------------");

					}
				} else {
					log(LogStatus.ERROR, "------------Action Button " + actionButtonName + " Already Present to sdg "
							+ TitleOfSDG + "----------------", YesNo.Yes);
					sa.assertTrue(false, "------------Action Button " + actionButtonName + " Already Present to sdg "
							+ TitleOfSDG + "----------------");

				}

				// 2nd Part

				if (lp.CRMlogout()) {
					log(LogStatus.PASS, "-----------Successfully Logged Out--------------", YesNo.No);
					if (lp.CRMLogin(crmUser1EmailID, adminPassword, appName)) {
						log(LogStatus.PASS,
								"-----------Successfully Logged In with Id: " + crmUser1EmailID + "--------------",
								YesNo.No);
						int rowCountAfterFilter = home.numberOfRecords(TitleOfSDG, pageSize);
						if (actionButtonCreateAndDisplayFlag || home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName)
								.size() == rowCountAfterFilter) {
							if (home.fundNameCorrespondingToSDG(TitleOfSDG, sdgLabels[0][1], 25) != null) {
								log(LogStatus.INFO, "Updated Name Already Found : " + sdgLabels[0][1] + " on SDG: "
										+ TitleOfSDG + " So try to Delete That Record", YesNo.Yes);
								int numberOfRecordsBeforeDelete = home
										.fundNamesCorrespondingToSDG(TitleOfSDG, sdgLabels[0][1]).size();
								if (home.deleteFundThroughSDG(projectName, TitleOfSDG, actionButtonName, 30,
										afterUpdateFundName, pageSize)) {
									log(LogStatus.INFO, "Fund : " + fundName + " Deleted Through SDG: " + TitleOfSDG,
											YesNo.Yes);
									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(20000);
									if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

										log(LogStatus.PASS,
												"-----------Page Size has selected to" + pageSize + " --------------",
												YesNo.No);
										if (home.fundNamesCorrespondingToSDG(TitleOfSDG, sdgLabels[0][1])
												.size() == numberOfRecordsBeforeDelete - 1) {
											log(LogStatus.INFO, "Updated Name Fund: " + sdgLabels[0][1]
													+ " deleted on SDG: " + TitleOfSDG, YesNo.Yes);
										} else {
											log(LogStatus.ERROR, "Updated Name Fund: " + sdgLabels[0][1]
													+ " not deleted on SDG: " + TitleOfSDG, YesNo.Yes);
											sa.assertTrue(false, "Updated Name Fund: " + sdgLabels[0][1]
													+ " not deleted on SDG: " + TitleOfSDG);

										}
									} else {
										log(LogStatus.FAIL, "-----------Not able to Select Page Size: " + pageSize
												+ "--------------", YesNo.No);
										sa.assertTrue(false, "-----------Not able to Select Page Size: " + pageSize
												+ " --------------");
									}
								} else {
									log(LogStatus.ERROR, "Not Able to Delete Fund : " + fundName
											+ " Deleted Through SDG: " + TitleOfSDG, YesNo.Yes);
									sa.assertTrue(false, "Not Able to Delete Fund : " + fundName
											+ " Deleted Through SDG: " + TitleOfSDG);

								}

							} else {
								log(LogStatus.ERROR, "Updated Name not Found : " + sdgLabels[0][1] + " on SDG: "
										+ TitleOfSDG + " So not able to Delete That Record", YesNo.Yes);
								sa.assertTrue(false, "Updated Name not Found : " + sdgLabels[0][1] + " on SDG: "
										+ TitleOfSDG + " So not able to Delete That Record");

							}
						} else {
							log(LogStatus.FAIL, "------------Action Row Buttons: " + actionButtonName
									+ " are not showing to SDG Grid " + TitleOfSDG + "Expected: " + rowCountAfterFilter
									+ " ,but Actual: " + home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName).size()
									+ "----------------", YesNo.No);
							appLog.error("------------Action Row Buttons: " + actionButtonName
									+ " are not showing to SDG Grid " + TitleOfSDG + "Expected: " + rowCountAfterFilter
									+ " ,but Actual: " + home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName).size()
									+ "----------------");
							;
							sa.assertTrue(false, "------------Action Row Buttons: " + actionButtonName
									+ " are not showing to SDG Grid " + TitleOfSDG + "Expected: " + rowCountAfterFilter
									+ " ,but Actual: " + home.rowButtonsInSDGGrid(TitleOfSDG, actionButtonName).size()
									+ "----------------");

						}
					} else {
						log(LogStatus.ERROR,
								"-----------Not Able to Logged in with Id: " + crmUser1EmailID + " -------------",
								YesNo.Yes);
						sa.assertTrue(false,
								"-----------Not Able to Logged in with Id: " + crmUser1EmailID + " -------------");

					}
				} else {
					log(LogStatus.ERROR, "-----------Not Able to LogOut -------------", YesNo.Yes);
					sa.assertTrue(false, "-----------Not Able to LogOut -------------");

				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

		}

		else {
			log(LogStatus.ERROR,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------", YesNo.No);
			sa.assertTrue(false,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc034_AddSDGGridOnContactRecordPageAndVerifyNewReferralAction(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);

		String[][] sdgLabels = { { ContactPageFieldLabelText.Legal_Name.toString(), M9_TC034_LegalName },
				{ ContactPageFieldLabelText.Introduction_Date.toString(), M9_TC034_IntroductionDate } };
		String TitleOfSDG = M9_TC034_SDGName;
		String referralsDataProviderName = M9_TC034_SDGDataProviderName;
		String actionButtonName = M9_TC034_SDGActionButton;

		if (lp.CRMLogin(superAdminUserName, adminPassword, appName)) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
				String viewList = "All Contacts", xpath = "";
				if (click(driver, edit.getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
					WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
							action.SCROLLANDBOOLEAN, 5);
					ThreadSleep(3000);
					if (selectListView != null) {
						log(LogStatus.INFO, "All List View already present", YesNo.No);
						if (click(driver, selectListView, "Select List Icon", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on " + viewList, YesNo.No);

							if (click(driver, edit.firstElementOfTable(20), "First Element of Table",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on First Element of Table", YesNo.No);

								WebElement alreadyAddedComponentToPage = FindElement(driver,
										"//a[text()='" + TitleOfSDG + "']", "Component Title ", action.SCROLLANDBOOLEAN,
										10);

								if (alreadyAddedComponentToPage != null) {
									log(LogStatus.ERROR, "------------Component Already Added to Contact Page: "
											+ TitleOfSDG + ", So Not Going To Add New One----------------", YesNo.Yes);
									sa.assertTrue(false, "------------Component Already Added to Contact Page: "
											+ TitleOfSDG + ", So Not Going To Add New One----------------");
								}

								else {
									log(LogStatus.INFO, "-----------Component Not Added to Contact Page: " + TitleOfSDG
											+ ", So Going To Add New One -------------", YesNo.Yes);

									if (edit.editPageAndAddSDG(appName, TitleOfSDG, referralsDataProviderName)) {
										log(LogStatus.INFO, "Added SDG: " + TitleOfSDG + " to the Contact Page",
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"Not Able to Added SDG: " + TitleOfSDG + " to the Contact Page",
												YesNo.No);

									}
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click/Find First Element of Table ", YesNo.No);

							}

						} else {
							log(LogStatus.ERROR, "Not Able to Click on " + viewList, YesNo.No);

						}

					} else {
						log(LogStatus.ERROR, "All List View not already present", YesNo.No);

					}
				} else {
					log(LogStatus.ERROR, "list dropdown is not clickable, so cannot check presence of All List View",
							YesNo.Yes);

				}
			}

			else {
				log(LogStatus.ERROR,
						"-----------Not Able to click on tab:" + TabName.Object2Tab.toString() + " --------------",
						YesNo.No);
				sa.assertTrue(false,
						"-----------Not Able to click on tab:" + TabName.Object2Tab.toString() + " --------------");

			}

			lp.CRMlogout();
			if (lp.CRMLogin(crmUser1EmailID, adminPassword, appName)) {
				if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
					String viewList = "All Contacts", xpath = "";
					if (click(driver, edit.getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(3000);
						xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
						WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
								action.SCROLLANDBOOLEAN, 5);
						ThreadSleep(3000);
						if (selectListView != null) {
							log(LogStatus.INFO, "All List View already present", YesNo.No);
							if (click(driver, selectListView, "Select List Icon", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on " + viewList, YesNo.No);

								if (click(driver, edit.firstElementOfTable(20), "First Element of Table",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on First Element of Table", YesNo.No);

									WebElement alreadyAddedComponentToPage = FindElement(driver,
											"//a[text()='" + TitleOfSDG + "']", "Component Title ",
											action.SCROLLANDBOOLEAN, 10);

									if (alreadyAddedComponentToPage != null) {
										log(LogStatus.INFO, "------------Component Already Added to Contact Page "
												+ TitleOfSDG + "----------------", YesNo.Yes);
										sa.assertTrue(true, "------------Component Already Added to Contact Page "
												+ TitleOfSDG + "---------------");
										if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
											log(LogStatus.INFO,
													"Verified SDG Grid: " + TitleOfSDG
															+ " is Expanded By default or Not, if not then Expand ",
													YesNo.Yes);

											if (home.listButtonOnSDG(TitleOfSDG, actionButtonName, 30) != null) {
												log(LogStatus.INFO,
														"------------Action List Button: " + actionButtonName
																+ " is showing to SDG Grid " + TitleOfSDG
																+ "----------------",
														YesNo.Yes);

												if (contact.createReferralThroughSDG(projectName, TitleOfSDG,
														actionButtonName, sdgLabels, 30)) {
													log(LogStatus.INFO, "Firm : " + sdgLabels[0][1]
															+ " Created Through SDG: " + TitleOfSDG, YesNo.Yes);
													if (home.firmNameHeader(sdgLabels[0][1], 30) != null) {
														log(LogStatus.INFO,
																"Header Verified of Firm : " + sdgLabels[0][1]
																		+ " Created Through SDG: " + TitleOfSDG,
																YesNo.Yes);
													} else {
														log(LogStatus.ERROR,
																"Header Not Verified of Firm : " + sdgLabels[0][1]
																		+ " Created Through SDG: " + TitleOfSDG,
																YesNo.Yes);
														sa.assertTrue(false,
																"Header Not Verified of Firm : " + sdgLabels[0][1]
																		+ " Created Through SDG: " + TitleOfSDG);

													}
												} else {
													log(LogStatus.ERROR, "Not Able to Create Firm : " + sdgLabels[0][1]
															+ " Created Through SDG: " + TitleOfSDG, YesNo.Yes);
													sa.assertTrue(false, "Not Able to Create Firm : " + sdgLabels[0][1]
															+ " Created Through SDG: " + TitleOfSDG);

												}

											} else {
												log(LogStatus.FAIL,
														"------------Action List Button: " + actionButtonName
																+ " is not showing to SDG Grid " + TitleOfSDG
																+ "----------------",
														YesNo.No);
												appLog.error("------------Action List Button: " + actionButtonName
														+ " is not showing to SDG Grid " + TitleOfSDG
														+ "----------------");
												;
												sa.assertTrue(false,
														"------------Action List Button: " + actionButtonName
																+ " is not showing to SDG Grid " + TitleOfSDG
																+ "----------------");

											}
										} else {
											log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------",
													YesNo.No);
											sa.assertTrue(false,
													"-----------Not able to Expand SDG Grid --------------");
										}

									} else {
										log(LogStatus.ERROR, "-----------Component Not Added to Contact Page: "
												+ TitleOfSDG + " -------------", YesNo.Yes);
										sa.assertTrue(false, "-----------Component Not Added to Contact Page: "
												+ TitleOfSDG + " ------------");

									}

								} else {
									log(LogStatus.ERROR, "Not Able to Click/Find First Element of Table ", YesNo.No);

								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on " + viewList, YesNo.No);

							}

						} else {
							log(LogStatus.ERROR, "All List View not already present", YesNo.No);

						}
					} else {
						log(LogStatus.ERROR,
								"list dropdown is not clickable, so cannot check presence of All List View", YesNo.Yes);

					}

				} else

				{
					log(LogStatus.ERROR, "-----------Not Able to logged in to the App--------------", YesNo.No);
					sa.assertTrue(false, "-----------Not Able to logged in to the App--------------");

				}

			} else {
				log(LogStatus.ERROR,
						"-----------Not Able to click on tab:" + TabName.Object2Tab.toString() + " --------------",
						YesNo.No);
				sa.assertTrue(false,
						"-----------Not Able to click on tab:" + TabName.Object2Tab.toString() + " --------------");

			}

		} else

		{
			log(LogStatus.ERROR, "-----------Not Able to logged in to the App--------------", YesNo.No);
			sa.assertTrue(false, "-----------Not Able to logged in to the App--------------");

		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc035_AddOpenTaskGridOnHomePage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		String TitleOfSDG = M9_TC035_SDGName;
		String openTaskDataProviderName = M9_TC035_SDGDataProviderName;
		String referencedComponentHeading = M9_TC031_1_ReferencedComponentHeading;

		if (lp.CRMLogin(superAdminUserName, adminPassword, appName)) {
			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
						"Component Title ", action.SCROLLANDBOOLEAN, 10);
				if (alreadyAddedComponentToHomePage != null) {

					log(LogStatus.INFO, "------------Component Already Added to Home Page, So Not adding Component: "
							+ TitleOfSDG + "----------------", YesNo.Yes);
					sa.assertTrue(true, "------------Component Already Added to Home Page, So Not adding Component: "
							+ TitleOfSDG + "---------------");
				}

				else {

					if (edit.addSDGComponentToRefrencedComponent(projectName, "Navatar SDG", TitleOfSDG,
							openTaskDataProviderName, referencedComponentHeading)) {
						log(LogStatus.INFO, "Added SDG: " + TitleOfSDG + " to the Home Page", YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Able to Added SDG: " + TitleOfSDG + " to the Home Page", YesNo.No);

					}

				}

			}

			else {
				log(LogStatus.ERROR,
						"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------",
						YesNo.No);
				sa.assertTrue(false,
						"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

			}
		}

		else {
			log(LogStatus.ERROR, "-----------Not Able to logged in to the App--------------", YesNo.No);
			sa.assertTrue(false, "-----------Not Able to logged in to the App--------------");

		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc036_VerifyErrorMessageForOpenTaskSDGFilter(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String sdgName = M9_TC035_SDGName;
		String[][] data = { M9_TC036_MPickListErrorMsgData1.split("<break>") };

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				if (home.sdgGridExpandedByDefaultIfNotThenExpand(sdgName)) {
					log(LogStatus.INFO, "Verified SDG Grid: " + sdgName + " is Expanded By or Not ", YesNo.Yes);
					if (click(driver, home.gtFilterButton(sdgName, 20), "Filter Button on SDG: " + sdgName,
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + sdgName, YesNo.No);

						if (home.sdgFilterSendDataAndDropDownHandleAndVerifyErrorMsg(sdgName, data)) {
							log(LogStatus.INFO, "---------Verified Filters Applied And Error Msg Found to SDG: "
									+ sdgName + " ---------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "---------Not Verified Filters Apply And Error Msg Found to SDG: "
									+ sdgName + " ---------", YesNo.Yes);
							sa.assertTrue(false, "---------Not Verified Filters Apply And Error Msg Found to SDG: "
									+ sdgName + " ---------");
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + sdgName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + sdgName);
					}

				} else {
					log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
					sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + sdgName + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + sdgName + " ------------");

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
	public void M9Tc037_VerifyRememberfilterAndVerifyTheImpactOnSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);

		String TitleOfSDG = M9_TC035_SDGName;
		String rememberFilterCheckBoxLabel = "Remember Filter";
		String[][] data = { M9_TC036_MPickListErrorMsgData1.split("<break>") };
		boolean rememberFilterFlag = false;
		String contactNameFilterLabel = M9_TC036_MPickListErrorMsgData1.split("<break>")[3];
		String contactNameFilterValue = M9_TC036_MPickListErrorMsgData1.split("<break>")[2];

		List<String> expectedDefaultContactNameOptionSelected = Arrays.asList(contactNameFilterValue);

		String parentId;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");

				if (home.ClickOnOpenSDGRecord(TitleOfSDG)) {

					parentId = null;
					parentId = home.SwitchToSDGWindow(TitleOfSDG);
					if (parentId != null) {
						log(LogStatus.PASS, "-----------Switched to Open SDG Record Window of SDG: " + TitleOfSDG
								+ "--------------", YesNo.No);

						log(LogStatus.INFO, "Succesfully move to Tab : " + TabName.SDGTab, YesNo.No);

						if (sdg.editCheckBoxOfSDGAfterClickOnOpenSDGRecord(projectName, TitleOfSDG,
								Condition.SelectCheckbox, rememberFilterCheckBoxLabel, 30)) {
							log(LogStatus.PASS, "-----------Edit/Verify SDG: " + TitleOfSDG + "--------------",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentId);
							CommonLib.refresh(driver);
							CommonLib.ThreadSleep(8000);
							rememberFilterFlag = true;

						}

						else {
							log(LogStatus.FAIL,
									"-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------",
									YesNo.No);
							sa.assertTrue(false,
									"-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------");

						}
						// 2nd Part

						log(LogStatus.INFO, "---------Now Going to Verify Error Msg After Filter Applied in SDG: "
								+ TitleOfSDG + " in case of User-----------", YesNo.Yes);
						if (rememberFilterFlag) {

							log(LogStatus.INFO, "------Remember Filter Applied Verified-------", YesNo.No);
							if (lp.CRMlogout()) {
								log(LogStatus.PASS, "-----------Successfully Logged Out--------------", YesNo.No);
								CommonLib.ThreadSleep(8000);
								if (lp.CRMLogin(crmUser1EmailID, adminPassword, appName)) {
									log(LogStatus.PASS, "-----------Successfully Logged In with Id: " + crmUser1EmailID
											+ "--------------", YesNo.No);

									CommonLib.ThreadSleep(8000);
									if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
										log(LogStatus.INFO,
												"Verified SDG Grid: " + TitleOfSDG
														+ " is Expanded By Default or Not, If not then Expand it ",
												YesNo.Yes);
										if (click(driver, home.gtFilterButton(TitleOfSDG, 20),
												"Filter Button on SDG: " + TitleOfSDG, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + TitleOfSDG,
													YesNo.No);

											if (home.sdgFilterSendDataAndDropDownHandleAndVerifyErrorMsg(TitleOfSDG,
													data)) {
												log(LogStatus.INFO,
														"---------Verified Filters Applied And Error Msg Found to SDG: "
																+ TitleOfSDG + " ---------",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"---------Not Verified Filters Apply And Error Msg Found to SDG: "
																+ TitleOfSDG + " ---------",
														YesNo.Yes);
												sa.assertTrue(false,
														"---------Not Verified Filters Apply And Error Msg Found to SDG: "
																+ TitleOfSDG + " ---------");
											}

										} else {
											log(LogStatus.ERROR,
													"Not able to click on Filter Button on SDG: " + TitleOfSDG,
													YesNo.Yes);
											sa.assertTrue(false,
													"Not able to click on Filter Button on SDG: " + TitleOfSDG);
										}

									} else {
										log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------",
												YesNo.No);
										sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
									}
								} else {
									log(LogStatus.ERROR, "-----------Not Able to Logged in with Id: " + crmUser1EmailID
											+ " -------------", YesNo.Yes);
									sa.assertTrue(false, "-----------Not Able to Logged in with Id: " + crmUser1EmailID
											+ " -------------");

								}
							} else {
								log(LogStatus.ERROR, "-----------Not Able to LogOut -------------", YesNo.Yes);
								sa.assertTrue(false, "-----------Not Able to LogOut -------------");

							}
						} else {
							log(LogStatus.ERROR,
									"------Remember Filter Applied Not Verified, So Not Able to Continue to verify Error Msg from User-------",
									YesNo.Yes);
							sa.assertTrue(false,
									"------Remember Filter Applied Not Verified, So Not Able to Continue to verify Error Msg from User-------");

						}

						// 3rd Part
						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(10000);
						log(LogStatus.INFO, "---------Now Going to Verify Default Filters in SDG: " + TitleOfSDG
								+ " in case of User-----------", YesNo.Yes);
						if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
							log(LogStatus.INFO, "Verified SDG Grid: " + TitleOfSDG
									+ " is Expanded By Default or Not, if not Then Expand ", YesNo.Yes);
							if (click(driver, home.gtFilterButton(TitleOfSDG, 20),
									"Filter Button on SDG: " + TitleOfSDG, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + TitleOfSDG, YesNo.No);

								if (home.VerifySDGFilterSelectDefaultSelectedValue(TitleOfSDG, contactNameFilterLabel,
										expectedDefaultContactNameOptionSelected)) {
									log(LogStatus.INFO,
											"--------" + contactNameFilterLabel + " Default Value: "
													+ expectedDefaultContactNameOptionSelected + " Matched--------",
											YesNo.No);
									String inputBoxValue = CommonLib.getAttribute(driver,
											home.inputBoxForSDGFilterName(contactNameFilterLabel, 20), "", "value");
									if (inputBoxValue.equals("")) {
										log(LogStatus.INFO, "------Verified: Input Box: " + contactNameFilterLabel
												+ " Cleared--------", YesNo.No);
										CommonLib.ThreadSleep(2000);

									} else {
										log(LogStatus.ERROR,
												"------Input Box: " + contactNameFilterLabel
														+ " not gets Cleared Expected: " + "" + " but Actual: "
														+ inputBoxValue + "--------",
												YesNo.Yes);
										sa.assertTrue(false,
												"------Input Box: " + contactNameFilterLabel
														+ " not gets Cleared Expected: " + "" + " but Actual: "
														+ inputBoxValue + "--------");
									}
								} else {
									log(LogStatus.ERROR,
											"--------" + contactNameFilterLabel + " Default Value: "
													+ expectedDefaultContactNameOptionSelected + " Not Matched--------",
											YesNo.Yes);
									sa.assertTrue(false, "--------" + contactNameFilterLabel + " Default Value: "
											+ expectedDefaultContactNameOptionSelected + " Not Matched--------");
								}
							}

							else {
								log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + TitleOfSDG,
										YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + TitleOfSDG);
							}
						} else {
							log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
							sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
						}

					}

					else {
						log(LogStatus.FAIL, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
								+ TitleOfSDG + "--------------", YesNo.No);
						sa.assertTrue(false, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
								+ TitleOfSDG + "--------------");

					}

				} else {
					log(LogStatus.FAIL,
							"-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG + "--------------",
							YesNo.No);
					sa.assertTrue(false,
							"-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG + "--------------");

				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

		}

		else {
			log(LogStatus.ERROR,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------", YesNo.No);
			sa.assertTrue(false,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

		}

		lp.CRMlogout();

		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc038_EditTabAndAddFundPrepSDG(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver);
		EditPageBusinessLayer EB = new EditPageBusinessLayer(driver);
		String sdgName = M9_TC038_SDGName;
		String referencedComponentHeading = M9_TC031_1_ReferencedComponentHeading;
		String DataProviderNameAfterColon = M9_TC038_SDGDataProviderName;

		/*
		 * String sdgName = "Fund Prep"; String referencedComponentHeading = "Deals";
		 * String DataProviderNameAfterColon = "Fund Prep";
		 */

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (BP.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, TabName.HomeTab + " has been open", YesNo.No);

			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.ERROR, "------------Component Already Added to Home Page " + sdgName
						+ ", So Not able to Add SDG and Global Filter To Home Page----------------", YesNo.Yes);
				sa.assertTrue(false, "------------Component Already Added to Home Page " + sdgName
						+ ", So Not able to Add SDG and Global Filter To Home Page----------------");
			}

			else {
				log(LogStatus.INFO, "-----------Component Not Already Added to Home Page: " + sdgName
						+ ", SO Going to Add SDG and Global Filter to HomePage -------------", YesNo.No);

				if (EB.addSDGComponentToRefrencedComponent(projectName, "Navatar SDG", sdgName,
						DataProviderNameAfterColon, referencedComponentHeading)) {
					log(LogStatus.INFO, sdgName + " sdg has been added", YesNo.No);

					CommonLib.refresh(driver);

					/*
					 * if (EB.editPageAndAddFilter("Fund Records",
					 * "Select Name from navpeII__Fund__c ORDER BY Name ASC", "", "", "", "",
					 * Condition.SelectCheckbox))
					 */

					if (EB.editPageAndAddFilter("Fund Records", M9_TC038_GlobalFilterQuery, "", "", "", "",
							Condition.SelectCheckbox)) {
						log(LogStatus.INFO, "Global Filter has been added", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Global filter is not added", YesNo.Yes);
						sa.assertTrue(false, "Global filter is not added");
					}

				} else {
					log(LogStatus.ERROR, "Not able to add the Fund Prep SDG", YesNo.Yes);
					sa.assertTrue(false, "Not able to add the Fund Prep SDG");
				}
			}

		} else {
			log(LogStatus.ERROR, "Not able to open the " + TabName.HomeTab + "", YesNo.Yes);
			sa.assertTrue(false, "Not able to open the " + TabName.HomeTab + "");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc039_VerifySDGFilterAlongWithInnerQueryInMyRecords(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver);
		EditPageBusinessLayer EB = new EditPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);

		String sdgName = M9_TC038_SDGName;
		int accountFilterRowCount, rowCountAfterFilter;
		String pageSize = "100";
		String xPath = "";
		WebElement ele = null;

		String[] numberOfRecords = M9_TC039_SDGNumberOfRecords.split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (BP.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, TabName.HomeTab + " has been open", YesNo.No);

			xPath = "//a[text()='" + sdgName
					+ "']/ancestor::article/preceding-sibling::lightning-icon[@title='Expand']";
			ele = CommonLib.FindElement(driver, xPath, sdgName + " SDG", action.SCROLLANDBOOLEAN, 20);

			if (ele != null) {
				if (CommonLib.clickUsingJavaScript(driver, ele, sdgName + " Expend icon")) {
					log(LogStatus.INFO, "Clicked on the " + sdgName + " SDG Expend icon", YesNo.No);
					sa.assertTrue(true, "Clicked on the " + sdgName + " SDG Expend icon");
				} else {
					log(LogStatus.ERROR, "Could not click on the " + sdgName + " SDG Expend icon", YesNo.No);
					sa.assertTrue(false, "Could not click on the " + sdgName + " SDG Expend icon");
				}
			}

			if (SB.sdgFilterSendDataAndFound(sdgName, M9_TC039_FilterLabel, M9_TC039_FilterSearch,
					M9_TC039_FilterPickList)) {
				log(LogStatus.PASS, M9_TC039_FilterSearch + " has been filtered in the " + sdgName, YesNo.No);
				sa.assertTrue(true, M9_TC039_FilterSearch + " Filter has been filtered in the " + sdgName);
				CommonLib.ThreadSleep(20000);
				accountFilterRowCount = AppBuilder.numberOfRecordsWithoutClickOnExpendIcon(M9_TC038_SDGName, pageSize);

				if (accountFilterRowCount == Integer.parseInt(numberOfRecords[0])) {
					log(LogStatus.PASS, "Filter Successfully, count is matched: " + accountFilterRowCount, YesNo.No);
					sa.assertTrue(true, "Filter Successfully and count is matched " + accountFilterRowCount);
				} else {
					log(LogStatus.ERROR,
							"Filtered not successfully, Count is not matched, Expected: "
									+ Integer.parseInt(numberOfRecords[0]) + " but Actual: " + accountFilterRowCount,
							YesNo.No);
					sa.assertTrue(false, "Filtered not successfully, Count is not matched, Expected: "
							+ Integer.parseInt(numberOfRecords[0]) + " but Actual: " + accountFilterRowCount);
				}

			} else {
				log(LogStatus.ERROR, M9_TC039_FilterSearch + " is not filtered in the " + sdgName, YesNo.Yes);
				sa.assertTrue(false, M9_TC039_FilterSearch + " is not filtered in the " + sdgName);
			}
		} else {
			log(LogStatus.ERROR, "Not able to open the " + TabName.HomeTab + "", YesNo.Yes);
			sa.assertTrue(false, "Not able to open the " + TabName.HomeTab + "");
		}

		if (BP.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, TabName.HomeTab + " has been open", YesNo.No);
			CommonLib.refresh(driver);
			if (AppBuilder.selectFilter("Show", "My Records")) {
				log(LogStatus.INFO, "Filter has been selected: ", YesNo.No);
				CommonLib.ThreadSleep(20000);

				rowCountAfterFilter = AppBuilder.numberOfRecordsWithoutClickOnExpendIcon(sdgName, pageSize);

				if (rowCountAfterFilter == Integer.parseInt(numberOfRecords[1])) {
					log(LogStatus.PASS, "Record has been matched on the basis of the My Record FIlter", YesNo.No);
					sa.assertTrue(true, "Record has been matched on the basis of the My Record FIlter");
				} else {
					log(LogStatus.ERROR, "Record is not matched on the basis of the My Record FIlter", YesNo.Yes);
					sa.assertTrue(false, "Record is not matched on the basis of the My Record FIlter");
				}

			} else {
				log(LogStatus.ERROR, "My Record is not filtered from the global filter", YesNo.Yes);
				sa.assertTrue(false, "My Record is not filtered from the global filter");
			}
		} else {
			log(LogStatus.ERROR, "Not able to open the " + TabName.HomeTab + "", YesNo.Yes);
			sa.assertTrue(false, "Not able to open the " + TabName.HomeTab + "");
		}

		if (BP.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, TabName.HomeTab + " has been open", YesNo.No);
			CommonLib.refresh(driver);
			if (SB.sdgFilterSendDataAndFound(sdgName, "Fund Type", "Fund")) {
				log(LogStatus.PASS, "Fund type has been filtered in the " + sdgName, YesNo.No);
				sa.assertTrue(true, "Fund type has been filtered in the " + sdgName);
				accountFilterRowCount = home.numberOfRecords(sdgName, pageSize);

				if (accountFilterRowCount == Integer.parseInt(numberOfRecords[2])) {
					log(LogStatus.PASS, "Filter Successfully, count is matched: " + accountFilterRowCount, YesNo.No);
					sa.assertTrue(true, "Filter Successfully and count is matched: " + accountFilterRowCount);
				} else {
					log(LogStatus.ERROR,
							"Filtered not successfully, Count is not matched. Expected: "
									+ Integer.parseInt(numberOfRecords[2]) + " but Actual: " + accountFilterRowCount,
							YesNo.No);
					sa.assertTrue(false, "Filtered not successfully, Count is not matched. Expected: "
							+ Integer.parseInt(numberOfRecords[2]) + " but Actual: " + accountFilterRowCount);
				}

			} else {
				log(LogStatus.ERROR, "Fund type is not filtered in the " + sdgName, YesNo.Yes);
				sa.assertTrue(false, "Fund type is not filtered in the " + sdgName);
			}
		} else {
			log(LogStatus.ERROR, "Not able to open the " + TabName.HomeTab + "", YesNo.Yes);
			sa.assertTrue(false, "Not able to open the " + TabName.HomeTab + "");
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc040_ChecktheCheckboxforRememberFilterandverifyimpactonfilter(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver);
		EditPageBusinessLayer EB = new EditPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);

		String sdgName = M9_TC038_SDGName;
		String fundName = M9_TC039_FilterSearch;
		String pageSize = "100";
		String xPath = "";
		WebElement ele = null;
		int rowCountAfterFilter;
		String[] numberOfRecords = M9_TC040_SDGNumberOfRecords.split("<break>");

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (BP.openAppFromAppLauchner(60, "Sortable Data Grids")) {

			log(LogStatus.INFO, "sortable data grid has been open from the app launcher", YesNo.No);
			if (SB.openSDG(projectName, sdgName)) {
				log(LogStatus.INFO, sdgName + " has been open from the App Launcher", YesNo.No);
				CommonLib.refresh(driver);
				if (SB.editCheckBoxOfSDGAfterClickOnOpenSDGRecord(projectName, sdgName, Condition.SelectCheckbox,
						"Remember Filter", 50)) {
					log(LogStatus.ERROR, "Remember filter has been selected", YesNo.No);
				} else {
					log(LogStatus.INFO, "Remember filter has been selected", YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to open the " + sdgName + " SDG", YesNo.Yes);
				sa.assertTrue(false, "Not able to open the " + sdgName + " SDG");
			}

		} else {
			log(LogStatus.ERROR, "Not able to open the sortable data grid from the app launcher", YesNo.Yes);
			sa.assertTrue(false, "Not able to open the sortable data grid from the app launcher");
		}

		lp.CRMlogout();
		CommonLib.ThreadSleep(12000);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (BP.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, TabName.HomeTab + " has been open", YesNo.No);

			xPath = "//a[text()='" + sdgName
					+ "']/ancestor::article/preceding-sibling::lightning-icon[@title='Expand']";
			ele = CommonLib.FindElement(driver, xPath, sdgName + " SDG", action.SCROLLANDBOOLEAN, 20);

			if (ele != null) {
				if (CommonLib.clickUsingJavaScript(driver, ele, "Fund Prep Expend icon")) {
					log(LogStatus.INFO, "Clicked on the " + sdgName + " SDG Expend icon", YesNo.No);
					sa.assertTrue(true, "Clicked on the " + sdgName + " SDG Expend icon");
				} else {
					log(LogStatus.ERROR, "Could not click on the " + sdgName + " SDG Expend icon", YesNo.No);
					sa.assertTrue(false, "Could not click on the " + sdgName + " SDG Expend icon");
				}
			}

			if (SB.sdgFilterSendDataAndFound(sdgName, "Fund Name", fundName, "Equals")) {
				log(LogStatus.PASS, fundName + " has been filtered in the " + sdgName, YesNo.No);
				sa.assertTrue(true, fundName + " Filter has been filtered in the " + sdgName);
				CommonLib.ThreadSleep(20000);
				if (!SB.verifyRecordExistOrNotOnSDG(sdgName, sdgName + " SDG")) {
					if (SB.verifyRecordAfterApplyingGlobalFilter(sdgName, fundName, "Fund Name", "Fund Prep SDG")) {
						log(LogStatus.PASS, fundName + " filter has been applied", YesNo.No);
						sa.assertTrue(true, fundName + " filter has been applied");

						CommonLib.refresh(driver);

						if (SB.verifyFilter(sdgName, "Fund Name", fundName)) {
							log(LogStatus.PASS, fundName + " filter is available after refresh the page", YesNo.No);
							sa.assertTrue(true, fundName + " filter is available after refresh the page");

						} else {
							log(LogStatus.ERROR, fundName + " filter is not available after refresh the page",
									YesNo.Yes);
							sa.assertTrue(false, fundName + " filter is not available after refresh the page");
						}

					} else {
						log(LogStatus.ERROR, fundName + " filter is not applied", YesNo.Yes);
						sa.assertTrue(false, fundName + " filter is not applied");
					}
				} else {
					log(LogStatus.PASS, "Record is not persent against " + fundName
							+ " Filter on Fund But Filter has been applied successfully", YesNo.Yes);
					sa.assertTrue(true, "Record is not persent against " + fundName
							+ " Filter on Fund But Filter has been applied successfully");

				}

			} else {
				log(LogStatus.ERROR, fundName + " is not filtered in the " + sdgName, YesNo.Yes);
				sa.assertTrue(false, fundName + " is not filtered in the " + sdgName);
			}

			if (BP.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, TabName.HomeTab + " has been open", YesNo.No);
				CommonLib.refresh(driver);
				if (AppBuilder.selectFilter("Show", "My Records")) {
					log(LogStatus.INFO, "Filter has been selected: ", YesNo.No);
					CommonLib.ThreadSleep(20000);

					rowCountAfterFilter = AppBuilder.numberOfRecordsWithoutClickOnExpendIcon(sdgName, pageSize);
					if (rowCountAfterFilter == Integer.parseInt(numberOfRecords[0])) {
						log(LogStatus.PASS,
								"Record has been matched on the basis of the My Record FIlter: " + rowCountAfterFilter,
								YesNo.No);
						sa.assertTrue(true,
								"Record has been matched on the basis of the My Record FIlter: " + rowCountAfterFilter);
					} else {
						log(LogStatus.ERROR, "Record is not matched on the basis of the My Record FIlter, Expected: "
								+ numberOfRecords[0] + " but Actual: " + rowCountAfterFilter, YesNo.Yes);
						sa.assertTrue(false, "Record is not matched on the basis of the My Record FIlter, Expected: "
								+ numberOfRecords[0] + " but Actual: " + rowCountAfterFilter);
					}

				} else {
					log(LogStatus.ERROR, "My Record is not filtered from the global filter", YesNo.Yes);
					sa.assertTrue(false, "My Record is not filtered from the global filter");
				}
			} else {
				log(LogStatus.ERROR, "Not able to open the " + TabName.HomeTab + "", YesNo.Yes);
				sa.assertTrue(false, "Not able to open the " + TabName.HomeTab + "");
			}

			if (BP.clickOnTab(projectName, TabName.HomeTab)) {

				log(LogStatus.INFO, TabName.HomeTab + " has been open", YesNo.No);
				CommonLib.refresh(driver);
				if (SB.sdgFilterSendDataAndFound(sdgName, M9_TC040_StandardFilterLabel1,
						M9_TC040_StandardFilterPickList1)) {
					log(LogStatus.PASS, "Fund type has been filtered in the " + sdgName, YesNo.No);
					sa.assertTrue(true, "Fund type has been filtered in the " + sdgName);

					xPath = "//a[text()='" + sdgName
							+ "']/ancestor::article/preceding-sibling::lightning-icon[@title='Expand']";
					ele = CommonLib.FindElement(driver, xPath, sdgName + " SDg", action.SCROLLANDBOOLEAN, 20);

					if (ele != null) {
						if (CommonLib.clickUsingJavaScript(driver, ele, sdgName + " Expend icon")) {
							log(LogStatus.INFO, "Clicked on the " + sdgName + " SDG Expend icon", YesNo.No);
							sa.assertTrue(true, "Clicked on the " + sdgName + " SDG Expend icon");
						} else {
							log(LogStatus.ERROR, "Could not click on the " + sdgName + " SDG Expend icon", YesNo.No);
							sa.assertTrue(false, "Could not click on the " + sdgName + " SDG Expend icon");
						}
					}

					rowCountAfterFilter = home.numberOfRecords(sdgName, pageSize);

					if (rowCountAfterFilter == Integer.parseInt(numberOfRecords[1])) {
						log(LogStatus.PASS, "Filter Successfully, count is matched " + rowCountAfterFilter, YesNo.No);
						sa.assertTrue(true, "Filter Successfully and count is matched " + rowCountAfterFilter);
					} else {
						log(LogStatus.ERROR,
								"Filtered not successfully, Count is not matched. Expected: "
										+ Integer.parseInt(numberOfRecords[1]) + " but Actual: " + rowCountAfterFilter,
								YesNo.No);
						sa.assertTrue(false, "Filtered not successfully, Count is not matched. Expected: "
								+ Integer.parseInt(numberOfRecords[1]) + " but Actual: " + rowCountAfterFilter);
					}

				} else {
					log(LogStatus.ERROR, "Fund type is not filtered in the " + sdgName, YesNo.Yes);
					sa.assertTrue(false, "Fund type is not filtered in the " + sdgName);
				}
			} else {
				log(LogStatus.ERROR, "Not able to open the " + TabName.HomeTab + "", YesNo.Yes);
				sa.assertTrue(false, "Not able to open the " + TabName.HomeTab + "");
			}
			lp.CRMlogout();
			ThreadSleep(12000);
			lp.CRMLogin(superAdminUserName, adminPassword, appName);
			if (BP.openAppFromAppLauchner(60, "Sortable Data Grids")) {

				log(LogStatus.INFO, "sortable data grid has been open from the app launcher", YesNo.No);
				if (SB.openSDG(projectName, sdgName)) {
					log(LogStatus.INFO, sdgName + " has been open from the App Launcher", YesNo.No);
					CommonLib.refresh(driver);
					if (SB.editCheckBoxOfSDGAfterClickOnOpenSDGRecord(projectName, sdgName, Condition.UnSelectCheckbox,
							"Remember Filter", 50)) {
						log(LogStatus.ERROR, "Remember filter has been selected", YesNo.No);
					} else {
						log(LogStatus.INFO, "Remember filter has been selected", YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Not able to open the " + sdgName + " SDG", YesNo.Yes);
					sa.assertTrue(false, "Not able to open the " + sdgName + " SDG");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open the sortable data grid from the app launcher", YesNo.Yes);
				sa.assertTrue(false, "Not able to open the sortable data grid from the app launcher");
			}

		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc041_VerifyMyHomeCheckboxAsTrueAndVerifydDataOnTheGrid(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);

		String TitleOfSDG = M9_TC038_SDGName;
		String myHomeFilterCheckBoxLabel = "My Home";

		String pageSize = "100";
		String[] numberOfRecords = M9_TC041_SDGNumberOfRecords.split("<break>");
		int expectedRecordsInAdminCase = Integer.parseInt(numberOfRecords[0]);
		int expectedRecordsInUserCase = Integer.parseInt(numberOfRecords[1]);

		boolean myHomeFilterFlag = false;
		String parentId;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");

				if (home.ClickOnOpenSDGRecord(TitleOfSDG)) {

					parentId = null;
					parentId = home.SwitchToSDGWindow(TitleOfSDG);
					if (parentId != null) {
						log(LogStatus.PASS, "-----------Switched to Open SDG Record Window of SDG: " + TitleOfSDG
								+ "--------------", YesNo.No);

						log(LogStatus.INFO, "Succesfully move to Tab : " + TabName.SDGTab, YesNo.No);

						if (sdg.editCheckBoxOfSDGAfterClickOnOpenSDGRecord(projectName, TitleOfSDG,
								Condition.SelectCheckbox, myHomeFilterCheckBoxLabel, 30)) {
							log(LogStatus.PASS, "-----------Edit/Verify SDG: " + TitleOfSDG + "--------------",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentId);
							CommonLib.refresh(driver);
							CommonLib.ThreadSleep(8000);
							myHomeFilterFlag = true;

						}

						else {
							log(LogStatus.FAIL,
									"-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------",
									YesNo.No);
							sa.assertTrue(false,
									"-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------");

						}
						// 2nd Part

						log(LogStatus.INFO, "---------Now Going to Verify Records After Checked the Filter: "
								+ myHomeFilterCheckBoxLabel + " in SDG: " + TitleOfSDG + " in case of Admin-----------",
								YesNo.Yes);
						if (myHomeFilterFlag) {

							log(LogStatus.INFO,
									"------" + myHomeFilterCheckBoxLabel + " Filter Applied Verified-------", YesNo.No);

							int rowCountAfterFilter = home.numberOfRecords(TitleOfSDG, pageSize);

							if (expectedRecordsInAdminCase == rowCountAfterFilter) {
								log(LogStatus.INFO,
										"------------Number of Records Matched in Admin Case, Expected: "
												+ expectedRecordsInAdminCase + ", Actual: " + rowCountAfterFilter
												+ "----------------",
										YesNo.Yes);

							} else {
								log(LogStatus.FAIL,
										"------------Number of Records Not Matched in Admin Case, Expected: "
												+ expectedRecordsInAdminCase + ", Actual: " + rowCountAfterFilter
												+ "----------------",
										YesNo.No);
								sa.assertTrue(false,
										"------------Number of Records Not Matched in Admin Case, Expected: "
												+ expectedRecordsInAdminCase + ", Actual: " + rowCountAfterFilter
												+ "----------------");

							}

						} else {
							log(LogStatus.ERROR, "------" + myHomeFilterCheckBoxLabel
									+ " Filter Applied Not Verified, So Not Able to Continue to verify Records-------",
									YesNo.Yes);
							sa.assertTrue(false, "------" + myHomeFilterCheckBoxLabel
									+ " Filter Applied Not Verified, So Not Able to Continue to verify Records-------");

						}

						// 3rd Part

						if (lp.CRMlogout()) {
							log(LogStatus.PASS, "-----------Successfully Logged Out--------------", YesNo.No);
							if (lp.CRMLogin(crmUser1EmailID, adminPassword, appName)) {
								log(LogStatus.PASS, "-----------Successfully Logged In with Id: " + crmUser1EmailID
										+ "--------------", YesNo.No);
								log(LogStatus.INFO,
										"---------Now Going to Verify Records After Checked the Filter: "
												+ myHomeFilterCheckBoxLabel + " in SDG: " + TitleOfSDG
												+ " in case of User-----------",
										YesNo.Yes);
								if (myHomeFilterFlag) {

									log(LogStatus.INFO,
											"------" + myHomeFilterCheckBoxLabel + " Filter Applied Verified-------",
											YesNo.No);

									int rowCountAfterFilter = home.numberOfRecords(TitleOfSDG, pageSize);

									if (expectedRecordsInUserCase == rowCountAfterFilter) {
										log(LogStatus.INFO,
												"------------Number of Records Matched in User Case, Expected: "
														+ expectedRecordsInUserCase + ", Actual: " + rowCountAfterFilter
														+ "----------------",
												YesNo.Yes);

									} else {
										log(LogStatus.FAIL,
												"------------Number of Records Not Matched in User Case, Expected: "
														+ expectedRecordsInUserCase + ", Actual: " + rowCountAfterFilter
														+ "----------------",
												YesNo.No);
										sa.assertTrue(false,
												"------------Number of Records Not Mathched in User Case, Expected: "
														+ expectedRecordsInUserCase + ", Actual: " + rowCountAfterFilter
														+ "----------------");

									}

								} else {
									log(LogStatus.ERROR, "------" + myHomeFilterCheckBoxLabel
											+ " Filter Applied Not Verified, So Not Able to Continue to verify Records-------",
											YesNo.Yes);
									sa.assertTrue(false, "------" + myHomeFilterCheckBoxLabel
											+ " Filter Applied Not Verified, So Not Able to Continue to verify Records-------");

								}
							} else {
								log(LogStatus.ERROR, "-----------Not Able to Logged in with Id: " + crmUser1EmailID
										+ " -------------", YesNo.Yes);
								sa.assertTrue(false, "-----------Not Able to Logged in with Id: " + crmUser1EmailID
										+ " -------------");

							}
						} else {
							log(LogStatus.ERROR, "-----------Not Able to LogOut -------------", YesNo.Yes);
							sa.assertTrue(false, "-----------Not Able to LogOut -------------");

						}

					}

					else {
						log(LogStatus.FAIL, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
								+ TitleOfSDG + "--------------", YesNo.No);
						sa.assertTrue(false, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
								+ TitleOfSDG + "--------------");

					}

				} else {
					log(LogStatus.FAIL,
							"-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG + "--------------",
							YesNo.No);
					sa.assertTrue(false,
							"-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG + "--------------");

				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

		}

		else {
			log(LogStatus.ERROR,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------", YesNo.No);
			sa.assertTrue(false,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

		}

		lp.CRMlogout();

		sa.assertAll();
	}

	/**
	 * @param projectName
	 */
	@Parameters({ "projectName" })
	@Test
	public void M9Tc042_VerifyFilterApplyOnFundPrepAndVerifyRecords(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);

		String[][] data = { M9_TC042_FilterMiscData.split("<break>") };

		String TitleOfSDG = M9_TC038_SDGName;

		String pageSize = "100";
		int expectedRecordsInUserCase = Integer.parseInt(M9_TC042_SDGNumberOfRecords);

		if (lp.CRMLogin(crmUser1EmailID, adminPassword, appName)) {
			log(LogStatus.PASS, "-----------Successfully Logged In with Id: " + crmUser1EmailID + "--------------",
					YesNo.No);
			log(LogStatus.INFO, "---------Now Going to Verify Records After Applied SDG Filter in SDG: " + TitleOfSDG
					+ " in case of User-----------", YesNo.Yes);
			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
				WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
						"Component Title ", action.SCROLLANDBOOLEAN, 10);

				if (alreadyAddedComponentToHomePage != null) {
					log(LogStatus.INFO,
							"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
							YesNo.Yes);

					if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
						log(LogStatus.INFO, "Verified SDG Grid: " + TitleOfSDG
								+ " is Expanded By Default or Not, If not then Expand it ", YesNo.Yes);
						if (click(driver, home.gtFilterButton(TitleOfSDG, 20), "Filter Button on SDG: " + TitleOfSDG,
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + TitleOfSDG, YesNo.No);

							if (home.sdgFilterSendDataAndDropDownHandle(TitleOfSDG, data)) {
								log(LogStatus.INFO,
										"---------Verified Filters Applied to SDG: " + TitleOfSDG + " ---------",
										YesNo.No);
								int rowCountAfterFilter = home.numberOfRecords(TitleOfSDG, pageSize);

								if (expectedRecordsInUserCase == rowCountAfterFilter) {
									log(LogStatus.INFO,
											"------------Number of Records Matched in User Case, Expected: "
													+ expectedRecordsInUserCase + ", Actual: " + rowCountAfterFilter
													+ "----------------",
											YesNo.Yes);

								} else {
									log(LogStatus.FAIL,
											"------------Number of Records Not Matched in User Case, Expected: "
													+ expectedRecordsInUserCase + ", Actual: " + rowCountAfterFilter
													+ "----------------",
											YesNo.No);
									sa.assertTrue(false,
											"------------Number of Records Not Mathched in User Case, Expected: "
													+ expectedRecordsInUserCase + ", Actual: " + rowCountAfterFilter
													+ "----------------");

								}

							} else {
								log(LogStatus.ERROR,
										"---------Not Verified Filters Apply to SDG: " + TitleOfSDG + " ---------",
										YesNo.Yes);
								sa.assertTrue(false,
										"---------Not Verified Filters Apply to SDG: " + TitleOfSDG + " ---------");
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + TitleOfSDG, YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + TitleOfSDG);
						}

					} else {
						log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
						sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
					}

				} else {
					log(LogStatus.ERROR,
							"-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------", YesNo.Yes);
					sa.assertTrue(false,
							"-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

				}

			}

			else {
				log(LogStatus.ERROR,
						"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------",
						YesNo.No);
				sa.assertTrue(false,
						"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

			}
		} else {
			log(LogStatus.ERROR, "-----------Not Able to Logged in with Id: " + crmUser1EmailID + " -------------",
					YesNo.Yes);
			sa.assertTrue(false, "-----------Not Able to Logged in with Id: " + crmUser1EmailID + " -------------");

		}

		lp.CRMlogout();

		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc043_VerifyRememberfilterAndVerifyTheImpactOnSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);

		String[][] data = { M9_TC042_FilterMiscData.split("<break>") };

		String TitleOfSDG = M9_TC038_SDGName;

		String pageSize = "100";
		int expectedRecordsInUserCase = Integer.parseInt(M9_TC042_SDGNumberOfRecords);

		String rememberFilterCheckBoxLabel = "Remember Filter";
		boolean rememberFilterFlag = false;
		String FilterLabel = M9_TC042_FilterMiscData.split("<break>")[3];
		String FilterValue = M9_TC042_FilterMiscData.split("<break>")[2];
		String FilterInputBoxValue = M9_TC042_FilterMiscData.split("<break>")[0];

		List<String> expectedDefaultOptionSelected = Arrays.asList(FilterValue);

		String parentId;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");

				if (home.ClickOnOpenSDGRecord(TitleOfSDG)) {

					parentId = null;
					parentId = home.SwitchToSDGWindow(TitleOfSDG);
					if (parentId != null) {
						log(LogStatus.PASS, "-----------Switched to Open SDG Record Window of SDG: " + TitleOfSDG
								+ "--------------", YesNo.No);

						log(LogStatus.INFO, "Succesfully move to Tab : " + TabName.SDGTab, YesNo.No);

						if (sdg.editCheckBoxOfSDGAfterClickOnOpenSDGRecord(projectName, TitleOfSDG,
								Condition.SelectCheckbox, rememberFilterCheckBoxLabel, 30)) {
							log(LogStatus.PASS, "-----------Edit/Verify SDG: " + TitleOfSDG + "--------------",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentId);
							CommonLib.refresh(driver);
							CommonLib.ThreadSleep(8000);
							rememberFilterFlag = true;

						}

						else {
							log(LogStatus.FAIL,
									"-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------",
									YesNo.No);
							sa.assertTrue(false,
									"-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------");

						}
						// 2nd Part

						log(LogStatus.INFO, "---------Now Going to Verify Records After Filter Applied in SDG: "
								+ TitleOfSDG + " in case of User-----------", YesNo.Yes);
						if (rememberFilterFlag) {

							log(LogStatus.INFO, "------Remember Filter Applied Verified-------", YesNo.No);
							if (lp.CRMlogout()) {
								log(LogStatus.PASS, "-----------Successfully Logged Out--------------", YesNo.No);
								if (lp.CRMLogin(crmUser1EmailID, adminPassword, appName)) {
									log(LogStatus.PASS, "-----------Successfully Logged In with Id: " + crmUser1EmailID
											+ "--------------", YesNo.No);

									if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
										log(LogStatus.INFO,
												"Verified SDG Grid: " + TitleOfSDG
														+ " is Expanded By Default or Not, If not then Expand it ",
												YesNo.Yes);
										if (click(driver, home.gtFilterButton(TitleOfSDG, 20),
												"Filter Button on SDG: " + TitleOfSDG, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + TitleOfSDG,
													YesNo.No);

											if (home.sdgFilterSendDataAndDropDownHandle(TitleOfSDG, data)) {
												log(LogStatus.INFO, "---------Verified Filters Applied to SDG: "
														+ TitleOfSDG + " ---------", YesNo.No);
												CommonLib.ThreadSleep(8000);
												int rowCountAfterFilter = home.numberOfRecords(TitleOfSDG, pageSize);

												if (expectedRecordsInUserCase == rowCountAfterFilter) {
													log(LogStatus.INFO,
															"------------Number of Records Matched in User Case, Expected: "
																	+ expectedRecordsInUserCase + ", Actual: "
																	+ rowCountAfterFilter + "----------------",
															YesNo.Yes);

												} else {
													log(LogStatus.FAIL,
															"------------Number of Records Not Matched in User Case, Expected: "
																	+ expectedRecordsInUserCase + ", Actual: "
																	+ rowCountAfterFilter + "----------------",
															YesNo.No);
													sa.assertTrue(false,
															"------------Number of Records Not Mathched in User Case, Expected: "
																	+ expectedRecordsInUserCase + ", Actual: "
																	+ rowCountAfterFilter + "----------------");

												}

											} else {
												log(LogStatus.ERROR, "---------Not Verified Filters Apply to SDG: "
														+ TitleOfSDG + " ---------", YesNo.Yes);
												sa.assertTrue(false, "---------Not Verified Filters Apply to SDG: "
														+ TitleOfSDG + " ---------");
											}

										} else {
											log(LogStatus.ERROR,
													"Not able to click on Filter Button on SDG: " + TitleOfSDG,
													YesNo.Yes);
											sa.assertTrue(false,
													"Not able to click on Filter Button on SDG: " + TitleOfSDG);
										}

									} else {
										log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------",
												YesNo.No);
										sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
									}
								} else {
									log(LogStatus.ERROR, "-----------Not Able to Logged in with Id: " + crmUser1EmailID
											+ " -------------", YesNo.Yes);
									sa.assertTrue(false, "-----------Not Able to Logged in with Id: " + crmUser1EmailID
											+ " -------------");

								}
							} else {
								log(LogStatus.ERROR, "-----------Not Able to LogOut -------------", YesNo.Yes);
								sa.assertTrue(false, "-----------Not Able to LogOut -------------");

							}
						} else {
							log(LogStatus.ERROR,
									"------Remember Filter Applied Not Verified, So Not Able to Continue to verify Error Msg from User-------",
									YesNo.Yes);
							sa.assertTrue(false,
									"------Remember Filter Applied Not Verified, So Not Able to Continue to verify Error Msg from User-------");

						}

						// 3rd Part
						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(10000);
						log(LogStatus.INFO, "---------Now Going to Verify Default Filters in SDG: " + TitleOfSDG
								+ " in case of User-----------", YesNo.Yes);
						if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
							log(LogStatus.INFO, "Verified SDG Grid: " + TitleOfSDG
									+ " is Expanded By Default or Not, if not Then Expand ", YesNo.Yes);
							if (click(driver, home.gtFilterButton(TitleOfSDG, 20),
									"Filter Button on SDG: " + TitleOfSDG, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + TitleOfSDG, YesNo.No);

								if (home.VerifySDGFilterSelectDefaultSelectedValue(TitleOfSDG, FilterLabel,
										expectedDefaultOptionSelected)) {
									log(LogStatus.INFO, "--------" + FilterLabel + " Default Value: "
											+ expectedDefaultOptionSelected + " Matched--------", YesNo.No);
									String inputBoxValue = CommonLib.getAttribute(driver,
											home.inputBoxForSDGFilterName(FilterLabel, 20), "", "value");
									if (inputBoxValue.equals(FilterInputBoxValue)) {
										log(LogStatus.INFO,
												"------Verified: Input Box: " + FilterLabel
														+ " Default Value Matched, Expected: " + FilterInputBoxValue
														+ " , Actual:" + inputBoxValue + " --------",
												YesNo.No);
										CommonLib.ThreadSleep(2000);

									} else {
										log(LogStatus.ERROR,
												"------Input Box: " + FilterLabel
														+ " Default Value not Matched, Expected: " + FilterInputBoxValue
														+ " but Actual: " + inputBoxValue + "--------",
												YesNo.Yes);
										sa.assertTrue(false,
												"------Input Box: " + FilterLabel
														+ " Default Value not Matched, Expected: " + FilterInputBoxValue
														+ " but Actual: " + inputBoxValue + "--------");
									}
								} else {
									log(LogStatus.ERROR,
											"--------" + FilterLabel + " Default Value: "
													+ expectedDefaultOptionSelected + " Not Matched--------",
											YesNo.Yes);
									sa.assertTrue(false, "--------" + FilterLabel + " Default Value: "
											+ expectedDefaultOptionSelected + " Not Matched--------");
								}
							}

							else {
								log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + TitleOfSDG,
										YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + TitleOfSDG);
							}
						} else {
							log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
							sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
						}

					}

					else {
						log(LogStatus.FAIL, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
								+ TitleOfSDG + "--------------", YesNo.No);
						sa.assertTrue(false, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
								+ TitleOfSDG + "--------------");

					}

				} else {
					log(LogStatus.FAIL,
							"-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG + "--------------",
							YesNo.No);
					sa.assertTrue(false,
							"-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG + "--------------");

				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

		}

		else {
			log(LogStatus.ERROR,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------", YesNo.No);
			sa.assertTrue(false,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

		}

		lp.CRMlogout();

		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc044_AddSDGGridFirmWithPrimaryMemberOnInstitutionRecordPageAndVerifyRecords(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		FundsPageBusinessLayer funds = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);

		String viewList = M9_TC044_ListViewName;
		String fundNameToSelect = M9_TC044_FundName;
		String tabName = M9_TC044_TabName;
		String referencedTabName = M9_TC044_ReferencedTabName;
		String dropTabTo = M9_TC044_ReferencedTabName;
		String tabLabel = M9_TC044_TabLabel;
		List<String> columnInSDG = new ArrayList<String>();
		String[] columnVerifyLink = { "PRIMARY CONTACT" };
		columnInSDG = Arrays.asList(columnVerifyLink);

		String TitleOfSDG = M9_TC044_SDGName;
		String dataProviderNameAfterColon = M9_TC044_SDGDataProviderName;

		String[] fieldsInSDG = M9_TC044_SDGFieldData.split("<break>");

		List<String> columnsInSDG = Arrays.asList(fieldsInSDG);

		String columnData = M9_TC044_ContactLegalName;
		String columnDataCorrespondToFirst = M9_TC044_ContactName;

		boolean addSDGtoFundPage = false;
		String pageSize = "100";
		int expectedRecordsInAdminCase = Integer.parseInt(M9_TC044_SDGNumberOfRecords);

		if (lp.CRMLogin(superAdminUserName, adminPassword, appName)) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
				String xpath = "";
				if (click(driver, edit.getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
					WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
							action.SCROLLANDBOOLEAN, 10);
					ThreadSleep(3000);
					if (selectListView != null) {
						log(LogStatus.INFO, viewList + " List View already present", YesNo.No);
						if (click(driver, selectListView, "Select List Icon", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on " + viewList, YesNo.No);

							if (click(driver, funds.fundNameElement(fundNameToSelect, 30),
									"Fund Name: " + fundNameToSelect, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Fund Name: " + fundNameToSelect, YesNo.No);
								if (edit.tabNameElement(tabName, 10) == null) {
									log(LogStatus.INFO,
											"-------Tab Name: " + tabName
													+ " is not Present, So Continue to Add Tab and SDG----------",
											YesNo.No);

									if (edit.addTabAndSDGComponentIntoThatTab(projectName, "Navatar SDG", TitleOfSDG,
											dataProviderNameAfterColon, referencedTabName, tabLabel, tabName,
											dropTabTo)) {
										log(LogStatus.INFO,
												"--------Added SDG: " + TitleOfSDG + " to the Firm Page----------",
												YesNo.No);

										List<WebElement> columns = FindElements(driver, "//a[text()='" + TitleOfSDG
												+ "']/ancestor::article//thead//th[contains(@class,'navpeI')]//span",
												"Records");
										List<String> columnsText = new ArrayList<String>();
										for (WebElement column : columns) {
											columnsText.add(column.getText());
										}
										System.out.println(columnsText);
										if (CommonLib.compareList(columnsText, columnsInSDG)) {
											log(LogStatus.INFO, "------All Fields are Matched----------", YesNo.No);
										}

										else {
											log(LogStatus.ERROR, "-----------All Fields are not Matched------------",
													YesNo.No);
											sa.assertTrue(false, "-----------All Fields are not Matched------------");

										}

										int rowCountAfterFilter = home.numberOfRecords(TitleOfSDG, pageSize);

										if (expectedRecordsInAdminCase == rowCountAfterFilter) {
											log(LogStatus.INFO,
													"------------Number of Records Matched in Admin Case, Expected: "
															+ expectedRecordsInAdminCase + ", Actual: "
															+ rowCountAfterFilter + "----------------",
													YesNo.Yes);

										} else {
											log(LogStatus.FAIL,
													"------------Number of Records Not Matched in Admin Case, Expected: "
															+ expectedRecordsInAdminCase + ", Actual: "
															+ rowCountAfterFilter + "----------------",
													YesNo.No);
											sa.assertTrue(false,
													"------------Number of Records Not Matched in Admin Case, Expected: "
															+ expectedRecordsInAdminCase + ", Actual: "
															+ rowCountAfterFilter + "----------------");

										}

										if (home.verifyColumnRecordsRedirecting(SDGGridName.Firm_with_Primary_Member,
												columnInSDG)) {
											log(LogStatus.FAIL, columnInSDG + " Column contains the Redirect URL for : "
													+ TitleOfSDG, YesNo.Yes);
											sa.assertTrue(false, columnInSDG
													+ " Column contains the Redirect URL for : " + TitleOfSDG);

										} else {
											log(LogStatus.INFO,
													columnInSDG + " Not Contains the Redirect URL for : " + TitleOfSDG,
													YesNo.No);

										}

										if (home.verifyBlankDataCorrespondingToBlankData(TitleOfSDG, 3, 4)) {
											log(LogStatus.INFO,
													"Primary Member column will appear blank in case of no primary contact against that account",
													YesNo.Yes);

										} else {
											log(LogStatus.ERROR, columnInSDG
													+ "Not Verified: Primary Member column will appear blank in case of no primary contact against that account",
													YesNo.No);
											sa.assertTrue(false,
													"Not Verified: Primary Member column will appear blank in case of no primary contact against that account");
										}

									} else {
										log(LogStatus.ERROR, "---------Not Able to Added SDG: " + TitleOfSDG
												+ " to the Firm Page----------", YesNo.No);
										sa.assertTrue(false, "---------Not Able to Added SDG: " + TitleOfSDG
												+ " to the Firm Page----------");

									}
								} else {
									log(LogStatus.ERROR, "-------Tab Name: " + tabName
											+ " is already Present, So Not able to Continue to Add Tab and SDG----------",
											YesNo.No);
									sa.assertTrue(false, "-------Tab Name: " + tabName
											+ " is already Present, So Not able to Continue to Add Tab and SDG----------");

								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Fund Name: " + fundNameToSelect, YesNo.No);
								sa.assertTrue(false, "Not Able to Click on Fund Name: " + fundNameToSelect);

							}

						} else {
							log(LogStatus.ERROR, "Not Able to Click on " + viewList, YesNo.No);
							sa.assertTrue(false, "Not Able to Click on " + viewList);

						}

					} else {
						log(LogStatus.ERROR, viewList + " List View not already present", YesNo.No);
						sa.assertTrue(false, viewList + " List View not already present");

					}
				} else {
					log(LogStatus.ERROR, "list dropdown is not clickable, so cannot check presence of All List View",
							YesNo.Yes);
					sa.assertTrue(false, "list dropdown is not clickable, so cannot check presence of All List View");

				}
			}

			else {
				log(LogStatus.ERROR,
						"-----------Not Able to click on tab:" + TabName.Object1Tab.toString() + " --------------",
						YesNo.No);
				sa.assertTrue(false,
						"-----------Not Able to click on tab:" + TabName.Object1Tab.toString() + " --------------");

			}

			// 2nd Part

			log(LogStatus.INFO,
					"---------Now Going to Verify Contact Detail Page will Open in same tab in case of User-----------",
					YesNo.Yes);

			if (lp.CRMlogout()) {
				log(LogStatus.PASS, "-----------Successfully Logged Out--------------", YesNo.No);
				if (lp.CRMLogin(crmUser1EmailID, adminPassword, appName)) {
					log(LogStatus.PASS,
							"-----------Successfully Logged In with Id: " + crmUser1EmailID + "--------------",
							YesNo.No);

					if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
						String xpath = "";
						if (click(driver, edit.getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(3000);
							xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
							WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
									action.SCROLLANDBOOLEAN, 5);
							ThreadSleep(3000);
							if (selectListView != null) {
								log(LogStatus.INFO, viewList + " List View already present", YesNo.No);
								if (click(driver, selectListView, "Select List Icon", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on " + viewList, YesNo.No);

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(8000);
									if (click(driver, funds.fundNameElement(fundNameToSelect, 30),
											"Fund Name: " + fundNameToSelect, action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Fund Name: " + fundNameToSelect, YesNo.No);
										if (edit.tabNameElement(tabName, 10) != null) {
											log(LogStatus.INFO,
													"-------Tab Name: " + tabName + " is already Present----------",
													YesNo.No);

											if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
												log(LogStatus.INFO,
														"Verified SDG Grid: " + TitleOfSDG + " is Expanded By or Not ",
														YesNo.No);

												if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

													log(LogStatus.PASS, "-----------Page Size has selected to"
															+ pageSize + " --------------", YesNo.No);
													if (home.columnDataCorrespondToDataOfSDG(TitleOfSDG, columnData,
															columnDataCorrespondToFirst, 30) != null) {
														log(LogStatus.INFO, "Link Found: " + columnDataCorrespondToFirst
																+ " on SDG: " + TitleOfSDG, YesNo.No);
														if (click(driver,
																home.columnDataCorrespondToDataOfSDG(TitleOfSDG,
																		columnData, columnDataCorrespondToFirst, 30),
																columnDataCorrespondToFirst, action.SCROLLANDBOOLEAN)) {
															log(LogStatus.INFO,
																	"Clicked on " + columnDataCorrespondToFirst,
																	YesNo.No);

															if (contact.headerName(TitleOfSDG,
																	columnDataCorrespondToFirst, 30) != null) {
																log(LogStatus.INFO, "-------Header Verified: "
																		+ columnDataCorrespondToFirst + "---------",
																		YesNo.No);

															} else {
																log(LogStatus.ERROR, "--------Header Not Verified: "
																		+ columnDataCorrespondToFirst + "------------",
																		YesNo.Yes);
																sa.assertTrue(false, "--------Header Not Verified: "
																		+ columnDataCorrespondToFirst + "------------");

															}

														} else {
															log(LogStatus.ERROR, "Not able to Click on "
																	+ columnDataCorrespondToFirst, YesNo.Yes);
															sa.assertTrue(false, "Not able to Click on "
																	+ columnDataCorrespondToFirst);

														}
													} else {
														log(LogStatus.ERROR,
																"Link not Found: " + columnDataCorrespondToFirst
																		+ " on SDG: " + TitleOfSDG,
																YesNo.Yes);
														sa.assertTrue(false,
																"Link not Found: " + columnDataCorrespondToFirst
																		+ " on SDG: " + TitleOfSDG);

													}
												} else {
													log(LogStatus.FAIL, "-----------Not able to Select Page Size: "
															+ pageSize + "--------------", YesNo.No);
													sa.assertTrue(false, "-----------Not able to Select Page Size: "
															+ pageSize + " --------------");
												}

											} else {
												log(LogStatus.FAIL,
														"-----------Not able to Expand SDG Grid --------------",
														YesNo.No);
												sa.assertTrue(false,
														"-----------Not able to Expand SDG Grid --------------");
											}

										} else {
											log(LogStatus.ERROR,
													"-------Tab Name: " + tabName
															+ " is not Present, So Not able to Continue----------",
													YesNo.No);
											sa.assertTrue(false, "-------Tab Name: " + tabName
													+ " is not Present, So Not able to Continue----------");

										}

									} else {
										log(LogStatus.ERROR, "Not Able to Click on Fund Name: " + fundNameToSelect,
												YesNo.No);
										sa.assertTrue(false, "Not Able to Click on Fund Name: " + fundNameToSelect);

									}

								} else {
									log(LogStatus.ERROR, "Not Able to Click on " + viewList, YesNo.No);
									sa.assertTrue(false, "Not Able to Click on " + viewList);

								}

							} else {
								log(LogStatus.ERROR, viewList + " List View not already present", YesNo.No);
								sa.assertTrue(false, viewList + " List View not already present");

							}
						} else {
							log(LogStatus.ERROR,
									"list dropdown is not clickable, so cannot check presence of All List View",
									YesNo.Yes);
							sa.assertTrue(false,
									"list dropdown is not clickable, so cannot check presence of All List View");

						}
					}

					else {
						log(LogStatus.ERROR, "-----------Not Able to click on tab:" + TabName.Object1Tab.toString()
								+ " --------------", YesNo.No);
						sa.assertTrue(false, "-----------Not Able to click on tab:" + TabName.Object1Tab.toString()
								+ " --------------");

					}
				} else {
					log(LogStatus.ERROR,
							"-----------Not Able to Logged in with Id: " + crmUser1EmailID + " -------------",
							YesNo.Yes);
					sa.assertTrue(false,
							"-----------Not Able to Logged in with Id: " + crmUser1EmailID + " -------------");

				}
			} else {
				log(LogStatus.ERROR, "-----------Not Able to LogOut -------------", YesNo.Yes);
				sa.assertTrue(false, "-----------Not Able to LogOut -------------");

			}

		} else {
			log(LogStatus.ERROR, "-----------Not Able to logged in to the App--------------", YesNo.No);
			sa.assertTrue(false, "-----------Not Able to logged in to the App--------------");

		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc045_AddCustomFieldsInFundFirstSDGGridSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);

		String[][] sdgLabels = { { SDGCreationLabel.Override_Label.toString(), M9_TC045_SDGFieldData } };

		String fieldToSelect = M9_TC045_SDGFieldData;
		String TitleOfSDG = M9_TC003_SDGName;
		List<String> FundFistSDGList = new ArrayList<String>();

		String parentId;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");

				if (home.ClickOnOpenSDGRecord(TitleOfSDG)) {

					parentId = null;
					parentId = home.SwitchToSDGWindow(TitleOfSDG);
					if (parentId != null) {
						log(LogStatus.PASS, "-----------Switched to Open SDG Record Window of SDG: " + TitleOfSDG
								+ "--------------", YesNo.No);

						log(LogStatus.INFO, "Succesfully move to Tab : " + TabName.SDGTab, YesNo.No);

						if (CommonLib.selectVisibleTextFromDropDown(driver, sdg.sdgFieldSelectElement(40),
								"Quick Field Finder", fieldToSelect)) {
							log(LogStatus.PASS, "-----------Edit/Verify SDG: " + TitleOfSDG + "--------------",
									YesNo.No);

							if (click(driver, sdg.getAddFieldButton(projectName, 30), "Add Field Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Add Field Button", YesNo.No);

								if (sdg.editCustomSDGAfterClickOnEdiButton(projectName, TitleOfSDG, sdgLabels,
										action.SCROLLANDBOOLEAN, 40)) {
									log(LogStatus.INFO, "Field: " + fieldToSelect + " has been added", YesNo.No);
									driver.close();
									driver.switchTo().window(parentId);
									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(8000);

									if (lp.clickOnTab(projectName, TabName.HomeTab)) {
										log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);

										if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
											log(LogStatus.INFO,
													"Verified SDG Grid: " + TitleOfSDG
															+ " is Expanded By Default or Not, if not Then Expand ",
													YesNo.Yes);

											for (WebElement li : home.getFundFirstSDGColumns(TitleOfSDG, 30))

											{
												FundFistSDGList.add(CommonLib.getText(driver, li,
														"Columns of SDG: " + TitleOfSDG, action.SCROLLANDBOOLEAN));

											}
											if (FundFistSDGList.size() != 0) {
												appLog.info(
														"Columns Present there are more than 0, So continue the process");

												if (FundFistSDGList.contains(fieldToSelect)) {
													appLog.info("-------Verified: Field: " + fieldToSelect
															+ " is added on SDG Grid: " + TitleOfSDG + "-------");

												} else {
													log(LogStatus.FAIL, "-------Not Verified: Field: " + fieldToSelect
															+ " is added on SDG Grid: " + TitleOfSDG + "-------",
															YesNo.No);
													sa.assertTrue(false, "-------Not Verified: Field: " + fieldToSelect
															+ " is added on SDG Grid: " + TitleOfSDG + "-------");
												}

											} else {
												log(LogStatus.FAIL, "-----------No Columns Present there in SDG Grid:"
														+ TitleOfSDG + "--------------", YesNo.No);
												sa.assertTrue(false, "-----------No Columns Present there in SDG Grid:"
														+ TitleOfSDG + "--------------");
											}
										} else {
											log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------",
													YesNo.No);
											sa.assertTrue(false,
													"-----------Not able to Expand SDG Grid --------------");
										}

									}

									else {
										log(LogStatus.ERROR, "-----------Not Able to click on tab:"
												+ TabName.HomeTab.toString() + " --------------", YesNo.No);
										sa.assertTrue(false, "-----------Not Able to click on tab:"
												+ TabName.HomeTab.toString() + " --------------");

									}

								} else {

									log(LogStatus.ERROR, "Not able to Add Field: " + fieldToSelect, YesNo.Yes);
									sa.assertTrue(false, "Not able to Add Field: " + fieldToSelect);
									driver.close();
									driver.switchTo().window(parentId);
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on Add Field Button", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Add Field Button");
							}

						}

						else {
							log(LogStatus.FAIL,
									"-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------",
									YesNo.No);
							sa.assertTrue(false,
									"-----------Not Able to Edit/Verify SDG: " + TitleOfSDG + "--------------");

						}

					}

					else {
						log(LogStatus.FAIL, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
								+ TitleOfSDG + "--------------", YesNo.No);
						sa.assertTrue(false, "-----------Not Able to Switched to Window Open SDG Record of SDG: "
								+ TitleOfSDG + "--------------");

					}

				} else {
					log(LogStatus.FAIL,
							"-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG + "--------------",
							YesNo.No);
					sa.assertTrue(false,
							"-----------Not Able to Click on Open SDG Record of SDG: " + TitleOfSDG + "--------------");

				}

			} else {
				log(LogStatus.ERROR, "-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------",
						YesNo.Yes);
				sa.assertTrue(false, "-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

			}

		}

		else {
			log(LogStatus.ERROR,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------", YesNo.No);
			sa.assertTrue(false,
					"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

		}

		lp.CRMlogout();

		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc046_VerifyImportExportInAccountSDG(String projectName)
			throws UnsupportedFlavorException, IOException {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		EditPageBusinessLayer EP = new EditPageBusinessLayer(driver);
		HomePage hp = new HomePage(driver);
		String TitleOfSDG = M9_TC003_SDGName;

		List<String> FundFistSDGList = new ArrayList<String>();
		List<String> ClonnedSDGList = new ArrayList<String>();

		String replaceText = "";
		String parentWindowID;
		String tablename = M9_TC046_SDGName;
		String dataProviderName = M9_TC046_SDGDataProviderName;
		String referencedComponentHeading = M9_TC031_1_ReferencedComponentHeading;

		if (lp.CRMLogin(superAdminUserName, adminPassword, appName)) {
			log(LogStatus.PASS, "-----------Successfully Logged In with Id: " + superAdminUserName + "--------------",
					YesNo.No);
			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Clicked on Tab : " + TabName.HomeTab, YesNo.No);
				WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
						"Component Title ", action.SCROLLANDBOOLEAN, 10);

				if (alreadyAddedComponentToHomePage != null) {
					log(LogStatus.INFO,
							"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
							YesNo.Yes);
					if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
						log(LogStatus.INFO, "Verified SDG Grid: " + TitleOfSDG
								+ " is Expanded By Default or Not, if not Then Expand ", YesNo.Yes);

						for (WebElement li : hp.getFundFirstSDGColumns(TitleOfSDG, 30))

						{
							FundFistSDGList.add(CommonLib
									.getText(driver, li, "Columns of SDG: " + TitleOfSDG, action.SCROLLANDBOOLEAN)
									.toLowerCase());

						}
						if (FundFistSDGList.size() != 0) {
							appLog.info("Columns Present there are more than 0, So continue the process");
							appLog.info("BeforeSort " + FundFistSDGList);
							Collections.sort(FundFistSDGList);
							appLog.info("After Sort" + FundFistSDGList);

							if (click(driver, hp.getFundFirstSDG_Setup(30, TitleOfSDG), "Fund First SDG Grid Setup",
									action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on Fund First Grid Setup");
								parentWindowID = switchOnWindow(driver);
								if (click(driver, hp.getFundFirstSDG_ExportWizard(30),
										"Fund First SDG Grid Export Wizard", action.SCROLLANDBOOLEAN)) {
									appLog.info("Clicked on Fund First Grid Export Wizard");

									if (click(driver, hp.getFundFirstSDG_ExportTextArea(30),
											"Fund First SDG Grid Text Area", action.SCROLLANDBOOLEAN)) {
										appLog.info("Clicked on Fund First Grid Text Area");
										CommonLib.ThreadSleep(4000);
										String jsonText = CommonLib.getAttribute(driver,
												hp.getFundFirstSDG_ExportTextArea(30), "Export Text Area", "value");

										if (jsonText != null) {

											appLog.info("---Text Present in Text Area of Export Wizard---");
											CommonLib.ThreadSleep(4000);
											appLog.info("Text Available is: " + jsonText);

											replaceText = jsonText.replace(TitleOfSDG, tablename);

											appLog.info("Replaced Text is: " + replaceText);

											if (replaceText.contains(tablename)) {
												appLog.info("Text: " + TitleOfSDG + " is replaced by " + tablename);
												if (click(driver, hp.getFundFirstSDG_ImportTab(30), "Import Tab",
														action.SCROLLANDBOOLEAN)) {
													appLog.info("Clicked on Import Tab");
													CommonLib.ThreadSleep(2000);

													if (click(driver, hp.getFundFirstSDG_SelectImportTextArea(30),
															"Import Text Area", action.SCROLLANDBOOLEAN)) {
														appLog.info("Clicked on Import Text Area");

														if (CommonLib.sendKeys(driver,
																hp.getFundFirstSDG_SelectImportTextArea(30),
																replaceText, "Import Text Area",
																action.SCROLLANDBOOLEAN)) {

															appLog.info(
																	"Text pasted in Import Text Area: " + replaceText);

															if (clickUsingJavaScript(driver,
																	hp.getFundFirstSDG_SelectImportButton(30),
																	"Import Button", action.SCROLLANDBOOLEAN)) {
																appLog.info("Clicked on Import Button");
																if (hp.getVerifyClonnedSDG(30) != null) {
																	appLog.info("------Verified: " + tablename
																			+ " SDG Grid gets Created-------");
																	CommonLib.refresh(driver);

																	if (click(driver, hp.getClonnedSDGRelatedTab(40),
																			"Related Tab", action.SCROLLANDBOOLEAN)) {
																		appLog.info("Clicked on " + tablename
																				+ " Related Tab");
																		if (clickUsingJavaScript(driver,
																				hp.getClonnedSDGViewAllClick(30),
																				tablename + " View All",
																				action.SCROLLANDBOOLEAN)) {
																			appLog.info(
																					tablename + " View All Clicked");
																			CommonLib.ThreadSleep(5000);

																			int index = hp.getClonnedSDGColumns(30)
																					.size() - 1;

																			WebElement Ele = hp.getClonnedSDGColumns(30)
																					.get(index);
																			((JavascriptExecutor) driver).executeScript(
																					"arguments[0].scrollIntoView(true);",
																					Ele);
																			CommonLib.ThreadSleep(8000);

																			for (WebElement li : hp
																					.getClonnedSDGColumns(30))

																			{

																				ClonnedSDGList.add(CommonLib
																						.getText(driver, li, tablename,
																								action.SCROLLANDBOOLEAN)
																						.toLowerCase());

																			}
																			appLog.info("BeforeSort " + ClonnedSDGList);

																			Collections.sort(ClonnedSDGList);
																			appLog.info("After Sort" + ClonnedSDGList);

																			if (FundFistSDGList
																					.equals(ClonnedSDGList)) {
																				appLog.info("---------Columns of SDG: "
																						+ tablename + " & " + TitleOfSDG
																						+ " gets matched---------");
																				CommonLib.ThreadSleep(5000);
																				driver.close();
																				driver.switchTo()
																						.window(parentWindowID);
																				if (clickUsingJavaScript(driver,
																						hp.getHomeTab(30), "Home Tab",
																						action.SCROLLANDBOOLEAN)) {
																					appLog.info(
																							"Could click on Home Tab");
																					if (EP.addSDGComponentToRefrencedComponent(
																							projectName, "Navatar SDG",
																							tablename, dataProviderName,
																							referencedComponentHeading)) {
																						appLog.info("--------SDG: "
																								+ tablename
																								+ " Gets Added on Home Page---------");
																						CommonLib.ThreadSleep(5000);
																					} else {
																						log(LogStatus.ERROR,
																								"--------SDG: "
																										+ tablename
																										+ " Not Gets Added on Home Page---------",
																								YesNo.Yes);
																						sa.assertTrue(false,
																								"--------SDG: "
																										+ tablename
																										+ " Not Gets Added on Home Page---------");
																					}
																				} else

																				{
																					appLog.error(
																							"Could Not click on Home Page");
																				}
																			} else {
																				appLog.error("---------Columns of SDG: "
																						+ tablename + " & " + TitleOfSDG
																						+ " gets matched---------");
																				sa.assertTrue(false,
																						"---------Columns of SDG: "
																								+ tablename + " & "
																								+ TitleOfSDG
																								+ " gets matched---------");
																			}

																		} else {
																			appLog.error(tablename
																					+ " View All not Clicked");
																			sa.assertTrue(false, tablename
																					+ " View All not Clicked");
																		}

																	} else {
																		appLog.error(tablename
																				+ " Related Tab  Not Clicked");
																		sa.assertTrue(false,
																				tablename + " Related Not Tab Clicked");
																	}
																} else {
																	appLog.error("--------Not Verified: " + tablename
																			+ " SDG Grid gets Created---------");
																	sa.assertTrue(false, "--------Not Verified: "
																			+ tablename
																			+ " SDG Grid gets Created---------");
																}

															} else {

																appLog.error("Unable to click on Import Button");
																sa.assertTrue(false,
																		"Unable to click on Import Button");
															}

														} else {

															appLog.error("Unable To paste the text: " + replaceText);
															sa.assertTrue(false,
																	"Unable To paste the text: " + replaceText);

														}
													} else {

														appLog.error("Unable to Click on Import Text Area");
														sa.assertTrue(false, "Unable to Click on Import Text Area");
													}

												} else {

													appLog.error("Unable To Click On Import Tab");
													sa.assertTrue(false, "Unable To Click On Import Tab");
												}
											} else {

												appLog.error("----Text: " + TitleOfSDG + " is not replaced by "
														+ tablename + "----");
												sa.assertTrue(false, "----Text: " + TitleOfSDG + " is not replaced by "
														+ tablename + "----");
											}

										} else {

											appLog.error("-----No Text Available in Text Area of Export wizard-----");
											sa.assertTrue(false,
													"-----No Text Available in Text Area of Export wizard-----");

										}

									} else {

										appLog.error("Not able to click on Fund First Grid Text Area");
										sa.assertTrue(false, "Not able to click on Fund First Grid Text Area");
									}
								} else {

									appLog.error("Not able to click on Fund First Grid Export Wizard");
									sa.assertTrue(false, "Not able to click on Fund First Grid Export Wizard");
								}

							} else {

								appLog.error("Not able to click on Fund First Grid Setup");
								sa.assertTrue(false, "Not able to click on Fund First Grid Setup");
							}
						} else {
							log(LogStatus.FAIL,
									"-----------No Columns Present there in SDG Grid:" + TitleOfSDG + "--------------",
									YesNo.No);
							sa.assertTrue(false,
									"-----------No Columns Present there in SDG Grid:" + TitleOfSDG + "--------------");
						}
					} else {
						log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
						sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
					}

				} else {
					log(LogStatus.ERROR,
							"-----------Component Not Added to Home Page: " + TitleOfSDG + " -------------", YesNo.Yes);
					sa.assertTrue(false,
							"-----------Component Not Added to Home Page: " + TitleOfSDG + " ------------");

				}

			}

			else {
				log(LogStatus.ERROR,
						"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------",
						YesNo.No);
				sa.assertTrue(false,
						"-----------Not Able to click on tab:" + TabName.HomeTab.toString() + " --------------");

			}
		} else {
			log(LogStatus.ERROR, "-----------Not Able to Logged in with Id: " + superAdminUserName + " -------------",
					YesNo.Yes);
			sa.assertTrue(false, "-----------Not Able to Logged in with Id: " + superAdminUserName + " -------------");

		}

		sa.assertAll();
		lp.CRMlogout();

	}

	@Parameters({ "projectName" })
	@Test
	public void M9Tc047_VerifyEnd2EndFuntionalityOnNewClonnedSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		boolean defaultSorting = false;

		String[][] data = { M9_TC047_FilterMiscData.split("<break>") };
		String[] numberOfRecords = M9_TC047_SDGNumberOfRecords.split("<break>");
		int expectedRecordsInUserCaseAfterSumoFilterApplied = Integer.parseInt(numberOfRecords[0]);
		String TitleOfSDG = M9_TC046_SDGName;
		String pageSize = "100";
		int expectedDefaultRecordsInUserCase = Integer.parseInt(numberOfRecords[1]);
		String[] fieldsInSDG = { M9_TC005_SDGField1, M9_TC005_SDGField2, M9_TC005_SDGField3, M9_TC005_SDGField4,
				M9_TC005_SDGField5, M9_TC005_SDGField6, M9_TC005_SDGField7 };
		String[] datefieldsInSDG = { M9_TC005_SDGField5 };

		List<String> columnInSDG = Arrays.asList(fieldsInSDG);
		List<String> dateColumnInSDG = Arrays.asList(datefieldsInSDG);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
			log(LogStatus.INFO,
					"Verified SDG Grid: " + TitleOfSDG + " is Expanded By Default or Not, If not then Expand it ",
					YesNo.Yes);

			int rowCount = home.numberOfRecords(TitleOfSDG, pageSize);

			if (expectedDefaultRecordsInUserCase == rowCount) {
				log(LogStatus.INFO,
						"------------Number of Records Matched in User Case, Expected: "
								+ expectedDefaultRecordsInUserCase + ", Actual: " + rowCount + "----------------",
						YesNo.Yes);

			} else {
				log(LogStatus.FAIL,
						"------------Number of Records Not Matched in User Case, Expected: "
								+ expectedDefaultRecordsInUserCase + ", Actual: " + rowCount + "----------------",
						YesNo.No);
				sa.assertTrue(false, "------------Number of Records Not Mathched in User Case, Expected: "
						+ expectedDefaultRecordsInUserCase + ", Actual: " + rowCount + "----------------");

			}

		} else {
			log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
			sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
		}

		// 2nd Part
		CommonLib.refresh(driver);
		CommonLib.ThreadSleep(10000);
		log(LogStatus.INFO, "---------Now Going to Verify Default Sorting & Also SOrting For other Columns in SDG: "
				+ TitleOfSDG + " in case of User-----------", YesNo.Yes);
		if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {

			if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

				log(LogStatus.PASS, "-----------Page Size has selected to" + pageSize + " --------------", YesNo.No);

				if (CommonLib.checkSorting(driver, SortOrder.Decending, home.columnData(TitleOfSDG, 2))) {
					log(LogStatus.PASS, "-----------Fund Name Column is in Descending Order By Default --------------",
							YesNo.No);
					sa.assertTrue(true, "-----------Fund Name Column is in Descending Order By Default --------------");
					defaultSorting = true;

				}

				else {
					log(LogStatus.FAIL, "-----------Fund Name Column not in Descending Order By Default --------------",
							YesNo.No);
					sa.assertTrue(false,
							"-----------Fund Name Column not in Descending Order By Default --------------");

				}
			} else {
				log(LogStatus.FAIL, "-----------Not able to Select Page Size: " + pageSize + "--------------",
						YesNo.No);
				sa.assertTrue(false, "-----------Not able to Select Page Size: " + pageSize + " --------------");
			}

		} else {
			log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
			sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
		}

		// 3rd Part
		if (defaultSorting) {
			log(LogStatus.PASS,
					"-----------Fund Name Column is in Descending Order By Default, Now Continue to Check Sorting of Other Columns --------------",
					YesNo.No);
			if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {

				if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

					log(LogStatus.PASS, "-----------Page Size has selected to" + pageSize + " --------------",
							YesNo.No);
					CommonLib.ThreadSleep(25000);
					home.verifyColumnAscendingDescendingOrder(SDGGridName.Clonned_SDG, columnInSDG, dateColumnInSDG);
				} else {
					log(LogStatus.FAIL, "-----------Not able to Select Page Size: " + pageSize + "--------------",
							YesNo.No);
					sa.assertTrue(false, "-----------Not able to Select Page Size: " + pageSize + " --------------");
				}

			} else {
				log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
				sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
			}

		}

		else

		{
			log(LogStatus.FAIL,
					"-----------Fund Name Column not in Descending Order By Default, SO No Able to Continue to Check Sorting of Other Columns --------------",
					YesNo.No);
			sa.assertTrue(false,
					"-----------Fund Name Column not in Descending Order By Default, SO No Able to Continue to Check Sorting of Other Columns --------------");
		}

		// 4th Part

		log(LogStatus.INFO, "---------Now Going to Verify Records After Filter Applied in SDG: " + TitleOfSDG
				+ " in case of User-----------", YesNo.Yes);

		if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
			log(LogStatus.INFO,
					"Verified SDG Grid: " + TitleOfSDG + " is Expanded By Default or Not, If not then Expand it ",
					YesNo.Yes);
			if (click(driver, home.gtFilterButton(TitleOfSDG, 20), "Filter Button on SDG: " + TitleOfSDG,
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + TitleOfSDG, YesNo.No);

				if (home.sdgFilterSendDataAndDropDownHandle(TitleOfSDG, data)) {
					log(LogStatus.INFO, "---------Verified Filters Applied to SDG: " + TitleOfSDG + " ---------",
							YesNo.No);
					CommonLib.ThreadSleep(8000);
					home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG);
					int rowCountAfterFilter = home.numberOfRecords(TitleOfSDG, pageSize);

					if (expectedRecordsInUserCaseAfterSumoFilterApplied == rowCountAfterFilter) {
						log(LogStatus.INFO,
								"------------Number of Records Matched in User Case, Expected: "
										+ expectedRecordsInUserCaseAfterSumoFilterApplied + ", Actual: "
										+ rowCountAfterFilter + "----------------",
								YesNo.Yes);

					} else {
						log(LogStatus.FAIL,
								"------------Number of Records Not Matched in User Case, Expected: "
										+ expectedRecordsInUserCaseAfterSumoFilterApplied + ", Actual: "
										+ rowCountAfterFilter + "----------------",
								YesNo.No);
						sa.assertTrue(false,
								"------------Number of Records Not Mathched in User Case, Expected: "
										+ expectedRecordsInUserCaseAfterSumoFilterApplied + ", Actual: "
										+ rowCountAfterFilter + "----------------");

					}

				} else {
					log(LogStatus.ERROR, "---------Not Verified Filters Apply to SDG: " + TitleOfSDG + " ---------",
							YesNo.Yes);
					sa.assertTrue(false, "---------Not Verified Filters Apply to SDG: " + TitleOfSDG + " ---------");
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + TitleOfSDG, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + TitleOfSDG);
			}

		} else {
			log(LogStatus.FAIL, "-----------Not able to Expand SDG Grid --------------", YesNo.No);
			sa.assertTrue(false, "-----------Not able to Expand SDG Grid --------------");
		}

		lp.CRMlogout();

		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc048_AddSDGToFundPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		FundsPageBusinessLayer funds = new FundsPageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);

		String viewList = M9_TC048_ListViewName;
		String fundNaeToSelect = M9_TC048_FundName;
		String TitleOfSDG = M9_TC048_SDGName;
		String dataProviderName = M9_TC048_SDGDataProviderName;
		boolean addSDGtoFundPage = false;
		String pageSize = "100";
		int expectedRecordsInUserCase = Integer.parseInt(M9_TC048_SDGNumberOfRecords);

		if (lp.CRMLogin(superAdminUserName, adminPassword, appName)) {
			if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object3Tab, YesNo.No);
				String xpath = "";
				if (click(driver, edit.getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
					WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
							action.SCROLLANDBOOLEAN, 5);
					ThreadSleep(3000);
					if (selectListView != null) {
						log(LogStatus.INFO, "All List View already present", YesNo.No);
						if (click(driver, selectListView, "Select List Icon", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on " + viewList, YesNo.No);

							if (click(driver, funds.fundNameElement(fundNaeToSelect, 30),
									"Fund Name: " + fundNaeToSelect, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Fund Name: " + fundNaeToSelect, YesNo.No);

								if (edit.editPageAndAddSDG(projectName, TitleOfSDG, dataProviderName)) {
									log(LogStatus.INFO,
											"--------Added SDG: " + TitleOfSDG + " to the Fund Page----------",
											YesNo.No);

									addSDGtoFundPage = true;

								} else {
									log(LogStatus.ERROR, "---------Not Able to Added SDG: " + TitleOfSDG
											+ " to the Fund Page----------", YesNo.No);
									sa.assertTrue(false, "---------Not Able to Added SDG: " + TitleOfSDG
											+ " to the Fund Page----------");

								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Fund Name: " + fundNaeToSelect, YesNo.No);
								sa.assertTrue(false, "Not Able to Click on Fund Name: " + fundNaeToSelect);

							}

						} else {
							log(LogStatus.ERROR, "Not Able to Click on " + viewList, YesNo.No);
							sa.assertTrue(false, "Not Able to Click on " + viewList);

						}

					} else {
						log(LogStatus.ERROR, "All List View not already present", YesNo.No);
						sa.assertTrue(false, "All List View not already present");

					}
				} else {
					log(LogStatus.ERROR, "list dropdown is not clickable, so cannot check presence of All List View",
							YesNo.Yes);
					sa.assertTrue(false, "list dropdown is not clickable, so cannot check presence of All List View");

				}
			}

			else {
				log(LogStatus.ERROR,
						"-----------Not Able to click on tab:" + TabName.Object3Tab.toString() + " --------------",
						YesNo.No);
				sa.assertTrue(false,
						"-----------Not Able to click on tab:" + TabName.Object3Tab.toString() + " --------------");

			}

			// 2nd Part

			log(LogStatus.INFO,
					"---------Now Going to Verify Records in SDG: " + TitleOfSDG + " in case of User-----------",
					YesNo.Yes);
			if (addSDGtoFundPage) {

				log(LogStatus.INFO, "------SDG: " + TitleOfSDG + " added to Fund Page-------", YesNo.No);
				if (lp.CRMlogout()) {
					log(LogStatus.PASS, "-----------Successfully Logged Out--------------", YesNo.No);
					if (lp.CRMLogin(crmUser1EmailID, adminPassword, appName)) {
						log(LogStatus.PASS,
								"-----------Successfully Logged In with Id: " + crmUser1EmailID + "--------------",
								YesNo.No);

						if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
							log(LogStatus.INFO, "Click on Tab : " + TabName.Object3Tab, YesNo.No);
							String xpath = "";
							if (click(driver, edit.getSelectListIcon(60), "Select List Icon",
									action.SCROLLANDBOOLEAN)) {
								ThreadSleep(3000);
								xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
								WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
										action.SCROLLANDBOOLEAN, 5);
								ThreadSleep(3000);
								if (selectListView != null) {
									log(LogStatus.INFO, "All List View already present", YesNo.No);
									if (click(driver, selectListView, "Select List Icon", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on " + viewList, YesNo.No);

										if (click(driver, funds.fundNameElement(fundNaeToSelect, 30),
												"Fund Name: " + fundNaeToSelect, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Fund Name: " + fundNaeToSelect, YesNo.No);

											WebElement alreadyAddedComponentToFundPage = FindElement(driver,
													"//a[text()='" + TitleOfSDG + "']", "Component Title ",
													action.SCROLLANDBOOLEAN, 10);

											if (alreadyAddedComponentToFundPage != null) {
												log(LogStatus.INFO, "------------Component Already Added to Fund Page "
														+ TitleOfSDG + "----------------", YesNo.Yes);
												sa.assertTrue(true, "------------Component Already Added to Fund Page "
														+ TitleOfSDG + "---------------");

												if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {
													log(LogStatus.INFO, "Verified SDG Grid: " + TitleOfSDG
															+ " is Expanded By Default or Not, If not then Expand it ",
															YesNo.Yes);

													int rowCountAfterFilter = home.numberOfRecords(TitleOfSDG,
															pageSize);

													if (expectedRecordsInUserCase == rowCountAfterFilter) {
														log(LogStatus.INFO,
																"------------Number of Records Matched in User Case, Expected: "
																		+ expectedRecordsInUserCase + ", Actual: "
																		+ rowCountAfterFilter + "----------------",
																YesNo.Yes);

													} else {
														log(LogStatus.FAIL,
																"------------Number of Records Not Matched in User Case, Expected: "
																		+ expectedRecordsInUserCase + ", Actual: "
																		+ rowCountAfterFilter + "----------------",
																YesNo.No);
														sa.assertTrue(false,
																"------------Number of Records Not Mathched in User Case, Expected: "
																		+ expectedRecordsInUserCase + ", Actual: "
																		+ rowCountAfterFilter + "----------------");

													}

												} else {
													log(LogStatus.FAIL,
															"-----------Not able to Expand SDG Grid --------------",
															YesNo.No);
													sa.assertTrue(false,
															"-----------Not able to Expand SDG Grid --------------");
												}
											} else {
												log(LogStatus.ERROR, "-----------Component Not Added to Fund Page: "
														+ TitleOfSDG + " -------------", YesNo.Yes);
												sa.assertTrue(false, "-----------Component Not Added to Fund Page: "
														+ TitleOfSDG + " ------------");

											}

										} else {
											log(LogStatus.ERROR, "Not Able to Click on Fund Name: " + fundNaeToSelect,
													YesNo.No);
											sa.assertTrue(false, "Not Able to Click on Fund Name: " + fundNaeToSelect);

										}

									} else {
										log(LogStatus.ERROR, "Not Able to Click on " + viewList, YesNo.No);
										sa.assertTrue(false, "Not Able to Click on " + viewList);

									}

								} else {
									log(LogStatus.ERROR, "All List View not already present", YesNo.No);
									sa.assertTrue(false, "All List View not already present");

								}
							} else {
								log(LogStatus.ERROR,
										"list dropdown is not clickable, so cannot check presence of All List View",
										YesNo.Yes);
								sa.assertTrue(false,
										"list dropdown is not clickable, so cannot check presence of All List View");

							}
						}

						else {
							log(LogStatus.ERROR, "-----------Not Able to click on tab:" + TabName.Object3Tab.toString()
									+ " --------------", YesNo.No);
							sa.assertTrue(false, "-----------Not Able to click on tab:" + TabName.Object3Tab.toString()
									+ " --------------");

						}
					} else {
						log(LogStatus.ERROR,
								"-----------Not Able to Logged in with Id: " + crmUser1EmailID + " -------------",
								YesNo.Yes);
						sa.assertTrue(false,
								"-----------Not Able to Logged in with Id: " + crmUser1EmailID + " -------------");

					}
				} else {
					log(LogStatus.ERROR, "-----------Not Able to LogOut -------------", YesNo.Yes);
					sa.assertTrue(false, "-----------Not Able to LogOut -------------");

				}
			} else {
				log(LogStatus.ERROR, "------SDG: " + TitleOfSDG + " Not added to Fund Page-------", YesNo.Yes);
				sa.assertTrue(false, "------SDG: " + TitleOfSDG + " Not added to Fund Page-------");

			}

		} else {
			log(LogStatus.ERROR, "-----------Not Able to logged in to the App--------------", YesNo.No);
			sa.assertTrue(false, "-----------Not Able to logged in to the App--------------");

		}

		lp.CRMlogout();
		sa.assertAll();
	}

	/*
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc049_CreateAppPageAndAddSDG(String projectName) {
	 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); String labelName =
	 * M9Tc049_AppPageName; String tableName = M9Tc049_SDGTableName; String
	 * dataProviderName = M9Tc049_SDGDataProvider;
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (home.clickOnSetUpLink()) {
	 * 
	 * String parentWindowID = switchOnWindow(driver); if (parentWindowID == null) {
	 * sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); }
	 * 
	 * if (setup.searchStandardOrCustomObject(projectName, mode,
	 * object.Lightning_App_Builder)) { if (AppBuilder.CreateAppPage(projectName,
	 * mode, labelName, tableName, dataProviderName, parentWindowID)) {
	 * log(LogStatus.PASS, labelName + " App Page has been Created", YesNo.Yes);
	 * sa.assertTrue(true, labelName + " App Page has been Created"); } else {
	 * log(LogStatus.ERROR, labelName + " App Page is not created", YesNo.Yes);
	 * sa.assertTrue(false, labelName + " App Page is not created"); } } else {
	 * log(LogStatus.ERROR, "Not able to search the Object", YesNo.Yes);
	 * sa.assertTrue(false, "Not able to search the Object"); }
	 * 
	 * } lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc050_VerifySDGDataOnAppPage(String projectName) {
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); String appPage =
	 * M9Tc050_AppPageName; String tableName = M9Tc050_SDGTableName; String text =
	 * crmUser1EmailID;
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	 * 
	 * String[][] val = { { M9SDGD_1_AccountIndustry, M9SDGD_1_Totalfirm,
	 * M9SDGD_1_Task_as_per_Industries, M9SDGD_1_Individuals,
	 * M9SDGD_1_Fundraising_as_per_Industries }, { M9SDGD_2_AccountIndustry,
	 * M9SDGD_2_Totalfirm, M9SDGD_2_Task_as_per_Industries, M9SDGD_2_Individuals,
	 * M9SDGD_2_Fundraising_as_per_Industries }, { M9SDGD_3_AccountIndustry,
	 * M9SDGD_3_Totalfirm, M9SDGD_3_Task_as_per_Industries, M9SDGD_3_Individuals,
	 * M9SDGD_3_Fundraising_as_per_Industries }, { M9SDGD_4_AccountIndustry,
	 * M9SDGD_4_Totalfirm, M9SDGD_4_Task_as_per_Industries, M9SDGD_4_Individuals,
	 * M9SDGD_4_Fundraising_as_per_Industries }, { M9SDGD_5_AccountIndustry,
	 * M9SDGD_5_Totalfirm, M9SDGD_5_Task_as_per_Industries, M9SDGD_5_Individuals,
	 * M9SDGD_5_Fundraising_as_per_Industries }, { M9SDGD_6_AccountIndustry,
	 * M9SDGD_6_Totalfirm, M9SDGD_6_Task_as_per_Industries, M9SDGD_6_Individuals,
	 * M9SDGD_6_Fundraising_as_per_Industries }, { M9SDGD_7_AccountIndustry,
	 * M9SDGD_7_Totalfirm, M9SDGD_7_Task_as_per_Industries, M9SDGD_7_Individuals,
	 * M9SDGD_7_Fundraising_as_per_Industries }, { M9SDGD_8_AccountIndustry,
	 * M9SDGD_8_Totalfirm, M9SDGD_8_Task_as_per_Industries, M9SDGD_8_Individuals,
	 * M9SDGD_8_Fundraising_as_per_Industries }, { M9SDGD_9_AccountIndustry,
	 * M9SDGD_9_Totalfirm, M9SDGD_9_Task_as_per_Industries, M9SDGD_9_Individuals,
	 * M9SDGD_9_Fundraising_as_per_Industries }, { M9SDGD_10_AccountIndustry,
	 * M9SDGD_10_Totalfirm, M9SDGD_10_Task_as_per_Industries, M9SDGD_10_Individuals,
	 * M9SDGD_10_Fundraising_as_per_Industries }, { M9SDGD_11_AccountIndustry,
	 * M9SDGD_11_Totalfirm, M9SDGD_11_Task_as_per_Industries, M9SDGD_11_Individuals,
	 * M9SDGD_11_Fundraising_as_per_Industries }, { M9SDGD_12_AccountIndustry,
	 * M9SDGD_12_Totalfirm, M9SDGD_12_Task_as_per_Industries, M9SDGD_12_Individuals,
	 * M9SDGD_12_Fundraising_as_per_Industries }, { M9SDGD_13_AccountIndustry,
	 * M9SDGD_13_Totalfirm, M9SDGD_13_Task_as_per_Industries, M9SDGD_13_Individuals,
	 * M9SDGD_13_Fundraising_as_per_Industries }, { M9SDGD_14_AccountIndustry,
	 * M9SDGD_14_Totalfirm, M9SDGD_14_Task_as_per_Industries, M9SDGD_14_Individuals,
	 * M9SDGD_14_Fundraising_as_per_Industries }, { M9SDGD_15_AccountIndustry,
	 * M9SDGD_15_Totalfirm, M9SDGD_15_Task_as_per_Industries, M9SDGD_15_Individuals,
	 * M9SDGD_15_Fundraising_as_per_Industries }, { M9SDGD_16_AccountIndustry,
	 * M9SDGD_16_Totalfirm, M9SDGD_16_Task_as_per_Industries, M9SDGD_16_Individuals,
	 * M9SDGD_16_Fundraising_as_per_Industries }, { M9SDGD_17_AccountIndustry,
	 * M9SDGD_17_Totalfirm, M9SDGD_17_Task_as_per_Industries, M9SDGD_17_Individuals,
	 * M9SDGD_17_Fundraising_as_per_Industries }, { M9SDGD_18_AccountIndustry,
	 * M9SDGD_18_Totalfirm, M9SDGD_18_Task_as_per_Industries, M9SDGD_18_Individuals,
	 * M9SDGD_18_Fundraising_as_per_Industries }, { M9SDGD_19_AccountIndustry,
	 * M9SDGD_19_Totalfirm, M9SDGD_19_Task_as_per_Industries, M9SDGD_19_Individuals,
	 * M9SDGD_19_Fundraising_as_per_Industries }, { M9SDGD_20_AccountIndustry,
	 * M9SDGD_20_Totalfirm, M9SDGD_20_Task_as_per_Industries, M9SDGD_20_Individuals,
	 * M9SDGD_20_Fundraising_as_per_Industries } };
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { ArrayList<String> Data =
	 * AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName,
	 * val); if (Data.isEmpty()) { log(LogStatus.PASS, "SDG Data has been Matched",
	 * YesNo.No); sa.assertTrue(true, "SDG Data has been Matched"); } else {
	 * log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
	 * sa.assertTrue(false, "SDG Data is not Matched : " + Data); } } else {
	 * log(LogStatus.ERROR, "Could not opened the App from the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not opened the App from the App Launcher"); } lp.CRMlogout();
	 * sa.assertAll();
	 * 
	 * }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc051_DeleteContactAndVerifySDGDataOnAppPage(String
	 * projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); String appPage =
	 * M9Tc051_AppPageName; String tableName = M9Tc051_SDGTableName;
	 * 
	 * String[][] val = { { M9SDGD_21_AccountIndustry, M9SDGD_21_Totalfirm,
	 * M9SDGD_21_Task_as_per_Industries, M9SDGD_21_Individuals,
	 * M9SDGD_21_Fundraising_as_per_Industries }, { M9SDGD_22_AccountIndustry,
	 * M9SDGD_22_Totalfirm, M9SDGD_22_Task_as_per_Industries, M9SDGD_22_Individuals,
	 * M9SDGD_22_Fundraising_as_per_Industries }, { M9SDGD_23_AccountIndustry,
	 * M9SDGD_23_Totalfirm, M9SDGD_23_Task_as_per_Industries, M9SDGD_23_Individuals,
	 * M9SDGD_23_Fundraising_as_per_Industries }, { M9SDGD_24_AccountIndustry,
	 * M9SDGD_24_Totalfirm, M9SDGD_24_Task_as_per_Industries, M9SDGD_24_Individuals,
	 * M9SDGD_24_Fundraising_as_per_Industries }, { M9SDGD_25_AccountIndustry,
	 * M9SDGD_25_Totalfirm, M9SDGD_25_Task_as_per_Industries, M9SDGD_25_Individuals,
	 * M9SDGD_25_Fundraising_as_per_Industries }, { M9SDGD_26_AccountIndustry,
	 * M9SDGD_26_Totalfirm, M9SDGD_26_Task_as_per_Industries, M9SDGD_26_Individuals,
	 * M9SDGD_26_Fundraising_as_per_Industries }, { M9SDGD_27_AccountIndustry,
	 * M9SDGD_27_Totalfirm, M9SDGD_27_Task_as_per_Industries, M9SDGD_27_Individuals,
	 * M9SDGD_27_Fundraising_as_per_Industries }, { M9SDGD_28_AccountIndustry,
	 * M9SDGD_28_Totalfirm, M9SDGD_28_Task_as_per_Industries, M9SDGD_28_Individuals,
	 * M9SDGD_28_Fundraising_as_per_Industries }, { M9SDGD_29_AccountIndustry,
	 * M9SDGD_29_Totalfirm, M9SDGD_29_Task_as_per_Industries, M9SDGD_29_Individuals,
	 * M9SDGD_29_Fundraising_as_per_Industries }, { M9SDGD_30_AccountIndustry,
	 * M9SDGD_30_Totalfirm, M9SDGD_30_Task_as_per_Industries, M9SDGD_30_Individuals,
	 * M9SDGD_30_Fundraising_as_per_Industries }, { M9SDGD_31_AccountIndustry,
	 * M9SDGD_31_Totalfirm, M9SDGD_31_Task_as_per_Industries, M9SDGD_31_Individuals,
	 * M9SDGD_31_Fundraising_as_per_Industries }, { M9SDGD_32_AccountIndustry,
	 * M9SDGD_32_Totalfirm, M9SDGD_32_Task_as_per_Industries, M9SDGD_32_Individuals,
	 * M9SDGD_32_Fundraising_as_per_Industries }, { M9SDGD_33_AccountIndustry,
	 * M9SDGD_33_Totalfirm, M9SDGD_33_Task_as_per_Industries, M9SDGD_33_Individuals,
	 * M9SDGD_33_Fundraising_as_per_Industries }, { M9SDGD_34_AccountIndustry,
	 * M9SDGD_34_Totalfirm, M9SDGD_34_Task_as_per_Industries, M9SDGD_34_Individuals,
	 * M9SDGD_34_Fundraising_as_per_Industries }, { M9SDGD_35_AccountIndustry,
	 * M9SDGD_35_Totalfirm, M9SDGD_35_Task_as_per_Industries, M9SDGD_35_Individuals,
	 * M9SDGD_35_Fundraising_as_per_Industries }, { M9SDGD_36_AccountIndustry,
	 * M9SDGD_36_Totalfirm, M9SDGD_36_Task_as_per_Industries, M9SDGD_36_Individuals,
	 * M9SDGD_36_Fundraising_as_per_Industries }, { M9SDGD_37_AccountIndustry,
	 * M9SDGD_37_Totalfirm, M9SDGD_37_Task_as_per_Industries, M9SDGD_37_Individuals,
	 * M9SDGD_37_Fundraising_as_per_Industries }, { M9SDGD_38_AccountIndustry,
	 * M9SDGD_38_Totalfirm, M9SDGD_38_Task_as_per_Industries, M9SDGD_38_Individuals,
	 * M9SDGD_38_Fundraising_as_per_Industries }, { M9SDGD_39_AccountIndustry,
	 * M9SDGD_39_Totalfirm, M9SDGD_39_Task_as_per_Industries, M9SDGD_39_Individuals,
	 * M9SDGD_39_Fundraising_as_per_Industries }, { M9SDGD_40_AccountIndustry,
	 * M9SDGD_40_Totalfirm, M9SDGD_40_Task_as_per_Industries, M9SDGD_40_Individuals,
	 * M9SDGD_40_Fundraising_as_per_Industries } };
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (lp.clickOnTab(projectName, TabName.Object2Tab)) { if
	 * (con.clickOnCreatedContact(projectName, M9_Con1_FName, M9_Con1_LName)) { if
	 * (con.deleteContact(projectName, M9_Con1_FName, M9_Con1_LName)) {
	 * log(LogStatus.PASS, "Contact has been deleted", YesNo.Yes);
	 * sa.assertTrue(true, "Contact has been deleted"); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) { ArrayList<String> Data =
	 * AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName,
	 * val); if (Data.isEmpty()) { log(LogStatus.PASS, "SDG Data has been Matched",
	 * YesNo.No); sa.assertTrue(true, "SDG Data has been Matched"); } else {
	 * log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
	 * sa.assertTrue(false, "SDG Data is not Matched : " + Data); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not Opened the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false, "Could not Opened the App Launcher"); } }
	 * else { log(LogStatus.ERROR, "Could not delete the Contact", YesNo.Yes);
	 * sa.assertTrue(false, "Could not delete the Contact"); } }
	 * 
	 * else { log(LogStatus.ERROR, "Could not click on the contact", YesNo.Yes);
	 * sa.assertTrue(false, "Could not click on the contact"); } } else {
	 * log(LogStatus.ERROR, "Could not click Tab", YesNo.Yes); sa.assertTrue(false,
	 * "Could not click Tab");
	 * 
	 * } lp.CRMlogout(); sa.assertAll();
	 * 
	 * }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc052_checkAllRowButtonOnSDGAndVerifySDGDataOnAppPage(String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); SDGPageBusinessLayer sd = new
	 * SDGPageBusinessLayer(driver); String appPage = M9Tc052_AppPageName; String
	 * tableName = M9Tc052_SDGTableName;
	 * 
	 * String[][] val = { { M9SDGD_21_AccountIndustry, M9SDGD_21_Totalfirm,
	 * M9SDGD_21_Task_as_per_Industries, M9SDGD_21_Individuals,
	 * M9SDGD_21_Fundraising_as_per_Industries }, { M9SDGD_22_AccountIndustry,
	 * M9SDGD_22_Totalfirm, M9SDGD_22_Task_as_per_Industries, M9SDGD_22_Individuals,
	 * M9SDGD_22_Fundraising_as_per_Industries }, { M9SDGD_23_AccountIndustry,
	 * M9SDGD_23_Totalfirm, M9SDGD_23_Task_as_per_Industries, M9SDGD_23_Individuals,
	 * M9SDGD_23_Fundraising_as_per_Industries }, { M9SDGD_24_AccountIndustry,
	 * M9SDGD_24_Totalfirm, M9SDGD_24_Task_as_per_Industries, M9SDGD_24_Individuals,
	 * M9SDGD_24_Fundraising_as_per_Industries }, { M9SDGD_25_AccountIndustry,
	 * M9SDGD_25_Totalfirm, M9SDGD_25_Task_as_per_Industries, M9SDGD_25_Individuals,
	 * M9SDGD_25_Fundraising_as_per_Industries }, { M9SDGD_26_AccountIndustry,
	 * M9SDGD_26_Totalfirm, M9SDGD_26_Task_as_per_Industries, M9SDGD_26_Individuals,
	 * M9SDGD_26_Fundraising_as_per_Industries }, { M9SDGD_27_AccountIndustry,
	 * M9SDGD_27_Totalfirm, M9SDGD_27_Task_as_per_Industries, M9SDGD_27_Individuals,
	 * M9SDGD_27_Fundraising_as_per_Industries }, { M9SDGD_28_AccountIndustry,
	 * M9SDGD_28_Totalfirm, M9SDGD_28_Task_as_per_Industries, M9SDGD_28_Individuals,
	 * M9SDGD_28_Fundraising_as_per_Industries }, { M9SDGD_29_AccountIndustry,
	 * M9SDGD_29_Totalfirm, M9SDGD_29_Task_as_per_Industries, M9SDGD_29_Individuals,
	 * M9SDGD_29_Fundraising_as_per_Industries }, { M9SDGD_30_AccountIndustry,
	 * M9SDGD_30_Totalfirm, M9SDGD_30_Task_as_per_Industries, M9SDGD_30_Individuals,
	 * M9SDGD_30_Fundraising_as_per_Industries }, { M9SDGD_31_AccountIndustry,
	 * M9SDGD_31_Totalfirm, M9SDGD_31_Task_as_per_Industries, M9SDGD_31_Individuals,
	 * M9SDGD_31_Fundraising_as_per_Industries }, { M9SDGD_32_AccountIndustry,
	 * M9SDGD_32_Totalfirm, M9SDGD_32_Task_as_per_Industries, M9SDGD_32_Individuals,
	 * M9SDGD_32_Fundraising_as_per_Industries }, { M9SDGD_33_AccountIndustry,
	 * M9SDGD_33_Totalfirm, M9SDGD_33_Task_as_per_Industries, M9SDGD_33_Individuals,
	 * M9SDGD_33_Fundraising_as_per_Industries }, { M9SDGD_34_AccountIndustry,
	 * M9SDGD_34_Totalfirm, M9SDGD_34_Task_as_per_Industries, M9SDGD_34_Individuals,
	 * M9SDGD_34_Fundraising_as_per_Industries }, { M9SDGD_35_AccountIndustry,
	 * M9SDGD_35_Totalfirm, M9SDGD_35_Task_as_per_Industries, M9SDGD_35_Individuals,
	 * M9SDGD_35_Fundraising_as_per_Industries }, { M9SDGD_36_AccountIndustry,
	 * M9SDGD_36_Totalfirm, M9SDGD_36_Task_as_per_Industries, M9SDGD_36_Individuals,
	 * M9SDGD_36_Fundraising_as_per_Industries }, { M9SDGD_37_AccountIndustry,
	 * M9SDGD_37_Totalfirm, M9SDGD_37_Task_as_per_Industries, M9SDGD_37_Individuals,
	 * M9SDGD_37_Fundraising_as_per_Industries }, { M9SDGD_38_AccountIndustry,
	 * M9SDGD_38_Totalfirm, M9SDGD_38_Task_as_per_Industries, M9SDGD_38_Individuals,
	 * M9SDGD_38_Fundraising_as_per_Industries }, { M9SDGD_39_AccountIndustry,
	 * M9SDGD_39_Totalfirm, M9SDGD_39_Task_as_per_Industries, M9SDGD_39_Individuals,
	 * M9SDGD_39_Fundraising_as_per_Industries }, { M9SDGD_40_AccountIndustry,
	 * M9SDGD_40_Totalfirm, M9SDGD_40_Task_as_per_Industries, M9SDGD_40_Individuals,
	 * M9SDGD_40_Fundraising_as_per_Industries } };
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(60, "Sortable Data Grids")) { if
	 * (sd.editAllRowOnSDG(projectName, "SDG_GROUPBY_1", Condition.SelectCheckbox))
	 * { if (BP.openAppFromAppLauchner(appPage, 50)) { ArrayList<String> Data =
	 * AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName,
	 * val); if (Data.isEmpty()) { log(LogStatus.PASS, "SDG Data has been Matched",
	 * YesNo.No); sa.assertTrue(true, "SDG Data has been Matched"); } else {
	 * log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
	 * sa.assertTrue(false, "SDG Data is not Matched : " + Data); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not Open the App from the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not Open the App from the App Launcher"); } }
	 * 
	 * else { log(LogStatus.ERROR, "Could not Edit the SDG", YesNo.Yes);
	 * sa.assertTrue(false, "Could not Edit the SDG"); } }
	 * 
	 * else { log(LogStatus.ERROR, "Could not Open the SDG from the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not Open the App from the App Launcher"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll();
	 * 
	 * }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc053_RemoveContactFromRecyclebinAndUncheckAllRowButtonVerifySDGDataOnAppPage
	 * (String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); SDGPageBusinessLayer sd = new
	 * SDGPageBusinessLayer(driver); WebElement ele;
	 * 
	 * String appPage = M9Tc053_AppPageName; String tableName =
	 * M9Tc053_SDGTableName; String concatFullName; if (M9_Con1_FName == null) {
	 * concatFullName = M9_Con1_LName; } else { concatFullName = M9_Con1_FName + " "
	 * + M9_Con1_LName; }
	 * 
	 * String[][] listViewSheetData = { { M9LV_5_Member, M9LV_5_TabName,
	 * M9LV_5_ListViewName, M9LV_5_ListAccessibility, M9LV_5_Filter, M9LV_5_Field,
	 * M9LV_5_Operators, M9LV_5_FilterValue } };
	 * 
	 * String[][] val = { { M9SDGD_1_AccountIndustry, M9SDGD_1_Totalfirm,
	 * M9SDGD_1_Task_as_per_Industries, M9SDGD_1_Individuals,
	 * M9SDGD_1_Fundraising_as_per_Industries }, { M9SDGD_2_AccountIndustry,
	 * M9SDGD_2_Totalfirm, M9SDGD_2_Task_as_per_Industries, M9SDGD_2_Individuals,
	 * M9SDGD_2_Fundraising_as_per_Industries }, { M9SDGD_3_AccountIndustry,
	 * M9SDGD_3_Totalfirm, M9SDGD_3_Task_as_per_Industries, M9SDGD_3_Individuals,
	 * M9SDGD_3_Fundraising_as_per_Industries }, { M9SDGD_4_AccountIndustry,
	 * M9SDGD_4_Totalfirm, M9SDGD_4_Task_as_per_Industries, M9SDGD_4_Individuals,
	 * M9SDGD_4_Fundraising_as_per_Industries }, { M9SDGD_5_AccountIndustry,
	 * M9SDGD_5_Totalfirm, M9SDGD_5_Task_as_per_Industries, M9SDGD_5_Individuals,
	 * M9SDGD_5_Fundraising_as_per_Industries }, { M9SDGD_6_AccountIndustry,
	 * M9SDGD_6_Totalfirm, M9SDGD_6_Task_as_per_Industries, M9SDGD_6_Individuals,
	 * M9SDGD_6_Fundraising_as_per_Industries }, { M9SDGD_7_AccountIndustry,
	 * M9SDGD_7_Totalfirm, M9SDGD_7_Task_as_per_Industries, M9SDGD_7_Individuals,
	 * M9SDGD_7_Fundraising_as_per_Industries }, { M9SDGD_8_AccountIndustry,
	 * M9SDGD_8_Totalfirm, M9SDGD_8_Task_as_per_Industries, M9SDGD_8_Individuals,
	 * M9SDGD_8_Fundraising_as_per_Industries }, { M9SDGD_9_AccountIndustry,
	 * M9SDGD_9_Totalfirm, M9SDGD_9_Task_as_per_Industries, M9SDGD_9_Individuals,
	 * M9SDGD_9_Fundraising_as_per_Industries }, { M9SDGD_10_AccountIndustry,
	 * M9SDGD_10_Totalfirm, M9SDGD_10_Task_as_per_Industries, M9SDGD_10_Individuals,
	 * M9SDGD_10_Fundraising_as_per_Industries }, { M9SDGD_11_AccountIndustry,
	 * M9SDGD_11_Totalfirm, M9SDGD_11_Task_as_per_Industries, M9SDGD_11_Individuals,
	 * M9SDGD_11_Fundraising_as_per_Industries }, { M9SDGD_12_AccountIndustry,
	 * M9SDGD_12_Totalfirm, M9SDGD_12_Task_as_per_Industries, M9SDGD_12_Individuals,
	 * M9SDGD_12_Fundraising_as_per_Industries }, { M9SDGD_13_AccountIndustry,
	 * M9SDGD_13_Totalfirm, M9SDGD_13_Task_as_per_Industries, M9SDGD_13_Individuals,
	 * M9SDGD_13_Fundraising_as_per_Industries }, { M9SDGD_14_AccountIndustry,
	 * M9SDGD_14_Totalfirm, M9SDGD_14_Task_as_per_Industries, M9SDGD_14_Individuals,
	 * M9SDGD_14_Fundraising_as_per_Industries }, { M9SDGD_15_AccountIndustry,
	 * M9SDGD_15_Totalfirm, M9SDGD_15_Task_as_per_Industries, M9SDGD_15_Individuals,
	 * M9SDGD_15_Fundraising_as_per_Industries }, { M9SDGD_16_AccountIndustry,
	 * M9SDGD_16_Totalfirm, M9SDGD_16_Task_as_per_Industries, M9SDGD_16_Individuals,
	 * M9SDGD_16_Fundraising_as_per_Industries }, { M9SDGD_17_AccountIndustry,
	 * M9SDGD_17_Totalfirm, M9SDGD_17_Task_as_per_Industries, M9SDGD_17_Individuals,
	 * M9SDGD_17_Fundraising_as_per_Industries }, { M9SDGD_18_AccountIndustry,
	 * M9SDGD_18_Totalfirm, M9SDGD_18_Task_as_per_Industries, M9SDGD_18_Individuals,
	 * M9SDGD_18_Fundraising_as_per_Industries }, { M9SDGD_19_AccountIndustry,
	 * M9SDGD_19_Totalfirm, M9SDGD_19_Task_as_per_Industries, M9SDGD_19_Individuals,
	 * M9SDGD_19_Fundraising_as_per_Industries }, { M9SDGD_20_AccountIndustry,
	 * M9SDGD_20_Totalfirm, M9SDGD_20_Task_as_per_Industries, M9SDGD_20_Individuals,
	 * M9SDGD_20_Fundraising_as_per_Industries } };
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); String recycleTab =
	 * lp.getTabName(projectName, TabName.RecycleBinTab); if
	 * (lp.openAppFromAppLauchner(60, recycleTab)) {
	 * 
	 * for (String[] row : listViewSheetData) {
	 * 
	 * if (lp.addListView(projectName, row, 10)) { log(LogStatus.INFO,
	 * "list view added on " + row[1], YesNo.No); ele =
	 * lp.getCheckboxOfRestoreItemOnRecycleBin(projectName, concatFullName, 30); if
	 * (clickUsingJavaScript(driver, ele, "Check box against : " + concatFullName,
	 * action.BOOLEAN)) { log(LogStatus.INFO, "Click on checkbox for " +
	 * concatFullName, YesNo.No); ; ele =
	 * lp.getRestoreButtonOnRecycleBin(projectName, 30); if
	 * (clickUsingJavaScript(driver, ele, "Restore Button : " + concatFullName,
	 * action.BOOLEAN)) { ThreadSleep(10000); log(LogStatus.INFO,
	 * "Click on Restore Button for " + concatFullName, YesNo.No);
	 * sa.assertTrue(true, "Contact has been restore from the Recycle bin");
	 * 
	 * CommonLib.switchToDefaultContent(driver); ThreadSleep(2000); if
	 * (BP.openAppFromAppLauchner(60, "Sortable Data Grids")) { if
	 * (sd.editAllRowOnSDG(projectName, "SDG_GROUPBY_1",
	 * Condition.UnSelectCheckbox)) {
	 * 
	 * lp.CRMlogout(); CommonLib.ThreadSleep(14000);
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) { ArrayList<String> Data =
	 * AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName,
	 * val); if (Data.isEmpty()) { log(LogStatus.PASS, "SDG Data has been Matched",
	 * YesNo.No); sa.assertTrue(true, "SDG Data has been Matched"); } else {
	 * log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
	 * sa.assertTrue(false, "SDG Data is not Matched : " + Data); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not Open the App from the App Launcher",
	 * YesNo.Yes);
	 * 
	 * sa.assertTrue(false, "Could not Open the App from the App Launcher"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not Edit the All Row on SDG",
	 * YesNo.Yes); } } else { log(LogStatus.ERROR,
	 * "Could not Open the SDG on the App Launcher", YesNo.Yes); }
	 * 
	 * } else {
	 * 
	 * log(LogStatus.ERROR, "Not Able to Click on Restore Button for " +
	 * concatFullName, YesNo.Yes); sa.assertTrue(false,
	 * "Contact is not restore from the Recycle bin"); }
	 * 
	 * } else {
	 * 
	 * log(LogStatus.ERROR, "Not Able to Click on checkbox for " + concatFullName,
	 * YesNo.Yes); sa.assertTrue(false, "Not Able to Click on checkbox for " +
	 * concatFullName); } }
	 * 
	 * else { log(LogStatus.FAIL, "list view could not added on " + row[1],
	 * YesNo.Yes); sa.assertTrue(false, "list view could not added on " + row[1]); }
	 * } } else { log(LogStatus.ERROR, "Not Able to open the Recycle been tab",
	 * YesNo.Yes); sa.assertTrue(false, "Not Able to open the Recycle been tab");
	 * 
	 * } lp.CRMlogout(); sa.assertAll();
	 * 
	 * }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc054_UpdatePiclistFieldLabelAndVerifySDGDataOnAppPage(String projectName)
	 * {
	 * 
	 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver);
	 * FieldAndRelationshipPageBusinessLayer fr = new
	 * FieldAndRelationshipPageBusinessLayer(driver); String appPage =
	 * M9Tc054_AppPageName; String tableName = M9Tc054_SDGTableName;
	 * 
	 * String[][] val = { { M9SDGD_41_AccountIndustry, M9SDGD_41_Totalfirm,
	 * M9SDGD_41_Task_as_per_Industries, M9SDGD_41_Individuals,
	 * M9SDGD_41_Fundraising_as_per_Industries }, { M9SDGD_42_AccountIndustry,
	 * M9SDGD_42_Totalfirm, M9SDGD_42_Task_as_per_Industries, M9SDGD_42_Individuals,
	 * M9SDGD_42_Fundraising_as_per_Industries }, { M9SDGD_43_AccountIndustry,
	 * M9SDGD_43_Totalfirm, M9SDGD_43_Task_as_per_Industries, M9SDGD_43_Individuals,
	 * M9SDGD_43_Fundraising_as_per_Industries }, { M9SDGD_44_AccountIndustry,
	 * M9SDGD_44_Totalfirm, M9SDGD_44_Task_as_per_Industries, M9SDGD_44_Individuals,
	 * M9SDGD_44_Fundraising_as_per_Industries }, { M9SDGD_45_AccountIndustry,
	 * M9SDGD_45_Totalfirm, M9SDGD_45_Task_as_per_Industries, M9SDGD_45_Individuals,
	 * M9SDGD_45_Fundraising_as_per_Industries }, { M9SDGD_46_AccountIndustry,
	 * M9SDGD_46_Totalfirm, M9SDGD_46_Task_as_per_Industries, M9SDGD_46_Individuals,
	 * M9SDGD_46_Fundraising_as_per_Industries }, { M9SDGD_47_AccountIndustry,
	 * M9SDGD_47_Totalfirm, M9SDGD_47_Task_as_per_Industries, M9SDGD_47_Individuals,
	 * M9SDGD_47_Fundraising_as_per_Industries }, { M9SDGD_48_AccountIndustry,
	 * M9SDGD_48_Totalfirm, M9SDGD_48_Task_as_per_Industries, M9SDGD_48_Individuals,
	 * M9SDGD_48_Fundraising_as_per_Industries }, { M9SDGD_49_AccountIndustry,
	 * M9SDGD_49_Totalfirm, M9SDGD_49_Task_as_per_Industries, M9SDGD_49_Individuals,
	 * M9SDGD_49_Fundraising_as_per_Industries }, { M9SDGD_50_AccountIndustry,
	 * M9SDGD_50_Totalfirm, M9SDGD_50_Task_as_per_Industries, M9SDGD_50_Individuals,
	 * M9SDGD_50_Fundraising_as_per_Industries }, { M9SDGD_51_AccountIndustry,
	 * M9SDGD_51_Totalfirm, M9SDGD_51_Task_as_per_Industries, M9SDGD_51_Individuals,
	 * M9SDGD_51_Fundraising_as_per_Industries }, { M9SDGD_52_AccountIndustry,
	 * M9SDGD_52_Totalfirm, M9SDGD_52_Task_as_per_Industries, M9SDGD_52_Individuals,
	 * M9SDGD_52_Fundraising_as_per_Industries }, { M9SDGD_53_AccountIndustry,
	 * M9SDGD_53_Totalfirm, M9SDGD_53_Task_as_per_Industries, M9SDGD_53_Individuals,
	 * M9SDGD_53_Fundraising_as_per_Industries }, { M9SDGD_54_AccountIndustry,
	 * M9SDGD_54_Totalfirm, M9SDGD_54_Task_as_per_Industries, M9SDGD_54_Individuals,
	 * M9SDGD_54_Fundraising_as_per_Industries }, { M9SDGD_55_AccountIndustry,
	 * M9SDGD_55_Totalfirm, M9SDGD_55_Task_as_per_Industries, M9SDGD_55_Individuals,
	 * M9SDGD_55_Fundraising_as_per_Industries }, { M9SDGD_56_AccountIndustry,
	 * M9SDGD_56_Totalfirm, M9SDGD_56_Task_as_per_Industries, M9SDGD_56_Individuals,
	 * M9SDGD_56_Fundraising_as_per_Industries }, { M9SDGD_57_AccountIndustry,
	 * M9SDGD_57_Totalfirm, M9SDGD_57_Task_as_per_Industries, M9SDGD_57_Individuals,
	 * M9SDGD_57_Fundraising_as_per_Industries }, { M9SDGD_58_AccountIndustry,
	 * M9SDGD_58_Totalfirm, M9SDGD_58_Task_as_per_Industries, M9SDGD_58_Individuals,
	 * M9SDGD_58_Fundraising_as_per_Industries }, { M9SDGD_59_AccountIndustry,
	 * M9SDGD_59_Totalfirm, M9SDGD_59_Task_as_per_Industries, M9SDGD_59_Individuals,
	 * M9SDGD_59_Fundraising_as_per_Industries }, { M9SDGD_60_AccountIndustry,
	 * M9SDGD_60_Totalfirm, M9SDGD_60_Task_as_per_Industries, M9SDGD_60_Individuals,
	 * M9SDGD_60_Fundraising_as_per_Industries } };
	 * 
	 * CommonLib.refresh(driver); lp.CRMLogin(superAdminUserName, adminPassword,
	 * appName); if (home.clickOnSetUpLink()) {
	 * 
	 * String parentWindowID = switchOnWindow(driver); if (parentWindowID == null) {
	 * sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); }
	 * 
	 * if (setup.searchStandardOrCustomObject(projectName, mode, object.Firm)) {
	 * 
	 * if (setup.clickOnObjectFeature(projectName, mode, object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip)) { if
	 * (fr.editPicklistFieldLabel(projectName, "Industry", "BiotechnologyUP",
	 * "Biotechnology")) { log(LogStatus.PASS, "Label Name has been Changed",
	 * YesNo.No); driver.close(); driver.switchTo().window(parentWindowID);
	 * CommonLib.refresh(driver); lp.CRMlogout(); CommonLib.ThreadSleep(14000);
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) { ArrayList<String> Data =
	 * AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName,
	 * val); if (Data.isEmpty()) { log(LogStatus.PASS, "SDG Data has been Matched",
	 * YesNo.No); sa.assertTrue(true, "SDG Data has been Matched"); } else {
	 * log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
	 * sa.assertTrue(false, "SDG Data is not Matched : " + Data); }
	 * 
	 * }
	 * 
	 * else { log(LogStatus.ERROR, "Could not Opened the App Launcher", YesNo.Yes);
	 * sa.assertTrue(false, "Could not Opened the App Launcher"); } } else {
	 * log(LogStatus.ERROR, "Could not edit the Picklist", YesNo.Yes);
	 * sa.assertTrue(false, "Could not edit the Picklist"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Not Able to Click on Object and Feature name",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Not Able to Click on Object and Feature name"); } } else {
	 * log(LogStatus.ERROR, "Not Able to Search the Object", YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to Search the Object"); } } else {
	 * log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to open the setup page"); }
	 * 
	 * lp.CRMlogout();
	 * 
	 * sa.assertAll();
	 * 
	 * }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc055_DeactivateBiotechnologyOnIndustryAndVerifySDGDataOnAppPage(String
	 * projectName) { SetupPageBusinessLayer setup = new
	 * SetupPageBusinessLayer(driver); LoginPageBusinessLayer lp = new
	 * LoginPageBusinessLayer(driver); HomePageBusineesLayer home = new
	 * HomePageBusineesLayer(driver); BasePageBusinessLayer BP = new
	 * BasePageBusinessLayer(driver); LightningAppBuilderPageBusinessLayer
	 * AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
	 * FieldAndRelationshipPageBusinessLayer fr = new
	 * FieldAndRelationshipPageBusinessLayer(driver); String appPage =
	 * M9Tc055_AppPageName; String tableName = M9Tc055_SDGTableName;
	 * 
	 * String[][] val = { { M9SDGD_61_AccountIndustry, M9SDGD_61_Totalfirm,
	 * M9SDGD_61_Task_as_per_Industries, M9SDGD_61_Individuals,
	 * M9SDGD_61_Fundraising_as_per_Industries }, { M9SDGD_62_AccountIndustry,
	 * M9SDGD_62_Totalfirm, M9SDGD_62_Task_as_per_Industries, M9SDGD_62_Individuals,
	 * M9SDGD_62_Fundraising_as_per_Industries }, { M9SDGD_63_AccountIndustry,
	 * M9SDGD_63_Totalfirm, M9SDGD_63_Task_as_per_Industries, M9SDGD_63_Individuals,
	 * M9SDGD_63_Fundraising_as_per_Industries }, { M9SDGD_64_AccountIndustry,
	 * M9SDGD_64_Totalfirm, M9SDGD_64_Task_as_per_Industries, M9SDGD_64_Individuals,
	 * M9SDGD_64_Fundraising_as_per_Industries }, { M9SDGD_65_AccountIndustry,
	 * M9SDGD_65_Totalfirm, M9SDGD_65_Task_as_per_Industries, M9SDGD_65_Individuals,
	 * M9SDGD_65_Fundraising_as_per_Industries }, { M9SDGD_66_AccountIndustry,
	 * M9SDGD_66_Totalfirm, M9SDGD_66_Task_as_per_Industries, M9SDGD_66_Individuals,
	 * M9SDGD_66_Fundraising_as_per_Industries }, { M9SDGD_67_AccountIndustry,
	 * M9SDGD_67_Totalfirm, M9SDGD_67_Task_as_per_Industries, M9SDGD_67_Individuals,
	 * M9SDGD_67_Fundraising_as_per_Industries }, { M9SDGD_68_AccountIndustry,
	 * M9SDGD_68_Totalfirm, M9SDGD_68_Task_as_per_Industries, M9SDGD_68_Individuals,
	 * M9SDGD_68_Fundraising_as_per_Industries }, { M9SDGD_69_AccountIndustry,
	 * M9SDGD_69_Totalfirm, M9SDGD_69_Task_as_per_Industries, M9SDGD_69_Individuals,
	 * M9SDGD_69_Fundraising_as_per_Industries }, { M9SDGD_70_AccountIndustry,
	 * M9SDGD_70_Totalfirm, M9SDGD_70_Task_as_per_Industries, M9SDGD_70_Individuals,
	 * M9SDGD_70_Fundraising_as_per_Industries }, { M9SDGD_71_AccountIndustry,
	 * M9SDGD_71_Totalfirm, M9SDGD_71_Task_as_per_Industries, M9SDGD_71_Individuals,
	 * M9SDGD_71_Fundraising_as_per_Industries }, { M9SDGD_72_AccountIndustry,
	 * M9SDGD_72_Totalfirm, M9SDGD_72_Task_as_per_Industries, M9SDGD_72_Individuals,
	 * M9SDGD_72_Fundraising_as_per_Industries }, { M9SDGD_73_AccountIndustry,
	 * M9SDGD_73_Totalfirm, M9SDGD_73_Task_as_per_Industries, M9SDGD_73_Individuals,
	 * M9SDGD_73_Fundraising_as_per_Industries }, { M9SDGD_74_AccountIndustry,
	 * M9SDGD_74_Totalfirm, M9SDGD_74_Task_as_per_Industries, M9SDGD_74_Individuals,
	 * M9SDGD_74_Fundraising_as_per_Industries }, { M9SDGD_75_AccountIndustry,
	 * M9SDGD_75_Totalfirm, M9SDGD_75_Task_as_per_Industries, M9SDGD_75_Individuals,
	 * M9SDGD_75_Fundraising_as_per_Industries }, { M9SDGD_76_AccountIndustry,
	 * M9SDGD_76_Totalfirm, M9SDGD_76_Task_as_per_Industries, M9SDGD_76_Individuals,
	 * M9SDGD_76_Fundraising_as_per_Industries }, { M9SDGD_77_AccountIndustry,
	 * M9SDGD_77_Totalfirm, M9SDGD_77_Task_as_per_Industries, M9SDGD_77_Individuals,
	 * M9SDGD_77_Fundraising_as_per_Industries }, { M9SDGD_78_AccountIndustry,
	 * M9SDGD_78_Totalfirm, M9SDGD_78_Task_as_per_Industries, M9SDGD_78_Individuals,
	 * M9SDGD_78_Fundraising_as_per_Industries }, { M9SDGD_79_AccountIndustry,
	 * M9SDGD_79_Totalfirm, M9SDGD_79_Task_as_per_Industries, M9SDGD_79_Individuals,
	 * M9SDGD_79_Fundraising_as_per_Industries }, { M9SDGD_80_AccountIndustry,
	 * M9SDGD_80_Totalfirm, M9SDGD_80_Task_as_per_Industries, M9SDGD_80_Individuals,
	 * M9SDGD_80_Fundraising_as_per_Industries } };
	 * 
	 * String value = "BiotechnologyUP";
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (home.clickOnSetUpLink()) {
	 * 
	 * String parentWindowID = switchOnWindow(driver); if (parentWindowID == null) {
	 * sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); } if (setup.searchStandardOrCustomObject(projectName, mode, "Firm")) { if
	 * (setup.clickOnObjectFeature(projectName, mode, object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip)) { if
	 * (fr.activateOrDeactivatePiclistValueOfField(projectName, "Industry", value,
	 * Condition.deactivate)) { log(LogStatus.PASS, value + " has been deactivated",
	 * YesNo.No); driver.close(); driver.switchTo().window(parentWindowID);
	 * CommonLib.refresh(driver); lp.CRMlogout(); CommonLib.ThreadSleep(14000);
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) { ArrayList<String> Data =
	 * AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName,
	 * val); if (Data.isEmpty()) { log(LogStatus.PASS, "SDG Data has been Matched",
	 * YesNo.No); sa.assertTrue(true, "SDG Data has been Matched"); } else {
	 * log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
	 * sa.assertTrue(false, "SDG Data is not Matched : " + Data); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not Opened the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false, "Could not Opened the App Launcher"); } }
	 * else { log(LogStatus.ERROR, "Could not deactivated the Picklist", YesNo.Yes);
	 * sa.assertTrue(false, "Could not deactivated the Picklist"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Not Able to Click on Object and Feature name",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Not Able to Click on Object and Feature name"); } } else {
	 * log(LogStatus.ERROR, "Not Able to Search the Object", YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to Search the Object"); } } else {
	 * log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to open the setup page"); } lp.CRMlogout();
	 * CommonLib.ThreadSleep(14000);
	 * 
	 * sa.assertAll();
	 * 
	 * }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc056_ActivateBiotechnologyOnIndustryAndVerifySDGDataOnAppPage(String
	 * projectName) {
	 * 
	 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver);
	 * FieldAndRelationshipPageBusinessLayer fr = new
	 * FieldAndRelationshipPageBusinessLayer(driver); String appPage =
	 * M9Tc056_AppPageName; String tableName = M9Tc056_SDGTableName;
	 * 
	 * String[][] val = { { M9SDGD_61_AccountIndustry, M9SDGD_61_Totalfirm,
	 * M9SDGD_61_Task_as_per_Industries, M9SDGD_61_Individuals,
	 * M9SDGD_61_Fundraising_as_per_Industries }, { M9SDGD_62_AccountIndustry,
	 * M9SDGD_62_Totalfirm, M9SDGD_62_Task_as_per_Industries, M9SDGD_62_Individuals,
	 * M9SDGD_62_Fundraising_as_per_Industries }, { M9SDGD_63_AccountIndustry,
	 * M9SDGD_63_Totalfirm, M9SDGD_63_Task_as_per_Industries, M9SDGD_63_Individuals,
	 * M9SDGD_63_Fundraising_as_per_Industries }, { M9SDGD_64_AccountIndustry,
	 * M9SDGD_64_Totalfirm, M9SDGD_64_Task_as_per_Industries, M9SDGD_64_Individuals,
	 * M9SDGD_64_Fundraising_as_per_Industries }, { M9SDGD_65_AccountIndustry,
	 * M9SDGD_65_Totalfirm, M9SDGD_65_Task_as_per_Industries, M9SDGD_65_Individuals,
	 * M9SDGD_65_Fundraising_as_per_Industries }, { M9SDGD_66_AccountIndustry,
	 * M9SDGD_66_Totalfirm, M9SDGD_66_Task_as_per_Industries, M9SDGD_66_Individuals,
	 * M9SDGD_66_Fundraising_as_per_Industries }, { M9SDGD_67_AccountIndustry,
	 * M9SDGD_67_Totalfirm, M9SDGD_67_Task_as_per_Industries, M9SDGD_67_Individuals,
	 * M9SDGD_67_Fundraising_as_per_Industries }, { M9SDGD_68_AccountIndustry,
	 * M9SDGD_68_Totalfirm, M9SDGD_68_Task_as_per_Industries, M9SDGD_68_Individuals,
	 * M9SDGD_68_Fundraising_as_per_Industries }, { M9SDGD_69_AccountIndustry,
	 * M9SDGD_69_Totalfirm, M9SDGD_69_Task_as_per_Industries, M9SDGD_69_Individuals,
	 * M9SDGD_69_Fundraising_as_per_Industries }, { M9SDGD_70_AccountIndustry,
	 * M9SDGD_70_Totalfirm, M9SDGD_70_Task_as_per_Industries, M9SDGD_70_Individuals,
	 * M9SDGD_70_Fundraising_as_per_Industries }, { M9SDGD_71_AccountIndustry,
	 * M9SDGD_71_Totalfirm, M9SDGD_71_Task_as_per_Industries, M9SDGD_71_Individuals,
	 * M9SDGD_71_Fundraising_as_per_Industries }, { M9SDGD_72_AccountIndustry,
	 * M9SDGD_72_Totalfirm, M9SDGD_72_Task_as_per_Industries, M9SDGD_72_Individuals,
	 * M9SDGD_72_Fundraising_as_per_Industries }, { M9SDGD_73_AccountIndustry,
	 * M9SDGD_73_Totalfirm, M9SDGD_73_Task_as_per_Industries, M9SDGD_73_Individuals,
	 * M9SDGD_73_Fundraising_as_per_Industries }, { M9SDGD_74_AccountIndustry,
	 * M9SDGD_74_Totalfirm, M9SDGD_74_Task_as_per_Industries, M9SDGD_74_Individuals,
	 * M9SDGD_74_Fundraising_as_per_Industries }, { M9SDGD_75_AccountIndustry,
	 * M9SDGD_75_Totalfirm, M9SDGD_75_Task_as_per_Industries, M9SDGD_75_Individuals,
	 * M9SDGD_75_Fundraising_as_per_Industries }, { M9SDGD_76_AccountIndustry,
	 * M9SDGD_76_Totalfirm, M9SDGD_76_Task_as_per_Industries, M9SDGD_76_Individuals,
	 * M9SDGD_76_Fundraising_as_per_Industries }, { M9SDGD_77_AccountIndustry,
	 * M9SDGD_77_Totalfirm, M9SDGD_77_Task_as_per_Industries, M9SDGD_77_Individuals,
	 * M9SDGD_77_Fundraising_as_per_Industries }, { M9SDGD_78_AccountIndustry,
	 * M9SDGD_78_Totalfirm, M9SDGD_78_Task_as_per_Industries, M9SDGD_78_Individuals,
	 * M9SDGD_78_Fundraising_as_per_Industries }, { M9SDGD_79_AccountIndustry,
	 * M9SDGD_79_Totalfirm, M9SDGD_79_Task_as_per_Industries, M9SDGD_79_Individuals,
	 * M9SDGD_79_Fundraising_as_per_Industries }, { M9SDGD_80_AccountIndustry,
	 * M9SDGD_80_Totalfirm, M9SDGD_80_Task_as_per_Industries, M9SDGD_80_Individuals,
	 * M9SDGD_80_Fundraising_as_per_Industries } };
	 * 
	 * String value = "BiotechnologyUP";
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (home.clickOnSetUpLink()) { String parentWindowID = switchOnWindow(driver);
	 * if (parentWindowID == null) { sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); }
	 * 
	 * if (setup.searchStandardOrCustomObject(projectName, mode, "Firm")) { if
	 * (setup.clickOnObjectFeature(projectName, mode, object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip)) { if
	 * (fr.activateOrDeactivatePiclistValueOfField(projectName, "Industry", value,
	 * Condition.activate)) { log(LogStatus.PASS, value + " has been activated",
	 * YesNo.No);
	 * 
	 * driver.close(); driver.switchTo().window(parentWindowID);
	 * CommonLib.refresh(driver); lp.CRMlogout(); CommonLib.ThreadSleep(14000);
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) { ArrayList<String> Data =
	 * AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName,
	 * val); if (Data.isEmpty()) { log(LogStatus.PASS, "SDG Data has been Matched",
	 * YesNo.No); sa.assertTrue(true, "SDG Data has been Matched"); } else {
	 * log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
	 * sa.assertTrue(false, "SDG Data is not Matched : " + Data); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not Opened the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false, "Could not Opened the App Launcher"); } }
	 * else { log(LogStatus.ERROR, "Could not Activated the Picklist ", YesNo.Yes);
	 * sa.assertTrue(false, "Could not Activated the Picklist"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Not Able to Click on Object and Feature name",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Not Able to Click on Object and Feature name"); } } else {
	 * log(LogStatus.ERROR, "Not Able to Search the Object", YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to Search the Object"); } } else {
	 * log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to open the setup page"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc057_deletePicklistOptionAndReplaceValueAndVerifySDGDataOnAppPage(String
	 * projectName) {
	 * 
	 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver);
	 * FieldAndRelationshipPageBusinessLayer fr = new
	 * FieldAndRelationshipPageBusinessLayer(driver); String field = "Industry";
	 * String picklistvalue = "Banking"; String replaceValueName = "Consulting";
	 * 
	 * String[][] val = { { M9SDGD_81_AccountIndustry, M9SDGD_81_Totalfirm,
	 * M9SDGD_81_Task_as_per_Industries, M9SDGD_81_Individuals,
	 * M9SDGD_81_Fundraising_as_per_Industries }, { M9SDGD_82_AccountIndustry,
	 * M9SDGD_82_Totalfirm, M9SDGD_82_Task_as_per_Industries, M9SDGD_82_Individuals,
	 * M9SDGD_82_Fundraising_as_per_Industries }, { M9SDGD_83_AccountIndustry,
	 * M9SDGD_83_Totalfirm, M9SDGD_83_Task_as_per_Industries, M9SDGD_83_Individuals,
	 * M9SDGD_83_Fundraising_as_per_Industries }, { M9SDGD_84_AccountIndustry,
	 * M9SDGD_84_Totalfirm, M9SDGD_84_Task_as_per_Industries, M9SDGD_84_Individuals,
	 * M9SDGD_84_Fundraising_as_per_Industries }, { M9SDGD_85_AccountIndustry,
	 * M9SDGD_85_Totalfirm, M9SDGD_85_Task_as_per_Industries, M9SDGD_85_Individuals,
	 * M9SDGD_85_Fundraising_as_per_Industries }, { M9SDGD_86_AccountIndustry,
	 * M9SDGD_86_Totalfirm, M9SDGD_86_Task_as_per_Industries, M9SDGD_86_Individuals,
	 * M9SDGD_86_Fundraising_as_per_Industries }, { M9SDGD_87_AccountIndustry,
	 * M9SDGD_87_Totalfirm, M9SDGD_87_Task_as_per_Industries, M9SDGD_87_Individuals,
	 * M9SDGD_87_Fundraising_as_per_Industries }, { M9SDGD_88_AccountIndustry,
	 * M9SDGD_88_Totalfirm, M9SDGD_88_Task_as_per_Industries, M9SDGD_88_Individuals,
	 * M9SDGD_88_Fundraising_as_per_Industries }, { M9SDGD_89_AccountIndustry,
	 * M9SDGD_89_Totalfirm, M9SDGD_89_Task_as_per_Industries, M9SDGD_89_Individuals,
	 * M9SDGD_89_Fundraising_as_per_Industries }, { M9SDGD_90_AccountIndustry,
	 * M9SDGD_90_Totalfirm, M9SDGD_90_Task_as_per_Industries, M9SDGD_90_Individuals,
	 * M9SDGD_90_Fundraising_as_per_Industries }, { M9SDGD_91_AccountIndustry,
	 * M9SDGD_91_Totalfirm, M9SDGD_91_Task_as_per_Industries, M9SDGD_91_Individuals,
	 * M9SDGD_91_Fundraising_as_per_Industries }, { M9SDGD_92_AccountIndustry,
	 * M9SDGD_92_Totalfirm, M9SDGD_92_Task_as_per_Industries, M9SDGD_92_Individuals,
	 * M9SDGD_92_Fundraising_as_per_Industries }, { M9SDGD_93_AccountIndustry,
	 * M9SDGD_93_Totalfirm, M9SDGD_93_Task_as_per_Industries, M9SDGD_93_Individuals,
	 * M9SDGD_93_Fundraising_as_per_Industries }, { M9SDGD_94_AccountIndustry,
	 * M9SDGD_94_Totalfirm, M9SDGD_94_Task_as_per_Industries, M9SDGD_94_Individuals,
	 * M9SDGD_94_Fundraising_as_per_Industries }, { M9SDGD_95_AccountIndustry,
	 * M9SDGD_95_Totalfirm, M9SDGD_95_Task_as_per_Industries, M9SDGD_95_Individuals,
	 * M9SDGD_95_Fundraising_as_per_Industries }, { M9SDGD_96_AccountIndustry,
	 * M9SDGD_96_Totalfirm, M9SDGD_96_Task_as_per_Industries, M9SDGD_96_Individuals,
	 * M9SDGD_96_Fundraising_as_per_Industries }, { M9SDGD_97_AccountIndustry,
	 * M9SDGD_97_Totalfirm, M9SDGD_97_Task_as_per_Industries, M9SDGD_97_Individuals,
	 * M9SDGD_97_Fundraising_as_per_Industries }, { M9SDGD_98_AccountIndustry,
	 * M9SDGD_98_Totalfirm, M9SDGD_98_Task_as_per_Industries, M9SDGD_98_Individuals,
	 * M9SDGD_98_Fundraising_as_per_Industries }, { M9SDGD_99_AccountIndustry,
	 * M9SDGD_99_Totalfirm, M9SDGD_99_Task_as_per_Industries, M9SDGD_99_Individuals,
	 * M9SDGD_99_Fundraising_as_per_Industries } };
	 * 
	 * for (int i = 0; i < val.length; i++) { for (int j = 0; j < val[i].length;
	 * j++) { System.out.print(" " + val[i][j]); } System.out.println();
	 * 
	 * } String appPage = M9Tc057_AppPageName; String tableName =
	 * M9Tc057_SDGTableName; lp.CRMLogin(superAdminUserName, adminPassword,
	 * appName); if (home.clickOnSetUpLink()) {
	 * 
	 * String parentWindowID = switchOnWindow(driver); if (parentWindowID == null) {
	 * sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); }
	 * 
	 * if (setup.searchStandardOrCustomObject(projectName, mode, object.Firm)) {
	 * 
	 * if (setup.clickOnObjectFeature(projectName, mode, object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip)) { if
	 * (fr.deletePicklistOptionAndReplaceValue(projectName, field, replaceValueName,
	 * picklistvalue, Condition.replaceWithValue)) { log(LogStatus.PASS,
	 * "Piclist option has been deleted", YesNo.No); driver.close();
	 * driver.switchTo().window(parentWindowID); CommonLib.refresh(driver);
	 * lp.CRMlogout(); ThreadSleep(14000); lp.CRMLogin(crmUser1EmailID,
	 * adminPassword, appName); if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode,
	 * appPage, tableName, val); if (Data.isEmpty()) { log(LogStatus.PASS,
	 * "SDG Data has been Matched", YesNo.No); sa.assertTrue(true,
	 * "SDG Data has been Matched"); } else { log(LogStatus.ERROR,
	 * "SDG Data is not Matched", YesNo.Yes); sa.assertTrue(false,
	 * "SDG Data is not Matched : " + Data); }
	 * 
	 * }
	 * 
	 * else { log(LogStatus.ERROR, "Could not Opened the App Launcher", YesNo.Yes);
	 * sa.assertTrue(false, "Could not Opened the App Launcher"); } } else {
	 * log(LogStatus.ERROR, "Piclist is not deleted", YesNo.Yes);
	 * sa.assertTrue(false, "Piclist is not deleted"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Not Able to Click on Object and Feature name",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Not Able to Click on Object and Feature name"); } } else {
	 * log(LogStatus.ERROR, "Not Able to Search the Object", YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to Search the Object"); } } else {
	 * log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to open the setup page"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll();
	 * 
	 * }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc058_deletePicklistOptionAndReplaceValuewithBlankAndVerifySDGDataOnAppPage
	 * (String projectName) {
	 * 
	 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver);
	 * FieldAndRelationshipPageBusinessLayer fr = new
	 * FieldAndRelationshipPageBusinessLayer(driver); String field = "Industry";
	 * String picklistvalue = "Agriculture"; String replaceValueName = ""; String
	 * appPage = M9Tc058_AppPageName; String tableName = M9Tc058_SDGTableName;
	 * 
	 * String[][] val = { { M9SDGD_100_AccountIndustry, M9SDGD_100_Totalfirm,
	 * M9SDGD_100_Task_as_per_Industries, M9SDGD_100_Individuals,
	 * M9SDGD_100_Fundraising_as_per_Industries }, { M9SDGD_101_AccountIndustry,
	 * M9SDGD_101_Totalfirm, M9SDGD_101_Task_as_per_Industries,
	 * M9SDGD_101_Individuals, M9SDGD_101_Fundraising_as_per_Industries }, {
	 * M9SDGD_102_AccountIndustry, M9SDGD_102_Totalfirm,
	 * M9SDGD_102_Task_as_per_Industries, M9SDGD_102_Individuals,
	 * M9SDGD_102_Fundraising_as_per_Industries }, { M9SDGD_103_AccountIndustry,
	 * M9SDGD_103_Totalfirm, M9SDGD_103_Task_as_per_Industries,
	 * M9SDGD_103_Individuals, M9SDGD_103_Fundraising_as_per_Industries }, {
	 * M9SDGD_104_AccountIndustry, M9SDGD_104_Totalfirm,
	 * M9SDGD_104_Task_as_per_Industries, M9SDGD_104_Individuals,
	 * M9SDGD_104_Fundraising_as_per_Industries }, { M9SDGD_105_AccountIndustry,
	 * M9SDGD_105_Totalfirm, M9SDGD_105_Task_as_per_Industries,
	 * M9SDGD_105_Individuals, M9SDGD_105_Fundraising_as_per_Industries }, {
	 * M9SDGD_106_AccountIndustry, M9SDGD_106_Totalfirm,
	 * M9SDGD_106_Task_as_per_Industries, M9SDGD_106_Individuals,
	 * M9SDGD_106_Fundraising_as_per_Industries }, { M9SDGD_107_AccountIndustry,
	 * M9SDGD_107_Totalfirm, M9SDGD_107_Task_as_per_Industries,
	 * M9SDGD_107_Individuals, M9SDGD_107_Fundraising_as_per_Industries }, {
	 * M9SDGD_108_AccountIndustry, M9SDGD_108_Totalfirm,
	 * M9SDGD_108_Task_as_per_Industries, M9SDGD_108_Individuals,
	 * M9SDGD_108_Fundraising_as_per_Industries }, { M9SDGD_109_AccountIndustry,
	 * M9SDGD_109_Totalfirm, M9SDGD_109_Task_as_per_Industries,
	 * M9SDGD_109_Individuals, M9SDGD_109_Fundraising_as_per_Industries }, {
	 * M9SDGD_110_AccountIndustry, M9SDGD_110_Totalfirm,
	 * M9SDGD_110_Task_as_per_Industries, M9SDGD_110_Individuals,
	 * M9SDGD_110_Fundraising_as_per_Industries }, { M9SDGD_111_AccountIndustry,
	 * M9SDGD_111_Totalfirm, M9SDGD_111_Task_as_per_Industries,
	 * M9SDGD_111_Individuals, M9SDGD_111_Fundraising_as_per_Industries }, {
	 * M9SDGD_112_AccountIndustry, M9SDGD_112_Totalfirm,
	 * M9SDGD_112_Task_as_per_Industries, M9SDGD_112_Individuals,
	 * M9SDGD_112_Fundraising_as_per_Industries }, { M9SDGD_113_AccountIndustry,
	 * M9SDGD_113_Totalfirm, M9SDGD_113_Task_as_per_Industries,
	 * M9SDGD_113_Individuals, M9SDGD_113_Fundraising_as_per_Industries }, {
	 * M9SDGD_114_AccountIndustry, M9SDGD_114_Totalfirm,
	 * M9SDGD_114_Task_as_per_Industries, M9SDGD_114_Individuals,
	 * M9SDGD_114_Fundraising_as_per_Industries }, { M9SDGD_115_AccountIndustry,
	 * M9SDGD_115_Totalfirm, M9SDGD_115_Task_as_per_Industries,
	 * M9SDGD_115_Individuals, M9SDGD_115_Fundraising_as_per_Industries }, {
	 * M9SDGD_116_AccountIndustry, M9SDGD_116_Totalfirm,
	 * M9SDGD_116_Task_as_per_Industries, M9SDGD_116_Individuals,
	 * M9SDGD_116_Fundraising_as_per_Industries }, { M9SDGD_117_AccountIndustry,
	 * M9SDGD_117_Totalfirm, M9SDGD_117_Task_as_per_Industries,
	 * M9SDGD_117_Individuals, M9SDGD_117_Fundraising_as_per_Industries } };
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (home.clickOnSetUpLink()) {
	 * 
	 * String parentWindowID = switchOnWindow(driver); if (parentWindowID == null) {
	 * sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); } if (setup.searchStandardOrCustomObject(projectName, mode, object.Firm))
	 * {
	 * 
	 * if (setup.clickOnObjectFeature(projectName, mode, object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip)) { if
	 * (fr.deletePicklistOptionAndReplaceValue(projectName, field, replaceValueName,
	 * picklistvalue, Condition.replaceWithBlank)) { log(LogStatus.PASS,
	 * "Piclist option has been deleted", YesNo.No); driver.close();
	 * driver.switchTo().window(parentWindowID); CommonLib.refresh(driver);
	 * lp.CRMlogout(); ThreadSleep(14000); lp.CRMLogin(crmUser1EmailID,
	 * adminPassword, appName); if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode,
	 * appPage, tableName, val); if (Data.isEmpty()) { log(LogStatus.PASS,
	 * "SDG Data has been Matched", YesNo.No); sa.assertTrue(true,
	 * "SDG Data has been Matched"); } else { log(LogStatus.ERROR,
	 * "SDG Data is not Matched", YesNo.Yes); sa.assertTrue(false,
	 * "SDG Data is not Matched : " + Data); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not Opened the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false, "Could not Opened the App Launcher"); } }
	 * else { log(LogStatus.ERROR, "Piclist is not deleted", YesNo.Yes);
	 * sa.assertTrue(false, "Piclist is not deleted"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Not Able to Click on Object and Feature name",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Not Able to Click on Object and Feature name"); } } else {
	 * log(LogStatus.ERROR, "Not Able to Search the Object", YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to Search the Object"); } } else {
	 * log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to open the setup page"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll();
	 * 
	 * }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc059_CreateNewOptionAndReplaceWithExistingValueVerifySDGDataOnAppPage(
	 * String projectName) {
	 * 
	 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver);
	 * FieldAndRelationshipPageBusinessLayer fr = new
	 * FieldAndRelationshipPageBusinessLayer(driver); String field = "Industry";
	 * String OptionValue = "Cargo"; String replaceValueName = "Education"; String
	 * appPage = M9Tc059_AppPageName; String tableName = M9Tc059_SDGTableName;
	 * 
	 * String[][] val = { { M9SDGD_118_AccountIndustry, M9SDGD_118_Totalfirm,
	 * M9SDGD_118_Task_as_per_Industries, M9SDGD_118_Individuals,
	 * M9SDGD_118_Fundraising_as_per_Industries }, { M9SDGD_119_AccountIndustry,
	 * M9SDGD_119_Totalfirm, M9SDGD_119_Task_as_per_Industries,
	 * M9SDGD_119_Individuals, M9SDGD_119_Fundraising_as_per_Industries }, {
	 * M9SDGD_120_AccountIndustry, M9SDGD_120_Totalfirm,
	 * M9SDGD_120_Task_as_per_Industries, M9SDGD_120_Individuals,
	 * M9SDGD_120_Fundraising_as_per_Industries }, { M9SDGD_121_AccountIndustry,
	 * M9SDGD_121_Totalfirm, M9SDGD_121_Task_as_per_Industries,
	 * M9SDGD_121_Individuals, M9SDGD_121_Fundraising_as_per_Industries }, {
	 * M9SDGD_122_AccountIndustry, M9SDGD_122_Totalfirm,
	 * M9SDGD_122_Task_as_per_Industries, M9SDGD_122_Individuals,
	 * M9SDGD_122_Fundraising_as_per_Industries }, { M9SDGD_123_AccountIndustry,
	 * M9SDGD_123_Totalfirm, M9SDGD_123_Task_as_per_Industries,
	 * M9SDGD_123_Individuals, M9SDGD_123_Fundraising_as_per_Industries }, {
	 * M9SDGD_124_AccountIndustry, M9SDGD_124_Totalfirm,
	 * M9SDGD_124_Task_as_per_Industries, M9SDGD_124_Individuals,
	 * M9SDGD_124_Fundraising_as_per_Industries }, { M9SDGD_125_AccountIndustry,
	 * M9SDGD_125_Totalfirm, M9SDGD_125_Task_as_per_Industries,
	 * M9SDGD_125_Individuals, M9SDGD_125_Fundraising_as_per_Industries }, {
	 * M9SDGD_126_AccountIndustry, M9SDGD_126_Totalfirm,
	 * M9SDGD_126_Task_as_per_Industries, M9SDGD_126_Individuals,
	 * M9SDGD_126_Fundraising_as_per_Industries }, { M9SDGD_127_AccountIndustry,
	 * M9SDGD_127_Totalfirm, M9SDGD_127_Task_as_per_Industries,
	 * M9SDGD_127_Individuals, M9SDGD_127_Fundraising_as_per_Industries }, {
	 * M9SDGD_128_AccountIndustry, M9SDGD_128_Totalfirm,
	 * M9SDGD_128_Task_as_per_Industries, M9SDGD_128_Individuals,
	 * M9SDGD_128_Fundraising_as_per_Industries }, { M9SDGD_129_AccountIndustry,
	 * M9SDGD_129_Totalfirm, M9SDGD_129_Task_as_per_Industries,
	 * M9SDGD_129_Individuals, M9SDGD_129_Fundraising_as_per_Industries }, {
	 * M9SDGD_130_AccountIndustry, M9SDGD_130_Totalfirm,
	 * M9SDGD_130_Task_as_per_Industries, M9SDGD_130_Individuals,
	 * M9SDGD_130_Fundraising_as_per_Industries }, { M9SDGD_131_AccountIndustry,
	 * M9SDGD_131_Totalfirm, M9SDGD_131_Task_as_per_Industries,
	 * M9SDGD_131_Individuals, M9SDGD_131_Fundraising_as_per_Industries }, {
	 * M9SDGD_132_AccountIndustry, M9SDGD_132_Totalfirm,
	 * M9SDGD_132_Task_as_per_Industries, M9SDGD_132_Individuals,
	 * M9SDGD_132_Fundraising_as_per_Industries }, { M9SDGD_133_AccountIndustry,
	 * M9SDGD_133_Totalfirm, M9SDGD_133_Task_as_per_Industries,
	 * M9SDGD_133_Individuals, M9SDGD_133_Fundraising_as_per_Industries }, {
	 * M9SDGD_134_AccountIndustry, M9SDGD_134_Totalfirm,
	 * M9SDGD_134_Task_as_per_Industries, M9SDGD_134_Individuals,
	 * M9SDGD_134_Fundraising_as_per_Industries }, { M9SDGD_135_AccountIndustry,
	 * M9SDGD_135_Totalfirm, M9SDGD_135_Task_as_per_Industries,
	 * M9SDGD_135_Individuals, M9SDGD_135_Fundraising_as_per_Industries } };
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (home.clickOnSetUpLink()) {
	 * 
	 * String parentWindowID = switchOnWindow(driver); if (parentWindowID == null) {
	 * sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); } if (setup.searchStandardOrCustomObject(projectName, mode, object.Firm))
	 * {
	 * 
	 * if (setup.clickOnObjectFeature(projectName, mode, object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip)) { if
	 * (fr.createPicklistValue(projectName, field, OptionValue)) { if
	 * (fr.replacePiclistOptionValue(projectName, OptionValue, replaceValueName)) {
	 * log(LogStatus.PASS, "Option value has been replaced", YesNo.No);
	 * driver.close(); driver.switchTo().window(parentWindowID);
	 * CommonLib.refresh(driver); lp.CRMlogout(); ThreadSleep(14000);
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) { ArrayList<String> Data =
	 * AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName,
	 * val); if (Data.isEmpty()) { log(LogStatus.PASS, "SDG Data has been Matched",
	 * YesNo.No); sa.assertTrue(true, "SDG Data has been Matched"); } else {
	 * log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
	 * sa.assertTrue(false, "SDG Data is not Matched : " + Data); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not Opened the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false, "Could not Opened the App Launcher"); } }
	 * else { log(LogStatus.ERROR, "Could not repalce the " + OptionValue +
	 * " value with the " + replaceValueName, YesNo.Yes); sa.assertTrue(false,
	 * "Could not repalce the " + OptionValue + " value with the " +
	 * replaceValueName); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not creare the option of the Piclist",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not creare the option of the Piclist"); } } else {
	 * log(LogStatus.ERROR, "Not Able to click on the Object Feature Name",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Not Able to click on the Object Feature Name"); } }
	 * 
	 * else { log(LogStatus.ERROR, "Not Able to Search the Object", YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to Search the Object"); } } else {
	 * log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to open the setup page"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll();
	 * 
	 * }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc060_AddSDGOnAppPage(String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * EditPageBusinessLayer EP = new EditPageBusinessLayer(driver); String appPage
	 * = M9Tc060_AppPageName;
	 * 
	 * ArrayList<String> tableNames = new ArrayList<String>(); String[] table =
	 * M9Tc060_SDGTableName.split("<BreakOn>"); for (int i = 0; i < table.length;
	 * i++) { tableNames.add(table[i]); }
	 * 
	 * String[] data = M9Tc060_SDGDataProvider.split("<BreakOn>");
	 * 
	 * ArrayList<String> dataProviderName = new ArrayList<String>(); for (int i = 0;
	 * i < data.length; i++) { dataProviderName.add(data[i]); }
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); for (int i = 0; i <
	 * dataProviderName.size(); i++) {
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { if
	 * (EP.editPageAndAddSDG(appPage, tableNames.get(i), dataProviderName.get(i))) {
	 * 
	 * } else { log(LogStatus.PASS, "App page has been edited and " +
	 * dataProviderName.get(i) + " has been added", YesNo.No); sa.assertTrue(true,
	 * "App page has been edited and " + dataProviderName.get(i) +
	 * " has been added"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not open the App from the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not open the App from the App Launcher"); } }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc061_VerifyNewlySDGDataOnAppPage(String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver);
	 * 
	 * String appPage = M9Tc061_AppPageName; String[] tableName =
	 * M9Tc061_SDGTableName.split("<BreakOn>"); String[][] SDG_GROUPBY_2_SDG = { {
	 * M9SDGD_136_Fundraising_Stage, M9SDGD_136_Count_as_per_fundraising_stage }, {
	 * M9SDGD_137_Fundraising_Stage, M9SDGD_137_Count_as_per_fundraising_stage }, {
	 * M9SDGD_138_Fundraising_Stage, M9SDGD_138_Count_as_per_fundraising_stage }, {
	 * M9SDGD_139_Fundraising_Stage, M9SDGD_139_Count_as_per_fundraising_stage }, {
	 * M9SDGD_140_Fundraising_Stage, M9SDGD_140_Count_as_per_fundraising_stage }, {
	 * M9SDGD_141_Fundraising_Stage, M9SDGD_141_Count_as_per_fundraising_stage }, {
	 * M9SDGD_142_Fundraising_Stage, M9SDGD_142_Count_as_per_fundraising_stage }, {
	 * M9SDGD_143_Fundraising_Stage, M9SDGD_143_Count_as_per_fundraising_stage } };
	 * 
	 * String[][] SDG_GROUPBY_3_SDG = { { M9SDGD_144_Fundraising,
	 * M9SDGD_144_Fundraising_Count }, { M9SDGD_145_Fundraising,
	 * M9SDGD_145_Fundraising_Count } };
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { ArrayList<String> Data =
	 * AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName[0],
	 * SDG_GROUPBY_2_SDG); if (Data.isEmpty()) { log(LogStatus.PASS,
	 * "SDG Data has been Matched", YesNo.No); sa.assertTrue(true,
	 * "SDG Data has been Matched"); } else { log(LogStatus.ERROR,
	 * "SDG Data is not Matched", YesNo.Yes); sa.assertTrue(false,
	 * "SDG Data is not Matched : " + Data); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not Opened the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false, "Could not Opened the App Launcher"); }
	 * 
	 * lp.CRMlogout(); ThreadSleep(14000); lp.CRMLogin(superAdminUserName,
	 * adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { ArrayList<String> Data =
	 * AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName[1],
	 * SDG_GROUPBY_3_SDG); if (Data.isEmpty()) { log(LogStatus.PASS,
	 * "SDG Data has been Matched", YesNo.No); sa.assertTrue(true,
	 * "SDG Data has been Matched"); } else { log(LogStatus.ERROR,
	 * "SDG Data is not Matched", YesNo.Yes); sa.assertTrue(false,
	 * "SDG Data is not Matched : " + Data); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not Opened the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false, "Could not Opened the App Launcher"); }
	 * 
	 * lp.CRMlogout();
	 * 
	 * sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc062_VerifyPermissionAddFieldToLayoutAndEditField(String
	 * projectName) { SetupPageBusinessLayer setup = new
	 * SetupPageBusinessLayer(driver); LoginPageBusinessLayer lp = new
	 * LoginPageBusinessLayer(driver); HomePageBusineesLayer home = new
	 * HomePageBusineesLayer(driver); DataLoaderWizardPageBusinessLayer dataload =
	 * new DataLoaderWizardPageBusinessLayer(driver); String parentWindow = "";
	 * 
	 * String data; lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (home.clickOnSetUpLink()) { parentWindow = switchOnWindow(driver); if
	 * (parentWindow == null) { sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create Field Set Component"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create Field Set Component"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component"
	 * ); } ThreadSleep(3000);
	 * 
	 * data = M9AFTPL_13_FieldNames;
	 * 
	 * String[] fields = data.split(",");
	 * 
	 * ArrayList<String> userEmail = new ArrayList<String>(); ArrayList<String>
	 * adminEmail = new ArrayList<String>();
	 * 
	 * userEmail.add(crmUser1EmailID); userEmail.add(crmUser2EmailID);
	 * 
	 * adminEmail.add(superAdminUserName);
	 * 
	 * ArrayList<String> columns = new ArrayList<String>(); for (String column :
	 * fields) { columns.add(column); }
	 * 
	 * List<String> layoutName = new ArrayList<String>();
	 * layoutName.add(M9AFTPL_13_PageLayoutName); HashMap<String, String>
	 * sourceANDDestination = new HashMap<String, String>(); for (String column :
	 * columns) { sourceANDDestination.put(column, "");
	 * 
	 * } if (dataload.addFieldToLayoutPage("", mode, layoutName,
	 * sourceANDDestination, object.User)) {
	 * CommonLib.switchToDefaultContent(driver); ThreadSleep(2000); if
	 * (setup.giveAndRemoveObjectPermissionFromObjectManager(object.User,
	 * ObjectFeatureName.FieldAndRelationShip, "Team",
	 * PermissionType.givePermission, "System Administrator")) { log(LogStatus.INFO,
	 * "Permission given from the User Object", YesNo.No);
	 * 
	 * for (String email : userEmail) { CommonLib.switchToDefaultContent(driver);
	 * ThreadSleep(2000); CommonLib.refresh(driver); if (setup.EditPEUser(email,
	 * "Team", HTMLTAG.select, "Origination")) { log(LogStatus.INFO,
	 * "Origination has been added in the Team for user :" + email, YesNo.No);
	 * sa.assertTrue(true, "Origination has been added in the Team for user :" +
	 * email); } else { log(LogStatus.ERROR,
	 * "Origination is not added in the Team field for user :" + email, YesNo.Yes);
	 * sa.assertTrue(false, "Origination is not added in the Team field for user :"
	 * + email); } } for (String email : adminEmail) {
	 * CommonLib.switchToDefaultContent(driver); ThreadSleep(2000);
	 * CommonLib.refresh(driver); if (setup.EditPEUser(email, "Team",
	 * HTMLTAG.select, "Portfolio")) { log(LogStatus.INFO,
	 * "Portfolio has been added in the Team for user :" + email, YesNo.No);
	 * sa.assertTrue(true, "Portfolio has been added in the Team for user :" +
	 * email); } else { log(LogStatus.ERROR,
	 * "Portfolio is not added in the Team field for user :" + email, YesNo.Yes);
	 * sa.assertTrue(false, "Portfolio is not added in the Team field for user :" +
	 * email); } }
	 * 
	 * } else { log(LogStatus.ERROR, "Not able to give the permission", YesNo.Yes);
	 * sa.assertTrue(false, "Not able to give the permission"); } } else {
	 * log(LogStatus.ERROR, "Not able to add the field on the Layout", YesNo.Yes);
	 * sa.assertTrue(false, "Not able to add the field on the Layout"); } }
	 * driver.close(); driver.switchTo().window(parentWindow); lp.CRMlogout();
	 * sa.assertAll();
	 * 
	 * }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc063_CreateAppPageAndAddSDGs(String projectName) {
	 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); String labelName =
	 * "Custom App Page";
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName);
	 * 
	 * String[] dataProvider = M9Tc063_SDGDataProvider.split("<BreakOn>");
	 * ArrayList<String> dataProviderName = new ArrayList<String>(); for (int i = 0;
	 * i < dataProvider.length; i++) { dataProviderName.add(dataProvider[i]); }
	 * 
	 * String[] name = M9Tc063_SDGTableName.split("<BreakOn>");
	 * 
	 * ArrayList<String> tableName = new ArrayList<String>(); for (int i = 0; i <
	 * name.length; i++) { tableName.add(name[i]); } if (home.clickOnSetUpLink()) {
	 * 
	 * String parentWindowID = switchOnWindow(driver); if (parentWindowID == null) {
	 * sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create App Page"
	 * ); }
	 * 
	 * if (setup.searchStandardOrCustomObject(projectName, mode,
	 * object.Lightning_App_Builder)) { if (AppBuilder.CreateAppPage(projectName,
	 * mode, labelName, tableName, dataProviderName, parentWindowID)) {
	 * log(LogStatus.PASS, "App Page has been Created : " + labelName, YesNo.Yes);
	 * sa.assertTrue(true, "App Page has been Created : " + labelName); } else {
	 * log(LogStatus.ERROR, "App Page is not created : " + labelName, YesNo.Yes);
	 * sa.assertTrue(false, "App Page is not created : " + labelName); } } else {
	 * log(LogStatus.ERROR, "Not able to search the Object", YesNo.Yes);
	 * sa.assertTrue(false, "Not able to search the Object"); }
	 * 
	 * }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc064_EditAppPageAndAddFilter(String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * EditPageBusinessLayer EP = new EditPageBusinessLayer(driver); String appPage
	 * = M9Tc064_AppPageName; lp.CRMLogin(superAdminUserName, adminPassword,
	 * appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { if
	 * (EP.editPageAndAddFilter("Fund",
	 * "Select Name from navpeII__Fund__c ORDER BY Name ASC", "Firm",
	 * "Select Name from Account where RecordType.Name = 'Institution' ORDER BY Name ASC"
	 * , "", "", Condition.UnSelectCheckbox)) { log(LogStatus.PASS,
	 * "Filter has been added on the page", YesNo.Yes); sa.assertTrue(true,
	 * "Filter has been added on the page"); } else { log(LogStatus.ERROR,
	 * "Could not edit the Page and add the Filter", YesNo.Yes);
	 * sa.assertTrue(false, "Could not edit the Page and add the SDG"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not open the App from the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not open the App from the App Launcher"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc065_VerifyFundAndFirmDropDownCountAndAcendingOrder(String projectName) {
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver);
	 * 
	 * String appPage = M9Tc065_AppPageName; lp.CRMLogin(crmUser1EmailID,
	 * adminPassword, appName); ArrayList<String> labelName = new
	 * ArrayList<String>(); labelName.add("Fund"); labelName.add("Firm");
	 * 
	 * ArrayList<Integer> count = new ArrayList<Integer>(); count.add(84);
	 * count.add(34);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { log(LogStatus.INFO,
	 * "App page has been open", YesNo.No);
	 * 
	 * if (AppBuilder.VerifyDropdownCountandAscendingOrder(labelName, count)) {
	 * log(LogStatus.INFO, "Dropdown count and Ascending order has been matched",
	 * YesNo.No); sa.assertTrue(true,
	 * "Dropdown count and Ascending order has been matched");
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Dropdown count and Ascending order is not matched ", YesNo.Yes);
	 * sa.assertTrue(false, "Dropdown count and Ascending order is not matched "); }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Could not open the App page from the App Launcher", YesNo.No); }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc066_VerifyGlobalFilterForFundAndFirm(String
	 * projectName) { LoginPageBusinessLayer lp = new
	 * LoginPageBusinessLayer(driver); BasePageBusinessLayer BP = new
	 * BasePageBusinessLayer(driver); LightningAppBuilderPageBusinessLayer
	 * AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	 * 
	 * String appPage = M9Tc066_AppPageName; String pageSize = "100"; String[] table
	 * = M9Tc066_SDGTableName.split("<BreakOn>"); String fundSDGName = table[2];
	 * String accountSDGName = table[0]; String fundraisingSDGName = table[3];
	 * String contactSDGName = table[1];
	 * 
	 * int fundRowCountBeforeFilter, fundraisingRowCountBeforeFilter,
	 * accountFilterRowCountBeforeFilter, contactFilterRowCountBeforeFilter; int
	 * fundRowCountAfterFilter, fundraisingRowCountAfterFilter,
	 * accountFilterRowCountAfterFilter, contactFilterRowCountAfterFilter;
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * fundRowCountBeforeFilter = AppBuilder.numberOfRecords(fundSDGName, pageSize);
	 * accountFilterRowCountBeforeFilter =
	 * AppBuilder.numberOfRecords(accountSDGName, pageSize);
	 * fundraisingRowCountBeforeFilter =
	 * AppBuilder.numberOfRecords(fundraisingSDGName, pageSize);
	 * contactFilterRowCountBeforeFilter =
	 * AppBuilder.numberOfRecords(contactSDGName, pageSize);
	 * 
	 * if (fundRowCountBeforeFilter != 0 && accountFilterRowCountBeforeFilter != 0
	 * && fundraisingRowCountBeforeFilter != 0 && contactFilterRowCountBeforeFilter
	 * != 0) { log(LogStatus.INFO, "The row count before filter of FundSDG : " +
	 * fundRowCountBeforeFilter + " \n AccountSDG : " +
	 * accountFilterRowCountBeforeFilter + " \nFundraising : " +
	 * fundraisingRowCountBeforeFilter + " \n Contact : " +
	 * contactFilterRowCountBeforeFilter, YesNo.No);
	 * 
	 * if (AppBuilder.selectFilter("Fund", "Centrient Pharmaceuticals -2018")) {
	 * log(LogStatus.INFO, "Filter has been selected of the fund", YesNo.No);
	 * CommonLib.ThreadSleep(8000);
	 * 
	 * fundRowCountAfterFilter = AppBuilder.numberOfRecords(fundSDGName, pageSize);
	 * accountFilterRowCountAfterFilter = AppBuilder.numberOfRecords(accountSDGName,
	 * pageSize); fundraisingRowCountAfterFilter =
	 * AppBuilder.numberOfRecords(fundraisingSDGName, pageSize);
	 * contactFilterRowCountAfterFilter = AppBuilder.numberOfRecords(contactSDGName,
	 * pageSize);
	 * 
	 * if (fundRowCountAfterFilter != 0 && accountFilterRowCountAfterFilter != 0 &&
	 * fundraisingRowCountAfterFilter != 0 && contactFilterRowCountAfterFilter != 0)
	 * { log(LogStatus.INFO, "The row count before filter of FundSDG : " +
	 * fundRowCountAfterFilter + " \n AccountSDG : " +
	 * accountFilterRowCountAfterFilter + " \nFundraising : " +
	 * fundraisingRowCountAfterFilter + " \n Contact : " +
	 * contactFilterRowCountAfterFilter, YesNo.No);
	 * 
	 * if (fundRowCountBeforeFilter == fundRowCountAfterFilter &&
	 * accountFilterRowCountBeforeFilter == accountFilterRowCountAfterFilter &&
	 * fundraisingRowCountBeforeFilter == fundraisingRowCountAfterFilter &&
	 * contactFilterRowCountBeforeFilter == contactFilterRowCountAfterFilter) {
	 * log(LogStatus.PASS,
	 * "The count of row has been matched.  The row count before filter of FundSDG : "
	 * + fundRowCountBeforeFilter + " \n AccountSDG : " +
	 * accountFilterRowCountBeforeFilter + " \nFundraising : " +
	 * fundraisingRowCountBeforeFilter + " \n Contact : " +
	 * contactFilterRowCountBeforeFilter +
	 * " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter +
	 * " \n AccountSDG : " + accountFilterRowCountAfterFilter + " \nFundraising : "
	 * + fundraisingRowCountAfterFilter + " \n Contact : " +
	 * contactFilterRowCountAfterFilter + "", YesNo.No); sa.assertTrue(true,
	 * "The count of row has been matched. The row count before filter of FundSDG : "
	 * + fundRowCountBeforeFilter + " \n AccountSDG : " +
	 * accountFilterRowCountBeforeFilter + " \nFundraising : " +
	 * fundraisingRowCountBeforeFilter + " \n Contact : " +
	 * contactFilterRowCountBeforeFilter +
	 * " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter +
	 * " \n AccountSDG : " + accountFilterRowCountAfterFilter + " \nFundraising : "
	 * + fundraisingRowCountAfterFilter + " \n Contact : " +
	 * contactFilterRowCountAfterFilter + ""); } else { log(LogStatus.FAIL,
	 * "The count of row is not matched. The row count before filter of FundSDG : "
	 * + fundRowCountBeforeFilter + " \n AccountSDG : " +
	 * accountFilterRowCountBeforeFilter + " \nFundraising : " +
	 * fundraisingRowCountBeforeFilter + " \n Contact : " +
	 * contactFilterRowCountBeforeFilter +
	 * " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter +
	 * " \n AccountSDG : " + accountFilterRowCountAfterFilter + " \nFundraising : "
	 * + fundraisingRowCountAfterFilter + " \n Contact : " +
	 * contactFilterRowCountAfterFilter + "", YesNo.Yes); sa.assertTrue(false,
	 * "The count of row is not matched. The row count before filter of FundSDG : "
	 * + fundRowCountBeforeFilter + " \n AccountSDG : " +
	 * accountFilterRowCountBeforeFilter + " \nFundraising : " +
	 * fundraisingRowCountBeforeFilter + " \n Contact : " +
	 * contactFilterRowCountBeforeFilter +
	 * " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter +
	 * " \n AccountSDG : " + accountFilterRowCountAfterFilter + " \nFundraising : "
	 * + fundraisingRowCountAfterFilter + " \n Contact : " +
	 * contactFilterRowCountAfterFilter + ""); } } else { log(LogStatus.ERROR,
	 * "Could not get the count of row ", YesNo.No); sa.assertTrue(false,
	 * "Could not get the count of row "); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not Select the filter", YesNo.No);
	 * sa.assertTrue(false, "Could not Select the filter"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not get the count of row ", YesNo.No);
	 * sa.assertTrue(false, "Could not get the count of row "); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not open the app from the App Launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); }
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * fundRowCountBeforeFilter = AppBuilder.numberOfRecords(fundSDGName, pageSize);
	 * accountFilterRowCountBeforeFilter =
	 * AppBuilder.numberOfRecords(accountSDGName, pageSize);
	 * fundraisingRowCountBeforeFilter =
	 * AppBuilder.numberOfRecords(fundraisingSDGName, pageSize);
	 * contactFilterRowCountBeforeFilter =
	 * AppBuilder.numberOfRecords(contactSDGName, pageSize);
	 * 
	 * if (fundRowCountBeforeFilter != 0 && accountFilterRowCountBeforeFilter != 0
	 * && fundraisingRowCountBeforeFilter != 0 && contactFilterRowCountBeforeFilter
	 * != 0) { log(LogStatus.INFO, "The row count before filter of FundSDG : " +
	 * fundRowCountBeforeFilter + " \n AccountSDG : " +
	 * accountFilterRowCountBeforeFilter + " \nFundraising : " +
	 * fundraisingRowCountBeforeFilter + " \n Contact : " +
	 * contactFilterRowCountBeforeFilter, YesNo.No);
	 * 
	 * if (AppBuilder.selectFilter("Firm", "Appfluent Technology, Inc.")) {
	 * log(LogStatus.INFO, "Filter has been selected of the Firm", YesNo.No);
	 * CommonLib.ThreadSleep(8000);
	 * 
	 * fundRowCountAfterFilter = AppBuilder.numberOfRecords(fundSDGName, pageSize);
	 * accountFilterRowCountAfterFilter = AppBuilder.numberOfRecords(accountSDGName,
	 * pageSize); fundraisingRowCountAfterFilter =
	 * AppBuilder.numberOfRecords(fundraisingSDGName, pageSize);
	 * contactFilterRowCountAfterFilter = AppBuilder.numberOfRecords(contactSDGName,
	 * pageSize);
	 * 
	 * if (fundRowCountAfterFilter != 0 && accountFilterRowCountAfterFilter != 0 &&
	 * fundraisingRowCountAfterFilter != 0 && contactFilterRowCountAfterFilter != 0)
	 * { log(LogStatus.INFO, "The row count After filter of FundSDG : " +
	 * fundRowCountAfterFilter + " \n AccountSDG : " +
	 * accountFilterRowCountAfterFilter + " \nFundraising : " +
	 * fundraisingRowCountAfterFilter + " \n Contact : " +
	 * contactFilterRowCountAfterFilter, YesNo.No);
	 * 
	 * if (fundRowCountBeforeFilter == fundRowCountAfterFilter &&
	 * accountFilterRowCountBeforeFilter == accountFilterRowCountAfterFilter &&
	 * fundraisingRowCountBeforeFilter == fundraisingRowCountAfterFilter &&
	 * contactFilterRowCountBeforeFilter == contactFilterRowCountAfterFilter) {
	 * log(LogStatus.PASS,
	 * "The row count has been matched. The row count before filter of FundSDG : " +
	 * fundRowCountBeforeFilter + " \n AccountSDG : " +
	 * accountFilterRowCountBeforeFilter + " \nFundraising : " +
	 * fundraisingRowCountBeforeFilter + " \n Contact : " +
	 * contactFilterRowCountBeforeFilter +
	 * " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter +
	 * " \n AccountSDG : " + accountFilterRowCountAfterFilter + " \nFundraising : "
	 * + fundraisingRowCountAfterFilter + " \n Contact : " +
	 * contactFilterRowCountAfterFilter + "", YesNo.No); sa.assertTrue(true,
	 * "The count of has been matched. The row count before filter of FundSDG : " +
	 * fundRowCountBeforeFilter + " \n AccountSDG : " +
	 * accountFilterRowCountBeforeFilter + " \nFundraising : " +
	 * fundraisingRowCountBeforeFilter + " \n Contact : " +
	 * contactFilterRowCountBeforeFilter +
	 * " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter +
	 * " \n AccountSDG : " + accountFilterRowCountAfterFilter + " \nFundraising : "
	 * + fundraisingRowCountAfterFilter + " \n Contact : " +
	 * contactFilterRowCountAfterFilter + ""); } else { log(LogStatus.FAIL,
	 * "Row count is not matched. The row count before filter of FundSDG : " +
	 * fundRowCountBeforeFilter + " \n AccountSDG : " +
	 * accountFilterRowCountBeforeFilter + " \nFundraising : " +
	 * fundraisingRowCountBeforeFilter + " \n Contact : " +
	 * contactFilterRowCountBeforeFilter +
	 * " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter +
	 * " \n AccountSDG : " + accountFilterRowCountAfterFilter + " \nFundraising : "
	 * + fundraisingRowCountAfterFilter + " \n Contact : " +
	 * contactFilterRowCountAfterFilter + "", YesNo.Yes); sa.assertTrue(false,
	 * "Row count is not mathced. The row count before filter of FundSDG : " +
	 * fundRowCountBeforeFilter + " \n AccountSDG : " +
	 * accountFilterRowCountBeforeFilter + " \nFundraising : " +
	 * fundraisingRowCountBeforeFilter + " \n Contact : " +
	 * contactFilterRowCountBeforeFilter +
	 * " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter +
	 * " \n AccountSDG : " + accountFilterRowCountAfterFilter + " \nFundraising : "
	 * + fundraisingRowCountAfterFilter + " \n Contact : " +
	 * contactFilterRowCountAfterFilter + ""); } } else { log(LogStatus.ERROR,
	 * "Could not get the count of row ", YesNo.No); sa.assertTrue(false,
	 * "Could not get the count of row "); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not Select the filter", YesNo.No);
	 * sa.assertTrue(false, "Could not Select the filter"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not get the count of row ", YesNo.No);
	 * sa.assertTrue(false, "Could not get the count of row "); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not open the app from the App Launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc067_AddFilterSequenceAndVerifyRecord(String
	 * projectName) { LoginPageBusinessLayer lp = new
	 * LoginPageBusinessLayer(driver); BasePageBusinessLayer BP = new
	 * BasePageBusinessLayer(driver); LightningAppBuilderPageBusinessLayer
	 * AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver); String appPage =
	 * M9Tc067_AppPageName; String[] table =
	 * M9Tc067_SDGTableName.split("<BreakOn>"); String fundraisingSDGName =
	 * table[1]; String fundSDGName = table[0]; int fundraisingRowCountBeforeFilter,
	 * fundraisingRowCountAfterFilter; String pageSize = "100"; int status = 0;
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(60, "Sortable Data Grids")) { if
	 * (SB.sequenceFilter(projectName, fundSDGName, "Name", "1")) {
	 * log(LogStatus.INFO, "Sequence Filter has been saved", YesNo.No);
	 * sa.assertTrue(true, "Sequence Filter has been saved"); lp.CRMlogout();
	 * ThreadSleep(14000); lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) { fundraisingRowCountBeforeFilter =
	 * AppBuilder.numberOfRecords(fundraisingSDGName, pageSize); if
	 * (fundraisingRowCountBeforeFilter != 0) { log(LogStatus.INFO,
	 * "The fundraising count before filter is : " +
	 * fundraisingRowCountBeforeFilter, YesNo.No); } else { log(LogStatus.ERROR,
	 * "Could not open the App page", YesNo.No); sa.assertTrue(false,
	 * "Could not get the Fundraising Count"); } if (AppBuilder.selectFilter("Fund",
	 * "Centrient Pharmaceuticals -2018")) { log(LogStatus.INFO,
	 * "Filter has been selected from the fund", YesNo.No);
	 * CommonLib.ThreadSleep(8000); fundraisingRowCountAfterFilter =
	 * AppBuilder.numberOfRecords(fundraisingSDGName, pageSize); if
	 * (fundraisingRowCountAfterFilter == fundraisingRowCountBeforeFilter) {
	 * log(LogStatus.PASS, "Fundraising count is not filtered", YesNo.No);
	 * sa.assertTrue(true, "Fundraising count is not filtered"); } else {
	 * log(LogStatus.ERROR, "Fundraising count is filtered", YesNo.No);
	 * sa.assertTrue(false, "Fundraising count is filtered"); }
	 * CommonLib.ThreadSleep(4000); String xpath =
	 * "//a[text()='Fund Filter Grid']/ancestor::div[contains(@class,'slds-card__header')]/following-sibling::div//tbody//td[@data-label='Fund: ']"
	 * ; ArrayList<String> list = SB.getListText(xpath, "Fund SDG"); if (list !=
	 * null) { for (int i = 0; i < list.size(); i++) { if
	 * (list.get(i).equals("Centrient Pharmaceuticals -2018")) { log(LogStatus.INFO,
	 * list.get(i) + " has been matched", YesNo.No);
	 * 
	 * } else { log(LogStatus.ERROR, list.get(i) +
	 * " is not matched with the Centrient Pharmaceuticals -2018", YesNo.No);
	 * status++; } }
	 * 
	 * if (status == 0) { log(LogStatus.PASS,
	 * "Sequence filter has been Applied Fund is matched", YesNo.No);
	 * sa.assertTrue(true, "Sequence filter has been Applied Fund is matched"); }
	 * else { log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Fund data is not matched", YesNo.Yes);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Fund data is not matched"); }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Could not get the data from the Fund SDG after applying filter", YesNo.No);
	 * status++; sa.assertTrue(false,
	 * "Could not get the data from the Fund SDG after applying filter"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not select the filter from the fund",
	 * YesNo.No); sa.assertTrue(true, "Could not select the filter from the fund");
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not open the App page", YesNo.No);
	 * sa.assertTrue(true, "Could not open the App page"); } } else {
	 * log(LogStatus.ERROR, "Could not open the App page", YesNo.No);
	 * sa.assertTrue(false, "Could not open the App page"); }
	 * 
	 * } lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc068_AddFilterSequenceForFundAndVerifyRecord(String
	 * projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); SDGPageBusinessLayer SB = new
	 * SDGPageBusinessLayer(driver); String appPage = M9Tc068_AppPageName; String[]
	 * table = M9Tc068_SDGTableName.split("<BreakOn>"); String fundraisingSDGName =
	 * table[1]; String filterOptionValue = "Appfluent Technology"; String
	 * filterOptionValue1 = "Sumo Logic - Nov 2017"; String fundSDGName = table[0];
	 * String pageSize = "100"; int fundRecordCountBeforeFilter,
	 * fundRecordCountAfterFilter, fundraisingRecordCountAfterFilter,
	 * fundraisingRecordCountBeforeFilter; lp.CRMLogin(superAdminUserName,
	 * adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(60, "Sortable Data Grids")) {
	 * 
	 * if (SB.sequenceFilter(projectName, "Fundraising Filter Grid", "Fund Name",
	 * "1")) { log(LogStatus.INFO, "Sequence Filter has been saved", YesNo.No);
	 * sa.assertTrue(true, "Sequence Filter has been saved");
	 * 
	 * lp.CRMlogout(); ThreadSleep(14000); lp.CRMLogin(crmUser1EmailID,
	 * adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * if (AppBuilder.selectFilter("Fund", filterOptionValue)) { log(LogStatus.INFO,
	 * "Filter has been selected from the fund", YesNo.No);
	 * CommonLib.ThreadSleep(8000); if
	 * (!SB.verifyRecordExistOrNotOnSDG(fundraisingSDGName, "Fundraising SDG")) { if
	 * (SB.verifyRecordAfterApplyingGlobalFilter(fundraisingSDGName,
	 * filterOptionValue, "Fund Name", "Fundraising SDG")) { log(LogStatus.PASS,
	 * "Sequence filter has been Applied Records are matched", YesNo.No);
	 * sa.assertTrue(true, "Sequence filter has been Applied Records are matched");
	 * } else { log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Record is not matched", YesNo.Yes);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Record is not matched"); } } else {
	 * log(LogStatus.PASS,
	 * "Record is not persent against Appfluent Technology Filter on Fund But Filter has been applied successfully"
	 * , YesNo.Yes); sa.assertTrue(true,
	 * "Record is not persent against Appfluent Technology Filter on Fund But Filter has been applied successfully"
	 * );
	 * 
	 * }
	 * 
	 * if (!SB.verifyRecordExistOrNotOnSDG(fundSDGName, "Fund SDG")) { if
	 * (SB.verifyRecordAfterApplyingGlobalFilter(fundSDGName, filterOptionValue,
	 * "Fund", "Fund SDG")) { log(LogStatus.PASS,
	 * "Sequence filter has been Applied Records are matched", YesNo.No);
	 * sa.assertTrue(true, "Sequence filter has been Applied Records are matched");
	 * } else { log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Record is not matched", YesNo.Yes);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Record is not matched"); } } else {
	 * log(LogStatus.PASS,
	 * "Record is not persent against Appfluent Technology Filter on Fund But Filter has been applied successfully"
	 * , YesNo.Yes); sa.assertTrue(true,
	 * "Record is not persent against Appfluent Technology Filter on Fund But Filter has been applied successfully"
	 * );
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not select the filter", YesNo.Yes);
	 * sa.assertTrue(false, "Could not select the filter"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not open the app from the app launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not open the app from the app launcher"); }
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { CommonLib.refresh(driver); if
	 * (AppBuilder.selectFilter("Fund", filterOptionValue1)) { log(LogStatus.INFO,
	 * "Filter has been selected from the fund", YesNo.No);
	 * CommonLib.ThreadSleep(8000); if
	 * (!SB.verifyRecordExistOrNotOnSDG(fundraisingSDGName, "Fundraising SDG")) { if
	 * (SB.verifyRecordAfterApplyingGlobalFilter(fundraisingSDGName,
	 * filterOptionValue1, "Fund Name", "Fundraising SDG")) { log(LogStatus.PASS,
	 * "Sequence filter has been Applied Records are matched", YesNo.No);
	 * sa.assertTrue(true, "Sequence filter has been Applied Records are matched");
	 * } else { log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Record is not matched", YesNo.Yes);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Record is not matched"); } } else {
	 * log(LogStatus.PASS, "Record is not persent against " + filterOptionValue1 +
	 * " Filter on Fund But Filter has been applied successfully", YesNo.No);
	 * sa.assertTrue(true, "Record is not persent against " + filterOptionValue1 +
	 * " Filter on Fund But Filter has been applied successfully");
	 * 
	 * }
	 * 
	 * if (!SB.verifyRecordExistOrNotOnSDG(fundSDGName, "Fund SDG")) { if
	 * (SB.verifyRecordAfterApplyingGlobalFilter(fundSDGName, filterOptionValue1,
	 * "Fund", "Fund SDG")) { log(LogStatus.PASS,
	 * "Sequence filter has been Applied Records are matched", YesNo.No);
	 * sa.assertTrue(true, "Sequence filter has been Applied Records are matched");
	 * } else { log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Record is not matched", YesNo.Yes);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Record is not matched"); } } else {
	 * log(LogStatus.PASS, "Record is not persent against " + filterOptionValue1 +
	 * " Filter on Fund But Filter has been applied successfully", YesNo.Yes);
	 * sa.assertTrue(true, "Record is not persent against " + filterOptionValue1 +
	 * " Filter on Fund But Filter has been applied successfully");
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not select the filter", YesNo.Yes);
	 * sa.assertTrue(false, "Could not select the filter"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not open the app from the app launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not open the app from the app launcher"); }
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { CommonLib.refresh(driver);
	 * fundRecordCountBeforeFilter = AppBuilder.numberOfRecords(fundSDGName,
	 * pageSize); fundraisingRecordCountBeforeFilter =
	 * AppBuilder.numberOfRecords(fundraisingSDGName, pageSize); if
	 * (AppBuilder.selectFilter("Fund", "All")) { log(LogStatus.INFO,
	 * "Filter has been selected from the fund", YesNo.No);
	 * CommonLib.ThreadSleep(8000); fundRecordCountAfterFilter =
	 * AppBuilder.numberOfRecords(fundSDGName, pageSize);
	 * fundraisingRecordCountAfterFilter =
	 * AppBuilder.numberOfRecords(fundraisingSDGName, pageSize);
	 * 
	 * if (fundRecordCountBeforeFilter == fundRecordCountAfterFilter &&
	 * fundraisingRecordCountBeforeFilter == fundraisingRecordCountAfterFilter) {
	 * 
	 * log(LogStatus.PASS, "Records are matched After Selecting All from the Fund",
	 * YesNo.No); sa.assertTrue(true,
	 * "Records are matched After Selecting All from the Fund"); } else {
	 * log(LogStatus.ERROR,
	 * "Records are not matched After Selecting All from the Fund", YesNo.Yes);
	 * sa.assertTrue(false,
	 * "Records are not matched After Selecting All from the Fund"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not select the filter", YesNo.Yes);
	 * sa.assertTrue(false, "Could not select the filter"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not open the app from the app launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not open the app from the app launcher"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Sequence Filter is not saved", YesNo.Yes);
	 * sa.assertTrue(false, "Sequence Filter is not saved"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not open the app from the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc069_AddFilterSequenceForAccountAndContactVerifyRecord(String projectName)
	 * {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); SDGPageBusinessLayer SB = new
	 * SDGPageBusinessLayer(driver); String appPage = M9Tc069_AppPageName; String[]
	 * table = M9Tc069_SDGTableName.split("<BreakOn>"); String accountSDGName =
	 * table[0]; String contactSDGName = table[1]; int status = 0,
	 * fundraisingRowCountBeforeFilter, fundraisingRowCountAfterFilter; String
	 * fundraisingSDGName = table[2]; lp.CRMLogin(superAdminUserName, adminPassword,
	 * appName);
	 * 
	 * if (BP.openAppFromAppLauchner(60, "Sortable Data Grids")) { if
	 * (SB.sequenceFilter(projectName, accountSDGName, "Account Name", "2")) {
	 * log(LogStatus.INFO, "Sequence Filter has been saved", YesNo.No);
	 * sa.assertTrue(true, "Sequence Filter has been saved"); } else {
	 * log(LogStatus.ERROR, "Sequence Filter is not saved", YesNo.No);
	 * sa.assertTrue(false, "Sequence Filter is not saved"); } } else {
	 * log(LogStatus.ERROR, "Could not open the app from the App Launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); }
	 * 
	 * if (BP.openAppFromAppLauchner(60, "Sortable Data Grids")) {
	 * CommonLib.refresh(driver); if (SB.sequenceFilter(projectName, contactSDGName,
	 * "Contact Name", "2")) { log(LogStatus.INFO, "Sequence Filter has been saved",
	 * YesNo.No); sa.assertTrue(true, "Sequence Filter has been saved"); } else {
	 * log(LogStatus.ERROR, "Sequence Filter is not saved", YesNo.No);
	 * sa.assertTrue(false, "Sequence Filter is not saved"); status++; } } else {
	 * log(LogStatus.ERROR, "Could not open the app from the App Launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); status++; }
	 * 
	 * if (status == 0) { lp.CRMlogout(); ThreadSleep(14000);
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) { fundraisingRowCountBeforeFilter =
	 * AppBuilder.numberOfRecords(fundraisingSDGName, "100"); if
	 * (AppBuilder.selectFilter("Firm", "AIG Group")) { log(LogStatus.INFO,
	 * "Filter has been selected from the firm", YesNo.No);
	 * CommonLib.ThreadSleep(8000); if
	 * (!SB.verifyRecordExistOrNotOnSDG(accountSDGName, "Account SDG")) { if
	 * (SB.verifyRecordAfterApplyingGlobalFilter(accountSDGName, "AIG Group",
	 * "Account Name", "Account SDG")) { log(LogStatus.PASS,
	 * "Sequence filter has been Applied Records are matched", YesNo.No);
	 * sa.assertTrue(true, "Sequence filter has been Applied Records are matched");
	 * } else { log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Record is not matched", YesNo.No);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Record is not matched"); } } else {
	 * log(LogStatus.PASS,
	 * "Record is not persent against AIG Group Filter on Fund But Filter has been applied successfully"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Record is not persent against AIG Group Filter on Fund But Filter has been applied successfully"
	 * );
	 * 
	 * }
	 * 
	 * if (!SB.verifyRecordExistOrNotOnSDG(contactSDGName, "Contact SDG")) { if
	 * (SB.verifyRecordAfterApplyingGlobalFilter(contactSDGName, "AIG Group",
	 * "Contact Name", "Contact SDG")) { log(LogStatus.PASS,
	 * "Sequence filter has been Applied Records are matched", YesNo.No);
	 * sa.assertTrue(true, "Sequence filter has been Applied Records are matched");
	 * } else { log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Record is not matched", YesNo.No);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Record is not matched"); } } else {
	 * log(LogStatus.PASS,
	 * "Record is not persent against AIG Group Filter on Fund But Filter has been applied successfully"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Record is not persent against AIG Group Filter on Fund But Filter has been applied successfully"
	 * );
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not select the filter", YesNo.No);
	 * sa.assertTrue(false, "Could not select the filter"); }
	 * CommonLib.refresh(driver); if (AppBuilder.selectFilter("Firm",
	 * "Applied Systems, Inc.")) { log(LogStatus.INFO,
	 * "Filter has been selected from the firm", YesNo.No);
	 * CommonLib.ThreadSleep(8000); if
	 * (!SB.verifyRecordExistOrNotOnSDG(accountSDGName, "Account SDG")) { if
	 * (SB.verifyRecordAfterApplyingGlobalFilter(accountSDGName,
	 * "Applied Systems, Inc.", "Account Name", "Account SDG")) {
	 * log(LogStatus.PASS, "Sequence filter has been Applied Records are matched",
	 * YesNo.No); sa.assertTrue(true,
	 * "Sequence filter has been Applied Records are matched"); } else {
	 * log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Record is not matched", YesNo.No);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Record is not matched"); } } else {
	 * log(LogStatus.PASS,
	 * "Record is not persent against Applied Systems, Inc. Filter on Fund But Filter has been applied successfully"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Record is not persent against Applied Systems, Inc. Filter on Fund But Filter has been applied successfully"
	 * );
	 * 
	 * }
	 * 
	 * if (!SB.verifyRecordExistOrNotOnSDG(contactSDGName, "Contact SDG")) { if
	 * (SB.verifyRecordAfterApplyingGlobalFilter(contactSDGName,
	 * "Applied Systems, Inc.", "Contact Name", "Contact SDG")) {
	 * log(LogStatus.PASS, "Sequence filter has been Applied Records are matched",
	 * YesNo.No); sa.assertTrue(true,
	 * "Sequence filter has been Applied Records are matched"); } else {
	 * log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Record is not matched", YesNo.No);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Record is not matched"); } } else {
	 * log(LogStatus.PASS,
	 * "Record is not persent against Applied Systems, Inc. Filter on Fund But Filter has been applied successfully"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Record is not persent against Applied Systems, Inc. Filter on Fund But Filter has been applied successfully"
	 * );
	 * 
	 * }
	 * 
	 * fundraisingRowCountAfterFilter =
	 * AppBuilder.numberOfRecords(fundraisingSDGName, "100"); if
	 * (fundraisingRowCountAfterFilter == fundraisingRowCountBeforeFilter) {
	 * log(LogStatus.INFO,
	 * "Fundraising grid is not filter based on the Applied Systems, Inc",
	 * YesNo.No); sa.assertTrue(true,
	 * "Fundraising grid is not filter based on the Applied Systems, Inc"); } else {
	 * log(LogStatus.ERROR, "Fundraising grid is filtered", YesNo.No);
	 * sa.assertTrue(false, "Fundraising grid is filtered"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not select the filter", YesNo.No);
	 * sa.assertTrue(false, "Could not select the filter"); }
	 * 
	 * }
	 * 
	 * else { log(LogStatus.ERROR, "Could not open the app from the app launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the app launcher"); } }
	 * 
	 * lp.CRMlogout(); sa.assertAll();
	 * 
	 * }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc070_VerifyFundAndFirmFilter(String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); SDGPageBusinessLayer SB = new
	 * SDGPageBusinessLayer(driver); String appPage = M9Tc070_AppPageName; String[]
	 * table = M9Tc070_SDGTableName.split("<BreakOn>"); String fundraisingSDGName =
	 * table[3]; String fundSDGName = table[2]; String accountSDGName = table[1];
	 * String contactSDGName = table[1];
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { if
	 * (AppBuilder.selectFilter("Fund", "VMWare- Capital Raise 2018")) {
	 * log(LogStatus.INFO, "Filter has been selected from the fund", YesNo.No);
	 * CommonLib.ThreadSleep(8000); if
	 * (!SB.verifyRecordExistOrNotOnSDG(fundraisingSDGName, "Fundraising SDG")) { if
	 * (SB.verifyRecordAfterApplyingGlobalFilter(fundraisingSDGName,
	 * "VMWare- Capital Raise 2018", "Fund Name", "Fundraising SDG")) {
	 * log(LogStatus.PASS, "Sequence filter has been Applied Records are matched",
	 * YesNo.No); sa.assertTrue(true,
	 * "Sequence filter has been Applied Records are matched"); } else {
	 * log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Record is not matched", YesNo.No);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Record is not matched"); } } else {
	 * log(LogStatus.PASS,
	 * "Record is not persent against Appfluent Technology Filter on Fund But Filter has been applied successfully"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Record is not persent against Appfluent Technology Filter on Fund But Filter has been applied successfully"
	 * );
	 * 
	 * }
	 * 
	 * if (!SB.verifyRecordExistOrNotOnSDG(fundSDGName, "Fund SDG")) { if
	 * (SB.verifyRecordAfterApplyingGlobalFilter(fundSDGName,
	 * "VMWare- Capital Raise 2018", "Fund", "Fund SDG")) { log(LogStatus.PASS,
	 * "Sequence filter has been Applied Records are matched", YesNo.No);
	 * sa.assertTrue(true, "Sequence filter has been Applied Records are matched");
	 * } else { log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Record is not matched", YesNo.No);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Record is not matched"); } } else {
	 * log(LogStatus.PASS,
	 * "Record is not persent against Appfluent Technology Filter on Fund But Filter has been applied successfully"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Record is not persent against Appfluent Technology Filter on Fund But Filter has been applied successfully"
	 * );
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not select the filter", YesNo.No);
	 * sa.assertTrue(false, "Could not select the filter"); }
	 * 
	 * if (AppBuilder.selectFilter("Firm", "General Motors")) { log(LogStatus.INFO,
	 * "Filter has been selected from the fund", YesNo.No);
	 * CommonLib.ThreadSleep(8000); if
	 * (!SB.verifyRecordExistOrNotOnSDG(accountSDGName, "Account SDG")) { if
	 * (SB.verifyRecordAfterApplyingGlobalFilter(accountSDGName, "General Motors",
	 * "Account Name", "Account SDG")) { log(LogStatus.PASS,
	 * "Sequence filter has been Applied Records are matched", YesNo.No);
	 * sa.assertTrue(true, "Sequence filter has been Applied Records are matched");
	 * } else { log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Record is not matched", YesNo.No);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Record is not matched"); } } else {
	 * log(LogStatus.PASS,
	 * "Record is not persent against General Motors Filter on Fund But Filter has been applied successfully"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Record is not persent against General Motors Filter on Fund But Filter has been applied successfully"
	 * );
	 * 
	 * }
	 * 
	 * if (!SB.verifyRecordExistOrNotOnSDG(contactSDGName, "Fund SDG")) { if
	 * (SB.verifyRecordAfterApplyingGlobalFilter(contactSDGName, "General Motors",
	 * "Contact Name", "Contact SDG")) { log(LogStatus.PASS,
	 * "Sequence filter has been Applied Records are matched", YesNo.No);
	 * sa.assertTrue(true, "Sequence filter has been Applied Records are matched");
	 * } else { log(LogStatus.ERROR,
	 * "Sequence filter has been Applied but Record is not matched", YesNo.No);
	 * sa.assertTrue(false,
	 * "Sequence filter has been Applied but Record is not matched"); } } else {
	 * log(LogStatus.PASS,
	 * "Record is not persent against General Motors Filter on Fund But Filter has been applied successfully"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Record is not persent against General Motors Filter on Fund But Filter has been applied successfully"
	 * );
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not select the filter", YesNo.No);
	 * sa.assertTrue(false, "Could not select the filter"); }
	 * 
	 * }
	 * 
	 * else { log(LogStatus.ERROR, "Could not open the app from the app launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the app launcher"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc071_checkMyRecordFiltercCheckbox(String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); EditPageBusinessLayer EB = new
	 * EditPageBusinessLayer(driver); String appPage = M9Tc071_AppPageName;
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * if (EB.editMyRecordCheckboxOnAppPage(Condition.SelectCheckbox)) {
	 * log(LogStatus.PASS, "My Record checkbox has been unselect", YesNo.No);
	 * sa.assertTrue(true, "My Record checkbox has been unselect"); lp.CRMlogout();
	 * ThreadSleep(12000); lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * if (AppBuilder.verifyRecordfilterfieldvisibility()) { log(LogStatus.PASS,
	 * "Record filter field is visible", YesNo.No); sa.assertTrue(true,
	 * "Record Filter field is visible");
	 * 
	 * ArrayList<String> optionValue = new ArrayList<String>();
	 * optionValue.add("All Records"); optionValue.add("My Records");
	 * optionValue.add("My Team's Records"); ArrayList<String> result =
	 * AppBuilder.verifyDropDownOptionValue("Show", optionValue); if
	 * (result.isEmpty()) { log(LogStatus.PASS,
	 * "Dropdown Option values has been verified", YesNo.No); sa.assertTrue(true,
	 * "Dropdown Option values has been verified"); } else { log(LogStatus.FAIL,
	 * "Dropdown Option values are not verify", YesNo.No); sa.assertTrue(false,
	 * "Dropdown Option values are not verify " + result); } } else {
	 * log(LogStatus.FAIL, "Record filter field is not visible", YesNo.No);
	 * sa.assertTrue(false, "Record Filter field is not visible"); }
	 * 
	 * } else { log(LogStatus.FAIL, "Could not open the app from the app launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the app launcher"); } } else {
	 * log(LogStatus.FAIL, "My Record checkbox is selected", YesNo.No);
	 * sa.assertTrue(false, "My Record checkbox is selected"); }
	 * 
	 * } else { log(LogStatus.FAIL, "Could not open the app from the app launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the app launcher"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc072_verifyMyTeamRecordUser(String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); String appPage =
	 * M9Tc072_AppPageName; String[] sdgName =
	 * M9Tc072_SDGTableName.split("<BreakOn>");
	 * 
	 * String fundraisingSDGName = sdgName[3]; String fundSDGName = sdgName[2];
	 * String accountSDGName = sdgName[0]; String contactSDGName = sdgName[1]; int
	 * fundRowCount, accountFilterRowCount, fundraisingRowCount,
	 * contactFilterRowCount; String pageSize = "100";
	 * 
	 * String[] sdgDataCount = M9Tc072_SDGCount.split("<break>");
	 * 
	 * int[] values =
	 * Arrays.stream(sdgDataCount).mapToInt(Integer::parseInt).toArray(); int
	 * accountSDGCount = values[0]; int contactSDGCount = values[1]; int
	 * fundSDGCount = values[2]; int fundraisingSDGCount = values[3];
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { if
	 * (AppBuilder.selectFilter("Show", "My Team's Records")) { log(LogStatus.INFO,
	 * "Filter has been selected from the Show Filter", YesNo.No);
	 * CommonLib.ThreadSleep(8000);
	 * 
	 * fundRowCount = AppBuilder.numberOfRecords(fundSDGName, pageSize);
	 * accountFilterRowCount = AppBuilder.numberOfRecords(accountSDGName, pageSize);
	 * fundraisingRowCount = AppBuilder.numberOfRecords(fundraisingSDGName,
	 * pageSize); contactFilterRowCount = AppBuilder.numberOfRecords(contactSDGName,
	 * pageSize);
	 * 
	 * if (fundRowCount == fundSDGCount) { log(LogStatus.PASS, "Fund Grid Count " +
	 * fundSDGCount + " has been verified", YesNo.No); sa.assertTrue(true,
	 * "Fund Grid Count " + fundSDGCount + " has been verified"); } else {
	 * log(LogStatus.FAIL, "Fund Grid Count " + fundSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fund Grid Count " + fundSDGCount +
	 * " is not matched"); }
	 * 
	 * if (accountFilterRowCount == accountSDGCount) { log(LogStatus.PASS,
	 * "Account Grid Count " + accountSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Account Grid Count " + accountSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Account Grid Count " +
	 * accountSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Account Grid Count " + accountSDGCount + " is not matched"); }
	 * 
	 * if (fundraisingRowCount == fundraisingSDGCount) { log(LogStatus.PASS,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " has been verified",
	 * YesNo.No); sa.assertTrue(true, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " has been verified"); } else { log(LogStatus.FAIL,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " is not matched"); }
	 * 
	 * if (contactFilterRowCount == contactSDGCount) { log(LogStatus.PASS,
	 * "Contact Grid Count " + contactSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Contact Grid Count " + contactSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Contact Grid Count " +
	 * contactSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Contact Grid Count " + contactSDGCount + " is not matched"); } } else {
	 * log(LogStatus.FAIL, "Could not select the filter from the Show field",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not select the filter from the Show field"); } } else {
	 * log(LogStatus.FAIL, "Could not open the app from the App Launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); }
	 * 
	 * lp.CRMlogout();
	 * 
	 * ThreadSleep(12000);
	 * 
	 * lp.CRMLogin(crmUser2EmailID, adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { if
	 * (AppBuilder.selectFilter("Show", "My Team's Records")) { log(LogStatus.INFO,
	 * "Filter has been selected from the Show Filter", YesNo.No);
	 * CommonLib.ThreadSleep(8000);
	 * 
	 * fundRowCount = AppBuilder.numberOfRecords(fundSDGName, pageSize);
	 * accountFilterRowCount = AppBuilder.numberOfRecords(accountSDGName, pageSize);
	 * fundraisingRowCount = AppBuilder.numberOfRecords(fundraisingSDGName,
	 * pageSize); contactFilterRowCount = AppBuilder.numberOfRecords(contactSDGName,
	 * pageSize);
	 * 
	 * if (fundRowCount == fundSDGCount) { log(LogStatus.PASS, "Fund Grid Count " +
	 * fundSDGCount + " has been verified", YesNo.No); sa.assertTrue(true,
	 * "Fund Grid Count " + fundSDGCount + " has been verified"); } else {
	 * log(LogStatus.FAIL, "Fund Grid Count " + fundSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fund Grid Count " + fundSDGCount +
	 * " is not matched"); }
	 * 
	 * if (accountFilterRowCount == accountSDGCount) { log(LogStatus.PASS,
	 * "Account Grid Count " + accountSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Account Grid Count " + accountSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Account Grid Count " +
	 * accountSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Account Grid Count " + accountSDGCount + " is not matched"); }
	 * 
	 * if (fundraisingRowCount == fundraisingSDGCount) { log(LogStatus.PASS,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " has been verified",
	 * YesNo.No); sa.assertTrue(true, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " has been verified"); } else { log(LogStatus.FAIL,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " is not matched"); }
	 * 
	 * if (contactFilterRowCount == contactSDGCount) { log(LogStatus.PASS,
	 * "Contact Grid Count " + contactSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Contact Grid Count " + contactSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Contact Grid Count " +
	 * contactSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Contact Grid Count " + contactSDGCount + " is not matched"); } } else {
	 * log(LogStatus.FAIL, "Could not select the filter from the Show field",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not select the filter from the Show field"); } } else {
	 * log(LogStatus.FAIL, "Could not open the app from the App Launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc073_verifyMyTeamRecordAdmin(String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); String appPage =
	 * M9Tc073_AppPageName; String[] sdgName =
	 * M9Tc073_SDGTableName.split("<BreakOn>");
	 * 
	 * String fundraisingSDGName = sdgName[3]; String fundSDGName = sdgName[2];
	 * String accountSDGName = sdgName[0]; String contactSDGName = sdgName[1]; int
	 * fundRowCount, accountFilterRowCount, fundraisingRowCount,
	 * contactFilterRowCount; String pageSize = "100";
	 * 
	 * String[] sdgDataCount = M9Tc073_SDGCount.split("<break>");
	 * 
	 * int[] values =
	 * Arrays.stream(sdgDataCount).mapToInt(Integer::parseInt).toArray(); int
	 * accountSDGCount = values[0]; int contactSDGCount = values[1]; int
	 * fundSDGCount = values[2]; int fundraisingSDGCount = values[3];
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { if
	 * (AppBuilder.selectFilter("Show", "My Team's Records")) { log(LogStatus.INFO,
	 * "Filter has been selected from the Show Filter", YesNo.No);
	 * CommonLib.ThreadSleep(8000);
	 * 
	 * fundRowCount = AppBuilder.numberOfRecords(fundSDGName, pageSize);
	 * accountFilterRowCount = AppBuilder.numberOfRecords(accountSDGName, pageSize);
	 * fundraisingRowCount = AppBuilder.numberOfRecords(fundraisingSDGName,
	 * pageSize); contactFilterRowCount = AppBuilder.numberOfRecords(contactSDGName,
	 * pageSize);
	 * 
	 * if (fundRowCount == fundSDGCount) { log(LogStatus.PASS, "Fund Grid Count " +
	 * fundSDGCount + " has been verified", YesNo.No); sa.assertTrue(true,
	 * "Fund Grid Count " + fundSDGCount + " has been verified"); } else {
	 * log(LogStatus.FAIL, "Fund Grid Count " + fundSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fund Grid Count " + fundSDGCount +
	 * " is not matched"); }
	 * 
	 * if (accountFilterRowCount == accountSDGCount) { log(LogStatus.PASS,
	 * "Account Grid Count " + accountSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Account Grid Count " + accountSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Account Grid Count " +
	 * accountSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Account Grid Count " + accountSDGCount + " is not matched"); }
	 * 
	 * if (fundraisingRowCount == fundraisingSDGCount) { log(LogStatus.PASS,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " has been verified",
	 * YesNo.No); sa.assertTrue(true, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " has been verified"); } else { log(LogStatus.FAIL,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " is not matched"); }
	 * 
	 * if (contactFilterRowCount == contactSDGCount) { log(LogStatus.PASS,
	 * "Contact Grid Count " + contactSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Contact Grid Count " + contactSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Contact Grid Count " +
	 * contactSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Contact Grid Count " + contactSDGCount + " is not matched"); } } else {
	 * log(LogStatus.FAIL, "Could not select the filter from the Show field",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not select the filter from the Show field"); } } else {
	 * log(LogStatus.FAIL, "Could not open the app from the App Launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc074_updateTeamBlankAndVerifyMyTeamRecordFilterRecord(String projectName)
	 * {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); SetupPageBusinessLayer setup =
	 * new SetupPageBusinessLayer(driver); HomePageBusineesLayer home = new
	 * HomePageBusineesLayer(driver); String appPage = M9Tc074_AppPageName; String[]
	 * sdgName = M9Tc074_SDGTableName.split("<BreakOn>");
	 * 
	 * String fundraisingSDGName = sdgName[3]; String fundSDGName = sdgName[2];
	 * String accountSDGName = sdgName[0]; String contactSDGName = sdgName[1]; int
	 * fundRowCount, accountFilterRowCount, fundraisingRowCount,
	 * contactFilterRowCount; String pageSize = "100"; String parentWindow = "";
	 * 
	 * String[] sdgDataCount = M9Tc074_SDGCount.split("<break>");
	 * 
	 * int[] values =
	 * Arrays.stream(sdgDataCount).mapToInt(Integer::parseInt).toArray(); int
	 * accountSDGCount = values[0]; int contactSDGCount = values[1]; int
	 * fundSDGCount = values[2]; int fundraisingSDGCount = values[3];
	 * 
	 * ArrayList<String> EmailId = new ArrayList<String>();
	 * EmailId.add(crmUser1EmailID);
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (home.clickOnSetUpLink()) {
	 * 
	 * parentWindow = switchOnWindow(driver); if (parentWindow == null) {
	 * sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create CRM User2"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create CRM User2"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create CRM User2"
	 * ); } }
	 * 
	 * for (String email : EmailId) { if (setup.EditPEUser(email, "Team",
	 * HTMLTAG.select, "--None--")) { log(LogStatus.INFO,
	 * "Team has been blank for : " + email, YesNo.No); sa.assertTrue(true,
	 * "Team has been blank for : " + email); driver.close();
	 * driver.switchTo().window(parentWindow); lp.CRMlogout(); ThreadSleep(12000);
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { if
	 * (AppBuilder.selectFilter("Show", "My Team's Records")) { log(LogStatus.INFO,
	 * "Filter has been selected from the Show Filter", YesNo.No);
	 * CommonLib.ThreadSleep(8000);
	 * 
	 * fundRowCount = AppBuilder.numberOfRecords(fundSDGName, pageSize);
	 * accountFilterRowCount = AppBuilder.numberOfRecords(accountSDGName, pageSize);
	 * fundraisingRowCount = AppBuilder.numberOfRecords(fundraisingSDGName,
	 * pageSize); contactFilterRowCount = AppBuilder.numberOfRecords(contactSDGName,
	 * pageSize);
	 * 
	 * if (fundRowCount == fundSDGCount) { log(LogStatus.PASS, "Fund Grid Count " +
	 * fundSDGCount + " has been verified", YesNo.No); sa.assertTrue(true,
	 * "Fund Grid Count " + fundSDGCount + " has been verified"); } else {
	 * log(LogStatus.FAIL, "Fund Grid Count " + fundSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fund Grid Count " + fundSDGCount +
	 * " is not matched"); }
	 * 
	 * if (accountFilterRowCount == accountSDGCount) { log(LogStatus.PASS,
	 * "Account Grid Count " + accountSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Account Grid Count " + accountSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Account Grid Count " +
	 * accountSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Account Grid Count " + accountSDGCount + " is not matched"); }
	 * 
	 * if (fundraisingRowCount == fundraisingSDGCount) { log(LogStatus.PASS,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " has been verified",
	 * YesNo.No); sa.assertTrue(true, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " has been verified"); } else { log(LogStatus.FAIL,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " is not matched"); }
	 * 
	 * if (contactFilterRowCount == contactSDGCount) { log(LogStatus.PASS,
	 * "Contact Grid Count " + contactSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Contact Grid Count " + contactSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Contact Grid Count " +
	 * contactSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Contact Grid Count " + contactSDGCount + " is not matched"); }
	 * 
	 * } else { log(LogStatus.PASS,
	 * "Could not select the filter from the Show field", YesNo.No);
	 * sa.assertTrue(false, "Could not select the filter from the Show field"); } }
	 * else { log(LogStatus.FAIL, "Could not open the app from the App Launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "--None-- is not added in the Team field for user :" + email, YesNo.Yes);
	 * sa.assertTrue(false, "--None-- is not added in the Team field for user :" +
	 * email); } }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc075_updateTeamToOriginationForUser1AndVerifyMyTeamRecordFilterRecords(
	 * String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); SetupPageBusinessLayer setup =
	 * new SetupPageBusinessLayer(driver); HomePageBusineesLayer home = new
	 * HomePageBusineesLayer(driver); String appPage = M9Tc075_AppPageName; String[]
	 * sdgName = M9Tc075_SDGTableName.split("<BreakOn>");
	 * 
	 * String fundraisingSDGName = sdgName[3]; String fundSDGName = sdgName[2];
	 * String accountSDGName = sdgName[0]; String contactSDGName = sdgName[1]; int
	 * fundRowCount, accountFilterRowCount, fundraisingRowCount,
	 * contactFilterRowCount; String pageSize = "100"; String parentWindow = "";
	 * 
	 * String[] sdgDataCount = M9Tc075_SDGCount.split("<break>");
	 * 
	 * int[] values =
	 * Arrays.stream(sdgDataCount).mapToInt(Integer::parseInt).toArray(); int
	 * accountSDGCount = values[0]; int contactSDGCount = values[1]; int
	 * fundSDGCount = values[2]; int fundraisingSDGCount = values[3];
	 * 
	 * ArrayList<String> EmailId = new ArrayList<String>();
	 * EmailId.add(crmUser1EmailID);
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (home.clickOnSetUpLink()) {
	 * 
	 * parentWindow = switchOnWindow(driver); if (parentWindow == null) {
	 * sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create CRM User2"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create CRM User2"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create CRM User2"
	 * ); } }
	 * 
	 * for (String email : EmailId) { if (setup.EditPEUser(email, "Team",
	 * HTMLTAG.select, "Origination")) { log(LogStatus.INFO,
	 * "Origination has been selected from Team for : " + email, YesNo.No);
	 * sa.assertTrue(true, "Origination has been selected from Team for : " +
	 * email); driver.close(); driver.switchTo().window(parentWindow);
	 * lp.CRMlogout(); ThreadSleep(12000); lp.CRMLogin(crmUser1EmailID,
	 * adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { if
	 * (AppBuilder.selectFilter("Show", "My Team's Records")) { log(LogStatus.INFO,
	 * "Filter has been selected from the Show Filter", YesNo.No);
	 * CommonLib.ThreadSleep(8000);
	 * 
	 * fundRowCount = AppBuilder.numberOfRecords(fundSDGName, pageSize);
	 * accountFilterRowCount = AppBuilder.numberOfRecords(accountSDGName, pageSize);
	 * fundraisingRowCount = AppBuilder.numberOfRecords(fundraisingSDGName,
	 * pageSize); contactFilterRowCount = AppBuilder.numberOfRecords(contactSDGName,
	 * pageSize);
	 * 
	 * if (fundRowCount == fundSDGCount) { log(LogStatus.PASS, "Fund Grid Count " +
	 * fundSDGCount + " has been verified", YesNo.No); sa.assertTrue(true,
	 * "Fund Grid Count " + fundSDGCount + " has been verified"); } else {
	 * log(LogStatus.FAIL, "Fund Grid Count " + fundSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fund Grid Count " + fundSDGCount +
	 * " is not matched"); }
	 * 
	 * if (accountFilterRowCount == accountSDGCount) { log(LogStatus.PASS,
	 * "Account Grid Count " + accountSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Account Grid Count " + accountSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Account Grid Count " +
	 * accountSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Account Grid Count " + accountSDGCount + " is not matched"); }
	 * 
	 * if (fundraisingRowCount == fundraisingSDGCount) { log(LogStatus.PASS,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " has been verified",
	 * YesNo.No); sa.assertTrue(true, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " has been verified"); } else { log(LogStatus.FAIL,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " is not matched"); }
	 * 
	 * if (contactFilterRowCount == contactSDGCount) { log(LogStatus.PASS,
	 * "Contact Grid Count " + contactSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Contact Grid Count " + contactSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Contact Grid Count " +
	 * contactSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Contact Grid Count " + contactSDGCount + " is not matched"); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "Could not select the filter from the Show field", YesNo.No);
	 * sa.assertTrue(false, "Could not select the filter from the Show field"); } }
	 * else { log(LogStatus.FAIL, "Could not open the app from the App Launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); }
	 * 
	 * lp.CRMlogout(); ThreadSleep(12000); lp.CRMLogin(crmUser2EmailID,
	 * adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { if
	 * (AppBuilder.selectFilter("Show", "My Team's Records")) { log(LogStatus.INFO,
	 * "Filter has been selected from the Show Filter", YesNo.No);
	 * CommonLib.ThreadSleep(8000);
	 * 
	 * fundRowCount = AppBuilder.numberOfRecords(fundSDGName, pageSize);
	 * accountFilterRowCount = AppBuilder.numberOfRecords(accountSDGName, pageSize);
	 * fundraisingRowCount = AppBuilder.numberOfRecords(fundraisingSDGName,
	 * pageSize); contactFilterRowCount = AppBuilder.numberOfRecords(contactSDGName,
	 * pageSize);
	 * 
	 * if (fundRowCount == fundSDGCount) { log(LogStatus.PASS, "Fund Grid Count " +
	 * fundSDGCount + " has been verified", YesNo.No); sa.assertTrue(true,
	 * "Fund Grid Count " + fundSDGCount + " has been verified"); } else {
	 * log(LogStatus.FAIL, "Fund Grid Count " + fundSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fund Grid Count " + fundSDGCount +
	 * " is not matched"); }
	 * 
	 * if (accountFilterRowCount == accountSDGCount) { log(LogStatus.PASS,
	 * "Account Grid Count " + accountSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Account Grid Count " + accountSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Account Grid Count " +
	 * accountSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Account Grid Count " + accountSDGCount + " is not matched"); }
	 * 
	 * if (fundraisingRowCount == fundraisingSDGCount) { log(LogStatus.PASS,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " has been verified",
	 * YesNo.No); sa.assertTrue(true, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " has been verified"); } else { log(LogStatus.FAIL,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " is not matched"); }
	 * 
	 * if (contactFilterRowCount == contactSDGCount) { log(LogStatus.PASS,
	 * "Contact Grid Count " + contactSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Contact Grid Count " + contactSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Contact Grid Count " +
	 * contactSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Contact Grid Count " + contactSDGCount + " is not matched"); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "Could not select the filter from the Show field", YesNo.No);
	 * sa.assertTrue(false, "Could not select the filter from the Show field"); } }
	 * else { log(LogStatus.FAIL, "Could not open the app from the App Launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "Origination is not added in the Team field for user :" + email, YesNo.Yes);
	 * sa.assertTrue(false, "Origination is not added in the Team field for user :"
	 * + email); } }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc076_updateTeamBlankAndVerifyMyTeamRecordFilterRecord(String projectName)
	 * {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver); SetupPageBusinessLayer setup =
	 * new SetupPageBusinessLayer(driver); HomePageBusineesLayer home = new
	 * HomePageBusineesLayer(driver); String appPage = M9Tc076_AppPageName; String[]
	 * sdgName = M9Tc076_SDGTableName.split("<BreakOn>");
	 * 
	 * String fundraisingSDGName = sdgName[3]; String fundSDGName = sdgName[2];
	 * String accountSDGName = sdgName[0]; String contactSDGName = sdgName[1]; int
	 * fundRowCount, accountFilterRowCount, fundraisingRowCount,
	 * contactFilterRowCount; String pageSize = "100"; String parentWindow = "";
	 * 
	 * String[] sdgDataCount = M9Tc076_SDGCount.split("<break>");
	 * 
	 * int[] values =
	 * Arrays.stream(sdgDataCount).mapToInt(Integer::parseInt).toArray(); int
	 * accountSDGCount = values[0]; int contactSDGCount = values[1]; int
	 * fundSDGCount = values[2]; int fundraisingSDGCount = values[3];
	 * 
	 * ArrayList<String> EmailId = new ArrayList<String>();
	 * EmailId.add(crmUser1EmailID);
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (home.clickOnSetUpLink()) {
	 * 
	 * parentWindow = switchOnWindow(driver); if (parentWindow == null) {
	 * sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create CRM User2"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create CRM User2"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create CRM User2"
	 * ); } }
	 * 
	 * for (String email : EmailId) { if (setup.EditPEUser(email, "Team",
	 * HTMLTAG.select, "--None--")) { log(LogStatus.INFO,
	 * "Team has been blank for : " + email, YesNo.No); sa.assertTrue(true,
	 * "Team has been blank for : " + email); driver.close();
	 * driver.switchTo().window(parentWindow); lp.CRMlogout(); ThreadSleep(12000);
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { if
	 * (AppBuilder.selectFilter("Show", "My Team's Records")) { log(LogStatus.INFO,
	 * "Filter has been selected from the Show Filter", YesNo.No);
	 * CommonLib.ThreadSleep(8000);
	 * 
	 * fundRowCount = AppBuilder.numberOfRecords(fundSDGName, pageSize);
	 * accountFilterRowCount = AppBuilder.numberOfRecords(accountSDGName, pageSize);
	 * fundraisingRowCount = AppBuilder.numberOfRecords(fundraisingSDGName,
	 * pageSize); contactFilterRowCount = AppBuilder.numberOfRecords(contactSDGName,
	 * pageSize);
	 * 
	 * if (fundRowCount == fundSDGCount) { log(LogStatus.PASS, "Fund Grid Count " +
	 * fundSDGCount + " has been verified", YesNo.No); sa.assertTrue(true,
	 * "Fund Grid Count " + fundSDGCount + " has been verified"); } else {
	 * log(LogStatus.FAIL, "Fund Grid Count " + fundSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fund Grid Count " + fundSDGCount +
	 * " is not matched"); }
	 * 
	 * if (accountFilterRowCount == accountSDGCount) { log(LogStatus.PASS,
	 * "Account Grid Count " + accountSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Account Grid Count " + accountSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Account Grid Count " +
	 * accountSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Account Grid Count " + accountSDGCount + " is not matched"); }
	 * 
	 * if (fundraisingRowCount == fundraisingSDGCount) { log(LogStatus.PASS,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " has been verified",
	 * YesNo.No); sa.assertTrue(true, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " has been verified"); } else { log(LogStatus.FAIL,
	 * "Fundraising Grid Count " + fundraisingSDGCount + " is not matched",
	 * YesNo.No); sa.assertTrue(false, "Fundraising Grid Count " +
	 * fundraisingSDGCount + " is not matched"); }
	 * 
	 * if (contactFilterRowCount == contactSDGCount) { log(LogStatus.PASS,
	 * "Contact Grid Count " + contactSDGCount + " has been verified", YesNo.No);
	 * sa.assertTrue(true, "Contact Grid Count " + contactSDGCount +
	 * " has been verified"); } else { log(LogStatus.FAIL, "Contact Grid Count " +
	 * contactSDGCount + " is not matched", YesNo.No); sa.assertTrue(false,
	 * "Contact Grid Count " + contactSDGCount + " is not matched"); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "Could not select the filter from the Show field", YesNo.No);
	 * sa.assertTrue(false, "Could not select the filter from the Show field"); } }
	 * else { log(LogStatus.FAIL, "Could not open the app from the App Launcher",
	 * YesNo.No); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "--None-- is not added in the Team field for user :" + email, YesNo.Yes);
	 * sa.assertTrue(false, "--None-- is not added in the Team field for user :" +
	 * email); } }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void M9Tc077_verifyEditLockedIconOnRecord(String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * 
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver); String appPage =
	 * M9Tc077_AppPageName; lp.CRMLogin(superAdminUserName, adminPassword, appName);
	 * if (BP.openAppFromAppLauchner(appPage, 50)) { log(LogStatus.INFO, appPage +
	 * " has been open from the App launcher", YesNo.No); CommonLib.refresh(driver);
	 * CommonLib.ThreadSleep(10000); if
	 * (SB.verifyEditOrLockedIconOnSDGData("Account Name", IconType.Edit)) {
	 * log(LogStatus.PASS, "Edit icon is visible on Account Name", YesNo.No);
	 * sa.assertTrue(true, "Edit icon is visible on Account Name"); } else {
	 * log(LogStatus.ERROR, "Edit icon is not visible on Account Name", YesNo.Yes);
	 * sa.assertTrue(false, "Edit icon is not visible on Account Name"); }
	 * 
	 * if (SB.verifyEditOrLockedIconOnSDGData("Phone", IconType.Edit)) {
	 * log(LogStatus.PASS, "Edit icon is visible on Phone", YesNo.No);
	 * sa.assertTrue(true, "Edit icon is visible on Phone"); } else {
	 * log(LogStatus.ERROR, "Edit icon is not visible on Phone", YesNo.Yes);
	 * sa.assertTrue(false, "Edit icon is not visible on Phone"); }
	 * 
	 * if (SB.verifyEditOrLockedIconOnSDGData("Website", IconType.Edit)) {
	 * log(LogStatus.PASS, "Edit icon is visible on Website", YesNo.No);
	 * sa.assertTrue(true, "Edit icon is visible on Website"); } else {
	 * log(LogStatus.ERROR, "Edit icon is not visible on Website", YesNo.Yes);
	 * sa.assertTrue(false, "Edit icon is not visible on Website"); }
	 * 
	 * if (SB.verifyEditOrLockedIconOnSDGData("Description", IconType.Edit)) {
	 * log(LogStatus.PASS, "Edit icon is visible on Description", YesNo.No);
	 * sa.assertTrue(true, "Edit icon is visible on Description"); } else {
	 * log(LogStatus.ERROR, "Edit icon is not visible on Description", YesNo.Yes);
	 * sa.assertTrue(false, "Edit icon is not visible on Description"); }
	 * 
	 * if (SB.verifyEditOrLockedIconOnSDGData("Revenue", IconType.Edit)) {
	 * log(LogStatus.PASS, "Edit icon is visible on Revenue", YesNo.No);
	 * sa.assertTrue(true, "Edit icon is visible on Revenue"); } else {
	 * log(LogStatus.ERROR, "Edit icon is not visible on Revenue", YesNo.Yes);
	 * sa.assertTrue(false, "Edit icon is not visible on Revenue"); }
	 * 
	 * if (SB.verifyEditOrLockedIconOnSDGData("Address", IconType.Locked)) {
	 * log(LogStatus.PASS, "Locked icon is visible on Address", YesNo.No);
	 * sa.assertTrue(true, "Locked icon is visible on Address"); } else {
	 * log(LogStatus.ERROR, "Locked icon is not visible on Address", YesNo.Yes);
	 * sa.assertTrue(false, "Locked icon is not visible on Address"); }
	 * 
	 * if (SB.verifyEditOrLockedIconOnSDGData("Record Type", IconType.Locked)) {
	 * log(LogStatus.PASS, "Locked icon is visible on Record Type", YesNo.No);
	 * sa.assertTrue(true, "Locked icon is visible on Record Type"); } else {
	 * log(LogStatus.ERROR, "Locked icon is not visible on Record Type", YesNo.Yes);
	 * sa.assertTrue(false, "Locked icon is not visible on Record Type"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not open the app from the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not open the app from the App Launcher"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void M9Tc078_VerifyHeaderCheckboxOfSDG(String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver); String appPage =
	 * M9Tc078_AppPageName; String sdgName = M9Tc078_SDGTableName;
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.CheckedOrUncheckedCheckox(sdgName, Condition.SelectCheckbox)) { int count
	 * = SB.CheckedOrUncheckedCheckboxCountOnSDGGrid(sdgName,
	 * Condition.SelectCheckbox); if (count == 10) { log(LogStatus.PASS,
	 * "Checkbox Selected, The count is 10 in " + sdgName + " Grid", YesNo.Yes);
	 * sa.assertTrue(true, "Checkbox Selected, The count is 10 in " + sdgName +
	 * " Grid"); } else { log(LogStatus.ERROR, "The count of selected checkbox is "
	 * + count + " in " + sdgName + " Grid", YesNo.Yes); sa.assertTrue(false,
	 * "The count of selected checkbox is " + count + " in " + sdgName + " Grid"); }
	 * } else { log(LogStatus.ERROR, "Could not select the checkbox of " + sdgName,
	 * YesNo.Yes); sa.assertTrue(false, "Could not select the checkbox of " +
	 * sdgName); }
	 * 
	 * if (SB.CheckedOrUncheckedCheckox(sdgName, Condition.UnSelectCheckbox)) { int
	 * count = SB.CheckedOrUncheckedCheckboxCountOnSDGGrid(sdgName,
	 * Condition.UnSelectCheckbox); if (count == 0) { log(LogStatus.PASS,
	 * "Checkbox Unselected, The count is 0 in " + sdgName + " Grid", YesNo.Yes);
	 * sa.assertTrue(true, "Checkbox Unselected, The count is 0 in " + sdgName +
	 * " Grid"); } else { log(LogStatus.FAIL, "The count of selected checkbox is " +
	 * count + " in " + sdgName + " Grid", YesNo.Yes); sa.assertTrue(false,
	 * "The count of selected checkbox is " + count + " in " + sdgName + " Grid"); }
	 * } else { log(LogStatus.ERROR, "Could not unselect the checkbox of " +
	 * sdgName, YesNo.Yes); sa.assertTrue(false,
	 * "Could not unselect the checkbox of " + sdgName); }
	 * 
	 * if (SB.CheckedOrUncheckedCheckox(sdgName, Condition.SelectCheckbox)) { int
	 * count = SB.CheckedOrUncheckedCheckboxCountOnSDGGrid(sdgName,
	 * Condition.SelectCheckbox); if (count == 10) { log(LogStatus.PASS,
	 * "Checkbox Selected, The count is 10 in " + sdgName + " Grid", YesNo.Yes);
	 * sa.assertTrue(true, "Checkbox Selected, The count is 10 in " + sdgName +
	 * " Grid"); } else { log(LogStatus.FAIL, "The count of selected checkbox is " +
	 * count + " in " + sdgName + " Grid", YesNo.Yes); sa.assertTrue(false,
	 * "The count of selected checkbox is " + count + " in " + sdgName + " Grid"); }
	 * } else { log(LogStatus.ERROR, "Could not select the checkbox of " + sdgName,
	 * YesNo.Yes); sa.assertTrue(false, "Could not select the checkbox of " +
	 * sdgName); }
	 * 
	 * if (SB.CheckedOrUncheckedCheckox(sdgName, Condition.UnSelectCheckbox)) { int
	 * count = SB.CheckedOrUncheckedCheckboxCountOnSDGGrid(sdgName,
	 * Condition.UnSelectCheckbox); if (count == 0) { log(LogStatus.PASS,
	 * "Checkbox Unselected, The count is 0 in " + sdgName + " Grid", YesNo.Yes);
	 * sa.assertTrue(true, "Checkbox Unselected, The count is 0 in " + sdgName +
	 * " Grid"); } else { log(LogStatus.FAIL, "The count of selected checkbox is " +
	 * count + " in " + sdgName + " Grid", YesNo.Yes); sa.assertTrue(false,
	 * "The count of selected checkbox is " + count + " in " + sdgName + " Grid"); }
	 * } else { log(LogStatus.ERROR, "Could not unselect the checkbox of " +
	 * sdgName, YesNo.Yes); sa.assertTrue(false,
	 * "Could not unselect the checkbox of " + sdgName); }
	 * 
	 * } lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void M9Tc079_VerifyErrorMessageIfRequireFieldEmpty(String projectName)
	 * {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver); String appPage =
	 * M9Tc079_AppPageName; String sdgName = M9Tc079_SDGTableName;
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.removeRecordAndVerifyErrorMessage(sdgName, "Account Name")) {
	 * log(LogStatus.PASS,
	 * "\"1 record has error. Kindly resolve them and try again.\" Error Message is visible"
	 * , YesNo.Yes); sa.assertTrue(true,
	 * "\"1 record has error. Kindly resolve them and try again.\" Error Message is visible"
	 * ); CommonLib.ThreadSleep(5000); if (SB.verifyErrorMessage(sdgName,
	 * "Account Name")) { log(LogStatus.PASS, "Error Message is visible",
	 * YesNo.Yes); sa.assertTrue(true, "Error Message is visible"); if
	 * (SB.updateRecordAndVerifyMessage(sdgName, "Account Name",
	 * "Account 1 Updated")) { log(LogStatus.PASS,
	 * "\"Your changes are saved.\" Update Mesage is visible", YesNo.Yes);
	 * sa.assertTrue(true, "\"Your changes are saved.\" Update Mesage is visible");
	 * } else { log(LogStatus.ERROR,
	 * "\"Your changes are saved.\" Update Mesage is not visible", YesNo.Yes);
	 * sa.assertTrue(false,
	 * "\"Your changes are saved.\" Update Mesage is not visible");
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.FAIL, "Error Message is not visible", YesNo.Yes);
	 * sa.assertTrue(false, "Error Message is not visible"); }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "\"1 record has error. Kindly resolve them and try again.\" Error Message is not visible"
	 * , YesNo.Yes); sa.assertTrue(false,
	 * "\"1 record has error. Kindly resolve them and try again.\" Error Message is not visible"
	 * ); } } lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void
	 * M9Tc080_VerifyFieldsWhichEditableOnSingleRecordAndMultipleRecord(String
	 * projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver);
	 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * InstitutionsPageBusinessLayer IB = new InstitutionsPageBusinessLayer(driver);
	 * String appPage = M9Tc080_AppPageName; String sdgName = M9Tc080_SDGTableName;
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); String parentWindow
	 * = ""; if (home.clickOnSetUpLink()) { parentWindow = switchOnWindow(driver);
	 * if (parentWindow == null) { sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create Field Set Component"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create Field Set Component"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component"
	 * ); } ThreadSleep(3000);
	 * 
	 * if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip, "Annual Revenue",
	 * PermissionType.givePermission, "System Administrator",
	 * RecordType.Institution)) { log(LogStatus.PASS,
	 * "Annual Revenue field Permission is given from the Firm Object Manager for Institution Record Type"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Annual Revenue field Permission is given from the Firm Object Manager for Institution Record Type"
	 * );
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type"
	 * , YesNo.No); sa.assertTrue(false,
	 * "Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type"
	 * ); }
	 * 
	 * CommonLib.switchToDefaultContent(driver); ThreadSleep(2000); if
	 * (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip, "Annual Revenue",
	 * PermissionType.givePermission, "System Administrator",
	 * RecordType.Intermediary)) { log(LogStatus.PASS,
	 * "Annual Revenue field Permission is given from the Firm Object Manager for Intermediary Record Type"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Annual Revenue field Permission is given from the Firm Object Manager for Intermediary Record Type"
	 * );
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Annual Revenue field Permission is not given from the Firm Object Manager for Intermediary Record Type"
	 * , YesNo.No); sa.assertTrue(false,
	 * "Annual Revenue field Permission is not given from the Firm Object Manager for Intermediary Record Type"
	 * );
	 * 
	 * }
	 * 
	 * CommonLib.switchToDefaultContent(driver); ThreadSleep(2000); if
	 * (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip, "Source",
	 * PermissionType.givePermission, "System Administrator",
	 * RecordType.Institution)) { log(LogStatus.PASS,
	 * "Source field Permission is given from the Firm Object Manager for Institution Record Type"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Source field Permission is given from the Firm Object Manager for Intermediary Record Type"
	 * );
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Source field Permission is not given from the Firm Object Manager for Institution Record Type"
	 * , YesNo.No); sa.assertTrue(false,
	 * "Source field Permission is not given from the Firm Object Manager for Institution Record Type"
	 * );
	 * 
	 * }
	 * 
	 * CommonLib.switchToDefaultContent(driver); ThreadSleep(2000); if
	 * (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip, "Source",
	 * PermissionType.givePermission, "System Administrator",
	 * RecordType.Intermediary)) { log(LogStatus.INFO,
	 * "Source field Permission is given from the Firm Object Manager for Intermediary Record Type"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Source field Permission is given from the Firm Object Manager for Intermediary Record Type"
	 * );
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Source field Permission is not given from the Firm Object Manager for Intermediary Record Type"
	 * , YesNo.No); sa.assertTrue(false,
	 * "Source field Permission is not given from the Firm Object Manager for Intermediary Record Type"
	 * );
	 * 
	 * }
	 * 
	 * CommonLib.switchToDefaultContent(driver); ThreadSleep(2000); if
	 * (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip, "Location Preferences",
	 * PermissionType.givePermission, "System Administrator",
	 * RecordType.Institution)) { log(LogStatus.INFO,
	 * "Location Preferences field Permission is given from the Firm Object Manager for Institution Record Type"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Location Preferences field Permission is given from the Firm Object Manager for Institution Record Type"
	 * );
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Location Preferences field Permission is not given from the Firm Object Manager for Institution Record Type"
	 * , YesNo.No); sa.assertTrue(false,
	 * "Location Preferences field Permission is not given from the Firm Object Manager for Institution Record Type"
	 * );
	 * 
	 * }
	 * 
	 * CommonLib.switchToDefaultContent(driver); ThreadSleep(2000); if
	 * (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip, "Location Preferences",
	 * PermissionType.givePermission, "System Administrator",
	 * RecordType.Intermediary)) { log(LogStatus.INFO,
	 * "Location Preferences field Permission is given from the Firm Object Manager for Intermediary Record Type"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Location Preferences field Permission is given from the Firm Object Manager for Intermediary Record Type"
	 * );
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Location Preferences field Permission is not given from the Firm Object Manager for Intermediary Record Type"
	 * , YesNo.No); sa.assertTrue(false,
	 * "Location Preferences field Permission is not given from the Firm Object Manager for Intermediary Record Type"
	 * );
	 * 
	 * } CommonLib.switchToDefaultContent(driver); ThreadSleep(2000);
	 * driver.close(); driver.switchTo().window(parentWindow);
	 * 
	 * lp.CRMlogout(); lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 2", "Revenue",
	 * "70000", "Text", null)) { log(LogStatus.INFO,
	 * "\"Account 2\" Revenue record has been updated to 70000", YesNo.No);
	 * sa.assertTrue(true,
	 * "\"Account 2\" Revenue record has been updated to 70000");
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * "Tab has been opened", YesNo.No); if
	 * (BP.clickOnAlreadyCreated_Lighting(environment, mode,
	 * TabName.InstituitonsTab, "Account 2", 50)) { log(LogStatus.INFO,
	 * "Clicked on the Account 2 name", YesNo.No); CommonLib.ThreadSleep(5000); if
	 * (IB.verifyValueOnFirm("Annual Revenue", "70,000")) { log(LogStatus.INFO,
	 * "\"Annual Revenue - 70000\" data has been matched", YesNo.No); } else {
	 * log(LogStatus.ERROR, "\"Annual Revenue - 70000\" data is not matched",
	 * YesNo.No);
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not click on the Account 2 name",
	 * YesNo.No); } } else { log(LogStatus.ERROR, TabName.InstituitonsTab.toString()
	 * + " is not open", YesNo.No); }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "\"Account 2\" Revenue record is not updated to 70000", YesNo.No);
	 * sa.assertTrue(false, "\"Account 2\" Revenue record is not updated to 70000");
	 * } }
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 8", "Phone",
	 * "9874563215", "Text", null)) { log(LogStatus.INFO,
	 * "\"Account 8\" phone record has been updated to 9874563215", YesNo.No);
	 * sa.assertTrue(true,
	 * "\"Account 8\" phone record has been updated to 9874563215");
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * " tab has been open", YesNo.No); if (BP.clickOnAlreadyCreated(environment,
	 * mode, TabName.InstituitonsTab, "Account 8", 50)) {
	 * 
	 * log(LogStatus.INFO, "Clicked on the Account 8 name", YesNo.No);
	 * CommonLib.ThreadSleep(5000); if (IB.verifyValueOnFirm("Phone",
	 * "(987) 456-3215")) { log(LogStatus.INFO,
	 * "\"Phone - (987) 456-3215\" data has been matched", YesNo.No); } else {
	 * log(LogStatus.ERROR, "\"Phone - (987) 456-3215\" data is not matched",
	 * YesNo.No);
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not clicks on the Account 8 name",
	 * YesNo.No); } } else { log(LogStatus.ERROR, TabName.InstituitonsTab.toString()
	 * + " is not open", YesNo.No); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Account 8\" Phone record is not updated to (987) 456-3215", YesNo.No);
	 * sa.assertTrue(false,
	 * "\"Account 8\" Phone record is not updated to (987) 456-3215"); } }
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 8", "Source",
	 * "Navatar Deal Connect", "Picklist", null)) { log(LogStatus.INFO,
	 * "\"Account 8\" Source record has been updated to Navatar Deal Connect",
	 * YesNo.No); sa.assertTrue(true,
	 * "\"Account 8\" Source record has been updated to Navatar Deal Connect");
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * " tab has been open", YesNo.No); if (BP.clickOnAlreadyCreated(environment,
	 * mode, TabName.InstituitonsTab, "Account 8", 50)) {
	 * 
	 * log(LogStatus.INFO, "Clicked on the Account 8 name", YesNo.No);
	 * CommonLib.ThreadSleep(5000); if (IB.verifyValueOnFirm("Source",
	 * "Navatar Deal Connect")) { log(LogStatus.INFO,
	 * "\"Source - Navatar Deal Connect\" data has been matched", YesNo.No); } else
	 * { log(LogStatus.ERROR,
	 * "\"Source - Navatar Deal Connect\" data is not matched", YesNo.No);
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not clicks on the name Account 8",
	 * YesNo.No); } } else { log(LogStatus.ERROR, TabName.InstituitonsTab.toString()
	 * + " is not open", YesNo.No); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Source\" record is not updated to Navatar Deal Connect", YesNo.No);
	 * sa.assertTrue(false,
	 * "\"Source\" record is not updated to Navatar Deal Connect "); }
	 * 
	 * } if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver);
	 * 
	 * ArrayList<String> val = new ArrayList<String>(); val.add("Asia"); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 8",
	 * "Location", "Asia", "Multipicklist", val)) { log(LogStatus.INFO,
	 * "\"Account 8\" Location record has been updated to Asia", YesNo.No);
	 * sa.assertTrue(true,
	 * "\"Account 8\" Location record has been updated to Asia");
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * " tab has been open", YesNo.No); if (BP.clickOnAlreadyCreated(environment,
	 * mode, TabName.InstituitonsTab, "Account 8", 50)) {
	 * 
	 * log(LogStatus.INFO, "Clicked on the Account 8 name", YesNo.No);
	 * CommonLib.ThreadSleep(5000); if (IB.verifyValueOnFirm("Location Preferences",
	 * "Asia")) { log(LogStatus.INFO, "\"Location - Asia\" data has been matched",
	 * YesNo.No); } else { log(LogStatus.ERROR,
	 * "\"Location - Asia\" data is not matched", YesNo.No);
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not clicks on the Account 8 name",
	 * YesNo.No); } } else { log(LogStatus.ERROR, TabName.InstituitonsTab.toString()
	 * + " tab is not open", YesNo.No); }
	 * 
	 * } else { log(LogStatus.FAIL, "\"Location\" record is not updated to Asia",
	 * YesNo.No); sa.assertTrue(false,
	 * "\"Location\" record is not updated to Asia"); } } if
	 * (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver);
	 * 
	 * ArrayList<String> val1 = new ArrayList<String>(); val1.add("Far East");
	 * val1.add("Global"); val1.add("Middle East"); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 8",
	 * "Location", null, "Multipicklist", val1)) { log(LogStatus.INFO,
	 * "\"Account 8\" Location record has been updated to Far East, Global, Middle East"
	 * , YesNo.No); sa.assertTrue(true,
	 * "\"Account 8\" Location record is not updated to Far East, Global, Middle East"
	 * );
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * " tab has been open", YesNo.No); if (BP.clickOnAlreadyCreated(environment,
	 * mode, TabName.InstituitonsTab, "Account 8", 50)) {
	 * 
	 * log(LogStatus.INFO, "Clicked on the name Account 8", YesNo.No);
	 * CommonLib.ThreadSleep(5000); if (IB.verifyValueOnFirm("Location Preferences",
	 * "Far East;Global;Middle East")) { log(LogStatus.INFO,
	 * "\"Location Preferences - East;Global;Middle East\" data has been matched",
	 * YesNo.No); } else { log(LogStatus.ERROR,
	 * "\"Location Preferences - East;Global;Middle East\" data is not matched",
	 * YesNo.No); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not click on the Account name",
	 * YesNo.No); } }
	 * 
	 * else { log(LogStatus.ERROR, TabName.InstituitonsTab.toString() +
	 * " tab is not open", YesNo.No); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Location Preferences\" record is not updated to East;Global;Middle East",
	 * YesNo.No); sa.assertTrue(false,
	 * "\"Location Preferences\" record is not updated to East;Global;Middle East");
	 * }
	 * 
	 * } if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 8",
	 * "Description",
	 * "The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company."
	 * , "Textarea", null)) { log(LogStatus.INFO,
	 * "\"Account 8\" Description record has been updated to The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company."
	 * , YesNo.No); sa.assertTrue(true,
	 * "\"Account 8\" Description record is not updated to The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company."
	 * );
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * " tab has been open", YesNo.No); if (BP.clickOnAlreadyCreated(environment,
	 * mode, TabName.InstituitonsTab, "Account 8", 50)) { log(LogStatus.INFO,
	 * "Clicked on the Account 8 name", YesNo.No); CommonLib.ThreadSleep(5000); if
	 * (IB.verifyValueOnFirm("Description", "The company was founded in 1999")) {
	 * log(LogStatus.INFO,
	 * "\"Description - The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company.\" data has been matched"
	 * , YesNo.No);
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "\"Description - The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company.\" data is not matched"
	 * , YesNo.No);
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not clicks on the Account 8 name",
	 * YesNo.No); } } else
	 * 
	 * { log(LogStatus.ERROR, TabName.InstituitonsTab.toString() +
	 * " tab is not open", YesNo.No); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Description\" record is not updated to The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company."
	 * , YesNo.No); sa.assertTrue(false,
	 * "\"Description\" record is not updated to The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company."
	 * ); }
	 * 
	 * } if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 8", "Website",
	 * "www.google.com", "Text", null)) { log(LogStatus.INFO,
	 * "\"Account 8\" Website record has been updated to www.google.com", YesNo.No);
	 * sa.assertTrue(true,
	 * "\"Account 8\" Websote record is not updated to www.google.com");
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * " tab has been open", YesNo.No); if (BP.clickOnAlreadyCreated(environment,
	 * mode, TabName.InstituitonsTab, "Account 8", 50)) { log(LogStatus.INFO,
	 * "Clicked on the Account 8 name", YesNo.No); CommonLib.ThreadSleep(5000); if
	 * (IB.verifyValueOnFirm("Website", "google")) { log(LogStatus.INFO,
	 * "\"Website - www.google.com\" data has been matched", YesNo.No);
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "\"Website - www.google.com\" data is not matched", YesNo.Yes);
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not click on the Account 8 name",
	 * YesNo.No); } } else
	 * 
	 * { log(LogStatus.ERROR, TabName.InstituitonsTab.toString() +
	 * " tab is not open", YesNo.No); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Website\" record is not updated to www.google.com", YesNo.No);
	 * sa.assertTrue(false, "\"Website\" record is not updated to www.google.com");
	 * }
	 * 
	 * }
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 1 Updated",
	 * "Revenue", "90000", "Text", null)) { log(LogStatus.INFO,
	 * "\"Account 1 Updated\" Revenue record has been updated to 90000", YesNo.No);
	 * sa.assertTrue(true,
	 * "\"Account 1 Updated\" Revenue record has been updated to 90000");
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * "Tab has been opened", YesNo.No); if
	 * (BP.clickOnAlreadyCreated_Lighting(environment, mode,
	 * TabName.InstituitonsTab, "Account 2", 50)) { log(LogStatus.INFO,
	 * "Clicked on the Account 1 Updated name", YesNo.No);
	 * CommonLib.ThreadSleep(5000); if (IB.verifyValueOnFirm("Annual Revenue",
	 * "90,000")) { log(LogStatus.INFO,
	 * "\"Annual Revenue - 90000\" data has been matched", YesNo.No); } else {
	 * log(LogStatus.ERROR, "\"Annual Revenue - 90000\" data is not matched",
	 * YesNo.No);
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Could not click on the Account 1 Updated name", YesNo.No); } } else {
	 * log(LogStatus.ERROR, TabName.InstituitonsTab.toString() + " is not open",
	 * YesNo.No); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Account 1 Updated\" Revenue record is not updated to 90000", YesNo.No);
	 * sa.assertTrue(false,
	 * "\"Account 1 Updated\" Revenue record is not updated to 90000"); } }
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 1 Updated",
	 * "Phone", "9874563235", "Text", null)) { log(LogStatus.INFO,
	 * "\"Account 1 Updated\" phone record has been updated to 9874563235",
	 * YesNo.No); sa.assertTrue(true,
	 * "\"Account 1 Updated\" phone record has been updated to 9874563235");
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * " tab has been open", YesNo.No); if (BP.clickOnAlreadyCreated(environment,
	 * mode, TabName.InstituitonsTab, "Account 8", 50)) {
	 * 
	 * log(LogStatus.INFO, "Clicked on the Account 1 Updated name", YesNo.No);
	 * CommonLib.ThreadSleep(5000); if (IB.verifyValueOnFirm("Phone",
	 * "(987) 456-3235")) { log(LogStatus.INFO,
	 * "\"Phone - (987) 456-3235\" data has been matched", YesNo.No); } else {
	 * log(LogStatus.ERROR, "\"Phone - (987) 456-3235\" data is not matched",
	 * YesNo.No);
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Could not clicks on the Account 1 Updated name", YesNo.No); } } else {
	 * log(LogStatus.ERROR, TabName.InstituitonsTab.toString() + " is not open",
	 * YesNo.No); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Account 8\" Phone record is not updated to (987) 456-3235", YesNo.No);
	 * sa.assertTrue(false,
	 * "\"Account 8\" Phone record is not updated to (987) 456-3235"); } }
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 1 Updated",
	 * "Source", "Navatar Deal Connect", "Picklist", null)) { log(LogStatus.INFO,
	 * "\"Account 1 Updated\" Source record has been updated to Navatar Deal Connect"
	 * , YesNo.No); sa.assertTrue(true,
	 * "\"Account 1 Updated\" Source record has been updated to Navatar Deal Connect"
	 * );
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * " tab has been open", YesNo.No); if (BP.clickOnAlreadyCreated(environment,
	 * mode, TabName.InstituitonsTab, "Account 8", 50)) {
	 * 
	 * log(LogStatus.INFO, "Clicked on the Account Account 1 Updated name",
	 * YesNo.No); CommonLib.ThreadSleep(5000); if (IB.verifyValueOnFirm("Source",
	 * "Navatar Deal Connect")) { log(LogStatus.INFO,
	 * "\"Source - Navatar Deal Connect\" data has been matched", YesNo.No); } else
	 * { log(LogStatus.ERROR,
	 * "\"Source - Navatar Deal Connect\" data is not matched", YesNo.No);
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Could not clicks on the name Account 1 Updated", YesNo.No); } } else {
	 * log(LogStatus.ERROR, TabName.InstituitonsTab.toString() + " is not open",
	 * YesNo.No); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Source\" record is not updated to Navatar Deal Connect", YesNo.No);
	 * sa.assertTrue(false,
	 * "\"Source\" record is not updated to Navatar Deal Connect "); }
	 * 
	 * }
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver);
	 * 
	 * ArrayList<String> val = new ArrayList<String>(); val.add("Far East"); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 1 Updated",
	 * "Location", "null", "Multipicklist", val)) { log(LogStatus.INFO,
	 * "\"Account 1 Updated\" Location record has been updated to Far East",
	 * YesNo.No); sa.assertTrue(true,
	 * "\"Account Updated\" Location record has been updated to Far East");
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * " tab has been open", YesNo.No); if (BP.clickOnAlreadyCreated(environment,
	 * mode, TabName.InstituitonsTab, "Account 1 Updated", 50)) {
	 * 
	 * log(LogStatus.INFO, "Clicked on the Account 1 Updated name", YesNo.No);
	 * CommonLib.ThreadSleep(5000); if (IB.verifyValueOnFirm("Location Preferences",
	 * "Far East")) { log(LogStatus.INFO,
	 * "\"Location - Far East\" data has been matched", YesNo.No); } else {
	 * log(LogStatus.ERROR, "\"Location - Far East\" data is not matched",
	 * YesNo.No);
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Could not click on the Account 1 Updated name", YesNo.No); } } else {
	 * log(LogStatus.ERROR, TabName.InstituitonsTab.toString() + " tab is not open",
	 * YesNo.No); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Location\" record is not updated to Far East", YesNo.No);
	 * sa.assertTrue(false, "\"Location\" record is not updated to Far East"); } }
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver);
	 * 
	 * ArrayList<String> val1 = new ArrayList<String>(); val1.add("Asia");
	 * val1.add("Middle East"); val1.add("Global"); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 1 Updated",
	 * "Location", null, "Multipicklist", val1)) { log(LogStatus.INFO,
	 * "\"Account 1 Updated\" Location record has been updated to Far Asia, Global, Middle East"
	 * , YesNo.No); sa.assertTrue(true,
	 * "\"Account 1 Updated\" Location record is not updated to Far Asia, Global, Middle East"
	 * );
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * " tab has been open", YesNo.No); if (BP.clickOnAlreadyCreated(environment,
	 * mode, TabName.InstituitonsTab, "Account 1 Updated", 50)) {
	 * 
	 * log(LogStatus.INFO, "Clicked on the name Account 1 Updated", YesNo.No);
	 * CommonLib.ThreadSleep(5000); if (IB.verifyValueOnFirm("Location Preferences",
	 * "Far Asia;Global;Middle East")) { log(LogStatus.INFO,
	 * "\"Location Preferences - Asia;Global;Middle East\" data has been matched",
	 * YesNo.No); } else { log(LogStatus.ERROR,
	 * "\"Location Preferences - Asia;Global;Middle East\" data is not matched",
	 * YesNo.No); }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not click on the Account name",
	 * YesNo.No); } }
	 * 
	 * else { log(LogStatus.ERROR, TabName.InstituitonsTab.toString() +
	 * " tab is not open", YesNo.No); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Location Preferences\" record is not updated to Asia;Global;Middle East",
	 * YesNo.No); sa.assertTrue(false,
	 * "\"Location Preferences\" record is not updated to Asia;Global;Middle East");
	 * }
	 * 
	 * }
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 1 Updated",
	 * "Description",
	 * "The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company."
	 * , "Textarea", null)) { log(LogStatus.INFO,
	 * "\"Account 1 Updated\" Description record has been updated to The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company."
	 * , YesNo.No); sa.assertTrue(true,
	 * "\"Account 1 Updated\" Description record is not updated to The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company."
	 * );
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * " tab has been open", YesNo.No); if (BP.clickOnAlreadyCreated(environment,
	 * mode, TabName.InstituitonsTab, "Account 8", 50)) { log(LogStatus.INFO,
	 * "Clicked on the Account 1 Updated name", YesNo.No);
	 * CommonLib.ThreadSleep(5000); if (IB.verifyValueOnFirm("Description",
	 * "The company was founded in 1999")) { log(LogStatus.INFO,
	 * "\"Description - The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company.\" data has been matched"
	 * , YesNo.No);
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "\"Description - The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company."
	 * , YesNo.No);
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not clicks on the Account 8 name",
	 * YesNo.No); } } else
	 * 
	 * { log(LogStatus.ERROR, TabName.InstituitonsTab.toString() +
	 * " tab is not open", YesNo.Yes); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Description\" record is not updated to The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company."
	 * , YesNo.No); sa.assertTrue(false,
	 * "\"Description\" record is not updated to The company was founded in 1999 by former�Oracle�executive�Marc Benioff, Parker Harris, Dave Moellenhoff, and Frank Dominguez as a�software as a service�(SaaS) company."
	 * ); }
	 * 
	 * } if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 1 Updated",
	 * "Website", "www.google.com", "Text", null)) { log(LogStatus.INFO,
	 * "\"Account 1 Updated\" Website record has been updated to www.google.com",
	 * YesNo.No); sa.assertTrue(true,
	 * "\"Account 1 Updated\" Websote record is not updated to www.google.com");
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * " tab has been open", YesNo.No); if (BP.clickOnAlreadyCreated(environment,
	 * mode, TabName.InstituitonsTab, "Account 8", 50)) { log(LogStatus.INFO,
	 * "Clicked on the Account 1 Updated name", YesNo.No);
	 * CommonLib.ThreadSleep(5000); if (IB.verifyValueOnFirm("Website", "google")) {
	 * log(LogStatus.INFO, "\"Website - www.google.com\" data has been matched",
	 * YesNo.No);
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "\"Website - www.google.com\" data is not matched", YesNo.Yes);
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Could not click on the Account 1 Updated name", YesNo.No); } } else
	 * 
	 * { log(LogStatus.ERROR, TabName.InstituitonsTab.toString() +
	 * " tab is not open", YesNo.Yes); }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Website\" record is not updated to www.google.com", YesNo.No);
	 * sa.assertTrue(false, "\"Website\" record is not updated to www.google.com");
	 * } } }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void
	 * M9Tc081_VerifyMultipleFieldsWhichEditableOnSameTimeWithSingleOrMultipleRecored
	 * (String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver);
	 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver); String
	 * appPage = M9Tc081_AppPageName; String sdgName = M9Tc081_SDGTableName;
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); String parentWindow
	 * = ""; if (home.clickOnSetUpLink()) { parentWindow = switchOnWindow(driver);
	 * if (parentWindow == null) { sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create Field Set Component"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create Field Set Component"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component"
	 * ); } ThreadSleep(3000);
	 * 
	 * if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip, "Phone",
	 * PermissionType.removePermission, "System Administrator",
	 * RecordType.Institution)) { log(LogStatus.PASS,
	 * "Phone field Permission is given from the Firm Object Manager for Institution Record Type"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Phone Revenue field Permission is given from the Firm Object Manager for Institution Record Type"
	 * );
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Phone field Permission is not given from the Firm Object Manager for Institution Record Type"
	 * , YesNo.No); sa.assertTrue(false,
	 * "Phone field Permission is not given from the Firm Object Manager for Institution Record Type"
	 * ); } }
	 * 
	 * CommonLib.switchToDefaultContent(driver); driver.close();
	 * driver.switchTo().window(parentWindow); lp.CRMlogout();
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.upadateLegalNameWebsiteRevenueAndVerifyErrorMessageSaveCancelButton(
	 * sdgName, "Account 1 Updated", "Account Name", "", "Website", "www.yahoo.com",
	 * "Revenue", "1000000")) { log(LogStatus.PASS,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also visible"
	 * , YesNo.No); sa.assertTrue(true,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also visible"
	 * ); CommonLib.ThreadSleep(7000); if
	 * (SB.verifyErrorMessageOnTriangleIcon(sdgName, "Revenue")) {
	 * log(LogStatus.PASS,
	 * "Error message is visible on the hover to the triangle icon", YesNo.No);
	 * sa.assertTrue(true,
	 * "Error message is visible on the hover to the triangle icon");
	 * 
	 * if (SB.clickCancelBtnAndVerifyRecord(sdgName, "Account 1 Updated", "Website",
	 * "www.yahoo.com")) { log(LogStatus.PASS,
	 * "Page has been refreash and data is matched", YesNo.No); sa.assertTrue(true,
	 * "Page has been refreash and data is matched");
	 * 
	 * } else { log(LogStatus.ERROR, "Page is not refeash and data is not matched",
	 * YesNo.No); sa.assertTrue(false,
	 * "Page is not refeash and data is not matched"); } } else {
	 * log(LogStatus.ERROR,
	 * "Error message is not visible on the hover to the triangle icon", YesNo.No);
	 * sa.assertTrue(false,
	 * "Error message is not visible on the hover to the triangle icon");
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * , YesNo.No); sa.assertTrue(false,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * );
	 * 
	 * }
	 * 
	 * CommonLib.refresh(driver); if
	 * (SB.upadatePhoneWebsiteRevenueAndVerifyErrorMessageSaveCancelButton(sdgName,
	 * "Account 1 Updated", "Phone", "6532145698", "Website", "www.yahoo.com",
	 * "Revenue", "1000000")) { log(LogStatus.PASS,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also visible"
	 * , YesNo.No); sa.assertTrue(true,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also visible"
	 * ); CommonLib.ThreadSleep(7000);
	 * 
	 * if (SB.clickCancelBtnAndVerifyRecord(sdgName, "Account 1 Updated", "Website",
	 * "www.yahoo.com")) { log(LogStatus.PASS,
	 * "Page has been refreash and data is matched", YesNo.No); sa.assertTrue(true,
	 * "Page has been refreash and data is matched");
	 * 
	 * } else { log(LogStatus.ERROR, "Page is not refeash and data is not matched",
	 * YesNo.No); sa.assertTrue(false,
	 * "Page is not refeash and data is not matched"); } } else {
	 * log(LogStatus.ERROR,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * , YesNo.No); sa.assertTrue(false,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * );
	 * 
	 * }
	 * 
	 * if (SB.
	 * clickTwoCheckboxupadatePhoneWebsiteRevenueAndVerifyErrorMessageSaveCancelButton
	 * (sdgName, "Account 1 Updated", "Account 2", "Phone", "6532145698", "Website",
	 * "www.yahoo.com", "Revenue", "1000000")) { log(LogStatus.PASS,
	 * "\"2 records has error. Kindly resolve them and try again.\", error message is visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also visible"
	 * , YesNo.No); sa.assertTrue(true,
	 * "\"2 records has error. Kindly resolve them and try again.\", error message is visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also visible"
	 * ); ArrayList<String> fieldName = new ArrayList<String>(); ArrayList<String>
	 * value = new ArrayList<String>(); fieldName.add("Phone");
	 * fieldName.add("Website"); fieldName.add("Revenue");
	 * 
	 * value.add("(653) 214-5698"); value.add("www.yahoo.com");
	 * value.add("$1,000,000");
	 * 
	 * ArrayList<String> data = SB.clickCancelBtnAndVerifyRecordNotMatched(sdgName,
	 * "Account 1 Updated", fieldName, value); if (data.isEmpty()) {
	 * log(LogStatus.PASS,
	 * "Page has been refreash and data is not updated in the Account 1 Updated Field"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Page has been refreash and data is not updated in the Account 1 Updated Field"
	 * ); } else { log(LogStatus.ERROR,
	 * "Either Page is not refresh or data has been updated in the Account 1 Updated Record"
	 * + data, YesNo.Yes); sa.assertTrue(false,
	 * "Either Page is not refresh or data has been updated in the Account 1 Updated Record"
	 * + data); }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "\"2 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * , YesNo.No); sa.assertTrue(false,
	 * "\"2 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * );
	 * 
	 * }
	 * 
	 * if (SB.
	 * clickTwoCheckboxupadatePhoneWebsiteRevenueAndVerifyErrorMessageSaveCancelButton
	 * (sdgName, "Account 1 Updated", "Account 5", "Phone", "6532145698", "Website",
	 * "www.yahoo.com", "Revenue", "1000000")) { log(LogStatus.PASS,
	 * "\"1 record has error. Kindly resolve them and try again.\", error message is visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also visible"
	 * , YesNo.No); sa.assertTrue(true,
	 * "\"1 record has error. Kindly resolve them and try again.\", error message is visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also visible"
	 * );
	 * 
	 * if (SB.verifyErrorMessageOnTriangleIconForPermission(sdgName, "Phone")) {
	 * log(LogStatus.PASS,
	 * "Error message is visible on the hover to the triangle icon", YesNo.No);
	 * sa.assertTrue(true,
	 * "Error message is visible on the hover to the triangle icon");
	 * 
	 * ArrayList<String> fieldName = new ArrayList<String>(); ArrayList<String>
	 * value1 = new ArrayList<String>(); fieldName.add("Phone");
	 * fieldName.add("Website"); fieldName.add("Revenue");
	 * 
	 * value1.add("(653) 214-5698"); value1.add("www.yahoo.com");
	 * value1.add("$1,000,000");
	 * 
	 * ArrayList<String> data = SB.clickCancelBtnAndVerifyRecordNotMatched(sdgName,
	 * "Account 1 Updated", fieldName, value1); if (data.isEmpty()) {
	 * log(LogStatus.PASS,
	 * "Page has been refreash and data is not updated in the Account 1 Updated Field"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Page has been refreash and data is not updated in the Account 1 Updated Field"
	 * ); } else { log(LogStatus.ERROR,
	 * "Either Page is not refresh or data has been updated in the Account 1 Updated Record"
	 * + data, YesNo.No); sa.assertTrue(false,
	 * "Either Page is not refresh or data has been updated in the Account 1 Updated Record"
	 * + data); }
	 * 
	 * ArrayList<String> value2 = new ArrayList<String>();
	 * value2.add("(653) 214-5698"); value2.add("www.yahoo.com");
	 * value2.add("$1,000,000"); ArrayList<String> data1 =
	 * SB.verifySDGRecord(sdgName, "Account 5", fieldName, value2); if
	 * (data1.isEmpty()) { log(LogStatus.PASS,
	 * "Page has been refreash and data is matched", YesNo.No); sa.assertTrue(true,
	 * "Page has been refreash and data is matched"); } else { log(LogStatus.ERROR,
	 * "Either Page is not refresh or data is not matched" + data1, YesNo.No);
	 * sa.assertTrue(false, "Either Page is not refresh or data is not matched" +
	 * data1); }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Error message is not visible on the hover to the triangle icon", YesNo.No);
	 * sa.assertTrue(false,
	 * "Error message is not visible on the hover to the triangle icon"); } } else {
	 * log(LogStatus.ERROR,
	 * "\"2 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * , YesNo.No); sa.assertTrue(false,
	 * "\"2 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * );
	 * 
	 * } }
	 * 
	 * else { log(LogStatus.ERROR, "Could not open the " + appPage +
	 * " from the App Launcher", YesNo.No); sa.assertTrue(false,
	 * "Could not open the " + appPage + " from the App Launcher");
	 * 
	 * }
	 * 
	 * lp.CRMlogout(); sa.assertAll();
	 * 
	 * }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void M9Tc082_UpdatePhoneForCompanyAndVerifyErrorMessage(String
	 * projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver);
	 * LightningAppBuilderPageBusinessLayer AppBuilder = new
	 * LightningAppBuilderPageBusinessLayer(driver);
	 * 
	 * String appPage = M9Tc082_AppPageName; String sdgName = M9Tc082_SDGTableName;
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); ThreadSleep(7000);
	 * AppBuilder.pageSizeSelect(sdgName, "100"); CommonLib.ThreadSleep(20000); if
	 * (SB.updatePhoneOnSDGRecordAndVerifyErrorMessage(sdgName, "Tata Motors",
	 * "Phone", "9876543321")) { log(LogStatus.INFO,
	 * "\"Tata Motors\" Phone record has been updated to Navatar 9876543321",
	 * YesNo.No); sa.assertTrue(true,
	 * "\"Tata Motors\" Phone record has been updated to Navatar 9876543321");
	 * CommonLib.ThreadSleep(7000); if
	 * (SB.verifyErrorMessageOnTriangleIconForPermission(sdgName, "Phone")) {
	 * log(LogStatus.PASS,
	 * "Error message is visible on the hover to the triangle icon", YesNo.No);
	 * sa.assertTrue(true,
	 * "Error message is visible on the hover to the triangle icon");
	 * 
	 * ArrayList<String> fieldName = new ArrayList<String>(); ArrayList<String>
	 * value1 = new ArrayList<String>(); fieldName.add("Phone"); value1.add("");
	 * ArrayList<String> data = SB.clickCancelBtnAndVerifyRecord(sdgName,
	 * "Account 3", fieldName, value1); if (data.isEmpty()) { log(LogStatus.PASS,
	 * "Page has been refreash and data is matched", YesNo.No); sa.assertTrue(true,
	 * "Page has been refreash and data is matched"); } else { log(LogStatus.ERROR,
	 * "Either Page is not refresh or data is not matched" + data, YesNo.No);
	 * sa.assertTrue(false, "Either Page is not refresh or data is not matched" +
	 * data); }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Error message is not visible on the hover to the triangle icon", YesNo.No);
	 * sa.assertTrue(false,
	 * "Error message is not visible on the hover to the triangle icon");
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * , YesNo.No); sa.assertTrue(false,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * );
	 * 
	 * } } else { log(LogStatus.ERROR,
	 * "Could not open the App from the App Launcher", YesNo.No);
	 * sa.assertTrue(false, "Could not open the App from the App Launcher"); }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void
	 * M9Tc083_UpdatePhoneWebSiteRevenueAndRefreashSDGAndVerifyRecord(String
	 * projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver); String appPage =
	 * M9Tc083_AppPageName; String sdgName = M9Tc083_SDGTableName;
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.upadatePhoneWebsiteRevenueAndVerifyErrorMessageSaveCancelButton(sdgName,
	 * "Account 1 Updated", "Phone", "9876543321", "Website", "www.yahoo.com",
	 * "Revenue", "1000000")) { log(LogStatus.PASS,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also visible"
	 * , YesNo.No); sa.assertTrue(true,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also visible"
	 * ); CommonLib.ThreadSleep(7000);
	 * 
	 * if (SB.verifyErrorMessageOnTriangleIconForPermission(sdgName, "Phone")) {
	 * log(LogStatus.PASS,
	 * "Error message is visible on the hover to the triangle icon", YesNo.No);
	 * sa.assertTrue(true,
	 * "Error message is visible on the hover to the triangle icon");
	 * 
	 * if (SB.reloadSDG(sdgName)) { CommonLib.ThreadSleep(5000); ArrayList<String>
	 * fieldName = new ArrayList<String>();
	 * 
	 * fieldName.add("Phone"); fieldName.add("Website"); fieldName.add("Revenue");
	 * 
	 * ArrayList<String> value = new ArrayList<String>();
	 * value.add("(653) 214-5698"); value.add("www.allsec.com");
	 * value.add("$1,000,000");
	 * 
	 * ArrayList<String> data = SB.VerifyRecordNotMatchedOnSDG(sdgName,
	 * "Account 1 Updated", fieldName, value); if (data.isEmpty()) {
	 * log(LogStatus.PASS,
	 * "Page has been refreash and data is not updated in the Account 1 Updated Field"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Page has been refreash and data is not updated in the Account 1 Updated Field"
	 * ); } else { log(LogStatus.ERROR,
	 * "Either Page is not refresh or data has been updated in the Account 1 Updated Record"
	 * + data, YesNo.No); sa.assertTrue(false,
	 * "Either Page is not refresh or data has been updated in the Account 1 Updated Record"
	 * + data); } } else { log(LogStatus.ERROR, "Could not refresh the page",
	 * YesNo.Yes); sa.assertTrue(false, "Could not refresh the page"); }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Error message is not visible on the hover to the triangle icon", YesNo.No);
	 * sa.assertTrue(false,
	 * "Error message is not visible on the hover to the triangle icon"); } }
	 * 
	 * else { log(LogStatus.ERROR,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * , YesNo.No); sa.assertTrue(false,
	 * "\"1 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * );
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not open the App from the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not open the App from the App Launcher");
	 * 
	 * } lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void
	 * M9Tc084_VerifyMultipleFieldWillNotEditSameTimeOnDifferentPage(String
	 * projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver); String appPage =
	 * M9Tc084_AppPageName; String sdgName = M9Tc084_SDGTableName;
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * if (SB.verifyMultipleFieldWillNotEditOnDifferentPage(sdgName, "Account 2",
	 * "Account 3", "AIH Limited", "AIG Group")) { log(LogStatus.PASS,
	 * "Multiple field is not editable on the multiple page", YesNo.No);
	 * sa.assertTrue(true, "Multiple field is not editable on the multiple page");
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "Multiple field is editable on the multiple page", YesNo.No);
	 * sa.assertTrue(false, "Multiple field is editable on the multiple page");
	 * 
	 * }
	 * 
	 * if (SB.
	 * clickTwoCheckboxupadatePhoneWebsiteRevenueAndVerifyErrorMessageSaveCancelButton
	 * (sdgName, "Account 1 Updated", "Account 8", "Phone", "6532145698", "Website",
	 * "www.yahoo.com", "Revenue", "1000000")) { log(LogStatus.PASS,
	 * "\"1 record has error. Kindly resolve them and try again.\", error message is visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also visible"
	 * , YesNo.No); sa.assertTrue(true,
	 * "\"1 record has error. Kindly resolve them and try again.\", error message is visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also visible"
	 * );
	 * 
	 * if (SB.verifyErrorMessageOnTriangleIconForPermission(sdgName, "Phone")) {
	 * log(LogStatus.PASS,
	 * "Error message is visible on the hover to the triangle icon", YesNo.No);
	 * sa.assertTrue(true,
	 * "Error message is visible on the hover to the triangle icon");
	 * 
	 * ArrayList<String> fieldName = new ArrayList<String>(); ArrayList<String>
	 * value1 = new ArrayList<String>(); fieldName.add("Phone");
	 * fieldName.add("Website"); fieldName.add("Revenue");
	 * 
	 * value1.add("(653) 214-5698"); value1.add("www.yahoo.com");
	 * value1.add("$1,000,000");
	 * 
	 * ArrayList<String> data = SB.clickCancelBtnAndVerifyRecordNotMatched(sdgName,
	 * "Account 1 Updated", fieldName, value1); if (data.isEmpty()) {
	 * log(LogStatus.PASS,
	 * "Page has been refreash and data is not updated in the Account 1 Updated Field"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Page has been refreash and data is not updated in the Account 1 Updated Field"
	 * ); } else { log(LogStatus.ERROR,
	 * "Either Page is not refresh or data has been updated in the Account 1 Updated Record"
	 * + data, YesNo.Yes); sa.assertTrue(false,
	 * "Either Page is not refresh or data has been updated in the Account 1 Updated Record"
	 * + data); }
	 * 
	 * ArrayList<String> value2 = new ArrayList<String>();
	 * value2.add("(653) 214-5698"); value2.add("www.yahoo.com");
	 * value2.add("$1,000,000"); ArrayList<String> data1 =
	 * SB.verifySDGRecord(sdgName, "Account 8", fieldName, value2); if
	 * (data1.isEmpty()) { log(LogStatus.PASS,
	 * "Page has been refreash and data is matched", YesNo.No); sa.assertTrue(true,
	 * "Page has been refreash and data is matched"); } else { log(LogStatus.ERROR,
	 * "Either Page is not refresh or data is not matched" + data1, YesNo.No);
	 * sa.assertTrue(false, "Either Page is not refresh or data is not matched" +
	 * data1); }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Error message is not visible on the hover to the triangle icon", YesNo.No);
	 * sa.assertTrue(false,
	 * "Error message is not visible on the hover to the triangle icon"); } } else {
	 * log(LogStatus.ERROR,
	 * "\"2 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * , YesNo.No); sa.assertTrue(false,
	 * "\"2 records has error. Kindly resolve them and try again.\", error message is not visible on record update of Legal Name, Website, Revenue field. Save and Cancel Button are also not visible"
	 * );
	 * 
	 * }
	 * 
	 * }
	 * 
	 * else { log(LogStatus.ERROR, "Could not open the App from the App Launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Could not open the App from the App Launcher");
	 * 
	 * } lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void M9Tc085_VerifyAllfieldsWillEditAtAameTimeOnSamePage(String
	 * projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver);
	 * InstitutionsPageBusinessLayer IB = new InstitutionsPageBusinessLayer(driver);
	 * String appPage = M9Tc085_AppPageName; String sdgName = M9Tc085_SDGTableName;
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * if (SB.verifyAllFieldEditAtSameTimeInWebsiteRevenue(sdgName,
	 * "Account 1 Updated", "Website", "www.yahoo.com", "Revenue", "1000000")) {
	 * log(LogStatus.PASS,
	 * "Data has been saved in all field at the same time. Save and Cancel Button are also visible"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Data has been saved in all field at the same time. Save and Cancel Button are also visible"
	 * );
	 * 
	 * ArrayList<String> recordName = new ArrayList<String>();
	 * recordName.add("Account 1 Updated"); recordName.add("Account 2");
	 * recordName.add("Account 3"); recordName.add("Account 4");
	 * recordName.add("Account 5"); recordName.add("Account 6");
	 * recordName.add("Account 7"); recordName.add("Account 8");
	 * recordName.add("Account 9"); recordName.add("AF Group Inc");
	 * 
	 * for (String record : recordName) {
	 * 
	 * if (BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
	 * log(LogStatus.INFO, TabName.InstituitonsTab.toString() +
	 * "Tab has been opened", YesNo.No);
	 * 
	 * if (BP.clickOnAlreadyCreated(environment, mode, TabName.InstituitonsTab,
	 * record, 50)) { log(LogStatus.INFO, "Clicked on the " + record + " name",
	 * YesNo.No); CommonLib.ThreadSleep(5000); ArrayList<String> labelName = new
	 * ArrayList<String>(); ArrayList<String> value = new ArrayList<String>();
	 * labelName.add("Website"); labelName.add("Annual Revenue");
	 * value.add("www.yahoo.com"); value.add("$1,000,000"); if
	 * (IB.verifyValueOnFirm(labelName, value)) { log(LogStatus.INFO,
	 * "data has been matched for the " + record + " record", YesNo.No);
	 * sa.assertTrue(true, "data is not matched for the " + record + " record"); }
	 * else { log(LogStatus.ERROR, "data is not matched for the " + record +
	 * " record", YesNo.Yes); sa.assertTrue(false, "data is not matched for the " +
	 * record + " record");
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.ERROR, "Could not click on the Account 2 name",
	 * YesNo.Yes); sa.assertTrue(false, "Could not click on the Account 2 name"); }
	 * }
	 * 
	 * else { log(LogStatus.ERROR, TabName.InstituitonsTab.toString() +
	 * " is not open", YesNo.Yes); sa.assertTrue(false,
	 * TabName.InstituitonsTab.toString() + " is not open"); }
	 * 
	 * } } else { log(LogStatus.ERROR,
	 * "Data is not saved in all field at the same time. Save and Cancel Button are also visible"
	 * , YesNo.No); sa.assertTrue(false,
	 * "Data is not saved in all field at the same time. Save and Cancel Button are also visible"
	 * );
	 * 
	 * } } else { log(LogStatus.ERROR,
	 * "Not able to open the app from the App launcher", YesNo.No);
	 * sa.assertTrue(false, "Not able to open the app from the App launcher");
	 * 
	 * }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void
	 * M9Tc086_CreateCustompicklistfieldAndSetCustomSourcefieldAsControllingField(
	 * String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); String parentWindow
	 * = ""; if (home.clickOnSetUpLink()) { parentWindow = switchOnWindow(driver);
	 * if (parentWindow == null) { sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create Field Set Component"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create Field Set Component"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component"
	 * ); } ThreadSleep(3000);
	 * 
	 * String[][] labelAndValues = { { M9FC_12_FieldType, M9FC_12_FieldLabel,
	 * excelLabel.Length.toString(), M9FC_12_FieldValues, M9FC_12_ObjectName } };
	 * for (String[] objects : labelAndValues) { String[][] valuesandLabel = { {
	 * objects[2], objects[3] } };
	 * 
	 * if (setup.addCustomFieldforFormula(environment, mode, objects[4],
	 * ObjectFeatureName.FieldAndRelationShip, objects[0], objects[1],
	 * valuesandLabel, null, null)) { log(LogStatus.PASS,
	 * "Field Object is created for :" + objects[1], YesNo.No); sa.assertTrue(true,
	 * "Field Object is created for :" + objects[1]); } else { log(LogStatus.ERROR,
	 * "Field Object is not created for :" + objects[1], YesNo.Yes);
	 * sa.assertTrue(false, "Field Object is not created for :" + objects[1]); } }
	 * 
	 * CommonLib.switchToDefaultContent(driver);
	 * 
	 * if (setup.searchStandardOrCustomObject(environment, mode, object.Firm)) {
	 * log(LogStatus.INFO, "Firm Object has been search", YesNo.No);
	 * 
	 * if (setup.clickOnObjectFeature(environment, mode, object.Firm,
	 * ObjectFeatureName.FieldAndRelationShip)) { log(LogStatus.INFO,
	 * "Field and relationship Feature has been open", YesNo.No); ArrayList<String>
	 * val = new ArrayList<String>(); val.add("Under Evaluation");
	 * val.add("Portfolio Company"); val.add("Declined"); val.add("Sold/Exited");
	 * val.add("New"); val.add("Due Diligence"); val.add("Qualified");
	 * val.add("Investment"); val.add("Watchlist");
	 * 
	 * if (setup.fieldDependencies(M9FC_12_FieldLabel, "Source", M9FC_12_FieldLabel,
	 * val)) { log(LogStatus.INFO, "Field Dependency has been created", YesNo.Yes);
	 * 
	 * driver.close(); driver.switchTo().window(parentWindow); } else {
	 * log(LogStatus.ERROR, "Not able to create the field Dependency", YesNo.Yes);
	 * sa.assertTrue(false, "Not able to create the field Dependency"); }
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Not able click on the field and relationship Feature Name", YesNo.Yes);
	 * sa.assertTrue(false,
	 * "Not able click on the field and relationship Feature Name"); } } else {
	 * log(LogStatus.ERROR, "Not able to search the object", YesNo.Yes);
	 * sa.assertTrue(false, "Not able to search the object"); } } else {
	 * log(LogStatus.ERROR, "Could not click on the setup Link", YesNo.Yes);
	 * sa.assertTrue(false, "Could not click on the setup Link"); } lp.CRMlogout();
	 * sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void M9Tc087_addFieldOnSDG(String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver); String sdgName =
	 * M9Tc087_SDGTableName; lp.CRMLogin(superAdminUserName, adminPassword,
	 * appName); if (BP.openAppFromAppLauchner(50, "Sortable Data Grids")) {
	 * 
	 * log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No); if
	 * (SB.openSDG(projectName, sdgName)) { log(LogStatus.INFO, sdgName +
	 * " has been open", YesNo.Yes);
	 * 
	 * if (SB.addFieldOnSDG(projectName, SDGLabels.APIName.toString(),
	 * "Custom_Status__c")) { log(LogStatus.INFO, "Successfully added fields on " +
	 * sdgName, YesNo.Yes); sa.assertTrue(true, "Successfully added fields on " +
	 * sdgName);
	 * 
	 * } else {
	 * 
	 * log(LogStatus.ERROR, "Not Able to add fields on SDG : " + sdgName,
	 * YesNo.Yes); sa.assertTrue(false, "Not Able to add fields on SDG : " +
	 * sdgName);
	 * 
	 * } } else { log(LogStatus.ERROR, "Not Able to open " + sdgName, YesNo.Yes);
	 * sa.assertTrue(false, "Not Able to open " + sdgName); }
	 * 
	 * } else {
	 * 
	 * log(LogStatus.ERROR, "Not Able to Click on Tab : " + TabName.SDGTab,
	 * YesNo.Yes); sa.assertTrue(false, "Not Able to Click on Tab : " +
	 * TabName.SDGTab);
	 * 
	 * } lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void M9Tc088_verifyLockedIconOnSourceAndStatusField(String
	 * projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver); String appPage =
	 * M9Tc088_AppPageName; String sdgName = M9Tc088_SDGTableName;
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) { log(LogStatus.INFO, appPage +
	 * " has been open from the App launcher", YesNo.No); CommonLib.refresh(driver);
	 * CommonLib.ThreadSleep(10000); if (SB.verifyEditOrLockedIconOnSDGData(sdgName,
	 * M9FC_12_FieldLabel, IconType.Locked)) { log(LogStatus.PASS,
	 * "Locked icon is visible on status", YesNo.No); sa.assertTrue(true,
	 * "Locked icon is visible on status"); } else { log(LogStatus.ERROR,
	 * "Locked icon is not visible on status", YesNo.No); sa.assertTrue(false,
	 * "Locked icon is not visible on status"); }
	 * 
	 * if (SB.verifyEditOrLockedIconOnSDGData(sdgName, "Source", IconType.Locked)) {
	 * log(LogStatus.PASS, "Locked icon is visible on Source", YesNo.No);
	 * sa.assertTrue(true, "Locked icon is visible on Source"); } else {
	 * log(LogStatus.ERROR, "Locked icon is not visible on Source", YesNo.No);
	 * sa.assertTrue(false, "Locked icon is not visible on Source"); } } else {
	 * log(LogStatus.ERROR, "Not able to open the sdg from the App launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Not able to open the sdg from the App launcher"); } lp.CRMlogout();
	 * sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test
	 * 
	 * public void M9Tc089_verifyValidation(String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver); String appPage =
	 * M9Tc089_AppPageName; String sdgName = M9Tc089_SDGTableName;
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName);
	 * 
	 * if (BP.openAppFromAppLauchner(appPage, 50)) {
	 * 
	 * log(LogStatus.INFO, appPage + " has been open from the App launcher",
	 * YesNo.No); CommonLib.refresh(driver); if
	 * (SB.VerifyValidationUpdateSDGRecordButValueshouldnotUpdate(sdgName,
	 * "Account 1 Updated", "Revenue", "Test of = Account 5")) { log(LogStatus.INFO,
	 * "Value is not changed. So we are not able to enter the character", YesNo.No);
	 * sa.assertTrue(true,
	 * "Value is not changed. So we are not able to enter the character");
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "Value has been changed. So we are able to enter the character", YesNo.Yes);
	 * sa.assertTrue(false,
	 * "Value has been changed. So we are able to enter the character");
	 * 
	 * }
	 * 
	 * CommonLib.refresh(driver);
	 * 
	 * if (SB.updateSDGRecordAndVerifySaveCancelButton(sdgName, "Account 1 Updated",
	 * "Revenue", "52369874", "Text", null)) { log(LogStatus.INFO,
	 * "\"Account 1 Updated\" Revenue record has been updated to 52369874",
	 * YesNo.No); sa.assertTrue(true,
	 * "\"Account 1 Updated\" Revenue record has been updated to 52369874");
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "\"Account 1 Updated\" Revenue record is not updated to 52369874", YesNo.No);
	 * sa.assertTrue(false,
	 * "\"Account 1 Updated\" Revenue record is not updated to 52369874");
	 * 
	 * }
	 * 
	 * CommonLib.refresh(driver);
	 * 
	 * if (SB.upadateSingleRecordVerifyErrorMessageSaveCancelButton(sdgName,
	 * "Account 1 Updated", "Revenue", "1234567890987654321")) { log(LogStatus.PASS,
	 * "\"1 record has error. Kindly resolve them and try again.\", error message is visible on record update of Revenue field. Save and Cancel Button are also visible"
	 * , YesNo.No); sa.assertTrue(true,
	 * "\"1 record has error. Kindly resolve them and try again.\", error message is visible on record update of Revenue field. Save and Cancel Button are also visible"
	 * );
	 * 
	 * if (SB.verifyErrorMessageOnTriangleIconForOutsideRange(sdgName,
	 * "Account 1 Updated")) { log(LogStatus.PASS,
	 * "outside range message has been verified on the hover of trianlge icon",
	 * YesNo.No); sa.assertTrue(true,
	 * "outside range message has been verified on the hover of trianlge icon");
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "outside range message is not visible on the hover of trianlge icon",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "outside range message is not visible on the hover of trianlge icon");
	 * 
	 * } } else { log(LogStatus.FAIL,
	 * "\"1 record has error. Kindly resolve them and try again.\", error message is not visible on record update of Revenue field. Save and Cancel Button are also visible"
	 * , YesNo.No); sa.assertTrue(false,
	 * "\"1 record has error. Kindly resolve them and try again.\", error message is not visible on record update of Revenue field."
	 * );
	 * 
	 * }
	 * 
	 * } else { log(LogStatus.FAIL,
	 * "Not able to open the App from the App Launcher", YesNo.Yes);
	 * sa.assertTrue(false, "Not able to open the App from the App Launcher");
	 * 
	 * }
	 * 
	 * lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc090_verifyLockedIconOnNumberOfContactField(String
	 * projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver); String appPage =
	 * M9Tc090_AppPageName; String sdgName = M9Tc090_SDGTableName;
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) { log(LogStatus.INFO, appPage +
	 * " has been open from the App launcher", YesNo.No); CommonLib.refresh(driver);
	 * CommonLib.ThreadSleep(10000); if (SB.verifyEditOrLockedIconOnSDGData(sdgName,
	 * "No of Contacts", IconType.Locked)) { log(LogStatus.PASS,
	 * "Locked icon is visible on No of Contacts", YesNo.No); sa.assertTrue(true,
	 * "Locked icon is visible on No of Contacts"); } else { log(LogStatus.ERROR,
	 * "Locked icon is not visible on No of Contacts", YesNo.No);
	 * sa.assertTrue(false, "Locked icon is not visible on No of Contacts"); } }
	 * else { log(LogStatus.ERROR, "Not able to open the sdg from the App launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Not able to open the sdg from the App launcher"); } lp.CRMlogout();
	 * sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc091_verifyLockedIconOnIntroducedByField(String
	 * projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver); String appPage =
	 * M9Tc091_AppPageName; String sdgName = M9Tc091_SDGTableName;
	 * 
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); if
	 * (BP.openAppFromAppLauchner(appPage, 50)) { log(LogStatus.INFO, appPage +
	 * " has been open from the App launcher", YesNo.No); CommonLib.refresh(driver);
	 * CommonLib.ThreadSleep(10000); if (SB.verifyEditOrLockedIconOnSDGData(sdgName,
	 * "Introduced By", IconType.Locked)) { log(LogStatus.PASS,
	 * "Locked icon is visible on Introduced By", YesNo.No); sa.assertTrue(true,
	 * "Locked icon is visible on Introduced By"); } else { log(LogStatus.ERROR,
	 * "Locked icon is not visible on Introduced By", YesNo.No);
	 * sa.assertTrue(false, "Locked icon is not visible on No of Contacts"); } }
	 * else { log(LogStatus.ERROR, "Not able to open the sdg from the App launcher",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "Not able to open the sdg from the App launcher"); } lp.CRMlogout();
	 * sa.assertAll(); }
	 */

}