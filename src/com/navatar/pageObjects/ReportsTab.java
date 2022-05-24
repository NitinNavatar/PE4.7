package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.EnumConstants.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.navatar.generic.SmokeCommonVariables;

public class ReportsTab extends BasePageBusinessLayer {

	public ReportsTab(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="(//div[@id='folderTreePanel']//button[contains(@id,'ext-gen')])[1]")
	private WebElement reportFolderIcon_Classic;

	/**
	 * @return the reportFolderIcon_Classic
	 */
	public WebElement getReportFolderIcon_Classic(String environment,int timeOut) {
		return isDisplayed(driver, reportFolderIcon_Classic, "Visibility", timeOut, "Report Folder Icon Classic");
	}
	
	@FindBy(xpath="//label[contains(text(),'Folder Label')]/../following-sibling::td//input")
	private WebElement folderNameTextBox_Classic;

	/**
	 * @return the folderNameTextBox_Classic
	 */
	public WebElement getFolderNameTextBox_Classic(String environment,int timeOut) {
		return isDisplayed(driver, folderNameTextBox_Classic, "Visibility", timeOut, " Folder Name Classic");
	}
	
	@FindBy(xpath="//input[@title='New Report...']")
	private WebElement newReportBtn_Classic;

	/**
	 * @return the newReportBtn_Classic
	 */
	public WebElement getNewReportBtn_Classic(String environment,int timeOut) {
		return isDisplayed(driver, newReportBtn_Classic, "Visibility", timeOut, "New Report Btn Classic");
	}
	
	@FindBy(xpath="//input[@id='quickFindInput']")
	private WebElement searchBox_Classic;

	/**
	 * @return the searchBox_Classic
	 */
	public WebElement getSearchBox_Classic(String environment,int timeOut) {
		return isDisplayed(driver, searchBox_Classic, "Visibility", timeOut, "Search box Classic");
	}
	
	@FindBy(xpath="//input[@id='thePage:rtForm:createButton']")
	private WebElement createBtn_Classic;

	/**
	 * @return the createBtn_Classic
	 */
	public WebElement getCreateBtn_Classic(String environment,int timeOut) {
		return isDisplayed(driver, createBtn_Classic, "Visibility", timeOut, "Create Btn Classic");
	}
	
	@FindBy(xpath="//label[text()='Show']/../../following-sibling::td//img")
	private WebElement showIcon_Classic;

	/**
	 * @return the showIcon_Classic
	 */
	public WebElement getShowIcon_Classic(String environment,int timeOut) {
		return isDisplayed(driver, showIcon_Classic, "Visibility", timeOut, "Show Icon Classic");
	}
	
	@FindBy(xpath="(//label[text()='Range']/following-sibling::div/img)[1]")
	private WebElement rangeIcon_Classic;

	/**
	 * @return the rangeIcon_Classic
	 */
	public WebElement getRangeIcon_Classic(String environment,int timeOut) {
		return isDisplayed(driver, rangeIcon_Classic, "Visibility", timeOut, "Range Icon Classic");
	}
	
	@FindBy(xpath="//button[text()='Save']")
	private WebElement saveBtn_Classic;

	/**
	 * @return the saveBtn_Classic
	 */
	public WebElement getSaveBtn_Classic(String environment,int timeOut) {
		return isDisplayed(driver, saveBtn_Classic, "Visibility", timeOut, "Save Btn Classic");
	}
	
	@FindBy(xpath="//label[text()='Report Name']/following-sibling::div//input")
	private WebElement reportNameTextBox_Classic;

	/**
	 * @return the reportNameTextBox_Classic
	 */
	public WebElement getReportNameTextBox_Classic(String environment,int timeOut) {
		return isDisplayed(driver, reportNameTextBox_Classic, "Visibility", timeOut, "report Name TextBox Classic");
	}
	
	@FindBy(xpath="//label[text()='Report Description']/following-sibling::div//textarea")
	private WebElement reportDescriptionTextBox_Classic;

	/**
	 * @return the reportDescriptionTextBox_Classic
	 */
	public WebElement getReportDescriptionTextBox_Classic(String environment,int timeOut) {
		return isDisplayed(driver, reportDescriptionTextBox_Classic, "Visibility", timeOut, "report Description TextBox Classic");
	}
	
	@FindBy(xpath="//label[text()='Report Folder']/following-sibling::div//img")
	private WebElement reportFolderIconOnSaveReport_Classic;

