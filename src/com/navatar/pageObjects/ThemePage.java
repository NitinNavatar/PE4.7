package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.FindElements;
import static com.navatar.generic.CommonLib.isDisplayed;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.action;

public class ThemePage extends BasePageBusinessLayer {

	public ThemePage(WebDriver driver) {
		super(driver);

	}

	@FindBy(xpath = "//button[text()=\"New Theme\"]")
	private WebElement newThemeButton;

	public WebElement newThemeButton(int timeOut) {
		return isDisplayed(driver, newThemeButton, "Visibility", timeOut, "newThemeButton");

	}

	@FindBy(xpath = "//button[text()=\"No\"]")
	private WebElement noButton;

	public WebElement noButton(int timeOut) {
		return isDisplayed(driver, noButton, "Visibility", timeOut, "noButton");

	}

	@FindBy(xpath = "//input[@name=\"Name\"]")
	private WebElement themeNameInputBox;

	public WebElement themeNameInputBox(int timeOut) {
		return isDisplayed(driver, themeNameInputBox, "Visibility", timeOut, "themeNameInputBox");

	}

	@FindBy(xpath = "//textarea[contains(@name,\"Description__c\")]")
	private WebElement themeDescription;

	public WebElement themeDescription(int timeOut) {
		return isDisplayed(driver, themeDescription, "Visibility", timeOut, "themeDescription");

	}

	@FindBy(xpath = "//section[contains(@class,\"slds-fade-in-open\")]//button[text()=\"Save\"]")
	private WebElement saveButton;

	public WebElement saveButton(int timeOut) {
		return isDisplayed(driver, saveButton, "Visibility", timeOut, "saveButton");

	}

	@FindBy(xpath = "//input[@name=\"Search\"]")
	private WebElement themeSearchBox;

	public WebElement themeSearchBox(int timeOut) {
		return isDisplayed(driver, themeSearchBox, "Visibility", timeOut, "themeSearchBox");

	}

	public WebElement recordInTableOfTheme(String recordName, int timeOut) {

		String xpath = "//lightning-primitive-cell-factory//lightning-formatted-url/a[text()=\"" + recordName + "\"]";
		WebElement type = FindElement(driver, xpath, "recordInTableOfTheme", action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, recordName);

	}

	@FindBy(xpath = "//div[contains(@class,'slds-theme_shade')]//input[contains(@name,'Search')]")
	private WebElement searchIcon;

	/**
	 * @return the searchIcon
	 */
	public WebElement getSearchIcon(int timeOut) {
		return isDisplayed(driver, searchIcon, "Visibility", timeOut, "Search Icon");
	}

	public WebElement plusIconButtonInThemeOfAccount(String accountName, int timeOut) {

		String path = "//span[contains(text(), \"" + accountName
				+ "\")]/ancestor::div[contains(@class,\"slds-size_1-of-1\")]/div//lightning-icon[@data-id=\"Account\"][1]";
		return FindElement(driver, path, "plusIconButtonInThemeOfAccount", action.BOOLEAN, timeOut);
	}

	public WebElement addToThemePopUpSearchBox(int timeOut) {
		return isDisplayed(driver, addToThemePopUpSearchBox, "Visibility", timeOut, "addToThemePopUpSearchBox");
	}

	@FindBy(xpath = "//input[@placeholder=\"Search...\"]")
	private WebElement addToThemePopUpSearchBox;

	public WebElement addToThemePopUpSearchBoxDropDownValue(String accountName, int timeOut) {

		String path = "//span[text()=\"" + accountName + "\"]/parent::span/parent::div";
		return FindElement(driver, path, "addToThemePopUpSearchBoxDropDownValue", action.BOOLEAN, timeOut);
	}

	public WebElement addToThemePopUpSaveButton(int timeOut) {
		return isDisplayed(driver, addToThemePopUpSaveButton, "Visibility", timeOut, "addToThemePopUpSaveButton");
	}

	@FindBy(xpath = "//footer//button[text()=\"Save\"]")
	private WebElement addToThemePopUpSaveButton;

