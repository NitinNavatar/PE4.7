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
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.APIUtils;
import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.EnumConstants.AppSetting;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PermissionType;
import com.navatar.generic.EnumConstants.ProgressType;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.recordTypeLabel;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ClipPageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.ResearchPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.navatar.pageObjects.ThemePageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class AcuityResearch extends BaseLib{
	
	String navigationMenuName=NavigationMenuItems.Research.toString().replace("_", " ");
	
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
	public void ARTc002_1_UpdateFirmRecordTypesAsActive(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword,appName);
	
	String RecordTypeList = AR_OtherLabelName1;
	String RecordTypeArray[] = RecordTypeList.split(breakSP, -1);
	
	String[][] RecordType = { { recordTypeLabel.Active.toString(), "Checked" }};
	String parentID=null;
	String value = "Not Checked";
	
	
	for (int i = 0; i < RecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Firm)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Firm,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(RecordTypeArray[i])) {
							switchToFrame(driver, 60, sp.getSetUpPageIframe(20));
							if(sp.getRecordTypeLabelWithoutEditMode(projectName, recordTypeLabel.Active.toString(),value, 10) != null) {
								log(LogStatus.ERROR,"Active field is not checked",YesNo.Yes);
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.ERROR,RecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,RecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, RecordTypeArray[i]+" not updated ");
							}
							}
							else
							{
								log(LogStatus.ERROR,RecordTypeArray[i]+" is already updated ",YesNo.Yes);
							}
						
						}else {
							log(LogStatus.ERROR, RecordTypeArray[i]+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, RecordTypeArray[i]+" is not clickable");
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
	lp.switchToLighting();
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
	public void ARTc002_2_SelectingRecordTypesForProfiles(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp=new ResearchPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword,appName);
	
	String recordTypes = "Account";
	String avail[] = {"Private Equity", "Limited Partner"};
	String[] profileForSelection = { "PE Standard User", "System Administrator"};
	String parentID=null;
	for(int k=0; k<profileForSelection.length; k++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles.toString())) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
					ThreadSleep(2000);
					if (clickUsingJavaScript(driver, rp.getProfileSelected(profileForSelection[k],10), profileForSelection[k].toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + profileForSelection[k], YesNo.No);
						ThreadSleep(2000);
						for(int i=0; i <avail.length; i++) {
						switchToDefaultContent(driver);
						ThreadSleep(2000);
						switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
						ThreadSleep(2000);
						if (click(driver, rp.getEditButtonForRecordTypes(recordTypes, 10), "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
							ThreadSleep(2000);
							if (selectVisibleTextFromDropDown(driver, sp.getavailableRecordType(10),
									"Selected Tab List", avail[i])) {
								appLog.info(recordTypes + " is selected successfully in Available tabs");
								if (click(driver, sp.getAddBtn(10), "Custom Tab Add Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on Add button");
								} else {
									appLog.error("Not able to click on Remove button so cannot add custom tabs");
								}
							} else {
								appLog.error(avail[i] + " record type is not Available list Tab.");
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
						log(LogStatus.ERROR, profileForSelection[k]+" profile is not clickable", YesNo.Yes);
						sa.assertTrue(false,profileForSelection[k]+" profile is not clickable");
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
	}
	switchToDefaultContent(driver);
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}
	
	@Parameters({ "projectName"})
	@Test
	public void ARTc002_3_VerifyTheNavigationMenuItems(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(glUser1EmailID, adminPassword,appName);

		// Verification on navigation menu
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, rp.getTextAreaResearch(10),"Research Text Area", action.BOOLEAN)) {
				log(LogStatus.INFO, "items verified "+bp.filesName+" on "+navigationMenuName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "items not verified "+bp.filesName+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"items not verified "+bp.filesName+" on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName);
		}
		refresh(driver);
		
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN)) {
				log(LogStatus.INFO, "items verified "+bp.filesName+" on "+navigationMenuName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "items not verified "+bp.filesName+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"items not verified "+bp.filesName+" on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName);
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
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName);
		}
		refresh(driver);
		
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			
			if (clickUsingJavaScript(driver, rp.getResearchPopOut(10),"Research Pop out Button", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on pop-out button", YesNo.No);
				ThreadSleep(5000);
				String parentWindow=switchOnWindow(driver);
				ThreadSleep(2000);
				if (clickUsingJavaScript(driver, rp.getResearchPopIn(10),"Research Pop In Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on pop-In closed button", YesNo.No);
				} else {
					log(LogStatus.ERROR, "not Able to Click on pop-In closed button", YesNo.Yes);
					sa.assertTrue(false,"NotAble to Click on pop-In closed button");
				}
				driver.switchTo().window(parentWindow);
				ThreadSleep(2000);
				
				if (rp.getResearchPopOut(10)!=null) {
					log(LogStatus.INFO, "Research popup successfully pop-out closed", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Research popup not successfully closed", YesNo.Yes);
					sa.assertTrue(false,"Research popup not successfully closed");
				}
				
			} else {
				log(LogStatus.ERROR, "not Able to Click on pop-out  button", YesNo.Yes);
				sa.assertTrue(false,"NotAble to Click on pop-out  button");
			}
			
			
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName);
		}
		lp.CRMlogout();
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
								if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Panel_Height.toString(), action.BOOLEAN, 10),"300",PageLabel.Panel_Height.toString()+" textbox value : 340",action.BOOLEAN)) {
									ThreadSleep(2000);
									log(LogStatus.INFO,"send value to "+PageLabel.Label.toString()+" textbox value : 340",YesNo.No);
									if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Panel_Width.toString(), action.BOOLEAN, 10),"340",PageLabel.Panel_Width.toString()+" textbox value : 300",action.BOOLEAN)) {
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
										sa.assertTrue(false, "Not Able to send value to "+PageLabel.Panel_Width.toString()+" textbox value : 340");
										log(LogStatus.FAIL,"Not Able to send value to "+PageLabel.Panel_Width.toString()+" textbox value : 340",YesNo.Yes);
									}
								} else {
									sa.assertTrue(false, "Not Able to send value to "+PageLabel.Panel_Height.toString()+" textbox value : 300");
									log(LogStatus.FAIL,"Not Able to send value to "+PageLabel.Panel_Height.toString()+" textbox value : 300",YesNo.Yes);
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
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
	String xpath,ele;
	int i = 1;
	String searchValues[] = {"","a","zz","1234567890~!@#$%^&*()_+-=[]{}?|;':,.<>/"};
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
			if (ele.equals(searchValue) || searchValue == null || searchValue == "") {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			}
			ele = rp.getResearchFindings(10).getText();
			if (ele!=null && ele.equalsIgnoreCase("Search Results")) {
			log(LogStatus.PASS, ele +" is visible", YesNo.Yes);
			}
				ele = rp.getNoResult(10).getText();
				if(ele.contains(bp.errorName1)){
					log(LogStatus.PASS, ele +" has been Matched with " +bp.errorName1, YesNo.No);
				} else {
					log(LogStatus.ERROR, ele +" is not Matched with " +bp.errorName1, YesNo.Yes);
					sa.assertTrue(false, ele +" is not Matched with " +bp.errorName1);
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
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword,appName);
	
	String contactRecordTypeList = AR_ContactRecordType1;
	String dealRecordTypeList = AR_DealRecordType1;
	String fundRecordTypeList = AR_FundRecordType1;
	String fundraisingRecordTypeList = AR_FundraisingRecordType1;
	String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP,-1);
	String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);
	String fundRecordTypeArray[] = fundRecordTypeList.split(breakSP, -1);
	String fundraisingRecordTypeArray[] = fundraisingRecordTypeList.split(breakSP, -1);

	String[][] RecordType = { { recordTypeLabel.Active.toString(), "Checked" }};
	
	String[][][] contactrecordType = {
			{ { recordTypeLabel.Record_Type_Label.toString(), contactRecordTypeArray[0] },
					{ recordTypeLabel.Description.toString(), contactRecordTypeArray[0] + bp.recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), contactRecordTypeArray[1] },
					{ recordTypeLabel.Description.toString(), contactRecordTypeArray[1] + bp.recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } } };
	
	String[][][] dealRecordType = {
			{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[0] },
					{ recordTypeLabel.Description.toString(), dealRecordTypeArray[0] + bp.recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[1] },
					{ recordTypeLabel.Description.toString(), dealRecordTypeArray[1] + bp.recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[2] },
						{ recordTypeLabel.Description.toString(), dealRecordTypeArray[2] + bp.recordTypeDescription },
						{ recordTypeLabel.Active.toString(), "" } }};

	String[][][] fundrecordType = {
			{ { recordTypeLabel.Record_Type_Label.toString(), fundRecordTypeArray[0] },
					{ recordTypeLabel.Description.toString(), fundRecordTypeArray[0] + bp.recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), fundRecordTypeArray[1] },
					{ recordTypeLabel.Description.toString(), fundRecordTypeArray[1] + bp.recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } } };

	String[][][] fundraisingrecordType = {
			{ { recordTypeLabel.Record_Type_Label.toString(), fundraisingRecordTypeArray[0] },
					{ recordTypeLabel.Description.toString(),
							fundraisingRecordTypeArray[0] + bp.recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), fundraisingRecordTypeArray[1] },
					{ recordTypeLabel.Description.toString(),
							fundraisingRecordTypeArray[1] + bp.recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } } };

	
	String[] profileForSelection = { "System Administrator","PE Standard User"};
	boolean isMakeAvailable = false;
	boolean isMakeDefault = false;
	boolean flag = false;
	String parentID=null;
//	object.Contact,object.Institution,object.Fund,object.Fundraising,object.Deal,object.Theme,object.Clip
	
	object[] objectsName = {object.Contact,object.Institution,object.Fund,object.Fundraising,object.Deal,object.Theme,object.Clip} ;
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
				log(LogStatus.PASS, "field not added/already present 1", YesNo.Yes);
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

	@Test
	public void ARTc004_1_CreateAccountByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Account.xlsx";
		String sheetName="Account";
		new APIUtils().AccountObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_2_CreateContactByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Contacts.xlsx";
		String sheetName="Contacts";
		new APIUtils().ContactObjectDataUpload(filePath, sheetName);
		
	}

	@Test
	public void ARTc004_3_CreateFundByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\\\API Files\\\\Acuity Research\\Fund.xlsx";
		String sheetName="Fund";
		new APIUtils().FundObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_3_CreateDealByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Deal.xlsx";
		String sheetName="Deal";
		new APIUtils().DealObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_4_CreateDealTeamByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Deal Team.xlsx";
		String sheetName="Deal Team";
		new APIUtils().DealTeamObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_5_CreateTargetByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Fundraising.xlsx";
		String sheetName="Fundraising";
		new APIUtils().TargetObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_6_CreateTargetContactRoleByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Fundraising Contact.xlsx";
		String sheetName="Fundraising Contact";
		new APIUtils().TargetContactRoleObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_7_CreateFinancingByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Financing.xlsx";
		String sheetName="Financing";
		new APIUtils().DealObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_8_CreateThemeByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Theme.xlsx";
		String sheetName="Theme";
		new APIUtils().ThemeObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_9_CreateThemeRelationByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Theme Relation.xlsx";
		String sheetName="Theme Relation";
		new APIUtils().ThemeRelationObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_a1_CreateClipByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Clip.xlsx";
		String sheetName="Clip";
		new APIUtils().ClipObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_a2_CreateClipRelationByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Clip Relation.xlsx";
		String sheetName="Clip Relation";
		new APIUtils().ClipRelationObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_a3_CreateTaskByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Task.xlsx";
		String sheetName="Task";
		new APIUtils().TaskObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_a4_CreateTaskRelationByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Task Relationship.xlsx";
		String sheetName="Task Relationship";
		new APIUtils().TaskRelationshipObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_a5_CreateEventByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Event.xlsx";
		String sheetName="Event";
		new APIUtils().EventObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void ARTc004_a6_CreateEventRelationByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Research\\Event Relationship Contact.xlsx";
		String sheetName="Event Relationship Contact";
		new APIUtils().EventRelationshipObjectDataUpload(filePath, sheetName);
		
	}
	
