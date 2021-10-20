package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ProjectName;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;

public class DealPage extends BasePageBusinessLayer {

	public DealPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath="//*[text()='New Question']")
	private WebElement newRequestTrackerBtn;

	/**
	 * @return the newRequestTrackerBtn
	 */
	public WebElement getNewRequestTrackerBtn(String projectName,int timeOut) {
		return isDisplayed(driver, newRequestTrackerBtn, "Visibility", timeOut, "New Request Tracker");
	}

	@FindBy(xpath="//*[text()='Status']/..//div//input")
	private WebElement statusDropDownList;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getStatus(String projectName,int timeOut) {
		return isDisplayed(driver, statusDropDownList, "Visibility", timeOut, "Status ");
	}
	@FindBy(xpath="//div[contains(@class,'photoDropdown')]/*")
	private WebElement imgIcon;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getimgIcon(String projectName,int timeOut) {
		return isDisplayed(driver, imgIcon, "Visibility", timeOut, "imgIcon");
	}
	@FindBy(xpath="//div[contains(@class,'entityPhotoSpecificity')]//span[contains(@class,'uiImage')]/img")
	private WebElement imgIconForPath;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getimgIconForPath(String projectName,int timeOut) {
		return isDisplayed(driver, imgIconForPath, "Visibility", timeOut, "imgIcon");
	}
	
	@FindBy(xpath = "//input[@name='file']")
	private WebElement uploadPhotoButton;
	
	public WebElement getuploadPhotoButton(String projectName,int timeOut) {
		return isDisplayed(driver, uploadPhotoButton, "Visibility", timeOut, "upload photo button");
		
	}
	
	@FindBy(xpath = "//textarea")
	private WebElement textArea;
	
	public WebElement getTextArea(int timeOut) {
		return isDisplayed(driver, textArea, "Visibility", timeOut, "textArea");
		
	}
	
	@FindBy(xpath = "//button[text()='Convert to Portfolio']")
	private WebElement convertToPortfolio;
	
	public WebElement getconvertToPortfolio(int timeOut) {
		return isDisplayed(driver, convertToPortfolio, "Visibility", timeOut, "convertToPortfolio");
		
	}
	@FindBy(xpath = "//button[text()='Convert']")
	private WebElement convert;
	
	public WebElement getconvertButton(int timeOut) {
		return isDisplayed(driver, convert, "Visibility", timeOut, "convertToPortfolio");
		
	}
	@FindBy(xpath = "//button[text()='Next']")
	private WebElement nextButton;
	
	public WebElement getnextButton(int timeOut) {
		return isDisplayed(driver, nextButton, "Visibility", timeOut, "nextButton");
		
	}
	@FindBy(xpath = "//button[@title='Finish']")
	private WebElement finishButton;
	
	public WebElement getfinishButton(int timeOut) {
		return isDisplayed(driver, finishButton, "Visibility", timeOut, "finish Button");
		
	}@FindBy(xpath = "//h2[text()='Convert to Portfolio']/ancestor::div//button[@title='Close this window']")
	private WebElement convertToPortfolioCrossButton;
	
	public WebElement getconvertToPortfolioCrossButton(int timeOut) {
		return isDisplayed(driver, convertToPortfolioCrossButton, "Visibility", timeOut, "cross Button");
		
	}

	public WebElement getconvertToPortfolioCrossButton(String head,int timeOut) {
		String xpath="//h2[text()='"+head+"']/ancestor::div//button[@title='Close this window']";
		WebElement ele=FindElement(driver, xpath, "cross",
				action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", timeOut, "cross Button");
		
	}
	
	@FindBy(xpath = "//span[text()='Mark Stage as Complete']/..")
	private WebElement markStageAsCompleteButton;
	
	public WebElement getmarkStageAsCompleteButton(int timeOut) {
		return isDisplayed(driver, markStageAsCompleteButton, "Visibility", timeOut, "markStageAsCompleteButton");
		
	}
	
	@FindBy(xpath = "//span[text()='Mark as Current Stage']/..")
	private WebElement markAsCurrentStage;
	
	public WebElement getmarkAsCurrentStage(int timeOut) {
		return isDisplayed(driver, markAsCurrentStage, "Visibility", timeOut, "markStageAsCompleteButton");
		
	}
	@FindBy(xpath = "//div[@title='New']")
	private WebElement NewButton;
	
	public WebElement getDealNewButton(int timeOut) {
		return isDisplayed(driver, NewButton, "Visibility", timeOut, "New deal button");
		
	}
	@FindBy(xpath = "//input[@name='Name']")
	private WebElement dealNameInput;
	
	public WebElement getNewDealPopupDealNameInput(int timeOut) {
		return isDisplayed(driver, dealNameInput, "Visibility", timeOut, " deal name input");
		
	}
	
	@FindBy(xpath="(//div[@class='requiredInput']//span[@class='lookupInput']//input)[2]")
	private WebElement company_Classic;
	
	@FindBy(xpath="//*[text()='Company Name']/following-sibling::div//input[@title='Search Institutions' or contains(@placeholder,'Search Institutions')]")
	private WebElement company_Lighting;

	/**
	 * @return the legalName
	 */
	public WebElement getDealCompanyName(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, company_Classic, "Visibility", timeOut, "company Name Classic");
		}else{
			return isDisplayed(driver, company_Lighting, "Visibility", timeOut, "company Name Lighting");
		}
	
	} 
	
	@FindBy(xpath="//*[text()='Stage']/following-sibling::div//input")
	private WebElement stageDropdown;
	
	public WebElement getStage(int timeOut){
		
		return isDisplayed(driver, stageDropdown, "Visibility", timeOut, " stage input");
	}
	
	@FindBy(xpath="//*[text()='New Source Firm']/following-sibling::div//input[@title='Search Institutions' or contains(@placeholder,'Search Institutions')]")
	private WebElement newSourcefirm;
	
	public WebElement getNewSourceFirm(int timeOut){
		
		return isDisplayed(driver, newSourcefirm, "Visibility", timeOut, " Source firm input");
	}
	
	@FindBy(xpath="//*[text()='Pipeline Comments']/following-sibling::div/textarea")
	private WebElement pipelineComments;
	
	public WebElement getPipelineComments(int timeOut){
		
		return isDisplayed(driver, pipelineComments, "Visibility", timeOut, "pipeline comments input");
	}
	
	@FindBy(xpath="//button[@name='SaveEdit']")
	private WebElement saveButton;
	
	public WebElement getSaveButton(int timeOut){
		
		return isDisplayed(driver, saveButton, "Visibility", timeOut, "save button ");
	}
	
	
}
