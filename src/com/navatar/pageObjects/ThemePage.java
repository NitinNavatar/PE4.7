package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.isDisplayed;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.action;

public class ThemePage extends BasePageBusinessLayer {

	public ThemePage(WebDriver driver) {
		super(driver);

	}

	@FindBy(xpath = "//button[text()=\"New Theme\"]")
	private WebElement newThemeButton;

	public WebElement newThemeButton(int timeOut) {
		return isDisplayed(driver, newThemeButton, "Visibility", timeOut, "newThemeButton");

	}

	@FindBy(xpath = "//button[text()=\"No\"]")
	private WebElement noButton;

	public WebElement noButton(int timeOut) {
		return isDisplayed(driver, noButton, "Visibility", timeOut, "noButton");

	}

	@FindBy(xpath = "//input[@name=\"Name\"]")
	private WebElement themeNameInputBox;

	public WebElement themeNameInputBox(int timeOut) {
		return isDisplayed(driver, themeNameInputBox, "Visibility", timeOut, "themeNameInputBox");

	}

	@FindBy(xpath = "//textarea[contains(@name,\"Description__c\")]")
	private WebElement themeDescription;

	public WebElement themeDescription(int timeOut) {
		return isDisplayed(driver, themeDescription, "Visibility", timeOut, "themeDescription");

	}

	@FindBy(xpath = "//section[contains(@class,\"slds-fade-in-open\")]//button[text()=\"Save\"]")
	private WebElement saveButton;

	public WebElement saveButton(int timeOut) {
		return isDisplayed(driver, saveButton, "Visibility", timeOut, "saveButton");

	}

	@FindBy(xpath = "//input[@name=\"Search\"]")
	private WebElement themeSearchBox;

	public WebElement themeSearchBox(int timeOut) {
		return isDisplayed(driver, themeSearchBox, "Visibility", timeOut, "themeSearchBox");

	}

	public WebElement recordInTableOfTheme(String recordName, int timeOut) {

		String xpath = "//lightning-primitive-cell-factory//lightning-formatted-url/a[text()=\"" + recordName + "\"]";
		WebElement type = FindElement(driver, xpath, "recordInTableOfTheme", action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, recordName);

	}

	@FindBy(xpath = "//input[contains(@name,'Search')]")
	private WebElement searchIcon;

	/**
	 * @return the searchIcon
	 */
	public WebElement getSearchIcon(int timeOut) {
		return isDisplayed(driver, searchIcon, "Visibility", timeOut, "Search Icon");
	}

	public WebElement plusIconButtonInThemeOfAccount(String accountName, int timeOut) {

		String path = "//span[contains(text(), \"" + accountName
				+ "\")]/ancestor::div[contains(@class,\"slds-size_1-of-1\")]/div//lightning-icon[@data-id=\"Account\"][1]";
		return FindElement(driver, path, "plusIconButtonInThemeOfAccount", action.BOOLEAN, timeOut);
	}

	public WebElement addToThemePopUpSearchBox(int timeOut) {
		return isDisplayed(driver, addToThemePopUpSearchBox, "Visibility", timeOut, "addToThemePopUpSearchBox");
	}

	@FindBy(xpath = "//input[@placeholder=\"Search...\"]")
	private WebElement addToThemePopUpSearchBox;

	public WebElement addToThemePopUpSearchBoxDropDownValue(String accountName, int timeOut) {

		String path = "//span[text()=\"" + accountName + "\"]/parent::span/parent::div";
		return FindElement(driver, path, "addToThemePopUpSearchBoxDropDownValue", action.BOOLEAN, timeOut);
	}

	public WebElement addToThemePopUpSaveButton(int timeOut) {
		return isDisplayed(driver, addToThemePopUpSaveButton, "Visibility", timeOut, "addToThemePopUpSaveButton");
	}

	@FindBy(xpath = "//footer//button[text()=\"Save\"]")
	private WebElement addToThemePopUpSaveButton;

	public WebElement addToThemeLogNoteButton(String accountName, int timeOut) {

		String path = "//a[text()=\"" + accountName + "\"]/ancestor::tr//td//button[@title=\"Log Note\"]";
		return FindElement(driver, path, "addToThemeLogNoteButton", action.BOOLEAN, timeOut);
	}

	public WebElement successMsg(int timeOut) {
		return isDisplayed(driver, successMsg, "Visibility", timeOut, "successMsg");
	}

	@FindBy(xpath = "//div/span[contains(text(), \"was created\")]")
	private WebElement successMsg;

	public WebElement addToThemeAccountTableRecord(String accountName, String columnName, int timeOut) {

		String path = "//a[text()=\"" + accountName + "\"]/ancestor::tr//td[@data-label=\"" + columnName + "\"]";
		return FindElement(driver, path, "addToThemeLastInteractionDate", action.BOOLEAN, timeOut);
	}

}