@Parameters({ "projectName"})
@Test
	public void ARTc005_1_DeselectingRecordTypesForProfiles(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp=new ResearchPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword,appName);
	
	String recordTypes [] = {"Deal","Fund","Fundraising"};
	String avail[][] = {{"SellSide Deal","BuySide Deal", "Capital Raise"},{"Mutual Fund","Trust Fund"},{"FRGRT","MSGRT"}};
	String[] profileForSelection = {"System Administrator"};
	String parentID=null;
	String master= "--Master--";
	for(int k=0; k<profileForSelection.length; k++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
					ThreadSleep(2000);
					if (clickUsingJavaScript(driver, rp.getProfileSelected(profileForSelection[k],10), profileForSelection[k].toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + profileForSelection[k], YesNo.No);
						ThreadSleep(5000);
						for(int i=0; i <3; i++) {
							System.out.println(avail[i].length);
						switchToDefaultContent(driver);
						refresh(driver);
						ThreadSleep(5000);
						switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
						ThreadSleep(2000);
						if (click(driver, rp.getEditButtonForRecordTypes(recordTypes[i], 10), "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
							ThreadSleep(2000);
						for(int j = 0; j <avail[i].length; j++) {
							if (selectVisibleTextFromDropDown(driver, sp.getSelectedRecordType(10),
									"Selected Tab List", avail[i][j])) {
								appLog.info(recordTypes + " is selected successfully in Selected tabs");
								if (click(driver, sp.getRemoveBtn(10), "Custom Tab Remove Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on Remove button");
								} else {
									appLog.error("Not able to click on Remove button so cannot add custom tabs");
								}
							} else {
								appLog.error(recordTypes + " record type is not Selected list Tab.");
								sa.assertTrue(false,recordTypes + " record type is not Selected list Tab.");
							}
							
						}
								if (selectVisibleTextFromDropDown(driver, sp.getavailableRecordType(10),
										"Available Tab List", master)) {
									appLog.info(recordTypes + " is selected successfully in Available tabs");
									if (click(driver, sp.getAddBtn(10), "Custom Tab Add Button",
											action.SCROLLANDBOOLEAN)) {
										appLog.error("clicked on add button");
									} else {
										appLog.error("Not able to click on add button so cannot add custom tabs");
									}
								} else {
									appLog.error(recordTypes + " record type is not Selected list Tab.");
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
						log(LogStatus.ERROR, profileForSelection[k]+" profile is not clickable", YesNo.Yes);
						sa.assertTrue(false,profileForSelection[k]+" profile is not clickable");
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
	}
	switchToDefaultContent(driver);
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
	public void ARTc005_2_UpdateRecordTypesAsInactive(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword,appName);
	
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
							if(sp.getRecordTypeLabelWithoutEditMode(projectName, RecordType[0][0], RecordType[0][1], 10) == null) {
								log(LogStatus.PASS,"Record Type need to update ",YesNo.Yes);
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.PASS,dealRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,dealRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, dealRecordTypeArray[i]+" not updated ");
							}
						} else {
							log(LogStatus.ERROR,"Record Type is already updated ",YesNo.Yes);
							sa.assertTrue(false, "Record Type is already updated ");
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
							if(sp.getRecordTypeLabelWithoutEditMode(projectName, RecordType[0][0], RecordType[0][1], 10) == null) {
								log(LogStatus.PASS,"Record Type need to update ",YesNo.Yes);
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.ERROR,fundRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,fundRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, fundRecordTypeArray[i]+" not updated ");
							}
							} else {
								log(LogStatus.ERROR,"Record Type is already updated ",YesNo.Yes);
								sa.assertTrue(false, "Record Type is already updated ");
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
							if(sp.getRecordTypeLabelWithoutEditMode(projectName, RecordType[0][0], RecordType[0][1], 10) == null) {
								log(LogStatus.PASS,"Record Type need to update ",YesNo.Yes);
							if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
								log(LogStatus.ERROR,fundraisingRecordTypeArray[i]+" has been updated ",YesNo.Yes);	
							}else {
								log(LogStatus.ERROR,fundraisingRecordTypeArray[i]+" not updated ",YesNo.Yes);
								sa.assertTrue(false, fundraisingRecordTypeArray[i]+" not updated ");
							}
							} else {
								log(LogStatus.ERROR,"Record Type is already updated ",YesNo.Yes);
								sa.assertTrue(false, "Record Type is already updated ");
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
	public void ARTc006_1_VerifyResearchFunctionalityForNSADMIN(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	
	String[] searchValues = {AR_Data1};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(4000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
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
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
					ExcelUtils.writeData(ResearchDataSheetFilePath, "Pass", "SearchData", excelLabel.Variable_Name,
	   						searchValue, excelLabel.Status);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
					ExcelUtils.writeData(ResearchDataSheetFilePath, "Variable: "+ searchValue + "||" + "list : "+list, "SearchData", excelLabel.Variable_Name,
	   						searchValue, excelLabel.Status);
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
	public void ARTc006_2_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	ArrayList<String> list=new ArrayList<>();
	
	String[] searchValues = readAllDataForAColumn(ResearchDataSheetFilePath, "SearchData" , 2,false).split("<break>");
	
//	for(String searchValue : searchValues) {
		for(int i =0; i <=30; i++) {
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValues[i], excelLabel.Variable_Name);
		
		log(LogStatus.PASS, "WOrking for " + searchValues[i], YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 5)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(5),searchValues[i], "Input", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(4000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(5),"Research Minimize Button", action.BOOLEAN);
			ThreadSleep(2000);
	} else {
		log(LogStatus.ERROR, "Not Able to send value "+searchValues[i], YesNo.Yes);
		sa.assertTrue(false,"Not Able to send value "+searchValues[i]);
	}
}
	log(LogStatus.INFO,
			"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
					+ searchValues[i] + "---------",
			YesNo.No);
	try{
	if(rp.getNoResult(5) != null){
		log(LogStatus.PASS, "There is no data retaled to " + searchValues[i], YesNo.No);
	} else 
		if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValues[i])) {
			log(LogStatus.INFO,
					"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValues[i] + "---------",
					YesNo.No);
		
		} else {
			log(LogStatus.FAIL,
					"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValues[i] + "---------",
					YesNo.No);
			sa.assertTrue(false,
					"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValues[i] + "---------");
			
	}
		list=	rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 5);

		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
			ExcelUtils.writeData(ResearchDataSheetFilePath, "Pass", "SearchData", excelLabel.Variable_Name,
						searchValues[i], excelLabel.Status);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list);
			ExcelUtils.writeData(ResearchDataSheetFilePath, "Variable: "+ searchValues[i] + "||" + "list : "+list, "SearchData", excelLabel.Variable_Name,
						searchValues[i], excelLabel.Status);
		}
		
	}
	catch(Exception e)
	{
		log(LogStatus.INFO,e.getMessage(), YesNo.No);
		continue;

	}
	refresh(driver);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc006_3_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	ArrayList<String> list=new ArrayList<>();
	
	String[] searchValues = readAllDataForAColumn(ResearchDataSheetFilePath, "SearchData" , 2,false).split("<break>");
	
//	for(String searchValue : searchValues) {
		for(int i =31; i <=60; i++) {
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValues[i], excelLabel.Variable_Name);
		
		log(LogStatus.PASS, "WOrking for " + searchValues[i], YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 5)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(5),searchValues[i], "Input", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(4000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(5),"Research Minimize Button", action.BOOLEAN);
			ThreadSleep(2000);
	} else {
		log(LogStatus.ERROR, "Not Able to send value "+searchValues[i], YesNo.Yes);
		sa.assertTrue(false,"Not Able to send value "+searchValues[i]);
	}
}
	log(LogStatus.INFO,
			"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
					+ searchValues[i] + "---------",
			YesNo.No);
	try{
		refresh(driver);
	if(rp.getNoResult(5) != null){
		log(LogStatus.PASS, "There is no data retaled to " + searchValues[i], YesNo.No);
	} else 
		if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValues[i])) {
			log(LogStatus.INFO,
					"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValues[i] + "---------",
					YesNo.No);
		
		} else {
			log(LogStatus.FAIL,
					"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValues[i] + "---------",
					YesNo.No);
			sa.assertTrue(false,
					"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValues[i] + "---------");
			
	}
		list=	rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 5);

		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
			ExcelUtils.writeData(ResearchDataSheetFilePath, "Pass", "SearchData", excelLabel.Variable_Name,
						searchValues[i], excelLabel.Status);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list);
			ExcelUtils.writeData(ResearchDataSheetFilePath, "Variable: "+ searchValues[i] + "||" + "list : "+list, "SearchData", excelLabel.Variable_Name,
						searchValues[i], excelLabel.Status);
		}
		
	}
	catch(Exception e)
	{
		log(LogStatus.INFO,e.getMessage(), YesNo.No);
		continue;

	}
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc006_4_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	ArrayList<String> list=new ArrayList<>();
	
	String[] searchValues = readAllDataForAColumn(ResearchDataSheetFilePath, "SearchData" , 2,false).split("<break>");
	
//	for(String searchValue : searchValues) {
		for(int i =61; i <=90; i++) {
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValues[i], excelLabel.Variable_Name);
		
		log(LogStatus.PASS, "WOrking for " + searchValues[i], YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 5)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(5),searchValues[i], "Input", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(4000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(5),"Research Minimize Button", action.BOOLEAN);
			ThreadSleep(2000);
	} else {
		log(LogStatus.ERROR, "Not Able to send value "+searchValues[i], YesNo.Yes);
		sa.assertTrue(false,"Not Able to send value "+searchValues[i]);
	}
}
	log(LogStatus.INFO,
			"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
					+ searchValues[i] + "---------",
			YesNo.No);
	try{
		refresh(driver);
	if(rp.getNoResult(5) != null){
		log(LogStatus.PASS, "There is no data retaled to " + searchValues[i], YesNo.No);
	} else 
		if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValues[i])) {
			log(LogStatus.INFO,
					"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValues[i] + "---------",
					YesNo.No);
		
		} else {
			log(LogStatus.FAIL,
					"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValues[i] + "---------",
					YesNo.No);
			sa.assertTrue(false,
					"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValues[i] + "---------");
			
	}
		list=	rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 5);

		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
			ExcelUtils.writeData(ResearchDataSheetFilePath, "Pass", "SearchData", excelLabel.Variable_Name,
						searchValues[i], excelLabel.Status);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list);
			ExcelUtils.writeData(ResearchDataSheetFilePath, "Variable: "+ searchValues[i] + "||" + "list : "+list, "SearchData", excelLabel.Variable_Name,
						searchValues[i], excelLabel.Status);
		}
		
	}
	catch(Exception e)
	{
		log(LogStatus.INFO,e.getMessage(), YesNo.No);
		continue;

	}
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc006_5_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	ThreadSleep(2000);
	ArrayList<String> list=new ArrayList<>();
	
	String[] searchValues = readAllDataForAColumn(ResearchDataSheetFilePath, "SearchData" , 2,false).split("<break>");
	
