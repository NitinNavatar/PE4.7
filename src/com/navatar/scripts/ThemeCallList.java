package com.navatar.scripts;

import static com.navatar.generic.BaseLib.sa;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.SmokeC6_FName;
import static com.navatar.generic.SmokeCommonVariables.SmokeC6_LName;
import static com.navatar.generic.SmokeCommonVariables.Smoke_OFFLoc1City;
import static com.navatar.generic.SmokeCommonVariables.Smoke_OFFLoc1Country;
import static com.navatar.generic.SmokeCommonVariables.Smoke_OFFLoc1Fax;
import static com.navatar.generic.SmokeCommonVariables.Smoke_OFFLoc1Phone;
import static com.navatar.generic.SmokeCommonVariables.Smoke_OFFLoc1StateProvince;
import static com.navatar.generic.SmokeCommonVariables.Smoke_OFFLoc1Street;
import static com.navatar.generic.SmokeCommonVariables.Smoke_OFFLoc1ZIP;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.IconType;
import com.navatar.generic.EnumConstants.MetaDataSetting;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.OfficeLocationLabel;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PermissionType;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.TaggedName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.recordTypeLabel;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.ResearchPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.FundraisingsPageBusinessLayer;
import com.navatar.pageObjects.ThemePageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class ThemeCallList extends BaseLib {

	String navigationMenuName=NavigationMenuItems.Create.toString().replace("_", " ");
	
@Parameters({ "projectName"})
@Test
	public void TCLTc001_CreateUsers(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	String parentWindow = null;
	String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
	String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
	String emailId = lp.generateRandomEmailId(gmailUserName);
	ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",excelLabel.User_Last_Name);
	lp.CRMLogin(superAdminUserName, adminPassword);
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
						crmUserProfile, null)) {
					log(LogStatus.INFO, "crm User is created Successfully: " + crmUser1FirstName + " " + UserLastName, YesNo.No);
					ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "Userl",
							excelLabel.User_Email);
					ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "Userl",
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
//	if (flag) {
//		if(!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
//			if (setup.installedPackages(environment,mode, crmUser1FirstName, UserLastName)) {
//				appLog.info("PE Package is installed Successfully in CRM User: " + crmUser1FirstName + " "
//						+ UserLastName);
//
//			} else {
//				appLog.error(
//						"Not able to install PE package in CRM User1: " + crmUser1FirstName + " " + UserLastName);
//				sa.assertTrue(false,
//						"Not able to install PE package in CRM User1: " + crmUser1FirstName + " " + UserLastName);
//				log(LogStatus.ERROR,
//						"Not able to install PE package in CRM User1: " + crmUser1FirstName + " " + UserLastName,
//						YesNo.Yes);
//			}
//		}
//		
//
//	}else{
//
//		log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
//		sa.assertTrue(false, "could not click on setup link, test case fail");
//
//	}

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
		appLog.info("Password is set successfully for crm User1: " + crmUser1FirstName + " " + UserLastName );
	} else {
		appLog.info("Password is not set for crm User1: " + crmUser1FirstName + " " + UserLastName);
		sa.assertTrue(false, "Password is not set for crm User1: " + crmUser1FirstName + " " + UserLastName);
		log(LogStatus.ERROR, "Password is not set for crm User1: " + crmUser1FirstName + " " + UserLastName,
				YesNo.Yes);
	}
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
	public void TCLTc002_CreatePreconditionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);//change
	
		String[][] entitys = {{ CLFirm1, CLRecordType1}, { CLFirm2, CLRecordType2},
				{ CLFirm3, CLRecordType3}, { CLFirm4, CLRecordType4}};
	
		ThreadSleep(5000);
		for(int i=0;i<entitys.length;i++) {
			if (lp.clickOnTab(projectName,mode, TabName.AccountsTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.AccountsTab,YesNo.No);	
	
				if (ip.createEntityOrAccount(projectName, mode, entitys[i][0], entitys[i][1], null,null,5)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+entitys[i][0]+" of record type : "+entitys[i][1],YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+entitys[i][0]+" of record type : "+entitys[i][1]);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+entitys[i][0]+" of record type : "+entitys[i][1],YesNo.Yes);
				}
	
	
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.AccountsTab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.AccountsTab,YesNo.Yes);
			}
	
		}
		String[][] contacts = {{CLConFN1, CLConLN1,CLConFirm1,CLConEmail1,CLConTier1,CLConTitle1,CLConPhone1},{CLConFN2, CLConLN2,CLConFirm2,CLConEmail2,CLConTier2,CLConTitle2,CLConPhone2},
		{CLConFN3, CLConLN3,CLConFirm3,CLConEmail3,CLConTier3,CLConTitle3,CLConPhone3},{CLConFN4, CLConLN4,CLConFirm4,CLConEmail4,CLConTier4,CLConTitle4,CLConPhone4},
		{CLConFN5, CLConLN5,CLConFirm5,CLConEmail5,CLConTier5,CLConTitle5,CLConPhone5},{CLConFN6, CLConLN6,CLConFirm6,CLConEmail6,CLConTier6,CLConTitle6,CLConPhone6},
		{CLConFN7, CLConLN7,CLConFirm7,CLConEmail7,CLConTier7,CLConTitle7,CLConPhone7},{CLConFN8, CLConLN8,CLConFirm8,CLConEmail8,CLConTier8,CLConTitle8,CLConPhone8},
		{CLConFN9, CLConLN9,CLConFirm9,CLConEmail9,CLConTier9,CLConTitle9,CLConPhone9},{CLConFN10, CLConLN10,CLConFirm10,CLConEmail10,CLConTier10,CLConTitle10,CLConPhone10},
		{CLConFN11, CLConLN11,CLConFirm11,CLConEmail11,CLConTier11,CLConTitle11,CLConPhone11},{CLConFN12, CLConLN12,CLConFirm12,CLConEmail12,CLConTier12,CLConTitle12,CLConPhone12},
		{CLConFN13, CLConLN13,CLConFirm13,CLConEmail13,CLConTier13,CLConTitle13,CLConPhone13},{CLConFN14, CLConLN14,CLConFirm14,CLConEmail14,CLConTier14,CLConTitle14,CLConPhone14},
		{CLConFN15, CLConLN15,CLConFirm15,CLConEmail15,CLConTier15,CLConTitle15,CLConPhone15},
		{CLConFN16, CLConLN16,CLConFirm16,CLConEmail16,CLConTier16,CLConTitle16,CLConPhone16},
		{CLConFN17, CLConLN17,CLConFirm17,CLConEmail17,CLConTier17,CLConTitle17,CLConPhone17},{CLConFN18, CLConLN18,CLConFirm18,CLConEmail18,CLConTier18,CLConTitle18,CLConPhone18},
		{CLConFN19, CLConLN19,CLConFirm19,CLConEmail19,CLConTier19,CLConTitle19,CLConPhone19},{CLConFN20, CLConLN20,CLConFirm20,CLConEmail20,CLConTier20,CLConTitle20,CLConPhone20}};
	
		ThreadSleep(5000);
		for(int i=0;i<contacts.length;i++) {
			if (lp.clickOnTab(projectName,mode, TabName.ContactTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
	
				if (cp.createContact(environment, mode, contacts[i][0], contacts[i][1], contacts[i][2], contacts[i][3],
						Fields.Phone.toString(), contacts[i][6],CreationPage.ContactPage,contacts[i][5],contacts[i][4])) {//change
					log(LogStatus.INFO, "successfully Created Contact : " + contacts[i][0] + " " + contacts[i][1],
							YesNo.No);
				} else {
					sa.assertTrue(false, "Not Able to Create Contact : " + contacts[i][0] + " " + contacts[i][1]);
					log(LogStatus.SKIP, "Not Able to Create Contact: " + contacts[i][0] + " " + contacts[i][1],
							YesNo.Yes);
				}
	
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.ContactTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.ContactTab, YesNo.Yes);
			}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);//Change
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void TCLTc003_CreateThemeRecordFromNewThemeButton(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	
		String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
		List<String> negativeResult = null;
		String themeTabName = TabName.Themes.toString();
		
		List<String> expectedListOfTabs = Arrays
				.asList(CLThemeTab1.split("<Break>", -1));
		List<String> expectedListOfHeaders = Arrays
				.asList(CLThemeGrid1.split("<break>", -1));
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		if (lp.clickOnTab(projectName, themeTabName)) {
			log(LogStatus.INFO, "Click on Tab : " + themeTabName, YesNo.No);
			if (theme.themeNoItemDisplay(10) != null) {
				log(LogStatus.INFO, "Verified: No items to display msg is showing", YesNo.No);
			} else {
				log(LogStatus.ERROR, "No items to display msg is not showing", YesNo.No);
				sa.assertTrue(false, "No items to display msg is not showing");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + themeTabName + " Tab", YesNo.No);
			sa.assertTrue(false, "Record: " + themeTabName + " has not been Created under: " + themeTabName);
		}
	
		String themeName = themeNameAndDescriptions[0];
		String themeDescription = themeNameAndDescriptions[1];
			if (theme.createTheme(projectName, themeTabName, themeName, themeDescription)) {
				log(LogStatus.INFO, "Record: " + themeName + " has been Created under: " + themeTabName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Record: " + themeName + " has not been Created under: " + themeTabName, YesNo.No);
				sa.assertTrue(false, "Record: " + themeName + " has not been Created under: " + themeTabName);
			}
	
			 if (theme.clickOnAlreadyCreatedItem(projectName, themeNameAndDescriptions[0], 10)) {
		    	  switchOnWindow(driver);
		    	  ThreadSleep(3000);
		    	  negativeResult = theme.verifyTabsOnAPage(expectedListOfTabs);
			 
		if (negativeResult.isEmpty()) {
			log(LogStatus.INFO, "----Verified Tabs in Theme Tab----", YesNo.No);
	
		} else {
			log(LogStatus.ERROR, "----Not Verified Tabs in Theme Tab, Reason: " + negativeResult + "----", YesNo.No);
			sa.assertTrue(false, "----Not Verified Tabs in Theme Tab, Reason: " + negativeResult + "----");
		}
		
		String ThemeName = theme.getThemeNameOnDetailsPage(themeNameAndDescriptions[0],10).getText();
		if (ThemeName != null) {
			log(LogStatus.INFO, "----Verified Theme Name " + ThemeName + " on Theme Page----", YesNo.No);
	
		} else {
			log(LogStatus.ERROR, "----Not Verified Theme Name " + ThemeName + " on Theme Page", YesNo.No);
			sa.assertTrue(false, "----Not Verified Theme Name " + ThemeName + " on Theme Page----");
		}
		
		click(driver, theme.getCallListButton(10), "Call List", action.BOOLEAN);
		ThreadSleep(5000);
		
		String noResult = theme.GetCallListNoItemDisplay(10).getText();
		if (noResult != null) {
			log(LogStatus.INFO, "----Verified Call List with " + noResult + "----", YesNo.No);
	
		} else {
			log(LogStatus.ERROR, "----Not Verified Call List with " + noResult, YesNo.No);
			sa.assertTrue(false, "----Not Verified Call List with " + noResult + "----");
		}
		
		String callListTitle = theme.getMyCallList(10).getText();
		if (callListTitle != null) {
			log(LogStatus.INFO, "----Verified Call List Title with " + callListTitle + "----", YesNo.No);
	
		} else {
			log(LogStatus.ERROR, "----Not Verified Call List Title with " + callListTitle, YesNo.No);
			sa.assertTrue(false, "----Not Verified Call List Title with " + callListTitle + "----");
		}
		
		List<String> tableHeader = theme.verifyTableOnCallListTab(expectedListOfHeaders);
		if (tableHeader.isEmpty()) {
			log(LogStatus.INFO, "----Verified Headers in Call List Tab----", YesNo.No);
	
		} else {
			log(LogStatus.ERROR, "----Not Verified Headers in Call List Tab, Reason: " + tableHeader + "----", YesNo.No);
			sa.assertTrue(false, "----Not Verified Headers in Call List Tab, Reason: " + tableHeader + "----");
		}
	 } else {
          sa.assertTrue(false, "Not Able to open created Theme : " + themeNameAndDescriptions[0]);
           log(LogStatus.SKIP, "Not Able to open created Theme: " + themeNameAndDescriptions[0], YesNo.Yes);
      }
		
		driver.close();
		driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
	
		CommonLib.ThreadSleep(3000);
		sa.assertAll();
		lp.CRMlogout();
	}

@Parameters({ "projectName" })
@Test
	public void TCLTc004_CreateAddToThemeForVariousSections(String projectName) {
	
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
		String themeName = themeNameAndDescriptions[0];
	
		List<String> expectedThemeRecordsToCheckRedirection = new ArrayList<String>();
		expectedThemeRecordsToCheckRedirection.add(themeName);
	
		String advisorNames = CLTheme2;
		String companyNames = CLTheme3;
		String financialSponsorNames = CLTheme4;
		String strategicCorpNames = CLTheme5;
		Integer addToThemeLoopCount = 0;
		String[] dataToVerify = {CLAddToTheme5,CLAddToTheme3,CLAddToTheme4,CLAddToTheme2};
		String[][] addToThemeData = { { CLThemeGrid2, tabObj1, advisorNames, CLThemeGridColumns2 },
				{ CLThemeGrid5, tabObj1, companyNames, CLThemeGridColumns2 },
				{ CLThemeGrid3, tabObj1, financialSponsorNames, CLThemeGridColumns2 },
				{ CLThemeGrid4, tabObj1, strategicCorpNames, CLThemeGridColumns2 } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		
		for (String[] data : addToThemeData) {
			CommonLib.refresh(driver);
			if (lp.clickOnTab(projectName, TabName.AccountsTab.toString())) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.AccountsTab.toString(), YesNo.No);
				  if (fp.clickOnAlreadyCreatedItem(projectName, TabName.AccountsTab,data[2], 10)) {
					log(LogStatus.INFO, themeName + " value has been passed in Account Search Box", YesNo.No);
					if (theme.createAddToTheme(false, false, true, true, false, PageName.ThemesPage, projectName, TabName.Themes.toString(),
							themeName, data[0], data[1],themeName, null, null, false, false, false, null)) {
						log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeName + " for Object: " + data[1]
								+ " and for Record: " + themeName + " -----", YesNo.No);
					} else {
						sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
								+ " and for Record: " + themeName + " -----");
						log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
								+ " and for Record: " + themeName + " -----", YesNo.Yes);
					}
					addToThemeLoopCount++;
					} else {
				log(LogStatus.ERROR, themeName + " value is not passed in Theme Search Box", YesNo.No);
				sa.assertTrue(false, themeName + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.AccountsTab.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.AccountsTab.toString() + " Tab");
		}
		
		}
			if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj1, companyNames, null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + companyNames, YesNo.No);
				List<String> negativeResult = theme.verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(false,
						TaggedName.Themes, tabObj9, expectedThemeRecordsToCheckRedirection, true);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Theme records Redirection to Specific Page Records in case of "
							+ tabObj1 + " -----", YesNo.No);
	
				} else {
					sa.assertTrue(false,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----");
					log(LogStatus.FAIL,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----",
							YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + companyNames, YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + companyNames);
			}
	
			if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj1, advisorNames, null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + advisorNames, YesNo.No);
				List<String> negativeResult = theme.verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(false,
						TaggedName.Themes, tabObj9, expectedThemeRecordsToCheckRedirection, true);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Theme records Redirection to Specific Page Records in case of "
							+ tabObj1 + " -----", YesNo.No);
	
				} else {
					sa.assertTrue(false,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----");
					log(LogStatus.FAIL,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----",
							YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + advisorNames, YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + advisorNames);
			}
		
			if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj1, financialSponsorNames, null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + financialSponsorNames, YesNo.No);
				List<String> negativeResult = theme.verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(false,
						TaggedName.Themes, tabObj9, expectedThemeRecordsToCheckRedirection, true);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Theme records Redirection to Specific Page Records in case of "
							+ tabObj1 + " -----", YesNo.No);
	
				} else {
					sa.assertTrue(false,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----");
					log(LogStatus.FAIL,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----",
							YesNo.Yes);
				}
	
			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + financialSponsorNames, YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + financialSponsorNames);
			}
		
			if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj1, strategicCorpNames, null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + strategicCorpNames, YesNo.No);
				List<String> negativeResult = theme.verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(false,
						TaggedName.Themes, tabObj9, expectedThemeRecordsToCheckRedirection, true);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Theme records Redirection to Specific Page Records in case of "
							+ tabObj1 + " -----", YesNo.No);
	
				} else {
					sa.assertTrue(false,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----");
					log(LogStatus.FAIL,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----",
							YesNo.Yes);
				}
	
			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + strategicCorpNames, YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + strategicCorpNames);
			}

		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeName,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeName + " value has been passed in Theme Search Box",
						YesNo.No);
				 if (theme.clickOnAlreadyCreatedItem(projectName, themeName, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeName,YesNo.No);
					switchOnWindow(driver);
			    	  ThreadSleep(3000);
					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
		
							if (CommonLib.clickUsingJavaScript(driver, theme.importContactbutton(5),
									"Theme Name: " + themeName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on the Import Contacts Button", YesNo.No);
								if (theme.recordOnImportContactTable(dataToVerify, 5) != null) {
									log(LogStatus.INFO, "Verified Firm " + dataToVerify + " Has Been Created",
											YesNo.No);
								} else {
									log(LogStatus.ERROR, "Theme " + themeName + " is present there", YesNo.No);
									sa.assertTrue(false, "Theme " + themeName + " is present there");
								}
							} else {
								log(LogStatus.ERROR, "Not able to Click on Import Contacts Button",YesNo.No);
								sa.assertTrue(false, "Not able to Click on Import Contacts Button");
							}
					} else {
						log(LogStatus.ERROR, "Not able to click on Call List Button",YesNo.No);
						sa.assertTrue(false, "Not able to click on Call List Button");
					}	
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeName,YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeName);
				}	
			} else {
				log(LogStatus.ERROR, themeName + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeName + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);
	}

