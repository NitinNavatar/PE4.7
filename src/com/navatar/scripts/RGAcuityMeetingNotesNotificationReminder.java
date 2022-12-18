package com.navatar.scripts;

import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.clickUsingJavaScript;
import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.getURL;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.removeNumbersFromString;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.*;
import com.navatar.pageObjects.*;
import com.relevantcodes.extentreports.LogStatus;

public class RGAcuityMeetingNotesNotificationReminder extends BaseLib {

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc001_CreateAnEventAndVerifyTheNotificationOnRecordPageAndHomePage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Seminar1.0";

		String eventAttendees = "Dealroom1.3+Max@gmail.com,Dealroom1.3+Martha@gmail.com,dealroom1.3+con9@gmail.com"
				+ "," + crmUser2EmailID;

		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 1);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 1);

		String user2FullName;
		if (!crmUser2FirstName.equals("") && !crmUser2LastName.equals("")) {
			user2FullName = crmUser2FirstName + " " + crmUser2LastName;
		} else {
			user2FullName = crmUser2LastName;

		}

		String user1FullName;
		if (!crmUser1FirstName.equals("") && !crmUser1LastName.equals("")) {
			user1FullName = crmUser1FirstName + " " + crmUser1LastName;
		} else {
			user1FullName = crmUser1LastName;

		}

		String startTime = "6:00 PM";
		String endTime = "6:30 PM";
		String descriptionBox = "Revenue Grid Event";
		String recordName = "con 9";
		String relatedToNotContains = user2FullName;
		String[][] relatedAssociationNotContains = { { "Related_To", relatedToNotContains } };
		String descriptionBoxOnInteractonCard = "";
		String dateToVerifyOnInteractionCard = startDate + ", " + startTime;
		String[] RelatedToVerifyInInteraction = "Martha<break>Max<break>+3".split("<break>");
		String[] RelatedAssociationVerifyInInteraction = ("con 9" + "<break>" + user2FullName + "<break>"
				+ (user1FullName).trim()).split("<break>", -1);
		/*
		 * String[] updatedRelatedAssociationVerifyInInteraction
		 * ="Con 1<break>con 2<break>con 3<break>con 4<break>con 5<break>con 6<break>con 7<break>con 8<break>con 9<break>con 10<break>con 11<break>con 12<break>con 13<break>con 14<break>con 15<break>con 16<break>con 17<break>con 18<break>con 19<break>con 20<break>con 21<break>con 22<break>con 23<break>con 24<break>con 25<break>con 26<break>con 27<break>con 28<break>con 29<break>con 30<break>con 31<break>con 32<break>con 33<break>con 34<break>con 35<break>con 36<break>con 37<break>con 38<break>con 39<break>con 40<break>con 41<break>con 42<break>con 43<break>con 44<break>con 45<break>con 46<break>con 47<break>con 48"
		 * .split("<break>", -1);
		 */

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			if (home.notificationHeader(15) == null) {
				log(LogStatus.INFO,
						"-----Verified: Notification Pop Up is not showing to Home Page after waiting for 15 seconds-----",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "-----Notification Pop Up is showing to Home Page-----", YesNo.No);
				sa.assertTrue(false, "-----Notification Pop Up is showing to Home Page-----");
			}

			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (lp.clickOnTab(projectName, TabName.HomeTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

					if (home.notificationHeader(10) == null) {
						log(LogStatus.INFO,
								"-----Verified: Notification Pop Up is not showing to Home Page after navigating to tab: "
										+ tabObj2 + " and then to Home Page -----",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"-----Notification Pop Up is showing to Home Page after navigating to tab: " + tabObj2
										+ " and then to Home Page -----",
								YesNo.No);
						sa.assertTrue(false,
								"-----Notification Pop Up is showing to Home Page after navigating to tab: " + tabObj2
										+ " and then to Home Page -----");
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
						List<String> notificationUiNegativeResult = BP
								.verifyNotificationUIWhenNoEventThereOnRecordDetailsPage();
						if (notificationUiNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified UI of Notification when No Record is there", YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Verified the UI of Notification when No Record is there, Reason: "
									+ notificationUiNegativeResult, YesNo.No);
							sa.assertTrue(false, "Not Verified the UI of Notification when No Record is there, Reason: "
									+ notificationUiNegativeResult);
						}

						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(5000);
						ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
								dateToVerifyOnInteractionCard, IconType.Meeting, eventTitle,
								descriptionBoxOnInteractonCard, false, true, RelatedToVerifyInInteraction,
								RelatedAssociationVerifyInInteraction);
						if (updatedresult.isEmpty()) {
							log(LogStatus.PASS, "------" + eventTitle + " record has been verified on intraction------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + eventTitle
									+ " record is not verified on intraction, Reason: " + updatedresult + "------",
									YesNo.No);
							sa.assertTrue(false, "------" + eventTitle
									+ " record is not verified on intraction, Reason: " + updatedresult + "------");
						}

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);

							if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);

								ThreadSleep(10000);
								ArrayList<String> verifyRelatedToNotContainsNegativeResults = BP
										.verifyRelatedToNotContains(relatedAssociationNotContains);
								if (verifyRelatedToNotContainsNegativeResults.isEmpty()) {
									log(LogStatus.INFO,
											"RelatedTo Association Not Contains some Records has been verified",
											YesNo.No);
									driver.close();
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								} else {
									log(LogStatus.ERROR,
											"RelatedTo Association Not Contains some Records has not been verified, Reason: "
													+ verifyRelatedToNotContainsNegativeResults,
											YesNo.No);
									sa.assertTrue(false,
											"RelatedTo Association Not Contains some Records has not been verified, Reason: "
													+ verifyRelatedToNotContainsNegativeResults);
									driver.close();
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
								}
							} else {
								log(LogStatus.ERROR,
										"Not Able Click on Edit button for Record Page of Event: " + eventTitle,
										YesNo.Yes);
								sa.assertTrue(false,
										"Not Able Click on Edit button for Record Page of Event: " + eventTitle);
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

						} else {
							log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
									YesNo.Yes);
							sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);
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
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc002_CreateAnEventAndVerifyTheNotificationOnRecordPageAndHomePageForMultipleEventsBasedOnRecordPages(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitles[] = "Event 1.0<Section>Event 2.0".split("<Section>", -1);
		String eventAttendees[] = ("Dealroom1.3+James@gmail.com" + "," + crmUser2EmailID + "<Section>"
				+ "Dealroom1.3+Litz@gmail.com" + "," + crmUser2EmailID).split("<Section>", -1);
		String startDate[] = (CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -2) + "<Section>"
				+ CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -2)).split("<Section>", -1);
		String endDate[] = (CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1) + "<Section>"
				+ CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1)).split("<Section>", -1);

		String startTime[] = ("2:00 PM" + "<Section>" + "4:00 PM").split("<Section>", -1);
		String endTime[] = ("3:00 PM" + "<Section>" + "5:00 PM").split("<Section>", -1);
		String descriptionBox = null;

		String updatedRelatedTo[] = "Sumo Logic<break>Maxjonic<Section>Nexus<break>Demo Deal<break>Acc 1"
				.split("<Section>", -1);

		String[][] task1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = null;

		String contactRecordName = "Litz";

		String accountRecordName1 = "Maxjonic";
		String accountRecordType1 = "Intermediary";
		String accountRecordName2 = "Nexus";
		String accountRecordType2 = "Institution";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Create Event(s): " + eventTitles + " through Outlook---------",
				YesNo.No);

		Integer loopCount = 0;
		Integer status = 0;
		for (String eventTitle : eventTitles) {

			if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
					eventAttendees[loopCount], startDate[loopCount], endDate[loopCount], startTime[loopCount],
					endTime[loopCount], descriptionBox, false)) {
				log(LogStatus.INFO, "-----Event Created Msg is showing, So Event of Title: " + eventTitle
						+ " has been created-----", YesNo.No);

				status++;

			} else {
				log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been created-----", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been created-----");
			}

			loopCount++;
		}
		Integer loop2Count = 0;
		for (String eventTitle : eventTitles) {
			String[][] task1UpdateBasicSection = { { "Related_To", updatedRelatedTo[loop2Count] } };
			CommonLib.refresh(driver);
			if (CommonLib.click(driver, home.addNoteButtonOfEventInHomePageNotification(eventTitle, 20),
					"Add Note Button of Event: " + eventTitle, action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Add Note Button of Event: " + eventTitle, YesNo.No);
				if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, task1UpdateAdvancedSection,
						null, updatedSuggestedTags, null)) {
					log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle, YesNo.No);

				} else {
					log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle, YesNo.No);
					sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Add Noted Button of Event: " + eventTitle, YesNo.No);
				sa.assertTrue(false, "Not Able to Click on Add Noted Button of Event: " + eventTitle);
			}
			loop2Count++;
		}

		if (status.equals(loopCount)) {

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitles);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitles + " on Home Page",
							YesNo.No);

					List<String> notificationOptionsListInText = home.getNotificationOptions().stream()
							.map(x -> CommonLib.getText(driver, x, "Event Name", action.BOOLEAN))
							.collect(Collectors.toList());

					if (notificationOptionsListInText.indexOf(eventTitles[0]) > notificationOptionsListInText
							.indexOf(eventTitles[1])) {
						log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitles[0]
								+ " on Home Page comes under " + eventTitles[1], YesNo.No);
					} else {
						log(LogStatus.ERROR, "Notification for Event(s): " + eventTitles[0]
								+ " on Home Page not comes under " + eventTitles[1], YesNo.No);
						sa.assertTrue(false, "Notification for Event(s): " + eventTitles[0]
								+ " on Home Page not comes under " + eventTitles[1]);

					}

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitles + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitles + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event(s): " + eventTitles[0] + " in " + accountRecordName1
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType1,
						accountRecordName1, 30)) {
					log(LogStatus.INFO,
							accountRecordName1 + " record of record type " + accountRecordType1 + " has been open",
							YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitles[0]);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitles[0], YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitles[0] + ", Reason: "
									+ notificationNegativeResult, YesNo.No);
							sa.assertTrue(false, "Not Verified the Event(s) " + eventTitles[0] + ", Reason: "
									+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR,
							"Not able to open " + accountRecordName1 + " record of record type " + accountRecordType1,
							YesNo.No);
					sa.assertTrue(false,
							"Not able to open " + accountRecordName1 + " record of record type " + accountRecordType1);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event(s): " + eventTitles[1] + " in " + accountRecordName2
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType2,
						accountRecordName2, 30)) {
					log(LogStatus.INFO,
							accountRecordName2 + " record of record type " + accountRecordType2 + " has been open",
							YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitles[1]);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitles[1], YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitles[1] + ", Reason: "
									+ notificationNegativeResult, YesNo.No);
							sa.assertTrue(false, "Not Verified the Event(s) " + eventTitles[1] + ", Reason: "
									+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR,
							"Not able to open " + accountRecordName2 + " record of record type " + accountRecordType2,
							YesNo.No);
					sa.assertTrue(false,
							"Not able to open " + accountRecordName2 + " record of record type " + accountRecordType2);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event(s): " + eventTitles[1] + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitles[1]);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"Verified Notification contains for Event(s): " + eventTitles[1]
											+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page"
											+ contactRecordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitles[1] + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitles[1] + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

		}

		else {

			sa.assertTrue(false, "Some of the Given Events not gets created: " + eventTitles
					+ " , So not able to further continue to check on Notification Panes");
			log(LogStatus.SKIP, "Some of the Given Events not gets created: " + eventTitles
					+ " , So not able to further continue to check on Notification Panes", YesNo.Yes);

		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc003_CreateAnEventAndVerifyItOnNotificationOnRecordPageAndHomePage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Seminar";
		String eventAttendees = "Dealroom1.3+Max@gmail.com,Dealroom1.3+Martha@gmail.com,dealroom1.3+con9@gmail.com"
				+ "," + crmUser2EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);

		String startTime = "6:00 PM";
		String endTime = "6:30 PM";
		String descriptionBox = "Revenue Grid Event";
		String recordName = "con 9";
		String user1FullName;
		if (!crmUser1FirstName.equals("") && !crmUser1LastName.equals("")) {
			user1FullName = crmUser1FirstName + " " + crmUser1LastName;
		} else {
			user1FullName = crmUser1LastName;

		}

		String user2FullName;
		if (!crmUser2FirstName.equals("") && !crmUser2LastName.equals("")) {
			user2FullName = crmUser2FirstName + " " + crmUser2LastName;
		} else {
			user2FullName = crmUser2LastName;

		}

		String dateToVerifyOnInteractionCard = startDate;
		String[] RelatedToVerifyInInteraction = "Max<break>Martha<break>+3".split("<break>", -1);
		String RelatedToVerifyInNotes = ("Max<break>Martha<break>con 9<break>" + user1FullName + "<break>"
				+ user2FullName).replaceAll(" +", " ");

		String[] RelatedAssociationVerifyInInteraction = null;

		String eventTitleUpdated = "Seminar Done";
		String updatedRelatedTo = "Houlihan Lokey<break>Sumo Logic";

		String[][] task1UpdateBasicSection = { { "Subject", eventTitleUpdated }, { "Related_To", updatedRelatedTo } };
		String[][] task1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = null;
		String contactRecordName = recordName;
		String accountRecordName = "Houlihan Lokey";
		String accountRecordType = "Institution";

		String[][] event1BasicSectionVerificationInNotesPopup = { { "Subject", eventTitle }, { "Notes", "" },
				{ "Related_To", RelatedToVerifyInNotes } };
		String[][] event1AdvancedSectionVerificationInNotesPopup = { { "Location", "" },
				{ "Assigned To ID", user1FullName }, { "Start Date Time<break>Date", startDate },
				{ "End Date Time<break>Date", endDate } };

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle, YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + ", Reason: "
									+ notificationNegativeResult, YesNo.No);
							sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + ", Reason: "
									+ notificationNegativeResult);
						}

						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(5000);
						ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(
								dateToVerifyOnInteractionCard, null, eventTitle, "", false, true,
								RelatedToVerifyInInteraction, RelatedAssociationVerifyInInteraction);
						if (updatedresult.isEmpty()) {
							log(LogStatus.PASS, "------" + eventTitle + " record has been verified on intraction------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + eventTitle
									+ " record is not verified on intraction, Reason: " + updatedresult + "------",
									YesNo.No);
							sa.assertTrue(false, "------" + eventTitle
									+ " record is not verified on intraction, Reason: " + updatedresult + "------");
						}

						if (lp.clickOnTab(projectName, TabName.HomeTab)) {
							log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

							CommonLib.ThreadSleep(5000);
							List<String> notificationHomeNegativeResult2 = home.verifyNotificationOptions(eventTitle);
							if (notificationHomeNegativeResult2.isEmpty()) {
								log(LogStatus.INFO,
										"Verified Notification for Event(s): " + eventTitle + " on Home Page",
										YesNo.No);

								if (CommonLib.click(driver,
										home.addNoteButtonOfEventInHomePageNotification(eventTitle, 20),
										"Add Note Button of Event: " + eventTitle, action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Add Note Button of Event: " + eventTitle, YesNo.No);

									String url2 = getURL(driver, 10);
									ThreadSleep(10000);
									ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
													event1BasicSectionVerificationInNotesPopup,
													event1AdvancedSectionVerificationInNotesPopup, null);
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

									if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
											task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
										log(LogStatus.PASS,
												"Activity timeline record has been Updated for: " + eventTitle,
												YesNo.No);

										if (lp.clickOnTab(projectName, tabObj2)) {

											log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

											if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
													recordName, 30)) {
												log(LogStatus.INFO, contactRecordName + " record has been open",
														YesNo.No);
												ThreadSleep(4000);
												if (BP.clicktabOnPage("Acuity")) {
													log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
													if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
															eventTitleUpdated)) {
														log(LogStatus.INFO,
																"Task Detail Page has been open for Record: "
																		+ eventTitleUpdated,
																YesNo.No);
														driver.close();
														CommonLib.ThreadSleep(3000);
														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());
													} else {
														log(LogStatus.ERROR,
																"Record Detail Page has not open for Record: "
																		+ eventTitleUpdated,
																YesNo.Yes);
														sa.assertTrue(false,
																"Record Detail Page has not open for Record: "
																		+ eventTitleUpdated);

													}

												} else {
													log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
													sa.assertTrue(false, "Not able to click on Acuity Tab");
												}

											} else {
												log(LogStatus.ERROR,
														"Not able to open " + contactRecordName + " record", YesNo.No);
												sa.assertTrue(false,
														"Not able to open " + contactRecordName + " record");
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
											sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
										}

										if (lp.clickOnTab(projectName, tabObj1)) {

											log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

											if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
													TabName.InstituitonsTab, accountRecordType, accountRecordName,
													30)) {
												log(LogStatus.INFO, accountRecordName + " record of record type "
														+ accountRecordType + " has been open", YesNo.No);
												ThreadSleep(4000);
												if (BP.clicktabOnPage("Acuity")) {
													log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

													if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
															eventTitleUpdated)) {
														log(LogStatus.INFO,
																"Task Detail Page has been open for Record: "
																		+ eventTitleUpdated,
																YesNo.No);
														driver.close();
														CommonLib.ThreadSleep(3000);
														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());
													} else {
														log(LogStatus.ERROR,
																"Record Detail Page has not open for Record: "
																		+ eventTitleUpdated,
																YesNo.Yes);
														sa.assertTrue(false,
																"Record Detail Page has not open for Record: "
																		+ eventTitleUpdated);

													}
												} else {
													log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
													sa.assertTrue(false, "Not able to click on Acuity Tab");
												}

											} else {
												log(LogStatus.ERROR,
														"Not able to open " + accountRecordName
																+ " record of record type " + accountRecordType,
														YesNo.No);
												sa.assertTrue(false, "Not able to open " + accountRecordName
														+ " record of record type " + accountRecordType);
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
											sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
										}

									} else {
										log(LogStatus.FAIL,
												"Activity timeline record has not Updated for: " + eventTitle,
												YesNo.No);
										sa.assertTrue(false,
												"Activity timeline record has not Updated for: " + eventTitle);
									}

								} else {
									log(LogStatus.ERROR,
											"Not Able to Click on Add Noted Button of Event: " + eventTitle, YesNo.No);
									sa.assertTrue(false,
											"Not Able to Click on Add Noted Button of Event: " + eventTitle);
								}

							} else {
								log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
										+ "+ on Home Page, Reason: " + notificationHomeNegativeResult2, YesNo.No);
								sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
										+ "+ on Home Page, Reason: " + notificationHomeNegativeResult2);
							}
						} else {
							sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
							log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
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
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}
		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc004_LoginToTheOrgAsUser2AndVerifyTheNotificationPopUp(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitleUpdated = "Seminar Done";
		String contactRecordName = "con 9";
		String contactRecordNameUser1User2AndAdminNotContain = "Con 1";

		String accountRecordName = "Sumo Logic";
		String accountRecordType = "Company";
		String accountRecordNameUser1User2AndAdminNotContain = "Acc 3";
		String accountRecordTypeUser1User2AndAdminNotContain = "Company";

		lp.CRMLogin(crmUser2EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Verify Event: " + eventTitleUpdated
						+ " on Home Page as well as on Detail Page in case of User 1: " + crmUser2EmailID + "---------",
				YesNo.No);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

			List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitleUpdated);
			if (notificationHomeNegativeResult.isEmpty()) {
				log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleUpdated + " on Home Page",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitleUpdated + "+ on Home Page, Reason: "
						+ notificationHomeNegativeResult, YesNo.No);
				sa.assertTrue(false, "Not Verified the Event(s) " + eventTitleUpdated + "+ on Home Page, Reason: "
						+ notificationHomeNegativeResult);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in " + contactRecordName
				+ " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsOnRecordDetailsPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleUpdated, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitleUpdated + ", Reason: "
								+ notificationNegativeResult, YesNo.No);
						sa.assertTrue(false, "Not Verified the Event(s) " + eventTitleUpdated + ", Reason: "
								+ notificationNegativeResult);
					}

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has been open for Record: " + eventTitleUpdated,
								YesNo.No);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					} else {
						log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitleUpdated,
								YesNo.Yes);
						sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitleUpdated);

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in " + accountRecordName
				+ " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType,
					accountRecordName, 30)) {
				log(LogStatus.INFO,
						accountRecordName + " record of record type " + accountRecordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsOnRecordDetailsPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleUpdated, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitleUpdated + ", Reason: "
								+ notificationNegativeResult, YesNo.No);
						sa.assertTrue(false, "Not Verified the Event(s) " + eventTitleUpdated + ", Reason: "
								+ notificationNegativeResult);
					}

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has been open for Record: " + eventTitleUpdated,
								YesNo.No);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					} else {
						log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitleUpdated,
								YesNo.Yes);
						sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitleUpdated);

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType,
						YesNo.No);
				sa.assertTrue(false,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in "
				+ contactRecordNameUser1User2AndAdminNotContain + " Record not contains in case of User 2---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					contactRecordNameUser1User2AndAdminNotContain, 30)) {
				log(LogStatus.INFO, contactRecordNameUser1User2AndAdminNotContain + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitleUpdated
								+ " in case of User 2 in Detail Page" + contactRecordNameUser1User2AndAdminNotContain,
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain in case of User 2 in Detail Page: "
										+ contactRecordNameUser1User2AndAdminNotContain + " , Reason: "
										+ notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain in case of User 2 in Detail Page: "
										+ contactRecordNameUser1User2AndAdminNotContain + " , Reason: "
										+ notificationNegativeResult);
					}

					if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has not been open for Record: " + eventTitleUpdated
								+ " in case of User 2 in Detail Page: " + contactRecordNameUser1User2AndAdminNotContain,
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of User 2 in Detail Page: " + contactRecordNameUser1User2AndAdminNotContain,
								YesNo.Yes);
						sa.assertTrue(false,
								"Event Record Detail Page  has opened for Record: " + eventTitleUpdated
										+ " in case of User 2 in Detail Page: "
										+ contactRecordNameUser1User2AndAdminNotContain);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordNameUser1User2AndAdminNotContain + " record",
						YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordNameUser1User2AndAdminNotContain + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		log(LogStatus.INFO,
				"---------Now Going to Verify Event: " + eventTitleUpdated + " in "
						+ accountRecordNameUser1User2AndAdminNotContain
						+ " Record not contains in Notification Pane in case of User 2---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					accountRecordTypeUser1User2AndAdminNotContain, accountRecordNameUser1User2AndAdminNotContain, 30)) {
				log(LogStatus.INFO, accountRecordNameUser1User2AndAdminNotContain + " record of record type "
						+ accountRecordTypeUser1User2AndAdminNotContain + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitleUpdated
								+ " in case of User 2 in Detail Page" + accountRecordNameUser1User2AndAdminNotContain,
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain i case of User 2 in Detail Page: "
										+ accountRecordNameUser1User2AndAdminNotContain + " , Reason: "
										+ notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain i case of User 2 in Detail Page: "
										+ accountRecordNameUser1User2AndAdminNotContain + " , Reason: "
										+ notificationNegativeResult);
					}

					if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has not been open for Record: " + eventTitleUpdated
								+ " in case of User 2 in Detail Page: " + accountRecordNameUser1User2AndAdminNotContain,
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of User 2 in Detail Page: " + accountRecordNameUser1User2AndAdminNotContain,
								YesNo.Yes);
						sa.assertTrue(false,
								"Event Record Detail Page  has opened for Record: " + eventTitleUpdated
										+ " in case of User 2 in Detail Page: "
										+ accountRecordNameUser1User2AndAdminNotContain);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + accountRecordNameUser1User2AndAdminNotContain
						+ " record of record type " + accountRecordTypeUser1User2AndAdminNotContain, YesNo.No);
				sa.assertTrue(false, "Not able to open " + accountRecordNameUser1User2AndAdminNotContain
						+ " record of record type " + accountRecordTypeUser1User2AndAdminNotContain);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated
				+ " on Home Page as well as on Detail Page in case of Admin: " + superAdminUserName + "---------",
				YesNo.No);

		lp.CRMLogin(superAdminUserName, adminPassword);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitleUpdated);
			if (notificationHomeNegativeResult.isEmpty()) {
				log(LogStatus.INFO,
						"Verified Notification for Event(s): " + eventTitleUpdated + " on Home Page not contains",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"The Event(s) " + eventTitleUpdated
								+ "+ on Home Page should not contain in case of Admin, Reason: "
								+ notificationHomeNegativeResult,
						YesNo.No);
				sa.assertTrue(false,
						"The Event(s) " + eventTitleUpdated
								+ "+ on Home Page should not contain in case of Admin, Reason: "
								+ notificationHomeNegativeResult);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in " + contactRecordName
				+ " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitleUpdated
								+ " in case of Admin in Detail Page" + contactRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain i case of Admin in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain i case of Admin in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult);
					}

					if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has not been open for Record: " + eventTitleUpdated
								+ " in case of Admin in Detail Page: " + contactRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of Admin in Detail Page: " + contactRecordName, YesNo.Yes);
						sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of Admin in Detail Page: " + contactRecordName);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in " + accountRecordName
				+ " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType,
					accountRecordName, 30)) {
				log(LogStatus.INFO,
						accountRecordName + " record of record type " + accountRecordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitleUpdated
								+ " in case of Admin in Detail Page" + accountRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain in case of Admin in Detail Page: " + accountRecordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain in case of Admin in Detail Page: " + accountRecordName
										+ " , Reason: " + notificationNegativeResult);
					}

					if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has not been open for Record: " + eventTitleUpdated
								+ " in case of Admin in Detail Page: " + accountRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of Admin in Detail Page: " + accountRecordName, YesNo.Yes);
						sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of Admin in Detail Page: " + accountRecordName);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType,
						YesNo.No);
				sa.assertTrue(false,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}

		ThreadSleep(5000);
		lp.CRMlogout();

		lp.CRMLogin(crmUser3EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated
				+ " on Home Page as well as on Detail Page in case of User 3: " + crmUser3EmailID + " ---------",
				YesNo.No);

		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitleUpdated);
			if (notificationHomeNegativeResult.isEmpty()) {
				log(LogStatus.INFO,
						"Verified Notification for Event(s): " + eventTitleUpdated + " on Home Page not contains",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"The Event(s) " + eventTitleUpdated
								+ "+ on Home Page should not contain in case of User 3, Reason: "
								+ notificationHomeNegativeResult,
						YesNo.No);
				sa.assertTrue(false,
						"The Event(s) " + eventTitleUpdated
								+ "+ on Home Page should not contain in case of User 3, Reason: "
								+ notificationHomeNegativeResult);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in " + contactRecordName
				+ " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitleUpdated
								+ " in case of User 3 in Detail Page" + contactRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain i case of User 3 in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain i case of User 3 in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult);
					}

					if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has not been open for Record: " + eventTitleUpdated
								+ " in case of User 3 in Detail Page: " + contactRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of User 3 in Detail Page: " + contactRecordName, YesNo.Yes);
						sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of User 3 in Detail Page: " + contactRecordName);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in " + accountRecordName
				+ " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType,
					accountRecordName, 30)) {
				log(LogStatus.INFO,
						accountRecordName + " record of record type " + accountRecordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitleUpdated
								+ " in case of User 3 in Detail Page" + accountRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain in case of User 3 in Detail Page: " + accountRecordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitleUpdated
										+ " should not contain in case of User 3 in Detail Page: " + accountRecordName
										+ " , Reason: " + notificationNegativeResult);
					}

					if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has not been open for Record: " + eventTitleUpdated
								+ " in case of User 3 in Detail Page: " + accountRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of User 3 in Detail Page: " + accountRecordName, YesNo.Yes);
						sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitleUpdated
								+ " in case of User 3 in Detail Page: " + accountRecordName);
						driver.close();
						CommonLib.ThreadSleep(3000);
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType,
						YesNo.No);
				sa.assertTrue(false,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType);
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
	public void rgAcuityMNNRTc005_UpdateAllEventFieldsOneByOneAndVerifyCustomNotification(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitleUpdated = "Seminar Done";
		String contactRecordName = "con 9";

		String accountRecordName = "Sumo Logic";
		String accountRecordType = "Company";

		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);

		String user1FullName;
		if (!crmUser1FirstName.equals("") && !crmUser1LastName.equals("")) {
			user1FullName = crmUser1FirstName + " " + crmUser1LastName;
		} else {
			user1FullName = crmUser1LastName;

		}

		String user2FullName;
		if (!crmUser2FirstName.equals("") && !crmUser2LastName.equals("")) {
			user2FullName = crmUser2FirstName + " " + crmUser2LastName;
		} else {
			user2FullName = crmUser2LastName;

		}
		String RelatedToVerifyInNotes = ("Max<break>Martha<break>con 9<break>" + user1FullName + "<break>"
				+ user2FullName).replaceAll(" +", " ");
		String[] labelAndValueSeprateByBreak = { "Assigned To" + "<break>" + user1FullName,
				"Subject" + "<break>" + eventTitleUpdated, "Name" + "<break>" + "" };

		String[][] event1BasicSectionVerificationInNotesPopup = { { "Subject", eventTitleUpdated }, { "Notes", "" },
				{ "Related_To", RelatedToVerifyInNotes } };
		String[][] event1AdvancedSectionVerificationInNotesPopup = { { "Location", "" },
				{ "Assigned To ID", user1FullName }, { "Start Date Time<break>Date", startDate },
				{ "End Date Time<break>Date", endDate } };

		String[][] event1UpdateBasicSection = { { "Related_To", "Vertica" } };
		String[][] event1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = null;
		String getAdvanceDueDate = startDate;
		String[] updatedRelatedToVerify = null;

		String updateStartDateOnHomepageNotePopup = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);
		String updateEndDateOnHomepageNotePopup = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 0);
		String[][] event1UpdateBasicSectionOnHomepageNotePopup = {
				{ "Start Date Time<break>Date", updateStartDateOnHomepageNotePopup },
				{ "End Date Time<break>Date", updateEndDateOnHomepageNotePopup } };
		String[][] event1UpdateAdvancedSectionOnHomepageNotePopup = null;
		String[] eventupdatedSuggestedTagsOnHomepageNotePopup = null;
		String getAdvanceDueDate2 = updateStartDateOnHomepageNotePopup;
		String[][] event1UpdateBasicSectionOnHomepageNotePopup2 = { { "Location", "NY" } };
		String[][] event1UpdateAdvancedSectionOnHomepageNotePopup2 = null;
		String[] eventupdatedSuggestedTagsOnHomepageNotePopup2 = null;

		String recordName = "Vertica";
		String recordType = "Company";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated
				+ " on Home Page as well as on Detail Page ---------", YesNo.No);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleUpdated)) {
						log(LogStatus.INFO, "Task Detail Page has been open for Record: " + eventTitleUpdated,
								YesNo.No);

						CommonLib.ThreadSleep(8000);
						List<String> eventDetailPageNegativeResult = BP
								.fieldValueVerification(labelAndValueSeprateByBreak);

						if (eventDetailPageNegativeResult.isEmpty()) {
							log(LogStatus.PASS,
									"------" + eventTitleUpdated
											+ " labels and their values in Detail page has been verified------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + eventTitleUpdated
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ eventDetailPageNegativeResult + "------",
									YesNo.No);
							sa.assertTrue(false,
									"------" + eventTitleUpdated
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ eventDetailPageNegativeResult + "------");

						}

						if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);

							ThreadSleep(10000);
							String url2 = getURL(driver, 10);
							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
											event1BasicSectionVerificationInNotesPopup,
											event1AdvancedSectionVerificationInNotesPopup, null);
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

							if (BP.updateActivityTimelineRecord(projectName, event1UpdateBasicSection,
									event1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
								log(LogStatus.PASS,
										"Activity timeline record has been Updated for: " + eventTitleUpdated,
										YesNo.No);

								CommonLib.refresh(driver);
								CommonLib.ThreadSleep(5000);

								ArrayList<String> updatedresult = BP.verifyRecordOnInteractionCard(getAdvanceDueDate,
										null, eventTitleUpdated, "", false, true, updatedRelatedToVerify, null);
								if (updatedresult.isEmpty()) {
									log(LogStatus.PASS, "------" + eventTitleUpdated
											+ " record has been verified on intraction------", YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"------" + eventTitleUpdated
													+ " record is not verified on intraction, Reason: " + updatedresult
													+ "------",
											YesNo.No);
									sa.assertTrue(false,
											"------" + eventTitleUpdated
													+ " record is not verified on intraction, Reason: " + updatedresult
													+ "------");
								}

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

									if (CommonLib.click(driver,
											home.eventLinkInHomePageNotification(eventTitleUpdated, 20),
											"Link of Event: " + eventTitleUpdated, action.BOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Link of Event: " + eventTitleUpdated, YesNo.No);

										if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Edit Button of Event: " + eventTitleUpdated,
													YesNo.No);

											if (BP.updateActivityTimelineRecord(projectName,
													event1UpdateBasicSectionOnHomepageNotePopup,
													event1UpdateAdvancedSectionOnHomepageNotePopup, null,
													eventupdatedSuggestedTagsOnHomepageNotePopup, null)) {
												log(LogStatus.PASS, "Activity timeline record has been Updated for: "
														+ eventTitleUpdated, YesNo.No);
												if (lp.clickOnTab(projectName, tabObj1)) {

													log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

													if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
															TabName.InstituitonsTab, accountRecordType,
															accountRecordName, 30)) {
														log(LogStatus.INFO,
																accountRecordName + " record of record type "
																		+ accountRecordType + " has been open",
																YesNo.No);
														ThreadSleep(4000);
														if (BP.clicktabOnPage("Acuity")) {
															log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

															ArrayList<String> updatedresult2 = BP
																	.verifyRecordOnInteractionCard(getAdvanceDueDate2,
																			null, eventTitleUpdated, "", false, true,
																			updatedRelatedToVerify, null);
															if (updatedresult2.isEmpty()) {
																log(LogStatus.PASS, "------" + eventTitleUpdated
																		+ " record has been verified on intraction------",
																		YesNo.No);

															} else {
																log(LogStatus.ERROR, "------" + eventTitleUpdated
																		+ " record is not verified on intraction, Reason: "
																		+ updatedresult2 + "------", YesNo.No);
																sa.assertTrue(false, "------" + eventTitleUpdated
																		+ " record is not verified on intraction, Reason: "
																		+ updatedresult2 + "------");
															}

															if (lp.clickOnTab(projectName, TabName.HomeTab)) {
																log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab,
																		YesNo.No);
																List<String> notificationHomeNegativeResult = home
																		.verifyNotificationOptions(eventTitleUpdated);
																if (notificationHomeNegativeResult.isEmpty()) {
																	log(LogStatus.INFO,
																			"Verified Notification for Event(s): "
																					+ eventTitleUpdated
																					+ " on Home Page",
																			YesNo.No);

																} else {
																	log(LogStatus.ERROR,
																			"Not Verified the Event(s) "
																					+ eventTitleUpdated
																					+ "+ on Home Page, Reason: "
																					+ notificationHomeNegativeResult,
																			YesNo.No);
																	sa.assertTrue(false,
																			"Not Verified the Event(s) "
																					+ eventTitleUpdated
																					+ "+ on Home Page, Reason: "
																					+ notificationHomeNegativeResult);
																}
															} else {
																sa.assertTrue(false, "Not Able to Click on Tab : "
																		+ TabName.HomeTab);
																log(LogStatus.SKIP,
																		"Not Able to Click on Tab : " + TabName.HomeTab,
																		YesNo.Yes);
															}

														} else {
															log(LogStatus.ERROR, "Not able to click on Acuity Tab",
																	YesNo.No);
															sa.assertTrue(false, "Not able to click on Acuity Tab");
														}

													} else {
														log(LogStatus.ERROR,
																"Not able to open " + recordName
																		+ " record of record type " + recordType,
																YesNo.No);
														sa.assertTrue(false, "Not able to open " + recordName
																+ " record of record type " + recordType);
													}
												} else {
													log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1,
															YesNo.No);
													sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
												}

											} else {
												log(LogStatus.FAIL, "Activity timeline record has not Updated for: "
														+ eventTitleUpdated, YesNo.No);
												sa.assertTrue(false, "Activity timeline record has not Updated for: "
														+ eventTitleUpdated);
											}

										} else {
											log(LogStatus.ERROR,
													"Not Able Click on Edit button for Record Page of Event: "
															+ eventTitleUpdated,
													YesNo.Yes);
											sa.assertTrue(false,
													"Not Able Click on Edit button for Record Page of Event: "
															+ eventTitleUpdated);
											driver.close();
											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										}

									} else {
										log(LogStatus.ERROR, "Not Able to Click on Link of Event: " + eventTitleUpdated,
												YesNo.No);
										sa.assertTrue(false,
												"Not Able to Click on Link of Event: " + eventTitleUpdated);
									}

								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

									if (CommonLib.click(driver,
											home.eventLinkInHomePageNotification(eventTitleUpdated, 20),
											"Link of Event: " + eventTitleUpdated, action.BOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Link of Event: " + eventTitleUpdated, YesNo.No);

										if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Edit Button of Event: " + eventTitleUpdated,
													YesNo.No);

											if (BP.updateActivityTimelineRecord(projectName,
													event1UpdateBasicSectionOnHomepageNotePopup2,
													event1UpdateAdvancedSectionOnHomepageNotePopup2, null,
													eventupdatedSuggestedTagsOnHomepageNotePopup2, null)) {
												log(LogStatus.PASS, "Activity timeline record has been Updated for: "
														+ eventTitleUpdated, YesNo.No);
												if (lp.clickOnTab(projectName, tabObj1)) {

													log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

													if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
															TabName.InstituitonsTab, accountRecordType,
															accountRecordName, 30)) {
														log(LogStatus.INFO,
																accountRecordName + " record of record type "
																		+ accountRecordType + " has been open",
																YesNo.No);
														ThreadSleep(4000);
														if (BP.clicktabOnPage("Acuity")) {
															log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

															ArrayList<String> updatedresult2 = BP
																	.verifyRecordOnInteractionCard(getAdvanceDueDate2,
																			null, eventTitleUpdated, "", false, true,
																			updatedRelatedToVerify, null);
															if (updatedresult2.isEmpty()) {
																log(LogStatus.PASS, "------" + eventTitleUpdated
																		+ " record has been verified on intraction------",
																		YesNo.No);

															} else {
																log(LogStatus.ERROR, "------" + eventTitleUpdated
																		+ " record is not verified on intraction, Reason: "
																		+ updatedresult2 + "------", YesNo.No);
																sa.assertTrue(false, "------" + eventTitleUpdated
																		+ " record is not verified on intraction, Reason: "
																		+ updatedresult2 + "------");
															}

															if (lp.clickOnTab(projectName, TabName.HomeTab)) {
																log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab,
																		YesNo.No);
																List<String> notificationHomeNegativeResult = home
																		.verifyNotificationOptions(eventTitleUpdated);
																if (notificationHomeNegativeResult.isEmpty()) {
																	log(LogStatus.INFO,
																			"Verified Notification for Event(s): "
																					+ eventTitleUpdated
																					+ " on Home Page",
																			YesNo.No);

																} else {
																	log(LogStatus.ERROR,
																			"Not Verified the Event(s) "
																					+ eventTitleUpdated
																					+ "+ on Home Page, Reason: "
																					+ notificationHomeNegativeResult,
																			YesNo.No);
																	sa.assertTrue(false,
																			"Not Verified the Event(s) "
																					+ eventTitleUpdated
																					+ "+ on Home Page, Reason: "
																					+ notificationHomeNegativeResult);
																}
															} else {
																sa.assertTrue(false, "Not Able to Click on Tab : "
																		+ TabName.HomeTab);
																log(LogStatus.SKIP,
																		"Not Able to Click on Tab : " + TabName.HomeTab,
																		YesNo.Yes);
															}

														} else {
															log(LogStatus.ERROR, "Not able to click on Acuity Tab",
																	YesNo.No);
															sa.assertTrue(false, "Not able to click on Acuity Tab");
														}

													} else {
														log(LogStatus.ERROR,
																"Not able to open " + recordName
																		+ " record of record type " + recordType,
																YesNo.No);
														sa.assertTrue(false, "Not able to open " + recordName
																+ " record of record type " + recordType);
													}
												} else {
													log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1,
															YesNo.No);
													sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
												}

											} else {
												log(LogStatus.FAIL, "Activity timeline record has not Updated for: "
														+ eventTitleUpdated, YesNo.No);
												sa.assertTrue(false, "Activity timeline record has not Updated for: "
														+ eventTitleUpdated);
											}

										} else {
											log(LogStatus.ERROR,
													"Not Able Click on Edit button for Record Page of Event: "
															+ eventTitleUpdated,
													YesNo.Yes);
											sa.assertTrue(false,
													"Not Able Click on Edit button for Record Page of Event: "
															+ eventTitleUpdated);
											driver.close();
											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										}

									} else {
										log(LogStatus.ERROR, "Not Able to Click on Link of Event: " + eventTitleUpdated,
												YesNo.No);
										sa.assertTrue(false,
												"Not Able to Click on Link of Event: " + eventTitleUpdated);
									}

								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

							} else {
								log(LogStatus.FAIL,
										"Activity timeline record has not Updated for: " + eventTitleUpdated, YesNo.No);
								sa.assertTrue(false,
										"Activity timeline record has not Updated for: " + eventTitleUpdated);
							}
						} else {
							log(LogStatus.ERROR,
									"Not Able Click on Edit button for Record Page of Event: " + eventTitleUpdated,
									YesNo.Yes);
							sa.assertTrue(false,
									"Not Able Click on Edit button for Record Page of Event: " + eventTitleUpdated);
							driver.close();
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						}
						driver.close();
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

					} else {
						log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitleUpdated,
								YesNo.Yes);
						sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitleUpdated);

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc006_AddTheNotesFromInteractionnSectionAddNotesButtonAndVerifyTheNotification(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitleUpdated = "Seminar Done";
		String contactRecordName = "Max";

		String accountRecordName = "Houlihan Lokey";
		String accountRecordType = "Institution";

		String[][] event1BasicSectionVerificationInNotesPopup = { { "Location", "NY" } };
		String[][] event1AdvancedSectionVerificationInNotesPopup = null;
		String[][] event1UpdateBasicSection = {
				{ "Notes", "Lomez and Vertica Houlihan Lokey can be considered as good opportunity." } };
		String[][] event1UpdateAdvancedSection = null;
		String[] event1UpdatedSuggestedTags = "Vertica<break>Houlihan Lokey<break>Lomez".split("<break>", -1);

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated
				+ " on Home Page as well as on Detail Page ---------", YesNo.No);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResultOnContactDetailPage = BP
							.verifyNotificationOptionsOnRecordDetailsPage(eventTitleUpdated);
					if (notificationNegativeResultOnContactDetailPage.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleUpdated, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitleUpdated + ", Reason: "
								+ notificationNegativeResultOnContactDetailPage, YesNo.No);
						sa.assertTrue(false, "Not Verified the Event(s) " + eventTitleUpdated + ", Reason: "
								+ notificationNegativeResultOnContactDetailPage);
					}

					if (CommonLib.click(driver,
							home.addNoteButtonInNotificationOfRecordDetailPage(eventTitleUpdated, 20),
							"Add Note Button of Event: " + eventTitleUpdated, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Add Note Button of Event: " + eventTitleUpdated, YesNo.No);

						String url2 = getURL(driver, 10);
						ThreadSleep(10000);
						ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
								.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
										event1BasicSectionVerificationInNotesPopup,
										event1AdvancedSectionVerificationInNotesPopup, null);
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

						if (BP.updateActivityTimelineRecord(projectName, event1UpdateBasicSection,
								event1UpdateAdvancedSection, null, event1UpdatedSuggestedTags, null)) {
							log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitleUpdated,
									YesNo.No);

							CommonLib.refresh(driver);

							List<String> notificationNegativeResultNotContainsOnContactRecordDetailPage = BP
									.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleUpdated);
							if (notificationNegativeResultNotContainsOnContactRecordDetailPage.isEmpty()) {
								log(LogStatus.INFO, "Verified Notification not contains Record(s): " + eventTitleUpdated
										+ " , after Add the Notes from Interaction Card", YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"Not Verified Notification doesn't contain record(s) : " + eventTitleUpdated
												+ ", after Add the Notes from Interaction Card, Reason: "
												+ notificationNegativeResultNotContainsOnContactRecordDetailPage,
										YesNo.No);
								sa.assertTrue(false,
										"Not Verified Notification doesn't contain record(s) : " + eventTitleUpdated
												+ ", after Add the Notes from Interaction Card, Reason: "
												+ notificationNegativeResultNotContainsOnContactRecordDetailPage);
							}

							if (lp.clickOnTab(projectName, tabObj1)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

								if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
										accountRecordType, accountRecordName, 30)) {
									log(LogStatus.INFO, accountRecordName + " record of record type "
											+ accountRecordType + " has been open", YesNo.No);
									ThreadSleep(4000);
									if (BP.clicktabOnPage("Acuity")) {
										log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

										if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
												eventTitleUpdated)) {
											log(LogStatus.INFO,
													"Event Detail Page has been open for Record: " + eventTitleUpdated,
													YesNo.No);
											driver.close();
											CommonLib.ThreadSleep(3000);
											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										} else {
											log(LogStatus.ERROR,
													"Record Detail Page has not open for Record: " + eventTitleUpdated,
													YesNo.Yes);
											sa.assertTrue(false,
													"Record Detail Page has not open for Record: " + eventTitleUpdated);

										}

										List<String> notificationNegativeResultNotContainsOnAccountRecordDetailPage = BP
												.verifyNotificationOptionsNotContainsInRecordDetailPage(
														eventTitleUpdated);
										if (notificationNegativeResultNotContainsOnAccountRecordDetailPage.isEmpty()) {
											log(LogStatus.INFO,
													"Verified Notification not contains Record(s): " + eventTitleUpdated
															+ " , after Add the Notes from Interaction Card",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"Not Verified Notification doesn't contain record(s) : "
															+ eventTitleUpdated
															+ ", after Add the Notes from Interaction Card, Reason: "
															+ notificationNegativeResultNotContainsOnAccountRecordDetailPage,
													YesNo.No);
											sa.assertTrue(false,
													"Not Verified Notification doesn't contain record(s) : "
															+ eventTitleUpdated
															+ ", after Add the Notes from Interaction Card, Reason: "
															+ notificationNegativeResultNotContainsOnAccountRecordDetailPage);
										}

									} else {
										log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Acuity Tab");
									}

								} else {
									log(LogStatus.ERROR, "Not able to open " + accountRecordName
											+ " record of record type " + accountRecordType, YesNo.No);
									sa.assertTrue(false, "Not able to open " + accountRecordName
											+ " record of record type " + accountRecordType);
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
							}

							if (lp.clickOnTab(projectName, TabName.HomeTab)) {
								log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
								List<String> notificationHomeNegativeResult = home
										.verifyNotificationOptionsNotContains(eventTitleUpdated);
								if (notificationHomeNegativeResult.isEmpty()) {
									log(LogStatus.INFO, "Verified Event(s): " + eventTitleUpdated
											+ " not contains in Notification pane in Home Page", YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Not Verified Event(s): " + eventTitleUpdated
													+ " not contains in Notification pane in Home Page, Reason: "
													+ notificationHomeNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"Not Verified Event(s): " + eventTitleUpdated
													+ " not contains in Notification pane in Home Page, Reason: "
													+ notificationHomeNegativeResult);
								}
							} else {
								sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
								log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
							}

						} else {
							log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitleUpdated,
									YesNo.No);
							sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitleUpdated);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Add Noted Button of Event: " + eventTitleUpdated,
								YesNo.No);
						sa.assertTrue(false, "Not Able to Click on Add Noted Button of Event: " + eventTitleUpdated);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc007_CreateEventAndAddTheNotesByLogingAsAttendeeUserAndVerifyTheReminderByLogingAsAssignedUser(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Deal Booking Event 1";
		String eventAttendees = "Dealroom1.3+James@gmail.com,Dealroom1.3+Jhon@gmail.com" + "," + crmUser1EmailID + ","
				+ crmUser2EmailID + "," + crmUser3EmailID + "," + superAdminUserName;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);

		String startTime = "6:00 PM";
		String endTime = "6:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String task1NotesUpdated = "As per your info tagging Account Houlihan Lokey, Acc 11 and Contact  Lomez too.";
		String[][] task1UpdateBasicSection = { { "Notes", task1NotesUpdated } };
		String[][] task1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = "Houlihan Lokey<break>Acc 11<break>Lomez".split("<break>", -1);
		String contactRecordName = "James";
		String accountRecordName = "Houlihan Lokey";
		String accountRecordType = "Institution";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			lp.CRMlogout();
			ThreadSleep(5000);

			lp.CRMLogin(crmUser2EmailID, adminPassword);

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

				CommonLib.ThreadSleep(5000);
				List<String> notificationHomeNegativeResult2 = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult2.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

					if (CommonLib.click(driver, home.addNoteButtonOfEventInHomePageNotification(eventTitle, 20),
							"Add Note Button of Event: " + eventTitle, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Add Note Button of Event: " + eventTitle, YesNo.No);

						if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
								task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
							log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle,
									YesNo.No);

							lp.CRMlogout();
							ThreadSleep(5000);
							lp.CRMLogin(crmUser1EmailID, "navatar123");

							log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
									+ contactRecordName + " Record---------", YesNo.No);
							if (lp.clickOnTab(projectName, tabObj2)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

								if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
										contactRecordName, 30)) {
									log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
									ThreadSleep(4000);
									if (BP.clicktabOnPage("Acuity")) {
										log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

										ArrayList<String> interactionResult = BP.verifyRecordOnInteractionCard(null,
												null, eventTitle, task1NotesUpdated, true, false, null, null);
										if (interactionResult.isEmpty()) {
											log(LogStatus.PASS,
													"------" + eventTitle
															+ " record has been verified on intraction------",
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"------" + eventTitle
															+ " record is not verified on intraction, Reason: "
															+ interactionResult + "------",
													YesNo.No);
											sa.assertTrue(false,
													"------" + eventTitle
															+ " record is not verified on intraction, Reason: "
															+ interactionResult + "------");
										}

										List<String> notificationNegativeResult = BP
												.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
										if (notificationNegativeResult.isEmpty()) {
											log(LogStatus.INFO,
													"Verified Notification not contains for Event(s): " + eventTitle
															+ " in case of User 1 i.e.: " + crmUser1EmailID
															+ " in Detail Page" + contactRecordName,
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"The Event(s) " + eventTitle
															+ " should not contain in case of User 1 i.e.: "
															+ crmUser1EmailID + " in Detail Page: " + contactRecordName
															+ " , Reason: " + notificationNegativeResult,
													YesNo.No);
											sa.assertTrue(false,
													"The Event(s) " + eventTitle
															+ " should not contain in case of User 1 i.e.: "
															+ crmUser1EmailID + " in Detail Page: " + contactRecordName
															+ " , Reason: " + notificationNegativeResult);
										}

									} else {
										log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Acuity Tab");
									}

								} else {
									log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
									sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
							}

							log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
									+ accountRecordName + " Record---------", YesNo.No);
							if (lp.clickOnTab(projectName, tabObj1)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

								if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
										accountRecordType, accountRecordName, 30)) {
									log(LogStatus.INFO, accountRecordName + " record of record type "
											+ accountRecordType + " has been open", YesNo.No);
									ThreadSleep(4000);
									if (BP.clicktabOnPage("Acuity")) {
										log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

										List<String> notificationNegativeResult = BP
												.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
										if (notificationNegativeResult.isEmpty()) {
											log(LogStatus.INFO,
													"Verified Notification not contains for Event(s): " + eventTitle
															+ " in case of User 1 in Detail Page" + accountRecordName,
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "The Event(s) " + eventTitle
													+ " should not contain in case of User 1 in Detail Page: "
													+ accountRecordName + " , Reason: " + notificationNegativeResult,
													YesNo.No);
											sa.assertTrue(false, "The Event(s) " + eventTitle
													+ " should not contain in case of User 1 in Detail Page: "
													+ accountRecordName + " , Reason: " + notificationNegativeResult);
										}

									} else {
										log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Acuity Tab");
									}

								} else {
									log(LogStatus.ERROR, "Not able to open " + accountRecordName
											+ " record of record type " + accountRecordType, YesNo.No);
									sa.assertTrue(false, "Not able to open " + accountRecordName
											+ " record of record type " + accountRecordType);
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
							}

						} else {
							log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle,
									YesNo.No);
							sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Add Noted Button of Event: " + eventTitle, YesNo.No);
						sa.assertTrue(false, "Not Able to Click on Add Noted Button of Event: " + eventTitle);
					}

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc008_LoginAsUser1CreateTheEventByChangingTheAssignedToUserAndVerifyTheNotificationForTheDifferentUsersWhichAreNeitherAnAttendeeNorAsignedAsUser(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Opportunity 1";
		String eventAttendees = "Dealroom1.3+James@gmail.com";
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);

		String startTime = "6:00 PM";
		String endTime = "6:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String contactRecordName = "James";
		String accountRecordName = "Vertica";
		String accountRecordType = "Company";
		String updatedRelatedToInNotes = "Maxjonic<break>Sumo Logic<break>Vertica<break>Demo Deal";

		String user2FullName;
		if (!crmUser2FirstName.equals("") && !crmUser2LastName.equals("")) {
			user2FullName = crmUser2FirstName + " " + crmUser2LastName;
		} else {
			user2FullName = crmUser2LastName;

		}
		String[][] task1UpdateBasicSection = { { "Related_To", updatedRelatedToInNotes } };
		String[][] task1UpdateAdvancedSection = { { "Assigned To ID", user2FullName } };
		String[] updatedSuggestedTags = null;

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

				CommonLib.ThreadSleep(5000);
				List<String> notificationHomeNegativeResult2 = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult2.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

					log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
							+ " Record---------", YesNo.No);
					if (lp.clickOnTab(projectName, tabObj2)) {

						log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

						if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName,
								30)) {
							log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
							ThreadSleep(4000);
							if (BP.clicktabOnPage("Acuity")) {
								log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

								if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
									log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle,
											YesNo.No);
									if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Edit Button of Event: " + eventTitle, YesNo.No);

										if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection,
												task1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
											log(LogStatus.PASS,
													"Activity timeline record has been Updated for: " + eventTitle,
													YesNo.No);

											driver.close();
											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());

											log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle
													+ " in " + accountRecordName + " Record---------", YesNo.No);
											CommonLib.refresh(driver);
											if (lp.clickOnTab(projectName, tabObj1)) {

												log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

												if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
														TabName.InstituitonsTab, accountRecordType, accountRecordName,
														30)) {
													log(LogStatus.INFO, accountRecordName + " record of record type "
															+ accountRecordType + " has been open", YesNo.No);
													ThreadSleep(4000);
													if (BP.clicktabOnPage("Acuity")) {
														log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

														List<String> notificationNegativeResult = BP
																.verifyNotificationOptionsNotContainsInRecordDetailPage(
																		eventTitle);
														if (notificationNegativeResult.isEmpty()) {
															log(LogStatus.INFO,
																	"Verified Notification not contains for Event(s): "
																			+ eventTitle
																			+ " in case of User 1 in Detail Page"
																			+ contactRecordName,
																	YesNo.No);

														} else {
															log(LogStatus.ERROR, "The Event(s) " + eventTitle
																	+ " should not contain in case of User 1 in Detail Page: "
																	+ contactRecordName + " , Reason: "
																	+ notificationNegativeResult, YesNo.No);
															sa.assertTrue(false, "The Event(s) " + eventTitle
																	+ " should not contain in case of User 1 in Detail Page: "
																	+ contactRecordName + " , Reason: "
																	+ notificationNegativeResult);
														}

														if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
																eventTitle)) {
															log(LogStatus.INFO,
																	"Event Detail Page has been open for Record: "
																			+ eventTitle,
																	YesNo.No);

															driver.close();
															driver.switchTo().window(driver.getWindowHandles().stream()
																	.findFirst().get());

														} else {
															log(LogStatus.ERROR,
																	"Event Detail Page has not been open for Record: "
																			+ eventTitle + " in case of User 1 i.e.: "
																			+ crmUser1EmailID + " in Detail Page: "
																			+ contactRecordName,
																	YesNo.Yes);
															sa.assertTrue(false,
																	"Event Detail Page has not been open for Record: "
																			+ eventTitle + " in case of User 1 i.e.: "
																			+ crmUser1EmailID + " in Detail Page: "
																			+ contactRecordName);

														}
													} else {
														log(LogStatus.ERROR, "Not able to click on Acuity Tab",
																YesNo.No);
														sa.assertTrue(false, "Not able to click on Acuity Tab");
													}

												} else {
													log(LogStatus.ERROR,
															"Not able to open " + accountRecordName
																	+ " record of record type " + accountRecordType,
															YesNo.No);
													sa.assertTrue(false, "Not able to open " + accountRecordName
															+ " record of record type " + accountRecordType);
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
												sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
											}

											log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle
													+ " in " + contactRecordName + " Record---------", YesNo.No);
											CommonLib.refresh(driver);
											if (lp.clickOnTab(projectName, tabObj2)) {

												log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

												if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
														TabName.ContactTab, contactRecordName, 30)) {
													log(LogStatus.INFO, contactRecordName + " record has been open",
															YesNo.No);
													ThreadSleep(4000);
													if (BP.clicktabOnPage("Acuity")) {
														log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

														List<String> notificationNegativeResult = BP
																.verifyNotificationOptionsNotContainsInRecordDetailPage(
																		eventTitle);
														if (notificationNegativeResult.isEmpty()) {
															log(LogStatus.INFO,
																	"Verified Notification not contains for Event(s): "
																			+ eventTitle + " in case of User 1 i.e.: "
																			+ crmUser1EmailID + " in Detail Page"
																			+ contactRecordName,
																	YesNo.No);

														} else {
															log(LogStatus.ERROR, "The Event(s) " + eventTitle
																	+ " should not contain i case of User 1 i.e.: "
																	+ crmUser1EmailID + " in Detail Page: "
																	+ contactRecordName + " , Reason: "
																	+ notificationNegativeResult, YesNo.No);
															sa.assertTrue(false, "The Event(s) " + eventTitle
																	+ " should not contain i case of User 1 i.e.: "
																	+ crmUser1EmailID + " in Detail Page: "
																	+ contactRecordName + " , Reason: "
																	+ notificationNegativeResult);
														}

														if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
																eventTitle)) {
															log(LogStatus.INFO,
																	"Event Detail Page has been open for Record: "
																			+ eventTitle,
																	YesNo.No);
															driver.close();
															driver.switchTo().window(driver.getWindowHandles().stream()
																	.findFirst().get());

														} else {
															log(LogStatus.ERROR,
																	"Event Detail Page has not been open for Record: "
																			+ eventTitle + " in case of User 1 i.e.: "
																			+ crmUser1EmailID + " in Detail Page: "
																			+ contactRecordName,
																	YesNo.Yes);
															sa.assertTrue(false,
																	"Event Detail Page has not been open for Record: "
																			+ eventTitle + " in case of User 1 i.e.: "
																			+ crmUser1EmailID + " in Detail Page: "
																			+ contactRecordName);

														}

													} else {
														log(LogStatus.ERROR, "Not able to click on Acuity Tab",
																YesNo.No);
														sa.assertTrue(false, "Not able to click on Acuity Tab");
													}

												} else {
													log(LogStatus.ERROR,
															"Not able to open " + contactRecordName + " record",
															YesNo.No);
													sa.assertTrue(false,
															"Not able to open " + contactRecordName + " record");
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
												sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
											}

											if (lp.clickOnTab(projectName, TabName.HomeTab)) {
												log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
												List<String> notificationHomeNegativeResult = home
														.verifyNotificationOptionsNotContains(eventTitle);
												if (notificationHomeNegativeResult.isEmpty()) {
													log(LogStatus.INFO, "Verified Notification for Event(s): "
															+ eventTitle
															+ " on Home Page not contains in case of user 1 i.e.: "
															+ crmUser1EmailID, YesNo.No);

												} else {
													log(LogStatus.ERROR, "The Event(s) " + eventTitle
															+ "+ on Home Page should not contain in case of User 1 i.e.: "
															+ crmUser1EmailID + ", Reason: "
															+ notificationHomeNegativeResult, YesNo.No);
													sa.assertTrue(false, "The Event(s) " + eventTitle
															+ "+ on Home Page should not contain in case of User 1 i.e.: "
															+ crmUser1EmailID + ", Reason: "
															+ notificationHomeNegativeResult);
												}
											} else {
												sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
												log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab,
														YesNo.Yes);
											}

											lp.CRMlogout();
											ThreadSleep(5000);
											lp.CRMLogin(superAdminUserName, adminPassword, appName);

											List<String> notificationHomeNegativeResult = home
													.verifyNotificationOptionsNotContains(eventTitle);
											if (notificationHomeNegativeResult.isEmpty()) {
												log(LogStatus.INFO,
														"Verified Event(s): " + eventTitle
																+ " not contains in Notification pane in Home Page",
														YesNo.No);

											} else {
												log(LogStatus.ERROR, "Not Verified Event(s): " + eventTitle
														+ " not contains in Notification pane in Home Page, Reason: "
														+ notificationHomeNegativeResult, YesNo.No);
												sa.assertTrue(false, "Not Verified Event(s): " + eventTitle
														+ " not contains in Notification pane in Home Page, Reason: "
														+ notificationHomeNegativeResult);
											}

										} else {
											log(LogStatus.FAIL,
													"Activity timeline record has not Updated for: " + eventTitle,
													YesNo.No);
											sa.assertTrue(false,
													"Activity timeline record has not Updated for: " + eventTitle);
											driver.close();
											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										}

									} else {
										log(LogStatus.ERROR,
												"Not Able Click on Edit button for Record Page of Event: " + eventTitle,
												YesNo.Yes);
										sa.assertTrue(false, "Not Able Click on Edit button for Record Page of Event: "
												+ eventTitle);
										driver.close();
										driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
									}

								} else {
									log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
											YesNo.Yes);
									sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);

								}

							} else {
								log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
								sa.assertTrue(false, "Not able to click on Acuity Tab");
							}

						} else {
							log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
							sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
						sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
					}

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc009_CreateTheEventWithStartDateInPastEndDateInFutureAndVerifyTheNotificationAndReminderForTheAttendeeUser(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Deal Closing";
		String eventAttendees = "Dealroom1.3+Max@gmail.com,Dealroom1.3+Martha@gmail.com" + "," + crmUser2EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 1);

		String startTime = "6:00 PM";
		String endTime = "6:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String contactRecordName = "Martha";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

				CommonLib.ThreadSleep(5000);

				List<String> notificationHomeNegativeResultUser1 = home
						.verifyNotificationOptionsNotContains(eventTitle);
				if (notificationHomeNegativeResultUser1.isEmpty()) {
					log(LogStatus.INFO,
							"Verified Event(s): " + eventTitle + " not contains in Notification pane in Home Page",
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"Not Verified Event(s): " + eventTitle
									+ " not contains in Notification pane in Home Page, Reason: "
									+ notificationHomeNegativeResultUser1,
							YesNo.No);
					sa.assertTrue(false,
							"Not Verified Event(s): " + eventTitle
									+ " not contains in Notification pane in Home Page, Reason: "
									+ notificationHomeNegativeResultUser1);
				}

				log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
						+ " Record---------", YesNo.No);
				if (lp.clickOnTab(projectName, tabObj2)) {

					log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

					if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName,
							30)) {
						log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
						ThreadSleep(4000);
						if (BP.clicktabOnPage("Acuity")) {
							log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

							List<String> notificationNegativeResult = BP
									.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
							if (notificationNegativeResult.isEmpty()) {
								log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitle
										+ " in case of Admin in Detail Page" + contactRecordName, YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"The Event(s) " + eventTitle
												+ " should not contain i case of Admin in Detail Page: "
												+ contactRecordName + " , Reason: " + notificationNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"The Event(s) " + eventTitle
												+ " should not contain i case of Admin in Detail Page: "
												+ contactRecordName + " , Reason: " + notificationNegativeResult);
							}

							if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
								log(LogStatus.INFO,
										"Event Detail Page has been open for Record: " + eventTitle
												+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page: "
												+ contactRecordName,
										YesNo.No);
								driver.close();
								CommonLib.ThreadSleep(3000);
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							} else {
								log(LogStatus.ERROR,
										"Event Detail Page has not been open for Record: " + eventTitle
												+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page: "
												+ contactRecordName,
										YesNo.Yes);
								sa.assertTrue(false,
										"Event Detail Page has not been open for Record: " + eventTitle
												+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page: "
												+ contactRecordName);

							}

							CommonLib.refresh(driver);
							ArrayList<String> result = BP.verifyRecordOnInteractionCard(null, null, eventTitle, null,
									false, true, null, null);
							if (result.isEmpty()) {
								log(LogStatus.PASS,
										"------" + eventTitle + " record has been verified on intraction------",
										YesNo.No);

							} else {
								log(LogStatus.ERROR, "------" + eventTitle
										+ " record is not verified on intraction, Reason: " + result + "------",
										YesNo.No);
								sa.assertTrue(false, "------" + eventTitle
										+ " record is not verified on intraction, Reason: " + result + "------");
							}

							lp.CRMlogout();
							ThreadSleep(5000);
							lp.CRMLogin(crmUser2EmailID, adminPassword);

							List<String> notificationHomeNegativeResult = home
									.verifyNotificationOptionsNotContains(eventTitle);
							if (notificationHomeNegativeResult.isEmpty()) {
								log(LogStatus.INFO, "Verified Event(s): " + eventTitle
										+ " not contains in Notification pane in Home Page", YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"Not Verified Event(s): " + eventTitle
												+ " not contains in Notification pane in Home Page, Reason: "
												+ notificationHomeNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Not Verified Event(s): " + eventTitle
												+ " not contains in Notification pane in Home Page, Reason: "
												+ notificationHomeNegativeResult);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
							sa.assertTrue(false, "Not able to click on Acuity Tab");
						}

					} else {
						log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
						sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
					sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc010_VerifyTheNotificationWhenEventIsCreatedWithNeitherContactNorRelatedAssociationTagged(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Announcing RampUp 2022 speakers and more +1";
		String eventAttendees = crmUser4EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);

		String startTime = "6:00 PM";
		String endTime = "6:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String contactRecordName = "Litz";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);
			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO,
							"Verified Notification for Event(s): " + eventTitle
									+ " on Home Page not contains in case of User 1 i.e.: " + crmUser1EmailID,
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 1 i.e.: "
									+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult,
							YesNo.No);
					sa.assertTrue(false,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 1 i.e.: "
									+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"Verified Notification not contains for Event(s): " + eventTitle
											+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page"
											+ contactRecordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"The Event(s) " + eventTitle + " should not contain in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"The Event(s) " + eventTitle + " should not contain in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			lp.CRMlogout();
			ThreadSleep(5000);

			lp.CRMLogin(crmUser4EmailID, "navatar123");

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO,
							"Verified Notification for Event(s): " + eventTitle
									+ " on Home Page not contains in case of User 4 i.e.: " + crmUser4EmailID,
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 4 i.e.: "
									+ crmUser4EmailID + ", Reason: " + notificationHomeNegativeResult,
							YesNo.No);
					sa.assertTrue(false,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 4 i.e.: "
									+ crmUser4EmailID + ", Reason: " + notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void rgAcuityMNNRTc011_CreateTheEventWithAttendeesTaggedRemoveOneOfTheAttendeeAndVerifyTheNotificationBeforeAndAfterRemovingTheUserAsAttendee(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "This event will have your Business circles talking +3";
		String eventAttendees = "Dealroom1.3+Litz@gmail.com" + "," + crmUser2EmailID + "," + crmUser3EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);

		String navigateStartDateForEdit = startDate;
		String navigateStartDateMonthYearForEdit = CommonLib.convertDateFromOneFormatToAnother(startDate, "M/d/yyyy",
				"MMMM yyyy");

		String startTime = "6:00 PM";
		String endTime = "6:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String contactRecordName = "Litz";

		String eventTitleUpdated = null;
		String eventAttendeesUpdated = null;
		String startDateUpdated = null;
		String endDateUpdated = null;
		String startTimeUpdated = null;
		String endTimeUpdated = null;
		String descriptionBoxUpdated = null;
		String eventAttendeesToRemove = crmUser2EmailID;

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------", YesNo.No);
			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO,
							"Verified Notification for Event(s): " + eventTitle + " on Home Page in case of User 1",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
							+ "+ on Home Page in case of User 1, Reason: " + notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
							+ "+ on Home Page in case of User 1, Reason: " + notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"Verified Notification contains for Event(s): " + eventTitle
											+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page"
											+ contactRecordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult);
						}

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);
							driver.close();
							CommonLib.ThreadSleep(3000);
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						} else {
							log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
									YesNo.Yes);
							sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);

						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			lp.CRMlogout();
			ThreadSleep(5000);

			lp.CRMLogin(crmUser2EmailID, adminPassword);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO,
							"Verified Notification for Event(s): " + eventTitle + " on Home Page in case of User 2",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
							+ "+ on Home Page in case of User 2, Reason: " + notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
							+ "+ on Home Page in case of User 2, Reason: " + notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}
			log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"Verified Notification contains for Event(s): " + eventTitle
											+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page"
											+ contactRecordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult);
						}

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);
							driver.close();
							CommonLib.ThreadSleep(3000);
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						} else {
							log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
									YesNo.Yes);
							sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);

						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			if (op.loginNavigateAndUpdateTheEvent(rgOutLookUser1Email, rgOutLookUser1Password, navigateStartDateForEdit,
					navigateStartDateMonthYearForEdit, eventTitle, false, eventTitleUpdated, eventAttendeesUpdated,
					startDateUpdated, endDateUpdated, startTimeUpdated, endTimeUpdated, descriptionBoxUpdated, false,
					eventAttendeesToRemove)) {
				log(LogStatus.INFO, "-----Event Updated Msg is showing, So Event of Title: " + eventTitle
						+ " has been Updated-----", YesNo.No);
				CommonLib.refresh(driver);
				if (lp.clickOnTab(projectName, TabName.HomeTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
					List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitle);
					if (notificationHomeNegativeResult.isEmpty()) {
						log(LogStatus.INFO,
								"Verified Notification for Event(s): " + eventTitle
										+ " on Home Page not contains in case of User 2 i.e.: " + crmUser2EmailID,
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Event(s) " + eventTitle
										+ "+ on Home Page should not contain in case of User 2 i.e.: " + crmUser2EmailID
										+ ", Reason: " + notificationHomeNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Event(s) " + eventTitle
										+ "+ on Home Page should not contain in case of User 2 i.e.: " + crmUser2EmailID
										+ ", Reason: " + notificationHomeNegativeResult);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
				}

				log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
						+ " Record not contains in case of User 2---------", YesNo.No);
				if (lp.clickOnTab(projectName, tabObj2)) {

					log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

					if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName,
							30)) {
						log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
						ThreadSleep(4000);
						if (BP.clicktabOnPage("Acuity")) {
							log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

							List<String> notificationNegativeResult = BP
									.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
							if (notificationNegativeResult.isEmpty()) {
								log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitle
										+ " in case of User 2 in Detail Page" + contactRecordName, YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"The Event(s) " + eventTitle
												+ " should not contain in case of User 2 in Detail Page: "
												+ contactRecordName + " , Reason: " + notificationNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"The Event(s) " + eventTitle
												+ " should not contain in case of User 2 in Detail Page: "
												+ contactRecordName + " , Reason: " + notificationNegativeResult);
							}

							if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
								log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle
										+ " in case of User 2 in Detail Page: " + contactRecordName, YesNo.No);
								driver.close();
								CommonLib.ThreadSleep(3000);
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							} else {
								log(LogStatus.ERROR, "Event Record Detail Page has not opened for Record: " + eventTitle
										+ " in case of User 2 in Detail Page: " + contactRecordName, YesNo.Yes);
								sa.assertTrue(false, "Event Record Detail Page has not opened for Record: " + eventTitle
										+ " in case of User 2 in Detail Page: " + contactRecordName);

							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
							sa.assertTrue(false, "Not able to click on Acuity Tab");
						}

					} else {
						log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
						sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
					sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
				}

				lp.CRMlogout();
				ThreadSleep(5000);

				log(LogStatus.INFO,
						"---------Now Going to Verify Event: " + eventTitle
								+ " not contans in Home Page Notification Section in case of user 1 i.e.: "
								+ crmUser1EmailID + "---------",
						YesNo.No);
				lp.CRMLogin(crmUser1EmailID, "navatar123");

				if (lp.clickOnTab(projectName, TabName.HomeTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
					List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
					if (notificationHomeNegativeResult.isEmpty()) {
						log(LogStatus.INFO,
								"Verified Notification for Event(s): " + eventTitle + " on Home Page in case of User 1",
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
								+ "+ on Home Page in case of User 1, Reason: " + notificationHomeNegativeResult,
								YesNo.No);
						sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
								+ "+ on Home Page in case of User 1, Reason: " + notificationHomeNegativeResult);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
				}

			}

			else {
				log(LogStatus.ERROR, "-----Event Updated Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been Updated-----", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Event Updated Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been Updated-----");
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc012_CreateThePastDatedEventVerifyTheNotificationDeleteTheEventAndVerify(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String eventTitle = "Join all major influencers";
		String eventAttendees = "Dealroom1.3+Lomez@gmail.com," + crmUser1EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);

		String startTime = "6:00 PM";
		String endTime = "6:30 PM";
		String descriptionBox = "Revenue Grid Event";
		String recordName = "Lomez";

		String updatedRelatedTo = "Sumo Logic";
		String[][] event1UpdateBasicSectionOnHomepageNotePopup = { { "Related_To", updatedRelatedTo } };
		String[][] event1UpdateAdvancedSectionOnHomepageNotePopup = null;
		String[] eventupdatedSuggestedTagsOnHomepageNotePopup = null;

		String accountRecordName = "Sumo Logic";
		String accountRecordType = "Company";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);

							if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Edit Button of Event: " + eventTitle, YesNo.No);

								if (BP.updateActivityTimelineRecord(projectName,
										event1UpdateBasicSectionOnHomepageNotePopup,
										event1UpdateAdvancedSectionOnHomepageNotePopup, null,
										eventupdatedSuggestedTagsOnHomepageNotePopup, null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle,
											YesNo.No);
									driver.close();
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
									CommonLib.refresh(driver);

									log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
											+ accountRecordName + " Record---------", YesNo.No);
									if (lp.clickOnTab(projectName, tabObj1)) {

										log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

										if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
												TabName.InstituitonsTab, accountRecordType, accountRecordName, 30)) {
											log(LogStatus.INFO, accountRecordName + " record of record type "
													+ accountRecordType + " has been open", YesNo.No);
											ThreadSleep(4000);
											if (BP.clicktabOnPage("Acuity")) {
												log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

												List<String> notificationNegativeResult = BP
														.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
												if (notificationNegativeResult.isEmpty()) {
													log(LogStatus.INFO,
															"Verified Notification for Event(s): " + eventTitle,
															YesNo.No);

												} else {
													log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
															+ ", Reason: " + notificationNegativeResult, YesNo.No);
													sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
															+ ", Reason: " + notificationNegativeResult);
												}

												if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
														eventTitle)) {
													log(LogStatus.INFO,
															"Event Detail Page has been open for Record: " + eventTitle,
															YesNo.No);
													driver.close();
													CommonLib.ThreadSleep(3000);
													driver.switchTo().window(
															driver.getWindowHandles().stream().findFirst().get());
												} else {
													log(LogStatus.ERROR,
															"Record Detail Page has not open for Record: " + eventTitle,
															YesNo.Yes);
													sa.assertTrue(false, "Record Detail Page has not open for Record: "
															+ eventTitle);

												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
												sa.assertTrue(false, "Not able to click on Acuity Tab");
											}

										} else {
											log(LogStatus.ERROR, "Not able to open " + accountRecordName
													+ " record of record type " + accountRecordType, YesNo.No);
											sa.assertTrue(false, "Not able to open " + accountRecordName
													+ " record of record type " + accountRecordType);
										}
									} else {
										log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
										sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
									}

									CommonLib.refresh(driver);
									log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
											+ recordName + " Record---------", YesNo.No);
									if (lp.clickOnTab(projectName, tabObj2)) {

										log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

										if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
												recordName, 30)) {
											log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
											ThreadSleep(4000);
											if (BP.clicktabOnPage("Acuity")) {
												log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

												List<String> notificationNegativeResult = BP
														.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
												if (notificationNegativeResult.isEmpty()) {
													log(LogStatus.INFO,
															"Verified Notification for Event(s): " + eventTitle,
															YesNo.No);

												} else {
													log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
															+ ", Reason: " + notificationNegativeResult, YesNo.No);
													sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
															+ ", Reason: " + notificationNegativeResult);
												}

												if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
														eventTitle)) {
													log(LogStatus.INFO,
															"Event Detail Page has been open for Record: " + eventTitle,
															YesNo.No);

													if (click(driver, taskBP.deleteButtonInEventDetailPage(15),
															"Event Delete Button", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO, "Clicked on Delete Button", YesNo.No);
														if (click(driver, taskBP.taskDeleteConfirmButton(15),
																"Delete Confirm Button", action.SCROLLANDBOOLEAN)) {
															log(LogStatus.INFO, "Clicked on Delete Confirm Button",
																	YesNo.No);

															if (taskBP.taskDeletedMsg(15) != null) {
																log(LogStatus.INFO,
																		"Event Delete Msg displayed, So Event has been deleted",
																		YesNo.No);
																driver.close();
																driver.switchTo().window(driver.getWindowHandles()
																		.stream().findFirst().get());
																CommonLib.refresh(driver);
																CommonLib.ThreadSleep(5000);

																log(LogStatus.INFO,
																		"---------Now Going to Verify Event: "
																				+ eventTitle + " in "
																				+ accountRecordName
																				+ " Record---------",
																		YesNo.No);
																if (lp.clickOnTab(projectName, tabObj1)) {

																	log(LogStatus.INFO, "Clicked on Tab : " + tabObj1,
																			YesNo.No);

																	if (BP.clickOnAlreadyCreated_Lighting(environment,
																			mode, TabName.InstituitonsTab,
																			accountRecordType, accountRecordName, 30)) {
																		log(LogStatus.INFO, accountRecordName
																				+ " record of record type "
																				+ accountRecordType + " has been open",
																				YesNo.No);
																		ThreadSleep(4000);
																		if (BP.clicktabOnPage("Acuity")) {
																			log(LogStatus.INFO, "clicked on Acuity tab",
																					YesNo.No);

																			List<String> notificationNegativeResultAfterDeleteInFirm = BP
																					.verifyNotificationOptionsNotContainsInRecordDetailPage(
																							eventTitle);
																			if (notificationNegativeResultAfterDeleteInFirm
																					.isEmpty()) {
																				log(LogStatus.INFO,
																						"Verified Notification not contains for Event(s): "
																								+ eventTitle
																								+ " in case of User 1 in Detail Page"
																								+ accountRecordName
																								+ " after delte the event",
																						YesNo.No);

																			} else {
																				log(LogStatus.ERROR, "The Event(s) "
																						+ eventTitle
																						+ " should not contain in case of User 1 in Detail Page: "
																						+ accountRecordName
																						+ " after delete the event  , Reason: "
																						+ notificationNegativeResultAfterDeleteInFirm,
																						YesNo.No);
																				sa.assertTrue(false, "The Event(s) "
																						+ eventTitle
																						+ " should not contain in case of User 1 in Detail Page: "
																						+ accountRecordName
																						+ " after delete the event  , Reason: "
																						+ notificationNegativeResultAfterDeleteInFirm);
																			}

																			if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(
																					eventTitle)) {
																				log(LogStatus.INFO,
																						"Event Detail Page has not been open for Record: "
																								+ eventTitle
																								+ " in case of User 1 in Detail Page: "
																								+ accountRecordName
																								+ " after delete the event",
																						YesNo.No);

																			} else {
																				log(LogStatus.ERROR,
																						"Event Record Detail Page  has opened for Record: "
																								+ eventTitle
																								+ " in case of User 1 in Detail Page: "
																								+ accountRecordName
																								+ " after delete the event",
																						YesNo.Yes);
																				sa.assertTrue(false,
																						"Event Record Detail Page  has opened for Record: "
																								+ eventTitle
																								+ " in case of User 1 in Detail Page: "
																								+ accountRecordName
																								+ " after delete the event");
																				driver.close();
																				CommonLib.ThreadSleep(3000);
																				driver.switchTo().window(driver
																						.getWindowHandles().stream()
																						.findFirst().get());
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
																				"Not able to open " + accountRecordName
																						+ " record of record type "
																						+ accountRecordType,
																				YesNo.No);
																		sa.assertTrue(false,
																				"Not able to open " + accountRecordName
																						+ " record of record type "
																						+ accountRecordType);
																	}
																} else {
																	log(LogStatus.ERROR,
																			"Not able to click on Tab : " + tabObj1,
																			YesNo.No);
																	sa.assertTrue(false,
																			"Not able to click on Tab : " + tabObj1);
																}

																List<String> notificationNegativeResultAfterDelete = BP
																		.verifyNotificationOptionsNotContainsInRecordDetailPage(
																				eventTitle);
																if (notificationNegativeResultAfterDelete.isEmpty()) {
																	log(LogStatus.INFO,
																			"Verified Notification not contains for Event(s): "
																					+ eventTitle
																					+ " in case of User 1 in Detail Page"
																					+ recordName
																					+ " after delete the event",
																			YesNo.No);

																} else {
																	log(LogStatus.ERROR, "The Event(s) " + eventTitle
																			+ " should not contain  after delete the event in case of User 1 in Detail Page: "
																			+ recordName + " , Reason: "
																			+ notificationNegativeResultAfterDelete,
																			YesNo.No);
																	sa.assertTrue(false, "The Event(s) " + eventTitle
																			+ " should not contain  after delete the event in case of User 1 in Detail Page: "
																			+ recordName + " , Reason: "
																			+ notificationNegativeResultAfterDelete);
																}

																if (lp.clickOnTab(projectName, TabName.HomeTab)) {
																	log(LogStatus.INFO,
																			"Click on Tab : " + TabName.HomeTab,
																			YesNo.No);
																	List<String> notificationHomeNegativeResult = home
																			.verifyNotificationOptionsNotContains(
																					eventTitle);
																	if (notificationHomeNegativeResult.isEmpty()) {
																		log(LogStatus.INFO,
																				"Verified Notification for Event(s): "
																						+ eventTitle
																						+ " on Home Page not contains after delete the event in case of User 1, i.e.: "
																						+ crmUser1EmailID,
																				YesNo.No);

																	} else {
																		log(LogStatus.ERROR, "The Event(s) "
																				+ eventTitle
																				+ "+ on Home Page should not contain after delete the event in case of User 1, i.e.: "
																				+ crmUser1EmailID + " ,Reason: "
																				+ notificationHomeNegativeResult,
																				YesNo.No);
																		sa.assertTrue(false, "The Event(s) "
																				+ eventTitle
																				+ "+ on Home Page should not contain after delete the event in case of User 1, i.e.: "
																				+ crmUser1EmailID + " ,Reason: "
																				+ notificationHomeNegativeResult);
																	}
																} else {
																	sa.assertTrue(false, "Not Able to Click on Tab : "
																			+ TabName.HomeTab);
																	log(LogStatus.SKIP, "Not Able to Click on Tab : "
																			+ TabName.HomeTab, YesNo.Yes);
																}

															} else {
																log(LogStatus.ERROR,
																		"Event Delete Msg not display, So Event not gets deleted "
																				+ eventTitle,
																		YesNo.Yes);
																sa.assertTrue(false,
																		"Event Delete Msg not display, So Event not gets deleted "
																				+ eventTitle);
																driver.close();
																driver.switchTo().window(driver.getWindowHandles()
																		.stream().findFirst().get());
															}

														} else {
															log(LogStatus.ERROR,
																	"Not ABle to Click on Delete Confirm Button in case of Event: "
																			+ eventTitle,
																	YesNo.Yes);
															sa.assertTrue(false,
																	"Not ABle to Click on Delete Confirm Button in case of Event: "
																			+ eventTitle);
															driver.close();
															driver.switchTo().window(driver.getWindowHandles().stream()
																	.findFirst().get());

														}

													} else {
														log(LogStatus.ERROR, "Not Able Click on Delete button",
																YesNo.Yes);
														sa.assertTrue(false, "Not Able Click on Delete button");
														driver.close();
														driver.switchTo().window(
																driver.getWindowHandles().stream().findFirst().get());
													}

												} else {
													log(LogStatus.ERROR,
															"Record Detail Page has not open for Record: " + eventTitle,
															YesNo.Yes);
													sa.assertTrue(false, "Record Detail Page has not open for Record: "
															+ eventTitle);

												}
											} else {
												log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
												sa.assertTrue(false, "Not able to click on Acuity Tab");
											}

										} else {
											log(LogStatus.ERROR, "Not able to open " + recordName + " record",
													YesNo.No);
											sa.assertTrue(false, "Not able to open " + recordName + " record");
										}
									} else {
										log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
										sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
									}

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle,
											YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);

									driver.close();
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
								}

							} else {
								log(LogStatus.ERROR,
										"Not Able Click on Edit button for Record Page of Event: " + eventTitle,
										YesNo.Yes);
								sa.assertTrue(false,
										"Not Able Click on Edit button for Record Page of Event: " + eventTitle);
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}
						} else {
							log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
									YesNo.Yes);
							sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);

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
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc013_RestoreTheDeletedEventAndVerifyTheNotificationForUser1(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Join all major influencers";

		String recordName = "Lomez";

		String[][] listViewSheetData = { { "user 1", "Recycle Bin", eventTitle, "All users can see this list view",
				"My recycle bin", "Record Name", "equals", eventTitle, "TextBox" } };
		String accountRecordName = "Sumo Logic";
		String accountRecordType = "Company";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		WebElement ele;
		String recycleTab = lp.getTabName(projectName, TabName.RecycleBinTab);
		if (lp.openAppFromAppLauchner(60, recycleTab)) {

			CommonLib.refresh(driver);

			for (String[] row : listViewSheetData) {

				if (lp.addListView(projectName, row, 10)) {
					log(LogStatus.INFO, "list view added on " + row[1], YesNo.No);
					ele = lp.getCheckboxOfRestoreItemOnRecycleBin(projectName, eventTitle, 30);
					if (clickUsingJavaScript(driver, ele, "Check box against : " + eventTitle, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on checkbox for " + eventTitle, YesNo.No);
						;
						ele = lp.getRestoreButtonOnRecycleBin(projectName, 30);
						if (clickUsingJavaScript(driver, ele, "Restore Button : " + eventTitle, action.BOOLEAN)) {
							ThreadSleep(10000);
							log(LogStatus.INFO, "Click on Restore Button for " + eventTitle, YesNo.No);
							sa.assertTrue(true, "Event has been restore from the Recycle bin");

							CommonLib.switchToDefaultContent(driver);
							ThreadSleep(2000);

							log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
									+ accountRecordName + " Record---------", YesNo.No);
							if (lp.clickOnTab(projectName, tabObj1)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

								if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
										accountRecordType, accountRecordName, 30)) {
									log(LogStatus.INFO, accountRecordName + " record of record type "
											+ accountRecordType + " has been open", YesNo.No);
									ThreadSleep(4000);
									if (BP.clicktabOnPage("Acuity")) {
										log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

										List<String> notificationNegativeResult = BP
												.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
										if (notificationNegativeResult.isEmpty()) {
											log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle,
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
													+ ", Reason: " + notificationNegativeResult, YesNo.No);
											sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
													+ ", Reason: " + notificationNegativeResult);
										}

										if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
											log(LogStatus.INFO,
													"Event Detail Page has been open for Record: " + eventTitle,
													YesNo.No);
											driver.close();
											CommonLib.ThreadSleep(3000);
											driver.switchTo()
													.window(driver.getWindowHandles().stream().findFirst().get());
										} else {
											log(LogStatus.ERROR,
													"Record Detail Page has not open for Record: " + eventTitle,
													YesNo.Yes);
											sa.assertTrue(false,
													"Record Detail Page has not open for Record: " + eventTitle);

										}
									} else {
										log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Acuity Tab");
									}

								} else {
									log(LogStatus.ERROR, "Not able to open " + accountRecordName
											+ " record of record type " + accountRecordType, YesNo.No);
									sa.assertTrue(false, "Not able to open " + accountRecordName
											+ " record of record type " + accountRecordType);
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
							}

							log(LogStatus.INFO, "---------Now Going to Verify Notification of Event: " + eventTitle
									+ " in Contact Record Page---------", YesNo.No);

							if (lp.clickOnTab(projectName, tabObj2)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

								if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName,
										30)) {
									log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
									ThreadSleep(4000);
									if (BP.clicktabOnPage("Acuity")) {
										log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

										List<String> notificationNegativeResult = BP
												.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
										if (notificationNegativeResult.isEmpty()) {
											log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle,
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
													+ ", Reason: " + notificationNegativeResult, YesNo.No);
											sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
													+ ", Reason: " + notificationNegativeResult);
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
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
							}

							if (lp.clickOnTab(projectName, TabName.HomeTab)) {
								log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
								List<String> notificationHomeNegativeResult = home
										.verifyNotificationOptions(eventTitle);
								if (notificationHomeNegativeResult.isEmpty()) {
									log(LogStatus.INFO,
											"Verified Notification for Event(s): " + eventTitle + " on Home Page",
											YesNo.No);

								} else {
									log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle
											+ "+ on Home Page, Reason: " + notificationHomeNegativeResult, YesNo.No);
									sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle
											+ "+ on Home Page, Reason: " + notificationHomeNegativeResult);
								}
							} else {
								sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
								log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
							}

						} else {

							log(LogStatus.ERROR, "Not Able to Click on Restore Button for " + eventTitle, YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Restore Button for " + eventTitle);
						}

					} else {

						log(LogStatus.ERROR, "Not Able to Click on checkbox for " + eventTitle, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on checkbox for " + eventTitle);
					}
				}

				else {
					log(LogStatus.FAIL, "list view could not added on " + row[1], YesNo.Yes);
					sa.assertTrue(false, "list view could not added on " + row[1]);
				}
			}

		} else {
			log(LogStatus.ERROR, "Not Able to open the Recycle been tab", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the Recycle been tab");

		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void rgAcuityMNNRTc014_UpdateTheSubjectOfTheEventAndVerifyTheSameAtNotificationPopUpAsWellAsInteractionSection(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "This event will have your Business circles talking +3";

		String contactRecordName = "Litz";

		String eventTitleUpdated = "Updated This event will have your Business circles talking +3";
		String[][] event1UpdateBasicSectionOnHomepageNotePopup = { { "Subject", eventTitleUpdated } };
		String[][] event1UpdateAdvancedSectionOnHomepageNotePopup = null;
		String[] eventupdatedSuggestedTagsOnHomepageNotePopup = null;

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO,
				"---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName + " Record---------",
				YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
						log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);

						if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Edit Button of Event: " + eventTitle, YesNo.No);

							if (BP.updateActivityTimelineRecord(projectName,
									event1UpdateBasicSectionOnHomepageNotePopup,
									event1UpdateAdvancedSectionOnHomepageNotePopup, null,
									eventupdatedSuggestedTagsOnHomepageNotePopup, null)) {
								log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle,
										YesNo.No);
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								CommonLib.refresh(driver);
								log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitleUpdated + " in "
										+ contactRecordName + " Record---------", YesNo.No);

								List<String> notificationNegativeResultForOldName = BP
										.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
								if (notificationNegativeResultForOldName.isEmpty()) {
									log(LogStatus.INFO,
											"Verified Notification not contains for Event(s): " + eventTitle
													+ " in case of User 1 in Detail Page" + contactRecordName,
											YesNo.No);

								} else {
									log(LogStatus.ERROR, "The Event(s) " + eventTitle
											+ " should not contain in case of User 1 in Detail Page: "
											+ contactRecordName + " , Reason: " + notificationNegativeResultForOldName,
											YesNo.No);
									sa.assertTrue(false, "The Event(s) " + eventTitle
											+ " should not contain in case of User 1 in Detail Page: "
											+ contactRecordName + " , Reason: " + notificationNegativeResultForOldName);
								}

								CommonLib.refresh(driver);

								List<String> notificationNegativeResult = BP
										.verifyNotificationOptionsOnRecordDetailsPage(eventTitleUpdated);
								if (notificationNegativeResult.isEmpty()) {
									log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleUpdated
											+ " in record page of: " + contactRecordName, YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Not Verified the Event(s) " + eventTitleUpdated + " in record page of: "
													+ contactRecordName + ", Reason: " + notificationNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"Not Verified the Event(s) " + eventTitleUpdated + " in record page of: "
													+ contactRecordName + ", Reason: " + notificationNegativeResult);
								}
								CommonLib.refresh(driver);

								CommonLib.ThreadSleep(5000);

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
									List<String> notificationHomeNegativeResultOldEventName = home
											.verifyNotificationOptionsNotContains(eventTitle);
									if (notificationHomeNegativeResultOldEventName.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle
												+ " on Home Page not contains", YesNo.No);

									} else {
										log(LogStatus.ERROR, "The Event(s) " + eventTitle
												+ "+ on Home Page should not contain in case of User 1, Reason: "
												+ notificationHomeNegativeResultOldEventName, YesNo.No);
										sa.assertTrue(false, "The Event(s) " + eventTitle
												+ "+ on Home Page should not contain in case of User 1, Reason: "
												+ notificationHomeNegativeResultOldEventName);
									}

								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
									List<String> notificationHomeNegativeResult = home
											.verifyNotificationOptions(eventTitleUpdated);
									if (notificationHomeNegativeResult.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleUpdated
												+ " on Home Page", YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"Not Verified the Event(s) " + eventTitleUpdated
														+ "+ on Home Page, Reason: " + notificationHomeNegativeResult,
												YesNo.No);
										sa.assertTrue(false, "Not Verified the Event(s) " + eventTitleUpdated
												+ "+ on Home Page, Reason: " + notificationHomeNegativeResult);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

							} else {
								log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle,
										YesNo.No);
								sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

						} else {
							log(LogStatus.ERROR,
									"Not Able Click on Edit button for Record Page of Event: " + eventTitle, YesNo.Yes);
							sa.assertTrue(false,
									"Not Able Click on Edit button for Record Page of Event: " + eventTitle);
							driver.close();
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						}
					} else {
						log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle, YesNo.Yes);
						sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void rgAcuityMNNRTc015_VerifyTheCustomNotificationOnHomePageAsWellAsByClickingOnBellIconForExistingTask(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String task1SubjectName = "Task Demo 3";

		String contactRecordName = "Con 1";
		String recordName = "Acc 3";
		String recordType = "Company";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Verify Notification of Task: " + task1SubjectName + " in "
				+ contactRecordName + " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(task1SubjectName);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Task(s): " + task1SubjectName
								+ " in case of User 1 in Detail Page" + contactRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Task(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Task(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Task: " + task1SubjectName + " in " + recordName
				+ " Record not contains in Notification Pane in case of User 1--------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(task1SubjectName);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Task(s): " + task1SubjectName
								+ " in case of User 1 in Detail Page" + recordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Task(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + recordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Task(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + recordName
										+ " , Reason: " + notificationNegativeResult);
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

		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(task1SubjectName);
			if (notificationHomeNegativeResult.isEmpty()) {
				log(LogStatus.INFO,
						"Verified Notification for Task(s): " + task1SubjectName + " on Home Page not contains",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"The Task(s) " + task1SubjectName
								+ "+ on Home Page should not contain in case of User 1, Reason: "
								+ notificationHomeNegativeResult,
						YesNo.No);
				sa.assertTrue(false,
						"The Task(s) " + task1SubjectName
								+ "+ on Home Page should not contain in case of User 1, Reason: "
								+ notificationHomeNegativeResult);
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
	public void rgAcuityMNNRTc016_VerifyTheCustomNotificationOnHomePageAsWellAsByClickingOnBellIconForExistingCall(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String task1SubjectName = "Task Demo 3 Call";

		String contactRecordName = "Con 1";
		String recordName = "Acc 3";
		String recordType = "Company";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Verify Notification of Call: " + task1SubjectName + " in "
				+ contactRecordName + " Record---------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(task1SubjectName);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Call(s): " + task1SubjectName
								+ " in case of User 1 in Detail Page" + contactRecordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Call(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Call(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + contactRecordName
										+ " , Reason: " + notificationNegativeResult);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		log(LogStatus.INFO, "---------Now Going to Verify Call: " + task1SubjectName + " in " + recordName
				+ " Record not contains in Notification Pane in case of User 1--------", YesNo.No);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordType, recordName,
					30)) {
				log(LogStatus.INFO, recordName + " record of record type " + recordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					List<String> notificationNegativeResult = BP
							.verifyNotificationOptionsNotContainsInRecordDetailPage(task1SubjectName);
					if (notificationNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification not contains for Call(s): " + task1SubjectName
								+ " in case of User 1 in Detail Page" + recordName, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"The Call(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + recordName
										+ " , Reason: " + notificationNegativeResult,
								YesNo.No);
						sa.assertTrue(false,
								"The Call(s) " + task1SubjectName
										+ " should not contain in case of User 1 in Detail Page: " + recordName
										+ " , Reason: " + notificationNegativeResult);
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

		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(task1SubjectName);
			if (notificationHomeNegativeResult.isEmpty()) {
				log(LogStatus.INFO,
						"Verified Notification for Call(s): " + task1SubjectName + " on Home Page not contains",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"The Call(s) " + task1SubjectName
								+ "+ on Home Page should not contain in case of User 1, Reason: "
								+ notificationHomeNegativeResult,
						YesNo.No);
				sa.assertTrue(false,
						"The Call(s) " + task1SubjectName
								+ "+ on Home Page should not contain in case of User 1, Reason: "
								+ notificationHomeNegativeResult);
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
	public void rgAcuityMNNRTc017_AddTheNotesFromTheCustomBellIconAndVerifyTheNotificationBeforeAndAfterUpdatingThenotes(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Event 2.0";

		String contactRecordName = "Litz";

		String[][] event1BasicSectionVerificationInNotesPopup = { { "Subject", eventTitle }, { "Notes", "" } };
		String[][] event1AdvancedSectionVerificationInNotesPopup = null;

		String notesVerifyOnInteraction = "";
		String[] relatedToVerifyOnInteraction = null;
		String updatedNotesOfEvent = "Adding @Glomez @Jack in the loop";
		String[][] event1UpdateBasicSection = { { "Notes", updatedNotesOfEvent } };
		String[][] event1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = null;

		String accountRecordName = "Nexus";
		String accountRecordType = "Institution";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in Interaction Section---------",
				YesNo.No);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
				log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					ArrayList<String> interactionResult = BP.verifyRecordOnInteractionCard(null, null, eventTitle, "",
							false, true, relatedToVerifyOnInteraction, null);
					if (interactionResult.isEmpty()) {
						log(LogStatus.PASS, "------" + eventTitle + " record has been verified on intraction------",
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "------" + eventTitle + " record is not verified on intraction, Reason: "
								+ interactionResult + "------", YesNo.No);
						sa.assertTrue(false, "------" + eventTitle + " record is not verified on intraction, Reason: "
								+ interactionResult + "------");
					}

					if (CommonLib.clickUsingJavaScript(driver, BP.getNotificationIcon(), "NotificationIcon")) {

						log(LogStatus.INFO, "Clicked on Notification Icon", YesNo.No);
						if (CommonLib.click(driver, home.addNoteButtonInNotificationOfRecordDetailPage(eventTitle, 20),
								"Add Note Button of Event: " + eventTitle, action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Add Note Button of Event: " + eventTitle, YesNo.No);

							String url2 = getURL(driver, 10);
							ThreadSleep(10000);
							ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
									.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
											event1BasicSectionVerificationInNotesPopup,
											event1AdvancedSectionVerificationInNotesPopup, null);
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

							if (BP.updateActivityTimelineRecord(projectName, event1UpdateBasicSection,
									event1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
								log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle,
										YesNo.No);

								CommonLib.refresh(driver);
								ArrayList<String> updatedInteractionResult = BP.verifyRecordOnInteractionCard(null,
										null, eventTitle, updatedNotesOfEvent, true, false,
										relatedToVerifyOnInteraction, null);
								if (updatedInteractionResult.isEmpty()) {
									log(LogStatus.PASS,
											"------" + eventTitle + " record has been verified on intraction------",
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"------" + eventTitle + " record is not verified on intraction, Reason: "
													+ updatedInteractionResult + "------",
											YesNo.No);
									sa.assertTrue(false,
											"------" + eventTitle + " record is not verified on intraction, Reason: "
													+ updatedInteractionResult + "------");
								}

								CommonLib.refresh(driver);
								List<String> notificationNegativeResultInContactRecordPage = BP
										.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
								if (notificationNegativeResultInContactRecordPage.isEmpty()) {
									log(LogStatus.INFO,
											"Verified Notification not contains for Event(s): " + eventTitle
													+ " in case of User 1 in Detail Page" + contactRecordName,
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"The Event(s) " + eventTitle
													+ " should not contain in case of User 1 in Detail Page: "
													+ contactRecordName + " , Reason: "
													+ notificationNegativeResultInContactRecordPage,
											YesNo.No);
									sa.assertTrue(false,
											"The Event(s) " + eventTitle
													+ " should not contain in case of User 1 in Detail Page: "
													+ contactRecordName + " , Reason: "
													+ notificationNegativeResultInContactRecordPage);
								}

								log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
										+ accountRecordName + " Record---------", YesNo.No);
								if (lp.clickOnTab(projectName, tabObj1)) {

									log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

									if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
											accountRecordType, accountRecordName, 30)) {
										log(LogStatus.INFO, accountRecordName + " record of record type "
												+ accountRecordType + " has been open", YesNo.No);
										ThreadSleep(4000);
										if (BP.clicktabOnPage("Acuity")) {
											log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

											List<String> notificationNegativeResult = BP
													.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
											if (notificationNegativeResult.isEmpty()) {
												log(LogStatus.INFO,
														"Verified Notification not contains for Event(s): " + eventTitle
																+ " in case of User 1 in Detail Page"
																+ accountRecordName,
														YesNo.No);

											} else {
												log(LogStatus.ERROR, "The Event(s) " + eventTitle
														+ " should not contain in case of User 1 in Detail Page: "
														+ accountRecordName + " , Reason: "
														+ notificationNegativeResult, YesNo.No);
												sa.assertTrue(false, "The Event(s) " + eventTitle
														+ " should not contain in case of User 1 in Detail Page: "
														+ accountRecordName + " , Reason: "
														+ notificationNegativeResult);
											}

										} else {
											log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
											sa.assertTrue(false, "Not able to click on Acuity Tab");
										}

									} else {
										log(LogStatus.ERROR, "Not able to open " + accountRecordName
												+ " record of record type " + accountRecordType, YesNo.No);
										sa.assertTrue(false, "Not able to open " + accountRecordName
												+ " record of record type " + accountRecordType);
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
									sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
								}

								log(LogStatus.INFO,
										"---------Now Going to Verify Event: " + eventTitle
												+ " on Home Page in case of User 1: " + crmUser1EmailID + " ---------",
										YesNo.No);

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
									List<String> notificationHomeNegativeResult = home
											.verifyNotificationOptionsNotContains(eventTitle);
									if (notificationHomeNegativeResult.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle
												+ " on Home Page not contains", YesNo.No);

									} else {
										log(LogStatus.ERROR, "The Event(s) " + eventTitle
												+ "+ on Home Page should not contain in case of User 1, Reason: "
												+ notificationHomeNegativeResult, YesNo.No);
										sa.assertTrue(false, "The Event(s) " + eventTitle
												+ "+ on Home Page should not contain in case of User 1, Reason: "
												+ notificationHomeNegativeResult);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

								lp.CRMlogout();
								ThreadSleep(5000);

								lp.CRMLogin(crmUser2EmailID, adminPassword);

								log(LogStatus.INFO,
										"---------Now Going to Verify Event: " + eventTitle
												+ " on Home Page in case of User 2: " + crmUser2EmailID + " ---------",
										YesNo.No);

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
									List<String> notificationHomeNegativeResult = home
											.verifyNotificationOptionsNotContains(eventTitle);
									if (notificationHomeNegativeResult.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle
												+ " on Home Page not contains n case of User 1", YesNo.No);

									} else {
										log(LogStatus.ERROR, "The Event(s) " + eventTitle
												+ "+ on Home Page should not contain in case of User 2, Reason: "
												+ notificationHomeNegativeResult, YesNo.No);
										sa.assertTrue(false, "The Event(s) " + eventTitle
												+ "+ on Home Page should not contain in case of User 2, Reason: "
												+ notificationHomeNegativeResult);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

							} else {
								log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle,
										YesNo.No);
								sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);
							}

						} else {
							log(LogStatus.ERROR, "Not Able to Click on Add Noted Button of Event: " + eventTitle,
									YesNo.No);
							sa.assertTrue(false, "Not Able to Click on Add Noted Button of Event: " + eventTitle);
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Notification Icon", YesNo.No);
						sa.assertTrue(false, "Not Able to Click on Notification Icon");
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void rgAcuityMNNRTc018_CreateTheEventClickOnTheEventHeaderFromHomePageNotificationAndAddTheNotesAndVerifyTheNotificationPopUp(
			String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String user1FullName;
		if (!crmUser1FirstName.equals("") && !crmUser1LastName.equals("")) {
			user1FullName = crmUser1FirstName + " " + crmUser1LastName;
		} else {
			user1FullName = crmUser1LastName;

		}
		String eventTitle = "Marketing Webinar 2";
		String eventAttendees = "Dealroom1.3+James@gmail.com,Dealroom1.3+Jhon@gmail.com" + "," + crmUser2EmailID + ","
				+ crmUser3EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);

		String startTime = "6:00 PM";
		String endTime = "6:30 PM";
		String descriptionBox = "Revenue Grid Event";

		String contactRecordName = "James";
		String relateToVerifyOnNotesPopUp = null;
		String[][] event1BasicSectionVerificationInNotesPopup = { { "Subject", eventTitle }, { "Notes", "" } };
		String[][] event1AdvancedSectionVerificationInNotesPopup = { { "Location", "" },
				{ "Assigned To ID", user1FullName }, { "Start Date Time<break>Date", startDate },
				{ "End Date Time<break>Date", endDate } };

		String updatedNotesOfEvent = "Deal @sumo logic and @Max fund should be in loop";
		String[][] event1UpdateBasicSectionOnHomepageNotePopup = { { "Notes", updatedNotesOfEvent } };
		String[][] event1UpdateAdvancedSectionOnHomepageNotePopup = null;
		String[] eventupdatedSuggestedTagsOnHomepageNotePopup = null;
		Integer countIconBeforeUpdate = 0;
		Integer countIconAfterUpdate = 0;

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);
			CommonLib.refresh(driver);
			log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						String parentID = BP.notificationEventNavigateToDetailPageOrNot(eventTitle);
						if (parentID != null) {
							log(LogStatus.INFO, "-------Able to navigate to Event Detail Page after click on event: "
									+ eventTitle + " from Record Detail Page Notification-------", YesNo.No);
							driver.close();
							CommonLib.ThreadSleep(2000);
							driver.switchTo().window(parentID);
						} else {
							log(LogStatus.ERROR,
									"-------Not able to navigate to Event Detail Page after click on event: "
											+ eventTitle + " from Record Detail Page Notification-------",
									YesNo.No);
							sa.assertTrue(false,
									"-------Not able to navigate to Event Detail Page after click on event: "
											+ eventTitle + " from Record Detail Page Notification-------");
						}

						try {

							CommonLib.refresh(driver);
							countIconBeforeUpdate = Integer.valueOf(
									CommonLib.getText(driver, BP.countOfAcuityNotificationInRecordDetailPage(20),
											"Count Notification Icon", action.BOOLEAN));
						} catch (Exception e) {
							log(LogStatus.ERROR,
									"Exception Occured, Can not Convert " + CommonLib.getText(driver,
											BP.countOfAcuityNotificationInRecordDetailPage(20),
											"Count Notification Icon", action.BOOLEAN) + " into Int",
									YesNo.No);
							sa.assertTrue(false,
									"Exception Occured, Can not Convert " + CommonLib.getText(driver,
											BP.countOfAcuityNotificationInRecordDetailPage(20),
											"Count Notification Icon", action.BOOLEAN) + " into Int");
						}
						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle, YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + ", Reason: "
									+ notificationNegativeResult, YesNo.No);
							sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + ", Reason: "
									+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

				CommonLib.ThreadSleep(5000);
				List<String> notificationHomeNegativeResult2 = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult2.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

					if (lp.clickOnTab(projectName, TabName.HomeTab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);

						if (CommonLib.click(driver, home.eventLinkInHomePageNotification(eventTitle, 20),
								"Link of Event: " + eventTitle, action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Link of Event: " + eventTitle, YesNo.No);

							String parentId2 = CommonLib.switchToWindowOpenNextToParentWindow(driver);

							if (parentId2 != null) {
								if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Edit Button of Event: " + eventTitle, YesNo.No);

									String url2 = getURL(driver, 10);
									ThreadSleep(10000);
									ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
													event1BasicSectionVerificationInNotesPopup,
													event1AdvancedSectionVerificationInNotesPopup, null);
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

									if (BP.updateActivityTimelineRecord(projectName,
											event1UpdateBasicSectionOnHomepageNotePopup,
											event1UpdateAdvancedSectionOnHomepageNotePopup, null,
											eventupdatedSuggestedTagsOnHomepageNotePopup, null)) {
										log(LogStatus.PASS,
												"Activity timeline record has been Updated for: " + eventTitle,
												YesNo.No);
										driver.close();
										driver.switchTo().window(parentId2);

										log(LogStatus.INFO,
												"---------Now Going to Verify Event: " + eventTitle + "---------",
												YesNo.No);

										log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in "
												+ contactRecordName + " Record---------", YesNo.No);
										if (lp.clickOnTab(projectName, tabObj2)) {

											log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

											if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
													contactRecordName, 30)) {
												log(LogStatus.INFO, contactRecordName + " record has been open",
														YesNo.No);
												ThreadSleep(4000);
												if (BP.clicktabOnPage("Acuity")) {
													log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

													try {

														countIconAfterUpdate = Integer.valueOf(CommonLib.getText(driver,
																BP.countOfAcuityNotificationInRecordDetailPage(20),
																"Count Notification Icon", action.BOOLEAN));
														if (countIconBeforeUpdate.equals(countIconAfterUpdate + 1)) {
															log(LogStatus.INFO,
																	"Verified Notification Icon Count before update event is greater by 1, before update event count: "
																			+ countIconBeforeUpdate
																			+ " & after update event count: "
																			+ countIconAfterUpdate,
																	YesNo.No);
														} else {
															log(LogStatus.ERROR,
																	"Not Verified Notification Icon Count before update event is greater by 1, before update event count: "
																			+ countIconBeforeUpdate
																			+ " & after update event count: "
																			+ countIconAfterUpdate,
																	YesNo.No);
															sa.assertTrue(false,
																	"Not Verified Notification Icon Count before update event is greater by 1, before update event count: "
																			+ countIconBeforeUpdate
																			+ " & after update event count: "
																			+ countIconAfterUpdate);
														}

														if (BP.notificationIconCountAndActualCountMatchedOrNot()) {
															log(LogStatus.INFO,
																	"Verified Count of Icon and Actual matched",
																	YesNo.No);
														} else {
															log(LogStatus.ERROR, "Count of Icon and Actual not matched",
																	YesNo.No);
															sa.assertTrue(false,
																	"Count of Icon and Actual not matched");
														}

													} catch (Exception e) {
														log(LogStatus.ERROR, "Exception Occured, Can not Convert "
																+ CommonLib.getText(driver,
																		BP.countOfAcuityNotificationInRecordDetailPage(
																				20),
																		"Count Notification Icon", action.BOOLEAN)
																+ " into Int", YesNo.No);
														sa.assertTrue(false, "Exception Occured, Can not Convert "
																+ CommonLib.getText(driver,
																		BP.countOfAcuityNotificationInRecordDetailPage(
																				20),
																		"Count Notification Icon", action.BOOLEAN)
																+ " into Int");
													}

													List<String> notificationNegativeResult = BP
															.verifyNotificationOptionsNotContainsInRecordDetailPage(
																	eventTitle);
													if (notificationNegativeResult.isEmpty()) {
														log(LogStatus.INFO,
																"Verified Notification not contains for Event(s): "
																		+ eventTitle
																		+ " in case of User 1 in Detail Page"
																		+ contactRecordName,
																YesNo.No);

													} else {
														log(LogStatus.ERROR,
																"The Event(s) " + eventTitle
																		+ " should not contain in Detail Page: "
																		+ contactRecordName + " , Reason: "
																		+ notificationNegativeResult,
																YesNo.No);
														sa.assertTrue(false,
																"The Event(s) " + eventTitle
																		+ " should not contain in Detail Page: "
																		+ contactRecordName + " , Reason: "
																		+ notificationNegativeResult);
													}
												} else {
													log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
													sa.assertTrue(false, "Not able to click on Acuity Tab");
												}

											} else {
												log(LogStatus.ERROR,
														"Not able to open " + contactRecordName + " record", YesNo.No);
												sa.assertTrue(false,
														"Not able to open " + contactRecordName + " record");
											}
										} else {
											log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
											sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
										}

										if (lp.clickOnTab(projectName, TabName.HomeTab)) {
											log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
											List<String> notificationHomeNegativeResult = home
													.verifyNotificationOptionsNotContains(eventTitle);
											if (notificationHomeNegativeResult.isEmpty()) {
												log(LogStatus.INFO,
														"Verified Notification for Event(s): " + eventTitle
																+ " on Home Page not contains in case of User 1 i.e.: "
																+ crmUser1EmailID,
														YesNo.No);

											} else {
												log(LogStatus.ERROR, "The Event(s) " + eventTitle
														+ "+ on Home Page should not contain in case of User 1 i.e.: "
														+ crmUser1EmailID + ", Reason: "
														+ notificationHomeNegativeResult, YesNo.No);
												sa.assertTrue(false, "The Event(s) " + eventTitle
														+ "+ on Home Page should not contain in case of User 1 i.e.: "
														+ crmUser1EmailID + ", Reason: "
														+ notificationHomeNegativeResult);
											}
										} else {
											sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
											log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab,
													YesNo.Yes);
										}

										lp.CRMlogout();
										ThreadSleep(5000);
										lp.CRMLogin(crmUser2EmailID, adminPassword);

										log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle
												+ " in Interaction Section---------", YesNo.No);
										if (lp.clickOnTab(projectName, TabName.HomeTab)) {
											log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
											List<String> notificationHomeNegativeResult = home
													.verifyNotificationOptionsNotContains(eventTitle);
											if (notificationHomeNegativeResult.isEmpty()) {
												log(LogStatus.INFO,
														"Verified Notification for Event(s): " + eventTitle
																+ " on Home Page not contains in case of User 2 i.e.: "
																+ crmUser2EmailID,
														YesNo.No);

											} else {
												log(LogStatus.ERROR, "The Event(s) " + eventTitle
														+ "+ on Home Page should not contain in case of User 2 i.e.: "
														+ crmUser2EmailID + ", Reason: "
														+ notificationHomeNegativeResult, YesNo.No);
												sa.assertTrue(false, "The Event(s) " + eventTitle
														+ "+ on Home Page should not contain in case of User 2 i.e.: "
														+ crmUser2EmailID + ", Reason: "
														+ notificationHomeNegativeResult);
											}
										} else {
											sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
											log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab,
													YesNo.Yes);
										}

										lp.CRMlogout();
										ThreadSleep(5000);
										lp.CRMLogin(crmUser3EmailID, adminPassword);

										log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle
												+ " in Interaction Section---------", YesNo.No);
										if (lp.clickOnTab(projectName, TabName.HomeTab)) {
											log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
											List<String> notificationHomeNegativeResult = home
													.verifyNotificationOptionsNotContains(eventTitle);
											if (notificationHomeNegativeResult.isEmpty()) {
												log(LogStatus.INFO,
														"Verified Notification for Event(s): " + eventTitle
																+ " on Home Page not contains in case of User 1 i.e.: "
																+ crmUser3EmailID,
														YesNo.No);

											} else {
												log(LogStatus.ERROR, "The Event(s) " + eventTitle
														+ "+ on Home Page should not contain in case of User 3 i.e.: "
														+ crmUser3EmailID + ", Reason: "
														+ notificationHomeNegativeResult, YesNo.No);
												sa.assertTrue(false, "The Event(s) " + eventTitle
														+ "+ on Home Page should not contain in case of User 3 i.e.: "
														+ crmUser3EmailID + ", Reason: "
														+ notificationHomeNegativeResult);
											}
										} else {
											sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
											log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab,
													YesNo.Yes);
										}

									} else {
										log(LogStatus.FAIL,
												"Activity timeline record has not Updated for: " + eventTitle,
												YesNo.No);
										sa.assertTrue(false,
												"Activity timeline record has not Updated for: " + eventTitle);

										driver.close();
										driver.switchTo().window(parentId2);
									}

								} else {
									log(LogStatus.ERROR,
											"Not Able Click on Edit button for Record Page of Event: " + eventTitle,
											YesNo.Yes);
									sa.assertTrue(false,
											"Not Able Click on Edit button for Record Page of Event: " + eventTitle);
									driver.close();
									driver.switchTo().window(parentId2);
								}

							}

							else {
								log(LogStatus.ERROR, "No New window open after click on event " + eventTitle
										+ " from Home page notification link, So not able to do further process",
										YesNo.No);
								sa.assertTrue(false, "No New window open after click on event " + eventTitle
										+ " from Home page notification link, So not able to do further process");
							}

						} else {
							log(LogStatus.ERROR, "Not Able to Click on Link of Event: " + eventTitle, YesNo.No);
							sa.assertTrue(false, "Not Able to Click on Link of Event: " + eventTitle);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
						log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult2);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void rgAcuityMNNRTc019_CreateTheEventByTaggingAttendeesMakeOneOfTheAttendeeInactiveAndVerifyTheNotificationPopUpOnceTheUserIsActive(
			String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);

		String eventTitle = "Mulesoft Connect";
		String eventAttendees = "dealroom1.3+lomez@gmail.com,Dealroom1.3+James@gmail.com" + "," + crmUser2EmailID + ","
				+ crmUser3EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);

		String startTime = "6:00 PM";
		String endTime = "6:30 PM";
		String descriptionBox = "Revenue Grid Event";
		String recordName = "Lomez";

		String user3firstName = crmUser3FirstName;
		String User3LastName = crmUser3LastName;

		String user3EmailId = crmUser3EmailID;
		// String[] labelAndValueSeprateByBreak = { "Attendees" + "<break>" +
		// crmUser3FirstName + " " + crmUser3LastName };

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			log(LogStatus.INFO, "---------Now Going to Verify Notification of Event: " + eventTitle
					+ " in Record Page: " + recordName + " in case of User 1 , i.e.: " + crmUser1EmailID + "---------",
					YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification contains for Event(s): " + eventTitle
									+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page" + recordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + recordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + recordName + " , Reason: "
											+ notificationNegativeResult);
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
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			lp.CRMlogout();
			ThreadSleep(5000);
			lp.CRMLogin(crmUser2EmailID, adminPassword);

			log(LogStatus.INFO, "---------Now Going to Verify Notification of Event: " + eventTitle
					+ " in Record Page: " + recordName + " in case of User 2 , i.e.: " + crmUser2EmailID + "---------",
					YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification contains for Event(s): " + eventTitle
									+ " in case of User 2 i.e.: " + crmUser2EmailID + " in Detail Page" + recordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitle + " in case of User 2 i.e.: "
											+ crmUser2EmailID + " in Detail Page: " + recordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitle + " in case of User 2 i.e.: "
											+ crmUser2EmailID + " in Detail Page: " + recordName + " , Reason: "
											+ notificationNegativeResult);
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
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			lp.CRMlogout();
			ThreadSleep(5000);
			lp.CRMLogin(crmUser3EmailID, adminPassword);

			log(LogStatus.INFO, "---------Now Going to Verify Notification of Event: " + eventTitle
					+ " in Record Page: " + recordName + " in case of User 3 , i.e.: " + crmUser3EmailID + "---------",
					YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification contains for Event(s): " + eventTitle
									+ " in case of User 3 i.e.: " + crmUser3EmailID + " in Detail Page" + recordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitle + " in case of User 3 i.e.: "
											+ crmUser3EmailID + " in Detail Page: " + recordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitle + " in case of User 3 i.e.: "
											+ crmUser3EmailID + " in Detail Page: " + recordName + " , Reason: "
											+ notificationNegativeResult);
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
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			lp.CRMlogout();
			ThreadSleep(5000);

			lp.CRMLogin(superAdminUserName, adminPassword, appName);
			boolean flag = false;
			if (home.clickOnSetUpLink()) {
				flag = true;
				String parentWindow = switchOnWindow(driver);
				if (parentWindow == null) {
					sa.assertTrue(false,
							"No new window is open after click on setup link in lighting mode so cannot Update CRM User3");
					log(LogStatus.SKIP,
							"No new window is open after click on setup link in lighting mode so cannot Update CRM User3",
							YesNo.Yes);
					exit("No new window is open after click on setup link in lighting mode so cannot Update CRM User3");
				}
				if (setup.userActiveOrInActive(user3firstName, User3LastName, user3EmailId,
						PermissionType.removePermission.toString())) {
					log(LogStatus.INFO, "CRM User has been updated Successfully and inactive: " + user3firstName + " "
							+ User3LastName, YesNo.No);

					flag = true;

				} else {
					log(LogStatus.ERROR, "CRM User is not updated and inactive " + user3firstName + " " + User3LastName,
							YesNo.No);
					sa.assertTrue(false,
							"CRM User is not updated and inactive " + user3firstName + " " + User3LastName);
				}
				driver.close();
				driver.switchTo().window(parentWindow);

			}

			if (flag == true) {

				lp.CRMlogout();
				ThreadSleep(5000);
				lp.CRMLogin(crmUser2EmailID, adminPassword);

				log(LogStatus.INFO,
						"---------Now Going to Verify Attendee User 3 in case in Event detail page in case of User 2: "
								+ crmUser2EmailID + "---------",
						YesNo.No);
				if (lp.clickOnTab(projectName, tabObj2)) {

					log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

					if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
						log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
						ThreadSleep(4000);
						if (BP.clicktabOnPage("Acuity")) {
							log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
							if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
								log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle,
										YesNo.No);

								CommonLib.ThreadSleep(8000);
//								List<String> eventDetailPageNegativeResult = BP
//										.fieldValueVerification(labelAndValueSeprateByBreak);
//
//								if (eventDetailPageNegativeResult.isEmpty()) {
//									log(LogStatus.PASS,
//											"------" + eventTitle
//													+ " labels and their values in Detail page has been verified------",
//											YesNo.No);
//									driver.close();
//									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
//
//								} else {
//									log(LogStatus.ERROR, "------" + eventTitle
//											+ " labels and their values in Detail page has not been verified, Reason: "
//											+ eventDetailPageNegativeResult + "------", YesNo.No);
//									sa.assertTrue(false, "------" + eventTitle
//											+ " labels and their values in Detail page has not been verified, Reason: "
//											+ eventDetailPageNegativeResult + "------");
//									driver.close();
//									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
//
//								}

								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							} else {
								log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
										YesNo.Yes);
								sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);
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
					log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
					sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
				}

				lp.CRMlogout();
				ThreadSleep(5000);

				lp.CRMLogin(superAdminUserName, adminPassword, appName);
				boolean flag2 = false;
				if (home.clickOnSetUpLink()) {
					flag2 = true;
					String parentWindow = switchOnWindow(driver);
					if (parentWindow == null) {
						sa.assertTrue(false,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User1");
						log(LogStatus.SKIP,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User1",
								YesNo.Yes);
						exit("No new window is open after click on setup link in lighting mode so cannot create CRM User1");
					}
					if (setup.userActiveOrInActive(user3firstName, User3LastName, user3EmailId,
							PermissionType.givePermission.toString())) {
						log(LogStatus.INFO, "CRM User has been updated Successfully and active: " + user3firstName + " "
								+ User3LastName, YesNo.No);

						flag2 = true;

					} else {
						log(LogStatus.ERROR,
								"CRM User is not updated and active" + user3firstName + " " + User3LastName, YesNo.No);
						sa.assertTrue(false,
								"CRM User is not updated and active " + user3firstName + " " + User3LastName);
					}
					driver.close();
					driver.switchTo().window(parentWindow);

				}
				if (flag2) {

					lp.CRMlogout();
					ThreadSleep(5000);
					lp.CRMLogin(crmUser3EmailID, adminPassword);

					log(LogStatus.INFO,
							"---------Now Going to Verify Notification of Event: " + eventTitle + " in Record Page: "
									+ recordName + " in case of User 3 , i.e.: " + crmUser3EmailID + "---------",
							YesNo.No);
					if (lp.clickOnTab(projectName, tabObj2)) {

						log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

						if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
							log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
							ThreadSleep(4000);
							if (BP.clicktabOnPage("Acuity")) {
								log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

								List<String> notificationNegativeResult = BP
										.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
								if (notificationNegativeResult.isEmpty()) {
									log(LogStatus.INFO,
											"Verified Notification contains for Event(s): " + eventTitle
													+ " in case of User 3 i.e.: " + crmUser3EmailID + " in Detail Page"
													+ recordName,
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Not Verified Event(s) " + eventTitle + " in case of User 3 i.e.: "
													+ crmUser3EmailID + " in Detail Page: " + recordName + " , Reason: "
													+ notificationNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"Not Verified Event(s) " + eventTitle + " in case of User 3 i.e.: "
													+ crmUser3EmailID + " in Detail Page: " + recordName + " , Reason: "
													+ notificationNegativeResult);
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
						log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
						sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
					}

				} else {
					log(LogStatus.ERROR, "Not able to active the User 3, i.e.: " + crmUser3EmailID, YesNo.No);
					sa.assertTrue(false, "Not able to active the User 3, i.e.: " + crmUser3EmailID);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Inactive the User 3, i.e.: " + crmUser3EmailID, YesNo.No);
				sa.assertTrue(false, "Not able to Inactive the User 3, i.e.: " + crmUser3EmailID);
			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc020_Create4EventsWithSameStartAndEndDateAndTimeAndVerifyTheNotificationPopUp(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitles[] = "Relive salesforce Live 1<break>Relive salesforce Live 2<break>Relive salesforce Live 3<break>Relive salesforce Live 4"
				.split("<break>", -1);
		String eventAttendees = "dealroom1.3+con1@gmail.com,dealroom1.3+con2@gmail.com" + "," + crmUser1EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);

		String startTime = "10:00 PM";
		String endTime = "10:00 PM";
		String descriptionBox = "";

		String updatedRelatedTo = "Acc 11";
		String[][] task1UpdateBasicSection = { { "Related_To", updatedRelatedTo } };
		String[][] task1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = null;
		String contactRecordName = "Con 1";

		String accountRecordName = "Acc 11";
		String accountRecordType = "Company";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Create Event(s): " + eventTitles + " through Outlook---------",
				YesNo.No);

		Integer loopCount = 0;
		Integer status = 0;
		for (String eventTitle : eventTitles) {

			if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
					eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
				log(LogStatus.INFO, "-----Event Created Msg is showing, So Event of Title: " + eventTitle
						+ " has been created-----", YesNo.No);

				status++;

			} else {
				log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been created-----", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been created-----");
			}

			loopCount++;
		}

		for (String eventTitle : eventTitles) {

			CommonLib.refresh(driver);
			if (CommonLib.click(driver, home.addNoteButtonOfEventInHomePageNotification(eventTitle, 20),
					"Add Note Button of Event: " + eventTitle, action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Add Note Button of Event: " + eventTitle, YesNo.No);
				if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, task1UpdateAdvancedSection,
						null, updatedSuggestedTags, null)) {
					log(LogStatus.PASS, "Activity timeline record has been Updated for: " + eventTitle, YesNo.No);

				} else {
					log(LogStatus.FAIL, "Activity timeline record has not Updated for: " + eventTitle, YesNo.No);
					sa.assertTrue(false, "Activity timeline record has not Updated for: " + eventTitle);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Add Noted Button of Event: " + eventTitle, YesNo.No);
				sa.assertTrue(false, "Not Able to Click on Add Noted Button of Event: " + eventTitle);
			}
		}

		if (status.equals(loopCount)) {

			CommonLib.refresh(driver);

			CommonLib.ThreadSleep(5000);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitles);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitles + " on Home Page",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitles + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitles + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event(s): " + eventTitles + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitles);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"Verified Notification contains for Event(s): " + eventTitles
											+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page"
											+ contactRecordName,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s) " + eventTitles + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s) " + eventTitles + " in case of User 1 i.e.: "
											+ crmUser1EmailID + " in Detail Page: " + contactRecordName + " , Reason: "
											+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event(s): " + eventTitles + " in " + accountRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType,
						accountRecordName, 30)) {
					log(LogStatus.INFO,
							accountRecordName + " record of record type " + accountRecordType + " has been open",
							YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsOnRecordDetailsPage(eventTitles);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitles, YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitles + ", Reason: "
									+ notificationNegativeResult, YesNo.No);
							sa.assertTrue(false, "Not Verified the Event(s) " + eventTitles + ", Reason: "
									+ notificationNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR,
							"Not able to open " + accountRecordName + " record of record type " + accountRecordType,
							YesNo.No);
					sa.assertTrue(false,
							"Not able to open " + accountRecordName + " record of record type " + accountRecordType);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
			}

		}

		else {

			sa.assertTrue(false, "Some of the Given Events not gets created: " + eventTitles
					+ " , So not able to further continue to check on Notification Panes");
			log(LogStatus.SKIP, "Some of the Given Events not gets created: " + eventTitles
					+ " , So not able to further continue to check on Notification Panes", YesNo.Yes);

		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void rgAcuityMNNRTc021_VerifyTheNotificationPopUpWhenAttendeeNameIsModifiedAfterCreatingTheEvent(
			String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);

		String eventTitle = "This event will have your Business circles talking +4";
		String eventAttendees = "Dealroom1.3+Lomez@gmail.com,Dealroom1.3+Max@gmail.com,Dealroom1.3+James@gmail.com"
				+ "," + crmUser2EmailID + "," + crmUser3EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -2);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", -1);

		String startTime = "6:00 PM";
		String endTime = "6:30 PM";
		String descriptionBox = "Revenue Grid Event";
		String contactRecordName = "Max";

		String user2firstName = crmUser2FirstName;
		String User2LastName = crmUser2LastName + "updated";

		String user2FullName;
		if (!crmUser2FirstName.equals("") && !crmUser2LastName.equals("")) {
			user2FullName = crmUser2FirstName + " " + crmUser2LastName;
		} else {
			user2FullName = crmUser2LastName;

		}

		user2FullName = user2FullName + "updated";
		String user2EmailId = crmUser2EmailID;

		String RelatedToVerifyInNotes = user2FullName;
		String[][] event1BasicSectionVerificationInNotesPopup = { { "Related_To", RelatedToVerifyInNotes } };
		String[][] event1AdvancedSectionVerificationInNotesPopup = null;

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			lp.CRMlogout();
			ThreadSleep(5000);
			lp.CRMLogin(crmUser2EmailID, adminPassword);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult, YesNo.No);
					sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
							+ notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			lp.CRMlogout();
			ThreadSleep(5000);

			lp.CRMLogin(superAdminUserName, adminPassword, appName);
			boolean flag = false;
			if (home.clickOnSetUpLink()) {
				flag = true;
				String parentWindow = switchOnWindow(driver);
				if (parentWindow == null) {
					sa.assertTrue(false,
							"No new window is open after click on setup link in lighting mode so cannot Update CRM User2");
					log(LogStatus.SKIP,
							"No new window is open after click on setup link in lighting mode so cannot Update CRM User2",
							YesNo.Yes);
					exit("No new window is open after click on setup link in lighting mode so cannot Update CRM User2");
				}
				if (setup.editPEUserAndUpdateTheName(user2firstName, User2LastName, user2EmailId)) {
					log(LogStatus.INFO,
							"CRM User Name has been updated Successfully to: " + user2firstName + " " + User2LastName,
							YesNo.No);
					ExcelUtils.writeData(testCasesFilePath, user2firstName, "Users", excelLabel.Variable_Name, "User2",
							excelLabel.User_First_Name);
					ExcelUtils.writeData(testCasesFilePath, User2LastName, "Users", excelLabel.Variable_Name, "User2",
							excelLabel.User_Last_Name);

					flag = true;

				} else {
					log(LogStatus.ERROR, "CRM User Name has not been updated Successfully to: " + user2firstName + " "
							+ User2LastName, YesNo.No);
					sa.assertTrue(false, "CRM User Name has not been updated Successfully to: " + user2firstName + " "
							+ User2LastName);
				}
				driver.close();
				driver.switchTo().window(parentWindow);

			}

			if (flag == true) {

				lp.CRMlogout();
				ThreadSleep(5000);
				lp.CRMLogin(crmUser2EmailID, adminPassword);

				if (lp.clickOnTab(projectName, TabName.HomeTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
					List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
					if (notificationHomeNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
								+ notificationHomeNegativeResult, YesNo.No);
						sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
								+ notificationHomeNegativeResult);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
				}

				lp.CRMlogout();
				ThreadSleep(5000);
				lp.CRMLogin(crmUser1EmailID, "navatar123");

				if (lp.clickOnTab(projectName, TabName.HomeTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
					List<String> notificationHomeNegativeResult = home.verifyNotificationOptions(eventTitle);
					if (notificationHomeNegativeResult.isEmpty()) {
						log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitle + " on Home Page",
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
								+ notificationHomeNegativeResult, YesNo.No);
						sa.assertTrue(false, "Not Verified the Event(s) " + eventTitle + "+ on Home Page, Reason: "
								+ notificationHomeNegativeResult);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
					log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
				}

				log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
						+ " Record---------", YesNo.No);
				if (lp.clickOnTab(projectName, tabObj2)) {

					log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

					if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName,
							30)) {
						log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
						ThreadSleep(4000);
						if (BP.clicktabOnPage("Acuity")) {
							log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

							List<String> notificationNegativeResult = BP
									.verifyNotificationOptionsOnRecordDetailsPage(eventTitle);
							if (notificationNegativeResult.isEmpty()) {
								log(LogStatus.INFO,
										"Verified Notification contains for Event(s): " + eventTitle
												+ " in case of User 1 i.e.: " + crmUser1EmailID + " in Detail Page"
												+ contactRecordName,
										YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
												+ crmUser1EmailID + " in Detail Page: " + contactRecordName
												+ " , Reason: " + notificationNegativeResult,
										YesNo.No);
								sa.assertTrue(false,
										"Not Verified Event(s) " + eventTitle + " in case of User 1 i.e.: "
												+ crmUser1EmailID + " in Detail Page: " + contactRecordName
												+ " , Reason: " + notificationNegativeResult);
							}

							if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
								log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle,
										YesNo.No);
								CommonLib.ThreadSleep(8000);

								if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);

									String url2 = getURL(driver, 10);
									ThreadSleep(2000);
									ArrayList<String> NotesPopUpPrefilledNegativeResultUpdated = BP
											.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url2,
													event1BasicSectionVerificationInNotesPopup,
													event1AdvancedSectionVerificationInNotesPopup, null);
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
									log(LogStatus.ERROR,
											"Not Able Click on Edit button for Record Page of Event: " + eventTitle,
											YesNo.Yes);
									sa.assertTrue(false,
											"Not Able Click on Edit button for Record Page of Event: " + eventTitle);

								}

								driver.close();
								CommonLib.ThreadSleep(3000);

								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							} else {
								log(LogStatus.ERROR, "Event Record Detail Page  has not opened for Record: "
										+ eventTitle + " in Detail Page: " + contactRecordName, YesNo.Yes);
								sa.assertTrue(false, "Event Record Detail Page  has not opened for Record: "
										+ eventTitle + " in Detail Page: " + contactRecordName);

							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
							sa.assertTrue(false, "Not able to click on Acuity Tab");
						}

					} else {
						log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
						sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
					sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
				}

			} else {
				log(LogStatus.ERROR,
						"CRM User Name has not been updated Successfully to: " + user2firstName + " " + User2LastName,
						YesNo.No);
				sa.assertTrue(false,
						"CRM User Name has not been updated Successfully to: " + user2firstName + " " + User2LastName);
			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc022_EditEventAndAddNotesWithAlreadyTaggedContactFirmThenSuggestedPopUpNotCome(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitleExisting = "Relive salesforce Live 1";

		String updatedNotes = "Acc 11, Con 1, con 2";
		String[][] event1UpdateBasicSection = { { "Notes", updatedNotes } };
		String[][] event1UpdateAdvancedSection = null;
		String[] updatedSuggestedTags = "SuggestedPopUpShouldNotThere".split("<break>", -1);

		String accountRecordName = "Acc 11";
		String accountRecordType = "Company";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, accountRecordType,
					accountRecordName, 30)) {
				log(LogStatus.INFO,
						accountRecordName + " record of record type " + accountRecordType + " has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitleExisting)) {
						log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitleExisting,
								YesNo.No);
						if (click(driver, taskBP.editButtonInEventDetailPage(20), "Edit Button",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);

							if (BP.updateActivityTimelineRecord(projectName, event1UpdateBasicSection,
									event1UpdateAdvancedSection, null, updatedSuggestedTags, null)) {
								log(LogStatus.PASS, "Activity timeline record has been Updated for: "
										+ eventTitleExisting + " , also verified Suggested PopUp not Open", YesNo.No);

								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								CommonLib.refresh(driver);

								ArrayList<String> interactionResult = BP.verifyRecordOnInteractionCard(null, null,
										eventTitleExisting, null, true, false, null, null);
								if (interactionResult.isEmpty()) {
									log(LogStatus.PASS, "------" + eventTitleExisting
											+ " record has been verified on intraction------", YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"------" + eventTitleExisting
													+ " record is not verified on intraction, Reason: "
													+ interactionResult + "------",
											YesNo.No);
									sa.assertTrue(false,
											"------" + eventTitleExisting
													+ " record is not verified on intraction, Reason: "
													+ interactionResult + "------");
								}

								List<String> notificationNegativeResult = BP
										.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitleExisting);
								if (notificationNegativeResult.isEmpty()) {
									log(LogStatus.INFO,
											"Verified Notification not contains for Event(s): " + eventTitleExisting
													+ " in case of User 1 in Detail Page" + accountRecordName,
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"The Event(s) " + eventTitleExisting
													+ " should not contain in case of User 1 in Detail Page: "
													+ accountRecordName + " , Reason: " + notificationNegativeResult,
											YesNo.No);
									sa.assertTrue(false,
											"The Event(s) " + eventTitleExisting
													+ " should not contain in case of User 1 in Detail Page: "
													+ accountRecordName + " , Reason: " + notificationNegativeResult);
								}

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
									List<String> notificationHomeNegativeResult = home
											.verifyNotificationOptionsNotContains(eventTitleExisting);
									if (notificationHomeNegativeResult.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleExisting
												+ " on Home Page not contains", YesNo.No);

									} else {
										log(LogStatus.ERROR, "The Event(s) " + eventTitleExisting
												+ "+ on Home Page should not contain in case of User 1, Reason: "
												+ notificationHomeNegativeResult, YesNo.No);
										sa.assertTrue(false, "The Event(s) " + eventTitleExisting
												+ "+ on Home Page should not contain in case of User 1, Reason: "
												+ notificationHomeNegativeResult);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

								lp.CRMlogout();
								ThreadSleep(5000);
								lp.CRMLogin(crmUser2EmailID, adminPassword);

								if (lp.clickOnTab(projectName, TabName.HomeTab)) {
									log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
									List<String> notificationHomeNegativeResult = home
											.verifyNotificationOptionsNotContains(eventTitleExisting);
									if (notificationHomeNegativeResult.isEmpty()) {
										log(LogStatus.INFO, "Verified Notification for Event(s): " + eventTitleExisting
												+ " on Home Page not contains in case of User 2", YesNo.No);

									} else {
										log(LogStatus.ERROR, "The Event(s) " + eventTitleExisting
												+ "+ on Home Page should not contain in case of User 2, Reason: "
												+ notificationHomeNegativeResult, YesNo.No);
										sa.assertTrue(false, "The Event(s) " + eventTitleExisting
												+ "+ on Home Page should not contain in case of User 2, Reason: "
												+ notificationHomeNegativeResult);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
									log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
								}

							} else {
								log(LogStatus.FAIL,
										"Activity timeline record has not Updated for: " + eventTitleExisting,
										YesNo.No);
								sa.assertTrue(false,
										"Activity timeline record has not Updated for: " + eventTitleExisting);
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								CommonLib.refresh(driver);
							}

						} else {
							log(LogStatus.ERROR,
									"Not Able Click on Edit button for Record Page of Event: " + eventTitleExisting,
									YesNo.Yes);
							sa.assertTrue(false,
									"Not Able Click on Edit button for Record Page of Event: " + eventTitleExisting);
							driver.close();
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						}

					} else {
						log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitleExisting,
								YesNo.Yes);
						sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitleExisting);

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType,
						YesNo.No);
				sa.assertTrue(false,
						"Not able to open " + accountRecordName + " record of record type " + accountRecordType);
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
	public void rgAcuityMNNRTc023_CreateAllDayEventAndVerifyTheNotification(String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Webinar 1 all day";
		String eventAttendees = "Dealroom1.3+James@gmail.com,Dealroom1.3+Lenis@gmail.com" + "," + crmUser3EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 0);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 0);

		String startTime = "";
		String endTime = "";
		String descriptionBox = "Revenue Grid Event";
		String contactRecordName = "Lenis";

		String user3FullName;
		if (!crmUser3FirstName.equals("") && !crmUser3LastName.equals("")) {
			user3FullName = crmUser3FirstName + " " + crmUser3LastName;
		} else {
			user3FullName = crmUser3LastName;

		}
		String[] labelAndValueSeprateByBreak = { "Subject" + "<break>" + eventTitle, "Start" + "<break>" + startDate,
				"End" + "<break>" + endDate, "Description" + "<break>" + "",

				"Name" + "<break>" + "James" };

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, true)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			if (lp.clickOnTab(projectName, TabName.HomeTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
				List<String> notificationHomeNegativeResult = home.verifyNotificationOptionsNotContains(eventTitle);
				if (notificationHomeNegativeResult.isEmpty()) {
					log(LogStatus.INFO,
							"Verified Notification for Event(s): " + eventTitle
									+ " on Home Page not contains in case of user 1 i.e.: " + crmUser1EmailID,
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 1 i.e.: "
									+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult,
							YesNo.No);
					sa.assertTrue(false,
							"The Event(s) " + eventTitle + "+ on Home Page should not contain in case of User 1 i.e.: "
									+ crmUser1EmailID + ", Reason: " + notificationHomeNegativeResult);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
			}

			log(LogStatus.INFO, "---------Now Going to Verify Event: " + eventTitle + " in " + contactRecordName
					+ " Record---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contactRecordName, 30)) {
					log(LogStatus.INFO, contactRecordName + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						List<String> notificationNegativeResult = BP
								.verifyNotificationOptionsNotContainsInRecordDetailPage(eventTitle);
						if (notificationNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Notification not contains for Event(s): " + eventTitle
									+ " in case of User 1 in Detail Page" + contactRecordName, YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"The Event(s) " + eventTitle
											+ " should not contain in case of User 1 in Detail Page: "
											+ contactRecordName + " , Reason: " + notificationNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"The Event(s) " + eventTitle
											+ " should not contain in case of User 1 in Detail Page: "
											+ contactRecordName + " , Reason: " + notificationNegativeResult);
						}

						CommonLib.refresh(driver);
						ArrayList<String> result = BP.verifyRecordOnInteractionCard(null, null, eventTitle, null, true,
								false, null, null);
						if (result.isEmpty()) {
							log(LogStatus.PASS, "------" + eventTitle + " record has been verified on intraction------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "------" + eventTitle
									+ " record is not verified on intraction, Reason: " + result + "------", YesNo.No);
							sa.assertTrue(false, "------" + eventTitle
									+ " record is not verified on intraction, Reason: " + result + "------");
						}

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);

							CommonLib.ThreadSleep(8000);
							List<String> eventDetailPageNegativeResult = BP
									.fieldValueVerification(labelAndValueSeprateByBreak);

							if (eventDetailPageNegativeResult.isEmpty()) {
								log(LogStatus.PASS,
										"------" + eventTitle
												+ " labels and their values in Detail page has been verified------",
										YesNo.No);
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							} else {
								log(LogStatus.ERROR, "------" + eventTitle
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ eventDetailPageNegativeResult + "------", YesNo.No);
								sa.assertTrue(false, "------" + eventTitle
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ eventDetailPageNegativeResult + "------");
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							}

						} else {
							log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + eventTitle,
									YesNo.Yes);
							sa.assertTrue(false, "Record Detail Page has not open for Record: " + eventTitle);
						}

						lp.CRMlogout();
						ThreadSleep(5000);
						lp.CRMLogin(crmUser3EmailID, adminPassword);

						List<String> notificationHomeNegativeResult = home
								.verifyNotificationOptionsNotContains(eventTitle);
						if (notificationHomeNegativeResult.isEmpty()) {
							log(LogStatus.INFO, "Verified Event(s): " + eventTitle
									+ " not contains in Notification pane in Home Page", YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Verified Event(s): " + eventTitle
											+ " not contains in Notification pane in Home Page, Reason: "
											+ notificationHomeNegativeResult,
									YesNo.No);
							sa.assertTrue(false,
									"Not Verified Event(s): " + eventTitle
											+ " not contains in Notification pane in Home Page, Reason: "
											+ notificationHomeNegativeResult);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contactRecordName + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contactRecordName + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void rgAcuityMNNRTc024_CreateEventAndVerifyTheImpactOnSalesforceWhenSubjectEventStartAndEndDateTimeLocationAndDescriptionOfTheEventIsUpdatedInOutlook(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);

		String eventTitle = "Salesforce Event";
		String eventAttendees = "Dealroom1.3+Litz@gmail.com,Dealroom1.3+Jhon@gmail.com" + "," + crmUser1EmailID + ","
				+ crmUser2EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 0);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 1);
		String endDateInAnotherForm1 = endDate;
		String endDateInAnotherForm2 = CommonLib.convertDateFromOneFormatToAnother(endDate, "M/d/yyyy", "MMMM yyyy");

		String startTime = "2:00 PM";
		String endTime = "9:00 PM";
		String descriptionBox = "Revenue Grid Event";

		String updatedEventName = "Salesforce Event Updated";
		String updatedEventAttendees = "Dealroom1.3+James@gmail.com";
		String updatedStartDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 1);
		String updatedEndDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 1);
		String updatedStartTime = "8:00 AM";
		String updatedEndTime = "10:00 AM";
		String updatedDescriptionBox = "";
		boolean updatedAllDayToggle = false;
		String updatedEventAttendeesToRemove = null;

		String recordName = "Litz";

		String user3FullName;
		if (!crmUser3FirstName.equals("") && !crmUser3LastName.equals("")) {
			user3FullName = crmUser3FirstName + " " + crmUser3LastName;
		} else {
			user3FullName = crmUser3LastName;

		}
		String[] labelAndValueSeprateByBreak = { "Subject" + "<break>" + updatedEventName,
				"Start" + "<break>" + updatedStartDate, "End" + "<break>" + updatedEndDate,
				"Description" + "<break>" + "", "Related Associations" + "<break>" + "Sumo Logic, Vertica, Demo Deal",
				"Related Contacts" + "<break>" + "James, Jhon, Litz" };

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle + " through Outlook---------",
				YesNo.No);

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			if (op.loginNavigateAndUpdateTheEvent(rgOutLookUser1Email, rgOutLookUser1Password, endDateInAnotherForm1,
					endDateInAnotherForm2, eventTitle, false, updatedEventName, updatedEventAttendees, updatedStartDate,
					updatedEndDate, updatedStartTime, updatedEndTime, updatedDescriptionBox, updatedAllDayToggle,
					updatedEventAttendeesToRemove)) {
				log(LogStatus.INFO, "-----Event Updated Msg is showing, So Event of Title: " + eventTitle
						+ " has been Updated-----", YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Verify Event: " + updatedEventName + " in Interaction Section---------",
						YesNo.No);
				if (lp.clickOnTab(projectName, tabObj2)) {

					log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

					if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
						log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
						ThreadSleep(4000);
						if (BP.clicktabOnPage("Acuity")) {
							log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

							if (!BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
								log(LogStatus.INFO, "Event Detail Page has not been open for Record: " + eventTitle
										+ " in case of User 1 in Detail Page: " + recordName, YesNo.No);

							} else {
								log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitle
										+ " in case of User 1 in Detail Page: " + recordName, YesNo.Yes);
								sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitle
										+ " in case of User 1 in Detail Page: " + recordName);
								driver.close();
								CommonLib.ThreadSleep(3000);
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

							if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(updatedEventName)) {
								log(LogStatus.INFO, "Event Detail Page has been open for Record: " + updatedEventName,
										YesNo.No);

								CommonLib.ThreadSleep(8000);
								List<String> eventDetailPageNegativeResult = BP
										.fieldValueVerification(labelAndValueSeprateByBreak);

								if (eventDetailPageNegativeResult.isEmpty()) {
									log(LogStatus.PASS,
											"------" + updatedEventName
													+ " labels and their values in Detail page has been verified------",
											YesNo.No);

								} else {
									log(LogStatus.ERROR, "------" + updatedEventName
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ eventDetailPageNegativeResult + "------", YesNo.No);
									sa.assertTrue(false, "------" + updatedEventName
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ eventDetailPageNegativeResult + "------");

								}

								driver.close();
								CommonLib.ThreadSleep(3000);
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							} else {
								log(LogStatus.ERROR, "Record Detail Page has not open for Record: " + updatedEventName,
										YesNo.Yes);
								sa.assertTrue(false, "Record Detail Page has not open for Record: " + updatedEventName);
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
					log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
					sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
				}

			} else {
				log(LogStatus.ERROR, "-----Event Updated Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been Updated-----", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Event Updated Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been Updated-----");
			}

		} else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void rgAcuityMNNRTc025_VerifyWhenPrivateEventIsCreatedInOutllookHavingOrganizerAsInvitee(
			String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String eventTitle = "Outlook Private Event";
		String eventAttendees = crmUser1EmailID;
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 0);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 0);

		String startTime = "8:00 AM";
		String endTime = "9:00 AM";
		String descriptionBox = "Revenue Grid Event";

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);
			home.notificationPopUpClose();
			if (home.globalSearchAndNavigate(eventTitle, "Events", true)) {

				log(LogStatus.INFO, "-----Verified No Event named: " + eventTitle + " found in Events Object-----",
						YesNo.No);
			} else {

				log(LogStatus.ERROR, "-----Event named: " + eventTitle + " found in Event Object-----", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Event named: " + eventTitle + " found in Event Object-----");

			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void rgAcuityMNNRTc026_VerifyWhenEventIsCreatedInOutlookByTaggingTheContactsAndAccountNotCreatedInSalesforce(
			String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String eventTitle = "Outlook Event Test";
		String eventAttendees = crmUser1EmailID + "," + crmUser2EmailID + "," + "cont1.test@zxc.com,cont2+test@zxc.com";
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 0);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 0);

		String startTime = "8:00 AM";
		String endTime = "9:00 AM";
		String descriptionBox = "Added the event in outlook to tag users and Contacts Con1";
		String contact1 = "cont1 test";
		String account1 = "zxc.com";
		String[] labelAndValueSeprateByBreak1 = { "Name" + "<break>" + contact1, "Legal Name" + "<break>" + account1,
				"Email" + "<break>" + "cont1.test@zxc.com" };

		String contact2 = "cont2+test";
		String account2 = "zxc.com";
		String[] labelAndValueSeprateByBreak2 = { "Name" + "<break>" + contact2, "Legal Name" + "<break>" + account2,
				"Email" + "<break>" + "cont2+test@zxc.com" };

		String user1FullName;
		if (!crmUser1FirstName.equals("") && !crmUser1LastName.equals("")) {
			user1FullName = crmUser1FirstName + " " + crmUser1LastName;
		} else {
			user1FullName = crmUser1LastName;

		}

		String[] labelAndValueSeprateByBreakInCaseOfEvent = { "Assigned To" + "<break>" + user1FullName,
				"Subject" + "<break>" + eventTitle, "Name" + "<break>" + contact1,
				"Related Associations" + "<break>" + "Assign Multiple Associations" };

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);
			home.notificationPopUpClose();
			if (home.globalSearchAndNavigate(contact1, "Contacts", false)) {

				log(LogStatus.INFO, "-----Verified Contact named: " + contact1 + " found in Contacts Object-----",
						YesNo.No);

				if (BP.recordDetailPageHeader(contact1, 15) != null) {
					log(LogStatus.INFO, "Record Detail Page has Opened for Record: " + contact1, YesNo.No);

					if (BP.clicktabOnPage("Details")) {
						log(LogStatus.INFO, "clicked on Details tab", YesNo.No);
						CommonLib.ThreadSleep(8000);
						List<String> contactDetailPageNegativeResult = BP
								.fieldValueVerification(labelAndValueSeprateByBreak1);

						if (contactDetailPageNegativeResult.isEmpty()) {
							log(LogStatus.PASS,
									"------" + contact1
											+ " labels and their values in Detail page has been verified------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + contact1
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ contactDetailPageNegativeResult + "------",
									YesNo.No);
							sa.assertTrue(false,
									"------" + contact1
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ contactDetailPageNegativeResult + "------");

						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Details Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Details Tab");
					}

				} else {

					log(LogStatus.ERROR, "Record Detail Page has not Opened for Record: " + contact1, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Record Detail Page has not Opened for Record: " + contact1);

				}

			} else {

				log(LogStatus.ERROR, "-----Contact named: " + contact1 + " not found in Contacts Object-----",
						YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Contact named: " + contact1 + " not found in Contacts Object-----");

			}

			CommonLib.refresh(driver);
			home.notificationPopUpClose();
			if (home.globalSearchAndNavigate(contact2, "Contacts", false)) {

				log(LogStatus.INFO, "-----Verified Contact named: " + contact2 + " found in Contacts Object-----",
						YesNo.No);

				if (BP.recordDetailPageHeader(contact2, 15) != null) {
					log(LogStatus.INFO, "Record Detail Page has Opened for Record: " + contact2, YesNo.No);
					CommonLib.refresh(driver);
					if (BP.clicktabOnPage("Details")) {
						log(LogStatus.INFO, "clicked on Details tab", YesNo.No);
						CommonLib.ThreadSleep(8000);
						List<String> contactDetailPageNegativeResult = BP
								.fieldValueVerification(labelAndValueSeprateByBreak2);

						if (contactDetailPageNegativeResult.isEmpty()) {
							log(LogStatus.PASS,
									"------" + contact2
											+ " labels and their values in Detail page has been verified------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + contact2
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ contactDetailPageNegativeResult + "------",
									YesNo.No);
							sa.assertTrue(false,
									"------" + contact2
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ contactDetailPageNegativeResult + "------");

						}
					} else {

						log(LogStatus.ERROR, "Record Detail Page has not Opened for Record: " + contact1, YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Record Detail Page has not Opened for Record: " + contact1);

					}

				} else {

					log(LogStatus.ERROR, "Record Detail Page has not Opened for Record: " + contact2, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Record Detail Page has not Opened for Record: " + contact2);

				}

			} else {

				log(LogStatus.ERROR, "-----Contact named: " + contact2 + " not found in Contacts Object-----",
						YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Contact named: " + contact2 + " not found in Contacts Object-----");

			}

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in " + contact1 + " Record---------",
					YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contact1, 30)) {
					log(LogStatus.INFO, contact1 + " record has been open", YesNo.No);
					ThreadSleep(4000);
					CommonLib.refresh(driver);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);
							CommonLib.ThreadSleep(8000);
							List<String> eventDetailPageNegativeResult = BP
									.fieldValueVerification(labelAndValueSeprateByBreakInCaseOfEvent);

							if (eventDetailPageNegativeResult.isEmpty()) {
								log(LogStatus.PASS,
										"------" + eventTitle
												+ " labels and their values in Detail page has been verified------",
										YesNo.No);

								driver.close();
								CommonLib.ThreadSleep(3000);

								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							} else {
								log(LogStatus.ERROR, "------" + eventTitle
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ eventDetailPageNegativeResult + "------", YesNo.No);
								sa.assertTrue(false, "------" + eventTitle
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ eventDetailPageNegativeResult + "------");

								driver.close();
								CommonLib.ThreadSleep(3000);

								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							}
						} else {
							log(LogStatus.ERROR, "Event Record Detail Page  has not opened for Record: " + eventTitle
									+ " in Detail Page: " + contact1, YesNo.Yes);
							sa.assertTrue(false, "Event Record Detail Page  has not opened for Record: " + eventTitle
									+ " in Detail Page: " + contact1);

						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contact1 + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contact1 + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void rgAcuityMNNRTc027_VerifyWhenEventIsCreatedInOutlookByTaggingTheContactsAndAccountWhereInOnlyAccountIsCreatedInSalesforceContact(
			String projectName) {

		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String eventTitle = "Outlook Event Test Firm";
		String eventAttendees = crmUser1EmailID + "," + crmUser2EmailID + ","
				+ "cont3.test@sumologic.com,cont4+test@sumologic.com";
		String startDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 0);
		String endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", 0);

		String startTime = "8:00 AM";
		String endTime = "9:00 AM";
		String descriptionBox = "Added the event in outlook to tag users and Contacts Con1";
		String contact1 = "cont3 test";
		String account1 = "Sumo Logic";
		String[] labelAndValueSeprateByBreak1 = { "Name" + "<break>" + contact1, "Legal Name" + "<break>" + account1,
				"Email" + "<break>" + "cont3.test@sumologic.com" };

		String contact2 = "cont4+test";
		String account2 = "Sumo Logic";
		String[] labelAndValueSeprateByBreak2 = { "Name" + "<break>" + contact2, "Legal Name" + "<break>" + account2,
				"Email" + "<break>" + "cont4+test@sumologic.com" };
		String user1FullName;
		if (!crmUser1FirstName.equals("") && !crmUser1LastName.equals("")) {
			user1FullName = crmUser1FirstName + " " + crmUser1LastName;
		} else {
			user1FullName = crmUser1LastName;

		}

		String[] labelAndValueSeprateByBreakInCaseOfEvent = { "Assigned To" + "<break>" + user1FullName,
				"Subject" + "<break>" + eventTitle, "Name" + "<break>" + contact1,
				"Related Associations" + "<break>" + "Assign Multiple Associations" };

		lp.CRMLogin(crmUser1EmailID, "navatar123");

		if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email, rgOutLookUser1Password, eventTitle,
				eventAttendees, startDate, endDate, startTime, endTime, descriptionBox, false)) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle + " has been created-----",
					YesNo.No);

			CommonLib.refresh(driver);
			home.notificationPopUpClose();
			if (home.globalSearchAndNavigate(contact1, "Contacts", false)) {

				log(LogStatus.INFO, "-----Verified Contact named: " + contact1 + " found in Contacts Object-----",
						YesNo.No);

				if (BP.recordDetailPageHeader(contact1, 15) != null) {
					log(LogStatus.INFO, "Record Detail Page has Opened for Record: " + contact1, YesNo.No);
					if (BP.clicktabOnPage("Details")) {
						log(LogStatus.INFO, "clicked on Details tab", YesNo.No);
						CommonLib.ThreadSleep(8000);
						List<String> contactDetailPageNegativeResult = BP
								.fieldValueVerification(labelAndValueSeprateByBreak1);

						if (contactDetailPageNegativeResult.isEmpty()) {
							log(LogStatus.PASS,
									"------" + contact1
											+ " labels and their values in Detail page has been verified------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + contact1
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ contactDetailPageNegativeResult + "------",
									YesNo.No);
							sa.assertTrue(false,
									"------" + contact1
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ contactDetailPageNegativeResult + "------");

						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Details Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Details Tab");
					}
				} else {

					log(LogStatus.ERROR, "Record Detail Page has not Opened for Record: " + contact1, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Record Detail Page has not Opened for Record: " + contact1);

				}

			} else {

				log(LogStatus.ERROR, "-----Contact named: " + contact1 + " not found in Contacts Object-----",
						YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Contact named: " + contact1 + " not found in Contacts Object-----");

			}

			CommonLib.refresh(driver);
			home.notificationPopUpClose();
			if (home.globalSearchAndNavigate(contact2, "Contacts", false)) {

				log(LogStatus.INFO, "-----Verified Contact named: " + contact2 + " found in Contacts Object-----",
						YesNo.No);

				if (BP.recordDetailPageHeader(contact2, 15) != null) {
					log(LogStatus.INFO, "Record Detail Page has Opened for Record: " + contact2, YesNo.No);
					if (BP.clicktabOnPage("Details")) {
						log(LogStatus.INFO, "clicked on Details tab", YesNo.No);
						CommonLib.ThreadSleep(8000);
						List<String> contactDetailPageNegativeResult = BP
								.fieldValueVerification(labelAndValueSeprateByBreak2);

						if (contactDetailPageNegativeResult.isEmpty()) {
							log(LogStatus.PASS,
									"------" + contact2
											+ " labels and their values in Detail page has been verified------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"------" + contact2
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ contactDetailPageNegativeResult + "------",
									YesNo.No);
							sa.assertTrue(false,
									"------" + contact2
											+ " labels and their values in Detail page has not been verified, Reason: "
											+ contactDetailPageNegativeResult + "------");

						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Details Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Details Tab");
					}
				} else {

					log(LogStatus.ERROR, "Record Detail Page has not Opened for Record: " + contact2, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Record Detail Page has not Opened for Record: " + contact2);

				}

			} else {

				log(LogStatus.ERROR, "-----Contact named: " + contact2 + " not found in Contacts Object-----",
						YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Contact named: " + contact2 + " not found in Contacts Object-----");

			}

			log(LogStatus.INFO,
					"---------Now Going to Verify Event: " + eventTitle + " in " + contact1 + " Record---------",
					YesNo.No);
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, contact1, 30)) {
					log(LogStatus.INFO, contact1 + " record has been open", YesNo.No);
					ThreadSleep(4000);
					if (BP.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle)) {
							log(LogStatus.INFO, "Event Detail Page has been open for Record: " + eventTitle, YesNo.No);
							CommonLib.ThreadSleep(8000);
							List<String> eventDetailPageNegativeResult = BP
									.fieldValueVerification(labelAndValueSeprateByBreakInCaseOfEvent);

							if (eventDetailPageNegativeResult.isEmpty()) {
								log(LogStatus.PASS,
										"------" + eventTitle
												+ " labels and their values in Detail page has been verified------",
										YesNo.No);

								driver.close();
								CommonLib.ThreadSleep(3000);

								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							} else {
								log(LogStatus.ERROR, "------" + eventTitle
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ eventDetailPageNegativeResult + "------", YesNo.No);
								sa.assertTrue(false, "------" + eventTitle
										+ " labels and their values in Detail page has not been verified, Reason: "
										+ eventDetailPageNegativeResult + "------");

								driver.close();
								CommonLib.ThreadSleep(3000);

								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

							}
						} else {
							log(LogStatus.ERROR, "Event Record Detail Page  has opened for Record: " + eventTitle
									+ " in Detail Page: " + contact1, YesNo.Yes);
							sa.assertTrue(false, "Event Record Detail Page  has opened for Record: " + eventTitle
									+ " in Detail Page: " + contact1);

						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
						sa.assertTrue(false, "Not able to click on Acuity Tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + contact1 + " record", YesNo.No);
					sa.assertTrue(false, "Not able to open " + contact1 + " record");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
				sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
			}

		}

		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle
					+ " has not been created-----");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	/*
	 * @Parameters({ "projectName" })
	 * 
	 * @Test public void
	 * rgAcuityMNNRTc121_VerifyTheImpactOnSalesforceCalendarWhenEventIsCancelledFromOutlook
	 * (String projectName) {
	 * 
	 * OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
	 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	 * 
	 * String eventTitle = "Outlook Event Test Cancel"; String eventAttendees =
	 * crmUser1EmailID + "<Break>" + crmUser2EmailID + "<Break>" +
	 * "Dealroom1.3+Max@gmail.com<Break>Dealroom1.3+Martha@gmail.com" + "<Break>" +
	 * "Dealroom1.3+Litz@gmail.com"; String startDate =
	 * CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0); String
	 * endDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MM/yyyy", 0);
	 * 
	 * String startTime = "08:00"; String endTime = "09:00"; String descriptionBox =
	 * "Added the event in outlook to tag users and Contacts Con1"; String
	 * endDateInAnotherForm1 = CommonLib.convertDateFromOneFormatToAnother(endDate,
	 * "dd/MM/yyyy", "m/d/yyyy"); String endDateInAnotherForm2 =
	 * CommonLib.convertDateFromOneFormatToAnother(endDate, "dd/MM/yyyy",
	 * "MMMM yyyy");
	 * 
	 * lp.CRMLogin(crmUser1EmailID, adminPassword);
	 * 
	 * if (op.loginAndCreateEventThroughOutLook(rgOutLookUser1Email,
	 * rgOutLookUser1Password, eventTitle, eventAttendees, startDate, endDate,
	 * startTime, endTime, descriptionBox, false)) { log(LogStatus.INFO,
	 * "-----Event Created Msg is showing, So Event of Title: " + eventTitle +
	 * " has been created-----", YesNo.No);
	 * 
	 * if (op.loginNavigateAndUpdateTheEvent(rgOutLookUser1Email,
	 * rgOutLookUser1Password, endDateInAnotherForm1, endDateInAnotherForm2,
	 * eventTitle, true, null, null, null, null, null, null, null, false, null)) {
	 * log(LogStatus.INFO, "-----Event Canceled Msg is showing, So Event of Title: "
	 * + eventTitle + " has been Canceled-----", YesNo.No);
	 * 
	 * } else { log(LogStatus.ERROR,
	 * "-----Event Canceled Msg is not showing, So Event of Title: " + eventTitle +
	 * " has not been Canceled-----", YesNo.Yes); BaseLib.sa.assertTrue(false,
	 * "-----Event Canceled Msg is not showing, So Event of Title: " + eventTitle +
	 * " has not been Canceled-----"); }
	 * 
	 * }
	 * 
	 * else { log(LogStatus.ERROR,
	 * "-----Event Created Msg is not showing, So Event of Title: " + eventTitle +
	 * " has not been created-----", YesNo.Yes); BaseLib.sa.assertTrue(false,
	 * "-----Event Created Msg is not showing, So Event of Title: " + eventTitle +
	 * " has not been created-----"); }
	 * 
	 * ThreadSleep(5000); lp.CRMlogout(); sa.assertAll(); }
	 */

}
