
package com.navatar.scripts;

import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.convertDateFromOneFormatToAnother;
import static com.navatar.generic.CommonLib.getURL;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.refresh;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;

import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;

import com.navatar.pageObjects.LoginPageBusinessLayer;

import com.relevantcodes.extentreports.LogStatus;

public class AcuityMeetingNotesNotificationReminder extends BaseLib {

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc047_CreateATaskAndAddTheNotesFromEditCommentButtonOfTaskLayout(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt("1"));

		ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"AMNNR_017", excelLabel.Advance_Due_Date);

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjecName = "Send Invoice";
		String task1Notes = "";
		String relatedTo = "Con 1<break>con 2<break>con 3<break>Sumo Logic<break>Houlihan Lokey<break>Vertica";
		String priority = "Normal";
		String status = "Not started";

		String[][] task1BasicSection = { { "Subject", task1SubjecName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };
		String task1ButtonName = "Task";
		String recordName = "Sumo Logic";
		String recordType = "Intermediary";
		String updatedCommentOfTask = "This is to notify that @ con4, @con5 should be in loop";
		String[] relatedToVerify = "Con 1<break>con 2<break>+5".split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjecName + " in Activity Timeline Section---------",
				YesNo.No);
		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, null)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjecName + " in Interaction Section---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjecName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjecName + " record has been verified on intraction------", YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntractionAndAbleToEditCommentsOfTask(driver,
								task1SubjecName, updatedCommentOfTask)) {
							log(LogStatus.PASS, "------" + task1SubjecName
									+ " record is able to redirect to Task Detail Page and is able to edit the comment : "
									+ updatedCommentOfTask + "------", YesNo.No);

							CommonLib.refresh(driver);
							ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
									task1SubjecName, updatedCommentOfTask, true, false, relatedToVerify, null);
							if (updatedresult.isEmpty()) {
								log(LogStatus.PASS,
										"------" + task1SubjecName + " record has been verified on intraction------",
										YesNo.No);

							} else {
								log(LogStatus.ERROR, "------" + task1SubjecName
										+ " record is not verified on intraction, Reason: " + updatedresult + "------",
										YesNo.No);
								sa.assertTrue(false, "------" + task1SubjecName
										+ " record is not verified on intraction, Reason: " + updatedresult + "------");
							}
						} else {
							log(LogStatus.ERROR, "------" + task1SubjecName
									+ " record is either not able to redirect to Task Detail Page or is not able to edit the comment : "
									+ updatedCommentOfTask + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjecName
									+ " record is either not able to redirect to Task Detail Page or is not able to edit the comment : "
									+ updatedCommentOfTask + "------");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjecName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjecName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc048_CreateATaskAndAddTheNotesAndVerifySuggestedTagPopUp(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt("0"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjecName = "Send Letter";
		String task1Notes = "This is to check Con 2, Con 3, Logic should be the part of the deal kind";
		String relatedTo = "Vertica";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";

		String[][] task1BasicSection = { { "Subject", task1SubjecName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[] SuggestedTags = "con 2==Contact<break>con 3==Contact<break>Sumo Logic==Firm<break>Sumo Kind==Firm<break>Sumo Kind==Deal"
				.split("<break>", -1);
		String[] relatedToArray = new String[SuggestedTags.length + relatedTo.split("<break>", -1).length];

		int relatedToLoop = 0;
		int suggestedLoop = 0;
		for (String related : relatedTo.split("<break>", -1)) {
			relatedToArray[relatedToLoop] = related;
			relatedToLoop++;
		}
		for (String suggestedTag : SuggestedTags) {
			relatedToArray[relatedToLoop + suggestedLoop] = suggestedTag.split("==", -1)[0];
			suggestedLoop++;

		}

		String recordName = "Vertica";
		String recordType = "Company";
		String updatedNotesOfTask = "This is to notify that Areca and Arrow should be in loop of vertica";
		String[] relatedToVerify = "con 2<break>con 3<break>+5".split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "con 14<break>con 34".split("<break>", -1);
		String[] updatedRelatedToVerify = "con 2<break>con 3<break>+7".split("<break>");

		String[] updatedRelatedToArray = new String[relatedToArray.length + updatedSuggestedTags.length];

		int updatedrelatedToLoop = 0;
		int updatedsuggestedLoop = 0;
		for (String related : relatedToArray) {
			updatedRelatedToArray[updatedrelatedToLoop] = related;
			updatedrelatedToLoop++;
		}
		for (String suggestedTag : updatedSuggestedTags) {
			updatedRelatedToArray[updatedrelatedToLoop + updatedsuggestedLoop] = suggestedTag;
			updatedsuggestedLoop++;

		}

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjecName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */
		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimelineAlsoVerifyUIOfSuggestedTag(projectName, true, task1ButtonName,
					task1BasicSection, task1AdvancedSection, null, SuggestedTags)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjecName + " in Interaction Section---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjecName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjecName + " record has been verified on intraction------", YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjecName)) {
							log(LogStatus.PASS, "------" + task1SubjecName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjecName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjecName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjecName, 20),
								"Edit Note Button of: " + task1SubjecName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1SubjecName, task1Notes,
											relatedToArray);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjecName, 20),
										"Edit Note Button of: " + task1SubjecName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjecName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjecName
															+ " record has been verified on intraction------",
													YesNo.No);

											String url2 = getURL(driver, 10);

											if (click(driver, BP.editButtonOnInteractionCard(task1SubjecName, 20),
													"Edit Note Button of: " + task1SubjecName,
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

												ThreadSleep(10000);
												ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
														.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
																task1SubjecName, updatedNotesOfTask,
																updatedRelatedToArray);
												if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
													log(LogStatus.INFO,
															"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
															YesNo.No);

												} else {
													log(LogStatus.ERROR,
															"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason:"
																	+ NotesPopUpPrefilledNegativeResultUpdated,
															YesNo.No);
													sa.assertTrue(false,
															"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason:"
																	+ NotesPopUpPrefilledNegativeResultUpdated);
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
												sa.assertTrue(false, "Not able to click on Edit Note button");
											}

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjecName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjecName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Edit Note button");
								}
							} else {
								log(LogStatus.ERROR,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjecName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjecName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record of record type " + recordType,
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record of record type " + recordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc045_CreateTheRecordsFromDataSheetAndVerifyInTheOrg(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);

		String[] fundNames = "Mutual Fund<Break>Sumo Kind Fund".split("<Break>", -1);
		String[] fundTypes = "Fund<Break>Fund".split("<Break>", -1);
		String[] investmentCategories = "Fund<Break>Fund".split("<Break>", -1);
		String otherLabelFields = null;
		String otherLabelValues = null;

		String[] fundraisingNames = "FC Fundraising<Break>Sumo Kind Fundraising".split("<Break>", -1);
		String[] fundraisingsFundName = "Mutual Fund<Break>Sumo Kind Fund".split("<Break>", -1);
		String[] fundraisingsInstitutionName = "Acc 4<Break>Acc 12".split("<Break>", -1);

		String dealRecordTypes = null;
		String[] dealName = "Demo Deal<Break>Sumo Kind".split("<Break>", -1);
		String[] dealCompany = "Acc 7<Break>Sumo Kind".split("<Break>", -1);
		String[] dealStage = "Deal Received<Break>NDA Signed".split("<Break>", -1);

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

					if (fr.createFundRaising(environment, "Lightning", fundraisingName,
							fundraisingsFundName[fundraisingLoopCount],
							fundraisingsInstitutionName[fundraisingLoopCount], null, null, null, null, null, null,
							null)) {
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

		log(LogStatus.INFO, "---------Now Going to Create " + tabObj4 + "---------", YesNo.No);
		for (int i = 0; i < dealName.length; i++) {
			if (lp.clickOnTab(projectName, tabObj4)) {
				log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
				ThreadSleep(3000);
				if (dp.createDeal(dealRecordTypes, dealName[i], dealCompany[i], dealStage[i], null, null)) {
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

	}

	/*
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * AcuityMNNRTc045_CreateATaskAndAddTheNotesWithoutUsingAtSignAndTagFromInteractionSectionByClickingOnEditNotesButton(
	 * String projectName) {
	 * 
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * 
	 * String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30",
	 * "MM/dd/yyyy", Integer.parseInt("0")); String getAdvanceDueDate =
	 * AdvanceDueDate; String task1SubjecName = "Send Letter"; String task1Notes =
	 * ""; String relatedTo = "con 2<break>con 3<break>Sumo Logic<break>Vertica";
	 * String[] relatedToArray = relatedTo.split("<break>", -1); String priority =
	 * "Normal"; String status = "In Progress"; String task1ButtonName = "Task";
	 * 
	 * String[][] task1BasicSection = { { "Subject", task1SubjecName }, { "Notes",
	 * task1Notes }, { "Related_To", relatedTo } };
	 * 
	 * String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate }, {
	 * "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status",
	 * status }, { "Priority", priority } };
	 * 
	 * String recordName = "Vertica"; String recordType = "Company"; String
	 * updatedNotesOfTask =
	 * "This is to notify that Areca and Arrow should be in loop of vertica";
	 * String[] relatedToVerify = "con 2<break>con 3<break>+3".split("<break>");
	 * String[] updatedRelatedToVerify =
	 * "con 2<break>con 3<break>+5".split("<break>");
	 * 
	 * String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
	 * String[] updatedSuggestedTags = "con 14<break>con 34".split("<break>", -1);
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword);
	 * 
	 * log(LogStatus.INFO, "---------Now Going to Create Task: " + task1SubjecName +
	 * " in Activity Timeline Section---------", YesNo.No);
	 * 
	 * 
	 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
	 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
	 * excelLabel.Advance_Due_Date);
	 * 
	 * CommonLib.refresh(driver); if (lp.clickOnTab(projectName, TabName.HomeTab)) {
	 * log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
	 * home.notificationPopUpClose(); if (BP.createActivityTimeline(projectName,
	 * true, task1ButtonName, task1BasicSection, task1AdvancedSection, null, null))
	 * { log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);
	 * 
	 * } else { log(LogStatus.FAIL, "Activity timeline record is not created",
	 * YesNo.No); sa.assertTrue(false, "Activity timeline record is not created"); }
	 * 
	 * } else { sa.assertTrue(false, "Not Able to Click on Tab : " +
	 * TabName.HomeTab); log(LogStatus.SKIP, "Not Able to Click on Tab : " +
	 * TabName.HomeTab, YesNo.Yes); }
	 * 
	 * log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjecName +
	 * " in Interaction Section---------", YesNo.No); if (lp.clickOnTab(projectName,
	 * tabObj1)) {
	 * 
	 * log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
	 * 
	 * if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
	 * TabName.InstituitonsTab, recordType, recordName, 30)) { log(LogStatus.INFO,
	 * recordName + " record of record type " + recordType + " has been open",
	 * YesNo.No); ThreadSleep(4000); if (BP.clicktabOnPage("Acuity")) {
	 * log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No); ArrayList<String>
	 * result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, task1SubjecName,
	 * task1Notes, true, false, relatedToVerify); if (result.isEmpty()) {
	 * log(LogStatus.PASS, "------" + task1SubjecName +
	 * " record has been verified on intraction------", YesNo.No); if
	 * (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjecName)) {
	 * log(LogStatus.PASS, "------" + task1SubjecName +
	 * " record is able to redirect to Task Detail Page" + "------", YesNo.No);
	 * 
	 * } else { log(LogStatus.ERROR, "------" + task1SubjecName +
	 * " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
	 * sa.assertTrue(false, "------" + task1SubjecName +
	 * " record is not able to redirect to Task Detail Page" + "------"); }
	 * 
	 * String url = getURL(driver, 10);
	 * 
	 * if (click(driver, BP.editButtonOnInteractionCard(task1SubjecName, 20),
	 * "Edit Note Button of: " + task1SubjecName, action.SCROLLANDBOOLEAN)) {
	 * log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
	 * 
	 * ThreadSleep(10000); ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
	 * .verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1SubjecName,
	 * task1Notes, relatedToArray); if (NotesPopUpPrefilledNegativeResult.isEmpty())
	 * { log(LogStatus.INFO,
	 * "Notes Popup has been verified and Notes popup is opening in same page with prefilled value"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Notes Popup has been verified and Notes popup is opening in same page with prefilled value, Reason: "
	 * + NotesPopUpPrefilledNegativeResult); refresh(driver); ThreadSleep(2000); if
	 * (click(driver, BP.editButtonOnInteractionCard(task1SubjecName, 20),
	 * "Edit Note Button of: " + task1SubjecName, action.SCROLLANDBOOLEAN)) {
	 * log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No); if
	 * (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
	 * null, updatedSuggestedTags, null)) { log(LogStatus.PASS,
	 * "Activity timeline record has been Updated", YesNo.No);
	 * 
	 * CommonLib.refresh(driver); ArrayList<String> updatedresult =
	 * BP.verifyRecordOnInteractionCard( getAdvanceDueDate, task1SubjecName,
	 * updatedNotesOfTask, true, false, updatedRelatedToVerify); if
	 * (updatedresult.isEmpty()) { log(LogStatus.PASS, "------" + task1SubjecName +
	 * " record has been verified on intraction------", YesNo.No);
	 * 
	 * } else { log(LogStatus.ERROR, "------" + task1SubjecName +
	 * " record is not verified on intraction, Reason: " + updatedresult + "------",
	 * YesNo.No); sa.assertTrue(false, "------" + task1SubjecName +
	 * " record is not verified on intraction, Reason: " + updatedresult +
	 * "------"); }
	 * 
	 * } else { log(LogStatus.FAIL, "Activity timeline record has not Updated",
	 * YesNo.No); sa.assertTrue(false, "Activity timeline record has not Updated");
	 * } } else { log(LogStatus.ERROR, "Not able to click on Edit Note button",
	 * YesNo.No); sa.assertTrue(false, "Not able to click on Edit Note button"); } }
	 * else { log(LogStatus.ERROR,
	 * "Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value"
	 * , YesNo.No); sa.assertTrue(false,
	 * "Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value"
	 * ); } } else { log(LogStatus.ERROR, "Not able to click on Edit Note button",
	 * YesNo.No); sa.assertTrue(false, "Not able to click on Edit Note button"); }
	 * 
	 * } else { log(LogStatus.ERROR, "------" + task1SubjecName +
	 * " record is not verified on intraction, Reason: " + result + "------",
	 * YesNo.No); sa.assertTrue(false, "------" + task1SubjecName +
	 * " record is not verified on intraction, Reason: " + result + "------"); } }
	 * else { log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
	 * sa.assertTrue(false, "Not able to click on Acuity Tab"); }
	 * 
	 * } else { log(LogStatus.ERROR, "Not able to open " + recordName +
	 * " record of record type " + recordType, YesNo.No); sa.assertTrue(false,
	 * "Not able to open " + recordName + " record of record type " + recordType); }
	 * } else { log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1,
	 * YesNo.No); sa.assertTrue(false, "Not able to click on Tab : " + tabObj1); }
	 * 
	 * ThreadSleep(5000); lp.CRMlogout(); sa.assertAll(); }
	 */

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc049_CreateATaskWithoutMeetingNotesAndTagFromInteractionSectionByClickingOnEditNotesButton(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt("-1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjecName = "Introduction";
		String task1Notes = "";
		String relatedTo = "con 5<break>con 6<break>Sumo Logic<break>Vertica<break>Demo Deal<break>Mutual Fund";
		String[] relatedToArray = relatedTo.split("<break>", -1);
		String priority = "Normal";
		String status = "Completed";
		String task1ButtonName = "Task";

		String[][] task1BasicSection = { { "Subject", task1SubjecName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "con 5";

		String updatedNotesOfTask = "areca  moss fundraising should be tagged";
		String[] relatedToVerify = "con 5<break>con 6<break>+5".split("<break>");
		String[] updatedRelatedToVerify = "con 5<break>con 6<break>+8".split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "All Records Select".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjecName + " in Activity Timeline Section---------",
				YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, null)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Task: " + task1SubjecName + " in Interaction Section---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjecName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjecName + " record has been verified on intraction------", YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjecName)) {
							log(LogStatus.PASS, "------" + task1SubjecName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjecName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjecName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjecName, 20),
								"Edit Note Button of: " + task1SubjecName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1SubjecName, task1Notes,
											relatedToArray);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);
								sa.assertTrue(true,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value, Reason: "
												+ NotesPopUpPrefilledNegativeResult);
								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjecName, 20),
										"Edit Note Button of: " + task1SubjecName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjecName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjecName
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjecName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjecName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------");
										}

									} else {
										log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
										sa.assertTrue(false, "Activity timeline record has not Updated");
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
						log(LogStatus.ERROR, "------" + task1SubjecName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjecName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

}
