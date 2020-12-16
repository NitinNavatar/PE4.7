package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.remote.server.handler.SwitchToWindow;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.android.dx.gen.Local;
import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.ActivityTypes;
import com.navatar.generic.EnumConstants.AddProspectsTab;
import com.navatar.generic.EnumConstants.AddressAction;
import com.navatar.generic.EnumConstants.CheckBox;
import com.navatar.generic.EnumConstants.ClickOrCheckEnableDisableCheckBox;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTab;
import com.navatar.generic.EnumConstants.OfficeLocationLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.RelatedList;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavatarSetUpPageErrorMessage;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.navatar.pageObjects.SDGPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.navatar.pageObjects.TaskPagePageErrorMessage;
import com.relevantcodes.extentreports.LogStatus;

import bsh.org.objectweb.asm.Label;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.BaseLib.testCasesFilePath;
import static com.navatar.generic.CommonLib.*;
public class Toggle extends BaseLib {
	String passwordResetLink = null;

	Scanner scn = new Scanner(System.in);

	@Parameters({ "projectName"})
	@Test
	public void ToggleTc001_createCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
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
					if (setup.createPEUser( crmUser1FirstName, UserLastName, emailId, crmUserLience,
							crmUserProfile)) {
						log(LogStatus.INFO, "CRM User is created Successfully: " + crmUser1FirstName + " " + UserLastName, YesNo.No);
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

		}else{

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

		}

		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
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
			appLog.info("Password is set successfully for CRM User1: " + crmUser1FirstName + " " + UserLastName );
		} else {
			appLog.info("Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void ToggleTc002_1_Prerequisite(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		for (String[] userPass : userAndPassword) {
			lp.CRMLogin(userPass[0], userPass[1], appName);


			String addRemoveTabName="";
			String tab1="";
			if (tabObj1.equalsIgnoreCase("Entity")){
				tab1="Entitie";
			}
			else{
				tab1=tabObj1;
			}
			addRemoveTabName=tab1+"s,"+tabObj2+"s,"+tabObj3+"s,"+tabCustomObj+"s,"+"Tasks"+",Recycle Bin"+",Navatar Setup";
			if (lp.addTab_Lighting( addRemoveTabName, 5)) {
				log(LogStatus.INFO,"Tab added : "+addRemoveTabName,YesNo.No);
			} else {
				log(LogStatus.FAIL,"Tab not added : "+addRemoveTabName,YesNo.No);
				sa.assertTrue(false, "Tab not added : "+addRemoveTabName);
			}		



			ThreadSleep(5000);
			lp.CRMlogout();
			closeBrowser();
			config(ExcelUtils.readDataFromPropertyFile("Browser"));
			lp = new LoginPageBusinessLayer(driver);

		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void ToggleTc002_2_AddListView(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);


		String[] tabs= {tabObj1,tabObj2,tabObj3,tabCustomObj};
		TabName[] tab= {TabName.Object1Tab,TabName.Object2Tab,TabName.Object3Tab,TabName.TestCustomObjectTab};
		int i=0;
		for (TabName t:tab) {

			if (lp.clickOnTab(projectName, t)) {	
				if (lp.addAutomationAllListView(projectName, tabs[i], 10)) {
					log(LogStatus.INFO,"list view added on "+tabs[i],YesNo.No);
				} else {
					log(LogStatus.FAIL,"list view could not added on "+tabs[i],YesNo.Yes);
					sa.assertTrue(false, "list view could not added on "+tabs[i]);
				}
			} else {
				log(LogStatus.FAIL,"could not click on "+tabs[i],YesNo.Yes);
				sa.assertTrue(false, "could not click on "+tabs[i]);
			}
			i++;
			ThreadSleep(5000);
		}


		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void ToggleTc003_1_CreatePreconditionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin("pe2.2.1_trial4545_admin@navatarplatform.com", adminPassword, appName);

		String value="";
		String type="";
		String website="";
		String[][] EntityOrAccounts = {{ ToggleIns1, ToggleIns1RecordType ,null}};
		
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				website=accounts[2];
				if (ip.createEntityOrAccount(projectName, value, type, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}

	

		String[][] fundsOrDeals = {{ToggleFund1,ToggleFund1Type,ToggleFund1Category,ToggleFund1RecordType},
				{ToggleFund2,ToggleFund2Type,ToggleFund2Category,ToggleFund2RecordType}};
		for (String[] funds : fundsOrDeals) {
			if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object3Tab,YesNo.No);	


					if (fp.createFundPE(projectName, funds[0], funds[3], funds[1], funds[2], null, 15)) {
						log(LogStatus.INFO,"Created Fund : "+funds[0],YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Fund : "+funds[0]);
						log(LogStatus.SKIP,"Not Able to Create Fund  : "+funds[0],YesNo.Yes);
					}
				
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
			}
		}
		
		
		
			if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	


					if (fp.createDeal(projectName,ToggleDeal1,ToggleDeal1CompanyName, ToggleDeal1RecordType, ToggleDeal1Stage,null, 15)) {
						log(LogStatus.INFO,"Created Deal : "+ToggleDeal1,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Deal  : "+ToggleDeal1);
						log(LogStatus.SKIP,"Not Able to Create Deal  : "+ToggleDeal1,YesNo.Yes);
					}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object4Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object4Tab,YesNo.Yes);
			}
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void ToggleTc005_VerifyTheToggleButtonAndFunctionalityOfToggleButtonOnInstitutionPageRecord(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SDGPageBusinessLayer sg = new SDGPageBusinessLayer(driver);

		lp.CRMLogin("pe2.2.1_trial4604_admin@navatarplatform.com", adminPassword, appName);

		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);

			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, ToggleIns1, 15)) {
				log(LogStatus.INFO,"account/entity found: "+ToggleIns1, YesNo.Yes);
				ThreadSleep(2000);

				if (click(driver, ip.getRelatedTab(projectName, PageName.Object1Page, RelatedTab.Investment, 5), RelatedTab.Investment.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO,"Click on Sub Tab : "+RelatedTab.Investment,YesNo.No);
					ThreadSleep(2000);

					String toggleBtn = ToggleButton.Fund_Investments.toString();

					if (ip.toggleButton(projectName, PageName.Object1Page, toggleBtn, action.BOOLEAN, 30)!=null) {
						log(LogStatus.INFO,"Toggle is present : "+toggleBtn,YesNo.No);
						ThreadSleep(2000);

						if (ip.toggleSDGButtons(projectName, PageName.Object1Page, toggleBtn,ToggleButtonGroup.SDGButton, action.BOOLEAN, 30)!=null) {
							log(LogStatus.PASS,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" is present",YesNo.Yes);
						} else {
							sa.assertTrue(false,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" should be present");
							log(LogStatus.FAIL,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" should be present",YesNo.Yes);
						}

						WebElement ele=ip.getFundNameAtToggle(projectName, PageName.Object1Page, ToggleFund1, action.BOOLEAN, 30);
						if(mouseOverOperation(edriver, ele)){
							log(LogStatus.PASS,"Able to mouse hover : "+ToggleFund1,YesNo.No);
							String text = ip.getFundNameAtToggleToolTip(projectName, PageName.Object1Page, ToggleFund1, action.BOOLEAN, 30).getAttribute("title");
							if(text.contains(ToggleFund1)){
								appLog.info("Tool tip error message is verified for : "+ToggleFund1);
							} else {
								sa.assertTrue(false,"Tool tip error message is not verified. Expected: "+ToggleFund1+ "\tActual: "+text);
								log(LogStatus.SKIP,"Tool tip error message is not verified. Expected: "+ToggleFund1+ "\tActual: "+text,YesNo.Yes);
							}
						} else {
							log(LogStatus.SKIP,"Not Able to mouse hover : "+ToggleFund1,YesNo.Yes);
							sa.assertTrue(false,"Not Able to mouse hover : "+ToggleFund1);
						}
						
						String lp11 ="Alexa Limited partner";
						ele=ip.getLegalEntityAtToggle(projectName, PageName.Object1Page, lp11, action.BOOLEAN, 30);
						if(mouseOverOperation(edriver, ele)){
							ThreadSleep(3000);
							log(LogStatus.PASS,"Able to mouse hover : "+lp11,YesNo.No);
							if (clickUsingJavaScript(driver, ip.getInlineOrLockedAtToggle(projectName, PageName.Object1Page, lp11, action.BOOLEAN, 10), "Inline Edit Icon : "+lp11, action.BOOLEAN)) {
								log(LogStatus.INFO,"click on inline edit icon "+lp11,YesNo.No);
								ThreadSleep(2000);
								
							} else {
								log(LogStatus.SKIP,"Not Able to click on inline edit icon "+lp11,YesNo.Yes);
								sa.assertTrue(false,"Not Able to click on inline edit icon "+lp11);
							}
						} else {
							log(LogStatus.SKIP,"Not Able to mouse hover : "+ToggleFund1,YesNo.Yes);
							sa.assertTrue(false,"Not Able to mouse hover : "+ToggleFund1);
						}
						
						
					} else {
						sa.assertTrue(false,"Toggle should be present : "+toggleBtn);
						log(LogStatus.SKIP,"Toggle should be present : "+toggleBtn,YesNo.Yes);
					}

					//  

//					toggleBtn = ToggleButton.CoInvestments.toString();
//
//					if (click(driver, ip.toggleButton(projectName, PageName.Object1Page, toggleBtn, action.BOOLEAN, 30), toggleBtn, action.BOOLEAN)) {
//						log(LogStatus.INFO,"Not Able to Click on Toggle : "+toggleBtn,YesNo.No);
//						ThreadSleep(2000);
//
//						if (ip.toggleSDGButtons(projectName, PageName.Object1Page, toggleBtn,ToggleButtonGroup.SDGButton, action.BOOLEAN, 30)!=null) {
//							sa.assertTrue(false,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" is present");
//						} else {
//							sa.assertTrue(false,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" should be present");
//							log(LogStatus.FAIL,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" should be present",YesNo.Yes);
//						}
//
//						WebElement ele=ip.getFundNameAtToggle(projectName, PageName.Object1Page, ToggleFund2, action.BOOLEAN, 30);
//						if(mouseHoverJScript(driver, ele)){
//							String text = trim(getText(driver, ip.getFundNameAtToggle(projectName, PageName.Object1Page, ToggleFund2, action.BOOLEAN, 30), ToggleFund2, action.BOOLEAN));
//							if(text.contains(ToggleFund2)){
//								appLog.info("Tool tip error message is verified for : "+ToggleFund2);
//							} else {
//								sa.assertTrue(false,"Tool tip error message is not verified. Expected: "+ToggleFund2+ "\tActual: "+text);
//								log(LogStatus.SKIP,"Tool tip error message is not verified. Expected: "+ToggleFund2+ "\tActual: "+text,YesNo.Yes);
//							}
//						} else {
//							log(LogStatus.SKIP,"Not Able to mouse hover : "+ToggleFund2,YesNo.Yes);
//							sa.assertTrue(false,"Not Able to mouse hover : "+ToggleFund2);
//						}
//						
//					} else {
//						sa.assertTrue(false,"Not Able to Click on Toggle : "+toggleBtn);
//						log(LogStatus.SKIP,"Not Able to Click on Toggle : "+toggleBtn,YesNo.Yes);
//					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Sub Tab : "+RelatedTab.Investment);
					log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+RelatedTab.Investment,YesNo.Yes);
				}

			}else {

				log(LogStatus.ERROR,"account/entity not found: "+ToggleIns1, YesNo.Yes);
				sa.assertTrue(false,"account/entity not found: "+ToggleIns1);
			}


		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void ToggleTc006_CreateNewCustomSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		SDGPageBusinessLayer sg = new SDGPageBusinessLayer(driver);
		
		lp.CRMLogin("pe2.2.1_trial4545_admin@navatarplatform.com", adminPassword, appName);
		if (lp.searchAndClickOnApp(SDG, 30)) {
			log(LogStatus.INFO,"Able to Click/Search : "+SDG+" going to create custom SDG",YesNo.No);	 
			ThreadSleep(3000);
			
			if (lp.clickOnTab(projectName, TabName.SDGTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.SDGTab,YesNo.No);
				
				String[][] sdgLabels = {{SDGCreationLabel.SDG_Name.toString(),Sdg1Name},
						{SDGCreationLabel.SDG_Tag.toString(),Sdg1TagName},
						{SDGCreationLabel.sObjectName.toString(),Sdg1ObjectName}};
				
				if (sg.createCustomSDG(projectName, Sdg1Name, sdgLabels, action.BOOLEAN, 20)) {
					log(LogStatus.PASS,"create/verify created SDG : "+Sdg1Name,YesNo.No);
				} else {
					sa.assertTrue(false,"Not Able to create/verify created SDG : "+Sdg1Name);
					log(LogStatus.SKIP,"Not Able to create/verify created SDG : "+Sdg1Name,YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.SDGTab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.SDGTab,YesNo.Yes);
			}
			
		} else {
			sa.assertTrue(false,"Not Able to Click/Search : "+SDG+" so can not create custom SDG");
			log(LogStatus.SKIP,"Not Able to Click/Search : "+SDG+" so can not create custom SDG",YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	


}
	
	