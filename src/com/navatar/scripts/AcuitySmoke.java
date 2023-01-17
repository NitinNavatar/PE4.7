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
	public void ASTc001_CreateCRMUser(String projectName) {
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
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {

			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				switchToDefaultContent(driver);
				CommonLib.ThreadSleep(5000);
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
	public void ASTc002_CreateTaskToVerifyDataOnInteractionCard(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fd = new FundsPageBusinessLayer(driver);

		String[] accountName = AS_FirmLegalName1.split("<break>");
		String[] recordType = AS_FirmRecordType1.split("<break>");

		String[] contactFirstName = AS_ContactFirstName.split("<break>");
		String[] contactLastName = AS_ContactLastName.split("<break>");
		String[] contactLegalName = AS_ContactLegalName.split("<break>");
		String[] contactEmail = AS_ContactEmail.split("<break>");

		String[] dealName = AS_DealName.split("<break>");
		String[] dealCompany = AS_DealCompany.split("<break>");
		String[] dealStage = AS_DealStage.split("<break>");

		String fundName = AS_FundName;
		String fundType = AS_FundType;
		String fundInvestmentCategory = AS_FundInvestmentCategory;
		String[] suggestedTag = new String[1];
		suggestedTag[0] = AS_ATSuggestedTag1;
		String companyRecord = accountName[1];
		int status = 0;
		String val = AS_ATDay1;
		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(val));
		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AT_001", excelLabel.Advance_Due_Date);

		String[][] basicsection = { { "Subject", AS_ATSubject1 }, { "Notes", AS_ATNotes1 },
				{ "Related_To", AS_ATRelatedTo1 } };
		String[][] advanceSection = { { "Due Date Only", AdvanceDueDate } };

		String[] completedate = AdvanceDueDate.split("/");
		char dayNum = completedate[1].charAt(0);
		String day;
		if (dayNum == '0') {
			day = completedate[1].replaceAll("0", "");
		} else {
			day = completedate[1];
		}

		String date = completedate[0] + "/" + day + "/" + completedate[2];

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
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

							if (lp.clickOnTab(projectName, tabObj1)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

								if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
										companyRecord, 30)) {
									log(LogStatus.INFO, companyRecord + " reocrd has been open", YesNo.No);
									if (bp.clicktabOnPage("Communications")) {
										log(LogStatus.INFO, "clicked on Communications tab", YesNo.No);

										if (bp.createActivityTimeline(projectName, false, AS_ATActivityType1,
												basicsection, advanceSection, null, suggestedTag)) {
											log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);
											sa.assertTrue(true, "Activity timeline record has been created");
											ThreadSleep(4000);
											if (bp.clicktabOnPage("Acuity")) {
												log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
												ArrayList<String> result = bp.verifyRecordOnInteractionCard(date, null,
														AS_ATSubject1, AS_ATNotes1, true, false, null, null);
												if (result.isEmpty()) {
													log(LogStatus.PASS,
															AS_ATSubject1 + " record has been verified on intraction",
															YesNo.No);
													sa.assertTrue(true,
															AS_ATSubject1 + " record has been verified on intraction");
												} else {
													log(LogStatus.ERROR,
															AS_ATSubject1 + " record is not verified on intraction",
															YesNo.No);
													sa.assertTrue(false,
															AS_ATSubject1 + " record is not verified on intraction");
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
												sa.assertTrue(false, "Not able to click on Acuity Tab");
											}
										} else {
											log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
											sa.assertTrue(false, "Activity timeline record is not created");
										}

									} else {
										log(LogStatus.ERROR, "Not able to click on Communications tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Communications tab");
									}

								} else {
									log(LogStatus.ERROR, "Not able to open " + companyRecord + " reocrd", YesNo.No);
									sa.assertTrue(false, "Not able to open " + companyRecord + " reocrd");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
							}
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
	public void ASTc003_VerifyImpactAfterCreationOfTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String[] Companies = AS_ACompanies1.split("<break>");
		String[] People = new String[1];
		String[] Deals = new String[1];
		People[0] = AS_APeople1;
		Deals[0] = AS_ADeals1;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, AS_FirmLegalName2, 30)) {
				log(LogStatus.INFO, AS_FirmLegalName2 + " reocrd has been open", YesNo.No);

				ArrayList<String> result = bp.verifyRecordOnTagged(Companies, People, Deals);
				if (result.isEmpty()) {
					log(LogStatus.INFO, "Records on Company, People and Deals Tagged have been matched", YesNo.No);
					sa.assertTrue(true, "Records on Company, People and Deals Tagged have been matched");
				} else {
					log(LogStatus.ERROR, "Records on Company, People and Deals Tagged are not matched", YesNo.No);
					sa.assertTrue(false, "Records on Company, People and Deals Tagged are not matched");
				}
				ArrayList<String> result1 = bp.verifyRecordOnContactSectionAcuity(AS_AContactName1, null, null,
						AS_AMeetingsAndCalls1, null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "Records on Contact slot have been matched", YesNo.No);
					sa.assertTrue(true, "Records on Contact slot have been matched");
				} else {
					log(LogStatus.ERROR, "Records on Contact slot are not matched", YesNo.No);
					sa.assertTrue(false, "Records on Contact slot are not matched");
				}
			} else {
				log(LogStatus.ERROR, "Not able to open " + AS_FirmLegalName2 + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + AS_FirmLegalName2 + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, AS_ContactName2, 30)) {
				log(LogStatus.INFO, AS_ContactName2 + " reocrd has been open", YesNo.No);

				ArrayList<String> result = bp.verifyRecordOnTagged(Companies, People, Deals);
				if (result.isEmpty()) {
					log(LogStatus.INFO, "Records on Company, People and Deals Tagged have been matched", YesNo.No);
					sa.assertTrue(true, "Records on Company, People and Deals Tagged have been matched");
				} else {
					log(LogStatus.ERROR, "Records on Company, People and Deals Tagged are not matched", YesNo.No);
					sa.assertTrue(false, "Records on Company, People and Deals Tagged are not matched");
				}
			} else {
				log(LogStatus.ERROR, "Not able to open " + AS_ContactName2 + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + AS_ContactName2 + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void ASTc004_CreateLogACallToVerifyDataOnInteractionCard(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String val = AS_ATDay2;
		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(val));
		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AT_003", excelLabel.Advance_Due_Date);

		String[][] basicsection = { { "Subject", AS_ATSubject3 }, { "Notes", AS_ATNotes3 },
				{ "Related_To", AS_ATRelatedTo3 } };
		String[][] advanceSection = { { "Due Date Only", AdvanceDueDate } };
		String subjectName = AS_ATSubject3;

		String[] completedate = AdvanceDueDate.split("/");
		char dayNum = completedate[1].charAt(0);
		String day;
		if (dayNum == '0') {
			day = completedate[1].replaceAll("0", "");
		} else {
			day = completedate[1];
		}

		String date = completedate[0] + "/" + day + "/" + completedate[2];
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, AS_FirmLegalName5, 30)) {
				log(LogStatus.INFO, AS_FirmLegalName5 + " reocrd has been open", YesNo.No);
				if (bp.clicktabOnPage("Communications")) {
					log(LogStatus.INFO, "clicked on Communications tab", YesNo.No);

					if (bp.createActivityTimeline(projectName, false, AS_ATActivityType3, basicsection, advanceSection,
							null, null)) {
						log(LogStatus.PASS, subjectName + " Activity timeline record has been created", YesNo.No);
						sa.assertTrue(true, subjectName + " Activity timeline record has been created");
						ThreadSleep(4000);
						if (bp.clicktabOnPage("Acuity")) {
							log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
							ArrayList<String> result = bp.verifyRecordOnInteractionCard(date, null, AS_ATSubject3,
									AS_ATNotes3, true, false, null, null);
							if (result.isEmpty()) {
								log(LogStatus.PASS, subjectName + " record has been verified on intraction", YesNo.No);
								sa.assertTrue(true, subjectName + " record has been verified on intraction");
							} else {
								log(LogStatus.ERROR, subjectName + " record is not verified on intraction", YesNo.No);
								sa.assertTrue(false, subjectName + " record is not verified on intraction");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
							sa.assertTrue(false, "Not able to click on Acuity Tab");
						}
					} else {
						log(LogStatus.ERROR, subjectName + " Activity timeline record is not created", YesNo.No);
						sa.assertTrue(false, subjectName + " Activity timeline record is not created");
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Communications tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Communications tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + AS_FirmLegalName5 + " reocrd", YesNo.No);
				sa.assertTrue(false, "Not able to open " + AS_FirmLegalName5 + " reocrd");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void ASTc005_VerifyImpactAfterCreationOfCall(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String[] Companies = AS_ACompanies2.split("<break>");
		String[] People = new String[1];
		String[] Deals = new String[1];
		People[0] = AS_APeople2;
		Deals[0] = AS_ADeals2;

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, AS_FirmLegalName6, 30)) {
				log(LogStatus.INFO, AS_FirmLegalName6 + " reocrd has been open", YesNo.No);

				ArrayList<String> result = bp.verifyRecordOnTagged(Companies, People, Deals);
				if (result.isEmpty()) {
					log(LogStatus.INFO, "Records on Company, People and Deals Tagged have been matched", YesNo.No);
					sa.assertTrue(true, "Records on Company, People and Deals Tagged have been matched");
				} else {
					log(LogStatus.ERROR, "Records on Company, People and Deals Tagged are not matched", YesNo.No);
					sa.assertTrue(false, "Records on Company, People and Deals Tagged are not matched");
				}
				ArrayList<String> result1 = bp.verifyRecordOnContactSectionAcuity(AS_AContactName2, null, null,
						AS_AMeetingsAndCalls2, null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "Records on Contact slot have been matched", YesNo.No);
					sa.assertTrue(true, "Records on Contact slot have been matched");
				} else {
					log(LogStatus.ERROR, "Records on Contact slot are not matched", YesNo.No);
					sa.assertTrue(false, "Records on Contact slot are not matched");
				}
			} else {
				log(LogStatus.ERROR, "Not able to open " + AS_FirmLegalName6 + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + AS_FirmLegalName6 + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, AS_ContactName4, 30)) {
				log(LogStatus.INFO, AS_ContactName4 + " reocrd has been open", YesNo.No);

				ArrayList<String> result = bp.verifyRecordOnTagged(Companies, People, Deals);
				if (result.isEmpty()) {
					log(LogStatus.INFO, "Records on Company, People and Deals Tagged have been matched", YesNo.No);
					sa.assertTrue(true, "Records on Company, People and Deals Tagged have been matched");
				} else {
					log(LogStatus.ERROR, "Records on Company, People and Deals Tagged are not matched", YesNo.No);
					sa.assertTrue(false, "Records on Company, People and Deals Tagged are not matched");
				}
			} else {
				log(LogStatus.ERROR, "Not able to open " + AS_ContactName4 + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + AS_ContactName4 + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ASTc006_VerifyInteractionCard(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String subjectName = AS_ATSubject1;
		String notes = AS_ATNotes1;

		String[] tag = AS_ATRelatedTo1.split("<break>");
		String[] relatedTo = new String[tag.length];

		for (int i = 0; i < tag.length; i++) {
			if (tag[i].contains("Prefilled")) {
				relatedTo[i] = tag[i].split("<Prefilled>")[0];
			} else {
				relatedTo[i] = tag[i];
			}
		}

		String[][] basicSection = { { "Notes", AS_ATNotes4 } };

		String[] suggestedTag = new String[1];
		suggestedTag[0] = AS_ATSuggestedTag3;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, AS_FirmLegalName6, 30)) {
				log(LogStatus.INFO, AS_FirmLegalName6 + " reocrd has been open", YesNo.No);

				String xPath = "//a[text()='" + subjectName
						+ "']/../preceding-sibling::div//button[@title='Edit Note']";
				WebElement ele = CommonLib.FindElement(driver, xPath, "email", action.SCROLLANDBOOLEAN, 30);
				String url = getURL(driver, 10);

				if (click(driver, ele, xPath, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

					ThreadSleep(10000);
					ArrayList<String> result = bp.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, subjectName,
							notes, relatedTo);
					if (result.isEmpty()) {
						log(LogStatus.INFO,
								"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
								YesNo.No);
						sa.assertTrue(true,
								"Notes Popup has been verified and Notes popup is opening in same page with prefilled value");
						refresh(driver);
						ThreadSleep(2000);
						if (click(driver, ele, xPath, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
							if (bp.updateActivityTimelineRecord(projectName, basicSection, null, null, suggestedTag,
									null)) {
								log(LogStatus.INFO, "Activity Timeline has been updated", YesNo.No);
								sa.assertTrue(true, "Activity Timeline has been updated");
							} else {
								log(LogStatus.ERROR, "Not able to update activity timeline", YesNo.No);
								sa.assertTrue(false, "Not able to update activity timeline");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}
					} else {
						log(LogStatus.ERROR,
								"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value",
								YesNo.No);
						sa.assertTrue(false,
								"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
					sa.assertTrue(false, "Not able to click on Edit Note button");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + AS_FirmLegalName6 + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + AS_FirmLegalName6 + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ASTc007_verifyViewAllLink(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName = AS_FirmLegalName7;

		String buttonName = AS_ATActivityType4;
		String buttonName1 = AS_ATActivityType5;
		String buttonName2 = AS_ATActivityType6;

		String[][] basicsection = { { "Subject", AS_ATSubject4 }, { "Notes", AS_ATNotes6 },
				{ "Related_To", AS_ATRelatedTo4 } };
		String val = AS_ATDay3;
		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(val));
		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AT_006", excelLabel.Advance_Due_Date);
		String[][] advanceSection = { { "Due Date Only", AdvanceDueDate } };

		String[][] basicsection1 = { { "Subject", AS_ATSubject5 }, { "Notes", AS_ATNotes7 },
				{ "Related_To", AS_ATRelatedTo5 } };
		String val1 = AS_ATDay4;
		String AdvanceDueDate1 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(val1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"AT_007", excelLabel.Advance_Due_Date);
		String[][] advanceSection1 = { { "Due Date Only", AdvanceDueDate1 } };

		String[][] basicsection2 = { { "Subject", AS_ATSubject6 }, { "Notes", AS_ATNotes8 },
				{ "Related_To", AS_ATRelatedTo6 } };

		String[] subjectsNameArray = AS_ATSubject7.split("<break>");
		ArrayList<String> subjectNames = new ArrayList<String>();
		for (int i = 0; i < subjectsNameArray.length; i++) {
			subjectNames.add(subjectsNameArray[i]);
		}
		String[] completedate = AdvanceDueDate.split("/");
		char dayNum = completedate[1].charAt(0);
		String day;
		if (dayNum == '0') {
			day = completedate[1].replaceAll("0", "");
		} else {
			day = completedate[1];
		}
		String date = completedate[0] + "/" + day + "/" + completedate[2];

		String[] completedate1 = AdvanceDueDate1.split("/");
		char dayNum1 = completedate1[1].charAt(0);
		String day1;
		if (dayNum1 == '0') {
			day1 = completedate1[1].replaceAll("0", "");
		} else {
			day1 = completedate1[1];
		}
		String date1 = completedate1[0] + "/" + day1 + "/" + completedate1[2];

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordName, 30)) {
				log(LogStatus.INFO, AS_FirmLegalName6 + " reocrd has been open", YesNo.No);
				if (!bp.verifyViewAllButtonOnIntractionCard(10)) {
					log(LogStatus.INFO, "view All Button is not visible", YesNo.No);
					sa.assertTrue(true, "view All Button is not visible");

					if (bp.clicktabOnPage("Communications")) {
						log(LogStatus.INFO, "clicked on Communications tab", YesNo.No);

						if (bp.createActivityTimeline(projectName, false, buttonName, basicsection, advanceSection,
								null, null)) {
							log(LogStatus.INFO, "Activity timeline has been created", YesNo.No);
							sa.assertTrue(true, "Activity timeline has been created");

							if (bp.clicktabOnPage("Acuity")) {
								log(LogStatus.INFO, "clicked on Acuity Tab", YesNo.No);
								ArrayList<String> result = bp.verifyRecordOnInteractionCard(date, null, AS_ATSubject4,
										AS_ATNotes6, false, false, null, null);
								if (result.isEmpty()) {
									log(LogStatus.PASS, AS_ATSubject4 + " record has been verified on intraction",
											YesNo.No);
									sa.assertTrue(true, AS_ATSubject4 + " record has been verified on intraction");
									if (!bp.verifyViewAllButtonOnIntractionCard(5)) {
										log(LogStatus.INFO, "view All Button is not visible", YesNo.No);
										sa.assertTrue(true, "view All Button is not visible");
										if (bp.clicktabOnPage("Communications")) {
											log(LogStatus.INFO, "clicked on Communications tab", YesNo.No);
											if (bp.createActivityTimeline(projectName, false, buttonName1,
													basicsection1, advanceSection1, null, null)) {
												log(LogStatus.INFO, "Activity timeline has been created", YesNo.No);
												sa.assertTrue(true, "Activity timeline has been created");
												if (bp.clicktabOnPage("Acuity")) {
													log(LogStatus.INFO, "clicked on Acuity Tab", YesNo.No);
													ArrayList<String> result1 = bp.verifyRecordOnInteractionCard(date1,
															null, AS_ATSubject5, AS_ATNotes7, false, false, null, null);
													if (result1.isEmpty()) {
														log(LogStatus.PASS,
																AS_ATSubject5
																		+ " record has been verified on intraction",
																YesNo.No);
														sa.assertTrue(true, AS_ATSubject5
																+ " record has been verified on intraction");
														if (!bp.verifyViewAllButtonOnIntractionCard(5)) {
															log(LogStatus.INFO, "view All Button is not visible",
																	YesNo.No);
															sa.assertTrue(true, "view All Button is not visible");

															if (bp.clicktabOnPage("Communications")) {
																log(LogStatus.INFO, "clicked on Communications tab",
																		YesNo.No);

																if (bp.createActivityTimeline(projectName, false,
																		buttonName2, basicsection2, null, null, null)) {
																	log(LogStatus.INFO,
																			"Activity timeline has been created",
																			YesNo.No);
																	sa.assertTrue(true,
																			"Activity timeline has been created");
																	if (bp.clicktabOnPage("Acuity")) {
																		log(LogStatus.INFO, "clicked on Acuity Tab",
																				YesNo.No);
																		ArrayList<String> result2 = bp
																				.verifyRecordOnInteractionCard(null,
																						null, AS_ATSubject6,
																						AS_ATNotes8, false, false, null,
																						null);
																		if (result2.isEmpty()) {
																			log(LogStatus.PASS, AS_ATSubject6
																					+ " record has been verified on intraction",
																					YesNo.No);
																			sa.assertTrue(true, AS_ATSubject6
																					+ " record has been verified on intraction");
																			if (bp.verifyViewAllButtonOnIntractionCard(
																					5)) {
																				log(LogStatus.INFO,
																						"view All Button is visible",
																						YesNo.No);
																				sa.assertTrue(true,
																						"view All Button is visible");

																				if (CommonLib.click(driver,
																						bp.getViewAllBtnOnIntration(5),
																						"view all button",
																						action.SCROLLANDBOOLEAN)) {
																					log(LogStatus.INFO,
																							"clicked on view All button",
																							YesNo.No);

																					if (bp.verifyAllIntractionsRecord(
																							subjectNames)) {
																						log(LogStatus.INFO,
																								"All records on Intraction card are visible "
																										+ subjectNames,
																								YesNo.No);
																						refresh(driver);
																						ThreadSleep(2000);
																						if (bp.verifySubjectLinkPopUpOnIntraction(
																								driver,
																								AS_ATSubject1)) {
																							log(LogStatus.INFO,
																									"page successfully redirecting to the "
																											+ AS_ATSubject1
																											+ " page on new tab",
																									YesNo.No);
																							refresh(driver);
																							ThreadSleep(2000);
																							if (lp.clickOnTab(
																									projectName,
																									tabObj2)) {
																								log(LogStatus.INFO,
																										"Clicked on Tab : "
																												+ tabObj2,
																										YesNo.No);
																								if (bp.clickOnAlreadyCreated_Lighting(
																										environment,
																										mode,
																										TabName.ContactTab,
																										AS_ContactName6,
																										30)) {
																									log(LogStatus.INFO,
																											AS_ContactName6
																													+ " reocrd has been open",
																											YesNo.No);
																									if (CommonLib.click(
																											driver,
																											bp.getViewAllBtnOnIntration(
																													5),
																											"view all button",
																											action.SCROLLANDBOOLEAN)) {
																										log(LogStatus.INFO,
																												"clicked on view All button",
																												YesNo.No);

																										if (bp.verifyAllIntractionsRecord(
																												subjectNames)) {
																											log(LogStatus.INFO,
																													"All records on Intraction card have been verified "
																															+ subjectNames,
																													YesNo.No);
																											refresh(driver);
																											ThreadSleep(
																													2000);
																											sa.assertTrue(
																													true,
																													"All records on Intraction card have been verified "
																															+ subjectNames);
																										} else {
																											log(LogStatus.ERROR,
																													"All records on Intraction card is not created",
																													YesNo.No);
																											sa.assertTrue(
																													false,
																													"All records on Intraction card is not created");
																										}
																									} else {
																										log(LogStatus.ERROR,
																												"not able to click on view All button",
																												YesNo.No);
																										sa.assertTrue(
																												false,
																												"not able to click on view All button");
																									}
																								} else {
																									log(LogStatus.ERROR,
																											"not able to open Contact record "
																													+ AS_ContactName6,
																											YesNo.No);
																									sa.assertTrue(false,
																											"not able to click on view All button "
																													+ AS_ContactName6);
																								}
																							} else {
																								log(LogStatus.ERROR,
																										"not able to click on tab "
																												+ tabObj2,
																										YesNo.No);
																								sa.assertTrue(false,
																										"not able to click on tab "
																												+ tabObj2);
																							}
																						} else {
																							log(LogStatus.ERROR,
																									"page is not redirecting to the "
																											+ AS_ATSubject1
																											+ " page on new tab",
																									YesNo.No);
																							sa.assertTrue(false,
																									"page is not redirecting to the "
																											+ AS_ATSubject1
																											+ " page on new tab");
																						}
																					} else {
																						log(LogStatus.ERROR,
																								"All records on Intraction card are not visible "
																										+ subjectNames,
																								YesNo.No);
																						sa.assertTrue(false,
																								"All records on Intraction card are not visible "
																										+ subjectNames);
																					}
																				} else {
																					log(LogStatus.ERROR,
																							"Not able to click on view All button",
																							YesNo.No);
																					sa.assertTrue(false,
																							"Not able to click on view All button");
																				}

																			} else {
																				log(LogStatus.ERROR,
																						"view All Button is not visible",
																						YesNo.No);
																				sa.assertTrue(false,
																						"view All Button is not visible");
																			}
																		} else {
																			log(LogStatus.ERROR, AS_ATSubject6
																					+ " record is not verified on intraction card",
																					YesNo.No);
																			sa.assertTrue(false, AS_ATSubject6
																					+ " record is not verified on intraction card");
																		}
																	} else {
																		log(LogStatus.ERROR,
																				"Not able to click on Acuity Tab",
																				YesNo.No);
																		sa.assertTrue(false,
																				"Not able to click on Acuity Tab");
																	}
																} else {
																	log(LogStatus.ERROR,
																			"Not able to create the Activity timeline",
																			YesNo.No);
																	sa.assertTrue(false,
																			"Not able to create the Activity timeline");

																}
															} else {
																log(LogStatus.ERROR,
																		"Not able to click on communication tab",
																		YesNo.No);
																sa.assertTrue(false,
																		"Not able to create on communication tab");
															}
														} else {
															log(LogStatus.ERROR, "view All Button is visible",
																	YesNo.No);
															sa.assertTrue(false, "view All Button is visible");
														}
													} else {
														log(LogStatus.ERROR,
																AS_ATSubject5
																		+ " record is not verified on intraction card",
																YesNo.No);
														sa.assertTrue(false, AS_ATSubject5
																+ " record is not verified on intraction card");
													}
												} else {
													log(LogStatus.ERROR, "Not able to click on Acuity sub tab",
															YesNo.No);
													sa.assertTrue(false, "Not able to click on Acuity sub tab");
												}
											} else {
												log(LogStatus.ERROR, "Not able to create the Activity timeline",
														YesNo.No);
												sa.assertTrue(false, "Not able to create the Activity timeline");
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Communication tab", YesNo.No);
											sa.assertTrue(false, "Not able to click on Communication tab");
										}
									} else {
										log(LogStatus.ERROR, "view All Button is visible", YesNo.No);
										sa.assertTrue(false, "view All Button is visible");
									}
								} else {
									log(LogStatus.ERROR, AS_ATSubject4 + " record is not verified on intraction",
											YesNo.No);
									sa.assertTrue(false, AS_ATSubject4 + " record is not verified on intraction");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
								sa.assertTrue(false, "Not able to click on Acuity Tab");
							}
						} else {

							log(LogStatus.ERROR, "Not able to created Activity timeline", YesNo.No);
							sa.assertTrue(false, "Not able to created Activity timeline");

						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Communication tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Communication tab");
					}

				} else {
					log(LogStatus.ERROR, "view All Button is visible", YesNo.No);
					sa.assertTrue(false, "view All Button is visible");
				}
			} else {
				log(LogStatus.ERROR, "Not able to open record " + recordName, YesNo.No);
				sa.assertTrue(false, "Not able to open record " + recordName);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on tab " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on tab " + tabObj1);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ASTc008_verifyInteractionCard(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String subjectName = AS_ATSubject1;
		String[] subjectsNameArray = AS_ATSubject7.split("<break>");
		ArrayList<String> subjectNames = new ArrayList<String>();
		for (int i = 0; i < subjectsNameArray.length; i++) {
			subjectNames.add(subjectsNameArray[i]);
		}

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, AS_FirmLegalName8, 30)) {
				log(LogStatus.INFO, AS_FirmLegalName8 + " reocrd has been open", YesNo.No);

				if (bp.verifyCountofIntractionCard(4)) {
					log(LogStatus.INFO, "The count of card on Intraction has been verified", YesNo.No);
					sa.assertTrue(true, "The count of card on Intraction has been verified");

					String xPath = "//a[text()='" + subjectName
							+ "']/../preceding-sibling::div//button[@title='Edit Note']";
					WebElement ele = CommonLib.FindElement(driver, xPath, "email", action.SCROLLANDBOOLEAN, 30);
					String url = getURL(driver, 10);

					if (click(driver, ele, xPath, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Edit Note button of " + subjectName, YesNo.No);

						ArrayList<String> result = bp.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, subjectName,
								null, null);
						if (result.isEmpty()) {
							log(LogStatus.INFO, "Notes popup has been open of " + subjectName, YesNo.No);
							refresh(driver);
							ThreadSleep(5000);

							if (CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(30),
									"view all button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on view All button", YesNo.No);

								if (bp.verifyAllIntractionsRecord(subjectNames)) {
									log(LogStatus.INFO,
											"All records on Intraction card have been verified " + subjectNames,
											YesNo.No);
									refresh(driver);
									ThreadSleep(2000);
									sa.assertTrue(true,
											"All records on Intraction card have been verified " + subjectNames);
								} else {
									log(LogStatus.ERROR, "All records on Intraction card is not created", YesNo.No);
									sa.assertTrue(false, "All records on Intraction card is not created");
								}
							} else {
								log(LogStatus.ERROR, "not able to click on view All button", YesNo.No);
								sa.assertTrue(false, "not able to click on view All button");
							}

						} else {
							log(LogStatus.ERROR, "Not able to open notes popup of " + subjectName, YesNo.No);
							sa.assertTrue(false, "Not able to open notes popup of " + subjectName);
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
						sa.assertTrue(false, "Not able to click on Edit Note button");
					}

				} else {
					log(LogStatus.ERROR, "The count of card on Intraction is not verified", YesNo.No);
					sa.assertTrue(false, "The count of card on Intraction is not verified");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open record " + AS_FirmLegalName8, YesNo.No);
				sa.assertTrue(false, "Not able to open record " + AS_FirmLegalName8);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on tab " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on tab " + tabObj1);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ASTc009_verifyInteractionCard(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String subjectName = AS_ATSubject1;
		String[] subjectsNameArray = AS_ATSubject7.split("<break>");
		ArrayList<String> subjectNames = new ArrayList<String>();
		for (int i = 0; i < subjectsNameArray.length; i++) {
			subjectNames.add(subjectsNameArray[i]);
		}

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, AS_FirmLegalName9, 30)) {
				log(LogStatus.INFO, AS_FirmLegalName8 + " reocrd has been open", YesNo.No);

				if (bp.verifyCountofIntractionCard(4)) {
					log(LogStatus.INFO, "The count of card on Intraction has been verified", YesNo.No);
					sa.assertTrue(true, "The count of card on Intraction has been verified");

					String xPath = "//a[text()='" + subjectName
							+ "']/../preceding-sibling::div//button[@title='Edit Note']";
					WebElement ele = CommonLib.FindElement(driver, xPath, "email", action.SCROLLANDBOOLEAN, 30);
					String url = getURL(driver, 10);

					if (click(driver, ele, xPath, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Edit Note button of " + subjectName, YesNo.No);

						ArrayList<String> result = bp.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, subjectName,
								null, null);
						if (result.isEmpty()) {
							log(LogStatus.INFO, "Notes popup has been open of " + subjectName, YesNo.No);
							refresh(driver);
							ThreadSleep(5000);

							if (CommonLib.click(driver, bp.getViewAllBtnOnIntration(25), "view all button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on view All button", YesNo.No);

								if (bp.verifyAllIntractionsRecord(subjectNames)) {
									log(LogStatus.INFO,
											"All records on Intraction card have been verified " + subjectNames,
											YesNo.No);
									refresh(driver);
									ThreadSleep(2000);
									sa.assertTrue(true,
											"All records on Intraction card have been verified " + subjectNames);
								} else {
									log(LogStatus.ERROR, "All records on Intraction card is not created", YesNo.No);
									sa.assertTrue(false, "All records on Intraction card is not created");
								}
							} else {
								log(LogStatus.ERROR, "not able to click on view All button", YesNo.No);
								sa.assertTrue(false, "not able to click on view All button");
							}
						} else {
							log(LogStatus.ERROR, "Not able to open notes popup of " + subjectName, YesNo.No);
							sa.assertTrue(false, "Not able to open notes popup of " + subjectName);
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
						sa.assertTrue(false, "Not able to click on Edit Note button");
					}

				} else {
					log(LogStatus.ERROR, "The count of card on Intraction is not verified", YesNo.No);
					sa.assertTrue(false, "The count of card on Intraction is not verified");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open record " + AS_FirmLegalName9, YesNo.No);
				sa.assertTrue(false, "Not able to open record " + AS_FirmLegalName9);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on tab " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on tab " + tabObj1);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ASTc010_VerifyPopupWindowOnTaggedGrid(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String[] subName = AS_ATSubject7.split("<break>");
		String[] subName1 = AS_ATSubject8.split("<break>");

		ArrayList<String> taggedCompanyAndpeople = new ArrayList<String>();
		for (int i = 0; i < subName.length; i++) {
			taggedCompanyAndpeople.add(subName[i]);
		}

		ArrayList<String> taggedCall = new ArrayList<String>();
		for (int i = 0; i < subName1.length; i++) {
			taggedCall.add(subName1[i]);
		}

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, AS_FirmLegalName7, 30)) {
				log(LogStatus.INFO, AS_FirmLegalName8 + " reocrd has been open", YesNo.No);

				ArrayList<String> result = bp.clickOnTaggedCountAndVerifyRecord(TaggedName.Companies, AS_ACompanies4,
						taggedCompanyAndpeople);
				if (result.isEmpty()) {
					log(LogStatus.INFO, AS_ACompanies4 + " records have been verified after clcking on count for "
							+ AS_FirmLegalName8 + " record type", YesNo.No);
				} else {
					log(LogStatus.ERROR, AS_ACompanies4 + " records is not verified after clcking on count for "
							+ AS_FirmLegalName8 + " record type", YesNo.No);
					sa.assertTrue(false, AS_ACompanies4 + " records is not verified after clcking on count for "
							+ AS_FirmLegalName8 + " record type");
				}

				refresh(driver);
				ThreadSleep(2000);

				ArrayList<String> result1 = bp.clickOnTaggedCountAndVerifyRecord(TaggedName.People, AS_APeople4,
						taggedCompanyAndpeople);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, AS_APeople4 + " records have been verified after clcking on count for "
							+ AS_FirmLegalName8 + " record type", YesNo.No);
				} else {
					log(LogStatus.ERROR, AS_APeople4 + " records is not verified after clcking on count for "
							+ AS_FirmLegalName8 + " record type", YesNo.No);
					sa.assertTrue(false, AS_APeople4 + " records is not verified after clcking on count for "
							+ AS_FirmLegalName8 + " record type");

				}

				refresh(driver);
				ThreadSleep(2000);
				ArrayList<String> result2 = bp.clickOnTaggedCountAndVerifyRecord(TaggedName.Deals, AS_ADeals4,
						taggedCall);
				if (result2.isEmpty()) {
					log(LogStatus.INFO, AS_ADeals4 + " records have been verified after clcking on count for "
							+ AS_FirmLegalName8 + " record type", YesNo.No);
				} else {
					log(LogStatus.ERROR, AS_ADeals4 + " records is not verified after clcking on count for "
							+ AS_FirmLegalName8 + " record type", YesNo.No);
					sa.assertTrue(false, AS_APeople4 + " records is not verified after clcking on count for "
							+ AS_FirmLegalName8 + " record type");
				}

			} else {
				log(LogStatus.ERROR, AS_ContactName4 + " record has been open from contact tab", YesNo.No);
				sa.assertTrue(false, AS_ContactName4 + " record has been open from contact tab");

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on tab : " + tabObj2);

		}

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, AS_ContactName4, 30)) {
				log(LogStatus.INFO, AS_FirmLegalName8 + " record has been open", YesNo.No);

				ArrayList<String> result = bp.clickOnTaggedCountAndVerifyRecord(TaggedName.Companies, AS_ACompanies4,
						taggedCompanyAndpeople);
				if (result.isEmpty()) {
					log(LogStatus.INFO, AS_ACompanies4 + " records have been verified after clcking on count for "
							+ AS_ContactName4 + " record type", YesNo.No);
				} else {
					log(LogStatus.ERROR, AS_ACompanies4 + " records is not verified after clcking on count for "
							+ AS_ContactName4 + " record type", YesNo.No);
					sa.assertTrue(false, AS_ACompanies4 + " records is not verified after clcking on count for "
							+ AS_ContactName4 + " record type");
				}

				refresh(driver);
				ThreadSleep(2000);

				ArrayList<String> result1 = bp.clickOnTaggedCountAndVerifyRecord(TaggedName.People, AS_APeople4,
						taggedCompanyAndpeople);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, AS_APeople4 + " records have been verified after clcking on count for "
							+ AS_ContactName4 + " record type", YesNo.No);
				} else {
					log(LogStatus.ERROR, AS_APeople4 + " records is not verified after clcking on count for "
							+ AS_ContactName4 + " record type", YesNo.No);
					sa.assertTrue(false, AS_APeople4 + " records is not verified after clcking on count for "
							+ AS_ContactName4 + " record type");
				}

				refresh(driver);
				ThreadSleep(2000);
				ArrayList<String> result2 = bp.clickOnTaggedCountAndVerifyRecord(TaggedName.Deals, AS_ADeals4,
						taggedCall);
				if (result2.isEmpty()) {
					log(LogStatus.INFO, AS_ADeals4 + " records have been verified after clcking on count for "
							+ AS_ContactName4 + " record type", YesNo.No);
				} else {
					log(LogStatus.ERROR, AS_ADeals4 + " records is not verified after clcking on count for "
							+ AS_ContactName4 + " record type", YesNo.No);
					sa.assertTrue(false, AS_ADeals4 + " records is not verified after clcking on count for "
							+ AS_ContactName4 + " record type");
				}

			} else {
				log(LogStatus.ERROR, AS_ContactName4 + " record has been open from contact tab", YesNo.No);
				sa.assertTrue(false, AS_ContactName4 + " record has been open from contact tab");

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on tab : " + tabObj2);
		}

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void ASTc011_VerifyContactsGrid(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fd = new FundsPageBusinessLayer(driver);
		DealTeamPageBusinessLayer dt = new DealTeamPageBusinessLayer(driver);

		int status = 0;
		String[] accountName = AS_FirmLegalName10.split("<break>");
		String[] recordType = AS_FirmRecordType2.split("<break>");

		String[] contactFirstName = AS_ContactFirstName2.split("<break>");
		String[] contactLastName = AS_ContactLastName2.split("<break>");
		String[] contactLegalName = AS_ContactLegalName2.split("<break>");
		String[] contactEmail = AS_ContactEmail2.split("<break>");
		String[] ContactTitle = AS_ContactTitle.split("<break>");

		String[] dealName = new String[1];
		dealName[0] = AS_DealName2;
		String[] dealCompany = new String[1];
		dealCompany[0] = AS_DealCompany2;
		String[] dealStage = new String[1];
		dealStage[0] = AS_DealStage2;

		String dealTeamName = AS_DealTeamName1;
		String dealTeamContact = AS_DealContact1;
		String dealTeamMember = AS_DealTeamMember1;
		String dealTeamRole = AS_DealTeamRole1;

		String[][] dealTeam = { { "Deal", dealTeamName }, { "Deal Contact", dealTeamContact },
				{ "Team Member", dealTeamMember }, { "Role", dealTeamRole } };

		String activityType = AS_ATActivityType7;
		String[][] basicsection = { { "Subject", AS_ATSubject9 }, { "Notes", AS_ATNotes9 },
				{ "Related_To", AS_ATRelatedTo7 } };

		String subjectName = AS_ATSubject9;
		String notes = AS_ATNotes9;

		String activityType1 = AS_ATActivityType8;
		String[][] basicsection1 = { { "Subject", AS_ATSubject10 }, { "Notes", AS_ATNotes10 },
				{ "Related_To", AS_ATRelatedTo8 } };
		String[][] advanceSection1 = { { "Assigned To ID", "PE Admin" } };

		String subjectName1 = AS_ATSubject10;
		String notes1 = AS_ATNotes10;

		String ContactRecord = AS_ContactName5;

		String contactGridName = AS_AContactName4;
		String contactGridTitle = AS_ATitle1;
		String contactGridDeal = AS_AContactDeals1;
		String contactGridMeetingAndCall = AS_AMeetingsAndCalls4;

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
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
							contactEmail[i], "", null, null, CreationPage.ContactPage, ContactTitle[i], null)) {
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

					if (lp.openAppFromAppLauchner(15, "Deal Team")) {

						log(LogStatus.INFO, "Click on Tab Deal Team", YesNo.No);
						ThreadSleep(3000);

						if (dt.createDealTeam(projectName, dealTeam, action.SCROLLANDBOOLEAN, 30)) {
							log(LogStatus.INFO, "Deal Team record has been created", YesNo.No);
							sa.assertTrue(true, "Deal Team record has been created");

							if (lp.clickOnTab(projectName, tabObj2)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

								if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
										ContactRecord, 30)) {
									log(LogStatus.INFO, ContactRecord + " reocrd has been open", YesNo.No);
									if (bp.clicktabOnPage("Communications")) {
										log(LogStatus.INFO, "clicked on Communications tab", YesNo.No);

										if (bp.createActivityTimeline(projectName, false, activityType, basicsection,
												null, null, null)) {
											log(LogStatus.PASS,
													"Activity timeline record has been created. SubjectName : "
															+ subjectName,
													YesNo.No);
											sa.assertTrue(true,
													"Activity timeline record has been created. SubjectName : "
															+ subjectName);

											ThreadSleep(4000);
											if (bp.clicktabOnPage("Acuity")) {
												log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
												ArrayList<String> result = bp.verifyRecordOnInteractionCard(null, null,
														subjectName, notes, true, false, null, null);
												if (result.isEmpty()) {
													log(LogStatus.PASS,
															subjectName + " record has been verified on intraction",
															YesNo.No);
													sa.assertTrue(true,
															subjectName + " record has been verified on intraction");

													if (lp.clickOnTab(projectName, tabObj2)) {

														log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

														if (bp.clickOnAlreadyCreated_Lighting(environment, mode,
																TabName.ContactTab, ContactRecord, 30)) {
															log(LogStatus.INFO, ContactRecord + " reocrd has been open",
																	YesNo.No);
															if (bp.clicktabOnPage("Communications")) {
																log(LogStatus.INFO, "clicked on Communications tab",
																		YesNo.No);

																if (bp.createActivityTimeline(projectName, false,
																		activityType1, basicsection1, advanceSection1,
																		null, null)) {
																	log(LogStatus.PASS,
																			"Activity timeline record has been created. SubjectName : "
																					+ subjectName1,
																			YesNo.No);
																	sa.assertTrue(true,
																			"Activity timeline record has been created. SubjectName : "
																					+ subjectName1);

																	ThreadSleep(4000);
																	if (bp.clicktabOnPage("Acuity")) {
																		log(LogStatus.INFO, "clicked on Acuity tab",
																				YesNo.No);
																		ArrayList<String> result1 = bp
																				.verifyRecordOnInteractionCard(null,
																						null, subjectName1, notes1,
																						true, false, null, null);
																		if (result1.isEmpty()) {
																			log(LogStatus.PASS, subjectName
																					+ " record has been verified on intraction",
																					YesNo.No);
																			sa.assertTrue(true, subjectName
																					+ " record has been verified on intraction");

																			if (lp.clickOnTab(projectName, tabObj1)) {

																				log(LogStatus.INFO,
																						"Clicked on Tab : " + tabObj1,
																						YesNo.No);

																				if (bp.clickOnAlreadyCreated_Lighting(
																						environment, mode,
																						TabName.InstituitonsTab,
																						AS_FirmLegalName11, 30)) {
																					log(LogStatus.INFO,
																							AS_FirmLegalName11
																									+ " record has been open",
																							YesNo.No);

																					ArrayList<String> result2 = bp
																							.verifyRecordOnContactSectionAcuity(
																									contactGridName,
																									contactGridTitle,
																									contactGridDeal,
																									contactGridMeetingAndCall,
																									null);
																					if (result2.isEmpty()) {
																						log(LogStatus.INFO,
																								"Records on Contact slot have been matched",
																								YesNo.No);
																						sa.assertTrue(true,
																								"Records on Contact slot have been matched");
																					} else {
																						log(LogStatus.ERROR,
																								"Records on Contact slot are not matched",
																								YesNo.No);
																						sa.assertTrue(false,
																								"Records on Contact slot are not matched");
																					}
																				} else {
																					log(LogStatus.ERROR,
																							"Not able to open "
																									+ AS_FirmLegalName11
																									+ " record",
																							YesNo.No);
																					sa.assertTrue(false,
																							"Not able to open "
																									+ AS_FirmLegalName11
																									+ " record");
																				}
																			} else {
																				log(LogStatus.ERROR,
																						"Not able to click on "
																								+ tabObj1 + " tab",
																						YesNo.No);
																				sa.assertTrue(false,
																						"Not able to click on "
																								+ tabObj1 + " tab");
																			}
																		} else {
																			log(LogStatus.ERROR, subjectName
																					+ " record is not verified on intraction",
																					YesNo.No);
																			sa.assertTrue(false, subjectName
																					+ " record is not verified on intraction");
																		}
																	} else {
																		log(LogStatus.ERROR,
																				"Not able to click on Acuity Tab",
																				YesNo.No);
																		sa.assertTrue(false,
																				"Not able to click on Acuity Tab");
																	}
																} else {
																	log(LogStatus.FAIL,
																			"Activity timeline record is not created",
																			YesNo.No);
																	sa.assertTrue(false,
																			"Activity timeline record is not created");
																}

															} else {
																log(LogStatus.ERROR,
																		"Not able to click on Communications tab",
																		YesNo.No);
																sa.assertTrue(false,
																		"Not able to click on Communications tab");
															}

														} else {
															log(LogStatus.ERROR,
																	"Not able to open " + ContactRecord + " reocrd",
																	YesNo.No);
															sa.assertTrue(false,
																	"Not able to open " + ContactRecord + " reocrd");
														}
													} else {
														log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2,
																YesNo.No);
														sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
													}
												} else {
													log(LogStatus.ERROR,
															subjectName + " record is not verified on intraction card",
															YesNo.No);
													sa.assertTrue(false,
															subjectName + " record is not verified on intraction card");
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
												sa.assertTrue(false, "Not able to click on Acuity Tab");
											}
										} else {
											log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
											sa.assertTrue(false, "Activity timeline record is not created");
										}

									} else {
										log(LogStatus.ERROR, "Not able to click on Communications tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Communications tab");
									}

								} else {
									log(LogStatus.ERROR, "Not able to open " + ContactRecord + " reocrd", YesNo.No);
									sa.assertTrue(false, "Not able to open " + ContactRecord + " reocrd");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
							}
						} else {
							log(LogStatus.ERROR, "Deal Team record is not created", YesNo.No);
							sa.assertTrue(false, "Deal Team record is not created");
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Deal Team", YesNo.No);
						sa.assertTrue(false, "Not able to click on Deal Team");
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
	public void ASTc012_VerifyPopupWindow(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String contactName = AS_AContactName4;

		String call1 = AS_ATSubject9;
		String call2 = AS_ATSubject10;
		String dealName = AS_DealName2;

		String meetingAndCallsPopupColumnAndValue[][] = { { "Subject", call2 } };
		String dealsPopupColumnAndValue[][] = { { "Deal Name", AS_DealName2 } };

		String xPath;
		WebElement ele;

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, AS_FirmLegalName11, 30)) {
				log(LogStatus.INFO, AS_FirmLegalName11 + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.PASS, "Clicked on Tab: Acuity", YesNo.No);

					if (CommonLib.click(driver, bp.contactDealCount(contactName, 30), "Deal Count", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Deal Count of Record: " + contactName, YesNo.No);

						ArrayList<String> result1 = bp.verifyRecordOnDealsPopUpSectionInAcuity(contactName, dealName,
								null, null, null);
						if (result1.isEmpty()) {
							log(LogStatus.INFO, "Records on Deals slot have been matched for : " + dealName, YesNo.No);

						} else {
							log(LogStatus.ERROR, "Records on Deals slot are not matched, Reason: " + result1, YesNo.No);
							sa.assertTrue(false, "Records on Deals slot are not matched, Reason" + result1);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Deal Count of Record: " + contactName, YesNo.No);
						sa.assertTrue(false, "Not Able to Click on Deal Count of Record: " + contactName);
					}

					if (CommonLib.click(driver, bp.contactMeetingAndCallCount(contactName, 30),
							"Meetings and call Count", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Meeting and call Count of Record: " + contactName, YesNo.No);

						ArrayList<String> result2 = bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(null, null,
								call1, null, null);
						if (result2.isEmpty()) {
							log(LogStatus.INFO, "Records on Meetings and call slot have been matched for :" + call1,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Records on Meetings and call slot are not matched, Reason: " + result2, YesNo.No);
							sa.assertTrue(false, "Records on Meetings and call slot are not matched, Reason" + result2);
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Meeting and call Count of Record: " + contactName,
								YesNo.No);
						sa.assertTrue(false, "Not able to click on Meeting and call Count of Record: " + contactName);
					}

					if (CommonLib.click(driver, bp.contactMeetingAndCallCount(contactName, 30),
							"Meetings and call Count", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Meeting and call Count of Record: " + contactName, YesNo.No);

						ArrayList<String> result3 = bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(null, null,
								call2, null, null);
						if (result3.isEmpty()) {
							log(LogStatus.INFO, "Records on Meetings and call slot have been matched for :" + call2,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Records on Meetings and call slot are not matched, Reason: " + result3, YesNo.No);
							sa.assertTrue(false, "Records on Meetings and call slot are not matched, Reason" + result3);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Meeting and call Count of Record: " + contactName,
								YesNo.No);
						sa.assertTrue(false, "Not able to click on Meeting and call Count of Record: " + contactName);

					}

					if (CommonLib.click(driver, bp.contactConnectionsCount(contactName, 30), "Connection icon",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on connection icon of Record: " + contactName, YesNo.No);

						ArrayList<String> result4 = bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactName,
								"PE Admin", null, null, null, null);
						if (result4.isEmpty()) {
							log(LogStatus.INFO,
									"Records on Connection popup slot have been matched for :" + contactName, YesNo.No);

						} else {
							log(LogStatus.ERROR, "Records on Connection popup slot are not matched, Reason: " + result4,
									YesNo.No);
							sa.assertTrue(false, "Records on Connection popup slot are not matched, Reason" + result4);
						}
					} else {
						log(LogStatus.INFO, "Not able to click on Connections icon", YesNo.No);
						sa.assertTrue(false, "Not able to click on Connections icon");
					}

					xPath = "//a[text()='" + contactName + "']/ancestor::td/..//td//button[@title='Connections']";
					ele = FindElement(driver, xPath, "Connection icon", action.SCROLLANDBOOLEAN, 30);

					if (clickUsingJavaScript(driver, ele, "Connection")) {
						log(LogStatus.INFO, "clicked on Connection icon", YesNo.No);

						ArrayList<String> result = bp.verifyPopupFromConnectionIcon(contactName, "PE Admin",
								meetingAndCallsPopupColumnAndValue, dealsPopupColumnAndValue);
						if (result.isEmpty()) {
							log(LogStatus.INFO, "The record on the pop of Meeting & call and Deals have been verified",
									YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Either record on the pop of Meeting & call or record on the pop of Deals is not verified",
									YesNo.No);
							sa.assertTrue(false,
									"Either record on the pop of Meeting & call or record on the pop of Deals is not verified");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Connection icon", YesNo.No);
						sa.assertTrue(false, "Not able to click on Connection icon");
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Tab: Acuity", YesNo.No);
					sa.assertTrue(false, "Not able to click on Tab: Acuity");
				}
			} else {
				log(LogStatus.ERROR, "Not able to open " + AS_FirmLegalName11 + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + AS_FirmLegalName11 + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ASTc013_VerifyConnectionSection(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String contactName = AS_ContactName5;
		String call2 = AS_ATRelatedTo8;

		String meetingAndCallsPopupColumnAndValue[][] = { { "Subject", call2 } };
		String dealsPopupColumnAndValue[][] = { { "Deal Name", AS_DealName2 } };

		String xPath;
		WebElement ele;

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactName, 30)) {
				log(LogStatus.INFO, contactName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.PASS, "Clicked on Tab: Acuity", YesNo.No);

					xPath = "//a[text()='" + contactName + "']/ancestor::td/..//td//button[@title='Connections']";
					ele = FindElement(driver, xPath, "Connection icon", action.SCROLLANDBOOLEAN, 30);

					if (clickUsingJavaScript(driver, ele, "Connection")) {
						log(LogStatus.INFO, "clicked on Connection icon", YesNo.No);

						ArrayList<String> result = bp.verifyPopupFromConnectionIcon(contactName, "PE Admin",
								meetingAndCallsPopupColumnAndValue, dealsPopupColumnAndValue);
						if (result.isEmpty()) {
							log(LogStatus.INFO, "The record on the pop of Meeting & call and Deals have been verified",
									YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Either record on the pop of Meeting & call or record on the pop of Deals is not verified",
									YesNo.No);
							sa.assertTrue(false,
									"Either record on the pop of Meeting & call or record on the pop of Deals is not verified");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Connection icon", YesNo.No);
						sa.assertTrue(false, "Not able to click on Connection icon");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Tab: Acuity", YesNo.No);
					sa.assertTrue(false, "Not able to click on Tab: Acuity");
				}
			} else {
				log(LogStatus.ERROR, "Not able to open " + AS_FirmLegalName11 + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + AS_FirmLegalName11 + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ASTc034_VerifyExportActionButtonOnRecordPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String[] firmName = AS_FirmLegalName12.split("<break>");

		String xPath;
		WebElement ele;
		ArrayList<String> reportName = new ArrayList<String>();
		ArrayList<String> reportDescription = new ArrayList<String>();
		reportName.add("Contacts with Firms");
		reportName.add("Activities with Firms");

		reportDescription.add("Shows the details of Contacts corresponding to the Firm");
		reportDescription.add("Shows the details of Activities corresponding to the Firm");

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		for (int i = 0; i < firmName.length; i++) {

			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, firmName[i], 30)) {
					log(LogStatus.INFO, firmName[i] + " reocrd has been open", YesNo.No);

					if (bp.verifyExportButtonPopup(firmName[i], reportName, reportDescription)) {
						log(LogStatus.INFO, "Export button is displaying and Report are also visible on export popup",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Either Export button is not displaying or Reports are not visible on export popup",
								YesNo.No);
					}
				} else {
					log(LogStatus.ERROR, "Not able to open " + firmName[i] + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + firmName[i] + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.No);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}

		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ASTc035_VerifyReportsFromNavigationMenu(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String[] reportName = AS_ReportName.split("<break>");
		String[] description = AS_ReportDescription.split("<break>");

		ArrayList<String> expectedReportName = new ArrayList<String>();
		ArrayList<String> expectedDescription = new ArrayList<String>();
		for (int i = 0; i < reportName.length; i++) {
			expectedReportName.add(reportName[i]);
		}

		for (int i = 0; i < description.length; i++) {
			expectedDescription.add(description[i]);
		}

		String xPath;
		WebElement ele;

		if (reportName.length == description.length) {
			lp.CRMLogin(superAdminUserName, adminPassword, appName);
			if (click(driver, bp.getUtilityRecord("Reports", 20), "Report button from utiliy",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on report button from utiliy bar", YesNo.No);
				sa.assertTrue(true, "Clicked on report button from utiliy bar");
				if (bp.verifyRecordandDescriptionThroughUtility(expectedReportName, expectedDescription)) {
					log(LogStatus.INFO, "Reports and Description have been verified from the Navigation menu",
							YesNo.No);
					sa.assertTrue(true, "Reports and Description have been verified from the Navigation menu");
				} else {
					log(LogStatus.ERROR, "Reports and Description are not verified from the Navigation menu", YesNo.No);
					sa.assertTrue(false, "Reports and Description are not verified from the Navigation menu");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on report button from utiliy bar", YesNo.No);
				sa.assertTrue(false, "Not able to click on report button from utiliy bar");
			}

		} else {
			log(LogStatus.ERROR, "The length of record name and description are not equal", YesNo.No);
			sa.assertTrue(false, "The length of record name and description are not equal");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ASTc036_DeleteCreatedRecordType(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);

		String parentWindow = null;
		String[] tabName = { "Deal", "Fundraising", "Fund" };
		String xPath;
		WebElement ele;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		if (home.clickOnSetUpLink()) {

			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create CRM User2");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create CRM User2",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create CRM User2");
			}
		}

		int status = 0;

		if (setup.searchStandardOrCustomObject(projectName, mode, object.Profiles)) {
			log(LogStatus.INFO, "Profile has been open", YesNo.Yes);

			if (setup.removeRecordTypeOfObject("System Administrator", RecordType.Fund)) {
				log(LogStatus.INFO, "The record type of fund has been removed", YesNo.No);
				sa.assertTrue(true, "The record type of fund has been removed");

				if (setup.searchStandardOrCustomObject(projectName, mode, object.Fund)) {
					log(LogStatus.INFO, "Fund object has been open", YesNo.Yes);

					if (setup.clickOnObjectFeature(projectName, mode, object.Fund, ObjectFeatureName.recordTypes)) {
						log(LogStatus.INFO, "clicked on Record type of object feature of fund object", YesNo.Yes);

						ArrayList<String> result = setup.inactiveRecordType(projectName, object.Fund);
						if (result.isEmpty()) {
							log(LogStatus.INFO, "All Record type have been inactive of fund object", YesNo.Yes);
							ArrayList<String> result1 = setup.deleteAllRecordTypeOfObject();
							if (result1.isEmpty()) {
								log(LogStatus.INFO, "All Record type has been deleted of fund object", YesNo.Yes);
								sa.assertTrue(true, "All Record type has been deleted of fund object");
								status++;
							} else {
								log(LogStatus.ERROR, "The record type is not deleted of fund object" + result1,
										YesNo.No);
								sa.assertTrue(false, "The record type is not deleted of fund object" + result1);
							}
						} else {
							log(LogStatus.ERROR, "The records are not inactive of fund object", YesNo.Yes);
							sa.assertTrue(false, "The records are not inactive of fund object");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Record type of object feature of fund object",
								YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Record type of object feature of fund object");
					}
				} else {
					log(LogStatus.ERROR, "Not able to open Fund object", YesNo.Yes);
					sa.assertTrue(false, "Not able to open Fund object");
				}

			} else {
				log(LogStatus.ERROR, "The record type of fund is not removed", YesNo.No);
				sa.assertTrue(false, "The record type of fund is not removed");
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Search the " + object.Profiles + " object", YesNo.Yes);
			sa.assertTrue(false, "Not Able to Search the Object" + object.Profiles + " object");
		}

		if (status != 0) {
			status = 0;
			switchToDefaultContent(driver);
			if (setup.searchStandardOrCustomObject(projectName, mode, object.Profiles)) {
				log(LogStatus.INFO, "Profile has been open", YesNo.Yes);

				if (setup.removeRecordTypeOfObject("System Administrator", RecordType.Deal)) {
					log(LogStatus.INFO, "The record type of Deal has been removed", YesNo.No);
					sa.assertTrue(true, "The record type of Deal has been removed");

					if (setup.searchStandardOrCustomObject(projectName, mode, object.Deal)) {
						log(LogStatus.INFO, "Fund object has been open", YesNo.Yes);

						if (setup.clickOnObjectFeature(projectName, mode, object.Deal, ObjectFeatureName.recordTypes)) {
							log(LogStatus.INFO, "clicked on Record type of object feature of Deal object", YesNo.Yes);

							ArrayList<String> result = setup.inactiveRecordType(projectName, object.Deal);
							if (result.isEmpty()) {
								log(LogStatus.INFO, "All Record type have been inactive of Deal object", YesNo.Yes);
								ArrayList<String> result1 = setup.deleteAllRecordTypeOfObject();
								if (result1.isEmpty()) {
									log(LogStatus.INFO, "All Record type has been deleted of Deal object", YesNo.Yes);
									sa.assertTrue(true, "All Record type has been deleted of Deal object");
									status++;
								} else {
									log(LogStatus.ERROR, "The record type is not deleted of Deal object" + result1,
											YesNo.No);
									sa.assertTrue(false, "The record type is not deleted of Deal object" + result1);
								}
							} else {
								log(LogStatus.ERROR, "The records are not inactive of Deal object", YesNo.Yes);
								sa.assertTrue(false, "The records are not inactive of Deal object");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Record type of object feature of Deal object",
									YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Record type of object feature of Deal object");
						}
					} else {
						log(LogStatus.ERROR, "Not able to open Deal object", YesNo.Yes);
						sa.assertTrue(false, "Not able to open Deal object");
					}
				} else {
					log(LogStatus.ERROR, "The record type of Deal is not removed", YesNo.No);
					sa.assertTrue(false, "The record type of Deal is not removed");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Search the " + object.Profiles + " object", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Search the Object" + object.Profiles + " object");
			}

			if (status != 0) {
				switchToDefaultContent(driver);
				if (setup.searchStandardOrCustomObject(projectName, mode, object.Profiles)) {
					log(LogStatus.INFO, "Profile has been open", YesNo.Yes);

					if (setup.removeRecordTypeOfObject("System Administrator", RecordType.Fundraising)) {
						log(LogStatus.INFO, "The record type of Fundraising has been removed", YesNo.No);
						sa.assertTrue(true, "The record type of Fundraising has been removed");

						if (setup.searchStandardOrCustomObject(projectName, mode, object.Fundraising)) {
							log(LogStatus.INFO, "Fund object has been open", YesNo.Yes);

							if (setup.clickOnObjectFeature(projectName, mode, object.Fundraising,
									ObjectFeatureName.recordTypes)) {
								log(LogStatus.INFO, "clicked on Record type of object feature of Fundraising object",
										YesNo.Yes);

								ArrayList<String> result = setup.inactiveRecordType(projectName, object.Fundraising);
								if (result.isEmpty()) {
									log(LogStatus.INFO, "All Record type have been inactive of Fundraising object",
											YesNo.Yes);
									ArrayList<String> result1 = setup.deleteAllRecordTypeOfObject();
									if (result1.isEmpty()) {
										log(LogStatus.INFO, "All Record type has been deleted of Fundraising object",
												YesNo.Yes);
										sa.assertTrue(true, "All Record type has been deleted of Fundraising object");

										driver.close();
										driver.switchTo().window(parentWindow);

										for (int i = 0; i < tabName.length; i++) {
											if (bp.clickOnTab(projectName, tabName[i] + "s")) {
												log(LogStatus.INFO, "Clicked on the " + tabName[i] + "s tab",
														YesNo.Yes);
												if (click(driver, setup.getNewButton(30), "New Button",
														action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "clicked on New button", YesNo.No);

													xPath = "//span[text()='" + tabName[i] + " Information']";
													ele = FindElement(driver, xPath, tabName[i] + " popup",
															action.SCROLLANDBOOLEAN, 30);
													if (ele != null) {
														log(LogStatus.INFO, "Record type is not displaying on "
																+ tabName[i] + " tab", YesNo.No);
														sa.assertTrue(true, "Record type is not displaying on "
																+ tabName[i] + " tab");
													} else {
														log(LogStatus.ERROR,
																"Record type is displaying on " + tabName[i], YesNo.No);
														sa.assertTrue(false,
																"Record type is displaying on " + tabName[i]);
													}

												} else {
													log(LogStatus.ERROR, "Not able to click on New button", YesNo.No);
													sa.assertTrue(false, "Not able to click on New button");
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on the " + tabName[i] + " tab",
														YesNo.Yes);
												sa.assertTrue(false, "Not able to click on the " + tabName[i] + " tab");
											}

										}
									} else {
										log(LogStatus.ERROR,
												"The record type is not deleted of Fundraising object" + result1,
												YesNo.No);
										sa.assertTrue(false,
												"The record type is not deleted of Fundraising object" + result1);
									}
								} else {
									log(LogStatus.ERROR, "The records are not inactive of Fundraising object",
											YesNo.Yes);
									sa.assertTrue(false, "The records are not inactive of Fundraising object");
								}
							} else {
								log(LogStatus.ERROR,
										"Not able to click on Record type of object feature of Fundraising object",
										YesNo.Yes);
								sa.assertTrue(false,
										"Not able to click on Record type of object feature of Fundraising object");
							}
						} else {
							log(LogStatus.ERROR, "Not able to open Fundraising object", YesNo.Yes);
							sa.assertTrue(false, "Not able to open Fundraising object");
						}
					} else {
						log(LogStatus.ERROR, "The record type of Fundraising is not removed", YesNo.No);
						sa.assertTrue(false, "The record type of Fundraising is not removed");
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Search the " + object.Profiles + " object", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Search the Object" + object.Profiles + " object");
				}
			} else {
				log(LogStatus.ERROR, "The record type of deal is not deleted", YesNo.Yes);
				sa.assertTrue(false, "The record type of deal is not deleted");
			}
		} else {
			log(LogStatus.ERROR, "The record type of fund is not deleted", YesNo.Yes);
			sa.assertTrue(false, "The record type of fund is not deleted");
		}

		lp.CRMlogout();
		sa.assertAll();
	}

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
					if (setup.createPEUser(crmUser2FirstName, UserLastName, emailId, crmUserLience, crmUserProfile,
							null)) {
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
					descriptionBox, false)) {
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

		String[] institutionName = AS_FirmLegalName13.split("<Break>", -1);
		String[] recordType = AS_FirmRecordType13.split("<Break>", -1);

		String[] contactFirstNames = AS_ContactFirstName8.split("<Break>", -1);
		String[] contactLastNames = AS_ContactLastName8.split("<Break>", -1);
		String[] contactEmailIds = AS_ContactEmail8.split("<Break>", -1);
		String[] otherContactLabels = AS_ContactOtherLabelNames8.split("<Break>", -1);
		String[] otherContactValues = AS_ContactOtherLabelValues8.split("<Break>", -1);
		String[] contactInstitutionsName = AS_ContactLegalName8.split("<Break>", -1);

		String[] dealRecordType = AS_DealRecordType3.split("<Break>", -1);
		String[] dealName = AS_DealName3.split("<Break>", -1);
		String[] dealCompany = AS_DealCompany3.split("<Break>", -1);
		String[] dealStage = AS_DealStage3.split("<Break>", -1);
		String otherLabels = AS_DealOtherLabelNames3;
		String otherLabelValues = AS_DealOtherLabelValues3;

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

					ExcelUtils.writeData(AcuityDataSheetFilePath, otherLabelValues.split("<Break>", -1)[2], "Deal",
							excelLabel.Variable_Name, "AS_Deal2", excelLabel.Date);

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

		String firmName = AS_FirmLegalName14;
		String subTabName = "Acuity";
		String dealName = AS_DealName3;

		String contactName = AS_ContactName9;

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

		String firmName = AS_FirmLegalName15;
		String subTabName = "Acuity";
		String dealName = AS_DealName3;

		String contactName = AS_ContactName10;
		String dealTeamMember = crmUser1FirstName + " " + crmUser1LastName;
		String company = AS_FirmLegalName14;
		String stage = AS_DealStage3;
		String dateReceived = AS_DealDate3;

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

						ArrayList<String> result3 = BP.verifyRecordOnDealsSectionInAcuity(contactName, dealName, stage,
								stage, dateReceived);
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

		String firmName = AS_FirmLegalName15;
		String subTabName = "Acuity";
		String dealName = AS_DealName3;

		String contactName = AS_ContactName10;
		String company = AS_FirmLegalName14;
		String stage = AS_DealStage3;
		String dateReceived = AS_DealDate3;
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

		String firmName = AS_FirmLegalName15;
		String subTabName = "Acuity";
		String dealName = AS_DealName3;

		String contactName = AS_ContactName10;
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

		String[] fundNames = AS_FundName2.split("<Break>", -1);
		String[] fundTypes = AS_FundType2.split("<Break>", -1);
		String[] investmentCategories = AS_FundInvestmentCategory2.split("<Break>", -1);
		String otherLabelFields = null;
		String otherLabelValues = null;

		String ClosingDate = AS_FundraisingClosingDate1;
		String[] fundraisingNames = AS_FundraisingName1.split("<Break>", -1);
		String[] fundraisingsFundName = AS_FundraisingFundName1.split("<Break>", -1);
		String[] fundraisingsInstitutionName = AS_FundraisingLegalName1.split("<Break>", -1);
		String[] fundraisingsStageName = AS_FundraisingStageName1.split("<Break>", -1);
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

		String[] institutionName = AS_FirmLegalName16.split("<Break>", -1);
		String[] recordType = AS_FirmRecordType16.split("<Break>", -1);

		String[] contactFirstNames = AS_ContactFirstName11.split("<Break>", -1);
		String[] contactLastNames = AS_ContactLastName11.split("<Break>", -1);
		String[] contactEmailIds = AS_ContactEmail11.split("<Break>", -1);
		String[] otherContactLabels = AS_ContactOtherLabelNames11.split("<Break>", -1);
		String[] otherContactValues = AS_ContactOtherLabelValues11.split("<Break>", -1);

		String[][] callBasicSection = { { "Subject", AS_ATSubject13 }, { "Notes", AS_ATNotes13 },
				{ "Related_To", AS_ATRelatedTo13 } };
		/*
		 * String[][] meetingBasicSection = { { "Subject", "Nav Meeting" }, { "Notes",
		 * "Meeting from Navigation" }, { "Related_To",
		 * "Nav Cmp<break>Nav Ins<break>Nav Cont1" } };
		 */

		String[][] taskBasicSection = { { "Subject", AS_ATSubject14 }, { "Notes", AS_ATNotes14 },
				{ "Related_To", AS_ATRelatedTo14 } };
		List<String> activityTimeLines = new ArrayList<String>();
		activityTimeLines.add(AS_ATSubject13);
		/* activityTimeLines.add("Nav Meeting"); */
		activityTimeLines.add(AS_ATSubject14);

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

		log(LogStatus.INFO,
				"---------Now Going to Create Call: " + AS_ATSubject13 + " in Activity Timeline Section---------",
				YesNo.No);
		CommonLib.refresh(driver);
		if (BP.createActivityTimeline(projectName, true, AS_ATActivityType13, callBasicSection, null, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			ThreadSleep(4000);
			if (CommonLib.getText(driver, BP.taskDetailPageHeader(15), "Call: " + callBasicSection, action.BOOLEAN)
					.equalsIgnoreCase(AS_ATSubject13)) {
				log(LogStatus.PASS,
						"-----Task Detail Page: " + AS_ATSubject13 + " has been open after Call Create-----", YesNo.No);
			}

			else {
				log(LogStatus.FAIL,
						"-----Task Detail Page: " + AS_ATSubject13 + " has not been open after Call Create-----",
						YesNo.No);
				sa.assertTrue(false,
						"-----Task Detail Page: " + AS_ATSubject13 + " has not been open after Call Create-----");
			}

		} else {
			log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created");
		}

		/*
		 * log(LogStatus.INFO,
		 * "---------Now Going to Create Meeting in Activity Timeline Section---------",
		 * YesNo.No); CommonLib.refresh(driver); if
		 * (BP.createActivityTimeline(projectName, true, "Meeting", meetingBasicSection,
		 * null, null, null)) { log(LogStatus.PASS,
		 * "Activity timeline record has been created", YesNo.No);
		 * 
		 * ThreadSleep(4000); if (CommonLib .getText(driver,
		 * BP.eventDetailPageHeader(15), "Meeting: " + meetingBasicSection,
		 * action.BOOLEAN) .equalsIgnoreCase("Nav Meeting")) { log(LogStatus.PASS,
		 * "-----Event Detail Page: " + "Nav Meeting" +
		 * " has been open after Meeting Create-----", YesNo.No); }
		 * 
		 * else { log(LogStatus.FAIL, "-----Event Detail Page: " + "Nav Meeting" +
		 * " has not been open after Meeting Create-----", YesNo.No);
		 * sa.assertTrue(false, "-----Event Detail Page: " + "Nav Meeting" +
		 * " has not been open after Meeting Create-----"); }
		 * 
		 * } else { log(LogStatus.FAIL, "Activity timeline record is not created",
		 * YesNo.No); sa.assertTrue(false, "Activity timeline record is not created"); }
		 */

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + AS_ATSubject14 + " in Activity Timeline Section---------",
				YesNo.No);
		CommonLib.refresh(driver);
		if (BP.createActivityTimeline(projectName, true, AS_ATActivityType14, taskBasicSection, null, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			ThreadSleep(4000);
			if (CommonLib.getText(driver, BP.taskDetailPageHeader(15), "Task: " + taskBasicSection, action.BOOLEAN)
					.equalsIgnoreCase(AS_ATSubject14)) {
				log(LogStatus.PASS,
						"-----Task Detail Page: " + AS_ATSubject14 + " has been open after Task Create-----", YesNo.No);
			}

			else {
				log(LogStatus.FAIL,
						"-----Task Detail Page: " + AS_ATSubject14 + " has not been open after Task Create-----",
						YesNo.No);
				sa.assertTrue(false,
						"-----Task Detail Page: " + AS_ATSubject14 + " has not been open after Task Create-----");
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

					ArrayList<String> result1 = BP.verifyRecordOnInteractionCard(null, null, "Nav Call", null, false,
							false, null, null);
					if (result1.isEmpty()) {
						log(LogStatus.PASS, AS_ATSubject13 + " record has been verified on intraction", YesNo.No);

					} else {
						log(LogStatus.ERROR,
								AS_ATSubject13 + " record is not verified on intraction, Reason: " + result1, YesNo.No);
						sa.assertTrue(false,
								AS_ATSubject13 + " record is not verified on intraction, Reason: " + result1);
					}
					ArrayList<String> result2 = BP.verifyRecordOnInteractionCard(null, null, "Nav Meeting", null, false,
							false, null, null);
					if (result2.isEmpty()) {
						log(LogStatus.PASS, "Nav Meeting" + " record has been verified on intraction", YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"Nav Meeting" + " record is not verified on intraction, Reason: " + result1, YesNo.No);
						sa.assertTrue(false,
								"Nav Meeting" + " record is not verified on intraction, Reason: " + result1);
					}
					ArrayList<String> result3 = BP.verifyRecordOnInteractionCard(null, null, "Nav Task", null, false,
							false, null, null);
					if (result3.isEmpty()) {
						log(LogStatus.PASS, AS_ATSubject14 + " record has been verified on intraction", YesNo.No);

					} else {
						log(LogStatus.ERROR,
								AS_ATSubject14 + " record is not verified on intraction, Reason: " + result1, YesNo.No);
						sa.assertTrue(false,
								AS_ATSubject14 + " record is not verified on intraction, Reason: " + result1);
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

	@SuppressWarnings("unlikely-arg-type")
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

		String dealRecordTypeList = AS_DealRecordType4;

		String fundRecordTypeList = AS_FundRecordType3;
		String fundraisingRecordTypeList = AS_FundraisingRecordType2;
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

		String[] institutionName = AS_FirmLegalName17.split("<Break>", -1);
		String[] recordType = AS_FirmRecordType17.split("<Break>", -1);
		String[] labelsOfFirmPopUp = AS_FirmLabelNames17.split("<Break>", -1);
		String[] valuesOfFirmPopUp = AS_FirmLabelValues17.split("<Break>", -1);

		String[] contactFirstNames = AS_ContactFirstName12.split("<Break>", -1);
		String[] contactLastNames = AS_ContactLastName12.split("<Break>", -1);
		String[] contactEmailIds = AS_ContactEmail12.split("<Break>", -1);

		String[] dealRecordTypes = AS_DealRecordType5.split("<Break>", -1);
		String[] dealName = AS_DealName5.split("<Break>", -1);
		String[] dealCompany = AS_DealCompany5.split("<Break>", -1);
		String[] dealStage = AS_DealStage5.split("<Break>", -1);

		String[] fundNames = AS_FundName4.split("<Break>", -1);
		String[] fundRecordTypes = AS_FundRecordType4.split("<Break>", -1);
		String[] fundTypes = AS_FundType4.split("<Break>", -1);
		String[] investmentCategories = AS_FundInvestmentCategory4.split("<Break>", -1);
		String otherLabelFields = null;
		String otherLabelValues = null;

		String[] ClosingDates = AS_FundraisingClosingDate3.split("<Break>", -1);
		String[] fundraisingRecordTypes = AS_FundraisingRecordType3.split("<Break>", -1);
		String[] fundraisingNames = AS_FundraisingName3.split("<Break>", -1);
		String[] fundraisingsFundName = fundNames;
		String[] fundraisingsInstitutionName = AS_FundraisingLegalName3.split("<Break>", -1);
		String[] fundraisingsStageName = AS_FundraisingStageName3.split("<Break>", -1);
		String[] fundraisingsClosingDate = new String[ClosingDates.length];

		int loop = 0;
		for (String ClosingDate : ClosingDates) {
			fundraisingsClosingDate[loop] = convertDateFromOneFormatToAnother(ClosingDate, "MM/dd/yyyy", "dd/MMM/yyyy");
			loop++;
		}

//		String val=AS_ATDay1;
		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt("10"));
//        ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline" , excelLabel.Variable_Name, "AT_001", excelLabel.Advance_Due_Date);

		String[][] task1BasicSection = { { "Subject", AS_ATSubject15 }, { "Notes", AS_ATNotes15 },
				{ "Related_To", AS_ATRelatedTo15 } };

		String[][] task1AdvancedSection = { { "Due Date Only", AdvanceDueDate } };

		String[][] task2BasicSection = { { "Subject", AS_ATSubject16 }, { "Notes", AS_ATNotes16 },
				{ "Related_To", AS_ATRelatedTo16 } };

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
								if (sp.listOfRecordTypes().contains(dealRecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + dealRecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, dealRecordType[i], isMakeAvailable,
											profileForSelection, isMakeDefault, null, 10);
								}
							} else {
								isMakeDefault = false;

								if (sp.listOfRecordTypes().contains(dealRecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + dealRecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, dealRecordType[i], isMakeAvailable,
											profileForSelection, isMakeDefault, null, 10);
								}
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
								if (sp.listOfRecordTypes().contains(fundrecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + fundrecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, fundrecordType[i], isMakeAvailable,
											profileForSelection, isMakeDefault, null, 10);
								}
							} else {
								isMakeDefault = false;

								if (sp.listOfRecordTypes().contains(fundrecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + fundrecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, fundrecordType[i], isMakeAvailable,
											profileForSelection, isMakeDefault, null, 10);
								}
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

								if (sp.listOfRecordTypes().contains(fundraisingrecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + fundraisingrecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, fundraisingrecordType[i],
											isMakeAvailable, profileForSelection, isMakeDefault, null, 10);
								}
							} else {
								isMakeDefault = false;
								if (sp.listOfRecordTypes().contains(fundraisingrecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + fundraisingrecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, fundraisingrecordType[i],
											isMakeAvailable, profileForSelection, isMakeDefault, null, 10);
								}
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

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + AS_ATSubject15 + " in Activity Timeline Section---------",
				YesNo.No);
		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, AS_ATActivityType15, task1BasicSection,
					task1AdvancedSection, null, null)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

				ThreadSleep(4000);
				if (CommonLib.getText(driver, BP.taskDetailPageHeader(15), "Task: " + AS_ATSubject15, action.BOOLEAN)
						.equalsIgnoreCase(AS_ATSubject15)) {
					log(LogStatus.PASS,
							"-----Task Detail Page: " + AS_ATSubject15 + " has been open after Task Create-----",
							YesNo.No);
				}

				else {
					log(LogStatus.FAIL,
							"-----Task Detail Page: " + AS_ATSubject15 + " has not been open after Task Create-----",
							YesNo.No);
					sa.assertTrue(false,
							"-----Task Detail Page: " + AS_ATSubject15 + " has not been open after Task Create-----");
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

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + AS_ATSubject16 + " in Activity Timeline Section---------",
				YesNo.No);
		CommonLib.refresh(driver);

		home.notificationPopUpClose();
		if (BP.createActivityTimeline(projectName, true, AS_ATActivityType16, task2BasicSection, task2AdvancedSection,
				null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			ThreadSleep(4000);
			if (CommonLib.getText(driver, BP.taskDetailPageHeader(15), "Task: " + AS_ATSubject16, action.BOOLEAN)
					.equalsIgnoreCase(AS_ATSubject16)) {
				log(LogStatus.PASS,
						"-----Task Detail Page: " + AS_ATSubject16 + " has been open after Task Create-----", YesNo.No);
			}

			else {
				log(LogStatus.FAIL,
						"-----Task Detail Page: " + AS_ATSubject16 + " has not been open after Task Create-----",
						YesNo.No);
				sa.assertTrue(false,
						"-----Task Detail Page: " + AS_ATSubject16 + " has not been open after Task Create-----");
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

		String searchItemInResearch = AS_ResearchString1;

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

		String[] searchItemsInResearch = AS_ResearchString2.split("<Break>", -1);

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

		String[] searchItemsInResearch = AS_ResearchString3.split("<Break>", -1);

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

		String[] searchItemsInResearch = AS_ResearchString4.split("<Break>", -1);

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

		String[] searchItemsInResearch = AS_ResearchString5.split("<Break>", -1);

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

		String[] searchItemsInResearch = AS_ResearchString5.split("<Break>", -1);

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
					if (setup.createPEUser(crmUser4FirstName, UserLastName, emailId, crmUserLience, crmUser4Profile,
							null)) {
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
