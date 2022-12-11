package com.navatar.scripts;

import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.removeNumbersFromString;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;

import java.util.ArrayList;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.*;
import com.navatar.pageObjects.*;
import com.relevantcodes.extentreports.LogStatus;

public class RGAcuityTaskAndEvent extends BaseLib {
	
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
	public void RGASTc002_CreateTaskToVerifyDataOnInteractionCard(String projectName) {

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
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);
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
}
