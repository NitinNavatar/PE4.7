package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.EnumConstants.*;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import com.navatar.generic.CommonLib;
import com.navatar.generic.SmokeCommonVariables;
import com.navatar.generic.EnumConstants.action;


public class ResearchPage extends BasePageBusinessLayer {

	public ResearchPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	public List<WebElement> gridInResearchResults(String gridName) {
		return FindElements(driver,"//span[contains(text(),'" + gridName + "(')]", "gridInResearchResults");
	}
	
	
	public WebElement clickOnRecordUsingGridName(String gridName, int timeOut) {

		try {
			return FindElement(driver, "(//span[contains(text(),'"+ gridName+"')]/ancestor::div/following-sibling::div//th//a)[1]",
					"Grid Name: " + gridName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver,
					"(//span[contains(text(),'"+ gridName+"')]/ancestor::div/following-sibling::div//th//a)[1]",
					"Grid Name: " + gridName, action.SCROLLANDBOOLEAN, timeOut);
		}

	}
	
	
	public List<WebElement> getAllRecordsUsingHeaderName(String gridName, int timeOut) {

		try {
			return FindElements(driver, "//span[contains(text(),'" + gridName + "')]/ancestor::div/following-sibling::div//tr[@class= 'slds-hint-parent']",
					"Grid Name: " + gridName);
		} catch (StaleElementReferenceException e) {
			return FindElements(driver, "//span[contains(text(),'" + gridName + "')]/ancestor::div/following-sibling::div//tr[@class= 'slds-hint-parent']",
					"Grid Name: " + gridName);
		}

	}
	
	@FindBy(xpath = "//lightning-vertical-navigation-item-badge[@title='All Categories']//a")
	private WebElement allCategoriesLink;

	/**
	 * @return All Categories Link
	 */
	public WebElement getAllCategoriesLink(int timeOut) {
		return isDisplayed(driver, allCategoriesLink, "Visibility", timeOut, "All Categories Link"); 
	}
	
	
	public WebElement RecordPagesHeader(String recordName, int timeOut) {

		try {
			return FindElement(driver,
					"//*[text()='" + recordName + "']",
					"Record Header: " + recordName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver,
					"//lightning-formatted-text[text()='"+recordName+"']",
					"Record Header: " + recordName, action.SCROLLANDBOOLEAN, timeOut);
		}

	}
	
	public List<WebElement> getElementsFromNavigation() {
		return FindElements(driver, "//lightning-vertical-navigation-item-badge[contains(@class,'slds-nav-vertical__item')]",
				"Get Elements from Navigation");
	}

	public List<WebElement> getElementsFromGrid() {
		return FindElements(driver, "//div[contains(@class,'slds-m-bottom_small')]//li//span[contains(@class,'slds-m-left_x-small')]",
				"Get Elements from Grid");
	}
	
	public WebElement getViewMoreOptionUsingHeaderName(String headerName, int timeOut) {

		try {
			return FindElement(driver,"//span[text()='" + headerName +"']/ancestor::ul/..//following-sibling::div//button[text()='View more']",
					"Record Header: " + headerName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver,"//span[text()='" + headerName +"']/ancestor::ul/..//following-sibling::div//button[text()='View more']",
					"Record Header: " + headerName, action.SCROLLANDBOOLEAN, timeOut);
		}

	}
	
	
	public WebElement researchFindingsLeftPanelHeadingName(String headerName,action action ,int timeout ) {
		return isDisplayed(driver,  FindElement(driver, "//div[contains(@class,'active')]//h2/following-sibling::div//a[text()='"+ headerName +"']",
				"researchFindingsLeftPanelHeadingCountForAllResults",action,timeout), "Visibility", timeout, headerName+":Name");
	}
	
	public WebElement researchFindingsLeftPanelHeadingCount(String headerName,action action ,int timeout) {
		return isDisplayed(driver,  FindElement(driver, "//div[contains(@class,'active')]//h2/following-sibling::div//a[text()='"+ headerName +"']/span",
				"researchFindingsLeftPanelHeadingCountForAllResults",action,timeout), "Visibility", timeout, headerName+":count");
		
	}
	
//	@FindBy()
//	protected WebElement NavigationSideBar;
//
	/**
	 * @return Get Text From Navigation Side Bar
	 */
//	public WebElement getElementFromNavigationSideBar(String projectName, int timeOut) {
//		return isDisplayed(driver, NavigationSideBar, "Visibility", timeOut,
//				"Text From Navigation Side Bar");
//	}
}