	public WebElement addToThemeLogNoteButton(String accountName, int timeOut) {

		String path = "//a[text()=\"" + accountName + "\"]/ancestor::tr//td//button[@title=\"Log Note\"]";
		return FindElement(driver, path, "addToThemeLogNoteButton", action.BOOLEAN, timeOut);
	}

	public WebElement successMsg(int timeOut) {
		return isDisplayed(driver, successMsg, "Visibility", timeOut, "successMsg");
	}

	@FindBy(xpath = "//div/span[contains(text(), \"was created\")]")
	private WebElement successMsg;

	public WebElement addToThemeAccountTableRecord(String accountName, String columnName, int timeOut) {

		String path = "//a[text()=\"" + accountName + "\"]/ancestor::tr//td[@data-label=\"" + columnName + "\"]";
		return FindElement(driver, path, "addToThemeLastInteractionDate", action.BOOLEAN, timeOut);
	}

	public WebElement themeGridsAddToTheme(String sectionName, int timeOut) {

		String xpath = "//span[@title='" + sectionName
				+ "']/ancestor::div[contains(@class,'slds-box')]//lightning-layout-item//lightning-icon[contains(@title,'Add To Theme')]";
		WebElement type = FindElement(driver, xpath, "themeGridsAddToTheme " + sectionName, action.SCROLLANDBOOLEAN,
				timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "themeGridsAddToTheme " + sectionName);

	}
	
	@FindBy(xpath = "//h2[text()='Add To Theme']/ancestor::header/following-sibling::*//button[@name='progress']")
	private WebElement addToThemeObjectSelectionButton;

	public WebElement addToThemeObjectSelectionButton(int timeOut) {

		return isDisplayed(driver, addToThemeObjectSelectionButton, "Visibility", timeOut,
				"addToThemeObjectSelectionButton");
	}
	
	public WebElement addToThemeObjectSelection(String objectName, int timeOut) {

		String xpath = "//span[contains(text(),'" + objectName + "')]/ancestor::lightning-base-combobox-item";
		WebElement type = FindElement(driver, xpath, "addToThemeObjectSelection " + objectName, action.SCROLLANDBOOLEAN,
				timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "addToThemeObjectSelection " + objectName);

	}
	
	@FindBy(xpath = "//input[@placeholder=\"Search...\"]")
	private WebElement addToThemePopUpSearchBox2;

	public WebElement addToThemePopUpSearchBox2(int timeOut) {
		return isDisplayed(driver, addToThemePopUpSearchBox2, "Visibility", timeOut, "addToThemePopUpSearchBox2");
	}
	
	public WebElement addToThemePopUpSearchBoxDropDownValue2(String accountName, int timeOut) {

		String path = "//li[text()=\"" + accountName + "\"]/parent::div";
		return FindElement(driver, path, "addToThemePopUpSearchBoxDropDownValue2", action.BOOLEAN, timeOut);
	}
	
	public WebElement advancedButtonOnTheme(int timeOut) {

		String xpath = "//div[@class=\"slds-modal__container\"]//a[text()=\"Advanced\"]";
		WebElement type = FindElement(driver, xpath, "advancedButtonOnTheme: ", action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "advancedButtonOnTheme: ");

	}
	
	@FindBy(xpath = "//lightning-layout[@class='slds-m-top_small slds-grid']//input")
	private WebElement searchByKeywordTextbox;

	public WebElement getSearchByKeywordTextbox(int timeOut) {
		return isDisplayed(driver, searchByKeywordTextbox, "Visibility", timeOut, "search By Keyword Textbox");

	}
	
	public WebElement getSearchForSpecificDropdownButton(int count, int timeOut) {
		String xpath = "(//div[@class='slds-combobox-group']//button[@data-value])[" + count + "]";

		return FindElement(driver, xpath, "Search For Specific Dropdown button", action.BOOLEAN, timeOut);
	}
	
	public WebElement getSearchForSpecificDropdown(String object, int timeOut) {
		String xpath = "//div[@class='slds-combobox-group']//div//span[@title='" + object
				+ "']/ancestor::lightning-base-combobox-item";

		List<WebElement> elements = FindElements(driver, xpath, "getSearchForSpecificDropdown");

		for (WebElement ele : elements) {

			if (ele.isDisplayed()) {
				return ele;
			}

		}
		return null;
	}
	
