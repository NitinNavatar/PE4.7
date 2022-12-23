package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.ExcelUtils.readAllDataForAColumn;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.crmUser3FirstName;
import static com.navatar.generic.SmokeCommonVariables.crmUser3LastName;
import static com.navatar.generic.SmokeCommonVariables.crmUser3Lience;
import static com.navatar.generic.SmokeCommonVariables.crmUser3Profile;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.record.RefreshAllRecord;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.SwitchToParentFrame;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.CommonVariables;
import com.navatar.generic.EmailLib;
import com.navatar.generic.EnumConstants.AppSetting;
import com.navatar.generic.EnumConstants.BulkActions_DefaultValues;
import com.navatar.generic.EnumConstants.Condition;
import com.navatar.generic.EnumConstants.CreateNew_DefaultValues;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.DataImportType;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.NewInteractions_DefaultValues;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.ObjectName;
import com.navatar.generic.EnumConstants.ObjectType;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PermissionType;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.RelatedTab;
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
import com.navatar.pageObjects.DataLoaderWizardPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.FieldAndRelationshipPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.MarketingInitiativesPageBusinesslayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.ResearchPageBusinessLayer;
import com.navatar.pageObjects.SDGPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class AcuityResearch extends BaseLib{
	
	String passwordResetLink = null;
	String navigationMenuName=NavigationMenuItems.Navatar_Research.toString().replace("_", " ");
	String filesName = "Enter one or more research terms";
	String customNavigationMenu = "Custom Navigation Menu";
	String recordTypeDescription = "Description Record Type";
	String valueInAccountField,valueInContactField1,valueInContactField2,valueInDealField1,valueInDealField2,valueInFundField1,valueInFundField2,valueInFundraisingField1,valueInFundraisingField2;
	
	@Parameters({ "projectName"})
	@Test
	public void ARTc001_CreateUsers(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(glUser1LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",excelLabel.User_Last_Name);
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
					if (setup.createPEUser( glUser1FirstName, UserLastName, emailId, glUserLience,
							glUserProfile, null)) {
						log(LogStatus.INFO, "GL User is created Successfully: " + glUser1FirstName + " " + UserLastName, YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "Usergl",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "Userg1",
								excelLabel.User_Last_Name);
						flag = true;
						break;

					}
					driver.close();
					driver.switchTo().window(parentWindow);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {
			if(!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				if (setup.installedPackages(glUser1FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + glUser1FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in CRM User1: " + glUser1FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in CRM User1: " + glUser1FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in CRM User1: " + glUser1FirstName + " " + UserLastName,
							YesNo.Yes);
				}
			}
			

		}else{

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

		}

		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
		String passwordResetLink=null;
		try {
			passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
					ExcelUtils.readDataFromPropertyFile("gmailUserName"),
					ExcelUtils.readDataFromPropertyFile("gmailPassword"));
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		appLog.info("ResetLinkIs: " + passwordResetLink);
		driver.get(passwordResetLink);
		if (lp.setNewPassword()) {
			appLog.info("Password is set successfully for GL User1: " + glUser1FirstName + " " + UserLastName );
		} else {
			appLog.info("Password is not set for GL User1: " + glUser1FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for GL User1: " + glUser1FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for GL User1: " + glUser1FirstName + " " + UserLastName,
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
		ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
		lp.CRMLogin(glUser1EmailID, adminPassword);

		// Verification on navigation menu
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, rp.getTextAreaResearch(10),"Research Text Area", action.BOOLEAN)) {
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
//			xpath = "(//div[contains(@class,'DOCKED')]//div//button)[3]";
//			ele = FindElement(driver, xpath,filesName, action.BOOLEAN, 10).getText();
//			if (ele!=null) {
			if(clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN)) {
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
			if(clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimize Button", action.BOOLEAN)) {
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
			clickUsingJavaScript(driver, rp.getResearchPopOut(10),"Research Pop out Button", action.BOOLEAN);
			ThreadSleep(20000);
//			CommonLib.switchToWindowOpenNextToParentWindow(driver);
			clickUsingJavaScript(driver, rp.getResearchPopIn(10),"Research Pop In Button", action.BOOLEAN);
			ThreadSleep(4000);
//			CommonLib.switchToWindowOpenNextToParentWindow(driver);
			switchToDefaultContent(driver);
			if (rp.getResearchPopOut(10)!=null) {
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
		sa.assertAll();
		ThreadSleep(2000);
		refresh(driver);
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
				if(setup.clickOnEditForApp(driver, appName, AppDeveloperName,AppDescription, 10)) {
					log(LogStatus.INFO,"able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription, YesNo.No);
					ThreadSleep(1000);
					if (setup.clickOnAppSettingList(driver, AppSetting.Utility_Items, 10)) {
						log(LogStatus.INFO, "able to click on "+AppSetting.Utility_Items, YesNo.No);
						ThreadSleep(2000);
						if (click(driver, setup.getResearchItem(10), "Research Item", action.BOOLEAN)) {
							log(LogStatus.INFO, "Able to click on Research Item", YesNo.No);
							ThreadSleep(2000);
								if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Panel_Height.toString(), action.BOOLEAN, 10),"450",PageLabel.Panel_Height.toString()+" textbox value : 340",action.BOOLEAN)) {
									ThreadSleep(2000);
									log(LogStatus.INFO,"send value to "+PageLabel.Label.toString()+" textbox value : 340",YesNo.No);
									if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Panel_Width.toString(), action.BOOLEAN, 10),"240",PageLabel.Panel_Width.toString()+" textbox value : 300",action.BOOLEAN)) {
										ThreadSleep(2000);
										log(LogStatus.INFO,"send value to "+PageLabel.Navigation_Type.toString()+" textbox value : 300",YesNo.No);
										
										if (click(driver, setup.getCustomTabSaveBtn(projectName, 10)," Save Button", action.BOOLEAN)) {
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
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	String errorName = "Your search term must have 2 or more characters.";
	String errorName1 = "No results for";
	String xpath,ele;
	int i = 1;
	String searchValues[] = {"","a","zz","1234567890~!@#$%^&*()_+-=[]{}?|;':,.<>/"};
	String[][] val = {{MRSD_1_ResearchFindings},{MRSD_2_ResearchFindings},{MRSD_3_ResearchFindings},{MRSD_4_ResearchFindings},{MRSD_5_ResearchFindings},{MRSD_6_ResearchFindings},{MRSD_7_ResearchFindings},{MRSD_8_ResearchFindings},{MRSD_9_ResearchFindings}};
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	for(String searchValue : searchValues) {
		log(LogStatus.PASS, "WOrking for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10), "Research Button");
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(8000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue) || searchValue == null) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
			ele = rp.getResearchFindings(10).getText();
			if (ele!=null && ele.equalsIgnoreCase("Search Results")) {
			log(LogStatus.PASS, ele +" is visible", YesNo.Yes);
			sa.assertTrue(true, ele +" is visible");
			}
			
			if(searchValue.length() < 2) {
				ele =rp.getErrorValue(10).getText();
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
				ele = rp.getNoResult(10).getText();
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
	i++;
	refresh(driver);
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
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[2] },
						{ recordTypeLabel.Description.toString(), dealRecordTypeArray[2] + recordTypeDescription },
						{ recordTypeLabel.Active.toString(), "" } }};

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
	boolean isMakeAvailable = false;
	boolean isMakeDefault = false;
	boolean flag = false;
	String parentID=null;
	
	object[] objectsName = {object.Contact,object.Firm,object.Fund,object.Fundraising,object.Deal} ;
	//String [][] fieldsType = {{"Email","Custom" + objectName[i]+ "Email",""},{"Phone","Custom Firm Phone",""},{"Text","Custom Firm Text","255"},{"Text Area","Custom Firm TA",""},{"Text Area (Long)","Custom Firm LTA","32768"},{"Text Area (Rich)","Custom Firm RTA","32768"}};
	String a="",name = "" ,length = "", field = "";
	
	if (home.clickOnSetUpLink()) {
		parentID=switchOnWindow(driver);
		if (parentID!=null) {
			for(object objectName : objectsName){
				String Email = "Custom " + objectName+ " Email";
				String Phone = "Custom " + objectName+ " Phone";
				String Text = "Custom " + objectName+ " Text";
				String TA = "Custom " + objectName+ " TA";
				String LTA = "Custom " + objectName+ " LTA";
				String RTA = "Custom " + objectName+ " RTA";
				//System.out.println(" " + Email + " " +Phone + " " + Text + " " + TA + " " + LTA + " " + RTA);
				String [][] fieldsType = {{"Email",Email,""},{"Phone",Phone,""},{"Text",Text,"255"},{"Text Area",TA,""},{"Text Area (Long)",LTA,"32768"},{"Text Area (Rich)",RTA,"32768"}};
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
			switchToDefaultContent(driver);
			refresh(driver);
		}else {
			log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
			sa.assertTrue(false, "could not find new window to switch");

		}
	}
	else {
		log(LogStatus.FAIL, "could not click on setup link", YesNo.Yes);
		sa.assertTrue(false, "could not click on setup link");
	
	}
	switchToDefaultContent(driver);
	refresh(driver);
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
										null, isMakeDefault, null, 10);
							}
						} else {
							isMakeDefault = false;

							if (sp.listOfRecordTypes().contains(contactrecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + contactrecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, contactrecordType[i], isMakeAvailable,
										null, isMakeDefault, null, 10);
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
	switchToDefaultContent(driver);
	refresh(driver);
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
										null, isMakeDefault, null, 10);
							}
						} else {
							isMakeDefault = false;

							if (sp.listOfRecordTypes().contains(dealRecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + dealRecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, dealRecordType[i], isMakeAvailable,
										null, isMakeDefault, null, 10);
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
	switchToDefaultContent(driver);
	refresh(driver);
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
										null, isMakeDefault, null, 10);
							}
						} else {
							isMakeDefault = false;

							if (sp.listOfRecordTypes().contains(fundrecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + fundrecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, fundrecordType[i], isMakeAvailable,
										null, isMakeDefault, null, 10);
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
	switchToDefaultContent(driver);
	refresh(driver);
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
										isMakeAvailable, null, isMakeDefault, null, 10);
							}
						} else {
							isMakeDefault = false;
							if (sp.listOfRecordTypes().contains(fundraisingrecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + fundraisingrecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, fundraisingrecordType[i],
										isMakeAvailable, null, isMakeDefault, null, 10);
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
	public void ARTc005_UpdateRecordTypesAsInactive(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String dealRecordTypeList = AR_DealRecordType1;
	String fundRecordTypeList = AR_FundRecordType1;
	String fundraisingRecordTypeList = AR_FundraisingRecordType1;
	String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);
	String fundRecordTypeArray[] = fundRecordTypeList.split(breakSP, -1);
	String fundraisingRecordTypeArray[] = fundraisingRecordTypeList.split(breakSP, -1);
	
	String[][] RecordType = { { recordTypeLabel.Active.toString(), "Not Checked" }};


	boolean flag = false;
	String parentID=null;
	
	
	for (int i = 0; i < dealRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Deal)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Deal,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(dealRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.ERROR,dealRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,dealRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, dealRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, dealRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, dealRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Deal object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Deal object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Fund)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Fund,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(fundRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.ERROR,fundRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,fundRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, fundRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, fundRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, fundRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Fund object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Fund object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Fundraising)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Fundraising,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(fundraisingRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.ERROR,fundraisingRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,fundraisingRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, fundraisingRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, fundraisingRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, fundraisingRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Fundraising object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Fundraising object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

	}

	
	lp.switchToLighting();
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
	public void ARTc006_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	ArrayList<String> list=new ArrayList<>();
	
	String[] searchValues = readAllDataForAColumn(ResearchDataSheetFilePath, "SearchData" , 2,false).split("<break>");
	
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValue, excelLabel.Variable_Name);
		
		log(LogStatus.PASS, "WOrking for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 5)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(5),searchValue, "Input", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(5),"Research Minimize Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			System.out.println(ele);
			if (ele.contains(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
			ele = rp.getResearchFindings(10).getText();
			if (ele!=null && ele.equalsIgnoreCase("Research Findings")) {
			log(LogStatus.PASS, ele +" is visible", YesNo.Yes);
			sa.assertTrue(true, ele +" is visible");
			}
			
	} else {
		log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
		sa.assertTrue(false,"Not Able to send value "+searchValue);
	}
}
	log(LogStatus.INFO,
			"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
					+ searchValue + "---------",
			YesNo.No);
	try{
		refresh(driver);
	if(rp.getNoResult(5).getText() != null){
		log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
		sa.assertTrue(true, "There is no data retaled to " + searchValue);
	} else 
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
			
	}
		list=	rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 5);

		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
		}
		
	}
	catch(Exception e)
	{
		log(LogStatus.INFO,e.getMessage(), YesNo.No);
		continue;

	}
		if (rp.mouseHoverOnNavigationAndGetText()) {
			log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
		} else {
			log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
			sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
		}
		
		if (rp.mouseHoverOnGridAndGetText()) {
			log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
		} else {
			log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
			sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
		}
		int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{		
			headerName = rp.getElementsFromGrid().get(i).getText();
			String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
			
			if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
				log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
				sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
			}
			if (rp.VerifyViewMoreOption(headerName)) {
				log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				sa.assertTrue(false,"--------- Not able to click on view more option for" + headerName + " ---------");
			}
		}

	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
	
	}

