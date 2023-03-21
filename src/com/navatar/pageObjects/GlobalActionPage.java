package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;

import java.util.List;

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
	
	
	@FindBy(xpath = "//button[text()='Save' and @name='SaveEdit']")
	 private WebElement saveButtonForEvent;
	
	/**
	 * @return the saveButtonForEvent
	 */
	public WebElement getSaveButtonForEvent(String projectName,int timeOut) {
		List<WebElement> eleList = FindElements(driver, "//button[@title='Save' or text()='Save'or @name='SaveEdit']", "Save Button");

		for (WebElement webElement : eleList) {
			webElement = isDisplayed(driver, webElement, "Visibility", 2, " Save Button lightning");
			if (webElement != null) {
				return webElement;
			} else {

			}
		}
		return isDisplayed(driver, saveButtonForEvent, "Visibility", timeOut, " Save Button lightning");
	}
	
	
	@FindBy(xpath = "//h2[text()='New Event']/ancestor::div[@data-aura-class='oneRecordActionWrapper']//button[@title='Save']")
	 private WebElement saveBtnOFEventPopup;
	
	/**
	 * @return the globalActionIcon
	 */
	public WebElement getSaveBtnOfEventPopup(int timeOut) {
		return isDisplayed(driver, saveBtnOFEventPopup, "Visibility", timeOut, "save button");
	}
	
	
	
}
