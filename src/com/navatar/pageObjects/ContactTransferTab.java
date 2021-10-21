package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.isDisplayed;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactTransferTab extends NavatarSetupPageBusinessLayer {

	public ContactTransferTab(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[contains(@class,'InsufficientPermissions_Fancybox1')]//a[@title='Close']")
	private WebElement confirmationPopUpCrossIcon;

	/**
	 * @return the confirmationPopUpCrossIcon
	 */
	public WebElement getConfirmationPopUpCrossIcon(String environment,int timeOut) {
		return isDisplayed(driver, confirmationPopUpCrossIcon, "Visibility", timeOut, "Confirmation Pop Up Cross Icon");
	}
	
	@FindBy(xpath="//div[contains(@class,'InsufficientPermissions_Fancybox1')]//input[@title='No']")
	private WebElement confirmationPopUpNoButton;

	/**
	 * @return the confirmationPopUpNoButton
	 */
	public WebElement getConfirmationPopUpNoButton(String environment,int timeOut) {
		return isDisplayed(driver, confirmationPopUpNoButton, "Visibility", timeOut, "confirmation Pop Up No Button");
	}
	
	@FindBy(xpath="//div[contains(@class,'InsufficientPermissions_Fancybox1')]//input[@title='Yes']")
	private WebElement confirmationPopUpYesButton;

	/**
	 * @return the warningPopUpYesButton
	 */
	public WebElement getConfirmationPopUpYesButton(String environment,int timeOut) {
		return isDisplayed(driver, confirmationPopUpYesButton, "Visibility", timeOut, "confirmation Pop Up Yes Button");
	}
	
	@FindBy(xpath="//div[contains(@class,'InsufficientPermissions_Fancybox1')]//div[@class='PopupContentStart']")
	private WebElement confirmationPopUpMsg;

	/**
	 * @return the confirmationPopUpMsg
	 */
	public WebElement getconfirmationPopUpMsg(String environment,int timeOut) {
		return isDisplayed(driver, confirmationPopUpMsg, "Visibility", timeOut, "confirmation Pop Up Message");
	}

}