@Parameters({ "projectName" })
@Test
	public void ARTc007_RenameAccountNameAndVerifyResearchData(String projectName) {
    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
    InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	
	 String updatedname = "Advisorfirm NSAdmin Record04 - Updated";
	 
		 lp.CRMLogin(glUser1EmailID, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
	
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm1, 10)) {
	           if (ip.UpdateLegalNameAccount(projectName, updatedname, 5)) {
	               log(LogStatus.INFO, "successfully update legal name " + updatedname, YesNo.Yes);
	               ExcelUtils.writeData(ResearchDataSheetFilePath, updatedname, "UpdatedData", excelLabel.Variable_Name,"AR_Up1",
	            		   excelLabel.ResearchFindings);
	           } else {
	               sa.assertTrue(false, "not able to update legal name " + updatedname);
	               log(LogStatus.SKIP, "not able to update legal name " + updatedname, YesNo.Yes);
	           }
	       } else {
	          sa.assertTrue(false, "Not Able to open created firm : " + AR_Firm1);
	           log(LogStatus.SKIP, "Not Able to open created firm: " + AR_Firm1, YesNo.Yes);
	      }
	   } else {
	       log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	       sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	   }
	   
	switchToDefaultContent(driver);
//	lp.CRMlogout();
//	sa.assertAll();
//	ThreadSleep(5000);
//	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	refresh(driver);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 5)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(5),'"' + updatedname + '"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				click(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(5),"Research Minimize Button", action.BOOLEAN);
				ThreadSleep(2000);
			}
	   }
	   
	   int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
			   System.out.println("Record Name : " + recordName);
			   
			   if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
		}  
		
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, updatedname, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 5);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data--------- " + list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
		}
	   
		
	}

@Parameters({ "projectName"})
@Test
	public void ARTc008_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	
	String[] searchValues = {AR_Firm2};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
	
	}

@Parameters({ "projectName" })
@Test
	public void ARTc009_RenameContactNameAndVerifyResearchData(String projectName) {
    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
    ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	
	 String updatedname = "Adm.rec05 - Updated";
	 
		 lp.CRMLogin(glUser1EmailID, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.ContactTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
	
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm2, 10)) {
	           if (cp.UpdateLastName(projectName, PageName.ContactPage,updatedname)) {
	               log(LogStatus.INFO, "successfully update contact name " + updatedname, YesNo.Yes);
	               ExcelUtils.writeData(ResearchDataSheetFilePath, updatedname, "UpdatedData", excelLabel.Variable_Name,"AR_Up2",
	            		   excelLabel.ResearchFindings);
	           } else {
	               sa.assertTrue(false, "not able to update contact name " + updatedname);
	               log(LogStatus.SKIP, "not able to update contact name " + updatedname, YesNo.Yes);
	           }
	       } else {
	          sa.assertTrue(false, "Not Able to open created contact : " + AR_Firm2);
	           log(LogStatus.SKIP, "Not Able to open created contact: " + AR_Firm2, YesNo.Yes);
	      }
	   } else {
	       log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
	       sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
	   }
	   
	switchToDefaultContent(driver);
//	lp.CRMlogout();
//	sa.assertAll();
//	ThreadSleep(5000);
//	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	refresh(driver);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),'"' + updatedname + '"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
			}
	   }
	    int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
			   System.out.println("Record Name : " + recordName);
			   
			   if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
		}  
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, updatedname, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
		}
	   
		lp.CRMlogout();
		sa.assertAll();
	}
	
@Parameters({ "projectName"})
@Test
	public void ARTc010_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	
	String[] searchValues = {AR_Firm3};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
		if(rp.getNoResult(5).getText() != null){
			log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
			sa.assertTrue(true, "There is no data retaled to " + searchValue);
		} else 
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName" })
@Test
	public void ARTc011_RenameDealNameAndVerifyResearchData(String projectName) {
    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
    DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	
	 String updatedname = "Deal NSAdmin Company Record05 - Updated";
	 String labellabels = "Deal Name";
	 
		 lp.CRMLogin(glUser1EmailID, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.DealTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.DealTab, YesNo.No);
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm3, 10)) {
	           if (dp.UpdateOtherLable(projectName, labellabels, updatedname, 10)) {
	               log(LogStatus.INFO, "successfully update contact name " + updatedname, YesNo.Yes);
	               ExcelUtils.writeData(ResearchDataSheetFilePath, updatedname, "UpdatedData", excelLabel.Variable_Name,"AR_Up3",
	            		   excelLabel.ResearchFindings);
	           } else {
	               sa.assertTrue(false, "not able to update deal name " + updatedname);
	               log(LogStatus.SKIP, "not able to update deal name " + updatedname, YesNo.Yes);
	           }
	       } else {
	          sa.assertTrue(false, "Not Able to open created Deal : " + AR_Firm3);
	           log(LogStatus.SKIP, "Not Able to open created Deal: " + AR_Firm3, YesNo.Yes);
	      }
	   } else {
	       log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
	       sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
	   }
	   
	switchToDefaultContent(driver);
//	lp.CRMlogout();
//	sa.assertAll();
//	ThreadSleep(5000);
//	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),'"' + updatedname + '"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
			}
	   }
	    int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
			   System.out.println("Record Name : " + recordName);
			   
			   if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
		}  
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, updatedname, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
		}  
	}

@Parameters({ "projectName"})
@Test
	public void ARTc012_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	
	String[] searchValues = {AR_Firm4};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName" })
@Test
	public void ARTc013_RenameFundNameAndVerifyResearchData(String projectName) {
    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	
	 String updatedname = "CompanyFund NSAdmin Record07 - Updated";
	 
		 lp.CRMLogin(superAdminUserName, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.FundsTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.FundsTab, YesNo.No);
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm4, 10)) {
	           if (fp.UpdateDealName(projectName, updatedname, 10)) {
	               log(LogStatus.INFO, "successfully update contact name " + updatedname, YesNo.Yes);
	               ExcelUtils.writeData(ResearchDataSheetFilePath, updatedname, "UpdatedData", excelLabel.Variable_Name,"AR_Up4",
	            		   excelLabel.ResearchFindings);
	           } else {
	               sa.assertTrue(false, "not able to update deal name " + updatedname);
	               log(LogStatus.SKIP, "not able to update deal name " + updatedname, YesNo.Yes);
	           }
	       } else {
	          sa.assertTrue(false, "Not Able to open created Deal : " + AR_Firm4);
	           log(LogStatus.SKIP, "Not Able to open created Deal: " + AR_Firm4, YesNo.Yes);
	      }
	   } else {
	       log(LogStatus.ERROR, "Not able to click on " + tabObj3 + " tab", YesNo.Yes);
	       sa.assertTrue(false, "Not able to click on " + tabObj3 + " tab");
	   }
	   
	switchToDefaultContent(driver);
//	lp.CRMlogout();
//	sa.assertAll();
//	ThreadSleep(5000);
//	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),'"' + updatedname +'"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
			}
	   }
	    int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
			   System.out.println("Record Name : " + recordName);
			   
			   if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
		}  
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, updatedname, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
		}  
	}

@Parameters({ "projectName"})
@Test
	public void ARTc014_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	
	String[] searchValues = {AR_Firm5};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName" })
@Test
	public void ARTc015_RenameFundraisingNameAndVerifyResearchData(String projectName) {
    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
    FundRaisingPageBusinessLayer frp = new FundRaisingPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	
	 String updatedname = "Fundraising with Institution NSAdmin Record03 - Updated";
	 
		 lp.CRMLogin(glUser1EmailID, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm5, 10)) {
	           if (frp.UpdateFundRaisingName(projectName, updatedname, 10)) {
	               log(LogStatus.INFO, "successfully update Fundraising name " + updatedname, YesNo.Yes);
	               ExcelUtils.writeData(ResearchDataSheetFilePath, updatedname, "UpdatedData", excelLabel.Variable_Name,"AR_Up5",
	            		   excelLabel.ResearchFindings);
	           } else {
	               sa.assertTrue(false, "not able to update Fundraising name " + updatedname);
	               log(LogStatus.SKIP, "not able to update Fundraising name " + updatedname, YesNo.Yes);
	           }
	       } else {
	          sa.assertTrue(false, "Not Able to open created Deal : " + AR_Firm5);
	           log(LogStatus.SKIP, "Not Able to open created Deal: " + AR_Firm5, YesNo.Yes);
	      }
	   } else {
	       log(LogStatus.ERROR, "Not able to click on " + tabObj9 + " tab", YesNo.Yes);
	       sa.assertTrue(false, "Not able to click on " + tabObj9 + " tab");
	   }
	switchToDefaultContent(driver);
//	lp.CRMlogout();
//	sa.assertAll();
//	ThreadSleep(5000);
//	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),'"' + updatedname + '"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
			}
	   }
	    int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
			   System.out.println("Record Name : " + recordName);
			   
			   if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
		}  
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, updatedname, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
		}  
	}

@Parameters({ "projectName"})
@Test
	public void ARTc016_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	
	String[] searchValues = {AR_Firm6};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName" })
@Test
	public void ARTc017_RenameTaskNameAndVerifyResearchData(String projectName) {
    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
    TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	
	 String updatedname = "Intermediary  Type - TSK03 Updated";
	 
		 lp.CRMLogin(superAdminUserName, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.TaskTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.TaskTab, YesNo.No);
	       ThreadSleep(2000);
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm6, 10)) {
	           if (tp.EditEnterNameAndSave(projectName, AR_Firm6, updatedname, true)) {
	               log(LogStatus.INFO, "successfully update Task name " + updatedname, YesNo.Yes);
	               ExcelUtils.writeData(ResearchDataSheetFilePath, updatedname, "UpdatedData", excelLabel.Variable_Name,"AR_Up6",
	            		   excelLabel.ResearchFindings);
	           } else {
	               sa.assertTrue(false, "not able to update Task name " + updatedname);
	               log(LogStatus.SKIP, "not able to update Task name " + updatedname, YesNo.Yes);
	           }
	       } else {
	          sa.assertTrue(false, "Not Able to open created Task : " + AR_Firm6);
	           log(LogStatus.SKIP, "Not Able to open created Task: " + AR_Firm6, YesNo.Yes);
	      }
	   } else {
	       log(LogStatus.ERROR, "Not able to click on " + tabObj9 + " tab", YesNo.Yes);
	       sa.assertTrue(false, "Not able to click on " + tabObj9 + " tab");
	   }
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
	ThreadSleep(5000);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),'"' + updatedname + '"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
			}
	   }
	    int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
			   System.out.println("Record Name : " + recordName);
			   
			   if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
		}  
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, updatedname, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
		}  
	}

@Parameters({ "projectName"})
@Test
	public void ARTc018_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	
	String[] searchValues = {AR_Firm7};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName" })
@Test
	public void ARTc019_RenameEventNameAndVerifyResearchData(String projectName) {
    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
    TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	
	 String updatedname = "Intermediary  Type - Event03 Updated";
	 
		 lp.CRMLogin(glUser1EmailID, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.TaskTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.TaskTab, YesNo.No);
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm7, 10)) {
	           if (tp.EditEnterNameAndSave(projectName, AR_Firm7, updatedname, true)) {
	               log(LogStatus.INFO, "successfully update Event name " + updatedname, YesNo.Yes);
	               ExcelUtils.writeData(ResearchDataSheetFilePath, updatedname, "UpdatedData", excelLabel.Variable_Name,"AR_Up7",
	            		   excelLabel.ResearchFindings);
	           } else {
	               sa.assertTrue(false, "not able to update Task name " + updatedname);
	               log(LogStatus.SKIP, "not able to update Task name " + updatedname, YesNo.Yes);
	           }
	       } else {
	          sa.assertTrue(false, "Not Able to open created Task : " + AR_Firm7);
	           log(LogStatus.SKIP, "Not Able to open created Task: " + AR_Firm7, YesNo.Yes);
	      }
	   } else {
	       log(LogStatus.ERROR, "Not able to click on " + tabObj9 + " tab", YesNo.Yes);
	       sa.assertTrue(false, "Not able to click on " + tabObj9 + " tab");
	   }
	switchToDefaultContent(driver);
//	lp.CRMlogout();
//	sa.assertAll();
//	ThreadSleep(5000);
//	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),'"' + updatedname + '"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
			}
	   }
	    int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
			   System.out.println("Record Name : " + recordName);
			   
			   if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
		}  
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, updatedname, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
		}  
	}

