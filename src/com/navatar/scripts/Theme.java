package com.navatar.scripts;


import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.SmokeC1_EmailID;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.gmailPassword;
import static com.navatar.generic.SmokeCommonVariables.gmailUserName;
import static com.navatar.generic.SmokeCommonVariables.rgUserPassword;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;
import static com.navatar.generic.SmokeCommonVariables.rgOrgPassword;
import static com.navatar.generic.SmokeCommonVariables.rgContact1;
import static com.navatar.generic.SmokeCommonVariables.rgContact3;
import static com.navatar.generic.SmokeCommonVariables.rgUser1;
import static com.navatar.generic.SmokeCommonVariables.rgUser2;
import static com.navatar.generic.SmokeCommonVariables.rgUser3;
import static com.navatar.generic.SmokeCommonVariables.rgContactPassword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import com.navatar.generic.*;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.Header;
import com.navatar.generic.EnumConstants.InstRecordType;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.MetaDataSetting;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.Stage;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.recordTypeLabel;
import com.navatar.pageObjects.*;
import com.relevantcodes.extentreports.LogStatus;


public class Theme extends BaseLib {

	@Parameters({ "projectName" })
	@Test
	public void THSTc001_1_createCRMUser(String projectName) {
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
							crmUserProfile,null)) {
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
	
	@Parameters({ "projectName" })
	@Test
	public void THSTc001_2_createCRMUser(String projectName) {
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
							crmUserProfile,null)) {
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
	
	@Parameters({ "projectName" })
	@Test
	public void THSTc001_3_createCRMUser(String projectName) {
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
							crmUserProfile,null)) {
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
	
	@Parameters({ "projectName" })
	@Test
	public void THSTc001_4_createCRMUser(String projectName) {
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
							crmUserProfile,null)) {
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
	
	@Parameters({ "projectName" })
	@Test
	public void THSTc020_PreCondition(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String themeTabName = "Themes";
		String themeName = ThemeName5;
		String themeDescription = ThemeDescription5;
		
		if (theme.createTheme(projectName, themeTabName, themeName, themeDescription)) {
			log(LogStatus.INFO, "Record: " + themeName + " has been Created under: " + themeTabName, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Record: " + themeName + " has not been Created under: " + themeTabName, YesNo.No);
			sa.assertTrue(false, "Record: " + themeName + " has not been Created under: " + themeTabName);
		}
		
		// INS
		String value = "";
		String type = "";
		String TabName1 = "";
		String[][] EntityOrAccounts = {  {TC_Firm1, TC_Firm1RecordType,null}, 
				{TC_Firm2, TC_Firm2RecordType,null},
				{TC_Firm3, TC_Firm3RecordType,null},
				{TC_Firm4, TC_Firm4RecordType,null}};

		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object1Tab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object1Tab, YesNo.Yes);
			}
		}
		
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			TC_Contact1EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, TC_Contact1EmailID, "Contact", excelLabel.Variable_Name,
					"TC_Contact_1", excelLabel.Contact_EmailId);

			if (cp.createContactAcuity(projectName, TC_Contact1FName, TC_Contact1LName, TC_Firm1, TC_Contact1EmailID,
					TC_Contact1RecordType, null, null, CreationPage.ContactPage, TC_Contact1Title, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + TC_Contact1FName + " " + TC_Contact1LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + TC_Contact1FName + " " + TC_Contact1LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + TC_Contact1FName + " " + TC_Contact1LName,
						YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
		
		
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			TC_Contact4EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, TC_Contact4EmailID, "Contact", excelLabel.Variable_Name,
					"TC_Contact_4", excelLabel.Contact_EmailId);

			if (cp.createContactAcuity(projectName, TC_Contact4FName, TC_Contact4LName, TC_Firm4, TC_Contact4EmailID,
					TC_Contact1RecordType, null, null, CreationPage.ContactPage, TC_Contact4Title, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + TC_Contact4FName + " " + TC_Contact4LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + TC_Contact4FName + " " + TC_Contact4LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + TC_Contact4FName + " " + TC_Contact4LName,
						YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
		
		
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			TC_Contact2EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, TC_Contact2EmailID, "Contact", excelLabel.Variable_Name,
					"TC_Contact_2", excelLabel.Contact_EmailId);

			if (cp.createContactAcuity(projectName, TC_Contact2FName, TC_Contact2LName, TC_Firm2, TC_Contact2EmailID,
					TC_Contact1RecordType, null, null, CreationPage.ContactPage, TC_Contact2Title, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + TC_Contact2FName + " " + TC_Contact2LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + TC_Contact2FName + " " + TC_Contact2LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + TC_Contact2FName + " " + TC_Contact2LName,
						YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			TC_Contact3EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, TC_Contact3EmailID, "Contact", excelLabel.Variable_Name,
					"TC_Contact_3", excelLabel.Contact_EmailId);

			if (cp.createContactAcuity(projectName, TC_Contact3FName, TC_Contact3LName, TC_Firm3, TC_Contact3EmailID,
					TC_Contact1RecordType, null, null, CreationPage.ContactPage, TC_Contact3Title, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + TC_Contact3FName + " " + TC_Contact3LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + TC_Contact3FName + " " + TC_Contact3LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + TC_Contact3FName + " " + TC_Contact3LName,
						YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);//Change
		sa.assertAll();
	}	
	
	@Parameters({ "projectName"})
	@Test
		public void THSTc021_VerifytheVisibilityofErrorMessageandColumnwhennoDataisVisibleinEmailListinsidetheTheme(String projectName) {
	    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	    ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	    BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	     lp.CRMLogin(crmUser1EmailID, adminPassword);
	     String expected[] = {Emaillabel.Name.toString(),Emaillabel.Title.toString(),Emaillabel.Firm.toString(),
	    		 Emaillabel.FirmType.toString(),Emaillabel.Email.toString()};	
	     String actualDetail = null;
	     String message = bp.ErrorMessageAcuity;
	     String themeName = ThemeName5;
	 
		   if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
		       log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
		       if (sendKeys(driver, bp.getsearchIcon_Interaction(20),themeName, "Search Icon Text",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
					ThreadSleep(5000);
					if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on subject of Clip :" +themeName, YesNo.No);
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
							ThreadSleep(3000);
							if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage, ShowMoreActionDropDownList.Email, 10)) {
								ThreadSleep(5000);
								
								for(int i=0;i<expected.length;i++) {
									WebElement ele = bp.listOfThemeEmailHeaderDetail(10).get(i);
									actualDetail = getText(driver, ele, "Theme Email detail",action.SCROLLANDBOOLEAN);
									if(actualDetail.contains(expected[i])) {
									log(LogStatus.INFO,actualDetail+ " matches with " +expected[i],YesNo.No);
								
								}else {
									log(LogStatus.ERROR, actualDetail+"not matches with "+expected[i], YesNo.Yes);
									sa.assertTrue(false, actualDetail+"not matches with "+expected[i]);
								}
								}
									String actualMsg = getText(driver, bp.getClipErrorMsg(20), "ClipErrorMsg", action.SCROLLANDBOOLEAN);
								
										if (message.contains(actualMsg)) {
										log(LogStatus.INFO,
												"Actual result " + actualMsg
														+ " of pop up has been matched with Expected result : " + message
														+ " for Contact Name: " + themeName,
												YesNo.No);
									} else {
										log(LogStatus.ERROR,
												"Actual result " + actualMsg
														+ " of pop up has been not matched with Expected result : "
														+ message + " for Contact Name: " + themeName,
												YesNo.No);
									}
							} else {
								log(LogStatus.ERROR, "Not able to click on show more drop down", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on show more drop down");
							}
						}
					}else {
						log(LogStatus.ERROR, "Not able to Click on subject of theme :" + themeName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on subject of theme");
					}
		       } else {
		          sa.assertTrue(false, "Not Able to open created Theme : " + themeName);
		           log(LogStatus.SKIP, "Not Able to open created Theme: " + themeName, YesNo.Yes);
		      }
		   } else {
		       log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab + " tab", YesNo.Yes);
		       sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab + " tab");
		   }
		   switchToDefaultContent(driver);
			lp.CRMlogout();
			sa.assertAll();
			
			}
							
	@Parameters({ "projectName" })
	@Test
	public void THSTc022_VerifythatvisibilityofThemeinAccountAcuityThemetaggedsectionwhenaddthemetoAccount(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String themeName = ThemeName5;
		String themeTabName = "Themes";
		String themeNameForAddToTheme = ThemeName5;

		List<String> expectedThemeRecordsToCheckRedirection = new ArrayList<String>();
		expectedThemeRecordsToCheckRedirection.add(themeName);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String[] accountNames = "TestCompany01<break>TestAdvisor01<break>TestInstitution01<break>TestIntermediary01".split("<break>", -1);


		if (theme.navigateToTheme(projectName, themeTabName, themeNameForAddToTheme, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);

		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}
		for (String accountName : accountNames) {
			CommonLib.refresh(driver);
			if (theme.createAddToTheme(false, true, false, true, false, PageName.ThemesPage, projectName, themeTabName,
					themeNameForAddToTheme,"Advisor", tabObj1, accountName, null, null, false, false, false, null)) {
				log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeNameForAddToTheme + " for Object: "
						+ tabObj1 + " and for Record: " + accountName + " -----", YesNo.No);
			} else {
				sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeNameForAddToTheme
						+ " for Object: " + tabObj1 + " and for Record: " + accountName + " -----");
				log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeNameForAddToTheme
						+ " for Object: " + tabObj1 + " and for Record: " + accountName + " -----", YesNo.Yes);
			}

		}
		 switchToDefaultContent(driver);
			lp.CRMlogout();
			sa.assertAll();
			
			}
	
	@Parameters({ "projectName"})
	@Test
		public void THSTc023_VerifytheVisibilityofErrorMessageandColumnwhennoDataisVisibleinEmailListinsidetheTheme(String projectName) {
	    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	    ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	    BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	     lp.CRMLogin(crmUser1EmailID, adminPassword);
	     String themeName = ThemeName5;
	    
	     
	     if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
		       log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
		       if (sendKeys(driver, bp.getsearchIcon_Interaction(20),themeName, "Search Icon Text",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
					ThreadSleep(5000);
					if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on subject of Clip :" +themeName, YesNo.No);
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
							ThreadSleep(3000);
							
							String subTabName = RelatedTab.Call_List.toString().replace("_"," ");
							if (bp.clicktabOnPage(subTabName)) {
								log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);
							} else {

								log(LogStatus.ERROR, "not able to click on " + subTabName + " tab", YesNo.Yes);
							}
							if (CommonLib.click(driver, tp.importContactbutton(30), "import Contact button", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on import Contact button", YesNo.No);
								if (CommonLib.click(driver, tp.importContactsSelectcheckbox(30), "import Contacts Select check box", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on import Contacts Select check box", YesNo.No);
									if (CommonLib.click(driver, tp.importbutton(30), "import button", action.BOOLEAN)) {
										log(LogStatus.INFO, "Clicked on import button", YesNo.No);
										ThreadSleep(3000);
										if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage, ShowMoreActionDropDownList.Email, 10)) {
											ThreadSleep(5000);
											
											 String contactName ="";
											 String title ="";
											 String accountname ="";
											 String firmtype ="";
											 String email ="";
											 
											 
										     String[][] EntityOrAccounts = {{TC_Contact1FName + " " + TC_Contact1LName,TC_Contact1Title,TC_Firm1,TC_Firm1RecordType,TC_Contact1EmailID},
										    		 {TC_Contact2FName + " " + TC_Contact2LName,TC_Contact2Title,TC_Firm2,TC_Firm2RecordType,TC_Contact2EmailID},
										    		 {TC_Contact3FName + " " + TC_Contact3LName,TC_Contact3Title,TC_Firm3,TC_Firm3RecordType,TC_Contact3EmailID},
										    		 {TC_Contact4FName + " " + TC_Contact4LName,TC_Contact4Title,TC_Firm4,TC_Firm4RecordType,TC_Contact4EmailID}};
											 
											 
											for (String[] accounts : EntityOrAccounts) {
												contactName =accounts[0];
												 title =accounts[1];
												 accountname =accounts[2];
												 firmtype =accounts[3];
												 email =accounts[4];
												 
											if(tp.verifyContactNameInThemeEmailList(contactName, title,accountname,firmtype,email).isEmpty()) {
												log(LogStatus.PASS, "Contact Name is verified in review target list",YesNo.No);
											}else {
												log(LogStatus.FAIL,  "Contact Name is not verified in review target list", YesNo.Yes);
												sa.assertTrue(false, "Contact Name is not verified in review target list");
											}
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on show more drop down", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on show more drop down");
										}
									 } else {
									       log(LogStatus.ERROR, "Not able to Clicked on import button", YesNo.Yes);
									       sa.assertTrue(false, "Not able to Clicked on import button");
									   }
								} else {
								       log(LogStatus.ERROR, "Not able to Clicked on import Contacts Select check box", YesNo.Yes);
								       sa.assertTrue(false, "Not able to Clicked on import Contacts Select check box");
								   }
							} else {
							       log(LogStatus.ERROR, "Not able to Clicked on import Contact button", YesNo.Yes);
							       sa.assertTrue(false, "Not able to Clicked on import Contact button");
							   }
							driver.close();
							driver.switchTo().window(parentWindowId);
						}
					} else {
					       log(LogStatus.ERROR, "Not able to Click on subject of Clip :" +themeName, YesNo.Yes);
					       sa.assertTrue(false, "Not able to Click on subject of Clip :" +themeName);
					   }
		       } else {
			       log(LogStatus.ERROR, "Not able to Pass Value : " + themeName, YesNo.Yes);
			       sa.assertTrue(false, "Not able to Pass Value : " + themeName);
			   }
	     } else {
		       log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab + " tab", YesNo.Yes);
		       sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab + " tab");
		   }
		   switchToDefaultContent(driver);
			lp.CRMlogout();
			sa.assertAll();
			
			}
	
	@Parameters({ "projectName"})
	@Test
		public void THSTc024_VerifythatIfNocontactisSelectedinEmailcontactlistthenNextbuttonisdisable(String projectName) {
	    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	    ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	    BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	     lp.CRMLogin(crmUser1EmailID, adminPassword);
	     String themeName = ThemeName5;
		 
		   if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
		       log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
		       if (sendKeys(driver, bp.getsearchIcon_Interaction(20),themeName, "Search Icon Text",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
					ThreadSleep(5000);
					if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on subject of Clip :" +themeName, YesNo.No);
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
							ThreadSleep(3000);
							if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage, ShowMoreActionDropDownList.Email, 10)) {
								ThreadSleep(5000);
								String disable = tp.EmailNextbutton(10).getAttribute("aria-disabled");
								if(disable.equalsIgnoreCase("true")) {
									log(LogStatus.INFO, "Next button is disable", YesNo.No);
								} else {
								       log(LogStatus.ERROR, "Next button is not disable", YesNo.Yes);
								       sa.assertTrue(false, "Next button is not disable");
								   }
							} else {
								log(LogStatus.ERROR, "Not able to click on show more drop down", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on show more drop down");
							}
							driver.close();
							driver.switchTo().window(parentWindowId);
						}
					}else {
						log(LogStatus.ERROR, "Not able to Click on subject of theme :" + themeName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on subject of theme");
					}
		       } else {
		          sa.assertTrue(false, "Not Able to open created Theme : " + themeName);
		           log(LogStatus.SKIP, "Not Able to open created Theme: " + themeName, YesNo.Yes);
		      }
		   } else {
		       log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab + " tab", YesNo.Yes);
		       sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab + " tab");
		   }
		   switchToDefaultContent(driver);
			lp.CRMlogout();
			sa.assertAll();
			
			}
	
	@Parameters({ "projectName"})
	@Test
		public void THSTc025_VerifyClearandApplyfunctionalityforFilterContactsSection(String projectName) {
	    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	    ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	    BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	     lp.CRMLogin(crmUser1EmailID, adminPassword);
	     String themeName = ThemeName5;
		 
		   if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
		       log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
		       if (sendKeys(driver, bp.getsearchIcon_Interaction(20),themeName, "Search Icon Text",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
					ThreadSleep(5000);
					if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on subject of Clip :" +themeName, YesNo.No);
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
							ThreadSleep(3000);
							if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage, ShowMoreActionDropDownList.Email, 10)) {
								ThreadSleep(5000);
								if (clickUsingJavaScript(driver, tp.FilterContact(30), "FilterContact",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Click on filter contact tab ", YesNo.No);
									ThreadSleep(5000);
								if(tp.SearchforEmailProspects(environment, mode, PageName.emailProspects,"Account:Record Type ID,Contact:Tier","equals,equals","Company,2","Clear")) {
									appLog.info("Filter logic applied successfully ");
									ThreadSleep(5000);
									if(tp.operatordisable(30) != null) {
										log(LogStatus.INFO, "operator is disable", YesNo.No);
										if(tp.secondrowcount(10) != null) {
											log(LogStatus.INFO, "Second row is not present", YesNo.No);
										} else {
											log(LogStatus.ERROR, "Second row is not present", YesNo.Yes);
											sa.assertTrue(true, "Second row is not present");
										}
									} else {
										log(LogStatus.ERROR, "operator is not disable", YesNo.Yes);
										sa.assertTrue(false, "operator is not disable");
									}
										} else {
											log(LogStatus.ERROR, "Not able to Filter logic applied successfully ", YesNo.Yes);
											sa.assertTrue(false, "Not able to Filter logic applied successfully ");
										}
								} else {
									log(LogStatus.ERROR, "Not able to Click on filter contact tab ", YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on filter contact tab ");
								}
										} else {
											log(LogStatus.ERROR, "Not able to click on show more drop down", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on show more drop down");
										}
										driver.close();
										driver.switchTo().window(parentWindowId);
									}
								}else {
									log(LogStatus.ERROR, "Not able to Click on subject of theme :" + themeName, YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on subject of theme");
								}
					       } else {
					          sa.assertTrue(false, "Not Able to open created Theme : " + themeName);
					           log(LogStatus.SKIP, "Not Able to open created Theme: " + themeName, YesNo.Yes);
					      }
					   } else {
					       log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab + " tab", YesNo.Yes);
					       sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab + " tab");
					   }
					   switchToDefaultContent(driver);
						lp.CRMlogout();
						sa.assertAll();
						
						}
	
	@Parameters({ "projectName"})
	@Test
		public void THSTc026_VerifytheErrormessageWhenNoDataisPresentaferFilter(String projectName) {
	    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	    ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	    BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	     lp.CRMLogin(crmUser1EmailID, adminPassword);
	     String themeName = ThemeName5;
	     String message = bp.ErrorMessageAcuity;
		   if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
		       log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
		       if (sendKeys(driver, bp.getsearchIcon_Interaction(20),themeName, "Search Icon Text",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
					ThreadSleep(5000);
					if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on subject of Clip :" +themeName, YesNo.No);
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
							ThreadSleep(3000);
							if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage, ShowMoreActionDropDownList.Email, 10)) {
								ThreadSleep(5000);
								if (clickUsingJavaScript(driver, tp.FilterContact(30), "FilterContact",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Click on filter contact tab ", YesNo.No);
									ThreadSleep(5000);
								if(tp.SearchforEmailProspects(environment, mode, PageName.emailProspects,"Account:Record Type ID,Contact:Tier,Contact:Full Name,Contact:Mobile Phone","equals,equals,equals,equals","Company,2,Email CE 1,7654897623","Apply")) {
									appLog.info("Filter logic applied successfully ");
									ThreadSleep(5000);
									String actualMsg = getText(driver, bp.getClipErrorMsg(20), "ClipErrorMsg", action.SCROLLANDBOOLEAN);
									
									if (message.contains(actualMsg)) {
									log(LogStatus.INFO,
											"Actual result " + actualMsg
													+ " of pop up has been matched with Expected result : " + message
													+ " for Contact Name: " + themeName,
											YesNo.No);
								} else {
									sa.assertTrue(false,"Actual result " + actualMsg
											+ " of pop up has been not matched with Expected result : "
											+ message);
								}
										} else {
											log(LogStatus.ERROR, "Not able to Filter logic applied successfully ", YesNo.Yes);
											sa.assertTrue(false, "Not able to Filter logic applied successfully ");
										}
								} else {
									log(LogStatus.ERROR, "Not able to Click on filter contact tab ", YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on filter contact tab ");
								}
										} else {
											log(LogStatus.ERROR, "Not able to click on show more drop down", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on show more drop down");
										}
										driver.close();
										driver.switchTo().window(parentWindowId);
									}
								}else {
									log(LogStatus.ERROR, "Not able to Click on subject of theme :" + themeName, YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on subject of theme");
								}
					       } else {
					          sa.assertTrue(false, "Not Able to open created Theme : " + themeName);
					           log(LogStatus.SKIP, "Not Able to open created Theme: " + themeName, YesNo.Yes);
					      }
					   } else {
					       log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab + " tab", YesNo.Yes);
					       sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab + " tab");
					   }
					   switchToDefaultContent(driver);
						lp.CRMlogout();
						sa.assertAll();
						
						}
	
	@Parameters({ "projectName"})
	@Test
		public void THSTc027_createNewReport(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ReportsTabBusinessLayer report = new ReportsTabBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		EmailMyTemplatesPageBusinessLayer emailtemplate = new EmailMyTemplatesPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		home.switchToClassic();
		if (home.clickOnSetUpLink(environment, Mode.Classic.toString())) {
			String[] splitedEmailTemplateFolderName = removeNumbersFromString(EmailthemeTemplate1_FolderName);
			EmailthemeTemplate1_FolderName = splitedEmailTemplateFolderName[0] + lp.generateRandomNumber();
			if (emailtemplate.createCustomEmailFolder(environment, Mode.Classic.toString(), EmailthemeTemplate1_FolderName,
					FolderAccess.ReadWrite)) {
				log(LogStatus.PASS, "Email Template Folder is created : " + EmailthemeTemplate1_FolderName, YesNo.No);
				ExcelUtils.writeData(AcuityDataSheetFilePath, EmailthemeTemplate1_FolderName, "CustomEmailFolder",
						excelLabel.Variable_Name, "EmailTemplate1", excelLabel.Email_Template_Folder_Label);
				ThreadSleep(2000);
				String[] splitedEmailTemplateName = removeNumbersFromString(EmailthemeTemplate1_TemplateName);
				EmailthemeTemplate1_TemplateName = splitedEmailTemplateName[0] + lp.generateRandomNumber();
				if (emailtemplate.createCustomEmailTemplate(environment, Mode.Classic.toString(),
						EmailthemeTemplate1_FolderName, EmailTemplateType.Text, EmailthemeTemplate1_TemplateName,
						EmailthemeTemplate1_TemplateDescription, EmailthemeTemplate1_Subject, EmailthemeTemplate1_Body)) {
					appLog.info("EMail Template is created :" + EmailthemeTemplate1_TemplateName);

					ExcelUtils.writeData(AcuityDataSheetFilePath, EmailthemeTemplate1_TemplateName, "CustomEmailFolder",
							excelLabel.Variable_Name, "EmailTemplate1", excelLabel.Email_Template_Name);

				} else {
					appLog.error("EMail Template is not created :" + EmailthemeTemplate1_TemplateName);
					sa.assertTrue(false, "EMail Template is not created :" + EmailthemeTemplate1_TemplateName);
					log(LogStatus.ERROR, "EMail Template is not created :" + EmailthemeTemplate1_TemplateName, YesNo.Yes);
				}
			} else {
				appLog.error("Not able to create Custom Email folder: " + EmailthemeTemplate1_FolderName);
				sa.assertTrue(false, "Not able to create Custom Email folder: " + EmailthemeTemplate1_FolderName);
				log(LogStatus.ERROR, "Not able to create Custom Email folder: " + EmailthemeTemplate1_FolderName, YesNo.Yes);
			}
		} else {
			appLog.error("Not able to clicked on setup link so cannot create Email Folder And Template");
			sa.assertTrue(false, "Not able to clicked on setup link so cannot create Email Folder And Template");
			log(LogStatus.ERROR, "Not able to clicked on setup link so cannot create Email Folder And Template",
					YesNo.Yes);
		}
		
		
		
//		if (home.clickOnSetUpLink(environment, Mode.Classic.toString())) {
//				String[] splitedEmailTemplateName = removeNumbersFromString(EmailthemeTemplate2_TemplateName);
//				EmailthemeTemplate2_TemplateName = splitedEmailTemplateName[0] + lp.generateRandomNumber();
//				if (emailtemplate.createCustomEmailTemplateHTML(environment, Mode.Classic.toString(),
//						EmailthemeTemplate1_FolderName, EmailTemplateType.HTML, EmailthemeTemplate2_TemplateName,
//						EmailthemeTemplate2_TemplateDescription, EmailthemeTemplate2_Subject, EmailthemeTemplate2_Body)) {
//					appLog.info("EMail Template is created :" + EmailthemeTemplate2_TemplateName);
//
//					ExcelUtils.writeData(AcuityDataSheetFilePath, EmailthemeTemplate2_TemplateName, "CustomEmailFolder",
//							excelLabel.Variable_Name, "EmailTemplate2", excelLabel.Email_Template_Name);
//
//				} else {
//					appLog.error("EMail Template is not created :" + EmailthemeTemplate2_TemplateName);
//					sa.assertTrue(false, "EMail Template is not created :" + EmailthemeTemplate2_TemplateName);
//					log(LogStatus.ERROR, "EMail Template is not created :" + EmailthemeTemplate2_TemplateName, YesNo.Yes);
//				}
//		} else {
//			appLog.error("Not able to clicked on setup link so cannot create Email Folder And Template");
//			sa.assertTrue(false, "Not able to clicked on setup link so cannot create Email Folder And Template");
//			log(LogStatus.ERROR, "Not able to clicked on setup link so cannot create Email Folder And Template",
//					YesNo.Yes);
//		}
		
		if (home.clickOnSetUpLink(environment, Mode.Classic.toString())) {
			
				String[] splitedEmailTemplateName = removeNumbersFromString(EmailthemeTemplate3_TemplateName);
				EmailthemeTemplate3_TemplateName = splitedEmailTemplateName[0] + lp.generateRandomNumber();
				if (emailtemplate.createCustomEmailTemplateCustom(environment, Mode.Classic.toString(),
						EmailthemeTemplate1_FolderName, EmailTemplateType.Custom, EmailthemeTemplate3_TemplateName,
						EmailthemeTemplate3_TemplateDescription, EmailthemeTemplate3_Subject, EmailthemeTemplate3_Body)) {
					appLog.info("EMail Template is created :" + EmailthemeTemplate3_TemplateName);

					ExcelUtils.writeData(AcuityDataSheetFilePath, EmailthemeTemplate3_TemplateName, "CustomEmailFolder",
							excelLabel.Variable_Name, "EmailTemplate3", excelLabel.Email_Template_Name);

				} else {
					appLog.error("EMail Template is not created :" + EmailthemeTemplate3_TemplateName);
					sa.assertTrue(false, "EMail Template is not created :" + EmailthemeTemplate3_TemplateName);
					log(LogStatus.ERROR, "EMail Template is not created :" + EmailthemeTemplate3_TemplateName, YesNo.Yes);
				}
		} else {
			appLog.error("Not able to clicked on setup link so cannot create Email Folder And Template");
			sa.assertTrue(false, "Not able to clicked on setup link so cannot create Email Folder And Template");
			log(LogStatus.ERROR, "Not able to clicked on setup link so cannot create Email Folder And Template",
					YesNo.Yes);
		}
		
		if (home.clickOnSetUpLink(environment, Mode.Classic.toString())) {
			
				String[] splitedEmailTemplateName = removeNumbersFromString(EmailthemeTemplate4_TemplateName);
				EmailthemeTemplate4_TemplateName = splitedEmailTemplateName[0] + lp.generateRandomNumber();
				if (emailtemplate.createCustomEmailTemplateVisualforce(environment, Mode.Classic.toString(),
						EmailthemeTemplate1_FolderName, EmailTemplateType.Visualforce, EmailthemeTemplate4_TemplateName,
						EmailthemeTemplate4_TemplateDescription, EmailthemeTemplate4_Subject, EmailthemeTemplate4_Recipient)) {
					appLog.info("EMail Template is created :" + EmailthemeTemplate4_TemplateName);

					ExcelUtils.writeData(AcuityDataSheetFilePath, EmailthemeTemplate4_TemplateName, "CustomEmailFolder",
							excelLabel.Variable_Name, "EmailTemplate4", excelLabel.Email_Template_Name);

				} else {
					appLog.error("EMail Template is not created :" + EmailthemeTemplate4_TemplateName);
					sa.assertTrue(false, "EMail Template is not created :" + EmailthemeTemplate4_TemplateName);
					log(LogStatus.ERROR, "EMail Template is not created :" + EmailthemeTemplate4_TemplateName, YesNo.Yes);
				}
			
		} else {
			appLog.error("Not able to clicked on setup link so cannot create Email Folder And Template");
			sa.assertTrue(false, "Not able to clicked on setup link so cannot create Email Folder And Template");
			log(LogStatus.ERROR, "Not able to clicked on setup link so cannot create Email Folder And Template",
					YesNo.Yes);
		}
		
		click(driver, bp.getSalesForceLightingIconnew(60), "sales force lighting icon new", action.THROWEXCEPTION);
		ThreadSleep(1000);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		
		}
	
	@Parameters({ "projectName"})
	@Test
		public void THSTc028_UncheckAvailableForUsecheckboxofEstablishinganewcompanyemailtemplateandVerifytheimpactonSendEmailpage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		 ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
		ReportsTabBusinessLayer report = new ReportsTabBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		EmailMyTemplatesPageBusinessLayer emailtemplate = new EmailMyTemplatesPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		home.switchToClassic();
		if (home.clickOnSetUpLink(environment, Mode.Classic.toString())) {
			if (emailtemplate.EditCustomEmailTemplate(environment, Mode.Classic.toString(),
					EmailthemeTemplate1_FolderName,EmailthemeTemplate1_TemplateName)) {
				appLog.info("EMail Template is edited :" + EmailthemeTemplate1_TemplateName);
			} else {
				appLog.error("EMail Template is not edited :" + EmailthemeTemplate1_TemplateName);
				sa.assertTrue(false, "EMail Template is not edited :" + EmailthemeTemplate1_TemplateName);
				log(LogStatus.ERROR, "EMail Template is not edited :" + EmailthemeTemplate1_TemplateName, YesNo.Yes);
			}
		
	} else {
		appLog.error("Not able to clicked on setup link so cannot create Email Folder And Template");
		sa.assertTrue(false, "Not able to clicked on setup link so cannot edit Email Folder And Template");
		log(LogStatus.ERROR, "Not able to clicked on setup link so cannot edit Email Folder And Template",
				YesNo.Yes);
	}
		click(driver, bp.getSalesForceLightingIconnew(60), "sales force lighting icon new", action.THROWEXCEPTION);
		ThreadSleep(1000);
		sa.assertAll();
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		 String themeName = ThemeName5;
		 String contactname = TC_Contact1FName+ " "+TC_Contact1LName;
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
		
		   if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
		       log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
		       if (sendKeys(driver, bp.getsearchIcon_Interaction(20),themeName, "Search Icon Text",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
					ThreadSleep(5000);
					if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on subject of Clip :" +themeName, YesNo.No);
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
							ThreadSleep(3000);
							if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage, ShowMoreActionDropDownList.Email, 10)) {
								ThreadSleep(5000);
								if (CommonLib.click(driver, tp.emailcontactcheckbox(contactname,30), "email contact check box", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on email contact check box", YesNo.No);
									if (CommonLib.click(driver, tp.themeEmailNextbtn1(30), "theme Email Next btn1", action.BOOLEAN)) {
										log(LogStatus.INFO, "Clicked on theme Email Next btn1", YesNo.No);
										if(tp.selectthemeEmailTemplateFromEmailContact(EmailthemeTemplate1_FolderName,"aaaa")) {
											log(LogStatus.INFO, "email folder is present and email name is not present", YesNo.No);
										} else {
											log(LogStatus.ERROR, "email folder is present and email name is present", YesNo.Yes);
											sa.assertTrue(false, "email folder is present and email name is present");
										}
										} else {
											log(LogStatus.ERROR, "Not able to Clicked on theme Email Next btn1", YesNo.Yes);
											sa.assertTrue(false, "Not able to Clicked on theme Email Next btn1");
										}
										} else {
											log(LogStatus.ERROR, "Not able to Clicked on email contact check box", YesNo.Yes);
											sa.assertTrue(false, "Not able to Clicked on email contact check box");
										}
							} else {
								log(LogStatus.ERROR, "Not able to click on show more drop down", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on show more drop down");
							}
											driver.close();
											driver.switchTo().window(parentWindowId);
										}
						
									}else {
										log(LogStatus.ERROR, "Not able to Click on subject of theme :" + themeName, YesNo.Yes);
										sa.assertTrue(false, "Not able to Click on subject of theme");
									}
						       } else {
						          sa.assertTrue(false, "Not Able to open created Theme : " + themeName);
						           log(LogStatus.SKIP, "Not Able to open created Theme: " + themeName, YesNo.Yes);
						      }
						   } else {
						       log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab + " tab", YesNo.Yes);
						       sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab + " tab");
						   }
		   sa.assertAll();
			lp.CRMlogout();
			ThreadSleep(5000);
			lp.CRMLogin(superAdminUserName, adminPassword);
			
			home.switchToClassic();
			if (home.clickOnSetUpLink(environment, Mode.Classic.toString())) {
				if (emailtemplate.EditCustomEmailTemplate(environment, Mode.Classic.toString(),
						EmailthemeTemplate1_FolderName,EmailthemeTemplate1_TemplateName)) {
					appLog.info("EMail Template is edited :" + EmailthemeTemplate1_TemplateName);
				} else {
					appLog.error("EMail Template is not edited :" + EmailthemeTemplate1_TemplateName);
					sa.assertTrue(false, "EMail Template is not edited :" + EmailthemeTemplate1_TemplateName);
					log(LogStatus.ERROR, "EMail Template is not edited :" + EmailthemeTemplate1_TemplateName, YesNo.Yes);
				}
			
		} else {
			appLog.error("Not able to clicked on setup link so cannot create Email Folder And Template");
			sa.assertTrue(false, "Not able to clicked on setup link so cannot edit Email Folder And Template");
			log(LogStatus.ERROR, "Not able to clicked on setup link so cannot edit Email Folder And Template",
					YesNo.Yes);
		}
			switchToDefaultContent(driver);
			lp.CRMlogout();
			sa.assertAll();
			
			}
	
	@Parameters({ "projectName"})
	@Test
		public void THSTc029_VerifytheUIofSendEmailpageWhenUserSelectsContactsandClicksonNextButton(String projectName) {
	    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	    ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	    BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	     lp.CRMLogin(crmUser1EmailID, adminPassword);
	     String themeName = ThemeName5;
	     String contactname= "";
	     String expected[] = {bp.eamilsgn1.toString(),bp.eamilsgn2.toString(),bp.eamilsgn3.toString()};	
	     String actualDetail = null;
		   if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
		       log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
		       if (sendKeys(driver, bp.getsearchIcon_Interaction(20),themeName, "Search Icon Text",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
					ThreadSleep(5000);
					if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on subject of theme :" +themeName, YesNo.No);
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
							ThreadSleep(3000);
							if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage, ShowMoreActionDropDownList.Email, 10)) {
								ThreadSleep(5000);
								if (CommonLib.click(driver, tp.emailcontactcheckbox(contactname,30), "email contact check box", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on email contact check box", YesNo.No);
									if (CommonLib.click(driver, tp.themeEmailNextbtn1(30), "theme Email Next btn1", action.BOOLEAN)) {
										log(LogStatus.INFO, "Clicked on theme Email Next btn1", YesNo.No);
										if(tp.selectEmailTemplateFromEmailContact(EmailthemeTemplate1_FolderName,EmailthemeTemplate1_TemplateName)) {
											log(LogStatus.INFO, "email folder and email name is selected", YesNo.No);
											for(int i=0;i<expected.length;i++) {
												WebElement ele = bp.listOfThemeEmailpage(10).get(i);
												actualDetail = getText(driver, ele, "Theme Email detail",action.SCROLLANDBOOLEAN);
												if(actualDetail.contains(expected[i])) {
												log(LogStatus.INFO,actualDetail+ " matches with " +expected[i],YesNo.No);
											
											}else {
												log(LogStatus.ERROR, actualDetail+"not matches with "+expected[i], YesNo.Yes);
												sa.assertTrue(false, actualDetail+"not matches with "+expected[i]);
											}
												
											}
										} else {
											log(LogStatus.ERROR, "email folder and email name is not selected", YesNo.Yes);
											sa.assertTrue(false, "email folder and email name is not selected");
										}
										} else {
											log(LogStatus.ERROR, "Not able to Clicked on theme Email Next btn1", YesNo.Yes);
											sa.assertTrue(false, "Not able to Clicked on theme Email Next btn1");
										}
										} else {
											log(LogStatus.ERROR, "Not able to Clicked on email contact check box", YesNo.Yes);
											sa.assertTrue(false, "Not able to Clicked on email contact check box");
										}
							} else {
								log(LogStatus.ERROR, "Not able to click on show more drop down", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on show more drop down");
							}
											driver.close();
											driver.switchTo().window(parentWindowId);
										}
						
									}else {
										log(LogStatus.ERROR, "Not able to Click on subject of theme :" + themeName, YesNo.Yes);
										sa.assertTrue(false, "Not able to Click on subject of theme");
									}
						       } else {
						          sa.assertTrue(false, "Not Able to open created Theme : " + themeName);
						           log(LogStatus.SKIP, "Not Able to open created Theme: " + themeName, YesNo.Yes);
						      }
						   } else {
						       log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab + " tab", YesNo.Yes);
						       sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab + " tab");
						   }
										
	
										switchToDefaultContent(driver);
										lp.CRMlogout();
										sa.assertAll();
										
										}


