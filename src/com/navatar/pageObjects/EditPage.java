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
	
	@FindBy (xpath = "//button[text()='Save']")
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
	
	@FindBy (xpath = "//*[text()='Data Provider']/following-sibling::div")
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
	
	
	
}
