package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.isDisplayed;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static com.navatar.generic.CommonLib.FindElement;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.action;

public class EmailMyTemplatesPage extends SetupPageBusinessLayer {

	public EmailMyTemplatesPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath="//a[contains(text(),'Create New Folder')]")
	private WebElement createNewFolderLink;

	/**
	 * @return the createNewFolderLink
	 */
	public WebElement getCreateNewFolderLink(String environment,String mode,int timeOut) {
		return isDisplayed(driver, createNewFolderLink, "Visibility", timeOut, "create New Folder Link");
	}
	
	@FindBy(xpath="//label[text()='Email Template Folder Label']/../following-sibling::td//input")
	private WebElement emailTemplateFolderLabelTextBox;

	/**
	 * @return the emailTemplateFolderLabeTextBoxl
	 */
	public WebElement getEmailTemplateFolderLabelTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, emailTemplateFolderLabelTextBox, "Visibility", timeOut, "Email Template Folder Label");
	}
	
	
	@FindBy(xpath="//label[text()='Folder Unique Name']/../following-sibling::td//input")
	private WebElement folderUniqueNameTextBox;

	/**
	 * @return the folderUniqueNameTextBox_Classsic
	 */
	public WebElement getFolderUniqueNameTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, folderUniqueNameTextBox, "Visibility", timeOut, "Folder Unique Name");
	}
	
	
	@FindBy(xpath="//select[@id='IsReadonly']")
	private WebElement publicFolderAccesDropdown;

	/**
	 * @return the publicFolderAccesDropdown
	 */
	public WebElement getpublicFolderAccesDropdown(String environment,String mode,int timeOut) {
		return isDisplayed(driver, publicFolderAccesDropdown, "Visibility", timeOut, "Public Folder Acce sDropdown");
	}
	
	@FindBy(xpath="//input[@name='save']")
	private WebElement saveButtonforFolderTempalate;
	

	/**
	 * @return the saveButton
	 */
	public WebElement getSaveButtonforFolderTempalte(String environment,String mode,int timeOut) {
		return isDisplayed(driver, saveButtonforFolderTempalate, "Visibility", timeOut, "Save Button");
	}
	
	@FindBy(xpath="//input[@title='New Template']")
	private WebElement createNewTempalteButton;

	/**
	 * @return the createNewTempalteButton
	 */
	public WebElement getCreateNewTempalteButton(String environment,String mode,int timeOut) {
		return isDisplayed(driver, createNewTempalteButton, "Visibility", timeOut, "createNewTempalteButton");
	}
	
	@FindBy(xpath="//input[contains(@value,'Next')]")
	private WebElement nextButton;

	/**
	 * @return the nextButton
	 */
	public WebElement getNextButton(String environment,String mode,int timeOut) {
		return isDisplayed(driver, nextButton, "Visibility", timeOut, "Next Button");
	}
	
	@FindBy(xpath="//label[text()='Email Template Name']/../following-sibling::td//input")
	private WebElement emailTemplateNameTextBox;

	/**
	 * @return the emailTemplateNameTextBox
	 */
	public WebElement getEmailTemplateNameTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, emailTemplateNameTextBox, "Visibility", timeOut, "emailTemplateNameTextBox");
	}

	@FindBy(xpath="//label[text()='Available For Use']/../following-sibling::td//input")
	private WebElement availableForUseCheckBox;

	/**
	 * @return the availableForUseCheckBox
	 */
	public WebElement getAvailableForUseCheckBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, availableForUseCheckBox, "Visibility", timeOut, "Available For Use CheckBox");
	}
	
	@FindBy(xpath="//label[text()='Description']/../following-sibling::td//input")
	private WebElement descriptionTextBox;

	/**
	 * @return the descriptionTextBox
	 */
	public WebElement getDescriptionTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, descriptionTextBox, "Visibility", timeOut, "Description TextBox");
	}
	
	@FindBy(xpath="//label[text()='Subject']/../following-sibling::td//input")
	private WebElement subjectTextBox;

	/**
	 * @return the subjectTextBox
	 */
	public WebElement getSubjectTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, subjectTextBox, "Visibility", timeOut, "Subject TextBox");
	}
	
	@FindBy(xpath="//label[text()='Email Body']/../following-sibling::td//textarea")
	private WebElement emailBodyTextArea;

	/**
	 * @return the emailBodyTextArea
	 */
	public WebElement getEmailBodyTextArea(String environment,String mode,int timeOut) {
		return isDisplayed(driver, emailBodyTextArea, "Visibility", timeOut, "Email Body Text Area");
	}
	
	@FindBy(xpath="//input[@title='New Letterhead']")
	private WebElement createNewLetterheadButton;

	/**
	 * @return the createNewLetterheadButton
	 */
	public WebElement getCreateNewLetterheadButton(String environment,String mode,int timeOut) {
		return isDisplayed(driver, createNewLetterheadButton, "Visibility", timeOut, "createNewLetterheadButton");
	}
	
	@FindBy(xpath="//a[text()='Go to Letterhead']")
	private WebElement GotoLetterheadButton;
	/**
	 * @return the nextButton
	 */
	public WebElement getGotoLetterheadButton(String environment,String mode,int timeOut) {
		return isDisplayed(driver, GotoLetterheadButton, "Visibility", timeOut, "Go to Letterhead Button");
	}
	
	@FindBy(xpath="//label[text()='Letterhead Label']/../following-sibling::td//input")
	private WebElement LetterheadLabelTextBox;

	/**
	 * @return the emailTemplateNameTextBox
	 */
	public WebElement getLetterheadLabelTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, LetterheadLabelTextBox, "Visibility", timeOut, "Letterhead Label Text Box");
	}
	
	@FindBy(xpath="//label[text()='Description']/../following-sibling::td//textarea")
	private WebElement DescriptionTextArea;

	/**
	 * @return the DescriptionTextArea
	 */
	public WebElement getDescriptionTextArea(String environment,String mode,int timeOut) {
		return isDisplayed(driver, DescriptionTextArea, "Visibility", timeOut, "Description Text Area");
	}
	
	@FindBy(xpath="//label[text()='HTML Body']/../following-sibling::td//textarea")
	private WebElement HTMLBodyTextArea;

	/**
	 * @return the emailHTMLTextArea
	 */
	public WebElement getHTMLBodyTextArea(String environment,String mode,int timeOut) {
		return isDisplayed(driver, HTMLBodyTextArea, "Visibility", timeOut, "HTML Body Text Area");
	}
	
	@FindBy(xpath="//label[text()='Email Subject']/../following-sibling::td//input")
	private WebElement emailsubjectTextBox;

	/**
	 * @return the emailsubjectTextBox
	 */
	public WebElement getemailSubjectTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, emailsubjectTextBox, "Visibility", timeOut, "email Subject TextBox");
	}
	
	@FindBy(xpath = "//select[@name='recipient_type']")
	private WebElement recipienttypeDropdown;

	/**
	 * @return the viewDropdown
	 */
	public WebElement getrecipienttypeDropdown(int timeOut) {
		return isDisplayed(driver, recipienttypeDropdown, "Visibility", timeOut, "recipienttype cDropdown");
	}
	
	public WebElement EmailTemplateEditButton(String emailTemplateName, int timeOut) {
		String xpath = "//a[text()='" + emailTemplateName + "']/ancestor::th/preceding-sibling::td/a[text()='Edit']";
		WebElement ele= null;
		try {
			return ele =FindElement(driver, xpath, "EmailTemplateEditButton",  action.SCROLLANDBOOLEAN, timeOut);

		} catch (StaleElementReferenceException e) {
			return ele =FindElement(driver, xpath, "EmailTemplateEditButton", action.SCROLLANDBOOLEAN, timeOut);
		}
	}
	}
