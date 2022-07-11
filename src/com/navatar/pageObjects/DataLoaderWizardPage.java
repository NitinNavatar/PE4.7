/**
 * 
 */
package com.navatar.pageObjects;


import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.navatar.generic.CommonLib.*;

/**
 * @author ANKIT JAISWAL
 *
 */
public class DataLoaderWizardPage extends BasePageBusinessLayer {

	/**
	 * @param driver
	 */
	public DataLoaderWizardPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}

	
	@FindBy(xpath = "//div[@id='userNav-menuItems']/a[@title='Setup']")
	private WebElement setUpLink;

	public WebElement getSetUpLink(int timeOut) {
		return isDisplayed(driver, setUpLink, "Visibility", timeOut, "setup link ");
	}
	
	public WebElement getQucikSearchInSetupPage(String environment, String mode,int timeOut ) {
		WebElement ele = null;
		String xpath="";
		if(mode.equalsIgnoreCase(Mode.Classic.toString())) {
			xpath = "//input[@id='setupSearch']";
		}else {
			xpath="//input[@placeholder='Quick Find']";
		}
		ele=FindElement(driver, xpath, "search text box in "+mode, action.SCROLLANDBOOLEAN,30);
		return isDisplayed(driver,ele,"visibility",30,"quick search text box in "+mode);
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
	
	
	/*
	 * @FindBy(xpath =
	 * "//div[@data-aura-class='dataImporterDiLanding']//a[text()='Launch Wizard!']"
	 * )
	 */
	@FindBy(xpath="//a[contains(text(),'Launch Wizard')]")
	private WebElement lunchWizardButton;

	public WebElement getLunchWizardButton(int timeOut) {
		
		
		  return isDisplayed(driver, lunchWizardButton, "Visibility", timeOut,
		  "lunch wizard button");
		 
	}
	
	
	@FindBy(xpath = "//div[@data-aura-class='dataImporterDiSfdcObjectSelectionActivity']//a[text()='Custom objects']")
	private WebElement customObjectHeaderTab;

	public WebElement getCustomObjectHeaderTab(int timeOut) {
		return isDisplayed(driver, customObjectHeaderTab, "Visibility", timeOut, "custom object header tab");
	}
	
	public WebElement getStandardOrCustomObjectNameLink(String objectName, int timeOut) {
		String xpath="//a[text()='"+objectName+"']";
		return FindElement(driver, xpath, "object name ", action.SCROLLANDBOOLEAN, timeOut);
	}
	

	public WebElement getDataImportChoiceActivity(String dataImportType, int timeOut) {
		String xpath="//a[text()='"+dataImportType+"']";
		return FindElement(driver, xpath, "data import type "+dataImportType+" link ", action.SCROLLANDBOOLEAN, timeOut);
	}
	
	
	@FindBy(xpath = "//div[@data-aura-class='dataImporterDiCsvSelectionActivity']//span[@data-aura-class='uiOutputText' and text()='CSV']")
	private WebElement uploadCSVFileText;

	public WebElement getUploadCSVFileText(int timeOut) {
		return isDisplayed(driver, uploadCSVFileText, "Visibility", timeOut, "upload csv file text");
	}
	
	@FindBy(xpath = "//div[@class='selectFile']//input[@name='file']")
	private WebElement chooseCSVFile;

	public WebElement getChooseCSVFile(int timeOut) {
		return isDisplayed(driver, chooseCSVFile, "Visibility", timeOut, "choose csv file input box");
	}
	
	@FindBy(xpath = "//div[@data-aura-class='dataImporterDiButtonBar']//a[text()='Next']")
	private WebElement dataImportNextButton;

	public WebElement getDataImportNextButton(int timeOut) {
		return isDisplayed(driver, dataImportNextButton, "Visibility", timeOut, "data import next button");
	}
	
	public List<WebElement> getMappedSalesforceObjectList(){
		return FindElements(driver, "//table[contains(@class,'list-view-field-mapping')]/tbody/tr/td[2]", "mapped salesforce object list");
	}
	
	
	public List<WebElement> getCSVHeaderList(){
		return FindElements(driver, "//table[contains(@class,'list-view-field-mapping')]/tbody/tr/td[3]", "CSV file header list");
	}
	
	@FindBy(xpath = "//div[@data-aura-class='dataImporterDiButtonBar']//a[text()='Start Import']")
	private WebElement startImportButton;

	public WebElement getStartImportButton(int timeOut) {
		return isDisplayed(driver, startImportButton, "Visibility", timeOut, "start import button");
	}
	
	@FindBy(xpath = "//a[@data-aura-class='uiOutputURL' and text()='OK']")
	private WebElement dataImportOKBtn;

	public WebElement getDataImportOKBtn(int timeOut) {
		return isDisplayed(driver, dataImportOKBtn, "Visibility", timeOut, "data import Congratulations popup OK button");
	}
	
	@FindBy(xpath = "//td[@class='pbButton ']//input[@value='Reload']")
	private WebElement reloadButton;
	
	
	
	public WebElement getReloadButton(int timeOut) {
		return isDisplayed(driver, reloadButton, "Visibility", timeOut, "reload button");
	}


	@FindBy(xpath = "//th[text()='Status']/following-sibling::td/span[text()='Closed']")
	private WebElement jobStatusClosedText;

	public WebElement getJobStatusClosedText(int timeOut) {
		return isDisplayed(driver, jobStatusClosedText, "Visibility", timeOut, "job status close text");
	}
	
	public WebElement getRecordsProcessedCount(int timeOut) {
		String xpath="//label[contains(text(),'Records Processed')]/../following-sibling::td/span";
		return FindElement(driver, xpath, "records processed count", action.SCROLLANDBOOLEAN, timeOut);
		
	}
	
	public WebElement getRecordsFailedCount(int timeOut) {
		String xpath="//label[contains(text(),'Records Failed')]/../following-sibling::td/span";
		return FindElement(driver, xpath, "records failed count", action.SCROLLANDBOOLEAN, timeOut);
		
	}
	
	@FindBy(xpath = "//a[@title='Cancel your import']")
	private WebElement cancelYourImportBtn;

	public WebElement getCancelYourImportBtn(int timeOut) {
		return isDisplayed(driver, cancelYourImportBtn, "Visibility", timeOut, "cancel your import button");
	}
	
	
	@FindBy(xpath = "//a[@class='button success uiOutputURL' and text()='Quit']")
	private WebElement quiteDataImportBtn;

	public WebElement getQuiteDataImportBtn(int timeOut) {
		return isDisplayed(driver, quiteDataImportBtn, "Visibility", timeOut, "quite data import button");
	}
	
	
	@FindBy(xpath = "//div[text()='Unmapped fields']/span[text()='0']")
	private WebElement unmappedFieldText;
	
	

	public WebElement getUnmappedFieldText(int timeOut) {
		return isDisplayed(driver, unmappedFieldText, "Visibility", timeOut, "unmapped field text");
	}
	
	
	@FindBy(xpath = "//div[@data-aura-class='dataImporterDiMatchingChoiceActivity']//tr[1]/td/div/div[6]/select")
	private WebElement selectAccountNameFromDropDownList;

	public WebElement getSelectAccountNameFromDropDownList(int timeOut) {
		return isDisplayed(driver, selectAccountNameFromDropDownList, "Visibility", timeOut, "select account name from drop down");
	}
	
	@FindBy(xpath = "//div[@data-aura-class='dataImporterDiMatchingChoiceActivity']//tr[1]/td/div/div[8]/select")
	private WebElement selectDealNameFromDropDownList;

	public WebElement getSelectDealNameFromDropDownList(int timeOut) {
		return isDisplayed(driver, selectDealNameFromDropDownList, "Visibility", timeOut, "select deal name from drop down");
	}
	
	@FindBy(xpath = "//div[@data-aura-class='dataImporterDiMatchingChoiceActivity']//tr[1]/td/div/div[4]/select")
	private WebElement accountMatchByDropDownList;

	public WebElement getAccountMatchByDropDownList(int timeOut) {
		return isDisplayed(driver, accountMatchByDropDownList, "Visibility", timeOut, "account match by drop down list");
	}
	
	@FindBy(xpath = "//option[@label='Fund Name']/parent::select")
	private WebElement fundNameLookUpFieldDropDownInFundRaising;

	public WebElement getFundNameLookUpFieldDropDownInFundRaising(int timeOut) {
		return isDisplayed(driver, fundNameLookUpFieldDropDownInFundRaising, "Visibility", timeOut, "fund Name LookUp Field DropDown In FundRaising");
	}
	
	@FindBy(xpath = "//option[@label='Navigation Name']/parent::select")
	private WebElement navigationNameLookUpFieldDropDownInFundRaising;

	public WebElement getNavigationNameLookUpFieldDropDownInNavigation(int timeOut) {
		return isDisplayed(driver, navigationNameLookUpFieldDropDownInFundRaising, "Visibility", timeOut, "navigation Name LookUp Field DropDown In FundRaising");
	}
	
	@FindBy(xpath = "(//span[text()='Which Firm field in your file do you want to match against to set the Legal Name lookup field?']/parent::div/following-sibling::div/select)[1]")
	private WebElement legalNameDropDownListInFundRaising;

	public WebElement getLegalNameDropDownListInFundRaising(int timeOut) {
		return isDisplayed(driver, legalNameDropDownListInFundRaising, "Visibility", timeOut, "legal Name DropDown List In FundRaising");
	}
	
	@FindBy(xpath = "//mark[text()='Data Import Wizard']")
	private WebElement dataImportWizardBtnLink;

	public WebElement getDataImportWizardBtnLink(int timeOut) {
		return isDisplayed(driver, dataImportWizardBtnLink, "Visibility", timeOut, "Data Import Wizard Link");
	}
	
	@FindBy(xpath = "//iframe[@title='Salesforce - Enterprise Edition']")
	private WebElement dataImportWizardIFrame;

	public WebElement getDataImportWizardIFrame(int timeOut) {
		return isDisplayed(driver, dataImportWizardIFrame, "Visibility", timeOut, "Data Import Wizard IFrame");
	}
	
	@FindBy(xpath = "//iframe[@title='Bulk Data Load Job ~ Salesforce - Enterprise Edition']")
	private WebElement bulkDataLoadJobIFrame;

	public WebElement getBulkDataLoadJobIFrame(int timeOut) {
		return isDisplayed(driver, bulkDataLoadJobIFrame, "Visibility", timeOut, "Bulk Data Load Job IFrame");
	}
	
	@FindBy(xpath = "//span[text()='Record type']/../following-sibling::div/select")
	private WebElement recordTypeDropDownList;

	public WebElement getRecordTypeDropDownList(int timeOut) {
		return isDisplayed(driver, recordTypeDropDownList, "Visibility", timeOut, "account match by drop down list");
	}
	
	
}
