package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.EnumConstants.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import com.navatar.generic.CommonLib;
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
	
	@FindBy(xpath = "//button[text()='Add']")
	private WebElement  addFilterBtn;

	public WebElement addFilterBtn(int timeOut) {
		return isDisplayed(driver, addFilterBtn, "Visibility", timeOut, "Add Filter Button");
	}
	
	@FindBy(xpath = "//input[starts-with(@id,'scope-ext-gen')]")
	private WebElement  showDrpDownLink;

	public WebElement showDrpDownLink(int timeOut) {
		return isDisplayed(driver, showDrpDownLink, "Visibility", timeOut, "Show Drop Down Link");
	}
	
	@FindBy(xpath = "//iframe[@title='sessionserver']/following-sibling::div[@class='x-layer x-combo-list ']/div/div")
	private List<WebElement>  showDrpDownList;
	public List<WebElement> showDrpDownList(){
		return FindElements(driver, showDrpDownList, "Show DropDown List");
	}
	
	@FindBy(xpath = "//input[@name='dateColumn']")
	private WebElement  dateFieldDrpDownLink;

	public WebElement dateFieldDrpDownLink(int timeOut) {
		return isDisplayed(driver, dateFieldDrpDownLink, "Visibility", timeOut, "Date Field Drop Down Link");
	}
	
	@FindBy(xpath = "//div[@class='dateColumnCategory']/parent::div/div")
	private List<WebElement>  dateFieldDrpDownList;
	public List<WebElement> dateFieldDrpDownList(){
		return FindElements(driver, dateFieldDrpDownList, "Date Field DropDown List");
	}
	
	@FindBy(xpath = "//input[@name='pc']/following-sibling::img")
	private WebElement  fieldDrpDownLink;
