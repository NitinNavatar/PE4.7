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

	@FindBy(xpath = "//span[text()='New mail']/ancestor::button")
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
	
	public WebElement crossButtonOfAttendeeInEditOutLook(String attendeeName, int timeOut) {
		String xpath = "//div[@aria-label=\"Invite attendees\"]//span[text()=\""+attendeeName+"\"]/parent::span//span[@id=\"removeButton\"]";
		try {
			return FindElement(driver, xpath, "Attendee Header: " + attendeeName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Attendee Header: " + attendeeName, action.SCROLLANDBOOLEAN, timeOut);
		}
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
	@FindBy(xpath = "//span[text()='Event canceled']")
	private WebElement eventCanceledMsg;

	public WebElement eventCanceledMsg(int timeOut) {
		return isDisplayed(driver, eventCanceledMsg, "Visibility", timeOut, "eventCanceledMsg");

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

	@FindBy(xpath = "//span[text()='This event']/ancestor::button")
	private WebElement thisEventDropDownValue;

	public WebElement thisEventDropDownValue(int timeOut) {
		return isDisplayed(driver, thisEventDropDownValue, "Visibility", timeOut, "thisEventDropDownValue");

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

	@FindBy(xpath = "//button[@aria-label='Revenue Grid for Salesforce CRM']")
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
	
	
	@FindBy(xpath = "//iframe[@title=\"Office Add-in Revenue Grid for Salesforce CRM\"]")
	private WebElement revenueGridFrame;

	public WebElement revenueGridFrame(int timeOut) {
		return isDisplayed(driver, revenueGridFrame, "Visibility", timeOut, "revenueGridFrame");

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
	
	@FindBy(xpath = "//div[@class=\"ajs-message ajs-error ajs-visible\" or @class=\"ajs-message ajs-success ajs-visible\"]")
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

	@FindBy(xpath = "//button[contains(@class,'monthAndYear')]/span")
	private WebElement actualMonth;

	public WebElement getActualMonth(int timeOut) {
		return isDisplayed(driver, actualMonth, "Visibility", timeOut, "actualMonth");

	}

	@FindBy(xpath = "//div[contains(@class,'monthComponents')]//i[@data-icon-name='Down']")
	private WebElement downArrow;

	public WebElement getdownArrowValue(int timeOut) {
		return isDisplayed(driver, downArrow, "Visibility", timeOut, "downArrow");

	}

	@FindBy(xpath = "//div[contains(@class,'monthComponents')]//i[@data-icon-name='Up']")
	private WebElement upArrow;

	public WebElement getUpArrowValue(int timeOut) {
		return isDisplayed(driver, upArrow, "Visibility", timeOut, "upArrow");

	}

	@FindBy(xpath = "//div[@class='pQPSJ']//div[contains(@class,'CT1Ra')]")
	private List<WebElement> listOfEventNames;

	public List<WebElement> getlistOfEventNames() {
		return listOfEventNames;

	}

	@FindBy(xpath = "//button[@name='Edit']")
	private WebElement eventEditButton;

	public WebElement getEventEditButton(int timeOut) {
		return isDisplayed(driver, eventEditButton, "Visibility", timeOut, "Event edit button");

	}

	@FindBy(xpath = "//div[@title='Calendar']//child::i")
	private WebElement getOutlookCalenderIcon;

	public WebElement getOutlookCalenderIcon(int timeOut) {
		return isDisplayed(driver, getOutlookCalenderIcon, "Visibility", timeOut, "calenderIcon");

	}

	@FindBy(xpath = "//button[@name='Cancel']")
	private WebElement eventCancelButton;

	public WebElement getEventCancelButton(int timeOut) {
		return isDisplayed(driver, eventCancelButton, "Visibility", timeOut, "Event Cancel button");

	}
	
	@FindBy(xpath = "//span[text()='Update sent']")
	private WebElement eventUpdateMsg;

	public WebElement eventUpdateMsg(int timeOut) {
		return isDisplayed(driver, eventUpdateMsg, "Visibility", timeOut, "eventUpdateMsg");

	}
	

	@FindBy(xpath = "//span[contains(@class,'ms-Dropdown-title')]/span[contains(text(),'repeat')]")
	private WebElement recurringBtn;

	public WebElement getRecurringBtn(int timeOut) {
		return isDisplayed(driver, recurringBtn, "Visibility", timeOut, "recurring button");
	}
	
	
	public WebElement getRecurringOption(String recurringOptionName, int timeOut) {
		String xpath = "//div[@aria-label='Repeat:']/button//span[text()='"+recurringOptionName+"']";
		try {
			return FindElement(driver, xpath, "Button Name: " + recurringOptionName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Button Name: " + recurringOptionName, action.SCROLLANDBOOLEAN, timeOut);
		}
	}
	
	
	@FindBy(xpath = "//span[text()='Repeat']/../..//span[text()='Save']")
	private WebElement saveButtonOnRepeatPopup;

	public WebElement getSaveButtonOnRepeatPopup(int timeOut) {
		return isDisplayed(driver, saveButtonOnRepeatPopup, "Visibility", timeOut, "save button on Repeate popup");
	}
	
	
	@FindBy(xpath = "//div[@title='Sent Items']//span[text()='Sent Items']")
	private WebElement sendItemButton;

	public WebElement getSendItemButton(int timeOut) {
		return isDisplayed(driver, sendItemButton, "Visibility", timeOut, "send item button");
	}
	
	public WebElement getSendItmSubject(String subjectName, int timeOut) {
		String xpath = "//div[@class='S2NDX']//span[text()='"+subjectName+"']";
		try {
			return FindElement(driver, xpath, "Button Name: " + subjectName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Button Name: " + subjectName, action.SCROLLANDBOOLEAN, timeOut);
		}
	}
	
	public WebElement getMoreIcon(int timeOut) {
		String xpath = "//button[@aria-label='More mail actions']";
		try {
			return FindElement(driver, xpath, "More icon", action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "More icon", action.SCROLLANDBOOLEAN, timeOut);
		}
	}
	
	@FindBy(xpath = "//button[@data-name='button-save' and text()='Save email']")
	private WebElement saveEmailButtonOnRG;

	public WebElement getSaveEmailButtonOnRG(int timeOut) {
		return isDisplayed(driver, saveEmailButtonOnRG, "Visibility", timeOut, "Save email button on RG");
	}
	
	@FindBy(xpath = "//div[text()='Click to link more records']")
	private WebElement linkedRecordPlaceholder;

	public WebElement getLinkedRecordPlaceholder(int timeOut) {
		return isDisplayed(driver, linkedRecordPlaceholder, "Visibility", timeOut, "linked record placeholder");
	}
	
	
	@FindBy(xpath = "//span[text()='Linked records']/ancestor::div[@data-name='group-title-container']/following-sibling::div//input[@role='combobox']")
	private WebElement linkedRecordInput;

	public WebElement getLinkedRecordInput(int timeOut) {
		return isDisplayed(driver, linkedRecordInput, "Visibility", timeOut, "linked record input type");
	}
	
	
	public WebElement getLinkedRecordSuggestion(String recordName, int timeOut) {
		String xpath = "//span[text()='Linked records']/ancestor::div[@data-name='group-title-container']/following-sibling::div//span[text()='"+recordName+"']";
		try {
			return FindElement(driver,xpath, "record name "+recordName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver,xpath, "record name "+recordName, action.SCROLLANDBOOLEAN, timeOut);
		}
	}
	
	@FindBy(xpath = "//button[@class='a-head__navigation-button' and text()='Save']")
	private WebElement saveButtonOnRG;

	public WebElement getSaveButtonOnRG(int timeOut) {
		return isDisplayed(driver, saveButtonOnRG, "Visibility", timeOut, "save button");
	}
	
	@FindBy(xpath = "//button[@data-name='button-save' and text()='Email is saved']")
	private WebElement emailSaveConfirmationOnRG;

	public WebElement getEmailSaveConfirmationOnRG(int timeOut) {
		return isDisplayed(driver, emailSaveConfirmationOnRG, "Visibility", timeOut, "email save confirmation");
	}
	
	
}