	public WebElement getSearchForSpecificSearch(int timeOut) {
		String xpath = "//div[@class='slds-combobox-group']//input";

		return FindElement(driver, xpath, "Search For Specific Search", action.BOOLEAN, timeOut);
	}
	
	@FindBy(xpath = "//p[text()=\"Search By Field Parameters\"]/following-sibling::lightning-layout//lightning-icon[@title='Add Row']")
	private WebElement getSearchForSpecificAddOption;

	public WebElement getSearchForSpecificAddOption(int timeOut) {
		return isDisplayed(driver, getSearchForSpecificAddOption, "Visibility", timeOut,
				"Search By Field For Operator Option");

	}
	
	public WebElement getSearchByFieldForFieldOption(Integer position, int timeOut) {
		String xpath = "(//div[contains(@class,'main-searchlook')]//input[@name='name'])[" + position + "]";

		return FindElement(driver, xpath, "getSearchByFieldForFieldOption" + position, action.BOOLEAN,
				timeOut);
	}
	
	public WebElement getValueForFieldParameter(String value, int timeout) {
		return isDisplayed(
				driver, FindElement(driver, "//span[@title='" + value + "']/ancestor::li//div",
						"Value For Field Parameters", action.BOOLEAN, timeout),
				"Visibility", timeout, "Value For Field Parameters");
	}
	
	public WebElement getSearchByFieldForOperatorOption(Integer position, int timeOut) {
		String xpath = "(//div[contains(@class,'main-searchlook')]//button[@data-value])[" + position + "]";

		return FindElement(driver, xpath, "Search For Specific Search Oprator DropDown Value: " + position,
				action.BOOLEAN, timeOut);
	}
	
	public WebElement getSearchByFieldForOperatorOptionValue(String operator, int timeOut) {
		String xpath = "//span[text()=\"" + operator + "\"]/ancestor::lightning-base-combobox-item";

		List<WebElement> elements = FindElements(driver, xpath, "getSearchByFieldForOperatorOptionValue");

		for (WebElement ele : elements) {

			if (ele.isDisplayed()) {
				return ele;
			}

		}
		return null;
	}
	
	public WebElement searchByFieldForValueOption(Integer position, int timeout) {
		return isDisplayed(driver,
				FindElement(driver,
						"(//div[contains(@class,\"main-searchlook\")]//input[@type=\"text\"])[" + position + "]",
						"Value For Field Parameters", action.BOOLEAN, timeout),
				"Visibility", timeout, "Value For Field Parameters: " + position);
	}
	
	@FindBy(xpath = "//*[text()=\"Theme\"]/..//span[@class=\"slds-pill__label\"]")
	private WebElement existingThemeNameVerifyInAddToTheme;

	public WebElement existingThemeNameVerifyInAddToTheme(int timeOut) {

		return isDisplayed(driver, existingThemeNameVerifyInAddToTheme, "Visibility", timeOut,
				"existingThemeNameVerifyInAddToTheme");
	}
	
	public WebElement addToThemeFooterButton2(String buttonName, int timeOut) {

		String xpath = "//div/button[text()=\"" + buttonName + "\"]";
		WebElement type = FindElement(driver, xpath, "addToThemeFooterButton2: " + buttonName, action.SCROLLANDBOOLEAN,
				timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "addToThemeFooterButton2: " + buttonName);

	}
	
	@FindBy(xpath = "//span[contains(@class,\"toastMessage\")]")
	private WebElement addToThemeInResearchErrorMsg;

	public WebElement addToThemeInResearchErrorMsg(int timeOut) {

		return isDisplayed(driver, addToThemeInResearchErrorMsg, "Visibility", timeOut, "addToThemeInResearchErrorMsg");
	}
	
	@FindBy(xpath = "//span[@title=\"Advanced\"]/ancestor::button")
	private WebElement advancedCollapsedExpandButtonInAddToTheme;

	public WebElement advancedCollapsedExpandButtonInAddToTheme(int timeOut) {

		return isDisplayed(driver, advancedCollapsedExpandButtonInAddToTheme, "Visibility", timeOut,
				"advancedCollapsedExpandButtonInAddToTheme");
	}
	
