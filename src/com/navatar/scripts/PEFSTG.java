package com.navatar.scripts;


import static com.navatar.generic.CommonLib.*;

import static com.navatar.generic.CommonVariables.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;

import com.navatar.generic.EnumConstants.*;

import com.navatar.generic.EnumConstants.ActivityType;
import com.navatar.generic.EnumConstants.CheckBox;
import com.navatar.generic.EnumConstants.ClickOrCheckEnableDisableCheckBox;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.InculdeActivityEnum;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.KeepActivityEnum;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTab;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.RelatedList;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.SDGGridName;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.SortOrder;
import com.navatar.generic.EnumConstants.SubjectElement;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;

import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.CommitmentsPageBusinessLayer;
import com.navatar.pageObjects.ContactsPage;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.EditPageBusinessLayer;
import com.navatar.pageObjects.FieldAndRelationshipPageBusinessLayer;
import com.navatar.pageObjects.FirmPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;

import com.navatar.pageObjects.FundraisingsPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePage;

import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.navatar.pageObjects.PartnershipsPageBusinessLayer;
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
					if (setup.createPEUser(crmUser1FirstName, UserLastName, emailId, crmUserLience, crmUserProfile, null)) {
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
			log(LogStatus.PASS, "Admin: PE Cloud Heading is available on the home page ", YesNo.No);
			sa.assertTrue(true, "Admin: PE Cloud Heading is available on the home page");
		} else {
			log(LogStatus.FAIL, "Admin: PE Cloud is not availale on the home page", YesNo.Yes);
			sa.assertTrue(false, "Admin: PE Cloud is not availale on the home page");
		}

		if (home.VerifyOnlyPECloudOnLauncher()) {
			log(LogStatus.PASS,
					"Admin: PE Could is appearing on the App Launcher page and No other Apps is Appearing on the View All of App Launcher",
					YesNo.No);
			sa.assertTrue(true,
					"Admin: PE Could is appearing on the App Launcher page and No other Apps is Appearing on the App Launcher");
		} else {
			log(LogStatus.FAIL,
					"Admin: Either PE Could is not appearing on the App Launcher page Or other Apps are Appearing on the View All of App Launcher",
					YesNo.Yes);
			sa.assertTrue(false,
					"Admin: Either PE Could is not appearing on the App Launcher page Or other Apps are Appearing on the View All of App Launcher");
		}
		CommonLib.refresh(driver);
		if (home.VerifyOnlyPECloudOnViewAll()) {
			log(LogStatus.PASS,
					"Admin: PE Could is appearing on the View All of App Launcher page and No other Apps is Appearing on the View All of App Launcher",
					YesNo.No);
			sa.assertTrue(true,
					"Admin: PE Could is appearing on the View All of App Launcher page and No other Apps is Appearing on the View All of App Launcher");
		} else {
			log(LogStatus.FAIL,
					"Admin: Either PE Could is not appearing on the View All of App Launcher page Or other Apps are Appearing on the View All of App Launcher",
					YesNo.Yes);
			sa.assertTrue(false,
					"Admin: Either PE Could is not appearing on the View All of App Launcher page Or other Apps are Appearing on the View All of App Launcher");
		}

		
		lp.CRMlogout();
		CommonLib.ThreadSleep(10000);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (home.verifyPECloudOnHomePage()) {
			log(LogStatus.PASS, "User: PE Cloud is availale on the home page", YesNo.No);
			sa.assertTrue(true, "User: PE Cloud is availale on the home page");
		} else {
			log(LogStatus.FAIL, "User: PE Cloud is not availale on the home page", YesNo.Yes);
			sa.assertTrue(false, "User: PE Cloud is not availale on the home page");
		}

		if (home.VerifyOnlyPECloudOnLauncher()) {
			log(LogStatus.PASS,
					"User: PE Could is appearing on the App Launcher page and No other Apps is Appearing on the View All of App Launcher",
					YesNo.No);
			sa.assertTrue(true,
					"User: PE Could is appearing on the App Launcher page and No other Apps is Appearing on the App Launcher");
		} else {
			log(LogStatus.FAIL,
					"User: Either PE Could is not appearing on the App Launcher page Or other Apps are Appearing on the View All of App Launcher",
					YesNo.Yes);
			sa.assertTrue(false,
					"User: Either PE Could is not appearing on the App Launcher page Or other Apps are Appearing on the View All of App Launcher");
		}
		CommonLib.refresh(driver);
		if (home.VerifyOnlyPECloudOnViewAll()) {
			log(LogStatus.PASS,
					"User: PE Could is appearing on the View All of App Launcher page and No other Apps is Appearing on the View All of App Launcher",
					YesNo.No);
			sa.assertTrue(true,
					"User: PE Could is appearing on the View All of App Launcher page and No other Apps is Appearing on the View All of App Launcher");
		} else {
			log(LogStatus.FAIL,
					"User: Either PE Could is not appearing on the View All of App Launcher page Or other Apps are Appearing on the View All of App Launcher",
					YesNo.Yes);
			sa.assertTrue(false,
					"User: Either PE Could is not appearing on the View All of App Launcher page Or other Apps are Appearing on the View All of App Launcher");
		}

		
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc003_VerifyTabsOnHomePage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ArrayList<String> tabsForAdmin = new ArrayList<String>();
		ArrayList<String> tabsForUser = new ArrayList<String>();
		String[] tabs=PEFSTG_3_TabName.split("<break>");
		for(int i=0; i<tabs.length; i++)
		{
			String[] roleAndTabName=tabs[i].split("<tabBreak>");
			if(roleAndTabName[0].equals("Admin"))
			{
				String[] tabNameA=roleAndTabName[1].split("<t>");
				for(int j=0; j<tabNameA.length;j++)
				{
					tabsForAdmin.add(tabNameA[j]);
				}
			}
			else if(roleAndTabName[0].equals("User"))
			{
				String[] tabNameU=roleAndTabName[1].split("<t>");
				for(int j=0; j<tabNameU.length;j++)
				{
					tabsForUser.add(tabNameU[j]);
				}
			}
			else
			{
				log(LogStatus.FAIL, "The role of users are not proper on excel level", YesNo.No);
				sa.assertTrue(false, "The role of users are not proper on excel level");
			}
		}
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ArrayList<String> result = home.verifyHomePageTabs(tabsForAdmin);
		if (result.isEmpty()) {
			log(LogStatus.PASS, "Homepage tabs have been verified for Admin " + tabsForAdmin, YesNo.No);
			sa.assertTrue(true, "Homepage tabs have been verified for Admin " + tabsForAdmin);

		} else {
			log(LogStatus.FAIL, "Homepage tabs are not verified for Admin " + result, YesNo.No);
			sa.assertTrue(false, "Homepage tabs are not verified for Admin " + result);
		}

		lp.CRMlogout();
		ThreadSleep(12000);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);


		ArrayList<String> resultuser = home.verifyHomePageTabs(tabsForUser);
		if (resultuser.isEmpty()) {
			log(LogStatus.PASS, "Homepage tabs have been verified for User " + tabsForUser, YesNo.No);
			sa.assertTrue(true, "Homepage tabs have been verified for User" + tabsForUser);

		} else {
			log(LogStatus.FAIL, "Homepage tabs are not verified for User " + resultuser, YesNo.No);
			sa.assertTrue(false, "Homepage tabs are not verified for User" + resultuser);
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
		ArrayList<String> recordName = new ArrayList<String>();
		ArrayList<String> activeName = new ArrayList<String>();
		ArrayList<String> recordType = new ArrayList<String>();
		String[] recordNameActiveName=PEFSTG_Tc004_RecordType_1.split("<break>");
		for(int i=0; i<recordNameActiveName.length;i++)
		{
			String[] recordNameAndActiveName=recordNameActiveName[i].split("<activeStatus>");
			recordName.add(recordNameAndActiveName[0]);
			activeName.add(recordNameAndActiveName[1]);
		}
		
		String[] recordTypeData=PEFSTG_Tc004_RecordType_2.split("<break>");
		for(int i=0; i<recordTypeData.length;i++)
		{
			recordType.add(recordTypeData[i]);
		}
		
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
		if (BP.clickOnTab(projectName, "Firms")) {

			ArrayList<String> result = fb.verifyFirmRecordType(recordType);

			if (result.isEmpty()) {
				log(LogStatus.PASS, "Record type name has been verified " + recordType, YesNo.Yes);
				sa.assertTrue(true, "Record type name has been verified " + recordType);
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
		ArrayList<String> recordName = new ArrayList<String>();
		ArrayList<String> description = new ArrayList<String>();
		String[] recordNameDescription=PEFSTG_Tc005_RecordType.split("<break>");
		for(int i=0; i<recordNameDescription.length; i++)
		{
			String[] recordNameAndDescription=recordNameDescription[i].split("<desBreak>");
			recordName.add(recordNameAndDescription[0]);
			description.add(recordNameAndDescription[1]);
		}
					
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

					ArrayList<String> result = setup.verifyDescriptionOnFirm(recordName, description);
					if (result.isEmpty()) {
						log(LogStatus.INFO, "The Descriptions of Firm record type have been verified " + description, YesNo.No);
						sa.assertTrue(true, "The Descriptions of Firm record type have been verified" + description);
					} else {
						log(LogStatus.ERROR, "The Descriptions of Firm record type are not verified " + result, YesNo.No);
						sa.assertTrue(false, "The Descriptions of Firm record type are not verified " + result);
					}
				} else {
					log(LogStatus.FAIL, "Not able to click on the Object feature name", YesNo.No);
					sa.assertTrue(false, "Not able to click on the Object feature name");
				}
			} else {
				log(LogStatus.FAIL, "Not able to search the Standard or Custom Object", YesNo.No);
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
					log(LogStatus.PASS, "Default record \"company\" has been verified for System Administrator", YesNo.Yes);
					sa.assertTrue(true, "Default record \"company\" has been verified for System Administrator");
				} else {
					log(LogStatus.FAIL, "Default record \"company\" is not verified for System Administrator", YesNo.Yes);
					sa.assertTrue(false, "Default record \"company\" is not verified for System Administrator");
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
					log(LogStatus.PASS, "Default record \"company\" has been verified for Standard User", YesNo.Yes);
					sa.assertTrue(true, "Default record \"company\" has been verified for Standard User");
				} else {
					log(LogStatus.ERROR, "Default record \"company\" is not verified for Standard User", YesNo.Yes);
					sa.assertTrue(false, "Default record \"company\" is not verified for Standard User");
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
	

	@Parameters("projectName")
    @Test
    public void PEFSTGTc007_VerifyFilesNotesAndAttachmentsRelatedlistforFirm(String projectName) {

        LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
        HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
        SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
        String parentID=null;

        String sectionsInPageLayout = PEFSTG_Tc007_pageLayoutSection;
        String PageLayouts = PEFSTG_Tc007_pageLayoutName;
        String fieldsNotAlreadyAddedLayoutWise = PEFSTG_Tc007_FieldName_1;
        String fieldsAlreadyAddedLayoutWise = PEFSTG_Tc007_FieldName_2;


        lp.CRMLogin(superAdminUserName, adminPassword);
        object tab = object.Firm;

        if(home.clickOnSetUpLink()) {
            log(LogStatus.PASS, "Clicked on setup link", YesNo.No);

             parentID = switchOnWindow(driver);
            if (parentID!=null) {

            if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
                log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);

                if(setup.clickOnObjectFeature(environment, mode, tab, ObjectFeatureName.pageLayouts)) {

                    log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
                    List<String> result= setup.verifyFieldsAvailabilityAndNonAvailabilityOnPageLayout (sectionsInPageLayout,
                             PageLayouts,  fieldsAlreadyAddedLayoutWise,  fieldsNotAlreadyAddedLayoutWise,
                            30);

                    if(result.isEmpty()) {
                        log(LogStatus.PASS, "All fields are verified on Page Layout of object "+tab.toString(),YesNo.No);
                        sa.assertTrue(true,"All fields are verified on Page Layout of object "+tab.toString());
                        
                    }else {

                        log(LogStatus.FAIL, "field are not verified on page layout : "+ result, YesNo.No);
                        sa.assertTrue(false,"field are not verified on page layout :"+result);
                    }

                }else {

                    log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
                    sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
                }

            }else {

                log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
                sa.assertTrue(false,"Unable to search object: "+tab.toString());
            }

            }else {
                log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
                sa.assertTrue(false, "could not find new window to switch");
            }
        }else {

            log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
            sa.assertTrue(false,"Not able to Click on setup link");
        }

        driver.close();
        driver.switchTo().window(parentID);
        lp.CRMlogout();
        sa.assertAll();
    }
	
	
	
    
	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc008_VerifyListViewsOnFirmObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);

		
		ArrayList<String> listview = new ArrayList<String>();
		String[] list=PEFSTG_008_ListViewNames.split("<break>");
		for(int i=0; i<list.length; i++)
		{
			listview.add(list[i]);
		}
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
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


	@Parameters({ "projectName"})	    
	@Test

	public void PEFSTGTc009_VerifyFieldsOnListViewsOnFirmObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP=new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if(BP.clickOnTab(projectName, TabName.InstituitonsTab))
		{
			log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);	

			ArrayList<String> result=FB.verifyFieldsOnListview(PEFSTG_Tc009_FieldName, 50);
			if(result.isEmpty())
			{
				log(LogStatus.PASS, "fields have been verified on the list view", YesNo.No);
				sa.assertTrue(true,"fields has been verified on the list view");
			}
			else
			{
				log(LogStatus.FAIL, "fields are not verified on the List view "+result, YesNo.Yes);
				sa.assertTrue(false,"fields are not verified on the List view "+result);

			}
		}
		else
		{
			log(LogStatus.FAIL, "Not Able to clicked on Firms Tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to clicked on Firms Tab");

		}
		lp.CRMlogout();
		sa.assertAll();
	}



	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc010_VerifyFiltersOnListViews(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		
		String[] listViewName = PEFSTG_10_ListViewName.split("<BreakOn>");
		String[] filterValue = PEFSTG_10_FilterValue.split("<BreakOn>");
		String[] filter = PEFSTG_10_Filter.split("<BreakOn>");
		String[] field = PEFSTG_10_Field.split("<BreakOn>");
		String[] operators = PEFSTG_10_Operators.split("<BreakOn>");
		String[] Filter_Condition = PEFSTG_10_FilterCondition.split("<BreakOn>");
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
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

	@Parameters({ "projectName"})	    
	@Test

	public void PEFSTGTc011_VerifyEntityTypeFieldOnFirmObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP=new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer FRB=new FieldAndRelationshipPageBusinessLayer(driver);

		String[] field=PEFSTG_TC011_FieldName.split("<section>");
		String fieldName=field[0];
		String[] picklistName=field[1].split("<f>");
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
						ObjectFeatureName.FieldAndRelationShip))
				{
					log(LogStatus.INFO, "Clicked on the Field and Relationship object feature name", YesNo.No);

					if(FRB.verifyField(fieldName))
					{
						log(LogStatus.PASS, "Entity type field is visible on the firm object", YesNo.No);
						sa.assertTrue(true, "Entity type field is visible on the firm object");

						ArrayList<String>result=FRB.verifyPicklistValue(fieldName, picklistName,Condition.activate);
						if(result.isEmpty())
						{
							log(LogStatus.PASS, "Picklist value of Entity type field has been verified on the firm object "+picklistName, YesNo.Yes);
							sa.assertTrue(true, "Picklist value of Entity type field has been verified on the firm object"+ picklistName);
						}
						else
						{
							log(LogStatus.FAIL, "Picklist value of Entity type field is not matched on the firm object", YesNo.Yes);
							sa.assertTrue(false, "Picklist value of Entity type field is not matched on the firm object");
						}	
					}
					else
					{
						log(LogStatus.ERROR, "Entity type field is not visible on the firm object", YesNo.Yes);
						sa.assertTrue(false, "Entity type field is not visible on the firm object");
					}

				}
				else
				{
					log(LogStatus.ERROR, "Could not click on the Field and Relationship object feature name", YesNo.Yes);
					sa.assertTrue(false, "Could not click on the Field and Relationship object feature name");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Could not click on the Firm Object manager name", YesNo.Yes);
				sa.assertTrue(false, "Could not click on the Firm Object manager name");
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on the setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on the setup link");
		}
		lp.CRMlogout();
		sa.assertAll();	
	}


	@Parameters({ "projectName"})
	@Test
	public void PEFSTGTc012_VerifyAssignmentOfEntityTypePicklistValuesOnDifferentFirmRecordTypes(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		FirmPageBusinessLayer fb=new FirmPageBusinessLayer(driver);
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

				if (setup.clickOnObjectFeature(projectName, mode, object.Firm,
						ObjectFeatureName.recordTypes))
				{
					ArrayList<String> result=setup.verifyPicklistOnDifferentRecordType(PEFSTG_TC012_FieldName, 50);
					if(result.isEmpty())
					{
						log(LogStatus.PASS, "Entity type picklist value has been verified on different Firm Record type", YesNo.Yes);
						sa.assertTrue(true, "Entity type picklist value has been verified on different Firm Record type");
					}

					else
					{
						log(LogStatus.FAIL, "Entity type picklist values are not verified on different Firm Record type "+result, YesNo.Yes);
						sa.assertTrue(false, "Entity type picklist values are not verified on different Firm Record type "+result);
					}
				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on the Object feature name", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on the Object feature name");
				}
			}
			else
			{
				log(LogStatus.FAIL, "Not able to search the Standard or Custom Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to search the Standard or Custom Object");

			}

		}
		else
		{
			log(LogStatus.FAIL, "Not able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not able to open the setup page");
		}

		lp.CRMlogout();
		sa.assertAll();	
	}


	@Parameters({ "projectName"})
	@Test
	public void PEFSTGTc013_VerifyPagLayoutAssignmentForFirmRecordTypes(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		FirmPageBusinessLayer fb=new FirmPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ArrayList<String> recordType=new ArrayList<String>();
		ArrayList<String> userProfile=new ArrayList<String>();


		String[] data=PEFSTG_Tc013_RecordType.split("<break>");
		String parentWindowID=null;
		for(int i=0; i<data.length; i++)
		{
			recordType.add(data[i]);
		}		
		String[] userProfileData=PEFSTG_Tc013_UserProfile.split("<break>");
		for(int i=0; i<userProfileData.length; i++)
		{
			userProfile.add(userProfileData[i]);
		}

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

			if (setup.searchStandardOrCustomObject(projectName, mode, object.Firm)) {

				if (setup.clickOnObjectFeature(projectName, mode, object.Firm,
						ObjectFeatureName.pageLayouts))
				{


					ArrayList<String> result=setup.verifyPageLayoutAssignment(recordType,userProfile,50);
					if(result.isEmpty())
					{
						log(LogStatus.PASS, "Record Pages assignment for Firm Record Types has been verified", YesNo.Yes);
						sa.assertTrue(true, "Record Pages assignment for Firm Record Types has been verified");
					}
					else
					{
						log(LogStatus.FAIL, "Record Pages assignment for Firm Record Types is not verified "+result, YesNo.Yes);
						sa.assertTrue(false, "Record Pages assignment for Firm Record Types is not verified "+result);

					}

				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on the Object feature name", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on the Object feature name");
				}
			}
			else
			{
				log(LogStatus.FAIL, "Not able to search the Standard or Custom Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to search the Standard or Custom Object");

			}

		}
		else
		{
			log(LogStatus.FAIL, "Not able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not able to open the setup page");
		}
		driver.close();
		driver.switchTo().window(parentWindowID);

		lp.CRMlogout();
		sa.assertAll();	
	}


	@Parameters({ "projectName"})
	@Test
	public void PEFSTGTc014_VerifyLightningRecordPagesAssignmentForFirmRecordTypes(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		FirmPageBusinessLayer fb=new FirmPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ArrayList<String> result=new ArrayList<String>();
		String parentWindowID=null;
		String abc=PEFSTG_Tc014_UserProfile;
		String abcd=PEFSTG_Tc014_RecordType;

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

			if (setup.searchStandardOrCustomObject(projectName, mode, object.Firm)) {

				if (setup.clickOnObjectFeature(projectName, mode, object.Firm,
						ObjectFeatureName.lightningRecordPages))
				{

					result=setup.VerifyLightningRecordPagesAssignment(PEFSTG_Tc014_RecordType,PEFSTG_Tc014_UserProfile,"APP, RECORD TYPE, AND PROFILE",50);
					if(result.isEmpty())
					{
						log(LogStatus.PASS, "Lightning Record Pages assignment for Firm Record Types has been verified", YesNo.Yes);
						sa.assertTrue(true, "Lightning Record Pages assignment for Firm Record Types has been verified");
					}
					else
					{
						log(LogStatus.FAIL, "Lightning Record Pages assignment for Firm Record Types is not verified "+result, YesNo.Yes);
						sa.assertTrue(false, "Lightning Record Pages assignment for Firm Record Types is not verified "+result);

					}

				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on the Object feature name", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on the Object feature name");
				}
			}
			else
			{
				log(LogStatus.FAIL, "Not able to search the Standard or Custom Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to search the Standard or Custom Object");

			}

		}
		else
		{
			log(LogStatus.FAIL, "Not able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not able to open the setup page");
		}
		driver.close();
		driver.switchTo().window(parentWindowID);

		lp.CRMlogout();
		sa.assertAll();	
	}



	@Parameters({ "projectName"})	    
	@Test

	public void PEFSTGTc015_VerifyPicklistVaulesForIndustryFieldOnFirmObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP=new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		FieldAndRelationshipPageBusinessLayer FRB=new FieldAndRelationshipPageBusinessLayer(driver);

		String fieldName1,fieldName2;

		String[] allfieldNamePickListValue=PEFSTG_TC015_FieldName.split("<break>");

		String[] fieldNamePickList1=allfieldNamePickListValue[0].split("<section>");
		fieldName1=fieldNamePickList1[0];
		String[] activePickListValue=fieldNamePickList1[1].split("<f>");

		String[] fieldNamePickList2=allfieldNamePickListValue[1].split("<section>");
		fieldName2=fieldNamePickList2[0];
		String[] deactivePickListValue=fieldNamePickList2[1].split("<f>");

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ArrayList<String>result=new ArrayList<String>();
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
						ObjectFeatureName.FieldAndRelationShip))
				{
					log(LogStatus.INFO, "Clicked on the Field and Relationship object feature name", YesNo.No);

					result=FRB.verifyPicklistValue(fieldName1, activePickListValue,Condition.activate);
					if(result.isEmpty())
					{
						log(LogStatus.PASS, "Picklist value of "+fieldName1+" field has been verified on the firm object "+activePickListValue, YesNo.Yes);
						sa.assertTrue(true, "Picklist value of "+fieldName1+" field has been verified on the firm object "+ activePickListValue);
					}
					else
					{
						log(LogStatus.FAIL, "Picklist value of "+fieldName1+" field is not matched on the firm object "+result, YesNo.Yes);
						sa.assertTrue(false, "Picklist value of "+fieldName1+" field is not matched on the firm object "+result);
					}		

					result=FRB.verifyPicklistValue(fieldName2, deactivePickListValue,Condition.deactivate);
					if(result.isEmpty())
					{
						log(LogStatus.PASS, "Picklist value of "+fieldName2+" field has been verified on the firm object "+deactivePickListValue, YesNo.Yes);
						sa.assertTrue(true, "Picklist value of "+fieldName2+" field has been verified on the firm object "+ deactivePickListValue);
					}
					else
					{
						log(LogStatus.FAIL, "Picklist value of "+fieldName2+" field is not matched on the firm object "+result, YesNo.Yes);
						sa.assertTrue(false, "Picklist value of "+fieldName2+" field is not matched on the firm object "+result);
					}	
				}
				else
				{
					log(LogStatus.ERROR, "Could not click on the Field and Relationship object feature name", YesNo.Yes);
					sa.assertTrue(false, "Could not click on the Field and Relationship object feature name");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Could not click on the Firm Object manager name", YesNo.Yes);
				sa.assertTrue(false, "Could not click on the Firm Object manager name");
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on the setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on the setup link");
		}
		lp.CRMlogout();
		sa.assertAll();	
	}	
	
	@Parameters({ "projectName"})
	@Test
	public void PEFSTGTc016_CreateANewAdvisorRecordAndVerifyPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		FirmPageBusinessLayer fb=new FirmPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip=new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
	
		if (BP.clickOnTab(projectName, "Firms")) {
			if (ip.createInstitution(projectName, environment, mode, PEFSTGINS1_Institution,PEFSTGINS1_RecordType, null,null)) {
				log(LogStatus.PASS,"successfully Created Account/Entity : "+PEFSTGINS1_Institution+" of record type : "+PEFSTGINS1_RecordType,YesNo.No);	
				sa.assertTrue(true,"successfully Created Account/Entity : "+PEFSTGINS1_Institution+" of record type : "+PEFSTGINS1_RecordType);
				
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+PEFSTGINS1_Institution+" of record type : "+PEFSTGINS1_RecordType);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+PEFSTGINS1_Institution+" of record type : "+PEFSTGINS1_RecordType,YesNo.Yes);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on firm tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on firm tab");
		}


		lp.CRMlogout();
		sa.assertAll();	
	}

	@Parameters({ "projectName"})
	@Test
	public void PEFSTGTc017_VerifySortingOnPicklistFieldOnAdvisorRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FirmPageBusinessLayer fb=new FirmPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (BP.clickOnTab(projectName, "Firms")) {
			if(BP.clickOnAlreadyCreatedItem(projectName, PEFSTGINS1_Institution, TabName.InstituitonsTab, 50))
			{
				log(LogStatus.INFO,"successfully open the record : "+PEFSTGINS1_Institution,YesNo.No);	
				if(fb.VerifySortingOnEntityTypeField())
				{
					log(LogStatus.PASS,"Sorting order of Entity type's list has been verified",YesNo.Yes);
					sa.assertTrue(true,"Sorting order of Entity type's list has been verified");
				}
				else
				{
					log(LogStatus.FAIL,"Sorting order of Entity type's list are not verified",YesNo.Yes);
					sa.assertTrue(false,"Sorting order of Entity type's list are not verified");
				}

			}
			else
			{
				log(LogStatus.FAIL,"Not Able to open the record : "+PEFSTGINS1_Institution,YesNo.Yes);
				sa.assertTrue(false,"Not Able to open the record : "+PEFSTGINS1_Institution);

			}

		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on firm tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on firm tab");
		}
		lp.CRMlogout();
		sa.assertAll();	
	}

	@Parameters({ "projectName"})
	@Test
	public void PEFSTGTc018_VerifyHighlightPanelOnAdvisorRecordPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FirmPageBusinessLayer fb=new FirmPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ArrayList<String>highlightedValueExpected=new ArrayList<String>();
		highlightedValueExpected.add(PEFSTGINS1_Institution);
		highlightedValueExpected.add("Entity Type");
		highlightedValueExpected.add("Website");

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (BP.clickOnTab(projectName, "Firms")) {
			if(BP.clickOnAlreadyCreatedItem(projectName, PEFSTGINS1_Institution, TabName.InstituitonsTab, 50))
			{
				log(LogStatus.INFO,"successfully open the record : "+PEFSTGINS1_Institution,YesNo.No);
				ArrayList<String>result=fb.verifyHighlightPanel(highlightedValueExpected);
				if(result.isEmpty())
				{
					log(LogStatus.PASS,"Highlight panel advisor has been verified on Advisor record page",YesNo.Yes);
					sa.assertTrue(true,"Highlight panel advisor has been verified on Advisor record page");
				}
				else
				{
					log(LogStatus.FAIL,"Highlight panel advisor is not verified on Advisor record page",YesNo.Yes);
					sa.assertTrue(false,"Highlight panel advisor is not verified on Advisor record page");
				
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open the record : "+PEFSTGINS1_Institution, YesNo.Yes);
				sa.assertTrue(false, "Not able to open the record : "+PEFSTGINS1_Institution);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on firm tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on firm tab");
		}
		lp.CRMlogout();
		sa.assertAll();	
	}
	
	@Parameters({ "projectName"})
	 @Test
	public void PEFSTGTc019_VerifyButtonsAndTabsOnAdvisorRecordPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FirmPageBusinessLayer fb=new FirmPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ArrayList<String>highlightedValueExpected=new ArrayList<String>();
		
		String[] buttonName=PEFSTG_Tc019_buttonName.split("<downArrowBreak>");	
		List<String> ExpectedButtonsOnPage = Arrays.asList(buttonName[0].split("<Break>"));
		List<String> ExpectedButtonsInDownArrowButton = Arrays.asList(buttonName[1].split("<Break>"));
		List<String> ExpectedTabsOnPage = Arrays.asList(PEFSTG_Tc019_tabName.split("<Break>"));
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (BP.clickOnTab(projectName, "Firms")) {
			if(BP.clickOnAlreadyCreatedItem(projectName, PEFSTGINS1_Institution, TabName.InstituitonsTab, 50))
			{
				log(LogStatus.INFO,"successfully open the record : "+PEFSTGINS1_Institution,YesNo.No);
				List<String> negativeResultOfButtons = BP.verifyButtonsOnAPageAndInDownArrowButton(ExpectedButtonsOnPage,
						ExpectedButtonsInDownArrowButton);
				if (negativeResultOfButtons.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Buttons on Page & in DownArrow Button-----", YesNo.No);
					sa.assertTrue(true, "-----Verified Buttons on Page & in DownArrow Button-----");
				} else {
					sa.assertTrue(false, "-----Not Verified Buttons on Page & in DownArrow Button-----: " + negativeResultOfButtons);
					log(LogStatus.FAIL, "Not Verified Buttons on Page & in DownArrow Button-----: " + negativeResultOfButtons,
							YesNo.Yes);
				}
				
				List<String> negativeResultOfTabs = BP.verifyTabsOnAPage(ExpectedTabsOnPage);
				if (negativeResultOfTabs.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Tabs on Page-----", YesNo.No);
					sa.assertTrue(true, "-----Not Verified Tabs on Page-----:" + negativeResultOfTabs);
				} else {
					sa.assertTrue(false, "-----Not Verified Tabs on Page-----:" + negativeResultOfTabs);
					log(LogStatus.FAIL, "-----Not Verified Buttons on Page-----:" + negativeResultOfTabs,
							YesNo.Yes);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open the record : "+PEFSTGINS1_Institution, YesNo.Yes);
				sa.assertTrue(false, "Not able to open the record : "+PEFSTGINS1_Institution);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on firm tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on firm tab");
		}
		lp.CRMlogout();
		sa.assertAll();	
	}
	
	
	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc0020_VerifyCustomActionsOnAdvisorRecordPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FirmPageBusinessLayer fb = new FirmPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

/*		String[] recordPageButtonNames = " <Section>New Affiliation<Section>New Client".split("<Section>");

		String[] recordPageLabelValuesAndTypesOfElements = "Salutation<Br>Mr.<Br>DropDown<Break>First Name<Br>pe_advisor_record<Br>TextBox<Break>Last Name<Br>Contact 1<Br>TextBox<Break>Primary Contact<Br>select<Br>Checkbox<Break>Title<Br>TitleAuto1231<Br>TextBox<Break>Phone<Br>3214236545<Br>TextBox<Break>Email<Br>pe_contact3243@yopmail.com<Br>TextBox"
				.split("<Section>");

		String[][] requiredRecordPageLabelValuesAndTypesOfElements = null;
		int recordPageLoopCount = 0;
		for (String recordPageLabelValuesAndTypesOfElement : recordPageLabelValuesAndTypesOfElements) {
			requiredRecordPageLabelValuesAndTypesOfElements = new String[recordPageLabelValuesAndTypesOfElement
					.split("<Break>").length][3];
			int i = 0;
			for (String recordPageLabelValuesAndTypesOfEle : recordPageLabelValuesAndTypesOfElement.split("<Break>")) {
				for (int j = 0; j < 3; j++) {
					requiredRecordPageLabelValuesAndTypesOfElements[i][j] = recordPageLabelValuesAndTypesOfEle
							.split("<Br>")[j];
				}
				i++;
			}

			if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {

				if(bp.clickOnAlreadyCreatedItem(projectName, PEFSTGINS1_Institution, TabName.InstituitonsTab, 50))
				{
					log(LogStatus.INFO,"successfully open the record : "+PEFSTGINS1_Institution,YesNo.No);	
				
							if (bp.enterDetailsForRecord(projectName, requiredRecordPageLabelValuesAndTypesOfElements,
									action.SCROLLANDBOOLEAN, 30))

							{

								if (click(driver, bp.pageLevelCreateRecordPopupSaveOrCancelButton("Save", 30),
										"Save Button ", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Save Button on "
											+ recordPageButtonNames[recordPageLoopCount] + " Form Page", YesNo.No);
								}

								else {
									log(LogStatus.ERROR,
											"Not Able to Click on Save Button on "
													+ recordPageButtonNames[recordPageLoopCount] + " Form Page",
											YesNo.Yes);

								}

							} else {
								log(LogStatus.ERROR,
										"Not Able to enter Values on " + recordPageButtonNames[recordPageLoopCount]
												+ " Form Page, So Not Able to Create New Refferal Record",
										YesNo.Yes);

								click(driver, bp.pageLevelCreateRecordPopupSaveOrCancelButton("Cancel", 30),
										"Cancel Button ", action.SCROLLANDBOOLEAN);

							}


					}  else
				{
					log(LogStatus.ERROR,"Not able to open the record "+PEFSTGINS1_Institution,YesNo.No);	
					sa.assertTrue(false,"Not able to open the record "+PEFSTGINS1_Institution);
				}
			}
			else
			{
				log(LogStatus.ERROR,"Not able to click on Firm tab",YesNo.No);	
				sa.assertTrue(false,"Not able to click on Firm tab");
			}
			

			recordPageLoopCount++;
		}
*/
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (bp.clickOnTab(projectName, "Firms")) {
			log(LogStatus.INFO,"Clicked on Firms Tab",YesNo.No);
			if(bp.clickOnAlreadyCreatedItem(projectName, PEFSTGINS1_Institution, TabName.InstituitonsTab, 50))
			{
				log(LogStatus.INFO,"successfully open the record : "+PEFSTGINS1_Institution,YesNo.No);	
				
				if(fb.verifyCustomAction(projectName,PEFSTG_Tc020_RecordType))
				{
					log(LogStatus.PASS, "Custom actions has been verified",YesNo.No);
					 sa.assertTrue(true,"Custom actions has been verified ");
					
				}
				else
				{
					log(LogStatus.ERROR,"Custom actions are not verified",YesNo.No);
					sa.assertTrue(false,"Custom actions are not verified");
				}
								
			}
			else
			{
				log(LogStatus.ERROR,"Not able to open the record "+PEFSTGINS1_Institution,YesNo.No);	
				sa.assertTrue(false,"Not able to open the record "+PEFSTGINS1_Institution);
			}
		}
		else
		{
			log(LogStatus.ERROR,"Not able to click on Firm tab",YesNo.No);	
			sa.assertTrue(false,"Not able to click on Firm tab");
		}
		
		lp.CRMlogout();
		sa.assertAll();	
	}
	
	
	
	@Parameters({ "projectName" })

	@Test
	public void PEFSTGTc0021_verifyDetailTabOnAdvisor(String projectName) throws InterruptedException {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer CPB = new ContactsPageBusinessLayer(driver);		
		FirmPageBusinessLayer FPB = new FirmPageBusinessLayer(driver);		
		ArrayList<String> DetailsTab = new ArrayList<String>();
		
		ArrayList<String> ExpectedDetailsTab = new ArrayList<String>();
		ExpectedDetailsTab.add("Advisor Information");
		ExpectedDetailsTab.add("Description");
		ExpectedDetailsTab.add("Address");
		ExpectedDetailsTab.add("System Information");		
		
		ArrayList<String> ExpectedAdvisorTab = new ArrayList<String>();
		ExpectedAdvisorTab.add("Legal Name"); 
		ExpectedAdvisorTab.add("Firm Owner");
		ExpectedAdvisorTab.add("Entity Type");
		ExpectedAdvisorTab.add("Phone");
		ExpectedAdvisorTab.add("Record Type");
		ExpectedAdvisorTab.add("Website");  
	    
		ArrayList<String> ExpectedDescriptionTab = new ArrayList<String>();
		ExpectedDescriptionTab.add("Description");		
		
		ArrayList<String> ExpectedAddressTab = new ArrayList<String>();
		ExpectedAddressTab.add("Address"); 		
	
		ArrayList<String> ExpectedSystemInformationTab = new ArrayList<String>();
		ExpectedSystemInformationTab.add("Created By"); 
		ExpectedSystemInformationTab.add("Parent Firm");
		ExpectedSystemInformationTab.add("Last Modified By");
	   
		HashMap<String,ArrayList<String>> labelListMap = new HashMap <String,ArrayList<String>>();
		labelListMap.put("Advisor Information", ExpectedAdvisorTab);
		labelListMap.put("Description", ExpectedDescriptionTab);
		labelListMap.put("Address", ExpectedAddressTab);
		labelListMap.put("System Information", ExpectedSystemInformationTab);
				
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);	
		if (BP.clickOnTab(projectName, projectName, TabName.InstituitonsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
			if (CPB.clickOnCreatedContact(projectName,null,"pe_advisor_record01")) {
				log(LogStatus.PASS, "Clicked on created Firm", YesNo.No);
				sa.assertTrue(true, "Clicked on created Frm");
				ArrayList<String> result = FPB.DetailTabVerify(ExpectedDetailsTab,labelListMap);
                if(!result.isEmpty())
                {
                    log(LogStatus.PASS, "Detail tab has been verified ", YesNo.No);
                    sa.assertTrue(true,"Detail Tab has been verified ");
                }
                else
                {
                    log(LogStatus.FAIL, "Detail Tab does not contain"+result, YesNo.Yes);
                    sa.assertTrue(false,"Detail Tab does not contain "+result);

                }
				
			}
			  else {
				log(LogStatus.ERROR, "Not able to click on Firm Tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Firm Tab");
			}

			}
			lp.CRMlogout();
			sa.assertAll();


		}
	
	
	@Parameters({ "projectName"})
	@Test
	public void PEFSTGTc0022_VerifyContactsTabOnAdvisorRecordPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FirmPageBusinessLayer fb=new FirmPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);	
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		
		if (BP.clickOnTab(projectName, "Firms")) {
			if(BP.clickOnAlreadyCreatedItem(projectName, PEFSTGINS1_Institution, TabName.InstituitonsTab, 50))
			{
				log(LogStatus.INFO,"successfully open the record : "+PEFSTGINS1_Institution,YesNo.No);
				CommonLib.refresh(driver);
		        String xPath="//a[text()=\"Contacts\" and @role=\"tab\"]";
		        WebElement ele=CommonLib.FindElement(driver, xPath, "Contacts Tab", action.SCROLLANDBOOLEAN, 50);
				if(CommonLib.click(driver, ele, "Contact Tab", action.SCROLLANDBOOLEAN))
				{
					ArrayList<String> result=fb.VerifyContactsTabOnAdvisorRecordPage(PEFSTG_Tc022_RecordType1, PEFSTG_Tc022_RecordType2,50);
					if(result.isEmpty())
					{
						log(LogStatus.PASS,"Contacts Tab On Advisor Record Page has been verified",YesNo.No);
						sa.assertTrue(true, "Contacts Tab On Advisor Record Page has been verified");
					}
					else
					{
						log(LogStatus.ERROR,"Contacts Tab On Advisor Record Page has been verified",YesNo.No);
						sa.assertTrue(false, "Contacts Tab On Advisor Record Page has been verified");	
					}
				}
				else
				{
					log(LogStatus.ERROR,"Not able to click on Contact tab",YesNo.No);
					sa.assertTrue(false, "Not able to click on Contact tab");					
				}			
			}
			else
			{
				log(LogStatus.ERROR,"Not able to open the record : "+PEFSTGINS1_Institution,YesNo.No);
				sa.assertTrue(false,"Not able to open the record : "+PEFSTGINS1_Institution);				
			}
		}
		else
		{
			log(LogStatus.ERROR,"Not able to click on firm Tab",YesNo.No);
			sa.assertTrue(false,"Not able to click on firm Tab");			
		}
		lp.CRMlogout();
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc0023_VerifySortingOnAllFieldsOfContactsAndAffiliationsGridOnContactsTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		

		String data="Contacts<fieldBreak>Name<f>Title<f>Email<f>Phone<f>Last Touchpoint<defaultSortBreak>Name<sortOrder>ASC<noSortOrderField>null<break>Affiliations<fieldBreak>Name<f>Firm<f>Start Date<f>End Date<f>Last Touchpoint<defaultSortBreak>Start Date<sortOrder>DESC<noSortOrderField>Role";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if(BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
			log(LogStatus.INFO, "Clicked On Firms Tab", YesNo.No);

			if(BP.clickOnAlreadyCreatedItem(projectName, "pe_advisor_record01", TabName.InstituitonsTab, 50))
			{

				String xPath="//a[@data-label='Contacts']";
				WebElement ele=CommonLib.FindElement(driver, xPath,"Contact tab", action.SCROLLANDBOOLEAN, 50);

				if(click(driver, ele, "Contacts Tab", action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "Clicked on Contact tab button", YesNo.No);	
				  ArrayList<String> result=FB.VerifySortingOnFields(data);
				  if(result.isEmpty())
				  {
					  log(LogStatus.PASS, "Sorting on All fields of Contacts and Affiliations grid on Contacts tab has been verified", YesNo.No);
					  sa.assertTrue(true, "Sorting on All fields of Contacts and Affiliations grid on Contacts tab has been verified");
				  }
				  else
				  {
					  log(LogStatus.FAIL, "Sorting on All fields of Contacts and Affiliations grid on Contacts tab have not been verified -- "+result, YesNo.No);
					  sa.assertTrue(false, "Sorting on All fields of Contacts and Affiliations grid on Contacts tab have not been verified -- "+result);
				  
				  }
					
				}
				else
				{
					log(LogStatus.FAIL, "Not able to Clicked On Contact tab", YesNo.No);
					sa.assertTrue(false, "Not able to Clicked On Contact tab");
				}
			}
			else {
				log(LogStatus.FAIL, "Unable the Enter Name and not clicked on it", YesNo.No);
				sa.assertTrue(false, "Unable the Enter Name and not clicked on it");
			}
		}else {
			log(LogStatus.FAIL, "Not able to clicked on Firms Tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to clicked on Firms Tab");
		}
		lp.CRMlogout();
		sa.assertAll();

	}
	

	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc0024_VerifyPageRedirectionForTheClickableFieldsOnContactsAndAffiliationsGridOnContactsTab (String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		ArrayList<String> tabName=new ArrayList<String>();
		tabName.add("Contacts");
		tabName.add("Affiliations");
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		for(int i=0; i<tabName.size(); i++)
		{
			if (BP.clickOnTab(projectName, "Firms")) {
				if(BP.clickOnAlreadyCreatedItem(projectName, PEFSTGINS1_Institution, TabName.InstituitonsTab, 50))
				{
					ArrayList<String> result= FB.VerifyPageRedirectionForTheClickableFieldsOnContactsAndAffiliations(PEFSTG_Tc025_RecordType1,PEFSTG_Tc025_RecordType1,tabName.get(i));
					if(result.isEmpty())
					{
						log(LogStatus.PASS, "Page redirection for the clickable fields on Contacts and Affiliations grid on Contacts tab has been verified", YesNo.No);
						sa.assertTrue(true, "Page redirection for the clickable fields on Contacts and Affiliations grid on Contacts tab has been verified");
					}
					else
					{
						log(LogStatus.FAIL, "Page redirection for the clickable fields on Contacts and Affiliations grid on Contacts tab is not verified "+result, YesNo.No);
						sa.assertTrue(false, "Page redirection for the clickable fields on Contacts and Affiliations grid on Contacts tab is not verified "+result);
					}
				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on "+PEFSTGINS1_Institution, YesNo.No);
					sa.assertTrue(false, "Not able to click on "+PEFSTGINS1_Institution);

				}
			}
			else
			{
				log(LogStatus.FAIL, "Not able to open Firm Tab", YesNo.No);
				sa.assertTrue(false, "Not able to Firm Tab");
			}

		}

		lp.CRMlogout();
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc0025_VerifyInlineEditingForContactsAndAffiliationsGridOnContactsTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		String val="Contacts<gridBreak>Title<v>textbox<f>Email<v>textbox<f>Phone<v>textbox<f>Last Touchpoint<v>calender<break>Affiliations<gridBreak>Role<v>multipicklist<f>Start Date<v>calender<f>End Date<v>calender";
		ArrayList<String> tabName=new ArrayList<String>();
		tabName.add("Contacts");
		tabName.add("Affiliations");
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		if (BP.clickOnTab(projectName, "Firms")) {
			if(BP.clickOnAlreadyCreatedItem(projectName, "pe_advisor_record01", TabName.InstituitonsTab, 50))
			{

				String xPath="//a[@data-label='Contacts']";
				WebElement ele=CommonLib.FindElement(driver, xPath,"Contact tab", action.SCROLLANDBOOLEAN, 50);

				if(click(driver, ele, "Contacts Tab", action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "Clicked on Contact tab button", YesNo.No);	

					ArrayList<String> result= FB.VerifyInlineEditingForContactsAndAffiliationsGrid(val);
					if(result.isEmpty())
					{
						log(LogStatus.PASS, "Inline editing for Contacts and Affiliations grid on Contacts tab has been verified", YesNo.No);
						sa.assertTrue(true, "Inline editing for Contacts and Affiliations grid on Contacts tab has been verified");
					}
					else
					{
						log(LogStatus.FAIL, "Inline editing for Contacts and Affiliations grid on Contacts tab is not verified", YesNo.No);
						sa.assertTrue(false, "Inline editing for Contacts and Affiliations grid on Contacts tab is not verified");

					}
				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on Contact tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Contact tab");

				}
			}
			else
			{
				log(LogStatus.FAIL, "Not able to click on "+PEFSTGINS1_Institution, YesNo.No);
				sa.assertTrue(false, "Not able to click on "+PEFSTGINS1_Institution);

			}
		}
		else
		{
			log(LogStatus.FAIL, "Not able to open Firm Tab", YesNo.No);
			sa.assertTrue(false, "Not able to Firm Tab");
		}

		lp.CRMlogout();
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc0026_01_PreconditionForClientTab(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer CPB = new ContactsPageBusinessLayer(driver);		
		FirmPageBusinessLayer FPB = new FirmPageBusinessLayer(driver);	
		FundRaisingPageBusinessLayer FRP =new FundRaisingPageBusinessLayer(driver);

		String institutionName="pe_advisor_Instituition1";
		String xPath="";
		WebElement ele;
		ArrayList<String> labelName=new ArrayList<String>();
		ArrayList<String> value=new ArrayList<String>();
		ArrayList<String> inputType=new ArrayList<String>();

		InstitutionsPageBusinessLayer ip=new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		if (BP.clickOnTab(projectName, "Firms")) {
			if (ip.createInstitution(projectName, environment, mode, institutionName, "Institution",null,null)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+PEFSTGINS1_Institution+" of record type : "+PEFSTGINS1_RecordType,YesNo.No);	

				labelName.add("Salutation");
				labelName.add("First Name");
				labelName.add("Last Name");
				labelName.add("Primary Contact");

				value.add("Mr.");
				value.add("Automation");
				value.add("Test 1");
				value.add("select");

				inputType.add("dropdown"); 
				inputType.add("TextBox");
				inputType.add("TextBox");
				inputType.add("Checkbox");
				
				CommonLib.ThreadSleep(4000);
				
				if(CPB.CreateNewContactFromTab(projectName,labelName,value,inputType))
				{
					log(LogStatus.INFO,"Contact has been created",YesNo.No);	
					labelName.removeAll(labelName);
					value.removeAll(value);
					inputType.removeAll(inputType);

					xPath="//a[@title='Fundraisings']";
					ele=FindElement(driver,xPath,"Fundraisings Tab", action.THROWEXCEPTION, 30);
					if(ele!=null)
					{
						if(CommonLib.clickUsingJavaScript(driver, ele, "Fundraising tab", action.BOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on Fundraising tab", YesNo.Yes);
							if(FRP.createFundRaising(environment, mode, "Auto test 1", "Sumo Logic - User", institutionName, "1st Closing", "Prospect", "5000", "Status notes","2022","July","22"))
							{
								log(LogStatus.PASS, "Fundraising has been created", YesNo.No);
								sa.assertTrue(true, "Fundraising has been created");

								labelName.add("Contact");
								labelName.add("Role");
								labelName.add("Firm");

								value.add("pe_advisor_record Contact");
								value.add("Advisor");
								value.add(institutionName);

								inputType.add("SearchDropDown"); 
								inputType.add("dropdown");
								inputType.add("SearchDropDown");

								if(FRP.CreateNewFundraisingContactFromTab(projectName,labelName,value,inputType))
								{
									log(LogStatus.PASS, "New Fundraising Contact has been created", YesNo.No);
									sa.assertTrue(true, "New Fundraising Contact has been created");
									labelName.removeAll(labelName);
									value.removeAll(value);
									inputType.removeAll(inputType);

									if (BP.clickOnTab(projectName, projectName, TabName.InstituitonsTab)) {
										log(LogStatus.PASS, "Clicked on Firm Tab", YesNo.No);

										log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
										if (CPB.clickOnCreatedContact(projectName,null,"pe_advisor_record01")) {
											log(LogStatus.PASS, "Clicked on created Firm", YesNo.No);

											xPath = "//a[text()='Clients']";
											ele=CommonLib.FindElement(driver, xPath, "Client tab", action.SCROLLANDBOOLEAN, 50);					
											if (CommonLib.click(driver,ele,"Client",action.SCROLLANDBOOLEAN)) {

												labelName.add("Firm");
												labelName.add("Role");
												labelName.add("Fundraising");

												value.add(institutionName);
												value.add("Legal");
												value.add("Auto test 1");

												inputType.add("SearchDropDown"); 
												inputType.add("dropdown");
												inputType.add("SearchDropDown");
												
												CommonLib.ThreadSleep(8000);
												
												log(LogStatus.INFO, "Clicked on client Tab", YesNo.No);
												if(BP.createNewRecordThroughSDG(projectName, "Clients", "New Client", labelName, value, inputType, 30))
												{
													log(LogStatus.PASS, "Record has been created on client SDG", YesNo.No);
													sa.assertTrue(true, "Record has been created on client SDG");
												}
												else
												{
													log(LogStatus.ERROR,"Record is not created on client SDG", YesNo.No);
													sa.assertTrue(false, "Record is not created on client SDG");
												}
											}
											else 
											{
												log(LogStatus.ERROR, "Not able to click pe_advisor_record01", YesNo.Yes);
												sa.assertTrue(false, "Not able to click pe_advisor_record01");
											}
										}
										else
										{
											log(LogStatus.ERROR, "Not able to click on Firm Tab", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on Firm Tab");
										}
									}
									else {
										log(LogStatus.ERROR, "Not able to click on Firm Tab", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on Firm Tab");
									}
								}
								else
								{
									log(LogStatus.FAIL, "New Fundraising Contact is not created", YesNo.No);
									sa.assertTrue(false, "New Fundraising Contact is not created");
								}
							}
							else
							{
								log(LogStatus.FAIL, "Fundraising is not created", YesNo.No);
								sa.assertTrue(false, "Fundraising is not created");
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Fundraising tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Fundraising tab");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to get the Element of Fundraising tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to get the Element of Fundraising tab");
					}
				}
				else {
					log(LogStatus.ERROR, "Not able to get create the contact", YesNo.Yes);
					sa.assertTrue(false, "Not able to get create the contact");
				}

			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+PEFSTGINS1_Institution+" of record type : "+PEFSTGINS1_RecordType);
				log(LogStatus.FAIL,"Not Able to Create Account/Entity : "+PEFSTGINS1_Institution+" of record type : "+PEFSTGINS1_RecordType,YesNo.Yes);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on firm tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on firm tab");
		}


		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc0026_02_PreconditionForClientTab(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer CPB = new ContactsPageBusinessLayer(driver);		
		FirmPageBusinessLayer FPB = new FirmPageBusinessLayer(driver);	
		FundRaisingPageBusinessLayer FRP =new FundRaisingPageBusinessLayer(driver);

		String institutionName="pe_advisor_Instituition2";
		String xPath="";
		WebElement ele;
		ArrayList<String> labelName=new ArrayList<String>();
		ArrayList<String> value=new ArrayList<String>();
		ArrayList<String> inputType=new ArrayList<String>();

		InstitutionsPageBusinessLayer ip=new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		if (BP.clickOnTab(projectName, "Firms")) {
			if (ip.createInstitution(projectName, environment, mode, institutionName, "Institution",null,null)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+PEFSTGINS1_Institution+" of record type : "+PEFSTGINS1_RecordType,YesNo.No);	

				labelName.add("Salutation");
				labelName.add("First Name");
				labelName.add("Last Name");
				labelName.add("Primary Contact");

				value.add("Mr.");
				value.add("Automation");
				value.add("Test 2");
				value.add("select");

				inputType.add("dropdown"); 
				inputType.add("TextBox");
				inputType.add("TextBox");
				inputType.add("Checkbox");
				CommonLib.ThreadSleep(4000);
				if(CPB.CreateNewContactFromTab(projectName,labelName,value,inputType))
				{
					log(LogStatus.INFO,"Contact has been created",YesNo.No);	
					labelName.removeAll(labelName);
					value.removeAll(value);
					inputType.removeAll(inputType);

					xPath="//a[@title='Fundraisings']";
					ele=FindElement(driver,xPath,"Fundraisings Tab", action.THROWEXCEPTION, 30);
					if(ele!=null)
					{
						if(CommonLib.clickUsingJavaScript(driver, ele, "Fundraising tab", action.BOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on Fundraising tab", YesNo.Yes);
							if(FRP.createFundRaising(environment, mode, "Auto test 2", "Sumo Logic - User", institutionName, "1st Closing", "Prospect", "5000", "Status notes","2022","July","22"))
							{
								log(LogStatus.PASS, "Fundraising has been created", YesNo.No);
								sa.assertTrue(true, "Fundraising has been created");

								labelName.add("Contact");
								labelName.add("Role");
								labelName.add("Firm");

								value.add("pe_advisor_record Contact 1");
								value.add("Advisor");
								value.add(institutionName);

								inputType.add("SearchDropDown"); 
								inputType.add("dropdown");
								inputType.add("SearchDropDown");

								if(FRP.CreateNewFundraisingContactFromTab(projectName,labelName,value,inputType))
								{
									log(LogStatus.PASS, "New Fundraising Contact has been created", YesNo.No);
									sa.assertTrue(true, "New Fundraising Contact has been created");
									labelName.removeAll(labelName);
									value.removeAll(value);
									inputType.removeAll(inputType);

									if (BP.clickOnTab(projectName, projectName, TabName.InstituitonsTab)) {
										log(LogStatus.PASS, "Clicked on Firm Tab", YesNo.No);

										log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
										if (CPB.clickOnCreatedContact(projectName,null,"pe_advisor_record01")) {
											log(LogStatus.PASS, "Clicked on created Firm", YesNo.No);

											xPath = "//a[text()='Clients']";
											ele=CommonLib.FindElement(driver, xPath, "Client tab", action.SCROLLANDBOOLEAN, 50);					
											if (CommonLib.click(driver,ele,"Client",action.SCROLLANDBOOLEAN)) {

												labelName.add("Firm");
												labelName.add("Role");
												labelName.add("Fundraising");

												value.add(institutionName);
												value.add("Legal");
												value.add("Auto test 2");

												inputType.add("SearchDropDown"); 
												inputType.add("dropdown");
												inputType.add("SearchDropDown");
												
												CommonLib.ThreadSleep(8000);
												
												log(LogStatus.INFO, "Clicked on client Tab", YesNo.No);
												if(BP.createNewRecordThroughSDG(projectName, "Clients", "New Client", labelName, value, inputType, 30))
												{
													log(LogStatus.PASS, "Record has been created on client SDG", YesNo.No);
													sa.assertTrue(true, "Record has been created on client SDG");
												}
												else
												{
													log(LogStatus.ERROR,"Record is not created on client SDG", YesNo.No);
													sa.assertTrue(false, "Record is not created on client SDG");
												}
											}
											else 
											{
												log(LogStatus.ERROR, "Not able to click pe_advisor_record01", YesNo.Yes);
												sa.assertTrue(false, "Not able to click pe_advisor_record01");
											}
										}
										else
										{
											log(LogStatus.ERROR, "Not able to click on Firm Tab", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on Firm Tab");
										}
									}
									else {
										log(LogStatus.ERROR, "Not able to click on Firm Tab", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on Firm Tab");
									}
								}
								else
								{
									log(LogStatus.FAIL, "New Fundraising Contact is not created", YesNo.No);
									sa.assertTrue(false, "New Fundraising Contact is not created");
								}
							}
							else
							{
								log(LogStatus.FAIL, "Fundraising is not created", YesNo.No);
								sa.assertTrue(false, "Fundraising is not created");
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Fundraising tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Fundraising tab");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to get the Element of Fundraising tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to get the Element of Fundraising tab");
					}
				}
				else {
					log(LogStatus.ERROR, "Not able to get create the contact", YesNo.Yes);
					sa.assertTrue(false, "Not able to get create the contact");
				}

			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+PEFSTGINS1_Institution+" of record type : "+PEFSTGINS1_RecordType);
				log(LogStatus.FAIL,"Not Able to Create Account/Entity : "+PEFSTGINS1_Institution+" of record type : "+PEFSTGINS1_RecordType,YesNo.Yes);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on firm tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on firm tab");
		}


		lp.CRMlogout();
		sa.assertAll();
	}
	



	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc0026_VerifyClientsTabOnAdvisorRecordPage(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer CPB = new ContactsPageBusinessLayer(driver);		
		FirmPageBusinessLayer FPB = new FirmPageBusinessLayer(driver);		

		ArrayList<String> ExpectedHeaderTab = new ArrayList<String>();
		ExpectedHeaderTab.add("Clients");
		ExpectedHeaderTab.add("Fundraising");			

		ArrayList<String> ExpectedClientTab = new ArrayList<String>();
		ExpectedClientTab.add("CLIENT"); 
		ExpectedClientTab.add("ROLE");
		ExpectedClientTab.add("MAIN CONTACT");

		ArrayList<String> ExpectedFundraisingTab = new ArrayList<String>();
		ExpectedFundraisingTab.add("CLIENT NAME"); 
		ExpectedFundraisingTab.add("FUND");
		ExpectedFundraisingTab.add("STAGE");
		ExpectedFundraisingTab.add("CLOSE DATE");
		ExpectedFundraisingTab.add("POTENTIAL INVESTMENT (M)");
		ExpectedFundraisingTab.add("STATUS NOTES");

		HashMap<String,ArrayList<String>> labelListMap = new HashMap <String,ArrayList<String>>();
		labelListMap.put("Clients", ExpectedClientTab);
		labelListMap.put("Fundraising", ExpectedFundraisingTab);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);	
		if (BP.clickOnTab(projectName, projectName, TabName.InstituitonsTab)) {
			log(LogStatus.PASS, "Clicked on Firm Tab", YesNo.No);

			log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
			if (CPB.clickOnCreatedContact(projectName,null,"pe_advisor_record01")) {
				log(LogStatus.PASS, "Clicked on created Firm", YesNo.No);

				String xPath = "//a[text()='Clients']";
				WebElement ele=CommonLib.FindElement(driver, xPath, "Client tab", action.SCROLLANDBOOLEAN, 50);					
				if (click(driver,ele,"Client",action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on client Tab", YesNo.No);
					ArrayList<String> result = FPB.ClientTabVerify(ExpectedHeaderTab,labelListMap);
					if(result.isEmpty())
					{
						log(LogStatus.PASS, "Client tab has been verified ", YesNo.No);
						sa.assertTrue(true,"Client Tab has been verified ");
					}
					else
					{
						log(LogStatus.FAIL, "Client Tab does not contain"+result, YesNo.Yes);
						sa.assertTrue(false,"Client Tab does not contain "+result);
					}
				}
				else 
				{
					log(LogStatus.ERROR, "Not able to click on Client Tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Client Tab");
				}
			}
			else {
				log(LogStatus.ERROR, "Not able to click on Firm Tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Firm Tab");
			}

		}
		lp.CRMlogout();
		sa.assertAll();
	}

/*
	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc0026_VerifySortingOnAllFieldsOfClientsFundraisingGridOnClientsTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		
		String data="Contacts<fieldBreak>Title<f>Email<f>Phone<f>Last Touchpoint<defaultSortBreak>Name<sortOrder>ASC<break>Affiliations<fieldBreak>Name<f>Firm<f>End Date<f>Last Touchpoint<defaultSortBreak>Start Date<sortOrder>DESC";

		if(BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
			log(LogStatus.INFO, "Clicked On Firms Tab", YesNo.No);

			if(BP.clickOnAlreadyCreatedItem(projectName, "pe_advisor_record01", TabName.InstituitonsTab, 50))
			{
				log(LogStatus.PASS, "Enter the Name and clicked on it", YesNo.No);
				if(FB.VerifySortingOnFields(data)) {
					log(LogStatus.INFO, "Clicked On Firms Details Page", YesNo.No);
				}
				else {
					log(LogStatus.FAIL, "Not able to Clicked On Firms Details Page", YesNo.Yes);
					sa.assertTrue(false, "Not able to Clicked On Firms Details Page");
				}

			}
			else {
				log(LogStatus.FAIL, "Unable the Enter Name and not clicked on it", YesNo.No);
				sa.assertTrue(false, "Unable the Enter Name and not clicked on it");
			}
		}else {
			log(LogStatus.FAIL, "Not able to clicked on Firms Tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to clicked on Firms Tab");
		}
		lp.CRMlogout();
		sa.assertAll();

	}
	
	*/
	
	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc0027_VerifySortingOnAllFieldsOfClientsFundraisingGridOnClientsTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		

		String data="Clients<fieldBreak>Client<f>Role<defaultSortBreak>Client<sortOrder>ASC<noSortOrderField>Main Contact<break>Fundraising<fieldBreak>Client Name<f>Fund<f>Stage<f>Close Date<f>Potential Investment (M)<f><defaultSortBreak>null<sortOrder>null<noSortOrderField>Status Notes";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if(BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
			log(LogStatus.INFO, "Clicked On Firms Tab", YesNo.No);

			if(BP.clickOnAlreadyCreatedItem(projectName, "pe_advisor_record01", TabName.InstituitonsTab, 50))
			{

				String xPath="//a[@data-label='Clients']";
				WebElement ele=CommonLib.FindElement(driver, xPath,"Contact tab", action.SCROLLANDBOOLEAN, 50);

				if(click(driver, ele, "Contacts Tab", action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "Clicked on Contact tab button", YesNo.No);	
				  ArrayList<String> result=FB.VerifySortingOnFields(data);
				  if(result.isEmpty())
				  {
					  log(LogStatus.PASS, "Sorting on All fields of Contacts and Affiliations grid on Contacts tab has been verified", YesNo.No);
					  sa.assertTrue(true, "Sorting on All fields of Contacts and Affiliations grid on Contacts tab has been verified");
				  }
				  else
				  {
					  log(LogStatus.FAIL, "Sorting on All fields of Contacts and Affiliations grid on Contacts tab have not been verified -- "+result, YesNo.No);
					  sa.assertTrue(false, "Sorting on All fields of Contacts and Affiliations grid on Contacts tab have not been verified -- "+result);
				  
				  }
					
				}
				else
				{
					log(LogStatus.FAIL, "Not able to Clicked On Contact tab", YesNo.No);
					sa.assertTrue(false, "Not able to Clicked On Contact tab");
				}
			}
			else {
				log(LogStatus.FAIL, "Unable the Enter Name and not clicked on it", YesNo.No);
				sa.assertTrue(false, "Unable the Enter Name and not clicked on it");
			}
		}else {
			log(LogStatus.FAIL, "Not able to clicked on Firms Tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to clicked on Firms Tab");
		}
		lp.CRMlogout();
		sa.assertAll();

	}
	
	
	
	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc0028_VerifyPageRedirectionForTheClickableFieldsOnClientsGridOnClientsTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		String [] gridNameAndFieldName=PEFSTG_Tc028_RecordType.split("<break>");
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		for(int i=0; i<gridNameAndFieldName.length; i++)
		{
			if (BP.clickOnTab(projectName, "Firms")) {
				if(BP.clickOnAlreadyCreatedItem(projectName, PEFSTGINS1_Institution, TabName.InstituitonsTab, 50))
				{

					String xPath="//a[@data-label='Clients']";
					WebElement ele=CommonLib.FindElement(driver, xPath,"Contact tab", action.SCROLLANDBOOLEAN, 50);

					if(click(driver, ele, "Contacts Tab", action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Clicked on Contact tab button", YesNo.No);	

						ArrayList<String> result= FB.verifyPageRedirectionForTheClickableFieldsOnClientsGridFundraisingGridAndReferalGridOnClientsTabAndReferralTab(projectName, PEFSTGINS1_Institution,gridNameAndFieldName[i]);
						if(result.isEmpty())
						{
							log(LogStatus.PASS, "Page redirection for the clickable fields on Client and Fundraising grid on Clients tab has been verified", YesNo.No);
							sa.assertTrue(true, "Page redirection for the clickable fields on Client and Fundraising grid on Clients tab has been verified");
						}
						else
						{
							log(LogStatus.FAIL, "Page redirection for the clickable fields on Client and Fundraising grid on Clients tab is not verified "+result, YesNo.No);
							sa.assertTrue(false, "Page redirection for the clickable fields on Client and Fundraising grid on Clients tab is not verified "+result);

						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Client tab", YesNo.No);
						sa.assertTrue(false,"Not able to click on Client tab");

					}

				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on "+PEFSTGINS1_Institution, YesNo.No);
					sa.assertTrue(false, "Not able to click on "+PEFSTGINS1_Institution);
				}
			}
			else
			{
				log(LogStatus.FAIL, "Not able to open Firm Tab", YesNo.No);
				sa.assertTrue(false, "Not able to Firm Tab");
			}

		}

		lp.CRMlogout();
		sa.assertAll();	
	}
	
	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc0029_VerifyInlineEditingForClientsFundraisingGridOnClientsTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
        String fundraisingSDGName="Fundraising";
        ArrayList<String> recordName=new ArrayList<String>();
        ArrayList<String> iconType=new ArrayList<String>();
        recordName.add("Client Name");
        recordName.add("Fund");
        recordName.add("Stage");
        recordName.add("Close Date");
        recordName.add("Potential Investment (M)");
        recordName.add("Status Notes");
        
        iconType.add("Lock");
        iconType.add("Lock");
        iconType.add("Lock");
        iconType.add("Lock");
        iconType.add("Lock");
        iconType.add("Lock");
        
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (BP.clickOnTab(projectName, "Firms")) {
			if(BP.clickOnAlreadyCreatedItem(projectName, PEFSTGINS1_Institution, TabName.InstituitonsTab, 50))
			{

				String xPath="//a[@data-label='Clients']";
				WebElement ele=CommonLib.FindElement(driver, xPath,"Contact tab", action.SCROLLANDBOOLEAN, 50);

				if(click(driver, ele, "Client Tab", action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "Clicked on Contact tab button", YesNo.No);	

					ArrayList<String> result= FB.verifyErrorMsgAfterInlineEditingForClientsGrid("Clients","Client");
					if(result.isEmpty())
					{
						log(LogStatus.PASS, "Inline editing for Client grid on Clients tab has been verified", YesNo.No);
						sa.assertTrue(true, "Inline editing for Client grid on Clients tab tab has been verified");
					}
					else
					{
						log(LogStatus.FAIL, "Inline editing for Client grid on Clients tab on Contacts tab is not verified", YesNo.No);
						sa.assertTrue(false, "Inline editing for Client grid on Clients tab on Contacts tab is not verified");
					}
					ArrayList<String> result1=BP.verifyEditOrLockIconOnSDgRecord(fundraisingSDGName, recordName, iconType);
					if(result1.isEmpty())
					{
						log(LogStatus.PASS, "Locked icon is visible on all record of fundraising grid "+recordName, YesNo.No);
						sa.assertTrue(true, "Locked icon is visible on all record of fundraising grid "+recordName);
					}
					else
					{
						log(LogStatus.FAIL, "Locked icon is not visible on  records of fundraising grid "+result1, YesNo.No);
						sa.assertTrue(false, "Locked icon is not visible on records of fundraising grid "+result1);
					}
				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on Contact tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Contact tab");

				}
			}
			else
			{
				log(LogStatus.FAIL, "Not able to click on "+PEFSTGINS1_Institution, YesNo.No);
				sa.assertTrue(false, "Not able to click on "+PEFSTGINS1_Institution);

			}
		}
		else
		{
			log(LogStatus.FAIL, "Not able to open Firm Tab", YesNo.No);
			sa.assertTrue(false, "Not able to Firm Tab");
		}

		lp.CRMlogout();
		sa.assertAll();	
	}
 
	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc0030_VerifyReferralsTabOnAdvisorRecordPage(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer CPB = new ContactsPageBusinessLayer(driver);		
		FirmPageBusinessLayer FPB = new FirmPageBusinessLayer(driver);		
		String xPath;
		WebElement ele;
		ArrayList<String> ExpectedHeaderTab = new ArrayList<String>();
		ExpectedHeaderTab.add("Referrals");			

		ArrayList<String> ExpectedReferralsColumnRecord = new ArrayList<String>();
		ExpectedReferralsColumnRecord.add("Name"); 
		ExpectedReferralsColumnRecord.add("Status");
		ExpectedReferralsColumnRecord.add("Introduction Date");
		ExpectedReferralsColumnRecord.add("Introduced By");
		ExpectedReferralsColumnRecord.add("Last Touchpoint");

		HashMap<String,ArrayList<String>> labelListMap = new HashMap <String,ArrayList<String>>();
		labelListMap.put("Referrals", ExpectedReferralsColumnRecord);

		ArrayList<String> labels=new ArrayList<String>();
		ArrayList<String> values=new ArrayList<String>();
		ArrayList<String> typeOfFields=new ArrayList<String>();

		labels.add("Legal Name");
		labels.add("Introduced by");
		labels.add("Introduction Date");

		values.add("Company Automation Test");
		values.add("pe_advisor_record Contact 1");
		values.add("17/Jul/2022");

		typeOfFields.add("TextBox");
		typeOfFields.add("SearchDropDown");
		typeOfFields.add("DatePicker");

		lp.CRMLogin(superAdminUserName, adminPassword, appName);	
		if (BP.clickOnTab(projectName, projectName, TabName.InstituitonsTab)) {
			log(LogStatus.PASS, "Clicked on Firm Tab", YesNo.No);

			log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
			if (CPB.clickOnCreatedContact(projectName,null,"pe_advisor_record01")) {
				log(LogStatus.PASS, "Clicked on created Firm", YesNo.No);

				xPath = "//a[text()='Referrals']";
				ele=CommonLib.FindElement(driver, xPath, "Referrals tab", action.SCROLLANDBOOLEAN, 50);					
				if (click(driver,ele,"Referrals",action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Referrals Tab", YesNo.No);
					ArrayList<String> result = FPB.ClientTabVerify(ExpectedHeaderTab,labelListMap);
					if(result.isEmpty())
					{
						log(LogStatus.PASS, "Referrals tab has been verified ", YesNo.No);
						sa.assertTrue(true,"Referrals Tab has been verified ");

						xPath="//a[text()='Referrals']/ancestor::article//span[text()='Name']/lightning-icon";
						if(FPB.checkAscOrder(xPath, "Name"))
						{
							log(LogStatus.PASS, "Default Ascending order has been verified on Name Record of Referral SDG", YesNo.No);
							sa.assertTrue(true,"Default Ascending order has been verified on Name Record of Referral SDG");
						}

						else
						{
							log(LogStatus.FAIL, "Default Ascending order is not verified on Name Record of Referral SDG", YesNo.No);
							sa.assertTrue(false,"Default Ascending order is not verified on Name Record of Referral SDG");
						}
					}
					else
					{
						log(LogStatus.FAIL, "Referrals Tab does not contain"+result, YesNo.Yes);
						sa.assertTrue(false,"Referrals Tab does not contain "+result);

					}

					if(FPB.verifyBtnVisibilityOnSDG("New Referral"))
					{
						log(LogStatus.PASS, "New Referral button is visible", YesNo.Yes);
						sa.assertTrue(true, "New Referral button is visible ");

						if(FPB.verifySDGPopUPOpen("New Referral", "New Firm: Company"))
						{
							log(LogStatus.PASS, "New Firm: Company popup has been open", YesNo.No);
							sa.assertTrue(true, "New Firm: Company popup has been open ");
							xPath="//div[contains(@id,'content')]/preceding-sibling::div/button[@title='Close this window']";
							ele=CommonLib.FindElement(driver, xPath, "Referrals tab", action.SCROLLANDBOOLEAN, 50);					
							if(CommonLib.clickUsingJavaScript(driver, ele, "Close popup", action.BOOLEAN))
							{
								log(LogStatus.PASS, "Clicked on close icon of popup", YesNo.No);
								sa.assertTrue(true, "Clicked on close icon of popup");

								if(BP.createNewRecordThroughSDG(projectName, "Referrals", "New Referral", labels, values, typeOfFields, 30))
								{
									log(LogStatus.PASS, "New Firm: Company record has been created", YesNo.No);
									sa.assertTrue(true, "New Firm: Company record has been created ");
								}
								else
								{
									log(LogStatus.FAIL, "New Firm: Company record is not created", YesNo.No);
									sa.assertTrue(false, "New Firm: Company record is not created ");
								}
							}
							else
							{
								log(LogStatus.FAIL, "Not able to click on close icon of popup", YesNo.No);
								sa.assertTrue(false, "Not able to click on close icon of popup");
							}
						}
						else
						{
							log(LogStatus.FAIL, "New Firm: Company popup has been open", YesNo.No);
							sa.assertTrue(false, "New Firm: Company popup is not open ");
						}
					}
					
					else
					{
						log(LogStatus.FAIL, "New Referral button is not visible", YesNo.Yes);
						sa.assertTrue(false, "New Referral button is not visible ");
					}

				}
				else 
				{
					log(LogStatus.ERROR, "Not able to click on Referrals Tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Referrals Tab");
				}
			}
			else {
				log(LogStatus.ERROR, "Not able to click on Firm Tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Firm Tab");
			}

		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc0031_VerifySortingOnAllFieldsOfReferralsGridOnReferralsTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		

		//String data="Clients<fieldBreak>Client<f>Role<defaultSortBreak>Client<sortOrder>ASC<noSortOrderField>Main Contact<break>Fundraising<fieldBreak>Client Name<f>Fund<f>Stage<f>Close Date<f>Potential Investment (M)<f><defaultSortBreak>null<sortOrder>null<noSortOrderField>Status Notes";
		String data="Referrals<fieldBreak>Name<f>Status<f>Introduction Date<f>Introduced By<defaultSortBreak>Name<sortOrder>ASC<noSortOrderField>Last Touchpoint<break>";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if(BP.clickOnTab(projectName, TabName.InstituitonsTab)) {
			log(LogStatus.INFO, "Clicked On Firms Tab", YesNo.No);

			if(BP.clickOnAlreadyCreatedItem(projectName, "pe_advisor_record01", TabName.InstituitonsTab, 50))
			{

				String xPath="//a[@data-label='Referrals']";
				WebElement ele=CommonLib.FindElement(driver, xPath,"Referrals tab", action.SCROLLANDBOOLEAN, 50);

				if(click(driver, ele, "Referrals Tab", action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "Clicked on Referrals tab button", YesNo.No);	
				  ArrayList<String> result=FB.VerifySortingOnFields(data);
				  if(result.isEmpty())
				  {
					  log(LogStatus.PASS, "Sorting on All fields of Referral grid on Referral tab has been verified", YesNo.No);
					  sa.assertTrue(true, "Sorting on All fields of Referral grid on Referral tab has been verified");
				  }
				  else
				  {
					  log(LogStatus.FAIL, "Sorting on All fields of Referral grid on Referral tab are not verified -- "+result, YesNo.No);
					  sa.assertTrue(false, "Sorting on All fields of Referral grid on Referral tab are not verified -- "+result);
				  
				  }
					
				}
				else
				{
					log(LogStatus.FAIL, "Not able to Clicked On Referral tab", YesNo.No);
					sa.assertTrue(false, "Not able to Clicked On Referral tab");
				}
			}
			else {
				log(LogStatus.FAIL, "Unable the Enter Name and not clicked on it", YesNo.No);
				sa.assertTrue(false, "Unable the Enter Name and not clicked on it");
			}
		}else {
			log(LogStatus.FAIL, "Not able to clicked on Firms Tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to clicked on Firms Tab");
		}
		lp.CRMlogout();
		sa.assertAll();

	}
	
	
	
	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc0032_VerifyPageRedirectionForTheClickableFieldsOnReferralsGridOnReferralsTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		String [] gridNameAndFieldName=PEFSTG_Tc032_RecordType.split("<break>");
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		for(int i=0; i<gridNameAndFieldName.length; i++)
		{
			if (BP.clickOnTab(projectName, "Firms")) {
				if(BP.clickOnAlreadyCreatedItem(projectName, PEFSTGINS1_Institution, TabName.InstituitonsTab, 50))
				{

					String xPath="//a[@data-label='Referrals']";
					WebElement ele=CommonLib.FindElement(driver, xPath,"Contact tab", action.SCROLLANDBOOLEAN, 50);

					if(click(driver, ele, "Contacts Tab", action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Clicked on Contact tab button", YesNo.No);	

						ArrayList<String> result= FB.verifyPageRedirectionForTheClickableFieldsOnClientsGridFundraisingGridAndReferalGridOnClientsTabAndReferralTab(projectName, PEFSTGINS1_Institution,gridNameAndFieldName[i]);
						if(result.isEmpty())
						{
							log(LogStatus.PASS, "Page redirection for the clickable fields on Client and Fundraising grid on Clients tab has been verified", YesNo.No);
							sa.assertTrue(true, "Page redirection for the clickable fields on Client and Fundraising grid on Clients tab has been verified");
						}
						else
						{
							log(LogStatus.FAIL, "Page redirection for the clickable fields on Client and Fundraising grid on Clients tab is not verified "+result, YesNo.No);
							sa.assertTrue(false, "Page redirection for the clickable fields on Client and Fundraising grid on Clients tab is not verified "+result);

						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Client tab", YesNo.No);
						sa.assertTrue(false,"Not able to click on Client tab");

					}

				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on "+PEFSTGINS1_Institution, YesNo.No);
					sa.assertTrue(false, "Not able to click on "+PEFSTGINS1_Institution);
				}
			}
			else
			{
				log(LogStatus.FAIL, "Not able to open Firm Tab", YesNo.No);
				sa.assertTrue(false, "Not able to Firm Tab");
			}

		}

		lp.CRMlogout();
		sa.assertAll();	
	}
	
	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc0033_VerifyInlineEditingForReferralsGridOnReferralsTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		String val="Referrals<gridBreak>Name<v>textbox<f>Status<v>searchDropDown<f>Introduction Date<v>calender<break>";
		ArrayList<String> tabName=new ArrayList<String>();
		tabName.add("Contacts");
		tabName.add("Affiliations");
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		if (BP.clickOnTab(projectName, "Firms")) {
			if(BP.clickOnAlreadyCreatedItem(projectName, "pe_advisor_record01", TabName.InstituitonsTab, 50))
			{

				String xPath="//a[@data-label='Referrals']";
				WebElement ele=CommonLib.FindElement(driver, xPath,"Referrals tab", action.SCROLLANDBOOLEAN, 50);

				if(click(driver, ele, "Referrals Tab", action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "Clicked on Referrals tab button", YesNo.No);	

					ArrayList<String> result= FB.VerifyInlineEditingForContactsAndAffiliationsGrid(val);
					if(result.isEmpty())
					{
						log(LogStatus.PASS, "Inline editing for Referrals grid on Referral tab has been verified", YesNo.No);
						sa.assertTrue(true, "Inline editing for Referrals grid on Referral tab has been verified");
					}
					else
					{
						log(LogStatus.FAIL, "Inline editing for Referrals grid on Referral tab is not verified", YesNo.No);
						sa.assertTrue(false, "Inline editing for Referrals grid on Referral tab is not verified");
					}
				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on Referrals tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Referrals tab");

				}
			}
			else
			{
				log(LogStatus.FAIL, "Not able to click on "+PEFSTGINS1_Institution, YesNo.No);
				sa.assertTrue(false, "Not able to click on "+PEFSTGINS1_Institution);

			}
		}
		else
		{
			log(LogStatus.FAIL, "Not able to open Firm Tab", YesNo.No);
			sa.assertTrue(false, "Not able to Firm Tab");
		}

		lp.CRMlogout();
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc0034_VerifyFilesTabOnAdvisorRecordPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		ArrayList<String> ExpectedColumnName=new ArrayList<String>();
		ExpectedColumnName.add("Title");
		ExpectedColumnName.add("Owner");
		ExpectedColumnName.add("Last Modified");
		ExpectedColumnName.add("Size");
	
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (BP.clickOnTab(projectName, "Firms")) {
			if(BP.clickOnAlreadyCreatedItem(projectName, "pe_advisor_record01", TabName.InstituitonsTab, 50))
			{
				String xPath="//a[@data-label='Files']";
				WebElement ele=CommonLib.FindElement(driver, xPath,"Files tab", action.SCROLLANDBOOLEAN, 50);

				if(click(driver, ele, "Files Tab", action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "Clicked on Files tab button", YesNo.No);	

					ArrayList<String> result= FB.verifyFileCountUploadAndAddFileButton();
					if(result.isEmpty())
					{
						log(LogStatus.PASS, "Files (count), Add File, Upload File buttons and Or drop File text are visible", YesNo.No);
						sa.assertTrue(true, "Files (count), Add File, Upload File buttons and Or drop File text are visible");
					
						if(FB.uploadFileAndVerify("AutomationTest.xlsx","xlsx","8KB"))
						{
							log(LogStatus.PASS, "The file has been uploaded sucessfully. Uploaded date, size, and type have been verified", YesNo.No);
							sa.assertTrue(true, "The file has been uploaded sucessfully. Uploaded date, size, and type have been verified");
							
							if(FB.verifyViewAllButtonForAdvisorOnFileTab(ExpectedColumnName))
							{
								log(LogStatus.PASS, "Column name for advisor record on Files tab has been verified", YesNo.No);
								sa.assertTrue(true, "Column name for advisor record on Files tab has been verified");
								
							}
							else
							{
								log(LogStatus.FAIL, "Column name for advisor record on Files tab are not verified", YesNo.No);
								sa.assertTrue(false, "Column name for advisor record on Files tab are not verified");
								
							}

						}
						else
						{
							log(LogStatus.FAIL, "Either The file is not uploaded or Uploaded date, size, and type are not verified", YesNo.No);
							sa.assertTrue(false, "Either The file is not uploaded or Uploaded date, size, and type are not verified");
	
						}
					}
					else
					{
						log(LogStatus.FAIL, "Either Files (count) or Add File or Upload File buttons or Or drop File text  are not visible", YesNo.No);
						sa.assertTrue(false, "Either Files (count) or Add File or Upload File buttons or Or drop File text are not visible");
					}
					
				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on Files tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Files tab");

				}
			}
			else
			{
				log(LogStatus.FAIL, "Not able to click on "+PEFSTGINS1_Institution, YesNo.No);
				sa.assertTrue(false, "Not able to click on "+PEFSTGINS1_Institution);

			}
		}
		else
		{
			log(LogStatus.FAIL, "Not able to open Firm Tab", YesNo.No);
			sa.assertTrue(false, "Not able to Firm Tab");
		}

		lp.CRMlogout();
		sa.assertAll();	
	}
	
	@Parameters({ "projectName" })
	@Test

	public void PEFSTGTc0035_VerifyFilesTabOnAdvisorRecordPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
		ArrayList<String> ExpectedTabName=new ArrayList<String>();
		ExpectedTabName.add("All Contacts");
		ExpectedTabName.add("Board Members");
		ExpectedTabName.add("Recent Moves");
	
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (BP.clickOnTab(projectName, "Firms")) {
			if(BP.clickOnAlreadyCreatedItem(projectName, "pe_advisor_record01", TabName.InstituitonsTab, 50))
			{
				String xPath="//a[@data-label='Connections']";
				WebElement ele=CommonLib.FindElement(driver, xPath,"Connections tab", action.SCROLLANDBOOLEAN, 50);

				if(click(driver, ele, "Connections Tab", action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "Clicked on Connections tab button", YesNo.No);	

					if(FB.verifyTabsInConnectionsTab(ExpectedTabName))
					{
						log(LogStatus.PASS, "The tabs on Connection tab has been verified", YesNo.No);
						sa.assertTrue(true, "The tabs on Connection tab has been verified");
					}
					else
					{
						log(LogStatus.FAIL, "The tabs on Connection tab are not verified", YesNo.No);
						sa.assertTrue(false, "The tabs on Connection tab are not verified");
					}
					
				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on Connections tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Connections tab");

				}
			}
			else
			{
				log(LogStatus.FAIL, "Not able to click on "+PEFSTGINS1_Institution, YesNo.No);
				sa.assertTrue(false, "Not able to click on "+PEFSTGINS1_Institution);

			}
		}
		else
		{
			log(LogStatus.FAIL, "Not able to open Firm Tab", YesNo.No);
			sa.assertTrue(false, "Not able to Firm Tab");
		}

		lp.CRMlogout();
		sa.assertAll();	
	}

	

	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc0036_VerifyActivityTimelineAndChatterOnAdvisorRecord(String projectName) 
	{
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);

		ArrayList<String> tabName=new ArrayList<String>();
		tabName.add("All Contacts");
		tabName.add("Board Members");
		tabName.add("Recent Moves");

		lp.CRMLogin(superAdminUserName, adminPassword);

		if (BP.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if(BP.clickOnAlreadyCreatedItem(projectName, "pe_advisor_record01", TabName.InstituitonsTab, 50))
			{
				String xPath="//a[@data-label='Connections']";
				WebElement ele=CommonLib.FindElement(driver, xPath,"Connections tab", action.SCROLLANDBOOLEAN, 50);

				if(click(driver, ele, "Connections Tab", action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "Clicked on Connections tab button", YesNo.No);	

					if(FB.verifyActivityTimelineTab(tabName))
					{
						log(LogStatus.PASS, "Tab Names have been verified", YesNo.No);
						sa.assertTrue(true, "Tab Names have been verified");
					}
					else
					{
						log(LogStatus.FAIL, "Tab Names are not verified", YesNo.No);
						sa.assertTrue(false, "Tab Names are not verified");
					}
					
					if (FB.sectionBelowTaskAndEventSection(10) == null) {
                        log(LogStatus.PASS,
                                "-----Verified: Chatter(Poll/Post) Section is not Present on this Page-----", YesNo.No);
                        sa.assertTrue(true,
                                "-----Verified: Chatter(Poll/Post) Section is not Present on this Page-----");
                    } else {
                        sa.assertTrue(false,
                                "-----Not Verified: Chatter(Poll/Post) Section is not Present on this Page-----");
                        log(LogStatus.FAIL,
                                "-----Not Verified: Chatter(Poll/Post) Section is not Present on this Page-----",
                                YesNo.Yes);
                    }
				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on Connections tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Connections tab");

				}
			}
			else
			{
				log(LogStatus.FAIL, "Not able to click on "+PEFSTGINS1_Institution, YesNo.No);
				sa.assertTrue(false, "Not able to click on "+PEFSTGINS1_Institution);

			}
		}
		else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}

		sa.assertAll();
		lp.CRMlogout();
	}


	@Parameters({ "projectName" })
	@Test
	public void PEFSTGTc0037_VerifyStandardActivityTimelineFunctionalityOnContactDetailPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

		String[] activityTimelineTabs = "New Task<Section>New Event<Section>Log a Call<Section>Email"
				.split("<Section>");

		String[] recordPageLabelValuesAndTypesOfElements = "Subject<Br>Advisor Task01<Br>SearchDropDownTextBox<Break>Due Date<Br>20<Br>DatePickerFutureDate<Break>Status<Br>Not Started<Br>DropDown<Section>Subject<Br>Advisor Event01<Br>SearchDropDownTextBox<Break>Start<Br>08/Jun/2022<Split>9:00 AM<Br>DateTimePickerCurrentDate<Break>End<Br>08/Jun/2022<Split>10:00 AM<Br>DateTimePickerCurrentDate<Section>Subject<Br>Advisor LAC01<Br>SearchDropDownTextBox<Break><Section>Subject<Br>Welcome to Navatar<Br>TextBox<Break>To<Br>1investorportal@gmail.com<Br>TextBox<Break>Body<Br>Welcome to Navatar<Br>TextBox<Break>".split("<Section>");
		String recordAndActivityTitle="Advisor Task01<Br>UpcomingAndOverdue<Break>Advisor Event01<Br>UpcomingAndOverdue<Break>Welcome to Navatar<Br>ThisMonth<Break>Advisor LAC01<Br>ThisMonth";

		lp.CRMLogin(superAdminUserName, adminPassword);

		if (BP.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			if(BP.clickOnAlreadyCreatedItem(projectName, "pe_advisor_record01", TabName.InstituitonsTab, 50))
			{

				String[][] requiredRecordPageLabelValuesAndTypesOfElements = null;
				int loopCount = 0;
				for (String recordPageLabelValuesAndTypesOfElement : recordPageLabelValuesAndTypesOfElements) {
					requiredRecordPageLabelValuesAndTypesOfElements = new String[recordPageLabelValuesAndTypesOfElement
					                                                             .split("<Break>").length][3];
					int i = 0;
					for (String recordPageLabelValuesAndTypesOfEle : recordPageLabelValuesAndTypesOfElement
							.split("<Break>")) {
						for (int j = 0; j < 3; j++) {
							requiredRecordPageLabelValuesAndTypesOfElements[i][j] = recordPageLabelValuesAndTypesOfEle
									.split("<Br>")[j];
						}
						i++;
					}

					if (cp.enterValuesForActivityTimeline(activityTimelineTabs[loopCount],
							requiredRecordPageLabelValuesAndTypesOfElements)) {

						log(LogStatus.INFO, "Data has been Created Successfully for Activity Timeline: "
								+ activityTimelineTabs[loopCount], YesNo.No);
						CommonLib.ThreadSleep(5000);

					} else

					{
						sa.assertTrue(false, "Data has not been Created Successfully for Activity Timeline: "
								+ activityTimelineTabs[loopCount]);
						log(LogStatus.FAIL, "Data has not been Created Successfully for Activity Timeline: "
								+ activityTimelineTabs[loopCount], YesNo.Yes);
					}

					loopCount++;
				}
				if(BP.verifyActivityRecord(recordAndActivityTitle))
				{
					log(LogStatus.PASS, "Activity Timeline has been created and record are apearing on Activity", YesNo.No);
					sa.assertTrue(true, "Activity Timeline has been created and record are apearing on Activity");					
				}
				else
				{
					log(LogStatus.FAIL, "Activity Timeline has been created but records are not apearing on Activity", YesNo.No);
					sa.assertTrue(false, "Activity Timeline has been created but records are apearing on Activity");
				}
			}
			else {
				sa.assertTrue(false, "Not Able to Click on pe_advisor_record01 record");
				log(LogStatus.SKIP, "Not Able to Click on pe_advisor_record01 record", YesNo.No);
			}
		}

		else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}
		sa.assertAll();
		lp.CRMlogout();
	}	
	
@Parameters({ "projectName" })
@Test

public void PEFSTGTcS002_VerifyFiltersOnListViews(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
	String[] listViews;
	String[] filterValue;
	String[] filter;
	String[] field;
	String[] operators;
	String[] Filter_Condition;
	List<String> listViewNames = new ArrayList<String>();
	String listViewAndFieldData;

	String[][] listViewSheetData = {
			{ PEFSTGLV_1_Member, PEFSTGLV_1_TabName, PEFSTGLV_1_ListViewName, PEFSTGLV_1_Filter, PEFSTGLV_1_Field,
					PEFSTGLV_1_Operators, PEFSTGLV_1_FilterValue, PEFSTGLV_1_FilterCondition,
					PEFSTGLV_1_FieldsName },
			{ PEFSTGLV_2_Member, PEFSTGLV_2_TabName, PEFSTGLV_2_ListViewName, PEFSTGLV_2_Filter, PEFSTGLV_2_Field,
					PEFSTGLV_2_Operators, PEFSTGLV_2_FilterValue, PEFSTGLV_2_FilterCondition,
					PEFSTGLV_2_FieldsName },
			{ PEFSTGLV_3_Member, PEFSTGLV_3_TabName, PEFSTGLV_3_ListViewName, PEFSTGLV_3_Filter, PEFSTGLV_3_Field,
					PEFSTGLV_3_Operators, PEFSTGLV_3_FilterValue, PEFSTGLV_3_FilterCondition,
					PEFSTGLV_3_FieldsName } };

	for (String[] row : listViewSheetData) {

		listViewNames = Arrays.asList(row[2].split("<BreakOn>", -1));
		listViews = row[2].replace(" (Pinned list)", "").split("<BreakOn>", -1);
		filterValue = row[6].split("<BreakOn>", -1);
		filter = row[3].split("<BreakOn>", -1);
		field = row[4].split("<BreakOn>", -1);
		operators = row[5].split("<BreakOn>", -1);
		Filter_Condition = row[7].split("<BreakOn>", -1);
		listViewAndFieldData = row[8];

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
						+ " So not able to verify Filters & Fields for those List Views", YesNo.Yes);
				sa.assertTrue(false, "List Views Presence has not been verified: " + notVerifiedListViews.toString()
						+ " So not able to verify Filters & Fields for those List Views");
			}

			List<String> notVerifiedListViewsFields = FB.verifyFieldsOnListview(listViewAndFieldData, 25);
			if (notVerifiedListViewsFields.isEmpty()) {
				log(LogStatus.PASS, "Fields of List Views has been verified: " + listViewAndFieldData, YesNo.No);

			} else {
				log(LogStatus.FAIL,
						"Fields of List Views has not been verified: " + notVerifiedListViewsFields.toString(),
						YesNo.Yes);
				sa.assertTrue(false,
						"Fields of List Views has not been verified: " + notVerifiedListViewsFields.toString());
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

@Parameters({ "projectName" })
@Test

public void PEFSTGTcS003_VerifyRecordCreationForInstitutionAndContact(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, gmailPassword, appName);

	if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (ip.createInstitution(projectName, environment, mode, PEFSTGEntities_1_InstitutionsName,
				PEFSTGEntities_1_RecordType, "", "")) {
			log(LogStatus.INFO, "Successfully Created Account/Entity : " + PEFSTGEntities_1_InstitutionsName
					+ " of record type : " + PEFSTGEntities_1_RecordType, YesNo.No);
		} else {
			sa.assertTrue(false, "Not Able to Create Account/Entity : " + PEFSTGEntities_1_InstitutionsName
					+ " of record type : " + PEFSTGEntities_1_RecordType);
			log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + PEFSTGEntities_1_InstitutionsName
					+ " of record type : " + PEFSTGEntities_1_RecordType, YesNo.Yes);
		}

	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object1Tab);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object1Tab, YesNo.Yes);
	}

	CommonLib.ThreadSleep(3000);
	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (cp.createContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName,
				PEFSTGEntities_1_InstitutionsName, PEFSTGContacts_1_EmailID, "", null, null,
				CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,
					"Successfully Created Contact : " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					YesNo.No);
		}

		else {
			sa.assertTrue(false,
					"Not Able to Create Contact : " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
			log(LogStatus.SKIP,
					"Not Able to Create Contact : " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					YesNo.Yes);
		}

	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();

	sa.assertAll();

}

