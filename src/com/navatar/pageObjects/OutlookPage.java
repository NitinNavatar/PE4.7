package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.navatar.generic.CommonLib.*;

public class OutlookPage extends BasePageBusinessLayer {

	public OutlookPage(WebDriver driver) {
		super(driver);

	}

	@FindBy(xpath = "//div[@title='Calendar']/button")
	private WebElement calendarButton;

	public WebElement calendarButton(int timeOut) {
		return isDisplayed(driver, calendarButton, "Visibility", timeOut, "calendarButton");

	}

	@FindBy(xpath = "//span[text()='New event']/ancestor::button")
	private WebElement newEventButton;

	public WebElement newEventButton(int timeOut) {
		return isDisplayed(driver, newEventButton, "Visibility", timeOut, "newEventButton");

	}

	@FindBy(xpath = "//input[@placeholder='Add a title']")
	private WebElement eventTitleInputBox;

	public WebElement eventTitleInputBox(int timeOut) {
		return isDisplayed(driver, eventTitleInputBox, "Visibility", timeOut, "eventTitleInputBox");

	}

	@FindBy(xpath = "//input[@aria-label='Invite attendees']")
	private WebElement inviteAttendeesInputBox;

	public WebElement inviteAttendeesInputBox(int timeOut) {
		return isDisplayed(driver, inviteAttendeesInputBox, "Visibility", timeOut, "inviteAttendeesInputBox");

	}

	@FindBy(xpath = "//input[@aria-label='Start date']")
	private WebElement startDateInputBox;

	public WebElement startDateInputBox(int timeOut) {
		return isDisplayed(driver, startDateInputBox, "Visibility", timeOut, "startDateInputBox");

	}

	@FindBy(xpath = "//input[@aria-label='End date']")
	private WebElement endDateInputBox;

	public WebElement endDateInputBox(int timeOut) {
		return isDisplayed(driver, endDateInputBox, "Visibility", timeOut, "endDateInputBox");

	}

	@FindBy(xpath = "//input[@aria-label='Start time']")
	private WebElement startTimeInputBox;

	public WebElement startTimeInputBox(int timeOut) {
		return isDisplayed(driver, startTimeInputBox, "Visibility", timeOut, "startTimeInputBox");

	}

	@FindBy(xpath = "//input[@aria-label='End time']")
	private WebElement endTimeInputBox;

	public WebElement endTimeInputBox(int timeOut) {
		return isDisplayed(driver, endTimeInputBox, "Visibility", timeOut, "endTimeInputBox");

	}

	@FindBy(xpath = "//div[contains(@aria-label,'Add a description or attach documents')]")
	private WebElement newEventDescriptionBox;

	public WebElement newEventDescriptionBox(int timeOut) {
		return isDisplayed(driver, newEventDescriptionBox, "Visibility", timeOut, "newEventDescriptionBox");

	}

	@FindBy(xpath = "//span[text()='Send']/ancestor::button")
	private WebElement eventSendButton;

	public WebElement eventSendButton(int timeOut) {
		return isDisplayed(driver, eventSendButton, "Visibility", timeOut, "eventSendButton");

	}

	@FindBy(xpath = "//div[@title='Email']/button")
	private WebElement emailNavigationButton;

	public WebElement emailNavigationButton(int timeOut) {
		return isDisplayed(driver, emailNavigationButton, "Visibility", timeOut, "emailNavigationButton");

	}
	
	@FindBy(xpath = "(//div[@id='groupHeaderToday']//..//div[@class='jA_gm'])[1]")
	private WebElement todaysFirstSentEmail;

	public WebElement todaysFirstSentEmail(int timeOut) {
		return isDisplayed(driver, todaysFirstSentEmail, "Visibility", timeOut, "todaysFirstSentEmail");

	}
	
	@FindBy(xpath = "//span[text()='Event created']")
	private WebElement eventCreatedMsg;

	public WebElement eventCreatedMsg(int timeOut) {
		return isDisplayed(driver, eventCreatedMsg, "Visibility", timeOut, "eventCreatedMsg");

	}
	
	@FindBy(xpath = "//input[@name='loginfmt']")
	private WebElement loginEmailInputBox;

	public WebElement loginEmailInputBox(int timeOut) {
		return isDisplayed(driver, loginEmailInputBox, "Visibility", timeOut, "loginEmailInputBox");

	}
	
	@FindBy(xpath = "//input[@value='Next']")
	private WebElement loginNextButton;

	public WebElement loginNextButton(int timeOut) {
		return isDisplayed(driver, loginNextButton, "Visibility", timeOut, "loginNextButton");

	}
	
	@FindBy(xpath = "//input[@label='Password']")
	private WebElement loginPasswordInputBox;

	public WebElement loginPasswordInputBox(int timeOut) {
		return isDisplayed(driver, loginPasswordInputBox, "Visibility", timeOut, "loginPasswordInputBox");

	}
	
	@FindBy(xpath = "//button[@id='submitBtn']")
	private WebElement loginSignInButton;

	public WebElement loginSignInButton(int timeOut) {
		return isDisplayed(driver, loginSignInButton, "Visibility", timeOut, "loginSignInButton");

	}
	
	@FindBy(xpath = "//input[@value='Yes']")
	private WebElement loginYesButton;

	public WebElement loginYesButton(int timeOut) {
		return isDisplayed(driver, loginYesButton, "Visibility", timeOut, "loginYesButton");

	}
	

}
