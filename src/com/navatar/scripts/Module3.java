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

import org.apache.poi.ss.formula.functions.Value;
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
	String reportTab="Reports";
	String dashBoardTab="Dashboards";
	List<String> csvRecords = ExcelUtils.readAllDataFromCSVFileIntoList(NavigationMenuTestData_PEExcel, false);
	String none="None";
	String myAppPage="My App Page";
	String googleUrlValue="https://www.google.com/";
	
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
					//						setup.clickOnEditForApp(driver, appName, AppDeveloperName,"Navatar Private Equity app � Lightning View(Edge)", 10);
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
		
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc006_VerifyTheItemsForWhichQuickCreateObjectFieldWasFill(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		Map<String, String> parentChildForQuickActionObject = new LinkedHashMap<String, String>(); 
		lp.CRMLogin(superAdminUserName, adminPassword);
		System.err.println(NavigationMenuTestData_PEExcel+" >>>>>>>>>>>>><<<<<<<<<<<< "+NavigationMenuTestData_PESheet);

		if (!csvRecords.isEmpty()) {
			log(LogStatus.INFO, "Records Fetched from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.No);
			// Verify the items for which Quick Create Object field was fill
			String labelValue="";
			String quickActionObject="";
			WebElement ele;
			String actualUrl="";
			String parent="";
			for (String labelName : csvRecords) {

				try {
					System.err.println(labelName);
					labelValue=labelName.split(commaSP)[0].trim();
					parent=labelName.split(commaSP)[2].trim();
					quickActionObject=labelName.split(commaSP)[3].trim();
					System.out.println(labelValue+" >>>>>>> "+quickActionObject);
					if (quickActionObject.isEmpty() || quickActionObject.equals("")) {
						quickActionObject=none;
					} else {
						if (parentChildForQuickActionObject.get(parent)!=null) {
							String a=parentChildForQuickActionObject.get(parent);
							parentChildForQuickActionObject.put(parent,a+breakSP+labelValue);
						} else {
							parentChildForQuickActionObject.put(parent,labelValue);
						}
					}
				} catch (Exception e) {
					quickActionObject=none;
					System.out.println("Exception "+labelValue+" >>>>>>> "+quickActionObject);
				}


			}
			String childs[]=null;
			if (!parentChildForQuickActionObject.isEmpty()) {

				for (String pr : parentChildForQuickActionObject.keySet()) {
					childs=parentChildForQuickActionObject.get(pr).split(breakSP);
					for (int i = 0; i < childs.length; i++) {
						if (pr.isEmpty()) {

						} else {
							ele=npbl.getNavigationLabel(projectName, pr, action.BOOLEAN, 5);
							click(driver, ele, pr, action.BOOLEAN);
						}
						ele=npbl.getNavigationLabel(projectName, childs[i], action.BOOLEAN, 5);
						if (click(driver, ele, childs[i], action.BOOLEAN)) {
							log(LogStatus.INFO, "Not able to click on "+childs[i], YesNo.No);
							ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, childs[i], action.BOOLEAN, 30);
							if (ele!=null) {
								log(LogStatus.INFO, "Pop Up open after clicking on "+childs[i] , YesNo.No);
								if (click(driver, ele, childs[i]+" pop up cross button", action.BOOLEAN)) {
									log(LogStatus.INFO, "click on cross button "+childs[i] , YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not Able to click on cross button "+childs[i], YesNo.Yes);
									sa.assertTrue(false, "Not Able to click on cross button "+childs[i]);
								}
							} else {
								log(LogStatus.ERROR, "No Pop Up is open after clicking on "+childs[i], YesNo.Yes);
								sa.assertTrue(false, "No Pop Up is open after clicking on "+childs[i]);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on "+childs[i], YesNo.Yes);
							sa.assertTrue(false, "Not able to click on "+childs[i]);
						}
					}

				}

			} else {
				log(LogStatus.FAIL, "Map is empty its means No label is present with Quick Action Object so cannot continue test case", YesNo.Yes);
				sa.assertTrue(false, "Map is empty its means No label is present with Quick Action Object so cannot continue test case");
			}
		} else {
			log(LogStatus.FAIL, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.Yes);
			sa.assertTrue(false, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel);
		}
		System.err.println("parentChildForQuickActionObject : "+parentChildForQuickActionObject);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc007_VerifyTheNavigationMenuForWhichListViewObjectFilled(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		Map<String, String> parentChildForListViewObject = new LinkedHashMap<String, String>(); 
		lp.CRMLogin(superAdminUserName, adminPassword);
		System.err.println(NavigationMenuTestData_PEExcel+" >>>>>>>>>>>>><<<<<<<<<<<< "+NavigationMenuTestData_PESheet);

		if (!csvRecords.isEmpty()) {
			log(LogStatus.INFO, "Records Fetched from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.No);
			// Verify the items for which Quick Create Object field was fill
			String labelValue="";
			String listViewObject="";
			WebElement ele;
			String parent="";
			for (String labelName : csvRecords) {

				try {
					System.err.println(labelName);
					labelValue=labelName.split(commaSP)[0].trim();
					parent=labelName.split(commaSP)[2].trim();
					listViewObject=labelName.split(commaSP)[5].trim();
					System.out.println(labelValue+" >>>>>>> "+listViewObject);
					if (listViewObject.isEmpty() || listViewObject.equals("")) {
						listViewObject=none;
					} else {
						if (parentChildForListViewObject.get(parent)!=null) {
							String a=parentChildForListViewObject.get(parent);
							parentChildForListViewObject.put(parent,a+breakSP+labelValue);
						} else {
							parentChildForListViewObject.put(parent,labelValue);
						}
					}
				} catch (Exception e) {
					listViewObject=none;
					System.out.println("Exception "+labelValue+" >>>>>>> "+listViewObject);
				}


			}
			String childs[]=null;
			boolean flag=false;
			if (!parentChildForListViewObject.isEmpty()) {

				for (String pr : parentChildForListViewObject.keySet()) {
					childs=parentChildForListViewObject.get(pr).split(breakSP);
					for (int i = 0; i < childs.length; i++) {
						if (pr.isEmpty()) {

						} else {
							ele=npbl.getNavigationLabel(projectName, pr, action.BOOLEAN, 5);
							click(driver, ele, pr, action.BOOLEAN);
						}
						ele=npbl.getNavigationLabel(projectName, childs[i], action.BOOLEAN, 5);
						if (click(driver, ele, childs[i], action.BOOLEAN)) {
							log(LogStatus.INFO, "Not able to click on "+childs[i], YesNo.No);
							flag=npbl.isAutomationAllListViewAdded(projectName, 30);
							if (flag) {
								log(LogStatus.INFO, "List View is available after clicking on "+childs[i] , YesNo.No);
								
							} else {
								log(LogStatus.ERROR, "No List View is available is open after clicking on "+childs[i], YesNo.Yes);
								sa.assertTrue(false, "No List View is available is open after clicking on "+childs[i]);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on "+childs[i], YesNo.Yes);
							sa.assertTrue(false, "Not able to click on "+childs[i]);
						}
					}

				}

			} else {
				log(LogStatus.FAIL, "Map is empty its means No label is present with List View Object so cannot continue test case", YesNo.Yes);
				sa.assertTrue(false, "Map is empty its means No label is present with List View Object so cannot continue test case");
			}
		} else {
			log(LogStatus.FAIL, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.Yes);
			sa.assertTrue(false, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel);
		}
		System.err.println("parentChildForQuickActionObject : "+parentChildForListViewObject);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc008_VerifyTheitemsForWhichURLAndActionObjectAndLiewViewObjectFieldWasBlank(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		Map<String, String> parentChildForListViewObject = new LinkedHashMap<String, String>(); 
		lp.CRMLogin(superAdminUserName, adminPassword);
		System.err.println(NavigationMenuTestData_PEExcel+" >>>>>>>>>>>>><<<<<<<<<<<< "+NavigationMenuTestData_PESheet);

		if (!csvRecords.isEmpty()) {
			log(LogStatus.INFO, "Records Fetched from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.No);
			// Verify the items for which Quick Create Object field was fill
			String labelValue="";
			String listViewObject="";
			String quickActionObject="";
			String urlValue="";
			String expectedUrl="";
			String actualUrl;
			WebElement ele;
			String parent="";
			expectedUrl=getURL(driver, 10);
			for (String labelName : csvRecords) {

				try {
					System.err.println(labelName);
					labelValue=labelName.split(commaSP)[0].trim();
					parent=labelName.split(commaSP)[2].trim();
					urlValue=labelName.split(commaSP)[7].trim();
					quickActionObject=labelName.split(commaSP)[3].trim();
					listViewObject=labelName.split(commaSP)[5].trim();
					System.out.println(labelValue+" >>>>>>> "+listViewObject);
					if (listViewObject.isEmpty() || listViewObject.equals("") 
							|| quickActionObject.isEmpty() || quickActionObject.equals("") 
							|| urlValue.isEmpty() || urlValue.equals("")) {
						listViewObject=none;
					} else {
						if (parentChildForListViewObject.get(parent)!=null) {
							String a=parentChildForListViewObject.get(parent);
							parentChildForListViewObject.put(parent,a+breakSP+labelValue);
						} else {
							parentChildForListViewObject.put(parent,labelValue);
						}
					}
				} catch (Exception e) {
					listViewObject=none;
					System.out.println("Exception "+labelValue+" >>>>>>> "+listViewObject);
				}


			}
			String childs[]=null;
			boolean flag=false;
			if (!parentChildForListViewObject.isEmpty()) {

				for (String pr : parentChildForListViewObject.keySet()) {
					childs=parentChildForListViewObject.get(pr).split(breakSP);
					for (int i = 0; i < childs.length; i++) {
						if (pr.isEmpty()) {

						} else {
							ele=npbl.getNavigationLabel(projectName, pr, action.BOOLEAN, 5);
							click(driver, ele, pr, action.BOOLEAN);
						}
						ele=npbl.getNavigationLabel(projectName, childs[i], action.BOOLEAN, 5);
						if (click(driver, ele, childs[i], action.BOOLEAN)) {
							log(LogStatus.INFO, "Not able to click on "+childs[i], YesNo.No);
							flag=npbl.isAutomationAllListViewAdded(projectName, 10);	
							if (!flag && ele==null ) {
								log(LogStatus.INFO, "No List View is available after clicking on "+childs[i] , YesNo.No);

								if (!flag) {
									log(LogStatus.INFO, "List View is available after clicking on "+childs[i] , YesNo.No);
									ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, childs[i], action.BOOLEAN, 30);
									if (ele==null ) {
										log(LogStatus.INFO, "No Pop Up List View is open after clicking on "+childs[i] , YesNo.No);
										actualUrl=getURL(driver, 10);
										if (expectedUrl.contains(actualUrl)) {
											log(LogStatus.INFO, "No change in url link after click on "+childs[i], YesNo.No);
										} else {
											log(LogStatus.ERROR, "url link sholud not be changed after click on "+childs[i]+" Actual : "+actualUrl+"\t Expected : "+expectedUrl, YesNo.Yes);
											sa.assertTrue(false,"url link sholud not be changed after click on "+childs[i]+" Actual : "+actualUrl+"\t Expected : "+expectedUrl);
											refresh(driver);
										}
									} else {
										log(LogStatus.ERROR, "No Pop Up Should be open after clicking on "+childs[i], YesNo.Yes);
										sa.assertTrue(false, "No Pop Up Should be open after clicking on "+childs[i]);
									}

								} else {
									log(LogStatus.ERROR, "List View should not available after clicking on "+childs[i], YesNo.Yes);
									sa.assertTrue(false, "List View should not available after clicking on "+childs[i]);
								}

							} else {
								log(LogStatus.ERROR, "No List View is available is open after clicking on "+childs[i], YesNo.Yes);
								sa.assertTrue(false, "No List View is available is open after clicking on "+childs[i]);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on "+childs[i], YesNo.Yes);
							sa.assertTrue(false, "Not able to click on "+childs[i]);
						}
					}

				}

			} else {
				log(LogStatus.FAIL, "Map is empty its means No label For Which URL, ActionObject And Liew View Object Field is Blank", YesNo.Yes);
				sa.assertTrue(false, "Map is empty its means No label For Which URL, ActionObject And Liew View Object Field is Blank");
			}
		} else {
			log(LogStatus.FAIL, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.Yes);
			sa.assertTrue(false, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel);
		}
		System.err.println("parentChildForQuickActionObject : "+parentChildForListViewObject);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc009_1_UpdateReportsAndDashboardURLWithAnActualReporAndDashboardLink_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String[] tabs = {reportTab,dashBoardTab};
		List<String> reportsDashboardName = new LinkedList<String>();
		String all ="All ";
		String link="";
		String name="";
		String xpath ;
		WebElement ele;
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String navLb;
		for (int i=0;i<tabs.length;i++) {
			String tab=tabs[i];
			if (npbl.clickOnTab(projectName, tab)) {
				log(LogStatus.INFO, "Click on Tab : "+tab, YesNo.No);
				refresh(driver);
				String all1=all+tab;
				if(click(driver, npbl.getAll(projectName, tab, action.BOOLEAN, 20), all1, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+all1+" so going to write url for : "+tab, YesNo.No);
					ThreadSleep(5000);
					xpath = "//table/tbody//tr/th//a";
					ele = FindElement(driver, xpath, "Actual "+tab+" link", action.BOOLEAN, 20);
					if (ele!=null) {
						log(LogStatus.INFO, tab+" is found after click on "+all1 , YesNo.No);
						link= ele.getAttribute("href").trim();
						name=ele.getText().trim();
						reportsDashboardName.add(name);
						if (link.startsWith("http")) {
							log(LogStatus.INFO, "url link found for : "+name, YesNo.No);

							// Verification on navigation Tab
							navLb=navigationLabel.split(breakSP)[i];
							if (npbl.clickOnTab(projectName, navigationTab)) {
								log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
								if (npbl.clickOnAlreadyCreatedItem(projectName, navLb, true, 15)) {
									log(LogStatus.INFO,"Item found: "+navLb+" on Tab : "+navigationTab, YesNo.No);

									npbl.clickOnShowMoreDropdownOnly(projectName);
									ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Edit, 10);

									if (click(driver, ele, ShowMoreActionDropDownList.Edit.toString(), action.BOOLEAN)) {
										log(LogStatus.INFO, "Not Able to Click on Edit Button : "+navLb, YesNo.No);
										ele = npbl.getNavigationField(projectName, CSVLabel.URL.toString(), action.BOOLEAN, 20);
										if (sendKeys(driver, ele, link, CSVLabel.URL.toString(), action.BOOLEAN)) {
											log(LogStatus.INFO, "Able to enter "+CSVLabel.URL.toString(), YesNo.No);
											ThreadSleep(2000);

											if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.ERROR, "Click on save Button : "+navLb, YesNo.No);
												ThreadSleep(2000);

												if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, link, CSVLabel.Navigation_Label.toString(), navLb, CSVLabel.URL.toString())) {
													log(LogStatus.INFO, "Able to write under "+CSVLabel.URL.toString()+" for "+navLb, YesNo.No);
												} else {
													log(LogStatus.ERROR, "Not able to write under "+CSVLabel.URL.toString()+" for "+navLb, YesNo.Yes);
													sa.assertTrue(false, "Not able to write under "+CSVLabel.URL.toString()+" for "+navLb);
												}

											} else {
												log(LogStatus.ERROR, "Not Able to Click on save Button : "+navLb, YesNo.Yes);
												sa.assertTrue(false,"Not Able to Click on save Button : "+navLb);}

										} else {
											log(LogStatus.ERROR, "Not Able to enter "+CSVLabel.URL.toString(), YesNo.Yes);
											sa.assertTrue(false,"Not Able to enter "+CSVLabel.URL.toString());
										}
									} else {
										log(LogStatus.ERROR, "Not Able to Click on Edit Button : "+navLb, YesNo.Yes);
										sa.assertTrue(false,"Not Able to Click on Edit Button : "+navLb);
									}

								}else {

									log(LogStatus.ERROR,"Item not found: "+navLb+" on Tab : "+navigationTab, YesNo.Yes);
									sa.assertTrue(false,"Item not found: "+navLb+" on Tab : "+navigationTab);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
								sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
							}

						} else {
							log(LogStatus.ERROR, "url link not found for : "+name, YesNo.Yes);
							sa.assertTrue(false, "url link not found for : "+name);
						}
					} else {
						log(LogStatus.ERROR, tab+" is not found after click on "+all1, YesNo.Yes);
						sa.assertTrue(false, tab+" is not found after click on "+all1);
					}
					all1="";

				}else {
					log(LogStatus.ERROR, "Not Able to Click on "+all1+" so can not  write url for : "+tab, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on "+all1+" so can not  write url for : "+tab);

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+tab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+tab);
			}
		}
		System.err.println(reportsDashboardName);
		ExcelUtils.writeData(phase1DataSheetFilePath, createStringOutOfList(reportsDashboardName), "FilePath", excelLabel.TestCases_Name, currentlyExecutingTC,excelLabel.Redirection_Label_Name);
		createStringOutOfList(reportsDashboardName);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc009_2_UpdateReportsAndDashboardURLWithAnActualReporAndDashboardLink_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		WebElement ele;
		String dependentTC="Module3Tc009_1_UpdateReportsAndDashboardURLWithAnActualReporAndDashboardLink_Action";
		String[] navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name).split(breakSP);
		String redirectionLink=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Redirection_Label_Name);

		String redirectLink;
		String labelValue="";

		for (int i = 0; i < navigationLabel.length; i++) {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				labelValue=navigationLabel[i];
				ele=npbl.getNavigationLabel(projectName, labelValue, action.BOOLEAN, 10);
				if (click(driver, ele, labelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+labelValue+" going to verify url", YesNo.No);
					ThreadSleep(5000);
					redirectLink=redirectionLink.split(breakSP)[i];
					ele = FindElement(driver, "//*[text()='"+redirectLink+"']", redirectLink, action.BOOLEAN, 40);
					if (ele!=null) {
						log(LogStatus.ERROR, "After clicking : "+labelValue+" redirected to "+redirectLink, YesNo.No);
					} else {
						log(LogStatus.ERROR, "After clicking : "+labelValue+" should be redirected to "+redirectLink, YesNo.Yes);
						sa.assertTrue(false,"After clicking : "+labelValue+" should be redirected to "+redirectLink);
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
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc010_VerifyTheFieldsOnNavigationObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		List<String> navigationField = new LinkedList<String>();
		navigationField.add("Navigation Label");
		navigationField.add("URL");
		navigationField.add("Parent");
		navigationField.add("Order");
		navigationField.add("Action Object");
		navigationField.add("Action Record Type");
		navigationField.add("List View Object");
		navigationField.add("List View Name");
		if (npbl.clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
			if(clickUsingJavaScript(driver, lp.getNewButton(projectName, 10), "new button")) {
				log(LogStatus.INFO, "Click on new button going to verify navigation field : "+navigationField, YesNo.No);
				WebElement ele = npbl.getCrossButtonForNavigationLabelPopuP(projectName, navigationTab, action.BOOLEAN, 60);
				if (ele!=null) {
					log(LogStatus.INFO, "Pop Up open after clicking on new button for : "+navigationTab , YesNo.No);
					for (String nf : navigationField) {
						ele = npbl.getNavigationField(projectName, nf, action.SCROLLANDBOOLEAN, 2);
						if (ele!=null) {
							log(LogStatus.INFO, nf+" field is present & verified on "+navigationTab+" pop up" , YesNo.No);
						} else {
							log(LogStatus.ERROR, nf+" field not present on "+navigationTab+" pop up" , YesNo.Yes);
							sa.assertTrue(false, nf+" field not present on "+navigationTab+" pop up");
						}
					}
				} else {
					log(LogStatus.ERROR, "No Pop Up is open after clicking on new button for : "+navigationTab, YesNo.Yes);
					sa.assertTrue(false, "No Pop Up is open after clicking on new button for : "+navigationTab);
				}


			}else {
				log(LogStatus.ERROR, "Not Able to Click on new button so cannot verify navigation field : "+navigationField, YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on new button so cannot verify navigation field : "+navigationField);

			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
		}
	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void Module3Tc011_RenameNavigationLabelAndVerifyImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String[] tabs = {reportTab,dashBoardTab};

		String updatedName="";
		WebElement ele;
		String[] navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name).split(breakSP);
		String[] updatedNavigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Update_Navigation_Label_Name).split(breakSP);

		String navLb;
		for (int j = 0; j < tabs.length; j++) {

			navLb=navigationLabel[j];
			if (npbl.clickOnTab(projectName, navigationTab)) {
				log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
				if (npbl.clickOnAlreadyCreatedItem(projectName, navLb, true, 15)) {
					log(LogStatus.INFO,"Item found: "+navLb+" on Tab : "+navigationTab, YesNo.No);

					npbl.clickOnShowMoreDropdownOnly(projectName);
					ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Edit, 10);

					if (click(driver, ele, ShowMoreActionDropDownList.Edit.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "Not Able to Click on Edit Button : "+navLb, YesNo.No);
						ele = npbl.getNavigationField(projectName, CSVLabel.Navigation_Label.toString(), action.BOOLEAN, 20);
						updatedName=updatedNavigationLabel[j];
						if (sendKeys(driver, ele, updatedName, CSVLabel.Navigation_Label.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Able to enter "+CSVLabel.Navigation_Label.toString(), YesNo.No);
							ThreadSleep(2000);

							if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "Click on save Button : "+navLb, YesNo.No);
								ThreadSleep(2000);

								if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, updatedName, CSVLabel.Navigation_Label.toString(), navLb, CSVLabel.Navigation_Label.toString())) {
									log(LogStatus.INFO, updatedName+" value has been written under "+CSVLabel.Navigation_Label.toString()+" for "+navLb, YesNo.No);

									if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
										log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
										ele=npbl.getNavigationLabel(projectName, updatedName, action.BOOLEAN, 10);
										if (ele!=null) {
											log(LogStatus.INFO, navLb+" has been updated to : "+updatedName, YesNo.No);	
										} else {
											log(LogStatus.ERROR, navLb+" should updated to : "+updatedName, YesNo.Yes);
											sa.assertTrue(false,navLb+" should updated to : "+updatedName);

										}
										click(driver, npbl.getNavatarQuickLinkMinimize_Lighting(projectName, 3), "Minimize Icon", action.BOOLEAN);


									} else {
										log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+updatedName, YesNo.Yes);
										sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+updatedName);
									}

								} else {
									log(LogStatus.ERROR, updatedName+" value has not been written under "+CSVLabel.Navigation_Label.toString()+" for "+navLb, YesNo.Yes);
									sa.assertTrue(false, updatedName+" value has not been written under "+CSVLabel.Navigation_Label.toString()+" for "+navLb);
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on save Button : "+navLb, YesNo.Yes);
								sa.assertTrue(false,"Not Able to Click on save Button : "+navLb);}

						} else {
							log(LogStatus.ERROR, "Not Able to enter "+CSVLabel.Navigation_Label.toString(), YesNo.Yes);
							sa.assertTrue(false,"Not Able to enter "+CSVLabel.Navigation_Label.toString());
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button : "+navLb, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button : "+navLb);
					}

				}else {

					log(LogStatus.ERROR,"Item not found: "+navLb+" on Tab : "+navigationTab, YesNo.Yes);
					sa.assertTrue(false,"Item not found: "+navLb+" on Tab : "+navigationTab);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
			}

		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc012_ChangeTheOrderOfTheLabelAndVerifyImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	//	lp.CRMLogin(superAdminUserName, adminPassword);
		String updatedOrder="";
		WebElement ele;
		String[] navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name).split(breakSP);
		String[] updatedOrderLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Updated_Order).split(breakSP);

		String navLb;
		for (int j = 0; j < navigationLabel.length; j++) {

			navLb=navigationLabel[j];
			if (npbl.clickOnTab(projectName, navigationTab)) {
				log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
				if (npbl.clickOnAlreadyCreatedItem(projectName, navLb, true, 15)) {
					log(LogStatus.INFO,"Item found: "+navLb+" on Tab : "+navigationTab, YesNo.No);

					npbl.clickOnShowMoreDropdownOnly(projectName);
					ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Edit, 10);

					if (click(driver, ele, ShowMoreActionDropDownList.Edit.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "Not Able to Click on Edit Button : "+navLb, YesNo.No);
						ele = npbl.getNavigationField(projectName, CSVLabel.Order.toString(), action.BOOLEAN, 20);
						updatedOrder=updatedOrderLabel[j];
						try {
							ele.clear();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ThreadSleep(2000);
						if (sendKeys(driver, ele, updatedOrder, CSVLabel.Order.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Able to enter "+CSVLabel.Order.toString(), YesNo.No);
							ThreadSleep(2000);

							if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "Click on save Button : "+navLb, YesNo.No);
								ThreadSleep(2000);

								if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, updatedOrder, CSVLabel.Navigation_Label.toString(), navLb, CSVLabel.Order.toString())) {
									log(LogStatus.INFO, updatedOrder+" value has been written under "+CSVLabel.Order.toString()+" for "+navLb, YesNo.No);
								} else {
									log(LogStatus.ERROR, updatedOrder+" value has not been written under "+CSVLabel.Order.toString()+" for "+navLb, YesNo.Yes);
									sa.assertTrue(false, updatedOrder+" value has not been written under "+CSVLabel.Order.toString()+" for "+navLb);
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on save Button : "+navLb, YesNo.Yes);
								sa.assertTrue(false,"Not Able to Click on save Button : "+navLb);}

						} else {
							log(LogStatus.ERROR, "Not Able to enter "+CSVLabel.Order.toString(), YesNo.Yes);
							sa.assertTrue(false,"Not Able to enter "+CSVLabel.Order.toString());
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button : "+navLb, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button : "+navLb);
					}

				}else {

					log(LogStatus.ERROR,"Item not found: "+navLb+" on Tab : "+navigationTab, YesNo.Yes);
					sa.assertTrue(false,"Item not found: "+navLb+" on Tab : "+navigationTab);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
			}

		}

		switchToDefaultContent(driver);
		ThreadSleep(2000);
		csvRecords=ExcelUtils.readAllDataFromCSVFileIntoList(NavigationMenuTestData_PEExcel, false);
		System.err.println(csvRecords);
		if (!csvRecords.isEmpty()) {
			log(LogStatus.INFO, "Records Fetched from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.No);

			Map<String, Integer> navigationParentLabelWithOrder = navigationParentLabelWithOrder(csvRecords);
			System.err.println(navigationParentLabelWithOrder);
			Map<String, Integer> navigationParentLabelWithSortedOrder = sortByValue(true, navigationParentLabelWithOrder);
			System.err.println(navigationParentLabelWithOrder);
			Map<String, String> navigationParentLabelWithChildAndOrder = navigationParentLabelWithChildAndOrder(csvRecords);
			System.err.println(navigationParentLabelWithChildAndOrder);
			Map<String, String> navigationParentLabelWithChildSorted = navigationParentLabelWithChildSorted(navigationParentLabelWithChildAndOrder);
			System.err.println(navigationParentLabelWithChildSorted);
			// Verification on navigation menu
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				npbl.verifyingNavigationMenuLink(projectName, navigationParentLabelWithSortedOrder, navigationParentLabelWithChildSorted, action.BOOLEAN, 0);;
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
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
	public void Module3Tc015_1_AddNewNavigationItemsAndVerifyItOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		List<String> navigationField = new LinkedList<String>();
		navigationField.add("Navigation Label");
		navigationField.add("Order");
		navigationField.add("Parent");
		navigationField.add("Action Object");
		navigationField.add("Action Record Type");
		navigationField.add("List View Object");
		navigationField.add("List View Name");
		navigationField.add("URL");
		String[] labelsValues= new String[navigationField.size()];
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);

		for(int i=0;i<csvRecords.size();i++) {
			if (csvRecords.get(i).contains(navigationLabel)) {
				System.err.println("pass : "+csvRecords.get(i));
				String[] record = csvRecords.get(i).split(commaSP);
				for (int j = 0; j < record.length; j++) {
					labelsValues[j]=record[j].trim();
					System.err.println("labelsValues[j] : "+labelsValues[j]);
				}
				

				String navigationField1;
				String navigationValue;

				if (npbl.clickOnTab(projectName, navigationTab)) {
					log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
					if(clickUsingJavaScript(driver, lp.getNewButton(projectName, 10), "new button")) {
						log(LogStatus.INFO, "Click on new button going to verify navigation field : "+navigationField, YesNo.No);
						WebElement ele = npbl.getCrossButtonForNavigationLabelPopuP(projectName, navigationTab, action.BOOLEAN, 60);
						if (ele!=null) {
							log(LogStatus.INFO, "Pop Up open after clicking on new button for : "+navigationTab , YesNo.No);
							for (int m = 0; m < labelsValues.length; m++) {
								if (!labelsValues[m].equals("") || labelsValues[m].isEmpty()) {
									ThreadSleep(1000);
									navigationField1=navigationField.get(m);
									navigationValue=labelsValues[m];
									ele = npbl.getNavigationField(projectName, navigationField1, action.BOOLEAN, 20);
									if (sendKeys(driver, ele, navigationValue, navigationField1, action.BOOLEAN)) {
										log(LogStatus.INFO, "Able to enter "+navigationField1, YesNo.No);
										ThreadSleep(500);

									} else {
										log(LogStatus.ERROR, "Not Able to enter "+navigationField1, YesNo.Yes);
										sa.assertTrue(false,"Not Able to enter "+navigationField1);
									}

								} 

							}

							if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "Click on save Button : "+navigationLabel, YesNo.No);
								ThreadSleep(2000);
								
								if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
									log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
									String xpath="//div[@id='treeview12']//*//*[text()='"+navigationLabel+"']"+"/../following-sibling::*//*[text()='"+navigationLabel+"']";
									 ele =FindElement(driver, xpath, navigationLabel, action.BOOLEAN, 10);
									 ele=isDisplayed(driver, ele, "visibility", 10, navigationLabel);
									 if (ele!=null) {
											log(LogStatus.INFO, "Duplicate "+navigationLabel+" label verified", YesNo.No);	
									} else {
										log(LogStatus.ERROR, "Duplicate "+navigationLabel+" label not verified", YesNo.Yes);
										sa.assertTrue(false,"Duplicate "+navigationLabel+" label not verified");
								
									}
									
								} else {
									log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
									sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
								}


							} else {
								log(LogStatus.ERROR, "Not Able to Click on save Button : "+navigationLabel, YesNo.Yes);
								sa.assertTrue(false,"Not Able to Click on save Button : "+navigationLabel);

							}

						} else {
							log(LogStatus.ERROR, "No Pop Up is open after clicking on new button for : "+navigationTab, YesNo.Yes);
							sa.assertTrue(false, "No Pop Up is open after clicking on new button for : "+navigationTab);
						}


					}else {
						log(LogStatus.ERROR, "Not Able to Click on new button so cannot verify navigation field : "+navigationField, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on new button so cannot verify navigation field : "+navigationField);

					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
				}
				break;
			} else {
				if (i==csvRecords.size()-1) {
					log(LogStatus.ERROR, "Record Not Found so cannot create Duplicate Label : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false, "Record Not Found so cannot create Duplicate Label : "+navigationLabel);
					
				}
				}
		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc016_DeleteTheNavigationItemAndVerifyItOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);

		WebElement ele;
		String dependentTC = "Module3Tc015_1_AddNewNavigationItemsAndVerifyItOnNavigationMenu";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);

		String navLb;
		navLb=navigationLabel;
		if (npbl.clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);

			if (npbl.clickOnAlreadyCreatedItem(projectName, navLb, true, 15)) {
				log(LogStatus.INFO,"Item found: "+navLb+" on Tab : "+navigationTab, YesNo.No);

				npbl.clickOnShowMoreDropdownOnly(projectName);
				ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Delete, 10);

				if (click(driver, ele, ShowMoreActionDropDownList.Delete.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on delete Button : "+navLb, YesNo.No);

					if(click(driver,npbl.getDeleteButtonPopUp(projectName, 10), "delete", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on delete button on delete popup : "+TabName.Object2Tab+" For : "+navLb,YesNo.No); 
						ThreadSleep(5000);

						if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
							log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
							String xpath="//div[@id='treeview12']//*//*[text()='"+navigationLabel+"']"+"/../following-sibling::*//*[text()='"+navigationLabel+"']";
							ele =FindElement(driver, xpath, navigationLabel, action.BOOLEAN, 2);
							ele=isDisplayed(driver, ele, "visibility", 10, navigationLabel);
							if (ele==null) {
								log(LogStatus.INFO, "Duplicate "+navigationLabel+" label is not present after delete", YesNo.No);	
							} else {
								log(LogStatus.ERROR, "Duplicate "+navigationLabel+" label should not present after delete", YesNo.Yes);
								sa.assertTrue(false,"Duplicate "+navigationLabel+" label should not present after delete");

							}

						} else {
							log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
							sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
						}

					}else {
						sa.assertTrue(false,"Not Able to Select delete button for "+navLb);
						log(LogStatus.SKIP,"Not Able to Select delete button for "+navLb,YesNo.Yes);

					}
				}

				else {
					log(LogStatus.ERROR, "Not Able to Click on delete Button : "+navLb, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on delete Button : "+navLb);
				}


			}else {

				log(LogStatus.ERROR,"Item not found: "+navLb+" on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Item not found: "+navLb+" on Tab : "+navigationTab);
			}


		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc017_VerifyTheSizeOfNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);

		String panelWidth="500";
		String panelHeight="500";
		for (int i = 0; i < 2; i++) {
			
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to update Navigation Label", YesNo.No);
					ThreadSleep(500);

					if(setup.searchStandardOrCustomObject(environment,mode, object.App_Manager)) {
						log(LogStatus.INFO, "click on Object : "+object.App_Manager, YesNo.No);
						ThreadSleep(2000);
						if(setup.clickOnEditForApp(driver, appName, AppDeveloperName,AppDescription, 10)) {
							log(LogStatus.INFO,"click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription, YesNo.No);
							ThreadSleep(1000);

							//Utlity Items
							if (setup.clickOnAppSettingList(driver, AppSetting.Utility_Items, 10)) {
								log(LogStatus.INFO, "able to click on "+AppSetting.Utility_Items, YesNo.No);
								ThreadSleep(500);
								if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Panel_Width.toString(), action.BOOLEAN, 10),panelWidth,PageLabel.Panel_Width.toString()+" textbox value : "+panelWidth,action.BOOLEAN)) {
									ThreadSleep(500);
									log(LogStatus.INFO,"send value to "+PageLabel.Panel_Width.toString()+" textbox value : "+panelWidth,YesNo.No);
									if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Panel_Height.toString(), action.BOOLEAN, 10),panelHeight,PageLabel.Panel_Height.toString()+" textbox value : "+panelHeight,action.BOOLEAN)) {
										ThreadSleep(500);
										log(LogStatus.INFO,"send value to "+PageLabel.Panel_Height.toString()+" textbox value : "+panelHeight,YesNo.No);

										if (click(driver, setup.getCustomTabSaveBtn(projectName, 10)," Save Button", action.BOOLEAN)) {
											log(LogStatus.INFO,"Click on Save Button",YesNo.No);
											ThreadSleep(5000);			
										} else {
											sa.assertTrue(false, "Not Able to Click on Save Button");
											log(LogStatus.FAIL,"Not Able to Click on Save Button",YesNo.Yes);
										}

									} else {
										sa.assertTrue(false, "Not Able to send value to "+PageLabel.Panel_Height.toString()+" textbox value : "+panelHeight);
										log(LogStatus.FAIL,"Not Able to send value to "+PageLabel.Panel_Height.toString()+" textbox value : "+panelHeight,YesNo.Yes);
									}
								} else {
									sa.assertTrue(false, "Not Able to send value to "+PageLabel.Panel_Width.toString()+" textbox value : "+panelWidth);
									log(LogStatus.FAIL,"Not Able to send value to "+PageLabel.Panel_Width.toString()+" textbox value : "+panelWidth,YesNo.Yes);
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
						log(LogStatus.ERROR, "Not able to search/click on Apps so cannot update Navigation Label", YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on Apps manager so cannot update Navigation Label");
					}

					driver.close();
					driver.switchTo().window(parentID);
					ThreadSleep(5000);
					refresh(driver);
					if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
						log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
						String xpath="//div[@id='treeview12']/../../../../../..";
						WebElement ele = FindElement(driver, xpath, navigationMenuName, action.BOOLEAN, 2);
						System.err.println(ele.getSize());
						System.err.println(ele.getSize().getHeight());
						System.err.println(ele.getSize().getWidth());
						System.err.println(ele.getLocation());
						System.err.println(ele.getRect());

						if (ele!=null) {
							int height = ele.getSize().getHeight();
							int width=ele.getSize().getWidth();
							if (Integer.parseInt(panelWidth)==width && Integer.parseInt(panelHeight)==height) {
								log(LogStatus.INFO, navigationMenuName+" dimension verified Actual Width & Height : "+width+" "+height+"\t Expected Width & Height : "+panelWidth+" "+panelHeight, YesNo.No);
									
							} else {
								log(LogStatus.ERROR, navigationMenuName+" dimension not verified Actual Width & Height : "+width+" "+height+"\t Expected Width & Height : "+panelWidth+" "+panelHeight, YesNo.Yes);
								sa.assertTrue(false,navigationMenuName+" dimension not verified Actual Width & Height : "+width+" "+height+"\t Expected Width & Height : "+panelWidth+" "+panelHeight);
							}
						} else {
							log(LogStatus.ERROR, navigationMenuName+" element not found so cannot check dimension", YesNo.Yes);
							sa.assertTrue(false,navigationMenuName+" element not found so cannot check dimension");
						}
						panelWidth="340";
						panelHeight="480";
					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
					}
					
				}else {
					log(LogStatus.FAIL, "could not find new window to switch, so cannot update Navigation Label", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, so cannot update Navigation Label");
				}

			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc018_CreateSubMenuAndVerify(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String updatedParent="";
		WebElement ele;
		String[] navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name).split(breakSP);
		String[] updatedParentLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Parent).split(breakSP);

		String navLb;
		for (int j = 0; j < navigationLabel.length; j++) {

			navLb=navigationLabel[j];
			if (npbl.clickOnTab(projectName, navigationTab)) {
				log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
				if (npbl.clickOnAlreadyCreatedItem(projectName, navLb, true, 15)) {
					log(LogStatus.INFO,"Item found: "+navLb+" on Tab : "+navigationTab, YesNo.No);

					npbl.clickOnShowMoreDropdownOnly(projectName);
					ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Edit, 10);

					if (click(driver, ele, ShowMoreActionDropDownList.Edit.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "Not Able to Click on Edit Button : "+navLb, YesNo.No);
						ele = npbl.getNavigationField(projectName, CSVLabel.Parent.toString(), action.BOOLEAN, 20);
						updatedParent=updatedParentLabel[j];
						try {
							ele.clear();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ThreadSleep(2000);
						if (sendKeys(driver, ele, updatedParent, CSVLabel.Parent.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Able to enter "+CSVLabel.Parent.toString(), YesNo.No);
							ThreadSleep(2000);
							if (click(driver,FindElement(driver,"//*[@title='"+updatedParent+"']//*[text()='"+updatedParent+"']",updatedParent, action.SCROLLANDBOOLEAN, 30),
									updatedParent + "   :  Parent Name", action.BOOLEAN)) {
								log(LogStatus.INFO, updatedParent+" is available", YesNo.No);
							} else {
								log(LogStatus.ERROR, updatedParent+" is not available", YesNo.Yes);
								sa.assertTrue(false, updatedParent+" is not available");

							}
							if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "Click on save Button : "+navLb, YesNo.No);
								ThreadSleep(2000);

								if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, updatedParent, CSVLabel.Navigation_Label.toString(), navLb, CSVLabel.Parent.toString())) {
									log(LogStatus.INFO, updatedParent+" value has been written under "+CSVLabel.Parent.toString()+" for "+navLb, YesNo.No);
								} else {
									log(LogStatus.ERROR, updatedParent+" value has not been written under "+CSVLabel.Parent.toString()+" for "+navLb, YesNo.Yes);
									sa.assertTrue(false, updatedParent+" value has not been written under "+CSVLabel.Parent.toString()+" for "+navLb);
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on save Button : "+navLb, YesNo.Yes);
								sa.assertTrue(false,"Not Able to Click on save Button : "+navLb);}

						} else {
							log(LogStatus.ERROR, "Not Able to enter "+CSVLabel.Parent.toString(), YesNo.Yes);
							sa.assertTrue(false,"Not Able to enter "+CSVLabel.Parent.toString());
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button : "+navLb, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button : "+navLb);
					}

				}else {

					log(LogStatus.ERROR,"Item not found: "+navLb+" on Tab : "+navigationTab, YesNo.Yes);
					sa.assertTrue(false,"Item not found: "+navLb+" on Tab : "+navigationTab);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
			}

		}

		switchToDefaultContent(driver);
		ThreadSleep(2000);
		csvRecords=ExcelUtils.readAllDataFromCSVFileIntoList(NavigationMenuTestData_PEExcel, false);
		System.err.println(csvRecords);
		if (!csvRecords.isEmpty()) {
			log(LogStatus.INFO, "Records Fetched from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.No);

			Map<String, Integer> navigationParentLabelWithOrder = navigationParentLabelWithOrder(csvRecords);
			System.err.println(navigationParentLabelWithOrder);
			Map<String, Integer> navigationParentLabelWithSortedOrder = sortByValue(true, navigationParentLabelWithOrder);
			System.err.println(navigationParentLabelWithOrder);
			Map<String, String> navigationParentLabelWithChildAndOrder = navigationParentLabelWithChildAndOrder(csvRecords);
			System.err.println(navigationParentLabelWithChildAndOrder);
			Map<String, String> navigationParentLabelWithChildSorted = navigationParentLabelWithChildSorted(navigationParentLabelWithChildAndOrder);
			System.err.println(navigationParentLabelWithChildSorted);
			// Verification on navigation menu
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				npbl.verifyingNavigationMenuLink(projectName, navigationParentLabelWithSortedOrder, navigationParentLabelWithChildSorted, action.BOOLEAN, 0);;
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
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
	public void Module3Tc019_CreateSubMenuForIIndlevelAndVerifyItOnTheNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		List<String> navigationField = new LinkedList<String>();
		navigationField.add("Navigation Label");
		navigationField.add("Order");
		navigationField.add("Parent");
		navigationField.add("Action Object");
		navigationField.add("Action Record Type");
		navigationField.add("List View Object");
		navigationField.add("List View Name");
		navigationField.add("URL");
		String[] labelsValues= new String[navigationField.size()];
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String navigationField1;
		String navigationValue;
		labelsValues=navigationLabel.split(",");
		if (npbl.clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
			if(clickUsingJavaScript(driver, lp.getNewButton(projectName, 10), "new button")) {
				log(LogStatus.INFO, "Click on new button going to verify navigation field : "+navigationField, YesNo.No);
				WebElement ele = npbl.getCrossButtonForNavigationLabelPopuP(projectName, navigationTab, action.BOOLEAN, 60);
				if (ele!=null) {
					log(LogStatus.INFO, "Pop Up open after clicking on new button for : "+navigationTab , YesNo.No);
					for (int m = 0; m < labelsValues.length; m++) {
						if (!labelsValues[m].equals("") || labelsValues[m].isEmpty()) {
							ThreadSleep(1000);
							navigationField1=navigationField.get(m);
							navigationValue=labelsValues[m];
							ele = npbl.getNavigationField(projectName, navigationField1, action.BOOLEAN, 20);
							if (sendKeys(driver, ele, navigationValue, navigationField1, action.BOOLEAN)) {
								log(LogStatus.INFO, "Able to enter "+navigationField1, YesNo.No);
								ThreadSleep(500);
								if (m==2) {
									ThreadSleep(10000);
									if (click(driver,npbl.getItemInList(projectName, navigationValue, action.BOOLEAN, 20),
											navigationValue + "   :  Parent Name", action.BOOLEAN)) {
										log(LogStatus.INFO, navigationValue+" is available", YesNo.No);
									} else {
										log(LogStatus.ERROR, navigationValue+" is not available", YesNo.Yes);
										sa.assertTrue(false, navigationValue+" is not available");

									}	
								}

							} else {
								log(LogStatus.ERROR, "Not Able to enter "+navigationField1, YesNo.Yes);
								sa.assertTrue(false,"Not Able to enter "+navigationField1);
							}

						} 

					}

					if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.ERROR, "Click on save Button : "+navigationLabel, YesNo.No);
						ThreadSleep(5000);

						if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
							log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
							List<WebElement> eleList = npbl.getExpandIcon(projectName, 10);
							if (eleList.size()==2) {
								click(driver, eleList.get(0), "> icon", action.BOOLEAN);
								ThreadSleep(3000);
								navigationValue=labelsValues[0];
								ele=npbl.getNavigationLabel(projectName, navigationValue, action.BOOLEAN, 5);
								if (ele==null) {
									log(LogStatus.INFO, navigationValue+" is not present till 1 level menu is supported.", YesNo.No);	
								} else {
									log(LogStatus.ERROR, navigationValue+" should not be present till 1 level menu is supported.", YesNo.Yes);
									sa.assertTrue(false,navigationValue+" is should not be present till 1 level menu is supported.");

								}
								eleList = npbl.getExpandIcon(projectName, 10);
								if (eleList.size()==1) {
									log(LogStatus.INFO, "> icon verified", YesNo.No);
								} else {
									log(LogStatus.ERROR, "> icon not verified", YesNo.Yes);
									sa.assertTrue(false,"> icon not verified");
								}
								click(driver, eleList.get(0), "> icon", action.BOOLEAN);
								ThreadSleep(3000);
								if (eleList.size()==1) {
									log(LogStatus.INFO, "> icon verified", YesNo.No);
								} else {
									log(LogStatus.ERROR, "> icon not verified", YesNo.Yes);
									sa.assertTrue(false,"> icon not verified");
								}
							}else {

								log(LogStatus.ERROR, "> icon not verified", YesNo.Yes);
								sa.assertTrue(false,"> icon not verified");
							} 	

						} else {
							log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
							sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
						}


					} else {
						log(LogStatus.ERROR, "Not Able to Click on save Button : "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on save Button : "+navigationLabel);

					}

				} else {
					log(LogStatus.ERROR, "No Pop Up is open after clicking on new button for : "+navigationTab, YesNo.Yes);
					sa.assertTrue(false, "No Pop Up is open after clicking on new button for : "+navigationTab);
				}


			}else {
				log(LogStatus.ERROR, "Not Able to Click on new button so cannot verify navigation field : "+navigationField, YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on new button so cannot verify navigation field : "+navigationField);

			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
		}



		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc021_FillThirdPartyWebsiteToReportAndDashboardAndVerifyTheNavigation(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String[] navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name).split(breakSP);
		String[] thirdPartyURLS= {"https://www.hubspot.com","https://www.preqin.com","https://pe4604.lightning.force.com/lightning/n/My_App_Page"};
		String[] navigationLabelWithParent=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_With_Parent).split(breakSP);

		String navLb;
		String thirdPartyURL;
		for (int j = 0; j < navigationLabel.length; j++) {

			navLb=navigationLabel[j];
			if (npbl.clickOnTab(projectName, navigationTab)) {
				log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
				if (npbl.clickOnAlreadyCreatedItem(projectName, navLb, true, 15)) {
					log(LogStatus.INFO,"Item found: "+navLb+" on Tab : "+navigationTab, YesNo.No);

					npbl.clickOnShowMoreDropdownOnly(projectName);
					ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Edit, 10);

					if (click(driver, ele, ShowMoreActionDropDownList.Edit.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "Not Able to Click on Edit Button : "+navLb, YesNo.No);
						ele = npbl.getNavigationField(projectName, CSVLabel.URL.toString(), action.BOOLEAN, 20);
						thirdPartyURL = thirdPartyURLS[j];
						try {
							ele.clear();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ThreadSleep(2000);
						if (sendKeys(driver, ele, thirdPartyURL, CSVLabel.URL.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Able to enter "+CSVLabel.URL.toString(), YesNo.No);
							ThreadSleep(2000);
							if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "Click on save Button : "+navLb, YesNo.No);
								ThreadSleep(2000);

								if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, thirdPartyURL, CSVLabel.Navigation_Label.toString(), navLb, CSVLabel.URL.toString())) {
									log(LogStatus.INFO, thirdPartyURL+" value has been written under "+CSVLabel.URL.toString()+" for "+navLb, YesNo.No);
								} else {
									log(LogStatus.ERROR, thirdPartyURL+" value has not been written under "+CSVLabel.URL.toString()+" for "+navLb, YesNo.Yes);
									sa.assertTrue(false, thirdPartyURL+" value has not been written under "+CSVLabel.URL.toString()+" for "+navLb);
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on save Button : "+navLb, YesNo.Yes);
								sa.assertTrue(false,"Not Able to Click on save Button : "+navLb);}

						} else {
							log(LogStatus.ERROR, "Not Able to enter "+CSVLabel.Parent.toString(), YesNo.Yes);
							sa.assertTrue(false,"Not Able to enter "+CSVLabel.Parent.toString());
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button : "+navLb, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button : "+navLb);
					}

				}else {

					log(LogStatus.ERROR,"Item not found: "+navLb+" on Tab : "+navigationTab, YesNo.Yes);
					sa.assertTrue(false,"Item not found: "+navLb+" on Tab : "+navigationTab);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
			}

		}

		String redirectLink;
		String labelValue="";
		String actualUrl="";

		for (int i = 0; i < navigationLabelWithParent.length; i++) {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				labelValue=navigationLabelWithParent[i];
				ele=npbl.getNavigationLabel(projectName, labelValue, action.BOOLEAN, 10);
				if (click(driver, ele, labelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+labelValue+" going to verify url", YesNo.No);
					ThreadSleep(5000);
					String pareNTiD=switchOnWindow(driver);
					if (pareNTiD!=null) {
						
						redirectLink=thirdPartyURLS[i];
						actualUrl=getURL(driver, 60);
						if (redirectLink.equalsIgnoreCase(actualUrl) || actualUrl.equalsIgnoreCase(redirectLink)) {
							log(LogStatus.ERROR, "After clicking : "+labelValue+" redirected to "+redirectLink, YesNo.No);
						} else {
							log(LogStatus.ERROR, "After clicking : "+labelValue+" should be redirected to "+redirectLink, YesNo.Yes);
							sa.assertTrue(false,"After clicking : "+labelValue+" should be redirected to "+redirectLink);
							
						}
						driver.close();
						driver.switchTo().window(pareNTiD);
					} else {
						log(LogStatus.ERROR, "No new window is open for  "+labelValue+" so cannot verify url", YesNo.Yes);
						sa.assertTrue(false,"No new window is open for  "+labelValue+" so cannot verify url");
					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+labelValue+" so cannot verify url", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+labelValue+" so cannot verify url");

				}
				click(driver, npbl.getNavatarQuickLinkMinimize_Lighting(projectName, 3), "Minimize Icon", action.BOOLEAN);
				refresh(driver);
				ThreadSleep(5000);

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+labelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+labelValue);
			}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc023_CreateALightningAppPageAndVerifyTheNavigationForLighteningAppPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to update Navigation Label", YesNo.No);
				ThreadSleep(500);

				if(setup.searchStandardOrCustomObject(environment,mode, object.Lightning_App_Builder)) {
					log(LogStatus.INFO, "click on Object : "+object.Lightning_App_Builder, YesNo.No);
					ThreadSleep(2000);
					//						setup.clickOnEditForApp(driver, appName, AppDeveloperName,"Navatar Private Equity app � Lightning View(Edge)", 10);
					//						scn.nextLine();
					switchToFrame(driver, 20, setup.getSetUpPageIframe(60));
					if(clickUsingJavaScript(driver, lp.getNewButton(projectName, 60), "new button")) {
						log(LogStatus.INFO, "Click on new button going to create Lightning App : "+myAppPage, YesNo.No);	
						switchToDefaultContent(driver);
						if(click(driver, npbl.getLightningPageNextBtn(projectName, 120), "Next Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on next button going to create Lightning App : "+myAppPage, YesNo.No);
							ThreadSleep(500);
							if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Label.toString(), action.BOOLEAN, 10),myAppPage,PageLabel.Label.toString()+" textbox value : "+myAppPage,action.BOOLEAN)) {
								ThreadSleep(500);
								log(LogStatus.INFO,"send value to "+PageLabel.Label.toString()+" textbox value : "+myAppPage,YesNo.No);
								if(click(driver, npbl.getLightningPageNextBtn2(projectName, 30), "Next Button2", action.BOOLEAN)) {
									log(LogStatus.INFO, "Click on next button going to create Lightning App : "+myAppPage, YesNo.No);
									ThreadSleep(500);
									
									if(click(driver, npbl.getLightningPagFinishBtn(projectName, 30), "Finish Button", action.BOOLEAN)) {
										log(LogStatus.INFO, "Click on Finish button going to create Lightning App : "+myAppPage, YesNo.No);
										ThreadSleep(500);
										
										if (click(driver, edit.getEditPageSaveButton(projectName, 30),"Save Button", action.BOOLEAN)) {
											log(LogStatus.INFO,"Click on Save Button",YesNo.No);
											ThreadSleep(5000);
											
										if (click(driver, edit.getActivateButton(projectName, 30),"Activate Button", action.BOOLEAN)) {
												log(LogStatus.INFO,"Click on Activate Button",YesNo.No);
												ThreadSleep(2000);
												
												///
												
												if (npbl.addingPageToApp(projectName, appName, action.BOOLEAN, 20)) {
													
													if (click(driver, edit.getEditPageSaveButton2(projectName, 30),"Save Button2", action.BOOLEAN)) {
														log(LogStatus.INFO,"Click on Save Button2",YesNo.No);
														ThreadSleep(5000);
														
														click(driver, edit.getFinishButton2(projectName, 30),"Finish Button2", action.BOOLEAN);
//														if (click(driver, edit.getFinishButton2(projectName, 30),"Finish Button2", action.BOOLEAN)) {
//															log(LogStatus.INFO,"Click on Finish Button2",YesNo.No);
//															ThreadSleep(5000);
//															} else {
//															sa.assertTrue(false, "Not Able to Click on Finish Button2");
//															log(LogStatus.FAIL,"Not Able to Click on Finish Button2",YesNo.Yes);
//														}

														if (click(driver, edit.getEditPageSaveButton(projectName, 30),"Save Button", action.BOOLEAN)) {
															log(LogStatus.INFO,"Click on Save Button",YesNo.No);
															ThreadSleep(5000);
															} else {
															sa.assertTrue(false, "Not Able to Click on Save Button");
															log(LogStatus.FAIL,"Not Able to Click on Save Button",YesNo.Yes);
														}
														
													
														
													} else {
														sa.assertTrue(false, "Not Able to Click on Save Button2");
														log(LogStatus.FAIL,"Not Able to Click on Save Button2",YesNo.Yes);
													}
													
												} else {
													sa.assertTrue(false, "Not Able to add page to App");
													log(LogStatus.FAIL,"Not Able to add page to App",YesNo.Yes);
												}
												
												
											} else {
												sa.assertTrue(false, "Not Able to Click on Activate Button");
												log(LogStatus.FAIL,"Not Able to Click on Activate Button",YesNo.Yes);
											}
											
										} else {
											sa.assertTrue(false, "Not Able to Click on Save Button");
											log(LogStatus.FAIL,"Not Able to Click on Save Button",YesNo.Yes);
										}
										
										
									}else {
										log(LogStatus.ERROR, "Not Able to Click on Finish button so cannot create Lightning App : "+myAppPage, YesNo.Yes);
										sa.assertTrue(false, "Not Able to Click on Finish button so cannot create Lightning App : "+myAppPage);

									}
									
								}else {
									log(LogStatus.ERROR, "Not Able to Click on next button so cannot create Lightning App : "+myAppPage, YesNo.Yes);
									sa.assertTrue(false, "Not Able to Click on next button so cannot create Lightning App : "+myAppPage);

								}
							} else {
								sa.assertTrue(false, "Not Able to send value to "+PageLabel.Label.toString()+" textbox value : "+myAppPage);
								log(LogStatus.FAIL,"Not Able to send value to "+PageLabel.Label.toString()+" textbox value : "+myAppPage,YesNo.Yes);
							}
							
						}else {
							log(LogStatus.ERROR, "Not Able to Click on next button so cannot create Lightning App : "+myAppPage, YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on next button so cannot create Lightning App : "+myAppPage);

						}
						
					}else {
						log(LogStatus.ERROR, "Not Able to Click on new button so cannot create Lightning App : "+myAppPage, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on new button so cannot create Lightning App : "+myAppPage);

					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Lightning_App_Builder, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Lightning_App_Builder);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot update Navigation Label", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot update Navigation Label");
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}
		
		///////////////////////////////////////////
		
		String redirectLink;
		String labelValue="";
		String actualUrl="";
		String[] navigationLabelWithParent=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_With_Parent).split(breakSP);

		for (int i = 0; i < navigationLabelWithParent.length; i++) {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				labelValue=navigationLabelWithParent[i];
				WebElement ele = npbl.getNavigationLabel(projectName, labelValue, action.BOOLEAN, 10);
				if (click(driver, ele, labelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+labelValue+" going to verify url", YesNo.No);
					ThreadSleep(5000);
//					String pareNTiD=switchOnWindow(driver);
//					if (pareNTiD!=null) {
						
						redirectLink="";
						actualUrl=getURL(driver, 60);
						if (redirectLink.equalsIgnoreCase(actualUrl) || actualUrl.equalsIgnoreCase(redirectLink)) {
							log(LogStatus.ERROR, "After clicking : "+labelValue+" redirected to "+redirectLink, YesNo.No);
						} else {
							log(LogStatus.ERROR, "After clicking : "+labelValue+" should be redirected to "+redirectLink, YesNo.Yes);
							sa.assertTrue(false,"After clicking : "+labelValue+" should be redirected to "+redirectLink);
							
						}
//						driver.close();
//						driver.switchTo().window(pareNTiD);
//					} else {
//						log(LogStatus.ERROR, "No new window is open for  "+labelValue+" so cannot verify url", YesNo.Yes);
//						sa.assertTrue(false,"No new window is open for  "+labelValue+" so cannot verify url");
//					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+labelValue+" so cannot verify url", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+labelValue+" so cannot verify url");

				}
				click(driver, npbl.getNavatarQuickLinkMinimize_Lighting(projectName, 3), "Minimize Icon", action.BOOLEAN);
				refresh(driver);
				ThreadSleep(5000);

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+labelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+labelValue);
			}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	
		/////////////////////////////////////////////;
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc022_CreateListViewAndVerifyTheNavigationForListView(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		WebElement ele;
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String listViewObject=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.List_View_Object);
		String listViewName=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.List_View_Name);
		String[][] navigationFieldWithValues= {{CSVLabel.List_View_Object.toString(),listViewObject},{CSVLabel.List_View_Name.toString(),listViewName}};
		String navLb;
		navLb=navigationLabel;

		if (lp.clickOnTab(projectName, navLb)) {	
			if (lp.addAutomationAllListView(projectName, navLb, 10)) {
				log(LogStatus.INFO,"list view added on "+navLb,YesNo.No);
			} else {
				log(LogStatus.FAIL,"list view could not added on "+navLb,YesNo.Yes);
				sa.assertTrue(false, "list view could not added on "+navLb);
			}
		} else {
			log(LogStatus.FAIL,"could not click on "+navLb,YesNo.Yes);
			sa.assertTrue(false, "could not click on "+navLb);
		}

		if (npbl.clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
			if (npbl.clickOnAlreadyCreatedItem(projectName, navLb, true, 15)) {
				log(LogStatus.INFO,"Item found: "+navLb+" on Tab : "+navigationTab, YesNo.No);

				npbl.clickOnShowMoreDropdownOnly(projectName);
				ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Edit, 10);

				if (click(driver, ele, ShowMoreActionDropDownList.Edit.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO, "Not Able to Click on Edit Button : "+navLb, YesNo.No);
					npbl.enteringValueForNavigation(projectName, navigationFieldWithValues, action.BOOLEAN, 20);
					if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.ERROR, "Click on save Button : "+navLb, YesNo.No);
						ThreadSleep(2000);
						

						if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewName
								, CSVLabel.Navigation_Label.toString(), navLb, CSVLabel.List_View_Name.toString())) {
							log(LogStatus.INFO, listViewName+" value has been written under "+CSVLabel.List_View_Name.toString()+" for "+navLb, YesNo.No);
						} else {
							log(LogStatus.ERROR, listViewName+" value has not been written under "+CSVLabel.List_View_Name.toString()+" for "+navLb, YesNo.Yes);
							sa.assertTrue(false, listViewName+" value has not been written under "+CSVLabel.List_View_Name.toString()+" for "+navLb);
						}
						

						if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewObject, CSVLabel.Navigation_Label.toString(), navLb, CSVLabel.List_View_Object.toString())) {
							log(LogStatus.INFO, listViewObject+" value has been written under "+CSVLabel.List_View_Object.toString()+" for "+navLb, YesNo.No);
						} else {
							log(LogStatus.ERROR, listViewObject+" value has not been written under "+CSVLabel.List_View_Object.toString()+" for "+navLb, YesNo.Yes);
							sa.assertTrue(false, listViewObject+" value has not been written under "+CSVLabel.List_View_Object.toString()+" for "+navLb);
						}
						
					} else {
						log(LogStatus.ERROR, "Not Able to Click on save Button : "+navLb, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on save Button : "+navLb);}


				} else {
					log(LogStatus.ERROR, "Not Able to Click on Edit Button : "+navLb, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on Edit Button : "+navLb);
				}

			}else {

				log(LogStatus.ERROR,"Item not found: "+navLb+" on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Item not found: "+navLb+" on Tab : "+navigationTab);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
		}



		String labelValue="";

		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			labelValue=navigationLabel;
			ele=npbl.getNavigationLabel(projectName, labelValue, action.BOOLEAN, 10);
			if (click(driver, ele, labelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+labelValue+" going to verify url", YesNo.No);
				ThreadSleep(5000);
				boolean flag=npbl.isAutomationAllListViewAdded(projectName, 30);
				if (flag) {
					log(LogStatus.INFO, "List View is available after clicking on "+labelValue , YesNo.No);
					
				} else {
					log(LogStatus.ERROR, "No List View is available is open after clicking on "+labelValue, YesNo.Yes);
					sa.assertTrue(false, "No List View is available is open after clicking on "+labelValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+labelValue+" so cannot verify url", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+labelValue+" so cannot verify url");

			}
			click(driver, npbl.getNavatarQuickLinkMinimize_Lighting(projectName, 3), "Minimize Icon", action.BOOLEAN);
			refresh(driver);
			ThreadSleep(5000);

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+labelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+labelValue);
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc024_VerifyQuickCreateObjectWhenRecordHasMultipleRecordTypes(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		WebElement ele;
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		

		String labelValue="";

		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			labelValue=navigationLabel;
			ele=npbl.getNavigationLabel(projectName, labelValue, action.BOOLEAN, 10);
			if (click(driver, ele, labelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+labelValue+" going to verify record type options", YesNo.No);
				ThreadSleep(5000);
				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, "Pop Up open after clicking on "+labelValue , YesNo.No);
					for (int i = 0; i < recordTypeArray.length; i++) {
						ele=npbl.getRadioButtonforRecordTypeAtAccount(recordTypeArray[i], 5);
						if (ele!=null) {
							log(LogStatus.ERROR, recordTypeArray[i]+" radio button is present", YesNo.No);
							if (i==0) {
								if (isSelected(driver, ele, recordTypeArray[i]+" radio button")) {
									log(LogStatus.INFO, recordTypeArray[i]+" radio button is selected by default", YesNo.No);	
								} else {
									log(LogStatus.ERROR, recordTypeArray[i]+" radio button is not selected by default", YesNo.Yes);
									sa.assertTrue(false, recordTypeArray[i]+" radio button is not selected by default");
								}
							}
						} else {
							log(LogStatus.ERROR, recordTypeArray[i]+" radio button not present", YesNo.Yes);
							sa.assertTrue(false, recordTypeArray[i]+" radio button not present");
						}

					}
					if (click(driver, ele, labelValue+" pop up cross button", action.BOOLEAN)) {
						log(LogStatus.INFO, "click on cross button "+labelValue , YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not Able to click on cross button "+labelValue, YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on cross button "+labelValue);
					}
				} else {
					log(LogStatus.ERROR, "No Pop Up is open after clicking on "+labelValue, YesNo.Yes);
					sa.assertTrue(false, "No Pop Up is open after clicking on "+labelValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+labelValue+" so cannot verify record type options", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+labelValue+" so cannot verify record type options");

			}
			click(driver, npbl.getNavatarQuickLinkMinimize_Lighting(projectName, 3), "Minimize Icon", action.BOOLEAN);
			refresh(driver);
			ThreadSleep(5000);

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify record type options for label : "+labelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify record type options for label : "+labelValue);
		}

		String[] userNames= {"PE Standard User","System Administrator"};
		String recordType="Company";
		for (String userName : userNames) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to update Navigation Label", YesNo.No);
					ThreadSleep(500);

					if(setup.searchStandardOrCustomObject(environment,mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : "+object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.changeRecordTypeSetting(driver, userName, recordType, 60)) {
							log(LogStatus.PASS,recordType+" is selected for "+userName,YesNo.No);
						} else {
							sa.assertTrue(false, recordType+" is not selected for "+userName);
							log(LogStatus.FAIL,recordType+" is not selected for "+userName,YesNo.Yes);
						}


					}else {
						log(LogStatus.ERROR, "Not able to search/click on "+object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on "+object.Profiles);
					}

					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "could not find new window to switch, so cannot update Navigation Label", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, so cannot update Navigation Label");
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");	
			}
		}

		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			labelValue=navigationLabel;
			ele=npbl.getNavigationLabel(projectName, labelValue, action.BOOLEAN, 10);
			if (click(driver, ele, labelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+labelValue+" going to verify record type options", YesNo.No);
				ThreadSleep(5000);
				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, "Pop Up open after clicking on "+labelValue , YesNo.No);
					ele=npbl.getRadioButtonforRecordTypeAtAccount(recordType, 5);
					if (ele!=null) {
						log(LogStatus.ERROR, recordType+" radio button is present", YesNo.No);

						if (isSelected(driver, ele, recordType+" radio button")) {
							log(LogStatus.INFO, recordType+" radio button is selected by default", YesNo.No);	
						} else {
							log(LogStatus.ERROR, recordType+" radio button is not selected by default", YesNo.Yes);
							sa.assertTrue(false, recordType+" radio button is not selected by default");
						}

					} else {
						log(LogStatus.ERROR, recordType+" radio button not present", YesNo.Yes);
						sa.assertTrue(false, recordType+" radio button not present");
					}



				} else {
					log(LogStatus.ERROR, "No Pop Up is open after clicking on "+labelValue, YesNo.Yes);
					sa.assertTrue(false, "No Pop Up is open after clicking on "+labelValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+labelValue+" so cannot verify record type options", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+labelValue+" so cannot verify record type options");

			}
			click(driver, npbl.getNavatarQuickLinkMinimize_Lighting(projectName, 3), "Minimize Icon", action.BOOLEAN);
			refresh(driver);
			ThreadSleep(5000);

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify record type options for label : "+labelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify record type options for label : "+labelValue);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc025_FillTheRecordTypeIDAndVerifyTheImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		WebElement ele;
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String recordTypeName="";
		/////////////////////////////////////////////////////
		boolean flag=false;
		for (int i = 0; i < recordTypeArray.length; i++) {
			if (home.clickOnSetUpLink()) {
				flag=false;
				String parentID = switchOnWindow(driver);
				SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
				if (parentID!=null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Entity )) {
						if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Entity, ObjectFeatureName.recordTypes)) {
							ThreadSleep(5000);
							if (sp.clickOnAlreadyCreatedLayout(recordTypeArray[i])) {
								ThreadSleep(5000);
								switchToFrame(driver, 20, sp.getSetUpPageIframe(60));
								if (click(driver, sp.getEditButton(environment,"Classic",10), "edit", action.SCROLLANDBOOLEAN)) {
									ThreadSleep(5000);
									switchToDefaultContent(driver);
									switchToFrame(driver, 20, sp.getSetUpPageIframe(60));
									ele=sp.getRecordTypeLabel(projectName, recordTypeLabel.Active.toString(), 20);
									if (ele!=null) {
										log(LogStatus.INFO,recordTypeArray+" ele found on edit page",YesNo.No);
										if (i==0) {
											if (!isSelected(driver, ele, recordTypeArray[i]+" "+recordTypeLabel.Active)) {
												if (click(driver, ele, recordTypeArray[i]+" "+recordTypeLabel.Active, action.BOOLEAN)) {
													log(LogStatus.INFO,recordTypeArray[i]+" "+recordTypeLabel.Active+" is clicked",YesNo.No);
													ThreadSleep(2000);
													if (click(driver, sp.getCreateUserSaveBtn_Lighting(30), "Save Button",action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO, "clicked on save button", YesNo.No);
														flag=true;
													} else {
														log(LogStatus.ERROR, "not able to click on save button", YesNo.Yes);
														sa.assertTrue(false, "not able to click on save button");
													}
													ThreadSleep(5000);
												} else {
													log(LogStatus.ERROR,recordTypeArray[i]+" "+recordTypeLabel.Active+" is not clickable",YesNo.Yes);
													sa.assertTrue(false, recordTypeArray[i]+" "+recordTypeLabel.Active+" is not clickable");
												}
											} 
										} else {
											ele=sp.getRecordTypeLabel(projectName, recordTypeLabel.Record_Type_Name.toString(), 20);
											recordTypeName=ele.getAttribute("value");
											System.err.println("recordTypeName "+recordTypeName);
											flag=true;
										}


									} else {
										log(LogStatus.ERROR,recordTypeArray+" ele not found on edit page",YesNo.Yes);
										sa.assertTrue(false, recordTypeArray+" ele not found on edit page");
									}

								}else {
									log(LogStatus.ERROR,"edit button is not clickable",YesNo.Yes);
									sa.assertTrue(false, "edit button is not clickable");
								}


							}else {
								log(LogStatus.ERROR, recordTypeArray[i]+" is not clickable", YesNo.Yes);
								sa.assertTrue(false, recordTypeArray[i]+" is not clickable");
							}
					
						}else {
							log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
						}
					}else {
						log(LogStatus.ERROR, "Entity object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, "Entity object could not be found in object manager");
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

			String labelValue;
			String recordType=recordTypeArray[i];
			labelValue=navigationLabel;

			//////////////////////////////////////////
			if (i!=0) {

				if (npbl.clickOnTab(projectName, navigationTab)) {
					log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
					if (npbl.clickOnAlreadyCreatedItem(projectName, labelValue, true, 15)) {
						log(LogStatus.INFO,"Item found: "+labelValue+" on Tab : "+navigationTab, YesNo.No);

						npbl.clickOnShowMoreDropdownOnly(projectName);
						ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Edit, 10);

						if (click(driver, ele, ShowMoreActionDropDownList.Edit.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Not Able to Click on Edit Button : "+labelValue, YesNo.No);
							ele = npbl.getNavigationField(projectName, CSVLabel.Action_Record_Type.toString(), action.BOOLEAN, 20);

							try {
								ele.clear();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ThreadSleep(2000);
							if (sendKeys(driver, ele, recordTypeName, CSVLabel.Order.toString(), action.BOOLEAN)) {
								log(LogStatus.INFO, "Able to enter "+CSVLabel.Order.toString(), YesNo.No);
								ThreadSleep(2000);

								if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "Click on save Button : "+labelValue, YesNo.No);
									ThreadSleep(2000);

									if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, recordTypeName, CSVLabel.Navigation_Label.toString(), labelValue, CSVLabel.Action_Record_Type.toString())) {
										log(LogStatus.INFO, recordTypeName+" value has been written under "+CSVLabel.Action_Record_Type.toString()+" for "+labelValue, YesNo.No);
									} else {
										log(LogStatus.ERROR, recordTypeName+" value has not been written under "+CSVLabel.Action_Record_Type.toString()+" for "+labelValue, YesNo.Yes);
										sa.assertTrue(false, recordTypeName+" value has not been written under "+CSVLabel.Action_Record_Type.toString()+" for "+labelValue);
									}

								} else {
									log(LogStatus.ERROR, "Not Able to Click on save Button : "+labelValue, YesNo.Yes);
									sa.assertTrue(false,"Not Able to Click on save Button : "+labelValue);}

							} else {
								log(LogStatus.ERROR, "Not Able to enter "+CSVLabel.Order.toString(), YesNo.Yes);
								sa.assertTrue(false,"Not Able to enter "+CSVLabel.Order.toString());
							}
						} else {
							log(LogStatus.ERROR, "Not Able to Click on Edit Button : "+labelValue, YesNo.Yes);
							sa.assertTrue(false,"Not Able to Click on Edit Button : "+labelValue);
						}

					}else {

						log(LogStatus.ERROR,"Item not found: "+labelValue+" on Tab : "+navigationTab, YesNo.Yes);
						sa.assertTrue(false,"Item not found: "+labelValue+" on Tab : "+navigationTab);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
				}

			} 


			////////////////////////////////////


			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, labelValue, action.BOOLEAN, 10);
				if (click(driver, ele, labelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+labelValue+" going to verify record type options", YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, "Pop Up open after clicking on "+labelValue , YesNo.No);

					} else {
						log(LogStatus.ERROR, "No Pop Up is open after clicking on "+labelValue, YesNo.Yes);
						sa.assertTrue(false, "No Pop Up is open after clicking on "+labelValue);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+labelValue+" so cannot verify record type options", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+labelValue+" so cannot verify record type options");

				}

				if (i==0) {
					ele=npbl.getRadioButtonforRecordTypeAtAccount(recordType, 5);
					if (ele==null) {
						log(LogStatus.ERROR, recordType+" radio button is not available after Inactive it", YesNo.No);
					} else {
						log(LogStatus.ERROR, recordType+" radio button should not present after inactive it", YesNo.Yes);
						sa.assertTrue(false, recordType+" radio button should not present after inactive it");
					}

				} else {

					ThreadSleep(5000);
					ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, recordType, action.BOOLEAN, 5);
					if (ele!=null) {
						log(LogStatus.INFO, recordType+" Pop Up open after clicking on "+labelValue , YesNo.No);

					} else {
						log(LogStatus.ERROR, recordType+" Pop Up should be open after clicking on "+labelValue, YesNo.Yes);
						sa.assertTrue(false, recordType+" Pop Up should be open after clicking on "+labelValue);
					}

				}

				click(driver, ele, "Cross Icon", action.BOOLEAN);
				refresh(driver);
				ThreadSleep(5000);

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify record type options for label : "+labelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify record type options for label : "+labelValue);
			}
			System.err.println("><><><><>");
			scn.nextLine();
		}


		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc027_RemoveTheRecordTypeAPIFieldAndVerifyTheImpactOnNavigationMenu(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[][] labelWithValue= {{CSVLabel.Action_Record_Type.toString(),""}};
		
		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, "Blank value has been updated & saved under "+CSVLabel.Action_Record_Type.toString()+" for "+navigationLabel, YesNo.No);
			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, "", CSVLabel.Navigation_Label.toString(), navigationLabel, CSVLabel.Action_Record_Type.toString())) {
				log(LogStatus.INFO, "Blank value has been written under "+CSVLabel.Action_Record_Type.toString()+" for "+navigationLabel, YesNo.No);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify record type options", YesNo.No);
						ThreadSleep(5000);
						ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
						if (ele!=null) {
							log(LogStatus.INFO, "Pop Up open after clicking on "+navigationLabel , YesNo.No);
							String recordType="Company";
							ele=npbl.getRadioButtonforRecordTypeAtAccount(recordType, 5);
							if (ele!=null) {
								log(LogStatus.INFO, recordType+" radio button is available after removing value from"+CSVLabel.Action_Record_Type.toString(), YesNo.No);
							} else {
								log(LogStatus.ERROR, recordType+" radio button should be present after removing value from"+CSVLabel.Action_Record_Type.toString(), YesNo.Yes);
								sa.assertTrue(false, recordType+" radio button should be present after removing value from"+CSVLabel.Action_Record_Type.toString());
							}
							ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
							click(driver, ele, "Navigation menu PoPuP Cross Icon", action.BOOLEAN);
						} else {
							log(LogStatus.ERROR, "No Pop Up is open after clicking on "+navigationLabel, YesNo.Yes);
							sa.assertTrue(false, "No Pop Up is open after clicking on "+navigationLabel);
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify record type options", YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify record type options");

					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify record Type for label : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify record Type for label : "+navigationLabel);
				}

			} else {
				log(LogStatus.ERROR, "Blank value has not been written under "+CSVLabel.Action_Record_Type.toString()+" for "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false, "Blank value has not been written under "+CSVLabel.Action_Record_Type.toString()+" for "+navigationLabel);
			}
			
			
		} else {
			log(LogStatus.ERROR, "Not Able to Remove the Record Type API field for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "Not Able to Remove the Record Type API field for "+navigationLabel);
	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc028_FillSomeThirdPartyUrlInUrlFieldAndVerifyTheImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String urlLabel=CSVLabel.URL.toString();
		String urlValue="https://www.google.com/";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[][] labelWithValue= {{urlLabel,urlValue}};

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, urlValue+" value has been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.No);
			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, urlValue, CSVLabel.Navigation_Label.toString(), navigationLabel, urlLabel)) {
				log(LogStatus.INFO, urlValue+" value has been written under "+urlLabel+" for "+navigationLabel, YesNo.No);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify url", YesNo.No);
						ThreadSleep(5000);
						String actualUrl=getURL(driver, 10);
						if (actualUrl.contains(urlValue)) {
							log(LogStatus.INFO, urlValue+" : Url Verified for : "+navigationLabel, YesNo.No);
						} else {
							log(LogStatus.ERROR, "Url Not Verified for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue, YesNo.Yes);
							sa.assertTrue(false,"Url Not Verified for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue);
							refresh(driver);
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify URL for label : "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify URL for label : "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify URL for label : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify URL for label : "+navigationLabel);

				}
			} else {
				log(LogStatus.ERROR, urlValue+" has not been written under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false, urlValue+" has not been written under "+urlLabel+" for "+navigationLabel);

			}
		}else{

			log(LogStatus.ERROR, "Not Able to enter url value field for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "Not Able to enter url value field for "+navigationLabel);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc029_CreateAlistViewOfAccountsAndListViewInformationOnNavigationPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String listViewNameLabel=CSVLabel.List_View_Name.toString();
		String listViewLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.List_View_Name);
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[][] labelWithValue= {{listViewNameLabel,listViewLabelValue}};

		String[] tabs= {tabObj1};
		TabName[] tab= {TabName.Object1Tab};
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
		
		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, listViewLabelValue+" value has been updated & saved under "+listViewNameLabel+" for "+navigationLabel, YesNo.No);
			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewLabelValue, CSVLabel.Navigation_Label.toString(), navigationLabel, listViewNameLabel)) {
				log(LogStatus.INFO, listViewLabelValue+" value has been written under "+listViewNameLabel+" for "+navigationLabel, YesNo.No);
				
				listViewLabelValue=googleUrlValue;
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify url", YesNo.No);
						ThreadSleep(5000);
						String parentID = switchOnWindow(driver);
						if (parentID!=null) {
							
							String actualUrl=getURL(driver, 10);
							if (actualUrl.contains(listViewLabelValue)) {
								log(LogStatus.INFO, listViewLabelValue+" : Url is opened & Verified after adding URL/ Quick Create Object/ List View for : "+navigationLabel, YesNo.No);
							} else {
								log(LogStatus.ERROR, "After adding URL/ Quick Create Object/ List View, Url should be open for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+listViewLabelValue, YesNo.Yes);
								sa.assertTrue(false,"After adding URL/ Quick Create Object/ List View, Url should be open for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+listViewLabelValue);
						
							}
							
							driver.close();
							driver.switchTo().window(parentID);
						} else {
							log(LogStatus.ERROR, "No New window is open after click on : "+navigationLabel+" so cannot check url after adding URL/ Quick Create Object/ List View", YesNo.Yes);
							sa.assertTrue(false,"No New window is open after click on : "+navigationLabel+" so cannot check url after adding URL/ Quick Create Object/ List View");
					
						}
						
					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify "+listViewLabelValue+" after adding URL/ Quick Create Object/ List View for label : "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify "+listViewLabelValue+" after adding URL/ Quick Create Object/ List View for label : "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify "+listViewLabelValue+" after adding URL/ Quick Create Object/ List View for label : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify "+listViewLabelValue+" after adding URL/ Quick Create Object/ List View for label : "+navigationLabel);

				}
			} else {
				log(LogStatus.ERROR, listViewLabelValue+" has not been written under "+listViewNameLabel+" for "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false, listViewLabelValue+" has not been written under "+listViewNameLabel+" for "+navigationLabel);

			}
		}else{

			log(LogStatus.ERROR, "Not Able to enter value on" +listViewNameLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "Not Able to enter value on" +listViewNameLabel+" for "+navigationLabel);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc030_RemoveTheURLAndVerifyTheImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String urlLabel=CSVLabel.URL.toString();
		String urlLabelValue="";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[][] labelWithValue= {{urlLabel,urlLabelValue}};
		
		// Now Accounts creation pop-up should appear. As Quick Create gets preference over List View if both the fields are filled.
		
		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, urlLabelValue+" value has been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.No);
			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, urlLabelValue, CSVLabel.Navigation_Label.toString(), navigationLabel, urlLabel)) {
				log(LogStatus.INFO, urlLabelValue+" value has been written under "+urlLabel+" for "+navigationLabel, YesNo.No);
				
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
						ThreadSleep(5000);
						ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
						if (ele!=null) {
							log(LogStatus.INFO, "Pop Up is open after after removing urlValue for "+navigationLabel , YesNo.No);
							String recordType="Company";
							ele=npbl.getRadioButtonforRecordTypeAtAccount(recordType, 5);
							if (ele!=null) {
								log(LogStatus.INFO, recordType+" radio button is available after removing value from"+urlLabel, YesNo.No);
							} else {
								log(LogStatus.ERROR, recordType+" radio button should be present after removing value from"+urlLabel, YesNo.Yes);
								sa.assertTrue(false, recordType+" radio button should be present after removing value from"+urlLabel);
							}
							ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
							click(driver, ele, "Navigation menu PoPuP Cross Icon", action.BOOLEAN);
						} else {
							log(LogStatus.ERROR, "Pop Up Should be open after after removing urlValue for "+navigationLabel, YesNo.Yes);
							sa.assertTrue(false, "Pop Up Should be open after after removing urlValue for "+navigationLabel);
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

				}
			} else {
				log(LogStatus.ERROR, urlLabelValue+" has not been written under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false, urlLabelValue+" has not been written under "+urlLabel+" for "+navigationLabel);

			}
		}else{

			log(LogStatus.ERROR, "Not Able to enter value on" +urlLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "Not Able to enter value on" +urlLabel+" for "+navigationLabel);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc031_RemoveQuickCreateObjectFieldAndVerifyTheImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String actionObjectLabel=CSVLabel.Action_Object.toString();
		String actionObjectValue="";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[][] labelWithValue= {{actionObjectLabel,actionObjectValue}};

		// Now list view of Account should get open. As both URL and Quick Create Object field is blank.

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, actionObjectValue+" value has been updated & saved under "+actionObjectLabel+" for "+navigationLabel, YesNo.No);
			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, actionObjectValue, CSVLabel.Navigation_Label.toString(), navigationLabel, actionObjectLabel)) {
				log(LogStatus.INFO, actionObjectValue+" value has been written under "+actionObjectLabel+" for "+navigationLabel, YesNo.No);

				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
						ThreadSleep(5000);
						boolean flag=npbl.isAutomationAllListViewAdded(projectName, 30);
						if (flag) {
							log(LogStatus.INFO, "List View is available after clicking on "+navigationLabel , YesNo.No);

						} else {
							log(LogStatus.ERROR, "No List View is available is open after clicking on "+navigationLabel, YesNo.Yes);
							sa.assertTrue(false, "No List View is available is open after clicking on "+navigationLabel);
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

				}
			} else {
				log(LogStatus.ERROR, actionObjectValue+" has not been written under "+actionObjectLabel+" for "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false, actionObjectValue+" has not been written under "+actionObjectLabel+" for "+navigationLabel);

			}
		}else{

			log(LogStatus.ERROR, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
}
	
	