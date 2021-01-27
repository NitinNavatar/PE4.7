package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

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
import static com.navatar.generic.BaseLib.toggleFilePath;
import static com.navatar.generic.CommonLib.*;

public class Toggle extends BaseLib {
	String passwordResetLink = null;

	Scanner scn = new Scanner(System.in);
	String breakSP = "<break>";
	String columnSP = "<column>";
	String commaSP = ",";
	String colonSP = ":";
	String emptyString="";
	
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
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

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

		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		if (lp.clickOnTab(projectName, TabName.Object5Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object5Tab,YesNo.No);	


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
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void ToggleTc004_VerifyTheToggleButtonAndFunctionalityOfToggleButtonOnPageRecord(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

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
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, 15)) {
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





		//					String toggleBtn = ToggleButton.Fund_Investments.toString();
		//
		//					
		//
		//						
		//						WebElement ele=ip.getFundNameAtToggle(projectName, PageName.Object1Page, ToggleFund1, action.BOOLEAN, 30);
		//						if(mouseOverOperation(edriver, ele)){
		//							log(LogStatus.PASS,"Able to mouse hover : "+ToggleFund1,YesNo.No);
		//							String text = ip.getFundNameAtToggleToolTip(projectName, PageName.Object1Page, ToggleFund1, action.BOOLEAN, 30).getAttribute("title");
		//							if(text.contains(ToggleFund1)){
		//								appLog.info("Tool tip error message is verified for : "+ToggleFund1);
		//							} else {
		//								sa.assertTrue(false,"Tool tip error message is not verified. Expected: "+ToggleFund1+ "\tActual: "+text);
		//								log(LogStatus.SKIP,"Tool tip error message is not verified. Expected: "+ToggleFund1+ "\tActual: "+text,YesNo.Yes);
		//							}
		//						} else {
		//							log(LogStatus.SKIP,"Not Able to mouse hover : "+ToggleFund1,YesNo.Yes);
		//							sa.assertTrue(false,"Not Able to mouse hover : "+ToggleFund1);
		//						}
		//
		//						String lp11 ="Alexa Limited partner";
		//						ele=ip.getLegalEntityAtToggle(projectName, PageName.Object1Page, lp11, action.BOOLEAN, 30);
		//						if(mouseOverOperation(edriver, ele)){
		//							ThreadSleep(3000);
		//							log(LogStatus.PASS,"Able to mouse hover : "+lp11,YesNo.No);
		//							if (clickUsingJavaScript(driver, ip.getInlineOrLockedAtToggle(projectName, PageName.Object1Page, lp11, action.BOOLEAN, 10), "Inline Edit Icon : "+lp11, action.BOOLEAN)) {
		//								log(LogStatus.INFO,"click on inline edit icon "+lp11,YesNo.No);
		//								ThreadSleep(2000);
		//
		//							} else {
		//								log(LogStatus.SKIP,"Not Able to click on inline edit icon "+lp11,YesNo.Yes);
		//								sa.assertTrue(false,"Not Able to click on inline edit icon "+lp11);
		//							}
		//						} else {
		//							log(LogStatus.SKIP,"Not Able to mouse hover : "+ToggleFund1,YesNo.Yes);
		//							sa.assertTrue(false,"Not Able to mouse hover : "+ToggleFund1);
		//						}
		//
		//
		//				
		//					//  
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

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void ToggleTc005_VerifyTheRetainAndDefaultSelectionOfToggleButton(String projectName) {
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
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, 15)) {
					log(LogStatus.INFO,"Item found: "+itemValue, YesNo.Yes);
					ThreadSleep(2000);
					
					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO,"click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);	
						//scn.nextLine();
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
									//scn.nextLine();
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
													//scn.nextLine();
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
	public void ToggleTc006_CreateNewCustomSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sg = new SDGPageBusinessLayer(driver);

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
	public void ToggleTc007_VerifyToAddNewToggleButtonWithMaxAndSpecialCharacter(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String customSdgNAME = "CustomObject:"+Sdg1Name;
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
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, 15)) {
					log(LogStatus.INFO,"Item found: "+itemValue, YesNo.Yes);
					ThreadSleep(2000);

					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO,"click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);	
						//scn.nextLine();
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
									//	scn.nextLine();
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

											//													if (sendKeys(driver, edit.getElgDataProviderTextBox(projectName, 10),Sdg1Name,"ELG Title TextBox",action.BOOLEAN)) {
											//														ThreadSleep(500);
											//														log(LogStatus.INFO,"send value to ELG Title TextBox : "+Sdg1Name,YesNo.No);
											//
											//													} else {
											//														sa.assertTrue(false, "Not Able to send value to ELG Title TextBox : "+Sdg1Name);
											//														log(LogStatus.FAIL,"Not Able to send value to ELG Title TextBox : "+Sdg1Name,YesNo.Yes);
											//													}



										} else {
											sa.assertTrue(false, "Not Able to Click on ELG Search Vaue Link: "+customSdgNAME);
											log(LogStatus.SKIP,"Not Able to Click on ELG Search Vaue Link: "+customSdgNAME,YesNo.Yes);
										}

										//	scn.nextLine();
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


								///////////////////////////////////////////////////
								//									String sdgToggles=getValueFromElementUsingJavaScript(driver, edit.getsdgConfigDataProviderTextBox(projectName, 10), "sdg Config Data Provider TextBox");
								//									System.err.println(">>>>> + "+sdgToggles);
								//									//scn.nextLine();
								//									if (sdgToggles!=null) {
								//										
								//									} else {
								//										sa.assertTrue(false,"Not Able to retrive input value from SDG Config Data Provider TextBox");
								//										log(LogStatus.SKIP,"Not Able to retrive input value from SDG Config Data Provider TextBox",YesNo.Yes);
								//									}


							}

						} else {
							sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
							log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
						}

						switchToDefaultContent(driver);
						if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),"Edit Page Back Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on Edit Page Back Button",YesNo.No);
							//scn.nextLine();} else {
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
	public void ToggleTc008_CreateNewToggleButtonAsDefaultButton(String projectName) {
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
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, 15)) {
					log(LogStatus.INFO,"Item found: "+itemValue, YesNo.Yes);
					ThreadSleep(2000);
					
					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO,"click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);	
						//scn.nextLine();
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
//									String sdgToggles=getValueFromElementUsingJavaScript(driver, edit.getsdgConfigDataProviderTextBox(projectName, 10), "sdg Config Data Provider TextBox");
//									System.err.println(">>>>> + "+sdgToggles);
//									//scn.nextLine();
//									if (sdgToggles!=null) {



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
													//scn.nextLine();
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

//									} else {
//										sa.assertTrue(false,"Not Able to retrive input value from SDG Config Data Provider TextBox");
//										log(LogStatus.SKIP,"Not Able to retrive input value from SDG Config Data Provider TextBox",YesNo.Yes);
//									}




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
	
}
	
	