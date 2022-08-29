package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.EnumConstants.*;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.FieldAndRelationshipPageBusinessLayer;
import com.navatar.pageObjects.FirmPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
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

}

