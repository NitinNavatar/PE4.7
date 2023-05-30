package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.isDisplayed;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.navatar.generic.EnumConstants.action;

public class FieldAndRelationshipPage extends BasePageBusinessLayer {

	public FieldAndRelationshipPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}

	public WebElement getQucikSearchInFieldAndRelationshipPage(int timeOut) {
		WebElement ele = null;
		String xpath = "";
		xpath = "//span[text()='Fields & Relationships']/ancestor::div[@id='setupComponent']//input[@placeholder='Quick Find']";
		ele = FindElement(driver, xpath, "search text box in ", action.SCROLLANDBOOLEAN, 30);
		return isDisplayed(driver, ele, "visibility", 30, "quick search text box in ");
	}

	@FindBy(xpath = "//iframe[contains(@title,'Salesforce - Enterprise Edition')]")
	private WebElement fieldsAndRelationshipsIframe;

	public WebElement getfieldsAndRelationshipsIframe(int timeOut) {
		ThreadSleep(15000);
		return isDisplayed(driver, fieldsAndRelationshipsIframe, "Visibility", timeOut,
				"Fields & Relationships iframe");
	}

	@FindBy(xpath = "//iframe[@title='Picklist Edit: Industry ~ Salesforce - Enterprise Edition']")
	private WebElement piclistEditiframe;

	public WebElement getpiclistEditiframe(int timeOut) {
		ThreadSleep(15000);
		return isDisplayed(driver, piclistEditiframe, "Visibility", timeOut, "Piclist Edit iframe");
	}

	@FindBy(xpath = "//label[text()='Label']/parent::td/following-sibling::td//input")
	private WebElement editPicklistLabelName;

	public WebElement getEditPicklistLabelName(int timeOut) {

		return isDisplayed(driver, editPicklistLabelName, "Visibility", timeOut, "Piclist Edit Label Name");
	}

	@FindBy(xpath = "//input[@title='Save']")
	private WebElement editPicklistSaveButton;

	public WebElement geteditPicklistSaveButton(int timeOut) {

		return isDisplayed(driver, editPicklistSaveButton, "Visibility", timeOut, "Piclist Edit save button");
	}

	@FindBy(xpath = "//iframe[@title='Find and Replace Value: Industry ~ Salesforce - Enterprise Edition']")
	private WebElement findAndReplaceIframe;

	public WebElement getfindAndReplaceIframe(int timeOut) {

		return isDisplayed(driver, findAndReplaceIframe, "Visibility", timeOut, "Find and Replace Iframe");
	}

	@FindBy(xpath = "//label[text()='Replace value on records with ']/following-sibling::select")
	private WebElement replaceValueDropDown;

	public WebElement getreplaceValueDropDown(int timeOut) {

		return isDisplayed(driver, replaceValueDropDown, "Visibility", timeOut, "Replace Value Drop Down Button");
	}

	@FindBy(xpath = "//input[@id='ReplaceValueWithNullValue']")
	private WebElement replaceValueWithNull;

	public WebElement getreplaceValueWithNull(int timeOut) {

		return isDisplayed(driver, replaceValueWithNull, "Visibility", timeOut, "Replace Value with Null Value");
	}

	@FindBy(xpath = "//input[@title='New Industry Picklist Values']")
	private WebElement picklistNewButton;

	public WebElement getpicklistNewButton(int timeOut) {

		return isDisplayed(driver, picklistNewButton, "Visibility", timeOut, "New Button for Picklist");
	}

	@FindBy(xpath = "//iframe[@title='Add Picklist Values: Industry ~ Salesforce - Enterprise Edition']")
	private WebElement addPicklistIFrame;

	public WebElement getaddPicklistIFrame(int timeOut) {

		return isDisplayed(driver, addPicklistIFrame, "Visibility", timeOut, "Add picklist Iframe");
	}

	@FindBy(xpath = "//table[@class='detailList']//textarea")
	private WebElement addPicklistTextArea;

	public WebElement getaddPicklistTextArea(int timeOut) {

		return isDisplayed(driver, addPicklistTextArea, "Visibility", timeOut, "Add picklist Textarea");
	}

	@FindBy(xpath = "//input[@title='Save']")
	private WebElement saveButton;

	public WebElement getsaveButton(int timeOut) {
		return isDisplayed(driver, saveButton, "Visibility", timeOut, "Save button");
	}

	@FindBy(xpath = "//input[@title='Replace']")
	private WebElement replaceButton;

	public WebElement getreplaceButton(int timeOut) {
		return isDisplayed(driver, replaceButton, "Visibility", timeOut, "Replace button");
	}

	@FindBy(xpath = "//label[text()='Exact Value Changing From']/parent::td/following-sibling::td//input[@type='text']")
	private WebElement valueChangingFrom;

	public WebElement getvalueChangingFrom(int timeOut) {
		return isDisplayed(driver, valueChangingFrom, "Visibility", timeOut, "Value Changing From textbox");
	}

	@FindBy(xpath = "//label[text()='Select Value Changing To']/parent::td/following-sibling::td//select")
	private WebElement valueChangingTo;

	public WebElement getvalueChangingTo(int timeOut) {
		return isDisplayed(driver, valueChangingTo, "Visibility", timeOut, "Value Changing To Dropdown");
	}

	@FindBy(xpath = "//iframe[contains(@title,'Find and Replace Picklist: Industry')]")
	private WebElement ReplaceOptionIframe;

	public WebElement getReplaceOptionIframe(int timeOut) {
		return isDisplayed(driver, ReplaceOptionIframe, "Visibility", timeOut, "Find and Replace Iframe");
	}

	@FindBy(xpath = "//h1[text()='Replace Picklist Confirmation']")
	private WebElement ReplaceConfirmationMessage;

	public WebElement getReplaceConfirmationMessage(int timeOut) {
		return isDisplayed(driver, ReplaceConfirmationMessage, "Visibility", timeOut, "Replace Confirmation Message");
	}

	@FindBy(xpath = "//iframe[contains(@title,'Replace Picklist Confirmation')]")
	private WebElement ReplacePicklistConfirmationiframe;

	public WebElement getReplacePicklistConfirmationiframe(int timeOut) {
		return isDisplayed(driver, ReplacePicklistConfirmationiframe, "Visibility", timeOut,
				"Replace Picklist Confirmation Iframe");
	}

	@FindBy(xpath = "//a[text()='Deactivate']/ancestor::tr//th")
	private WebElement activePicklist;

	public WebElement getactivePicklist(int timeOut) {
		return isDisplayed(driver, activePicklist, "Visibility", timeOut, "Active Picklist");
	}

}