@Parameters({ "projectName" })
@Test
	public void TCLTc005_VerifyAccountOnImportContactPopup(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
		String themeNameToNavigate = themeNameAndDescriptions[0];
		String accountName = CLTheme5;
		
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	  ThreadSleep(3000);
				if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
						"Call List Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
	
						if (CommonLib.clickUsingJavaScript(driver, theme.importContactbutton(5),
								"Import Contact Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the Import Contacts Button", YesNo.No);
							if (CommonLib.clickUsingJavaScript(driver, theme.FirmInImportContacts(accountName,5),
									"Import Contact Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on " + accountName,YesNo.No);
								switchToWindowOpenNextToParentWindow(driver);
						    	  ThreadSleep(3000);
								if(theme.GetFirmName(5).getText().equalsIgnoreCase(accountName)) { 
								log(LogStatus.INFO, "Verified account " + accountName,
										YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not Verified account " + accountName, YesNo.No);
									sa.assertTrue(false, "Not Verified account " + accountName);
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on" + accountName, YesNo.No);
								sa.assertTrue(false, "Not able to click on " + accountName);
							}
						} else {
							log(LogStatus.ERROR, "Not able to Click on Import Contacts Button",YesNo.No);
							sa.assertTrue(false, "Not able to Click on Import Contacts Button");
						}
				} else {
					log(LogStatus.ERROR, "Not able to click on Call List Button",
							YesNo.No);
					sa.assertTrue(false, "Not able to click on Call List Button");
				}	
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		
		switchToDefaultContent(driver);
		ThreadSleep(2000);
		
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			String Value = npbl.getCallLinkOnCreateOption(5).getText();
			if(Value.equalsIgnoreCase(PageLabel.Call.toString())) {
				log(LogStatus.INFO, PageLabel.Call.toString() + "is visible at Top position", YesNo.No);
			} else {
				log(LogStatus.ERROR, PageLabel.Call.toString() + "is not visible at Top position", YesNo.Yes);
				sa.assertTrue(false,PageLabel.Call.toString() + "is not visible at Top position");
			}
			
			String Value1 = npbl.getTaskLinkOnCreateOption(5).getText();
			if(Value1.equalsIgnoreCase(PageLabel.Task.toString())) {
				log(LogStatus.INFO, PageLabel.Task.toString() + "is visible at Top position", YesNo.No);
			} else {
				log(LogStatus.ERROR, PageLabel.Task.toString() + "is not visible at Top position", YesNo.Yes);
				sa.assertTrue(false,PageLabel.Task.toString() + "is not visible at Top position");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
		}
		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);
	}

