package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.FindElements;
import static com.navatar.generic.CommonLib.isDisplayed;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.StaleElementReferenceException;
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
	
	@FindBy(xpath = "//span[text()='Associated to the Theme']")
	private WebElement successMsg1;

	public WebElement successMsg1(int timeOut) {
		return isDisplayed(driver, successMsg1, "Visibility", timeOut, "successMsg1");
	}
	
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
	
	@FindBy(xpath = "//input[@placeholder=\"Search Themes...\"]")
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
	
	@FindBy(xpath = "//button[text()='Add to Theme']")
	private WebElement AddtoThemeButton;

	public WebElement getAddtoThemeButton(int timeOut) {
		return isDisplayed(driver, AddtoThemeButton, "Visibility", timeOut, "Add to Theme Button");

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
   
   @FindBy(xpath = "//div[text()='No items to display']")
	private WebElement themeNoItemDisplay;

	public WebElement themeNoItemDisplay(int timeOut) {

		return isDisplayed(driver, themeNoItemDisplay, "Visibility", timeOut, "themeNoItemDisplay");
	}
	
	public List<WebElement> themeOuterButtons() {
		return FindElements(driver,
				"//ul[@class='slds-button-group-list']/li//button[contains(@class,'slds-button_neutral')]",
				"themeOuterButtons");
	}
	
	@FindBy(xpath = "//*[text()='Show more actions']/ancestor::lightning-button-menu")
	private WebElement downArrowButtonInTheme;

	public WebElement downArrowButtonInTheme(int timeOut) {

		return isDisplayed(driver, downArrowButtonInTheme, "Visibility", timeOut, "downArrowButtonInTheme");
	}
	
	public List<WebElement> themeInnerButtons() {
		return FindElements(driver, "//div[contains(@class,'slds-dropdown_right')]//a/span", "themeInnerButtons");
	}
	
	@FindBy(xpath = "//label[text()='Existing Theme Name']/..//span[@title]")
	private WebElement existingThemeNameVerify;

	public WebElement existingThemeNameVerify(int timeOut) {

		return isDisplayed(driver, existingThemeNameVerify, "Visibility", timeOut, "existingThemeNameVerify");
	}
	
	@FindBy(xpath = "//label[text()='Existing Theme Name']/parent::slot//button[contains(@title,'Remove')]")
	private WebElement existingThemeRemoveButtton;

	public WebElement existingThemeRemoveButtton(int timeOut) {

		return isDisplayed(driver, existingThemeRemoveButtton, "Visibility", timeOut, "existingThemeRemoveButtton");
	}
	
	@FindBy(xpath = "//label[text()='Existing Theme Name']/ancestor::*//input[@placeholder='Search Themes...']")
	private WebElement existingThemeNameInput;

	public WebElement existingThemeNameInput(int timeOut) {

		return isDisplayed(driver, existingThemeNameInput, "Visibility", timeOut, "existingThemeNameInput");
	}
	
	public WebElement existingThemeNameDropDown(String existingThemeName, int timeOut) {

		String xpath = "//*[text()='" + existingThemeName + "']/ancestor::div[@data-name]";
		WebElement type = FindElement(driver, xpath, existingThemeName, action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, existingThemeName);

	}
	
	@FindBy(xpath = "//*[text()='Copy All Interactions']/ancestor::span//input")
	private WebElement copyAllInteractionCheckBox;

	public WebElement copyAllInteractionCheckBox(int timeOut) {

		return isDisplayed(driver, copyAllInteractionCheckBox, "Visibility", timeOut, "copyAllInteractionCheckBox");
	}
	
	public WebElement copyThemeSaveOrCancelButton(String buttonName, int timeOut) {

		String xpath = "//div[@class='slds-modal__footer']//button[text()=\"" + buttonName + "\"]";

		List<WebElement> elements = FindElements(driver, xpath, "copyThemeSaveOrCancelButton: " + buttonName);

		for (WebElement ele : elements) {

			if (ele.isDisplayed()) {
				return ele;
			}

		}
		return null;

	}
	
	@FindBy(xpath = "//h1[text()='Copy Theme']")
	private WebElement copyThemeHeader;

	public WebElement copyThemeHeader(int timeOut) {

		return isDisplayed(driver, copyThemeHeader, "Visibility", timeOut, "copyThemeHeader");
	}
	
	public WebElement copyThemeErrorMsg(int timeOut) {

		String xpath = "//h2[text()='Review the errors on this page.']/parent::div/following-sibling::p";
		WebElement type = FindElement(driver, xpath, "Copy Theme Error Msg: ", action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "Copy Theme Error Msg: ");

	}
	
	@FindBy(xpath = "//button[contains(@aria-label,\"Custom Theme Picklist\")]")
	private WebElement customThemePicklistComboxBox;

	public WebElement customThemePicklistComboxBox(int timeOut) {

		return isDisplayed(driver, customThemePicklistComboxBox, "Visibility", timeOut, "customThemePicklistComboxBox");
	}
	
	public WebElement customThemePicklistComboxBoxDropDownValue(String value, int timeOut) {

		String xpath = "//span[text()=\"" + value + "\"]/ancestor::lightning-base-combobox-item";
		WebElement type = FindElement(driver, xpath, "customThemePicklistComboxBoxDropDownValue: " + value,
				action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "customThemePicklistComboxBoxDropDownValue: " + value);

	}
	
	public WebElement updateThemeFooterButton(String buttoName, int timeOut) {
		List<WebElement> eleList = FindElements(driver, "//lightning-button//button[text()=\"" + buttoName + "\"]",
				"Save Button");
		for (WebElement webElement : eleList) {
			webElement = isDisplayed(driver, webElement, "Visibility", 2, "updateThemeFooterButton: " + buttoName);
			if (webElement != null) {
				return webElement;
			}
		}

		return null;
	}
	
	@FindBy(xpath = "//div/h1[text()=\"New Theme\"]")
	private WebElement newThemeHeader;

	public WebElement newThemeHeader(int timeOut) {

		return isDisplayed(driver, newThemeHeader, "Visibility", timeOut, "newThemeHeader");

	}
	
	@FindBy(xpath = "//lightning-icon[@title=\"Close\"]")
	private WebElement newThemeCloseIcon;

	public WebElement newThemeCloseIcon(int timeOut) {

		return isDisplayed(driver, newThemeCloseIcon, "Visibility", timeOut, "newThemeCloseIcon");

	}
	
	public WebElement themeFieldOfTextArea(String labelName, int timeOut) {

		String xpath = "//label[text()=\"" + labelName + "\"]/ancestor::lightning-textarea//textarea";
		WebElement type = FindElement(driver, xpath, "themeFieldOfTextArea: ", action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "themeFieldOfTextArea: ");

	}
	
	@FindBy(xpath = "(//span[text()='Description']/ancestor::lightning-layout/following-sibling::*//li)[1]")
	private WebElement themeDescriptionText;

	public WebElement themeDescriptionText(int timeOut) {

		return isDisplayed(driver, themeDescriptionText, "Visibility", timeOut, "themeDescriptionText");
	}
	
	@FindBy(xpath = "//ul//a[text()=\"show more\"]")
	private WebElement descriptionShowMoreLink;

	public WebElement descriptionShowMoreLink(int timeOut) {

		return isDisplayed(driver, descriptionShowMoreLink, "Visibility", timeOut, "descriptionShowMoreLink");

	}
	
	@FindBy(xpath = "//span[text()='Description']/ancestor::lightning-layout/following-sibling::*//p/..//li")
	private WebElement themeTeamText;

	public WebElement themeTeamText(int timeOut) {

		return isDisplayed(driver, themeTeamText, "Visibility", timeOut, "themeTeamText");
	}
	
	@FindBy(xpath = "//*[@title=\"Add Team Member\"]")
	private WebElement addTeamMemberPlusIconButton;

	public WebElement addTeamMemberPlusIconButton(int timeOut) {

		return isDisplayed(driver, addTeamMemberPlusIconButton, "Visibility", timeOut, "addTeamMemberPlusIconButton");

	}
	
	@FindBy(xpath = "//*[text()='New Team Member']/ancestor::header/following-sibling::div//*[text()='Theme']/parent::div/following-sibling::div//span/span")
	private WebElement teamMemberThemeName;

	public WebElement teamMemberThemeName(int timeOut) {

		return isDisplayed(driver, teamMemberThemeName, "Visibility", timeOut, "teamMemberThemeName");
	}
	
	@FindBy(xpath = "//*[text()='New Team Member']/ancestor::header/following-sibling::div//*[text()='Member']/ancestor::label/following-sibling::div//input")
	private WebElement teamMemberThemeMemberInputBox;

	public WebElement teamMemberThemeMemberInputBox(int timeOut) {

		return isDisplayed(driver, teamMemberThemeMemberInputBox, "Visibility", timeOut,
				"teamMemberThemeMemberInputBox");
	}
	
	public WebElement teamMemberThemeMemberSelection(String memberName, int timeOut) {

		String xpath = "//*[text()='New Team Member']/ancestor::header/following-sibling::div//*[text()='Member']/ancestor::label/following-sibling::*//div[@title='"
				+ memberName + "']/ancestor::a";
		WebElement type = FindElement(driver, xpath, "teamMemberThemeMemberSelection: " + memberName,
				action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "teamMemberThemeMemberSelection: " + memberName);

	}
	
	@FindBy(xpath = "//*[text()='New Team Member']/ancestor::header/following-sibling::div//*[text()='Role']/ancestor::span/following-sibling::*//a")
	private WebElement teamMemberRoleButton;

	public WebElement teamMemberRoleButton(int timeOut) {

		return isDisplayed(driver, teamMemberRoleButton, "Visibility", timeOut, "teamMemberRoleButton");
	}
	
	public WebElement teamMemberRoleSelection(String role, int timeOut) {

		String xpath = "//a[@role=\"menuitemradio\" and text()=\"" + role + "\"]";
		WebElement type = FindElement(driver, xpath, "teamMemberRoleSelection: " + role, action.SCROLLANDBOOLEAN,
				timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "teamMemberRoleSelection: " + role);

	}
	
	public WebElement teamMemberButtonName(String buttonName, int timeOut) {

		String xpath = "//*[text()=\"New Team Member\"]/ancestor::header/following-sibling::footer//span[text()=\""
				+ buttonName + "\"]/ancestor::button";
		WebElement type = FindElement(driver, xpath, "teamMemberButtonName: " + buttonName, action.SCROLLANDBOOLEAN,
				timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "teamMemberButtonName: " + buttonName);

	}
	
	@FindBy(xpath = "//span[contains(@class,\"toastMessage\")][contains(normalize-space(), \"was created.\")]")
	private WebElement addTeamMemberSuccessMsg;

	public WebElement addTeamMemberSuccessMsg(int timeOut) {

		return isDisplayed(driver, addTeamMemberSuccessMsg, "Visibility", timeOut, "addTeamMemberSuccessMsg");

	}
	
	@FindBy(xpath = "//ul[contains(@class,\"errorsList\")]//li")
	private WebElement addTeamMemberErrorMsg;

	public WebElement addTeamMemberErrorMsg(int timeOut) {

		return isDisplayed(driver, addTeamMemberErrorMsg, "Visibility", timeOut, "addTeamMemberErrorMsg");

	}
	
	
	
	public List<WebElement> AddTeamMemberNameDataElements() {
		return FindElements(driver,
				"//*[contains(@class,\"themteamdatatable\")]//tbody//tr/th[@data-label=\"Name\"]" + "",
				"AddTeamMemberHeaders");
	}
	
	@FindBy(xpath = "//span[text()='Delete']/ancestor::button")
	private WebElement themeDeleteConfirmButton;

	public WebElement themeDeleteConfirmButton(int timeOut) {

		return isDisplayed(driver, themeDeleteConfirmButton, "Visibility", timeOut, "themeDeleteConfirmButton");
	}
	
	@FindBy(xpath = "//span[contains(@class,'toastMessage')][contains(normalize-space(), \"was deleted.\")]")
	private WebElement themeDeleteTastMsg;

	public WebElement themeDeleteTastMsg(int timeOut) {

		return isDisplayed(driver, themeDeleteTastMsg, "Visibility", timeOut, "themeDeleteTastMsg");
	}
	
	@FindBy(xpath = "//*[@title='Remove Team Member']")
	private WebElement removeTeamMemberMinusIconButton;

	public WebElement removeTeamMemberMinusIconButton(int timeOut) {

		return isDisplayed(driver, removeTeamMemberMinusIconButton, "Visibility", timeOut,
				"removeTeamMemberMinusIconButton");

	}
	
	@FindBy(xpath = "//input[contains(@class,\"datatable-select-all\")]")
	private WebElement teamMemberAllCheckBoxInput;

	public WebElement teamMemberAllCheckBoxInput(int timeOut) {
		return isDisplayed(driver, teamMemberAllCheckBoxInput, "Visibility", timeOut, "teamMemberAllCheckBoxInput");

	}
	
	public WebElement checBoxOfTeamMembers(String teamMemberName, String roleName, int timeOut) {

		String xpath = "//*[contains(@class,\"themteamdatatable\")]//tbody//tr//*[text()=\"" + teamMemberName
				+ "\"]/ancestor::tr//*[text()=\"" + roleName + "\"]/ancestor::tr//*[@type=\"checkbox\"]";
		WebElement type = FindElement(driver, xpath,
				"checBoxOfTeamMembers: " + teamMemberName + " with Role: " + roleName, action.SCROLLANDBOOLEAN,
				timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut,
				"checBoxOfTeamMembers: " + teamMemberName + " with Role: " + roleName);

	}
	
	public WebElement teamMemberButton(String teamMemberButtonName, int timeOut) {

		String xpath = "//lightning-button//button[text()=\"" + teamMemberButtonName + "\"]";
		WebElement type = FindElement(driver, xpath, "teamMemberButtonName: " + teamMemberButtonName,
				action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "teamMemberButtonName: " + teamMemberButtonName);

	}
	
	@FindBy(xpath = "//p[text()=\"No items to display\"]")
	private WebElement noRecordFoundThemeTeam;

	public WebElement noRecordFoundThemeTeam(int timeOut) {
		return isDisplayed(driver, noRecordFoundThemeTeam, "Visibility", timeOut, "noRecordFoundThemeTeam");

	}
	
	@FindBy(xpath = "//div[contains(@class,\"main-Container-themeteam\")]//p")
	private WebElement themeTeamErrorMsg;

	public WebElement themeTeamErrorMsg(int timeOut) {
		return isDisplayed(driver, themeTeamErrorMsg, "Visibility", timeOut, "themeTeamErrorMsg");

	}
	
	public List<WebElement> researchResultsGridCountsInTheme() {
		return FindElements(driver,
				"//div[contains(@class,'slds-col slds-size_9-of-12')]//div[contains(@class,'slds-col slds-size_1-of-1')]//span[contains(@class,'slds-page-header__title') and not(contains(text(),'(0)'))]",
				"researchResultsGridCountsInTheme");
	}
	
	public List<WebElement> recordsNamesOnTaggedSection(String tagNameOnTaggedSection, int timeOut) {

		String xpath = "//span[text()='" + tagNameOnTaggedSection + "']/ancestor::table//lightning-formatted-url//a";

		List<WebElement> recordsNameOnTagged = FindElements(driver, xpath, "recordsNameOnTaggedSection");

		for (int i = 0; i < 3; i++) {
			if (recordsNameOnTagged.size() == 0) {
				recordsNameOnTagged = FindElements(driver, xpath, "recordsNameOnTaggedSection");
			} else {
				break;
			}
		}

		return recordsNameOnTagged;
	}
	
	public WebElement themeRecordPageName(String tabNameFromPropertyFile, String themeRecordName, int timeOut) {

		String xpath = "//div[contains(@class,\"entityNameTitle\") and text()=\"" + tabNameFromPropertyFile
				+ "\"]/following-sibling::*/*[text()=\"" + themeRecordName + "\"]";
		try {
			return FindElement(driver, xpath, "Record name: " + themeRecordName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Record name: " + themeRecordName, action.SCROLLANDBOOLEAN, timeOut);
		}

	}
	
	public WebElement recordsNameOnTaggedSection(String tagNameOnTaggedSection, String themeRecordName, int timeOut) {

		String xpath = "//span[text()=\"" + tagNameOnTaggedSection
				+ "\"]/ancestor::table//lightning-formatted-url//a[text()=\"" + themeRecordName + "\"]";
		try {
			return FindElement(driver, xpath, "Record name: " + themeRecordName, action.BOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "Record name: " + themeRecordName, action.BOOLEAN, timeOut);
		}

	}
	
	public List<WebElement> themeGridsHeaders(String sectionName) {
		return FindElements(driver, "(//span[@title=\"" + sectionName
				+ "\" and not(contains(text(),\"(0)\"))]/ancestor::div[contains(@class,\"slds-box\")]/following-sibling::div)[1]//table//thead//th//span[@class=\"slds-truncate\"]",
				"themeGridsHeaders");
	}
	
	public WebElement themeGridsViewAllButton(String sectionName, int timeOut) {

		String xpath = "(//span[@title=\"" + sectionName
				+ "\" and not(contains(text(),\"(0)\"))]/ancestor::div[contains(@class,\"slds-box\")]/following-sibling::div)[1]/div/div//button[text()=\"View All\"]";
		WebElement type = FindElement(driver, xpath, "themeGridsViewAllButton", action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "themeGridsViewAllButton");

	}
	
	public WebElement themeGridsSortingButton(String sectionName, int timeOut) {

		String xpath = "//span[@title='" + sectionName
				+ "']/ancestor::ul/following-sibling::ul//span[text()='Sorted by']/ancestor::ul//button";
		WebElement type = FindElement(driver, xpath, "themeGridsSortingButton: " + sectionName, action.SCROLLANDBOOLEAN,
				timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "themeGridsSortingButton: " + sectionName);

	}
	
	public WebElement themeGridsSortingButtonOptionsValue(String sectionName, String sortByValue, int timeOut) {

		String xpath = "//span[@title='" + sectionName
				+ "']/ancestor::ul/following-sibling::ul//span[text()='Sorted by']/ancestor::ul//button/../following-sibling::div//*[contains(text(),'"
				+ sortByValue + "')]/ancestor::lightning-base-combobox-item";
		WebElement type = FindElement(driver, xpath,
				"themeGridsSortingButtonOptionsValue, Section: " + sectionName + " & SortByValue: " + sortByValue,
				action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut,
				"themeGridsSortingButtonOptionsValue, Section: " + sectionName + " & SortByValue: " + sortByValue);

	}
	
	public List<WebElement> interactionAllIcons() {

		String xpath = "//th//lightning-icon";
		List<WebElement> ele = FindElements(driver, xpath, "Interactions Tab All Icons");
		for (int i = 0; i < 3; i++) {
			if (ele.size() == 0) {
				ele = FindElements(driver, xpath, "Interactions Tab All Icons");
			} else
				break;
		}

		return ele;
	}
	
	public WebElement themeGridsclumnAndRowWiseData(String sectionName, int columnIndex, int rowIndex, int timeOut) {

		String xpath = "(//span[@title=\"" + sectionName
				+ "\" and not(contains(text(),\"(0)\"))]/ancestor::div[contains(@class,\"slds-box\")]/following-sibling::div)[1]//table//tbody/tr["
				+ rowIndex + "]/*[" + columnIndex + "]";

		String xpath2 = "(//span[@title=\"" + sectionName
				+ "\" and not(contains(text(),\"(0)\"))]/ancestor::div[contains(@class,\"slds-box\")]/following-sibling::div)[1]//table//tbody/tr["
				+ rowIndex + "]/*[" + columnIndex + "]//*";

		List<WebElement> types = FindElements(driver, xpath2, sectionName);
		for (WebElement type : types) {
			System.out.println("Type of Element: " + type.getTagName());
			if (type.getTagName().equals("a") || type.getTagName().equals("input")
					|| type.getTagName().equals("button")) {
				return type;
			}
		}
		return isDisplayed(driver, FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, timeOut), "Visibility",
				timeOut, sectionName);

	}
	
	public WebElement popUpOfIntractionPage(int timeOut) {

		String xpath = "//lightning-layout//h2";
		WebElement ele = FindElement(driver, xpath, "subject of Interaction page: ", action.SCROLLANDBOOLEAN, timeOut);
		try {
			return isDisplayed(driver, ele, "Visibility", timeOut, "subject of Interaction page: ");

		} catch (StaleElementReferenceException e) {
			return isDisplayed(driver, ele, "Visibility", timeOut, "subject of Interaction page: ");
		}
	}
	
	public WebElement dataOfSectionBasedOnColumn(String sectionName, String recordName, String columnName,
			int timeOut) {

		String xpath = "(//span[@title=\"" + sectionName
				+ "\" and not(contains(text(),\"(0)\"))]/ancestor::div[contains(@class,\"slds-box\")]/following-sibling::div)[1]//table//*[text()=\""
				+ recordName + "\"]/ancestor::tr/*[@data-label=\"" + columnName + "\"]";
		try {
			return FindElement(driver, xpath, "dataOfSectionBasedOnColumn: " + sectionName + " of Record: " + recordName
					+ " of Column: " + columnName, action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "dataOfSectionBasedOnColumn: " + sectionName + " of Record: " + recordName
					+ " of Column: " + columnName, action.SCROLLANDBOOLEAN, timeOut);
		}

	}
	
	public WebElement themeGridsRemoveIcon(String sectionName, int timeOut) {

		String xpath = "//span[@title='" + sectionName
				+ "']/ancestor::div[contains(@class,'slds-box')]//lightning-layout-item//lightning-icon[contains(@title,'Remove')]";
		WebElement type = FindElement(driver, xpath, "themeGridsRemoveIcon " + sectionName, action.SCROLLANDBOOLEAN,
				timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "themeGridsRemoveIcon " + sectionName);

	}
	
	public WebElement checkBoxOfRecordInAGrid(String sectionName, String recordName, Integer columnNumber,
			int timeOut) {

		String xpath = "(//span[@title=\"" + sectionName
				+ "\" and not(contains(text(),\"(0)\"))]/ancestor::div[contains(@class,\"slds-box\")]/following-sibling::div)[1]//table//tbody/tr/*["
				+ columnNumber + "]//*[text()=\"" + recordName + "\"]/ancestor::tr//*[@type=\"checkbox\"]";
		WebElement type = FindElement(driver, xpath, "checkBoxOfRecordInAGrid: " + sectionName + " with Record: "
				+ recordName + " with Column Number: " + columnNumber, action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "checkBoxOfRecordInAGrid: " + sectionName
				+ " with Record: " + recordName + " with Column Number: " + columnNumber);

	}
	
	public WebElement checkBoxOfAllRecordInAGrid(String sectionName, int timeOut) {

		String xpath = "(//span[@title=\"" + sectionName
				+ "\" and not(contains(text(),\"(0)\"))]/ancestor::div[contains(@class,\"slds-box\")]/following-sibling::div)[1]//table//thead//tr//*[@type=\"checkbox\"]";
		WebElement type = FindElement(driver, xpath, "checkBoxOfAllRecordInAGrid: " + sectionName,
				action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "checkBoxOfAllRecordInAGrid: " + sectionName);

	}
	
	public WebElement footerButtonOfAGrid(String sectionName, String buttonName, int timeOut) {

		String xpath = "(//span[@title=\"" + sectionName
				+ "\" and not(contains(text(),\"(0)\"))]/ancestor::div[contains(@class,\"slds-box\")]/following-sibling::div)[1]//button[text()=\""
				+ buttonName + "\"]";
		WebElement type = FindElement(driver, xpath, "footerButtonOfAGrid: " + buttonName, action.SCROLLANDBOOLEAN,
				timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "footerButtonOfAGrid: " + buttonName);

	}
	
	public WebElement logANoteButtonBasedOnSection(String sectionName, String recordName, int timeOut) {

		String xpath = "(//span[@title='" + sectionName
				+ "' and not(contains(text(),'(0)'))]/ancestor::div[contains(@class,'slds-box')]/following-sibling::div[contains(@class,'around_none')])[1]//table//a[text()=\""
				+ recordName + "\"]/ancestor::tr//td//button[@title=\"Log Note\"]";
		try {
			return FindElement(driver, xpath,
					"logANoteButtonBasedOnSection: " + sectionName + " of Record: " + recordName,
					action.SCROLLANDBOOLEAN, timeOut);
		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath,
					"logANoteButtonBasedOnSection: " + sectionName + " of Record: " + recordName,
					action.SCROLLANDBOOLEAN, timeOut);
		}

	}
	
	@FindBy(xpath = "//button[text()=\"Yes\"]")
	private WebElement yesButton;

	public WebElement yesButton(int timeOut) {
		return isDisplayed(driver, yesButton, "Visibility", timeOut, "yesButton");

	}
	
	@FindBy(xpath = "//div[@role=\"alert\"]//following-sibling::p")
	private WebElement newThemeErrorMsg;

	public WebElement newThemeErrorMsg(int timeOut) {
		return isDisplayed(driver, newThemeErrorMsg, "Visibility", timeOut, "newThemeErrorMsg");

	}
	
	public List<WebElement> themeGrids() {
		return FindElements(driver,
				"//div[contains(@class,\"slds-size_9-of-12 main-Container\")]//span[contains(@class,\"slds-page-header__title\")]",
				"themeGrids");
	}
	
	public WebElement editIconLinkOfTeamMember(String member, String role, int timeout) {
		return isDisplayed(driver,
				FindElement(driver,
						"//*[text()=\"" + member
								+ "\"]/ancestor::th/following-sibling::td[@data-label=\"Role\"]//span[text()=\"" + role
								+ "\"]/..//button",
						"Value For Field Parameters", action.SCROLLANDBOOLEAN, timeout),
				"Visibility", timeout, "editIconLinkOfTeamMember of member: " + member + " Role: " + role);
	}
	
	@FindBy(xpath = "//button[@name=\"picklist\"]")
	private WebElement teamMemberEditRolePicklistButton;

	public WebElement teamMemberEditRolePicklistButton(int timeOut) {
		return isDisplayed(driver, teamMemberEditRolePicklistButton, "Visibility", timeOut,
				"teamMemberEditRolePicklistButton");

	}
	
	public WebElement teamMemberEditRolePicklistValue(String role, int timeout) {
		return isDisplayed(driver,
				FindElement(driver, "//span[text()=\"" + role + "\"]/ancestor::lightning-base-combobox-item",
						"teamMemberEditRolePicklistValue", action.SCROLLANDBOOLEAN, timeout),
				"Visibility", timeout, "teamMemberEditRolePicklistValue" + role);
	}
	
	public WebElement teamMemberEditRoleFooterButtonName(String buttonName, int timeout) {
		return isDisplayed(driver,
				FindElement(driver, "//div[contains(@class,\"footer\")]//button[text()=\"" + buttonName + "\"]",
						"teamMemberEditRoleFooterButtonName: " + buttonName, action.SCROLLANDBOOLEAN, timeout),
				"Visibility", timeout, "teamMemberEditRoleFooterButtonName" + buttonName);
	}
	
	public List<String> themeGridFirstColumnText(String sectionName) {
		return FindElements(driver, "(//span[@title='" + sectionName
				+ "' and not(contains(text(),'(0)'))]/ancestor::div[contains(@class,'slds-box')]/following-sibling::div)[1]//table//tbody/tr/th",
				"themeGridFirstColumnText").stream().map(data -> data.getText()).collect(Collectors.toList());
	}
	
	public List<String> themeGridsHeadersText(String sectionName) {
		return FindElements(driver, "(//span[@title=\"" + sectionName
				+ "\" and not(contains(text(),\"(0)\"))]/ancestor::div[contains(@class,\"slds-box\")]/following-sibling::div)[1]//table//thead//th//span[@class=\"slds-truncate\"]",
				"themeGridsHeaders").stream().map(x -> x.getText())
				.collect(Collectors.toList());
	}
	
	public List<String> AddTeamMemberNameData() {
		return AddTeamMemberNameDataElements().stream()
				.map(x ->  x.getText()).collect(Collectors.toList());
	}
	
	public List<String> AddTeamMemberDataAsPerIndex(Integer index) {
		return FindElements(driver, "//*[contains(@class,\"themteamdatatable\")]//tbody//tr[" + index + "]/*",
				"themeDataAsPerIndex").stream().map(x ->  x.getText())
				.collect(Collectors.toList());
	}

	@FindBy(xpath = "//label[text()='Description updated']")
	private WebElement themeDescriptionlabel;

	public WebElement getthemeDescriptionlabel(int timeOut) {
		return isDisplayed(driver, themeDescriptionlabel, "Visibility", timeOut, "theme Description label");

	}
}
