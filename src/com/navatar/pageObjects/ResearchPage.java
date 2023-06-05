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

		if(isDisplayed(driver,  FindElement(driver, "(//span[contains(text(),'"+ gridName+"')]/ancestor::div/following-sibling::div//a)[1]",
				"Grid Name: " + gridName, action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "Grid Name: " + gridName) != null)
		return isDisplayed(driver,  FindElement(driver, "(//span[contains(text(),'"+ gridName+"')]/ancestor::div/following-sibling::div//a)[1]",
				"Grid Name: " + gridName, action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "Grid Name: " + gridName);
		else
		{
			return isDisplayed(driver,  FindElement(driver, "(//span[contains(text(),'"+ gridName+"')]/ancestor::div/following-sibling::div//tbody//button)[1]",
					"Grid Name: " + gridName, action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "Grid Name: " + gridName);
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
			return FindElement(driver,"//span[text()='" + headerName +"']/ancestor::ul/..//following-sibling::div//button[text()='View More']",
					"Record Header: " + headerName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver,"//span[text()='" + headerName +"']/ancestor::ul/..//following-sibling::div//button[text()='View More']",
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
	
	public WebElement getProfileSelected(String ProfileName,int timeout ) {
		return isDisplayed(driver,  FindElement(driver, "//th//a[text()='" + ProfileName + "']",
				"getProfileSelected",action.SCROLLANDBOOLEAN,timeout),"Visibility", timeout, "Profile");
	}
	
	public WebElement getEditButtonForRecordTypes(String recordTypes,int timeout ) {
		return isDisplayed(driver,  FindElement(driver, "//*[text()='" + recordTypes + "s" +"']/following-sibling::*//*[text()='Edit']",
				"getEditButtonForRecordTypes",action.SCROLLANDBOOLEAN,timeout),"Visibility", timeout, "Record Type");
	}
	
	// Acuity Research
		@FindBy(xpath = "//div[contains(@class,'DOCKED')]//div//input")
		private WebElement textAreaResearch;

		public WebElement getTextAreaResearch(int timeOut) {
			return isDisplayed(driver, textAreaResearch, "Visibility", timeOut, "Text Area Research");

		}

		@FindBy(xpath = "(//div[contains(@class,'DOCKED')]//div//button)[1]")
		private WebElement researchMinimize;

		public WebElement getResearchMinimize(int timeOut) {
			return isDisplayed(driver, researchMinimize, "Visibility", timeOut, "Research Minimize");

		}
		
		@FindBy(xpath = "//div[contains(@class,'slds-combobox_object-switcher')]//button[contains(@class,'slds-input_faux')]")
		private WebElement progressDropdown;

		public WebElement getProgressDropdown(int timeOut) {
			return isDisplayed(driver, progressDropdown, "Visibility", timeOut, "Research Progress Dropdown");
		}
		
		public WebElement getClickOnProgress(String progressName, int timeOut) {
			String xpath = "//div[contains(@class,'slds-dropdown_fluid')]//span[text()='"+ progressName +"']";

			return isDisplayed(driver, FindElement(driver, xpath, "Click on : " + progressName, action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "Click on Progress");
		}

		@FindBy(xpath = "(//div[contains(@class,'DOCKED')]//div//button)[2]")
		private WebElement researchPopOut;

		public WebElement getResearchPopOut(int timeOut) {
			return isDisplayed(driver, researchPopOut, "Visibility", timeOut, "Research Pop-Out");

		}

		@FindBy(xpath = "//div[contains(@class,'normal')]//button[@title='Pop-in']")
		private WebElement researchPopIn;

		public WebElement getResearchPopIn(int timeOut) {
			return isDisplayed(driver, researchPopIn, "Visibility", timeOut, "Research Pop In Button");

		}
		
		//@FindBy(xpath = "(//div[contains(@class,'left_small')]//span)[2]")
		
		public WebElement getErrorValue(int timeout) {
			if(isDisplayed(driver,  FindElement(driver, "//div[contains(@class,'bottom_xx-small')]//span[contains(@class,'left_medium')]",
					"researchFindingsLeftPanelHeadingCountForAllResults",action.SCROLLANDBOOLEAN,timeout), "Visibility", timeout, "Error Value") != null)
			return isDisplayed(driver,  FindElement(driver, "//div[contains(@class,'bottom_xx-small')]//span[contains(@class,'left_medium')]",
					"researchFindingsLeftPanelHeadingCountForAllResults",action.SCROLLANDBOOLEAN,timeout), "Visibility", timeout, "Error Value");
			else
			{
				return isDisplayed(driver,  FindElement(driver, "(//div[contains(@class,'bottom_xx-small')]//span[contains(@class,'left_medium')])[2]",
						"researchFindingsLeftPanelHeadingCountForAllResults",action.SCROLLANDBOOLEAN,timeout), "Visibility", timeout, "Error Value");
			}
			
		}
		
		@FindBy(xpath = "//div[@class='slds-m-around_medium main-Container']//button[@title='Research']")
		private WebElement researchButton;

		public WebElement getResearchButton(int timeOut) {
			return isDisplayed(driver, researchButton, "Visibility", timeOut, "Research Button");

		}

		@FindBy(xpath = "//div[contains(@class,'active')]//h2[contains(@class,'vertical__title')]")
		private WebElement researchFindings;

		public WebElement getResearchFindings(int timeOut) {
			return isDisplayed(driver, researchFindings, "Visibility", timeOut, "Research Findings");

		}

		public WebElement getFieldName(String tableName, int timeOut) {
			String xpath = "//div[contains(@class,'active')]//a[text()='" + tableName + "']";

			return FindElement(driver, xpath, "Field Header Name: " + tableName, action.SCROLLANDBOOLEAN, timeOut);
		}
		
		

		public List<WebElement> researchFindingsCountForAllResults() {
			return FindElements(driver, "//div[contains(@class,'active')]//h2/following-sibling::div//a/span",
					"researchFIndingsCountResults");
		}

		
		//div[contains(@class,'normal')]//span[contains(@class,'italic')]
	
		@FindBy(xpath = "//div[contains(@class,'active')]//div[contains(@class,'header__title')]")
		private WebElement researchFindingsValue;

		public WebElement getResearchFindingsValue(int timeOut) {
			return isDisplayed(driver, researchFindingsValue, "Visibility", timeOut, "Research Findings Value");

		}
		
		@FindBy(xpath = "//a[@title='Advanced']")
		private WebElement advancedResearch;

		public WebElement getAdvancedResearch(int timeOut) {
			return isDisplayed(driver, advancedResearch, "Visibility", timeOut, "Advanced Research");

		}
		
		@FindBy(xpath = "//span[@title='Advanced']")
		private WebElement advancedOnResearchPage;

		public WebElement getAdvancedOnResearchPage(int timeOut) {
			return isDisplayed(driver, advancedOnResearchPage, "Visibility", timeOut, "Advanced On Research Page");

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
		
		public WebElement getNoResult(int timeout) {
			if(isDisplayed(driver,  FindElement(driver, "//div[contains(@class,'noResultsTitle')]",
					"No Result",action.SCROLLANDBOOLEAN,timeout), "Visibility", timeout, "No Result") != null)
			return isDisplayed(driver,  FindElement(driver, "//div[contains(@class,'noResultsTitle')]",
					"No Result",action.SCROLLANDBOOLEAN,timeout), "Visibility", timeout, "No Result");
			else
			{
				return isDisplayed(driver,  FindElement(driver, "(//div[contains(@class,'noResultsTitle')])[2]",
						"No Result",action.SCROLLANDBOOLEAN,timeout), "Visibility", timeout, "No Result");
			}
			
		}
}
