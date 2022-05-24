package com.navatar.scripts;

import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;
import static com.navatar.generic.SmokeCommonVariables.todaysDate;
import static com.navatar.generic.SmokeCommonVariables.AdminUserEmailID;
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
import java.util.List;

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
import com.navatar.generic.EnumConstants.ReportDashboardFolderType;
import com.navatar.generic.EnumConstants.ReportField;
import com.navatar.generic.EnumConstants.ReportFormatName;
import com.navatar.generic.EnumConstants.SDGCreationLabel;
import com.navatar.generic.EnumConstants.SDGLabels;
import com.navatar.generic.EnumConstants.SearchBasedOnExistingFundsOptions;
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
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.ReportsTabBusinessLayer;
import com.navatar.pageObjects.SDGPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class Module9 extends BaseLib {

	/*
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc001_1_CreateCRMUser1(String projectName) {
	 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver); String
	 * parentWindow = null; String[] splitedUserLastName =
	 * removeNumbersFromString(crmUser1LastName); String UserLastName =
	 * splitedUserLastName[0] + lp.generateRandomNumber(); String emailId =
	 * lp.generateRandomEmailId(gmailUserName);
	 * ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users",
	 * excelLabel.Variable_Name, "User1", excelLabel.User_Last_Name);
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); boolean flag =
	 * false; for (int i = 0; i < 3; i++) { try { if (home.clickOnSetUpLink()) {
	 * flag = true; parentWindow = switchOnWindow(driver); if (parentWindow == null)
	 * { sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create CRM User1"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create CRM User1"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create CRM User1"
	 * ); } if (setup.createPEUser(crmUser1FirstName, UserLastName, emailId,
	 * crmUserLience, crmUserProfile)) { log(LogStatus.INFO,
	 * "CRM User is created Successfully: " + crmUser1FirstName + " " +
	 * UserLastName, YesNo.No); ExcelUtils.writeData(testCasesFilePath, emailId,
	 * "Users", excelLabel.Variable_Name, "User1", excelLabel.User_Email);
	 * ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users",
	 * excelLabel.Variable_Name, "User1", excelLabel.User_Last_Name); flag = true;
	 * break;
	 * 
	 * } driver.close(); driver.switchTo().window(parentWindow);
	 * 
	 * } } catch (Exception e) { log(LogStatus.INFO,
	 * "could not find setup link, trying again..", YesNo.No); }
	 * 
	 * } if (flag) {
	 * 
	 * if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) { if
	 * (setup.installedPackages(crmUser1FirstName, UserLastName)) {
	 * appLog.info("PE Package is installed Successfully in CRM User: " +
	 * crmUser1FirstName + " " + UserLastName);
	 * 
	 * } else { appLog.error( "Not able to install PE package in CRM User1: " +
	 * crmUser1FirstName + " " + UserLastName); sa.assertTrue(false,
	 * "Not able to install PE package in CRM User1: " + crmUser1FirstName + " " +
	 * UserLastName); log(LogStatus.ERROR,
	 * "Not able to install PE package in CRM User1: " + crmUser1FirstName + " " +
	 * UserLastName, YesNo.Yes); } } } else {
	 * 
	 * log(LogStatus.ERROR, "could not click on setup link, test case fail",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "could not click on setup link, test case fail");
	 * 
	 * }
	 * 
	 * closeBrowser(); driver.switchTo().window(parentWindow); lp.CRMlogout(); lp =
	 * new LoginPageBusinessLayer(driver); String passwordResetLink = null; try {
	 * passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
	 * ExcelUtils.readDataFromPropertyFile("gmailUserName"),
	 * ExcelUtils.readDataFromPropertyFile("gmailPassword")); } catch
	 * (InterruptedException e2) { e2.printStackTrace(); }
	 * appLog.info("ResetLinkIs: " + passwordResetLink);
	 * driver.get(passwordResetLink); if (lp.setNewPassword()) {
	 * appLog.info("Password is set successfully for CRM User1: " +
	 * crmUser1FirstName + " " + UserLastName); } else {
	 * appLog.info("Password is not set for CRM User1: " + crmUser1FirstName + " " +
	 * UserLastName); sa.assertTrue(false, "Password is not set for CRM User1: " +
	 * crmUser1FirstName + " " + UserLastName); log(LogStatus.ERROR,
	 * "Password is not set for CRM User1: " + crmUser1FirstName + " " +
	 * UserLastName, YesNo.Yes); } lp.CRMlogout(); sa.assertAll(); }
	 * 
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void M9Tc001_2_CreateCRMUser2(String projectName) {
	 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver); String
	 * parentWindow = null; String[] splitedUserLastName =
	 * removeNumbersFromString(crmUser2LastName); String UserLastName =
	 * splitedUserLastName[0] + lp.generateRandomNumber(); String emailId =
	 * lp.generateRandomEmailId(gmailUserName2);
	 * ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users",
	 * excelLabel.Variable_Name, "User2", excelLabel.User_Last_Name);
	 * lp.CRMLogin(superAdminUserName, adminPassword, appName); boolean flag =
	 * false; for (int i = 0; i < 3; i++) { try { if (home.clickOnSetUpLink()) {
	 * flag = true; parentWindow = switchOnWindow(driver); if (parentWindow == null)
	 * { sa.assertTrue(false,
	 * "No new window is open after click on setup link in lighting mode so cannot create CRM User2"
	 * ); log(LogStatus.SKIP,
	 * "No new window is open after click on setup link in lighting mode so cannot create CRM User2"
	 * , YesNo.Yes);
	 * exit("No new window is open after click on setup link in lighting mode so cannot create CRM User2"
	 * ); } if (setup.createPEUser(crmUser2FirstName, UserLastName, emailId,
	 * crmUserLience, crmUserProfile)) { log(LogStatus.INFO,
	 * "CRM User is created Successfully: " + crmUser2FirstName + " " +
	 * UserLastName, YesNo.No); ExcelUtils.writeData(testCasesFilePath, emailId,
	 * "Users", excelLabel.Variable_Name, "User2", excelLabel.User_Email);
	 * ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users",
	 * excelLabel.Variable_Name, "User2", excelLabel.User_Last_Name); flag = true;
	 * break;
	 * 
	 * } driver.close(); driver.switchTo().window(parentWindow);
	 * 
	 * } } catch (Exception e) { log(LogStatus.INFO,
	 * "could not find setup link, trying again..", YesNo.No); }
	 * 
	 * } if (flag) {
	 * 
	 * if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) { if
	 * (setup.installedPackages(crmUser2FirstName, UserLastName)) {
	 * appLog.info("PE Package is installed Successfully in CRM User: " +
	 * crmUser2FirstName + " " + UserLastName);
	 * 
	 * } else { appLog.error( "Not able to install PE package in CRM User1: " +
	 * crmUser2FirstName + " " + UserLastName); sa.assertTrue(false,
	 * "Not able to install PE package in CRM User1: " + crmUser2FirstName + " " +
	 * UserLastName); log(LogStatus.ERROR,
	 * "Not able to install PE package in CRM User1: " + crmUser2FirstName + " " +
	 * UserLastName, YesNo.Yes); } } } else {
	 * 
	 * log(LogStatus.ERROR, "could not click on setup link, test case fail",
	 * YesNo.Yes); sa.assertTrue(false,
	 * "could not click on setup link, test case fail");
	 * 
	 * }
	 * 
	 * closeBrowser(); driver.switchTo().window(parentWindow); lp.CRMlogout(); lp =
	 * new LoginPageBusinessLayer(driver); String passwordResetLink = null; try {
	 * passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
	 * ExcelUtils.readDataFromPropertyFile("gmailUserName2"),
	 * ExcelUtils.readDataFromPropertyFile("gmailPassword")); } catch
	 * (InterruptedException e2) { e2.printStackTrace(); }
	 * appLog.info("ResetLinkIs: " + passwordResetLink);
	 * driver.get(passwordResetLink); if (lp.setNewPassword()) {
	 * appLog.info("Password is set successfully for CRM User2: " +
	 * crmUser2FirstName + " " + UserLastName); } else {
	 * appLog.info("Password is not set for CRM User2: " + crmUser2FirstName + " " +
	 * UserLastName); sa.assertTrue(false, "Password is not set for CRM User2: " +
	 * crmUser2FirstName + " " + UserLastName); log(LogStatus.ERROR,
	 * "Password is not set for CRM User2: " + crmUser2FirstName + " " +
	 * UserLastName, YesNo.Yes); } lp.CRMlogout(); sa.assertAll(); }
	 */



	@Parameters("projectName")
	@Test
	public void M9tc001_4_createCustomReport(String projectName) throws InterruptedException {


		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ReportsTabBusinessLayer report = new ReportsTabBusinessLayer(driver);
		
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
//		lp.CRMLogin(superAdminUserName, adminPassword);
		

			ReportField[][] fields = {{ReportField.Fundraising_Name, ReportField.Fund_Name},
					{ReportField.First_Name, ReportField.Last_Name,ReportField.Legal_Name,ReportField.Industry}};
			
			String datas[][] = 
				{{"Public Reports","#Stage - Interested","Fundraisings with Fund Name","All fundraisings","All Time","Closing Date","Fund Name: Fund Name<Break>Stage New","equals<Break>equals","Sumo Logic - Nov 2017<Break>Interested"},
					{"Public Reports","#Individuals","Contacts & Firms","All firms","All Time","Created Date","Industry","equals","Agriculture"}};
			
		
			String[] splitedReportName;
			int i=0;
			for(String[] data :datas) {
			
				splitedReportName = removeNumbersFromString("Test Module 9 Report");
				SmokeReport2Name = splitedReportName[0] + lp.generateRandomNumber();
			if (report.createCustomReportForFolderLightningMode(environment, mode, data[0], ReportFormatName.Null,
					SmokeReport2Name, SmokeReport2Name, data[2], fields[i], null, null,
					null, null, null)) {
				appLog.info("Custom Report is created succesdfully : " + SmokeReport2Name);
				sa.assertTrue(true,
						"Custom Report is created succesdfully : " + SmokeReport2Name);
				
					
				  if(report.addFilterInCustomReportLightning(data[3],data[5],data[4],data[6],data[7],
				  data[8])) {
				  appLog.info("All Filters has been successfully applied to the report : " +
				  SmokeReport2Name);
				  sa.assertTrue(true,
						  "All filters added to Custom Report : " + SmokeReport2Name);
				  
				  } else {
				  appLog.error("Filters are not applied to Custom Report : " +
				  SmokeReport2Name); sa.assertTrue(false,
				  "Not able to add filters to Custom Report : " + SmokeReport2Name); }
				 
					
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
	public void M9Tc002_1_Create_Fund_First_SDG_Grid(String projectName) {
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

			String[][] sdgLabels = { { SDGCreationLabel.SDG_Name.toString(), "Fund - First SDG Grid" },
					{ SDGCreationLabel.SDG_Tag.toString(), SDGRandomTagName },
					{ SDGCreationLabel.sObjectName.toString(), "navpeII__Fund__c" },
					{ SDGCreationLabel.Default_Sort.toString(), "Name Desc" } };

			if (sdg.createCustomSDG(projectName, "Fund - First SDG Grid", sdgLabels, action.BOOLEAN, 20)) {
				log(LogStatus.PASS, "create/verify created SDG : " + "Fund - First SDG Grid", YesNo.No);

				for (int i = 0; i < 1; i++) {
					String api = "Name";
					values = api;
					if (sdg.addFieldOnSDG(projectName, fields, values)) {
						log(LogStatus.INFO, "Successfully added fields on " + Sdg1Name, YesNo.Yes);

					} else {
						sa.assertTrue(false, "Not Able to add fields on SDG : " + Sdg1Name);
						log(LogStatus.SKIP, "Not Able to add fields on SDG : " + Sdg1Name, YesNo.Yes);
					}
				}

			} else {
				sa.assertTrue(false, "Not Able to create/verify created SDG : " + Sdg1Name);
				log(LogStatus.SKIP, "Not Able to create/verify created SDG : " + Sdg1Name, YesNo.Yes);
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
	public void M9Tc003_1_ValidateErrorMsg_Fund_First_SDG_Grid(String projectName) {
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

			String[][] sdgLabels = { { SDGCreationLabel.Filter.toString(), "Fund1" },
					{ SDGCreationLabel.List_View_Name.toString(), "Fund_Type" } };

			if (sdg.editCustomSDGandFoundErrorMsg(projectName, "Fund - First SDG Grid", sdgLabels, action.BOOLEAN, 20,
					"You can either fill 'Filter' or 'List View Name' to save the record.")) {
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
	public void M9Tc003_2_ValidateErrorMsg_Fund_First_SDG_Grid(String projectName) {
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

			String[][] sdgLabels = { { SDGCreationLabel.Filter.toString(), "Name <> 'Fund2'" },
					{ SDGCreationLabel.List_View_Name.toString(), "Launched_Date" } };

			if (sdg.editCustomSDGandFoundErrorMsg(projectName, "Fund - First SDG Grid", sdgLabels, action.BOOLEAN, 20,
					"You can either fill 'Filter' or 'List View Name' to save the record.")) {
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

}