	@FindBy(xpath = "//span[text()=\"Select All\"]/ancestor::label/preceding-sibling::input")
	private WebElement allCategoriesCheckBoxOfAddToTheme;

	public WebElement allCategoriesCheckBoxOfAddToTheme(int timeOut) {

		return isDisplayed(driver, allCategoriesCheckBoxOfAddToTheme, "Visibility", timeOut,
				"allCategoriesCheckBoxOfAddToTheme");
	}
	
	public WebElement objectOrRecordTypeCheckBoxInAddToTheme(String objectOrRecordTypeName, int timeOut) {

		String xpath = "//lightning-base-formatted-text[text()=\"" + objectOrRecordTypeName
				+ "\"]/ancestor::th/preceding-sibling::td//input";
		WebElement type = FindElement(driver, xpath, "objectOrRecordTypeCheckBoxInAddToTheme: ",
				action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "objectOrRecordTypeCheckBoxInAddToTheme: ");

	}
	
	@FindBy(xpath = "//span[text()=\"Include all contacts for each firm\"]/ancestor::label/preceding-sibling::input")
	private WebElement includeAllContactsCheckBox;

	public WebElement includeAllContactsCheckBox(int timeOut) {

		return isDisplayed(driver, includeAllContactsCheckBox, "Visibility", timeOut, "includeAllContactsCheckBox");
	}
	
	@FindBy(xpath = "//span[text()=\"Theme is related to selected categories\" or text()=\"Record is already associated.\"]")
	private WebElement addToThemeInResearchSuccessMsg;

	public WebElement addToThemeInResearchSuccessMsg(int timeOut) {

		return isDisplayed(driver, addToThemeInResearchSuccessMsg, "Visibility", timeOut,
				"addToThemeInResearchSuccessMsg");
	}
	
	public WebElement addToThemeFooterCancelButton(int timeOut) {

		String xpath = "//footer//button[text()=\"Cancel\"]";
		WebElement type = FindElement(driver, xpath, "addToThemeFooterCancelButton ", action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "addToThemeFooterCancelButton ");

	}
	
	@FindBy(xpath = "//h2[text()='Add To Theme']/parent::header")
	private WebElement addToThemeHeader;

	public WebElement addToThemeHeader(int timeOut) {

		return isDisplayed(driver, addToThemeHeader, "Visibility", timeOut, "addToThemeHeader");
	}
	
	@FindBy(xpath = "//button[@title='Import Contacts']")
	private WebElement importContactbutton;

	public WebElement importContactbutton(int timeOut) {
		return isDisplayed(driver, importContactbutton, "Visibility", timeOut, "import Contact button");

	}
	
	@FindBy(xpath = "//h2[@title='Import Contacts']/..//..//span[@title='Firm']/ancestor::div//span[@class='slds-checkbox']//input[@class='datatable-select-all']")
	private WebElement importContactsSelectcheckbox;

	public WebElement importContactsSelectcheckbox(int timeOut) {
		return isDisplayed(driver, importContactsSelectcheckbox, "Visibility", timeOut, "import Contacts Select check box");

	}
	
	@FindBy(xpath = "//button[@title='Import']")
	private WebElement importbutton;

	public WebElement importbutton(int timeOut) {
		return isDisplayed(driver, importbutton, "Visibility", timeOut, "import button");

	}
	
	@FindBy(xpath = "(//button[@title='Next'])[1]")
	private WebElement EmailNextbutton;

	public WebElement EmailNextbutton(int timeOut) {
		return isDisplayed(driver, EmailNextbutton, "Visibility", timeOut, "Email Next button");

	}
	
	@FindBy(xpath = "//button[text()='Filter Contacts']")
	private WebElement FilterContact;

	public WebElement FilterContact(int timeOut) {
		return isDisplayed(driver, FilterContact, "Visibility", timeOut, "Filter Contact");

	}
	
