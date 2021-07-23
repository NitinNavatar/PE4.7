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

public class EditPage extends BasePageBusinessLayer {

	public EditPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	@FindBy (xpath = "//iframe[@class='surfaceFrame']")
	private WebElement editPageFrame;

	/**
	 * @return the editPageFrame
	 */
	public WebElement getEditPageFrame(String projectName,int timeOut) {
		return isDisplayed(driver, editPageFrame, "Visibility", timeOut, "Edit Page Frame");
	}
	
	@FindBy (xpath = "//*[text()='SDG Config Data Provider']/following-sibling::div/input")
	private WebElement sdgConfigDataProviderTextBox;

	/**
	 * @return the sdgConfigDataProviderTextBox
	 */
	public WebElement getsdgConfigDataProviderTextBox(String projectName,int timeOut) {
		return isDisplayed(driver, sdgConfigDataProviderTextBox, "Visibility", timeOut, "sdg Config Data Provider TextBox");
	}
	
	
	@FindBy (xpath = "//*[text()='Default SDG Toggle']/following-sibling::div/input")
	private WebElement defaultSDGToggleTextBox;

	/**
	 * @return the defaultSDGToggleTextBox
	 */
	public WebElement getDefaultSDGToggleTextBox(String projectName,int timeOut) {
		return isDisplayed(driver, defaultSDGToggleTextBox, "Visibility", timeOut, "Default SDG Toggle TextBox");
	}
	
	@FindBy (xpath = "//button[text()='Save']")
	private WebElement editPageSaveButton;

	/**
	 * @return the editPageSaveButton
	 */
	public WebElement getEditPageSaveButton(String projectName,int timeOut) {
		return isDisplayed(driver, editPageSaveButton, "Visibility", timeOut, "Edit Page Save Button");
	}
	
	@FindBy (xpath = "//a[contains(@class,'backButton')]")
	private WebElement editPageBackButton;

	/**
	 * @return the editPageBackButton
	 */
	public WebElement getEditPageBackButton(String projectName,int timeOut) {
		return isDisplayed(driver, editPageBackButton, "Visibility", timeOut, "Edit Page Back Button");
	}
	
	
	@FindBy (xpath = "//*[text()='Search...']/..//*[@placeholder='Search...']")
	private WebElement editPageSeachTextBox;

	/**
	 * @return the editPageSeachTextBox
	 */
	public WebElement getEditPageSeachTextBox(String projectName,int timeOut) {
		return isDisplayed(driver, editPageSeachTextBox, "Visibility", timeOut, "Edit Page Search TextBox");
	}
	
	@FindBy (xpath = "//*[text()='Data Provider']/following-sibling::div//input")
	private WebElement elgDataProviderTextBox;

	/**
	 * @return the elgDataProviderTextBox
	 */
	public WebElement getElgDataProviderTextBox(String projectName,int timeOut) {
		return isDisplayed(driver, elgDataProviderTextBox, "Visibility", timeOut, "ELG Data Provider TextBox");
	}
	
	
	@FindBy (xpath = "//*[text()='Title']/..//following-sibling::div//input[@type='text']")
	private WebElement elgTitleTextBox;

	/**
	 * @return the elgTitleTextBox
	 */
	public WebElement getElgTitleTextBox(String projectName,int timeOut) {
		return isDisplayed(driver, elgTitleTextBox, "Visibility", timeOut, "ELG Title TextBox");
	}
	
	
	@FindBy (xpath = "//*[text()='Enable Toggle']/..//preceding-sibling::input")
	private WebElement enableToggleCheckBox;

	/**
	 * @return the enableToggleCheckBox
	 */
	public WebElement getEnableToggleCheckBox(String projectName,int timeOut) {
		return isDisplayed(driver, enableToggleCheckBox, "Visibility", timeOut, "Enable Toggle CheckBox");
	}
	
	@FindBy(xpath = "//*[text()='Field Set Name']/following-sibling::div/input")
	private WebElement fieldSetNameTextBox;

	public WebElement getFieldSetNameTextBox(int timeOut) {
		return isDisplayed(driver, fieldSetNameTextBox, "Visibility", timeOut, "field set name text box");
	}
	
	@FindBy(xpath = "//a[@aria-describedby='backButton']")
	private WebElement backButton;

	public WebElement getBackButton(int timeOut) {
		return isDisplayed(driver, backButton, "Visibility", timeOut, "back button");
	}
	
	@FindBy (xpath = "//a[text()='Add Component(s) Here']")
	private WebElement addComponent;

	/**
	 * @return the addComponent
	 */
	public WebElement getAddComponent(String projectName,int timeOut) {
		return isDisplayed(driver, addComponent, "Visibility", timeOut, "Add Component(s) Here");
	}
	
	
	@FindBy (xpath = "//*[text()='Data Provider']/following-sibling::div//*[@data-key='search']/../..")
	private WebElement elgDataProviderTextBoxSearchIcon;

