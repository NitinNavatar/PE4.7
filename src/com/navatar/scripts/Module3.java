package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.ExcelUtils.readAllDataForAColumn;
import static com.navatar.generic.SmokeCommonVariables.Smoke_EntityMeeting1Priority;
import static com.navatar.generic.SmokeCommonVariables.Smoke_EntityMeeting1Type;
import static com.navatar.generic.SmokeCommonVariables.Smoke_STDTask2OnComment;
import static com.navatar.generic.SmokeCommonVariables.Smoke_STDTask2OnSubject;
import static com.navatar.generic.SmokeCommonVariables.Smoke_Task2MultipleComment;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskFund1Name;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskINS3Name;
import static com.navatar.generic.SmokeCommonVariables.tabCustomObjField;
import static com.navatar.generic.SmokeCommonVariables.taskCustomObj1Name;

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
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
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
import com.navatar.pageObjects.CoveragePageBusinessLayer;
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
	
	//////////////Condition Start/////////////////////
	
	/////////////////Condition End//////////////////
	String passwordResetLink = null;
//	Scanner scn = new Scanner(System.in);

	String navatarEdge="EDGE";
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
	String upDated="Updated";
	String customMenu="Custom Menu";
	String allListView="All";
	String automationAll="Automation All";
	String recordTypeDescription = " Description Record Type";
	String homeTab="Home";
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc001_1_createCRMUser(String projectName) {
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
	public void Module3Tc001_2_AddTabAndListView(String projectName) {
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
			addRemoveTabName=tab1+"s,"+navigationTab+"s,"+tabObj3+"s,"+tabObj2+"s,"+tabObj8Coverage+"s";
			if (lp.addTab_Lighting( addRemoveTabName, 5)) {
				log(LogStatus.INFO,"Tab added : "+addRemoveTabName,YesNo.No);
			} else {
				log(LogStatus.FAIL,"Tab not added : "+addRemoveTabName,YesNo.No);
				sa.assertTrue(false, "Tab not added : "+addRemoveTabName);
			}		

			
			String[] tabs= {tabObj1,navigationTab,tabObj3,tabObj2,tabObj8Coverage};
			//TabName[] tab= {TabName.Object1Tab,TabName.Object5Tab,TabName.Object3Tab,TabName.Object4Tab};
			int i=0;
			for (String t:tabs) {
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
	public void Module3Tc002_UploadCsvToCreateTheNavigationData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		DataLoaderWizardPageBusinessLayer dataload = new DataLoaderWizardPageBusinessLayer(driver);
		mode=Mode.Classic.toString();
		Boolean flag = false;
		lp.CRMLogin(superAdminUserName, adminPassword);
		lp.switchToClassic();
		if(dataload.dataImportWizard(ObjectName.Navigation, ObjectType.Custom, "\\UploadFiles\\Module 3\\UploadCSV\\NavigationMenuTestData_PE - Parent.csv", DataImportType.AddNewRecords, "12")) {
			appLog.info("Parent Data is imported Successfully in "+ObjectName.Navigation);
			flag=true;
			}else {
			appLog.error("Parent Data is not imported in "+ObjectName.Navigation+" so child data cannot imported");
			sa.assertTrue(false, "Parent Data is not imported in "+ObjectName.Navigation+" so child data cannot imported");
		}
		if(flag) {

			if(dataload.dataImportWizard(ObjectName.Navigation, ObjectType.Custom, "\\UploadFiles\\Module 3\\UploadCSV\\NavigationMenuTestData_PE - Child.csv", DataImportType.AddNewRecords, "9")) {
				appLog.info("Data is imported Successfully in "+ObjectName.Navigation);
				flag=true;

			}else {
				appLog.error("Data is not imported in "+ObjectName.Navigation);
				sa.assertTrue(false, "Data is not imported in "+ObjectName.Navigation);
			}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void Module3Tc003_VerifyTheDefaultMenuLabelAndUpdateTheLabel(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge+" so menu name appear as "+navatarEdge, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so menu name should appear as "+navatarEdge, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so menu name should appear as "+navatarEdge);
		}

		switchToDefaultContent(driver);
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to update Navigation Label", YesNo.No);
				ThreadSleep(500);

				if(setup.searchStandardOrCustomObject(environment,mode, object.App_Manager)) {
					log(LogStatus.INFO, "click on Object : "+object.App_Manager, YesNo.No);
					ThreadSleep(2000);
					//						setup.clickOnEditForApp(driver, appName, AppDeveloperName,"Navatar Private Equity app – Lightning View(Edge)", 10);
					//						// scn.nextLine();
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
		switchToDefaultContent(driver);
		refresh(driver);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName+" so menu name appear as "+navigationMenuName, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so menu name should appear as "+navigationMenuName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so menu name should appear as "+navigationMenuName);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void Module3Tc004_OpentheNavigationMenuAndVerifyTheMenuItems(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
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
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		List<String>  parentQuickActionObjectWithNoChild = new LinkedList<String>();
		System.err.println(NavigationMenuTestData_PEExcel+" >>>>>>>>>>>>><<<<<<<<<<<< "+NavigationMenuTestData_PESheet);

		if (!csvRecords.isEmpty()) {
			log(LogStatus.INFO, "Records Fetched from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.No);
			// Verify the items for which Quick Create Object field was fill
			String labelValue="";
			String quickActionObject="";
			WebElement ele;
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
			System.err.println("parentChildForQuickActionObject : "+parentChildForQuickActionObject);
			if (!parentChildForQuickActionObject.isEmpty()) {

				for (String pr : parentChildForQuickActionObject.keySet()) {
					childs=parentChildForQuickActionObject.get(pr).split(breakSP);
					for (int i = 0; i < childs.length; i++) {
						
						if (pr.isEmpty()) {
							log(LogStatus.INFO, "No Parent For "+childs[i], YesNo.No);
							parentQuickActionObjectWithNoChild.add(childs[i]);
						} else {
							
							if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
								log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
								
							} else {
								log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
								sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
							}
							parentQuickActionObjectWithNoChild.remove(pr);
							ele=npbl.getNavigationLabel(projectName, pr, action.BOOLEAN, 5);
							click(driver, ele, pr, action.BOOLEAN);
							ele=npbl.getNavigationLabel(projectName, childs[i], action.BOOLEAN, 5);
							if (click(driver, ele, childs[i], action.BOOLEAN)) {
								log(LogStatus.INFO, "click on "+childs[i], YesNo.No);
								ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, childs[i], action.BOOLEAN, 30);
								if (ele!=null) {
									log(LogStatus.INFO, "Pop Up open after clicking on "+childs[i] , YesNo.No);
									if (click(driver, ele, childs[i]+" pop up cross button", action.BOOLEAN)) {
										log(LogStatus.INFO, "click on cross button "+childs[i] , YesNo.No);
										ThreadSleep(5000);
									} else {
										log(LogStatus.ERROR, "Not Able to click on cross button "+childs[i], YesNo.Yes);
										sa.assertTrue(false, "Not Able to click on cross button "+childs[i]);
									}
								} else {
									ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
									click(driver, ele, " pop up cross button", action.BOOLEAN);
									ThreadSleep(5000);
									log(LogStatus.ERROR, "No Pop Up is open after clicking on "+childs[i], YesNo.Yes);
									sa.assertTrue(false, "No Pop Up is open after clicking on "+childs[i]);
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on "+childs[i], YesNo.Yes);
								sa.assertTrue(false, "Not able to click on "+childs[i]);
							}
							
						}
						refresh(driver);
					}
					refresh(driver);
				}
				Map<String, String> navigationParentLabelWithChildAndOrder = navigationParentLabelWithChildAndOrder(csvRecords);
				System.err.println("parentQuickActionObjectWithNoChild : "+parentQuickActionObjectWithNoChild);
				for (String string : navigationParentLabelWithChildAndOrder.keySet()) {
					parentQuickActionObjectWithNoChild.remove(string);
				}
			//	parentQuickActionObjectWithNoChild.remove("Contact");
				System.err.println("parentQuickActionObjectWithNoChild : "+parentQuickActionObjectWithNoChild);
				// scn.nextLine();
				for (String childs1 : parentQuickActionObjectWithNoChild) {
					
					if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
						log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
						

						ele=npbl.getNavigationLabel(projectName, childs1, action.BOOLEAN, 5);
						if (click(driver, ele, childs1, action.BOOLEAN)) {
							log(LogStatus.INFO, "click on "+childs1, YesNo.No);
							ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, childs1, action.BOOLEAN, 30);
							if (ele!=null) {
								log(LogStatus.INFO, "Pop Up open after clicking on "+childs1 , YesNo.No);
								if (click(driver, ele, childs1+" pop up cross button", action.BOOLEAN)) {
									log(LogStatus.INFO, "click on cross button "+childs1 , YesNo.No);
									ThreadSleep(5000);
								} else {
									log(LogStatus.ERROR, "Not Able to click on cross button "+childs1, YesNo.Yes);
									sa.assertTrue(false, "Not Able to click on cross button "+childs1);
								}
							} else {
								log(LogStatus.ERROR, "No Pop Up is open after clicking on "+childs1, YesNo.Yes);
								sa.assertTrue(false, "No Pop Up is open after clicking on "+childs1);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on "+childs1, YesNo.Yes);
							sa.assertTrue(false, "Not able to click on "+childs1);
						}
						refresh(driver);
					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
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
		System.err.println("parentQuickActionObjectWithNoChild : "+parentQuickActionObjectWithNoChild);
		System.err.println("parentQuickActionObjectWithNoChild : "+parentQuickActionObjectWithNoChild);
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
		lp.CRMLogin(crmUser1EmailID, adminPassword);
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
			String parentLabel="";
			System.err.println("parentChildForListViewObject : "+parentChildForListViewObject);
			if (!parentChildForListViewObject.isEmpty()) {

				for (String pr : parentChildForListViewObject.keySet()) {
					childs=parentChildForListViewObject.get(pr).split(breakSP);
					for (int i = 0; i < childs.length; i++) {
						if (pr.isEmpty()) {
							parentLabel=childs[i];
						} else {
							parentLabel=pr+"/"+childs[i];
						}
						if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
							log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
							sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
						}

						ele=npbl.getNavigationLabel(projectName, parentLabel, action.BOOLEAN, 5);
						if (click(driver, ele, childs[i], action.BOOLEAN)) {
							log(LogStatus.INFO, "Not able to click on "+childs[i], YesNo.No);
							flag=npbl.isAutomationAllListViewForObject(projectName,childs[i],allListView, 30);;
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
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String actualUrl="";
		String[] navigationLabels=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name).split(breakSP);
		String navigationLabel;
		String expectedUrl=getURL(driver, 20);
		for (int j = 0; j < navigationLabels.length; j++) {
			navigationLabel=navigationLabels[j];
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				WebElement ele = npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 5);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "click on "+navigationLabel, YesNo.No);
					ThreadSleep(5000);
					actualUrl=getURL(driver, 10);
					if (expectedUrl.contains(actualUrl)) {
						log(LogStatus.INFO, "Nothing Happen after click on "+navigationLabel, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Nothing Should be happen after click on "+navigationLabel+"as url changed Actual : "+actualUrl+"\t Expected : "+expectedUrl, YesNo.Yes);
						sa.assertTrue(false,"Nothing Should be happen after click on "+navigationLabel+"as url changed Actual : "+actualUrl+"\t Expected : "+expectedUrl);

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on "+navigationLabel ,YesNo.Yes);
					sa.assertTrue(false, "Not able to click on "+navigationLabel);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
			refresh(driver);

		}
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
					
//					String actualUrl = getURL(driver, 10);
//					String urlValue=ExcelUtils.readDataFromCSVFile(NavigationMenuTestData_PEExcel, CSVLabel.Navigation_Label.toString(), labelValue, CSVLabel.URL.toString());
//					System.err.println(">>> "+urlValue);
//					if (actualUrl.contains(urlValue)) {
//						log(LogStatus.INFO, urlValue+" : Url Verified for : "+labelValue, YesNo.No);
//					} else {
//						log(LogStatus.ERROR, "Url Not Verified for : "+labelValue+" Actual : "+actualUrl+"\t Expected : "+urlValue, YesNo.Yes);
//						sa.assertTrue(false,"Url Not Verified for : "+labelValue+" Actual : "+actualUrl+"\t Expected : "+urlValue);
//						refresh(driver);
//					}
					
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
		lp.CRMLogin(crmUser1EmailID, adminPassword);
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
		lp.CRMLogin(crmUser1EmailID, adminPassword);
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
										String customLabel1="Custom Label 1";
										if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, updatedName, CSVLabel.Navigation_Label.toString(), customLabel1, CSVLabel.Parent.toString())) {
											log(LogStatus.INFO, updatedName+" value has been written under "+CSVLabel.Parent.toString()+" for "+customLabel1, YesNo.No);
										} else {
											log(LogStatus.ERROR, updatedName+" value has not been written under "+CSVLabel.Parent.toString()+" for "+customLabel1, YesNo.Yes);
											sa.assertTrue(false, updatedName+" value has not been written under "+CSVLabel.Parent.toString()+" for "+customLabel1);
										}

										
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
		lp.CRMLogin(crmUser1EmailID, adminPassword);
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
				npbl.verifyingNavigationMenuLink(projectName, navigationParentLabelWithSortedOrder, navigationParentLabelWithChildSorted, action.BOOLEAN, 1);;
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
					labelsValues[j]=record[j].trim().replace("\"", "");
					System.err.println("labelsValues[j] : "+labelsValues[j]);
				}
				

				String navigationField1;
				String navigationValue;

				if (npbl.clickOnTab(projectName, navigationTab)) {
					log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
					if(clickUsingJavaScript(driver, lp.getNewButton(projectName, 10), "new button")) {
						log(LogStatus.INFO, "Click on new button going to create: "+navigationLabel, YesNo.No);
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
						log(LogStatus.ERROR, "Not Able to Click on new button so cannot create : "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on new button so cannot create : "+navigationLabel);

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
						// scn.nextLine();
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
		lp.CRMLogin(crmUser1EmailID, adminPassword);
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

								refresh(driver);
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
		lp.CRMLogin(crmUser1EmailID, adminPassword);
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
						refresh(driver);
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
								eleList = npbl.getExpandIcon(projectName, 10);
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
		String[] thirdPartyURLS= {"https://www.hubspot.com/","https://www.preqin.com/"};
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
							log(LogStatus.ERROR, "After clicking : "+labelValue+" should be redirected to "+redirectLink+" \t Actual : "+actualUrl, YesNo.Yes);
							sa.assertTrue(false,"After clicking : "+labelValue+" should be redirected to "+redirectLink+" \t Actual : "+actualUrl);
							
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
		refresh(driver);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			labelValue=navigationLabel;
			ele=npbl.getNavigationLabel(projectName, labelValue, action.BOOLEAN, 10);
			if (click(driver, ele, labelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+labelValue+" going to verify list view", YesNo.No);
				ThreadSleep(5000);
				boolean flag=npbl.isAutomationAllListViewForObject(projectName,labelValue, 30);
				if (flag) {
					log(LogStatus.INFO, "List View is available after clicking on "+labelValue , YesNo.No);
					
				} else {
					log(LogStatus.ERROR, "No List View is available is open after clicking on "+labelValue, YesNo.Yes);
					sa.assertTrue(false, "No List View is available is open after clicking on "+labelValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+labelValue+" so cannot verify list view", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+labelValue+" so cannot verify list view");

			}
			click(driver, npbl.getNavatarQuickLinkMinimize_Lighting(projectName, 3), "Minimize Icon", action.BOOLEAN);
			ThreadSleep(5000);

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list view for label : "+labelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list view for label : "+labelValue);
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc023_1_CreateALightningAppPageAndVerifyTheNavigationForLighteningAppPage(String projectName) {
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
					//						setup.clickOnEditForApp(driver, appName, AppDeveloperName,"Navatar Private Equity app – Lightning View(Edge)", 10);
					//						// scn.nextLine();
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
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	
		/////////////////////////////////////////////;
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc023_2_CreateALightningAppPageAndVerifyTheNavigationForLighteningAppPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		
		///////////////////////////////////////////
		String myAppPageURL="";
		if (npbl.clickOnTab(projectName, myAppPage)) {
			log(LogStatus.INFO, "Click on Tab : "+myAppPage, YesNo.No);
			ThreadSleep(10000);
			myAppPageURL=getURL(driver, 60);
			
			String urlLabel=CSVLabel.URL.toString();
			String urlValue=myAppPageURL;
			String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
			String[][] labelWithValue= {{urlLabel,urlValue}};
			
			if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
				log(LogStatus.INFO, urlValue+" value has been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.No);
				//NO NEED FOR THIS
//				if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, urlValue, CSVLabel.Navigation_Label.toString(), navigationLabel, urlLabel)) {
//					log(LogStatus.INFO, urlValue+" value has been written under "+urlLabel+" for "+navigationLabel, YesNo.No);
//				} else {
//					log(LogStatus.ERROR, urlValue+" has not been written under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
//					sa.assertTrue(false, urlValue+" has not been written under "+urlLabel+" for "+navigationLabel);
//
//				}
			}else{

				log(LogStatus.ERROR, "Not Able to enter url value field for "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false, "Not Able to enter url value field for "+navigationLabel);
			}
			
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+myAppPage, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+myAppPage);
	
		}
		
		String redirectLink;
		String labelValue="";
		String actualUrl="";
		String[] navigationLabelWithParent=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_With_Parent).split(breakSP);
		refresh(driver);
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
						
						redirectLink=myAppPageURL;
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
		
		//////////////////////////////////
		
		String actionObjectLabel=CSVLabel.Action_Object.toString();
		String actionObjecValue="Account";

		String otherAccountRecords=navigationLabel+",,,"+actionObjecValue+",,,,";
		String[][] labelWithValue= {{CSVLabel.Navigation_Label.toString(),navigationLabel},{actionObjectLabel,actionObjecValue}};
		
		
		if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
			log(LogStatus.INFO, "created "+navigationLabel, YesNo.No);
			
			if (ExcelUtils.writeOneRecordOnCSV(NavigationMenuTestData_PEExcel,otherAccountRecords)) {
				log(LogStatus.INFO, "Able to write record on csv "+otherAccountRecords , YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not Able to write record on csv "+otherAccountRecords, YesNo.Yes);
				sa.assertTrue(false, "Not Able to write record on csv "+otherAccountRecords);
			}
			refresh(driver);
			
			} else {
			log(LogStatus.ERROR, "Not Able to create "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+navigationLabel);

		}
		////////////////////////////////

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
											if (isSelected(driver, ele, recordTypeArray[i]+" "+recordTypeLabel.Active)) {
												ThreadSleep(2000);
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
							if (sendKeys(driver, ele, recordTypeName, CSVLabel.Action_Record_Type.toString(), action.BOOLEAN)) {
								log(LogStatus.INFO, "Able to enter "+CSVLabel.Action_Record_Type.toString(), YesNo.No);
								ThreadSleep(2000);

								if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "Click on save Button : "+labelValue, YesNo.No);
									ThreadSleep(2000);

//									if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, recordTypeName, CSVLabel.Navigation_Label.toString(), labelValue, CSVLabel.Action_Record_Type.toString())) {
//										log(LogStatus.INFO, recordTypeName+" value has been written under "+CSVLabel.Action_Record_Type.toString()+" for "+labelValue, YesNo.No);
//									} else {
//										log(LogStatus.ERROR, recordTypeName+" value has not been written under "+CSVLabel.Action_Record_Type.toString()+" for "+labelValue, YesNo.Yes);
//										sa.assertTrue(false, recordTypeName+" value has not been written under "+CSVLabel.Action_Record_Type.toString()+" for "+labelValue);
//									}

								} else {
									log(LogStatus.ERROR, "Not Able to Click on save Button : "+labelValue, YesNo.Yes);
									sa.assertTrue(false,"Not Able to Click on save Button : "+labelValue);}

							} else {
								log(LogStatus.ERROR, "Not Able to enter "+CSVLabel.Action_Record_Type.toString(), YesNo.Yes);
								sa.assertTrue(false,"Not Able to enter "+CSVLabel.Action_Record_Type.toString());
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
				ThreadSleep(5000);
				refresh(driver);
				ThreadSleep(5000);

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify record type options for label : "+labelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify record type options for label : "+labelValue);
			}
			System.err.println("><><><><>");
			// scn.nextLine();
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
			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, "", CSVLabel.Navigation_Label.toString(), navigationLabel, CSVLabel.Action_Record_Type.toString())) {
//				log(LogStatus.INFO, "Blank value has been written under "+CSVLabel.Action_Record_Type.toString()+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, "Blank value has not been written under "+CSVLabel.Action_Record_Type.toString()+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, "Blank value has not been written under "+CSVLabel.Action_Record_Type.toString()+" for "+navigationLabel);
//			}
			
			refresh(driver);
			ThreadSleep(5000);
			
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
							
							ele=npbl.getnavigationPopUpHeader(projectName, 10);
							String expecedHeader="New Entity";;
							if (ele!=null) {
								log(LogStatus.INFO, "PopUp is open" , YesNo.No);
								String actualHeader = ele.getText().trim();
								if (ele.getText().trim().equals(expecedHeader)) {
									log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);
									
								} else {
									log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
									sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
								}

							} else {
								log(LogStatus.ERROR, "No PopUp is open so cannoy verify Heading "+expecedHeader, YesNo.Yes);
								sa.assertTrue(false, "No PopUp is open so cannoy verify Heading "+expecedHeader);
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
			refresh(driver);
			ThreadSleep(5000);
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, urlValue, CSVLabel.Navigation_Label.toString(), navigationLabel, urlLabel)) {
//				log(LogStatus.INFO, urlValue+" value has been written under "+urlLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, urlValue+" has not been written under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, urlValue+" has not been written under "+urlLabel+" for "+navigationLabel);
//
//			}
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify url", YesNo.No);
						ThreadSleep(5000);
						String parentId = switchOnWindow(driver);
						if (parentId!=null) {
							String actualUrl=getURL(driver, 10);
							if (actualUrl.contains(urlValue)) {
								log(LogStatus.INFO, urlValue+" : Url Verified for : "+navigationLabel, YesNo.No);
							} else {
								log(LogStatus.ERROR, "Url Not Verified for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue, YesNo.Yes);
								sa.assertTrue(false,"Url Not Verified for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue);

							}
							driver.close();
							driver.switchTo().window(parentId);
						} else {

							log(LogStatus.ERROR, "No New Window is open so cannot verify URL for label :"+navigationLabel, YesNo.Yes);
							sa.assertTrue(false,"No New Window is open so cannot verify URL for label :"+navigationLabel);

						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify URL for label : "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify URL for label : "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify URL for label : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify URL for label : "+navigationLabel);

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
		String listViewObjectLabel=CSVLabel.List_View_Object.toString();
		String listViewObjectValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.List_View_Object);
		
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[][] labelWithValue= {{listViewNameLabel,listViewLabelValue},{listViewObjectLabel,listViewObjectValue}};

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
			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewLabelValue, CSVLabel.Navigation_Label.toString(), navigationLabel, listViewNameLabel)) {
//				log(LogStatus.INFO, listViewLabelValue+" value has been written under "+listViewNameLabel+" for "+navigationLabel, YesNo.No);
//				ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewObjectValue, CSVLabel.Navigation_Label.toString(), navigationLabel, listViewObjectLabel);
//			} else {
//				log(LogStatus.ERROR, listViewLabelValue+" has not been written under "+listViewNameLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, listViewLabelValue+" has not been written under "+listViewNameLabel+" for "+navigationLabel);
//
//			}
				refresh(driver);
				ThreadSleep(5000);
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
			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, urlLabelValue, CSVLabel.Navigation_Label.toString(), navigationLabel, urlLabel)) {
//				log(LogStatus.INFO, urlLabelValue+" value has been written under "+urlLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, urlLabelValue+" has not been written under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, urlLabelValue+" has not been written under "+urlLabel+" for "+navigationLabel);
//
//			}
				refresh(driver);
				ThreadSleep(5000);
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
							
							ele=npbl.getnavigationPopUpHeader(projectName, 10);
							String expecedHeader="New Entity";;
							if (ele!=null) {
								log(LogStatus.INFO, "PopUp is open" , YesNo.No);
								String actualHeader = ele.getText().trim();
								if (ele.getText().trim().equals(expecedHeader)) {
									log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);
									
								} else {
									log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
									sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
								}

							} else {
								log(LogStatus.ERROR, "No PopUp is open so cannoy verify Heading "+expecedHeader, YesNo.Yes);
								sa.assertTrue(false, "No PopUp is open so cannoy verify Heading "+expecedHeader);
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

//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, actionObjectValue, CSVLabel.Navigation_Label.toString(), navigationLabel, actionObjectLabel)) {
//				log(LogStatus.INFO, actionObjectValue+" value has been written under "+actionObjectLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, actionObjectValue+" has not been written under "+actionObjectLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, actionObjectValue+" has not been written under "+actionObjectLabel+" for "+navigationLabel);
//
//			}
				refresh(driver);
				ThreadSleep(5000);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
						ThreadSleep(5000);
						boolean flag = npbl.isAutomationAllListViewForObject(projectName,"Entities",automationAll, 30);;
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
	
		}else{

			log(LogStatus.ERROR, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc032_RemoveListViewObjectAndVerifyTheImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String listViewObjectLabel=CSVLabel.List_View_Object.toString();
		String listViewObjectLabelValue="";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[][] labelWithValue= {{listViewObjectLabel,listViewObjectLabelValue}};

		// Now list view of Account should get open. As both URL and Quick Create Object field is blank.

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, listViewObjectLabelValue+" value has been updated & saved under "+listViewObjectLabel+" for "+navigationLabel, YesNo.No);
			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewObjectLabelValue, CSVLabel.Navigation_Label.toString(), navigationLabel, listViewObjectLabel)) {
//				log(LogStatus.INFO, listViewObjectLabelValue+" value has been written under "+listViewObjectLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, listViewObjectLabelValue+" has not been written under "+listViewObjectLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, listViewObjectLabelValue+" has not been written under "+listViewObjectLabel+" for "+navigationLabel);
//
//			}
				refresh(driver);
				ThreadSleep(5000);
				String expectedUrl=getURL(driver, 10);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
						ThreadSleep(5000);
						String actualUrl=getURL(driver, 10);
						if (expectedUrl.equals(actualUrl)) {
							log(LogStatus.ERROR, "Nothing happens upon clicking on "+navigationLabel+" as there is no change in url ", YesNo.Yes);
						}else {
							log(LogStatus.ERROR, "Nothing should be happens upon clicking on "+navigationLabel+" as there is change in url ", YesNo.Yes);
							sa.assertTrue(false, "Nothing should be happens upon clicking on "+navigationLabel+" as there is change in url ");
						
						}
				
					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

				}
			
		}else{

			log(LogStatus.ERROR, "Not Able to enter value on" +listViewObjectLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "Not Able to enter value on" +listViewObjectLabel+" for "+navigationLabel);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc033_RemoveListViewNameAndVerifyTheImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String listViewNameLabel=CSVLabel.List_View_Name.toString();
		String listViewNameLabelValue="";
		String listViewObjectLabel=CSVLabel.List_View_Object.toString();
		String listViewObjectValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.List_View_Object);
		
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[][] labelWithValue= {{listViewNameLabel,listViewNameLabelValue},{listViewObjectLabel,listViewObjectValue}};

		// Now list view of Account should get open. As both URL and Quick Create Object field is blank.

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, listViewNameLabelValue+" value has been updated & saved under "+listViewNameLabel+" for "+navigationLabel, YesNo.No);
			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewNameLabelValue, CSVLabel.Navigation_Label.toString(), navigationLabel, listViewNameLabel)) {
//				log(LogStatus.INFO, listViewNameLabelValue+" value has been written under "+listViewNameLabel+" for "+navigationLabel, YesNo.No);
//				ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewObjectValue, CSVLabel.Navigation_Label.toString(), navigationLabel, listViewObjectLabel);
//			} else {
//				log(LogStatus.ERROR, listViewNameLabelValue+" has not been written under "+listViewNameLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, listViewNameLabelValue+" has not been written under "+listViewNameLabel+" for "+navigationLabel);
//
//			}
				
				refresh(driver);
				ThreadSleep(5000);
				String expectedUrl=getURL(driver, 10);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
						ThreadSleep(5000);
						boolean flag = npbl.isAutomationAllListViewForObject(projectName,"Entities","Recently Viewed", 30);;
						if (flag) {
							log(LogStatus.INFO, "Account Page is opened List View is available after clicking on "+navigationLabel , YesNo.No);
						} else {
							log(LogStatus.ERROR, "Account Page is not opened after clicking on "+navigationLabel, YesNo.Yes);
							sa.assertTrue(false, "Account Page is not opened after clicking on "+navigationLabel);
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

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
	public void Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String[][] fundRecordType = {{recordTypeLabel.Record_Type_Label.toString(),recordTypeArray[0]},
				{recordTypeLabel.Description.toString(),recordTypeArray[0]+recordTypeDescription},
				{recordTypeLabel.Active.toString(),""}};

		String[][] ffrecordType = {{recordTypeLabel.Record_Type_Label.toString(),recordTypeArray[1]},
				{recordTypeLabel.Description.toString(),recordTypeArray[1]+recordTypeDescription},
				{recordTypeLabel.Active.toString(),""}};

		boolean isMakeAvailable=true;
		boolean isMakeDefault =true;
		boolean flag=false;
		for (int i = 0; i < recordTypeArray.length; i++) {
			if (home.clickOnSetUpLink()) {
				flag=false;
				String parentID = switchOnWindow(driver);
				SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
				if (parentID!=null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Fund )) {
						if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Fund, ObjectFeatureName.recordTypes)) {
							ThreadSleep(5000);
							if (i==0) {
								flag=sp.createRecordTypeForObject(projectName, fundRecordType, isMakeAvailable, isMakeDefault,null, 10);	
							} else {
								isMakeDefault=false;
								flag=sp.createRecordTypeForObject(projectName, ffrecordType, isMakeAvailable, isMakeDefault,null, 10);
							}
							if (flag) {
								log(LogStatus.ERROR, "Created Record Type : "+recordTypeArray[i], YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not Able to Create Record Type : "+recordTypeArray[i], YesNo.Yes);
								sa.assertTrue(false,"Not Able to Create Record Type : "+recordTypeArray[i]);
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
					refresh(driver);
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
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc035_VerifyQuickCreateObjectWhenRecordHasMultipleRecordTypes(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String actionObjectLabel=CSVLabel.Action_Object.toString();
		String actionObjectLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Action_Object);;
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[][] labelWithValue= {{actionObjectLabel,actionObjectLabelValue}};

		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		//Verify Quick Create Object when a record has multiple record types

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, actionObjectLabelValue+" value has been updated & saved under "+actionObjectLabel+" for "+navigationLabel, YesNo.No);
			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, actionObjectLabelValue, CSVLabel.Navigation_Label.toString(), navigationLabel, actionObjectLabel)) {
//				log(LogStatus.INFO, actionObjectLabelValue+" value has been written under "+actionObjectLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, actionObjectLabelValue+" has not been written under "+actionObjectLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, actionObjectLabelValue+" has not been written under "+actionObjectLabel+" for "+navigationLabel);
//
//			}
				
				refresh(driver);
				ThreadSleep(5000);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
						ThreadSleep(5000);
						ThreadSleep(5000);
						ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
						if (ele!=null) {
							log(LogStatus.INFO, "Pop Up open after clicking on "+navigationLabel , YesNo.No);
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
									
									ele=npbl.getrecordTypeWithDescription(recordTypeArray[i], recordTypeArray[i]+recordTypeDescription, 10);
									if (ele!=null) {
										log(LogStatus.INFO, "Record Type & description verified : "+recordTypeArray[i]+recordTypeDescription , YesNo.No);

									} else {
										log(LogStatus.ERROR, "Record Type & description not verified : "+recordTypeArray[i]+recordTypeDescription, YesNo.Yes);
										sa.assertTrue(false, "Record Type & description not verified : "+recordTypeArray[i]+recordTypeDescription);
									}
									
								} else {
									log(LogStatus.ERROR, recordTypeArray[i]+" radio button not present", YesNo.Yes);
									sa.assertTrue(false, recordTypeArray[i]+" radio button not present");
								}

							}
							
							ele=npbl.getnavigationPopUpHeader(projectName, 10);
							String expecedHeader="New Fund";;
							if (ele!=null) {
								log(LogStatus.INFO, "PopUp is open" , YesNo.No);
								String actualHeader = ele.getText().trim();
								if (ele.getText().trim().equals(expecedHeader)) {
									log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);
									
								} else {
									log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
									sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
								}

							} else {
								log(LogStatus.ERROR, "No PopUp is open so cannoy verify Heading "+expecedHeader, YesNo.Yes);
								sa.assertTrue(false, "No PopUp is open so cannoy verify Heading "+expecedHeader);
							}
							
							ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
							if (click(driver, ele, navigationLabel+" pop up cross button", action.BOOLEAN)) {
								log(LogStatus.INFO, "click on cross button "+navigationLabel , YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not Able to click on cross button "+navigationLabel, YesNo.Yes);
								sa.assertTrue(false, "Not Able to click on cross button "+navigationLabel);
							}
						} else {
							log(LogStatus.ERROR, "No Pop Up is open after clicking on "+navigationLabel, YesNo.Yes);
							sa.assertTrue(false, "No Pop Up is open after clicking on "+navigationLabel);
						}
				
					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

				}

		}else{

			log(LogStatus.ERROR, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc036_1_UpdateTheRecordTypeAndDescriptionImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		

		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		
		String[][] fundRecordType = {{recordTypeLabel.Record_Type_Label.toString(),upDated+recordTypeArray[0]},
				{recordTypeLabel.Description.toString(),upDated+recordTypeArray[0]+recordTypeDescription}};
		
		
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
	
		/////////////////////////////////////////////////////
		for (int i = 0; i < 1; i++) {
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
				if (parentID!=null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Fund )) {
						if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Fund, ObjectFeatureName.recordTypes)) {
							ThreadSleep(5000);
							if (sp.clickOnAlreadyCreatedLayout(recordTypeArray[i])) {
								ThreadSleep(5000);
								if (sp.editRecordTypeForObject(projectName, fundRecordType, 10)) {
									log(LogStatus.ERROR,recordTypeArray[i]+" has been updated ",YesNo.Yes);	
								}else {
									log(LogStatus.ERROR,recordTypeArray[i]+" not updated ",YesNo.Yes);
									sa.assertTrue(false, recordTypeArray[i]+" not updated ");
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
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify record type options", YesNo.No);
				ThreadSleep(5000);
				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, "Pop Up open after clicking on "+navigationLabel , YesNo.No);

				} else {
					log(LogStatus.ERROR, "No Pop Up is open after clicking on "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false, "No Pop Up is open after clicking on "+navigationLabel);
				}
				ele=npbl.getrecordTypeWithDescription(upDated+recordTypeArray[0], upDated+recordTypeArray[0]+recordTypeDescription, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "Record Type & description Updated & verified" , YesNo.No);

				} else {
					log(LogStatus.ERROR, "Record Type & description not Updated & not verified "+upDated+recordTypeArray[0] +" "+ upDated+recordTypeArray[0]+recordTypeDescription, YesNo.Yes);
					sa.assertTrue(false, "Record Type & description not Updated & not verified "+upDated+recordTypeArray[0] +" "+ upDated+recordTypeArray[0]+recordTypeDescription);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify record type options after update", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify record type options after");

			}
			
				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 5);
				click(driver, ele, "Cross Icon", action.BOOLEAN);
				ThreadSleep(3000);

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify record type options for label : "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify record type options for label : "+navigationLabel);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc036_2_UpdateTheRecordTypeAndDescriptionImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		

		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		
		
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
	
		/////////////////////////////////////////////////////
		
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify record type options", YesNo.No);
				ThreadSleep(5000);
				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, "Pop Up open after clicking on "+navigationLabel , YesNo.No);

				} else {
					log(LogStatus.ERROR, "No Pop Up is open after clicking on "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false, "No Pop Up is open after clicking on "+navigationLabel);
				}
				ele=npbl.getrecordTypeWithDescription(upDated+recordTypeArray[0], upDated+recordTypeArray[0]+recordTypeDescription, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "Record Type & description Updated & verified" , YesNo.No);

				} else {
					log(LogStatus.ERROR, "Record Type & description not Updated & not verified "+upDated+recordTypeArray[0] +" "+ upDated+recordTypeArray[0]+recordTypeDescription, YesNo.Yes);
					sa.assertTrue(false, "Record Type & description not Updated & not verified "+upDated+recordTypeArray[0] +" "+ upDated+recordTypeArray[0]+recordTypeDescription);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify record type options after update", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify record type options after");

			}
			
				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 5);
				click(driver, ele, "Cross Icon", action.BOOLEAN);
				ThreadSleep(3000);

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify record type options for label : "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify record type options for label : "+navigationLabel);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc037_1_FillQuickCreateRecordTypeAndVerifyTheImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		WebElement ele;
		String recordTypeNameLabelValueForUpdatedFundRecordType="";

		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String updatedFundRecordType=upDated+recordTypeArray[0];
		/////////////////////////////////////////////////////
		boolean flag=false;
		if (home.clickOnSetUpLink()) {
			flag=false;
			String parentID = switchOnWindow(driver);
			SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Fund )) {
					if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Fund, ObjectFeatureName.recordTypes)) {
						ThreadSleep(5000);
						if (sp.clickOnAlreadyCreatedLayout(updatedFundRecordType)) {
							ThreadSleep(5000);
							switchToFrame(driver, 20, sp.getSetUpPageIframe(60));
							if (click(driver, sp.getEditButton(environment,"Classic",10), "edit", action.SCROLLANDBOOLEAN)) {
								ThreadSleep(5000);
								switchToDefaultContent(driver);
								switchToFrame(driver, 20, sp.getSetUpPageIframe(60));
								ele=sp.getRecordTypeLabel(projectName, recordTypeLabel.Record_Type_Name.toString(), 20);
								if (ele!=null) {
									log(LogStatus.INFO,updatedFundRecordType+" ele found on edit page",YesNo.No);
									recordTypeNameLabelValueForUpdatedFundRecordType=ele.getAttribute("value");
									System.err.println("recordTypeName "+recordTypeNameLabelValueForUpdatedFundRecordType);
									flag=true;
								} else {
									log(LogStatus.ERROR,updatedFundRecordType+" ele not found on edit page",YesNo.Yes);
									sa.assertTrue(false, updatedFundRecordType+" ele not found on edit page");
								}

							}else {
								log(LogStatus.ERROR,"edit button is not clickable",YesNo.Yes);
								sa.assertTrue(false, "edit button is not clickable");
							}


						}else {
							log(LogStatus.ERROR, updatedFundRecordType+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, updatedFundRecordType+" is not clickable");
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

		if (flag) {

			String actionRecordTypeLabel=CSVLabel.Action_Record_Type.toString();
			String actionRecordTypeLabelValue=recordTypeNameLabelValueForUpdatedFundRecordType;
			String[][] labelWithValue= {{actionRecordTypeLabel,actionRecordTypeLabelValue}};


			if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
				log(LogStatus.INFO, actionRecordTypeLabelValue+" value has been updated & saved under "+actionRecordTypeLabel+" for "+navigationLabel, YesNo.No);

//				if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, actionRecordTypeLabelValue, CSVLabel.Navigation_Label.toString(), navigationLabel, actionRecordTypeLabel)) {
//					log(LogStatus.INFO, actionRecordTypeLabelValue+" value has been written under "+actionRecordTypeLabel+" for "+navigationLabel, YesNo.No);
//				} else {
//					log(LogStatus.ERROR, actionRecordTypeLabelValue+" has not been written under "+actionRecordTypeLabel+" for "+navigationLabel, YesNo.Yes);
//					sa.assertTrue(false, actionRecordTypeLabelValue+" has not been written under "+actionRecordTypeLabel+" for "+navigationLabel);
//
//				}
				refresh(driver);
				ThreadSleep(5000);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
						ThreadSleep(5000);
						String popup=object.Fund+": "+updatedFundRecordType;
						ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, popup, action.BOOLEAN, 30);
						if (ele!=null) {
							log(LogStatus.INFO, popup+" Pop Up open after clicking on "+navigationLabel , YesNo.No);
							ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, popup, action.BOOLEAN, 30);
							if (click(driver, ele, navigationLabel+" pop up cross button", action.BOOLEAN)) {
								log(LogStatus.INFO, "click on cross button "+navigationLabel , YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not Able to click on cross button "+navigationLabel, YesNo.Yes);
								sa.assertTrue(false, "Not Able to click on cross button "+navigationLabel);
							}
						} else {
							log(LogStatus.ERROR, "No Pop Up is open after clicking on "+navigationLabel, YesNo.Yes);
							sa.assertTrue(false, "No Pop Up is open after clicking on "+navigationLabel);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

				}

			}else{

				log(LogStatus.ERROR, "Not Able to enter value on" +actionRecordTypeLabel+" for "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false, "Not Able to enter value on" +actionRecordTypeLabel+" for "+navigationLabel);
			}

		}else {
			log(LogStatus.ERROR, "As Flag is false not able to check on " +navigationMenuName+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "As Flag is false not able to check on " +navigationMenuName+" for "+navigationLabel);	
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc037_2_FillQuickCreateRecordTypeAndVerifyTheImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		
		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String updatedFundRecordType=upDated+recordTypeArray[0];
		/////////////////////////////////////////////////////
		

			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
					ThreadSleep(5000);
					String popup=object.Fund+": "+updatedFundRecordType;
					ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, popup, action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, popup+" Pop Up open after clicking on "+navigationLabel , YesNo.No);
						ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, popup, action.BOOLEAN, 30);
						if (click(driver, ele, navigationLabel+" pop up cross button", action.BOOLEAN)) {
							log(LogStatus.INFO, "click on cross button "+navigationLabel , YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not Able to click on cross button "+navigationLabel, YesNo.Yes);
							sa.assertTrue(false, "Not Able to click on cross button "+navigationLabel);
						}
					} else {
						log(LogStatus.ERROR, "No Pop Up is open after clicking on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false, "No Pop Up is open after clicking on "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

			}

		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc038_1_ChangeQuickCreateRecordTypeAndVerifyTheImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		WebElement ele;
		String recordTypeNameLabelValueForFundOfFundRecordType="";

		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String fundOfFundRecordType=recordTypeArray[1];
		/////////////////////////////////////////////////////
		boolean flag=false;
		if (home.clickOnSetUpLink()) {
			flag=false;
			String parentID = switchOnWindow(driver);
			SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Fund )) {
					if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Fund, ObjectFeatureName.recordTypes)) {
						ThreadSleep(5000);
						if (sp.clickOnAlreadyCreatedLayout(fundOfFundRecordType)) {
							ThreadSleep(5000);
							switchToFrame(driver, 20, sp.getSetUpPageIframe(60));
							if (click(driver, sp.getEditButton(environment,"Classic",10), "edit", action.SCROLLANDBOOLEAN)) {
								ThreadSleep(5000);
								switchToDefaultContent(driver);
								switchToFrame(driver, 20, sp.getSetUpPageIframe(60));
								ele=sp.getRecordTypeLabel(projectName, recordTypeLabel.Record_Type_Name.toString(), 20);
								if (ele!=null) {
									log(LogStatus.INFO,fundOfFundRecordType+" ele found on edit page",YesNo.No);
									recordTypeNameLabelValueForFundOfFundRecordType=ele.getAttribute("value");
									System.err.println("recordTypeName "+recordTypeNameLabelValueForFundOfFundRecordType);
									flag=true;
								} else {
									log(LogStatus.ERROR,fundOfFundRecordType+" ele not found on edit page",YesNo.Yes);
									sa.assertTrue(false, fundOfFundRecordType+" ele not found on edit page");
								}

							}else {
								log(LogStatus.ERROR,"edit button is not clickable",YesNo.Yes);
								sa.assertTrue(false, "edit button is not clickable");
							}


						}else {
							log(LogStatus.ERROR, fundOfFundRecordType+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, fundOfFundRecordType+" is not clickable");
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

		if (flag) {

			String actionRecordTypeLabel=CSVLabel.Action_Record_Type.toString();
			String actionRecordTypeLabelValue=recordTypeNameLabelValueForFundOfFundRecordType;
			String[][] labelWithValue= {{actionRecordTypeLabel,actionRecordTypeLabelValue}};


			if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
				log(LogStatus.INFO, actionRecordTypeLabelValue+" value has been updated & saved under "+actionRecordTypeLabel+" for "+navigationLabel, YesNo.No);

//				if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, actionRecordTypeLabelValue, CSVLabel.Navigation_Label.toString(), navigationLabel, actionRecordTypeLabel)) {
//					log(LogStatus.INFO, actionRecordTypeLabelValue+" value has been written under "+actionRecordTypeLabel+" for "+navigationLabel, YesNo.No);
//				} else {
//					log(LogStatus.ERROR, actionRecordTypeLabelValue+" has not been written under "+actionRecordTypeLabel+" for "+navigationLabel, YesNo.Yes);
//					sa.assertTrue(false, actionRecordTypeLabelValue+" has not been written under "+actionRecordTypeLabel+" for "+navigationLabel);
//
//				}
				refresh(driver);
				ThreadSleep(5000);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
						ThreadSleep(5000);
						String popup=object.Fund+": "+fundOfFundRecordType;
						ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, popup, action.BOOLEAN, 30);
						if (ele!=null) {
							log(LogStatus.INFO, popup+" Pop Up open after clicking on "+navigationLabel , YesNo.No);
							ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, popup, action.BOOLEAN, 30);
							if (click(driver, ele, navigationLabel+" pop up cross button", action.BOOLEAN)) {
								log(LogStatus.INFO, "click on cross button "+navigationLabel , YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not Able to click on cross button "+navigationLabel, YesNo.Yes);
								sa.assertTrue(false, "Not Able to click on cross button "+navigationLabel);
							}
						} else {
							log(LogStatus.ERROR, "No Pop Up is open after clicking on "+navigationLabel, YesNo.Yes);
							sa.assertTrue(false, "No Pop Up is open after clicking on "+navigationLabel);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

				}

			}else{

				log(LogStatus.ERROR, "Not Able to enter value on" +actionRecordTypeLabel+" for "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false, "Not Able to enter value on" +actionRecordTypeLabel+" for "+navigationLabel);
			}

		}else {
			log(LogStatus.ERROR, "As Flag is false not able to check on " +navigationMenuName+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "As Flag is false not able to check on " +navigationMenuName+" for "+navigationLabel);	
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc038_2_ChangeQuickCreateRecordTypeAndVerifyTheImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		
		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String fundOfFundRecordType=recordTypeArray[1];
		/////////////////////////////////////////////////////
		

			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
					ThreadSleep(5000);
					String popup=object.Fund+": "+fundOfFundRecordType;
					ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, popup, action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, popup+" Pop Up open after clicking on "+navigationLabel , YesNo.No);
						ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, popup, action.BOOLEAN, 30);
						if (click(driver, ele, navigationLabel+" pop up cross button", action.BOOLEAN)) {
							log(LogStatus.INFO, "click on cross button "+navigationLabel , YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not Able to click on cross button "+navigationLabel, YesNo.Yes);
							sa.assertTrue(false, "Not Able to click on cross button "+navigationLabel);
						}
					} else {
						log(LogStatus.ERROR, "No Pop Up is open after clicking on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false, "No Pop Up is open after clicking on "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

			}

		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc039_RemoveTheRecordTypeAPIFieldAndVerifyTheImpactOnNavigationMenu(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		
		String actionRecordTypeLabel=CSVLabel.Action_Record_Type.toString();
		String actionRecordTypeValue="";
		String[][] labelWithValue= {{actionRecordTypeLabel,actionRecordTypeValue}};

		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String updatedFundRecordType=upDated+recordTypeArray[0];
		
		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, "Blank value has been updated & saved under "+actionRecordTypeLabel+" for "+navigationLabel, YesNo.No);
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, "", CSVLabel.Navigation_Label.toString(), navigationLabel, actionRecordTypeLabel)) {
//				log(LogStatus.INFO, "Blank value has been written under "+actionRecordTypeLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, "Blank value has not been written under "+actionRecordTypeLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, "Blank value has not been written under "+actionRecordTypeLabel+" for "+navigationLabel);
//			}
			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify record type options", YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, "Pop Up open after clicking on "+navigationLabel , YesNo.No);
						String recordType=updatedFundRecordType;
						ele=npbl.getRadioButtonforRecordTypeAtAccount(recordType, 5);
						if (ele!=null) {
							log(LogStatus.INFO, recordType+" radio button is available after removing value from"+actionRecordTypeLabel, YesNo.No);
						} else {
							log(LogStatus.ERROR, recordType+" radio button should be present after removing value from"+actionRecordTypeLabel, YesNo.Yes);
							sa.assertTrue(false, recordType+" radio button should be present after removing value from"+actionRecordTypeLabel);
						}
						
						ele=npbl.getnavigationPopUpHeader(projectName, 10);
						String expecedHeader="New Fund";
						if (ele!=null) {
							log(LogStatus.INFO, "PopUp is open" , YesNo.No);
							String actualHeader = ele.getText().trim();
							if (ele.getText().trim().equals(expecedHeader)) {
								log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);
								
							} else {
								log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
								sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
							}

						} else {
							log(LogStatus.ERROR, "No PopUp is open so cannoy verify Heading "+expecedHeader, YesNo.Yes);
							sa.assertTrue(false, "No PopUp is open so cannoy verify Heading "+expecedHeader);
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
			log(LogStatus.ERROR, "Not Able to Remove the Record Type API field for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "Not Able to Remove the Record Type API field for "+navigationLabel);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc040_CreateListViewForFundAndVerifyTheNavigationForListView(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String updatedFundRecordType=upDated+recordTypeArray[0];
		
		String listViewObjectLabel=CSVLabel.List_View_Object.toString();
		String listViewNameLabel=CSVLabel.List_View_Name.toString();
		String listViewObject=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.List_View_Object);
		String listViewName=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.List_View_Name);
		String[][] navigationFieldWithValues= {{listViewObjectLabel,listViewObject},{listViewNameLabel,listViewName}};
		

		if (lp.clickOnTab(projectName, TabName.Object3Tab)) {	
			if (lp.addAutomationAllListView(projectName,TabName.Object3Tab.toString(), 10)) {
				log(LogStatus.INFO,"list view added on "+TabName.Object3Tab,YesNo.No);
			} else {
				log(LogStatus.FAIL,"list view could not added on "+TabName.Object3Tab,YesNo.Yes);
				sa.assertTrue(false, "list view could not added on "+TabName.Object3Tab);
			}
		} else {
			log(LogStatus.FAIL,"could not click on "+TabName.Object3Tab,YesNo.Yes);
			sa.assertTrue(false, "could not click on "+TabName.Object3Tab);
		}

		///////////////////////////////////////////////////////
		
		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, navigationFieldWithValues, 20)) {
			log(LogStatus.INFO, listViewObjectLabel+" and "+listViewNameLabel+" value has been updated & saved under for "+navigationLabel, YesNo.No);
			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewName
//					, CSVLabel.Navigation_Label.toString(), navigationLabel, CSVLabel.List_View_Name.toString())) {
//				log(LogStatus.INFO, listViewName+" value has been written under "+CSVLabel.List_View_Name.toString()+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, listViewName+" value has not been written under "+CSVLabel.List_View_Name.toString()+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, listViewName+" value has not been written under "+CSVLabel.List_View_Name.toString()+" for "+navigationLabel);
//			}
//			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewObject, CSVLabel.Navigation_Label.toString(), navigationLabel, CSVLabel.List_View_Object.toString())) {
//				log(LogStatus.INFO, listViewObject+" value has been written under "+CSVLabel.List_View_Object.toString()+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, listViewObject+" value has not been written under "+CSVLabel.List_View_Object.toString()+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, listViewObject+" value has not been written under "+CSVLabel.List_View_Object.toString()+" for "+navigationLabel);
//			}
			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify pop up even after adding list view", YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, "Pop Up open even after adding list view after clicking on "+navigationLabel , YesNo.No);
						String recordType=updatedFundRecordType;
						ele=npbl.getRadioButtonforRecordTypeAtAccount(recordType, 5);
						if (ele!=null) {
							log(LogStatus.INFO, recordType+" radio button is available even after adding list view", YesNo.No);
						} else {
							log(LogStatus.ERROR, recordType+" radio button should be present even after adding list view", YesNo.Yes);
							sa.assertTrue(false, recordType+" radio button should be present even after adding list view");
						}
						ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
						click(driver, ele, "Navigation menu PoPuP Cross Icon", action.BOOLEAN);
					} else {
						log(LogStatus.ERROR, "No Pop Up is open after clicking on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false, "No Pop Up is open after clicking on "+navigationLabel);
					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify record type options even after adding list view", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify record type options even after adding list view");

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify record type options even after adding list view for label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify record type options even after adding list view for label : "+navigationLabel);
			}
		} else {
			log(LogStatus.ERROR, listViewObjectLabel+" and "+listViewNameLabel+" value has not been updated & saved under for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, listViewObjectLabel+" and "+listViewNameLabel+" value has not been updated & saved under for "+navigationLabel);

		}
		///////////////////////////////////////////////////

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc041_AddSomeThirPartyURLAndVerifyImpact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);

		String urlLabel=CSVLabel.URL.toString();
		String urlValue=googleUrlValue;
		String[][] labelWithValue= {{urlLabel,urlValue}};

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, urlValue+" value has been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.No);

