package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.ExcelUtils.readAllDataForAColumn;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
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
import com.navatar.pageObjects.NavigationPageBusineesLayer;

import static com.navatar.pageObjects.NavigationPageBusineesLayer.*;
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

public class Module3 extends BaseLib {
	String passwordResetLink = null;
	Scanner scn = new Scanner(System.in);

	String navigationMenuName="Navigation Menu";
	public  String NavigationMenuTestData_PEExcel = System.getProperty("user.dir")+"\\UploadFiles\\Module 3\\UploadCSV\\NavigationMenuTestData_PE - AllNew.csv";
	public  String NavigationMenuTestData_PESheet = "asd";
	String navigationTab="Navigation";
	List<String> csvRecords = ExcelUtils.readAllDataFromCSVFileIntoList(NavigationMenuTestData_PEExcel, false);
	String none="None";

	@Parameters({ "projectName"})
	@Test
	public void Module3Tc001_createCRMUser(String projectName) {
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
					//						setup.clickOnEditForApp(driver, appName, AppDeveloperName,"Navatar Private Equity app – Lightning View(Edge)", 10);
					//						scn.nextLine();
					if(setup.clickOnEditForApp(driver, appName, AppDeveloperName,AppDescription, 10)) {
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

	@Parameters({ "projectName"})
	@Test
	public void Module3Tc004_OpentheNavigationMenuAndVerifyTheMenuItems(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		System.err.println(NavigationMenuTestData_PEExcel+" >>>>>>>>>>>>><<<<<<<<<<<< "+NavigationMenuTestData_PESheet);

		if (!csvRecords.isEmpty()) {
			log(LogStatus.INFO, "Records Fetched from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.No);

			Map<String, Integer> navigationParentLabelWithOrder = navigationParentLabelWithOrder(csvRecords);
			Map<String, Integer> navigationParentLabelWithSortedOrder = sortByValue(true, navigationParentLabelWithOrder);
			Map<String, String> navigationParentLabelWithChildAndOrder = navigationParentLabelWithChildAndOrder(csvRecords);
			Map<String, String> navigationParentLabelWithChildSorted = navigationParentLabelWithChildSorted(navigationParentLabelWithChildAndOrder);


			// Verification on navigation menu
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				npbl.verifyingNavigationMenuLink(projectName, navigationParentLabelWithSortedOrder, navigationParentLabelWithChildSorted, action.BOOLEAN, 0);;
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
			}

			// Verification on navigation Tab
			String labelValue="";
			if (npbl.clickOnTab(projectName, navigationTab)) {
				log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);

				for (String labelName : csvRecords) {
					labelValue=labelName.split(commaSP)[0];
					if (npbl.clickOnAlreadyCreatedItem(projectName, labelValue, false, 15)) {
						log(LogStatus.INFO,"Item found: "+labelValue+" on Tab : "+navigationTab, YesNo.No);
					}else {

						log(LogStatus.ERROR,"Item not found: "+labelValue+" on Tab : "+navigationTab, YesNo.Yes);
						sa.assertTrue(false,"Item not found: "+labelValue+" on Tab : "+navigationTab);
					}
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
			}

		} else {
			log(LogStatus.FAIL, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.Yes);
			sa.assertTrue(false, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void Module3Tc005_VerifyTheURLNavigationFromNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		System.err.println(NavigationMenuTestData_PEExcel+" >>>>>>>>>>>>><<<<<<<<<<<< "+NavigationMenuTestData_PESheet);

		if (!csvRecords.isEmpty()) {
			log(LogStatus.INFO, "Records Fetched from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.No);

			// Verify The URL Navigation From Navigation Menu
			String labelValue="";
			String urlValue="";
			WebElement ele;
			String actualUrl="";
			for (String labelName : csvRecords) {

				try {
					System.err.println(labelName);
					labelValue=labelName.split(commaSP)[0].trim();
					urlValue=labelName.split(commaSP)[7].trim();
					System.out.println(labelValue+" >>>>>>> "+urlValue);
					if (urlValue.isEmpty() || urlValue.equals("")) {
						urlValue=none;
					} else {

					}
				} catch (Exception e) {
					urlValue=none;
					System.out.println("Exception "+labelValue+" >>>>>>> "+urlValue);
				}
				if (urlValue.contains("/")) {
					if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
						log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
						ele=npbl.getNavigationLabel(projectName, labelValue, action.BOOLEAN, 10);
						if (click(driver, ele, labelValue, action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on "+labelValue+" going to verify url", YesNo.No);
							ThreadSleep(5000);
							actualUrl=getURL(driver, 10);
							if (actualUrl.contains(urlValue)) {
								log(LogStatus.INFO, urlValue+" : Url Verified for : "+labelValue, YesNo.No);
							} else {
								log(LogStatus.ERROR, "Url Not Verified for : "+labelValue+" Actual : "+actualUrl+"\t Expected : "+urlValue, YesNo.Yes);
								sa.assertTrue(false,"Url Not Verified for : "+labelValue+" Actual : "+actualUrl+"\t Expected : "+urlValue);
								refresh(driver);
							}
						} else {
							log(LogStatus.ERROR, "Not Able to Click on "+labelValue+" so cannot verify url", YesNo.Yes);
							sa.assertTrue(false,"Not Able to Click on "+labelValue+" so cannot verify url");

						}
						click(driver, npbl.getNavatarQuickLinkMinimize_Lighting(projectName, 3), "Minimize Icon", action.BOOLEAN);
					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+labelValue, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+labelValue);
					}

				} else {
					log(LogStatus.ERROR, labelValue+" doesn't have url therfore doesnot check url redirection", YesNo.No);
				}

			}
		} else {
			log(LogStatus.FAIL, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.Yes);
			sa.assertTrue(false, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}


}
	
	