@Parameters({ "projectName" })
@Test

public void PEFSTGTcS004_VerifyHighlightPanelOnContactRecordPage(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	String[] labels = "Legal Name<Break>Tier<Break>Phone<Break>Email<Break>Last Touchpoint".split("<Break>", -1);
	String[] values = ("Birla Group<Break><Break><Break>" + PEFSTGContacts_1_EmailID.toLowerCase() + "<Break>")
			.split("<Break>", -1);

	List<String> expectedHighlightLabels = Arrays.asList(labels).stream().map(x -> x.trim())
			.collect(Collectors.toList());
	;
	List<String> expectedHighlightLabelsValues = Arrays.asList(values).stream().map(x -> x.trim())
			.collect(Collectors.toList());
	List<String> negativeResult;
	lp.CRMLogin(crmUser1EmailID, gmailPassword, appName);
	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Clicked on Contact: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contactName = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contactName != null) {

				log(LogStatus.INFO, "Contact Page Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);

				negativeResult = cp.verifyHighlightLabelValues(expectedHighlightLabels,
						expectedHighlightLabelsValues);
				if (negativeResult.isEmpty()) {
					log(LogStatus.INFO, "-----Verified Highlighted Label Values-----", YesNo.No);
				} else {
					sa.assertTrue(false, "Not Verified Highlighted Label Values: " + negativeResult);
					log(LogStatus.FAIL, "Not Verified Highlighted Label Values: " + negativeResult, YesNo.Yes);
				}
			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else

		{
			sa.assertTrue(false,
					"Not Able to Click on Contact: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
			log(LogStatus.FAIL,
					"Not Able to Click on Contact: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					YesNo.Yes);
		}

	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
		log(LogStatus.ERROR, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();

	sa.assertAll();

}

@Parameters({ "projectName", "environment", "mode" })
@Test
public void PEFSTGTcS005_VerifyPollPostSectionAndButtonsAndTabsOnContactRecordPage(String environment, String mode,
		String projectName) {
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

	List<String> ExpectedButtonsOnPage = Arrays
			.asList("Edit<Break>New Affiliation<Break>New Referral".split("<Break>", -1));
	List<String> ExpectedButtonsInDownArrowButton = Arrays.asList(
			"New Sourced Deal<Break>Contact Transfer<Break>Clone<Break>Printable View<Break>Change Owner<Break>Delete"
					.split("<Break>", -1));
	List<String> ExpectedTabsOnPage = Arrays
			.asList("Details<Break>Affiliations<Break>Investor Relations<Break>Deals<Break>Connections<Break>Files"
					.split("<Break>", -1));

	lp.CRMLogin(crmUser1EmailID, gmailPassword);

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contactName = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contactName != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);
				List<String> negativeResultOfButtons = cp.verifyButtonsOnAPageAndInDownArrowButton(
						ExpectedButtonsOnPage, ExpectedButtonsInDownArrowButton);
				if (negativeResultOfButtons.isEmpty()) {
					log(LogStatus.INFO, "-----Verified Buttons on Page & in DownArrow Button-----", YesNo.No);
				} else {
					sa.assertTrue(false, "-----Not Verified Buttons on Page & in DownArrow Button-----: "
							+ negativeResultOfButtons);
					log(LogStatus.FAIL,
							"Not Verified Buttons on Page & in DownArrow Button-----: " + negativeResultOfButtons,
							YesNo.Yes);
				}

				List<String> negativeResultOfTabs = cp.verifyTabsOnAPage(ExpectedTabsOnPage);
				if (negativeResultOfTabs.isEmpty()) {
					log(LogStatus.INFO, "-----Verified Tabs on Page-----", YesNo.No);
				} else {
					sa.assertTrue(false, "-----Not Verified Tabs on Page-----:" + negativeResultOfTabs);
					log(LogStatus.FAIL, "-----Not Verified Buttons on Page-----:" + negativeResultOfTabs,
							YesNo.Yes);
				}

				if (cp.sectionBelowTaskAndEventSection(10) == null) {
					log(LogStatus.INFO,
							"-----Verified: Chatter(Poll/Post) Section is not Present on this Page-----", YesNo.No);
				} else {
					sa.assertTrue(false,
							"-----Not Verified: Chatter(Poll/Post) Section is not Present on this Page-----");
					log(LogStatus.FAIL,
							"-----Not Verified: Chatter(Poll/Post) Section is not Present on this Page-----",
							YesNo.Yes);
				}

			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName", "environment", "mode" })
@Test
public void PEFSTGTcS006_VerifyDetailsTabOnContactRecordPage(String environment, String mode, String projectName) {
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

	List<String> ExpectedSectionHeadersOnPage = Arrays.asList(
			"Contact Information<Break>Sourcing Details<Break>Description<Break>Address Information<Break>Additional Information<Break>System Information"
					.split("<Break>", -1));

	String ExpectedSectionHeaderAndLabelsOnPage = "Contact Information<Header&Labels>Name<Header&Labels>Contact Owner<Header&Labels>Title<Header&Labels>Last Touchpoint<Header&Labels>Legal Name<Header&Labels>Next Touchpoint Date<Header&Labels>Primary Contact<Break>Sourcing Details<Header&Labels>Average Deal Quality Score<Header&Labels>Total Deals Shown<Header&Labels>Tier<Header&Labels>Industry Focus<Header&Labels>Office Location<Break>Description<Header&Labels>Description<Break>Address Information<Header&Labels>Mailing Address<Header&Labels>Other Address<Break>Additional Information<Header&Labels>Email<Header&Labels>Mobile Phone 2<Header&Labels>Email 2<Header&Labels>"
			+ "Email Opt Out<Header&Labels>Email 3<Header&Labels>Assistant<Header&Labels>Phone<Header&Labels>Asst. Phone<Header&Labels>Phone 2<Header&Labels>Birthdate<Header&Labels>Home Phone<Header&Labels>Spouse/Partner's Name<Header&Labels>Fax<Header&Labels>Spouse/Partner's Birthday<Header&Labels>Mobile"
			+ "<Break>System Information<Header&Labels>Created By<Header&Labels>Last Modified By";

	lp.CRMLogin(crmUser1EmailID, adminPassword);

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contactName = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contactName != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);

				List<String> negativeResultOfSectionHeaders = cp
						.verifyRecordLayoutSectionHeaders(ExpectedSectionHeadersOnPage);
				if (negativeResultOfSectionHeaders.isEmpty()) {
					log(LogStatus.INFO, "-----Verified Record Layout Section Headers on Page-----", YesNo.No);
				} else {
					sa.assertTrue(false, "-----Not Verified Record Layout Section Headers on Page-----:"
							+ negativeResultOfSectionHeaders);
					log(LogStatus.FAIL, "-----Not Verified Record Layout Section Headers on Page-----:"
							+ negativeResultOfSectionHeaders, YesNo.Yes);
				}

				List<String> negativeResultOfSectionHeaderLabels = cp
						.verifyRecordLayoutSectionHeaderLabels(ExpectedSectionHeaderAndLabelsOnPage);
				if (negativeResultOfSectionHeaderLabels.isEmpty()) {
					log(LogStatus.INFO, "-----Verified Labels of Section Headers on Page-----", YesNo.No);
				} else {
					sa.assertTrue(false, "-----Not Verified Labels of Section Headers on Page-----"
							+ negativeResultOfSectionHeaderLabels);
					log(LogStatus.FAIL, "-----Not Verified Labels of Section Headers on Page-----"
							+ negativeResultOfSectionHeaderLabels, YesNo.Yes);
				}

			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName" })
@Test
public void PEFSTGTs007_VerifyPicklistFieldsOnContactDetailsTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer CB = new ContactsPageBusinessLayer(driver);

	lp.CRMLogin(crmUser1EmailID, adminPassword);
	ArrayList<String> listview = new ArrayList<String>();
	listview.add("--None--");
	listview.add("1");
	listview.add("2");
	listview.add("3");

	if (BP.clickOnTab(projectName, projectName, TabName.ContactTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);

		if (CB.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.PASS, "Clicked on All Contacts and search Name: " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.No);

			ArrayList<String> result = CB.verifyContactTierDetails(listview);
			if (result.isEmpty()) {
				log(LogStatus.PASS, "----Picklist of Tier field has been verified----", YesNo.No);
			} else {
				log(LogStatus.FAIL, "----Picklist of Tier field has not been verified----" + result, YesNo.Yes);
				sa.assertTrue(false, "----Picklist of Tier field has not been verified----" + result);

			}

		} else {
			log(LogStatus.FAIL, "Not Able to Click on All Contacts and search Name: " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.No);
			sa.assertTrue(false, "Not Able to Click on All Contacts and search Name: " + PEFSTGContacts_1_FName
					+ " " + PEFSTGContacts_1_LName);
		}

	} else {
		log(LogStatus.FAIL, "Not Able to clicked on Contact Tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to clicked on Contact Tab");

	}

	lp.CRMlogout();
	ThreadSleep(5000);
	sa.assertAll();
}

@Parameters({ "projectName" })

@Test
public void PEFSTGTcS011_verifySubTabConnections(String projectName) throws InterruptedException {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer CPB = new ContactsPageBusinessLayer(driver);
	ContactsPage CP = new ContactsPage(driver);

	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String connectionsTab = "External Relationships";
	String connectionsTab1 = "Internal Relationships";
	if (BP.clickOnTab(projectName, projectName, TabName.ContactTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
		if (CPB.clickOnCreatedContact(projectName, "RamPal", "Yadav")) {
			log(LogStatus.PASS, "Clicked on created Contact", YesNo.No);
			sa.assertTrue(true, "Clicked on created Contact");

			if (CommonLib.click(driver, CP.Connections(30), "Rampal Yadav", action.SCROLLANDBOOLEAN)) {

				log(LogStatus.INFO, "Clicked on connections", YesNo.No);
				Thread.sleep(10000);
				if (CP.ConnectionsTab(connectionsTab, 30) != null) {

					log(LogStatus.INFO, connectionsTab + " verified successfully", YesNo.No);
					sa.assertTrue(true, connectionsTab + " verified successfully");

				} else {
					log(LogStatus.ERROR, connectionsTab + "not verified", YesNo.No);
					sa.assertTrue(false, connectionsTab + "not verified");

				}
				if (CP.ConnectionsTab(connectionsTab1, 30) != null) {

					log(LogStatus.INFO, connectionsTab1 + " verified successfully", YesNo.No);
					sa.assertTrue(true, connectionsTab1 + " verified successfully");

				} else {
					log(LogStatus.ERROR, connectionsTab1 + "not verified", YesNo.No);
					sa.assertTrue(false, connectionsTab1 + "not verified");

				}

			} else {
				log(LogStatus.ERROR, "Unable to click on connections", YesNo.No);
				sa.assertTrue(false, "Unable to click on connections");
			}

		} else {
			log(LogStatus.ERROR, "Unable to click on created Contact", YesNo.No);
			sa.assertTrue(false, "Unable to click on created Contact");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on ContactsTab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on ContactsTab");
	}

	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName", "environment", "mode" })
@Test
public void PEFSTGTcS008_VerifyAffiliationsTabOnContactRecordPage(String environment, String mode,
		String projectName) {
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

	String sdgName = "Roles";
	String tabInPage = "Affiliations";
	String buttonName = "New Affiliation";

	String startDate = CommonLib.getDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy");
	String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy", 4);
	String firmToSelect = "pe_advisor_record01";
	String roleToChosen = "Former Employee";

	List<String> columnsInSDG = Arrays.asList("FIRM<Break>ROLE<Break>START DATE<Break>END DATE".split("<Break>"));

	String[] labelsAccTSDG = "Firm<Break>Role<Break>Start Date<Break>End Date".split("<Break>");
	String[] valuesAccTSDG = ("pe_advisor_record01<Break>Former Employee<Break>" + startDate + "<Break>" + endDate)
			.split("<Break>");
	String[] typesOfFieldsAccTSDG = "SearchDropDown<Break>MultiPickList<Break>DatePicker<Break>DatePicker"
			.split("<Break>");
	String expectDataCorresspondToColumns = firmToSelect + "<Break>" + roleToChosen + "<Break>" + startDate
			+ "<Break>" + endDate;
	List<String> expectedDataCorresspondToColumns = Arrays.asList(expectDataCorresspondToColumns.split("<Break>"));
	List<String> labelsList = new ArrayList<String>();
	List<String> valuesList = new ArrayList<String>();
	labelsList.add("Firm");

	valuesList.add(firmToSelect);

	List<String> labels = Arrays.asList(labelsAccTSDG);
	List<String> values = Arrays.asList(valuesAccTSDG);
	List<String> typesOfFields = Arrays.asList(typesOfFieldsAccTSDG);

	String[] columnEqualValue = ("FIRM = pe_advisor_record01<Break>ROLE = Former Employee <Break> START DATE = 03/Jun/2022 <Break> END DATE = 10/Jul/2022")
			.split("<Break>");
	String columnNameBasedOnWhichRecordSearch = "FIRM";

	String inlineEditRecord = "Roles<gridBreak>Role<v>multipicklist<f>Start Date<v>calender<f>End Date<v>calender";

	lp.CRMLogin(crmUser1EmailID, adminPassword);

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contactName = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contactName != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);

				if (cp.verifySDGGridInPageTab(tabInPage, sdgName)) {
					log(LogStatus.PASS, "Verified SDG Name: " + sdgName + " on Tab: " + tabInPage, YesNo.No);
				} else {
					sa.assertTrue(false, "Not Verified SDG Name: " + sdgName + " on Tab: " + tabInPage);
					log(LogStatus.FAIL, "Not Verified SDG Name: " + sdgName + " on Tab: " + tabInPage, YesNo.Yes);
				}

				if (edit.verifyColumnsOfSDG(sdgName, columnsInSDG)) {
					log(LogStatus.PASS, "-------Columns of SDG: " + sdgName + " are Matched--------", YesNo.No);

				} else {
					log(LogStatus.FAIL, "------Columns of SDG: " + sdgName + " are not Matched------", YesNo.Yes);
					sa.assertTrue(false, "------Columns of SDG: " + sdgName + " are not Matched------");
				}
				if (cp.sdgButtonName(sdgName, buttonName, 30) != null) {
					log(LogStatus.PASS,
							"-------Button of SDG: " + sdgName + " is Matched: " + buttonName + "--------",
							YesNo.No);
				} else {
					log(LogStatus.FAIL,
							"-------Button of SDG: " + sdgName + " is not Matched: " + buttonName + "--------",
							YesNo.Yes);
					sa.assertTrue(false,
							"-------Button of SDG: " + sdgName + " is not Matched: " + buttonName + "--------");
				}

				if (cp.createNewRecordThroughSDG(projectName, sdgName, buttonName, labels, values, typesOfFields,
						30)) {
					log(LogStatus.PASS,
							"-------" + buttonName + " Record has been created of SDG: " + sdgName + "--------",
							YesNo.No);
					List<String> detailPageLabelValuesText = home.detailPageLabelValues("Information").stream()
							.map(x -> x.getText()).collect(Collectors.toList());
					int i = 0;

					for (String label : labelsList) {

						if (detailPageLabelValuesText.get(i).contains(label)) {
							log(LogStatus.INFO, "Label Verified : " + label + " Created Through SDG: " + sdgName,
									YesNo.No);

							if (detailPageLabelValuesText.get(i).contains(valuesList.get(i))) {
								log(LogStatus.INFO, "Value Verified : " + valuesList.get(i)
										+ " corresponding to label: " + label, YesNo.No);

							} else {
								log(LogStatus.ERROR, "Value Not Verified : " + valuesList.get(i)
										+ " corresponding to label: " + label + " So, Affiliation not created",
										YesNo.Yes);
								sa.assertTrue(false, "Value Not Verified : " + valuesList.get(i)
										+ " corresponding to label: " + label + " So, Affiliation not created");
							}

						} else {
							log(LogStatus.ERROR,
									"Label not Verified : " + label + " Created Through SDG: " + sdgName,
									YesNo.Yes);
							sa.assertTrue(false,
									"Label not Verified : " + label + " Created Through SDG: " + sdgName);
						}

						i++;
					}

				} else {
					log(LogStatus.FAIL,
							"-------" + buttonName + " Record has not been created of SDG: " + sdgName + "--------",
							YesNo.Yes);
					sa.assertTrue(false, "-------" + buttonName + " Record has not been created of SDG: " + sdgName
							+ "--------");
				}

				if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
					if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
						log(LogStatus.INFO,
								"Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
								YesNo.No);

						WebElement contact = cp
								.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName, 30);

						if (contact != null) {

							log(LogStatus.INFO, "Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " "
									+ PEFSTGContacts_1_LName, YesNo.No);

							if (click(driver, home.getTabInPage(tabInPage, 40), "Tab Name: " + tabInPage,
									action.SCROLLANDBOOLEAN)) {

								log(LogStatus.INFO, "Clicked on Tab: " + tabInPage, YesNo.No);

								cp.verifySDGRecord(sdgName, columnEqualValue, columnNameBasedOnWhichRecordSearch);

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPage, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPage);
							}

						} else

						{
							sa.assertTrue(false, "Contact Page not Open: " + PEFSTGContacts_1_FName + " "
									+ PEFSTGContacts_1_LName);
							log(LogStatus.FAIL, "Contact Page not Open: " + PEFSTGContacts_1_FName + " "
									+ PEFSTGContacts_1_LName, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName
								+ " " + PEFSTGContacts_1_LName);
						log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
								+ PEFSTGContacts_1_LName, YesNo.Yes);

					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Tab");
					log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
				}

				if (home.sdgGridColumnsDataOfFirstRow(sdgName, 2).getText().equals(firmToSelect)) {
					log(LogStatus.INFO, "Firm Found: " + firmToSelect, YesNo.No);

					if (CommonLib.clickUsingJavaScript(driver,
							home.sdgGridColumnsDataOfFirstRowFirmColumn(sdgName, 2), "sdgGridColumnsDataOfFirstRow",
							action.SCROLLANDBOOLEAN)) {

						log(LogStatus.INFO, "Clicked on Firm: " + firmToSelect, YesNo.No);

						String parentID = CommonLib.switchOnWindow(driver);

						CommonLib.ThreadSleep(15000);
						CommonLib.refresh(driver);

						String xPath = "//div[contains(@class,'entityNameTitle')]";
						WebElement ele = CommonLib.FindElement(driver, xPath, firmToSelect + " heading",
								action.SCROLLANDBOOLEAN, 40);

						if (ele != null) {

							log(LogStatus.INFO, "Value Verified : " + valuesList.get(0)
									+ " corresponding to label: " + labelsList.get(0), YesNo.No);
							log(LogStatus.INFO, "Redirection is working properly on : " + firmToSelect, YesNo.No);
							driver.close();
							driver.switchTo().window(parentID);

						} else {
							log(LogStatus.ERROR, "Firm Detail Page not Verified : " + firmToSelect
									+ " Created Through SDG: " + sdgName, YesNo.Yes);
							sa.assertTrue(false, "Redirection is not working properly on: " + firmToSelect);
							log(LogStatus.ERROR, "Redirection is not working properly on: " + firmToSelect,
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentID);
						}

					} else {
						sa.assertTrue(false, "Not Able to click on Firm: " + firmToSelect
								+ " So, not able to verify Page will redirect to Company name detail Page");
						log(LogStatus.ERROR,
								"Not Able to click on Firm: " + firmToSelect
										+ " So, not able to verify Page will redirect to Company name detail Page",
								YesNo.Yes);

					}

				}

				else {
					sa.assertTrue(false, "No Firm Found: " + firmToSelect
							+ " So, not able to verify Page will redirect to Company name detail Page");
					log(LogStatus.ERROR,
							"No Firm Found: " + firmToSelect
									+ " So, not able to verify Page will redirect to Company name detail Page",
							YesNo.Yes);
				}

				if (click(driver, home.sdgGridColumnsDataOfFirstRowDetailButton(sdgName, 30), "Detail Button",
						action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Detail Button", YesNo.No);

					CommonLib.ThreadSleep(8000);
					List<String> detailPageLabelValuesText = home.detailPageLabelValues("Information").stream()
							.map(x -> x.getText()).collect(Collectors.toList());
					int i = 0;
					for (String label : labelsList) {

						if (detailPageLabelValuesText.get(i).contains(label)) {
							log(LogStatus.INFO, "Label Verified : " + label + " Created Through SDG: " + sdgName,
									YesNo.No);

							if (detailPageLabelValuesText.get(i).contains(valuesList.get(i))) {
								log(LogStatus.INFO, "Value Verified : " + valuesList.get(i)
										+ " corresponding to label: " + label, YesNo.No);

							} else {
								log(LogStatus.ERROR, "Value Not Verified : " + valuesList.get(i)
										+ " corresponding to label: " + label + " So, Affiliation not created",
										YesNo.Yes);
								sa.assertTrue(false, "Value Not Verified : " + valuesList.get(i)
										+ " corresponding to label: " + label + " So, Affiliation not created");
							}

						} else {
							log(LogStatus.ERROR,
									"Label not Verified : " + label + " Created Through SDG: " + sdgName,
									YesNo.Yes);
							sa.assertTrue(false,
									"Label not Verified : " + label + " Created Through SDG: " + sdgName);
						}

						i++;
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Detail Button", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Detail Button");
				}

				if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
					if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
						log(LogStatus.INFO,
								"Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
								YesNo.No);

						WebElement contact = cp
								.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName, 30);

						if (contact != null) {

							log(LogStatus.INFO, "Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " "
									+ PEFSTGContacts_1_LName, YesNo.No);

							if (click(driver, home.getTabInPage(tabInPage, 40), "Tab Name: " + tabInPage,
									action.SCROLLANDBOOLEAN)) {

								log(LogStatus.INFO, "Clicked on Tab: " + tabInPage, YesNo.No);

								ArrayList<String> negativResult = cp
										.VerifyInlineEditingForContactsAndAffiliationsGrid(inlineEditRecord);

								if (negativResult.isEmpty()) {
									log(LogStatus.INFO, "InLine Editing of Record successfull for SDG: " + sdgName,
											YesNo.No);
								} else {

									sa.assertTrue(false, "InLine Editing of Record not successfull for SDG: "
											+ sdgName + " , Reason: " + negativResult);
									log(LogStatus.FAIL, "InLine Editing of Record not successfull for SDG: "
											+ sdgName + " , Reason: " + negativResult, YesNo.Yes);
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPage, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPage);
							}

						} else

						{
							sa.assertTrue(false, "Contact Page not Open: " + PEFSTGContacts_1_FName + " "
									+ PEFSTGContacts_1_LName);
							log(LogStatus.FAIL, "Contact Page not Open: " + PEFSTGContacts_1_FName + " "
									+ PEFSTGContacts_1_LName, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName
								+ " " + PEFSTGContacts_1_LName);
						log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
								+ PEFSTGContacts_1_LName, YesNo.Yes);

					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Tab");
					log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
				}

			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName", "environment", "mode" })
@Test
public void PEFSTGTcS009_VerifyInvestorRelationsTabOnContactRecordPage(String environment, String mode,
		String projectName) {
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	PartnershipsPageBusinessLayer ps = new PartnershipsPageBusinessLayer(driver);
	CommitmentsPageBusinessLayer commit = new CommitmentsPageBusinessLayer(driver);

	String[] sdgNames = "Fundraising<Break>Co-Investments<Break>Correspondence List".split("<Break>");
	String tabInPage = "Investor Relations";
	String[] buttonNames = "New Fundraising Contact<Break>New Fundraising Contact<Break>New Correspondence List"
			.split("<Break>");

	String startDate = CommonLib.getDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy");
	String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy", 4);
	String firmToSelect = "pe_advisor_record01";
	String roleToChosen = "Former Employee";
	String[] columnsAccToSDG = "Fundraising<Break>Fund<Break>Role<Break>Closing<Break>Stage<Break>Close Date<Break>Potential Investment (M)<Section>Fundraising<Break>Company<Break>Role<Break>Stage<Break>Close Date<Break>Investment (M)<Section>Fund<Break>Investor<Break>Type<Break>Commitment Amount<Break>Advisory Commitee Participation"
			.split("<Section>");

	String[] labelsAccTSDG = "Fundraising<Break>Role<Break>Firm<Section>Fundraising<Break>Role<Break>Firm<Section>Commitment<Break>Advisory Commitee Participation"
			.split("<Section>");
	String[] valuesAccTSDG = "Birla Fundraising<Break>Advisor<Break>Birla Group<Section>Birla Co Fundraising<Break>Evaluator<Break>Birla Group<Section>CMT - 000955<Break>Advisory Committee Member"
			.split("<Section>");
	String[] typesOfFieldsAccTSDG = "SearchDropDown<Break>DropDown<Break>SearchDropDown<Section>SearchDropDown<Break>DropDown<Break>SearchDropDown<Section>SearchDropDown<Break>DropDown"
			.split("<Section>");

	List<String> columnsInSDGs = Arrays.asList("FIRM<Break>ROLE<Break>START DATE<Break>END DATE".split("<Break>"));
	String expectDataCorresspondToColumns = firmToSelect + "<Break>" + roleToChosen + "<Break>" + startDate
			+ "<Break>" + endDate;
	List<String> expectedDataCorresspondToColumns = Arrays.asList(expectDataCorresspondToColumns.split("<Break>"));
	List<String> labelsList = new ArrayList<String>();
	List<String> valuesList = new ArrayList<String>();
	labelsList.add("Firm");

	valuesList.add(firmToSelect);

	String[] fundNames = "Birla Fund<Break>Birla Co Fund".split("<Break>");
	String[] fundTypes = "Fund<Break>Fund".split("<Break>");
	String[] investmentCategories = "Fund<Break>Fund".split("<Break>");
	String otherLabelFields = null;
	String otherLabelValues = null;

	String[] institutionName = "Birla Group<Break>BirlaSoft<Break>Birla LP".split("<Break>");
	String[] recordType = "Institution<Break>Company<Break>Limited Partner".split("<Break>");
	String[] labelsOfFirmPopUp = "<Break><Break>Parent Firm".split("<Break>");
	String[] valuesOfFirmPopUp = "<Break><Break>Birla Group".split("<Break>");

	String[] fundraisingNames = "Birla Fundraising<Break>Birla Co Fundraising".split("<Break>");
	String[] fundraisingsFundName = "Birla Fund<Break>Birla Co Fund".split("<Break>");
	String[] fundraisingsInstitutionName = "Birla Group<Break>Birla Group".split("<Break>");
	String[] fundraisingsCompanyName = "<Break>BirlaSoft".split("<Break>");

	String[] partnershipNames = "Birla Partnership".split("<Break>");
	String[] partnershipFundNames = "Birla Fund".split("<Break>");

	String[] commitmentsLimitedPartner = "Birla LP".split("<Break>");
	String[] commitmentsPartnership = "Birla Partnership".split("<Break>");
	String[] commitmentsAmount = "100".split("<Break>");

	lp.CRMLogin(crmUser1EmailID, adminPassword);

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contactName = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contactName != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);

				for (int i = 0; i < sdgNames.length; i++) {
					if (cp.verifySDGGridInPageTab(tabInPage, sdgNames[i])) {
						log(LogStatus.PASS, "Verified SDG Name: " + sdgNames[i] + " on Tab: " + tabInPage,
								YesNo.No);
					} else {
						sa.assertTrue(false, "Not Verified SDG Name: " + sdgNames[i] + " on Tab: " + tabInPage);
						log(LogStatus.FAIL, "Not Verified SDG Name: " + sdgNames[i] + " on Tab: " + tabInPage,
								YesNo.Yes);
					}
				}

				for (int i = 0; i < columnsAccToSDG.length; i++) {
					List<String> columnsInSDG = Arrays.asList(columnsAccToSDG[i].split("<Break>"));
					if (edit.verifyColumnsOfSDG(sdgNames[i], columnsInSDG)) {
						log(LogStatus.PASS, "-------Columns of SDG: " + sdgNames[i] + " are Matched--------",
								YesNo.No);

					} else {
						log(LogStatus.FAIL, "------Columns of SDG: " + sdgNames[i] + " are not Matched------",
								YesNo.Yes);
						sa.assertTrue(false, "------Columns of SDG: " + sdgNames[i] + " are not Matched------");
					}

				}

				for (int i = 0; i < buttonNames.length; i++) {
					if (cp.sdgButtonName(sdgNames[i], buttonNames[i], 30) != null) {
						log(LogStatus.PASS, "-------Button of SDG: " + sdgNames[i] + " is Matched: "
								+ buttonNames[i] + "--------", YesNo.No);
					} else {
						log(LogStatus.FAIL, "-------Button of SDG: " + sdgNames[i] + " is not Matched: "
								+ buttonNames[i] + "--------", YesNo.Yes);
						sa.assertTrue(false, "-------Button of SDG: " + sdgNames[i] + " is not Matched: "
								+ buttonNames[i] + "--------");
					}
				}

				int firmLoopCount = 0;
				for (String instName : institutionName) {
					if (lp.clickOnTab(projectName, tabObj1)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

						if (ip.createInstitution(projectName, environment, mode, instName,
								recordType[firmLoopCount], labelsOfFirmPopUp[firmLoopCount],
								valuesOfFirmPopUp[firmLoopCount]))

						{
							log(LogStatus.INFO, "successfully Created Account/Entity : " + instName
									+ " of record type : " + recordType[firmLoopCount], YesNo.No);
						} else {
							sa.assertTrue(false, "Not Able to Create Account/Entity : " + instName
									+ " of record type : " + recordType[firmLoopCount]);
							log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + instName
									+ " of record type : " + recordType[firmLoopCount], YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj1);
						log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj1, YesNo.Yes);
					}
					ThreadSleep(2000);
					firmLoopCount++;
				}

				int fundLoopCount = 0;
				for (String fundName : fundNames) {
					if (fund.clickOnTab(environment, mode, TabName.FundsTab)) {

						if (fund.createFund(projectName, fundName, fundTypes[fundLoopCount],
								investmentCategories[fundLoopCount], otherLabelFields, otherLabelValues)) {
							appLog.info("Fund is created Successfully: " + fundName);

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

				int fundraisingLoopCount = 0;
				for (String fundraisingName : fundraisingNames) {
					if (bp.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
						if (fr.createFundRaising(environment, "Lightning", fundraisingName,
								fundraisingsFundName[fundraisingLoopCount],
								fundraisingsInstitutionName[fundraisingLoopCount], "", "", "", "",
								fundraisingsCompanyName[fundraisingLoopCount])) {
							appLog.info("fundraising is created : " + fundraisingName);
						} else {
							appLog.error("Not able to create fundraising: " + fundraisingName);
							sa.assertTrue(false, "Not able to create fundraising: " + fundraisingName);
						}
					} else {
						appLog.error("Not able to click on fundraising tab so cannot create fundraising: "
								+ fundraisingName);
						sa.assertTrue(false, "Not able to click on fundraising tab so cannot create fundraising: "
								+ fundraisingName);
					}
					ThreadSleep(2000);

					fundraisingLoopCount++;

				}

				int partnershipLoopCount = 0;
				for (String partnershipName : partnershipNames) {
					if (bp.clickOnTab(environment, mode, TabName.PartnershipsTab)) {

						if (ps.createPartnership(projectName, environment, "Lightning", partnershipName,
								partnershipFundNames[partnershipLoopCount])) {
							appLog.info("Partnership is created : " + partnershipName);
						} else {
							appLog.error("Not able to create Partnership: " + partnershipName);
							sa.assertTrue(false, "Not able to create Partnership: " + partnershipName);
						}
					} else {
						appLog.error("Not able to click on Partnership tab so cannot create Partnership: "
								+ partnershipName);
						sa.assertTrue(false, "Not able to click on Partnership tab so cannot create Partnership: "
								+ partnershipName);
					}
					ThreadSleep(2000);

					partnershipLoopCount++;

				}

				int commitmentLoopCount = 0;
				for (String commitmentLimitedPartner : commitmentsLimitedPartner) {
					if (bp.clickOnTab(environment, mode, TabName.CommitmentsTab)) {

						if (commit.createCommitment(projectName, commitmentLimitedPartner,
								commitmentsPartnership[commitmentLoopCount], null,
								commitmentsAmount[commitmentLoopCount], phase1DataSheetFilePath, null)) {
							appLog.info("Commitment created with Limited Partner: " + commitmentLimitedPartner
									+ " & Partnership: " + commitmentsPartnership[commitmentLoopCount]);
						} else {
							appLog.error("Not able to create Commitment with Limited Partner: "
									+ commitmentLimitedPartner + " & Partnership: "
									+ commitmentsPartnership[commitmentLoopCount]);
							sa.assertTrue(false,
									"Not able to create Commitment with Limited Partner: "
											+ commitmentLimitedPartner + " & Partnership: "
											+ commitmentsPartnership[commitmentLoopCount]);
						}
					} else {
						appLog.error(
								"Not able to click on Commitment tab so cannot create Commitment with Limited Partner: "
										+ commitmentLimitedPartner + " & Partnership: "
										+ commitmentsPartnership[commitmentLoopCount]);
						sa.assertTrue(false,
								"Not able to click on Commitment tab so cannot create Commitment with Limited Partner: "
										+ commitmentLimitedPartner + " & Partnership: "
										+ commitmentsPartnership[commitmentLoopCount]);
					}
					ThreadSleep(2000);

					commitmentLoopCount++;

				}

				for (int i = 0; i < buttonNames.length; i++) {
					List<String> labels = Arrays.asList(labelsAccTSDG[i].split("<Break>"));
					List<String> values = Arrays.asList(valuesAccTSDG[i].split("<Break>"));
					List<String> typesOfFields = Arrays.asList(typesOfFieldsAccTSDG[i].split("<Break>"));

					if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
						if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
							log(LogStatus.INFO,
									"Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
									YesNo.No);

							WebElement contact = cp.getContactFullNameEle(
									PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName, 30);

							if (contact != null) {

								log(LogStatus.INFO, "Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " "
										+ PEFSTGContacts_1_LName, YesNo.No);
								if (click(driver, home.getTabInPage(tabInPage, 40), "Tab Name: " + tabInPage,
										action.SCROLLANDBOOLEAN)) {

									log(LogStatus.INFO, "Clicked on Tab: " + tabInPage, YesNo.No);

									if (cp.createNewRecordThroughSDG(projectName, sdgNames[i], buttonNames[i],
											labels, values, typesOfFields, 30)) {
										log(LogStatus.PASS, "-------Record has been created of SDG: " + sdgNames[i]
												+ "--------", YesNo.No);
										List<String> detailPageLabelValuesText = home
												.detailPageLabelValues("Information").stream().map(x -> x.getText())
												.collect(Collectors.toList());
										/*
										 * int i = 0; for (String label : labelsList) {
										 * 
										 * if (detailPageLabelValuesText.get(i).contains(label)) {
										 * log(LogStatus.INFO, "Label Verified : " + label +
										 * " Created Through SDG: " + sdgName, YesNo.No);
										 * 
										 * if (detailPageLabelValuesText.get(i).contains(valuesList.get(i))) {
										 * log(LogStatus.INFO, "Value Verified : " + valuesList.get(i) +
										 * " corresponding to label: " + label, YesNo.No);
										 * 
										 * } else { log(LogStatus.ERROR, "Value Not Verified : " + valuesList.get(i)
										 * + " corresponding to label: " + label + " So, Affiliation not created",
										 * YesNo.Yes); sa.assertTrue(false, "Value Not Verified : " +
										 * valuesList.get(i) + " corresponding to label: " + label +
										 * " So, Affiliation not created"); }
										 * 
										 * } else { log(LogStatus.ERROR, "Label not Verified : " + label +
										 * " Created Through SDG: " + sdgName, YesNo.Yes); sa.assertTrue(false,
										 * "Label not Verified : " + label + " Created Through SDG: " + sdgName); }
										 * 
										 * i++; }
										 * 
										 * 
										 */

									} else {
										log(LogStatus.FAIL, "-------Record has not been created of SDG: "
												+ sdgNames[i] + "--------", YesNo.Yes);
										sa.assertTrue(false, "-------Record has not been created of SDG: "
												+ sdgNames[i] + "--------");
									}
								} else {
									log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPage, YesNo.Yes);
									sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPage);
								}
							} else

							{
								sa.assertTrue(false, "Contact Page not Open: " + PEFSTGContacts_1_FName + " "
										+ PEFSTGContacts_1_LName);
								log(LogStatus.FAIL, "Contact Page not Open: " + PEFSTGContacts_1_FName + " "
										+ PEFSTGContacts_1_LName, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName
									+ " " + PEFSTGContacts_1_LName);
							log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName
									+ " " + PEFSTGContacts_1_LName, YesNo.Yes);

						}
					} else {
						sa.assertTrue(false, "Not Able to Click on Contact Tab");
						log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
					}

				}

			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName", "environment", "mode" })