@Parameters({ "projectName" })
@Test
	public void TCLTc006_CreateCallForCallList(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
			
		String activityType1=CLActionType1;
		String taskSubject1=CLSubject1;
		String taskRelatedTo1=CLRelated1;
		String taskDueDate1 = todaysDate;
		String contactName = CLConFN1 + " " + CLConLN1;
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"TCL_Activity1", excelLabel.Advance_Due_Date);
	
		String activityType2=CLActionType2;
		String taskSubject2=CLSubject2;
		String taskRelatedTo2=CLRelated2;
		String taskDueDate2 = dayAfterTomorrowsDate = previousOrForwardDate(10, "M/d/YYYY");;
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"TCL_Activity2", excelLabel.Advance_Due_Date);
		String[] subjects = {CLSubject1,CLSubject2};
		String[] themeName = CLTheme1.split("<Break>");
		String[] tags = {themeName[0],contactName};
		
		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Date", taskDueDate1 } };
	
		String[][] basicsection2 = { { "Subject", taskSubject2 }, { "Related_To", taskRelatedTo2 } };
		String[][] advanceSection2 = { { "Date", taskDueDate2 }};
		
		if (bp.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}	 
	
		if (bp.createActivityTimeline(projectName, true, activityType2, basicsection2, advanceSection2, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject2, YesNo.No);
		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject2, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject2);
		}	
	
		ThreadSleep(5000);	
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
				ThreadSleep(3000);
				
			if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName, 10)){
				log(LogStatus.INFO, "Click on contact : " + contactName, YesNo.No);
				ThreadSleep(5000);
				for(String subject : subjects) {
				if (bp.InteractionRecord(subject,10) != null) {
					log(LogStatus.INFO,
							"Records on Intraction card have been verified with name "+subject ,
							YesNo.No);
					click(driver, bp.InteractionRecord(subject,10),subject,action.SCROLLANDBOOLEAN);
					for(String tag : tags) {
						if(bp.TagsOnCallPopup(tag, 10) != null) {
							log(LogStatus.INFO,
									"Tag on Popup have been verified with name "+tag ,YesNo.No);
						} else {
							log(LogStatus.ERROR, "Tag on Popup is not verified with name "+tag , YesNo.No);
							sa.assertTrue(false, "Tag on Popup is not verified with name "+tag);
						}
					}
					
				} else {
					log(LogStatus.ERROR, "Records on Intraction card is not created with name "+subject , YesNo.No);
					sa.assertTrue(false, "Records on Intraction card is not created with name "+subject );
				}
					}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + contactName, YesNo.No);
				sa.assertTrue(false, "Not able to click on " + contactName);
			}
		}else {
		log(LogStatus.ERROR, "could not click on " + TabName.Object2Tab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.Object2Tab );
	}	
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void TCLTc007_VerifyCallOnImportContactTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1),callList = {CLConFN1 +" " + CLConLN1,CLConFN5 +" " + CLConLN5,CLConFN6 +" " + CLConLN6,CLConFN8 +" " + CLConLN8,
				CLConFN12 +" " + CLConLN12,CLConFN15 +" " + CLConLN15,CLConFN19 +" " + CLConLN19};
		String callName = CLConFN1 + " " + CLConLN1,themeNameToNavigate = themeNameAndDescriptions[0], Value = CLFirm1;
		
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	  ThreadSleep(5000);
					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						if(theme.ContactOnMyCallList(callName,5) == null) { 
							log(LogStatus.INFO, "Verified Call " + callName + " is not visible",
								YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not Verified account " + callName + " is visible", YesNo.No);
							sa.assertTrue(false, "Not Verified account " + callName + " is visible");
							
						}
							
						if(CommonLib.clickUsingJavaScript(driver, theme.importContactbutton(5),
								"Import Contacts Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Import Contacts button",YesNo.No);
							if(CommonLib.clickUsingJavaScript(driver, theme.getCloseIconOnImportContactPopup(5),
									"Close Icon on Import Contacts Popup", action.SCROLLANDBOOLEAN)) { 
								log(LogStatus.INFO, "Clicked on Close Icon on Import Contacts Popup",
									YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not able to Close Icon on Import Contacts Popup", YesNo.No);
								sa.assertTrue(false, "Not able to Close Icon on Import Contacts Popup");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Import Contacts Button",
									YesNo.No);
							sa.assertTrue(false, "Not able to click on Import Contacts Button");
						}
						
						if(CommonLib.clickUsingJavaScript(driver, theme.importContactbutton(5),
								"Import Contacts Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Import Contacts button",YesNo.No);
							if(CommonLib.clickUsingJavaScript(driver, theme.getCheckboxForAllFirmsInImportContactPopup(5),
									"All Firm Checkbox", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on All Firm Checkbox",YesNo.No);
								if(CommonLib.clickUsingJavaScript(driver, theme.getCancelButtonOnImportContactPopup(5),
										"Cancel Button on Import Contacts Popup", action.SCROLLANDBOOLEAN)) { 
									log(LogStatus.INFO, "Clicked on Cancel Button on Import Contacts Popup",
										YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to Close Icon on Import Contacts Popup", YesNo.No);
									sa.assertTrue(false, "Not able to Close Icon on Import Contacts Popup");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on All Firm Checkbox",YesNo.No);
								sa.assertTrue(false, "Not able to click on All Firm Checkbox");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Import Contacts Button",YesNo.No);
							sa.assertTrue(false, "Not able to click on Import Contacts Button");
						}
						
						if(CommonLib.clickUsingJavaScript(driver, theme.importContactbutton(5),
								"Import Contacts Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Import Contacts button",YesNo.No);
							if(CommonLib.clickUsingJavaScript(driver, theme.getCheckboxForFirmInImportContactPopup(Value,5),
									"Import Contacts Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Import Contacts button",YesNo.No);
								if(CommonLib.clickUsingJavaScript(driver, theme.importbutton(5),
										"Import Button on Import Contacts Popup", action.SCROLLANDBOOLEAN)) { 
									log(LogStatus.INFO, "Clicked on Import Button on Import Contacts Popup",
										YesNo.No);
									ThreadSleep(1000);
									if(theme.getImportToastMsg(5) != null) {
										log(LogStatus.INFO, "The Contacts has been successfully imported",YesNo.No);
										ThreadSleep(3000);
										for(String value : callList) {
											if(theme.ContactOnMyCallList(value,5) != null) { 
												log(LogStatus.INFO, "Verified Call " + value + " is not visible",
													YesNo.No);
											} else {
												log(LogStatus.ERROR, "Not Verified account " + value + " is visible", YesNo.No);
												sa.assertTrue(false, "Not Verified account " + value + " is visible");
											}
										}
									} else {
										log(LogStatus.ERROR, "Not able to import contacts", YesNo.Yes);
										sa.assertTrue(false, "Not able to import contacts");
									}
								} else {
									log(LogStatus.ERROR, "Not able to Close Icon on Import Contacts Popup", YesNo.No);
									sa.assertTrue(false, "Not able to Close Icon on Import Contacts Popup");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Import Contacts Button",YesNo.No);
								sa.assertTrue(false, "Not able to click on Import Contacts Button");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Import Contacts Button",YesNo.No);
							sa.assertTrue(false, "Not able to click on Import Contacts Button");
						}
						
					} else {
						log(LogStatus.ERROR, "Not able to click on Call List Button",YesNo.No);
						sa.assertTrue(false, "Not able to click on Call List Button");
					}	
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);
	}

@Parameters({ "projectName" })
@Test
	public void TCLTc008_CreateContactFromAccount_VerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		ContactsPageBusinessLayer contact = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1), callList = {CLConFN1 +" " + CLConLN1,CLConFN5 +" " + CLConLN5,CLConFN6 +" " + CLConLN6,CLConFN8 +" " + CLConLN8,
				CLConFN12 +" " + CLConLN12,CLConFN15 +" " + CLConLN15,CLConFN19 +" " + CLConLN19,CLConFN21 +" " + CLConLN21},
				AllContact ={CLConFN1 +" " + CLConLN1,CLConFN2 +" " + CLConLN2,CLConFN3 +" " + CLConLN3,CLConFN4 +" " + CLConLN4,CLConFN5 +" " + CLConLN5,CLConFN6 +" " + CLConLN6,CLConFN7 +" " + CLConLN7,CLConFN8 +" " + CLConLN8,
						CLConFN9 +" " + CLConLN9,CLConFN10 +" " + CLConLN10,CLConFN11 +" " + CLConLN11,CLConFN12 +" " + CLConLN12,CLConFN13 +" " + CLConLN13,CLConFN14 +" " + CLConLN14,CLConFN15 +" " + CLConLN15,CLConFN16 +" " + CLConLN16,
						CLConFN17 +" " + CLConLN17,CLConFN18 +" " + CLConLN18,CLConFN19 +" " + CLConLN19,CLConFN20 +" " + CLConLN20,CLConFN21 +" " + CLConLN21} ;
		String themeNameToNavigate = themeNameAndDescriptions[0], emailId = CLConEmail21, Value = CLFirm1;
		
		if (lp.clickOnTab(projectName,mode, TabName.AccountsTab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.AccountsTab,YesNo.No);	
			  if (fp.clickOnAlreadyCreatedItem(projectName, TabName.AccountsTab,CLFirm1, 10)) {
				  appLog.info("Clicked on created Account: " + CLFirm1);
				if (contact.createContactFromFirm(environment, mode, CLConFN21, CLConLN21, null, emailId,
						null, null, CreationPage.AccountPage,null,null)) {
					appLog.info("Contact is create Successfully: " + CLConFN21 + " " + CLConLN21);
					ExcelUtils.writeData(smokeFilePath, emailId, "Contacts", excelLabel.Variable_Name,"CLContact21", excelLabel.Contact_EmailId);
				} else {
					sa.assertTrue(false, "Not able to create Contact: " + SmokeC6_FName + " " + SmokeC6_LName);
					log(LogStatus.ERROR, "Not able to create Contact: " + SmokeC6_FName + " " + SmokeC6_LName,YesNo.Yes);
				}	
			  }else {
				   sa.assertTrue(false, "Not Able to open created Account : " + CLFirm1);
		           log(LogStatus.ERROR, "Not Able to open created Account: " + CLFirm1, YesNo.Yes);
			  }
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.AccountsTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.AccountsTab,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	  ThreadSleep(5000);
					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						if(CommonLib.clickUsingJavaScript(driver, theme.importContactbutton(5),
								"Import Contacts Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Import Contacts button",YesNo.No);
							if(CommonLib.clickUsingJavaScript(driver, theme.getCheckboxForFirmInImportContactPopup(Value,5),
									"Import Contacts Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Import Contacts button",YesNo.No);
								if(CommonLib.clickUsingJavaScript(driver, theme.importbutton(5),
										"Import Button on Import Contacts Popup", action.SCROLLANDBOOLEAN)) { 
									log(LogStatus.INFO, "Clicked on Import Button on Import Contacts Popup",
										YesNo.No);
									ThreadSleep(1000);
									if(theme.getImportToastMsg(5) != null) {
										log(LogStatus.INFO, "The Contacts has been successfully imported",YesNo.No);
										ThreadSleep(3000);
										for(String callName : callList) {
											if(theme.ContactOnMyCallList(callName,5) != null) { 
												log(LogStatus.INFO, "Verified Call " + callName + " is visible",
													YesNo.No);
											} else {
												log(LogStatus.ERROR, "Not Verified account " + callName + " is visible", YesNo.No);
												sa.assertTrue(false, "Not Verified account " + callName + " is visible");
											}
										}
									} else {
										log(LogStatus.ERROR, "Not able to import contacts", YesNo.Yes);
										sa.assertTrue(false, "Not able to import contacts");
									}
								} else {
									log(LogStatus.ERROR, "Not able to Close Icon on Import Contacts Popup", YesNo.No);
									sa.assertTrue(false, "Not able to Close Icon on Import Contacts Popup");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Import Contacts Button",YesNo.No);
								sa.assertTrue(false, "Not able to click on Import Contacts Button");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Import Contacts Button",YesNo.No);
							sa.assertTrue(false, "Not able to click on Import Contacts Button");
						}
						
					} else {
						log(LogStatus.ERROR, "Not able to click on Call List Button",YesNo.No);
						sa.assertTrue(false, "Not able to click on Call List Button");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
//		driver.close();
		switchToDefaultContent(driver);
		refresh(driver);
		ThreadSleep(5000);

					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						if(CommonLib.clickUsingJavaScript(driver, theme.importContactbutton(5),
								"Import Contacts Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Import Contacts button",YesNo.No);
							if(CommonLib.clickUsingJavaScript(driver, theme.getCheckboxForAllFirmsInImportContactPopup(5),
									"Import Contacts Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Import Contacts button",YesNo.No);
								if(CommonLib.clickUsingJavaScript(driver, theme.importbutton(5),
										"Import Button on Import Contacts Popup", action.SCROLLANDBOOLEAN)) { 
									log(LogStatus.INFO, "Clicked on Import Button on Import Contacts Popup",
										YesNo.No);
									ThreadSleep(1000);
									if(theme.getImportToastMsg(5) != null) {
										log(LogStatus.INFO, "The Contacts has been successfully imported",YesNo.No);
										ThreadSleep(3000);
										for(String contacts : AllContact) {
											if(theme.ContactOnMyCallList(contacts,5) != null) { 
												log(LogStatus.INFO, "Verified Call " + contacts + " is visible",
													YesNo.No);
											} else {
												log(LogStatus.ERROR, "Not Verified account " + contacts + " is visible", YesNo.No);
												sa.assertTrue(false, "Not Verified account " + contacts + " is visible");
											}
										}
									} else {
										log(LogStatus.ERROR, "Not able to import contacts", YesNo.Yes);
										sa.assertTrue(false, "Not able to import contacts");
									}
								} else {
									log(LogStatus.ERROR, "Not able to Close Icon on Import Contacts Popup", YesNo.No);
									sa.assertTrue(false, "Not able to Close Icon on Import Contacts Popup");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Import Contacts Button",YesNo.No);
								sa.assertTrue(false, "Not able to click on Import Contacts Button");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Import Contacts Button",YesNo.No);
							sa.assertTrue(false, "Not able to click on Import Contacts Button");
						}
						
					} else {
						log(LogStatus.ERROR, "Not able to click on Call List Button",YesNo.No);
						sa.assertTrue(false, "Not able to click on Call List Button");
					}	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
@Parameters({ "projectName" })
@Test
	public void TCLTc009_DeleteTaskAndRevert_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword);
	WebElement ele = null;
	String primaryContact = CLConFN1 + " " + CLConLN1;
	String task = CLSubject2;

	if (home.globalSearchAndDeleteTaskorCall(task, "tasks", false)) {

		log(LogStatus.INFO, "-----Verified Task named: " + task + " found and delete in Tasks Object-----",
				YesNo.No);

	} else {

		log(LogStatus.ERROR, "-----Task named: " + task + " not deleted in Tasks Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Task named: " + task + " not deleted in Tasks Object-----");

	}
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabObj2 + " For : " + primaryContact, YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, primaryContact, 30)) {
			log(LogStatus.INFO, "Clicked on  : " + primaryContact + " For : " + tabObj2, YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = bp.getRelatedTab(RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			ele = cp.getlastTouchPointValue(projectName, 10);
			String value = "";
			if (ele != null) {
				value = ele.getText().trim();
				if (value.isEmpty() || value.equals("")) {
					log(LogStatus.INFO, "Last Touch Point is Blank for " + primaryContact, YesNo.No);
				} else {
					log(LogStatus.ERROR, "Last Touch Point should be Blank for " + primaryContact, YesNo.Yes);
					sa.assertTrue(false, "Last Touch Point should be Blank for " + primaryContact);
				}
			} else {
				log(LogStatus.ERROR, "last touch point value is not visible For : " + primaryContact, YesNo.Yes);
				sa.assertTrue(false, "last touch point value is not visible For : " + primaryContact);
			}
		} else {
			sa.assertTrue(false, "Item Not Found : " + primaryContact + " For : " + tabObj2);
			log(LogStatus.SKIP, "Item Not Found : " + primaryContact + " For : " + tabObj2, YesNo.Yes);
		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj2 + " For : " + primaryContact);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj2 + " For : " + primaryContact, YesNo.Yes);
	}
	
	refresh(driver);
	if (lp.restoreValueFromRecycleBin(projectName, task)) {
		log(LogStatus.INFO, "Able to restore item from Recycle Bin " + task, YesNo.Yes);
	} else {
		sa.assertTrue(false, "Not Able to restore item from Recycle Bin " + task);
		log(LogStatus.SKIP, "Not Able to restore item from Recycle Bin " + task, YesNo.Yes);
	}
	
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabObj2 + " For : " + primaryContact, YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, primaryContact, 30)) {
			log(LogStatus.INFO, "Clicked on  : " + primaryContact + " For : " + tabObj2, YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = bp.getRelatedTab(RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			ele = cp.getlastTouchPointValue(projectName, 10);
			String value = "";
			if (ele != null) {
				value = ele.getText().trim();
				if (value.isEmpty() || value.equals("")) {
					log(LogStatus.INFO, "Last Touch Point is Blank for " + primaryContact, YesNo.No);
				} else {
					log(LogStatus.ERROR, "Last Touch Point should be Blank for " + primaryContact, YesNo.Yes);
					sa.assertTrue(false, "Last Touch Point should be Blank for " + primaryContact);
				}
			} else {
				log(LogStatus.ERROR, "last touch point value is not visible For : " + primaryContact, YesNo.Yes);
				sa.assertTrue(false, "last touch point value is not visible For : " + primaryContact);
			}
		} else {
			sa.assertTrue(false, "Item Not Found : " + primaryContact + " For : " + tabObj2);
			log(LogStatus.SKIP, "Item Not Found : " + primaryContact + " For : " + tabObj2, YesNo.Yes);
		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj2 + " For : " + primaryContact);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj2 + " For : " + primaryContact, YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout(environment, mode);
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
	public void TCLTc010_VerifyCallOnImportContactTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
		String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String themeNameToNavigate = themeNameAndDescriptions[0];
		String[] values = {CLConFN1 +" "+CLConLN1,CLFirm1};
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);
			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				for(String value : values ) {
					refresh(driver);
		    	  ThreadSleep(5000);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					ThreadSleep(5000);
//						if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
//								"Call List Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
							if(CommonLib.clickUsingJavaScript(driver, theme.getCallList(value,5),
									"Import Contacts Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Import Contacts button",YesNo.No);
								try {
									String parentWindowId = CommonLib.switchOnWindow(driver);
									if (!parentWindowId.isEmpty()) {
										log(LogStatus.PASS, "New Window Open after click on Grid Link: " + value,
												YesNo.No);
										refresh(driver);
										if (rp.RecordPagesHeader(value, 10) != null) {//need to update according to Research
											log(LogStatus.PASS, "----Detail Page is redirecting for Record: "
													+ value + "-----", YesNo.No);
											Set<String> childWindow = driver.getWindowHandles();
											switchToDefaultContent(driver);
											System.out.println(childWindow);
											for (String child : childWindow) {
												driver.switchTo().window(child);
											}
//											driver.close();
//											switchOnWindow(driver);
											CommonLib.switchToDefaultContent(driver);
											
										} else {
											log(LogStatus.FAIL, "----Detail Page is not redirecting for Record: "
													+ value + "-----", YesNo.Yes);
											BaseLib.sa.assertTrue(false, "----Detail Page is not showing for Record: "
													+ value + "-----");
										}
									} else {
										log(LogStatus.FAIL, "No New Window Open after click on Grid Link: " + value,
												YesNo.Yes);
										BaseLib.sa.assertTrue(false, "No New Window Open after click on Grid Link: " + value);
									}
								} catch (Exception e) {
									log(LogStatus.FAIL,
											"Not able to switch to window after click on Grid Link, Msg showing: "
													+ e.getMessage(),
											YesNo.Yes);
									BaseLib.sa.assertTrue(false,
											"Not able to switch to window after click on Grid Link, Msg showing: "
													+ e.getMessage());
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Import Contacts Button",YesNo.No);
								sa.assertTrue(false, "Not able to click on Import Contacts Button");
							}
//						} else {
//							log(LogStatus.ERROR, "Not able to click on Call List Button",YesNo.No);
//							sa.assertTrue(false, "Not able to click on Call List Button");
//						}
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			  }
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		CommonLib.switchToDefaultContent(driver);
//		driver.close();
		switchOnWindow(driver);
		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);
	}

@Parameters({ "projectName" })
@Test
	public void TCLTc011_RenameContactDetailsAndVerify(String projectName) {
    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
    ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
    ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
    String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	 String PhoneNumber = CLConPhone1, themeNameToNavigate = themeNameAndDescriptions[0], updatedName = CLConLN1Updated;
		 lp.CRMLogin(glUser1EmailID, adminPassword);
		 String contact = CLConFN1 + " " + CLConLN1;
		 String parentID = switchOnWindow(driver);
	   if (fp.clickOnTab(environment, mode, TabName.ContactTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
	
	      if (fp.clickOnAlreadyCreatedItem(projectName,TabName.ContactTab, contact, 10)) {
	           if (cp.UpdateFieldsForContact(projectName, PageName.ContactPage,updatedName,PhoneNumber)) {
	               log(LogStatus.INFO, "successfully update contact name " + updatedName, YesNo.Yes);
	           } else {
	               sa.assertTrue(false, "not able to update contact name " + updatedName);
	               log(LogStatus.SKIP, "not able to update contact name " + updatedName, YesNo.Yes);
	           }
	       } else {
	          sa.assertTrue(false, "Not Able to open created contact : " + contact);
	           log(LogStatus.SKIP, "Not Able to open created contact: " + contact, YesNo.Yes);
	      }
	   } else {
	       log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
	       sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
	   }
	   
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	ThreadSleep(8000);
					CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN);
					ThreadSleep(8000);
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);	
						if(isDisplayed(driver, theme.getCallList(CLConFN1 + " " + updatedName,5), "Visibility", 10,
								"Import Contacts Button") != null) {
							log(LogStatus.INFO, "Updated Name " + CLConFN1 + " " + updatedName + " is visible" ,YesNo.No);
						} else {
							log(LogStatus.ERROR, "Updated Name " + CLConFN1 + " " + updatedName + " is not visible",YesNo.No);
							sa.assertTrue(false, "Updated Name " + CLConFN1 + " " + updatedName + " is not visible");
						}
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
@Parameters({ "projectName" })
@Test
	public void TCLTc012_RenameFirmDetailsAndVerify(String projectName) {
    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
    InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
    ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
    String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	String themeNameToNavigate = themeNameAndDescriptions[0], updatedName = CLFirm1Updated;
	lp.CRMLogin(glUser1EmailID, adminPassword);
    String parentID = switchOnWindow(driver);
		 if (fp.clickOnTab(environment, mode, TabName.AccountsTab)) {
		       log(LogStatus.INFO, "Click on Tab : " + TabName.AccountsTab, YesNo.No);
		
		      if (fp.clickOnAlreadyCreatedItem(projectName, TabName.AccountsTab,CLFirm1, 10)) {
		           if (ip.UpdateLegalNameAccount(projectName, updatedName, 5)) {
		               log(LogStatus.INFO, "successfully update legal name " + updatedName, YesNo.Yes);
		           } else {
		               sa.assertTrue(false, "not able to update legal name " + updatedName);
		               log(LogStatus.SKIP, "not able to update legal name " + updatedName, YesNo.Yes);
		           }
		       } else {
		          sa.assertTrue(false, "Not Able to open created Account : " + CLFirm1);
		           log(LogStatus.SKIP, "Not Able to open created Account: " + CLFirm1, YesNo.Yes);
		      }
		   } else {
		       log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		       sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		   }
   
	if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

		if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
				"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
					YesNo.No);
			if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
				log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
				switchOnWindow(driver);
		    	ThreadSleep(8000);
				CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
						"Call List Button", action.SCROLLANDBOOLEAN);
				ThreadSleep(5000);
					log(LogStatus.INFO, "Clicked on call list button",YesNo.No);	
					if(isDisplayed(driver, theme.getCallList(updatedName,5), "Visibility", 10,
							"Import Contacts Button") != null) {
						log(LogStatus.INFO, "Updated Name " + updatedName + " is visible" ,YesNo.No);
					} else {
						log(LogStatus.ERROR, "Updated Name " + updatedName + " is not visible",YesNo.No);
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Call List Button",YesNo.No);
				}	
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void TCLTc013_UpdateCustomMetaDataTypesForContact_VerifyImpact(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	String themeNameToNavigate = themeNameAndDescriptions[0], parentWindow = null;
	String[] FieldName = {CLCMDField1,CLCMDField2,CLCMDField3,CLCMDField4,CLCMDField5}, Value = {CLCMDNewValue1,CLCMDNewValue2,
			CLCMDNewValue3,CLCMDNewValue4,CLCMDNewValue5}, firmNames = CLFirmsName1.split("<break>");
//	
	lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create clone user");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create clone user",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
			}
			ThreadSleep(3000);
			for(int i = 0 ; i <FieldName.length; i++) {
				if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), FieldName[i], Value[i], 10))
				{
					log(LogStatus.INFO, "Changed the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "Not able to change the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
					sa.assertTrue(false, "Not able to changed the value of " + FieldName[i] + " for Acuity Setting");	
				}
				ThreadSleep(5000);
			}	
			ThreadSleep(5000);
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		} else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot change value");
		}
		
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	ThreadSleep(8000);
			    	CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN);
					ThreadSleep(5000);
					log(LogStatus.INFO, "Clicked on call list button",YesNo.No);	
					for(String firmName : firmNames) {
						if(isDisplayed(driver, theme.getCallListHeader(firmName,5), "Visibility", 10,
								"Import Contacts Button") != null) {
							log(LogStatus.INFO, "Updated " + firmName + " are visible after updating custom metadata" ,YesNo.No);
						} else {
							log(LogStatus.ERROR, "Updated " + firmName + " are not visible after updating custom metadata",YesNo.No);
							sa.assertTrue(false, "Updated " + firmName + " are not visible after updating custom metadata");
						}
					}
				} else {
					log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
							YesNo.No);
					sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void TCLTc014_RevertCustomMetaDataTypesForAllObjects_VerifyImpact(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	String themeNameToNavigate = themeNameAndDescriptions[0], parentWindow = null;
	String[] FieldName = {CLCMDField1,CLCMDField2,CLCMDField3,CLCMDField4,CLCMDField5}, Value = {CLCMDValue1,CLCMDValue2,CLCMDValue3,
			CLCMDValue4,CLCMDValue5}, firmNames = CLFirmsName2.split("<break>");
	lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode");
			}
			ThreadSleep(3000);
			for(int i = 0 ; i <FieldName.length; i++) {
				if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), FieldName[i], Value[i], 10))
				{
					log(LogStatus.INFO, "Changed the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "Not able to change the value of " + FieldName[i] + " for Acuity Setting", YesNo.No);
					sa.assertTrue(false, "Not able to changed the value of " + FieldName[i] + " for Acuity Setting");	
				}
				ThreadSleep(5000);
			}	
			ThreadSleep(5000);
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		} else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot change value");
		}
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	ThreadSleep(8000);
			    	CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN);
					ThreadSleep(5000);
					log(LogStatus.INFO, "Clicked on call list button",YesNo.No);	
					for(String firmName : firmNames) {
						if(isDisplayed(driver, theme.getCallListHeader(firmName,5), "Visibility", 10,
								"Import Contacts Button") != null) {
							log(LogStatus.INFO, "Updated " + firmName + " are visible after updating custom metadata" ,YesNo.No);
						} else {
							log(LogStatus.ERROR, "Updated " + firmName + " are not visible after updating custom metadata",YesNo.No);
							sa.assertTrue(false, "Updated " + firmName + " are not visible after updating custom metadata");
						}
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Call List Button",YesNo.No);
					sa.assertTrue(false, "Not able to click on Call List Button");
				}	
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName"})
@Test
	public void TCLTc015_RemoveObjectPermissionForContact_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	String themeNameToNavigate = themeNameAndDescriptions[0];
	
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String profileForSelection = UserProfile.PE_Standard_User.toString().replace("_", " ");
	String parentID=null, objects[] ={object.Contact.toString()}, permissionTypes[] = {PermissionType.Read.toString(),PermissionType.Create.toString(),
			PermissionType.Edit.toString(),PermissionType.Delete.toString()}, status = CheckBox.Not_Checked.toString().replace("_", " ");
	boolean flag = false;
	
	switchToDefaultContent(driver);
	home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
		if (parentID!=null) {
			ThreadSleep(2000);
				if(sp.giveAndRemoveObjectPermissionFromProfiles(profileForSelection,objects,permissionTypes,status)) {
					log(LogStatus.PASS,"Remove Permission from Contact Object", YesNo.No);
					flag=true;
				}else {
					log(LogStatus.ERROR,"Not able to remove permission from" + objects + "Object", YesNo.Yes);
					sa.assertTrue(false, "Not able to remove permission from" + objects + "Object");
				}
			driver.close();
			driver.switchTo().window(parentID);
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
	}
		
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		lp.CRMlogout();
		refresh(driver);
		ThreadSleep(2000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		ThreadSleep(2000);
		
		
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	ThreadSleep(8000);
			    	CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN);
					ThreadSleep(5000);
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);	
						if(isDisplayed(driver, theme.getCallList(CLConFN1 + " " + CLConLN1Updated,5), "Visibility", 10,
								"Import Contacts Button") != null) {
							log(LogStatus.INFO, "After removing permission, hyperlink is not visible on contact" ,YesNo.No);
							CommonLib.clickUsingJavaScript(driver,theme.getCallList(CLConFN1 + " " + CLConLN1Updated,5),
									"Call List Button", action.SCROLLANDBOOLEAN);
							CommonLib.switchToWindowOpenNextToParentWindow(driver);
							ThreadSleep(5000);
							String Name = getText(driver, cp.getContactFullNameInViewMode(environment, mode, 60), "Contact Name", action.SCROLLANDBOOLEAN);
							if(Name.contains(CLConFN1 + " " + CLConLN1Updated)){
								log(LogStatus.ERROR, "After removing permission, still hyperlink is working on contact",YesNo.No);
								sa.assertTrue(false, "After removing permission, still hyperlink is working on contact");
							} else {
								log(LogStatus.INFO, "After removing permission, hyperlink is not working on contact",YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "After removing permission, still hyperlink is visible on contact",YesNo.No);
							sa.assertTrue(false, "After removing permission, still hyperlink is visible on contact");
						}
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		lp.CRMlogout();
		sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void TCLTc016_AddObjectPermissionForContact_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	String themeNameToNavigate = themeNameAndDescriptions[0];

	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String profileForSelection = UserProfile.PE_Standard_User.toString().replace("_", " ");
	String parentID=null, objects[] ={object.Contact.toString()}, permissionTypes[] = {PermissionType.Read.toString(),PermissionType.Create.toString()
			,PermissionType.Edit.toString(),PermissionType.Delete.toString()}, status = CheckBox.Checked.toString();
	boolean flag = false;
	
	switchToDefaultContent(driver);
	home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
			if (parentID!=null) {
				ThreadSleep(2000);
					if(sp.giveAndRemoveObjectPermissionFromProfiles(profileForSelection,objects,permissionTypes,status)) {
						log(LogStatus.PASS,"Remove Permission from Contact Object", YesNo.No);
						flag=true;
					}else {
						log(LogStatus.ERROR,"Not able to remove permission from" + objects + "Object", YesNo.Yes);
						sa.assertTrue(false, "Not able to remove permission from" + objects + "Object");
					}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
		}
		
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		lp.CRMlogout();
		refresh(driver);
		ThreadSleep(2000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		ThreadSleep(2000);
		
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	ThreadSleep(8000);
			    	CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN);
					ThreadSleep(5000);
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);	
						if(isDisplayed(driver, theme.getCallList(CLConFN1 + " " + CLConLN1Updated,5), "Visibility", 10,
								"Import Contacts Button") != null) {
							log(LogStatus.INFO, "After adding permission, hyperlink is visible on contact" ,YesNo.No);
							CommonLib.clickUsingJavaScript(driver,theme.getCallList(CLConFN1 + " " + CLConLN1Updated,5),
									"Call List Button", action.SCROLLANDBOOLEAN);
							CommonLib.switchToWindowOpenNextToParentWindow(driver);
							ThreadSleep(5000);
							String Name = getText(driver, cp.getContactFullNameInViewMode(environment, mode, 60), "Contact Name", action.SCROLLANDBOOLEAN);
							if(Name.contains(CLConFN1 + " " + CLConLN1Updated)){
								log(LogStatus.INFO, "After removing permission, hyperlink is working on contact",YesNo.No);
							} else {
								log(LogStatus.ERROR, "After removing permission, hyperlink is not working on contact",YesNo.No);
								sa.assertTrue(false, "After removing permission, still hyperlink is working on contact");
							}
						} else {
							log(LogStatus.ERROR, "After adding permission, still hyperlink is not visible on contact",YesNo.No);
							sa.assertTrue(false, "After adding permission, still hyperlink is not visible on contact");
						}
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		lp.CRMlogout();
		sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void TCLTc017_RemoveObjectPermissionForAccount_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	String themeNameToNavigate = themeNameAndDescriptions[0], updatedName = CLFirm1Updated;
	
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String profileForSelection = UserProfile.PE_Standard_User.toString().replace("_", " ");
	String parentID=null, objects[] ={object.Account.toString()}, permissionTypes[] = {PermissionType.Read.toString(),PermissionType.Create.toString(),
			PermissionType.Edit.toString(),PermissionType.Delete.toString()}, status = CheckBox.Not_Checked.toString().replace("_", " ");
	boolean flag = false;
	
	switchToDefaultContent(driver);
	home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
		if (parentID!=null) {
			ThreadSleep(2000);
			if(sp.giveAndRemoveObjectPermissionFromProfiles(profileForSelection,objects,permissionTypes,status)) {
				log(LogStatus.PASS,"Remove Permission from Contact Object", YesNo.No);
				flag=true;
			}else {
				log(LogStatus.ERROR,"Not able to remove permission from" + objects + "Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to remove permission from" + objects + "Object");
			}
	
			driver.close();
			driver.switchTo().window(parentID);
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
	}
		
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		lp.CRMlogout();
		refresh(driver);
		ThreadSleep(2000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		ThreadSleep(2000);
		
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	ThreadSleep(8000);
			    	CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN);
					ThreadSleep(5000);
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						if(isDisplayed(driver, theme.getCallList(updatedName,5), "Visibility", 10,
								"Import Contacts Button") != null) {
							log(LogStatus.INFO, "After removing permission, hyperlink is visible on account" ,YesNo.No);
							CommonLib.clickUsingJavaScript(driver,theme.getCallList(updatedName,5),
									"Call List Button", action.SCROLLANDBOOLEAN);
							CommonLib.switchToWindowOpenNextToParentWindow(driver);
							ThreadSleep(5000);
							String Name = getText(driver, ip.getLegalName(projectName,5), "Account Name", action.SCROLLANDBOOLEAN);
							if(Name.contains(updatedName)){
								log(LogStatus.ERROR, "After removing permission, hyperlink is working on account",YesNo.No);
								sa.assertTrue(false, "After removing permission, still hyperlink is working on account");
							} else {
								log(LogStatus.INFO, "After removing permission, hyperlink is not working on account",YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "After removing permission, still hyperlink is not visible on account",YesNo.No);
							sa.assertTrue(false, "After removing permission, still hyperlink is not visible on account");
						}
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		driver.close();
		driver.switchTo().window(parentID);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void TCLTc018_AddObjectPermissionForAccount_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	String themeNameToNavigate = themeNameAndDescriptions[0], updatedName = CLFirm1Updated;

	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String profileForSelection = UserProfile.PE_Standard_User.toString().replace("_", " ");
	String parentID=null, objects[] ={object.Account.toString()}, permissionTypes[] = {PermissionType.Read.toString(),PermissionType.Create.toString()
			,PermissionType.Edit.toString(),PermissionType.Delete.toString()}, status = CheckBox.Checked.toString();
	boolean flag = false;
	
	switchToDefaultContent(driver);
	home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
			if (parentID!=null) {
				ThreadSleep(2000);
				if(sp.giveAndRemoveObjectPermissionFromProfiles(profileForSelection,objects,permissionTypes,status)) {
					log(LogStatus.PASS,"Remove Permission from Contact Object", YesNo.No);
					flag=true;
				}else {
					log(LogStatus.ERROR,"Not able to remove permission from" + objects + "Object", YesNo.Yes);
					sa.assertTrue(false, "Not able to remove permission from" + objects + "Object");
				}
		
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
		}
		
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		lp.CRMlogout();
		refresh(driver);
		ThreadSleep(2000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		ThreadSleep(2000);
		
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	ThreadSleep(8000);
			    	CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN);
					ThreadSleep(5000);
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);	
						if(isDisplayed(driver, theme.getCallList(updatedName,5), "Visibility", 10,
								"Import Contacts Button") != null) {
							log(LogStatus.INFO, "After adding permission, hyperlink is visible on account" ,YesNo.No);
							CommonLib.clickUsingJavaScript(driver,theme.getCallList(updatedName,5),
									"Call List Button", action.SCROLLANDBOOLEAN);
							CommonLib.switchToWindowOpenNextToParentWindow(driver);
							ThreadSleep(5000);
							String Name = getText(driver, ip.getLegalName(projectName,5), "Account Name", action.SCROLLANDBOOLEAN);
							if(Name.contains(updatedName)){
								log(LogStatus.INFO, "After removing permission, hyperlink is working on account",YesNo.No);
							} else {
								log(LogStatus.ERROR, "After removing permission, hyperlink is not working on account",YesNo.No);
								sa.assertTrue(false, "After removing permission, still hyperlink is working on account");
							}
						} else {
							log(LogStatus.ERROR, "After adding permission, still hyperlink is not visible on account",YesNo.No);
							sa.assertTrue(false, "After adding permission, still hyperlink is not visible on account");
						}
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		driver.close();
		driver.switchTo().window(parentID);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void TCLTc019_CreateContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);//change
		String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
		String[] contacts = {CLConFN22, CLConLN22,CLConFirm22,null,null,null,null};
		String themeNameToNavigate = themeNameAndDescriptions[0];
		ThreadSleep(5000);
			if (lp.clickOnTab(projectName,mode, TabName.ContactTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
	
				if (cp.createContact(environment, mode, contacts[0], contacts[1], contacts[2], null,
						Fields.Phone.toString(), null,CreationPage.ContactPage,null,null)) {//change
					log(LogStatus.INFO, "successfully Created Contact : " + contacts[0] + " " + contacts[1],
							YesNo.No);
				} else {
					sa.assertTrue(false, "Not Able to Create Contact : " + contacts[0] + " " + contacts[1]);
					log(LogStatus.SKIP, "Not Able to Create Contact: " + contacts[0] + " " + contacts[1],
							YesNo.Yes);
				}
	
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.ContactTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.ContactTab, YesNo.Yes);
			}
		
		ThreadSleep(2000);
		
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	ThreadSleep(8000);
					CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN);
					ThreadSleep(8000);
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);	
						if(isDisplayed(driver, theme.getCallList(CLConFN22 + " " + CLConLN22,5), "Visibility", 10,
								"Import Contacts Button") == null) {
							log(LogStatus.INFO, "New Created Contact Name " + CLConFN22 + " " + CLConLN22 + " is not visible" ,YesNo.No);
						} else {
							log(LogStatus.ERROR, "New Created Contact Name " + CLConFN22 + " " + CLConLN22 + " is visible",YesNo.No);
							sa.assertTrue(false, "New Created Contact Name " + CLConFN22 + " " + CLConLN22 + " is visible");
						}
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);//Change
		sa.assertAll();
	}
	