@Parameters({ "projectName"})
@Test
	public void ARTc020_VerifyTheResearchFunctionalityForAccountRecordID(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	String errorName = "Your search term must have 2 or more characters.";
	String errorName1 = "No results for";
	String xpath,ele,searchValue = null;
	int i = 1;	
	//String searchValues[] = {"","a","zz","~!@#$%^&*()_+=-[]{}|;':,.<>/?"};
	String[][] val = {{MRSD_1_ResearchFindings},{MRSD_2_ResearchFindings},{MRSD_3_ResearchFindings},{MRSD_4_ResearchFindings},{MRSD_5_ResearchFindings},{MRSD_6_ResearchFindings},{MRSD_7_ResearchFindings},{MRSD_8_ResearchFindings},{MRSD_9_ResearchFindings}};
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	
	if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
		if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, AR_Firm1Name, 10)) {
			String recordID[] = driver.getCurrentUrl().split("Account/");
			String[] recordNo = recordID[1].split("/view"); 
			searchValue = recordNo[0];
			
		}
	ThreadSleep(2000);
		log(LogStatus.PASS, "WOrking for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
			ArrayList<String> Data = rp.verifyFieldonResearchPage(projectName, mode, val);
			if (Data.isEmpty()) {
				log(LogStatus.PASS, "Data has been Matched", YesNo.No);
				sa.assertTrue(true, "Data has been Matched");
			} else {
				log(LogStatus.ERROR, "Data is not Matched", YesNo.Yes);
				sa.assertTrue(false, "Data is not Matched : " + Data);
			}
			
			if(searchValue.length() < 2) {
				ele = rp.getErrorValue(10).getText();
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
				ele = rp.getNoResult(10).getText();
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
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}

}

