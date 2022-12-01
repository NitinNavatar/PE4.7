package com.navatar.pageObjects;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;

public class TaskPage extends BasePageBusinessLayer {

	public TaskPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//button[@title='Refresh']")
	private WebElement refreshIcon;

	/**
	 * @return the taskRayFrame
	 */
	public WebElement getRefreshIcon(String projectName, int timeOut) {
		return isDisplayed(driver, refreshIcon, "Visibility", timeOut, "Refresh Icon");
	}

	@FindBy(xpath = "(//*[@role='dialog']//button)[1]")
	private WebElement taskPagePopUp;

	/**
	 * @return the taskRayFrame
	 */
	public WebElement getTaskPagePopUp(String projectName, int timeOut) {
		return isDisplayed(driver, taskPagePopUp, "Visibility", timeOut, "Task Page Pop Up");
	}

	/**
	 * @return the taskRayFrame
	 */
	public WebElement getTaskNameLinkInSideMMenu(String projectName, String taskName, int timeOut) {
		WebElement ele = getTaskPagePopUp(projectName, 10);
		if (ele != null) {
			click(driver, ele, "Task Page Pop Up", action.BOOLEAN);
		}

		String xpath = "//div[@class='oneConsoleObjectHome']//div//span[text()='" + taskName + "']";
		ele = FindElement(driver, xpath, taskName, action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, taskName);
	}

	@FindBy(xpath = "//iframe[@title='accessibility title']")
	private WebElement taskPageFrame;

	/**
	 * @return the taskPageFrame
	 */
	public WebElement getTaskPageFrame(String projectName, int timeOut) {
		return isDisplayed(driver, taskPageFrame, "Visibility", timeOut, "task Page Frame");
	}

	@FindBy(xpath = "//div[@id='popupOpenId']//h2")
	private WebElement taskPoUpEditHeader;

	/**
	 * @return the taskPoUpEditHeader
	 */
	public WebElement getTaskPoUpEditHeader(String projectName, int timeOut) {
		return isDisplayed(driver, taskPoUpEditHeader, "Visibility", timeOut, "task PoUp EditHeader");
	}

	@FindBy(xpath = "//label[text()='Related Contacts']//following-sibling::div")
	private WebElement relatedContactsLabel;

	/**
	 * @return the taskPoUpEditHeader
	 */
	public WebElement getRelatedContactsLabel(String projectName, int timeOut) {
		return relatedContactsLabel;
		// return isDisplayed(driver, relatedContactsLabel, "Visibility", timeOut,
		// "Related Contact Label");
	}

	@FindBy(xpath = "//span[text()='Comments']/..//following-sibling::div")
	private WebElement commentsLabel;

	/**
	 * @return the taskPoUpEditHeader
	 */
	public WebElement getCommentsLabel(String projectName, int timeOut) {
		return relatedContactsLabel;
		// return isDisplayed(driver, relatedContactsLabel, "Visibility", timeOut,
		// "Related Contact Label");
	}

	@FindBy(xpath = "//div[contains(@class,'hint')]//ul[contains(@class,'error')]")
	private WebElement DaysErrorMsg;

	/**
	 * @return the taskPoUpEditHeader
	 */
	public WebElement get14DaysErrorMsg(String projectName, int timeOut) {

		return isDisplayed(driver, DaysErrorMsg, "Visibility", timeOut, "14DaysErrorMsg");
	}

	@FindBy(xpath = "//div[text()='Edit Comments']/ancestor::a")
	private WebElement editCommentsButton;

	public WebElement editCommentsButton(int timeOut) {

		return isDisplayed(driver, editCommentsButton, "Visibility", timeOut, "editCommentsButton");
	}

	@FindBy(xpath = "//span[@id='quickTextKeyboardTip']/following-sibling::textarea")
	private WebElement commentTextArea;

	public WebElement commentTextArea(int timeOut) {

		return isDisplayed(driver, commentTextArea, "Visibility", timeOut, "commentTextArea");
	}

	@FindBy(xpath = "//span[text()='Cancel']/ancestor::button[contains(@class,'uiButton--default')]")
	private WebElement commentTextAreaCancelButton;

	public WebElement commentTextAreaCancelButton(int timeOut) {

		return isDisplayed(driver, commentTextAreaCancelButton, "Visibility", timeOut, "commentTextAreaCancelButton");
	}

	@FindBy(xpath = "//span[text()='Save']/ancestor::button[contains(@class,'slds-button_brand')]")
	private WebElement commentTextAreaSaveButton;

	public WebElement commentTextAreaSaveButton(int timeOut) {

		return isDisplayed(driver, commentTextAreaSaveButton, "Visibility", timeOut, "commentTextAreaSaveButton");
	}

	@FindBy(xpath = "//a[contains(@title,' more actions')]")
	private WebElement downArrowButton;

	public WebElement downArrowButton(int timeOut) {

		return isDisplayed(driver, downArrowButton, "Visibility", timeOut, "downArrowButton");
	}

	@FindBy(xpath = "//span[text()='Comments']/parent::div/following-sibling::div/span/span")
	private WebElement commentsLabelValueInTaskDetailPage;

	public WebElement commentsLabelValueInTaskDetailPage(int timeOut) {

		return isDisplayed(driver, commentsLabelValueInTaskDetailPage, "Visibility", timeOut,
				"commentsLabelValueInTaskDetailPage");
	}

	public WebElement buttonInTheDownArrowList(String buttonName, int timeOut) {
		String xpath = "//div[contains(@class,'actionMenu')]//li/a/div[text()='" + buttonName + "']/parent::a";
		WebElement ele = FindElement(driver, xpath, "buttonInTheDownArrowList: " + buttonName, action.SCROLLANDBOOLEAN,
				timeOut);
		try {
			return isDisplayed(driver, ele, "Visibility", timeOut, "buttonInTheDownArrowList: " + buttonName);

		} catch (StaleElementReferenceException e) {
			return isDisplayed(driver, ele, "Visibility", timeOut, "buttonInTheDownArrowList: " + buttonName);
		}
	}

	@FindBy(xpath = "//div//span[text()='Delete']/parent::button")
	private WebElement taskDeleteConfirmButton;

	public WebElement taskDeleteConfirmButton(int timeOut) {

		return isDisplayed(driver, taskDeleteConfirmButton, "Visibility", timeOut,
				"taskDeleteConfirmButton");
	}
	
	@FindBy(xpath = "//div[contains(@class,'toastContent')]")
	private WebElement taskDeletedMsg;

	public WebElement taskDeletedMsg(int timeOut) {

		return isDisplayed(driver, taskDeletedMsg, "Visibility", timeOut,
				"taskDeletedMsg");
	}
	
	
	
}
