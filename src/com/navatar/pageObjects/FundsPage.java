package com.navatar.pageObjects;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ProjectName;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;

public class FundsPage extends BasePageBusinessLayer {

	public FundsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//span[text()='Fund']/ancestor::article//span[text()='View All']")
	private WebElement fundsPageViewAll_Lightning;

	/**
	 * @return the fundsPageViewAll_Lightning
	 */
	public WebElement getFundsPageViewAll_Lightning(int timeOut) {
		return isDisplayed(driver, fundsPageViewAll_Lightning, "Visibility", timeOut, "View all button on lightning");
	}

	@FindBy(xpath = "//a[@title='Create Drawdown']")
	private WebElement createFundDrawdownButton_Ligh;

	@FindBy(xpath = "//td[@id='topButtonRow']//input[@title='Create Drawdown']")
	private WebElement createdFundDrawdownButton_classic;

	/**
	 * @return the createdFundDrawdownButton_classic
	 */
	public WebElement getCreatedFundDrawdownButton(String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString()))
			return isDisplayed(driver, createdFundDrawdownButton_classic, "Visibility", timeOut,
					"Fund Drawdown Classic");

		else
			return isDisplayed(driver, createFundDrawdownButton_Ligh, "Visibility", timeOut, "Fund Drawdown Lightning");

	}

	@FindBy(xpath = "//td[@id='topButtonRow']//input[@title='Create Distribution']")
	private WebElement createFundDistributionButton_classic;

	@FindBy(xpath = "//a[@title='Create Distribution']")
	private WebElement createFundDistributionButton_Ligh;

	public WebElement getCreatedFundDistributionButton(String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString()))
			return isDisplayed(driver, createFundDistributionButton_classic, "Visibility", timeOut,
					"Fund Distribution Classic");

		else
			return isDisplayed(driver, createFundDistributionButton_Ligh, "Visibility", timeOut,
					"Fund Distribution Lightning");

	}

	@FindBy(xpath = "//label[text()='Fund Name']/following-sibling::div//input")

	private WebElement fundName_Lighting;

	/**
	 * @return the fundName
	 */
	public WebElement getFundName(String projectName, int timeOut) {

		return isDisplayed(driver, fundName_Lighting, "Visibility", timeOut, "Fund Name Lighting");

	}

	@FindBy(xpath = "//div[@class='requiredInput']//select")
	private WebElement fundType_Classic;

	@FindBy(xpath = "//*[text()='Fund Type']/following-sibling::div//button")
	private WebElement fundType_Lighting;

	/**
	 * @return the fundType
	 */
	public WebElement getFundType(String projectName, int timeOut) {

		return isDisplayed(driver, fundType_Lighting, "Visibility", timeOut, "Fund Type Lighting");

	}

	@FindBy(xpath = "//*[text()='Investment Category']/following-sibling::div//button")
	private WebElement investmentCategory_Lighting;

	/**
	 * @return the investmentCategory
	 */
	public WebElement getInvestmentCategory(String projectName, int timeOut) {

		return isDisplayed(driver, investmentCategory_Lighting, "Visibility", timeOut, "Investment Category Lighting");

	}

	@FindBy(xpath = "//div//h1/div[contains(text(),'Fund')]/..")
	private WebElement fundNameInViewMode_Lighting;

	/**
	 * @return the fundNameLabel
	 */
	public WebElement getFundNameInViewMode(String projectName, int timeOut) {

		return isDisplayed(driver, fundNameInViewMode_Lighting, "Visibility", timeOut,
				"Fund Name in View Mode Lighting");

	}

	public WebElement getFundNameAtFundPage(String fundName, int timeOut) {
		WebElement ele = FindElement(driver,
				"//div[@class='x-panel-bwrap']//a//span[contains(text(),'" + fundName + "')]", "Fund Name",
				action.SCROLLANDBOOLEAN, 60);

		return isDisplayed(driver, ele, "Visibility", timeOut, "Select all check box");
	}

	public WebElement getFundtPageTextBoxOrRichTextBoxWebElement(String projectName, String labelName, int timeOut) {
		WebElement ele = null;
		String xpath = "", inputXpath = "", dateXpath = "", finalXpath = "", finalLabelName = "";
		if (labelName.contains("_")) {
			finalLabelName = labelName.replace("_", " ");
		} else {
			finalLabelName = labelName;
		}

		// span[text()='Description']/..//following-sibling::textarea
		xpath = "//label[contains(text(),'" + finalLabelName + "')]";
		inputXpath = "/..//following-sibling::div//input";
		dateXpath = "/..//input";

		if (labelName.contains("Date")) {
			finalXpath = xpath + dateXpath;
		} else {
			finalXpath = xpath + inputXpath;
		}
		ele = isDisplayed(
				driver, FindElement(driver, finalXpath, finalLabelName + " text box in " + projectName,
						action.SCROLLANDBOOLEAN, 30),
				"Visibility", timeOut, finalLabelName + "text box in " + projectName);
		return ele;
	}

	@FindBy(xpath = "//div[contains(@class,'windowViewMode-normal')]//iframe[@title='accessibility title']")

	private WebElement emailFundraisingContactFrame_Lightning;

	/**
	 * @return the emailFundraisingContactFrame_Lightning
	 */
	public WebElement getEmailFundraisingContactFrame_Lightning(int timeOut) {
		return isDisplayed(driver, emailFundraisingContactFrame_Lightning, "Visibility", timeOut,
				"email Fundraising Contact Frame Lightning");
	}

	@FindBy(xpath = "//input[@title='Email Fundraising Contacts']")
	private WebElement emailFundraisingContactsBtn_Classic;

	@FindBy(xpath = "//button[@title='Email Fundraising Contacts' or text()='Email Fundraising Contacts']")

	private WebElement emailFundraisingContactsBtn_Lightning;

	/**
	 * @return the emailFundraisingContactsBtn
	 */
	public WebElement getEmailFundraisingContactsBtn(String environment, String mode, int timeOut) {
		if (mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
			return isDisplayed(driver, emailFundraisingContactsBtn_Lightning, "Visibility", timeOut,
					"email fundraising contact button in " + mode);
		} else {
			return isDisplayed(driver, emailFundraisingContactsBtn_Classic, "Visibility", timeOut,
					"email fundraising contact button in " + mode);
		}
	}

	@FindBy(xpath = "//span[text()='Tasks']/..")
	private WebElement tasksTab;

	public WebElement gettasksTab(int timeOut) {
		return isDisplayed(driver, tasksTab, "Visibility", timeOut, "tasksTab in funds page");
	}

	/**
	 * @return the getDealType
	 */
	public WebElement getDealType(String projectName, String dealType, int timeOut) {

		String xpath = "//span[text()='" + dealType + "']/../..";
		WebElement ele = FindElement(driver, xpath, "Deal Type", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Deal Type : " + dealType);

	}

	@FindBy(xpath = "//*[text()='Status']/..//div//button")
	private WebElement dealStatusDropDownList;

	/**
	 * @return the fundType
	 */
	public WebElement getDealStatus(String projectName, int timeOut) {

		return isDisplayed(driver, dealStatusDropDownList, "Visibility", timeOut, "Deal Status ");

	}

	@FindBy(xpath = "//*[text()='Role']/..//div//button")
	private WebElement roleDropDownList;

	/**
	 * @return the fundType
	 */
	public WebElement getRoleDropDownList(String projectName, int timeOut) {

		return isDisplayed(driver, roleDropDownList, "Visibility", timeOut, "Role DropDown List ");

	}

	@FindBy(xpath = "//*[text()='Stage']/..//div//button")
	private WebElement dealStageDropDownList;

	/**
	 * @return the fundType
	 */
	public WebElement getDealStage(String projectName, int timeOut) {

		return isDisplayed(driver, dealStageDropDownList, "Visibility", timeOut, "Deal Stage");

	}

	@FindBy(xpath = "//*[text()='Company']/following-sibling::div//input[contains(@placeholder,'Search Institutions')]")
	private WebElement CompanyName;

	/**
	 * @return the legalName
	 */
	public WebElement getCompanyName(String projectName, int timeOut) {

		return isDisplayed(driver, CompanyName, "Visibility", timeOut, "Company Name");

	}

	public WebElement getSourceFirmAndSourceContactTextBox(String fieldLabelName, int timeOut) {
		String xpath = "//*[text()='" + fieldLabelName + "']/following-sibling::div//input";
		WebElement ele = FindElement(driver, xpath, fieldLabelName + " text box ", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "text box : " + fieldLabelName);

	}

	public WebElement getFundNameHeader(String fundName, int timeOut) {
		String xpath = "//h1//lightning-formatted-text[text()='" + fundName + "']";
		WebElement ele = FindElement(driver, xpath, fundName + " Header ", action.SCROLLANDBOOLEAN, timeOut);
		try {
			return isDisplayed(driver, ele, "Visibility", timeOut, "Header : " + fundName);
		} catch (StaleElementReferenceException e) {
			return isDisplayed(driver, ele, "Visibility", timeOut, "Header : " + fundName);
		}

	}

	public WebElement fundNameHeader(String FundName, int timeOut) {

		String xpath = "//div[contains(@class,'windowViewMode-normal')]//lightning-formatted-text[text()='" + FundName
				+ "']";
		try {
			return FindElement(driver, xpath, "Fund Name Header: " + FundName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Fund Name Header: " + FundName, action.SCROLLANDBOOLEAN, timeOut);
		}

	}

	public WebElement fundNameElement(String FundName, int timeOut) {

		try {
			return FindElement(driver, "//tbody//th//a[text()='" + FundName + "']", "Fund Name",
					action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, "//tbody//th//a[text()='" + FundName + "']", "Fund Name",
					action.SCROLLANDBOOLEAN, timeOut);
		}

	}
	@FindBy(xpath = "//label[text()='Company']/following-sibling::div//button/lightning-primitive-icon")

	private WebElement CompanyCrossIcon;

	public WebElement getCompanyCrossIcon(String projectName, int timeOut) {
		return isDisplayed(driver, CompanyCrossIcon, "Visibility", timeOut, "CompanyCrossIcon");
	}

	@FindBy(xpath = "//label[text()='Platform Company']/following-sibling::div//button")
	private WebElement PlatformCompanyCrossIcon;

	public WebElement getPlatformCompanyCrossIcon(String projectName, int timeOut) {
		return isDisplayed(driver, PlatformCompanyCrossIcon, "Visibility", timeOut, "Platform Company Cross Icon");
	}

	@FindBy(xpath = "//label[text()='Legal Name']/following-sibling::div//button")
	private WebElement LegalNameCrossIcon;

	public WebElement getLegalNameCrossIcon(String projectName, int timeOut) {
		return isDisplayed(driver, LegalNameCrossIcon, "Visibility", timeOut, "LegalNameCrossIcon");
	}

	@FindBy(xpath = "//h2[text()='Delete Fund']/../..//button[@title='Delete']")
	private WebElement deleteFundConfirmationMsg;

	public WebElement getDeleteFundConfirmationMsg(int timeOut) {
		return isDisplayed(driver, deleteFundConfirmationMsg, "Visibility", timeOut,
				"fund delete confirmation message");
	}

	@FindBy(xpath = "//span[text()='Next']/parent::button")
	private WebElement fundPageRecordTypeNextButton;

	public WebElement fundPageRecordTypeNextButton(int timeOut) {
		return isDisplayed(driver, fundPageRecordTypeNextButton, "Visibility", timeOut, "fundPageRecordTypeNextButton");
	}

}
