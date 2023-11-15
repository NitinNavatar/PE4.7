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
import java.util.stream.Collectors;

public class NavigationPage extends BasePageBusinessLayer {

	public NavigationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//div[@class=\"flexipagePage\"]//div[contains(@class,\"slds-is-open\")]//button[@title=\"Minimize\"]//span[text()=\"Minimize\"]")

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
		return FindElements(driver, "//div[contains(@id,'treeview')]//ul//li[contains(@class,'list-group-item node-treeview')]", "Navigation List item");		
	}

	@FindBy(xpath = "//div[contains(@class,'body_container')]//div//input[contains(@class,'slds-input')]")
	private WebElement navigationResearch;

	/**
	 * @return the navigationResearch
	 */
	public WebElement getNavigationResearch(String projectName, int timeOut) {

		return isDisplayed(driver, navigationResearch, "Visibility", timeOut, "Navigation Research");
	}

	@FindBy(xpath = "//div[contains(@class,'body_container')]//div//button[contains(@class,'slds-button')]")
	private WebElement navigationResearchButton;

	/**
	 * @return the navigationResearchBUtton
	 */
	public WebElement getNavigationResearchButton(String projectName, int timeOut) {

		return isDisplayed(driver, navigationResearchButton, "Visibility", timeOut, "Navigation Research Button");
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

	@FindBy(xpath = "//div[contains(@class,'DOCKED')]//div//input")
	private WebElement researchSearchBox;

	public WebElement researchSearchBox(int timeOut) {

		return isDisplayed(driver, researchSearchBox, "Visibility", timeOut, "researchSearchBox");

	}

	@FindBy(xpath = "//button[text()='Research']")
	private WebElement researchButton;

	public WebElement researchButton(int timeOut) {

		return isDisplayed(driver, researchButton, "Visibility", timeOut, "researchButton");

	}

	@FindBy(xpath = "//div[@class='slds-card__body']//slot//div/span")
	private WebElement researchErrorMsg;

	public WebElement researchErrorMsg(int timeOut) {

		return isDisplayed(driver, researchErrorMsg, "Visibility", timeOut, "researchErrorMsg");

	}

	public List<String> navList() {
		List<String> navItems = FindElements(driver, "//div[contains(@id,'treeview')]//li/span[3]", "Nav List").stream()
				.map(x -> x.getText()).collect(Collectors.toList());

		if (navItems.isEmpty()) {
			return FindElements(driver, "//div[contains(@id,'treeview')]//li/span[3]", "Nav List").stream()
					.map(x -> x.getText()).collect(Collectors.toList());
		}
		return navItems;
	}

	@FindBy(xpath = "//button[@title='Minimize']")
	private WebElement createNavPopUpMinimizeButton;

	public WebElement createNavPopUpMinimizeButton(int timeOut) {

		return isDisplayed(driver, createNavPopUpMinimizeButton, "Visibility", timeOut, "createNavPopUpMinimizeButton");

	}
	
	@FindBy(xpath = "(//div[@class='treeview']//li)[4]")
	private WebElement firmLinkOnCreateOption;

	public WebElement getFirmLinkOnCreateOption(int timeOut) {
		return isDisplayed(driver, firmLinkOnCreateOption, "Visibility", timeOut, "Firm Link On Create Option");

	}
	
	@FindBy(xpath="(//div[@class='flexipageComponent']//button[contains(@class,'active')]/span)[1]")
	private WebElement createLinkOnNavigationPage;

	/**
	 * @return the lightningPageNextBtn
	 */
	public WebElement getCreateLinkOnNavigationPage(int timeOut) {
		return isDisplayed(driver, createLinkOnNavigationPage, "Visibility", timeOut, "Create Link On Navigation Page");
	}
	
	@FindBy(xpath = "//div[contains(@class,'for_desk')]//input")
	private WebElement firmNameTextBoxForCreateOption;

	public WebElement getFirmNameTextBoxForCreateOption(int timeOut) {
		return isDisplayed(driver, firmNameTextBoxForCreateOption, "Visibility", timeOut, "Firm Name Text Box For Create Option");

	}
	
	@FindBy(xpath = "//div[contains(@class,'for_desk')]//label")
	private WebElement firmNameLabelForCreateOption;

	public WebElement getFirmNameLabelForCreateOption(int timeOut) {
		return isDisplayed(driver, firmNameLabelForCreateOption, "Visibility", timeOut, "Firm Name Label For Create Option");

	}
	
	@FindBy(xpath = "//footer[contains(@class,'Footer')]//button[text()='Cancel']")
	private WebElement cancelButtonForCreateOption;

	public WebElement getCancelButtonForCreateOption(int timeOut) {
		return isDisplayed(driver, cancelButtonForCreateOption, "Visibility", timeOut, "Cancel Button For Create Option");

	}
	
	@FindBy(xpath = "//lightning-icon[contains(@class,'utility-close')]")
	private WebElement crossIconForQuickFirm;

	public WebElement getCrossIconForQuickFirm(int timeOut) {
		return isDisplayed(driver, crossIconForQuickFirm, "Visibility", timeOut, "Cross Icon For Quick Firm");
	}
	
	@FindBy(xpath = "//footer[contains(@class,'Footer')]//button[text()='Save']")
	private WebElement saveButtonForCreateOption;

	public WebElement getSaveButtonForCreateOption(int timeOut) {
		return isDisplayed(driver, saveButtonForCreateOption, "Visibility", timeOut, "Save Button For Create Option");
	}
	
	@FindBy(xpath = "//p[text()='These required fields must be completed: Firm Name. ']")
	private WebElement errorOnQuickFirm;

	public WebElement getErrorOnQuickFirm(int timeOut) {
		return isDisplayed(driver, errorOnQuickFirm, "Visibility", timeOut, "Error On Quick Firm");
	}
	
	@FindBy(xpath = "//div[text()='Complete this field.']")
	private WebElement errorOnQuickFirmName;

	public WebElement getErrorOnQuickFirmName(int timeOut) {
		return isDisplayed(driver, errorOnQuickFirmName, "Visibility", timeOut, "Error On Quick Firm Name");
	}
	
	public WebElement getRecordTypeOnQuickFirm(String RecordType, int timeOut) {
		return FindElement(driver,"//span[@title='"+ RecordType +"']/ancestor::lightning-base-combobox-item","Record Type On Quick Firm", action.BOOLEAN, timeOut);
	}
	
	@FindBy(xpath = "//div[contains(@class,'for_desk')]//button")
	private WebElement firmRecordTypeForCreateOption;

	public WebElement getFirmRecordTypeForCreateOption(int timeOut) {
		return isDisplayed(driver, firmRecordTypeForCreateOption, "Visibility", timeOut, "Firm Record Type For Create Option");

	}
	
	@FindBy(xpath = "//div[text()='You have entered an invalid format.']")
	private WebElement errorOnEmailOfQuickContact;

	public WebElement getErrorOnEmailOfQuickContact(int timeOut) {
		return isDisplayed(driver, errorOnEmailOfQuickContact, "Visibility", timeOut, "Error On Email Of Quick Contact");
	}
	
	@FindBy(xpath = "//div[text()='Mobile number should not more than 10 digit']")
	private WebElement errorOnPhoneOfQuickContact;

	public WebElement getErrorOnPhoneOfQuickContact(int timeOut) {
		return isDisplayed(driver, errorOnPhoneOfQuickContact, "Visibility", timeOut, "Error On Phone Of Quick Contact");
	}

	@FindBy(xpath = "(//div[@class='treeview']//li)[3]")
	private WebElement contactLinkOnCreateOption;

	public WebElement getContactLinkOnCreateOption(int timeOut) {
		return isDisplayed(driver, contactLinkOnCreateOption, "Visibility", timeOut, "Contact Link On Create Option");
	}
	
	@FindBy(xpath = "//input[@name='Phone']")
	private WebElement phoneTextBoxForContactOption;

	public WebElement getPhoneTextBoxForContactOption(int timeOut) {
		return isDisplayed(driver, phoneTextBoxForContactOption, "Visibility", timeOut, "Phone Text Box For Create Option");
	}
	
	@FindBy(xpath = "//input[@name='FirstName']")
	private WebElement firstNameTextBoxForCreateOption;

	public WebElement getFirstNameTextBoxForCreateOption(int timeOut) {
		return isDisplayed(driver, firstNameTextBoxForCreateOption, "Visibility", timeOut, "First Name Text Box For Create Option");
	}
	
	@FindBy(xpath = "//label[text()='F Name']")
	private WebElement fNameLabelForCreateOption;

	public WebElement getFNameLabelForCreateOption(int timeOut) {
		return isDisplayed(driver, fNameLabelForCreateOption, "Visibility", timeOut, "F Name Label For Create Option");
	}
	
	@FindBy(xpath = "//label[text()='L Name']")
	private WebElement lNameLabelForCreateOption;

	public WebElement getLNameLabelForCreateOption(int timeOut) {
		return isDisplayed(driver, lNameLabelForCreateOption, "Visibility", timeOut, "L Name Label For Create Option");
	}
	
	@FindBy(xpath = "//label[text()='Email ID']")
	private WebElement emailIDLabelForCreateOption;

	public WebElement getEmailIDLabelForCreateOption(int timeOut) {
		return isDisplayed(driver, emailIDLabelForCreateOption, "Visibility", timeOut, "Email ID Label For Create Option");
	}
	
	@FindBy(xpath = "//input[@name='LastName']")
	private WebElement lastNameTextBoxForCreateOption;

	public WebElement getLastNameTextBoxForCreateOption(int timeOut) {
		return isDisplayed(driver, lastNameTextBoxForCreateOption, "Visibility", timeOut, "Last Name Text Box For Create Option");
	}
	
	@FindBy(xpath = "//label[text()='Phone Number']")
	private WebElement phoneNumberLabelForCreateOption;

	public WebElement getPhoneNumberLabelForCreateOption(int timeOut) {
		return isDisplayed(driver, phoneNumberLabelForCreateOption, "Visibility", timeOut, "Phone Number Label For Create Option");
	}
	
	
	@FindBy(xpath = "//Label[text()='Account Name']/..//div/input[@placeholder='Search']")
	private WebElement firmNameTextBoxForContactOption;

	public WebElement getFirmNameTextBoxForContactOption(int timeOut) {
		return isDisplayed(driver, firmNameTextBoxForContactOption, "Visibility", timeOut, "Firm Name Text Box For Create Option");
	}
	
	@FindBy(xpath = "//input[@name='Email']")
	private WebElement emailTextBoxForContactOption;

	public WebElement getEmailTextBoxForContactOption(int timeOut) {
		return isDisplayed(driver, emailTextBoxForContactOption, "Visibility", timeOut, "Email Text Box For Create Option");
	}
	

	@FindBy(xpath = "//p[text()='These required fields must be completed: Last Name. ']")
	private WebElement errorOnQuickContact;

	public WebElement getErrorOnQuickContact(int timeOut) {
		return isDisplayed(driver, errorOnQuickContact, "Visibility", timeOut, "Error On Quick Contact");
	}

}
