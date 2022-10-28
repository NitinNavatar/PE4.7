package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import com.navatar.generic.*;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.recordTypeLabel;
import com.navatar.pageObjects.*;
import com.relevantcodes.extentreports.LogStatus;

public class AcuitySmoke extends BaseLib {

	@Parameters({ "projectName" })

	@Test
	public void AcuitySmokeTc016_1_CreateCRMUser2(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser2LastName);
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
								"No new window is open after click on setup link in lighting mode so cannot create CRM User2");
						log(LogStatus.SKIP,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User2",
								YesNo.Yes);
						exit("No new window is open after click on setup link in lighting mode so cannot create CRM User2");
					}
					if (setup.createPEUser(crmUser2FirstName, UserLastName, emailId, crmUserLience, crmUserProfile)) {
						log(LogStatus.INFO,
								"CRM User is created Successfully: " + crmUser2FirstName + " " + UserLastName,
								YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User2",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name,
								"User2", excelLabel.User_Last_Name);
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
				if (setup.installedPackages(crmUser2FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + crmUser2FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in CRM User1: " + crmUser2FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in CRM User1: " + crmUser2FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in CRM User1: " + crmUser2FirstName + " " + UserLastName,
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
			e2.printStackTrace();
		}
		appLog.info("ResetLinkIs: " + passwordResetLink);
		driver.get(passwordResetLink);
		if (lp.setNewPassword()) {
			appLog.info("Password is set successfully for CRM User2: " + crmUser2FirstName + " " + UserLastName);
		} else {
			appLog.info("Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuitySmokeTc016_2_VerifyCustomNotification(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);

		String[] institutionName = "NTF Company<Break>NTF Intermedairy".split("<Break>", -1);
		String[] recordType = "Company<Break>Intermediary".split("<Break>", -1);

		String[] contactFirstNames = "NTF<Break>NTF".split("<Break>", -1);
		String[] contactLastNames = "Contact1<Break>Contact2".split("<Break>", -1);
		String[] contactEmailIds = "1investorportal+CSContact1@gmail.com<Break>1investorportal+CSContact2@gmail.com"
				.split("<Break>", -1);
		String[] contactTitles = "CTO<Break>Dev".split("<Break>", -1);

		String eventTitle = "Outlook Event";
		String eventAttendees = crmUser1EmailID + "<Break>" + crmUser1EmailID + "<Break>" + contactEmailIds[0]
				+ "<Break>" + contactEmailIds[1];
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -4);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", -4);

		String startTime = "18:00";
		String endTime = "18:30";
		String descriptionBox = "Added the event in outlook to tag users and Contacts";

		String outLookEmailCRMUser1 = crmUser1EmailID.substring(0, crmUser1EmailID.indexOf("com") + 3);
		int firmLoopCount = 0;

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create " + tabObj1 + "---------", YesNo.No);
		for (String instName : institutionName) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (ip.createInstitution(projectName, environment, mode, instName, recordType[firmLoopCount], null,
						null))

				{
					log(LogStatus.INFO, "successfully Created Account/Entity : " + instName + " of record type : "
							+ recordType[firmLoopCount], YesNo.No);
				} else {
					sa.assertTrue(false, "Not Able to Create Account/Entity : " + instName + " of record type : "
							+ recordType[firmLoopCount]);
					log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + instName + " of record type : "
							+ recordType[firmLoopCount], YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj1);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj1, YesNo.Yes);
			}
			ThreadSleep(2000);
			firmLoopCount++;
		}

		log(LogStatus.INFO, "---------Now Going to Create " + TabName.Object2Tab + "---------", YesNo.No);
		int contactLoopCount = 0;
		for (String contactFirstName : contactFirstNames) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

				if (cp.createContact(projectName, contactFirstName, contactLastNames[contactLoopCount],
						institutionName[contactLoopCount], contactEmailIds[contactLoopCount], "", null, null,
						CreationPage.ContactPage, contactTitles[contactLoopCount], null)) {
					log(LogStatus.INFO, "Successfully Created Contact : " + contactFirstName + " "
							+ contactLastNames[contactLoopCount], YesNo.No);
				}

				else {
					sa.assertTrue(false, "Not Able to Create Contact : " + contactFirstName + " "
							+ contactLastNames[contactLoopCount]);
					log(LogStatus.SKIP, "Not Able to Create Contact : " + contactFirstName + " "
							+ contactLastNames[contactLoopCount], YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
			}
			ThreadSleep(2000);
			contactLoopCount++;
		}

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);
		if (op.outLookLogin(outLookEmailCRMUser1, adminPassword)) {
			log(LogStatus.INFO, "-----Successfully Logged in to Outlook for Email: " + outLookEmailCRMUser1 + "------",
					YesNo.No);

			if (op.createEventThroughOutlook(eventTitle, eventAttendees, startDate, endDate, startTime, endTime,
					descriptionBox)) {
				log(LogStatus.INFO, "-----Event Created Msg is showing, So Event of Title: " + eventTitle
						+ " has been created-----", YesNo.No);

			}

			else {
				log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been created-----", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been created-----");
			}

		}

		else {
			log(LogStatus.ERROR,
					"-----Not Successfully Logged in to Outlook for Email: " + outLookEmailCRMUser1 + "------",
					YesNo.Yes);
			BaseLib.sa.assertTrue(false,
					"-----Not Successfully Logged in to Outlook for Email: " + outLookEmailCRMUser1 + "------");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuitySmokeTc018_VerifyThatDealsAreDisplayingAtAccountsAndContactsAcuityTabRespectively(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		String[] institutionName = "DummyCom<Break>DummyInsti<Break>DummyInt".split("<Break>", -1);
		String[] recordType = "Company<Break>Institution<Break>Intermediary".split("<Break>", -1);

		String[] contactFirstNames = "DummyAshish<Break>DummyAshish".split("<Break>", -1);
		String[] contactLastNames = "TC<Break>TC1".split("<Break>", -1);
		String[] contactEmailIds = "dtc@atc.in<Break>dtc1@atc.in".split("<Break>", -1);
		String[] otherContactLabels = "Contact Type<Break>Contact Type".split("<Break>", -1);
		String[] otherContactValues = "Banker<Break>Contact".split("<Break>", -1);
		String[] contactInstitutionsName = "DummyCom<Break>DummyInt".split("<Break>", -1);

		String[] dealRecordType = "".split("<Break>", -1);
		String[] dealName = "SmokeDummy deal".split("<Break>", -1);
		String[] dealCompany = "DummyCom".split("<Break>", -1);
		String[] dealStage = "Parked".split("<Break>", -1);
		String otherLabels = "Source Firm<Break>Source Contact<Break>Date Received";
		String otherLabelValues = "DummyInt<Break>DummyAshish TC<Break>-15";

		int firmLoopCount = 0;

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create " + tabObj1 + "---------", YesNo.No);
		for (String instName : institutionName) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (ip.createInstitution(projectName, environment, mode, instName, recordType[firmLoopCount], null,
						null))

				{
					log(LogStatus.INFO, "successfully Created Account/Entity : " + instName + " of record type : "
							+ recordType[firmLoopCount], YesNo.No);
				} else {
					sa.assertTrue(false, "Not Able to Create Account/Entity : " + instName + " of record type : "
							+ recordType[firmLoopCount]);
					log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + instName + " of record type : "
							+ recordType[firmLoopCount], YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj1);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj1, YesNo.Yes);
			}
			ThreadSleep(2000);
			firmLoopCount++;
		}

		log(LogStatus.INFO, "---------Now Going to Create " + TabName.Object2Tab + "---------", YesNo.No);
		int contactLoopCount = 0;
		for (String contactFirstName : contactFirstNames) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

				if (cp.createContact(projectName, contactFirstName, contactLastNames[contactLoopCount],
						contactInstitutionsName[contactLoopCount], contactEmailIds[contactLoopCount], "",
						otherContactLabels[contactLoopCount], otherContactValues[contactLoopCount],
						CreationPage.ContactPage, null, null)) {
					log(LogStatus.INFO, "Successfully Created Contact : " + contactFirstName + " "
							+ contactLastNames[contactLoopCount], YesNo.No);
				}

				else {
					sa.assertTrue(false, "Not Able to Create Contact : " + contactFirstName + " "
							+ contactLastNames[contactLoopCount]);
					log(LogStatus.SKIP, "Not Able to Create Contact : " + contactFirstName + " "
							+ contactLastNames[contactLoopCount], YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
			}
			ThreadSleep(2000);
			contactLoopCount++;
		}

		log(LogStatus.INFO, "---------Now Going to Create " + tabObj4 + "---------", YesNo.No);
		for (int i = 0; i < dealName.length; i++) {
			if (lp.clickOnTab(projectName, tabObj4)) {
				log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
				ThreadSleep(3000);
				if (dp.createDeal(dealRecordType[i], dealName[i], dealCompany[i], dealStage[i], otherLabels,
						otherLabelValues)) {
					log(LogStatus.INFO, dealName[i] + " deal has been created", YesNo.No);

				} else {
					log(LogStatus.ERROR, dealName[i] + " deal is not created", YesNo.No);
					sa.assertTrue(false, dealName[i] + " deal is not created");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
			}
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuitySmokeTc019_VerifyThatDealNameIsClickableAtFAAndCATab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String firmName = "DummyCom";
		String subTabName = "Acuity";
		String dealName = "SmokeDummy deal";

		String contactName = "DummyAshish TC";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Check Redirection for Deal Name: " + dealName
				+ "in Acuity Tab of Firm: " + firmName + "---------", YesNo.No);

		if (BP.clickOnTab(projectName, tabObj1))

		{
			if (BP.clickOnAlreadyCreatedItem(projectName, firmName, TabName.InstituitonsTab, 50)) {
				log(LogStatus.INFO, "successfully open the record : " + firmName, YesNo.No);
				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on Tab: " + subTabName, YesNo.No);

					if (BP.dealNameLinkInAcuityTab(dealName, 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and it is Clickable", YesNo.No);

						if (clickUsingJavaScript(driver, BP.dealNameLinkInAcuityTab(dealName, 30),
								"Deal Name: " + dealName, action.BOOLEAN)) {
							log(LogStatus.PASS, "Clicked on Deal Name: " + dealName, YesNo.No);
							try {
								String parentWindowId = CommonLib.switchOnWindow(driver);
								if (!parentWindowId.isEmpty()) {
									log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName,
											YesNo.No);

									if (BP.dealRecordPage(dealName, 20) != null) {
										log(LogStatus.PASS, "----Deal Detail Page is redirecting for Deal Record: "
												+ dealName + "-----", YesNo.No);
										driver.close();
										driver.switchTo().window(parentWindowId);

									} else {
										log(LogStatus.FAIL, "----Deal Detail Page is not redirecting for Deal Record: "
												+ dealName + "-----", YesNo.Yes);
										sa.assertTrue(false, "----Deal Detail Page is not showing for Deal Record: "
												+ dealName + "-----");
										driver.close();
										driver.switchTo().window(parentWindowId);

									}

								} else {
									log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + dealName,
											YesNo.Yes);
									sa.assertTrue(false, "No New Window Open after click on Deal Link: " + dealName);
								}
							} catch (Exception e) {
								log(LogStatus.FAIL,
										"Not able to switch to window after click on Deal Link, Msg showing: "
												+ e.getMessage(),
										YesNo.Yes);
								sa.assertTrue(false,
										"Not able to switch to window after click on Deal Link, Msg showing: "
												+ e.getMessage());
							}
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}

					} else {
						log(LogStatus.FAIL, "Deal Name: " + dealName + " is not hyperlink and it is not Clickable",
								YesNo.Yes);
						sa.assertTrue(false, "Deal Name: " + dealName + " is not hyperlink and it is not Clickable");
					}

				} else {
					log(LogStatus.FAIL, "Not Able to Click on Tab: " + subTabName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + subTabName);
				}

			} else {
				log(LogStatus.FAIL, "Not Able to open the record : " + firmName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to open the record : " + firmName);

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		log(LogStatus.INFO, "---------Now Going to Check Redirection for Deal Name: " + dealName
				+ "in Acuity Tab of Contact: " + contactName + "---------", YesNo.No);

		if (BP.clickOnTab(projectName, tabObj2))

		{
			if (BP.clickOnAlreadyCreatedItem(projectName, contactName, TabName.ContactTab, 50)) {
				log(LogStatus.INFO, "successfully open the record : " + contactName, YesNo.No);
				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on Tab: " + subTabName, YesNo.No);

					if (BP.dealNameLinkInAcuityTab(dealName, 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and it is Clickable", YesNo.No);

						if (clickUsingJavaScript(driver, BP.dealNameLinkInAcuityTab(dealName, 30),
								"Deal Name: " + dealName, action.BOOLEAN)) {
							log(LogStatus.PASS, "Clicked on Deal Name: " + dealName, YesNo.No);
							try {
								String parentWindowId = CommonLib.switchOnWindow(driver);
								if (!parentWindowId.isEmpty()) {
									log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName,
											YesNo.No);

									if (BP.dealRecordPage(dealName, 20) != null) {
										log(LogStatus.PASS, "----Deal Detail Page is redirecting for Deal Record: "
												+ dealName + "-----", YesNo.No);
										driver.close();
										driver.switchTo().window(parentWindowId);

									} else {
										log(LogStatus.FAIL, "----Deal Detail Page is not redirecting for Deal Record: "
												+ dealName + "-----", YesNo.Yes);
										sa.assertTrue(false, "----Deal Detail Page is not showing for Deal Record: "
												+ dealName + "-----");
										driver.close();
										driver.switchTo().window(parentWindowId);

									}

								} else {
									log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + dealName,
											YesNo.Yes);
									sa.assertTrue(false, "No New Window Open after click on Deal Link: " + dealName);
								}
							} catch (Exception e) {
								log(LogStatus.FAIL,
										"Not able to switch to window after click on Deal Link, Msg showing: "
												+ e.getMessage(),
										YesNo.Yes);
								sa.assertTrue(false,
										"Not able to switch to window after click on Deal Link, Msg showing: "
												+ e.getMessage());
							}
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}

					} else {
						log(LogStatus.FAIL, "Deal Name: " + dealName + " is not hyperlink and it is not Clickable",
								YesNo.Yes);
						sa.assertTrue(false, "Deal Name: " + dealName + " is not hyperlink and it is not Clickable");
					}

				} else {
					log(LogStatus.FAIL, "Not Able to Click on Tab: " + subTabName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + subTabName);
				}

			} else {
				log(LogStatus.FAIL, "Not Able to open the record : " + contactName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to open the record : " + contactName);

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on firm tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on firm tab");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuitySmokeTc020_VerifyTheImpactOnDealCountForContactAtContactGridAtFATab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);

		String firmName = "DummyInt";
		String subTabName = "Acuity";
		String dealName = "SmokeDummy deal";

		String contactName = "DummyAshish TC1";
		String dealTeamMember = crmUser1FirstName + " " + crmUser1LastName;
		String company = "DummyCom";
		String stage = "Parked";
		String dateReceived = "9/28/2022";

		String[][] data = { { PageLabel.Team_Member.toString(), dealTeamMember },
				{ PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName } };

		String beforeDealCountInFirm = "0";
		String afterDealCountInFirm = "1";
		String actualDealCount = null;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Check Deal Team Count should get increase by one for Contact named "
				+ contactName + " at Firm Tab under Acuity section---------", YesNo.No);

		if (BP.clickOnTab(projectName, tabObj1))

		{
			if (BP.clickOnAlreadyCreatedItem(projectName, firmName, TabName.InstituitonsTab, 50)) {
				log(LogStatus.INFO, "successfully open the record : " + firmName, YesNo.No);
				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on Tab: " + subTabName, YesNo.No);

					actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
							action.SCROLLANDBOOLEAN);
					if (BP.contactDealCount(contactName, 30) != null) {
						if (!actualDealCount.equalsIgnoreCase("")) {

							if (actualDealCount.equalsIgnoreCase(beforeDealCountInFirm)) {
								log(LogStatus.INFO,
										"Deal Count for Contact: " + contactName + " is " + actualDealCount
												+ " before Deal Team Create is matched to " + beforeDealCountInFirm,
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Deal Count for Contact: " + contactName
												+ " is before Deal Team Create is not matched, Expected: "
												+ beforeDealCountInFirm + " but Actual: " + actualDealCount,
										YesNo.Yes);
								sa.assertTrue(false,
										"Deal Count for Contact: " + contactName
												+ " is before Deal Team Create is not matched, Expected: "
												+ beforeDealCountInFirm + " but Actual: " + actualDealCount);
							}

						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName);
						}
					} else {
						log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
						sa.assertTrue(false, "No Contact found of Name: " + contactName);
					}

				} else {
					log(LogStatus.FAIL, "Not Able to Click on Tab: " + subTabName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + subTabName);
				}

			} else {
				log(LogStatus.FAIL, "Not Able to open the record : " + firmName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to open the record : " + firmName);

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		log(LogStatus.INFO, "---------Now Going to Create Deal Team for Deal: " + dealName
				+ " to check the count in Deal Team record for Acuity Tab under Firm: " + firmName + "---------",
				YesNo.No);
		BP.openAppFromAppLauchner("Deal Team", 30);
		CommonLib.ThreadSleep(3000);
		CommonLib.refresh(driver);
		if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);

			if (BP.clickOnTab(projectName, tabObj1)) {
				if (BP.clickOnAlreadyCreatedItem(projectName, firmName, TabName.InstituitonsTab, 50)) {
					log(LogStatus.INFO, "successfully open the record : " + firmName, YesNo.No);
					if (BP.clicktabOnPage(subTabName)) {
						log(LogStatus.PASS, "Clicked on Tab: " + subTabName, YesNo.No);

						ArrayList<String> result1 = BP.verifyRecordOnContactSectionAcuity(contactName, null,
								afterDealCountInFirm, null, null);
						if (result1.isEmpty()) {
							log(LogStatus.INFO, "Records on Contact slot have been matched", YesNo.No);

						} else {
							log(LogStatus.ERROR, "Records on Contact slot are not matched, result: " + result1,
									YesNo.No);
							sa.assertTrue(false, "Records on Contact slot are not matched, result: " + result1);
						}

						ArrayList<String> result2 = BP.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactName,
								dealTeamMember, null, afterDealCountInFirm, null, null);
						if (result2.isEmpty()) {
							log(LogStatus.INFO, "Records on Connections of Contact slot have been matched", YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Records on Connections of Contact slot are not matched, Reason: " + result2,
									YesNo.No);
							sa.assertTrue(false,
									"Records on Connections of Contact slot are not matched, Reason" + result2);
						}

					} else {
						log(LogStatus.FAIL, "Not Able to Click on Tab: " + subTabName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on Tab: " + subTabName);
					}

				} else {
					log(LogStatus.FAIL, "Not Able to open the record : " + firmName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to open the record : " + firmName);

				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count under Connections section should get increase by one for Contact named "
							+ contactName + " at Contact Tab under Acuity section---------",
					YesNo.No);

			if (BP.clickOnTab(projectName, tabObj2))

			{
				if (BP.clickOnAlreadyCreatedItem(projectName, contactName, TabName.ContactTab, 50)) {
					log(LogStatus.INFO, "successfully open the record : " + contactName, YesNo.No);
					if (BP.clicktabOnPage(subTabName)) {
						log(LogStatus.PASS, "Clicked on Tab: " + subTabName, YesNo.No);

						ArrayList<String> result2 = BP.verifyRecordOnConnectionsSectionInAcuity(contactName,
								dealTeamMember, null, afterDealCountInFirm, null, null);
						if (result2.isEmpty()) {
							log(LogStatus.INFO, "Records on Connections of Contact slot have been matched", YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Records on Connections of Contact slot are not matched, Reason: " + result2,
									YesNo.No);
							sa.assertTrue(false,
									"Records on Connections of Contact slot are not matched, Reason" + result2);
						}

						ArrayList<String> result3 = BP.verifyRecordOnDealsSectionInAcuity(contactName, dealName,
								company, stage, dateReceived);
						if (result3.isEmpty()) {
							log(LogStatus.INFO, "Records on Deals slot have been matched", YesNo.No);

						} else {
							log(LogStatus.ERROR, "Records on Deals slot are not matched, Reason: " + result3, YesNo.No);
							sa.assertTrue(false, "Records on Deals slot are not matched, Reason" + result3);
						}

					} else {
						log(LogStatus.FAIL, "Not Able to Click on Tab: " + subTabName, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on Tab: " + subTabName);
					}

				} else {
					log(LogStatus.FAIL, "Not Able to open the record : " + contactName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to open the record : " + contactName);

				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
			}

		} else {
			log(LogStatus.FAIL, "----Not Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.Yes);
			sa.assertTrue(false, "----Not Successfully Created the Deal Team for Deal: " + dealName + "----");

		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuitySmokeTc021_VerifyThatDealCountIsClickableAtFAAndCATab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String firmName = "DummyInt";
		String subTabName = "Acuity";
		String dealName = "SmokeDummy deal";

		String contactName = "DummyAshish TC1";
		String company = "DummyCom";
		String stage = "Parked";
		String dateReceived = "9/28/2022";
		String teamMemberName = crmUser1FirstName + " " + crmUser1LastName;

		String actualDealCount = null;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Check Deal Team Count is clickable and verify record after popup open for Contact named "
						+ contactName + " at Firm Tab under Acuity section---------",
				YesNo.No);

		if (BP.clickOnTab(projectName, tabObj1))

		{
			if (BP.clickOnAlreadyCreatedItem(projectName, firmName, TabName.InstituitonsTab, 50)) {
				log(LogStatus.INFO, "successfully open the record : " + firmName, YesNo.No);
				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on Tab: " + subTabName, YesNo.No);

					actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
							action.SCROLLANDBOOLEAN);
					if (BP.contactDealCount(contactName, 30) != null) {
						if (!actualDealCount.equalsIgnoreCase("")) {

							if (CommonLib.click(driver, BP.contactDealCount(contactName, 30),
									"Deal Count: " + actualDealCount, action.BOOLEAN)) {
								log(LogStatus.INFO,
										"Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName,
										YesNo.No);

								ArrayList<String> result1 = BP.verifyRecordOnDealsPopUpSectionInAcuity(contactName,
										dealName, company, stage, dateReceived);
								if (result1.isEmpty()) {
									log(LogStatus.INFO, "Records on Deals slot have been matched", YesNo.No);

								} else {
									log(LogStatus.ERROR, "Records on Deals slot are not matched, Reason: " + result1,
											YesNo.No);
									sa.assertTrue(false, "Records on Deals slot are not matched, Reason" + result1);
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Deal Count: " + actualDealCount
										+ " of Record: " + contactName, YesNo.No);

								sa.assertTrue(false, "Not Able to Click on Deal Count: " + actualDealCount
										+ " of Record: " + contactName);

							}

						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName);
						}
					} else {
						log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
						sa.assertTrue(false, "No Contact found of Name: " + contactName);
					}

				} else {
					log(LogStatus.FAIL, "Not Able to Click on Tab: " + subTabName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + subTabName);
				}

			} else {
				log(LogStatus.FAIL, "Not Able to open the record : " + firmName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to open the record : " + firmName);

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		log(LogStatus.INFO,
				"---------Now Going to Check Deal Team Count under Connections section is clickable anverify record after popup open for Contact named "
						+ contactName + " at Contact Tab under Acuity section---------",
				YesNo.No);

		if (BP.clickOnTab(projectName, tabObj2))

		{
			if (BP.clickOnAlreadyCreatedItem(projectName, contactName, TabName.ContactTab, 50)) {
				log(LogStatus.INFO, "successfully open the record : " + contactName, YesNo.No);
				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on Tab: " + subTabName, YesNo.No);

					actualDealCount = getText(driver, BP.teamMemberDealCount(teamMemberName, 20), "deal",
							action.SCROLLANDBOOLEAN);
					if (BP.teamMemberDealCount(teamMemberName, 20) != null) {
						if (!actualDealCount.equalsIgnoreCase("")) {

							if (CommonLib.click(driver, BP.teamMemberDealCount(teamMemberName, 20),
									"Deal Count: " + actualDealCount, action.BOOLEAN)) {
								log(LogStatus.INFO,
										"Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName,
										YesNo.No);

								ArrayList<String> result1 = BP.verifyRecordOnDealsPopUpSectionInAcuity(contactName,
										dealName, company, stage, dateReceived);
								if (result1.isEmpty()) {
									log(LogStatus.INFO, "Records on Deals slot have been matched", YesNo.No);

								} else {
									log(LogStatus.ERROR, "Records on Deals slot are not matched, Reason: " + result1,
											YesNo.No);
									sa.assertTrue(false, "Records on Deals slot are not matched, Reason" + result1);
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Deal Count: " + actualDealCount
										+ " of Record: " + contactName, YesNo.No);

								sa.assertTrue(false, "Not Able to Click on Deal Count: " + actualDealCount
										+ " of Record: " + contactName);

							}

						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName);
						}
					} else {
						log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
						sa.assertTrue(false, "No Contact found of Name: " + contactName);
					}

				} else {
					log(LogStatus.FAIL, "Not Able to Click on Tab: " + subTabName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + subTabName);
				}

			} else {
				log(LogStatus.FAIL, "Not Able to open the record : " + contactName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to open the record : " + contactName);

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuitySmokeTc022_VerifyThatDealNameIsClickableAtDealDetailPopUpForFAAndCATab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String firmName = "DummyInt";
		String subTabName = "Acuity";
		String dealName = "SmokeDummy deal";

		String contactName = "DummyAshish TC1";
		String teamMember = crmUser1FirstName + " " + crmUser1LastName;

		String actualDealCount = null;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Check Deal Team Count Popup open and after click on Deal Named: "
				+ dealName + " at Firm Tab under Acuity section is redirecting to Deal Record Page---------", YesNo.No);

		if (BP.clickOnTab(projectName, tabObj1))

		{
			if (BP.clickOnAlreadyCreatedItem(projectName, firmName, TabName.InstituitonsTab, 50)) {
				log(LogStatus.INFO, "successfully open the record : " + firmName, YesNo.No);
				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on Tab: " + subTabName, YesNo.No);

					actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
							action.SCROLLANDBOOLEAN);
					if (BP.contactDealCount(contactName, 30) != null) {
						if (!actualDealCount.equalsIgnoreCase("")) {

							if (clickUsingJavaScript(driver, BP.contactDealCount(contactName, 30),
									"Deal Count of Contact: " + contactName, action.BOOLEAN)) {
								log(LogStatus.PASS, "Clicked on Deal Count of Contact: " + contactName, YesNo.No);

								if (clickUsingJavaScript(driver, BP.dealAcuityPopUpDealName(dealName, 20),
										"Deal Name: " + dealName, action.BOOLEAN)) {
									log(LogStatus.PASS, "Clicked on Deal Name: " + dealName, YesNo.No);
									try {
										String parentWindowId = CommonLib.switchOnWindow(driver);
										if (!parentWindowId.isEmpty()) {
											log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName,
													YesNo.No);

											if (BP.dealRecordPage(dealName, 20) != null) {
												log(LogStatus.PASS,
														"----Deal Detail Page is redirecting for Deal Record: "
																+ dealName + "-----",
														YesNo.No);
												driver.close();
												driver.switchTo().window(parentWindowId);

											} else {
												log(LogStatus.FAIL,
														"----Deal Detail Page is not redirecting for Deal Record: "
																+ dealName + "-----",
														YesNo.Yes);
												sa.assertTrue(false,
														"----Deal Detail Page is not showing for Deal Record: "
																+ dealName + "-----");
												driver.close();
												driver.switchTo().window(parentWindowId);

											}

										} else {
											log(LogStatus.FAIL,
													"No New Window Open after click on Deal Link: " + dealName,
													YesNo.Yes);
											sa.assertTrue(false,
													"No New Window Open after click on Deal Link: " + dealName);
										}
									} catch (Exception e) {
										log(LogStatus.FAIL,
												"Not able to switch to window after click on Deal Link, Msg showing: "
														+ e.getMessage(),
												YesNo.Yes);
										sa.assertTrue(false,
												"Not able to switch to window after click on Deal Link, Msg showing: "
														+ e.getMessage());
									}
								} else {
									log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

								}

							} else {
								log(LogStatus.FAIL, "Not able to Click on Deal Count of Contact: " + contactName,
										YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Deal Count of Contact: " + contactName);

							}

						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName);
						}
					} else {
						log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
						sa.assertTrue(false, "No Contact found of Name: " + contactName);
					}

				} else {
					log(LogStatus.FAIL, "Not Able to Click on Tab: " + subTabName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + subTabName);
				}

			} else {
				log(LogStatus.FAIL, "Not Able to open the record : " + firmName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to open the record : " + firmName);

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		log(LogStatus.INFO, "---------Now Going to Check Connection Popup open and  Deal Named: " + dealName
				+ " at Firm Tab under Acuity section is redirecting to Deal Record Page, popup gets open after click on User Icon---------",
				YesNo.No);

		if (BP.clickOnTab(projectName, tabObj1)) {
			if (BP.clickOnAlreadyCreatedItem(projectName, firmName, TabName.InstituitonsTab, 50)) {
				log(LogStatus.INFO, "successfully open the record : " + firmName, YesNo.No);
				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on Tab: " + subTabName, YesNo.No);

					if (contactName != null && contactName != "") {

						if (BP.contactNameUserIconButton(contactName, 30) != null) {

							if (click(driver, BP.contactNameUserIconButton(contactName, 30),
									"Contact Name: " + contactName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Contact: " + contactName, YesNo.No);

								if (clickUsingJavaScript(driver, BP.connectionPopUpTeamMember(teamMember, 20),
										"Deal Name: " + dealName, action.BOOLEAN)) {
									log(LogStatus.PASS, "Clicked on Team Member Name: " + teamMember, YesNo.No);
									try {
										String parentWindowId = CommonLib.switchOnWindow(driver);
										if (!parentWindowId.isEmpty()) {
											log(LogStatus.PASS,
													"New Window Open after click on Team Member Link: " + teamMember,
													YesNo.No);

											if (home.getUserNameHeader(teamMember, 30) != null) {
												log(LogStatus.PASS,
														"----User Detail Page is redirecting for Team Member Record: "
																+ teamMember + "-----",
														YesNo.No);
												driver.close();
												driver.switchTo().window(parentWindowId);

											} else {
												log(LogStatus.FAIL,
														"----User Detail Page is not redirecting for Team Member Record: "
																+ teamMember + "-----",
														YesNo.Yes);
												sa.assertTrue(false,
														"----User Detail Page is not redirecting for Team Member Record: "
																+ teamMember + "-----");
												driver.close();
												driver.switchTo().window(parentWindowId);

											}

										} else {
											log(LogStatus.FAIL,
													"No New Window Open after click on Team Member Link: " + teamMember,
													YesNo.Yes);
											sa.assertTrue(false, "No New Window Open after click on Team Member Link: "
													+ teamMember);
										}
									} catch (Exception e) {
										log(LogStatus.FAIL,
												"Not able to switch to window after click on Team Member Link, Msg showing: "
														+ e.getMessage(),
												YesNo.Yes);
										sa.assertTrue(false,
												"Not able to switch to window after click on Team Member Link, Msg showing: "
														+ e.getMessage());
									}
								} else {
									log(LogStatus.FAIL, "Not able to Click on Team Member Name: " + teamMember,
											YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on Team Member Name: " + teamMember);

								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Contact: " + contactName, YesNo.No);
								sa.assertTrue(false, "Not Able to Click on Contact: " + contactName);

							}
						} else {
							log(LogStatus.ERROR,
									contactName + " is not avalable in contact section, So Can not Click on User Icon",
									YesNo.No);
							sa.assertTrue(false,
									contactName + " is not avalable in contact section, So Can not Click on User Icon");

						}
					} else {
						log(LogStatus.ERROR, "Provided Contact Name should not be null in DataSheet", YesNo.No);
						sa.assertTrue(false, "Provided Contact Name should not be null in DataSheet");

					}

				} else {
					log(LogStatus.FAIL, "Not Able to Click on Tab: " + subTabName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + subTabName);
				}

			} else {
				log(LogStatus.FAIL, "Not Able to open the record : " + firmName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to open the record : " + firmName);

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		log(LogStatus.INFO, "---------Now Going to Check Deal Team Count Popup open and after click on Deal Named: "
				+ dealName + " at Contact Tab under Acuity section is redirecting to Deal Record Page---------",
				YesNo.No);

		if (BP.clickOnTab(projectName, tabObj2))

		{
			if (BP.clickOnAlreadyCreatedItem(projectName, contactName, TabName.ContactTab, 50)) {
				log(LogStatus.INFO, "successfully open the record : " + contactName, YesNo.No);
				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on Tab: " + subTabName, YesNo.No);

					actualDealCount = getText(driver, BP.teamMemberDealCount(teamMember, 20), "deal",
							action.SCROLLANDBOOLEAN);
					if (BP.teamMemberDealCount(teamMember, 20) != null) {
						if (!actualDealCount.equalsIgnoreCase("")) {

							if (CommonLib.click(driver, BP.teamMemberDealCount(teamMember, 20),
									"Deal Count: " + actualDealCount, action.BOOLEAN)) {
								log(LogStatus.INFO,
										"Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName,
										YesNo.No);

								if (clickUsingJavaScript(driver, BP.dealAcuityPopUpDealName(dealName, 20),
										"Deal Name: " + dealName, action.BOOLEAN)) {
									log(LogStatus.PASS, "Clicked on Deal Name: " + dealName, YesNo.No);
									try {
										String parentWindowId = CommonLib.switchOnWindow(driver);
										if (!parentWindowId.isEmpty()) {
											log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName,
													YesNo.No);

											if (BP.dealRecordPage(dealName, 20) != null) {
												log(LogStatus.PASS,
														"----Deal Detail Page is redirecting for Deal Record: "
																+ dealName + "-----",
														YesNo.No);
												driver.close();
												driver.switchTo().window(parentWindowId);

											} else {
												log(LogStatus.FAIL,
														"----Deal Detail Page is not redirecting for Deal Record: "
																+ dealName + "-----",
														YesNo.Yes);
												sa.assertTrue(false,
														"----Deal Detail Page is not showing for Deal Record: "
																+ dealName + "-----");
												driver.close();
												driver.switchTo().window(parentWindowId);

											}

										} else {
											log(LogStatus.FAIL,
													"No New Window Open after click on Deal Link: " + dealName,
													YesNo.Yes);
											sa.assertTrue(false,
													"No New Window Open after click on Deal Link: " + dealName);
										}
									} catch (Exception e) {
										log(LogStatus.FAIL,
												"Not able to switch to window after click on Deal Link, Msg showing: "
														+ e.getMessage(),
												YesNo.Yes);
										sa.assertTrue(false,
												"Not able to switch to window after click on Deal Link, Msg showing: "
														+ e.getMessage());
									}
								} else {
									log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Deal Count: " + actualDealCount
										+ " of Record: " + contactName, YesNo.No);

								sa.assertTrue(false, "Not Able to Click on Deal Count: " + actualDealCount
										+ " of Record: " + contactName);

							}

						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName);
						}
					} else {
						log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
						sa.assertTrue(false, "No Contact found of Name: " + contactName);
					}

				} else {
					log(LogStatus.FAIL, "Not Able to Click on Tab: " + subTabName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + subTabName);
				}

			} else {
				log(LogStatus.FAIL, "Not Able to open the record : " + contactName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to open the record : " + contactName);

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuitySmokeTc023_VerifyTheImapactOfNewlyCreatedFundraisingOnInstitutionTypeFirm(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);

		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);

		String subTabName = "Acuity";

		String[] fundNames = "SD Fund".split("<Break>", -1);
		String[] fundTypes = "Fund".split("<Break>", -1);
		String[] investmentCategories = "Fund".split("<Break>", -1);
		String otherLabelFields = null;
		String otherLabelValues = null;

		String ClosingDate = "9/26/2022";
		String[] fundraisingNames = "SD Fundraising".split("<Break>", -1);
		String[] fundraisingsFundName = "SD Fund".split("<Break>", -1);
		String[] fundraisingsInstitutionName = "DummyInsti".split("<Break>", -1);
		String[] fundraisingsStageName = "Prospect".split("<Break>", -1);
		String[] fundraisingsClosingDate = convertDateFromOneFormatToAnother(ClosingDate, "MM/dd/yyyy", "dd/MMM/yyyy")
				.split("<Break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		int fundStatus = 0;
		int fundLoopCount = 0;
		for (String fundName : fundNames) {

			log(LogStatus.INFO, "---------Now Going to Create Fund Named: " + fundName + "---------", YesNo.No);
			if (fund.clickOnTab(environment, mode, TabName.FundsTab)) {

				if (fund.createFund(projectName, fundName, fundTypes[fundLoopCount],
						investmentCategories[fundLoopCount], otherLabelFields, otherLabelValues)) {
					appLog.info("Fund is created Successfully: " + fundName);
					fundStatus++;

				} else {
					appLog.error("Not able to click on fund: " + fundName);
					sa.assertTrue(false, "Not able to click on fund: " + fundName);
					log(LogStatus.ERROR, "Not able to click on fund: " + fundName, YesNo.Yes);
				}
			} else {
				appLog.error("Not able to click on Fund tab so cannot create Fund: " + fundName);
				sa.assertTrue(false, "Not able to click on Fund tab so cannot create Fund: " + fundName);
			}
			ThreadSleep(2000);
			fundLoopCount++;

		}

		if (fundStatus == fundLoopCount) {
			int fundraisingLoopCount = 0;
			for (String fundraisingName : fundraisingNames) {
				log(LogStatus.INFO, "---------Now Going to Create Fundraising Named: " + fundraisingName + "---------",
						YesNo.No);
				if (BP.clickOnTab(environment, mode, TabName.FundraisingsTab)) {

					String[] targetClosingCompleteDate = fundraisingsClosingDate[fundraisingLoopCount].split("/");
					String targetClosingDate = targetClosingCompleteDate[0];
					String targetClosingMonth = targetClosingCompleteDate[1];
					String targetClosingYear = targetClosingCompleteDate[2];

					if (fr.createFundRaising(environment, "Lightning", fundraisingName,
							fundraisingsFundName[fundraisingLoopCount],
							fundraisingsInstitutionName[fundraisingLoopCount], null,
							fundraisingsStageName[fundraisingLoopCount], null, null, targetClosingYear,
							targetClosingMonth, targetClosingDate)) {
						appLog.info("fundraising is created : " + fundraisingName);
					} else {
						appLog.error("Not able to create fundraising: " + fundraisingName);
						sa.assertTrue(false, "Not able to create fundraising: " + fundraisingName);
					}

				} else {
					appLog.error(
							"Not able to click on fundraising tab so cannot create fundraising: " + fundraisingName);
					sa.assertTrue(false,
							"Not able to click on fundraising tab so cannot create fundraising: " + fundraisingName);
				}
				ThreadSleep(2000);

				fundraisingLoopCount++;

			}

		} else {
			appLog.error("No Fund is created, So not able to Create Fundraising: " + fundraisingNames);
			sa.assertTrue(false, "No Fund is created, So not able to Create Fundraising: " + fundraisingNames);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Fundraising Record Named: " + fundraisingNames[0]
						+ " under Fundraising Section comes under Acuity Section of Record Named: "
						+ fundraisingsInstitutionName[0] + "---------",
				YesNo.No);

		if (BP.clickOnTab(projectName, tabObj1))

		{
			if (BP.clickOnAlreadyCreatedItem(projectName, fundraisingsInstitutionName[0], TabName.InstituitonsTab,
					50)) {
				log(LogStatus.INFO, "successfully open the record : " + fundraisingsInstitutionName[0], YesNo.No);
				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on Tab: " + subTabName, YesNo.No);

					ArrayList<String> result1 = BP.verifyRecordOnFundraisingsSectionInAcuity(
							fundraisingsInstitutionName[0], fundraisingNames[0], fundraisingsFundName[0],
							fundraisingsStageName[0], ClosingDate);
					if (result1.isEmpty()) {
						log(LogStatus.INFO, "Records on Fundraisings slot have been matched", YesNo.No);

					} else {
						log(LogStatus.ERROR, "Records on Fundraisings slot are not matched, Reason: " + result1,
								YesNo.No);
						sa.assertTrue(false, "Records on Fundraisings slot are not matched, Reason" + result1);
					}

				} else {
					log(LogStatus.FAIL, "Not Able to Click on Tab: " + subTabName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + subTabName);
				}

			} else {
				log(LogStatus.FAIL, "Not Able to open the record : " + fundraisingsInstitutionName[0], YesNo.Yes);
				sa.assertTrue(false, "Not Able to open the record : " + fundraisingsInstitutionName[0]);

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuitySmokeTc027_VerifyCreateNavigationMenu(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String subTabName = "Acuity";

		String[] institutionName = "Nav Cmp<Break>Nav Ins".split("<Break>", -1);
		String[] recordType = "Company<Break>Institution".split("<Break>", -1);

		String[] contactFirstNames = "Nav<Break>Nav".split("<Break>", -1);
		String[] contactLastNames = "Cont1<Break>Cont2".split("<Break>", -1);
		String[] contactEmailIds = "1investorportal+nvContact1@gmail.com<Break>1investorportal+nvContact2@gmail.com"
				.split("<Break>", -1);
		String[] otherContactLabels = "Title<Break>Title".split("<Break>", -1);
		String[] otherContactValues = "CTO<Break>Dev".split("<Break>", -1);

		String[][] callBasicSection = { { "Subject", "Nav Call" }, { "Notes", "Call from Navigation" },
				{ "Related_To", "Nav Cmp<break>Nav Ins<break>Nav Cont1" } };
		String[][] meetingBasicSection = { { "Subject", "Nav Meeting" }, { "Notes", "Meeting from Navigation" },
				{ "Related_To", "Nav Cmp<break>Nav Ins<break>Nav Cont1" } };

		String[][] taskBasicSection = { { "Subject", "Nav Task" }, { "Notes", "Task from Navigation" },
				{ "Related_To", "Nav Cmp<break>Nav Ins<break>Nav Cont1" } };
		List<String> activityTimeLines = new ArrayList<String>();
		activityTimeLines.add("Nav Call");
		activityTimeLines.add("Nav Meeting");
		activityTimeLines.add("Nav Task");

		int firmLoopCount = 0;

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create " + tabObj1 + "---------", YesNo.No);
		home.notificationPopUpClose();
		for (String instName : institutionName) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (ip.createInstitution(projectName, environment, mode, instName, recordType[firmLoopCount], null,
						null))

				{
					log(LogStatus.INFO, "successfully Created Account/Entity : " + instName + " of record type : "
							+ recordType[firmLoopCount], YesNo.No);
				} else {
					sa.assertTrue(false, "Not Able to Create Account/Entity : " + instName + " of record type : "
							+ recordType[firmLoopCount]);
					log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + instName + " of record type : "
							+ recordType[firmLoopCount], YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj1);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj1, YesNo.Yes);
			}
			ThreadSleep(2000);
			firmLoopCount++;
		}

		log(LogStatus.INFO, "---------Now Going to Create " + TabName.Object2Tab + "---------", YesNo.No);
		int contactLoopCount = 0;
		for (String contactFirstName : contactFirstNames) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

				if (cp.createContact(projectName, contactFirstName, contactLastNames[contactLoopCount],
						institutionName[contactLoopCount], contactEmailIds[contactLoopCount], "",
						otherContactLabels[contactLoopCount], otherContactValues[contactLoopCount],
						CreationPage.ContactPage, null, null)) {
					log(LogStatus.INFO, "Successfully Created Contact : " + contactFirstName + " "
							+ contactLastNames[contactLoopCount], YesNo.No);
				}

				else {
					sa.assertTrue(false, "Not Able to Create Contact : " + contactFirstName + " "
							+ contactLastNames[contactLoopCount]);
					log(LogStatus.SKIP, "Not Able to Create Contact : " + contactFirstName + " "
							+ contactLastNames[contactLoopCount], YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
			}
			ThreadSleep(2000);
			contactLoopCount++;
		}

		log(LogStatus.INFO, "---------Now Going to Create Call in Activity Timeline Section---------", YesNo.No);
		CommonLib.refresh(driver);
		if (BP.createActivityTimeline(projectName, true, "Call", callBasicSection, null, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			ThreadSleep(4000);
			if (CommonLib.getText(driver, BP.taskDetailPageHeader(15), "Call: " + callBasicSection, action.BOOLEAN)
					.equalsIgnoreCase("Nav Call")) {
				log(LogStatus.PASS, "-----Task Detail Page: " + "Nav Call" + " has been open after Call Create-----",
						YesNo.No);
			}

			else {
				log(LogStatus.FAIL,
						"-----Task Detail Page: " + "Nav Call" + " has not been open after Call Create-----", YesNo.No);
				sa.assertTrue(false,
						"-----Task Detail Page: " + "Nav Call" + " has not been open after Call Create-----");
			}

		} else {
			log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created");
		}

		log(LogStatus.INFO, "---------Now Going to Create Meeting in Activity Timeline Section---------", YesNo.No);
		CommonLib.refresh(driver);
		if (BP.createActivityTimeline(projectName, true, "Meeting", meetingBasicSection, null, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			ThreadSleep(4000);
			if (CommonLib
					.getText(driver, BP.eventDetailPageHeader(15), "Meeting: " + meetingBasicSection, action.BOOLEAN)
					.equalsIgnoreCase("Nav Meeting")) {
				log(LogStatus.PASS,
						"-----Event Detail Page: " + "Nav Meeting" + " has been open after Meeting Create-----",
						YesNo.No);
			}

			else {
				log(LogStatus.FAIL,
						"-----Event Detail Page: " + "Nav Meeting" + " has not been open after Meeting Create-----",
						YesNo.No);
				sa.assertTrue(false,
						"-----Event Detail Page: " + "Nav Meeting" + " has not been open after Meeting Create-----");
			}

		} else {
			log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created");
		}

		log(LogStatus.INFO, "---------Now Going to Create Task in Activity Timeline Section---------", YesNo.No);
		CommonLib.refresh(driver);
		if (BP.createActivityTimeline(projectName, true, "Task", taskBasicSection, null, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			ThreadSleep(4000);
			if (CommonLib.getText(driver, BP.taskDetailPageHeader(15), "Task: " + taskBasicSection, action.BOOLEAN)
					.equalsIgnoreCase("Nav Task")) {
				log(LogStatus.PASS, "-----Task Detail Page: " + "Nav Task" + " has been open after Task Create-----",
						YesNo.No);
			}

			else {
				log(LogStatus.FAIL,
						"-----Task Detail Page: " + "Nav Task" + " has not been open after Task Create-----", YesNo.No);
				sa.assertTrue(false,
						"-----Task Detail Page: " + "Nav Task" + " has not been open after Task Create-----");
			}

		} else {
			log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created");
		}

		log(LogStatus.INFO, "---------Now Going to Check Interaction Cards under Interaction Section for Firm Named "
				+ institutionName[0] + " under Acuity Subtab---------", YesNo.No);
		CommonLib.refresh(driver);
		if (BP.clickOnTab(projectName, tabObj1))

		{
			if (BP.clickOnAlreadyCreatedItem(projectName, institutionName[0], TabName.InstituitonsTab, 50)) {
				log(LogStatus.INFO, "successfully open the record : " + institutionName[0], YesNo.No);
				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on Tab: " + subTabName, YesNo.No);

					ArrayList<String> result1 = BP.verifyRecordOnInteractionCard(null, "Nav Call", null, false, false);
					if (result1.isEmpty()) {
						log(LogStatus.PASS, "Nav Call" + " record has been verified on intraction", YesNo.No);

					} else {
						log(LogStatus.ERROR, "Nav Call" + " record is not verified on intraction, Reason: " + result1,
								YesNo.No);
						sa.assertTrue(false, "Nav Call" + " record is not verified on intraction, Reason: " + result1);
					}
					ArrayList<String> result2 = BP.verifyRecordOnInteractionCard(null, "Nav Meeting", null, false,
							false);
					if (result2.isEmpty()) {
						log(LogStatus.PASS, "Nav Meeting" + " record has been verified on intraction", YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"Nav Meeting" + " record is not verified on intraction, Reason: " + result1, YesNo.No);
						sa.assertTrue(false,
								"Nav Meeting" + " record is not verified on intraction, Reason: " + result1);
					}
					ArrayList<String> result3 = BP.verifyRecordOnInteractionCard(null, "Nav Task", null, false, false);
					if (result3.isEmpty()) {
						log(LogStatus.PASS, "Nav Task" + " record has been verified on intraction", YesNo.No);

					} else {
						log(LogStatus.ERROR, "Nav Task" + " record is not verified on intraction, Reason: " + result1,
								YesNo.No);
						sa.assertTrue(false, "Nav Task" + " record is not verified on intraction, Reason: " + result1);
					}

					log(LogStatus.INFO, "---------Now Going to Verify Activity Timeline Section under ---------",
							YesNo.No);

					if (BP.clicktabOnPage("Communications")) {
						log(LogStatus.PASS, "Clicked on Tab: " + "Communications", YesNo.No);

						List<String> activityTimelineList = BP.activityTimeLineListUnderCommunicationTab().stream()
								.map(x -> x.getText()).collect(Collectors.toList());

						if (activityTimelineList.size() != 0) {
							for (String activityTimeline : activityTimeLines) {
								if (activityTimelineList.contains(activityTimeline)) {
									log(LogStatus.PASS, "Activity Timeline: " + activityTimeline
											+ " is Present under Tab: " + "Communications", YesNo.No);
								} else {
									log(LogStatus.FAIL, "Activity Timeline: " + activityTimeline
											+ " is not Present under Tab: " + "Communications", YesNo.Yes);
									sa.assertTrue(false, "Activity Timeline: " + activityTimeline
											+ " is not Present under Tab: " + "Communications");
								}
							}
						} else {
							log(LogStatus.FAIL, "No Activity Timeline found under Tab: " + "Communications", YesNo.Yes);
							sa.assertTrue(false, "No Activity Timeline found under Tab: " + "Communications");
						}
					} else {
						log(LogStatus.FAIL, "Not Able to Click on Tab: " + "Communications", YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on Tab: " + "Communications");
					}

				} else {
					log(LogStatus.FAIL, "Not Able to Click on Tab: " + subTabName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Tab: " + subTabName);
				}

			} else {
				log(LogStatus.FAIL, "Not Able to open the record : " + institutionName[0], YesNo.Yes);
				sa.assertTrue(false, "Not Able to open the record : " + institutionName[0]);

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuitySmokeTc028_CreateRecordTypeAndData(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String dealRecordTypeList = "BuySide Deal<break>SellSide Deal";
		String fundRecordTypeList = "Mutual Fund<break>Pension Fund";
		String fundraisingRecordTypeList = "FDKRT01<break>MKDRT01";
		String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);
		String fundRecordTypeArray[] = fundRecordTypeList.split(breakSP, -1);
		String fundraisingRecordTypeArray[] = fundraisingRecordTypeList.split(breakSP, -1);
		String recordTypeDescription = "Description Record Type";

		String[][][] dealRecordType = {
				{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[0] },
						{ recordTypeLabel.Description.toString(), dealRecordTypeArray[0] + recordTypeDescription },
						{ recordTypeLabel.Active.toString(), "" } },
				{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[1] },
						{ recordTypeLabel.Description.toString(), dealRecordTypeArray[1] + recordTypeDescription },
						{ recordTypeLabel.Active.toString(), "" } } };

		String[][][] fundrecordType = {
				{ { recordTypeLabel.Record_Type_Label.toString(), fundRecordTypeArray[0] },
						{ recordTypeLabel.Description.toString(), fundRecordTypeArray[0] + recordTypeDescription },
						{ recordTypeLabel.Active.toString(), "" } },
				{ { recordTypeLabel.Record_Type_Label.toString(), fundRecordTypeArray[1] },
						{ recordTypeLabel.Description.toString(), fundRecordTypeArray[1] + recordTypeDescription },
						{ recordTypeLabel.Active.toString(), "" } } };

		String[][][] fundraisingrecordType = {
				{ { recordTypeLabel.Record_Type_Label.toString(), fundraisingRecordTypeArray[0] },
						{ recordTypeLabel.Description.toString(),
								fundraisingRecordTypeArray[0] + recordTypeDescription },
						{ recordTypeLabel.Active.toString(), "" } },
				{ { recordTypeLabel.Record_Type_Label.toString(), fundraisingRecordTypeArray[1] },
						{ recordTypeLabel.Description.toString(),
								fundraisingRecordTypeArray[1] + recordTypeDescription },
						{ recordTypeLabel.Active.toString(), "" } } };

		String[] profileForSelection = { "PE Standard User", "System Administrator" };
		boolean isMakeAvailable = true;
		boolean isMakeDefault = true;
		boolean flag = false;

		String[] institutionName = "Research Institution 01<Break>Research Company 01<Break>Research Intermediary 01<Break>Research Advisor 01<Break>Research Lender 01<Break>Research Limited Partner 01<Break>Research Portfolio Company 01"
				.split("<Break>", -1);
		String[] recordType = "Institution<Break>Company<Break>Intermediary<Break>Advisor<Break>Lender<Break>Limited Partner<Break>Portfolio Company"
				.split("<Break>", -1);
		String[] labelsOfFirmPopUp = "<Break><Break><Break><Break><Break>Parent Firm<Break>".split("<Break>", -1);
		String[] valuesOfFirmPopUp = "<Break><Break><Break><Break><Break>Research Institution 01<Break>"
				.split("<Break>", -1);

		String[] contactFirstNames = "Research<Break>Research<Break>Research<Break>Research<Break>Research<Break>Research<Break>Research"
				.split("<Break>", -1);
		String[] contactLastNames = "Inst Contact1<Break>Com Contact2<Break>Int Contact3<Break>Adv Contact4<Break>Len Contact5<Break>Com LP Contact6<Break>Com PC Contact7"
				.split("<Break>", -1);
		String[] contactEmailIds = "1investorportal+ResearchInstContact1@gmail.com<Break>1investorportal+ResearchComContact2@gmail.com<Break>1investorportal+ResearchIntContact3@gmail.com<Break>1investorportal+ResearchAdvContact4@gmail.com<Break>1investorportal+ResearchLenContact5@gmail.com<Break>1investorportal+ResearchLpContact6@gmail.com<Break>1investorportal+ResearchPcContact7@gmail.com"
				.split("<Break>", -1);

		String[] dealRecordTypes = "BuySide Deal<Break>SellSide Deal<Break>BuySide Deal".split("<Break>", -1);
		String[] dealName = "Research Inst Deal 01<Break>Research Com Deal 02<Break>Research Int Deal 03"
				.split("<Break>", -1);
		String[] dealCompany = "Research Institution 01<Break>Research Company 01<Break>Research Intermediary 01"
				.split("<Break>", -1);
		String[] dealStage = "NDA Signed<Break>IOI<Break>LOI".split("<Break>", -1);

		String[] fundNames = "Research Fund 001<Break>Research Fund 002<Break>Research Fund 003".split("<Break>", -1);
		String[] fundRecordTypes = "Mutual Fund<Break>Pension Fund<Break>Mutual Fund".split("<Break>", -1);
		String[] fundTypes = "Fund<Break>Fund<Break>Fund".split("<Break>", -1);
		String[] investmentCategories = "Fund<Break>Fund<Break>Fund".split("<Break>", -1);
		String otherLabelFields = null;
		String otherLabelValues = null;

		String[] ClosingDates = "12/27/2022<Break>11/25/2022<Break>10/27/2022".split("<Break>", -1);
		String[] fundraisingRecordTypes = "FDKRT01<Break>MKDRT01<Break>FDKRT01".split("<Break>", -1);
		String[] fundraisingNames = "Research FR 01<Break>Research FR 01<Break>Research FR 01".split("<Break>", -1);
		String[] fundraisingsFundName = fundNames;
		String[] fundraisingsInstitutionName = "Research Institution 01<Break>Research Limited Partner 01<Break>Research Institution 01"
				.split("<Break>", -1);
		String[] fundraisingsStageName = "Prospect<Break>Interested<Break>Prospect".split("<Break>", -1);
		String[] fundraisingsClosingDate = new String[ClosingDates.length];

		int loop = 0;
		for (String ClosingDate : ClosingDates) {
			fundraisingsClosingDate[loop] = convertDateFromOneFormatToAnother(ClosingDate, "MM/dd/yyyy", "dd/MMM/yyyy");
			loop++;
		}

//		String val=AS_ATDay1;
		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt("10"));
//        ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline" , excelLabel.Variable_Name, "AT_001", excelLabel.Advance_Due_Date);

		String[][] task1BasicSection = { { "Subject", "Research Task 01" },
				{ "Notes", "Task For Power Search Smoke Researching" }, { "Related_To",
						"Research Inst Contact1<break>Research Company 01<break>Research Limited Partner 01<break>Research Inst Deal 01" } };

		String[][] task1AdvancedSection = { { "Due Date Only", AdvanceDueDate } };

		String[][] task2BasicSection = { { "Subject", "Research Task 02" },
				{ "Notes", "Task For Power Search Smoke Researching" }, { "Related_To",
						"Research Inst Contact2<break>Research Institution 01<break>Research Lender 01<break>Research Fund 002<break>Research FR 01" } };

		String[][] task2AdvancedSection = { { "Due Date Only", AdvanceDueDate } };

		lp.CRMLogin(superAdminUserName, adminPassword);
		for (int i = 0; i < dealRecordTypeArray.length; i++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				flag = false;
				String parentID = switchOnWindow(driver);
				SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
				if (parentID != null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Deal)) {
						if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Deal,
								ObjectFeatureName.recordTypes)) {
							if (i == 0) {
								flag = sp.createRecordTypeForObject(projectName, dealRecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							} else {
								isMakeDefault = false;
								flag = sp.createRecordTypeForObject(projectName, dealRecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							}
							if (flag) {
								log(LogStatus.INFO, "Created Record Type : " + dealRecordTypeArray[i], YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not Able to Create Record Type : " + dealRecordTypeArray[i],
										YesNo.Yes);
								sa.assertTrue(false, "Not Able to Create Record Type : " + dealRecordTypeArray[i]);
							}

						} else {
							log(LogStatus.ERROR,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable", YesNo.Yes);
							sa.assertTrue(false,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable");
						}
					} else {
						log(LogStatus.ERROR, object.Deal + " object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, object.Deal + " object could not be found in object manager");
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

		for (int i = 0; i < fundRecordTypeArray.length; i++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				flag = false;
				String parentID = switchOnWindow(driver);
				SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
				if (parentID != null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Fund)) {
						if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Fund,
								ObjectFeatureName.recordTypes)) {
							if (i == 0) {
								flag = sp.createRecordTypeForObject(projectName, fundrecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							} else {
								isMakeDefault = false;
								flag = sp.createRecordTypeForObject(projectName, fundrecordType[i], isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							}
							if (flag) {
								log(LogStatus.INFO, "Created Record Type : " + fundRecordTypeArray[i], YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not Able to Create Record Type : " + fundRecordTypeArray[i],
										YesNo.Yes);
								sa.assertTrue(false, "Not Able to Create Record Type : " + fundRecordTypeArray[i]);
							}

						} else {
							log(LogStatus.ERROR,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable", YesNo.Yes);
							sa.assertTrue(false,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable");
						}
					} else {
						log(LogStatus.ERROR, object.Fund + " object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, object.Fund + " object could not be found in object manager");
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

		for (int i = 0; i < fundraisingRecordTypeArray.length; i++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				flag = false;
				String parentID = switchOnWindow(driver);
				SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
				if (parentID != null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Fundraising)) {
						if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Fundraising,
								ObjectFeatureName.recordTypes)) {
							if (i == 0) {
								flag = sp.createRecordTypeForObject(projectName, fundraisingrecordType[i],
										isMakeAvailable, profileForSelection, isMakeDefault, null, 10);
							} else {
								isMakeDefault = false;
								flag = sp.createRecordTypeForObject(projectName, fundraisingrecordType[i],
										isMakeAvailable, profileForSelection, isMakeDefault, null, 10);
							}
							if (flag) {
								log(LogStatus.INFO, "Created Record Type : " + fundraisingRecordTypeArray[i], YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Not Able to Create Record Type : " + fundraisingRecordTypeArray[i], YesNo.Yes);
								sa.assertTrue(false,
										"Not Able to Create Record Type : " + fundraisingRecordTypeArray[i]);
							}

						} else {
							log(LogStatus.ERROR,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable", YesNo.Yes);
							sa.assertTrue(false,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable");
						}
					} else {
						log(LogStatus.ERROR, object.Fundraising + " object could not be found in object manager",
								YesNo.Yes);
						sa.assertTrue(false, object.Fundraising + " object could not be found in object manager");
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
		switchToDefaultContent(driver);
		lp.CRMlogout();
		ThreadSleep(5000);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		int firmLoopCount = 0;
		log(LogStatus.INFO, "---------Now Going to Create " + tabObj1 + "---------", YesNo.No);
		home.notificationPopUpClose();
		for (String instName : institutionName) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (ip.createInstitution(projectName, environment, mode, instName, recordType[firmLoopCount],
						labelsOfFirmPopUp[firmLoopCount], valuesOfFirmPopUp[firmLoopCount]))

				{
					log(LogStatus.INFO, "successfully Created Account/Entity : " + instName + " of record type : "
							+ recordType[firmLoopCount], YesNo.No);
				} else {
					sa.assertTrue(false, "Not Able to Create Account/Entity : " + instName + " of record type : "
							+ recordType[firmLoopCount]);
					log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + instName + " of record type : "
							+ recordType[firmLoopCount], YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj1);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj1, YesNo.Yes);
			}
			ThreadSleep(2000);
			firmLoopCount++;
		}

		log(LogStatus.INFO, "---------Now Going to Create " + TabName.Object2Tab + "---------", YesNo.No);
		int contactLoopCount = 0;
		for (String contactFirstName : contactFirstNames) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

				if (cp.createContact(projectName, contactFirstName, contactLastNames[contactLoopCount],
						institutionName[contactLoopCount], contactEmailIds[contactLoopCount], "", null, null,
						CreationPage.ContactPage, null, null)) {
					log(LogStatus.INFO, "Successfully Created Contact : " + contactFirstName + " "
							+ contactLastNames[contactLoopCount], YesNo.No);
				}

				else {
					sa.assertTrue(false, "Not Able to Create Contact : " + contactFirstName + " "
							+ contactLastNames[contactLoopCount]);
					log(LogStatus.SKIP, "Not Able to Create Contact : " + contactFirstName + " "
							+ contactLastNames[contactLoopCount], YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
			}
			ThreadSleep(2000);
			contactLoopCount++;
		}

		log(LogStatus.INFO, "---------Now Going to Create " + tabObj4 + "---------", YesNo.No);
		for (int i = 0; i < dealName.length; i++) {
			if (lp.clickOnTab(projectName, tabObj4)) {
				log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
				ThreadSleep(3000);
				if (dp.createDeal(dealRecordTypes[i], dealName[i], dealCompany[i], dealStage[i], null, null)) {
					log(LogStatus.INFO, dealName[i] + " deal has been created", YesNo.No);

				} else {
					log(LogStatus.ERROR, dealName[i] + " deal is not created", YesNo.No);
					sa.assertTrue(false, dealName[i] + " deal is not created");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
			}
		}

		int fundStatus = 0;
		int fundLoopCount = 0;
		for (String fundName : fundNames) {

			log(LogStatus.INFO, "---------Now Going to Create Fund Named: " + fundName + "---------", YesNo.No);
			if (fund.clickOnTab(environment, mode, TabName.FundsTab)) {

				if (fund.createFund(projectName, fundRecordTypes[fundLoopCount], fundName, fundTypes[fundLoopCount],
						investmentCategories[fundLoopCount], otherLabelFields, otherLabelValues)) {
					appLog.info("Fund is created Successfully: " + fundName);
					fundStatus++;

				} else {
					appLog.error("Not able to click on fund: " + fundName);
					sa.assertTrue(false, "Not able to click on fund: " + fundName);
					log(LogStatus.ERROR, "Not able to click on fund: " + fundName, YesNo.Yes);
				}
			} else {
				appLog.error("Not able to click on Fund tab so cannot create Fund: " + fundName);
				sa.assertTrue(false, "Not able to click on Fund tab so cannot create Fund: " + fundName);
			}
			ThreadSleep(2000);
			fundLoopCount++;

		}

		if (fundStatus == fundLoopCount) {
			int fundraisingLoopCount = 0;
			for (String fundraisingName : fundraisingNames) {
				log(LogStatus.INFO, "---------Now Going to Create Fundraising Named: " + fundraisingName + "---------",
						YesNo.No);
				if (BP.clickOnTab(environment, mode, TabName.FundraisingsTab)) {

					String[] targetClosingCompleteDate = fundraisingsClosingDate[fundraisingLoopCount].split("/");
					String targetClosingDate = targetClosingCompleteDate[0];
					String targetClosingMonth = targetClosingCompleteDate[1];
					String targetClosingYear = targetClosingCompleteDate[2];

					if (fr.createFundRaising(environment, "Lightning", fundraisingRecordTypes[fundraisingLoopCount],
							fundraisingName, fundraisingsFundName[fundraisingLoopCount],
							fundraisingsInstitutionName[fundraisingLoopCount], null,
							fundraisingsStageName[fundraisingLoopCount], null, null, targetClosingYear,
							targetClosingMonth, targetClosingDate)) {
						appLog.info("fundraising is created : " + fundraisingName);
					} else {
						appLog.error("Not able to create fundraising: " + fundraisingName);
						sa.assertTrue(false, "Not able to create fundraising: " + fundraisingName);
					}

				} else {
					appLog.error(
							"Not able to click on fundraising tab so cannot create fundraising: " + fundraisingName);
					sa.assertTrue(false,
							"Not able to click on fundraising tab so cannot create fundraising: " + fundraisingName);
				}
				ThreadSleep(2000);

				fundraisingLoopCount++;

			}

		} else {
			appLog.error("No Fund is created, So not able to Create Fundraising: " + fundraisingNames);
			sa.assertTrue(false, "No Fund is created, So not able to Create Fundraising: " + fundraisingNames);
		}

		log(LogStatus.INFO, "---------Now Going to Create Task in Activity Timeline Section---------", YesNo.No);
		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, "Task", task1BasicSection, task1AdvancedSection, null,
					null)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

				ThreadSleep(4000);
				if (CommonLib.getText(driver, BP.taskDetailPageHeader(15), "Task: " + task1BasicSection, action.BOOLEAN)
						.equalsIgnoreCase("Nav Task")) {
					log(LogStatus.PASS,
							"-----Task Detail Page: " + "Nav Task" + " has been open after Task Create-----", YesNo.No);
				}

				else {
					log(LogStatus.FAIL,
							"-----Task Detail Page: " + "Nav Task" + " has not been open after Task Create-----",
							YesNo.No);
					sa.assertTrue(false,
							"-----Task Detail Page: " + "Nav Task" + " has not been open after Task Create-----");
				}

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		ThreadSleep(5000);

		lp.CRMLogin(superAdminUserName, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Task in Activity Timeline Section---------", YesNo.No);
		CommonLib.refresh(driver);

		home.notificationPopUpClose();
		if (BP.createActivityTimeline(projectName, true, "Task", task2BasicSection, task2AdvancedSection, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			ThreadSleep(4000);
			if (CommonLib.getText(driver, BP.taskDetailPageHeader(15), "Task: " + task2BasicSection, action.BOOLEAN)
					.equalsIgnoreCase("Nav Task")) {
				log(LogStatus.PASS, "-----Task Detail Page: " + "Nav Task" + " has been open after Task Create-----",
						YesNo.No);
			}

			else {
				log(LogStatus.FAIL,
						"-----Task Detail Page: " + "Nav Task" + " has not been open after Task Create-----", YesNo.No);
				sa.assertTrue(false,
						"-----Task Detail Page: " + "Nav Task" + " has not been open after Task Create-----");
			}

		} else {
			log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuitySmokeTc029_VerifySearchResultWithResearchSearchboxLeftBlank(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String searchItemInResearch = "";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Verify Search Result With Research Searchbox Left Blank---------",
				YesNo.No);
		home.notificationPopUpClose();
		BP.searchAnItemAndFoundErrorMsgInResearch(projectName, searchItemInResearch);

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuitySmokeTc030_VerifyResearchResultWhenUserEnterSearchTermWithAValidCharacterString(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String searchItemInResearch = "Test";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Verify Research Result When User Enter Search Term With A Valid Character String---------",
				YesNo.No);
		home.notificationPopUpClose();
		if (BP.searchAnItemInResearchAndVerify(projectName, searchItemInResearch)) {
			log(LogStatus.INFO, "---------Search Functionality in Research has been Verified for Text: "
					+ searchItemInResearch + "---------", YesNo.No);
		} else {
			log(LogStatus.FAIL, "---------Search Functionality in Research has not been Verified for Text: "
					+ searchItemInResearch + "---------", YesNo.No);
			sa.assertTrue(false, "---------Search Functionality in Research has not been Verified for Text: "
					+ searchItemInResearch + "---------");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuitySmokeTc031_VerifyResearchResultWhenUserEnterSearchTermWith2ValidCharacterStringsWithASpaceInBetween(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String[] searchItemsInResearch = "Research Account<Break>Research Company<Break>Research Inst Deal<Break>Research FR 01"
				.split("<Break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String searchItemInResearch : searchItemsInResearch) {
			log(LogStatus.INFO,
					"---------Now Going to Verify Research Result when user enter search term with 2 Valid Character Strings with a space in between: "
							+ searchItemInResearch + "---------",
					YesNo.No);
			CommonLib.refresh(driver);
			home.notificationPopUpClose();
			if (BP.searchAnItemInResearchAndVerify(projectName, searchItemInResearch)) {
				log(LogStatus.INFO, "---------Search Functionality in Research has been Verified for Text: "
						+ searchItemInResearch + "---------", YesNo.No);
			} else {
				log(LogStatus.FAIL, "---------Search Functionality in Research has not been Verified for Text: "
						+ searchItemInResearch + "---------", YesNo.No);
				sa.assertTrue(false, "---------Search Functionality in Research has not been Verified for Text: "
						+ searchItemInResearch + "---------");
			}
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuitySmokeTc032_VerifyResearchResultWhenUserEnterSearchTermWithAValidCharacterStringWithSpaceAfterwards(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String[] searchItemsInResearch = "FR <Break>Research Company  <Break>Research Inst Deal <Break>Research FR 01 "
				.split("<Break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String searchItemInResearch : searchItemsInResearch) {
			log(LogStatus.INFO,
					"---------Verify Research Result when user enter search term with a Valid Character String with space afterwards: "
							+ searchItemInResearch + "---------",
					YesNo.No);
			CommonLib.refresh(driver);
			home.notificationPopUpClose();
			if (BP.searchAnItemInResearchAndVerify(projectName, searchItemInResearch)) {
				log(LogStatus.INFO, "---------Search Functionality in Research has been Verified for Text: "
						+ searchItemInResearch + "---------", YesNo.No);
			} else {
				log(LogStatus.FAIL, "---------Search Functionality in Research has not been Verified for Text: "
						+ searchItemInResearch + "---------", YesNo.No);
				sa.assertTrue(false, "---------Search Functionality in Research has not been Verified for Text: "
						+ searchItemInResearch + "---------");
			}
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuitySmokeTc033_VerifyResearchResultwhenUserEnterSearchTermWith2ValidCharacterStringEnclosedWithDoubleQuotes(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String[] searchItemsInResearch = "\"Research Institution\"<Break> \"Research FR\"<Break>\"Research Limited Partner 01\""
				.split("<Break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String searchItemInResearch : searchItemsInResearch) {
			log(LogStatus.INFO,
					"---------Verify Research Result when user enter search term with 2 Valid Character String enclosed with Double Quotes: "
							+ searchItemInResearch + "---------",
					YesNo.No);
			CommonLib.refresh(driver);
			home.notificationPopUpClose();
			if (BP.searchAnItemInResearchAndVerify(projectName, searchItemInResearch)) {
				log(LogStatus.INFO, "---------Search Functionality in Research has been Verified for Text: "
						+ searchItemInResearch + "---------", YesNo.No);
			} else {
				log(LogStatus.FAIL, "---------Search Functionality in Research has not been Verified for Text: "
						+ searchItemInResearch + "---------", YesNo.No);
				sa.assertTrue(false, "---------Search Functionality in Research has not been Verified for Text: "
						+ searchItemInResearch + "---------");
			}
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuitySmokeTc034_VerifyTheResultCountForEachCategoryFromTheResearchFindingsSideMenu(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String[] searchItemsInResearch = "Research<Break>Institution".split("<Break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String searchItemInResearch : searchItemsInResearch) {
			log(LogStatus.INFO,
					"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
							+ searchItemInResearch + "---------",
					YesNo.No);
			CommonLib.refresh(driver);
			home.notificationPopUpClose();
			if (BP.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchItemInResearch)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchItemInResearch + "---------",
						YesNo.No);
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchItemInResearch + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchItemInResearch + "---------");
			}
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuitySmokeTc035_VerifyTheNumberOfVisibleRecordsUnderEachGridMustLessThanOrEqualTo5(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String[] searchItemsInResearch = "Research<Break>Institution".split("<Break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String searchItemInResearch : searchItemsInResearch) {
			log(LogStatus.INFO,
					"---------Going to Verify the number of visible records under Grid Must Less Than Or Equal To 5 for Searched Item: "
							+ searchItemInResearch + "---------",
					YesNo.No);
			CommonLib.refresh(driver);
			home.notificationPopUpClose();
			if (BP.searchAnItemInResearchAndVerifyItsCountInGridNotMoreThan5(projectName, searchItemInResearch)) {
				log(LogStatus.INFO,
						"---------Verify the number of visible records under Grid Must Less Than Or Equal To 5 for Searched Item: "
								+ searchItemInResearch + "---------",
						YesNo.No);
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the number of visible records under Grid Must Less Than Or Equal To 5 for Searched Item: "
								+ searchItemInResearch + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the number of visible records under Grid Must Less Than Or Equal To 5 for Searched Item: "
								+ searchItemInResearch + "---------");
			}
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuitySmokeTc041_1_CreateDealUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
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
					if (setup.createPEUser(crmUser4FirstName, UserLastName, emailId, crmUserLience, crmUser4Profile)) {
						log(LogStatus.INFO,
								"CRM User is created Successfully: " + crmUser4FirstName + " " + UserLastName,
								YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User4",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name,
								"User4", excelLabel.User_Last_Name);
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
				if (setup.installedPackages(crmUser4FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + crmUser4FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in CRM User4: " + crmUser4FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in CRM User4: " + crmUser4FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in CRM User4: " + crmUser4FirstName + " " + UserLastName,
							YesNo.Yes);
				}
			}
		} else {

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

		}
		lp.CRMlogout();
		closeBrowser();
//		driver.switchTo().window(parentWindow);
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
			appLog.info("Password is set successfully for CRM User4: " + crmUser4FirstName + " " + UserLastName);
		} else {
			appLog.info("Password is not set for CRM User4: " + crmUser4FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User4: " + crmUser4FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User4: " + crmUser4FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuitySmokeTc041_2_VerifyAcuityTabFromDealUser(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		lp.CRMLogin(crmUser4EmailID, adminPassword);

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}
}
