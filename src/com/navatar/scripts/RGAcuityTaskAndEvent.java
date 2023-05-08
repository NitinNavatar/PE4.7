package com.navatar.scripts;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.clickUsingJavaScript;
import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.getURL;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.refresh;
import static com.navatar.generic.CommonLib.removeNumbersFromString;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.Result;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.SwitchToWindow;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.*;
import com.navatar.pageObjects.*;
import com.relevantcodes.extentreports.LogStatus;

public class RGAcuityTaskAndEvent extends BaseLib {

	public boolean isInstitutionRecord=false;

	@Parameters({ "projectName" })
	@Test
	public void RGATETc001_CreateCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		for(int k=0; k<6; k++)
		{
			lp = new LoginPageBusinessLayer(driver);
			home = new HomePageBusineesLayer(driver);
			setup = new SetupPageBusinessLayer(driver);
			String[] firstName= {RGcrmUser1FirstName,RGcrmUser2FirstName,RGcrmUser3FirstName,RGcrmUser4FirstName,RGcrmUser5FirstName,RGcrmUser6FirstName};
			String[] lastName= {RGcrmUser1LastName,RGcrmUser2LastName,RGcrmUser3LastName,RGcrmUser4LastName,RGcrmUser5LastName,RGcrmUser6LastName};
			String[] userLicence= {RGcrmUser1Lience,RGcrmUser2Lience,RGcrmUser3Lience,RGcrmUser4Lience,RGcrmUser5Lience,RGcrmUser6Lience,};
			String[] userProfile= {RGcrmUser1Profile,RGcrmUser2Profile,RGcrmUser3Profile,RGcrmUser4Profile,RGcrmUser5Profile,RGcrmUser6Profile,};
			String[] userTitle= {null,null,null,null,null,null};
			String parentWindow = null;
			String[] splitedUserLastName = removeNumbersFromString(lastName[k]);
			String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
			String emailId = lp.generateRandomEmailId(gmailUserName);

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
						if (setup.createPEUser(firstName[k], UserLastName, emailId, userLicence[k], userProfile[k], userTitle[k])) {
							log(LogStatus.INFO,
									"CRM User is created Successfully: " + firstName[k] + " " + UserLastName,
									YesNo.No);
							ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "RGUser"+(k+1),
									excelLabel.User_Email);
							ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name,
									"RGUser"+(k+1), excelLabel.User_Last_Name);
							flag = true;
							break;

						}
						driver.close();
						driver.switchTo().window(parentWindow);

					}
				} catch (Exception e) {
					log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
				}

			}
			if (flag) {

				if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
					switchToDefaultContent(driver);
					CommonLib.ThreadSleep(5000);
					if (setup.installedPackages(firstName[k], UserLastName)) {
						appLog.info("PE Package is installed Successfully in CRM User: " + firstName[k] + " "
								+ UserLastName);

					} else {
						appLog.error(
								"Not able to install PE package in CRM User1: " + firstName[k] + " " + UserLastName);
						sa.assertTrue(false,
								"Not able to install PE package in CRM User1: " + firstName[k] + " " + UserLastName);
						log(LogStatus.ERROR,
								"Not able to install PE package in CRM User1: " + firstName[k] + " " + UserLastName,
								YesNo.Yes);
					}
				}
			} else {

				log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
				sa.assertTrue(false, "could not click on setup link, test case fail");

			}
			lp.CRMlogout();
			closeBrowser();
			// driver.switchTo().window(parentWindow);
			config(ExcelUtils.readDataFromPropertyFile("Browser"));
			lp = new LoginPageBusinessLayer(driver);
			String passwordResetLink = null;
			try {
				passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
						ExcelUtils.readDataFromPropertyFile("gmailUserName"),
						ExcelUtils.readDataFromPropertyFile("gmailPassword"));
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			appLog.info("ResetLinkIs: " + passwordResetLink);
			driver.get(passwordResetLink);
			if (lp.setNewPassword()) {
				appLog.info("Password is set successfully for CRM User: " + firstName[k] + " " + UserLastName);
			} else {
				appLog.info("Password is not set for CRM User: " + firstName[k] + " " + UserLastName);
				sa.assertTrue(false, "Password is not set for CRM User: " + firstName[k] + " " + UserLastName);
				log(LogStatus.ERROR, "Password is not set for CRM User: " + firstName[k] + " " + UserLastName,
						YesNo.Yes);
			}
			lp.CRMlogout();
			ThreadSleep(8000);
		}
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc002_CreateTaskToVerifyDataOnInteractionCard(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundsPageBusinessLayer fd = new FundsPageBusinessLayer(driver);

		String[] accountName = RGATE_FirmLegalName1.split("<break>");
		String[] recordType = RGATE_FirmRecordType1.split("<break>");

		String[] contactFirstName = RGATE_ContactFirstName1.split("<break>");
		String[] contactLastName = RGATE_ContactLastName1.split("<break>");
		String[] contactLegalName = RGATE_ContactLegalName1.split("<break>");
		String[] contactEmail = RGATE_ContactEmail1.split("<break>");

		String[] dealName = RGATE_DealName1.split("<break>");
		String[] dealCompany = RGATE_DealCompany1.split("<break>");
		String[] dealStage = RGATE_DealStage1.split("<break>");

		String fundName = RGATE_FundName1;
		String fundType = RGATE_FundType1;
		String fundInvestmentCategory = RGATE_FundInvestmentCategory1;
		int status = 0;
		lp.CRMLogin(RGcrmUser1EmailID, adminPassword, appName);
		if (accountName.length == recordType.length) {
			for (int i = 0; i < accountName.length; i++) {
				if (lp.clickOnTab(projectName, tabObj1)) {

					log(LogStatus.INFO, "Click on Tab : " + tabObj1, YesNo.No);
					ThreadSleep(3000);
					if (ip.createEntityOrAccount(environment, mode, accountName[i], recordType[i], null, null, 30)) {
						log(LogStatus.INFO,
								"successfully Created Firm : " + accountName[i] + " of record type : " + recordType[i],
								YesNo.No);
						sa.assertTrue(true,
								"successfully Created Firm : " + accountName[i] + " of record type : " + recordType[i]);
						status++;

					} else {
						sa.assertTrue(false,
								"Not Able to Create Firm : " + accountName[i] + " of record type : " + recordType[i]);
						log(LogStatus.SKIP,
								"Not Able to Create Firm : " + accountName[i] + " of record type :" + recordType[i],
								YesNo.Yes);
					}

				} else {
					log(LogStatus.FAIL, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
				}

			}
		} else {
			log(LogStatus.FAIL,
					"The count of Legal name and Record Type are not equal. Either Legal Name or Record type value are not proper",
					YesNo.No);
			sa.assertTrue(false,
					"The count of Legal name and Record Type are not equal. Either Legal Name or Record type value are not proper");
		}

		if (status == accountName.length) {
			status = 0;

			for (int i = 0; i < contactLastName.length; i++) {
				if (lp.clickOnTab(projectName, tabObj2)) {

					log(LogStatus.INFO, "Click on Tab : " + tabObj2, YesNo.No);
					ThreadSleep(3000);

					if (cp.createContact(projectName, contactFirstName[i], contactLastName[i], contactLegalName[i],
							contactEmail[i], "", null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,
								"successfully Created Contact : " + contactFirstName[i] + " " + contactLastName[i],
								YesNo.No);
						sa.assertTrue(true,
								"successfully Created Contact : " + contactFirstName[i] + " " + contactLastName[i]);
						status++;

					} else {
						log(LogStatus.FAIL,
								"Not able to create the Contact : " + contactFirstName[i] + " " + contactLastName[i],
								YesNo.No);
						sa.assertTrue(false,
								"Not able to create the Contact : " + contactFirstName[i] + " " + contactLastName[i]);
					}

				} else {
					log(LogStatus.FAIL, "Not able to click on " + tabObj2 + " Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on " + tabObj2 + " Tab");
				}
			}
			if (status == contactLastName.length) {
				status = 0;
				for (int i = 0; i < dealName.length; i++) {
					if (lp.clickOnTab(projectName, tabObj4)) {

						log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
						ThreadSleep(3000);

						if (dp.createDeal(projectName, dealName[i], dealCompany[i], dealStage[i])) {
							log(LogStatus.INFO, dealName[i] + " deal has been created", YesNo.No);
							sa.assertTrue(true, dealName[i] + " deal has been created");
							status++;
						} else {
							log(LogStatus.ERROR, dealName[i] + " deal is not created", YesNo.No);
							sa.assertTrue(false, dealName[i] + " deal is not created");
						}
					}
					else {
						log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
					}
				}
				if (status == dealName.length) {

					if (lp.clickOnTab(projectName, tabObj3)) {

						log(LogStatus.INFO, "Click on Tab : " + tabObj3, YesNo.No);
						ThreadSleep(3000);

						if (fd.createFund(projectName, fundName, fundType, fundInvestmentCategory, null, null)) {
							log(LogStatus.INFO, fundName + " Fund has been created", YesNo.No);
							sa.assertTrue(true, fundName + " Fund has been created");

						} else {
							log(LogStatus.ERROR, fundName + " Fund is not created", YesNo.No);
							sa.assertTrue(false, fundName + " Fund is not created");
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on " + tabObj3 + " Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on " + tabObj3 + " Tab");
					}
				} else {
					log(LogStatus.ERROR, "Deal records are not created", YesNo.No);
					sa.assertTrue(false, "Deal records are not created");
				}

			} else {
				log(LogStatus.ERROR, "Contact records are not created", YesNo.No);
				sa.assertTrue(false, "Contact records are not created");
			}
		} else {
			log(LogStatus.FAIL, "Firm records are not created", YesNo.No);
			sa.assertTrue(false, "Firm records are not created");
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc003_1_CreateSomeRevenueInboxEvents(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);

		String username1=RGcrmUser1EmailID;
		String username2=RGcrmUser2EmailID;
		String username3=RGcrmUser3EmailID;

		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);

		String eventTitle = RGATE_Subject1;
		String[] userAndContact=RGATE_RelatedTo1.split("<userBreak>");
		String [] user=userAndContact[1].split("<b>");
		String eventAttendees=userAndContact[0];
		for(int i=0; i<user.length; i++)
		{
			if(user[i].equalsIgnoreCase("user 1"))
			{
				eventAttendees=eventAttendees+","+username1;
			}
			else if(user[i].equalsIgnoreCase("user 2"))
			{
				eventAttendees=eventAttendees+","+username2;
			}
			else if(user[i].equalsIgnoreCase("user 3"))
			{
				eventAttendees=eventAttendees+","+username3;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}

		}

		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_001", excelLabel.Advance_Start_Date);

		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_001", excelLabel.Advance_End_Date);

		String startTime = null;
		String endTime = null;
		String descriptionBox = RGATE_Notes1;


		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLookWithoutRG(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc003_2_CreateSomeRevenueInboxEvents(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);

		String username1=RGcrmUser1EmailID;
		String username2=RGcrmUser2EmailID;
		String username3=RGcrmUser3EmailID;
		ThreadSleep(3000);
		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);


		String eventTitle1 = RGATE_Subject2;
		String [] user1=RGATE_RelatedTo2.split("<b>");
		String eventAttendees1=null;

		if(user1[0].equalsIgnoreCase("user 1"))
		{
			eventAttendees1=username1;
		}
		else if(user1[0].equalsIgnoreCase("user 2"))
		{
			eventAttendees1=username2;
		}
		else if(user1[0].equalsIgnoreCase("user 3"))
		{
			eventAttendees1=username3;
		}
		else
		{
			Assertion hardAssert = new Assertion();
			log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
			hardAssert.assertTrue(true == false);
		}	
		for(int i=1; i<user1.length; i++)
		{
			if(user1[i].equalsIgnoreCase("user 1"))
			{
				eventAttendees1=eventAttendees1+","+username1;
			}
			else if(user1[i].equalsIgnoreCase("user 2"))
			{
				eventAttendees1=eventAttendees1+","+username2;
			}
			else if(user1[i].equalsIgnoreCase("user 3"))
			{
				eventAttendees1=eventAttendees1+","+username3;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}

		}

		String startDate1 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay2));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate1, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_002", excelLabel.Advance_Start_Date);

		String endDate1 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay2));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate1, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_002", excelLabel.Advance_End_Date);

		String startTime1 = null;
		String endTime1 = null;
		String descriptionBox1 = RGATE_Notes2;

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle1 + " through Outlook---------",
				YesNo.No);
		if (op.loginAndCreateEventThroughOutLookWithoutRG(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle1,
				eventAttendees1, startDate1, endDate1, startTime1, endTime1, descriptionBox1, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle1 + " has been created-----",
					YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle1
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle1
					+ " has not been created-----");
		}

		ThreadSleep(5000);

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc003_3_CreateSomeRevenueInboxEvents(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
	
		String eventTitle2 = RGATE_Subject3;
	//	String eventAttendees2=RGATE_RelatedTo3;
		String eventAttendees2="srrg@yopmail.com,srrg12@yopmail.com";
		String startDate2 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay3));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate2, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_003", excelLabel.Advance_Start_Date);

		String endDate2 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay3));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate2, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_003", excelLabel.Advance_End_Date);

		String startTime2 = null;
		String endTime2 = null;
		String descriptionBox2 = RGATE_Notes3;
		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);
		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle2 + " through Outlook---------",
				YesNo.No);
		if (op.loginAndCreateEventThroughOutLookWithoutRG(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle2,
				eventAttendees2, startDate2, endDate2, startTime2, endTime2, descriptionBox2, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle2 + " has been created-----",
					YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle2
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle2
					+ " has not been created-----");
		}

		ThreadSleep(5000);

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc003_4_CreateSomeRevenueInboxEvents(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);

		String username1=RGcrmUser1EmailID;
		String username2=RGcrmUser2EmailID;
		String username3=RGcrmUser3EmailID;

		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);

		String eventTitle3 = RGATE_Subject4;
		String[] userAndContact3=RGATE_RelatedTo4.split("<userBreak>");
		String [] user3=userAndContact3[1].split("<b>");
		String eventAttendees3=userAndContact3[0];
		for(int i=0; i<user3.length; i++)
		{
			if(user3[i].equalsIgnoreCase("user 1"))
			{
				eventAttendees3=eventAttendees3+","+username1;
			}
			else if(user3[i].equalsIgnoreCase("user 2"))
			{
				eventAttendees3=eventAttendees3+","+username2;
			}
			else if(user3[i].equalsIgnoreCase("user 3"))
			{
				eventAttendees3=eventAttendees3+","+username3;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}		
		}		
		String startDate3 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay4));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate3, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_004", excelLabel.Advance_Start_Date);

		String endDate3 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay4));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate3, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_004", excelLabel.Advance_End_Date);

		String startTime3 = null;
		String endTime3 = null;
		String descriptionBox3 = RGATE_Notes4;
		String eventRepeat=RGATE_Repeat1;

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle3 + " through Outlook---------",
				YesNo.No);
		if (op.loginAndCreateEventThroughOutLookWithoutForceSyncUp(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle3,
				eventAttendees3, startDate3, endDate3, startTime3, endTime3, descriptionBox3, false,eventRepeat)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle3 + " has been created-----",
					YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle3
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle3
					+ " has not been created-----");
		}

		ThreadSleep(5000);

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc003_5_CreateSomeRevenueInboxEvents(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);

		String username1=RGcrmUser1EmailID;
		String username2=RGcrmUser2EmailID;
		String username3=RGcrmUser3EmailID;

		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);



		String eventTitle4 = RGATE_Subject5;
		String user4=RGATE_RelatedTo5.split("<userBreak>")[1];

		String eventAttendees4=RGATE_RelatedTo5.split("<userBreak>")[0];
		if(user4.equalsIgnoreCase("user 1"))
		{
			eventAttendees4=eventAttendees4+","+username1;
		}
		else if(user4.equalsIgnoreCase("user 2"))
		{
			eventAttendees4=eventAttendees4+","+username2;
		}
		else if(user4.equalsIgnoreCase("user 3"))
		{
			eventAttendees4=eventAttendees4+","+username3;
		}
		else
		{
			Assertion hardAssert = new Assertion();
			log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
			hardAssert.assertTrue(true == false);
		}

		String startDate4 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay5));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate4, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_005", excelLabel.Advance_Start_Date);

		String endDate4 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay5));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate4, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_005", excelLabel.Advance_End_Date);


		String startTime4 = null;
		String endTime4 = null;
		String descriptionBox4 = RGATE_Notes5;

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle4 + " through Outlook---------",
				YesNo.No);
		if (op.loginAndCreateEventThroughOutLookWithoutRG(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle4,
				eventAttendees4, startDate4, endDate4, startTime4, endTime4, descriptionBox4, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle4 + " has been created-----",
					YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle4
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle4
					+ " has not been created-----");
		}


		ThreadSleep(5000);

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc003_6_CreateSomeRevenueInboxEvents(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);

		String username1=RGcrmUser1EmailID;
		String username2=RGcrmUser2EmailID;
		String username3=RGcrmUser3EmailID;

		String eventTitle5= RGATE_Subject6;
		String eventAttendees5=RGATE_RelatedTo6;

		String startDate5 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay6));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate5, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_006", excelLabel.Advance_Start_Date);

		String endDate5 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay6));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate5, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_006", excelLabel.Advance_End_Date);


		String startTime5 = null;
		String endTime5 = null;
		String descriptionBox5 = RGATE_Notes6;

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle5 + " through Outlook---------",
				YesNo.No);
		if (op.loginAndCreateEventThroughOutLookWithoutRG(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle5,
				eventAttendees5, startDate5, endDate5, startTime5, endTime5, descriptionBox5, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle5 + " has been created-----",
					YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle5
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle5
					+ " has not been created-----");
		}

		ThreadSleep(5000);

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc003_7_CreateSomeRevenueInboxEvents(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);

		String username1=RGcrmUser1EmailID;
		String username2=RGcrmUser2EmailID;
		String username3=RGcrmUser3EmailID;

		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);

		ThreadSleep(20000);

		String eventTitle6= RGATE_Subject7;
		String[] userAndContact6=RGATE_RelatedTo7.split("<userBreak>");
		String [] user6=userAndContact6[1].split("<b>");
		String eventAttendees6=userAndContact6[0];
		for(int i=0; i<user6.length; i++)
		{
			if(user6[i].equalsIgnoreCase("user 1"))
			{
				eventAttendees6=eventAttendees6+","+username1;
			}
			else if(user6[i].equalsIgnoreCase("user 2"))
			{
				eventAttendees6=eventAttendees6+","+username2;
			}
			else if(user6[i].equalsIgnoreCase("user 3"))
			{
				eventAttendees6=eventAttendees6+","+username3;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}		
		}
		String startDate6 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay7));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate6, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_007", excelLabel.Advance_Start_Date);

		String endDate6 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay7));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate6, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_007", excelLabel.Advance_End_Date);

		String startTime6 = null;
		String endTime6 = null;
		String descriptionBox6 = RGATE_Notes7;

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle6 + " through Outlook---------",
				YesNo.No);
		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle6,
				eventAttendees6, startDate6, endDate6, startTime6, endTime6, descriptionBox6, true)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle6 + " has been created-----",
					YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle6
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle6
					+ " has not been created-----");
		}

		ThreadSleep(5000);

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc004_GoToAccountAcuityTabAndVerifyRevenueEventOnInteractionsAndContactAccountInCompanyReferenceAndContactInPeopleTaggedSection(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=RGATE_FirmLegalName1.split("<break>")[0];
		String recordName1=RGATE_FirmLegalName2;

		String[] companiesTaggedName= {RGATE_TaggedCompany1,RGATE_TaggedCompany2,RGATE_TaggedCompany3};
		String[] companiesTaggedTimeReference= {RGATE_TaggedCompanyReferenceCount1,RGATE_TaggedCompanyReferenceCount2,RGATE_TaggedCompanyReferenceCount3};

		String[] peopleTagedName={RGATE_TaggedPeople1,RGATE_TaggedPeople2,RGATE_TaggedPeople3};
		String[] peopleTaggedTimeReference={RGATE_TaggedPeopleReferenceCount1,RGATE_TaggedPeopleReferenceCount2,RGATE_TaggedPeopleReferenceCount3};

		String companyTagName=RGATE_TaggedCompany1;
		String companyTagTimeReferenceCount=RGATE_TaggedCompanyReferenceCount1;

		String peopleTagName=RGATE_TaggedPeople1;
		String peopleTagTimeReferenceCount=RGATE_TaggedPeopleReferenceCount1;

		String contactSectionName[]= {RGATE_ContactSectionName1};
		String contactSectionTitle[]= {null};
		String contactSectionDeal[]= {RGATE_ContactSectionNameDeals1};
		String contactSectionMeetingAndCall[]= {RGATE_ContactSectionNameMeetingAndCall1};
		String contactSectionEmail[]= {RGATE_ContactSectionEmail1};

		String contactSectionName1[]= {RGATE_ContactSectionName2};
		String contactSectionTitle1[]= {null};
		String contactSectionDeal1[]= {RGATE_ContactSectionNameDeals2};
		String contactSectionMeetingAndCall1[]= {RGATE_ContactSectionNameMeetingAndCall2};
		String contactSectionEmail1[]= {null};


		String[] iconType= {RGATE_ActivityType1,RGATE_ActivityType3,RGATE_ActivityType5,RGATE_ActivityType6,RGATE_ActivityType7};

		String[] subjectName= {RGATE_Subject1,RGATE_Subject3,RGATE_Subject5,RGATE_Subject6,RGATE_Subject7};

		String[] details= {null,null,null,null,null};

		String[] details1= {RGATE_Notes1,RGATE_Notes3,RGATE_Notes5,RGATE_Notes6,RGATE_Notes7};

		String[] date= {RGATE_AdvanceStartDate1,RGATE_AdvanceStartDate3,RGATE_AdvanceStartDate5,RGATE_AdvanceStartDate6,RGATE_AdvanceStartDate7};

		String[][] participant= {bp.getParticipantDataRG(RGATE_Participant1),bp.getParticipantDataRG(RGATE_Participant3),bp.getParticipantDataRG(RGATE_Participant5),bp.getParticipantDataRG(RGATE_Participant6),bp.getParticipantDataRG(RGATE_Participant7)};
		
		lp.CRMLogin(RGcrmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagedName, peopleTaggedTimeReference, null, null,isInstitutionRecord,null,null);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}
					ArrayList<String> result1=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCall, contactSectionEmail);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+result1, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact :  "+result1);
					}


					if (click(driver, bp.getTaggedRecordName("Firms", 30), "Firms tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Firms tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Firms", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on firm Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);

							ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(companyTagName,iconType, date, subjectName, details, subjectName, participant, null);
							if(result2.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+companyTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+companyTagName+" record " +result2, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+companyTagName+" record "+result2);
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}


						if (click(driver, bp.getTaggedRecordTimeReference("Firms", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on firm Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);

							ArrayList<String> result5=bp.verifyDescriptionShouldNotVisibleUnderDetailsOnInteractionSection(subjectName, details1);
							if(result5.isEmpty())
							{
								log(LogStatus.INFO, "Description message is not showing under details on view all interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Description message is showing under details on view all interaction popup. "+result5, YesNo.No);
								sa.assertTrue(false,  "Description message is showing under details on view all interaction popup. "+result5);
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Firms tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Firms tab name");
					}


					if (click(driver, bp.getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("People", peopleTagName, peopleTagTimeReferenceCount,30), peopleTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+peopleTagName,YesNo.No);

							ArrayList<String> result3=bp.verifyRecordsonInteractionsViewAllPopup(peopleTagName,iconType, date, subjectName, details, subjectName, participant, null);
							if(result3.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+peopleTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+peopleTagName+" record " +result3, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+peopleTagName+" record "+result3);
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+peopleTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+peopleTagName);
						}

						if (click(driver, bp.getTaggedRecordTimeReference("People", peopleTagName, peopleTagTimeReferenceCount,30), peopleTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+peopleTagName,YesNo.No);


							ArrayList<String> result6=bp.verifyDescriptionShouldNotVisibleUnderDetailsOnInteractionSection(subjectName, details1);
							if(result6.isEmpty())
							{
								log(LogStatus.INFO, "Description message is not showing under details on view all interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Description message is showing under details on view all interaction popup. "+result6, YesNo.No);
								sa.assertTrue(false,  "Description message is showing under details on view all interaction popup. "+result6);
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+peopleTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+peopleTagName);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on People tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on People tab name");
					}



					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(recordName,iconType,date, subjectName, details, subjectName, participant, null);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result2);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}	


					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result7=bp.verifyDescriptionShouldNotVisibleUnderDetailsOnInteractionSection(subjectName, details1);
						if(result7.isEmpty())
						{
							log(LogStatus.INFO, "Description message is not showing under details on view all interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Description message is showing under details on view all interaction popup. "+result7, YesNo.No);
							sa.assertTrue(false,  "Description message is showing under details on view all interaction popup. "+result7);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}	

					if (lp.clickOnTab(projectName, tabObj1)) {

						log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
						if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
								recordName1, 30)) {
							log(LogStatus.INFO, recordName1 + " reocrd has been open", YesNo.No);

							if (bp.clicktabOnPage(TabName.Acuity.toString())) {
								log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

								ArrayList<String> result2=bp.verifyRecordOnContactSectionAcuity(contactSectionName1, contactSectionTitle1, contactSectionDeal1, contactSectionMeetingAndCall1, contactSectionEmail1);
								if(result2.isEmpty())
								{
									log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact", YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+result2, YesNo.No);
									sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact :  "+result2);
								}	
							}		
							else
							{
								log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
								sa.assertTrue(false,  "Not able to click on Acuity tab");
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to open record "+recordName1, YesNo.No);
							sa.assertTrue(false,  "Not able to open record "+recordName1);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
						sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
					}					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc005_VerifyRevenueEventsInSalesforce(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer hp=new HomePageBusineesLayer(driver);

		String recordName=RGATE_FirmLegalName1.split("<break>")[0];

		String globalEventSubjectName=RGATE_Subject2;
		String globalActivityName=RGATE_ActivityType2+"s";

		String globalEventSubjectName1=RGATE_Subject4;
		String globalActivityName1=RGATE_ActivityType4+"s";

		lp.CRMLogin(RGcrmUser1EmailID, adminPassword, appName);

		if(hp.globalSearchAndNavigate(globalEventSubjectName,globalActivityName, true))
		{
			log(LogStatus.INFO, globalEventSubjectName+" is not available in the org", YesNo.No);
		}
		else
		{
			log(LogStatus.ERROR, globalEventSubjectName+" is available in the org", YesNo.No);
			sa.assertTrue(false,  globalEventSubjectName+" is available in the org");
		}
		CommonLib.refresh(driver);
		ThreadSleep(4000);

		if(hp.globalSearchAndNavigate(globalEventSubjectName1,globalActivityName1, true))
		{
			log(LogStatus.INFO, globalEventSubjectName1+" is not available in the org", YesNo.No);
		}
		else
		{
			log(LogStatus.ERROR, globalEventSubjectName1+" is available in the org", YesNo.No);
			sa.assertTrue(false,  globalEventSubjectName1+" is available in the org");
		}

		CommonLib.refresh(driver);
		ThreadSleep(4000);
		
		String[] participant=bp.getParticipantDataRG(RGATE_Participant1);
		String relatedTo=null;
		for(int i=0; i<participant.length; i++)
		{
			if(i<participant.length-1)
			{
			relatedTo=participant[i]+"<break>";
			}
			else
			{
				relatedTo=participant[i];
			}
		}

		String eventDueDate=RGATE_AdvanceStartDate1;
		String eventSubjectName=RGATE_Subject1;
		String eventNotes=null;
		
		String[][] basicsection = { { "Subject", RGATE_Subject1 }, { "Related_To", relatedTo } };
		String[][] advanceSection = { { "Date", eventDueDate }};

		
		
		String eventNotes1=RGATE_Notes1;
		String[] eventRelatedTo=RGATE_InteractionRelatedT01.split("<break>");	

		String[] relatedAssocVal=RGATE_InteractionRelatedAssoc01.split("<break>");
		String[] eventRelatedAssociation=new String[relatedAssocVal.length];
		
		String updateEventNotes=RGATE_UNotes1;
		String updateEventStartDate=CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(RGATE_UStartDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, updateEventStartDate, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_U001", excelLabel.Advance_Start_Date);

		String updateEventEndDate=CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(RGATE_UEndDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, updateEventEndDate, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_U001", excelLabel.Advance_End_Date);


		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ArrayList<String> result=bp.verifyRecordOnInteractionCard(eventDueDate, IconType.Meeting, eventSubjectName, eventNotes, false,true, eventRelatedTo, eventRelatedAssociation);

					if(result.isEmpty())
					{
						log(LogStatus.INFO, eventSubjectName+" Event has been verified on interaction section", YesNo.No);	
					}
					else
					{
						log(LogStatus.ERROR, eventSubjectName+" Event is not verified on interaction section. "+result, YesNo.No);	
						sa.assertTrue(false, eventSubjectName+" Event is not verified on interaction section. "+result);
					}
					
					if(bp.verifyDescriptionShouldNotVisibleOnInteractionCard(eventSubjectName, eventNotes1))
					{
						log(LogStatus.INFO, "Description message : \" "+eventNotes1+" \" is not available on interaction card" + eventSubjectName, YesNo.No);
					}
					else
					{
						log(LogStatus.INFO, "Description message : \" "+eventNotes1+" \" is available on interaction card" + eventSubjectName, YesNo.No);
						sa.assertTrue(false, "Description message : \" "+eventNotes1+" \" is available on interaction card" + eventSubjectName);
					}
	
					ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, eventSubjectName, basicsection, advanceSection, IconType.Event, PageName.AcuityDetails);

					if(result2.isEmpty())
					{
						log(LogStatus.PASS,"The details on popup of subject "+eventSubjectName+" has been verified",YesNo.No);
						sa.assertTrue(true,"The details on popup of subject "+eventSubjectName+" has been verified");							
					}
					else
					{
						log(LogStatus.FAIL,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2,YesNo.No);
						sa.assertTrue(false,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2);

					}
					
					String currentUrl = getURL(driver, 10);
					refresh(driver);

					if(CommonLib.clickUsingJavaScript(driver, bp.addButtonOnInteractionCard(eventSubjectName,20), "Edit button of "+eventSubjectName))
					{
						log(LogStatus.INFO,"Clicked on Edit button on interaction section of subject name "+eventSubjectName,YesNo.No);
						ArrayList<String> NotesPopUpPrefilledNegativeResult = bp.verifyNotesPopupWithPrefilledValueAndOnSameUrl(currentUrl, basicsection,advanceSection, null);
						if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
							log(LogStatus.INFO,"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",YesNo.No);
						} else {
							log(LogStatus.ERROR,"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "+ NotesPopUpPrefilledNegativeResult,YesNo.No);
							sa.assertTrue(false,"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "+ NotesPopUpPrefilledNegativeResult);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Edit button on interaction section of subject name "+eventSubjectName,YesNo.No);
						sa.assertTrue(false, "Not able to click on Edit button on interaction section of subject name "+eventSubjectName);
					}
					refresh(driver);

	
					}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}
	
	@Parameters({ "projectName" })
	@Test
	public void RGATETc006_UpdateTheDescriptionOfRevenueEventAndVerify(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		
		String recordName=RGATE_FirmLegalName1.split("<break>")[0];


		String[] participant=bp.getParticipantDataRG(RGATE_Participant1);
		String relatedTo=null;
		for(int i=0; i<participant.length; i++)
		{
			if(i<participant.length-1)
			{
				relatedTo=participant[i]+"<break>";
			}
			else
			{
				relatedTo=participant[i];
			}
		}

		String eventDueDate=RGATE_AdvanceStartDate1;
		String eventSubjectName=RGATE_Subject1;
		String eventNotes=null;
		String[] eventRelatedTo=RGATE_InteractionRelatedT01.split("<break>");	

		String[] relatedAssocVal=RGATE_InteractionRelatedAssoc01.split("<break>");
		String[] eventRelatedAssociation=new String[relatedAssocVal.length];

		String updateEventNotes=RGATE_UNotes1;
		String[][] updateBasicSection= {{"Notes",updateEventNotes}};

		String[][] basicsection = { { "Subject", RGATE_Subject1 },{"Notes",updateEventNotes}, { "Related_To", relatedTo } };
		String[][] advanceSection = { { "Date", eventDueDate }};

		lp.CRMLogin(RGcrmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					if (click(driver, bp.addButtonOnInteractionCard(eventSubjectName,20), "Add note button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Add Note button of " + eventSubjectName, YesNo.No);

						if(bp.updateActivityTimelineRecord(projectName, updateBasicSection, null, null, null, null, false, null, null, null, null, null, null))
						{
							log(LogStatus.INFO, eventSubjectName+" Event has been updated", YesNo.No);	


							ArrayList<String> result=bp.verifyRecordOnInteractionCard(eventDueDate, IconType.Meeting, eventSubjectName, eventNotes, false,true, eventRelatedTo, eventRelatedAssociation);

							if(result.isEmpty())
							{
								log(LogStatus.INFO, eventSubjectName+" Event has been verified on interaction section", YesNo.No);	
							}
							else
							{
								log(LogStatus.ERROR, eventSubjectName+" Event is not verified on interaction section. "+result, YesNo.No);	
								sa.assertTrue(false, eventSubjectName+" Event is not verified on interaction section. "+result);
							}

							ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, eventSubjectName, basicsection, advanceSection, IconType.Event, PageName.AcuityDetails);

							if(result2.isEmpty())
							{
								log(LogStatus.PASS,"The details on popup of subject "+eventSubjectName+" has been verified",YesNo.No);
								sa.assertTrue(true,"The details on popup of subject "+eventSubjectName+" has been verified");							
							}
							else
							{
								log(LogStatus.FAIL,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2,YesNo.No);
								sa.assertTrue(false,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2);

							}

							String currentUrl = getURL(driver, 10);
							refresh(driver);

							if(CommonLib.clickUsingJavaScript(driver, bp.addButtonOnInteractionCard(eventSubjectName,20), "Edit button of "+eventSubjectName))
							{
								log(LogStatus.INFO,"Clicked on Edit button on interaction section of subject name "+eventSubjectName,YesNo.No);
								ArrayList<String> NotesPopUpPrefilledNegativeResult = bp.verifyNotesPopupWithPrefilledValueAndOnSameUrl(currentUrl, basicsection,advanceSection, null);
								if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
									log(LogStatus.INFO,"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",YesNo.No);
								} else {
									log(LogStatus.ERROR,"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "+ NotesPopUpPrefilledNegativeResult,YesNo.No);
									sa.assertTrue(false,"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "+ NotesPopUpPrefilledNegativeResult);
								}
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on Edit button on interaction section of subject name "+eventSubjectName,YesNo.No);
								sa.assertTrue(false, "Not able to click on Edit button on interaction section of subject name "+eventSubjectName);
							}
							refresh(driver);

						}
						else
						{
							log(LogStatus.ERROR, eventSubjectName+" Event is not updated", YesNo.No);
							sa.assertTrue(false,  eventSubjectName+" Event is not updated");
						}
					}
					else
					{

					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc007_VerifyEventsOnContactSectionOfAccountPageAlsoVerifyEventsOnConnectionPopup(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=RGATE_FirmLegalName1.split("<break>")[0];
		String contactName=RGATE_ContactSectionName1;

		String userName1=RGcrmUser1FirstName+" "+RGcrmUser1LastName;
		String userName2=RGcrmUser2FirstName+" "+RGcrmUser2LastName;


		String[] icon= {RGATE_ActivityType1,RGATE_ActivityType3,RGATE_ActivityType5,RGATE_ActivityType6,RGATE_ActivityType7};

		String[] subjectName= {RGATE_Subject1,RGATE_Subject3,RGATE_Subject5,RGATE_Subject6,RGATE_Subject7};

		String[] details= {RGATE_UNotes1,"","","",""};

		String[] date= {RGATE_AdvanceStartDate1,RGATE_AdvanceStartDate3,RGATE_AdvanceStartDate5,RGATE_AdvanceStartDate6,RGATE_AdvanceStartDate7};
		String[][] participant= {bp.getParticipantDataRG(RGATE_Participant1),bp.getParticipantDataRG(RGATE_Participant3),bp.getParticipantDataRG(RGATE_Participant5),bp.getParticipantDataRG(RGATE_Participant6),bp.getParticipantDataRG(RGATE_Participant7)};
		

		String[] icon1= {RGATE_ActivityType1,RGATE_ActivityType5,RGATE_ActivityType7};

		String[] subjectName1= {RGATE_Subject1,RGATE_Subject5,RGATE_Subject7};

		String[] date1= {RGATE_AdvanceStartDate1,RGATE_AdvanceStartDate5,RGATE_AdvanceStartDate7};

		String[] details1= {RGATE_UNotes1,"",""};
		String[][] participant1= {bp.getParticipantDataRG(RGATE_Participant1),bp.getParticipantDataRG(RGATE_Participant5),bp.getParticipantDataRG(RGATE_Participant7)};
		


		String[] icon2= {RGATE_ActivityType1,RGATE_ActivityType7};

		String[] subjectName2= {RGATE_Subject1,RGATE_Subject7};

		String[] date2= {RGATE_AdvanceStartDate1,RGATE_AdvanceStartDate7};

		String[] details2= {RGATE_UNotes1,""};

		String[][] participant2= {bp.getParticipantDataRG(RGATE_Participant1),bp.getParticipantDataRG(RGATE_Participant7)};
		

		lp.CRMLogin(RGcrmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ThreadSleep(5000);					

					if(CommonLib.clickUsingJavaScript(driver, bp.getMeetingAndCallCount(RGATE_ContactSectionName1, 20),"Count of "+RGATE_ContactSectionName1+" on contact section" , action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on count of "+RGATE_ContactSectionName1,YesNo.No);
						ArrayList<String> result4=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(icon, date, subjectName, details, participant, null);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, "Records have been verified on meeting and call popup for record "+RGATE_ContactSectionName1+". ",YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Records are not verifid on meeting and call popup for record "+RGATE_ContactSectionName1+". " +result4,YesNo.No);
							sa.assertTrue(false, "Records are not verifid on meeting and call popup"+RGATE_ContactSectionName1+". " +result4);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on count of "+RGATE_ContactSectionName1,YesNo.No);
						sa.assertTrue(false,  "Not able to click on count of "+RGATE_ContactSectionName1);
					}		

					if (CommonLib.clickUsingJavaScript(driver, bp.contactNameUserIconButton(contactName, 30), "Contact Name: " + contactName,
							action.SCROLLANDBOOLEAN)) {
						String parentId=switchOnWindow(driver);
						log(LogStatus.INFO, "Clicked on connection icon of contact : " + contactName, YesNo.No);

						if(CommonLib.clickUsingJavaScript(driver, bp.getMeetingAndCallCount(userName1, 20),"Count of "+userName1+" on contact section" , action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "clicked on count of "+userName1,YesNo.No);
							ArrayList<String> result4=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(icon1, date1, subjectName1, details1, participant1, null);
							if(result4.isEmpty())
							{
								log(LogStatus.INFO, "Records have been verified on meeting and call popup. user name : "+userName1+".",YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Records are not verifid on meeting and call popup for user "+userName1+". " +result4,YesNo.No);
								sa.assertTrue(false, "Records are not verifid on meeting and call popup for user "+userName1+". " +result4);
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on count of "+userName1,YesNo.No);
							sa.assertTrue(false,  "Not able to click on count of "+userName1);
						}		
						ThreadSleep(3000);

						if(CommonLib.clickUsingJavaScript(driver,  bp.getMeetingAndCallCount(userName2, 20),"Count of "+userName2+" on contact section" , action.SCROLLANDBOOLEAN))
						{
							ThreadSleep(2000);
							log(LogStatus.INFO, "clicked on count of "+userName2,YesNo.No);
							ArrayList<String> result5=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(icon2, date2, subjectName2, details2, participant2, null);
							if(result5.isEmpty())
							{
								log(LogStatus.INFO, "Records have been verified on meeting and call popup. user name : "+userName2+".",YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Records are not verifid on meeting and call popup for user "+userName2+". " +result5,YesNo.No);
								sa.assertTrue(false, "Records are not verifid on meeting and call popup for user "+userName2+". " +result5);
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on count of "+userName2,YesNo.No);
							sa.assertTrue(false,  "Not able to click on count of "+userName2);
						}

						driver.close();
						driver.switchTo().window(parentId);

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on connection icon of contact : " + contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on connection icon of contact : " + contactName);
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc008_GoToContactAcuityTabAndVerifyRevenueEventOnInteractionsAndContactAccountInCompanyReferenceAndContactInPeopleTaggedSection(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=RGATE_ContactFullName1.split("<break>")[0];
		String recordName1=RGATE_FirmLegalName1.split("<break>")[1]; 

		String[] companiesTaggedName= {RGATE_TaggedCompany1,RGATE_TaggedCompany2,RGATE_TaggedCompany3};
		String[] companiesTaggedTimeReference= {RGATE_TaggedCompanyReferenceCount1,RGATE_TaggedCompanyReferenceCount2,RGATE_TaggedCompanyReferenceCount3};

		String[] peopleTagedName={RGATE_TaggedPeople1,RGATE_TaggedPeople2,RGATE_TaggedPeople3};
		String[] peopleTaggedTimeReference={RGATE_TaggedPeopleReferenceCount1,RGATE_TaggedPeopleReferenceCount2,RGATE_TaggedPeopleReferenceCount2};

		String companyTagName=RGATE_TaggedCompany1;
		String companyTagTimeReferenceCount=RGATE_TaggedCompanyReferenceCount1;

		String peopleTagName=RGATE_TaggedPeople1;
		String peopleTagTimeReferenceCount=RGATE_TaggedPeopleReferenceCount1;

		String contactSectionName[]= {RGATE_ContactSectionName3};
		String contactSectionTitle[]= {null};
		String contactSectionDeal[]= {RGATE_ContactSectionNameDeals3};
		String contactSectionMeetingAndCall[]= {RGATE_ContactSectionNameMeetingAndCall3};
		String contactSectionEmail[]= {RGATE_ContactSectionEmail3};


		String[] iconType= {RGATE_ActivityType1,RGATE_ActivityType3,RGATE_ActivityType5,RGATE_ActivityType6,RGATE_ActivityType7};

		String[] subjectName= {RGATE_Subject1,RGATE_Subject3,RGATE_Subject5,RGATE_Subject6,RGATE_Subject7};

		String[] details= {RGATE_UNotes1,"","","",""};

		String[] date= {RGATE_AdvanceStartDate1,RGATE_AdvanceStartDate3,RGATE_AdvanceStartDate5,RGATE_AdvanceStartDate6,RGATE_AdvanceStartDate7};
		String[][] participant= {bp.getParticipantDataRG(RGATE_Participant1),bp.getParticipantDataRG(RGATE_Participant3),bp.getParticipantDataRG(RGATE_Participant5),bp.getParticipantDataRG(RGATE_Participant6),bp.getParticipantDataRG(RGATE_Participant7)};
		
		String[] subjectName1= {RGATE_Subject3,RGATE_Subject5,RGATE_Subject6,RGATE_Subject7};
		String[] notes1= {RGATE_Notes3,RGATE_Notes5,RGATE_Notes6,RGATE_Notes7};
		
		lp.CRMLogin(RGcrmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagedName, peopleTaggedTimeReference, null, null, isInstitutionRecord,null,null);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}


					if (click(driver, bp.getTaggedRecordName("Firms", 30), "Firms tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Firms tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Firms", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on firm Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);

							ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(companyTagName,iconType, date, subjectName, details, subjectName, participant, null);
							if(result2.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+companyTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+companyTagName+" record " +result2, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+companyTagName+" record "+result2);
							}

						
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}


						if (click(driver, bp.getTaggedRecordTimeReference("Firms", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);


							ArrayList<String> result5=bp.verifyDescriptionShouldNotVisibleUnderDetailsOnInteractionSection(subjectName1, notes1);
							if(result5.isEmpty())
							{
								log(LogStatus.INFO, "Description message is not showing under details on view all interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Description message is showing under details on view all interaction popup. "+result5, YesNo.No);
								sa.assertTrue(false,  "Description message is showing under details on view all interaction popup. "+result5);
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Firms tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Firms tab name");
					}


					if (click(driver, bp.getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("People", peopleTagName, peopleTagTimeReferenceCount,30), peopleTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+peopleTagName,YesNo.No);

							ArrayList<String> result3=bp.verifyRecordsonInteractionsViewAllPopup(peopleTagName,iconType, date, subjectName, details, subjectName, participant, null);
							if(result3.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+peopleTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+peopleTagName+" record " +result3, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+peopleTagName+" record "+result3);
							}
				
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+peopleTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+peopleTagName);
						}

						if (click(driver, bp.getTaggedRecordTimeReference("People", peopleTagName, peopleTagTimeReferenceCount,30), peopleTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+peopleTagName,YesNo.No);

							ArrayList<String> result6=bp.verifyDescriptionShouldNotVisibleUnderDetailsOnInteractionSection(subjectName1, notes1);
							if(result6.isEmpty())
							{
								log(LogStatus.INFO, "Description message is not showing under details on view all interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Description message is showing under details on view all interaction popup. "+result6, YesNo.No);
								sa.assertTrue(false,  "Description message is showing under details on view all interaction popup. "+result6);
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+peopleTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+peopleTagName);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on People tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on People tab name");
					}



					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(recordName,iconType,date, subjectName, details, subjectName, participant, null);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result2);
						}

						
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}	


					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result7=bp.verifyDescriptionShouldNotVisibleUnderDetailsOnInteractionSection(subjectName1, notes1);
						if(result7.isEmpty())
						{
							log(LogStatus.INFO, "Description message is not showing under details on view all interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Description message is showing under details on view all interaction popup. "+result7, YesNo.No);
							sa.assertTrue(false,  "Description message is showing under details on view all interaction popup. "+result7);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}	

					if (lp.clickOnTab(projectName, tabObj1)) {

						log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
						if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
								recordName1, 30)) {
							log(LogStatus.INFO, recordName1 + " reocrd has been open", YesNo.No);

							if (bp.clicktabOnPage(TabName.Acuity.toString())) {
								log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

								ArrayList<String> result2=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCall, contactSectionEmail);
								if(result2.isEmpty())
								{
									log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact", YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+result2, YesNo.No);
									sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact :  "+result2);
								}	
							}		
							else
							{
								log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
								sa.assertTrue(false,  "Not able to click on Acuity tab");
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to open record "+recordName1, YesNo.No);
							sa.assertTrue(false,  "Not able to open record "+recordName1);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
						sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
					}					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	
	@Parameters({ "projectName" })
	@Test
	public void RGATETc009_VerifyRevenueEventsInSalesforce(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer hp=new HomePageBusineesLayer(driver);

		String recordName=RGATE_ContactFullName1.split("<break>")[0];

		String globalEventSubjectName=RGATE_Subject2;
		String globalActivityName=RGATE_ActivityType2+"s";

		String globalEventSubjectName1=RGATE_Subject4;
		String globalActivityName1=RGATE_ActivityType4+"s";


		lp.CRMLogin(RGcrmUser1EmailID, adminPassword, appName);

		if(hp.globalSearchAndNavigate(globalEventSubjectName,globalActivityName, true))
		{
			log(LogStatus.INFO, globalEventSubjectName+" is not available in the org", YesNo.No);
		}
		else
		{
			log(LogStatus.ERROR, globalEventSubjectName+" is available in the org", YesNo.No);
			sa.assertTrue(false,  globalEventSubjectName+" is available in the org");
		}
		CommonLib.refresh(driver);
		ThreadSleep(4000);

		if(hp.globalSearchAndNavigate(globalEventSubjectName1,globalActivityName1, true))
		{
			log(LogStatus.INFO, globalEventSubjectName1+" is not available in the org", YesNo.No);
		}
		else
		{
			log(LogStatus.ERROR, globalEventSubjectName1+" is available in the org", YesNo.No);
			sa.assertTrue(false,  globalEventSubjectName1+" is available in the org");
		}

		CommonLib.refresh(driver);
		ThreadSleep(4000);
		String[] participant=bp.getParticipantDataRG(RGATE_Participant1);
		String relatedTo=null;
		for(int i=0; i<participant.length; i++)
		{
			if(i<participant.length-1)
			{
				relatedTo=participant[i]+"<break>";
			}
			else
			{
				relatedTo=participant[i];
			}
		}

		String eventDueDate=RGATE_AdvanceStartDate1;
		String eventSubjectName=RGATE_Subject1;
		String eventNotes=RGATE_UNotes1;
		String[] eventRelatedTo=RGATE_InteractionRelatedT01.split("<break>");	

		String[] relatedAssocVal=RGATE_InteractionRelatedAssoc01.split("<break>");
		String[] eventRelatedAssociation=new String[relatedAssocVal.length];

		String updateEventNotes=RGATE_UNotes1;

		String[][] basicsection = { { "Subject", RGATE_Subject1 },{"Notes",updateEventNotes}, { "Related_To", relatedTo } };
		String[][] advanceSection = { { "Date", eventDueDate }};

		String rgUser = RGEventUserFirstName + " " + RGEventUserLastName;
		String userName1 = RGcrmUser1FirstName + " " + RGcrmUser1LastName;
		String userName2 = RGcrmUser2FirstName + " " + RGcrmUser2LastName;
		String userName3 = RGcrmUser3FirstName + " " + RGcrmUser3LastName;
		
		
		String connectionUserName=userName1;
		String connectionMeetingaAndCall=RGATE_ConnectionSectionNameMeetingAndCall3;
		
		String connectionUserName1=userName2;
		String connectionMeetingaAndCall1=RGATE_ConnectionSectionNameMeetingAndCall4;
		
		String connectionUserName2=userName3;
		String connectionMeetingaAndCall2=RGATE_ConnectionSectionNameMeetingAndCall5;
		
		String connectionUserName3=rgUser;
		String connectionMeetingaAndCall3=RGATE_ConnectionSectionNameMeetingAndCall6;
	
		String eventSubject=RGATE_Subject1;

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ArrayList<String> result1=bp.verifyRecordOnInteractionCard(eventDueDate, IconType.Meeting, eventSubject, eventNotes, false,true, eventRelatedTo, eventRelatedAssociation);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, eventSubject+" Event has been verified on interaction section", YesNo.No);	
					}
					else
					{
						log(LogStatus.ERROR, eventSubject+" Event is not verified on interaction section. "+result1, YesNo.No);	
						sa.assertTrue(false, eventSubject+" Event is not verified on interaction section. "+result1);
					}
					
					ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, eventSubjectName, basicsection, advanceSection, IconType.Event, PageName.AcuityDetails);

					if(result2.isEmpty())
					{
						log(LogStatus.PASS,"The details on popup of subject "+eventSubjectName+" has been verified",YesNo.No);
						sa.assertTrue(true,"The details on popup of subject "+eventSubjectName+" has been verified");							
					}
					else
					{
						log(LogStatus.FAIL,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2,YesNo.No);
						sa.assertTrue(false,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2);

					}
					
					String currentUrl = getURL(driver, 10);
					refresh(driver);

					if(CommonLib.clickUsingJavaScript(driver, bp.addButtonOnInteractionCard(eventSubjectName,20), "Edit button of "+eventSubjectName))
					{
						log(LogStatus.INFO,"Clicked on Edit button on interaction section of subject name "+eventSubjectName,YesNo.No);
						ArrayList<String> NotesPopUpPrefilledNegativeResult = bp.verifyNotesPopupWithPrefilledValueAndOnSameUrl(currentUrl, basicsection,advanceSection, null);
						if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
							log(LogStatus.INFO,"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",YesNo.No);
						} else {
							log(LogStatus.ERROR,"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "+ NotesPopUpPrefilledNegativeResult,YesNo.No);
							sa.assertTrue(false,"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "+ NotesPopUpPrefilledNegativeResult);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Edit button on interaction section of subject name "+eventSubjectName,YesNo.No);
						sa.assertTrue(false, "Not able to click on Edit button on interaction section of subject name "+eventSubjectName);
					}
					refresh(driver);
					
					
					ArrayList<String> result6=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionUserName, null, null, connectionMeetingaAndCall, null);
					if(result6.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity user : "+connectionUserName, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity. user : "+connectionUserName+" "+result6, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity.  user : "+connectionUserName+" "+result6);
					}
					
					ArrayList<String> result7=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionUserName1, null, null, connectionMeetingaAndCall1, null);
					if(result7.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity user : "+connectionUserName1, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity. user : "+connectionUserName1+" "+result7, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity.  user : "+connectionUserName1+" "+result7);
					}
					
					ArrayList<String> result8=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionUserName2, null, null, connectionMeetingaAndCall2, null);
					if(result8.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity user : "+connectionUserName2, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity. user : "+connectionUserName2+" "+result8, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity.  user : "+connectionUserName2+" "+result8);
					}
					
					ArrayList<String> result9=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionUserName3, null, null, connectionMeetingaAndCall3, null);
					if(result9.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity user : "+connectionUserName3, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity. user : "+connectionUserName3+" "+result8, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity.  user : "+connectionUserName3+" "+result8);
					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	
    public void RGATETc010_UpdateTheInviteeInRevenueEventsFromOutlook(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);

		String username1=RGcrmUser1EmailID;
		String username2=RGcrmUser2EmailID;
		String username3=RGcrmUser3EmailID;
		String username4=RGcrmUser4EmailID;
		String username5=RGcrmUser5EmailID;		

		String eventTitle = RGATE_Subject1;		
		String [] user=RGATE_URelatedTo1.split("<b>");
		String eventAttendees=null;

		if(user[0].equalsIgnoreCase("user 1"))
		{
			eventAttendees=username1;
		}
		else if(user[0].equalsIgnoreCase("user 2"))
		{
			eventAttendees=username2;
		}
		else if(user[0].equalsIgnoreCase("user 3"))
		{
			eventAttendees=username3;
		}
		else if(user[0].equalsIgnoreCase("user 4"))
		{
			eventAttendees=username4;
		}
		else if(user[0].equalsIgnoreCase("user 5"))
		{
			eventAttendees=username5;
		}
		else
		{
			Assertion hardAssert = new Assertion();
			log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
			hardAssert.assertTrue(true == false);
		}	
		for(int i=1; i<user.length; i++)
		{
			if(user[i].equalsIgnoreCase("user 1"))
			{
				eventAttendees=eventAttendees+","+username1;
			}
			else if(user[i].equalsIgnoreCase("user 2"))
			{
				eventAttendees=eventAttendees+","+username2;
			}
			else if(user[i].equalsIgnoreCase("user 3"))
			{
				eventAttendees=eventAttendees+","+username3;
			}
			else if(user[i].equalsIgnoreCase("user 4"))
			{
				eventAttendees=eventAttendees+","+username4;
			}
			else if(user[i].equalsIgnoreCase("user 5"))
			{
				eventAttendees=eventAttendees+","+username5;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}

		}
		String startDate =RGATE_AdvanceStartDate1;		
		String endDate = CommonLib.convertDateFromOneFormatToAnother(
				RGATE_AdvanceEndDate1, "M/d/yyyy", "MMMM yyyy");

		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);
		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginNavigateAndUpdateTheEvent(rgOutLookUser1Email, rgOutLookUser1Password, startDate,endDate, eventTitle,false,null, eventAttendees,null, null, null, null, null,false, null)) {
			log(LogStatus.INFO,
					"-----Event updated Msg is showing, So Event of Title: " + eventTitle + " has been updated-----",
					YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "-----Event updated Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been updated-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been updated-----");
		}


		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test

	public void RGATETc011_VerifyUpdatedEventOnContactObject(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=RGATE_ContactFullName1.split("<break>")[0];
	
		String eventNotes=RGATE_UNotes1;
	
		String eventSubject=RGATE_Subject1;
		String[] participant=bp.getParticipantDataRG(RGATE_Participant1);
		String relatedTo=null;
		for(int i=0; i<participant.length; i++)
		{
			if(i<participant.length-1)
			{
			relatedTo=participant[i]+"<break>";
			}
			else
			{
				relatedTo=participant[i];
			}
		}

		String eventDueDate=RGATE_AdvanceStartDate1;
		String eventSubjectName=RGATE_Subject1;
		String updateEventNotes=RGATE_UNotes1;
		String[][] basicsection = { { "Subject", RGATE_Subject1 },{"Notes",updateEventNotes}, { "Related_To", relatedTo } };
		String[][] advanceSection = { { "Date", eventDueDate }};
		
		String userName1 = RGcrmUser4FirstName + " " + RGcrmUser4LastName;
		String userName2 = RGcrmUser5FirstName + " " + RGcrmUser5LastName;
		
		
		String connectionUserName=userName1;
		String connectionMeetingaAndCall=RGATE_ConnectionSectionNameMeetingAndCall1;
		
		String connectionUserName1=userName2;
		String connectionMeetingaAndCall1=RGATE_ConnectionSectionNameMeetingAndCall2;
			
		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ArrayList<String> result1=bp.verifyRecordOnInteractionCard(eventDueDate, IconType.Meeting, eventSubject, eventNotes, false,true, null, null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, eventSubject+" Event has been verified on interaction section", YesNo.No);	
					}
					else
					{
						log(LogStatus.ERROR, eventSubject+" Event is not verified on interaction section. "+result1, YesNo.No);	
						sa.assertTrue(false, eventSubject+" Event is not verified on interaction section. "+result1);
					}
					
					ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, eventSubjectName, basicsection, advanceSection, IconType.Event, PageName.AcuityDetails);

					if(result2.isEmpty())
					{
						log(LogStatus.PASS,"The details on popup of subject "+eventSubjectName+" has been verified",YesNo.No);
						sa.assertTrue(true,"The details on popup of subject "+eventSubjectName+" has been verified");							
					}
					else
					{
						log(LogStatus.FAIL,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2,YesNo.No);
						sa.assertTrue(false,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2);

					}
					
					String currentUrl = getURL(driver, 10);
					refresh(driver);

					if(CommonLib.clickUsingJavaScript(driver, bp.addButtonOnInteractionCard(eventSubjectName,20), "Edit button of "+eventSubjectName))
					{
						log(LogStatus.INFO,"Clicked on Edit button on interaction section of subject name "+eventSubjectName,YesNo.No);
						ArrayList<String> NotesPopUpPrefilledNegativeResult = bp.verifyNotesPopupWithPrefilledValueAndOnSameUrl(currentUrl, basicsection,advanceSection, null);
						if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
							log(LogStatus.INFO,"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",YesNo.No);
						} else {
							log(LogStatus.ERROR,"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "+ NotesPopUpPrefilledNegativeResult,YesNo.No);
							sa.assertTrue(false,"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "+ NotesPopUpPrefilledNegativeResult);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Edit button on interaction section of subject name "+eventSubjectName,YesNo.No);
						sa.assertTrue(false, "Not able to click on Edit button on interaction section of subject name "+eventSubjectName);
					}
					refresh(driver);
					
					ArrayList<String> result6=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionUserName, null, null, connectionMeetingaAndCall, null);
					if(result6.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity user : "+connectionUserName, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity. user : "+connectionUserName+" "+result6, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity.  user : "+connectionUserName+" "+result6);
					}
					
					ArrayList<String> result7=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionUserName1, null, null, connectionMeetingaAndCall1, null);
					if(result7.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity user : "+connectionUserName1, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity. user : "+connectionUserName1+" "+result7, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity.  user : "+connectionUserName1+" "+result7);
					}
					
					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc012_VerifyUpdatedEventOnFirmRecordPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=RGATE_FirmLegalName1.split("<break>")[0];
		String contactName=RGATE_ContactSectionName1;
		String eventNotes=RGATE_UNotes1;
		
		String eventSubject=RGATE_Subject1;
		String[] participant=bp.getParticipantDataRG(RGATE_Participant1);
		String relatedTo=null;
		for(int i=0; i<participant.length; i++)
		{
			if(i<participant.length-1)
			{
			relatedTo=participant[i]+"<break>";
			}
			else
			{
				relatedTo=participant[i];
			}
		}

		String eventDueDate=RGATE_AdvanceStartDate1;
		String eventSubjectName=RGATE_Subject1;
		String updateEventNotes=RGATE_UNotes1;
		String[][] basicsection = { { "Subject", RGATE_Subject1 },{"Notes",updateEventNotes}, { "Related_To", relatedTo } };
		String[][] advanceSection = { { "Date", eventDueDate }};
		
		String userName1 = RGcrmUser4FirstName + " " + RGcrmUser4LastName;
		String userName2 = RGcrmUser5FirstName + " " + RGcrmUser5LastName;
		
		
		String[] connectionUserName= {userName1,userName2};
		String[] connectionMeetingaAndCall= {RGATE_ConnectionSectionNameMeetingAndCall1,RGATE_ConnectionSectionNameMeetingAndCall2};
		
		
		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ArrayList<String> result1=bp.verifyRecordOnInteractionCard(eventDueDate, IconType.Meeting, eventSubject, eventNotes, false,true, null, null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, eventSubject+" Event has been verified on interaction section", YesNo.No);	
					}
					else
					{
						log(LogStatus.ERROR, eventSubject+" Event is not verified on interaction section. "+result1, YesNo.No);	
						sa.assertTrue(false, eventSubject+" Event is not verified on interaction section. "+result1);
					}
					
					ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, eventSubjectName, basicsection, advanceSection, IconType.Event, PageName.AcuityDetails);

					if(result2.isEmpty())
					{
						log(LogStatus.PASS,"The details on popup of subject "+eventSubjectName+" has been verified",YesNo.No);
						sa.assertTrue(true,"The details on popup of subject "+eventSubjectName+" has been verified");							
					}
					else
					{
						log(LogStatus.FAIL,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2,YesNo.No);
						sa.assertTrue(false,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2);

					}
					
					String currentUrl = getURL(driver, 10);
					refresh(driver);

					if(CommonLib.clickUsingJavaScript(driver, bp.addButtonOnInteractionCard(eventSubjectName,20), "Edit button of "+eventSubjectName))
					{
						log(LogStatus.INFO,"Clicked on Edit button on interaction section of subject name "+eventSubjectName,YesNo.No);
						ArrayList<String> NotesPopUpPrefilledNegativeResult = bp.verifyNotesPopupWithPrefilledValueAndOnSameUrl(currentUrl, basicsection,advanceSection, null);
						if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
							log(LogStatus.INFO,"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",YesNo.No);
						} else {
							log(LogStatus.ERROR,"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "+ NotesPopUpPrefilledNegativeResult,YesNo.No);
							sa.assertTrue(false,"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "+ NotesPopUpPrefilledNegativeResult);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Edit button on interaction section of subject name "+eventSubjectName,YesNo.No);
						sa.assertTrue(false, "Not able to click on Edit button on interaction section of subject name "+eventSubjectName);
					}
					refresh(driver);
					
					
					ArrayList<String> result7=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactName, connectionUserName, null, null, connectionMeetingaAndCall,null);
					if(result7.isEmpty())
					{
						log(LogStatus.INFO, "The records on Connection popup have been verified for "+contactName, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records on Connection popup are not verified for "+contactName+". "+result7 , YesNo.No);
						sa.assertTrue(false, "The records on Connection popup are not verified for "+contactName+". "+result7);
					}
					

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

		
	@Parameters({ "projectName" })
	@Test
	
    public void RGATETc013_UpdateTheDateInRevenueEventsFromOutlook(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);

		
		String eventTitle = RGATE_Subject1;		
	
		String startDate =RGATE_AdvanceStartDate1;		
		String endDate = CommonLib.convertDateFromOneFormatToAnother(
				RGATE_AdvanceEndDate1, "M/d/yyyy", "MMMM yyyy");
		
		
		String updatedEventStartDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_UStartDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, updatedEventStartDate, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_U001", excelLabel.Advance_Start_Date);

		String updatedEventEndDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_UEndDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, updatedEventEndDate, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_U001", excelLabel.Advance_End_Date);


		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);
		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginNavigateAndUpdateTheEvent(rgOutLookUser1Email, rgOutLookUser1Password, startDate,endDate, eventTitle,false,null, null,updatedEventStartDate, updatedEventEndDate, null, null, null,false, null)) {
			log(LogStatus.INFO,
					"-----Event updated Msg is showing, So Event of Title: " + eventTitle + " has been updated-----",
					YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "-----Event updated Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been updated-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been updated-----");
		}


		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test

	public void RGATETc014_VerifyUpdatedDateEventOnContactObject(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=RGATE_ContactFullName1.split("<break>")[0];
	
		String eventNotes=RGATE_UNotes1;
		String eventSubject=RGATE_Subject1;
		String eventDueDate=RGATE_UStartDate1;

		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ArrayList<String> result1=bp.verifyRecordOnInteractionCard(eventDueDate, IconType.Meeting, eventSubject, eventNotes, true,false, null, null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, eventSubject+" Event has been verified on interaction section", YesNo.No);	
					}
					else
					{
						log(LogStatus.ERROR, eventSubject+" Event is not verified on interaction section. "+result1, YesNo.No);	
						sa.assertTrue(false, eventSubject+" Event is not verified on interaction section. "+result1);
					}
					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName" })
	@Test
	public void RGATETc015_VerifyUpdatedDateEventOnFirmRecordPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=RGATE_FirmLegalName1.split("<break>")[0];

		String eventNotes=RGATE_UNotes1;
		String eventSubject=RGATE_Subject1;
		String eventDueDate=RGATE_UStartDate1;
		
		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ArrayList<String> result1=bp.verifyRecordOnInteractionCard(eventDueDate, IconType.Meeting, eventSubject, eventNotes, true,false, null, null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, eventSubject+" Event has been verified on interaction section", YesNo.No);	
					}
					else
					{
						log(LogStatus.ERROR, eventSubject+" Event is not verified on interaction section. "+result1, YesNo.No);	
						sa.assertTrue(false, eventSubject+" Event is not verified on interaction section. "+result1);
					}				
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName" })
	@Test
	public void RGATETc016_VerifyViewAllLinkOnInteractionSectionOfContactPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=RGATE_ContactFullName1.split("<break>")[0];

		String[] iconType= {RGATE_ActivityType1};

		String[] subjectName= {RGATE_Subject1};

		String[] details= {RGATE_UNotes1};

		String[] date= {RGATE_UStartDate1};
		
		String[][] participant= {bp.getParticipantDataRG(RGATE_UParticipant1)};

		
		String[] participants=bp.getParticipantDataRG(RGATE_UParticipant1);
		String relatedTo=null;
		for(int i=0; i<participants.length; i++)
		{
			if(i<participants.length-1)
			{
			relatedTo=participants[i]+"<break>";
			}
			else
			{
				relatedTo=participants[i];
			}
		}

		String eventDueDate=RGATE_UStartDate1;
		String eventSubjectName=RGATE_Subject1;
		String eventNotes=RGATE_UNotes1;
		
		String[][] basicsection = { { "Subject", eventSubjectName }, {"Notes",eventNotes},{ "Related_To", relatedTo } };
		String[][] advanceSection = { { "Date", eventDueDate }};
	
		lp.CRMLogin(RGcrmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result=bp.verifyRecordsonInteractionsViewAllPopup(recordName,iconType,date, subjectName, details, subjectName, participant, null);
						if(result.isEmpty())
						{
							log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result, YesNo.No);
							sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result);
						}		
						
						ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, eventSubjectName, basicsection, advanceSection, IconType.Event, PageName.Interaction);

						if(result2.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+eventSubjectName+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+eventSubjectName+" has been verified");							
						}
						else
						{
							log(LogStatus.FAIL,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2,YesNo.No);
							sa.assertTrue(false,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2);

						}
						
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}	


					if(bp.verifySubjectLinkPopUpOnIntraction(driver, RGATE_Subject1))
					{
						log(LogStatus.INFO,RGATE_Subject1+ " event is opeing in the new tab", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,RGATE_Subject1+ " event is not opening in the new tab", YesNo.No);
						sa.assertTrue(false,RGATE_Subject1+ " event is not opening in the new tab" );
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc017_VerifyUpdatedDateEventOnFirmRecordPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=RGATE_FirmLegalName1.split("<break>")[0];

		String[] iconType= {RGATE_ActivityType1};

		String[] subjectName= {RGATE_Subject1};

		String[] details= {RGATE_UNotes1};

		String[] date= {RGATE_UStartDate1};
		
		String[][] participant= {bp.getParticipantDataRG(RGATE_UParticipant1)};

		
		String[] participants=bp.getParticipantDataRG(RGATE_UParticipant1);
		String relatedTo=null;
		for(int i=0; i<participants.length; i++)
		{
			if(i<participants.length-1)
			{
			relatedTo=participants[i]+"<break>";
			}
			else
			{
				relatedTo=participants[i];
			}
		}

		String eventDueDate=RGATE_UStartDate1;
		String eventSubjectName=RGATE_Subject1;
		String eventNotes=RGATE_UNotes1;
		
		String[][] basicsection = { { "Subject", eventSubjectName }, {"Notes",eventNotes},{ "Related_To", relatedTo } };
		String[][] advanceSection = { { "Date", eventDueDate }};
	
		
		lp.CRMLogin(RGcrmUser1EmailID, adminPassword);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
				{
					log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
					ArrayList<String> result=bp.verifyRecordsonInteractionsViewAllPopup(recordName,iconType,date, subjectName, details, subjectName, participant, null);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result);
					}		
					
					ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, eventSubjectName, basicsection, advanceSection, IconType.Event, PageName.Interaction);

					if(result2.isEmpty())
					{
						log(LogStatus.PASS,"The details on popup of subject "+eventSubjectName+" has been verified",YesNo.No);
						sa.assertTrue(true,"The details on popup of subject "+eventSubjectName+" has been verified");							
					}
					else
					{
						log(LogStatus.FAIL,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2,YesNo.No);
						sa.assertTrue(false,"The details on popup of subject "+eventSubjectName+" are not verified. "+result2);

					}
					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
					sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
				}	


				if(bp.verifySubjectLinkPopUpOnIntraction(driver, RGATE_Subject1))
				{
					log(LogStatus.INFO,RGATE_Subject1+ " event is opeing in the new tab", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR,RGATE_Subject1+ " event is not opening in the new tab", YesNo.No);
					sa.assertTrue(false,RGATE_Subject1+ " event is not opening in the new tab" );
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName" })
	@Test
	public void RGATETc018_CreateAccountDealsAndFunds(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundsPageBusinessLayer fd = new FundsPageBusinessLayer(driver);

		String[] accountName = RGATE_FirmLegalName3.split("<break>");
		String[] recordType = RGATE_FirmRecordType2.split("<break>");

		String[] contactFirstName = {RGATE_ContactFirstName2};
		String[] contactLastName = {RGATE_ContactLastName2};
		String[] contactLegalName = {RGATE_ContactLegalName2};
		String[] contactEmail = {RGATE_ContactEmail2};

		String dealName = RGATE_DealName2;
		String dealCompany = RGATE_DealCompany2;
		String dealStage = RGATE_DealStage2;

		String fundName = RGATE_FundName2;
		String fundType = RGATE_FundType2;
		String fundInvestmentCategory = RGATE_FundInvestmentCategory2;

		lp.CRMLogin(RGcrmUser1EmailID, adminPassword, appName);
		if (accountName.length == recordType.length) {
			for (int i = 0; i < accountName.length; i++) {
				if (lp.clickOnTab(projectName, tabObj1)) {

					log(LogStatus.INFO, "Click on Tab : " + tabObj1, YesNo.No);
					ThreadSleep(3000);
					if (ip.createEntityOrAccount(environment, mode, accountName[i], recordType[i], null, null, 30)) {
						log(LogStatus.INFO,
								"successfully Created Firm : " + accountName[i] + " of record type : " + recordType[i],
								YesNo.No);
						sa.assertTrue(true,
								"successfully Created Firm : " + accountName[i] + " of record type : " + recordType[i]);


					} else {
						sa.assertTrue(false,
								"Not Able to Create Firm : " + accountName[i] + " of record type : " + recordType[i]);
						log(LogStatus.SKIP,
								"Not Able to Create Firm : " + accountName[i] + " of record type :" + recordType[i],
								YesNo.Yes);
					}

				} else {
					log(LogStatus.FAIL, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
				}

			}
		} else {
			log(LogStatus.FAIL,
					"The count of Legal name and Record Type are not equal. Either Legal Name or Record type value are not proper",
					YesNo.No);
			sa.assertTrue(false,
					"The count of Legal name and Record Type are not equal. Either Legal Name or Record type value are not proper");
		}

		for (int i = 0; i < contactLastName.length; i++) {
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Click on Tab : " + tabObj2, YesNo.No);
				ThreadSleep(3000);

				if (cp.createContact(projectName, contactFirstName[i], contactLastName[i], contactLegalName[i],
						contactEmail[i], "", null, null, CreationPage.ContactPage, null, null)) {
					log(LogStatus.INFO,
							"successfully Created Contact : " + contactFirstName[i] + " " + contactLastName[i],
							YesNo.No);
					sa.assertTrue(true,
							"successfully Created Contact : " + contactFirstName[i] + " " + contactLastName[i]);


				} else {
					log(LogStatus.FAIL,
							"Not able to create the Contact : " + contactFirstName[i] + " " + contactLastName[i],
							YesNo.No);
					sa.assertTrue(false,
							"Not able to create the Contact : " + contactFirstName[i] + " " + contactLastName[i]);
				}

			} else {
				log(LogStatus.FAIL, "Not able to click on " + tabObj2 + " Tab", YesNo.No);
				sa.assertTrue(false, "Not able to click on " + tabObj2 + " Tab");
			}
		}

		if (lp.clickOnTab(projectName, tabObj4)) {

			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);

			if (dp.createDeal(projectName, dealName, dealCompany, dealStage)) {
				log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);
				sa.assertTrue(true, dealName + " deal has been created");

			} else {
				log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
				sa.assertTrue(false, dealName + " deal is not created");
			}
		}
		else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		}


		if (lp.clickOnTab(projectName, tabObj3)) {

			log(LogStatus.INFO, "Click on Tab : " + tabObj3, YesNo.No);
			ThreadSleep(3000);

			if (fd.createFund(projectName, fundName, fundType, fundInvestmentCategory, null, null)) {
				log(LogStatus.INFO, fundName + " Fund has been created", YesNo.No);
				sa.assertTrue(true, fundName + " Fund has been created");

			} else {
				log(LogStatus.ERROR, fundName + " Fund is not created", YesNo.No);
				sa.assertTrue(false, fundName + " Fund is not created");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj3 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj3 + " Tab");
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc019_CreateEmailTaskFromRGUser(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	


		String[] to= RGATE_EmailTo1.split("<break>");
		String [] cc = {RGATE_EmailCC1};
		String subject = RGATE_EmailSubject1;
		String message=RGATE_EmailDescription1;
		
		String[] linkedRecord=RGATE_EmailLinkedRecord1.split("<break>");
		op.outLookLogin(rgOutLookUser1Email, rgOutLookUser1Password);
		if(op.sendMailFromRGOutlook(to, cc, null, subject, message, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+rgOutLookUser1Email+" to "+to, YesNo.No);

			if(op.saveEmailFromOutlook(subject, linkedRecord))
			{
				log(LogStatus.INFO, "The email have been saved from RG. subjectName "+subject, YesNo.No);
				op.outLookSignOut();
			}
			else
			{
				log(LogStatus.ERROR, "The email is not saved from RG. subjectName "+subject, YesNo.No);
				sa.assertTrue(false, "The email is not saved from RG. subjectName "+subject);
				op.outLookSignOut();
			}

		} else {
			log(LogStatus.ERROR, "Not able to send email from "+rgOutLookUser1Email+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+rgOutLookUser1Email+" to "+to);
		}

		String[] to1= RGATE_EmailTo2.split("<break>");
		String [] cc1 = {RGATE_EmailCC2};
		String subject1 = RGATE_EmailSubject2;
		String message1=RGATE_EmailDescription2;
		ThreadSleep(5000);
		op.outLookLogin(rgOutLookUser1Email, rgOutLookUser1Password);
		if(op.sendMailFromRGOutlook(to1, cc1, null, subject1, message1, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+rgOutLookUser1Email+" to "+to, YesNo.No);

			if(op.saveEmailFromOutlook(subject1, linkedRecord))
			{
				log(LogStatus.INFO, "The email have been saved from RG. subjectName "+subject1, YesNo.No);
				op.outLookSignOut();
			}
			else
			{
				log(LogStatus.ERROR, "The email is not saved from RG. subjectName "+subject1, YesNo.No);
				sa.assertTrue(false, "The email is not saved from RG. subjectName "+subject1);
				op.outLookSignOut();
			}
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+rgOutLookUser1Email+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+rgOutLookUser1Email+" to "+to);
		}

		ThreadSleep(5000);
		op.outLookLogin(rgOutLookUser1Email, rgOutLookUser1Password);
		String[] to2= RGATE_EmailTo3.split("<break>");
		String [] cc2 = {RGATE_EmailCC3};
		String subject2 = RGATE_EmailSubject3;
		String message2=RGATE_EmailDescription3;	

		if(op.sendMailFromRGOutlook(to2, cc2, null, subject2, message2, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+rgOutLookUser1Email+" to "+to, YesNo.No);

			if(op.saveEmailFromOutlook(subject2, linkedRecord))
			{
				log(LogStatus.INFO, "The email have been saved from RG. subjectName "+subject2, YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR, "The email is not saved from RG. subjectName "+subject2, YesNo.No);
				sa.assertTrue(false, "The email is not saved from RG. subjectName "+subject2);
			}


		} else {
			log(LogStatus.ERROR, "Not able to send email from "+rgOutLookUser1Email+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+rgOutLookUser1Email+" to "+to);
		}		
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc020_VerifyEmailTaskInTheFirmAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String xPath;
		WebElement ele;

		String recordName=RGATE_FirmLegalName1.split("<break>")[0];

		String[] companiesTaggedName= {RGATE_TaggedCompany5,RGATE_TaggedCompany6,RGATE_TaggedCompany7,RGATE_TaggedCompany8};
		String[] companiesTaggedTimeReference= {RGATE_TaggedCompanyReferenceCount5,RGATE_TaggedCompanyReferenceCount6,RGATE_TaggedCompanyReferenceCount7,RGATE_TaggedCompanyReferenceCount8};

		String[] peopleTagedName={RGATE_TaggedPeople4,RGATE_TaggedPeople5,RGATE_TaggedPeople6,RGATE_TaggedPeople7,RGATE_TaggedPeople8};
		String[] peopleTaggedTimeReference={RGATE_TaggedPeopleReferenceCount4,RGATE_TaggedPeopleReferenceCount5,RGATE_TaggedPeopleReferenceCount6,RGATE_TaggedPeopleReferenceCount7,RGATE_TaggedPeopleReferenceCount8};

		String[] iconType= {RGATE_EIcon1,RGATE_EIcon2,RGATE_EIcon3};

		String[] subjectName= {RGATE_ESubject1,RGATE_ESubject2,RGATE_ESubject3};

		String[] details= {"","",""};

		String[] date= {null,null,null};

		String[] userData= {RGEventUserLastName,RGEventUserLastName,RGEventUserLastName};;

		lp.CRMLogin(RGcrmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocord has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagedName, peopleTaggedTimeReference, null, null,isInstitutionRecord,null,null);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}


					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(recordName,iconType,date, subjectName, details, subjectName, null, null);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result2);
						}
						/*		
						xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}
						 */
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}	

	@Parameters({ "projectName" })
	@Test
	public void RGATETc021_VerifyEmailTaskInTheFirmAccountContactPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String xPath;
		WebElement ele;

		String recordName=RGATE_ContactFullName1.split("<break>")[0];

		String[] companiesTaggedName= {RGATE_TaggedCompany5,RGATE_TaggedCompany6,RGATE_TaggedCompany7,RGATE_TaggedCompany8};
		String[] companiesTaggedTimeReference= {RGATE_TaggedCompanyReferenceCount5,RGATE_TaggedCompanyReferenceCount6,RGATE_TaggedCompanyReferenceCount7,RGATE_TaggedCompanyReferenceCount8};

		String[] peopleTagedName={RGATE_TaggedPeople4,RGATE_TaggedPeople5,RGATE_TaggedPeople6,RGATE_TaggedPeople7};
		String[] peopleTaggedTimeReference={RGATE_TaggedPeopleReferenceCount4,RGATE_TaggedPeopleReferenceCount5,RGATE_TaggedPeopleReferenceCount6,RGATE_TaggedPeopleReferenceCount7};

		String[] iconType= {RGATE_EIcon1,RGATE_EIcon2,RGATE_EIcon3};

		String[] subjectName= {RGATE_ESubject1,RGATE_ESubject2,RGATE_ESubject3};

		String[] details= {"","",""};

		String[] date= {null,null,null};

		String[] userData= {RGEventUserLastName,RGEventUserLastName,RGEventUserLastName};;

		lp.CRMLogin(RGcrmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagedName, peopleTaggedTimeReference, null, null, isInstitutionRecord,null,null);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}


					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(recordName,iconType,date, subjectName, details, subjectName, null, null);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result2);
						}
						/*	
						xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}
						 */
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}	

	@Parameters({ "projectName" })
	@Test
	public void RGATETc022_CreateAccountDeals(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		
		String[] accountName = RGATE_FirmLegalName4.split("<break>");
		String[] recordType = RGATE_FirmRecordType3.split("<break>");

		String[] contactFirstName = RGATE_ContactFirstName3.split("<break>");
		String[] contactLastName = RGATE_ContactLastName3.split("<break>");
		String[] contactLegalName = RGATE_ContactLegalName3.split("<break>");
		String[] contactEmail = RGATE_ContactEmail3.split("<break>");
		String[] contactTitle = RGATE_Title3.split("<break>");
		String dealName = RGATE_DealName3;
		String dealCompany = RGATE_DealCompany3;
		String dealStage = RGATE_DealStage3;

			for (int i = 0; i < accountName.length; i++) {
				if (lp.clickOnTab(projectName, TabName.Object1Tab)) {

					log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
					ThreadSleep(3000);
					if (ip.createEntityOrAccount(environment, mode, accountName[i], recordType[i], null, null, 30)) {
						log(LogStatus.INFO,
								"successfully Created Firm : " + accountName[i] + " of record type : " + recordType[i],
								YesNo.No);
						sa.assertTrue(true,
								"successfully Created Firm : " + accountName[i] + " of record type : " + recordType[i]);


					} else {
						sa.assertTrue(false,
								"Not Able to Create Firm : " + accountName[i] + " of record type : " + recordType[i]);
						log(LogStatus.SKIP,
								"Not Able to Create Firm : " + accountName[i] + " of record type :" + recordType[i],
								YesNo.Yes);
					}

				} else {
					log(LogStatus.FAIL, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
				}
				refresh(driver);
			}
			ThreadSleep(2000);
		for (int i = 0; i < contactLastName.length; i++) {
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Click on Tab : " + tabObj2, YesNo.No);
				ThreadSleep(3000);

				if (cp.createContact(projectName, contactFirstName[i], contactLastName[i], contactLegalName[i],
						contactEmail[i], "", Fields.Title.toString(),contactTitle[i], CreationPage.ContactPage, null, null)) {
					log(LogStatus.INFO,
							"successfully Created Contact : " + contactFirstName[i] + " " + contactLastName[i],
							YesNo.No);
					sa.assertTrue(true,
							"successfully Created Contact : " + contactFirstName[i] + " " + contactLastName[i]);
				} else {
					log(LogStatus.FAIL,
							"Not able to create the Contact : " + contactFirstName[i] + " " + contactLastName[i],
							YesNo.No);
					sa.assertTrue(false,
							"Not able to create the Contact : " + contactFirstName[i] + " " + contactLastName[i]);
				}

			} else {
				log(LogStatus.FAIL, "Not able to click on " + tabObj2 + " Tab", YesNo.No);
				sa.assertTrue(false, "Not able to click on " + tabObj2 + " Tab");
			}
			refresh(driver);
		}
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Deals)) {

			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);

			if (dp.createDeal(projectName, dealName, dealCompany, dealStage)) {
				log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);
				sa.assertTrue(true, dealName + " deal has been created");

			} else {
				log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
				sa.assertTrue(false, dealName + " deal is not created");
			}
		}
		else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void RGATETc023_CreateDealTeamAndVerifyOnInternalAndExternalTabOfDeal(String projectName) {	
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		
		String[] TeamMember = ACDealTeamMember1.split("<break>");
		String[] contactName = ACDealContact1.split("<break>");
		String[] teamRole = ACDealTeamRole1.split("<break>");
		String dealName = ACDealName1;
		String[] accountName = RGATE_FirmLegalName4.split("<break>");
		String[] dealContact = ACDealContact1.split("<break>");
		String[] title = RGATEConnectionTitle9.split("<break>");
		String[] role = RGATEConnectionRole9.split("<break>");
		String[] dealCount = RGATEConnectionsDeal9.split("<break>");
		String[] meetCount = RGATEConnectionsCallCount9.split("<break>");
		String[] emailCount = RGATEConnectionsEmailCount9.split("<break>");
		String[] user = ACDealTeamMember1.split("<break>");
		String[] userTitle = ACDealTeamTitle1.split("<break>");
		String[] userRole = ACDealTeamRole1.split("<break>");
		
		log(LogStatus.INFO, TeamMember.length + " is length", YesNo.No);
		for (int i = 0; i < TeamMember.length; i++) {
		String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName[i] },
				{ PageLabel.Team_Member.toString(), TeamMember[i] }, { PageLabel.Role.toString(), teamRole[i] } };
		if (BP.openAppFromAppLauchner(10, "Deal Team")) {
			log(LogStatus.INFO, "Deal Team has been open from the app launcher", YesNo.No);

			if (DTP.createDealTeam(projectName, TeamMember[i], data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR, "could not create a new deal team", YesNo.Yes);
				sa.assertTrue(false,"could not create a new deal team" );
			}
		}
		else
		{
			log(LogStatus.ERROR, "could not open a deal team tab", YesNo.Yes);
			sa.assertTrue(false,"could not open a deal team tab" );
		}
		}
		
		ThreadSleep(2000);
		
		if (lp.clickOnTab(projectName, TabName.Deals)) {

			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(5000);
		
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.DealTab , dealName, 10)){
			log(LogStatus.INFO, "Click on deal : " + dealName, YesNo.No);
			
			for(int i=0; i<accountName.length;i++) {
				if(BP.ExternalTab(10, action.SCROLLANDBOOLEAN) != null) {
					log(LogStatus.PASS, "External Tab is present", YesNo.No);
					clickUsingJavaScript(driver, BP.ExternalTab(10,action.SCROLLANDBOOLEAN), "External Tab");
					log(LogStatus.PASS, "Clicked on External Tab", YesNo.No);
					ThreadSleep(2000);
					if (BP.dealTeamAcuityDealName(dealContact[i], 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealContact[i] + " is hyperlink and is present", YesNo.No);
						if (BP.dealTeamAcuityTitleName(dealContact[i], title[i], 30) != null) {
							log(LogStatus.PASS, "Title Name: " + title[i] + " is present", YesNo.No);
							if (BP.dealTeamAcuityAccountName(dealContact[i], accountName[i], 30) != null) {
								log(LogStatus.PASS, "Account Name : " + accountName + " is present", YesNo.No);
								if (BP.dealTeamAcuityRole(dealContact[i], role[i], 30) != null) {
									log(LogStatus.PASS, "HSR: " + role[i] + " is present", YesNo.No);								
									if (BP.dealTeamAcuityDeals(dealContact[i], dealCount[i], 30) != null) {
										log(LogStatus.INFO,"Deal Count : " + dealCount[i] + " is present", YesNo.No);
										if (BP.dealTeamAcuityMeetingsAndCalls(dealContact[i], meetCount[i], 30) != null) {
											log(LogStatus.INFO,"Meetings & Calls Count : " + meetCount[i] + " is present", YesNo.No);
											if (BP.dealTeamAcuityEmail(dealContact[i], emailCount[i], 30) != null) {
												log(LogStatus.INFO,"Email Count : " + emailCount[i] + " is present", YesNo.No);
											}
											else {
											log(LogStatus.ERROR, "Email Count is not matched with "+emailCount[i], YesNo.Yes);
												sa.assertTrue(false,"Email Count is not matched with "+emailCount[i] );
											}
										}
										else {
										log(LogStatus.ERROR, "Meetings & Calls Count is not matched with "+meetCount[i], YesNo.Yes);
											sa.assertTrue(false,"Meetings & Calls Count is not matched with "+meetCount[i] );
										}
									}
									else {
									log(LogStatus.ERROR, "Deal Count is not matched with "+dealCount[i], YesNo.Yes);
										sa.assertTrue(false,"Deal Count is not matched with "+dealCount[i] );
									}
								} else {
									log(LogStatus.ERROR, "Role name not present: " + role[i], YesNo.Yes);
									sa.assertTrue(false, "Role name not present: " + role[i]);
								}
							} else {
								log(LogStatus.ERROR, "Not able to Click on Account Name: " + accountName[i], YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Account Name: " + accountName[i]);

							}
							} else {
								log(LogStatus.ERROR, "Not able to Click on Title: " + title[i], YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Title: " + title[i]);
							}
						} else {
							log(LogStatus.ERROR, "Contact not present: " + dealContact[i], YesNo.Yes);
							sa.assertTrue(false, "Contact not present: " + dealContact[i]);
						}
					} else {
						log(LogStatus.ERROR, "External Tab not present.", YesNo.Yes);
						sa.assertTrue(false, "External Tab not present.");
					}	
				
				ThreadSleep(2000);
				
			if(BP.InternalTab(10, action.SCROLLANDBOOLEAN) != null) {
				log(LogStatus.PASS, "Internal Tab is present", YesNo.No);
				clickUsingJavaScript(driver, BP.InternalTab(10,action.SCROLLANDBOOLEAN), "Internal Tab");
				log(LogStatus.PASS, "Clicked on Internal Tab", YesNo.No);
				ThreadSleep(2000);
				if (BP.dealTeamAcuityUserName(user[i], 30) != null) {
					log(LogStatus.PASS, "User Name: " + user[i] + " is hyperlink and is present", YesNo.No);
					if (BP.dealTeamAcuityUserTitle(user[i], userTitle[i], 10) != null) {
						log(LogStatus.PASS, "Title Name: " + userTitle[i] + " is present", YesNo.No);
							if (BP.dealTeamAcuityRoleForInternal(user[i], userRole[i], 30) != null) {
								log(LogStatus.PASS, "Role : " + userRole[i] + " is present", YesNo.No);								
							} else {
								log(LogStatus.ERROR, "Role name not present: " + userRole[i], YesNo.Yes);
								sa.assertTrue(false, "Role name not present: " + userRole[i]);
							}
						} else {
							log(LogStatus.ERROR, "Not visible : " + userTitle[i], YesNo.Yes);
							sa.assertTrue(false, "Not visible : " + userTitle[i]);
						}
					} else {
						log(LogStatus.ERROR, "User Name name not present: " + user[i], YesNo.Yes);
						sa.assertTrue(false, "User name not present: " + user[i]);
					}
				} else {
					log(LogStatus.ERROR, "Internal Tab not present.", YesNo.Yes);
					sa.assertTrue(false, "Internal Tab not present.");
				}
		}
			}
		}else {
			log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
			sa.assertTrue(false,"could not click on new task button" );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc024_CreateSomeRevenueInboxEvents(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		
//		String username1=RGcrmUser1EmailID;
//		String username2=RGcrmUser2EmailID;
//		String username3=RGcrmUser3EmailID;
		String dealName = ACDealName1;
		
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String[] accountName = RGATE_FirmLegalName4.split("<break>");
		String[] firmsTaggedName= {RGATE_TaggedFirmName8};
		String[] firmsTaggedTimeReference= {RGATE_TaggedFirmCount8};
		String dealContact[] = ACDealContact1.split("<break>");
		String[] peopleTaggedName= {RGATE_TaggedPeopleName8};
		String[] peopleTaggedTimeReference= {RGATE_TaggedPeopleCount8};
		String[] title = RGATEConnectionTitle9.split("<break>");
		String[] role = RGATEConnectionRole9.split("<break>");
		String[] dealCount = RGATEConnectionsDeal9.split("<break>");
		String[] meetCount = RGATEConnectionsMeetCount9.split("<break>");
		String[] emailCount = RGATEConnectionsEmailCount9.split("<break>");
		String eventTitle8= RGATE_Subject8;
		String stage = RGATE_DealStage3;
		String hsr = RGATE_DealHSR3;
		String dateReceived = "";
		String[] user = ACDealTeamMember1.split("<break>");
		String[] userTitle = ACDealTeamTitle1.split("<break>");
		String[] userRole = ACDealTeamRole1.split("<break>");
		ArrayList<String> subjectNames = new ArrayList<String>();
		for (String subjectName : subjectNames) {
			subjectNames.add(subjectName);
		}
		String eventAttendees8=RGATE_RelatedTo8;
//		for(String userAndContact : userAndContact8)
//		{
//			if(userAndContact.equalsIgnoreCase("user 1"))
//			{
//				eventAttendees8=eventAttendees8+","+username1;
//			}
//			else if(userAndContact.equalsIgnoreCase("user 2"))
//			{
//				eventAttendees8=eventAttendees8+","+username2;
//			}
//			else if(userAndContact.equalsIgnoreCase("user 3"))
//			{
//				eventAttendees8=eventAttendees8+","+username3;
//			}
//			else
//			{
//				Assertion hardAssert = new Assertion();
//				log(LogStatus.ERROR, "user data is not correct on excel", YesNo.No);
//				hardAssert.assertTrue(true == false);
//			}		
//		}
		
		
		String startDate8 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay8));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate8, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_008", excelLabel.Advance_Start_Date);

		String endDate8 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay8));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate8, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_008", excelLabel.Advance_End_Date);

		String startTime8 = null;
		String endTime8 = null;
		String descriptionBox6 = RGATE_Notes8;

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle8 + " through Outlook---------",
				YesNo.No);
		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle8,
				eventAttendees8, startDate8, endDate8, startTime8, endTime8, descriptionBox6, true)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle8 + " has been created-----",
					YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle8
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle8
					+ " has not been created-----");
		}

		ThreadSleep(5000);

		if (lp.clickOnTab(projectName, tabObj4)) {

			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);
		
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.DealTab , dealName, 10)){
			log(LogStatus.INFO, "Click on deal : " + dealName, YesNo.No);
			
			if (BP.clicktabOnPage(TabName.Acuity.toString())) {
				log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
				
				refresh(driver);
				ThreadSleep(5000);
				if (BP.InteractionRecord(eventTitle8,10) != null) {
					log(LogStatus.INFO,
							"All records on Intraction card have been verified " + eventTitle8,
							YesNo.No);
					ThreadSleep(2000);
					sa.assertTrue(true,
							"All records on Intraction card have been verified " + eventTitle8);
				} else {
					log(LogStatus.ERROR, "All records on Intraction card is not created", YesNo.No);
					sa.assertTrue(false, "All records on Intraction card is not created");
				}
				
				clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
				ThreadSleep(4000);
				
				ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, null, null, null, null,isInstitutionRecord, null,null);
				if(result5.isEmpty())
				{
					log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
					sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
				}
				
				ArrayList<String> result=BP.verifyRecordAndReferencedTypeOnTagged(null, null, peopleTaggedName, peopleTaggedTimeReference, null, null,isInstitutionRecord, null,null);
				if(result.isEmpty())
				{
					log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
					sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
				}
				
				if(BP.ExternalTab(10, action.SCROLLANDBOOLEAN) != null) {
					log(LogStatus.PASS, "External Tab is present", YesNo.No);
					clickUsingJavaScript(driver, BP.ExternalTab(10,action.SCROLLANDBOOLEAN), "External Tab");
					log(LogStatus.PASS, "Clicked on External Tab", YesNo.No);
					ThreadSleep(2000);
					for(int i=0; i<accountName.length;i++) {
					if (BP.dealTeamAcuityDealName(dealContact[i], 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealContact[i] + " is hyperlink and is present", YesNo.No);
						if (BP.dealTeamAcuityTitleName(dealContact[i], title[i], 30) != null) {
							log(LogStatus.PASS, "Title Name: " + title[i] + " is present", YesNo.No);
							if (BP.dealTeamAcuityAccountName(dealContact[i], accountName[i], 30) != null) {
								log(LogStatus.PASS, "Account Name : " + accountName + " is present", YesNo.No);
								if (BP.dealTeamAcuityRole(dealContact[i], role[i], 30) != null) {
									log(LogStatus.PASS, "Role : " + role[i] + " is present", YesNo.No);								
									if (BP.dealTeamAcuityDeals(dealContact[i], dealCount[i], 30) != null) {
										log(LogStatus.INFO,"Deal Count : " + dealCount[i] + " is present", YesNo.No);
										if (BP.dealTeamAcuityMeetingsAndCalls(dealContact[i], meetCount[i], 30) != null) {
											log(LogStatus.INFO,"Meetings & Calls Count : " + meetCount[i] + " is present", YesNo.No);
											if (BP.dealTeamAcuityEmail(dealContact[i], emailCount[i], 30) != null) {
												log(LogStatus.INFO,"Email Count : " + emailCount[i] + " is present", YesNo.No);
											}
											else {
											log(LogStatus.ERROR, "Email Count is not matched with "+emailCount[i], YesNo.Yes);
												sa.assertTrue(false,"Email Count is not matched with "+emailCount[i] );
											}
										}
										else {
										log(LogStatus.ERROR, "Meetings & Calls Count is not matched with "+meetCount[i], YesNo.Yes);
											sa.assertTrue(false,"Meetings & Calls Count is not matched with "+meetCount[i] );
										}
									}
									else {
									log(LogStatus.ERROR, "Deal Count is not matched with "+dealCount[i], YesNo.Yes);
										sa.assertTrue(false,"Deal Count is not matched with "+dealCount[i] );
									}
								} else {
									log(LogStatus.ERROR, "Role name not present: " + role[i], YesNo.Yes);
									sa.assertTrue(false, "Role name not present: " + role[i]);
								}
							} else {
								log(LogStatus.ERROR, "Not able to Click on Account Name: " + accountName[i], YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Account Name: " + accountName[i]);

							}
							} else {
								log(LogStatus.ERROR, "Not able to Click on Title: " + title[i], YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Title: " + title[i]);
							}
						} else {
							log(LogStatus.ERROR, "Contact not present: " + dealContact[i], YesNo.Yes);
							sa.assertTrue(false, "Contact not present: " + dealContact[i]);
						}
				}
					} else {
						log(LogStatus.ERROR, "External Tab not present.", YesNo.Yes);
						sa.assertTrue(false, "External Tab not present.");
					}	
				
				ThreadSleep(2000);
				
			if(BP.InternalTab(10, action.SCROLLANDBOOLEAN) != null) {
				log(LogStatus.PASS, "Internal Tab is present", YesNo.No);
				clickUsingJavaScript(driver, BP.InternalTab(10,action.SCROLLANDBOOLEAN), "Internal Tab");
				log(LogStatus.PASS, "Clicked on Internal Tab", YesNo.No);
				ThreadSleep(2000);
				for(int i=0; i<accountName.length;i++) {
				if (BP.dealTeamAcuityUserName(user[i], 30) != null) {
					log(LogStatus.PASS, "User Name: " + user[i] + " is hyperlink and is present", YesNo.No);
					if (BP.dealTeamAcuityUserTitle(user[i], userTitle[i], 30) != null) {
						log(LogStatus.PASS, "Title Name: " + userTitle[i] + " is present", YesNo.No);
							if (BP.dealTeamAcuityRoleForInternal(user[i], userRole[i], 30) != null) {
								log(LogStatus.PASS, "Role : " + userRole[i] + " is present", YesNo.No);								
							} else {
								log(LogStatus.ERROR, "Role name not present: " + userRole[i], YesNo.Yes);
								sa.assertTrue(false, "Role name not present: " + userRole[i]);
							}
						} else {
							log(LogStatus.ERROR, "Not visible : " + userTitle[i], YesNo.Yes);
							sa.assertTrue(false, "Not visible : " + userTitle[i]);
						}
					} else {
						log(LogStatus.ERROR, "User Name name not present: " + user[i], YesNo.Yes);
						sa.assertTrue(false, "User name not present: " + user[i]);
					}
				}
				} else {
					log(LogStatus.ERROR, "Internal Tab not present.", YesNo.Yes);
					sa.assertTrue(false, "Internal Tab not present.");
				}
			} else {
				log(LogStatus.ERROR, "Not able to Click on Acuity Tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Acuity Tab");
			}
		} else
		{
			log(LogStatus.ERROR, "Not able to Click on deal : " + dealName, YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on deal : " + dealName);
		}
		
		}else {
			log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
			sa.assertTrue(false,"could not click on new task button" );
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Click on Tab : " + tabObj2, YesNo.No);
			ThreadSleep(3000);
		
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , peopleTaggedName[0], 10)){
			log(LogStatus.INFO, "Click on deal : " + peopleTaggedName[0], YesNo.No);
			ThreadSleep(5000);
			if (BP.InteractionRecord(eventTitle8,10) != null) {
				log(LogStatus.INFO,
						"All records on Intraction card have been verified " + eventTitle8,
						YesNo.No);
				ThreadSleep(2000);
				if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//					if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
							log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);
							
						} else {
							log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
							sa.assertTrue(false, "HSR stage name not present: " + hsr);

						}
					} else {
						log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

					}
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}

			} else {
				log(LogStatus.ERROR, "All records on Intraction card is not created", YesNo.No);
				sa.assertTrue(false, "All records on Intraction card is not created");
			}
		}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc025_CreateAccountDeals(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
		FundsPageBusinessLayer fd = new FundsPageBusinessLayer(driver);
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		
		String[] accountName = RGATE_FirmLegalName5.split("<break>");
		String[] recordType = RGATE_FirmRecordType4.split("<break>");

		String[] contactFirstName = RGATE_ContactFirstName4.split("<break>");
		String[] contactLastName = RGATE_ContactLastName4.split("<break>");
		String[] contactLegalName = RGATE_ContactLegalName4.split("<break>");
		String[] contactEmail = RGATE_ContactEmail4.split("<break>");
		String[] contactTitle = RGATE_Title4.split("<break>");
		String dealName = RGATE_DealName4;
		String dealCompany = RGATE_DealCompany4;
		String dealStage = RGATE_DealStage4;
		String fundName = RGATE_FundName4;
		String fundType = AS_FundType;
		String fundInvestmentCategory = AS_FundInvestmentCategory;

			for (int i = 0; i < accountName.length; i++) {
				if (lp.clickOnTab(projectName, TabName.Object1Tab)) {

					log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
					ThreadSleep(3000);
					if (ip.createEntityOrAccount(environment, mode, accountName[i], recordType[i], null, null, 30)) {
						log(LogStatus.INFO,
								"successfully Created Firm : " + accountName[i] + " of record type : " + recordType[i],
								YesNo.No);
						sa.assertTrue(true,
								"successfully Created Firm : " + accountName[i] + " of record type : " + recordType[i]);


					} else {
						sa.assertTrue(false,
								"Not Able to Create Firm : " + accountName[i] + " of record type : " + recordType[i]);
						log(LogStatus.SKIP,
								"Not Able to Create Firm : " + accountName[i] + " of record type :" + recordType[i],
								YesNo.Yes);
					}

				} else {
					log(LogStatus.FAIL, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
				}
				refresh(driver);
			}
			ThreadSleep(2000);
		for (int i = 0; i < contactLastName.length; i++) {
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Click on Tab : " + tabObj2, YesNo.No);
				ThreadSleep(3000);

				if (cp.createContact(projectName, contactFirstName[i], contactLastName[i], contactLegalName[i],
						contactEmail[i], "", Fields.Title.toString(),contactTitle[i], CreationPage.ContactPage, null, null)) {
					log(LogStatus.INFO,
							"successfully Created Contact : " + contactFirstName[i] + " " + contactLastName[i],
							YesNo.No);
					sa.assertTrue(true,
							"successfully Created Contact : " + contactFirstName[i] + " " + contactLastName[i]);
				} else {
					log(LogStatus.FAIL,
							"Not able to create the Contact : " + contactFirstName[i] + " " + contactLastName[i],
							YesNo.No);
					sa.assertTrue(false,
							"Not able to create the Contact : " + contactFirstName[i] + " " + contactLastName[i]);
				}

			} else {
				log(LogStatus.FAIL, "Not able to click on " + tabObj2 + " Tab", YesNo.No);
				sa.assertTrue(false, "Not able to click on " + tabObj2 + " Tab");
			}
			refresh(driver);
		}
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, tabObj3)) {

			log(LogStatus.INFO, "Click on Tab : " + tabObj3, YesNo.No);
			ThreadSleep(3000);

			if (fd.createFund(projectName, fundName, fundType, fundInvestmentCategory, null, null)) {
				log(LogStatus.INFO, fundName + " Fund has been created", YesNo.No);
				sa.assertTrue(true, fundName + " Fund has been created");
			} else {
				log(LogStatus.ERROR, fundName + " Fund is not created", YesNo.No);
				sa.assertTrue(false, fundName + " Fund is not created");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj3 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj3 + " Tab");
		}
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {

			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);

			if (fr.createFundRaising(environment, mode, dealName, fundName, dealCompany, null, dealStage,null,null)) {
				log(LogStatus.INFO, dealName + " Fundraising has been created", YesNo.No);
				sa.assertTrue(true, dealName + " Fundraising has been created");

			} else {
				log(LogStatus.ERROR, dealName + " Fundraising is not created", YesNo.No);
				sa.assertTrue(false, dealName + " Fundraising is not created");
			}
		}
		else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.FundraisingsTab + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.FundraisingsTab + " Tab");
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void RGATETc026_CreateDealTeamAndVerifyOnInternalAndExternalTabOfDeal(String projectName) {	
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		
		String[] TeamMember = ACDealTeamMember2.split("<break>");
		String[] contactName = ACDealContact2.split("<break>");
		String[] teamRole = ACDealTeamRole2.split("<break>");
		String dealName = ACDealName2;
		String[] accountName = RGATE_FirmLegalName5.split("<break>");
		String[] title = RGATEConnectionTitle10.split("<break>");
		String[] role = ACDealTeamRole2.split("<break>");
		String[] dealCount = RGATEConnectionsDeal10.split("<break>");
		String[] meetCount = RGATEConnectionsCallCount10.split("<break>");
		String[] emailCount = RGATEConnectionsEmailCount10.split("<break>");
		
		log(LogStatus.INFO, TeamMember.length + " is length", YesNo.No);
		for (int i = 0; i < TeamMember.length; i++) {
			String[][] data = { { PageLabel.Fundraising.toString(), dealName }, { PageLabel.Role.toString(), teamRole[i] },
					{ PageLabel.Contact.toString(), contactName[i] }, { PageLabel.Firm.toString(), TeamMember[i] } };
		if (BP.openAppFromAppLauchner(10, "Fundraising Contacts")) {
			log(LogStatus.INFO, "Fundraising Contacts has been open from the app launcher", YesNo.No);

			if (fr.createFundraisingContact(projectName, dealName, data, action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Fundraising Contacts for Firm: " + TeamMember + "----", YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR, "could not create a new Fundraising Contacts", YesNo.Yes);
				sa.assertTrue(false,"could not create a new Fundraising Contacts" );
			}
		}
		else
		{
			log(LogStatus.ERROR, "could not open a Fundraising Contacts tab", YesNo.Yes);
			sa.assertTrue(false,"could not open a Fundraising Contacts tab" );
		}
		}
		
		ThreadSleep(2000);
		
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {

			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(5000);
		
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , dealName, 10)){
			log(LogStatus.INFO, "Click on deal : " + dealName, YesNo.No);
			
			for(int i=0; i<accountName.length-1;i++) {
					if (BP.dealTeamAcuityDealName(contactName[i], 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + contactName[i] + " is hyperlink and is present", YesNo.No);
						if (BP.dealTeamAcuityTitleName(contactName[i], title[i], 30) != null) {
							log(LogStatus.PASS, "Title Name: " + title[i] + " is present", YesNo.No);
							if (BP.dealTeamAcuityAccountName(contactName[i], accountName[i], 30) != null) {
								log(LogStatus.PASS, "Account Name : " + accountName + " is present", YesNo.No);
								if (BP.dealTeamAcuityRole(contactName[i], role[i], 30) != null) {
									log(LogStatus.PASS, "HSR: " + role[i] + " is present", YesNo.No);								
									if (BP.dealTeamAcuityDeals(contactName[i], dealCount[i], 30) != null) {
										log(LogStatus.INFO,"Deal Count : " + dealCount[i] + " is present", YesNo.No);
										if (BP.dealTeamAcuityMeetingsAndCalls(contactName[i], meetCount[i], 30) != null) {
											log(LogStatus.INFO,"Meetings & Calls Count : " + meetCount[i] + " is present", YesNo.No);
											if (BP.dealTeamAcuityEmail(contactName[i], emailCount[i], 30) != null) {
												log(LogStatus.INFO,"Email Count : " + emailCount[i] + " is present", YesNo.No);
											}
											else {
											log(LogStatus.ERROR, "Email Count is not matched with "+emailCount[i], YesNo.Yes);
												sa.assertTrue(false,"Email Count is not matched with "+emailCount[i] );
											}
										}
										else {
										log(LogStatus.ERROR, "Meetings & Calls Count is not matched with "+meetCount[i], YesNo.Yes);
											sa.assertTrue(false,"Meetings & Calls Count is not matched with "+meetCount[i] );
										}
									}
									else {
									log(LogStatus.ERROR, "Deal Count is not matched with "+dealCount[i], YesNo.Yes);
										sa.assertTrue(false,"Deal Count is not matched with "+dealCount[i] );
									}
								} else {
									log(LogStatus.ERROR, "Role name not present: " + role[i], YesNo.Yes);
									sa.assertTrue(false, "Role name not present: " + role[i]);
								}
							} else {
								log(LogStatus.ERROR, "Not able to Click on Account Name: " + accountName[i], YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Account Name: " + accountName[i]);

							}
							} else {
								log(LogStatus.ERROR, "Not able to Click on Title: " + title[i], YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Title: " + title[i]);
							}
						} else {
							log(LogStatus.ERROR, "Contact not present: " + contactName[i], YesNo.Yes);
							sa.assertTrue(false, "Contact not present: " + contactName[i]);
						}	
				}
					}
				}else {
					log(LogStatus.ERROR, "could not click on fundraising tab", YesNo.Yes);
					sa.assertTrue(false,"could not click on fundraising tab" );
				}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc027_CreateSomeRevenueInboxEvents(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		
//		String username1=RGcrmUser1EmailID;
//		String username2=RGcrmUser2EmailID;
//		String username3=RGcrmUser3EmailID;
		String dealName = ACDealName2;
		
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String[] accountName = RGATE_FirmLegalName5.split("<break>");
		String[] firmsTaggedName= {RGATE_TaggedFirmName9};
		String[] firmsTaggedTimeReference= {RGATE_TaggedFirmCount9};
		String dealContact[] = ACDealContact2.split("<break>");
		String[] peopleTaggedName= {RGATE_TaggedPeopleName9};
		String[] peopleTaggedTimeReference= {RGATE_TaggedPeopleCount9};
		String[] title = RGATEConnectionTitle10.split("<break>");
		String[] role = RGATEConnectionRole10.split("<break>");
		String[] dealCount = RGATEConnectionsDeal10.split("<break>");
		String[] meetCount = RGATEConnectionsMeetCount10.split("<break>");
		String[] emailCount = RGATEConnectionsEmailCount10.split("<break>");
		String eventTitle8= RGATE_Subject9;
		ArrayList<String> subjectNames = new ArrayList<String>();
		for (String subjectName : subjectNames) {
			subjectNames.add(subjectName);
		}
		String eventAttendees8=RGATE_RelatedTo9;
//		for(String userAndContact : userAndContact8)
//		{
//			if(userAndContact.equalsIgnoreCase("user 1"))
//			{
//				eventAttendees8=eventAttendees8+","+username1;
//			}
//			else if(userAndContact.equalsIgnoreCase("user 2"))
//			{
//				eventAttendees8=eventAttendees8+","+username2;
//			}
//			else if(userAndContact.equalsIgnoreCase("user 3"))
//			{
//				eventAttendees8=eventAttendees8+","+username3;
//			}
//			else
//			{
//				Assertion hardAssert = new Assertion();
//				log(LogStatus.ERROR, "user data is not correct on excel", YesNo.No);
//				hardAssert.assertTrue(true == false);
//			}		
//		}
		
		
		String startDate8 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay9));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate8, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_009", excelLabel.Advance_Start_Date);

		String endDate8 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay9));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate8, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_009", excelLabel.Advance_End_Date);

		String startTime8 = null;
		String endTime8 = null;
		String descriptionBox6 = RGATE_Notes9;

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle8 + " through Outlook---------",
				YesNo.No);
		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle8,
				eventAttendees8, startDate8, endDate8, startTime8, endTime8, descriptionBox6, true)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle8 + " has been created-----",
					YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle8
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle8
					+ " has not been created-----");
		}

		ThreadSleep(5000);

		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {

			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
		
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , dealName, 10)){
			log(LogStatus.INFO, "Click on deal : " + dealName, YesNo.No);
			
			if (BP.clicktabOnPage(TabName.Acuity.toString())) {
				log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
				
				refresh(driver);
				ThreadSleep(5000);
				if (BP.InteractionRecord(eventTitle8,10) != null) {
					log(LogStatus.INFO,
							"All records on Intraction card have been verified " + eventTitle8,
							YesNo.No);
					ThreadSleep(2000);
					sa.assertTrue(true,
							"All records on Intraction card have been verified " + eventTitle8);
				} else {
					log(LogStatus.ERROR, "All records on Intraction card is not created", YesNo.No);
					sa.assertTrue(false, "All records on Intraction card is not created");
				}
				
				clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
				ThreadSleep(4000);
				
				ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, null, null, null, null,isInstitutionRecord, null,null);
				if(result5.isEmpty())
				{
					log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
					sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
				}
				
				ArrayList<String> result=BP.verifyRecordAndReferencedTypeOnTagged(null, null, peopleTaggedName, peopleTaggedTimeReference, null, null,isInstitutionRecord, null,null);
				if(result.isEmpty())
				{
					log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
					sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
				}
				
					for(int i=0; i<accountName.length;i++) {
					if (BP.dealTeamAcuityDealName(dealContact[i], 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealContact[i] + " is hyperlink and is present", YesNo.No);
						if (BP.dealTeamAcuityTitleName(dealContact[i], title[i], 30) != null) {
							log(LogStatus.PASS, "Title Name: " + title[i] + " is present", YesNo.No);
							if (BP.dealTeamAcuityAccountName(dealContact[i], accountName[i], 30) != null) {
								log(LogStatus.PASS, "Account Name : " + accountName + " is present", YesNo.No);
								if (BP.dealTeamAcuityRole(dealContact[i], role[i], 30) != null) {
									log(LogStatus.PASS, "Role : " + role[i] + " is present", YesNo.No);								
									if (BP.dealTeamAcuityDeals(dealContact[i], dealCount[i], 30) != null) {
										log(LogStatus.INFO,"Deal Count : " + dealCount[i] + " is present", YesNo.No);
										if (BP.dealTeamAcuityMeetingsAndCalls(dealContact[i], meetCount[i], 30) != null) {
											log(LogStatus.INFO,"Meetings & Calls Count : " + meetCount[i] + " is present", YesNo.No);
											if (BP.dealTeamAcuityEmail(dealContact[i], emailCount[i], 30) != null) {
												log(LogStatus.INFO,"Email Count : " + emailCount[i] + " is present", YesNo.No);
											}
											else {
											log(LogStatus.ERROR, "Email Count is not matched with "+emailCount[i], YesNo.Yes);
												sa.assertTrue(false,"Email Count is not matched with "+emailCount[i] );
											}
										}
										else {
										log(LogStatus.ERROR, "Meetings & Calls Count is not matched with "+meetCount[i], YesNo.Yes);
											sa.assertTrue(false,"Meetings & Calls Count is not matched with "+meetCount[i] );
										}
									}
									else {
									log(LogStatus.ERROR, "Deal Count is not matched with "+dealCount[i], YesNo.Yes);
										sa.assertTrue(false,"Deal Count is not matched with "+dealCount[i] );
									}
								} else {
									log(LogStatus.ERROR, "Role name not present: " + role[i], YesNo.Yes);
									sa.assertTrue(false, "Role name not present: " + role[i]);
								}
							} else {
								log(LogStatus.ERROR, "Not able to Click on Account Name: " + accountName[i], YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Account Name: " + accountName[i]);

							}
							} else {
								log(LogStatus.ERROR, "Not able to Click on Title: " + title[i], YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Title: " + title[i]);
							}
						} else {
							log(LogStatus.ERROR, "Contact not present: " + dealContact[i], YesNo.Yes);
							sa.assertTrue(false, "Contact not present: " + dealContact[i]);
						}
				}	
			} else {
				log(LogStatus.ERROR, "Not able to Click on Acuity Tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Acuity Tab");
			}
		} else
		{
			log(LogStatus.ERROR, "Not able to Click on deal : " + dealName, YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on deal : " + dealName);
		}
		
		}else {
			log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
			sa.assertTrue(false,"could not click on new task button" );
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Click on Tab : " + tabObj2, YesNo.No);
			ThreadSleep(3000);
		
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , peopleTaggedName[0], 10)){
			log(LogStatus.INFO, "Click on deal : " + peopleTaggedName[0], YesNo.No);
			ThreadSleep(5000);
			if (BP.InteractionRecord(eventTitle8,10) != null) {
				log(LogStatus.INFO,
						"All records on Intraction card have been verified " + eventTitle8,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "All records on Intraction card is not created", YesNo.No);
				sa.assertTrue(false, "All records on Intraction card is not created");
			}
		} else
		{
			log(LogStatus.ERROR, "Not able to click on " + peopleTaggedName[0], YesNo.No);
			sa.assertTrue(false, "Not able to click on " + peopleTaggedName[0]);
		}
		
		ThreadSleep(2000);
		
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Object1Tab , firmsTaggedName[0], 10)){
			log(LogStatus.INFO, "Click on deal : " + firmsTaggedName[0], YesNo.No);
			ThreadSleep(5000);
			if (BP.InteractionRecord(eventTitle8,10) != null) {
				log(LogStatus.INFO,
						"All records on Intraction card have been verified " + eventTitle8,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "All records on Intraction card is not created", YesNo.No);
				sa.assertTrue(false, "All records on Intraction card is not created");
			}
		} else
		{
			log(LogStatus.ERROR, "Not able to click on " + firmsTaggedName[0], YesNo.No);
			sa.assertTrue(false, "Not able to click on " + firmsTaggedName[0]);
		}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc028_CreateSomeRevenueInboxEvents(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		
//		String username1=RGcrmUser1EmailID;
//		String username2=RGcrmUser2EmailID;
//		String username3=RGcrmUser3EmailID;
		
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String[] accountName = RGATE_FirmLegalName3.split("<break>");
		String details = RGATE_Notes10;
		String[] firmsTaggedName= RGATE_TaggedFirmName10.split("<break>");
		String[] firmsTaggedTimeReference= RGATE_TaggedFirmCount10.split("<break>");
		String[] peopleTaggedName= RGATE_TaggedPeopleName10.split("<break>");
		String[] peopleTaggedTimeReference= RGATE_TaggedPeopleCount10.split("<break>");
		String eventTitle8= RGATE_Subject10;
		String eventAttendees8=RGATE_RelatedTo10;
		String advancedDate = RGATE_AdvanceStartDate10;
		ArrayList<String> subjectNames = new ArrayList<String>();
		for (String subjectName : subjectNames) {
			subjectNames.add(subjectName);
		}
//		for(String userAndContact : userAndContact8)
//		{
//			if(userAndContact.equalsIgnoreCase("user 1"))
//			{
//				eventAttendees8=eventAttendees8+","+username1;
//			}
//			else if(userAndContact.equalsIgnoreCase("user 2"))
//			{
//				eventAttendees8=eventAttendees8+","+username2;
//			}
//			else if(userAndContact.equalsIgnoreCase("user 3"))
//			{
//				eventAttendees8=eventAttendees8+","+username3;
//			}
//			else
//			{
//				Assertion hardAssert = new Assertion();
//				log(LogStatus.ERROR, "user data is not correct on excel", YesNo.No);
//				hardAssert.assertTrue(true == false);
//			}		
//		}
		
		
		String startDate8 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay10));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate8, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_010", excelLabel.Advance_Start_Date);

		String endDate8 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay10));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate8, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_010", excelLabel.Advance_End_Date);

		String startTime8 = null;
		String endTime8 = null;
		String descriptionBox6 = RGATE_Notes10;

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle8 + " through Outlook---------",
				YesNo.No);
		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle8,
				eventAttendees8, startDate8, endDate8, startTime8, endTime8, descriptionBox6, true)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle8 + " has been created-----",
					YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle8
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle8
					+ " has not been created-----");
		}

		ThreadSleep(5000);

		if (lp.clickOnTab(projectName, TabName.InstituitonsTab)) {

			log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
			ThreadSleep(3000);
		
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.InstituitonsTab , accountName[2], 10)){
			log(LogStatus.INFO, "Click on deal : " + accountName[2], YesNo.No);
			
			if (BP.clicktabOnPage(TabName.Acuity.toString())) {
				log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
				
				refresh(driver);
				ThreadSleep(5000);
				
				ArrayList<String> result1=BP.verifyRecordOnInteractionCard(advancedDate, IconType.Meeting, eventTitle8, details, false, true, null,null);
				if(result1.isEmpty())
				{
					log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+eventTitle8 , YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+eventTitle8+". "+result1 , YesNo.No);
					sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+eventTitle8+". "+result1);
				}
				
				for(int i=0; i<firmsTaggedName.length; i++) {
				ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, null, null, null, null,isInstitutionRecord, null,null);
				if(result5.isEmpty())
				{
					log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					if(Integer.parseInt(firmsTaggedTimeReference[i]) > 0) {
						clickUsingJavaScript(driver, BP.getCountLinkOnTagged(10), "Firm Tab");
						ThreadSleep(4000);
						CommonLib.switchOnWindow(driver);
							if(BP.returnXpathOfSubjectInInteractionPage(eventTitle8,10) != null) {
								log(LogStatus.PASS, eventTitle8 + " is present", YesNo.No);
							} else {
								log(LogStatus.ERROR, eventTitle8 + " is not present", YesNo.Yes);
								sa.assertTrue(false, eventTitle8 + " is not present");
							}
						} else {
							log(LogStatus.ERROR, "Count of firm tagged tim reference is zero, so no need to click", YesNo.No);
						}
				}
				else
				{
					log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
					sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
				}
				
				clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
				ThreadSleep(4000);
				
				ArrayList<String> result=BP.verifyRecordAndReferencedTypeOnTagged(null, null, peopleTaggedName, peopleTaggedTimeReference, null, null,isInstitutionRecord, null,null);
				if(result.isEmpty())
				{
					log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					if(Integer.parseInt(peopleTaggedTimeReference[i]) > 0) {
					clickUsingJavaScript(driver, BP.getCountLinkOnTagged(10), "People Tab");
					ThreadSleep(4000);
					CommonLib.switchOnWindow(driver);
						if(BP.returnXpathOfSubjectInInteractionPage(eventTitle8,10) != null) {
							log(LogStatus.PASS, eventTitle8 + " is present", YesNo.No);
						} else {
							log(LogStatus.ERROR, eventTitle8 + " is not present", YesNo.Yes);
							sa.assertTrue(false, eventTitle8 + " is not present");
						}
					} else {
						log(LogStatus.ERROR, "Count of people tagged tim reference is zero, so no need to click", YesNo.No);
					}
				} else {
					log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.Yes);
					sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
				}
				}
				} else {
					log(LogStatus.ERROR, "Not able to Click on Acuity Tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Acuity Tab");
				}
					} else
					{
						log(LogStatus.ERROR, "Not able to Click on firm : " + accountName[2], YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on firm : " + accountName[2]);
					}
					
					}else {
						log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
						sa.assertTrue(false,"could not click on new task button" );
					}
		lp.CRMlogout();
		sa.assertAll();
		}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc029_VeriyDataForLenderFirm(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		
//		String username1=RGcrmUser1EmailID;
//		String username2=RGcrmUser2EmailID;
//		String username3=RGcrmUser3EmailID;
		
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String[] accountName = RGATE_FirmLegalName3.split("<break>");
		String details = RGATE_Notes10;
		String[] firmsTaggedName= RGATE_TaggedFirmName11.split("<break>");
		String[] firmsTaggedTimeReference= RGATE_TaggedFirmCount11.split("<break>");
		String[] peopleTaggedName= RGATE_TaggedPeopleName11.split("<break>");
		String[] peopleTaggedTimeReference= RGATE_TaggedPeopleCount11.split("<break>");
		String eventTitle8= RGATE_Subject10;
		String advancedDate = RGATE_AdvanceStartDate10;
		ArrayList<String> subjectNames = new ArrayList<String>();
		for (String subjectName : subjectNames) {
			subjectNames.add(subjectName);
		}
//		for(String userAndContact : userAndContact8)
//		{
//			if(userAndContact.equalsIgnoreCase("user 1"))
//			{
//				eventAttendees8=eventAttendees8+","+username1;
//			}
//			else if(userAndContact.equalsIgnoreCase("user 2"))
//			{
//				eventAttendees8=eventAttendees8+","+username2;
//			}
//			else if(userAndContact.equalsIgnoreCase("user 3"))
//			{
//				eventAttendees8=eventAttendees8+","+username3;
//			}
//			else
//			{
//				Assertion hardAssert = new Assertion();
//				log(LogStatus.ERROR, "user data is not correct on excel", YesNo.No);
//				hardAssert.assertTrue(true == false);
//			}		
//		}
		
		
		String startDate8 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay10));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate8, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_010", excelLabel.Advance_Start_Date);

		String endDate8 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay10));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate8, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_010", excelLabel.Advance_End_Date);

		if (lp.clickOnTab(projectName, TabName.InstituitonsTab)) {

			log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
			ThreadSleep(3000);
		
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.InstituitonsTab , accountName[3], 10)){
			log(LogStatus.INFO, "Click on deal : " + accountName[3], YesNo.No);
			
			if (BP.clicktabOnPage(TabName.Acuity.toString())) {
				log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
				
				refresh(driver);
				ThreadSleep(5000);
				
				ArrayList<String> result1=BP.verifyRecordOnInteractionCard(advancedDate, IconType.Meeting, eventTitle8, details, false, true, null,null);
				if(result1.isEmpty())
				{
					log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+eventTitle8 , YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+eventTitle8+". "+result1 , YesNo.No);
					sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+eventTitle8+". "+result1);
				}
				
				for(int i=0; i<firmsTaggedName.length; i++) {
				ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, null, null, null, null,isInstitutionRecord, null,null);
				if(result5.isEmpty())
				{
					log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					if(Integer.parseInt(firmsTaggedTimeReference[i]) > 0) {
						clickUsingJavaScript(driver, BP.getCountLinkOnTagged(10), "Firm Tab");
						ThreadSleep(4000);
						CommonLib.switchOnWindow(driver);
							if(BP.returnXpathOfSubjectInInteractionPage(eventTitle8,10) != null) {
								log(LogStatus.PASS, eventTitle8 + " is present", YesNo.No);
							} else {
								log(LogStatus.ERROR, eventTitle8 + " is not present", YesNo.Yes);
								sa.assertTrue(false, eventTitle8 + " is not present");
							}
						} else {
							log(LogStatus.ERROR, "Count of firm tagged tim reference is zero, so no need to click", YesNo.No);
						}
				}
				else
				{
					log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
					sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
				}
				
				clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
				ThreadSleep(4000);
				
				ArrayList<String> result=BP.verifyRecordAndReferencedTypeOnTagged(null, null, peopleTaggedName, peopleTaggedTimeReference, null, null,isInstitutionRecord, null,null);
				if(result.isEmpty())
				{
					log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					if(Integer.parseInt(peopleTaggedTimeReference[i]) > 0) {
					clickUsingJavaScript(driver, BP.getCountLinkOnTagged(10), "People Tab");
					ThreadSleep(4000);
					CommonLib.switchOnWindow(driver);
						if(BP.returnXpathOfSubjectInInteractionPage(eventTitle8,10) != null) {
							log(LogStatus.PASS, eventTitle8 + " is present", YesNo.No);
						} else {
							log(LogStatus.ERROR, eventTitle8 + " is not present", YesNo.Yes);
							sa.assertTrue(false, eventTitle8 + " is not present");
						}
					} else {
						log(LogStatus.ERROR, "Count of people tagged tim reference is zero, so no need to click", YesNo.No);
					}
				} else {
					log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.Yes);
					sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
				}
				}
				} else {
					log(LogStatus.ERROR, "Not able to Click on Acuity Tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Acuity Tab");
				}
					} else {
						log(LogStatus.ERROR, "Not able to Click on firm : " + accountName[3], YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on firm : " + accountName[3]);
					}
					
					}else {
						log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
						sa.assertTrue(false,"could not click on new task button" );
					}
		lp.CRMlogout();
		sa.assertAll();
		}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc030_VeriyDataForPrivateEquityFirm(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String[] accountName = RGATE_FirmLegalName3.split("<break>");
		String details = RGATE_Notes10;
		String[] firmsTaggedName= RGATE_TaggedFirmName12.split("<break>");
		String[] firmsTaggedTimeReference= RGATE_TaggedFirmCount12.split("<break>");
		String[] peopleTaggedName= RGATE_TaggedPeopleName12.split("<break>");
		String[] peopleTaggedTimeReference= RGATE_TaggedPeopleCount12.split("<break>");
		String eventTitle8= RGATE_Subject10;
		String advancedDate = RGATE_AdvanceStartDate10;
		ArrayList<String> subjectNames = new ArrayList<String>();
		for (String subjectName : subjectNames) {
			subjectNames.add(subjectName);
		}
		
		String startDate8 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay10));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate8, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_010", excelLabel.Advance_Start_Date);

		String endDate8 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay10));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate8, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_010", excelLabel.Advance_End_Date);

		if (lp.clickOnTab(projectName, TabName.InstituitonsTab)) {

			log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
			ThreadSleep(3000);
		
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.InstituitonsTab , accountName[4], 10)){
			log(LogStatus.INFO, "Click on deal : " + accountName[4], YesNo.No);
			
			if (BP.clicktabOnPage(TabName.Acuity.toString())) {
				log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
				
				refresh(driver);
				ThreadSleep(5000);
				
				ArrayList<String> result1=BP.verifyRecordOnInteractionCard(advancedDate, IconType.Meeting, eventTitle8, details, false, true, null,null);
				if(result1.isEmpty())
				{
					log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+eventTitle8 , YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+eventTitle8+". "+result1 , YesNo.No);
					sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+eventTitle8+". "+result1);
				}
				
				for(int i=0; i<firmsTaggedName.length; i++) {
				ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, null, null, null, null,isInstitutionRecord, null,null);
				if(result5.isEmpty())
				{
					log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					if(Integer.parseInt(firmsTaggedTimeReference[i]) > 0) {
						clickUsingJavaScript(driver, BP.getCountLinkOnTagged(10), "Firm Tab");
						ThreadSleep(4000);
						CommonLib.switchOnWindow(driver);
							if(BP.returnXpathOfSubjectInInteractionPage(eventTitle8,10) != null) {
								log(LogStatus.PASS, eventTitle8 + " is present", YesNo.No);
							} else {
								log(LogStatus.ERROR, eventTitle8 + " is not present", YesNo.Yes);
								sa.assertTrue(false, eventTitle8 + " is not present");
							}
						} else {
							log(LogStatus.ERROR, "Count of firm tagged tim reference is zero, so no need to click", YesNo.No);
						}
				}
				else
				{
					log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
					sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
				}
				
				clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
				ThreadSleep(4000);
				
				ArrayList<String> result=BP.verifyRecordAndReferencedTypeOnTagged(null, null, peopleTaggedName, peopleTaggedTimeReference, null, null,isInstitutionRecord, null,null);
				if(result.isEmpty())
				{
					log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					if(Integer.parseInt(peopleTaggedTimeReference[i]) > 0) {
					clickUsingJavaScript(driver, BP.getCountLinkOnTagged(10), "People Tab");
					ThreadSleep(4000);
					CommonLib.switchOnWindow(driver);
						if(BP.returnXpathOfSubjectInInteractionPage(eventTitle8,10) != null) {
							log(LogStatus.PASS, eventTitle8 + " is present", YesNo.No);
						} else {
							log(LogStatus.ERROR, eventTitle8 + " is not present", YesNo.Yes);
							sa.assertTrue(false, eventTitle8 + " is not present");
						}
					} else {
						log(LogStatus.ERROR, "Count of people tagged tim reference is zero, so no need to click", YesNo.No);
					}
				} else {
					log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.Yes);
					sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
				}
				}
				} else {
					log(LogStatus.ERROR, "Not able to Click on Acuity Tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Acuity Tab");
				}
					} else {
						log(LogStatus.ERROR, "Not able to Click on firm : " + accountName[4], YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on firm : " + accountName[4]);
					}
					
					}else {
						log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
						sa.assertTrue(false,"could not click on new task button" );
					}
		lp.CRMlogout();
		sa.assertAll();
		}

	@Parameters({ "projectName" })
	@Test
	public void RGATETc031_UpdateEventDetailsAndVerify(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String updatedCommentOfTask = RGATE_Description10;
		String[][] task1UpdateBasicSection = { { RGATE_Subject10, updatedCommentOfTask } };
		String[] task1SubjectName = RGATE_FirmLegalName3.split("<break>");
		String[] accountName = RGATE_FirmLegalName3.split("<break>");
		String details = RGATE_Notes10;
		String eventTitle8= RGATE_Subject10;
		String advancedDate = RGATE_AdvanceStartDate10;
		ArrayList<String> subjectNames = new ArrayList<String>();
		for (String subjectName : subjectNames) {
			subjectNames.add(subjectName);
		}
		
		String startDate8 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_StartDay10));
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate8, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_010", excelLabel.Advance_Start_Date);

		String endDate8 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt(RGATE_EndDay10));
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate8, "Activity Timeline", excelLabel.Variable_Name,
				"RGATE_010", excelLabel.Advance_End_Date);

		if (home.globalSearchAndNavigate(task1SubjectName[2], "Events", false)) {

		log(LogStatus.INFO,
				"-----Verified Task named: " + task1SubjectName[2] + " found in Tasks Object-----",
				YesNo.No);

		if (click(driver, BP.editButtonOfSubjectLinkPopUpInInteractionSection(20),
				"Edit Note Button of: " + task1SubjectName[2], action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on Edit button on Subject Link Popup", YesNo.No);

			if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null, null, null,
					null, false, null, null, null, null, null, null)) {
				log(LogStatus.PASS,
						"Activity timeline record has been Updated for Task: " + task1SubjectName[2],
						YesNo.No);
			} else {
				log(LogStatus.FAIL,
						"Activity timeline record has not Updated for task: " + task1SubjectName[2],
						YesNo.No);
				sa.assertTrue(false,
						"Activity timeline record has not Updated for task: " + task1SubjectName[2]);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on Edit button on Subjec Link Popup of Task: "
					+ task1SubjectName[2], YesNo.No);
			sa.assertTrue(false, "Not able to click on Edit button on Subjec Link Popup of Task: "
					+ task1SubjectName[2]);
		}

	} else {

		log(LogStatus.ERROR, "-----Task named: " + task1SubjectName[2] + " not found in Tasks Object-----",
				YesNo.Yes);
		BaseLib.sa.assertTrue(false,
				"-----Task named: " + task1SubjectName[2] + " not found in Tasks Object-----");

	}

		
		if (lp.clickOnTab(projectName, TabName.InstituitonsTab)) {

			log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
			ThreadSleep(3000);
		
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.InstituitonsTab , accountName[4], 10)){
			log(LogStatus.INFO, "Click on deal : " + accountName[4], YesNo.No);
			
			if (BP.clicktabOnPage(TabName.Acuity.toString())) {
				log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
				
				refresh(driver);
				ThreadSleep(5000);
				
				ArrayList<String> result1=BP.verifyRecordOnInteractionCard(advancedDate, IconType.Meeting, eventTitle8, details, false, true, null,null);
				if(result1.isEmpty())
				{
					log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+eventTitle8 , YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+eventTitle8+". "+result1 , YesNo.No);
					sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+eventTitle8+". "+result1);
				}
				} else {
					log(LogStatus.ERROR, "Not able to Click on Acuity Tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Acuity Tab");
				}
					} else {
						log(LogStatus.ERROR, "Not able to Click on firm : " + accountName[4], YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on firm : " + accountName[4]);
					}
					
					}else {
						log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
						sa.assertTrue(false,"could not click on new task button" );
					}
	
			ThreadSleep(2000);
			
			if (lp.clickOnTab(projectName, TabName.ContactTab)) {

				log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
				ThreadSleep(3000);
			
			if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , accountName[4], 10)){
				log(LogStatus.INFO, "Click on deal : " + accountName[4], YesNo.No);
				
				if (BP.clicktabOnPage(TabName.Acuity.toString())) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					
					refresh(driver);
					ThreadSleep(5000);
					
					ArrayList<String> result1=BP.verifyRecordOnInteractionCard(advancedDate, IconType.Meeting, eventTitle8, details, false, true, null,null);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+eventTitle8 , YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+eventTitle8+". "+result1 , YesNo.No);
						sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+eventTitle8+". "+result1);
					}
				} else {
					log(LogStatus.ERROR, "Not able to Click on Acuity Tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Acuity Tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to Click on firm : " + accountName[4], YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on firm : " + accountName[4]);
			}			
		}else {
			log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
			sa.assertTrue(false,"could not click on new task button" );
		}
		lp.CRMlogout();
		sa.assertAll();
		}
	
	}
