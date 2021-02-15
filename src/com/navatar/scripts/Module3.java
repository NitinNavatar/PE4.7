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
		//	lp.CRMLogin(superAdminUserName, adminPassword);
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
}
	
	