@Test
public void PEFSTGTcS010_VerifyDealsTabOnContactRecordPage(String environment, String mode, String projectName) {
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

	String[] sdgNames = "Referrals<Break>Sourced Deals<Break>Deal Team".split("<Break>", -1);
	String tabInPage = "Deals";
	String[] buttonNames = "New Referral<Break>New Deal<Break>New Team Member".split("<Break>", -1);

	String[] labelsAccTSDG = "Legal Name<Break>Status<Break>Introduction Date<Section>Deal Name<Break>Company<Break>Stage<Break>Date Received<Section>Deal<Break>Team Member<Break>Role"
			.split("<Section>", -1);
	String[] valuesAccTSDG = "TCS<Break>Under Evaluation<Break>20<Section>TCS Deal<Break>TCS<Break>Deal Received<Break><Section>TCS Deal<Break>PE Standard User<Break>Partner"
			.split("<Section>", -1);
	String[] typesOfFieldsAccTSDG = "TextBox<Break>DropDown<Break>DatePickerFutureDate<Section>TextBox<Break>SearchDropDown<Break>DropDown<Break>DatePickerCurrentDate<Section>SearchDropDown<Break>SearchDropDown<Break>DropDown"
			.split("<Section>", -1);

	String[] columnNameInWhichRecordPresentForRedirect = "NAME<Break>DEAL<Break>DEAL".split("<Break>", -1);
	String[] recordNameToRedirect = "TCS<Break>TCS Deal<Break>TCS Deal".split("<Break>", -1);

	String[] columnsAccToSDG = "Name<Break>Status<Break>Introduction Date<Break>Last Touchpoint<Section>Deal<Break>Source Firm<Break>Highest Stage Reached<Break>Date<Section>Deal<Break>Company<Break>Deal Stage<Break>Role"
			.split("<Section>");
	// =CONCATENATE("Today is ",TEXT(TODAY(), "mmmm dd, yyyy"))

	String currentDate = CommonLib.getDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy");
	String futureDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy", 20);
	String[] columnEqualValue = ("NAME = TCS<Break>STATUS = Under Evaluation <Break> INTRODUCTION DATE = "
			+ futureDate
			+ "<Break> LAST TOUCHPOINT = <Section> DEAL = TCS Deal <Break> SOURCE FIRM = <Break> HIGHEST STAGE REACHED = Deal Received <Break> DATE =  "
			+ currentDate
			+ "<Section> DEAL = TCS Deal <Break> COMPANY = TCS <Break> DEAL STAGE = Deal Received <Break> ROLE = Partner")
			.split("<Section>", -1);

	String[] columnNameBasedOnWhichRecordSearchInSDG = ("NAME<Section>DEAL<Section>DEAL").split("<Section>", -1);

	lp.CRMLogin(crmUser1EmailID, adminPassword);

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contactName = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contactName != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);

				for (int i = 0; i < sdgNames.length; i++) {
					if (cp.verifySDGGridInPageTab(tabInPage, sdgNames[i])) {
						log(LogStatus.PASS, "Verified SDG Name: " + sdgNames[i] + " on Tab: " + tabInPage,
								YesNo.No);
					} else {
						sa.assertTrue(false, "Not Verified SDG Name: " + sdgNames[i] + " on Tab: " + tabInPage);
						log(LogStatus.FAIL, "Not Verified SDG Name: " + sdgNames[i] + " on Tab: " + tabInPage,
								YesNo.Yes);
					}
				}

				for (int i = 0; i < columnsAccToSDG.length; i++) {
					List<String> columnsInSDG = Arrays.asList(columnsAccToSDG[i].split("<Break>"));
					if (edit.verifyColumnsOfSDG(sdgNames[i], columnsInSDG)) {
						log(LogStatus.PASS, "-------Columns of SDG: " + sdgNames[i] + " are Matched--------",
								YesNo.No);

					} else {
						log(LogStatus.FAIL, "------Columns of SDG: " + sdgNames[i] + " are not Matched------",
								YesNo.Yes);
						sa.assertTrue(false, "------Columns of SDG: " + sdgNames[i] + " are not Matched------");
					}

				}

				for (int i = 0; i < buttonNames.length; i++) {
					if (cp.sdgButtonName(sdgNames[i], buttonNames[i], 30) != null) {
						log(LogStatus.PASS, "-------Button of SDG: " + sdgNames[i] + " is Matched: "
								+ buttonNames[i] + "--------", YesNo.No);
					} else {
						log(LogStatus.FAIL, "-------Button of SDG: " + sdgNames[i] + " is not Matched: "
								+ buttonNames[i] + "--------", YesNo.Yes);
						sa.assertTrue(false, "-------Button of SDG: " + sdgNames[i] + " is not Matched: "
								+ buttonNames[i] + "--------");
					}
				}

				for (int i = 0; i < buttonNames.length; i++) {
					List<String> labels = Arrays.asList(labelsAccTSDG[i].split("<Break>", -1));
					List<String> values = Arrays.asList(valuesAccTSDG[i].split("<Break>", -1));
					List<String> typesOfFields = Arrays.asList(typesOfFieldsAccTSDG[i].split("<Break>", -1));

					if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
						if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
							log(LogStatus.INFO,
									"Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
									YesNo.No);

							WebElement contact = cp.getContactFullNameEle(
									PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName, 30);

							if (contact != null) {

								log(LogStatus.INFO, "Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " "
										+ PEFSTGContacts_1_LName, YesNo.No);
								if (click(driver, home.getTabInPage(tabInPage, 40), "Tab Name: " + tabInPage,
										action.SCROLLANDBOOLEAN)) {

									log(LogStatus.INFO, "Clicked on Tab: " + tabInPage, YesNo.No);

									if (cp.createNewRecordThroughSDG(projectName, sdgNames[i], buttonNames[i],
											labels, values, typesOfFields, 30)) {
										log(LogStatus.PASS, "-------Record has been created of SDG: " + sdgNames[i]
												+ "--------", YesNo.No);

										List<String> detailPageLabelValuesText = home
												.detailPageLabelValues("Company Information").stream()
												.map(x -> x.getText()).collect(Collectors.toList());

										/*
										 * int i = 0; for (String label : labelsList) {
										 * 
										 * if (detailPageLabelValuesText.get(i).contains(label)) {
										 * log(LogStatus.INFO, "Label Verified : " + label +
										 * " Created Through SDG: " + sdgName, YesNo.No);
										 * 
										 * if (detailPageLabelValuesText.get(i).contains(valuesList.get(i))) {
										 * log(LogStatus.INFO, "Value Verified : " + valuesList.get(i) +
										 * " corresponding to label: " + label, YesNo.No);
										 * 
										 * } else { log(LogStatus.ERROR, "Value Not Verified : " + valuesList.get(i)
										 * + " corresponding to label: " + label + " So, Affiliation not created",
										 * YesNo.Yes); sa.assertTrue(false, "Value Not Verified : " +
										 * valuesList.get(i) + " corresponding to label: " + label +
										 * " So, Affiliation not created"); }
										 * 
										 * } else { log(LogStatus.ERROR, "Label not Verified : " + label +
										 * " Created Through SDG: " + sdgName, YesNo.Yes); sa.assertTrue(false,
										 * "Label not Verified : " + label + " Created Through SDG: " + sdgName); }
										 * 
										 * i++; }
										 */
									} else {
										log(LogStatus.FAIL, "-------Record has not been created of SDG: "
												+ sdgNames[i] + "--------", YesNo.Yes);
										sa.assertTrue(false, "-------Record has not been created of SDG: "
												+ sdgNames[i] + "--------");
									}
								} else {
									log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPage, YesNo.Yes);
									sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPage);
								}
							} else

							{
								sa.assertTrue(false, "Contact Page not Open: " + PEFSTGContacts_1_FName + " "
										+ PEFSTGContacts_1_LName);
								log(LogStatus.FAIL, "Contact Page not Open: " + PEFSTGContacts_1_FName + " "
										+ PEFSTGContacts_1_LName, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName
									+ " " + PEFSTGContacts_1_LName);
							log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName
									+ " " + PEFSTGContacts_1_LName, YesNo.Yes);

						}
					} else {
						sa.assertTrue(false, "Not Able to Click on Contact Tab");
						log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
					}

				}

				if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
					if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
						log(LogStatus.INFO,
								"Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
								YesNo.No);

						WebElement contact = cp
								.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName, 30);

						if (contact != null) {

							log(LogStatus.INFO, "Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " "
									+ PEFSTGContacts_1_LName, YesNo.No);
							if (click(driver, home.getTabInPage(tabInPage, 40), "Tab Name: " + tabInPage,
									action.SCROLLANDBOOLEAN)) {

								log(LogStatus.INFO, "Clicked on Tab: " + tabInPage, YesNo.No);

								for (int i = 0; i < sdgNames.length; i++) {
									if (cp.verifySDGRecordRedirectToNewWindow(sdgNames[i],
											columnNameInWhichRecordPresentForRedirect[i],
											recordNameToRedirect[i])) {
										log(LogStatus.PASS,
												"-------Record: " + recordNameToRedirect[i]
														+ " has been Redirect of SDG: " + sdgNames[i] + "--------",
												YesNo.No);

									} else {
										log(LogStatus.FAIL, "-------Record: " + recordNameToRedirect[i]
												+ " has not been Redirect of SDG: " + sdgNames[i] + "--------",
												YesNo.Yes);
										sa.assertTrue(false, "-------Record: " + recordNameToRedirect[i]
												+ " has not been Redirect of SDG: " + sdgNames[i] + "--------");
									}

									String[] columnEqualValueForSDG = columnEqualValue[i].split("<Break>", -1);

									log(LogStatus.PASS, "-------Now Going to verify Record in SDG--------",
											YesNo.No);
									cp.verifySDGRecord(sdgNames[i], columnEqualValueForSDG,
											columnNameBasedOnWhichRecordSearchInSDG[i]);

								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPage, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPage);
							}
						} else

						{
							sa.assertTrue(false, "Contact Page not Open: " + PEFSTGContacts_1_FName + " "
									+ PEFSTGContacts_1_LName);
							log(LogStatus.FAIL, "Contact Page not Open: " + PEFSTGContacts_1_FName + " "
									+ PEFSTGContacts_1_LName, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName
								+ " " + PEFSTGContacts_1_LName);
						log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
								+ PEFSTGContacts_1_LName, YesNo.Yes);

					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Tab");
					log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
				}

			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName" })