@Parameters({ "projectName" })
@Test
	public void TCLTc020_updateFirmNameAndVerifyImpact(String projectName) {
	    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	    ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String[][] labelsWithValues = {
				{ PageLabel.Account_Name.toString(), UpdatedCLFirm4 }};
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	   
		   if (fp.clickOnTab(environment, mode, TabName.AccountsTab)) {
		       log(LogStatus.INFO, "Click on Tab : " + TabName.AccountsTab, YesNo.No);
		
		      if (fp.clickOnAlreadyCreatedItem(projectName, TabName.AccountsTab,CLFirm4, 10)) {
		           if (ip.UpdateLegalNameAccount(projectName, UpdatedCLFirm4, 5)) {
		               log(LogStatus.INFO, "successfully update legal name " + UpdatedCLFirm4, YesNo.Yes);
		           } else {
		               sa.assertTrue(false, "not able to update legal name " + UpdatedCLFirm4);
		               log(LogStatus.SKIP, "not able to update legal name " + UpdatedCLFirm4, YesNo.Yes);
		           }
		       } else {
		          sa.assertTrue(false, "Not Able to open created Account : " + CLFirm4);
		           log(LogStatus.SKIP, "Not Able to open created Account: " + CLFirm4, YesNo.Yes);
		      }
		   } else {
		       log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		       sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		   }
		   
		switchToDefaultContent(driver);
		refresh(driver);
		ThreadSleep(5000);
		if (lp.clickOnTab(projectName,mode, TabName.ContactTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
			 if (fp.clickOnAlreadyCreatedItem(projectName,TabName.ContactTab, CLConFN4 + " " + CLConLN4 , 10)) {
		      
			ThreadSleep(2000);
			click(driver, cp.getRelatedTab(RelatedTab.Details.toString(), 20), "detail tab", action.BOOLEAN);
			ThreadSleep(2000);
			for (String[] labelWithValue : labelsWithValues) {

				if (cp.fieldValueVerificationOnContactPage(environment, mode, TabName.ContactTab,
						labelWithValue[0], labelWithValue[1])) {
					log(LogStatus.PASS,
							labelWithValue[0] + " : with value : " + labelWithValue[1] + "  Verified",
							YesNo.No);
				} else {
					sa.assertTrue(false, labelWithValue[0] + " : with value : " + labelWithValue[1]
							+ " Not Verified");
					log(LogStatus.FAIL, labelWithValue[0] + " : with value : " + labelWithValue[1]
							+ " Not Verified", YesNo.Yes);
				}
			}
		 } else {
	          sa.assertTrue(false, "Not Able to open created contact : " + CLConFN4 + " " + CLConLN4);
	           log(LogStatus.SKIP, "Not Able to open created contact: " + CLConFN4 + " " + CLConLN4, YesNo.Yes);
	      }
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.ContactTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.ContactTab, YesNo.Yes);
		}
	
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName"})
@Test
	public void TCLTc021_CreateNewRecordtypeAndVerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	String themeNameForAddToTheme = themeNameAndDescriptions[0];
	String RecordTypeList = CLRecordType5;
	String RecordTypeArray[] = RecordTypeList.split(breakSP,-1);
	
	String[][][] RecordType = {
			{ { recordTypeLabel.Record_Type_Label.toString(), RecordTypeArray[0] },
					{ recordTypeLabel.Description.toString(), RecordTypeArray[0] + " " + bp.recordTypeDescription },
					{ recordTypeLabel.Active.toString(), "" } } };
	
	String[] profileForSelection = { "MnA Standard User","System Administrator"};
	String layout = "Advisor Layout";
	String themeTabName = "Themes";
	boolean isMakeAvailable = false;
	boolean isMakeDefault = false;
	boolean flag = false;
	String parentID=null;
	
	for (int i = 0; i < RecordTypeArray.length; i++) {
		home.notificationPopUpClose();
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Account.toString())) {
					if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Account,
							ObjectFeatureName.recordTypes)) {
						if (i == 0) {
							if (sp.listOfRecordTypes().contains(RecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + RecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, RecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, layout, 10);
							}
						} else {
							isMakeDefault = false;

							if (sp.listOfRecordTypes().contains(RecordType[i][0][1])) {
								log(LogStatus.INFO, "Record Type: " + RecordType[i][0][1]
										+ " is already created, So not going to Create", YesNo.No);
								flag = true;
							} else {
								flag = sp.createRecordTypeForObject(projectName, RecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, layout, 10);
							}
						}
						if (flag) {
							log(LogStatus.INFO, "Created Record Type : " + RecordTypeArray[i], YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not Able to Create Record Type : " + RecordTypeArray[i],
									YesNo.Yes);
							sa.assertTrue(false, "Not Able to Create Record Type : " + RecordTypeArray[i]);
						}

					} else {
						log(LogStatus.ERROR,
								"object feature " + ObjectFeatureName.recordTypes + " is not clickable", YesNo.Yes);
						sa.assertTrue(false,
								"object feature " + ObjectFeatureName.recordTypes + " is not clickable");
					}
				} else {
					log(LogStatus.ERROR, object.Contact + " object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, object.Contact + " object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
				refresh(driver);
			} else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		} else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
	}
	lp.CRMlogout();
	refresh(driver);
	ThreadSleep(5000);
	lp.CRMLogin(crmUser1EmailID, adminPassword);//change
	
	String[][] entitys = {{ CLFirm5, CLRecordType5}};

	ThreadSleep(5000);
	for(int i=0;i<entitys.length;i++) {
		if (lp.clickOnTab(projectName,mode, TabName.AccountsTab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.AccountsTab,YesNo.No);	

			if (ip.createEntityOrAccount(environment, mode, entitys[i][0], entitys[i][1], null,null,10)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+entitys[i][0]+" of record type : "+entitys[i][1],YesNo.No);	
				CommonLib.refresh(driver);
				if (theme.createAddToTheme(false, false, true, true, false, PageName.Object1Page, projectName, themeTabName,
						themeNameForAddToTheme, "Advisor", tabObj1, themeNameForAddToTheme, null, null, false, false, false, null)) {
					log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeNameForAddToTheme + " for Object: "
							+ tabObj1 + " and for Record: " + themeNameForAddToTheme + " -----", YesNo.No);
				} else {
					sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeNameForAddToTheme
							+ " for Object: " + tabObj1 + " and for Record: " + themeNameForAddToTheme + " -----");
					log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeNameForAddToTheme
							+ " for Object: " + tabObj1 + " and for Record: " + themeNameForAddToTheme + " -----", YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+entitys[i][0]+" of record type : "+entitys[i][1]);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+entitys[i][0]+" of record type : "+entitys[i][1],YesNo.Yes);
			}


		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.AccountsTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.AccountsTab,YesNo.Yes);
		}

	}
	refresh(driver);
	ThreadSleep(5000);
	if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

		if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameForAddToTheme,
				"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, themeNameForAddToTheme + " value has been passed in Theme Search Box",
					YesNo.No);
			if (theme.clickOnAlreadyCreatedItem(projectName, themeNameForAddToTheme, 10)) {
				log(LogStatus.INFO, "Clicked on Theme " + themeNameForAddToTheme,YesNo.No);
				  ThreadSleep(5000);
				String parent = switchOnWindow(driver);
		    	  ThreadSleep(5000);
			if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
					"Call List Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on call list button",YesNo.No);

					if (CommonLib.clickUsingJavaScript(driver, theme.importContactbutton(5),
							"Import Contact Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the Import Contacts Button", YesNo.No);
						if (isDisplayed(driver, theme.FirmInImportContacts(CLFirm5,5),
								"Import Contact Button",10,"") != null) {
							log(LogStatus.INFO, "clicked on " + CLFirm5,YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not able to click on" + CLFirm5, YesNo.No);
							sa.assertTrue(false, "Not able to click on " + CLFirm5);
						}
					} else {
						log(LogStatus.ERROR, "Not able to Click on Import Contacts Button",YesNo.No);
						sa.assertTrue(false, "Not able to Click on Import Contacts Button");
					}
			} else {
				log(LogStatus.ERROR, "Not able to click on Call List Button",
						YesNo.No);
				sa.assertTrue(false, "Not able to click on Call List Button");
			}	
			} else {
				log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameForAddToTheme,
						YesNo.No);
				sa.assertTrue(false, "Not able to click on created Theme " + themeNameForAddToTheme);
			}
		} else {
			log(LogStatus.ERROR, themeNameForAddToTheme + " value is not passed in Theme Search Box",
					YesNo.No);
			sa.assertTrue(false, themeNameForAddToTheme + " value is not passed in Theme Search Box");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
	}
	switchToDefaultContent(driver);
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
	public void TCLTc022_CreateContactAndVerifyCallOnImportContactTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1),callList = {CLConFN23 +" " + CLConLN23};
		String callName = CLConFN23 + " " + CLConLN23,themeNameToNavigate = themeNameAndDescriptions[0], Value = CLConFirm23;
		
		String[][] contacts = {{CLConFN23, CLConLN23,CLConFirm23,null,null,null,null}};
			
				ThreadSleep(5000);
				for(int i=0;i<contacts.length;i++) {
					if (lp.clickOnTab(projectName,mode, TabName.ContactTab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
			
						if (cp.createContact(environment, mode, contacts[i][0], contacts[i][1], contacts[i][2], contacts[i][3],
								Fields.Phone.toString(), contacts[i][6],CreationPage.ContactPage,contacts[i][5],contacts[i][4])) {//change
							log(LogStatus.INFO, "successfully Created Contact : " + contacts[i][0] + " " + contacts[i][1],
									YesNo.No);
						} else {
							sa.assertTrue(false, "Not Able to Create Contact : " + contacts[i][0] + " " + contacts[i][1]);
							log(LogStatus.SKIP, "Not Able to Create Contact: " + contacts[i][0] + " " + contacts[i][1],
									YesNo.Yes);
						}
			
					} else {
						sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.ContactTab);
						log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.ContactTab, YesNo.Yes);
					}
				}
		ThreadSleep(5000);
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	  ThreadSleep(5000);
					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						if(theme.ContactOnMyCallList(callName,5) == null) { 
							log(LogStatus.INFO, "Verified Call " + callName + " is not visible",
								YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not Verified account " + callName + " is visible", YesNo.No);
							sa.assertTrue(false, "Not Verified account " + callName + " is visible");
							
						}
						if(CommonLib.clickUsingJavaScript(driver, theme.importContactbutton(5),
								"Import Contacts Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Import Contacts button",YesNo.No);
							if(CommonLib.clickUsingJavaScript(driver, theme.getCheckboxForFirmInImportContactPopup(Value,5),
									"Import Contacts Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Import Contacts button",YesNo.No);
								if(CommonLib.clickUsingJavaScript(driver, theme.importbutton(5),
										"Import Button on Import Contacts Popup", action.SCROLLANDBOOLEAN)) { 
									log(LogStatus.INFO, "Clicked on Import Button on Import Contacts Popup",
										YesNo.No);
									ThreadSleep(1000);
									if(theme.getImportToastMsg(5) != null) {
										log(LogStatus.INFO, "The Contacts has been successfully imported",YesNo.No);
										ThreadSleep(3000);
										for(String value : callList) {
											if(theme.ContactOnMyCallList(value,5) != null) { 
												log(LogStatus.INFO, "Verified Call " + value + " is not visible",
													YesNo.No);
											} else {
												log(LogStatus.ERROR, "Not Verified account " + value + " is visible", YesNo.No);
												sa.assertTrue(false, "Not Verified account " + value + " is visible");
											}
										}
									} else {
										log(LogStatus.ERROR, "Not able to import contacts", YesNo.Yes);
										sa.assertTrue(false, "Not able to import contacts");
									}
								} else {
									log(LogStatus.ERROR, "Not able to Close Icon on Import Contacts Popup", YesNo.No);
									sa.assertTrue(false, "Not able to Close Icon on Import Contacts Popup");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Import Contacts Button",YesNo.No);
								sa.assertTrue(false, "Not able to click on Import Contacts Button");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Import Contacts Button",YesNo.No);
							sa.assertTrue(false, "Not able to click on Import Contacts Button");
						}
						
					} else {
						log(LogStatus.ERROR, "Not able to click on Call List Button",YesNo.No);
						sa.assertTrue(false, "Not able to click on Call List Button");
					}	
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);
	}

