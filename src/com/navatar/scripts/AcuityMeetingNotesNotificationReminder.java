
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
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;

import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
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

		String task1SubjectName = "Send Invoice";
		String task1Notes = "";
		String relatedTo = "Con 1<break>con 2<break>con 3<break>Sumo Logic<break>Houlihan Lokey<break>Vertica";
		String priority = "Normal";
		String status = "Not started";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
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
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
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
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntractionAndAbleToEditCommentsOfTask(driver,
								task1SubjectName, updatedCommentOfTask)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page and is able to edit the comment : "
									+ updatedCommentOfTask + "------", YesNo.No);

							CommonLib.refresh(driver);
							ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
									task1SubjectName, updatedCommentOfTask, true, false, relatedToVerify, null);
							if (updatedresult.isEmpty()) {
								log(LogStatus.PASS,
										"------" + task1SubjectName + " record has been verified on intraction------",
										YesNo.No);

							} else {
								log(LogStatus.ERROR, "------" + task1SubjectName
										+ " record is not verified on intraction, Reason: " + updatedresult + "------",
										YesNo.No);
								sa.assertTrue(false, "------" + task1SubjectName
										+ " record is not verified on intraction, Reason: " + updatedresult + "------");
							}
						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is either not able to redirect to Task Detail Page or is not able to edit the comment : "
									+ updatedCommentOfTask + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is either not able to redirect to Task Detail Page or is not able to edit the comment : "
									+ updatedCommentOfTask + "------");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
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
		String task1SubjectName = "Send Letter";
		String task1Notes = "This is to check Con 2, Con 3, Logic should be the part of the deal kind";
		String relatedTo = "Vertica";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
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
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
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
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1SubjectName, task1Notes,
											relatedToArray);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

											String url2 = getURL(driver, 10);

											if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
													"Edit Note Button of: " + task1SubjectName,
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

												ThreadSleep(10000);
												ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
														.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
																task1SubjectName, updatedNotesOfTask,
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
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
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
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
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
		CustomObjPageBusinessLayer customObjPageBusinessLayer = new CustomObjPageBusinessLayer(driver);

		String[] userTypesToGivePermissions = { "PE Standard User" };

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

		String[] customObjectNames = "Acuity Notes Custom Object".split("<Break>", -1);

		String tabName = "Test Custom Objects";
		String textBoxRecordLabel = "Test Custom Object Name";
		String[] textBoxRecordNames = "Golden Ret<Break>Pothoscust<Break>custareca<Break>Custom Object 1.1<Break>Custom Object 1.2<Break>Custom Object 1.3"
				.split("<Break>", -1);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*
		 * int fundStatus = 0; int fundLoopCount = 0; for (String fundName : fundNames)
		 * {
		 * 
		 * log(LogStatus.INFO, "---------Now Going to Create Fund Named: " + fundName +
		 * "---------", YesNo.No); if (fund.clickOnTab(environment, mode,
		 * TabName.FundsTab)) {
		 * 
		 * if (fund.createFund(projectName, fundName, fundTypes[fundLoopCount],
		 * investmentCategories[fundLoopCount], otherLabelFields, otherLabelValues)) {
		 * appLog.info("Fund is created Successfully: " + fundName); fundStatus++;
		 * 
		 * } else { appLog.error("Not able to click on fund: " + fundName);
		 * sa.assertTrue(false, "Not able to click on fund: " + fundName);
		 * log(LogStatus.ERROR, "Not able to click on fund: " + fundName, YesNo.Yes); }
		 * } else { appLog.error("Not able to click on Fund tab so cannot create Fund: "
		 * + fundName); sa.assertTrue(false,
		 * "Not able to click on Fund tab so cannot create Fund: " + fundName); }
		 * ThreadSleep(2000); fundLoopCount++;
		 * 
		 * }
		 * 
		 * if (fundStatus == fundLoopCount) { int fundraisingLoopCount = 0; for (String
		 * fundraisingName : fundraisingNames) { log(LogStatus.INFO,
		 * "---------Now Going to Create Fundraising Named: " + fundraisingName +
		 * "---------", YesNo.No); if (BP.clickOnTab(environment, mode,
		 * TabName.FundraisingsTab)) {
		 * 
		 * if (fr.createFundRaising(environment, "Lightning", fundraisingName,
		 * fundraisingsFundName[fundraisingLoopCount],
		 * fundraisingsInstitutionName[fundraisingLoopCount], null, null, null, null,
		 * null, null, null)) { appLog.info("fundraising is created : " +
		 * fundraisingName); } else { appLog.error("Not able to create fundraising: " +
		 * fundraisingName); sa.assertTrue(false, "Not able to create fundraising: " +
		 * fundraisingName); }
		 * 
		 * } else { appLog.error(
		 * "Not able to click on fundraising tab so cannot create fundraising: " +
		 * fundraisingName); sa.assertTrue(false,
		 * "Not able to click on fundraising tab so cannot create fundraising: " +
		 * fundraisingName); } ThreadSleep(2000);
		 * 
		 * fundraisingLoopCount++;
		 * 
		 * }
		 * 
		 * } else {
		 * appLog.error("No Fund is created, So not able to Create Fundraising: " +
		 * fundraisingNames); sa.assertTrue(false,
		 * "No Fund is created, So not able to Create Fundraising: " +
		 * fundraisingNames); }
		 * 
		 * log(LogStatus.INFO, "---------Now Going to Create " + tabObj4 + "---------",
		 * YesNo.No); for (int i = 0; i < dealName.length; i++) { if
		 * (lp.clickOnTab(projectName, tabObj4)) { log(LogStatus.INFO, "Click on Tab : "
		 * + tabObj4, YesNo.No); ThreadSleep(3000); if (dp.createDeal(dealRecordTypes,
		 * dealName[i], dealCompany[i], dealStage[i], null, null)) { log(LogStatus.INFO,
		 * dealName[i] + " deal has been created", YesNo.No);
		 * 
		 * } else { log(LogStatus.ERROR, dealName[i] + " deal is not created",
		 * YesNo.No); sa.assertTrue(false, dealName[i] + " deal is not created"); } }
		 * else { log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab",
		 * YesNo.No); sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		 * } }
		 */

		for (String textBoxRecordName : textBoxRecordNames) {

			if (BP.createRecordForCustomObject(projectName, tabName, textBoxRecordLabel, textBoxRecordName)) {
				log(LogStatus.INFO, "Record: " + textBoxRecordName + " has been Created under: " + tabName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Record: " + textBoxRecordName + " has not been Created under: " + tabName,
						YesNo.No);
				sa.assertTrue(false, "Record: " + textBoxRecordName + " has not been Created under: " + tabName);
			}
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

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
	 * AdvanceDueDate; String task1SubjectName = "Send Letter"; String task1Notes =
	 * ""; String relatedTo = "con 2<break>con 3<break>Sumo Logic<break>Vertica";
	 * String[] relatedToArray = relatedTo.split("<break>", -1); String priority =
	 * "Normal"; String status = "In Progress"; String task1ButtonName = "Task";
	 * 
	 * String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes",
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
	 * log(LogStatus.INFO, "---------Now Going to Create Task: " + task1SubjectName
	 * + " in Activity Timeline Section---------", YesNo.No);
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
	 * log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjectName
	 * + " in Interaction Section---------", YesNo.No); if
	 * (lp.clickOnTab(projectName, tabObj1)) {
	 * 
	 * log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
	 * 
	 * if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
	 * TabName.InstituitonsTab, recordType, recordName, 30)) { log(LogStatus.INFO,
	 * recordName + " record of record type " + recordType + " has been open",
	 * YesNo.No); ThreadSleep(4000); if (BP.clicktabOnPage("Acuity")) {
	 * log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No); ArrayList<String>
	 * result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate,
	 * task1SubjectName, task1Notes, true, false, relatedToVerify); if
	 * (result.isEmpty()) { log(LogStatus.PASS, "------" + task1SubjectName +
	 * " record has been verified on intraction------", YesNo.No); if
	 * (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
	 * log(LogStatus.PASS, "------" + task1SubjectName +
	 * " record is able to redirect to Task Detail Page" + "------", YesNo.No);
	 * 
	 * } else { log(LogStatus.ERROR, "------" + task1SubjectName +
	 * " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
	 * sa.assertTrue(false, "------" + task1SubjectName +
	 * " record is not able to redirect to Task Detail Page" + "------"); }
	 * 
	 * String url = getURL(driver, 10);
	 * 
	 * if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
	 * "Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
	 * log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
	 * 
	 * ThreadSleep(10000); ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
	 * .verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1SubjectName,
	 * task1Notes, relatedToArray); if (NotesPopUpPrefilledNegativeResult.isEmpty())
	 * { log(LogStatus.INFO,
	 * "Notes Popup has been verified and Notes popup is opening in same page with prefilled value"
	 * , YesNo.No); sa.assertTrue(true,
	 * "Notes Popup has been verified and Notes popup is opening in same page with prefilled value, Reason: "
	 * + NotesPopUpPrefilledNegativeResult); refresh(driver); ThreadSleep(2000); if
	 * (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
	 * "Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
	 * log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No); if
	 * (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
	 * null, updatedSuggestedTags, null)) { log(LogStatus.PASS,
	 * "Activity timeline record has been Updated", YesNo.No);
	 * 
	 * CommonLib.refresh(driver); ArrayList<String> updatedresult =
	 * BP.verifyRecordOnInteractionCard( getAdvanceDueDate, task1SubjectName,
	 * updatedNotesOfTask, true, false, updatedRelatedToVerify); if
	 * (updatedresult.isEmpty()) { log(LogStatus.PASS, "------" + task1SubjectName +
	 * " record has been verified on intraction------", YesNo.No);
	 * 
	 * } else { log(LogStatus.ERROR, "------" + task1SubjectName +
	 * " record is not verified on intraction, Reason: " + updatedresult + "------",
	 * YesNo.No); sa.assertTrue(false, "------" + task1SubjectName +
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
	 * } else { log(LogStatus.ERROR, "------" + task1SubjectName +
	 * " record is not verified on intraction, Reason: " + result + "------",
	 * YesNo.No); sa.assertTrue(false, "------" + task1SubjectName +
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
		String task1SubjectName = "Introduction";
		String task1Notes = "";
		String relatedTo = "con 5<break>con 6<break>Sumo Logic<break>Vertica<break>Demo Deal<break>Mutual Fund";
		String[] relatedToArray = relatedTo.split("<break>", -1);
		String priority = "Normal";
		String status = "Completed";
		String task1ButtonName = "Task";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
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
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
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
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1SubjectName, task1Notes,
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
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
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
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
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

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc050_CreateATaskWithoutMeetingNotesAndAddTheNotesFromEditButton(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt("-1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Sales Meeting";
		String task1Notes = "";
		String relatedTo = "Jhon<break>con 10<break>Sumo Logic";
		String[] relatedToArray = relatedTo.split("<break>", -1);
		String priority = "Normal";
		String status = "Completed";
		String task1ButtonName = "Task";
		String value = CommonLib.convertDateFromOneFormatToAnother(getAdvanceDueDate, "MM/dd/yyyy", "MMM dd, yyyy");

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Jhon";

		String updatedNotesOfTask = "Golden Ret";
		String[] relatedToVerify = "Jhon<break>con 10<break>+2".split("<break>");
		String[] updatedRelatedToVerify = "Jhon<break>con 10<break>+3".split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "Golden Ret".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
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
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntractionAndAbleToClickOnEditButtonInTaskDetailPage(
								driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page and clicked on Edit Button"
									+ "------", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(10000);

								if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null, null,
										updatedSuggestedTags, null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

										if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
												"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
													YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdateBasicSection, task1AdvancedSection, null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);
												CommonLib.ThreadSleep(3000);
												driver.close();
												CommonLib.ThreadSleep(3000);

												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											}

										} else {
											log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
													YesNo.Yes);
											driver.close();

											driver.switchTo().window(driver.getWindowHandle());
										}

									} else {
										log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
										driver.close();
										driver.switchTo().window(driver.getWindowHandle());
									}
									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true, false,
											updatedRelatedToVerify, null);
									if (updatedresult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " record has been verified on intraction------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------",
												YesNo.No);
										sa.assertTrue(false,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------");
									}

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");
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
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
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

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc046_VerifyTheUIOfMeetingNotesPopUpFromAddNoteButtonPlacedOnAcuityTabInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);

		String task1ButtonName = "Task";

		String task1SubjectName = "";
		String task1Notes = "";

		String getAdvanceDueDate = "";
		String priority = "Normal";
		String status = "Not Started";

		String taskSectionSubject = "";
		String taskSectionStatus = "Not Started";
		String taskSectionDueDateOnly = "";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1TaskSection = { { "Subject", taskSectionSubject },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", taskSectionStatus },
				{ "Due Date Only", taskSectionDueDateOnly } };

		List<String> expectedFooterList = new ArrayList<String>();
		expectedFooterList.add("Cancel");
		expectedFooterList.add("Save");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Verify UI of Task: " + task1SubjectName
				+ " in Activity Timeline Section---------", YesNo.No);

		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
