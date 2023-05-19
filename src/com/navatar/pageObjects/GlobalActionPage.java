package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;

public class GlobalActionPage extends BasePageBusinessLayer {

	public GlobalActionPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	@FindBy(xpath = "(//div[contains(@class,'headerTrigger  tooltip-trigger uiTooltip')])[1]")
	 private WebElement globalActionIcon;
	
	/**
	 * @return the globalActionIcon
	 */
	public WebElement getGlobalActionIcon(String projectName,int timeOut) {
		return isDisplayed(driver, globalActionIcon, "Visibility", timeOut, "Global Action Icon");
	}
	
	public WebElement getActionItem(String projectName,GlobalActionItem globalActionItem,int timeOut) {
		boolean flag=false;
		WebElement ele;
		String xpath="";
		String value=globalActionItem.toString().replace("_", " ");
		xpath = "//div[@class='globalCreateMenuList']//ul/li/a[@title='"+value+"']";
		ele = FindElement(driver, xpath, value, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, value);

		
	}
	
	
	@FindBy(xpath = "//span//button[@title='Maximize']")
	 private WebElement maximizeIcon;
	
	/**
	 * @return the globalActionIcon
	 */
	public WebElement getMaximizeIcon(String projectName,int timeOut) {
		return isDisplayed(driver, maximizeIcon, "Visibility", timeOut, "Maximize Icon");
	}
	
	
	@FindBy(xpath = "//button[contains(@class, 'forceActionButton')]//span[text()='Save']")
	 private WebElement saveButtonForEvent;
	
	/**
	 * @return the saveButtonForEvent
	 */
	public WebElement getSaveButtonForEvent(String projectName,int timeOut) {
		return isDisplayed(driver, saveButtonForEvent, "Visibility", timeOut, "save Button For Event");
	}
	
}