@Parameters({ "projectName" })
@Test
public void THSTc030_VerifytheSortinginEmailPageforEachcolumn (String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword);
	 String themeName = ThemeName5;
	if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
		ThreadSleep(3000);
		 if (sendKeys(driver, bp.getsearchIcon_Interaction(20),themeName, "Search Icon Text",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
				ThreadSleep(5000);
				if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Click on subject of Clip :" +themeName, YesNo.No);
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						ThreadSleep(3000);
						if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage, ShowMoreActionDropDownList.Email, 10)) {
							ThreadSleep(5000);
		if (click(driver, tp.themeEmailcontactname(30), "Name",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Click on theme Email contact name header", YesNo.No);
		List<WebElement> ele = tp.themeemailcontactname(10);
		if (CommonLib.checkSorting(driver, SortOrder.Assecending, ele)) {
			log(LogStatus.PASS, "-----------Deal Column is in Descending Order By Default  --------------",
					YesNo.No);
			sa.assertTrue(true, "-----------Deal Column is in Descending Order By Default --------------");
		} else {
			log(LogStatus.ERROR, "-----------Deal Column is not in Descending Order By Default  --------------",
					YesNo.Yes);
			sa.assertTrue(false,
					"-----------Deal Column is not in Descending Order By Default  --------------");
		}
		} else {
			log(LogStatus.ERROR, "Not able to Click on theme Email contact name header", YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on theme Email contact name header");
		}
			} else {
				log(LogStatus.ERROR, "Not able to click on show more drop down", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on show more drop down");
			}
							driver.close();
							driver.switchTo().window(parentWindowId);
						}
		
					}else {
						log(LogStatus.ERROR, "Not able to Click on subject of theme :" + themeName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on subject of theme");
					}
		       } else {
		          sa.assertTrue(false, "Not Able to open created Theme : " + themeName);
		           log(LogStatus.SKIP, "Not Able to open created Theme: " + themeName, YesNo.Yes);
		      }
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab, YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab);
	}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}