//	for(String searchValue : searchValues) {
		for(int i =91; i <=searchValues.length-1; i++) {
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"SearchData",excelLabel.ResearchFindings, searchValues[i], excelLabel.Variable_Name);
		
		log(LogStatus.PASS, "WOrking for " + searchValues[i], YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 5)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(5),searchValues[i], "Input", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(4000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(5),"Research Minimize Button", action.BOOLEAN);
			ThreadSleep(2000);
	} else {
		log(LogStatus.ERROR, "Not Able to send value "+searchValues[i], YesNo.Yes);
		sa.assertTrue(false,"Not Able to send value "+searchValues[i]);
	}
}
	log(LogStatus.INFO,
			"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
					+ searchValues[i] + "---------",
			YesNo.No);
	try{
		refresh(driver);
	if(rp.getNoResult(5) != null){
		log(LogStatus.PASS, "There is no data retaled to " + searchValues[i], YesNo.No);
	} else 
		if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValues[i])) {
			log(LogStatus.INFO,
					"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValues[i] + "---------",
					YesNo.No);
		
		} else {
			log(LogStatus.FAIL,
					"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValues[i] + "---------",
					YesNo.No);
			sa.assertTrue(false,
					"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
							+ searchValues[i] + "---------");
			
	}
		list=	rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 5);

		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
			ExcelUtils.writeData(ResearchDataSheetFilePath, "Pass", "SearchData", excelLabel.Variable_Name,
						searchValues[i], excelLabel.Status);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list);
			ExcelUtils.writeData(ResearchDataSheetFilePath, "Variable: "+ searchValues[i] + "||" + "list : "+list, "SearchData", excelLabel.Variable_Name,
						searchValues[i], excelLabel.Status);
		}
		
	}
	catch(Exception e)
	{
		log(LogStatus.INFO,e.getMessage(), YesNo.No);
		continue;

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
	 
		 lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
	
	      if (fp.clickOnAlreadyCreatedItem(projectName, TabName.InstituitonsTab, AR_Firm1.replace("  ", "").replace("\"", ""), 10)) {
	           if (ip.UpdateLegalNameAccount(projectName, AR_Research1.replace("  ", "").replace("\"", ""), 5)) {
	               log(LogStatus.INFO, "successfully update legal name " + AR_Research1, YesNo.Yes);
	           } else {
	               sa.assertTrue(false, "not able to update legal name " + AR_Research1);
	               log(LogStatus.SKIP, "not able to update legal name " + AR_Research1, YesNo.Yes);
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
	refresh(driver);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 5)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(5), AR_Research1, "Research Input Field", action.BOOLEAN)){
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
		
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, AR_Research1, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 5);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research1 + "||" + "list : "+list, YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research1 + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research1 + "||" + "list : "+list);
		}
	   
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName"})
@Test
	public void ARTc008_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
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
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//			if (rp.mouseHoverOnNavigationAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			
//			if (rp.mouseHoverOnGridAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			int gridSize = rp.getElementsFromGrid().size();
//			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//			for(int i=0; i<gridSize; i++)
//			{		
//				headerName = rp.getElementsFromGrid().get(i).getText();
//				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//				
//				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//				}
//				if (rp.VerifyViewMoreOption(headerName)) {
//					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//				}
//			}
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
	 
		 lp.CRMLogin(glUser1EmailID, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.ContactTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
	
	      if (fp.clickOnAlreadyCreatedItem(projectName, TabName.ContactsTab, AR_Firm2.replace("  ", "").replace("\"", ""), 10)) {
	           if (cp.UpdateLastName(projectName, PageName.ContactPage,AR_Research2.replace("  ", "").replace("\"", ""))) {
	               log(LogStatus.INFO, "successfully update contact name " + AR_Research2, YesNo.Yes);
	           } else {
	               sa.assertTrue(false, "not able to update contact name " + AR_Research2);
	               log(LogStatus.SKIP, "not able to update contact name " + AR_Research2, YesNo.Yes);
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
	refresh(driver);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),AR_Research2, "Research Input Field", action.BOOLEAN)){
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
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, AR_Research2, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research2 + "||" + "list : "+list, YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research2 + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research2 + "||" + "list : "+list);
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
		if(rp.getNoResult(5) != null){
			log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
		} else 
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
	 
		 lp.CRMLogin(glUser1EmailID, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.DealTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.DealTab, YesNo.No);
	      if (fp.clickOnAlreadyCreatedItem(projectName, TabName.DealTab, AR_Firm3.replace("  ", "").replace("\"", ""), 10)) {
	           if (dp.UpdateOtherLabel(projectName, PageLabel.Pipeline_Name.toString(), AR_Research3.replace("  ", "").replace("\"", ""), 10)) {
	               log(LogStatus.INFO, "successfully update contact name " + AR_Research3, YesNo.Yes);
	           } else {
	               sa.assertTrue(false, "not able to update deal name " + AR_Research3);
	               log(LogStatus.SKIP, "not able to update deal name " + AR_Research3, YesNo.Yes);
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
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),AR_Research3, "Research Input Field", action.BOOLEAN)){
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
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, AR_Research3, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research3 + "||" + "list : "+list, YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research3 + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research3 + "||" + "list : "+list);
		}
		
		lp.CRMlogout();
		sa.assertAll();
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
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
	 
		 lp.CRMLogin(superAdminUserName, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.FundsTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.FundsTab, YesNo.No);
	      if (fp.clickOnAlreadyCreatedItem(projectName, TabName.FundsTab, AR_Firm4.replace("  ", "").replace("\"", ""), 10)) {
	           if (fp.UpdateFundName(projectName, AR_Research4.replace("  ", "").replace("\"", ""), 10)) {
	               log(LogStatus.INFO, "successfully update contact name " + AR_Research4, YesNo.Yes);
	           } else {
	               sa.assertTrue(false, "not able to update deal name " + AR_Research4);
	               log(LogStatus.SKIP, "not able to update deal name " + AR_Research4, YesNo.Yes);
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
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),AR_Research4, "Research Input Field", action.BOOLEAN)){
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
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, AR_Research4, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research4 + "||" + "list : "+list, YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research4 + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research4 + "||" + "list : "+list);
		}  
		lp.CRMlogout();
		sa.assertAll();
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
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
	 
		 lp.CRMLogin(glUser1EmailID, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
	      if (fp.clickOnAlreadyCreatedItem(projectName, AR_Firm5.replace("  ", "").replace("\"", ""), 10)) {
	           if (frp.UpdateFundRaisingName(projectName, AR_Research5.replace("  ", "").replace("\"", ""), 10)) {
	               log(LogStatus.INFO, "successfully update Fundraising name " + AR_Research5, YesNo.Yes);
	           } else {
	               sa.assertTrue(false, "not able to update Fundraising name " + AR_Research5);
	               log(LogStatus.SKIP, "not able to update Fundraising name " + AR_Research5, YesNo.Yes);
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
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),AR_Research5, "Research Input Field", action.BOOLEAN)){
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
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, AR_Research5, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research5 + "||" + "list : "+list, YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research5 + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research5 + "||" + "list : "+list);
		}  
		lp.CRMlogout();
		sa.assertAll();
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
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
    HomePageBusineesLayer home = new HomePageBusineesLayer(driver); 
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
    TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	
	 String[][] task1BasicSection = { { excelLabel.Subject.toString(), AR_Research6.replace("\"", "")} };
		 lp.CRMLogin(superAdminUserName, adminPassword, appName);
   
		 if (home.globalSearchAndNavigate(AR_Firm6.replace("   ", "").replace("\"", ""), RelatedTab.Tasks.toString(), false, projectName)) {

				log(LogStatus.INFO,
						"-----Verified Task named: " + AR_Firm6 + " found in Tasks Object-----",
						YesNo.No);
//				if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
//					log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

//					if (click(driver, taskBP.buttonInTheDownArrowList(ShowMoreAction.Edit.toString(), 20),
//							"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
//						log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);
				if (click(driver, BP.editButtonOfSubjectLinkPopUpInInteractionSection(20),
							"Edit Note Button of: " + task1BasicSection, action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Edit button on Subject Link Popup", YesNo.No);
						ThreadSleep(5000);

							if (BP.updateActivityTimelineRecord(projectName, task1BasicSection, null,
									null, null, null,false,null,null,null,null,null,null)) {
								log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

								CommonLib.refresh(driver);

							} else {
								log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
								sa.assertTrue(false, "Activity timeline record has not Updated");
							}

//					} else {
//						log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
//								YesNo.Yes);
//						BaseLib.sa.assertTrue(false, "Not Able Click on Edit button in Down Arrow Button");
//
//					}

				} else {
					log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able Click on Down Arrow Button");

				}

			} else {

				log(LogStatus.ERROR,
						"-----Task named: " + AR_Firm6 + " not found in Tasks Object-----",
						YesNo.Yes);
				BaseLib.sa.assertTrue(false,
						"-----Task named: " + AR_Firm6 + " not found in Tasks Object-----");

			}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	ThreadSleep(5000);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),AR_Research6, "Research Input Field", action.BOOLEAN)){
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
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, AR_Research6, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research6 + "||" + "list : "+list, YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research6 + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research6 + "||" + "list : "+list);
		} 
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
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
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
	public void ARTc019_1_RenameEventNameAndVerifyResearchData(String projectName) {
	    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	    HomePageBusineesLayer home = new HomePageBusineesLayer(driver); 
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	    TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		
		 String[][] task1BasicSection = { { excelLabel.Subject.toString(), AR_Research7.replace("\"", "")} };
			 lp.CRMLogin(superAdminUserName, adminPassword, appName);
	   
			 if (home.globalSearchAndNavigate(AR_Firm7.replace("   ", "").replace("\"", ""), RelatedTab.Events.toString(), false, projectName)) {

					log(LogStatus.INFO,
							"-----Verified Task named: " + AR_Firm7 + " found in Tasks Object-----",
							YesNo.No);
//					if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
//						log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

//						if (click(driver, taskBP.buttonInTheDownArrowList(ShowMoreAction.Edit.toString(), 20),
//								"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
//							log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);
					if (click(driver, BP.editButtonOfSubjectLinkPopUpInInteractionSection(20),
								"Edit Note Button of: " + task1BasicSection, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on Edit button on Subject Link Popup", YesNo.No);
							ThreadSleep(5000);

								if (BP.updateActivityTimelineRecord(projectName, task1BasicSection, null,
										null, null, null,false,null,null,null,null,null,null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");
								}

//						} else {
//							log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
//									YesNo.Yes);
//							BaseLib.sa.assertTrue(false, "Not Able Click on Edit button in Down Arrow Button");
	//
//						}

					} else {
						log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not Able Click on Down Arrow Button");

					}

				} else {

					log(LogStatus.ERROR,
							"-----Task named: " + AR_Firm7 + " not found in Tasks Object-----",
							YesNo.Yes);
					BaseLib.sa.assertTrue(false,
							"-----Task named: " + AR_Firm7 + " not found in Tasks Object-----");

				}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		ThreadSleep(5000);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(5000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),AR_Research7, "Research Input Field", action.BOOLEAN)){
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
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, AR_Research7, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research7 + "||" + "list : "+list, YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research7 + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research7+ "||" + "list : "+list);
		} 
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName"})
@Test
	public void ARTc019_2_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	
	String[] searchValues = {AR_Firm54};
	
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
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
	public void ARTc019_3_RenameThemeNameAndVerifyResearchData(String projectName) {
    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
    ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	 
		 lp.CRMLogin(superAdminUserName, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
	      if (tp.clickOnAlreadyCreatedItem(projectName, AR_Firm54.replace("  ", "").replace("\"", ""), 10)) {
	    	  switchOnWindow(driver);
	    	  ThreadSleep(3000);
	           if (tp.UpdateThemeName(projectName, AR_Research54.replace("\"", ""), 10)) {
	               log(LogStatus.INFO, "successfully update Theme name " + AR_Research54, YesNo.Yes);
	           } else {
	               sa.assertTrue(false, "not able to update Theme name " + AR_Research54);
	               log(LogStatus.SKIP, "not able to update Theme name " + AR_Research54, YesNo.Yes);
	           }
	       } else {
	          sa.assertTrue(false, "Not Able to open created Theme : " + AR_Firm54);
	           log(LogStatus.SKIP, "Not Able to open created Theme: " + AR_Firm54, YesNo.Yes);
	      }
	   } else {
	       log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab + " tab", YesNo.Yes);
	       sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab + " tab");
	   }
