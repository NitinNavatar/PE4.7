package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.isDisplayed;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.action;

public class FOFTransactionPage extends BasePageBusinessLayer {

	public FOFTransactionPage(WebDriver driver) {

		
		super(driver);
		// TODO Auto-generated constructor stub
	}
	

@FindBy(xpath = "//*[text()='Fund']/..//input")
private WebElement fundInputBox;


/**
 * @return the fund name input box
 */
public WebElement getSelectFundNameInputBox(int timeOut) {
	return isDisplayed(driver, fundInputBox, "Visibility", timeOut, "fund name input box");
}

@FindBy(xpath = "//*[text()='Fund Manager’s Fund']/..//input")
private WebElement fundManagersFundInputBox;


/**
 * @return the fund name input box
 */
public WebElement getFundManagersFundInputBox(int timeOut) {
	return isDisplayed(driver, fundManagersFundInputBox, "Visibility", timeOut, "fund manager fund input box");
}


@FindBy(xpath = "//*[text()='Transaction Type']/..//input")
private WebElement transactionTypeDropdown;


/**
 * @return the fund name input box
 */
public WebElement getTransactionTypeDropdown(int timeOut) {
	return isDisplayed(driver, transactionTypeDropdown, "Visibility", timeOut, "transaction type dropdown");
}


@FindBy(xpath = "//input[@name='navpeII__Transaction_Date__c']")
private WebElement transactionDateInputBox;


/**
 * @return the fund name input box
 */
public WebElement getTransactionDateInputBox(int timeOut) {
	return isDisplayed(driver, transactionDateInputBox, "Visibility", timeOut, "transaction date input box");
}


public WebElement getfieldLabel(String fieldName,int timeout) {
	String xpath ="//*[text()='"+fieldName+"']/..//input";
	
	return isDisplayed(driver, FindElement(driver, xpath, fieldName, action.BOOLEAN, timeout), "Visibility", timeout, fieldName);
	
}


}
