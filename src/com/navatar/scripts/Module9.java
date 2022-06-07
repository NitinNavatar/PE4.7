package com.navatar.scripts;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.FindElements;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.clickUsingJavaScript;
import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.removeNumbersFromString;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonVariables.*;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.navatar.generic.EnumConstants.Condition;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.HTMLTAG;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PermissionType;
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
import com.navatar.pageObjects.DataLoaderWizardPageBusinessLayer;
import com.navatar.pageObjects.EditPageBusinessLayer;
import com.navatar.pageObjects.FieldAndRelationshipPageBusinessLayer;
import com.navatar.pageObjects.FundsPage;
import com.navatar.pageObjects.HomePage;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.LightningAppBuilderPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.ReportsTab;
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

	@Parameters("projectName")

	@Test
	public void M9tc001_4_createCustomReports(String projectName) throws InterruptedException {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ReportsTabBusinessLayer report = new ReportsTabBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		ReportField[][] fields = { { ReportField.Fundraising_Name, ReportField.Fund_Name },
				{ ReportField.First_Name, ReportField.Last_Name, ReportField.Legal_Name, ReportField.Industry } };

		String datas[][] = {
				{ "Public Reports", "#Stage - Interested", "Fundraisings with Fund Name", "All fundraisings",
						"All Time", "Closing Date", "Fund Name: Fund Name<Break>Stage", "equals<Break>equals",
						"Sumo Logic - Nov 2017<Break>Interested" },
				{ "Public Reports", "#Individuals", "Contacts & Firms", "All firms", "All Time", "Created Date",
						"Industry", "equals", "Agriculture" } };

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
	 * @Parameters({"projectName"})
	 * 
	 * @Test public void
	 * 
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
	public void M9Tc002_1_Create_Fund_First_SDG_Grid(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String fields = SDGLabels.APIName.toString();
		String values = "";
		lp.searchAndClickOnApp(SDG, 30);

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

			if (sdg.editCustomSDGandFoundErrorMsgAndAtLastWithoutError(projectName, "Fund - First SDG Grid", sdgLabels,
					action.BOOLEAN, 20, "You can either fill 'Filter' or 'List View Name' to save the record.")) {
				log(LogStatus.PASS, "edit/verify created SDG : " + "Fund - First SDG Grid", YesNo.No);
				sa.assertTrue(true, "Able to edit SDG and Find error Msg for SDG : " + Sdg1Name);

			} else {
				sa.assertTrue(false, "Not Able to edit/verify created SDG : " + Sdg1Name);
				log(LogStatus.SKIP, "Not Able to edit/verify created SDG : " + Sdg1Name, YesNo.Yes);
				sa.assertTrue(false, "Not Able to edit SDG and Find error Msg for SDG " + Sdg1Name);
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

			if (sdg.editCustomSDGandFoundErrorMsg(projectName, "Fund - First SDG Grid", sdgLabels, action.BOOLEAN, 20,
					"must be a Reference, ID, String or Text Area field")) {
				log(LogStatus.PASS, "edit/verify created SDG : " + "Fund - First SDG Grid", YesNo.No);
				sa.assertTrue(true, "Able to edit SDG and Find error Msg for SDG : " + Sdg1Name);

			} else {
				sa.assertTrue(false, "Not Able to edit/verify created SDG : " + Sdg1Name);
				log(LogStatus.SKIP, "Not Able to edit/verify created SDG : " + Sdg1Name, YesNo.Yes);
				sa.assertTrue(false, "Not Able to edit SDG and Find error Msg for SDG " + Sdg1Name);
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
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

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

				if (edit.verifySDGExpandByDefault(TitleOfSDG)) {
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
				if (edit.verifyColumnsOfSDG(TitleOfSDG, columnsInSDG)) {
					log(LogStatus.PASS, "-------Columns of SDG: " + TitleOfSDG + "are Matched--------", YesNo.No);
					sa.assertTrue(true, "-------Columns of SDG: " + TitleOfSDG + " are Matched-------");

				} else {
					log(LogStatus.FAIL, "------Columns of SDG: " + TitleOfSDG + " are not Matched------", YesNo.Yes);
					sa.assertTrue(false, "------Columns of SDG: " + TitleOfSDG + " are not Matched------");
					status++;
				}
				int row = 2;
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
					status++;
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
							status++;
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

	@Parameters({ "projectName" })

	@Test
	public void M9tc001_3_AddListViews(String projectName) {
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

	@Parameters({ "projectName" })

	@Test
	public void M9Tc053_CreateAppPageAndAddSDG(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		String labelName = "Test App Page";
		String tableName = "Test";

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

			if (setup.searchStandardOrCustomObject(projectName, mode, object.Lightning_App_Builder)) {
				if (AppBuilder.CreateAppPage(projectName, mode, labelName, tableName, parentWindowID)) {
					log(LogStatus.PASS, "App Page has been Created : " + labelName, YesNo.Yes);
					sa.assertTrue(true, "App Page has been Created");
				} else {
					log(LogStatus.ERROR, "App Page is not created : " + labelName, YesNo.Yes);
					sa.assertTrue(false, "App Page is not created : " + labelName);
				}
			} else {
				log(LogStatus.ERROR, "Not able to search the Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to search the Object");
			}

		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc054_VerifySDGDataOnAppPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		String appPage = "Test App Page";
		String tableName = "Test";
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[][] val = {
				{ M9SDGD_1_AccountIndustry, M9SDGD_1_Totalfirm, M9SDGD_1_Task_as_per_Industries, M9SDGD_1_Individuals,
						M9SDGD_1_Fundraising_as_per_Industries },
				{ M9SDGD_2_AccountIndustry, M9SDGD_2_Totalfirm, M9SDGD_2_Task_as_per_Industries, M9SDGD_2_Individuals,
						M9SDGD_2_Fundraising_as_per_Industries },
				{ M9SDGD_3_AccountIndustry, M9SDGD_3_Totalfirm, M9SDGD_3_Task_as_per_Industries, M9SDGD_3_Individuals,
						M9SDGD_3_Fundraising_as_per_Industries },
				{ M9SDGD_4_AccountIndustry, M9SDGD_4_Totalfirm, M9SDGD_4_Task_as_per_Industries, M9SDGD_4_Individuals,
						M9SDGD_4_Fundraising_as_per_Industries },
				{ M9SDGD_5_AccountIndustry, M9SDGD_5_Totalfirm, M9SDGD_5_Task_as_per_Industries, M9SDGD_5_Individuals,
						M9SDGD_5_Fundraising_as_per_Industries },
				{ M9SDGD_6_AccountIndustry, M9SDGD_6_Totalfirm, M9SDGD_6_Task_as_per_Industries, M9SDGD_6_Individuals,
						M9SDGD_6_Fundraising_as_per_Industries },
				{ M9SDGD_7_AccountIndustry, M9SDGD_7_Totalfirm, M9SDGD_7_Task_as_per_Industries, M9SDGD_7_Individuals,
						M9SDGD_7_Fundraising_as_per_Industries },
				{ M9SDGD_8_AccountIndustry, M9SDGD_8_Totalfirm, M9SDGD_8_Task_as_per_Industries, M9SDGD_8_Individuals,
						M9SDGD_8_Fundraising_as_per_Industries },
				{ M9SDGD_9_AccountIndustry, M9SDGD_9_Totalfirm, M9SDGD_9_Task_as_per_Industries, M9SDGD_9_Individuals,
						M9SDGD_9_Fundraising_as_per_Industries },
				{ M9SDGD_10_AccountIndustry, M9SDGD_10_Totalfirm, M9SDGD_10_Task_as_per_Industries,
						M9SDGD_10_Individuals, M9SDGD_10_Fundraising_as_per_Industries },
				{ M9SDGD_11_AccountIndustry, M9SDGD_11_Totalfirm, M9SDGD_11_Task_as_per_Industries,
						M9SDGD_11_Individuals, M9SDGD_11_Fundraising_as_per_Industries },
				{ M9SDGD_12_AccountIndustry, M9SDGD_12_Totalfirm, M9SDGD_12_Task_as_per_Industries,
						M9SDGD_12_Individuals, M9SDGD_12_Fundraising_as_per_Industries },
				{ M9SDGD_13_AccountIndustry, M9SDGD_13_Totalfirm, M9SDGD_13_Task_as_per_Industries,
						M9SDGD_13_Individuals, M9SDGD_13_Fundraising_as_per_Industries },
				{ M9SDGD_14_AccountIndustry, M9SDGD_14_Totalfirm, M9SDGD_14_Task_as_per_Industries,
						M9SDGD_14_Individuals, M9SDGD_14_Fundraising_as_per_Industries },
				{ M9SDGD_15_AccountIndustry, M9SDGD_15_Totalfirm, M9SDGD_15_Task_as_per_Industries,
						M9SDGD_15_Individuals, M9SDGD_15_Fundraising_as_per_Industries },
				{ M9SDGD_16_AccountIndustry, M9SDGD_16_Totalfirm, M9SDGD_16_Task_as_per_Industries,
						M9SDGD_16_Individuals, M9SDGD_16_Fundraising_as_per_Industries },
				{ M9SDGD_17_AccountIndustry, M9SDGD_17_Totalfirm, M9SDGD_17_Task_as_per_Industries,
						M9SDGD_17_Individuals, M9SDGD_17_Fundraising_as_per_Industries },
				{ M9SDGD_18_AccountIndustry, M9SDGD_18_Totalfirm, M9SDGD_18_Task_as_per_Industries,
						M9SDGD_18_Individuals, M9SDGD_18_Fundraising_as_per_Industries },
				{ M9SDGD_19_AccountIndustry, M9SDGD_19_Totalfirm, M9SDGD_19_Task_as_per_Industries,
						M9SDGD_19_Individuals, M9SDGD_19_Fundraising_as_per_Industries },
				{ M9SDGD_20_AccountIndustry, M9SDGD_20_Totalfirm, M9SDGD_20_Task_as_per_Industries,
						M9SDGD_20_Individuals, M9SDGD_20_Fundraising_as_per_Industries } };

		if (BP.openAppFromAppLauchner(appPage, 50)) {
			ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName, val);
			if (Data == null) {
				log(LogStatus.PASS, "SDG Data has been Matched", YesNo.No);
				sa.assertTrue(true, "SDG Data has been Matched");
			} else {
				log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
				sa.assertTrue(false, "SDG Data is not Matched : " + Data);
			}
		} else {
			log(LogStatus.ERROR, "Could not opened the App from the App Launcher", YesNo.Yes);
			sa.assertTrue(false, "Could not opened the App from the App Launcher");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc055_DeleteContactAndVerifySDGDataOnAppPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		String appPage = "Test App Page";
		String tableName = "Test";

		String[][] val = {
				{ M9SDGD_21_AccountIndustry, M9SDGD_21_Totalfirm, M9SDGD_21_Task_as_per_Industries,
						M9SDGD_21_Individuals, M9SDGD_21_Fundraising_as_per_Industries },
				{ M9SDGD_22_AccountIndustry, M9SDGD_22_Totalfirm, M9SDGD_22_Task_as_per_Industries,
						M9SDGD_22_Individuals, M9SDGD_22_Fundraising_as_per_Industries },
				{ M9SDGD_23_AccountIndustry, M9SDGD_23_Totalfirm, M9SDGD_23_Task_as_per_Industries,
						M9SDGD_23_Individuals, M9SDGD_23_Fundraising_as_per_Industries },
				{ M9SDGD_24_AccountIndustry, M9SDGD_24_Totalfirm, M9SDGD_24_Task_as_per_Industries,
						M9SDGD_24_Individuals, M9SDGD_24_Fundraising_as_per_Industries },
				{ M9SDGD_25_AccountIndustry, M9SDGD_25_Totalfirm, M9SDGD_25_Task_as_per_Industries,
						M9SDGD_25_Individuals, M9SDGD_25_Fundraising_as_per_Industries },
				{ M9SDGD_26_AccountIndustry, M9SDGD_26_Totalfirm, M9SDGD_26_Task_as_per_Industries,
						M9SDGD_26_Individuals, M9SDGD_26_Fundraising_as_per_Industries },
				{ M9SDGD_27_AccountIndustry, M9SDGD_27_Totalfirm, M9SDGD_27_Task_as_per_Industries,
						M9SDGD_27_Individuals, M9SDGD_27_Fundraising_as_per_Industries },
				{ M9SDGD_28_AccountIndustry, M9SDGD_28_Totalfirm, M9SDGD_28_Task_as_per_Industries,
						M9SDGD_28_Individuals, M9SDGD_28_Fundraising_as_per_Industries },
				{ M9SDGD_29_AccountIndustry, M9SDGD_29_Totalfirm, M9SDGD_29_Task_as_per_Industries,
						M9SDGD_29_Individuals, M9SDGD_29_Fundraising_as_per_Industries },
				{ M9SDGD_30_AccountIndustry, M9SDGD_30_Totalfirm, M9SDGD_30_Task_as_per_Industries,
						M9SDGD_30_Individuals, M9SDGD_30_Fundraising_as_per_Industries },
				{ M9SDGD_31_AccountIndustry, M9SDGD_31_Totalfirm, M9SDGD_31_Task_as_per_Industries,
						M9SDGD_31_Individuals, M9SDGD_31_Fundraising_as_per_Industries },
				{ M9SDGD_32_AccountIndustry, M9SDGD_32_Totalfirm, M9SDGD_32_Task_as_per_Industries,
						M9SDGD_32_Individuals, M9SDGD_32_Fundraising_as_per_Industries },
				{ M9SDGD_33_AccountIndustry, M9SDGD_33_Totalfirm, M9SDGD_33_Task_as_per_Industries,
						M9SDGD_33_Individuals, M9SDGD_33_Fundraising_as_per_Industries },
				{ M9SDGD_34_AccountIndustry, M9SDGD_34_Totalfirm, M9SDGD_34_Task_as_per_Industries,
						M9SDGD_34_Individuals, M9SDGD_34_Fundraising_as_per_Industries },
				{ M9SDGD_35_AccountIndustry, M9SDGD_35_Totalfirm, M9SDGD_35_Task_as_per_Industries,
						M9SDGD_35_Individuals, M9SDGD_35_Fundraising_as_per_Industries },
				{ M9SDGD_36_AccountIndustry, M9SDGD_36_Totalfirm, M9SDGD_36_Task_as_per_Industries,
						M9SDGD_36_Individuals, M9SDGD_36_Fundraising_as_per_Industries },
				{ M9SDGD_37_AccountIndustry, M9SDGD_37_Totalfirm, M9SDGD_37_Task_as_per_Industries,
						M9SDGD_37_Individuals, M9SDGD_37_Fundraising_as_per_Industries },
				{ M9SDGD_38_AccountIndustry, M9SDGD_38_Totalfirm, M9SDGD_38_Task_as_per_Industries,
						M9SDGD_38_Individuals, M9SDGD_38_Fundraising_as_per_Industries },
				{ M9SDGD_39_AccountIndustry, M9SDGD_39_Totalfirm, M9SDGD_39_Task_as_per_Industries,
						M9SDGD_39_Individuals, M9SDGD_39_Fundraising_as_per_Industries },
				{ M9SDGD_40_AccountIndustry, M9SDGD_40_Totalfirm, M9SDGD_40_Task_as_per_Industries,
						M9SDGD_40_Individuals, M9SDGD_40_Fundraising_as_per_Industries } };

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (con.clickOnCreatedContact(projectName, M9_Con1_FName, M9_Con1_LName)) {
				if (con.deleteContact(projectName, M9_Con1_FName, M9_Con1_LName)) {
					log(LogStatus.PASS, "Contact has been deleted", YesNo.Yes);
					sa.assertTrue(true, "Contact has been deleted");
					if (BP.openAppFromAppLauchner(appPage, 50)) {
						ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage,
								tableName, val);
						if (Data == null) {
							log(LogStatus.PASS, "SDG Data has been Matched", YesNo.No);
							sa.assertTrue(true, "SDG Data has been Matched");
						} else {
							log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
							sa.assertTrue(false, "SDG Data is not Matched : " + Data);
						}

					} else {
						log(LogStatus.ERROR, "Could not Opened the App Launcher", YesNo.Yes);
						sa.assertTrue(false, "Could not Opened the App Launcher");
					}
				} else {
					log(LogStatus.ERROR, "Could not delete the Contact", YesNo.Yes);
					sa.assertTrue(false, "Could not delete the Contact");
				}
			}

			else {
				log(LogStatus.ERROR, "Could not click on the contact", YesNo.Yes);
				sa.assertTrue(false, "Could not click on the contact");
			}
		} else {
			log(LogStatus.ERROR, "Could not click Tab", YesNo.Yes);
			sa.assertTrue(false, "Could not click Tab");

		}
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc056_checkAllRowAndVerifySDGDataOnAppPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		SDGPageBusinessLayer sd = new SDGPageBusinessLayer(driver);
		String appPage = "Test App Page";
		String tableName = "Test";

		String[][] val = {
				{ M9SDGD_21_AccountIndustry, M9SDGD_21_Totalfirm, M9SDGD_21_Task_as_per_Industries,
						M9SDGD_21_Individuals, M9SDGD_21_Fundraising_as_per_Industries },
				{ M9SDGD_22_AccountIndustry, M9SDGD_22_Totalfirm, M9SDGD_22_Task_as_per_Industries,
						M9SDGD_22_Individuals, M9SDGD_22_Fundraising_as_per_Industries },
				{ M9SDGD_23_AccountIndustry, M9SDGD_23_Totalfirm, M9SDGD_23_Task_as_per_Industries,
						M9SDGD_23_Individuals, M9SDGD_23_Fundraising_as_per_Industries },
				{ M9SDGD_24_AccountIndustry, M9SDGD_24_Totalfirm, M9SDGD_24_Task_as_per_Industries,
						M9SDGD_24_Individuals, M9SDGD_24_Fundraising_as_per_Industries },
				{ M9SDGD_25_AccountIndustry, M9SDGD_25_Totalfirm, M9SDGD_25_Task_as_per_Industries,
						M9SDGD_25_Individuals, M9SDGD_25_Fundraising_as_per_Industries },
				{ M9SDGD_26_AccountIndustry, M9SDGD_26_Totalfirm, M9SDGD_26_Task_as_per_Industries,
						M9SDGD_26_Individuals, M9SDGD_26_Fundraising_as_per_Industries },
				{ M9SDGD_27_AccountIndustry, M9SDGD_27_Totalfirm, M9SDGD_27_Task_as_per_Industries,
						M9SDGD_27_Individuals, M9SDGD_27_Fundraising_as_per_Industries },
				{ M9SDGD_28_AccountIndustry, M9SDGD_28_Totalfirm, M9SDGD_28_Task_as_per_Industries,
						M9SDGD_28_Individuals, M9SDGD_28_Fundraising_as_per_Industries },
				{ M9SDGD_29_AccountIndustry, M9SDGD_29_Totalfirm, M9SDGD_29_Task_as_per_Industries,
						M9SDGD_29_Individuals, M9SDGD_29_Fundraising_as_per_Industries },
				{ M9SDGD_30_AccountIndustry, M9SDGD_30_Totalfirm, M9SDGD_30_Task_as_per_Industries,
						M9SDGD_30_Individuals, M9SDGD_30_Fundraising_as_per_Industries },
				{ M9SDGD_31_AccountIndustry, M9SDGD_31_Totalfirm, M9SDGD_31_Task_as_per_Industries,
						M9SDGD_31_Individuals, M9SDGD_31_Fundraising_as_per_Industries },
				{ M9SDGD_32_AccountIndustry, M9SDGD_32_Totalfirm, M9SDGD_32_Task_as_per_Industries,
						M9SDGD_32_Individuals, M9SDGD_32_Fundraising_as_per_Industries },
				{ M9SDGD_33_AccountIndustry, M9SDGD_33_Totalfirm, M9SDGD_33_Task_as_per_Industries,
						M9SDGD_33_Individuals, M9SDGD_33_Fundraising_as_per_Industries },
				{ M9SDGD_34_AccountIndustry, M9SDGD_34_Totalfirm, M9SDGD_34_Task_as_per_Industries,
						M9SDGD_34_Individuals, M9SDGD_34_Fundraising_as_per_Industries },
				{ M9SDGD_35_AccountIndustry, M9SDGD_35_Totalfirm, M9SDGD_35_Task_as_per_Industries,
						M9SDGD_35_Individuals, M9SDGD_35_Fundraising_as_per_Industries },
				{ M9SDGD_36_AccountIndustry, M9SDGD_36_Totalfirm, M9SDGD_36_Task_as_per_Industries,
						M9SDGD_36_Individuals, M9SDGD_36_Fundraising_as_per_Industries },
				{ M9SDGD_37_AccountIndustry, M9SDGD_37_Totalfirm, M9SDGD_37_Task_as_per_Industries,
						M9SDGD_37_Individuals, M9SDGD_37_Fundraising_as_per_Industries },
				{ M9SDGD_38_AccountIndustry, M9SDGD_38_Totalfirm, M9SDGD_38_Task_as_per_Industries,
						M9SDGD_38_Individuals, M9SDGD_38_Fundraising_as_per_Industries },
				{ M9SDGD_39_AccountIndustry, M9SDGD_39_Totalfirm, M9SDGD_39_Task_as_per_Industries,
						M9SDGD_39_Individuals, M9SDGD_39_Fundraising_as_per_Industries },
				{ M9SDGD_40_AccountIndustry, M9SDGD_40_Totalfirm, M9SDGD_40_Task_as_per_Industries,
						M9SDGD_40_Individuals, M9SDGD_40_Fundraising_as_per_Industries } };

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (BP.openAppFromAppLauchner(60, "Sortable Data Grids")) {
			if (sd.editAllRowOnSDG(projectName, "SDG_GROUPBY_1", Condition.SelectCheckbox)) {
				if (BP.openAppFromAppLauchner(appPage, 50)) {
					ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName,
							val);
					if (Data == null) {
						log(LogStatus.PASS, "SDG Data has been Matched", YesNo.No);
						sa.assertTrue(true, "SDG Data has been Matched");
					} else {
						log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
						sa.assertTrue(false, "SDG Data is not Matched : " + Data);
					}

				} else {
					log(LogStatus.ERROR, "Could not Open the App from the App Launcher", YesNo.Yes);
					sa.assertTrue(false, "Could not Open the App from the App Launcher");
				}
			}

			else {
				log(LogStatus.ERROR, "Could not Edit the SDG", YesNo.Yes);
				sa.assertTrue(false, "Could not Edit the SDG");
			}
		}

		else {
			log(LogStatus.ERROR, "Could not Open the SDG from the App Launcher", YesNo.Yes);
			sa.assertTrue(false, "Could not Open the App from the App Launcher");
		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc057_RemoveEmailFromRecycleAndEditSDGAndVerifySDGDataOnAppPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		SDGPageBusinessLayer sd = new SDGPageBusinessLayer(driver);
		WebElement ele;

		String appPage = "Test App Page";
		String tableName = "Test";
		String name = "James Falcon -1";
		String[][] val = {
				{ M9SDGD_1_AccountIndustry, M9SDGD_1_Totalfirm, M9SDGD_1_Task_as_per_Industries, M9SDGD_1_Individuals,
						M9SDGD_1_Fundraising_as_per_Industries },
				{ M9SDGD_2_AccountIndustry, M9SDGD_2_Totalfirm, M9SDGD_2_Task_as_per_Industries, M9SDGD_2_Individuals,
						M9SDGD_2_Fundraising_as_per_Industries },
				{ M9SDGD_3_AccountIndustry, M9SDGD_3_Totalfirm, M9SDGD_3_Task_as_per_Industries, M9SDGD_3_Individuals,
						M9SDGD_3_Fundraising_as_per_Industries },
				{ M9SDGD_4_AccountIndustry, M9SDGD_4_Totalfirm, M9SDGD_4_Task_as_per_Industries, M9SDGD_4_Individuals,
						M9SDGD_4_Fundraising_as_per_Industries },
				{ M9SDGD_5_AccountIndustry, M9SDGD_5_Totalfirm, M9SDGD_5_Task_as_per_Industries, M9SDGD_5_Individuals,
						M9SDGD_5_Fundraising_as_per_Industries },
				{ M9SDGD_6_AccountIndustry, M9SDGD_6_Totalfirm, M9SDGD_6_Task_as_per_Industries, M9SDGD_6_Individuals,
						M9SDGD_6_Fundraising_as_per_Industries },
				{ M9SDGD_7_AccountIndustry, M9SDGD_7_Totalfirm, M9SDGD_7_Task_as_per_Industries, M9SDGD_7_Individuals,
						M9SDGD_7_Fundraising_as_per_Industries },
				{ M9SDGD_8_AccountIndustry, M9SDGD_8_Totalfirm, M9SDGD_8_Task_as_per_Industries, M9SDGD_8_Individuals,
						M9SDGD_8_Fundraising_as_per_Industries },
				{ M9SDGD_9_AccountIndustry, M9SDGD_9_Totalfirm, M9SDGD_9_Task_as_per_Industries, M9SDGD_9_Individuals,
						M9SDGD_9_Fundraising_as_per_Industries },
				{ M9SDGD_10_AccountIndustry, M9SDGD_10_Totalfirm, M9SDGD_10_Task_as_per_Industries,
						M9SDGD_10_Individuals, M9SDGD_10_Fundraising_as_per_Industries },
				{ M9SDGD_11_AccountIndustry, M9SDGD_11_Totalfirm, M9SDGD_11_Task_as_per_Industries,
						M9SDGD_11_Individuals, M9SDGD_11_Fundraising_as_per_Industries },
				{ M9SDGD_12_AccountIndustry, M9SDGD_12_Totalfirm, M9SDGD_12_Task_as_per_Industries,
						M9SDGD_12_Individuals, M9SDGD_12_Fundraising_as_per_Industries },
				{ M9SDGD_13_AccountIndustry, M9SDGD_13_Totalfirm, M9SDGD_13_Task_as_per_Industries,
						M9SDGD_13_Individuals, M9SDGD_13_Fundraising_as_per_Industries },
				{ M9SDGD_14_AccountIndustry, M9SDGD_14_Totalfirm, M9SDGD_14_Task_as_per_Industries,
						M9SDGD_14_Individuals, M9SDGD_14_Fundraising_as_per_Industries },
				{ M9SDGD_15_AccountIndustry, M9SDGD_15_Totalfirm, M9SDGD_15_Task_as_per_Industries,
						M9SDGD_15_Individuals, M9SDGD_15_Fundraising_as_per_Industries },
				{ M9SDGD_16_AccountIndustry, M9SDGD_16_Totalfirm, M9SDGD_16_Task_as_per_Industries,
						M9SDGD_16_Individuals, M9SDGD_16_Fundraising_as_per_Industries },
				{ M9SDGD_17_AccountIndustry, M9SDGD_17_Totalfirm, M9SDGD_17_Task_as_per_Industries,
						M9SDGD_17_Individuals, M9SDGD_17_Fundraising_as_per_Industries },
				{ M9SDGD_18_AccountIndustry, M9SDGD_18_Totalfirm, M9SDGD_18_Task_as_per_Industries,
						M9SDGD_18_Individuals, M9SDGD_18_Fundraising_as_per_Industries },
				{ M9SDGD_19_AccountIndustry, M9SDGD_19_Totalfirm, M9SDGD_19_Task_as_per_Industries,
						M9SDGD_19_Individuals, M9SDGD_19_Fundraising_as_per_Industries },
				{ M9SDGD_20_AccountIndustry, M9SDGD_20_Totalfirm, M9SDGD_20_Task_as_per_Industries,
						M9SDGD_20_Individuals, M9SDGD_20_Fundraising_as_per_Industries } };

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String recycleTab = lp.getTabName(projectName, TabName.RecycleBinTab);
		if (lp.searchAndClickOnApp(recycleTab, 60)) {
			ele = lp.getCheckboxOfRestoreItemOnRecycleBin(projectName, name, 30);
			if (clickUsingJavaScript(driver, ele, "Check box against : " + name, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on checkbox for " + name, YesNo.No);
				;
				ele = lp.getRestoreButtonOnRecycleBin(projectName, 30);
				if (clickUsingJavaScript(driver, ele, "Restore Button : " + name, action.BOOLEAN)) {
					ThreadSleep(10000);
					log(LogStatus.INFO, "Click on Restore Button for " + name, YesNo.No);
					if (BP.openAppFromAppLauchner(60, "Sortable Data Grids")) {
						if (sd.editAllRowOnSDG(projectName, "SDG_GROUPBY_1", Condition.UnSelectCheckbox)) {

							lp.CRMlogout();
							lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
							if (BP.openAppFromAppLauchner(appPage, 50)) {
								ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage,
										tableName, val);
								if (Data == null) {
									log(LogStatus.PASS, "SDG Data has been Matched", YesNo.No);
									sa.assertTrue(true, "SDG Data has been Matched");
								} else {
									log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
									sa.assertTrue(false, "SDG Data is not Matched : " + Data);
								}

							} else {
								log(LogStatus.ERROR, "Could not Open the App from the App Launcher", YesNo.Yes);
							}

						} else {
							log(LogStatus.ERROR, "Could not Edit the All Row on SDG", YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Could not Open the SDG on the App Launcher", YesNo.Yes);
					}

				} else {

					log(LogStatus.ERROR, "Not Able to Click on Restore Button for " + name, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Restore Button for " + name);
				}

			} else {

				log(LogStatus.ERROR, "Not Able to Click on checkbox for " + name, YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on checkbox for " + name);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to open the Recycle been tab", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the Recycle been tab");

		}
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc058_UpdatePiclistFieldLabelAndVerifySDGDataOnAppPage(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		String appPage = "Test App Page";
		String tableName = "Test";

		String[][] val = {
				{ M9SDGD_41_AccountIndustry, M9SDGD_41_Totalfirm, M9SDGD_41_Task_as_per_Industries,
						M9SDGD_41_Individuals, M9SDGD_41_Fundraising_as_per_Industries },
				{ M9SDGD_42_AccountIndustry, M9SDGD_42_Totalfirm, M9SDGD_42_Task_as_per_Industries,
						M9SDGD_42_Individuals, M9SDGD_42_Fundraising_as_per_Industries },
				{ M9SDGD_43_AccountIndustry, M9SDGD_43_Totalfirm, M9SDGD_43_Task_as_per_Industries,
						M9SDGD_43_Individuals, M9SDGD_43_Fundraising_as_per_Industries },
				{ M9SDGD_44_AccountIndustry, M9SDGD_44_Totalfirm, M9SDGD_44_Task_as_per_Industries,
						M9SDGD_44_Individuals, M9SDGD_44_Fundraising_as_per_Industries },
				{ M9SDGD_45_AccountIndustry, M9SDGD_45_Totalfirm, M9SDGD_45_Task_as_per_Industries,
						M9SDGD_45_Individuals, M9SDGD_45_Fundraising_as_per_Industries },
				{ M9SDGD_46_AccountIndustry, M9SDGD_46_Totalfirm, M9SDGD_46_Task_as_per_Industries,
						M9SDGD_46_Individuals, M9SDGD_46_Fundraising_as_per_Industries },
				{ M9SDGD_47_AccountIndustry, M9SDGD_47_Totalfirm, M9SDGD_47_Task_as_per_Industries,
						M9SDGD_47_Individuals, M9SDGD_47_Fundraising_as_per_Industries },
				{ M9SDGD_48_AccountIndustry, M9SDGD_48_Totalfirm, M9SDGD_48_Task_as_per_Industries,
						M9SDGD_48_Individuals, M9SDGD_48_Fundraising_as_per_Industries },
				{ M9SDGD_49_AccountIndustry, M9SDGD_49_Totalfirm, M9SDGD_49_Task_as_per_Industries,
						M9SDGD_49_Individuals, M9SDGD_49_Fundraising_as_per_Industries },
				{ M9SDGD_50_AccountIndustry, M9SDGD_50_Totalfirm, M9SDGD_50_Task_as_per_Industries,
						M9SDGD_50_Individuals, M9SDGD_50_Fundraising_as_per_Industries },
				{ M9SDGD_51_AccountIndustry, M9SDGD_51_Totalfirm, M9SDGD_51_Task_as_per_Industries,
						M9SDGD_51_Individuals, M9SDGD_51_Fundraising_as_per_Industries },
				{ M9SDGD_52_AccountIndustry, M9SDGD_52_Totalfirm, M9SDGD_52_Task_as_per_Industries,
						M9SDGD_52_Individuals, M9SDGD_52_Fundraising_as_per_Industries },
				{ M9SDGD_53_AccountIndustry, M9SDGD_53_Totalfirm, M9SDGD_53_Task_as_per_Industries,
						M9SDGD_53_Individuals, M9SDGD_53_Fundraising_as_per_Industries },
				{ M9SDGD_54_AccountIndustry, M9SDGD_54_Totalfirm, M9SDGD_54_Task_as_per_Industries,
						M9SDGD_54_Individuals, M9SDGD_54_Fundraising_as_per_Industries },
				{ M9SDGD_55_AccountIndustry, M9SDGD_55_Totalfirm, M9SDGD_55_Task_as_per_Industries,
						M9SDGD_55_Individuals, M9SDGD_55_Fundraising_as_per_Industries },
				{ M9SDGD_56_AccountIndustry, M9SDGD_56_Totalfirm, M9SDGD_56_Task_as_per_Industries,
						M9SDGD_56_Individuals, M9SDGD_56_Fundraising_as_per_Industries },
				{ M9SDGD_57_AccountIndustry, M9SDGD_57_Totalfirm, M9SDGD_57_Task_as_per_Industries,
						M9SDGD_57_Individuals, M9SDGD_57_Fundraising_as_per_Industries },
				{ M9SDGD_58_AccountIndustry, M9SDGD_58_Totalfirm, M9SDGD_58_Task_as_per_Industries,
						M9SDGD_58_Individuals, M9SDGD_58_Fundraising_as_per_Industries },
				{ M9SDGD_59_AccountIndustry, M9SDGD_59_Totalfirm, M9SDGD_59_Task_as_per_Industries,
						M9SDGD_59_Individuals, M9SDGD_59_Fundraising_as_per_Industries },
				{ M9SDGD_60_AccountIndustry, M9SDGD_60_Totalfirm, M9SDGD_60_Task_as_per_Industries,
						M9SDGD_60_Individuals, M9SDGD_60_Fundraising_as_per_Industries } };

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
					if (fr.editPicklistFieldLabel(projectName, "Industry", "BiotechnologyUP", "Biotechnology")) {
						log(LogStatus.PASS, "Label Name has been Changed", YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowID);
						CommonLib.refresh(driver);
						lp.CRMlogout();
						lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
						if (BP.openAppFromAppLauchner(appPage, 50)) {
							ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage,
									tableName, val);
							if (Data == null) {
								log(LogStatus.PASS, "SDG Data has been Matched", YesNo.No);
								sa.assertTrue(true, "SDG Data has been Matched");
							} else {
								log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
								sa.assertTrue(false, "SDG Data is not Matched : " + Data);
							}

						}

						else {
							log(LogStatus.ERROR, "Could not Opened the App Launcher", YesNo.Yes);
							sa.assertTrue(false, "Could not Opened the App Launcher");
						}
					} else {
						log(LogStatus.ERROR, "Could not edit the Picklist", YesNo.Yes);
						sa.assertTrue(false, "Could not edit the Picklist");
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Object and Feature name", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Object and Feature name");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Search the Object", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Search the Object");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the setup page");
		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc059_DeactivateBiotechnologyOnIndustryAndVerifySDGDataOnAppPage(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		String appPage = "Test App Page";
		String tableName = "Test";

		String[][] val = {
				{ M9SDGD_61_AccountIndustry, M9SDGD_61_Totalfirm, M9SDGD_61_Task_as_per_Industries,
						M9SDGD_61_Individuals, M9SDGD_61_Fundraising_as_per_Industries },
				{ M9SDGD_62_AccountIndustry, M9SDGD_62_Totalfirm, M9SDGD_62_Task_as_per_Industries,
						M9SDGD_62_Individuals, M9SDGD_62_Fundraising_as_per_Industries },
				{ M9SDGD_63_AccountIndustry, M9SDGD_63_Totalfirm, M9SDGD_63_Task_as_per_Industries,
						M9SDGD_63_Individuals, M9SDGD_63_Fundraising_as_per_Industries },
				{ M9SDGD_64_AccountIndustry, M9SDGD_64_Totalfirm, M9SDGD_64_Task_as_per_Industries,
						M9SDGD_64_Individuals, M9SDGD_64_Fundraising_as_per_Industries },
				{ M9SDGD_65_AccountIndustry, M9SDGD_65_Totalfirm, M9SDGD_65_Task_as_per_Industries,
						M9SDGD_65_Individuals, M9SDGD_65_Fundraising_as_per_Industries },
				{ M9SDGD_66_AccountIndustry, M9SDGD_66_Totalfirm, M9SDGD_66_Task_as_per_Industries,
						M9SDGD_66_Individuals, M9SDGD_66_Fundraising_as_per_Industries },
				{ M9SDGD_67_AccountIndustry, M9SDGD_67_Totalfirm, M9SDGD_67_Task_as_per_Industries,
						M9SDGD_67_Individuals, M9SDGD_67_Fundraising_as_per_Industries },
				{ M9SDGD_68_AccountIndustry, M9SDGD_68_Totalfirm, M9SDGD_68_Task_as_per_Industries,
						M9SDGD_68_Individuals, M9SDGD_68_Fundraising_as_per_Industries },
				{ M9SDGD_69_AccountIndustry, M9SDGD_69_Totalfirm, M9SDGD_69_Task_as_per_Industries,
						M9SDGD_69_Individuals, M9SDGD_69_Fundraising_as_per_Industries },
				{ M9SDGD_70_AccountIndustry, M9SDGD_70_Totalfirm, M9SDGD_70_Task_as_per_Industries,
						M9SDGD_70_Individuals, M9SDGD_70_Fundraising_as_per_Industries },
				{ M9SDGD_71_AccountIndustry, M9SDGD_71_Totalfirm, M9SDGD_71_Task_as_per_Industries,
						M9SDGD_71_Individuals, M9SDGD_71_Fundraising_as_per_Industries },
				{ M9SDGD_72_AccountIndustry, M9SDGD_72_Totalfirm, M9SDGD_72_Task_as_per_Industries,
						M9SDGD_72_Individuals, M9SDGD_72_Fundraising_as_per_Industries },
				{ M9SDGD_73_AccountIndustry, M9SDGD_73_Totalfirm, M9SDGD_73_Task_as_per_Industries,
						M9SDGD_73_Individuals, M9SDGD_73_Fundraising_as_per_Industries },
				{ M9SDGD_74_AccountIndustry, M9SDGD_74_Totalfirm, M9SDGD_74_Task_as_per_Industries,
						M9SDGD_74_Individuals, M9SDGD_74_Fundraising_as_per_Industries },
				{ M9SDGD_75_AccountIndustry, M9SDGD_75_Totalfirm, M9SDGD_75_Task_as_per_Industries,
						M9SDGD_75_Individuals, M9SDGD_75_Fundraising_as_per_Industries },
				{ M9SDGD_76_AccountIndustry, M9SDGD_76_Totalfirm, M9SDGD_76_Task_as_per_Industries,
						M9SDGD_76_Individuals, M9SDGD_76_Fundraising_as_per_Industries },
				{ M9SDGD_77_AccountIndustry, M9SDGD_77_Totalfirm, M9SDGD_77_Task_as_per_Industries,
						M9SDGD_77_Individuals, M9SDGD_77_Fundraising_as_per_Industries },
				{ M9SDGD_78_AccountIndustry, M9SDGD_78_Totalfirm, M9SDGD_78_Task_as_per_Industries,
						M9SDGD_78_Individuals, M9SDGD_78_Fundraising_as_per_Industries },
				{ M9SDGD_79_AccountIndustry, M9SDGD_79_Totalfirm, M9SDGD_79_Task_as_per_Industries,
						M9SDGD_79_Individuals, M9SDGD_79_Fundraising_as_per_Industries },
				{ M9SDGD_80_AccountIndustry, M9SDGD_80_Totalfirm, M9SDGD_80_Task_as_per_Industries,
						M9SDGD_80_Individuals, M9SDGD_80_Fundraising_as_per_Industries } };

		String value = "BiotechnologyUP";

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
			if (setup.searchStandardOrCustomObject(projectName, mode, "Firm")) {
				if (setup.clickOnObjectFeature(projectName, mode, object.Firm,
						ObjectFeatureName.FieldAndRelationShip)) {
					if (fr.activateOrDeactivatePiclistValueOfField(projectName, "Industry", value,
							Condition.deactivate)) {
						log(LogStatus.PASS, value + " has been deactivated", YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowID);
						CommonLib.refresh(driver);
						lp.CRMlogout();
						lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
						if (BP.openAppFromAppLauchner(appPage, 50)) {
							ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage,
									tableName, val);
							if (Data == null) {
								log(LogStatus.PASS, "SDG Data has been Matched", YesNo.No);
								sa.assertTrue(true, "SDG Data has been Matched");
							} else {
								log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
								sa.assertTrue(false, "SDG Data is not Matched : " + Data);
							}

						} else {
							log(LogStatus.ERROR, "Could not Opened the App Launcher", YesNo.Yes);
							sa.assertTrue(false, "Could not Opened the App Launcher");
						}
					} else {
						log(LogStatus.ERROR, "Could not deactivated the Picklist", YesNo.Yes);
						sa.assertTrue(false, "Could not deactivated the Picklist");
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Object and Feature name", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Object and Feature name");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Search the Object", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Search the Object");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the setup page");
		}
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc060_ActivateBiotechnologyOnIndustryAndVerifySDGDataOnAppPage(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		String appPage = "Test App Page";
		String tableName = "Test";

		String[][] val = {
				{ M9SDGD_61_AccountIndustry, M9SDGD_61_Totalfirm, M9SDGD_61_Task_as_per_Industries,
						M9SDGD_61_Individuals, M9SDGD_61_Fundraising_as_per_Industries },
				{ M9SDGD_62_AccountIndustry, M9SDGD_62_Totalfirm, M9SDGD_62_Task_as_per_Industries,
						M9SDGD_62_Individuals, M9SDGD_62_Fundraising_as_per_Industries },
				{ M9SDGD_63_AccountIndustry, M9SDGD_63_Totalfirm, M9SDGD_63_Task_as_per_Industries,
						M9SDGD_63_Individuals, M9SDGD_63_Fundraising_as_per_Industries },
				{ M9SDGD_64_AccountIndustry, M9SDGD_64_Totalfirm, M9SDGD_64_Task_as_per_Industries,
						M9SDGD_64_Individuals, M9SDGD_64_Fundraising_as_per_Industries },
				{ M9SDGD_65_AccountIndustry, M9SDGD_65_Totalfirm, M9SDGD_65_Task_as_per_Industries,
						M9SDGD_65_Individuals, M9SDGD_65_Fundraising_as_per_Industries },
				{ M9SDGD_66_AccountIndustry, M9SDGD_66_Totalfirm, M9SDGD_66_Task_as_per_Industries,
						M9SDGD_66_Individuals, M9SDGD_66_Fundraising_as_per_Industries },
				{ M9SDGD_67_AccountIndustry, M9SDGD_67_Totalfirm, M9SDGD_67_Task_as_per_Industries,
						M9SDGD_67_Individuals, M9SDGD_67_Fundraising_as_per_Industries },
				{ M9SDGD_68_AccountIndustry, M9SDGD_68_Totalfirm, M9SDGD_68_Task_as_per_Industries,
						M9SDGD_68_Individuals, M9SDGD_68_Fundraising_as_per_Industries },
				{ M9SDGD_69_AccountIndustry, M9SDGD_69_Totalfirm, M9SDGD_69_Task_as_per_Industries,
						M9SDGD_69_Individuals, M9SDGD_69_Fundraising_as_per_Industries },
				{ M9SDGD_70_AccountIndustry, M9SDGD_70_Totalfirm, M9SDGD_70_Task_as_per_Industries,
						M9SDGD_70_Individuals, M9SDGD_70_Fundraising_as_per_Industries },
				{ M9SDGD_71_AccountIndustry, M9SDGD_71_Totalfirm, M9SDGD_71_Task_as_per_Industries,
						M9SDGD_71_Individuals, M9SDGD_71_Fundraising_as_per_Industries },
				{ M9SDGD_72_AccountIndustry, M9SDGD_72_Totalfirm, M9SDGD_72_Task_as_per_Industries,
						M9SDGD_72_Individuals, M9SDGD_72_Fundraising_as_per_Industries },
				{ M9SDGD_73_AccountIndustry, M9SDGD_73_Totalfirm, M9SDGD_73_Task_as_per_Industries,
						M9SDGD_73_Individuals, M9SDGD_73_Fundraising_as_per_Industries },
				{ M9SDGD_74_AccountIndustry, M9SDGD_74_Totalfirm, M9SDGD_74_Task_as_per_Industries,
						M9SDGD_74_Individuals, M9SDGD_74_Fundraising_as_per_Industries },
				{ M9SDGD_75_AccountIndustry, M9SDGD_75_Totalfirm, M9SDGD_75_Task_as_per_Industries,
						M9SDGD_75_Individuals, M9SDGD_75_Fundraising_as_per_Industries },
				{ M9SDGD_76_AccountIndustry, M9SDGD_76_Totalfirm, M9SDGD_76_Task_as_per_Industries,
						M9SDGD_76_Individuals, M9SDGD_76_Fundraising_as_per_Industries },
				{ M9SDGD_77_AccountIndustry, M9SDGD_77_Totalfirm, M9SDGD_77_Task_as_per_Industries,
						M9SDGD_77_Individuals, M9SDGD_77_Fundraising_as_per_Industries },
				{ M9SDGD_78_AccountIndustry, M9SDGD_78_Totalfirm, M9SDGD_78_Task_as_per_Industries,
						M9SDGD_78_Individuals, M9SDGD_78_Fundraising_as_per_Industries },
				{ M9SDGD_79_AccountIndustry, M9SDGD_79_Totalfirm, M9SDGD_79_Task_as_per_Industries,
						M9SDGD_79_Individuals, M9SDGD_79_Fundraising_as_per_Industries },
				{ M9SDGD_80_AccountIndustry, M9SDGD_80_Totalfirm, M9SDGD_80_Task_as_per_Industries,
						M9SDGD_80_Individuals, M9SDGD_80_Fundraising_as_per_Industries } };

		String value = "BiotechnologyUP";

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

			if (setup.searchStandardOrCustomObject(projectName, mode, "Firm")) {
				if (setup.clickOnObjectFeature(projectName, mode, object.Firm,
						ObjectFeatureName.FieldAndRelationShip)) {
					if (fr.activateOrDeactivatePiclistValueOfField(projectName, "Industry", value,
							Condition.activate)) {
						log(LogStatus.PASS, value + " has been activated", YesNo.No);

						driver.close();
						driver.switchTo().window(parentWindowID);
						CommonLib.refresh(driver);
						lp.CRMlogout();
						lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
						if (BP.openAppFromAppLauchner(appPage, 50)) {
							ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage,
									tableName, val);
							if (Data == null) {
								log(LogStatus.PASS, "SDG Data has been Matched", YesNo.No);
								sa.assertTrue(true, "SDG Data has been Matched");
							} else {
								log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
								sa.assertTrue(false, "SDG Data is not Matched : " + Data);
							}

						} else {
							log(LogStatus.ERROR, "Could not Opened the App Launcher", YesNo.Yes);
							sa.assertTrue(false, "Could not Opened the App Launcher");
						}
					} else {
						log(LogStatus.ERROR, "Could not Activated the Picklist ", YesNo.Yes);
						sa.assertTrue(false, "Could not Activated the Picklist");
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Object and Feature name", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Object and Feature name");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Search the Object", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Search the Object");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the setup page");
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc061_deletePicklistOptionAndReplaceValueAndVerifySDGDataOnAppPage(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		String field = "Industry";
		String picklistvalue = "Retail";
		String replaceValueName = "Consulting";

		String[][] val = {
				{ M9SDGD_81_AccountIndustry, M9SDGD_81_Totalfirm, M9SDGD_81_Task_as_per_Industries,
						M9SDGD_81_Individuals, M9SDGD_81_Fundraising_as_per_Industries },
				{ M9SDGD_82_AccountIndustry, M9SDGD_82_Totalfirm, M9SDGD_82_Task_as_per_Industries,
						M9SDGD_82_Individuals, M9SDGD_82_Fundraising_as_per_Industries },
				{ M9SDGD_83_AccountIndustry, M9SDGD_83_Totalfirm, M9SDGD_83_Task_as_per_Industries,
						M9SDGD_83_Individuals, M9SDGD_83_Fundraising_as_per_Industries },
				{ M9SDGD_84_AccountIndustry, M9SDGD_84_Totalfirm, M9SDGD_84_Task_as_per_Industries,
						M9SDGD_84_Individuals, M9SDGD_84_Fundraising_as_per_Industries },
				{ M9SDGD_85_AccountIndustry, M9SDGD_85_Totalfirm, M9SDGD_85_Task_as_per_Industries,
						M9SDGD_85_Individuals, M9SDGD_85_Fundraising_as_per_Industries },
				{ M9SDGD_86_AccountIndustry, M9SDGD_86_Totalfirm, M9SDGD_86_Task_as_per_Industries,
						M9SDGD_86_Individuals, M9SDGD_86_Fundraising_as_per_Industries },
				{ M9SDGD_87_AccountIndustry, M9SDGD_87_Totalfirm, M9SDGD_87_Task_as_per_Industries,
						M9SDGD_87_Individuals, M9SDGD_87_Fundraising_as_per_Industries },
				{ M9SDGD_88_AccountIndustry, M9SDGD_88_Totalfirm, M9SDGD_88_Task_as_per_Industries,
						M9SDGD_88_Individuals, M9SDGD_88_Fundraising_as_per_Industries },
				{ M9SDGD_89_AccountIndustry, M9SDGD_89_Totalfirm, M9SDGD_89_Task_as_per_Industries,
						M9SDGD_89_Individuals, M9SDGD_89_Fundraising_as_per_Industries },
				{ M9SDGD_90_AccountIndustry, M9SDGD_90_Totalfirm, M9SDGD_90_Task_as_per_Industries,
						M9SDGD_90_Individuals, M9SDGD_90_Fundraising_as_per_Industries },
				{ M9SDGD_91_AccountIndustry, M9SDGD_91_Totalfirm, M9SDGD_91_Task_as_per_Industries,
						M9SDGD_91_Individuals, M9SDGD_91_Fundraising_as_per_Industries },
				{ M9SDGD_92_AccountIndustry, M9SDGD_92_Totalfirm, M9SDGD_92_Task_as_per_Industries,
						M9SDGD_92_Individuals, M9SDGD_92_Fundraising_as_per_Industries },
				{ M9SDGD_93_AccountIndustry, M9SDGD_93_Totalfirm, M9SDGD_93_Task_as_per_Industries,
						M9SDGD_93_Individuals, M9SDGD_93_Fundraising_as_per_Industries },
				{ M9SDGD_94_AccountIndustry, M9SDGD_94_Totalfirm, M9SDGD_94_Task_as_per_Industries,
						M9SDGD_94_Individuals, M9SDGD_94_Fundraising_as_per_Industries },
				{ M9SDGD_95_AccountIndustry, M9SDGD_95_Totalfirm, M9SDGD_95_Task_as_per_Industries,
						M9SDGD_95_Individuals, M9SDGD_95_Fundraising_as_per_Industries },
				{ M9SDGD_96_AccountIndustry, M9SDGD_96_Totalfirm, M9SDGD_96_Task_as_per_Industries,
						M9SDGD_96_Individuals, M9SDGD_96_Fundraising_as_per_Industries },
				{ M9SDGD_97_AccountIndustry, M9SDGD_97_Totalfirm, M9SDGD_97_Task_as_per_Industries,
						M9SDGD_97_Individuals, M9SDGD_97_Fundraising_as_per_Industries },
				{ M9SDGD_98_AccountIndustry, M9SDGD_98_Totalfirm, M9SDGD_98_Task_as_per_Industries,
						M9SDGD_98_Individuals, M9SDGD_98_Fundraising_as_per_Industries },
				{ M9SDGD_99_AccountIndustry, M9SDGD_99_Totalfirm, M9SDGD_99_Task_as_per_Industries,
						M9SDGD_99_Individuals, M9SDGD_99_Fundraising_as_per_Industries } };

		String appPage = "Test App Page";
		String tableName = "Test";
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
					if (fr.deletePicklistOptionAndReplaceValue(projectName, field, replaceValueName, picklistvalue,
							Condition.replaceWithValue)) {
						log(LogStatus.PASS, "Piclist option has been deleted", YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowID);
						CommonLib.refresh(driver);
						lp.CRMlogout();
						lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
						if (BP.openAppFromAppLauchner(appPage, 50)) {
							ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage,
									tableName, val);
							if (Data == null) {
								log(LogStatus.PASS, "SDG Data has been Matched", YesNo.No);
								sa.assertTrue(true, "SDG Data has been Matched");
							} else {
								log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
								sa.assertTrue(false, "SDG Data is not Matched : " + Data);
							}

						}

						else {
							log(LogStatus.ERROR, "Could not Opened the App Launcher", YesNo.Yes);
							sa.assertTrue(false, "Could not Opened the App Launcher");
						}
					} else {
						log(LogStatus.ERROR, "Piclist is not deleted", YesNo.Yes);
						sa.assertTrue(false, "Piclist is not deleted");
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Object and Feature name", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Object and Feature name");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Search the Object", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Search the Object");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the setup page");
		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc062_deletePicklistOptionAndReplaceValuewithBlankAndVerifySDGDataOnAppPage(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		String field = "Industry";
		String picklistvalue = "Utilities";
		String replaceValueName = "Consulting";
		String appPage = "Test App Page";
		String tableName = "Test";

		String[][] val = {
				{ M9SDGD_100_AccountIndustry, M9SDGD_100_Totalfirm, M9SDGD_100_Task_as_per_Industries,
						M9SDGD_100_Individuals, M9SDGD_100_Fundraising_as_per_Industries },
				{ M9SDGD_101_AccountIndustry, M9SDGD_101_Totalfirm, M9SDGD_101_Task_as_per_Industries,
						M9SDGD_101_Individuals, M9SDGD_101_Fundraising_as_per_Industries },
				{ M9SDGD_102_AccountIndustry, M9SDGD_102_Totalfirm, M9SDGD_102_Task_as_per_Industries,
						M9SDGD_102_Individuals, M9SDGD_102_Fundraising_as_per_Industries },
				{ M9SDGD_103_AccountIndustry, M9SDGD_103_Totalfirm, M9SDGD_103_Task_as_per_Industries,
						M9SDGD_103_Individuals, M9SDGD_103_Fundraising_as_per_Industries },
				{ M9SDGD_104_AccountIndustry, M9SDGD_104_Totalfirm, M9SDGD_104_Task_as_per_Industries,
						M9SDGD_104_Individuals, M9SDGD_104_Fundraising_as_per_Industries },
				{ M9SDGD_105_AccountIndustry, M9SDGD_105_Totalfirm, M9SDGD_105_Task_as_per_Industries,
						M9SDGD_105_Individuals, M9SDGD_105_Fundraising_as_per_Industries },
				{ M9SDGD_106_AccountIndustry, M9SDGD_106_Totalfirm, M9SDGD_106_Task_as_per_Industries,
						M9SDGD_106_Individuals, M9SDGD_106_Fundraising_as_per_Industries },
				{ M9SDGD_107_AccountIndustry, M9SDGD_107_Totalfirm, M9SDGD_107_Task_as_per_Industries,
						M9SDGD_107_Individuals, M9SDGD_107_Fundraising_as_per_Industries },
				{ M9SDGD_108_AccountIndustry, M9SDGD_108_Totalfirm, M9SDGD_108_Task_as_per_Industries,
						M9SDGD_108_Individuals, M9SDGD_108_Fundraising_as_per_Industries },
				{ M9SDGD_109_AccountIndustry, M9SDGD_109_Totalfirm, M9SDGD_109_Task_as_per_Industries,
						M9SDGD_109_Individuals, M9SDGD_109_Fundraising_as_per_Industries },
				{ M9SDGD_110_AccountIndustry, M9SDGD_110_Totalfirm, M9SDGD_110_Task_as_per_Industries,
						M9SDGD_110_Individuals, M9SDGD_110_Fundraising_as_per_Industries },
				{ M9SDGD_111_AccountIndustry, M9SDGD_111_Totalfirm, M9SDGD_111_Task_as_per_Industries,
						M9SDGD_111_Individuals, M9SDGD_111_Fundraising_as_per_Industries },
				{ M9SDGD_112_AccountIndustry, M9SDGD_112_Totalfirm, M9SDGD_112_Task_as_per_Industries,
						M9SDGD_112_Individuals, M9SDGD_112_Fundraising_as_per_Industries },
				{ M9SDGD_113_AccountIndustry, M9SDGD_113_Totalfirm, M9SDGD_113_Task_as_per_Industries,
						M9SDGD_113_Individuals, M9SDGD_113_Fundraising_as_per_Industries },
				{ M9SDGD_114_AccountIndustry, M9SDGD_114_Totalfirm, M9SDGD_114_Task_as_per_Industries,
						M9SDGD_114_Individuals, M9SDGD_114_Fundraising_as_per_Industries },
				{ M9SDGD_115_AccountIndustry, M9SDGD_115_Totalfirm, M9SDGD_115_Task_as_per_Industries,
						M9SDGD_115_Individuals, M9SDGD_115_Fundraising_as_per_Industries },
				{ M9SDGD_116_AccountIndustry, M9SDGD_116_Totalfirm, M9SDGD_116_Task_as_per_Industries,
						M9SDGD_116_Individuals, M9SDGD_116_Fundraising_as_per_Industries },
				{ M9SDGD_117_AccountIndustry, M9SDGD_117_Totalfirm, M9SDGD_117_Task_as_per_Industries,
						M9SDGD_117_Individuals, M9SDGD_117_Fundraising_as_per_Industries } };

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
					if (fr.deletePicklistOptionAndReplaceValue(projectName, field, replaceValueName, picklistvalue,
							Condition.replaceWithBlank)) {
						log(LogStatus.PASS, "Piclist option has been deleted", YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowID);
						CommonLib.refresh(driver);
						lp.CRMlogout();
						lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
						if (BP.openAppFromAppLauchner(appPage, 50)) {
							ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage,
									tableName, val);
							if (Data == null) {
								log(LogStatus.PASS, "SDG Data has been Matched", YesNo.No);
								sa.assertTrue(true, "SDG Data has been Matched");
							} else {
								log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
								sa.assertTrue(false, "SDG Data is not Matched : " + Data);
							}

						} else {
							log(LogStatus.ERROR, "Could not Opened the App Launcher", YesNo.Yes);
							sa.assertTrue(false, "Could not Opened the App Launcher");
						}
					} else {
						log(LogStatus.ERROR, "Piclist is not deleted", YesNo.Yes);
						sa.assertTrue(false, "Piclist is not deleted");
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Object and Feature name", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Object and Feature name");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Search the Object", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Search the Object");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the setup page");
		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc063_CreateNewOptionAndReplaceWithExistingValueVerifySDGDataOnAppPage(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		String field = "Industry";
		String OptionValue = "Cargo";
		String replaceValueName = "Education";
		String appPage = "Test App Page";
		String tableName = "Test";

		String[][] val = {
				{ M9SDGD_118_AccountIndustry, M9SDGD_118_Totalfirm, M9SDGD_118_Task_as_per_Industries,
						M9SDGD_118_Individuals, M9SDGD_118_Fundraising_as_per_Industries },
				{ M9SDGD_119_AccountIndustry, M9SDGD_119_Totalfirm, M9SDGD_119_Task_as_per_Industries,
						M9SDGD_119_Individuals, M9SDGD_119_Fundraising_as_per_Industries },
				{ M9SDGD_120_AccountIndustry, M9SDGD_120_Totalfirm, M9SDGD_120_Task_as_per_Industries,
						M9SDGD_120_Individuals, M9SDGD_120_Fundraising_as_per_Industries },
				{ M9SDGD_121_AccountIndustry, M9SDGD_121_Totalfirm, M9SDGD_121_Task_as_per_Industries,
						M9SDGD_121_Individuals, M9SDGD_121_Fundraising_as_per_Industries },
				{ M9SDGD_122_AccountIndustry, M9SDGD_122_Totalfirm, M9SDGD_122_Task_as_per_Industries,
						M9SDGD_122_Individuals, M9SDGD_122_Fundraising_as_per_Industries },
				{ M9SDGD_123_AccountIndustry, M9SDGD_123_Totalfirm, M9SDGD_123_Task_as_per_Industries,
						M9SDGD_123_Individuals, M9SDGD_123_Fundraising_as_per_Industries },
				{ M9SDGD_124_AccountIndustry, M9SDGD_124_Totalfirm, M9SDGD_124_Task_as_per_Industries,
						M9SDGD_124_Individuals, M9SDGD_124_Fundraising_as_per_Industries },
				{ M9SDGD_125_AccountIndustry, M9SDGD_125_Totalfirm, M9SDGD_125_Task_as_per_Industries,
						M9SDGD_125_Individuals, M9SDGD_125_Fundraising_as_per_Industries },
				{ M9SDGD_126_AccountIndustry, M9SDGD_126_Totalfirm, M9SDGD_126_Task_as_per_Industries,
						M9SDGD_126_Individuals, M9SDGD_126_Fundraising_as_per_Industries },
				{ M9SDGD_127_AccountIndustry, M9SDGD_127_Totalfirm, M9SDGD_127_Task_as_per_Industries,
						M9SDGD_127_Individuals, M9SDGD_127_Fundraising_as_per_Industries },
				{ M9SDGD_128_AccountIndustry, M9SDGD_128_Totalfirm, M9SDGD_128_Task_as_per_Industries,
						M9SDGD_128_Individuals, M9SDGD_128_Fundraising_as_per_Industries },
				{ M9SDGD_129_AccountIndustry, M9SDGD_129_Totalfirm, M9SDGD_129_Task_as_per_Industries,
						M9SDGD_129_Individuals, M9SDGD_129_Fundraising_as_per_Industries },
				{ M9SDGD_130_AccountIndustry, M9SDGD_130_Totalfirm, M9SDGD_130_Task_as_per_Industries,
						M9SDGD_130_Individuals, M9SDGD_130_Fundraising_as_per_Industries },
				{ M9SDGD_131_AccountIndustry, M9SDGD_131_Totalfirm, M9SDGD_131_Task_as_per_Industries,
						M9SDGD_131_Individuals, M9SDGD_131_Fundraising_as_per_Industries },
				{ M9SDGD_132_AccountIndustry, M9SDGD_132_Totalfirm, M9SDGD_132_Task_as_per_Industries,
						M9SDGD_132_Individuals, M9SDGD_132_Fundraising_as_per_Industries },
				{ M9SDGD_133_AccountIndustry, M9SDGD_133_Totalfirm, M9SDGD_133_Task_as_per_Industries,
						M9SDGD_133_Individuals, M9SDGD_133_Fundraising_as_per_Industries },
				{ M9SDGD_134_AccountIndustry, M9SDGD_134_Totalfirm, M9SDGD_134_Task_as_per_Industries,
						M9SDGD_134_Individuals, M9SDGD_134_Fundraising_as_per_Industries },
				{ M9SDGD_135_AccountIndustry, M9SDGD_135_Totalfirm, M9SDGD_135_Task_as_per_Industries,
						M9SDGD_135_Individuals, M9SDGD_135_Fundraising_as_per_Industries } };

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
					if (fr.createPicklistValue(projectName, field, OptionValue)) {
						if (fr.replacePiclistOptionValue(projectName, OptionValue, replaceValueName)) {
							log(LogStatus.PASS, "Option value has been replaced", YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowID);
							CommonLib.refresh(driver);
							lp.CRMlogout();
							lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
							if (BP.openAppFromAppLauchner(appPage, 50)) {
								ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage,
										tableName, val);
								if (Data == null) {
									log(LogStatus.PASS, "SDG Data has been Matched", YesNo.No);
									sa.assertTrue(true, "SDG Data has been Matched");
								} else {
									log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
									sa.assertTrue(false, "SDG Data is not Matched : " + Data);
								}

							} else {
								log(LogStatus.ERROR, "Could not Opened the App Launcher", YesNo.Yes);
								sa.assertTrue(false, "Could not Opened the App Launcher");
							}
						} else {
							log(LogStatus.ERROR,
									"Could not repalce the " + OptionValue + " value with the " + replaceValueName,
									YesNo.Yes);
							sa.assertTrue(false,
									"Could not repalce the " + OptionValue + " value with the " + replaceValueName);
						}

					} else {
						log(LogStatus.ERROR, "Could not creare the option of the Piclist", YesNo.Yes);
						sa.assertTrue(false, "Could not creare the option of the Piclist");
					}
				} else {
					log(LogStatus.ERROR, "Not Able to click on the Object Feature Name", YesNo.Yes);
					sa.assertTrue(false, "Not Able to click on the Object Feature Name");
				}
			}

			else {
				log(LogStatus.ERROR, "Not Able to Search the Object", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Search the Object");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the setup page");
		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc064_AddSDGOnAppPage(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		EditPageBusinessLayer EP = new EditPageBusinessLayer(driver);
		String labelName = "Testing1234";
		String tableName = "Test";
		String appPage = "Testing1234";

		ArrayList<String> tableNames = new ArrayList<String>();
		tableNames.add("SDG_GROUPBY_2");
		tableNames.add("SDG_GROUPBY_3");
		ArrayList<String> dataProviderName = new ArrayList<String>();
		dataProviderName.add("CustomObject:SDG_GROUPBY_2");
		dataProviderName.add("CustomObject:SDG_GROUPBY_3");
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		for (int i = 0; i < dataProviderName.size(); i++) {

			if (BP.openAppFromAppLauchner(appPage, 50)) {
				if (EP.editPageAndAddSDG(appPage, tableNames.get(i), dataProviderName.get(i))) {

				} else {
					log(LogStatus.ERROR, "Could not edit the Page and add the SDG", YesNo.Yes);
					sa.assertTrue(false, "Could not edit the Page and add the SDG");
				}

			} else {
				log(LogStatus.ERROR, "Could not open the App from the App Launcher", YesNo.Yes);
				sa.assertTrue(false, "Could not open the App from the App Launcher");
			}
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc065_VerifySDGDataOnAppPage(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		EditPageBusinessLayer EP = new EditPageBusinessLayer(driver);
		String tableName = "";
		String appPage = "Test App Page";
		String[][] SDG_GROUPBY_2_SDG = { { M9SDGD_136_Fundraising_Stage, M9SDGD_136_Count_as_per_fundraising_stage },
				{ M9SDGD_137_Fundraising_Stage, M9SDGD_137_Count_as_per_fundraising_stage },
				{ M9SDGD_138_Fundraising_Stage, M9SDGD_138_Count_as_per_fundraising_stage },
				{ M9SDGD_139_Fundraising_Stage, M9SDGD_139_Count_as_per_fundraising_stage },
				{ M9SDGD_140_Fundraising_Stage, M9SDGD_140_Count_as_per_fundraising_stage },
				{ M9SDGD_141_Fundraising_Stage, M9SDGD_141_Count_as_per_fundraising_stage },
				{ M9SDGD_142_Fundraising_Stage, M9SDGD_142_Count_as_per_fundraising_stage },
				{ M9SDGD_143_Fundraising_Stage, M9SDGD_143_Count_as_per_fundraising_stage } };

		String[][] SDG_GROUPBY_3_SDG = { { M9SDGD_144_Fundraising, M9SDGD_144_Fundraising_Count },
				{ M9SDGD_145_Fundraising, M9SDGD_145_Fundraising_Count } };

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		tableName = "SDG_GROUPBY_2";
		if (BP.openAppFromAppLauchner(appPage, 50)) {
			ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName,
					SDG_GROUPBY_2_SDG);
			if (Data == null) {
				log(LogStatus.PASS, "SDG Data has been Matched", YesNo.No);
				sa.assertTrue(true, "SDG Data has been Matched");
			} else {
				log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
				sa.assertTrue(false, "SDG Data is not Matched : " + Data);
			}

		} else {
			log(LogStatus.ERROR, "Could not Opened the App Launcher", YesNo.Yes);
			sa.assertTrue(false, "Could not Opened the App Launcher");
		}

		lp.CRMlogout();

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		tableName = "SDG_GROUPBY_3";
		if (BP.openAppFromAppLauchner(appPage, 50)) {
			ArrayList<String> Data = AppBuilder.verifySDGDataOnAppPage(projectName, mode, appPage, tableName,
					SDG_GROUPBY_3_SDG);
			if (Data == null) {
				log(LogStatus.PASS, "SDG Data has been Matched", YesNo.No);
				sa.assertTrue(true, "SDG Data has been Matched");
			} else {
				log(LogStatus.ERROR, "SDG Data is not Matched", YesNo.Yes);
				sa.assertTrue(false, "SDG Data is not Matched : " + Data);
			}

		} else {
			log(LogStatus.ERROR, "Could not Opened the App Launcher", YesNo.Yes);
			sa.assertTrue(false, "Could not Opened the App Launcher");
		}

		lp.CRMlogout();

		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc066_VerifyPermissionAddFieldToLayoutAndEditField(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		EditPageBusinessLayer EP = new EditPageBusinessLayer(driver);
		DataLoaderWizardPageBusinessLayer dataload = new DataLoaderWizardPageBusinessLayer(driver);
		String parentWindow = "";
		boolean flag = false;
		String data;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
			}
			ThreadSleep(3000);

			data = M9AFTPL_13_FieldNames;
			System.out.println(data);
			String[] fields = data.split(",");

			ArrayList<String> userEmail = new ArrayList<String>();
			userEmail.add(crmUser1EmailID);
			userEmail.add(crmUser2EmailID);

			ArrayList<String> columns = new ArrayList<String>();
			for (String column : fields) {
				columns.add(column);
			}

			List<String> layoutName = new ArrayList<String>();
			layoutName.add(M9AFTPL_13_PageLayoutName);
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			for (String column : columns) {
				sourceANDDestination.put(column, "Identifier");

			}
			if (dataload.addFieldToLayoutPage("", mode, layoutName, sourceANDDestination, object.User)) {
				CommonLib.switchToDefaultContent(driver);

				if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.User,
						ObjectFeatureName.FieldAndRelationShip, "Team", PermissionType.givePermission,
						"System Administrator")) {
					log(LogStatus.INFO, "Permission given from the User Object", YesNo.No);

					for (String email : userEmail) {
						CommonLib.switchToDefaultContent(driver);
						CommonLib.refresh(driver);
						if (setup.EditPEUser(email, "Team", HTMLTAG.select, "Origination")) {
							log(LogStatus.INFO, "Origination has been added in the Team for user :" + email, YesNo.No);
							sa.assertTrue(true, "Origination has been added in the Team for user :" + email);
						} else {
							log(LogStatus.INFO, "Origination is not added in the Team field for user :" + email,
									YesNo.Yes);
							sa.assertTrue(false, "Origination is not added in the Team field for user :" + email);
						}
					}

				} else {
					log(LogStatus.INFO, "Not able to give the permission", YesNo.Yes);
					sa.assertTrue(false, "Not able to give the permission");
				}
			} else {
				log(LogStatus.INFO, "Not able to add the field on the Layout", YesNo.Yes);
				sa.assertTrue(false, "Not able to add the field on the Layout");
			}
		}
		driver.close();
		driver.switchTo().window(parentWindow);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc067_CreateAppPageAndAddSDGs(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		String labelName = "Custom App Page";

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		ArrayList<String> dataProviderName = new ArrayList<String>();
		dataProviderName.add("CustomObject:Account Filter Grid");
		dataProviderName.add("CustomObject:Contact Filter Grid");
		dataProviderName.add("CustomObject:Fund Filter Grid");
		dataProviderName.add("CustomObject:Fundraising Filter Grid");

		ArrayList<String> tableName = new ArrayList<String>();
		tableName.add("Account Filter Grid");
		tableName.add("Contact Filter Grid");
		tableName.add("Fund Filter Grid");
		tableName.add("Fundraising Filter Grid");

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

			if (setup.searchStandardOrCustomObject(projectName, mode, object.Lightning_App_Builder)) {
				if (AppBuilder.CreateAppPage(projectName, mode, labelName, tableName, dataProviderName,
						parentWindowID)) {
					log(LogStatus.PASS, "App Page has been Created : " + labelName, YesNo.Yes);
					sa.assertTrue(true, "App Page has been Created");
				} else {
					log(LogStatus.ERROR, "App Page is not created : " + labelName, YesNo.Yes);
					sa.assertTrue(false, "App Page is not created : " + labelName);
				}
			} else {
				log(LogStatus.ERROR, "Not able to search the Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to search the Object");
			}

		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9tc012_DeleteListViewAndVerifySDGRecords(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		String[][] listViewSheetData = { { M9LV_3_Member, M9LV_3_TabName, M9LV_3_ListViewName } };

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

			String sdgName = "Fund - First SDG Grid";

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

										int numberOfRecords = 84;
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

					String[][][] sdgLabels = { { { SDGCreationLabel.List_View_Name.toString(), "" } },
							{ { SDGCreationLabel.List_View_Name.toString(), "Type_Fund" } } };

					for (String[][] sdgLabel : sdgLabels) {
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

												int numberOfRecords = 84;
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

											if (sdgLabel[0][1].equals("Type_Fund")) {

												int numberOfRecords = 76;
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
	public void M9tc013_DeleteOneFundRecordAndVerifySDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String sdgName = "Fund - First SDG Grid";
		String fundNameToDelete = "Test Fund 1";

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
	public void M9tc014_CheckAllRowsCheckboxAndThenVerifySDGGrid(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		HomePage hp = new HomePage(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String sdgName = "Fund - First SDG Grid";
		String fundNameDeleted = "Test Fund 1";

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + sdgName + "']",
					"Component Title ", action.SCROLLANDBOOLEAN, 10);

			if (alreadyAddedComponentToHomePage != null) {
				log(LogStatus.INFO, "------------Component Already Added to Home Page " + sdgName + "----------------",
						YesNo.Yes);
				sa.assertTrue(true, "------------Component Already Added to Home Page " + sdgName + "---------------");
				lp.searchAndClickOnApp(SDG, 30);

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

									int numberOfRecords = 76;
									if (home.numberOfRecordsMatch(sdgName, numberOfRecords)) {
										log(LogStatus.INFO, "----------No. of Records Matched: " + numberOfRecords
												+ "for All Records Checked------------", YesNo.No);

										if (hp.fundNameElement(sdgName, fundNameDeleted) != null) {
											log(LogStatus.INFO, "Record Found " + fundNameDeleted, YesNo.No);

										} else {
											log(LogStatus.ERROR, "Record not Found: " + fundNameDeleted, YesNo.No);
											sa.assertTrue(false, "Record not Found: " + fundNameDeleted);
										}

									}

									else {
										log(LogStatus.FAIL, "---------No. of Records not Matched " + numberOfRecords
												+ "for All Records Checked-------- ", YesNo.No);
										sa.assertTrue(false, "-----------No. of Records not Matched: " + numberOfRecords
												+ "for All Records Checked--------------");

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

		WebElement ele;
		String recycleTab = lp.getTabName(projectName, TabName.RecycleBinTab);
		if (lp.openAppFromAppLauchner(60, recycleTab)) {

			if (click(driver, hp.getSelectListIcon(60), "List View Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on List Views Button  ", YesNo.No);
				WebElement allListViewEle = FindElement(driver, "//span[text()='Org Recycle Bin']/ancestor::a",
						"Org Recycle Bin List View Button ", action.SCROLLANDBOOLEAN, 10);
				if (click(driver, allListViewEle, "Org Recycle Bin List View Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Org Recycle Bin List Views Button  ", YesNo.No);

					ele = lp.getCheckboxOfRestoreItemOnRecycleBin(projectName, fundNameDeleted, 30);
					if (clickUsingJavaScript(driver, ele, "Check box against : " + fundNameDeleted, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on checkbox for " + fundNameDeleted, YesNo.No);
						;
						ele = lp.getRestoreButtonOnRecycleBin(projectName, 30);
						if (clickUsingJavaScript(driver, ele, "Restore Button : " + fundNameDeleted, action.BOOLEAN)) {
							ThreadSleep(10000);
							log(LogStatus.INFO, "Click on Restore Button for " + fundNameDeleted, YesNo.No);

						} else {

							log(LogStatus.ERROR, "Not Able to Click on Restore Button for " + fundNameDeleted,
									YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Restore Button for " + fundNameDeleted);
						}

					} else {

						log(LogStatus.ERROR, "Not Able to Click on checkbox for " + fundNameDeleted, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on checkbox for " + fundNameDeleted);
					}
				} else {
					appLog.error("Not able to click on Org Recycle Bin List View Button");

				}
			} else {
				appLog.error("Not able to click on List Views Button");

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
	public void M9tc015_UpdateMyRecordsFieldWithRecordOwnerId(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		boolean flag = false;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String sdgName = "Fund - First SDG Grid";
		String globalFilterQuery = "Select Name from navpeII__Fund__c ORDER BY Name ASC";
		String[][] sdgLabels = { { SDGCreationLabel.My_Records.toString(), "Owner.Id = '<<User.Id>>'" } };
		String pageSize = "100";
		int rowCountAfterFilter;
		int rowCountAfterFilterAdminCase = 51;
		int rowCountAfterFilterUser1Case = 27;
		int rowCountAfterFilterUser2Case = 6;
		boolean globalFilterFlag = false;

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
				if (edit.editPageAndAddFilter("Fund", globalFilterQuery, "", "", "", "")) {
					log(LogStatus.INFO, "Global Filter Added to: " + TabName.HomeTab.toString(), YesNo.No);
					globalFilterFlag = true;

				} else {
					sa.assertTrue(false, "Global not Filter Added to: " + TabName.HomeTab.toString());
					log(LogStatus.FAIL, "Global not Filter Added to: " + TabName.HomeTab.toString(), YesNo.Yes);
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

		if (globalFilterFlag)

		{
			if (AppBuilder.selectFilter("Show", "My Records")) {
				log(LogStatus.INFO, "Filter has been selected: ", YesNo.No);
				CommonLib.ThreadSleep(8000);
				rowCountAfterFilter = AppBuilder.numberOfRecords(sdgName, pageSize);

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
					rowCountAfterFilter = AppBuilder.numberOfRecords(sdgName, pageSize);

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
	public void M9Tc068_EditAppPageAndAddFilter(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		EditPageBusinessLayer EP = new EditPageBusinessLayer(driver);
		String labelName = "Testing1234";
		String tableName = "Test";
		String appPage = "Custom App Page";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		if (BP.openAppFromAppLauchner(appPage, 50)) {
			if (EP.editPageAndAddFilter("Fund", "Select Name from navpeII__Fund__c ORDER BY Name ASC", "Firm",
					"Select Name from Account where RecordType.Name = 'Institution' ORDER BY Name ASC", "", "")) {
				log(LogStatus.PASS, "Filter has been added on the page", YesNo.Yes);
				sa.assertTrue(true, "Filter has been added on the page");
			} else {
				log(LogStatus.ERROR, "Could not edit the Page and add the Filter", YesNo.Yes);
				sa.assertTrue(false, "Could not edit the Page and add the SDG");
			}

		} else {
			log(LogStatus.ERROR, "Could not open the App from the App Launcher", YesNo.Yes);
			sa.assertTrue(false, "Could not open the App from the App Launcher");
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc069_VerifyFundAndFirmDropDownCountAndAcendingOrder(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		EditPageBusinessLayer EP = new EditPageBusinessLayer(driver);

		String appPage = "Custom App Page";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ArrayList<String> labelName = new ArrayList<String>();
		labelName.add("Fund");
		labelName.add("Firm");

		ArrayList<Integer> count = new ArrayList<Integer>();
		count.add(84);
		count.add(34);

		if (BP.openAppFromAppLauchner(appPage, 50)) {
			log(LogStatus.INFO, "App page has been open", YesNo.No);

			if (AppBuilder.VerifyDropdownCountandAscendingOrder(labelName, count)) {
				log(LogStatus.INFO, "Dropdown count and Ascending order has been matched", YesNo.No);

			} else {
				log(LogStatus.INFO, "Dropdown count and Ascending order is not matched of ", YesNo.No);
			}

		} else {
			log(LogStatus.ERROR, "Could not open the App page", YesNo.No);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc070_VerifyGlobalFilterForFundAndFirm(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		EditPageBusinessLayer EP = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String appPage = "Custom App Page";
		String pageSize = "100";
		String fundSDGName = "Fund Filter Grid";
		String accountSDGName = "Account Filter Grid";
		String fundraisingSDGName = "Fundraising Filter Grid";
		String contactSDGName = "Contact Filter Grid";

		int fundRowCountBeforeFilter, fundraisingRowCountBeforeFilter, accountFilterRowCountBeforeFilter,
				contactFilterRowCountBeforeFilter;
		int fundRowCountAfterFilter, fundraisingRowCountAfterFilter, accountFilterRowCountAfterFilter,
				contactFilterRowCountAfterFilter;

		if (BP.openAppFromAppLauchner(appPage, 50)) {

			fundRowCountBeforeFilter = AppBuilder.numberOfRecords(fundSDGName, pageSize);
			accountFilterRowCountBeforeFilter = AppBuilder.numberOfRecords(accountSDGName, pageSize);
			fundraisingRowCountBeforeFilter = AppBuilder.numberOfRecords(fundraisingSDGName, pageSize);
			contactFilterRowCountBeforeFilter = AppBuilder.numberOfRecords(contactSDGName, pageSize);

			if (fundRowCountBeforeFilter != 0 && accountFilterRowCountBeforeFilter != 0
					&& fundraisingRowCountBeforeFilter != 0 && contactFilterRowCountBeforeFilter != 0) {
				log(LogStatus.INFO, "The row count before filter of FundSDG : " + fundRowCountBeforeFilter
						+ " \n AccountSDG : " + accountFilterRowCountBeforeFilter + " \nFundraising : "
						+ fundraisingRowCountBeforeFilter + " \n Contact : " + contactFilterRowCountBeforeFilter,
						YesNo.No);

				if (AppBuilder.selectFilter("Fund", "Appfluent Technology")) {
					log(LogStatus.INFO, "Filter has been selected of the fund", YesNo.No);
					CommonLib.ThreadSleep(8000);

					fundRowCountAfterFilter = AppBuilder.numberOfRecords(fundSDGName, pageSize);
					accountFilterRowCountAfterFilter = AppBuilder.numberOfRecords(accountSDGName, pageSize);
					fundraisingRowCountAfterFilter = AppBuilder.numberOfRecords(fundraisingSDGName, pageSize);
					contactFilterRowCountAfterFilter = AppBuilder.numberOfRecords(contactSDGName, pageSize);

					if (fundRowCountAfterFilter != 0 && accountFilterRowCountAfterFilter != 0
							&& fundraisingRowCountAfterFilter != 0 && contactFilterRowCountAfterFilter != 0) {
						log(LogStatus.INFO, "The row count before filter of FundSDG : " + fundRowCountAfterFilter
								+ " \n AccountSDG : " + accountFilterRowCountAfterFilter + " \nFundraising : "
								+ fundraisingRowCountAfterFilter + " \n Contact : " + contactFilterRowCountAfterFilter,
								YesNo.No);

						if (fundRowCountBeforeFilter == fundRowCountAfterFilter
								&& accountFilterRowCountBeforeFilter == accountFilterRowCountAfterFilter
								&& fundraisingRowCountBeforeFilter == fundraisingRowCountAfterFilter
								&& contactFilterRowCountBeforeFilter == contactFilterRowCountAfterFilter) {
							log(LogStatus.PASS,
									"The count of row has been matched.  The row count before filter of FundSDG : "
											+ fundRowCountBeforeFilter + " \n AccountSDG : "
											+ accountFilterRowCountBeforeFilter + " \nFundraising : "
											+ fundraisingRowCountBeforeFilter + " \n Contact : "
											+ contactFilterRowCountBeforeFilter
											+ " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter
											+ " \n AccountSDG : " + accountFilterRowCountAfterFilter
											+ " \nFundraising : " + fundraisingRowCountAfterFilter + " \n Contact : "
											+ contactFilterRowCountAfterFilter + "",
									YesNo.No);
							sa.assertTrue(true,
									"The count of row has been matched. The row count before filter of FundSDG : "
											+ fundRowCountBeforeFilter + " \n AccountSDG : "
											+ accountFilterRowCountBeforeFilter + " \nFundraising : "
											+ fundraisingRowCountBeforeFilter + " \n Contact : "
											+ contactFilterRowCountBeforeFilter
											+ " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter
											+ " \n AccountSDG : " + accountFilterRowCountAfterFilter
											+ " \nFundraising : " + fundraisingRowCountAfterFilter + " \n Contact : "
											+ contactFilterRowCountAfterFilter + "");
						} else {
							log(LogStatus.FAIL,
									"The count of row is not matched. The row count before filter of FundSDG : "
											+ fundRowCountBeforeFilter + " \n AccountSDG : "
											+ accountFilterRowCountBeforeFilter + " \nFundraising : "
											+ fundraisingRowCountBeforeFilter + " \n Contact : "
											+ contactFilterRowCountBeforeFilter
											+ " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter
											+ " \n AccountSDG : " + accountFilterRowCountAfterFilter
											+ " \nFundraising : " + fundraisingRowCountAfterFilter + " \n Contact : "
											+ contactFilterRowCountAfterFilter + "",
									YesNo.Yes);
							sa.assertTrue(false,
									"The count of row is not matched. The row count before filter of FundSDG : "
											+ fundRowCountBeforeFilter + " \n AccountSDG : "
											+ accountFilterRowCountBeforeFilter + " \nFundraising : "
											+ fundraisingRowCountBeforeFilter + " \n Contact : "
											+ contactFilterRowCountBeforeFilter
											+ " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter
											+ " \n AccountSDG : " + accountFilterRowCountAfterFilter
											+ " \nFundraising : " + fundraisingRowCountAfterFilter + " \n Contact : "
											+ contactFilterRowCountAfterFilter + "");
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
				log(LogStatus.ERROR, "Could not get the count of row ", YesNo.No);
				sa.assertTrue(false, "Could not get the count of row ");
			}

		} else {
			log(LogStatus.ERROR, "Could not open the app from the App Launcher", YesNo.No);
			sa.assertTrue(false, "Could not open the app from the App Launcher");
		}

		if (BP.openAppFromAppLauchner(appPage, 50)) {

			fundRowCountBeforeFilter = AppBuilder.numberOfRecords(fundSDGName, pageSize);
			accountFilterRowCountBeforeFilter = AppBuilder.numberOfRecords(accountSDGName, pageSize);
			fundraisingRowCountBeforeFilter = AppBuilder.numberOfRecords(fundraisingSDGName, pageSize);
			contactFilterRowCountBeforeFilter = AppBuilder.numberOfRecords(contactSDGName, pageSize);

			if (fundRowCountBeforeFilter != 0 && accountFilterRowCountBeforeFilter != 0
					&& fundraisingRowCountBeforeFilter != 0 && contactFilterRowCountBeforeFilter != 0) {
				log(LogStatus.INFO, "The row count before filter of FundSDG : " + fundRowCountBeforeFilter
						+ " \n AccountSDG : " + accountFilterRowCountBeforeFilter + " \nFundraising : "
						+ fundraisingRowCountBeforeFilter + " \n Contact : " + contactFilterRowCountBeforeFilter,
						YesNo.No);

				if (AppBuilder.selectFilter("Firm", "Appfluent Technology, Inc.")) {
					log(LogStatus.INFO, "Filter has been selected of the Firm", YesNo.No);
					CommonLib.ThreadSleep(8000);

					fundRowCountAfterFilter = AppBuilder.numberOfRecords(fundSDGName, pageSize);
					accountFilterRowCountAfterFilter = AppBuilder.numberOfRecords(accountSDGName, pageSize);
					fundraisingRowCountAfterFilter = AppBuilder.numberOfRecords(fundraisingSDGName, pageSize);
					contactFilterRowCountAfterFilter = AppBuilder.numberOfRecords(contactSDGName, pageSize);

					if (fundRowCountAfterFilter != 0 && accountFilterRowCountAfterFilter != 0
							&& fundraisingRowCountAfterFilter != 0 && contactFilterRowCountAfterFilter != 0) {
						log(LogStatus.INFO, "The row count After filter of FundSDG : " + fundRowCountAfterFilter
								+ " \n AccountSDG : " + accountFilterRowCountAfterFilter + " \nFundraising : "
								+ fundraisingRowCountAfterFilter + " \n Contact : " + contactFilterRowCountAfterFilter,
								YesNo.No);

						if (fundRowCountBeforeFilter == fundRowCountAfterFilter
								&& accountFilterRowCountBeforeFilter == accountFilterRowCountAfterFilter
								&& fundraisingRowCountBeforeFilter == fundraisingRowCountAfterFilter
								&& contactFilterRowCountBeforeFilter == contactFilterRowCountAfterFilter) {
							log(LogStatus.PASS,
									"The row count has been matched. The row count before filter of FundSDG : "
											+ fundRowCountBeforeFilter + " \n AccountSDG : "
											+ accountFilterRowCountBeforeFilter + " \nFundraising : "
											+ fundraisingRowCountBeforeFilter + " \n Contact : "
											+ contactFilterRowCountBeforeFilter
											+ " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter
											+ " \n AccountSDG : " + accountFilterRowCountAfterFilter
											+ " \nFundraising : " + fundraisingRowCountAfterFilter + " \n Contact : "
											+ contactFilterRowCountAfterFilter + "",
									YesNo.No);
							sa.assertTrue(true,
									"The count of has been matched. The row count before filter of FundSDG : "
											+ fundRowCountBeforeFilter + " \n AccountSDG : "
											+ accountFilterRowCountBeforeFilter + " \nFundraising : "
											+ fundraisingRowCountBeforeFilter + " \n Contact : "
											+ contactFilterRowCountBeforeFilter
											+ " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter
											+ " \n AccountSDG : " + accountFilterRowCountAfterFilter
											+ " \nFundraising : " + fundraisingRowCountAfterFilter + " \n Contact : "
											+ contactFilterRowCountAfterFilter + "");
						} else {
							log(LogStatus.FAIL, "Row count is not matched. The row count before filter of FundSDG : "
									+ fundRowCountBeforeFilter + " \n AccountSDG : " + accountFilterRowCountBeforeFilter
									+ " \nFundraising : " + fundraisingRowCountBeforeFilter + " \n Contact : "
									+ contactFilterRowCountBeforeFilter
									+ " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter
									+ " \n AccountSDG : " + accountFilterRowCountAfterFilter + " \nFundraising : "
									+ fundraisingRowCountAfterFilter + " \n Contact : "
									+ contactFilterRowCountAfterFilter + "", YesNo.Yes);
							sa.assertTrue(false, "Row count is not mathced. The row count before filter of FundSDG : "
									+ fundRowCountBeforeFilter + " \n AccountSDG : " + accountFilterRowCountBeforeFilter
									+ " \nFundraising : " + fundraisingRowCountBeforeFilter + " \n Contact : "
									+ contactFilterRowCountBeforeFilter
									+ " \n\nThe row count After filter of FundSDG : " + fundRowCountAfterFilter
									+ " \n AccountSDG : " + accountFilterRowCountAfterFilter + " \nFundraising : "
									+ fundraisingRowCountAfterFilter + " \n Contact : "
									+ contactFilterRowCountAfterFilter + "");
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
				log(LogStatus.ERROR, "Could not get the count of row ", YesNo.No);
				sa.assertTrue(false, "Could not get the count of row ");
			}

		} else {
			log(LogStatus.ERROR, "Could not open the app from the App Launcher", YesNo.No);
			sa.assertTrue(false, "Could not open the app from the App Launcher");
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void M9Tc071_AddFilterSequenceAndVerifyRecord(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		EditPageBusinessLayer EP = new EditPageBusinessLayer(driver);
		SDGPageBusinessLayer SB = new SDGPageBusinessLayer(driver);
		String appPage = "Custom App Page";
		String fundraisingSDGName = "Fundraising Filter Grid";
		int fundraisingRowCountBeforeFilter, fundraisingRowCountAfterFilter;
		String pageSize = "100";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		if (BP.openAppFromAppLauchner(60, "Sortable Data Grids")) {
			if (SB.sequenceFilter(projectName, "Fund Filter Grid")) {
				if (BP.openAppFromAppLauchner(appPage, 50)) {
					fundraisingRowCountBeforeFilter = AppBuilder.numberOfRecords(fundraisingSDGName, pageSize);
					if (fundraisingRowCountBeforeFilter != 0) {
						log(LogStatus.INFO,
								"The fundraising count before filter is : " + fundraisingRowCountBeforeFilter,
								YesNo.No);
					} else {
						log(LogStatus.ERROR, "Could not open the App page", YesNo.No);
						sa.assertTrue(false, "Could not get the Fundraising Count");
					}
					if (AppBuilder.selectFilter("Fund", "Appfluent Technology")) {
						log(LogStatus.INFO, "Filter has been selected from the fund", YesNo.No);
						CommonLib.ThreadSleep(8000);
						fundraisingRowCountAfterFilter = AppBuilder.numberOfRecords(fundraisingSDGName, pageSize);
						if (fundraisingRowCountAfterFilter == fundraisingRowCountBeforeFilter) {
							log(LogStatus.PASS, "Fundraising count is not filtered", YesNo.No);
							sa.assertTrue(true, "Fundraising count is not filtered");
						} else {
							log(LogStatus.PASS, "Fundraising count is filtered", YesNo.No);
							sa.assertTrue(true, "Fundraising count is filtered");
						}

					} else {
						log(LogStatus.ERROR, "Could not select the filter from the fund", YesNo.No);
						sa.assertTrue(true, "Could not select the filter from the fund");
					}

					log(LogStatus.INFO, "Sequence filter is not added", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Could not open the App page", YesNo.No);
					sa.assertTrue(true, "Could not open the App page");
				}
			} else {
				log(LogStatus.ERROR, "Could not open the App page", YesNo.No);
				sa.assertTrue(false, "Could not open the App page");
			}

			lp.CRMlogout();
			sa.assertAll();
		}
	}

	@Parameters({ "projectName" })

	@Test
	public void M9tc016_UpdateMyRecordsFieldWithSomeRandomText(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		boolean flag = false;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String sdgName = "Fund - First SDG Grid";
		String[][] sdgLabels = { { SDGCreationLabel.My_Records.toString(), "SDG Testing" } };
		String pageSize = "100";
		int rowCountAfterFilter;
		int rowCountAfterFilterAdminCase = 76;

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
						rowCountAfterFilter = AppBuilder.numberOfRecords(sdgName, pageSize);

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
	public void M9tc017_RemoveValueFromMyRecordsFieldAndListView(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		boolean flag = false;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String sdgName = "Fund - First SDG Grid";
		String[][] sdgLabels = { { SDGCreationLabel.My_Records.toString(), "" },
				{ SDGCreationLabel.List_View_Name.toString(), "" } };
		String pageSize = "100";
		int rowCountAfterFilter;
		int rowCountAfterFilterAdminCase = 84;

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
						rowCountAfterFilter = AppBuilder.numberOfRecords(sdgName, pageSize);

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
	public void M9tc018_VerifySDGGridFieldsWithRedirectURLAndGrouping(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		FundsPage fundPage = new FundsPage(driver);
		ReportsTab reportPage = new ReportsTab(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String sdgName = "Fund - First SDG Grid";

		String[] fieldsInSDG = { "FUND NAME", "OWNER NAME", "#TARGET CLOSED", "#STAGE - INTERESTED" };
		String[] fieldDataInSDG = { "Westpac Corporation", "CRM1 User15514", "2", "6" };

		List<String> columnInSDG = Arrays.asList(fieldsInSDG);
		List<String> columnDataInSDG = Arrays.asList(fieldDataInSDG);

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
									if (home.getUserNameHeader(columnDataInSDG.get(indexColumnDataFromList),
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
	public void M9tc019_VerifyNavigationAfterClickingonLinks(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ReportsTab reportPage = new ReportsTab(driver);
		ReportsTabBusinessLayer reportBusinessLayer = new ReportsTabBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String sdgName = "Fund - First SDG Grid";

		String[] fieldsInSDG = { "#STAGE - INTERESTED" };
		String[] fieldDataInSDG = { "6" };
		String[] fieldsInReport = { "Fundraising: Fundraising Name", "Fund Name: Fund Name" };
		String reportName = "#Stage - Interested";
		int reportRowsCount = 6;

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
	public void M9tc020_CreateOneListViewOnFundraisingsToValidateCount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		String[][] listViewSheetData = { { "Admin", "Fundraisings", "#Stage - Interested",
				"All users can see this list view", "All fundraisings", "Stage<Break>Fund Name", "equals<Break>equals",
				"Interested<Break>Sumo Logic - Nov 2017" } };

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

}