package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.EnumConstants.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AgreementAmendmentPage extends BasePageBusinessLayer{

	public AgreementAmendmentPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	@FindBy(xpath = "//div[@class='changeRecordTypeRow']//span[text()='Agreement']/../..")
	private WebElement agreementRadioButton;
	
	
	public WebElement getRecordTypeAgreemetnRadioButton(String projectName, int timeOut) {
		return isDisplayed(driver, agreementRadioButton, "Visibility", timeOut, "agreement radio button");

	}
	
	@FindBy(xpath = "//div[@class='changeRecordTypeRow']//span[text()='Amendment']/../..")
	private WebElement amendmentRadioButton;
	
	
	public WebElement getRecordTypeAmendmentRadioButton(String projectName, int timeOut) {
		return isDisplayed(driver, amendmentRadioButton, "Visibility", timeOut, "amendment radio button");

	}
	
	@FindBy(xpath = "//input[@name='navpeII__Agreement_Amendment_Number__c']")
	private WebElement agreementAmendmentNumber;
	
	
	public WebElement getAgreementAmendmentNumberInput(String projectName, int timeOut) {
		return isDisplayed(driver, agreementAmendmentNumber, "Visibility", timeOut, "agreement/amendment number input");

	}
	
	@FindBy(xpath = "//*[text()='Commitment']/..//input")
	private WebElement commitementInput;
	
	
	public WebElement getCommitmentInput(String projectName, int timeOut) {
		return isDisplayed(driver, commitementInput, "Visibility", timeOut, "commitement input");

	}
	
	@FindBy(xpath = "//input[@name='navpeII__Effective_Date__c']")
	private WebElement effectiveDate;
	
	
	public WebElement getEffectiveDateInput(String projectName, int timeOut) {
		return isDisplayed(driver, effectiveDate, "Visibility", timeOut, "effective date input");

	}
	
	@FindBy(xpath = "//input[@name='navpeII__Receipt_Date__c']")
	private WebElement recieptDateInput;
	
	
	public WebElement getRecieptDateInput(String projectName, int timeOut) {
		return isDisplayed(driver, recieptDateInput, "Visibility", timeOut, "reciept date input");

	}
	
	
	
}