//	 driver.close();
	ThreadSleep(3000);
	switchToDefaultContent(driver);
	refresh(driver);
	lp.CRMlogout();
	ThreadSleep(2000);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),AR_Research54, "Research Input Field", action.BOOLEAN)){
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
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, AR_Research54, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research54 + "||" + "list : "+list, YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research54 + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research54+ "||" + "list : "+list);
		}  
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc020_1_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	
	String[] searchValues = {AR_Firm55};
	
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
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
	public void ARTc020_2_RenameClipNameAndVerifyResearchData(String projectName) {
    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	ClipPageBusinessLayer cp = new ClipPageBusinessLayer(driver);
		 lp.CRMLogin(superAdminUserName, adminPassword, appName);
   
	   if (fp.clickOnTab(environment, mode, TabName.ClipsTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.ClipsTab, YesNo.No);
	      if (tp.clickOnAlreadyCreatedItem(projectName, AR_Firm55.replace("  ", "").replace("\"", ""), 10)) {
//	    	  ThreadSleep(3000);
//				click(driver, bp.getPagePopUp(projectName, 5), "Page PopUp", action.BOOLEAN);
	           if (cp.UpdateClipName(projectName,AR_Firm55.replace("  ", "").replace("\"", ""), AR_Research55.replace("\"", ""), 10)) {
	               log(LogStatus.INFO, "successfully update contact name " + AR_Research55, YesNo.Yes);
	           } else {
	               sa.assertTrue(false, "not able to update deal name " + AR_Research55);
	               log(LogStatus.SKIP, "not able to update deal name " + AR_Research55, YesNo.Yes);
	           }
	       } else {
	          sa.assertTrue(false, "Not Able to open created Deal : " + AR_Firm55);
	           log(LogStatus.SKIP, "Not Able to open created Deal: " + AR_Firm55, YesNo.Yes);
	      }
	   } else {
	       log(LogStatus.ERROR, "Not able to click on " + TabName.ClipsTab + " tab", YesNo.Yes);
	       sa.assertTrue(false, "Not able to click on " + TabName.ClipsTab + " tab");
	   }
	   
	switchToDefaultContent(driver);
	ThreadSleep(3000);
//	refresh(driver);
	lp.CRMlogout();
	ThreadSleep(2000);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(sendKeys(driver, rp.getTextAreaResearch(10),AR_Research55, "Research Input Field", action.BOOLEAN)){
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
	   String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.ResearchFindings, AR_Research55, excelLabel.Variable_Name);
	   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(variable, action.SCROLLANDBOOLEAN, 10);
		if(list.isEmpty()) {
			
			log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research55 + "||" + "list : "+list, YesNo.No);
		} else {
			log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research55 + "||" + "list : "+list, YesNo.No);
			sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Research55+ "||" + "list : "+list);
		} 
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc020_3_VerifyTheResearchFunctionalityForAccountRecordID(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String xpath,ele,headerName,searchValue = null;
	
	if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
		if (ip.clickOnAlreadyCreatedItem(projectName, TabName.InstituitonsTab, AR_Firm1Name, 10)) {
			String recordID[] = driver.getCurrentUrl().split("Account/");
			String[] recordNo = recordID[1].split("/view"); 
			searchValue = recordNo[0];
		}
		ExcelUtils.writeData(ResearchDataSheetFilePath, searchValue, "UpdatedData", excelLabel.Variable_Name, "AR_Up58",excelLabel.Name);
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		ThreadSleep(2000);
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
		if(ele.contains(bp.errorName1)){
			log(LogStatus.PASS, ele +" has been Matched with " +bp.errorName1, YesNo.No);
		} else {
			log(LogStatus.ERROR, ele +" is not Matched with " +bp.errorName1, YesNo.Yes);
			sa.assertTrue(false, ele +" is not Matched with " +bp.errorName1);
		}
		
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
				}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}
}

@Parameters({ "projectName"})
@Test
	public void ARTc021_VerifyTheResearchFunctionalityForContactRecordID(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer ip = new ContactsPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String searchValue = null,ele = null;
	
	if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
		if (ip.clickOnAlreadyCreatedItem(projectName, TabName.ContactsTab, AR_Contact1Name, 10)) {
			String recordID[] = driver.getCurrentUrl().split("Contact/");
			String[] recordNo = recordID[1].split("/view"); 
			searchValue = recordNo[0];
			
		}
		ExcelUtils.writeData(ResearchDataSheetFilePath, searchValue, "UpdatedData", excelLabel.Variable_Name, "AR_Up59",excelLabel.Name);

		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		ThreadSleep(2000);
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
		if(ele.contains(bp.errorName1)){
			log(LogStatus.PASS, ele +" has been Matched with " +bp.errorName1, YesNo.No);
		} else {
			log(LogStatus.ERROR, ele +" is not Matched with " +bp.errorName1, YesNo.Yes);
			sa.assertTrue(false, ele +" is not Matched with " +bp.errorName1);
		}
		
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
				}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}
}