@Parameters({ "projectName"})
@Test
public void THSTc031_VerifytheUIofSendEmailpageWhenUserSelectsContactsandClicksonNextButton(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword);
	String themeName = ThemeName5;
	String text = null;
	String contactname = TC_Contact1FName+ " "+TC_Contact1LName;
	String message = bp.eamilSuccessmsg;
	int count = 4;
	if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
		if (sendKeys(driver, bp.getsearchIcon_Interaction(20), themeName, "Search Icon Text",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
			ThreadSleep(5000);
			if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Click on subject of Clip :" + themeName, YesNo.No);
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
					ThreadSleep(3000);
					if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage,
							ShowMoreActionDropDownList.Email, 10)) {
						ThreadSleep(5000);
						if (CommonLib.click(driver, tp.emailSelectAllcontactcheckbox(30),
								"email Select All contact check box", action.BOOLEAN)) {
							log(LogStatus.INFO, "email Select All contact check box", YesNo.No);
							
							int  selected = tp.themeemailcontactname(10).size();
							if(selected==count) {
								log(LogStatus.INFO, "Actual result " + selected
										+ " of pop up has been matched with Expected result : "
										+ count + " for Contact Name: " + count,
										YesNo.No);
							} else {
								sa.assertTrue(false, "Actual result " + selected
										+ " of pop up has been not matched with Expected result : "
										+ count);
							}	
							
							if (CommonLib.click(driver, tp.themeEmailNextbtn1(30), "theme Email Next btn1",
									action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on theme Email Next btn1", YesNo.No);
								ThreadSleep(3000);
								String disable = tp.EmailNextbutton(10).getAttribute("aria-disabled");
								if (disable.equalsIgnoreCase("true")) {
									log(LogStatus.INFO, "Next button is disable", YesNo.No);
								} else {
									log(LogStatus.ERROR, "Next button is not disable", YesNo.Yes);
									sa.assertTrue(false, "Next button is not disable");
								}
								if (CommonLib.click(driver, tp.themeEmailPreviousbtn1(30), "theme Email Previous btn1",
										action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on theme Email Previous btn1", YesNo.No);
									ThreadSleep(3000);
									int  Allselected = tp.themeemailcontactname(10).size();
									if(Allselected==count) {
										log(LogStatus.INFO, "Actual result " + selected
												+ " of pop up has been matched with Expected result : "
												+ count + " All contacts are still selected ",
												YesNo.No);
									} else {
										sa.assertTrue(false, "Actual result " + selected
												+ " of pop up has been not matched with Expected result :"
												+ count + " All contacts are still  not selected ");
									}
									if (CommonLib.click(driver, tp.emailSelectAllcontactcheckbox(30),
											"email Select All contact check box", action.BOOLEAN)) {
										log(LogStatus.INFO, "email Select All contact check box", YesNo.No);
									} else {
										log(LogStatus.ERROR, "Not able to email Select All contact check box", YesNo.Yes);
										sa.assertTrue(false, "Not able to email Select All contact check box");
									}
										if (CommonLib.click(driver, tp.emailcontactcheckbox(contactname,30),
												"contact check box is selected", action.BOOLEAN)) {
											log(LogStatus.INFO, "contact check box is selected", YesNo.No);
										} else {
											log(LogStatus.ERROR, "contact check box is not selected", YesNo.Yes);
											sa.assertTrue(false, "contact check box is not selected");
										}
										
									if (CommonLib.click(driver, tp.themeEmailNextbtn1(30), "theme Email Next btn1",
											action.BOOLEAN)) {
										log(LogStatus.INFO, "Clicked on theme Email Next btn1", YesNo.No);
										ThreadSleep(3000);
										if (tp.selectEmailTemplateFromEmailContact(EmailthemeTemplate1_FolderName,
												EmailthemeTemplate1_TemplateName)) {
											log(LogStatus.INFO, "email folder and email name is selected", YesNo.No);
											ThreadSleep(3000);
											if (CommonLib.click(driver, tp.themeEmailBCCcheckbox(30),
													"theme Email BCC check box", action.BOOLEAN)) {
												log(LogStatus.INFO, "Clicked on theme Email BCC check box", YesNo.No);
												if (CommonLib.click(driver, tp.themeEmailSendbtn1(30),
														"theme Email Send btn1", action.BOOLEAN)) {
													log(LogStatus.INFO, "Clicked on theme Email Send btn1", YesNo.No);
													String succesfulmsg = tp.themeEmailSendSuccessmsg(30).getText()
															.trim();

													if (message.contains(succesfulmsg)) {
														log(LogStatus.INFO, "Actual result " + succesfulmsg
																+ " of pop up has been matched with Expected result : "
																+ message + " for Contact Name: " + themeName,
																YesNo.No);
													} else {
														sa.assertTrue(false, "Actual result " + succesfulmsg
																+ " of pop up has been not matched with Expected result : "
																+ message);
													}
												} else {
													log(LogStatus.ERROR, "Not able to Clicked on theme Email Send btn1",
															YesNo.Yes);
													sa.assertTrue(false,
															"Not able to Clicked on theme Email Send btn1");
												}
											} else {
												log(LogStatus.ERROR, "Not able to Clicked on theme Email BCC check box",
														YesNo.Yes);
												sa.assertTrue(false,
														"Not able to Clicked on theme Email BCC check box");
											}
										} else {
											log(LogStatus.ERROR, "email folder and email name is not selected",
													YesNo.Yes);
											sa.assertTrue(false, "email folder and email name is not selected");
										}
									} else {
										log(LogStatus.ERROR, "Not able to Clicked on theme Email Next btn1", YesNo.Yes);
										sa.assertTrue(false, "Not able to Clicked on theme Email Next btn1");
									}
								} else {
									log(LogStatus.ERROR, "Not able to Clicked on theme Email Previous btn1", YesNo.Yes);
									sa.assertTrue(false, "Not able to Clicked on theme Email Previous btn1");
								}
							} else {
								log(LogStatus.ERROR, "Not able to Clicked on theme Email Next btn1", YesNo.Yes);
								sa.assertTrue(false, "Not able to Clicked on theme Email Next btn1");
							}

						} else {
							log(LogStatus.ERROR, "Not able to email Select All contact check box", YesNo.Yes);
							sa.assertTrue(false, "Not able to email Select All contact check box");
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on show more drop down", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on show more drop down");
					}

					driver.close();
					driver.switchTo().window(parentWindowId);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Click on subject of theme :" + themeName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on subject of theme");
			}
		} else {
			sa.assertTrue(false, "Not Able to open created Theme : " + themeName);
			log(LogStatus.SKIP, "Not Able to open created Theme: " + themeName, YesNo.Yes);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab, YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab);
	}
	ThreadSleep(5000);
	
	switchToDefaultContent(driver);
	lp.CRMlogout(environment, mode);
	EmailLib email = new EmailLib();
	
	
	try {
		text = email.getEMailContent(gmailUserName, gmailPassword, crmUser1EmailID, TC_Contact1EmailID,
				"Capital Call Notice");
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		sa.assertTrue(false, "Email Template is not verified from email : " + gmailUserName);
		log(LogStatus.ERROR, "Email Template is not verified from email : " + gmailUserName, YesNo.No);
		e.printStackTrace();
	}
}


