package com.navatar.scripts;

import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;
import static com.navatar.generic.SmokeCommonVariables.todaysDate;
import static com.navatar.generic.SmokeCommonVariables.AdminUserEmailID;
import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.previousOrForwardDateAccordingToTimeZone;
import static com.navatar.generic.CommonLib.refresh;
import static com.navatar.generic.CommonLib.removeNumbersFromString;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonLib.switchToFrame;
import static com.navatar.generic.CommonVariables.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.BulkActions_DefaultValues;
import com.navatar.generic.EnumConstants.CommitmentType;
import com.navatar.generic.EnumConstants.DataImportType;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.FolderAccess;
import com.navatar.generic.EnumConstants.FundraisingContactPageTab;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.ObjectName;
import com.navatar.generic.EnumConstants.ObjectType;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.ReportDashboardFolderType;
import com.navatar.generic.EnumConstants.ReportField;
import com.navatar.generic.EnumConstants.ReportFormatName;
import com.navatar.generic.EnumConstants.SDGCreationLabel;
import com.navatar.generic.EnumConstants.SDGGridName;
import com.navatar.generic.EnumConstants.SDGLabels;
import com.navatar.generic.EnumConstants.SearchBasedOnExistingFundsOptions;
import com.navatar.generic.EnumConstants.SortOrder;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.searchContactInEmailProspectGrid;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.DataLoaderWizardPageBusinessLayer;
import com.navatar.pageObjects.EditPageBusinessLayer;
import com.navatar.pageObjects.HomePage;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.ReportsTabBusinessLayer;
import com.navatar.pageObjects.SDGPageBusinessLayer;
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
		try {
			if (home.clickOnSetUpLink()) {
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
					log(LogStatus.INFO, "CRM User is created Successfully: " + crmUser1FirstName + " " + UserLastName,
							YesNo.No);
					ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User1",
							excelLabel.User_Email);
					ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",
							excelLabel.User_Last_Name);
					flag = true;

				}

			}
		} catch (Exception e) {
			log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
		}

		if (flag) {
			flag = false;
			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				if (setup.installedPackages(crmUser1FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + crmUser1FirstName + " "
							+ UserLastName);
					flag = true;

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

			log(LogStatus.ERROR, "User Not Created, So Test Case Fail", YesNo.Yes);
			sa.assertTrue(false, "User Not Created, So Test Case Fail");

		}

		if (parentWindow != null) {
			driver.close();
			driver.switchTo().window(parentWindow);
		}
		if (flag) {
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
		} else {
			appLog.error("Not able to Create User/install PE package in CRM User1: " + crmUser1FirstName + " "
					+ UserLastName);
			sa.assertTrue(false, "Not able to Create User/install PE package in CRM User1: " + crmUser1FirstName + " "
					+ UserLastName);
			log(LogStatus.ERROR, "Not able to Create User/ install PE package in CRM User1: " + crmUser1FirstName + " "
					+ UserLastName, YesNo.Yes);
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
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User2",
				excelLabel.User_Last_Name);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		boolean flag = false;
		try {
			if (home.clickOnSetUpLink()) {
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
					log(LogStatus.INFO, "CRM User is created Successfully: " + crmUser2FirstName + " " + UserLastName,
							YesNo.No);
					ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User2",
							excelLabel.User_Email);
					ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User2",
							excelLabel.User_Last_Name);
					flag = true;

				}

			}
		} catch (Exception e) {
			log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
		}

		if (flag) {
			flag = false;
			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				if (setup.installedPackages(crmUser2FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + crmUser2FirstName + " "
							+ UserLastName);
					flag = true;

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

			log(LogStatus.ERROR, "User Not Created, So Test Case Fail", YesNo.Yes);
			sa.assertTrue(false, "User Not Created, So Test Case Fail");

		}

		if (parentWindow != null) {
			driver.close();
			driver.switchTo().window(parentWindow);
		}
		if (flag) {
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
				appLog.info("Password is set successfully for CRM User2: " + crmUser2FirstName + " " + UserLastName);
			} else {
				appLog.info("Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName);
				sa.assertTrue(false, "Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName);
				log(LogStatus.ERROR, "Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName,
						YesNo.Yes);
			}
		} else {
			appLog.error("Not able to Create User/install PE package in CRM User2: " + crmUser2FirstName + " "
					+ UserLastName);
			sa.assertTrue(false, "Not able to Create User/install PE package in CRM User2: " + crmUser2FirstName + " "
					+ UserLastName);
			log(LogStatus.ERROR, "Not able to Create User/ install PE package in CRM User2: " + crmUser2FirstName + " "
					+ UserLastName, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")

	@Test
	public void M9tc001_4_createCustomReport(String projectName) throws InterruptedException {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ReportsTabBusinessLayer report = new ReportsTabBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		ReportField[][] fields = { { ReportField.Fundraising_Name, ReportField.Fund_Name },
				{ ReportField.First_Name, ReportField.Last_Name, ReportField.Legal_Name, ReportField.Industry } };

		String datas[][] = {
				{ "Public Reports", "#Stage - Interested", "Fundraisings with Fund Name", "All fundraisings",
						"All Time", "Closing Date", "Fund Name: Fund Name<Break>Stage New", "equals<Break>equals",
						"Sumo Logic - Nov 2017<Break>Interested" },
				{ "Public Reports", "#Individuals", "Contacts & Firms", "All firms", "All Time", "Created Date",
						"Industry", "equals", "Agriculture" } };

		String[] splitedReportName;
		int i = 0;
		for (String[] data : datas) {

			splitedReportName = removeNumbersFromString("Test Module 9 Report");
			SmokeReport2Name = splitedReportName[0] + lp.generateRandomNumber();

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
				appLog.error("Not able to create Custom Report : " + SmokeReport2Name);
				sa.assertTrue(false, "Not able to create Custom Report : " + SmokeReport2Name);
				log(LogStatus.ERROR, "Not able to create Custom Report : " + SmokeReport2Name, YesNo.Yes);
			}

			i++;
		}

		home.switchToLighting();

		lp.CRMlogout();
		sa.assertAll();

	}

	/*
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * M9Tc001_2_verifyPreconditionRecordsDataImportWizardAdmin(String projectName)
	 * { SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * DataLoaderWizardPageBusinessLayer dataload = new
	 * DataLoaderWizardPageBusinessLayer(driver); CustomObjPageBusinessLayer
	 * customObjPageBusinessLayer = new CustomObjPageBusinessLayer(driver);
	 * 
	 * customObjPageBusinessLayer.CreateACustomObject(projectName,
	 * M9FC_1_ObjectName);
	 * customObjPageBusinessLayer.CreateACustomObject(projectName,
	 * M9FC_10_ObjectName); String[][] labelAndValues= {
	 * {M9FC_1_FieldType,M9FC_1_FieldLabel,excelLabel.Length.toString(),
	 * M9FC_1_FieldValues,M9FC_1_ObjectName},
	 * {M9FC_2_FieldType,M9FC_2_FieldLabel,excelLabel.Length.toString(),
	 * M9FC_2_FieldValues,M9FC_2_ObjectName},
	 * {M9FC_3_FieldType,M9FC_3_FieldLabel,excelLabel.Length.toString(),
	 * M9FC_3_FieldValues,M9FC_3_ObjectName},
	 * {M9FC_4_FieldType,M9FC_4_FieldLabel,excelLabel.Length.toString(),
	 * M9FC_4_FieldValues,M9FC_4_ObjectName},
	 * {M9FC_5_FieldType,M9FC_5_FieldLabel,excelLabel.Length.toString(),
	 * M9FC_5_FieldValues,M9FC_5_ObjectName},
	 * {M9FC_6_FieldType,M9FC_6_FieldLabel,excelLabel.Related_To.toString(),
	 * M9FC_6_FieldValues,M9FC_6_ObjectName},
	 * {M9FC_7_FieldType,M9FC_7_FieldLabel,excelLabel.Length.toString(),
	 * M9FC_7_FieldValues,M9FC_7_ObjectName},
	 * {M9FC_8_FieldType,M9FC_8_FieldLabel,excelLabel.Length.toString(),
	 * M9FC_8_FieldValues,M9FC_8_ObjectName},
	 * {M9FC_9_FieldType,M9FC_9_FieldLabel,excelLabel.Length.toString(),
	 * M9FC_9_FieldValues,M9FC_9_ObjectName},
	 * {M9FC_10_FieldType,M9FC_10_FieldLabel,excelLabel.Related_To.toString(),
	 * M9FC_10_FieldValues,M9FC_10_ObjectName},
	 * {M9FC_11_FieldType,M9FC_11_FieldLabel,excelLabel.Related_To.toString(),
	 * M9FC_11_FieldValues,M9FC_11_ObjectName}};
	 * 
	 * 
	 * setup.createFieldsForCustomObject(projectName, labelAndValues);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
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
	 * 
	 * 
	 * }
	 */

	@Parameters({ "projectName" })

	@Test
	public void M9Tc002_Create_Fund_First_SDG_Grid(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String fields = SDGLabels.APIName.toString();
		String values = "";
		lp.searchAndClickOnApp(SDG, 30);

		String[] splitedSDGName;
		String SDGRandomTagName;
		splitedSDGName = removeNumbersFromString("Test SDG Module 9");
		SDGRandomTagName = splitedSDGName[0] + lp.generateRandomNumber();

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);
			String sdgName = "Fund - First SDG Grid New";
			String sdgTag = "Fund - First SDG Grid New";
			String[][] sdgLabels = { { SDGCreationLabel.SDG_Name.toString(), sdgName },
					{ SDGCreationLabel.SDG_Tag.toString(), sdgTag },
					{ SDGCreationLabel.sObjectName.toString(), "navpeII__Fund__c" },
					{ SDGCreationLabel.Default_Sort.toString(), "Name Desc" } };

			if (sdg.createCustomSDG(projectName, sdgName, sdgLabels, action.BOOLEAN, 20)) {
				log(LogStatus.PASS, "create/verify created SDG : " + sdgName, YesNo.No);

				for (int i = 0; i < 1; i++) {
					String api = "Name";
					values = api;
					if (sdg.addFieldOnSDG(projectName, fields, values)) {
						log(LogStatus.INFO, "Successfully added fields on " + sdgName, YesNo.Yes);

					} else {
						sa.assertTrue(false, "Not Able to add fields on SDG : " + sdgName);
						log(LogStatus.SKIP, "Not Able to add fields on SDG : " + sdgName, YesNo.Yes);
					}
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
		String fields = SDGLabels.APIName.toString();
		String values = "";
		String sdgName = "Fund - First SDG Grid New";
		lp.searchAndClickOnApp(SDG, 30);

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);

			String[][] sdgLabels1 = { { SDGCreationLabel.Filter.toString(), "Fund1" },
					{ SDGCreationLabel.List_View_Name.toString(), "Fund_Type" } };

			String[][] sdgLabels2 = { { SDGCreationLabel.Filter.toString(), "Name <> 'Fund2'" },
					{ SDGCreationLabel.List_View_Name.toString(), "Launched_Date" } };

			String[][] sdgLabels3 = { { SDGCreationLabel.Filter.toString(), "" },
					{ SDGCreationLabel.List_View_Name.toString(), "" } };
			String[][][] sdgLabels = { sdgLabels1, sdgLabels2, sdgLabels3 };

			/*
			 * NOTE: editCustomSDGandFoundErrorMsg: Method Validate Error Msg, Also if
			 * labelWithValue[0][1] !="" Then it will save SDG and validate the headers
			 */

			if (sdg.editCustomSDGandFoundErrorMsgAndAtLastWithoutError(projectName, sdgName, sdgLabels, action.BOOLEAN,
					20, "You can either fill 'Filter' or 'List View Name' to save the record.")) {
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
	public void M9Tc004_ValidateErrorMsg_Fund_First_SDG_Grid(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String fields = SDGLabels.APIName.toString();
		String values = "";
		String sdgName = "Fund - First SDG Grid New";
		lp.searchAndClickOnApp(SDG, 30);

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);

			String[][] sdgLabels1 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), "navpeII__X1st_Closing_Date__c" } };
			String[][] sdgLabels2 = { { SDGCreationLabel.Parent_Field_Name.toString(), "Active__c" } };
			String[][] sdgLabels3 = { { SDGCreationLabel.Parent_Field_Name.toString(), "Custom_Mpick_list__c" } };
			String[][] sdgLabels4 = { { SDGCreationLabel.Parent_Field_Name.toString(), "navpeII__Fund_Type__c" } };
			String[][] sdgLabels5 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), "navpeII__Remaining_Commitments_USD_mn__c" } };
			String[][] sdgLabels6 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), "navpeII__Target_Commitments_USD_mn__c" } };
			String[][] sdgLabels7 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), "navpeII__Total_Capital_Called_mn__c" } };
			String[][] sdgLabels8 = {
					{ SDGCreationLabel.Parent_Field_Name.toString(), "navpeII__Total_Commitment__c" } };

			String[][][] sdgLabels = { sdgLabels1, sdgLabels2, sdgLabels3, sdgLabels4, sdgLabels5, sdgLabels6,
					sdgLabels7, sdgLabels8 };

