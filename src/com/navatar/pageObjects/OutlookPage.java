package com.navatar.pageObjects;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;

import java.util.List;

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

	@FindBy(xpath = "//span[text()='New email']/ancestor::button")
	private WebElement newEmailButton;

	public WebElement getNewEmailButton(int timeOut) {
		return isDisplayed(driver, newEmailButton, "Visibility", timeOut, "new email button");

	}

	@FindBy(xpath = "//input[@placeholder='Add a title']")
	private WebElement eventTitleInputBox;

	public WebElement eventTitleInputBox(int timeOut) {
		return isDisplayed(driver, eventTitleInputBox, "Visibility", timeOut, "eventTitleInputBox");

	}

	@FindBy(xpath = "//div[@aria-label='Invite attendees']")
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

	@FindBy(xpath = "//input[@name='passwd']")
	private WebElement loginPasswordInputBox;

	public WebElement loginPasswordInputBox(int timeOut) {
		return isDisplayed(driver, loginPasswordInputBox, "Visibility", timeOut, "loginPasswordInputBox");

	}

	@FindBy(xpath = "//input[@value='Sign in']")
	private WebElement loginSignInButton;

	public WebElement loginSignInButton(int timeOut) {
		return isDisplayed(driver, loginSignInButton, "Visibility", timeOut, "loginSignInButton");

	}

	@FindBy(xpath = "//input[@value='Yes']")
	private WebElement loginYesButton;

	public WebElement loginYesButton(int timeOut) {
		return isDisplayed(driver, loginYesButton, "Visibility", timeOut, "loginYesButton");

	}

	@FindBy(xpath = "//button[@title='Jump to previous or next date range']")
	private WebElement calendarMonthYearDropDownButton;

	public WebElement calendarMonthYearDropDownButton(int timeOut) {
		return isDisplayed(driver, calendarMonthYearDropDownButton, "Visibility", timeOut,
				"calendarMonthYearDropDownButton");

	}

	@FindBy(xpath = "//button[contains(@aria-label,'select to change the year')]")
	private WebElement calendarMonthYearDropDownsYearButton;

	public WebElement calendarMonthYearDropDownsYearButton(int timeOut) {
		return isDisplayed(driver, calendarMonthYearDropDownsYearButton, "Visibility", timeOut,
				"calendarMonthYearDropDownsYearButton");

	}

	public List<WebElement> listOfMonthsInCalendar() {
		return FindElements(driver, "//div[contains(@class,'gridContainer')]//button", "listOfMonthsInCalendar");
	}

	@FindBy(xpath = "//button[contains(@title,'Go to previous year')]")
	private WebElement calendarMonthYearDropDownsYearPreviousButton;

	public WebElement calendarMonthYearDropDownsYearPreviousButton(int timeOut) {
		return isDisplayed(driver, calendarMonthYearDropDownsYearPreviousButton, "Visibility", timeOut,
				"calendarMonthYearDropDownsYearPreviousButton");

	}

	@FindBy(xpath = "//button[contains(@title,'Go to next year')]")
	private WebElement calendarMonthYearDropDownsYearForwardButton;

	public WebElement calendarMonthYearDropDownsYearForwardButton(int timeOut) {
		return isDisplayed(driver, calendarMonthYearDropDownsYearForwardButton, "Visibility", timeOut,
				"calendarMonthYearDropDownsYearForwardButton");

	}

	public List<WebElement> calendarViewDateList() {
		return FindElements(driver,
				"//div[contains(@aria-label,'calendar view')]//div[contains(@class,'PQHVk')]//div[contains(@id,'header')]",
				"calendarViewDateList");
	}

	public List<WebElement> createdEventsNameAccToDateSelected() {
		return FindElements(driver, "//div[@class='zw21U CT1Ra']", "createdEventsNameAccToDateSelected");
	}

	@FindBy(xpath = "//span[text()='Cancel']/ancestor::button")
	private WebElement eventCancelButton;

	public WebElement eventCancelButton(int timeOut) {
		return isDisplayed(driver, eventCancelButton, "Visibility", timeOut, "eventCancelButton");

	}

	@FindBy(xpath = "//span[text()='This event']/ancestor::button")
	private WebElement thisEventDropDownValue;

	public WebElement thisEventDropDownValue(int timeOut) {
		return isDisplayed(driver, thisEventDropDownValue, "Visibility", timeOut, "thisEventDropDownValue");

	}

	@FindBy(xpath = "//span[text()='Edit']/ancestor::button")
	private WebElement eventEditButton;

	public WebElement eventEditButton(int timeOut) {
		return isDisplayed(driver, eventEditButton, "Visibility", timeOut, "eventEditButton");

	}

	public WebElement crossButtonOfAttendee(String attendeeName, int timeOut) {
		String xpath = "//span[text()='" + attendeeName
				+ "']/ancestor::div[contains(@class,'personaInfoWrapper')]/following-sibling::button";
		try {
			return FindElement(driver, xpath, "Attendee Header: " + attendeeName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Attendee Header: " + attendeeName, action.SCROLLANDBOOLEAN, timeOut);
		}
	}

	@FindBy(xpath = "//button[@aria-label='More mail actions']")
	private WebElement moreMailActionsButton;

	public WebElement moreMailActionsButton(int timeOut) {
		return isDisplayed(driver, moreMailActionsButton, "Visibility", timeOut, "moreMailActionsButton");

	}

	@FindBy(xpath = "//button[@name='Revenue Grid for Salesforce CRM']")
	private WebElement revenueGridButton;

	public WebElement getRevenueGridButton(int timeOut) {
		return isDisplayed(driver, revenueGridButton, "Visibility", timeOut, "revenueGridButton");

	}

	@FindBy(xpath = "//button[@name='Open Revenue Grid']")
	private WebElement openRevenueGridButton;

	public WebElement getOpenRevenueGridButton(int timeOut) {
		return isDisplayed(driver, openRevenueGridButton, "Visibility", timeOut, "openRevenueGridButton");

	}

	@FindBy(xpath = "//button[@title='Open the main menu']")
	private WebElement revenueGridMainMenuButton;

	public WebElement getRevenueGridMainMenuButton(int timeOut) {
		return isDisplayed(driver, revenueGridMainMenuButton, "Visibility", timeOut, "revenueGridMainMenuButton");

	}
	
	@FindBy(xpath = "//div[@title='Open sync settings in browser']")
	private WebElement syncSettingMenu;

	public WebElement getSyncSettingButton(int timeOut) {
		return isDisplayed(driver, syncSettingMenu, "Visibility", timeOut, "sync setting button");

	}
	
	@FindBy(xpath = "//button[@title='Force Sync']")
	private WebElement forceSyncButton;

	public WebElement getForceSyncButton(int timeOut) {
		return isDisplayed(driver, forceSyncButton, "Visibility", timeOut, "force Sync button");

	}
	
	@FindBy(xpath = "//div[@class='ajs-message ajs-error ajs-visible']")
	private WebElement forceSyncSuccessErrorMessage;

	public WebElement getForceSyncSuccessErrorMessage(int timeOut) {
		return isDisplayed(driver, forceSyncSuccessErrorMessage, "Visibility", timeOut, "force Sync success/error message");

	}
	
	@FindBy(xpath = "//h3[@title='Last Session']/..//span")
	private WebElement forceSyncLastSession;

	public WebElement getForceSyncLastSession(int timeOut) {
		return isDisplayed(driver, forceSyncLastSession, "Visibility", timeOut, "force Sync Last session");

	}
	
	@FindBy(xpath = "//div[@aria-label='To']")
	private WebElement toInputBox;

	public WebElement getToInputBox(int timeOut) {
		return isDisplayed(driver, toInputBox, "Visibility", timeOut, "To input box");

	}
	
	
	@FindBy(xpath = "//div[@aria-label='Cc']")
	private WebElement ccInputBox;

	public WebElement getCCInputBox(int timeOut) {
		return isDisplayed(driver, ccInputBox, "Visibility", timeOut, "CC input box");

	}
	
	@FindBy(xpath = "//div[@aria-label='Bcc']")
	private WebElement bccLinkBox;

	public WebElement getBCCLink(int timeOut) {
		return isDisplayed(driver, bccLinkBox, "Visibility", timeOut, "BCC link");

	}

	@FindBy(xpath = "//div[@aria-label='Bcc']")
	private WebElement bccInputBox;

	public WebElement getBCCInputBox(int timeOut) {
		return isDisplayed(driver, bccInputBox, "Visibility", timeOut, "BCC input box");

	}
	
	@FindBy(xpath = "//input[@aria-label='Add a subject']")
	private WebElement subjectInputBox;

	public WebElement getSubjectInputBox(int timeOut) {
		return isDisplayed(driver, subjectInputBox, "Visibility", timeOut, "subject input box");

	}
	
	@FindBy(xpath = "//div[@class='elementToProof']")
	private WebElement messageInputBox;

	public WebElement getMailMessageInputBox(int timeOut) {
		return isDisplayed(driver, messageInputBox, "Visibility", timeOut, "message input box");

	}
	
	@FindBy(xpath = "//button[@aria-label='Send']")
	private WebElement sendButton;

	public WebElement getSendButton(int timeOut) {
		return isDisplayed(driver, sendButton, "Visibility", timeOut, "send Button ");

	}

	public WebElement sideNavButtonUnderFolderSection(String sideNavButtonName, int timeOut) {
		String xpath = "//span[text()='Folders']/parent::div/parent::div/following-sibling::div//span[text()=\""
				+ sideNavButtonName + "\"]/ancestor::div[@title=\"" + sideNavButtonName + "\"]";
		try {
			return FindElement(driver, xpath, "Nav Header Under Folder: " + sideNavButtonName, action.SCROLLANDBOOLEAN,
					timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Nav Header Under Folder: " + sideNavButtonName, action.SCROLLANDBOOLEAN,
					timeOut);
		}
	}

	public WebElement sideNavSectionButton(String sideNavSectionName, int timeOut) {
		String xpath = "//span[text()='" + sideNavSectionName + "']/preceding-sibling::button";
		try {
			return FindElement(driver, xpath, "Nav Section Button: " + sideNavSectionName, action.SCROLLANDBOOLEAN,
					timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Nav Section Button: " + sideNavSectionName, action.SCROLLANDBOOLEAN,
					timeOut);
		}
	}

	public WebElement sideNavSectionExpandedOrNot(String sideNavSectionName, int timeOut) {
		String xpath = "//span[text()=\"" + sideNavSectionName + "\"]/parent::div[@aria-expanded=\"true\"]";
		try {
			return FindElement(driver, xpath, "Nav Section Expanded: " + sideNavSectionName, action.SCROLLANDBOOLEAN,
					timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Nav Section Expanded: " + sideNavSectionName, action.SCROLLANDBOOLEAN,
					timeOut);
		}
	}

	public WebElement emailSectionHeading(String emailsSectionName, int timeOut) {
		String xpath = "//span[text()=\"" + emailsSectionName + "\"]/parent::div[@role=\"heading\"]";
		try {
			return FindElement(driver, xpath, "Email Section Name: " + emailsSectionName, action.SCROLLANDBOOLEAN,
					timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Email Section Name: " + emailsSectionName, action.SCROLLANDBOOLEAN,
					timeOut);
		}
	}

	public List<WebElement> listOfSubjectsOfSentBoxEmails() {
		return FindElements(driver,
				"//div[@aria-label = \"Message list\"]//div[@role=\"region\"]/div/div/div/div//div[contains(@class,\"ZfoST\")]/following-sibling::div/div[2]/div[contains(@class,\"IjzWp\")]/span",
				"listOfSubjectsOfSentBoxEmails");
	}

	public WebElement inviteAttendeeSuggestionBoxName(String attendeeName, int timeOut) {
		String xpath = "//span[text()='" + attendeeName + "']/ancestor::button";
		try {
			return FindElement(driver, xpath, "Invitee Suggestion Name: " + attendeeName, action.SCROLLANDBOOLEAN,
					timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Invitee Suggestion Name: " + attendeeName, action.SCROLLANDBOOLEAN,
					timeOut);
		}
	}

	@FindBy(xpath = "//label[text()='All day']/parent::div//button")
	private WebElement allDayToggleButton;

	public WebElement allDayToggleButton(int timeOut) {
		return isDisplayed(driver, allDayToggleButton, "Visibility", timeOut, "allDayToggleButton");

	}

	@FindBy(xpath = "//input[@aria-label='Search']")
	private WebElement globalSearchButton;

	public WebElement globalSearchButton(int timeOut) {
		return isDisplayed(driver, globalSearchButton, "Visibility", timeOut, "globalSearchButton");

	}

	@FindBy(xpath = "//div[@class='ms-Dropdown-container L2ulM']")
	private WebElement globalSearchCategoryButton;

	public WebElement globalSearchCategoryButton(int timeOut) {
		return isDisplayed(driver, globalSearchCategoryButton, "Visibility", timeOut, "globalSearchCategoryButton");

	}

	public WebElement globalSearchCategoryDropDownButton(String buttonName, int timeOut) {
		String xpath = "//span[text()='" + buttonName + "']/ancestor::button";
		try {
			return FindElement(driver, xpath, "Button Name: " + buttonName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Button Name: " + buttonName, action.SCROLLANDBOOLEAN, timeOut);
		}
	}

}