	public WebElement themeEmailClearandApplyBtn(String button, int timeOut) {
		String xpath="//div[@class='slds-align_absolute-center']//button[@title='" +button+ "']";
		return  isDisplayed(driver, FindElement(driver,xpath, "", action.SCROLLANDBOOLEAN,20), "visibility",20,button+" theme email");
	}
	
	@FindBy(xpath = "(//input[@name='name' and @placeholder='Select Field'])[\"+(i+1)+\"]//ancestor::div[@class='headers']/following-sibling::div//button[contains(@class,'disabled')]")
	private WebElement operatordisable;

	public WebElement operatordisable(int timeOut) {

		return isDisplayed(driver, operatordisable, "Visibility", timeOut, "operator disable");
	}
	
	@FindBy(xpath = "//*[text()='2']")
	private WebElement secondrowcount;

	public WebElement secondrowcount(int timeOut) {

		return isDisplayed(driver, secondrowcount, "Visibility", timeOut, "second row count");
	}
	
	public WebElement emailcontactcheckbox(String ContactName, int timeOut) {

		String path = "//*[text()='" + ContactName + "']/ancestor::tr//label/span[@class='slds-checkbox_faux']";
		return FindElement(driver, path, "emailcontactcheckbox", action.BOOLEAN, timeOut);
	}
	
	@FindBy(xpath = "(//button[text()='Next'])[1]")
	private WebElement themeEmailNextbtn1;

	public WebElement themeEmailNextbtn1(int timeOut) {
		return isDisplayed(driver, themeEmailNextbtn1, "Visibility", timeOut, "theme Email Next btn1");

	}
	
	@FindBy(xpath = "(//button[text()='Next'])[1]")
	private WebElement themeEmailNextbtn2;

	public WebElement themeEmailNextbtn2(int timeOut) {
		return isDisplayed(driver, themeEmailNextbtn2, "Visibility", timeOut, "theme Email Next btn2");

	}
	
	@FindBy(xpath = "//span[@title='Name']")
	private WebElement themeEmailcontactname;

	public WebElement themeEmailcontactname(int timeOut) {
		return isDisplayed(driver, themeEmailcontactname, "Visibility", timeOut, "theme Email contact name");

	}
	
	public List<WebElement> themeemailcontactname(int timeOut) {
		return FindElements(driver,
				"//div[contains(@class,'dt-outer-container')]//th[@data-label='Name']");
	}
	
	public WebElement emailSelectAllcontactcheckbox( int timeOut) {

		String path = "//span[text()='Select All']";
		return FindElement(driver, path, "emailSelectAllcontactcheckbox", action.BOOLEAN, timeOut);
	}
	
	@FindBy(xpath = "(//button[text()='Previous'])[1]")
	private WebElement themeEmailPreviousbtn1;

	public WebElement themeEmailPreviousbtn1(int timeOut) {
		return isDisplayed(driver, themeEmailPreviousbtn1, "Visibility", timeOut, "theme Email Previous btn1");

	}
	
	public WebElement themeEmailBCCcheckbox( int timeOut) {

		String path = "//span[@part='input-checkbox']//input[@name='BCC']";
		return FindElement(driver, path, "themeEmailBCCcheckbox", action.BOOLEAN, timeOut);
	}
	
	@FindBy(xpath = "(//button[text()='Send'])[1]")
	private WebElement themeEmailSendbtn1;

	public WebElement themeEmailSendbtn1(int timeOut) {
		return isDisplayed(driver, themeEmailSendbtn1, "Visibility", timeOut, "theme Email Send btn1");

	}
	
	@FindBy(xpath = "//div[@class='toastContent slds-notify__content']")
	private WebElement themeEmailSendSuccessmsg;

	public WebElement themeEmailSendSuccessmsg(int timeOut) {
		return isDisplayed(driver, themeEmailSendSuccessmsg, "Visibility", timeOut, "theme Email Send Success msg");

	}
	
   public WebElement themecontactname(String ContactName , int timeOut) {
		
		String xpath = "//*[text()='" + ContactName + "']";
				
	WebElement type = FindElement(driver, xpath, ContactName, action.SCROLLANDBOOLEAN, timeOut);	
	
	return isDisplayed(driver, type, "Visibility", timeOut, ContactName);
	}
}
