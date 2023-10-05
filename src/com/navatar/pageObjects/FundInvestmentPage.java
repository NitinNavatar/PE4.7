package com.navatar.pageObjects;

import java.util.List;

import static com.navatar.generic.CommonLib.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.action;

public class FundInvestmentPage extends BasePageBusinessLayer {

	public FundInvestmentPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	

	
	/**
	 * @return the fund name input box
	 */
	
	@FindBy(xpath = "//*[text()='Fund Name']/..//input")
	private WebElement fundNameInputBox;
	
	/**
	 * @return the fund name input box on fund investement popup
	 */
	public WebElement getFundName(int timeOut) {
		return isDisplayed(driver,fundNameInputBox , "visibility", timeOut, "fund name input box");
	}
	
	
	/**
	 * @return the company name input box
	 */
	@FindBy(xpath = "//*[text()='Company']/..//input")
	private WebElement companyNameInputBox;
	
	/**
	 * @return the company name input box on fund investement popup
	 */
	public WebElement getCompanyName(int timeOut) {
		return isDisplayed(driver,companyNameInputBox , "visibility", timeOut, "company name input box");
	}

	
	@FindBy(xpath="//input[@name='navpeII__Investment_Amount__c']")
	private WebElement investmentAmountInputbox;

	/**
	 * @return the investment amount input box on fund investement popup
	 */
	public WebElement getInvestmentAmount(int timeOut) {
		return isDisplayed(driver, investmentAmountInputbox, "Visibility", timeOut, "investment amount input box");
	}

	@FindBy(xpath="//input[@name='navpeII__Investment_Date__c']")
	private WebElement investmentDateInputbox;

	/**
	 * @return the investment date input box on fund investement popup
	 */
	public WebElement getInvestmentDate(int timeOut) {
		return isDisplayed(driver, investmentDateInputbox, "Visibility", timeOut, "investment date input box");
	}

	
	
	
	}
