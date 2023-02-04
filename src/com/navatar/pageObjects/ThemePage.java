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

	@FindBy(xpath = "//textarea[@name=\"Description__c\"]")
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

}