@Parameters({ "projectName"})
@Test
public void THSTc032_VerifyClearandApplyfunctionalityforFilterContactsSection(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword);
	String themeName = ThemeName5;
	String contactname = "";
	String message = bp.ErrorMessageAcuity;
	if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
		if (sendKeys(driver, bp.getsearchIcon_Interaction(20), themeName, "Search Icon Text",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
			ThreadSleep(5000);
			if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Click on subject of Clip :" + themeName, YesNo.No);
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
					ThreadSleep(3000);
					if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage,
							ShowMoreActionDropDownList.Email, 10)) {
						ThreadSleep(5000);
						if (clickUsingJavaScript(driver, tp.FilterContact(30), "FilterContact",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Click on filter contact tab ", YesNo.No);
							ThreadSleep(3000);
							if (tp.SearchforEmailProspects(environment, mode, PageName.emailProspects,
									"Contact:Full Name", "equals", "Theme_Email CE 1", "Apply")) {
								appLog.info("Filter logic applied successfully ");
								ThreadSleep(3000);
								if (tp.themecontactname(contactname, 20) != null) {
									log(LogStatus.INFO, "theme contact name verified is present" + contactname,
											YesNo.No);
									ThreadSleep(3000);
									if (clickUsingJavaScript(driver, tp.FilterContact(30), "FilterContact",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Click on filter contact tab ", YesNo.No);
										ThreadSleep(3000);
										if (tp.SearchforEmailProspects(environment, mode, PageName.emailProspects,
												"Contact:Full Name", "equals", "", "Apply")) {
											appLog.info("Filter logic applied successfully ");
											ThreadSleep(3000);
											String actualMsg = getText(driver, bp.getClipErrorMsg(20), "ClipErrorMsg",
													action.SCROLLANDBOOLEAN);

											if (message.contains(actualMsg)) {
												log(LogStatus.INFO,
														"Actual result " + actualMsg
																+ " of pop up has been matched with Expected result : "
																+ message + " for Contact Name: " + themeName,
														YesNo.No);
											} else {
												log(LogStatus.ERROR, "Actual result " + actualMsg
														+ " of pop up has been not matched with Expected result : "
														+ message + " for Contact Name: " + themeName, YesNo.No);
											}
											if (clickUsingJavaScript(driver, tp.FilterContact(30), "FilterContact",
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "Click on filter contact tab ", YesNo.No);
												ThreadSleep(3000);
												if (tp.SearchforEmailProspects(environment, mode,
														PageName.emailProspects, "Contact:Full Name,Account:Institution Name",
														"equals,not equal to", "Theme_Email CE 1,TestAdvisor",
														"Apply")) {
													appLog.info("Filter logic applied successfully ");
													ThreadSleep(3000);
													if (tp.themecontactname(contactname, 20) != null) {
														log(LogStatus.INFO,
																"theme contact name verified is present" + contactname,
																YesNo.No);
													} else {
														log(LogStatus.ERROR,
																"theme contact name not verified not present"
																		+ contactname,
																YesNo.Yes);
														sa.assertTrue(false,
																"theme contact name not verified not present"
																		+ contactname);
													}
												} else {
													log(LogStatus.ERROR, "Filter logic not applied successfully ",
															YesNo.Yes);
													sa.assertTrue(false, "Filter logic not applied successfully ");
												}
											} else {
												log(LogStatus.ERROR, "Not able to Click on filter contact tab",
														YesNo.Yes);
												sa.assertTrue(false, "Not able to Click on filter contact tab");
											}
										} else {
											log(LogStatus.ERROR, "Filter logic not applied successfully ", YesNo.Yes);
											sa.assertTrue(false, "Filter logic not applied successfully ");
										}
									} else {
										log(LogStatus.ERROR, "Not able to Click on filter contact tab", YesNo.Yes);
										sa.assertTrue(false, "Not able to Click on filter contact tab");
									}
								} else {
									log(LogStatus.ERROR, "theme contact name not verified not present" + contactname,
											YesNo.Yes);
									sa.assertTrue(false, "theme contact name not verified not present" + contactname);
								}
							} else {
								log(LogStatus.ERROR, "Filter logic not applied successfully ", YesNo.Yes);
								sa.assertTrue(false, "Filter logic not applied successfully ");
							}
						} else {
							log(LogStatus.ERROR, "Not able to Click on filter contact tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on filter contact tab");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on show more drop down", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on show more drop down");
					}

					driver.close();
					driver.switchTo().window(parentWindowId);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Click on subject of theme :" + themeName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on subject of theme");
			}
		} else {
			sa.assertTrue(false, "Not Able to open created Theme : " + themeName);
			log(LogStatus.SKIP, "Not Able to open created Theme: " + themeName, YesNo.Yes);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab, YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab);
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void THSTc033_VerifythatCustomerisnotVisibleifEmailisnotPresentfortheCustomer(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
    BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword);
	 String themeName = ThemeName5;
	 String contactname = TC_Contact5FName+ " "+TC_Contact5LName;
	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		TC_Contact5EmailID = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(AcuityDataSheetFilePath, TC_Contact5EmailID, "Contact", excelLabel.Variable_Name,
				"TC_Contact_5", excelLabel.Contact_EmailId);

		if (cp.createContactAcuity(projectName, TC_Contact5FName, TC_Contact5LName, TC_Firm2, TC_Contact5EmailID,
				TC_Contact5RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO, "successfully Created Contact : " + TC_Contact5FName + " " + TC_Contact5LName,
					YesNo.No);
		} else {
			sa.assertTrue(false, "Not Able to Create Contact : " + TC_Contact5FName + " " + TC_Contact5LName);
			log(LogStatus.SKIP, "Not Able to Create Contact: " + TC_Contact5FName + " " + TC_Contact5LName,
					YesNo.Yes);
		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
	}
	
	  if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
	       if (sendKeys(driver, bp.getsearchIcon_Interaction(20),themeName, "Search Icon Text",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
				ThreadSleep(5000);
				if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Click on subject of Clip :" +themeName, YesNo.No);
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						ThreadSleep(3000);
						
						String subTabName = RelatedTab.Call_List.toString().replace("_"," ");
						if (bp.clicktabOnPage(subTabName)) {
							log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);
						} else {

							log(LogStatus.ERROR, "not able to click on " + subTabName + " tab", YesNo.Yes);
						}
						if (CommonLib.click(driver, tp.importContactbutton(30), "import Contact button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on import Contact button", YesNo.No);
							if (CommonLib.click(driver, tp.emailcontactcheckbox(TC_Firm2,30), "email firm check box", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on email contact check box" + TC_Firm2, YesNo.No);
								if (CommonLib.click(driver, tp.importbutton(30), "import button", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on import button", YesNo.No);
									ThreadSleep(3000);
									if (tp.themecontactname(contactname, 20) != null) {
										log(LogStatus.INFO, "theme contact name verified is present" + contactname,
												YesNo.No);
									} else {
										log(LogStatus.ERROR, "theme contact name not not verified is not present" + contactname, YesNo.Yes);
										sa.assertTrue(false, "theme contact name not verified is not present" + contactname);
									}
								} else {
									log(LogStatus.ERROR, "Not able to Clicked on import button", YesNo.Yes);
									sa.assertTrue(false, "Not able to Clicked on import button");
								}
							} else {
								log(LogStatus.ERROR, "Not able to Clicked on email contact check box" + TC_Firm2, YesNo.Yes);
								sa.assertTrue(false, "Not able to Clicked on email contact check box" + TC_Firm2);
							}
						} else {
							log(LogStatus.ERROR, "Not able to Clicked on import Contact button", YesNo.Yes);
							sa.assertTrue(false, "Not able to Clicked on import Contact button");
						}
						if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage, ShowMoreActionDropDownList.Email, 10)) {
							log(LogStatus.INFO, "Email is  selected from drop down", YesNo.No);
							ThreadSleep(5000);
							if (tp.themecontactname(contactname, 20) != null) {
								log(LogStatus.INFO, "theme contact name verified is present" + contactname,
										YesNo.No);
							} else {
								log(LogStatus.ERROR, "theme contact name not not verified is not present" + contactname, YesNo.Yes);
								sa.assertTrue(false, "theme contact name not verified is not present" + contactname);
							}
						} else {
							log(LogStatus.ERROR, "Email is not selected from drop down", YesNo.Yes);
							sa.assertTrue(false, "Email is not selected from drop down");
						}
						driver.close();
						driver.switchTo().window(parentWindowId);
					}
				} else {
					log(LogStatus.ERROR, "Not able to Click on subject of theme :" + themeName, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on subject of theme");
				}
			} else {
				
				log(LogStatus.SKIP, "Not Able to open created Theme: " + themeName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to open created Theme : " + themeName);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab);
		}
									
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void THSTc034_VerifytheUIForMaxCharacterinContactNameandFirmname(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
    BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword);
	String value = "";
	String type = "";
	String TabName1 = "";
	String[][] EntityOrAccounts = {{TC_Firm5, TC_Firm5RecordType,null}};
	String themeName = ThemeName5;
	for (String[] accounts : EntityOrAccounts) {
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			value = accounts[0];
			type = accounts[1];
			if (ip.createEntityOrAccount(environment, mode, value, type, null, null,20)) {
				log(LogStatus.INFO, "successfully Created Account/Entity : " + value + " of record type : " + type,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Account/Entity : " + value + " of record type : " + type);
				log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + value + " of record type : " + type,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object1Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object1Tab, YesNo.Yes);
		}
	}
	 String contactname = TC_Contact6FName+ " "+TC_Contact6LName;
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			TC_Contact6EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, TC_Contact6EmailID, "Contact", excelLabel.Variable_Name,
					"TC_Contact_6", excelLabel.Contact_EmailId);

			if (cp.createContactAcuity(projectName, TC_Contact6FName, TC_Contact6LName, TC_Firm5, TC_Contact6EmailID,
					TC_Contact6RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + TC_Contact6FName + " " + TC_Contact6LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + TC_Contact6FName + " " + TC_Contact6LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + TC_Contact6FName + " " + TC_Contact6LName,
						YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
		
		if (fp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, TC_Firm5, 30)) {
				log(LogStatus.INFO, "Click on Tab : " + TC_Firm5, YesNo.No);
				ThreadSleep(5000);
				if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.CompanyPage, ShowMoreActionDropDownList.Add_to_Theme, 10)) {
					log(LogStatus.INFO, "Add to theme is  selected from drop down", YesNo.No);
					ThreadSleep(5000);
					if (sendKeys(driver, tp.addToThemePopUpSearchBox2(20), themeName, "Theme Search Box", action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to Pass the Value:  " + themeName, YesNo.No);

						if (CommonLib.clickUsingJavaScript(driver, tp.addToThemePopUpSearchBoxDropDownValue2(themeName, 20),
								"Dropdown Value: " + themeName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Dropdown Value: " + themeName, YesNo.No);
							if (CommonLib.clickUsingJavaScript(driver, tp.addToThemePopUpSaveButton(10),
									"Dropdown Value: " + themeName, action.SCROLLANDBOOLEAN)) {
			                    } else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.No);

							}
						} else {
							log(LogStatus.ERROR, "Not Able to Click on Dropdown Value: " + themeName, YesNo.No);

						}
					} else {
						log(LogStatus.ERROR, "Not Able to Pass the Value:  " + themeName, YesNo.No);

					}
		               
		               
		               if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
		        	       log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
		        	       if (sendKeys(driver, bp.getsearchIcon_Interaction(20),themeName, "Search Icon Text",
		        					action.SCROLLANDBOOLEAN)) {
		        				log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
		        				ThreadSleep(5000);
		        				if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject",
		        						action.SCROLLANDBOOLEAN)) {
		        					log(LogStatus.INFO, "Click on subject of Clip :" +themeName, YesNo.No);
		        					String parentWindowId = CommonLib.switchOnWindow(driver);
		        					if (!parentWindowId.isEmpty()) {
		        						ThreadSleep(3000);
		        						
		        						String subTabName = RelatedTab.Call_List.toString().replace("_"," ");
		        						if (bp.clicktabOnPage(subTabName)) {
		        							log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);
		        						} else {

		        							log(LogStatus.ERROR, "not able to click on " + subTabName + " tab", YesNo.Yes);
		        						}
		        						if (CommonLib.click(driver, tp.importContactbutton(30), "import Contact button", action.BOOLEAN)) {
		        							log(LogStatus.INFO, "Clicked on import Contact button", YesNo.No);
		        							if (CommonLib.click(driver, tp.emailcontactcheckbox(TC_Firm5,30), "email firm check box", action.BOOLEAN)) {
		        								log(LogStatus.INFO, "Clicked on email contact check box" + TC_Firm5, YesNo.No);
		        								if (CommonLib.click(driver, tp.importbutton(30), "import button", action.BOOLEAN)) {
		        									log(LogStatus.INFO, "Clicked on import button", YesNo.No);
		        									ThreadSleep(3000);
		        									if (tp.themecontactname(contactname, 20) != null) {
		        										log(LogStatus.INFO, "theme contact name verified is present" + contactname,
		        												YesNo.No);
		        									} else {
		        										log(LogStatus.ERROR, "theme contact name not not verified is not present" + contactname, YesNo.Yes);
		        										sa.assertTrue(false, "theme contact name not verified is not present" + contactname);
		        									}
		        								} else {
		        									log(LogStatus.ERROR, "Not able to Clicked on import button", YesNo.Yes);
		        									sa.assertTrue(false, "Not able to Clicked on import button");
		        								}
		        							} else {
		        								log(LogStatus.ERROR, "Not able to Clicked on email contact check box" + TC_Firm5, YesNo.Yes);
		        								sa.assertTrue(false, "Not able to Clicked on email contact check box" + TC_Firm5);
		        							}
		        						} else {
		        							log(LogStatus.ERROR, "Not able to Clicked on import Contact button", YesNo.Yes);
		        							sa.assertTrue(false, "Not able to Clicked on import Contact button");
		        						}
		        						driver.close();
		        						driver.switchTo().window(parentWindowId);
		        					}
		        					} else {
		        						log(LogStatus.ERROR, "Not able to Click on subject of theme :" + themeName, YesNo.Yes);
		        						sa.assertTrue(false, "Not able to Click on subject of theme");
		        					}
		        				} else {
		        					
		        					log(LogStatus.SKIP, "Not Able to open created Theme: " + themeName, YesNo.Yes);
		        					sa.assertTrue(false, "Not Able to open created Theme : " + themeName);
		        				}
		        			} else {
		        				log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab, YesNo.Yes);
		        				sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab);
		        			}
				} else {
					log(LogStatus.ERROR, "Not able to Add to theme is  selected from drop down", YesNo.Yes);
					sa.assertTrue(false, "Not able to Add to theme is  selected from drop down");
				}
			} else {
				log(LogStatus.SKIP, "Not Able to open created account: " + TC_Firm5, YesNo.Yes);
				sa.assertTrue(false, "Not Able to open created account : " + TC_Firm5);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.InstituitonsTab, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.InstituitonsTab);
		}
		        										
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();        	
}        	
	