	/**
	 * @return the elgDataProviderTextBoxSearchIcon
	 */
	public WebElement getElgDataProviderTextBoxSearchIcon(String projectName,int timeOut) {
		return isDisplayed(driver, elgDataProviderTextBoxSearchIcon, "Visibility", timeOut, "ELG Data Provider TextBox Search Box Icon");
	}
	
	@FindBy(xpath = "//div[@data-label='Navatar Fieldset']/div[@class='toolbox']")
	private WebElement fieldSetCompoentXpath;

	public WebElement getFieldSetCompoentXpath(int timeOut) {
		scrollDownThroughWebelement(driver, fieldSetCompoentXpath, "");
		return fieldSetCompoentXpath;
	}
	
	@FindBy(xpath = "//*[contains(text(),'Image Field  Name')]/following-sibling::div/input")
	private WebElement imageFieldNameTextBox;

	public WebElement getImageFieldNameTextBox(int timeOut) {
		return isDisplayed(driver, imageFieldNameTextBox, "Visibility", timeOut, "image field name text box");
	}
	
	@FindBy (xpath = "//button[text()='Activate']")
	private WebElement activateButton;

	/**
	 * @return the activateButton
	 */
	public WebElement getActivateButton(String projectName,int timeOut) {
		return isDisplayed(driver, activateButton, "Visibility", timeOut, "Activate Button");
	}
	
	@FindBy (xpath = "(//button[text()='Save'])[2]")
	private WebElement editPageSaveButton2;

	/**
	 * @return the editPageSaveButton
	 */
	public WebElement getEditPageSaveButton2(String projectName,int timeOut) {
		return isDisplayed(driver, editPageSaveButton2, "Visibility", timeOut, "Edit Page Save Button2");
	}
	
	@FindBy (xpath = "//button[text()='Finish']")
	private WebElement finishButton2;

	/**
	 * @return the finishButton2
	 */
	public WebElement getFinishButton2(String projectName,int timeOut) {
		return isDisplayed(driver, finishButton2, "Visibility", timeOut, "Finish Button2");
	}
	
	
	public WebElement getFieldTextbox(String projectName,String pageLabel,int timeOut) {
		String label=pageLabel.replace("_", " ");
		String xpath = "//*[text()='"+label+"']/following-sibling::div/input";
		WebElement ele=FindElement(driver, xpath,pageLabel.toString(), action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Finish Button2");
	}
	
	@FindBy (xpath = "//*[text()='Number of Records to Display']/following-sibling::div[2]")
	private WebElement noOfRecordsErrorMessage;

	/**
	 * @return the finishButton2
	 */
	public WebElement getnoOfRecordsErrorMessage(String projectName,int timeOut) {
		return isDisplayed(driver, noOfRecordsErrorMessage, "Visibility", timeOut, "noOfRecordsErrorMessage");
	}
	@FindBy (xpath = "//span[text()='Expanded']/../..//input[@type='checkbox']")
	private WebElement expandedCheckbox;

	/**
	 * @return the finishButton2
	 */
	public WebElement getexpandedCheckbox(String projectName,int timeOut) {
		return isDisplayed(driver, expandedCheckbox, "Visibility", timeOut, "expandedCheckbox");
	}
	
	@FindBy (xpath = "//*[@data-id='Field Set Name']/div/*/div[@role='alert']")
	private WebElement noOfRecordsErrorPopup;

	/**
	 * @return the finishButton2
	 */
	public WebElement getnoOfRecordsErrorPopup(String projectName,int timeOut) {
		return isDisplayed(driver, noOfRecordsErrorPopup, "Visibility", timeOut, "noOfRecordsErrorPopup");
	}
	@FindBy (xpath = "//h2[text()='Error']/../following-sibling::div[contains(@class,'footer')]//button[text()='OK']")
	private WebElement noOfRecordsErrorPopupOK;

	/**
	 * @return the finishButton2
	 */
	public WebElement getnoOfRecordsErrorPopupOK(String projectName,int timeOut) {
		return isDisplayed(driver, noOfRecordsErrorPopupOK, "Visibility", timeOut, "noOfRecordsErrorPopupOK");
	}
	@FindBy (xpath = "//div[contains(@class,'elementProxyContainer')]//a")
	private WebElement calendarOnEditPage;

	/**
	 * @return the finishButton2
	 */
	public WebElement getcalendarOnEditPage(String projectName,int timeOut) {
		return isDisplayed(driver, calendarOnEditPage, "Visibility", timeOut, "calendarOnEditPage");
	}
	
}