//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, urlValue, CSVLabel.Navigation_Label.toString(), navigationLabel, urlLabel)) {
//				log(LogStatus.INFO, urlValue+" value has been written under "+urlLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel);
//			}
			refresh(driver);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify url", YesNo.No);
					ThreadSleep(5000);
					String parentId=switchOnWindow(driver);
					if (parentId!=null) {
						log(LogStatus.ERROR, "New window is open after Click on "+navigationLabel+" so going to verify url", YesNo.Yes);
						String actualUrl = getURL(driver, 10);
						if (urlValue.contains(actualUrl)) {
							log(LogStatus.INFO, urlValue+" : Url Verified for : "+navigationLabel, YesNo.No);
						} else {
							log(LogStatus.ERROR, "Url Not Verified for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue, YesNo.Yes);
							sa.assertTrue(false,"Url Not Verified for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue);
					
						}
						driver.close();
						driver.switchTo().window(parentId);
					} else {
						log(LogStatus.ERROR, "No New window is open after Click on "+navigationLabel+" so cannot verify url", YesNo.Yes);
						sa.assertTrue(false,"No New window is open after Click on "+navigationLabel+" so cannot verify url");
					}
					

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify url", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify url");

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+navigationLabel);
			}
		} else {
			log(LogStatus.ERROR, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc042_RemoveURLAndVerifyImpact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);

		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String updatedFundRecordType=upDated+recordTypeArray[0];
		
		String urlLabel=CSVLabel.URL.toString();
		String urlValue="";
		String[][] labelWithValue= {{urlLabel,urlValue}};

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, urlValue+" value has been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.No);

//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, urlValue, CSVLabel.Navigation_Label.toString(), navigationLabel, urlLabel)) {
//				log(LogStatus.INFO, urlValue+" value has been written under "+urlLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel);
//			}
			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify record option", YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, "Pop Up open even after removing value for url after clicking on "+navigationLabel , YesNo.No);
						String recordType=updatedFundRecordType;
						ele=npbl.getRadioButtonforRecordTypeAtAccount(recordType, 5);
						if (ele!=null) {
							log(LogStatus.INFO, recordType+" radio button is available even after removing value for url", YesNo.No);
						} else {
							log(LogStatus.ERROR, recordType+" radio button should be present even after removing value for url", YesNo.Yes);
							sa.assertTrue(false, recordType+" radio button should be present even after removing value for url");
						}
						ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
						click(driver, ele, "Navigation menu PoPuP Cross Icon", action.BOOLEAN);
					} else {
						log(LogStatus.ERROR, "No Pop Up is open after clicking on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false, "No Pop Up is open after clicking on "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify record type pop up after removing url value", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify record type pop up after removing url value");

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify record type pop up after removing url value for label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify record type pop up after removing url value for label : "+navigationLabel);
			}
		} else {
			log(LogStatus.ERROR, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc043_RemoveQuickCreateFieldAndVerifyImpactOnNavigationMenu(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);

		String urlLabel=CSVLabel.Action_Object.toString();
		String urlValue="";
		String[][] labelWithValue= {{urlLabel,urlValue}};

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, urlValue+" value has been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.No);

//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, urlValue, CSVLabel.Navigation_Label.toString(), navigationLabel, urlLabel)) {
//				log(LogStatus.INFO, urlValue+" value has been written under "+urlLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel);
//			}
			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify list view after removing action object value", YesNo.No);
					ThreadSleep(5000);
					boolean flag = npbl.isAutomationAllListViewForObject(projectName,"Funds",automationAll, 30);
					if (flag) {
						log(LogStatus.INFO, "list view after removing action object value is available after clicking on "+navigationLabel , YesNo.No);
						
					} else {
						log(LogStatus.ERROR, "No list view after removing action object value is available is open after clicking on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false, "No list view after removing action object value is available is open after clicking on "+navigationLabel);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify list view after removing action object value", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify list view after removing action object value");

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list view after removing action object value for label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list view after removing action object value for label : "+navigationLabel);
			}
		} else {
			log(LogStatus.ERROR, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc044_CreateACustomObjectAndAddFewFieldsOnTheObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String customObject=tabCustomObj+"s";
		/////////////////////////////////////////////////////
		boolean flag=true;
		if (home.clickOnSetUpLink()) {
			flag=false;
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Create )) {
					log(LogStatus.INFO, "Click on Create/Custom object so going to create : "+customObject, YesNo.No);
					String[][] labelWithValue= {{customObjectLabel.Label.toString(),customObject},{customObjectLabel.Plural_Label.toString(),customObject+"s"}};
					if(sp.createCustomObject(projectName, labelWithValue, 10)) {
						log(LogStatus.INFO, "Custom Object Created : "+customObject, YesNo.No);
						flag=true;
					}else {
						log(LogStatus.ERROR, "Not Able to Create : "+customObject, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Create : "+customObject);
					}
				}else {
					log(LogStatus.ERROR, "Not Able to Click on Create/Custom object so cannot create : "+customObject, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Create/Custom object so cannot create : "+customObject);
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

		switchToDefaultContent(driver);
		refresh(driver);
		ThreadSleep(5000);
		if (flag) {
			if (home.clickOnSetUpLink()) {
				flag=false;
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					FC_Length1="255";
					FC_FieldType1="Text";
					FC_FieldLabelName1="Test Demo1";
					String[][] labelAndValues= {{"Length",FC_Length1}};
					if(sp.addCustomFieldforFormula(environment,mode,object.Custom_Object,ObjectFeatureName.FieldAndRelationShip,FC_FieldType1,FC_FieldLabelName1, labelAndValues, null,null)) {
						log(LogStatus.PASS, "Field Component is created for :"+FC_FieldLabelName1, YesNo.No);
						flag=true;
					}else {
						log(LogStatus.PASS, "Field Component is not created for :"+FC_FieldLabelName1, YesNo.Yes);
						sa.assertTrue(false, "Field Component is not created for :"+FC_FieldLabelName1);
					} 
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch");
				}
			}else {
				log(LogStatus.ERROR, "could not click on setup link so cannot create Field", YesNo.Yes);
				sa.assertTrue(false, "could not click on setup link so cannot create Field");
			}

			switchToDefaultContent(driver);
			refresh(driver);
			ThreadSleep(5000);
			if (home.clickOnSetUpLink()) {
				flag=false;
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					
					if(sp.addObjectToTab(environment, mode, projectName, object.Tabs, customObject, "Bell",parentID)) {
						log(LogStatus.PASS, customObject+" added to Tab", YesNo.No);
						flag=true;
					}else {
						log(LogStatus.PASS, customObject+" not added to Tab", YesNo.Yes);
						sa.assertTrue(false, customObject+" not added to Tab");
					} 
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch");
				}
			}else {
				log(LogStatus.ERROR, "could not click on setup link so cannot add custom object to Tab", YesNo.Yes);
				sa.assertTrue(false, "could not click on setup link so cannot add custom object to Tab");
			}
			
		}
		
		String[] userNames= {"PE Standard User"};
		String onObject=customObject;
		String permission="Create";
		for (String userName : userNames) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to set"+permission+" for "+onObject, YesNo.No);
					ThreadSleep(500);
					if(setup.searchStandardOrCustomObject(environment,mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : "+object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.permissionChangeForUserONObject(driver, userName, new String[][]{{onObject,permission},{onObject,"Delete"}}, 20)) {
							log(LogStatus.PASS,permission+ " permission change for "+userName+" on object "+onObject,YesNo.No);
						} else {
							sa.assertTrue(false, permission+ " permission not change for "+userName+" on object "+onObject);
							log(LogStatus.FAIL,permission+ " permission not change for "+userName+" on object "+onObject,YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to search/click on "+object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on "+object.Profiles);
					}
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "could not find new window to switch, so cannot to set"+permission+" for "+onObject, YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, to set"+permission+" for "+onObject);
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");	
			}
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc045_CreateMenuItemForCustomObjectOnNavigationTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String navigationLabel=CSVLabel.Navigation_Label.toString();
		String navigationLabelValue=customMenu;
		String orderLabel=CSVLabel.Order.toString();
		String orderLabelValue="30";
		String actionObjectLabel=CSVLabel.Action_Object.toString();
		String actionObjecValue=tabCustomObjAPIName;

		String navigationCustomMenuRecords=customMenu+","+orderLabelValue+",,"+actionObjecValue+",,,";
		String[][] labelWithValue= {{navigationLabel,navigationLabelValue},{orderLabel,orderLabelValue},{actionObjectLabel,actionObjecValue}};
		WebElement ele;
		if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
			log(LogStatus.INFO, "created "+customMenu, YesNo.No);
			
			if (ExcelUtils.writeOneRecordOnCSV(NavigationMenuTestData_PEExcel,navigationCustomMenuRecords)) {
				log(LogStatus.INFO, "Able to write record on csv "+navigationCustomMenuRecords , YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not Able to write record on csv "+navigationCustomMenuRecords, YesNo.Yes);
				sa.assertTrue(false, "Not Able to write record on csv "+navigationCustomMenuRecords);
			}
			refresh(driver);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, tabCustomObj, action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, tabCustomObj+" Pop Up open after clicking on "+navigationLabelValue , YesNo.No);

					} else {
						log(LogStatus.ERROR, tabCustomObj+" Pop Up should be open after clicking on "+navigationLabelValue, YesNo.Yes);
						sa.assertTrue(false, tabCustomObj+" Pop Up should be open after clicking on "+navigationLabelValue);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to create "+customMenu, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+customMenu);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc046_AddSomeThirPartyURLAndVerifyImpact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String navigationLabel=customMenu;

		String urlLabel=CSVLabel.URL.toString();
		String urlValue=googleUrlValue;
		String[][] labelWithValue= {{urlLabel,urlValue}};

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, urlValue+" value has been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.No);

//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, urlValue, CSVLabel.Navigation_Label.toString(), navigationLabel, urlLabel)) {
//				log(LogStatus.INFO, urlValue+" value has been written under "+urlLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel);
//			}
			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify url", YesNo.No);
					ThreadSleep(5000);
					String parentId=switchOnWindow(driver);
					if (parentId!=null) {
						log(LogStatus.ERROR, "New window is open after Click on "+navigationLabel+" so going to verify url", YesNo.Yes);
						String actualUrl = getURL(driver, 10);
						if (urlValue.contains(actualUrl)) {
							log(LogStatus.INFO, urlValue+" : Url Verified for : "+navigationLabel, YesNo.No);
						} else {
							log(LogStatus.ERROR, "Url Not Verified for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue, YesNo.Yes);
							sa.assertTrue(false,"Url Not Verified for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue);
					
						}
					driver.close();
					driver.switchTo().window(parentId);
					} else {
						log(LogStatus.ERROR, "No New window is open after Click on "+navigationLabel+" so cannot verify url", YesNo.Yes);
						sa.assertTrue(false,"No New window is open after Click on "+navigationLabel+" so cannot verify url");
					}
					

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify url", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify url");

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+navigationLabel);
			}
		} else {
			log(LogStatus.ERROR, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc047_RemoveURLActionObjectAndVerifyImpact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String navigationLabel=customMenu;
		
		String urlLabel=CSVLabel.URL.toString();
		String urlValue="";
		String actionObjectLabel=CSVLabel.Action_Object.toString();
		String actionObjectValue="";
		String[][] labelWithValue= {{urlLabel,urlValue},{actionObjectLabel,actionObjectValue}};

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, urlValue+" value has been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.No);

//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, urlValue, CSVLabel.Navigation_Label.toString(), navigationLabel, urlLabel)) {
//				log(LogStatus.INFO, urlValue+" value has been written under "+urlLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel);
//			}
//			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, actionObjectValue, CSVLabel.Navigation_Label.toString(), navigationLabel, actionObjectLabel)) {
//				log(LogStatus.INFO, actionObjectValue+" value has been written under "+actionObjectLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, actionObjectValue+" value has not been written under "+actionObjectLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, actionObjectValue+" value has not been written under "+actionObjectLabel+" for "+navigationLabel);
//			}
			
			refresh(driver);
			ThreadSleep(5000);
			urlValue=getURL(driver, 10);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify pop up", YesNo.No);
					ThreadSleep(5000);
					String actualUrl = getURL(driver, 10);
					if (urlValue.contains(actualUrl)) {
						log(LogStatus.INFO, urlValue+" : Url is same for : "+navigationLabel+" as nothing happens after removing value from url & action object", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Url Should not be changed for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue+" as nothing should happens after removing value from url & action object", YesNo.Yes);
						sa.assertTrue(false,"Url Should not be changed for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue+" as nothing should happens after removing value from url & action object");
				
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click label : "+navigationLabel);
			}
		} else {
			log(LogStatus.ERROR, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc048_FillListViewFieldsAndVerifyImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		
		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		
		String navigationLabel=customMenu;
		String listViewObjectLabel=CSVLabel.List_View_Object.toString();
		String listViewNameLabel=CSVLabel.List_View_Name.toString();
		String listViewObject=tabCustomObjAPIName;
		String listViewName=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.List_View_Name);
		String[][] navigationFieldWithValues= {{listViewObjectLabel,listViewObject},{listViewNameLabel,listViewName}};
		

		///////////////////////////////////////////////////////
		
		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, navigationFieldWithValues, 20)) {
			log(LogStatus.INFO, listViewObjectLabel+" and "+listViewNameLabel+" value has been updated & saved under for "+navigationLabel, YesNo.No);
			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewName, CSVLabel.Navigation_Label.toString(), navigationLabel, CSVLabel.List_View_Name.toString())) {
//				log(LogStatus.INFO, listViewName+" value has been written under "+CSVLabel.List_View_Name.toString()+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, listViewName+" value has not been written under "+CSVLabel.List_View_Name.toString()+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, listViewName+" value has not been written under "+CSVLabel.List_View_Name.toString()+" for "+navigationLabel);
//			}
//			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewObject, CSVLabel.Navigation_Label.toString(), navigationLabel, CSVLabel.List_View_Object.toString())) {
//				log(LogStatus.INFO, listViewObject+" value has been written under "+CSVLabel.List_View_Object.toString()+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, listViewObject+" value has not been written under "+CSVLabel.List_View_Object.toString()+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, listViewObject+" value has not been written under "+CSVLabel.List_View_Object.toString()+" for "+navigationLabel);
//			}
			refresh(driver);
			ThreadSleep(5000);
			String urlValue=getURL(driver, 10);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify home page of custom menu even after adding list view name & list view object different", YesNo.No);
					ThreadSleep(5000);
					// scn.nextLine();
					String afterClickUrl=getURL(driver, 10);
					if (urlValue.equalsIgnoreCase(afterClickUrl)) {
						log(LogStatus.INFO, "Nothing happen after adding list view name & list view object different for "+navigationLabel, YesNo.No);
							
					} else {
						log(LogStatus.ERROR, "Nothing should happen after adding list view name & list view object different for "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,"Nothing should happen after adding list view name & list view object different for "+navigationLabel);

					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify home page of custom menu even after adding list view name & list view object different", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify home page of custom menu even after adding list view name & list view object different");

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify home page of custom menu even after adding list view name & list view object different for label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify home page of custom menu even after adding list view name & list view object different for label : "+navigationLabel);
			}
		} else {
			log(LogStatus.ERROR, listViewObjectLabel+" and "+listViewNameLabel+" value has not been updated & saved under for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, listViewObjectLabel+" and "+listViewNameLabel+" value has not been updated & saved under for "+navigationLabel);

		}
		///////////////////////////////////////////////////

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc049_FillListViewFieldsAndVerifyImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		
		String dependentTC="Module3Tc034_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		
		String navigationLabel=customMenu;
		String listViewObjectLabel=CSVLabel.List_View_Object.toString();
		String listViewNameLabel=CSVLabel.List_View_Name.toString();
		String listViewObject=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.List_View_Object);
		String listViewName=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.List_View_Name);
		String[][] navigationFieldWithValues= {{listViewObjectLabel,listViewObject},{listViewNameLabel,listViewName}};
		

		///////////////////////////////////////////////////////
		
		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, navigationFieldWithValues, 20)) {
			log(LogStatus.INFO, listViewObjectLabel+" and "+listViewNameLabel+" value has been updated & saved under for "+navigationLabel, YesNo.No);
			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewName, CSVLabel.Navigation_Label.toString(), navigationLabel, CSVLabel.List_View_Name.toString())) {
//				log(LogStatus.INFO, listViewName+" value has been written under "+CSVLabel.List_View_Name.toString()+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, listViewName+" value has not been written under "+CSVLabel.List_View_Name.toString()+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, listViewName+" value has not been written under "+CSVLabel.List_View_Name.toString()+" for "+navigationLabel);
//			}
//			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, listViewObject, CSVLabel.Navigation_Label.toString(), navigationLabel, CSVLabel.List_View_Object.toString())) {
//				log(LogStatus.INFO, listViewObject+" value has been written under "+CSVLabel.List_View_Object.toString()+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, listViewObject+" value has not been written under "+CSVLabel.List_View_Object.toString()+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, listViewObject+" value has not been written under "+CSVLabel.List_View_Object.toString()+" for "+navigationLabel);
//			}
			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify List View of Fund even after adding list view name & list view object same object", YesNo.No);
					ThreadSleep(5000);
					boolean flag=npbl.isAutomationAllListViewForObject(projectName,"Funds", 30);
					if (flag) {
						log(LogStatus.INFO, "List View of Fund is available after clicking on "+navigationLabel , YesNo.No);
						
					} else {
						log(LogStatus.ERROR, "List View of Fund should be available after clicking on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false, "List View of Fund should be available after clicking on "+navigationLabel);
					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify List View of Fund even after adding list view name & list view object same object", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify List View of Fund even after adding list view name & list view object same object");

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify List View of Fund even after adding list view name & list view object same object for label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify List View of Fund even after adding list view name & list view object same object for label : "+navigationLabel);
			}
		} else {
			log(LogStatus.ERROR, listViewObjectLabel+" and "+listViewNameLabel+" value has not been updated & saved under for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, listViewObjectLabel+" and "+listViewNameLabel+" value has not been updated & saved under for "+navigationLabel);

		}
		///////////////////////////////////////////////////

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc050_CreateNewItemTaskOnNavigationTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String navigationLabel=CSVLabel.Navigation_Label.toString();
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String orderLabel=CSVLabel.Order.toString();
		String orderLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Updated_Order);
		String actionObjectLabel=CSVLabel.Action_Object.toString();
		String actionObjecValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Action_Object);

		String navigationTaskRecords=navigationLabelValue+","+orderLabelValue+",,"+actionObjecValue+",,,";
		String[][] labelWithValue= {{navigationLabel,navigationLabelValue},{orderLabel,orderLabelValue},{actionObjectLabel,actionObjecValue}};
		WebElement ele;
		if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
			log(LogStatus.INFO, "created "+navigationLabelValue, YesNo.No);

			if (ExcelUtils.writeOneRecordOnCSV(NavigationMenuTestData_PEExcel,navigationTaskRecords)) {
				log(LogStatus.INFO, "Able to write record on csv "+navigationTaskRecords , YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not Able to write record on csv "+navigationTaskRecords, YesNo.Yes);
				sa.assertTrue(false, "Not Able to write record on csv "+navigationTaskRecords);
			}
			refresh(driver);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, navigationLabelValue, action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, navigationLabelValue+" Pop Up open after clicking on "+navigationLabelValue , YesNo.No);

					} else {
						log(LogStatus.ERROR, navigationLabelValue+" Pop Up should be open after clicking on "+navigationLabelValue, YesNo.Yes);
						sa.assertTrue(false, navigationLabelValue+" Pop Up should be open after clicking on "+navigationLabelValue);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to create "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+navigationLabelValue);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc051_CreateNewItemsOnNavigationPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		//////////////////////////////////////////////////
		
		String AllParent=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Parent);
		String parentOrder = "70";
		String all = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		
		String[] AllChilds=all.split(breakSP);
		String child="";
		String childOrder="";
		
		String[] actionObject= {"Account","navpeII__Fund__c","Task"};
		
		if (npbl.createNavigationItem(projectName, new String[][]{{CSVLabel.Navigation_Label.toString(),AllParent},{CSVLabel.Order.toString(),parentOrder}}, 20)) {
			log(LogStatus.INFO, "created "+AllParent, YesNo.No);
			String createNewRecords=AllParent+","+parentOrder+",,,,,";
			if (ExcelUtils.writeOneRecordOnCSV(NavigationMenuTestData_PEExcel,createNewRecords)) {
				log(LogStatus.INFO, "Able to write record on csv "+createNewRecords , YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not Able to write record on csv "+createNewRecords, YesNo.Yes);
				sa.assertTrue(false, "Not Able to write record on csv "+createNewRecords);
			}
			
			for (int i = 0; i < AllChilds.length; i++) {
				refresh(driver);
				ThreadSleep(5000);
				child=AllChilds[i];
				childOrder=String.valueOf(i+7);
				if (npbl.createNavigationItem(projectName, new String[][]{{CSVLabel.Navigation_Label.toString(),child},{CSVLabel.Parent.toString(),AllParent},{CSVLabel.Order.toString(),childOrder},{CSVLabel.Action_Object.toString(),actionObject[i]}}, 20)) {
					log(LogStatus.INFO, "created "+child, YesNo.No);

					String childRecords=child+","+childOrder+","+AllParent+","+actionObject[i]+",,,";
					if (ExcelUtils.writeOneRecordOnCSV(NavigationMenuTestData_PEExcel,childRecords)) {
						log(LogStatus.INFO, "Able to write record on csv "+childRecords , YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not Able to write record on csv "+childRecords, YesNo.Yes);
						sa.assertTrue(false, "Not Able to write record on csv "+childRecords);
					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to create "+child, YesNo.Yes);
					sa.assertTrue(false, "Not Able to create "+child);

				}
			}

		} else {
			log(LogStatus.ERROR, "Not Able to create "+AllParent+" so cannot create childs : "+all, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+AllParent+" so cannot create childs : "+all);

		}
		Map<String,String> parentWithChild = new LinkedHashMap<String,String>(); 
		all=all.replace(breakSP, commaSP);
		parentWithChild.put(AllParent,all);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			npbl.verifyingNavigationMenuLink(projectName, null, parentWithChild, action.BOOLEAN, 0);;
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc052_AddSomeThirPartyURLAndVerifyImpact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String dependentTC="Module3Tc050_CreateNewItemTaskOnNavigationTab";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);;

		String urlLabel=CSVLabel.URL.toString();
		String urlValue=googleUrlValue;
		String[][] labelWithValue= {{urlLabel,urlValue}};

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, urlValue+" value has been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.No);

//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, urlValue, CSVLabel.Navigation_Label.toString(), navigationLabel, urlLabel)) {
//				log(LogStatus.INFO, urlValue+" value has been written under "+urlLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel);
//			}
			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify url", YesNo.No);
					ThreadSleep(5000);
					String parentId=switchOnWindow(driver);
					if (parentId!=null) {
						log(LogStatus.ERROR, "New window is open after Click on "+navigationLabel+" so going to verify url", YesNo.Yes);
						String actualUrl = getURL(driver, 10);
						if (urlValue.contains(actualUrl)) {
							log(LogStatus.INFO, urlValue+" : Url Verified for : "+navigationLabel, YesNo.No);
						} else {
							log(LogStatus.ERROR, "Url Not Verified for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue, YesNo.Yes);
							sa.assertTrue(false,"Url Not Verified for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue);
					
						}
					driver.close();
					driver.switchTo().window(parentId);
					} else {
						log(LogStatus.ERROR, "No New window is open after Click on "+navigationLabel+" so cannot verify url", YesNo.Yes);
						sa.assertTrue(false,"No New window is open after Click on "+navigationLabel+" so cannot verify url");
					}
					

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot verify url", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot verify url");

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify url for label : "+navigationLabel);
			}
		} else {
			log(LogStatus.ERROR, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc053_RemoveURLActionObjectAndVerifyImpact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String dependentTC="Module3Tc050_CreateNewItemTaskOnNavigationTab";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);;

		
		String urlLabel=CSVLabel.URL.toString();
		String urlValue="";
		String actionObjectLabel=CSVLabel.Action_Object.toString();
		String actionObjectValue="";
		String[][] labelWithValue= {{urlLabel,urlValue},{actionObjectLabel,actionObjectValue}};

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, urlValue+" value has been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.No);

//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, urlValue, CSVLabel.Navigation_Label.toString(), navigationLabel, urlLabel)) {
//				log(LogStatus.INFO, urlValue+" value has been written under "+urlLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, urlValue+" value has not been written under "+urlLabel+" for "+navigationLabel);
//			}
//			
//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, actionObjectValue, CSVLabel.Navigation_Label.toString(), navigationLabel, actionObjectLabel)) {
//				log(LogStatus.INFO, actionObjectValue+" value has been written under "+actionObjectLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, actionObjectValue+" value has not been written under "+actionObjectLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, actionObjectValue+" value has not been written under "+actionObjectLabel+" for "+navigationLabel);
//			}
			
			refresh(driver);
			ThreadSleep(5000);
			urlValue=getURL(driver, 10);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel+" going to verify pop up", YesNo.No);
					ThreadSleep(5000);
					String actualUrl = getURL(driver, 10);
					if (urlValue.contains(actualUrl)) {
						log(LogStatus.INFO, urlValue+" : Url is same for : "+navigationLabel+" as nothing happens after removing value from url & action object", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Url Should not be changed for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue+" as nothing should happens after removing value from url & action object", YesNo.Yes);
						sa.assertTrue(false,"Url Should not be changed for : "+navigationLabel+" Actual : "+actualUrl+"\t Expected : "+urlValue+" as nothing should happens after removing value from url & action object");
				
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click label : "+navigationLabel);
			}
		} else {
			log(LogStatus.ERROR, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, urlValue+" value has not been updated & saved under "+urlLabel+" for "+navigationLabel);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc054_CreateRecordsAfterNavigationThroughNavigationMenu(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String[] navigationLabels=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name).split("<break>");
		String navigationLabel="";
		for (int i = 0; i < navigationLabels.length; i++) {
			navigationLabel=navigationLabels[i];
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+navigationLabel, YesNo.No);	

					if (i==0) {
						Smoke_CDINS1RecordType="Company";
						Smoke_CDINS1Name="AccountFirm";
						if (ip.createEntityOrAccountPopUp(projectName, Smoke_CDINS1Name, Smoke_CDINS1RecordType, null, 10)) {
							log(LogStatus.INFO,"successfully Created Account/Entity : "+Smoke_CDINS1Name+" of record type : "+Smoke_CDINS1RecordType,YesNo.No);	
						} else {
							sa.assertTrue(false,"Not Able to Create Account/Entity : "+Smoke_CDINS1Name+" of record type : "+Smoke_CDINS1RecordType);
							log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+Smoke_CDINS1Name+" of record type : "+Smoke_CDINS1RecordType,YesNo.Yes);
						}
					} else if(i==1) {
						ToggleFund1Type="Fund of Funds";
						ToggleFund1="";
						ToggleFund1RecordType="Fund of Funds";
						ToggleFund1Category="Fund";
						String[] funds = {ToggleFund1,ToggleFund1Type,ToggleFund1Category,ToggleFund1RecordType};
						if (fp.createFundPEPopUp(projectName, funds[0], funds[3], funds[1], funds[2], null, 15)) {
							log(LogStatus.INFO,"Created Fund : "+funds[0],YesNo.No);	
						} else {
							sa.assertTrue(false,"Not Able to Create Fund : "+funds[0]);
							log(LogStatus.SKIP,"Not Able to Create Fund  : "+funds[0],YesNo.Yes);
						}
					}else {
						TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
						String taskSubject="Task Subject";
						if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.NewTaskPopUP, taskSubject, null, action.SCROLLANDBOOLEAN, 10)) {
							log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
							ThreadSleep(1000);
							if (clickUsingJavaScript(driver, tp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully created task",  YesNo.Yes);

							}else {
								log(LogStatus.ERROR, "Save Button is not visible so task could not be created", YesNo.Yes);
								sa.assertTrue(false,"Save Button is not visible so task could not be created" );
							}


						}else {
							log(LogStatus.ERROR, "Subject textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"Subject textbox is not visible so task could not be created" );
						}
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel+" so cannot create Record for the same", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel+" so cannot create Record for the same");

				}

			
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click "+navigationLabel);
			}
			ThreadSleep(5000);
			refresh(driver);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc055_MakeParentItemAsChildOfOtherItem(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String updatedParentLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Parent);

		String parentLabel=CSVLabel.Parent.toString();
		String parentValue=updatedParentLabel;
		String[][] labelWithValue= {{parentLabel,parentValue}};

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, parentValue+" value has been updated & saved under "+parentLabel+" for "+navigationLabel, YesNo.No);

