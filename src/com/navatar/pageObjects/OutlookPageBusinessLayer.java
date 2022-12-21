package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.sendKeys;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonVariables.*;

import java.util.Date;

import java.sql.DriverAction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	
	public boolean outLookLoginRevenueGrid(String userName, String userPassword) {
		boolean flag = false;
		if (click(driver, getsignInWithGoogle(30), "signinwithgoogle", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on signinwithgoogle", YesNo.No);
			String parentWindowId = CommonLib.switchOnWindow(driver);
			ThreadSleep(2000);
			Set<String> wind = driver.getWindowHandles();
			System.out.println(wind);
			for(String window : wind) {
			driver.switchTo().window(window);
		}
			if (!parentWindowId.isEmpty()) { 
				log(LogStatus.PASS, "New Window Open after click on Email subject Link: " + userName, YesNo.No);
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

	}
		}

		return flag;
	}
	
		

	public boolean openRGGridAndDoForceSync(action action,int timeout) {
		boolean flag=false;
		String beforeSyncTime =null;
		String afterSyncTime =null;
		WebElement ele =null;
		int count=0;
		
		if (click(driver, getRevenueGridButton(timeout), "Revenue grid button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Revenue grid button", YesNo.No);
			ThreadSleep(1000);
			if (click(driver, getOpenRevenueGridButton(timeout), "open Revenue grid button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on open Revenue grid button", YesNo.No);
				ThreadSleep(2000);
				if (click(driver, getRevenueGridMainMenuButton(timeout), "Revenue grid menu button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Revenue grid menu button", YesNo.No);
					ThreadSleep(1000);
					
					if (click(driver, getSyncSettingButton(timeout), "sync setting button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on sync setting button", YesNo.No);
						ThreadSleep(1000);
						String parentWindow = switchOnWindow(driver);
						if(parentWindow!=null) {
							
							beforeSyncTime = getForceSyncLastSession(timeout).getText();
							
							if (click(driver, getForceSyncButton(timeout), "force sync button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on force sync button", YesNo.No);
								ThreadSleep(1000);
								ele=getForceSyncSuccessErrorMessage(timeout);
								if(ele!=null) {
									ThreadSleep(2000);
									log(LogStatus.INFO, "sucess message present force sync is done", YesNo.No);
									afterSyncTime = getForceSyncLastSession(timeout).getText();
									
									while(beforeSyncTime.equalsIgnoreCase(afterSyncTime) && count<30) {
										refresh(driver);
										ThreadSleep(20000);
										afterSyncTime = getForceSyncLastSession(timeout).getText();
										count++;
										if(beforeSyncTime.equalsIgnoreCase(afterSyncTime)) {
											log(LogStatus.INFO, "Force sync in successfully updated", YesNo.No);
											flag=true;
											break;
										}
										
									}
									driver.close();
									driver.switchTo().window(parentWindow);
								
								}else {
									log(LogStatus.ERROR, "sucess message not present force sync not completed", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "sucess message not present force sync not completed");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on force sync button", YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Not able to click on force sync button");
							}
							
							log(LogStatus.ERROR, "Not able to click on force sync button", YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Not able to click on force sync button");
						}else {
							log(LogStatus.ERROR, "No new window is open after click on sync setting button", YesNo.Yes);
							BaseLib.sa.assertTrue(false, "No new window is open after click on sync setting button");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on sync setting button", YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not able to click on sync setting button");
					}
					
				} else {
					log(LogStatus.ERROR, "Not able to click on Revenue grid menu button", YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not able to click on Revenue grid menu button");
				}
				
			} else {
				log(LogStatus.ERROR, "Not able to click on open revenue grid button", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not able to click on open revenue grid button");
			}
			
		} else {
			log(LogStatus.ERROR, "Not able to click on revenue grid button", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not able to click on revenue grid button");
		}
		
		return flag;
		
	}
		
	public boolean sendMailFromRGOutlook(String[] to,String[] cc,String bcc,String subject,String message ,action action,int timeout) {
		boolean flag = false;

		if (click(driver, getNewEmailButton(timeout), "new email button", action)) {
			log(LogStatus.INFO, "Clicked on new email button", YesNo.No);
			ThreadSleep(2000);
			for(int i = 0; i <to.length; i++) {
			if (sendKeysWithoutClearingTextBox(driver, getToInputBox(timeout), to[i]+",", "to input box", action)) {
				log(LogStatus.INFO, "enter value in To box :" + to[i], YesNo.No);
				ThreadSleep(2000);
			} else {
				log(LogStatus.ERROR, "Not able to enter value in To box :" + to[i], YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not able to enter value in To box :" + to[i]);
			}
			}

			
				if (cc != null) {
					for(int i = 0; i <cc.length; i++) {
					if (sendKeysAndPressEnter(driver, getCCInputBox(timeout), cc[i]+",", "cc input box", action)) {
						log(LogStatus.INFO, "enter value in CC box :" + cc[i], YesNo.No);
						ThreadSleep(1000);
					} else {
						log(LogStatus.ERROR, "Not able to enter value in CC box :" + cc[i], YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not able to enter value in CC box :" + cc[i]);
					}

				}
			}
				ThreadSleep(2000);
				if (bcc != null) {

					if (click(driver, getBCCLink(timeout), "bcc link", action)) {
						log(LogStatus.INFO, "Clicked on bcc link button", YesNo.No);
						ThreadSleep(1000);
						if (sendKeys(driver, getBCCInputBox(timeout), bcc+",", "bcc input box", action)) {
							log(LogStatus.INFO, "able to enter value in BCC box :" + bcc, YesNo.No);
							ThreadSleep(1000);
						} else {
							log(LogStatus.ERROR, "Not able to enter value in BCC box :" + bcc, YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Not able to enter value in BCC box :" + bcc);
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on bcc link button", YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not able to click on bcc link button");
					}
				}

				if (sendKeys(driver, getSubjectInputBox(timeout), subject, "subject input box", action)) {

					log(LogStatus.INFO, "enter value in subject box :" + subject, YesNo.No);
					ThreadSleep(1000);
					if (message != null) {
						if (sendKeys(driver, getMailMessageInputBox(timeout), message, "message input box", action)) {
							log(LogStatus.INFO, "enter value in message box :" + message, YesNo.No);
							ThreadSleep(1000);

						} else {
							log(LogStatus.ERROR, "Not able to enter value in message box :" + message, YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Not able to enter value in message box :" + message);
						}

					}

					if (click(driver, getSendButton(timeout), "Send button", action)) {
						log(LogStatus.INFO, "Clicked on Send button", YesNo.No);
						ThreadSleep(5000);
						flag = true;

					} else {
						log(LogStatus.ERROR, "Not able to clicked on send button", YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not able to clicked on send button");
					}

				} else {
					log(LogStatus.ERROR, "Not able to enter value in subject box :" + subject, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not able to enter value in subject box :" + subject);
				}


		} else {
			log(LogStatus.ERROR, "Not able to click on new email button", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not able to click on new email button");
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

	public boolean navigateToOutlookEventAndClickOnEditOrCancelButton(String userName, String password,
			String eventDate, String expMonthYear, String eventName, boolean cancelEvent) {
		boolean flag = false;

		if (CommonLib.checkDateFormat("m/d/yyyy", eventDate)) {
			log(LogStatus.INFO, "EventDate format is correct.." + eventDate, YesNo.No);

			if (expMonthYear.equalsIgnoreCase(CommonLib.getMonthYear(eventDate))) {
				log(LogStatus.INFO, "Expected Month and Year matched with eventDate:" + eventDate, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Expected Month and Year does not match with eventDate: " + eventDate
						+ " Please pass correct month and Year", YesNo.No);
				return flag;
			}

		} else {
			log(LogStatus.ERROR, "EventDate Format is not correct, Please pass the date in format: m/d/yyyy",
					YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Event Date Format is not correct, Please pass the date in format: m/d/yyyy");
			return flag;
		}

		// Compare two dates:

		SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy");

		String eventDate1 = eventDate;
		String todayDate = CommonLib.getTodayDate();
		// Parsing the given String to Date object
		Date date1 = null;
		try {
			date1 = formatter.parse(eventDate1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date date2 = null;
		try {
			date2 = formatter.parse(todayDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Boolean eventDateGreaterThanTodayDate = date1.after(date2);
		Boolean eventDateSmallerThanTodayDate = date1.before(date2);
		// Boolean eventDateAndTodayDateEqual = date1.equals(date2);

		if (outLookLogin(userName, password)) {
			if (click(driver, getOutlookCalenderIcon(30), "outLookCalenderIcon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on calender Icon", YesNo.No);

				// selectFutureEventDate("December 2022", "6, December, 2022");

				eventDate = CommonLib.convertDateFromOneFormatToAnother(eventDate1, "M/d/yyyy", "d, MMMM, yyyy");

				if (eventDateGreaterThanTodayDate) {
					log(LogStatus.INFO,
							"Passed date is Greater than today's date..Going to select Future date:" + eventDate,
							YesNo.No);
					selectFutureEventDate(expMonthYear, eventDate, eventName, cancelEvent);

				} else if (eventDateSmallerThanTodayDate) {
					log(LogStatus.INFO,
							"Passed date is smaller than today's date..Going to select Past date:" + eventDate,
							YesNo.No);
					selectPastEventDate(expMonthYear, eventDate, eventName, cancelEvent);

				} else {
					selectFutureEventDate(expMonthYear, eventDate, eventName, cancelEvent);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on calender Icon", YesNo.No);
				BaseLib.sa.assertTrue(false, "Not able to click on calender Icon");
			}

		} else {
			log(LogStatus.ERROR, "Not able to login to Outlook with Id: " + userName, YesNo.No);
			BaseLib.sa.assertTrue(false, "Not able to login to Outlook with Id: " + userName);
		}

		return flag;

	}

	public boolean selectFutureEventDate(String expMonthYear, String dateNum, String eventName, boolean cancelEvent) {

		boolean flag = false;
		String xPath = null;
		xPath = "//button[contains(@class,'dayButton') and @aria-label='" + dateNum + "']";

		String actMonthYear = CommonLib.getText(driver, getActualMonth(30), "actual month", action.SCROLLANDBOOLEAN);
		if (actMonthYear != null) {
			log(LogStatus.INFO, "Actual month and Year is: " + actMonthYear, YesNo.No);

			while (!actMonthYear.equalsIgnoreCase(expMonthYear)) {
				// click on down:
				if (click(driver, getdownArrowValue(30), "calender down arrow", action.SCROLLANDBOOLEAN)) {
					actMonthYear = CommonLib.getText(driver, getActualMonth(30), "actual month",
							action.SCROLLANDBOOLEAN);
				} else {
					log(LogStatus.ERROR, "Actual month not fetched, there is some problem ", YesNo.No);
					return flag;
				}
			}

			if (click(driver, CommonLib.FindElement(driver, xPath, "date Number", action.SCROLLANDBOOLEAN, 30),
					"date Number", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Actual date successfully.", YesNo.No);

				CommonLib.ThreadSleep(5000);

				if (selectEventName(eventName)) {
					log(LogStatus.INFO, "clicked on expected eventName", YesNo.No);

					if (cancelEvent) {
						if (click(driver, getEventCancelButton(30), "Event cancel button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Cancel button Successfully.", YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not able to click on Cancel button", YesNo.No);
							return flag;
						}
					} else {

						if (click(driver, getEventEditButton(30), "Event Edit button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Edit button Successfully.", YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not able to click on Edit button", YesNo.No);
							return flag;
						}
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on expected eventName, Event not FOUND.", YesNo.No);
					return flag;
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on date.. ", YesNo.No);
				return flag;
			}
		} else {
			log(LogStatus.ERROR, "Actual month is null, there is some problem with locator " + actMonthYear, YesNo.No);
			return flag;
		}
		return flag;

	}

	public boolean selectPastEventDate(String MonthYear, String dateNum, String eventName, boolean cancelEvent) {

		boolean flag = false;
		String xPath = null;
		xPath = "//button[contains(@class,'dayButton') and @aria-label='" + dateNum + "']";

		String actMonthYear = CommonLib.getText(driver, getActualMonth(30), "actual month", action.SCROLLANDBOOLEAN);
		if (actMonthYear != null) {
			log(LogStatus.INFO, "Actual month is: " + actMonthYear, YesNo.No);

			while (!actMonthYear.equalsIgnoreCase(MonthYear)) {
				// click on up:
				if (click(driver, getUpArrowValue(30), "calender up arrow", action.SCROLLANDBOOLEAN)) {

					actMonthYear = CommonLib.getText(driver, getActualMonth(30), "actual month",
							action.SCROLLANDBOOLEAN);
				} else {
					log(LogStatus.ERROR, "Actual month not fetched, there is some problem ", YesNo.No);
					return flag;
				}
			}

			if (click(driver, CommonLib.FindElement(driver, xPath, "date Number", action.SCROLLANDBOOLEAN, 30),
					"date Number", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Actual date successfully.", YesNo.No);

				CommonLib.ThreadSleep(5000);

				if (selectEventName(eventName)) {
					log(LogStatus.INFO, "clicked on expected eventName", YesNo.No);

					if (cancelEvent) {
						if (click(driver, getEventCancelButton(30), "Event cancel button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Cancel button Successfully.", YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not able to click on Cancel button", YesNo.No);
							return flag;
						}
					} else {

						if (click(driver, getEventEditButton(30), "Event Edit button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Edit button Successfully.", YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not able to click on Edit button", YesNo.No);
							return flag;
						}
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on expected eventName, Event not FOUND.", YesNo.No);
					return flag;
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on date.. ", YesNo.No);
				return flag;
			}
		} else {
			log(LogStatus.ERROR, "Actual month is null, there is some problem with locator " + actMonthYear, YesNo.No);
			return flag;
		}
		return flag;

	}

	public boolean selectEventName(String eventName) {

		boolean flag = false;

		List<WebElement> eventNameList = getlistOfEventNames();

		for (WebElement e : eventNameList) {
			String text = e.getText();
			if (eventName.equals(text)) {
				e.click();
				break;
			}

			flag = true;
		}
		return flag;

	}

	public boolean updateEventThroughOutlook(String eventTitle, String eventAttendees, String startDate, String endDate,
			String startTime, String endTime, String descriptionBox, boolean allDayToggle,
			String eventAttendeesToRemove) {

		boolean flag = false;

		if (eventAttendeesToRemove != null || !"".equals(eventAttendeesToRemove)) {
			String[] reMoveAttendees = eventAttendees.split("<Break>", -1);
			WebElement ele;
			for (int i = 0; i < reMoveAttendees.length; i++) {
				ele = crossButtonOfAttendeeInEditOutLook(reMoveAttendees[i], 10);
				if (ele != null) {
					if (CommonLib.clickUsingJavaScript(driver, ele, reMoveAttendees[i] + " tag name")) {
						log(LogStatus.INFO, "clicked on cross button of " + reMoveAttendees[i], YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on cross button of " + reMoveAttendees[i], YesNo.No);
						sa.assertTrue(false, "Not able to click on cross button of " + reMoveAttendees[i]);
						return false;
					}
				} else {
					log(LogStatus.ERROR, "Not able to get the element of " + reMoveAttendees[i], YesNo.No);
					sa.assertTrue(false, "Not able to get the element of " + reMoveAttendees[i]);
					return false;
				}
			}

		}

		if (eventTitle != null) {

			eventTitleInputBox(30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			if (sendKeys(driver, eventTitleInputBox(30), eventTitle, "Input Value : " + eventTitle, action.BOOLEAN)) {
				CommonLib.log(LogStatus.INFO, "Entered Value: " + eventTitle, YesNo.No);
			} else {

				CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + eventTitle, YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + eventTitle);
			}
		}

		if (eventAttendees != null) {
			String[] attendees = eventAttendees.split("<Break>", -1);
			for (String attendee : attendees) {
				if (sendKeys(driver, inviteAttendeesInputBox(30), attendee, "Input Attendee Value : " + attendee,
						action.BOOLEAN)) {
					CommonLib.log(LogStatus.INFO, "Entered Value: " + attendee, YesNo.No);

					if (click(driver, inviteAttendeeSuggestionBoxName(attendee, 4), "Suggested Invitee: " + attendee,
							action.SCROLLANDBOOLEAN)) {
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

		}

		if (allDayToggle) {
			String toggleFlag = CommonLib.getAttribute(driver, allDayToggleButton(20), "All Day Toggle Button",
					"aria-checked");
			if (toggleFlag.contains("false")) {

				if (click(driver, allDayToggleButton(20), "All Day Toggle Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on All Day Toggle Button", YesNo.No);

					toggleFlag = CommonLib.getAttribute(driver, allDayToggleButton(20), "All Day Toggle Button",
							"aria-checked");

					if (toggleFlag.contains("true")) {
						log(LogStatus.INFO, "All Day toggle Button has been Enabled", YesNo.No);
					} else {
						log(LogStatus.ERROR, "All Day toggle Button has not been Enable after Click on it", YesNo.Yes);
						BaseLib.sa.assertTrue(false, "All Day toggle Button has not been Enable after Click on it");
					}
				} else {
					log(LogStatus.ERROR, "Not able to Click on All Day Toggle Button", YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not able to Click on All Day Toggle Button");
				}
			}

		}

		if (startDate != null || !"".equalsIgnoreCase(startDate)) {

			startDateInputBox(30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			if (sendKeys(driver, startDateInputBox(30), startDate, "Input Value : " + startDate, action.BOOLEAN)) {
				CommonLib.log(LogStatus.INFO, "Entered Value: " + startDate, YesNo.No);

			} else {

				CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + startDate, YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + startDate);

			}

		}

		if (!allDayToggle) {
			if (startTime != null || !"".equalsIgnoreCase(startTime)) {

				startTimeInputBox(30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
				if (sendKeys(driver, startTimeInputBox(30), startTime, "Input Value : " + startTime, action.BOOLEAN)) {
					CommonLib.log(LogStatus.INFO, "Entered Value: " + startTime, YesNo.No);

				} else {

					CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + startTime, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + startTime);

				}

			}
		}

		if (endDate != null || !"".equalsIgnoreCase(endDate)) {

			endDateInputBox(30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			if (sendKeys(driver, endDateInputBox(30), endDate, "Input Value : " + endDate, action.BOOLEAN)) {
				CommonLib.log(LogStatus.INFO, "Entered Value: " + endDate, YesNo.No);

			} else {

				CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + endDate, YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + endDate);

			}
		}

		if (!allDayToggle) {
			if (endTime != null || !"".equalsIgnoreCase(endTime)) {

				endTimeInputBox(30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
				if (sendKeys(driver, endTimeInputBox(30), endTime, "Input Value : " + endTime, action.BOOLEAN)) {
					CommonLib.log(LogStatus.INFO, "Entered Value: " + endTime, YesNo.No);

				} else {

					CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + endTime, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + endTime);

				}

			}
		}

		if (descriptionBox != null) {

			newEventDescriptionBox(30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			if (sendKeys(driver, newEventDescriptionBox(30), descriptionBox, "Input Value : " + descriptionBox,
					action.BOOLEAN)) {
				CommonLib.log(LogStatus.INFO, "Entered Value: " + descriptionBox, YesNo.No);

			} else {

				CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + descriptionBox, YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not Able to Entered Value: " + descriptionBox);

			}

		}

		if (click(driver, eventSendButton(30), "eventSendButton", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on eventSendButton", YesNo.No);
			if (eventUpdateMsg(30) != null) {
				log(LogStatus.INFO,
						"-----Event Update Msg is showing, So Event of Title: " + eventTitle + " has been Updated-----",
						YesNo.No);

				flag = true;
			}

			else {

				log(LogStatus.ERROR, "-----Event Update Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been Updated-----", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "-----Event Update Msg is not showing, So Event of Title: " + eventTitle
						+ " has not been Updated-----");

			}

		} else {
			log(LogStatus.ERROR, "Not able to Click on eventSendButton", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not able to Click on eventSendButton");
		}

		return flag;

	}

	public boolean loginNavigateAndUpdateTheEvent(String userName, String password, String eventDate,
			String expMonthYear, String eventName, boolean cancelEvent, String updatedEventName, String eventAttendees, String startDate,
			String endDate, String startTime, String endTime, String descriptionBox, boolean allDayToggle,
			String eventAttendeesToRemove) {

		boolean flag = false;
		String newWindowCode = "window. open('about:blank','_blank');";
		((JavascriptExecutor) driver).executeScript(newWindowCode);
		String parentId = CommonLib.switchOnWindow(driver);
		if (parentId != null) {
			log(LogStatus.INFO, "Switched to New Tab", YesNo.No);
			if (navigateToOutlookEventAndClickOnEditOrCancelButton(userName, password, eventDate, expMonthYear,
					eventName, cancelEvent)) {
				log(LogStatus.INFO,
						"Logged In to Outlook for Id: " + userName + " & Successfully Navigated to Event: " + eventName,
						YesNo.No);
				if (updateEventThroughOutlook(updatedEventName, eventAttendees, startDate, endDate, startTime, endTime,
						descriptionBox, allDayToggle, eventAttendeesToRemove)) {
					log(LogStatus.INFO, "------Event: " + updatedEventName + " Updated through outlook---------", YesNo.No);
					flag = true;
					driver.close();
					driver.switchTo().window(parentId);
				} else {
					CommonLib.log(LogStatus.ERROR,
							"------Not able to Update Event: " + updatedEventName + " through outlook--------", YesNo.Yes);
					BaseLib.sa.assertTrue(false,
							"------Not able to Update Event: " + updatedEventName + " through outlook--------");
					driver.close();
					driver.switchTo().window(parentId);
				}
			}

			else {
				CommonLib.log(LogStatus.ERROR, "Either not able to Logged In to Outlook for Id: " + userName
						+ " or Not Successfully Navigated to Event: " + eventName, YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Either not able to Logged In to Outlook for Id: " + userName
						+ " or Not Successfully Navigated to Event: " + eventName);
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
