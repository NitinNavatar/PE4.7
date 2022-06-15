package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.SDGCreationLabel;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;

import static com.navatar.generic.CommonLib.*;

import java.util.List;

public class SDGPage extends BasePageBusinessLayer {

	public SDGPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//button[text()='Add Field']")
	private WebElement addFieldButton;

	@FindBy(xpath = "//select[@name='fieldSelect']")
	private WebElement fieldSelectDropdown;

	public WebElement getAddFieldButton(String projectName, int timeOut) {
		return isDisplayed(driver, addFieldButton, "Visibility", timeOut, "Add Field buttton");
	}

	public WebElement getFieldSelectDropdown(String projectName, int timeOut) {
		return isDisplayed(driver, fieldSelectDropdown, "Visibility", timeOut, "field select dropdown");
	}

	@FindBy(xpath = "//span[contains(@title,'Fields')]/ancestor::article//button[text()='New']")
	private WebElement fieldNewButton;

	public WebElement getFieldNewButton(String projectName, int timeOut) {
		return isDisplayed(driver, fieldNewButton, "Visibility", timeOut, "fieldNewButton");
	}

	/**
	 * @return the contactFullNameLabel
	 */
	public WebElement getSDGHeaderValueInViewMode(String projectName, String value, int timeOut) {

		String xpath = "//*[text()='Sortable Data Grid']/..//*[text()='" + value + "']";
		WebElement ele = FindElement(driver, xpath, value, action.SCROLLANDBOOLEAN, timeOut);
		scrollDownThroughWebelement(driver, ele, value);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, value);
		return isDisplayed(driver, ele, "Visibility", timeOut, value);

	}

	@FindBy(xpath = "//span[text()='More options']/..")
	private WebElement moreOptionsButton;

	public WebElement getmoreOptionsButton(String projectName, int timeOut) {
		return isDisplayed(driver, moreOptionsButton, "Visibility", timeOut, "more Options Button");
	}

	public WebElement getSDGValue(String projectName, String header, String value, int timeOut) {

		String xpath = "//div//h2//a[text()='" + header + "']/../../../../../following-sibling::*//table//*[text()='"
				+ value + "']";
		WebElement ele = FindElement(driver, xpath, value, action.SCROLLANDBOOLEAN, timeOut);
		scrollDownThroughWebelement(driver, ele, value);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, value);
		return isDisplayed(driver, ele, "Visibility", timeOut, value);

	}

	@FindBy(xpath = "//input[@placeholder='Search this list...']")
	private WebElement sdgSearchBox;

	public WebElement sdgSearchBox(int timeOut) {
		return isDisplayed(driver, sdgSearchBox, "Visibility", timeOut, "SDG Search Box");
	}

	@FindBy(xpath = "//div[contains(@class,'active')]//button[text()='Edit']")
	private WebElement sdgEditButton;

	public WebElement sdgEditButton(int timeOut) {
		return isDisplayed(driver, sdgEditButton, "Visibility", timeOut, "SDG Edit Button");
	}

	public WebElement sdgLabelValueElement(int timeOut, String SDGCreationLabel) {
		WebElement ele;
		String xpath = "//span[text()='" + SDGCreationLabel
				+ "']/parent::div/following-sibling::div//lightning-formatted-text";
		return ele = FindElement(driver, xpath, "SDGLabelValue Element, Label: " + SDGCreationLabel, action.SCROLLANDBOOLEAN,
				timeOut);

	}

	@FindBy(xpath = "//div[contains(@class,'active')]//button[text()='Edit']")
	private WebElement editButton;

	public WebElement getEditButton(String projectName, int timeOut) {
		return isDisplayed(driver, editButton, "Visibility", timeOut, "Edit Button");
	}

	@FindBy(xpath = "//span[text()='All Rows']/parent::label/following-sibling::div//input[@type='checkbox']")
	private WebElement allRowCheckbox;

	public WebElement getAllRowCheckbox() {
		return allRowCheckbox;
	}

	@FindBy(xpath = "//button[text()='Save']")
	private WebElement saveButton;

	public WebElement getSaveButton(String projectName, int timeOut) {
		return isDisplayed(driver, saveButton, "Visibility", timeOut, "Save Button");
	}

	@FindBy(xpath = "//span[text()='Sortable Data Grid']")
	private WebElement confirmationSaveMessage;

	public WebElement getconfirmationSaveMessage(String projectName, int timeOut) {
		return isDisplayed(driver, confirmationSaveMessage, "Visibility", timeOut, "Save confirmation message");
	}

	@FindBy(xpath = "//input[@placeholder='Search this list...']")
	private WebElement sdgSearchbox;

	public WebElement getsdgSearchbox(String projectName, int timeOut) {
		return isDisplayed(driver, sdgSearchbox, "Visibility", timeOut, "SDG Searchbox");
	}
	

	@FindBy(xpath = "//a[@id='relatedListsTab__item']")
	private WebElement relatedTabOnSDG;

	public WebElement getrelatedTabOnSDG(int timeOut) {
		return isDisplayed(driver, relatedTabOnSDG, "Visibility", timeOut, "Related tab on SDG");
	}
	
	@FindBy(xpath = "//tr[@class='slds-hint-parent']/th[@tabindex='0']//span[@id='window']")
	private WebElement nameFieldButton;

	public WebElement getnameFieldButton(int timeOut) {
		return isDisplayed(driver, nameFieldButton, "Visibility", timeOut, "Name Field Button");
	}
	
	@FindBy(xpath = "//label[text()='Filter Sequence']/parent::lightning-combobox//button")
	private WebElement filterSequenceButton;

	public WebElement getfilterSequenceButton(int timeOut) {
		return isDisplayed(driver, filterSequenceButton, "Visibility", timeOut, "Filter Sequence Button");
	}
	
	@FindBy(xpath = "//label[text()='Filter Sequence']/parent::lightning-combobox//lightning-base-combobox-item//span[@class='slds-truncate']")
	private List<WebElement> filterSequenceDropdownList;

	public List<WebElement> getfilterSequenceDropdownList() {
		return filterSequenceDropdownList;
	}
	
	@FindBy(xpath = "//span[contains(@class,'toastMessage')]")
	private WebElement sdgSaveConfirmationMsg;

	public WebElement getsdgSaveConfirmationMsg(int timeOut) {
		return isDisplayed(driver, sdgSaveConfirmationMsg, "Visibility", timeOut, "SDG save button");
	}
	
	@FindBy(xpath = "//button[text()='Save']")
	private WebElement sdgSaveBtn;

	public WebElement getsdgSaveBtn(int timeOut) {
		return isDisplayed(driver, sdgSaveBtn, "Visibility", timeOut, "SDG save button");
	}
	
	
	
	
	@FindBy(xpath = "//tbody//lst-formatted-text[text()='Name']/ancestor::td/following-sibling::td//button")
	private WebElement nameEroBtn;

	public WebElement getnameEroBtn(int timeOut) {
		return isDisplayed(driver, nameEroBtn, "Visibility", timeOut, "Name ero button");
	}
	
	@FindBy(xpath = "//a[@title='Edit']")
	private WebElement sdgPageEditButton;

	public WebElement getsdgPageEditButton(int timeOut) {
		return isDisplayed(driver, sdgPageEditButton, "Visibility", timeOut, "Edit button");
	}
	
	
	public WebElement getsdgGridCheckbox(String sdgGridName, int timeOut) {
		String xPath="//a[text()='"+sdgGridName+"']/ancestor::article//lightning-input[@class='slds-form-element']//input[@type='checkbox']";
		return isDisplayed(driver, FindElement(driver, xPath, "SDG Grid Checkbox", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "SDG Grid Checkbox");
	}
	

	@FindBy(xpath = "//span[text()='1 record has error. Kindly resolve them and try again.']")
	private WebElement errorMessageAfterSaveBlankRecord;

	public WebElement geterrorMessageAfterSaveBlankRecord(int timeOut) {
		return isDisplayed(driver, errorMessageAfterSaveBlankRecord, "Visibility", timeOut, "Error Message");
	}
	
	@FindBy(xpath = "//span[contains(text(),'has error. Kindly resolve them and try again.')]")
	private WebElement errorMessageAfterSaveTwoRecord;

	public WebElement geterrorMessageAfterSaveTwoRecord(int timeOut) {
		return isDisplayed(driver, errorMessageAfterSaveTwoRecord, "Visibility", timeOut, "Error Message");
	}
	
	@FindBy(xpath = "//div[contains(text(),'[Name] field is required.')]")
	private WebElement triangleErrorIcon;

	public WebElement gettriangleErrorIcon(int timeOut) {
		return isDisplayed(driver, triangleErrorIcon, "Visibility", timeOut, "Triangle error message");
	}
	
	@FindBy(xpath = "//div[contains(text(),'permission to edit')]")
	private WebElement triangleIconPermissionError;

	public WebElement gettriangleIconPermissionError(int timeOut) {
		return isDisplayed(driver, triangleIconPermissionError, "Visibility", timeOut, "Triangle Permission error message");
	}
	
	@FindBy(xpath = "//div[contains(text(),'value outside of valid range')]")
	private WebElement triangleIconOutsideRangeError;

	public WebElement gettriangleIconOutsideRangeError(int timeOut) {
		return isDisplayed(driver, triangleIconOutsideRangeError, "Visibility", timeOut, "Triangle outside range message");
	}
	
	
	
	
	
	@FindBy(xpath = "//span[text()='Your changes are saved.']")
	private WebElement UpdateMessageAfterSaveRecord;

	public WebElement getUpdateMessageAfterSaveRecord(int timeOut) {
		return isDisplayed(driver, UpdateMessageAfterSaveRecord, "Visibility", timeOut, "Update Message");
	}
	
	
	
	
	
	
	
	

}
