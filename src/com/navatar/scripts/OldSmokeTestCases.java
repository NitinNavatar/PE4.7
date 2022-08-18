package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.Smoke_TWINS1Name;
import static com.navatar.generic.CommonVariables.Smoke_TWINS2Name;
import static com.navatar.generic.CommonVariables.TWTask3Subject;
import static com.navatar.generic.CommonVariables.adminPassword;
import static com.navatar.generic.CommonVariables.appName;
import static com.navatar.generic.CommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.remote.server.handler.SwitchToWindow;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.android.dx.gen.Local;
import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.pageObjects.CapitalCallsPageBusinessLayer;
import com.navatar.pageObjects.CoInvestmentSettingsTabBusinessLayer;
import com.navatar.pageObjects.CommitmentsPageBusinessLayer;
import com.navatar.pageObjects.ContactPageErrorMessage;
import com.navatar.pageObjects.ContactTransferTabBusinessLayer;
import com.navatar.pageObjects.ContactTransferTabErrorMessage;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.CorrespondenceListPageBusinessLayer;
import com.navatar.pageObjects.DealCreationTabBusinessLayer;
import com.navatar.pageObjects.EditPageBusinessLayer;
import com.navatar.pageObjects.EditPageErrorMessage;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.HomePageErrorMessage;
import com.navatar.pageObjects.FundDrawdownPageErrorMessage;
import com.navatar.pageObjects.IndividualInvestorCreationTab;
import com.navatar.pageObjects.IndividualInvestorCreationTabBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.navatar.pageObjects.PartnershipsPageBusinessLayer;
import com.navatar.pageObjects.MarketingInitiativesPageBusinesslayer;
import com.navatar.pageObjects.NavatarSetUpPageErrorMessage;
import com.navatar.pageObjects.MarketingInitiativesPageErrorMsg;
import com.navatar.pageObjects.PipelinesPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;
import com.navatar.generic.EmailLib;
import com.navatar.generic.EnumConstants.ActivityRelatedButton;
import com.navatar.generic.EnumConstants.ActivityRelatedLabel;
import com.navatar.generic.EnumConstants.ActivityTimeLineItem;
import com.navatar.generic.EnumConstants.AddProspectsTab;
import com.navatar.generic.EnumConstants.AddressAction;
import com.navatar.generic.EnumConstants.BulkActions_DefaultValues;
import com.navatar.generic.EnumConstants.CapitalCallPageFieldLabelText;
import com.navatar.generic.EnumConstants.CheckBox;
import com.navatar.generic.EnumConstants.CommitmentType;
import com.navatar.generic.EnumConstants.CreatedOrNot;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.DealCreationPageLayout;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.EmailTemplateType;
import com.navatar.generic.EnumConstants.Existing;
import com.navatar.generic.EnumConstants.FolderAccess;
import com.navatar.generic.EnumConstants.FundPageFieldLabelText;
import com.navatar.generic.EnumConstants.FundraisingContactPageTab;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.IndiviualInvestorSectionsName;
import com.navatar.generic.EnumConstants.LookUpIcon;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavatarQuickLink;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTab;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTabLayoutSection;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.OfficeLocationLabel;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.RelatedList;
import com.navatar.generic.EnumConstants.ReportDashboardFolderType;
import com.navatar.generic.EnumConstants.ReportField;
import com.navatar.generic.EnumConstants.SearchBasedOnExistingFundsOptions;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.fundraisingContactActions;
import com.navatar.generic.EnumConstants.searchContactInEmailProspectGrid;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SmokeCommonVariables;
import com.navatar.generic.SoftAssert;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.CoInvestmentSettingsTabBusinessLayer;
import com.navatar.pageObjects.CommitmentsPageBusinessLayer;
import com.navatar.pageObjects.ContactTransferTabBusinessLayer;
import com.navatar.pageObjects.ContactTransferTabErrorMessage;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.DealCreationTabBusinessLayer;
import com.navatar.pageObjects.EmailMyTemplatesPageBusinessLayer;
import com.navatar.pageObjects.FundDistributionsPageBusinessLayer;
import com.navatar.pageObjects.FundDrawdownsPageBusinessLayer;
import com.navatar.pageObjects.FundraisingsPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.HomePageErrorMessage;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.InvestorDistributionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.MarketingInitiativesPageBusinesslayer;
import com.navatar.pageObjects.MarketingInitiativesPageErrorMsg;
import com.navatar.pageObjects.NavatarSetUpPageErrorMessage;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.navatar.pageObjects.PartnershipsPageBusinessLayer;
import com.navatar.pageObjects.PipelinesPageBusinessLayer;
import com.navatar.pageObjects.ReportsTabBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.BaseLib.smokeFilePath;
import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.EnumConstants.*;
public class OldSmokeTestCases extends BaseLib {
	String passwordResetLink = null;
	String navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();
//	Scanner scn = new Scanner(System.in);
	// DRSmoke Modules Starts from here
	
//	Default Settings of PE:-
//	Deal Creation-->Checked
//	Individual Investor--->Uncheck
//	Commitment Creation-->Checked.
//	Contact Transfer-->Disable(Old Institution Only ---Contact Only)
//	Account Associations--->All uncheck
//	Bulk Email-->Checked.
//	Office Location-->Uncheck.
//	CoInvestment-->Uncheck.
//	Pipeline Stage Log-->Checked.
// deal source relate list header column on institution page (Pipeline Name<>Company Name<>Deal Type<>Stage<>Source Contact)
// deal source relate list header column on contact page (Pipeline Name<>Property Name<>Deal Type<>Stage<>Source Firm<>Log In Date<>Investment Size)
// pipeline related list on company page (Pipeline Name<>Stage<>Log In Date<>Investment Size<>Source Firm<>Deal Type<>Our Role)	
	
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc000_1_addField(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		List<String> abc = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		lp.switchToClassic();	
		if (home.clickOnSetUpLink(environment, Mode.Classic.toString())) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("Company");
			layoutName.add("Institution");
			layoutName.add("Individual Investor");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put("Account Record Type", "Account Name");
			abc= setup.DragNDrop(environment, Mode.Classic.toString(), object.Account, ObjectFeatureName.pageLayouts, layoutName, sourceANDDestination);

			if (!abc.isEmpty()) {
				log(LogStatus.FAIL, "field not added/already present 1", YesNo.Yes);
				sa.assertTrue(false, "field not added/already present 1");
			}else{
				log(LogStatus.INFO, "field added/already present 1", YesNo.Yes);
			}


		} else {
			sa.assertTrue(false, "Not able to click on setup link so cannot Add Field");
			log(LogStatus.FAIL, "Not able to click on setup link so cannot Add Field", YesNo.Yes);
		}

		ThreadSleep(5000);
		////////////////////////

		if (home.clickOnSetUpLink(environment, Mode.Classic.toString())) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("Company");
			layoutName.add("Institution");
			layoutName.add("Individual Investor");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put("Related List<break>Deals Sourced", "Contacts");
			abc= setup.DragNDrop(environment, Mode.Classic.toString(), object.Account, ObjectFeatureName.pageLayouts, layoutName, sourceANDDestination);

			if (!abc.isEmpty()) {
				log(LogStatus.FAIL, "field not added/already present 2", YesNo.Yes);
				sa.assertTrue(false, "field not added/already present 2");
			}else{
				log(LogStatus.INFO, "field added/already present 2", YesNo.No);

			}

		} else {
			sa.assertTrue(false, "Not able to click on setup link so cannot Add Field");
			log(LogStatus.FAIL, "Not able to click on setup link so cannot Add Field", YesNo.Yes);
		}

		//////////////////////////

		if (lp.activateLighting(environment, Mode.Classic.toString(), 20)) {
			log(LogStatus.PASS,"Able to activated Lighting",YesNo.No);
		} else {
			log(LogStatus.FAIL,"No Able to activate Lighting",YesNo.Yes);
			sa.assertTrue(false, "No Able to activate Lighting");
		}
		home.switchToLighting();
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc001_1_createCRMUser(String environment, String mode) {
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName2);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",excelLabel.User_Last_Name);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag = false;
		switchToDefaultContent(driver);
		
		for (int i = 0; i < 3; i++) {
			try {
				if (home.clickOnSetUpLink(environment, mode)) {
					flag = true;
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						parentWindow = switchOnWindow(driver);
						if (parentWindow == null) {
							sa.assertTrue(false,
									"No new window is open after click on setup link in lighting mode so cannot create CRM User1");
							log(LogStatus.SKIP,
									"No new window is open after click on setup link in lighting mode so cannot create CRM User1",
									YesNo.Yes);
							exit("No new window is open after click on setup link in lighting mode so cannot create CRM User1");
						}
					}
					if (sp.createPEUser(crmUser1FirstName, UserLastName, emailId, crmUserLience,
							crmUserProfile)) {
						log(LogStatus.INFO, "CRM User is created Successfully: " + crmUser1FirstName + " " + UserLastName, YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User1",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",
								excelLabel.User_Last_Name);
						flag = true;
						break;
						
					}
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						driver.close();
						driver.switchTo().window(parentWindow);
					}
				
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}
			
		}
		if (flag && (environment.equalsIgnoreCase("Test")|| environment.equalsIgnoreCase("Testing"))) {
			
			if (sp.installedPackages(crmUser1FirstName, UserLastName)) {
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
		
		
		
		lp.CRMlogout(environment, mode);
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
		try {
			passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
					ExcelUtils.readDataFromPropertyFile("gmailUserName2"),
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
		sa.assertAll();
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc001_2_AddRemoveTabAndActivateLighting(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		for (String[] userPass : userAndPassword) {
		lp.CRMLogin(userPass[0], userPass[1]);
		List<String> abc = new ArrayList<String>();
		
		
		String addRemoveTabName="Pipelines"+","+"Partnerships"+","+"Commitments"+","+"Fundraising Contacts"+","+"Marketing Initiatives"+","
				+"Marketing Prospects"+","+"Reports"+","+"Office Locations"+","+"Navatar Setup"+","+"Fund Distributions"+","+"Fund Drawdowns"
				+","+"Investor Distributions"+","+"Capital Calls";

		String[] addRemoveTabs = addRemoveTabName.split(",");
		WebElement ele;
		
		
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			
			for (String tab : addRemoveTabs) {
				ele=FindElement(driver, "//a[contains(@title,'"+tab+"')]", "Tab : "+tab,action.SCROLLANDBOOLEAN, 5);
				if (ele==null) {
					abc = lp.addRemoveCustomTab(tab, customTabActionType.Add);
					if (!abc.isEmpty()) {
						for (int i = 0; i < abc.size(); i++) {
							log(LogStatus.FAIL,"No Able to Add Tab : "+tab,YesNo.Yes);
						}
					} 
				} else {
					log(LogStatus.INFO,"Tab Already Added : "+tab,YesNo.No);
				}
			}

			addRemoveTabName="Dashboards"+","+"Navatar Investor Manager";
			addRemoveTabs =addRemoveTabName.split(",");
			for (String tab : addRemoveTabs) {
				ele=FindElement(driver, "//a[contains(@title,'"+tab+"')]", "Tab : "+tab,action.SCROLLANDBOOLEAN, 5);
				if (ele!=null) {
					abc = lp.addRemoveCustomTab(tab, customTabActionType.Remove);
					if (!abc.isEmpty()) {
						for (int i = 0; i < abc.size(); i++) {
							log(LogStatus.FAIL,"No Able to Remove Tab : "+tab,YesNo.Yes);
						}
					} 
				} else {
					log(LogStatus.FAIL,"Tab nOT Present: "+tab,YesNo.No);
				}
			}		
		} else {
			lp.switchToLighting();
				if (lp.addTab_Lighting( addRemoveTabName, 5)) {
					log(LogStatus.INFO,"Tab added : "+addRemoveTabName,YesNo.No);
				} else {
					log(LogStatus.FAIL,"Tab not added : "+addRemoveTabName,YesNo.No);
					sa.assertTrue(false, "Tab not added : "+addRemoveTabName);
				}		
			
			
		}
		ThreadSleep(5000);
		lp.CRMlogout(environment,mode);
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);

	}
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc001_3_createCustomEmailAndTemplate(String environment, String mode) {
	ReportsTabBusinessLayer report = new ReportsTabBusinessLayer(driver);
	EmailMyTemplatesPageBusinessLayer emailtemplate = new EmailMyTemplatesPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	String[] splitedReportFolderName = removeNumbersFromString(SmokeReportFolderName);
	SmokeReportFolderName = splitedReportFolderName[0] + lp.generateRandomNumber();
	if (report.createCustomReportOrDashboardFolder(environment, SmokeReportFolderName,
			ReportDashboardFolderType.ReportFolder, FolderAccess.ReadOnly)) {
		
		String[] splitedReportName = removeNumbersFromString(SmokeReportName);
		SmokeReportName = splitedReportName[0] + lp.generateRandomNumber();
		ReportField[] reportField= {ReportField.ContactID};
		
		ExcelUtils.writeData(smokeFilePath, SmokeReportFolderName, "Report", excelLabel.Variable_Name, "SmokeReport1",
				excelLabel.Report_Folder_Name);
		if (report.createCustomReportForFolder(environment, mode, SmokeReportFolderName,ReportFormatName.Null,SmokeReportName,
				SmokeReportName, SmokeReportType, reportField, SmokeReportShow, null, SmokeReportRange, null, null)) {
			appLog.info("Custom Report is created succesdfully : " + SmokeReportName);
			ExcelUtils.writeData(smokeFilePath, SmokeReportName, "Report", excelLabel.Variable_Name, "SmokeReport1",
					excelLabel.Report_Name);
		} else {
			appLog.error("Not able to create Custom Report : " + SmokeReportName);
			sa.assertTrue(false, "Not able to create Custom Report : " + SmokeReportName);
			log(LogStatus.ERROR, "Not able to create Custom Report : " + SmokeReportName, YesNo.Yes);
		}
		switchToDefaultContent(driver);
		home.clickOnSetUpLink(environment, Mode.Classic.toString());
		if (home.clickOnTab(environment, Mode.Classic.toString(), TabName.HomeTab)) {
			SmokeReportName="R2"+SmokeReportName;
			if (report.createCustomReportForFolder(environment, mode, SmokeReportFolderName,ReportFormatName.Null,SmokeReportName,
					SmokeReportName, SmokeReportType, null, SmokeReportShow, null, SmokeReportRange, null, null)) {
				appLog.info("Custom Report is created succesdfully : R2"+SmokeReportName);
			} else {
				appLog.error("Not able to create Custom Report : R2"+ SmokeReportName);
				sa.assertTrue(false, "Not able to create Custom Report : R2"+SmokeReportName);
				log(LogStatus.ERROR, "Not able to create Custom Report : R2"+SmokeReportName, YesNo.Yes);
			}
		}
	} else {
		appLog.error("Not able to create Custom Report folder: " + SmokeReportFolderName);
		sa.assertTrue(false, "Not able to create Custom Report folder: " + SmokeReportFolderName);
		log(LogStatus.ERROR, "Not able to create Custom Report folder: " + SmokeReportFolderName, YesNo.Yes);
	}
	home.switchToClassic();
	if (home.clickOnSetUpLink(environment, Mode.Classic.toString())) {
		String[] splitedEmailTemplateFolderName = removeNumbersFromString(EmailTemplate1_FolderName);
		EmailTemplate1_FolderName = splitedEmailTemplateFolderName[0] + lp.generateRandomNumber();
		if (emailtemplate.createCustomEmailFolder(environment, Mode.Classic.toString(), EmailTemplate1_FolderName, FolderAccess.ReadWrite)) {
			log(LogStatus.PASS, "Email Template Folder is created : "+EmailTemplate1_FolderName, YesNo.No);
			ExcelUtils.writeData(smokeFilePath, EmailTemplate1_FolderName, "CustomEmailFolder", excelLabel.Variable_Name, "EmailTemplate1",
					excelLabel.Email_Template_Folder_Label);
			ThreadSleep(2000);
			String[] splitedEmailTemplateName = removeNumbersFromString(EmailTemplate1_TemplateName);
			EmailTemplate1_TemplateName = splitedEmailTemplateName[0] + lp.generateRandomNumber();
			if (emailtemplate.createCustomEmailTemplate(environment, Mode.Classic.toString(), EmailTemplate1_FolderName, EmailTemplateType.Text,
					EmailTemplate1_TemplateName, EmailTemplate1_TemplateDescription, EmailTemplate1_Subject, EmailTemplate1_Body)) {
				appLog.info("EMail Template is created :" + EmailTemplate1_TemplateName);
				
				ExcelUtils.writeData(smokeFilePath, EmailTemplate1_TemplateName, "CustomEmailFolder", excelLabel.Variable_Name, "EmailTemplate1",
						excelLabel.Email_Template_Name);
				
			} else {
				appLog.error("EMail Template is not created :" + EmailTemplate1_TemplateName);
				sa.assertTrue(false, "EMail Template is not created :" + EmailTemplate1_TemplateName);
				log(LogStatus.ERROR, "EMail Template is not created :" + EmailTemplate1_TemplateName, YesNo.Yes);
			}
		} else {
			appLog.error("Not able to create Custom Email folder: " + EmailTemplate1_FolderName);
			sa.assertTrue(false, "Not able to create Custom Email folder: " + EmailTemplate1_FolderName);
			log(LogStatus.ERROR, "Not able to create Custom Email folder: " + EmailTemplate1_FolderName, YesNo.Yes);
		}
	} else {
		appLog.error("Not able to clicked on setup link so cannot create Email Folder And Template");
		sa.assertTrue(false, "Not able to clicked on setup link so cannot create Email Folder And Template");
		log(LogStatus.ERROR, "Not able to clicked on setup link so cannot create Email Folder And Template",
				YesNo.Yes);
	}
	home.switchToLighting();
	lp.CRMlogout(environment, mode);
	sa.assertAll();
}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc001_4_createPreCondition(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String FieldLabels = excelLabel.Street.toString() + "," + excelLabel.City.toString() + ","
				+ excelLabel.State.toString() + "," + excelLabel.Postal_Code.toString() + ","
				+ excelLabel.Country.toString() + "," + excelLabel.Phone.toString() ;
		for (int j = 0; j < 10; j++) {
			if (lp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
				ThreadSleep(2000);
				if (j == 0) {
					if (ins.createInstitution(environment, mode, SmokeINS1, SmokeINS1_RecordType, null, null)) {
						appLog.info("institution is created : " + SmokeINS1);
					} else {
						appLog.error("Not able to click on create Institute : " + SmokeINS1);
						sa.assertTrue(false, "Not able to click on create Institute : " + SmokeINS1);
						log(LogStatus.ERROR, "Not able to click on create Institute : " + SmokeINS1, YesNo.Yes);
					}
				}
				if (j == 1) {
					String FielsValues = SmokeINS2_Street + "," + SmokeINS2_City + "," + SmokeINS2_State + ","
							+ SmokeINS2_Postal_Code + "," + SmokeINS2_Country + "," + SmokeINS2_Phone ;
					if (ins.createInstitution(environment, mode, SmokeINS2, SmokeINS2_RecordType, FieldLabels,
							FielsValues)) {
						appLog.info("institution is created : " + SmokeINS2);
						String emailId = contact.generateRandomEmailId();
						if (lp.clickOnTab(environment, mode, TabName.ContactTab)) {
							appLog.info("click on contact tab");
							if (contact.createContact(mode, SmokeC5_FName, SmokeC5_LName, SmokeINS2, emailId,
									SmokeC5_RecordType,null, null, CreationPage.InstitutionPage,null,null)) {
								appLog.info("Contact is create Successfully: " + SmokeC5_FName + " " + SmokeC5_LName);
								ExcelUtils.writeData(smokeFilePath, emailId, "Contacts", excelLabel.Variable_Name,
										"SmokeC5", excelLabel.Contact_EmailId);
							} else {
								appLog.error("Not able to create Contact: " + SmokeC5_FName + " " + SmokeC5_LName);
								sa.assertTrue(false, "Not able to create Contact: " + SmokeC5_FName + " " + SmokeC5_LName);
								log(LogStatus.ERROR, "Not able to create Contact: " + SmokeC5_FName + " " + SmokeC5_LName,
										YesNo.Yes);
							}

						}else {
							appLog.error("Not able to click on related contact tab");
							sa.assertTrue(false, "Not able to click on related contact tab");
							log(LogStatus.ERROR, "Not able to click on related contact tab", YesNo.Yes);
						}
						
					} else {
						appLog.error("Not able to click on create Institute : " + SmokeINS2);
						sa.assertTrue(false, "Not able to click on create Institute : " + SmokeINS2);
						log(LogStatus.ERROR, "Not able to click on create Institute : " + SmokeINS2, YesNo.Yes);
					}
				}
				if (j == 2) {
					if (ins.createInstitution(environment, mode, SmokeINS3, SmokeINS3_RecordType, null, null)) {
						appLog.info("institution is created : " + SmokeINS3);
					} else {
						appLog.error("Not able to click on create Institute : " + SmokeINS3);
						sa.assertTrue(false, "Not able to click on create Institute : " + SmokeINS3);
						log(LogStatus.ERROR, "Not able to click on create Institute : " + SmokeINS3, YesNo.Yes);
					}
				}
				if (j == 3) {
					String FielsValues = SmokeINS4_Street + "," + SmokeINS4_City + "," + SmokeINS4_State + ","
							+ SmokeINS4_Postal_Code + "," + SmokeINS4_Country + "," + SmokeINS4_Phone ;
					if (ins.createInstitution(environment, mode, SmokeINS4, SmokeINS4_RecordType, FieldLabels,
							FielsValues)) {
						appLog.info("institution is created : " + SmokeINS4);
						String emailId = contact.generateRandomEmailId();
						String contact6FieldLabels=excelLabel.Other_Street.toString()+","+excelLabel.Other_City.toString()+","+excelLabel.Other_State.toString()+","+excelLabel.Other_Zip.toString()+","+excelLabel.Other_Country.toString();
						String contact6FieldValues=SmokeC6_Other_Street+","+SmokeC6_Other_City+","+SmokeC6_Other_State+","+SmokeC6_Other_Zip+","+SmokeC6_Other_Country;
						
						if (lp.clickOnTab(environment, mode, TabName.ContactTab)) {
							appLog.info("click on contact tab");
							if (contact.createContact(mode, SmokeC6_FName, SmokeC6_LName, SmokeINS4, emailId,SmokeC4_RecordType,
									contact6FieldLabels, contact6FieldValues, CreationPage.InstitutionPage,null,null)) {
								appLog.info("Contact is create Successfully: " + SmokeC6_FName + " " + SmokeC6_LName);
								ExcelUtils.writeData(smokeFilePath, emailId, "Contacts", excelLabel.Variable_Name,
										"SmokeC6", excelLabel.Contact_EmailId);
							} else {
								appLog.error("Not able to create Contact: " + SmokeC6_FName + " " + SmokeC6_LName);
								sa.assertTrue(false, "Not able to create Contact: " + SmokeC6_FName + " " + SmokeC6_LName);
								log(LogStatus.ERROR, "Not able to create Contact: " + SmokeC6_FName + " " + SmokeC6_LName,
										YesNo.Yes);
							}
						}else {
							appLog.error("Not able to click on related contact tab");
							sa.assertTrue(false, "Not able to click on related contact tab");
							log(LogStatus.ERROR, "Not able to click on related contact tab", YesNo.Yes);
						}
						
					} else {
						appLog.error("Not able to click on create Institute : " + SmokeINS4);
						sa.assertTrue(false, "Not able to click on create Institute : " + SmokeINS4);
						log(LogStatus.ERROR, "Not able to click on create Institute : " + SmokeINS4, YesNo.Yes);
					}
				}
				if (j == 4) {
					if (ins.createInstitution(environment, mode, SmokeINS5, SmokeINS5_RecordType, null, null)) {
						appLog.info("institution is created : " + SmokeINS5);
					} else {
						appLog.error("Not able to click on create Institute : " + SmokeINS5);
						sa.assertTrue(false, "Not able to click on create Institute : " + SmokeINS5);
						log(LogStatus.ERROR, "Not able to click on create Institute : " + SmokeINS5, YesNo.Yes);
					}
				}
				if (j == 5) {
					if (ins.createInstitution(environment, mode, SmokeINDINV1, SmokeINDINV1_RecordType, null, null)) {
						appLog.info("Indiviual Insvestor is created : " + SmokeINDINV1_RecordType);
					} else {
						appLog.error("Not able to click on create Indiviual Insvestor : " + SmokeINDINV1);
						sa.assertTrue(false, "Not able to click on create Indiviual Insvestor : " + SmokeINDINV1);
						log(LogStatus.ERROR, "Not able to click on create Indiviual Insvestor : " + SmokeINDINV1,
								YesNo.Yes);
					}
				}
				if (j == 6) {
					if (ins.createInstitution(environment, mode, SmokeINDINV2, SmokeINDINV2_RecordType, null, null)) {
						appLog.info("Indiviual Insvestor is created : " + SmokeINDINV2_RecordType);
					} else {
						appLog.error("Not able to click on create Indiviual Insvestor : " + SmokeINDINV1);
						sa.assertTrue(false, "Not able to click on create Indiviual Insvestor : " + SmokeINDINV2);
						log(LogStatus.ERROR, "Not able to click on create Indiviual Insvestor : " + SmokeINDINV2,
								YesNo.Yes);
					}
				}
				if (j == 7) {
					if (ins.createInstitution(environment, mode, SmokeINDINV3, SmokeINDINV3_RecordType, null, null)) {
						appLog.info("Indiviual Insvestor is created : " + SmokeINDINV3_RecordType);
					} else {
						appLog.error("Not able to click on create Indiviual Insvestor : " + SmokeINDINV3);
						sa.assertTrue(false, "Not able to click on create Indiviual Insvestor : " + SmokeINDINV3);
						log(LogStatus.ERROR, "Not able to click on create Indiviual Insvestor : " + SmokeINDINV3,
								YesNo.Yes);
					}
				}
				if (j == 8) {
					if (ins.createInstitution(environment, mode, SmokeCOM1, SmokeCOM1_RecordType, null, null)) {
						appLog.info("Company is created : " + SmokeCOM1);
					} else {
						appLog.error("Not able to click on create Company : " + SmokeCOM1);
						sa.assertTrue(false, "Not able to click on create Company : " + SmokeCOM1);
						log(LogStatus.ERROR, "Not able to click on create Company : " + SmokeCOM1, YesNo.Yes);
					}
				}
				if (j == 9) {
					if (ins.createInstitution(environment, mode, SmokeCOM2, SmokeCOM2_RecordType, null, null)) {
						appLog.info("Company is created : " + SmokeCOM2);
					} else {
						appLog.error("Not able to click on create Company : " + SmokeCOM2);
						sa.assertTrue(false, "Not able to click on create Company : " + SmokeCOM2);
						log(LogStatus.ERROR, "Not able to click on create Company : " + SmokeCOM2, YesNo.Yes);
					}

				}
			} else {
				appLog.error(
						"Not able to click on Institute Tab so cannot Create institute, indiviual insvestor and company");
				sa.assertTrue(false,
						"Not able to click on Institute Tab so cannot Create institute, indiviual insvestor and company");
				log(LogStatus.SKIP,
						"Not able to click on Institute Tab so cannot Create institute, indiviual insvestor and company",
						YesNo.Yes);
			}
			System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
		
			System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
		}
		for (int i = 0; i < 4; i++) {
			if (lp.clickOnTab(environment, mode, TabName.ContactTab)) {
				String emailId = contact.generateRandomEmailId();
				if (i == 0) {
					if (contact.createContact(mode, SmokeC1_FName, SmokeC1_LName, SmokeINS1, emailId, SmokeC1_RecordType,null,
							null, CreationPage.ContactPage,null,null)) {
						appLog.info("Contact is create Successfully: " + SmokeC1_FName + " " + SmokeC1_LName);
						ExcelUtils.writeData(smokeFilePath, emailId, "Contacts", excelLabel.Variable_Name, "SmokeC1",
								excelLabel.Contact_EmailId);
					} else {
						appLog.error("Not able to create Contact: " + SmokeC1_FName + " " + SmokeC1_LName);
						sa.assertTrue(false, "Not able to create Contact: " + SmokeC1_FName + " " + SmokeC1_LName);
						log(LogStatus.ERROR, "Not able to create Contact: " + SmokeC1_FName + " " + SmokeC1_LName,
								YesNo.Yes);
					}
				}
				if (i == 1) {
					if (contact.createContact(mode, SmokeC2_FName, SmokeC2_LName, SmokeINS1, emailId, SmokeC2_RecordType,null,
							null, CreationPage.ContactPage,null,null)) {
						appLog.info("Contact is create Successfully: " + SmokeC2_FName + " " + SmokeC2_LName);
						ExcelUtils.writeData(smokeFilePath, emailId, "Contacts", excelLabel.Variable_Name, "SmokeC2",
								excelLabel.Contact_EmailId);
					} else {
						appLog.error("Not able to create Contact: " + SmokeC2_FName + " " + SmokeC2_LName);
						sa.assertTrue(false, "Not able to create Contact: " + SmokeC2_FName + " " + SmokeC2_LName);
						log(LogStatus.ERROR, "Not able to create Contact: " + SmokeC2_FName + " " + SmokeC2_LName,
								YesNo.Yes);
					}
				}
				if (i == 2) {
					if (contact.createContact(mode, SmokeC3_FName, SmokeC3_LName, SmokeINDINV1, emailId,SmokeC3_RecordType,
							null, null, CreationPage.ContactPage,null,null)) {
						appLog.info("Contact is create Successfully: " + SmokeC3_FName + " " + SmokeC3_LName);
						ExcelUtils.writeData(smokeFilePath, emailId, "Contacts", excelLabel.Variable_Name, "SmokeC3",
								excelLabel.Contact_EmailId);
					} else {
						appLog.error("Not able to create Contact: " + SmokeC3_FName + " " + SmokeC3_LName);
						sa.assertTrue(false, "Not able to create Contact: " + SmokeC3_FName + " " + SmokeC3_LName);
						log(LogStatus.ERROR, "Not able to create Contact: " + SmokeC3_FName + " " + SmokeC3_LName,
								YesNo.Yes);
					}
				}
				if (i == 3) {
					if (contact.createContact(mode, SmokeC4_FName, SmokeC4_LName, SmokeINDINV1, "",SmokeC4_RecordType, null,
							null, CreationPage.ContactPage,null,null)) {
						appLog.info("Contact is create Successfully: " + SmokeC4_FName + " " + SmokeC4_LName);

					} else {
						appLog.error("Not able to create Contact: " + SmokeC4_FName + " " + SmokeC4_LName);
						sa.assertTrue(false, "Not able to create Contact: " + SmokeC4_FName + " " + SmokeC4_LName);
						log(LogStatus.ERROR, "Not able to create Contact: " + SmokeC4_FName + " " + SmokeC4_LName,
								YesNo.Yes);
					}
				}
			} else {
				appLog.error("Not able to click on Contacts Tab so cannot Create Contacts");
				sa.assertTrue(false, "Not able to click on Contacts Tab so cannot Create Contacts");
				log(LogStatus.SKIP, "Not able to click on Contacts Tab so cannot Create Contacts", YesNo.Yes);
			}
		}
		for (int i = 0; i < 3; i++) {
			if (lp.clickOnTab(environment, mode, TabName.MarketingInitiatives)) {
				if (i == 0) {
					if (market.createMarketInitiatives(environment, mode, Smoke_MI1)) {
						appLog.info("Create MI : " + Smoke_MI1);
					} else {
						appLog.error("Not able to create MI: " + Smoke_MI1);
						sa.assertTrue(false, "Not able to create MI: " + Smoke_MI1);
						log(LogStatus.SKIP, "Not able to create MI: " + Smoke_MI1, YesNo.Yes);
					}
				}
				if (i == 1) {
					if (market.createMarketInitiatives(environment, mode, Smoke_MI2)) {
						appLog.info("Create MI : " + Smoke_MI2);
					} else {
						appLog.error("Not able to create MI: " + Smoke_MI2);
						sa.assertTrue(false, "Not able to create MI: " + Smoke_MI2);
						log(LogStatus.SKIP, "Not able to create MI: " + Smoke_MI2, YesNo.Yes);
					}
				}
				if (i == 2) {
					if (market.createMarketInitiatives(environment, mode, Smoke_MI3)) {
						appLog.info("Create MI : " + Smoke_MI3);
					} else {
						appLog.error("Not able to create MI: " + Smoke_MI3);
						sa.assertTrue(false, "Not able to create MI: " + Smoke_MI3);
						log(LogStatus.SKIP, "Not able to create MI: " + Smoke_MI3, YesNo.Yes);
					}
				}
			} else {
				appLog.error("Not able to click on MI Tab so cannot MI's");
				sa.assertTrue(false, "Not able to click on MI Tab so cannot MI's");
				log(LogStatus.SKIP, "Not able to click on MI Tab so cannot MI's", YesNo.Yes);
			}
			if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				driver.navigate().refresh();
			}

		}
		FieldLabels = excelLabel.Target_Commitments + "," + excelLabel.Vintage_Year + ","
				+ FundPageFieldLabelText.Frist_Closing_Date;
		String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/YYYY");
		String Values1 = SmokeFund1Target_Commitments + "," + SmokeFund1_VintageYear + "," + date;
		String Values2 = SmokeFund2Target_Commitments + "," + SmokeFund2_VintageYear + "," + date;
		String Value3 = SmokeFund2Target_Commitments + "," + SmokeFund3_VintageYear + "," + date;
		String[] Values = { Values1, Values2, Value3 };
		for (int i = 0; i < 3; i++) {
			if (fund.clickOnTab(environment, mode, TabName.FundsTab)) {
				if (i == 0) {
					if (fund.createFund( mode, Smoke_Fund1, SmokeFund1_Type, SmokeFund1_InvestmentCategory,
							FieldLabels, Values[i])) {
						appLog.info("Fund is created Successfully: " + Smoke_Fund1);
						
					} else {
						appLog.error("Not able to click on fund: " + Smoke_Fund1);
						sa.assertTrue(false, "Not able to click on fund: " + Smoke_Fund1);
						log(LogStatus.SKIP, "Not able to click on fund: " + Smoke_Fund1, YesNo.Yes);
					}
					ExcelUtils.writeData(smokeFilePath, date, "Funds", excelLabel.Variable_Name, "SmokeFund1",
							excelLabel.Frist_Closing_Date);
				}
				if (i == 1) {
					if (fund.createFund(mode, Smoke_Fund2, SmokeFund2_Type, SmokeFund2_InvestmentCategory,
							FieldLabels, Values[i])) {
						appLog.info("Fund is created Successfully: " + Smoke_Fund2);
				
					} else {
						appLog.error("Not able to click on fund: " + Smoke_Fund2);
						sa.assertTrue(false, "Not able to click on fund: " + Smoke_Fund2);
						log(LogStatus.SKIP, "Not able to click on fund: " + Smoke_Fund2, YesNo.Yes);
					}
					ExcelUtils.writeData(smokeFilePath, date, "Funds", excelLabel.Variable_Name, "SmokeFund2",
							excelLabel.Frist_Closing_Date);
				}
				if (i == 2) {
					if (fund.createFund( mode, Smoke_Fund3, SmokeFund3_Type, SmokeFund3_InvestmentCategory,
							FieldLabels, Values[i])) {
						appLog.info("Fund is created Successfully: " + Smoke_Fund3);
					
					} else {
						appLog.error("Not able to click on fund: " + Smoke_Fund3);
						sa.assertTrue(false, "Not able to click on fund: " + Smoke_Fund3);
						log(LogStatus.SKIP, "Not able to click on fund: " + Smoke_Fund3, YesNo.Yes);
					}
					ExcelUtils.writeData(smokeFilePath, date, "Funds", excelLabel.Variable_Name, "SmokeFund3",
							excelLabel.Frist_Closing_Date);
				}
				
			} else {
				appLog.error("Not able to click on fund Tab so cannot Fund");
				sa.assertTrue(false, "Not able to click on fund Tab so cannot Fund");
				log(LogStatus.SKIP, "Not able to click on fund Tab so cannot Fund", YesNo.Yes);
			}

		}
		ThreadSleep(3000);
		switchToDefaultContent(driver);
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			if (lp.clickOnSettings_Lightning(environment, mode)) {
				if (click(driver,lp.getDisplayAndLayout_Lightning(30), "display and layout", action.SCROLLANDBOOLEAN)) {
					scrollDownThroughWebelement(driver, lp.getRecordPageSettings(30), "record page settings");
					if (click(driver, lp.getRecordPageSettings(30), "record page settings", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(3000);
						if (click(driver, lp.getRelatedListRecordPageSetting(30), "related list setting", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on related list option", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not click on related list option", YesNo.Yes);
							sa.assertTrue(false, "could not click on related list option");
						}
						if (click(driver,lp.getActivityTimelineRecordPageSetting(30), "activity timeline", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on activity setting", YesNo.No);
							if (click(driver, lp.getRecordPageSettingSave(30), "save button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on record page setting save button", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not clicked on record page setting save button", YesNo.Yes);
								sa.assertTrue(false, "could not clicked on record page setting save button");
							}


						}
						else {
							log(LogStatus.ERROR, "activity timeline is not clickable", YesNo.Yes);
							sa.assertTrue(false, "activity timeline is not clickable");
						}
					}
					else {
						log(LogStatus.ERROR,"could not click on record page setting", YesNo.Yes);
						sa.assertTrue(false, "could not click on record page setting");
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on display and layout link", YesNo.Yes);
					sa.assertTrue(false,  "could not click on display and layout link");
				}
			}
			else {
				log(LogStatus.ERROR, "could not click on settings link", YesNo.Yes);
				sa.assertTrue(false, "could not click on settings links");
			}
		}
		ThreadSleep(5000);
		lp.CRMlogout(environment, mode);
		sa.assertAll();

	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc002_verifyAddProspectAndAddContacts(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		ContactAndAccountName.put(SmokeC2_FName + " " + SmokeC2_LName, SmokeINS1);
		ContactAndAccountName.put(SmokeC3_FName + " " + SmokeC3_LName, SmokeINDINV1);
		ContactAndAccountName.put(SmokeC4_FName + " " + SmokeC4_LName, SmokeINDINV1);
		if (market.clickOnTab(environment, mode, TabName.MarketingInitiatives)) {
			if (market.clickOnCreatedMarketInitiatives(environment, mode, Smoke_MI1)) {
				appLog.info("clicked on MI : " + Smoke_MI1);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (market.clickOnShowMoreDropdownOnly(environment, PageName.Marketing_Initiative,mode)) {
						appLog.info("clicked on show 4 more action button in lighting");
					} else {
						appLog.error("Not able to click on show 4 more action button in lighting ");
						sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
					}
				}
				if (market.actionDropdownElement(environment, PageName.Marketing_Initiative, ShowMoreActionDropDownList.Add_Prospect, 10) != null) {
					appLog.info("add prospects button is displaying on MI : " + Smoke_MI1);
				} else {
					appLog.error("Add prospect button is not visible on created MI: " + Smoke_MI1);
					sa.assertTrue(false, "Add prospect button is not visible on created MI: " + Smoke_MI1);
					log(LogStatus.ERROR, "Add prospect button is not visible on created MI: " + Smoke_MI1, YesNo.Yes);
				}
				if (market.actionDropdownElement(environment, PageName.Marketing_Initiative, ShowMoreActionDropDownList.Email_Prospects, 10) != null) {
					appLog.info("Email prospects button is displaying on MI : " + Smoke_MI1);
				} else {
					appLog.error("Email prospect button is not visible on created MI: " + Smoke_MI1);
					sa.assertTrue(false, "Email prospect button is not visible on created MI: " + Smoke_MI1);
					log(LogStatus.ERROR, "Email prospect button is not visible on created MI: " + Smoke_MI1, YesNo.Yes);
				}
				if (click(driver, market.actionDropdownElement(environment, PageName.Marketing_Initiative, ShowMoreActionDropDownList.Add_Prospect, 10), " add prospects button",
						action.BOOLEAN)) {
					ThreadSleep(2000);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
					}
					WebElement ele = market.getAddProspectsHeaderText(60);
					if (ele != null) {
						String aa = ele.getText().trim();
						if (aa.contains("Add Prospects for " + Smoke_MI1)) {
							appLog.info("Add Prospects for " + Smoke_MI1 + " header name is verified.");
						} else {
							appLog.error("Add Prospects header is not Matched. Expected: Add Prospects for " + Smoke_MI1
									+ " /t Actual: " + aa);
							sa.assertTrue(false, "Add Prospects header is not Matched. Expected: Add Prospects for "
									+ Smoke_MI1 + " /t Actual: " + aa);
							log(LogStatus.ERROR, "Add Prospects header is not Matched. Expected: Add Prospects for "
									+ Smoke_MI1 + " /t Actual: " + aa, YesNo.Yes);
						}
					} else {
						appLog.error("Add Prospects header is not visible. Expected: Add Prospects for " + Smoke_MI1);
						sa.assertTrue(false,
								"Add Prospects header is not visible. Expected: Add Prospects for " + Smoke_MI1);
						log(LogStatus.ERROR,
								"Add Prospects header is not visible. Expected: Add Prospects for " + Smoke_MI1,
								YesNo.Yes);
					}
					if (market.addProspects(environment, mode, AddProspectsTab.AccountAndContacts, "Account:Legal Name",
							"not equal to", null, ContactAndAccountName, false)) {
						appLog.info("Contacts is added Successfully in Market Initiative on MI " + Smoke_MI1);

					} else {
						appLog.error("Contacts is not added in Market Initiative on MI " + Smoke_MI1);
						sa.assertTrue(false, "Contacts is not added in Market Initiative on MI " + Smoke_MI1);
						log(LogStatus.ERROR, "Contacts is not added in Market Initiative on MI " + Smoke_MI1,
								YesNo.Yes);
					}
				} else {
					appLog.error("Not able to click on add prospects button so cannot add Contacts");
					sa.assertTrue(false, "Not able to click on add prospects button so cannot add Contacts");
					log(LogStatus.ERROR, "Not able to click on add prospects button so cannot add Contacts", YesNo.Yes);
				}
			} else {
				appLog.error("Not able to clicked on created MI: " + Smoke_MI1);
				sa.assertTrue(false, "Not able to clicked on created MI: " + Smoke_MI1);
				log(LogStatus.ERROR, "Not able to clicked on created MI: " + Smoke_MI1, YesNo.Yes);
			}
		} else {
			appLog.error(
					"Not able to clicked on MI Tab so cannot verify add prospects and add contacts in add prospects.");
			sa.assertTrue(false,
					"Not able to clicked on MI Tab so cannot verify add prospects and add contacts in add prospects.");
			log(LogStatus.ERROR,
					"Not able to clicked on MI Tab so cannot verify add prospects and add contacts in add prospects.",
					YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc003_verifyAddContactsFromPastMarketingInitiativeTab(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele=null;
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		ContactAndAccountName.put(SmokeC2_FName + " " + SmokeC2_LName, SmokeINS1);
		ContactAndAccountName.put(SmokeC3_FName + " " + SmokeC3_LName, SmokeINDINV1);
		ContactAndAccountName.put(SmokeC4_FName + " " + SmokeC4_LName, SmokeINDINV1);
		if (market.clickOnTab(environment, mode, TabName.MarketingInitiatives)) {
			if (market.clickOnCreatedMarketInitiatives(environment, mode, Smoke_MI2)) {
				appLog.info("clicked on MI : " + Smoke_MI2);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (market.clickOnShowMoreDropdownOnly(environment, mode, PageName.MarketingInitiatives)) {
						appLog.info("clicked on show 4 more action button in lighting");
					} else {
						appLog.error("Not able to click on show 4 more action button in lighting ");
						sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
					}
				}
				ThreadSleep(2000);
				ele=market.actionDropdownElement( mode, PageName.MarketingInitiatives, ShowMoreActionDropDownList.Add_Prospect, 10);
				if (click(driver, ele, " add prospects button",action.BOOLEAN)) {
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
					}
					ThreadSleep(2000);
					if (market.addProspects(environment, mode, AddProspectsTab.PastMarketingInitiatives,
							"Marketing Prospect:Contact", "not equal to", null, ContactAndAccountName, true)) {
						appLog.info("Contacts is added Successfully in Market Initiative on MI " + Smoke_MI2);

					} else {
						appLog.error("Contacts is not added in Market Initiative on MI " + Smoke_MI2);
						sa.assertTrue(false, "Contacts is not added in Market Initiative on MI " + Smoke_MI2);
						log(LogStatus.ERROR, "Contacts is not added in Market Initiative on MI " + Smoke_MI2,
								YesNo.Yes);
					}
				} else {
					appLog.error("Not able to click on add prospects button so cannot add Contacts");
					sa.assertTrue(false, "Not able to click on add prospects button so cannot add Contacts");
					log(LogStatus.ERROR, "Not able to click on add prospects button so cannot add Contacts", YesNo.Yes);
				}
			} else {
				appLog.error("Not able to clicked on created MI: " + Smoke_MI2);
				sa.assertTrue(false, "Not able to clicked on created MI: " + Smoke_MI2);
				log(LogStatus.ERROR, "Not able to clicked on created MI: " + Smoke_MI2, YesNo.Yes);
			}
		} else {
			appLog.error(
					"Not able to clicked on MI Tab so cannot verify add prospects and add contacts in add prospects.");
			sa.assertTrue(false,
					"Not able to clicked on MI Tab so cannot verify add prospects and add contacts in add prospects.");
			log(LogStatus.ERROR,
					"Not able to clicked on MI Tab so cannot verify add prospects and add contacts in add prospects.",
					YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc004_verifyErrorMsgOnReportsTabInAddProspects(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String msg=null;
		String reportFolderName = ExcelUtils.readData(smokeFilePath, "Report", excelLabel.Variable_Name, "SmokeReport1",
				excelLabel.Report_Folder_Name);
		String reportName = ExcelUtils.readData(smokeFilePath, "Report", excelLabel.Variable_Name, "SmokeReport1",
				excelLabel.Report_Name);
		if (market.clickOnTab(environment, mode, TabName.MarketingInitiatives)) {
			if (market.clickOnCreatedMarketInitiatives(environment, mode, Smoke_MI3)) {
				appLog.info("clicked on MI : " + Smoke_MI2);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (market.clickOnShowMoreDropdownOnly(environment, mode, PageName.MarketingInitiatives)) {
						appLog.info("clicked on show 4 more action button in lighting");
					} else {
						appLog.error("Not able to click on show 4 more action button in lighting ");
						sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
					}
				}
				ele = market.actionDropdownElement(mode, PageName.MarketingInitiatives, ShowMoreActionDropDownList.Add_Prospect, 10);
				if (click(driver, ele, " add prospects button",action.BOOLEAN)) {
					ThreadSleep(2000);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
					}
					if (click(driver, market.getReportTab(60), "reports tab", action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on reports tab");
						if (market.getLoadReportsBtn(10) != null) {
							appLog.info("Load Report Button is visible on reports tab");
						} else {
							appLog.error("Load reports button is not visible on reports tab");
							sa.assertTrue(false, "Load reports button is not visible on reports tab");
							log(LogStatus.ERROR, "Load reports button is not visible on reports tab", YesNo.Yes);
						}
						if (click(driver, market.getLoadReportsBtn(10), "load reports button",
								action.SCROLLANDBOOLEAN)) {
							 ele = market.getSelectAReportsErrorMsg(20);
							if (ele != null) {
								String aa = ele.getText().trim();
								if (aa.contains(MarketingInitiativesPageErrorMsg.selectAReportsErrorMsg)) {
									appLog.info(MarketingInitiativesPageErrorMsg.selectAReportsErrorMsg
											+ " error messgae is verified.");
								} else {
									appLog.error(MarketingInitiativesPageErrorMsg.selectAReportsErrorMsg
											+ " error messgae is not verified. Actual :" + aa);
									sa.assertTrue(false, MarketingInitiativesPageErrorMsg.selectAReportsErrorMsg
											+ " error messgae is not verified. Actual :" + aa);
									log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.selectAReportsErrorMsg
											+ " error messgae is not verified. Actual :" + aa, YesNo.Yes);
								}
								if (click(driver, market.getSelectAReportErrorMsgOKBtn(20), "ok button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("Select a reports error message ok button");
								} else {
									appLog.error(
											"Select a reports error message ok button so cannot close error message pop up");
									sa.assertTrue(false,
											"Select a reports error message ok button so cannot close error message pop up");
									log(LogStatus.ERROR,
											"Select a reports error message ok button so cannot close error message pop up",
											YesNo.Yes);
								}
							} else {
								appLog.error(
										"Select a reports error message is not visible so cannot verify error message");
								sa.assertTrue(false,
										"Select a reports error message is not visible so cannot verify error message");
								log(LogStatus.ERROR,
										"Select a reports error message is not visible so cannot verify error message",
										YesNo.Yes);
							}
						} else {
							appLog.error("Not able to click on load reports button so cannot verify error message");
							sa.assertTrue(false,
									"Not able to click on load reports button so cannot verify error message");
							log(LogStatus.ERROR,
									"Not able to click on load reports button so cannot verify error message",
									YesNo.Yes);
						}
						
						// Azhar Start
						
						
						// 2nd 

						if (sendKeys(driver, market.getSelectReportSearchBox(10), "Test11 Navatar11 Report11", "Invalid Value on Search Text Box", action.BOOLEAN)) {


							if (click(driver, market.getLoadReportsBtn(10), "load reports button",
									action.SCROLLANDBOOLEAN)) {


								 ele = market.getReportDoesNotExistMsg(20);
								if (ele != null) {
									String aa = ele.getText().trim();
									if (aa.contains(MarketingInitiativesPageErrorMsg.reportDoestNotExistErrorMsg)) {
										appLog.info(MarketingInitiativesPageErrorMsg.reportDoestNotExistErrorMsg
												+ " error messgae is verified.");
									} else {
										appLog.error(MarketingInitiativesPageErrorMsg.reportDoestNotExistErrorMsg
												+ " error messgae is not verified. Actual :" + aa);
										sa.assertTrue(false, MarketingInitiativesPageErrorMsg.reportDoestNotExistErrorMsg
												+ " error messgae is not verified. Actual :" + aa);
										log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.reportDoestNotExistErrorMsg
												+ " error messgae is not verified. Actual :" + aa, YesNo.Yes);
									}
									if (click(driver, market.getReportDoesNotExistMsgOKBtn(20), "ok button",
											action.SCROLLANDBOOLEAN)) {
										appLog.error("Repert Deos not Exist ok button");
									} else {
										appLog.error(
												"Repert Deos not Exist ok button so cannot close error message pop up");
										sa.assertTrue(false,
												"Repert Deos not Exist ok button so cannot close error message pop up");
										log(LogStatus.ERROR,
												"Repert Deos not Exist ok button so cannot close error message pop up",
												YesNo.Yes);
									}
								} else {
									appLog.error(
											"Select a reports error message is not visible so cannot verify error message");
									sa.assertTrue(false,
											"Select a reports error message is not visible so cannot verify error message");
									log(LogStatus.ERROR,
											"Select a reports error message is not visible so cannot verify error message",
											YesNo.Yes);
								}

							} else {
								appLog.error("Not able to click on load reports button so cannot verify error message");
								sa.assertTrue(false,
										"Not able to click on load reports button so cannot verify error message");
								log(LogStatus.ERROR,
										"Not able to click on load reports button so cannot verify error message",
										YesNo.Yes);
							}


							// 3rd 

							if (click(driver, market.getAddProspectsHideSearchPopUp(10), "Add Prospects Hide Search PopUp", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on Add Prospect Hide Image",YesNo.Yes);	

								if (sendKeys(driver, market.getSelectReportSearchBox(10), "", "Clear Search Text Box", action.BOOLEAN)) {
									log(LogStatus.INFO, "Cleared Value on Search Text Box",YesNo.Yes);

									if(click(driver,market.getSelectAReportLookUpIcon(20), "select a report look up icon", action.SCROLLANDBOOLEAN)) {
										appLog.info("clicked on select a report look up icon");
										ThreadSleep(2000);

										if(click(driver,market.getSelectAReportSearchLookUpIcon(20), "select a report search look up icon", action.SCROLLANDBOOLEAN)) {
											appLog.info("clicked on select a report search look up icon");
											ThreadSleep(2000);

											 ele = market.getPleaseEnterAValuetMsg(20);
											if (ele != null) {
												String aa = ele.getText().trim();
												if (aa.contains(MarketingInitiativesPageErrorMsg.enterAVAlueErrorMsg)) {
													appLog.info(MarketingInitiativesPageErrorMsg.enterAVAlueErrorMsg
															+ " error messgae is verified.");
												} else {
													appLog.error(MarketingInitiativesPageErrorMsg.enterAVAlueErrorMsg+ " error messgae is not verified. Actual :" + aa);
													sa.assertTrue(false, MarketingInitiativesPageErrorMsg.enterAVAlueErrorMsg+ " error messgae is not verified. Actual :" + aa);
													log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.enterAVAlueErrorMsg+ " error messgae is not verified. Actual :" + aa, YesNo.Yes);
												}
												if (click(driver, market.getPleaseEnterAValueOKBtnOKBtn(20), "ok button",
														action.SCROLLANDBOOLEAN)) {
													appLog.error("Click on Please Enter a value ok button");
												} else {
													appLog.error("Not Able to Click on Please Enter a value ok button");
													sa.assertTrue(false,"Not Able to Click on Please Enter a value ok button");
													log(LogStatus.ERROR,"Not Able to Click on Please Enter a value ok button",YesNo.Yes);
												}
											} else {
												appLog.error(MarketingInitiativesPageErrorMsg.enterAVAlueErrorMsg+" is not visible so cannot verify error message");
												sa.assertTrue(false,MarketingInitiativesPageErrorMsg.enterAVAlueErrorMsg+" is not visible so cannot verify error message");
												log(LogStatus.ERROR,MarketingInitiativesPageErrorMsg.enterAVAlueErrorMsg+" is not visible so cannot verify error message",YesNo.Yes);
											}

											
											

										} else {
											sa.assertTrue(false, "Not able to click Select Report SearchBox Look Up Icon");
											log(LogStatus.ERROR, "Not able to click Select Report SearchBox Look Up Icon",YesNo.Yes);
										}

										// 4th 
										
											if (sendKeys(driver, market.getSelectAReportSearchTextBox(30),SmokeReportName, SmokeReportName,action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO," pass value in select a report search text box : "+SmokeReportName,YesNo.Yes);
												ThreadSleep(2000);
												
												ele=market.getSelectAReportPopUpFileName(SmokeReportFolderName, SmokeReportName, 20);
												if(ele!=null) {
													appLog.info(SmokeReportName+" is visible in "+SmokeReportFolderName+" folder select a reports look up pop up");
													
													if (click(driver, market.getCrossImgSelectAReportPopUp(10), "Cross Img", action.BOOLEAN)) {
														log(LogStatus.INFO, "click on Cross Icon",YesNo.No);
														
														ThreadSleep(2000);
														
														ele=market.getSelectAReportSearchTextBox(10);
														if(ele!=null) {
															msg = ele.getText().trim();
															appLog.info("Search Box Element is present");
															if (msg.isEmpty() || msg.equalsIgnoreCase("")) {
																appLog.info("Search Box is Blank after Click on Clear icon");	
															} else {
																sa.assertTrue(false,"Search Box Sould Blank after Click on Clear icon but it has a value : "+msg);
																log(LogStatus.ERROR,"Search Box Sould Blank after Click on Clear icon but it has a value : "+msg,YesNo.Yes);
														
															}
													
														}else {
															sa.assertTrue(false,"Search Box Element is not present");
															log(LogStatus.ERROR,"Search Box Element is not present",YesNo.Yes);
													
														}
									
													} else {
														sa.assertTrue(false, "Not able to clickon Cross Icon");
														log(LogStatus.ERROR, "Not able to clickon Cross Icon",YesNo.Yes);
													}
													
													}else {
													appLog.error("searched report is not visible in select a report pop up : "+SmokeReportName+" so cannot select it");
													sa.assertTrue(false,"searched report is not visible in select a report pop up : "+SmokeReportName+" so cannot select it");
													log(LogStatus.ERROR,"searched report is not visible in select a report pop up : "+SmokeReportName+" so cannot select it",YesNo.Yes);
											
													}
												
											} else {
												sa.assertTrue(false,"Not able to pass value in select a report search text box : "+SmokeReportName);
												log(LogStatus.ERROR,"Not able to pass value in select a report search text box : "+SmokeReportName,YesNo.Yes);
											}


										

									} else {
										sa.assertTrue(false, "Not able to click Select Report Look Up Icon");
										log(LogStatus.ERROR, "Not able to click Select Report Look Up Icon",YesNo.Yes);	
									}


									if (clickUsingJavaScript(driver, market.getSelectAReportPopUpCrossIcon(10), "Select A rEPORT Cross Icon", action.SCROLLANDBOOLEAN)) {
										appLog.info("Click on Select A RePORT pOPuP cROSS Icon");
									} else {
										appLog.error("Not Able to Click on Select A RePORT pOPuP cROSS Icon");
										sa.assertTrue(false,"Not Able to Click on Select A RePORT pOPuP cROSS Icon");
										log(LogStatus.ERROR,"Not Able to Click on Select A RePORT pOPuP cROSS Icon",YesNo.Yes);
								
									}

								} else {
									sa.assertTrue(false, "Not able to Clear Value on Search Text Box");
									log(LogStatus.ERROR, "Not able to Clear Value on Search Text Box",YesNo.Yes);	
								}

							} else {
								sa.assertTrue(false, "Not able to click on Add Prospect Hide Image");
								log(LogStatus.ERROR, "Not able to click on Add Prospect Hide Image",YesNo.Yes);
							}


						} else {
							sa.assertTrue(false, "Not able to Enter Value on Search Text Box");
							log(LogStatus.ERROR, "Not able to Enter Value on Search Text Box",YesNo.Yes);	
						}
						
						// Azhar End
						System.err.println(">>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<");
						ThreadSleep(5000);
						if (click(driver, market.getSelectAReportLookUpIcon(20), "select a report look up icon",
								action.SCROLLANDBOOLEAN)) {
								
							ele = market.getSelectAReportPopUpFileName(reportFolderName, reportName, 20);
								if (ele != null) {
									appLog.info(reportFolderName + " is visible in select a reports look up pop up");
									appLog.info(reportName + " is visible in " + reportFolderName
											+ " folder select a reports look up pop up");
								} else {
									appLog.error(reportName + " is not visible in " + reportFolderName
											+ " folder select a reports look up pop up");
									sa.assertTrue(false, reportName + " is not visible in " + reportFolderName
											+ " folder select a reports look up pop up");
									log(LogStatus.ERROR, reportName + " is not visible in " + reportFolderName
											+ " folder select a reports look up pop up", YesNo.Yes);

								}
								
								reportFolderName="Bulk E-Mail Reports";
								ele=market.getSelectAReportPopUpFolderName(reportFolderName, 10);
								scrollDownThroughWebelement(driver, ele, reportFolderName);
								reportName="Capital Call Recipients (by Fund)";
								ele = market.getSelectAReportPopUpFileName(reportFolderName, reportName, 20);
								if (ele != null) {
									appLog.info(reportFolderName + " is visible in select a reports look up pop up");
									appLog.info(reportName + " is visible in " + reportFolderName
											+ " folder select a reports look up pop up");
								} else {
									appLog.error(reportName + " is not visible in " + reportFolderName
											+ " folder select a reports look up pop up");
									sa.assertTrue(false, reportName + " is not visible in " + reportFolderName
											+ " folder select a reports look up pop up");
									log(LogStatus.ERROR, reportName + " is not visible in " + reportFolderName
											+ " folder select a reports look up pop up", YesNo.Yes);

								}
								
								
								reportFolderName="Bulk E-Mail Reports";
								reportName="Distribution Notice Recipients (by Fund)";
								ele = market.getSelectAReportPopUpFileName(reportFolderName, reportName, 20);
								if (ele != null) {
									appLog.info(reportFolderName + " is visible in select a reports look up pop up");
									appLog.info(reportName + " is visible in " + reportFolderName
											+ " folder select a reports look up pop up");
								} else {
									appLog.error(reportName + " is not visible in " + reportFolderName
											+ " folder select a reports look up pop up");
									sa.assertTrue(false, reportName + " is not visible in " + reportFolderName
											+ " folder select a reports look up pop up");
									log(LogStatus.ERROR, reportName + " is not visible in " + reportFolderName
											+ " folder select a reports look up pop up", YesNo.Yes);

								}
								
								reportFolderName="Public Reports";
								reportName="Sample Report: # of Contacts";
						
							if (sendKeys(driver, market.getSelectAReportSearchTextBox(30),
									"Sample Report: # of Contacts", "select a reports search text box",
									action.SCROLLANDBOOLEAN)) {
								 ele = market.getSelectAReportPopUpFileName("Public Reports",
										"Sample Report: # of Contacts", 20);
								if (ele != null) {
									appLog.info(reportName + " is visible in " + reportFolderName
											+ " folder select a reports look up pop up");
									if (click(driver, ele, "Sample Report: # of Contacts report link",
											action.SCROLLANDBOOLEAN)) {
										appLog.info("clicked on Sample Report: # of Contacts report");
									} else {
										appLog.error(
												"Not able to click on Sample Report: # of Contacts report so cannot select it");
										sa.assertTrue(false,
												"Not able to click on Sample Report: # of Contacts report so cannot select it");
										log(LogStatus.ERROR,
												"Not able to click on Sample Report: # of Contacts report so cannot select it",
												YesNo.Yes);
									}
								} else {
									appLog.error(reportName + " is not visible in " + reportFolderName
											+ " folder select a reports look up pop up");
									sa.assertTrue(false, reportName + " is not visible in " + reportFolderName
											+ " folder select a reports look up pop up");
									log(LogStatus.ERROR, reportName + " is not visible in " + reportFolderName
											+ " folder select a reports look up pop up", YesNo.Yes);

								}
								if (click(driver, market.getSelectAReportPopUpOKBtn(30),
										"select a reports pop up ok btn", action.SCROLLANDBOOLEAN)) {
									appLog.info("Clicked on OK button");
								} else {
									appLog.error(
											"Not able to click on select a reports OK button so cannot select report : \"Sample Report: # of Contacts\"");
									sa.assertTrue(false,
											"Not able to click on select a reports OK button so cannot select report : \"Sample Report: # of Contacts\"");
									log(LogStatus.ERROR,
											"Not able to click on select a reports OK button so cannot select report : \"Sample Report: # of Contacts\"",
											YesNo.Yes);
								}
								if (click(driver, market.getLoadReportsBtn(10), "load reports button",
										action.SCROLLANDBOOLEAN)) {
									appLog.info("clicked on load reports Button.");
									ele = market.getContactIDMissingErrorMsgInReportTab(20);
									if (ele != null) {
										String aa = ele.getText().trim();
										if (aa.contains(MarketingInitiativesPageErrorMsg.contactIDMissingErrorMsg)) {
											appLog.info(MarketingInitiativesPageErrorMsg.contactIDMissingErrorMsg
													+ " error messgae is verified.");
										} else {
											appLog.error(MarketingInitiativesPageErrorMsg.contactIDMissingErrorMsg
													+ " error messgae is not verified. Actual :" + aa);
											sa.assertTrue(false,
													MarketingInitiativesPageErrorMsg.contactIDMissingErrorMsg
															+ " error messgae is not verified. Actual :" + aa);
											log(LogStatus.ERROR,
													MarketingInitiativesPageErrorMsg.contactIDMissingErrorMsg
															+ " error messgae is not verified. Actual :" + aa,
													YesNo.Yes);
										}
										if (click(driver, market.getContactIDMissingErrorMsgPopUpOKBtnInReportTab(20),
												"ok button", action.SCROLLANDBOOLEAN)) {
											appLog.error("clicked on Contact ID Missing Error Msg PopUp OK Button");
											ThreadSleep(3000);
											if (market.getContactIDMissingErrorMsgPopUpOKBtnInReportTab(5) == null) {
												appLog.info("Contact ID Missing Error Msg PopUp is closed");
											} else {
												appLog.error("Contact ID Missing Error Msg PopUp is not closed");
												sa.assertTrue(false,
														"Contact ID Missing Error Msg PopUp is not closed");
												log(LogStatus.ERROR, "Contact ID Missing Error Msg PopUp is not closed",
														YesNo.Yes);
											}
										} else {
											appLog.error(
													"Not able to click on Contact ID Missing Error Msg PopUp OK Button so cannot close error message pop up");
											sa.assertTrue(false,
													"Not able to click on Contact ID Missing Error Msg PopUp OK Button so cannot close error message pop up");
											log(LogStatus.ERROR,
													"Not able to click on Contact ID Missing Error Msg PopUp OK Button so cannot close error message pop up",
													YesNo.Yes);
										}

									} else {
										appLog.error(
												"Select a reports error message is not visible so cannot verify error message");
										sa.assertTrue(false,
												"Select a reports error message is not visible so cannot verify error message");
										log(LogStatus.ERROR,
												"Select a reports error message is not visible so cannot verify error message",
												YesNo.Yes);
									}

								} else {
									appLog.error(
											"Not able to click on load reports button so cannot cannot load selected report : \"Sample Report: # of Contacts\"");
									sa.assertTrue(false,
											"Not able to click on load reports button so cannot cannot load selected report : \"Sample Report: # of Contacts\"");
									log(LogStatus.ERROR,
											"Not able to click on load reports button so cannot cannot load selected report : \"Sample Report: # of Contacts\"",
											YesNo.Yes);
								}

							} else {
								appLog.error(
										"Not able to pass value in select a report search text box : \"Sample Report: # of Contacts\" so cannot select report");
								sa.assertTrue(false,
										"Not able to pass value in select a report search text box : \"Sample Report: # of Contacts\" so cannot select report");
								log(LogStatus.ERROR,
										"Not able to pass value in select a report search text box : \"Sample Report: # of Contacts\" so cannot select report",
										YesNo.Yes);
							}

						} else {
							appLog.error(
									"Not able to click on select a reports look up icon button so cannot verify custom reports and Bulk E-Mail Report folders");
							sa.assertTrue(false,
									"Not able to click on select a reports look up icon button so cannot verify custom reports and Bulk E-Mail Report folders");
							log(LogStatus.ERROR,
									"Not able to click on select a reports look up icon button so cannot verify custom reports and Bulk E-Mail Report folders",
									YesNo.Yes);
						}

					} else {
						appLog.error("Not able to click on report tab so cannot verify error messages");
						sa.assertTrue(false, "Not able to click on report tab so cannot verify error messages");
						log(LogStatus.ERROR, "Not able to click on report tab so cannot verify error messages",
								YesNo.Yes);

					}

				} else {
					appLog.error("Not able to click on add prospects button so cannot add Contacts");
					sa.assertTrue(false, "Not able to click on add prospects button so cannot add Contacts");
					log(LogStatus.ERROR, "Not able to click on add prospects button so cannot add Contacts", YesNo.Yes);
				}
			} else {
				appLog.error("Not able to clicked on created MI: " + Smoke_MI3);
				sa.assertTrue(false, "Not able to clicked on created MI: " + Smoke_MI3);
				log(LogStatus.SKIP, "Not able to clicked on created MI: " + Smoke_MI3, YesNo.Yes);
			}
		} else {
			appLog.error("Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.");
			sa.assertTrue(false,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.");
			log(LogStatus.SKIP,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.",
					YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc005_1_addContactsOnReportsTabInAddProspects(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String reportFolderName = ExcelUtils.readData(smokeFilePath, "Report", excelLabel.Variable_Name, "SmokeReport1",
				excelLabel.Report_Folder_Name);
		String reportName = ExcelUtils.readData(smokeFilePath, "Report", excelLabel.Variable_Name, "SmokeReport1",
				excelLabel.Report_Name);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		ContactAndAccountName.put(SmokeC2_FName + " " + SmokeC2_LName, SmokeINS1);
		ContactAndAccountName.put(SmokeC3_FName + " " + SmokeC3_LName, SmokeINDINV1);
		ContactAndAccountName.put(SmokeC4_FName + " " + SmokeC4_LName, SmokeINDINV1);
		if (market.clickOnTab(environment, mode, TabName.MarketingInitiatives)) {
			if (market.clickOnCreatedMarketInitiatives(environment, mode, Smoke_MI3)) {
				appLog.info("clicked on MI : " + Smoke_MI2);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (market.clickOnShowMoreDropdownOnly(environment, mode, PageName.MarketingInitiatives)) {
						appLog.info("clicked on show 4 more action button in lighting");
					} else {
						appLog.error("Not able to click on show 4 more action button in lighting ");
						sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
					}
				}
				WebElement ele = market.actionDropdownElement(mode, PageName.MarketingInitiatives, ShowMoreActionDropDownList.Add_Prospect, 10);
				if (click(driver, ele, " add prospects button",action.BOOLEAN)) {
					ThreadSleep(2000);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
					}
					if (click(driver, market.getReportTab(60), "reports tab", action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on reports tab");
						for (int i = 0; i < 2; i++) {
							if (i == 1) {
								 ele = market.getAddProspectsHideSearchPopUp(5);
								if (ele != null) {
									if (click(driver, ele, "add prospects hide pop up image",
											action.SCROLLANDBOOLEAN)) {
										appLog.info("clicked on add prospects hide pop up image");
									} else {
										appLog.error(
												"Not able to click on add prospects hide pop up so cannot select report : "
														+ reportName);
										sa.assertTrue(false,
												"Not able to click on add prospects hide pop up so cannot select report : "
														+ reportName);
										log(LogStatus.ERROR,
												"Not able to click on add prospects hide pop up so cannot select report : "
														+ reportName,
												YesNo.Yes);
									}
								}
							}
							if (market.selectReportFromReportsTab(reportFolderName, reportName)) {
								appLog.info(reportName + " is selected and loaded successfully.");
								
								// Azhar Start
								if (i==0) {
									
								
									if (sendKeys(driver, market.getSearchForAContactTextBoxOnReportTab(10), SmokeC1_LName, "Search Box", action.BOOLEAN)) {
										appLog.info("Values Entered on Serach Box : "+SmokeC1_LName);
										ThreadSleep(2000);
										if (click(driver, market.getSearchIconForAContactOnReportTab(10), "search Icon ForA Contact On ReportTab",
												action.SCROLLANDBOOLEAN)) {
											appLog.info("clicked on search Icon ForA Contact On Report Tab");
											ThreadSleep(2000);
											
											if(market.ScrollAndClickOnContactNameCheckBoxInAddProspect(AddProspectsTab.Report,SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1, 10)) {
											log(LogStatus.INFO,"Conatct Found After Search : "+SmokeC1_FName + " " + SmokeC1_LName,YesNo.Yes);
											
											if(market.ScrollAndClickOnContactNameCheckBoxInAddProspect(AddProspectsTab.Report,SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1, 10)) {
												log(LogStatus.INFO,"Conatct Found After Search : "+SmokeC1_FName + " " + SmokeC1_LName,YesNo.Yes);
													
											} else {
												sa.assertTrue(false,"Conatct Not Found After Search : "+SmokeC1_FName + " " + SmokeC1_LName);
												log(LogStatus.ERROR,"Conatct Not Found After Search : "+SmokeC1_FName + " " + SmokeC1_LName,YesNo.Yes);
											}
												
										} else {
											sa.assertTrue(false,"Conatct Not Found After Search : "+SmokeC1_FName + " " + SmokeC1_LName);
											log(LogStatus.ERROR,"Conatct Not Found After Search : "+SmokeC1_FName + " " + SmokeC1_LName,YesNo.Yes);
										}
										
											if (click(driver, market.getClearIconForAContactOnReportTab(10), "Clear Img Icon",action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO,"clicked on Clear Img Icon",YesNo.Yes);	


											} else {
												sa.assertTrue(false,"Not able to clicked on Clear Img Icon");
												log(LogStatus.ERROR,"Not able to clicked on Clear Img Icon",YesNo.Yes);
											}

										} else {
											sa.assertTrue(false,"Not able to clicked on search Icon ForA Contac on Report Tab");
											log(LogStatus.ERROR,"Not able to clicked on search Icon ForA Contac on Report Tab",YesNo.Yes);
										}

									} else {
										sa.assertTrue(false,"Not Able to Entered Values Entered on Serach Box : "+SmokeC1_LName);
										log(LogStatus.ERROR,"Not Able to Entered Values Entered on Serach Box : "+SmokeC1_LName,YesNo.Yes);
									}
								}
								// Azhar End
								ThreadSleep(2000);
								market.getExcludeAnyAccountsIncludedInThisMITextBox(30).clear();
								if(click(driver, market.getExcludeAnyAccountsIncludedInThisMIRemoveBtn(30), "Remove nutton", action.SCROLLANDBOOLEAN)){
									String text = market.getSelectMIErrorMessage(30).getText().trim();
									if(text.equalsIgnoreCase(MarketingInitiativesPageErrorMsg.SelectMarketingInitiativeErrorMessage)){
										log(LogStatus.INFO, "Select MI error message is verified.", YesNo.No);
									} else {
										log(LogStatus.ERROR, "Select MI error message is not verified. Expected: "+MarketingInitiativesPageErrorMsg.SelectMarketingInitiativeErrorMessage+"\tActual: "+text, YesNo.Yes);
										sa.assertTrue(false, "Select MI error message is not verified. Expected: "+MarketingInitiativesPageErrorMsg.SelectMarketingInitiativeErrorMessage+"\tActual: "+text);
									}
									if(click(driver, market.getSelectMIErrorMessageOKButton(30), "Ok Button", action.BOOLEAN));
								} else {
									log(LogStatus.ERROR, "NOt able to click on remove button so cannot check the error message.", YesNo.Yes);
									sa.assertTrue(false,"NOt able to click on remove button so cannot check the error message.");
								}
								ThreadSleep(2000);
								if (market.selectProspectsContactAndVerifyReviewProspectList(environment,mode,AddProspectsTab.Report,
										ContactAndAccountName, false).isEmpty()) {
									appLog.info("Contact is selected Successfully in review prospects list");
									if (i == 0) {
										if (click(driver, market.getExcludeAnyAccountsIncludedInThisMILookUpIcon(20),
												"Exclude Any Accounts Included In This MI Look Up Icon",
												action.SCROLLANDBOOLEAN)) {
											appLog.info(
													"clicked on Exclude Any Accounts Included In This MI Look Up Icon ");
											if (market.selectValueFromLookUpWindow(Smoke_MI1)) {
												appLog.info(Smoke_MI1
														+ " is selected from Exclude Any Accounts Included In This MI LookUp PopUp");
												if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
													switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
												}
												if (click(driver,
														market.getExcludeAnyAccountsIncludedInThisMIRemoveBtn(20),
														"Exclude Any Accounts Included In This MI remove button",
														action.SCROLLANDBOOLEAN)) {
													appLog.info(
															"clicked on Exclude Any Accounts Included In This MI remove button");
													ThreadSleep(2000);
													 ele = market
															.getReomveContactConfirmationPopUpErrorMsg(30);
													if (ele != null) {
														String aa = ele.getText().trim();
														if (aa.contains(MarketingInitiativesPageErrorMsg
																.removeContactConfirmationErrorMsg("4"))) {
															appLog.info(MarketingInitiativesPageErrorMsg
																	.removeContactConfirmationErrorMsg("4")
																	+ " error message is verified.");

														} else {
															appLog.error("Error message is not verified : "
																	+ MarketingInitiativesPageErrorMsg
																			.removeContactConfirmationErrorMsg("4"));
															sa.assertTrue(false, "Error message is not verified : "
																	+ MarketingInitiativesPageErrorMsg
																			.removeContactConfirmationErrorMsg("4"));
															log(LogStatus.ERROR, "Error message is not verified : "
																	+ MarketingInitiativesPageErrorMsg
																			.removeContactConfirmationErrorMsg("4"),
																	YesNo.Yes);
														}
														if (click(driver,
																market.getReomveContactConfirmationPopUpOkBtn(30),
																"ok button", action.SCROLLANDBOOLEAN)) {
															appLog.info("clicked on OK button successfully");
														} else {
															appLog.error(
																	"Not able to click on OK button so cannot close contact remove confirmation pop up");
															sa.assertTrue(false,
																	"Not able to click on OK button so cannot close contact remove confirmation pop up");
															log(LogStatus.ERROR,
																	"Not able to click on OK button so cannot close contact remove confirmation pop up",
																	YesNo.Yes);
														}
													} else {
														appLog.error(
																"Error message is not visible so cannot verify error message: "
																		+ MarketingInitiativesPageErrorMsg
																				.removeContactConfirmationErrorMsg(
																						"4"));
														sa.assertTrue(false,
																"Error message is not visible so cannot verify error message: "
																		+ MarketingInitiativesPageErrorMsg
																				.removeContactConfirmationErrorMsg(
																						"4"));
														log(LogStatus.ERROR,
																"Error message is not visible so cannot verify error message: "
																		+ MarketingInitiativesPageErrorMsg
																				.removeContactConfirmationErrorMsg("4"),
																YesNo.Yes);
													}

												} else {
													appLog.error(
															"Not able to click on Exclude Any Accounts Included In This MI remove button so cannot remove contacts from review prospects list");
													sa.assertTrue(false,
															"Not able to click on Exclude Any Accounts Included In This MI remove button so cannot remove contacts from review prospects list");
													log(LogStatus.ERROR,
															"Not able to click on Exclude Any Accounts Included In This MI remove button so cannot remove contacts from review prospects list",
															YesNo.Yes);
												}
											} else {
												appLog.error(
														"Not able to select value from Exclude Any Accounts Included In This MI look up PopUp so cannot remove Contacts from review prospect list");
												sa.assertTrue(false,
														"Not able to select value from Exclude Any Accounts Included In This MI look up PopUp so cannot remove Contacts from review prospect list");
											}
										} else {
											appLog.error(
													"Not able to click on Exclude Any Accounts Included In This MI Look Up Icon so cannot select MI: "
															+ Smoke_MI1);
											sa.assertTrue(false,
													"Not able to click on Exclude Any Accounts Included In This MI Look Up Icon so cannot select MI: "
															+ Smoke_MI1);
											log(LogStatus.ERROR,
													"Not able to click on Exclude Any Accounts Included In This MI Look Up Icon so cannot select MI: "
															+ Smoke_MI1,
													YesNo.Yes);
										}

									} else {
										if (market.removeContactFromReviewPorspectList(environment, mode,
												AddProspectsTab.Report, SmokeC4_FName + " " + SmokeC4_LName,
												SmokeINDINV1)) {
											appLog.info(SmokeC4_FName + " " + SmokeC4_LName
													+ "Contact is removed from review prospect list");
											if (click(driver, market.getAddProspectMarketingInitiativeBtn(30),
													"add propects market initiative button", action.SCROLLANDBOOLEAN)) {
												appLog.info("clicked on add propects market initiative button");

											} else {
												appLog.error(
														"Not able to click on Add propects initiative button so cannot add contacts");
												sa.assertTrue(false,
														"Not able to click on Add propects initiative button so cannot add contacts");
												log(LogStatus.ERROR,
														"Not able to click on Add propects initiative button so cannot add contacts",
														YesNo.Yes);
											}
										} else {
											appLog.error(
													"Not able to remove Contact " + SmokeC4_FName + " " + SmokeC4_LName
															+ " from review prospect list so cannot add contacts in MI "
															+ Smoke_MI3);
											sa.assertTrue(false,
													"Not able to remove Contact " + SmokeC4_FName + " " + SmokeC4_LName
															+ " from review prospect list so cannot add contacts in MI "
															+ Smoke_MI3);
											log(LogStatus.ERROR,
													"Not able to remove Contact " + SmokeC4_FName + " " + SmokeC4_LName
															+ " from review prospect list so cannot add contacts in MI "
															+ Smoke_MI3,
													YesNo.Yes);
										}
									}
								} else {
									appLog.error(
											"Not able to select contacts and add contact into the review prospects list so cannot remove contacts from Exclude any Accounts included in this Marketing Initiative");
									sa.assertTrue(false,
											"Not able to select contacts and add contact into the review prospects list so cannot remove contacts from Exclude any Accounts included in this Marketing Initiative");
									log(LogStatus.ERROR,
											"Not able to select contacts and add contact into the review prospects list so cannot remove contacts from Exclude any Accounts included in this Marketing Initiative",
											YesNo.Yes);
								}
							} else {
								appLog.error("Not able to select report " + reportName
										+ " from select a report look up so cannot select contacts");
								sa.assertTrue(false, "Not able to select report " + reportName
										+ " from select a report look up so cannot select contacts");
								log(LogStatus.ERROR, "Not able to select report " + reportName
										+ " from select a report look up so cannot select contacts", YesNo.Yes);
							}

						}

					} else {
						appLog.error("Not able to click on report tab so cannot verify error messages");
						sa.assertTrue(false, "Not able to click on report tab so cannot verify error messages");
						log(LogStatus.ERROR, "Not able to click on report tab so cannot verify error messages",
								YesNo.Yes);

					}

				} else {
					appLog.error("Not able to click on add prospects button so cannot add Contacts");
					sa.assertTrue(false, "Not able to click on add prospects button so cannot add Contacts");
					log(LogStatus.ERROR, "Not able to click on add prospects button so cannot add Contacts", YesNo.Yes);
				}
			} else {
				appLog.error("Not able to clicked on created MI: " + Smoke_MI3);
				sa.assertTrue(false, "Not able to clicked on created MI: " + Smoke_MI3);
				log(LogStatus.SKIP, "Not able to clicked on created MI: " + Smoke_MI3, YesNo.Yes);
			}
		} else {
			appLog.error("Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.");
			sa.assertTrue(false,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.");
			log(LogStatus.SKIP,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.",
					YesNo.Yes);
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc005_2_addFilterLogicAndAddColumnFromWrenchIcon(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		ContactAndAccountName.put(SmokeC2_FName + " " + SmokeC2_LName, SmokeINS1);
		String msg;
		WebElement ele ;
		String parenTiD;
		if (market.clickOnTab(environment, mode, TabName.MarketingInitiatives)) {
			if (market.clickOnCreatedMarketInitiatives(environment, mode, Smoke_MI3)) {
				appLog.info("clicked on MI : " + Smoke_MI3);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (market.clickOnShowMoreDropdownOnly(environment, mode, PageName.MarketingInitiatives)) {
						appLog.info("clicked on show 4 more action button in lighting");
					} else {
						appLog.error("Not able to click on show 4 more action button in lighting ");
						sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
					}
				}
				
				
				ele=market.actionDropdownElement( mode, PageName.MarketingInitiatives, ShowMoreActionDropDownList.Add_Prospect, 10);
				if (click(driver, ele, " add prospects button",action.BOOLEAN)) {
					ThreadSleep(2000);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
						
					}
					
					// Azhar Start
					
					if (click(driver, market.getSearchBtnOnAddProspect(10), "Search Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "click on Search button so going to check the error message.", YesNo.No);	
						ThreadSleep(2000);
						
						 ele = market.getPleaseSelectASearchCriteriaMsg(20);
							if (ele != null) {
								 msg = ele.getText().trim();
								if (msg.contains(MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage)) {
									appLog.info(MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage+ " error messgae is verified.");
								} else {
									sa.assertTrue(false, MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage+ " error messgae is not verified. Actual :" + msg);
									log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage+ " error messgae is not verified. Actual :" + msg, YesNo.Yes);
								}
								if (click(driver, market.getPleaseSelectASearchCriteriaPopUpOKBtn(PageName.MarketingInitiatives, 10), "ok button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("Select a criteria error message ok button");
									ThreadSleep(2000);
									
									 ele = market.getPleaseSelectASearchCriteriaMsg(5);
										if (ele == null) {
											log(LogStatus.INFO,"Select a search Criteria error is not visible after Click on OK Button",YesNo.No);	
										}else{
											sa.assertTrue(false,"Select a search Criteria error is visible after Click on OK Button");
											log(LogStatus.ERROR,"Select a search Criteria error is visible after Click on OK Button",YesNo.Yes);
										
										}
								} else {
									sa.assertTrue(false,"Select a search Criteria error message ok button so cannot close error message pop up");
									log(LogStatus.ERROR,"Select a search Criteria error message ok button so cannot close error message pop up",YesNo.Yes);
								}
							} else {
								sa.assertTrue(false,"Select a search Criteria error message is not visible so cannot verify error message");
								log(LogStatus.ERROR,"Select a search Criteria error message is not visible so cannot verify error message",YesNo.Yes);
							}
							
					} else {
						log(LogStatus.ERROR, "NOt able to click on Search button so cannot check the error message.", YesNo.Yes);
						sa.assertTrue(false,"NOt able to click on Search button so cannot check the error message.");
					}

					// Azhar End
					
					if(click(driver, market.getAddRowLink(AddProspectsTab.AccountAndContacts), "account and contact tab", action.BOOLEAN)) {
						log(LogStatus.FAIL,"Clicked on Add row link",YesNo.No);
						ThreadSleep(2000);
						if(click(driver, market.getRowRemoveIcon().get(0), "remove icon", action.BOOLEAN)) {
							log(LogStatus.FAIL,"clicked on remove row link",YesNo.No);
						}else {
							appLog.error("Not able to click on remove row link so cannot check remove functionality");
							log(LogStatus.FAIL,"Not able to click on remove row link so cannot check remove functionality",YesNo.Yes);
							sa.assertTrue(false,"Not able to click on remove row link so cannot check remove functionality");
						}
						ThreadSleep(2000);
					}else {
						appLog.error("Not able to click on Add row link so cannot check remove functionality");
						log(LogStatus.FAIL,"Not able to click on Add row link so cannot check remove functionality",YesNo.Yes);
						sa.assertTrue(false,"Not able to click on Add row link so cannot check remove functionality");
					}
					
					// Azhar Start
					
					String	Xpath="addprospectid:add_prspct_frm:cmdlink";
					ele=isDisplayed(driver,FindElement(driver, "//a[@id='"+Xpath+"']", "Add filter logic link", action.BOOLEAN,10), "Visibility", 10, "Add filter logic link");
					if(ele!=null) {
						log(LogStatus.INFO,"find add filter logic Link",YesNo.No);	
						if(click(driver, ele, "add Filter Logic Button", action.BOOLEAN)) {
						
							log(LogStatus.INFO,"clicked on add Filter Logic Button",YesNo.No);	
							ThreadSleep(2000);
							
							Xpath="addprospectid:add_prspct_frm:textfilt";
						
							ele=isDisplayed(driver,FindElement(driver, "//input[@id='"+Xpath+"']", "Add filter logic text box", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
							if(ele!=null) {
								log(LogStatus.INFO,"find add filter logic text box",YesNo.No);	
							}else {
								log(LogStatus.FAIL,"Not able find add filter logic text box",YesNo.Yes);
								sa.assertTrue(false, "Not able find add filter logic text box  ");
							}
							
							
							if (click(driver, market.getInfoLink(10), "Info Link", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"click on Info Link",YesNo.No);
								ThreadSleep(2000);
								parenTiD = switchOnWindow(driver);
								
								if (parenTiD!=null) {
									log(LogStatus.INFO,"New Window is open",YesNo.No);
									Xpath="//h1//*[text()='Add Filter Logic']";
									ele=isDisplayed(driver,FindElement(driver, Xpath, "Add filter logic Window", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
									if(ele!=null) {
										log(LogStatus.INFO,"Add Filter Logic Window is open",YesNo.No);	
									}else {
										log(LogStatus.FAIL,"Add Filter Logic Window is not open",YesNo.Yes);
										sa.assertTrue(false, "Add Filter Logic Window is not open");
									}
									driver.close();
									driver.switchTo().window(parenTiD);
								} else {
									log(LogStatus.FAIL,"No New Window is open",YesNo.Yes);
									sa.assertTrue(false, "No New Window is open");
								}
							} else {
								log(LogStatus.FAIL,"Not able to click on Info Link",YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Info Link");
							}
							
							if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
								
							}
							
							for (int j = 0; j < 2; j++) {
								Xpath="AddRow2";
								ele=isDisplayed(driver,FindElement(driver, "//a[@id='"+Xpath+"']", "Add row button", action.BOOLEAN,10), "Visibility", 10, "Add row button");
								
								if(click(driver, ele, "add row button", action.BOOLEAN)) {
									log(LogStatus.INFO,"clicked on add row link : "+j,YesNo.No);
								}else {
									log(LogStatus.FAIL,"Not Able to click on add row link : "+j,YesNo.Yes);
									sa.assertTrue(false, "Not Able to click on add row link : "+j);	
								}
								ThreadSleep(2000);
							}
								
							if (click(driver, market.getAddProspectClearBtn(10), "Clear Btn", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"click on Clear Btn",YesNo.No);	
							} else {
								log(LogStatus.FAIL,"Not able to click on Clear Btn",YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Clear Btn");
							}
							
							
						}else {
							log(LogStatus.FAIL,"Not able to click on add filter logic ",YesNo.Yes);
							sa.assertTrue(false, "Not able to click on add filter logic ");
					
						}
					}else {
						log(LogStatus.FAIL,"Not able find add filter logic link ",YesNo.Yes);
						sa.assertTrue(false, "Not able find add filter logic link ");
					
					}
					
					// Azhar End
					
					if(market.SearchforProspects(AddProspectsTab.AccountAndContacts,"Account:Legal Name,Contact:Email,Contact:Legal Name","equals,equals,equals",SmokeINS1+","+SmokeC1_EmailID+","+SmokeINS1,"(1 AND 2) OR 3")) {
						appLog.info("Filter logic applied successfully ");
						ThreadSleep(5000);
						if(market.selectProspectsContactAndVerifyReviewProspectList(environment,mode,AddProspectsTab.AccountAndContacts, ContactAndAccountName, false,false).isEmpty()) {
							log(LogStatus.PASS, "Search Contacts is visible in Select Propects Grid ", YesNo.No);
						}else {
							appLog.error("Search Contacts is not visible in Select Propects Grid ");
							log(LogStatus.FAIL,"Search Contacts is not visible in Select Propects Grid ",YesNo.Yes);
							sa.assertTrue(false, "Search Contacts is not visible in Select Propects Grid ");
						}
						
						
						// Azhar Start
						
						if(click(driver, market.getSelectProspectsWrenchIcon(PageName.MarketingInitiatives,mode,60), "wrench icon", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(1000);
							
							if(selectVisibleTextFromDropDown(driver, market.getColumnToDisplayViewDropDownList(PageName.MarketingInitiatives,30), "view drop down list", ViewFieldsName.Contact_Fields)) {
								log(LogStatus.INFO,"Select value from view drop down list : "+ViewFieldsName.Contact_Fields,YesNo.Yes);
								
							}else {
								log(LogStatus.FAIL,"Not able to select value from view drop down list : "+ViewFieldsName.Contact_Fields,YesNo.Yes);
								sa.assertTrue(false, "Not able to select value from view drop down list : "+ViewFieldsName.Contact_Fields);
							}
							ThreadSleep(1000);
							
							if(selectVisibleTextFromDropDown(driver, market.getColumnToDisplayViewDropDownList(PageName.MarketingInitiatives,30), "view drop down list", ViewFieldsName.Account_Fields)) {
								log(LogStatus.INFO,"Select value from view drop down list : "+ViewFieldsName.Account_Fields,YesNo.Yes);
								
							}else {
								log(LogStatus.FAIL,"Not able to select value from view drop down list : "+ViewFieldsName.Account_Fields,YesNo.Yes);
								sa.assertTrue(false, "Not able to select value from view drop down list : "+ViewFieldsName.Account_Fields);
							}
							ThreadSleep(1000);
							
							if (click(driver, market.getWrenchIconCancelBtn(10), "Cancel Btn", action.BOOLEAN)) {
								log(LogStatus.INFO,"click on Wrench Icon Cancel Btn",YesNo.No);
							} else {
								log(LogStatus.FAIL,"Not able to click on Wrench Icon Cancel Btn",YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Wrench Icon Cancel Btn");
							}
							ThreadSleep(1000);
						}else{
							log(LogStatus.FAIL,"Not able to click on Wrench Icon",YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Wrench Icon");
						}
						
						// Azhar End
						
						String [] ss = {"Birthdate","Firm Phone"};
						if(market.addAndRemoveCloumnInSelectProspectGrid(mode,PageName.MarketingInitiatives,ss, null, null,true).isEmpty()) {
							log(LogStatus.PASS, "column added form column to display popup", YesNo.No);
							ThreadSleep(2000);
							if(compareMultipleList(driver, "Birthdate"+","+"Firm Phone", market.getSelectProspectsHeaderTextList(PageName.MarketingInitiatives)).isEmpty()) {
								log(LogStatus.PASS, "Selected Prospects Header Text is verified ", YesNo.No);
							}else {
								
								log(LogStatus.FAIL,"Selected Prospects Header Text is not verified",YesNo.Yes);
								sa.assertTrue(false, "Selected Prospects Header Text is not verified");
							}
							ThreadSleep(2000);
							if(market.columnToDisplayRevertToDefaultsSettings(PageName.MarketingInitiatives,mode)) {
								log(LogStatus.PASS, "column to display settings is revert to default successfully", YesNo.No);
							}else {
								appLog.error("column to display settings is not revert to default");
								log(LogStatus.FAIL,"column to display settings is not revert to default",YesNo.Yes);
								sa.assertTrue(false,"column to display settings is not revert to default");
							}
							if(!compareMultipleList(driver, "Birthdate"+","+" Firm Phone", market.getSelectProspectsHeaderTextList(PageName.MarketingInitiatives)).isEmpty()) {
								log(LogStatus.PASS, "Select Prospects Header Text is removed", YesNo.No);
							}else {
								appLog.error("Select Prospects Header Text is not removed");
								log(LogStatus.FAIL,"Select Prospects Header Text is not removed",YesNo.Yes);
								sa.assertTrue(false, "Select Prospects Header Text is not removed");
							}
						}else {
							appLog.error("Not able to add column form column to display popup");
							log(LogStatus.FAIL,"Not able to add column form column to display popup",YesNo.Yes);
							sa.assertTrue(false, "Not able to add column form column to display popup");
						}
					}else {
						appLog.error("Not able to apply filter login so cannot verify contact name and add columns in select prospect grid");
						log(LogStatus.FAIL, "Not able to apply filter login so cannot verify contact name and add columns in select prospect grid", YesNo.Yes);
						sa.assertTrue(false, "Not able to apply filter login so cannot verify contact name and add columns in select prospect grid");
					}
				} else {
					appLog.error("Not able to click on add prospects button so cannot add Contacts");
					sa.assertTrue(false, "Not able to click on add prospects button so cannot add Contacts");
					log(LogStatus.ERROR, "Not able to click on add prospects button so cannot add Contacts", YesNo.Yes);
				}
			} else {
				appLog.error("Not able to clicked on created MI: " + Smoke_MI3);
				sa.assertTrue(false, "Not able to clicked on created MI: " + Smoke_MI3);
				log(LogStatus.SKIP, "Not able to clicked on created MI: " + Smoke_MI3, YesNo.Yes);
			}
		} else {
			appLog.error("Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.");
			sa.assertTrue(false,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.");
			log(LogStatus.SKIP,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.",
					YesNo.Yes);
		}
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToDefaultContent(driver);
			
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc006_verifyEmailProspectAndSendEmail(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		String XpathelementTOSearch = "//span[contains(@class,'aw-hpanel-middle')]//span[contains(@class,'aw-grid-row')]//a[text()='"
				+ SmokeC4_FName + " " + SmokeC4_LName + "']/../following-sibling::span[text()='" + SmokeINDINV1
				+ "']/../span[2]/span/span[1]";
		if (market.clickOnTab(environment, mode, TabName.MarketingInitiatives)) {
			if (market.clickOnCreatedMarketInitiatives(environment, mode, Smoke_MI1)) {
				appLog.info("clicked on MI : " + Smoke_MI1);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (market.clickOnShowMoreDropdownOnly(environment, mode, PageName.MarketingInitiatives)) {
						appLog.info("clicked on show 4 more action button in lighting");
						log(LogStatus.ERROR, "clicked on show 4 more action button in lighting", YesNo.No);

					} else {
						appLog.error("Not able to click on show 4 more action button in lighting ");
						sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
					}
				}
				WebElement ele=market.actionDropdownElement( mode, PageName.MarketingInitiatives, ShowMoreActionDropDownList.Email_Prospects, 10);
				if (click(driver, ele, " Email prospects button",
						action.BOOLEAN)) {
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
					}
					ThreadSleep(5000);
					 ele = isDisplayed(driver,
							FindElement(driver, XpathelementTOSearch, "", action.BOOLEAN, 20), "visibility", 20,
							SmokeC4_FName + " " + SmokeC4_LName + " Contact Name input box");
					if (ele == null) {
						log(LogStatus.INFO, SmokeC4_FName + " " + SmokeC4_LName
								+ " is not available in email prospect contact grid", YesNo.No);
					} else {
						log(LogStatus.ERROR, SmokeC4_FName + " " + SmokeC4_LName
								+ " is available in email prospect contact grid", YesNo.Yes);
						sa.assertTrue(false, SmokeC4_FName + " " + SmokeC4_LName
								+ " is available in email prospect contact grid");
					}
					if (market.selectProspectsContactAndVerifyReviewProspectListInEmailProspect(PageName.emailProspects,ContactAndAccountName,
							searchContactInEmailProspectGrid.Yes).isEmpty()) {
						log(LogStatus.INFO,
								SmokeC1_FName + " " + SmokeC1_LName + " Contact is select and in email prospect grid",
								YesNo.No);
						if (click(driver, market.getEmailProspectStep1NextBtn(20), "step 1 next button",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Steps 1 Next button", YesNo.No);
							String expectedResult = "All,Capital Call Notice,Investor Distribution Notice,"+EmailTemplate1_FolderName;
							List<WebElement> lst = allOptionsInDropDrop(driver,
									market.getEmailProspectFolderDropDownList(20), "folder drop down list");
							if (compareMultipleList(driver, expectedResult, lst).isEmpty()) {
								log(LogStatus.INFO, "Folder Drop Down list All options is verified", YesNo.No);
							} else {
								sa.assertTrue(false, "Folder Drop Down list All options is not verified");
								log(LogStatus.ERROR, "Folder Drop Down list All options is not verified", YesNo.Yes);
							}
							if (market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
								log(LogStatus.INFO, "Capital Call Notice email template is selected successfully",
										YesNo.No);
								ele = market.emailProspectPreviewTemplateLink("Capital Call Notice", 30);
								if (ele != null) {
									if (click(driver, ele, "Capital Call Notice preview link",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Capital Call Notice email template link", YesNo.No);
										String parentWindow = null;
										parentWindow = switchOnWindow(driver);
										if (parentWindow != null) {
											String xpath = "//td[text()='Capital Call Notice']";
											ele = isDisplayed(driver,
													FindElement(driver, xpath, "", action.BOOLEAN, 20), "visibility",
													20, "Capital Call Notice template name");
											if (ele != null) {
												log(LogStatus.INFO,
														"Capital Call Notice template is open in preview mode",
														YesNo.No);
											} else {
												sa.assertTrue(false,
														"Capital Call Notice template is open in preview mode");
												log(LogStatus.ERROR,
														"Capital Call Notice template is open in preview mode",
														YesNo.Yes);
											}
											driver.close();
											driver.switchTo().window(parentWindow);
											if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
												switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
											}
										} else {
											sa.assertTrue(false,
													"No new window is open after click on Capital Call Notice template preview link");
											log(LogStatus.ERROR,
													"No new window is open after click on Capital Call Notice template preview link",
													YesNo.Yes);
										}
										if (click(driver, market.getEmailProspectStep2NextBtn(30), "step 2 next button",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on step 2 next button", YesNo.No);
											ele = market.getEmailProspectsSelectedRecipientErrorMsg(PageName.emailProspects,20);
											if (ele != null) {
												String aa = ele.getText().trim();
												if (aa.contains(MarketingInitiativesPageErrorMsg
														.selectRecipientOnStep3ErrorMsg("1"))) {
													log(LogStatus.INFO,
															MarketingInitiativesPageErrorMsg
																	.selectRecipientOnStep3ErrorMsg("1")
																	+ " error message is verified",
															YesNo.No);
												} else {
													sa.assertTrue(false,
															"error message is not verified Expected: "
																	+ MarketingInitiativesPageErrorMsg
																			.selectRecipientOnStep3ErrorMsg("1")
																	+ " \t Actual : " + aa);
													log(LogStatus.ERROR,
															"error message is not verified Expected: "
																	+ MarketingInitiativesPageErrorMsg
																			.selectRecipientOnStep3ErrorMsg("1")
																	+ " \t Actual : " + aa,
															YesNo.Yes);
												}
											} else {
												sa.assertTrue(false,
														MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg(
																"1") + " error message is not visible");
												log(LogStatus.ERROR,
														MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg(
																"1") + " error message is not visible",
														YesNo.Yes);
											}
											List<WebElement> labellist = market
													.getEmailProspectProcessingOptionsLableTextList();
											List<WebElement> checkBoxList = market
													.getEmailProspectProcessingOptionsCheckBoxList();
											String[] expctRsult = { "BCC me on one message", "Use my signature",
													"Store an Activity for Each Message" };
											for (int i = 0; i < expctRsult.length; i++) {
												for (int j = 0; j < labellist.size(); j++) {
													if (labellist.get(j).getText().trim().contains(expctRsult[i])) {
														if (j != 2) {
															if (checkBoxList.get(j).getAttribute("checked") == null) {
																log(LogStatus.INFO,
																		expctRsult[i] + "check box is not selected",
																		YesNo.No);
															} else {
																sa.assertTrue(false,
																		expctRsult[i] + "check box is selected");
																log(LogStatus.ERROR,
																		expctRsult[i] + "check box is selected",
																		YesNo.Yes);
															}
														} else {
															System.err.println("checked:   "
																	+ checkBoxList.get(j).getAttribute("checked"));
															if (checkBoxList.get(j).getAttribute("checked")
																	.contains("true")) {
																log(LogStatus.INFO,
																		expctRsult[i] + "check box is selected",
																		YesNo.No);
															} else {
																sa.assertTrue(false,
																		expctRsult[i] + "check box is not selected");
																log(LogStatus.ERROR,
																		expctRsult[i] + "check box is not selected",
																		YesNo.Yes);
															}
														}
														break;
													}
													if (j == labellist.size() - 1) {
														sa.assertTrue(false, expctRsult[i] + "label is not verified");
														log(LogStatus.ERROR, expctRsult[i] + "label is not verified",
																YesNo.Yes);
													}
												}
											}
											if (click(driver,
													market.getEmailProspectProcessingOptionsCheckBoxList().get(1),
													"Use my signature check box", action.BOOLEAN)) {
												log(LogStatus.INFO, "clicked on Use my signature check box", YesNo.No);
												if (click(driver, market.getEmailProspectSendBtn(TopOrBottom.BOTTOM, 30), "send button",
														action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "clicked on send button", YesNo.No);
													ele = market.getEmailProspectSendEmailCongratulationsErrorMsg(30);
													String errorMsg = MarketingInitiativesPageErrorMsg.congratulationErrorMsg
															+ " " + MarketingInitiativesPageErrorMsg
																	.generatedEmailedRecipientErrorMsg("1", "Email");
													if (ele != null) {
														String aa = ele.getText().trim();
														if (aa.contains(
																MarketingInitiativesPageErrorMsg.congratulationErrorMsg)
																&& aa.contains(MarketingInitiativesPageErrorMsg
																		.generatedEmailedRecipientErrorMsg("1", "Email"))) {
															log(LogStatus.INFO,
																	"Congratulation Error Message is verified",
																	YesNo.No);
														} else {
															sa.assertTrue(false,
																	"Congratulation Error Message is not verified. Expected: "
																			+ errorMsg + "\t Actual: " + aa);
															log(LogStatus.ERROR,
																	"Congratulation Error Message is not verified. Expected: "
																			+ errorMsg + "\t Actual: " + aa,
																	YesNo.Yes);
														}
													} else {
														sa.assertTrue(false,
																"Congratulations Error Message is not visible so cannot verify it. Error Msg: "
																		+ errorMsg);
														log(LogStatus.ERROR,
																"Congratulations Error Message is not visible so cannot verify it. Error Msg: "
																		+ errorMsg,
																YesNo.Yes);
													}
													if (click(driver, market.getEmailProspectFinishBtn(30),
															"finish button", action.BOOLEAN)) {
														log(LogStatus.INFO, "Clicked on finish button", YesNo.No);
													} else {
														sa.assertTrue(false, "Not able to click on finish button");
														log(LogStatus.ERROR, "Not able to click on finish button",
																YesNo.Yes);
													}
												} else {
													sa.assertTrue(false,
															"Not able to click on send button so cannot send email to contact: "
																	+ SmokeC1_FName + " " + SmokeC1_LName);
													log(LogStatus.ERROR,
															"Not able to click on send button so cannot send email to contact: "
																	+ SmokeC1_FName + " " + SmokeC1_LName,
															YesNo.Yes);
												}

											} else {
												sa.assertTrue(false, "Not able to click on Use my signature check box");
												log(LogStatus.ERROR, "Not able to click on Use my signature check box",
														YesNo.Yes);
											}

										} else {
											sa.assertTrue(false,
													"Not able to click on steps 2 nect button so cannot send email to contact : "
															+ SmokeC1_FName + " " + SmokeC1_LName);
											log(LogStatus.ERROR,
													"Not able to click on steps 2 nect button so cannot send email to contact : "
															+ SmokeC1_FName + " " + SmokeC1_LName,
													YesNo.Yes);
										}

									} else {
										sa.assertTrue(false,
												"Not able to click on Capital Call Notice email template link so cannot check preview email template");
										log(LogStatus.ERROR,
												"Not able to click on Capital Call Notice email template link so cannot check preview email template",
												YesNo.Yes);
									}
								} else {
									sa.assertTrue(false,
											"Capital Call Notice email template preview link is not visible so cannot click on it");
									log(LogStatus.ERROR,
											"Capital Call Notice email template preview link is not visible so cannot click on it",
											YesNo.Yes);
								}

							} else {
								sa.assertTrue(false,
										"Not able to select Email Template from Email Prospects so cannot send email to contact "
												+ SmokeC1_FName + " " + SmokeC1_LName);
								log(LogStatus.ERROR,
										"Not able to select Email Template from Email Prospects so cannot send email to contact "
												+ SmokeC1_FName + " " + SmokeC1_LName,
										YesNo.Yes);
							}

						} else {
							sa.assertTrue(false,
									"Not able to click on Steps 1 Next button so cannot select email template and send email to contact "
											+ SmokeC1_FName + " " + SmokeC1_LName);
							log(LogStatus.ERROR,
									"Not able to click on Steps 1 Next button so cannot select email template and send email to contact "
											+ SmokeC1_FName + " " + SmokeC1_LName,
									YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not able to select Contact Name from email prospect: " + SmokeC1_FName
								+ " " + SmokeC1_LName);
						log(LogStatus.ERROR, "Not able to select Contact Name from email prospect: " + SmokeC1_FName
								+ " " + SmokeC1_LName, YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not able to click on email prospects button so cannot add Contacts");
					log(LogStatus.ERROR, "Not able to click on email prospects button so cannot add Contacts",
							YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not able to clicked on created MI: " + Smoke_MI1);
				log(LogStatus.SKIP, "Not able to clicked on created MI: " + Smoke_MI1, YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.");
			log(LogStatus.SKIP,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.",
					YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc007_verifyEmailAndCreateActivity(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		String text = null;
		String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (cp.clickOnTab(environment, mode, TabName.ContactTab)) {
			log(LogStatus.INFO, "Clicked on Contact Tab",YesNo.No);
			if (cp.clickOnCreatedContact( mode, SmokeC1_FName, SmokeC1_LName)) {
				log(LogStatus.INFO, "Clicked on Created Contact : "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.No);	
				
				if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Activity_History, null)) {
					log(LogStatus.INFO, "Clicked on Activity History",YesNo.No);	
					
					if (cp.verifyCreatedActivityHistory(environment, mode, "Capital Call Notice  of Commitment")) {
						log(LogStatus.INFO, "Activity verified: "+"Capital Call Notice  of Commitment",YesNo.No);	
					} else {
						sa.assertTrue(false, "Activity not verified: "+"Capital Call Notice  of Commitment");
						log(LogStatus.SKIP, "Activity not verified: "+"Capital Call Notice  of Commitment",YesNo.Yes);
					}
				
				} else {
					sa.assertTrue(false, "Not on Activity History");
					log(LogStatus.SKIP, "Not on Activity History",YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false, "Contact not Found : "+SmokeC1_FName+" "+SmokeC1_LName);
				log(LogStatus.SKIP, "Contact not Found : "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);	
			}
			
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab",YesNo.Yes);	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		EmailLib email = new EmailLib();
		try {
			text = email.getEMailContent(gmailUserName, gmailPassword, crmUser1EmailID, SmokeC1_EmailID,
					"Capital Call Notice");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			sa.assertTrue(false, "Email Template is not verified from email : " + gmailUserName);
			log(LogStatus.ERROR, "Email Template is not verified from email : " + gmailUserName, YesNo.No);
			e.printStackTrace();
		}
		if (text != null) {
			if (text.contains(MarketingInitiativesPageErrorMsg.companyName)) {
				log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.companyName + " Company Name is verified.",
						YesNo.No);
			} else {
				sa.assertTrue(false, MarketingInitiativesPageErrorMsg.companyName + " Company Name is not verified.");
				log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.companyName + " Company Name is not verified.",
						YesNo.No);
			}
			if (lp.verifyDate(text, null, "email template")) {
				log(LogStatus.INFO, date + " Date is verified", YesNo.No);
			} else {
				sa.assertTrue(false, date + " Date is not verified");
				log(LogStatus.ERROR, date + " Date is not verified", YesNo.No);
			}

			if (text.contains(MarketingInitiativesPageErrorMsg.salutation(SmokeC1_FName))) {
				log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.salutation(SmokeC1_FName) + " is verified",
						YesNo.No);
			} else {
				sa.assertTrue(false, MarketingInitiativesPageErrorMsg.salutation(SmokeC1_FName) + " is not verified");
				log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.salutation(SmokeC1_FName) + " is not verified",
						YesNo.No);
			}
			if (text.contains(MarketingInitiativesPageErrorMsg.ErrorMsg1)) {
				log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.ErrorMsg1 + " is verified", YesNo.No);
			} else {
				sa.assertTrue(false, MarketingInitiativesPageErrorMsg.ErrorMsg1 + " is not verified");
				log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.ErrorMsg1 + " is not verified", YesNo.No);
			}

			if (text.contains(MarketingInitiativesPageErrorMsg.ErrorMsg2)) {
				log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.ErrorMsg2 + " is verified", YesNo.No);
			} else {
				sa.assertTrue(false, MarketingInitiativesPageErrorMsg.ErrorMsg2 + " is not verified");
				log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.ErrorMsg2 + " is not verified", YesNo.No);
			}
			if (text.contains(MarketingInitiativesPageErrorMsg.ErrorMsg3)) {
				log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.ErrorMsg3 + " is verified", YesNo.No);
			} else {
				sa.assertTrue(false, MarketingInitiativesPageErrorMsg.ErrorMsg3 + " is not verified");
				log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.ErrorMsg3 + " is not verified", YesNo.No);
			}
			if (text.contains(MarketingInitiativesPageErrorMsg.Sincerely)) {
				log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.Sincerely + " is verified", YesNo.No);
			} else {
				sa.assertTrue(false, MarketingInitiativesPageErrorMsg.Sincerely + " is not verified");
				log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.Sincerely + " is not verified", YesNo.No);
			}
			if (text.contains(crmUser1FirstName + " " + crmUser1LastName)) {
				log(LogStatus.INFO, crmUser1FirstName + " " + crmUser1LastName + " is verified", YesNo.No);
			} else {
				sa.assertTrue(false, crmUser1FirstName + " " + crmUser1LastName + " is not verified");
				log(LogStatus.ERROR, crmUser1FirstName + " " + crmUser1LastName + " is not verified", YesNo.No);
			}

		} else {
			sa.assertTrue(false, "Capital Call Notice template is not availble on email " + gmailUserName
					+ " so cannot verify send email text");
			log(LogStatus.ERROR, "Capital Call Notice template is not availble on email " + gmailUserName
					+ " so cannot verify send email text", YesNo.No);
		}
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		if (market.clickOnTab(environment, mode, TabName.MarketingInitiatives)) {
			if (market.clickOnCreatedMarketInitiatives(environment, mode, Smoke_MI1)) {
				appLog.info("clicked on MI : " + Smoke_MI2);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (market.clickOnShowMoreDropdownOnly(environment, mode, PageName.MarketingInitiatives)) {
						log(LogStatus.ERROR, "clicked on show 4 more action button in lighting", YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
					}
				}
				ThreadSleep(3000);
				WebElement ele = market.actionDropdownElement(mode, PageName.MarketingInitiatives, ShowMoreActionDropDownList.Email_Prospects, 10);
				if (click(driver, ele, " Email prospects button",action.BOOLEAN)) {
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
					}
					ThreadSleep(3000);
					windowScrollYAxis(driver, 0, 500);
					List<WebElement> lst = market.getemailProspectContentGrid("Last Mass Email", 20);
					if (!lst.isEmpty()) {
						String ActualDate = lst.get(0).getText().trim();
						if (market.verifyDate(ActualDate, null, "Last Mass Email")) {
							log(LogStatus.INFO,
									"Last Mass Email Date is verifed of contact" + SmokeC1_FName + " " + SmokeC1_LName,
									YesNo.No);
						} else {
							sa.assertTrue(false,
									"Last Mass Email Date is not verifed of contact" + SmokeC1_FName + " " + SmokeC1_LName);
							log(LogStatus.ERROR,
									"Last Mass Email Date is not verifed of contact" + SmokeC1_FName + " " + SmokeC1_LName,
									YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,
								"Date Column is not visible in email prospect content grid so cannot verify "
										+ SmokeC1_FName + " " + SmokeC1_LName + "Last Mass Email");
						log(LogStatus.ERROR,
								"Date Column is not visible in email prospect content grid so cannot verify "
										+ SmokeC1_FName + " " + SmokeC1_LName + "Last Mass Email",
								YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not able to click on email prospects button so cannot add Contacts");
					log(LogStatus.ERROR, "Not able to click on email prospects button so cannot add Contacts",
							YesNo.Yes);

				}
			} else {
				sa.assertTrue(false, "Not able to clicked on created MI: " + Smoke_MI1);
				log(LogStatus.SKIP, "Not able to clicked on created MI: " + Smoke_MI1, YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.");
			log(LogStatus.SKIP,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.",
					YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc008_verifyEmailProspectPageAndSendMail(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		WebElement ele = null;
		String emailFolderName = ExcelUtils.readData(smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,
				"EmailTemplate1", excelLabel.Email_Template_Folder_Label);
		String emailTemplateName = ExcelUtils.readData(smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,
				"EmailTemplate1", excelLabel.Email_Template_Name);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC2_FName + " " + SmokeC2_LName, SmokeINS1);
		if (market.clickOnTab(environment, mode, TabName.MarketingInitiatives)) {
			if (market.clickOnCreatedMarketInitiatives(environment, mode, Smoke_MI2)) {
				appLog.info("clicked on MI : " + Smoke_MI2);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (lp.clickOnShowMoreDropdownOnly(environment, mode, PageName.MarketingInitiatives)) {
						appLog.info("clicked on show 4 more action button in lighting");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);

					} else {
						appLog.error("Not able to click on show 4 more action button in lighting ");
						sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
					}
				}
				ThreadSleep(3000);
				
				// Azhar Start
				ele = market.actionDropdownElement( mode, PageName.MarketingInitiatives, ShowMoreActionDropDownList.Email_Prospects, 10);
				if (click(driver, ele, " Email prospects button",action.BOOLEAN)) {
					ThreadSleep(5000);
					
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					
							switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
					}
					
					
					if (click(driver, market.getEmailProspectStep1CancelBtn(10), "Cancel Button", action.BOOLEAN)) {
						log(LogStatus.INFO, "clicked on Cancel Button",YesNo.No);
					} else {
						sa.assertTrue(false,"Not able to click on Cancel Button");
						log(LogStatus.ERROR,"Not able to click on Cancel Button",YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not able to click on email prospects button so cannot add Contacts");
					log(LogStatus.ERROR, "Not able to click on email prospects button so cannot add Contacts",
							YesNo.Yes);
				}
				
				// Azhar End
				switchToDefaultContent(driver);
				
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (lp.clickOnShowMoreDropdownOnly(environment, mode, PageName.MarketingInitiatives)) {
						appLog.info("clicked on show 4 more action button in lighting");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);

					} else {
						appLog.error("Not able to click on show 4 more action button in lighting ");
						sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
					}
				}
				ThreadSleep(3000);
				ele = market.actionDropdownElement(environment, PageName.MarketingInitiatives, ShowMoreActionDropDownList.Email_Prospects, 10);
				
				if (click(driver, ele, " Email prospects button",action.BOOLEAN)) {
					ThreadSleep(5000);
					if (market.EmailProspects(environment, mode,PageName.emailProspects, "Contact:Last Name", "equals", SmokeC2_LName,
							ContactAndAccountName, searchContactInEmailProspectGrid.No, emailFolderName,
							emailTemplateName, null, true)) {
						log(LogStatus.INFO, "send email prospect to contact" + SmokeC2_FName + " " + SmokeC2_LName,
								YesNo.No);
					} else {
						sa.assertTrue(false,
								"Not able to send email prospect to contact" + SmokeC2_FName + " " + SmokeC2_LName);
						log(LogStatus.ERROR,
								"Not able to send email prospect to contact" + SmokeC2_FName + " " + SmokeC2_LName,
								YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not able to click on email prospects button so cannot add Contacts");
					log(LogStatus.ERROR, "Not able to click on email prospects button so cannot add Contacts",
							YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not able to clicked on created MI: " + Smoke_MI2);
				log(LogStatus.SKIP, "Not able to clicked on created MI: " + Smoke_MI2, YesNo.Yes);
			}
		} else {
			log(LogStatus.SKIP,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.",
					YesNo.Yes);
			sa.assertTrue(false,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc009_1_verifyEmailAndCreateActivityforContact2(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		String text = null;
		String emailFolderSubject = ExcelUtils.readData(smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,
				"EmailTemplate1", excelLabel.Subject);
		String emailBody = ExcelUtils.readData(smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,
				"EmailTemplate1", excelLabel.Email_Body);
		String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (contact.clickOnTab(environment, mode, TabName.ContactTab)) {
			log(LogStatus.INFO, "Clicked on Contact Tab",YesNo.No);
			if (contact.clickOnCreatedContact(environment, SmokeC2_FName, SmokeC2_LName)) {
				log(LogStatus.INFO, "Clicked on Created Contact : "+SmokeC2_FName+" "+SmokeC2_LName,YesNo.No);	
				
				if (contact.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Activity_History, null)) {
					log(LogStatus.INFO, "Clicked on Activity History",YesNo.No);	
					
					if (contact.verifyCreatedActivityHistory(environment, mode, EmailTemplate1_Subject)) {
						log(LogStatus.INFO, "Activity verified: "+EmailTemplate1_Subject,YesNo.No);	
					} else {
						sa.assertTrue(false, "Activity not verified: "+EmailTemplate1_Subject);
						log(LogStatus.SKIP, "Activity not verified: "+EmailTemplate1_Subject,YesNo.Yes);
					}
				
				} else {
					sa.assertTrue(false, "Not on Activity History");
					log(LogStatus.SKIP, "Not on Activity History",YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false, "Contact not Found : "+SmokeC2_FName+" "+SmokeC2_LName);
				log(LogStatus.SKIP, "Contact not Found : "+SmokeC2_FName+" "+SmokeC2_LName,YesNo.Yes);	
			}
			
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab",YesNo.Yes);	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		EmailLib email = new EmailLib();
		try {
			text = email.getEMailContent(gmailUserName, gmailPassword, crmUser1EmailID, SmokeC2_EmailID,
					emailFolderSubject);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			sa.assertTrue(false, "Email Template is not verified from email : " + gmailUserName);
			log(LogStatus.ERROR, "Email Template is not verified from email : " + gmailUserName, YesNo.No);
			e.printStackTrace();
		}
		if (text != null) {
			if (text.contains(emailBody)) {
				log(LogStatus.INFO, emailBody + " body text is verified.", YesNo.No);
			} else {
				sa.assertTrue(false, emailBody + " body text is verified.");
				log(LogStatus.ERROR, emailBody + " body text is verified.", YesNo.No);
			}
		} else {
			sa.assertTrue(false, emailFolderSubject + " template is not availble on email " + gmailUserName
					+ " so cannot verify send email text");
			log(LogStatus.ERROR, emailFolderSubject + " template is not availble on email " + gmailUserName
					+ " so cannot verify send email text", YesNo.No);
		}
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC2_FName + " " + SmokeC2_LName, SmokeINS2);
		if (market.clickOnTab(environment, mode, TabName.MarketingInitiatives)) {
			if (market.clickOnCreatedMarketInitiatives(environment, mode, Smoke_MI2)) {
				appLog.info("clicked on MI : " + Smoke_MI2);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (market.clickOnShowMoreDropdownOnly(environment, mode, PageName.MarketingInitiatives)) {
						appLog.info("clicked on show 4 more action button in lighting");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);

					} else {
						appLog.error("Not able to click on show 4 more action button in lighting ");
						sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
					}
				}
				WebElement ele = market.actionDropdownElement(environment, PageName.MarketingInitiatives, ShowMoreActionDropDownList.Email_Prospects, 10);
				if (click(driver, ele, " Email prospects button",action.BOOLEAN)) {
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
					}
					ThreadSleep(3000);
					windowScrollYAxis(driver, 0, 500);
					List<WebElement> lst = market.getemailProspectContentGrid("Last Mass Email", 20);
					if (!lst.isEmpty()) {
						String ActualDate = lst.get(1).getText().trim();
						if (market.verifyDate(ActualDate,null, "Last Mass Email")) {
							log(LogStatus.INFO,
									"Last Mass Email Date is verifed of contact" + SmokeC2_FName + " " + SmokeC2_LName,
									YesNo.No);
						} else {
							sa.assertTrue(false, "Last Mass Email Date is not verifed of contact" + SmokeC2_FName + " "
									+ SmokeC2_LName);
							log(LogStatus.ERROR, "Last Mass Email Date is not verifed of contact" + SmokeC2_FName + " "
									+ SmokeC2_LName, YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,
								"Date Column is not visible in email prospect content grid so cannot verify "
										+ SmokeC2_FName + " " + SmokeC2_LName + "Last Mass Email");
						log(LogStatus.ERROR,
								"Date Column is not visible in email prospect content grid so cannot verify "
										+ SmokeC2_FName + " " + SmokeC2_LName + "Last Mass Email",
								YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not able to click on email prospects button so cannot add Contacts");
					log(LogStatus.ERROR, "Not able to click on email prospects button so cannot add Contacts",
							YesNo.Yes);

				}
			} else {
				sa.assertTrue(false, "Not able to clicked on created MI: " + Smoke_MI2);
				log(LogStatus.SKIP, "Not able to clicked on created MI: " + Smoke_MI2, YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.");
			log(LogStatus.SKIP,
					"Not able to clicked on MI Tab so cannot verify Error Message in add prospects reports tab.",
					YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc009_2_addFilterLogicAndAddColumnFromWrenchIconInEMailProspect(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		ContactAndAccountName.put(SmokeC2_FName + " " + SmokeC2_LName, SmokeINS1);
		WebElement ele=null;
		String msg = null;
		String parenTiD;
		String Xpath = null;
		if (market.clickOnTab(environment, mode, TabName.MarketingInitiatives)) {
			if (market.clickOnCreatedMarketInitiatives(environment, mode, Smoke_MI3)) {
				appLog.info("clicked on MI : " + Smoke_MI3);
				
				
				// Azhar Start
				
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (market.clickOnShowMoreDropdownOnly(environment, mode, PageName.MarketingInitiatives)) {
						appLog.info("clicked on show 4 more action button in lighting");
					} else {
						appLog.error("Not able to click on show 4 more action button in lighting ");
						sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
					}
					
				}
				ele = market.actionDropdownElement(environment, PageName.MarketingInitiatives, ShowMoreActionDropDownList.Email_Prospects, 10);
				
					if (click(driver, ele, " email prospects button",action.BOOLEAN)) {
						ThreadSleep(2000);
						log(LogStatus.ERROR, "clicked on Email Prospect Btn", YesNo.No);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
							
						}
						
						if(click(driver, market.getEmailProspectApplyBtn(30), "search button", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on Apply Button", YesNo.Yes);
							ThreadSleep(2000);
							
							ele = market.getPleaseSelectApplyCriteriaPopUpMsg(20);
							if (ele != null) {
								 msg = ele.getText().trim();
								if (msg.contains(MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage)) {
									appLog.info(MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage+ " error messgae is verified.");
								} else {
									sa.assertTrue(false, MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage+ " error messgae is not verified. Actual :" + msg);
									log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage+ " error messgae is not verified. Actual :" + msg, YesNo.Yes);
								}
								
								if (click(driver, market.getPleaseSelectApplyCriteriaPopUpOkBtn(20), "ok button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("Select a criteria error message ok button");
									ThreadSleep(2000);
									
									 ele = market.getPleaseSelectASearchCriteriaMsg(5);
										if (ele == null) {
											log(LogStatus.INFO,"Select a search Criteria error is not visible after Click on OK Button",YesNo.No);	
										}else{
											sa.assertTrue(false,"Select a search Criteria error is visible after Click on OK Button");
											log(LogStatus.ERROR,"Select a search Criteria error is visible after Click on OK Button",YesNo.Yes);
										
										}
								} else {
									sa.assertTrue(false,"Select a search Criteria error message ok button so cannot close error message pop up");
									log(LogStatus.ERROR,"Select a search Criteria error message ok button so cannot close error message pop up",YesNo.Yes);
								}
								
								} else {
								sa.assertTrue(false,"Select a search Criteria error message is not visible so cannot verify error message");
								log(LogStatus.ERROR,"Select a search Criteria error message is not visible so cannot verify error message",YesNo.Yes);
							}
							
						}else {
							sa.assertTrue(false, "Not able to click on Apply Button");
							log(LogStatus.ERROR, "Not able to click on Apply Button", YesNo.Yes);
						}
						
						
						
						
						if (click(driver, market.getEmailProspectStep1CancelBtn(10), "Cancel Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on top Cancel Button",YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(2000);
							
							if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								if (market.clickOnShowMoreDropdownOnly(environment, mode, PageName.MarketingInitiatives)) {
									appLog.info("clicked on show 4 more action button in lighting");
									ThreadSleep(1000);
									
									ele = market.actionDropdownElement(environment, PageName.MarketingInitiatives, ShowMoreActionDropDownList.Email_Prospects, 10);
									if (click(driver, ele, " email prospects button",action.BOOLEAN)) {
										ThreadSleep(2000);
										log(LogStatus.ERROR, "clicked on Email Prospect Btn", YesNo.No);
										if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
											switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
											
										}
										
										if (click(driver, market.getEmailProspectStep1CancelBottomBtn(10), "Cancel Button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on bottom Cancel Button",YesNo.No);
											switchToDefaultContent(driver);
											}else{
												sa.assertTrue(false,"Not able to click on bottom Cancel Button");
												log(LogStatus.ERROR,"Not able to click on bottom Cancel Button",YesNo.Yes);	
											}
									}else{
										sa.assertTrue(false, "Not able to click on Email Prospect Btn");
										log(LogStatus.ERROR, "Not able to click on Email Prospect Btn", YesNo.Yes);
									}
								} else {
									appLog.error("Not able to click on show 4 more action button in lighting ");
									sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
									log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
								}
								
							}
							
						} else {
							sa.assertTrue(false,"Not able to click on top Cancel Button");
							log(LogStatus.ERROR,"Not able to click on top Cancel Button",YesNo.Yes);
						}
						
						
						
					}else{
						sa.assertTrue(false, "Not able to click on Email Prospect Btn");
						log(LogStatus.ERROR, "Not able to click on Email Prospect Btn", YesNo.Yes);
					}
				
				
				
				// Azhar End
				
					switchToDefaultContent(driver);
				
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (market.clickOnShowMoreDropdownOnly(environment, mode, PageName.MarketingInitiatives)) {
						appLog.info("clicked on show 4 more action button in lighting");
					} else {
						appLog.error("Not able to click on show 4 more action button in lighting ");
						sa.assertTrue(false, "Not able to click on show 4 more action button in lighting ");
						log(LogStatus.ERROR, "Not able to click on show 4 more action button in lighting ", YesNo.Yes);
					}
				}
				
				ele = market.actionDropdownElement(environment,  PageName.MarketingInitiatives, ShowMoreActionDropDownList.Email_Prospects, 10);
				if (click(driver, ele, " email prospects button",action.BOOLEAN)) {
					ThreadSleep(2000);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
						
					}
					if(click(driver, market.getAddRowLink(AddProspectsTab.AccountAndContacts), "account and contact tab", action.BOOLEAN)) {
						log(LogStatus.FAIL,"Clicked on Add row link",YesNo.No);
						ThreadSleep(2000);
						if(click(driver, market.getRowRemoveIcon().get(0), "remove icon", action.BOOLEAN)) {
							log(LogStatus.FAIL,"clicked on remove row link",YesNo.No);
						}else {
							appLog.error("Not able to click on remove row link so cannot check remove functionality");
							log(LogStatus.FAIL,"Not able to click on remove row link so cannot check remove functionality",YesNo.Yes);
							sa.assertTrue(false,"Not able to click on remove row link so cannot check remove functionality");
						}
						ThreadSleep(2000);
					}else {
						appLog.error("Not able to click on Add row link so cannot check remove functionality");
						log(LogStatus.FAIL,"Not able to click on Add row link so cannot check remove functionality",YesNo.Yes);
						sa.assertTrue(false,"Not able to click on Add row link so cannot check remove functionality");
					}
					
					// Azhar Start
					if (click(driver, market.getAddFilterLogicLinkOnEmailProspect(10), "Add Filter Logic Link", action.BOOLEAN)) {
						log(LogStatus.INFO, "clicked on Add Filter Logic Link", YesNo.No);
						ThreadSleep(1000);
						
						Xpath="pageid:frm:textfilt";
					
						ele=isDisplayed(driver,FindElement(driver, "//input[@id='"+Xpath+"']", "Add filter logic text box", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
						if(ele!=null) {
							log(LogStatus.INFO,"find add filter logic text box",YesNo.No);	
						}else {
							log(LogStatus.FAIL,"Not able find add filter logic text box",YesNo.Yes);
							sa.assertTrue(false, "Not able find add filter logic text box  ");
						}
						
						
						if (click(driver, market.getInfoLink(10), "Info Link", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"click on Info Link",YesNo.No);
							ThreadSleep(2000);
							parenTiD = switchOnWindow(driver);
							
							if (parenTiD!=null) {
								log(LogStatus.INFO,"New Window is open",YesNo.No);
								Xpath="//h1//*[text()='Add Filter Logic']";
								ele=isDisplayed(driver,FindElement(driver, Xpath, "Add filter logic Window", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
								if(ele!=null) {
									log(LogStatus.INFO,"Add Filter Logic Window is open",YesNo.No);	
								}else {
									log(LogStatus.FAIL,"Add Filter Logic Window is not open",YesNo.Yes);
									sa.assertTrue(false, "Add Filter Logic Window is not open");
								}
								driver.close();
								driver.switchTo().window(parenTiD);
							} else {
								log(LogStatus.FAIL,"No New Window is open",YesNo.Yes);
								sa.assertTrue(false, "No New Window is open");
							}
						} else {
							log(LogStatus.FAIL,"Not able to click on Info Link",YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Info Link");
						}
						switchToDefaultContent(driver);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 20, market.getMarketInitiativeFrame_Lightning(20));
							}
						appLog.info("After All");
						for (int j = 0; j < 2; j++) {
							Xpath="AddRow2";
							ele=isDisplayed(driver,FindElement(driver, "//a[@id='"+Xpath+"']", "Add row button", action.BOOLEAN,10), "Visibility", 10, "Add row button");
							
							if(click(driver, ele, "add row button", action.BOOLEAN)) {
								log(LogStatus.INFO,"clicked on add row link : "+j,YesNo.No);
							}else {
								log(LogStatus.FAIL,"Not Able to click on add row link : "+j,YesNo.Yes);
								sa.assertTrue(false, "Not Able to click on add row link : "+j);	
							}
							ThreadSleep(2000);
						}
							
						if (click(driver, market.getEmailProspectClearBtn(10), "Clear Btn", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"click on Clear Btn",YesNo.No);	
						} else {
							log(LogStatus.FAIL,"Not able to click on Clear Btn",YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Clear Btn");
						}
						
						
					
						
						
					} else {
						sa.assertTrue(false, "Not able to click on Add Filter Logic Link");
						log(LogStatus.ERROR, "Not able to click on Add Filter Logic Link", YesNo.Yes);
					}
					// Azhar End
					
					if(market.SearchforEmailProspects(environment, mode, PageName.emailProspects,"Account:Legal Name,Contact:Email,Contact:Legal Name","equals,equals,equals",SmokeINS1+","+SmokeC1_EmailID+","+SmokeINS1,"(1 AND 2) OR 3")) {
						appLog.info("Filter logic applied successfully ");
						ThreadSleep(5000);
						if(market.selectProspectsContactAndVerifyReviewProspectListInEmailProspect(PageName.emailProspects,ContactAndAccountName, searchContactInEmailProspectGrid.No).isEmpty()) {
							log(LogStatus.PASS, "Search Contacts is visible in Select Propects Grid ", YesNo.No);
						}else {
							appLog.error("Search Contacts is not visible in Select Propects Grid ");
							log(LogStatus.FAIL,"Search Contacts is not visible in Select Propects Grid ",YesNo.Yes);
							sa.assertTrue(false, "Search Contacts is not visible in Select Propects Grid ");
						}
						
						// Azhar Start
						
						if(click(driver, market.getSelectProspectsWrenchIcon(PageName.emailProspects,mode,60), "wrench icon", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(1000);
							
							if(selectVisibleTextFromDropDown(driver, market.getColumnToDisplayViewDropDownList(PageName.emailProspects,30), "view drop down list", ViewFieldsName.Contact_Fields)) {
								log(LogStatus.INFO,"Select value from view drop down list : "+ViewFieldsName.Contact_Fields,YesNo.Yes);
								
							}else {
								log(LogStatus.FAIL,"Not able to select value from view drop down list : "+ViewFieldsName.Contact_Fields,YesNo.Yes);
								sa.assertTrue(false, "Not able to select value from view drop down list : "+ViewFieldsName.Contact_Fields);
							}
							ThreadSleep(1000);
							
							if(selectVisibleTextFromDropDown(driver, market.getColumnToDisplayViewDropDownList(PageName.emailProspects,30), "view drop down list", ViewFieldsName.Marketing_Prospect_Fields)) {
								log(LogStatus.INFO,"Select value from view drop down list : "+ViewFieldsName.Marketing_Prospect_Fields,YesNo.No);
								
							}else {
								log(LogStatus.FAIL,"Not able to select value from view drop down list : "+ViewFieldsName.Marketing_Prospect_Fields,YesNo.Yes);
								sa.assertTrue(false, "Not able to select value from view drop down list : "+ViewFieldsName.Marketing_Prospect_Fields);
							}
							ThreadSleep(1000);
							
							if (click(driver, market.getWrenchIconCancelBtn(10), "Cancel Btn", action.BOOLEAN)) {
								log(LogStatus.INFO,"click on Wrench Icon Cancel Btn",YesNo.No);
							} else {
								log(LogStatus.FAIL,"Not able to click on Wrench Icon Cancel Btn",YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Wrench Icon Cancel Btn");
							}
							ThreadSleep(1000);
						}else{
							log(LogStatus.FAIL,"Not able to click on Wrench Icon",YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Wrench Icon");
						}
						
						// Azhar End
						
						String [] ss = {"Birthdate","Marketing Initiative"};
						if(market.addAndRemoveCloumnInSelectProspectGrid(mode,PageName.emailProspects,ss, null, null,true).isEmpty()) {
							log(LogStatus.PASS, "column added form column to display popup", YesNo.No);
							ThreadSleep(2000);
							if(compareMultipleList(driver, "Birthdate"+","+"Marketing Initiative", market.getSelectProspectsHeaderTextList(PageName.emailProspects)).isEmpty()) {
								log(LogStatus.PASS, "Selected Prospects Header Text is verified ", YesNo.No);
							}else {
								appLog.error("Selected Prospects Header Text is not verified");
								log(LogStatus.FAIL,"Selected Prospects Header Text is not verified",YesNo.Yes);
								sa.assertTrue(false, "Selected Prospects Header Text is not verified");
							}
							ThreadSleep(2000);
							if(market.columnToDisplayRevertToDefaultsSettings(PageName.emailProspects,mode)) {
								log(LogStatus.PASS, "column to display settings is revert to default successfully", YesNo.No);
							}else {
								appLog.error("column to display settings is not revert to default");
								log(LogStatus.FAIL,"column to display settings is not revert to default",YesNo.Yes);
								sa.assertTrue(false,"column to display settings is not revert to default");
							}
							if(!compareMultipleList(driver, "Birthdate"+","+"Marketing Initiative", market.getSelectProspectsHeaderTextList(PageName.emailProspects)).isEmpty()) {
								log(LogStatus.PASS, "Select Prospects Header Text is removed", YesNo.No);
							}else {
								appLog.error("Select Prospects Header Text is not removed");
								log(LogStatus.FAIL,"Select Prospects Header Text is not removed",YesNo.Yes);
								sa.assertTrue(false, "Select Prospects Header Text is not removed");
							}
						}else {
							appLog.error("Not able to add column form column to display popup");
							log(LogStatus.FAIL,"Not able to add column form column to display popup",YesNo.Yes);
							sa.assertTrue(false, "Not able to add column form column to display popup");
						}
					}else {
						appLog.error("Not able to apply filter login so cannot verify contact name and add columns in select prospect grid");
						log(LogStatus.FAIL, "Not able to apply filter login so cannot verify contact name and add columns in select prospect grid", YesNo.Yes);
						sa.assertTrue(false, "Not able to apply filter login so cannot verify contact name and add columns in select prospect grid");
					}
				} else {
					appLog.error("Not able to click on add prospects button so cannot add Contacts");
					sa.assertTrue(false, "Not able to click on add prospects button so cannot add Contacts");
					log(LogStatus.ERROR, "Not able to click on add prospects button so cannot add Contacts", YesNo.Yes);
				}
			} else {
				appLog.error("Not able to clicked on created MI: " + Smoke_MI3);
				sa.assertTrue(false, "Not able to clicked on created MI: " + Smoke_MI3);
				log(LogStatus.SKIP, "Not able to clicked on created MI: " + Smoke_MI3, YesNo.Yes);
			}
		} else {
			appLog.error("Not able to clicked on MI Tab so cannot verify Email prospects filter");
			sa.assertTrue(false,
					"Not able to clicked on MI Tab so cannot verify Email prospects filter");
			log(LogStatus.SKIP,
					"Not able to clicked on MI Tab so cannot verify Email prospects filter",
					YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc010_verifyCreateFundRaisingButton(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		for(int i=0; i<2; i++) {
			if (market.clickOnTab(environment, mode, TabName.FundsTab)) {
				if(i==0) {
					if (fund.clickOnCreatedFund(environment, mode, Smoke_Fund1)) {
						appLog.info("clicked on Fund : " + Smoke_Fund1);
						if (mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
							if (fund.ClickonRelatedTab_Lighting(environment, RecordType.Fund, RelatedTab.Fundraising.toString())) {
								log(LogStatus.INFO, "Sucessfully clicked on related tab", YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not able to click on related tab", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on related tab so cannot click on it");
							}
						}
						if (fund.getCreateFundRaisingBtn(environment, mode, PageName.FundsPage, 20) != null) {
							log(LogStatus.INFO, "Create Fundraising button is visible on fund page", YesNo.No);
						} else {
							log(LogStatus.ERROR, "Create Fundraising button is not visible on fund page", YesNo.Yes);
							sa.assertTrue(false, "Create Fundraising button is not visible on fund page");
						}
					} else {
						sa.assertTrue(false, "Not able to clicked on created Fund: " + Smoke_Fund1);
						log(LogStatus.SKIP, "Not able to clicked on created Fund: " + Smoke_Fund1, YesNo.Yes);
					}
				}
				if(i==1) {
					if (fund.clickOnCreatedFund(environment, mode, Smoke_Fund2)) {
						appLog.info("clicked on Fund : " + Smoke_Fund2);
						if (mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
							if (fund.ClickonRelatedTab_Lighting(environment, RecordType.Fund, RelatedTab.Fundraising.toString())) {
								log(LogStatus.INFO, "Sucessfully clicked on related tab", YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not able to click on related tab", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on related tab so cannot click on it");
							}
						}
						if (click(driver,fund.getCreateFundRaisingBtn(environment, mode, PageName.FundsPage, 20),"create fundraising button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Fundraising button", YesNo.No);
							if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
							}
							if(home.selectFundNameOrCompanyNameOnCreateFundraisings(environment,mode,PopUpName.selectFundPopUp, Smoke_Fund2, SmokeCOM1)) {
								log(LogStatus.INFO, "select fund Name and company name", YesNo.No);
								if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
								}
								if(click(driver, home.getSelectFundNamePopUpCancelBtn(10), "cancel button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on cancel button", YesNo.No);
								}else {
									log(LogStatus.ERROR, "Not able to click on cancel button", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on cancel button");
								}
							}else {
								log(LogStatus.ERROR, "Not able to select fund Name and company name", YesNo.Yes);
								sa.assertTrue(false, "Not able to select fund Name and company name");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on  Fundraising button", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on  Fundraising button");
						}
					} else {
						sa.assertTrue(false, "Not able to clicked on created Fund: " + Smoke_Fund2);
						log(LogStatus.SKIP, "Not able to clicked on created Fund: " + Smoke_Fund2, YesNo.Yes);
					}
				}

			} else {
				sa.assertTrue(false, "Not able to clicked on Fund Tab so cannot verify fundraising button.");
				log(LogStatus.SKIP, "Not able to clicked on Fund Tab so cannot verify fundraising button.", YesNo.Yes);
			}
		}
		switchToDefaultContent(driver);
		// PE 4.7 Changes Company page fund raising button remove
		if (market.clickOnTab(environment, mode, TabName.HomeTab)) {
			log(LogStatus.INFO, "Clickrd on Home Tab", YesNo.No);

			String filesName=BulkActions_DefaultValues.Bulk_Email.toString()+","+
					BulkActions_DefaultValues.Bulk_Fundraising.toString()+","+
					BulkActions_DefaultValues.Bulk_Commitments.toString();

			if (home.clickOnNavatarEdgeLinkHomePage(navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				List<String> abc = compareMultipleList(driver, filesName, home.getNavigationList(10));
				if (abc.isEmpty()) {
					log(LogStatus.INFO, "items verified "+filesName+" on "+navigationMenuName, YesNo.No);
				} else {
					log(LogStatus.ERROR, "items not verified "+filesName+" on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,"items not verified "+filesName+" on "+navigationMenuName);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName);
			}
			switchToDefaultContent(driver);
		} else {
			sa.assertTrue(false, "Not able to clicked on Home Tab so cannot verify navatar quick links.");
			log(LogStatus.SKIP, "Not able to clicked on Home Tab so cannot verify navatar quick links.", YesNo.Yes);
		}

		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc011_verifyCreateFundraisingsFromHomePage(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		List<String> contactNamelist= new ArrayList<String>();
		contactNamelist.add(SmokeC1_FName+" "+SmokeC1_LName);
		contactNamelist.add(SmokeC2_FName+" "+SmokeC2_LName);
		contactNamelist.add(SmokeC3_FName+" "+SmokeC3_LName);
		contactNamelist.add(SmokeC4_FName+" "+SmokeC4_LName);
		List<String> accountlist= new ArrayList<String>();
		accountlist.add(SmokeINS1);
		accountlist.add(SmokeINS1);
		accountlist.add(SmokeINDINV1);
		accountlist.add(SmokeINDINV1);
		List<String> Instituon1ContactName= new ArrayList<String>();
		Instituon1ContactName.add(SmokeC2_FName+" "+SmokeC2_LName);
		Instituon1ContactName.add(SmokeC1_FName+" "+SmokeC1_LName);
		List<String> Institution1List= new ArrayList<String>();
		Institution1List.add(SmokeINS1);
		Institution1List.add(SmokeINS1);
		List<String> actionsList= new ArrayList<String>();
		actionsList.add(SmokeC2_FName+" "+SmokeC2_LName+"<break>"+fundraisingContactActions.Remove.toString());
		actionsList.add(SmokeC1_FName+" "+SmokeC1_LName+"<break>"+fundraisingContactActions.PrimaryContact.toString());
		actionsList.add(SmokeC1_FName+" "+SmokeC1_LName+"<break>"+fundraisingContactActions.Role_DecisionMaker.toString());
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Fundraising.toString();
		boolean flag = false;
		for(int j=0; j<=2; j++) {
			switchToDefaultContent(driver);
			if(home.ClickOnItemOnNavatarEdge(navigationMenuName, bulkActionNavigationLink, action.BOOLEAN, 20)) {
				log(LogStatus.INFO, "clicked on create fundraising link : "+j, YesNo.No);
				if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
					
				}
				if(j==0) {
					if(click(driver,home.getSelectFundPopUpCrossIcon(10),"cross icon", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on cross icon", YesNo.No);
					}else {
						log(LogStatus.ERROR, "Not able to click on cross icon so cannot check cross icon  ", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on cross icon so cannot check cross icon  ");
					}
				}
				if(j==1) {
					if(home.selectFundNameOrCompanyNameOnCreateFundraisings(environment,mode, PopUpName.selectFundPopUp, Smoke_Fund1, null)) {
						log(LogStatus.INFO, "Select Fund : "+Smoke_Fund1, YesNo.No);
						if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
						}
						if(click(driver, home.getSelectFundPopUpCancelBtn(10), "cancel button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on cancel button in select fund popup", YesNo.No);
						}else {
							log(LogStatus.ERROR, "Not able to click on cancel button in select fund popup", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on cancel button in select fund popup");
						}
						
						
					}else {
						log(LogStatus.ERROR, "Not able to click on select fund Name from lookup popup", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on select fund Name from lookup popup");
					}
				}
				
				if(j==2) {
					if(home.selectFundNameOrCompanyNameOnCreateFundraisings(environment,mode, PopUpName.selectFundPopUp, Smoke_Fund1, null)) {
						log(LogStatus.INFO, "Select Fund : "+Smoke_Fund1, YesNo.No);
						if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
						}
						if(click(driver, home.getSelectFundNamePopUpContinueBtn(), "continue button", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(3000);
							if(click(driver, home.getSearchBasedOnAccountsAndContactsTab(30), "Search Based On Accounts And Contacts Tab", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "click on Search Based On Accounts And Contacts Tab", YesNo.No);
								ThreadSleep(3000);
								if(home.applyFilterOnSearchBasedOnAccountsandContacts( FundraisingContactPageTab.SearchBasedOnAccountsAndContacts, SearchBasedOnExistingFundsOptions.AllContacts, environment,mode, null, "Account:Legal Name", "not equal to", "", null)) {
									log(LogStatus.INFO, "apply filter logic", YesNo.No);
									
									if(home.checkContactOrAccountOrFundraisingPage(environment, mode, SmokeC1_FName+" "+SmokeC1_LName, PageName.CreateFundraisingPage, columnName.contactName,market.getEmailProspectSelectProspectsGridScrollBox(10))) {
										log(LogStatus.INFO, "contact Page is verified ",YesNo.No);
										if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
											switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
										}
										
									}else {
										log(LogStatus.ERROR, "Contact page is not verified", YesNo.Yes);
										sa.assertTrue(false, "Contact page is not verified");
									}
									click(driver, home.getSearchBasedOnAccountsAndContactsSearchBtn(30), "search button", action.SCROLLANDBOOLEAN);
									if(home.checkContactOrAccountOrFundraisingPage(environment, mode, SmokeINS1, PageName.CreateFundraisingPage, columnName.AccountName,market.getEmailProspectSelectProspectsGridScrollBox(10))) {
										log(LogStatus.INFO, "Account Page is verified ",YesNo.No);
										if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
											switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
										}
									}else {
										log(LogStatus.ERROR, "Account page is not verified", YesNo.Yes);
										sa.assertTrue(false, "Account page is not verified");
									}
									click(driver, home.getSearchBasedOnAccountsAndContactsSearchBtn(30), "search button", action.SCROLLANDBOOLEAN);
									if(home.selectInvestorsContactFromCreateFundRaising(contactNamelist,accountlist).isEmpty()) {
										log(LogStatus.INFO, "contact name is selected successfully",YesNo.No);
										if(click(driver, home.getAddToFundraisingListBtn(30), "Add To Fundraising List Button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "click on Add To Fundraising List", YesNo.No);
											if(home.verifyContactNameInReviewInvestorList(contactNamelist, accountlist).isEmpty()) {
												log(LogStatus.INFO, "Contact Name is verified in review investor list", YesNo.No);
												ThreadSleep(2000);
												if(home.clickOnReviewInvestorListFundraisingContactLink(accountlist.get(0))) {
													ThreadSleep(2000);
													if(home.verifyFundRaisingContactAndSetRolePrimaryContact(Instituon1ContactName, Institution1List, actionsList).isEmpty()) {
														log(LogStatus.INFO, "Fundraising contact is verified and select role,primary contact successfully", YesNo.No);
														if(click(driver, home.getFundraisingContactPopUpApplyBtn(20), "fundraising contact apply button", action.SCROLLANDBOOLEAN)) {
															log(LogStatus.INFO, "clicked on apply button", YesNo.No);
															List<String> fieldAndValue=new ArrayList<String>();
															fieldAndValue.add("Fundraising:Investment Likely Amount (mn)<break>100000000");
															if(home.selectDefaultFundraisingValue(fieldAndValue)) {
																log(LogStatus.INFO, "select  Default Fundraising Values", YesNo.No);
																if(click(driver, home.getCreateFundraisingBtn(PageName.CreateFundraisingPage, 30), "create fundraising button", action.SCROLLANDBOOLEAN)) {
																	log(LogStatus.INFO, "clicked on create fundraising button", YesNo.No);
																	if(click(driver,home.getCreateFundraisingConfirmationOkBtn(30), "ok button", action.SCROLLANDBOOLEAN)) {
																		log(LogStatus.INFO, "clicked on OK button", YesNo.No);
																		flag=true;
																	}else {
																		log(LogStatus.ERROR, "Not able to click on OK button so cannot get contact id and verify contact details on  created fundraising", YesNo.Yes);
																		sa.assertTrue(false, "Not able to click on OK button so cannot get contact id and verify contact details on  created fundraising");
																	} 
																	flag=true;
																	
																}else {
																	log(LogStatus.ERROR, "Not able to click on create fundraising button so cannot create fundraisings", YesNo.Yes);
																	sa.assertTrue(false, "Not able to click on create fundraising button so cannot create fundraisings");
																}
															}else {
																log(LogStatus.ERROR, "Not able to select  Default Fundraising Values so cannot create fundraising", YesNo.Yes);
																sa.assertTrue(false, "Not able to select  Default Fundraising Values so cannot create fundraising");
															}
														}else {
															log(LogStatus.ERROR, "Not able to click on apply button so cannot create fundraising", YesNo.Yes);
															sa.assertTrue(false, "Not able to click on apply button so cannot create fundraising");
														}
														
													}else {
														log(LogStatus.ERROR, "Fundraising contact is verified and select role,primary contact", YesNo.Yes);
														sa.assertTrue(false, "Fundraising contact is verified and select role,primary contact");
													}
												}else {
													log(LogStatus.ERROR, "Not able to click on "+accountlist.get(0)+" fundraising contact link so cannot verify conatatcs and create fundraising", YesNo.Yes);
													sa.assertTrue(false, "Not able to click on "+accountlist.get(0)+" fundraising contact link so cannot verify conatatcs and create fundraising");
												}
												
											}else {
												log(LogStatus.ERROR, "Contact Name is not verified in review investor list so cannot create fundraising",YesNo.Yes);
												sa.assertTrue(false, "Contact Name is not verified in review investor list so cannot create fundraising");
											}
										}else {
											log(LogStatus.ERROR, "Not able to click on Add To Fundraising List Button so cannot create fundraising", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on Add To Fundraising List Button so cannot create fundraising");
										}
									}else {
										log(LogStatus.ERROR, " Not able to select Contact Name from select investor grid so cannot create fundraising", YesNo.Yes);
										sa.assertTrue(false, " Not able to select Contact Name from select investor grid so cannot create fundraising");
									}
									
								}else {
									log(LogStatus.ERROR, "Not able to apply filter logic so cannot verify create fundraising page", YesNo.Yes);
									sa.assertTrue(false, "Not able to apply filter logic so cannot verify create fundraising page");
								}
							}else {
								log(LogStatus.ERROR, "Not able to click on Search Based On Accounts And Contacts Tab so cannot verify create fundraising page", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Search Based On Accounts And Contacts Tab so cannot verify create fundraising page");
							}
						}else {
							log(LogStatus.ERROR, "Not able to click on select fund continue button so cannot create fundraising", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on select fund continue button so cannot create fundraising");
						}
					}else {
						log(LogStatus.ERROR, "Not able to select fund from select fund pop up so cannot verify create fundraising page", YesNo.Yes);
						sa.assertTrue(false, "Not able to select fund from select fund pop up so cannot verify create fundraising page");
					}
					
				}
				
				
			}else {
				log(LogStatus.ERROR, "Not able to click on create fundraising link so cannot verify create fundraising page : "+j, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on create fundraising link so cannot verify create fundraising page : "+j);
			}
		}
			if (flag) {
				if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToDefaultContent(driver);
				}
				
				String[] ss= {Smoke_FR1,Smoke_FR2};
				for(int i=0; i<ss.length; i++) {
					if(home.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
						if(frpg.clickOnCreatedFundRaising(environment, mode, ss[i])) {
							if(frpg.clickOnRelatedList(environment, mode, RecordType.Fundraising, RelatedList.Fundraising_Contacts, RelatedTab.Fundraising_Contacts.toString())) {
									if(i==0){
										if(frpg.verifyfundraisingContacts("",SmokeC1_FName+" "+SmokeC1_LName,SmokeINS1,SmokeFRC1_Role,SmokeFRC1_Email)) {
											log(LogStatus.INFO, SmokeC1_FName+" "+SmokeC1_LName+" contact details are verified on Fundraising "+ss[i], YesNo.No);
										}else {
											log(LogStatus.INFO, SmokeC1_FName+" "+SmokeC1_LName+" contact details are not verified on Fundraising "+ss[i], YesNo.No);
											sa.assertTrue(false,SmokeC1_FName+" "+SmokeC1_LName+" contact details are not verified on Fundraising "+ss[i]);
										}
									}
									if(i==1){
										if(frpg.verifyfundraisingContacts("",SmokeC3_FName+" "+SmokeC3_LName,SmokeINDINV1,SmokeFRC3_Role,SmokeFRC3_Email)) {
											log(LogStatus.INFO, SmokeC3_FName+" "+SmokeC3_LName+" and "+SmokeC4_FName+" "+SmokeC4_LName+"  contact details are verified on Fundraising "+ss[i], YesNo.No);
										}else {
											log(LogStatus.INFO, SmokeC3_FName+" "+SmokeC3_LName+" and "+SmokeC4_FName+" "+SmokeC4_LName+" contact details are not verified on Fundraising "+ss[i], YesNo.No);
											sa.assertTrue(false,SmokeC3_FName+" "+SmokeC3_LName+" and "+SmokeC4_FName+" "+SmokeC4_LName+" contact details are not verified on Fundraising "+ss[i]);
										}
									
									if(frpg.verifyfundraisingContacts("",SmokeC4_FName+" "+SmokeC4_LName,SmokeINDINV1,SmokeFRC4_Role,SmokeFRC4_Email)) {
										log(LogStatus.INFO, SmokeC4_FName+" "+SmokeC4_LName+"  contact details are verified on Fundraising "+ss[i], YesNo.No);
									}else {
										log(LogStatus.INFO, SmokeC4_FName+" "+SmokeC4_LName+" contact details are not verified on Fundraising "+ss[i], YesNo.No);
										sa.assertTrue(false,SmokeC4_FName+" "+SmokeC4_LName+" contact details are not verified on Fundraising "+ss[i]);
									}
									}
							}else {
								log(LogStatus.ERROR, "Not able to click on related list tab in lightning mode so cannot get contact id "+SmokeC3_FName+" "+SmokeC3_LName+","+SmokeC4_FName+" "+SmokeC4_LName, YesNo.Yes);
								sa.assertTrue(false, "Not able to click on related list tab in lightning mode so cannot get contact id "+SmokeC3_FName+" "+SmokeC3_LName+","+SmokeC4_FName+" "+SmokeC4_LName);
							}
						}else {
							log(LogStatus.ERROR, "Not able to click on create fundraising "+Smoke_FR1+" so cannot get contact id "+SmokeC3_FName+" "+SmokeC3_LName+","+SmokeC4_FName+" "+SmokeC4_LName, YesNo.Yes);
							sa.assertTrue(false,  "Not able to click on create fundraising "+Smoke_FR1+" so cannot get contact id "+SmokeC3_FName+" "+SmokeC3_LName+","+SmokeC4_FName+" "+SmokeC4_LName);
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on fundraising tab so cannot get contact id from "+Smoke_FR2, YesNo.Yes);
						sa.assertTrue(false,"Not able to click on fundraising tab so cannot get contact id from "+Smoke_FR2);
					}
				}
			
				
			} else {
				log(LogStatus.ERROR, "Not able to click on OK button so cannot get contact id and verify contact details on  created fundraising", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on OK button so cannot get contact id and verify contact details on  created fundraising");
			
			}
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc012_1_verifyCreateFundraisingsForCoInvestment(String environment, String mode) {
			LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
			HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
			MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
			FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
			FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
			List<String> contactNamelist= new ArrayList<String>();
			contactNamelist.add(SmokeC1_FName+" "+SmokeC1_LName);
			contactNamelist.add(SmokeC2_FName+" "+SmokeC2_LName);
			contactNamelist.add(SmokeC3_FName+" "+SmokeC3_LName);
			contactNamelist.add(SmokeC4_FName+" "+SmokeC4_LName);
			List<String> accountlist= new ArrayList<String>();
			accountlist.add(SmokeINS1);
			accountlist.add(SmokeINS1);
			accountlist.add(SmokeINDINV1);
			accountlist.add(SmokeINDINV1);
			List<String> Instituon1ContactName= new ArrayList<String>();
			Instituon1ContactName.add(SmokeC1_FName+" "+SmokeC1_LName); 
			Instituon1ContactName.add(SmokeC2_FName+" "+SmokeC2_LName);
			List<String> Institution1List= new ArrayList<String>();
			Institution1List.add(SmokeINS1);
			Institution1List.add(SmokeINS1);
			List<String> actionsList= new ArrayList<String>();
			actionsList.add(SmokeC1_FName+" "+SmokeC1_LName+"<break>"+fundraisingContactActions.Remove.toString());
			actionsList.add(SmokeC2_FName+" "+SmokeC2_LName+"<break>"+fundraisingContactActions.Remove.toString());
			actionsList.add(SmokeC1_FName+" "+SmokeC1_LName+"<break>"+fundraisingContactActions.AddNewContactInFundraisingContact.toString());
			actionsList.add(SmokeC1_FName+" "+SmokeC1_LName+"<break>"+fundraisingContactActions.Remove.toString());
			String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/yyyy");
			boolean flag=false;
			lp.CRMLogin(crmUser1EmailID, adminPassword);
			if(fund.clickOnTab(environment, mode, TabName.FundsTab)) {
				log(LogStatus.INFO, "clicked on fund tab", YesNo.No);
				if(fund.clickOnCreatedFund(environment, mode, Smoke_Fund2)) {
					log(LogStatus.INFO, "clicked on fund "+Smoke_Fund2, YesNo.No);
					if(fund.clickOnRelatedList(environment, mode, RecordType.Fundraising, RelatedList.Fundraisings, RelatedTab.Fundraising.toString())) {
						if(click(driver, fund.getCreateFundRaisingBtn(environment, mode, PageName.FundsPage,30),"create fundraising button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "click on fundraising button", YesNo.No);
							if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
							}
							if(home.selectFundNameOrCompanyNameOnCreateFundraisings(environment,mode, PopUpName.selectFundPopUp, Smoke_Fund2, null)) {
								log(LogStatus.INFO, "Select Fund : "+Smoke_Fund2, YesNo.No);
								if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
								}
								if(click(driver, home.getSelectFundNamePopUpContinueBtn(), "continue button", action.SCROLLANDBOOLEAN)) {
									ThreadSleep(3000);
									if(home.applyFilterOnSearchBasedOnAccountsandContacts( FundraisingContactPageTab.SearchBasedOnExistingFunds,SearchBasedOnExistingFundsOptions.AllContacts, environment,mode,Smoke_Fund1,null, null, null, null)) {
										log(LogStatus.INFO, "apply filter logic", YesNo.No);
										windowScrollYAxis(driver, 0, 500);
										ThreadSleep(3000);
										if(home.clickOnInfoIcon(SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1)) {
											log(LogStatus.INFO, "clicked on contact "+SmokeC1_FName+" "+SmokeC1_LName+" info icon",YesNo.No);
											ThreadSleep(2000);
											SoftAssert result=home.VerifyPastFundraisingData(Smoke_FR1,"",SmokeFR1_Investment_Likely_Amount,SmokeFRC1_Role,SmokeFRC1_Primary,date);
											sa.combineAssertions(result);
											ThreadSleep(2000);
											if(home.checkContactOrAccountOrFundraisingPage(environment, mode, Smoke_FR1, PageName.pastFundraisingContactPopUp, columnName.Fundraising_Name, home.getPastFundraisingsPopUpScrollBox(PageName.pastFundraisingContactPopUp,5))) {
												log(LogStatus.INFO, "Fundraising page is verified "+Smoke_FR1, YesNo.No);
	
	
											}else {
												log(LogStatus.ERROR, "Fundraising page is not verified"+Smoke_FR1, YesNo.Yes);
												sa.assertTrue(false, "Fundraising page is not verified"+Smoke_FR1);
											}
											switchToDefaultContent(driver);
											if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
												switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
											}
											for(int i=0; i<2; i++) {
												if(click(driver, market.getSelectProspectsWrenchIcon(PageName.pastFundraisingContactPopUp,mode,60), "wrench icon", action.SCROLLANDBOOLEAN)) {
													ThreadSleep(1000);
													if(i==0) {
														if (click(driver, home.getPastFundraisingwrenchIconCancelBtn(PageName.pastFundraisingContactPopUp, 10), "Cancel Btn", action.BOOLEAN)) {
															log(LogStatus.INFO,"click on Wrench Icon Cancel Btn",YesNo.No);
														} else {
															log(LogStatus.FAIL,"Not able to click on Wrench Icon Cancel Btn",YesNo.Yes);
															sa.assertTrue(false, "Not able to click on Wrench Icon Cancel Btn");
														}
	
													}
													if(i==1) {
														if (click(driver, home.getPastFundraisingwrenchIconCrossIcon(PageName.pastFundraisingContactPopUp,10), "cross Btn", action.BOOLEAN)) {
															log(LogStatus.INFO,"click on Wrench Icon cross Btn",YesNo.No);
														} else {
															log(LogStatus.FAIL,"Not able to click on Wrench Icon cross Btn",YesNo.Yes);
															sa.assertTrue(false, "Not able to click on Wrench Icon cross Btn");
														}
	
													}
													ThreadSleep(1000);
												}else{
													log(LogStatus.FAIL,"Not able to click on Wrench Icon",YesNo.Yes);
													sa.assertTrue(false, "Not able to click on Wrench Icon");
												}
	
											}
											String [] ss = {"Created By ID"};
											if(market.addAndRemoveCloumnInSelectProspectGrid(mode,PageName.pastFundraisingContactPopUp,ss, null, null,true).isEmpty()) {
												log(LogStatus.PASS, "column added form column to display popup", YesNo.No);
												ThreadSleep(2000);
												if(compareMultipleList(driver, "Created By ID", market.getSelectProspectsHeaderTextList(PageName.pastFundraisingContactPopUp)).isEmpty()) {
													log(LogStatus.PASS, "Selected Prospects Header Text is verified ", YesNo.No);
												}else {
													appLog.error("Selected Prospects Header Text is not verified");
													log(LogStatus.FAIL,"Selected Prospects Header Text is not verified",YesNo.Yes);
													sa.assertTrue(false, "Selected Prospects Header Text is not verified");
												}
												ThreadSleep(2000);
												if(market.columnToDisplayRevertToDefaultsSettings(PageName.pastFundraisingContactPopUp,mode)) {
													log(LogStatus.PASS, "column to display settings is revert to default successfully", YesNo.No);
												}else {
													appLog.error("column to display settings is not revert to default");
													log(LogStatus.FAIL,"column to display settings is not revert to default",YesNo.Yes);
													sa.assertTrue(false,"column to display settings is not revert to default");
												}
												if(!compareMultipleList(driver, "Created By ID", market.getSelectProspectsHeaderTextList(PageName.pastFundraisingContactPopUp)).isEmpty()) {
													log(LogStatus.PASS, "Select Prospects Header Text is removed", YesNo.No);
												}else {
													appLog.error("Select Prospects Header Text is not removed");
													log(LogStatus.FAIL,"Select Prospects Header Text is not removed",YesNo.Yes);
													sa.assertTrue(false, "Select Prospects Header Text is not removed");
												}
											}else {
												appLog.error("Not able to add column form column to display popup");
												log(LogStatus.FAIL,"Not able to add column form column to display popup",YesNo.Yes);
												sa.assertTrue(false, "Not able to add column form column to display popup");
											}
	
											if(click(driver, home.getPastFundraisingPopCrossIcon(PageName.pastFundraisingContactPopUp, 10), "past fundraising cross icon", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "clicked on past fundraising cross icon", YesNo.No);
											}else {
												log(LogStatus.ERROR, "Not able to click on past fundraising cross icon so cannot close pop up", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on past fundraising cross icon so cannot close pop up");
											}
	
											/*	if(click(driver, home.getPastFundraisingwrenchIconCrossIcon(PageName.pastFundraisingContactPopUp, 10), "past fundraising wrench cross icon", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "clicked on past fundraising Wrech cross icon", YesNo.No);
											}else {
												log(LogStatus.ERROR, "Not able to click on past fundraising Wrech cross icon so cannot close pop up", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on past fundraising Wrech cross icon so cannot close pop up");
											}*/
	
	
										}else {
											log(LogStatus.ERROR, "Not able to click on contact "+SmokeC1_FName+" "+SmokeC1_LName+" info icon so cannot verify past fundraising data", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on contact "+SmokeC1_FName+" "+SmokeC1_LName+" info icon so cannot verify past fundraising data");
										}
										System.err.println("><><><><>< ");
										ThreadSleep(5000);
										WebElement ele = FindElement(driver, "//a[contains(text(),'"+SmokeC1_FName+"')][contains(text(),'"+SmokeC1_LName+"')]/../../following-sibling::span//a[text()='"+SmokeINS1+"']", "account name link", action.SCROLLANDBOOLEAN,10);
										if(ele!=null) {
											if(mouseOverOperation(driver,ele)) {
												log(LogStatus.INFO, "mouse over on account name "+SmokeINS1, YesNo.No);
												ThreadSleep(5000);
												ele=FindElement(driver,"//a[contains(text(),'"+SmokeC1_FName+"')][contains(text(),'"+SmokeC1_LName+"')]/../../following-sibling::span//a[text()='"+SmokeINS1+"']/following-sibling::a//img", "", action.BOOLEAN,10);
												if(ele!=null) {
													if(clickUsingJavaScript(driver, ele,"account name info icon", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO, "clicked on account name "+SmokeINS1, YesNo.No);
														ThreadSleep(2000);
														if(home.checkContactOrAccountOrFundraisingPage(environment, mode, Smoke_FR1, PageName.pastFundraisingAccountPopUp, columnName.Fundraising_Name, home.getPastFundraisingsPopUpScrollBox(PageName.pastFundraisingAccountPopUp,5))) {
															log(LogStatus.INFO, "Fundraising page is verified "+Smoke_FR1, YesNo.No);
															if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
																switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
															}
	
														}else {
															log(LogStatus.ERROR, "Fundraising page is not verified"+Smoke_FR1, YesNo.Yes);
															sa.assertTrue(false, "Fundraising page is not verified"+Smoke_FR1);
														}
														switchToDefaultContent(driver);
														if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
															switchToDefaultContent(driver);
															switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
														}
														for(int i=0; i<2; i++) {
															if(click(driver, market.getSelectProspectsWrenchIcon(PageName.pastFundraisingAccountPopUp,mode,60), "wrench icon", action.SCROLLANDBOOLEAN)) {
																ThreadSleep(1000);
																if(i==0) {
																	if (click(driver, home.getPastFundraisingwrenchIconCancelBtn(PageName.pastFundraisingAccountPopUp, 10), "Cancel Btn", action.BOOLEAN)) {
																		log(LogStatus.INFO,"click on Wrench Icon Cancel Btn",YesNo.No);
																	} else {
																		log(LogStatus.FAIL,"Not able to click on Wrench Icon Cancel Btn",YesNo.Yes);
																		sa.assertTrue(false, "Not able to click on Wrench Icon Cancel Btn");
																	}
	
																}
																if(i==1) {
																	if (click(driver, home.getPastFundraisingwrenchIconCrossIcon(PageName.pastFundraisingAccountPopUp, 10), "Cancel Btn", action.BOOLEAN)) {
																		log(LogStatus.INFO,"click on Wrench Icon cross Btn",YesNo.No);
																	} else {
																		log(LogStatus.FAIL,"Not able to click on Wrench Icon cross Btn",YesNo.Yes);
																		sa.assertTrue(false, "Not able to click on Wrench Icon cross Btn");
																	}
	
																}
																ThreadSleep(1000);
															}else{
																log(LogStatus.FAIL,"Not able to click on Wrench Icon",YesNo.Yes);
																sa.assertTrue(false, "Not able to click on Wrench Icon");
															}
	
														}
														String [] ss = {"Created By ID"};
														if(market.addAndRemoveCloumnInSelectProspectGrid(mode,PageName.pastFundraisingAccountPopUp,ss, null, null,true).isEmpty()) {
															log(LogStatus.PASS, "column added form column to display popup", YesNo.No);
															ThreadSleep(2000);
															if(compareMultipleList(driver, "Created By ID", market.getSelectProspectsHeaderTextList(PageName.pastFundraisingAccountPopUp)).isEmpty()) {
																log(LogStatus.PASS, "Selected Prospects Header Text is verified ", YesNo.No);
															}else {
																appLog.error("Selected Prospects Header Text is not verified");
																log(LogStatus.FAIL,"Selected Prospects Header Text is not verified",YesNo.Yes);
																sa.assertTrue(false, "Selected Prospects Header Text is not verified");
															}
															ThreadSleep(2000);
															if(market.columnToDisplayRevertToDefaultsSettings(PageName.pastFundraisingAccountPopUp,mode)) {
																log(LogStatus.PASS, "column to display settings is revert to default successfully", YesNo.No);
															}else {
																appLog.error("column to display settings is not revert to default");
																log(LogStatus.FAIL,"column to display settings is not revert to default",YesNo.Yes);
																sa.assertTrue(false,"column to display settings is not revert to default");
															}
															if(!compareMultipleList(driver, "Created By ID", market.getSelectProspectsHeaderTextList(PageName.pastFundraisingAccountPopUp)).isEmpty()) {
																log(LogStatus.PASS, "Select Prospects Header Text is removed", YesNo.No);
															}else {
																appLog.error("Select Prospects Header Text is not removed");
																log(LogStatus.FAIL,"Select Prospects Header Text is not removed",YesNo.Yes);
																sa.assertTrue(false, "Select Prospects Header Text is not removed");
															}
														}else {
															appLog.error("Not able to add column form column to display popup");
															log(LogStatus.FAIL,"Not able to add column form column to display popup",YesNo.Yes);
															sa.assertTrue(false, "Not able to add column form column to display popup");
														}
	
														if(click(driver, home.getPastFundraisingPopCrossIcon(PageName.pastFundraisingAccountPopUp, 10), "past fundraising cross icon", action.SCROLLANDBOOLEAN)) {
															log(LogStatus.INFO, "clicked on past fundraising cross icon", YesNo.No);
														}else {
															log(LogStatus.ERROR, "Not able to click on past fundraising cross icon so cannot close pop up", YesNo.Yes);
															sa.assertTrue(false, "Not able to click on past fundraising cross icon so cannot close pop up");
														}
	
														/*if(click(driver, home.getPastFundraisingwrenchIconCrossIcon(PageName.pastFundraisingAccountPopUp, 10), "past fundraising wrench cross icon", action.SCROLLANDBOOLEAN)) {
															log(LogStatus.INFO, "clicked on past fundraising wrench cross icon", YesNo.No);
														}else {
															log(LogStatus.ERROR, "Not able to click on past fundraising wrench cross icon so cannot close pop up", YesNo.Yes);
															sa.assertTrue(false, "Not able to click on past fundraising wrench cross icon so cannot close pop up");
														}*/
	
	
	
	
													}else {
														log(LogStatus.ERROR, "Not able to click on account name "+SmokeINS1+"  info icon", YesNo.Yes);
														sa.assertTrue(false,"Not able to click on account name "+SmokeINS1+"  info icon");
													}
												}else {
													log(LogStatus.ERROR, "Not able to click on account "+SmokeINS1+" info icon so cannot verify past fundraising data", YesNo.Yes);
													sa.assertTrue(false, "Not able to click on account "+SmokeINS1+" info icon so cannot verify past fundraising data");
												}
	
											}else {
												log(LogStatus.ERROR,"Not able to mouse over on account name "+SmokeINS1+" so cannot click on contact Name info icon", YesNo.Yes);
												sa.assertTrue(false, "Not able to mouse over on account name "+SmokeINS1+" so cannot click on contact Name info icon");
											}
	
										}else {
											log(LogStatus.ERROR, "Not able to click on account name "+SmokeINS1+" info icon so cannot verify past fundraising data", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on account name "+SmokeINS1+" info icon so cannot verify past fundraising data");
										}
	
	
										if(home.selectInvestorsContactFromCreateFundRaising(contactNamelist,accountlist).isEmpty()) {
											log(LogStatus.INFO, "contact name is selected successfully",YesNo.No);
											if(click(driver, home.getAddToFundraisingListBtn(30), "Add To Fundraising List Button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "click on Add To Fundraising List", YesNo.No);
												if(home.verifyContactNameInReviewInvestorList(contactNamelist, accountlist).isEmpty()) {
													log(LogStatus.INFO, "Contact Name is verified in review investor list", YesNo.No);
													ThreadSleep(2000);
													if(home.clickOnReviewInvestorListFundraisingContactLink(accountlist.get(0))) {
														ThreadSleep(2000);
														if(home.verifyFundRaisingContactAndSetRolePrimaryContact(Instituon1ContactName, Institution1List, actionsList).isEmpty()) {
															log(LogStatus.INFO, "contatcts are removed successfully", YesNo.No);
															String error=HomePageErrorMessage.fundraisingContactNoDataToDisplay;
															if(home.getFundraisingContactErrorMsg(30)!=null) {
																String aa =home.getFundraisingContactErrorMsg(10).getText().trim();
																if(aa.contains(error)) {
																	log(LogStatus.INFO, error+" Error Message is verified.", YesNo.No);
																}else {
																	log(LogStatus.ERROR, error+" Error Message is not verified.", YesNo.Yes);
																	sa.assertTrue(false, error+" Error Message is not verified.");
																}
															}else {
																log(LogStatus.ERROR, error+" is not visible so cannot verify error message",YesNo.Yes);
																sa.assertTrue(false, error+" is not visible so cannot verify error message");
															}
															if(home.getFundraisingContactRecordCount(20)!=null)
																if(home.getFundraisingContactRecordCount(10).getText().contains("0")) {
																	log(LogStatus.INFO, "fundraising contact record contact is matched successfully", YesNo.No);
																}else {
																	log(LogStatus.ERROR, "fundraising contact record contact is not matched", YesNo.Yes);
																	sa.assertTrue(false, "fundraising contact record contact is not matched");
																}
															//														home.getFundraisingContactPopUpApplyBtn(20);
															System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
															ThreadSleep(10000);
															
	
															if(clickUsingJavaScript(driver, home.getFundraisingContactPopUpApplyBtn(20), "apply button", action.SCROLLANDBOOLEAN)) {
																log(LogStatus.INFO, "clicked on apply button", YesNo.No);
																ThreadSleep(5000);
																if(click(driver, home.getCreateFundraisingDefaultFundraisingValuesAddRowsLink(10), "add row buton", action.SCROLLANDBOOLEAN)) {
																	log(LogStatus.INFO, "Clicked on Add row link in Default Fundraising Values", YesNo.No);
																}else {
																	log(LogStatus.INFO, "Not able to Add row link in Default Fundraising Values", YesNo.Yes);
																	sa.assertTrue(false, "Not able to Add row link in Default Fundraising Values");
																}
																List<String> fieldAndValue=new ArrayList<String>();
																fieldAndValue.add("Fundraising:Investment Likely Amount (mn)<break>100000000");
																fieldAndValue.add("<break>");
																if(home.selectDefaultFundraisingValue(fieldAndValue)) {
																	log(LogStatus.INFO, "select  Default Fundraising Values", YesNo.No);
																	SoftAssert saaa = home.deleteRowsFromCreateFundraisingDefaultFundraisingValues(environment, mode);
																	sa.combineAssertions(saaa);
																	for(int k=0; k<3; k++) {
																		if(click(driver, home.getCreateFundraisingBtn(PageName.CreateFundraisingPage, 30), "create fundraising button", action.SCROLLANDBOOLEAN)) {
																			log(LogStatus.INFO, "clicked on create fundraising button", YesNo.No);
																			if(k==0) {
																				if(click(driver, home.getCreateFundraisingPopUpCancelBtn(5), "cancel button", action.SCROLLANDBOOLEAN)) {
																					log(LogStatus.INFO,"clicked on cancel button", YesNo.No);
																				}else {
																					log(LogStatus.INFO, "Not able to click on create fundraising cancel button", YesNo.Yes);
																					sa.assertTrue(false, "Not able to click on create fundraising cancel button");
																				}
																			}
																			if(k==1) {
																				if(click(driver, home.getCreateFundraisingPopUpCrossIcon(5), "cross button", action.SCROLLANDBOOLEAN)) {
																					log(LogStatus.INFO,"clicked on cross button", YesNo.No);
																				}else {
																					log(LogStatus.INFO, "Not able to click on create fundraising cross button", YesNo.Yes);
																					sa.assertTrue(false, "Not able to click on create fundraising cross button");
																				}
																			}
																			if(k==2) {
																				if(home.selectFundNameOrCompanyNameOnCreateFundraisings(environment, mode,PopUpName.WarningPopUp, null, SmokeCOM1)) {
																					log(LogStatus.INFO, "select company name "+SmokeCOM1, YesNo.No);
																					if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
																						switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
																					}
																					if(click(driver, home.getCreateFundraisingBtn(PageName.WarningPopUp, 30), "create fundraising button", action.SCROLLANDBOOLEAN)) {
																						log(LogStatus.INFO, "clicked on create fundraising button", YesNo.No);
																						if(click(driver,home.getCreateFundraisingConfirmationOkBtn(30), "ok button", action.SCROLLANDBOOLEAN)) {
																							log(LogStatus.INFO, "clicked on OK button", YesNo.No);
																							flag=true;
																						}else {
																							log(LogStatus.ERROR, "Not able to click on Ok button so cannot nevigate on fundraising page", YesNo.Yes);
																							sa.assertTrue(false, "Not able to click on Ok button so cannot nevigate on fundraising page");
																						}
																					}else {
																						log(LogStatus.ERROR, "Not able to click on create fundraising button so warniing pop up", YesNo.Yes);
																						sa.assertTrue(false, "Not able to click on create fundraising button so warniing pop up");
																					}
																				}else {
																					log(LogStatus.ERROR, "Not able to select company name "+SmokeCOM1, YesNo.Yes);
																					sa.assertTrue(false, "Not able to select company name "+SmokeCOM1);
																				} 
	
																			}
	
	
																		}else {
																			log(LogStatus.ERROR, " Not able to click on create fundraising button so cannot create fundraising", YesNo.Yes);
																			sa.assertTrue(false, " Not able to click on create fundraising button so cannot create fundraising");
																		}
	
																	}
	
																}else {
																	log(LogStatus.ERROR, "Not able to select Fundraising:Investment Likely Amount field and pass value 100000000 ", YesNo.Yes);
																	sa.assertTrue(false, "Not able to select Fundraising:Investment Likely Amount field and pass value 100000000 ");
																}
															}else {
																log(LogStatus.ERROR, "Not able to click on apply button so cannot close fundraising contact pop up", YesNo.Yes);
																sa.assertTrue(false, "Not able to click on apply button so cannot close fundraising contact pop up");
															}
														}else {
															log(LogStatus.ERROR, "Not able to remove contacts form fundraising contact grid", YesNo.Yes);
															sa.assertTrue(false,  "Not able to remove contacts form fundraising contact grid");
														}
													}else {
														log(LogStatus.ERROR, "Not able to click on "+accountlist.get(0)+" list so cannot remove contacts", YesNo.Yes);
														sa.assertTrue(false, "Not able to click on "+accountlist.get(0)+" list so cannot remove contacts");
													}
												}else {
													log(LogStatus.ERROR, "Not able to verify contact name in review investor list", YesNo.Yes);
													sa.assertTrue(false, "Not able to verify contact name in review investor list");
												}
	
											}else {
												log(LogStatus.ERROR, "Not able to click on add fundraising button so cannot create fundraising", YesNo.No);
												sa.assertTrue(false,  "Not able to click on add fundraising button so cannot create fundraising");
											}
										}else {
											log(LogStatus.ERROR, "Not able to add contacts in review investor list so cannot create fundraising", YesNo.Yes);
											sa.assertTrue(false, "Not able to add contacts in review investor list so cannot create fundraising");
										}
									}else {
										log(LogStatus.ERROR, "Not able to select existing fund "+Smoke_Fund1+" from Search Based On Existing Funds", YesNo.Yes);
										sa.assertTrue(false, "Not able to select existing fund "+Smoke_Fund1+" from Search Based On Existing Funds");
									}
								}else {
									log(LogStatus.ERROR, "Not able to click on select fund continue button so cannot create fundraising", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on select fund continue button so cannot create fundraising");
								}
							}else {
								log(LogStatus.ERROR, "Not able to select fund "+Smoke_Fund1+" from select fund pop up so cannot create fundraising", YesNo.Yes);
								sa.assertTrue(false, "Not able to select fund "+Smoke_Fund1+" from select fund pop up so cannot create fundraising");
							}
	
						}else {
							log(LogStatus.ERROR, "Not able to click on cerate fundraising button so cannot create fundraising", YesNo.Yes);
							sa.assertTrue(false,  "Not able to click on cerate fundraising button so cannot create fundraising");
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on related list link so cannot click on create fundraising button",YesNo.Yes);
						sa.assertTrue(false, "Not able to click on related list link so cannot click on create fundraising button");
					}
	
				}else {
					log(LogStatus.ERROR, "Not able to click on fund "+Smoke_Fund2, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on fund "+Smoke_Fund2);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on fund tab so cannot create co investment fundraising", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on fund tab so cannot create co investment fundraising");
			}
			
			if (flag) {
				if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToDefaultContent(driver);
				}
				String[] FR= {Smoke_FR3,Smoke_FR4};
				for(int i=0; i<FR.length; i++)
					if(home.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
						if(frpg.clickOnCreatedFundRaising(environment, mode,FR[i])) {
							if(frpg.clickOnRelatedList(environment, mode, RecordType.Fundraising, RelatedList.Fundraising_Contacts, RelatedTab.Fundraising_Contacts.toString())) {
								if(i==0){
									try{if(home.verifyGridErrorMessage1(environment, mode, RelatedList.Fundraising_Contacts,HomePageErrorMessage.NoRecordToDisplayOnFR, 10)) {
										log(LogStatus.INFO, HomePageErrorMessage.NoRecordToDisplayOnFR+" Verify Error Message on "+FR[i], YesNo.No);
									}else {
										log(LogStatus.ERROR, HomePageErrorMessage.NoRecordToDisplayOnFR+" Error Message is not verified on "+FR[i], YesNo.Yes);
										sa.assertTrue(false, HomePageErrorMessage.NoRecordToDisplayOnFR+" Error Message is not verified on "+FR[i]);
									}} catch(Exception e) {
										appLog.error("exception in checking error");
									}
								}
								if(i==1)
								{
										
										if(frpg.verifyfundraisingContacts("",SmokeC3_FName+" "+SmokeC3_LName,SmokeINDINV1,SmokeFRC5_Role,SmokeFRC5_Email)) {
											log(LogStatus.INFO, SmokeC3_FName+" "+SmokeC3_LName+"  contact details are verified on Fundraising "+FR[i], YesNo.No);
										}else {
											log(LogStatus.INFO, SmokeC3_FName+" "+SmokeC3_LName+" contact details are not verified on Fundraising "+FR[i], YesNo.No);
											sa.assertTrue(false,SmokeC3_FName+" "+SmokeC3_LName+" contact details are not verified on Fundraising "+FR[i]);
										}
										
										if(frpg.verifyfundraisingContacts("",SmokeC4_FName+" "+SmokeC4_LName,SmokeINDINV1,SmokeFRC6_Role,SmokeFRC6_Email)) {
											log(LogStatus.INFO, SmokeC4_FName+" "+SmokeC4_LName+"  contact details are verified on Fundraising "+FR[i], YesNo.No);
										}else {
											log(LogStatus.INFO, SmokeC4_FName+" "+SmokeC4_LName+" contact details are not verified on Fundraising "+FR[i], YesNo.No);
											sa.assertTrue(false,SmokeC4_FName+" "+SmokeC4_LName+" contact details are not verified on Fundraising "+FR[i]);
										}
								}
									
							}else {
								log(LogStatus.ERROR, "Not able to click on related list tab in lightning mode so cannot get contact id "+SmokeC3_FName+" "+SmokeC3_LName+","+SmokeC4_FName+" "+SmokeC4_LName, YesNo.Yes);
								sa.assertTrue(false, "Not able to click on related list tab in lightning mode so cannot get contact id "+SmokeC3_FName+" "+SmokeC3_LName+","+SmokeC4_FName+" "+SmokeC4_LName);
							}
						}else {
							log(LogStatus.ERROR, "Not able to click on create fundraising "+Smoke_FR4+" so cannot get contact id "+SmokeC3_FName+" "+SmokeC3_LName+","+SmokeC4_FName+" "+SmokeC4_LName, YesNo.Yes);
							sa.assertTrue(false,  "Not able to click on create fundraising "+Smoke_FR4+" so cannot get contact id "+SmokeC3_FName+" "+SmokeC3_LName+","+SmokeC4_FName+" "+SmokeC4_LName);
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on fundraising tab so cannot get contact id from "+Smoke_FR4, YesNo.Yes);
						sa.assertTrue(false,"Not able to click on fundraising tab so cannot get contact id from "+Smoke_FR4);
					}
			
				
			} else {
				log(LogStatus.ERROR, "Not able to click on OK button so cannot get contact id and verify contact details on  created fundraising", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on OK button so cannot get contact id and verify contact details on  created fundraising");
			}
			switchToDefaultContent(driver);
			lp.CRMlogout(environment, mode);
			sa.assertAll();
	
}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc012_2_addFilterLogicInCreateFundraising(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		List<String> contactNamelist= new ArrayList<String>();
		contactNamelist.add(SmokeC1_FName+" "+SmokeC1_LName);
		List<String> accountlist= new ArrayList<String>();
		accountlist.add(SmokeINS1);
		WebElement ele=null;
		String msg = null;
		String parenTiD;
		String Xpath = null;
		
		String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Fundraising.toString();
		
		if(home.ClickOnItemOnNavatarEdge(navigationMenuName, bulkActionNavigationLink, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "clicked on create fundraising link", YesNo.No);
			if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));

			}
			if(home.selectFundNameOrCompanyNameOnCreateFundraisings(environment,mode, PopUpName.selectFundPopUp, Smoke_Fund1, SmokeCOM1)) {
				log(LogStatus.INFO, "Select Fund : "+Smoke_Fund1, YesNo.No);
				if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
				}
				if(click(driver, home.getSelectFundNamePopUpContinueBtn(), "continue button", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					

					for(int j=0; j<2; j++) {
						if(j==0) {

							if(click(driver, home.getSearchBasedOnAccountsAndContactsTab(30), "Search Based On Accounts And Contacts Tab", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "click on Search Based On Accounts And Contacts Tab", YesNo.No);
								ThreadSleep(3000);

							}else {
								log(LogStatus.ERROR, "Not able to click on Search Based On Accounts And Contacts Tab so cannot verify create fundraising page", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Search Based On Accounts And Contacts Tab so cannot verify create fundraising page");
							}
						}else {
							if(mouseOverOperation(driver, home.getSearchBasedOnExistingFundsDownArrow(20))) {
								ThreadSleep(2000);
								if(clickUsingJavaScript(driver, home.getOnlyFundraisingContactOptionOnSearchBasedOnExistingFunds(20), "Only Fundraising Contact text")) {
									log(LogStatus.INFO, "clicked on Only Fundraising Contact text", YesNo.No);

								}else {
									log(LogStatus.ERROR, "Not able to click on Only Fundraising Contact option so cannot applied filter logic", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on Only Fundraising Contact option so cannot applied filter logic");

								}
							}else {
								log(LogStatus.ERROR, "Not able to click on Search Based On Existing Funds Down Arrow", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Search Based On Existing Funds Down Arrow");

							}
						}
						if(click(driver, market.getAddRowLink(AddProspectsTab.AccountAndContacts), "account and contact tab", action.BOOLEAN)) {
							log(LogStatus.FAIL,"Clicked on Add row link",YesNo.No);
							ThreadSleep(2000);
							if(click(driver, home.getCreateFundraisingFilterRemoveBtn().get(0), "remove icon", action.BOOLEAN)) {
								log(LogStatus.FAIL,"clicked on remove row link",YesNo.No);
							}else {
								appLog.error("Not able to click on remove row link so cannot check remove functionality");
								log(LogStatus.FAIL,"Not able to click on remove row link so cannot check remove functionality",YesNo.Yes);
								sa.assertTrue(false,"Not able to click on remove row link so cannot check remove functionality");
							}
							ThreadSleep(2000);
						}else {
							appLog.error("Not able to click on Add row link so cannot check remove functionality");
							log(LogStatus.FAIL,"Not able to click on Add row link so cannot check remove functionality",YesNo.Yes);
							sa.assertTrue(false,"Not able to click on Add row link so cannot check remove functionality");
						}
						if (click(driver, home.getAddFilterLogicLinkOnCreateFundraising(10), "Add Filter Logic Link", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on Add Filter Logic Link", YesNo.No);
							ThreadSleep(1000);

							Xpath="j_id0:CreateFundraisingFormId:textfilt";

							ele=isDisplayed(driver,FindElement(driver, "//input[@id='"+Xpath+"']", "Add filter logic text box", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
							if(ele!=null) {
								log(LogStatus.INFO,"find add filter logic text box",YesNo.No);	
							}else {
								log(LogStatus.FAIL,"Not able find add filter logic text box",YesNo.Yes);
								sa.assertTrue(false, "Not able find add filter logic text box  ");
							}
							if (click(driver, market.getInfoLink(10), "Info Link", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"click on Info Link",YesNo.No);
								ThreadSleep(2000);
								parenTiD = switchOnWindow(driver);

								if (parenTiD!=null) {
									log(LogStatus.INFO,"New Window is open",YesNo.No);
									Xpath="//h1//*[text()='Add Filter Logic']";
									ele=isDisplayed(driver,FindElement(driver, Xpath, "Add filter logic Window", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
									if(ele!=null) {
										log(LogStatus.INFO,"Add Filter Logic Window is open",YesNo.No);	
									}else {
										log(LogStatus.FAIL,"Add Filter Logic Window is not open",YesNo.Yes);
										sa.assertTrue(false, "Add Filter Logic Window is not open");
									}
									driver.close();
									driver.switchTo().window(parenTiD);
								} else {
									log(LogStatus.FAIL,"No New Window is open",YesNo.Yes);
									sa.assertTrue(false, "No New Window is open");
								}
							} else {
								log(LogStatus.FAIL,"Not able to click on Info Link",YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Info Link");
							}
							switchToDefaultContent(driver);
							if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 20, home.getCreateFundraisingsFrame_Lighting(20));
							}
							appLog.info("After All");
							for (int j1 = 0; j1 < 2; j1++) {
								Xpath="AddRow2";
								ele=isDisplayed(driver,FindElement(driver, "//a[@id='"+Xpath+"']", "Add row button", action.BOOLEAN,10), "Visibility", 10, "Add row button");

								if(click(driver, ele, "add row button", action.BOOLEAN)) {
									log(LogStatus.INFO,"clicked on add row link : "+j1,YesNo.No);
								}else {
									log(LogStatus.FAIL,"Not Able to click on add row link : "+j1,YesNo.Yes);
									sa.assertTrue(false, "Not Able to click on add row link : "+j1);	
								}
								ThreadSleep(2000);
							}

							if (click(driver, home.getCreateFundraisingClearBtn(10), "Clear Btn", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"click on Clear Btn",YesNo.No);	
							} else {
								log(LogStatus.FAIL,"Not able to click on Clear Btn",YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Clear Btn");
							}
						} else {
							sa.assertTrue(false, "Not able to click on Add Filter Logic Link");
							log(LogStatus.ERROR, "Not able to click on Add Filter Logic Link", YesNo.Yes);
						}
						if(j==0) {
							if(home.applyFilterOnSearchBasedOnAccountsandContacts( FundraisingContactPageTab.SearchBasedOnAccountsAndContacts,SearchBasedOnExistingFundsOptions.AllContacts, 
									environment,mode,null,"Account:Legal Name<break>Contact:Email<break>Contact:Legal Name","equals<break>equals<break>equals",SmokeINS1+"<break>"+SmokeC1_EmailID+"<break>"+SmokeINS1, "(1 AND 2) OR 3")) {
								appLog.info("Filter logic applied successfully ");
								ThreadSleep(5000);



							}else {
								appLog.error("Not able to apply filter logic in SearchBasedOnAccountsAndContacts  so cannot verify check filter logic");
								log(LogStatus.FAIL, "Not able to apply filter logic in SearchBasedOnAccountsAndContacts  so cannot verify check filter logic", YesNo.Yes);
								sa.assertTrue(false, "Not able to apply filter logic in SearchBasedOnAccountsAndContacts  so cannot verify check filter logic");
							}


						}else {
							if(home.applyFilterOnSearchBasedOnAccountsandContacts( FundraisingContactPageTab.SearchBasedOnExistingFunds,SearchBasedOnExistingFundsOptions.AllContacts, 
									environment,mode,Smoke_Fund1,"Account:Legal Name<break>Contact:Email<break>Contact:Legal Name","equals<break>equals<break>equals",SmokeINS1+"<break>"+SmokeC1_EmailID+"<break>"+SmokeINS1, "(1 AND 2) OR 3")) {
								appLog.info("Filter logic applied successfully ");
								ThreadSleep(5000);



							}else {
								appLog.error("Not able to apply filter logic in OnlyFundraisingContacts  so cannot verify check filter logic");
								log(LogStatus.FAIL, "Not able to apply filter logic in OnlyFundraisingContacts  so cannot verify check filter logic", YesNo.Yes);
								sa.assertTrue(false, "Not able to apply filter logic in OnlyFundraisingContacts  so cannot verify check filter logic");
							}
						}
						if(home.selectInvestorsContactFromCreateFundRaising(contactNamelist,accountlist).isEmpty()) {
							log(LogStatus.PASS, "Search Contacts is visible in Select Propects Grid ", YesNo.No);
						}else {
							appLog.error("Search Contacts is not visible in Select Propects Grid ");
							log(LogStatus.FAIL,"Search Contacts is not visible in Select Propects Grid ",YesNo.Yes);
							sa.assertTrue(false, "Search Contacts is not visible in Select Propects Grid ");
						}
						if(j==0) {
							if(click(driver, market.getSelectProspectsWrenchIcon(PageName.CreateFundraisingPage,mode,60), "wrench icon", action.SCROLLANDBOOLEAN)) {
								ThreadSleep(1000);

								if(selectVisibleTextFromDropDown(driver, market.getColumnToDisplayViewDropDownList(PageName.CreateFundraisingPage,30), "view drop down list", ViewFieldsName.Contact_Fields)) {
									log(LogStatus.INFO,"Select value from view drop down list : "+ViewFieldsName.Contact_Fields,YesNo.Yes);

								}else {
									log(LogStatus.FAIL,"Not able to select value from view drop down list : "+ViewFieldsName.Contact_Fields,YesNo.Yes);
									sa.assertTrue(false, "Not able to select value from view drop down list : "+ViewFieldsName.Contact_Fields);
								}
								ThreadSleep(1000);

								if(selectVisibleTextFromDropDown(driver, market.getColumnToDisplayViewDropDownList(PageName.CreateFundraisingPage,30), "view drop down list", ViewFieldsName.Account_Fields)) {
									log(LogStatus.INFO,"Select value from view drop down list : "+ViewFieldsName.Account_Fields,YesNo.No);

								}else {
									log(LogStatus.FAIL,"Not able to select value from view drop down list : "+ViewFieldsName.Account_Fields,YesNo.Yes);
									sa.assertTrue(false, "Not able to select value from view drop down list : "+ViewFieldsName.Account_Fields);
								}
								ThreadSleep(1000);

								if (click(driver, home.getCreateFundraisingwrenchIconCancelBtn(10), "Cancel Btn", action.BOOLEAN)) {
									log(LogStatus.INFO,"click on Wrench Icon Cancel Btn",YesNo.No);
								} else {
									log(LogStatus.FAIL,"Not able to click on Wrench Icon Cancel Btn",YesNo.Yes);
									sa.assertTrue(false, "Not able to click on Wrench Icon Cancel Btn");
								}
								ThreadSleep(1000);
							}else{
								log(LogStatus.FAIL,"Not able to click on Wrench Icon",YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Wrench Icon");
							}
							String [] ss = {"Birthdate","Firm Phone"};
							if(market.addAndRemoveCloumnInSelectProspectGrid(mode,PageName.CreateFundraisingPage,ss, null, null,true).isEmpty()) {
								log(LogStatus.PASS, "column added form column to display popup", YesNo.No);
								ThreadSleep(2000);
								if(compareMultipleList(driver, "Birthdate"+","+"Firm Phone", market.getSelectProspectsHeaderTextList(PageName.CreateFundraisingPage)).isEmpty()) {
									log(LogStatus.PASS, "Selected Prospects Header Text is verified ", YesNo.No);
								}else {
									appLog.error("Selected Prospects Header Text is not verified");
									log(LogStatus.FAIL,"Selected Prospects Header Text is not verified",YesNo.Yes);
									sa.assertTrue(false, "Selected Prospects Header Text is not verified");
								}
								ThreadSleep(2000);
								if(market.columnToDisplayRevertToDefaultsSettings(PageName.CreateFundraisingPage,mode)) {
									log(LogStatus.PASS, "column to display settings is revert to default successfully", YesNo.No);
								}else {
									appLog.error("column to display settings is not revert to default");
									log(LogStatus.FAIL,"column to display settings is not revert to default",YesNo.Yes);
									sa.assertTrue(false,"column to display settings is not revert to default");
								}
								if(!compareMultipleList(driver, "Birthdate"+","+"Firm Phone", market.getSelectProspectsHeaderTextList(PageName.CreateFundraisingPage)).isEmpty()) {
									log(LogStatus.PASS, "Select Prospects Header Text is removed", YesNo.No);
								}else {
									appLog.error("Select Prospects Header Text is not removed");
									log(LogStatus.FAIL,"Select Prospects Header Text is not removed",YesNo.Yes);
									sa.assertTrue(false, "Select Prospects Header Text is not removed");
								}
							}else {
								appLog.error("Not able to add column form column to display popup");
								log(LogStatus.FAIL,"Not able to add column form column to display popup",YesNo.Yes);
								sa.assertTrue(false, "Not able to add column form column to display popup");
							}
						}
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on select fund continue button so cannot create fundraising", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on select fund continue button so cannot create fundraising");
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on select fund Name from lookup popup", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on select fund Name from lookup popup");
			}
		} else {
			appLog.error("Not able to click on create fundraising so cannot verify add filter logic");
			sa.assertTrue(false,"Not able to click on create fundraising so cannot verify add filter logic");
			log(LogStatus.SKIP,"Not able to click on create fundraising so cannot verify add filter logic",YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc014_verifyCoInvestmentSetting(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer nsp = new NavatarSetupPageBusinessLayer(driver);
		CoInvestmentSettingsTabBusinessLayer coinsp= new CoInvestmentSettingsTabBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		if(nsp.clickOnTab(environment, mode, TabName.NavatarSetup)) {
			if(nsp.clickOnNavatarSetupSideMenusTab(environment, NavatarSetupSideMenuTab.CoInvestmentSettings)) {
				ThreadSleep(2000);
				if(click(driver,nsp.getEditButtonforNavatarSetUpSideMenuTab(environment, NavatarSetupSideMenuTab.CoInvestmentSettings, 30),"edit button", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 10, nsp.getnavatarSetUpTabFrame_Lighting(environment, 10));
						
					}
					if(click(driver, nsp.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(environment, NavatarSetupSideMenuTab.CoInvestmentSettings, EditViewMode.Edit, 30), "Automatically link any Co-investment Fundraisings or Commitments to this Fund check box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Automatically link any Co-investment Fundraisings or Commitments to this Fund check box", YesNo.No);
						if(click(driver, nsp.getCoInvestmentFundLookUpIcon(20), "co investment look up icon", action.SCROLLANDBOOLEAN)) {
							if(nsp.selectValueFromLookUpWindow(Smoke_Fund1)) {
								log(LogStatus.INFO, Smoke_Fund1+" fund Name is selected successfully", YesNo.No);
								if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									switchToFrame(driver, 10, nsp.getnavatarSetUpTabFrame_Lighting(environment, 10));
									
								}
								if(click(driver, nsp.getSaveButtonforNavatarSetUpSideMenuTab(environment, NavatarSetupSideMenuTab.CoInvestmentSettings, 30, TopOrBottom.TOP), "save button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on save button successfully", YesNo.Yes);
									if(nsp.getSelectCoInvestmentFundErrorMsg(20) != null);
									String aa = nsp.getSelectCoInvestmentFundErrorMsg(20).getText().trim();
									if(aa.equalsIgnoreCase(NavatarSetUpPageErrorMessage.selectCoInvestmentErrorMsg)) {
										log(LogStatus.INFO, NavatarSetUpPageErrorMessage.selectCoInvestmentErrorMsg+" error message is verified", YesNo.No);
									}else {
										log(LogStatus.INFO, NavatarSetUpPageErrorMessage.selectCoInvestmentErrorMsg+" error message is not verified."+" \t Actual Result: "+aa, YesNo.No);
										sa.assertTrue(false, NavatarSetUpPageErrorMessage.selectCoInvestmentErrorMsg+" error message is not verified."+" \t Actual Result: "+aa);
									}
									if(click(driver, nsp.getCancelButtonforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.CoInvestmentSettings, 20), "cancel button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "click on cancel button", YesNo.No);
									}else {
										log(LogStatus.ERROR, "Not able to click on cancel button so cannot close co investment pop up", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on cancel button so cannot close co investment pop up");
									}
								}else {
									log(LogStatus.ERROR, "Not able to Click on save button so cannot check error message", YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on save button so cannot check error message");
								}
								if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									switchToDefaultContent(driver);
									
								}
								if(coinsp.activateCoInvestmentSetting(environment, mode, Smoke_Fund3)) {
									log(LogStatus.INFO, "co investment settings is activated for fund "+Smoke_Fund3, YesNo.No);
								}else {
									log(LogStatus.ERROR, "co investment settings is not activated for fund "+Smoke_Fund3, YesNo.Yes);
									sa.assertTrue(false, "co investment settings is not activated for fund "+Smoke_Fund3);
								}
							}else {
								log(LogStatus.ERROR, "Not able to select fund "+Smoke_Fund1+" from look up PopUp so cannot check error message", YesNo.Yes);
								sa.assertTrue(false, "Not able to select fund "+Smoke_Fund1+" from look up PopUp so cannot check error message");
							}
						}else {
							log(LogStatus.ERROR, "Not able to click on co investment look up icon so cannot check error message", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on co investment look up icon so cannot check error message");
						}
					}else {
						log(LogStatus.ERROR, "Not ale to click on Automatically link any Co-investment Fundraisings or Commitments to this Fund check box so cannot check error message",YesNo.Yes);
						sa.assertTrue(false, "Not ale to click on Automatically link any Co-investment Fundraisings or Commitments to this Fund check box so cannot check error message");
					}
				}else {
					log(LogStatus.ERROR,"Not able to click on co-investment edit button so cannot check error message", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on co-investment edit button so cannot check error message");
				}
				
			}else {
				log(LogStatus.ERROR, "Not able to click on co investment tab so cannot verify co investment setting", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on co investment tab so cannot verify co investment setting");
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on Navatar Setup Tab so cannot verify co investment setting", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on Navatar Setup Tab so cannot verify co investment setting");
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc015_verifyErrorMsgRelatedToOtherCoInvestmentFund(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer nsp = new NavatarSetupPageBusinessLayer(driver);
		CoInvestmentSettingsTabBusinessLayer coinsp= new CoInvestmentSettingsTabBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Fundraising.toString();
		if(home.ClickOnItemOnNavatarEdge(navigationMenuName, bulkActionNavigationLink, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "clicked on create fundraising link : ", YesNo.No);
			if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));

			}
			if(home.selectFundNameOrCompanyNameOnCreateFundraisings(environment,mode, PopUpName.selectFundPopUp, Smoke_Fund2, null)) {
				log(LogStatus.INFO, "Select Fund : "+Smoke_Fund2, YesNo.No);
				if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
				}
				if(click(driver, home.getSelectFundNamePopUpContinueBtn(), "continue button", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					String aa = null;
					if(home.getSelectCoInvestmentRelatedFundErrorMsg(20)!=null)
						 aa = home.getSelectCoInvestmentRelatedFundErrorMsg(20).getText().trim();
						if(aa.equalsIgnoreCase(HomePageErrorMessage.selectCoInvestmentRelatedFundErrorMsg)) {
							log(LogStatus.INFO, HomePageErrorMessage.selectCoInvestmentRelatedFundErrorMsg+" is verified successfully", YesNo.No);
						}else {
							log(LogStatus.ERROR, HomePageErrorMessage.selectCoInvestmentRelatedFundErrorMsg+" : error message is not verified ", YesNo.Yes);
							sa.assertTrue(false, HomePageErrorMessage.selectCoInvestmentRelatedFundErrorMsg+" : error message is not verified ");
						}
					if(click(driver, home.getSelectFundNamePopUpCancelBtn(20), "cancel button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Cancel buton", YesNo.No);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on select fund continue button so cannot check error message", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on select fund continue button so cannot check error message");
				}
			}else {
				log(LogStatus.ERROR, "Not able to select fund from select fund pop up so cannot check error message", YesNo.Yes);
				sa.assertTrue(false, "Not able to select fund from select fund pop up so cannot check error message");
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on create fundraising link so cannot check error message", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on create fundraising link so cannot check error message");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc016_1_createFundraisingFromCompanyPage(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		List<String> contactNamelist= new ArrayList<String>();
		contactNamelist.add(SmokeC1_FName+" "+SmokeC1_LName);
		contactNamelist.add(SmokeC3_FName+" "+SmokeC3_LName);
		contactNamelist.add(SmokeC4_FName+" "+SmokeC4_LName);
		List<String> accountlist= new ArrayList<String>();
		accountlist.add(SmokeINS1);
		accountlist.add(SmokeINDINV1);
		accountlist.add(SmokeINDINV1);
		List<String> Instituon1ContactName= new ArrayList<String>();
		Instituon1ContactName.add(SmokeC2_FName+" "+SmokeC2_LName);
		Instituon1ContactName.add(SmokeC1_FName+" "+SmokeC1_LName);
		List<String> Institution1List= new ArrayList<String>();
		Institution1List.add(SmokeINS1);
		Institution1List.add(SmokeINS1);
		List<String> actionsList= new ArrayList<String>();
		actionsList.add(SmokeC2_FName+" "+SmokeC2_LName+"<break>"+fundraisingContactActions.Remove.toString());
		actionsList.add(SmokeC1_FName+" "+SmokeC1_LName+"<break>"+fundraisingContactActions.PrimaryContact.toString());
		actionsList.add(SmokeC1_FName+" "+SmokeC1_LName+"<break>"+fundraisingContactActions.Role_DecisionMaker.toString());
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Fundraising.toString();
		////////////////////////////////////////////////////////////////////////////////
		boolean flag = false;
		switchToDefaultContent(driver);
		if(home.ClickOnItemOnNavatarEdge(navigationMenuName, bulkActionNavigationLink, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "clicked on create fundraising link : ", YesNo.No);
			if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));

			}
			if(home.selectFundNameOrCompanyNameOnCreateFundraisings(environment,mode, PopUpName.selectFundPopUp, Smoke_Fund3, null)) {
				log(LogStatus.INFO, "Select Fund : "+Smoke_Fund3, YesNo.No);
				if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
				}
				if(click(driver, home.getSelectFundNamePopUpContinueBtn(), "continue button", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					if(click(driver, home.getSearchBasedOnAccountsAndContactsTab(30), "Search Based On Accounts And Contacts Tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "click on Search Based On Accounts And Contacts Tab", YesNo.No);
						ThreadSleep(3000);
						if(home.applyFilterOnSearchBasedOnAccountsandContacts( FundraisingContactPageTab.SearchBasedOnAccountsAndContacts, SearchBasedOnExistingFundsOptions.AllContacts, environment,mode, null, "Account:Legal Name", "not equal to", "", null)) {
							log(LogStatus.INFO, "apply filter logic", YesNo.No);

							click(driver, home.getSearchBasedOnAccountsAndContactsSearchBtn(30), "search button", action.SCROLLANDBOOLEAN);
							if(home.selectInvestorsContactFromCreateFundRaising(contactNamelist,accountlist).isEmpty()) {
								log(LogStatus.INFO, "contact name is selected successfully",YesNo.No);
								if(click(driver, home.getAddToFundraisingListBtn(30), "Add To Fundraising List Button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "click on Add To Fundraising List", YesNo.No);
									if(home.verifyContactNameInReviewInvestorList(contactNamelist, accountlist).isEmpty()) {
										log(LogStatus.INFO, "Contact Name is verified in review investor list", YesNo.No);
										ThreadSleep(2000);
										if(click(driver, home.getCreateFundraisingBtn(PageName.CreateFundraisingPage, 30), "create fundraising button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on create fundraising button", YesNo.No);

											///////////////////////////
											if(home.selectFundNameOrCompanyNameOnCreateFundraisings(environment, mode,PopUpName.WarningPopUp, null, SmokeCOM2)) {
												log(LogStatus.INFO, "select company name "+SmokeCOM2, YesNo.No);
												if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
													switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));
												}
												if(click(driver, home.getCreateFundraisingBtn(PageName.WarningPopUp, 30), "create fundraising button", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "clicked on create fundraising button", YesNo.No);
													if(click(driver,home.getCreateFundraisingConfirmationOkBtn(30), "ok button", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO, "clicked on OK button", YesNo.No);	
														log(LogStatus.INFO, "clicked on OK button", YesNo.No);

														ExcelUtils.writeData(smokeFilePath,SmokeINS1+" - "+Smoke_Fund3, "Fundraisings",excelLabel.Variable_Name, "SmokeFR5",excelLabel.FundRaising_Name);
														ExcelUtils.writeData(smokeFilePath,Smoke_Fund3, "Fundraisings",excelLabel.Variable_Name, "SmokeFR5",excelLabel.Fund_Name);
														ExcelUtils.writeData(smokeFilePath,SmokeINS1, "Fundraisings",excelLabel.Variable_Name, "SmokeFR5",excelLabel.Legal_Name);
														ExcelUtils.writeData(smokeFilePath,"1", "Fundraisings",excelLabel.Variable_Name, "SmokeFR5",excelLabel.Total_Fundraising_Contacts);
														ExcelUtils.writeData(smokeFilePath,SmokeCOM2, "Fundraisings",excelLabel.Variable_Name, "SmokeFR5",excelLabel.Company_Name);
														ExcelUtils.writeData(smokeFilePath,SmokeINDINV1+" - "+Smoke_Fund3, "Fundraisings",excelLabel.Variable_Name, "SmokeFR6",excelLabel.FundRaising_Name);
														ExcelUtils.writeData(smokeFilePath,Smoke_Fund3, "Fundraisings",excelLabel.Variable_Name, "SmokeFR6",excelLabel.Fund_Name);
														ExcelUtils.writeData(smokeFilePath,SmokeINDINV1, "Fundraisings",excelLabel.Variable_Name, "SmokeFR6",excelLabel.Legal_Name);
														ExcelUtils.writeData(smokeFilePath,"2", "Fundraisings",excelLabel.Variable_Name, "SmokeFR6",excelLabel.Total_Fundraising_Contacts);
														ExcelUtils.writeData(smokeFilePath,SmokeCOM2, "Fundraisings",excelLabel.Variable_Name, "SmokeFR6",excelLabel.Company_Name);
														flag=true;
													}else {
														log(LogStatus.ERROR, "Not able to click on create fundraising button so warniing pop up", YesNo.Yes);
														sa.assertTrue(false, "Not able to click on create fundraising button so warniing pop up");
													}
												}else {
													log(LogStatus.ERROR, "Not able to click on create fundraising button so warniing pop up", YesNo.Yes);
													sa.assertTrue(false, "Not able to click on create fundraising button so warniing pop up");
												}
											}else {
												log(LogStatus.ERROR, "Not able to select company name "+SmokeCOM2, YesNo.Yes);
												sa.assertTrue(false, "Not able to select company name "+SmokeCOM2);
											} 
											/////////////////////////////////


										}else {
											log(LogStatus.ERROR, "Not able to click on create fundraising button so cannot create fundraisings", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on create fundraising button so cannot create fundraisings");
										}


									}else {
										log(LogStatus.ERROR, "Contact Name is not verified in review investor list so cannot create fundraising",YesNo.Yes);
										sa.assertTrue(false, "Contact Name is not verified in review investor list so cannot create fundraising");
									}
								}else {
									log(LogStatus.ERROR, "Not able to click on Add To Fundraising List Button so cannot create fundraising", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on Add To Fundraising List Button so cannot create fundraising");
								}
							}else {
								log(LogStatus.ERROR, " Not able to select Contact Name from select investor grid so cannot create fundraising", YesNo.Yes);
								sa.assertTrue(false, " Not able to select Contact Name from select investor grid so cannot create fundraising");
							}

						}else {
							log(LogStatus.ERROR, "Not able to apply filter logic so cannot verify create fundraising page", YesNo.Yes);
							sa.assertTrue(false, "Not able to apply filter logic so cannot verify create fundraising page");
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on Search Based On Accounts And Contacts Tab so cannot verify create fundraising page", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Search Based On Accounts And Contacts Tab so cannot verify create fundraising page");
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on select fund continue button so cannot create fundraising", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on select fund continue button so cannot create fundraising");
				}
			}else {
				log(LogStatus.ERROR, "Not able to select fund from select fund pop up so cannot verify create fundraising page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select fund from select fund pop up so cannot verify create fundraising page");
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on create fundraising link so cannot verify create fundraising page : ", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on create fundraising link so cannot verify create fundraising page : ");
		}

		switchToDefaultContent(driver);

		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc016_2_createFundraisingFromCompanyPage(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		List<String> contactNamelist= new ArrayList<String>();
		contactNamelist.add(SmokeC1_FName+" "+SmokeC1_LName);
		contactNamelist.add(SmokeC3_FName+" "+SmokeC3_LName);
		contactNamelist.add(SmokeC4_FName+" "+SmokeC4_LName);
		List<String> accountlist= new ArrayList<String>();
		accountlist.add(SmokeINS1);
		accountlist.add(SmokeINDINV1);
		accountlist.add(SmokeINDINV1);
		List<String> Instituon1ContactName= new ArrayList<String>();
		Instituon1ContactName.add(SmokeC2_FName+" "+SmokeC2_LName);
		Instituon1ContactName.add(SmokeC1_FName+" "+SmokeC1_LName);
		List<String> Institution1List= new ArrayList<String>();
		Institution1List.add(SmokeINS1);
		Institution1List.add(SmokeINS1);
		List<String> actionsList= new ArrayList<String>();
		actionsList.add(SmokeC2_FName+" "+SmokeC2_LName+"<break>"+fundraisingContactActions.Remove.toString());
		actionsList.add(SmokeC1_FName+" "+SmokeC1_LName+"<break>"+fundraisingContactActions.PrimaryContact.toString());
		actionsList.add(SmokeC1_FName+" "+SmokeC1_LName+"<break>"+fundraisingContactActions.Role_DecisionMaker.toString());
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		switchToDefaultContent(driver);
		String[] FR= {Smoke_FR5,Smoke_FR6};
		String id="";
		for(int i=0; i<FR.length; i++) {
			if(home.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
				if(frpg.clickOnCreatedFundRaising(environment, mode,FR[i])) {
					if(frpg.clickOnRelatedList(environment, mode, RecordType.Fundraising, RelatedList.Fundraising_Contacts, RelatedTab.Fundraising_Contacts.toString())) {
						if(i==0) {
							
							
							if(frpg.verifyfundraisingContacts("",SmokeC1_FName+" "+SmokeC1_LName,SmokeINS1,SmokeFRC7_Role,SmokeFRC7_Email)) {
								log(LogStatus.INFO, SmokeC1_FName+" "+SmokeC1_LName+"  contact details are verified on Fundraising "+FR[i], YesNo.No);
							}else {
								log(LogStatus.INFO, SmokeC1_FName+" "+SmokeC1_LName+" contact details are not verified on Fundraising "+FR[i], YesNo.No);
								sa.assertTrue(false,SmokeC1_FName+" "+SmokeC1_LName+" contact details are not verified on Fundraising "+FR[i]);
							}
						}
						if(i==1) {
							
							
							if(frpg.verifyfundraisingContacts("",SmokeC3_FName+" "+SmokeC3_LName,SmokeINDINV1,SmokeFRC8_Role,SmokeFRC8_Email)) {
								log(LogStatus.INFO, SmokeC3_FName+" "+SmokeC3_LName+"  contact details are verified on Fundraising "+FR[i], YesNo.No);
							}else {
								log(LogStatus.INFO, SmokeC3_FName+" "+SmokeC3_LName+" contact details are not verified on Fundraising "+FR[i], YesNo.No);
								sa.assertTrue(false,SmokeC3_FName+" "+SmokeC3_LName+" contact details are not verified on Fundraising "+FR[i]);
							}
							
							if(frpg.verifyfundraisingContacts("",SmokeC4_FName+" "+SmokeC4_LName,SmokeINDINV1,SmokeFRC9_Role,SmokeFRC9_Email)) {
								log(LogStatus.INFO, SmokeC4_FName+" "+SmokeC4_LName+"  contact details are verified on Fundraising "+FR[i], YesNo.No);
							}else {
								log(LogStatus.INFO, SmokeC4_FName+" "+SmokeC4_LName+" contact details are not verified on Fundraising "+FR[i], YesNo.No);
								sa.assertTrue(false,SmokeC4_FName+" "+SmokeC4_LName+" contact details are not verified on Fundraising "+FR[i]);
							}
						}

					}else {
						log(LogStatus.ERROR, "Not able to click on related list tab in lightning mode so cannot get contact id "+SmokeC3_FName+" "+SmokeC3_LName+","+SmokeC4_FName+" "+SmokeC4_LName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on related list tab in lightning mode so cannot get contact id "+SmokeC3_FName+" "+SmokeC3_LName+","+SmokeC4_FName+" "+SmokeC4_LName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on create fundraising "+Smoke_FR4+" so cannot get contact id "+SmokeC3_FName+" "+SmokeC3_LName+","+SmokeC4_FName+" "+SmokeC4_LName, YesNo.Yes);
					sa.assertTrue(false,  "Not able to click on create fundraising "+Smoke_FR4+" so cannot get contact id "+SmokeC3_FName+" "+SmokeC3_LName+","+SmokeC4_FName+" "+SmokeC4_LName);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on fundraising tab so cannot get contact id from "+Smoke_FR4, YesNo.Yes);
				sa.assertTrue(false,"Not able to click on fundraising tab so cannot get contact id from "+Smoke_FR4);
			}
		}

		switchToDefaultContent(driver);

		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
		
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc017_1_verifyCancelNextPreviousOnEmailFundraisingContacts(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		for(int j=0; j<=7; j++) {
			switchToDefaultContent(driver);
		if(fund.clickOnTab(environment, mode, TabName.FundsTab)) {
			log(LogStatus.INFO, "clicked on Fund Tab : "+j, YesNo.No);
			if(fund.clickOnCreatedFund(environment, mode, Smoke_Fund1)) {
				log(LogStatus.INFO, j+" : clicked on Fund : : "+Smoke_Fund1, YesNo.No);
					if(fund.clickOnRelatedList(environment, mode, RecordType.Fund, RelatedList.Fundraising_Contacts, RelatedTab.Fundraising_Contact.toString())) {
						log(LogStatus.INFO, j+" : clicked on Fundraisings", YesNo.No);
						ThreadSleep(2000);
						windowScrollYAxis(driver, 0, 500);
						if(click(driver,fund.getEmailFundraisingContactsBtn(environment, mode, 30), "email fundraising contact button", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(30000);
							if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 20, market.getEmailFundRaisingContact_Lightning(20));
							}
							List<String> result =market.selectProspectsContactAndVerifyReviewProspectListInEmailProspect(PageName.emailFundraisingContact,ContactAndAccountName, searchContactInEmailProspectGrid.No);
							if(j==0) {
								if(result.isEmpty()) {
									log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
									for(int i=0; i<=3; i++) {
										if(i==0) {
											if(click(driver, market.getStep1NextBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"Top Next button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on top next button", YesNo.No);
											}else {
												log(LogStatus.ERROR,"Not able to click on top next button", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on top next button");
											}
										}
										if(i==1) {
											if(click(driver, market.getStep2PreviousBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"Top previous button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on top previous button", YesNo.No);
											}else {
												log(LogStatus.ERROR,"Not able to click on top previous button", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on top previous button");
											}
										}
										if(i==2) {
											if(click(driver, market.getStep1NextBtn(PageName.emailFundraisingContact, TopOrBottom.BOTTOM,10),"bottom Next button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on BOTTOM next button", YesNo.No);
											}else {
												log(LogStatus.ERROR,"Not able to click on BOTTOM next button", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on BOTTOM next button");
											}
										}
										
										if(i==3) {
											if(click(driver, market.getStep2PreviousBtn(PageName.emailFundraisingContact, TopOrBottom.BOTTOM,10),"bottom previous button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on BOTTOM previous button", YesNo.No);
											}else {
												log(LogStatus.ERROR,"Not able to click on BOTTOM previous button", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on BOTTOM previous button");
											}
										}
										if(i==0 && i==2) {
											ThreadSleep(2000);
											WebElement ele = fd.getStep2TextEmailing(30);
											if (ele!=null) {
												String msg = ele.getText().trim();
												if (HomePageErrorMessage.Step2_SelectAnEmailTemplate.equals(msg)) {
													log(LogStatus.PASS, "Step2 Page Verified : "+msg, YesNo.No);
												} else {
													sa.assertTrue(false, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate);
													log(LogStatus.FAIL, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate, YesNo.Yes);
												}
											} else {
												sa.assertTrue(false, "Step2 Page Element is null");
												log(LogStatus.FAIL, "Step2 Page Element is null", YesNo.Yes);
												
											}
										}
										
									}
								}else {
									log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button", YesNo.Yes);
									sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button");
								}
							}
							if(j==1) {
								if(result.isEmpty()) {
									log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
									if(click(driver, market.getStep1CancelBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"Top cancel button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top cancel button step 1", YesNo.No);
									}else {
										log(LogStatus.ERROR,"Not able to click on top cancel button step 1", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top cancel button step 1");
									}
								}else {
									log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot Top Cancel bottom on step 1", YesNo.Yes);
									sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot Top Cancel bottom on step 1");
								}
							}
							if(j==2) {
								if(result.isEmpty()) {
									log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
									if(click(driver, market.getStep1CancelBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"bottom cancel button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on bottom cancel button step 1", YesNo.No);
									}else {
										log(LogStatus.ERROR,"Not able to click on bottom cancel button step 1", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on bottom cancel button step 1");
									}
								}else {
									log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot bottom Cancel bottom on step 1", YesNo.Yes);
									sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot bottom Cancel bottom on step 1");
								}
							}
							if(j==3) {
								if(result.isEmpty()) {
									log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
									if(click(driver, market.getStep1NextBtn(PageName.emailFundraisingContact, TopOrBottom.BOTTOM,10),"bottom Next button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on BOTTOM next button", YesNo.No);
										ThreadSleep(2000);
										if(click(driver, market.getStep2CancelBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"top cancel button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on top cancel button step 2", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on top cancel button step 2", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on top cancel button step 2");
										}
										
										
									}else {
										log(LogStatus.ERROR,"Not able to click on BOTTOM next button so cannot check Top Cancel button on Step2", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on BOTTOM next button so cannot check Top Cancel button on Step2");
									}
								}else {
									log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check Top Cancel button on Step2", YesNo.Yes);
									sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check Top Cancel button on Step2");
								}
							}
							if(j==4) {
								if(result.isEmpty()) {
									log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
									if(click(driver, market.getStep1NextBtn(PageName.emailFundraisingContact, TopOrBottom.BOTTOM,10),"bottom Next button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on BOTTOM next button", YesNo.No);
										ThreadSleep(2000);
										if(click(driver, market.getStep2CancelBtn(PageName.emailFundraisingContact, TopOrBottom.BOTTOM,10),"bottom cancel button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on bottom cancel button step 2", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on bottom cancel button step 2", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on bottom cancel button step 2");
										}
									}else {
										log(LogStatus.ERROR,"Not able to click on BOTTOM next button so cannot check bottom Cancel button on Step2", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on BOTTOM next button so cannot check bottom Cancel button on Step2");
									}
								}else {
									log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check bottom Cancel button on Step2", YesNo.Yes);
									sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check bottom Cancel button on Step2");
								}
							}
							if(j==5) {
								if(result.isEmpty()) {
									log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
									if(click(driver, market.getStep1NextBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"Top Next button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top next button step 1", YesNo.No);
										ThreadSleep(2000);
										for(int i=0; i<=3; i++) {
											if(i==0) {
												if(market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
													log(LogStatus.ERROR, "Select Capital Call Notice template", YesNo.No);
													if(click(driver, market.getStep2NextBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);
													}else {
														log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
														sa.assertTrue(false, "Not able to click on top next button step 2");
													}
												}else {
													log(LogStatus.ERROR,"Not able to click select email template from step 2 so cannot check previous botton of step 3", YesNo.Yes);
													sa.assertTrue(false,"Not able to click select email template from step 2 so cannot check previous botton of step 3");
													break;
												}

											}
											if(i==1) {
												if(click(driver, market.getStep3PreviousBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"Top previous button step 3", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.ERROR, "clicked on top previous button step 3", YesNo.No);
												}else {
													log(LogStatus.ERROR,"Not able to click on top previous button step 3", YesNo.Yes);
													sa.assertTrue(false, "Not able to click on top previous button step 3");
												}
											}
											if(i==2) {
												if(click(driver, market.getStep2NextBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);

												}else {
													log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
													sa.assertTrue(false, "Not able to click on top next button step 2");
												}
											}

											if(i==3) {
												if(click(driver, market.getStep3PreviousBtn(PageName.emailFundraisingContact, TopOrBottom.BOTTOM,10),"bottom previous button step 3", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.ERROR, "clicked on bottom previous button step 3", YesNo.No);
												}else {
													log(LogStatus.ERROR,"Not able to click on bottom previous button step 3", YesNo.Yes);
													sa.assertTrue(false, "Not able to click on bottom previous button step 3");
												}
											}
											if(i==0 && i==2) {
												ThreadSleep(2000);
												WebElement ele = fd.getStep2TextEmailing(30);
												if (ele!=null) {
													String msg = ele.getText().trim();
													if (HomePageErrorMessage.step3_ReviewAndConfirm.equals(msg)) {
														log(LogStatus.PASS, "Step3 Page Verified : "+msg, YesNo.No);
													} else {
														sa.assertTrue(false, "Step3 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step3_ReviewAndConfirm);
														log(LogStatus.FAIL, "Step3 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step3_ReviewAndConfirm, YesNo.Yes);
													}
												} else {
													sa.assertTrue(false, "Step3 Page Element is null");
													log(LogStatus.FAIL, "Step3 Page Element is null", YesNo.Yes);

												}
											}

										}
									}else {
										log(LogStatus.ERROR,"Not able to click on top next button step 1 so cannot check previous botton of step3", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top next button step 1 so cannot check previous botton of step3");
									}
								}else {
									log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button", YesNo.Yes);
									sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button");
								}
							}
							if(j==6) {
								if(result.isEmpty()) {
									log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
									if(click(driver, market.getStep1NextBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"Top Next button step 1", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top next button step 1", YesNo.No);
										if(market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
											log(LogStatus.ERROR, "Select Capital Call Notice template", YesNo.No);
											if(click(driver, market.getStep2NextBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);
												ThreadSleep(2000);
												if(click(driver, market.getStep3CancelBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"top cancel button step 3", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.ERROR, "clicked on top cancel button step 3", YesNo.No);
												}else {
													log(LogStatus.ERROR,"Not able to click on top cancel button step 3", YesNo.Yes);
													sa.assertTrue(false, "Not able to click on top cancel button step 3");
												}
											}else {
												log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on top next button step 2");
											}
										}else {
											log(LogStatus.ERROR,"Not able to click select email template from step 2 so cannot check previous botton of step 3", YesNo.Yes);
											sa.assertTrue(false,"Not able to click select email template from step 2 so cannot check previous botton of step 3");
										}
									}else {
										log(LogStatus.ERROR,"Not able to click on top next button step 1 so cannot check previous botton of step3", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top next button step 1 so cannot check previous botton of step3");
									}
								}else {
									log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check step 3 cancel button", YesNo.Yes);
									sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check step 3 cancel button");
								}
							}
							if(j==7) {
								if(result.isEmpty()) {
									log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
									if(click(driver, market.getStep1NextBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"Top Next button step 1", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top next button step 1", YesNo.No);
										if(market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
											log(LogStatus.ERROR, "Select Capital Call Notice template", YesNo.No);
											if(click(driver, market.getStep2NextBtn(PageName.emailFundraisingContact, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);
												ThreadSleep(2000);
												if(click(driver, market.getStep3CancelBtn(PageName.emailFundraisingContact, TopOrBottom.BOTTOM,10),"buttom cancel button step 3", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.ERROR, "clicked on buttom cancel button step 3", YesNo.No);
												}else {
													log(LogStatus.ERROR,"Not able to click on buttom cancel button step 3", YesNo.Yes);
													sa.assertTrue(false, "Not able to click on buttom cancel button step 3");
												}
											}else {
												log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on top next button step 2");
											}
										}else {
											log(LogStatus.ERROR,"Not able to click select email template from step 2 so cannot check previous botton of step 3", YesNo.Yes);
											sa.assertTrue(false,"Not able to click select email template from step 2 so cannot check previous botton of step 3");
										}
										
									}else {
										log(LogStatus.ERROR,"Not able to click on top next button step 1 so cannot check previous botton of step3", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top next button step 1 so cannot check previous botton of step3");
									}
								}else {
									log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check step 3 cancel button", YesNo.Yes);
									sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check step 3 cancel button");
								}
							}
						}else {
							log(LogStatus.ERROR, "Not able to click on fundraising contact button so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.Yes);
							sa.assertTrue(false, "Not able to click on fundraising contact button so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on related list so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on related list so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
					}
					
			}else {
				log(LogStatus.ERROR, "Not able to click on fund "+Smoke_Fund1+" so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);
				sa.assertTrue(false, "Not able to click on fund "+Smoke_Fund1+" so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
			}
		}else {
			log(LogStatus.ERROR, j+" : Not able to click on fund tab so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);
			sa.assertTrue(false, j+" : Not able to click on fund tab so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
		}
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc017_2_EmailFundraisingContactsAndSendEMail(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		if(fund.clickOnTab(environment, mode, TabName.FundsTab)) {
			if(fund.clickOnCreatedFund(environment, mode, Smoke_Fund1)) {
				if(fund.clickOnRelatedList(environment, mode, RecordType.Fund, RelatedList.Fundraisings, RelatedTab.Fundraising_Contact.toString())) {
					ThreadSleep(2000);
					windowScrollYAxis(driver, 0, 500);
					if(click(driver,fund.getEmailFundraisingContactsBtn(environment, mode, 30), "email fundraising contact button", action.SCROLLANDBOOLEAN)) {
						if(market.EmailProspects(environment, mode,PageName.emailFundraisingContact, null, null,null,
								ContactAndAccountName, searchContactInEmailProspectGrid.Yes, "Capital Call Notice",
								"Capital Call Notice", "Use my signature", false)) {
							log(LogStatus.INFO, "Email has been send to contact "+SmokeC1_FName+" "+SmokeC1_LName+" successfully", YesNo.No);
						}else {
							log(LogStatus.ERROR, "Not able to send email to contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.Yes);
							sa.assertTrue(false, "Not able to send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
						}
						
					}else {
						log(LogStatus.ERROR, "Not able to click on fundraising contact button so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on fundraising contact button so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on related list so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on related list so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on fund "+Smoke_Fund1+" so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);
				sa.assertTrue(false, "Not able to click on fund "+Smoke_Fund1+" so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on fund tab so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);
			sa.assertTrue(false, "Not able to click on fund tab so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc018_verifyRecievedEmailAndCreatedActivity(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		String text = null;
		String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (contact.clickOnTab(environment, mode, TabName.ContactTab)) {
			log(LogStatus.INFO, "Clicked on Contact Tab",YesNo.No);
			if (contact.clickOnCreatedContact(environment, SmokeC1_FName, SmokeC1_LName)) {
				log(LogStatus.INFO, "Clicked on Created Contact : "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.No);	
				
				if (contact.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Activity_History, null)) {
					log(LogStatus.INFO, "Clicked on Activity History",YesNo.No);	
					
					if (contact.verifyCreatedActivityHistory(environment, mode, "Capital Call Notice  of Commitment")) {
						log(LogStatus.INFO, "Activity verified: "+"Capital Call Notice  of Commitment",YesNo.No);	
					} else {
						sa.assertTrue(false, "Activity not verified: "+"Capital Call Notice  of Commitment");
						log(LogStatus.SKIP, "Activity not verified: "+"Capital Call Notice  of Commitment",YesNo.Yes);
					}
				
				} else {
					sa.assertTrue(false, "Not on Activity History");
					log(LogStatus.SKIP, "Not on Activity History",YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false, "Contact not Found : "+SmokeC1_FName+" "+SmokeC1_LName);
				log(LogStatus.SKIP, "Contact not Found : "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);	
			}
			
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab",YesNo.Yes);	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		
		EmailLib email = new EmailLib();
		try {
			text = email.getEMailContent(gmailUserName, gmailPassword, crmUser1EmailID, SmokeC1_EmailID,
					"Capital Call Notice");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			sa.assertTrue(false, "Email Template is not verified from email : " + gmailUserName);
			log(LogStatus.ERROR, "Email Template is not verified from email : " + gmailUserName, YesNo.No);
			e.printStackTrace();
		}
		if (text != null) {
			if (text.contains(MarketingInitiativesPageErrorMsg.companyName)) {
				log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.companyName + " Company Name is verified.",
						YesNo.No);
			} else {
				sa.assertTrue(false, MarketingInitiativesPageErrorMsg.companyName + " Company Name is not verified.");
				log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.companyName + " Company Name is not verified.",
						YesNo.No);
			}
			if (lp.verifyDate(text, null, "email template")) {
				log(LogStatus.INFO, date + " Date is verified", YesNo.No);
			} else {
				sa.assertTrue(false, date + " Date is not verified");
				log(LogStatus.ERROR, date + " Date is not verified", YesNo.No);
			}

			if (text.contains(MarketingInitiativesPageErrorMsg.salutation(SmokeC1_FName))) {
				log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.salutation(SmokeC1_FName) + " is verified",
						YesNo.No);
			} else {
				sa.assertTrue(false, MarketingInitiativesPageErrorMsg.salutation(SmokeC1_FName) + " is not verified");
				log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.salutation(SmokeC1_FName) + " is not verified",
						YesNo.No);
			}
			if (text.contains(MarketingInitiativesPageErrorMsg.ErrorMsg1)) {
				log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.ErrorMsg1 + " is verified", YesNo.No);
			} else {
				sa.assertTrue(false, MarketingInitiativesPageErrorMsg.ErrorMsg1 + " is not verified");
				log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.ErrorMsg1 + " is not verified", YesNo.No);
			}

			if (text.contains(MarketingInitiativesPageErrorMsg.ErrorMsg2)) {
				log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.ErrorMsg2 + " is verified", YesNo.No);
			} else {
				sa.assertTrue(false, MarketingInitiativesPageErrorMsg.ErrorMsg2 + " is not verified");
				log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.ErrorMsg2 + " is not verified", YesNo.No);
			}
			if (text.contains(MarketingInitiativesPageErrorMsg.ErrorMsg3)) {
				log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.ErrorMsg3 + " is verified", YesNo.No);
			} else {
				sa.assertTrue(false, MarketingInitiativesPageErrorMsg.ErrorMsg3 + " is not verified");
				log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.ErrorMsg3 + " is not verified", YesNo.No);
			}
			if (text.contains(MarketingInitiativesPageErrorMsg.Sincerely)) {
				log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.Sincerely + " is verified", YesNo.No);
			} else {
				sa.assertTrue(false, MarketingInitiativesPageErrorMsg.Sincerely + " is not verified");
				log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.Sincerely + " is not verified", YesNo.No);
			}
			if (text.contains(crmUser1FirstName + " " + crmUser1LastName)) {
				log(LogStatus.INFO, crmUser1FirstName + " " + crmUser1LastName + " is verified", YesNo.No);
			} else {
				sa.assertTrue(false, crmUser1FirstName + " " + crmUser1LastName + " is not verified");
				log(LogStatus.ERROR, crmUser1FirstName + " " + crmUser1LastName + " is not verified", YesNo.No);
			}

		} else {
			sa.assertTrue(false, "Capital Call Notice template is not availble on email " + gmailUserName
					+ " so cannot verify send email text");
			log(LogStatus.ERROR, "Capital Call Notice template is not availble on email " + gmailUserName
					+ " so cannot verify send email text", YesNo.No);
		}
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		if(fund.clickOnTab(environment, mode, TabName.FundsTab)) {
			if(fund.clickOnCreatedFund(environment, mode, Smoke_Fund1)) {
				appLog.info("clicked on Fund : " + Smoke_Fund1);
				if(fund.clickOnRelatedList(environment, mode, RecordType.Fund, RelatedList.Fundraising_Contacts, RelatedTab.Fundraising_Contact.toString())) {
					if(click(driver,fund.getEmailFundraisingContactsBtn(environment, mode, 30), "email fundraising contact button", action.SCROLLANDBOOLEAN)) {
						if(mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 20, fund.getEmailFundraisingContactFrame_Lightning(20));
						}
						ThreadSleep(3000);
						windowScrollYAxis(driver, 0, 500);
						List<WebElement> lst = market.getemailProspectContentGrid("Email Sent", 20);
							if (!lst.isEmpty()) {
								String ActualDate = lst.get(0).getText().trim();
								if (market.verifyDate(ActualDate, null, "Last Mass Email")) {
									log(LogStatus.INFO,"Email Sent Date is verifed of contact" + SmokeC1_FName + " " + SmokeC1_LName,YesNo.No);
								} else {
									sa.assertTrue(false,"Email Sent Date is verifed of contact" + SmokeC1_FName + " " + SmokeC1_LName);
									log(LogStatus.ERROR,"Email Sent Date is verifed of contact" + SmokeC1_FName + " " + SmokeC1_LName,YesNo.Yes);
								}
							} else {
								sa.assertTrue(false,"Email Sent Column is not visible in fundraising content grid so cannot verify "+ SmokeC1_FName + " " + SmokeC1_LName + "Email Sent");
								log(LogStatus.ERROR,"Email Sent Column is not visible in fundraising content grid so cannot verify "+ SmokeC1_FName + " " + SmokeC1_LName + "Email Sent",YesNo.Yes);
							}
					}else {
						log(LogStatus.ERROR, "Not able to click on fundraising contact button so cannot check activity of contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on fundraising contact button so cannot check activity of contact "+SmokeC1_FName+" "+SmokeC1_LName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on related list so cannot check activity of contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on related list so cannot check activity of contact "+SmokeC1_FName+" "+SmokeC1_LName);
				}
			}else {
				log(LogStatus.SKIP, "Not able to click on fund "+Smoke_Fund1+" so cannot check email fundraising activities of contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on fund "+Smoke_Fund1+" so cannot check email fundraising activities of contact "+SmokeC1_FName+" "+SmokeC1_LName);
			}
		}else {
			log(LogStatus.SKIP, "Not able to click on fund tab so cannot check email fundraising activities of contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on fund tab so cannot check email fundraising activities of contact "+SmokeC1_FName+" "+SmokeC1_LName);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc019_SendCustomEMailFromEmailFundraisingContact(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC3_FName + " " + SmokeC3_LName, SmokeINDINV1);
		if(fund.clickOnTab(environment, mode, TabName.FundsTab)) {
			if(fund.clickOnCreatedFund(environment, mode, Smoke_Fund3)) {
				if(fund.clickOnRelatedList(environment, mode, RecordType.Fund, RelatedList.Fundraisings, RelatedTab.Fundraising_Contact.toString())) {
					if(click(driver,fund.getEmailFundraisingContactsBtn(environment, mode, 30), "email fundraising contact button", action.SCROLLANDBOOLEAN)) {
						if(market.EmailProspects(environment, mode,PageName.emailFundraisingContact, "Contact:Last Name", "equals",SmokeC3_LName,
								ContactAndAccountName, searchContactInEmailProspectGrid.No,EmailTemplate1_FolderName,
								EmailTemplate1_TemplateName, null, false)) {
							log(LogStatus.INFO, "Email has been send to contact "+SmokeC3_FName+" "+SmokeC3_LName+" successfully", YesNo.No);
						}else {
							log(LogStatus.ERROR, "Not able to send email to contact "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
							sa.assertTrue(false, "Not able to send email to contact "+SmokeC3_FName+" "+SmokeC3_LName);
						}
						
					}else {
						log(LogStatus.ERROR, "Not able to click on fundraising contact button so cannot send email to contact "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on fundraising contact button so cannot send email to contact "+SmokeC3_FName+" "+SmokeC3_LName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on related list so cannot send email to contact "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on related list so cannot send email to contact "+SmokeC3_FName+" "+SmokeC3_LName);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on fund "+Smoke_Fund1+" so cannot send email to contact "+SmokeC3_FName+" "+SmokeC3_LName,YesNo.Yes);
				sa.assertTrue(false, "Not able to click on fund "+Smoke_Fund1+" so cannot send email to contact "+SmokeC3_FName+" "+SmokeC3_LName);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on fund tab so cannot send email to contact "+SmokeC3_FName+" "+SmokeC3_LName,YesNo.Yes);
			sa.assertTrue(false, "Not able to click on fund tab so cannot send email to contact "+SmokeC3_FName+" "+SmokeC3_LName);
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc020_1_verifyRecievedCustomEmailAndCreatedActivityforContact3(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		String text = null;
		String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (contact.clickOnTab(environment, mode, TabName.ContactTab)) {
			log(LogStatus.INFO, "Clicked on Contact Tab",YesNo.No);
			if (contact.clickOnCreatedContact(environment,  SmokeC3_FName, SmokeC3_LName)) {
				log(LogStatus.INFO, "Clicked on Created Contact : "+SmokeC3_FName+" "+SmokeC3_LName,YesNo.No);	
				
				if (contact.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Activity_History, null)) {
					log(LogStatus.INFO, "Clicked on Activity History",YesNo.No);	
					
					if (contact.verifyCreatedActivityHistory(environment, mode, EmailTemplate1_Subject)) {
						log(LogStatus.INFO, "Activity verified: "+EmailTemplate1_Subject,YesNo.No);	
					} else {
						sa.assertTrue(false, "Activity not verified: "+EmailTemplate1_Subject);
						log(LogStatus.SKIP, "Activity not verified: "+EmailTemplate1_Subject,YesNo.Yes);
					}
				
				} else {
					sa.assertTrue(false, "Not on Activity History");
					log(LogStatus.SKIP, "Not on Activity History",YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false, "Contact not Found : "+SmokeC3_FName+" "+SmokeC3_LName);
				log(LogStatus.SKIP, "Contact not Found : "+SmokeC3_FName+" "+SmokeC3_LName,YesNo.Yes);	
			}
			
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab",YesNo.Yes);	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		
		EmailLib email = new EmailLib();
		try {
			text = email.getEMailContent(gmailUserName, gmailPassword, crmUser1EmailID, SmokeC3_EmailID,
					EmailTemplate1_Subject);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			sa.assertTrue(false, "Email Template is not verified from email : " + gmailUserName);
			log(LogStatus.ERROR, "Email Template is not verified from email : " + gmailUserName, YesNo.No);
			e.printStackTrace();
		}
		if (text != null) {
			if (text.contains(EmailTemplate1_Body)) {
				log(LogStatus.INFO, EmailTemplate1_Body + " body text is verified.", YesNo.No);
			} else {
				sa.assertTrue(false, EmailTemplate1_Body + " body text is verified.");
				log(LogStatus.ERROR, EmailTemplate1_Body + " body text is verified.", YesNo.No);
			}
		} else {
			sa.assertTrue(false, EmailTemplate1_Subject + " template is not availble on email " + gmailUserName
					+ " so cannot verify send email text");
			log(LogStatus.ERROR, EmailTemplate1_Subject + " template is not availble on email " + gmailUserName
					+ " so cannot verify send email text", YesNo.No);
		}
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC3_FName + " " + SmokeC3_LName, SmokeINDINV1);
		if(fund.clickOnTab(environment, mode, TabName.FundsTab)) {
			if(fund.clickOnCreatedFund(environment, mode, Smoke_Fund3)) {
				appLog.info("clicked on Fund : " + Smoke_Fund3);
				if(fund.clickOnRelatedList(environment, mode, RecordType.Fund, RelatedList.Fundraising_Contacts, RelatedTab.Fundraising_Contact.toString())) {
					if(click(driver,fund.getEmailFundraisingContactsBtn(environment, mode, 30), "email fundraising contact button", action.SCROLLANDBOOLEAN)) {
						if(mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 20, fund.getEmailFundraisingContactFrame_Lightning(20));
						}
						ThreadSleep(3000);
						windowScrollYAxis(driver, 0, 500);
						List<WebElement> lst = market.getemailProspectContentGrid("Email Sent", 20);
						if (!lst.isEmpty()) {
							String ActualDate = lst.get(1).getText().trim();
							if (market.verifyDate(ActualDate, null, "Last Mass Email")) {
								log(LogStatus.INFO,"Email Sent Date is verifed of contact" + SmokeC3_FName + " " + SmokeC3_LName,YesNo.No);
							} else {
								sa.assertTrue(false,"Email Sent Date is verifed of contact" + SmokeC3_FName + " " + SmokeC3_LName);
								log(LogStatus.ERROR,"Email Sent Date is verifed of contact" + SmokeC3_FName + " " + SmokeC3_LName,YesNo.Yes);
							}
						} else {
							sa.assertTrue(false,"Email Sent Column is not visible in fundraising content grid so cannot verify "+ SmokeC3_FName + " " + SmokeC3_LName + "Email Sent");
							log(LogStatus.ERROR,"Email Sent Column is not visible in fundraising content grid so cannot verify "+ SmokeC3_FName + " " + SmokeC3_LName + "Email Sent",YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on fundraising contact button so cannot check activity of contact "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on fundraising contact button so cannot check activity of contact "+SmokeC3_FName+" "+SmokeC3_LName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on related list so cannot check activity of contact "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on related list so cannot check activity of contact "+SmokeC3_FName+" "+SmokeC3_LName);
				}
			}else {
				log(LogStatus.SKIP, "Not able to click on fund "+Smoke_Fund3+" so cannot check email fundraising activities of contact "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on fund "+Smoke_Fund3+" so cannot check email fundraising activities of contact "+SmokeC3_FName+" "+SmokeC3_LName);
			}
		}else {
			log(LogStatus.SKIP, "Not able to click on fund tab so cannot check email fundraising activities of contact "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on fund tab so cannot check email fundraising activities of contact "+SmokeC3_FName+" "+SmokeC3_LName);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc020_2_verifyAddFilterLogicFundraisingContacts(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele=null;
		String msg = null;
		String parenTiD;
		String Xpath = null;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		if(fund.clickOnTab(environment, mode, TabName.FundsTab)) {
			if(fund.clickOnCreatedFund(environment, mode, Smoke_Fund1)) {
					if(fund.clickOnRelatedList(environment, mode, RecordType.Fund, RelatedList.Fundraisings, RelatedTab.Fundraising_Contact.toString())) {
						ThreadSleep(2000);
						windowScrollYAxis(driver, 0, 500);
						if(click(driver,fund.getEmailFundraisingContactsBtn(environment, mode, 30), "email fundraising contact button", action.SCROLLANDBOOLEAN)) {
							if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 20, market.getEmailFundRaisingContact_Lightning(20));
							}
							if(click(driver, market.getEmailProspectApplyBtn(30), "search button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR,"clicked on Search button successfully", YesNo.No);
								
								  ele = market.getPleaseSelectASearchCriteriaMsg(20);
									if (ele != null) {
										 msg = ele.getText().trim();
										if (msg.contains(MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage)) {
											appLog.info(MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage+ " error messgae is verified.");
										} else {
											sa.assertTrue(false, MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage+ " error messgae is not verified. Actual :" + msg);
											log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage+ " error messgae is not verified. Actual :" + msg, YesNo.Yes);
										}
										if (click(driver, market.getPleaseSelectASearchCriteriaPopUpOKBtn(PageName.emailFundraisingContact, 10), "ok button",
												action.SCROLLANDBOOLEAN)) {
											appLog.error("Select a criteria error message ok button");
											ThreadSleep(2000);
											
											 ele = market.getPleaseSelectASearchCriteriaMsg(5);
												if (ele == null) {
													log(LogStatus.INFO,"Select a search Criteria error is not visible after Click on OK Button",YesNo.No);	
												}else{
													sa.assertTrue(false,"Select a search Criteria error is visible after Click on OK Button");
													log(LogStatus.ERROR,"Select a search Criteria error is visible after Click on OK Button",YesNo.Yes);
												
												}
										} else {
											sa.assertTrue(false,"Select a search Criteria error message ok button so cannot close error message pop up");
											log(LogStatus.ERROR,"Select a search Criteria error message ok button so cannot close error message pop up",YesNo.Yes);
										}
									} else {
										sa.assertTrue(false,"Select a search Criteria error message is not visible so cannot verify error message");
										log(LogStatus.ERROR,"Select a search Criteria error message is not visible so cannot verify error message",YesNo.Yes);
									}
								
							}else {
								log(LogStatus.ERROR,"Not able to click on Search button so cannot check error message "+MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage, YesNo.Yes);
								sa.assertTrue(false, "Not able to click on Search button so cannot check error message "+MarketingInitiativesPageErrorMsg.SelectASearchCriteriaMessage);
							}
					
							if(click(driver, market.getAddRowLink(AddProspectsTab.AccountAndContacts), "account and contact tab", action.BOOLEAN)) {
								log(LogStatus.FAIL,"Clicked on Add row link",YesNo.No);
								ThreadSleep(2000);
								if(click(driver, home.getCreateFundraisingFilterRemoveBtn().get(0), "remove icon", action.BOOLEAN)) {
									log(LogStatus.FAIL,"clicked on remove row link",YesNo.No);
								}else {
									appLog.error("Not able to click on remove row link so cannot check remove functionality");
									log(LogStatus.FAIL,"Not able to click on remove row link so cannot check remove functionality",YesNo.Yes);
									sa.assertTrue(false,"Not able to click on remove row link so cannot check remove functionality");
								}
								ThreadSleep(2000);
							}else {
								appLog.error("Not able to click on Add row link so cannot check remove functionality");
								log(LogStatus.FAIL,"Not able to click on Add row link so cannot check remove functionality",YesNo.Yes);
								sa.assertTrue(false,"Not able to click on Add row link so cannot check remove functionality");
							}
							
							if (click(driver, market.getAddFilterLogicLinkOnEmailProspect(10), "Add Filter Logic Link", action.BOOLEAN)) {
								log(LogStatus.INFO, "clicked on Add Filter Logic Link", YesNo.No);
								ThreadSleep(1000);
								
								Xpath="pageid:frm:textfilt";
							
								ele=isDisplayed(driver,FindElement(driver, "//input[@id='"+Xpath+"']", "Add filter logic text box", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
								if(ele!=null) {
									log(LogStatus.INFO,"find add filter logic text box",YesNo.No);	
								}else {
									log(LogStatus.FAIL,"Not able find add filter logic text box",YesNo.Yes);
									sa.assertTrue(false, "Not able find add filter logic text box  ");
								}
								
								
								if (click(driver, market.getInfoLink(10), "Info Link", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"click on Info Link",YesNo.No);
									ThreadSleep(2000);
									parenTiD = switchOnWindow(driver);
									
									if (parenTiD!=null) {
										log(LogStatus.INFO,"New Window is open",YesNo.No);
										Xpath="//h1//*[text()='Add Filter Logic']";
										ele=isDisplayed(driver,FindElement(driver, Xpath, "Add filter logic Window", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
										if(ele!=null) {
											log(LogStatus.INFO,"Add Filter Logic Window is open",YesNo.No);	
										}else {
											log(LogStatus.FAIL,"Add Filter Logic Window is not open",YesNo.Yes);
											sa.assertTrue(false, "Add Filter Logic Window is not open");
										}
										driver.close();
										driver.switchTo().window(parenTiD);
									} else {
										log(LogStatus.FAIL,"No New Window is open",YesNo.Yes);
										sa.assertTrue(false, "No New Window is open");
									}
								} else {
									log(LogStatus.FAIL,"Not able to click on Info Link",YesNo.Yes);
									sa.assertTrue(false, "Not able to click on Info Link");
								}
								switchToDefaultContent(driver);
								if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									switchToFrame(driver, 20, market.getEmailFundRaisingContact_Lightning(20));
									}
								appLog.info("After All");
								for (int j = 0; j < 2; j++) {
									Xpath="AddRow2";
									ele=isDisplayed(driver,FindElement(driver, "//a[@id='"+Xpath+"']", "Add row button", action.BOOLEAN,10), "Visibility", 10, "Add row button");
									
									if(click(driver, ele, "add row button", action.BOOLEAN)) {
										log(LogStatus.INFO,"clicked on add row link : "+j,YesNo.No);
									}else {
										log(LogStatus.FAIL,"Not Able to click on add row link : "+j,YesNo.Yes);
										sa.assertTrue(false, "Not Able to click on add row link : "+j);	
									}
									ThreadSleep(2000);
								}
									
								if (click(driver, market.getEmailProspectClearBtn(10), "Clear Btn", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"click on Clear Btn",YesNo.No);	
								} else {
									log(LogStatus.FAIL,"Not able to click on Clear Btn",YesNo.Yes);
									sa.assertTrue(false, "Not able to click on Clear Btn");
								}
							} else {
								sa.assertTrue(false, "Not able to click on Add Filter Logic Link");
								log(LogStatus.ERROR, "Not able to click on Add Filter Logic Link", YesNo.Yes);
							}
							if(market.SearchforEmailProspects(environment, mode, PageName.emailFundraisingContact,"Account:Legal Name,Contact:Email,Contact:Legal Name","equals,equals,equals",SmokeINS1+","+SmokeC1_EmailID+","+SmokeINS1,"(1 AND 2) OR 3")) {
								appLog.info("Filter logic applied successfully ");
								ThreadSleep(5000);
								if(market.selectProspectsContactAndVerifyReviewProspectListInEmailProspect(PageName.emailFundraisingContact,ContactAndAccountName, searchContactInEmailProspectGrid.No).isEmpty()) {
									log(LogStatus.PASS, "Search Contacts is visible in Select Propects Grid ", YesNo.No);
								}else {
									appLog.error("Search Contacts is not visible in Select Propects Grid ");
									log(LogStatus.FAIL,"Search Contacts is not visible in Select Propects Grid ",YesNo.Yes);
									sa.assertTrue(false, "Search Contacts is not visible in Select Propects Grid ");
								}
								
								// Azhar Start
								
								if(click(driver, market.getSelectProspectsWrenchIcon(PageName.emailFundraisingContact,mode,60), "wrench icon", action.SCROLLANDBOOLEAN)) {
									ThreadSleep(1000);
									
									if(selectVisibleTextFromDropDown(driver, market.getColumnToDisplayViewDropDownList(PageName.emailFundraisingContact,30), "view drop down list", ViewFieldsName.Fundraising_Fields)) {
										log(LogStatus.INFO,"Select value from view drop down list : "+ViewFieldsName.Fundraising_Fields,YesNo.Yes);
										
									}else {
										log(LogStatus.FAIL,"Not able to select value from view drop down list : "+ViewFieldsName.Fundraising_Fields,YesNo.Yes);
										sa.assertTrue(false, "Not able to select value from view drop down list : "+ViewFieldsName.Fundraising_Fields);
									}
									ThreadSleep(1000);
									
									if(selectVisibleTextFromDropDown(driver, market.getColumnToDisplayViewDropDownList(PageName.emailFundraisingContact,30), "view drop down list", ViewFieldsName.Fundraising_Contact_Fields)) {
										log(LogStatus.INFO,"Select value from view drop down list : "+ViewFieldsName.Fundraising_Contact_Fields,YesNo.No);
										
									}else {
										log(LogStatus.FAIL,"Not able to select value from view drop down list : "+ViewFieldsName.Fundraising_Contact_Fields,YesNo.Yes);
										sa.assertTrue(false, "Not able to select value from view drop down list : "+ViewFieldsName.Fundraising_Contact_Fields);
									}
									ThreadSleep(1000);
									
									if (click(driver, market.getWrenchIconCancelBtn(10), "Cancel Btn", action.BOOLEAN)) {
										log(LogStatus.INFO,"click on Wrench Icon Cancel Btn",YesNo.No);
									} else {
										log(LogStatus.FAIL,"Not able to click on Wrench Icon Cancel Btn",YesNo.Yes);
										sa.assertTrue(false, "Not able to click on Wrench Icon Cancel Btn");
									}
									ThreadSleep(1000);
								}else{
									log(LogStatus.FAIL,"Not able to click on Wrench Icon",YesNo.Yes);
									sa.assertTrue(false, "Not able to click on Wrench Icon");
								}
								
								// Azhar End
								
								String [] ss = {"Created date"};
								if(market.addAndRemoveCloumnInSelectProspectGrid(mode,PageName.emailFundraisingContact,ss, null, null,true).isEmpty()) {
									log(LogStatus.PASS, "column added form column to display popup", YesNo.No);
									ThreadSleep(2000);
									if(compareMultipleList(driver, "Created date", market.getSelectProspectsHeaderTextList(PageName.emailFundraisingContact)).isEmpty()) {
										log(LogStatus.PASS, "Selected Prospects Header Text is verified ", YesNo.No);
									}else {
										appLog.error("Selected Prospects Header Text is not verified");
										log(LogStatus.FAIL,"Selected Prospects Header Text is not verified",YesNo.Yes);
										sa.assertTrue(false, "Selected Prospects Header Text is not verified");
									}
									ThreadSleep(2000);
									if(market.columnToDisplayRevertToDefaultsSettings(PageName.emailFundraisingContact,mode)) {
										log(LogStatus.PASS, "column to display settings is revert to default successfully", YesNo.No);
									}else {
										appLog.error("column to display settings is not revert to default");
										log(LogStatus.FAIL,"column to display settings is not revert to default",YesNo.Yes);
										sa.assertTrue(false,"column to display settings is not revert to default");
									}
									if(!compareMultipleList(driver,"Created date", market.getSelectProspectsHeaderTextList(PageName.emailFundraisingContact)).isEmpty()) {
										log(LogStatus.PASS, "Select Prospects Header Text is removed", YesNo.No);
									}else {
										appLog.error("Select Prospects Header Text is not removed");
										log(LogStatus.FAIL,"Select Prospects Header Text is not removed",YesNo.Yes);
										sa.assertTrue(false, "Select Prospects Header Text is not removed");
									}
								}else {
									appLog.error("Not able to add column form column to display popup");
									log(LogStatus.FAIL,"Not able to add column form column to display popup",YesNo.Yes);
									sa.assertTrue(false, "Not able to add column form column to display popup");
								}
							}else {
								appLog.error("Not able to apply filter login so cannot verify contact name and add columns in select prospect grid");
								log(LogStatus.FAIL, "Not able to apply filter login so cannot verify contact name and add columns in select prospect grid", YesNo.Yes);
								sa.assertTrue(false, "Not able to apply filter login so cannot verify contact name and add columns in select prospect grid");
							}
						
							
							
						}else {
							log(LogStatus.ERROR, "Not able to click on fundraising contact button so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.Yes);
							sa.assertTrue(false, "Not able to click on fundraising contact button so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on related list so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on related list so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
					}
					
			}else {
				log(LogStatus.ERROR, "Not able to click on fund "+Smoke_Fund1+" so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);
				sa.assertTrue(false, "Not able to click on fund "+Smoke_Fund1+" so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on fund tab so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);
			sa.assertTrue(false, "Not able to click on fund tab so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc021_verifyCommmitmentButtonOnPages(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		WebElement ele;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (market.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
			if (frpg.clickOnCreatedFundRaising(environment, mode, Smoke_FR1)) {
				appLog.info("clicked on FR : " + Smoke_FR1);
				ThreadSleep(5000);
				boolean flag=fund.clickOnShowMoreActionDownArrow(environment, PageName.FundraisingPage, ShowMoreActionDropDownList.Create_Commitments, 10);
					ThreadSleep(2000);
					if (flag) {
						log(LogStatus.INFO, "Clicked on Commitment Creation Button"+SmokeCOM1, YesNo.No);
						ThreadSleep(2000);
						if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));

						}
						if (click(driver, comm.getCommitmentCancelBtn(TopOrBottom.TOP, 10), "Commitment Creation Cancel Top Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Commitment Creation Cancel Top Button "+SmokeCOM1, YesNo.No);
							switchToDefaultContent(driver);
							 flag=fund.clickOnShowMoreActionDownArrow(environment, PageName.FundraisingPage, ShowMoreActionDropDownList.Create_Commitments, 10);
							ThreadSleep(2000);
							if (flag) {
								log(LogStatus.INFO, "Clicked on Commitment Creation Button"+SmokeCOM1, YesNo.No);
								ThreadSleep(2000);
								if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));

								}
								if (click(driver, comm.getCommitmentCancelBtn(TopOrBottom.BOTTOM, 10), "Commitment Creation Cancel BOTTOM Button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Commitment Creation Cancel BOTTOM Button "+SmokeCOM1, YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not Able to Click on Commitment Creation Cancel BOTTOM Button"+SmokeCOM1, YesNo.Yes);
									sa.assertTrue(false, "Not Able to Click on Create Commitment Creation Cancel BOTTOM Button "+SmokeCOM1);
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Create Commitment Button "+SmokeCOM1, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Create Commitment Button "+SmokeCOM1);
							}
						} else {
							log(LogStatus.ERROR, "Not Able to Click on Commitment Creation Cancel Top Button"+SmokeCOM1, YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Create Commitment Creation Cancel Top Button "+SmokeCOM1);
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Create Commitment Button "+SmokeCOM1, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on Create Commitment Button "+SmokeCOM1);
					}
			} else {
				sa.assertTrue(false, "Not able to clicked on created FR: " + Smoke_FR1+"so cannot verify Commitments button.");
				log(LogStatus.SKIP, "Not able to clicked on created FR: " + Smoke_FR1+"so cannot verify Commitments button.", YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "Not able to clicked on FundraisingsTab so cannot verify Commitments button.");
			log(LogStatus.SKIP, "Not able to clicked on FundraisingsTab so cannot verify Commitments button.",YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc022_1_createCommitmentFromHomePageAction(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		PartnershipsPageBusinessLayer partnership= new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String todayDate = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/yyyy");
		String OneDayeAfterDate=previousOrForwardDate(1, "M/d/yyyy");
		String[][] commitmentInformation= {{Smoke_LP1,SmokeCOMM1_CommitmentAmount,Smoke_P1,todayDate},{Smoke_LP2,SmokeCOMM2_CommitmentAmount,Smoke_P1,OneDayeAfterDate}};
		WebElement ele = null;
		String closeCross = null;
		String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Commitments.toString();
		boolean flag=false;
		for (int i = 1; i <= 2; i++) {
			// Azhar Start
			if(home.ClickOnItemOnNavatarEdge(navigationMenuName, bulkActionNavigationLink, action.BOOLEAN, 20)) {
				log(LogStatus.INFO, "clicked on Create New Commitment link", YesNo.No);
				if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));
					
				}
				if (i==1) {
					ele = home.getCommitmentCreationCloseIcon(environment, mode, 10);
					closeCross = "Commitment Creation Close Icon";
				} else {
					ele = home.getCommitmentCreationCancelButton(environment, mode, 10);
					closeCross = "Commitment Creation Cancel Button";
				}
				
				if (click(driver, ele, closeCross, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+closeCross, YesNo.No);	
				} else {
					log(LogStatus.ERROR, "Not able to click on "+closeCross, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on "+closeCross);
				}
			}else{
				log(LogStatus.ERROR, "Not able to click on Create New Commitment link so cannot create commitments", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Create New Commitment link so cannot create commitments");
		
			}
			switchToDefaultContent(driver);
			// Azhar End
			
		}
		switchToDefaultContent(driver);
		if(home.ClickOnItemOnNavatarEdge(navigationMenuName, bulkActionNavigationLink, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "clicked on Create New Commitment link", YesNo.No);
			if(home.selectFundraisingNameOrCommitmentType(environment, mode, Smoke_FR1, null, null, null, CommitmentType.fundraisingName)) {
					if(home.commitmentInfoAndAdditionalInfo(environment, mode, commitmentInformation, SmokeCOMM1_partnerType,SmokeCOMM1_TaxForms,null)) {
						log(LogStatus.INFO, "All commitment information and additional information is passed successfully", YesNo.Yes);
						if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));
							
						}
						if(home.writeTotalAmountInExcelSheet(smokeFilePath, "SmokeFund1", "Funds")) {
							if(click(driver, home.getCreateCommitmentBtn(20, TopOrBottom.BOTTOM), "create commitment button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "click on create commitment button", YesNo.No);
								ThreadSleep(2000);
								if(click(driver, home.getCreateCommitmentOkBtn(30), "OK button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on OK button", YesNo.No);
									flag=true;
									ExcelUtils.writeData(smokeFilePath, todayDate, "Commitments", excelLabel.Variable_Name,"SmokeCOMM1", excelLabel.Final_Commitment_Date);
									ExcelUtils.writeData(smokeFilePath, OneDayeAfterDate, "Commitments", excelLabel.Variable_Name,"SmokeCOMM2", excelLabel.Final_Commitment_Date);
									//writing commitment amount in lp sheet
									ExcelUtils.writeData(smokeFilePath, SmokeCOMM1_CommitmentAmount, "Limited Partner", excelLabel.Variable_Name,"SmokeLP1", excelLabel.Total_Fund_Commitments);
									ExcelUtils.writeData(smokeFilePath, SmokeCOMM2_CommitmentAmount, "Limited Partner", excelLabel.Variable_Name,"SmokeLP2", excelLabel.Total_Fund_Commitments);
									
								}else {
									log(LogStatus.ERROR, "Not able to click on OK button", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on OK button");
								}
								
							}else {
								log(LogStatus.ERROR, "Not able to click on create commitment button so cannot create commitment", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on create commitment button so cannot create commitment");
							}
						}else {
							log(LogStatus.ERROR, "Not able to write total commitment amount in fund sheet total commitment amount column in "+Smoke_Fund1, YesNo.Yes);
							sa.assertTrue(false, "Not able to write total commitment amount in fund sheet total commitment amount column in "+Smoke_Fund1);
						}
					}else {
						log(LogStatus.ERROR, "All commitment information and additional information is not passed so cannot create commitment", YesNo.Yes);
						sa.assertTrue(false, "All commitment information and additional information is not passed so cannot create commitment");
					}

			}else {
				log(LogStatus.ERROR, "Not able to select fundraising name from commitment creation pop up so cannot create commitment",YesNo.Yes);
				sa.assertTrue(false,  "Not able to select fundraising name from commitment creation pop up so cannot create commitment");
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on Create New Commitment link so cannot create commitments", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on Create New Commitment link so cannot create commitments");
		}
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc022_2_createCommitmentFromHomePageImpact(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		PartnershipsPageBusinessLayer partnership= new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String todayDate = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/yyyy");
		String OneDayeAfterDate=previousOrForwardDate(1, "M/d/yyyy");
		String[][] commitmentInformation= {{Smoke_LP1,SmokeCOMM1_CommitmentAmount,Smoke_P1,todayDate},{Smoke_LP2,SmokeCOMM2_CommitmentAmount,Smoke_P1,OneDayeAfterDate}};
		WebElement ele = null;
		String closeCross = null;
		String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Commitments.toString();
		boolean flag=false;
		switchToDefaultContent(driver);
		if(fund.clickOnTab(environment, mode, TabName.FundsTab)) {
				if(fund.clickOnCreatedFund(environment, mode, Smoke_Fund1)) {
					log(LogStatus.INFO, "clicked on fund tab", YesNo.No);
					if(fund.clickOnRelatedList(environment, mode, RecordType.Fund, RelatedList.Partnerships, RelatedTab.Fund_Management.toString())) {
						log(LogStatus.INFO, "Clicked on Partnerships related list", YesNo.No);
							if(fund.clickOncreatedPartnershipFromRelatedList(environment, mode, Smoke_P1)) {
								log(LogStatus.INFO, "clicked on partnership "+Smoke_P1, YesNo.No);
								String parentId=switchOnWindow(driver);
								if (parentId!=null) {
									log(LogStatus.ERROR, "New window is open after click on partnership "+Smoke_P1, YesNo.Yes);
									if(partnership.clickOnRelatedList(environment, mode, RecordType.Partnerships, RelatedList.Commitments, null)) {
										log(LogStatus.INFO, "clicked on related list "+RelatedList.Partnerships.toString(), YesNo.No);
										if(partnership.clickOnViewAllRelatedList(environment, mode, RelatedList.Commitments)) {
											log(LogStatus.INFO, "clicked on commitments view all ", YesNo.No);
											String id=partnership.getCommitmentID(mode, Smoke_LP1);
											if(!id.isEmpty()) {
												ExcelUtils.writeData(smokeFilePath, id, "Commitments", excelLabel.Variable_Name, "SmokeCOMM1", excelLabel.Commitment_ID);
											}else {
												log(LogStatus.ERROR, "Not able to get commitment id of  "+Smoke_LP1, YesNo.Yes);
												sa.assertTrue(false, "Not able to get commitment id of  "+Smoke_LP1);
											}
											id="";
											id=partnership.getCommitmentID(mode, Smoke_LP2);
											if(!id.isEmpty()) {
												ExcelUtils.writeData(smokeFilePath, id, "Commitments", excelLabel.Variable_Name, "SmokeCOMM2", excelLabel.Commitment_ID);
											}else {
												log(LogStatus.ERROR, "Not able to get commitment id of  "+Smoke_LP2, YesNo.Yes);
												sa.assertTrue(false, "Not able to get commitment id of  "+Smoke_LP2);
											}
											settingsBeforeTests();
											if(partnership.verifyPartnerShipRelatedList(environment, mode, PageName.PartnershipsPage, SmokeCOMM1_ID,Smoke_LP1,null,SmokeCOMM1_CommitmentAmount)) {
												log(LogStatus.INFO, "Commitment Related details are verified on partnership page "+Smoke_P1, YesNo.No);
											}else {
												log(LogStatus.INFO, "Commitment Related details are not verified on partnership page "+Smoke_P1, YesNo.Yes);
												sa.assertTrue(false, "Commitment Related details are not verified on partnership page "+Smoke_P1);
											}
											if(partnership.verifyPartnerShipRelatedList(environment, mode,  PageName.PartnershipsPage, SmokeCOMM2_ID,Smoke_LP2,null,SmokeCOMM2_CommitmentAmount)) {
												log(LogStatus.INFO, "Commitment Related details are verified on partnership page "+Smoke_P1, YesNo.No);
											}else {
												log(LogStatus.INFO, "Commitment Related details are not verified on partnership page "+Smoke_P1, YesNo.Yes);
												sa.assertTrue(false, "Commitment Related details are not verified on partnership page "+Smoke_P1);
											}
										}else {
											log(LogStatus.ERROR, "Not able to click on commitment view all link", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on commitment view all link");
										}
										
									}else {
										log(LogStatus.ERROR, "Not able to click on related list "+RelatedList.Partnerships.toString(), YesNo.Yes);
										sa.assertTrue(false, "Not able to click on related list "+RelatedList.Partnerships.toString());
									}
									driver.close();
									driver.switchTo().window(parentId);
								} else {
									log(LogStatus.ERROR, "No New is open after click on partnership "+Smoke_P1, YesNo.Yes);
									sa.assertTrue(false, "No New is open after click on partnership "+Smoke_P1);
								}
							
								
							}else {
								log(LogStatus.ERROR, "Not able to click on partnership "+Smoke_P1, YesNo.Yes);
								sa.assertTrue(false, "Not able to click on partnership "+Smoke_P1);
							}
							
						
					}else {
						log(LogStatus.ERROR, "Not able to click on Partnerships related list",YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Partnerships related list");
					}
					
				}else {
					log(LogStatus.ERROR, "Not able to click on cerate fund "+Smoke_Fund1+" so cannot click on partnership "+Smoke_P1, YesNo.No);
					sa.assertTrue(false, "Not able to click on cerate fund "+Smoke_Fund1+" so cannot click on partnership "+Smoke_P1);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on fund tab so cannot verify Partnership and Commitments", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on fund tab so cannot verify Partnership and Commitments");
			}
			String[] commID= {SmokeCOMM1_ID,SmokeCOMM2_ID};
			for(int i=0; i<commID.length; i++) {
				if(comm.clickOnTab(environment, mode, TabName.CommitmentsTab)) {
					if(comm.clickOnCreatedCommitmentId(environment, mode, commID[i])) {
						log(LogStatus.INFO, "clicked on commitment id "+commID[i], YesNo.No);
						//need to fix...
						String labelName=excelLabel.Commitment_ID+","+excelLabel.Limited_Partner+","+excelLabel.Commitment_Amount;
						String labelValue="";
						if(i==0) {
							labelValue=SmokeCOMM1_ID+","+Smoke_LP1+","+SmokeCOMM1_CommitmentAmount;
						}
						if(i==1) {
							labelValue=SmokeCOMM2_ID+","+Smoke_LP2+","+SmokeCOMM2_CommitmentAmount;
						}
						
						String[] labels = labelName.split(",");
						String[] values = labelValue.split(",");
						
						for (int j = 0; j < values.length; j++) {
							if(comm.FieldValueVerificationOnCommitmentPage(environment, mode, labels[j], values[j])) {
								log(LogStatus.INFO, labels[j]+" : With value verified for : "+values[j], YesNo.No);
							}else {
								log(LogStatus.INFO, labels[j]+" : With value not verified for : "+values[j], YesNo.Yes);
								sa.assertTrue(false, labels[j]+" : With value not verified for : "+values[j]);
							}	
						}
						
						
					}else {
						log(LogStatus.ERROR, "Not able to click on create commitment id "+commID[i]+" so cannot verify data", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on create commitment id "+commID[i]+" so cannot verify data");
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on commitment tab so cannot verify data on commit id "+commID[i], YesNo.Yes);
					sa.assertTrue(false, "Not able to click on commitment tab so cannot verify data on commit id "+commID[i]);
				}
				
			}
			
			if(ins.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
				if(ins.clickOnCreatedInstitution(environment, mode, SmokeINS1)) {
					String[][] labelAndvalue= {{excelLabel.Total_CoInvestment_Commitments.toString(),"0"},
							{excelLabel.Total_Fund_Commitments.toString(),String.valueOf((Integer.parseInt(SmokeCOMM1_CommitmentAmount)+Integer.parseInt(SmokeCOMM2_CommitmentAmount)))}};
					
					for (String[] strings : labelAndvalue) {
						if(ins.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.InstituitonsTab,strings[0], strings[1])) {
							log(LogStatus.INFO, strings[0]+" is verified on Intitution :"+SmokeINS1, YesNo.No);
						}else {
							log(LogStatus.INFO, strings[0]+" is not verified on Intitution :"+SmokeINS1, YesNo.No);
							sa.assertTrue(false, strings[0]+" is not verified on Intitution :"+SmokeINS1);
						}
					}

					
					
					String[][] commitmentRowRecord1= {{Smoke_Fund1,SmokeCOMM1_CommitmentAmount,Smoke_LP1,todayDate,""},
							{Smoke_Fund1,SmokeCOMM2_CommitmentAmount,Smoke_LP2,todayDate,""}};
					//String totalAmount1=String.valueOf((Integer.parseInt(SmokeCOMM1_CommitmentAmount)+Integer.parseInt(SmokeCOMM2_CommitmentAmount)));
					
					if(fund.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Commitments, RelatedTab.Fundraising.toString())) {
						ThreadSleep(3000);
						log(LogStatus.INFO, "Clicked on related list fundraising tab on institution page"+SmokeINS1, YesNo.No);
						
						if(ins.verifyCommitmentDetails(environment, mode, commitmentRowRecord1, Smoke_Fund1,"").isEmpty()) {
							log(LogStatus.INFO, "Commitment details are verified on institution page "+SmokeINS1, YesNo.No);
						}else {
							log(LogStatus.INFO, "Commitment details are not verified on institution page "+SmokeINS1, YesNo.No);
							sa.assertTrue(false, "Commitment details are not verified on institution page "+SmokeINS1);
						}
					}else {
						
						log(LogStatus.INFO, "Not able to click on fundraising tab on institution page"+SmokeINS1, YesNo.No);
						sa.assertTrue(false, "Not able to click on fundraising tab on institution page"+SmokeINS1);
					}
					
					
					if(clickUsingJavaScript(driver, FindElement(driver, "//a[text()='"+Smoke_LP1+"']", Smoke_LP1+" Link", action.BOOLEAN, 30), Smoke_LP1+" Link", action.SCROLLANDBOOLEAN)){
						String parentWin = switchOnWindow(driver);
						String[][] labelAndvalue1= {{excelLabel.Record_Type.toString(),"Limited Partner"},{excelLabel.Total_CoInvestment_Commitments.toString(),SmokeLP1_Total_CoInvesment_Commitments},
								{excelLabel.Total_Fund_Commitments.toString(),SmokeLP1_Total_Fund_Commitments}};
							ThreadSleep(5000);
						for (String[] strings : labelAndvalue1) {
							if(ins.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.LimitedPartne,strings[0], strings[1])) {
								log(LogStatus.INFO, strings[0]+" is verified on LP :"+Smoke_LP1, YesNo.No);
							}else {
								log(LogStatus.INFO, strings[0]+" is not verified on LP :"+Smoke_LP1, YesNo.No);
								sa.assertTrue(false, strings[0]+" is not verified on LP :"+Smoke_LP1);
							}
						}
						
						if(partnership.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Commitments, RelatedTab.Fundraising.toString())) {
							log(LogStatus.INFO, "clicked on related list "+RelatedList.Commitments.toString(), YesNo.No);
								ThreadSleep(2000);

								if(ins.verifyCommitmentDetailsLP(environment, mode,Smoke_Fund1,SmokeCOMM1_CommitmentAmount,todayDate).isEmpty()) {
									log(LogStatus.INFO, "Commitment Related details are verified on Limited Partner page "+Smoke_LP1, YesNo.No);
								}else {
									log(LogStatus.INFO, "Commitment Related details are not verified on Limited Partner page "+Smoke_LP1, YesNo.Yes);
									sa.assertTrue(false, "Commitment Related details are not verified on Limited Partner page "+Smoke_LP1);
								}
							
								if (ins.clickonDetails(environment, mode, Smoke_Fund1, SmokeCOMM1_CommitmentAmount)) {
									log(LogStatus.ERROR, "Click on Details against "+Smoke_Fund1+" "+SmokeCOMM1_CommitmentAmount, YesNo.Yes);
								
									String labelName=excelLabel.Commitment_ID+","+excelLabel.Limited_Partner+","+excelLabel.Commitment_Amount+","+excelLabel.Partnership;
									String labelValue="";
									labelValue=SmokeCOMM1_ID+","+Smoke_LP1+","+SmokeCOMM1_CommitmentAmount+","+Smoke_P1;
									String[] labels = labelName.split(",");
									String[] values = labelValue.split(",");
									for (int j = 0; j < values.length; j++) {
										if(comm.FieldValueVerificationOnCommitmentPage(environment, mode, labels[j], values[j])) {
											log(LogStatus.INFO, labels[j]+" : With value verified for : "+values[j], YesNo.No);
										}else {
											log(LogStatus.INFO, labels[j]+" : With value not verified for : "+values[j], YesNo.Yes);
											sa.assertTrue(false, labels[j]+" : With value not verified for : "+values[j]);
										}	
									}
								
								} else {
									log(LogStatus.ERROR, "Not able to click on Details against "+Smoke_Fund1+" "+SmokeCOMM1_CommitmentAmount, YesNo.Yes);
									sa.assertTrue(false, "Not able to click on Details against "+Smoke_Fund1+" "+SmokeCOMM1_CommitmentAmount);
								}
								
							
						}else {
							log(LogStatus.ERROR, "Not able to click on related tab so cannot verify Commitments", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on related tab so cannot verify Commitments");
						}
						driver.close();
						driver.switchTo().window(parentWin);
						
					} else {
						log(LogStatus.ERROR, "Not Able to click on link "+Smoke_LP1, YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on link "+Smoke_LP1);
					}
					switchToDefaultContent(driver);
					ThreadSleep(2000);
					if(clickUsingJavaScript(driver, FindElement(driver, "//a[text()='"+Smoke_LP2+"']", Smoke_LP2+" Link", action.BOOLEAN, 30), Smoke_LP2+" Link", action.SCROLLANDBOOLEAN)){
						String parentWin = switchOnWindow(driver);
						String[][] labelAndvalue1= {{excelLabel.Record_Type.toString(),"Limited Partner"},{excelLabel.Total_CoInvestment_Commitments.toString(),SmokeLP2_Total_CoInvesment_Commitments},
								{excelLabel.Total_Fund_Commitments.toString(),SmokeLP2_Total_Fund_Commitments}};
						ThreadSleep(5000);
						for (String[] strings : labelAndvalue1) {
							if(ins.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.LimitedPartne,strings[0], strings[1])) {
								log(LogStatus.INFO, strings[0]+" is verified on LP :"+Smoke_LP2, YesNo.No);
							}else {
								log(LogStatus.INFO, strings[0]+" is not verified on LP :"+Smoke_LP2, YesNo.No);
								sa.assertTrue(false, strings[0]+" is not verified on LP :"+Smoke_LP2);
							}
						}
						
						if(partnership.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Commitments, RelatedTab.Fundraising.toString())) {
							log(LogStatus.INFO, "clicked on related list "+RelatedList.Commitments.toString(), YesNo.No);
								ThreadSleep(2000);

							
								if(ins.verifyCommitmentDetailsLP(environment, mode,Smoke_Fund1,SmokeCOMM2_CommitmentAmount,OneDayeAfterDate).isEmpty()) {
									log(LogStatus.INFO, "Commitment Related details are verified on Limited Partner page "+Smoke_LP2, YesNo.No);
								}else {
									log(LogStatus.INFO, "Commitment Related details are not verified on Limited Partner page "+Smoke_LP2, YesNo.Yes);
									sa.assertTrue(false, "Commitment Related details are not verified on Limited Partner page "+Smoke_LP2);
								} 
								
								if (ins.clickonDetails(environment, mode, Smoke_Fund1, SmokeCOMM2_CommitmentAmount)) {
									log(LogStatus.ERROR, "Click on Details against "+Smoke_Fund1+" "+SmokeCOMM2_CommitmentAmount, YesNo.Yes);
								
									String labelName=excelLabel.Commitment_ID+","+excelLabel.Limited_Partner+","+excelLabel.Commitment_Amount+","+excelLabel.Partnership;
									String labelValue="";
									labelValue=SmokeCOMM2_ID+","+Smoke_LP2+","+SmokeCOMM2_CommitmentAmount+","+Smoke_P1;
									String[] labels = labelName.split(",");
									String[] values = labelValue.split(",");
									for (int j = 0; j < values.length; j++) {
										if(comm.FieldValueVerificationOnCommitmentPage(environment, mode, labels[j], values[j])) {
											log(LogStatus.INFO, labels[j]+" : With value verified for : "+values[j], YesNo.No);
										}else {
											log(LogStatus.INFO, labels[j]+" : With value not verified for : "+values[j], YesNo.Yes);
											sa.assertTrue(false, labels[j]+" : With value not verified for : "+values[j]);
										}	
									}
								
								} else {
									log(LogStatus.ERROR, "Not able to click on Details against "+Smoke_Fund1+" "+SmokeCOMM2_CommitmentAmount, YesNo.Yes);
									sa.assertTrue(false, "Not able to click on Details against "+Smoke_Fund1+" "+SmokeCOMM2_CommitmentAmount);
								}
								
						}else {
							log(LogStatus.ERROR, "Not able to click on related tab so cannot verify Commitments", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on related tab so cannot verify Commitments");
						}
						driver.close();
						driver.switchTo().window(parentWin);
						
					} else {
						log(LogStatus.ERROR, "Not Able to click on link "+Smoke_LP2, YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on link "+Smoke_LP2);
					}
					switchToDefaultContent(driver);
				}else {
					log(LogStatus.ERROR, "Not able to click on created Intitution :"+SmokeINS1, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on created Intitution :"+SmokeINS1);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on Institution tab so cannot verify commitment details", YesNo.No);
				sa.assertTrue(false, "Not able to click on Institution tab so cannot verify commitment details");
			}
		
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc023_VerifyCommitmentCreationSetUpPage(String environment, String mode) {

		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		DealCreationTabBusinessLayer dctb = new DealCreationTabBusinessLayer(driver);
		NavatarSetupPageBusinessLayer nspbl = new NavatarSetupPageBusinessLayer(driver);
		SoftAssert tcsa = new SoftAssert();
		String month = getSystemDate("MMM");
		String year = getSystemDate("yyyy");
		System.err.println("month " + month);
		System.err.println("year " + year);
		lp.CRMLogin(superAdminUserName, adminPassword);
			appLog.info("Login with User");
			if (bp.clickOnTab(environment, mode, TabName.NavatarSetup)) {
				appLog.info("Able to Click on Navatar Set up Page");
				if (nspbl.clickOnNavatarSetupSideMenusTab(environment, NavatarSetupSideMenuTab.CommitmentCreation)) {
				log(LogStatus.INFO, "Clicked on Navatar Set up Side Menu Deal Creation", YesNo.No);
					WebElement checkBox;
					if (click(driver,
							nspbl.getEditButtonforNavatarSetUpSideMenuTab(environment,
									NavatarSetupSideMenuTab.CommitmentCreation, 10),
							"Edit Button on Commitment Creation Tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on commitment Creation Edit Button", YesNo.No);
						ThreadSleep(2000);

						// Deal Information 2nd
						String expectedResult = "Company" + "," + "Fund Manager" + "," + "Fund Manager’s Fund" + ","
								+ "Individual Investor" + "," + "Institution" + "," + "Limited Partner";
						checkBox = nspbl.getEnableCheckBoxforNavatarSetUpSideMenuTab(environment, mode,
								NavatarSetupSideMenuTab.CommitmentCreation, EditViewMode.Edit, ClickOrCheckEnableDisableCheckBox.EnableOrDisable, 10);
						if (isSelected(driver, checkBox, "Check Box")) {
							log(LogStatus.PASS, "CheckBox verified and Checked", YesNo.No);
						} else {
							sa.assertTrue(false,"Check Box not Verified");
							log(LogStatus.FAIL, "Check Box not Verified", YesNo.Yes);
						}
						
						String[] labels= {"Fundraising Name","Legal Name","Fund Name","Company","None","None"};
						////////////////
						if(nspbl.verifyLabelInEditModeforNavatarSetUpSideMenuTab(environment,mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FundRaisingInformation, labels).isEmpty()) {
							log(LogStatus.PASS, "Commitment Creation FundRaising Information labels are verified ", YesNo.No);
						}else {
							log(LogStatus.FAIL, "Commitment Creation FundRaising Information labels are not verified ", YesNo.Yes);
							sa.assertTrue(false, "Commitment Creation FundRaising Information labels are not verified ");
						}
						String[] labels1= {"Legal Name","Fund Name","Company"};
						if(nspbl.verifyLabelInEditModeforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_GeneralInformation, labels1).isEmpty()) {
							log(LogStatus.PASS, "Commitment Creation Genral Information labels are verified ", YesNo.No);
						}else {
							log(LogStatus.FAIL, "Commitment Creation Genral Information labels are not verified ", YesNo.Yes);
							sa.assertTrue(false, "Commitment Creation Genral Information labels are not verified ");
						}
						
						String[] labels2= {"Limited Partner","Commitment Amount","Partnership","Final Commitment Date"};
						if(nspbl.verifyLabelInEditModeforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_CommitmentInformation, labels2).isEmpty()) {
							log(LogStatus.PASS, "Commitment Creation Commitment Information labels are verified ", YesNo.No);
						}else {
							log(LogStatus.FAIL, "Commitment Creation Commitment Information labels are not verified ", YesNo.Yes);
							sa.assertTrue(false, "Commitment Creation Commitment Information labels are not verified ");
						}
						
						String[] labels3= {"Partner Type","Tax Forms","None"};
						if(nspbl.verifyLabelInEditModeforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_AdditionalInformation, labels3).isEmpty()) {
							log(LogStatus.PASS, "Commitment Creation Additional Information labels are verified ", YesNo.No);
						}else {
							log(LogStatus.FAIL, "Commitment Creation Additional Information labels are not verified ", YesNo.Yes);
							sa.assertTrue(false, "Commitment Creation Additional Information labels are not verified ");
						}
						
						String[] labels4= {"Legal Name","None"};
						if(nspbl.verifyLabelInEditModeforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FieldsForNewLimitedPartner, labels4).isEmpty()) {
							log(LogStatus.PASS, "Commitment Creation Fields For New Limited Partner labels are verified ", YesNo.No);
						}else {
							log(LogStatus.FAIL, "Commitment Creation Fields For New Limited Partner labels are not verified ", YesNo.Yes);
							sa.assertTrue(false, "Commitment Creation Fields For New Limited Partner labels are not verified ");
						}
						
						String[] labels5= {"Fund","Partnership Legal Name","None"};
						if(nspbl.verifyLabelInEditModeforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FieldsForNewPartnership, labels5).isEmpty()) {
							log(LogStatus.PASS, "Commitment Creation Fields For New Partnership labels are verified ", YesNo.No);
						}else {
							log(LogStatus.FAIL, "Commitment Creation Fields For New Partnership labels are not verified ", YesNo.Yes);
							sa.assertTrue(false, "Commitment Creation Fields For New Partnership labels are not verified ");
						}
						if(click(driver, nspbl.getCancelButtonforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, 30), "commitment creation cancel button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on Commitment Creation cancel button ", YesNo.No);
							if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								refresh(driver);
								if(switchToFrame(driver, 10, nspbl.getnavatarSetUpTabFrame_Lighting(environment, 10))){
									appLog.info("Inside Frame1");
								}
								
							}
							ThreadSleep(5000);
							if (click(driver,nspbl.getEditButtonforNavatarSetUpSideMenuTab(environment,NavatarSetupSideMenuTab.CommitmentCreation, 60),
									"Edit Button on Commitment Creation Tab", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on commitment Creation Edit Button", YesNo.No);
								ThreadSleep(10000);
								if(nspbl.clickOnRequiredFieldListforNavatarSetUpSideMenuTab(environment,mode , NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FundRaisingInformation)) {
									log(LogStatus.INFO, "click on fundraising required field link", YesNo.No);
									String[][] fundRaisingRowValues = { { "Fundraising Name", "Name", "string" },
											{ "Fund Name", "navpeII__Fund_Name__c", "reference" },
											{ "Legal Name", "navpeII__Legal_Name__c", "reference" } };
									ThreadSleep(5000);
									tcsa = nspbl.verifyingFundRaisingLayoutRequiredFieldListInformationCommitmentTab(environment, mode, "Fundraising Layout", null, fundRaisingRowValues);
									sa.combineAssertions(tcsa);
									
								}else {
									log(LogStatus.SKIP, "Not able to click on fundraising required field link", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on fundraising required field link");
								}
								ThreadSleep(5000);
								if(nspbl.clickOnRequiredFieldListforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_AdditionalInformation)) {
									log(LogStatus.INFO, "click on Additional required field link", YesNo.No);
									String[][] AdditionalsRowValues = { { "Limited Partner", "navpeII__Limited_Partner__c", "reference" },
											{ "Partnership", "navpeII__Partnership__c", "reference" }};
									ThreadSleep(5000);
									tcsa = nspbl.verifyingAdditionalLayoutRequiredFieldListInformationCommitmentTab(environment, mode, "Commitment Layout", null, AdditionalsRowValues);
									sa.combineAssertions(tcsa);
									
								}else {
									log(LogStatus.SKIP, "Not able to click on Additional required field link", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on Additional required field link");
								}
								ThreadSleep(5000);
								if(nspbl.clickOnRequiredFieldListforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FieldsForNewLimitedPartner)) {
									log(LogStatus.INFO, "click on Limited Partner required field link", YesNo.No);
									ThreadSleep(5000);
									List<WebElement> options = allOptionsInDropDrop(driver, nspbl.getNewLP_CommitmentTab_DropDownList(environment, mode, 10), "limited partner drop down list");
									if(compareMultipleList(driver, "Company,Fund Manager,Fund Manager’s Fund,Individual Investor,Institution,Limited Partner", options).isEmpty()) {
										log(LogStatus.INFO, "Limited partner drop down list is verified ", YesNo.No);
									}else {
										log(LogStatus.FAIL, "Limited partner drop down list is not verified ", YesNo.No);
										sa.assertTrue(false, "Limited partner drop down list is not verified");
									}
									String[][] insRowValues = { { "Legal Name", "Name", "string" } };
									ThreadSleep(5000);
									tcsa = nspbl.verifyingNewLPLayoutRequiredFieldListInformationCommitmentTab(environment, mode, "Company", null, insRowValues);
									sa.combineAssertions(tcsa);
									
								}else {
									log(LogStatus.SKIP, "Not able to click on Limited Partner required field link", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on Limited Partner required field link");
								}
								ThreadSleep(5000);
								if(nspbl.clickOnRequiredFieldListforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FieldsForNewPartnership)) {
									log(LogStatus.INFO, "click on Partnership required field link", YesNo.No);
									String[][] insRowValues = { { "Partnership Legal Name", "Name", "string" },{ "Fund", "navpeII__Fund__c", "reference" } };
									ThreadSleep(5000);
									tcsa = nspbl.verifyingNewPartnerShipLayoutRequiredFieldListInformationCommitmentTab(environment, mode, "Partnership Layout", null, insRowValues);
									sa.combineAssertions(tcsa);
									
								}else {
									log(LogStatus.SKIP, "Not able to click on Partnership required field link", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on Partnership required field link");
								}
								ThreadSleep(3000);
								// Azhar Start
								NavatarSetupSideMenuTabLayoutSection[] nvsp = {NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FundRaisingInformation,
										NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_AdditionalInformation,
										NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FieldsForNewLimitedPartner,
										NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FieldsForNewPartnership};
								for (int i = 0; i < nvsp.length; i++) {
									if(nspbl.clickOnAddIconAndThenRemoveIconOnNavatarSetupSideMenuTabSections(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, nvsp[i])) {
										log(LogStatus.INFO, "Add/Delete working Fine : "+nvsp[i],YesNo.No);
									}else {
										log(LogStatus.INFO, "Add/Delete Not working Fine : "+nvsp[i],YesNo.Yes);
										sa.assertTrue(false, "Add/Delete Not working Fine : "+nvsp[i]);
									}
								}
							// Azhar End
								if(nspbl.addingMoreSelectFieldAndValuesToNavatarSetupSideMenuTabSections(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FundRaisingInformation, "Investment Likely Amount (mn)", null)) {
									log(LogStatus.INFO, "Investment Likely Amount (mn) value is selected in fundraising information section ",YesNo.No);
								}else {
									log(LogStatus.INFO, "Not able to select Investment Likely Amount (mn) value in fundraising information section ",YesNo.Yes);
									sa.assertTrue(false, "Not able to select Investment Likely Amount (mn) value in fundraising information section ");
								}
								
								if(nspbl.addingMoreSelectFieldAndValuesToNavatarSetupSideMenuTabSections(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_AdditionalInformation,null, "Placement Fee %")) {
									log(LogStatus.INFO, "Placement Fee % value is selected in Additional information section ",YesNo.No);
								}else {
									log(LogStatus.INFO, "Not able to select Placement Fee % value in Additional information section ",YesNo.Yes);
									sa.assertTrue(false, "Not able to select Placement Fee % value in Additional information section ");
								}
								
								if(nspbl.addingMoreSelectFieldAndValuesToNavatarSetupSideMenuTabSections(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FieldsForNewLimitedPartner, "Bank Name", null)) {
									log(LogStatus.INFO, "Bank Name value is selected in FieldsForNewLimitedPartner",YesNo.No);
								}else {
									log(LogStatus.INFO, "Not able to select Bank Name value in FieldsForNewLimitedPartner section ",YesNo.Yes);
									sa.assertTrue(false, "Not able to select Bank Name value in FieldsForNewLimitedPartner section ");
								}
								
								if(nspbl.addingMoreSelectFieldAndValuesToNavatarSetupSideMenuTabSections(environment, mode, NavatarSetupSideMenuTab.CommitmentCreation, NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FieldsForNewPartnership, "Fund Investment Category", null)) {
									log(LogStatus.INFO, "Fund Investment Category value is selected in FieldsForNewPartnership",YesNo.No);
								}else {
									log(LogStatus.INFO, "Not able to select Fund Investment Category value in FieldsForNewPartnership section ",YesNo.Yes);
									sa.assertTrue(false, "Not able to select Fund Investment Category value in FieldsForNewPartnership section ");
								}
								if(click(driver, nspbl.getSaveButtonforNavatarSetUpSideMenuTab(environment, NavatarSetupSideMenuTab.CommitmentCreation, 30, TopOrBottom.TOP), "save button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on saved button", YesNo.No);
									ThreadSleep(5000);
								}else {
									log(LogStatus.FAIL, "Not able to click on saved button", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on saved button");
								}
							}else {
								log(LogStatus.FAIL, "Not able to click on Commitment Creation edit button", YesNo.Yes);
								sa.assertTrue(false,"Not able to click on Commitment Creation edit button");
							}
							
						}else {
							log(LogStatus.FAIL, "Not able to click on Commitment Creation cancel button", YesNo.Yes);
							sa.assertTrue(false,"Not able to click on Commitment Creation cancel button");
						}
					} else {
						sa.assertFalse(false,"Not Able to Click on Deal Creation Edit Button");
						appLog.error("Not Able to Click on Deal Creation Edit Button");
						log(LogStatus.SKIP, "Not Able to Click on Deal Creation Edit Button", YesNo.Yes);
					}
				} else {
					sa.assertFalse(false,"Not Able to Click on Navatar Set up Side Menu Deal Creation");
					appLog.error("Not Able to Click on Navatar Set up Side Menu Deal Creation");
					log(LogStatus.SKIP, "Not Able to Click on Navatar Set up Side Menu Deal Creation", YesNo.Yes);
				}

			} else {
				sa.assertFalse(false,"Not Able to Click on Navatar Set up Page");
				appLog.error("Not Able to Click on Navatar Set up Page");
				log(LogStatus.SKIP, "Not Able to Click on Navatar Set up Page", YesNo.Yes);
			}
		
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc024_1_createCommitmentFromFundraisingPageAction(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		PartnershipsPageBusinessLayer partnership= new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer frsp = new FundraisingsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String ThreeDayeAfterDate=previousOrForwardDate(3, "M/d/YYYY");
		String todayDate = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/YYYY");
		String FourDayeAfterDate=previousOrForwardDate(4, "M/d/YYYY");
		String[][] commitmentInformation= {{Smoke_LP1,SmokeCOMM3_CommitmentAmount,Smoke_P1,ThreeDayeAfterDate},{Smoke_LP3+"<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Bank_Name.toString()+":"+SmokeLP3_BankName,SmokeCOMM4_CommitmentAmount,Smoke_P2+"<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Fund_Investment_Category.toString()+":"+SmokeP2_Fund_Investment_Category,FourDayeAfterDate}};
		if(frsp.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
			if(frsp.clickOnCreatedFundRaising(environment, mode, Smoke_FR1)) {
				log(LogStatus.INFO, "click on created FR "+Smoke_FR1, YesNo.No);
				if(frsp.clickOnShowMoreActionDownArrow(environment,  PageName.FundraisingPage, ShowMoreActionDropDownList.Create_Commitments,20)) {
					log(LogStatus.INFO, "clicked on create commitment button", YesNo.No);
					if(home.commitmentInfoAndAdditionalInfo(environment, mode, commitmentInformation, null,null,SmokeCoMM3_PlacementFee)) {
						log(LogStatus.INFO, "All commitment information and additional information is passed successfully", YesNo.Yes);
						if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));

						}
						if(home.writeTotalAmountInExcelSheet(smokeFilePath, "SmokeFund1", "Funds")) {
							if(click(driver, home.getCreateCommitmentBtn(20, TopOrBottom.BOTTOM), "create commitment button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "click on create commitment button", YesNo.No);
								ThreadSleep(2000);
								if(click(driver, home.getCreateCommitmentOkBtn(30), "OK button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on OK button", YesNo.No);
									ExcelUtils.writeData(smokeFilePath, ThreeDayeAfterDate, "Commitments", excelLabel.Variable_Name,"SmokeCOMM3", excelLabel.Final_Commitment_Date);
									ExcelUtils.writeData(smokeFilePath, FourDayeAfterDate, "Commitments", excelLabel.Variable_Name,"SmokeCOMM4", excelLabel.Final_Commitment_Date);
								}else {
									log(LogStatus.ERROR, "Not able to click on OK button", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on OK button");
								}

							}else {
								log(LogStatus.ERROR, "Not able to click on create commitment button so cannot create commitment", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on create commitment button so cannot create commitment");
							}
						}else {
							log(LogStatus.ERROR, "Not able to write total commitment amount in fund sheet total commitment amount column in "+Smoke_Fund1, YesNo.Yes);
							sa.assertTrue(false, "Not able to write total commitment amount in fund sheet total commitment amount column in "+Smoke_Fund1);
						}
					}else {
						log(LogStatus.ERROR, "All commitment information and additional information is not passed so cannot create commitment", YesNo.Yes);
						sa.assertTrue(false, "All commitment information and additional information is not passed so cannot create commitment");
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on create commitment button so cannot create commitment from fundraising page", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on create commitment button so cannot create commitment from fundraising page");
				}

			}else {
				log(LogStatus.INFO, "Not able to click on created FR "+Smoke_FR1+" so cannot create commitments from fundraising page", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created FR "+Smoke_FR1+" so cannot create commitments from fundraising page");
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on fundraising tab so cannot create commitments from fundraising tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on fundraising tab so cannot create commitments from fundraising tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc024_2_createCommitmentFromFundraisingPageImpact(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		PartnershipsPageBusinessLayer partnership= new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer frsp = new FundraisingsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String todayDate = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/YYYY");
		
		ThreadSleep(5000);
		if(fund.clickOnTab(environment, mode, TabName.FundsTab)) {
			if(fund.clickOnCreatedFund(environment, mode, Smoke_Fund1)) {
				log(LogStatus.INFO, "clicked on fund tab", YesNo.No);
				if(fund.clickOnRelatedList(environment, mode, RecordType.Fund, RelatedList.Partnerships,  RelatedTab.Fund_Management.toString())) {
					log(LogStatus.INFO, "Clicked on Partnerships related list", YesNo.No);
						if(fund.clickOncreatedPartnershipFromRelatedList(environment, mode, Smoke_P1)) {
							log(LogStatus.INFO, "clicked on partnership "+Smoke_P1, YesNo.No);
							String parentId=switchOnWindow(driver);
							if (parentId!=null) {
								log(LogStatus.ERROR, "New window is open after click on partnership "+Smoke_P1, YesNo.Yes);
								if(partnership.clickOnRelatedList(environment, mode, RecordType.Partnerships, RelatedList.Commitments, null)) {
									log(LogStatus.INFO, "clicked on related list "+RelatedList.Partnerships.toString(), YesNo.No);
									if(partnership.clickOnViewAllRelatedList(environment, mode, RelatedList.Commitments)) {
										log(LogStatus.INFO, "clicked on commitments view all ", YesNo.No);
										String id=partnership.getCommitmentID(mode, Smoke_LP1);
										if(!id.isEmpty()) {
											ExcelUtils.writeData(smokeFilePath, id, "Commitments", excelLabel.Variable_Name, "SmokeCOMM3", excelLabel.Commitment_ID);
										}else {
											log(LogStatus.ERROR, "Not able to get commitment id of  "+Smoke_LP1, YesNo.Yes);
											sa.assertTrue(false, "Not able to get commitment id of  "+Smoke_LP1);
										}
										settingsBeforeTests();
										if(partnership.verifyPartnerShipRelatedList(environment, mode,  PageName.PartnershipsPage, SmokeCOMM3_ID,Smoke_LP1,null,SmokeCOMM3_CommitmentAmount)) {
											log(LogStatus.INFO, "Commitment Related details are verified on partnership page "+Smoke_P1, YesNo.No);
										}else {
											log(LogStatus.INFO, "Commitment Related details are not verified on partnership page "+Smoke_P1, YesNo.Yes);
											sa.assertTrue(false, "Commitment Related details are not verified on partnership page "+Smoke_P1);
										}
									}else {
										log(LogStatus.ERROR, "Not able to click on commitment view all link", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on commitment view all link");
									}

								}else {
									log(LogStatus.ERROR, "Not able to click on related list "+RelatedList.Partnerships.toString(), YesNo.Yes);
									sa.assertTrue(false, "Not able to click on related list "+RelatedList.Partnerships.toString());
								}	
								driver.close();
								driver.switchTo().window(parentId);
							} else {
								log(LogStatus.ERROR, "No New is open after click on partnership "+Smoke_P1, YesNo.Yes);
								sa.assertTrue(false, "No New is open after click on partnership "+Smoke_P1);
							}
						}else {
							log(LogStatus.ERROR, "Not able to click on partnership "+Smoke_P1, YesNo.Yes);
							sa.assertTrue(false, "Not able to click on partnership "+Smoke_P1);
						}
					
				}else {
					log(LogStatus.ERROR, "Not able to click on Partnerships related list",YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Partnerships related list");
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on cerate fund "+Smoke_Fund1+" so cannot click on partnership "+Smoke_P1, YesNo.No);
				sa.assertTrue(false, "Not able to click on cerate fund "+Smoke_Fund1+" so cannot click on partnership "+Smoke_P1);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on fund tab so cannot verify Partnership and Commitments", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on fund tab so cannot verify Partnership and Commitments");
		}
		switchToDefaultContent(driver);
		if(partnership.clickOnTab(environment, mode, TabName.PartnershipsTab)) {
			if(partnership.clickOnCreatedPartnership(environment, mode, Smoke_P2)) {
				if(partnership.clickOnRelatedList(environment, mode, RecordType.Partnerships, RelatedList.Commitments, null)) {
					log(LogStatus.INFO, "clicked on related list "+RelatedList.Partnerships.toString(), YesNo.No);
					if(partnership.clickOnViewAllRelatedList(environment, mode, RelatedList.Commitments)) {
						log(LogStatus.INFO, "clicked on commitments view all ", YesNo.No);
						String id=partnership.getCommitmentID(mode, Smoke_LP3);
						if(!id.isEmpty()) {
							ExcelUtils.writeData(smokeFilePath, id, "Commitments", excelLabel.Variable_Name, "SmokeCOMM4", excelLabel.Commitment_ID);
						}else {
							log(LogStatus.ERROR, "Not able to get commitment id of  "+Smoke_LP3, YesNo.Yes);
							sa.assertTrue(false, "Not able to get commitment id of  "+Smoke_LP3);
						}
						settingsBeforeTests();
						if(partnership.verifyPartnerShipRelatedList(environment, mode,  PageName.PartnershipsPage, SmokeCOMM4_ID,Smoke_LP3,null,SmokeCOMM4_CommitmentAmount)) {
							log(LogStatus.INFO, "Commitment Related details are verified on partnership page "+Smoke_P2, YesNo.No);
						}else {
							log(LogStatus.INFO, "Commitment Related details are not verified on partnership page "+Smoke_P2, YesNo.Yes);
							sa.assertTrue(false, "Commitment Related details are not verified on partnership page "+Smoke_P2);
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on commitment view all link so cannot get commitment id of LP "+Smoke_LP2, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on commitment view all link so cannot get commitment id of LP "+Smoke_LP2);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on related list "+RelatedList.Partnerships.toString(), YesNo.Yes);
					sa.assertTrue(false, "Not able to click on related list "+RelatedList.Partnerships.toString());
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on created partnership "+Smoke_P2+" so cannot get commitment id of LP "+Smoke_LP2, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created partnership "+Smoke_P2+" so cannot get commitment id of LP "+Smoke_LP2);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on partnership Tab so cannot get commitment id of LP "+Smoke_LP2, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on partnership Tab so cannot get commitment id of LP "+Smoke_LP2);
		}
		switchToDefaultContent(driver);
		if(comm.clickOnTab(environment, mode, TabName.CommitmentsTab)) {
			if(comm.clickOnCreatedCommitmentId(environment, mode, SmokeCOMM4_ID)) {
				log(LogStatus.INFO, "clicked on commitment id "+SmokeCOMM4_ID, YesNo.No);
				//need to fix...
				String labelName=excelLabel.Commitment_ID+","+excelLabel.Limited_Partner+","+excelLabel.Commitment_Amount+","+excelLabel.Placement_Fee;

				String labelValue=SmokeCOMM4_ID+","+Smoke_LP3+","+SmokeCOMM4_CommitmentAmount+","+SmokeCOMM4_PlacementFee;
				String[] labels = labelName.split(",");
				String[] values = labelValue.split(",");
				for (int j = 0; j < values.length; j++) {
					if(comm.FieldValueVerificationOnCommitmentPage(environment, mode, labels[j], values[j])) {
						log(LogStatus.INFO, labels[j]+" : With value verified for : "+values[j], YesNo.No);
					}else {
						log(LogStatus.INFO, labels[j]+" : With value not verified for : "+values[j], YesNo.Yes);
						sa.assertTrue(false, labels[j]+" : With value not verified for : "+values[j]);
					}	
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on create commitment id "+SmokeCOMM4_ID+" so cannot verify data", YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on commitment tab so cannot verify data on commit id "+SmokeCOMM4_ID, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on commitment tab so cannot verify data on commit id "+SmokeCOMM4_ID);
		}
		if(ins.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if(ins.clickOnCreatedInstitution(environment, mode, SmokeINS1)) {
				String[][] commitmentRowRecord= {{Smoke_Fund1,SmokeCOMM1_CommitmentAmount,Smoke_LP1,Smoke_P1,"",todayDate},
						{Smoke_Fund1,SmokeCOMM2_CommitmentAmount,Smoke_LP2,Smoke_P1,"",todayDate},{Smoke_Fund1,SmokeCOMM3_CommitmentAmount,Smoke_LP1,Smoke_P1,"",todayDate},
						{Smoke_Fund1,SmokeCOMM4_CommitmentAmount,Smoke_LP3,Smoke_P2,"",todayDate}};
				//String totalAmount=String.valueOf((Integer.parseInt(SmokeCOMM1_CommitmentAmount)+Integer.parseInt(SmokeCOMM2_CommitmentAmount)+Integer.parseInt(SmokeCOMM3_CommitmentAmount)+Integer.parseInt(SmokeCOMM4_CommitmentAmount)));
				if(ins.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Commitments, RelatedTab.Fundraising.toString())) {
					log(LogStatus.INFO, "Clicked on related tab fundraising on institution page"+SmokeINS1, YesNo.No);

					if(ins.verifyCommitmentDetails(environment, mode, commitmentRowRecord, Smoke_Fund1,"").isEmpty()) {
						log(LogStatus.INFO, "Commitment details are verified on institution page "+SmokeINS1, YesNo.No);
					}else {
						log(LogStatus.INFO, "Commitment details are not verified on institution page "+SmokeINS1, YesNo.No);
						sa.assertTrue(false, "Commitment details are not verified on institution page "+SmokeINS1);
					}
				}else {
					
					log(LogStatus.INFO, "Not abel to click on related tab fundraising on institution page"+SmokeINS1, YesNo.No);
					sa.assertTrue(false, "Not abel to click on related tab fundraising on institution page"+SmokeINS1);
				}
				
				
			}else {
				log(LogStatus.ERROR, "clicked on created institution "+SmokeINS1, YesNo.Yes);
				sa.assertTrue(false, "clicked on created institution "+SmokeINS1);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on tab so cannot verify commitment details", YesNo.No);
			sa.assertTrue(false, "Not able to click on tab so cannot verify commitment details");
		}
		if(ins.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if(ins.clickOnCreatedLP(environment, mode, Smoke_LP3)) {
				if(ins.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.InstituitonsTab, excelLabel.Bank_Name.toString(), SmokeLP3_BankName)) {
					log(LogStatus.INFO, "Bank Name is verified on LP :"+Smoke_LP3, YesNo.No);
				}else {
					log(LogStatus.INFO, "Bank Name is not verified on LP :"+Smoke_LP3, YesNo.No);
					sa.assertTrue(false, "Bank Name is not verified on LP :"+Smoke_LP3);
				}

			}else {
				log(LogStatus.ERROR, "clicked on created institution "+SmokeINS1, YesNo.Yes);
				sa.assertTrue(false, "clicked on created institution "+SmokeINS1);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on tab so cannot verify commitment details", YesNo.No);
			sa.assertTrue(false, "Not able to click on tab so cannot verify commitment details");
		}
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc025_1_createCommitmentFromHomePageUsingFundButtonAction(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		PartnershipsPageBusinessLayer partnership= new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		String xpath=null;
		WebElement ele= null;

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String todayDate = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/yyyy");
		String OneDayeAfterDate=previousOrForwardDate(1, "M/d/yyyy");
		String SevenDayeAfterDate=previousOrForwardDate(7, "M/d/yyyy");
		String[][] commitmentInformation= {{Smoke_LP4+"<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Bank_Name.toString(),SmokeCOMM5_CommitmentAmount,Smoke_P2+"<break>"+CreatedOrNot.AlreadyCreated,SevenDayeAfterDate}};

		String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Commitments.toString();
		if(home.ClickOnItemOnNavatarEdge(navigationMenuName, bulkActionNavigationLink, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "clicked on Create New Commitment link", YesNo.No);
			if(home.selectFundraisingNameOrCommitmentType(environment, mode, null, Smoke_Fund1, null, SmokeINDINV1, CommitmentType.fund)) {
				if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));
				}
				ThreadSleep(5000);
				if(getText(driver, home.getCompanyNameLabelTextOnCreateCommitment(60), "company name", action.SCROLLANDBOOLEAN).isEmpty()) {
					log(LogStatus.INFO, "Company Name is displaying blank on create commitment page", YesNo.No);
				}else {
					log(LogStatus.ERROR, "Company Name is not displaying blank on create commitment page", YesNo.Yes);
					sa.assertTrue(false, "Company Name is not displaying blank on create commitment page");
				}
				if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToDefaultContent(driver);
					switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));
				}

				// Azhar Start 
				System.err.println("Azhar    >>>>>>>>>>>>>>>>>>>>>>>");
				ThreadSleep(3000);
				xpath="//input[contains(@name,'mypage:CommitmentCreationFormId') and contains(@name,':0:')]";
				ele=FindElement(driver, xpath, "LP Text Box", action.SCROLLANDBOOLEAN, 10);
				if(sendKeys(driver, ele, Smoke_LP4+"1", "LP text box : "+Smoke_LP4 , action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "pass value in text box "+Smoke_LP4, YesNo.No);

					try {
						Robot rob  = new Robot();

						rob.keyPress(KeyEvent.VK_TAB);
						ThreadSleep(500);
						rob.keyRelease(KeyEvent.VK_TAB);
						ThreadSleep(500);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();

					}

					if (click(driver, home.getNewLimitedPartnerCrossIconInCreateCommitment(10), "", action.BOOLEAN)) {
						log(LogStatus.INFO, "click on Cross Icon on New Limited PopUp", YesNo.Yes);	
					} else {
						log(LogStatus.ERROR, "Not able to click on Cross Icon on New Limited PopUp", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Cross Icon on New Limited PopUp");
					}

				}
				else {
					log(LogStatus.ERROR, "Not able to pass value in text box "+Smoke_LP4+" so cannot Check Cross/Cancel", YesNo.Yes);
					sa.assertTrue(false, "Not able to pass value in text box "+Smoke_LP4+" so cannot Check Cross/Cancel");	
				}

				// Azhar End
				System.err.println("Azhar   end >>>>>>>>>>>>>>>>>>>>>>>");
				ThreadSleep(3000);
				if(home.commitmentInfoAndAdditionalInfo(environment, mode, commitmentInformation, null,null,null)) {
					log(LogStatus.INFO, "All commitment information and additional information is passed successfully", YesNo.Yes);
					if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));

					}
					if(home.writeTotalAmountInExcelSheet(smokeFilePath, "SmokeFund1", "Funds")) {
						if(click(driver, home.getCreateCommitmentBtn(20, TopOrBottom.BOTTOM), "create commitment button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "click on create commitment button", YesNo.No);
							ThreadSleep(2000);
							if(click(driver, home.getCreateCommitmentOkBtn(30), "OK button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on OK button", YesNo.No);
								ExcelUtils.writeData(smokeFilePath, SevenDayeAfterDate, "Commitments", excelLabel.Variable_Name,"SmokeCOMM5", excelLabel.Final_Commitment_Date);
							}else {
								log(LogStatus.ERROR, "Not able to click on OK button", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on OK button");
							}
						}else {
							log(LogStatus.ERROR, "Not able to click on create commitment button so cannot create commitment", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on create commitment button so cannot create commitment");
						}
					}else {
						log(LogStatus.ERROR, "Not able to write total commitment amount in fund sheet total commitment amount column in "+Smoke_Fund1, YesNo.Yes);
						sa.assertTrue(false, "Not able to write total commitment amount in fund sheet total commitment amount column in "+Smoke_Fund1);
					}
				}else {
					log(LogStatus.ERROR, "All commitment information and additional information is not passed so cannot create commitment", YesNo.Yes);
					sa.assertTrue(false, "All commitment information and additional information is not passed so cannot create commitment");
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on commitment creation continue button so cannot create commitment", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on commitment creation continue button so cannot create commitment");
			}
		}else {
			log(LogStatus.ERROR, "Not able to select fundraising name from commitment creation pop up so cannot create commitment",YesNo.Yes);
			sa.assertTrue(false,  "Not able to select fundraising name from commitment creation pop up so cannot create commitment");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc025_1_createCommitmentFromHomePageUsingFundButtonImpact(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		PartnershipsPageBusinessLayer partnership= new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		String xpath=null;
		WebElement ele= null;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String SevenDayeAfterDate=previousOrForwardDate(7, "M/d/yyyy");
		switchToDefaultContent(driver);
		if(partnership.clickOnTab(environment, mode, TabName.PartnershipsTab)) {
			if(partnership.clickOnCreatedPartnership(environment, mode, Smoke_P2)) {
				if(partnership.clickOnRelatedList(environment, mode, RecordType.Partnerships, RelatedList.Commitments, null)) {
					log(LogStatus.INFO, "clicked on related list "+RelatedList.Partnerships.toString(), YesNo.No);
					if(partnership.clickOnViewAllRelatedList(environment, mode, RelatedList.Commitments)) {
						log(LogStatus.INFO, "clicked on commitments view all ", YesNo.No);
						String id=partnership.getCommitmentID(mode, Smoke_LP4);
						if(!id.isEmpty()) {
							ExcelUtils.writeData(smokeFilePath, id, "Commitments", excelLabel.Variable_Name, "SmokeCOMM5", excelLabel.Commitment_ID);
						}else {
							log(LogStatus.ERROR, "Not able to get commitment id of  "+Smoke_LP4, YesNo.Yes);
							sa.assertTrue(false, "Not able to get commitment id of  "+Smoke_LP4);
						}
						settingsBeforeTests();
						if(partnership.verifyPartnerShipRelatedList(environment, mode, PageName.PartnershipsPage, SmokeCOMM5_ID,Smoke_LP4,null,SmokeCOMM5_CommitmentAmount)) {
							log(LogStatus.INFO, "Commitment Related details are verified on partnership page "+Smoke_P2, YesNo.No);
						}else {
							log(LogStatus.INFO, "Commitment Related details are not verified on partnership page "+Smoke_P2, YesNo.Yes);
							sa.assertTrue(false, "Commitment Related details are not verified on partnership page "+Smoke_P2);
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on commitment view all link so cannot get commitment id of LP "+Smoke_LP2, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on commitment view all link so cannot get commitment id of LP "+Smoke_LP2);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on related list "+RelatedList.Partnerships.toString(), YesNo.Yes);
					sa.assertTrue(false, "Not able to click on related list "+RelatedList.Partnerships.toString());
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on created partnership "+Smoke_P2+" so cannot get commitment id of LP "+Smoke_LP2, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created partnership "+Smoke_P2+" so cannot get commitment id of LP "+Smoke_LP2);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on partnership Tab so cannot get commitment id of LP "+Smoke_LP2, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on partnership Tab so cannot get commitment id of LP "+Smoke_LP2);
		}
		if(ins.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if(ins.clickOnCreatedLP(environment, mode, Smoke_LP4)) {
				String[][] labelAndvalue= {{excelLabel.Bank_Name.toString(),""},
						{excelLabel.Total_CoInvestment_Commitments.toString(),SmokeLP4_Total_CoInvesment_Commitments},
						{excelLabel.Total_Fund_Commitments.toString(),SmokeLP4_Total_Fund_Commitments}};

				for (String[] strings : labelAndvalue) {
					if(ins.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.LimitedPartne,strings[0], strings[1])) {
						log(LogStatus.INFO, strings[0]+" is verified on LP :"+Smoke_LP3, YesNo.No);
					}else {
						log(LogStatus.INFO, strings[0]+" is not verified on LP :"+Smoke_LP3, YesNo.No);
						sa.assertTrue(false, strings[0]+" is not verified on LP :"+Smoke_LP3);
					}
				}

				settingsBeforeTests();

				if(partnership.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Commitments, RelatedTab.Fundraising.toString())) {
					log(LogStatus.INFO, "clicked on related list "+RelatedTab.Fundraising.toString(), YesNo.No);
						ThreadSleep(2000);

						if(ins.verifyCommitmentDetailsLP(environment, mode,Smoke_Fund1,SmokeCOMM5_CommitmentAmount,SevenDayeAfterDate).isEmpty()) {
							log(LogStatus.INFO, "Commitment Related details are verified on Limited Partner page "+Smoke_LP4, YesNo.No);
						}else {
							log(LogStatus.INFO, "Commitment Related details are not verified on Limited Partner page "+Smoke_LP4, YesNo.Yes);
							sa.assertTrue(false, "Commitment Related details are not verified on Limited Partner page "+Smoke_LP4);
						}
						
						if (ins.clickonDetails(environment, mode, Smoke_Fund1, SmokeCOMM5_CommitmentAmount)) {
							log(LogStatus.ERROR, "Click on Details against "+Smoke_Fund1+" "+SmokeCOMM5_CommitmentAmount, YesNo.Yes);
						
							String labelName=excelLabel.Commitment_ID+","+excelLabel.Limited_Partner+","+excelLabel.Commitment_Amount+","+excelLabel.Partnership;
							String labelValue="";
							labelValue=SmokeCOMM5_ID+","+Smoke_LP4+","+SmokeCOMM5_CommitmentAmount+","+Smoke_P2;
							String[] labels = labelName.split(",");
							String[] values = labelValue.split(",");
							for (int j = 0; j < values.length; j++) {
								if(comm.FieldValueVerificationOnCommitmentPage(environment, mode, labels[j], values[j])) {
									log(LogStatus.INFO, labels[j]+" : With value verified for : "+values[j], YesNo.No);
								}else {
									log(LogStatus.INFO, labels[j]+" : With value not verified for : "+values[j], YesNo.Yes);
									sa.assertTrue(false, labels[j]+" : With value not verified for : "+values[j]);
								}	
							}
						
						} else {
							log(LogStatus.ERROR, "Not able to click on Details against "+Smoke_Fund1+" "+SmokeCOMM5_CommitmentAmount, YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Details against "+Smoke_Fund1+" "+SmokeCOMM5_CommitmentAmount);
						}
						
				}else {
					log(LogStatus.ERROR, "Not able to click on related tab so cannot verify partnership", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on related tab so cannot verify partnership");
				}


			}else {
				log(LogStatus.ERROR, "Not able to click on created LP "+Smoke_LP4, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created LP "+Smoke_LP4);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on Institution tab so cannot verify commitment details", YesNo.No);
			sa.assertTrue(false, "Not able to click on Institution tab so cannot verify commitment details");
		}
		if(ins.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if(ins.clickOnCreatedInstitution(environment, mode, SmokeINDINV1)) {
				String[][] labelAndvalue= {{excelLabel.Total_CoInvestment_Commitments.toString(),SmokeLP4_Total_CoInvesment_Commitments},
						{excelLabel.Total_Fund_Commitments.toString(),SmokeLP4_Total_Fund_Commitments}};

				for (String[] strings : labelAndvalue) {
					if(ins.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.LimitedPartne,strings[0], strings[1])) {
						log(LogStatus.INFO, strings[0]+" is verified on Individual Investor :"+Smoke_LP3, YesNo.No);
					}else {
						log(LogStatus.INFO, strings[0]+" is not verified on Individual Investor :"+Smoke_LP3, YesNo.No);
						sa.assertTrue(false, strings[0]+" is not verified on Individual Investor :"+Smoke_LP3);
					}
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on created Individual Investor "+SmokeINDINV1, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created Individual Investor "+SmokeINDINV1);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on "+TabName.InstituitonsTab+" so cannot verify Individual Investor "+SmokeINDINV1+"details", YesNo.No);
			sa.assertTrue(false, "Not able to click on "+TabName.InstituitonsTab+" so cannot verify Individual Investor "+SmokeINDINV1+"details");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc025_2_createCommitmentFromHomePageUsingFundButton(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		PartnershipsPageBusinessLayer partnership= new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		String xpath=null;
		WebElement ele= null;
		List<WebElement> eleList= new ArrayList<WebElement>();
		String msg=null;

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String todayDate = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/yyyy");
		String OneDayeAfterDate=previousOrForwardDate(1, "M/d/yyyy");
		String SevenDayeAfterDate=previousOrForwardDate(7, "M/d/yyyy");
		String[][] commitmentInformation= {{Smoke_LP4+"<break>"+CreatedOrNot.AlreadyCreated,SmokeCOMM5_CommitmentAmount,Smoke_P2+"<break>"+CreatedOrNot.NotCreated,SevenDayeAfterDate}};
		
		String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Commitments.toString();
		for (int i = 1; i <= 2; i++) {
			if(home.ClickOnItemOnNavatarEdge(navigationMenuName, bulkActionNavigationLink, action.BOOLEAN, 20)) {
				log(LogStatus.INFO, "clicked on Create New Commitment link", YesNo.No);
				if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));

				}
				// Azhar Start
				// Fund Verification

				if (i==1) {

					if(click(driver, home.getFundTypeCommitment(20), "fund type text", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "click on fund type commitment Btn", YesNo.Yes);
						xpath = "//span[@id='mypage:CommitmentCreationFormId:FundPanelId']//label";
						eleList=FindElements(driver, xpath, "Commitment creation pop up Fund Label");

						List<String> returnlist = compareMultipleList(driver, "*Fund Name,*Legal Name",
								eleList);
						if (returnlist.isEmpty()) {
							log(LogStatus.PASS, "Commitment creation pop up Fund Label is Verified", YesNo.No);
						} else {
							sa.assertTrue(false, "Commitment creation pop up Fund Label is not Verified");
							log(LogStatus.FAIL, "Commitment creation pop up Fund Label is not Verified", YesNo.Yes);
						}

						if (click(driver, home.getFundTypeCommitmentCreationCancelBtn(10), "", action.BOOLEAN)) {
							log(LogStatus.INFO, "click on fund type commitment Creation Cancel Btn", YesNo.Yes);	
						} else {
							log(LogStatus.ERROR, "Not Able to click on fund type commitment Creation Cancel Btn", YesNo.Yes);
							sa.assertTrue(false, "Not Able to click on fund type commitment Creation Cancel Btn");
						}


					}else{
						log(LogStatus.ERROR, "Not Able to click on fund type commitment Btn", YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on fund type commitment Btn");	
					}

				} else {

					// Co investment Verification
					if(click(driver, home.getCoInvesmentTypeCommitment(20), "co investment type text", action.SCROLLANDBOOLEAN)) {

						log(LogStatus.INFO, "click on co investment type commitment Btn", YesNo.Yes);
						xpath = "//span[@id='mypage:CommitmentCreationFormId:CompanyFundPanelId']//label";
						eleList=FindElements(driver, xpath, "Commitment creation pop up Fund Label");

						List<String> returnlist = compareMultipleList(driver, "Company,*Fund Name,*Legal Name",
								eleList);
						if (returnlist.isEmpty()) {
							log(LogStatus.PASS, "Commitment creation pop up Fund Label is Verified", YesNo.No);
						} else {
							sa.assertTrue(false, "Commitment creation pop up Fund Label is not Verified");
							log(LogStatus.FAIL, "Commitment creation pop up Fund Label is not Verified", YesNo.Yes);
						}

						xpath = "//label[contains(text(),'Fund Name')]/../following-sibling::td/table//div/span[text()='"+Smoke_Fund3+"']";
						ele = FindElement(driver, xpath, "Fund Label Text Value", action.BOOLEAN, 10);

						if (ele!=null) {
							log(LogStatus.INFO, "Fund Name Label Element Verified : "+Smoke_Fund3, YesNo.Yes);
						} else {
							log(LogStatus.ERROR, "Fund Name Label Element is null : "+Smoke_Fund3, YesNo.Yes);
							sa.assertTrue(false, "Fund Name Label Element is null : "+Smoke_Fund3);
						}

						if(click(driver, home.getSelectCompanyNameWarningPopUpLookUpIcon(20), "company name look up icon", action.SCROLLANDBOOLEAN)) {
							if(home.selectValueFromLookUpWindow(SmokeCOM2)) {
								log(LogStatus.INFO, "Company name is selected "+SmokeCOM2, YesNo.No);


							}else {
								log(LogStatus.ERROR, "Not able to select Company Name "+SmokeCOM2, YesNo.Yes);
								sa.assertTrue(false, "Not able to select Company Name "+SmokeCOM2);
							}

						}else {
							log(LogStatus.ERROR, "Not able to click on company Name look up icon so cannot select Company Name "+SmokeCOM2,YesNo.Yes);
							sa.assertTrue(false, "Not able to click on company Name look up icon so cannot select Company Name "+SmokeCOM2);
						}

						if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));

						}
						xpath = "//label[text()='Company']/../following-sibling::td//span/input[@id='mypage:CommitmentCreationFormId:CompanyInputId']";
						ele = FindElement(driver, xpath, "Company Text Value", action.BOOLEAN, 10);
						if (sendKeys(driver, ele, "", "Company Blank Text Value", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clear Value of Company Text Box", YesNo.No);	
						} else {
							log(LogStatus.ERROR, "Not able to Clear Value of Company Text Box", YesNo.Yes);
							sa.assertTrue(false, "Not able to Clear Value of Company Text Box");
						}

						boolean flag = false;
						if(click(driver, home.getLegalNameLookUpIcon(PageName.CreateCommitmentCoInvestmentType, 20), "legal name look up icon", action.SCROLLANDBOOLEAN)) {
							if(home.selectValueFromLookUpWindow(SmokeINDINV1)) {
								log(LogStatus.INFO, "legal name is selected "+SmokeINDINV1, YesNo.No);
								if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));

								}
								WebElement element=null;
								List<WebElement> ele1=home.getCommitmentCreationContinueBtn(2);
								for(int j=0; j<ele1.size(); j++) {
									element=isDisplayed(driver, ele1.get(j), "Visibility", 5, "");
									if(element!=null) {
									if(click(driver, element, "continue button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "clicked on continue button", YesNo.No);
										flag = true;
										break;
									}else {
										if(j==ele1.size()-1) {
											log(LogStatus.ERROR, "Not able to click on create commitment pop up continue button", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on create commitment pop up continue button");

										}
									}
									}
								}
								String text= null;
								if (flag) {
									log(LogStatus.INFO, "clicked on continue button", YesNo.No);


									xpath = "//span[@id='mypage:CommitmentCreationFormId:GeneralDivId']//label[text()='Fund Name']/../following-sibling::td/span[text()='"+Smoke_Fund3+"']";
									ele = FindElement(driver, xpath, "Fund Label Text Value", action.BOOLEAN, 10);

									if (ele!=null) {
										log(LogStatus.INFO, "Fund Name Label With Value Verified: "+Smoke_Fund3, YesNo.Yes);
									} else {
										log(LogStatus.ERROR, "Fund Name Label With Value Not Verified : "+Smoke_Fund3, YesNo.Yes);
										sa.assertTrue(false, "Fund Name Label With Value Not Verified : "+Smoke_Fund3);
									}

									xpath = "//span[@id='mypage:CommitmentCreationFormId:GeneralDivId']//label[text()='Legal Name']/../following-sibling::td/span[text()='"+SmokeINDINV1+"']";
									ele = FindElement(driver, xpath, "Legal Label Text Value", action.BOOLEAN, 10);

									if (ele!=null) {
										log(LogStatus.INFO, "Legal Name Label With Value Verified: "+SmokeINDINV1, YesNo.Yes);
									} else {
										log(LogStatus.ERROR, "Legal Name Label With Value Not Verified : "+SmokeINDINV1, YesNo.Yes);
										sa.assertTrue(false, "Legal Name Label With Value Not Verified : "+SmokeINDINV1);
									}

									xpath = "//span[@id='mypage:CommitmentCreationFormId:GeneralDivId']//label[text()='Company']/../following-sibling::td/span";
									ele = FindElement(driver, xpath, "Company Label Text Value", action.BOOLEAN, 10);
									String txt=null;
									if (ele!=null) {
										log(LogStatus.INFO, "Company Label is Present", YesNo.Yes);
										txt = ele.getText().trim();
										if (txt.isEmpty()) {
											log(LogStatus.INFO, "Company Label have Blank Value", YesNo.Yes);
										} else {
											log(LogStatus.ERROR, "Company Label Should have Blank Value", YesNo.Yes);
											sa.assertTrue(false, "Company Label Should have Blank Value");

										}} else {
											log(LogStatus.ERROR, "Company Label is Not Present", YesNo.Yes);
											sa.assertTrue(false, "Company Label is Not Present");
										}


									
									//
									
									
									switchToDefaultContent(driver);
									if(home.commitmentInfoAndAdditionalInfo(environment, mode, commitmentInformation, null,null,null)) {
										log(LogStatus.INFO, "All commitment information and additional information is passed successfully", YesNo.Yes);
										if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
											switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));

										}
										
										for (int k = 1; k <= 2; k++) {
											
											if(click(driver, home.getCreateCommitmentBtn(20, TopOrBottom.BOTTOM), "create commitment button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "click on create commitment button", YesNo.No);
												ThreadSleep(2000);
												
												
												if(click(driver, home.getSelectCompanyNameLookUpIconOnCreateCommitmentPopUp(20), "company name look up icon", action.SCROLLANDBOOLEAN)) {
													if(home.selectValueFromLookUpWindow(SmokeCOM2)) {
														log(LogStatus.INFO, "Company name is selected "+SmokeCOM2, YesNo.No);
														
														if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
															switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));

														}
														
														xpath = "(//a[contains(@onclick,'CancelCompnayPopup()')])["+i+"]";
														ele = FindElement(driver, xpath, "Cross/Cancel : "+i, action.BOOLEAN, 10);

														if (click(driver, ele, "Cross/Cancel : "+i, action.BOOLEAN)) {
															log(LogStatus.INFO, "clicked on Cross/Cancel : "+i, YesNo.Yes);
														} else {
															log(LogStatus.ERROR, "Not able to click on Cross/Cancel : "+i, YesNo.Yes);
															sa.assertTrue(false, "Not able to click on Cross/Cancel : "+i);
														}
										
														
													}else {
														log(LogStatus.ERROR, "Not able to select Company Name "+SmokeCOM2, YesNo.Yes);
														sa.assertTrue(false, "Not able to select Company Name "+SmokeCOM2);
													}
													
												}else {
													log(LogStatus.ERROR, "Not able to click on company Name look up icon so cannot select Company Name "+SmokeCOM2,YesNo.Yes);
													sa.assertTrue(false, "Not able to click on company Name look up icon so cannot select Company Name "+SmokeCOM2);
												}
												
												
											}else {
												log(LogStatus.ERROR, "Not able to click on create commitment button so cannot create commitment", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on create commitment button so cannot create commitment");
											}
											
										}
										
										
									}else {
										log(LogStatus.ERROR, "All commitment information and additional information is not passed so cannot create commitment", YesNo.Yes);
										sa.assertTrue(false, "All commitment information and additional information is not passed so cannot create commitment");
									}
									
								} else {
									log(LogStatus.ERROR, "Not able to click on create commitment pop up continue button", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on create commitment pop up continue button");

								}

							}else {
								log(LogStatus.ERROR, "Not able to select institutionName "+SmokeINDINV1, YesNo.Yes);
								sa.assertTrue(false, "Not able to select institutionName "+SmokeINDINV1);


							}
						}else {
							log(LogStatus.ERROR, "Not able to select institutionName "+SmokeINDINV1, YesNo.Yes);
							sa.assertTrue(false, "Not able to select institutionName "+SmokeINDINV1);

						}



					}else{
						log(LogStatus.ERROR, "Not Able to click on Co - Investment commitment Btn", YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on Co - Investment commitment Btn");	
					}
				}

			}else {
				log(LogStatus.ERROR, "Not able to select fundraising name from commitment creation pop up so cannot create commitment",YesNo.Yes);
				sa.assertTrue(false,  "Not able to select fundraising name from commitment creation pop up so cannot create commitment");
			}
			switchToDefaultContent(driver);
		}

		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc026_1_createCommitmentFromFundraisingForCoInvestmentAction(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		PartnershipsPageBusinessLayer partnership= new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer frsp = new FundraisingsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String todayDate = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/yyyy");
		String TenDayAfterDate=previousOrForwardDate(10, "M/d/yyyy");
		String ElevnDayAfterDate=previousOrForwardDate(11, "M/d/yyyy");
		String xpath = null;
		WebElement ele = null;
		String parentId = null;
		String[][] commitmentInformation= {{Smoke_LP4+"<break>"+CreatedOrNot.AlreadyCreated,SmokeCOMM6_CommitmentAmount,Smoke_P3+"<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Fund_Investment_Category.toString(),TenDayAfterDate},
				{"Test1LPRmove Link<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Bank_Name,"12000000",Smoke_P3+"<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Fund_Investment_Category.toString(),ElevnDayAfterDate}};

		for (int i = 1; i<=2; i++) {

			if(frsp.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
				if(frsp.clickOnCreatedFundRaising(environment, mode, Smoke_FR6)) {
					log(LogStatus.INFO, "click on created FR "+Smoke_FR1, YesNo.No);
					if(frsp.clickOnShowMoreActionDownArrow(environment,  PageName.FundraisingPage, ShowMoreActionDropDownList.Create_Commitments,20)) {
						log(LogStatus.INFO, "clicked on create commitment button", YesNo.No);

						if (i==1) {

							// Azhar Start

							if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));
							}

							String[] linkClick = {SmokeCOM2};

							for (int j = 0; j < linkClick.length; j++) {

								xpath = "//div[@id='FundraisingSection']//span/a[text()='"+linkClick[j]+"']";
								ele = FindElement(driver, xpath, linkClick[j], action.SCROLLANDBOOLEAN, 10);

								if (ele!=null) {
									appLog.info("Clicked on : "+linkClick[j]);
									if (click(driver, ele, linkClick[j], action.SCROLLANDBOOLEAN)) {
										switchToDefaultContent(driver);
										if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
											xpath = "//div/*[contains(text(),'"+linkClick[j]+"')]";
										}else{
											xpath = "//h2[contains(text(),'"+linkClick[j]+"')]";	
										}

										ele = FindElement(driver, xpath, "on same window : "+linkClick[j], action.BOOLEAN, 10);

										if (ele!=null) {
											appLog.info("Landing Page Verified : "+linkClick[j]);
										} else {
											appLog.error("Landing Page Not Verified : "+linkClick[j]);
											sa.assertTrue(false, "Landing Page Not Verified : "+linkClick[j]);
										}


									} else {
										appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
										sa.assertTrue(false, "Not able to click on "+linkClick[j]+" so cannot verify landing page");
									}
								} else {
									appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
									sa.assertTrue(false, "Not able to click on "+linkClick[j]+" so cannot verify landing page");
								}


							}

							// Azhar End

						} else {

							ThreadSleep(5000);
							String[][] labelAndValue= {{CreateCommitmentPageFieldLabelText.Legal_Name.toString(),SmokeINDINV1},
									{CreateCommitmentPageFieldLabelText.Fundraising_Name.toString(),Smoke_FR6},
									{CreateCommitmentPageFieldLabelText.Investment_Likely_Amount.toString(),""},
									{CreateCommitmentPageFieldLabelText.Company.toString(),""},
									{CreateCommitmentPageFieldLabelText.Fund_Name.toString(),Smoke_Fund3}};
							SoftAssert result=home.verifyCreateCommitmentGenralOrFundraisingInfo(environment, mode, labelAndValue);
							sa.combineAssertions(result);


							if(home.commitmentInfoAndAdditionalInfo(environment, mode, commitmentInformation, null,null,null)) {
								log(LogStatus.INFO, "All commitment information and additional information is passed successfully", YesNo.Yes);
								if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));

								}
								SoftAssert result1= home.deleteRowsFromCreateCommitmentGenralOrFundraisingInfo(environment, mode);
								sa.combineAssertions(result1);
								if(home.writeTotalAmountInExcelSheet(smokeFilePath, "SmokeFund3", "Funds")) {
									if(click(driver, home.getCreateCommitmentBtn(20, TopOrBottom.BOTTOM), "create commitment button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "click on create commitment button", YesNo.No);
										ThreadSleep(2000);
										if(click(driver, home.getCreateCommitmentOkBtn(30), "OK button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on OK button", YesNo.No);
											ExcelUtils.writeData(smokeFilePath, TenDayAfterDate, "Commitments", excelLabel.Variable_Name,"SmokeCOMM6", excelLabel.Final_Commitment_Date);

										}else {
											log(LogStatus.ERROR, "Not able to click on OK button", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on OK button");
										}
										if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
											switchToDefaultContent(driver);
										}
										
									}else {
										log(LogStatus.ERROR, "Not able to click on create commitment button so cannot create commitment", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on create commitment button so cannot create commitment");
									}
								}else {
									log(LogStatus.ERROR, "Not able to write total commitment amount in fund sheet total commitment amount column in "+Smoke_Fund1, YesNo.Yes);
									sa.assertTrue(false, "Not able to write total commitment amount in fund sheet total commitment amount column in "+Smoke_Fund1);
								}
							}else {
								log(LogStatus.ERROR, "All commitment information and additional information is not passed so cannot create commitment", YesNo.Yes);
								sa.assertTrue(false, "All commitment information and additional information is not passed so cannot create commitment");
							}

						}
					}else {
						log(LogStatus.ERROR, "Not able to click on create commitment button so cannot create commitment from fundraising page", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on create commitment button so cannot create commitment from fundraising page");
					}

				}else {
					log(LogStatus.INFO, "Not able to click on created FR "+Smoke_FR6+" so cannot create commitments from fundraising page", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on created FR "+Smoke_FR6+" so cannot create commitments from fundraising page");
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on fundraising tab so cannot create commitments from fundraising tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on fundraising tab so cannot create commitments from fundraising tab");
			}
			switchToDefaultContent(driver);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc026_2_createCommitmentFromFundraisingForCoInvestmentImpact(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		PartnershipsPageBusinessLayer partnership= new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer frsp = new FundraisingsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String todayDate = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/yyyy");
		String TenDayAfterDate=previousOrForwardDate(10, "M/d/yyyy");
		String ElevnDayAfterDate=previousOrForwardDate(11, "M/d/yyyy");
		String xpath = null;
		WebElement ele = null;
		String parentId = null;
		String[][] commitmentInformation= {{Smoke_LP4+"<break>"+CreatedOrNot.AlreadyCreated,SmokeCOMM6_CommitmentAmount,Smoke_P3+"<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Fund_Investment_Category.toString(),TenDayAfterDate},
				{"Test1LPRmove Link<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Bank_Name,"12000000",Smoke_P3+"<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Fund_Investment_Category.toString(),ElevnDayAfterDate}};

			if(fund.clickOnTab(environment, mode, TabName.FundsTab)) {
				if(fund.clickOnCreatedFund(environment, mode, Smoke_Fund3)) {
					log(LogStatus.INFO, "clicked on fund tab", YesNo.No);
					if(fund.clickOnRelatedList(environment, mode, RecordType.Fund, RelatedList.Partnerships, RelatedTab.Fund_Management.toString())) {
						log(LogStatus.INFO, "Clicked on Partnerships related list", YesNo.No);;
						if(fund.clickOncreatedPartnershipFromRelatedList(environment, mode, Smoke_P3)) {
							log(LogStatus.INFO, "clicked on partnership "+Smoke_P3, YesNo.No);
							parentId=switchOnWindow(driver);
							if (parentId!=null) {
								log(LogStatus.ERROR, "New window is open after click on partnership "+Smoke_P3, YesNo.Yes);
								if(partnership.clickOnRelatedList(environment, mode, RecordType.Partnerships, RelatedList.Commitments, null)) {
									log(LogStatus.INFO, "clicked on related list "+RelatedList.Partnerships.toString(), YesNo.No);
									if(partnership.clickOnViewAllRelatedList(environment, mode, RelatedList.Commitments)) {
										log(LogStatus.INFO, "clicked on commitments view all ", YesNo.No);
										String id=partnership.getCommitmentID(mode, Smoke_LP4);
										if(!id.isEmpty()) {
											ExcelUtils.writeData(smokeFilePath, id, "Commitments", excelLabel.Variable_Name, "SmokeCOMM6", excelLabel.Commitment_ID);
										}else {
											log(LogStatus.ERROR, "Not able to get commitment id of  "+Smoke_LP4, YesNo.Yes);
											sa.assertTrue(false, "Not able to get commitment id of  "+Smoke_LP4);
										}
										settingsBeforeTests();
										if(partnership.verifyPartnerShipRelatedList(environment, mode,  PageName.PartnershipsPage, SmokeCOMM6_ID,Smoke_LP4,SmokeCOM2,SmokeCOMM6_CommitmentAmount)) {
											log(LogStatus.INFO, "Commitment Related details are verified on partnership page "+Smoke_P1, YesNo.No);
										}else {
											log(LogStatus.INFO, "Commitment Related details are not verified on partnership page "+Smoke_P1, YesNo.Yes);
											sa.assertTrue(false, "Commitment Related details are not verified on partnership page "+Smoke_P1);
										}
									}else {
										log(LogStatus.ERROR, "Not able to click on commitment view all link", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on commitment view all link");
									}

								}else {
									log(LogStatus.ERROR, "Not able to click on related list "+RelatedList.Partnerships.toString(), YesNo.Yes);
									sa.assertTrue(false, "Not able to click on related list "+RelatedList.Partnerships.toString());
								}


								driver.close();
								driver.switchTo().window(parentId);
							} else {
								log(LogStatus.ERROR, "No New is open after click on partnership "+Smoke_P3, YesNo.Yes);
								sa.assertTrue(false, "No New is open after click on partnership "+Smoke_P3);
							}
						}else {
							log(LogStatus.ERROR, "Not able to click on partnership "+Smoke_P3, YesNo.Yes);
							sa.assertTrue(false, "Not able to click on partnership "+Smoke_P3);
						}

					}else {
						log(LogStatus.ERROR, "Not able to click on Partnerships related list",YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Partnerships related list");
					}

				}else {
					log(LogStatus.ERROR, "Not able to click on cerate fund "+Smoke_Fund3+" so cannot click on partnership "+Smoke_P1, YesNo.No);
					sa.assertTrue(false, "Not able to click on cerate fund "+Smoke_Fund3+" so cannot click on partnership "+Smoke_P1);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on fund tab so cannot verify Partnership and Commitments", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on fund tab so cannot verify Partnership and Commitments");
			}



			if(comm.clickOnTab(environment, mode, TabName.CommitmentsTab)) {
				if(comm.clickOnCreatedCommitmentId(environment, mode, SmokeCOMM6_ID)) {
					log(LogStatus.INFO, "clicked on commitment id "+SmokeCOMM6_ID, YesNo.No);
					String labelName=excelLabel.Commitment_ID+","+excelLabel.Limited_Partner+","+excelLabel.Commitment_Amount+","+excelLabel.Placement_Fee+","+excelLabel.Tax_Forms;

					String labelValue=SmokeCOMM6_ID+","+Smoke_LP4+","+SmokeCOMM6_CommitmentAmount+","+""+","+"";
					String[] labels = labelName.split(",");
					String[] values = labelValue.split(",");
					for (int j = 0; j < values.length; j++) {
						if(comm.FieldValueVerificationOnCommitmentPage(environment, mode, labels[j], values[j])) {
							log(LogStatus.INFO, labels[j]+" : With value verified for : "+values[j], YesNo.No);
						}else {
							log(LogStatus.INFO, labels[j]+" : With value not verified for : "+values[j], YesNo.Yes);
							sa.assertTrue(false, labels[j]+" : With value not verified for : "+values[j]);
						}	
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on create commitment id "+SmokeCOMM6_ID+" so cannot verify data", YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on commitment tab so cannot verify data on commit id "+SmokeCOMM6_ID, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on commitment tab so cannot verify data on commit id "+SmokeCOMM6_ID);
			}

			if(ins.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
				if(ins.clickOnCreatedInstitution(environment, mode, SmokeINDINV1)) {
					
					
					if(ins.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Commitments, RelatedTab.Fundraising.toString())) {
						
						log(LogStatus.INFO, "click on related fundraising tab on insitiution page"+SmokeINDINV1, YesNo.No);
						String[][] commitmentRowRecord= {{Smoke_Fund1,SmokeCOMM5_CommitmentAmount,Smoke_LP4,todayDate,""}};

						if(ins.verifyCommitmentDetails(environment, mode, commitmentRowRecord, Smoke_Fund1,SmokeCOMM5_CommitmentAmount).isEmpty()) {
							log(LogStatus.INFO, "Commitment details are verified on Individual Investor page "+SmokeINDINV1, YesNo.No);
						}else {
							log(LogStatus.INFO, "Commitment details are not verified on Individual Investor page "+SmokeINS1, YesNo.No);
							sa.assertTrue(false, "Commitment details are not verified on Individual Investor page "+SmokeINS1);
						}
						
						String[][] commitmentRowRecord1= {{Smoke_Fund3,SmokeCOMM6_CommitmentAmount,Smoke_LP4,todayDate,""}};

						if(ins.verifyCommitmentDetails(environment, mode, commitmentRowRecord1, Smoke_Fund3,SmokeCOMM6_CommitmentAmount).isEmpty()) {
							log(LogStatus.INFO, "Commitment details are verified on Individual Investor page "+SmokeINDINV1, YesNo.No);
						}else {
							log(LogStatus.INFO, "Commitment details are not verified on Individual Investor page "+SmokeINS1, YesNo.No);
							sa.assertTrue(false, "Commitment details are not verified on Individual Investor page "+SmokeINS1);
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on related fundraising tab on insitiution page"+SmokeINDINV1, YesNo.Yes);
						sa.assertTrue(false, "Not able to related fundraising tab on insitiution page "+SmokeINDINV1);
					}
					

				}else {
					log(LogStatus.ERROR, "Not able to click on created Individual Investor"+SmokeINDINV1, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on created Individual Investor "+SmokeINDINV1);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on institution tab so cannot verify commitment details", YesNo.No);
				sa.assertTrue(false, "Not able to click on institution tab so cannot verify commitment details");
			}
	
		
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc027_verifyErrorMessageFundraisingForCoInvestment(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		PartnershipsPageBusinessLayer partnership= new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer frsp = new FundraisingsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if(frsp.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
			if(frsp.clickOnCreatedFundRaising(environment, mode, Smoke_FR3)) {
				log(LogStatus.INFO, "click on created FR "+Smoke_FR1, YesNo.No);
				if(frsp.clickOnShowMoreActionDownArrow(environment, PageName.FundraisingPage, ShowMoreActionDropDownList.Create_Commitments,20)) {
					log(LogStatus.INFO, "clicked on create commitment button", YesNo.No);
					if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));
						
					}
					String errorMesg=HomePageErrorMessage.InsufficientPermissionErrorMsgOnCreateCommitmentPage1+HomePageErrorMessage.InsufficientPermissionErrorMsgOnCreateCommitmentPage2;
					if(home.getInsufficientPermissionErrorMsgOnCreateCommitmentPage(60)!=null) {
						String aa = home.getInsufficientPermissionErrorMsgOnCreateCommitmentPage(10).getText().trim();
						System.err.println(aa);
						if(aa.contains(HomePageErrorMessage.InsufficientPermissionErrorMsgOnCreateCommitmentPage1) && aa.contains(HomePageErrorMessage.InsufficientPermissionErrorMsgOnCreateCommitmentPage2)) {
							log(LogStatus.INFO, errorMesg+" : Error Message is verified ", YesNo.No);
						}else {
							log(LogStatus.ERROR, errorMesg+" : Error Message is not verified \t Actual: "+aa, YesNo.Yes);
							sa.assertTrue(false, errorMesg+" : Error Message is not verified \t Actual: "+aa);
						}
					}else {
						log(LogStatus.INFO, "Error Message popup is not visible so cannot verify error message "+errorMesg, YesNo.Yes);
						sa.assertTrue(false, "Error Message popup is not visible so cannot verify error message "+errorMesg);
					}
					if(click(driver, home.getInsufficientPermissionOKBtnOnCreateCommitmentPage(20), "ok button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on OK button",YesNo.Yes);
						
					}else {
						log(LogStatus.ERROR, "Not able to click on OK button", YesNo.Yes);
						sa.assertTrue(false,"Not able to click on OK  button");
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on create commitment button so cannot create commitment from fundraising page", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on create commitment button so cannot create commitment from fundraising page");
				}

			}else {
				log(LogStatus.INFO, "Not able to click on created FR "+Smoke_FR3+" so cannot create commitments from fundraising page", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created FR "+Smoke_FR3+" so cannot create commitments from fundraising page");
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on fundraising tab so cannot create commitments from fundraising tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on fundraising tab so cannot create commitments from fundraising tab");
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc028_1_createCommitmentFromCompanyPageForCoInvestmentAction(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		PartnershipsPageBusinessLayer partnership= new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer frsp = new FundraisingsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String FifteenDayAfterDate=previousOrForwardDate(15, "M/d/yyyy");
		String[][] commitmentInformation= {{Smoke_LP5+"<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Bank_Name,SmokeCOMM7_CommitmentAmount,Smoke_P4+"<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Fund_Investment_Category.toString(),FifteenDayAfterDate}};
		String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Commitments.toString();
		if(home.ClickOnItemOnNavatarEdge(navigationMenuName, bulkActionNavigationLink, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "clicked on Create New Commitment link", YesNo.No);
			ThreadSleep(5000);
			if(home.selectFundraisingNameOrCommitmentType(environment, mode, null, null, SmokeCOM2, SmokeINDINV1, CommitmentType.coInvestment)) {
				log(LogStatus.INFO, "successfully selected co-investment and select legal name : "+SmokeINDINV1, YesNo.No);
				if(home.commitmentInfoAndAdditionalInfo(environment, mode, commitmentInformation, null,null,null)) {
					log(LogStatus.INFO, "All commitment information and additional information is passed successfully", YesNo.Yes);
					if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));

					}
					if(home.writeTotalAmountInExcelSheet(smokeFilePath, "SmokeFund3", "Funds")) {
						if(click(driver, home.getCreateCommitmentBtn(20, TopOrBottom.BOTTOM), "create commitment button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "click on create commitment button", YesNo.No);
							ThreadSleep(2000);
							if(click(driver, home.getCreateCommitmentOkBtn(30), "OK button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on OK button", YesNo.No);
								ExcelUtils.writeData(smokeFilePath, FifteenDayAfterDate, "Commitments", excelLabel.Variable_Name,"SmokeCOMM7", excelLabel.Final_Commitment_Date);
							}else {
								log(LogStatus.ERROR, "Not able to click on OK button", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on OK button");
							}
							if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToDefaultContent(driver);
							}

						}else {
							log(LogStatus.ERROR, "Not able to click on create commitment button so cannot create commitment", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on create commitment button so cannot create commitment");
						}
					}else {
						log(LogStatus.ERROR, "Not able to write total commitment amount in fund sheet total commitment amount column in "+Smoke_Fund1, YesNo.Yes);
						sa.assertTrue(false, "Not able to write total commitment amount in fund sheet total commitment amount column in "+Smoke_Fund1);
					}
				}else {
					log(LogStatus.ERROR, "All commitment information and additional information is not passed so cannot create commitment", YesNo.Yes);
					sa.assertTrue(false, "All commitment information and additional information is not passed so cannot create commitment");
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on co-investment option and select legal name "+SmokeINDINV1, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on co-investment option and select legal name "+SmokeINDINV1);
			}
		}else {
			log(LogStatus.ERROR, "Not able to select fundraising name from commitment creation pop up so cannot create commitment",YesNo.Yes);
			sa.assertTrue(false,  "Not able to select fundraising name from commitment creation pop up so cannot create commitment");
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc028_2_createCommitmentFromCompanyPageForCoInvestmentImpact(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundraisingsPageBusinessLayer frpg = new FundraisingsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		PartnershipsPageBusinessLayer partnership= new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer frsp = new FundraisingsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String FifteenDayAfterDate=previousOrForwardDate(15, "M/d/yyyy");
		String[][] commitmentInformation= {{Smoke_LP5+"<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Bank_Name,SmokeCOMM7_CommitmentAmount,Smoke_P4+"<break>"+CreatedOrNot.NotCreated+"<break>"+excelLabel.Fund_Investment_Category.toString(),FifteenDayAfterDate}};
		String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Commitments.toString();
		
			if(fund.clickOnTab(environment, mode, TabName.FundsTab)) {
				if(fund.clickOnCreatedFund(environment, mode, Smoke_Fund3)) {
					log(LogStatus.INFO, "clicked on fund tab", YesNo.No);
					if(fund.clickOnRelatedList(environment, mode, RecordType.Fund, RelatedList.Partnerships, RelatedTab.Fund_Management.toString())) {
						log(LogStatus.INFO, "Clicked on Partnerships related list", YesNo.No);
							if(fund.clickOncreatedPartnershipFromRelatedList(environment, mode, Smoke_P4)) {
								log(LogStatus.INFO, "clicked on partnership "+Smoke_P4, YesNo.No);
								String parentId=switchOnWindow(driver);
								if (parentId!=null) {
									log(LogStatus.ERROR, "New window is open after click on partnership "+Smoke_P4, YesNo.Yes);
									if(partnership.clickOnRelatedList(environment, mode, RecordType.Partnerships, RelatedList.Commitments, null)) {
										log(LogStatus.INFO, "clicked on related list "+RelatedList.Partnerships.toString(), YesNo.No);
										if(partnership.clickOnViewAllRelatedList(environment, mode, RelatedList.Commitments)) {
											log(LogStatus.INFO, "clicked on commitments view all ", YesNo.No);
											String id=partnership.getCommitmentID(mode, Smoke_LP5);
											if(!id.isEmpty()) {
												ExcelUtils.writeData(smokeFilePath, id, "Commitments", excelLabel.Variable_Name, "SmokeCOMM7", excelLabel.Commitment_ID);
											}else {
												log(LogStatus.ERROR, "Not able to get commitment id of  "+Smoke_LP5, YesNo.Yes);
												sa.assertTrue(false, "Not able to get commitment id of  "+Smoke_LP5);
											}
											settingsBeforeTests();
										}else {
											log(LogStatus.ERROR, "Not able to click on commitment view all link", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on commitment view all link");
										}

									}else {
										log(LogStatus.ERROR, "Not able to click on related list "+RelatedList.Partnerships.toString(), YesNo.Yes);
										sa.assertTrue(false, "Not able to click on related list "+RelatedList.Partnerships.toString());
									}
									driver.close();
									driver.switchTo().window(parentId);
								} else {
									log(LogStatus.ERROR, "No New is open after click on partnership "+Smoke_P4, YesNo.Yes);
									sa.assertTrue(false, "No New is open after click on partnership "+Smoke_P4);
								}
							}else {
								log(LogStatus.ERROR, "Not able to click on partnership "+Smoke_P4, YesNo.Yes);
								sa.assertTrue(false, "Not able to click on partnership "+Smoke_P4);
							}

						
					}else {
						log(LogStatus.ERROR, "Not able to click on Partnerships related list",YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Partnerships related list");
					}

				}else {
					log(LogStatus.ERROR, "Not able to click on cerate fund "+Smoke_Fund3+" so cannot click on partnership "+Smoke_P1, YesNo.No);
					sa.assertTrue(false, "Not able to click on cerate fund "+Smoke_Fund3+" so cannot click on partnership "+Smoke_P1);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on fund tab so cannot verify Partnership and Commitments", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on fund tab so cannot verify Partnership and Commitments");
			}
			if(comm.clickOnTab(environment, mode, TabName.CommitmentsTab)) {
				if(comm.clickOnCreatedCommitmentId(environment, mode, SmokeCOMM7_ID)) {
					log(LogStatus.INFO, "clicked on commitment id "+SmokeCOMM7_ID, YesNo.No);
					//need to fix...
					String labelName=excelLabel.Commitment_ID+","+excelLabel.Limited_Partner+","+excelLabel.Commitment_Amount;

					String labelValue=SmokeCOMM7_ID+","+Smoke_LP5+","+SmokeCOMM7_CommitmentAmount;
					String[] labels = labelName.split(",");
					String[] values = labelValue.split(",");
					for (int j = 0; j < values.length; j++) {
						if(comm.FieldValueVerificationOnCommitmentPage(environment, mode, labels[j], values[j])) {
							log(LogStatus.INFO, labels[j]+" : With value verified for : "+values[j], YesNo.No);
						}else {
							log(LogStatus.INFO, labels[j]+" : With value not verified for : "+values[j], YesNo.Yes);
							sa.assertTrue(false, labels[j]+" : With value not verified for : "+values[j]);
						}	
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on create commitment id "+SmokeCOMM7_ID+" so cannot verify data", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on create commitment id "+SmokeCOMM7_ID+" so cannot verify data");
					
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on commitment tab so cannot verify data on commit id "+SmokeCOMM7_ID, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on commitment tab so cannot verify data on commit id "+SmokeCOMM7_ID);
			}	
		
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc029_VerifyRevertToDefaultForCommitmentTabInNavatarSetup(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer nsp = new NavatarSetupPageBusinessLayer(driver);
		String month = getSystemDate("MMM");
		String year = getSystemDate("yyyy");
		System.err.println("month " + month);
		System.err.println("year " + year);
		String msg;
		lp.CRMLogin(superAdminUserName, adminPassword);
			appLog.info("Login with User");
			if (bp.clickOnTab(environment, mode, TabName.NavatarSetup)) {
				log(LogStatus.INFO, "Clicked on Navatar SetUp", YesNo.No);
	
				if (nsp.clickOnNavatarSetupSideMenusTab(environment,  NavatarSetupSideMenuTab.CommitmentCreation)) {
					appLog.info("Clicked on Contact Transfer Tab");
					log(LogStatus.INFO, "Clicked on Commitment Creation Tab", YesNo.No);
					switchToDefaultContent(driver);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 10, nsp.getnavatarSetUpTabFrame_Lighting(environment, 10));
					}
				
					if (click(driver, nsp.getEditButtonforNavatarSetUpSideMenuTab(environment, 
						NavatarSetupSideMenuTab.CommitmentCreation, 10), "Edit Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button ", YesNo.No);
					ThreadSleep(2000);
					TopOrBottom[] topOrBottom = {TopOrBottom.TOP,TopOrBottom.BOTTOM,TopOrBottom.TOP};
					for (int i = 0; i<topOrBottom.length; i++) {
						
						if (click(driver,nsp.getRevertToDefaultButtonforNavatarSetUpSideMenuTab(environment, mode,NavatarSetupSideMenuTab.CommitmentCreation, topOrBottom[i], 10),"Revert to Default Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Revert to Default Button", YesNo.No);
							ThreadSleep(2000);
							msg = nsp.getWarningPopUpMsg(environment, 10).getText();
							if (msg != null) {
								if (msg.contains(NavatarSetUpPageErrorMessage.WarningPopUpMessage1)
										&& msg.contains(NavatarSetUpPageErrorMessage.WarningPopUpMessage2)) {
									log(LogStatus.INFO, "Warning PopUp MEssage Verified : " + msg, YesNo.No);
									
								} else {
									appLog.error("Warning PopUp MEssage Not Verified :  Actual -  " + msg
											+ "\t Expected : " + NavatarSetUpPageErrorMessage.WarningPopUpMessage1);
									sa.assertTrue(false, "Warning PopUp MEssage Not Verified :  Actual -  " + msg
											+ "\t Expected : " + NavatarSetUpPageErrorMessage.WarningPopUpMessage1);
									log(LogStatus.FAIL, "Warning PopUp MEssage Not Verified :  Actual -  " + msg
											+ "\t Expected : " + NavatarSetUpPageErrorMessage.WarningPopUpMessage1,
											YesNo.Yes);
								}
							} else {
								appLog.error("Warning PopUp Message Element is null");
								sa.assertTrue(false, "Warning PopUp Message Element is null");
								log(LogStatus.SKIP, "Warning PopUp Message Element is null", YesNo.Yes);
							}

							if (i==0) {
								if (click(driver, nsp.getWarningPopUpNoButton(environment, 10,NavatarSetupSideMenuTab.CommitmentCreation), "No Button",
										action.BOOLEAN)) {
									appLog.info("Clicked on No Button");
								}else{
									appLog.error("Not Able to Click on No Button");
									sa.assertTrue(false, "Not Able to Click on No Button");
									log(LogStatus.SKIP, "Not Able to Click on No Button", YesNo.Yes);
								}
							} else if (i==1){
								
								if (click(driver, nsp.getWarningPopUpCrossIcon(environment, 10), "Cross Icon",action.BOOLEAN)) {
									appLog.info("Clicked on Cross Icon");
								}else{
									appLog.error("Not Able to Click on Cross Icon");
									sa.assertTrue(false, "Not Able to Click on Cross Icon");
									log(LogStatus.SKIP, "Not Able to Click on Cross Icon", YesNo.Yes);
								}
								
							}else{
							
								if (click(driver, nsp.getWarningPopUpYesButton(environment, 10,NavatarSetupSideMenuTab.CommitmentCreation), "Yes Button",
										action.BOOLEAN)) {
									appLog.info("Clicked on Yes Button");
									log(LogStatus.INFO, "Clicked on Yes Button", YesNo.No);
									
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
										switchToFrame(driver, 10, nsp.getnavatarSetUpTabFrame_Lighting(environment, 10));
									}
									List<String> result = new ArrayList<String>();
									String[] labels = { "Investment Likely Amount (mn)" };

									result = nsp.verifyLabelInViewModeforNavatarSetUpSideMenuTab(environment, mode,NavatarSetupSideMenuTab.CommitmentCreation,
											NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FundRaisingInformation, labels);

									if (!result.isEmpty() || (result.contains("Investment Likely Amount (mn)"))) {
										log(LogStatus.INFO, "Label Verified on FundRaising Information ", YesNo.No);
									} else {
										appLog.error("Label Not Verified for FundRaising Information ");
										sa.assertTrue(false, "Label Not Verified for FundRaising Information ");
										log(LogStatus.FAIL, "Label Not Verified for FundRaising Information ", YesNo.Yes);
									}

									result.clear();
									String[] l1 = { "Placement Fee %" };
									result = nsp.verifyLabelInViewModeforNavatarSetUpSideMenuTab(environment, mode,NavatarSetupSideMenuTab.CommitmentCreation,
											NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_AdditionalInformation, l1);
									if (!result.isEmpty() || (result.contains("Placement Fee %"))) {
										log(LogStatus.INFO, "Label Verified on Additional Information ", YesNo.No);
									} else {
										appLog.error("Label Not Verified for Additional Information ");
										sa.assertTrue(false, "Label Not Verified for Additional Information ");
										log(LogStatus.FAIL, "Label Not Verified for Additional Information ", YesNo.Yes);
									}

									result.clear();
									String[] l2 = { excelLabel.Bank_Name.toString() };
									result = nsp.verifyLabelInViewModeforNavatarSetUpSideMenuTab(environment, mode,NavatarSetupSideMenuTab.CommitmentCreation,
											NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FieldsForNewLimitedPartner, l2);

									if (!result.isEmpty() || (result.contains(excelLabel.Bank_Name.toString()))) {
										log(LogStatus.INFO, "Label Verified on Fields For New Limited Partner" , YesNo.No);
									} else {
										appLog.error("Label Not Verified for Fields For New Limited Partner");
										sa.assertTrue(false, "Label Not Verified for Fields For New Limited Partner");
										log(LogStatus.FAIL, "Label Not Verified for Fields For New Limited Partner", YesNo.Yes);
									}
									
									result.clear();
									String[] l3 = { "Fund Investment Category" };
									result = nsp.verifyLabelInViewModeforNavatarSetUpSideMenuTab(environment, mode,NavatarSetupSideMenuTab.CommitmentCreation,
											NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FieldsForNewPartnership, l3);

									if (!result.isEmpty() || (result.contains("Fund Investment Category"))) {
										log(LogStatus.INFO, "Label Verified on Fields For New Partnership" , YesNo.No);
									} else {
										appLog.error("Label Not Verified for Fields For New Partnership");
										sa.assertTrue(false, "Label Not Verified for Fields For New Partnership");
										log(LogStatus.FAIL, "Label Not Verified for Fields For New Partnership", YesNo.Yes);
									}

								} else {
									appLog.error("Not Able to Click on Yes Button");
									sa.assertTrue(false, "Not Able to Click on Yes Button");
									log(LogStatus.SKIP, "Not Able to Click on Yes Button", YesNo.Yes);
								}
								
							}
					

						} else {
							appLog.error("Not Able to Click on Revert to Default Button");
							sa.assertTrue(false, "Not Able to Click on Revert to Default Button");
							log(LogStatus.SKIP, "Not Able to Click on Revert to Default Button", YesNo.Yes);
						}
						
					}
					
					} else {
						appLog.error("Not Able to Click on Edit Button");
						sa.assertTrue(false, "Not Able to Click on Edit Button");
						log(LogStatus.SKIP, "Not Able to Click on Edit Button", YesNo.Yes);
					}
				}else{
					sa.assertTrue(false, "Not Able to Click on Commitment Creation Tab");
					log(LogStatus.SKIP, "Not Able to Click on Commitment Creation Tab", YesNo.Yes);
				}
			} else {
					appLog.error("Not Able to Click on Navatar SetUp");
					sa.assertTrue(false, "Not Able to Click on Edit Button");
					log(LogStatus.SKIP, "Not Able to Click on Navatar SetUp", YesNo.Yes);
			}


		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	// functionality not exists so remove all scenario of that
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc045_1_VerifyFundDrawdownPage(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		CapitalCallsPageBusinessLayer ccp = new CapitalCallsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String fields = "Total Commitments<break>Total Capital Called<break>Total Recallable Capital";
		String totalCommitments = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.Total_Commitments);
		String percent = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.Percent);
		// 75000000 Total_Commitments 10%  percent  7500000
		String percentValues = "80,10,10";
		String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/YYYY");
		String OneDayAfterDate=previousOrForwardDate(1, "M/d/YYYY");


		String callAmount = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.CallAmount);
		String capitalAmount = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.CapitalAmount);
		String ManagementFee = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.ManagementFee);
		String OtherFee = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.OtherFee);
		double callAmountD = Double.parseDouble(callAmount);
		double dcapitalAmount = Double.parseDouble(capitalAmount);
		double dManagementFee = Double.parseDouble(ManagementFee);
		double dOtherFee = Double.parseDouble(OtherFee);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele = null;
		if (lp.clickOnTab(environment, mode, TabName.FundsTab)) {
			if (fp.clickOnCreatedFund(environment, mode, Smoke_Fund1)) {

				// Azhar Start

				for (int i = 1; i <= 2; i++) {
					ThreadSleep(2000);
					if (bp.clickOnShowMoreActionDownArrow(environment,  PageName.FundsPage, ShowMoreActionDropDownList.Create_Drawdown, 60)) {
						log(LogStatus.INFO, "clicked on Create_Drawdowns", YesNo.No);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())){
							switchToFrame(driver, 30, fd.getEmailingFrame_Lighting(30));
						}

						if (i==1) {
							ele = fd.getFundDrawDownBackArrowLink(environment,mode, 60);
						} else {
							ele = fd.getfundDrawDownCancelButton(environment,mode, 60);
						}

						if (clickUsingJavaScript(driver, ele, "Back Arrow/Cancel Btn",action.BOOLEAN)) {
							appLog.info("Clicked on Back Arrow/Cancel");
						}else{
							appLog.error("Not Able to Click on Back Arrow/Cancel Btn");
							sa.assertTrue(false, "Not Able to Click on Back Arrow/Cancel Btn");
							log(LogStatus.SKIP, "Not Able to Click on Back Arrow/Cancel Btn", YesNo.Yes);
						}

					}else{
						log(LogStatus.ERROR, "could not click on show more dropdown : "+ShowMoreActionDropDownList.Create_Drawdown, YesNo.Yes);
						sa.assertTrue(false, "could not click on show more dropdown : "+ShowMoreActionDropDownList.Create_Drawdown);

					}
					switchToDefaultContent(driver);
					ThreadSleep(5000);
				}


				switchToDefaultContent(driver);
				// Azhar End

				if (bp.clickOnShowMoreActionDownArrow(environment, PageName.FundsPage, ShowMoreActionDropDownList.Create_Drawdown, 30)) {
					log(LogStatus.INFO, "clicked on Create_Drawdowns", YesNo.No);
					/*if (mode.equalsIgnoreCase(Mode.Lightning.toString())){
						switchToFrame(driver, 30, fd.getEmailingFrame_Lighting(30));
					}*/
					//capital amount, management fee, other fee textbox
					String[] linkClick = {Smoke_LP1,"CMT"};
					for (int j = 0; j < linkClick.length; j++) {
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())){
							switchToFrame(driver, 30, fd.getEmailingFrame_Lighting(30));
						}
						if (click(driver, fd.getPercentageRadioBtn(30), "percentage radio button", action.SCROLLANDBOOLEAN)) {
							appLog.info("Click on %age radio button");
							if (sendKeys(driver, fd.getPercentageTextboxCreateDrawdown(30), percent, "percentage textbox", action.BOOLEAN)) {
								appLog.info("Enter value on %age text box : "+percent);
								sendKeys(driver, fd.getCapitalAmountTextboxCreateDrawdown(PercentOrValue.Percent, 30), percentValues.split(",")[0], "capital amount", action.SCROLLANDBOOLEAN);
								sendKeys(driver, fd.getManagementFeeTextboxCreateDrawdown(PercentOrValue.Percent, 30), percentValues.split(",")[1], "management fee", action.SCROLLANDBOOLEAN);
								sendKeys(driver, fd.getOtherFeeTextBoxCreateDrawdown(PercentOrValue.Percent, 30), percentValues.split(",")[2], "other fee", action.SCROLLANDBOOLEAN);

								sendKeys(driver, fd.getDueDateValue(30),OneDayAfterDate , "due date", action.BOOLEAN);
								sendKeys(driver, fd.getCallDateTextbox(30),date , "due date", action.BOOLEAN);


								if (click(driver, fd.getSetupCapitalCalls(30), "setup capital call sbutton", action.SCROLLANDBOOLEAN)) {

									// Azhar Start 


									String xpath;

									if (j==0) {
										xpath = "//div[@class='individualPalette']//td/a";	
									} else {
										xpath = "(//div[@class='individualPalette']//td/a)[2]";
									}
								
									ele = FindElement(driver, xpath, linkClick[j], action.SCROLLANDBOOLEAN, 10);

									if (ele!=null) {
										appLog.info("Clicked on : "+linkClick[j]);
										if (click(driver, ele, linkClick[j], action.SCROLLANDBOOLEAN)) {
											switchToDefaultContent(driver);
											if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
												xpath ="//div[contains(@class,'outputName')]//*[text()='"+linkClick[j]+"']";
											}else{
												xpath = "//h2[contains(text(),'"+linkClick[j]+"')]";	
											}

											ele = FindElement(driver, xpath, "on same window : "+linkClick[j], action.BOOLEAN, 10);

											if (ele!=null) {
												appLog.info("Landing Page Verified : "+linkClick[j]);
											} else {
												appLog.error("Landing Page Not Verified : "+linkClick[j]);
												sa.assertTrue(false, "Landing Page Not Verified : "+linkClick[j]);
											}
											
											driver.navigate().back();

										} else {
											appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
											sa.assertTrue(false, "Not able to click on "+linkClick[j]+" so cannot verify landing page");
										}
									} else {
										appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
										sa.assertTrue(false, "Not able to click on "+linkClick[j]+" so cannot verify landing page");
									}




									// Azhar End

								} else {
									log(LogStatus.ERROR, "Not Able to Click on SetUp", YesNo.Yes);
									sa.assertTrue(false, "Not Able to Enter %age Values");	
								}


							}else{
								log(LogStatus.ERROR, "Not Able to Enter %age Values", YesNo.Yes);
								sa.assertTrue(false, "Not Able to Enter %age Values");	
							}




						}
						else {
							log(LogStatus.ERROR, "percentage radio button is not clickable", YesNo.Yes);
							sa.assertTrue(false, "percentage radio button is not clickable");
						}
						switchToDefaultContent(driver);
					}

				}
				else {
					log(LogStatus.ERROR, "could not click on show more dropdown", YesNo.Yes);
					sa.assertTrue(false, "could not click on show more dropdown");
				}
			}
			else {
				log(LogStatus.ERROR, "could not find created fund on funds page", YesNo.Yes);
				sa.assertTrue(false,  "could not find created fund on funds page");
			}
		}
		else {
			log(LogStatus.ERROR, "funds tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "funds tab is not clickable");
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc045_2_VerifyFundDrawdownPage(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		CapitalCallsPageBusinessLayer ccp = new CapitalCallsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String fields = "Total Commitments<break>Total Capital Called<break>Total Recallable Capital";
		String totalCommitments = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.Total_Commitments);
		String percent = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.Percent);
		// 75000000 Total_Commitments 10%  percent  7500000
		String percentValues = "80,10,10";
		String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "M/d/YYYY");
		String OneDayAfterDate=previousOrForwardDate(1, "M/d/YYYY");
		
		for (int i =0;i<5;i++) {
			ExcelUtils.writeData(smokeFilePath,date ,"CapitalCall",excelLabel.Variable_Name, "CC"+(i+1), excelLabel.CallDate);
			ExcelUtils.writeData(smokeFilePath,OneDayAfterDate ,"CapitalCall",excelLabel.Variable_Name, "CC"+(i+1), excelLabel.DueDate);
		}
		ExcelUtils.writeData(smokeFilePath,date ,"FundDrawdown",excelLabel.Variable_Name, "DD1", excelLabel.CallDate);
		ExcelUtils.writeData(smokeFilePath, OneDayAfterDate ,"FundDrawdown",excelLabel.Variable_Name, "DD1", excelLabel.DueDate);
		System.err.println(">>>>>>>>>>>>> INNNN");
		settingsBeforeTests();
		
		String callAmount = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.CallAmount);
		String capitalAmount = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.CapitalAmount);
		String ManagementFee = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.ManagementFee);
		String OtherFee = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.OtherFee);
		double callAmountD = Double.parseDouble(callAmount);
		double dcapitalAmount = Double.parseDouble(capitalAmount);
		double dManagementFee = Double.parseDouble(ManagementFee);
		double dOtherFee = Double.parseDouble(OtherFee);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele = null;
		if (lp.clickOnTab(environment, mode, TabName.FundsTab)) {
			log(LogStatus.INFO, "clicked on Fund Tab", YesNo.No);
			if (fp.clickOnCreatedFund(environment, mode, Smoke_Fund1)) {
				log(LogStatus.INFO, "clicked on Fund : "+Smoke_Fund1, YesNo.No);
					if (bp.clickOnShowMoreActionDownArrow(environment, PageName.FundsPage, ShowMoreActionDropDownList.Create_Drawdown, 30)) {
						log(LogStatus.INFO, "clicked on Create_Drawdowns", YesNo.No);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString()))
						switchToFrame(driver, 30, fd.getEmailingFrame_Lighting(30));
						if (fd.getValueInCreateDrawdownsOrDistributionsPage(fields.split("<break>")[0])!=null) {
							String totalCommitments1 = 	bp.convertNumberAccordingToFormatWithCurrencySymbol(totalCommitments, "0,000.00");
							
							if (fd.getValueInCreateDrawdownsOrDistributionsPage(fields.split("<break>")[0]).equalsIgnoreCase(totalCommitments1)) {
								log(LogStatus.INFO, "successfully verified Total Commitments value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify Total Commitments value", YesNo.Yes);
								sa.assertTrue(false, "could not verify Total Commitments value");
							}
						}
						if(mode.equalsIgnoreCase(Mode.Classic.toString())){
							click(driver, fd.getTotalCapitalCalled(30), "capital call", action.BOOLEAN);
						}
						if (fd.getValueInCreateDrawdownsOrDistributionsPage(fields.split("<break>")[1])!=null) {
							if (fd.getValueInCreateDrawdownsOrDistributionsPage(fields.split("<break>")[1]).contains("")) {
								log(LogStatus.INFO, "successfully verified total capital called value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify total capital called value", YesNo.Yes);
								sa.assertTrue(false, "could not verify total capital called value");
							}
						}
						else {
							log(LogStatus.INFO, "no value is written in total capital called as expected", YesNo.No);
						}
						if (fd.getValueInCreateDrawdownsOrDistributionsPage(fields.split("<break>")[2])!=null) {
							if (fd.getValueInCreateDrawdownsOrDistributionsPage(fields.split("<break>")[2]).contains("")) {
								log(LogStatus.INFO, "successfully verified total recallable capital value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify total recallable capital value", YesNo.Yes);
								sa.assertTrue(false, "could not verify total recallable capital value");
							}
						}
						else {
							log(LogStatus.INFO, "no value is written in total capital called as expected", YesNo.No);
						}
						
						//capital amount, management fee, other fee textbox
						click(driver, fp.getTotalCommitment(30), "total commitment", action.BOOLEAN);
						String text = fd.getCapitalAmountTextboxCreateDrawdown(PercentOrValue.Value,30).getAttribute("value");
						if (text.contains("0.00")) {
							log(LogStatus.INFO,"successfully verified capital amount textbox value", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify capital amount textbox value. Expected: 0.00\tActual: "+text, YesNo.Yes);
							sa.assertTrue(false, "could not verify capital amount textbox value. Expected: 0.00\tActual: "+text);
						}
						if (getValueFromElementUsingJavaScript(driver, fd.getManagementFeeTextboxCreateDrawdown(PercentOrValue.Value,30), "ManagementFee value").contains("0.0")) {
							log(LogStatus.INFO,"successfully verified ManagementFee textbox value", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify ManagementFee textbox value", YesNo.Yes);
							sa.assertTrue(false, "could not verify ManagementFee textbox value");
						}
						if (getValueFromElementUsingJavaScript(driver, fd.getOtherFeeTextBoxCreateDrawdown(PercentOrValue.Value,30), "OtherFee value").contains("0.0")) {
							log(LogStatus.INFO,"successfully verified OtherFee textbox value", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify OtherFee textbox value", YesNo.Yes);
							sa.assertTrue(false, "could not verify OtherFee textbox value");
						}
						if (fd.getCapitalCallValueCreateDrawdown(30).getText().trim().contains("0.00")) {
							log(LogStatus.INFO, "successfully verified capital call value", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify capital call amount value", YesNo.Yes);
							sa.assertTrue(false, "could not verify capital call amount value");
						}
						if (click(driver, fd.getPercentageRadioBtn(30), "percentage radio button", action.SCROLLANDBOOLEAN)) {
							if (sendKeys(driver, fd.getPercentageTextboxCreateDrawdown(30), percent, "percentage textbox", action.BOOLEAN)) {
								
							}
							sendKeys(driver, fd.getCapitalAmountTextboxCreateDrawdown(PercentOrValue.Percent, 30), percentValues.split(",")[0], "capital amount", action.SCROLLANDBOOLEAN);
							sendKeys(driver, fd.getManagementFeeTextboxCreateDrawdown(PercentOrValue.Percent, 30), percentValues.split(",")[1], "management fee", action.SCROLLANDBOOLEAN);
							sendKeys(driver, fd.getOtherFeeTextBoxCreateDrawdown(PercentOrValue.Percent, 30), percentValues.split(",")[2], "other fee", action.SCROLLANDBOOLEAN);
							
							sendKeys(driver, fd.getDueDateValue(30),OneDayAfterDate , "due date", action.BOOLEAN);
							sendKeys(driver, fd.getCallDateTextbox(30),date , "due date", action.BOOLEAN);
							//verify values present in capital amount, management fee, and other fee
							String a=getValueFromElementUsingJavaScript(driver, fd.getCapitalAmountTextboxCreateDrawdown(PercentOrValue.Value, 30), "value textbox capital call");
							if (a.trim().contains(capitalAmount)) {
								log(LogStatus.INFO,"successfully verified capital amount textbox value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify capital amount textbox value"+a+" and "+capitalAmount,YesNo.Yes);
								sa.assertTrue(false, "could not verify capital amount textbox value");
							}
							a=getValueFromElementUsingJavaScript(driver, fd.getManagementFeeTextboxCreateDrawdown(PercentOrValue.Value, 30), "value textbox ManagementFee");
							if (a.trim().contains(ManagementFee)) {
								log(LogStatus.INFO,"successfully verified ManagementFee textbox value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify ManagementFee textbox value"+a+" and "+ManagementFee,YesNo.Yes);
								sa.assertTrue(false, "could not verify ManagementFee textbox value");
							}
							a=getValueFromElementUsingJavaScript(driver, fd.getOtherFeeTextBoxCreateDrawdown(PercentOrValue.Value, 30), "value textbox OtherFee");
							if (a.trim().contains(OtherFee)) {
								log(LogStatus.INFO,"successfully verified OtherFee textbox value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify OtherFee textbox value"+a+ " and "+OtherFee, YesNo.Yes);
								sa.assertTrue(false, "could not verify OtherFee textbox value");
							}
							String callAmount1=bp.convertNumberAccordingToFormatWithCurrencySymbol(callAmount, "0,000.00");
							
							if (getText(driver,fd.getCapitalCallValueCreateDrawdown(30), "capital call value", action.BOOLEAN).trim().contains(callAmount1)) {
								log(LogStatus.INFO, "call amount verified successfully", YesNo.No);
							}
							else {
								log(LogStatus.ERROR,"call amount value could not be verified"+getText(driver,fd.getCapitalCallValueCreateDrawdown(30), "capital call value", action.BOOLEAN)+ " and "+callAmount, YesNo.Yes);
								sa.assertTrue(false, "call amount value could not be verified");
							}
							
							click(driver, fd.getSetupCapitalCalls(30), "setup capital call sbutton", action.SCROLLANDBOOLEAN);
							
							String[][] capitalCallGrids = {
									{Smoke_LP1,SmokeCOMM1_ID,SmokeCOMM1_CommitmentAmount,fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM1_ID, SmokeCOMM1_CommitmentAmount, totalCommitments, capitalAmount),
										fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM1_ID, SmokeCOMM1_CommitmentAmount, totalCommitments, ManagementFee),fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM1_ID, SmokeCOMM1_CommitmentAmount, totalCommitments, OtherFee)
							},{
								Smoke_LP2,SmokeCOMM2_ID,SmokeCOMM2_CommitmentAmount,fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM2_ID, SmokeCOMM2_CommitmentAmount, totalCommitments, capitalAmount),
								fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM2_ID, SmokeCOMM2_CommitmentAmount, totalCommitments, ManagementFee),fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM2_ID, SmokeCOMM2_CommitmentAmount, totalCommitments, OtherFee)
							},{
								Smoke_LP1,SmokeCOMM3_ID,SmokeCOMM3_CommitmentAmount,fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM3_ID, SmokeCOMM3_CommitmentAmount, totalCommitments, capitalAmount),
								fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM3_ID, SmokeCOMM3_CommitmentAmount, totalCommitments, ManagementFee),fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM3_ID, SmokeCOMM3_CommitmentAmount, totalCommitments, OtherFee)
							},{
								Smoke_LP3,SmokeCOMM4_ID,SmokeCOMM4_CommitmentAmount,fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM4_ID, SmokeCOMM4_CommitmentAmount, totalCommitments, capitalAmount),
								fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM4_ID, SmokeCOMM4_CommitmentAmount, totalCommitments, ManagementFee),fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM4_ID, SmokeCOMM4_CommitmentAmount, totalCommitments, OtherFee)
							},{
								Smoke_LP4,SmokeCOMM5_ID,SmokeCOMM5_CommitmentAmount,fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM5_ID, SmokeCOMM5_CommitmentAmount, totalCommitments, capitalAmount),
								fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM5_ID, SmokeCOMM5_CommitmentAmount, totalCommitments, ManagementFee),fd.CapitalAmountAndManagementFeeAndOtherFee(SmokeCOMM5_ID, SmokeCOMM5_CommitmentAmount, totalCommitments, OtherFee)
							}
							};
							
							for (int i =0;i<capitalCallGrids.length;i++) {
								if (fd.verifyOneRowCreateDrawdownPage(capitalCallGrids[i])) {
									log(LogStatus.INFO, "data for row "+i+" was successfully verified", YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "data for row "+i+" could not be verified", YesNo.Yes);
									sa.assertTrue(false, "data for row "+i +" could not be verified");
								}
							}
							
							List<WebElement> commitments = fd.getCommitmentListFromCapitalCall(environment, mode);
							List<WebElement> capitalAmounts = fd.getCapitalAmountListFromCapitalCall(environment, mode);
							List<WebElement> managementFees = fd.getManagementFeeAmountListFromCapitalCall(environment, mode);
							List<WebElement> otherFees = fd.getOtherFeeAmountListFromCapitalCall(environment, mode);
							List<WebElement> totalAmount = fd.getTotalAmountListFromCapitalCall(environment, mode);
							String commitName;
							String totalAmounts;
							String convertValue;
							String amount;
							
							if (!commitments.isEmpty()) {
								
								if (commitments.size() == capitalAmounts.size()) {
									
									for (int i = 0; i < commitments.size(); i++) {
										commitName = commitments.get(i).getText().trim();
										ExcelUtils.writeData(smokeFilePath, commitName, "CapitalCall", excelLabel.Variable_Name, "CC"+(i+1), excelLabel.Commitment_ID);
										
										amount = capitalAmounts.get(i).getAttribute("value").trim();
										amount=fd.convertNumberAccordingToFormatWithoutCurrencySymbol(amount, "0000");
										ExcelUtils.writeData(smokeFilePath, amount, "CapitalCall", excelLabel.Variable_Name, "CC"+(i+1), excelLabel.CapitalAmount);
										
										amount = managementFees.get(i).getAttribute("value").trim();
										amount=fd.convertNumberAccordingToFormatWithoutCurrencySymbol(amount, "0000");
										ExcelUtils.writeData(smokeFilePath, amount, "CapitalCall", excelLabel.Variable_Name, "CC"+(i+1), excelLabel.ManagementFee);
										
										amount = otherFees.get(i).getAttribute("value").trim();
										amount=fd.convertNumberAccordingToFormatWithoutCurrencySymbol(amount, "0000");
										ExcelUtils.writeData(smokeFilePath, amount, "CapitalCall", excelLabel.Variable_Name, "CC"+(i+1), excelLabel.OtherFee);
										
										totalAmounts = totalAmount.get(i).getText().trim();
										totalAmounts=fd.convertNumberAccordingToFormatWithoutCurrencySymbol(totalAmounts, "0000");
										ExcelUtils.writeData(smokeFilePath, totalAmounts, "CapitalCall", excelLabel.Variable_Name, "CC"+(i+1), excelLabel.CallAmount);
										
										if (commitName.equalsIgnoreCase(SmokeCOMM1_ID)) {
											ExcelUtils.writeData(smokeFilePath, totalAmounts, "CapitalCall", excelLabel.Variable_Name, "CC"+(i+1), excelLabel.CallAmountReceived);
											ExcelUtils.writeData(smokeFilePath, previousOrForwardDate(0, "M/d/yyyy"), "CapitalCall", excelLabel.Variable_Name, "CC"+(i+1), excelLabel.ReceivedDate);
												
										}
										
										convertValue=fd.convertNumberAccordingToFormatWithoutCurrencySymbol(totalAmounts, "0,000.000");
										ExcelUtils.writeData(smokeFilePath, convertValue, "CapitalCall", excelLabel.Variable_Name, "CC"+(i+1), excelLabel.AmountDue);
									}
									
								} else {
									log(LogStatus.ERROR, "cOLUMN lIST ARTE NOT eQUAL", YesNo.Yes);
									sa.assertTrue(false, "cOLUMN lIST ARTE NOT eQUAL");
								}
								
							} else {
								log(LogStatus.ERROR, "cAPITAL CALL Grid is empty", YesNo.Yes);
								sa.assertTrue(false, "cAPITAL CALL Grid is empty");
							}
							
						if (click(driver, fd.getGenerateCapitalCall(30), "generate capital call",
								action.SCROLLANDBOOLEAN)) {
							// fund detail page
							ThreadSleep(3000);
							String FDID = null;
							if (fd.openAppFromAppLauchner(ObjectName.Fund_Drawdowns.toString(), 10)) {

								log(LogStatus.PASS, "Able to open fund drawdown object ", YesNo.No);
								FDID = fp.getFirstValueFromRelatedList(environment, mode,
										RelatedList.FundDrawdown.toString(), Smoke_Fund1).getText().trim();

							} else {
								log(LogStatus.ERROR, "Not Able to open fund drawdown object ", YesNo.Yes);
								sa.assertTrue(false, "Not Able to open fund drawdown object ");
							}

							ThreadSleep(2000);
							if (FDID != null || !FDID.isEmpty() || !FDID.equalsIgnoreCase("")) {

								ExcelUtils.writeData(smokeFilePath, FDID, "FundDrawdown", excelLabel.Variable_Name,
										"DD1", excelLabel.DrawdownID);
								for (int i = 0; i < 5; i++) {
									ExcelUtils.writeData(smokeFilePath, FDID, "CapitalCall", excelLabel.Variable_Name,
											"CC" + (i + 1), excelLabel.DrawdownID);

								}

								ThreadSleep(2000);

								if (clickUsingJavaScript(driver,fp.getFirstValueFromRelatedList(environment, mode,RelatedList.FundDrawdown.toString(), Smoke_Fund1),
										"fund drawdown id", action.SCROLLANDBOOLEAN)) {
									ThreadSleep(4000);

									if (fd.ClickonRelatedTab_Lighting(environment, RecordType.Fund, null)) {

										if (bp.clickOnViewAllRelatedList(environment, mode, RelatedList.CapitalCalls)) {
											log(LogStatus.PASS,
													" Able to Click on View All for " + RelatedList.CapitalCalls,
													YesNo.No);
										} else {
											log(LogStatus.ERROR,
													"Not Able to Click on View All for " + RelatedList.CapitalCalls,
													YesNo.Yes);
											sa.assertTrue(false,
													"Not Able to Click on View All for " + RelatedList.CapitalCalls);
										}

									} else {
										log(LogStatus.ERROR, "Not Able to Click on Related List ", YesNo.Yes);
										sa.assertTrue(false, "Not Able to Click on Related List ");
									}
									List<String> s = fp.returnAllValuesInRelatedList(environment, mode,
											RelatedList.CapitalCalls.toString());
									for (int i = 0; i < 5; i++) {
										ExcelUtils.writeData(smokeFilePath, s.get(i), "CapitalCall",
												excelLabel.Variable_Name, "CC" + (i + 1), excelLabel.CapitalCalllID);
									}

								}
								else {
									log(LogStatus.ERROR, "fund drawdown link is not clickable", YesNo.Yes);
									sa.assertTrue(false, "fund drawdown link is not clickable");
								}
								}else {
									log(LogStatus.ERROR, "fund drawdown is not present so cannot verify ", YesNo.Yes);
									sa.assertTrue(false, "fund drawdown is not present so cannot verify ");
								}
							}
							else {
								log(LogStatus.ERROR, "generate capital call button is not clickable", YesNo.Yes);
								sa.assertTrue(false, "generate capital call button is not clickable");
							}
						}
						else {
							log(LogStatus.ERROR, "percentage radio button is not clickable", YesNo.Yes);
							sa.assertTrue(false, "percentage radio button is not clickable");
						}
					}
					else {
						log(LogStatus.ERROR, "could not click on show more dropdown", YesNo.Yes);
						sa.assertTrue(false, "could not click on show more dropdown");
					}
				}
			else {
				log(LogStatus.ERROR, "could not find created fund on funds page", YesNo.Yes);
				sa.assertTrue(false,  "could not find created fund on funds page");
			}
		}
		else {
			log(LogStatus.ERROR, "funds tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "funds tab is not clickable");
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc046_VerifyCreatedCapitalCalls(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		CapitalCallsPageBusinessLayer ccp = new CapitalCallsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String[] labels = {CapitalCallPageFieldLabelText.Fund_Drawdown.toString(),
				CapitalCallPageFieldLabelText.Capital_Amount.toString(),
				CapitalCallPageFieldLabelText.Management_Fee.toString(),
				CapitalCallPageFieldLabelText.Other_Fee.toString(),
				CapitalCallPageFieldLabelText.Call_Amount.toString(),
				CapitalCallPageFieldLabelText.Call_Date.toString(),
				CapitalCallPageFieldLabelText.Due_Date.toString(),
				CapitalCallPageFieldLabelText.Call_Amount_Received.toString(),
				CapitalCallPageFieldLabelText.Received_Date.toString(),
				CapitalCallPageFieldLabelText.Amount_Due.toString(),
				CapitalCallPageFieldLabelText.Commitment.toString()
				};
		if (bp.clickOnTab(environment, mode, TabName.CapitalCalls)) {
			//cc1
			if (ccp.clickOnCreatedCapitalCall(environment, mode, SmokeCC1_ID)) {
				appLog.info("on cc 1 page");
				for (int i = 0;i<6;i++) {
					
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC1Data[i], EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+labels[i], YesNo.No);
					}
					else {
						
						log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+labels[i]);
					}
				}
				
				try {
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Call_Amount_Received.toString() , "", EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), YesNo.No);
					}
					else {

						log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString());
					} }
				catch(Exception e) {
					log(LogStatus.INFO, "could not find text in front of capital amount received(success)", YesNo.No);
				}
				
				try {
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Received_Date.toString() , "", EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+CapitalCallPageFieldLabelText.Received_Date.toString(), YesNo.No);
					}
					else {

						log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Received_Date.toString(), YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Received_Date.toString());
					} }
				catch(Exception e) {
					log(LogStatus.INFO, "could not find text in front of received date(success)", YesNo.No);
				}
				for (int i = 9;i<=10;i++) {
					
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC1Data[i], EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+labels[i], YesNo.No);
					}
					else {
						
						log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+labels[i]);
					}
				}
			}
			else {
				log(LogStatus.ERROR, "could not find capital call 1 id on capital calls tab", YesNo.Yes);
				sa.assertTrue(false, "could not find capital call 1 id on capital calls tab");
			}
		}
		else {
			log(LogStatus.ERROR, "could not find capital calls tab", YesNo.Yes);
			sa.assertTrue(false, "could not find capital calls tab");
		}
		//cc2
		if (bp.clickOnTab(environment, mode, TabName.CapitalCalls)) {
			if (ccp.clickOnCreatedCapitalCall(environment, mode, SmokeCC2_ID)) {
				appLog.info("on cc 2 page");
				for (int i = 0;i<6;i++) {
					
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC2Data[i], EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+labels[i], YesNo.No);
					}
					else {
						
						log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+labels[i]);
					}
				}
				try {
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Call_Amount_Received.toString() , "", EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), YesNo.No);
					}
					else {

						log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString());
					} }
				catch(Exception e) {
					log(LogStatus.INFO, "could not find text in front of capital amount received(success)", YesNo.No);
				}
				
				try {
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Received_Date.toString() , "", EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+CapitalCallPageFieldLabelText.Received_Date.toString(), YesNo.No);
					}
					else {

						log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Received_Date.toString(), YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Received_Date.toString());
					} }
				catch(Exception e) {
					log(LogStatus.INFO, "could not find text in front of received date(success)", YesNo.No);
				}
				for (int i = 9;i<=10;i++) {
					
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC2Data[i], EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+labels[i], YesNo.No);
					}
					else {
						
						log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+labels[i]);
					}
				}
				}
			else {
				log(LogStatus.ERROR, "could not find capital call 2 id on capital calls tab", YesNo.Yes);
				sa.assertTrue(false, "could not find capital call 2 id on capital calls tab");
			}
		}
		else {
			log(LogStatus.ERROR, "could not find capital calls tab", YesNo.Yes);
			sa.assertTrue(false, "could not find capital calls tab");
		}
		//cc3
		if (bp.clickOnTab(environment, mode, TabName.CapitalCalls)) {
			if (ccp.clickOnCreatedCapitalCall(environment, mode, SmokeCC3_ID)) {
				appLog.info("on cc 3 page");
				
				for (int i = 0;i<6;i++) {
					
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC3Data[i], EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+labels[i], YesNo.No);
					}
					else {
						
						log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+labels[i]);
					}
				}
				try {
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Call_Amount_Received.toString() , "", EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), YesNo.No);
					}
					else {

						log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString());
					} }
				catch(Exception e) {
					log(LogStatus.INFO, "could not find text in front of capital amount received(success)", YesNo.No);
				}
				
				try {
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Received_Date.toString() , "", EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+CapitalCallPageFieldLabelText.Received_Date.toString(), YesNo.No);
					}
					else {

						log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Received_Date.toString(), YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Received_Date.toString());
					} }
				catch(Exception e) {
					log(LogStatus.INFO, "could not find text in front of received date(success)", YesNo.No);
				}
				for (int i = 9;i<=10;i++) {
					
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC3Data[i], EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+labels[i], YesNo.No);
					}
					else {
						
						log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+labels[i]);
					}
				}
			}
			else {
				log(LogStatus.ERROR, "could not find capital call 3 id on capital calls tab", YesNo.Yes);
				sa.assertTrue(false, "could not find capital call 3 id on capital calls tab");
			}
		}
		else {
			log(LogStatus.ERROR, "could not find capital calls tab", YesNo.Yes);
			sa.assertTrue(false, "could not find capital calls tab");
		}
		//cc4
		if (bp.clickOnTab(environment, mode, TabName.CapitalCalls)) {
			if (ccp.clickOnCreatedCapitalCall(environment, mode, SmokeCC4_ID)) {
				appLog.info("on cc 4 page");
				for (int i = 0;i<6;i++) {
					
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC4Data[i], EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+labels[i], YesNo.No);
					}
					else {
						
						log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+labels[i]);
					}
				}
				try {
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Call_Amount_Received.toString() , "", EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), YesNo.No);
					}
					else {

						log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString());
					} }
				catch(Exception e) {
					log(LogStatus.INFO, "could not find text in front of capital amount received(success)", YesNo.No);
				}
				
				try {
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Received_Date.toString() , "", EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+CapitalCallPageFieldLabelText.Received_Date.toString(), YesNo.No);
					}
					else {

						log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Received_Date.toString(), YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Received_Date.toString());
					} }
				catch(Exception e) {
					log(LogStatus.INFO, "could not find text in front of received date(success)", YesNo.No);
				}
				for (int i = 9;i<=10;i++) {
					
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC4Data[i], EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+labels[i], YesNo.No);
					}
					else {
						
						log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+labels[i]);
					}
				}
			}
			else {
				log(LogStatus.ERROR, "could not find capital call 4 id on capital calls tab", YesNo.Yes);
				sa.assertTrue(false, "could not find capital call 4 id on capital calls tab");
			}
		}
		else {
			log(LogStatus.ERROR, "could not find capital calls tab", YesNo.Yes);
			sa.assertTrue(false, "could not find capital calls tab");
		}
		//cc5
		if (bp.clickOnTab(environment, mode, TabName.CapitalCalls)) {
			if (ccp.clickOnCreatedCapitalCall(environment, mode, SmokeCC5_ID)) {
				appLog.info("on cc 5 page");
				for (int i = 0;i<6;i++) {
					
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC5Data[i], EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+labels[i], YesNo.No);
					}
					else {
						
						log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+labels[i]);
					}
				}
				try {
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Call_Amount_Received.toString() , "", EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), YesNo.No);
					}
					else {

						log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString());
					} }
				catch(Exception e) {
					log(LogStatus.INFO, "could not find text in front of capital amount received(success)", YesNo.No);
				}
				
				try {
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Received_Date.toString() , "", EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+CapitalCallPageFieldLabelText.Received_Date.toString(), YesNo.No);
					}
					else {

						log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Received_Date.toString(), YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Received_Date.toString());
					} }
				catch(Exception e) {
					log(LogStatus.INFO, "could not find text in front of received date(success)", YesNo.No);
				}
				for (int i = 9;i<=10;i++) {
					
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC5Data[i], EditViewMode.View)) {
						log(LogStatus.INFO, "successfully verified "+labels[i], YesNo.No);
					}
					else {
						
						log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+labels[i]);
					}
				}
			}
			else {
				log(LogStatus.ERROR, "could not find capital call 5 id on capital calls tab", YesNo.Yes);
				sa.assertTrue(false, "could not find capital call 5 id on capital calls tab");
			}
		}
		else {
			log(LogStatus.ERROR, "could not find capital calls tab", YesNo.Yes);
			sa.assertTrue(false, "could not find capital calls tab");
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc047_VerifyEditingCapitalCalls(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		CapitalCallsPageBusinessLayer ccp = new CapitalCallsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fdp = new FundDrawdownsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		SmokeCC3_ID = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.CapitalCalllID);
		//todo received date add todays date
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String[] labels = {CapitalCallPageFieldLabelText.Fund_Drawdown.toString(),
				CapitalCallPageFieldLabelText.Capital_Amount.toString(),
				CapitalCallPageFieldLabelText.Management_Fee.toString(),
				CapitalCallPageFieldLabelText.Other_Fee.toString(),
				CapitalCallPageFieldLabelText.Call_Amount.toString(),
				CapitalCallPageFieldLabelText.Call_Date.toString(),
				CapitalCallPageFieldLabelText.Due_Date.toString(),
				CapitalCallPageFieldLabelText.Call_Amount_Received.toString(),
				CapitalCallPageFieldLabelText.Received_Date.toString(),
				CapitalCallPageFieldLabelText.Amount_Due.toString(),
				CapitalCallPageFieldLabelText.Commitment.toString()
				};
		SmokeCC3Data[0] = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.DrawdownID);
		SmokeCC3Data[1] = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.CapitalAmount);
		SmokeCC3Data[2] = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.ManagementFee);
		SmokeCC3Data[3] = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.OtherFee);
		SmokeCC3Data[4] = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.CallAmount);
		SmokeCC3Data[5] = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.CallDate);
		SmokeCC3Data[6] = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.DueDate);
		SmokeCC3Data[7] = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.CallAmountReceived);
		SmokeCC3Data[8] = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.ReceivedDate);
		SmokeCC3Data[9] = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.AmountDue);
		SmokeCC3Data[10] = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.Commitment_ID);
		String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
		if (bp.clickOnTab(environment, mode, TabName.CapitalCalls)) {
			if (ccp.clickOnCreatedCapitalCall(environment, mode, SmokeCC3_ID)) {
				if (ccp.clickOnShowMoreActionDownArrow("PE", PageName.CapitalCall, ShowMoreActionDropDownList.Edit, 20)) {
					for (int i = 0;i<=3;i++) {
						if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC3Data[i], EditViewMode.Edit)) {
							log(LogStatus.PASS, "successfully verified "+labels[i], YesNo.No);
						}
						else {
							log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
							sa.assertTrue(false, "data could not be verified of "+labels[i]);
						}
					}
					for (int i = 5;i<=6;i++) {
						if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC3Data[i], EditViewMode.Edit)) {
							log(LogStatus.PASS, "successfully verified "+labels[i], YesNo.No);
						}
						else {
							log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
							sa.assertTrue(false, "data could not be verified of "+labels[i]);
						}
					}
					try{
						if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Call_Amount_Received.toString() , "", EditViewMode.Edit)) {
							log(LogStatus.PASS, "successfully verified "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), YesNo.No);

						}
						else {
							log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), YesNo.Yes);
							sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Call_Amount_Received.toString());

						}
					}
					catch(Exception e) {
						log(LogStatus.PASS, "could not find text in front of call amount received(success)", YesNo.No);
					}

					try{
						if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Received_Date.toString() , "", EditViewMode.Edit)) {
							log(LogStatus.PASS, "successfully verified "+CapitalCallPageFieldLabelText.Received_Date.toString(), YesNo.No);

						}
						else {
							log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Received_Date.toString(), YesNo.Yes);
							sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Received_Date.toString());

						}
					}
					catch(Exception e) {
						log(LogStatus.PASS, "could not find text in front of received date(success)", YesNo.No);
					}
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,CapitalCallPageFieldLabelText.Commitment.toString() , SmokeCC3Data[10], EditViewMode.Edit)) {
						log(LogStatus.PASS, "successfully verified "+CapitalCallPageFieldLabelText.Commitment.toString(), YesNo.No);
						
					}
					else {
						log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Commitment.toString(), YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Commitment.toString());
			
					}
					System.err.println("going to enter values");
					if (sendKeys(driver, ccp.getCapitalCallPageTextBoxOrRichTextBoxWebElement(environment, mode, CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), 30), SmokeCC3Data[4], "call amount received", action.BOOLEAN)) {
						if (sendKeys(driver, ccp.getCapitalCallPageTextBoxOrRichTextBoxWebElement(environment, mode,CapitalCallPageFieldLabelText.Received_Date.toString() , 30), SmokeCC3Data[5], "received date", action.SCROLLANDBOOLEAN)) {
							if (click(driver, bp.getCustomTabSaveBtn(environment,  30), "save button", action.SCROLLANDBOOLEAN)) {
							}
							else {
								log(LogStatus.ERROR, "could not click on save button", YesNo.Yes);
								sa.assertTrue(false, "could not click on save button");
							}
						}
						else {
							log(LogStatus.SKIP, "received date textbox is not visible", YesNo.Yes);
							sa.assertTrue(false, "received date textbox is not visible");
						}
					}
					else {
						log(LogStatus.SKIP,"call amount received textbox is not visible", YesNo.Yes);
						sa.assertTrue(false, "call amount received textbox is not visible");
					}
					//verifying updated data
					log(LogStatus.PASS, "verifing data for view mode(after changing call amount received)",YesNo.No );
					for (int i = 0;i<=8;i++) {
						if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[i] , SmokeCC3Data[i], EditViewMode.View)) {
							log(LogStatus.PASS, "successfully verified "+labels[i], YesNo.No);
						}
						else {
							log(LogStatus.FAIL, "data could not be verified of "+labels[i], YesNo.Yes);
							sa.assertTrue(false, "data could not be verified of "+labels[i]);
						}
					}
					if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls,labels[9] ,"$0.000", EditViewMode.View)) {
						log(LogStatus.PASS, "successfully verified "+CapitalCallPageFieldLabelText.Amount_Due.toString(), YesNo.No);
					}
					else {
						log(LogStatus.FAIL, "data could not be verified of "+CapitalCallPageFieldLabelText.Amount_Due.toString(), YesNo.Yes);
						sa.assertTrue(false, "data could not be verified of "+CapitalCallPageFieldLabelText.Amount_Due.toString());
					}
				}
				else {
					log(LogStatus.SKIP, "edit button is not clickable", YesNo.Yes);
					sa.assertTrue(false, "edit button is not clickable");
				}
			}
			else {
				log(LogStatus.SKIP, "capital call not found", YesNo.Yes);
				sa.assertTrue(false, "capital call not found");
			}
		}
		switchToDefaultContent(driver);
		//fund drawdown page
		String[][] label={{FundDrawdownPageFieldLabelText.DD_No.toString(),SmokeDD1_ID},
				{FundDrawdownPageFieldLabelText.Fund_Name.toString(),SmokeDD1_FundName},
				{FundDrawdownPageFieldLabelText.Call_Amount.toString(),SmokeDD1_CallAmount},
				{FundDrawdownPageFieldLabelText.Call_Date.toString(),SmokeDD1_CallDate},
				{FundDrawdownPageFieldLabelText.Due_Date.toString(),SmokeDD1_DueDate},
				{FundDrawdownPageFieldLabelText.Amount_Due.toString(),SmokeDD1_AmountDue},
				{FundDrawdownPageFieldLabelText.Total_Call_Amount_Received.toString(),SmokeDD1_TotalCallAmountReceived},
				{FundDrawdownPageFieldLabelText.Capital_Amount.toString(),SmokeDD1_CapitalAmount},
				{FundDrawdownPageFieldLabelText.Management_Fee.toString(),SmokeDD1_ManagementFee},
				{FundDrawdownPageFieldLabelText.Other_Fee.toString(),SmokeDD1_OtherFee}};
		if (bp.clickOnTab(environment, mode, TabName.FundDrawdowns)) {
			if (fdp.clickOnCreatedDrawdown(environment, mode, SmokeDD1_ID)) {
				for (int i = 0;i<label.length;i++) {
					if (fdp.fieldValueVerificationOnFundDrawdown(environment, mode, TabName.FundDrawdowns, label[i][0], label[i][1])) {
						log(LogStatus.PASS, label[i][0]+" is successfully verified", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, label[i][0]+" is not correct", YesNo.Yes);
						sa.assertTrue(false, label[i][0]+" is not correct");
					}
				}
			}
			else {
				log(LogStatus.SKIP, "could not click on drawdown "+SmokeDD1_ID, YesNo.Yes);
				sa.assertTrue(false, "could not click on drawdown "+SmokeDD1_ID);
			}
		}
		else {
			log(LogStatus.SKIP, "could not click on drawdown tab", YesNo.Yes);
			sa.assertTrue(false, "could not clickc on drawdown tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc048_VerifyCommitmentPage(String environment, String mode) {
	
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String text = ExcelUtils.readData(smokeFilePath, "Commitments", excelLabel.Variable_Name, "SmokeCOMM1", excelLabel.Commitment_ID);
		ExcelUtils.writeData(smokeFilePath, text, "Commitments", excelLabel.Variable_Name, "SmokeCOMM8", excelLabel.Commitment_ID);
		text = ExcelUtils.readData(smokeFilePath, "Commitments", excelLabel.Variable_Name, "SmokeCOMM1", excelLabel.Final_Commitment_Date);
		ExcelUtils.writeData(smokeFilePath, text, "Commitments", excelLabel.Variable_Name, "SmokeCOMM8", excelLabel.Final_Commitment_Date);
		String labelName=CommitmentPageFieldLabelText.Commitment_ID+","+CommitmentPageFieldLabelText.Partner_Type+","+
						CommitmentPageFieldLabelText.Limited_Partner+","+CommitmentPageFieldLabelText.Final_Commitment_Date+","+
						CommitmentPageFieldLabelText.Partnership+","
							+CommitmentPageFieldLabelText.Tax_Forms+","+CommitmentPageFieldLabelText.Commitment_Amount+","+
							CommitmentPageFieldLabelText.Total_Amount_Called+","+CommitmentPageFieldLabelText.Total_Amount_Received+","+
							CommitmentPageFieldLabelText.Total_Uncalled_Amount+","+CommitmentPageFieldLabelText.Total_Commitment_Due+","+
							CommitmentPageFieldLabelText.Commitment_Called+","+CommitmentPageFieldLabelText.Called_Due;

		String labelValue=SmokeCOMM8_ID+","+SmokeCOMM8_partnerType+","+Smoke_LP1+","+SmokeCOMM8_FinalCommitmentDate+","+Smoke_P1+","+SmokeCOMM8_TaxForms
				+","+SmokeCOMM8_CommitmentAmount+","+SmokeCOMM8_TotalAmountCalled+","+SmokeCOMM8_TotalAmountReceived+","+SmokeCOMM8_TotalUncalledAmount
				+","+SmokeCOMM8_TotalCommitmentDue+","+SmokeCOMM8_CommitmentCalled+","+SmokeCOMM8_CalledDue;
		String[] labels = labelName.split(",");
		String[] values = labelValue.split(",");
		if (bp.clickOnTab(environment, mode, TabName.CommitmentsTab)) {
			if (comm.clickOnCreatedCommitmentId(environment, mode, SmokeCOMM8_ID)) {
				for (int j = 0; j < values.length; j++) {
					if(comm.FieldValueVerificationOnCommitmentPage(environment, mode, labels[j], values[j])) {
						log(LogStatus.PASS, labels[j]+" : With value verified for : "+values[j], YesNo.No);
					}else {
						log(LogStatus.ERROR, labels[j]+" : With value not verified for : "+values[j], YesNo.Yes);
						sa.assertTrue(false, labels[j]+" : With value not verified for : "+values[j]);
					}	
				}
			}
			else {
				log(LogStatus.SKIP, SmokeCOMM8_ID+" cannot click on commitment", YesNo.Yes);
				sa.assertTrue(false, SmokeCOMM8_ID+" cannot click on commitment");
			}
		}
		else {
			log(LogStatus.SKIP, "cannot click on commitments tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on commitments tab");
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc049_VerifySendCapitalCallNotices(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (bp.clickOnTab(environment, mode, TabName.FundDrawdowns)) {
			if (fd.clickOnCreatedDrawdown(environment, mode, SmokeDD1_ID)) {
				for (int i = 1; i <= 2; i++) {
					
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						if (bp.clickOnShowMoreDropdownOnly(environment, mode, PageName.FundDrawdown)) {
							log(LogStatus.PASS, "successfully clicked on dropdown",YesNo.No);
							WebElement ele=bp.actionDropdownElement(environment, PageName.FundDrawdown, ShowMoreActionDropDownList.Send_Capital_Call_Notices, 30);
							if (ele!=null) {
								log(LogStatus.PASS, "successfully found send capital call notices button", YesNo.No);
								if (click(driver, bp.actionDropdownElement(environment, PageName.FundDrawdown,  ShowMoreActionDropDownList.Send_Capital_Call_Notices, 30), ShowMoreActionDropDownList.Send_Capital_Call_Notices.toString(), action.BOOLEAN)) {
									log(LogStatus.PASS, "successfully click on send button", YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "cannot click on send button", YesNo.Yes);
									sa.assertTrue(false, "could not click on send button");
								}
							}
							else {
								log(LogStatus.ERROR, "could not find send capital call notices button", YesNo.Yes);
								sa.assertTrue(false, "could not find send capital call notices button");
							}
						}
						else {
							log(LogStatus.ERROR, "could not click on show more dropdown", YesNo.Yes);
							sa.assertTrue(false, "could not click on show more dropdown");
						}
					}
					else {
						if (fd.getSendCapitalCallNotices_Classic(30)!=null) {
							log(LogStatus.PASS, "send capital call notices button is successfully present", YesNo.No);
							if (click(driver, fd.getSendCapitalCallNotices_Classic(30), "send capital call notices button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "successfully click on send capital call button", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not click on send button", YesNo.Yes);
								sa.assertTrue(false, "could not click on send button");
							}
						}
						else {
							log(LogStatus.FAIL, "send capital call notices button is not present", YesNo.Yes);
							sa.assertTrue(false, "send capital call notices button is not present");
						}
					}
					//on email page
					
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 10, fd.getEmailingFrame_Lighting(30));
					}
					
					if (i==1) {
						
						
						if (fd.getEmailing_text(30).getText().trim().equalsIgnoreCase(FundDrawdownPageErrorMessage.emailingText1+SmokeDD1_ID+FundDrawdownPageErrorMessage.emailingText2+Smoke_Fund1)) {
							log(LogStatus.PASS, "successfully verified emailing capital call notices text", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify emailing capital call notices text", YesNo.Yes);
							sa.assertTrue(false, "could not verify emailing capital call notices text");
						}
						if (fd.getSpecifyRecepients(30).getText().trim().equalsIgnoreCase(FundDrawdownPageErrorMessage.specifyRecepients)) {
							log(LogStatus.PASS, "successfully verified specify recepients text", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify specify recepients text", YesNo.Yes);
							sa.assertTrue(false, "could not verify specify recepients text");
						}
						if (fd.getNoDataToDisplayEmailing(30).getText().trim().equalsIgnoreCase(BasePageErrorMessage.nodataDisplayMsg)) {
							log(LogStatus.PASS, "successfully verified nodataDisplayMsg text", YesNo.No);

						}
						else {
							log(LogStatus.ERROR, "could not verify nodataDisplayMsg text", YesNo.Yes);
							sa.assertTrue(false, "could not verify nodataDisplayMsg text");
						}
						if (fd.getRecordsCount(30).getText().trim().equalsIgnoreCase("Records: 0")) {
							log(LogStatus.PASS, "successfully verified record number", YesNo.No);

						}
						else {
							log(LogStatus.ERROR, "could not verify record number text", YesNo.Yes);
							sa.assertTrue(false, "could not verify record number text");
						}
						
						if (click(driver, fd.getStep1CancelButtonEmailing(TopOrBottom.TOP,20), "step1 Cancel Button Emailing : Top", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "Click on step1 Cancel Button Emailing : Top", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Not Able to Click on step1 Cancel Button Emailing : Top", YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on step1 Cancel Button Emailing : Top");
						}
						
						
					} else {

						if (click(driver, fd.getStep1CancelButtonEmailing(TopOrBottom.BOTTOM,20), "step1 Cancel Button Emailing : Bottom", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "Click on step1 Cancel Button Emailing : Bottom", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Not Able to Click on step1 Cancel Button Emailing : Bottom", YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on step1 Cancel Button Emailing : Bottom");
						}
						
					}
					
					String xpath;
					if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
						xpath = "//*[contains(text(),'Fund Drawdown')]/..//*[contains(text(),'"+SmokeDD1_ID+"')]";
					}else{
						xpath = "//h2[contains(text(),'"+SmokeDD1_ID+"')]";	
					}

					WebElement ele = FindElement(driver, xpath, "LAnding PAge : "+SmokeDD1_ID, action.BOOLEAN, 10);

					if (ele!=null) {
						appLog.info("Landing Page Verified : "+SmokeDD1_ID);
					} else {
						appLog.error("Landing Page Not Verified : "+SmokeDD1_ID);
						sa.assertTrue(false, "Landing Page Not Verified : "+SmokeDD1_ID);
					}
					
					switchToDefaultContent(driver);
				}
			
			}
			else {
				log(LogStatus.SKIP, "could not click on drawdown "+SmokeDD1_ID, YesNo.Yes);
				sa.assertTrue(false, "cannot click on drawdown "+SmokeDD1_ID);
			}
		}
		else {
			log(LogStatus.SKIP,"could not click on drawdown tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on drawdown tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc050_1_AddContactToCorrespondingList(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CorrespondenceListPageBusinessLayer crp = new CorrespondenceListPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		List<String> corrList = new ArrayList<String>();
		corrList.add("Distribution Notices");
		corrList.add("Capital Calls");
		//contact 1
		if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
			if (cp.clickOnCreatedContact(environment,  SmokeC1_FName, SmokeC1_LName)) {
				ThreadSleep(5000);
				if (bp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Correspondence_Lists, RelatedTab.Investor_Relations.toString())) {
					log(LogStatus.INFO, "clicked on Investor Relations Tab", YesNo.No);
					ThreadSleep(3000);
					if (click(driver, crp.getNewCorrespondenceListButton(30),"New Correspondence List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on new button on correspondence list related list", YesNo.No);
						ThreadSleep(5000);
						if (crp.createNewCorrList( "",mode,environment, SmokeC1_FName, SmokeC1_LName, SmokeCOMM8_ID,corrList )) {
							log(LogStatus.PASS, "successfully created new correspondence list", YesNo.No);
							if (cp.verifyPresenceOfCorrespondenceRelatedList(mode, environment,SmokeC1_FName+" "+SmokeC1_LName, Smoke_LP1, Smoke_P1, SmokeCOMM8_ID, 30)) {
								log(LogStatus.PASS, "successfully verified "+SmokeCOMM8_ID+" on related list", YesNo.No);
							}
							else {
								log(LogStatus.FAIL,"could not find "+SmokeCOMM8_ID+" on related lsit", YesNo.Yes);
								sa.assertTrue(false, "could not find "+SmokeCOMM8_ID+" on related list");
							}
						}
						else {
							log(LogStatus.FAIL, "could not create new correspondence list", YesNo.Yes);
							sa.assertTrue(false, "could not create new correspondence list");
						}

					}
					else {
						log(LogStatus.ERROR, "could not click on new button on correspondence list related list", YesNo.Yes);
						sa.assertTrue(false, "could not click on new button on correspondence list related list");
					}

				}
				else {
					log(LogStatus.ERROR, "could not click on Investor Relations Tab", YesNo.Yes);
					sa.assertTrue(false, "could not click on Investor Relations Tab");
				}
			}
			else {
				log(LogStatus.FAIL, "could not find contact "+SmokeC1_LName, YesNo.Yes);
				sa.assertTrue(false, "could not find contact "+SmokeC1_LName);
			}
		}else{
			log(LogStatus.SKIP, "Not ABle to Click on Contact Tab", YesNo.Yes);
			sa.assertTrue(false, "Not ABle to Click on Contact Tab");	
		}
		//contact 2
		if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
			if (cp.clickOnCreatedContact(environment,  SmokeC2_FName, SmokeC2_LName)) {
				ThreadSleep(5000);
				if (bp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Correspondence_Lists, RelatedTab.Investor_Relations.toString())) {
					log(LogStatus.INFO, "clicked on Investor Relations Tab", YesNo.No);
					if (click(driver, crp.getNewCorrespondenceListButton(30),"New Correspondence List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on new button on correspondence list related list", YesNo.No);
						if (crp.createNewCorrList("",mode, environment, SmokeC2_FName, SmokeC2_LName, SmokeCOMM2_ID,corrList )) {
							log(LogStatus.PASS, "successfully created new correspondence list", YesNo.No);
							if (cp.verifyPresenceOfCorrespondenceRelatedList(mode, environment,SmokeC2_FName+" "+SmokeC2_LName, Smoke_LP2, Smoke_P1, SmokeCOMM2_ID, 30)) {
								log(LogStatus.PASS, "successfully verified "+SmokeCOMM2_ID+" on related list", YesNo.No);
							}
							else {
								log(LogStatus.FAIL,"could not find "+SmokeCOMM2_ID+" on related lsit", YesNo.Yes);
								sa.assertTrue(false, "could not find "+SmokeCOMM2_ID+" on related list");
							}

						}
						else {
							log(LogStatus.FAIL, "could not create new correspondence list", YesNo.Yes);
							sa.assertTrue(false, "could not create new correspondence list");
						}

					}
					else {
						log(LogStatus.ERROR, "could not click on new button on correspondence list related list", YesNo.Yes);
						sa.assertTrue(false, "could not click on new button on correspondence list related list");
					}

				}
				else {
					log(LogStatus.ERROR, "could not click on Investor Relations Tab", YesNo.Yes);
					sa.assertTrue(false, "could not click on Investor Relations Tab");
				}
			}
			else {
				log(LogStatus.SKIP, "could not find contact "+SmokeC2_LName, YesNo.Yes);
				sa.assertTrue(false, "could not find contact "+SmokeC2_LName);
			}
		}else{
			log(LogStatus.SKIP, "Not ABle to Click on Contact Tab", YesNo.Yes);
			sa.assertTrue(false, "Not ABle to Click on Contact Tab");	
		}
		//contact 4
		if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
			if (cp.clickOnCreatedContact(environment, SmokeC4_FName, SmokeC4_LName)) {
				ThreadSleep(5000);
				if (bp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Correspondence_Lists, RelatedTab.Investor_Relations.toString())) {
					log(LogStatus.INFO, "clicked on Investor Relations Tab", YesNo.No);
					if (click(driver, crp.getNewCorrespondenceListButton(30),"New Correspondence List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on new button on correspondence list related list", YesNo.No);
						if (crp.createNewCorrList("",mode, environment, SmokeC4_FName, SmokeC4_LName, SmokeCOMM3_ID,corrList )) {
							log(LogStatus.PASS, "successfully created new correspondence list", YesNo.No);
							if (cp.verifyPresenceOfCorrespondenceRelatedList(mode, environment,SmokeC4_FName+" "+SmokeC4_LName, Smoke_LP1, Smoke_P1, SmokeCOMM3_ID, 30)) {
								log(LogStatus.PASS, "successfully verified "+SmokeCOMM3_ID+" on related list", YesNo.No);
							}
							else {
								log(LogStatus.FAIL,"could not find "+SmokeCOMM3_ID+" on related lsit", YesNo.Yes);
								sa.assertTrue(false, "could not find "+SmokeCOMM3_ID+" on related list");
							}

						}
						else {
							log(LogStatus.FAIL, "could not create new correspondence list", YesNo.Yes);
							sa.assertTrue(false, "could not create new correspondence list");
						}

					}
					else {
						log(LogStatus.ERROR, "could not click on new button on correspondence list related list", YesNo.Yes);
						sa.assertTrue(false, "could not click on new button on correspondence list related list");
					}

				}
				else {
					log(LogStatus.ERROR, "could not click on Investor Relations Tab", YesNo.Yes);
					sa.assertTrue(false, "could not click on Investor Relations Tab");
				}
			}
			else {
				log(LogStatus.SKIP, "could not find contact "+SmokeC4_LName, YesNo.Yes);
				sa.assertTrue(false, "could not find contact "+SmokeC4_LName);
			}
		}else{
			log(LogStatus.SKIP, "Not ABle to Click on Contact Tab", YesNo.Yes);
			sa.assertTrue(false, "Not ABle to Click on Contact Tab");	
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc050_2_verifyCancelNextPreviousOnCapitalCallNotice(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		for(int j=0; j<=7; j++) {
			if(fund.clickOnTab(environment, mode, TabName.FundDrawdowns)) {
				if(fd.clickOnCreatedDrawdown(environment, mode, SmokeDD1_ID)) {
					appLog.info("Click on dRAW dOWN : "+SmokeDD1_ID);
					
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						if (bp.clickOnShowMoreDropdownOnly(environment, mode, PageName.FundDrawdown)) {
							log(LogStatus.PASS, "successfully clicked on dropdown",YesNo.No);
							WebElement ele=bp.actionDropdownElement(environment,  PageName.FundDrawdown, ShowMoreActionDropDownList.Send_Capital_Call_Notices, 30);
							if (ele!=null) {
								log(LogStatus.PASS, "successfully found send capital call notices button", YesNo.No);
								if (click(driver, bp.actionDropdownElement(environment, PageName.FundDrawdown,  ShowMoreActionDropDownList.Send_Capital_Call_Notices, 30), ShowMoreActionDropDownList.Send_Capital_Call_Notices.toString(), action.BOOLEAN)) {
									log(LogStatus.PASS, "successfully click on send button", YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "cannot click on send button", YesNo.Yes);
									sa.assertTrue(false, "could not click on send button");
								}
							}
							else {
								log(LogStatus.ERROR, "could not find send capital call notices button", YesNo.Yes);
								sa.assertTrue(false, "could not find send capital call notices button");
							}
						}
						else {
							log(LogStatus.ERROR, "could not click on show more dropdown", YesNo.Yes);
							sa.assertTrue(false, "could not click on show more dropdown");
						}
					}
					else {
						if (fd.getSendCapitalCallNotices_Classic(30)!=null) {
							log(LogStatus.PASS, "send capital call notices button is successfully present", YesNo.No);
							if (click(driver, fd.getSendCapitalCallNotices_Classic(30), "send capital call notices button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "successfully click on send capital call button", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not click on send button", YesNo.Yes);
								sa.assertTrue(false, "could not click on send button");
							}
						}
						else {
							log(LogStatus.FAIL, "send capital call notices button is not present", YesNo.Yes);
							sa.assertTrue(false, "send capital call notices button is not present");
						}
					}
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 10, fd.getEmailingFrame_Lighting(30));
					}
				//	List<String> result =market.selectProspectsContactAndVerifyReviewProspectListInEmailProspect(PageName.emailCapitalCallNotice,ContactAndAccountName, searchContactInEmailProspectGrid.No);
					if(j==0) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.CapitalCall,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							for(int i=0; i<=3; i++) {
								if(i==0) {
									if(click(driver, market.getStep1NextBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"Top Next button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top next button", YesNo.No);
									}else {
										log(LogStatus.ERROR,"Not able to click on top next button", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top next button");
									}
								}
								if(i==1) {
									if(click(driver, market.getStep2PreviousBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"Top previous button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top previous button", YesNo.No);
									}else {
										log(LogStatus.ERROR,"Not able to click on top previous button", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top previous button");
									}
								}
								if(i==2) {
									if(click(driver, market.getStep1NextBtn(PageName.emailCapitalCallNotice, TopOrBottom.BOTTOM,10),"bottom Next button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on BOTTOM next button", YesNo.No);
									}else {
										log(LogStatus.ERROR,"Not able to click on BOTTOM next button", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on BOTTOM next button");
									}
								}

								if(i==3) {
									if(click(driver, market.getStep2PreviousBtn(PageName.emailCapitalCallNotice, TopOrBottom.BOTTOM,10),"bottom previous button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on BOTTOM previous button", YesNo.No);
									}else {
										log(LogStatus.ERROR,"Not able to click on BOTTOM previous button", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on BOTTOM previous button");
									}
								}
								if(i==0 && i==2) {
									ThreadSleep(2000);
									WebElement ele = fd.getStep2TextEmailing(30);
									if (ele!=null) {
										String msg = ele.getText().trim();
										if (HomePageErrorMessage.Step2_SelectAnEmailTemplate.equals(msg)) {
											log(LogStatus.PASS, "Step2 Page Verified : "+msg, YesNo.No);
										} else {
											sa.assertTrue(false, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate);
											log(LogStatus.FAIL, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate, YesNo.Yes);
										}
									} else {
										sa.assertTrue(false, "Step2 Page Element is null");
										log(LogStatus.FAIL, "Step2 Page Element is null", YesNo.Yes);

									}
								}

							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button");
						}
					}
					if(j==1) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.CapitalCall,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1CancelBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"Top cancel button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on top cancel button step 1", YesNo.No);
							}else {
								log(LogStatus.ERROR,"Not able to click on top cancel button step 1", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on top cancel button step 1");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot Top Cancel bottom on step 1", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot Top Cancel bottom on step 1");
						}
					}
					if(j==2) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.CapitalCall,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1CancelBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"bottom cancel button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on bottom cancel button step 1", YesNo.No);
							}else {
								log(LogStatus.ERROR,"Not able to click on bottom cancel button step 1", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on bottom cancel button step 1");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot bottom Cancel bottom on step 1", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot bottom Cancel bottom on step 1");
						}
					}
					if(j==3) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.CapitalCall,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1NextBtn(PageName.emailCapitalCallNotice, TopOrBottom.BOTTOM,10),"bottom Next button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on BOTTOM next button", YesNo.No);
								ThreadSleep(2000);
								if(click(driver, market.getStep2CancelBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"top cancel button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "clicked on top cancel button step 2", YesNo.No);
								}else {
									log(LogStatus.ERROR,"Not able to click on top cancel button step 2", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on top cancel button step 2");
								}


							}else {
								log(LogStatus.ERROR,"Not able to click on BOTTOM next button so cannot check Top Cancel button on Step2", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on BOTTOM next button so cannot check Top Cancel button on Step2");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check Top Cancel button on Step2", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check Top Cancel button on Step2");
						}
					}
					if(j==4) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.CapitalCall,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1NextBtn(PageName.emailCapitalCallNotice, TopOrBottom.BOTTOM,10),"bottom Next button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on BOTTOM next button", YesNo.No);
								ThreadSleep(2000);
								if(click(driver, market.getStep2CancelBtn(PageName.emailCapitalCallNotice, TopOrBottom.BOTTOM,10),"bottom cancel button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "clicked on bottom cancel button step 2", YesNo.No);
								}else {
									log(LogStatus.ERROR,"Not able to click on bottom cancel button step 2", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on bottom cancel button step 2");
								}
							}else {
								log(LogStatus.ERROR,"Not able to click on BOTTOM next button so cannot check bottom Cancel button on Step2", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on BOTTOM next button so cannot check bottom Cancel button on Step2");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check bottom Cancel button on Step2", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check bottom Cancel button on Step2");
						}
					}
					if(j==5) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.CapitalCall,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1NextBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"Top Next button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on top next button step 1", YesNo.No);
								ThreadSleep(2000);
								for(int i=0; i<=3; i++) {
									if(i==0) {
										if(market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
											log(LogStatus.ERROR, "Select Capital Call Notice template", YesNo.No);
											if(click(driver, market.getStep2NextBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);
											}else {
												log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on top next button step 2");
											}
										}else {
											log(LogStatus.ERROR,"Not able to click select email template from step 2 so cannot check previous botton of step 3", YesNo.Yes);
											sa.assertTrue(false,"Not able to click select email template from step 2 so cannot check previous botton of step 3");
											break;
										}

									}
									if(i==1) {
										if(click(driver, market.getStep3PreviousBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"Top previous button step 3", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on top previous button step 3", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on top previous button step 3", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on top previous button step 3");
										}
									}
									if(i==2) {
										if(click(driver, market.getStep2NextBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);

										}else {
											log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on top next button step 2");
										}
									}

									if(i==3) {
										if(click(driver, market.getStep3PreviousBtn(PageName.emailCapitalCallNotice, TopOrBottom.BOTTOM,10),"bottom previous button step 3", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on bottom previous button step 3", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on bottom previous button step 3", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on bottom previous button step 3");
										}
									}
									if(i==0 && i==2) {
										ThreadSleep(2000);
										WebElement ele = fd.getStep2TextEmailing(30);
										if (ele!=null) {
											String msg = ele.getText().trim();
											if (HomePageErrorMessage.step3_ReviewAndConfirm.equals(msg)) {
												log(LogStatus.PASS, "Step3 Page Verified : "+msg, YesNo.No);
											} else {
												sa.assertTrue(false, "Step3 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step3_ReviewAndConfirm);
												log(LogStatus.FAIL, "Step3 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step3_ReviewAndConfirm, YesNo.Yes);
											}
										} else {
											sa.assertTrue(false, "Step3 Page Element is null");
											log(LogStatus.FAIL, "Step3 Page Element is null", YesNo.Yes);

										}
									}

								}
							}else {
								log(LogStatus.ERROR,"Not able to click on top next button step 1 so cannot check previous botton of step3", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on top next button step 1 so cannot check previous botton of step3");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button");
						}
					}
					if(j==6) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.CapitalCall,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1NextBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"Top Next button step 1", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on top next button step 1", YesNo.No);
								if(market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
									log(LogStatus.ERROR, "Select Capital Call Notice template", YesNo.No);
									if(click(driver, market.getStep2NextBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);
										ThreadSleep(2000);
										if(click(driver, market.getStep3CancelBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"top cancel button step 3", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on top cancel button step 3", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on top cancel button step 3", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on top cancel button step 3");
										}
									}else {
										log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top next button step 2");
									}
								}else {
									log(LogStatus.ERROR,"Not able to click select email template from step 2 so cannot check previous botton of step 3", YesNo.Yes);
									sa.assertTrue(false,"Not able to click select email template from step 2 so cannot check previous botton of step 3");
								}
							}else {
								log(LogStatus.ERROR,"Not able to click on top next button step 1 so cannot check previous botton of step3", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on top next button step 1 so cannot check previous botton of step3");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check step 3 cancel button", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check step 3 cancel button");
						}
					}
					if(j==7) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.CapitalCall,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1NextBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"Top Next button step 1", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on top next button step 1", YesNo.No);
								if(market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
									log(LogStatus.ERROR, "Select Capital Call Notice template", YesNo.No);
									if(click(driver, market.getStep2NextBtn(PageName.emailCapitalCallNotice, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);
										ThreadSleep(2000);
										if(click(driver, market.getStep3CancelBtn(PageName.emailCapitalCallNotice, TopOrBottom.BOTTOM,10),"buttom cancel button step 3", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on buttom cancel button step 3", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on buttom cancel button step 3", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on buttom cancel button step 3");
										}
									}else {
										log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top next button step 2");
									}
								}else {
									log(LogStatus.ERROR,"Not able to click select email template from step 2 so cannot check previous botton of step 3", YesNo.Yes);
									sa.assertTrue(false,"Not able to click select email template from step 2 so cannot check previous botton of step 3");
								}

							}else {
								log(LogStatus.ERROR,"Not able to click on top next button step 1 so cannot check previous botton of step3", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on top next button step 1 so cannot check previous botton of step3");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check step 3 cancel button", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check step 3 cancel button");
						}
					}



				}else {
					log(LogStatus.ERROR, "Not able to click on Draw Down "+SmokeDD1_ID+" so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Draw Down "+SmokeDD1_ID+" so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
				}
			}else {
			log(LogStatus.ERROR, "Not able to click on fund Draw Down tab so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);
			sa.assertTrue(false, "Not able to click on fund Draw Down tab so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
		}
			switchToDefaultContent(driver);
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc051_VerifyEmailSendToOneContact(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String keywords[] = ExcelUtils.readData("FilePath", excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.KeyWord_For_Search).split(",");
		String selectedGrid[] = keywords[0].split("<break>");
		String columnsOnGrid[] = keywords[2].split("<break>");
		SoftAssert saa = new SoftAssert();
		int n = 2;
		WebElement ele;
		String xpath;
		String parentId=null;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (bp.clickOnTab(environment, mode, TabName.FundDrawdowns)) {
			if (fd.clickOnCreatedDrawdown(environment, mode, SmokeDD1_ID)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (bp.clickOnShowMoreDropdownOnly(environment, mode, PageName.FundDrawdown)) {
						log(LogStatus.PASS, "successfully clicked on dropdown",YesNo.No);
						 ele=bp.actionDropdownElement(environment,  PageName.FundDrawdown, ShowMoreActionDropDownList.Send_Capital_Call_Notices, 30);
						if (ele!=null) {
							log(LogStatus.PASS, "successfully found send capital call notices button", YesNo.No);
							if (click(driver, bp.actionDropdownElement(environment,  PageName.FundDrawdown,  ShowMoreActionDropDownList.Send_Capital_Call_Notices, 30), ShowMoreActionDropDownList.Send_Capital_Call_Notices.toString(), action.BOOLEAN)) {
								log(LogStatus.PASS, "successfully click on send button", YesNo.No);
							}
							else {
								log(LogStatus.FAIL, "cannot click on send button", YesNo.Yes);
								sa.assertTrue(false, "could not click on send button");
							}
						}
						else {
							log(LogStatus.ERROR, "could not find send capital call notices button", YesNo.Yes);
							sa.assertTrue(false, "could not find send capital call notices button");
						}
					}
					else {
						log(LogStatus.PASS, "could not click on show more dropdown", YesNo.Yes);
						sa.assertTrue(false, "could not click on show more dropdown");
					}
				}
				else {
				if (fd.getSendCapitalCallNotices_Classic(30)!=null) {
					log(LogStatus.PASS, "send capital call notices button is successfully present", YesNo.No);
					if (click(driver, fd.getSendCapitalCallNotices_Classic(30), "send capital call notices button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "successfully click on send capital call button", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not click on send button", YesNo.Yes);
						sa.assertTrue(false, "could not click on send button");
					}
				}
				else {
					log(LogStatus.FAIL, "send capital call notices button is not present", YesNo.Yes);
					sa.assertTrue(false, "send capital call notices button is not present");
				}}
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(30));
				}
				//verify mailing grid
				SmokeCC1_ID = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM8_ID, excelLabel.CapitalCalllID);
				saa = fd.verifyEmailingContentGrid(driver, PageName.FundDrawdown,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, SmokeC1_EmailID, SmokeCC1_ID, SmokeCOMM8_ID, Smoke_LP1, Smoke_P1, false);
				sa.combineAssertions(saa);
				SmokeCC2_ID = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM2_ID, excelLabel.CapitalCalllID);
				saa = fd.verifyEmailingContentGrid(driver, PageName.FundDrawdown,SmokeC2_FName+" "+SmokeC2_LName, SmokeINS1, SmokeC2_EmailID, SmokeCC2_ID, SmokeCOMM2_ID, Smoke_LP2, Smoke_P1, false);
				sa.combineAssertions(saa);
				List<WebElement> li = fd.listOfContactNames(30);
				boolean flag = false;
				for (int i =0;i<li.size();i++) {
					if (li.get(i).getText().trim().contains(SmokeC4_FName + " "+ SmokeC4_LName)) {
						flag = true;
						break;
					}
				}
				if (flag) {
					log(LogStatus.ERROR,SmokeC4_FName + " "+ SmokeC4_LName+ " is found in emailing grid but it should not be",YesNo.Yes);
					sa.assertTrue(false,SmokeC4_FName + " "+ SmokeC4_LName+ " is found in emailing grid but it should not be" );
				}
				else {
					log(LogStatus.INFO, "could not find "+SmokeC4_FName + " "+ SmokeC4_LName+ " as expected", YesNo.No);
				}
				//verify total number of records
				if (fd.getRecordsCount(30).getText().trim().contains("Records: "+n)) {
					log(LogStatus.INFO,"successfully verified number of records",YesNo.Yes);
				}
				else {
					log(LogStatus.ERROR,"could not verify correct number of records", YesNo.Yes);
					sa.assertTrue(false, "could not verify correct number of records");
				}
				
				//verify columns to display ui
				if (click(driver, fd.getWrenchIcon(30), "wrench icon", action.SCROLLANDBOOLEAN)) {
					for (int i = 0;i<selectedGrid.length;i++) {
						if (fd.verifyColumnInColumnsToDisplayGrid(PageName.FundDrawdown, selectedGrid[i])) {
							log(LogStatus.INFO, "successfully found "+selectedGrid[i], YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "not found "+selectedGrid[i], YesNo.Yes);
							sa.assertTrue(false, "not found "+selectedGrid[i]);
						}
					}
				}
				if (fd.moveFromAvailableToSelectedColumnsToDisplay(PageName.FundDrawdown, keywords[1])) {
					log(LogStatus.INFO, "successfully moved last name column to selected grid", YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not move last name column to selected grid", YesNo.Yes);
					sa.assertTrue(false, "could not move last name column to selected grid");
				}
				click(driver, fd.getApplyColumnsToDisplay(30), "clicked on apply button", action.BOOLEAN);
				
				//verify presence of all columns
				for (int i =0;i<columnsOnGrid.length;i++) {
				if (fd.getEmailingGridColumn(columnsOnGrid[i], 30)!=null) {
					log(LogStatus.INFO, "successfully found "+columnsOnGrid[i]+ " column", YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "could not find "+columnsOnGrid[i]+" column", YesNo.Yes);
					sa.assertTrue(false, "could not find "+columnsOnGrid[i]+" column");
				}
				}
				
				// Azhar Start
		
				String[] linkClick = {SmokeC1_FName+" "+SmokeC1_LName,SmokeINS1};
				for (int j = 0; j < linkClick.length; j++) {
					
					xpath = "//span/a[text()='"+linkClick[j]+"']";
					ele = FindElement(driver, xpath, linkClick[j], action.BOOLEAN, 10);
					
					if (ele!=null) {
						
						if (click(driver, ele, linkClick[j], action.BOOLEAN)) {
							
						parentId = 	switchOnWindow(driver);
						
						if (parentId!=null) {
							ThreadSleep(2000);
							if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
								xpath = "//*[contains(text(),'"+linkClick[j]+"')]";
								}else{
									xpath = "//h2[contains(text(),'"+linkClick[j]+"')]";	
								}
							
							ele = FindElement(driver, xpath, "on new window : "+linkClick[j], action.BOOLEAN, 10);
							
							if (ele!=null) {
								appLog.info("Landing Page Verified : "+linkClick[j]);
							} else {
								appLog.error("Landing Page Not Verified : "+linkClick[j]);
								sa.assertTrue(false, "Landing Page Not Verified : "+linkClick[j]);
							}
							
							driver.close();
							driver.switchTo().window(parentId);
							switchToDefaultContent(driver);
							
							if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(20));
							}
							
						} else {
							appLog.error("Not New Window for "+linkClick[j]);
							sa.assertTrue(false, "Not New Window for "+linkClick[j]);
						}
						
					} else {
							appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
							sa.assertTrue(false, "Not able to click on "+linkClick[j]+" so cannot verify landing page");
						}
					} else {
						appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
						sa.assertTrue(false, "Not able to click on "+linkClick[j]+" so cannot verify landing page");
					}
					
					
				}
				
			// Azhar End
				
				//checking searchbox functionality
				if (sendKeys(driver, fd.getSearchTextboxEmailingGrid(30),SmokeC1_LName,"search box", action.SCROLLANDBOOLEAN)) {
					if (click(driver,fd.getSearchIconEmailingGrid(30), "search icon", action.BOOLEAN)) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo( 0 ,0)",
								fd.getScrollBoxStep1FundDrawdownEmailingGrid(30));
						
						saa = fd.verifyEmailingContentGrid(driver, PageName.FundDrawdown,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, SmokeC1_EmailID, SmokeCC1_ID, SmokeCOMM8_ID, Smoke_LP1, Smoke_P1, false);
						sa.combineAssertions(saa);
						

					}
					else {
						log(LogStatus.ERROR, "search icon is not clickable on emailing grid", YesNo.Yes);
						sa.assertTrue(false, "search icon is not clickable on emailing grid");
					}
				}
				else {
					log(LogStatus.ERROR, "search textbox is not visible", YesNo.Yes);
					sa.assertTrue(false, "search textbox is not visible");
				}
				if (click(driver,fd.getStep1HeaderCheckbox(30), "header checkbox", action.SCROLLANDBOOLEAN)) {
					if (click(driver, fd.getStep1NextButtonEmailing(30), "next button", action.SCROLLANDBOOLEAN)) {
						if (fd.getStep2TextEmailing(30)!=null) {
							appLog.info("successfully found step 2 page");
						}
						String expectedResult = "All,Capital Call Notice,Investor Distribution Notice,"+EmailTemplate1_FolderName;
						List<WebElement> lst = allOptionsInDropDrop(driver,
								fd.getFundDrawdownFolderDropDownList(20), "folder drop down list");
						if (compareMultipleList(driver, expectedResult, lst).isEmpty()) {
							log(LogStatus.INFO, "Folder Drop Down list All options is verified", YesNo.No);
						} else {
							sa.assertTrue(false, "Folder Drop Down list All options is not verified");
							log(LogStatus.ERROR, "Folder Drop Down list All options is not verified", YesNo.Yes);
						}
						//select capital calls folder
						selectVisibleTextFromDropDown(driver, fd.getFundDrawdownFolderDropDownList(20), "folder dropdown", expectedResult.split(",")[1]);
						if (fd.clickOnEmailTemplatePreviewLink(expectedResult.split(",")[1])) {
							log(LogStatus.INFO, "successfully verified email preview link", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify preview link", YesNo.Yes);
							sa.assertTrue(false, "could not verify preview link");
						}
						//after clicking preview link page will come back to frame
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(30));
						}
						if(fd.selectEmailTemplateFromFundDrawdown(expectedResult.split(",")[1], expectedResult.split(",")[1])) {
							log(LogStatus.INFO, "successfully selected template", YesNo.No);
							click(driver, fd.getStep2NextButtonEmailing(30), "step 2 next button", action.BOOLEAN);
						}
						else {
							log(LogStatus.ERROR, "could not select email template", YesNo.Yes);
							sa.assertTrue(false, "could not select email template");
						}
						 ele = fd.getFundDrawdownEmailingSelectedRecipientErrorMsg(20);
						if (ele != null) {
							String aa = ele.getText().trim();
							if (aa.contains(MarketingInitiativesPageErrorMsg
									.selectRecipientOnStep3ErrorMsg("1"))) {
								log(LogStatus.INFO,
										MarketingInitiativesPageErrorMsg
												.selectRecipientOnStep3ErrorMsg("1")
												+ " error message is verified",
										YesNo.No);
							} else {
								sa.assertTrue(false,
										"error message is not verified Expected: "
												+ MarketingInitiativesPageErrorMsg
														.selectRecipientOnStep3ErrorMsg("1")
												+ " \t Actual : " + aa);
								log(LogStatus.ERROR,
										"error message is not verified Expected: "
												+ MarketingInitiativesPageErrorMsg
														.selectRecipientOnStep3ErrorMsg("1")
												+ " \t Actual : " + aa,
										YesNo.Yes);
							}
						} else {
							sa.assertTrue(false,
									MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg(
											"1") + " error message is not visible");
							log(LogStatus.ERROR,
									MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg(
											"1") + " error message is not visible",
									YesNo.Yes);
						}
						
						
						List<WebElement> labellist = fd
								.getFundDrawdownEmailingProcessingOptionsLableTextList();
						List<WebElement> checkBoxList = fd
								.getFundDrawdownEmailingProcessingOptionsCheckBoxList1();
						String[] expctRsult = { "BCC me on one message", "Use my signature",
								"Store an Activity for Each Message" };
						for (int i = 0; i < expctRsult.length; i++) {
							for (int j = 0; j < labellist.size(); j++) {
								if (labellist.get(j).getText().trim().contains(expctRsult[i])) {
									if (j != 2) {
										if (checkBoxList.get(j).getAttribute("checked") == null) {
											log(LogStatus.INFO,
													expctRsult[i] + "check box is not selected",
													YesNo.No);
										} else {
											sa.assertTrue(false,
													expctRsult[i] + "check box is selected");
											log(LogStatus.ERROR,
													expctRsult[i] + "check box is selected",
													YesNo.Yes);
										}
									} else {
										System.err.println("checked:   "
												+ checkBoxList.get(j).getAttribute("checked"));
										if (checkBoxList.get(j).getAttribute("checked")
												.contains("true")) {
											log(LogStatus.INFO,
													expctRsult[i] + "check box is selected",
													YesNo.No);
										} else {
											sa.assertTrue(false,
													expctRsult[i] + "check box is not selected");
											log(LogStatus.ERROR,
													expctRsult[i] + "check box is not selected",
													YesNo.Yes);
										}
									}
									break;
								}
								if (j == labellist.size() - 1) {
									sa.assertTrue(false, expctRsult[i] + "label is not verified");
									log(LogStatus.ERROR, expctRsult[i] + "label is not verified",
											YesNo.Yes);
								}
							}
						}
						//check first 2 checkboxes also
						checkBoxList = fd.getFundDrawdownEmailingProcessingOptionsCheckBoxList2();
						for (int i =0;i<2;i++) {
							if (!isSelected(driver, checkBoxList.get(i), i+" th checkbox")) {
								click(driver, checkBoxList.get(i), i+" th checkbox", action.BOOLEAN);
								log(LogStatus.INFO, "selected "+i+"th checkbox", YesNo.No);
							}
						}
						
							if(click(driver,fd.getfundDrawdownEmailingSendBtn(TopOrBottom.TOP, 30), "send button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on send button", YesNo.No);
								ele=fd.getFundDrawdownSendEmailCongratulationsErrorMsg(30);
								String errorMsg=FundDrawdownPageErrorMessage.congratulationErrorMsg+" "+FundDrawdownPageErrorMessage.generatedEmailedRecipientErrorMsg("1", "Email");
								if(ele!=null) {
									String aa1 =ele.getText().trim();
									if(aa1.contains(FundDrawdownPageErrorMessage.congratulationErrorMsg) && aa1.contains(FundDrawdownPageErrorMessage.generatedEmailedRecipientErrorMsg("1", "Email"))) {
										log(LogStatus.INFO, "Congratulation Error Message is verified", YesNo.No);
									}else {
										log(LogStatus.ERROR, "Congratulation Error Message is not verified. Expected: "+errorMsg+"\t Actual: "+aa1, YesNo.Yes);
									}
								}else {
									log(LogStatus.ERROR, "Congratulations Error Message is not visible so cannot verify it. Error Msg: "+errorMsg, YesNo.Yes);
								}
								
								//click on finished button and check drawdown as landing page
								if (click(driver, fd.getFinishedButtonEmailing(30), "finished button", action.SCROLLANDBOOLEAN)) {
									if (fd.fieldValueVerificationOnFundDrawdown(environment, mode, TabName.FundDrawdowns, FundDrawdownPageFieldLabelText.DD_No.toString(), SmokeDD1_ID)) {
										log(LogStatus.INFO, "successfully verified DD No so landing page is drawdown page", YesNo.No);
									}
									else {
										log(LogStatus.ERROR, "could not verify landing page after finished button", YesNo.Yes);
										sa.assertTrue(false, "could not verify landing page after finished button");
									}
								}
								else {
									log(LogStatus.ERROR, "finished button is not clickable", YesNo.Yes);
									sa.assertTrue(false,  "finished button is not clickable");
								}
							}else {
								log(LogStatus.ERROR, "Not able to click on send button so cannot send email to contact", YesNo.Yes);
							}
					}
					else {
						log(LogStatus.ERROR, "step 1 next button is not clickable", YesNo.Yes);
						sa.assertTrue(false, "step 1 next button is not clickable");
					}
				}
				else {
					log(LogStatus.ERROR, "header checkbox is not clickable", YesNo.Yes);
					sa.assertTrue(false, "header checkbox is not clickable");
				}
				switchToDefaultContent(driver);
			}
			else {
				log(LogStatus.ERROR,"could not click on created drawdown", YesNo.Yes);
				sa.assertTrue(false, "could not click on created drawdown");
			}
		}
		else {
			log(LogStatus.ERROR,"could not click on fund drawdowns tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on fund drawdowns tab");
		}
		
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc052_1_VerifySentEmailAndActivityFromDrawdownPage(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		SmokeCC1_ID = ExcelUtils.readData(smokeFilePath, "CapitalCall", excelLabel.Commitment_ID, SmokeCOMM8_ID, excelLabel.CapitalCalllID);
		String subject = ContactPageErrorMessage.emailSubject(SmokeCC1_ID, SmokeCOMM8_ID);
		subject = subject.split(":")[1];
		String subjectWithoutEmail= subject.substring(1,subject.length()-1);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
			if (cp.clickOnCreatedContact(environment,  SmokeC1_FName, SmokeC1_LName)) {
				if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Open_Activities, null)) {
					log(LogStatus.INFO, "Clicked on Open Activities",YesNo.No);	

					if (mode.toString().equalsIgnoreCase(Mode.Classic.toString())) {
						if (cp.verifyCreatedActivityHistory(environment, mode, ContactPageErrorMessage.emailSubject(SmokeCC1_ID, SmokeCOMM8_ID))) {
							log(LogStatus.INFO, "Verified successfully email activity history",YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify email activity history", YesNo.Yes);
							sa.assertTrue(false, "could not verify email activity history");
						}
					}
					else {
						WebElement ele =cp.verifyCreatedActivityHistory_Lighting(environment, subjectWithoutEmail); 
						if (ele!=null) {
							log(LogStatus.INFO, "Verified successfully email activity history",YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify email activity history", YesNo.Yes);
							sa.assertTrue(false, "could not verify email activity history");
						}
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on related list button", YesNo.Yes);
					sa.assertTrue(false, "could not click on related list button");
				}
				
			}
			else {
				log(LogStatus.ERROR, "could not click on created contact", YesNo.Yes);
				sa.assertTrue(false, "could not click on created contact");
			}
		}
		else {
			log(LogStatus.ERROR, "contacts tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "contacts tab is not clickable");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		boolean flag = false;
		for (int i=0;i<10;i++) {
			if (EmailLib.mailReceived(gmailUserName,gmailPassword, crmUser1EmailID, SmokeC1_EmailID,subjectWithoutEmail , "")) {
				log(LogStatus.INFO, "successfully verified email present", YesNo.No);
				flag = true;
				break;
			}
		}
		if(!flag) {
			log(LogStatus.ERROR, "could not verify email present", YesNo.Yes);
			sa.assertTrue(false, "could not verify email present");
		}
		flag = false;
		//check user bcc
		for (int i=0;i<10;i++) {
			if (EmailLib.mailReceived(gmailUserName2,gmailPassword, crmUser1EmailID, SmokeC1_EmailID,subjectWithoutEmail , "")) {
				log(LogStatus.INFO, "successfully verified email present", YesNo.No);
				flag = true;
				break;
			}
		}
		if(!flag) {
			log(LogStatus.ERROR, "could not verify email present", YesNo.Yes);
			sa.assertTrue(false, "could not verify email present");
		}
		
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc052_2_VerifySentEmailAndActivityFromDrawdownPage(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		SoftAssert saa = new SoftAssert();
		if (bp.clickOnTab(environment, mode, TabName.FundDrawdowns)) {
			if (fd.clickOnCreatedDrawdown(environment, mode, SmokeDD1_ID)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (bp.clickOnShowMoreDropdownOnly(environment, mode, PageName.FundDrawdown)) {
						log(LogStatus.PASS, "successfully clicked on dropdown",YesNo.No);
						WebElement ele=bp.actionDropdownElement(environment,  PageName.FundDrawdown, ShowMoreActionDropDownList.Send_Capital_Call_Notices, 30);
						if (ele!=null) {
							log(LogStatus.PASS, "successfully found send capital call notices button", YesNo.No);
							if (click(driver, bp.actionDropdownElement(environment, PageName.FundDrawdown,  ShowMoreActionDropDownList.Send_Capital_Call_Notices, 30), ShowMoreActionDropDownList.Send_Capital_Call_Notices.toString(), action.BOOLEAN)) {
								log(LogStatus.PASS, "successfully click on send button", YesNo.No);
							}
							else {
								log(LogStatus.FAIL, "cannot click on send button", YesNo.Yes);
								sa.assertTrue(false, "could not click on send button");
							}
						}
						else {
							log(LogStatus.ERROR, "could not find send capital call notices button", YesNo.Yes);
							sa.assertTrue(false, "could not find send capital call notices button");
						}
					}
					else {
						log(LogStatus.PASS, "could not click on show more dropdown", YesNo.Yes);
						sa.assertTrue(false, "could not click on show more dropdown");
					}
				}
				else {
				if (fd.getSendCapitalCallNotices_Classic(30)!=null) {
					log(LogStatus.PASS, "send capital call notices button is successfully present", YesNo.No);
					if (click(driver, fd.getSendCapitalCallNotices_Classic(30), "send capital call notices button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "successfully click on send capital call button", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not click on send button", YesNo.Yes);
						sa.assertTrue(false, "could not click on send button");
					}
				}
				else {
					log(LogStatus.FAIL, "send capital call notices button is not present", YesNo.Yes);
					sa.assertTrue(false, "send capital call notices button is not present");
				}
				}
				if (mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(30));
				}
				//verify contact 1 and contact2 on emailing grid
				System.err.println("SmokeCOMM1_ID : "+SmokeCOMM1_ID);
				SmokeCC1_ID=ExcelUtils.readData(smokeFilePath,"CapitalCall",excelLabel.Commitment_ID, SmokeCOMM1_ID, excelLabel.CapitalCalllID);
				System.err.println("SmokeCC1_ID : "+SmokeCC1_ID);
				saa = fd.verifyEmailingContentGrid(driver, PageName.FundDrawdown,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, SmokeC1_EmailID, SmokeCC1_ID, SmokeCOMM8_ID, Smoke_LP1, Smoke_P1, true);
				sa.combineAssertions(saa);
				
				SmokeCC2_ID = ExcelUtils.readData(smokeFilePath, "capitalCall", excelLabel.Commitment_ID, SmokeCOMM2_ID, excelLabel.CapitalCalllID);
				saa = fd.verifyEmailingContentGrid(driver, PageName.FundDrawdown,SmokeC2_FName+" "+SmokeC2_LName, SmokeINS1, SmokeC2_EmailID, SmokeCC2_ID, SmokeCOMM2_ID, Smoke_LP2, Smoke_P1, false);
				sa.combineAssertions(saa);
				switchToDefaultContent(driver);
			}
			else {
				log(LogStatus.ERROR, "could not click on created drawdown", YesNo.Yes);
				sa.assertTrue(false, "could not click on created drawdown");
			}
		}
		else {
			log(LogStatus.ERROR, "fund drawdown tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "drawdown tab is not clickable");
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc053_1_VerifyWrenchatSendCapitalCallNoticesPage(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		SoftAssert saa = new SoftAssert();
		
		WebElement ele;
		String xpath;
		String parentId=null;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String value = "Created Date";
		if (bp.clickOnTab(environment, mode, TabName.FundDrawdowns)) {
			if (fd.clickOnCreatedDrawdown(environment, mode, SmokeDD1_ID)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (bp.clickOnShowMoreDropdownOnly(environment, mode, PageName.FundDrawdown)) {
						log(LogStatus.PASS, "successfully clicked on dropdown",YesNo.No);
						ele=bp.actionDropdownElement(environment,  PageName.FundDrawdown, ShowMoreActionDropDownList.Send_Capital_Call_Notices, 30);
						if (ele!=null) {
							log(LogStatus.PASS, "successfully found send capital call notices button", YesNo.No);
							if (click(driver, bp.actionDropdownElement(environment,  PageName.FundDrawdown,  ShowMoreActionDropDownList.Send_Capital_Call_Notices, 30), ShowMoreActionDropDownList.Send_Capital_Call_Notices.toString(), action.BOOLEAN)) {
								log(LogStatus.PASS, "successfully click on send button", YesNo.No);
							}
							else {
								log(LogStatus.FAIL, "cannot click on send button", YesNo.Yes);
								sa.assertTrue(false, "could not click on send button");
							}
						}
						else {
							log(LogStatus.ERROR, "could not find send capital call notices button", YesNo.Yes);
							sa.assertTrue(false, "could not find send capital call notices button");
						}
					}
					else {
						log(LogStatus.PASS, "could not click on show more dropdown", YesNo.Yes);
						sa.assertTrue(false, "could not click on show more dropdown");
					}
				}
				else {
					if (fd.getSendCapitalCallNotices_Classic(30)!=null) {
						log(LogStatus.PASS, "send capital call notices button is successfully present", YesNo.No);
						if (click(driver, fd.getSendCapitalCallNotices_Classic(30), "send capital call notices button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "successfully click on send capital call button", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not click on send button", YesNo.Yes);
							sa.assertTrue(false, "could not click on send button");
						}
					}
					else {
						log(LogStatus.FAIL, "send capital call notices button is not present", YesNo.Yes);
						sa.assertTrue(false, "send capital call notices button is not present");
					}
				}
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(30));
				}


				//verify columns to display ui
				for (int j = 1; j <= 3; j++) {

					if (j==1 || j==2) {

						if (click(driver, fd.getWrenchIcon(30), "wrench icon", action.SCROLLANDBOOLEAN)) {

							if (j==1) {

								ThreadSleep(1000);

								if(selectVisibleTextFromDropDown(driver, market.getColumnToDisplayViewDropDownList(PageName.FundDrawdown,30), "view drop down list", ViewFieldsName.Contact_Fields)) {
									log(LogStatus.INFO,"Select value from view drop down list : "+ViewFieldsName.Contact_Fields,YesNo.Yes);
								}else {
									log(LogStatus.FAIL,"Not able to select value from view drop down list : "+ViewFieldsName.Contact_Fields,YesNo.Yes);
									sa.assertTrue(false, "Not able to select value from view drop down list : "+ViewFieldsName.Contact_Fields);
								}


								if (click(driver, fd.getCancelButtonOnColumnsToDisplay(10), "Cancel Button", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Not Able to Click on Cancel Button", YesNo.Yes);
									sa.assertTrue(false, "Not Able to Click on Cancel Button");
								}
							} else if(j==2) {
								if (click(driver, fd.getCrossIconOnColumnsToDisplay(10), "Cross Icon", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Cross Icon", YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Not Able to Click on Cross Icon", YesNo.Yes);
									sa.assertTrue(false, "Not Able to Click on Cross Icon");
								}
							} 
						}else{
							log(LogStatus.ERROR, "Not Able to Click on Wrench Icon", YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Wrench Icon");	
						}

					} 
					else if(j==3){


						String [] ss = {value};
						if(market.addAndRemoveCloumnInSelectProspectGrid(mode,PageName.FundDrawdown,ss, null, null,true).isEmpty()) {
							log(LogStatus.PASS, "column added form column to display popup", YesNo.No);
							ThreadSleep(2000);
							if(compareMultipleList(driver, value, market.getSelectProspectsHeaderTextList(PageName.FundDrawdown)).isEmpty()) {
								log(LogStatus.PASS, "Selected Prospects Header Text is verified ", YesNo.No);
							}else {

								log(LogStatus.FAIL,"Selected Prospects Header Text is not verified",YesNo.Yes);
								sa.assertTrue(false, "Selected Prospects Header Text is not verified");
							}
							ThreadSleep(2000);
							if(market.columnToDisplayRevertToDefaultsSettings(PageName.FundDrawdown,mode)) {
								log(LogStatus.PASS, "column to display settings is revert to default successfully", YesNo.No);
							}else {
								appLog.error("column to display settings is not revert to default");
								log(LogStatus.FAIL,"column to display settings is not revert to default",YesNo.Yes);
								sa.assertTrue(false,"column to display settings is not revert to default");
							}
							if(!compareMultipleList(driver, value, market.getSelectProspectsHeaderTextList(PageName.FundDrawdown)).isEmpty()) {
								log(LogStatus.PASS, "Select Prospects Header Text is removed", YesNo.No);
							}else {
								appLog.error("Select Prospects Header Text is not removed");
								log(LogStatus.FAIL,"Select Prospects Header Text is not removed",YesNo.Yes);
								sa.assertTrue(false, "Select Prospects Header Text is not removed");
							}
						}else {
							appLog.error("Not able to add column form column to display popup");
							log(LogStatus.FAIL,"Not able to add column form column to display popup",YesNo.Yes);
							sa.assertTrue(false, "Not able to add column form column to display popup");
						}



					}

				}


			}
			else {
				log(LogStatus.ERROR,"could not click on created drawdown", YesNo.Yes);
				sa.assertTrue(false, "could not click on created drawdown");
			}
		}
		else {
			log(LogStatus.ERROR,"could not click on fund drawdowns tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on fund drawdowns tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc053_2_VerifySentEmailAndActivityFromCapitalCallPage(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		CapitalCallsPageBusinessLayer ccp = new CapitalCallsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		SmokeCC2_ID = ExcelUtils.readData(smokeFilePath, "capitalCall", excelLabel.Commitment_ID, SmokeCOMM2_ID, excelLabel.CapitalCalllID);
		String keywords[] = ExcelUtils.readData("FilePath", excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.KeyWord_For_Search).split("<break>");
		String folder = ExcelUtils.readData(smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,
				"EmailTemplate1", excelLabel.Email_Template_Folder_Label);
		String template = ExcelUtils.readData(smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,
				"EmailTemplate1", excelLabel.Email_Template_Name);
	
		SoftAssert saa = new SoftAssert();
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (bp.clickOnTab(environment, mode, TabName.CapitalCalls)) {
			if (ccp.clickOnCreatedCapitalCall(environment, mode, SmokeCC2_ID)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (bp.clickOnShowMoreActionDownArrow(environment, PageName.CapitalCall, ShowMoreActionDropDownList.Send_Capital_Call_Notices, 30)) {
						log(LogStatus.INFO, "clicked on send capital call notices", YesNo.No);
					}
					else {
						log(LogStatus.PASS, "could not click on show more dropdown", YesNo.Yes);
						sa.assertTrue(false, "could not click on show more dropdown");
					}
				}
				else {
				if (ccp.getSendCapitalCallNotices_Classic(30)!=null) {
					log(LogStatus.PASS, "send capital call notices button is successfully present", YesNo.No);
					if (click(driver, ccp.getSendCapitalCallNotices_Classic(30), "send capital call notices button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "successfully click on send capital call button", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not click on send button", YesNo.Yes);
						sa.assertTrue(false, "could not click on send button");
					}
				}
				else {
					log(LogStatus.FAIL, "send capital call notices button is not present", YesNo.Yes);
					sa.assertTrue(false, "send capital call notices button is not present");
				}
				}
			
				if (mode.toString().equalsIgnoreCase(Mode.Lightning.toString()))
					switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(30));
				saa = fd.verifyEmailingContentGrid(driver, PageName.FundDrawdown,SmokeC2_FName+" "+SmokeC2_LName, SmokeINS1, SmokeC2_EmailID, SmokeCC2_ID, SmokeCOMM2_ID, Smoke_LP2, Smoke_P1, false);
				sa.combineAssertions(saa);
				List<WebElement> li = fd.listOfContactNames(30);
				
				boolean flag = false;
				for (int i =0;i<li.size();i++) {
					if (li.get(i).getText().trim().contains(SmokeC1_FName + " "+ SmokeC1_LName)) {
						flag = true;
						break;
					}
				}
				if (flag) {
					log(LogStatus.ERROR,SmokeC1_FName + " "+ SmokeC1_LName+ " is found in emailing grid but it should not be",YesNo.Yes);
					sa.assertTrue(false,SmokeC1_FName + " "+ SmokeC1_LName+ " is found in emailing grid but it should not be" );
				}
				else {
					log(LogStatus.INFO, "could not find "+SmokeC1_FName + " "+ SmokeC1_LName+ " as expected", YesNo.No);
				}
				
				if (fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.CapitalCall,SmokeC2_FName+" "+SmokeC2_LName, SmokeINS1, 30)) {
					log(LogStatus.INFO, "successfully clicked on checkbox of "+SmokeC2_FName+" "+SmokeC2_LName, YesNo.No);
					if (click(driver, fd.getStep1NextButtonEmailing(30), "step 1 next button", action.SCROLLANDBOOLEAN)) {
						if (fd.selectEmailTemplateFromFundDrawdown(folder, template)) {
							if (click(driver, fd.getStep2NextButtonEmailing(30), "step 2 next button", action.SCROLLANDBOOLEAN)) {
								List<WebElement> labellist = fd
										.getFundDrawdownEmailingProcessingOptionsLableTextList();
								List<WebElement> checkBoxList = fd
										.getFundDrawdownEmailingProcessingOptionsCheckBoxList1();
								String[] expctRsult = { "BCC me on one message", "Use my signature",
										"Store an Activity for Each Message" };
								for (int i = 0; i < expctRsult.length; i++) {
									for (int j = 0; j < labellist.size(); j++) {
										if (labellist.get(j).getText().trim().contains(expctRsult[i])) {
											if (j != 2) {
												if (checkBoxList.get(j).getAttribute("checked") == null) {
													log(LogStatus.INFO,
															expctRsult[i] + "check box is not selected",
															YesNo.No);
												} else {
													sa.assertTrue(false,
															expctRsult[i] + "check box is selected");
													log(LogStatus.ERROR,
															expctRsult[i] + "check box is selected",
															YesNo.Yes);
												}
											} else {
												System.err.println("checked:   "
														+ checkBoxList.get(j).getAttribute("checked"));
												if (checkBoxList.get(j).getAttribute("checked")
														.contains("true")) {
													log(LogStatus.INFO,
															expctRsult[i] + "check box is selected",
															YesNo.No);
												} else {
													sa.assertTrue(false,
															expctRsult[i] + "check box is not selected");
													log(LogStatus.ERROR,
															expctRsult[i] + "check box is not selected",
															YesNo.Yes);
												}
											}
											break;
										}
										if (j == labellist.size() - 1) {
											sa.assertTrue(false, expctRsult[i] + "label is not verified");
											log(LogStatus.ERROR, expctRsult[i] + "label is not verified",
													YesNo.Yes);
										}
									}
								}
								if (click(driver, fd.getfundDrawdownEmailingSendBtn(TopOrBottom.BOTTOM, 30), "send button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on send button", YesNo.No);
									WebElement ele=fd.getFundDrawdownSendEmailCongratulationsErrorMsg(30);
									String errorMsg=FundDrawdownPageErrorMessage.congratulationErrorMsg+" "+FundDrawdownPageErrorMessage.generatedEmailedRecipientErrorMsg("1", "Email");
									if(ele!=null) {
										String aa1 =ele.getText().trim();
										if(aa1.contains(FundDrawdownPageErrorMessage.congratulationErrorMsg) && aa1.contains(FundDrawdownPageErrorMessage.generatedEmailedRecipientErrorMsg("1", "Email"))) {
											log(LogStatus.INFO, "Congratulation Error Message is verified", YesNo.No);
										}else {
											log(LogStatus.ERROR, "Congratulation Error Message is not verified. Expected: "+errorMsg+"\t Actual: "+aa1, YesNo.Yes);
											sa.assertTrue(false, "Congratulation Error Message is not verified. Expected: "+errorMsg+"\t Actual: "+aa1);
										}
									}else {
										log(LogStatus.ERROR, "Congratulations Error Message is not visible so cannot verify it. Error Msg: "+errorMsg, YesNo.Yes);
										sa.assertTrue(false, "Congratulations Error Message is not visible so cannot verify it. Error Msg: "+errorMsg);
									}
									//check finished button functionality
									if (click(driver, fd.getFinishedButtonEmailing(30), "finished button", action.SCROLLANDBOOLEAN)) {
										//verify present on capital call page
										if (mode.equalsIgnoreCase(Mode.Lightning.toString()))
											switchToDefaultContent(driver);
										if (ccp.fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls, CapitalCallPageFieldLabelText.CC_No.toString(), SmokeCC2_ID, EditViewMode.View)) {
											log(LogStatus.INFO, "after clicking finished button, successfully verified capital call detail page is open", YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "after clicking finished button, landing page could not be verified", YesNo.Yes);
											sa.assertTrue(false, "after clicking finished button, landing page could not be verified");
										}
									}
									else {
										log(LogStatus.ERROR, "finished button could not be clicked", YesNo.Yes);
										sa.assertTrue(false, "finished button could not be clicked");
									}
								}
								else {
									log(LogStatus.ERROR, "could not click on send button", YesNo.Yes);
									sa.assertTrue(false, "could not click on send button");
								}
							}
							else {
								log(LogStatus.ERROR,"could not click on next step 2 button", YesNo.Yes);
								sa.assertTrue(false, "could not click on next step 2 button");
							}
						}
						else {
							log(LogStatus.ERROR, "could not select template", YesNo.Yes);
							sa.assertTrue(false, "could not select template");
						}
					}
					else {
						log(LogStatus.ERROR, "could not click on next button step 1", YesNo.Yes);
						sa.assertTrue(false, "could not click on next button step 1");
					}
				}
				else {
					log(LogStatus.ERROR,"could not click on contact checkbox", YesNo.Yes);
					sa.assertTrue(false, "could not click on contact checkbox");
				}
			}
			else {
				log(LogStatus.ERROR, "could not click on "+SmokeCC2_ID+" capital call", YesNo.Yes);
				sa.assertTrue(false, "could not click on "+SmokeCC2_ID+" capital call");
			}
		}
		else {
			log(LogStatus.ERROR, "capital call tab is not clickable", YesNo.No);
			sa.assertTrue(false, "capital call tab is not clickable");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc054_VerifyMailReceivedAndActivity(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		CapitalCallsPageBusinessLayer ccp = new CapitalCallsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String subject = ExcelUtils.readData(smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,
				"EmailTemplate1", excelLabel.Subject);
		String emailBody = ExcelUtils.readData(smokeFilePath, "CustomEmailFolder", excelLabel.Variable_Name,
				"EmailTemplate1", excelLabel.Email_Body);
		
		boolean flag = false;
		for (int i=0;i<10;i++) {
			if (EmailLib.mailReceived(gmailUserName,gmailPassword, crmUser1EmailID, SmokeC2_EmailID,subject , emailBody)) {
				log(LogStatus.INFO, "successfully verified email present", YesNo.No);
				flag = true;
				break;
			}
		}
		if(!flag) {
			log(LogStatus.ERROR, "could not verify email present", YesNo.Yes);
			sa.assertTrue(false, "could not verify email present");
		}
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
			if (cp.clickOnCreatedContact(environment,  SmokeC2_FName, SmokeC2_LName)) {
				if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Open_Activities, null)) {
					log(LogStatus.INFO, "Clicked on Open Activities",YesNo.No);	

					if (mode.toString().equalsIgnoreCase(Mode.Classic.toString())) {
						if (cp.verifyCreatedActivityHistory(environment, mode,"Email: "+ subject)) {
							log(LogStatus.INFO, "Verified successfully email activity history",YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify email activity history", YesNo.Yes);
							sa.assertTrue(false, "could not verify email activity history");
						}
					}
					else {
						WebElement ele =cp.verifyCreatedActivityHistory_Lighting(environment, subject); 
						if (ele!=null) {
							log(LogStatus.INFO, "Verified successfully email activity history",YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify email activity history", YesNo.Yes);
							sa.assertTrue(false, "could not verify email activity history");
						}
					}
				}
				else {
					log(LogStatus.ERROR, "not able to click on related button", YesNo.Yes);
					sa.assertTrue(false, "not able to click on related button");
				}
			}
			else {
				log(LogStatus.ERROR, "not able to click on created contact", YesNo.Yes);
				sa.assertTrue(false, "not able to click on created contact");
			}
		}
		else {
			log(LogStatus.ERROR,"not able to click on contacts tab", YesNo.Yes);
			sa.assertTrue(false, "not able to click on contacts tab");
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc055_1_CreateDistributionAndVerify(String environment, String mode) {
			LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
			FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
			FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
			FundDistributionsPageBusinessLayer fdi = new FundDistributionsPageBusinessLayer(driver);
			CapitalCallsPageBusinessLayer ccp = new CapitalCallsPageBusinessLayer(driver);
			BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
			String fields = "Total Commitments<break>Total Capital Called<break>Total Recallable Capital";
			String totalCommitments = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.Total_Commitments);
			String percent = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.Percent);
			// 75000000 Total_Commitments 10%  percent  7500000
			String percentValues = "80,10,10";
			String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
			String FifteenDayAfterDate=previousOrForwardDate(15, "MM/dd/YYYY");
			String capitalReturn = ExcelUtils.readData(smokeFilePath,"FundDistribution", excelLabel.Variable_Name, "FD1", excelLabel.CapitalReturn);
			String totalCapitalCalled = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.CallAmount);
			String dividends = ExcelUtils.readData(smokeFilePath,"FundDistribution", excelLabel.Variable_Name, "FD1", excelLabel.Dividends);
			String realizedGain = ExcelUtils.readData(smokeFilePath,"FundDistribution", excelLabel.Variable_Name, "FD1", excelLabel.RealizedGain);
			String OtherProceeds = ExcelUtils.readData(smokeFilePath,"FundDistribution", excelLabel.Variable_Name, "FD1", excelLabel.OtherProceeds);
			double capitalReturnD = Double.parseDouble(capitalReturn);
			double dDividends = Double.parseDouble(dividends);
			double dRealizedGain = Double.parseDouble(realizedGain);
			double dOtherProceeds = Double.parseDouble(OtherProceeds);
			String totalDistributions =Double.toString(capitalReturnD + dDividends + dRealizedGain+ dOtherProceeds);
			totalDistributions = String.valueOf(capitalReturnD + dDividends + dRealizedGain+ dOtherProceeds);
			lp.CRMLogin(crmUser1EmailID, adminPassword);
			WebElement ele;
			if (lp.clickOnTab(environment, mode, TabName.FundsTab)) {
				if (fp.clickOnCreatedFund(environment, mode, Smoke_Fund1)) {
					
					
					for (int i = 1; i <= 2; i++) {
						ThreadSleep(2000);
						if (bp.clickOnShowMoreActionDownArrow(environment,  PageName.FundsPage, ShowMoreActionDropDownList.Create_Distribution, 30)) {
							log(LogStatus.INFO, "clicked on Create_Distributions : "+i, YesNo.No);
								if (mode.equalsIgnoreCase(Mode.Lightning.toString())){
								switchToFrame(driver, 30, fd.getEmailingFrame_Lighting(30));
								}
								
								if (i==1) {
									ele = fd.getFundDistributionBackArrowLink(environment,mode, 10);
								} else {
									ele = fd.getfundDistributionCancelButton(environment,mode, 10);
								}

								if (clickUsingJavaScript(driver, ele, "Back Arrow/Cancel Btn",action.BOOLEAN)) {
									appLog.info("Clicked on Back Arrow/Cancel : "+i);
								}else{
									appLog.error("Not Able to Click on Back Arrow/Cancel Btn : "+i);
									sa.assertTrue(false, "Not Able to Click on Back Arrow/Cancel Btn : "+i);
									log(LogStatus.SKIP, "Not Able to Click on Back Arrow/Cancel Btn : "+i, YesNo.Yes);
								}

							}
						else {
							log(LogStatus.ERROR, "could not click on show more dropdown : "+i, YesNo.Yes);
							sa.assertTrue(false, "could not click on show more dropdown : "+i);
						}
						
						switchToDefaultContent(driver);
						ThreadSleep(5000);
					}
					switchToDefaultContent(driver);
					if (bp.clickOnShowMoreActionDownArrow(environment,  PageName.FundsPage, ShowMoreActionDropDownList.Create_Distribution, 30)) {
						log(LogStatus.INFO, "clicked on Create_Distributions", YesNo.No);
						
						String[] linkClick = {Smoke_LP1,"CMT"};
						for (int j = 0; j < linkClick.length; j++) {
						
							if (mode.equalsIgnoreCase(Mode.Lightning.toString())){
								switchToFrame(driver, 30, fd.getEmailingFrame_Lighting(30));
							}

						
							sendKeys(driver, fdi.getCapitalReturn( 30),capitalReturn, "capitalReturn", action.SCROLLANDBOOLEAN);
							sendKeys(driver, fdi.getDividends( 30),dividends, "dividends", action.SCROLLANDBOOLEAN);
							sendKeys(driver, fdi.getRealizedGain( 30),realizedGain, "realized gain", action.SCROLLANDBOOLEAN);
							sendKeys(driver, fdi.getTxtOtherProceeds(30),OtherProceeds, "other proceeds", action.SCROLLANDBOOLEAN);
							sendKeys(driver, fdi.getDistributionDate(30),FifteenDayAfterDate , "distribution date", action.BOOLEAN);
							if (click(driver, fdi.getSetupInvestorDist(30), "setup investor distribution sbutton", action.SCROLLANDBOOLEAN)) {
								
								ThreadSleep(2000);
								// Azhar Start 


								String xpath;

								if (j==0) {
									xpath = "//div[@class='individualPalette']//td/a";	
								} else {
									xpath = "(//div[@class='individualPalette']//td/a)[2]";
								}
							
								ele = FindElement(driver, xpath, linkClick[j], action.SCROLLANDBOOLEAN, 10);

								if (ele!=null) {
									appLog.info("Clicked on : "+linkClick[j]);
									if (click(driver, ele, linkClick[j], action.SCROLLANDBOOLEAN)) {
										switchToDefaultContent(driver);
										if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
											
											xpath ="//div[contains(@class,'outputName')]//*[text()='"+linkClick[j]+"']";
										}else{
											xpath = "//h2[contains(text(),'"+linkClick[j]+"')]";	
										}

										ele = FindElement(driver, xpath, "on same window : "+linkClick[j], action.SCROLLANDBOOLEAN, 10);

										if (ele!=null) {
											appLog.info("Landing Page Verified : "+linkClick[j]);
										} else {
											appLog.error("Landing Page Not Verified : "+linkClick[j]);
											sa.assertTrue(false, "Landing Page Not Verified : "+linkClick[j]);
										}
										driver.navigate().back();

									} else {
										appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
										sa.assertTrue(false, "Not able to click on "+linkClick[j]+" so cannot verify landing page");
									}
								} else {
									appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
									sa.assertTrue(false, "Not able to click on "+linkClick[j]+" so cannot verify landing page");
								}




								// Azhar End
								
							}else{
								log(LogStatus.ERROR, "could not click on getSetupInvestorDist", YesNo.Yes);
								sa.assertTrue(false, "could not click on getSetupInvestorDist");
							}
					
							
							
							
						}
						
						
						
							
						
					}
					else {
						log(LogStatus.ERROR, "could not click on show more dropdown", YesNo.Yes);
						sa.assertTrue(false, "could not click on show more dropdown");
					}
				}
				else {
					log(LogStatus.ERROR, "could not find created fund on funds page", YesNo.Yes);
					sa.assertTrue(false,  "could not find created fund on funds page");
				}
			}
			else {
				log(LogStatus.ERROR, "funds tab is not clickable", YesNo.Yes);
				sa.assertTrue(false, "funds tab is not clickable");
			}
			switchToDefaultContent(driver);
			lp.CRMlogout(environment, mode);
			sa.assertAll();
		
		
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc055_2_CreateDistributionAndVerify(String environment, String mode) {
			LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
			FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
			FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
			FundDistributionsPageBusinessLayer fdi = new FundDistributionsPageBusinessLayer(driver);
			CapitalCallsPageBusinessLayer ccp = new CapitalCallsPageBusinessLayer(driver);
			BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
			String fields = "Total Commitments<break>Total Capital Called<break>Total Recallable Capital";
			String totalCommitments = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.Total_Commitments);
			String percent = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.Percent);
			// 75000000 Total_Commitments 10%  percent  7500000
			String percentValues = "80,10,10";
			String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
			String FifteenDayAfterDate=previousOrForwardDate(15, "MM/dd/YYYY");
			String capitalReturn = ExcelUtils.readData(smokeFilePath,"FundDistribution", excelLabel.Variable_Name, "FD1", excelLabel.CapitalReturn);
			String totalCapitalCalled = ExcelUtils.readData(smokeFilePath,"FundDrawdown", excelLabel.Variable_Name, "DD1", excelLabel.CallAmount);
			String dividends = ExcelUtils.readData(smokeFilePath,"FundDistribution", excelLabel.Variable_Name, "FD1", excelLabel.Dividends);
			String realizedGain = ExcelUtils.readData(smokeFilePath,"FundDistribution", excelLabel.Variable_Name, "FD1", excelLabel.RealizedGain);
			String OtherProceeds = ExcelUtils.readData(smokeFilePath,"FundDistribution", excelLabel.Variable_Name, "FD1", excelLabel.OtherProceeds);
			double capitalReturnD = Double.parseDouble(capitalReturn);
			double dDividends = Double.parseDouble(dividends);
			double dRealizedGain = Double.parseDouble(realizedGain);
			double dOtherProceeds = Double.parseDouble(OtherProceeds);
			String totalDistributions =Double.toString(capitalReturnD + dDividends + dRealizedGain+ dOtherProceeds);
			totalDistributions = String.valueOf(capitalReturnD + dDividends + dRealizedGain+ dOtherProceeds);
			lp.CRMLogin(crmUser1EmailID, adminPassword);
			WebElement ele;
			if (lp.clickOnTab(environment, mode, TabName.FundsTab)) {
				if (fp.clickOnCreatedFund(environment, mode, Smoke_Fund1)) {
					
					
					for (int i = 1; i <= 2; i++) {
						ThreadSleep(2000);
						if (bp.clickOnShowMoreActionDownArrow(environment,  PageName.FundsPage, ShowMoreActionDropDownList.Create_Distribution, 30)) {
							log(LogStatus.INFO, "clicked on Create_Distributions : "+i, YesNo.No);
								if (mode.equalsIgnoreCase(Mode.Lightning.toString())){
								switchToFrame(driver, 30, fd.getEmailingFrame_Lighting(30));
								}
								
								if (i==1) {
									ele = fd.getFundDistributionBackArrowLink(environment,mode, 10);
								} else {
									ele = fd.getfundDistributionCancelButton(environment,mode, 10);
								}

								if (clickUsingJavaScript(driver, ele, "Back Arrow/Cancel Btn",action.BOOLEAN)) {
									appLog.info("Clicked on Back Arrow/Cancel : "+i);
								}else{
									appLog.error("Not Able to Click on Back Arrow/Cancel Btn : "+i);
									sa.assertTrue(false, "Not Able to Click on Back Arrow/Cancel Btn : "+i);
									log(LogStatus.SKIP, "Not Able to Click on Back Arrow/Cancel Btn : "+i, YesNo.Yes);
								}

							}
						else {
							log(LogStatus.ERROR, "could not click on show more dropdown : "+i, YesNo.Yes);
							sa.assertTrue(false, "could not click on show more dropdown : "+i);
						}
						
						
					}
					
					if (bp.clickOnShowMoreActionDownArrow(environment, PageName.FundsPage, ShowMoreActionDropDownList.Create_Distribution, 30)) {
						log(LogStatus.INFO, "clicked on Create_Distributions", YesNo.No);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString()))
							switchToFrame(driver, 30, fd.getEmailingFrame_Lighting(30));
						if (fd.getValueInCreateDrawdownsOrDistributionsPage(fields.split("<break>")[0])!=null) {
							String totalCommitments1 = 	bp.convertNumberAccordingToFormatWithCurrencySymbol(totalCommitments, "0,000.00");

							if (fd.getValueInCreateDrawdownsOrDistributionsPage(fields.split("<break>")[0]).equalsIgnoreCase(totalCommitments1)) {
								log(LogStatus.INFO, "successfully verified Total Commitments value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify Total Commitments value", YesNo.Yes);
								sa.assertTrue(false, "could not verify Total Commitments value");
							}
						}
						if (fd.getValueInCreateDrawdownsOrDistributionsPage(fields.split("<break>")[1])!=null) {
							if (fd.getValueInCreateDrawdownsOrDistributionsPage(fields.split("<break>")[1]).contains("0.0")) {
								log(LogStatus.INFO, "successfully verified total capital called value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify total capital called value", YesNo.Yes);
								sa.assertTrue(false, "could not verify total capital called value");
							}
						}
						else {
							log(LogStatus.INFO, "no value is written in total capital called as expected", YesNo.No);
						}
						if (fd.getValueInCreateDrawdownsOrDistributionsPage(fields.split("<break>")[2])!=null) {
							String text = fd.getValueInCreateDrawdownsOrDistributionsPage(fields.split("<break>")[2]);
							if (text.contains("") ) {
								log(LogStatus.INFO, "successfully verified total recallable capital value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify total recallable capital value", YesNo.Yes);
								sa.assertTrue(false, "could not verify total recallable capital value");
							}
						}
						else {
							log(LogStatus.INFO, "no value is written in total capital called as expected", YesNo.No);
						}

						//capital amount, management fee, other fee textbox
						click(driver, fd.getTotalRecallablecapital(30), "", action.BOOLEAN);
						if (getValueFromElementUsingJavaScript(driver, fdi.getCapitalReturn(30), "capital amount value").contains("0.0")) {
							log(LogStatus.INFO,"successfully verified CapitalReturn textbox value", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify CapitalReturn textbox value", YesNo.Yes);
							sa.assertTrue(false, "could not verify CapitalReturn textbox value");
						}
						if (getValueFromElementUsingJavaScript(driver, fdi.getDividends(30), "Dividends value").contains("0.0")) {
							log(LogStatus.INFO,"successfully verified Dividends textbox value", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify Dividends textbox value", YesNo.Yes);
							sa.assertTrue(false, "could not verify Dividends textbox value");
						}
						if (getValueFromElementUsingJavaScript(driver, fdi.getRealizedGain(30), "RealizedGain value").contains("0.0")) {
							log(LogStatus.INFO,"successfully verified RealizedGain textbox value", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify RealizedGain textbox value", YesNo.Yes);
							sa.assertTrue(false, "could not verify RealizedGain textbox value");
						}
						if (getValueFromElementUsingJavaScript(driver, fdi.getTxtOtherProceeds(30), "OtherProceeds value").contains("0.0")) {
							log(LogStatus.INFO,"successfully verified OtherProceeds textbox value", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify OtherProceeds textbox value", YesNo.Yes);
							sa.assertTrue(false, "could not verify OtherProceeds textbox value");
						}
						if (fdi.getTotalDistributions(30).getText().trim().contains("0.00")) {
							log(LogStatus.INFO, "successfully verified TotalDistributions( value", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify TotalDistributions amount value", YesNo.Yes);
							sa.assertTrue(false, "could not verify TotalDistributions amount value");
						}
						
						if (!isSelected(driver, fdi.getCanCheckRecalled(30), "can capital be recalled checkbox"))
							log(LogStatus.INFO, "successfully found can capital be recalled checkbox as unchecked", YesNo.No);
						else {
							log(LogStatus.ERROR, "checkbox for capital to be recalled is checked but it should not be", YesNo.Yes);
							sa.assertTrue(false,"checkbox for capital to be recalled is checked but it should not be");
						}
						//verified UI now entering data in edit mode
							sendKeys(driver, fdi.getCapitalReturn( 30),capitalReturn, "capitalReturn", action.SCROLLANDBOOLEAN);
							sendKeys(driver, fdi.getDividends( 30),dividends, "dividends", action.SCROLLANDBOOLEAN);
							sendKeys(driver, fdi.getRealizedGain( 30),realizedGain, "realized gain", action.SCROLLANDBOOLEAN);
							sendKeys(driver, fdi.getTxtOtherProceeds(30),OtherProceeds, "other proceeds", action.SCROLLANDBOOLEAN);
							
							click(driver, fdi.getCanCheckRecalled(30), "can capital be recalled checkbox", action.SCROLLANDBOOLEAN);
							
							sendKeys(driver, fdi.getDistributionDate(30),FifteenDayAfterDate , "distribution date", action.BOOLEAN);
							//verify values present in capitalReturn, dividends, realizedGain and OtherProceeds  
							String a=getValueFromElementUsingJavaScript(driver, fdi.getCapitalReturn( 30), "value textbox CapitalReturn");
							if (a.trim().contains(capitalReturn)) {
								log(LogStatus.INFO,"successfully verified CapitalReturn textbox value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify CapitalReturn textbox value"+a+" and "+capitalReturn,YesNo.Yes);
								sa.assertTrue(false, "could not verify CapitalReturn textbox value");
							}
							a=getValueFromElementUsingJavaScript(driver, fdi.getDividends( 30), "value textbox Dividends");
							if (a.trim().contains(dividends)) {
								log(LogStatus.INFO,"successfully verified Dividends textbox value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify Dividends textbox value"+a+" and "+dividends,YesNo.Yes);
								sa.assertTrue(false, "could not verify Dividends textbox value");
							}
							a=getValueFromElementUsingJavaScript(driver, fdi.getRealizedGain( 30), "value textbox OtherFee");
							if (a.trim().contains(realizedGain)) {
								log(LogStatus.INFO,"successfully verified RealizedGain textbox value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify RealizedGain textbox value"+a+ " and "+realizedGain, YesNo.Yes);
								sa.assertTrue(false, "could not verify RealizedGain textbox value");
							}
							a=getValueFromElementUsingJavaScript(driver, fdi.getTxtOtherProceeds(30), "value textbox OtherProceeds");
							if (a.trim().contains(OtherProceeds)) {
								log(LogStatus.INFO,"successfully verified OtherProceeds textbox value", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify OtherProceeds textbox value"+a+ " and "+OtherProceeds, YesNo.Yes);
								sa.assertTrue(false, "could not verify OtherProceeds textbox value");
							}
							String totalDistributions1=bp.convertNumberAccordingToFormatWithCurrencySymbol(totalDistributions, "0,000.00");

							if (getText(driver,fdi.getTotalDistributions(30), "total distribution value", action.BOOLEAN).trim().contains(totalDistributions1)) {
								log(LogStatus.INFO, "call amount verified successfully", YesNo.No);
							}
							else {
								log(LogStatus.ERROR,"call amount value could not be verified"+getText(driver,fdi.getTotalDistributions(30), "total distribution value", action.BOOLEAN)+ " and "+capitalReturn, YesNo.Yes);
								sa.assertTrue(false, "call amount value could not be verified");
							}

							click(driver, fdi.getSetupInvestorDist(30), "setup investor distribution sbutton", action.SCROLLANDBOOLEAN);
							
							String[][] investorDistGrid = {
									{Smoke_LP1,SmokeCOMM1_ID, fdi.getCapitalReturn(SmokeCOMM1_CommitmentAmount, totalCommitments, capitalReturn), fdi.getDividends(SmokeCOMM1_CommitmentAmount, totalCommitments, dividends),
										fdi.getRealizedGain(SmokeCOMM1_CommitmentAmount, totalCommitments, realizedGain), fdi.getOtherProceeds(SmokeCOMM1_CommitmentAmount, totalCommitments, OtherProceeds),
									fdi.getTotalInvestorDistributions(fdi.getCapitalReturn(SmokeCOMM1_CommitmentAmount, totalCommitments, capitalReturn), fdi.getDividends(SmokeCOMM1_CommitmentAmount, totalCommitments, dividends),
										fdi.getRealizedGain(SmokeCOMM1_CommitmentAmount, totalCommitments, realizedGain), fdi.getOtherProceeds(SmokeCOMM1_CommitmentAmount, totalCommitments, OtherProceeds))
									},
									{Smoke_LP2,SmokeCOMM2_ID, fdi.getCapitalReturn(SmokeCOMM2_CommitmentAmount, totalCommitments, capitalReturn), fdi.getDividends(SmokeCOMM2_CommitmentAmount, totalCommitments, dividends),
										fdi.getRealizedGain(SmokeCOMM2_CommitmentAmount, totalCommitments, realizedGain), fdi.getOtherProceeds(SmokeCOMM2_CommitmentAmount, totalCommitments, OtherProceeds),
									fdi.getTotalInvestorDistributions(fdi.getCapitalReturn(SmokeCOMM2_CommitmentAmount, totalCommitments, capitalReturn), fdi.getDividends(SmokeCOMM2_CommitmentAmount, totalCommitments, dividends),
										fdi.getRealizedGain(SmokeCOMM2_CommitmentAmount, totalCommitments, realizedGain), fdi.getOtherProceeds(SmokeCOMM2_CommitmentAmount, totalCommitments, OtherProceeds))
									},
									{Smoke_LP1,SmokeCOMM3_ID, fdi.getCapitalReturn(SmokeCOMM3_CommitmentAmount, totalCommitments, capitalReturn), fdi.getDividends(SmokeCOMM3_CommitmentAmount, totalCommitments, dividends),
										fdi.getRealizedGain(SmokeCOMM3_CommitmentAmount, totalCommitments, realizedGain), fdi.getOtherProceeds(SmokeCOMM3_CommitmentAmount, totalCommitments, OtherProceeds),
									fdi.getTotalInvestorDistributions(fdi.getCapitalReturn(SmokeCOMM3_CommitmentAmount, totalCommitments, capitalReturn), fdi.getDividends(SmokeCOMM3_CommitmentAmount, totalCommitments, dividends),
										fdi.getRealizedGain(SmokeCOMM3_CommitmentAmount, totalCommitments, realizedGain), fdi.getOtherProceeds(SmokeCOMM3_CommitmentAmount, totalCommitments, OtherProceeds))
									},
									{Smoke_LP3,SmokeCOMM4_ID, fdi.getCapitalReturn(SmokeCOMM4_CommitmentAmount, totalCommitments, capitalReturn), fdi.getDividends(SmokeCOMM4_CommitmentAmount, totalCommitments, dividends),
										fdi.getRealizedGain(SmokeCOMM4_CommitmentAmount, totalCommitments, realizedGain), fdi.getOtherProceeds(SmokeCOMM4_CommitmentAmount, totalCommitments, OtherProceeds),
									fdi.getTotalInvestorDistributions(fdi.getCapitalReturn(SmokeCOMM4_CommitmentAmount, totalCommitments, capitalReturn), fdi.getDividends(SmokeCOMM4_CommitmentAmount, totalCommitments, dividends),
										fdi.getRealizedGain(SmokeCOMM4_CommitmentAmount, totalCommitments, realizedGain), fdi.getOtherProceeds(SmokeCOMM4_CommitmentAmount, totalCommitments, OtherProceeds))
									},
									{Smoke_LP4,SmokeCOMM5_ID, fdi.getCapitalReturn(SmokeCOMM5_CommitmentAmount, totalCommitments, capitalReturn), fdi.getDividends(SmokeCOMM5_CommitmentAmount, totalCommitments, dividends),
										fdi.getRealizedGain(SmokeCOMM5_CommitmentAmount, totalCommitments, realizedGain), fdi.getOtherProceeds(SmokeCOMM5_CommitmentAmount, totalCommitments, OtherProceeds),
									fdi.getTotalInvestorDistributions(fdi.getCapitalReturn(SmokeCOMM5_CommitmentAmount, totalCommitments, capitalReturn), fdi.getDividends(SmokeCOMM5_CommitmentAmount, totalCommitments, dividends),
										fdi.getRealizedGain(SmokeCOMM5_CommitmentAmount, totalCommitments, realizedGain), fdi.getOtherProceeds(SmokeCOMM5_CommitmentAmount, totalCommitments, OtherProceeds))
									}
							};

							for (int i =0;i<investorDistGrid.length;i++) {
								if (fdi.verifyOneRowCreateDistributionsPage(investorDistGrid[i])) {
									log(LogStatus.INFO, "data for row "+i+" was successfully verified", YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "data for row "+i+" could not be verified", YesNo.Yes);
									sa.assertTrue(false, "data for row "+i +" could not be verified");
								}
							}
							List<WebElement> commitments = fd.getCommitmentListFromInvestorDistribution(environment, mode);
							List<WebElement> commitmentAmounts = fd.getCommitmentAmountListFromInvestorDistribution(environment, mode);
							List<WebElement> capitalReturns = fd.getCapitalReturnFromInvestorDistribution(environment, mode);
							List<WebElement> dividend = fd.getDividendsFromInvestorDistribution(environment, mode);
							List<WebElement> realisedGains = fd.getRealisedGainFromInvestorDistribution(environment, mode);
							List<WebElement> otherProceeds = fd.getOtherProceedsFromInvestorDistribution(environment, mode);
							List<WebElement> totalDistribution = fd.getTotalFromInvestorDistribution(environment, mode);
							String commitName;
							String totalAmounts;
							String convertValue;
							String amount;
							
							if (!commitments.isEmpty()) {
								
								if (commitments.size() == commitmentAmounts.size()) {
									
									for (int i = 0; i < commitments.size(); i++) {
										
										commitName = commitments.get(i).getText().trim();
										ExcelUtils.writeData(smokeFilePath, commitName, "InvestorDistribution", excelLabel.Variable_Name, "ID"+(i+1), excelLabel.Commitment_ID);
										System.err.println(commitName);
										amount = commitmentAmounts.get(i).getText().trim();
										amount=fd.convertNumberAccordingToFormatWithoutCurrencySymbol(amount, "0000");
										ExcelUtils.writeData(smokeFilePath, amount, "InvestorDistribution", excelLabel.Variable_Name, "ID"+(i+1), excelLabel.Commitment_Amount);
										System.err.println(amount);
										amount = capitalReturns.get(i).getAttribute("value").trim();
										amount=fd.convertNumberAccordingToFormatWithoutCurrencySymbol(amount, "0000");
										ExcelUtils.writeData(smokeFilePath, amount, "InvestorDistribution", excelLabel.Variable_Name, "ID"+(i+1), excelLabel.CapitalReturn);
										System.err.println(amount);
										amount = dividend.get(i).getAttribute("value").trim();
										amount=fd.convertNumberAccordingToFormatWithoutCurrencySymbol(amount, "0.000");
										ExcelUtils.writeData(smokeFilePath, amount, "InvestorDistribution", excelLabel.Variable_Name, "ID"+(i+1), excelLabel.Dividends);
										System.err.println(amount);
										amount = realisedGains.get(i).getAttribute("value").trim();
										amount=fd.convertNumberAccordingToFormatWithoutCurrencySymbol(amount, "0.000");
										ExcelUtils.writeData(smokeFilePath, amount, "InvestorDistribution", excelLabel.Variable_Name, "ID"+(i+1), excelLabel.RealizedGain);
										System.err.println(amount);
										amount = otherProceeds.get(i).getAttribute("value").trim();
										amount=fd.convertNumberAccordingToFormatWithoutCurrencySymbol(amount, "0.000");
										ExcelUtils.writeData(smokeFilePath, amount, "InvestorDistribution", excelLabel.Variable_Name, "ID"+(i+1), excelLabel.OtherProceeds);
										System.err.println(amount);
										amount = totalDistribution.get(i).getText().trim();
										amount=fd.convertNumberAccordingToFormatWithoutCurrencySymbol(amount, "0.000");
										ExcelUtils.writeData(smokeFilePath, amount, "InvestorDistribution", excelLabel.Variable_Name, "ID"+(i+1), excelLabel.TotalDistributions);
										System.err.println(amount);
									}
									
								} else {
									log(LogStatus.ERROR, "Column list are not equal", YesNo.Yes);
									sa.assertTrue(false, "Column list are not equal");
								}
								
							} else {
								log(LogStatus.ERROR, "Commitment amount Grid is empty", YesNo.Yes);
								sa.assertTrue(false, "Commitment amount Grid is empty");
							}
							if (click(driver, fdi.getGenerateInvDist(30), "generate inv dist", action.SCROLLANDBOOLEAN)) {
								//fund detail page
								ThreadSleep(3000);
								String FDID=null;
								if(bp.openAppFromAppLauchner(ObjectName.Fund_Distributions.toString(), 20)) {
									appLog.info("able to open fund distribution form app laucnher");
									 FDID= fp.getFirstValueFromRelatedList(environment,mode,RelatedList.FundDistribution.toString(), Smoke_Fund1).getText().trim();

								}else {
									log(LogStatus.ERROR, "Not able to open fund distribution form app laucnher", YesNo.Yes);
									sa.assertTrue(false, "Not able to open fund distribution form app laucnher");
								}
								
							
								if(FDID!=null) {
								if (clickUsingJavaScript(driver, fp.getFirstValueFromRelatedList(environment,mode,RelatedList.FundDistribution.toString(), Smoke_Fund1), "FundDistribution id", action.SCROLLANDBOOLEAN)) {
									
									ExcelUtils.writeData(smokeFilePath, FDID, "FundDistribution", excelLabel.Variable_Name, "FD1", excelLabel.FundDistributionID);
									for (int i = 0;i<5;i++) {
										ExcelUtils.writeData(smokeFilePath, FDID, "InvestorDistribution", excelLabel.Variable_Name, "ID"+(i+1), excelLabel.FundDistributionID);

									}
									
										if(bp.ClickonRelatedTab_Lighting(environment, RecordType.Fund, null)) {
											appLog.info("clicked on related list tab");
										}else {
											appLog.error("could not click on related tab");
										}
										
										if (bp.scrollToRelatedListViewAll_Lightning(environment, mode, RelatedList.InvestorDistributions, true))  {
											log(LogStatus.INFO, "succesfully scrolled to InvestorDistributions related listi", YesNo.No);
										}
										else {
											log(LogStatus.ERROR, "coould not scroll to InvestorDistributions related list", YesNo.Yes);
											sa.assertTrue(false, "coould not scroll to InvestorDistributions related list");
										}
										
										if (bp.clickOnViewAllRelatedList(environment, mode, RelatedList.InvestorDistributions)) {
											
										}
										else {
											log(LogStatus.ERROR, "could not click on view all of related list of "+RelatedList.InvestorDistributions.toString(), YesNo.Yes);
											sa.assertTrue(false, "could not click on view all of related list of "+RelatedList.InvestorDistributions.toString());
										}
										List<String> s = fp.returnAllValuesInRelatedList(environment, mode, RelatedList.InvestorDistributions.toString());
										for (int i =0;i<5;i++) {
											log(LogStatus.INFO, s.get(i)+" id of investor distribution", YesNo.No);
											ExcelUtils.writeData(smokeFilePath,s.get(i) ,"InvestorDistribution",excelLabel.Variable_Name, "ID"+(i+1), excelLabel.InvestorDistributionID);
										}	
					
								}
								else {
									log(LogStatus.ERROR, "fund distribution link is not clickable "+FDID, YesNo.Yes);
									sa.assertTrue(false, "fund distribution link is not clickable "+FDID);
								}
								}else {
									log(LogStatus.ERROR, "fund distribution link is not present so cannot conitnue "+FDID, YesNo.Yes);
									sa.assertTrue(false, "fund distribution link is not present so cannot conitnue "+FDID);
								}
							}
							else {
								log(LogStatus.ERROR, "generate capital call button is not clickable", YesNo.Yes);
								sa.assertTrue(false, "generate capital call button is not clickable");
							}
						
					}
					else {
						log(LogStatus.ERROR, "could not click on show more dropdown", YesNo.Yes);
						sa.assertTrue(false, "could not click on show more dropdown");
					}
				}
				else {
					log(LogStatus.ERROR, "could not find created fund on funds page", YesNo.Yes);
					sa.assertTrue(false,  "could not find created fund on funds page");
				}
			}
			else {
				log(LogStatus.ERROR, "funds tab is not clickable", YesNo.Yes);
				sa.assertTrue(false, "funds tab is not clickable");
			}
			switchToDefaultContent(driver);
			lp.CRMlogout(environment, mode);
			sa.assertAll();
		
		
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc056_VerifyCreatedInvestorDistribution(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		FundDistributionsPageBusinessLayer fdi = new FundDistributionsPageBusinessLayer(driver);
		CapitalCallsPageBusinessLayer ccp = new CapitalCallsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		InvestorDistributionsPageBusinessLayer id = new InvestorDistributionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String SmokeID1Data[] = new String[10];
		String SmokeID2Data[] = new String[10];
		String SmokeID3Data[] = new String[10];
		String SmokeID4Data[] = new String[10];
		String SmokeID5Data[] = new String[10];
		SmokeID1Data[0] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID1", excelLabel.InvestorDistributionID);
		SmokeID1Data[1] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID1", excelLabel.FundDistributionID);
		SmokeID1Data[2] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID1", excelLabel.Commitment_ID);
		SmokeID1Data[3] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID1", excelLabel.CapitalReturn);
		SmokeID1Data[4] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID1", excelLabel.Dividends);
		SmokeID1Data[5] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID1", excelLabel.RealizedGain);
		SmokeID1Data[6] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID1", excelLabel.OtherProceeds);
		SmokeID1Data[7] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID1", excelLabel.TotalDistributions);
		
		SmokeID2Data[0] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID2", excelLabel.InvestorDistributionID);
		SmokeID2Data[1] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID2", excelLabel.FundDistributionID);
		SmokeID2Data[2] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID2", excelLabel.Commitment_ID);
		SmokeID2Data[3] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID2", excelLabel.CapitalReturn);
		SmokeID2Data[4] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID2", excelLabel.Dividends);
		SmokeID2Data[5] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID2", excelLabel.RealizedGain);
		SmokeID2Data[6] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID2", excelLabel.OtherProceeds);
		SmokeID2Data[7] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID2", excelLabel.TotalDistributions);
		
		SmokeID3Data[0] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID3", excelLabel.InvestorDistributionID);
		SmokeID3Data[1] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID3", excelLabel.FundDistributionID);
		SmokeID3Data[2] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID3", excelLabel.Commitment_ID);
		SmokeID3Data[3] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID3", excelLabel.CapitalReturn);
		SmokeID3Data[4] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID3", excelLabel.Dividends);
		SmokeID3Data[5] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID3", excelLabel.RealizedGain);
		SmokeID3Data[6] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID3", excelLabel.OtherProceeds);
		SmokeID3Data[7] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID3", excelLabel.TotalDistributions);
		
		SmokeID4Data[0] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID4", excelLabel.InvestorDistributionID);
		SmokeID4Data[1] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID4", excelLabel.FundDistributionID);
		SmokeID4Data[2] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID4", excelLabel.Commitment_ID);
		SmokeID4Data[3] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID4", excelLabel.CapitalReturn);
		SmokeID4Data[4] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID4", excelLabel.Dividends);
		SmokeID4Data[5] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID4", excelLabel.RealizedGain);
		SmokeID4Data[6] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID4", excelLabel.OtherProceeds);
		SmokeID4Data[7] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID4", excelLabel.TotalDistributions);
		
		SmokeID5Data[0] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID5", excelLabel.InvestorDistributionID);
		SmokeID5Data[1] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID5", excelLabel.FundDistributionID);
		SmokeID5Data[2] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID5", excelLabel.Commitment_ID);
		SmokeID5Data[3] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID5", excelLabel.CapitalReturn);
		SmokeID5Data[4] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID5", excelLabel.Dividends);
		SmokeID5Data[5] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID5", excelLabel.RealizedGain);
		SmokeID5Data[6] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID5", excelLabel.OtherProceeds);
		SmokeID5Data[7] = ExcelUtils.readData(smokeFilePath,"InvestorDistribution",excelLabel.Variable_Name, "ID5", excelLabel.TotalDistributions);
		
		String[] labels = {InvestorDistributionPageFieldLabelText.ID_No.toString(),
				InvestorDistributionPageFieldLabelText.Fund_Distribution.toString(),
				InvestorDistributionPageFieldLabelText.Commitment.toString(),
				InvestorDistributionPageFieldLabelText.Capital_Return.toString(),
				InvestorDistributionPageFieldLabelText.Dividends.toString(),
				InvestorDistributionPageFieldLabelText.Realized_Gain.toString(),
				InvestorDistributionPageFieldLabelText.Other_Proceeds.toString(),
				InvestorDistributionPageFieldLabelText.Total_Distributions.toString(),
				InvestorDistributionPageFieldLabelText.Capital_Recallable.toString(),
				InvestorDistributionPageFieldLabelText.Distribution_Date.toString(),
				};
		if (bp.clickOnTab(environment, mode, TabName.InvestorDistributions)) {
			if (ccp.clickOnCreatedCapitalCall(environment, mode, SmokeID1ID)) {
				for (int i = 0;i<8;i++) {
					if (id.fieldValueVerificationOnInvestorDistribution(environment, mode, TabName.InvestorDistributions, labels[i], SmokeID1Data[i])) {
						log(LogStatus.INFO, "successfully verified "+labels[i] + " for 1st ID", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify "+labels[i]+ " for 1st ID", YesNo.Yes);
						sa.assertTrue(false, "could not verify "+labels[i]+ " for 1st ID");
					}
				}
				//verify capital recallable checked or not
				if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
					if (id.getCapitalRecallableValue(mode, 30).getAttribute("title").contains("Not Checked")) {
						log(LogStatus.ERROR, "checkbox for capital recallabel checkbox is unchecked but it should be", YesNo.Yes);
						sa.assertTrue(false, "checkbox for capital recallabel checkbox is unchecked but it should be");
					}
					else
						log(LogStatus.INFO, "capital recallable checkbox is successfully checked", YesNo.No);
				}
				else {//lighting
					try {
						if (id.getCapitalRecallableValue(mode, 30).getAttribute("class").contains("unchecked")) {
							log(LogStatus.ERROR, "checkbox for capital recallabel checkbox is unchecked but it should be", YesNo.Yes);
							sa.assertTrue(false, "checkbox for capital recallabel checkbox is unchecked but it should be");
						}
						else {
							log(LogStatus.INFO, "capital recallable checkbox is successfully checked", YesNo.No);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log(LogStatus.ERROR, "Exception on checkbox for capital recallabel checkbox", YesNo.Yes);
						sa.assertTrue(false, "Exception on checkbox for capital recallabel checkbox");
					}
				}
			}
			else {
				log(LogStatus.ERROR,"could not click on 1st investor distribution"+SmokeID1ID, YesNo.Yes);
				sa.assertTrue(false, "could not click on 1st investor distribution"+SmokeID1ID);
			}
		}
		else {
			log(LogStatus.ERROR, " could not click on investor distributions tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on investor distributions tab");
		}
		if (bp.clickOnTab(environment, mode, TabName.InvestorDistributions)) {
			if (ccp.clickOnCreatedCapitalCall(environment, mode, SmokeID2ID)) {

				for (int i = 0;i<8;i++) {
					if (id.fieldValueVerificationOnInvestorDistribution(environment, mode, TabName.InvestorDistributions, labels[i], SmokeID2Data[i])) {
						log(LogStatus.INFO, "successfully verified "+labels[i]+ " for 2nd ID", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify "+labels[i]+ " for 2nd ID", YesNo.Yes);
						sa.assertTrue(false, "could not verify "+labels[i]+ " for 2nd ID");
					}
				}
				//verify capital recallable checked or not
				if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
					if (id.getCapitalRecallableValue(mode, 30).getAttribute("title").contains("Not Checked")) {
						log(LogStatus.ERROR, "checkbox for capital recallabel checkbox is unchecked but it should be", YesNo.Yes);
						sa.assertTrue(false, "checkbox for capital recallabel checkbox is unchecked but it should be");
					}
					else
						log(LogStatus.INFO, "capital recallable checkbox is successfully checked", YesNo.No);
				}
				else {//lighting
					if (id.getCapitalRecallableValue(mode, 30).getAttribute("class").contains("unchecked")) {
						log(LogStatus.ERROR, "checkbox for capital recallabel checkbox is unchecked but it should be", YesNo.Yes);
						sa.assertTrue(false, "checkbox for capital recallabel checkbox is unchecked but it should be");
					}
					else
						log(LogStatus.INFO, "capital recallable checkbox is successfully checked", YesNo.No);
				}
			}
			else {
				log(LogStatus.ERROR,"could not click on investor distribution"+SmokeID2ID, YesNo.Yes);
				sa.assertTrue(false, "could not click on investor distribution"+SmokeID2ID);
			}
		}
		else {
			log(LogStatus.ERROR, " could not click on investor distributions tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on investor distributions tab");
		}
		if (bp.clickOnTab(environment, mode, TabName.InvestorDistributions)) {
			if (ccp.clickOnCreatedCapitalCall(environment, mode, SmokeID3ID)) {

				for (int i = 0;i<8;i++) {
					if (id.fieldValueVerificationOnInvestorDistribution(environment, mode, TabName.InvestorDistributions, labels[i], SmokeID3Data[i])) {
						log(LogStatus.INFO, "successfully verified "+labels[i]+ " for 3rd ID", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify "+labels[i]+ " for 3rd ID", YesNo.Yes);
						sa.assertTrue(false, "could not verify "+labels[i]+ " for 3rd ID");
					}
				}
				//verify capital recallable checked or not
				if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
					if (id.getCapitalRecallableValue(mode, 30).getAttribute("title").contains("Not Checked")) {
						log(LogStatus.ERROR, "checkbox for capital recallabel checkbox is unchecked but it should be", YesNo.Yes);
						sa.assertTrue(false, "checkbox for capital recallabel checkbox is unchecked but it should be");
					}
					else
						log(LogStatus.INFO, "capital recallable checkbox is successfully checked", YesNo.No);
				}
				else {//lighting
					if (id.getCapitalRecallableValue(mode, 30).getAttribute("class").contains("unchecked")) {
						log(LogStatus.ERROR, "checkbox for capital recallabel checkbox is unchecked but it should be", YesNo.Yes);
						sa.assertTrue(false, "checkbox for capital recallabel checkbox is unchecked but it should be");
					}
					else
						log(LogStatus.INFO, "capital recallable checkbox is successfully checked", YesNo.No);
				}
			}
			else {
				log(LogStatus.ERROR,"could not click on investor distribution"+SmokeID3ID, YesNo.Yes);
				sa.assertTrue(false, "could not click on investor distribution"+SmokeID3ID);
			}
		}
		else {
			log(LogStatus.ERROR, " could not click on investor distributions tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on investor distributions tab");
		}
		if (bp.clickOnTab(environment, mode, TabName.InvestorDistributions)) {
			if (ccp.clickOnCreatedCapitalCall(environment, mode, SmokeID4ID)) {

				for (int i = 0;i<8;i++) {
					if (id.fieldValueVerificationOnInvestorDistribution(environment, mode, TabName.InvestorDistributions, labels[i], SmokeID4Data[i])) {
						log(LogStatus.INFO, "successfully verified "+labels[i]+ " for 4th ID", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify "+labels[i]+ " for 4th ID", YesNo.Yes);
						sa.assertTrue(false, "could not verify "+labels[i]+ " for 4th ID");
					}
				}
				//verify capital recallable checked or not
				if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
					if (id.getCapitalRecallableValue(mode, 30).getAttribute("title").contains("Not Checked")) {
						log(LogStatus.ERROR, "checkbox for capital recallabel checkbox is unchecked but it should be", YesNo.Yes);
						sa.assertTrue(false, "checkbox for capital recallabel checkbox is unchecked but it should be");
					}
					else
						log(LogStatus.INFO, "capital recallable checkbox is successfully checked", YesNo.No);
				}
				else {//lighting
					if (id.getCapitalRecallableValue(mode, 30).getAttribute("class").contains("unchecked")) {
						log(LogStatus.ERROR, "checkbox for capital recallabel checkbox is unchecked but it should be", YesNo.Yes);
						sa.assertTrue(false, "checkbox for capital recallabel checkbox is unchecked but it should be");
					}
					else
						log(LogStatus.INFO, "capital recallable checkbox is successfully checked", YesNo.No);
				}
			}
			else {
				log(LogStatus.ERROR,"could not click on investor distribution"+SmokeID4ID, YesNo.Yes);
				sa.assertTrue(false, "could not click on investor distribution"+SmokeID4ID);
			}
		}
		else {
			log(LogStatus.ERROR, " could not click on investor distributions tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on investor distributions tab");
		}
		if (bp.clickOnTab(environment, mode, TabName.InvestorDistributions)) {
			if (ccp.clickOnCreatedCapitalCall(environment, mode, SmokeID5ID)) {

				for (int i = 0;i<8;i++) {
					if (id.fieldValueVerificationOnInvestorDistribution(environment, mode, TabName.InvestorDistributions, labels[i], SmokeID5Data[i])) {
						log(LogStatus.INFO, "successfully verified "+labels[i]+ " for 5th ID", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not verify "+labels[i]+ " for 5th ID", YesNo.Yes);
						sa.assertTrue(false, "could not verify "+labels[i]+ " for 5th ID");
					}
				}
				//verify capital recallable checked or not
				if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
					if (id.getCapitalRecallableValue(mode, 30).getAttribute("title").contains("Not Checked")) {
						log(LogStatus.ERROR, "checkbox for capital recallabel checkbox is unchecked but it should be", YesNo.Yes);
						sa.assertTrue(false, "checkbox for capital recallabel checkbox is unchecked but it should be");
					}
					else
						log(LogStatus.INFO, "capital recallable checkbox is successfully checked", YesNo.No);
				}
				else {//lighting
					if (id.getCapitalRecallableValue(mode, 30).getAttribute("class").contains("unchecked")) {
						log(LogStatus.ERROR, "checkbox for capital recallabel checkbox is unchecked but it should be", YesNo.Yes);
						sa.assertTrue(false, "checkbox for capital recallabel checkbox is unchecked but it should be");
					}
					else
						log(LogStatus.INFO, "capital recallable checkbox is successfully checked", YesNo.No);
				}
			}
			else {
				log(LogStatus.ERROR,"could not click on investor distribution"+SmokeID5ID, YesNo.Yes);
				sa.assertTrue(false, "could not click on investor distribution"+SmokeID5ID);
			}
		}
		else {
			log(LogStatus.ERROR, " could not click on investor distributions tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on investor distributions tab");
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc057_VerifyCommitmentPageAfterDistributions(String environment, String mode) {
	
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String labelName=CommitmentPageFieldLabelText.Commitment_ID+","+CommitmentPageFieldLabelText.Partner_Type+","+
						CommitmentPageFieldLabelText.Limited_Partner+","+CommitmentPageFieldLabelText.Final_Commitment_Date+","+
						CommitmentPageFieldLabelText.Partnership+","
							+CommitmentPageFieldLabelText.Tax_Forms+","+CommitmentPageFieldLabelText.Commitment_Amount+","+
							CommitmentPageFieldLabelText.Total_Amount_Called+","+CommitmentPageFieldLabelText.Total_Amount_Received+","+
							CommitmentPageFieldLabelText.Total_Uncalled_Amount+","+CommitmentPageFieldLabelText.Total_Commitment_Due+","+
							CommitmentPageFieldLabelText.Commitment_Called+","+CommitmentPageFieldLabelText.Called_Due+","+
							CommitmentPageFieldLabelText.Total_Distributions+","+CommitmentPageFieldLabelText.Capital_Returned_Recallable+","+
							CommitmentPageFieldLabelText.Capital_Returned_NonRecallable;

		String labelValue=SmokeCOMM8_ID+","+SmokeCOMM8_partnerType+","+Smoke_LP1+","+SmokeCOMM8_FinalCommitmentDate+","+Smoke_P1+","+SmokeCOMM8_TaxForms
				+","+SmokeCOMM8_CommitmentAmount+","+SmokeCOMM8_TotalAmountCalled+","+SmokeCOMM8_TotalAmountReceived+","+SmokeCOMM8_TotalUncalledAmount
				+","+SmokeCOMM8_TotalCommitmentDue+","+SmokeCOMM8_CommitmentCalled+","+SmokeCOMM8_CalledDue+","+SmokeCOMM8_TotalDist+","+SmokeCOMM8_CapitalReturnedRecallable+
				","+SmokeCOMM8_CapitalReturnedNonRecallable;
		String[] labels = labelName.split(",");
		String[] values = labelValue.split(",");
		
		if (bp.clickOnTab(environment, mode, TabName.CommitmentsTab)) {
			if (comm.clickOnCreatedCommitmentId(environment, mode, SmokeCOMM8_ID)) {
				for (int j = 0; j < values.length; j++) {
					System.err.println(labels[j] +" : "+values[j]);
					if(comm.FieldValueVerificationOnCommitmentPage(environment, mode, labels[j], values[j])) {
						log(LogStatus.PASS, labels[j]+" : With value verified for : "+values[j], YesNo.No);
					}else {
						log(LogStatus.ERROR, labels[j]+" : With value not verified for : "+values[j], YesNo.Yes);
						sa.assertTrue(false, labels[j]+" : With value not verified for : "+values[j]);
					}	
				}
			}
			else {
				log(LogStatus.SKIP, SmokeCOMM8_ID+" cannot click on commitment", YesNo.Yes);
				sa.assertTrue(false, SmokeCOMM8_ID+" cannot click on commitment");
			}
		}
		else {
			log(LogStatus.SKIP, "cannot click on commitments tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on commitments tab");
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc058_1_verifyCancelNextPreviousOnSendDistributionNotice(String environment, String mode) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		FundDistributionsPageBusinessLayer fdd = new FundDistributionsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		HashMap<String, String> ContactAndAccountName = new HashMap<>();
		ContactAndAccountName.put(SmokeC1_FName + " " + SmokeC1_LName, SmokeINS1);
		for(int j=0; j<=7; j++) {
			if(fund.clickOnTab(environment, mode, TabName.FundDistributions)) {
				if(fd.clickOnCreatedDrawdown(environment, mode, SmokeFD1ID)) {
					appLog.info("Click on dRAW dOWN : "+SmokeFD1ID);
					
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						if (bp.clickOnShowMoreDropdownOnly(environment, mode, PageName.FundDistribution)) {
							log(LogStatus.PASS, "successfully clicked on dropdown",YesNo.No);
							WebElement ele=bp.actionDropdownElement(environment,  PageName.FundDistribution, ShowMoreActionDropDownList.Send_Distribution_Notices, 30);
							if (ele!=null) {
								log(LogStatus.PASS, "successfully found send capital call notices button", YesNo.No);
								if (click(driver, bp.actionDropdownElement(environment,  PageName.FundDistribution,  ShowMoreActionDropDownList.Send_Distribution_Notices, 30), ShowMoreActionDropDownList.Send_Distribution_Notices.toString(), action.BOOLEAN)) {
									log(LogStatus.PASS, "successfully click on send button", YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "cannot click on send button", YesNo.Yes);
									sa.assertTrue(false, "could not click on send button");
								}
							}
							else {
								log(LogStatus.ERROR, "could not find send capital call notices button", YesNo.Yes);
								sa.assertTrue(false, "could not find send capital call notices button");
							}
						}
						else {
							log(LogStatus.ERROR, "could not click on show more dropdown", YesNo.Yes);
							sa.assertTrue(false, "could not click on show more dropdown");
						}
					}
					else {
						if (fdd.getSendDistributionNotices_Classic(30)!=null) {
							log(LogStatus.PASS, "send capital call notices button is successfully present", YesNo.No);
							if (click(driver, fdd.getSendDistributionNotices_Classic(30), "send capital call notices button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "successfully click on send capital call button", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not click on send button", YesNo.Yes);
								sa.assertTrue(false, "could not click on send button");
							}
						}
						else {
							log(LogStatus.FAIL, "send capital call notices button is not present", YesNo.Yes);
							sa.assertTrue(false, "send capital call notices button is not present");
						}
					}
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 10, fd.getEmailingFrame_Lighting(30));
					}
				//	List<String> result =market.selectProspectsContactAndVerifyReviewProspectListInEmailProspect(PageName.Send_Distribution_Notices,ContactAndAccountName, searchContactInEmailProspectGrid.No);
					if(j==0) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.Send_Distribution_Notices,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							for(int i=0; i<=3; i++) {
								if(i==0) {
									if(click(driver, market.getStep1NextBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"Top Next button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top next button", YesNo.No);
									}else {
										log(LogStatus.ERROR,"Not able to click on top next button", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top next button");
									}
								}
								if(i==1) {
									if(click(driver, market.getStep2PreviousBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"Top previous button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top previous button", YesNo.No);
									}else {
										log(LogStatus.ERROR,"Not able to click on top previous button", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top previous button");
									}
								}
								if(i==2) {
									if(click(driver, market.getStep1NextBtn(PageName.Send_Distribution_Notices, TopOrBottom.BOTTOM,10),"bottom Next button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on BOTTOM next button", YesNo.No);
									}else {
										log(LogStatus.ERROR,"Not able to click on BOTTOM next button", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on BOTTOM next button");
									}
								}

								if(i==3) {
									if(click(driver, market.getStep2PreviousBtn(PageName.Send_Distribution_Notices, TopOrBottom.BOTTOM,10),"bottom previous button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on BOTTOM previous button", YesNo.No);
									}else {
										log(LogStatus.ERROR,"Not able to click on BOTTOM previous button", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on BOTTOM previous button");
									}
								}
								if(i==0 && i==2) {
									ThreadSleep(2000);
									WebElement ele = fd.getStep2TextEmailing(30);
									if (ele!=null) {
										String msg = ele.getText().trim();
										if (HomePageErrorMessage.Step2_SelectAnEmailTemplate.equals(msg)) {
											log(LogStatus.PASS, "Step2 Page Verified : "+msg, YesNo.No);
										} else {
											sa.assertTrue(false, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate);
											log(LogStatus.FAIL, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate, YesNo.Yes);
										}
									} else {
										sa.assertTrue(false, "Step2 Page Element is null");
										log(LogStatus.FAIL, "Step2 Page Element is null", YesNo.Yes);

									}
								}

							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button");
						}
					}
					if(j==1) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.Send_Distribution_Notices,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1CancelBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"Top cancel button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on top cancel button step 1", YesNo.No);
							}else {
								log(LogStatus.ERROR,"Not able to click on top cancel button step 1", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on top cancel button step 1");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot Top Cancel bottom on step 1", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot Top Cancel bottom on step 1");
						}
					}
					if(j==2) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.Send_Distribution_Notices,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1CancelBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"bottom cancel button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on bottom cancel button step 1", YesNo.No);
							}else {
								log(LogStatus.ERROR,"Not able to click on bottom cancel button step 1", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on bottom cancel button step 1");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot bottom Cancel bottom on step 1", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot bottom Cancel bottom on step 1");
						}
					}
					if(j==3) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.Send_Distribution_Notices,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1NextBtn(PageName.Send_Distribution_Notices, TopOrBottom.BOTTOM,10),"bottom Next button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on BOTTOM next button", YesNo.No);
								ThreadSleep(2000);
								if(click(driver, market.getStep2CancelBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"top cancel button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "clicked on top cancel button step 2", YesNo.No);
								}else {
									log(LogStatus.ERROR,"Not able to click on top cancel button step 2", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on top cancel button step 2");
								}


							}else {
								log(LogStatus.ERROR,"Not able to click on BOTTOM next button so cannot check Top Cancel button on Step2", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on BOTTOM next button so cannot check Top Cancel button on Step2");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check Top Cancel button on Step2", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check Top Cancel button on Step2");
						}
					}
					if(j==4) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.Send_Distribution_Notices,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1NextBtn(PageName.Send_Distribution_Notices, TopOrBottom.BOTTOM,10),"bottom Next button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on BOTTOM next button", YesNo.No);
								ThreadSleep(2000);
								if(click(driver, market.getStep2CancelBtn(PageName.Send_Distribution_Notices, TopOrBottom.BOTTOM,10),"bottom cancel button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "clicked on bottom cancel button step 2", YesNo.No);
								}else {
									log(LogStatus.ERROR,"Not able to click on bottom cancel button step 2", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on bottom cancel button step 2");
								}
							}else {
								log(LogStatus.ERROR,"Not able to click on BOTTOM next button so cannot check bottom Cancel button on Step2", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on BOTTOM next button so cannot check bottom Cancel button on Step2");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check bottom Cancel button on Step2", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check bottom Cancel button on Step2");
						}
					}
					if(j==5) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.Send_Distribution_Notices,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1NextBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"Top Next button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on top next button step 1", YesNo.No);
								ThreadSleep(2000);
								for(int i=0; i<=3; i++) {
									if(i==0) {
										if(market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
											log(LogStatus.ERROR, "Select Capital Call Notice template", YesNo.No);
											if(click(driver, market.getStep2NextBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);
											}else {
												log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on top next button step 2");
											}
										}else {
											log(LogStatus.ERROR,"Not able to click select email template from step 2 so cannot check previous botton of step 3", YesNo.Yes);
											sa.assertTrue(false,"Not able to click select email template from step 2 so cannot check previous botton of step 3");
											break;
										}

									}
									if(i==1) {
										if(click(driver, market.getStep3PreviousBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"Top previous button step 3", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on top previous button step 3", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on top previous button step 3", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on top previous button step 3");
										}
									}
									if(i==2) {
										if(click(driver, market.getStep2NextBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);

										}else {
											log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on top next button step 2");
										}
									}

									if(i==3) {
										if(click(driver, market.getStep3PreviousBtn(PageName.Send_Distribution_Notices, TopOrBottom.BOTTOM,10),"bottom previous button step 3", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on bottom previous button step 3", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on bottom previous button step 3", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on bottom previous button step 3");
										}
									}
									if(i==0 && i==2) {
										ThreadSleep(2000);
										WebElement ele = fd.getStep2TextEmailing(30);
										if (ele!=null) {
											String msg = ele.getText().trim();
											if (HomePageErrorMessage.step3_ReviewAndConfirm.equals(msg)) {
												log(LogStatus.PASS, "Step3 Page Verified : "+msg, YesNo.No);
											} else {
												sa.assertTrue(false, "Step3 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step3_ReviewAndConfirm);
												log(LogStatus.FAIL, "Step3 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step3_ReviewAndConfirm, YesNo.Yes);
											}
										} else {
											sa.assertTrue(false, "Step3 Page Element is null");
											log(LogStatus.FAIL, "Step3 Page Element is null", YesNo.Yes);

										}
									}

								}
							}else {
								log(LogStatus.ERROR,"Not able to click on top next button step 1 so cannot check previous botton of step3", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on top next button step 1 so cannot check previous botton of step3");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button");
						}
					}
					if(j==6) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.Send_Distribution_Notices,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1NextBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"Top Next button step 1", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on top next button step 1", YesNo.No);
								if(market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
									log(LogStatus.ERROR, "Select Capital Call Notice template", YesNo.No);
									if(click(driver, market.getStep2NextBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);
										ThreadSleep(2000);
										if(click(driver, market.getStep3CancelBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"top cancel button step 3", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on top cancel button step 3", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on top cancel button step 3", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on top cancel button step 3");
										}
									}else {
										log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top next button step 2");
									}
								}else {
									log(LogStatus.ERROR,"Not able to click select email template from step 2 so cannot check previous botton of step 3", YesNo.Yes);
									sa.assertTrue(false,"Not able to click select email template from step 2 so cannot check previous botton of step 3");
								}
							}else {
								log(LogStatus.ERROR,"Not able to click on top next button step 1 so cannot check previous botton of step3", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on top next button step 1 so cannot check previous botton of step3");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check step 3 cancel button", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check step 3 cancel button");
						}
					}
					if(j==7) {
						if(fd.ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName.Send_Distribution_Notices,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, 30)) {
							log(LogStatus.ERROR, "select contact "+SmokeC1_FName+" "+SmokeC1_LName, YesNo.No);
							if(click(driver, market.getStep1NextBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"Top Next button step 1", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on top next button step 1", YesNo.No);
								if(market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
									log(LogStatus.ERROR, "Select Capital Call Notice template", YesNo.No);
									if(click(driver, market.getStep2NextBtn(PageName.Send_Distribution_Notices, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);
										ThreadSleep(2000);
										if(click(driver, market.getStep3CancelBtn(PageName.Send_Distribution_Notices, TopOrBottom.BOTTOM,10),"buttom cancel button step 3", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on buttom cancel button step 3", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on buttom cancel button step 3", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on buttom cancel button step 3");
										}
									}else {
										log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top next button step 2");
									}
								}else {
									log(LogStatus.ERROR,"Not able to click select email template from step 2 so cannot check previous botton of step 3", YesNo.Yes);
									sa.assertTrue(false,"Not able to click select email template from step 2 so cannot check previous botton of step 3");
								}

							}else {
								log(LogStatus.ERROR,"Not able to click on top next button step 1 so cannot check previous botton of step3", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on top next button step 1 so cannot check previous botton of step3");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check step 3 cancel button", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check step 3 cancel button");
						}
					}



				}else {
					log(LogStatus.ERROR, "Not able to click on Draw Down "+SmokeFD1ID+" so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Draw Down "+SmokeFD1ID+" so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
				}
			}else {
			log(LogStatus.ERROR, "Not able to click on fund Draw Down tab so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);
			sa.assertTrue(false, "Not able to click on fund Draw Down tab so cannot send email to contact "+SmokeC1_FName+" "+SmokeC1_LName);
		}
			switchToDefaultContent(driver);
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc058_2_VerifySendDistributionNoticesFromFundDistribution(String environment, String mode) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		FundDistributionsPageBusinessLayer fdi = new FundDistributionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		String keywords[] = ExcelUtils.readData("FilePath", excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.KeyWord_For_Search).split(",");
		String selectedGrid[] = keywords[0].split("<break>");
		String columnsOnGrid[] = keywords[2].split("<break>");
		SoftAssert saa = new SoftAssert();
		int n = 2;
		WebElement ele;
		String msg;
		if (bp.clickOnTab(environment, mode, TabName.FundDistributions)) {
			if (fd.clickOnCreatedDrawdown(environment, mode, SmokeFD1ID)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (bp.clickOnShowMoreDropdownOnly(environment, mode, PageName.FundDistribution)) {
						log(LogStatus.PASS, "successfully clicked on dropdown",YesNo.No);
						ele=bp.actionDropdownElement(environment, PageName.FundDistribution, ShowMoreActionDropDownList.Send_Distribution_Notices, 30);
						if (ele!=null) {
							log(LogStatus.PASS, "successfully found send distribution notices button", YesNo.No);
							if (click(driver, bp.actionDropdownElement(environment, PageName.FundDistribution,  ShowMoreActionDropDownList.Send_Distribution_Notices, 30), ShowMoreActionDropDownList.Send_Distribution_Notices_Button.toString(), action.BOOLEAN)) {
								log(LogStatus.PASS, "successfully click on send button", YesNo.No);
							}
							else {
								log(LogStatus.FAIL, "cannot click on send button", YesNo.Yes);
								sa.assertTrue(false, "could not click on send button");
							}
						}
						else {
							log(LogStatus.ERROR, "could not find send capital call notices button", YesNo.Yes);
							sa.assertTrue(false, "could not find send capital call notices button");
						}
					}
					else {
						log(LogStatus.PASS, "could not click on show more dropdown", YesNo.Yes);
						sa.assertTrue(false, "could not click on show more dropdown");
					}
				}
				else {
					if (fdi.getSendDistributionNotices_Classic(30)!=null) {
						log(LogStatus.PASS, "send Distribution notices button is successfully present", YesNo.No);
						if (click(driver, fdi.getSendDistributionNotices_Classic(30), "send capital call notices button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "successfully click on send Distribution button", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not click on send button", YesNo.Yes);
							sa.assertTrue(false, "could not click on send button");
						}
					}
					else {
						log(LogStatus.FAIL, "send capital call notices button is not present", YesNo.Yes);
						sa.assertTrue(false, "send capital call notices button is not present");
					}
				}
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(30));
				}
				//verify mailing grid
				SmokeID1ID = ExcelUtils.readData(smokeFilePath, "InvestorDistribution", excelLabel.Commitment_ID, SmokeCOMM8_ID, excelLabel.InvestorDistributionID);
				saa = fdi.verifyEmailingContentGrid(driver, PageName.FundDistribution,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, SmokeC1_EmailID, SmokeID1ID, SmokeCOMM8_ID, Smoke_LP1, Smoke_P1, false);
				sa.combineAssertions(saa);
				SmokeID2ID = ExcelUtils.readData(smokeFilePath, "InvestorDistribution", excelLabel.Commitment_ID, SmokeCOMM2_ID, excelLabel.InvestorDistributionID);
				saa = fdi.verifyEmailingContentGrid(driver, PageName.FundDistribution,SmokeC2_FName+" "+SmokeC2_LName, SmokeINS1, SmokeC2_EmailID, SmokeID2ID, SmokeCOMM2_ID, Smoke_LP2, Smoke_P1, false);
				sa.combineAssertions(saa);
				List<WebElement> li = fd.listOfContactNames(30);
				boolean flag = false;
				int rowCount = li.size();
				for (int i =0;i<li.size();i++) {
					if (li.get(i).getText().trim().contains(SmokeC4_FName + " "+ SmokeC4_LName)) {
						flag = true;
						break;
					}
				}
				if (flag) {
					log(LogStatus.ERROR,SmokeC4_FName + " "+ SmokeC4_LName+ " is found in emailing grid but it should not be",YesNo.Yes);
					sa.assertTrue(false,SmokeC4_FName + " "+ SmokeC4_LName+ " is found in emailing grid but it should not be" );
				}
				else {
					log(LogStatus.INFO, "could not find "+SmokeC4_FName + " "+ SmokeC4_LName+ " as expected", YesNo.No);
				}
				//verify total number of records
				if (fd.getRecordsCount(30).getText().trim().contains("Records: "+n)) {
					log(LogStatus.INFO,"successfully verified number of records",YesNo.No);
				}
				else {
					log(LogStatus.ERROR,"could not verify correct number of records", YesNo.Yes);
					sa.assertTrue(false, "could not verify correct number of records");
				}
				//verify columns to display ui
				flag = false;
				if (click(driver, fd.getWrenchIcon(30), "wrench icon", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					ele = fd.getSelectedField(environment, mode, 10);
					if (ele!=null) {
						msg = ele.getText().trim();
						log(LogStatus.INFO," "+msg,YesNo.No);
						log(LogStatus.INFO, "Element Found Selected Field ", YesNo.Yes);
						if (msg.contains("Selected Fields (7/20)")) {
							log(LogStatus.INFO, "Message verified "+msg, YesNo.Yes);

						} else {
							log(LogStatus.ERROR, "Message Not verified : Actual : "+msg+" \t Expected : "+"Selected Fields (7/20)", YesNo.Yes);
							sa.assertTrue(false, "Message Not verified : Actual : "+msg+" \t Expected : "+"Selected Fields (7/20)");
						}
					} else {
						log(LogStatus.ERROR, "Selected Fields (7/20) not found ", YesNo.Yes);
						sa.assertTrue(false, "Selected Fields (7/20) not found ");
					}
					for (int i = 0;i<selectedGrid.length;i++) {
						if (fd.verifyColumnInColumnsToDisplayGrid(PageName.FundDistribution, selectedGrid[i])) {
							log(LogStatus.INFO, "successfully found "+selectedGrid[i], YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "not found "+selectedGrid[i], YesNo.Yes);
							sa.assertTrue(false, "not found "+selectedGrid[i]);
						}
					}
					if (fd.moveFromAvailableToSelectedColumnsToDisplay(PageName.FundDistribution, keywords[1])) {
						log(LogStatus.INFO, "successfully moved last name column to selected grid", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not move last name column to selected grid", YesNo.Yes);
						sa.assertTrue(false, "could not move last name column to selected grid");
					}
					if (click(driver, fd.getApplyColumnsToDisplay2(30), "Apply button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Apply Button", YesNo.No);	
						ThreadSleep(5000);
						flag = true;
						//verify presence of all columns
						for (int i =0;i<columnsOnGrid.length;i++) {
							if (fd.getEmailingGridColumn(columnsOnGrid[i], 30)!=null) {
								log(LogStatus.INFO, "successfully found "+columnsOnGrid[i]+ " column", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not find "+columnsOnGrid[i]+" column", YesNo.Yes);
								sa.assertTrue(false, "could not find "+columnsOnGrid[i]+" column");
							}
						}
						
						
						
						// Azhar Start
						String xpath;
						String parentId;
						String[] linkClick = {SmokeC1_FName+" "+SmokeC1_LName,SmokeINS1};
						for (int j = 0; j < linkClick.length; j++) {
							
							xpath = "//span/a[text()='"+linkClick[j]+"']";
							ele = FindElement(driver, xpath, linkClick[j], action.BOOLEAN, 10);
							
							if (ele!=null) {
								
								if (click(driver, ele, linkClick[j], action.BOOLEAN)) {
									
								parentId = 	switchOnWindow(driver);
								
								if (parentId!=null) {
									ThreadSleep(2000);
									if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
										xpath = "//div/*[contains(text(),'"+linkClick[j]+"')]";
										}else{
											xpath = "//h2[contains(text(),'"+linkClick[j]+"')]";	
										}
									
									ele = FindElement(driver, xpath, "on new window : "+linkClick[j], action.BOOLEAN, 10);
									
									if (ele!=null) {
										appLog.info("Landing Page Verified : "+linkClick[j]);
									} else {
										appLog.error("Landing Page Not Verified : "+linkClick[j]);
										sa.assertTrue(false, "Landing Page Not Verified : "+linkClick[j]);
									}
									
									driver.close();
									driver.switchTo().window(parentId);
									switchToDefaultContent(driver);
									
									if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
										switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(30));
									}
									
								} else {
									appLog.error("Not New Window for "+linkClick[j]);
									sa.assertTrue(false, "Not New Window for "+linkClick[j]);
								}
								
							} else {
									appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
									sa.assertTrue(false, "Not able to click on "+linkClick[j]+" so cannot verify landing page");
								}
							} else {
								appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
								sa.assertTrue(false, "Not able to click on "+linkClick[j]+" so cannot verify landing page");
							}
							
							
						}
						
					// Azhar End
						
						n=1;
						//checking searchbox functionality
						if (sendKeys(driver, fd.getSearchTextboxEmailingGrid(30),SmokeC1_LName,"search box", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(2000);
							if (click(driver,fd.getSearchIconEmailingGrid(30), "search icon", action.BOOLEAN)) {
								((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo( 0 ,0)",
										fd.getScrollBoxStep1FundDrawdownEmailingGrid(30));
								SmokeID1ID = ExcelUtils.readData(smokeFilePath, "InvestorDistribution", excelLabel.Commitment_ID, SmokeCOMM8_ID, excelLabel.InvestorDistributionID);
								saa = fd.verifyEmailingContentGrid(driver, PageName.FundDrawdown,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, SmokeC1_EmailID, SmokeC1_FName+" "+SmokeC1_LName, SmokeCOMM8_ID, Smoke_LP1, Smoke_P1, false);
								sa.combineAssertions(saa);

								if (fd.getRecordsCount(30).getText().trim().contains("Records: "+n)) {
									log(LogStatus.INFO,"successfully verified number of records : "+n,YesNo.No);
								}
								else {
									log(LogStatus.ERROR,"could not verify correct number of records : "+n, YesNo.Yes);
									sa.assertTrue(false, "could not verify correct number of records : "+n);
								}
							}
							else {
								log(LogStatus.ERROR, "search icon is not clickable on emailing grid", YesNo.Yes);
								sa.assertTrue(false, "search icon is not clickable on emailing grid");
							}
						}
						else {
							log(LogStatus.ERROR, "search textbox is not visible", YesNo.Yes);
							sa.assertTrue(false, "search textbox is not visible");
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Apply Button", YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on Apply Button");
					}
				}
				if (flag) {
					if (click(driver,fd.getStep1HeaderCheckbox(30), "header checkbox", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						if (click(driver, fd.getStep1NextButtonEmailing(30), "next button", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(2000);
							if (fd.getStep2TextEmailing(30)!=null) {
								log(LogStatus.INFO, "Step 2 Msg Verified", YesNo.Yes);
							}else{
								sa.assertTrue(false, "Step 2 Msg Not Verified");
								log(LogStatus.ERROR, "Step 2 Msg Not Verified", YesNo.Yes);	
							}
							String expectedResult = "All,Capital Call Notice,Investor Distribution Notice,"+EmailTemplate1_FolderName;
							List<WebElement> lst = allOptionsInDropDrop(driver,
									fd.getFundDrawdownFolderDropDownList(20), "folder drop down list");
							if (compareMultipleList(driver, expectedResult, lst).isEmpty()) {
								log(LogStatus.INFO, "Folder Drop Down list All options is verified", YesNo.No);
							} else {
								sa.assertTrue(false, "Folder Drop Down list All options is not verified");
								log(LogStatus.ERROR, "Folder Drop Down list All options is not verified", YesNo.Yes);
							}
							//select capital calls folder
							String template = "Investor Distribution";
							if (selectVisibleTextFromDropDown(driver, fd.getFundDrawdownFolderDropDownList(20), "folder dropdown", expectedResult.split(",")[2])) {
								log(LogStatus.INFO, " Able to Select "+expectedResult.split(",")[2], YesNo.No);	
							
								if (fd.clickOnEmailTemplatePreviewLink(template)) {
									log(LogStatus.INFO, "successfully verified email preview link for "+template, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "could not verify preview link "+template, YesNo.Yes);
									sa.assertTrue(false, "could not verify preview link "+template);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Select "+expectedResult.split(",")[2], YesNo.Yes);
								sa.assertTrue(false, "Not Able to Select "+expectedResult.split(",")[2]);
							}
							//after clicking preview link page will come back to frame
							switchToDefaultContent(driver);
							if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(30));
							}
							if(fd.selectEmailTemplateFromFundDrawdown(expectedResult.split(",")[2], template)) {
								log(LogStatus.INFO, "successfully selected template : "+expectedResult.split(",")[2], YesNo.No);
								if (click(driver, fd.getStep2NextButtonEmailing(30), "step 2 next button", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on step 2 next button", YesNo.No);
									ThreadSleep(2000);
									ele = fd.getFundDrawdownEmailingSelectedRecipientErrorMsg(20);
									if (ele != null) {
										String aa = ele.getText().trim();
										if (aa.contains(MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1"))) {
											log(LogStatus.INFO,MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1")+ " error message is verified",YesNo.No);
										} else {
											sa.assertTrue(false,"error message is not verified Expected: "+ MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1")+ " \t Actual : " + aa);
											log(LogStatus.ERROR,"error message is not verified Expected: "+ MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1")+ " \t Actual : " + aa,YesNo.Yes);
										}
									} else {
										sa.assertTrue(false,	MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1") + " error message is not visible");
										log(LogStatus.ERROR,	MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1") + " error message is not visible",YesNo.Yes);
									}
									List<WebElement> labellist = fd.getEmailProcessingOptionsLableTextList();
									List<WebElement> checkBoxList = fd.getEmailProcessingOptionsCheckBoxList();
									String[] expctRsult = { "BCC me on one message", "Use my signature","Store an Activity for Each Message" };
								
										for (int j = 0; j < labellist.size(); j++) {
											if (labellist.get(j).getText().trim().contains(expctRsult[j])) {
												String checkBoxAttr = checkBoxList.get(j).getAttribute("checked");
												System.err.println(j+"  checked:   "+ checkBoxAttr);
												if (j == 2) {
													if (("true").equalsIgnoreCase(checkBoxAttr)) {
														log(LogStatus.INFO,expctRsult[j] + "check box is selected",YesNo.No);
													} else {
														sa.assertTrue(false,expctRsult[j] + "check box is not selected");
														log(LogStatus.ERROR,expctRsult[j] + "check box is not selected",YesNo.Yes);
													}
												} else {
													
													if (checkBoxAttr==null) {
														log(LogStatus.INFO,expctRsult[j] + "check box is not selected",YesNo.No);
													} else {
														sa.assertTrue(false,expctRsult[j] + "check box is selected");
														log(LogStatus.ERROR,expctRsult[j] + "check box is selected",YesNo.Yes);
													}
												}
												
											}else{
												sa.assertTrue(false, expctRsult[j] + "label is not verified");
												log(LogStatus.ERROR, expctRsult[j] + "label is not verified",YesNo.Yes);
											}
										}
									String xpath = "(//h3[text()='PROCESSING OPTIONS']/following-sibling::table)[1]//td[text()='Use my signature']/following-sibling::td/label/span";
									 ele = FindElement(driver, xpath, "Use My Signature", action.SCROLLANDBOOLEAN, 10);
									if (click(driver, ele, "Use My Signature ", action.BOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Use My Signature CheckBox",YesNo.No);	
									} else {
										sa.assertTrue(false, "Not ABle to Click on Use My Signature CheckBox");
										log(LogStatus.ERROR, "Not ABle to Click on Use My Signature CheckBox",YesNo.Yes);
									}
									
									if(click(driver,fd.getfundDrawdownEmailingSendBtn(TopOrBottom.BOTTOM, 30), "send button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "clicked on send button", YesNo.No);
										ele=fd.getFundDrawdownSendEmailCongratulationsErrorMsg(30);
										String errorMsg=FundDrawdownPageErrorMessage.congratulationErrorMsg+" "+FundDrawdownPageErrorMessage.generatedEmailedRecipientErrorMsg("1", "Email");
										if(ele!=null) {
											String aa1 =ele.getText().trim();
											if(aa1.contains(FundDrawdownPageErrorMessage.congratulationErrorMsg) && aa1.contains(FundDrawdownPageErrorMessage.generatedEmailedRecipientErrorMsg("1", "Email"))) {
												log(LogStatus.INFO, "Congratulation Error Message is verified", YesNo.No);
											}else {
												log(LogStatus.ERROR, "Congratulation Error Message is not verified. Expected: "+errorMsg+"\t Actual: "+aa1, YesNo.Yes);
											}
										}else {
											log(LogStatus.ERROR, "Congratulations Error Message is not visible so cannot verify it. Error Msg: "+errorMsg, YesNo.Yes);
										}
										if (click(driver, fd.getFinishedButtonEmailing(30), "finished button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "finished button clicked", YesNo.No);
											ThreadSleep(2000);
										}
										else {
											log(LogStatus.ERROR, "finished button could not be clicked", YesNo.Yes);
											sa.assertTrue(false, "finished button could not be clicked");
										}
									}else {
										log(LogStatus.ERROR, "Not able to click on send button so cannot send email to contact", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on send button so cannot send email to contact");
									}
								}else {
									log(LogStatus.ERROR, "step 2 next button is not clickable", YesNo.Yes);
									sa.assertTrue(false, "step 2 next button is not clickable");
								}	
							}
							else {
								log(LogStatus.ERROR, "could not select email template : "+expectedResult.split(",")[2], YesNo.Yes);
								sa.assertTrue(false, "could not select email template : "+expectedResult.split(",")[2]);
							}
						}
						else {
							log(LogStatus.ERROR, "step 1 next button is not clickable", YesNo.Yes);
							sa.assertTrue(false, "step 1 next button is not clickable");
						}
					}
					else {
						log(LogStatus.ERROR, "header checkbox is not clickable", YesNo.Yes);
						sa.assertTrue(false, "header checkbox is not clickable");
					}
				}else{
					log(LogStatus.ERROR, "Flag is False so cannot Continue", YesNo.Yes);
					sa.assertTrue(false, "Flag is False so cannot Continue");
				}
				switchToDefaultContent(driver);
			}
			else {
				log(LogStatus.ERROR,"could not click on created drawdown", YesNo.Yes);
				sa.assertTrue(false, "could not click on created drawdown");
			}
		}
		else {
			log(LogStatus.ERROR,"could not click on fund drawdowns tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on fund drawdowns tab");
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc059_verifyReceivedEmailAndCreatedActivityForFundDistribution(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		FundDistributionsPageBusinessLayer fdi = new FundDistributionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		SoftAssert tsa;
		String text = null;
		String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
		WebElement ele;
		EmailLib email = new EmailLib();
		String EmailTemplate1_Subject ="Investor Distribution Notice ID-";
		String commitSub = "of Commitment "+SmokeCOMM1_ID;
		try {
			text = email.getEMailContent(gmailUserName, gmailPassword, crmUser1EmailID, SmokeC1_EmailID,EmailTemplate1_Subject);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			sa.assertTrue(false, EmailTemplate1_Subject +" Email Template is not verified from email : " + gmailUserName);
			log(LogStatus.FAIL, EmailTemplate1_Subject+ " Email Template is not verified from email : " + gmailUserName, YesNo.No);
			e.printStackTrace();
		}
		if (text != null) {
			log(LogStatus.INFO, EmailTemplate1_Subject+" template is availble on email " + gmailUserName
					+ "Hence verified send email text", YesNo.No);	
		} else {
			sa.assertTrue(false, EmailTemplate1_Subject+" template is not availble on email " + gmailUserName+ " so cannot verify send email text");
			log(LogStatus.ERROR, EmailTemplate1_Subject+" template is not availble on email " + gmailUserName+ " so cannot verify send email text", YesNo.No);
		}

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (cp.clickOnTab(environment, mode, TabName.ContactTab)) {
			log(LogStatus.INFO, "Clicked on Contact Tab",YesNo.No);
			if (cp.clickOnCreatedContact(environment, SmokeC1_FName, SmokeC1_LName)) {
				log(LogStatus.INFO, "Clicked on Created Contact : "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.No);	

				if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Activity_History, null)) {
					log(LogStatus.INFO, "Clicked on Activity History",YesNo.No);	

					if (cp.verifyCreatedActivityHistory(environment, mode, EmailTemplate1_Subject)) {
						log(LogStatus.INFO, "Activity verified: "+EmailTemplate1_Subject,YesNo.No);	

						if (cp.verifyCreatedActivityHistory(environment, mode, commitSub)) {
							log(LogStatus.INFO, "Activity verified: "+commitSub,YesNo.No);	
						} else {
							sa.assertTrue(false, "Activity not verified: "+commitSub);
							log(LogStatus.SKIP, "Activity not verified: "+commitSub,YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Activity not verified: "+EmailTemplate1_Subject);
						log(LogStatus.SKIP, "Activity not verified: "+EmailTemplate1_Subject,YesNo.Yes);
					}

				} else {
					sa.assertTrue(false, "Not on Activity History");
					log(LogStatus.SKIP, "Not on Activity History",YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Contact not Found : "+SmokeC1_FName+" "+SmokeC1_LName);
				log(LogStatus.SKIP, "Contact not Found : "+SmokeC1_FName+" "+SmokeC1_LName,YesNo.Yes);	
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab",YesNo.Yes);	
		}

		switchToDefaultContent(driver);
		if (bp.clickOnTab(environment, mode, TabName.FundDistributions)) {
			if (fd.clickOnCreatedDrawdown(environment, mode, SmokeFD1ID)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (bp.clickOnShowMoreDropdownOnly(environment, mode, PageName.FundDistribution)) {
						log(LogStatus.PASS, "successfully clicked on dropdown",YesNo.No);
						ele=bp.actionDropdownElement(environment,  PageName.FundDistribution, ShowMoreActionDropDownList.Send_Distribution_Notices, 30);
						if (ele!=null) {
							log(LogStatus.PASS, "successfully found send distribution notices button", YesNo.No);
							if (click(driver, bp.actionDropdownElement(environment,  PageName.FundDistribution,  ShowMoreActionDropDownList.Send_Distribution_Notices, 30), ShowMoreActionDropDownList.Send_Distribution_Notices_Button.toString(), action.BOOLEAN)) {
								log(LogStatus.PASS, "successfully click on send button", YesNo.No);
							}
							else {
								log(LogStatus.FAIL, "cannot click on send button", YesNo.Yes);
								sa.assertTrue(false, "could not click on send button");
							}
						}
						else {
							log(LogStatus.ERROR, "could not find send capital call notices button", YesNo.Yes);
							sa.assertTrue(false, "could not find send capital call notices button");
						}
					}
					else {
						log(LogStatus.PASS, "could not click on show more dropdown", YesNo.Yes);
						sa.assertTrue(false, "could not click on show more dropdown");
					}
				}
				else {
					if (fdi.getSendDistributionNotices_Classic(30)!=null) {
						log(LogStatus.PASS, "send Distribution notices button is successfully present", YesNo.No);
						if (click(driver, fdi.getSendDistributionNotices_Classic(30), "send capital call notices button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "successfully click on send Distribution button", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not click on send button", YesNo.Yes);
							sa.assertTrue(false, "could not click on send button");
						}
					}
					else {
						log(LogStatus.FAIL, "send capital call notices button is not present", YesNo.Yes);
						sa.assertTrue(false, "send capital call notices button is not present");
					}
				}
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(30));
				}
				//verify mailing grid
				SmokeID1ID = ExcelUtils.readData(smokeFilePath, "InvestorDistribution", excelLabel.Commitment_ID, SmokeCOMM8_ID, excelLabel.InvestorDistributionID);
				SmokeID2ID = ExcelUtils.readData(smokeFilePath, "InvestorDistribution", excelLabel.Commitment_ID, SmokeCOMM2_ID, excelLabel.InvestorDistributionID);
				SoftAssert saa = fdi.verifyEmailingContentGrid(driver, PageName.FundDistribution,SmokeC1_FName+" "+SmokeC1_LName, SmokeINS1, SmokeC1_EmailID, SmokeID1ID, SmokeCOMM8_ID, Smoke_LP1, Smoke_P1, true);
				sa.combineAssertions(saa);

				saa = fdi.verifyEmailingContentGrid(driver, PageName.FundDistribution,SmokeC2_FName+" "+SmokeC2_LName, SmokeINS1, SmokeC2_EmailID, SmokeID2ID, SmokeCOMM2_ID, Smoke_LP2, Smoke_P1, false);
				sa.combineAssertions(saa);
				
			}else {
				log(LogStatus.ERROR,"could not click on created FD : "+SmokeFD1ID, YesNo.Yes);
				sa.assertTrue(false, "could not click on created FD : "+SmokeFD1ID);
			}
		}
		else {
			log(LogStatus.ERROR,"could not click on fund FundDistributions tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on fund FundDistributions tab");
		}

		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc060_1_VerifyWrenchatSendDistributionNoticesPage(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		FundDistributionsPageBusinessLayer fdd = new FundDistributionsPageBusinessLayer(driver);
	
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		SoftAssert saa = new SoftAssert();
		
		WebElement ele;
		String xpath;
		String parentId=null;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String value = "Created Date";
		if (bp.clickOnTab(environment, mode, TabName.FundDistributions)) {
			if (fd.clickOnCreatedDrawdown(environment, mode, SmokeFD1ID)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					if (bp.clickOnShowMoreDropdownOnly(environment, mode, PageName.FundDistribution)) {
						log(LogStatus.PASS, "successfully clicked on dropdown",YesNo.No);
						ele=bp.actionDropdownElement(environment,  PageName.FundDistribution, ShowMoreActionDropDownList.Send_Distribution_Notices, 30);
						if (ele!=null) {
							log(LogStatus.PASS, "successfully found send capital call notices button", YesNo.No);
							if (click(driver, bp.actionDropdownElement(environment,  PageName.FundDistribution,  ShowMoreActionDropDownList.Send_Distribution_Notices, 30), ShowMoreActionDropDownList.Send_Distribution_Notices.toString(), action.BOOLEAN)) {
								log(LogStatus.PASS, "successfully click on send button", YesNo.No);
							}
							else {
								log(LogStatus.FAIL, "cannot click on send button", YesNo.Yes);
								sa.assertTrue(false, "could not click on send button");
							}
						}
						else {
							log(LogStatus.ERROR, "could not find send capital call notices button", YesNo.Yes);
							sa.assertTrue(false, "could not find send capital call notices button");
						}
					}
					else {
						log(LogStatus.PASS, "could not click on show more dropdown", YesNo.Yes);
						sa.assertTrue(false, "could not click on show more dropdown");
					}
				}
				else {
					if (fdd.getSendDistributionNotices_Classic(30)!=null) {
						log(LogStatus.PASS, "send capital call notices button is successfully present", YesNo.No);
						if (click(driver, fd.getSendCapitalCallNotices_Classic(30), "send capital call notices button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "successfully click on send capital call button", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not click on send button", YesNo.Yes);
							sa.assertTrue(false, "could not click on send button");
						}
					}
					else {
						log(LogStatus.FAIL, "send capital call notices button is not present", YesNo.Yes);
						sa.assertTrue(false, "send capital call notices button is not present");
					}
				}
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(30));
				}


				//verify columns to display ui
				for (int j = 1; j <= 3; j++) {

					if (j==1 || j==2) {

						if (click(driver, fd.getWrenchIcon(30), "wrench icon", action.SCROLLANDBOOLEAN)) {

							if (j==1) {

								ThreadSleep(1000);

								if(selectVisibleTextFromDropDown(driver, market.getColumnToDisplayViewDropDownList(PageName.Send_Distribution_Notices,30), "view drop down list", ViewFieldsName.Contact_Fields)) {
									log(LogStatus.INFO,"Select value from view drop down list : "+ViewFieldsName.Contact_Fields,YesNo.Yes);
								}else {
									log(LogStatus.FAIL,"Not able to select value from view drop down list : "+ViewFieldsName.Contact_Fields,YesNo.Yes);
									sa.assertTrue(false, "Not able to select value from view drop down list : "+ViewFieldsName.Contact_Fields);
								}


								if (click(driver, fdd.getCancelButtonOnColumnsToDisplay(10), "Cancel Button", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Not Able to Click on Cancel Button", YesNo.Yes);
									sa.assertTrue(false, "Not Able to Click on Cancel Button");
								}
							} else if(j==2) {
								if (click(driver, fdd.getCrossIconOnColumnsToDisplay(10), "Cross Icon", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Cross Icon", YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Not Able to Click on Cross Icon", YesNo.Yes);
									sa.assertTrue(false, "Not Able to Click on Cross Icon");
								}
							} 
						}else{
							log(LogStatus.ERROR, "Not Able to Click on Wrench Icon", YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Wrench Icon");	
						}

					} 
					else if(j==3){


						String [] ss = {value};
						if(market.addAndRemoveCloumnInSelectProspectGrid(mode,PageName.Send_Distribution_Notices,ss, null, null,true).isEmpty()) {
							log(LogStatus.PASS, "column added form column to display popup", YesNo.No);
							ThreadSleep(2000);
							if(compareMultipleList(driver, value, market.getSelectProspectsHeaderTextList(PageName.Send_Distribution_Notices)).isEmpty()) {
								log(LogStatus.PASS, "Selected Prospects Header Text is verified ", YesNo.No);
							}else {

								log(LogStatus.FAIL,"Selected Prospects Header Text is not verified",YesNo.Yes);
								sa.assertTrue(false, "Selected Prospects Header Text is not verified");
							}
							ThreadSleep(2000);
							if(market.columnToDisplayRevertToDefaultsSettings(PageName.Send_Distribution_Notices,mode)) {
								log(LogStatus.PASS, "column to display settings is revert to default successfully", YesNo.No);
							}else {
								appLog.error("column to display settings is not revert to default");
								log(LogStatus.FAIL,"column to display settings is not revert to default",YesNo.Yes);
								sa.assertTrue(false,"column to display settings is not revert to default");
							}
							if(!compareMultipleList(driver, value, market.getSelectProspectsHeaderTextList(PageName.Send_Distribution_Notices)).isEmpty()) {
								log(LogStatus.PASS, "Select Prospects Header Text is removed", YesNo.No);
							}else {
								appLog.error("Select Prospects Header Text is not removed");
								log(LogStatus.FAIL,"Select Prospects Header Text is not removed",YesNo.Yes);
								sa.assertTrue(false, "Select Prospects Header Text is not removed");
							}
						}else {
							appLog.error("Not able to add column form column to display popup");
							log(LogStatus.FAIL,"Not able to add column form column to display popup",YesNo.Yes);
							sa.assertTrue(false, "Not able to add column form column to display popup");
						}



					}

				}


			}
			else {
				log(LogStatus.ERROR,"could not click on created drawdown", YesNo.Yes);
				sa.assertTrue(false, "could not click on created drawdown");
			}
		}
		else {
			log(LogStatus.ERROR,"could not click on fund drawdowns tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on fund drawdowns tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc060_2_VerifySendDistributionNoticesFromInvestorDistribution(String environment, String mode) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		CommitmentsPageBusinessLayer comm = new CommitmentsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		FundDistributionsPageBusinessLayer fdi = new FundDistributionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		InvestorDistributionsPageBusinessLayer idp = new InvestorDistributionsPageBusinessLayer(driver);
		SmokeID2ID = ExcelUtils.readData(smokeFilePath, "InvestorDistribution", excelLabel.Commitment_ID, SmokeCOMM2_ID, excelLabel.InvestorDistributionID);
		SoftAssert saa = new SoftAssert();
		int n = 1;
		WebElement ele;
		String msg;
		if (bp.clickOnTab(environment, mode, TabName.InvestorDistributions)) {
			if (fd.clickOnCreatedDrawdown(environment, mode, SmokeID2ID)) {

				if (bp.clickOnShowMoreActionDownArrow(environment, PageName.InvestorDistribution, ShowMoreActionDropDownList.Send_Distribution_Notices, 30)) {
					log(LogStatus.INFO, "clicked on show more dropdown", YesNo.Yes);	

					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(30));
					}

					ele = idp.getEmailInvestorDistributionHeading(environment, mode, 10);

					if (ele != null) {
						log(LogStatus.INFO, "Email Investor Distribution Heading Element", YesNo.Yes);	
						msg = ele.getText().trim();
						String s = "Emailing Investor Distribution Notice(s) of Investor Distribution ID "+SmokeID2ID+" for Fund - "+Smoke_Fund1;

						if (msg.contains(s)) {
							log(LogStatus.INFO, "Message verified "+msg, YesNo.Yes);
						} else {
							log(LogStatus.ERROR, "Message Not verified : Actual : "+msg+" \t Expected : "+s, YesNo.Yes);
							sa.assertTrue(false, "Message Not verified : Actual : "+msg+" \t Expected : "+s);
						}
					} else {
						log(LogStatus.ERROR, "Email Investor Distribution Heading Element Not Found", YesNo.Yes);
						sa.assertTrue(false, "Email Investor Distribution Heading Element Not Fouund");
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on show more dropdown", YesNo.Yes);
					sa.assertTrue(false, "could not click on show more dropdown");
				}

				

				//verify mailing grid
				saa = fdi.verifyEmailingContentGrid(driver, PageName.FundDistribution,SmokeC2_FName+" "+SmokeC2_LName, SmokeINS1, SmokeC2_EmailID, SmokeID2ID, SmokeCOMM2_ID, Smoke_LP2, Smoke_P1, false);
				sa.combineAssertions(saa);
				List<WebElement> li = fd.listOfContactNames(30);
				boolean flag = false;
				int rowCount = li.size();
				for (int i =0;i<li.size();i++) {
					if (li.get(i).getText().trim().contains(SmokeC1_FName + " "+ SmokeC1_LName)) {
						flag = true;
						break;
					}
				}
				if (flag) {
					log(LogStatus.ERROR,SmokeC1_FName + " "+ SmokeC1_LName+ " is found in emailing grid but it should not be",YesNo.Yes);
					sa.assertTrue(false,SmokeC1_FName + " "+ SmokeC1_LName+ " is found in emailing grid but it should not be" );
				}
				else {
					log(LogStatus.INFO, "could not find "+SmokeC1_FName + " "+ SmokeC1_LName+ " as expected", YesNo.No);
				}
				//verify total number of records
				if (fd.getRecordsCount(30).getText().trim().contains("Records: "+n)) {
					log(LogStatus.INFO,"successfully verified number of records",YesNo.No);
				}
				else {
					log(LogStatus.ERROR,"could not verify correct number of records", YesNo.Yes);
					sa.assertTrue(false, "could not verify correct number of records");
				}


				if (click(driver,fd.getStep1HeaderCheckbox(30), "header checkbox", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					if (click(driver, fd.getStep1NextButtonEmailing(30), "next button", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						ele = fd.getStep2TextEmailing(30);
						if (ele!=null) {
							msg = ele.getText().trim();
							if (HomePageErrorMessage.Step2_SelectAnEmailTemplate.equals(msg)) {
								log(LogStatus.PASS, "Step2 Page Verified : "+msg, YesNo.No);
							} else {
								sa.assertTrue(false, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate);
								log(LogStatus.FAIL, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate, YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "Step2 Page Element is null");
							log(LogStatus.FAIL, "Step2 Page Element is null", YesNo.Yes);
						
						}
						
						if(fd.selectEmailTemplateFromFundDrawdown(EmailTemplate1_FolderName, EmailTemplate1_TemplateName)) {
							log(LogStatus.INFO, "successfully selected template : "+EmailTemplate1_TemplateName, YesNo.No);
							if (click(driver, fd.getStep2NextButtonEmailing(30), "step 2 next button", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on step 2 next button", YesNo.No);
								ThreadSleep(2000);
								
								ele = hp.getStep3_EmailInvestorDistributione(environment, mode, 10);
								
								if (ele!=null) {
									msg = ele.getText().trim();
									if (HomePageErrorMessage.step3_ReviewAndConfirm.equals(msg)) {
										log(LogStatus.PASS, "Step3 Page Verified : "+msg, YesNo.No);
									} else {
										sa.assertTrue(false, "Step3 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step3_ReviewAndConfirm);
										log(LogStatus.FAIL, "Step3 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step3_ReviewAndConfirm, YesNo.Yes);
									}
								} else {
									sa.assertTrue(false, "Step3 Page Element is null");
									log(LogStatus.FAIL, "Step3 Page Element is null", YesNo.Yes);
								
								}
								
								ele = fd.getFundDrawdownEmailingSelectedRecipientErrorMsg(20);
								if (ele != null) {
									String aa = ele.getText().trim();
									if (aa.contains(MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1"))) {
										log(LogStatus.INFO,MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1")+ " error message is verified",YesNo.No);
									} else {
										sa.assertTrue(false,"error message is not verified Expected: "+ MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1")+ " \t Actual : " + aa);
										log(LogStatus.ERROR,"error message is not verified Expected: "+ MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1")+ " \t Actual : " + aa,YesNo.Yes);
									}
								} else {
									sa.assertTrue(false,	MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1") + " error message is not visible");
									log(LogStatus.ERROR,	MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1") + " error message is not visible",YesNo.Yes);
								}
								List<WebElement> labellist = fd.getEmailProcessingOptionsLableTextList();
								List<WebElement> checkBoxList = fd.getEmailProcessingOptionsCheckBoxList();
								String[] expctRsult = { "BCC me on one message", "Use my signature","Store an Activity for Each Message" };

								for (int j = 0; j < labellist.size(); j++) {
									if (labellist.get(j).getText().trim().contains(expctRsult[j])) {
										String checkBoxAttr = checkBoxList.get(j).getAttribute("checked");
										System.err.println(j+"  checked:   "+ checkBoxAttr);
										if (j == 2) {
											if (("true").equalsIgnoreCase(checkBoxAttr)) {
												log(LogStatus.INFO,expctRsult[j] + "check box is selected",YesNo.No);
											} else {
												sa.assertTrue(false,expctRsult[j] + "check box is not selected");
												log(LogStatus.ERROR,expctRsult[j] + "check box is not selected",YesNo.Yes);
											}
										} else {

											if (checkBoxAttr==null) {
												log(LogStatus.INFO,expctRsult[j] + "check box is not selected",YesNo.No);
											} else {
												sa.assertTrue(false,expctRsult[j] + "check box is selected");
												log(LogStatus.ERROR,expctRsult[j] + "check box is selected",YesNo.Yes);
											}
										}

									}else{
										sa.assertTrue(false, expctRsult[j] + "label is not verified");
										log(LogStatus.ERROR, expctRsult[j] + "label is not verified",YesNo.Yes);
									}
								}
								

								if(click(driver,fd.getfundDrawdownEmailingSendBtn(TopOrBottom.BOTTOM, 30), "send button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on send button", YesNo.No);
									ele=fd.getFundDrawdownSendEmailCongratulationsErrorMsg(30);
									String errorMsg=FundDrawdownPageErrorMessage.congratulationErrorMsg+" "+FundDrawdownPageErrorMessage.generatedEmailedRecipientErrorMsg("1", "Email");
									if(ele!=null) {
										String aa1 =ele.getText().trim();
										if(aa1.contains(FundDrawdownPageErrorMessage.congratulationErrorMsg) && aa1.contains(FundDrawdownPageErrorMessage.generatedEmailedRecipientErrorMsg("1", "Email"))) {
											log(LogStatus.INFO, "Congratulation Error Message is verified", YesNo.No);
										}else {
											log(LogStatus.ERROR, "Congratulation Error Message is not verified. Expected: "+errorMsg+"\t Actual: "+aa1, YesNo.Yes);
										}
									}else {
										log(LogStatus.ERROR, "Congratulations Error Message is not visible so cannot verify it. Error Msg: "+errorMsg, YesNo.Yes);
									}
									if (click(driver, fd.getFinishedButtonEmailing(30), "finished button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "finished button clicked", YesNo.No);
										ThreadSleep(2000);
									}
									else {
										log(LogStatus.ERROR, "finished button could not be clicked", YesNo.Yes);
										sa.assertTrue(false, "finished button could not be clicked");
									}
								}else {
									log(LogStatus.ERROR, "Not able to click on send button so cannot send email to contact", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on send button so cannot send email to contact");
								}
							}else {
								log(LogStatus.ERROR, "step 2 next button is not clickable", YesNo.Yes);
								sa.assertTrue(false, "step 2 next button is not clickable");
							}	
						}
						else {
							log(LogStatus.ERROR, "could not select email template : "+EmailTemplate1_TemplateName, YesNo.Yes);
							sa.assertTrue(false, "could not select email template : "+EmailTemplate1_TemplateName);
						}
					}
					else {
						log(LogStatus.ERROR, "step 1 next button is not clickable", YesNo.Yes);
						sa.assertTrue(false, "step 1 next button is not clickable");
					}
				}
				else {
					log(LogStatus.ERROR, "header checkbox is not clickable", YesNo.Yes);
					sa.assertTrue(false, "header checkbox is not clickable");
				}

				switchToDefaultContent(driver);
			}
			else {
				log(LogStatus.ERROR,"could not click on created drawdown", YesNo.Yes);
				sa.assertTrue(false, "could not click on created drawdown");
			}
		}
		else {
			log(LogStatus.ERROR,"could not click on fund drawdowns tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on fund drawdowns tab");
		}
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc061_verifyReceivedEmailAndCreatedActivityForInvestorDistribution(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		SoftAssert tsa;
		String text = null;
		String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
		EmailLib email = new EmailLib();
		try {
			text = email.getEMailContent(gmailUserName, gmailPassword, crmUser1EmailID, SmokeC2_EmailID,EmailTemplate1_Subject);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			sa.assertTrue(false, EmailTemplate1_Subject +" Email Template is not verified from email : " + gmailUserName);
			log(LogStatus.FAIL, EmailTemplate1_Subject+ " Email Template is not verified from email : " + gmailUserName, YesNo.No);
			e.printStackTrace();
		}
		if (text != null) {
			log(LogStatus.INFO, EmailTemplate1_Subject+" template is availble on email " + gmailUserName
					+ "Hence verified send email text", YesNo.No);	
		} else {
			sa.assertTrue(false, EmailTemplate1_Subject+" template is not availble on email " + gmailUserName
					+ " so cannot verify send email text");
			log(LogStatus.ERROR, EmailTemplate1_Subject+" template is not availble on email " + gmailUserName
					+ " so cannot verify send email text", YesNo.No);
		}
	//	lp.CRMLogin(crmUser1EmailID, adminPassword);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		if (cp.clickOnTab(environment, mode, TabName.ContactTab)) {
			log(LogStatus.INFO, "Clicked on Contact Tab",YesNo.No);
			if (cp.clickOnCreatedContact(environment,  SmokeC2_FName, SmokeC2_LName)) {
				log(LogStatus.INFO, "Clicked on Created Contact : "+SmokeC2_FName+" "+SmokeC2_LName,YesNo.No);	
				
				if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Activity_History, null)) {
					log(LogStatus.INFO, "Clicked on Activity History",YesNo.No);	
					
					if (cp.verifyCreatedActivityHistory(environment, mode, EmailTemplate1_Subject)) {
						log(LogStatus.INFO, "Activity verified: "+EmailTemplate1_Subject,YesNo.No);	
					} else {
						sa.assertTrue(false, "Activity not verified: "+EmailTemplate1_Subject);
						log(LogStatus.SKIP, "Activity not verified: "+EmailTemplate1_Subject,YesNo.Yes);
					}
				
				} else {
					sa.assertTrue(false, "Not on Activity History");
					log(LogStatus.SKIP, "Not on Activity History",YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false, "Contact not Found : "+SmokeC2_FName+" "+SmokeC2_LName);
				log(LogStatus.SKIP, "Contact not Found : "+SmokeC2_FName+" "+SmokeC2_LName,YesNo.Yes);	
			}
			
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab",YesNo.Yes);	
		}
		
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc062_VerifyContactTransferButtonAtContactPageAndOfficeLocationRelatedListAtAccountPage(
			String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String officeLocationLabel;
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			officeLocationLabel = "//label[text()='Office Location']";
		} else {
			officeLocationLabel = "//label[text()='Office Location']";
		}
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Contact Tab");
		if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
			if (cp.clickOnCreatedContact(environment,  SmokeC5_FName, SmokeC5_LName)) {
				log(LogStatus.INFO, "Click on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName, YesNo.No);

				WebElement contTransfer = cp.getContactTransfer(environment, 5);
				contTransfer=isDisplayed(driver, contTransfer, "Visibility", 5, "Contact Transfer Lighting");
				if (contTransfer == null) {
					log(LogStatus.INFO, "Contact Transfer Button not Present", YesNo.No);
				} else {
					sa.assertTrue(false, "Contact Transfer Button is Present");
					log(LogStatus.ERROR, "Contact Transfer Button is Present", YesNo.Yes);
				}

				if (cp.clickOnShowMoreActionDownArrow("PE", PageName.ContactPage, ShowMoreActionDropDownList.Edit, 20)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);

					WebElement officeLocEle = FindElement(driver, officeLocationLabel, "Office Location Label",
							action.SCROLLANDBOOLEAN, 10);

					if (officeLocEle == null) {
						log(LogStatus.INFO, "office Location Label is not Present", YesNo.No);
					} else {
						sa.assertTrue(false, "office Location Label is Present");
						log(LogStatus.ERROR, "office Location Label is Present", YesNo.Yes);
					}

				} else {
					sa.assertTrue(false, "Not Able to Click on Edit Button");
					log(LogStatus.ERROR, "Not Able to Click on Edit Button", YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName,
						YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		if (ip.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			log(LogStatus.INFO, "Clicked on Institution Tab", YesNo.No);
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINS2)) {
				log(LogStatus.INFO, "Clicked on Created Institution : " + SmokeINS2, YesNo.No);

				if (cp.clickOnRelatedList(environment, mode, RecordType.IndividualInvestor, RelatedList.Office_Locations, RelatedTab.Details.toString())) {
					log(LogStatus.INFO, "Click on details Tab for Office Locations", YesNo.No);

					if (ip.getOfficeLocation(environment, mode, RecordType.IndividualInvestor, 5) == null) {
						log(LogStatus.INFO, "Office Location Related List is not Available", YesNo.Yes);
					} else {
						sa.assertTrue(false, "Office Location Related List is Available");
						log(LogStatus.ERROR, "Office Location Related List is Available", YesNo.Yes);
					}

				} else {
					sa.assertTrue(false, "Not Able to Click on details Tab for Office Locations");
					log(LogStatus.SKIP, "Not Able to Click on details Tab for Office Locations", YesNo.Yes);
				}

				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINS2);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINS2, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc063_EnableContactTransferSettingFromNavatarSetup(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		;
		ContactTransferTabBusinessLayer ctt = new ContactTransferTabBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword);
		appLog.info("Login with User");
		if (bp.clickOnTab(environment, mode, TabName.NavatarSetup)) {
			appLog.info("Clicked on Navatar Set Up Tab");

			if (ctt.clickOnNavatarSetupSideMenusTab(environment,  NavatarSetupSideMenuTab.ContactTransfer)) {
				appLog.error("Clicked on Contact Transfer Tab");

				// 1st

				if (!isSelected(driver, ctt.getEnableCheckBoxforNavatarSetUpSideMenuTab(environment, mode,
						NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.View, ClickOrCheckEnableDisableCheckBox.EnableOrDisable, 10), "Enabled CheckBox")) {
					log(LogStatus.INFO, "Enable Contact Transfer is Unchecked", YesNo.No);
				} else {
					sa.assertTrue(false, "Enable Contact Transfer is Already checked");
					log(LogStatus.SKIP, "Enable Contact Transfer is Already checked", YesNo.Yes);
				}
				
				// Azhar Start
				if (click(driver, ctt.getEditButtonforNavatarSetUpSideMenuTab(environment, 
						NavatarSetupSideMenuTab.ContactTransfer, 10), "Edit Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);
					ThreadSleep(2000);
					
					if(click(driver, ctt.getCancelButtonforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.ContactTransfer, 20), "cancel button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "click on cancel button", YesNo.No);
					}else {
						log(LogStatus.ERROR, "Not able to click on cancel button so cannot close co investment pop up", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on cancel button so cannot close co investment pop up");
					}
					
				}else{
					appLog.error("Not Able to Click on Edit Button for Cancel");
					sa.assertTrue(false, "Not Able to Click on Edit Button for Cancel");
					log(LogStatus.SKIP, "Not Able to Click on Edit Button for Cancel", YesNo.Yes);	
				}
				// Azhar End
				ThreadSleep(5000);
				switchToDefaultContent(driver);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 10, ctt.getnavatarSetUpTabFrame_Lighting(environment, 10));
				}
				if (click(driver, ctt.getEditButtonforNavatarSetUpSideMenuTab(environment,
						NavatarSetupSideMenuTab.ContactTransfer, 10), "Edit Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);
					ThreadSleep(2000);

					// 2nd

					log(LogStatus.INFO, "Going to Verify Keep Activities At Drop Down Values", YesNo.No);
					String keepActivitiesDefaultValue = "Old Institution Only";
					String defaultvalue = getSelectedOptionOfDropDown(driver,
							ctt.getKeepActivitiesAtSelectList(environment, mode, EditViewMode.Edit, 10),
							keepActivitiesDefaultValue, "Text");
					if (keepActivitiesDefaultValue.equalsIgnoreCase(defaultvalue)) {
						log(LogStatus.INFO, "Keep Activities Default Value Matched: " + defaultvalue, YesNo.No);
					} else {
						sa.assertTrue(false, "Keep Activities Default default value not matched Actual : "
								+ defaultvalue + " \t Expected : " + keepActivitiesDefaultValue);
						log(LogStatus.INFO, "Keep Activities Default default value not matched Actual : " + defaultvalue
								+ " \t Expected : " + keepActivitiesDefaultValue, YesNo.Yes);
					}

					String expectedResult = "Old Institution Only" + "," + "Old and New Institutions";
					List<WebElement> keepActivitiesSelectList = allOptionsInDropDrop(driver,
							ctt.getKeepActivitiesAtSelectList(environment, mode, EditViewMode.Edit, 10),
							"Keep Activities At Drop Down");

					List<String> returnlist = compareMultipleList(driver, expectedResult, keepActivitiesSelectList);
					if (returnlist.isEmpty()) {
						appLog.info("Keep Activities Drop Down matched  ");
					} else {
						appLog.error("Keep Activities Drop Down not matched");
						sa.assertTrue(false, "Keep Activities Drop Down not matched");
						log(LogStatus.INFO, "Keep Activities Drop Down not matched", YesNo.Yes);
					}

					log(LogStatus.INFO, "Going to Verify Include Activities Related to Drop Down Values", YesNo.No);
					keepActivitiesDefaultValue = "Contact Only";
					defaultvalue = getSelectedOptionOfDropDown(driver,
							ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.Edit, 10),
							keepActivitiesDefaultValue, "Text");
					if (keepActivitiesDefaultValue.equalsIgnoreCase(defaultvalue)) {
						log(LogStatus.INFO, "Include Activities Related to Default Value Matched: " + defaultvalue,
								YesNo.No);
					} else {
						sa.assertTrue(false, "Include Activities Related to default value not matched Actual : "
								+ defaultvalue + " \t Expected : " + keepActivitiesDefaultValue);
						log(LogStatus.INFO, "Include Activities Related to default value not matched Actual : "
								+ defaultvalue + " \t Expected : " + keepActivitiesDefaultValue, YesNo.Yes);
					}

					expectedResult = "Contact Only" + "<break>" + "Contact and Institution" + "<break>"
							+ "Contact, Institution and Custom Object";
					keepActivitiesSelectList = allOptionsInDropDrop(driver,
							ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.Edit, 10),
							"Include Activities Related to Drop Down");

					returnlist = compareMultipleListWithBreak(driver, expectedResult, keepActivitiesSelectList);
					if (returnlist.isEmpty()) {
						log(LogStatus.INFO, "Include Activities Related to Drop Down matched ", YesNo.No);
					} else {
						sa.assertTrue(false, "Include Activities Related to Drop Down Value not matched");
						log(LogStatus.INFO, "Include Activities Related to Drop Down Value not matched", YesNo.Yes);
					}

					if (!isSelected(driver, ctt.getEnableCheckBoxforNavatarSetUpSideMenuTab(environment, mode,
							NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled CheckBox")) {

						log(LogStatus.INFO, "Enable Contact Transfer is Unchecked", YesNo.No);

						if (click(driver,
								ctt.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(environment, 
										NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, 10),
								"Enabled PipeLine Stage Log", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Enable Contact Transfer Box Checkbox", YesNo.No);
							ThreadSleep(2000);

						} else {
							sa.assertTrue(false, "Not Able to Click on Enable Contact Transfer Checkbox");
							log(LogStatus.SKIP, "Not Able to Click on Enable Contact Transfer Checkbox", YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Enable Contact Transfer is Already checked");
						log(LogStatus.SKIP, "Enable Contact Transfer is Already checked", YesNo.Yes);
					}

					// 3rd

					String selectIncludeActivitiesValue = "Contact, Institution and Custom Object";
					if (selectVisibleTextFromDropDown(driver,
							ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.Edit, 10),
							selectIncludeActivitiesValue, selectIncludeActivitiesValue)) {
						log(LogStatus.INFO, "Selected Include Activities related to : " + selectIncludeActivitiesValue,
								YesNo.No);
					} else {
						sa.assertTrue(false,
								"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue);
						log(LogStatus.SKIP,
								"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue,
								YesNo.Yes);

					}

					if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(environment, 
							NavatarSetupSideMenuTab.ContactTransfer, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
						ThreadSleep(2000);
						log(LogStatus.INFO, "Clicked on Save Button for No Button", YesNo.No);

						String msg = ctt.getconfirmationPopUpMsg(environment, 10).getText();
						if (msg != null) {
							if (msg.contains(ContactTransferTabErrorMessage.ConfirmationPopUpMessage1)
									&& msg.contains(ContactTransferTabErrorMessage.ConfirmationPopUpMessage2)) {
								log(LogStatus.INFO, "Confirmation PopUp MEssage Verified : " + msg, YesNo.No);
							} else {
								sa.assertTrue(false, "Confirmation PopUp MEssage Not Verified :  Actual -  " + msg
										+ "\t Expected : " + ContactTransferTabErrorMessage.ConfirmationPopUpMessage1);
								log(LogStatus.SKIP, "Confirmation PopUp MEssage Not Verified :  Actual -  " + msg
										+ "\t Expected : " + ContactTransferTabErrorMessage.ConfirmationPopUpMessage1,
										YesNo.Yes);
							}
						} else {
							;
							sa.assertTrue(false, "Confirmation PopUp Message Element is null");
							log(LogStatus.SKIP, "Confirmation PopUp Message Element is null", YesNo.Yes);
						}

						if (click(driver, ctt.getConfirmationPopUpNoButton(environment, 10), "No Button",
								action.BOOLEAN)) {

							log(LogStatus.INFO, "Clicked on No Button", YesNo.No);
							ThreadSleep(2000);
							defaultvalue = getSelectedOptionOfDropDown(driver,
									ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.Edit, 10),
									selectIncludeActivitiesValue, "Text");
							if (selectIncludeActivitiesValue.equalsIgnoreCase(defaultvalue)) {
								log(LogStatus.INFO,
										"Include Activities Related to Selected Value Matched: " + defaultvalue,
										YesNo.No);
							} else {
								sa.assertTrue(false,
										"Include Activities Related to Selected value not matched Actual : "
												+ defaultvalue + " \t Expected : " + selectIncludeActivitiesValue);
								log(LogStatus.INFO,
										"Include Activities Related to Selected value not matched Actual : "
												+ defaultvalue + " \t Expected : " + selectIncludeActivitiesValue,
										YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "Not Able to Click on No Button");
							log(LogStatus.SKIP, "Not Able to Click on No Button", YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Save Button for No Button");
						log(LogStatus.SKIP, "Not Able to Click on Save Button No Button", YesNo.Yes);
					}

					if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(environment,
							NavatarSetupSideMenuTab.ContactTransfer, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
						ThreadSleep(2000);
						log(LogStatus.INFO, "Clicked on Save Button for Cross Icon", YesNo.No);

						String msg = ctt.getconfirmationPopUpMsg(environment, 10).getText();
						if (msg != null) {
							if (msg.contains(ContactTransferTabErrorMessage.ConfirmationPopUpMessage1)
									&& msg.contains(ContactTransferTabErrorMessage.ConfirmationPopUpMessage2)) {
								log(LogStatus.INFO, "Confirmation PopUp MEssage Verified : " + msg, YesNo.No);
							} else {
								sa.assertTrue(false, "Confirmation PopUp MEssage Not Verified :  Actual -  " + msg
										+ "\t Expected : " + ContactTransferTabErrorMessage.ConfirmationPopUpMessage1);
								log(LogStatus.SKIP, "Confirmation PopUp MEssage Not Verified :  Actual -  " + msg
										+ "\t Expected : " + ContactTransferTabErrorMessage.ConfirmationPopUpMessage1,
										YesNo.Yes);
							}
						} else {
							;
							sa.assertTrue(false, "Confirmation PopUp Message Element is null");
							log(LogStatus.SKIP, "Confirmation PopUp Message Element is null", YesNo.Yes);
						}

						if (click(driver, ctt.getConfirmationPopUpCrossIcon(environment, 10), "Cross Icon",
								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Cross Icon", YesNo.No);
							ThreadSleep(2000);
							defaultvalue = getSelectedOptionOfDropDown(driver,
									ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.Edit, 10),
									selectIncludeActivitiesValue, "Text");
							if (selectIncludeActivitiesValue.equalsIgnoreCase(defaultvalue)) {
								log(LogStatus.INFO,
										"Include Activities Related to Selected Value Matched for Cross Icon: "
												+ defaultvalue,
										YesNo.No);
							} else {
								sa.assertTrue(false,
										"Include Activities Related to Selected value not matched for Cross Icon Actual : "
												+ defaultvalue + " \t Expected : " + selectIncludeActivitiesValue);
								log(LogStatus.INFO,
										"Include Activities Related to Selected value not matched for Cross Icon Actual : "
												+ defaultvalue + " \t Expected : " + selectIncludeActivitiesValue,
										YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "Not Able to Click on Cross Icon");
							log(LogStatus.SKIP, "Not Able to Click on Cross Icon", YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Save Button for Cross Icon");
						log(LogStatus.SKIP, "Not Able to Click on Save Button for Cross Icon", YesNo.Yes);
					}

					if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(environment, 
							NavatarSetupSideMenuTab.ContactTransfer, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
						ThreadSleep(7000);
						log(LogStatus.INFO, "Clicked on Save Button for Yes Button", YesNo.No);

						String msg = ctt.getconfirmationPopUpMsg(environment, 10).getText();
						if (msg != null) {
							if (msg.contains(ContactTransferTabErrorMessage.ConfirmationPopUpMessage1)
									&& msg.contains(ContactTransferTabErrorMessage.ConfirmationPopUpMessage2)) {
								log(LogStatus.INFO, "Confirmation PopUp MEssage Verified : " + msg, YesNo.No);
							} else {
								sa.assertTrue(false, "Confirmation PopUp MEssage Not Verified :  Actual -  " + msg
										+ "\t Expected : " + ContactTransferTabErrorMessage.ConfirmationPopUpMessage1);
								log(LogStatus.SKIP, "Confirmation PopUp MEssage Not Verified :  Actual -  " + msg
										+ "\t Expected : " + ContactTransferTabErrorMessage.ConfirmationPopUpMessage1,
										YesNo.Yes);
							}
						} else {
							;
							sa.assertTrue(false, "Confirmation PopUp Message Element is null");
							log(LogStatus.SKIP, "Confirmation PopUp Message Element is null", YesNo.Yes);
						}

						if (click(driver, ctt.getConfirmationPopUpYesButton(environment, 10), "Yes Button",
								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Yes Button", YesNo.No);
							ThreadSleep(10000);
							keepActivitiesDefaultValue = "Old Institution Only";
							SoftAssert tsa = ctt.verifyingContactTransferTab(environment, mode, EditViewMode.View,
									CheckBox.Checked, keepActivitiesDefaultValue, selectIncludeActivitiesValue);
							sa.combineAssertions(tsa);
						} else {
							sa.assertTrue(false, "Not Able to Click on Yes Button");
							log(LogStatus.SKIP, "Not Able to Click on Yes Button", YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Save Button for Yes Button");
						log(LogStatus.SKIP, "Not Able to Click on Save Button for Yes Button", YesNo.Yes);
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
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc064_VerifyOfficeLocationSetting(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		;
		ContactTransferTabBusinessLayer ctt = new ContactTransferTabBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword);
		appLog.info("Login with User");
		if (bp.clickOnTab(environment, mode, TabName.NavatarSetup)) {
			appLog.info("Clicked on Navatar Set Up Tab");

			if (ctt.clickOnNavatarSetupSideMenusTab(environment, NavatarSetupSideMenuTab.OfficeLocations)) {
				appLog.error("Clicked on Office Locations Tab");

				// 1st

				if (!isSelected(driver, ctt.getEnableCheckBoxforNavatarSetUpSideMenuTab(environment, mode,
						NavatarSetupSideMenuTab.OfficeLocations, EditViewMode.View, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled CheckBox")) {
					log(LogStatus.INFO, "Enable Office Locations is Unchecked", YesNo.No);
				} else {
					sa.assertTrue(false, "Enable Office Locations is Already checked");
					log(LogStatus.SKIP, "Enable Office Locations is Already checked", YesNo.Yes);
				}
				
				
				// Azhar Start
				if (click(driver, ctt.getEditButtonforNavatarSetUpSideMenuTab(environment, 
						NavatarSetupSideMenuTab.OfficeLocations, 10), "Edit Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);
					ThreadSleep(2000);
					
					if(click(driver, ctt.getCancelButtonforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.OfficeLocations, 20), "cancel button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "click on cancel button", YesNo.No);
					}else {
						log(LogStatus.ERROR, "Not able to click on cancel button so cannot close co investment pop up", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on cancel button so cannot close co investment pop up");
					}
					
				}else{
					appLog.error("Not Able to Click on Edit Button for Cancel");
					sa.assertTrue(false, "Not Able to Click on Edit Button for Cancel");
					log(LogStatus.SKIP, "Not Able to Click on Edit Button for Cancel", YesNo.Yes);	
				}
				// Azhar End
				ThreadSleep(5000);
				switchToDefaultContent(driver);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 10, ctt.getnavatarSetUpTabFrame_Lighting(environment, 10));
				}
				if (click(driver, ctt.getEditButtonforNavatarSetUpSideMenuTab(environment, 
						NavatarSetupSideMenuTab.OfficeLocations, 10), "Edit Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);
					ThreadSleep(2000);

					// 2nd

					if (!isSelected(driver, ctt.getEnableCheckBoxforNavatarSetUpSideMenuTab(environment, mode,
							NavatarSetupSideMenuTab.OfficeLocations, EditViewMode.Edit, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled CheckBox")) {
						log(LogStatus.INFO, "Enable Office Locations is Unchecked", YesNo.No);

						if (click(driver,
								ctt.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(environment, 
										NavatarSetupSideMenuTab.OfficeLocations, EditViewMode.Edit, 10),
								"ChecK Box", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Enable office Location CheckBox", YesNo.No);
							ThreadSleep(2000);
							// 3rd
							if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(environment, 
									NavatarSetupSideMenuTab.OfficeLocations, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
								ThreadSleep(10000);
								log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);

								if (isSelected(driver,
										ctt.getEnableCheckBoxforNavatarSetUpSideMenuTab(environment, mode,
												NavatarSetupSideMenuTab.OfficeLocations, EditViewMode.View, ClickOrCheckEnableDisableCheckBox.Click, 10),
										"Enabled CheckBox")) {
									log(LogStatus.INFO, "Enable Office Locations is Checked", YesNo.No);
								} else {
									sa.assertTrue(false, "Enable Office Locations is Unchecked");
									log(LogStatus.SKIP, "Enable Office Locations is Unchecked", YesNo.Yes);
								}

							} else {
								sa.assertTrue(false, "Not Able to Click on Save Button");
								log(LogStatus.SKIP, "Not Able to Click on Save Button", YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Enable office Location CheckBox");
							log(LogStatus.SKIP, "Not Able to Click on Enable office Location CheckBox", YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Enable Office Locations is Already checked");
						log(LogStatus.SKIP, "Enable Office Locations is Already checked", YesNo.Yes);
					}

				} else {

					sa.assertTrue(false, "Not Able to Click on Edit Button");
					log(LogStatus.SKIP, "Not Able to Click on Edit Button", YesNo.Yes);
				}
			} else {

				sa.assertTrue(false, "Not Able to Click on Office Locations Tab");
				log(LogStatus.SKIP, "Not Able to Click on Office Locations Tab", YesNo.Yes);
			}

		} else {

			sa.assertTrue(false, "Not Able to Click on Navatar Set Up Tab");
			log(LogStatus.SKIP, "Not Able to Click on Navatar Set Up Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc065_VerifyContactTransferButtonAtContactPageAndOfficeLocationRelatedListAtAccountPage(
			String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String officeLocationLabel;
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			officeLocationLabel = "//label[text()='Office Location']";
		} else {
			officeLocationLabel = "//label[text()='Office Location']";
		}
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Contact Tab");
		if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
			if (cp.clickOnCreatedContact(environment,  SmokeC5_FName, SmokeC5_LName)) {
				log(LogStatus.INFO, "Click on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName, YesNo.No);

				WebElement contTransfer = cp.getContactTransfer(environment,  10);
				if (contTransfer != null) {
					log(LogStatus.INFO, "Contact Transfer Button Present", YesNo.No);
				} else {
					sa.assertTrue(false, "Contact Transfer Button is not Present");
					log(LogStatus.ERROR, "Contact Transfer Button is NOT Present", YesNo.Yes);
				}

				if (cp.clickOnShowMoreActionDownArrow("PE", PageName.ContactPage, ShowMoreActionDropDownList.Edit, 20)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);

					WebElement officeLocEle = FindElement(driver, officeLocationLabel, "Office Location Label",
							action.SCROLLANDBOOLEAN, 10);

					if (officeLocEle != null) {
						log(LogStatus.INFO, "office Location Label is Present", YesNo.No);
					} else {
						sa.assertTrue(false, "office Location Label is not Present");
						log(LogStatus.ERROR, "office Location Label is not Present", YesNo.Yes);
					}

				} else {
					sa.assertTrue(false, "Not Able to Click on Edit Button");
					log(LogStatus.ERROR, "Not Able to Click on Edit Button", YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName,
						YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}
		switchToDefaultContent(driver);


		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc066_CreateOfficeLocationsForInstitutionContactTransferAcc1(String environment, String mode) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SoftAssert tsa = new SoftAssert();
		String[][] officeLocation1 = { { OfficeLocationLabel.Office_Location_Name.toString(), Smoke_OFFLoc1Name },
				{ OfficeLocationLabel.Phone.toString(), Smoke_OFFLoc1Phone },
				{ OfficeLocationLabel.Street.toString(), Smoke_OFFLoc1Street },
				{ OfficeLocationLabel.Fax.toString(), Smoke_OFFLoc1Fax },
				{ OfficeLocationLabel.City.toString(), Smoke_OFFLoc1City },
				{ OfficeLocationLabel.Primary.toString(), Smoke_OFFLoc1Primary },
				{ OfficeLocationLabel.State_Province.toString(), Smoke_OFFLoc1StateProvince },
				{ OfficeLocationLabel.Country.toString(), Smoke_OFFLoc1Country },
				{ OfficeLocationLabel.ZIP.toString(), Smoke_OFFLoc1ZIP },
				{ OfficeLocationLabel.Organization_Name.toString(), Smoke_OFFLoc1OrgName }};

		String[][] officeLocation2 = { { OfficeLocationLabel.Office_Location_Name.toString(), Smoke_OFFLoc2Name },
				{ OfficeLocationLabel.Phone.toString(), Smoke_OFFLoc2Phone },
				{ OfficeLocationLabel.Street.toString(), Smoke_OFFLoc2Street },
				{ OfficeLocationLabel.Fax.toString(), Smoke_OFFLoc2Fax },
				{ OfficeLocationLabel.City.toString(), Smoke_OFFLoc2City },
				{ OfficeLocationLabel.Primary.toString(), Smoke_OFFLoc2Primary },
				{ OfficeLocationLabel.State_Province.toString(), Smoke_OFFLoc2StateProvince },
				{ OfficeLocationLabel.Country.toString(), Smoke_OFFLoc2Country },
				{ OfficeLocationLabel.ZIP.toString(), Smoke_OFFLoc2ZIP },
				{ OfficeLocationLabel.Organization_Name.toString(), Smoke_OFFLoc2OrgName }};

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		log(LogStatus.INFO, "Login with CRM User", YesNo.No);
		log(LogStatus.INFO, "Going to Enter information about Office Location 1", YesNo.No);
		if (ip.clickOnTab(environment, mode, TabName.OfficeLocations)) {
			log(LogStatus.INFO, "Clicked on office locations Tab", YesNo.No);
			
				if (click(driver, ip.getNewOfficeLocationButton(environment, mode, RecordType.Institution, 10),
						"New Office Location", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.SKIP, "Clicked on New Office Location  : " + SmokeINS2, YesNo.Yes);

					tsa = ip.EnterValueForLabelonOfficeLocation(environment, mode, officeLocation1);
					sa.combineAssertions(tsa);
				} else {
					sa.assertTrue(false, "Not Able to Click on New Office Location : " + SmokeINS2);
					log(LogStatus.SKIP, "Not Able to Click on New Office Location  : " + SmokeINS2, YesNo.Yes);
				}

			
		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}

		log(LogStatus.INFO, "Going to Enter information about Office Location 2", YesNo.No);
		if (ip.clickOnTab(environment, mode, TabName.OfficeLocations)) {
			log(LogStatus.INFO, "Clicked on office locations Tab", YesNo.No);
			
				if (click(driver, ip.getNewOfficeLocationButton(environment, mode, RecordType.Institution, 10),
						"New Office Location", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.SKIP, "Clicked on New Office Location  : " + SmokeINS2, YesNo.Yes);

					tsa = ip.EnterValueForLabelonOfficeLocation(environment, mode, officeLocation2);
					sa.combineAssertions(tsa);
				} else {
					sa.assertTrue(false, "Not Able to Click on New Office Location : " + SmokeINS2);
					log(LogStatus.SKIP, "Not Able to Click on New Office Location  : " + SmokeINS2, YesNo.Yes);
				}

			
		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}
		String[] label= {excelLabel.Office_Location_Name.toString(),
				excelLabel.Street.toString(),
				excelLabel.City.toString(),
				"State/Province",excelLabel.Country.toString(),
				excelLabel.Primary.toString()};
		
		log(LogStatus.INFO, "Going to Verify Office Location 1 & 2", YesNo.No);
		
		String[][] locations = {{Smoke_OFFLoc1Name,Smoke_OFFLoc1Street,Smoke_OFFLoc1City,Smoke_OFFLoc1StateProvince,Smoke_OFFLoc1Country,Smoke_OFFLoc1Primary},
				{Smoke_OFFLoc2Name,Smoke_OFFLoc2Street,Smoke_OFFLoc2City,Smoke_OFFLoc2StateProvince,Smoke_OFFLoc2Country,Smoke_OFFLoc2Primary}};
		
		for(String[] location:locations) {
		if (ip.clickOnTab(environment, mode, TabName.OfficeLocations)) {
			log(LogStatus.INFO, "Clicked on office location Tab", YesNo.No);
			if (ip.clickOnCreatedOfficeLocation(environment, mode, location[0])) {
				log(LogStatus.INFO, "Clicked on Created office location : " + location[0], YesNo.No);
	
				if (ip.clickOnRelatedList(environment, mode, RecordType.IndividualInvestor, RelatedList.Office_Locations, RelatedTab.Details.toString())) {
					
					log(LogStatus.PASS, "Clicked on Details TAB : "+RelatedList.Office_Locations, YesNo.No);
					
					

					for(int i=0;i<location.length;i++) {
						if(ip.fieldValueVerificationOnOfficeLocationPage(environment, mode, label[i], location[i])) {
							log(LogStatus.FAIL, "value Verified for : " + location[i], YesNo.Yes);
							
						}else {
							sa.assertTrue(false, "value Not Verified for : " + location[i]);
							log(LogStatus.FAIL, "value Not Verified for : " + location[i], YesNo.Yes);
						}
			
					}
				} else {
					sa.assertTrue(false, "Not Able to Clicked on link : " + RelatedList.Office_Locations);
					log(LogStatus.FAIL, "Not Able to Clicked on link : " + RelatedList.Office_Locations, YesNo.Yes);
				}
	
			} else {
				sa.assertTrue(false, "Not Able to Click on Created office location : " + location[0]);
				log(LogStatus.SKIP, "Not Able to Click on Created office location : " + location[0], YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc067_VerifyBillingAddressOfInstitutionContactTransferAcc1AfterCreatingPrimaryOfficeLocation(
			String environment, String mode) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		log(LogStatus.INFO, "Login with CRM User", YesNo.No);
		if (ip.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			log(LogStatus.INFO, "Clicked on Institution Tab", YesNo.No);
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINS2)) {
				log(LogStatus.INFO, "Clicked on Created Institution : " + SmokeINS2, YesNo.No);

				String[][] labelsWithValues = { { OfficeLocationLabel.Phone.toString(), Smoke_OFFLoc2Phone },
						{ OfficeLocationLabel.Fax.toString(), Smoke_OFFLoc2Fax },
						{ OfficeLocationLabel.Street.toString(), Smoke_OFFLoc2Street },
						{ OfficeLocationLabel.City.toString(), Smoke_OFFLoc2City },
						{ OfficeLocationLabel.State.toString(), Smoke_OFFLoc2StateProvince },
						{ OfficeLocationLabel.Country.toString(), Smoke_OFFLoc2Country },
						{ excelLabel.Postal_Code.toString(), Smoke_OFFLoc2ZIP } };

				for (String[] labelWithValue : labelsWithValues) {

					if (ip.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.InstituitonsTab,
							labelWithValue[0], labelWithValue[1])) {
						log(LogStatus.PASS, labelWithValue[0] + " : with value : " + labelWithValue[1] + "  Verified",
								YesNo.No);
					} else {
						sa.assertTrue(false,
								labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified");
						log(LogStatus.FAIL,
								labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified",
								YesNo.Yes);
					}
				}

				if (ip.clickOnShowMoreActionDownArrow("PE", PageName.InstitutionsPage, ShowMoreActionDropDownList.Edit, 20)) {
					log(LogStatus.INFO, "Edit Button", YesNo.No);
					ThreadSleep(2000);
					WebElement ele;
					String value;
					for (String[] labelWithValue : labelsWithValues) {
						ele = ip.getInstitutionPageTextBoxOrRichTextBoxWebElement(environment, mode, labelWithValue[0],
								10);
						if (ele != null) {
							log(LogStatus.INFO, "Label Found : " + labelWithValue[0], YesNo.Yes);
							value = ele.getAttribute("value");
							if (labelWithValue[1].contains(value)) {
								log(LogStatus.PASS,
										"Label : " + labelWithValue[0] + " With Value Verified : " + labelWithValue[1],
										YesNo.No);
							} else {
								sa.assertTrue(false,
										"Label : " + labelWithValue[0] + " With Value Not Verified Actual : " + value
												+ " \t Expected : " + labelWithValue[1]);
								log(LogStatus.FAIL,
										"Label : " + labelWithValue[0] + " With Value Not Verified Actual : " + value
												+ " \t Expected : " + labelWithValue[1],
										YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "Label Not Found : " + labelWithValue[0]);
							log(LogStatus.SKIP, "Label Not Found : " + labelWithValue[0], YesNo.Yes);
						}

					}

				} else {
					sa.assertTrue(false, "Not Able to Click on Edit Button");
					log(LogStatus.SKIP, "Not Able to Click on Edit Button", YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINS2);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINS2, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc068_VerifMailingAddressAtContactDetailpage(String environment, String mode) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		log(LogStatus.INFO, "Login with CRM User", YesNo.No);
		if (ip.clickOnTab(environment, mode, TabName.ContactTab)) {
			log(LogStatus.INFO, "Clicked on Contact Tab", YesNo.No);
			if (cp.clickOnCreatedContact(environment, SmokeC5_FName, SmokeC5_LName)) {
				log(LogStatus.INFO, "Clicked on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName, YesNo.No);

				String[][] labelsWithValues = { { OfficeLocationLabel.Phone.toString(), SmokeINS2_Phone },
						{ OfficeLocationLabel.Fax.toString(), SmokeINS2_Fax },
						{ OfficeLocationLabel.Street.toString(), SmokeINS2_Street },
						{ OfficeLocationLabel.City.toString(), SmokeINS2_City },
						{ OfficeLocationLabel.State.toString(), SmokeINS2_State },
						{ OfficeLocationLabel.Country.toString(), SmokeINS2_Country },
						{ excelLabel.Postal_Code.toString(), SmokeINS2_Postal_Code } };

				for (String[] labelWithValue : labelsWithValues) {

					if (cp.fieldValueVerificationOnContactPage(environment,  TabName.ContactTab, labelWithValue[0],
							labelWithValue[1])) {
						log(LogStatus.PASS, labelWithValue[0] + " : with value : " + labelWithValue[1] + "  Verified",
								YesNo.No);
					} else {
						sa.assertTrue(false,
								labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified");
						log(LogStatus.FAIL,
								labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified",
								YesNo.Yes);
					}
				}

			} else {
				sa.assertTrue(false, "Contact not Found : " + SmokeC5_FName + " " + SmokeC5_LName);
				log(LogStatus.SKIP, "Contact not Found : " + SmokeC5_FName + " " + SmokeC5_LName, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc069_VerifyOfficeLocationsAtContactPageAndUpdationOfMailingAddressAfterSelectingOfficeLocation(
			String environment, String mode) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		log(LogStatus.INFO, "Login with CRM User", YesNo.No);
		if (ip.clickOnTab(environment, mode, TabName.ContactTab)) {
			log(LogStatus.INFO, "Clicked on Contact Tab", YesNo.No);
			if (cp.clickOnCreatedContact(environment, SmokeC5_FName, SmokeC5_LName)) {
				log(LogStatus.INFO, "Clicked on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName, YesNo.No);

				if (cp.clickOnShowMoreActionDownArrow("PE", PageName.ContactPage, ShowMoreActionDropDownList.Edit, 20)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);
					if (cp.ClickOnLookUpAndSelectValueFromLookUpWindow(environment, mode, LookUpIcon.OfficeLocation,
							"Office Location", Smoke_OFFLoc1Name + "," + Smoke_OFFLoc2Name)) {
						log(LogStatus.INFO, "Selected ", YesNo.No);

						if (click(driver, cp.getSaveButton(environment, mode, 10), "Save Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
							ThreadSleep(2000);

							String[][] labelsWithValues = {
									{ OfficeLocationLabel.Phone.toString(), Smoke_OFFLoc1Phone },
									{ OfficeLocationLabel.Fax.toString(), Smoke_OFFLoc1Fax },
									{ OfficeLocationLabel.Street.toString(), Smoke_OFFLoc1Street },
									{ OfficeLocationLabel.City.toString(), Smoke_OFFLoc1City },
									{ OfficeLocationLabel.State.toString(), Smoke_OFFLoc1StateProvince },
									{ OfficeLocationLabel.Country.toString(), Smoke_OFFLoc1Country },
									{ excelLabel.Postal_Code.toString(), Smoke_OFFLoc1ZIP } };

							for (String[] labelWithValue : labelsWithValues) {

								if (cp.fieldValueVerificationOnContactPage(environment,  TabName.ContactTab,
										labelWithValue[0], labelWithValue[1])) {
									log(LogStatus.PASS,
											labelWithValue[0] + " : with value : " + labelWithValue[1] + "  Verified",
											YesNo.No);
								} else {
									sa.assertTrue(false, labelWithValue[0] + " : with value : " + labelWithValue[1]
											+ " Not Verified");
									log(LogStatus.FAIL, labelWithValue[0] + " : with value : " + labelWithValue[1]
											+ " Not Verified", YesNo.Yes);
								}
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Save Button");
							log(LogStatus.FAIL, "Not Able to Click on Save Button", YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Select ");
						log(LogStatus.SKIP, "Not Able to Select ", YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Edit Button");
					log(LogStatus.SKIP, "Not Able to Click on Edit Button", YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Contact not Found : " + SmokeC5_FName + " " + SmokeC5_LName);
				log(LogStatus.SKIP, "Contact not Found : " + SmokeC5_FName + " " + SmokeC5_LName, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc070_UpdatePrimaryOfOfficeLocation1AtContactTransferAcc1DetailPageAndVerifyitsimpact(
			String environment, String mode) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SoftAssert tsa = new SoftAssert();

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		log(LogStatus.INFO, "Login with CRM User", YesNo.No);
		log(LogStatus.INFO, "Going to Enter information about Office Location 1", YesNo.No);
		if (ip.clickOnTab(environment, mode, TabName.OfficeLocations)) {
			log(LogStatus.INFO, "Clicked on office location Tab", YesNo.No);
			if (ip.clickOnCreatedOfficeLocation(environment, mode, Smoke_OFFLoc1Name)) {
				log(LogStatus.INFO, "Clicked on Created office location : " + Smoke_OFFLoc1Name, YesNo.No);

				if (ip.clickOnRelatedList(environment, mode, RecordType.IndividualInvestor, RelatedList.Office_Locations, RelatedTab.Details.toString())) {
					
					log(LogStatus.PASS, "Clicked on details TAB : "+RelatedList.Office_Locations, YesNo.No);
					
					
						String[][] officeLocation1 = {
								{ OfficeLocationLabel.Primary.toString(), Smoke_OFFLoc1UpdatedPrimary } };
							ThreadSleep(2000);
							if (ip.clickOnShowMoreActionDownArrow("PE", PageName.ContactPage, ShowMoreActionDropDownList.Edit, 10)) {
								
								tsa = ip.EnterValueForLabelonOfficeLocation(environment, mode, officeLocation1);
								sa.combineAssertions(tsa);
								switchToDefaultContent(driver);
							}else {
								sa.assertTrue(false, "Not Able to Click on edit button on new window: " + Smoke_OFFLoc1Name);
								log(LogStatus.SKIP, "Not Able to Click on edit button on new window  : " + Smoke_OFFLoc1Name, YesNo.Yes);
							}

				} else {
					sa.assertTrue(false, "Not Able to Click on Related List " + RelatedList.Office_Locations);
					log(LogStatus.FAIL, "Not Able to Click on Related List " + RelatedList.Office_Locations, YesNo.Yes);
				}
				
					

			} else {
				sa.assertTrue(false, "Not Able to Click on Created office location : " + Smoke_OFFLoc1Name);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + Smoke_OFFLoc1Name, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on office location Tab");
			log(LogStatus.SKIP, "Not Able to Click on office location Tab", YesNo.Yes);
		}

		String[] label= {excelLabel.Office_Location_Name.toString(),
				excelLabel.Street.toString(),
				excelLabel.City.toString(),
				"State/Province",excelLabel.Country.toString(),
				excelLabel.Primary.toString()};
		
		log(LogStatus.INFO, "Going to Verify Office Location 1 & 2", YesNo.No);
		
		String[][] locations = {{Smoke_OFFLoc1Name,Smoke_OFFLoc1Street,Smoke_OFFLoc1City,Smoke_OFFLoc1StateProvince,Smoke_OFFLoc1Country,Smoke_OFFLoc1UpdatedPrimary},
				{Smoke_OFFLoc2Name,Smoke_OFFLoc2Street,Smoke_OFFLoc2City,Smoke_OFFLoc2StateProvince,Smoke_OFFLoc2Country,Smoke_OFFLoc2UpdatedPrimary}};
		
		for(String[] location:locations) {
		if (ip.clickOnTab(environment, mode, TabName.OfficeLocations)) {
			log(LogStatus.INFO, "Clicked on office location Tab", YesNo.No);
			if (ip.clickOnCreatedOfficeLocation(environment, mode, location[0])) {
				log(LogStatus.INFO, "Clicked on Created office location : " + location[0], YesNo.No);
	
				if (ip.clickOnRelatedList(environment, mode, RecordType.IndividualInvestor, RelatedList.Office_Locations, RelatedTab.Details.toString())) {
					
					log(LogStatus.PASS, "Clicked on Details TAB : "+RelatedList.Office_Locations, YesNo.No);
					
					

					for(int i=0;i<location.length;i++) {
						if(ip.fieldValueVerificationOnOfficeLocationPage(environment, mode, label[i], location[i])) {
							log(LogStatus.FAIL, "value Verified for : " + location[i], YesNo.Yes);
							
						}else {
							sa.assertTrue(false, "value Not Verified for : " + location[i]);
							log(LogStatus.FAIL, "value Not Verified for : " + location[i], YesNo.Yes);
						}
			
					}
				} else {
					sa.assertTrue(false, "Not Able to Clicked on link : " + RelatedList.Office_Locations);
					log(LogStatus.FAIL, "Not Able to Clicked on link : " + RelatedList.Office_Locations, YesNo.Yes);
				}
	
			} else {
				sa.assertTrue(false, "Not Able to Click on Created office location : " + location[0]);
				log(LogStatus.SKIP, "Not Able to Click on Created office location : " + location[0], YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}
		}

		log(LogStatus.PASS, "Going to Verify Address Information ", YesNo.Yes);
		if (ip.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			log(LogStatus.INFO, "Clicked on Institution Tab", YesNo.No);
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINS2)) {
				log(LogStatus.INFO, "Clicked on Created Institution : " + SmokeINS2, YesNo.No);

				String[][] labelsWithValues = { { OfficeLocationLabel.Phone.toString(), Smoke_OFFLoc1Phone },
						{ OfficeLocationLabel.Fax.toString(), Smoke_OFFLoc1Fax },
						{ OfficeLocationLabel.Street.toString(), Smoke_OFFLoc1Street },
						{ OfficeLocationLabel.City.toString(), Smoke_OFFLoc1City },
						{ OfficeLocationLabel.State.toString(), Smoke_OFFLoc1StateProvince },
						{ OfficeLocationLabel.Country.toString(), Smoke_OFFLoc1Country },
						{ excelLabel.Postal_Code.toString(), Smoke_OFFLoc1ZIP } };

				for (String[] labelWithValue : labelsWithValues) {

					if (ip.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.InstituitonsTab,
							labelWithValue[0], labelWithValue[1])) {
						log(LogStatus.PASS, labelWithValue[0] + " : with value : " + labelWithValue[1] + "  Verified",
								YesNo.No);
					} else {
						sa.assertTrue(false,
								labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified");
						log(LogStatus.FAIL,
								labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified",
								YesNo.Yes);
					}
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINS2);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINS2, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc071_UpdatePrimaryOfficeLocationValueAndVerifyItsImpact(String environment, String mode) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		SoftAssert tsa = new SoftAssert();

		String[][] updatedOfficeLocation1 = {
				{ OfficeLocationLabel.Office_Location_Name.toString(), Smoke_OFFLoc1UpdName },
				{ OfficeLocationLabel.Phone.toString(), Smoke_OFFLoc1UpdPhone },
				{ OfficeLocationLabel.Street.toString(), Smoke_OFFLoc1UpdStreet },
				{ OfficeLocationLabel.Fax.toString(), Smoke_OFFLoc1UpdFax },
				{ OfficeLocationLabel.City.toString(), Smoke_OFFLoc1UpdCity },
				{ OfficeLocationLabel.State_Province.toString(), Smoke_OFFLoc1UpdStateProvince },
				{ OfficeLocationLabel.Country.toString(), Smoke_OFFLoc1UpdCountry },
				{ OfficeLocationLabel.ZIP.toString(), Smoke_OFFLoc1UpdZIP } };

		String[][] labelsWithValues = { { OfficeLocationLabel.Phone.toString(), Smoke_OFFLoc1UpdPhone },
				{ OfficeLocationLabel.Fax.toString(), Smoke_OFFLoc1UpdFax },
				{ OfficeLocationLabel.Street.toString(), Smoke_OFFLoc1UpdStreet },
				{ OfficeLocationLabel.City.toString(), Smoke_OFFLoc1UpdCity },
				{ OfficeLocationLabel.State.toString(), Smoke_OFFLoc1UpdStateProvince },
				{ OfficeLocationLabel.Country.toString(), Smoke_OFFLoc1UpdCountry },
				{ excelLabel.Postal_Code.toString(), Smoke_OFFLoc1UpdZIP } };

		boolean flag=false;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		log(LogStatus.INFO, "Login with CRM User", YesNo.No);
		log(LogStatus.INFO, "Going to Update information about Office Location 1", YesNo.No);
		if (ip.clickOnTab(environment, mode, TabName.OfficeLocations)) {
			log(LogStatus.INFO, "Clicked on office location Tab", YesNo.No);
			if (ip.clickOnCreatedOfficeLocation(environment, mode, Smoke_OFFLoc1Name)) {
				log(LogStatus.INFO, "Clicked on Created office location : " + Smoke_OFFLoc1Name, YesNo.No);

				if (ip.clickOnRelatedList(environment, mode, RecordType.IndividualInvestor, RelatedList.Office_Locations, RelatedTab.Details.toString())) {
					
					log(LogStatus.PASS, "Clicked on detail TAB : "+RelatedList.Office_Locations, YesNo.No);
					
							ThreadSleep(2000);
							if (cp.clickOnShowMoreActionDownArrow("PE", PageName.ContactPage, ShowMoreActionDropDownList.Edit, 10)) {
								log(LogStatus.INFO, "Clicked on edit button", YesNo.No);
							tsa = ip.EnterValueForLabelonOfficeLocation(environment, mode, updatedOfficeLocation1);
							sa.combineAssertions(tsa);
							flag=true;
							}else {
								sa.assertTrue(false, "Not Able to Click on edit button on new window: " + Smoke_OFFLoc1Name);
								log(LogStatus.SKIP, "Not Able to Click on edit button on new window  : " + Smoke_OFFLoc1Name, YesNo.Yes);
							}
						
					
				} else {
					sa.assertTrue(false, "Not Able to Click on Related List " + RelatedList.Office_Locations);
					log(LogStatus.FAIL, "Not Able to Click on Related List " + RelatedList.Office_Locations, YesNo.Yes);
				}

					
				

			} else {
				sa.assertTrue(false, "Not Able to Click on Created office location : " + Smoke_OFFLoc1Name);
				log(LogStatus.SKIP, "Not Able to Click on Created office location : " + Smoke_OFFLoc1Name, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on office location Tab");
			log(LogStatus.SKIP, "Not Able to Click on office location Tab", YesNo.Yes);
		}
		if (flag) {
			
			String[] label= {excelLabel.Office_Location_Name.toString(),
					excelLabel.Street.toString(),
					excelLabel.City.toString(),
					"State/Province",excelLabel.Country.toString(),
					excelLabel.Primary.toString()};
			
			log(LogStatus.INFO, "Going to Verify Office Location 1", YesNo.No);

			String[][] locations = {{Smoke_OFFLoc1UpdName,Smoke_OFFLoc1UpdStreet,Smoke_OFFLoc1UpdCity,Smoke_OFFLoc1UpdStateProvince,Smoke_OFFLoc1UpdCountry,Smoke_OFFLoc1UpdatedPrimary}
				};
			
			for(String[] location:locations) {
			if (ip.clickOnTab(environment, mode, TabName.OfficeLocations)) {
				log(LogStatus.INFO, "Clicked on office location Tab", YesNo.No);
				if (ip.clickOnCreatedOfficeLocation(environment, mode, location[0])) {
					log(LogStatus.INFO, "Clicked on Created office location : " + location[0], YesNo.No);
		
					if (ip.clickOnRelatedList(environment, mode, RecordType.IndividualInvestor, RelatedList.Office_Locations, RelatedTab.Details.toString())) {
						
						log(LogStatus.PASS, "Clicked on Details TAB : "+RelatedList.Office_Locations, YesNo.No);
						
						

						for(int i=0;i<location.length;i++) {
							if(ip.fieldValueVerificationOnOfficeLocationPage(environment, mode, label[i], location[i])) {
								log(LogStatus.FAIL, "value Verified for : " + location[i], YesNo.Yes);
								
							}else {
								sa.assertTrue(false, "value Not Verified for : " + location[i]);
								log(LogStatus.FAIL, "value Not Verified for : " + location[i], YesNo.Yes);
							}
				
						}
					} else {
						sa.assertTrue(false, "Not Able to Clicked on link : " + RelatedList.Office_Locations);
						log(LogStatus.FAIL, "Not Able to Clicked on link : " + RelatedList.Office_Locations, YesNo.Yes);
					}
		
				} else {
					sa.assertTrue(false, "Not Able to Click on Created office location : " + location[0]);
					log(LogStatus.SKIP, "Not Able to Click on Created office location : " + location[0], YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Institution Tab");
				log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
			}
			}

			log(LogStatus.INFO, "Going to Verify Address Information on INstitution ", YesNo.No);
			if (ip.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
				log(LogStatus.INFO, "Clicked on Institution Tab", YesNo.No);
				if (ip.clickOnCreatedInstitution(environment, mode, SmokeINS2)) {
					log(LogStatus.INFO, "Clicked on Created Institution : " + SmokeINS2, YesNo.No);

					for (String[] labelWithValue : labelsWithValues) {

						if (ip.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.InstituitonsTab,
								labelWithValue[0], labelWithValue[1])) {
							log(LogStatus.PASS, labelWithValue[0] + " : with value : " + labelWithValue[1] + "  Verified",
									YesNo.No);
						} else {
							sa.assertTrue(false,
									labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified");
							log(LogStatus.FAIL,
									labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified",
									YesNo.Yes);
						}
					}

				} else {
					sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINS2);
					log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINS2, YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Institution Tab");
				log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
			}

			refresh(driver);
			log(LogStatus.INFO, "Going to Verify Address Information on Conatct ", YesNo.No);
			if (ip.clickOnTab(environment, mode, TabName.ContactTab)) {
				log(LogStatus.INFO, "Clicked on Contact Tab", YesNo.No);
				if (cp.clickOnCreatedContact(environment,  SmokeC5_FName, SmokeC5_LName)) {
					log(LogStatus.INFO, "Clicked on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName, YesNo.No);

					for (String[] labelWithValue : labelsWithValues) {

						if (cp.fieldValueVerificationOnContactPage(environment,  TabName.ContactTab, labelWithValue[0],
								labelWithValue[1])) {
							log(LogStatus.PASS, labelWithValue[0] + " : with value : " + labelWithValue[1] + "  Verified",
									YesNo.No);
						} else {
							sa.assertTrue(false,
									labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified");
							log(LogStatus.FAIL,
									labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified",
									YesNo.Yes);
						}
					}

				} else {
					sa.assertTrue(false, "Contact not Found : " + SmokeC5_FName + " " + SmokeC5_LName);
					log(LogStatus.SKIP, "Contact not Found : " + SmokeC5_FName + " " + SmokeC5_LName, YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Contact Tab");
				log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
			}
			
		}

		

		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc072_CreateActivitiesWithContact(String environment, String mode){

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		SoftAssert tsa = new SoftAssert();
		String startDate = previousOrForwardDate(1, "M/d/yyyy");
		String endDate = previousOrForwardDate(2, "M/d/yyyy");

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		log(LogStatus.INFO, "Login with CRM User",YesNo.No);
		System.err.println("Date : "+startDate);

		String[][] taskLabelsWithValues= {{ActivityRelatedLabel.Subject.toString(),Smoke_NewTask1Subject}};

		String contactName=SmokeC5_FName+" "+SmokeC5_LName;
		String task = Smoke_NewTask1Subject;
		String[][] event1 = {{PageLabel.Subject.toString(),task},{PageLabel.Name.toString(),contactName}};

		if (gp.clickOnGlobalActionAndEnterValue("", GlobalActionItem.New_Task, event1)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
			if (click(driver, gp.getSaveButtonForEvent("", 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}

		String[][] eventLabelsWithValues= {{ActivityRelatedLabel.Subject.toString(),Smoke_NewEvent1Subject},
				{ActivityRelatedLabel.Related_To.toString(),"Institution"+"<break>"+SmokeINS2},
				{ActivityRelatedLabel.Start.toString(),startDate},
				{ActivityRelatedLabel.End.toString(),endDate}};


		task = Smoke_NewEvent1Subject;
		String[][] event2 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Name.toString(),contactName},
				{PageLabel.Start_Date.toString(),startDate},
				{PageLabel.End_Date.toString(),endDate}};

		if (gp.clickOnGlobalActionAndEnterValue("", GlobalActionItem.New_Event, event2)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);

			gp.enterValueOnRelatedTo("", PageName.GlobalActtion_TaskPOpUp, TabName.InstituitonsTab, SmokeINS2);
			if (click(driver, gp.getSaveButtonForEvent("", 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		


			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}


		String[][] CallLogLabelsWithValues= {{ActivityRelatedLabel.Subject.toString(),Smoke_CallLog1Subject},
				{ActivityRelatedLabel.Related_To.toString(),"Fund"+"<break>"+Smoke_Fund1},
				/*{ActivityRelatedLabel.Due_Date.toString(),startDate}*/};


		task = Smoke_CallLog1Subject;
		String[][] event3 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),startDate},
				{PageLabel.Name.toString(),contactName}};

		if (gp.clickOnGlobalActionAndEnterValue("", GlobalActionItem.Log_a_Call, event3)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
			gp.enterValueOnRelatedTo("", PageName.GlobalActtion_TaskPOpUp, TabName.Object3Tab, Smoke_Fund1);
			if (click(driver, gp.getSaveButtonForEvent("", 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}





		if (cp.clickOnTab(environment, mode, TabName.ContactTab)) {
			log(LogStatus.INFO, "Clicked on Contact Tab",YesNo.No);
			if (cp.clickOnCreatedContact(environment,  SmokeC5_FName, SmokeC5_LName)) {
				log(LogStatus.INFO, "Clicked on Created Contact : "+SmokeC5_FName+" "+SmokeC5_LName,YesNo.No);	
				if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Open_Activities, null)) {
					log(LogStatus.INFO, "Clicked on Open Activities",YesNo.No);	
					ThreadSleep(2000);
					if (cp.verifyCreatedOpenActivity(environment, mode, Smoke_NewTask1Subject)) {
						log(LogStatus.INFO, "Activity verified: "+Smoke_NewTask1Subject,YesNo.No);	
					} else {
						sa.assertTrue(false, "Activity not verified: "+Smoke_NewTask1Subject);
						log(LogStatus.SKIP, "Activity not verified: "+Smoke_NewTask1Subject,YesNo.Yes);
					}

					if (cp.verifyCreatedOpenActivity(environment, mode, Smoke_NewEvent1Subject)) {
						log(LogStatus.INFO, "Activity verified: "+Smoke_NewEvent1Subject,YesNo.No);	
					} else {
						sa.assertTrue(false, "Activity not verified: "+Smoke_NewEvent1Subject);
						log(LogStatus.SKIP, "Activity not verified: "+Smoke_NewEvent1Subject,YesNo.Yes);
					}

					if (cp.verifyCreatedActivityHistory(environment, mode, Smoke_CallLog1Subject)) {
						log(LogStatus.INFO, "Activity verified: "+Smoke_CallLog1Subject,YesNo.No);	
					} else {
						sa.assertTrue(false, "Activity not verified: "+Smoke_CallLog1Subject);
						log(LogStatus.SKIP, "Activity not verified: "+Smoke_CallLog1Subject,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not on Open Activities");
					log(LogStatus.SKIP, "Not on Open Activities",YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Contact not Found : "+SmokeC5_FName+" "+SmokeC5_LName);
				log(LogStatus.SKIP, "Contact not Found : "+SmokeC5_FName+" "+SmokeC5_LName,YesNo.Yes);	
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab",YesNo.Yes);	
		}

		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");	
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc073_VerifyContactTransferAB(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer np = new NavatarSetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Contact Tab");
		if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
			if (cp.clickOnCreatedContact(environment,  SmokeC5_FName, SmokeC5_LName)) {
				log(LogStatus.INFO, "Click on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName, YesNo.No);

				// Azhar Start
				if (cp.clickOnShowMoreActionDownArrow("PE", PageName.InstitutionsPage, ShowMoreActionDropDownList.Contact_Transfer, 10)) {
					log(LogStatus.INFO, "Clicked on Contact Transfer for Cancel Button", YesNo.No);	
					ThreadSleep(2000);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 10, np.getnavatarSetUpTabFrame_Lighting(environment, 10));
					} 
					if (click(driver, cp.getContactTransferCancelButton(environment, mode, 10), "contact Transfer Cancel Button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);		
					} else {
						sa.assertTrue(false, "Not Able to Click on Cancel Button");
						log(LogStatus.SKIP, "Not Able to Click on  Cancel Button", YesNo.Yes);	
					}
				}else{
					sa.assertTrue(false, "Not Able to Click on Contact Transfer for Cancel Button");
					log(LogStatus.SKIP, "Not Able to Click on Contact Transfer for Cancel Button", YesNo.Yes);	
				}
				// Azhar End
				ThreadSleep(5000);
				switchToDefaultContent(driver);
				if (cp.clickOnShowMoreActionDownArrow("PE", PageName.InstitutionsPage, ShowMoreActionDropDownList.Contact_Transfer, 10)) {
					log(LogStatus.INFO, "Clicked on Contact Transfer", YesNo.No);	
					
					if (cp.verifyContactTransferUIonContactPage(environment, mode,SmokeC5_FName+" "+SmokeC5_LName, SmokeINS2, 10)) {
						log(LogStatus.PASS, "Contact Transfer UI Verify", YesNo.Yes);
					} else {
						sa.assertTrue(false, "Contact Transfer UI Not Verify");
						log(LogStatus.FAIL, "Contact Transfer UI Not Verify", YesNo.Yes);
					}
					// Azhar Start
					if (cp.enteringValueforLegalNameOnContactTransferPage(environment, mode, SmokeINDINV2, AddressAction.CrossIcon, 10)) {
						log(LogStatus.PASS, "Click on Cross Icon", YesNo.No);
					} else {
						sa.assertTrue(false, "Not Able to Click on Cross Icon");
						log(LogStatus.FAIL, "Not Able to Click on Cross Icon", YesNo.Yes);
					}
					// Azhar End
					System.err.println(">>>>>>>>>>><<<<<<<<<<<<<<<<");
					ThreadSleep(10000);
					if (cp.enteringValueforLegalNameOnContactTransferPage(environment, mode, SmokeINDINV2, AddressAction.Retain, 10)) {
						log(LogStatus.PASS, "Able to Transfer Contact", YesNo.No);
					} else {
						sa.assertTrue(false, "Not Able to Transfer Contact");
						log(LogStatus.FAIL, "Not Able to Transfer Contact", YesNo.Yes);
					}
					
					switchToDefaultContent(driver);
					refresh(driver);
					String[][] labelsWithValues = { { excelLabel.Name.toString(), SmokeC5_FName+" "+SmokeC5_LName },
							{ excelLabel.Legal_Name.toString(), SmokeINDINV2 },
							{ OfficeLocationLabel.Phone.toString(), Smoke_OFFLoc1UpdPhone },
							{ OfficeLocationLabel.Fax.toString(), Smoke_OFFLoc1UpdFax },
							{ OfficeLocationLabel.Street.toString(), Smoke_OFFLoc1UpdStreet },
							{ OfficeLocationLabel.City.toString(), Smoke_OFFLoc1UpdCity },
							{ OfficeLocationLabel.State.toString(), Smoke_OFFLoc1UpdStateProvince },
							{ OfficeLocationLabel.Country.toString(), Smoke_OFFLoc1UpdCountry },
							{ excelLabel.Postal_Code.toString(), Smoke_OFFLoc1UpdZIP } };
					
							for (String[] labelWithValue : labelsWithValues) {

								if (cp.fieldValueVerificationOnContactPage(environment,  TabName.InstituitonsTab,
										labelWithValue[0], labelWithValue[1])) {
									log(LogStatus.PASS, labelWithValue[0] + " : with value : " + labelWithValue[1] + "  Verified",
											YesNo.No);
								} else {
									sa.assertTrue(false,
											labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified");
									log(LogStatus.FAIL,
											labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified",
											YesNo.Yes);
								}
							}
							
					if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Affiliations, RelatedTab.Affiliations.toString())) {
						log(LogStatus.INFO, "Click on Affiliations", YesNo.Yes);

						if (cp.verifyOpenActivityRelatedList(environment, mode, TabName.ContactTab,
								Smoke_NewTask1Subject, SmokeINS2, null)) {
							log(LogStatus.INFO, "Open Activity Grid  Verified For " + Smoke_NewTask1Subject, YesNo.No);
						} else {
							sa.assertTrue(false, "Open Activity Grid Not Verified For " + Smoke_NewTask1Subject);
							log(LogStatus.FAIL, "Open Activity Grid Not Verified For " + Smoke_NewTask1Subject,
									YesNo.Yes);
						}

						if (cp.verifyOpenActivityRelatedList(environment, mode, TabName.ContactTab,
								Smoke_NewEvent1Subject, SmokeINS2, null)) {
							log(LogStatus.INFO, "Open Activity Grid Verified For " + Smoke_NewEvent1Subject, YesNo.No);
						} else {
							sa.assertTrue(false, "Open Activity Grid Not Verified For " + Smoke_NewEvent1Subject);
							log(LogStatus.FAIL, "Open Activity  Grid Not Verified For " + Smoke_NewEvent1Subject,
									YesNo.Yes);
						}

						if (cp.verifyActivityHistoryRelatedList(environment, mode, TabName.ContactTab,
								Smoke_CallLog1Subject, Smoke_Fund1, null)) {
							log(LogStatus.INFO, "Activity History Grid Verified For " + Smoke_CallLog1Subject,
									YesNo.No);
						} else {
							sa.assertTrue(false, "Activity History Grid Not Verified For " + Smoke_CallLog1Subject);
							log(LogStatus.FAIL, "Activity History Grid Not Verified For " + Smoke_CallLog1Subject,
									YesNo.Yes);
						}

						if (cp.verifyAffliationRelatedList(environment, mode, TabName.ContactTab, SmokeINS2)) {
							log(LogStatus.PASS, "Affialition Grid Verified For " + SmokeINS2, YesNo.Yes);
						} else {
							sa.assertTrue(false, "Affialition Grid Not Verified For " + SmokeINS2);
							log(LogStatus.FAIL, "Affialition Grid Not Verified For " + SmokeINS2, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Affiliations");
						log(LogStatus.SKIP, "Not Able to Click on Affiliations", YesNo.Yes);
					}

				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Transfer");
					log(LogStatus.SKIP, "Not Able to Click on Contact Transfer", YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName,
						YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc074_VerifyActivitiesAtOldInstitutionContactTransferAcc1(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Institution Tab");
		if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINS2)) {
				log(LogStatus.INFO, "Click on Created Inst : " + SmokeINS2, YesNo.No);

				if (ip.verifyOpenActivityRelatedList(environment, mode, TabName.InstituitonsTab, Smoke_NewTask1Subject,
						SmokeINS2, SmokeC5_FName + " " + SmokeC5_LName)) {
					log(LogStatus.INFO, "Open Activity Grid  Verified For " + Smoke_NewTask1Subject, YesNo.No);
				} else {
					sa.assertTrue(false, "Open Activity Grid Not Verified For " + Smoke_NewTask1Subject);
					log(LogStatus.FAIL, "Open Activity Grid Not Verified For " + Smoke_NewTask1Subject, YesNo.Yes);
				}

				if (ip.verifyOpenActivityRelatedList(environment, mode, TabName.InstituitonsTab, Smoke_NewEvent1Subject,
						SmokeINS2, SmokeC5_FName + " " + SmokeC5_LName)) {
					log(LogStatus.INFO, "Open Activity Grid Verified For " + Smoke_NewEvent1Subject, YesNo.No);
				} else {
					sa.assertTrue(false, "Open Activity Grid Not Verified For " + Smoke_NewEvent1Subject);
					log(LogStatus.FAIL, "Open Activity  Grid Not Verified For " + Smoke_NewEvent1Subject, YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINS2);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINS2, YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc075_VerifyActivitiesAtNewInstitutionContactTransferAcc2(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Institution Tab");
		if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINDINV2)) {
				log(LogStatus.INFO, "Click on Created Inst : " + SmokeINDINV2, YesNo.No);
						
								if (ip.verifyOpenActivityRelatedList(environment, mode,TabName.InstituitonsTab, Smoke_NewTask1Subject, SmokeINS2, SmokeC5_FName + " " + SmokeC5_LName)) {
									log(LogStatus.INFO, "Open Activity Grid  Verified For "+Smoke_NewTask1Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewTask1Subject);
									log(LogStatus.FAIL, "Open Activity Grid Not Verified For "+Smoke_NewTask1Subject, YesNo.Yes);
								}
								
								if (ip.verifyOpenActivityRelatedList(environment, mode,TabName.InstituitonsTab, Smoke_NewEvent1Subject, SmokeINS2, SmokeC5_FName + " " + SmokeC5_LName)) {
									log(LogStatus.INFO, "Open Activity Grid Verified For "+Smoke_NewEvent1Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewEvent1Subject);
									log(LogStatus.FAIL, "Open Activity  Grid Not Verified For "+Smoke_NewEvent1Subject, YesNo.Yes);
								}
								
								if (ip.verifyActivityHistoryRelatedList(environment, mode,TabName.ContactTab, Smoke_CallLog1Subject, Smoke_Fund1, null)) {
									log(LogStatus.INFO, "Activity History Grid Verified For "+Smoke_CallLog1Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Activity History Grid Not Verified For "+Smoke_CallLog1Subject);
									log(LogStatus.FAIL, "Activity History Grid Not Verified For "+Smoke_CallLog1Subject, YesNo.Yes);
								}
								
							
						
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINDINV2);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINDINV2,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc076_VerifyContactTransferBC(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Contact Tab");
		if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
			if (cp.clickOnCreatedContact(environment,  SmokeC5_FName, SmokeC5_LName)) {
				log(LogStatus.INFO, "Click on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName, YesNo.No);

				
				if (cp.clickOnShowMoreActionDownArrow("PE", PageName.ContactPage, ShowMoreActionDropDownList.Contact_Transfer, 10)) {
					log(LogStatus.INFO, "Clicked on Contact Transfer", YesNo.No);	
					
					if (cp.verifyContactTransferUIonContactPage(environment, mode, SmokeC5_FName+" "+SmokeC5_LName, SmokeINDINV2, 10)) {
						log(LogStatus.PASS, "Contact Transfer UI Verify", YesNo.Yes);
					} else {
						sa.assertTrue(false, "Contact Transfer UI Not Verify");
						log(LogStatus.FAIL, "Contact Transfer UI Not Verify", YesNo.Yes);
					}
					
					if (cp.enteringValueforLegalNameOnContactTransferPage(environment, mode, SmokeINS3, AddressAction.Clear, 10)) {
						log(LogStatus.PASS, "Able to Transfer Contact", YesNo.No);
					} else {
						sa.assertTrue(false, "Not Able to Transfer Contact");
						log(LogStatus.FAIL, "Not Able to Transfer Contact", YesNo.Yes);
					}
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					refresh(driver);
					String[][] contactLabelWithValue = { { excelLabel.Name.toString(), SmokeC5_FName+" "+SmokeC5_LName },
							{ excelLabel.Legal_Name.toString(), SmokeINS3 } };
					
							for (String[] labelWithValue : contactLabelWithValue) {

								if (cp.fieldValueVerificationOnContactPage(environment,  TabName.InstituitonsTab,
										labelWithValue[0], labelWithValue[1])) {
									log(LogStatus.PASS, labelWithValue[0] + " : with value : " + labelWithValue[1] + "  Verified",
											YesNo.No);
								} else {
									sa.assertTrue(false,
											labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified");
									log(LogStatus.FAIL,
											labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified",
											YesNo.Yes);
								}
							}
							
							String[][] labelsWithValues = {{ OfficeLocationLabel.Phone.toString(), Smoke_OFFLoc1UpdPhone },
									{ OfficeLocationLabel.Fax.toString(), Smoke_OFFLoc1UpdFax },
									{ OfficeLocationLabel.Street.toString(), Smoke_OFFLoc1UpdStreet },
									{ OfficeLocationLabel.City.toString(), Smoke_OFFLoc1UpdCity },
									{ OfficeLocationLabel.State.toString(), Smoke_OFFLoc1UpdStateProvince },
									{ OfficeLocationLabel.Country.toString(), Smoke_OFFLoc1UpdCountry },
									{ excelLabel.Postal_Code.toString(), Smoke_OFFLoc1UpdZIP } };
							
									for (String[] labelWithValue : labelsWithValues) {

										if (!cp.fieldValueVerificationOnContactPage(environment, TabName.InstituitonsTab,
												labelWithValue[0], labelWithValue[1])) {
											log(LogStatus.PASS, labelWithValue[0] + " : with value : " + labelWithValue[1] + "  not present",
													YesNo.No);
										} else {
											sa.assertTrue(false,
													labelWithValue[0] + " : with value : " + labelWithValue[1] + " is present");
											log(LogStatus.FAIL,
													labelWithValue[0] + " : with value : " + labelWithValue[1] + " is present",
													YesNo.Yes);
										}
									}
							
							if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Affiliations, RelatedTab.Affiliations.toString())) {
								log(LogStatus.INFO, "Click on Affiliations", YesNo.Yes);	
								
								
								if (cp.verifyOpenActivityRelatedList(environment, mode,TabName.ContactTab, Smoke_NewTask1Subject, SmokeINS2, null)) {
									log(LogStatus.INFO, "Open Activity Grid  Verified For "+Smoke_NewTask1Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewTask1Subject);
									log(LogStatus.FAIL, "Open Activity Grid Not Verified For "+Smoke_NewTask1Subject, YesNo.Yes);
								}
								
								if (cp.verifyOpenActivityRelatedList(environment, mode,TabName.ContactTab, Smoke_NewEvent1Subject, SmokeINS2, null)) {
									log(LogStatus.INFO, "Open Activity Grid Verified For "+Smoke_NewEvent1Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewEvent1Subject);
									log(LogStatus.FAIL, "Open Activity  Grid Not Verified For "+Smoke_NewEvent1Subject, YesNo.Yes);
								}
								
								if (cp.verifyActivityHistoryRelatedList(environment, mode,TabName.ContactTab, Smoke_CallLog1Subject, Smoke_Fund1, null)) {
									log(LogStatus.INFO, "Activity History Grid Verified For "+Smoke_CallLog1Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Activity History Grid Not Verified For "+Smoke_CallLog1Subject);
									log(LogStatus.FAIL, "Activity History Grid Not Verified For "+Smoke_CallLog1Subject, YesNo.Yes);
								}
								
								
									if (cp.verifyAffliationRelatedList(environment, mode,TabName.ContactTab, SmokeINS2)) {
										log(LogStatus.PASS, "Affialition Grid Verified For "+SmokeINS2, YesNo.Yes);	
									} else {
										sa.assertTrue(false, "Affialition Grid Not Verified For "+SmokeINS2);
										log(LogStatus.FAIL, "Affialition Grid Not Verified For "+SmokeINS2, YesNo.Yes);
									}
									
									if (cp.verifyAffliationRelatedList(environment, mode,TabName.ContactTab, SmokeINDINV2)) {
										log(LogStatus.PASS, "Affialition Grid Verified For "+SmokeINDINV2, YesNo.Yes);	
									} else {
										sa.assertTrue(false, "Affialition Grid Not Verified For "+SmokeINDINV2);
										log(LogStatus.FAIL, "Affialition Grid Not Verified For "+SmokeINDINV2, YesNo.Yes);
									}
									
								
								
								
							} else {
								sa.assertTrue(false, "Not Able to Click on Affiliations");
								log(LogStatus.SKIP, "Not Able to Click on Affiliations", YesNo.Yes);
							}


				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Transfer");
					log(LogStatus.SKIP, "Not Able to Click on Contact Transfer", YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + SmokeC5_FName + " " + SmokeC5_LName,
						YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}
		ThreadSleep(10000);
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc077_VerifyActivitiesAtOldInstitutionContactTransferAcc1(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Institution Tab");
		if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINS2)) {
				log(LogStatus.INFO, "Click on Created Inst : " + SmokeINS2, YesNo.No);
							
							
								if (ip.verifyOpenActivityRelatedList(environment, mode,TabName.InstituitonsTab, Smoke_NewTask1Subject, SmokeINS2, SmokeC5_FName + " " + SmokeC5_LName)) {
									log(LogStatus.INFO, "Open Activity Grid  Verified For "+Smoke_NewTask1Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewTask1Subject);
									log(LogStatus.FAIL, "Open Activity Grid Not Verified For "+Smoke_NewTask1Subject, YesNo.Yes);
								}
								
								if (ip.verifyOpenActivityRelatedList(environment, mode,TabName.InstituitonsTab, Smoke_NewEvent1Subject, SmokeINS2, SmokeC5_FName + " " + SmokeC5_LName)) {
									log(LogStatus.INFO, "Open Activity Grid Verified For "+Smoke_NewEvent1Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewEvent1Subject);
									log(LogStatus.FAIL, "Open Activity  Grid Not Verified For "+Smoke_NewEvent1Subject, YesNo.Yes);
								}
								
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINS2);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINS2,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc078_VerifyActivitiesAtOldInstitutionContactTransferAcc2(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Institution Tab");
		if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINDINV2)) {
				log(LogStatus.INFO, "Click on Created Inst : " + SmokeINDINV2, YesNo.No);
							
//							if (ip.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Activities, null)) {
//								log(LogStatus.INFO, "Click on Related Tab", YesNo.No);	
								if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									windowScrollYAxis(driver, 0, 2800);
									log(LogStatus.INFO, "Scroll >>>>>", YesNo.No);	
								}
								ThreadSleep(2000);
//								if (ip.verifyNoDataAtOpenActivitiesSection(environment, mode, TabName.InstituitonsTab, 10)) {
//									log(LogStatus.INFO, "Open Activities Grid Verified For No Records/No Next Activity Msg", YesNo.No);	
//								} else {
//									sa.assertTrue(false, "Open Activities Grid Not Verified For No Records/No Next Activity Msg");
//									log(LogStatus.FAIL, "Open Activities Grid Not Verified For No Records/No Next Activity Msg", YesNo.Yes);
//								}
								
//								if (ip.verifyNoDataAtActivitiesSection(environment, mode, TabName.InstituitonsTab, 10)) {
//									log(LogStatus.INFO, " Activities Grid Verified For No Records/No Activities Msg", YesNo.No);	
//								} else {
//									sa.assertTrue(false, "Activities Grid Not Verified For No Records/No Activities Msg");
//									log(LogStatus.FAIL, "Activities Grid Not Verified For No Records/No Activities Msg", YesNo.Yes);
//								}
								
								if (ip.verifyNoDataAtActivityHistorySection(environment, mode, TabName.InstituitonsTab, 10)) {
										log(LogStatus.INFO, "Activity History Section Verified For No Records/No Activities Msg", YesNo.No);	
									} else {
										sa.assertTrue(false, "Activity History Section Not Verified For No Records/No Activities Msg");
										log(LogStatus.FAIL, "Activity History Section Not Verified For No Records/No Activities Msg", YesNo.Yes);
									}	
								
								
								
//							} else {
//								sa.assertTrue(false, "Not Able to Click on Related Tab");
//								log(LogStatus.SKIP, "Not Able to Click on Related Tab", YesNo.Yes);
//							}


				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINDINV2);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINDINV2,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc079_VerifyActivitiesAtNewInstitutionContactTransferAcc3(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Institution Tab");
		if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINS3)) {
				log(LogStatus.INFO, "Click on Created Inst : " + SmokeINS3, YesNo.No);
							
							if (ip.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Open_Activities, null)) {
								log(LogStatus.INFO, "Click on Open Activities", YesNo.No);
								if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									windowScrollYAxis(driver, 0, 2800);
									log(LogStatus.INFO, "Scroll >>>>>", YesNo.No);	
								}
								ThreadSleep(2000);

								
//								if (ip.verifyNoDataAtActivitiesSection(environment, mode, TabName.InstituitonsTab, 10)) {
//									log(LogStatus.INFO, " Activities Grid Verified For No Records/No Activities Msg", YesNo.No);	
//								} else {
//									sa.assertTrue(false, "Activities Grid Not Verified For No Records/No Activities Msg");
//									log(LogStatus.FAIL, "Activities Grid Not Verified For No Records/No Activities Msg", YesNo.Yes);
//								}

								if (ip.verifyOpenActivityRelatedList(environment, mode,TabName.InstituitonsTab, Smoke_NewTask1Subject, SmokeINS2, SmokeC5_FName + " " + SmokeC5_LName)) {
									log(LogStatus.INFO, "Open Activity Grid  Verified For "+Smoke_NewTask1Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewTask1Subject);
									log(LogStatus.FAIL, "Open Activity Grid Not Verified For "+Smoke_NewTask1Subject, YesNo.Yes);
								}
								
								if (ip.verifyOpenActivityRelatedList(environment, mode,TabName.InstituitonsTab, Smoke_NewEvent1Subject, SmokeINS2, SmokeC5_FName + " " + SmokeC5_LName)) {
									log(LogStatus.INFO, "Open Activity Grid Verified For "+Smoke_NewEvent1Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewEvent1Subject);
									log(LogStatus.FAIL, "Open Activity  Grid Not Verified For "+Smoke_NewEvent1Subject, YesNo.Yes);
								}
								

								if (ip.verifyActivityHistoryRelatedList(environment, mode, TabName.InstituitonsTab, Smoke_CallLog1Subject, Smoke_Fund1, SmokeC5_FName + " " + SmokeC5_LName)) {
									log(LogStatus.PASS, "Activitiy History Grid Verified For "+Smoke_CallLog1Subject, YesNo.Yes);	
								} else {
									sa.assertTrue(false, "Activitiy History Grid Not Verified For "+Smoke_CallLog1Subject);
									log(LogStatus.FAIL, "Activitiy History Grid Not Verified For "+Smoke_CallLog1Subject, YesNo.Yes);
								}
								
							} else {
								sa.assertTrue(false, "Not Able to Click on Open Activities");
								log(LogStatus.SKIP, "Not Able to Click on Open Activities", YesNo.Yes);
							}


				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINS3);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINS3,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc080_ChangeContactTransferSetting(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactTransferTabBusinessLayer ctt = new ContactTransferTabBusinessLayer(driver);
		String keepActivitiesDefaultValue = "Old and New Institutions";
		String selectIncludeActivitiesValue="Contact, Institution and Custom Object";
		lp.CRMLogin(superAdminUserName, adminPassword);
		appLog.info("Login with User");
		if (bp.clickOnTab(environment, mode, TabName.NavatarSetup)) {
			appLog.info("Clicked on Navatar Set Up Tab");

			if (ctt.clickOnNavatarSetupSideMenusTab(environment,  NavatarSetupSideMenuTab.ContactTransfer)) {
				appLog.error("Clicked on Contact Transfer Tab");
				ThreadSleep(1000);
				
				if (click(driver, ctt.getEditButtonforNavatarSetUpSideMenuTab(environment, 
						NavatarSetupSideMenuTab.ContactTransfer, 10), "Edit Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);
					ThreadSleep(1000);
					
					if (selectVisibleTextFromDropDown(driver,
							ctt.getKeepActivitiesAtSelectList(environment, mode, EditViewMode.Edit, 10),
							keepActivitiesDefaultValue, keepActivitiesDefaultValue)) {
						log(LogStatus.INFO, "Selected Include Activities related to : " + keepActivitiesDefaultValue,
								YesNo.No);
						ThreadSleep(1000);
						if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(environment, 
								NavatarSetupSideMenuTab.ContactTransfer, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
							ThreadSleep(10000);
							log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
							ThreadSleep(1000);
								SoftAssert tsa = ctt.verifyingContactTransferTab(environment, mode, EditViewMode.View,
										CheckBox.Checked, keepActivitiesDefaultValue, selectIncludeActivitiesValue);
								sa.combineAssertions(tsa);
							
						} else {
							sa.assertTrue(false, "Not Able to Click on Save Button");
							log(LogStatus.SKIP, "Not Able to Click on Save Button", YesNo.Yes);
						}
						
					} else {
						sa.assertTrue(false,
								"Not Able to Select Include Activities related to : " + keepActivitiesDefaultValue);
						log(LogStatus.SKIP,
								"Not Able to Select Include Activities related to : " + keepActivitiesDefaultValue,
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
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc081_CreateActivitiesWithContact(String environment, String mode){

			LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
			ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
			GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);

			lp.CRMLogin(crmUser1EmailID, adminPassword);
			SoftAssert tsa = new SoftAssert();
			String startDate = previousOrForwardDate(1, "M/d/yyyy");
			String endDate = previousOrForwardDate(2, "M/d/yyyy");
			
			System.err.println("Date : "+startDate);
			
			String[][] taskLabelsWithValues= {{ActivityRelatedLabel.Subject.toString(),Smoke_NewTask2Subject}};
			
			String contactName=SmokeC6_FName+" "+SmokeC6_LName;
			String task = Smoke_NewTask2Subject;
			String[][] event1 = {{PageLabel.Subject.toString(),task},{PageLabel.Name.toString(),contactName}};

			if (gp.clickOnGlobalActionAndEnterValue("", GlobalActionItem.New_Task, event1)) {
				log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
				if (click(driver, gp.getSaveButtonForEvent("", 10), "Save Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
				}else {
					sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
				}
			} else {
				sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
				log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
			}
			
			String[][] eventLabelsWithValues= {{ActivityRelatedLabel.Subject.toString(),Smoke_NewEvent2Subject},
												{ActivityRelatedLabel.Related_To.toString(),"Institution"+"<break>"+Smoke_NewEvent2RelatedTo},
												{ActivityRelatedLabel.Start.toString(),startDate},
												{ActivityRelatedLabel.End.toString(),endDate}};
			
			 task = Smoke_NewEvent2Subject;
				String[][] event2 = {{PageLabel.Subject.toString(),task},
						{PageLabel.Name.toString(),contactName},
						{PageLabel.Start_Date.toString(),startDate},
						{PageLabel.End_Date.toString(),endDate}};

				if (gp.clickOnGlobalActionAndEnterValue("", GlobalActionItem.New_Event, event2)) {
					log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
					
					gp.enterValueOnRelatedTo("", PageName.GlobalActtion_TaskPOpUp, TabName.InstituitonsTab, SmokeINS4);
						if (click(driver, gp.getSaveButtonForEvent("", 10), "Save Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
					
						
					}else {
						sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
					}
				} else {
					sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
					log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
				}
				
			
			String[][] CallLogLabelsWithValues= {{ActivityRelatedLabel.Subject.toString(),Smoke_CallLog2Subject},
												{ActivityRelatedLabel.Related_To.toString(),"Fund"+"<break>"+Smoke_CallLog2RelatedTo},
												/*{ActivityRelatedLabel.Due_Date.toString(),startDate}*/};
			
			 task = Smoke_CallLog2Subject;
				String[][] event3 = {{PageLabel.Subject.toString(),task},
						{PageLabel.Due_Date.toString(),startDate},
						{PageLabel.Name.toString(),contactName}};

				if (gp.clickOnGlobalActionAndEnterValue("", GlobalActionItem.Log_a_Call, event3)) {
					log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
					gp.enterValueOnRelatedTo("", PageName.GlobalActtion_TaskPOpUp, TabName.FundsTab, Smoke_Fund2);
					if (click(driver, gp.getSaveButtonForEvent("", 10), "Save Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
					}else {
						sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
					}
				} else {
					sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
					log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
				}
				
				
	
			
		
			log(LogStatus.INFO, "Login with CRM User",YesNo.No);
				if (cp.clickOnTab(environment, mode, TabName.ContactTab)) {
					log(LogStatus.INFO, "Clicked on Contact Tab",YesNo.No);
					if (cp.clickOnCreatedContact(environment,  SmokeC6_FName, SmokeC6_LName)) {
						log(LogStatus.INFO, "Clicked on Created Contact : "+SmokeC6_FName+" "+SmokeC6_LName,YesNo.No);	
						
						if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Open_Activities, null)) {
							log(LogStatus.INFO, "Clicked on Open Activities",YesNo.No);	
							
							ThreadSleep(2000);
							if (cp.verifyCreatedOpenActivity(environment, mode, Smoke_NewTask2Subject)) {
								log(LogStatus.INFO, "Activity verified: "+Smoke_NewTask2Subject,YesNo.No);	
							} else {
								sa.assertTrue(false, "Activity not verified: "+Smoke_NewTask2Subject);
								log(LogStatus.SKIP, "Activity not verified: "+Smoke_NewTask2Subject,YesNo.Yes);
							}
							
							if (cp.verifyCreatedOpenActivity(environment, mode, Smoke_NewEvent2Subject)) {
								log(LogStatus.INFO, "Activity verified: "+Smoke_NewEvent2Subject,YesNo.No);	
							} else {
								sa.assertTrue(false, "Activity not verified: "+Smoke_NewEvent2Subject);
								log(LogStatus.SKIP, "Activity not verified: "+Smoke_NewEvent2Subject,YesNo.Yes);
							}
							
							if (cp.verifyCreatedActivityHistory(environment, mode, Smoke_CallLog2Subject)) {
								log(LogStatus.INFO, "Activity verified: "+Smoke_CallLog2Subject,YesNo.No);	
							} else {
								sa.assertTrue(false, "Activity not verified: "+Smoke_CallLog2Subject);
								log(LogStatus.SKIP, "Activity not verified: "+Smoke_CallLog2Subject,YesNo.Yes);
							}
							ThreadSleep(2000);
						
						} else {
							sa.assertTrue(false, "Not on Open Activities");
							log(LogStatus.SKIP, "Not on Open Activities",YesNo.Yes);
						}
						
					} else {
						sa.assertTrue(false, "Contact not Found : "+SmokeC6_FName+" "+SmokeC6_LName);
						log(LogStatus.SKIP, "Contact not Found : "+SmokeC6_FName+" "+SmokeC6_LName,YesNo.Yes);	
					}
					
				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Tab");
					log(LogStatus.SKIP, "Not Able to Click on Contact Tab",YesNo.Yes);	
				}
				
			switchToDefaultContent(driver);
			lp.CRMlogout(environment, mode);
			sa.assertAll();
			appLog.info("Pass");	
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc082_VerifyContactTransferAB(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Contact Tab");
		if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
			if (cp.clickOnCreatedContact(environment, SmokeC6_FName, SmokeC6_LName)) {
				log(LogStatus.INFO, "Click on Created Contact : " + SmokeC6_FName + " " + SmokeC6_LName, YesNo.No);

				
				if (cp.clickOnShowMoreActionDownArrow("PE", PageName.ContactPage, ShowMoreActionDropDownList.Contact_Transfer, 10)) {
					log(LogStatus.INFO, "Clicked on Contact Transfer", YesNo.No);	
					
					if (cp.verifyContactTransferUIonContactPage(environment, mode, SmokeC6_FName+" "+SmokeC6_LName, SmokeINS4, 10)) {
						log(LogStatus.PASS, "Contact Transfer UI Verify", YesNo.Yes);
					} else {
						sa.assertTrue(false, "Contact Transfer UI Not Verify");
						log(LogStatus.FAIL, "Contact Transfer UI Not Verify", YesNo.Yes);
					}
					
					if (cp.enteringValueforLegalNameOnContactTransferPage(environment, mode, SmokeINDINV3, AddressAction.Retain, 10)) {
						log(LogStatus.PASS, "Able to Transfer Contact", YesNo.No);
					} else {
						sa.assertTrue(false, "Not Able to Transfer Contact");
						log(LogStatus.FAIL, "Not Able to Transfer Contact", YesNo.Yes);
					}
					ThreadSleep(10000);
					switchToDefaultContent(driver);
					refresh(driver);
					String[][] labelsWithValues = { { excelLabel.Name.toString(), SmokeC6_FName+" "+SmokeC6_LName },
							{ excelLabel.Legal_Name.toString(), SmokeINDINV3 },
							{ excelLabel.Phone.toString(), SmokeINS4_Phone },
							{ excelLabel.Fax.toString(), SmokeINS4_Fax },
							{ excelLabel.Street.toString(), SmokeINS4_Street },
							{ excelLabel.City.toString(), SmokeINS4_City },
							{ excelLabel.State.toString(), SmokeINS4_State },
							{ excelLabel.Country.toString(), SmokeINS4_Country },
							{ excelLabel.Postal_Code.toString(), SmokeINS4_Postal_Code },
							{ excelLabel.Other_Street.toString(), SmokeC6_Other_Street },
							{ excelLabel.Other_City.toString(), SmokeC6_Other_City },
							{ excelLabel.Other_State.toString(), SmokeC6_Other_State },
							{ excelLabel.Other_Zip.toString(), SmokeC6_Other_Zip },
							{ excelLabel.Other_Country.toString(), SmokeC6_Other_Country } };
					
							for (String[] labelWithValue : labelsWithValues) {

								if (cp.fieldValueVerificationOnContactPage(environment,  TabName.InstituitonsTab,
										labelWithValue[0], labelWithValue[1])) {
									log(LogStatus.PASS, labelWithValue[0] + " : with value : " + labelWithValue[1] + "  Verified",
											YesNo.No);
								} else {
									sa.assertTrue(false,
											labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified");
									log(LogStatus.FAIL,
											labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified",
											YesNo.Yes);
								}
							}
						
							if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Affiliations, RelatedTab.Affiliations.toString())){
								log(LogStatus.INFO, "Click on Affiliations", YesNo.Yes);

							
								
								
								if (cp.verifyOpenActivityRelatedList(environment, mode,TabName.ContactTab, Smoke_NewTask2Subject, null, null)) {
									log(LogStatus.INFO, "Open Activity Grid  Verified For "+Smoke_NewTask2Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewTask2Subject);
									log(LogStatus.FAIL, "Open Activity Grid Not Verified For "+Smoke_NewTask2Subject, YesNo.Yes);
								}
								
								if (cp.verifyOpenActivityRelatedList(environment, mode,TabName.ContactTab, Smoke_NewEvent2Subject, SmokeINDINV3, null)) {
									log(LogStatus.INFO, "Open Activity Grid Verified For "+Smoke_NewEvent2Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewEvent2Subject);
									log(LogStatus.FAIL, "Open Activity  Grid Not Verified For "+Smoke_NewEvent2Subject, YesNo.Yes);
								}
								
								if (cp.verifyActivityHistoryRelatedList(environment, mode,TabName.ContactTab, Smoke_CallLog2Subject, Smoke_Fund2, null)) {
									log(LogStatus.INFO, "Activity History Grid Verified For "+Smoke_CallLog2Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Activity History Grid Not Verified For "+Smoke_CallLog2Subject);
									log(LogStatus.FAIL, "Activity History Grid Not Verified For "+Smoke_CallLog2Subject, YesNo.Yes);
								}
//								if (bp.scrollToRelatedListViewAll_Lightning(environment, mode, RelatedList.Affiliations, true)) {
//									log(LogStatus.INFO, "successfully scrolled to affiliations related list", YesNo.No);
//								}
//								else {
//									log(LogStatus.ERROR, "could not scroll to affiliations related list",YesNo.Yes);
//									sa.assertTrue(false, "could not scroll to affiliations related list");
//								}
//								if (cp.clickOnViewAllRelatedList(environment, mode,RelatedList.Affiliations)) {
//									log(LogStatus.INFO, "Click on View All Affiliations", YesNo.No);
									
									if (cp.verifyAffliationRelatedList(environment, mode,TabName.ContactTab, SmokeINS4)) {
										log(LogStatus.PASS, "Affialition Grid Verified For "+SmokeINS4, YesNo.Yes);	
									} else {
										sa.assertTrue(false, "Affialition Grid Not Verified For "+SmokeINS4);
										log(LogStatus.FAIL, "Affialition Grid Not Verified For "+SmokeINS4, YesNo.Yes);
									}
//								} else {
//									sa.assertTrue(false, "Not Able to Click on View All Affiliations");
//									log(LogStatus.SKIP, "Not Able to Click on View All Affiliations", YesNo.Yes);
//								}
								
								
							} else {
								sa.assertTrue(false, "Not Able to Click on Affiliations");
								log(LogStatus.SKIP, "Not Able to Click on Affiliations", YesNo.Yes);
							}


				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Transfer");
					log(LogStatus.SKIP, "Not Able to Click on Contact Transfer", YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + SmokeC6_FName + " " + SmokeC6_LName);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + SmokeC6_FName + " " + SmokeC6_LName,
						YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc083_VerifyActivitiesAtOldInstitutionContactTransferAcc4(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Institution Tab");
		if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINS4)) {
				log(LogStatus.INFO, "Click on Created Inst : " + SmokeINS4, YesNo.No);
							
//							if (ip.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Activities, null)) {
//								log(LogStatus.INFO, "Click on Related Tab", YesNo.Yes);	
								
								if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									windowScrollYAxis(driver, 0, 2800);
								}
								
//								if (ip.verifyNoDataAtOpenActivitiesSection(environment, mode, TabName.InstituitonsTab, 10)) {
//									log(LogStatus.INFO, "Open Activities Grid Verified For No Records/No Next Activity Msg", YesNo.No);	
//								} else {
//									sa.assertTrue(false, "Open Activities Grid Not Verified For No Records/No Next Activity Msg");
//									log(LogStatus.FAIL, "Open Activities Grid Not Verified For No Records/No Next Activity Msg", YesNo.Yes);
//								}
								
								if (ip.verifyNoDataAtActivityHistorySection(environment, mode, TabName.InstituitonsTab, 10)) {
									log(LogStatus.INFO, "Activity History Grid Verified For No Records/No Past Activity Msg", YesNo.No);	
								} else {
									sa.assertTrue(false, "Activity History Grid Not Verified For No Records/No Past Activity Msg");
									log(LogStatus.FAIL, "Activity History Grid Not Verified For No Records/No Past Activity Msg", YesNo.Yes);
								}
								
//								if (ip.clickOnViewAllRelatedList(environment, mode,RelatedList.Activities)) {
//									log(LogStatus.INFO, "Click on View All Affiliations", YesNo.No);
									
									String[][] activitiesRecords ={{Smoke_NewTask2Subject,SmokeC6_FName + " " + SmokeC6_LName,null},
															{Smoke_NewEvent2Subject,SmokeC6_FName + " " + SmokeC6_LName,SmokeINDINV3},
															{Smoke_CallLog2Subject,SmokeC6_FName + " " + SmokeC6_LName,Smoke_Fund2}};
									
									for (String[] activityRecord : activitiesRecords) {
										
										if (ip.verifyActivitiesRelatedList(environment, mode, TabName.InstituitonsTab, activityRecord[0], activityRecord[1], activityRecord[2])) {
											log(LogStatus.PASS, "Activities Grid Verified For "+activityRecord[0], YesNo.Yes);	
										} else {
											sa.assertTrue(false, "Activities Grid Not Verified For "+activityRecord[0]);
											log(LogStatus.FAIL, "Activities Grid Not Verified For "+activityRecord[0], YesNo.Yes);
										}	
									}
									
									
//								} else {
//									sa.assertTrue(false, "Not Able to Click on View All Activities");
//									log(LogStatus.SKIP, "Not Able to Click on View All Activities", YesNo.Yes);
//								}
								
//								
//							} else {
//								sa.assertTrue(false, "Not Able to Click on Related Tab");
//								log(LogStatus.SKIP, "Not Able to Click on Related Tab", YesNo.Yes);
//							}


				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINS4);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINS4,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc084_VerifyActivitiesAtNewInstitutionContactTransferAcc5(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Institution Tab");
		if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINDINV3)) {
				log(LogStatus.INFO, "Click on Created Inst : " + SmokeINDINV3, YesNo.No);
							
//							if (ip.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Activities, null)) {
//								log(LogStatus.INFO, "Click on Related Tab", YesNo.Yes);	
								
								if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									windowScrollYAxis(driver, 0, 2800);
								}
								
								if (ip.verifyOpenActivityRelatedList(environment, mode,TabName.InstituitonsTab, Smoke_NewTask2Subject, null, SmokeC6_FName + " " + SmokeC6_LName)) {
									log(LogStatus.PASS, "Open Activity Grid  Verified For "+Smoke_NewTask2Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewTask2Subject);
									log(LogStatus.FAIL, "Open Activity Grid Not Verified For "+Smoke_NewTask2Subject, YesNo.Yes);
								}
								
								if (ip.verifyOpenActivityRelatedList(environment, mode,TabName.InstituitonsTab, Smoke_NewEvent2Subject, SmokeINDINV3, SmokeC6_FName + " " + SmokeC6_LName)) {
									log(LogStatus.PASS, "Open Activity Grid Verified For "+Smoke_NewEvent2Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewEvent2Subject);
									log(LogStatus.FAIL, "Open Activity  Grid Not Verified For "+Smoke_NewEvent2Subject, YesNo.Yes);
								}
								
								if (ip.verifyActivityHistoryRelatedList(environment, mode, TabName.InstituitonsTab, Smoke_CallLog2Subject, Smoke_Fund2, SmokeC6_FName + " " + SmokeC6_LName)) {
									log(LogStatus.PASS, "Activity History Grid Verified For "+Smoke_CallLog2Subject, YesNo.Yes);	
								} else {
									sa.assertTrue(false, "Activity History Grid Not Verified For "+Smoke_CallLog2Subject);
									log(LogStatus.FAIL, "Activity History Grid Not Verified For "+Smoke_CallLog2Subject, YesNo.Yes);
								}	
								
//							if (ip.verifyGridErrorMessage(environment, mode, RelatedList.Activities, HomePageErrorMessage.NoRecordToDisplayOnActivities, 10)) {
//									log(LogStatus.PASS, HomePageErrorMessage.NoRecordToDisplayOnActivities+" Verify Error Message on Actvities Grid", YesNo.No);
//								}else {
//									log(LogStatus.FAIL, HomePageErrorMessage.NoRecordToDisplayOnActivities+" Error Message is not verified on Activities Grid", YesNo.Yes);
//									sa.assertTrue(false, HomePageErrorMessage.NoRecordToDisplayOnActivities+" Error Message is not verified on Actvities Grid");
//								}

//							} else {
//								sa.assertTrue(false, "Not Able to Click on Related Tab");
//								log(LogStatus.SKIP, "Not Able to Click on Related Tab", YesNo.Yes);
//							}


				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINDINV3);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINDINV3,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}
	
	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc085_VerifyContactTransferBC(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Contact Tab");
		if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
			if (cp.clickOnCreatedContact(environment,  SmokeC6_FName, SmokeC6_LName)) {
				log(LogStatus.INFO, "Click on Created Contact : " + SmokeC6_FName + " " + SmokeC6_LName, YesNo.No);

				
				if (cp.clickOnShowMoreActionDownArrow("PE", PageName.ContactPage, ShowMoreActionDropDownList.Contact_Transfer, 10)) {
					log(LogStatus.INFO, "Clicked on Contact Transfer", YesNo.No);	
					
					if (cp.verifyContactTransferUIonContactPage(environment, mode, SmokeC6_FName+" "+SmokeC6_LName, SmokeINDINV3, 10)) {
						log(LogStatus.PASS, "Contact Transfer UI Verify", YesNo.Yes);
					} else {
						sa.assertTrue(false, "Contact Transfer UI Not Verify");
						log(LogStatus.FAIL, "Contact Transfer UI Not Verify", YesNo.Yes);
					}
					
					if (cp.enteringValueforLegalNameOnContactTransferPage(environment, mode, SmokeINS5, AddressAction.Clear, 10)) {
						log(LogStatus.PASS, "Able to Transfer Contact", YesNo.No);
					} else {
						sa.assertTrue(false, "Not Able to Transfer Contact");
						log(LogStatus.FAIL, "Not Able to Transfer Contact", YesNo.Yes);
					}
					ThreadSleep(10000);
					switchToDefaultContent(driver);
					refresh(driver);
					String[][] labelsWithValues = { { excelLabel.Name.toString(), SmokeC6_FName+" "+SmokeC6_LName },
							{ excelLabel.Legal_Name.toString(), SmokeINS5 }};
					
							for (String[] labelWithValue : labelsWithValues) {

								if (cp.fieldValueVerificationOnContactPage(environment, TabName.InstituitonsTab,
										labelWithValue[0], labelWithValue[1])) {
									log(LogStatus.PASS, labelWithValue[0] + " : with value : " + labelWithValue[1] + "  Verified",
											YesNo.No);
								} else {
									sa.assertTrue(false,
											labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified");
									log(LogStatus.FAIL,
											labelWithValue[0] + " : with value : " + labelWithValue[1] + " Not Verified",
											YesNo.Yes);
								}
							}
							
							
							String[][] labelWithBlankValue = {{ excelLabel.Fax.toString(), "" },
									{ excelLabel.Phone.toString(), "" },
									{ excelLabel.Mailing_Address.toString(), "" },
									{ excelLabel.Other_Address.toString(), "" }};
							
							for (String[] labelWithValue : labelWithBlankValue) {

								if (!cp.fieldValueVerificationOnContactPage(environment,  TabName.InstituitonsTab,
										labelWithValue[0], labelWithValue[1])) {
									log(LogStatus.PASS, labelWithValue[0] + " : with value Blank Value Verified",YesNo.No);
								} else {
									sa.assertTrue(false,labelWithValue[0] + " : with Blank Value Not Verified");
									log(LogStatus.FAIL,labelWithValue[0] + " : with Blank Value Not Verified",YesNo.Yes);
								}
							}
							
						
							
								
							if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Affiliations, RelatedTab.Affiliations.toString())){
								log(LogStatus.INFO, "Click on Affiliations", YesNo.Yes);
								if (cp.verifyOpenActivityRelatedList(environment, mode,TabName.ContactTab, Smoke_NewTask2Subject, null, null)) {
									log(LogStatus.PASS, "Open Activity Grid  Verified For "+Smoke_NewTask2Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewTask2Subject);
									log(LogStatus.FAIL, "Open Activity Grid Not Verified For "+Smoke_NewTask2Subject, YesNo.Yes);
								}
								
								if (cp.verifyOpenActivityRelatedList(environment, mode,TabName.ContactTab, Smoke_NewEvent2Subject, SmokeINS5, null)) {
									log(LogStatus.PASS, "Open Activity Grid Verified For "+Smoke_NewEvent2Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewEvent2Subject);
									log(LogStatus.FAIL, "Open Activity  Grid Not Verified For "+Smoke_NewEvent2Subject, YesNo.Yes);
								}
								
								if (cp.verifyActivityHistoryRelatedList(environment, mode,TabName.ContactTab, Smoke_CallLog2Subject, Smoke_Fund2, null)) {
									log(LogStatus.PASS, "Activity History Grid Verified For "+Smoke_CallLog2Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Activity History Grid Not Verified For "+Smoke_CallLog2Subject);
									log(LogStatus.FAIL, "Activity History Grid Not Verified For "+Smoke_CallLog2Subject, YesNo.Yes);
								}
								
//									if (bp.scrollToRelatedListViewAll_Lightning(environment, mode, RelatedList.Affiliations, true)) {
//										log(LogStatus.INFO, "successfully scrolled to affiliations related list", YesNo.No);
//									}
//									else {
//										log(LogStatus.ERROR, "could not scroll to affiliations related list", YesNo.Yes);
//										sa.assertTrue(false, "could not scroll to affiliations related list");
//									}
//								if (cp.clickOnViewAllRelatedList(environment, mode,RelatedList.Affiliations)) {
//									log(LogStatus.INFO, "Click on View All Affiliations", YesNo.No);
									
									if (cp.verifyAffliationRelatedList(environment, mode,TabName.ContactTab, SmokeINS4)) {
										log(LogStatus.PASS, "Affialition Grid Verified For "+SmokeINS4, YesNo.Yes);	
									} else {
										sa.assertTrue(false, "Affialition Grid Not Verified For "+SmokeINS4);
										log(LogStatus.FAIL, "Affialition Grid Not Verified For "+SmokeINS4, YesNo.Yes);
									}
									
									if (cp.verifyAffliationRelatedList(environment, mode,TabName.ContactTab, SmokeINDINV3)) {
										log(LogStatus.PASS, "Affialition Grid Verified For "+SmokeINDINV3, YesNo.Yes);	
									} else {
										sa.assertTrue(false, "Affialition Grid Not Verified For "+SmokeINDINV3);
										log(LogStatus.FAIL, "Affialition Grid Not Verified For "+SmokeINDINV3, YesNo.Yes);
									}
									
//								} else {
//									sa.assertTrue(false, "Not Able to Click on View All Affiliations");
//									log(LogStatus.SKIP, "Not Able to Click on View All Affiliations", YesNo.Yes);
//								}
								
								
							} else {
								sa.assertTrue(false, "Not Able to Click on Affiliations");
								log(LogStatus.SKIP, "Not Able to Click on Affiliations", YesNo.Yes);
							}


				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Transfer");
					log(LogStatus.SKIP, "Not Able to Click on Contact Transfer", YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + SmokeC6_FName + " " + SmokeC6_LName);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + SmokeC6_FName + " " + SmokeC6_LName,
						YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc086_VerifyActivitiesAtOldInstitutionContactTransferAcc4(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Institution Tab");
		if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINS4)) {
				log(LogStatus.INFO, "Click on Created Inst : " + SmokeINS4, YesNo.No);
							
							if (ip.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Activities, null)) {
								log(LogStatus.INFO, "Click on Related Tab", YesNo.No);	
								if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									windowScrollYAxis(driver, 0, 180000);
								}
								
								if (ip.verifyNoDataAtOpenActivitiesSection(environment, mode, TabName.InstituitonsTab, 10)) {
									log(LogStatus.PASS, "Open Activities Grid Verified For No Records/No Next Activity Msg", YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activities Grid Not Verified For No Records/No Next Activity Msg");
									log(LogStatus.FAIL, "Open Activities Grid Not Verified For No Records/No Next Activity Msg", YesNo.Yes);
								}
								
								
								if (ip.verifyNoDataAtActivityHistorySection(environment, mode, TabName.InstituitonsTab, 10)) {
									log(LogStatus.PASS, "Activity History Grid Verified For No Records/No Past Activity Msg", YesNo.No);	
								} else {
									sa.assertTrue(false, "Activity History Grid Not Verified For No Records/No Past Activity Msg");
									log(LogStatus.FAIL, "Activity History Grid Not Verified For No Records/No Past Activity Msg", YesNo.Yes);
								}
								
								if (ip.clickOnViewAllRelatedList(environment, mode,RelatedList.Activities)) {
									log(LogStatus.INFO, "Click on View All Affiliations", YesNo.No);
									
									String[][] activitiesRecords ={{Smoke_NewTask2Subject,SmokeC6_FName + " " + SmokeC6_LName,null},
															{Smoke_NewEvent2Subject,SmokeC6_FName + " " + SmokeC6_LName,SmokeINS5},
															{Smoke_CallLog2Subject,SmokeC6_FName + " " + SmokeC6_LName,Smoke_Fund2}};
									
									for (String[] activityRecord : activitiesRecords) {
										
										if (ip.verifyActivitiesRelatedList(environment, mode, TabName.InstituitonsTab, activityRecord[0], activityRecord[1], activityRecord[2])) {
											log(LogStatus.PASS, "Activities Grid Verified For "+activityRecord[0], YesNo.Yes);	
										} else {
											sa.assertTrue(false, "Activities Grid Not Verified For "+activityRecord[0]);
											log(LogStatus.FAIL, "Activities Grid Not Verified For "+activityRecord[0], YesNo.Yes);
										}	
									}
									
									
								} else {
									sa.assertTrue(false, "Not Able to Click on View All Activities");
									log(LogStatus.SKIP, "Not Able to Click on View All Activities", YesNo.Yes);
								}
								
								
							} else {
								sa.assertTrue(false, "Not Able to Click on  Related Tab");
								log(LogStatus.SKIP, "Not Able to Click on Related Tab", YesNo.Yes);
							}


				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINS4);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINS4,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc087_VerifyActivitiesAtOldInstitutionContactTransferAcc5(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Institution Tab");
		if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINDINV3)) {
				log(LogStatus.INFO, "Click on Created Inst : " + SmokeINDINV3, YesNo.No);
//							
//							if (ip.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Activities, null)) {
//								log(LogStatus.INFO, "Click on Related Tab", YesNo.No);	
								if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									windowScrollYAxis(driver, 0, 180000);
									log(LogStatus.INFO, "Scroll >>>>>", YesNo.No);	
								}
								ThreadSleep(2000);
//								if (ip.verifyNoDataAtOpenActivitiesSection(environment, mode, TabName.InstituitonsTab, 10)) {
//									log(LogStatus.PASS, "Open Activities Grid Verified For No Records/No Next Activity Msg", YesNo.No);	
//								} else {
//									sa.assertTrue(false, "Open Activities Grid Not Verified For No Records/No Next Activity Msg");
//									log(LogStatus.FAIL, "Open Activities Grid Not Verified For No Records/No Next Activity Msg", YesNo.Yes);
//								}

								if (ip.verifyNoDataAtActivityHistorySection(environment, mode, TabName.InstituitonsTab, 10)) {
									log(LogStatus.PASS, "Activity History Section Verified For No Records/No Activities Msg", YesNo.No);	
								} else {
									sa.assertTrue(false, "Activity History Section Not Verified For No Records/No Activities Msg");
									log(LogStatus.FAIL, "Activity History Section Not Verified For No Records/No Activities Msg", YesNo.Yes);
								}	
								
//								if (ip.verifyGridErrorMessage(environment, mode, RelatedList.Activities, HomePageErrorMessage.NoRecordToDisplayOnActivities, 10)) {
//									log(LogStatus.PASS, HomePageErrorMessage.NoRecordToDisplayOnActivities+" Verify Error Message on Actvities Grid", YesNo.No);
//								}else {
//									log(LogStatus.FAIL, HomePageErrorMessage.NoRecordToDisplayOnActivities+" Error Message is not verified on Activities Grid", YesNo.Yes);
//									sa.assertTrue(false, HomePageErrorMessage.NoRecordToDisplayOnActivities+" Error Message is not verified on Actvities Grid");
//								}
								
								
								
//							} else {
//								sa.assertTrue(false, "Not Able to Click on Related Tab");
//								log(LogStatus.SKIP, "Not Able to Click on Related Tab", YesNo.Yes);
//							}


				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINDINV3);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINDINV3,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc088_VerifyActivitiesAtNewInstitutionContactTransferAcc6(String environment, String mode) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		appLog.info("Login with User");
		appLog.info("Going on Institution Tab");
		if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if (ip.clickOnCreatedInstitution(environment, mode, SmokeINS5)) {
				log(LogStatus.INFO, "Click on Created Inst : " + SmokeINS5, YesNo.No);
							
//							if (ip.clickOnRelatedList(environment, mode, RecordType.Institution, RelatedList.Activities, null)) {
//								log(LogStatus.INFO, "Click on Related Tab", YesNo.Yes);	
								
								if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
									windowScrollYAxis(driver, 0, 2800);
								}
								
								if (ip.verifyOpenActivityRelatedList(environment, mode,TabName.InstituitonsTab, Smoke_NewTask2Subject, null, SmokeC6_FName + " " + SmokeC6_LName)) {
									log(LogStatus.INFO, "Open Activity Grid  Verified For "+Smoke_NewTask2Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewTask2Subject);
									log(LogStatus.FAIL, "Open Activity Grid Not Verified For "+Smoke_NewTask2Subject, YesNo.Yes);
								}
								
								if (ip.verifyOpenActivityRelatedList(environment, mode,TabName.InstituitonsTab, Smoke_NewEvent2Subject, SmokeINS5, SmokeC6_FName + " " + SmokeC6_LName)) {
									log(LogStatus.INFO, "Open Activity Grid Verified For "+Smoke_NewEvent2Subject, YesNo.No);	
								} else {
									sa.assertTrue(false, "Open Activity Grid Not Verified For "+Smoke_NewEvent2Subject);
									log(LogStatus.FAIL, "Open Activity  Grid Not Verified For "+Smoke_NewEvent2Subject, YesNo.Yes);
								}
								
								if (ip.verifyActivityHistoryRelatedList(environment, mode, TabName.InstituitonsTab, Smoke_CallLog2Subject, Smoke_Fund2, SmokeC6_FName + " " + SmokeC6_LName)) {
									log(LogStatus.PASS, "Activity History Grid Verified For "+Smoke_CallLog2Subject, YesNo.Yes);	
								} else {
									sa.assertTrue(false, "Activity History Grid Not Verified For "+Smoke_CallLog2Subject);
									log(LogStatus.FAIL, "Activity History Grid Not Verified For "+Smoke_CallLog2Subject, YesNo.Yes);
								}	
								
//								if (ip.verifyGridErrorMessage(environment, mode, RelatedList.Activities, HomePageErrorMessage.NoRecordToDisplayOnActivities, 10)) {
//									log(LogStatus.INFO, HomePageErrorMessage.NoRecordToDisplayOnActivities+" Verify Error Message on Actvities Grid", YesNo.No);
//								}else {
//									log(LogStatus.FAIL, HomePageErrorMessage.NoRecordToDisplayOnActivities+" Error Message is not verified on Activities Grid", YesNo.Yes);
//									sa.assertTrue(false, HomePageErrorMessage.NoRecordToDisplayOnActivities+" Error Message is not verified on Actvities Grid");
//								}

//							} else {
//								sa.assertTrue(false, "Not Able to Click on Related Tab");
//								log(LogStatus.SKIP, "Not Able to Click on Related Tab", YesNo.Yes);
//							}


				
			} else {
				sa.assertTrue(false, "Not Able to Click on Created Institution : " + SmokeINS5);
				log(LogStatus.SKIP, "Not Able to Click on Created Institution : " + SmokeINS5,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Institution Tab");
			log(LogStatus.SKIP, "Not Able to Click on Institution Tab", YesNo.Yes);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc089_1_VerifyBulkEmailfunctionality(String environment, String mode) {
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele;
		String msg;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	//	lp.CRMLogin(superAdminUserName, adminPassword);
		appLog.info("Login with User");
		String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Email.toString();
		
		if (home.ClickOnItemOnNavatarEdge(navigationMenuName, bulkActionNavigationLink, action.BOOLEAN, 20)) {
				log(LogStatus.INFO, "Clicked On Bulk Email Link with Navatar Quick Link", YesNo.No);
				
				if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(20));
					
				}
				
				if (hp.verifyLandingPageAfterClickingOnNavatarSetUpPage(environment, mode, NavatarQuickLink.BulkEmail)) {
					log(LogStatus.PASS, "Landing Page Verified for Bulk Email", YesNo.No);
				} else {
					sa.assertTrue(false, "Landing Page Not Verified for Bulk Email");
					log(LogStatus.FAIL, "Landing Page Not Verified for Bulk Email", YesNo.Yes);
				}
				
				msg = hp.getStep1BuildYourOwnDistribution(environment, mode, 10).getText().trim();
				if (msg.contains(HomePageErrorMessage.step1_BuildYourOwn)) {
					log(LogStatus.PASS, "Message Verified "+HomePageErrorMessage.step1_BuildYourOwn, YesNo.No);
				} else {
					sa.assertTrue(false, "Message Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step1_BuildYourOwn);
					log(LogStatus.FAIL, "Message Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step1_BuildYourOwn, YesNo.Yes);
				}
				
				if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode,SmokeReportFolderName,"R2"+SmokeReportName)) {
					log(LogStatus.INFO, "Clicked On Deal Report : Deal Pipeline", YesNo.No);	
					
					msg = hp.getBulkEmailErrorPopUp(environment, mode, 10).getText().trim();
					if (HomePageErrorMessage.bulkEmailErrorPopUpMsg.equals(msg)) {
						log(LogStatus.PASS, "Error PopUp Message Verified Actual : "+HomePageErrorMessage.bulkEmailErrorPopUpMsg, YesNo.No);
					} else {
						sa.assertTrue(false, "Error PopUp Message Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.bulkEmailErrorPopUpMsg);
						log(LogStatus.FAIL, "Error PopUp Message Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.bulkEmailErrorPopUpMsg, YesNo.Yes);
					}
					
					if (click(driver, hp.getBulkEmailErrorPopUpOkBtn(environment, mode, 10), "Ok Button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked On OK Button", YesNo.No);	

						ThreadSleep(3000);

						if (hp.getBulkEmailErrorPopUp(environment, mode, 3)==null) {
							log(LogStatus.PASS, "Error PopUp Closed", YesNo.No);	
						} else {
							sa.assertTrue(false, "Error PopUp Not Closed");
							log(LogStatus.FAIL, "Error PopUp Not Closed", YesNo.Yes);
						}
						
					} else {
						sa.assertTrue(false, "Not Able to Click On OK Button");
						log(LogStatus.SKIP, "Not Able to Click On OK Button", YesNo.Yes);
					}
					
				} else {
					sa.assertTrue(false, "Not Able to Click On Deal Report : Deal Pipeline");
					log(LogStatus.SKIP, "Not Able to Click On Deal Report : Deal Pipeline", YesNo.Yes);
				}
				
				//
				
				if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, "Public Reports", "Sample Report: # of Accounts")) {
					log(LogStatus.INFO, "Clicked On Public Report : Sample Report: # of Accounts", YesNo.No);	
					ThreadSleep(2000);
					msg = hp.getBulkEmailErrorPopUp(environment, mode, 10).getText().trim();
					if (HomePageErrorMessage.bulkEmailErrorPopUpMsg1.equals(msg)) {
						log(LogStatus.PASS, "Error PopUp Message Verified Actual : "+HomePageErrorMessage.bulkEmailErrorPopUpMsg1, YesNo.No);
					} else {
						sa.assertTrue(false, "Error PopUp Message Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.bulkEmailErrorPopUpMsg1);
						log(LogStatus.FAIL, "Error PopUp Message Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.bulkEmailErrorPopUpMsg1, YesNo.Yes);
					}
					
					if (click(driver, hp.getBulkEmailErrorPopUpOkBtn(environment, mode, 10), "Ok Button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked On OK Button", YesNo.No);	
						ThreadSleep(3000);
						if (hp.getBulkEmailErrorPopUp(environment, mode, 3)==null) {
							log(LogStatus.PASS, "Error PopUp Closed", YesNo.No);	
						} else {
							sa.assertTrue(false, "Error PopUp Not Closed");
							log(LogStatus.FAIL, "Error PopUp Not Closed", YesNo.Yes);
						}
						
					} else {
						sa.assertTrue(false, "Not Able to Click On OK Button");
						log(LogStatus.SKIP, "Not Able to Click On OK Button", YesNo.Yes);
					}
					
				} else {
					sa.assertTrue(false, "Not Able to Click On Public Report : Sample Report: # of Accounts");
					log(LogStatus.SKIP, "Not Able to Click On Public Report : Sample Report: # of Accounts", YesNo.Yes);
				}
				
				//
				if (sendKeys(driver, hp.getSearchForAReportsTextBox(environment, mode, 10), SmokeReportName, "Report Search Text Box", action.BOOLEAN)) {
					log(LogStatus.INFO, "Entered Value on Report Search Box : "+SmokeReportName, YesNo.Yes);
					
					if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, SmokeReportFolderName, SmokeReportName)) {
						log(LogStatus.INFO, "Clicked On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.No);	
						
						try {
							ele = hp.getRecordsOnBulkEmail(environment, mode, 10);
							if (ele!=null) {
								String[] record = ele.getText().trim().split(":");
								if (Integer.parseInt(record[1])>0) {
									log(LogStatus.PASS, "Record is  greater than 0", YesNo.No);		
								} else {
									sa.assertTrue(false, "Record is not greater than 0");
									log(LogStatus.FAIL, "Record is not greater than 0", YesNo.Yes);	
								}
								
								
							} else {
								sa.assertTrue(false, "Record Label null");
								log(LogStatus.FAIL, "Record Label null", YesNo.Yes);	
							}
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							sa.assertTrue(false, "Exception Occur");
							log(LogStatus.FAIL, "Exception Occur", YesNo.Yes);
							e.printStackTrace();
						}
						
					}else{
						sa.assertTrue(false, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName);
						log(LogStatus.FAIL, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.Yes);	
					}
					
				} else {
					sa.assertTrue(false, "Not Able to Entered Value on Report Search Box : "+SmokeReportName);
					log(LogStatus.SKIP, "Not Able to Entered Value on Report Search Box : "+SmokeReportName, YesNo.Yes);
				}
				
				
			} else {
				sa.assertTrue(false, "Not Able to Click On Bulk Email Link with Navatar Quick Link");
				log(LogStatus.SKIP, "Not Able to Click On Bulk Email Link with Navatar Quick Link", YesNo.Yes);
			}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc089_2_verifyCancelNextPreviousOnBulkEmail(String environment, String mode) {
			SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
			LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
			InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
			ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
			MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
			FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
			FundDistributionsPageBusinessLayer fdd = new FundDistributionsPageBusinessLayer(driver);
			FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
			BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

			HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
			WebElement ele;
			String msg;
			List<String> result;
			lp.CRMLogin(crmUser1EmailID, adminPassword);

			HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
			String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Email.toString();


			for(int j=0; j<=7; j++) {
				
				if (home.ClickOnItemOnNavatarEdge(navigationMenuName, bulkActionNavigationLink, action.BOOLEAN, 20)) {
					log(LogStatus.INFO, "Clicked On Bulk Email Link with Navatar Quick Link : "+j, YesNo.No);

					if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(20));

					}


					//	List<String> result =market.selectProspectsContactAndVerifyReviewProspectListInEmailProspect(PageName.BulkEmail,ContactAndAccountName, searchContactInEmailProspectGrid.No);
					if(j==0) {
						if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, SmokeReportFolderName, SmokeReportName)) {
							log(LogStatus.INFO, "Clicked On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.No);	

							result = hp.selectContactAndVerifyInBulkEmail(environment, mode, SmokeC3_FName,SmokeC3_LName,SmokeC3_LName,searchContactInEmailProspectGrid.Yes, 10);

							if (result.isEmpty()) {
								log(LogStatus.PASS, "Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);

								for(int i=0; i<=3; i++) {
									ThreadSleep(3000);
									if(i==0) {
										if(click(driver, market.getStep1NextBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"Top Next button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on top next button", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on top next button", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on top next button");
										}
									}
									if(i==1) {
										if(click(driver, market.getStep2PreviousBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"Top previous button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on top previous button", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on top previous button", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on top previous button");
										}
									}
									if(i==2) {
										if(click(driver, market.getStep1NextBtn(PageName.BulkEmail, TopOrBottom.BOTTOM,10),"bottom Next button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on BOTTOM next button", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on BOTTOM next button", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on BOTTOM next button");
										}
									}

									if(i==3) {
										if(click(driver, market.getStep2PreviousBtn(PageName.BulkEmail, TopOrBottom.BOTTOM,10),"bottom previous button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on BOTTOM previous button", YesNo.No);
										}else {
											log(LogStatus.ERROR,"Not able to click on BOTTOM previous button", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on BOTTOM previous button");
										}
									}
									if(i==0 && i==2) {
										ThreadSleep(2000);
										ele = fd.getStep2TextEmailing(30);
										if (ele!=null) {
											msg = ele.getText().trim();
											if (HomePageErrorMessage.Step2_SelectAnEmailTemplate.equals(msg)) {
												log(LogStatus.PASS, "Step2 Page Verified : "+msg, YesNo.No);
											} else {
												sa.assertTrue(false, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate);
												log(LogStatus.FAIL, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate, YesNo.Yes);
											}
										} else {
											sa.assertTrue(false, "Step2 Page Element is null");
											log(LogStatus.FAIL, "Step2 Page Element is null", YesNo.Yes);

										}
									}

								}


							} else {
								sa.assertTrue(false, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName);
								log(LogStatus.FAIL, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
							}


						}else {
							log(LogStatus.ERROR, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button", YesNo.Yes);
							sa.assertTrue(false, "Not able to select contact "+SmokeC1_FName+" "+SmokeC1_LName+" so cannot check next and previous button");
						}
					}
					if(j==1) {

						if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, SmokeReportFolderName, SmokeReportName)) {
							log(LogStatus.INFO, "Clicked On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.No);	

							result = hp.selectContactAndVerifyInBulkEmail(environment, mode, SmokeC3_FName,SmokeC3_LName,SmokeC3_LName,searchContactInEmailProspectGrid.Yes, 10);

							if (result.isEmpty()) {
								log(LogStatus.PASS, "Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);


								if(click(driver, market.getStep1CancelBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"Top cancel button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "clicked on top cancel button step 1", YesNo.No);
								}else {
									log(LogStatus.ERROR,"Not able to click on top cancel button step 1", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on top cancel button step 1");
								}


							} else {
								sa.assertTrue(false, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName);
								log(LogStatus.FAIL, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName);
							log(LogStatus.SKIP, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.Yes);	
						}

					}
					if(j==2) {

						if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, SmokeReportFolderName, SmokeReportName)) {
							log(LogStatus.INFO, "Clicked On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.No);	

							result = hp.selectContactAndVerifyInBulkEmail(environment, mode, SmokeC3_FName,SmokeC3_LName,SmokeC3_LName,searchContactInEmailProspectGrid.Yes, 10);

							if (result.isEmpty()) {
								log(LogStatus.PASS, "Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);

								if(click(driver, market.getStep1CancelBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"bottom cancel button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "clicked on bottom cancel button step 1", YesNo.No);
								}else {
									log(LogStatus.ERROR,"Not able to click on bottom cancel button step 1", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on bottom cancel button step 1");
								}

							} else {
								sa.assertTrue(false, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName);
								log(LogStatus.FAIL, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName);
							log(LogStatus.SKIP, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.Yes);	
						}



					}
					if(j==3) {

						if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, SmokeReportFolderName, SmokeReportName)) {
							log(LogStatus.INFO, "Clicked On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.No);	

							result = hp.selectContactAndVerifyInBulkEmail(environment, mode, SmokeC3_FName,SmokeC3_LName,SmokeC3_LName,searchContactInEmailProspectGrid.Yes, 10);

							if (result.isEmpty()) {
								log(LogStatus.PASS, "Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
								if(click(driver, market.getStep1NextBtn(PageName.BulkEmail, TopOrBottom.BOTTOM,10),"bottom Next button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "clicked on BOTTOM next button", YesNo.No);
									ThreadSleep(2000);
									if(click(driver, market.getStep2CancelBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"top cancel button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on top cancel button step 2", YesNo.No);
									}else {
										log(LogStatus.ERROR,"Not able to click on top cancel button step 2", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on top cancel button step 2");
									}


								}else {
									log(LogStatus.ERROR,"Not able to click on BOTTOM next button so cannot check Top Cancel button on Step2", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on BOTTOM next button so cannot check Top Cancel button on Step2");
								}
							} else {
								sa.assertTrue(false, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName);
								log(LogStatus.FAIL, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName);
							log(LogStatus.SKIP, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.Yes);	
						}


					}
					if(j==4) {

						if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, SmokeReportFolderName, SmokeReportName)) {
							log(LogStatus.INFO, "Clicked On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.No);	

							result = hp.selectContactAndVerifyInBulkEmail(environment, mode, SmokeC3_FName,SmokeC3_LName,SmokeC3_LName,searchContactInEmailProspectGrid.Yes, 10);

							if (result.isEmpty()) {
								log(LogStatus.PASS, "Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);

								if(click(driver, market.getStep1NextBtn(PageName.BulkEmail, TopOrBottom.BOTTOM,10),"bottom Next button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "clicked on BOTTOM next button", YesNo.No);
									ThreadSleep(2000);
									if(click(driver, market.getStep2CancelBtn(PageName.BulkEmail, TopOrBottom.BOTTOM,10),"bottom cancel button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "clicked on bottom cancel button step 2", YesNo.No);
									}else {
										log(LogStatus.ERROR,"Not able to click on bottom cancel button step 2", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on bottom cancel button step 2");
									}
								}else {
									log(LogStatus.ERROR,"Not able to click on BOTTOM next button so cannot check bottom Cancel button on Step2", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on BOTTOM next button so cannot check bottom Cancel button on Step2");
								}

							} else {
								sa.assertTrue(false, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName);
								log(LogStatus.FAIL, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName);
							log(LogStatus.SKIP, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.Yes);	
						}



					}
					if(j==5) {
						if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, SmokeReportFolderName, SmokeReportName)) {
							log(LogStatus.INFO, "Clicked On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.No);	

							result = hp.selectContactAndVerifyInBulkEmail(environment, mode, SmokeC3_FName,SmokeC3_LName,SmokeC3_LName,searchContactInEmailProspectGrid.Yes, 10);

							if (result.isEmpty()) {
								log(LogStatus.PASS, "Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);


								if(click(driver, market.getStep1NextBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"Top Next button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "clicked on top next button step 1", YesNo.No);
									ThreadSleep(2000);
									for(int i=0; i<=3; i++) {
										ThreadSleep(3000);
										if(i==0) {
											if(market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
												log(LogStatus.ERROR, "Select Capital Call Notice template", YesNo.No);
												if(click(driver, market.getStep2NextBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);
												}else {
													log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
													sa.assertTrue(false, "Not able to click on top next button step 2");
												}
											}else {
												log(LogStatus.ERROR,"Not able to click select email template from step 2 so cannot check previous botton of step 3", YesNo.Yes);
												sa.assertTrue(false,"Not able to click select email template from step 2 so cannot check previous botton of step 3");
												break;
											}

										}
										if(i==1) {
											if(click(driver, market.getStep3PreviousBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"Top previous button step 3", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on top previous button step 3", YesNo.No);
											}else {
												log(LogStatus.ERROR,"Not able to click on top previous button step 3", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on top previous button step 3");
											}
										}
										if(i==2) {
											if(click(driver, market.getStep2NextBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);

											}else {
												log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on top next button step 2");
											}
										}

										if(i==3) {
											if(click(driver, market.getStep3PreviousBtn(PageName.BulkEmail, TopOrBottom.BOTTOM,10),"bottom previous button step 3", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on bottom previous button step 3", YesNo.No);
											}else {
												log(LogStatus.ERROR,"Not able to click on bottom previous button step 3", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on bottom previous button step 3");
											}
										}
										if(i==0 && i==2) {
											ThreadSleep(2000);
											ele = fd.getStep2TextEmailing(30);
											if (ele!=null) {
												msg = ele.getText().trim();
												if (HomePageErrorMessage.step3_ReviewAndConfirm.equals(msg)) {
													log(LogStatus.PASS, "Step3 Page Verified : "+msg, YesNo.No);
												} else {
													sa.assertTrue(false, "Step3 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step3_ReviewAndConfirm);
													log(LogStatus.FAIL, "Step3 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step3_ReviewAndConfirm, YesNo.Yes);
												}
											} else {
												sa.assertTrue(false, "Step3 Page Element is null");
												log(LogStatus.FAIL, "Step3 Page Element is null", YesNo.Yes);

											}
										}

									}
								}else {
									log(LogStatus.ERROR,"Not able to click on top next button step 1 so cannot check previous botton of step3", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on top next button step 1 so cannot check previous botton of step3");
								}

							} else {
								sa.assertTrue(false, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName);
								log(LogStatus.FAIL, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName);
							log(LogStatus.SKIP, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.Yes);	
						}

					}
					if(j==6) {

						if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, SmokeReportFolderName, SmokeReportName)) {
							log(LogStatus.INFO, "Clicked On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.No);	

							result = hp.selectContactAndVerifyInBulkEmail(environment, mode, SmokeC3_FName,SmokeC3_LName,SmokeC3_LName,searchContactInEmailProspectGrid.Yes, 10);

							if (result.isEmpty()) {
								log(LogStatus.PASS, "Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);

								if(click(driver, market.getStep1NextBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"Top Next button step 1", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "clicked on top next button step 1", YesNo.No);
									if(market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
										log(LogStatus.ERROR, "Select Capital Call Notice template", YesNo.No);
										if(click(driver, market.getStep2NextBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);
											ThreadSleep(2000);
											if(click(driver, market.getStep3CancelBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"top cancel button step 3", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on top cancel button step 3", YesNo.No);
											}else {
												log(LogStatus.ERROR,"Not able to click on top cancel button step 3", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on top cancel button step 3");
											}
										}else {
											log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on top next button step 2");
										}
									}else {
										log(LogStatus.ERROR,"Not able to click select email template from step 2 so cannot check previous botton of step 3", YesNo.Yes);
										sa.assertTrue(false,"Not able to click select email template from step 2 so cannot check previous botton of step 3");
									}
								}else {
									log(LogStatus.ERROR,"Not able to click on top next button step 1 so cannot check previous botton of step3", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on top next button step 1 so cannot check previous botton of step3");
								}

							} else {
								sa.assertTrue(false, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName);
								log(LogStatus.FAIL, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName);
							log(LogStatus.SKIP, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.Yes);	
						}



					}
					if(j==7) {

						if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, SmokeReportFolderName, SmokeReportName)) {
							log(LogStatus.INFO, "Clicked On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.No);	

							result = hp.selectContactAndVerifyInBulkEmail(environment, mode, SmokeC3_FName,SmokeC3_LName,SmokeC3_LName,searchContactInEmailProspectGrid.Yes, 10);

							if (result.isEmpty()) {
								log(LogStatus.PASS, "Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);

								if(click(driver, market.getStep1NextBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"Top Next button step 1", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "clicked on top next button step 1", YesNo.No);
									if(market.selectEmailTemplateFromEmailProspect(null, "Capital Call Notice")) {
										log(LogStatus.ERROR, "Select Capital Call Notice template", YesNo.No);
										if(click(driver, market.getStep2NextBtn(PageName.BulkEmail, TopOrBottom.TOP,10),"Top Next button step 2 ", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on top next button step 2", YesNo.No);
											ThreadSleep(2000);
											if(click(driver, market.getStep3CancelBtn(PageName.BulkEmail, TopOrBottom.BOTTOM,10),"buttom cancel button step 3", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "clicked on buttom cancel button step 3", YesNo.No);
											}else {
												log(LogStatus.ERROR,"Not able to click on buttom cancel button step 3", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on buttom cancel button step 3");
											}
										}else {
											log(LogStatus.ERROR,"Not able to click on top next button step 2", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on top next button step 2");
										}
									}else {
										log(LogStatus.ERROR,"Not able to click select email template from step 2 so cannot check previous botton of step 3", YesNo.Yes);
										sa.assertTrue(false,"Not able to click select email template from step 2 so cannot check previous botton of step 3");
									}

								}else {
									log(LogStatus.ERROR,"Not able to click on top next button step 1 so cannot check previous botton of step3", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on top next button step 1 so cannot check previous botton of step3");
								}

							} else {
								sa.assertTrue(false, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName);
								log(LogStatus.FAIL, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName);
							log(LogStatus.SKIP, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.Yes);	
						}



					}




				}else {
					sa.assertTrue(false, "Not Able to Click On Bulk Email Link with Navatar Quick Link : "+j);
					log(LogStatus.SKIP, "Not Able to Click On Bulk Email Link with Navatar Quick Link : "+j, YesNo.Yes);
				}
				switchToDefaultContent(driver);
			}
			lp.CRMlogout(environment, mode);
			sa.assertAll();
		}
		
	@Parameters({"environment","mode"})
	@Test
	public void PESmokeTc090_VerifySearchingOfContactAndSendingEmailByBulkEmail(String environment, String mode) {
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
		WebElement ele;
		List<String> result;
		String msg;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	//	lp.CRMLogin(superAdminUserName, adminPassword);
		appLog.info("Login with User");
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String bulkActionNavigationLink=BulkActions_DefaultValues.Bulk_Email.toString();
		if (home.ClickOnItemOnNavatarEdge(navigationMenuName, bulkActionNavigationLink, action.BOOLEAN, 20)) {
				log(LogStatus.INFO, "Clicked On Bulk Email Link with Navatar Quick Link", YesNo.No);
				
				if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(20));
					
				}
			
				if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, SmokeReportFolderName, SmokeReportName)) {
					log(LogStatus.INFO, "Clicked On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.No);	
					
					result = hp.selectContactAndVerifyInBulkEmail(environment, mode, SmokeC3_FName,SmokeC3_LName,SmokeC3_LName,searchContactInEmailProspectGrid.Yes, 10);
					
					if (result.isEmpty()) {
						log(LogStatus.PASS, "Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);	
						
						///
						
						
						if (click(driver, market.getEmailProspectStep1NextBtn(20), "step 1 next button",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Steps 1 Next button", YesNo.No);
							
							ele = hp.step2_BulkEmailPage(environment, mode, 10);
							
							if (ele!=null) {
								msg = ele.getText().trim();
								if (HomePageErrorMessage.Step2_SelectAnEmailTemplate.equals(msg)) {
									log(LogStatus.PASS, "Step2 Page Verified : "+msg, YesNo.No);
								} else {
									sa.assertTrue(false, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate);
									log(LogStatus.FAIL, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate, YesNo.Yes);
								}
							} else {
								sa.assertTrue(false, "Step2 Page Element is null");
								log(LogStatus.FAIL, "Step2 Page Element is null", YesNo.Yes);
							
							}
							
							String expectedResult = "All,Capital Call Notice,Investor Distribution Notice,"+EmailTemplate1_FolderName;
							List<WebElement> lst = allOptionsInDropDrop(driver,
									hp.getBulkEmailFolderDropDownList(environment,mode,20), "folder drop down list");
							if (compareMultipleList(driver, expectedResult, lst).isEmpty()) {
								log(LogStatus.INFO, "Folder Drop Down list All options is verified", YesNo.No);
							} else {
								sa.assertTrue(false, "Folder Drop Down list All options is not verified");
								log(LogStatus.ERROR, "Folder Drop Down list All options is not verified", YesNo.Yes);
							}
							if (market.selectEmailTemplateFromEmailProspect(EmailTemplate1_FolderName, EmailTemplate1_TemplateName)) {
								log(LogStatus.INFO, "PE Test Custom Email Template is selected successfully",
										YesNo.No);
								ele = market.emailProspectPreviewTemplateLink(EmailTemplate1_TemplateName, 30);
								if (ele != null) {
									if (click(driver, ele, EmailTemplate1_TemplateName+" preview link",action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, EmailTemplate1_TemplateName+" preview link", YesNo.No);
										String parentWindow = null;
										parentWindow = switchOnWindow(driver);
										if (parentWindow != null) {
											String xpath = "//td[text()='"+EmailTemplate1_TemplateName+"']";
											ele = isDisplayed(driver,FindElement(driver, xpath, "", action.BOOLEAN, 20), "visibility",20, "PE Test Custom Email Template template name");
											if (ele != null) {
												log(LogStatus.INFO,EmailTemplate1_TemplateName+" is open in preview mode",YesNo.No);
											} else {
												sa.assertTrue(false,EmailTemplate1_TemplateName+" is not open in preview mode");
												log(LogStatus.ERROR,EmailTemplate1_TemplateName+" is not open in preview mode",YesNo.Yes);
											}
											driver.close();
											driver.switchTo().window(parentWindow);
										} else {
											sa.assertTrue(false,"No new window is open after click on" +EmailTemplate1_TemplateName+" preview link");
											log(LogStatus.ERROR,"No new window is open after click on" +EmailTemplate1_TemplateName+" preview link",YesNo.Yes);
										}
										ThreadSleep(5000);
										
										if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
											switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(20));
											
										}
										
										if (click(driver, market.getEmailProspectStep2NextBtn(30), "step 2 next button",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on step 2 next button", YesNo.No);
											ele = hp.step3_BulkEmailPage(environment, mode, 10);
											
											if (ele!=null) {
												msg = ele.getText().trim();
												if (HomePageErrorMessage.step3_ReviewAndConfirm.equals(msg)) {
													log(LogStatus.PASS, "Step3 Page Verified : "+msg, YesNo.No);
												} else {
													sa.assertTrue(false, "Step3 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step3_ReviewAndConfirm);
													log(LogStatus.FAIL, "Step3 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step3_ReviewAndConfirm, YesNo.Yes);
												}
											} else {
												sa.assertTrue(false, "Step3 Page Element is null");
												log(LogStatus.FAIL, "Step3 Page Element is null", YesNo.Yes);
											
											}
											
											ele = hp.getBulkEmailSelectedRecipientErrorMsg(environment, mode, 10);
											if (ele != null) {
												 msg = ele.getText().trim();
												if (MarketingInitiativesPageErrorMsg
														.selectRecipientOnStep3ErrorMsg("1").equals(msg)) {
													log(LogStatus.INFO,MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1")+ " error message is verified",YesNo.No);
												} else {
													sa.assertTrue(false,
															"error message is not verified Expected: "+ MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1")+ " \t Actual : " + msg);
													log(LogStatus.ERROR,
															"error message is not verified Expected: "+ MarketingInitiativesPageErrorMsg	.selectRecipientOnStep3ErrorMsg("1")	+ " \t Actual : " + msg,YesNo.Yes);
												}
											} else {
												sa.assertTrue(false,MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1") + " error message is not visible");
												log(LogStatus.ERROR,MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg("1") + " error message is not visible",YesNo.Yes);
											}
											
											
											List<WebElement> labellist = hp.getBulkEmailProcessingOptionsLableTextList();
											List<WebElement> checkBoxList = hp.getBulkEmailProcessingOptionsCheckBoxList();
											String[] expctRsult = { "BCC Me on One Message", "Use My Signature",
													"Store an Activity for Each Message" };
											for (int i = 0; i < expctRsult.length; i++) {
												for (int j = 0; j < labellist.size(); j++) {
													if (labellist.get(j).getText().trim().contains(expctRsult[i])) {
														if (j != 2) {
															if (checkBoxList.get(j).getAttribute("checked") == null) {
																log(LogStatus.INFO,expctRsult[i] + "check box is not selected",YesNo.No);
															} else {
																sa.assertTrue(false,expctRsult[i] + "check box is selected");
																log(LogStatus.ERROR,expctRsult[i] + "check box is selected",YesNo.Yes);
															}
														} else {
															System.err.println("checked:   "+ checkBoxList.get(j).getAttribute("checked"));
															if (checkBoxList.get(j).getAttribute("checked").contains("true")) {
																log(LogStatus.INFO,expctRsult[i] + "check box is selected",YesNo.No);
															} else {
																sa.assertTrue(false,expctRsult[i] + "check box is not selected");
																log(LogStatus.ERROR,expctRsult[i] + "check box is not selected",YesNo.Yes);
															}
														}
														break;
													}
													if (j == labellist.size() - 1) {
														sa.assertTrue(false, expctRsult[i] + "label is not verified");
														log(LogStatus.ERROR, expctRsult[i] + "label is not verified",YesNo.Yes);
													}
												}
											}
									
												if (click(driver, market.getEmailProspectSendBtn(TopOrBottom.TOP, 30), "send button",
														action.SCROLLANDBOOLEAN)) {
													
													ele = hp.step4_BulkEmailPage(environment, mode, 10);
													
													if (ele!=null) {
														msg = ele.getText().trim();
														if (HomePageErrorMessage.step4_YourEmail.equals(msg)) {
															log(LogStatus.PASS, "Step4 Page Verified : "+msg, YesNo.No);
														} else {
															sa.assertTrue(false, "Step4 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step4_YourEmail);
															log(LogStatus.FAIL, "Step4 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step4_YourEmail, YesNo.Yes);
														}
													} else {
														sa.assertTrue(false, "Step4 Page Element is null");
														log(LogStatus.FAIL, "Step4 Page Element is null", YesNo.Yes);
													
													}
													
													log(LogStatus.INFO, "clicked on send button", YesNo.No);
													ele = market.getEmailProspectSendEmailCongratulationsErrorMsg(30);
													String errorMsg = MarketingInitiativesPageErrorMsg.congratulationErrorMsg
															+ " " + MarketingInitiativesPageErrorMsg
																	.generatedEmailedRecipientErrorMsg("1", "Bulk");
													if (ele != null) {
														 msg = ele.getText().trim();
														if (msg.contains(MarketingInitiativesPageErrorMsg.congratulationErrorMsg) &&
																msg.contains(MarketingInitiativesPageErrorMsg.generatedEmailedRecipientErrorMsg("1", "Bulk"))) {
															log(LogStatus.INFO,"Congratulation Error Message is verified",YesNo.No);
														} else {
															sa.assertTrue(false,"Congratulation Error Message is not verified. Expected: "+ errorMsg + "\t Actual: " + msg);
															log(LogStatus.ERROR,"Congratulation Error Message is not verified. Expected: "+ errorMsg + "\t Actual: " + msg,YesNo.Yes);
														}
													} else {
														sa.assertTrue(false,"Congratulations Error Message is not visible so cannot verify it. Error Msg: "+ errorMsg);
														log(LogStatus.ERROR,"Congratulations Error Message is not visible so cannot verify it. Error Msg: "+ errorMsg,YesNo.Yes);
													}
													if (click(driver, market.getEmailProspectFinishBtn(30),
															"finish button", action.BOOLEAN)) {
														log(LogStatus.INFO, "Clicked on finish button", YesNo.No);
													} else {
														sa.assertTrue(false, "Not able to click on finish button");
														log(LogStatus.ERROR, "Not able to click on finish button",YesNo.Yes);
													}
												} else {
													sa.assertTrue(false,"Not able to click on send button so cannot send email to contact: "+ SmokeC3_FName + " " + SmokeC3_LName);
													log(LogStatus.ERROR,
															"Not able to click on send button so cannot send email to contact: "+ SmokeC3_FName + " " + SmokeC3_LName,YesNo.Yes);
												}


										} else {
											sa.assertTrue(false,"Not able to click on steps 2 nect button so cannot send email to contact : "+ SmokeC3_FName + " " + SmokeC3_LName);
											log(LogStatus.ERROR,"Not able to click on steps 2 nect button so cannot send email to contact : "+ SmokeC3_FName + " " + SmokeC3_LName,YesNo.Yes);
										}

									} else {
										sa.assertTrue(false,"Not able to click on "+EmailTemplate1_TemplateName+ " link so cannot check preview email template");
										log(LogStatus.ERROR,"Not able to click on "+EmailTemplate1_TemplateName+ " link so cannot check preview email template",YesNo.Yes);
									}
								} else {
									sa.assertTrue(false,EmailTemplate1_TemplateName+" preview link is not visible so cannot click on it");
									log(LogStatus.ERROR,EmailTemplate1_TemplateName+" preview link is not visible so cannot click on it",YesNo.Yes);
								}

							} else {
								sa.assertTrue(false,"Not able to select Email Template from Bulk Email so cannot send email to contact "+ SmokeC3_FName + " " + SmokeC3_LName);
								log(LogStatus.ERROR,"Not able to select Email Template from Bulk Email so cannot send email to contact "+ SmokeC3_FName + " " + SmokeC3_LName,YesNo.Yes);
							}

						} else {
							sa.assertTrue(false,"Not able to click on Steps 1 Next button so cannot select email template and send email to contact "+ SmokeC3_FName + " " + SmokeC3_LName);
							log(LogStatus.ERROR,"Not able to click on Steps 1 Next button so cannot select email template and send email to contact "+ SmokeC3_FName + " " + SmokeC3_LName,YesNo.Yes);
						}
						
						
						///
					} else {
						sa.assertTrue(false, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName);
						log(LogStatus.FAIL, "Not Able to Search/Check Contact : "+SmokeC3_FName+" "+SmokeC3_LName, YesNo.Yes);
					}
					
				} else {
					sa.assertTrue(false, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName);
					log(LogStatus.SKIP, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.Yes);	
				}
			
					
			} else {
				sa.assertTrue(false, "Not Able to Click On Bulk Email Link with Navatar Quick Link");
				log(LogStatus.SKIP, "Not Able to Click On Bulk Email Link with Navatar Quick Link", YesNo.Yes);
			}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({ "environment", "mode" })
	@Test
	public void PESmokeTc091_verifyReceivedEmailAndCreatedActivity(String environment, String mode) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		SoftAssert tsa;
		String text = null;
		String date = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
		EmailLib email = new EmailLib();
		try {
			text = email.getEMailContent(gmailUserName, gmailPassword, crmUser1EmailID, SmokeC3_EmailID,EmailTemplate1_Subject);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			sa.assertTrue(false, EmailTemplate1_Subject +" Email Template is not verified from email : " + gmailUserName);
			log(LogStatus.FAIL, EmailTemplate1_Subject+ " Email Template is not verified from email : " + gmailUserName, YesNo.No);
			e.printStackTrace();
		}
		if (text != null) {
			log(LogStatus.INFO, EmailTemplate1_Subject+" template is availble on email " + gmailUserName
					+ "Hence verified send email text", YesNo.No);	
		} else {
			sa.assertTrue(false, EmailTemplate1_Subject+" template is not availble on email " + gmailUserName
					+ " so cannot verify send email text");
			log(LogStatus.ERROR, EmailTemplate1_Subject+" template is not availble on email " + gmailUserName
					+ " so cannot verify send email text", YesNo.No);
		}
	//	lp.CRMLogin(crmUser1EmailID, adminPassword);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		if (cp.clickOnTab(environment, mode, TabName.ContactTab)) {
			log(LogStatus.INFO, "Clicked on Contact Tab",YesNo.No);
			if (cp.clickOnCreatedContact(environment,  SmokeC3_FName, SmokeC3_LName)) {
				log(LogStatus.INFO, "Clicked on Created Contact : "+SmokeC3_FName+" "+SmokeC3_LName,YesNo.No);	
				
				if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Activity_History, null)) {
					log(LogStatus.INFO, "Clicked on Activity History",YesNo.No);	
					
					if (cp.verifyCreatedActivityHistory(environment, mode, EmailTemplate1_Subject)) {
						log(LogStatus.INFO, "Activity verified: "+EmailTemplate1_Subject,YesNo.No);	
					} else {
						sa.assertTrue(false, "Activity not verified: "+EmailTemplate1_Subject);
						log(LogStatus.SKIP, "Activity not verified: "+EmailTemplate1_Subject,YesNo.Yes);
					}
				
				} else {
					sa.assertTrue(false, "Not on Activity History");
					log(LogStatus.SKIP, "Not on Activity History",YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false, "Contact not Found : "+SmokeC3_FName+" "+SmokeC3_LName);
				log(LogStatus.SKIP, "Contact not Found : "+SmokeC3_FName+" "+SmokeC3_LName,YesNo.Yes);	
			}
			
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab",YesNo.Yes);	
		}
		
		lp.CRMlogout(environment, mode);
		sa.assertAll();
	}

}
