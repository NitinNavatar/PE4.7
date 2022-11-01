package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.SwitchToParentFrame;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.EnumConstants.AppSetting;
import com.navatar.generic.EnumConstants.BulkActions_DefaultValues;
import com.navatar.generic.EnumConstants.CreateNew_DefaultValues;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.NewInteractions_DefaultValues;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.SDGPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class AcuityResearch extends BaseLib{
	
	String passwordResetLink = null;
	String navigationMenuName=NavigationMenuItems.Research.toString();
	String filesName = "Enter one or more research terms";
//	public  String NavigationMenuTestData_PEExcel = System.getProperty("user.dir")+"\\UploadFiles\\Module 3\\UploadCSV\\Navigation Menu Test Data - Parent - Child - All.csv";
//	public  String NavigationMenuTestData_PESheet = "asd";
//	String navigationTab="Navigation";
//	String dashBoardTab="Dashboards";
//	String recordTypeDescription = "Description Record Type";
//	String upDated="Updated";
//	String customNavigationMenu = "Custom Navigation Menu";

	@Parameters({ "projectName" })

	@Test
	public void ARTc001_CreateUsers(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(glUser1LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		boolean flag = false;
		for (int i = 0; i < 3; i++) {
			try {
				if (home.clickOnSetUpLink()) {
					flag = true;
					parentWindow = switchOnWindow(driver);
					if (parentWindow == null) {
						sa.assertTrue(false,
								"No new window is open after click on setup link in lighting mode so cannot create gl User1");
						log(LogStatus.SKIP,
								"No new window is open after click on setup link in lighting mode so cannot create gl User1",
								YesNo.Yes);
						exit("No new window is open after click on setup link in lighting mode so cannot create gl User1");
					}
					if (setup.createPEUser(glUser1FirstName, UserLastName, emailId, glUserLience, glUserProfile)) {
						log(LogStatus.INFO,
								"GLSearch User is created Successfully: " + glUser1FirstName + " " + UserLastName,
								YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User01",
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
				if (setup.installedPackages(glUser1FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in GLSearch User: " + glUser1FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in GLSearch User1: " + glUser1FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in GLSearch User1: " + glUser1FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in GLSearch User1: " + glUser1FirstName + " " + UserLastName,
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
			appLog.info("Password is set successfully for GLSearch User1: " + glUser1FirstName + " " + UserLastName);
		} else {
			appLog.info("Password is not set for GLSearch User1: " + glUser1FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for GLSearch User1: " + glUser1FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for GLSearch User1: " + glUser1FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void ARTc002_VerifyTheNavigationMenuItems(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String xpath;
		String ele;

		// Verification on navigation menu
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
//			xpath = "//div[contains(@class,'DOCKED')]//div//input";
//			ele = FindElement(driver, xpath,filesName, action.BOOLEAN, 10).getAttribute("placeholder");
//			if (ele!=null) {
			if(clickUsingJavaScript(driver, bp.getTextAreaResearch(20),"Research Text Area", action.BOOLEAN)) {
				log(LogStatus.INFO, "items verified "+filesName+" on "+navigationMenuName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "items not verified "+filesName+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"items not verified "+filesName+" on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName);
		}
		refresh(driver);
		
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			xpath = "(//div[contains(@class,'DOCKED')]//div//button)[3]";
			ele = FindElement(driver, xpath,filesName, action.BOOLEAN, 10).getText();
			if (ele!=null) {
//			if(clickUsingJavaScript(driver, bp.getResearchButton(20),"Research Button", action.BOOLEAN)) {
				log(LogStatus.INFO, "items verified "+filesName+" on "+navigationMenuName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "items not verified "+filesName+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"items not verified "+filesName+" on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName);
		}
		refresh(driver);
		
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
//			xpath = "(//div[contains(@class,'DOCKED')]//div//button)[1]";
//			WebElement ele1 = FindElement(driver, xpath,filesName, action.BOOLEAN, 10);
//			click(driver,ele1,"Minimize",action.BOOLEAN);
//			if (ele1!=null) {
			if(clickUsingJavaScript(driver, bp.getResearchMinimize(20),"Research Minimize Button", action.BOOLEAN)) {
				log(LogStatus.INFO, "Research popup successfully minimized", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Research popup not successfully minimized", YesNo.Yes);
				sa.assertTrue(false,"Research popup not successfully minimized");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName);
		}
		refresh(driver);
		
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			xpath = "(//div[contains(@class,'DOCKED')]//div//button)[2]";
			WebElement ele1 = FindElement(driver, xpath,filesName, action.BOOLEAN, 10);
			click(driver,ele1,"Pop-out",action.BOOLEAN);
			ThreadSleep(8000);
			xpath = "//div[contains(@class,'normal')]//button[@title='Pop-in']";
			WebElement ele2 = FindElement(driver, xpath,filesName, action.BOOLEAN, 10);
			click(driver,ele2,"Pop-in",action.BOOLEAN);
			ThreadSleep(4000);
			switchToDefaultContent(driver);
			if (ele2==null) {
				log(LogStatus.INFO, "Research popup successfully pop-out closed", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Research popup not successfully closed", YesNo.Yes);
				sa.assertTrue(false,"Research popup not successfully closed");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName);
		}
		lp.CRMlogout();
		
		ThreadSleep(2000);
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ThreadSleep(5000);
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
			ThreadSleep(5000);
			if(setup.searchStandardOrCustomObject(environment,mode, object.App_Manager)) {
				log(LogStatus.INFO, "click on Object : "+object.App_Manager, YesNo.No);
				ThreadSleep(2000);
				if(setup.clickOnEditForApp(driver, appName, AppDeveloperName,AppDescription, 60)) {
					log(LogStatus.INFO,"able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription, YesNo.No);
					ThreadSleep(1000);
					if (setup.clickOnAppSettingList(driver, AppSetting.Utility_Items, 30)) {
						log(LogStatus.INFO, "able to click on "+AppSetting.Utility_Items, YesNo.No);
						ThreadSleep(2000);
						if (click(driver, setup.getResearchItem(30), "Research Item", action.BOOLEAN)) {
							log(LogStatus.INFO, "Able to click on Research Item", YesNo.No);
							ThreadSleep(2000);
								if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Panel_Height.toString(), action.BOOLEAN, 30),"340",PageLabel.Panel_Height.toString()+" textbox value : 340",action.BOOLEAN)) {
									ThreadSleep(2000);
									log(LogStatus.INFO,"send value to "+PageLabel.Label.toString()+" textbox value : 340",YesNo.No);
									if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Panel_Width.toString(), action.BOOLEAN, 30),"300",PageLabel.Panel_Width.toString()+" textbox value : 300",action.BOOLEAN)) {
										ThreadSleep(2000);
										log(LogStatus.INFO,"send value to "+PageLabel.Navigation_Type.toString()+" textbox value : 300",YesNo.No);
										
										if (click(driver, setup.getCustomTabSaveBtn(projectName, 30)," Save Button", action.BOOLEAN)) {
											log(LogStatus.INFO,"Click on Save Button",YesNo.No);
											ThreadSleep(2000);			
										} else {
											sa.assertTrue(false, "Not Able to Click on Save Button");
											log(LogStatus.FAIL,"Not Able to Click on Save Button",YesNo.Yes);
										}
									} else {
										sa.assertTrue(false, "Not Able to send value to "+PageLabel.Panel_Width.toString()+" textbox value : 300");
										log(LogStatus.FAIL,"Not Able to send value to "+PageLabel.Panel_Width.toString()+" textbox value : 300",YesNo.Yes);
									}
								} else {
									sa.assertTrue(false, "Not Able to send value to "+PageLabel.Panel_Height.toString()+" textbox value : 340");
									log(LogStatus.FAIL,"Not Able to send value to "+PageLabel.Panel_Height.toString()+" textbox value : 340",YesNo.Yes);
								}

						} else {
							log(LogStatus.ERROR, "Not able to click on Research Item", YesNo.Yes);
							sa.assertTrue(false,"Not able to click on Research Item");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on "+AppSetting.Utility_Items, YesNo.Yes);
						sa.assertTrue(false,"Not able to click on "+AppSetting.Utility_Items);
					}
				}else {
					log(LogStatus.ERROR,"Not able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription);
				}

			}else {
				log(LogStatus.ERROR, "Not able to search/click on Apps manager", YesNo.Yes);
				sa.assertTrue(false, "Not able to search/click on Apps manager");
			}

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}


@Parameters({ "projectName"})
@Test
public void ARTc003_VerifyTheResearchFunctionality(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	String errorName = "  Your search term must have 2 or more characters.";
	String xpath;
	String ele;
	
	String[][] val = {{MRSD_1_ResearchFindings},{MRSD_2_ResearchFindings},{MRSD_3_ResearchFindings},{MRSD_4_ResearchFindings},{MRSD_5_ResearchFindings},{MRSD_6_ResearchFindings},{MRSD_7_ResearchFindings},{MRSD_8_ResearchFindings},{MRSD_9_ResearchFindings}};
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(5000);
	
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(clickUsingJavaScript(driver, bp.getResearchButton(10),"Research Button", action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Research Button", YesNo.No);
			ThreadSleep(2000);
			clickUsingJavaScript(driver, bp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			xpath = "//h2[contains(@class,'vertical__title')]";
			ele = FindElement(driver, xpath,"Research Findings", action.BOOLEAN, 10).getText();
			if (ele!=null && ele.equalsIgnoreCase("Research Findings")) {
			log(LogStatus.PASS, ele +" is visible", YesNo.Yes);
			sa.assertTrue(true, ele +" is visible");
			}
			log(LogStatus.PASS, val[1][0] +" is geting from excel", YesNo.Yes);
			log(LogStatus.PASS, val[2][0] +" is geting from excel", YesNo.Yes);
			ArrayList<String> Data = bp.verifyDataonResearchPage(projectName, mode, val);
			if (Data.isEmpty()) {
				log(LogStatus.PASS, "Data has been Matched", YesNo.No);
				sa.assertTrue(true, "Data has been Matched");
			} else {
				log(LogStatus.ERROR, "Data is not Matched", YesNo.Yes);
				sa.assertTrue(false, "Data is not Matched : " + Data);
			}
			
			xpath = "(//div[contains(@class,'left_small')]//span)[2]";
			ele = FindElement(driver, xpath, errorName, action.BOOLEAN, 10).getText();
			if(ele.equalsIgnoreCase(errorName)){
				log(LogStatus.PASS, ele +" has been Matched with " +errorName, YesNo.No);
				sa.assertTrue(true, ele +" has been Matched with " +errorName);
			} else {
				log(LogStatus.ERROR, ele +" is not Matched with " +errorName, YesNo.Yes);
				sa.assertTrue(false, ele +" is not Matched with " +errorName);
			}
	} else {
		log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName, YesNo.Yes);
		sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName);
	}
	
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}
}
}