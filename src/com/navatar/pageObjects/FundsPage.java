package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;

public class FundsPage extends BasePageBusinessLayer {

	public FundsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	@FindBy(xpath = "//span[text()='Fund']/ancestor::article//span[text()='View All']")
	private WebElement fundsPageViewAll_Lightning;
	/**
	 * @return the fundsPageViewAll_Lightning
	 */
	public WebElement getFundsPageViewAll_Lightning(int timeOut) {
		return isDisplayed(driver, fundsPageViewAll_Lightning, "Visibility", timeOut, "View all button on lightning");
		}
	@FindBy(xpath = "//a[@title='Create Drawdown']")
	private WebElement createFundDrawdownButton_Ligh;
	

	
	@FindBy(xpath = "//td[@id='topButtonRow']//input[@title='Create Drawdown']")
	private WebElement createdFundDrawdownButton_classic;
	
	/**
	 * @return the createdFundDrawdownButton_classic
	 */
	public WebElement getCreatedFundDrawdownButton(String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString()))
		return isDisplayed(driver, createdFundDrawdownButton_classic, "Visibility", timeOut, "Fund Drawdown Classic");
	
		else
		return isDisplayed(driver, createFundDrawdownButton_Ligh, "Visibility", timeOut, "Fund Drawdown Lightning");
	
	}
	@FindBy(xpath = "//td[@id='topButtonRow']//input[@title='Create Distribution']")
	private WebElement createFundDistributionButton_classic;
	

	@FindBy(xpath = "//a[@title='Create Distribution']")
	private WebElement createFundDistributionButton_Ligh;
	
	public WebElement getCreatedFundDistributionButton(String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString()))
		return isDisplayed(driver, createFundDistributionButton_classic, "Visibility", timeOut, "Fund Distribution Classic");
	
		else
		return isDisplayed(driver, createFundDistributionButton_Ligh, "Visibility", timeOut, "Fund Distribution Lightning");
	
	}


	
	@FindBy(xpath="//span[text()='Fund Name']/../following-sibling::input")
	private WebElement fundName_Lighting;

	/**
	 * @return the fundName
	 */
	public WebElement getFundName(String projectName,int timeOut) {
		
			return isDisplayed(driver, fundName_Lighting, "Visibility", timeOut, "Fund Name Lighting");
	
		
	}
	
	@FindBy(xpath="//div[@class='requiredInput']//select")
	private WebElement fundType_Classic;
	
	@FindBy(xpath="//*[text()='Fund Type']/following-sibling::div[@class='slds-form-element__control']//input[@type='text']")
	private WebElement fundType_Lighting;

	/**
	 * @return the fundType
	 */
	public WebElement getFundType(String projectName,int timeOut) {
		
			return isDisplayed(driver, fundType_Lighting, "Visibility", timeOut, "Fund Type Lighting");
		
		
	}
	

	
	@FindBy(xpath="//*[text()='Investment Category']/following-sibling::div//input[@type='text']")
	private WebElement investmentCategory_Lighting;

	/**
	 * @return the investmentCategory
	 */
	public WebElement getInvestmentCategory(String projectName,int timeOut) {
		
			return isDisplayed(driver, investmentCategory_Lighting, "Visibility", timeOut, "Investment Category Lighting");
	
	} 
	
	
	
	@FindBy(xpath="//div//h1/div[contains(text(),'Fund')]/..")
	private WebElement fundNameInViewMode_Lighting;

	/**
	 * @return the fundNameLabel
	 */
	public WebElement getFundNameInViewMode(String projectName,int timeOut) {
		
			return isDisplayed(driver, fundNameInViewMode_Lighting, "Visibility", timeOut, "Fund Name in View Mode Lighting");
		
		
	}

	public WebElement getFundNameAtFundPage(String fundName,int timeOut){
		WebElement ele = FindElement(driver,
				"//div[@class='x-panel-bwrap']//a//span[contains(text(),'" + fundName + "')]", "Fund Name",
				action.SCROLLANDBOOLEAN, 60);
		
		return isDisplayed(driver, ele, "Visibility", timeOut, "Select all check box");
	}
	
	public WebElement getFundtPageTextBoxOrRichTextBoxWebElement(String projectName, String labelName, int timeOut) {
		WebElement ele=null;
		String xpath ="",inputXpath="", dateXpath="",finalXpath="",finalLabelName="";
		if(labelName.contains("_")) {
			finalLabelName=labelName.replace("_", " ");
		}else {
			finalLabelName=labelName;
		}
		
			//span[text()='Description']/..//following-sibling::textarea
			xpath="//span[contains(text(),'"+finalLabelName+"')]";
			inputXpath="/..//following-sibling::input";
			dateXpath="/../following-sibling::div/input";
		
		if(labelName.contains("Date")) {
			finalXpath=xpath+dateXpath;
		}else {
			finalXpath=xpath+inputXpath;
		}
		ele=isDisplayed(driver, FindElement(driver, finalXpath, finalLabelName+" text box in "+projectName, action.SCROLLANDBOOLEAN,30), "Visibility", timeOut, finalLabelName+"text box in "+projectName);
		return ele;
	}
	
	
	@FindBy(xpath="//iframe[@title='Email Fundraisings']")
	private WebElement emailFundraisingContactFrame_Lightning;
	
	/**
	 * @return the emailFundraisingContactFrame_Lightning
	 */
	public WebElement getEmailFundraisingContactFrame_Lightning(int timeOut) {
		return isDisplayed(driver, emailFundraisingContactFrame_Lightning, "Visibility", timeOut, "email Fundraising Contact Frame Lightning");
	}

	@FindBy(xpath="//input[@title='Email Fundraising Contacts']")
	private WebElement emailFundraisingContactsBtn_Classic;

	@FindBy(xpath="//a[@title='Email Fundraising Contacts']")
	private WebElement emailFundraisingContactsBtn_Lightning;
	/**
	 * @return the emailFundraisingContactsBtn
	 */
	public WebElement getEmailFundraisingContactsBtn(String environment, String mode,int timeOut) {
		if(mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
			return isDisplayed(driver, emailFundraisingContactsBtn_Lightning, "Visibility", timeOut, "email fundraising contact button in "+mode);
		}else {
			return isDisplayed(driver, emailFundraisingContactsBtn_Classic, "Visibility", timeOut, "email fundraising contact button in "+mode);
		}
	}
	
	@FindBy(xpath = "//span[text()='Tasks']/..")
	private WebElement tasksTab;
	
	public WebElement gettasksTab(int timeOut) {
		return isDisplayed(driver, tasksTab, "Visibility", timeOut, "tasksTab in funds page");
	}
	
	

	/**
	 * @return the getDealType
	 */
	public WebElement getDealType(String projectName,String dealType,int timeOut) {

			String xpath = "//span[text()='"+dealType+"']/../..";
			WebElement ele = FindElement(driver, xpath, "Deal Type", action.SCROLLANDBOOLEAN, timeOut);
			return isDisplayed(driver, ele, "Visibility", timeOut, "Deal Type : "+dealType);
		

	}
	
	
	@FindBy(xpath="//span[text()='Status']/../..//div[@class='uiMenu']//a[@class='select']")
	private WebElement dealStatusDropDownList;

	/**
	 * @return the fundType
	 */
	public WebElement getDealStatus(String projectName,int timeOut) {
	
			return isDisplayed(driver, dealStatusDropDownList, "Visibility", timeOut, "Deal Status ");
		

	}
	

	@FindBy(xpath="//span[text()='Stage']/../..//div[@class='uiMenu']//a[@class='select']")
	private WebElement dealStageDropDownList;

	/**
	 * @return the fundType
	 */
	public WebElement getDealStage(String projectName,int timeOut) {
		
			return isDisplayed(driver, dealStageDropDownList, "Visibility", timeOut, "Deal Stage");
		

	}
	
}
