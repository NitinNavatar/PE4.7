package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.Mode;

public class InvestorDistributionsPage extends BasePageBusinessLayer {

	public InvestorDistributionsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	@FindBy(xpath = "//td[text()='Capital Recallable']/../td[2]/div/img")
	private WebElement capitalRecallableValue_Classic;
	
	
	public WebElement getCapitalRecallableValue(String mode,int timeOut) {
		if (mode.toString().equalsIgnoreCase(Mode.Classic.toString()))
		return isDisplayed(driver,capitalRecallableValue_Classic , "visibility", timeOut, "capitalRecallableValue checkbox");
		else
			return isDisplayed(driver,capitalRecallableValue_Lighting , "visibility", timeOut, "capitalRecallableValue checkbox");
			}
	
	@FindBy(xpath = "//*[text()='Capital Recallable']/../following-sibling::div//*[@class='slds-checkbox_faux']")
	private WebElement capitalRecallableValue_Lighting;
	
	@FindBy(xpath = "//td[text()='Capital Recallable']/../td[2]/div/img")
	private WebElement EmailHeading;
	
	
	public WebElement getEmailInvestorDistributionHeading(String environment,String mode,int timeOut) {
		String xpath ="//div[@class='slds-media']//p[2]";
		WebElement ele =FindElement(driver, xpath, "Email Investor Distribution Heading", action.BOOLEAN, timeOut);
		return isDisplayed(driver,ele , "visibility", timeOut, "Email Investor Distribution Heading");
	}
		
	
}