@Parameters({ "projectName" })
@Test
	public void TCLTc023_VerifyCallOnImportContactTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
		String callName = CLConFN23 + " " + CLConLN23,themeNameToNavigate = themeNameAndDescriptions[0];
			
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	  ThreadSleep(5000);
					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						if(theme.ContactOnMyCallList(callName,5) != null) { 
							log(LogStatus.INFO, "Verified Call " + callName + " is visible",
								YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not Verified account " + callName + " is not visible", YesNo.No);
							sa.assertTrue(false, "Not Verified account " + callName + " is not visible");
							
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Call List Button",YesNo.No);
						sa.assertTrue(false, "Not able to click on Call List Button");
					}	
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);
	}

@Parameters({ "projectName" })
@Test
	public void TCLTc024_VerifyLogNotesOnCallListTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
		String callName = CLConFN23 + " " + CLConLN23,themeNameToNavigate = themeNameAndDescriptions[0];
		
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	  ThreadSleep(5000);
					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						if(theme.ContactOnMyCallList(callName,5) != null) { 
							log(LogStatus.INFO, "Verified Call " + callName + " is not visible",
								YesNo.No);
							if(CommonLib.clickUsingJavaScript(driver, theme.LogNoteOnMyCallList(callName,5),
									"Call List Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Log Note on call list button",YesNo.No);
								ThreadSleep(5000);
								if(theme.GetCallNotesHeader(5) != null) { 
									log(LogStatus.INFO, "Verified header Call Notes is visible",
										YesNo.No);
									if(theme.getExpandOption(5) != null) { 
										log(LogStatus.INFO, "Verified expand is visible",YesNo.No);
										if(theme.getCancelButtonOnCallNotes(5) != null  && theme.getCancelButtonOnCallNotes(5) != null) { 
											log(LogStatus.INFO, "Verified Save and cancel buttons",YesNo.No);
									} else {
										log(LogStatus.ERROR, "Not able to verify save and cancel button",YesNo.No);
										sa.assertTrue(false, "Not able to verify save and cancel button");
									}	
								} else {
									log(LogStatus.ERROR, "Not able to verify expand",YesNo.No);
									sa.assertTrue(false, "Not able to verify expand");
								}
									
							} else {
								log(LogStatus.ERROR, "Not able to verify header Call Notes",YesNo.No);
								sa.assertTrue(false, "Not able to verify header Call Notes");
							}
							}
						} else {
							log(LogStatus.ERROR, "Not Verified account " + callName + " is visible", YesNo.No);
							sa.assertTrue(false, "Not Verified account " + callName + " is visible");
							
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Call List Button",YesNo.No);
						sa.assertTrue(false, "Not able to click on Call List Button");
					}	
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);
	}