@Parameters({ "projectName"})
@Test
	public void ARTc021_VerifyTheResearchFunctionalityForContactRecordID(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ContactsPageBusinessLayer ip = new ContactsPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword);
	String ele,headerName,RecordValue,searchValue = AR_Firm52;
	int i = 1;	
	//String searchValues[] = {"","a","zz","~!@#$%^&*()_+=-[]{}|;':,.<>/?"};
	
	if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
		if (ip.clickOnCreatedContact(projectName, AR_Contact1FirstName, AR_Contact1LastName)) {
			String recordID[] = driver.getCurrentUrl().split("Contact/");
			String[] recordNo = recordID[1].split("/view"); 
			RecordValue = recordNo[0];
			ExcelUtils.writeData(ResearchDataSheetFilePath, RecordValue, "UpdatedData", excelLabel.Variable_Name, "AR_Up52",
					excelLabel.Name);	
		}
	}else
	{
		log(LogStatus.ERROR, TabName.Object2Tab +" is not clickable", YesNo.Yes);
		sa.assertTrue(false, TabName.Object2Tab +" is not clickable");
	}
	ThreadSleep(2000);
		log(LogStatus.PASS, "WOrking for " + searchValue, YesNo.Yes);
			String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
			log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
				ele = rp.getResearchFindingsValue(10).getText();
				if (ele.equals(searchValue)) {
				log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
				sa.assertTrue(true, ele +" is matched with " +searchValue);
				}
				else
				{
					log(LogStatus.ERROR, ele +" is not matched with " +searchValue, YesNo.Yes);
					sa.assertTrue(false, ele +" is not matched with " +searchValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to send value "+searchValue);
			}
			}
		else {
			   log(LogStatus.FAIL, "Not able to Click on "+navigationMenuName, YesNo.No);
				sa.assertTrue(false,"--------- Not able to Click on "+ navigationMenuName + "---------");
		   }
			log(LogStatus.INFO,
					"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
							+ searchValue + "---------",
					YesNo.No);
			if(rp.getNoResult(10).getText() != null){
				log(LogStatus.PASS, rp.getNoResult(10).getText() + " is visible", YesNo.No);
				sa.assertTrue(true, rp.getNoResult(10).getText() + " is visible");
			} else {
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
					}
		
				} else {
					log(LogStatus.FAIL,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
					sa.assertTrue(false,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------");
					
			}
	}
				if (rp.mouseHoverOnNavigationAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				
				if (rp.mouseHoverOnGridAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				int gridSize = rp.getElementsFromGrid().size();
				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
				for(i=0; i<gridSize; i++)
				{		
					headerName = rp.getElementsFromGrid().get(i).getText();
					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
					
					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
					}
					if (rp.VerifyViewMoreOption(headerName)) {
						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
					}
				}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();	

}

@Parameters({ "projectName"})
@Test
	public void ARTc022_CreateCustomFields(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp=new ResearchPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String RecordTypeList = AR_RecordType1;
	String RecordTypeArray[] = RecordTypeList.split(breakSP,-1);
	String dealRecordTypeList = AR_DealRecordType1;
	String fundRecordTypeList = AR_FundRecordType1;
	String fundraisingRecordTypeList = AR_FundraisingRecordType1;
	String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);
	String fundRecordTypeArray[] = fundRecordTypeList.split(breakSP, -1);
	String fundraisingRecordTypeArray[] = fundraisingRecordTypeList.split(breakSP, -1);
	
	String[][][] RecordType = {
			{ { recordTypeLabel.Record_Type_Label.toString(), RecordTypeArray[0] },
					{ recordTypeLabel.Description.toString(), RecordTypeArray[0] + recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), RecordTypeArray[1] },
					{ recordTypeLabel.Description.toString(), RecordTypeArray[1] + recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } } };
	
	String recordActive[][] = {{ recordTypeLabel.Active.toString(), "Checked" }};
	String recordTypes [] = {"Firm","Deal","Fund","Fundraising"};
	String avail[][] = {{"Consultant RT","IT Firm"},{"SellSide Deal","BuySide Deal", "Capital Raise"},{"Mutual Fund","Trust Fund"},{"FRGRT","MSGRT"}};
	String defaultValue[] = {"SellSide Deal","Mutual Fund", "FRGRT"};
	String[] profileForSelection = { "PE Standard User", "System Administrator" };
	boolean isMakeAvailable = false;
	boolean isMakeDefault = false;
	boolean flag = false;
	String parentID=null;
	
	for (int i = 0; i < RecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Firm)) {
					if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Firm,
							ObjectFeatureName.recordTypes)) {
						if (i == 0) {
							if (sp.listOfRecordTypes().contains(RecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + RecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, RecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							}
						} else {
							isMakeDefault = false;

							if (sp.listOfRecordTypes().contains(RecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + RecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, RecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, PageLayout.Institution.toString(), 10);
							}
						}
						if (flag) {
							log(LogStatus.INFO, "Created Record Type : " + RecordTypeArray[i], YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not Able to Create Record Type : " + RecordTypeArray[i],
									YesNo.Yes);
							sa.assertTrue(false, "Not Able to Create Record Type : " + RecordTypeArray[i]);
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
						if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Deal)) {
							if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Deal,
									ObjectFeatureName.recordTypes)) {
								if (sp.clickOnAlreadyCreatedLayout(dealRecordTypeArray[i])) {
									if (sp.editRecordTypeForObject(projectName, recordActive, 10)) {
										log(LogStatus.ERROR,dealRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
									}else {
										log(LogStatus.ERROR,dealRecordTypeArray[i]+" not updated ",YesNo.Yes);
										sa.assertTrue(false, dealRecordTypeArray[i]+" not updated ");
									}
								
								}else {
									log(LogStatus.ERROR, dealRecordTypeArray[i]+" is not clickable", YesNo.Yes);
									sa.assertTrue(false, dealRecordTypeArray[i]+" is not clickable");
								}
						
							}else {
								log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
								sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
							}
						}else {
							log(LogStatus.ERROR, "Deal object could not be found in object manager", YesNo.Yes);
							sa.assertTrue(false, "Deal object could not be found in object manager");
						}
						driver.close();
						driver.switchTo().window(parentID);
						switchToDefaultContent(driver);
					}else {
						log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
						sa.assertTrue(false, "could not find new window to switch");
					}
				}else {
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
						if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Fund)) {
							if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Fund,
									ObjectFeatureName.recordTypes)) {
								if (sp.clickOnAlreadyCreatedLayout(fundRecordTypeArray[i])) {
									if (sp.editRecordTypeForObject(projectName, recordActive, 10)) {
										log(LogStatus.ERROR,fundRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
									}else {
										log(LogStatus.ERROR,fundRecordTypeArray[i]+" not updated ",YesNo.Yes);
										sa.assertTrue(false, fundRecordTypeArray[i]+" not updated ");
									}
								
								}else {
									log(LogStatus.ERROR, fundRecordTypeArray[i]+" is not clickable", YesNo.Yes);
									sa.assertTrue(false, fundRecordTypeArray[i]+" is not clickable");
								}
						
							}else {
								log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
								sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
							}
						}else {
							log(LogStatus.ERROR, "Fund object could not be found in object manager", YesNo.Yes);
							sa.assertTrue(false, "Fund object could not be found in object manager");
						}
						driver.close();
						driver.switchTo().window(parentID);
						switchToDefaultContent(driver);
					}else {
						log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
						sa.assertTrue(false, "could not find new window to switch");
					}
				}else {
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
						if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Fundraising)) {
							if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Fundraising,
									ObjectFeatureName.recordTypes)) {
								if (sp.clickOnAlreadyCreatedLayout(fundraisingRecordTypeArray[i])) {
									if (sp.editRecordTypeForObject(projectName, recordActive, 10)) {
										log(LogStatus.ERROR,fundraisingRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
									}else {
										log(LogStatus.ERROR,fundraisingRecordTypeArray[i]+" not updated ",YesNo.Yes);
										sa.assertTrue(false, fundraisingRecordTypeArray[i]+" not updated ");
									}
								
								}else {
									log(LogStatus.ERROR, fundraisingRecordTypeArray[i]+" is not clickable", YesNo.Yes);
									sa.assertTrue(false, fundraisingRecordTypeArray[i]+" is not clickable");
								}
						
							}else {
								log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
								sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
							}
						}else {
							log(LogStatus.ERROR, "Fundraising object could not be found in object manager", YesNo.Yes);
							sa.assertTrue(false, "Fundraising object could not be found in object manager");
						}
						driver.close();
						driver.switchTo().window(parentID);
						switchToDefaultContent(driver);
					}else {
						log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
						sa.assertTrue(false, "could not find new window to switch");
					}
				}else {
					log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
					sa.assertTrue(false, "could not click on setup link");
				}
		
			}
			
			switchToDefaultContent(driver);
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
		
					if (clickUsingJavaScript(driver, rp.getProfileSelected(profileForSelection[0],10), profileForSelection[0].toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + profileForSelection[0], YesNo.No);
						ThreadSleep(10000);
						for(int i=0; i <3; i++) {
							System.out.println(avail[i].length);
						switchToDefaultContent(driver);
						ThreadSleep(5000);
						switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
						if (click(driver, rp.getEditButtonForRecordTypes(recordTypes[i], 10), "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
							ThreadSleep(2000);
							
								for(int j = 0; j <avail[i].length; j++)
							if (selectVisibleTextFromDropDown(driver, sp.getavailableRecordType(10),
									"Available Tab List", avail[i][j])) {
								appLog.info(recordTypes + " is selected successfully in available tabs");
								if (click(driver, sp.getAddBtn(10), "Custom Tab Add Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on add button");
								} else {
									//sa.assertTrue(false,"Not able to click on add button so cannot add custom tabs");
									appLog.error("Not able to click on add button so cannot add custom tabs");
								}
							} else {
								appLog.error(recordTypes + " record type is not Available list Tab.");
								sa.assertTrue(false,recordTypes + " record type is not Available list Tab.");
							}
							
							if (selectVisibleTextFromDropDown(driver, sp.getdefaultRecord(10), "Default Record Type",
									defaultValue[i])) {
								log(LogStatus.INFO, "successfully verified "+defaultValue[i], YesNo.No);
		
							}else {
								log(LogStatus.ERROR, "not able to verify "+defaultValue[i]+" in selected record type", YesNo.Yes);
								sa.assertTrue(false,"not able to verify "+defaultValue[i]+" in selected record type");
		
							}
							if (click(driver, sp.getCreateUserSaveBtn_Lighting(10), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
								ThreadSleep(2000);
							} else {
								log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on save button for record type settiing");
		
							}
							}else {
								log(LogStatus.ERROR, "not able to click on edit button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on edit button for record type settiing");
		
							}
					}
					}else {
						log(LogStatus.ERROR, profileForSelection[0]+" profile is not clickable", YesNo.Yes);
						sa.assertTrue(false,profileForSelection[0]+" profile is not clickable");
					}
					
				} else {
					log(LogStatus.ERROR, "profiles tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"profiles tab is not clickable");
				}
		
				driver.close();
				driver.switchTo().window(parentID);
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
			}else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
	switchToDefaultContent(driver);
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
	public void ARTc023_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	
	String[] searchValues = {AR_Firm8};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc024_CreateAccountRecords(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(glUser1EmailID, adminPassword, appName);
		String value = "";
		String type = "";
		String[][] EntityOrAccounts = { { ARNewFirm1Name, ARNewFirm1RecordType, null }, { ARNewFirm2Name, ARNewFirm2RecordType, null } };
		
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 10)) {
					log(LogStatus.INFO, "successfully Created Account/Entity : " + value + " of record type : " + type,
							YesNo.No);

				} else {
					sa.assertTrue(false, "Not Able to Create Account/Entity : " + value + " of record type : " + type);
					log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + value + " of record type : " + type,
							YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object1Tab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object1Tab, YesNo.Yes);
			}
		}
		lp.CRMlogout();
		sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc025_VerifyResearchFunctionalityForValidDataAndRecordTypes(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	
	String[] searchValues = {AR_Firm9,AR_Firm10,AR_Firm11,AR_Firm12,AR_Firm13,AR_Firm14,AR_Firm15,AR_Firm16,AR_Firm17,AR_Firm18,AR_Firm19};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,varibale + "---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,varibale + "---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,varibale + "---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc026_UpdateRecordTypeNamesForAllObjects(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String firmRecordTypeList = AR_OtherLabels1;
	String dealRecordTypeList = AR_DealRecordType1;
	String fundRecordTypeList = AR_FundRecordType1;
	String fundraisingRecordTypeList = AR_FundraisingRecordType1;
	String firmRecordTypeArray[] = firmRecordTypeList.split(breakSP,-1);
	String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);
	String fundRecordTypeArray[] = fundRecordTypeList.split(breakSP, -1);
	String fundraisingRecordTypeArray[] = fundraisingRecordTypeList.split(breakSP, -1);
	
	boolean flag = false;
	String parentID=null;
		
	for (int i = 0; i < firmRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				String[][] firmRecordType = {{recordTypeLabel.Record_Type_Label.toString(),firmRecordTypeArray[i]+"_Updated"}};
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Firm)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Firm,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(firmRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, firmRecordType, 10)) {
								log(LogStatus.ERROR,firmRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,firmRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, firmRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, firmRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, firmRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Firm object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Firm object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
				String[][] dealRecordType = {{recordTypeLabel.Record_Type_Label.toString(),dealRecordTypeArray[i]+"_Updated"}};
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Deal)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Deal,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(dealRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, dealRecordType, 10)) {
								log(LogStatus.ERROR,dealRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,dealRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, dealRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, dealRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, dealRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Deal object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Deal object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
				String[][] fundRecordType = {{recordTypeLabel.Record_Type_Label.toString(),fundRecordTypeArray[i]+"_Updated"}};
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Fund)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Fund,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(fundRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, fundRecordType, 10)) {
								log(LogStatus.ERROR,fundRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,fundRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, fundRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, fundRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, fundRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Fund object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Fund object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
				String[][] fundraisingRecordType = {{recordTypeLabel.Record_Type_Label.toString(),fundraisingRecordTypeArray[i]+"_Updated"}};
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Fundraising)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Fundraising,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(fundraisingRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, fundraisingRecordType, 10)) {
								log(LogStatus.ERROR,fundraisingRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,fundraisingRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, fundraisingRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, fundraisingRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, fundraisingRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Fundraising object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Fundraising object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
	public void ARTc027_VerifyResearchFunctionalityForUpdatedRecordTypes(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	
	String[] searchValues = {ARUpdated1Name,ARUpdated2Name,ARUpdated3Name,ARUpdated4Name};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedRecordType",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc028_RevertRecordTypeNamesForAllObjects(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String firmRecordTypeList = AR_OtherLabels1;
	String dealRecordTypeList = AR_DealRecordType1;
	String fundRecordTypeList = AR_FundRecordType1;
	String fundraisingRecordTypeList = AR_FundraisingRecordType1;
	String firmRecordTypeArray[] = firmRecordTypeList.split(breakSP,-1);
	String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);
	String fundRecordTypeArray[] = fundRecordTypeList.split(breakSP, -1);
	String fundraisingRecordTypeArray[] = fundraisingRecordTypeList.split(breakSP, -1);
	
	boolean flag = false;
	String parentID=null;
		
	for (int i = 0; i < firmRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				String[][] firmRecordType = {{recordTypeLabel.Record_Type_Label.toString(),firmRecordTypeArray[i]}};
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Firm)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Firm,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(firmRecordTypeArray[i]+"_Updated")) {
							if (sp.editRecordTypeForObject(projectName, firmRecordType, 10)) {
								log(LogStatus.ERROR,firmRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,firmRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, firmRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, firmRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, firmRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Firm object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Firm object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
				String[][] dealRecordType = {{recordTypeLabel.Record_Type_Label.toString(),dealRecordTypeArray[i]}};
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Deal)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Deal,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(dealRecordTypeArray[i]+"_Updated")) {
							if (sp.editRecordTypeForObject(projectName, dealRecordType, 10)) {
								log(LogStatus.ERROR,dealRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,dealRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, dealRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, dealRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, dealRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Deal object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Deal object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
				String[][] fundRecordType = {{recordTypeLabel.Record_Type_Label.toString(),fundRecordTypeArray[i]}};
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Fund)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Fund,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(fundRecordTypeArray[i]+"_Updated")) {
							if (sp.editRecordTypeForObject(projectName, fundRecordType, 10)) {
								log(LogStatus.ERROR,fundRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,fundRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, fundRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, fundRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, fundRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Fund object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Fund object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
				String[][] fundraisingRecordType = {{recordTypeLabel.Record_Type_Label.toString(),fundraisingRecordTypeArray[i]}};
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Fundraising)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Fundraising,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(fundraisingRecordTypeArray[i]+"_Updated")) {
							if (sp.editRecordTypeForObject(projectName, fundraisingRecordType, 10)) {
								log(LogStatus.ERROR,fundraisingRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,fundraisingRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, fundraisingRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, fundraisingRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, fundraisingRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Fundraising object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Fundraising object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
	public void ARTc029_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	
	String[] searchValues = {AR_Firm11,AR_Firm12,AR_Firm13,AR_Firm20};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc030_UpdateRecordTypesAsInactive(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String firmRecordTypeList = AR_RecordType1;
	String contactRecordTypeList = AR_ContactRecordType1;
	String dealRecordTypeList = AR_DealRecordType1;
	String fundRecordTypeList = AR_FundRecordType1;
	String fundraisingRecordTypeList = AR_FundraisingRecordType1;
	String firmRecordTypeArray[] = firmRecordTypeList.split(breakSP, -1);
	String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP, -1);
	String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);
	String fundRecordTypeArray[] = fundRecordTypeList.split(breakSP, -1);
	String fundraisingRecordTypeArray[] = fundraisingRecordTypeList.split(breakSP, -1);
	
	String[][] RecordType = { { recordTypeLabel.Active.toString(), "Not Checked" }};

	boolean flag = false;
	String parentID=null;
	
	for (int i = 0; i < firmRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Firm)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Firm,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(firmRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.ERROR,firmRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,firmRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, firmRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, firmRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, firmRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Firm object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Firm object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

	}
	
	
	for (int i = 0; i < contactRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Contact)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Contact,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(contactRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.ERROR,contactRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,contactRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, contactRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, contactRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, contactRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Contact object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Contact object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Deal)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Deal,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(dealRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.ERROR,dealRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,dealRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, dealRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, dealRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, dealRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Deal object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Deal object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Fund)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Fund,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(fundRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.ERROR,fundRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,fundRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, fundRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, fundRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, fundRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Fund object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Fund object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
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
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Fundraising)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Fundraising,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(fundraisingRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.ERROR,fundraisingRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,fundraisingRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, fundraisingRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, fundraisingRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, fundraisingRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Fundraising object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Fundraising object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
	}
	lp.switchToLighting();
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
	public void ARTc031_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	
	String[] searchValues = {AR_Firm21};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc032_UpdateRecordTypesAsActive_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String contactRecordTypeList = AR_ContactRecordType1;
	String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP, -1);
	
	String[][] RecordType = { { recordTypeLabel.Active.toString(), "Checked" }};

	boolean flag = false;
	String parentID=null;
	
	
	for (int i = 0; i < contactRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Contact)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Contact,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(contactRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.ERROR,contactRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,contactRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, contactRecordTypeArray[i]+" not updated ");
							}
						
						}else {
							log(LogStatus.ERROR, contactRecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, contactRecordTypeArray[i]+" is not clickable");
						}
				
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Contact object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Contact object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
			}
		}
	lp.CRMlogout();
	ThreadSleep(2000);
	refresh(driver);
	ThreadSleep(2000);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	
	String[] searchValues = {AR_Firm22};
	
	for(String searchValue : searchValues) {
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc033_RemoveObjectPermissionForContactAndFund_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String profileForSelection = "PE Standard User";
	String parentID=null, objects[] ={"Fund","Contact"}, permissionTypes[] = {"Read","Create","Edit","Delete"}, status = "Not Checked";
	boolean flag = false;
	
	switchToDefaultContent(driver);
	home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
		if (parentID!=null) {
			if(sp.giveAndRemoveObjectPermissionFromProfiles(profileForSelection,objects,permissionTypes,status)) {
				log(LogStatus.PASS,"Remove Permission from Contact Object", YesNo.No);
				flag=true;
			}else {
				log(LogStatus.ERROR,"Not able to remove permission from" + objects + "Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to remove permission from" + objects + "Object");
			}
	
			driver.close();
			driver.switchTo().window(parentID);
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
	}
	switchToDefaultContent(driver);
	ThreadSleep(5000);
	lp.CRMlogout();
	refresh(driver);
	ThreadSleep(2000);
	
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	
	String[] searchValues = {AR_Firm23,AR_Firm24};
	
	for(String searchValue : searchValues) {
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
		
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc034_AddObjectPermissionForContactAndFund_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String profileForSelection = "PE Standard User";
	String parentID=null, objects[] ={"Fund","Contact"}, permissionTypes[] = {"Read","Create","Edit","Delete"}, status = "Checked";
	boolean flag = false;
	
	switchToDefaultContent(driver);
	home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
		if (parentID!=null) {
			if(sp.giveAndRemoveObjectPermissionFromProfiles(profileForSelection,objects,permissionTypes,status)) {
				log(LogStatus.PASS,"Remove Permission from Contact Object", YesNo.No);
				flag=true;
			}else {
				log(LogStatus.ERROR,"Not able to remove permission from" + objects + "Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to remove permission from" + objects + "Object");
			}
	
			driver.close();
			driver.switchTo().window(parentID);
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
	}
	switchToDefaultContent(driver);
	ThreadSleep(5000);
	lp.CRMlogout();
	refresh(driver);
	ThreadSleep(2000);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	String errorName1 = "No results for";
	
	String[] searchValues = {AR_Firm22};
	
	for(String searchValue : searchValues) {
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				ThreadSleep(2000);
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName" })
@Test
	public void ARTc035_MakeFieldInvisibleForContactPage_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String parentWindow = "";
	String[] contactName = {"LPcon01","User.rec01"},searchValues = {AR_Firm25};
	String ele, headerName;

	
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

		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Contact,
				ObjectFeatureName.FieldAndRelationShip, "Phone", PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(true,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type");

		} else {
			log(LogStatus.ERROR,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(false,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		driver.close();
	}
		driver.switchTo().window(parentWindow);
		
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (cp.clickOnCreatedContact(projectName, contactName[0], contactName[1])) {
				if (isDisplayed(driver, cp.getPhoneFieldOnContactPage(10), "Visibility", 10, "Phone") != null) {
					System.out.println(isDisplayed(driver, cp.getPhoneFieldOnContactPage(10), "Visibility", 10, "Phone"));
					log(LogStatus.PASS, "Phone Field is visible", YesNo.Yes);
					sa.assertTrue(true, "Phone Field is visible");
				} else {
					log(LogStatus.ERROR, "Phone Field is not visible", YesNo.Yes);
					sa.assertTrue(false, "Phone Field is not visible");
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
		ThreadSleep(2000);
			
		for(String searchValue : searchValues) {
			String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
			log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
				ele = rp.getResearchFindingsValue(10).getText();
				if (ele.equals(searchValue)) {
				log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
				sa.assertTrue(true, ele +" is matched with " +searchValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to send value "+searchValue);
			}
			}
			log(LogStatus.INFO,
					"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
							+ searchValue + "---------",
					YesNo.No);
			if(rp.getNoResult(5).getText() != null){
				log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
				sa.assertTrue(true, "There is no data retaled to " + searchValue);
			} else 
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
					}
		
				} else {
					log(LogStatus.FAIL,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
					sa.assertTrue(false,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------");
					
			}
				if (rp.mouseHoverOnNavigationAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				
				if (rp.mouseHoverOnGridAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				int gridSize = rp.getElementsFromGrid().size();
				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
				for(int i=0; i<gridSize; i++)
				{		
					headerName = rp.getElementsFromGrid().get(i).getText();
					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
					
					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
					}
					if (rp.VerifyViewMoreOption(headerName)) {
						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
					}
				}
			}
		lp.CRMlogout();
		refresh(driver);		
		lp.CRMLogin(glUser1EmailID, adminPassword, appName);
		String[] searchValues1 = {AR_Firm26};
		ThreadSleep(2000);
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				if (cp.clickOnCreatedContact(projectName, contactName[0], contactName[1])) {
					if (isDisplayed(driver, cp.getPhoneFieldOnContactPage(10), "Visibility", 10, "Phone") == null) {
						log(LogStatus.PASS, "Phone Field is not visible", YesNo.Yes);
						sa.assertTrue(true, "Phone Field is not visible");
					} else {
						log(LogStatus.ERROR, "Phone Field is visible", YesNo.Yes);
						sa.assertTrue(false, "Phone Field is visible");
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
			
			for(String searchValue : searchValues1) {
				String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
				log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
					ThreadSleep(2000);
					clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
					ThreadSleep(8000);
					clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
					ThreadSleep(2000);
					ele = rp.getResearchFindingsValue(10).getText();
					if (ele.equals(searchValue)) {
					log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
					sa.assertTrue(true, ele +" is matched with " +searchValue);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to send value "+searchValue);
				}
				}
				log(LogStatus.INFO,
						"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
								+ searchValue + "---------",
						YesNo.No);
				if(rp.getNoResult(5).getText() != null){
					log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
					sa.assertTrue(true, "There is no data retaled to " + searchValue);
				} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
						}
			
					} else {
						log(LogStatus.FAIL,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
						sa.assertTrue(false,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------");
						
				}
					if (rp.mouseHoverOnNavigationAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					
					if (rp.mouseHoverOnGridAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					int gridSize = rp.getElementsFromGrid().size();
					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
					for(int i=0; i<gridSize; i++)
					{		
						headerName = rp.getElementsFromGrid().get(i).getText();
						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
						
						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
						}
						if (rp.VerifyViewMoreOption(headerName)) {
							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
						}
					}
				}
			
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
	public void ARTc036_MakeFieldVisibleForContactPage_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String parentWindow = "";
	String[] contactName = {"LPcon01","User.rec01"},searchValues = {AR_Firm26};
	String ele, headerName;

	
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

		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Contact,
				ObjectFeatureName.FieldAndRelationShip, "Phone", PermissionType.givePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(true,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type");

		} else {
			log(LogStatus.ERROR,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(false,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		driver.close();
	}
		driver.switchTo().window(parentWindow);
		
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (cp.clickOnCreatedContact(projectName, contactName[0], contactName[1])) {
				if (isDisplayed(driver, cp.getPhoneFieldOnContactPage(10), "Visibility", 10, "Phone") != null) {
					System.out.println(isDisplayed(driver, cp.getPhoneFieldOnContactPage(10), "Visibility", 10, "Phone"));
					log(LogStatus.PASS, "Phone Field is visible", YesNo.Yes);
					sa.assertTrue(true, "Phone Field is visible");
				} else {
					log(LogStatus.ERROR, "Phone Field is not visible", YesNo.Yes);
					sa.assertTrue(false, "Phone Field is not visible");
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
		ThreadSleep(2000);
		lp.CRMlogout();
		refresh(driver);		
		lp.CRMLogin(glUser1EmailID, adminPassword, appName);
		ThreadSleep(2000);
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				if (cp.clickOnCreatedContact(projectName, contactName[0], contactName[1])) {
					if (isDisplayed(driver, cp.getPhoneFieldOnContactPage(10), "Visibility", 10, "Phone") == null) {
						log(LogStatus.PASS, "Phone Field is visible", YesNo.Yes);
						sa.assertTrue(true, "Phone Field is visible");
					} else {
						log(LogStatus.ERROR, "Phone Field is not visible", YesNo.Yes);
						sa.assertTrue(false, "Phone Field is not visible");
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
			
			for(String searchValue : searchValues) {
				String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
				log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
					ThreadSleep(2000);
					clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
					ThreadSleep(8000);
					clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
					ThreadSleep(2000);
					ele = rp.getResearchFindingsValue(10).getText();
					if (ele.equals(searchValue)) {
					log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
					sa.assertTrue(true, ele +" is matched with " +searchValue);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to send value "+searchValue);
				}
				}
				log(LogStatus.INFO,
						"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
								+ searchValue + "---------",
						YesNo.No);
				if(rp.getNoResult(5).getText() != null){
					log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
					sa.assertTrue(true, "There is no data retaled to " + searchValue);
				} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
						}
			
					} else {
						log(LogStatus.FAIL,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
						sa.assertTrue(false,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------");
						
				}
					if (rp.mouseHoverOnNavigationAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					
					if (rp.mouseHoverOnGridAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					int gridSize = rp.getElementsFromGrid().size();
					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
					for(int i=0; i<gridSize; i++)
					{		
						headerName = rp.getElementsFromGrid().get(i).getText();
						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
						
						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
						}
						if (rp.VerifyViewMoreOption(headerName)) {
							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
						}
					}
				}
			
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
	public void ARTc037_MakeFieldInvisibleForAllPage_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String parentWindow = "", contactFields[] = {"Description","Account Name"}, dealFields[] = {"Stage","Pipeline Comments"}, fundraisingFields[] = {"Notes","Legal Name"};
	object fields[] = {object.Task,object.Event};
	String[] searchValues = {AR_Firm27,AR_Firm28};
	String ele, headerName;

	
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

		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Firm,
				ObjectFeatureName.FieldAndRelationShip, "Description", PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(true,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type");

		} else {
			log(LogStatus.ERROR,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(false,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < contactFields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Contact,
				ObjectFeatureName.FieldAndRelationShip, contactFields[i], PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(true,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type");

		} else {
			log(LogStatus.ERROR,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(false,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type");
		}
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < dealFields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Deal,
				ObjectFeatureName.FieldAndRelationShip, dealFields[i], PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(true,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type");

		} else {
			log(LogStatus.ERROR,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(false,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type");
		}
		}	
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < fundraisingFields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Fundraising,
				ObjectFeatureName.FieldAndRelationShip, fundraisingFields[i], PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(true,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type");

		} else {
			log(LogStatus.ERROR,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(false,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type");
		}
		}	
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < fields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(fields[i],
				ObjectFeatureName.FieldAndRelationShip, "Comments", PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(true,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type");

		} else {
			log(LogStatus.ERROR,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(false,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type");
		}
		}	
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		driver.close();
	}
		driver.switchTo().window(parentWindow);
		
		ThreadSleep(2000);
	lp.CRMlogout();
	refresh(driver);		
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
			
		for(String searchValue : searchValues) {
			String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
			log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
				ele = rp.getResearchFindingsValue(10).getText();
				if (ele.equals(searchValue)) {
				log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
				sa.assertTrue(true, ele +" is matched with " +searchValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to send value "+searchValue);
			}
			}
			log(LogStatus.INFO,
					"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
							+ searchValue + "---------",
					YesNo.No);
			try {
			if(rp.getNoResult(5).getText() != null){
				log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
				sa.assertTrue(true, "There is no data retaled to " + searchValue);
			} else
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
					}
		
				} else {
					log(LogStatus.FAIL,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
					sa.assertTrue(false,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------");
					
			}
			}
			catch(Exception e)
			{
				log(LogStatus.INFO,e.getMessage(), YesNo.No);
				continue;

			}
				if (rp.mouseHoverOnNavigationAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				
				if (rp.mouseHoverOnGridAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				int gridSize = rp.getElementsFromGrid().size();
				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
				for(int i=0; i<gridSize; i++)
				{		
					headerName = rp.getElementsFromGrid().get(i).getText();
					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
					
					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
					}
					if (rp.VerifyViewMoreOption(headerName)) {
						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
					}
				}
			}
		lp.CRMlogout();
		sa.assertAll();
}

@Parameters({ "projectName" })
@Test
	public void ARTc038_MakeFieldVisibleForAllPage_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String parentWindow = "", contactFields[] = {"Description","Account Name"}, dealFields[] = {"Stage","Pipeline Comments"}, fundraisingFields[] = {"Notes","Legal Name"};
	object fields[] = {object.Task,object.Event};
	String[] searchValues = {AR_Firm27,AR_Firm28};
	String ele, headerName;

	
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

		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Firm,
				ObjectFeatureName.FieldAndRelationShip, "Description", PermissionType.givePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(true,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type");

		} else {
			log(LogStatus.ERROR,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(false,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < contactFields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Contact,
				ObjectFeatureName.FieldAndRelationShip, contactFields[i], PermissionType.givePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(true,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type");

		} else {
			log(LogStatus.ERROR,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(false,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type");
		}
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < dealFields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Deal,
				ObjectFeatureName.FieldAndRelationShip, dealFields[i], PermissionType.givePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(true,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type");

		} else {
			log(LogStatus.ERROR,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(false,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type");
		}
		}	
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < fundraisingFields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Fundraising,
				ObjectFeatureName.FieldAndRelationShip, fundraisingFields[i], PermissionType.givePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(true,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type");

		} else {
			log(LogStatus.ERROR,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(false,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type");
		}
		}	
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < fields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(fields[i],
				ObjectFeatureName.FieldAndRelationShip, "Comments", PermissionType.givePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(true,
					"Phone field Permission is given from the Firm Object Manager for Institution Record Type");

		} else {
			log(LogStatus.ERROR,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type",
					YesNo.No);
			sa.assertTrue(false,
					"Annual Revenue field Permission is not given from the Firm Object Manager for Institution Record Type");
		}
		}	
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		driver.close();
	}
		driver.switchTo().window(parentWindow);
		
		ThreadSleep(2000);
			
		for(String searchValue : searchValues) {
			String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
			log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
				ele = rp.getResearchFindingsValue(10).getText();
				if (ele.equals(searchValue)) {
				log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
				sa.assertTrue(true, ele +" is matched with " +searchValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to send value "+searchValue);
			}
			}
			log(LogStatus.INFO,
					"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
							+ searchValue + "---------",
					YesNo.No);
			
			try {
			if(rp.getNoResult(5).getText() != null){
				log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
				sa.assertTrue(true, "There is no data retaled to " + searchValue);
			} else
				
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
					}
		
				} else {
					log(LogStatus.FAIL,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
					sa.assertTrue(false,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------");
					
			}
			}
			catch(Exception e)
			{
				log(LogStatus.INFO,e.getMessage(), YesNo.No);
				continue;

			}
				if (rp.mouseHoverOnNavigationAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				
				if (rp.mouseHoverOnGridAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				int gridSize = rp.getElementsFromGrid().size();
				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
				for(int i=0; i<gridSize; i++)
				{		
					headerName = rp.getElementsFromGrid().get(i).getText();
					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
					
					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
					}
					if (rp.VerifyViewMoreOption(headerName)) {
						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
					}
				}
			}
		lp.CRMlogout();
		refresh(driver);		
		lp.CRMLogin(glUser1EmailID, adminPassword, appName);
		ThreadSleep(2000);
//			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
//				if (cp.clickOnCreatedContact(projectName, contactName[0], contactName[1])) {
//					if (isDisplayed(driver, cp.getPhoneFieldOnContactPage(10), "Visibility", 10, "Phone") == null) {
//						log(LogStatus.PASS, "Phone Field is not visible", YesNo.Yes);
//						sa.assertTrue(true, "Phone Field is not visible");
//					} else {
//						log(LogStatus.ERROR, "Phone Field is visible", YesNo.Yes);
//						sa.assertTrue(false, "Phone Field is visible");
//					}
//				}
//
//				else {
//					log(LogStatus.ERROR, "Could not click on the contact", YesNo.Yes);
//					sa.assertTrue(false, "Could not click on the contact");
//				}
//			} else {
//				log(LogStatus.ERROR, "Could not click Tab", YesNo.Yes);
//				sa.assertTrue(false, "Could not click Tab");
//
//			}
			
			for(String searchValue : searchValues) {
				String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
				log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
					ThreadSleep(2000);
					clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
					ThreadSleep(8000);
					clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
					ThreadSleep(2000);
					ele = rp.getResearchFindingsValue(10).getText();
					if (ele.equals(searchValue)) {
					log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
					sa.assertTrue(true, ele +" is matched with " +searchValue);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to send value "+searchValue);
				}
				}
				log(LogStatus.INFO,
						"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
								+ searchValue + "---------",
						YesNo.No);
				try {
				if(rp.getNoResult(5).getText() != null){
					log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
					sa.assertTrue(true, "There is no data retaled to " + searchValue);
				} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
						}
			
					} else {
						log(LogStatus.FAIL,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
						sa.assertTrue(false,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------");
						
				}
				}
				catch(Exception e)
				{
					log(LogStatus.INFO,e.getMessage(), YesNo.No);
					continue;

				}
				
					if (rp.mouseHoverOnNavigationAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					
					if (rp.mouseHoverOnGridAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					int gridSize = rp.getElementsFromGrid().size();
					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
					for(int i=0; i<gridSize; i++)
					{		
						headerName = rp.getElementsFromGrid().get(i).getText();
						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
						
						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
						}
						if (rp.VerifyViewMoreOption(headerName)) {
							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
						}
					}
				}
			
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
	public void ARTc039_UpdateFieldNames_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String parentWindow = "";
	String[] searchValues = {AR_Firm27};
	boolean flag1 = false;
	String tabNames1 = "Accounts" ,tabNames2 = "Contacts" ;
	String[] labelsWithValues1 = {  "Account Name<break>Account Name upd", "Description<break>Description upd" },labelsWithValues2 = {  "Contact Name<break>Contact Name upd", "Description<break>Description upd"  };
	String ele, headerName;
	String DealLabel1= PageLabel.Stage_Upd.toString();
	String DealLabel2= PageLabel.Pipeline_Comments_Upd.toString();
	String FundraisingLabel1= PageLabel.Legal_Name_Upd.toString();
	String FundraisingLabel2= PageLabel.Status_Notes_Upd.toString();
	
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
		if (setup.searchStandardOrCustomObject(environment, mode, object.Rename_Tabs_And_Labels)) {
			log(LogStatus.INFO, "click on Object : " + object.Rename_Tabs_And_Labels, YesNo.No);
			ThreadSleep(2000);

		if (setup.renameLabelsOfFields(driver, tabNames1, labelsWithValues1, 10)) {
			flag1 = true;
			log(LogStatus.PASS, labelsWithValues1[0] + " is updated as " +labelsWithValues1[1] , YesNo.Yes);
			sa.assertTrue(true, labelsWithValues1[0] + " is updated as " +labelsWithValues1[1]);
		}

		if (setup.renameLabelsOfFields(driver, tabNames2, labelsWithValues2, 10)) {
			flag1 = true;
			log(LogStatus.PASS, labelsWithValues2[0] + " is updated as " +labelsWithValues2[1] , YesNo.Yes);
			sa.assertTrue(true, labelsWithValues2[0] + " is updated as " +labelsWithValues2[1]);
		}
		} else {
			log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_And_Labels, YesNo.Yes);
			sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_And_Labels);
		}	
		driver.close();
	}	
	
	ThreadSleep(2000);
	driver.switchTo().window(parentWindow);
	lp.CRMlogout();
	
	ThreadSleep(2000);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
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
		
		if (setup.searchStandardOrCustomObject(environment, mode,object.Override)){
			log(LogStatus.INFO, "click on Object : " +object.valueOf("Override"), YesNo.No);
			ThreadSleep(2000);				
			switchToFrame(driver, 10, setup.getSetUpPageIframe(10));
			ThreadSleep(5000);	
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
				log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);	
				if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Deal.toString())){
					log(LogStatus.INFO, "Select "+PageLabel.Deal.toString()+" text in object dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Stage.toString(), DealLabel1.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Stage.toString()+" successfully update to "+DealLabel1, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Stage.toString()+" successfully update to "+DealLabel1, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Stage.toString()+" to "+DealLabel1);	
					}
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Pipeline_Comments.toString().replace("_", " "), DealLabel2.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Pipeline_Comments.toString()+" successfully update to "+DealLabel2, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Pipeline_Comments.toString()+" successfully update to "+DealLabel2, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Pipeline_Comments.toString()+" to "+DealLabel2);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Deal.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Deal.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
			}
		}else{
			
			log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Override"), YesNo.Yes);
			sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Override"));
		}
		
		
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
				log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);	
				if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Fundraising.toString())){
					log(LogStatus.INFO, "Select "+PageLabel.Fundraising.toString()+" text in object dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Legal_Name.toString().replace("_"," "), FundraisingLabel1.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Legal_Name.toString()+" successfully update to "+FundraisingLabel1, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Legal_Name.toString()+" successfully update to "+FundraisingLabel1, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Legal_Name.toString()+" to "+FundraisingLabel1);	
					}
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Status_Notes.toString().replace("_", " "), FundraisingLabel2.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Status_Notes.toString()+" successfully update to "+FundraisingLabel2, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Status_Notes.toString()+" successfully update to "+FundraisingLabel2, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Status_Notes.toString()+" to "+FundraisingLabel2);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Fundraising.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Fundraising.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
			}
			ThreadSleep(2000);
		driver.close();
	}	
	ThreadSleep(2000);
	driver.switchTo().window(parentWindow);
	lp.CRMlogout();
	ThreadSleep(2000);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
		for(String searchValue : searchValues) {
			String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
			log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
				ele = rp.getResearchFindingsValue(10).getText();
				if (ele.equals(searchValue)) {
				log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
				sa.assertTrue(true, ele +" is matched with " +searchValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to send value "+searchValue);
			}
			}
			log(LogStatus.INFO,
					"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
							+ searchValue + "---------",
					YesNo.No);
			try {
				if(rp.getNoResult(5).getText() != null){
					log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
					sa.assertTrue(true, "There is no data retaled to " + searchValue);
				} else
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
					}
		
				} else {
					log(LogStatus.FAIL,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
					sa.assertTrue(false,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------");
					
			}
			}
			catch(Exception e)
			{
				log(LogStatus.INFO,e.getMessage(), YesNo.No);
				continue;

			}
				if (rp.mouseHoverOnNavigationAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				
				if (rp.mouseHoverOnGridAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				int gridSize = rp.getElementsFromGrid().size();
				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
				for(int i=0; i<gridSize; i++)
				{		
					headerName = rp.getElementsFromGrid().get(i).getText();
					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
					
					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
					}
					if (rp.VerifyViewMoreOption(headerName)) {
						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
					}
				}
			}
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
	public void ARTc040_UpdateFieldNamesWithSpecialCharaters_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String parentWindow = "";
	String[] searchValues = {AR_Firm27};
	boolean flag1 = false;
	String tabNames1 = "Accounts" ,tabNames2 = "Contacts" ;
	String[] labelsWithValues1 = {  "Account Name<break>Account Name Upd !@&*()(*& 123 Account Name Upd !@&*()(*& 123 Account Name Upd", "Description<break>Description Upd !@&*()(*& 123 Description Upd !@&*()(*& 123 Description Upd Upd" },labelsWithValues2 = {  "Contact Name<break>Contact Name Upd !@&*()(*& 123 Contact Name Upd !@&*()(*& 123 Contact Name Upd", "Description<break>Description Upd !@&*()(*& 123 Description Upd !@&*()(*& 123 Description Upd Upd"  };
	String ele, headerName;
	String DealLabel1= "Stage Upd !@&*()(*& 123 Stage Upd !@&*()(*& 123 Stage Upd !@&*()(*& 123 Stage";
	String DealLabel2= "Pipeline Comments Upd !@&*()(*& 123 Pipeline Comments Upd !@&*()(*&123 Pipeline";
	String FundraisingLabel1= "Legal Name Upd !@&*()(*& 123 Legal Name Upd !@&*()(*&123 Legal Name Upd";
	String FundraisingLabel2= "Status Notes Upd !@&*()(*& 123 Status Notes Upd !@&*()(*&123 Status Notes Upd!@";
	
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
		if (setup.searchStandardOrCustomObject(environment, mode, object.Rename_Tabs_And_Labels)) {
			log(LogStatus.INFO, "click on Object : " + object.Rename_Tabs_And_Labels, YesNo.No);
			ThreadSleep(2000);

		if (setup.renameLabelsOfFields(driver, tabNames1, labelsWithValues1, 10)) {
			flag1 = true;
			log(LogStatus.PASS, labelsWithValues1[0] + " is updated as " +labelsWithValues1[1] , YesNo.Yes);
			sa.assertTrue(true, labelsWithValues1[0] + " is updated as " +labelsWithValues1[1]);
		}

		if (setup.renameLabelsOfFields(driver, tabNames2, labelsWithValues2, 10)) {
			flag1 = true;
			log(LogStatus.PASS, labelsWithValues2[0] + " is updated as " +labelsWithValues2[1] , YesNo.Yes);
			sa.assertTrue(true, labelsWithValues2[0] + " is updated as " +labelsWithValues2[1]);
		}
		} else {
			log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_And_Labels, YesNo.Yes);
			sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_And_Labels);
		}	
		driver.close();
	}	
	
	ThreadSleep(2000);
	driver.switchTo().window(parentWindow);
	lp.CRMlogout();
	
	ThreadSleep(2000);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
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
		
		if (setup.searchStandardOrCustomObject(environment, mode,object.Override)){
			log(LogStatus.INFO, "click on Object : " +object.valueOf("Override"), YesNo.No);
			ThreadSleep(2000);				
			switchToFrame(driver, 10, setup.getSetUpPageIframe(10));
			ThreadSleep(5000);	
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
				log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);	
				if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Deal.toString())){
					log(LogStatus.INFO, "Select "+PageLabel.Deal.toString()+" text in object dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Stage.toString(), DealLabel1.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Stage.toString()+" successfully update to "+DealLabel1, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Stage.toString()+" successfully update to "+DealLabel1, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Stage.toString()+" to "+DealLabel1);	
					}
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Pipeline_Comments.toString().replace("_", " "), DealLabel2.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Pipeline_Comments.toString()+" successfully update to "+DealLabel2, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Pipeline_Comments.toString()+" successfully update to "+DealLabel2, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Pipeline_Comments.toString()+" to "+DealLabel2);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Deal.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Deal.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
			}
		}else{
			
			log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Override"), YesNo.Yes);
			sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Override"));
		}
		
		
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
				log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);	
				if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Fundraising.toString())){
					log(LogStatus.INFO, "Select "+PageLabel.Fundraising.toString()+" text in object dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Legal_Name.toString().replace("_"," "), FundraisingLabel1.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Legal_Name.toString()+" successfully update to "+FundraisingLabel1, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Legal_Name.toString()+" successfully update to "+FundraisingLabel1, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Legal_Name.toString()+" to "+FundraisingLabel1);	
					}
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Status_Notes.toString().replace("_", " "), FundraisingLabel2.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Status_Notes.toString()+" successfully update to "+FundraisingLabel2, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Status_Notes.toString()+" successfully update to "+FundraisingLabel2, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Status_Notes.toString()+" to "+FundraisingLabel2);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Fundraising.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Fundraising.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
			}
			ThreadSleep(2000);
		driver.close();
	}	
	ThreadSleep(2000);
	driver.switchTo().window(parentWindow);
	lp.CRMlogout();
	ThreadSleep(2000);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
		for(String searchValue : searchValues) {
			String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
			log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
				ele = rp.getResearchFindingsValue(10).getText();
				if (ele.equals(searchValue)) {
				log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
				sa.assertTrue(true, ele +" is matched with " +searchValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to send value "+searchValue);
			}
			}
			log(LogStatus.INFO,
					"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
							+ searchValue + "---------",
					YesNo.No);
			
			try {
			if(rp.getNoResult(5).getText() != null){
				log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
				sa.assertTrue(true, "There is no data retaled to " + searchValue);
			} else
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
					}
		
				} else {
					log(LogStatus.FAIL,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
					sa.assertTrue(false,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------");
					
			}
			}
			catch(Exception e)
			{
				log(LogStatus.INFO,e.getMessage(), YesNo.No);
				continue;

			}
				if (rp.mouseHoverOnNavigationAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				
				if (rp.mouseHoverOnGridAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				int gridSize = rp.getElementsFromGrid().size();
				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
				for(int i=0; i<gridSize; i++)
				{		
					headerName = rp.getElementsFromGrid().get(i).getText();
					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
					
					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
					}
					if (rp.VerifyViewMoreOption(headerName)) {
						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
					}
				}
			}
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
	public void ARTc041_UpdateFieldNames_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String parentWindow = "";
	String[] searchValues = {AR_Firm27};
	boolean flag1 = false;
	String tabNames1 = "Accounts" ,tabNames2 = "Contacts" ;
	String[] labelsWithValues1 = {  "Account Name<break>Account Name", "Description<break>Description" },labelsWithValues2 = {  "Contact Name<break>Contact Name", "Description<break>Description"  };
	String ele, headerName;
	String DealLabel1= PageLabel.Stage.toString();
	String DealLabel2= PageLabel.Pipeline_Comments.toString();
	String FundraisingLabel1= PageLabel.Legal_Name.toString();
	String FundraisingLabel2= PageLabel.Status_Notes.toString();
	
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
		if (setup.searchStandardOrCustomObject(environment, mode, object.Rename_Tabs_And_Labels)) {
			log(LogStatus.INFO, "click on Object : " + object.Rename_Tabs_And_Labels, YesNo.No);
			ThreadSleep(2000);

		if (setup.renameLabelsOfFields(driver, tabNames1, labelsWithValues1, 10)) {
			flag1 = true;
			log(LogStatus.PASS, labelsWithValues1[0] + " is updated as " +labelsWithValues1[1] , YesNo.Yes);
			sa.assertTrue(true, labelsWithValues1[0] + " is updated as " +labelsWithValues1[1]);
		}

		if (setup.renameLabelsOfFields(driver, tabNames2, labelsWithValues2, 10)) {
			flag1 = true;
			log(LogStatus.PASS, labelsWithValues2[0] + " is updated as " +labelsWithValues2[1] , YesNo.Yes);
			sa.assertTrue(true, labelsWithValues2[0] + " is updated as " +labelsWithValues2[1]);
		}
		} else {
			log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_And_Labels, YesNo.Yes);
			sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_And_Labels);
		}	
		driver.close();
	}	
	
	ThreadSleep(2000);
	driver.switchTo().window(parentWindow);
	lp.CRMlogout();
	
	ThreadSleep(2000);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
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
		
		if (setup.searchStandardOrCustomObject(environment, mode,object.Override)){
			log(LogStatus.INFO, "click on Object : " +object.valueOf("Override"), YesNo.No);
			ThreadSleep(2000);				
			switchToFrame(driver, 10, setup.getSetUpPageIframe(10));
			ThreadSleep(5000);	
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
				log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);	
				if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Deal.toString())){
					log(LogStatus.INFO, "Select "+PageLabel.Deal.toString()+" text in object dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Stage.toString(), DealLabel1.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Stage.toString()+" successfully update to "+DealLabel1, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Stage.toString()+" successfully update to "+DealLabel1, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Stage.toString()+" to "+DealLabel1);	
					}
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Pipeline_Comments.toString().replace("_", " "), DealLabel2.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Pipeline_Comments.toString()+" successfully update to "+DealLabel2, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Pipeline_Comments.toString()+" successfully update to "+DealLabel2, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Pipeline_Comments.toString()+" to "+DealLabel2);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Deal.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Deal.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
			}
		}else{
			
			log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Override"), YesNo.Yes);
			sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Override"));
		}
		
		
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
				log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);	
				if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Fundraising.toString())){
					log(LogStatus.INFO, "Select "+PageLabel.Fundraising.toString()+" text in object dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Legal_Name.toString().replace("_"," "), FundraisingLabel1.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Legal_Name.toString()+" successfully update to "+FundraisingLabel1, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Legal_Name.toString()+" successfully update to "+FundraisingLabel1, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Legal_Name.toString()+" to "+FundraisingLabel1);	
					}
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Status_Notes.toString().replace("_", " "), FundraisingLabel2.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Status_Notes.toString()+" successfully update to "+FundraisingLabel2, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Status_Notes.toString()+" successfully update to "+FundraisingLabel2, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Status_Notes.toString()+" to "+FundraisingLabel2);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Fundraising.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Fundraising.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
			}
			ThreadSleep(2000);
		driver.close();
	}	
	ThreadSleep(2000);
	driver.switchTo().window(parentWindow);
	lp.CRMlogout();
	ThreadSleep(2000);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
		for(String searchValue : searchValues) {
			String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
			log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
				ele = rp.getResearchFindingsValue(10).getText();
				if (ele.equals(searchValue)) {
				log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
				sa.assertTrue(true, ele +" is matched with " +searchValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to send value "+searchValue);
			}
			}
			log(LogStatus.INFO,
					"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
							+ searchValue + "---------",
					YesNo.No);
			try {
				if(rp.getNoResult(5).getText() != null){
					log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
					sa.assertTrue(true, "There is no data retaled to " + searchValue);
				} else
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
					}
		
				} else {
					log(LogStatus.FAIL,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
					sa.assertTrue(false,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------");
					
			}
		}
			catch(Exception e)
			{
				log(LogStatus.INFO,e.getMessage(), YesNo.No);
				continue;
		
			}	
				if (rp.mouseHoverOnNavigationAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				
				if (rp.mouseHoverOnGridAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				int gridSize = rp.getElementsFromGrid().size();
				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
				for(int i=0; i<gridSize; i++)
				{		
					headerName = rp.getElementsFromGrid().get(i).getText();
					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
					
					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
					}
					if (rp.VerifyViewMoreOption(headerName)) {
						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
					}
				}
			}
	lp.CRMlogout();
	sa.assertAll();
}

	@Parameters({ "projectName" })
	@Test
	public void ARTc042_1_createCloneProfileAndUser(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	String parentWindow = null;
	String[] splitedUserLastName = removeNumbersFromString(crmUser3LastName);
	String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
	String emailId = lp.generateRandomEmailId(gmailUserName);
	ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User3",
			excelLabel.User_Last_Name);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create clone user");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create clone user",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
			}
			ThreadSleep(3000);
			if (setup.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
				log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
				ThreadSleep(2000);
				switchToDefaultContent(driver);
				switchToFrame(driver, 10, setup.getSetUpPageIframe(20));
				String xpath = "//th//a[text()='PE Standard User']";
				WebElement ele = FindElement(driver, xpath, "PE Standard User", action.SCROLLANDBOOLEAN, 10);
				ele = isDisplayed(driver, ele, "visibility", 10, "PE Standard User");
				if (clickUsingJavaScript(driver, ele, "PE Standard user link", action.BOOLEAN)) {
					log(LogStatus.INFO, "able to click on PE standard user link", YesNo.No);
					ThreadSleep(1000);
					switchToFrame(driver, 10, setup.getSetUpPageIframe(10));
					if (click(driver, setup.getCloneButton(10), "clone button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "click on clone button of  PE standard user link", YesNo.No);
						ThreadSleep(5000);
						switchToFrame(driver, 10, setup.getSetUpPageIframe(10));
						if (sendKeys(driver, setup.getProfileNameTextBox(10), "Cloned PE standard User",
								"profile name text box ", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "enter the clone PE user profile name ", YesNo.No);
							if (click(driver, setup.getViewAccessbilityDropDownSaveButton(10), "save button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "clicked on save button", YesNo.No);
							} else {
								log(LogStatus.PASS, "not able to clicked on save button so cannot create cloned user ",
										YesNo.No);
								sa.assertTrue(false,
										"not able to clicked on save button so cannot create cloned user ");
							}
						} else {
							log(LogStatus.PASS, "not able to enter the cline PE user profile name ", YesNo.No);
							sa.assertTrue(false, "not able to enter the cline PE user profile name ");
						}
					} else {
						log(LogStatus.INFO, "not able to click on clone button of  Pe Standard user link", YesNo.No);
						sa.assertTrue(false, "not able to click on clone button of  Pe Standard user link");
					}
				} else {
					log(LogStatus.INFO, "not able to click on Pe Standard user link", YesNo.No);
					sa.assertTrue(false, "not able to click on Pe Standard user link");
				}
	
			} else {
				log(LogStatus.ERROR, "Not able to search/click on " + object.Profiles, YesNo.Yes);
				sa.assertTrue(false, "Not able to search/click on " + object.Profiles);
			}
			ThreadSleep(5000);
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		} else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot create clone user", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create clone user");
		}
		lp.CRMlogout();
		sa.assertAll();

		ThreadSleep(2000);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ThreadSleep(2000);
		boolean flag = false;
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
					if (setup.createPEUser(crmUser3FirstName, UserLastName, emailId, crmUser3Lience, crmUser3Profile, " ")) {
						log(LogStatus.INFO,
								"CRM User is created Successfully: " + crmUser3FirstName + " " + UserLastName,
								YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User3",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name,
								"User3", excelLabel.User_Last_Name);
						flag = true;
	
					}
					ThreadSleep(5000);
					driver.close();
					driver.switchTo().window(parentWindow);
	
				}
			} catch (Exception e) {
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}
		lp.CRMlogout();
		closeBrowser();
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
			appLog.info("Password is set successfully for CRM User3: " + crmUser3FirstName + " " + UserLastName);
		} else {
			appLog.info("Password is not set for CRM User3: " + crmUser3FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User3: " + crmUser3FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User3: " + crmUser3FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void ARTc042_2_VerifyTheNavigationMenuItems(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
		lp.CRMLogin(glUser3EmailID, adminPassword);
		String ele,headerName,varibale;
		String[] searchValues = {AR_Firm30,AR_Firm31,AR_Firm32,AR_Firm33,AR_Firm34,AR_Firm35,AR_Firm36,AR_Firm37,AR_Firm38,AR_Firm39,AR_Firm40,AR_Firm41,AR_Firm42,AR_Firm43,AR_Data1,AR_Data2,AR_Data3};
		
		// Verification on navigation menu
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, rp.getTextAreaResearch(10),"Research Text Area", action.BOOLEAN)) {
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
//			xpath = "(//div[contains(@class,'DOCKED')]//div//button)[3]";
//			ele = FindElement(driver, xpath,filesName, action.BOOLEAN, 10).getText();
//			if (ele!=null) {
			if(clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN)) {
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
			if(clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimize Button", action.BOOLEAN)) {
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
		
//		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
//			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
//			xpath = "(//div[contains(@class,'DOCKED')]//div//button)[2]";
//			WebElement ele1 = FindElement(driver, xpath,filesName, action.BOOLEAN, 10);
//			click(driver,ele1,"Pop-out",action.BOOLEAN);
//			ThreadSleep(8000);
//			xpath = "//div[contains(@class,'normal')]//button[@title='Pop-in']";
//			WebElement ele2 = FindElement(driver, xpath,filesName, action.BOOLEAN, 10);
//			click(driver,ele2,"Pop-in",action.BOOLEAN);
//			ThreadSleep(4000);
//			switchToDefaultContent(driver);
//			if (ele2==null) {
//				log(LogStatus.INFO, "Research popup successfully pop-out closed", YesNo.No);
//			} else {
//				log(LogStatus.ERROR, "Research popup not successfully closed", YesNo.Yes);
//				sa.assertTrue(false,"Research popup not successfully closed");
//			}
//		} else {
//			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName, YesNo.Yes);
//			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName);
//		}
		
		for(String searchValue : searchValues) {
			if(searchValue.contains("ACR_")) {
				varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValue, excelLabel.Variable_Name);
			}
			else {
				varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
			}
			
			log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
				ele = rp.getResearchFindingsValue(10).getText();
				if (ele.equals(searchValue)) {
				log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
				sa.assertTrue(true, ele +" is matched with " +searchValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to send value "+searchValue);
			}
			}
			log(LogStatus.INFO,
					"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
							+ searchValue + "---------",
					YesNo.No);
			try {
			if(rp.getNoResult(5).getText() != null){
				log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
				sa.assertTrue(true, "There is no data retaled to " + searchValue);
			} else
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
					}
		
				} else {
					log(LogStatus.FAIL,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
					sa.assertTrue(false,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------");
					
			}

}
	catch(Exception e)
	{
		log(LogStatus.INFO,e.getMessage(), YesNo.No);
		continue;

	}		
				if (rp.mouseHoverOnNavigationAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				
				if (rp.mouseHoverOnGridAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				int gridSize = rp.getElementsFromGrid().size();
				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
				for(int i=0; i<gridSize; i++)
				{		
					headerName = rp.getElementsFromGrid().get(i).getText();
					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
					
					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
					}
					if (rp.VerifyViewMoreOption(headerName)) {
						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
					}
				}
			}
		lp.CRMlogout();
		
		ThreadSleep(2000);
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void ARTc043_UpdateCustomMetaDataTypesForAccount_VerifyImpact(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	String parentWindow = null;
	String[] searchValues = {AR_Data1};
	String headerName, FieldName = ARFieldName1, Value = ARNewValue1;
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create clone user");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create clone user",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
			}
			ThreadSleep(3000);
				if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), FieldName, Value, 10))
				{
					log(LogStatus.INFO, "Changed the value of " + FieldName + " for Acuity Setting", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "Not able to change the value of " + FieldName + " for Acuity Setting", YesNo.No);
					sa.assertTrue(false, "Not able to changed the value of " + FieldName + " for Acuity Setting");	
				}
			
			ThreadSleep(5000);
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		} else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot change value");
		}
		lp.CRMlogout();
		sa.assertAll();
		
		ThreadSleep(2000);
		lp.CRMLogin(glUser1EmailID, adminPassword, appName);
		ThreadSleep(2000);
			for(String searchValue : searchValues) {
				String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValue, excelLabel.Variable_Name);
				log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
					ThreadSleep(2000);
					clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
					ThreadSleep(8000);
					clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
					ThreadSleep(2000);
				String ele = rp.getResearchFindingsValue(10).getText();
					if (ele.equals(searchValue)) {
					log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
					sa.assertTrue(true, ele +" is matched with " +searchValue);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to send value "+searchValue);
				}
				}
				log(LogStatus.INFO,
						"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
								+ searchValue + "---------",
						YesNo.No);
				
				try {
				if(rp.getNoResult(5).getText() != null){
					log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
					sa.assertTrue(true, "There is no data retaled to " + searchValue);
				} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
						}
			
					} else {
						log(LogStatus.FAIL,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
						sa.assertTrue(false,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------");
						
				}
				}
				catch(Exception e)
				{
					log(LogStatus.INFO,e.getMessage(), YesNo.No);
					continue;

				}
					if (rp.mouseHoverOnNavigationAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					
					if (rp.mouseHoverOnGridAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					int gridSize = rp.getElementsFromGrid().size();
					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
					for(int i=0; i<gridSize; i++)
					{		
						headerName = rp.getElementsFromGrid().get(i).getText();
						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
						
						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
						}
						if (rp.VerifyViewMoreOption(headerName)) {
							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
						}
					}
				}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ARTc044_UpdateCustomMetaDataTypesForContact_VerifyImpact(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	String parentWindow = null;
	String[] searchValues = {AR_Data1}, FieldName = {ARFieldName2,ARFieldName3}, Value  = {ARNewValue2,ARNewValue3};
	String headerName;
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create clone user");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create clone user",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
			}
			ThreadSleep(3000);
			for(int i = 0 ; i <FieldName.length; i++) {
				if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), FieldName[i], Value[i], 10))
				{
					log(LogStatus.INFO, "Changed the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "Not able to change the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
					sa.assertTrue(false, "Not able to changed the value of " + FieldName[i] + " for Acuity Setting");	
				}
				ThreadSleep(5000);
			}	
			ThreadSleep(5000);
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		} else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot change value");
		}
		lp.CRMlogout();
		sa.assertAll();
		
		ThreadSleep(2000);
		lp.CRMLogin(glUser1EmailID, adminPassword, appName);
		ThreadSleep(2000);
			for(String searchValue : searchValues) {
				String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValue, excelLabel.Variable_Name);
				log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
					ThreadSleep(2000);
					clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
					ThreadSleep(8000);
					clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
					ThreadSleep(2000);
				String ele = rp.getResearchFindingsValue(10).getText();
					if (ele.equals(searchValue)) {
					log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
					sa.assertTrue(true, ele +" is matched with " +searchValue);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to send value "+searchValue);
				}
				}
				log(LogStatus.INFO,
						"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
								+ searchValue + "---------",
						YesNo.No);
				try {
					if(rp.getNoResult(5).getText() != null){
						log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
						sa.assertTrue(true, "There is no data retaled to " + searchValue);
					} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
						}
			
					} else {
						log(LogStatus.FAIL,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
						sa.assertTrue(false,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------");
						
				}
			}
				catch(Exception e)
				{
					log(LogStatus.INFO,e.getMessage(), YesNo.No);
					continue;

				}
					if (rp.mouseHoverOnNavigationAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					
					if (rp.mouseHoverOnGridAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					int gridSize = rp.getElementsFromGrid().size();
					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
					for(int i=0; i<gridSize; i++)
					{		
						headerName = rp.getElementsFromGrid().get(i).getText();
						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
						
						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
						}
						if (rp.VerifyViewMoreOption(headerName)) {
							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
						}
					}
				}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ARTc045_UpdateCustomMetaDataTypesForDeal_VerifyImpact(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	String parentWindow = null;
	String[] searchValues = {AR_Data1}, FieldName = {ARFieldName4,ARFieldName5}, Value = {ARNewValue4,ARNewValue5};
	String headerName;
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create clone user");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create clone user",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
			}
			ThreadSleep(3000);
			for(int i = 0 ; i <FieldName.length; i++) {
				if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), FieldName[i], Value[i], 10))
				{
					log(LogStatus.INFO, "Changed the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "Not able to change the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
					sa.assertTrue(false, "Not able to changed the value of " + FieldName[i] + " for Acuity Setting");	
				}
				ThreadSleep(5000);
			}	
			ThreadSleep(5000);
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		} else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot change value");
		}
		lp.CRMlogout();
		sa.assertAll();
		
		ThreadSleep(2000);
		lp.CRMLogin(glUser1EmailID, adminPassword, appName);
		ThreadSleep(2000);
			for(String searchValue : searchValues) {
				String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValue, excelLabel.Variable_Name);
				log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
					ThreadSleep(2000);
					clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
					ThreadSleep(8000);
					clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
					ThreadSleep(2000);
				String ele = rp.getResearchFindingsValue(10).getText();
					if (ele.equals(searchValue)) {
					log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
					sa.assertTrue(true, ele +" is matched with " +searchValue);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to send value "+searchValue);
				}
				}
				log(LogStatus.INFO,
						"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
								+ searchValue + "---------",
						YesNo.No);
				try {
					if(rp.getNoResult(5).getText() != null){
						log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
						sa.assertTrue(true, "There is no data retaled to " + searchValue);
					} else	
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
						}
			
					} else {
						log(LogStatus.FAIL,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
						sa.assertTrue(false,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------");
						
				}
				}
				catch(Exception e)
				{
					log(LogStatus.INFO,e.getMessage(), YesNo.No);
					continue;

				}
					if (rp.mouseHoverOnNavigationAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					
					if (rp.mouseHoverOnGridAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					int gridSize = rp.getElementsFromGrid().size();
					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
					for(int i=0; i<gridSize; i++)
					{		
						headerName = rp.getElementsFromGrid().get(i).getText();
						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
						
						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
						}
						if (rp.VerifyViewMoreOption(headerName)) {
							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
						}
					}
				}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ARTc046_UpdateCustomMetaDataTypesForFund_VerifyImpact(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	String parentWindow = null;
	String[] searchValues = {AR_Data1}, FieldName = {ARFieldName6,ARFieldName7},Value = {ARNewValue6,ARNewValue7};
	String headerName;
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create clone user");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create clone user",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
			}
			ThreadSleep(3000);
			for(int i = 0 ; i <FieldName.length; i++) {
				if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), FieldName[i], Value[i], 10))
				{
					log(LogStatus.INFO, "Changed the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "Not able to change the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
					sa.assertTrue(false, "Not able to changed the value of " + FieldName[i] + " for Acuity Setting");	
				}
				ThreadSleep(5000);
			}	
			ThreadSleep(5000);
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		} else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot change value");
		}
		lp.CRMlogout();
		sa.assertAll();
		
		ThreadSleep(2000);
		lp.CRMLogin(glUser1EmailID, adminPassword, appName);
		ThreadSleep(2000);
			for(String searchValue : searchValues) {
				String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValue, excelLabel.Variable_Name);
				log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
					ThreadSleep(2000);
					clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
					ThreadSleep(8000);
					clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
					ThreadSleep(2000);
				String ele = rp.getResearchFindingsValue(10).getText();
					if (ele.equals(searchValue)) {
					log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
					sa.assertTrue(true, ele +" is matched with " +searchValue);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to send value "+searchValue);
				}
				}
				log(LogStatus.INFO,
						"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
								+ searchValue + "---------",
						YesNo.No);
				try {
					if(rp.getNoResult(5).getText() != null){
						log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
						sa.assertTrue(true, "There is no data retaled to " + searchValue);
					} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
						}
			
					} else {
						log(LogStatus.FAIL,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
						sa.assertTrue(false,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------");
						
				}
				}
				catch(Exception e)
				{
					log(LogStatus.INFO,e.getMessage(), YesNo.No);
					continue;

				}
					if (rp.mouseHoverOnNavigationAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					
					if (rp.mouseHoverOnGridAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					int gridSize = rp.getElementsFromGrid().size();
					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
					for(int i=0; i<gridSize; i++)
					{		
						headerName = rp.getElementsFromGrid().get(i).getText();
						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
						
						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
						}
						if (rp.VerifyViewMoreOption(headerName)) {
							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
						}
					}
				}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ARTc047_UpdateCustomMetaDataTypesForFundraising_VerifyImpact(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	String parentWindow = null;
	String[] searchValues = {AR_Data1}, FieldName = {ARFieldName8,ARFieldName9}, Value = {ARNewValue8,ARNewValue9};
	String headerName;
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create clone user");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create clone user",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
			}
			ThreadSleep(3000);
			for(int i = 0 ; i <FieldName.length; i++) {
				if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), FieldName[i], Value[i], 10))
				{
					log(LogStatus.INFO, "Changed the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "Not able to change the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
					sa.assertTrue(false, "Not able to changed the value of " + FieldName[i] + " for Acuity Setting");	
				}
				ThreadSleep(5000);
			}	
			ThreadSleep(5000);
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		} else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot change value");
		}
		lp.CRMlogout();
		sa.assertAll();
		
		ThreadSleep(2000);
		lp.CRMLogin(glUser1EmailID, adminPassword, appName);
		ThreadSleep(2000);
			for(String searchValue : searchValues) {
				String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValue, excelLabel.Variable_Name);
				log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
					ThreadSleep(2000);
					clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
					ThreadSleep(8000);
					clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
					ThreadSleep(2000);
				String ele = rp.getResearchFindingsValue(10).getText();
					if (ele.equals(searchValue)) {
					log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
					sa.assertTrue(true, ele +" is matched with " +searchValue);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to send value "+searchValue);
				}
				}
				log(LogStatus.INFO,
						"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
								+ searchValue + "---------",
						YesNo.No);
				try {
					if(rp.getNoResult(5).getText() != null){
						log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
						sa.assertTrue(true, "There is no data retaled to " + searchValue);
					} else	
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
						}
			
					} else {
						log(LogStatus.FAIL,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
						sa.assertTrue(false,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------");
						
				}
				}
				catch(Exception e)
				{
					log(LogStatus.INFO,e.getMessage(), YesNo.No);
					continue;

				}
					if (rp.mouseHoverOnNavigationAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					
					if (rp.mouseHoverOnGridAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					int gridSize = rp.getElementsFromGrid().size();
					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
					for(int i=0; i<gridSize; i++)
					{		
						headerName = rp.getElementsFromGrid().get(i).getText();
						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
						
						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
						}
						if (rp.VerifyViewMoreOption(headerName)) {
							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
						}
					}
				}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ARTc048_RevertCustomMetaDataTypesForAllObjects_VerifyImpact(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	String parentWindow = null;
	String[] searchValues = {AR_Data1};
	String[] FieldName = {ARFieldName1,ARFieldName2,ARFieldName3,ARFieldName4,ARFieldName5,ARFieldName6,ARFieldName7,ARFieldName8,ARFieldName9},
			Value = {ARValue1,ARValue2,ARValue3,ARValue4,ARValue5,ARValue6,ARValue7,ARValue8,ARValue9};
	String headerName;
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create clone user");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create clone user",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
			}
			ThreadSleep(3000);
			for(int i = 0 ; i <FieldName.length; i++) {
				if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), FieldName[i], Value[i], 10))
				{
					log(LogStatus.INFO, "Changed the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "Not able to change the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
					sa.assertTrue(false, "Not able to changed the value of " + FieldName[i] + " for Acuity Setting");	
				}
				ThreadSleep(5000);
			}	
			ThreadSleep(5000);
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		} else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot change value");
		}
		lp.CRMlogout();
		sa.assertAll();
		ThreadSleep(2000);
		lp.CRMLogin(glUser1EmailID, adminPassword, appName);
		ThreadSleep(2000);
			for(String searchValue : searchValues) {
				String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValue, excelLabel.Variable_Name);
				log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
					ThreadSleep(2000);
					clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
					ThreadSleep(8000);
					clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
					ThreadSleep(2000);
				String ele = rp.getResearchFindingsValue(10).getText();
					if (ele.equals(searchValue)) {
					log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
					sa.assertTrue(true, ele +" is matched with " +searchValue);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to send value "+searchValue);
				}
				}
				log(LogStatus.INFO,
						"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
								+ searchValue + "---------",
						YesNo.No);
				try {
					if(rp.getNoResult(5).getText() != null){
						log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
						sa.assertTrue(true, "There is no data retaled to " + searchValue);
					} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
						}
			
					} else {
						log(LogStatus.FAIL,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
						sa.assertTrue(false,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------");
						
				}
			}
				catch(Exception e)
				{
					log(LogStatus.INFO,e.getMessage(), YesNo.No);
					continue;

				}
					if (rp.mouseHoverOnNavigationAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					
					if (rp.mouseHoverOnGridAndGetText()) {
						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
					}
					int gridSize = rp.getElementsFromGrid().size();
					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
					for(int i=0; i<gridSize; i++)
					{		
						headerName = rp.getElementsFromGrid().get(i).getText();
						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
						
						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
						}
						if (rp.VerifyViewMoreOption(headerName)) {
							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
						} else {
							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
						}
					}
				}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
