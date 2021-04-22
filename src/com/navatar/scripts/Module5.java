package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
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
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
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
import com.navatar.generic.EnumConstants.SDGLabels;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
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
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.EditPageBusinessLayer;
import com.navatar.pageObjects.EditPageErrorMessage;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.MarketingEventPageBusinessLayer;
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
import static com.navatar.generic.BaseLib.phase1DataSheetFilePath;
import static com.navatar.generic.CommonLib.*;

public class Module5 extends BaseLib {
	String passwordResetLink = null;

	Scanner scn = new Scanner(System.in);
	String breakSP = "<break>";
	String columnSP = "<column>";
	String commaSP = ",";
	String colonSP = ":";
	String emptyString="";
	
	@Parameters({ "projectName"})
	@Test
	public void Module5TC001_createCRMUser(String projectName) {
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
	public void Module5TC002_1_Prerequisite(String projectName) {
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
			addRemoveTabName=tab1+"s,"+tabObj5+"s,"+tabObj3+"s,"+tabObj4+"s,"+"Partnerships,Commitments";
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
	public void Module5TC002_2_AddListView(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String[] tabs= {tabObj1,tabObj5,tabObj3,tabObj4};
		TabName[] tab= {TabName.Object1Tab,TabName.Object5Tab,TabName.Object3Tab,TabName.Object4Tab};
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
	public void Module5TC003_1_CreatePreconditionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer  dp = new DealPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ ToggleIns1, ToggleIns1RecordType ,null}};

		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
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

		// scn.nextLine();

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

		// scn.nextLine();

		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	


			if (fp.createDeal(projectName,ToggleDeal1RecordType,ToggleDeal1,ToggleDeal1CompanyName, ToggleDeal1Stage,null, 15)) {
				log(LogStatus.INFO,"Created Deal : "+ToggleDeal1,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Deal  : "+ToggleDeal1);
				log(LogStatus.SKIP,"Not Able to Create Deal  : "+ToggleDeal1,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object4Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object4Tab,YesNo.Yes);
		}

		// scn.nextLine();
		
		
		if (lp.clickOnTab(projectName, TabName.Object5Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object5Tab,YesNo.No);	
			ToggleMarketingEvent1Date=	previousOrForwardDateAccordingToTimeZone(0, "M/d/YYYY", BasePageBusinessLayer.AmericaLosAngelesTimeZone);;
			ExcelUtils.writeData(phase1DataSheetFilePath, ToggleMarketingEvent1Date, "MarketingEvent", excelLabel.Variable_Name, "TOGGLEME1",excelLabel.Date);
			
			if (me.createMarketingEvent(projectName, ToggleMarketingEvent1Name, ToggleMarketingEvent1RecordType, ToggleMarketingEvent1Date, ToggleMarketingEvent1Organizer, 10)) {
				log(LogStatus.INFO,"Created Marketing Event : "+ToggleMarketingEvent1Name,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Marketing Event  : "+ToggleMarketingEvent1Name);
				log(LogStatus.SKIP,"Not Able to Create Marketing Event  : "+ToggleMarketingEvent1Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object5Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object5Tab,YesNo.Yes);
		}
	//	// scn.nextLine();
		String relatedTab;
		ToggleOpenQA1RequestedDate=	previousOrForwardDateAccordingToTimeZone(-2, "M/d/YYYY", BasePageBusinessLayer.AmericaLosAngelesTimeZone);;
		ExcelUtils.writeData(phase1DataSheetFilePath, ToggleOpenQA1RequestedDate, "DealRequestTracker", excelLabel.Variable_Name, "OPENQA1",excelLabel.Date_Requested);
		
		ToggleClosedQA1RequestedDate=	previousOrForwardDateAccordingToTimeZone(-5, "M/d/YYYY", BasePageBusinessLayer.AmericaLosAngelesTimeZone);;
		ExcelUtils.writeData(phase1DataSheetFilePath, ToggleClosedQA1RequestedDate, "DealRequestTracker", excelLabel.Variable_Name, "CLOSEDQA1",excelLabel.Date_Requested);
		
		
		String[][] openRequest = {{PageLabel.Date_Requested.toString(),ToggleOpenQA1RequestedDate}
								,{PageLabel.Request.toString(),ToggleOpenQA1Request}
										,{PageLabel.Status.toString(),ToggleOpenQA1Status}};
		
		String[][] closeRequest = {{PageLabel.Date_Requested.toString(),ToggleClosedQA1RequestedDate}
						,{PageLabel.Request.toString(),ToggleClosedQA1Request},{PageLabel.Status.toString(),ToggleClosedQA1Status}};
		
		
		for (int i = 0; i < 2; i++) {
			if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	

				if (ip.clickOnAlreadyCreatedItem(projectName, ToggleDeal1, true, 15)) {
					log(LogStatus.INFO,"Item found: "+ToggleDeal1, YesNo.No);
					ThreadSleep(2000);
					
					relatedTab=ToggleCheck2RelatedTab;
					if (click(driver, ip.getRelatedTab(projectName, relatedTab, 5), relatedTab.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
						ThreadSleep(2000);
						if (i==0) {
						dp.createNewRequest(projectName, ToggleDeal1, openRequest , 10);
						}else {
						dp.createNewRequest(projectName, ToggleDeal1, closeRequest , 10);
						}
						
						} else {
						sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
						log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
					}
					
				}else {

						log(LogStatus.ERROR,"Item not found: "+ToggleDeal1, YesNo.Yes);
						sa.assertTrue(false,"Item not found: "+ToggleDeal1);
					}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object4Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object4Tab,YesNo.Yes);
			}
		}
		// scn.nextLine();
		String[][] attendee1 = {{PageLabel.Attendee_Staff.toString(),AdminUserFirstName+" "+AdminUserLastName}
									,{PageLabel.Status.toString(),"Invited"}};

		String[][] attendee2 = {{PageLabel.Attendee_Staff.toString(),AdminUserFirstName+" "+AdminUserLastName}
										,{PageLabel.Status.toString(),"Invited"}};
		for (int i = 0; i < 2; i++) {
			if (lp.clickOnTab(projectName, TabName.Object5Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object5Tab,YesNo.No);	

				if (ip.clickOnAlreadyCreatedItem(projectName, ToggleMarketingEvent1Name, true, 15)) {
					log(LogStatus.INFO,"Item found: "+ToggleMarketingEvent1Name, YesNo.No);
					ThreadSleep(2000);
					
					relatedTab="Related";
					if (click(driver, ip.getRelatedTab(projectName, relatedTab, 5), relatedTab.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
						ThreadSleep(2000);
						if (i==0) {
						me.createAttendee(projectName, ToggleMarketingEvent1Name, attendee1,action.BOOLEAN , 10);
						}else {
						me.createAttendee(projectName, ToggleMarketingEvent1Name, attendee2,action.BOOLEAN , 10);
						}
						
						} else {
						sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
						log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
					}
					
				}else {

						log(LogStatus.ERROR,"Item not found: "+ToggleMarketingEvent1Name, YesNo.Yes);
						sa.assertTrue(false,"Item not found: "+ToggleMarketingEvent1Name);
					}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object5Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object5Tab,YesNo.Yes);
			}
		}

		if(bp.clickOnTab(projectName,TabName.Object1Tab)) {

			if(ins.createInstitution(environment, mode, ToggleLP1, "Limited Partner", InstitutionPageFieldLabelText.Parent_Institution.toString(), ToggleIns1)) {
				appLog.info("limited partner is created: "+ToggleLP1);
			}else {
				appLog.error("Not able to create limited partner: "+ToggleLP1);
				sa.assertTrue(false, "Not able to create limited partner: "+ToggleLP1);
			}
		}else {
			appLog.error("Not able to click on institution tab so cannot create limite partner: "+ToggleLP1);
			sa.assertTrue(false, "Not able to click on institution tab so cannot create limite partner: "+ToggleLP1);
		}
		
		if(bp.clickOnTab(projectName,TabName.PartnershipsTab)) {
			if(ins.createPartnership(environment,mode,TogglePartnerShip1,ToggleFund1)) {
				appLog.info("partnership is created: "+TogglePartnerShip1);
			}else {
				appLog.error("Not able to create partnership: "+TogglePartnerShip1);
				sa.assertTrue(false, "Not able to create partnership: "+TogglePartnerShip1);
			}
		}else {
			appLog.error("Not able to click on partnership tab so cannot create partnership: "+TogglePartnerShip1);
			sa.assertTrue(false, "Not able to click on partnership tab so cannot create partnership: "+TogglePartnerShip1);
		}
	
		if(bp.clickOnTab(projectName,TabName.PartnershipsTab)) {
			if(ins.createPartnership(environment,mode,TogglePartnerShip2,ToggleFund2)) {
				appLog.info("partnership is created: "+TogglePartnerShip2);
			}else {
				appLog.error("Not able to create partnership: "+TogglePartnerShip2);
				sa.assertTrue(false, "Not able to create partnership: "+TogglePartnerShip2);
			}
		}else {
			appLog.error("Not able to click on partnership tab so cannot create partnership: "+TogglePartnerShip2);
			sa.assertTrue(false, "Not able to click on partnership tab so cannot create partnership: "+TogglePartnerShip2);
		}
		
		if(bp.clickOnTab(projectName,TabName.CommitmentsTab)) {

			if(ins.createCommitment(environment, mode,ToggleLP1,TogglePartnerShip1,"TOGGLECMT1", phase1DataSheetFilePath)) {
				appLog.info("commitment is created successfully");
			}else {
				appLog.error("Not able to create commitment for limited partner: "+ToggleLP1+" and partnership Name: "+TogglePartnerShip1);
				sa.assertTrue(false, "Not able to create commitment for limited partner: "+ToggleLP1+" and partnership Name: "+TogglePartnerShip1);
			}
		}else {
			appLog.error("Not able to click on commitment tab so cannot create committment for limite partner: "+ToggleLP1+" and partnership Name: "+TogglePartnerShip1);
			sa.assertTrue(false, "Not able to click on commitment tab so cannot create committment for limite partner: "+ToggleLP1+" and partnership Name: "+TogglePartnerShip1);
		}
		
		if(bp.clickOnTab(projectName,TabName.CommitmentsTab)) {

			if(ins.createCommitment(environment, mode,ToggleLP1,TogglePartnerShip2,"TOGGLECMT2", phase1DataSheetFilePath)) {
				appLog.info("commitment is created successfully");
			}else {
				appLog.error("Not able to create commitment for limited partner: "+ToggleLP1+" and partnership Name: "+TogglePartnerShip2);
				sa.assertTrue(false, "Not able to create commitment for limited partner: "+ToggleLP1+" and partnership Name: "+TogglePartnerShip2);
			}
		}else {
			appLog.error("Not able to click on commitment tab so cannot create committment for limite partner: "+ToggleLP1+" and partnership Name: "+TogglePartnerShip2);
			sa.assertTrue(false, "Not able to click on commitment tab so cannot create committment for limite partner: "+ToggleLP1+" and partnership Name: "+TogglePartnerShip2);
		}
		
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void Module5TC004_VerifyTheToggleButtonAndFunctionalityOfToggleButtonOnPageRecord(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] tabNames = {ToggleCheck1TabName,ToggleCheck2TabName,ToggleCheck3TabName};
		String tabName;


		String[] itemValues = {ToggleCheck1ItemName,ToggleCheck2ItemName,ToggleCheck3ItemName};
		String itemValue;

		String[] relatedTabs = {ToggleCheck1RelatedTab,ToggleCheck2RelatedTab,ToggleCheck3RelatedTab};
		String relatedTab;

		String toggleButtons[] = {ToggleCheck1ToggleButtons,ToggleCheck2ToggleButtons,ToggleCheck3ToggleButtons};
		
		String columnNames[] = {ToggleCheck1ColumnName,ToggleCheck2ColumnName,ToggleCheck3ColumnName};
		String[] columnName=null;
		String[] toggleColumn=null;
		String cName;
		WebElement ele;
		
		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Click on Tab : "+tabName,YesNo.No);

				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO,"Item found: "+itemValue, YesNo.Yes);
					ThreadSleep(2000);

					relatedTab=relatedTabs[i];
					if (click(driver, ip.getRelatedTab(projectName, relatedTab, 5), relatedTab.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
						ThreadSleep(2000);

						String[] toggles = toggleButtons[i].split(breakSP);
						columnName=columnNames[i].split(breakSP);
						String toggleBtn = "";
						for (int j = 0; j < toggles.length; j++) {
							toggleBtn = toggles[j];
							toggleColumn=columnName[j].split(columnSP);
							if (j==0) {
								
								if (ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30)!=null) {
									log(LogStatus.INFO,"Toggle is present : "+toggleBtn,YesNo.No);
									ThreadSleep(2000);
								} else {
									sa.assertTrue(false,"Toggle should be present : "+toggleBtn);
									log(LogStatus.SKIP,"Toggle should be present : "+toggleBtn,YesNo.Yes);
								}
								if (ip.toggleSDGButtons(projectName, toggleBtn,ToggleButtonGroup.SDGButton, action.BOOLEAN, 30)!=null) {
									log(LogStatus.PASS,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" is present",YesNo.Yes);
								} else {
									sa.assertTrue(false,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" should be present");
									log(LogStatus.FAIL,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" should be present",YesNo.Yes);
								}
								
								for (int c = 0; c < toggleColumn.length; c++) {
									cName = toggleColumn[c];
									ele = ip.toggleButtonColumnNames(projectName, toggleBtn, cName, action.BOOLEAN, 2);
									if (ele!=null) {
										log(LogStatus.PASS,"At "+toggleBtn+" "+cName+" is present",YesNo.No);
											
									} else {
										sa.assertTrue(false,"At "+toggleBtn+" "+cName+" should be present");
										log(LogStatus.FAIL,"At "+toggleBtn+" "+cName+" should be present",YesNo.Yes);
								
									}
								}
								
								
								if (i==1) {
									if (dp.verifyingOpenRequest(projectName, ToggleOpenQA1ID, ToggleOpenQA1RequestedDate, ToggleOpenQA1Request, ToggleOpenQA1Status, 10)) {
										log(LogStatus.PASS,toggleBtn+" Records Verified : "+ToggleOpenQA1Request,YesNo.Yes);
									} else {
										sa.assertTrue(false,toggleBtn+" Records Not Verified : "+ToggleOpenQA1Request);
										log(LogStatus.FAIL,toggleBtn+" Records Not Verified : "+ToggleOpenQA1Request,YesNo.Yes);
									}
									ThreadSleep(1000);
									if (mouseHoverJScript(driver, dp.getRequestAtToggle(projectName, null, ToggleOpenQA1Request, action.SCROLLANDBOOLEAN, 10), ToggleOpenQA1Request, 10)) {
										log(LogStatus.INFO,"Mouse hover on : "+ToggleOpenQA1Request,YesNo.No);
									} else {
										sa.assertTrue(false,"Not ABle to Mouse hover on : "+ToggleOpenQA1Request);
										log(LogStatus.FAIL,"Not ABle to Mouse hover on : "+ToggleOpenQA1Request,YesNo.Yes);
									}
									ThreadSleep(1000);
									if (dp.isRequestAtToggleToolTip(projectName, null, ToggleOpenQA1Request, action.SCROLLANDBOOLEAN, 10)) {
										log(LogStatus.INFO,"Tool Tip Verified : "+ToggleOpenQA1Request,YesNo.No);
									} else {
										sa.assertTrue(false,"Tool Tip Not Verified : "+ToggleOpenQA1Request);
										log(LogStatus.FAIL,"Tool Tip Not Verified : "+ToggleOpenQA1Request,YesNo.Yes);
									}
//									ele = dp.getEditBtn(projectName, ToggleOpenQA1Request, action.SCROLLANDBOOLEAN, 10);
//									if (clickUsingJavaScript(driver, ele, ToggleOpenQA1Request, action.BOOLEAN)) {
//										log(LogStatus.INFO,"Click on Edit Btn : "+ToggleOpenQA1Request,YesNo.No);
//										ThreadSleep(5000);
//										
//										if (click(driver, dp.getTextArea(20), ToggleOpenQA1Request, action.BOOLEAN)) {
//											log(LogStatus.INFO,"Click on text area for "+ToggleOpenQA1Request,YesNo.No);
//											ThreadSleep(5000);
//											try {
//												dp.getTextArea(20).clear();
//												
//												 Robot robot = new Robot();  // Robot class throws AWT Exception	
//										           Thread.sleep(1000); // Thread.sleep throws InterruptedException	
//										           robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//										           Thread.sleep(1000);
//										           robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//										           Thread.sleep(1000);
//										           
//											} catch (Exception e) {
//												// TODO Auto-generated catch block
//												e.printStackTrace();
//											}
//											if (sendKeys(driver, dp.getTextArea(20), ToggleOpenQA1Request+"s", ToggleOpenQA1Request, action.BOOLEAN)) {
//												log(LogStatus.INFO,"enter value on textarea "+ToggleOpenQA1Request,YesNo.No);
//												
//												ThreadSleep(2000);
//												 try {
//													Robot robot = new Robot();  // Robot class throws AWT Exception	
//													   Thread.sleep(1000); // Thread.sleep throws InterruptedException	
//													   robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//													   Thread.sleep(1000);
//													   robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//													   Thread.sleep(1000);
//												} catch (AWTException e) {
//													// TODO Auto-generated catch block
//													e.printStackTrace();
//												} catch (InterruptedException e) {
//													// TODO Auto-generated catch block
//													e.printStackTrace();
//												}
//												if (ip.toggleEditSaveButton(projectName, toggleBtn, action.BOOLEAN, 30)!=null) {
//													log(LogStatus.INFO,"Edit Save button verified "+ToggleOpenQA1Request,YesNo.Yes);
//												} else {
//													sa.assertTrue(false,"Edit Save button not verified "+ToggleOpenQA1Request);
//													log(LogStatus.SKIP,"Edit Save button not verified "+ToggleOpenQA1Request,YesNo.Yes);
//												}
//												if (ip.toggleEditCancelButton(projectName, toggleBtn, action.BOOLEAN, 30)!=null) {
//													log(LogStatus.INFO,"Edit Cancel button verified "+ToggleOpenQA1Request,YesNo.Yes);
//												} else {
//													sa.assertTrue(false,"Edit Cancel button not verified "+ToggleOpenQA1Request);
//													log(LogStatus.SKIP,"Edit Cancel button not verified "+ToggleOpenQA1Request,YesNo.Yes);
//												}
//												
//											} else {
//												sa.assertTrue(false,"Not Able to enter value on textarea "+ToggleOpenQA1Request);
//												log(LogStatus.SKIP,"Not Able to enter value on textarea "+ToggleOpenQA1Request,YesNo.Yes);
//											}
//										} else {
//											sa.assertTrue(false,"Not Able to Click on text area for "+ToggleOpenQA1Request);
//											log(LogStatus.SKIP,"Not Able to Click on text area for "+ToggleOpenQA1Request,YesNo.Yes);
//										}
//										
//										
//									} else {
//										sa.assertTrue(false,"Not Able to Click on Edit Btn : "+ToggleOpenQA1Request);
//										log(LogStatus.SKIP,"Not Able to Click on Edit Btn : "+ToggleOpenQA1Request,YesNo.Yes);
//									}
									
								}
								
							} else {

								if (click(driver, ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30), toggleBtn, action.BOOLEAN)) {
									log(LogStatus.INFO,"Able to Click on Toggle : "+toggleBtn,YesNo.No);
									ThreadSleep(2000);
									if (ip.toggleSDGButtons(projectName, toggleBtn,ToggleButtonGroup.SDGButton, action.BOOLEAN, 30)!=null) {
										log(LogStatus.PASS,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" is present",YesNo.Yes);
									} else {
										sa.assertTrue(false,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" should be present");
										log(LogStatus.FAIL,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" should be present",YesNo.Yes);
									}
									for (int c = 0; c < toggleColumn.length; c++) {
										cName = toggleColumn[c];
										ele = ip.toggleButtonColumnNames(projectName, toggleBtn, cName, action.BOOLEAN, 2);
										if (ele!=null) {
											log(LogStatus.PASS,"At "+toggleBtn+" "+cName+" is present",YesNo.No);
												
										} else {
											sa.assertTrue(false,"At "+toggleBtn+" "+cName+" should be present");
											log(LogStatus.FAIL,"At "+toggleBtn+" "+cName+" should be present",YesNo.Yes);
									
										}
									}
									
									if (i==1) {
										if (dp.verifyingClosedRequest(projectName, ToggleClosedQA1ID, ToggleClosedQA1RequestedDate, ToggleClosedQA1Request, 10)) {
											log(LogStatus.PASS,toggleBtn+" Records Verified : "+ToggleClosedQA1Request,YesNo.Yes);
										} else {
											sa.assertTrue(false,toggleBtn+" Records Not Verified : "+ToggleClosedQA1Request);
											log(LogStatus.FAIL,toggleBtn+" Records Not Verified : "+ToggleClosedQA1Request,YesNo.Yes);
										}
									}
									
									
								} else {
									sa.assertTrue(false,"Not Able to Click on Toggle : "+toggleBtn);
									log(LogStatus.SKIP,"Not Able to Click on Toggle : "+toggleBtn,YesNo.Yes);
								}
								
							}
						}
						
						
						
						

					} else {
						sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
						log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
					}

				}else {

					log(LogStatus.ERROR,"Item not found: "+itemValue, YesNo.Yes);
					sa.assertTrue(false,"Item not found: "+itemValue);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName,YesNo.Yes);
			}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module5TC005_VerifyTheRetainAndDefaultSelectionOfToggleButton(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String[] tabNames = {ToggleCheck1TabName,ToggleCheck2TabName,ToggleCheck3TabName};
		String tabName;

		String[] itemValues = {ToggleCheck1ItemName,ToggleCheck2ItemName,ToggleCheck3ItemName};
		String itemValue;

		String[] relatedTabs = {ToggleCheck1RelatedTab,ToggleCheck2RelatedTab,ToggleCheck3RelatedTab};
		String relatedTab;

		String toggleButtons[] = {ToggleCheck1ToggleButtons,ToggleCheck2ToggleButtons,ToggleCheck3ToggleButtons};
		String[] sdgToggle=null;
		String[] setDefaultSdgToggle=null;
		String toggleValue="";

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Click on Tab : "+tabName,YesNo.No);

				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO,"Item found: "+itemValue, YesNo.Yes);
					ThreadSleep(2000);
					
					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO,"click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);	
						//// scn.nextLine();
						switchToDefaultContent(driver);
						switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));

						relatedTab=relatedTabs[i];
						if (click(driver, ip.getRelatedTab(projectName, relatedTab, 5), relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
							ThreadSleep(2000);

							String[] toggles = toggleButtons[i].split(breakSP);
							String toggleBtn = "";
							for (int j = 0; j < 1; j++) {
								toggleBtn = toggles[j];
								if (click(driver, ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30), toggleBtn, action.BOOLEAN)) {
									log(LogStatus.INFO,"Able to Click on Toggle : "+toggleBtn,YesNo.No);
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									ThreadSleep(20000);
									String sdgToggles=getValueFromElementUsingJavaScript(driver, edit.getsdgConfigDataProviderTextBox(projectName, 10), "sdg Config Data Provider TextBox");
									System.err.println(">>>>> + "+sdgToggles);
									//// scn.nextLine();
									if (sdgToggles!=null) {

										sdgToggle=sdgToggles.split(commaSP);
										setDefaultSdgToggle=sdgToggle[1].split(colonSP);
										toggleValue = setDefaultSdgToggle[1];

										if (sendKeys(driver, edit.getDefaultSDGToggleTextBox(projectName, 10),toggleValue,"Default SDG Toggle TextBox: "+toggleValue,action.BOOLEAN)) {
											ThreadSleep(500);
											log(LogStatus.INFO,"send value to Default SDG Toggle TextBox : "+toggleValue,YesNo.No);
											if (click(driver, edit.getEditPageSaveButton(projectName, 10),"Edit Page Save Button", action.BOOLEAN)) {
												log(LogStatus.INFO,"Click on Edit Page Save Button",YesNo.No);
												ThreadSleep(5000);
												switchToDefaultContent(driver);
												switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
												if (ip.toggleSDGButtons(projectName, toggleBtn,ToggleButtonGroup.SDGButton, action.BOOLEAN, 10)==null) {
													log(LogStatus.PASS,"After Save "+toggleBtn+" is not selected ",YesNo.No);
												} else {
													sa.assertTrue(false,"After Save "+toggleBtn+" should not be selected ");
													log(LogStatus.FAIL,"After Save "+toggleBtn+" should not be selected ",YesNo.Yes);
												}

												toggleBtn = toggles[j+1];
												if (ip.toggleSDGButtons(projectName, toggleBtn,ToggleButtonGroup.SDGButton, action.BOOLEAN, 10)!=null) {
													log(LogStatus.PASS,"After Save "+toggleBtn+" is selected ",YesNo.No);
												} else {
													sa.assertTrue(false,"After Save "+toggleBtn+" should be selected ");
													log(LogStatus.FAIL,"After Save "+toggleBtn+" should be selected ",YesNo.Yes);
												}

											} else {
												sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
												log(LogStatus.FAIL,"Not Able to Click on Edit Page Save Button",YesNo.Yes);
											}

										} else {
											sa.assertTrue(false, "Not Able to send value to Default SDG Toggle TextBox : "+toggleValue);
											log(LogStatus.FAIL,"Not Able to send value to Default SDG Toggle TextBox : "+toggleValue,YesNo.Yes);
										}

										/////////////////////////////////////////////

										switchToDefaultContent(driver);
										toggleValue = emptyString;
										if (sendKeys(driver, edit.getDefaultSDGToggleTextBox(projectName, 10),toggleValue,"Default SDG Toggle TextBox",action.BOOLEAN)) {
											ThreadSleep(200);
											log(LogStatus.INFO,"send value to Default SDG Toggle TextBox : "+toggleValue,YesNo.No);
											if (click(driver, edit.getEditPageSaveButton(projectName, 10),"Edit Page Save Button", action.BOOLEAN)) {
												log(LogStatus.INFO,"Click on Edit Page Save Button",YesNo.No);;
												ThreadSleep(5000);

												if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),"Edit Page Back Button", action.BOOLEAN)) {
													log(LogStatus.INFO,"Click on Edit Page Back Button",YesNo.No);
													//// scn.nextLine();
													ThreadSleep(10000);

													if (click(driver, ip.getRelatedTab(projectName,relatedTab, 30), relatedTab, action.BOOLEAN)) {
														log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
														ThreadSleep(2000);

														toggleBtn = toggles[j];
														if (ip.toggleSDGButtons(projectName, toggleBtn,ToggleButtonGroup.SDGButton, action.BOOLEAN, 10)!=null) {
															log(LogStatus.PASS,"After Save "+toggleBtn+" is selected ",YesNo.No);
														} else {
															sa.assertTrue(false,"After Save "+toggleBtn+" should be selected ");
															log(LogStatus.FAIL,"After Save "+toggleBtn+" should be selected ",YesNo.Yes);
														}



													} else {
														sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
														log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
													}

												} else {
													sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
													log(LogStatus.SKIP,"Not Able to Click on Edit Page Back Button",YesNo.Yes);
												}

											} else {
												sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
												log(LogStatus.SKIP,"Not Able to Click on Edit Page Save Button",YesNo.Yes);
											}

										} else {
											sa.assertTrue(false, "Not Able to send value to Default SDG Toggle TextBox : "+toggleValue);
											log(LogStatus.SKIP,"Not Able to send value to Default SDG Toggle TextBox : "+toggleValue,YesNo.Yes);
										}

									} else {
										sa.assertTrue(false,"Not Able to retrive input value from SDG Config Data Provider TextBox");
										log(LogStatus.SKIP,"Not Able to retrive input value from SDG Config Data Provider TextBox",YesNo.Yes);
									}




								} else {
									sa.assertTrue(false,"Not Able to Click on Toggle : "+toggleBtn);
									log(LogStatus.SKIP,"Not Able to Click on Toggle : "+toggleBtn,YesNo.Yes);
								}

							}

						} else {
							sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
							log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR,"Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false,"Not Able to click on Edit Page SetUp Link");
					}



				}else {

					log(LogStatus.ERROR,"Item not found: "+itemValue, YesNo.Yes);
					sa.assertTrue(false,"Item not found: "+itemValue);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName,YesNo.Yes);
			}
			refresh(driver);
			ThreadSleep(7000);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module5TC006_CreateNewCustomSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sg = new SDGPageBusinessLayer(driver);
		String fields=SDGLabels.APIName.toString();String values="";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
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
					
					for(int i = 0;i<1;i++) {
						String api=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "CField" + (i+1), excelLabel.APIName);
						values=api;
						if (sg.addFieldOnSDG(projectName,fields,values)) {
							log(LogStatus.INFO,"Successfully added fields on "+Sdg1Name,YesNo.Yes);

						}else {
							sa.assertTrue(false,"Not Able to add fields on SDG : "+Sdg1Name);
							log(LogStatus.SKIP,"Not Able to add fields on SDG : "+Sdg1Name,YesNo.Yes);
						}
					}
					
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
	
	@Parameters({ "projectName"})
	@Test
	public void Module5TC007_VerifyToAddNewToggleButtonWithMaxAndSpecialCharacter(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String customSdgNAME = customObject+Sdg1Name;
		String sdgConfigDataProviderTextBox = ActiveDealToggleButton+":"+Sdg1Name;

		String[] tabNames = {ToggleCheck1TabName,ToggleCheck2TabName,ToggleCheck3TabName};
		String tabName;

		String[] itemValues = {ToggleCheck1ItemName,ToggleCheck2ItemName,ToggleCheck3ItemName};
		String itemValue;

		String[] relatedTabs = {ToggleCheck1RelatedTab,ToggleCheck2RelatedTab,ToggleCheck3RelatedTab};
		String relatedTab;

		String toggleButtons[] = {ToggleCheck1ToggleButtons,ToggleCheck2ToggleButtons,ToggleCheck3ToggleButtons};

		String fileLocation[] = {"FundInv.PNG","OpenQues.PNG","ThirdPartyEvent.PNG"};

		boolean flag=false;
		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Click on Tab : "+tabName,YesNo.No);

				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO,"Item found: "+itemValue, YesNo.Yes);
					ThreadSleep(2000);

					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO,"click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);	
						//// scn.nextLine();
						switchToDefaultContent(driver);
						switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));

						relatedTab=relatedTabs[i];
						if (click(driver, ip.getRelatedTab(projectName, relatedTab, 5), relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
							ThreadSleep(2000);


							String[] toggles = toggleButtons[i].split(breakSP);
							String toggleBtn = "";
							for (int j = 0; j < 1; j++) {
								toggleBtn = toggles[j];
								switchToDefaultContent(driver);
								ThreadSleep(20000);
								/////////////////////////////////////////////////////
								String sValue = EditPageErrorMessage.EnhancedLightningGrid;
								if (sendKeys(driver, edit.getEditPageSeachTextBox(projectName, 10),sValue,"Search TextBox",action.BOOLEAN)) {
									ThreadSleep(2000);
									log(LogStatus.INFO,"send value to Search TextBox : "+sValue,YesNo.No);
									Actions act = new Actions(driver);
									WebElement source=edit.getEditPageSeachValueLink(projectName, sValue, 10);
									act.clickAndHold(source).build().perform();
									ThreadSleep(1000);
									Screen screen = new Screen();
									try {
										screen.dropAt(".\\AutoIT\\EditPage\\"+fileLocation[i]+"");
										flag=true;
									} catch (FindFailed e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//	// scn.nextLine();
									if (flag) {
										log(LogStatus.INFO,"Able to DragNDrop : "+sValue,YesNo.No);
										ThreadSleep(2000);
										try {
											edit.getElgDataProviderTextBox(projectName, 10).clear();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										ThreadSleep(2000);
										if (edit.clickOnELGSeachValueLink(projectName, customSdgNAME, 20)) {
											log(LogStatus.INFO,"Click on ELG Search Vaue Link: "+customSdgNAME,YesNo.No);;
											ThreadSleep(500);
										} else {
											sa.assertTrue(false, "Not Able to Click on ELG Search Vaue Link: "+customSdgNAME);
											log(LogStatus.SKIP,"Not Able to Click on ELG Search Vaue Link: "+customSdgNAME,YesNo.Yes);
										}
										if (sendKeys(driver, edit.getElgTitleTextBox(projectName, 10),ActiveDealToggleButton,"ELG Title TextBox",action.BOOLEAN)) {
											ThreadSleep(500);
											log(LogStatus.INFO,"send value to ELG Title TextBox : "+ActiveDealToggleButton,YesNo.No);
										

										} else {
											sa.assertTrue(false, "Not Able to send value to ELG Title TextBox : "+ActiveDealToggleButton);
											log(LogStatus.FAIL,"Not Able to send value to ELG Title TextBox : "+ActiveDealToggleButton,YesNo.Yes);
										}
										
										//	// scn.nextLine();
										if (click(driver, edit.getEnableToggleCheckBox(projectName, 10), "Enable Toggle CheckBox", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO,"click on Enable Toggle CheckBox",YesNo.No);
										} else {

											sa.assertTrue(false, "Not Able to click on Enable Toggle CheckBox");
											log(LogStatus.FAIL,"Not Able to click on Enable Toggle CheckBox",YesNo.Yes);

										}

									} else {
										sa.assertTrue(false, "Not Able to DragNDrop : "+sValue);
										log(LogStatus.FAIL,"Not Able to DragNDrop : "+sValue,YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to send value to Search TextBox : "+sValue);
									log(LogStatus.FAIL,"Not Able to send value to Search TextBox : "+sValue,YesNo.Yes);
								}

								switchToDefaultContent(driver);
								switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
								if (click(driver, ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30), toggleBtn, action.BOOLEAN)) {
									log(LogStatus.INFO,"Click on Toggle : "+toggleBtn,YesNo.No);
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									if (sendKeysWithoutClearingTextBox(driver, edit.getsdgConfigDataProviderTextBox(projectName, 10),","+sdgConfigDataProviderTextBox,"sdg Config Data Provider TextBox : "+sdgConfigDataProviderTextBox,action.BOOLEAN)) {
										ThreadSleep(500);
										log(LogStatus.INFO,"send value to sdg Config Data Provider TextBox : "+sdgConfigDataProviderTextBox,YesNo.No);


										if (click(driver, edit.getEditPageSaveButton(projectName, 10),"Edit Page Save Button", action.BOOLEAN)) {
											log(LogStatus.INFO,"Click on Edit Page Save Button",YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
											log(LogStatus.FAIL,"Not Able to Click on Edit Page Save Button",YesNo.Yes);
										}
										toggleBtn = ActiveDealToggleButton ;
										switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
										if (ip.toggleButton(projectName, PageName.Object1Page, toggleBtn, action.BOOLEAN, 30)!=null) {
											log(LogStatus.INFO,"New Toggle Button Added : "+toggleBtn,YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false, "New Toggle Button Added : "+toggleBtn);
											log(LogStatus.FAIL,"New Toggle Button Added : "+toggleBtn,YesNo.Yes);
										}


									} else {
										sa.assertTrue(false, "Not Able to send value to sdg Config Data Provider TextBox : "+sdgConfigDataProviderTextBox);
										log(LogStatus.FAIL,"Not Able to send value to sdg Config Data Provider TextBox : "+sdgConfigDataProviderTextBox,YesNo.Yes);
									}



								} else {
									sa.assertTrue(false,"Not Able to Click on Toggle : "+toggleBtn);
									log(LogStatus.SKIP,"Not Able to Click on Toggle : "+toggleBtn,YesNo.Yes);
								}

							}

						} else {
							sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
							log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
						}
						ThreadSleep(5000);
						switchToDefaultContent(driver);
						if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),"Edit Page Back Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on Edit Page Back Button",YesNo.No);
							//// scn.nextLine();
						} else {
							sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
							log(LogStatus.SKIP,"Not Able to Click on Edit Page Back Button",YesNo.Yes);
						}


					} else {
						log(LogStatus.ERROR,"Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false,"Not Able to click on Edit Page SetUp Link");
					}



				}else {

					log(LogStatus.ERROR,"Item not found: "+itemValue, YesNo.Yes);
					sa.assertTrue(false,"Item not found: "+itemValue);
				}
				refresh(driver);
				ThreadSleep(7000);
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName,YesNo.Yes);
			}
			refresh(driver);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module5TC008_CreateNewToggleButtonAsDefaultButton(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String[] tabNames = {ToggleCheck1TabName,ToggleCheck2TabName,ToggleCheck3TabName};
		String tabName;

		String[] itemValues = {ToggleCheck1ItemName,ToggleCheck2ItemName,ToggleCheck3ItemName};
		String itemValue;

		String[] relatedTabs = {ToggleCheck1RelatedTab,ToggleCheck2RelatedTab,ToggleCheck3RelatedTab};
		String relatedTab;

		String toggleButtons[] = {ToggleCheck1ToggleButtons,ToggleCheck2ToggleButtons,ToggleCheck3ToggleButtons};

		String toggleValue=Sdg1Name;
		String toggleButton = ActiveDealToggleButton ;

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Click on Tab : "+tabName,YesNo.No);

				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO,"Item found: "+itemValue, YesNo.Yes);
					ThreadSleep(2000);
					
					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO,"click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);	
						//// scn.nextLine();
						switchToDefaultContent(driver);
						switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));

						relatedTab=relatedTabs[i];
						if (click(driver, ip.getRelatedTab(projectName, relatedTab, 5), relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
							ThreadSleep(2000);

							String[] toggles = toggleButtons[i].split(breakSP);
							String toggleBtn = "";
							for (int j = 0; j < 1; j++) {
								toggleBtn = toggles[j];
								if (click(driver, ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30), toggleBtn, action.BOOLEAN)) {
									log(LogStatus.INFO,"Able to Click on Toggle : "+toggleBtn,YesNo.No);
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									ThreadSleep(20000);
										/////////////////////////////////////////////

										switchToDefaultContent(driver);
										if (sendKeys(driver, edit.getDefaultSDGToggleTextBox(projectName, 10),toggleValue,"Default SDG Toggle TextBox",action.BOOLEAN)) {
											ThreadSleep(200);
											log(LogStatus.INFO,"send value to Default SDG Toggle TextBox : "+toggleValue,YesNo.No);
											if (click(driver, edit.getEditPageSaveButton(projectName, 10),"Edit Page Save Button", action.BOOLEAN)) {
												log(LogStatus.INFO,"Click on Edit Page Save Button",YesNo.No);;
												ThreadSleep(5000);
												switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
												if (ip.toggleSDGButtons(projectName, toggleButton,ToggleButtonGroup.SDGButton, action.BOOLEAN, 10)!=null) {
													log(LogStatus.PASS,"After Save "+toggleButton+" is selected ",YesNo.No);
												} else {
													sa.assertTrue(false,"After Save "+toggleButton+" should be selected ");
													log(LogStatus.FAIL,"After Save "+toggleButton+" should be selected ",YesNo.Yes);
												}

												switchToDefaultContent(driver);
												if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),"Edit Page Back Button", action.BOOLEAN)) {
													log(LogStatus.INFO,"Click on Edit Page Back Button",YesNo.No);
													//// scn.nextLine();
													ThreadSleep(10000);

													if (click(driver, ip.getRelatedTab(projectName,relatedTab, 30), relatedTab, action.BOOLEAN)) {
														log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
														ThreadSleep(2000);

														if (ip.toggleSDGButtons(projectName, toggleButton,ToggleButtonGroup.SDGButton, action.BOOLEAN, 10)!=null) {
															log(LogStatus.PASS,"After Save "+toggleButton+" is selected ",YesNo.No);
														} else {
															sa.assertTrue(false,"After Save "+toggleButton+" should be selected ");
															log(LogStatus.FAIL,"After Save "+toggleButton+" should be selected ",YesNo.Yes);
														}



													} else {
														sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
														log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
													}

												} else {
													sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
													log(LogStatus.SKIP,"Not Able to Click on Edit Page Back Button",YesNo.Yes);
												}

											} else {
												sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
												log(LogStatus.SKIP,"Not Able to Click on Edit Page Save Button",YesNo.Yes);
											}

										} else {
											sa.assertTrue(false, "Not Able to send value to Default SDG Toggle TextBox : "+toggleValue);
											log(LogStatus.SKIP,"Not Able to send value to Default SDG Toggle TextBox : "+toggleValue,YesNo.Yes);
										}

								} else {
									sa.assertTrue(false,"Not Able to Click on Toggle : "+toggleBtn);
									log(LogStatus.SKIP,"Not Able to Click on Toggle : "+toggleBtn,YesNo.Yes);
								}

							}

						} else {
							sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
							log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR,"Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false,"Not Able to click on Edit Page SetUp Link");
					}

				}else {

					log(LogStatus.ERROR,"Item not found: "+itemValue, YesNo.Yes);
					sa.assertTrue(false,"Item not found: "+itemValue);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName,YesNo.Yes);
			}
			refresh(driver);
			ThreadSleep(7000);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module5TC009_VerifyOutSideAndInsideSdgInContainerAndEnableToggleSwitchingAndVerifySDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String baseLineSDG =watchListSDG ;
		String customBaseLineSdgNAME = customObject+baseLineSDG;

		String baseLineSDG2 =Sdg1Name ;
		String customBaseLineSdgNAME2 = customObject+baseLineSDG2;

		//	String sdgConfigDataProviderTextBox = ActiveDealToggleButton+":"+Sdg1Name;
		String insideContainerTitle = watchListTitle;
		String outSideContainerTitle = dealTitle;

	//	String sdgConfigDataProviderTextBox = "Watchlist:Origination_Watchlist_AllWatchlist_Baseline,Deals:Custom SDG";
		String sdgConfigDataProviderTextBox = insideContainerTitle+":"+baseLineSDG+","+outSideContainerTitle+":"+baseLineSDG2;
		String defaultSdgToggle = baseLineSDG;

		String[] tabNames = {ToggleCheck1TabName,ToggleCheck2TabName,ToggleCheck3TabName};
		String tabName;

		String[] itemValues = {ToggleCheck1ItemName,ToggleCheck2ItemName,ToggleCheck3ItemName};
		String itemValue;

		String[] relatedTabs = {ToggleCheck1RelatedTab,ToggleCheck2RelatedTab,ToggleCheck3RelatedTab};
		String relatedTab;

		String toggleButtons[] = {ToggleCheck1ToggleButtons,ToggleCheck2ToggleButtons,ToggleCheck3ToggleButtons};
		//ActiveDealToggle
		String outsideContainerPath = ".\\AutoIT\\EditPage\\EditButton.PNG";
		String insideContainerPath = ".\\AutoIT\\EditPage\\EditButton1.PNG";
		String EnhanceLightningGridImg = ".\\AutoIT\\EditPage\\EnhanceLightningGrid.PNG";
		String NavatarSDGToggleImg = ".\\AutoIT\\EditPage\\NavatarSDGToggle.PNG";
		
		String sValue = EditPageErrorMessage.EnhancedLightningGrid;
		boolean outsideFlag=false;
		boolean insideFlag=false;
		String parentId=null;
		String url ="";
		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Click on Tab : "+tabName,YesNo.No);

				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO,"Item found: "+itemValue, YesNo.Yes);
					ThreadSleep(2000);

					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO,"click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);	
						//// scn.nextLine();
						switchToDefaultContent(driver);


						////////////////////////new////////////////////////////////////////

						relatedTab=relatedTabs[i];
						String[] toggles = toggleButtons[i].split(breakSP);
						switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
						if (click(driver, ip.getRelatedTab(projectName, relatedTab, 5), relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
							ThreadSleep(2000);
							scrollDownThroughWebelement(driver, ip.toggleButton(projectName, ActiveDealToggleButton, action.SCROLLANDBOOLEAN, 30), ActiveDealToggleButton);
							switchToDefaultContent(driver);
							sValue = EditPageErrorMessage.EnhancedLightningGrid;
							if (sendKeys(driver, edit.getEditPageSeachTextBox(projectName, 10),sValue,"Search TextBox",action.BOOLEAN)) {
								ThreadSleep(20000);
								log(LogStatus.INFO,"send value to Search TextBox : "+sValue,YesNo.No);
							//	// scn.nextLine();
								ThreadSleep(10000);
								//edit.dragNDropUsingScreen(projectName, edit.getEditPageSeachValueLink(projectName, sValue, 30), insideContainerPath, 20)
								if (edit.dragNDropUsingScreen(projectName, EnhanceLightningGridImg, insideContainerPath, 20)) {
									log(LogStatus.INFO,"Able to DragNDrop : "+sValue,YesNo.No);
									ThreadSleep(2000);

									if (sendKeys(driver, edit.getElgTitleTextBox(projectName, 10),insideContainerTitle,"ELG Title TextBox",action.BOOLEAN)) {
										ThreadSleep(500);
										log(LogStatus.INFO,"send value to ELG Title TextBox : "+insideContainerTitle,YesNo.No);
										insideFlag=true;

									} else {
										sa.assertTrue(false, "Not Able to send value to ELG Title TextBox : "+insideContainerTitle);
										log(LogStatus.FAIL,"Not Able to send value to ELG Title TextBox : "+insideContainerTitle,YesNo.Yes);
									}
									ThreadSleep(2000);
									if (edit.clickOnELGSeachValueLink(projectName, customBaseLineSdgNAME, 20)) {
										log(LogStatus.INFO,"Click on ELG Search Vaue Link: "+customBaseLineSdgNAME,YesNo.No);;
										ThreadSleep(500);

									} else {
										sa.assertTrue(false, "Not Able to Click on ELG Search Vaue Link: "+customBaseLineSdgNAME);
										log(LogStatus.SKIP,"Not Able to Click on ELG Search Vaue Link: "+customBaseLineSdgNAME,YesNo.Yes);
									}


								} else {
									sa.assertTrue(false, "Not Able to DragNDrop : "+sValue);
									log(LogStatus.FAIL,"Not Able to DragNDrop : "+sValue,YesNo.Yes);
								}

							} else {
								sa.assertTrue(false, "Not Able to send value to Search TextBox : "+sValue);
								log(LogStatus.FAIL,"Not Able to send value to Search TextBox : "+sValue,YesNo.Yes);
							}


							// outside
							System.err.println(">>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							// scn.nextLine();
							ThreadSleep(10000);
							switchToDefaultContent(driver);
							sValue = EditPageErrorMessage.EnhancedLightningGrid;
							if (sendKeys(driver, edit.getEditPageSeachTextBox(projectName, 10),sValue,"Search TextBox",action.BOOLEAN)) {
								ThreadSleep(20000);
								log(LogStatus.INFO,"send value to Search TextBox : "+sValue,YesNo.No);
						//		// scn.nextLine();
								ThreadSleep(10000);
								switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
								ThreadSleep(2000);
								WebElement ele = edit.actionDropdownElement(projectName, ShowMoreActionDropDownList.Edit, 10);
								scrollDownThroughWebelement(driver, ele, "EDIT");
								ThreadSleep(3000);
								//edit.dragNDropUsingScreen(projectName, edit.getEditPageSeachValueLink(projectName, sValue, 30), outsideContainerPath, 20)
								switchToDefaultContent(driver);
								if (edit.dragNDropUsingScreen(projectName, EnhanceLightningGridImg, outsideContainerPath, 20)) {
									log(LogStatus.INFO,"Able to DragNDrop : "+sValue,YesNo.No);
									ThreadSleep(2000);

									if (sendKeys(driver, edit.getElgTitleTextBox(projectName, 10),outSideContainerTitle,"ELG Title TextBox",action.BOOLEAN)) {
										ThreadSleep(500);
										log(LogStatus.INFO,"send value to ELG Title TextBox : "+outSideContainerTitle,YesNo.No);
										outsideFlag=true;
									} else {
										sa.assertTrue(false, "Not Able to send value to ELG Title TextBox : "+outSideContainerTitle);
										log(LogStatus.FAIL,"Not Able to send value to ELG Title TextBox : "+outSideContainerTitle,YesNo.Yes);
									}
									ThreadSleep(2000);
									if (edit.clickOnELGSeachValueLink(projectName, customBaseLineSdgNAME2, 20)) {
										log(LogStatus.INFO,"Click on ELG Search Vaue Link: "+customBaseLineSdgNAME2,YesNo.No);;
										ThreadSleep(500);
									} else {
										sa.assertTrue(false, "Not Able to Click on ELG Search Vaue Link: "+customBaseLineSdgNAME2);
										log(LogStatus.SKIP,"Not Able to Click on ELG Search Vaue Link: "+customBaseLineSdgNAME2,YesNo.Yes);
									}


								} else {
									sa.assertTrue(false, "Not Able to DragNDrop : "+sValue);
									log(LogStatus.FAIL,"Not Able to DragNDrop : "+sValue,YesNo.Yes);
								}

							} else {
								sa.assertTrue(false, "Not Able to send value to Search TextBox : "+sValue);
								log(LogStatus.FAIL,"Not Able to send value to Search TextBox : "+sValue,YesNo.Yes);
							}


							// togles
							switchToDefaultContent(driver);
							switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
							ThreadSleep(2000);
							scrollDownThroughWebelement(driver, ip.toggleButton(projectName, ActiveDealToggleButton, action.SCROLLANDBOOLEAN, 30), ActiveDealToggleButton);
							// scn.nextLine();
							ThreadSleep(10000);
							switchToDefaultContent(driver);
							sValue = EditPageErrorMessage.NavatarSDGToggles;
							if (sendKeys(driver, edit.getEditPageSeachTextBox(projectName, 10),sValue,"Search TextBox",action.BOOLEAN)) {
								ThreadSleep(20000);
								log(LogStatus.INFO,"send value to Search TextBox : "+sValue,YesNo.No);
							//	// scn.nextLine();
								ThreadSleep(10000);
								//edit.dragNDropUsingScreen(projectName, edit.getEditPageSeachValueLink(projectName, sValue, 30), insideContainerPath, 20)
								if (edit.dragNDropUsingScreen(projectName, NavatarSDGToggleImg, insideContainerPath, 20)) {
									log(LogStatus.INFO,"Able to DragNDrop : "+sValue,YesNo.No);
									ThreadSleep(2000);

									if (sendKeysWithoutClearingTextBox(driver, edit.getsdgConfigDataProviderTextBox(projectName, 30),sdgConfigDataProviderTextBox,"sdg Config Data Provider TextBox : "+sdgConfigDataProviderTextBox,action.BOOLEAN)) {
										ThreadSleep(500);
										log(LogStatus.INFO,"send value to sdg Config Data Provider TextBox : "+sdgConfigDataProviderTextBox,YesNo.No);

										if (sendKeys(driver, edit.getDefaultSDGToggleTextBox(projectName, 10),defaultSdgToggle,"Default SDG Toggle TextBox : "+defaultSdgToggle,action.BOOLEAN)) {
											ThreadSleep(200);
											log(LogStatus.INFO,"send value to Default SDG Toggle TextBox : "+defaultSdgToggle,YesNo.No);
											if (click(driver, edit.getEditPageSaveButton(projectName, 10),"Edit Page Save Button", action.BOOLEAN)) {
												log(LogStatus.INFO,"Click on Edit Page Save Button",YesNo.No);;
											} else {
												sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
												log(LogStatus.SKIP,"Not Able to Click on Edit Page Save Button",YesNo.Yes);
											}

										} else {
											sa.assertTrue(false, "Not Able to send value to Default SDG Toggle TextBox : "+defaultSdgToggle);
											log(LogStatus.SKIP,"Not Able to send value to Default SDG Toggle TextBox : "+defaultSdgToggle,YesNo.Yes);
										}

									} else {
										sa.assertTrue(false, "Not Able to send value to sdg Config Data Provider TextBox : "+sdgConfigDataProviderTextBox);
										log(LogStatus.FAIL,"Not Able to send value to sdg Config Data Provider TextBox : "+sdgConfigDataProviderTextBox,YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to DragNDrop : "+sValue);
									log(LogStatus.FAIL,"Not Able to DragNDrop : "+sValue,YesNo.Yes);
								}

							} else {
								sa.assertTrue(false, "Not Able to send value to Search TextBox : "+sValue);
								log(LogStatus.FAIL,"Not Able to send value to Search TextBox : "+sValue,YesNo.Yes);
							}
							switchToDefaultContent(driver);
							switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
							System.err.println(">>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							// scn.nextLine();
							ThreadSleep(10000);
							if (insideFlag) {
								if (click(driver, ip.customToggleButton(projectName, insideContainerTitle, action.SCROLLANDBOOLEAN, 30), insideContainerTitle+" SDG", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS,"Click on "+insideContainerTitle,YesNo.No);
									ThreadSleep(200);
									switchToDefaultContent(driver);
									if (click(driver, edit.getEnableToggleCheckBox(projectName, 10), "Enable Toggle CheckBox", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO,"click on Enable Toggle CheckBox for : "+insideContainerTitle,YesNo.No);
										ThreadSleep(200);

										if (click(driver, edit.getEditPageSaveButton(projectName, 10),"Edit Page Save Button", action.BOOLEAN)) {
											log(LogStatus.INFO,"Click on Edit Page Save Button",YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
											log(LogStatus.FAIL,"Not Able to Click on Edit Page Save Button",YesNo.Yes);
										}

									} else {
										sa.assertTrue(false, "Not Able to click on Enable Toggle CheckBox for : "+insideContainerTitle);
										log(LogStatus.FAIL,"Not Able to click on Enable Toggle CheckBox for : "+insideContainerTitle,YesNo.Yes);

									}

								}else{
									sa.assertTrue(false, "Not Able to Click on "+insideContainerTitle);
									log(LogStatus.FAIL,"Not Able to Click on "+insideContainerTitle,YesNo.Yes);
								}
							}
							

							switchToDefaultContent(driver);
							switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
							if (outsideFlag) {
								if (click(driver, ip.customToggleButton(projectName, outSideContainerTitle, action.SCROLLANDBOOLEAN, 30), outSideContainerTitle+" SDG", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS,"Click on "+outSideContainerTitle,YesNo.No);
									ThreadSleep(200);
									switchToDefaultContent(driver);
									if (click(driver, edit.getEnableToggleCheckBox(projectName, 10), "Enable Toggle CheckBox", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO,"click on Enable Toggle CheckBox for : "+outSideContainerTitle,YesNo.No);
										ThreadSleep(200);

										if (click(driver, edit.getEditPageSaveButton(projectName, 10),"Edit Page Save Button", action.BOOLEAN)) {
											log(LogStatus.INFO,"Click on Edit Page Save Button",YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
											log(LogStatus.FAIL,"Not Able to Click on Edit Page Save Button",YesNo.Yes);
										}

									} else {
										sa.assertTrue(false, "Not Able to click on Enable Toggle CheckBox for : "+outSideContainerTitle);
										log(LogStatus.FAIL,"Not Able to click on Enable Toggle CheckBox for : "+outSideContainerTitle,YesNo.Yes);

									}

								}else{
									sa.assertTrue(false, "Not Able to Click on "+outSideContainerTitle);
									log(LogStatus.FAIL,"Not Able to Click on "+outSideContainerTitle,YesNo.Yes);
								}
							}
							
						} else {
							sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
							log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
						}
						System.err.println(">>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						// scn.nextLine();
						ThreadSleep(10000);
						//////////////////////////////////////new/////////////////////////////


						switchToDefaultContent(driver);
						if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),"Edit Page Back Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on Edit Page Back Button",YesNo.No);
						} else {
							sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
							log(LogStatus.SKIP,"Not Able to Click on Edit Page Back Button",YesNo.Yes);
						}


					} else {
						log(LogStatus.ERROR,"Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false,"Not Able to click on Edit Page SetUp Link");
					}



				}else {

					log(LogStatus.ERROR,"Item not found: "+itemValue, YesNo.Yes);
					sa.assertTrue(false,"Item not found: "+itemValue);
				}
				refresh(driver);
				ThreadSleep(7000);
				
				//////////////////////////////////////////new window ////////////////////////////////
				
				String str = "window.open()";
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript(str, "");
				ThreadSleep(5000);
				parentId = switchOnWindow(driver);
				if (URL.contains("http")) {

					url=URL;
				} else {
					url="https://"+URL;
				}

				driver.get(url);
				ThreadSleep(5000);
				if (lp.CRMLogin(crmUser1EmailID, adminPassword, appName)) {
						tabName = tabNames[i];
						if (lp.clickOnTab(projectName, tabName)) {
							log(LogStatus.INFO,"Click on Tab : "+tabName,YesNo.No);

							itemValue = itemValues[i];
							if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
								log(LogStatus.INFO,"Item found: "+itemValue, YesNo.Yes);
								ThreadSleep(2000);

								relatedTab=relatedTabs[i];
								if (click(driver, ip.getRelatedTab(projectName, relatedTab, 5), relatedTab.toString(), action.BOOLEAN)) {
									log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
									ThreadSleep(2000);

									String toggleBtn = watchListTitle;
									if (ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 10)==null) {
										log(LogStatus.INFO,"Toggle is not present : "+toggleBtn,YesNo.No);
										ThreadSleep(2000);
									} else {
										sa.assertTrue(false,"Toggle should not present : "+toggleBtn);
										log(LogStatus.SKIP,"Toggle should not present : "+toggleBtn,YesNo.Yes);
									}
									


									toggleBtn = dealTitle;
									if (ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 10)==null) {
										log(LogStatus.INFO,"Toggle is not present : "+toggleBtn,YesNo.No);
										ThreadSleep(2000);
									} else {
										sa.assertTrue(false,"Toggle should not present : "+toggleBtn);
										log(LogStatus.SKIP,"Toggle should not present : "+toggleBtn,YesNo.Yes);
									}
									

								} else {
									sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
									log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
								}

							}else {

								log(LogStatus.ERROR,"Item not found: "+itemValue, YesNo.Yes);
								sa.assertTrue(false,"Item not found: "+itemValue);
							}

						} else {
							sa.assertTrue(false,"Not Able to Click on Tab : "+tabName);
							log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName,YesNo.Yes);
						}
					
				} else {
					sa.assertTrue(false,"Not Able to login with user "+tabName);
					log(LogStatus.SKIP,"Not Able to login with user "+tabName,YesNo.Yes);
				}
				driver.close();
				driver.switchTo().window(parentId);
				switchToDefaultContent(driver);
				lp.CRMlogout();
				ThreadSleep(10000);
				lp.CRMLogin(superAdminUserName, adminPassword, appName);
				
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName,YesNo.Yes);
			}
			ThreadSleep(5000);
			refresh(driver);
			ThreadSleep(10000);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module5TC010_VerifyTheButtonToggleButtonWithUserNewSession(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] tabNames = {ToggleCheck1TabName,ToggleCheck2TabName,ToggleCheck3TabName};
		String tabName;


		String[] itemValues = {ToggleCheck1ItemName,ToggleCheck2ItemName,ToggleCheck3ItemName};
		String itemValue;

		String[] relatedTabs = {ToggleCheck1RelatedTab,ToggleCheck2RelatedTab,ToggleCheck3RelatedTab};
		String relatedTab;


		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Click on Tab : "+tabName,YesNo.No);

				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO,"Item found: "+itemValue, YesNo.Yes);
					ThreadSleep(2000);

					relatedTab=relatedTabs[i];
					if (click(driver, ip.getRelatedTab(projectName, relatedTab, 5), relatedTab.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
						ThreadSleep(2000);

						String toggleBtn = watchListTitle;
						if (ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30)!=null) {
							log(LogStatus.INFO,"Toggle is present : "+toggleBtn,YesNo.No);
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false,"Toggle should be present : "+toggleBtn);
							log(LogStatus.SKIP,"Toggle should be present : "+toggleBtn,YesNo.Yes);
						}
						if (ip.toggleSDGButtons(projectName, toggleBtn,ToggleButtonGroup.SDGButton, action.BOOLEAN, 30)!=null) {
							log(LogStatus.PASS,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" is present",YesNo.Yes);
						} else {
							sa.assertTrue(false,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" should be present");
							log(LogStatus.FAIL,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" should be present",YesNo.Yes);
						}


						toggleBtn = dealTitle;
						if (ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30)!=null) {
							log(LogStatus.INFO,"Toggle is present : "+toggleBtn,YesNo.No);
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false,"Toggle should be present : "+toggleBtn);
							log(LogStatus.SKIP,"Toggle should be present : "+toggleBtn,YesNo.Yes);
						}
						if (ip.toggleSDGButtons(projectName, toggleBtn,ToggleButtonGroup.SDGButton, action.BOOLEAN, 30)!=null) {
							log(LogStatus.PASS,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" is present",YesNo.Yes);
						} else {
							sa.assertTrue(false,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" should be present");
							log(LogStatus.FAIL,"At "+toggleBtn+" "+ToggleButtonGroup.SDGButton+" should be present",YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
						log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
					}

				}else {

					log(LogStatus.ERROR,"Item not found: "+itemValue, YesNo.Yes);
					sa.assertTrue(false,"Item not found: "+itemValue);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName,YesNo.Yes);
			}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
		
	
}
	
	