@Parameters({ "projectName" })
@Test
	public void TCLTc025_1_UpdateFieldName_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	String themeNameToNavigate = themeNameAndDescriptions[0];
	String parentWindow = "";
	boolean flag1 = false;
	String tabNames1 = TabName.Contacts.toString();
	String[] labelsWithValues1 = {"Name<break>Name Testing","Phone<break>Phone Testing"};
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
			}
			ThreadSleep(3000);
			if (setup.searchStandardOrCustomObject(environment, mode, object.Rename_Tabs_and_Labels.toString())) {
				log(LogStatus.INFO, "click on Object : " + object.Rename_Tabs_and_Labels, YesNo.No);
				ThreadSleep(2000);
	
				if (setup.renameLabelsOfFields(driver, tabNames1, labelsWithValues1, 10)) {
					flag1 = true;
					log(LogStatus.PASS, labelsWithValues1[0] + " is updated as " +labelsWithValues1[1] , YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_and_Labels, YesNo.Yes);
				sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_and_Labels);
			}	
			driver.close();
		} 
		ThreadSleep(2000);
		driver.switchTo().window(parentWindow);
		sa.assertAll();
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	  ThreadSleep(5000);
					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						for(int i = 0; i< labelsWithValues1.length;i++){
						String[] labels = labelsWithValues1[i].split("<break>");
						if(theme.HeadersOnMyCallList(labels[1],5) != null) {
							log(LogStatus.INFO, "Verified Header " + labels[1] + " is visible",
								YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to verify Header",YesNo.No);
						sa.assertTrue(false, "Not able to verify Header");
					}
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on call list button",
								YesNo.No);
						sa.assertTrue(false, "Not able to click on ccall list button");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
			ThreadSleep(2000);
			switchToDefaultContent(driver);
			sa.assertAll();
			lp.CRMlogout();
}

