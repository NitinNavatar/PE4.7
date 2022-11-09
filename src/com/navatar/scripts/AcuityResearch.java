package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.SwitchToParentFrame;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.EnumConstants.AppSetting;
import com.navatar.generic.EnumConstants.BulkActions_DefaultValues;
import com.navatar.generic.EnumConstants.Condition;
import com.navatar.generic.EnumConstants.CreateNew_DefaultValues;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.NewInteractions_DefaultValues;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.Stage;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.recordTypeLabel;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.FieldAndRelationshipPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
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
	String customNavigationMenu = "Custom Navigation Menu";

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
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	String errorName = "Your search term must have 2 or more characters.";
	String errorName1 = "No results for";
	String xpath,ele;
	int i = 1;
	String searchValues[] = {"","a","zz","~!@#$%^&*()_+=-[]{}|;':,.<>/?"};
	String[][] val = {{MRSD_1_ResearchFindings},{MRSD_2_ResearchFindings},{MRSD_3_ResearchFindings},{MRSD_4_ResearchFindings},{MRSD_5_ResearchFindings},{MRSD_6_ResearchFindings},{MRSD_7_ResearchFindings},{MRSD_8_ResearchFindings},{MRSD_9_ResearchFindings}};
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	
	for(String searchValue : searchValues) {
		log(LogStatus.PASS, "WOrking for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		xpath = "//div[contains(@class,'DOCKED')]//div//input";
		if(sendKeysAndPressEnter(driver, bp.getTextAreaResearch(10),searchValue, xpath, action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, bp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			xpath = "//div[contains(@class,'normal')]//span[contains(@class,'italic')]";
			ele = FindElement(driver, xpath,searchValue, action.BOOLEAN, 10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
			xpath = "//h2[contains(@class,'vertical__title')]";
			ele = FindElement(driver, xpath,"Research Findings", action.BOOLEAN, 10).getText();
			if (ele!=null && ele.equalsIgnoreCase("Research Findings")) {
			log(LogStatus.PASS, ele +" is visible", YesNo.Yes);
			sa.assertTrue(true, ele +" is visible");
			}
			ArrayList<String> Data = bp.verifyFieldonResearchPage(projectName, mode, val);
			if (Data.isEmpty()) {
				log(LogStatus.PASS, "Data has been Matched", YesNo.No);
				sa.assertTrue(true, "Data has been Matched");
			} else {
				log(LogStatus.ERROR, "Data is not Matched", YesNo.Yes);
				sa.assertTrue(false, "Data is not Matched : " + Data);
			}
			
			if(searchValue.length() < 2) {
				xpath = "(//div[contains(@class,'left_small')]//span)[2]";
				ele = FindElement(driver, xpath, errorName, action.BOOLEAN, 10).getText();
				if(ele.equalsIgnoreCase("  "+ errorName)){
					log(LogStatus.PASS, ele +" has been Matched with " +errorName, YesNo.No);
					sa.assertTrue(true, ele +" has been Matched with " +errorName);
				} else {
					log(LogStatus.ERROR, ele +" is not Matched with " +errorName, YesNo.Yes);
					sa.assertTrue(false, ele +" is not Matched with " +errorName);
				}
				ThreadSleep(2000);
				xpath = "(//lightning-icon[contains(@class,'utility-warning')])["+i+"]";
				WebElement element = FindElement(driver, xpath, errorName, action.BOOLEAN, 10);
				if(mouseOverGetTextOperation(driver, element).contains(errorName)){
					log(LogStatus.PASS, ele +" on mouse hover has been Matched with " +errorName, YesNo.No);
					sa.assertTrue(true, ele +" on mouse hover has been Matched with " +errorName);
				}else {
					log(LogStatus.ERROR, ele +" on mouse hover is not Matched with " +errorName, YesNo.Yes);
					sa.assertTrue(false, ele +" on mouse hover is not Matched with " +errorName);
			}
			} else {
				String xpath1 = "//div[contains(@class,'noResultsTitle')]";
				ele = FindElement(driver, xpath1, errorName1, action.BOOLEAN, 10).getText();
				if(ele.contains(errorName1)){
					log(LogStatus.PASS, ele +" has been Matched with " +errorName1, YesNo.No);
					sa.assertTrue(true, ele +" has been Matched with " +errorName1);
				} else {
					log(LogStatus.ERROR, ele +" is not Matched with " +errorName1, YesNo.Yes);
					sa.assertTrue(false, ele +" is not Matched with " +errorName1);
				}
			}
	} else {
		log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
		sa.assertTrue(false,"Not Able to send value "+searchValue);
	}
}
	refresh(driver);
	i++;
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}
	
@Parameters({ "projectName"})
@Test
	public void ARTc004_CreateCustomFields(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String contactRecordTypeList = AR_ContactRecordType1;
	String dealRecordTypeList = AR_DealRecordType1;
	String fundRecordTypeList = AR_FundRecordType1;
	String fundraisingRecordTypeList = AR_FundraisingRecordType1;
	String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP,-1);
	String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);
	String fundRecordTypeArray[] = fundRecordTypeList.split(breakSP, -1);
	String fundraisingRecordTypeArray[] = fundraisingRecordTypeList.split(breakSP, -1);
	String recordTypeDescription = "Description Record Type";

	String[][][] contactrecordType = {
			{ { recordTypeLabel.Record_Type_Label.toString(), contactRecordTypeArray[0] },
					{ recordTypeLabel.Description.toString(), contactRecordTypeArray[0] + recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), contactRecordTypeArray[1] },
					{ recordTypeLabel.Description.toString(), contactRecordTypeArray[1] + recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } } };
	
	String[][][] dealRecordType = {
			{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[0] },
					{ recordTypeLabel.Description.toString(), dealRecordTypeArray[0] + recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[1] },
					{ recordTypeLabel.Description.toString(), dealRecordTypeArray[1] + recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } } };

	String[][][] fundrecordType = {
			{ { recordTypeLabel.Record_Type_Label.toString(), fundRecordTypeArray[0] },
					{ recordTypeLabel.Description.toString(), fundRecordTypeArray[0] + recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), fundRecordTypeArray[1] },
					{ recordTypeLabel.Description.toString(), fundRecordTypeArray[1] + recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } } };

	String[][][] fundraisingrecordType = {
			{ { recordTypeLabel.Record_Type_Label.toString(), fundraisingRecordTypeArray[0] },
					{ recordTypeLabel.Description.toString(),
							fundraisingRecordTypeArray[0] + recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), fundraisingRecordTypeArray[1] },
					{ recordTypeLabel.Description.toString(),
							fundraisingRecordTypeArray[1] + recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } } };

	
	String[] profileForSelection = { "PE Standard User", "System Administrator" };
	boolean isMakeAvailable = true;
	boolean isMakeDefault = true;
	boolean flag = false;
	String parentID=null;
	
	object[] objectsName = {object.Contact,object.Account,object.Fund,object.Fundraising,object.Deal} ;
	String [][] fieldsType = {{"Email","Custom Firm Email",""},{"Phone","Custom Firm Phone",""},{"Text","Custom Firm Text","255"},{"Text Area","Custom Firm TA",""},{"Text Area (Long)","Custom Firm LTA","32768"},{"Text Area (Rich)","Custom Firm RTA","32768"}};
	String a="",name = "" ,length = "", field = "";
	
	if (home.clickOnSetUpLink()) {
		parentID=switchOnWindow(driver);
		if (parentID!=null) {
			for(object objectName : objectsName){
				for(String[] fieldType : fieldsType) {
					field=fieldType[0];
					name=fieldType[1];
					length=fieldType[2];
					String[][] labelAndValues= {{"Length",length}};
			if (sp.addCustomFieldforFormula(environment,mode, objectName, ObjectFeatureName.FieldAndRelationShip, field, name, labelAndValues, null, null)) {
				flag=true;
				log(LogStatus.INFO, "successfully created new custom field", YesNo.No);
				if (sendKeys(driver, sp.getQuickSearchInObjectManager_Lighting(10),name+Keys.ENTER, "search", action.SCROLLANDBOOLEAN)) {
					a=sp.returnAPINameOfField(projectName, name);
						log(LogStatus.PASS, "found api name of "+name, YesNo.Yes);
						sa.assertTrue(true, "found api name of "+name);
				}else {
					log(LogStatus.FAIL, "could not find api name of "+name, YesNo.Yes);
					sa.assertTrue(false, "could not find api name of "+name);
				
				}
			}
			else {
				log(LogStatus.FAIL, "could not create new field", YesNo.Yes);
				sa.assertTrue(false, "could not create new field");
			
				}
			   }
			}
			if (flag) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("Contact Layout");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put(name,excelLabel.Title.toString());
			List<String> abc = sp.DragNDrop("", mode, object.Contact, ObjectFeatureName.pageLayouts, layoutName, sourceANDDestination);
			ThreadSleep(10000);
			if (!abc.isEmpty()) {
				log(LogStatus.FAIL, "field not added/already present 1", YesNo.Yes);
				sa.assertTrue(false, "field not added/already present 1");
			}else{
				log(LogStatus.INFO, "field added/already present 1", YesNo.Yes);
			}
			}else {
				log(LogStatus.FAIL, "new field could not be created, so no need to add in page layout", YesNo.Yes);
				sa.assertTrue(false, "new field could not be created, so no need to add in page layout");

			}
			driver.close();
			driver.switchTo().window(parentID);
		}else {
			log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
			sa.assertTrue(false, "could not find new window to switch");

		}
	}
	else {
		log(LogStatus.FAIL, "could not click on setup link", YesNo.Yes);
		sa.assertTrue(false, "could not click on setup link");
	
	}
	
	for (int i = 0; i < contactRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Contact)) {
					if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Contact,
							ObjectFeatureName.recordTypes)) {
						if (i == 0) {
							if (sp.listOfRecordTypes().contains(contactrecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + contactrecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, contactrecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							}
						} else {
							isMakeDefault = false;

							if (sp.listOfRecordTypes().contains(contactrecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + contactrecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, contactrecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							}
						}
						if (flag) {
							log(LogStatus.INFO, "Created Record Type : " + contactRecordTypeArray[i], YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not Able to Create Record Type : " + contactRecordTypeArray[i],
									YesNo.Yes);
							sa.assertTrue(false, "Not Able to Create Record Type : " + contactRecordTypeArray[i]);
						}

					} else {
						log(LogStatus.ERROR,
								"object feature " + ObjectFeatureName.recordTypes + " is not clickable", YesNo.Yes);
						sa.assertTrue(false,
								"object feature " + ObjectFeatureName.recordTypes + " is not clickable");
					}
				} else {
					log(LogStatus.ERROR, object.Contact + " object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, object.Contact + " object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
				refresh(driver);
			} else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		} else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
	}
		
	for (int i = 0; i < dealRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Deal)) {
					if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Deal,
							ObjectFeatureName.recordTypes)) {
						if (i == 0) {
							if (sp.listOfRecordTypes().contains(dealRecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + dealRecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, dealRecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							}
						} else {
							isMakeDefault = false;

							if (sp.listOfRecordTypes().contains(dealRecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + dealRecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, dealRecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							}
						}
						if (flag) {
							log(LogStatus.INFO, "Created Record Type : " + dealRecordTypeArray[i], YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not Able to Create Record Type : " + dealRecordTypeArray[i],
									YesNo.Yes);
							sa.assertTrue(false, "Not Able to Create Record Type : " + dealRecordTypeArray[i]);
						}

					} else {
						log(LogStatus.ERROR,
								"object feature " + ObjectFeatureName.recordTypes + " is not clickable", YesNo.Yes);
						sa.assertTrue(false,
								"object feature " + ObjectFeatureName.recordTypes + " is not clickable");
					}
				} else {
					log(LogStatus.ERROR, object.Deal + " object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, object.Deal + " object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
				refresh(driver);
			} else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		} else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

	}

	for (int i = 0; i < fundRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Fund)) {
					if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Fund,
							ObjectFeatureName.recordTypes)) {
						if (i == 0) {
							if (sp.listOfRecordTypes().contains(fundrecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + fundrecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, fundrecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							}
						} else {
							isMakeDefault = false;

							if (sp.listOfRecordTypes().contains(fundrecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + fundrecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, fundrecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							}
						}
						if (flag) {
							log(LogStatus.INFO, "Created Record Type : " + fundRecordTypeArray[i], YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not Able to Create Record Type : " + fundRecordTypeArray[i],
									YesNo.Yes);
							sa.assertTrue(false, "Not Able to Create Record Type : " + fundRecordTypeArray[i]);
						}

					} else {
						log(LogStatus.ERROR,
								"object feature " + ObjectFeatureName.recordTypes + " is not clickable", YesNo.Yes);
						sa.assertTrue(false,
								"object feature " + ObjectFeatureName.recordTypes + " is not clickable");
					}
				} else {
					log(LogStatus.ERROR, object.Fund + " object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, object.Fund + " object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
				refresh(driver);
			} else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		} else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

	}

	for (int i = 0; i < fundraisingRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Fundraising)) {
					if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Fundraising,
							ObjectFeatureName.recordTypes)) {
						if (i == 0) {

							if (sp.listOfRecordTypes().contains(fundraisingrecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + fundraisingrecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, fundraisingrecordType[i],
										isMakeAvailable, profileForSelection, isMakeDefault, null, 10);
							}
						} else {
							isMakeDefault = false;
							if (sp.listOfRecordTypes().contains(fundraisingrecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + fundraisingrecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, fundraisingrecordType[i],
										isMakeAvailable, profileForSelection, isMakeDefault, null, 10);
							}
						}
						if (flag) {
							log(LogStatus.INFO, "Created Record Type : " + fundraisingRecordTypeArray[i], YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Not Able to Create Record Type : " + fundraisingRecordTypeArray[i], YesNo.Yes);
							sa.assertTrue(false,
									"Not Able to Create Record Type : " + fundraisingRecordTypeArray[i]);
						}

					} else {
						log(LogStatus.ERROR,
								"object feature " + ObjectFeatureName.recordTypes + " is not clickable", YesNo.Yes);
						sa.assertTrue(false,
								"object feature " + ObjectFeatureName.recordTypes + " is not clickable");
					}
				} else {
					log(LogStatus.ERROR, object.Fundraising + " object could not be found in object manager",
							YesNo.Yes);
					sa.assertTrue(false, object.Fundraising + " object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
				refresh(driver);
			} else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		} else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

	}
	switchToDefaultContent(driver);
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
	public void ARTc005_VerifyTheResearchFunctionality(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	String xpath,ele;
	String searchValues[] = {"Sumo"};
	String[][] val = {{MRSD_11_ResearchFindings,MRSD_11_Count},{MRSD_12_ResearchFindings,MRSD_12_Count},{MRSD_13_ResearchFindings,MRSD_13_Count},{MRSD_14_ResearchFindings,MRSD_14_Count},{MRSD_15_ResearchFindings,MRSD_15_Count},
			{MRSD_16_ResearchFindings,MRSD_16_Count},{MRSD_17_ResearchFindings,MRSD_17_Count},{MRSD_18_ResearchFindings,MRSD_18_Count},{MRSD_19_ResearchFindings,MRSD_19_Count},{MRSD_20_ResearchFindings,MRSD_20_Count},
			{MRSD_21_ResearchFindings,MRSD_21_Count},{MRSD_22_ResearchFindings,MRSD_22_Count},{MRSD_23_ResearchFindings,MRSD_23_Count},{MRSD_24_ResearchFindings,MRSD_24_Count},{MRSD_25_ResearchFindings,MRSD_25_Count}};
	
	for(String searchValue : searchValues) {
		log(LogStatus.PASS, "WOrking for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		xpath = "//div[contains(@class,'DOCKED')]//div//input";
		if(sendKeysAndPressEnter(driver, bp.getTextAreaResearch(10),searchValue, xpath, action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, bp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			xpath = "//div[contains(@class,'normal')]//span[contains(@class,'italic')]";
			ele = FindElement(driver, xpath,searchValue, action.BOOLEAN, 10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
			xpath = "//h2[contains(@class,'vertical__title')]";
			ele = FindElement(driver, xpath,"Research Findings", action.BOOLEAN, 10).getText();
			if (ele!=null && ele.equalsIgnoreCase("Research Findings")) {
			log(LogStatus.PASS, ele +" is visible", YesNo.Yes);
			sa.assertTrue(true, ele +" is visible");
			}
			ArrayList<String> Data = bp.verifyFieldonResearchPage(projectName, mode, val);
			if (Data.isEmpty()) {
				log(LogStatus.PASS, "Data has been Matched", YesNo.No);
				sa.assertTrue(true, "Data has been Matched");
				
			} else {
				log(LogStatus.ERROR, "Data is not Matched", YesNo.Yes);
				sa.assertTrue(false, "Data is not Matched : " + Data);
			}
			
	} else {
		log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
		sa.assertTrue(false,"Not Able to send value "+searchValue);
	}
}
	refresh(driver);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
	public void ARTc006_VerifyResearchFuncationalityforValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	
	String xpath,value = "";
	String searchValues[] = {ACR_1_Search};
	String findings[] = {ACR_1_All,ACR_1_Firms,ACR_1_Advisor,ACR_1_Company,ACR_1_Institution,ACR_1_Intermediary,ACR_1_Lender,ACR_1_LP,ACR_1_PC,ACR_1_Contacts,ACR_1_deals,ACR_1_Fund,ACR_1_Fundraising,ACR_1_Interactionss,ACR_1_RA,ACR_1_RC};
	
	
	for(String searchValue : searchValues) {
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
		CommonLib.refresh(driver);
		home.notificationPopUpClose();
		if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
			log(LogStatus.INFO,
					"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValue + "---------",
					YesNo.No);
		} else {
			log(LogStatus.FAIL,
					"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValue + "---------",
					YesNo.No);
			sa.assertTrue(false,
					"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValue + "---------");
		
				
//			xpath = "/span";
//			List<String> findingsCount = bp.researchFindingsCountForAllResults()
//					.stream().map(x -> x.getText().trim().replace("New Items", "").replace(":", "")
//							.replaceAll("[\\t\\n\\r]+", "").trim())
//					.collect(Collectors.toList());
//
//			List<String> researchResultsGridCounts = bp.researchResultsGridCounts().stream()
//					.map(x -> x.getText().trim()).collect(Collectors.toList());
//			
//			int size = findings.length;
//			
//			int [] find = new int [size];
//					 
//			for(int i=1; i<=size;i++)
//			{
//				find[i] = Integer.parseInt(findings[i]);
//				if(find[i] != 0 && findings[i] != null)
//				{
//					String ExcelCounts = findings[i];
//					log(LogStatus.PASS, "Excel counts are: "+ExcelCounts, YesNo.No);
//				}
//			}
	}
}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
	
}

	}