//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, parentValue, CSVLabel.Navigation_Label.toString(), navigationLabel, parentLabel)) {
//				log(LogStatus.INFO, parentValue+" value has been written under "+parentLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, parentValue+" value has not been written under "+parentLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, parentValue+" value has not been written under "+parentLabel+" for "+navigationLabel);
//			}

			ThreadSleep(5000);
			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);

				ele=npbl.getNavigationLabel(projectName, parentValue+"/"+navigationLabel, action.BOOLEAN, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "Child Label "+navigationLabel+" with Parent verified "+parentValue, YesNo.No);	
				} else {
					log(LogStatus.ERROR, "Child Label "+navigationLabel+" with Parent not verified "+parentValue, YesNo.Yes);
					sa.assertTrue(false,"Child Label "+navigationLabel+" with Parent not verified "+parentValue);

				}


			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify child label for label : "+parentValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify child label for label : "+parentValue);
			}
		} else {
			log(LogStatus.ERROR, parentValue+" value has not been updated & saved under "+parentLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, parentValue+" value has not been updated & saved under "+parentLabel+" for "+navigationLabel);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc056_MakeChildItemAsAParentItem(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String dependenTC="Module3Tc055_MakeParentItemAsChildOfOtherItem";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependenTC, excelLabel.Navigation_Label_Name);
		String updatedParentLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependenTC, excelLabel.Parent);

		String parentLabel=CSVLabel.Parent.toString();
		String parentValue="";
		String[][] labelWithValue= {{parentLabel,parentValue}};

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, parentValue+" value has been updated & saved under "+parentLabel+" for "+navigationLabel, YesNo.No);