@Parameters({ "projectName" })
@Test
	public void TCLTc025_2_RevertUpdatedFieldName_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	String themeNameToNavigate = themeNameAndDescriptions[0];
	String parentWindow = "";
	boolean flag1 = false;
	String tabNames1 = TabName.Contacts.toString();
	String[] labelsWithValues1 = {"Name<break>Name","Phone<break>Phone"};
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
			}
			ThreadSleep(3000);
			if (setup.searchStandardOrCustomObject(environment, mode, object.Rename_Tabs_and_Labels.toString())) {
				log(LogStatus.INFO, "click on Object : " + object.Rename_Tabs_and_Labels, YesNo.No);
				ThreadSleep(2000);
	
				if (setup.renameLabelsOfFields(driver, tabNames1, labelsWithValues1, 10)) {
					flag1 = true;
					log(LogStatus.PASS, labelsWithValues1[0] + " is updated as " +labelsWithValues1[1] , YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_and_Labels, YesNo.Yes);
				sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_and_Labels);
			}	
			driver.close();
		} 
		ThreadSleep(2000);
		driver.switchTo().window(parentWindow);
		sa.assertAll();
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	  ThreadSleep(5000);
					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						for(int i = 0; i< labelsWithValues1.length;i++){
						String[] labels = labelsWithValues1[i].split("<break>");
						if(theme.HeadersOnMyCallList(labels[1],5) != null) {
							log(LogStatus.INFO, "Verified Header " + labels[1] + " is visible",
								YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to verify Header",YesNo.No);
						sa.assertTrue(false, "Not able to verify Header");
					}
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on call list button",
								YesNo.No);
						sa.assertTrue(false, "Not able to click on ccall list button");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
			ThreadSleep(2000);
			switchToDefaultContent(driver);
			sa.assertAll();
			lp.CRMlogout();
}

