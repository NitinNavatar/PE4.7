package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.sendKeys;
import static com.navatar.generic.CommonVariables.*;

import java.util.Date;

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
		
	public boolean sendMailFromRGOutlook(action action,int timeout) {
		boolean flag=false;
		String beforeSyncTime =null;
		String afterSyncTime =null;
		WebElement ele =null;
		int count=0;
		
		if (click(driver, getNewEmailButton(timeout), "new email button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on new email button", YesNo.No);
			ThreadSleep(2000);
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
			log(LogStatus.ERROR, "Not able to new email button", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not able to click on new email button");
		}
		
		return flag;
		
	}
}