@Test
	public void ARTc049_AfterBulkDataUpload_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
	
	String[] searchValues = {AR_Firm44,AR_Firm45,AR_Firm46,AR_Firm47,AR_Firm48,AR_Firm49,AR_Firm50,AR_Firm51};
	String ele, headerName,errorName1 = "No results for";
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
		for(String searchValue : searchValues) {
			String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
			log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
				ThreadSleep(2000);
				ele = rp.getResearchFindingsValue(10).getText();
				if (ele.equals(searchValue)) {
				log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
				sa.assertTrue(true, ele +" is matched with " +searchValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to send value "+searchValue);
			}
			}
			log(LogStatus.INFO,
					"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
							+ searchValue + "---------",
					YesNo.No);
			ele = rp.getNoResult(10).getText();
			if(ele.contains(errorName1)){
				log(LogStatus.PASS, ele +" has been Matched with " +errorName1, YesNo.No);
				sa.assertTrue(true, ele +" has been Matched with " +errorName1);
			} else {
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
					}
		
				} else {
					log(LogStatus.FAIL,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
					sa.assertTrue(false,
							"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------");
					
			}
		}
				if (rp.mouseHoverOnNavigationAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				
				if (rp.mouseHoverOnGridAndGetText()) {
					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
				}
				int gridSize = rp.getElementsFromGrid().size();
				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
				for(int i=0; i<gridSize; i++)
				{		
					headerName = rp.getElementsFromGrid().get(i).getText();
					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
					
					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
					}
					if (rp.VerifyViewMoreOption(headerName)) {
						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
					} else {
						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
					}
				}
			}
	lp.CRMlogout();
	sa.assertAll();
}

}