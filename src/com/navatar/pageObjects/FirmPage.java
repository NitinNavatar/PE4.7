package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.isDisplayed;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FirmPage extends BasePageBusinessLayer{
	
	public FirmPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath = "//a[@title='New']")
	private WebElement newButton;

	public WebElement getnewButton(int timeOut) {
		return isDisplayed(driver, newButton, "Visibility", timeOut, "new button");
	}
	
	@FindBy(xpath = "//div[@class='changeRecordTypeRow']//span[@class='slds-form-element__label']")
	private List<WebElement> recordTypeLabelName;

	public List<WebElement> getrecordTypeLabelName() {
		return recordTypeLabelName;
	}
	
	
	

}