@Parameters({ "projectName"})
@Test
	public void THSTc035_VerifyImpactSendEmailpageUsermaintainsSignatureEmailSettingVerifyUserSignatureinSendEmail(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ReportsTabBusinessLayer report = new ReportsTabBusinessLayer(driver);
	ThemePageBusinessLayer tp = new ThemePageBusinessLayer(driver);
	EmailMyTemplatesPageBusinessLayer emailtemplate = new EmailMyTemplatesPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword);
	String signature=bp.signature;
	String contactname = TC_Contact1FName+ " "+TC_Contact1LName;
	String themeName = ThemeName5;
	String message = bp.eamilSuccessmsg;
	if(clickUsingJavaScript(driver, bp.getUserMenuTab_Lightning().get(0), "profile logo"))
	{
		log(LogStatus.INFO, "Clicked on profile logo", YesNo.No);
		if(clickUsingJavaScript(driver, home.getSettingBtn(40), "Setting button"))
		{
			log(LogStatus.INFO, "Clicked on setting button", YesNo.No);

			if(sendKeysAndPressEnter(driver, home.getQuickFind(20), "My Email Settings", "quick find", action.SCROLLANDBOOLEAN))
			{

				log(LogStatus.INFO, "My Email Settings has been passed in quick find field", YesNo.No);

				if(clickUsingJavaScript(driver, home.getQuickSearchItem("My Email Settings",20), "My Email Settings record"))
				{
					log(LogStatus.INFO, "Clicked on My Email Settings record", YesNo.No);

					if(CommonLib.switchToFrame(driver, 40, home.getMyEmailSettingIframe(20)))
					{
						log(LogStatus.INFO, "Switched to my email setting iframe", YesNo.No);

						if(sendKeys(driver, home.getEmailSignature(20), signature, "email signature", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, signature+" has been passed in email signature.", YesNo.No);

							if(click(driver, home.getSaveBtn(20), "save button", action.SCROLLANDBOOLEAN))
							{								
								log(LogStatus.INFO, "Clicked on save button", YesNo.No);
								CommonLib.switchToDefaultContent(driver);
							}
							else
							{
								log(LogStatus.ERROR, "Clicked on save button", YesNo.No);
								sa.assertTrue(false, "Clicked on save button");
							}
						}
						else
						{
							log(LogStatus.ERROR, signature+" has been passed in email signature.", YesNo.No);
							sa.assertTrue(false, signature+" has been passed in email signature.");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to switched to my email setting iframe", YesNo.No);
						sa.assertTrue(false, "Not able to switched to my email setting iframe");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to clicked on My Email Settings record", YesNo.No);
					sa.assertTrue(false, "Not able to clicked on My Email Settings record");
				}
			}
			else
			{
				log(LogStatus.ERROR, "My Email Settings is not passed in quick find field", YesNo.No);
				sa.assertTrue(false,  "My Email Settings is not passed in quick find field");
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to clicked on setting button", YesNo.No);
			sa.assertTrue(false, "Not able to clicked on setting button");
		}
	}
	else
	{
		log(LogStatus.ERROR, "Not able to clicked on profile logo", YesNo.No);
		sa.assertTrue(false, "Not able to clicked on profile logo");
	}
								if (fp.clickOnTab(environment, mode, TabName.ThemesTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.ThemesTab, YesNo.No);
									if (sendKeys(driver, bp.getsearchIcon_Interaction(20), themeName, "Search Icon Text",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Pass Value : " + themeName, YesNo.No);
										ThreadSleep(5000);
										if (clickUsingJavaScript(driver, bp.getThemeSubject(themeName, 30), "subject", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Click on subject of Clip :" + themeName, YesNo.No);
											String parentWindowId = CommonLib.switchOnWindow(driver);
											if (!parentWindowId.isEmpty()) {
												ThreadSleep(3000);
												if (tp.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage,
														ShowMoreActionDropDownList.Email, 10)) {
													ThreadSleep(5000);
													if (CommonLib.click(driver, tp.emailcontactcheckbox(contactname,30),
															"contact check box is selected", action.BOOLEAN)) {
														log(LogStatus.INFO, "contact check box is selected", YesNo.No);
													} else {
														log(LogStatus.ERROR, "contact check box is not selected", YesNo.Yes);
														sa.assertTrue(false, "contact check box is not selected");
													}
													if (CommonLib.click(driver, tp.themeEmailNextbtn1(30), "theme Email Next btn1",
															action.BOOLEAN)) {
														log(LogStatus.INFO, "Clicked on theme Email Next btn1", YesNo.No);
														ThreadSleep(3000);
														if (tp.selectEmailTemplateFromEmailContact(EmailthemeTemplate1_FolderName,
																EmailthemeTemplate1_TemplateName)) {
															log(LogStatus.INFO, "email folder and email name is selected", YesNo.No);
															ThreadSleep(3000);
															if (CommonLib.click(driver, tp.themeEmailBCCcheckbox(30),
																	"theme Email BCC check box", action.BOOLEAN)) {
																log(LogStatus.INFO, "Clicked on theme Email BCC check box", YesNo.No);
																if (CommonLib.click(driver, tp.themeEmailSendbtn1(30),
																		"theme Email Send btn1", action.BOOLEAN)) {
																	log(LogStatus.INFO, "Clicked on theme Email Send btn1", YesNo.No);
																	String succesfulmsg = tp.themeEmailSendSuccessmsg(30).getText()
																			.trim();

																	if (message.contains(succesfulmsg)) {
																		log(LogStatus.INFO, "Actual result " + succesfulmsg
																				+ " of pop up has been matched with Expected result : "
																				+ message + " for Contact Name: " + themeName,
																				YesNo.No);
																	} else {
																		sa.assertTrue(false, "Actual result " + succesfulmsg
																				+ " of pop up has been not matched with Expected result : "
																				+ message);
																	}
																	ThreadSleep(5000);
																	lp.CRMlogout();
																	try {
																		EmailLib.getEmailBody("emailbody", crmUser1EmailID, ExcelUtils.readDataFromPropertyFile("gmailPassword"),crmUser1EmailID , null,null,null,signature);
																	} catch (InterruptedException e) {
																		// TODO Auto-generated catch block
																		e.printStackTrace();
																	}
																	
																} else {
																	log(LogStatus.ERROR, "Not able to Clicked on theme Email Send btn1",
																			YesNo.Yes);
																	sa.assertTrue(false,
																			"Not able to Clicked on theme Email Send btn1");
																}
															} else {
																log(LogStatus.ERROR, "Not able to Clicked on theme Email BCC check box",
																		YesNo.Yes);
																sa.assertTrue(false,
																		"Not able to Clicked on theme Email BCC check box");
															}
														} else {
															log(LogStatus.ERROR, "email folder and email name is not selected",
																	YesNo.Yes);
															sa.assertTrue(false, "email folder and email name is not selected");
														}
													} else {
														log(LogStatus.ERROR, "Not able to Clicked on theme Email Next btn1", YesNo.Yes);
														sa.assertTrue(false, "Not able to Clicked on theme Email Next btn1");
													}
												} else {
													log(LogStatus.ERROR, "Not able to click on show more drop down", YesNo.Yes);
													sa.assertTrue(false, "Not able to click on show more drop down");
												}

												driver.close();
												driver.switchTo().window(parentWindowId);
											}

										} else {
											log(LogStatus.ERROR, "Not able to Click on subject of theme :" + themeName, YesNo.Yes);
											sa.assertTrue(false, "Not able to Click on subject of theme");
										}
									} else {
										sa.assertTrue(false, "Not Able to open created Theme : " + themeName);
										log(LogStatus.SKIP, "Not Able to open created Theme: " + themeName, YesNo.Yes);
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on " + TabName.ThemesTab, YesNo.Yes);
									sa.assertTrue(false, "Not able to click on " + TabName.ThemesTab);
								}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();        	
} 
}