//			if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, parentValue, CSVLabel.Navigation_Label.toString(), navigationLabel, parentLabel)) {
//				log(LogStatus.INFO, parentValue+" value has been written under "+parentLabel+" for "+navigationLabel, YesNo.No);
//			} else {
//				log(LogStatus.ERROR, parentValue+" value has not been written under "+parentLabel+" for "+navigationLabel, YesNo.Yes);
//				sa.assertTrue(false, parentValue+" value has not been written under "+parentLabel+" for "+navigationLabel);
//			}
			ThreadSleep(5000);
			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);

				ele=npbl.getNavigationLabel(projectName, updatedParentLabel+"/"+navigationLabel, action.BOOLEAN, 10);
				if (ele==null) {
					log(LogStatus.INFO, "Child Label "+navigationLabel+" is not under  "+updatedParentLabel+" hence verified", YesNo.No);	
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, navigationLabel+"  start appearing at first level ", YesNo.No);	
					} else {
						log(LogStatus.ERROR, navigationLabel+"  should be at first level ", YesNo.Yes);
						sa.assertTrue(false,navigationLabel+"  should be at first level ");
					}
				} else {
					log(LogStatus.ERROR, "Child Label "+navigationLabel+" should not be under  "+updatedParentLabel+" hence not verified", YesNo.Yes);
					sa.assertTrue(false,"Child Label "+navigationLabel+" should not be under  "+updatedParentLabel+" hence not verified");

				}


			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify child label for label : "+parentValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify child label for label : "+parentValue);
			}
		} else {
			log(LogStatus.ERROR, parentValue+" value has not been updated & saved under "+parentLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, parentValue+" value has not been updated & saved under "+parentLabel+" for "+navigationLabel);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc057_DeleteCreateNewItemFromNavigationPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag=true;
		WebElement ele;
		String[] navigationLabels=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name).split(breakSP);

		String navigationLabel;
		navigationLabel=navigationLabels[0];
		if (npbl.clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);

			if (npbl.clickOnAlreadyCreatedItem(projectName, navigationLabel, true, 15)) {
				log(LogStatus.INFO,"Item found: "+navigationLabel+" on Tab : "+navigationTab, YesNo.No);

				npbl.clickOnShowMoreDropdownOnly(projectName);
				ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Delete, 10);

				if (click(driver, ele, ShowMoreActionDropDownList.Delete.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on delete Button : "+navigationLabel, YesNo.No);

					if(click(driver,npbl.getDeleteButtonPopUp(projectName, 10), "delete", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on delete button on delete popup : "+TabName.Object2Tab+" For : "+navigationLabel,YesNo.No); 
						ThreadSleep(5000);
						flag=true;
						
						for (int i = 1; i < navigationLabels.length; i++) {
							navigationLabel=navigationLabels[i];
							if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, "", CSVLabel.Navigation_Label.toString(), navigationLabel, CSVLabel.Parent.toString())) {
								log(LogStatus.INFO, " value has been written under "+CSVLabel.Parent.toString()+" for "+navigationLabel, YesNo.No);
							} else {
								log(LogStatus.ERROR, " value has not been written under "+CSVLabel.Parent.toString()+" for "+navigationLabel, YesNo.Yes);
								sa.assertTrue(false," value has not been written under "+CSVLabel.Parent.toString()+" for "+navigationLabel);
							}
							
						}
					}else {
						sa.assertTrue(false,"Not Able to Select delete button for "+navigationLabel);
						log(LogStatus.SKIP,"Not Able to Select delete button for "+navigationLabel,YesNo.Yes);

					}
				}

				else {
					log(LogStatus.ERROR, "Not Able to Click on delete Button : "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on delete Button : "+navigationLabel);
				}


			}else {

				log(LogStatus.ERROR,"Item not found: "+navigationLabel+" on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Item not found: "+navigationLabel+" on Tab : "+navigationTab);
			}


		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
		}

		if (flag) {
			navigationLabel=navigationLabels[0];
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				for (int i = 1; i < navigationLabels.length; i++) {
					
					ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 0);
					if (ele==null) {
						log(LogStatus.INFO, navigationLabel+" is not present after delete", YesNo.No);	
					} else {
						log(LogStatus.ERROR, navigationLabel+" is not present after delete", YesNo.Yes);
						sa.assertTrue(false,navigationLabel+" is not present after delete");
					}

					ele=npbl.getNavigationLabel(projectName, navigationLabel+"/"+navigationLabels[i], action.BOOLEAN, 0);
					if (ele==null) {
						log(LogStatus.INFO, "Child Label "+navigationLabels[i]+" is not under Parent "+navigationLabel+" after delete", YesNo.No);	
					} else {
						log(LogStatus.ERROR, "Child Label "+navigationLabels[i]+" should not be under Parent "+navigationLabel+" after delete", YesNo.Yes);
						sa.assertTrue(false,"Child Label "+navigationLabels[i]+" should not be under Parent "+navigationLabel+" after delete");

					}
					
					ele=npbl.getNavigationLabel(projectName, navigationLabels[i], action.BOOLEAN, 0);
					if (ele!=null) {
						log(LogStatus.INFO, navigationLabels[i]+" is present at first level after deleting "+navigationLabel, YesNo.No);	
					} else {
						log(LogStatus.ERROR, navigationLabels[i]+" should be present at first level after deleting "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false,navigationLabels[i]+" should be present at first level after deleting "+navigationLabel);
					}
					
					
				}
				

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify label", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify label");
			}

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc058_Create20ItemsUnderAParentItemAndVerfiyNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String AllParent=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Parent);
		String parentOrder="91";
		String all = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		
		String[] AllChilds=all.split(breakSP);
		String child="";
		if (npbl.createNavigationItem(projectName, new String[][]{{CSVLabel.Navigation_Label.toString(),AllParent},{CSVLabel.Order.toString(),parentOrder}}, 20)) {
			log(LogStatus.INFO, "created "+AllParent, YesNo.No);

			String createNewRecords=AllParent+","+parentOrder+",,,,,,";
			if (ExcelUtils.writeOneRecordOnCSV(NavigationMenuTestData_PEExcel,createNewRecords)) {
				log(LogStatus.INFO, "Able to write record on csv "+createNewRecords , YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not Able to write record on csv "+createNewRecords, YesNo.Yes);
				sa.assertTrue(false, "Not Able to write record on csv "+createNewRecords);
			}
			
			for (int i = 0; i < AllChilds.length; i++) {
				child=AllChilds[i];
				ThreadSleep(5000);
				refresh(driver);
				ThreadSleep(5000);
				String childOrder = String.valueOf(i);
				if (npbl.createNavigationItem(projectName, new String[][]{{CSVLabel.Navigation_Label.toString(),child},{CSVLabel.Parent.toString(),AllParent},{CSVLabel.Order.toString(),childOrder}}, 20)) {
					log(LogStatus.INFO, "created "+child, YesNo.No);

					String childRecords=child+","+childOrder+","+AllParent+",,,,,";
					if (ExcelUtils.writeOneRecordOnCSV(NavigationMenuTestData_PEExcel,childRecords)) {
						log(LogStatus.INFO, "Able to write record on csv "+childRecords , YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not Able to write record on csv "+childRecords, YesNo.Yes);
						sa.assertTrue(false, "Not Able to write record on csv "+childRecords);
					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to create "+child, YesNo.Yes);
					sa.assertTrue(false, "Not Able to create "+child);

				}
			}

		} else {
			log(LogStatus.ERROR, "Not Able to create "+AllParent+" so cannot create childs : "+all, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+AllParent+" so cannot create childs : "+all);

		}
		Map<String,String> parentWithChild = new LinkedHashMap<String,String>(); 
		all=all.replace(breakSP, commaSP);
		parentWithChild.put(AllParent,all);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			npbl.verifyingNavigationMenuLink(projectName, null, parentWithChild, action.BOOLEAN, 0);;
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc059_UpdateNavigationMenuLabelIconAndVerify(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);

		String attachmentPath= System.getProperty("user.dir")+"\\UploadFiles\\Module 3\\TC059\\";
		String imgName="EdgeIcon.JPG";
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to update Navigation Label", YesNo.No);
				ThreadSleep(500);

				if(setup.searchStandardOrCustomObject(environment,mode, object.App_Manager)) {
					log(LogStatus.INFO, "click on Object : "+object.App_Manager, YesNo.No);
					ThreadSleep(2000);
					if(setup.clickOnEditForApp(driver, appName, AppDeveloperName,AppDescription, 10)) {
						log(LogStatus.INFO,"able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription, YesNo.No);
						ThreadSleep(1000);

						//Icon
						if (setup.updateEdgeIcon(projectName, attachmentPath, imgName)) {
							log(LogStatus.INFO,"File uploaded successfully "+imgName,YesNo.No);
						} else {
							sa.assertTrue(false, "File not uploaded "+imgName);
							log(LogStatus.FAIL,"File not uploaded "+imgName,YesNo.Yes);
						}
						
						//Utlity Items
						if (setup.clickOnAppSettingList(driver, AppSetting.Utility_Items, 10)) {
							log(LogStatus.INFO, "able to click on "+AppSetting.Utility_Items, YesNo.No);
							ThreadSleep(500);
							if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Label.toString(), action.BOOLEAN, 10),navatarEdge,PageLabel.Label.toString()+" textbox value : "+navatarEdge,action.BOOLEAN)) {
								ThreadSleep(500);
								log(LogStatus.INFO,"send value to "+PageLabel.Label.toString()+" textbox value : "+navatarEdge,YesNo.No);
								if (click(driver, setup.getCustomTabSaveBtn(projectName, 10)," Save Button", action.BOOLEAN)) {
									log(LogStatus.INFO,"Click on Save Button",YesNo.No);
									ThreadSleep(5000);			
								} else {
									sa.assertTrue(false, "Not Able to Click on Save Button");
									log(LogStatus.FAIL,"Not Able to Click on Save Button",YesNo.Yes);
								}

							} else {
								sa.assertTrue(false, "Not Able to send value to "+PageLabel.Label.toString()+" textbox value : "+navatarEdge);
								log(LogStatus.FAIL,"Not Able to send value to "+PageLabel.Label.toString()+" textbox value : "+navatarEdge,YesNo.Yes);
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
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot update Navigation Label", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot update Navigation Label");
			}

		}
		refresh(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge+" hence updated", YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" hence not updated", YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" hence not updated");
		}
		
		WebElement ele = setup.getHeaderImg(projectName, 30);
		String style="";
		
		if (ele!=null) {
			log(LogStatus.INFO, "Updated Image found "+imgName, YesNo.No);	
			style=ele.getAttribute("style");
			log(LogStatus.INFO, "Updated Image Icon Style "+style, YesNo.No);	
			String expectedImg=imgName.substring(0, 7);
			System.err.println(">>>>>>>> "+expectedImg);
			if (style.contains(expectedImg)) {
				log(LogStatus.INFO, "Updated Image Icon verified "+imgName, YesNo.No);	
			} else {
				log(LogStatus.FAIL, "Updated Image Icon not verified "+imgName, YesNo.Yes);
				sa.assertTrue(false, "Updated Image Icon not verified "+imgName);
			}
		} else {
			log(LogStatus.FAIL, "Updated Image Icon not found "+imgName, YesNo.Yes);
			sa.assertTrue(false, "Updated Image Icon not found "+imgName);
	
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc061_VerifyThatBulkEmailpageFromNavigationmenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String navigationLabel=CSVLabel.Navigation_Label.toString();
		String navigationLabelValue=NavatarQuickLink.BulkEmail.toString();
		String urlLabel=CSVLabel.URL.toString();
		String urlLabelValue="";
		WebElement ele;

		if (hp.clickOnLinkFromNavatarQuickLink(environment, mode, NavatarQuickLink.BulkEmail)) {
			log(LogStatus.INFO, "Clicked On Bulk Email Link with Navatar Quick Link", YesNo.No);
			switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(20));
			urlLabelValue=getURL(driver, 10);
			if (hp.verifyLandingPageAfterClickingOnNavatarSetUpPage(environment, mode, NavatarQuickLink.BulkEmail)) {
				log(LogStatus.PASS, "Landing Page Verified for Bulk Email", YesNo.No);
			} else {
				sa.assertTrue(false, "Landing Page Not Verified for Bulk Email");
				log(LogStatus.FAIL, "Landing Page Not Verified for Bulk Email", YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false, "Not Able to Click On Bulk Email Link with Navatar Quick Link");
			log(LogStatus.SKIP, "Not Able to Click On Bulk Email Link with Navatar Quick Link", YesNo.Yes);
		}


		String[][] labelWithValue= {{navigationLabel,navigationLabelValue},{urlLabel,urlLabelValue}};
		if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
			log(LogStatus.INFO, "created "+navigationLabelValue, YesNo.No);

			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					switchToFrame(driver, 30, hp.getCreateFundraisingsFrame_Lighting(20));
					if (hp.verifyLandingPageAfterClickingOnNavatarSetUpPage(environment, mode, NavatarQuickLink.BulkEmail)) {
						log(LogStatus.PASS, "Landing Page Verified for Bulk Email", YesNo.No);
					} else {
						sa.assertTrue(false, "Landing Page Not Verified for Bulk Email");
						log(LogStatus.FAIL, "Landing Page Not Verified for Bulk Email", YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to create "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+navigationLabelValue);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc063_1_RemoveTheRecordCreationRightForStandardUser(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);

		String[] userNames= {"PE Standard User"};
		String onObject="Accounts";
		String permission="Create";
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
						if (setup.permissionChangeForUserONObject(driver, userName, new String[][]{{onObject,permission}}, 20)) {
							log(LogStatus.PASS,permission+ " permission change for "+userName+" on object "+onObject,YesNo.No);
						} else {
							sa.assertTrue(false, permission+ " permission not change for "+userName+" on object "+onObject);
							log(LogStatus.FAIL,permission+ " permission not change for "+userName+" on object "+onObject,YesNo.Yes);
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
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc063_2_RemoveTheRecordCreationRightForStandardUser(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			
			ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
			if (ele==null) {
				
				log(LogStatus.INFO, navigationLabel+" is not present after removing creation access", YesNo.No);
				ThreadSleep(5000);
			} else {
				log(LogStatus.ERROR, navigationLabel+" should not present after removing creation access", YesNo.Yes);
				sa.assertTrue(false,navigationLabel+" should not present after removing creation access");

			}
			
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot verify label : "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot verify label : "+navigationLabel);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc064_SetTheUserIndustryAsEnergy(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);

		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to Set the User Industry as "+energyCoverage, YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Users)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);

					if(setup.clickOnEditBtnForCRMUser(driver, crmUser1LastName, crmUser1FirstName, 20)) {
						log(LogStatus.INFO, "Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver, setup.getIndustryDropdownList(10), "Industry DropDown List",energyCoverage)) {
							log(LogStatus.INFO, "selected visbible text from the Industry dropdown "+energyCoverage, YesNo.No);
							ThreadSleep(2000);

							if (click(driver, setup.getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
								scrollDownThroughWebelement(driver, setup.getIndustryDropdownList("Industry", energyCoverage, 10), energyCoverage);
								if (setup.getIndustryDropdownList("Industry", energyCoverage, 10)!=null) {
									log(LogStatus.INFO, "Industry Value verified "+energyCoverage, YesNo.No);
								} else {
									log(LogStatus.ERROR, "Industry Value not verified "+energyCoverage, YesNo.Yes);
									sa.assertTrue(false, "Industry Value not verified "+energyCoverage);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to select visbible text from the Industry dropdown "+energyCoverage, YesNo.Yes);
							sa.assertTrue(false, "Not able to select visbible text from the Industry dropdown "+energyCoverage);
						}


					}else {
						log(LogStatus.ERROR, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Users, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Users);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot Set the User Industry as "+energyCoverage, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot Set the User Industry as "+energyCoverage);
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc065_CreateMyIndustryMenuItem(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String navigationLabel=CSVLabel.Navigation_Label.toString();
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);

		String orderLabel=CSVLabel.Order.toString();
		String orderLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Updated_Order);
		String urlObjectLabel=CSVLabel.URL.toString();
		String urlObjecValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.URL);

		String[][] labelWithValue= {{navigationLabel,navigationLabelValue},{orderLabel,orderLabelValue},{urlObjectLabel,urlObjecValue}};
		WebElement ele;
		if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
			log(LogStatus.INFO, "created "+customMenu, YesNo.No);
			refresh(driver);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getPageDoesNotExist(projectName, action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" verified " , YesNo.No);

					} else {
						log(LogStatus.ERROR, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ", YesNo.Yes);
						sa.assertTrue(false, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ");
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to create "+customMenu, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+customMenu);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc066_CreateRecordOnCoverageObjectAndVerifyMyIndustrylink(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CoveragePageBusinessLayer cp = new CoveragePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String[][] industryCoverages = {{energyCoverage,energyCoverageRecordType},{healthCareCoverage,healthCareCoverageRecordType},
				{manufacturingCoverage,manufacturingCoverageRecordType},{businessServicesCoverage,businessServicesCoverageRecordType}
				,{pharmaCoverage,pharmaCoverageRecordType}};

		String coverageName="";
		String coverageRecordType="";
		for (String[] industryCov : industryCoverages) {
			coverageName=industryCov[0];
			coverageRecordType=industryCov[1];
			if (cp.clickOnTab(projectName, tabObj8Coverage)) {
				log(LogStatus.INFO, "Click on Tab : "+tabObj8Coverage, YesNo.No);

				if (cp.createCoverage(projectName, coverageRecordType, coverageName)) {
					log(LogStatus.INFO, "created/verified "+coverageName+" of type "+coverageRecordType, YesNo.No);
				} else {
					log(LogStatus.ERROR, "cannot create "+coverageName+" of type "+coverageRecordType, YesNo.Yes);
					sa.assertTrue(false,"cannot create "+coverageName+" of type "+coverageRecordType);

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+tabObj8Coverage+" so cannot create "+coverageName+" of type "+coverageRecordType, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj8Coverage+" so cannot create "+coverageName+" of type "+coverageRecordType);
			}

		}
		
		refresh(driver);
		cp.clickOnTab(projectName, homeTab);
		String dependentTC="Module3Tc065_CreateMyIndustryMenuItem";
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);
				if (npbl.getCoverageTabAfterClick(projectName, energyCoverage, action.SCROLLANDBOOLEAN, 20)!=null) {
					log(LogStatus.INFO, energyCoverage+" is open after click on "+navigationLabelValue, YesNo.No);
				} else {
					log(LogStatus.ERROR, energyCoverage+" should be open after click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,energyCoverage+" should be open after click on "+navigationLabelValue);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc067_1_ChangetheUserIndustryAsHealthCareAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag=false;
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to Set the User Industry as "+healthCareCoverage, YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Users)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);

					if(setup.clickOnEditBtnForCRMUser(driver, crmUser1LastName, crmUser1FirstName, 20)) {
						log(LogStatus.INFO, "Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver, setup.getIndustryDropdownList(10), "Industry DropDown List",healthCareCoverage)) {
							log(LogStatus.INFO, "selected visbible text from the Industry dropdown "+healthCareCoverage, YesNo.No);
							ThreadSleep(2000);

							if (click(driver, setup.getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
								scrollDownThroughWebelement(driver, setup.getIndustryDropdownList("Industry", healthCareCoverage, 10), healthCareCoverage);
								if (setup.getIndustryDropdownList("Industry", healthCareCoverage, 10)!=null) {
									log(LogStatus.INFO, "Industry Value verified "+healthCareCoverage, YesNo.No);
									flag=true;
								} else {
									log(LogStatus.ERROR, "Industry Value not verified "+healthCareCoverage, YesNo.Yes);
									sa.assertTrue(false, "Industry Value not verified "+healthCareCoverage);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to select visbible text from the Industry dropdown "+healthCareCoverage, YesNo.Yes);
							sa.assertTrue(false, "Not able to select visbible text from the Industry dropdown "+healthCareCoverage);
						}


					}else {
						log(LogStatus.ERROR, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Users, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Users);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot Set the User Industry as "+healthCareCoverage, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot Set the User Industry as "+healthCareCoverage);
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}
		flag=false;
		if (flag) {
			refresh(driver);
			lp.clickOnTab(projectName, homeTab);
			String dependentTC="Module3Tc065_CreateMyIndustryMenuItem";
			String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
			NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
				WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					if (npbl.getCoverageTabAfterClick(projectName, healthCareCoverage, action.SCROLLANDBOOLEAN, 20)!=null) {
						log(LogStatus.INFO, healthCareCoverage+" is open after click on "+navigationLabelValue, YesNo.No);
					} else {
						log(LogStatus.ERROR, healthCareCoverage+" should be open after click on "+navigationLabelValue, YesNo.Yes);
						sa.assertTrue(false,healthCareCoverage+" should be open after click on "+navigationLabelValue);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
			}	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc067_2_ChangetheUserIndustryAsHealthCareAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String dependentTC="Module3Tc065_CreateMyIndustryMenuItem";
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);
				if (npbl.getCoverageTabAfterClick(projectName, healthCareCoverage, action.SCROLLANDBOOLEAN, 20)!=null) {
					log(LogStatus.INFO, healthCareCoverage+" is open after click on "+navigationLabelValue, YesNo.No);
				} else {
					log(LogStatus.ERROR, healthCareCoverage+" should be open after click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,healthCareCoverage+" should be open after click on "+navigationLabelValue);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc068_1_ChangetheUserIndustryAsTechnologyAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag=false;
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to Set the User Industry as "+TechonlogyCoverage, YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Users)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);

					if(setup.clickOnEditBtnForCRMUser(driver, crmUser1LastName, crmUser1FirstName, 20)) {
						log(LogStatus.INFO, "Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver, setup.getIndustryDropdownList(10), "Industry DropDown List",TechonlogyCoverage)) {
							log(LogStatus.INFO, "selected visbible text from the Industry dropdown "+TechonlogyCoverage, YesNo.No);
							ThreadSleep(2000);

							if (click(driver, setup.getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
								scrollDownThroughWebelement(driver, setup.getIndustryDropdownList("Industry", TechonlogyCoverage, 10), TechonlogyCoverage);
								if (setup.getIndustryDropdownList("Industry", TechonlogyCoverage, 10)!=null) {
									log(LogStatus.INFO, "Industry Value verified "+TechonlogyCoverage, YesNo.No);
									flag=true;
								} else {
									log(LogStatus.ERROR, "Industry Value not verified "+TechonlogyCoverage, YesNo.Yes);
									sa.assertTrue(false, "Industry Value not verified "+TechonlogyCoverage);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to select visbible text from the Industry dropdown "+TechonlogyCoverage, YesNo.Yes);
							sa.assertTrue(false, "Not able to select visbible text from the Industry dropdown "+TechonlogyCoverage);
						}


					}else {
						log(LogStatus.ERROR, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Users, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Users);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot Set the User Industry as "+TechonlogyCoverage, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot Set the User Industry as "+TechonlogyCoverage);
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}

		if (flag) {
			refresh(driver);
			String dependentTC="Module3Tc065_CreateMyIndustryMenuItem";
			String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
			NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
				WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getPageDoesNotExist(projectName, action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" verified " , YesNo.No);
					} else {
						log(LogStatus.ERROR, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ", YesNo.Yes);
						sa.assertTrue(false, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ");
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
			}	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc068_2_ChangetheUserIndustryAsTechnologyAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String dependentTC="Module3Tc065_CreateMyIndustryMenuItem";
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);
				ele=npbl.getPageDoesNotExist(projectName, action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" verified " , YesNo.No);
				} else {
					log(LogStatus.ERROR, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ", YesNo.Yes);
					sa.assertTrue(false, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ");
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc069_1_ChangetheUserIndustryAsNoneAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		none="--"+none+"--";
		boolean flag=false;
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to Set the User Industry as "+TechonlogyCoverage, YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Users)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);

					if(setup.clickOnEditBtnForCRMUser(driver, crmUser1LastName, crmUser1FirstName, 20)) {
						log(LogStatus.INFO, "Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver, setup.getIndustryDropdownList(10), "Industry DropDown List",none)) {
							log(LogStatus.INFO, "selected visbible text from the Industry dropdown "+none, YesNo.No);
							ThreadSleep(2000);

							if (click(driver, setup.getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
								scrollDownThroughWebelement(driver, setup.getIndustryDropdownList("Industry", none, 10), none);
								if (setup.getIndustryDropdownList("Industry", none, 10)!=null) {
									log(LogStatus.INFO, "Industry Value verified "+none, YesNo.No);
									flag=true;
								} else {
									log(LogStatus.ERROR, "Industry Value not verified "+none, YesNo.Yes);
									sa.assertTrue(false, "Industry Value not verified "+none);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to select visbible text from the Industry dropdown "+none, YesNo.Yes);
							sa.assertTrue(false, "Not able to select visbible text from the Industry dropdown "+none);
						}


					}else {
						log(LogStatus.ERROR, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Users, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Users);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot Set the User Industry as "+none, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot Set the User Industry as "+none);
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}

		if (flag) {
			refresh(driver);
			String dependentTC="Module3Tc065_CreateMyIndustryMenuItem";
			String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
			NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
				WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getPageDoesNotExist(projectName, action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" verified " , YesNo.No);
					} else {
						log(LogStatus.ERROR, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ", YesNo.Yes);
						sa.assertTrue(false, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ");
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
			}	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc069_2_ChangetheUserIndustryAsNoneAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String dependentTC="Module3Tc065_CreateMyIndustryMenuItem";
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);
				ele=npbl.getPageDoesNotExist(projectName, action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" verified " , YesNo.No);
				} else {
					log(LogStatus.ERROR, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ", YesNo.Yes);
					sa.assertTrue(false, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ");
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc070_createCRMUser2(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser2LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User2",excelLabel.User_Last_Name);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		boolean flag = false;
		for (int i = 0; i < 3; i++) {
			try {
				if (home.clickOnSetUpLink()) {
					flag = true;
					parentWindow = switchOnWindow(driver);
					if (parentWindow == null) {
						sa.assertTrue(false,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User2");
						log(LogStatus.SKIP,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User2",
								YesNo.Yes);
						exit("No new window is open after click on setup link in lighting mode so cannot create CRM User2");
					}
					if (setup.createPEUser( crmUser2FirstName, UserLastName, emailId, crmUserLience,
							crmUserProfile)) {
						log(LogStatus.INFO, "CRM User is created Successfully: " + crmUser2FirstName + " " + UserLastName, YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User2",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User2",
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
				
				if (setup.installedPackages(crmUser2FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + crmUser2FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in CRM User2: " + crmUser2FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in CRM User2: " + crmUser2FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in CRM User2: " + crmUser2FirstName + " " + UserLastName,
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
			appLog.info("Password is set successfully for CRM User2: " + crmUser2FirstName + " " + UserLastName );
		} else {
			appLog.info("Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc071_ChangetheUserIndustryAsManufacturingAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag=false;
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to Set the User Industry as "+manufacturingCoverage, YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Users)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);

					if(setup.clickOnEditBtnForCRMUser(driver, crmUser1LastName, crmUser1FirstName, 20)) {
						log(LogStatus.INFO, "Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver, setup.getIndustryDropdownList(10), "Industry DropDown List",manufacturingCoverage)) {
							log(LogStatus.INFO, "selected visbible text from the Industry dropdown "+manufacturingCoverage, YesNo.No);
							ThreadSleep(2000);

							if (click(driver, setup.getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
								scrollDownThroughWebelement(driver, setup.getIndustryDropdownList("Industry", manufacturingCoverage, 10), manufacturingCoverage);
								if (setup.getIndustryDropdownList("Industry", manufacturingCoverage, 10)!=null) {
									log(LogStatus.INFO, "Industry Value verified "+manufacturingCoverage, YesNo.No);
									flag=true;
								} else {
									log(LogStatus.ERROR, "Industry Value not verified "+manufacturingCoverage, YesNo.Yes);
									sa.assertTrue(false, "Industry Value not verified "+manufacturingCoverage);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to select visbible text from the Industry dropdown "+manufacturingCoverage, YesNo.Yes);
							sa.assertTrue(false, "Not able to select visbible text from the Industry dropdown "+manufacturingCoverage);
						}


					}else {
						log(LogStatus.ERROR, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Users, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Users);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot Set the User Industry as "+manufacturingCoverage, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot Set the User Industry as "+manufacturingCoverage);
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}
		flag=false;
		if (flag) {
			refresh(driver);
			lp.clickOnTab(projectName, homeTab);
			String dependentTC="Module3Tc065_CreateMyIndustryMenuItem";
			String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
			NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
				WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					if (npbl.getCoverageTabAfterClick(projectName, manufacturingCoverage, action.SCROLLANDBOOLEAN, 20)!=null) {
						log(LogStatus.INFO, manufacturingCoverage+" is open after click on "+navigationLabelValue, YesNo.No);
					} else {
						log(LogStatus.ERROR, manufacturingCoverage+" should be open after click on "+navigationLabelValue, YesNo.Yes);
						sa.assertTrue(false,manufacturingCoverage+" should be open after click on "+navigationLabelValue);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
			}	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc072_ChangetheUserIndustryAsManufacturingAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String dependentTC="Module3Tc065_CreateMyIndustryMenuItem";
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);
				if (npbl.getCoverageTabAfterClick(projectName, manufacturingCoverage, action.SCROLLANDBOOLEAN, 20)!=null) {
					log(LogStatus.INFO, manufacturingCoverage+" is open after click on "+navigationLabelValue, YesNo.No);
				} else {
					log(LogStatus.ERROR, manufacturingCoverage+" should be open after click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,manufacturingCoverage+" should be open after click on "+navigationLabelValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc073_SetTheUserRegionAsLondon(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);

		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to Set the User Region as "+LondonCoverage, YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Users)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);

					if(setup.clickOnEditBtnForCRMUser(driver, crmUser1LastName, crmUser1FirstName, 20)) {
						log(LogStatus.INFO, "Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver, setup.getRegionDropdownList(10), "Region DropDown List",LondonCoverage)) {
							log(LogStatus.INFO, "selected visbible text from the Region dropdown "+LondonCoverage, YesNo.No);
							ThreadSleep(2000);

							if (click(driver, setup.getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
								scrollDownThroughWebelement(driver, setup.getIndustryDropdownList("Region", LondonCoverage, 10), LondonCoverage);
								if (setup.getRegionDropdownList("Region", LondonCoverage, 10)!=null) {
									log(LogStatus.INFO, "Region Value verified "+LondonCoverage, YesNo.No);
								} else {
									log(LogStatus.ERROR, "Region Value not verified "+LondonCoverage, YesNo.Yes);
									sa.assertTrue(false, "Region Value not verified "+LondonCoverage);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to select visbible text from the Region dropdown "+LondonCoverage, YesNo.Yes);
							sa.assertTrue(false, "Not able to select visbible text from the Region dropdown "+LondonCoverage);
						}


					}else {
						log(LogStatus.ERROR, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Users, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Users);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot Set the User Region as "+LondonCoverage, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot Set the User Region as "+LondonCoverage);
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc074_CreateMyRegionMenuItem(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String navigationLabel=CSVLabel.Navigation_Label.toString();
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);

		String orderLabel=CSVLabel.Order.toString();
		String orderLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Updated_Order);
		String urlObjectLabel=CSVLabel.URL.toString();
		String urlObjecValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.URL);

		String[][] labelWithValue= {{navigationLabel,navigationLabelValue},{orderLabel,orderLabelValue},{urlObjectLabel,urlObjecValue}};
		WebElement ele;
		if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
			log(LogStatus.INFO, "created "+customMenu, YesNo.No);
			refresh(driver);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getPageDoesNotExist(projectName, action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" verified " , YesNo.No);

					} else {
						log(LogStatus.ERROR, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ", YesNo.Yes);
						sa.assertTrue(false, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ");
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to create "+customMenu, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+customMenu);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc075_CreateRecordOnCoverageObjectAndVerifyMyRegionlink(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CoveragePageBusinessLayer cp = new CoveragePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String[][] industryCoverages = {{dublinCoverage,dublinCoverageRecordType},{LondonCoverage,LondonCoverageRecordType},{NewYorkCoverage,NewYorkCoverageRecordType}};

		String coverageName="";
		String coverageRecordType="";
		for (String[] industryCov : industryCoverages) {
			coverageName=industryCov[0];
			coverageRecordType=industryCov[1];
			if (cp.clickOnTab(projectName, tabObj8Coverage)) {
				log(LogStatus.INFO, "Click on Tab : "+tabObj8Coverage, YesNo.No);

				if (cp.createCoverage(projectName, coverageRecordType, coverageName)) {
					log(LogStatus.INFO, "created/verified "+coverageName+" of type "+coverageRecordType, YesNo.No);
				} else {
					log(LogStatus.ERROR, "cannot create "+coverageName+" of type "+coverageRecordType, YesNo.Yes);
					sa.assertTrue(false,"cannot create "+coverageName+" of type "+coverageRecordType);

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+tabObj8Coverage+" so cannot create "+coverageName+" of type "+coverageRecordType, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj8Coverage+" so cannot create "+coverageName+" of type "+coverageRecordType);
			}

		}
		
		refresh(driver);
		lp.clickOnTab(projectName, homeTab);
		String dependentTC="Module3Tc074_CreateMyRegionMenuItem";
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);
				if (npbl.getCoverageTabAfterClick(projectName, LondonCoverage, action.SCROLLANDBOOLEAN, 20)!=null) {
					log(LogStatus.INFO, LondonCoverage+" is open after click on "+navigationLabelValue, YesNo.No);
				} else {
					log(LogStatus.ERROR, LondonCoverage+" should be open after click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,LondonCoverage+" should be open after click on "+navigationLabelValue);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc076_1_ChangetheUserRegionAsNewYorkAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag=false;
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to Set the User Region as "+NewYorkCoverage, YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Users)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);

					if(setup.clickOnEditBtnForCRMUser(driver, crmUser1LastName, crmUser1FirstName, 20)) {
						log(LogStatus.INFO, "Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver, setup.getRegionDropdownList(10), "Region DropDown List",NewYorkCoverage)) {
							log(LogStatus.INFO, "selected visbible text from the Region dropdown "+NewYorkCoverage, YesNo.No);
							ThreadSleep(2000);

							if (click(driver, setup.getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
								scrollDownThroughWebelement(driver, setup.getRegionDropdownList("Region", NewYorkCoverage, 10), NewYorkCoverage);
								if (setup.getRegionDropdownList("Region", NewYorkCoverage, 10)!=null) {
									log(LogStatus.INFO, "Region Value verified "+NewYorkCoverage, YesNo.No);
									flag=true;
								} else {
									log(LogStatus.ERROR, "Region Value not verified "+NewYorkCoverage, YesNo.Yes);
									sa.assertTrue(false, "Region Value not verified "+NewYorkCoverage);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to select visbible text from the Region dropdown "+NewYorkCoverage, YesNo.Yes);
							sa.assertTrue(false, "Not able to select visbible text from the Region dropdown "+NewYorkCoverage);
						}


					}else {
						log(LogStatus.ERROR, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Users, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Users);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot Set the User Region as "+NewYorkCoverage, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot Set the User Region as "+NewYorkCoverage);
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}
		flag=false;
		if (flag) {
			refresh(driver);
			lp.clickOnTab(projectName, homeTab);
			String dependentTC="Module3Tc074_CreateMyRegionMenuItem";
			String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
			NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
				WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					if (npbl.getCoverageTabAfterClick(projectName, NewYorkCoverage, action.SCROLLANDBOOLEAN, 20)!=null) {
						log(LogStatus.INFO, NewYorkCoverage+" is open after click on "+navigationLabelValue, YesNo.No);
					} else {
						log(LogStatus.ERROR, NewYorkCoverage+" should be open after click on "+navigationLabelValue, YesNo.Yes);
						sa.assertTrue(false,NewYorkCoverage+" should be open after click on "+navigationLabelValue);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
			}	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc076_2_ChangetheUserRegionAsNewYorkAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String dependentTC="Module3Tc074_CreateMyRegionMenuItem";
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);
				if (npbl.getCoverageTabAfterClick(projectName, NewYorkCoverage, action.SCROLLANDBOOLEAN, 20)!=null) {
					log(LogStatus.INFO, NewYorkCoverage+" is open after click on "+navigationLabelValue, YesNo.No);
				} else {
					log(LogStatus.ERROR, NewYorkCoverage+" should be open after click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,NewYorkCoverage+" should be open after click on "+navigationLabelValue);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc077_1_ChangetheUserRegionAsLosAngelesAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag=false;
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to Set the User Region as "+LosAngelesCoverage, YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Users)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);

					if(setup.clickOnEditBtnForCRMUser(driver, crmUser1LastName, crmUser1FirstName, 20)) {
						log(LogStatus.INFO, "Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver, setup.getRegionDropdownList(10), "Region DropDown List",LosAngelesCoverage)) {
							log(LogStatus.INFO, "selected visbible text from the Region dropdown "+LosAngelesCoverage, YesNo.No);
							ThreadSleep(2000);

							if (click(driver, setup.getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
								scrollDownThroughWebelement(driver, setup.getRegionDropdownList("Region", LosAngelesCoverage, 10), LosAngelesCoverage);
								if (setup.getRegionDropdownList("Region", LosAngelesCoverage, 10)!=null) {
									log(LogStatus.INFO, "Region Value verified "+LosAngelesCoverage, YesNo.No);
									flag=true;
								} else {
									log(LogStatus.ERROR, "Region Value not verified "+LosAngelesCoverage, YesNo.Yes);
									sa.assertTrue(false, "Region Value not verified "+LosAngelesCoverage);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to select visbible text from the Region dropdown "+LosAngelesCoverage, YesNo.Yes);
							sa.assertTrue(false, "Not able to select visbible text from the Region dropdown "+LosAngelesCoverage);
						}


					}else {
						log(LogStatus.ERROR, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Users, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Users);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot Set the User Region as "+LosAngelesCoverage, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot Set the User Region as "+LosAngelesCoverage);
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}
		flag=false;
		if (flag) {
			refresh(driver);
			String dependentTC="Module3Tc074_CreateMyRegionMenuItem";
			String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
			NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
				WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getPageDoesNotExist(projectName, action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" verified " , YesNo.No);
					} else {
						log(LogStatus.ERROR, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ", YesNo.Yes);
						sa.assertTrue(false, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ");
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
			}	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc077_2_ChangetheUserRegionAsLosAngelesAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String dependentTC="Module3Tc074_CreateMyRegionMenuItem";
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);
				ele=npbl.getPageDoesNotExist(projectName, action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" verified " , YesNo.No);
				} else {
					log(LogStatus.ERROR, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ", YesNo.Yes);
					sa.assertTrue(false, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ");
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc078_1_ChangetheUserRegionAsNoneAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		none="--"+none+"--";
		boolean flag=false;
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to Set the User Region as "+TechonlogyCoverage, YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Users)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);

					if(setup.clickOnEditBtnForCRMUser(driver, crmUser1LastName, crmUser1FirstName, 20)) {
						log(LogStatus.INFO, "Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver, setup.getRegionDropdownList(10), "Region DropDown List",none)) {
							log(LogStatus.INFO, "selected visbible text from the Region dropdown "+none, YesNo.No);
							ThreadSleep(2000);

							if (click(driver, setup.getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
								scrollDownThroughWebelement(driver, setup.getRegionDropdownList("Region", none, 10), none);
								if (setup.getRegionDropdownList("Region", none, 10)!=null) {
									log(LogStatus.INFO, "Region Value verified "+none, YesNo.No);
									flag=true;
								} else {
									log(LogStatus.ERROR, "Region Value not verified "+none, YesNo.Yes);
									sa.assertTrue(false, "Region Value not verified "+none);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to select visbible text from the Region dropdown "+none, YesNo.Yes);
							sa.assertTrue(false, "Not able to select visbible text from the Region dropdown "+none);
						}


					}else {
						log(LogStatus.ERROR, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on edit Button "+crmUser1LastName+","+crmUser1FirstName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Users, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Users);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot Set the User Region as "+none, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot Set the User Region as "+none);
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}
		flag=false;
		if (flag) {
			refresh(driver);
			String dependentTC="Module3Tc074_CreateMyRegionMenuItem";
			String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
			NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
				WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getPageDoesNotExist(projectName, action.BOOLEAN, 30);
					if (ele!=null) {
						log(LogStatus.INFO, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" verified " , YesNo.No);
					} else {
						log(LogStatus.ERROR, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ", YesNo.Yes);
						sa.assertTrue(false, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ");
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
			}	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc078_2_ChangetheUserRegionAsNoneAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String dependentTC="Module3Tc074_CreateMyRegionMenuItem";
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);
				ele=npbl.getPageDoesNotExist(projectName, action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" verified " , YesNo.No);
				} else {
					log(LogStatus.ERROR, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ", YesNo.Yes);
					sa.assertTrue(false, NavatarSetUpPageErrorMessage.PageDoesExist+" and "+NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain+" not verified ");
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc079_ChangetheUserRegionAsDublinAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag=false;
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to Set the User Region as "+dublinCoverage, YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Users)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);

					if(setup.clickOnEditBtnForCRMUser(driver, crmUser2LastName, crmUser2FirstName, 20)) {
						log(LogStatus.INFO, "Click on edit Button "+crmUser2LastName+","+crmUser2FirstName, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver, setup.getRegionDropdownList(10), "Region DropDown List",dublinCoverage)) {
							log(LogStatus.INFO, "selected visbible text from the Region dropdown "+dublinCoverage, YesNo.No);
							ThreadSleep(2000);

							if (click(driver, setup.getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button for  "+crmUser2LastName+","+crmUser2FirstName, YesNo.No);
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
								scrollDownThroughWebelement(driver, setup.getRegionDropdownList("Region", dublinCoverage, 10), dublinCoverage);
								if (setup.getRegionDropdownList("Region", dublinCoverage, 10)!=null) {
									log(LogStatus.INFO, "Region Value verified "+dublinCoverage, YesNo.No);
									flag=true;
								} else {
									log(LogStatus.ERROR, "Region Value not verified "+dublinCoverage, YesNo.Yes);
									sa.assertTrue(false, "Region Value not verified "+dublinCoverage);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser2LastName+","+crmUser2FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser2LastName+","+crmUser2FirstName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to select visbible text from the Region dropdown "+dublinCoverage, YesNo.Yes);
							sa.assertTrue(false, "Not able to select visbible text from the Region dropdown "+dublinCoverage);
						}


					}else {
						log(LogStatus.ERROR, "Not Able to Click on edit Button "+crmUser2LastName+","+crmUser2FirstName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on edit Button "+crmUser2LastName+","+crmUser2FirstName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Users, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Users);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot Set the User Region as "+dublinCoverage, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot Set the User Region as "+dublinCoverage);
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}
		flag=false;
		if (flag) {
			refresh(driver);
			lp.clickOnTab(projectName, homeTab);
			String dependentTC="Module3Tc074_CreateMyRegionMenuItem";
			String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
			NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
				WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					if (npbl.getCoverageTabAfterClick(projectName, dublinCoverage, action.SCROLLANDBOOLEAN, 20)!=null) {
						log(LogStatus.INFO, dublinCoverage+" is open after click on "+navigationLabelValue, YesNo.No);
					} else {
						log(LogStatus.ERROR, dublinCoverage+" should be open after click on "+navigationLabelValue, YesNo.Yes);
						sa.assertTrue(false,dublinCoverage+" should be open after click on "+navigationLabelValue);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
			}	
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc080_ChangetheUserRegionAsDublinAndVerifyImpactonNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser2EmailID, adminPassword);

		String dependentTC="Module3Tc074_CreateMyRegionMenuItem";
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);
				if (npbl.getCoverageTabAfterClick(projectName, dublinCoverage, action.SCROLLANDBOOLEAN, 20)!=null) {
					log(LogStatus.INFO, dublinCoverage+" is open after click on "+navigationLabelValue, YesNo.No);
				} else {
					log(LogStatus.ERROR, dublinCoverage+" should be open after click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,dublinCoverage+" should be open after click on "+navigationLabelValue);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc081_CreateMultiTaggedLogACallAllNavigationMenuItem(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String createNewLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Parent);

		String logAcall = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String logAcallOrder = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Updated_Order);
		String logAcallActionObject = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Action_Object);

		if (npbl.createNavigationItem(projectName, new String[][]{{CSVLabel.Navigation_Label.toString(),createNewLabel}}, 20)) {
			log(LogStatus.INFO, "created "+createNewLabel, YesNo.No);

			if (npbl.createNavigationItem(projectName, new String[][]{{CSVLabel.Navigation_Label.toString(),logAcall},{CSVLabel.Parent.toString(),createNewLabel},{CSVLabel.Order.toString(),logAcallOrder},{CSVLabel.Action_Object.toString(),logAcallActionObject}}, 20)) {
				log(LogStatus.INFO, "created "+logAcall, YesNo.No);

			} else {
				log(LogStatus.ERROR, "Not Able to create "+logAcall, YesNo.Yes);
				sa.assertTrue(false, "Not Able to create "+logAcall);

			}


		} else {
			log(LogStatus.ERROR, "Not Able to create "+createNewLabel+" so cannot create child : "+logAcall, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+createNewLabel+" so cannot create child : "+logAcall);

		}
		Map<String,String> parentWithChild = new LinkedHashMap<String,String>(); 
		parentWithChild.put(createNewLabel,logAcall);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			npbl.verifyingNavigationMenuLink(projectName, null, parentWithChild, action.BOOLEAN, 2);
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot verify order", YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot verify order");
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc082_ClickOnLogACallwithMultipleAssociationsMenuItem(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String dependentTC="Module3Tc081_CreateMultiTaggedLogACallAllNavigationMenuItem";
		String createNewLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Parent);
		String logAcall = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		String navigationLabelValue=createNewLabel+"/"+logAcall;
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);

					String task="TC082TaskSubjectName";	

					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.TaskPage, task, null, action.SCROLLANDBOOLEAN, 10)) {	

						log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

						if (clickUsingJavaScript(driver,lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
							ThreadSleep(1000);
							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, task);
								if (actualValue.contains(expectedValue) || expectedValue.contains(actualValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}


						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created" );
						}

					}else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}


			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc083_ClickOnLogACallwithMultipleAssociationsMenuItem(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver) ;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		

		String dependentTC="Module3Tc081_CreateMultiTaggedLogACallAllNavigationMenuItem";
		String createNewLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Parent);
		String logAcall = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		String navigationLabelValue=createNewLabel+"/"+logAcall;
		
		String listViewNameLabel=CSVLabel.Activities_Button_API_Name.toString();
		String listViewNameLabelValue=logAcall.replace(" ", "_");
		String[][] labelWithValue= {{listViewNameLabel,listViewNameLabelValue}};
		boolean flag=false;

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, logAcall, labelWithValue, 20)) {
			log(LogStatus.INFO, listViewNameLabelValue+" value has been updated & saved under "+listViewNameLabel+" for "+logAcall, YesNo.No);
		}else{
			log(LogStatus.ERROR, "Not Able to enter value on" +listViewNameLabel+" for "+logAcall, YesNo.Yes);
			sa.assertTrue(false, "Not Able to enter value on" +listViewNameLabel+" for "+logAcall);
		}

		
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);

				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, "New Task Pop Up is open after clicking on "+navigationLabelValue , YesNo.No);

					String task="TC083LogACallSubjectName";	

					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.TaskPage, task, null, action.SCROLLANDBOOLEAN, 10)) {	

						log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

						if (clickUsingJavaScript(driver,lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
							ThreadSleep(1000);
							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, task);
								if (actualValue.contains(expectedValue) || expectedValue.contains(actualValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}


						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created" );
						}

					}else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}



				} else {
					log(LogStatus.ERROR, "New Task Pop Up should be open after clicking on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false, "New Task Pop Up should be open after clicking on "+navigationLabelValue);
				}


			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module5TC084_1_CreateData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ M3Ins1, M3Ins1RecordType ,null},{ M3Ins2, M3Ins2RecordType ,null}};

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
		
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
			
			M3Contact1EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M3Contact1EmailID, "Contacts", excelLabel.Variable_Name, "M3CON1",excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, M3Contact1FName, M3Contact1LName, M3Ins1, M3Contact1EmailID,M3Contact1RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M3Contact1FName+" "+M3Contact1LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M3Contact1FName+" "+M3Contact1LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M3Contact1FName+" "+M3Contact1LName,YesNo.Yes);
			}


		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object3Tab,YesNo.No);	
			String[] funds = {M3Fund1,M3Fund1Type,M3Fund1RecordType,M3Fund1RecordType};
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
		
		if (lp.clickOnTab(projectName, TabName.TestCustomObjectTab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.TestCustomObjectTab,YesNo.No);	

			if (cop.createRecord(projectName, M3TestCustomObj1RecordType, tabCustomObjField, M3TestCustomObj1Name, false)) {
				log(LogStatus.INFO,"successfully Created custom record : "+M3TestCustomObj1Name,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create custom record  : "+M3TestCustomObj1Name);
				log(LogStatus.SKIP,"Not Able to Create custom record  : "+M3TestCustomObj1Name,YesNo.Yes);
			}


		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
		}
		
		
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc085_CreateCustomActionOfTypeTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String newMeeting=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to Create Custom action of Type 'Task'", YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Global_Actions)) {
					log(LogStatus.INFO , "Able to search/click on "+object.Global_Actions, YesNo.No);
					String[][] labelsWithValues= {{excelLabel.Label.toString(),newMeeting}};
					if (setup.createNewAction(driver, labelsWithValues, 20)) {
						log(LogStatus.INFO, "created action "+newMeeting, YesNo.No);
					} else {
						log(LogStatus.ERROR, "not able to create action "+newMeeting, YesNo.Yes);
						sa.assertTrue(false, "not able to create action "+newMeeting);
					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Global_Actions, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Global_Actions);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot Create Custom action of Type 'Task'", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot Create Custom action of Type 'Task'");
			}

		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc086_CreateNavigationRecordsRelatedToMultiTaggedActions(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String listViewNameLabel=CSVLabel.Activities_Button_API_Name.toString();
		String listViewNameLabelValue="";

		String createNewLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Parent);

		String newTask = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String newTaskOrder = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Updated_Order);
		String newTaskActionObject = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Action_Object);
		listViewNameLabelValue=newTask.replace(" ", "_");
		if (npbl.createNavigationItem(projectName, new String[][]{{CSVLabel.Navigation_Label.toString(),newTask},{CSVLabel.Parent.toString(),createNewLabel},{CSVLabel.Order.toString(),newTaskOrder},{CSVLabel.Action_Object.toString(),newTaskActionObject}
		,{listViewNameLabel,listViewNameLabelValue}}, 20)) {
			log(LogStatus.INFO, "created "+newTask, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not Able to create "+newTask, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+newTask);
		}
		refresh(driver);
		String dependtTC="Module3Tc085_CreateCustomActionOfTypeTask";
		String newMeeting = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependtTC, excelLabel.Navigation_Label_Name);
		listViewNameLabelValue=newMeeting.replace(" ", "_");
		newTaskOrder = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependtTC, excelLabel.Updated_Order);
		newTaskActionObject = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependtTC, excelLabel.Action_Object);
		if (npbl.createNavigationItem(projectName, new String[][]{{CSVLabel.Navigation_Label.toString(),newMeeting},{CSVLabel.Parent.toString(),createNewLabel},{CSVLabel.Order.toString(),newTaskOrder},{CSVLabel.Action_Object.toString(),newTaskActionObject}
		,{listViewNameLabel,listViewNameLabelValue}}, 20)) {
			log(LogStatus.INFO, "created "+newMeeting, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not Able to create "+newMeeting, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+newMeeting);
		}

		refresh(driver);
		String logAcall = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, "Module3Tc081_CreateMultiTaggedLogACallAllNavigationMenuItem", excelLabel.Navigation_Label_Name);
		Map<String,String> parentWithChild = new LinkedHashMap<String,String>(); 
		parentWithChild.put(createNewLabel,logAcall+","+newTask+","+newMeeting);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			npbl.verifyingNavigationMenuLink(projectName, null, parentWithChild, action.BOOLEAN, 2);
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot verify order", YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot verify order");
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc088_VerifyLogACallWithMultipleAssociationsAction(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String dependentTC="Module3Tc081_CreateMultiTaggedLogACallAllNavigationMenuItem";
		String createNewLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Parent);
		String logAcall = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		String navigationLabelValue=createNewLabel+"/"+logAcall;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);

				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, "New Task Pop Up is open after clicking on "+navigationLabelValue , YesNo.No);

					///////////////////////////////////////////////////

					String subject=M3Task1Subject;
					String dueDate=getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
					String status1 = M3Task1Status;
					try {
						String name = getValueFromElementUsingJavaScript(driver, tp.getLabelTextBox(projectName, PageName.TaskPage.toString(),PageLabel.Subject.toString(),10), "subject");
						if (name.contains(subject.trim())) {
							log(LogStatus.INFO, "successfully verified subject textbox : "+subject, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify subject textbox, found: "+name, YesNo.No);
							sa.assertTrue(false,"could not verify subject textbox, found: "+name);

						}

						String status=getValueFromElementUsingJavaScript(driver, tp.getstatusDropdownInCreateNewTask(projectName, 10), "status dropdown");
						System.out.println("div value "+status);
						if (status.trim().contains(status1)) {
							log(LogStatus.INFO, "successfully verfied status dropdown", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not verify status dropdown. Found is "+status, YesNo.Yes);
							sa.assertTrue(false,"could not verify status dropdown. Found is "+status );

						}

						if ( tp.getdueDateTextBoxInNewTask(projectName, 10)!=null) {
							//name= getdueDateTextBoxInNewTask(projectName, 20).getText().trim();
							name=getValueFromElementUsingJavaScript(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), "dueDateTextBoxInNewTask");
							if (tp.verifyDate(todaysDate, name)) {
								log(LogStatus.INFO, "successfully verified dueDate textbox "+dueDate + " contains "+name, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not verify dueDate textbox, found: "+name, YesNo.No);
								sa.assertTrue(false,"could not verify dueDate textbox, found: "+name);

							}
						}else {
							log(LogStatus.ERROR, "not visible on page dueDate textbox", YesNo.No);
							sa.assertTrue(false,"not visible on page dueDate textbox" );

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}








					/////////////////////////////////////////////////////
					String task=subject;	

					String[][] dropDownLabelWithValues = {{PageLabel.Priority.toString(),M3Task1Priority}};
					String relatedContact = M3Contact1FName+" "+M3Contact1LName;
					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.NewTaskPopUP, task, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {	
						log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

						boolean flag = cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Name.toString(), TabName.TaskTab, relatedContact, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+relatedContact+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.TaskTab)+" For Label "+PageLabel.Name,YesNo.No);
							ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.NewTaskPopUP, PageLabel.Name.toString(),true, relatedContact, action.SCROLLANDBOOLEAN, 5);
							if (ele!=null) {
								log(LogStatus.INFO, relatedContact+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	
							} else {
								sa.assertTrue(false,relatedContact+" not Found For Label "+PageLabel.Name.toString());
								log(LogStatus.ERROR, relatedContact+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

							}
						} else {
							sa.assertTrue(false,"Not Able to Select "+relatedContact+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"Not Able to Select "+relatedContact+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						
						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Related_Associations.toString(), TabName.Object1Tab, M3Ins1, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+M3Ins1+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+M3Ins1+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+M3Ins1+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}
						
						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Related_Associations.toString(), TabName.Object1Tab, M3Ins2, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+M3Ins2+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+M3Ins2+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+M3Ins2+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}
						
						
						if (sendKeys(driver, cp.getcommentsTextBox(projectName, 10), M3Task1Comment, "comments", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "value entered in comment textbox", YesNo.No);	
							
						}
						else {
							log(LogStatus.ERROR, "comments textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"comments textbox is not visible so task could not be created" );
						}
						
						
						
						if (clickUsingJavaScript(driver,lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, task);
								if (actualValue.contains(expectedValue) || expectedValue.contains(actualValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}

							
							String relatedAssoctaion=tp.Comment(projectName, PageLabel.Related_Associations, M3Ins1)+", "+M3Ins2;
							String comment = relatedAssoctaion+"\n"+TaskPagePageErrorMessage.Dots+"\n"+M3Task1Comment;

							String[][] fieldsWithValues= {
									{PageLabel.Subject.toString(),M3Task1Subject},
									{PageLabel.Due_Date.toString(),dueDate},
									{PageLabel.Status.toString(),M3Task1Status},
									{PageLabel.Priority.toString(),M3Task1Priority},
									{PageLabel.Name.toString(),M3Contact1FName+" "+M3Contact1LName},
									{PageLabel.Related_Associations.toString(),M3Ins1+", "+M3Ins2},
									{PageLabel.Comments.toString(),comment}};

							tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);


							
							

						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created" );
						}

					}else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}



				} else {
					log(LogStatus.ERROR, "New Task Pop Up should be open after clicking on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false, "New Task Pop Up should be open after clicking on "+navigationLabelValue);
				}


			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc089_VerifyNewTaskwithMultipleAssociationsAction(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String dependentTC="Module3Tc081_CreateMultiTaggedLogACallAllNavigationMenuItem";
		String createNewLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Parent);
		String newTask = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, "Module3Tc086_CreateNavigationRecordsRelatedToMultiTaggedActions", excelLabel.Navigation_Label_Name);
		String navigationLabelValue=createNewLabel+"/"+newTask;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);

				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, "New Task Pop Up is open after clicking on "+navigationLabelValue , YesNo.No);

					String task=M3Task2Subject;	

					String[][] dropDownLabelWithValues = {{PageLabel.Status.toString(),M3Task2Status},{PageLabel.Priority.toString(),M3Task2Priority}};
					String relatedContact = M3Contact1FName+" "+M3Contact1LName;
					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.NewTaskPopUP, task, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {	
						log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

						boolean flag = cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Name.toString(), TabName.TaskTab, relatedContact, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+relatedContact+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.TaskTab)+" For Label "+PageLabel.Name,YesNo.No);
							ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.NewTaskPopUP, PageLabel.Name.toString(),true, relatedContact, action.SCROLLANDBOOLEAN, 5);
							if (ele!=null) {
								log(LogStatus.INFO, relatedContact+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	
							} else {
								sa.assertTrue(false,relatedContact+" not Found For Label "+PageLabel.Name.toString());
								log(LogStatus.ERROR, relatedContact+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

							}
						} else {
							sa.assertTrue(false,"Not Able to Select "+relatedContact+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"Not Able to Select "+relatedContact+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						
						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Related_Associations.toString(), TabName.Object1Tab, M3Ins1, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+M3Ins1+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+M3Ins1+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+M3Ins1+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}
						
						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Related_Associations.toString(), TabName.Object1Tab, M3Ins2, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+M3Ins2+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+M3Ins2+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+M3Ins2+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}
						
						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Related_Associations.toString(), TabName.Object3Tab, M3Fund1, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+M3Fund1+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+M3Fund1+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+M3Fund1+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}
						
//						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, M3TestCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
//						if (flag) {
//							log(LogStatus.INFO,"Selected "+M3TestCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);
//
//						} else {
//							sa.assertTrue(false,"Not Able to Select "+M3TestCustomObj1Name+" For Label "+PageLabel.Related_Associations);
//							log(LogStatus.SKIP,"Not Able to Select "+M3TestCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
//
//						}
						
						
						
						if (sendKeys(driver, cp.getcommentsTextBox(projectName, 10), M3Task2Comment, "comments", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "value entered in comment textbox", YesNo.No);	
							
						}
						else {
							log(LogStatus.ERROR, "comments textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"comments textbox is not visible so task could not be created" );
						}
						
						
						
						if (clickUsingJavaScript(driver,lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
							// scn.nextLine();
							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, task);
								if (actualValue.contains(expectedValue ) || expectedValue.contains(actualValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}

							
							String relatedAssoctaion=tp.Comment(projectName, PageLabel.Related_Associations, M3Ins1)+", "+M3Ins2+", "+M3Fund1;
							String comment = relatedAssoctaion+"\n"+TaskPagePageErrorMessage.Dots+"\n"+M3Task2Comment;

							String dueDate = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
							String[][] fieldsWithValues= {
									{PageLabel.Subject.toString(),M3Task2Subject},
									{PageLabel.Due_Date.toString(),dueDate},
									{PageLabel.Status.toString(),M3Task2Status},
									{PageLabel.Priority.toString(),M3Task2Priority},
									{PageLabel.Name.toString(),M3Contact1FName+" "+M3Contact1LName},
									{PageLabel.Related_Associations.toString(),M3Ins1+", "+M3Ins2+", "+M3Fund1},
									{PageLabel.Comments.toString(),comment}};

							tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);


							
							

						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created" );
						}

					}else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}



				} else {
					log(LogStatus.ERROR, "New Task Pop Up should be open after clicking on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false, "New Task Pop Up should be open after clicking on "+navigationLabelValue);
				}


			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc090_VerifyNewMeetingAction(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String dependentTC="Module3Tc081_CreateMultiTaggedLogACallAllNavigationMenuItem";
		String createNewLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Parent);
		String newMeeting = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, "Module3Tc085_CreateCustomActionOfTypeTask", excelLabel.Navigation_Label_Name);
		String navigationLabelValue=createNewLabel+"/"+newMeeting;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
			WebElement ele = npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
				ThreadSleep(5000);

				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, "New Task Pop Up is open after clicking on "+navigationLabelValue , YesNo.No);

					String task=M3Task3Subject;	

					String[][] dropDownLabelWithValues = {{PageLabel.Status.toString(),M3Task3Status},{PageLabel.Priority.toString(),M3Task3Priority},{PageLabel.Meeting_Type.toString(),M3Task3MeetingType}};
					String relatedContact = M3Contact1FName+" "+M3Contact1LName;
					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.NewTaskPopUP, task, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {	
						log(LogStatus.INFO,"Entered Value on Subject Text Box : "+task,  YesNo.Yes);

						boolean flag = cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Name.toString(), TabName.TaskTab, relatedContact, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+relatedContact+" For  Drown Down Value : "+cp.getTabName(projectName, TabName.TaskTab)+" For Label "+PageLabel.Name,YesNo.No);
							ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.NewTaskPopUP, PageLabel.Name.toString(),true, relatedContact, action.SCROLLANDBOOLEAN, 5);
							if (ele!=null) {
								log(LogStatus.INFO, relatedContact+" Found For Label "+PageLabel.Name.toString(),YesNo.No);	
							} else {
								sa.assertTrue(false,relatedContact+" not Found For Label "+PageLabel.Name.toString());
								log(LogStatus.ERROR, relatedContact+" not Found For Label "+PageLabel.Name.toString(),YesNo.Yes);

							}
						} else {
							sa.assertTrue(false,"Not Able to Select "+relatedContact+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"Not Able to Select "+relatedContact+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						
						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Related_Associations.toString(), TabName.Object1Tab, M3Ins1, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+M3Ins1+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+M3Ins1+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+M3Ins1+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}
						
						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Related_Associations.toString(), TabName.Object1Tab, M3Ins2, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+M3Ins2+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+M3Ins2+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+M3Ins2+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}
						
						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Related_Associations.toString(), TabName.Object3Tab, M3Fund1, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+M3Fund1+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+M3Fund1+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+M3Fund1+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}
						
//						flag=cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewTaskPopUP, PageLabel.Related_Associations.toString(), TabName.TestCustomObjectTab, M3TestCustomObj1Name, action.SCROLLANDBOOLEAN, 10);		
//						if (flag) {
//							log(LogStatus.INFO,"Selected "+M3TestCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.No);
//
//						} else {
//							sa.assertTrue(false,"Not Able to Select "+M3TestCustomObj1Name+" For Label "+PageLabel.Related_Associations);
//							log(LogStatus.SKIP,"Not Able to Select "+M3TestCustomObj1Name+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
//
//						}
						
						
						
						if (sendKeys(driver, cp.getcommentsTextBox(projectName, 10), M3Task3Comment, "comments", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "value entered in comment textbox", YesNo.No);	
							
						}
						else {
							log(LogStatus.ERROR, "comments textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"comments textbox is not visible so task could not be created" );
						}
						
						
						
						if (clickUsingJavaScript(driver,lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
							// scn.nextLine();
							ele = cp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, task);
								if (actualValue.contains(expectedValue) || expectedValue.contains(actualValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);

								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

							}

							
							String relatedAssoctaion=tp.Comment(projectName, PageLabel.Related_Associations, M3Ins1)+", "+M3Ins2+", "+M3Fund1;
							String comment = relatedAssoctaion+"\n"+TaskPagePageErrorMessage.Dots+"\n"+M3Task3Comment;

							String dueDate = getDateAccToTimeZone(BasePageErrorMessage.AmericaLosAngelesTimeZone, "MM/dd/YYYY");
							String[][] fieldsWithValues= {
									{PageLabel.Subject.toString(),M3Task3Subject},
									{PageLabel.Due_Date.toString(),dueDate},
									{PageLabel.Status.toString(),M3Task3Status},
									{PageLabel.Priority.toString(),M3Task3Priority},
									{PageLabel.Meeting_Type.toString(),M3Task3MeetingType},
									{PageLabel.Name.toString(),M3Contact1FName+" "+M3Contact1LName},
									{PageLabel.Related_Associations.toString(),M3Ins1+", "+M3Ins2+", "+M3Fund1},
									{PageLabel.Comments.toString(),comment}};

							tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);


							
							

						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created" );
						}

					}else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}



				} else {
					log(LogStatus.ERROR, "New Task Pop Up should be open after clicking on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false, "New Task Pop Up should be open after clicking on "+navigationLabelValue);
				}


			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+navigationLabelValue);
		}	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc091_CreatePredefinendValuesInNavigationRecords(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String actionName="";
		String allAction=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[] actionNames =allAction.split(breakSP);
		String[][] labelsWithValues;
		for (int i = 0; i < actionNames.length; i++) {
			actionName=actionNames[i];
			
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to Create predefined values ", YesNo.No);
					ThreadSleep(100);
					if(setup.searchStandardOrCustomObject(environment,mode, object.Global_Actions)) {
						log(LogStatus.INFO , "Able to search/click on "+object.Global_Actions, YesNo.No);
						if (i==0) {
							labelsWithValues=newTaskPredefinedValue;
						} else if(i==1) {
							labelsWithValues=LogACallPredefinedValue;
						}else {
							labelsWithValues=newMeetingPredefinedValue;
						}
						setup.createPredefinedValueForGlobalAction(driver,actionName, labelsWithValues, 20);
					}else {
						log(LogStatus.ERROR, "Not able to search/click on "+object.Global_Actions, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on "+object.Global_Actions);
					}

					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "could not find new window to switch, so cannot Create predefined values", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, so cannot Create predefined values");
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");	
			}
			
		}
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc093_CreateTaskRecordTypesAndPageLayouts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);


		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String[][] taskType1 = {{recordTypeLabel.Record_Type_Label.toString(),recordTypeArray[0]},
				{recordTypeLabel.Active.toString(),""}};

		String[][] taskType2 = {{recordTypeLabel.Record_Type_Label.toString(),recordTypeArray[1]},
				{recordTypeLabel.Active.toString(),""}};

		String[][] taskType3 = {{recordTypeLabel.Record_Type_Label.toString(),recordTypeArray[2]},
				{recordTypeLabel.Active.toString(),""}};

		boolean isMakeAvailable=true, isMakeDefault=true;

		boolean flag=false;
		String existingPageLayout="Task Layout";
		String src = "Task Record Type";
		String trgt="";

		String pageLayouts="Cst Pageloyout1"+breakSP+"Cst Pageloyout2"+breakSP+"Cst Pageloyout3";
		String[] pageLayout=pageLayouts.split(breakSP);
		if (home.clickOnSetUpLink()) {
			flag=false;
			String parentID = switchOnWindow(driver);
			SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Task )) {
					log(LogStatus.INFO, "Click on "+object.Task, YesNo.No);
					for (int i = 0; i < recordTypeArray.length; i++) {
						switchToDefaultContent(driver);
						if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Task, ObjectFeatureName.pageLayouts)) {
							log(LogStatus.INFO, "Click on "+ObjectFeatureName.pageLayouts, YesNo.No);
							ThreadSleep(5000);
							if (i==0) {
								trgt="Status";
								flag=sp.createPageLayout(projectName,new String[][]{{PageLabel.Page_Layout_Name.toString(),pageLayout[i]}} , existingPageLayout, src, trgt, 10)	;	
							} else if(i==1) {
								trgt="Priority";
								flag=sp.createPageLayout(projectName,new String[][]{{PageLabel.Page_Layout_Name.toString(),pageLayout[i]}} , existingPageLayout, src, trgt, 10)	;	

							}else {
								trgt="Due Date";
								flag=sp.createPageLayout(projectName,new String[][]{{PageLabel.Page_Layout_Name.toString(),pageLayout[i]}} , existingPageLayout, src, trgt, 10)	;	
							}
							if (flag) {
								log(LogStatus.ERROR, "Created Page Layout : "+pageLayout[i], YesNo.No);
							} else {
								log(LogStatus.ERROR, "Created Page Layout : "+pageLayout[i], YesNo.Yes);
								sa.assertTrue(false,"Created Page Layout : "+pageLayout[i]);
							}

						}else {
							log(LogStatus.ERROR, "object feature "+ObjectFeatureName.pageLayouts+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, "object feature "+ObjectFeatureName.pageLayouts+" is not clickable");
						}

						switchToDefaultContent(driver);
						if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Task, ObjectFeatureName.recordTypes)) {
							log(LogStatus.INFO, "Click on "+ObjectFeatureName.recordTypes, YesNo.No);
							ThreadSleep(5000);
							if (i==0) {
								flag=sp.createRecordTypeForObject(projectName, taskType1, isMakeAvailable, isMakeDefault,pageLayout[i], 10);	
							} else if(i==1) {
								isMakeDefault=false;
								flag=sp.createRecordTypeForObject(projectName, taskType2, isMakeAvailable, isMakeDefault,pageLayout[i], 10);
							}else {
								isMakeDefault=false;
								flag=sp.createRecordTypeForObject(projectName, taskType3, isMakeAvailable, isMakeDefault,pageLayout[i], 10);
							}
							if (flag) {
								log(LogStatus.ERROR, "Created Record Type : "+recordTypeArray[i], YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not Able to Create Record Type : "+recordTypeArray[i], YesNo.Yes);
								sa.assertTrue(false,"Not Able to Create Record Type : "+recordTypeArray[i]);
							}

						}else {
							log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
						}
					}
				}else {
					log(LogStatus.ERROR, "Task object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Task object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
				refresh(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}


		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void Module3Tc094_EditNavigationRecordAndSetRecordTypesInEachRecord(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String actionName="";
		String allAction=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[] actionNames =allAction.split(breakSP);

		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);

		String actionRecordTypeLabel=CSVLabel.Action_Record_Type.toString();
		String actionRecordTypeLabelValue="";
		for (int i = 0; i < actionNames.length; i++) {
			actionName=actionNames[i];

			actionRecordTypeLabelValue=recordTypeArray[i].replace(" ", "_");
			String[][] labelWithValue= {{actionRecordTypeLabel,actionRecordTypeLabelValue}};
			if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, actionName, labelWithValue, 20)) {
				log(LogStatus.INFO, actionRecordTypeLabelValue+" value has been updated & saved under "+actionRecordTypeLabel+" for "+actionName, YesNo.No);
			}else{
				log(LogStatus.ERROR, "Not Able to enter value on" +actionRecordTypeLabel+" for "+actionName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to enter value on" +actionRecordTypeLabel+" for "+actionName);
			}


		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void Module3Tc095_VerifyActionsfromNavigationPageAfterAssigningRecordTypes(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String parentLabel = ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Parent);;

		String navigationLabels=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[] navigationLabel =navigationLabels.split(breakSP);

		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);

		String navigationLabelValue="";
		WebElement ele=null;
		String expecedHeader="New Task: ";
		String actualHeader="";

		for (int i = 0; i < navigationLabel.length; i++) {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navatarEdge, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navatarEdge, YesNo.No);
				navigationLabelValue=navigationLabel[i];
				ele=npbl.getNavigationLabel(projectName, parentLabel+"/"+navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					ele=npbl.getnavigationPopUpHeader(projectName, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "PopUp is open" , YesNo.No);
						expecedHeader=expecedHeader+recordTypeArray[i];
						actualHeader=ele.getText().trim();
						if (ele.getText().trim().equals(expecedHeader)) {
							log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);
							
						} else {
							log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
							sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
						}

					} else {
						log(LogStatus.ERROR, "No PopUp is open so cannoy verify Heading "+expecedHeader, YesNo.Yes);
						sa.assertTrue(false, "No PopUp is open so cannoy verify Heading "+expecedHeader);
					}
					} else {
					log(LogStatus.ERROR, "Not Able to Click on "+parentLabel+"/"+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+parentLabel+"/"+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navatarEdge+" so cannot check label : "+parentLabel+"/"+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navatarEdge+" so cannot check label : "+parentLabel+"/"+navigationLabelValue);
			}
			refresh(driver);
			expecedHeader="New Task: ";
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
}
	
	