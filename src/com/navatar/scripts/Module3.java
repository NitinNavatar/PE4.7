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
import com.navatar.pageObjects.DataLoaderWizardPageBusinessLayer;
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
import static com.navatar.generic.BaseLib.pahse1DataSheetFilePath;
import static com.navatar.generic.CommonLib.*;

public class Module3 extends BaseLib {
	String passwordResetLink = null;

	Scanner scn = new Scanner(System.in);
	String breakSP = "<break>";
	String columnSP = "<column>";
	String commaSP = ",";
	String colonSP = ":";
	String emptyString="";
	String navigationMenuName="Navigation Menu";
	
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc002_UploadCsvToCreateTheNavigationData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		DataLoaderWizardPageBusinessLayer dataload = new DataLoaderWizardPageBusinessLayer(driver);
		mode=Mode.Classic.toString();
		Boolean flag = false;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		lp.switchToClassic();
		if(dataload.dataImportWizard(ObjectName.Navigation, ObjectType.Custom, "\\UploadCSV\\NavigationMenuTestData_PE.csv", DataImportType.AddNewRecords, "22")) {
			appLog.info("Data is imported Successfully in "+ObjectName.Navigation);
			flag=true;
			
		}else {
			appLog.error("Data is not imported in "+ObjectName.Navigation);
			sa.assertTrue(false, "Data is not imported in "+ObjectName.Navigation);
		}
		if(flag) {
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc003_VerifyTheDefaultMenuLabelAndUpdateTheLabel(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to update Navigation Label", YesNo.No);
				ThreadSleep(500);

				if(setup.searchStandardOrCustomObject(environment,mode, object.App_Manager)) {
					log(LogStatus.INFO, "click on Object : "+object.App_Manager, YesNo.No);
					ThreadSleep(2000);
//					if(setup.clickOnObjectFeature(environment,mode, object.Apps, ObjectFeatureName.App_Manager)) {
//						log(LogStatus.INFO, "Clicked on feature : "+ObjectFeatureName.App_Manager, YesNo.No);
						ThreadSleep(1000);
						scrollDownThroughWebelement(driver, setup.getAppMangerScroll(10), "App manager horizontal scroll");
						setup.clickOnEditForApp(driver, appName, AppDeveloperName,"Navatar Private Equity app – Lightning View(Edge)", 10);
						scn.nextLine();
						if(true/*setup.clickOnEditForApp(driver, appName, AppDeveloperName,AppDescription, 10)*/) {
							log(LogStatus.INFO,"able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription, YesNo.No);
							ThreadSleep(1000);
							
							//Utlity Items
							if (setup.clickOnAppSettingList(driver, AppSetting.Utility_Items, 10)) {
								log(LogStatus.INFO, "able to click on "+AppSetting.Utility_Items, YesNo.No);
								ThreadSleep(500);
								if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Label.toString(), action.BOOLEAN, 10),navigationMenuName,PageLabel.Label.toString()+" textbox value : "+navigationMenuName,action.BOOLEAN)) {
									ThreadSleep(500);
									log(LogStatus.INFO,"send value to "+PageLabel.Label.toString()+" textbox value : "+navigationMenuName,YesNo.No);
									if (click(driver, setup.getCustomTabSaveBtn(projectName, 10)," Save Button", action.BOOLEAN)) {
										log(LogStatus.INFO,"Click on Save Button",YesNo.No);
										ThreadSleep(5000);			
									} else {
										sa.assertTrue(false, "Not Able to Click on Save Button");
										log(LogStatus.FAIL,"Not Able to Click on Save Button",YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to send value to "+PageLabel.Label.toString()+" textbox value : "+navigationMenuName);
									log(LogStatus.FAIL,"Not Able to send value to "+PageLabel.Label.toString()+" textbox value : "+navigationMenuName,YesNo.Yes);
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on "+AppSetting.Utility_Items, YesNo.Yes);
								sa.assertTrue(false,"Not able to click on "+AppSetting.Utility_Items);
							}

							//Navigations Items
							if (setup.clickOnAppSettingList(driver, AppSetting.Navigation_Items, 10)) {
								log(LogStatus.INFO, "able to click on "+AppSetting.Navigation_Items, YesNo.No);
								ThreadSleep(500);
								setup.addRemoveAppSetingData(projectName,"Navigation", customTabActionType.Add);
							} else {
								log(LogStatus.ERROR, "Not able to click on "+AppSetting.Navigation_Items, YesNo.Yes);
								sa.assertTrue(false,"Not able to click on "+AppSetting.Navigation_Items);
								
							}
							
							//Navigations Items
							if (setup.clickOnAppSettingList(driver, AppSetting.User_Profiles, 10)) {
								log(LogStatus.INFO, "able to click on "+AppSetting.User_Profiles, YesNo.No);
								ThreadSleep(500);
								setup.addRemoveAppSetingData(projectName,"Standard User,System Administrator", customTabActionType.Add);
							} else {
								log(LogStatus.ERROR, "Not able to click on "+AppSetting.User_Profiles, YesNo.Yes);
								sa.assertTrue(false,"Not able to click on "+AppSetting.User_Profiles);
								
							}
							
							
						}else {
							log(LogStatus.ERROR,"Not able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription, YesNo.Yes);
							sa.assertTrue(false, "Not able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription);
						}
//					}else {
//						log(LogStatus.ERROR, "Not able to click on App Manager set so cannot update Navigation Label", YesNo.Yes);
//						sa.assertTrue(false, "Not able to click on App Manager set so cannot update Navigation Label");
//					}

				}else {
					log(LogStatus.ERROR, "Not able to search/click on Apps so cannot update Navigation Label", YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on Apps manager so cannot update Navigation Label");
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot update Navigation Label", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot update Navigation Label");
			}

		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
}
	
	