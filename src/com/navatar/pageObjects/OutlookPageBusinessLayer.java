package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.sendKeys;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonVariables.*;

import java.sql.DriverAction;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
			String startTime, String endTime, String descriptionBox, boolean allDayToggle) {

		boolean flag = false;

		String[] attendees = eventAttendees.split("<Break>", -1);
		if (click(driver, calendarButton(30), "calendarButton", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Calendar Button", YesNo.No);

			CommonLib.ThreadSleep(4000);
			if (CommonLib.clickUsingJavaScript(driver, newEventButton(30), "newEventButton", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on newEventButton", YesNo.No);

				eventTitleInputBox(30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
				if (sendKeys(driver, eventTitleInputBox(30), eventTitle, "Input Value : " + eventTitle,
						action.BOOLEAN)) {
					CommonLib.log(LogStatus.INFO, "Entered Value: " + eventTitle, YesNo.No);

					for (String attendee : attendees) {
						if (sendKeys(driver, inviteAttendeesInputBox(30), attendee,
								"Input Attendee Value : " + attendee, action.BOOLEAN)) {
							CommonLib.log(LogStatus.INFO, "Entered Value: " + attendee, YesNo.No);

							if (click(driver, inviteAttendeeSuggestionBoxName(attendee, 4),
									"Suggested Invitee: " + attendee, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Suggested Invitee: " + attendee, YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not able to Click on Suggested Invitee: " + attendee, YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Not able to Click on Suggested Invitee: " + attendee);
								return false;
							}
						} else {

							CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + attendee, YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + attendee);

							return false;
						}

						CommonLib.ThreadSleep(1000);
					}

					if (allDayToggle) {
						String toggleFlag = CommonLib.getAttribute(driver, allDayToggleButton(20),
								"All Day Toggle Button", "aria-checked");
						if (toggleFlag.contains("false")) {

							if (click(driver, allDayToggleButton(20), "All Day Toggle Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on All Day Toggle Button", YesNo.No);

								toggleFlag = CommonLib.getAttribute(driver, allDayToggleButton(20),
										"All Day Toggle Button", "aria-checked");

								if (toggleFlag.contains("true")) {
									log(LogStatus.INFO, "All Day toggle Button has been Enabled", YesNo.No);
								} else {
									log(LogStatus.ERROR, "All Day toggle Button has not been Enable after Click on it",
											YesNo.Yes);
									BaseLib.sa.assertTrue(false,
											"All Day toggle Button has not been Enable after Click on it");
								}
							} else {
								log(LogStatus.ERROR, "Not able to Click on All Day Toggle Button", YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Not able to Click on All Day Toggle Button");
							}
						}

					}

					if (startDate != null || !"".equalsIgnoreCase(startDate)) {

						startDateInputBox(30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
						if (sendKeys(driver, startDateInputBox(30), startDate, "Input Value : " + startDate,
								action.BOOLEAN)) {
							CommonLib.log(LogStatus.INFO, "Entered Value: " + startDate, YesNo.No);

						} else {

							CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + startDate, YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + startDate);

						}

					}

					if (!allDayToggle) {
						if (startTime != null || !"".equalsIgnoreCase(startTime)) {

							startTimeInputBox(30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
							if (sendKeys(driver, startTimeInputBox(30), startTime, "Input Value : " + startTime,
									action.BOOLEAN)) {
								CommonLib.log(LogStatus.INFO, "Entered Value: " + startTime, YesNo.No);

							} else {

								CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + startTime, YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + startTime);

							}

						}
					}

					if (endDate != null || !"".equalsIgnoreCase(endDate)) {

						endDateInputBox(30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
						if (sendKeys(driver, endDateInputBox(30), endDate, "Input Value : " + endDate,
								action.BOOLEAN)) {
							CommonLib.log(LogStatus.INFO, "Entered Value: " + endDate, YesNo.No);

						} else {

							CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + endDate, YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + endDate);

						}
					}

					if (!allDayToggle) {
						if (endTime != null || !"".equalsIgnoreCase(endTime)) {

							endTimeInputBox(30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
							if (sendKeys(driver, endTimeInputBox(30), endTime, "Input Value : " + endTime,
									action.BOOLEAN)) {
								CommonLib.log(LogStatus.INFO, "Entered Value: " + endTime, YesNo.No);

							} else {

								CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + endTime, YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + endTime);

							}

						}
					}

					if (descriptionBox != null || !"".equalsIgnoreCase(descriptionBox)) {

						newEventDescriptionBox(30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
						if (sendKeys(driver, newEventDescriptionBox(30), descriptionBox,
								"Input Value : " + descriptionBox, action.BOOLEAN)) {
							CommonLib.log(LogStatus.INFO, "Entered Value: " + descriptionBox, YesNo.No);

						} else {

							CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + descriptionBox, YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + descriptionBox);

						}

					}

					if (click(driver, eventSendButton(30), "eventSendButton", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on eventSendButton", YesNo.No);
						if (eventCreatedMsg(30) != null) {
							log(LogStatus.INFO, "-----Event Created Msg is showing, So Event of Title: " + eventTitle
									+ " has been created-----", YesNo.No);

							flag = true;
						}

						else {

							log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: "
									+ eventTitle + " has not been created-----", YesNo.Yes);
							BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: "
									+ eventTitle + " has not been created-----");

						}

					} else {
						log(LogStatus.ERROR, "Not able to Click on eventSendButton", YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not able to Click on eventSendButton");
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

				if (sendKeys(driver, loginPasswordInputBox(30), userPassword, "Input Value : " + userPassword,
						action.BOOLEAN)) {
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

	public boolean loginAndCreateEventThroughOutLook(String userName, String userPassword, String eventTitle,
			String eventAttendees, String startDate, String endDate, String startTime, String endTime,
			String descriptionBox, boolean allDayToggle) {
		// ((JavascriptExecutor) driver).executeScript("window.open()");
		boolean flag = false;
		String newWindowCode = "window. open('about:blank','_blank');";
		((JavascriptExecutor) driver).executeScript(newWindowCode);
		String parentId = CommonLib.switchOnWindow(driver);
		if (parentId != null) {
			log(LogStatus.INFO, "Switched to New Tab", YesNo.No);
			if (outLookLogin(userName, userPassword)) {
				log(LogStatus.INFO, "Logged In to Outlook for Id: " + userName, YesNo.No);
				if (createEventThroughOutlook(eventTitle, eventAttendees, startDate, endDate, startTime, endTime,
						descriptionBox, allDayToggle)) {
					log(LogStatus.INFO, "Event: " + eventTitle + " Created through outlook", YesNo.No);
					flag = true;
					driver.close();
					driver.switchTo().window(parentId);
				} else {
					CommonLib.log(LogStatus.ERROR, "Not able to Create Event: " + eventTitle + " through outlook",
							YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not able to Create Event: " + eventTitle + " through outlook");
					driver.close();
					driver.switchTo().window(parentId);
				}
			}

			else {
				CommonLib.log(LogStatus.ERROR, "Not able to Login to Oulook for id: " + userName, YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not able to Login to Oulook for id: " + userName);
				driver.close();
				driver.switchTo().window(parentId);
			}

		} else {
			CommonLib.log(LogStatus.ERROR, "Not Able to switch to new tab", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not Able to switch to new tab");
		}

		return flag;
	}

}
