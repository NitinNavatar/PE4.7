package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.object;

import static com.navatar.generic.CommonLib.*;

public class SetupPage extends BasePageBusinessLayer {

	public SetupPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}
	
	public WebElement getQucikSearchInSetupPage(int timeOut ) {
		WebElement ele = null;
		String xpath="";
			xpath="//input[contains(@placeholder,'Quick Find')]";
		ele=FindElement(driver, xpath, "search text box in ", action.SCROLLANDBOOLEAN,30);
		return isDisplayed(driver,ele,"visibility",30,"quick search text box in ");
	}
	
	@FindBy(xpath="//ul[contains(@class,'tabBarItems slds-grid')]//span[contains(@class,'title slds-truncate')][contains(text(),'Object Manager')]")
	private WebElement objectManager_Lighting;

	/**
	 * @return the objectManage_Lighting
	 */
	public WebElement getObjectManager_Lighting(int timeOut) {
		return isDisplayed(driver, objectManager_Lighting, "Visibility", timeOut, "object manage");
	}
	
	@FindBy(xpath="//input[@id='globalQuickfind']")
	private WebElement quickSearchInObjectManager_Lighting;

	/**
	 * @return the quickSearchInObjectManager_Lighting
	 */
	public WebElement getQuickSearchInObjectManager_Lighting(int timeOut) {
		return isDisplayed(driver, quickSearchInObjectManager_Lighting, "Visibility", timeOut, "quick search in object manager in lighting");
	}
	
	
	@FindBy(xpath="//iframe[contains(@title,'Salesforce - Enterprise Edition')]")
	private WebElement editPageLayoutFrame_Lighting;

	/**
	 * @return the editPageLayoutFrame_Lighting
	 */
	public WebElement getEditPageLayoutFrame_Lighting(int timeOut) {
		return isDisplayed(driver, editPageLayoutFrame_Lighting, "Visibility", timeOut, "edit page layout frame in lighting");
	}
	
	@FindBy(xpath="//em[@class='x-btn-split']//button[@type='button'][contains(text(),'Save')]")
	private WebElement pageLayoutSaveBtn;

	/**
	 * @return the pageLayoutSaveBtn
	 */
	public WebElement getPageLayoutSaveBtn(object obj,int timeOut) {
		String xpath="";
		WebElement ele=null;
		if (obj==object.Global_Actions) {
			xpath="//table[@id='saveBtn']";
			ele=FindElement(driver, xpath, "save", action.BOOLEAN, 10);
			return isDisplayed(driver, ele, "Visibility", timeOut, "pagelayout save button");
		}
		else
		return isDisplayed(driver, pageLayoutSaveBtn, "Visibility", timeOut, "pagelayout save button");
	}
	
	@FindBy(xpath="//iframe[contains(@title,'Salesforce - Enterprise Edition')]")
	private WebElement setupPageIframe;

	/**
	 * @return the userIframe
	 */
	public WebElement getSetUpPageIframe(int timeOut) {
		return isDisplayed(driver, setupPageIframe, "Visibility", timeOut, "active users iframe");
	}
	
	@FindBy(xpath="//td[@id='topButtonRow']//input[@name='save']")
	private WebElement createUserSaveBtn_Lighting;

	/**
	 * @return the createUserSaveBtn
	 */
	public WebElement getCreateUserSaveBtn_Lighting(int timeOut) {
		return isDisplayed(driver, createUserSaveBtn_Lighting, "Visibility", timeOut, "create user save button in lighting");
	}
	
	@FindBy(id = "ImportedPackage_font")
	private WebElement installedPackageLink_Classic;

	/**
	 * @return the installedpackageLink
	 */
	@FindBy(xpath="//a[contains(@href,'/setup/ImportedPackage/home')]")
	private WebElement installedPackageLink_Lighting;
	
	public WebElement getInstalledPackageLink(int timeOut) {
			return isDisplayed(driver, installedPackageLink_Lighting, "Visibility", timeOut, "Installed package in ");
	}
	
	@FindBy(xpath="//iframe[@id='available']")
	private WebElement installedPackageFrame;
	
	@FindBy(xpath="//iframe[contains(@title,'Add Users:')]")
	private WebElement installedPackageParentFrame_Lighting;
	
	/**
	 * @return the installedPackageFrame_Lighting
	 */
	public WebElement getInstalledPackageParentFrame_Lighting(int timeOut) {
		return isDisplayed(driver, installedPackageParentFrame_Lighting, "Visibility", timeOut, " Installed Package Parent Frame in Lightning");
	}

	/**
	 * @return the installedPackageFrame
	 */
	public WebElement getInstalledPackageFrame(int timeOut) {
		return isDisplayed(driver, installedPackageFrame, "Visibility", timeOut, "Add Users frame in installed package.");
	}
	
	@FindBy(xpath = "//label[text()='Quick Find']/following-sibling::input")
	private WebElement quickFindSearch;
	
	public WebElement getquickFindSearch(int timeOut) {
		return isDisplayed(driver, quickFindSearch, "Visibility", timeOut, "quickFindSearch");
	}
	
	@FindBy(xpath = "//button[@title='Custom Field']")
	private WebElement customFieldNewButton;

	public WebElement getCustomFieldNewButton(int timeOut) {
		return isDisplayed(driver, customFieldNewButton, "Visibility", timeOut, "custom field new button");
	}
	
	public WebElement getNewCustomFieldFrame(object objectName, int timeOut) {
		String xpath="//iframe[contains(@title,'"+objectName.toString()+"')]";
		WebElement ele= FindElement(driver, xpath,objectName+" new object frame xpath", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut,objectName+" new object frame xpath");
	}
	
	public WebElement getNewCustomFieldDataTypeOrFormulaReturnType(String dataType, int timeOut) {
		String xpath="//label[text()='"+dataType+"']/preceding-sibling::input";
		WebElement ele= FindElement(driver, xpath,dataType+" data type xpath", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut,dataType+" data type xpath");
	}
	
	
	@FindBy(xpath = "//div[@class='pbTopButtons']//input[@name='goNext']")
	private WebElement customFieldNextBtn;

	public WebElement getCustomFieldNextBtn(int timeOut) {
		return isDisplayed(driver, customFieldNextBtn, "Visibility", timeOut, "custom field next button");
	}
	
	@FindBy(xpath = "//input[@id='MasterLabel']")
	private WebElement fieldLabelTextBox;

	public WebElement getFieldLabelTextBox(int timeOut) {
		return isDisplayed(driver, fieldLabelTextBox, "Visibility", timeOut, "field label text box");
	}
	
	@FindBy(xpath = "//div[@class='pbTopButtons']//input[@name='save']")
	private WebElement customFieldSaveBtn;

	public WebElement getCustomFieldSaveBtn(int timeOut) {
		return isDisplayed(driver, customFieldSaveBtn, "Visibility", timeOut, "custom field save button");
	}
	
}