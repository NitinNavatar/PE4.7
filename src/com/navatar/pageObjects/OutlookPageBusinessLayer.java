package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.sendKeys;
import static com.navatar.generic.CommonVariables.*;

import org.openqa.selenium.WebDriver;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;

public class OutlookPageBusinessLayer extends OutlookPage {

	public OutlookPageBusinessLayer(WebDriver driver) {
		super(driver);

	}

	public boolean createEventThroughOutlook(String eventTitle, String eventAttendees, String startDate, String endDate,
			String startTime, String endTime, String descriptionBox) {

		boolean flag = false;
		String[] attendees = eventAttendees.split("<Break>", -1);
		if (click(driver, calendarButton(30), "calendarButton", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Calendar Button", YesNo.No);

			if (click(driver, newEventButton(30), "newEventButton", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on newEventButton", YesNo.No);
				if (sendKeys(driver, eventTitleInputBox(30), eventTitle, "Input Value : " + eventTitle,
						action.BOOLEAN)) {
					CommonLib.log(LogStatus.INFO, "Entered Value: " + eventTitle, YesNo.No);

					for (String attendee : attendees) {
						if (sendKeys(driver, inviteAttendeesInputBox(30), attendee,
								"Input Attendee Value : " + attendee, action.BOOLEAN)) {
							CommonLib.log(LogStatus.INFO, "Entered Value: " + attendee, YesNo.No);
						} else {

							CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + attendee, YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + attendee);
							return false;
						}

						CommonLib.ThreadSleep(1000);
					}

					if (sendKeys(driver, startDateInputBox(30), startDate, "Input Value : " + startDate,
							action.BOOLEAN)) {
						CommonLib.log(LogStatus.INFO, "Entered Value: " + startDate, YesNo.No);

						if (sendKeys(driver, startTimeInputBox(30), startTime, "Input Value : " + startTime,
								action.BOOLEAN)) {
							CommonLib.log(LogStatus.INFO, "Entered Value: " + startTime, YesNo.No);

							if (sendKeys(driver, endDateInputBox(30), endDate, "Input Value : " + startDate,
									action.BOOLEAN)) {
								CommonLib.log(LogStatus.INFO, "Entered Value: " + endDate, YesNo.No);

								if (sendKeys(driver, endTimeInputBox(30), endTime, "Input Value : " + startTime,
										action.BOOLEAN)) {
									CommonLib.log(LogStatus.INFO, "Entered Value: " + endTime, YesNo.No);

									if (sendKeys(driver, newEventDescriptionBox(30), descriptionBox,
											"Input Value : " + descriptionBox, action.BOOLEAN)) {
										CommonLib.log(LogStatus.INFO, "Entered Value: " + descriptionBox, YesNo.No);

										if (click(driver, eventSendButton(30), "eventSendButton",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on eventSendButton", YesNo.No);
											if (eventCreatedMsg(30) != null) {
												log(LogStatus.INFO,
														"-----Event Created Msg is showing, So Event of Title: "
																+ eventTitle + " has been created-----",
														YesNo.No);
												flag = true;
											}

											else {

												log(LogStatus.ERROR,
														"-----Event Created Msg is not showing, So Event of Title: "
																+ eventTitle + " has not been created-----",
														YesNo.Yes);
												BaseLib.sa.assertTrue(false,
														"-----Event Created Msg is not showing, So Event of Title: "
																+ eventTitle + " has not been created-----");
											}

										} else {
											log(LogStatus.ERROR, "Not able to Click on eventSendButton", YesNo.Yes);
											BaseLib.sa.assertTrue(false, "Not able to Click on eventSendButton");
										}

									} else {

										CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + descriptionBox,
												YesNo.Yes);
										BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + descriptionBox);

									}

								} else {

									CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + endTime, YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + endTime);

								}
							} else {

								CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + endDate, YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + endDate);

							}

						} else {

							CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + startTime, YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + startTime);

						}
					} else {

						CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + startDate, YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + startDate);

					}

				} else {

					CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + eventTitle, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + eventTitle);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Click on newEventButton", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not able to Click on newEventButton");
			}
		} else {
			log(LogStatus.ERROR, "Not able to Click on Calendar Button", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not able to Click on Calendar Button");
		}

		return flag;

	}

	public boolean outLookLogin(String userName, String userPassword) {
		driver.get(outLookAddress);
		boolean flag = false;
		if (sendKeys(driver, loginEmailInputBox(30), userName, "Input Value : " + userName, action.BOOLEAN)) {
			CommonLib.log(LogStatus.INFO, "Entered Value: " + userName, YesNo.No);

			if (click(driver, loginNextButton(30), "loginNextButton", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on loginNextButton", YesNo.No);

				if (sendKeys(driver, loginPasswordInputBox(30), userPassword, "Input Value : " + userPassword, action.BOOLEAN)) {
					CommonLib.log(LogStatus.INFO, "Entered Value: " + userPassword, YesNo.No);

					if (click(driver, loginSignInButton(30), "loginSignInButton", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on loginSignInButton", YesNo.No);

						if (loginYesButton(10) != null) {
							if (click(driver, loginYesButton(30), "loginYesButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on loginYesButton", YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not able to Click on loginYesButton", YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Not able to Click on loginYesButton");
							}
						}
						if (CommonLib.getTitle(driver).contains("Email")) {
							log(LogStatus.INFO,
									"-----Successfully Logged in to Outlook for Email: " + userName + "------",
									YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR,
									"-----Not Successfully Logged in to Outlook for Email: " + userName + "------",
									YesNo.Yes);
							BaseLib.sa.assertTrue(false,
									"-----Not Successfully Logged in to Outlook for Email: " + userName + "------");
						}

					} else {
						log(LogStatus.ERROR, "Not able to Click on loginSignInButton", YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not able to Click on loginSignInButton");
					}
				} else {

					CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + userPassword, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + userPassword);

				}

			} else {
				log(LogStatus.ERROR, "Not able to Click on loginNextButton", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not able to Click on loginNextButton");
			}
		} else {

			CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + userName, YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + userName);

		}
		return flag;

	}

}
