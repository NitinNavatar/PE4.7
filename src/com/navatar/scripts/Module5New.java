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
import com.navatar.generic.EnumConstants.CreationPage;
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
import com.navatar.generic.EnumConstants.SDGCreationLabel;
import com.navatar.generic.EnumConstants.SDGLabels;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.ToggleButtonGroup;
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
	String breakSP = "<break>";
	String columnSP = "<column>";
	String commaSP = ",";
	String colonSP = ":";
	String emptyString = "";
	String EnhanceLightningGridImg = ".\\AutoIT\\EditPage\\NavatarSDG.PNG";

	@Parameters({ "projectName" })
	@Test
	public void M5Tc001_1_createCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",
				excelLabel.User_Last_Name);
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
					if (setup.createPEUser(crmUser1FirstName, UserLastName, emailId, crmUserLience, crmUserProfile,
							null)) {
						log(LogStatus.INFO,
								"CRM User is created Successfully: " + crmUser1FirstName + " " + UserLastName,
								YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User1",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name,
								"User1", excelLabel.User_Last_Name);
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
			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
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

		} else {

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

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
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		appLog.info("ResetLinkIs: " + passwordResetLink);
		driver.get(passwordResetLink);
		if (lp.setNewPassword()) {
			appLog.info("Password is set successfully for CRM User1: " + crmUser1FirstName + " " + UserLastName);
		} else {
			appLog.info("Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc001_2_Prerequisite(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		String[][] userAndPassword = { { superAdminUserName, adminPassword }, { crmUser1EmailID, adminPassword } };
		for (String[] userPass : userAndPassword) {
			lp.CRMLogin(userPass[0], userPass[1], appName);

			String addRemoveTabName = "";
			String tab1 = "";
			if (tabObj1.equalsIgnoreCase("Entity")) {
				tab1 = "Entitie";
			} else {
				tab1 = tabObj1;
			}
			addRemoveTabName = tab1 + "s," + tabObj2 + "s," + tabObj4 + "s," + tabObj5 + "s";
			if (lp.addTab_Lighting(addRemoveTabName, 5)) {
				log(LogStatus.INFO, "Tab added : " + addRemoveTabName, YesNo.No);
			} else {
				log(LogStatus.FAIL, "Tab not added : " + addRemoveTabName, YesNo.No);
				sa.assertTrue(false, "Tab not added : " + addRemoveTabName);
			}

			ThreadSleep(5000);
			lp.CRMlogout();
			closeBrowser();
			config(ExcelUtils.readDataFromPropertyFile("Browser"));
			lp = new LoginPageBusinessLayer(driver);

		}
		sa.assertAll();
		lp.CRMlogout();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc001_3_AddListView(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String[] tabs = { tabObj1, tabObj2, tabObj5, tabObj3, tabObj4 };
		TabName[] tab = { TabName.Object1Tab, TabName.Object2Tab, TabName.Object5Tab, TabName.Object3Tab,
				TabName.Object4Tab };
		int i = 0;
		for (TabName t : tab) {

			if (lp.clickOnTab(projectName, t)) {
				if (lp.addAutomationAllListView(projectName, tabs[i], 10)) {
					log(LogStatus.INFO, "list view added on " + tabs[i], YesNo.No);
				} else {
					log(LogStatus.FAIL, "list view could not added on " + tabs[i], YesNo.Yes);
					sa.assertTrue(false, "list view could not added on " + tabs[i]);
				}
			} else {
				log(LogStatus.FAIL, "could not click on " + tabs[i], YesNo.Yes);
				sa.assertTrue(false, "could not click on " + tabs[i]);
			}
			i++;
			ThreadSleep(5000);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc001_4_CreatePreconditionData(String projectName) {

		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value = "";
		String type = "";
		String[][] EntityOrAccounts = { { ToggleIns1, ToggleIns1RecordType, null } };
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO, "Click on Tab : " + tabObj1, YesNo.No);
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
					log(LogStatus.INFO, "successfully Created Account/Entity : " + value + " of record type : " + type,
							YesNo.No);
				} else {
					sa.assertTrue(false, "Not Able to Create Account/Entity : " + value + " of record type : " + type);
					log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + value + " of record type : " + type,
							YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj1);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj1, YesNo.Yes);
			}
		}

		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj2, YesNo.No);

			ToggleContact1EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, ToggleContact1EmailID, "Contacts", excelLabel.Variable_Name,
					"M5CON1", excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, ToggleContact1FName, ToggleContact1LName, ToggleContact1Inst,
					ToggleContact1EmailID, ToggleContact1RecordType, null, null, CreationPage.ContactPage, null,
					null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + ToggleContact1FName + " " + ToggleContact1LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + ToggleContact1FName + " " + ToggleContact1LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + ToggleContact1FName + " " + ToggleContact1LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj2);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj2, YesNo.Yes);
		}

		if (lp.clickOnTab(projectName, tabObj4)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);

			if (fp.createDeal(projectName, ToggleDeal1RecordType, ToggleDeal1, ToggleDeal1CompanyName, ToggleDeal1Stage,
					null, 15)) {
				log(LogStatus.INFO, "Created Deal : " + ToggleDeal1, YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Deal  : " + ToggleDeal1);
				log(LogStatus.SKIP, "Not Able to Create Deal  : " + ToggleDeal1, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj4);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj4, YesNo.Yes);
		}

		if (lp.clickOnTab(projectName, tabObj5)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj5, YesNo.No);
			ToggleMarketingEvent1Date = previousOrForwardDateAccordingToTimeZone(0, "M/d/YYYY",
					BasePageBusinessLayer.AmericaLosAngelesTimeZone);
			;
			ExcelUtils.writeData(phase1DataSheetFilePath, ToggleMarketingEvent1Date, "MarketingEvent",
					excelLabel.Variable_Name, "TOGGLEME1", excelLabel.Date);
			if (me.createMarketingEvent(projectName, ToggleMarketingEvent1Name, ToggleMarketingEvent1RecordType,
					ToggleMarketingEvent1Date, ToggleMarketingEvent1Organizer, 10)) {
				log(LogStatus.INFO, "Created Marketing Event : " + ToggleMarketingEvent1Name, YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Marketing Event  : " + ToggleMarketingEvent1Name);
				log(LogStatus.SKIP, "Not Able to Create Marketing Event  : " + ToggleMarketingEvent1Name, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj5);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj5, YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc001_5_AddTabInRecordPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword);
		TabName[] tabs = { TabName.InstituitonsTab, TabName.Deals };
		String[] relatedtabs = { TabName.Events.toString(), TabName.QandA.toString() };
		String[] data = { ToggleIns1, ToggleDeal1 };

		for (int i = 0; i < tabs.length; i++) {
			CommonLib.refresh(driver);
			if (lp.clickOnTab(projectName, tabs[i])) {
				log(LogStatus.INFO, "Click on Tab : " + tabs[i], YesNo.No);

				if (lp.clickOnAlreadyCreatedItem(projectName, data[i], tabs[i], 30)) {

					log(LogStatus.INFO, "Click on Tab : " + tabs[i], YesNo.No);

					if (edit.addTabInEditPage(projectName, "Details", "Custom", relatedtabs[i], "Details").isEmpty()) {
						log(LogStatus.INFO, "able to add related tab :" + relatedtabs[i], YesNo.Yes);

					} else {
						log(LogStatus.ERROR, "Not able to add related tab :" + relatedtabs[i], YesNo.Yes);
						sa.assertTrue(false, "Not able to add related tab :" + relatedtabs[i]);
					}

				} else {

					log(LogStatus.SKIP, "Not Able to click on item:" + data, YesNo.Yes);
					sa.assertTrue(false, "Not Able to click on item:" + data);
				}

			} else {
				log(LogStatus.SKIP, "Not Able to click on tab:" + tabs[i], YesNo.Yes);
				sa.assertTrue(false, "Not Able to click on tab:" + tabs[i]);

			}
		}
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc002_1_CreateMarketingEventSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String fields = SDGLabels.APIName.toString();
		String values = "";
		lp.searchAndClickOnApp(SDG, 30);

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);

			String[][] sdgLabels = { { SDGCreationLabel.SDG_Name.toString(), Sdg1Name },
					{ SDGCreationLabel.SDG_Tag.toString(), Sdg1TagName },
					{ SDGCreationLabel.sObjectName.toString(), Sdg1ObjectName } };

			if (sdg.createCustomSDG(projectName, Sdg1Name, sdgLabels, action.BOOLEAN, 20)) {
				log(LogStatus.PASS, "create/verify created SDG : " + Sdg1Name, YesNo.No);

				for (int i = 0; i < 1; i++) {
					String api = ExcelUtils.readData(phase1DataSheetFilePath, "Fields", excelLabel.Variable_Name,
							"CField" + (i + 1), excelLabel.APIName);
					values = api;
					if (sdg.addFieldOnSDG(projectName, fields, values)) {
						log(LogStatus.INFO, "Successfully added fields on " + Sdg1Name, YesNo.Yes);

					} else {
						sa.assertTrue(false, "Not Able to add fields on SDG : " + Sdg1Name);
						log(LogStatus.SKIP, "Not Able to add fields on SDG : " + Sdg1Name, YesNo.Yes);
					}
				}

			} else {
				sa.assertTrue(false, "Not Able to create/verify created SDG : " + Sdg1Name);
				log(LogStatus.SKIP, "Not Able to create/verify created SDG : " + Sdg1Name, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
		}

		lp.searchAndClickOnApp(SDG, 30);

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);

			String[][] sdgLabels = { { SDGCreationLabel.SDG_Name.toString(), M5Sdg2Name },
					{ SDGCreationLabel.SDG_Tag.toString(), M5Sdg2TagName },
					{ SDGCreationLabel.sObjectName.toString(), M5Sdg2ObjectName },
					{ SDGCreationLabel.Parent_Field_Name.toString(), M5Sdg2ParentName } };

			if (sdg.createCustomSDG(projectName, M5Sdg2Name, sdgLabels, action.BOOLEAN, 20)) {
				log(LogStatus.PASS, "create/verify created SDG : " + M5Sdg2Name, YesNo.No);
				for (int i = 0; i < 3; i++) {
					String api = ExcelUtils.readData(phase1DataSheetFilePath, "Fields", excelLabel.Variable_Name,
							"SDGField" + (i + 1), excelLabel.APIName);

					values = api;
					if (sdg.addFieldOnSDG(projectName, fields, values)) {
						log(LogStatus.INFO, "Successfully added fields on " + M5Sdg2Name, YesNo.Yes);

					} else {
						sa.assertTrue(false, "Not Able to add fields on SDG : " + M5Sdg2Name);
						log(LogStatus.SKIP, "Not Able to add fields on SDG : " + M5Sdg2Name, YesNo.Yes);
					}
				}
			} else {
				sa.assertTrue(false, "Not Able to create/verify created SDG : " + M5Sdg2Name);
				log(LogStatus.SKIP, "Not Able to create/verify created SDG : " + M5Sdg2Name, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
		}

		ThreadSleep(3000);

		lp.searchAndClickOnApp(SDG, 30);

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);

			String[][] sdgLabels = { { SDGCreationLabel.SDG_Name.toString(), M5Sdg3Name },
					{ SDGCreationLabel.SDG_Tag.toString(), M5Sdg3TagName },
					{ SDGCreationLabel.sObjectName.toString(), M5Sdg3ObjectName },
					{ SDGCreationLabel.Parent_Field_Name.toString(), M5Sdg3ParentName } };

			if (sdg.createCustomSDG(projectName, M5Sdg3Name, sdgLabels, action.BOOLEAN, 20)) {
				log(LogStatus.PASS, "create/verify created SDG : " + M5Sdg3Name, YesNo.No);
				for (int i = 3; i < 6; i++) {
					String api = ExcelUtils.readData(phase1DataSheetFilePath, "Fields", excelLabel.Variable_Name,
							"SDGField" + (i + 1), excelLabel.APIName);

					values = api;
					if (sdg.addFieldOnSDG(projectName, fields, values)) {
						log(LogStatus.INFO, "Successfully added fields on " + M5Sdg3Name, YesNo.Yes);

					} else {
						sa.assertTrue(false, "Not Able to add fields on SDG : " + M5Sdg3Name);
						log(LogStatus.SKIP, "Not Able to add fields on SDG : " + M5Sdg3Name, YesNo.Yes);
					}
				}
			} else {
				sa.assertTrue(false, "Not Able to create/verify created SDG : " + M5Sdg3Name);
				log(LogStatus.SKIP, "Not Able to create/verify created SDG : " + M5Sdg3Name, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc002_2_AddSDGAtAmarketingEvent(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String dataProviderName = "";
		String dataProviderNameTitle = "";
		String sdgName = "";

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };

		String fileLocation[] = { ".\\AutoIT\\EditPage\\EventTab.PNG", ".\\AutoIT\\EditPage\\Q&ATab.PNG",
				".\\AutoIT\\EditPage\\MECreatedBy.PNG" };

		String EnhanceLightningGridImg = ".\\AutoIT\\EditPage\\NavatarSDG.PNG";

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);
					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO, "click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);
						// scn.nextLine();
						switchToDefaultContent(driver);
						switchToFrame(driver, 60, edit.getEditPageFrame(projectName, 120));
						relatedTab = relatedTabs[i];
						if (clickUsingJavaScript(driver, ip.getRelatedTab(projectName, relatedTab, 120),
								relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
							ThreadSleep(2000);
							String[] toggles = toggleButtons[i].split(breakSP);
							String toggleBtn = "";
							for (int j = 0; j < 2; j++) {
								toggleBtn = toggles[j];
								// CustomObject:Test_Third_Party_Event1
								if (i == 0) {
									if (j == 0) {
										sdgName = "Record_Entity_Entity's_Upcoming Third Party Events";
									} else {
										sdgName = "Record Entity Events Our Event Invitees";
									}
								} else if (i == 1) {
									if (j == 0) {
										sdgName = "Record_Deals_QA_Requests_Open";
									} else {
										sdgName = "Record_Deals_QA_Requests_Closed";
									}
								} else {
									if (j == 0) {
										sdgName = M5Sdg2Name;
									} else {
										sdgName = M5Sdg3Name;
									}
								}

								dataProviderName = customObject + sdgName;
								dataProviderNameTitle = toggleBtn;
								switchToDefaultContent(driver);
								ThreadSleep(20000);
								/////////////////////////////////////////////////////
								String sValue = EditPageErrorMessage.NavatarSDG;
								if (sendKeys(driver, edit.getEditPageSeachTextBox(projectName, 10), sValue,
										"Search TextBox", action.BOOLEAN)) {
									ThreadSleep(2000);
									log(LogStatus.INFO, "send value to Search TextBox : " + sValue, YesNo.No);
									String psource = fileLocation[i];

									// // scn.nextLine();
									// scn.nextLine();
									if (edit.dragNDropUsingScreen(projectName, EnhanceLightningGridImg, psource, 20)) {
										log(LogStatus.INFO, "Able to DragNDrop : " + sValue, YesNo.No);
										ThreadSleep(2000);
										try {
											edit.getElgDataProviderTextBox(projectName, 10).clear();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										ThreadSleep(2000);
										if (edit.clickOnELGSeachValueLink(projectName, dataProviderName, 20)) {
											log(LogStatus.INFO, "Click on ELG Search Vaue Link: " + dataProviderName,
													YesNo.No);
											;
											ThreadSleep(500);
										} else {
											sa.assertTrue(false,
													"Not Able to Click on ELG Search Vaue Link: " + dataProviderName);
											log(LogStatus.SKIP,
													"Not Able to Click on ELG Search Vaue Link: " + dataProviderName,
													YesNo.Yes);
										}
										if (sendKeys(driver, edit.getElgTitleTextBox(projectName, 10),
												dataProviderNameTitle, "ELG Title TextBox", action.BOOLEAN)) {
											ThreadSleep(500);
											log(LogStatus.INFO,
													"send value to ELG Title TextBox : " + dataProviderNameTitle,
													YesNo.No);

										} else {
											sa.assertTrue(false, "Not Able to send value to ELG Title TextBox : "
													+ dataProviderNameTitle);
											log(LogStatus.FAIL, "Not Able to send value to ELG Title TextBox : "
													+ dataProviderNameTitle, YesNo.Yes);
										}

										if (click(driver, edit.getEditPageSaveButton(projectName, 10),
												"Edit Page Save Button", action.BOOLEAN)) {
											log(LogStatus.INFO, "Click on Edit Page Save Button", YesNo.No);
											ThreadSleep(5000);
										} else {
											sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
											log(LogStatus.FAIL, "Not Able to Click on Edit Page Save Button",
													YesNo.Yes);
										}

									} else {
										sa.assertTrue(false, "Not Able to DragNDrop : " + sValue);
										log(LogStatus.FAIL, "Not Able to DragNDrop : " + sValue, YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to send value to Search TextBox : " + sValue);
									log(LogStatus.FAIL, "Not Able to send value to Search TextBox : " + sValue,
											YesNo.Yes);
								}
							}
						} else {
							sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
							log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
						}
						ThreadSleep(5000);
						switchToDefaultContent(driver);
						if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),
								"Edit Page Back Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Edit Page Back Button", YesNo.No);
							ThreadSleep(5000);
						} else {
							sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
							log(LogStatus.SKIP, "Not Able to Click on Edit Page Back Button", YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on Edit Page SetUp Link");
					}
				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc003_CreateTheToggleButtonForRecord(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String sdgConfigDataProviderTextBox = "";
		String defaultSdgToggle = "";

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };
		String[] toggles = null;
		String toggleButton;

		String[] dropLocation = { ".\\AutoIT\\EditPage\\InsEvent.PNG", ".\\AutoIT\\EditPage\\DealOpenQuestion.PNG",
				".\\AutoIT\\EditPage\\OurEvent.PNG" };
		String dropImageLocation = "";

		String NavatarSDGToggleImg = ".\\AutoIT\\EditPage\\NavatarSDGToggle.PNG";
		String sValue = EditPageErrorMessage.NavatarSDGToggles;

		WebElement ele;

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);
					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO, "click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);
						//// scn.nextLine();
						switchToDefaultContent(driver);
						//////////////////////// new////////////////////////////////////////
						relatedTab = relatedTabs[i];
						toggles = toggleButtons[i].split(breakSP);
						switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 120));
						ThreadSleep(10000);
						if (clickUsingJavaScript(driver, ip.getRelatedTab(projectName, relatedTab, 120),
								relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
							ThreadSleep(2000);
							switchToDefaultContent(driver);
							if (sendKeys(driver, edit.getEditPageSeachTextBox(projectName, 180), sValue,
									"Search TextBox", action.BOOLEAN)) {
								ThreadSleep(20000);
								log(LogStatus.INFO, "send value to Search TextBox : " + sValue, YesNo.No);
								ThreadSleep(10000);
								dropImageLocation = dropLocation[i];
								if (edit.dragNDropUsingScreen(projectName, NavatarSDGToggleImg, dropImageLocation,
										20)) {
									log(LogStatus.INFO, "Able to DragNDrop : " + sValue, YesNo.No);
									ThreadSleep(2000);

									if (i == 0) {
										sdgConfigDataProviderTextBox = toggles[0] + ":"
												+ EditPageErrorMessage.EntityUpcomingEventSDG + "," + toggles[1] + ":"
												+ EditPageErrorMessage.EntityEventInviteeSDG;
										defaultSdgToggle = EditPageErrorMessage.EntityUpcomingEventSDG;
									} else if (i == 1) {
										sdgConfigDataProviderTextBox = toggles[0] + ":"
												+ EditPageErrorMessage.DealOpenQuestion + "," + toggles[1] + ":"
												+ EditPageErrorMessage.DealClosed;
										defaultSdgToggle = EditPageErrorMessage.DealOpenQuestion;
									} else {
										sdgConfigDataProviderTextBox = toggles[0] + ":"
												+ EditPageErrorMessage.MarketingEventThirdPartyEvent + "," + toggles[1]
												+ ":" + EditPageErrorMessage.MarketingEventOurEvent;
										defaultSdgToggle = EditPageErrorMessage.MarketingEventThirdPartyEvent;
									}

									if (sendKeysWithoutClearingTextBox(driver,
											edit.getsdgConfigDataProviderTextBox(projectName, 30),
											sdgConfigDataProviderTextBox,
											"sdg Config Data Provider TextBox : " + sdgConfigDataProviderTextBox,
											action.BOOLEAN)) {
										ThreadSleep(500);
										log(LogStatus.INFO, "send value to sdg Config Data Provider TextBox : "
												+ sdgConfigDataProviderTextBox, YesNo.No);

										if (sendKeys(driver, edit.getDefaultSDGToggleTextBox(projectName, 10),
												defaultSdgToggle, "Default SDG Toggle TextBox : " + defaultSdgToggle,
												action.BOOLEAN)) {
											ThreadSleep(200);
											log(LogStatus.INFO,
													"send value to Default SDG Toggle TextBox : " + defaultSdgToggle,
													YesNo.No);
											if (click(driver, edit.getEditPageSaveButton(projectName, 10),
													"Edit Page Save Button", action.BOOLEAN)) {
												log(LogStatus.INFO, "Click on Edit Page Save Button", YesNo.No);
												ThreadSleep(10000);
											} else {
												sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
												log(LogStatus.SKIP, "Not Able to Click on Edit Page Save Button",
														YesNo.Yes);
											}

										} else {
											sa.assertTrue(false,
													"Not Able to send value to Default SDG Toggle TextBox : "
															+ defaultSdgToggle);
											log(LogStatus.SKIP,
													"Not Able to send value to Default SDG Toggle TextBox : "
															+ defaultSdgToggle,
													YesNo.Yes);
										}

									} else {
										sa.assertTrue(false,
												"Not Able to send value to sdg Config Data Provider TextBox : "
														+ sdgConfigDataProviderTextBox);
										log(LogStatus.FAIL,
												"Not Able to send value to sdg Config Data Provider TextBox : "
														+ sdgConfigDataProviderTextBox,
												YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to DragNDrop : " + sValue);
									log(LogStatus.FAIL, "Not Able to DragNDrop : " + sValue, YesNo.Yes);
								}

							} else {
								sa.assertTrue(false, "Not Able to send value to Search TextBox : " + sValue);
								log(LogStatus.FAIL, "Not Able to send value to Search TextBox : " + sValue, YesNo.Yes);
							}
							switchToDefaultContent(driver);
							switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));
							System.err.println(">>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							// scn.nextLine();
							ThreadSleep(10000);
							for (int j = 0; j < toggles.length; j++) {
								toggleButton = toggles[j];
								ele = ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
										action.BOOLEAN, true, 30);
								if (clickUsingJavaScript(driver, ele, toggleButton + " SDG", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, "Click on " + toggleButton, YesNo.No);
									ThreadSleep(200);
									switchToDefaultContent(driver);
									if (click(driver, edit.getEnableToggleCheckBox(projectName, 120),
											"Enable Toggle CheckBox", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "click on Enable Toggle CheckBox for : " + toggleButton,
												YesNo.No);
										ThreadSleep(200);

										if (click(driver, edit.getEditPageSaveButton(projectName, 10),
												"Edit Page Save Button", action.BOOLEAN)) {
											log(LogStatus.INFO, "Click on Edit Page Save Button", YesNo.No);
											ThreadSleep(10000);

										} else {
											sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
											log(LogStatus.FAIL, "Not Able to Click on Edit Page Save Button",
													YesNo.Yes);
										}

									} else {
										sa.assertTrue(false,
												"Not Able to click on Enable Toggle CheckBox for : " + toggleButton);
										log(LogStatus.FAIL,
												"Not Able to click on Enable Toggle CheckBox for : " + toggleButton,
												YesNo.Yes);

									}

								} else {
									sa.assertTrue(false, "Not Able to Click on " + toggleButton);
									log(LogStatus.FAIL, "Not Able to Click on " + toggleButton, YesNo.Yes);
								}
								switchToDefaultContent(driver);
								switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));
							}
							switchToDefaultContent(driver);
							switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 120));
							toggleButton = toggles[0];
							ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
							if (ele != null) {
								log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
								ThreadSleep(2000);
							} else {
								sa.assertTrue(false, "Toggle should be present : " + toggleButton);
								log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
							}

							toggleButton = toggles[1];
							ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
							if (ele != null) {
								log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
								ThreadSleep(2000);
							} else {
								sa.assertTrue(false, "Toggle should be present : " + toggleButton);
								log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
							}

							switchToDefaultContent(driver);
							if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),
									"Edit Page Back Button", action.BOOLEAN)) {
								log(LogStatus.INFO, "Click on Edit Page Back Button", YesNo.No);
								//// scn.nextLine();
								ThreadSleep(5000);
								refresh(driver);
								ThreadSleep(5000);
								if (click(driver, ip.getRelatedTab(projectName, relatedTab, 30), relatedTab,
										action.BOOLEAN)) {
									log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
									ThreadSleep(2000);

									toggleButton = toggles[0];
									ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
									if (ele != null) {
										log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
										ThreadSleep(2000);
									} else {
										sa.assertTrue(false, "Toggle should be present : " + toggleButton);
										log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
									}

									if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
											action.BOOLEAN, true, 30).getAttribute("class").contains("brand")) {
										log(LogStatus.PASS, "After Save " + toggleButton + " is selected by default",
												YesNo.No);
									} else {
										sa.assertTrue(false,
												"After Save " + toggleButton + " should be selected by default");
										log(LogStatus.FAIL,
												"After Save " + toggleButton + " should be selected by default",
												YesNo.Yes);
									}

									toggleButton = toggles[1];

									ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
									if (ele != null) {
										log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
										ThreadSleep(2000);
									} else {
										sa.assertTrue(false, "Toggle should be present : " + toggleButton);
										log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
									}

									if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
											action.BOOLEAN, true, 2).getAttribute("class").contains("neutral")) {
										log(LogStatus.PASS,
												"After Save " + toggleButton + " is not selected by default", YesNo.No);
									} else {
										sa.assertTrue(false,
												"After Save " + toggleButton + " should not be selected by default");
										log(LogStatus.FAIL,
												"After Save " + toggleButton + " should not be selected by default",
												YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
									log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
								}

							} else {
								sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
								log(LogStatus.SKIP, "Not Able to Click on Edit Page Back Button", YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
							log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
						}

						switchToDefaultContent(driver);
						clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),
								"Edit Page Back Button", action.BOOLEAN);

					} else {
						log(LogStatus.ERROR, "Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on Edit Page SetUp Link");
					}
				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			ThreadSleep(5000);
			refresh(driver);
			ThreadSleep(10000);
			// break;
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc004_VerifyFunctionalityOfTogleButton(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };
		String[] toggles = null;
		String toggleButton;
		WebElement ele = null;

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);
					relatedTab = relatedTabs[i];
					toggles = toggleButtons[i].split(breakSP);
					if (click(driver, ip.getRelatedTab(projectName, relatedTab, 120), relatedTab, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
						ThreadSleep(2000);

						for (int j = 0; j < toggles.length; j++) {

							toggleButton = toggles[j];
							if (j == 0) {

								ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
								if (ele != null) {
									log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
									ThreadSleep(2000);
								} else {
									sa.assertTrue(false, "Toggle should be present : " + toggleButton);
									log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
								}

								if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
										action.BOOLEAN, true, 30).getAttribute("class").contains("brand")) {
									log(LogStatus.PASS, toggleButton + " is selected by default", YesNo.No);
								} else {
									sa.assertTrue(false, toggleButton + " should be selected by default");
									log(LogStatus.FAIL, toggleButton + " should be selected by default", YesNo.Yes);
								}

								toggleButton = toggles[1];

								ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
								if (ele != null) {

									log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
									ThreadSleep(2000);
								} else {
									sa.assertTrue(false, "Toggle should be present : " + toggleButton);
									log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
								}

								if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
										action.BOOLEAN, true, 2).getAttribute("class").contains("neutral")) {
									log(LogStatus.PASS, toggleButton + " is not selected by default", YesNo.No);
								} else {
									sa.assertTrue(false, toggleButton + " should not be selected by default");
									log(LogStatus.FAIL, toggleButton + " should not be selected by default", YesNo.Yes);
								}

							} else {
								click(driver, ele, "toggle: " + toggleButton, action.SCROLLANDBOOLEAN);
								ThreadSleep(3000);
								if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
										action.BOOLEAN, true, 10) != null) {
									log(LogStatus.PASS, "After click " + toggleButton + " is visible", YesNo.No);
								} else {
									sa.assertTrue(false, "After click " + toggleButton + " should be visible");
									log(LogStatus.FAIL, "After click " + toggleButton + " should be visible",
											YesNo.Yes);
								}
							}
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
						log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
					}

				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			ThreadSleep(5000);
			refresh(driver);
			ThreadSleep(10000);
			// break;
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc005_1_VerifyTheRetainAndDefaultSelectionOfToggleButton(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };
		String[] sdgToggle = null;
		String[] setDefaultSdgToggle = null;
		String toggleValue = "";

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);

					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO, "click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);
						//// scn.nextLine();
						switchToDefaultContent(driver);
						switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));

						relatedTab = relatedTabs[i];
						if (clickUsingJavaScript(driver, ip.getRelatedTab(projectName, relatedTab, 120),
								relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
							ThreadSleep(2000);

							String[] toggles = toggleButtons[i].split(breakSP);
							String toggleBtn = "";
							for (int j = 0; j < 1; j++) {
								toggleBtn = toggles[j];
								if (click(driver, ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30),
										toggleBtn, action.BOOLEAN)) {
									log(LogStatus.INFO, "Able to Click on Toggle : " + toggleBtn, YesNo.No);
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									ThreadSleep(20000);
									String sdgToggles = getValueFromElementUsingJavaScript(driver,
											edit.getsdgConfigDataProviderTextBox(projectName, 10),
											"sdg Config Data Provider TextBox");
									System.err.println(">>>>> + " + sdgToggles);
									//// scn.nextLine();
									if (sdgToggles != null) {

										sdgToggle = sdgToggles.split(commaSP);
										setDefaultSdgToggle = sdgToggle[1].split(colonSP);
										toggleValue = setDefaultSdgToggle[1];

										if (sendKeys(driver, edit.getDefaultSDGToggleTextBox(projectName, 10),
												toggleValue, "Default SDG Toggle TextBox: " + toggleValue,
												action.BOOLEAN)) {
											ThreadSleep(500);
											log(LogStatus.INFO,
													"send value to Default SDG Toggle TextBox : " + toggleValue,
													YesNo.No);
											if (click(driver, edit.getEditPageSaveButton(projectName, 10),
													"Edit Page Save Button", action.BOOLEAN)) {
												log(LogStatus.INFO, "Click on Edit Page Save Button", YesNo.No);
												ThreadSleep(5000);
												switchToDefaultContent(driver);
												switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));
												if (ip.toggleSDGButtons(projectName, toggleBtn,
														ToggleButtonGroup.SDGButton, action.BOOLEAN, true, 10)
														.getAttribute("class").contains("neutral")) {
													log(LogStatus.PASS, "After Save " + toggleBtn + " is not selected ",
															YesNo.No);
												} else {
													sa.assertTrue(false,
															"After Save " + toggleBtn + " should not be selected ");
													log(LogStatus.FAIL,
															"After Save " + toggleBtn + " should not be selected ",
															YesNo.Yes);
												}

												toggleBtn = toggles[j + 1];
												if (ip.toggleSDGButtons(projectName, toggleBtn,
														ToggleButtonGroup.SDGButton, action.BOOLEAN, true, 10)
														.getAttribute("class").contains("brand")) {
													log(LogStatus.PASS, "After Save " + toggleBtn + " is selected ",
															YesNo.No);
												} else {
													sa.assertTrue(false,
															"After Save " + toggleBtn + " should be selected ");
													log(LogStatus.FAIL,
															"After Save " + toggleBtn + " should be selected ",
															YesNo.Yes);
												}

											} else {
												sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
												log(LogStatus.FAIL, "Not Able to Click on Edit Page Save Button",
														YesNo.Yes);
											}

										} else {
											sa.assertTrue(false,
													"Not Able to send value to Default SDG Toggle TextBox : "
															+ toggleValue);
											log(LogStatus.FAIL,
													"Not Able to send value to Default SDG Toggle TextBox : "
															+ toggleValue,
													YesNo.Yes);
										}

										/////////////////////////////////////////////

									} else {
										sa.assertTrue(false,
												"Not Able to retrive input value from SDG Config Data Provider TextBox");
										log(LogStatus.SKIP,
												"Not Able to retrive input value from SDG Config Data Provider TextBox",
												YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to Click on Toggle : " + toggleBtn);
									log(LogStatus.SKIP, "Not Able to Click on Toggle : " + toggleBtn, YesNo.Yes);
								}

							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
							log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
						}
						switchToDefaultContent(driver);
						if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),
								"Edit Page Back Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Edit Page Back Button", YesNo.No);
							ThreadSleep(10000);
						} else {
							sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
							log(LogStatus.SKIP, "Not Able to Click on Edit Page Back Button", YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on Edit Page SetUp Link");
					}
				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			refresh(driver);
			ThreadSleep(7000);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc005_2_VerifyTheRetainAndDefaultSelectionOfToggleButton(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };
		String[] toggles = null;
		String toggleButton;
		WebElement ele = null;

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);
					relatedTab = relatedTabs[i];
					toggles = toggleButtons[i].split(breakSP);
					if (click(driver, ip.getRelatedTab(projectName, relatedTab, 120), relatedTab, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
						ThreadSleep(2000);

						for (int j = toggles.length - 1; j >= 0; j--) {

							toggleButton = toggles[j];
							if (j == 1) {

								ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
								if (ele != null) {
									log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
									ThreadSleep(2000);
								} else {
									sa.assertTrue(false, "Toggle should be present : " + toggleButton);
									log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
								}

								if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
										action.BOOLEAN, true, 30).getAttribute("class").contains("brand")) {
									log(LogStatus.PASS, toggleButton + " is selected by default after changing setting",
											YesNo.No);
								} else {
									sa.assertTrue(false,
											toggleButton + " should be selected by default after changing setting");
									log(LogStatus.FAIL,
											toggleButton + " should be selected by defaul after changing settingt",
											YesNo.Yes);
								}

								toggleButton = toggles[0];

								ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
								if (ele != null) {
									log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
									ThreadSleep(2000);
								} else {
									sa.assertTrue(false, "Toggle should be present : " + toggleButton);
									log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
								}

								if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
										action.BOOLEAN, true, 2).getAttribute("class").contains("neutral")) {
									log(LogStatus.PASS,
											toggleButton + " is not selected by default after changing setting",
											YesNo.No);
								} else {
									sa.assertTrue(false,
											toggleButton + " should not be selected by default after changing setting");
									log(LogStatus.FAIL,
											toggleButton + " should not be selected by default after changing setting",
											YesNo.Yes);
								}

							} else {
								click(driver, ele, "toggle: " + toggleButton, action.SCROLLANDBOOLEAN);
								ThreadSleep(3000);
								if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
										action.BOOLEAN, true, 10).getAttribute("class").contains("brand")) {
									log(LogStatus.PASS, "After click " + toggleButton + " is visible", YesNo.No);
								} else {
									sa.assertTrue(false, "After click " + toggleButton + " should be visible");
									log(LogStatus.FAIL, "After click " + toggleButton + " should be visible",
											YesNo.Yes);
								}
							}
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
						log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
					}

				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			ThreadSleep(2000);
			// break;
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc006_1_VerifyToAddNewToggleButtonWithMaxAndSpecialCharacter(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String customSdgNAME = customObject + Sdg1Name;
		String sdgConfigDataProviderTextBox = ActiveDealToggleButton + ":" + Sdg1Name;

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };

		String fileLocation[] = { ".\\AutoIT\\EditPage\\OurEventInvitee.PNG", ".\\AutoIT\\EditPage\\Closed.PNG",
				".\\AutoIT\\EditPage\\OurEvent.PNG" };

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);

					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO, "click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);
						//// scn.nextLine();
						switchToDefaultContent(driver);
						switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));

						relatedTab = relatedTabs[i];
						if (clickUsingJavaScript(driver, ip.getRelatedTab(projectName, relatedTab, 120),
								relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
							ThreadSleep(2000);

							String[] toggles = toggleButtons[i].split(breakSP);
							String toggleBtn = "";
							for (int j = 0; j < 1; j++) {
								toggleBtn = toggles[j];
								switchToDefaultContent(driver);
								ThreadSleep(20000);
								/////////////////////////////////////////////////////
								String sValue = EditPageErrorMessage.NavatarSDG;
								if (sendKeys(driver, edit.getEditPageSeachTextBox(projectName, 10), sValue,
										"Search TextBox", action.BOOLEAN)) {
									ThreadSleep(2000);
									log(LogStatus.INFO, "send value to Search TextBox : " + sValue, YesNo.No);
									String psource = fileLocation[i];
									System.err.println("path of file : " + psource);
									// // scn.nextLine();
									if (edit.dragNDropUsingScreen(projectName, EnhanceLightningGridImg, psource, 20)) {
										log(LogStatus.INFO, "Able to DragNDrop : " + sValue, YesNo.No);
										ThreadSleep(2000);
										try {
											edit.getElgDataProviderTextBox(projectName, 10).clear();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										ThreadSleep(2000);
										if (edit.clickOnELGSeachValueLink(projectName, customSdgNAME, 20)) {
											log(LogStatus.INFO, "Click on ELG Search Vaue Link: " + customSdgNAME,
													YesNo.No);
											;
											ThreadSleep(500);
										} else {
											sa.assertTrue(false,
													"Not Able to Click on ELG Search Vaue Link: " + customSdgNAME);
											log(LogStatus.SKIP,
													"Not Able to Click on ELG Search Vaue Link: " + customSdgNAME,
													YesNo.Yes);
										}
										if (sendKeys(driver, edit.getElgTitleTextBox(projectName, 10),
												ActiveDealToggleButton, "ELG Title TextBox", action.BOOLEAN)) {
											ThreadSleep(500);
											log(LogStatus.INFO,
													"send value to ELG Title TextBox : " + ActiveDealToggleButton,
													YesNo.No);

										} else {
											sa.assertTrue(false, "Not Able to send value to ELG Title TextBox : "
													+ ActiveDealToggleButton);
											log(LogStatus.FAIL, "Not Able to send value to ELG Title TextBox : "
													+ ActiveDealToggleButton, YesNo.Yes);
										}

										// // scn.nextLine();
										if (click(driver, edit.getEnableToggleCheckBox(projectName, 10),
												"Enable Toggle CheckBox", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "click on Enable Toggle CheckBox", YesNo.No);
										} else {

											sa.assertTrue(false, "Not Able to click on Enable Toggle CheckBox");
											log(LogStatus.FAIL, "Not Able to click on Enable Toggle CheckBox",
													YesNo.Yes);

										}

									} else {
										sa.assertTrue(false, "Not Able to DragNDrop : " + sValue);
										log(LogStatus.FAIL, "Not Able to DragNDrop : " + sValue, YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to send value to Search TextBox : " + sValue);
									log(LogStatus.FAIL, "Not Able to send value to Search TextBox : " + sValue,
											YesNo.Yes);
								}

								switchToDefaultContent(driver);
								switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));
								if (click(driver, ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30),
										toggleBtn, action.BOOLEAN)) {
									log(LogStatus.INFO, "Click on Toggle : " + toggleBtn, YesNo.No);
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									if (sendKeysWithoutClearingTextBox(driver,
											edit.getsdgConfigDataProviderTextBox(projectName, 10),
											"," + sdgConfigDataProviderTextBox,
											"sdg Config Data Provider TextBox : " + sdgConfigDataProviderTextBox,
											action.BOOLEAN)) {
										ThreadSleep(500);
										log(LogStatus.INFO, "send value to sdg Config Data Provider TextBox : "
												+ sdgConfigDataProviderTextBox, YesNo.No);

										if (click(driver, edit.getEditPageSaveButton(projectName, 10),
												"Edit Page Save Button", action.BOOLEAN)) {
											log(LogStatus.INFO, "Click on Edit Page Save Button", YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
											log(LogStatus.FAIL, "Not Able to Click on Edit Page Save Button",
													YesNo.Yes);
										}
										toggleBtn = ActiveDealToggleButton;
										switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));
										if (ip.toggleButton(projectName, PageName.Object1Page, toggleBtn,
												action.BOOLEAN, 30) != null) {
											log(LogStatus.INFO,
													"After Adding , New Toggle Button is present : " + toggleBtn,
													YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false, "After Adding , New Toggle Button should be present : "
													+ toggleBtn);
											log(LogStatus.FAIL,
													"After Adding , New Toggle Button shoud be  present : " + toggleBtn,
													YesNo.Yes);
										}

									} else {
										sa.assertTrue(false,
												"Not Able to send value to sdg Config Data Provider TextBox : "
														+ sdgConfigDataProviderTextBox);
										log(LogStatus.FAIL,
												"Not Able to send value to sdg Config Data Provider TextBox : "
														+ sdgConfigDataProviderTextBox,
												YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to Click on Toggle : " + toggleBtn);
									log(LogStatus.SKIP, "Not Able to Click on Toggle : " + toggleBtn, YesNo.Yes);
								}

							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
							log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
						}
						ThreadSleep(5000);
						switchToDefaultContent(driver);
						if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),
								"Edit Page Back Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Edit Page Back Button", YesNo.No);
						} else {
							sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
							log(LogStatus.SKIP, "Not Able to Click on Edit Page Back Button", YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on Edit Page SetUp Link");
					}

				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
				ThreadSleep(5000);
				refresh(driver);
				ThreadSleep(7000);
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			refresh(driver);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc006_2_VerifyToAddNewToggleButtonWithMaxAndSpecialCharacter(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };
		String[] toggles = null;
		String toggleButton;
		WebElement ele = null;

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);
					relatedTab = relatedTabs[i];
					toggles = toggleButtons[i].split(breakSP);
					if (click(driver, ip.getRelatedTab(projectName, relatedTab, 120), relatedTab, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
						ThreadSleep(2000);

						for (int j = toggles.length - 1; j >= 0; j--) {

							toggleButton = toggles[j];
							if (j == 1) {

								ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
								if (ele != null) {
									log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
									ThreadSleep(2000);
								} else {
									sa.assertTrue(false, "Toggle should be present : " + toggleButton);
									log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
								}

								if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
										action.BOOLEAN, true, 30).getAttribute("class").contains("brand")) {
									log(LogStatus.PASS, toggleButton + " is selected by default after changing setting",
											YesNo.No);
								} else {
									sa.assertTrue(false,
											toggleButton + " should be selected by default after changing setting");
									log(LogStatus.FAIL,
											toggleButton + " should be selected by defaul after changing settingt",
											YesNo.Yes);
								}

								toggleButton = toggles[0];

								ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
								if (ele != null) {
									log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
									ThreadSleep(2000);
								} else {
									sa.assertTrue(false, "Toggle should be present : " + toggleButton);
									log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
								}

								if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
										action.BOOLEAN, true, 2).getAttribute("class").contains("neutral")) {
									log(LogStatus.PASS,
											toggleButton + " is not selected by default after changing setting",
											YesNo.No);
								} else {
									sa.assertTrue(false,
											toggleButton + " should not be selected by default after changing setting");
									log(LogStatus.FAIL,
											toggleButton + " should not be selected by default after changing setting",
											YesNo.Yes);
								}

								toggleButton = ActiveDealToggleButton;

								ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
								if (ele != null) {
									log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
									ThreadSleep(2000);
								} else {
									sa.assertTrue(false, "Toggle should be present : " + toggleButton);
									log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
								}

							} else {
								toggleButton = ActiveDealToggleButton;
								ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
								click(driver, ele, "toggle: " + toggleButton, action.SCROLLANDBOOLEAN);
								ThreadSleep(3000);
								if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
										action.BOOLEAN, true, 10) != null) {
									log(LogStatus.PASS, "After click " + toggleButton + " is visible", YesNo.No);
								} else {
									sa.assertTrue(false, "After click " + toggleButton + " should be visible");
									log(LogStatus.FAIL, "After click " + toggleButton + " should be visible",
											YesNo.Yes);
								}
							}
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
						log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
					}

				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			ThreadSleep(2000);
			// break;
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc007_1_CreateNewToggleButtonAsDefaultButton(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };

		String toggleValue = Sdg1Name;
		String toggleButton = ActiveDealToggleButton;

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);
					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO, "click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);
						//// scn.nextLine();
						switchToDefaultContent(driver);
						switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));

						relatedTab = relatedTabs[i];
						if (clickUsingJavaScript(driver, ip.getRelatedTab(projectName, relatedTab, 120),
								relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
							ThreadSleep(2000);

							String[] toggles = toggleButtons[i].split(breakSP);
							String toggleBtn = "";
							for (int j = 0; j < 1; j++) {
								toggleBtn = toggles[j];
								if (clickUsingJavaScript(driver,
										ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30), toggleBtn,
										action.BOOLEAN)) {
									log(LogStatus.INFO, "Able to Click on Toggle : " + toggleBtn, YesNo.No);
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									ThreadSleep(20000);
									/////////////////////////////////////////////
									switchToDefaultContent(driver);
									if (sendKeys(driver, edit.getDefaultSDGToggleTextBox(projectName, 10), toggleValue,
											"Default SDG Toggle TextBox", action.BOOLEAN)) {
										ThreadSleep(200);
										log(LogStatus.INFO, "send value to Default SDG Toggle TextBox : " + toggleValue,
												YesNo.No);
										if (click(driver, edit.getEditPageSaveButton(projectName, 10),
												"Edit Page Save Button", action.BOOLEAN)) {
											log(LogStatus.INFO, "Click on Edit Page Save Button", YesNo.No);
											;
											ThreadSleep(5000);
											switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));
											if (ip.toggleSDGButtons(projectName, toggleButton,
													ToggleButtonGroup.SDGButton, action.BOOLEAN, true, 10)
													.getAttribute("class").contains("brand")) {
												log(LogStatus.PASS, "After Save " + toggleButton + " is selected ",
														YesNo.No);
											} else {
												sa.assertTrue(false,
														"After Save " + toggleButton + " should be selected ");
												log(LogStatus.FAIL,
														"After Save " + toggleButton + " should be selected ",
														YesNo.Yes);
											}
											switchToDefaultContent(driver);
											if (clickUsingJavaScript(driver,
													edit.getEditPageBackButton(projectName, 10),
													"Edit Page Back Button", action.BOOLEAN)) {
												log(LogStatus.INFO, "Click on Edit Page Back Button", YesNo.No);
												//// scn.nextLine();
												ThreadSleep(10000);
												if (click(driver, ip.getRelatedTab(projectName, relatedTab, 30),
														relatedTab, action.BOOLEAN)) {
													log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
													ThreadSleep(2000);
													if (ip.toggleSDGButtons(projectName, toggleButton,
															ToggleButtonGroup.SDGButton, action.BOOLEAN, true, 10)
															.getAttribute("class").contains("brand")) {
														log(LogStatus.PASS,
																"After Save " + toggleButton + " is selected ",
																YesNo.No);
													} else {
														sa.assertTrue(false,
																"After Save " + toggleButton + " should be selected ");
														log(LogStatus.FAIL,
																"After Save " + toggleButton + " should be selected ",
																YesNo.Yes);
													}
												} else {
													sa.assertTrue(false,
															"Not Able to Click on Sub Tab : " + relatedTab);
													log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab,
															YesNo.Yes);
												}

											} else {
												sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
												log(LogStatus.SKIP, "Not Able to Click on Edit Page Back Button",
														YesNo.Yes);
											}

										} else {
											sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
											log(LogStatus.SKIP, "Not Able to Click on Edit Page Save Button",
													YesNo.Yes);
										}

									} else {
										sa.assertTrue(false, "Not Able to send value to Default SDG Toggle TextBox : "
												+ toggleValue);
										log(LogStatus.SKIP,
												"Not Able to send value to Default SDG Toggle TextBox : " + toggleValue,
												YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to Click on Toggle : " + toggleBtn);
									log(LogStatus.SKIP, "Not Able to Click on Toggle : " + toggleBtn, YesNo.Yes);
								}

							}
						} else {
							sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
							log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
						}
						clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),
								"Edit Page Back Button", action.BOOLEAN);
					} else {
						log(LogStatus.ERROR, "Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on Edit Page SetUp Link");
					}
				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			refresh(driver);
			ThreadSleep(7000);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc007_2_CreateNewToggleButtonAsDefaultButton(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };
		String[] toggles = null;
		String toggleButton;
		WebElement ele = null;

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);
					relatedTab = relatedTabs[i];
					toggles = toggleButtons[i].split(breakSP);
					if (click(driver, ip.getRelatedTab(projectName, relatedTab, 120), relatedTab, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
						ThreadSleep(2000);
						toggleButton = ActiveDealToggleButton;
						ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
						if (ele != null) {
							log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Toggle should be present : " + toggleButton);
							log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
						}

						if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton, action.BOOLEAN,
								true, 30).getAttribute("class").contains("brand")) {
							log(LogStatus.PASS, toggleButton + " is selected by default after changing setting",
									YesNo.No);
						} else {
							sa.assertTrue(false,
									toggleButton + " should be selected by default after changing setting");
							log(LogStatus.FAIL, toggleButton + " should be selected by default after changing setting",
									YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
						log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
					}

				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			ThreadSleep(2000);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc008_VerifyOutSideAndInsideSdgInContainerAndEnableToggleSwitchingAndVerifySDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String customSDGFileLocation = ".\\AutoIT\\EditPage\\CustomSDG.PNG";
		String outSideContainerLocation = ".\\AutoIT\\EditPage\\AddComponent.PNG";

		if (lp.CRMLogin(superAdminUserName, adminPassword, appName)) {
			log(LogStatus.INFO, "login with Admin user so going to dragNdrop custom Sdg outside container with Admin",
					YesNo.No);
			for (int i = 0; i < tabNames.length; i++) {
				tabName = tabNames[i];
				if (lp.clickOnTab(projectName, tabName)) {
					log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
					itemValue = itemValues[i];
					if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
						log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
						if (hp.clickOnEditPageLinkOnSetUpLink()) {
							log(LogStatus.INFO, "click on Edit Page SetUp Link", YesNo.No);
							switchToDefaultContent(driver);
							switchToFrame(driver, 200, edit.getEditPageFrame(projectName, 400));
							ThreadSleep(30000);
							relatedTab = relatedTabs[i];
							if (clickUsingJavaScript(driver, ip.getRelatedTab(projectName, relatedTab, 120),
									relatedTab.toString(), action.BOOLEAN)) {
								log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
//								String toggleButton = ActiveDealToggleButton;
//								WebElement ele=ip.toggleSDGButtons(projectName, toggleButton,ToggleButtonGroup.SDGButton, action.BOOLEAN, 200);
//								click(driver, ele,"sdg: "+toggleButton, action.BOOLEAN);
								ThreadSleep(30000);
								if (i == 2) {
									// outSideContainerLocation = ".\\AutoIT\\EditPage\\DropdownIcon.PNG";
								}
								if (edit.dragNDropUsingScreen(projectName, customSDGFileLocation,
										outSideContainerLocation, 20)) {
									log(LogStatus.INFO, "Able to DragNDrop Custom SDG to Outside container", YesNo.No);
									switchToDefaultContent(driver);
									ThreadSleep(5000);
									if (click(driver, edit.getEditPageSaveButton(projectName, 10),
											"Edit Page Save Button", action.BOOLEAN)) {
										log(LogStatus.INFO, "Click on Edit Page Save Button", YesNo.No);
										ThreadSleep(10000);
									} else {
										sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
										log(LogStatus.FAIL, "Not Able to Click on Edit Page Save Button", YesNo.Yes);
									}
								} else {
									sa.assertTrue(false, "Not Able to DragNDrop Custom SDG to Outside container");
									log(LogStatus.FAIL, "Not Able to DragNDrop Custom SDG to Outside container",
											YesNo.Yes);
								}

							} else {
								sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
								log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
							}
							switchToDefaultContent(driver);
							if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),
									"Edit Page Back Button", action.BOOLEAN)) {
								log(LogStatus.INFO, "Click on Edit Page Back Button", YesNo.No);
								//// scn.nextLine();
							} else {
								sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
								log(LogStatus.SKIP, "Not Able to Click on Edit Page Back Button", YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Not Able to click on Edit Page SetUp Link", YesNo.Yes);
							sa.assertTrue(false, "Not Able to click on Edit Page SetUp Link");
						}
					} else {

						log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
						sa.assertTrue(false, "Item not found: " + itemValue);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
				}
			}
			ThreadSleep(5000);
			refresh(driver);
			ThreadSleep(5000);
		} else {
			sa.assertTrue(false,
					"Not Able to login with Admin user so cannot dragNdrop custom Sdg outside container with Admin");
			log(LogStatus.SKIP,
					"Not Able to login with Admin user so cannot dragNdrop custom Sdg outside container with Admin",
					YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc009_VerifyOutSideAndInsideSdgInContainerAndEnableToggleSwitchingAndVerifySDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };
		String[] toggles = null;
		String toggleButton;
		WebElement ele = null;

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);
					relatedTab = relatedTabs[i];
					toggles = toggleButtons[i].split(breakSP);
					if (click(driver, ip.getRelatedTab(projectName, relatedTab, 30), relatedTab, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
						ThreadSleep(2000);

						for (int j = 0; j < toggles.length; j++) {
							toggleButton = toggles[j];
							ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
							if (ele != null) {
								log(LogStatus.INFO,
										"After deleting custom toggle , Toggle is present : " + toggleButton, YesNo.No);
								ThreadSleep(2000);
							} else {
								sa.assertTrue(false,
										"After deleting custom toggle , Toggle should be present : " + toggleButton);
								log(LogStatus.SKIP,
										"After deleting custom toggle , Toggle should be present : " + toggleButton,
										YesNo.Yes);
							}

						}

						toggleButton = ActiveDealToggleButton;
						ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 3);
						if (ele != null) {
							log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Toggle should be present : " + toggleButton);
							log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
						}

						ele = ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
								action.BOOLEAN, false, 10);
						if (ele != null) {
							log(LogStatus.PASS,
									"After Moving Custom SDG  " + toggleButton + " is present outside container",
									YesNo.No);
						} else {
							sa.assertTrue(false, "After Moving Custom SDG  " + toggleButton
									+ " should be present outside container");
							log(LogStatus.FAIL,
									"After Moving Custom SDG  " + toggleButton + " should be present outside container",
									YesNo.Yes);
						}

						ele = ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
								action.BOOLEAN, true, 10);
						if (ele == null) {
							log(LogStatus.PASS,
									"After Moving Custom SDG  " + toggleButton + " is not present inside container",
									YesNo.No);
						} else {
							sa.assertTrue(false, "After Moving Custom SDG  " + toggleButton
									+ " should not be present inside container");
							log(LogStatus.FAIL, "After Moving Custom SDG  " + toggleButton
									+ " should not be present inside container", YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
						log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
					}

				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			ThreadSleep(2000);
			// break;
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc010_CreateTwoMoreCustomSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String fields = SDGLabels.APIName.toString();
		String values = "";
		lp.searchAndClickOnApp(SDG, 30);

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);
			String[][] sdgLabels = { { SDGCreationLabel.SDG_Name.toString(), M5Sdg4Name },
					{ SDGCreationLabel.SDG_Tag.toString(), M5Sdg4TagName },
					{ SDGCreationLabel.sObjectName.toString(), M5Sdg4ObjectName } };

			if (sdg.createCustomSDG(projectName, M5Sdg4Name, sdgLabels, action.BOOLEAN, 20)) {
				log(LogStatus.PASS, "create/verify created SDG : " + M5Sdg4Name, YesNo.No);
				String api = ExcelUtils.readData(phase1DataSheetFilePath, "Fields", excelLabel.Variable_Name, "CField1",
						excelLabel.APIName);
				values = api;
				if (sdg.addFieldOnSDG(projectName, fields, values)) {
					log(LogStatus.INFO, "Successfully added fields on " + M5Sdg4Name, YesNo.Yes);

				} else {
					sa.assertTrue(false, "Not Able to add fields on SDG : " + M5Sdg4Name);
					log(LogStatus.SKIP, "Not Able to add fields on SDG : " + M5Sdg4Name, YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not Able to create/verify created SDG : " + M5Sdg4Name);
				log(LogStatus.SKIP, "Not Able to create/verify created SDG : " + M5Sdg4Name, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
		}

		ThreadSleep(3000);
		lp.searchAndClickOnApp(SDG, 30);

		if (lp.clickOnTab(projectName, TabName.SDGTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.SDGTab, YesNo.No);

			String[][] sdgLabels = { { SDGCreationLabel.SDG_Name.toString(), M5Sdg5Name },
					{ SDGCreationLabel.SDG_Tag.toString(), M5Sdg5TagName },
					{ SDGCreationLabel.sObjectName.toString(), M5Sdg5ObjectName } };

			if (sdg.createCustomSDG(projectName, M5Sdg5Name, sdgLabels, action.BOOLEAN, 20)) {
				log(LogStatus.PASS, "create/verify created SDG : " + M5Sdg5Name, YesNo.No);
				String api = ExcelUtils.readData(phase1DataSheetFilePath, "Fields", excelLabel.Variable_Name, "CField1",
						excelLabel.APIName);
				values = api;
				if (sdg.addFieldOnSDG(projectName, fields, values)) {
					log(LogStatus.INFO, "Successfully added fields on " + M5Sdg5Name, YesNo.Yes);

				} else {
					sa.assertTrue(false, "Not Able to add fields on SDG : " + M5Sdg5Name);
					log(LogStatus.SKIP, "Not Able to add fields on SDG : " + M5Sdg5Name, YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not Able to create/verify created SDG : " + M5Sdg5Name);
				log(LogStatus.SKIP, "Not Able to create/verify created SDG : " + M5Sdg5Name, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.SDGTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.SDGTab, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc011_VerifyToAddOneMoreNewToggleButtonOnebyOne(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String customSdgNAME = customObject + M5Sdg4Name;
		String sdgConfigDataProviderTextBox = M5Sdg4Name + ":" + M5Sdg4Name;

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };

		String fileLocation[] = { ".\\AutoIT\\EditPage\\OurEventInvitee1.PNG", ".\\AutoIT\\EditPage\\Closed1.PNG",
				".\\AutoIT\\EditPage\\OurEvent1.PNG" };

		boolean flag = false;
		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);

					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO, "click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);
						//// scn.nextLine();
						switchToDefaultContent(driver);
						switchToFrame(driver, 60, edit.getEditPageFrame(projectName, 200));

						relatedTab = relatedTabs[i];
						if (clickUsingJavaScript(driver, ip.getRelatedTab(projectName, relatedTab, 120),
								relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
							ThreadSleep(2000);

							String[] toggles = toggleButtons[i].split(breakSP);
							String toggleBtn = "";
							for (int j = 0; j < 1; j++) {
								toggleBtn = toggles[j];
								switchToDefaultContent(driver);
								ThreadSleep(20000);
								/////////////////////////////////////////////////////
								String sValue = EditPageErrorMessage.NavatarSDG;
								if (sendKeys(driver, edit.getEditPageSeachTextBox(projectName, 10), sValue,
										"Search TextBox", action.BOOLEAN)) {
									ThreadSleep(2000);
									log(LogStatus.INFO, "send value to Search TextBox : " + sValue, YesNo.No);
									String psource = fileLocation[i];
									System.err.println("path of file : " + psource);
									switchToDefaultContent(driver);
									switchToFrame(driver, 20, edit.getEditPageFrame(projectName, 20));
									toggleBtn = toggles[j];
									WebElement ele = ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30);
									scrollDownThroughWebelement(driver, ele, toggleBtn);
									switchToDefaultContent(driver);
									// // scn.nextLine();
									if (edit.dragNDropUsingScreen(projectName, EnhanceLightningGridImg, psource, 20)) {
										log(LogStatus.INFO, "Able to DragNDrop : " + sValue, YesNo.No);
										ThreadSleep(2000);
										try {
											edit.getElgDataProviderTextBox(projectName, 10).clear();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										ThreadSleep(2000);
										if (edit.clickOnELGSeachValueLink(projectName, customSdgNAME, 20)) {
											log(LogStatus.INFO, "Click on ELG Search Vaue Link: " + customSdgNAME,
													YesNo.No);
											;
											ThreadSleep(500);
										} else {
											sa.assertTrue(false,
													"Not Able to Click on ELG Search Vaue Link: " + customSdgNAME);
											log(LogStatus.SKIP,
													"Not Able to Click on ELG Search Vaue Link: " + customSdgNAME,
													YesNo.Yes);
										}
										if (sendKeys(driver, edit.getElgTitleTextBox(projectName, 10), M5Sdg4Name,
												"ELG Title TextBox", action.BOOLEAN)) {
											ThreadSleep(500);
											log(LogStatus.INFO, "send value to ELG Title TextBox : " + M5Sdg4Name,
													YesNo.No);

										} else {
											sa.assertTrue(false,
													"Not Able to send value to ELG Title TextBox : " + M5Sdg4Name);
											log(LogStatus.FAIL,
													"Not Able to send value to ELG Title TextBox : " + M5Sdg4Name,
													YesNo.Yes);
										}

										// // scn.nextLine();
										if (click(driver, edit.getEnableToggleCheckBox(projectName, 10),
												"Enable Toggle CheckBox", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "click on Enable Toggle CheckBox", YesNo.No);
										} else {

											sa.assertTrue(false, "Not Able to click on Enable Toggle CheckBox");
											log(LogStatus.FAIL, "Not Able to click on Enable Toggle CheckBox",
													YesNo.Yes);

										}

									} else {
										sa.assertTrue(false, "Not Able to DragNDrop : " + sValue);
										log(LogStatus.FAIL, "Not Able to DragNDrop : " + sValue, YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to send value to Search TextBox : " + sValue);
									log(LogStatus.FAIL, "Not Able to send value to Search TextBox : " + sValue,
											YesNo.Yes);
								}

								switchToDefaultContent(driver);
								switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));
								if (click(driver, ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30),
										toggleBtn, action.BOOLEAN)) {
									log(LogStatus.INFO, "Click on Toggle : " + toggleBtn, YesNo.No);
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									if (sendKeysWithoutClearingTextBox(driver,
											edit.getsdgConfigDataProviderTextBox(projectName, 10),
											"," + sdgConfigDataProviderTextBox,
											"sdg Config Data Provider TextBox : " + sdgConfigDataProviderTextBox,
											action.BOOLEAN)) {
										ThreadSleep(500);
										log(LogStatus.INFO, "send value to sdg Config Data Provider TextBox : "
												+ sdgConfigDataProviderTextBox, YesNo.No);

										if (click(driver, edit.getEditPageSaveButton(projectName, 10),
												"Edit Page Save Button", action.BOOLEAN)) {
											log(LogStatus.INFO, "Click on Edit Page Save Button", YesNo.No);
											ThreadSleep(5000);
										} else {
											sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
											log(LogStatus.FAIL, "Not Able to Click on Edit Page Save Button",
													YesNo.Yes);
										}
										toggleBtn = M5Sdg4Name;
										switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));
										if (ip.toggleButton(projectName, PageName.Object1Page, toggleBtn,
												action.BOOLEAN, 30) != null) {
											log(LogStatus.INFO, "New Toggle Button Added : " + toggleBtn, YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false, "New Toggle Button Added : " + toggleBtn);
											log(LogStatus.FAIL, "New Toggle Button Added : " + toggleBtn, YesNo.Yes);
										}

									} else {
										sa.assertTrue(false,
												"Not Able to send value to sdg Config Data Provider TextBox : "
														+ sdgConfigDataProviderTextBox);
										log(LogStatus.FAIL,
												"Not Able to send value to sdg Config Data Provider TextBox : "
														+ sdgConfigDataProviderTextBox,
												YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to Click on Toggle : " + toggleBtn);
									log(LogStatus.SKIP, "Not Able to Click on Toggle : " + toggleBtn, YesNo.Yes);
								}

							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
							log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
						}
						ThreadSleep(5000);
						switchToDefaultContent(driver);
						if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),
								"Edit Page Back Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Edit Page Back Button", YesNo.No);
							//// scn.nextLine();
						} else {
							sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
							log(LogStatus.SKIP, "Not Able to Click on Edit Page Back Button", YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on Edit Page SetUp Link");
					}

				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			ThreadSleep(5000);
			refresh(driver);
			ThreadSleep(7000);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc012_VerifyTheNewlyCreatedToggleButton(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };
		String[] toggles = null;
		String toggleButton;
		WebElement ele = null;

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);
					relatedTab = relatedTabs[i];
					toggles = toggleButtons[i].split(breakSP);
					if (click(driver, ip.getRelatedTab(projectName, relatedTab, 120), relatedTab, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
						ThreadSleep(2000);

						for (int j = 0; j < toggles.length; j++) {
							toggleButton = toggles[j];
							ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
							if (ele != null) {
								log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
								ThreadSleep(2000);
							} else {
								sa.assertTrue(false, "Toggle should be present : " + toggleButton);
								log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
							}
						}

						toggleButton = ActiveDealToggleButton;
						ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
						if (ele != null) {
							log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Toggle should be present : " + toggleButton);
							log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
						}

						if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton, action.BOOLEAN,
								false, 2).getAttribute("class").contains("brand")) {
							log(LogStatus.PASS, toggleButton + " is selected by default", YesNo.No);
						} else {
							sa.assertTrue(false, toggleButton + " should be selected by default");
							log(LogStatus.FAIL, toggleButton + " should be selected by default", YesNo.Yes);
						}

						toggleButton = M5Sdg4Name;
						ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
						if (ele != null) {
							log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Toggle should be present : " + toggleButton);
							log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
						log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
					}

				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			ThreadSleep(2000);
			// break;
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc013_VerifyToAddOneMoreNewToggleButtonOnebyOne(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String customSdgNAME = customObject + M5Sdg5Name;
		String sdgConfigDataProviderTextBox = M5Sdg5Name + ":" + M5Sdg5Name;

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };

		String fileLocation[] = { ".\\AutoIT\\EditPage\\OurEventInvitee1.PNG", ".\\AutoIT\\EditPage\\Closed1.PNG",
				".\\AutoIT\\EditPage\\OurEvent1.PNG" };

		boolean flag = false;
		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);

					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO, "click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);
						//// scn.nextLine();
						switchToDefaultContent(driver);
						switchToFrame(driver, 60, edit.getEditPageFrame(projectName, 200));

						relatedTab = relatedTabs[i];
						if (clickUsingJavaScript(driver, ip.getRelatedTab(projectName, relatedTab, 200),
								relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
							ThreadSleep(2000);

							String[] toggles = toggleButtons[i].split(breakSP);
							String toggleBtn = "";
							for (int j = 0; j < 1; j++) {
								toggleBtn = toggles[j];
								switchToDefaultContent(driver);
								ThreadSleep(20000);
								/////////////////////////////////////////////////////
								String sValue = EditPageErrorMessage.NavatarSDG;
								if (sendKeys(driver, edit.getEditPageSeachTextBox(projectName, 10), sValue,
										"Search TextBox", action.BOOLEAN)) {
									ThreadSleep(2000);
									log(LogStatus.INFO, "send value to Search TextBox : " + sValue, YesNo.No);
									String psource = fileLocation[i];
									System.err.println("path of file : " + psource);
									switchToDefaultContent(driver);
									switchToFrame(driver, 20, edit.getEditPageFrame(projectName, 20));
									toggleBtn = toggles[j];
									WebElement ele = ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30);
									scrollDownThroughWebelement(driver, ele, toggleBtn);
									switchToDefaultContent(driver);
									// // scn.nextLine();
									if (edit.dragNDropUsingScreen(projectName, EnhanceLightningGridImg, psource, 20)) {
										log(LogStatus.INFO, "Able to DragNDrop : " + sValue, YesNo.No);
										ThreadSleep(2000);
										try {
											edit.getElgDataProviderTextBox(projectName, 10).clear();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										ThreadSleep(2000);
										if (edit.clickOnELGSeachValueLink(projectName, customSdgNAME, 20)) {
											log(LogStatus.INFO, "Click on ELG Search Vaue Link: " + customSdgNAME,
													YesNo.No);
											;
											ThreadSleep(500);
										} else {
											sa.assertTrue(false,
													"Not Able to Click on ELG Search Vaue Link: " + customSdgNAME);
											log(LogStatus.SKIP,
													"Not Able to Click on ELG Search Vaue Link: " + customSdgNAME,
													YesNo.Yes);
										}
										if (sendKeys(driver, edit.getElgTitleTextBox(projectName, 10), M5Sdg5Name,
												"ELG Title TextBox", action.BOOLEAN)) {
											ThreadSleep(500);
											log(LogStatus.INFO, "send value to ELG Title TextBox : " + M5Sdg5Name,
													YesNo.No);

										} else {
											sa.assertTrue(false,
													"Not Able to send value to ELG Title TextBox : " + M5Sdg5Name);
											log(LogStatus.FAIL,
													"Not Able to send value to ELG Title TextBox : " + M5Sdg5Name,
													YesNo.Yes);
										}

										// // scn.nextLine();
										if (click(driver, edit.getEnableToggleCheckBox(projectName, 10),
												"Enable Toggle CheckBox", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "click on Enable Toggle CheckBox", YesNo.No);
										} else {

											sa.assertTrue(false, "Not Able to click on Enable Toggle CheckBox");
											log(LogStatus.FAIL, "Not Able to click on Enable Toggle CheckBox",
													YesNo.Yes);

										}

									} else {
										sa.assertTrue(false, "Not Able to DragNDrop : " + sValue);
										log(LogStatus.FAIL, "Not Able to DragNDrop : " + sValue, YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to send value to Search TextBox : " + sValue);
									log(LogStatus.FAIL, "Not Able to send value to Search TextBox : " + sValue,
											YesNo.Yes);
								}

								switchToDefaultContent(driver);
								switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));
								if (click(driver, ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30),
										toggleBtn, action.BOOLEAN)) {
									log(LogStatus.INFO, "Click on Toggle : " + toggleBtn, YesNo.No);
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									if (sendKeysWithoutClearingTextBox(driver,
											edit.getsdgConfigDataProviderTextBox(projectName, 10),
											"," + sdgConfigDataProviderTextBox,
											"sdg Config Data Provider TextBox : " + sdgConfigDataProviderTextBox,
											action.BOOLEAN)) {
										ThreadSleep(500);
										log(LogStatus.INFO, "send value to sdg Config Data Provider TextBox : "
												+ sdgConfigDataProviderTextBox, YesNo.No);

										if (click(driver, edit.getEditPageSaveButton(projectName, 10),
												"Edit Page Save Button", action.BOOLEAN)) {
											log(LogStatus.INFO, "Click on Edit Page Save Button", YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
											log(LogStatus.FAIL, "Not Able to Click on Edit Page Save Button",
													YesNo.Yes);
										}
										toggleBtn = M5Sdg5Name;
										switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));
										if (ip.toggleButton(projectName, PageName.Object1Page, toggleBtn,
												action.BOOLEAN, 30) != null) {
											log(LogStatus.INFO, "New Toggle Button Added : " + toggleBtn, YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false, "New Toggle Button Added : " + toggleBtn);
											log(LogStatus.FAIL, "New Toggle Button Added : " + toggleBtn, YesNo.Yes);
										}

									} else {
										sa.assertTrue(false,
												"Not Able to send value to sdg Config Data Provider TextBox : "
														+ sdgConfigDataProviderTextBox);
										log(LogStatus.FAIL,
												"Not Able to send value to sdg Config Data Provider TextBox : "
														+ sdgConfigDataProviderTextBox,
												YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to Click on Toggle : " + toggleBtn);
									log(LogStatus.SKIP, "Not Able to Click on Toggle : " + toggleBtn, YesNo.Yes);
								}

							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
							log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
						}
						ThreadSleep(5000);
						switchToDefaultContent(driver);
						if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),
								"Edit Page Back Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Edit Page Back Button", YesNo.No);
							//// scn.nextLine();
						} else {
							sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
							log(LogStatus.SKIP, "Not Able to Click on Edit Page Back Button", YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on Edit Page SetUp Link");
					}

				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
				refresh(driver);
				ThreadSleep(7000);
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			refresh(driver);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc014_VerifyTheNewlyCreatedToggleButton(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };
		String[] toggles = null;
		String toggleButton;
		WebElement ele = null;

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);
					relatedTab = relatedTabs[i];
					toggles = toggleButtons[i].split(breakSP);
					if (click(driver, ip.getRelatedTab(projectName, relatedTab, 30), relatedTab, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
						ThreadSleep(2000);

						for (int j = 0; j < toggles.length; j++) {
							toggleButton = toggles[j];
							ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
							if (ele != null) {
								log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
								ThreadSleep(2000);
							} else {
								sa.assertTrue(false, "Toggle should be present : " + toggleButton);
								log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
							}
						}

						toggleButton = ActiveDealToggleButton;
						ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
						if (ele != null) {
							log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Toggle should be present : " + toggleButton);
							log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
						}

						if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton, action.BOOLEAN,
								false, 2).getAttribute("class").contains("brand")) {
							log(LogStatus.PASS, toggleButton + " is selected by default", YesNo.No);
						} else {
							sa.assertTrue(false, toggleButton + " should be selected by default");
							log(LogStatus.FAIL, toggleButton + " should be selected by default", YesNo.Yes);
						}

						toggleButton = M5Sdg4Name;
						ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
						if (ele != null) {
							log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Toggle should be present : " + toggleButton);
							log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
						}

						toggleButton = M5Sdg5Name;
						ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
						if (ele != null) {
							log(LogStatus.INFO, "Toggle is present : " + toggleButton, YesNo.No);
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Toggle should be present : " + toggleButton);
							log(LogStatus.SKIP, "Toggle should be present : " + toggleButton, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
						log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
					}

				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			ThreadSleep(2000);
			// break;
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc015_1_DeleteTheAllCustomToggleButton_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };
		String[] sdgToggle = null;
		String[] setDefaultSdgToggle = null;
		String toggleValue = "";

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);

					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO, "click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(1000);
						//// scn.nextLine();
						switchToDefaultContent(driver);
						switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));

						relatedTab = relatedTabs[i];
						if (clickUsingJavaScript(driver, ip.getRelatedTab(projectName, relatedTab, 120),
								relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
							ThreadSleep(2000);

							String[] toggles = toggleButtons[i].split(breakSP);
							String toggleBtn = "";
							for (int j = 0; j < 1; j++) {
								toggleBtn = toggles[j];
								if (click(driver, ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30),
										toggleBtn, action.BOOLEAN)) {
									log(LogStatus.INFO, "Able to Click on Toggle : " + toggleBtn, YesNo.No);
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									ThreadSleep(20000);
									String sdgToggles = getValueFromElementUsingJavaScript(driver,
											edit.getsdgConfigDataProviderTextBox(projectName, 10),
											"sdg Config Data Provider TextBox");
									System.err.println(">>>>> 1 + " + sdgToggles);
									//// scn.nextLine();
									if (sdgToggles != null) {

										sdgToggle = sdgToggles.split(commaSP);
										setDefaultSdgToggle = sdgToggle[1].split(colonSP);
										toggleValue = setDefaultSdgToggle[1];
										toggleValue = "";
										System.err.println(">>>>>>>>>>>>>>> 2 " + sdgToggle[0] + "," + sdgToggle[1]);
										sendKeys(driver, edit.getsdgConfigDataProviderTextBox(projectName, 10),
												sdgToggle[0] + "," + sdgToggle[1], "sdg Config Data Provider TextBox : "
														+ sdgToggle[0] + "," + sdgToggle[1],
												action.BOOLEAN);
										if (sendKeys(driver, edit.getDefaultSDGToggleTextBox(projectName, 10),
												toggleValue, "Default SDG Toggle TextBox: " + toggleValue,
												action.BOOLEAN)) {
											ThreadSleep(500);
											log(LogStatus.INFO,
													"send value to Default SDG Toggle TextBox : " + toggleValue,
													YesNo.No);
											// scn.nextLine();
											if (click(driver, edit.getEditPageSaveButton(projectName, 10),
													"Edit Page Save Button", action.BOOLEAN)) {
												log(LogStatus.INFO, "Click on Edit Page Save Button", YesNo.No);
												ThreadSleep(5000);
												switchToDefaultContent(driver);
												switchToFrame(driver, 30, edit.getEditPageFrame(projectName, 30));
												toggleBtn = toggles[j];

												WebElement ele = ip.toggleButton(projectName, toggleBtn, action.BOOLEAN,
														30);
												if (ele != null) {
													log(LogStatus.INFO,
															"After deleting custom toggle , Toggle is present : "
																	+ toggleBtn,
															YesNo.No);
													ThreadSleep(2000);
												} else {
													sa.assertTrue(false,
															"After deleting custom toggle , Toggle should be present : "
																	+ toggleBtn);
													log(LogStatus.SKIP,
															"After deleting custom toggle , Toggle should be present : "
																	+ toggleBtn,
															YesNo.Yes);
												}

												toggleBtn = toggles[j + 1];
												ele = ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 30);
												if (ele != null) {
													log(LogStatus.INFO,
															"After deleting custom toggle , Toggle is present : "
																	+ toggleBtn,
															YesNo.No);
													ThreadSleep(2000);
												} else {
													sa.assertTrue(false,
															"After deleting custom toggle , Toggle should be present : "
																	+ toggleBtn);
													log(LogStatus.SKIP,
															"After deleting custom toggle , Toggle should be present : "
																	+ toggleBtn,
															YesNo.Yes);
												}

												toggleBtn = M5Sdg4Name;
												ele = ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 3);
												if (ele == null) {
													log(LogStatus.INFO,
															"Toggle is not be present after delete " + toggleBtn,
															YesNo.No);
													ThreadSleep(2000);
												} else {
													sa.assertTrue(false,
															"Toggle should not be present after delete " + toggleBtn);
													log(LogStatus.SKIP,
															"Toggle should not be present after delete " + toggleBtn,
															YesNo.Yes);
												}

												toggleBtn = M5Sdg5Name;
												ele = ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 3);
												if (ele == null) {
													log(LogStatus.INFO,
															"Toggle is not be present after delete " + toggleBtn,
															YesNo.No);
													ThreadSleep(2000);
												} else {
													sa.assertTrue(false,
															"Toggle should not be present after delete " + toggleBtn);
													log(LogStatus.SKIP,
															"Toggle should not be present after delete " + toggleBtn,
															YesNo.Yes);
												}

												toggleBtn = ActiveDealToggleButton;
												ele = ip.toggleButton(projectName, toggleBtn, action.BOOLEAN, 3);
												if (ele == null) {
													log(LogStatus.INFO,
															"Toggle is not be present after delete " + toggleBtn,
															YesNo.No);
													ThreadSleep(2000);
												} else {
													sa.assertTrue(false,
															"Toggle should not be present after delete " + toggleBtn);
													log(LogStatus.SKIP,
															"Toggle should not be present after delete " + toggleBtn,
															YesNo.Yes);
												}
												switchToDefaultContent(driver);

											} else {
												sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
												log(LogStatus.FAIL, "Not Able to Click on Edit Page Save Button",
														YesNo.Yes);
											}

										} else {
											sa.assertTrue(false,
													"Not Able to send value to Default SDG Toggle TextBox : "
															+ toggleValue);
											log(LogStatus.FAIL,
													"Not Able to send value to Default SDG Toggle TextBox : "
															+ toggleValue,
													YesNo.Yes);
										}

										/////////////////////////////////////////////

									} else {
										sa.assertTrue(false,
												"Not Able to retrive input value from SDG Config Data Provider TextBox");
										log(LogStatus.SKIP,
												"Not Able to retrive input value from SDG Config Data Provider TextBox",
												YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to Click on Toggle : " + toggleBtn);
									log(LogStatus.SKIP, "Not Able to Click on Toggle : " + toggleBtn, YesNo.Yes);
								}

							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
							log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
						}

						switchToDefaultContent(driver);
						if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),
								"Edit Page Back Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on Edit Page Back Button", YesNo.No);
							ThreadSleep(10000);
						} else {
							sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
							log(LogStatus.SKIP, "Not Able to Click on Edit Page Back Button", YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false, "Not Able to click on Edit Page SetUp Link");
					}
				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			refresh(driver);
			ThreadSleep(7000);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void M5Tc015_2_DeleteTheAllCustomToggleButton_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] tabNames = { ToggleCheck1TabName, ToggleCheck2TabName, ToggleCheck3TabName };
		String tabName;

		String[] itemValues = { ToggleCheck1ItemName, ToggleCheck2ItemName, ToggleCheck3ItemName };
		String itemValue;

		String[] relatedTabs = { ToggleCheck1RelatedTab, ToggleCheck2RelatedTab, ToggleCheck3RelatedTab };
		String relatedTab;

		String toggleButtons[] = { ToggleCheck1ToggleButtons, ToggleCheck2ToggleButtons, ToggleCheck3ToggleButtons };
		String[] toggles = null;
		String toggleButton;
		WebElement ele = null;

		for (int i = 0; i < tabNames.length; i++) {
			tabName = tabNames[i];
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
				itemValue = itemValues[i];
				if (ip.clickOnAlreadyCreatedItem(projectName, itemValue, true, 15)) {
					log(LogStatus.INFO, "Item found: " + itemValue, YesNo.Yes);
					ThreadSleep(2000);
					relatedTab = relatedTabs[i];
					toggles = toggleButtons[i].split(breakSP);
					if (click(driver, ip.getRelatedTab(projectName, relatedTab, 30), relatedTab, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
						ThreadSleep(2000);

						for (int j = 0; j < toggles.length; j++) {
							toggleButton = toggles[j];
							ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
							if (ele != null) {
								log(LogStatus.INFO,
										"After deleting custom toggle , Toggle is present : " + toggleButton, YesNo.No);
								ThreadSleep(2000);
							} else {
								sa.assertTrue(false,
										"After deleting custom toggle , Toggle should be present : " + toggleButton);
								log(LogStatus.SKIP,
										"After deleting custom toggle , Toggle should be present : " + toggleButton,
										YesNo.Yes);
							}

							if (j == 0) {
								if (ip.toggleSDGButtons(projectName, toggleButton, ToggleButtonGroup.SDGButton,
										action.BOOLEAN, true, 10) != null) {
									log(LogStatus.PASS, "After Deleting Custom toggle  " + toggleButton
											+ " is present & selected by default", YesNo.No);
								} else {
									sa.assertTrue(false, "After Deleting Custom toggle  " + toggleButton
											+ " should be present & selected by default");
									log(LogStatus.FAIL, "After Deleting Custom toggle  " + toggleButton
											+ " should be present & selected by default", YesNo.Yes);
								}
							}
						}

						toggleButton = M5Sdg4Name;
						ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 3);
						if (ele == null) {
							log(LogStatus.INFO, "Toggle is not be present after delete " + toggleButton, YesNo.No);
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Toggle should not be present after delete " + toggleButton);
							log(LogStatus.SKIP, "Toggle should not be present after delete " + toggleButton, YesNo.Yes);
						}

						toggleButton = M5Sdg5Name;
						ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 3);
						if (ele == null) {
							log(LogStatus.INFO, "Toggle is not be present after delete " + toggleButton, YesNo.No);
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Toggle should not be present after delete " + toggleButton);
							log(LogStatus.SKIP, "Toggle should not be present after delete " + toggleButton, YesNo.Yes);
						}

						toggleButton = ActiveDealToggleButton;
						ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 3);
						if (ele == null) {
							log(LogStatus.INFO, "Toggle is not be present after delete " + toggleButton, YesNo.No);
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Toggle should not be present after delete " + toggleButton);
							log(LogStatus.SKIP, "Toggle should not be present after delete " + toggleButton, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
						log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
					}

				} else {

					log(LogStatus.ERROR, "Item not found: " + itemValue, YesNo.Yes);
					sa.assertTrue(false, "Item not found: " + itemValue);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabName);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName, YesNo.Yes);
			}
			ThreadSleep(2000);
			// break;
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

}
