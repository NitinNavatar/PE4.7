package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.ExcelUtils.readAllDataForAColumn;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.ResearchPageBusinessLayer;
import com.navatar.pageObjects.SDGPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class AcuityResearch extends BaseLib{
	
	String passwordResetLink = null;
	String navigationMenuName=NavigationMenuItems.Research.toString();
	String filesName = "Enter one or more research terms";
	String customNavigationMenu = "Custom Navigation Menu";

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
							glUserProfile)) {
						log(LogStatus.INFO, "GL User is created Successfully: " + glUser1FirstName + " " + UserLastName, YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User1",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",
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
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
		lp.CRMLogin(glUser1EmailID, adminPassword);
		String xpath;
		String ele;

		// Verification on navigation menu
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, rp.getTextAreaResearch(20),"Research Text Area", action.BOOLEAN)) {
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
			if(clickUsingJavaScript(driver, rp.getResearchMinimize(20),"Research Minimize Button", action.BOOLEAN)) {
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
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
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
		if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			ele = rp.getResearchFindings(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			sa.assertTrue(true, ele +" is matched with " +searchValue);
			}
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele!=null && ele.equalsIgnoreCase("Research Findings")) {
			log(LogStatus.PASS, ele +" is visible", YesNo.Yes);
			sa.assertTrue(true, ele +" is visible");
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
	public void ARTc005_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	
	String[] searchValues = readAllDataForAColumn(ResearchDataSheetFilePath, "SearchData" , 1,false).split("<break>");
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(AcuityDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValue, excelLabel.Variable_Name);
		
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
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
			String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
			
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
	
	String[] searchValues = readAllDataForAColumn(ResearchDataSheetFilePath, "SearchData" , 1,false).split("<break>");
	
	
	for(String searchValue : searchValues) {
		
		String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValue, excelLabel.Variable_Name);
		
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
				ThreadSleep(8000);
				ele = rp.getResearchFindingsValue(10).getText();
				if (ele.equals(searchValue)) {
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
		list =	rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);

		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------list:"+list);
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
			String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
			
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
	 
		 lp.CRMLogin(superAdminUserName, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
	
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm1, 30)) {
	           if (ip.UpdateLegalNameAccount(projectName, updatedname, 10)) {
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
	lp.CRMlogout();
	sa.assertAll();
	ThreadSleep(5000);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),'"' + updatedname + '"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
				ThreadSleep(5000);
			}
	   }
	   
	   int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
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
	public void ARTc008_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	
	String[] searchValues = {AR_Firm2};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
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
				String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
				
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
	 
		 lp.CRMLogin(superAdminUserName, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.ContactTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
	
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm2, 30)) {
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
	lp.CRMlogout();
	sa.assertAll();
	ThreadSleep(5000);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),'"' + updatedname + '"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			}
	   }
	    int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
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
	public void ARTc010_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	
	String[] searchValues = {AR_Firm3};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
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
				String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
				
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
	 
		 lp.CRMLogin(superAdminUserName, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.DealTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.DealTab, YesNo.No);
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm3, 30)) {
	           if (dp.UpdateOtherLable(projectName, labellabels, updatedname, 20)) {
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
	lp.CRMlogout();
	sa.assertAll();
	ThreadSleep(5000);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),'"' + updatedname + '"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			}
	   }
	    int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
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
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	
	String[] searchValues = {AR_Firm4};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
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
				String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
				
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
    FundsPageBusinessLayer dp = new FundsPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	
	 String updatedname = "CompanyFund NSAdmin Record07 - Updated";
	 
		 lp.CRMLogin(superAdminUserName, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.Object3Tab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.Object3Tab, YesNo.No);
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm4, 30)) {
	           if (dp.UpdateDealName(projectName, updatedname, 20)) {
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
	lp.CRMlogout();
	sa.assertAll();
	ThreadSleep(5000);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),'"' + updatedname +'"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			}
	   }
	    int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
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
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	
	String[] searchValues = {AR_Firm5};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
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
				String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
				
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
	 
		 lp.CRMLogin(superAdminUserName, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm5, 30)) {
	           if (frp.UpdateFundRaisingName(projectName, updatedname, 20)) {
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
	lp.CRMlogout();
	sa.assertAll();
	ThreadSleep(5000);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),'"' + updatedname + '"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			}
	   }
	    int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
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
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	
	String[] searchValues = {AR_Firm6};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
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
				String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
				
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
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm6, 30)) {
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
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),'"' + updatedname + '"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			}
	   }
	    int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
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
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	String ele, headerName;
	
	String[] searchValues = {AR_Firm7};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
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
				String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
				
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
	 
		 lp.CRMLogin(superAdminUserName, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.TaskTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.TaskTab, YesNo.No);
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm7, 30)) {
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
	lp.CRMlogout();
	sa.assertAll();
	ThreadSleep(5000);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeysAndPressEnter(driver, rp.getTextAreaResearch(10),'"' + updatedname + '"', "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Button", action.BOOLEAN);
			}
	   }
	    int gridSize = rp.getElementsFromGrid().size();
		log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
		for(int i=0; i<gridSize; i++)
		{
			   String headerName = rp.getElementsFromGrid().get(i).getText();
			   System.out.println("Hedader Name : "  + headerName);
			   String recordName = rp.clickOnRecordUsingGridName(headerName, 30).getText();
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
	public void ARTc020_CreateCustomFields(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String RecordTypeList = AR_RecordType1;
	String RecordTypeArray[] = RecordTypeList.split(breakSP,-1);
	String recordTypeDescription = "Description Record Type";

	String[][][] RecordType = {
			{ { recordTypeLabel.Record_Type_Label.toString(), RecordTypeArray[0] },
					{ recordTypeLabel.Description.toString(), RecordTypeArray[0] + recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), RecordTypeArray[1] },
					{ recordTypeLabel.Description.toString(), RecordTypeArray[1] + recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } } };
	
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
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Contact)) {
					if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Contact,
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
										profileForSelection, isMakeDefault, null, 10);
							}
						}
						if (flag) {
							log(LogStatus.INFO, "Created Record Type : " + RecordType[i], YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not Able to Create Record Type : " + RecordType[i],
									YesNo.Yes);
							sa.assertTrue(false, "Not Able to Create Record Type : " + RecordType[i]);
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
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

}