//	  NOTE: editCustomSDGandFoundErrorMsg: Method Validate Error Msg

			if (sdg.editCustomSDGandFoundErrorMsg(projectName, sdgName, sdgLabels, action.BOOLEAN, 30,
					"must be a Reference, ID, String or Text Area field")) {
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
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] fieldsInSDG = { "FUND NAME", "OWNER NAME", "FUND TYPE", "POINT OF CONTACT", "1ST CLOSING DATE",
				"SECTOR", "REGION", "CUSTOM MPICK_LIST", "TARGET - C@MMITMENTS", "#TARGET CLOSED",
				"#STAGE - INTERESTED", "#CLOSING - 3RD CLOSING", "#TOTAL LOW INVESTMENT AMOUNT",
				"#MAXINVESTOR'SAMOUNT($MN)", "#FINANCEBUYERS", "#FORMULA - COMMITMENT" };
		String TitleOfSDG = "Fund - First SDG Grid";

		List<String> columnsInSDG = Arrays.asList(fieldsInSDG);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);
			if (alreadyAddedComponentToHomePage != null) {

				log(LogStatus.ERROR, "------------Component Already Added to Home Page, So Not adding Component: "
						+ TitleOfSDG + "----------------", YesNo.Yes);
				sa.assertTrue(false, "------------Component Already Added to Home Page, So Not adding Component: "
						+ TitleOfSDG + "---------------");
			}

			else {

				if (edit.clickOnEditPageLink()) {
					log(LogStatus.INFO, "Component Not Already Added to Home Page, So adding Component: " + TitleOfSDG,
							YesNo.No);
					if (edit.addSDGComponentToRefrencedComponentAndVerifyColumnsAndNoOfRecords(projectName,
							columnsInSDG, "Navatar SDG", TitleOfSDG, "Fund - First SDG Grid", 84)) {
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
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] fieldsInSDG = { "FUND NAME", "OWNER NAME", "FUND TYPE", "POINT OF CONTACT", "1ST CLOSING DATE",
				"SECTOR", "REGION", "CUSTOM MPICK_LIST", "TARGET - C@MMITMENTS", "#TARGET CLOSED",
				"#STAGE - INTERESTED", "#CLOSING - 3RD CLOSING", "#TOTAL LOW INVESTMENT AMOUNT",
				"#MAXINVESTOR'SAMOUNT($MN)", "#FINANCEBUYERS", "#FORMULA - COMMITMENT" };
		String TitleOfSDG = "Fund - First SDG Grid";
		Boolean flag = false;
		int status = 0;
		List<String> columnsInSDG = Arrays.asList(fieldsInSDG);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + TitleOfSDG + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);
			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO,
						"------------Component Already Added to Home Page " + TitleOfSDG + "----------------",
						YesNo.Yes);
				sa.assertTrue(true,
						"------------Component Already Added to Home Page " + TitleOfSDG + "---------------");

				if (home.verifySDGExpandByDefault(TitleOfSDG)) {
					log(LogStatus.PASS, "-----------SDG: " + TitleOfSDG + " is Expanded By Default--------------",
							YesNo.No);
					sa.assertTrue(true, "-----------SDG: " + TitleOfSDG + " is Expanded By Default--------------");

				}

				else {
					log(LogStatus.FAIL, "-----------SDG: " + TitleOfSDG + " is not Expanded By Default--------------",
							YesNo.No);
					sa.assertTrue(false, "-----------SDG: " + TitleOfSDG + " is not Expanded By Default--------------");
					status++;
				}
				if (home.verifyColumnsOfSDG(TitleOfSDG, columnsInSDG)) {
					log(LogStatus.PASS, "-------Columns of SDG: " + TitleOfSDG + "are Matched--------", YesNo.No);
					sa.assertTrue(true, "-------Columns of SDG: " + TitleOfSDG + " are Matched-------");

				} else {
					log(LogStatus.FAIL, "------Columns of SDG: " + TitleOfSDG + " are not Matched------", YesNo.Yes);
					sa.assertTrue(false, "------Columns of SDG: " + TitleOfSDG + " are not Matched------");
					status++;
				}
				int row = 2;
				if (home.verifySDGTooltipForARecord(TitleOfSDG, row)) {
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
					status++;
				}

				if (lp.clickOnTab(projectName, TabName.InstituitonsTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
					if (lp.clickOnTab(projectName, TabName.HomeTab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
						if (home.verifyCollapseTooltipAFterGoingToInstitutionPageAndComingBack(TitleOfSDG)) {
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
							status++;
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
						if (home.verifyCollapseTooltipAFterGoingToInstitutionPageAndComingBack(TitleOfSDG)) {
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
							status++;
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Switch to Lightning Mode", YesNo.No);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Switch to Classic Mode", YesNo.No);
				}

				if (home.verifySDGTooltipForExpandAndCollapse(TitleOfSDG)) {
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
					status++;
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
		HashMap<String, String> map = new HashMap<>();

		String[] Emails = { superAdminUserName, crmUser2EmailID };

		for (String Email : Emails) {
			lp.CRMLogin(Email, adminPassword, appName);

			String TitleOfSDG = "Fund - First SDG Grid";

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

		String[] fieldsInSDG = { "FUND NAME", "OWNER NAME", "FUND TYPE", "POINT OF CONTACT", "1ST CLOSING DATE",
				"SECTOR", "REGION", "TARGET - C@MMITMENTS" };
		List<String> columnInSDG = Arrays.asList(fieldsInSDG);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String TitleOfSDG = "Fund - First SDG Grid";

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

				home.verifyColumnAscendingDescendingOrder(SDGGridName.Fund_First_SDG, columnInSDG);

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
	public void M9Tc009_ValidateNumberOfRecordsAfterEnterLitViewNameSDGComponent_Fund_First_SDG_Grid_InHomepage(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		HomePage hp = new HomePage(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String sdgName = "Fund - First SDG Grid";

		lp.searchAndClickOnApp(SDG, 30);

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);

			String[][] sdgLabels = { { SDGCreationLabel.List_View_Name.toString(), "Type_Fund" } };

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

								int numberOfRecords = 76;
								if (home.numberOfRecordsMatch(sdgName, 76)) {
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

		String sdgName = "Fund - First SDG Grid";

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

						int numberOfRecords = 76;
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
		String[][][] sdgLabels = { { { SDGCreationLabel.List_View_Name.toString(), "InvalidListViewName" } },
				{ { SDGCreationLabel.List_View_Name.toString(), "Launched_Date" } } };
		String sdgName = "Fund - First SDG Grid";
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

									if (sdgLabel[0][1].equals("InvalidListViewName")) {

										int numberOfRecords = 84;
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
									} else if (sdgLabel[0][1].equals("Launched_Date")) {

										int numberOfRecords = 2;
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
		}

		lp.CRMlogout();
		sa.assertAll();
	}

}