@Test

public void PEFSTGTcS012_VerifyFilesTabOnContactRecordPage(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	FirmPageBusinessLayer FB = new FirmPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	String fileName = "Simple.pdf";
	String fileType = "pdf";
	String fileSize = "3KB";
	lp.CRMLogin(crmUser1EmailID, adminPassword);
	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contactName = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contactName != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);
				String xPath = "//a[@data-label='Files']";
				WebElement ele = CommonLib.FindElement(driver, xPath, "Files tab", action.SCROLLANDBOOLEAN, 50);

				if (click(driver, ele, "Files Tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Files tab button", YesNo.No);

					ArrayList<String> result = FB.verifyFileCountUploadAndAddFileButton();
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"Files (count), Add File, Upload File buttons and Or drop File text are visible",
								YesNo.No);
						sa.assertTrue(true,
								"Files (count), Add File, Upload File buttons and Or drop File text are visible");

						if (bp.uploadFileAndVerify(fileName, fileType, fileSize)) {
							log(LogStatus.PASS,
									"The file has been uploaded sucessfully. Uploaded date, size, and type have been verified",
									YesNo.No);
							sa.assertTrue(true,
									"The file has been uploaded sucessfully. Uploaded date, size, and type have been verified");

						} else {
							log(LogStatus.FAIL,
									"Either The file is not uploaded or Uploaded date, size, and type are not verified",
									YesNo.No);
							sa.assertTrue(false,
									"Either The file is not uploaded or Uploaded date, size, and type are not verified");

						}
					} else {
						log(LogStatus.FAIL,
								"Either Files (count) or Add File or Upload File buttons or Or drop File text  are not visible",
								YesNo.No);
						sa.assertTrue(false,
								"Either Files (count) or Add File or Upload File buttons or Or drop File text are not visible");
					}

				} else {
					log(LogStatus.FAIL, "Not able to click on Files tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Files tab");

				}
			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName", "environment", "mode" })

