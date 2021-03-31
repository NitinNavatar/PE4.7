package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.navatar.generic.AppListeners.*;

public class MarketingEventPageBusinessLayer extends MarketingEventPage {

	public MarketingEventPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	///////////////////////////////////////////////////////  Activity Association ///////////////////////////////////////////////////////////////////////////
	
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param marketingEventName
	 * @param recordType
	 * @param labelsWithValues
	 * @param timeOut
	 * @return true/false
	 * @description this method is used to create single entity if pe and account if mna
	 */
	public boolean createMarketingEvent(String projectName,String marketingEventName,String recordType, String date,String organizer,int timeOut) {
		boolean flag=false;
		refresh(driver);
		ThreadSleep(3000);
		ThreadSleep(10000);
		if(clickUsingJavaScript(driver, getNewButton(projectName, timeOut), "new button")) {
			appLog.info("clicked on new button");

			if (!recordType.equals("") || !recordType.isEmpty()) {
				ThreadSleep(2000);
				if(click(driver, getRadioButtonforRecordType(recordType, timeOut), "Radio Button for : "+recordType, action.SCROLLANDBOOLEAN)){
					appLog.info("Clicked on radio Button for Marketing Event for record type : "+recordType);
					if (click(driver, getContinueOrNextButton(projectName,timeOut), "Continue Button", action.BOOLEAN)) {
						appLog.info("Clicked on Continue or Nxt Button");	
						ThreadSleep(1000);
					}else{
						appLog.error("Not Able to Clicked on Next Button");
						return false;	
					}
				}else{
					appLog.error("Not Able to Clicked on radio Button for record type : "+recordType);
					return false;
				}

			}

			if (sendKeys(driver, getMarketingEventTextBox(projectName,timeOut), marketingEventName, "leagl name text box",action.SCROLLANDBOOLEAN)) {
				appLog.info("passed data in text box: " + marketingEventName);

				FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
				String label;
				String value;
				if (!date.equals("") || !date.isEmpty()) {

					WebElement ele = getLabelTextBox(projectName, PageName.Object5Page.toString(), PageLabel.Date.toString(), timeOut);
					if (sendKeys(driver,ele, date, date, action.BOOLEAN)) {
						appLog.info("Successfully Entered value on date TextBox : "+date);		
						ThreadSleep(1000);
					}else {

					}
				}

				if (sendKeys(driver, getOrganizerName(projectName, 60), organizer, "organizer Name",
						action.SCROLLANDBOOLEAN)) {
					ThreadSleep(1000);
					if (click(driver,FindElement(driver,"//*[@title='"+organizer+"']","organizer Name List", action.BOOLEAN, 30),
							organizer + "   :   Company Name", action.BOOLEAN)) {
						appLog.info(organizer + "  is present in list.");
					} else {
						appLog.info(organizer + "  is not present in the list.");
						return false;
					}

				} else {
					appLog.error("Not able to enter organizer name");
					return false;
				}
				if (click(driver, getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on save button");

					String str = getText(driver, getMarketingEventInViewMode(timeOut, marketingEventName), "Marketing Name Label Text",action.SCROLLANDBOOLEAN);
					if (str != null) {
						if (str.contains(marketingEventName)) {
							appLog.info("created Marketing Event " + marketingEventName + " is verified successfully.");
							appLog.info(marketingEventName + " is created successfully.");
							flag=true;
						} else {
							appLog.error("Created  " + marketingEventName + " is not matched with " + str);
						}
					} else {
						appLog.error("Created  " + marketingEventName + " is not visible");
					}
				} else {
					appLog.error("Not able to click on save button so cannot create : "+ marketingEventName);
				}
			} else {
				appLog.error("Not able to pass data in Marketing name text box so cannot create : "+ marketingEventName);
			}

		}else {
			appLog.error("Not able to click on New Button so cannot create Marketing Event: " + marketingEventName);

		}
		return flag;
	}
	public boolean createMarketingEventFromSDG(String projectName,String marketingEventName,String recordType, String date,String organizer,int timeOut) {
		boolean flag=false;

			if (!recordType.equals("") || !recordType.isEmpty()) {
				ThreadSleep(2000);
				if(click(driver, getRadioButtonforRecordTypeME(recordType, timeOut), "Radio Button for : "+recordType, action.SCROLLANDBOOLEAN)){
					appLog.info("Clicked on radio Button for Marketing Event for record type : "+recordType);
					if (click(driver, getContinueOrNextButton(projectName,timeOut), "Continue Button", action.BOOLEAN)) {
						appLog.info("Clicked on Continue or Nxt Button");	
						ThreadSleep(1000);
					}else{
						appLog.error("Not Able to Clicked on Next Button");
						return false;	
					}
				}else{
					appLog.error("Not Able to Clicked on radio Button for record type : "+recordType);
					return false;
				}

			}

			if (sendKeys(driver, getMarketingEventTextBox(projectName,timeOut), marketingEventName, "leagl name text box",action.SCROLLANDBOOLEAN)) {
				appLog.info("passed data in text box: " + marketingEventName);

				FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
				String label;
				String value;
				if (!date.equals("") || !date.isEmpty()) {

					WebElement ele = getLabelTextBox(projectName, PageName.Object5Page.toString(), PageLabel.Date.toString(), timeOut);
					if (sendKeys(driver,ele, date, date, action.BOOLEAN)) {
						appLog.info("Successfully Entered value on date TextBox : "+date);		
						ThreadSleep(1000);
					}else {

					}
				}
				if(organizer!=null) {
				if (sendKeys(driver, getOrganizerName(projectName, 60), organizer, "organizer Name",
						action.SCROLLANDBOOLEAN)) {
					ThreadSleep(1000);
					if (click(driver,FindElement(driver,"//*[@title='"+organizer+"']","organizer Name List", action.BOOLEAN, 30),
							organizer + "   :   Company Name", action.BOOLEAN)) {
						appLog.info(organizer + "  is present in list.");
					} else {
						appLog.info(organizer + "  is not present in the list.");
						return false;
					}

				} else {
					appLog.error("Not able to enter organizer name");
					return false;
				}
				}
				if (click(driver, getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on save button");

					String str = getText(driver, getMarketingEventInViewMode(timeOut, marketingEventName), "Marketing Name Label Text",action.SCROLLANDBOOLEAN);
					if (str != null) {
						if (str.contains(marketingEventName)) {
							appLog.info("created Marketing Event " + marketingEventName + " is verified successfully.");
							appLog.info(marketingEventName + " is created successfully.");
							flag=true;
						} else {
							appLog.error("Created  " + marketingEventName + " is not matched with " + str);
						}
					} else {
						appLog.error("Created  " + marketingEventName + " is not visible");
					}
				} else {
					appLog.error("Not able to click on save button so cannot create : "+ marketingEventName);
				}
			} else {
				appLog.error("Not able to pass data in Marketing name text box so cannot create : "+ marketingEventName);
			}

		return flag;
	}
	
	public WebElement toggleButton(String projectName,PageName pageName,String btnName,action action,int timeOut) {
		String xpath = "//button[@title='"+btnName+"']";
		WebElement ele = FindElement(driver, xpath,"Toggle Button : "+btnName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btnName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btnName);
		return ele;
	}
	
	public WebElement toggleSDGButtons(String projectName,PageName pageName,String toggleTab,ToggleButtonGroup btnName,action action,int timeOut) {
		String btname = btnName.toString();
		String xpath = "//*[text()='"+toggleTab+"']/../../..//following-sibling::div//button[@title='"+btname+"']";
		WebElement ele = FindElement(driver, xpath,toggleTab+" >> "+btname, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btname);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btname);
		return ele;
	}
	
	
	public WebElement getFundNameAtToggle(String projectName,PageName pageName,String fundName,action action,int timeOut) {
		String xpath = "//*[@data-label='Fund: ']//*[text()='"+fundName+"']";
		WebElement ele = FindElement(driver, xpath,fundName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Fund Name : "+fundName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Fund Name : "+fundName);
		return ele;
	}
	
	
	public WebElement getFundNameAtToggleToolTip(String projectName,PageName pageName,String fundName,action action,int timeOut) {
		String xpath = "//*[@data-label='Fund: ']//*[text()='"+fundName+"']/..";
		WebElement ele = FindElement(driver, xpath,fundName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Fund Name : "+fundName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Fund Name : "+fundName);
		return ele;
	}
	
	public WebElement getLegalEntityAtToggle(String projectName,PageName pageName,String entityName,action action,int timeOut) {
		String xpath = "//*[@data-label='Legal Entity: ']//*[text()='"+entityName+"']";
		WebElement ele = FindElement(driver, xpath,entityName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Fund Name : "+entityName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Fund Name : "+entityName);
		return ele;
	}
	
	public WebElement getInlineOrLockedAtToggle(String projectName,PageName pageName,String name,action action,int timeOut) {
		String xpath = "//a[text()='Fund Investments']/../../../../../..//*[@title='"+name+"']/../following-sibling::span/button";
		WebElement ele = FindElement(driver, xpath,name, action, timeOut);
		scrollDownThroughWebelement(driver, ele, name);
		return ele;
	}
	
	public boolean createAttendee(String projectName,String marketingEvent,String[][] requestInfo,action action,int timeOut) {
		boolean flag=true;
		String label;
		String value;
		String xpath="";
		WebElement ele;
		
		if(clickUsingJavaScript(driver, getNewAttendee(projectName, timeOut), "New Attendee Button")) {
			log(LogStatus.INFO,"click on New Attendee Button",YesNo.Yes);

			for (String[] reuestData : requestInfo) {
				label=reuestData[0].replace("_", " ");
				value=reuestData[1];

				if(PageLabel.Attendee_Staff.toString().equals(reuestData[0])){
					if (sendKeys(driver, getAttendeeStaffTextBoxe(projectName, timeOut), value, label+" : "+value,action)) {
						ThreadSleep(1000);
						log(LogStatus.INFO,"Able to send "+value+" to label : "+label,YesNo.Yes);
						if (click(driver,FindElement(driver,"//span[contains(@class,'listbox')]//*[@title='"+value+"']","ATTENDEE Name List", action, 30),
								value + "   :   Company Name", action)) {
							log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);
						} else {
							sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
							log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
							flag=false;
						}

					} else {
						sa.assertTrue(false,"Not Able to send "+value+" to label : "+label);
						log(LogStatus.SKIP,"Not Able to send "+value+" to label : "+label,YesNo.Yes);
						return false;
					}
				}else if(PageLabel.Status.toString().equals(reuestData[0])) {

					if (click(driver, getStatus(projectName, timeOut), label, action)) {
						ThreadSleep(2000);
						log(LogStatus.INFO,"Able to Click on "+label,YesNo.No);

						xpath="//span[@title='"+value+"']";
						ele = FindElement(driver,xpath, value,action, timeOut);
						ThreadSleep(2000);
						if (click(driver, ele, value, action)) {
							log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);	
						} else {
							sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
							log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
							flag=false;
						}

					} else {
						sa.assertTrue(false,"Not Able to Click on "+label);
						log(LogStatus.SKIP,"Not Able to Click on "+label,YesNo.Yes);
						flag=false;
					}

				}
				else if(AttendeeLabels.Notes.toString().equals(reuestData[0])) {
					AttendeePage ap = new AttendeePage(driver);
					if (sendKeys(driver, ap.labelTextBox(projectName,reuestData[0], 60), value, "notes",
							action.BOOLEAN)) {

					} else {
						sa.assertTrue(false,"not able to enter text to "+label);
						log(LogStatus.SKIP,"not able to enter text to "+label,YesNo.Yes);
						flag=false;
					}
				}
			}

			xpath="//*[text()='Marketing Event']/following-sibling::div//input[@placeholder='"+marketingEvent+"']";
			ele = FindElement(driver, xpath, marketingEvent, action, timeOut);
			if (ele!=null) {
				log(LogStatus.INFO,"marketingEvent Label prefilled with value : "+marketingEvent,YesNo.No);	
			} else {
				sa.assertTrue(false,"marketingEvent Label not prefilled with value : "+marketingEvent);
				log(LogStatus.SKIP,"marketingEvent Label not prefilled with value : "+marketingEvent,YesNo.Yes);
				flag=false;
			}

			if (click(driver, getCustomTabSaveBtn(projectName,timeOut), "save button", action)) {
				appLog.info("clicked on save button");

			} else {
				sa.assertTrue(false,"Not Able to Click on save button so cannot create request");
				log(LogStatus.SKIP,"Not Able to Click on save button so cannot create request",YesNo.Yes);
				flag=false;
			}


		}else {
			sa.assertTrue(false,"Not able to click on New Attendee button");
			log(LogStatus.SKIP,"Not able to click on New Attendee button",YesNo.Yes);
			flag=false;

		}
		return flag;
	}
	
	public WebElement getRadioButtonforRecordTypeME(String recordType,int timeOut) {
		String xpath="//div[@id='containerBodyID']//span[text()='"+recordType+"']/..//input";
		WebElement ele = null;
		ele=FindElement(driver, xpath, "radio button of record type "+recordType, action.SCROLLANDBOOLEAN,timeOut);
		return isDisplayed(driver,ele,"visibility",timeOut,"radio button of record type "+recordType);
	}
}
