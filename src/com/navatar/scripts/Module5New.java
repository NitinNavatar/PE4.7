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
import com.navatar.generic.EnumConstants.Environment;
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

public class Module5New extends BaseLib {
	String passwordResetLink = null;

	Scanner scn = new Scanner(System.in);
	String breakSP = "<break>";
	String columnSP = "<column>";
	String commaSP = ",";
	String colonSP = ":";
	String emptyString="";
	
	@Parameters({ "projectName"})
	@Test
	public void M5TC001_createCRMUser(String projectName) {
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
			if(!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
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
	public void M5TC002_1_Prerequisite(String projectName) {
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
	public void M5TC002_2_AddListView(String projectName) {
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
	public void M5TC003_1_CreatePreconditionData(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ M5Ins1, M5Ins1RecordType ,null}};
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
	}


	@Parameters({ "projectName"})
	@Test
	public void M5TC004_CreateTheToggleButtonInCreatedInstitutionRecord(String projectName) {
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
		String sdgConfigDataProviderTextBox = "";
		String defaultSdgToggle = "";

		String[] tabNames = {ToggleCheck1TabName,ToggleCheck2TabName,ToggleCheck3TabName};
		String tabName;

		String[] itemValues = {ToggleCheck1ItemName,ToggleCheck2ItemName,ToggleCheck3ItemName};
		String itemValue;

		String[] relatedTabs = {ToggleCheck1RelatedTab,ToggleCheck2RelatedTab,ToggleCheck3RelatedTab};
		String relatedTab;

		String toggleButtons[] = {ToggleCheck1ToggleButtons,ToggleCheck2ToggleButtons,ToggleCheck3ToggleButtons};
		
		String[] dropLocation = {".\\AutoIT\\EditPage\\InsEvent.PNG",".\\AutoIT\\EditPage\\EditButton1.PNG",".\\AutoIT\\EditPage\\EditButton1.PNG"};
		//ActiveDealToggle
		String dropImageLocation = "";
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
						switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
						if (click(driver, ip.getRelatedTab(projectName, relatedTab, 5), relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
							ThreadSleep(2000);
							switchToDefaultContent(driver);
							sValue = EditPageErrorMessage.NavatarSDGToggles;
							if (sendKeys(driver, edit.getEditPageSeachTextBox(projectName, 10),sValue,"Search TextBox",action.BOOLEAN)) {
								ThreadSleep(20000);
								log(LogStatus.INFO,"send value to Search TextBox : "+sValue,YesNo.No);
								ThreadSleep(10000);
								dropImageLocation=dropLocation[i];
								if (edit.dragNDropUsingScreen(projectName, NavatarSDGToggleImg, dropImageLocation, 20)) {
									log(LogStatus.INFO,"Able to DragNDrop : "+sValue,YesNo.No);
									ThreadSleep(2000);
									String[] toggles = toggleButtons[i].split(breakSP);
									if (i==0) {
										sdgConfigDataProviderTextBox = toggles[0]+":"+EditPageErrorMessage.EntityUpcomingEventSDG+","+toggles[1]+":"+EditPageErrorMessage.EntityEventInviteeSDG;
										defaultSdgToggle=EditPageErrorMessage.EntityUpcomingEventSDG;
									} else {

									}
									
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
	
	
	
}
	
	