@Parameters({ "projectName" })
@Test
	public void TCLTc026_VerifyLogNotesOnCallListTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
		String callName = CLConFN4 + " " + CLConLN4,themeNameToNavigate = themeNameAndDescriptions[0];
		
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	ThreadSleep(5000);
					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						if(theme.ContactOnMyCallList(callName,5) != null) { 
							log(LogStatus.INFO, "Verified Call " + callName + " is not visible",
								YesNo.No);
							if(CommonLib.clickUsingJavaScript(driver, theme.LogNoteOnMyCallList(callName,5),
									"Call List Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Log Note on call list button",YesNo.No);
								ThreadSleep(5000);
										if(CommonLib.clickUsingJavaScript(driver, theme.getCancelButtonOnCallNotes(5),
												"Call List Button", action.SCROLLANDBOOLEAN)) { 
											log(LogStatus.INFO, "clicked on cancel button",YesNo.No);
											refresh(driver);
											ThreadSleep(5000);
											if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
													"Call List Button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
												if(theme.ContactOnMyCallList(callName,5) != null) { 
													log(LogStatus.INFO, "Verified Call " + callName + " is not visible",
														YesNo.No);
													if(CommonLib.clickUsingJavaScript(driver, theme.LogNoteOnMyCallList(callName,5),
															"Call List Button", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO, "Clicked on Log Note on call list button",YesNo.No);
														ThreadSleep(5000);
																if(CommonLib.clickUsingJavaScript(driver, theme.getCloseButtonOnCallNotes(5),
																		"Call List Button", action.SCROLLANDBOOLEAN)) { 
																	log(LogStatus.INFO, "clicked on close button",YesNo.No);
																	refresh(driver);
																	ThreadSleep(5000);
															} else {
																log(LogStatus.ERROR, "Not able to click on close button",YesNo.No);
																sa.assertTrue(false, "Not able to click on close button");
															}	
													} else {
														log(LogStatus.ERROR, "Not able to click on Log Note on call list button", YesNo.No);
														sa.assertTrue(false, "Not able to click on Log Note on call list button");
													}
												} else {
													log(LogStatus.ERROR, "Not Verified account " + callName + " is visible", YesNo.No);
													sa.assertTrue(false, "Not Verified account " + callName + " is visible");
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on call list button", YesNo.No);
												sa.assertTrue(false, "Not able to click on call list button");
											}
									} else {
										log(LogStatus.ERROR, "Not able to click on cancel button",YesNo.No);
										sa.assertTrue(false, "Not able to click on cancel button");
									}	
							} else {
								log(LogStatus.ERROR, "Not able to click on Log Note on call list button", YesNo.No);
								sa.assertTrue(false, "Not able to click on Log Note on call list button");
							}
						} else {
							log(LogStatus.ERROR, "Not Verified account " + callName + " is visible", YesNo.No);
							sa.assertTrue(false, "Not Verified account " + callName + " is visible");		
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Call List Button",YesNo.No);
						sa.assertTrue(false, "Not able to click on Call List Button");
					}	
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);
	}

@Parameters({ "projectName" })
@Test
	public void TCLTc027_ChangeLogNoteAndVerifyLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String xPath, taskSubject1 = "Log a Call from Call List", taskNotes1 = "Log a Call from Call List description check";
		WebElement ele;
		String[][] basicSection = { { excelLabel.Subject.toString(), taskSubject1 }, { excelLabel.Notes.toString(), taskNotes1 }};
		String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
		String callName = CLConFN23 + " " + CLConLN23,themeNameToNavigate = themeNameAndDescriptions[0];
		
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	  ThreadSleep(5000);
					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						if(theme.ContactOnMyCallList(callName,5) != null) { 
							log(LogStatus.INFO, "Verified Call " + callName + " is not visible",
								YesNo.No);
							if(CommonLib.clickUsingJavaScript(driver, theme.LogNoteOnMyCallList(callName,5),
									"Call List Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Log Note on call list button",YesNo.No);
								ThreadSleep(5000);
								for (String[] val : basicSection) {
									String labelName = val[0];
									String value = val[1];
									if (labelName.contains(excelLabel.Subject.toString())) {
										xPath = "//label[text()='" + labelName + "']/..//input[contains(@data-id,'combobox')]";
										ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
										if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
										} else {
											log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
											sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
										}
									} else if (labelName.contains(excelLabel.Notes.toString())) {

										if (value.contains("<Section>")) {
											boolean notesFlag = BP.activityTimelineNotesSuggesstionBoxHandle(value);
										} else {
											xPath = "//div[label[text()='Notes']]//textarea";
											ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);

											if (sendKeys(driver, ele, value, labelName + " paragraph", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, value + " has been passed on " + labelName + " paragraph", YesNo.No);

											} else {
												log(LogStatus.ERROR, value + " is not passed on " + labelName + " paragraph", YesNo.No);
												sa.assertTrue(false, value + " is not passed on " + labelName + " paragraph");
											}
										}
									} else {
										log(LogStatus.ERROR, "Label Name is not correct", YesNo.No);
										sa.assertTrue(false, "Label Name is not correct");
									}
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Log Note", YesNo.No);
								sa.assertTrue(false, "Not able to click on Log Note");	
							}
						} else {
							log(LogStatus.ERROR, "Not Verified account " + callName + " is visible", YesNo.No);
							sa.assertTrue(false, "Not Verified account " + callName + " is visible");	
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Call List Button",YesNo.No);
						sa.assertTrue(false, "Not able to click on Call List Button");
					}	
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);
	}

@Parameters({ "projectName" })
@Test
	public void TCLTc029_UpdateFieldName_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	String themeNameToNavigate = themeNameAndDescriptions[0];
	String parentWindow = "";
	boolean flag1 = false;
	String tabNames1 = TabName.Accounts.toString();
	String[] labelsWithValues1 = {"Account Name<break>Legal Name", "Account Record Type<break>Record Type"};
	String[] labelsWithValues = {"Account Name<break>Legal Name", "Account Record Type<break>Record Types"};
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
			}
			ThreadSleep(3000);
			if (setup.searchStandardOrCustomObject(environment, mode, object.Rename_Tabs_and_Labels.toString())) {
				log(LogStatus.INFO, "click on Object : " + object.Rename_Tabs_and_Labels, YesNo.No);
				ThreadSleep(2000);
	
				if (setup.renameSingularAndPluralLabelsOfFields(driver, tabNames1, labelsWithValues1,labelsWithValues, 10)) {
					flag1 = true;
					log(LogStatus.PASS, labelsWithValues1[0] + " is updated as " +labelsWithValues1[1] , YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_and_Labels, YesNo.Yes);
				sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_and_Labels);
			}	
			driver.close();
		} 
		ThreadSleep(2000);
		driver.switchTo().window(parentWindow);
		sa.assertAll();
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	  ThreadSleep(5000);
					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						if(CommonLib.clickUsingJavaScript(driver, theme.importContactbutton(5),
								"Import Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on import button",YesNo.No);
							ThreadSleep(2000);
						for(int i = 0; i< labelsWithValues1.length;i++){
						String[] labels = labelsWithValues1[i].split("<break>");
						if(theme.HeadersOnImportContact(labels[1],5) != null) {
							log(LogStatus.INFO, "Verified Header " + labels[1] + " is visible",
								YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to verify Header",YesNo.No);
						sa.assertTrue(false, "Not able to verify Header");
					}
						}
						} else {
							log(LogStatus.ERROR, "Not able to click on import button",
									YesNo.No);
							sa.assertTrue(false, "Not able to click on import button");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on call list button",
								YesNo.No);
						sa.assertTrue(false, "Not able to click on ccall list button");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
			ThreadSleep(2000);
			switchToDefaultContent(driver);
			sa.assertAll();
			lp.CRMlogout();
}

@Parameters({ "projectName" })
@Test
	public void TCLTc030_RevertUpdatedFieldName_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	String[] themeNameAndDescriptions = CLTheme1.split("<Break>", -1);
	String themeNameToNavigate = themeNameAndDescriptions[0];
	String parentWindow = "";
	boolean flag1 = false;
	String tabNames1 = TabName.Accounts.toString();
	String[] labelsWithValues1 = {"Account Name<break>Firm", "Account Record Type<break>Record Type"};
	String[] labelsWithValues = {"Account Name<break>Firms", "Account Record Type<break>Record Types"};
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
			}
			ThreadSleep(3000);
			if (setup.searchStandardOrCustomObject(environment, mode, object.Rename_Tabs_and_Labels.toString())) {
				log(LogStatus.INFO, "click on Object : " + object.Rename_Tabs_and_Labels, YesNo.No);
				ThreadSleep(2000);
	
				if (setup.renameSingularAndPluralLabelsOfFields(driver, tabNames1, labelsWithValues1,labelsWithValues, 10)) {
					flag1 = true;
					log(LogStatus.PASS, labelsWithValues1[0] + " is updated as " +labelsWithValues1[1] , YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_and_Labels, YesNo.Yes);
				sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_and_Labels);
			}	
			driver.close();
		} 
		ThreadSleep(2000);
		driver.switchTo().window(parentWindow);
		sa.assertAll();
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Themes.toString(), YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, theme.themeSearchBox(10), themeNameToNavigate,
					"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
						YesNo.No);
				if (theme.clickOnAlreadyCreatedItem(projectName, themeNameToNavigate, 10)) {
					log(LogStatus.INFO, "Clicked on Theme " + themeNameToNavigate,YesNo.No);
					switchOnWindow(driver);
			    	  ThreadSleep(5000);
					if(CommonLib.clickUsingJavaScript(driver, theme.getCallListButton(5),
							"Call List Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on call list button",YesNo.No);
						if(CommonLib.clickUsingJavaScript(driver, theme.importContactbutton(5),
								"Import Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on import button",YesNo.No);
							ThreadSleep(2000);
						for(int i = 0; i< labelsWithValues1.length;i++){
						String[] labels = labelsWithValues1[i].split("<break>");
						if(theme.HeadersOnImportContact(labels[1],5) != null) {
							log(LogStatus.INFO, "Verified Header " + labels[1] + " is visible",
								YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to verify Header",YesNo.No);
						sa.assertTrue(false, "Not able to verify Header");
					}
						}
						} else {
							log(LogStatus.ERROR, "Not able to click on import button",
									YesNo.No);
							sa.assertTrue(false, "Not able to click on import button");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on call list button",
								YesNo.No);
						sa.assertTrue(false, "Not able to click on ccall list button");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on created Theme " + themeNameToNavigate,
							YesNo.No);
					sa.assertTrue(false, "Not able to click on created Theme " + themeNameToNavigate);
				}
			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
						YesNo.No);
				sa.assertTrue(false, themeNameToNavigate + " value is not passed in Theme Search Box");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Themes.toString() + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Themes.toString() + " Tab");
		}
			ThreadSleep(2000);
			switchToDefaultContent(driver);
			sa.assertAll();
			lp.CRMlogout();
}

}

