package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.action;
public class CapitalCallsPage extends BasePageBusinessLayer {

	public CapitalCallsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[text()='Call Date']/../following-sibling::div//span[contains(@class,'Date')]")
	private WebElement callDate_Lightning;
	
	@FindBy(xpath = "//td[text()='Call Date']/following-sibling::td//div")
	private WebElement callDate_Classic;
	
	public WebElement callDate(String environment, String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			return isDisplayed(driver, callDate_Classic, "visibility", timeOut, "callDate read mode");
		}
		else
			return isDisplayed(driver, callDate_Lightning, "visibility", timeOut, "callDate read mode");

	}

	@FindBy(xpath = "//input[@value='Send Capital Call Notices']")
	private WebElement sendCapitalCallNotices_Classic;
	
	
	/**
	 * @return the sendCapitalCallNotices_Classic
	 */
	public WebElement getSendCapitalCallNotices_Classic(int timeOut) {
		return isDisplayed(driver,sendCapitalCallNotices_Classic , "visibility", timeOut, "send capital call notices button");
	}

	@FindBy(xpath = "//label[text()='Call Amount Received']/../following-sibling::td//input")
	private WebElement callAmountReceivedEdit_Classic;
	
	@FindBy(xpath = "//span[text()='Call Amount Received']/../following-sibling::input")
	private WebElement callAmountReceivedEdit_Lightning;
	
	
	public WebElement callAmountReceivedEdit(String environment, String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			return isDisplayed(driver, callAmountReceivedEdit_Classic, "visibility", timeOut, "callAmountReceived edit mode");
		}
		else
			return isDisplayed(driver, callAmountReceivedEdit_Lightning, "visibility", timeOut, "callAmountReceived edit mode");

	}
	
	
	public WebElement getCCNameAtCCPage(String CCName,int timeOut){
		WebElement ele = FindElement(driver,
				"//div[@class='x-panel-bwrap']//a//span[contains(text(),'" + CCName + "')]", "Fund Name",
				action.SCROLLANDBOOLEAN, 60);
		
		return isDisplayed(driver, ele, "Visibility", timeOut, "Select all check box");
	}
}