////img[@id='ext-gen304']
	public WebElement fieldDrpDownLink(int timeOut) {
		return isDisplayed(driver, fieldDrpDownLink, "Visibility", timeOut, "Field Drop Down Link");
	}
	
	@FindBy(xpath = "//div[@class='acFolder']/parent::div/div")
	private List<WebElement>  fieldDrpDownList;
	public List<WebElement> fieldDrpDownList(){
		return FindElements(driver, fieldDrpDownList, "Field DropDown List");
	}
	
	@FindBy(xpath = "//input[@name='pc']/parent::div/following-sibling::div//img")
	private WebElement  operatorDrpDownLink;

	public WebElement operatorDrpDownLink(int timeOut) {
		return isDisplayed(driver, operatorDrpDownLink, "Visibility", timeOut, "Operator Drop Down Link");
	}
	
	@FindBy(xpath = "//div[text()='equals']/parent::div/div")
	private List<WebElement>  operatorDrpDownList;
	public List<WebElement> operatorDrpDownList(){
		return FindElements(driver, operatorDrpDownList, "Operator Drop Down List");
	}
	
	@FindBy(xpath = "(//input[@name='pc']/parent::div/following-sibling::div)[2]/input")
	private WebElement  fieldFilterValueInputBox;

	public WebElement fieldFilterValueInputBox(int timeOut) {
		return isDisplayed(driver, fieldFilterValueInputBox, "Visibility", timeOut, "Field Filter Value Input Box");
	}
	
	@FindBy(xpath = "(//label[text()='Range']/preceding-sibling::div/following-sibling::div/input)[1]")
	private WebElement  rangeDrpDownLink;

	public WebElement rangeDrpDownLink(int timeOut) {
		return isDisplayed(driver, rangeDrpDownLink, "Visibility", timeOut, "Range Drop Down Link");
	}
	
	
	@FindBy(xpath = "//iframe[@title='sessionserver']/following::div[text()='All Time']/../div")
	private List<WebElement>  rangeDrpDownList;
	public List<WebElement> rangeDrpDownList(){
		return FindElements(driver, rangeDrpDownList, "Range Drop Down List");
	}
	
	@FindBy(xpath = "//button[text()='OK']")
	private WebElement  filterOKButton;

	public WebElement filterOKButton(int timeOut) {
		return isDisplayed(driver, filterOKButton, "Visibility", timeOut, "Filter Ok Button");
	}
	
	@FindBy(xpath = "//img[@title='Home']")
	private WebElement  homeLink;

	public WebElement homeLink(int timeOut) {
		return isDisplayed(driver, homeLink, "Visibility", timeOut, "Home Link");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@FindBy(xpath="//a[@title='New Report']")
	private WebElement newReportBtn_Lightning;

	public WebElement newReportBtn_Lightning(int timeOut) {
		return isDisplayed(driver, newReportBtn_Lightning, "Visibility", timeOut, "New Report Btn Lightning");
	}
	
	
	@FindBy(xpath="//input[@id='modal-search-input']")
	private WebElement ReportTypeSearchBox_Lightning;

	public WebElement ReportTypeSearchBox_Lightning(int timeOut) {
		return isDisplayed(driver, ReportTypeSearchBox_Lightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	@FindBy(xpath="//button[text()='Continue']")
	private WebElement continueButton_Lightning;

	public WebElement continueButton_Lightning(int timeOut) {
		return isDisplayed(driver, continueButton_Lightning, "Visibility", timeOut, "Report Type Search Box");
	}
	//div[text()='Columns']/parent::div/following-sibling::div//li/span/span/span
	
	@FindBy(xpath = "//div[text()='Columns']/parent::div/following-sibling::div//li/span/span/span")
	private List<WebElement>  listOfFieldsAlreadyAdded;
	public List<WebElement> listOfFieldsAlreadyAdded(){
		return FindElements(driver, listOfFieldsAlreadyAdded, "List Of Fields Already Added");
	}
	
	@FindBy(xpath="//div[text()='Columns']/parent::div/following-sibling::div//ul")
	private WebElement FieldsAlreadyAdded;

	public WebElement FieldsAlreadyAdded(int timeOut) {
		return isDisplayed(driver, FieldsAlreadyAdded, "Visibility", timeOut, "Report Type Search Box");
	}
	
	
	@FindBy(xpath="//iframe[@title='Report Builder']")
	private WebElement iFrameReportTypeLightning;

	public WebElement iFrameReportTypeLightning(int timeOut) {
		return isDisplayed(driver, iFrameReportTypeLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	@FindBy(xpath="//input[@placeholder='Add column...']")
	private WebElement addColumnSearchBoxLightning;

	public WebElement addColumnSearchBoxLightning(int timeOut) {
		return isDisplayed(driver, addColumnSearchBoxLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	@FindBy(xpath="//input[@id='reportName']")
	private WebElement reportNameInputBoxLightning;

	public WebElement reportNameInputBoxLightning(int timeOut) {
		return isDisplayed(driver, reportNameInputBoxLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	@FindBy(xpath="//input[@id='reportUniqueName']")
	private WebElement reportUniqueNameInputBoxLightning;

	public WebElement reportUniqueNameInputBoxLightning(int timeOut) {
		return isDisplayed(driver, reportUniqueNameInputBoxLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	@FindBy(xpath="//label[text()='Report Description']/parent::div//textarea")
	private WebElement reportDescriptionBoxLightning;

	public WebElement reportDescriptionBoxLightning(int timeOut) {
		return isDisplayed(driver, reportDescriptionBoxLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	@FindBy(xpath="//button[text()='Select Folder']")
	private WebElement selectReportFolderButtonLightning;

	public WebElement selectReportFolderButtonLightning(int timeOut) {
		return isDisplayed(driver, selectReportFolderButtonLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	
	@FindBy(xpath="//button[@title='Close']/../..//footer/button[text()='Save']")
	private WebElement saveButtonInSaveReportPopUpLightning;

	public WebElement saveButtonInSaveReportPopUpLightning(int timeOut) {
		return isDisplayed(driver, saveButtonInSaveReportPopUpLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	
	
	//Filters Locators
	@FindBy(xpath="//h2[text()='Filters']/ancestor::a")
	private WebElement filterButtonLightning;

	public WebElement filterButtonLightning(int timeOut) {
		return isDisplayed(driver, filterButtonLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	@FindBy(xpath="//span[text()='Show Me']/parent::button")
	private WebElement showMeFilterButtonLightning;

	public WebElement showMeFilterButtonLightning(int timeOut) {
		return isDisplayed(driver, showMeFilterButtonLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	@FindBy(xpath="//label[text()='Show Me']/parent::div//button")
	private WebElement showMeFilterDropDownButtonLightning;

	public WebElement showMeFilterDropDownButtonLightning(int timeOut) {
		return isDisplayed(driver, showMeFilterDropDownButtonLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	@FindBy(xpath="//button[text()='Apply']")
	private WebElement applyButtonLightning;

	public WebElement applyButtonLightning(int timeOut) {
		return isDisplayed(driver, applyButtonLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	@FindBy(xpath="//span[text()='Show Me']/parent::button/ancestor::li/following-sibling::li//button")
	private WebElement dateRangeFilterButtonLightning;

	public WebElement dateRangeFilterButtonLightning(int timeOut) {
		return isDisplayed(driver, dateRangeFilterButtonLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	
	@FindBy(xpath="//label[text()='Date']/parent::div//input")
	private WebElement dateFilterDropDownButtonLightning;

	public WebElement dateFilterDropDownButtonLightning(int timeOut) {
		return isDisplayed(driver, dateFilterDropDownButtonLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	@FindBy(xpath="//label[text()='Range']/parent::div//button")
	private WebElement rangeFilterDropDownButtonLightning;

	public WebElement rangeFilterDropDownButtonLightning(int timeOut) {
		return isDisplayed(driver, rangeFilterDropDownButtonLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	@FindBy(xpath="//input[@placeholder='Add filter...']")
	private WebElement adFilterSearchBoxLightning;

	public WebElement adFilterSearchBoxLightning(int timeOut) {
		return isDisplayed(driver, adFilterSearchBoxLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	
	@FindBy(xpath="//label[text()='Operator']/parent::div//button")
	private WebElement operatorDropDownLinkLightning;

	public WebElement operatorDropDownLinkLightning(int timeOut) {
		return isDisplayed(driver, operatorDropDownLinkLightning, "Visibility", timeOut, "Report Type Search Box");
	}
	
	
	@FindBy(xpath="//input[@id='undefined-input']")
	private WebElement customFilterFieldValueInputBoxLightning;

	public WebElement customFilterFieldValueInputBoxLightning(int timeOut) {
		return isDisplayed(driver, customFilterFieldValueInputBoxLightning, "Visibility", timeOut, "Report Type Search Box");
	}

	
	public WebElement getreportName(String reportName) {
		String xpath="//span[text()='"+reportName+"']";
		return isDisplayed(driver, FindElement(driver, xpath, "report Name: "+reportName, action.SCROLLANDBOOLEAN,20),"visibility",20,"report name "+reportName);
	}
	
	@FindBy(xpath="//div[text()='Total Records']/following-sibling::div")
	private WebElement reportRecordsCount;

	public WebElement reportRecordsCount(int timeOut) {
		return isDisplayed(driver, reportRecordsCount, "Visibility", timeOut, "Report Records Count");
	}
	
	@FindBy(xpath="//tbody/tr[1]/th[contains(@id,'full-data')]/div/div/span[contains(@class,'wave-table')]/preceding-sibling::span")
	private List <WebElement> reportColumnHeaders;

	public List <WebElement> reportColumnHeaders() {
		
		return FindElements(driver, reportColumnHeaders, "Report Headers");
	}
	
	@FindBy(xpath="//tbody/tr[not(contains(@class,'header'))]")
	private List <WebElement> reportRecordsRowsCount;

	public List <WebElement> reportRecordsRowsCount() {
		return FindElements(driver, reportRecordsRowsCount, "Report Records Rows Count");

	}
	

	
	@FindBy(xpath="//iframe[@title='Report Viewer']")
	private WebElement reportViewerIFrame;

	public WebElement reportViewerIFrame(int timeOut) {
		return isDisplayed(driver, reportViewerIFrame, "Visibility", timeOut, "Report Viewer IFrame");
	}
	
	
	}