//			home.notificationPopUpClose();

			WebElement ele;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Create.toString(), action.BOOLEAN,
					30)) {
				log(LogStatus.INFO, "Able to Click on " + task1ButtonName + " Going to click on : "
						+ NavigationMenuItems.Create.toString() + " for creation ", YesNo.No);
				ele = npbl.getNavigationLabel(projectName, task1ButtonName, action.BOOLEAN, 10);
				if (ele != null && CommonLib.click(driver, ele, task1ButtonName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on " + task1ButtonName + " so going for creation", YesNo.No);
					String url = getURL(driver, 10);

					ThreadSleep(10000);

					String headerName = CommonLib.getText(driver, BP.notePopUpHeading(), "Heading", action.BOOLEAN);
					String expectedHeaderName = "Note";
					if (expectedHeaderName.equals(headerName)) {
						log(LogStatus.INFO, "PopUp Name has been verified to: " + headerName, YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "PopUp Name has been not been verified, Expected: " + expectedHeaderName
								+ " but Actual: " + headerName, YesNo.No);
						sa.assertTrue(false, "PopUp Name has been not been verified, Expected: " + expectedHeaderName
								+ " but Actual: " + headerName);
					}

					if (BP.notePopUpCrossButton(7) != null) {
						log(LogStatus.INFO, "Cross Button is visible in " + expectedHeaderName + " Popup", YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "Cross Button is not visible in " + expectedHeaderName + " Popup",
								YesNo.No);
						sa.assertTrue(false, "Cross Button is not visible in " + expectedHeaderName + " Popup");
					}

					if (BP.notePopUpAddMoreButton(7) != null) {
						log(LogStatus.INFO, "Add More Button is present in " + expectedHeaderName + " Popup", YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "Add More Button is not present in " + expectedHeaderName + " Popup",
								YesNo.No);
						sa.assertTrue(false, "Add More Button is not present in " + expectedHeaderName + " Popup");
					}

					List<String> actualFooterList = BP.notePopUpFooterButtons().stream()
							.map(x -> CommonLib.getText(driver, x, "Footer", action.BOOLEAN))
							.collect(Collectors.toList());

					if (actualFooterList.containsAll(expectedFooterList)) {
						log(LogStatus.INFO, "Footer List Matched: " + expectedFooterList, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Footer List not Matched, Expected: " + expectedFooterList + ", Actual: "
								+ expectedFooterList, YesNo.No);
						sa.assertTrue(false, "Footer List not Matched, Expected: " + expectedFooterList + ", Actual: "
								+ expectedFooterList);
					}

					ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
							.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
									task1AdvancedSection, task1TaskSection);
					if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
						log(LogStatus.INFO,
								"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
								YesNo.No);

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
					log(LogStatus.ERROR,
							"Not Able to Click on " + task1ButtonName + " so cannot create data related to this ",
							YesNo.Yes);
					sa.assertTrue(false,
							"Not Able to Click on " + task1ButtonName + " so cannot create data related to this ");

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + task1ButtonName + " for creation ", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + task1ButtonName + " for creation ");

			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc051_CreateATaskWithMeetingNotesAndAddTheNotesFromEditButton(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt("2"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Marketing Strategy";
		String task1Notes = "We as an organization need to have certain strategy towards our marketing approch with Vertica and sumo logic Firm";
		String relatedTo = "Max<break>Jhon<break>con 11";
		String[] relatedToArray = relatedTo.split("<break>", -1);
		String priority = "Normal";
		String status = "Not Started";
		String task1ButtonName = "Task";
		String value = CommonLib.convertDateFromOneFormatToAnother(getAdvanceDueDate, "MM/dd/yyyy", "MMM dd, yyyy");

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };
		String[] suggestedTags = "Vertica<break>Sumo Logic".split("<break>", -1);

		String RelatedToVerify = relatedTo + "<break>" + "Vertica<break>Sumo Logic";
		String[][] task1BasicSectionVerification = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", RelatedToVerify } };

		String recordName = "Max";

		String updatedNotesOfTask = "keep in loop  con 4, con 5";
		String[] relatedToVerify = "Max<break>Jhon<break>+4".split("<break>");
		String[] updatedRelatedToVerify = "Max<break>Jhon<break>+6".split("<break>");

		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[] updatedSuggestedTags = "con 4<break>con 5".split("<break>", -1);

		String updatedRelatedToVerifyInNotes = RelatedToVerify + "<break>" + "con 4<break>con 5";
		String[][] task1UpdatedBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedToVerifyInNotes } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
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
					null, suggestedTags)) {
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
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {
						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntractionAndAbleToClickOnEditButtonInTaskDetailPage(
								driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page and clicked on Edit Button"
									+ "------", YesNo.No);

							String url = getURL(driver, 10);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSectionVerification,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(10000);

								if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null, null,
										updatedSuggestedTags, null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

									CommonLib.refresh(driver);

									if (click(driver, taskBP.downArrowButton(20), "downArrowButton",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

										if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20),
												"Edit Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button",
													YesNo.No);

											String url2 = getURL(driver, 10);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdatedBasicSectionVerification, task1AdvancedSection,
															null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);
												CommonLib.ThreadSleep(3000);
												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);

												driver.close();
												CommonLib.ThreadSleep(3000);
												driver.switchTo()
														.window(driver.getWindowHandles().stream().findFirst().get());

											}

										} else {
											log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button",
													YesNo.Yes);
											driver.close();

											driver.switchTo().window(driver.getWindowHandle());
										}

									} else {
										log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
										driver.close();
										driver.switchTo().window(driver.getWindowHandle());
									}

									CommonLib.refresh(driver);
									CommonLib.ThreadSleep(5000);
									ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
											getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true, false,
											updatedRelatedToVerify, null);
									if (updatedresult.isEmpty()) {
										log(LogStatus.PASS, "------" + task1SubjectName
												+ " record has been verified on intraction------", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------",
												YesNo.No);
										sa.assertTrue(false,
												"------" + task1SubjectName
														+ " record is not verified on intraction, Reason: "
														+ updatedresult + "------");
									}

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");
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
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page and Not able to Clicked on edit Button"
									+ "------");
						}

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
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

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc053_CreateATaskWithMeetingNotesAndUpdateTheNotesFromEditNoteButtonOnInteractionSectionOfAcuityTab(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt("1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Send Quote";
		String task1Notes = "unicorn";
		String relatedTo = "Maxtra<break>Martha<break>Jhon<break>con 11<break>Sumo Logic<break>Vertica";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };
		String[] suggestedTags = null;

		String recordName = "Martha";

		String updatedNotesOfTask = "Palm areca";
		String[] relatedToVerify = "Martha<break>Jhon<break>+5".split("<break>");
		String[] updatedRelatedToVerify = "Martha<break>Jhon<break>+7".split("<break>");

		String updatedRelatedTo = relatedTo + "<break>" + "Palm<break>areca";
		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[] updatedSuggestedTags = "All Records Select".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
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
					null, suggestedTags)) {
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
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
											null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult + "------");
										}

										String url2 = getURL(driver, 10);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdateBasicSectionVerification, task1AdvancedSection,
															null);
											if (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button");
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
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
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

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc054_CreateATaskAndVerifyTaskHyperlinkUpdateTheTaskSubjectAndVerifyTheTaskHyperlink(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt("1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "Send Notice";
		String task1UpdatedSubjectName = "Send Notice updated";
		String task1Notes = "";

		String relatedTo = "Acc 3<break>Martha<break>Echo<break>Alexa<break>Green Pothos<break>areca";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";
		String getAdvanceDueDateInTaskSection = "";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String[][] tasksSectionVerificationData = { { "Subject", task1SubjectName },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Due Date Only", getAdvanceDueDateInTaskSection } };

		String[] suggestedTags = null;

		String recordName = "Acc 3";
		String recordType = "Company";

		String[] relatedToVerify = "Martha<break>areca<break>+5".split("<break>");

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Create Task: " + task1UpdatedSubjectName + " and followUp task: "
				+ task1SubjectName + " in Activity Timeline Section---------", YesNo.No);

		/*
		 * ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline", excelLabel.Variable_Name, "AMNNR_018",
		 * excelLabel.Advance_Due_Date);
		 */

		CommonLib.refresh(driver);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();

			if (BP.createTasksWithVerificationOfFollowUpTaskSubjectNameAfterClickThenAgainUpdateTaskNameandVerifyFollowUpTaskSubjectName(
					projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection, suggestedTags,
					tasksSectionVerificationData, task1UpdatedSubjectName)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1UpdatedSubjectName + " and followUp task: "
				+ task1SubjectName + " in Interaction Section---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDateInTaskSection, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);

						/*
						 * String url = getURL(driver, 10);
						 * 
						 * if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
						 * "Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
						 * log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
						 * 
						 * ThreadSleep(10000); ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
						 * .verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
						 * task1AdvancedSection, null); if (NotesPopUpPrefilledNegativeResult.isEmpty())
						 * { log(LogStatus.INFO,
						 * "Notes Popup has been verified and Notes popup is opening in same page with prefilled value"
						 * , YesNo.No);
						 * 
						 * refresh(driver); ThreadSleep(2000); if (click(driver,
						 * BP.editButtonOnInteractionCard(task1SubjectName, 20), "Edit Note Button of: "
						 * + task1SubjectName, action.SCROLLANDBOOLEAN)) { log(LogStatus.INFO,
						 * "clicked on Edit Note button", YesNo.No); if
						 * (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
						 * null, updatedSuggestedTags, null)) { log(LogStatus.PASS,
						 * "Activity timeline record has been Updated", YesNo.No);
						 * 
						 * CommonLib.refresh(driver); ArrayList<String> updatedresult =
						 * BP.verifyRecordOnInteractionCard( getAdvanceDueDate, null, task1SubjectName,
						 * updatedNotesOfTask, true, false, updatedRelatedToVerify, null); if
						 * (updatedresult.isEmpty()) { log(LogStatus.PASS, "------" + task1SubjectName +
						 * " record has been verified on intraction------", YesNo.No);
						 * 
						 * } else { log(LogStatus.ERROR, "------" + task1SubjectName +
						 * " record is not verified on intraction, Reason: " + updatedresult + "------",
						 * YesNo.No); sa.assertTrue(false, "------" + task1SubjectName +
						 * " record is not verified on intraction, Reason: " + updatedresult +
						 * "------"); }
						 * 
						 * String url2 = getURL(driver, 10);
						 * 
						 * if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
						 * "Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
						 * log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
						 * 
						 * ThreadSleep(10000); ArrayList<String>
						 * NotesPopUpPrefilledNegativeResultUpdated = BP
						 * .verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
						 * task1UpdateBasicSectionVerification, task1AdvancedSection, null); if
						 * (NotesPopUpPrefilledNegativeResultUpdated.isEmpty()) { log(LogStatus.INFO,
						 * "Notes Popup has been verified and Notes popup is opening in same page with prefilled value"
						 * , YesNo.No);
						 * 
						 * } else { log(LogStatus.ERROR,
						 * "Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
						 * + NotesPopUpPrefilledNegativeResultUpdated, YesNo.No); sa.assertTrue(false,
						 * "Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
						 * + NotesPopUpPrefilledNegativeResultUpdated); } } else { log(LogStatus.ERROR,
						 * "Not able to click on Edit Note button", YesNo.No); sa.assertTrue(false,
						 * "Not able to click on Edit Note button"); }
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
						 */

					} else {
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------");
					}

					ArrayList<String> result2 = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1UpdatedSubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result2.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1UpdatedSubjectName + " record has been verified on intraction------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "------" + task1UpdatedSubjectName
								+ " record is not verified on intraction, Reason: " + result2 + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1UpdatedSubjectName
								+ " record is not verified on intraction, Reason: " + result2 + "------");
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
	public void AcuityMNNRTc055_CreateATaskWithMeetingNotesAndUpdateTheNotesForFollowUpTasksWhenCreatedMultipleFollowUpTasks(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt("1"));
		String getAdvanceDueDate = AdvanceDueDate;
		String task1SubjectName = "SSend Notice";
		String task1Notes = "echo alexa Green pothos areca";
		String relatedTo = "Acc 3<break>Martha";

		String priority = "Normal";
		String status = "In Progress";
		String task1ButtonName = "Task";
		String[] suggestedTags = "".split("<break>", -1);

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes },
				{ "Related_To", relatedTo } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", status },
				{ "Priority", priority } };

		String recordName = "Acc 3";
		String recordType = "Company";
		String task1UpdateTaskSection1Subject = "SSend Notice Follow up 1";
		String task1UpdateTaskSection1DueDateOnly = AdvanceDueDate;
		String task1UpdateTaskSection2Subject = "SSend Notice Follow up 2";
		String task1UpdateTaskSection2DueDateOnly = AdvanceDueDate;
		String task1UpdateTaskSection3Subject = "SSend Notice Follow up 3";
		String task1UpdateTaskSection3DueDateOnly = AdvanceDueDate;

		String updatedNotesOfTask = "Follow up task As Send Notice Updated for Con 6,Con 7, Con 8, Acc 4";
		String[][] task1UpdateBasicSection = { { "Notes", updatedNotesOfTask } };
		String[][] task1UpdateTaskSection1 = { { "Subject", task1UpdateTaskSection1Subject },
				{ "Due Date Only", task1UpdateTaskSection1DueDateOnly } };
		String[][] task1UpdateTaskSection2 = { { "Subject", task1UpdateTaskSection2Subject },
				{ "Due Date Only", task1UpdateTaskSection2DueDateOnly } };
		String[][] task1UpdateTaskSection3 = { { "Subject", task1UpdateTaskSection3Subject },
				{ "Due Date Only", task1UpdateTaskSection3DueDateOnly } };
		String[][][] task1UpdateTaskSection = { task1UpdateTaskSection1, task1UpdateTaskSection2,
				task1UpdateTaskSection3 };

		String[] relatedToVerify = ("Martha<break>"+crmUser1FirstName + " " + crmUser1LastName+"<break>+1").split("<break>");
		String[] updatedRelatedToVerify = "Martha<break>con 6<break>+5".split("<break>");

		String updatedRelatedTo = relatedTo + "<break>" + "con 6<break>con 7<break>con 8<break>Acc 4";

		String[][] task1UpdateBasicSectionVerification = { { "Subject", task1SubjectName },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };

		String[][] followUptask1BasicSectionVerification = { { "Subject", task1UpdateTaskSection1Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[][] followUptask2BasicSectionVerification = { { "Subject", task1UpdateTaskSection2Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };
		String[][] followUptask3BasicSectionVerification = { { "Subject", task1UpdateTaskSection3Subject },
				{ "Notes", updatedNotesOfTask }, { "Related_To", updatedRelatedTo } };

		String[][] followUptask1AdvanceSectionVerification = { { "Due Date Only", task1UpdateTaskSection1DueDateOnly },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] followUptask2AdvanceSectionVerification = { { "Due Date Only", task1UpdateTaskSection2DueDateOnly },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[][] followUptask3AdvanceSectionVerification = { { "Due Date Only", task1UpdateTaskSection3DueDateOnly },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not Started" },
				{ "Priority", priority } };

		String[] updatedSuggestedTags = "con 6<break>con 7<break>con 8<break>Acc 4".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjectName + " in Activity Timeline Section---------",
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
					null, suggestedTags)) {
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
				"---------Now Going to Verify Task: " + task1SubjectName + " in Interaction Section---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					ArrayList<String> result = BP.verifyRecordOnInteractionCard(getAdvanceDueDate, null,
							task1SubjectName, task1Notes, true, false, relatedToVerify, null);
					if (result.isEmpty()) {

						log(LogStatus.PASS,
								"------" + task1SubjectName + " record has been verified on intraction------",
								YesNo.No);
						if (BP.verifySubjectLinkRedirectionOnIntraction(driver, task1SubjectName)) {
							log(LogStatus.PASS, "------" + task1SubjectName
									+ " record is able to redirect to Task Detail Page" + "------", YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------", YesNo.Yes);
							sa.assertTrue(false, "------" + task1SubjectName
									+ " record is not able to redirect to Task Detail Page" + "------");
						}

						String url = getURL(driver, 10);

						if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
								"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResult = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url, task1BasicSection,
											task1AdvancedSection, null);
							if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
										YesNo.No);

								refresh(driver);
								ThreadSleep(2000);
								if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
										"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
									if (BP.updateActivityTimelineRecordForMultipleFollowUpTasks(projectName,
											task1UpdateBasicSection, null, task1UpdateTaskSection, updatedSuggestedTags,
											null)) {
										log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);

										CommonLib.refresh(driver);
										ArrayList<String> updatedresult1 = BP.verifyRecordOnInteractionCard(
												getAdvanceDueDate, null, task1SubjectName, updatedNotesOfTask, true,
												false, updatedRelatedToVerify, null);
										if (updatedresult1.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1SubjectName
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult1 + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1SubjectName
															+ " record is not verified on intraction, Reason: "
															+ updatedresult1 + "------");
										}

										ArrayList<String> updatedresult2 = BP.verifyRecordOnInteractionCard(
												task1UpdateTaskSection1DueDateOnly, null,
												task1UpdateTaskSection1Subject, updatedNotesOfTask, true, false,
												updatedRelatedToVerify, null);
										if (updatedresult2.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1UpdateTaskSection1Subject
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1UpdateTaskSection1Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult2 + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1UpdateTaskSection1Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult2 + "------");
										}

										ArrayList<String> updatedresult3 = BP.verifyRecordOnInteractionCard(
												task1UpdateTaskSection2DueDateOnly, null,
												task1UpdateTaskSection2Subject, updatedNotesOfTask, true, false,
												updatedRelatedToVerify, null);
										if (updatedresult3.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1UpdateTaskSection2Subject
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1UpdateTaskSection2Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult3 + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1UpdateTaskSection2Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult3 + "------");
										}

										ArrayList<String> updatedresult4 = BP.verifyRecordOnInteractionCard(
												task1UpdateTaskSection3DueDateOnly, null,
												task1UpdateTaskSection3Subject, updatedNotesOfTask, true, false,
												updatedRelatedToVerify, null);
										if (updatedresult4.isEmpty()) {
											log(LogStatus.PASS,
													"------" + task1UpdateTaskSection3Subject
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + task1UpdateTaskSection3Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult4 + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + task1UpdateTaskSection3Subject
															+ " record is not verified on intraction, Reason: "
															+ updatedresult4 + "------");
										}

										String url2 = getURL(driver, 10);

										if (click(driver, BP.editButtonOnInteractionCard(task1SubjectName, 20),
												"Edit Note Button of: " + task1SubjectName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated1 = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															task1UpdateBasicSectionVerification, task1AdvancedSection,
															null);
											if (NotesPopUpPrefilledNegativeResultUpdated1.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated1,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated1);
											}
										} else {
											log(LogStatus.ERROR,
													"Not able to click on Edit Note button " + task1SubjectName,
													YesNo.No);
											sa.assertTrue(false,
													"Not able to click on Edit Note button " + task1SubjectName);
										}

										CommonLib.refresh(driver);
										if (click(driver,
												BP.editButtonOnInteractionCard(task1UpdateTaskSection1Subject, 20),
												"Edit Note Button of: " + task1UpdateTaskSection1Subject,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated2 = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															followUptask1BasicSectionVerification,
															followUptask1AdvanceSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated2.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated2,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated2);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button :"
													+ task1UpdateTaskSection1Subject, YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button :"
													+ task1UpdateTaskSection1Subject);
										}

										CommonLib.refresh(driver);
										if (click(driver,
												BP.editButtonOnInteractionCard(task1UpdateTaskSection2Subject, 20),
												"Edit Note Button of: " + task1UpdateTaskSection2Subject,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated3 = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															followUptask2BasicSectionVerification,
															followUptask2AdvanceSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated3.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated3,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated3);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button "
													+ task1UpdateTaskSection2Subject, YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button "
													+ task1UpdateTaskSection2Subject);
										}

										CommonLib.refresh(driver);
										if (click(driver,
												BP.editButtonOnInteractionCard(task1UpdateTaskSection3Subject, 20),
												"Edit Note Button of: " + task1UpdateTaskSection3Subject,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

											ThreadSleep(10000);
											ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated4 = BP
													.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
															followUptask3BasicSectionVerification,
															followUptask3AdvanceSectionVerification, null);
											if (NotesPopUpPrefilledNegativeResultUpdated4.isEmpty()) {
												log(LogStatus.INFO,
														"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated4,
														YesNo.No);
												sa.assertTrue(false,
														"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
																+ NotesPopUpPrefilledNegativeResultUpdated4);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Edit Note button "
													+ task1UpdateTaskSection3Subject, YesNo.No);
											sa.assertTrue(false, "Not able to click on Edit Note button "
													+ task1UpdateTaskSection3Subject);
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
						log(LogStatus.ERROR, "------" + task1SubjectName
								+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
						sa.assertTrue(false, "------" + task1SubjectName
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

}
