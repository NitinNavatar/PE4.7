package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.FindElements;
import static com.navatar.generic.CommonLib.isDisplayed;

import java.util.List;

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
	
	@FindBy(xpath = "//flexipage-tab2[contains(@class,'slds-show')]//div[text()='No items to display']")
	private WebElement callListNoItemDisplay;

	public WebElement GetCallListNoItemDisplay(int timeOut) {

		return isDisplayed(driver, callListNoItemDisplay, "Visibility", timeOut, "Call List No Item Display");
	}
	
	@FindBy(xpath = "//span[text()='My Call List']")
	private WebElement myCallList;

	public WebElement getMyCallList(int timeOut) {

		return isDisplayed(driver, myCallList, "Visibility", timeOut, "My Call List");
	}
	
	public List<WebElement> getMyCallListTableHeader() {
		return FindElements(driver,"//table[contains(@class,'table_resizable-cols')]//th//span[@class='slds-truncate']");
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
	
	@FindBy(xpath = "//button[@title='Import Contacts']")
	private WebElement importContactbutton;

	public WebElement importContactbutton(int timeOut) {
		return isDisplayed(driver, importContactbutton, "Visibility", timeOut, "import Contact button");

	}
	
	public WebElement FirmInImportContacts(String accountName, int timeOut) {
		String path = "//h2[@title='Import Contacts']/../following-sibling::div//a[text()='"+ accountName +"']";
		return FindElement(driver, path, "Firms In Import Contacts", action.BOOLEAN, timeOut);
	}
	
	@FindBy(xpath = "//h1//lightning-formatted-text")
	private WebElement firmName;

	public WebElement GetFirmName(int timeOut) {

		return isDisplayed(driver, firmName, "Visibility", timeOut, "Firm Name");
	}
	
	public WebElement RecordTypeInImportContacts(String recordType, int timeOut) {

		String path = "//h2[@title='Import Contacts']/../following-sibling::div//lightning-base-formatted-text[text()='"+ recordType +"']";
		return FindElement(driver, path, "RecordTypes In Import Contacts", action.BOOLEAN, timeOut);
	}

	public WebElement getCheckboxForAllFirmsInImportContactPopup(int timeOut) {

		String path = "//h2[@title='Import Contacts']/../..//span[text()='Firm']/ancestor::th/preceding-sibling::th//input";
		return FindElement(driver, path, "getCheckboxForAllFirmsInImportContactPopup", action.BOOLEAN, timeOut);
	}
	
	@FindBy(xpath = "//button[@title='Cancel']")
	private WebElement cancelButtonOnImportContactPopup;

	public WebElement getCancelButtonOnImportContactPopup(int timeOut) {
		return isDisplayed(driver, cancelButtonOnImportContactPopup, "Visibility", timeOut, "Cancel Button On Import Contact Popup");

	}
	
	public WebElement getCheckboxForFirmInImportContactPopup(String firmName, int timeOut) {

		String path = "//a[text()='"+ firmName +"']/ancestor::th/preceding-sibling::td//input";
		return FindElement(driver, path, "CheckboxForFirmInImportContactPopup", action.BOOLEAN, timeOut);
	}
	
	@FindBy(xpath = "//button[@title='Import']")
	private WebElement importbutton;

	public WebElement importbutton(int timeOut) {
		return isDisplayed(driver, importbutton, "Visibility", timeOut, "import button");

	}
	
	@FindBy(xpath = "//span[text()='The Contacts has been successfully imported.']")
	private WebElement importToastMsg;

	public WebElement getImportToastMsg(int timeOut) {
		return isDisplayed(driver, importToastMsg, "Visibility", timeOut, "Import Toast Msg");

	}
	
	public WebElement ContactOnMyCallList(String Record, int timeOut) {

		String path = "//div[@class='main-Container-calllist']//a[text()='"+ Record +"']";
		return FindElement(driver, path, "Contact On Call List", action.BOOLEAN, timeOut);
	}
	
	public WebElement getCallList(String firmName, int timeOut) {

		String path = "//span[@title='My Call List']/../../following-sibling::*//a[text()='"+ firmName +"']";
		return FindElement(driver, path, "Call List", action.BOOLEAN, timeOut);
	}
	
	@FindBy(xpath = "//button[@title='Close']")
	private WebElement closeIconOnImportContactPopup;

	public WebElement getCloseIconOnImportContactPopup(int timeOut) {
		return isDisplayed(driver, closeIconOnImportContactPopup, "Visibility", timeOut, "Close Icon On Import Contact Popup");

	}
	
	@FindBy(xpath = "//ul[@role='tablist']/li/a[text()='Call List']")
	private WebElement callListButton;

	public WebElement getCallListButton(int timeOut) {
		return isDisplayed(driver, callListButton, "Visibility", timeOut, "Call List Button");

	}
	
	public List<WebElement> researchResultsGridCountsInTheme() {
		return FindElements(driver,
				"//div[contains(@class,'slds-col slds-size_9-of-12')]//div[contains(@class,'slds-col slds-size_1-of-1')]//span[contains(@class,'slds-page-header__title') and not(contains(text(),'(0)'))]",
				"researchResultsGridCountsInTheme");
	}
	
	@FindBy(xpath = "//div[@class='main-Container-calllist']//div[text()='No items to display']")
	private WebElement themeNoItemDisplay;

	public WebElement themeNoItemDisplay(int timeOut) {

		return isDisplayed(driver, themeNoItemDisplay, "Visibility", timeOut, "themeNoItemDisplay");
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
	
	public WebElement getCallListHeader(String Header, int timeOut) {

		String path = "//span[@title='My Call List']/../../following-sibling::*//span[text()='"+ Header +"']";
		return FindElement(driver, path, "Call List", action.BOOLEAN, timeOut);
	}
	
	@FindBy(xpath = "//h2[text()='Call Notes']")
	private WebElement callNotesHeader;

	public WebElement GetCallNotesHeader(int timeOut) {

		return isDisplayed(driver, callNotesHeader, "Visibility", timeOut,
				"callNotesHeader");
	}
	
	@FindBy(xpath = "//lightning-icon[@icon-name='utility:expand']//lightning-primitive-icon")
	private WebElement expandOption;

	public WebElement getExpandOption(int timeOut) {

		return isDisplayed(driver, expandOption, "Visibility", timeOut,
				"expandOption");
	}
	
	@FindBy(xpath = "//footer//button[@title='Cancel']")
	private WebElement cancelButtonOnCallNotes;

	public WebElement getCancelButtonOnCallNotes(int timeOut) {

		return isDisplayed(driver, cancelButtonOnCallNotes, "Visibility", timeOut,
				"cancelButtonOnCallNotes");
	}
	
	@FindBy(xpath = "//lightning-icon[@title='Close']")
	private WebElement closeButtonOnCallNotes;

	public WebElement getCloseButtonOnCallNotes(int timeOut) {

		return isDisplayed(driver, closeButtonOnCallNotes, "Visibility", timeOut,
				"closeButtonOnCallNotes");
	}
	
	public WebElement LogNoteOnMyCallList(String Record, int timeOut) {

		String path = "(//a[@title='"+ Record +"']/ancestor::th/following-sibling::td//button[@title='Log Note'])[2]";
		String path1 = "//a[@title='"+ Record +"']/ancestor::th/following-sibling::td//button[@title='Log Note']";

		if(isDisplayed(driver, FindElement(driver, path, "Contact On Call List", action.BOOLEAN, timeOut), "Visibility", timeOut,
				"cancelButtonOnCallNotes") != null) {
			return isDisplayed(driver, FindElement(driver, path, "Contact On Call List", action.BOOLEAN, timeOut), "Visibility", timeOut,
					"cancelButtonOnCallNotes"); 
		} else {
			return isDisplayed(driver, FindElement(driver, path1, "Contact On Call List", action.BOOLEAN, timeOut), "Visibility", timeOut,
					"cancelButtonOnCallNotes"); 
		}
	}
	
	public WebElement HeadersOnMyCallList(String Record, int timeOut) {

		String path = "//span[@title='"+Record+"']";
		return FindElement(driver, path, "Contact On Call List", action.BOOLEAN, timeOut);
	}
	
	public WebElement HeadersOnImportContact(String Record, int timeOut) {

		String path = "//section[@class='slds-modal slds-fade-in-open main-Container']//span[@class='slds-th__action']/span[text()='"+Record+"']";
		return FindElement(driver, path, "HeadersOnImportContact", action.BOOLEAN, timeOut);
	}
	
	public WebElement themeGridsAddToTheme(String sectionName, int timeOut) {

		String xpath = "//span[@title='" + sectionName
				+ "']/ancestor::div[contains(@class,'slds-box')]//lightning-layout-item//lightning-icon[contains(@title,'Add To Theme')]";
		WebElement type = FindElement(driver, xpath, "themeGridsAddToTheme " + sectionName, action.SCROLLANDBOOLEAN,
				timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "themeGridsAddToTheme " + sectionName);

	}
	
//	@FindBy(xpath = "//h2[text()='Add to Theme']/ancestor::header/following-sibling::*//button[@name='progress']")
	@FindBy(xpath = "//h2[text()='Add to Theme']/ancestor::header/following-sibling::div//input[@type='search']")

	private WebElement addToThemeObjectSelectionButton;

	public WebElement addToThemeObjectSelectionButton(int timeOut) {

		return isDisplayed(driver, addToThemeObjectSelectionButton, "Visibility", timeOut,
				"addToThemeObjectSelectionButton");
	}
	
	public WebElement addToThemeObjectSelection(String objectName, int timeOut) {

		//String xpath = "//span[contains(text(),'" + objectName + "')]/ancestor::lightning-base-combobox-item";
		String xpath = "//button//span[contains(text(),'" + objectName + "')]/ancestor::lightning-base-combobox";

		WebElement type = FindElement(driver, xpath, "addToThemeObjectSelection " + objectName, action.SCROLLANDBOOLEAN,
				timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "addToThemeObjectSelection " + objectName);

	}
	
	@FindBy(xpath = "//input[@placeholder='Search Themes...']")
	private WebElement addToThemePopUpSearchBox2;

	public WebElement addToThemePopUpSearchBox2(int timeOut) {
		return isDisplayed(driver, addToThemePopUpSearchBox2, "Visibility", timeOut, "addToThemePopUpSearchBox2");
	}
	
	public WebElement addToThemePopUpSearchBoxDropDownValue2(String accountName, int timeOut) {

//		String path = "//span[text()='" + accountName + "']/ancestor::li";
		String path = "//li[text()='" + accountName + "']";

		return FindElement(driver, path, "addToThemePopUpSearchBoxDropDownValue2", action.BOOLEAN, timeOut);
	}
	
	public WebElement advancedButtonOnTheme(int timeOut) {

		String xpath = "//div[@class=\"slds-modal__container\"]//a[text()=\"Advanced\"]";
		WebElement type = FindElement(driver, xpath, "advancedButtonOnTheme: ", action.SCROLLANDBOOLEAN, timeOut);

		return isDisplayed(driver, type, "Visibility", timeOut, "advancedButtonOnTheme: ");

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
	
//	@FindBy(xpath = "//span[text()=\"Theme is related to selected categories\" or text()=\"Record is already associated.\"]")
	@FindBy(xpath = "//span[text()='Theme is associated with selected categories.' or text()='Record is already associated.' or text()='All Contact was successfully added to your Theme.']")
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

}