	/**
	 * @return the reportFolderIconOnSaveReport_Classic
	 */
	public WebElement getReportFolderIconOnSaveReport_Classic(String environment,int timeOut) {
		return isDisplayed(driver, reportFolderIconOnSaveReport_Classic, "Visibility", timeOut, "report Folder Icon On Save Report Classic");
	}
	
	@FindBy(xpath="//div[@id='saveReportDlg']//button[text()='Save']")
	private WebElement saveBtnOnSaveReport_Classic;

	/**
	 * @return the saveBtnOnSaveReport_Classic
	 */
	public WebElement getSaveBtnOnSaveReport_Classic(String environment,int timeOut) {
		return isDisplayed(driver, saveBtnOnSaveReport_Classic, "Visibility", timeOut, "Save Btn on Save Report Classic");
	}
	
	@FindBy(xpath = "//table[@id='reportFormatMink']//tr/td/em")
	private WebElement reportFormatDropDown;
	
	public WebElement getReportFormatName(int timeOut) {
		return isDisplayed(driver, reportFormatDropDown, "Visibility", timeOut, "report format drop down");
	}
	
	
	public WebElement getreportFormatName(ReportFormatName reportFormatName) {
		String xpath="//span[text()='"+reportFormatName.toString()+"']/../..";
		return isDisplayed(driver, FindElement(driver, xpath, "report Format Name "+reportFormatName.toString(), action.SCROLLANDBOOLEAN,10),"visibility",10,"report format name "+reportFormatName.toString());
	}
	
	
	//div[@class='x-tree-root-node']//div[text()='aa']
	
	public WebElement getCreateReportFolderNameInSideTree(String folderName, int timeOut) {
		String xpath="//div[@class='x-tree-root-node']//div[text()='"+folderName+"']";
		return isDisplayed(driver, FindElement(driver, xpath, "report folder Name "+folderName, action.SCROLLANDBOOLEAN,10),"visibility",10,"report folder name "+folderName);
		
	}
	
	public WebElement getCreateReportFolderNamePinIconInSideTree(String folderName, int timeOut) {
		String xpath="//div[@class='x-tree-root-node']//div[text()='"+folderName+"']/following-sibling::table";
		return isDisplayed(driver, FindElement(driver, xpath, "report folder Name "+folderName, action.SCROLLANDBOOLEAN,10),"visibility",10,"report folder name "+folderName);
		
	}
	
	@FindBy(xpath = "//span[text()='Share']")
	private WebElement ReportFolderShareText;

	public WebElement getReportFolderShareText(int timeOut) {
		return isDisplayed(driver, ReportFolderShareText, "Visibility", timeOut, "Report Folder Share Text");
	}
	
	@FindBy(xpath = "//a[text()='Users, ']")
	private WebElement userLinkInSharePopUp;

	public WebElement getUserLinkInSharePopUp(int timeOut) {
		return isDisplayed(driver, userLinkInSharePopUp, "Visibility", timeOut, "user Link In Share PopUp");
	}
	
	public WebElement getUserShareButton() {
		String xpath="//div[text()='"+SmokeCommonVariables.crmUser1FirstName+" "+SmokeCommonVariables.crmUser1LastName+"']/../following-sibling::td/button[text()='Share']";
		return isDisplayed(driver, FindElement(driver, xpath,"CRM User Share Icon", action.SCROLLANDBOOLEAN,10),"visibility",10,"CRM User Share Icon");
	}
	
	public WebElement getReportFolderAccessDownArrow() {
		String xpath="(//div[text()='"+SmokeCommonVariables.crmUser1FirstName+" "+SmokeCommonVariables.crmUser1LastName+"']/../following-sibling::td/a)[1]";
		return isDisplayed(driver, FindElement(driver, xpath,"Report Folder Access Down Arrow", action.SCROLLANDBOOLEAN,10),"visibility",10,"Report Folder Access Down Arrow");
	}
	
	@FindBy(xpath = "//span[text()='Manager']")
	private WebElement ReportFolderNameManagerText;

	public WebElement getReportFolderNameManagerText(int timeOut) {
		return isDisplayed(driver, ReportFolderNameManagerText, "Visibility", timeOut, "Report Folder Name Manager Text");
	}
	
	@FindBy(xpath = "//tr[@class='x-toolbar-left-row']//button")
	private WebElement  ReportFolderSharePopUpDoneAndCloseButton;

	public WebElement getReportFolderSharePopUpDoneAndCloseButton(int timeOut) {
		return isDisplayed(driver, ReportFolderSharePopUpDoneAndCloseButton, "Visibility", timeOut, "Report Folder Share Pop Up Done And Close Button");
	}
	
}