@Parameters({ "projectName"})
@Test
	public void ARTc021_2_UpdateRecordTypesAsActive(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	
	String contactRecordTypeList = AR_ContactRecordType1;
	String dealRecordTypeList = AR_DealRecordType1;
	String fundRecordTypeList = AR_FundRecordType1;
	String fundraisingRecordTypeList = AR_FundraisingRecordType1;
	String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP, -1);
	String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);
	String fundRecordTypeArray[] = fundRecordTypeList.split(breakSP, -1);
	String fundraisingRecordTypeArray[] = fundraisingRecordTypeList.split(breakSP, -1);
	
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
	public void ARTc022_1_CreateCustomFields(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp=new ResearchPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword,appName);
	
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
					{ recordTypeLabel.Description.toString(), RecordTypeArray[0] + bp.recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } },
			{ { recordTypeLabel.Record_Type_Label.toString(), RecordTypeArray[1] },
					{ recordTypeLabel.Description.toString(), RecordTypeArray[1] + bp.recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } } };
	
	String recordActive[][] = {{ recordTypeLabel.Active.toString(), "Checked" }};
	String recordTypes [] = {"Deal","Fund","Fundraising"};
	String avail[][] = {{"SellSide Deal","BuySide Deal", "Capital Raise"},{"Mutual Fund","Trust Fund"},{"FRGRT","MSGRT"}};
	String defaultValue[] = {"SellSide Deal","Mutual Fund", "FRGRT"};
	String[] profileForSelection = {"System Administrator" };
	boolean isMakeAvailable = false;
	boolean isMakeDefault = false;
	boolean flag = false;
	String parentID=null;
	
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
					ThreadSleep(5000);
					if (clickUsingJavaScript(driver, rp.getProfileSelected(profileForSelection[0],10), profileForSelection[0].toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + profileForSelection[0], YesNo.No);
						ThreadSleep(10000);
						for(int i=0; i <3; i++) {
							System.out.println(avail[i].length);
						switchToDefaultContent(driver);
						ThreadSleep(5000);
						switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
						ThreadSleep(5000);
						if (clickUsingJavaScript(driver, rp.getEditButtonForRecordTypes(recordTypes[i], 10), "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
							ThreadSleep(2000);
							
								for(int j = 0; j <avail[i].length; j++)
							if (selectVisibleTextFromDropDown(driver, sp.getavailableRecordType(10),
									"Available Tab List", avail[i][j])) {
								appLog.info(recordTypes[i] + " is selected successfully in available tabs");
								if (click(driver, sp.getAddBtn(10), "Custom Tab Add Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on add button");
								} else {
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
										profileForSelection, isMakeDefault, PageLayout.Institution.toString(), 10);
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
	
	refresh(driver);
	ThreadSleep(2000);
			
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
		
			refresh(driver);
			ThreadSleep(2000);
			
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
		
			refresh(driver);
			ThreadSleep(2000);
			
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
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

	@Parameters({ "projectName"})
	@Test
	public void ARTc022_2_SelectingRecordTypesForProfiles(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp=new ResearchPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword,appName);
	
	String recordTypes = "Account";
	String avail[] = {"Consultant RT", "IT Firm"};
	String[] profileForSelection = {"System Administrator"};
	String parentID=null;
	for(int k=0; k<profileForSelection.length; k++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
					ThreadSleep(2000);
					if (clickUsingJavaScript(driver, rp.getProfileSelected(profileForSelection[k],10), profileForSelection[k].toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + profileForSelection[k], YesNo.No);
						ThreadSleep(2000);
						for(int i=0; i <avail.length; i++) {
						switchToDefaultContent(driver);
						ThreadSleep(5000);
						switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
						ThreadSleep(2000);
						if (click(driver, rp.getEditButtonForRecordTypes(recordTypes, 10), "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
							ThreadSleep(2000);
							if (selectVisibleTextFromDropDown(driver, sp.getavailableRecordType(10),
									"Selected Tab List", avail[i])) {
								appLog.info(recordTypes + " is selected successfully in Available tabs");
								if (click(driver, sp.getAddBtn(10), "Custom Tab Add Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.info("clicked on Add button");
								} else {
									appLog.error("Not able to click on Remove button so cannot add custom tabs");
								}
							} else {
								appLog.error(avail[i] + " record type is not Available list Tab.");
							}
	
							if (click(driver, sp.getCreateUserSaveBtn_Lighting(10), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
								ThreadSleep(2000);
							} else {
								log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
							}
							}else {
								log(LogStatus.ERROR, "not able to click on edit button for record type settiing", YesNo.Yes);
							}
					}
					}else {
						log(LogStatus.ERROR, profileForSelection[k]+" profile is not clickable", YesNo.Yes);
					}
				
				} else {
					log(LogStatus.ERROR, "profiles tab is not clickable", YesNo.Yes);
				}
				driver.close();
				driver.switchTo().window(parentID);
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
		}
			}else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			}
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
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//			int gridSize = rp.getElementsFromGrid().size();
//			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//			for(int i=0; i<gridSize; i++)
//			{		
//				headerName = rp.getElementsFromGrid().get(i).getText();
//				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//				
//				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//				}
//				if (rp.VerifyViewMoreOption(headerName)) {
//					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//				}
//			}
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
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
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
	String ele;
	
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
					log(LogStatus.ERROR,varibale + "---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
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
//			if (rp.mouseHoverOnNavigationAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			
//			if (rp.mouseHoverOnGridAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			int gridSize = rp.getElementsFromGrid().size();
//			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//			for(int i=0; i<gridSize; i++)
//			{		
//				headerName = rp.getElementsFromGrid().get(i).getText();
//				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//				
//				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//				}
//				if (rp.VerifyViewMoreOption(headerName)) {
//					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//				}
//			}
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
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	
	String firmRecordTypeList = AR_OtherLabels1;
	String dealRecordTypeList = AR_DealRecordType1;
	String fundRecordTypeList = AR_FundRecordType1;
	String contactRecordTypeList = AR_ContactRecordType1;
	String fundraisingRecordTypeList = AR_FundraisingRecordType1;
	String firmRecordTypeArray[] = firmRecordTypeList.split(breakSP,-1);
	String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);
	String fundRecordTypeArray[] = fundRecordTypeList.split(breakSP, -1);
	String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP, -1);
	String fundraisingRecordTypeArray[] = fundraisingRecordTypeList.split(breakSP, -1);
	
	boolean flag = false;
	String parentID=null;
		
	for (int i = 0; i < firmRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				String[][] firmRecordType = {{recordTypeLabel.Record_Type_Label.toString(),firmRecordTypeArray[i]+" Updated"}};
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
	
	for (int i = 0; i < contactRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				String[][] contactRecordType = {{recordTypeLabel.Record_Type_Label.toString(),contactRecordTypeArray[i]+" Updated"}};
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Contact)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Contact,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(contactRecordTypeArray[i])) {
							if (sp.editRecordTypeForObject(projectName, contactRecordType, 10)) {
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
				String[][] dealRecordType = {{recordTypeLabel.Record_Type_Label.toString(),dealRecordTypeArray[i]+" Updated"}};
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
				String[][] fundRecordType = {{recordTypeLabel.Record_Type_Label.toString(),fundRecordTypeArray[i]+" Updated"}};
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
				String[][] fundraisingRecordType = {{recordTypeLabel.Record_Type_Label.toString(),fundraisingRecordTypeArray[i]+" Updated"}};
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
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//			if (rp.mouseHoverOnNavigationAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			
//			if (rp.mouseHoverOnGridAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			int gridSize = rp.getElementsFromGrid().size();
//			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//			for(int i=0; i<gridSize; i++)
//			{		
//				headerName = rp.getElementsFromGrid().get(i).getText();
//				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//				
//				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//				}
//				if (rp.VerifyViewMoreOption(headerName)) {
//					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//				}
//			}
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
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	
	String firmRecordTypeList = AR_OtherLabels1;
	String dealRecordTypeList = AR_DealRecordType1;
	String fundRecordTypeList = AR_FundRecordType1;
	String contactRecordTypeList = AR_ContactRecordType1;
	String fundraisingRecordTypeList = AR_FundraisingRecordType1;
	String firmRecordTypeArray[] = firmRecordTypeList.split(breakSP,-1);
	String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);
	String fundRecordTypeArray[] = fundRecordTypeList.split(breakSP, -1);
	String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP, -1);
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
						if (sp.clickOnAlreadyCreatedLayout(firmRecordTypeArray[i]+" Updated")) {
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
	
	for (int i = 0; i < contactRecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				String[][] dealRecordType = {{recordTypeLabel.Record_Type_Label.toString(),contactRecordTypeArray[i]}};
				if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Contact)) {
					if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Contact,
							ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(contactRecordTypeArray[i]+" Updated")) {
							if (sp.editRecordTypeForObject(projectName, dealRecordType, 10)) {
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
						if (sp.clickOnAlreadyCreatedLayout(dealRecordTypeArray[i]+" Updated")) {
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
						if (sp.clickOnAlreadyCreatedLayout(fundRecordTypeArray[i]+" Updated")) {
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
						if (sp.clickOnAlreadyCreatedLayout(fundraisingRecordTypeArray[i]+" Updated")) {
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
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//			if (rp.mouseHoverOnNavigationAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			
//			if (rp.mouseHoverOnGridAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			int gridSize = rp.getElementsFromGrid().size();
//			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//			for(int i=0; i<gridSize; i++)
//			{		
//				headerName = rp.getElementsFromGrid().get(i).getText();
//				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//				
//				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//				}
//				if (rp.VerifyViewMoreOption(headerName)) {
//					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//				}
//			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void ARTc030_1_DeselectingRecordTypesForProfiles(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp=new ResearchPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword,appName);
	
	String recordTypes [] = {"Contact","Deal","Fund","Fundraising"};
	String avail[][] = {{"Banker","Broker"},{"SellSide Deal","BuySide Deal", "Capital Raise"},{"Mutual Fund","Trust Fund"},{"FRGRT","MSGRT"}};
	String[] profileForSelection = {"System Administrator"};
	String parentID=null;
	String master= "--Master--";
	for(int k=0; k<profileForSelection.length; k++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
					ThreadSleep(2000);
					if (clickUsingJavaScript(driver, rp.getProfileSelected(profileForSelection[k],10), profileForSelection[k].toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + profileForSelection[k], YesNo.No);
						ThreadSleep(5000);
						for(int i=0; i <4; i++) {
							System.out.println(avail[i].length);
						switchToDefaultContent(driver);
						refresh(driver);
						ThreadSleep(5000);
						switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
						ThreadSleep(2000);
						if (click(driver, rp.getEditButtonForRecordTypes(recordTypes[i], 10), "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
							ThreadSleep(2000);
						for(int j = 0; j <avail[i].length; j++) {
							if (selectVisibleTextFromDropDown(driver, sp.getSelectedRecordType(10),
									"Selected Tab List", avail[i][j])) {
								appLog.info(recordTypes + " is selected successfully in Selected tabs");
								if (click(driver, sp.getRemoveBtn(10), "Custom Tab Remove Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on Remove button");
								} else {
									appLog.error("Not able to click on Remove button so cannot add custom tabs");
								}
							} else {
								appLog.error(recordTypes + " record type is not Selected list Tab.");
								sa.assertTrue(false,recordTypes + " record type is not Selected list Tab.");
							}
							
						}
								if (selectVisibleTextFromDropDown(driver, sp.getavailableRecordType(10),
										"Available Tab List", master)) {
									appLog.info(recordTypes + " is selected successfully in Available tabs");
									if (click(driver, sp.getAddBtn(10), "Custom Tab Add Button",
											action.SCROLLANDBOOLEAN)) {
										appLog.error("clicked on add button");
									} else {
										appLog.error("Not able to click on add button so cannot add custom tabs");
									}
								} else {
									appLog.error(recordTypes + " record type is not Selected list Tab.");
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
						log(LogStatus.ERROR, profileForSelection[k]+" profile is not clickable", YesNo.Yes);
						sa.assertTrue(false,profileForSelection[k]+" profile is not clickable");
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
	}
	switchToDefaultContent(driver);
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
	public void ARTc030_2_UpdateRecordTypesAsInactive(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	
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
	String ele;
	
	String[] varibles = {"AR_Up21"};
	String varibale21 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name,varibles[0], excelLabel.Name);

	String[] searchValues = {varibale21};
	
	for(int i=0;i<searchValues.length;i++) {
		log(LogStatus.PASS, "Working for " + searchValues[i], YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValues[i], "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValues[i])) {
			log(LogStatus.PASS, ele +" is matched with " +searchValues[i], YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValues[i], YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValues[i]);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValues[i] + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValues[i])) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValues[i] + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibles[i], action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i]+ "||" + "list : "+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValues[i] + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValues[i] + "---------");
				
		}
//			if (rp.mouseHoverOnNavigationAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			
//			if (rp.mouseHoverOnGridAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			int gridSize = rp.getElementsFromGrid().size();
//			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//			for(int i=0; i<gridSize; i++)
//			{		
//				headerName = rp.getElementsFromGrid().get(i).getText();
//				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//				
//				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//				}
//				if (rp.VerifyViewMoreOption(headerName)) {
//					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//				}
//			}
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
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	
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
	String[] varibles = {"AR_Up22"};
	String varibale22 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name,varibles[0], excelLabel.Name);

	String[] searchValues = {varibale22};
	
	for(int i=0;i<searchValues.length;i++) {
		log(LogStatus.PASS, "Working for " + searchValues[i], YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValues[i], "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValues[i])) {
			log(LogStatus.PASS, ele +" is matched with " +searchValues[i], YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValues[i], YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValues[i]);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValues[i] + "---------",
				YesNo.No);
			
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValues[i])) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValues[i] + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibles[i], action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValues[i] + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValues[i] + "---------");
				
		}
//			if (rp.mouseHoverOnNavigationAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			
//			if (rp.mouseHoverOnGridAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			int gridSize = rp.getElementsFromGrid().size();
//			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//			for(int i=0; i<gridSize; i++)
//			{		
//				headerName = rp.getElementsFromGrid().get(i).getText();
//				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//				
//				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//				}
//				if (rp.VerifyViewMoreOption(headerName)) {
//					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//				}
//			}
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
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	
	String profileForSelection = "PE Standard User";
	String parentID=null, objects[] ={"Fund","Contact","Theme","Clip"}, permissionTypes[] = {"Read","Create","Update","Delete"}, status = "Not Checked";
	boolean flag = false;
	
	switchToDefaultContent(driver);
	home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
		if (parentID!=null) {
			ThreadSleep(2000);
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
	String[] varibles = {"AR_Up23","AR_Up24"};
	String varibale23 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name,varibles[0], excelLabel.Name);
	String varibale24 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name, varibles[1], excelLabel.Name);

	String[] searchValues = {varibale23,varibale24};
	
	for(int i=0;i<searchValues.length;i++) {
		log(LogStatus.PASS, "Working for " + searchValues[i], YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValues[i], "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValues[i])) {
			log(LogStatus.PASS, ele +" is matched with " +searchValues[i], YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValues[i], YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValues[i]);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValues[i] + "---------",
				YesNo.No);
		
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValues[i])) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValues[i] + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibles[i], action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValues[i] + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValues[i] + "---------");
				
		}
//			if (rp.mouseHoverOnNavigationAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			
//			if (rp.mouseHoverOnGridAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			int gridSize = rp.getElementsFromGrid().size();
//			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//			for(int i=0; i<gridSize; i++)
//			{		
//				headerName = rp.getElementsFromGrid().get(i).getText();
//				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//				
//				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//				}
//				if (rp.VerifyViewMoreOption(headerName)) {
//					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//				}
//			}
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
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	
	String profileForSelection = "PE Standard User";
	String parentID=null, objects[] ={"Fund","Contact","Theme","Clip"}, permissionTypes[] = {"Read","Create","Update","Delete"}, status = "Checked";
	boolean flag = false;
	
	switchToDefaultContent(driver);
	home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
		if (parentID!=null) {
			ThreadSleep(2000);
			if(sp.giveAndRemoveObjectPermissionFromProfiles(profileForSelection,objects,permissionTypes,status)) {
				log(LogStatus.PASS,"Add Permission from Contact Object", YesNo.No);
				flag=true;
			}else {
				log(LogStatus.ERROR,"Not able to add permission from" + objects + "Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to add permission from" + objects + "Object");
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
	String[] varibles = {"AR_Up22"};
	String varibale22 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name,varibles[0], excelLabel.Name);
	String[] searchValues = {varibale22};
	for(int i=0;i<searchValues.length;i++) {
		log(LogStatus.PASS, "Working for " + searchValues[i], YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValues[i], "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
			ThreadSleep(2000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValues[i])) {
			log(LogStatus.PASS, ele +" is matched with " +searchValues[i], YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValues[i], YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValues[i]);
		}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValues[i] + "---------",
				YesNo.No);
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValues[i])) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValues[i] + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibles[i], action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValues[i] + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValues[i] + "---------");
				
		}
//			if (rp.mouseHoverOnNavigationAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			
//			if (rp.mouseHoverOnGridAndGetText()) {
//				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//			} else {
//				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//			}
//			int gridSize = rp.getElementsFromGrid().size();
//			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//			for(int i=0; i<gridSize; i++)
//			{		
//				headerName = rp.getElementsFromGrid().get(i).getText();
//				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//				
//				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//				}
//				ThreadSleep(2000);
//				if (rp.VerifyViewMoreOption(headerName)) {
//					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//				}
//			}
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
					"Phone field Permission is given in the Contact Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					"Phone field Permission is not given in the Contact Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					"Phone field Permission is not given in the Contact Object Manager");
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
			if(rp.getNoResult(5) != null){
				log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
			} else 
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//				if (rp.mouseHoverOnNavigationAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				
//				if (rp.mouseHoverOnGridAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				int gridSize = rp.getElementsFromGrid().size();
//				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//				for(int i=0; i<gridSize; i++)
//				{		
//					headerName = rp.getElementsFromGrid().get(i).getText();
//					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//					
//					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//					}
//					if (rp.VerifyViewMoreOption(headerName)) {
//						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//					}
//				}
			}
		lp.CRMlogout();
		refresh(driver);		
		lp.CRMLogin(glUser1EmailID, adminPassword, appName);
		ThreadSleep(2000);
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				if (cp.clickOnCreatedContact(projectName, contactName[0], contactName[1])) {
					if (isDisplayed(driver, cp.getPhoneFieldOnContactPage(10), "Visibility", 10, "Phone") == null) {
						log(LogStatus.PASS, "Phone Field is not visible", YesNo.Yes);
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
			
			String[] varibles = {"AR_Up26"};
			String varibale26 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name,varibles[0], excelLabel.Name);

			String[] searchValues1 = {varibale26};
			
			for(int i=0;i<searchValues1.length;i++) {

				log(LogStatus.PASS, "Working for " + searchValues1[i], YesNo.Yes);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(sendKeys(driver, rp.getTextAreaResearch(10),searchValues1[i], "Research Input Field", action.BOOLEAN)){
					ThreadSleep(2000);
					clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
					ThreadSleep(8000);
					clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
					ThreadSleep(2000);
					ele = rp.getResearchFindingsValue(10).getText();
					if (ele.equals(searchValues1[i])) {
					log(LogStatus.PASS, ele +" is matched with " +searchValues1[i], YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to send value "+searchValues1[i], YesNo.Yes);
					sa.assertTrue(false,"Not Able to send value "+searchValues1[i]);
				}
				}
				log(LogStatus.INFO,
						"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
								+ searchValues1[i] + "---------",
						YesNo.No);
				if(rp.getNoResult(5) != null){
					log(LogStatus.PASS, "There is no data retaled to " + searchValues1[i], YesNo.No);
				} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValues1[i])) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValues1[i] + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibles[i], action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list);
						}
			
					} else {
						log(LogStatus.FAIL,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValues1[i] + "---------",
								YesNo.No);
						sa.assertTrue(false,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValues1[i] + "---------");
						
				}
//					if (rp.mouseHoverOnNavigationAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					
//					if (rp.mouseHoverOnGridAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					int gridSize = rp.getElementsFromGrid().size();
//					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//					for(int i=0; i<gridSize; i++)
//					{		
//						headerName = rp.getElementsFromGrid().get(i).getText();
//						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//						
//						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//						}
//						if (rp.VerifyViewMoreOption(headerName)) {
//							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//						}
//					}
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
	String[] contactName = {"LPcon01","User.rec01"};
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
					"Phone field Permission is given in contact Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					"Phone field Permission is not given in contact Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					"Phone field Permission is not given in contact Object Manager");
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
						log(LogStatus.PASS, "Phone Field is not visible", YesNo.Yes);
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
			
			String[] varibles = {"AR_Up25"};
			String varibale25 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name,varibles[0], excelLabel.Name);

			String[] searchValues = {varibale25};
			
			for(int i=0;i<searchValues.length;i++) {

				log(LogStatus.PASS, "Working for " + searchValues[i], YesNo.Yes);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(sendKeys(driver, rp.getTextAreaResearch(10),searchValues[i], "Research Input Field", action.BOOLEAN)){
					ThreadSleep(2000);
					clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
					ThreadSleep(8000);
					clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimum Button", action.BOOLEAN);
					ThreadSleep(2000);
					ele = rp.getResearchFindingsValue(10).getText();
					if (ele.equals(searchValues[i])) {
					log(LogStatus.PASS, ele +" is matched with " +searchValues[i], YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to send value "+searchValues[i], YesNo.Yes);
					sa.assertTrue(false,"Not Able to send value "+searchValues[i]);
				}
				}
				log(LogStatus.INFO,
						"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
								+ searchValues[i] + "---------",
						YesNo.No);
				if(rp.getNoResult(5) != null){
					log(LogStatus.PASS, "There is no data retaled to " + searchValues[i], YesNo.No);
				} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValues[i])) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValues[i] + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibles[i], action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list, YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValues[i] + "||" + "list : "+list);
						}
			
					} else {
						log(LogStatus.FAIL,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValues[i] + "---------",
								YesNo.No);
						sa.assertTrue(false,
								"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValues[i] + "---------");
						
				}
//					if (rp.mouseHoverOnNavigationAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					
//					if (rp.mouseHoverOnGridAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					int gridSize = rp.getElementsFromGrid().size();
//					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//					for(int i=0; i<gridSize; i++)
//					{		
//						headerName = rp.getElementsFromGrid().get(i).getText();
//						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//						
//						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//						}
//						if (rp.VerifyViewMoreOption(headerName)) {
//							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//						}
//					}
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
	String parentWindow = "", contactFields[] = {"Description","Legal Name"}, dealFields[] = {"Stage","Pipeline Comments"}, fundraisingFields[] = {"Status Notes","Legal Name"};
	String[] varibles = {"AR_Up57","AR_Up28","AR_Up27"};
	String varibale27 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name,varibles[0], excelLabel.Name);
	String varibale28 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name, varibles[1], excelLabel.Name);
	String varibale57 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name, varibles[2], excelLabel.Name);

	String[] searchValues = {varibale57,varibale28,varibale27};
	String ele;

	
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
					"Description field Permission is given from the Firm Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					"Description field Permission is not given for Firm Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					"Description field Permission is not given for Firm Object Manager");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < contactFields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Contact,
				ObjectFeatureName.FieldAndRelationShip, contactFields[i], PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					contactFields[i] + " field Permission is given from the Contact Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					contactFields[i] + "field Permission is not given for Contact Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					contactFields[i] + " field Permission is not given for Contact Object Manager");
		}
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < dealFields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Deal,
				ObjectFeatureName.FieldAndRelationShip, dealFields[i], PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					dealFields[i] + " field Permission is given from the Deal Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					dealFields[i] + "field Permission is not given for Deal Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					dealFields[i] + " field Permission is not given for Deal Object Manager");
		}
		}	
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < fundraisingFields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Fundraising,
				ObjectFeatureName.FieldAndRelationShip, fundraisingFields[i], PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					fundraisingFields[i] + " field Permission is given from the Fundraising Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					fundraisingFields[i] + " field Permission is not given for Fundraising Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					fundraisingFields[i] + " field Permission is not given for Fundraising Object Manager");
		}
		}	
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Task,
				ObjectFeatureName.FieldAndRelationShip, "Comments", PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Comments field Permission is given from the Task Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					"Comments field Permission is not given for Task Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					"Comments field Permission is not given for Task Object Manager");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Event,
				ObjectFeatureName.FieldAndRelationShip, "Description", PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Description field Permission is given from the Task Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					"Description field Permission is not given for Task Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					"Description field Permission is not given for Task Object Manager");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Theme,
				ObjectFeatureName.FieldAndRelationShip, "Description", PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Description field Permission is given from the Theme Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					"Description field Permission is not given for Theme Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					"Description field Permission is not given for Theme Object Manager");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Clip,
				ObjectFeatureName.FieldAndRelationShip, "Summary", PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Summary field Permission is given from the Clip Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					"Summary field Permission is not given for Clip Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					"Summary field Permission is not given for Clip Object Manager");
		}
		
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
			if(rp.getNoResult(5) != null){
				log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
			} else
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//				if (rp.mouseHoverOnNavigationAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				
//				if (rp.mouseHoverOnGridAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				int gridSize = rp.getElementsFromGrid().size();
//				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//				for(int i=0; i<gridSize; i++)
//				{		
//					headerName = rp.getElementsFromGrid().get(i).getText();
//					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//					
//					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//					}
//					if (rp.VerifyViewMoreOption(headerName)) {
//						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//					}
//				}
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
	String parentWindow = "", contactFields[] = {"Description","Legal Name"}, dealFields[] = {"Stage","Pipeline Comments"}, fundraisingFields[] = {"Status Notes","Legal Name"};
	String[] varibles = {"AR_Up27","AR_Up28"};
	String varibale27 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name,varibles[0], excelLabel.Name);
	String varibale281 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name, varibles[1], excelLabel.Name);

	String[] searchValues = {varibale27,varibale281};
	String ele;

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
					"Description field Permission is given from the Firm Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					"Description field Permission is not given for Firm Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					"Description field Permission is not given for Firm Object Manager");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < contactFields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Contact,
				ObjectFeatureName.FieldAndRelationShip, contactFields[i], PermissionType.givePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					contactFields[i] + " field Permission is given from the Contact Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					contactFields[i] + " field Permission is not given for Contact Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					contactFields[i] + " field Permission is not given for Contact Object Manager");
		}
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < dealFields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Deal,
				ObjectFeatureName.FieldAndRelationShip, dealFields[i], PermissionType.givePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					dealFields[i] +" field Permission is given from the Deal Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					dealFields[i] + " field Permission is not given for Deal Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					dealFields[i] + " field Permission is not given for Deal Object Manager");
		}
		}	
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		for(int i=0; i < fundraisingFields.length; i++) {
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Fundraising,
				ObjectFeatureName.FieldAndRelationShip, fundraisingFields[i], PermissionType.givePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					fundraisingFields[i] + " field Permission is given from the Fundraising Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					fundraisingFields[i] + " field Permission is not given for Fundraising Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					fundraisingFields[i] + " field Permission is not given for Fundraising Object Manager");
		}
		}	
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Task,
				ObjectFeatureName.FieldAndRelationShip, "Comments", PermissionType.givePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Comments field Permission is given from the Task Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					"Comments field Permission is not given for Task Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					"Comments field Permission is not given for Task Object Manager");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Event,
				ObjectFeatureName.FieldAndRelationShip, "Description", PermissionType.givePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Description field Permission is given from the Event Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					"Description field Permission is not given for Event Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					"Description field Permission is not given for Event Object Manager");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		driver.close();
	}
		driver.switchTo().window(parentWindow);
		
		ThreadSleep(2000);
			
		for(String searchValue : searchValues) {
//			String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
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
			if(rp.getNoResult(5) != null){
				log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
			} else
				
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(searchValue, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//				if (rp.mouseHoverOnNavigationAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				
//				if (rp.mouseHoverOnGridAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				int gridSize = rp.getElementsFromGrid().size();
//				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//				for(int i=0; i<gridSize; i++)
//				{		
//					headerName = rp.getElementsFromGrid().get(i).getText();
//					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//					
//					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//					}
//					if (rp.VerifyViewMoreOption(headerName)) {
//						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//					}
//				}
			}
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
				if(rp.getNoResult(5) != null){
					log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
				} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
				
//					if (rp.mouseHoverOnNavigationAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					
//					if (rp.mouseHoverOnGridAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					int gridSize = rp.getElementsFromGrid().size();
//					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//					for(int i=0; i<gridSize; i++)
//					{		
//						headerName = rp.getElementsFromGrid().get(i).getText();
//						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//						
//						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//						}
//						if (rp.VerifyViewMoreOption(headerName)) {
//							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//						}
//					}
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
	String[] varibles = {"AR_Up27"};
	String varibale27 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name,varibles[0], excelLabel.Name);
	
	String[] searchValues = {varibale27};

	boolean flag1 = false;
	String tabNames1 = "Accounts" ,tabNames2 = "Contacts" ;
	String[] labelsWithValues1 = {  "Legal Name<break>Legal Name upd", "Description<break>Description upd" },labelsWithValues2 = {  "Contact Name<break>Contact Name upd", "Description<break>Description upd"  };
	String ele, headerName;
	String DealLabel1= PageLabel.Stage_Upd.toString();
	String DealLabel2= PageLabel.Pipeline_Comments_Upd.toString();
	String FundraisingLabel1= PageLabel.Legal_Name_Upd.toString();
	String FundraisingLabel2= PageLabel.Status_Notes_Upd.toString();
	String FundLabel= PageLabel.Vintage_Year_Upd.toString();
	String ThemeLabel= PageLabel.Description_Upd.toString();
	String ClipLabel= PageLabel.Summary_Upd.toString();
	
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
		}

		if (setup.renameLabelsOfFields(driver, tabNames2, labelsWithValues2, 10)) {
			flag1 = true;
			log(LogStatus.PASS, labelsWithValues2[0] + " is updated as " +labelsWithValues2[1] , YesNo.Yes);
		}
		} else {
			log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_And_Labels, YesNo.Yes);
			sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_And_Labels);
		}	
		driver.close();
	}	
	
	ThreadSleep(2000);
	driver.switchTo().window(parentWindow);
	
