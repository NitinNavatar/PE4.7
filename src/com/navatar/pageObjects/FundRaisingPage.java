/**
 * 
 */
package com.navatar.pageObjects;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static com.navatar.generic.CommonLib.*;

import java.util.List;

/**
 * @author Parul Singh
 *
 */
public class FundRaisingPage extends BasePage {

	/**
	 * @param driver
	 */
	public FundRaisingPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@name='Name']")
	private WebElement fundraisingName;

	/**
	 * @return the fundraisingName
	 */
	public WebElement getFundraisingName(int timeOut) {
		return isDisplayed(driver, fundraisingName, "Visibility", timeOut, "FundRaising Name");
	}

	@FindBy(xpath = "(//div[@class='requiredInput']//span[@class='lookupInput']//input)[1]")
	private WebElement fundName;

	/**
	 * @return the fundName
	 */
	public WebElement getFundName(int timeOut) {
		return isDisplayed(driver, fundName, "Visibility", timeOut, "Fund Name");
	}

	@FindBy(xpath = "(//div[@class='requiredInput']//span[@class='lookupInput']//input)[2]")
	private WebElement legalName;

	/**
	 * @return the legalName
	 */
	public WebElement getLegalName(int timeOut) {
		return isDisplayed(driver, legalName, "Visibility", timeOut, "Legal Name");
	}

	@FindBy(xpath = "//div[@id='Name_ileinner']")
	private WebElement fundraisingNameInViewMode;

	/**
	 * @return the fundraisingNameInViewMode
	 */
	public WebElement getFundraisingNameInViewMode(int timeOut) {
		return isDisplayed(driver, fundraisingNameInViewMode, "Visibility", timeOut, "Fundraising Name in view Mode");
	}

	public WebElement getTestCheckboxFCheckbox(String TestCheckboxFCheckboxFieldLabel, int timeOut) {
		WebElement ele = null;
		ele = isDisplayed(driver,
				FindElement(driver, "//label[text()='" + TestCheckboxFCheckboxFieldLabel + "']/../..//input",
						"Test Checkbox F field label", action.SCROLLANDBOOLEAN, timeOut),
				"Visibility", timeOut, "Test Checkbox F  Field Label");
		return ele;
	}

	@FindBy(xpath = "(//span[@class='dateInput dateOnlyInput']/../..//input)[1]")
	private WebElement targetCloseDateTextBox;

	/**
	 * @return the targetCloseDateTextBox
	 */
	public WebElement getTargetCloseDateTextBox(int timeOut) {
		return isDisplayed(driver, targetCloseDateTextBox, "Visibility", timeOut, "Target Close Date Text Box");
	}

	@FindBy(xpath = "//span[@class='dateInput']/../..//input")
	private WebElement testDateTimeTextBox;

	/**
	 * @return the testDateTimeTextBox
	 */
	public WebElement getTestDateTimeTextBox(int timeOut) {
		return isDisplayed(driver, testDateTimeTextBox, "Visibility", timeOut, "Test Date Time TextBOx");
	}

	public WebElement getTestEmailTextbox(String TestEmailFieldLabel, int timeOut) {
		WebElement ele = null;
		ele = isDisplayed(driver,
				FindElement(driver, "//label[text()='" + TestEmailFieldLabel + "']/../..//input",
						"Test Email Field Label", action.SCROLLANDBOOLEAN, timeOut),
				"Visibility", timeOut, "Test Email Field Label");
		return ele;
	}

	@FindBy(xpath = "//label[text()='Investment High Amount (mn)']/../..//td[@class='dataCol']//input")
	private WebElement investmenthighAmountTestBox;

	/**
	 * @return the investmenthighAmountTestBox
	 */
	public WebElement getInvestmenthighAmountTestBox(int timeOut) {
		return isDisplayed(driver, investmenthighAmountTestBox, "Visibility", timeOut,
				"Investment High Amount TextBox");
	}

	@FindBy(xpath = "//label[text()='TestGeolocF (Latitude)']/../..//input")
	private WebElement testGeolocationLatitudeTextBox;

	/**
	 * @return the testGeolocationLatitudeTextBox
	 */
	public WebElement getTestGeolocationLatitudeTextBox(int timeOut) {
		return isDisplayed(driver, testGeolocationLatitudeTextBox, "Visibility", timeOut,
				"Test Geolocation Latitude checkbox");
	}

	@FindBy(xpath = "//label[text()='TestGeolocF (Longitude)']/../..//input")
	private WebElement testGeolocationLongitudeTextBox;

	/**
	 * @return the testGeolocationLatitudeTextBox
	 */
	public WebElement getTestGeolocationLongitudeTextBox(int timeOut) {
		return isDisplayed(driver, testGeolocationLongitudeTextBox, "Visibility", timeOut,
				"Test Geolocation Longitude checkbox");
	}

	public WebElement getTestNumberTextbox(String TestNumberFieldLabel, int timeOut) {
		WebElement ele = null;
		ele = isDisplayed(driver,
				FindElement(driver, "//label[text()='" + TestNumberFieldLabel + "']/../..//input",
						"Test Number Field Label", action.SCROLLANDBOOLEAN, timeOut),
				"Visibility", timeOut, "Test Number Field Label");
		return ele;
	}

	@FindBy(xpath = "(//label[text()='Potential Management Fee%']/../..//input)[2]")
	private WebElement potentialManagementFeeTextBox;

	/**
	 * @return the potentialManagementFeeTextBox
	 */
	public WebElement getPotentialManagementFeeTextBox(int timeOut) {
		return isDisplayed(driver, potentialManagementFeeTextBox, "Visibility", timeOut,
				"Potential Managemnet Fee Text Box");
	}

	public WebElement getTestPhoneTextbox(String TestPhoneFieldLabel, int timeOut) {
		WebElement ele = null;
		ele = isDisplayed(driver,
				FindElement(driver, "//label[text()='" + TestPhoneFieldLabel + "']/../..//input",
						"Test Phone Field Label", action.SCROLLANDBOOLEAN, timeOut),
				"Visibility", timeOut, "Test Phone Field Label");
		return ele;
	}

	@FindBy(xpath = "//label[text()='Stage']/../..//Select")
	private WebElement stageDropdown;;

	/**
	 * @return the stageDropdown
	 */
	public WebElement getStageDropdown(int timeOut) {
		return isDisplayed(driver, stageDropdown, "Visibility", timeOut, "stage Dropdown");
	}

	@FindBy(xpath = "//label[text()='Reason for Decline']/../..//Select")
	private WebElement reasonForDeclineDropdown;

	/**
	 * @return the reasonForDeclineDropdown
	 */
	public WebElement getReasonForDeclineDropdown(int timeOut) {
		return isDisplayed(driver, reasonForDeclineDropdown, "Visibility", timeOut, "Reason For Decline Dropdown");
	}

	@FindBy(xpath = "//select[@title='TestMultipicklistF - Available']")
	private WebElement multiplePicklist;

	/**
	 * @return the multiplePicklist
	 */
	public WebElement getMultiplePicklist(int timeOut) {
		return isDisplayed(driver, multiplePicklist, "Visibility", timeOut, "Multiple picklist");
	}

	@FindBy(xpath = "//label[text()='Next Step']/../..//input")
	private WebElement nextStepTextbox;

	/**
	 * @return the nextStepTextbox
	 */
	public WebElement getNextStepTextbox(int timeOut) {
		return isDisplayed(driver, nextStepTextbox, "Visibility", timeOut, "Next Step Text Box");
	}

	public WebElement getTestTextArea(String TestTextAreaFieldLabel, int timeOut) {
		WebElement ele = null;
		ele = isDisplayed(driver,
				FindElement(driver, "//label[text()='" + TestTextAreaFieldLabel + "']/../..//textarea",
						"Test Text Area Field Label", action.SCROLLANDBOOLEAN, timeOut),
				"Visibility", timeOut, "Test Text Area Field Label");
		return ele;
	}

	@FindBy(xpath = "//span[@class='timeContainer']/input")
	private WebElement testTimeBetaFTextBox;

	/**
	 * @return the testTimeBetaFTextBox
	 */
	public WebElement getTestTimeBetaFTextBox(int timeOut) {
		return isDisplayed(driver, testTimeBetaFTextBox, "Visibility", timeOut, "Test Time Beta F Text Box");
	}

	public WebElement getTestURL(String TestURLFieldLabel, int timeOut) {
		WebElement ele = null;
		ele = isDisplayed(
				driver, FindElement(driver, "//label[text()='" + TestURLFieldLabel + "']/../..//input",
						"Test URL Field Label", action.SCROLLANDBOOLEAN, timeOut),
				"Visibility", timeOut, "Test URL Field Label");
		return ele;
	}

	@FindBy(xpath = "//img[@class='picklistArrowRight']")
	private WebElement multiPicklistAddButton;

	/**
	 * @return the multiPicklistAddButton
	 */
	public WebElement getMultiPicklistAddButton(int timeOut) {
		return isDisplayed(driver, multiPicklistAddButton, "Visibility", timeOut, "Multi PickList Add Buton");
	}

	@FindBy(xpath = "//input[@value='New Fundraising Contact']")
	private WebElement newFundRaisngContactButton;

	/**
	 * @return the newFundRaisngContactButton
	 */
	public WebElement getNewFundRaisngContactButton(int timeOut) {
		return isDisplayed(driver, newFundRaisngContactButton, "Visibility", timeOut, "New FundRaising contact button");
	}

	@FindBy(xpath = "//label[text()='Contact']/../..//span//input")
	private WebElement fundraisngContactContacttextBox;

	/**
	 * @return the fundraisngContactContacttextBox
	 */
	public WebElement getFundraisngContactContacttextBox(int timeOut) {
		return isDisplayed(driver, fundraisngContactContacttextBox, "Visibility", timeOut, "Contact text box");
	}

	@FindBy(xpath = "(//td[text()='Total Fundraising Contacts']/..//div)[2]")
	private WebElement totalFundRaisngCount;

	/**
	 * @return the fundraisngContactID
	 */
	public WebElement getTotalFundRaisngCount(int timeOut) {
		return isDisplayed(driver, totalFundRaisngCount, "Visibility", timeOut, "Total FundRaisng Count");
	}

	@FindBy(xpath = "//input[@name='new']")
	private WebElement newButtonClassic;

	@FindBy(xpath = "//a[@title='New']")
	private WebElement newButtonLighting;

	/**
	 * @return the newButton
	 */
	public WebElement getNewButton(String environment, String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			return isDisplayed(driver, newButtonClassic, "Visibility", timeOut, "New Button Classic");
		} else {
			return newButtonLighting;
		}

	}

	@FindBy(xpath = "//input[@name='Name']")
	private WebElement fundraisingName_Classic;

	@FindBy(xpath = "//*[text()='Fundraising Name']/following-sibling::div/input")
	private WebElement fundraisingName_Lighting;

	/**
	 * @return the fundraisingName
	 */
	public WebElement getFundraisingName(String environment, String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			return isDisplayed(driver, fundraisingName_Classic, "Visibility", timeOut, "FundRaising Name Classic");
		} else {
			return isDisplayed(driver, fundraisingName_Lighting, "Visibility", timeOut, "FundRaising Name Lighting");
		}

	}

	@FindBy(xpath = "(//div[@class='requiredInput']//span[@class='lookupInput']//input)[1]")
	private WebElement fundName_Classic;

	@FindBy(xpath = "//*[text()='Fund Name']/following-sibling::div//input[@title='Search Funds' or contains(@placeholder,'Search Funds')]")
	private WebElement fundName_Lighting;

	/**
	 * @return the fundName
	 */
	public WebElement getFundName(String environment, String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			return isDisplayed(driver, fundName_Classic, "Visibility", timeOut, "Fund Name Classic");
		} else {
			return isDisplayed(driver, fundName_Lighting, "Visibility", timeOut, "Fund Name Lighting");
		}

	}

	@FindBy(xpath = "(//div[@class='requiredInput']//span[@class='lookupInput']//input)[2]")
	private WebElement legalName_Classic;

	@FindBy(xpath = "//*[text()='Legal Name']/following-sibling::div//input[@title='Search' or contains(@placeholder,'Search')]")
	private WebElement legalName_Lighting;

	/**
	 * @return the legalName
	 */
	public WebElement getLegalName(String environment, String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			return isDisplayed(driver, legalName_Classic, "Visibility", timeOut, "Legal Name Classic");
		} else {
			return isDisplayed(driver, legalName_Lighting, "Visibility", timeOut, "Legal Name Lighting");
		}

	}

	@FindBy(xpath = "//div[@id='Name_ileinner']")
	private WebElement fundraisingNameInViewMode_Classic;

	@FindBy(xpath = "//div[@class='slds-media__body']//h1/span")
	private WebElement fundraisingNameInViewMode_Lighting;

	/**
	 * @return the fundraisingNameInViewMode
	 */
	public WebElement getFundraisingNameInViewMode(String environment, String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			return isDisplayed(driver, fundraisingNameInViewMode_Classic, "Visibility", timeOut,
					"Fundraising Name in view Mode Classic");
		} else {
			return isDisplayed(driver, fundraisingNameInViewMode_Lighting, "Visibility", timeOut,
					"Fundraising Name in view Mode Lighting");
		}
	}

	
	
	public List<WebElement> recordsInListView() {

		String xpath = "//table[contains(@class,'forceRecordLayout')]//tbody//tr";
		try {
			return FindElements(driver, xpath, "recordsInListView");
		} catch (StaleElementReferenceException e) {
			return FindElements(driver, xpath, "recordsInListView");
		}
	}
	
	
	
	@FindBy(xpath = "//*[text()='Company']/following-sibling::div//input[@title='Search' or contains(@placeholder,'Search')]")
	private WebElement companyName_Lighting;
	public WebElement getCompanyName(String environment, String mode, int timeOut) {
		
			return isDisplayed(driver, companyName_Lighting, "Visibility", timeOut, "Company Name");
		

	}

	
	@FindBy(xpath = "//a[text()='Fundraising Contacts' and @role='tab']")
	private WebElement fundraisingContactTab;

	public WebElement getfundraisingContactTab(int timeOut) {
		return isDisplayed(driver, fundraisingContactTab, "Visibility", timeOut, "Fundraising Contact tab");
	}
	
	@FindBy(xpath = "//div[@class='slds-button-group']//button[text()='New Fundraising Contact']")
	private WebElement newFundraisingContactBtn;

	public WebElement newFundraisingContactBtn(int timeOut) {
		return isDisplayed(driver, newFundraisingContactBtn, "Visibility", timeOut, "Fundraising Contact button");
	}
	
	@FindBy(xpath = "//label[text()='Legal Name']/following-sibling::div//button")
	private WebElement LegalNameCrossIcon;

	public WebElement getLegalNameCrossIcon(String projectName, int timeOut) {
		return isDisplayed(driver, LegalNameCrossIcon, "Visibility", timeOut, "LegalNameCrossIcon");
	}
	

}
