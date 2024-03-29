package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ProjectName;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;

import static com.navatar.generic.CommonLib.*;

import java.util.List;

public class MarketingEventPage extends BasePageBusinessLayer {

	public MarketingEventPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	
	@FindBy(xpath="//*[text()='Organizer']/following-sibling::div//input")
	private WebElement organizer;

	/**
	 * @return the organizer
	 */
	public WebElement getOrganizerName(String projectName,int timeOut) {
		return isDisplayed(driver, organizer, "Visibility", timeOut, "Organizer Name");
	}
	@FindBy(xpath="//*[text()='Organizer']/../following-sibling::div//input[contains(@placeholder,'Search Entities')]")
	private WebElement organizerForCalender;

	/**
	 * @return the organizer
	 */
	public WebElement getOrganizerNameForCalender(String projectName,int timeOut) {
		return isDisplayed(driver, organizerForCalender, "Visibility", timeOut, "Organizer Name");
	}
	@FindBy(xpath="//*[text()='Marketing Event Name']/following-sibling::div//input")
	private WebElement marketingEventTextBox;
	
	/**
	 * @return the marketingEventTextBox
	 */
	public WebElement getMarketingEventTextBox(String projectName,int timeOut) {
		return isDisplayed(driver, marketingEventTextBox, "Visibility", timeOut, "Marketing Name");
		} 
	@FindBy(xpath="//*[text()='Marketing Event Name']/../following-sibling::input")
	private WebElement marketingEventTextBoxForCalender;
	
	/**
	 * @return the marketingEventTextBox
	 */
	public WebElement getMarketingEventTextBoxForCalender(String projectName,int timeOut) {
		return isDisplayed(driver, marketingEventTextBoxForCalender, "Visibility", timeOut, "Marketing Name");
		} 
	@FindBy(xpath="//span[@class='custom-truncate uiOutputText']")
	private WebElement marketingEventHeader;
	
	/**
	 * @return the marketingEventHeader
	 */
	public WebElement getMarketingEventHeader(String projectName,int timeOut) {
	return isDisplayed(driver, marketingEventHeader, "Visibility", timeOut, "Marketing Name Header");
	}
	
	@FindBy(xpath="//*[text()='Attendees']/../../../../../following-sibling::div//*[text()='New']")
	private WebElement newAttendee;

	/**
	 * @return the newAttendee
	 */
	public WebElement getNewAttendee(String projectName,int timeOut) {
		return isDisplayed(driver, newAttendee, "Visibility", timeOut, "New Attendee");
	}
	
	
	@FindBy(xpath="//*[text()='Attendee Staff']/following-sibling::div//input[contains(@placeholder,'Search People')]")
	private WebElement attendeeStaffTextBox;

	/**
	 * @return the organizer
	 */
	public WebElement getAttendeeStaffTextBoxe(String projectName,int timeOut) {
		return isDisplayed(driver, attendeeStaffTextBox, "Visibility", timeOut, "attendee Staff TextBox");
	}
	
	public WebElement getMarketingEventInViewMode(int timeOut,String marketingEvent) {
		return FindElement(driver, "//*[contains(text(),'Marketing Event')]/../..//*[text()='"+marketingEvent+"']", "marketing event text label", action.SCROLLANDBOOLEAN, timeOut);
	}
	
	

	
			
}