//	lp.CRMlogout();
//	
//	ThreadSleep(2000);
//	lp.CRMLogin(superAdminUserName, adminPassword, appName);
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
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Pipeline_Comments.toString().replace("_", " "), DealLabel2.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Pipeline_Comments.toString()+" successfully update to "+DealLabel2, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Pipeline_Comments.toString()+" successfully update to "+DealLabel2, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Pipeline_Comments.toString()+" to "+DealLabel2);	
					}
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Stage.toString(), DealLabel1.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Stage.toString()+" successfully update to "+DealLabel1, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Stage.toString()+" successfully update to "+DealLabel1, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Stage.toString()+" to "+DealLabel1);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Deal.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Deal.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
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
			
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
				log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);	
				if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Fund.toString())){
					log(LogStatus.INFO, "Select "+PageLabel.Fund.toString()+" text in object dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Vintage_Year.toString().replace("_"," "), FundLabel.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Vintage_Year.toString()+" successfully update to "+FundLabel, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Vintage_Year.toString()+" successfully update to "+FundLabel, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Vintage_Year.toString()+" to "+FundLabel);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Fund.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Fund.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
			}
			
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
				log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);
				if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Theme.toString())){
					log(LogStatus.INFO, "Select "+PageLabel.Theme.toString()+" text in object dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Description.toString().replace("_"," "), ThemeLabel.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Description.toString()+" successfully update to "+ThemeLabel, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Description.toString()+" successfully update to "+ThemeLabel, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Description.toString()+" to "+ThemeLabel);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Theme.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Theme.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
			}
			
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
				log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);
				if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Clip.toString())){
					log(LogStatus.INFO, "Select "+PageLabel.Clip.toString()+" text in object dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Summary.toString().replace("_"," "), ClipLabel.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Summary.toString()+" successfully update to "+ClipLabel, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Summary.toString()+" successfully update to "+ClipLabel, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Summary.toString()+" to "+ClipLabel);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Clip.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Clip.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
			}
		}else{
			log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Override"), YesNo.Yes);
			sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Override"));
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
				if(rp.getNoResult(5) != null){
					log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
				} else
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//				if (rp.mouseHoverOnNavigationAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				
//				if (rp.mouseHoverOnGridAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				int gridSize = rp.getElementsFromGrid().size();
//				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//				for(int i=0; i<gridSize; i++)
//				{		
//					headerName = rp.getElementsFromGrid().get(i).getText();
//					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//					
//					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//					}
//					if (rp.VerifyViewMoreOption(headerName)) {
//						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//					}
//				}
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
	String[] varibles = {"AR_Up27"};
	String varibale27 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name,varibles[0], excelLabel.Name);

	String[] searchValues = {varibale27};

	boolean flag1 = false;
	String tabNames1 = "Accounts" ,tabNames2 = "Contacts" ;
	String[] labelsWithValues1 = {  "Legal Name<break>Legal Name Upd !@&*()(*& 123", "Description<break>Description Upd !@&*()(*& 123" },
			labelsWithValues2 = {  "Contact Name<break>Contact Name Upd !@&*()(*& 123", "Description<break>Description Upd !@&*()(*& 123"  };
	String ele;
	String DealLabel1= "!@&*()(*& 123";
	String DealLabel2= " !@&*()(*& 123";
	String FundraisingLabel1= " !@&*()(*& 123";
	String FundraisingLabel2= "!@&*()(*& 123";
	String FundLabel= "!@&*()(*& 123";
	String ThemeLabel= "!@&*()(*& 123";
	String ClipLabel= "!@&*()(*& 123";
	
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
		}

		if (setup.renameLabelsOfFields(driver, tabNames2, labelsWithValues2, 10)) {
			flag1 = true;
			log(LogStatus.PASS, labelsWithValues2[0] + " is updated as " +labelsWithValues2[1] , YesNo.Yes);
		}
		} else {
			log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_And_Labels, YesNo.Yes);
			sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_And_Labels);
		}	
		driver.close();
	}	
	
	ThreadSleep(2000);
	driver.switchTo().window(parentWindow);
