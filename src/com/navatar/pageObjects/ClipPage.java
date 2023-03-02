package com.navatar.pageObjects;


import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.isDisplayed;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.action;


public class ClipPage extends BasePageBusinessLayer {

	public ClipPage(WebDriver driver) {
		super(driver);

	}
	
	@FindBy(xpath = "//button[@title='Save']")
	private WebElement clipTabSaveBtn;

	/**
	 * @return the customTabSaveBtn
	 */
	public WebElement getClipTabSaveBtn(int timeOut) {
		return isDisplayed(driver, clipTabSaveBtn, "Visibility", timeOut, "Clip Tab Save Button");
	}
	
	@FindBy(xpath = "(//button[@title='Save' or text()='Save'])[2]")
	private WebElement saveButton;

	/**
	 * @return the saveButton
	 */
	public WebElement getSaveButton(int timeOut) {
		return isDisplayed(driver, saveButton, "Visibility", timeOut, "Save Button");

	}

	@FindBy(xpath = "//button[@title='Close']")
	private WebElement closeButtonOnPopUp;

	/**
	 * @return the saveButton
	 */
	public WebElement getCloseButtonOnPopUp(int timeOut) {
		return isDisplayed(driver, closeButtonOnPopUp, "Visibility", timeOut, "close Button On PopUp");

	}
}