@Test
public void PEFSTGTcS013_VerifyRecordPageCustomButtonFunctionality(String environment, String mode,
		String projectName) {
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	PartnershipsPageBusinessLayer ps = new PartnershipsPageBusinessLayer(driver);
	CommitmentsPageBusinessLayer commit = new CommitmentsPageBusinessLayer(driver);

	String[] recordPageButtonNames = "New Affiliation<Section>New Referral<Section>New Sourced Deal"
			.split("<Section>");

	String[] recordPageLabelValuesAndTypesOfElements = "Firm<Br>Birla Group<Br>SearchDropDown<Break>Role<Br>Consultant<Br>MultiPickList<Break>Start Date<Br><Br>DatePickerCurrentDate<Break>End Date<Br>4<Br>DatePickerFutureDate<Section>Legal Name<Br>Birla Group<Br>TextBox<Break>Status<Br>Watchlist<Br>DropDown<Break>Introduction Date<Br><Br>DatePickerCurrentDate<Section>Deal Name<Br>Satyam Deal<Br>TextBox<Break>Company<Br>Satyam Group<Br>SearchDropDown<Break>Stage<Br>Deal Received<Br>DropDown<Break>Date Received<Br><Br>DatePickerCurrentDate"
			.split("<Section>");

	String startDate = CommonLib.getDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy");
	String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy", 4);

	String[] institutionName = "Satyam Group<Break>BirlaTech Institution<Break>BirlaTech LP".split("<Break>");
	String[] recordType = "Company<Break>Institution<Break>Limited Partner".split("<Break>");
	String[] labelsOfFirmPopUp = "<Break><Break>Parent Firm".split("<Break>");
	String[] valuesOfFirmPopUp = "<Break><Break>BirlaTech Institution".split("<Break>");

	String tabInPageDeals = "Deals";
	String sdgName = "Deal Team";
	String buttonName = "New Team Member";

	String[] labelsAccTSDG = "Deal<Break>Team Member<Break>Role".split("<Break>");
	String[] valuesAccTSDG = ("Satyam Deal<Break>" + crmUser1FirstName + " " + crmUser1LastName + "<Break>Analyst")
			.split("<Break>");
	String[] typesOfFieldsAccTSDG = "SearchDropDown<Break>SearchDropDown<Break>DropDown".split("<Break>");

	List<String> labels = Arrays.asList(labelsAccTSDG);
	List<String> values = Arrays.asList(valuesAccTSDG);
	List<String> typesOfFields = Arrays.asList(typesOfFieldsAccTSDG);

	String[] fundraisingNames = "BirlaTech Fundraising<Break>BirlaTech Co Fundraising".split("<Break>");
	String[] fundraisingsFundName = "Birla Fund<Break>Birla Co Fund".split("<Break>");
	String[] fundraisingsInstitutionName = "Birla Group<Break>Birla Group".split("<Break>");
	String[] fundraisingsCompanyName = "<Break>BirlaSoft".split("<Break>");

	String sdgNameFundraising = "Fundraising";
	String tabInPageInvestorRelations = "Investor Relations";
	String buttonNameFundraisingContact = "New Fundraising Contact";

	String[] labelsAccTSDGInvestorRelations = "Fundraising<Break>Role<Break>Firm".split("<Break>");
	String[] valuesAccTSDGInvestorRelations = "BirlaTech Fundraising<Break>Advisor<Break>Birla Group"
			.split("<Break>");
	String[] typesOfFieldsAccTSDGInvestorRelations = "SearchDropDown<Break>DropDown<Break>SearchDropDown"
			.split("<Break>");

	List<String> labelsInvestorRelations = Arrays.asList(labelsAccTSDGInvestorRelations);
	List<String> valuesInvestorRelations = Arrays.asList(valuesAccTSDGInvestorRelations);
	List<String> typesOfFieldsInvestorRelations = Arrays.asList(typesOfFieldsAccTSDGInvestorRelations);

	String[] partnershipNames = "BirlaTech Partnership".split("<Break>");
	String[] partnershipFundNames = "Birla Fund".split("<Break>");

	String[] commitmentsLimitedPartner = "BirlaTech LP".split("<Break>");
	String[] commitmentsPartnership = "BirlaTech Partnership".split("<Break>");
	String[] commitmentsAmount = "150".split("<Break>");

	String sdgNameCoInvestments = "Co-Investments";

	String[] labelsAccTSDGCoInvestments = "Fundraising<Break>Role<Break>Firm".split("<Break>");
	String[] valuesAccTSDGCoInvestments = "BirlaTech Co Fundraising<Break>Evaluator<Break>Birla Group"
			.split("<Break>");
	String[] typesOfFieldsAccTSDGCoInvestments = "SearchDropDown<Break>DropDown<Break>SearchDropDown"
			.split("<Break>");

	List<String> labelsCoInvestments = Arrays.asList(labelsAccTSDGCoInvestments);
	List<String> valuesCoInvestments = Arrays.asList(valuesAccTSDGCoInvestments);
	List<String> typesOfFieldsCoInvestments = Arrays.asList(typesOfFieldsAccTSDGCoInvestments);

	lp.CRMLogin(crmUser1EmailID, adminPassword);

	int firmLoopCount = 0;
	for (String instName : institutionName) {
		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (ip.createInstitution(projectName, environment, mode, instName, recordType[firmLoopCount],
					labelsOfFirmPopUp[firmLoopCount].trim(), valuesOfFirmPopUp[firmLoopCount].trim()))

			{
				log(LogStatus.INFO, "successfully Created Account/Entity : " + instName + " of record type : "
						+ recordType[firmLoopCount], YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Account/Entity : " + instName + " of record type : "
						+ recordType[firmLoopCount]);
				log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + instName + " of record type : "
						+ recordType[firmLoopCount], YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj1);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj1, YesNo.Yes);
		}
		ThreadSleep(2000);
		firmLoopCount++;
	}

	String[][] requiredRecordPageLabelValuesAndTypesOfElements = null;
	int recordPageLoopCount = 0;
	for (String recordPageLabelValuesAndTypesOfElement : recordPageLabelValuesAndTypesOfElements) {
		requiredRecordPageLabelValuesAndTypesOfElements = new String[recordPageLabelValuesAndTypesOfElement
				.split("<Break>").length][3];
		int i = 0;
		for (String recordPageLabelValuesAndTypesOfEle : recordPageLabelValuesAndTypesOfElement.split("<Break>")) {
			for (int j = 0; j < 3; j++) {
				requiredRecordPageLabelValuesAndTypesOfElements[i][j] = recordPageLabelValuesAndTypesOfEle
						.split("<Br>")[j];
			}
			i++;
		}

		if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {

			if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
				log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
						YesNo.No);

				WebElement contactName = cp
						.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName, 30);

				if (contactName != null) {

					log(LogStatus.INFO, "Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " "
							+ PEFSTGContacts_1_LName, YesNo.No);

					if (cp.clickOnRecordPageButtonForNewRecordCreation(recordPageButtonNames[recordPageLoopCount],
							8)) {
						log(LogStatus.INFO, "Clicked on Button: " + recordPageButtonNames[recordPageLoopCount]
								+ " on Record Page", YesNo.No);

						if (cp.enterDetailsForRecord(projectName, requiredRecordPageLabelValuesAndTypesOfElements,
								action.SCROLLANDBOOLEAN, 30))

						{

							if (click(driver, cp.pageLevelCreateRecordPopupSaveOrCancelButton("Save", 30),
									"Save Button ", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Save Button on "
										+ recordPageButtonNames[recordPageLoopCount] + " Form Page", YesNo.No);
							}

							else {
								log(LogStatus.ERROR,
										"Not Able to Click on Save Button on "
												+ recordPageButtonNames[recordPageLoopCount] + " Form Page",
										YesNo.Yes);

							}

						} else {
							log(LogStatus.ERROR,
									"Not Able to enter Values on " + recordPageButtonNames[recordPageLoopCount]
											+ " Form Page, So Not Able to Create New Refferal Record",
									YesNo.Yes);

							click(driver, cp.pageLevelCreateRecordPopupSaveOrCancelButton("Cancel", 30),
									"Cancel Button ", action.SCROLLANDBOOLEAN);

						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Button: "
								+ recordPageButtonNames[recordPageLoopCount] + " on Record Page", YesNo.Yes);

					}

				} else

				{
					sa.assertTrue(false,
							"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
					log(LogStatus.FAIL,
							"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
							YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
						+ PEFSTGContacts_1_LName);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
						+ PEFSTGContacts_1_LName, YesNo.Yes);

			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}

		recordPageLoopCount++;
	}

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contact = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);
				if (click(driver, home.getTabInPage(tabInPageDeals, 40), "Tab Name: " + tabInPageDeals,
						action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Tab: " + tabInPageDeals, YesNo.No);

					if (cp.createNewRecordThroughSDG(projectName, sdgName, buttonName, labels, values,
							typesOfFields, 30)) {
						log(LogStatus.PASS, "-------Record has been created of SDG: " + sdgName + "--------",
								YesNo.No);

						/*
						 * List<String> detailPageLabelValuesText =
						 * home.detailPageLabelValues("Information").stream() .map(x ->
						 * x.getText()).collect(Collectors.toList());
						 * 
						 * int i = 0; for (String label : labelsList) {
						 * 
						 * if (detailPageLabelValuesText.get(i).contains(label)) { log(LogStatus.INFO,
						 * "Label Verified : " + label + " Created Through SDG: " + sdgName, YesNo.No);
						 * 
						 * if (detailPageLabelValuesText.get(i).contains(valuesList.get(i))) {
						 * log(LogStatus.INFO, "Value Verified : " + valuesList.get(i) +
						 * " corresponding to label: " + label, YesNo.No);
						 * 
						 * } else { log(LogStatus.ERROR, "Value Not Verified : " + valuesList.get(i) +
						 * " corresponding to label: " + label + " So, Affiliation not created",
						 * YesNo.Yes); sa.assertTrue(false, "Value Not Verified : " + valuesList.get(i)
						 * + " corresponding to label: " + label + " So, Affiliation not created"); }
						 * 
						 * } else { log(LogStatus.ERROR, "Label not Verified : " + label +
						 * " Created Through SDG: " + sdgName, YesNo.Yes); sa.assertTrue(false,
						 * "Label not Verified : " + label + " Created Through SDG: " + sdgName); }
						 * 
						 * i++; }
						 */

					} else {
						log(LogStatus.FAIL, "-------Record has not been created of SDG: " + sdgName + "--------",
								YesNo.Yes);
						sa.assertTrue(false, "-------Record has not been created of SDG: " + sdgName + "--------");
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPageDeals, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPageDeals);
				}
			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	int fundraisingLoopCount = 0;
	for (String fundraisingName : fundraisingNames) {
		if (bp.clickOnTab(environment, mode, TabName.FundraisingsTab)) {

			if (fr.createFundRaising(environment, "Lightning", fundraisingName,
					fundraisingsFundName[fundraisingLoopCount], fundraisingsInstitutionName[fundraisingLoopCount],
					"", "", "", "", fundraisingsCompanyName[fundraisingLoopCount])) {
				appLog.info("fundraising is created : " + fundraisingName);
			} else {
				appLog.error("Not able to create fundraising: " + fundraisingName);
				sa.assertTrue(false, "Not able to create fundraising: " + fundraisingName);
			}
		} else {
			appLog.error("Not able to click on fundraising tab so cannot create fundraising: " + fundraisingName);
			sa.assertTrue(false,
					"Not able to click on fundraising tab so cannot create fundraising: " + fundraisingName);
		}
		ThreadSleep(2000);

		fundraisingLoopCount++;

	}

	int partnershipLoopCount = 0;
	for (String partnershipName : partnershipNames) {
		if (bp.clickOnTab(environment, mode, TabName.PartnershipsTab)) {

			if (ps.createPartnership(projectName, environment, "Lightning", partnershipName,
					partnershipFundNames[partnershipLoopCount])) {
				appLog.info("Partnership is created : " + partnershipName);
			} else {
				appLog.error("Not able to create Partnership: " + partnershipName);
				sa.assertTrue(false, "Not able to create Partnership: " + partnershipName);
			}
		} else {
			appLog.error("Not able to click on Partnership tab so cannot create Partnership: " + partnershipName);
			sa.assertTrue(false,
					"Not able to click on Partnership tab so cannot create Partnership: " + partnershipName);
		}
		ThreadSleep(2000);

		partnershipLoopCount++;

	}

	int commitmentLoopCount = 0;
	for (String commitmentLimitedPartner : commitmentsLimitedPartner) {
		if (bp.clickOnTab(environment, mode, TabName.CommitmentsTab)) {

			if (commit.createCommitment(projectName, commitmentLimitedPartner,
					commitmentsPartnership[commitmentLoopCount], null, commitmentsAmount[commitmentLoopCount],
					phase1DataSheetFilePath, null)) {
				appLog.info("Commitment created with Limited Partner: " + commitmentLimitedPartner
						+ " & Partnership: " + commitmentsPartnership[commitmentLoopCount]);
			} else {
				appLog.error("Not able to create Commitment with Limited Partner: " + commitmentLimitedPartner
						+ " & Partnership: " + commitmentsPartnership[commitmentLoopCount]);
				sa.assertTrue(false,
						"Not able to create Commitment with Limited Partner: " + commitmentLimitedPartner
								+ " & Partnership: " + commitmentsPartnership[commitmentLoopCount]);
			}
		} else {
			appLog.error("Not able to click on Commitment tab so cannot create Commitment with Limited Partner: "
					+ commitmentLimitedPartner + " & Partnership: " + commitmentsPartnership[commitmentLoopCount]);
			sa.assertTrue(false,
					"Not able to click on Commitment tab so cannot create Commitment with Limited Partner: "
							+ commitmentLimitedPartner + " & Partnership: "
							+ commitmentsPartnership[commitmentLoopCount]);
		}
		ThreadSleep(2000);

		commitmentLoopCount++;

	}

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contact = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);
				if (click(driver, home.getTabInPage(tabInPageInvestorRelations, 40),
						"Tab Name: " + tabInPageInvestorRelations, action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Tab: " + tabInPageInvestorRelations, YesNo.No);

					if (cp.createNewRecordThroughSDG(projectName, sdgNameFundraising, buttonNameFundraisingContact,
							labelsInvestorRelations, valuesInvestorRelations, typesOfFieldsInvestorRelations, 30)) {
						log(LogStatus.PASS,
								"-------Record has been created of SDG: " + sdgNameFundraising + "--------",
								YesNo.No);
						/*
						 * List<String> detailPageLabelValuesText =
						 * home.detailPageLabelValues("Information").stream() .map(x ->
						 * x.getText()).collect(Collectors.toList());
						 * 
						 * int i = 0; for (String label : labelsList) {
						 * 
						 * if (detailPageLabelValuesText.get(i).contains(label)) { log(LogStatus.INFO,
						 * "Label Verified : " + label + " Created Through SDG: " + sdgName, YesNo.No);
						 * 
						 * if (detailPageLabelValuesText.get(i).contains(valuesList.get(i))) {
						 * log(LogStatus.INFO, "Value Verified : " + valuesList.get(i) +
						 * " corresponding to label: " + label, YesNo.No);
						 * 
						 * } else { log(LogStatus.ERROR, "Value Not Verified : " + valuesList.get(i) +
						 * " corresponding to label: " + label + " So, Affiliation not created",
						 * YesNo.Yes); sa.assertTrue(false, "Value Not Verified : " + valuesList.get(i)
						 * + " corresponding to label: " + label + " So, Affiliation not created"); }
						 * 
						 * } else { log(LogStatus.ERROR, "Label not Verified : " + label +
						 * " Created Through SDG: " + sdgName, YesNo.Yes); sa.assertTrue(false,
						 * "Label not Verified : " + label + " Created Through SDG: " + sdgName); }
						 * 
						 * i++; }
						 */

					} else {
						log(LogStatus.FAIL,
								"-------Record has not been created of SDG: " + sdgNameFundraising + "--------",
								YesNo.Yes);
						sa.assertTrue(false,
								"-------Record has not been created of SDG: " + sdgNameFundraising + "--------");
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPageInvestorRelations, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPageInvestorRelations);
				}
			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contact = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);
				if (click(driver, home.getTabInPage(tabInPageInvestorRelations, 40),
						"Tab Name: " + tabInPageInvestorRelations, action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Tab: " + tabInPageInvestorRelations, YesNo.No);

					if (cp.createNewRecordThroughSDG(projectName, sdgNameCoInvestments,
							buttonNameFundraisingContact, labelsCoInvestments, valuesCoInvestments,
							typesOfFieldsCoInvestments, 30)) {
						log(LogStatus.PASS,
								"-------Record has been created of SDG: " + sdgNameCoInvestments + "--------",
								YesNo.No);

						/*
						 * List<String> detailPageLabelValuesText =
						 * home.detailPageLabelValues("Information").stream() .map(x ->
						 * x.getText()).collect(Collectors.toList());
						 * 
						 * int i = 0; for (String label : labelsList) {
						 * 
						 * if (detailPageLabelValuesText.get(i).contains(label)) { log(LogStatus.INFO,
						 * "Label Verified : " + label + " Created Through SDG: " + sdgName, YesNo.No);
						 * 
						 * if (detailPageLabelValuesText.get(i).contains(valuesList.get(i))) {
						 * log(LogStatus.INFO, "Value Verified : " + valuesList.get(i) +
						 * " corresponding to label: " + label, YesNo.No);
						 * 
						 * } else { log(LogStatus.ERROR, "Value Not Verified : " + valuesList.get(i) +
						 * " corresponding to label: " + label + " So, Affiliation not created",
						 * YesNo.Yes); sa.assertTrue(false, "Value Not Verified : " + valuesList.get(i)
						 * + " corresponding to label: " + label + " So, Affiliation not created"); }
						 * 
						 * } else { log(LogStatus.ERROR, "Label not Verified : " + label +
						 * " Created Through SDG: " + sdgName, YesNo.Yes); sa.assertTrue(false,
						 * "Label not Verified : " + label + " Created Through SDG: " + sdgName); }
						 * 
						 * i++; }
						 */

					} else {
						log(LogStatus.FAIL,
								"-------Record has not been created of SDG: " + sdgNameCoInvestments + "--------",
								YesNo.Yes);
						sa.assertTrue(false,
								"-------Record has not been created of SDG: " + sdgNameCoInvestments + "--------");
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPageInvestorRelations, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPageInvestorRelations);
				}
			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName" })

@Test
public void PEFSTGTcS014_VerifySortingOnAffiliationsSDG(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

	String tabInPage = "Affiliations";
	String pageSize = "100";
	String TitleOfSDG = "Roles";

	String[] fieldsInSDG = "FIRM<BREAK>START DATE<BREAK>END DATE".split("<BREAK>");
	String[] datefieldsInSDG = "START DATE<BREAK>END DATE".split("<BREAK>");
	String[] amountfieldsInSDG = "".split("<BREAK>");

	String columnName = "";
	String dateColumn = "START DATE";
	String AscendingOrDescending = "Descending";

	List<String> columnInSDG = Arrays.asList(fieldsInSDG);
	List<String> dateColumnInSDG = Arrays.asList(datefieldsInSDG);
	List<String> amountColumnInSDG = Arrays.asList(amountfieldsInSDG);
	lp.CRMLogin(crmUser1EmailID, adminPassword);

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contact = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);
				if (click(driver, home.getTabInPage(tabInPage, 40), "Tab Name: " + tabInPage,
						action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Tab: " + tabInPage, YesNo.No);

					WebElement alreadyAddedComponentToHomePage = FindElement(driver,
							"//a[text()='" + TitleOfSDG + "']", "Component Title ", action.SCROLLANDBOOLEAN, 10);

					if (alreadyAddedComponentToHomePage != null) {
						log(LogStatus.INFO, "------------SDG Already Added to Contact's Page Tab: " + tabInPage
								+ "----------------", YesNo.Yes);

						if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {

							if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

								log(LogStatus.PASS,
										"-----------Page Size has selected to" + pageSize + " --------------",
										YesNo.No);

								if (home.verifySDGColumnDefaultAscendingOrDescending(TitleOfSDG, columnName,
										dateColumn, AscendingOrDescending)) {
									log(LogStatus.PASS,
											"-----------Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
								} else {

									log(LogStatus.FAIL,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
									sa.assertTrue(false,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------");

								}

								home.verifyColumnAscendingDescendingOrder(TitleOfSDG, columnInSDG, dateColumnInSDG,
										amountColumnInSDG, "No");

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
						log(LogStatus.ERROR, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------", YesNo.Yes);
						sa.assertTrue(false, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------");

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPage, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPage);
				}
			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	sa.assertAll();
	ThreadSleep(5000);
	lp.CRMlogout();

}

@Parameters({ "projectName" })

@Test
public void PEFSTGTcS015_VerifySortingOnReferralsSDGAtDealsTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

	String tabInPage = "Deals";
	String pageSize = "100";
	String TitleOfSDG = "Referrals";

	String columnName = "NAME";
	String dateColumn = "";
	String AscendingOrDescending = "Ascending";

	String[] fieldsInSDG = "NAME<BREAK>STATUS<BREAK>INTRODUCTION DATE".split("<BREAK>");
	String[] datefieldsInSDG = "INTRODUCTION DATE".split("<BREAK>");
	String[] amountfieldsInSDG = "".split("<BREAK>");

	List<String> columnInSDG = Arrays.asList(fieldsInSDG);
	List<String> dateColumnInSDG = Arrays.asList(datefieldsInSDG);
	List<String> amountColumnInSDG = Arrays.asList(amountfieldsInSDG);
	lp.CRMLogin(crmUser1EmailID, adminPassword);

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contact = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);
				if (click(driver, home.getTabInPage(tabInPage, 40), "Tab Name: " + tabInPage,
						action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Tab: " + tabInPage, YesNo.No);

					WebElement alreadyAddedComponentToHomePage = FindElement(driver,
							"//a[text()='" + TitleOfSDG + "']", "Component Title ", action.SCROLLANDBOOLEAN, 10);

					if (alreadyAddedComponentToHomePage != null) {
						log(LogStatus.INFO, "------------SDG Already Added to Contact's Page Tab: " + tabInPage
								+ "----------------", YesNo.Yes);

						if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {

							if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

								log(LogStatus.PASS,
										"-----------Page Size has selected to" + pageSize + " --------------",
										YesNo.No);

								if (home.verifySDGColumnDefaultAscendingOrDescending(TitleOfSDG, columnName,
										dateColumn, AscendingOrDescending)) {
									log(LogStatus.PASS,
											"-----------Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
								} else {

									log(LogStatus.FAIL,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
									sa.assertTrue(false,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------");

								}

								home.verifyColumnAscendingDescendingOrder(TitleOfSDG, columnInSDG, dateColumnInSDG,
										amountColumnInSDG, "No");

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
						log(LogStatus.ERROR, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------", YesNo.Yes);
						sa.assertTrue(false, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------");

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPage, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPage);
				}
			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName" })

@Test
public void PEFSTGTcS016_VerifySortingOnSourcedDealsSDGAtDealsTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

	String tabInPage = "Deals";
	String pageSize = "100";
	String TitleOfSDG = "Sourced Deals";

	String columnName = "";
	String dateColumn = "DATE";
	String AscendingOrDescending = "Descending";

	String[] fieldsInSDG = "DEAL<BREAK>SOURCE FIRM<BREAK>HIGHEST STAGE REACHED<BREAK>DATE".split("<BREAK>");
	String[] datefieldsInSDG = "DATE".split("<BREAK>");
	String[] amountfieldsInSDG = "".split("<BREAK>");
	List<String> columnInSDG = Arrays.asList(fieldsInSDG);
	List<String> dateColumnInSDG = Arrays.asList(datefieldsInSDG);
	List<String> amountColumnInSDG = Arrays.asList(amountfieldsInSDG);
	lp.CRMLogin(crmUser1EmailID, adminPassword);

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contact = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);
				if (click(driver, home.getTabInPage(tabInPage, 40), "Tab Name: " + tabInPage,
						action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Tab: " + tabInPage, YesNo.No);

					WebElement alreadyAddedComponentToHomePage = FindElement(driver,
							"//a[text()='" + TitleOfSDG + "']", "Component Title ", action.SCROLLANDBOOLEAN, 10);

					if (alreadyAddedComponentToHomePage != null) {
						log(LogStatus.INFO, "------------SDG Already Added to Contact's Page Tab: " + tabInPage
								+ "----------------", YesNo.Yes);

						if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {

							if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

								log(LogStatus.PASS,
										"-----------Page Size has selected to" + pageSize + " --------------",
										YesNo.No);

								if (home.verifySDGColumnDefaultAscendingOrDescending(TitleOfSDG, columnName,
										dateColumn, AscendingOrDescending)) {
									log(LogStatus.PASS,
											"-----------Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
								} else {

									log(LogStatus.FAIL,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
									sa.assertTrue(false,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------");

								}
								home.verifyColumnAscendingDescendingOrder(TitleOfSDG, columnInSDG, dateColumnInSDG,
										amountColumnInSDG, "No");

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
						log(LogStatus.ERROR, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------", YesNo.Yes);
						sa.assertTrue(false, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------");

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPage, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPage);
				}
			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

// Doubt related Default Sort

@Parameters({ "projectName" })

@Test
public void PEFSTGTcS017_VerifySortingOnDealTeamSDGAtDealsTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

	String tabInPage = "Deals";
	String pageSize = "100";
	String TitleOfSDG = "Deal Team";

	String columnName = "";
	String dateColumn = "START DATE";
	String AscendingOrDescending = "Descending";

	String[] fieldsInSDG = "DEAL<BREAK>COMPANY<BREAK>DEAL STAGE<BREAK>ROLE".split("<BREAK>");
	String[] datefieldsInSDG = "".split("<BREAK>");
	String[] amountfieldsInSDG = "".split("<BREAK>");
	List<String> columnInSDG = Arrays.asList(fieldsInSDG);
	List<String> dateColumnInSDG = Arrays.asList(datefieldsInSDG);
	List<String> amountColumnInSDG = Arrays.asList(amountfieldsInSDG);
	lp.CRMLogin(crmUser1EmailID, adminPassword);

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contact = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);
				if (click(driver, home.getTabInPage(tabInPage, 40), "Tab Name: " + tabInPage,
						action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Tab: " + tabInPage, YesNo.No);

					WebElement alreadyAddedComponentToHomePage = FindElement(driver,
							"//a[text()='" + TitleOfSDG + "']", "Component Title ", action.SCROLLANDBOOLEAN, 10);

					if (alreadyAddedComponentToHomePage != null) {
						log(LogStatus.INFO, "------------SDG Already Added to Contact's Page Tab: " + tabInPage
								+ "----------------", YesNo.Yes);

						if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {

							if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

								log(LogStatus.PASS,
										"-----------Page Size has selected to" + pageSize + " --------------",
										YesNo.No);

								if (home.verifySDGColumnDefaultAscendingOrDescending(TitleOfSDG, columnName,
										dateColumn, AscendingOrDescending)) {
									log(LogStatus.PASS,
											"-----------Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
								} else {

									log(LogStatus.FAIL,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
									sa.assertTrue(false,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------");

								}
								home.verifyColumnAscendingDescendingOrder(TitleOfSDG, columnInSDG, dateColumnInSDG,
										amountColumnInSDG, "No");

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
						log(LogStatus.ERROR, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------", YesNo.Yes);
						sa.assertTrue(false, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------");

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPage, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPage);
				}
			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName" })

@Test
public void PEFSTGTcS018_VerifySortingOnFundraisingSDGAtInvestorRelationsTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

	String tabInPage = "Investor Relations";
	String pageSize = "100";
	String TitleOfSDG = "Fundraising";

	String columnName = "FUNDRAISING";
	String dateColumn = "";
	String AscendingOrDescending = "Ascending";

	String[] fieldsInSDG = "FUNDRAISING<BREAK>FUND<BREAK>ROLE<BREAK>CLOSING<BREAK>STAGE<BREAK>CLOSE DATE<BREAK>POTENTIAL INVESTMENT (M)"
			.split("<BREAK>");
	String[] datefieldsInSDG = "CLOSE DATE".split("<BREAK>");
	String[] amountfieldsInSDG = "POTENTIAL INVESTMENT (M)".split("<BREAK>");
	List<String> columnInSDG = Arrays.asList(fieldsInSDG);
	List<String> dateColumnInSDG = Arrays.asList(datefieldsInSDG);
	List<String> amountColumnInSDG = Arrays.asList(amountfieldsInSDG);
	lp.CRMLogin(crmUser1EmailID, adminPassword);

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contact = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);
				if (click(driver, home.getTabInPage(tabInPage, 40), "Tab Name: " + tabInPage,
						action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Tab: " + tabInPage, YesNo.No);

					WebElement alreadyAddedComponentToHomePage = FindElement(driver,
							"//a[text()='" + TitleOfSDG + "']", "Component Title ", action.SCROLLANDBOOLEAN, 10);

					if (alreadyAddedComponentToHomePage != null) {
						log(LogStatus.INFO, "------------SDG Already Added to Contact's Page Tab: " + tabInPage
								+ "----------------", YesNo.Yes);

						if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {

							if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

								log(LogStatus.PASS,
										"-----------Page Size has selected to" + pageSize + " --------------",
										YesNo.No);

								if (home.verifySDGColumnDefaultAscendingOrDescending(TitleOfSDG, columnName,
										dateColumn, AscendingOrDescending)) {
									log(LogStatus.PASS,
											"-----------Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
								} else {

									log(LogStatus.FAIL,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
									sa.assertTrue(false,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------");

								}
								home.verifyColumnAscendingDescendingOrder(TitleOfSDG, columnInSDG, dateColumnInSDG,
										amountColumnInSDG, "Yes");

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
						log(LogStatus.ERROR, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------", YesNo.Yes);
						sa.assertTrue(false, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------");

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPage, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPage);
				}
			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName" })

@Test
public void PEFSTGTcS019_VerifySortingOnCoInvestmentsSDGAtInvestorRelationsTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

	String tabInPage = "Investor Relations";
	String pageSize = "100";
	String TitleOfSDG = "Co-Investments";

	String columnName = "FUNDRAISING";
	String dateColumn = "";
	String AscendingOrDescending = "Ascending";

	String[] fieldsInSDG = "FUNDRAISING<BREAK>COMPANY<BREAK>ROLE<BREAK>STAGE<BREAK>CLOSE DATE<BREAK>INVESTMENT (M)"
			.split("<BREAK>");
	String[] datefieldsInSDG = "CLOSE DATE".split("<BREAK>");
	String[] amountfieldsInSDG = "".split("<BREAK>");
	List<String> columnInSDG = Arrays.asList(fieldsInSDG);
	List<String> dateColumnInSDG = Arrays.asList(datefieldsInSDG);
	List<String> amountColumnInSDG = Arrays.asList(amountfieldsInSDG);
	lp.CRMLogin(crmUser1EmailID, adminPassword);

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contact = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);
				if (click(driver, home.getTabInPage(tabInPage, 40), "Tab Name: " + tabInPage,
						action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Tab: " + tabInPage, YesNo.No);

					WebElement alreadyAddedComponentToHomePage = FindElement(driver,
							"//a[text()='" + TitleOfSDG + "']", "Component Title ", action.SCROLLANDBOOLEAN, 10);

					if (alreadyAddedComponentToHomePage != null) {
						log(LogStatus.INFO, "------------SDG Already Added to Contact's Page Tab: " + tabInPage
								+ "----------------", YesNo.Yes);

						if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {

							if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

								log(LogStatus.PASS,
										"-----------Page Size has selected to" + pageSize + " --------------",
										YesNo.No);

								if (home.verifySDGColumnDefaultAscendingOrDescending(TitleOfSDG, columnName,
										dateColumn, AscendingOrDescending)) {
									log(LogStatus.PASS,
											"-----------Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
								} else {

									log(LogStatus.FAIL,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
									sa.assertTrue(false,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------");

								}
								home.verifyColumnAscendingDescendingOrder(TitleOfSDG, columnInSDG, dateColumnInSDG,
										amountColumnInSDG, "No");

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
						log(LogStatus.ERROR, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------", YesNo.Yes);
						sa.assertTrue(false, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------");

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPage, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPage);
				}
			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName" })

@Test
public void PEFSTGTcS020_VerifySortingOnCorrespondenceListSDGAtInvestorRelationsTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

	String tabInPage = "Investor Relations";
	String pageSize = "100";
	String TitleOfSDG = "Correspondence List";

	String columnName = "FUND";
	String dateColumn = "";
	String AscendingOrDescending = "Ascending";

	String[] fieldsInSDG = "FUND<BREAK>INVESTOR<BREAK>TYPE<BREAK>COMMITMENT AMOUNT<BREAK>ADVISORY COMMITEE PARTICIPATION"
			.split("<BREAK>");
	String[] datefieldsInSDG = "".split("<BREAK>");
	String[] amountfieldsInSDG = "COMMITMENT AMOUNT".split("<BREAK>");
	List<String> columnInSDG = Arrays.asList(fieldsInSDG);
	List<String> dateColumnInSDG = Arrays.asList(datefieldsInSDG);
	List<String> amountColumnInSDG = Arrays.asList(amountfieldsInSDG);
	lp.CRMLogin(crmUser1EmailID, adminPassword);

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, PEFSTGContacts_1_FName, PEFSTGContacts_1_LName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + PEFSTGContacts_1_LName,
					YesNo.No);

			WebElement contact = cp.getContactFullNameEle(PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
					30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.No);
				if (click(driver, home.getTabInPage(tabInPage, 40), "Tab Name: " + tabInPage,
						action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Tab: " + tabInPage, YesNo.No);

					WebElement alreadyAddedComponentToHomePage = FindElement(driver,
							"//a[text()='" + TitleOfSDG + "']", "Component Title ", action.SCROLLANDBOOLEAN, 10);

					if (alreadyAddedComponentToHomePage != null) {
						log(LogStatus.INFO, "------------SDG Already Added to Contact's Page Tab: " + tabInPage
								+ "----------------", YesNo.Yes);
						if (home.sdgGridExpandedByDefaultIfNotThenExpand(TitleOfSDG)) {

							if (home.pageSizeSelect(TitleOfSDG, pageSize)) {

								log(LogStatus.PASS,
										"-----------Page Size has selected to" + pageSize + " --------------",
										YesNo.No);

								if (home.verifySDGColumnDefaultAscendingOrDescending(TitleOfSDG, columnName,
										dateColumn, AscendingOrDescending)) {
									log(LogStatus.PASS,
											"-----------Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
								} else {

									log(LogStatus.FAIL,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------",
											YesNo.No);
									sa.assertTrue(false,
											"-----------Not Verified Default Sorting for SDG: " + TitleOfSDG
													+ " in Order: " + AscendingOrDescending + " for Column: "
													+ columnName + dateColumn + "--------------");

								}
								home.verifyColumnAscendingDescendingOrder(TitleOfSDG, columnInSDG, dateColumnInSDG,
										amountColumnInSDG, "No");

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
						log(LogStatus.ERROR, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------", YesNo.Yes);
						sa.assertTrue(false, "-----------Component Not Added to Contact Page's SDG: " + TitleOfSDG
								+ " under tab: " + tabInPage + "------------");

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabInPage, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + tabInPage);
				}
			} else

			{
				sa.assertTrue(false,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName);
				log(LogStatus.FAIL,
						"Contact Page not Open: " + PEFSTGContacts_1_FName + " " + PEFSTGContacts_1_LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName);
			log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + PEFSTGContacts_1_FName + " "
					+ PEFSTGContacts_1_LName, YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })

@Test
public void PEFSTGTcS021_VerifyStandardActivityTimelineFunctionalityOnContactDetailPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

	String[] activityTimelineTabs = "New Task<Section>New Event<Section>Log a Call<Section>Email"
			.split("<Section>");

	String[] recordPageLabelValuesAndTypesOfElements = "Subject<Br>Contact Task<Br>SearchDropDownTextBox<Break>Related To<Br>Firms<Split>Birla Group<Br>DoubleDropDown<Break>Due Date<Br>08/Jun/2022<Br>DatePickerCurrentDate<Break>Status<Br>Not Started<Br>DropDown<Section>Subject<Br>Contact Event<Br>SearchDropDownTextBox<Break>Related To<Br>Firms<Split>Birla Group<Br>DoubleDropDown<Break>Start<Br>08/Jun/2022<Split>9:00 PM<Br>DateTimePickerCurrentDate<Break>End<Br>08/Jun/2022<Split>10:00 PM<Br>DateTimePickerCurrentDate<Section>Subject<Br>Contact Call<Br>SearchDropDownTextBox<Break>Related To<Br>Firms<Split>Birla Group<Br>DoubleDropDown<Section>Subject<Br>Contact Email<Br>TextBox<Break>Related To<Br>Firms<Split>Birla Group<Br>DoubleDropDown"
			.split("<Section>");

	String recordAndActivityTitle = "Contact Task<Br>UpcomingAndOverdue<Break>Contact Event<Br>UpcomingAndOverdue<Break>Contact Call<Br>ThisMonth<Break>Contact Email<Br>ThisMonth";

	String newContactFirstName = "RamPal";
	String newContactLastName = "Yadav New";

	/*
	 * String[][] labelValueAndTypes = { { PageLabel.Subject.toString(), task,
	 * "SearchDropDownTextBox" }, { PageLabel.Related_To.toString(),
	 * "Firms<Split>Birla Group", "DoubleDropDown" }, {
	 * PageLabel.Due_Date.toString(), SmokeCTTask1dueDate, "DatePicker" }, {
	 * PageLabel.Status.toString(), "Not Started", "DropDown" } };
	 * 
	 * String[][] labelValueAndTypesEvents = { { PageLabel.Subject.toString(),
	 * "Contact Event", "SearchDropDownTextBox" }, {
	 * PageLabel.Related_To.toString(), "Firms<Split>Birla Group", "DoubleDropDown"
	 * }, { "Start", SmokeCTTask1dueDate + "<Split>" + "9:00 AM", "DateTimePicker"
	 * }, { "End", SmokeCTTask1dueDate + "<Split>" + "10:00 PM", "DateTimePicker" }
	 * };
	 */

	lp.CRMLogin(crmUser1EmailID, adminPassword);

	CommonLib.ThreadSleep(4000);
	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (cp.createContact(projectName, newContactFirstName, newContactLastName,
				PEFSTGEntities_1_InstitutionsName, PEFSTGContacts_1_EmailID, "", null, null,
				CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO, "Successfully Created Contact : " + newContactFirstName + " " + newContactLastName,
					YesNo.No);
		}

		else {
			sa.assertTrue(false, "Not Able to Create Contact : " + newContactFirstName + " " + newContactLastName);
			log(LogStatus.SKIP, "Not Able to Create Contact : " + newContactFirstName + " " + newContactLastName,
					YesNo.Yes);
		}

	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
	}

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, newContactFirstName, newContactLastName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + newContactFirstName + " " + newContactLastName,
					YesNo.No);

			WebElement contact = cp.getContactFullNameEle(newContactFirstName + " " + newContactLastName, 30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + newContactFirstName + " " + newContactLastName,
						YesNo.No);

				String[][] requiredRecordPageLabelValuesAndTypesOfElements = null;
				int loopCount = 0;
				for (String recordPageLabelValuesAndTypesOfElement : recordPageLabelValuesAndTypesOfElements) {
					requiredRecordPageLabelValuesAndTypesOfElements = new String[recordPageLabelValuesAndTypesOfElement
							.split("<Break>").length][3];
					int i = 0;
					for (String recordPageLabelValuesAndTypesOfEle : recordPageLabelValuesAndTypesOfElement
							.split("<Break>")) {
						for (int j = 0; j < 3; j++) {
							requiredRecordPageLabelValuesAndTypesOfElements[i][j] = recordPageLabelValuesAndTypesOfEle
									.split("<Br>")[j];
						}
						i++;
					}

					if (cp.enterValuesForActivityTimeline(activityTimelineTabs[loopCount],
							requiredRecordPageLabelValuesAndTypesOfElements)) {

						log(LogStatus.INFO, "------Data has been Created Successfully for Activity Timeline: "
								+ activityTimelineTabs[loopCount] + "------", YesNo.No);
						CommonLib.ThreadSleep(5000);

					} else

					{
						sa.assertTrue(false, "------Data has not been Created Successfully for Activity Timeline: "
								+ activityTimelineTabs[loopCount] + "-------");
						log(LogStatus.FAIL, "------Data has not been Created Successfully for Activity Timeline: "
								+ activityTimelineTabs[loopCount] + "------", YesNo.Yes);
					}

					loopCount++;
				}

				log(LogStatus.INFO, "------Now, Going to verify Activity Timeline Data------", YesNo.No);

				if (cp.verifyActivityRecord(recordAndActivityTitle)) {
					log(LogStatus.INFO, "------Activity Timeline Data has been verified------", YesNo.No);
				} else {

					sa.assertTrue(false, "------Activity Timeline Data has not been verified------");
					log(LogStatus.FAIL, "------Activity Timeline Data has not been verified------", YesNo.Yes);
				}

			} else

			{
				sa.assertTrue(false, "Contact Page not Open: " + newContactFirstName + " " + newContactLastName);
				log(LogStatus.FAIL, "Contact Page not Open: " + newContactFirstName + " " + newContactLastName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,
					"Not Able to Click on Created Contact : " + newContactLastName + " " + PEFSTGContacts_1_LName);
			log(LogStatus.SKIP,
					"Not Able to Click on Created Contact : " + newContactLastName + " " + PEFSTGContacts_1_LName,
					YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName" })

@Test
public void PEFSTGTcS022_VerifyContactTransferFunctionalityOnContactRecordPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	NavatarSetupPageBusinessLayer np = new NavatarSetupPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

	String[] institutionName = "Tata Group".split("<Break>");
	String[] recordType = "Advisor".split("<Break>");
	String[] labelsOfFirmPopUp = "".split("<Break>");
	String[] valuesOfFirmPopUp = "".split("<Break>");

	String recordPageButtonName = "Contact Transfer";
	String parentWindowId;
	String newContactFirstName = "RamPal";
	String newContactLastName = "Yadav New";
	String confirmButtonText = "Retain Address";

	List<String> labelsList = new ArrayList<String>();
	List<String> valuesList = new ArrayList<String>();

	ArrayList<String> ExpectedContactInformationTab = new ArrayList<String>();
	ExpectedContactInformationTab.add("Legal Name");

	HashMap<String, ArrayList<String>> labelListMap = new HashMap<String, ArrayList<String>>();
	labelListMap.put("Contact Information", ExpectedContactInformationTab);
	labelsList.add("Legal Name");
	valuesList.add("Tata Group");

	String affiliationTabInPage = "Affiliations";
	String rolesSDG = "Roles";
	String columnNameBasedOnWhichRecordSearchInSDGRoles = "FIRM";
	String[] columnEqualValue = ("FIRM = Birla Group<Break>ROLE = Former Employee <Break> START DATE =  <Break> END DATE = ")
			.split("<Break>");

	String recordAndActivityTitle = "Contact Task<Br>UpcomingAndOverdue<Break>Contact Event<Br>UpcomingAndOverdue<Break>Contact Call<Br>ThisMonth<Break>Contact Email<Br>ThisMonth";

	String detailPageHeader = "Affiliation";

	String[] listViewAndRecordName1 = "All Advisors<Break>Tata Group".split("<Break>");
	String[] listViewAndRecordName2 = "All Institutions<Break>Birla Group".split("<Break>");

	lp.CRMLogin(superAdminUserName, adminPassword);

	if (ip.clickOnTab(projectName, TabName.NavatarSetup)) {
		if (np.clickOnNavatarSetupSideMenusTab(projectName, NavatarSetupSideMenuTab.ContactTransfer)) {
			log(LogStatus.INFO, "Clicked on Contact Transfer Tab", YesNo.No);
			ThreadSleep(2000);

			if (isSelected(driver,
					np.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,
							NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.View,
							ClickOrCheckEnableDisableCheckBox.Click, 20),
					"Enabled CheckBox")) {

				log(LogStatus.INFO, "Contact Transfer is already Enabled", YesNo.No);
				ThreadSleep(5000);
			} else {
				log(LogStatus.ERROR, "Contact Transfer Checkbox is not Enabled", YesNo.Yes);
				sa.assertTrue(false, "Contact Transfer Checkbox is not Enabled");
			}

		} else {
			sa.assertTrue(false, "contact transfer side menu tab is not clickable");
			log(LogStatus.SKIP, "contact transfer side menu tab is not clickable", YesNo.Yes);
		}
	} else {
		sa.assertTrue(false, "navatar setup tab is not clickable");
		log(LogStatus.SKIP, "navatar setup tab is not clickable", YesNo.Yes);
	}

	switchToDefaultContent(driver);
	lp.CRMlogout();
	ThreadSleep(5000);

	lp.CRMLogin(crmUser1EmailID, adminPassword);

	int firmLoopCount = 0;
	for (String instName : institutionName) {
		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (ip.createInstitution(projectName, environment, mode, instName, recordType[firmLoopCount],
					labelsOfFirmPopUp[firmLoopCount].trim(), valuesOfFirmPopUp[firmLoopCount].trim()))

			{
				log(LogStatus.INFO, "successfully Created Account/Entity : " + instName + " of record type : "
						+ recordType[firmLoopCount], YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Account/Entity : " + instName + " of record type : "
						+ recordType[firmLoopCount]);
				log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + instName + " of record type : "
						+ recordType[firmLoopCount], YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj1);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj1, YesNo.Yes);
		}
		ThreadSleep(2000);
		firmLoopCount++;
	}

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, newContactFirstName, newContactLastName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + projectName + " " + newContactLastName, YesNo.No);

			WebElement contact = cp.getContactFullNameEle(newContactFirstName + " " + newContactLastName, 30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + newContactFirstName + " " + newContactLastName,
						YesNo.No);

				if (cp.clickOnRecordPageButtonForNewRecordCreation(recordPageButtonName, 8)) {
					log(LogStatus.INFO, "Clicked on Button: " + recordPageButtonName + " on Record Page", YesNo.No);

					if (CommonLib.switchToFrame(driver, 30, cp.transferContactIFrame(30))) {

						log(LogStatus.INFO, "Successfully Switched to Frame", YesNo.No);

						if (clickUsingJavaScript(driver, cp.transferContactLegalNameSearchBtn(30),
								"transferContactLegalNameSearchBtn", action.SCROLLANDBOOLEAN)) {

							log(LogStatus.INFO, "Clicked on Legal Name Search Button", YesNo.No);
							CommonLib.switchToDefaultContent(driver);
							parentWindowId = CommonLib.switchOnWindow(driver);
							if (CommonLib.switchToFrame(driver, 30, cp.getLookUpResultFrame(30))) {
								log(LogStatus.INFO, "Successfully Switched to Frame", YesNo.No);
								if (cp.transferContactLegalNameInNewWindowAndFrame(institutionName[0],
										30) != null) {
									log(LogStatus.INFO, "Successfully Found Legal Name: " + institutionName[0],
											YesNo.No);

									CommonLib.ThreadSleep(5000);
									if (clickUsingJavaScript(driver,
											cp.transferContactLegalNameInNewWindowAndFrame(institutionName[0], 30),
											"transferContactLegalNameInNewWindowAndFrame",
											action.SCROLLANDBOOLEAN)) {

										log(LogStatus.INFO, "Clicked on Legal Name Link: " + institutionName[0],
												YesNo.No);

										CommonLib.ThreadSleep(5000);

										if (parentWindowId != null) {
											driver.switchTo().window(parentWindowId);
											System.out.println(driver.getTitle());
										}
										if (CommonLib.switchToFrame(driver, 30, cp.transferContactIFrame(30))) {

											log(LogStatus.INFO, "Successfully Switched to Frame", YesNo.No);

											if (click(driver, cp.getTransferButton(projectName, 30),
													"getTransferButton", action.SCROLLANDBOOLEAN)) {

												log(LogStatus.INFO, "Clicked on Transfer Button", YesNo.No);

												if (click(driver,
														cp.contactTransferConfirmButton(confirmButtonText, 30),
														"getTransferButton", action.SCROLLANDBOOLEAN)) {

													log(LogStatus.INFO,
															"Clicked on Confirm Button " + confirmButtonText,
															YesNo.No);

													CommonLib.ThreadSleep(5000);
													CommonLib.switchToDefaultContent(driver);
													List<String> detailPageLabelValuesText = home
															.detailPageLabelValues("Contact Information").stream()
															.map(x -> x.getText().replace("Open", "")
																	.replace("Preview", "").trim())
															.collect(Collectors.toList());
													int i = 0;
													for (String label : labelsList) {

														if (detailPageLabelValuesText.contains(label)) {
															log(LogStatus.INFO, "Label Verified : " + label,
																	YesNo.No);
															int index = detailPageLabelValuesText.indexOf(label);
															if (detailPageLabelValuesText.get(index + 1)
																	.contains(valuesList.get(i))) {
																log(LogStatus.INFO,
																		"Value Verified : " + valuesList.get(i)
																				+ " corresponding to label: "
																				+ label,
																		YesNo.No);

															} else {
																log(LogStatus.ERROR,
																		"Value Not Verified : " + valuesList.get(i)
																				+ " corresponding to label: "
																				+ label
																				+ " So, Affiliation not created",
																		YesNo.Yes);
																sa.assertTrue(false,
																		"Value Not Verified : " + valuesList.get(i)
																				+ " corresponding to label: "
																				+ label
																				+ " So, Affiliation not created");
															}

														} else {
															log(LogStatus.ERROR, "Label not Verified : " + label,
																	YesNo.Yes);
															sa.assertTrue(false, "Label not Verified : " + label);
														}

														i++;
													}
												} else {
													log(LogStatus.ERROR, "Not Able to Click on Confirm Button "
															+ confirmButtonText, YesNo.Yes);
													sa.assertTrue(false, "Not Able to Click on Confirm Button "
															+ confirmButtonText);
												}

											} else {
												log(LogStatus.ERROR, "Not Able to Click on Transfer Button",
														YesNo.Yes);
												sa.assertTrue(false, "Not Able to Click on Transfer Button");
											}

										} else {
											log(LogStatus.ERROR, "Not Able to Switch to IFrame", YesNo.Yes);
											sa.assertTrue(false, "Not Able to Switch to IFrame");

										}
									} else {
										log(LogStatus.ERROR,
												"Not Able to Click on Legal Name Link: " + institutionName[0],
												YesNo.Yes);
										sa.assertTrue(false,
												"Not Able to Click on Legal Name Link: " + institutionName[0]);
									}

								}

								else {

									log(LogStatus.ERROR,
											"Legal Name: " + institutionName[0] + " is not found in New Window",
											YesNo.Yes);
									sa.assertTrue(false,
											"Legal Name: " + institutionName[0] + " is not found in New Window");
								}
							}

						} else {
							log(LogStatus.ERROR, "Not Able to Click on Legal Name Search Button", YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Legal Name Search Button");
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Switch to IFrame", YesNo.Yes);
						sa.assertTrue(false, "Not Able to Switch to IFrame");

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Button: " + recordPageButtonName + " on Record Page",
							YesNo.Yes);

				}

			} else

			{
				sa.assertTrue(false, "Contact Page not Open: " + newContactFirstName + " " + newContactLastName);
				log(LogStatus.FAIL, "Contact Page not Open: " + newContactFirstName + " " + newContactLastName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,
					"Not Able to Click on Created Contact : " + newContactFirstName + " " + newContactLastName);
			log(LogStatus.SKIP,
					"Not Able to Click on Created Contact : " + newContactFirstName + " " + newContactLastName,
					YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	if (bp.clickOnTab(environment, mode, TabName.ContactTab)) {
		if (cp.clickOnCreatedContact(projectName, newContactFirstName, newContactLastName)) {
			log(LogStatus.INFO, "Click on Created Contact : " + newContactFirstName + " " + newContactLastName,
					YesNo.No);

			WebElement contact = cp.getContactFullNameEle(newContactFirstName + " " + newContactLastName, 30);

			if (contact != null) {

				log(LogStatus.INFO,
						"Contact Page Open Succesfully: " + newContactFirstName + " " + newContactLastName,
						YesNo.No);

				if (click(driver, home.getTabInPage(affiliationTabInPage, 40), "Tab Name: " + affiliationTabInPage,
						action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Clicked on Tab: " + affiliationTabInPage, YesNo.No);

					cp.verifySDGRecord(rolesSDG, columnEqualValue, columnNameBasedOnWhichRecordSearchInSDGRoles);

					log(LogStatus.INFO, "------Now, Going to verify Activity Timeline Data------", YesNo.No);

					if (cp.verifyActivityRecord(recordAndActivityTitle)) {
						log(LogStatus.INFO, "------Activity Timeline Data has been verified------", YesNo.No);
					} else {

						sa.assertTrue(false, "------Activity Timeline Data has not been verified------");
						log(LogStatus.FAIL, "------Activity Timeline Data has not been verified------", YesNo.Yes);
					}

					log(LogStatus.INFO, "-------Now, Going to verify Detail Button Redirection---------", YesNo.No);

					if (click(driver, cp.buttonCorrespondToRecordInSDG(rolesSDG, "Details", 40),
							"Button Name: Details of SDG: " + rolesSDG, action.SCROLLANDBOOLEAN)) {

						log(LogStatus.INFO, "Clicked on Details Button of SDG: " + rolesSDG, YesNo.No);

						CommonLib.ThreadSleep(7000);
						CommonLib.refresh(driver);
						if (cp.detailPageHeader(detailPageHeader, 30) != null) {
							log(LogStatus.INFO, "----Detail Page Open Successfully, Header Verified for "
									+ detailPageHeader + "----", YesNo.No);
						} else {
							log(LogStatus.ERROR, "----Detail Page not Open Successfully, Header not Verified for "
									+ detailPageHeader + "----", YesNo.Yes);
							sa.assertTrue(false, "----Detail Page not Open Successfully, Header not Verified for "
									+ detailPageHeader + "----");
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Details Button of SDG: " + rolesSDG, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on Details Button of SDG: " + rolesSDG);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab: " + affiliationTabInPage, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + affiliationTabInPage);
				}

			} else

			{
				sa.assertTrue(false, "Contact Page not Open: " + newContactFirstName + " " + newContactLastName);
				log(LogStatus.FAIL, "Contact Page not Open: " + newContactFirstName + " " + newContactLastName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,
					"Not Able to Click on Created Contact : " + newContactFirstName + " " + newContactLastName);
			log(LogStatus.SKIP,
					"Not Able to Click on Created Contact : " + newContactFirstName + " " + newContactLastName,
					YesNo.Yes);

		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Contact Tab");
		log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
	}

	if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {

		log(LogStatus.INFO, "Click on Tab: " + TabName.InstituitonsTab, YesNo.No);

		if (bp.clickOnAlreadyCreatedListView(projectName, listViewAndRecordName1[1], listViewAndRecordName1[0],
				30)) {

			log(LogStatus.INFO, "Record has been Selected: " + listViewAndRecordName1[1], YesNo.No);

			log(LogStatus.INFO, "------Now, Going to verify Activity Timeline Data------", YesNo.No);

			if (cp.verifyActivityRecord(recordAndActivityTitle)) {
				log(LogStatus.INFO, "------Activity Timeline Data has been verified------", YesNo.No);
			} else {

				sa.assertTrue(false, "------Activity Timeline Data has not been verified------");
				log(LogStatus.FAIL, "------Activity Timeline Data has not been verified------", YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Record has not been Selected: " + listViewAndRecordName1[1]);
			log(LogStatus.SKIP, "Record has not been Selected: " + listViewAndRecordName1[1], YesNo.Yes);
		}

	} else {
		sa.assertTrue(false, "Not Able to Click on Tab: " + TabName.InstituitonsTab);
		log(LogStatus.SKIP, "Not Able to Click on Tab: " + TabName.InstituitonsTab, YesNo.Yes);
	}

	if (bp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {

		log(LogStatus.INFO, "Click on Tab: " + TabName.InstituitonsTab, YesNo.No);

		if (bp.clickOnAlreadyCreatedListView(projectName, listViewAndRecordName2[1], listViewAndRecordName2[0],
				30)) {

			log(LogStatus.INFO, "Record has been Selected: " + listViewAndRecordName2[1], YesNo.No);

			log(LogStatus.INFO, "------Now, Going to verify Activity Timeline Data------", YesNo.No);

			if (cp.verifyActivityRecord(recordAndActivityTitle)) {
				log(LogStatus.INFO, "------Activity Timeline Data has been verified------", YesNo.No);
			} else {

				sa.assertTrue(false, "------Activity Timeline Data has not been verified------");
				log(LogStatus.FAIL, "------Activity Timeline Data has not been verified------", YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Record has not been Selected: " + listViewAndRecordName2[1]);
			log(LogStatus.SKIP, "Record has not been Selected: " + listViewAndRecordName2[1], YesNo.Yes);
		}

	} else {
		sa.assertTrue(false, "Not Able to Click on Tab: " + TabName.InstituitonsTab);
		log(LogStatus.SKIP, "Not Able to Click on Tab: " + TabName.InstituitonsTab, YesNo.Yes);
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}
}