//	lp.CRMlogout();
//	
//	ThreadSleep(2000);
//	lp.CRMLogin(superAdminUserName, adminPassword, appName);
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
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Pipeline_Comments.toString().replace("_", " "), DealLabel2.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Pipeline_Comments.toString()+" successfully update to "+DealLabel2, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Pipeline_Comments.toString()+" successfully update to "+DealLabel2, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Pipeline_Comments.toString()+" to "+DealLabel2);	
					}
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Stage.toString(), DealLabel1.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Stage.toString()+" successfully update to "+DealLabel1, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Stage.toString()+" successfully update to "+DealLabel1, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Stage.toString()+" to "+DealLabel1);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Deal.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Deal.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
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
			
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
				log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);	
				if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Fund.toString())){
					log(LogStatus.INFO, "Select "+PageLabel.Fund.toString()+" text in object dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Vintage_Year.toString().replace("_"," "), FundLabel.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Vintage_Year.toString()+" successfully update to "+FundLabel, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Vintage_Year.toString()+" successfully update to "+FundLabel, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Vintage_Year.toString()+" to "+FundLabel);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Fund.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Fund.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
			}
			
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
				log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);
				if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Theme.toString())){
					log(LogStatus.INFO, "Select "+PageLabel.Theme.toString()+" text in object dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Description.toString().replace("_"," "), ThemeLabel.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Description.toString()+" successfully update to "+ThemeLabel, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Description.toString()+" successfully update to "+ThemeLabel, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Description.toString()+" to "+ThemeLabel);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Theme.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Theme.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
			}
			
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
				log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);
				if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Clip.toString())){
					log(LogStatus.INFO, "Select "+PageLabel.Clip.toString()+" text in object dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Summary.toString().replace("_"," "), ClipLabel.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Summary.toString()+" successfully update to "+ClipLabel, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Summary.toString()+" successfully update to "+ClipLabel, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Summary.toString()+" to "+ClipLabel);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Clip.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Clip.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
			}
		}else{
			log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Override"), YesNo.Yes);
			sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Override"));
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
			if(rp.getNoResult(5) != null){
				log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
			} else
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//				if (rp.mouseHoverOnNavigationAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				
//				if (rp.mouseHoverOnGridAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				int gridSize = rp.getElementsFromGrid().size();
//				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//				for(int i=0; i<gridSize; i++)
//				{		
//					headerName = rp.getElementsFromGrid().get(i).getText();
//					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//					
//					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//					}
//					if (rp.VerifyViewMoreOption(headerName)) {
//						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//					}
//				}
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
	String[] varibles = {"AR_Up27"};
	String varibale27 =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name,varibles[0], excelLabel.Name);
	
	String[] searchValues = {varibale27};

	boolean flag1 = false;
	String tabNames1 = "Accounts" ,tabNames2 = "Contacts" ;
	String[] labelsWithValues1 = {  "Legal Name<break>Legal Name", "Description<break>Description" },labelsWithValues2 = {  "Contact Name<break>Contact Name", "Description<break>Description"  };
	String ele, headerName;
	String DealLabel1= PageLabel.Stage.toString();
	String DealLabel2= PageLabel.Pipeline_Comments.toString();
	String FundraisingLabel1= PageLabel.Legal_Name.toString();
	String FundraisingLabel2= PageLabel.Status_Notes.toString();
	String FundLabel= PageLabel.Vintage_Year.toString();
	String ThemeLabel= PageLabel.Description.toString();
	String ClipLabel= PageLabel.Summary.toString();
	
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
		}

		if (setup.renameLabelsOfFields(driver, tabNames2, labelsWithValues2, 10)) {
			flag1 = true;
			log(LogStatus.PASS, labelsWithValues2[0] + " is updated as " +labelsWithValues2[1] , YesNo.Yes);
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
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Pipeline_Comments.toString().replace("_", " "), DealLabel2.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Pipeline_Comments.toString()+" successfully update to "+DealLabel2, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Pipeline_Comments.toString()+" successfully update to "+DealLabel2, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Pipeline_Comments.toString()+" to "+DealLabel2);	
					}
					if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Stage.toString(), DealLabel1.replace("_", " "), action.SCROLLANDBOOLEAN)){
						log(LogStatus.INFO, "Field label: "+PageLabel.Stage.toString()+" successfully update to "+DealLabel1, YesNo.No);
						
					}else{
						log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Stage.toString()+" successfully update to "+DealLabel1, YesNo.Yes);
						sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Stage.toString()+" to "+DealLabel1);	
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Deal.toString()+" in  object dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: "+PageLabel.Deal.toString()+" in  object dropdown in override page");
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
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
	
		if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
			log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
			ThreadSleep(5000);	
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Fund.toString())){
				log(LogStatus.INFO, "Select "+PageLabel.Fund.toString()+" text in object dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);
				if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Vintage_Year.toString().replace("_"," "), FundLabel.replace("_", " "), action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "Field label: "+PageLabel.Vintage_Year.toString()+" successfully update to "+FundLabel, YesNo.No);
					
				}else{
					log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Vintage_Year.toString()+" successfully update to "+FundLabel, YesNo.Yes);
					sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Vintage_Year.toString()+" to "+FundLabel);	
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Fund.toString()+" in  object dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: "+PageLabel.Fund.toString()+" in  object dropdown in override page");
			}
		}else{
			log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
			sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
		}
	
		if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
			log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
			ThreadSleep(5000);	
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Theme.toString())){
				log(LogStatus.INFO, "Select "+PageLabel.Theme.toString()+" text in object dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);
				if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Description.toString().replace("_"," "), ThemeLabel.replace("_", " "), action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "Field label: "+PageLabel.Description.toString()+" successfully update to "+ThemeLabel, YesNo.No);
					
				}else{
					log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Description.toString()+" successfully update to "+ThemeLabel, YesNo.Yes);
					sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Description.toString()+" to "+ThemeLabel);	
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Theme.toString()+" in  object dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: "+PageLabel.Theme.toString()+" in  object dropdown in override page");
			}
		}else{
			log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
			sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
		}
	
		if(selectVisibleTextFromDropDown(driver, setup.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
			log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
			ThreadSleep(5000);	
			if(selectVisibleTextFromDropDown(driver, setup.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Clip.toString())){
				log(LogStatus.INFO, "Select "+PageLabel.Clip.toString()+" text in object dropdown in override setup page", YesNo.No);
				ThreadSleep(5000);
				if(setup.updateFieldLabelInOverridePage(driver, PageLabel.Summary.toString().replace("_"," "), ClipLabel.replace("_", " "), action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "Field label: "+PageLabel.Summary.toString()+" successfully update to "+ClipLabel, YesNo.No);
					
				}else{
					log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Summary.toString()+" successfully update to "+ClipLabel, YesNo.Yes);
					sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Summary.toString()+" to "+ClipLabel);	
				}
			}else{
				log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Clip.toString()+" in  object dropdown in override page", YesNo.Yes);
				sa.assertTrue(false, "Not able to select text: "+PageLabel.Clip.toString()+" in  object dropdown in override page");
			}
		}else{
			log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
			sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
		}
		}else{
			log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Override"), YesNo.Yes);
			sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Override"));
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
				if(rp.getNoResult(5) != null){
					log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
				} else
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//				if (rp.mouseHoverOnNavigationAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				
//				if (rp.mouseHoverOnGridAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				int gridSize = rp.getElementsFromGrid().size();
//				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//				for(int i=0; i<gridSize; i++)
//				{		
//					headerName = rp.getElementsFromGrid().get(i).getText();
//					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//					
//					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//					}
//					if (rp.VerifyViewMoreOption(headerName)) {
//						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//					}
//				}
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
				String xpath = "//td//span[text()='PE Standard User']/..";
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
						if (sendKeys(driver, setup.getProfileNameTextBox(10), "Cloned PE Standard User",
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
		lp.CRMLogin(glUser3EmailID, adminPassword, appName);
		String ele,headerName,varibale;
		String[] searchValues = {AR_Firm30,AR_Firm31,AR_Firm32,AR_Firm33,AR_Firm34,AR_Firm35,AR_Firm36,AR_Firm37,AR_Firm38,AR_Firm39,AR_Firm40,AR_Firm41,AR_Firm42,AR_Firm43,AR_Data1,AR_Data2,AR_Data3};
		
		// Verification on navigation menu
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, rp.getTextAreaResearch(10),"Research Text Area", action.BOOLEAN)) {
				log(LogStatus.INFO, "items verified "+bp.filesName+" on "+navigationMenuName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "items not verified "+bp.filesName+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"items not verified "+bp.filesName+" on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName);
		}
		refresh(driver);
		
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN)) {
				log(LogStatus.INFO, "items verified "+bp.filesName+" on "+navigationMenuName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "items not verified "+bp.filesName+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"items not verified "+bp.filesName+" on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName);
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
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName);
		}
		
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
			refresh(driver);
			try {
			if(rp.getNoResult(5) != null){
				log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
			} else
				if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
					log(LogStatus.INFO,
							"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
									+ searchValue + "---------",
							YesNo.No);
				ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
					if(list.isEmpty()) {
						
						log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
					} else {
						log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
						sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//				if (rp.mouseHoverOnNavigationAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				
//				if (rp.mouseHoverOnGridAndGetText()) {
//					log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//				} else {
//					log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//					sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//				}
//				int gridSize = rp.getElementsFromGrid().size();
//				log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//				for(int i=0; i<gridSize; i++)
//				{		
//					headerName = rp.getElementsFromGrid().get(i).getText();
//					String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//					
//					if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//						log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//						sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//					}
//					if (rp.VerifyViewMoreOption(headerName)) {
//						log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//					}
//				}
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
				if(rp.getNoResult(5) != null){
					log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
				} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//					if (rp.mouseHoverOnNavigationAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					
//					if (rp.mouseHoverOnGridAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					int gridSize = rp.getElementsFromGrid().size();
//					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//					for(int i=0; i<gridSize; i++)
//					{		
//						headerName = rp.getElementsFromGrid().get(i).getText();
//						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//						
//						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//						}
//						if (rp.VerifyViewMoreOption(headerName)) {
//							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//						}
//					}
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
					if(rp.getNoResult(5) != null){
						log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
					} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//					if (rp.mouseHoverOnNavigationAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					
//					if (rp.mouseHoverOnGridAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					int gridSize = rp.getElementsFromGrid().size();
//					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//					for(int i=0; i<gridSize; i++)
//					{		
//						headerName = rp.getElementsFromGrid().get(i).getText();
//						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//						
//						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//						}
//						if (rp.VerifyViewMoreOption(headerName)) {
//							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//						}
//					}
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
					if(rp.getNoResult(5) != null){
						log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
					} else	
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//					if (rp.mouseHoverOnNavigationAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					
//					if (rp.mouseHoverOnGridAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					int gridSize = rp.getElementsFromGrid().size();
//					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//					for(int i=0; i<gridSize; i++)
//					{		
//						headerName = rp.getElementsFromGrid().get(i).getText();
//						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//						
//						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//						}
//						if (rp.VerifyViewMoreOption(headerName)) {
//							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//						}
//					}
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
				log(LogStatus.INFO,
						"New window is open after click on setup link in lighting mode",
						YesNo.Yes);
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
			}
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
					if(rp.getNoResult(5) != null){
						log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
					} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//					if (rp.mouseHoverOnNavigationAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					
//					if (rp.mouseHoverOnGridAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					int gridSize = rp.getElementsFromGrid().size();
//					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//					for(int i=0; i<gridSize; i++)
//					{		
//						headerName = rp.getElementsFromGrid().get(i).getText();
//						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//						
//						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//						}
//						if (rp.VerifyViewMoreOption(headerName)) {
//							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//						}
//					}
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
					if(rp.getNoResult(5) != null){
						log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
					} else	
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//					if (rp.mouseHoverOnNavigationAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					
//					if (rp.mouseHoverOnGridAndGetText()) {
//						log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
//					} else {
//						log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
//						sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
//					}
//					int gridSize = rp.getElementsFromGrid().size();
//					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//					for(int i=0; i<gridSize; i++)
//					{		
//						headerName = rp.getElementsFromGrid().get(i).getText();
//						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//						
//						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//						}
//						if (rp.VerifyViewMoreOption(headerName)) {
//							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//						}
//					}
				}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ARTc048_UpdateCustomMetaDataTypesForTaskAndEvent_VerifyImpact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] FieldName = {ARFieldName10,ARFieldName11}, Value = {null};
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
					if (setup.searchStandardOrCustomObject(environment, mode, object.Custom_Metadata_Types)) {
						log(LogStatus.INFO, "click on Object : " + object.Custom_Metadata_Types, YesNo.No);
						ThreadSleep(2000);
						switchToFrame(driver, 60, setup.getSetUpPageIframe(120));
						if (clickUsingJavaScript(driver, setup.settingTypeManageRecordsButton(MetaDataSetting.Acuity_Setting.toString().replace("_", " "), 10), "Manage Records",
								action.BOOLEAN)) {
							log(LogStatus.INFO, "able to click on Manage Records link", YesNo.No);
							ThreadSleep(1000);
							switchToFrame(driver, 60, setup.getSetUpPageIframe(60));
							if(setup.LabelNameInCustomMetaData(FieldName[i], 10) == null) {
								log(LogStatus.INFO, "yes, We are not able to find " + FieldName[i], YesNo.No);
								refresh(driver);
							}
							else {
								log(LogStatus.ERROR, "no, we are able to find " + FieldName[i], YesNo.No);
								refresh(driver);
							}
							}
						else
						{
							log(LogStatus.INFO, "not able to click on Manage Records link", YesNo.No);
							sa.assertTrue(false, "not able to click on Manage Records link");
						}
							}
					else
					{
						log(LogStatus.ERROR, "Not able to search/click on " + object.Custom_Metadata_Types, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on " + object.Custom_Metadata_Types);
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
		}
	
	@Parameters({ "projectName" })
	@Test
	public void ARTc049_RevertCustomMetaDataTypesForAllObjects_VerifyImpact(String projectName) {
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
					if(rp.getNoResult(5) != null){
						log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
					} else
					if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
						log(LogStatus.INFO,
								"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
										+ searchValue + "---------",
								YesNo.No);
					ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
						if(list.isEmpty()) {
							
							log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
						} else {
							log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
							sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
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
//					int gridSize = rp.getElementsFromGrid().size();
//					log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
//					for(int i=0; i<gridSize; i++)
//					{		
//						headerName = rp.getElementsFromGrid().get(i).getText();
//						String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
//						
//						if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
//							log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
//							sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
//						}
//						if (rp.VerifyViewMoreOption(headerName)) {
//							log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
//						} else {
//							log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
//						}
//					}
				}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ARTc050_VerifyResearchDataForCurrentRecord(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	FundRaisingPageBusinessLayer frp = new FundRaisingPageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword);
	
	String[] searchValues = readAllDataForAColumn(ResearchDataSheetFilePath, "CurrentRecord", 0, false).split("<break>");
	
	for(String searchValue : searchValues) {
		String variable =ExcelUtils.readData(ResearchDataSheetFilePath,"CurrentRecord",excelLabel.Variable_Name, searchValue, excelLabel.ResearchFindings);
		String tabName =ExcelUtils.readData(ResearchDataSheetFilePath,"CurrentRecord",excelLabel.Variable_Name, searchValue, excelLabel.Tab_Name);
		String recordName =ExcelUtils.readData(ResearchDataSheetFilePath,"CurrentRecord",excelLabel.Variable_Name, searchValue, excelLabel.Record_Name);
		if(!tabName.equals("ThemesTab"))
		{
		if (fp.clickOnTab(environment, mode, TabName.valueOf(tabName))) {
		       log(LogStatus.INFO, "Click on Tab : " + TabName.valueOf(tabName), YesNo.No);
		      if (fp.clickOnAlreadyCreatedItem(projectName, TabName.valueOf(tabName),recordName.replace("  ", "").replace("\"", "").trim(), 10)) {
		    	  if(rp.openResearchForCurrentRecord(projectName,ProgressType.Current_Record.toString(),variable,10)) {
			   		   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(searchValue, action.SCROLLANDBOOLEAN, 10);
			   			if(list.isEmpty()) {
			   				
			   				log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
			   				ExcelUtils.writeData(ResearchDataSheetFilePath, "Pass", "CurrentRecord", excelLabel.Variable_Name,
			   						searchValue, excelLabel.Status);
			   				ThreadSleep(1000);
			   			} else {
			   				log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Variable Name: "+ searchValue + "||" + "list : "+list, YesNo.No);
			   				sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Variable: "+ searchValue + "||" + "list : "+list);
			   				ExcelUtils.writeData(ResearchDataSheetFilePath, "Variable: "+ searchValue + "||" + "list : "+list, "CurrentRecord", excelLabel.Variable_Name,
			   						searchValue, excelLabel.Status);
			   				ThreadSleep(1000);
			   			}  	  
		           }
		       } else {
		          sa.assertTrue(false, "Not Able to open created Record : " + recordName);
		           log(LogStatus.SKIP, "Not Able to open created Record: " + recordName, YesNo.Yes);
		           ExcelUtils.writeData(ResearchDataSheetFilePath, "Variable: "+ searchValue + "||" + "Not Able to open created Record: " + recordName, "CurrentRecord", excelLabel.Variable_Name,
	   						searchValue, excelLabel.Status);
		           ThreadSleep(1000);
		      }
		   } else {
		       log(LogStatus.ERROR, "Not able to click on " + TabName.valueOf(tabName) + " tab", YesNo.Yes);
		       sa.assertTrue(false, "Not able to click on " + TabName.valueOf(tabName) + " tab");
		       ExcelUtils.writeData(ResearchDataSheetFilePath, "Variable: "+ searchValue + "||" + "Not able to click on " + TabName.valueOf(tabName), "CurrentRecord", excelLabel.Variable_Name,
  						searchValue, excelLabel.Status);
		       ThreadSleep(1000);
		   }  
		}
		else
		{
			if (lp.clickOnTab(projectName, TabName.Themes.toString())) {

				log(LogStatus.INFO, "Clicked on Tab : "+TabName.Themes.toString(), YesNo.No);
				ThreadSleep(2000);
				String parentWindowID=bp.clickOnThemeRecord(recordName.replace("  ", "").replace("\"", "").trim());
				ThreadSleep(2000);
				if (parentWindowID!=null) {
					log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
					if(rp.openResearchForCurrentRecord(projectName,ProgressType.Current_Record.toString(),variable,10)) {
				   		   ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(searchValue, action.SCROLLANDBOOLEAN, 10);
				   			if(list.isEmpty()) {
				   				
				   				log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------", YesNo.No);
				   				ExcelUtils.writeData(ResearchDataSheetFilePath, "Pass", "CurrentRecord", excelLabel.Variable_Name,
				   						searchValue, excelLabel.Status);
				   				ThreadSleep(1000);
				   			} else {
				   				log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Variable Name: "+ searchValue + "||" + "list : "+list, YesNo.No);
				   				sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Variable: "+ searchValue + "||" + "list : "+list);
				   				ExcelUtils.writeData(ResearchDataSheetFilePath, "Variable: "+ searchValue + "||" + "list : "+list, "CurrentRecord", excelLabel.Variable_Name,
				   						searchValue, excelLabel.Status);
				   				ThreadSleep(1000);
				   				
				   			}  	  
			           }
					driver.close();
					driver.switchTo().window(parentWindowID);
				}
				else
				{
					log(LogStatus.ERROR, "Not able to open "+recordName +" reocrd", YesNo.No);
					sa.assertTrue(false, "Not able to open "+recordName +" reocrd");
					ExcelUtils.writeData(ResearchDataSheetFilePath, "Variable: "+ searchValue + "||" + "Not able to open "+recordName +" reocrd", "CurrentRecord",excelLabel.Variable_Name,
	   						searchValue, excelLabel.Status);
					ThreadSleep(1000);
				}
				
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on tab : "+TabName.Themes.toString(), YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab : "+TabName.Themes.toString());
				ExcelUtils.writeData(ResearchDataSheetFilePath, "Variable: "+ searchValue + "||" + "Not able to click on tab : "+TabName.Themes.toString(), "CurrentRecord",excelLabel.Variable_Name,
   						searchValue, excelLabel.Status);
				ThreadSleep(1000);
			}


		}
	}   
			lp.CRMlogout();
			sa.assertAll();
		}
	
}