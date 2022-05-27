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
	public WebElement getAddFieldButton(String projectName,int timeOut) {
		return isDisplayed(driver, addFieldButton, "Visibility", timeOut, "Add Field buttton");
	}
	
	public WebElement getFieldSelectDropdown(String projectName,int timeOut) {
		return isDisplayed(driver, fieldSelectDropdown, "Visibility", timeOut, "field select dropdown");
	}
	
	@FindBy(xpath = "//span[contains(@title,'Fields')]/ancestor::article//button[text()='New']")
	private WebElement fieldNewButton;
	public WebElement getFieldNewButton(String projectName,int timeOut) {
		return isDisplayed(driver, fieldNewButton, "Visibility", timeOut, "fieldNewButton");
	}
	
	/**
	 * @return the contactFullNameLabel
	 */
	public WebElement getSDGHeaderValueInViewMode(String projectName,String value,int timeOut) {
		
		String xpath ="//*[text()='Sortable Data Grid']/..//*[text()='"+value+"']";
		WebElement ele = FindElement(driver, xpath,value , action.SCROLLANDBOOLEAN, timeOut);
		scrollDownThroughWebelement(driver, ele, value);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, value);
		return isDisplayed(driver, ele, "Visibility", timeOut,value);
		
		
	}
	@FindBy(xpath = "//span[text()='More options']/..")
	private WebElement moreOptionsButton;
	public WebElement getmoreOptionsButton(String projectName,int timeOut) {
		return isDisplayed(driver, moreOptionsButton, "Visibility", timeOut, "more Options Button");
	}
	public WebElement getSDGValue(String projectName,String header,String value,int timeOut) {
		
		String xpath ="//div//h2//a[text()='"+header+"']/../../../../../following-sibling::*//table//*[text()='"+value+"']";
		WebElement ele = FindElement(driver, xpath,value , action.SCROLLANDBOOLEAN, timeOut);
		scrollDownThroughWebelement(driver, ele, value);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, value);
		return isDisplayed(driver, ele, "Visibility", timeOut,value);
		
		
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
	
	
	@FindBy(xpath = "//button[text()='Edit']")
	private WebElement editButton;
	public WebElement getEditButton(String projectName,int timeOut) {
		return isDisplayed(driver, editButton, "Visibility", timeOut, "Edit Button");
	}

	@FindBy(xpath = "//span[text()='All Rows']/parent::label/following-sibling::div//input[@type='checkbox']")
	private WebElement allRowCheckbox;
	public WebElement getAllRowCheckbox() {
		return allRowCheckbox;
	}

	@FindBy(xpath = "//button[text()='Save']")
	private WebElement saveButton;
	public WebElement getSaveButton(String projectName,int timeOut) {
		return isDisplayed(driver, saveButton, "Visibility", timeOut, "Save Button");
	}

	@FindBy(xpath = "//span[text()='Sortable Data Grid']")
	private WebElement confirmationSaveMessage;
	public WebElement getconfirmationSaveMessage(String projectName,int timeOut) {
		return isDisplayed(driver, confirmationSaveMessage, "Visibility", timeOut, "Save confirmation message");
	}
	
	@FindBy(xpath = "//input[@placeholder='Search this list...']")
	private WebElement sdgSearchbox;
	public WebElement getsdgSearchbox(String projectName,int timeOut) {
		return isDisplayed(driver, sdgSearchbox, "Visibility", timeOut, "SDG Searchbox");
	}
	
	
	
}
