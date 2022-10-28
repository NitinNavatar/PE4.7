package com.navatar.pageObjects;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.SearchBasedOnExistingFundsOptions;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.List;

public class NavigationPage extends BasePageBusinessLayer {

	public NavigationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//div[@class='flexipagePage']//span[text()='Minimize']")
	private WebElement navatarQuickLinkMinimize_Lighting;

	/**
	 * @return the navatarQuickLinkMinimize_Lighting
	 */
	public WebElement getNavatarQuickLinkMinimize_Lighting(String projectName, int timeOut) {
		return isDisplayed(driver, navatarQuickLinkMinimize_Lighting, "Visibility", timeOut,
				"Navatar Quick Link Minimize Lighting");
	}

	/**
	 * @return the getExpandIcon
	 */
	public List<WebElement> getExpandIcon(String projectName, int timeOut) {
		return FindElements(driver, "//span[@class='icon expand-icon glyphicon glyphicon-plus']", "> icon");
	}

	@FindBy(xpath = "//div/a[text()='Next']")
	private WebElement lightningPageNextBtn;

	/**
	 * @return the lightningPageNextBtn
	 */
	public WebElement getLightningPageNextBtn(String projectName, int timeOut) {
		return isDisplayed(driver, lightningPageNextBtn, "Visibility", timeOut, "lightning Page Next Btn");
	}

	@FindBy(xpath = "(//div/a[text()='Next'])[2]")
	private WebElement lightningPageNextBtn2;

	/**
	 * @return the lightningPageNextBtn
	 */
	public WebElement getLightningPageNextBtn2(String projectName, int timeOut) {
		return isDisplayed(driver, lightningPageNextBtn2, "Visibility", timeOut, "lightning Page Next Btn2");
	}

	@FindBy(xpath = "//div/a[text()='Finish']")
	private WebElement lightningPageFinishBtn;

	/**
	 * @return the lightningPageNextBtn
	 */
	public WebElement getLightningPagFinishBtn(String projectName, int timeOut) {
		return isDisplayed(driver, lightningPageFinishBtn, "Visibility", timeOut, "lightning Page Finish Btn");
	}

	@FindBy(xpath = "//*[text()='Lightning Experience']")
	private WebElement lightningExperienceTab;

	/**
	 * @return the lightningExperience
	 */
	public WebElement getLightningExperienceTab(String projectName, int timeOut) {
		return isDisplayed(driver, lightningExperienceTab, "Visibility", timeOut, "lightning Experience Tab");
	}

	// div[@class='outPopupBox']//h2
	// h2[@id='modal-heading-01']
	@FindBy(xpath = "//div[@class='outPopupBox']//h2")
	private WebElement navigationPopUpHeader;

	/**
	 * @return the navigationPopUpHeader
	 */
	public WebElement getnavigationPopUpHeader(String projectName, int timeOut) {
		return isDisplayed(driver, navigationPopUpHeader, "Visibility", timeOut, "navigation PopUp Header");
	}

	/**
	 * @return the NavigationList
	 */
	public List<WebElement> getNavigationList(String projectName, int timeOut) {
		return FindElements(driver, "//div[contains(@id,'treeview')]//ul//li/span[3]", "Navigation List item");
	}

	@FindBy(xpath = "//*[text()='Navigation Type']/..//div//button")
	private WebElement navigationTypeLabel;

	/**
	 * @return the navigationTypeLabel
	 */
	public WebElement getNavigationTypeLabel(String projectName, int timeOut) {

		return isDisplayed(driver, navigationTypeLabel, "Visibility", timeOut, "Navigation TYpe Label");

	}

	@FindBy(xpath = "//button[@title='Clear Selection']")
	private WebElement clearSelection;

	/**
	 * @return the clearSelection
	 */
	public WebElement getClearSelection(String projectName, int timeOut) {

		return isDisplayed(driver, clearSelection, "Visibility", timeOut, "clear Selection");

	}

	public WebElement navButtonName(String navigationButtonName, int timeOut) {
		String xpath = "//ul[contains(@class,'utilitybar')]//span[text()='" + navigationButtonName
				+ "']/parent::button";

		try {
			return FindElement(driver, xpath, "navigationButtonName: " + navigationButtonName, action.SCROLLANDBOOLEAN,
					timeOut);

		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "navigationButtonName: " + navigationButtonName, action.SCROLLANDBOOLEAN,
					timeOut);
		}
	}

	
	@FindBy(xpath = "//div[@class='slds-m-around_medium']//div/input")
	private WebElement researchSearchBox;
	public WebElement researchSearchBox( int timeOut) {

		return isDisplayed(driver, researchSearchBox, "Visibility", timeOut, "researchSearchBox");

	}
	
	@FindBy(xpath = "//lightning-input/following-sibling::lightning-button/button[text()='Research']")
	private WebElement researchButton;
	public WebElement researchButton( int timeOut) {

		return isDisplayed(driver, researchButton, "Visibility", timeOut, "researchButton");

	}
	
	@FindBy(xpath = "//div[@class='slds-card__body']//slot//div/span")
	private WebElement researchErrorMsg;
	public WebElement researchErrorMsg( int timeOut) {

		return isDisplayed(driver, researchErrorMsg, "Visibility", timeOut, "researchErrorMsg");

	}
	
	
	
	
}
