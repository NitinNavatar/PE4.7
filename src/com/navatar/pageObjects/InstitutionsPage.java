package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.navatar.generic.EnumConstants.ProjectName;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;

import static com.navatar.generic.CommonLib.*;

import java.util.List;

public class InstitutionsPage extends BasePageBusinessLayer {

	public InstitutionsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath="//select[@name='p3']")
	private WebElement recordTypeOfNewRecordDropDownList;
	
	/**
	 * @return the recordTypeOfNewRecordDropDownList
	 */
	public WebElement getRecordTypeOfNewRecordDropDownList(int timeOut) {
		return isDisplayed(driver, recordTypeOfNewRecordDropDownList, "Visibility", timeOut, "Record type of new record drop down list");
	}
	
	@FindBy(xpath="//input[@title='Continue']")
	private WebElement continueBtnClassic;

	
	/**
	 * @return the continueBtn
	 */
	public WebElement getContinueOrNextBtn(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, continueBtnClassic, "Visibility", timeOut, "Continue Button Classic");	
		}else{
			return isDisplayed(driver, nextBtnLighting, "Visibility", timeOut, "Next Button Lighting");
		}
		
	}
	
	@FindBy(xpath="//input[@name='acc2']")
	private WebElement legalNameTextBoxClassic;
	
	
	/**
	 * @return the legalNameTextBox
	 */
	public WebElement getLegalNameTextBox(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, legalNameTextBoxClassic, "Visibility", timeOut, "Legal Name Text Box Classic");
		}else{
			return isDisplayed(driver, legalNameTextBoxLighting, "Visibility", timeOut, "Legal Name Text Box Lighting");
		}
		
	}
	 
	
	
	@FindBy(xpath="//div[@id='acc2_ileinner']")
	private WebElement legalNameLabelTextboxClassic;

	/**
	 * @return the legalNameLabelTextbox
	 */
	public WebElement getLegalNameLabelTextbox(String environment,String mode,String institution,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, legalNameLabelTextboxClassic, "Visibility", timeOut, "Legal Name Label Text Box Classic");
		}else{
		
			return isDisplayed(driver, legalNameHeader, "Visibility", timeOut, "Legal Name Label Text Box Lighting");
		}
	
	}
	
	@FindBy(xpath="//div[@class='changeRecordTypeRow']//span[text()='Institution']/../..")
	private WebElement radioButtonforNewInstitution;
	
	
	
	@FindBy(xpath="//div[@class='changeRecordTypeRow']//span[text()='Limited Partner']/../..")
	private WebElement radioButtonforNewLP;
	
	/**
	 * @return the radioButtonforNewInstitution
	 */
	public WebElement getRadioButtonforLP(int timeOut) {
		return isDisplayed(driver, radioButtonforNewLP, "Visibility", timeOut, "Radio Button for New LP");
	}

	@FindBy(xpath="//input[@name='acc3']")
	private WebElement parentInstitutionTextBoxClassic;
	
	@FindBy(xpath="//label[@data-aura-class='uiLabel']//span[text()='Parent Institution']/..//following-sibling::div//input")
	private WebElement parentInstitutionTextBoxLighting;


	/**
	 * @return the parentInstitutionTextBox
	 */
	public WebElement getParentInstitutionTextBox(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, parentInstitutionTextBoxClassic, "Visibility", timeOut, "Parent Institution Text Box Classic");
		}else{
			return isDisplayed(driver, parentInstitutionTextBoxLighting, "Visibility", timeOut, "Parent Institution Text Box Lighting");
		}
	}
	
	public WebElement getInstitutionPageTextBoxOrRichTextBoxWebElement(String environment,String mode, String labelName, int timeOut) {
		WebElement ele=null;
		String xpath ="",inputXpath="", textAreaXpath="",finalXpath="",finalLabelName="";
		if(labelName.equalsIgnoreCase(excelLabel.Shipping_State.toString())) {
			labelName=InstitutionPageFieldLabelText.Shipping_State.toString();
		}else if (labelName.equalsIgnoreCase(excelLabel.Shipping_Zip.toString())) {
			labelName=InstitutionPageFieldLabelText.Shipping_Zip.toString();
		}
		if(labelName.contains("_")) {
			finalLabelName=labelName.replace("_", " ");
		}else {
			finalLabelName=labelName;
		}
		if(mode.equalsIgnoreCase(Mode.Classic.toString())) {
			xpath="//label[text()='"+finalLabelName+"']";
			inputXpath="/../following-sibling::td/input";
			textAreaXpath="/../following-sibling::td/textarea";
			if(labelName.toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Institution.toString())) {
				inputXpath="/../following-sibling::td//span/input";
			}
			
		}else {
			//span[text()='Description']/..//following-sibling::textarea
			xpath="//span[text()='"+finalLabelName+"']";
			inputXpath="/..//following-sibling::input";
			textAreaXpath="/..//following-sibling::textarea";
			if(labelName.toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Institution.toString())) {
				inputXpath="/..//following-sibling::div//input[@title='Search Institutions']";
			}
			
		}
		
		if(labelName.equalsIgnoreCase(InstitutionPageFieldLabelText.Description.toString()) || labelName.equalsIgnoreCase(InstitutionPageFieldLabelText.Referral_Source_Description.toString()) 
				|| labelName.equalsIgnoreCase(InstitutionPageFieldLabelText.Street.toString()) || labelName.equalsIgnoreCase(excelLabel.Shipping_Street.toString())) {
			finalXpath=xpath+textAreaXpath;
		}else {
			finalXpath=xpath+inputXpath;
		}
		ele=isDisplayed(driver, FindElement(driver, finalXpath, finalLabelName+" text box in "+mode, action.SCROLLANDBOOLEAN,30), "Visibility", timeOut, finalLabelName+"text box in "+mode);
		return ele;
		
	}
	
	@FindBy(xpath="//span[text()='Related']")
	private WebElement relatedList_Lighting;

	/**
	 * @return the relatedList_Lighting
	 */
	public WebElement getRelatedList_Lighting(int timeOut) {
		return isDisplayed(driver, relatedList_Lighting, "Visibility", timeOut, "related list tab in lighting");
	}
	
	
	@FindBy(xpath="//td[@class='pbButton']//input[@name='newContact']")
	private WebElement newContactBtn_Classic;
	
	
	/**
	 * @param environment
	 * @param mode
	 * @param timeOut
	 * @return
	 */
	public WebElement getNewContactBtn(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, newContactBtn_Classic, "Visibility", timeOut, "new contact button Classic");
		}else{
			return isDisplayed(driver, newContactBtn_Lighting, "Visibility", timeOut, "new contact butto Lighting");
		}
	}
	
	
	@FindBy(xpath="//span[text()='Pipelines']/ancestor::article//span[text()='View All']")
	private WebElement pipeLineViewAll_Lighting;
	
	
	/**
	 * @param environment
	 * @param timeOut
	 * @return
	 */
	public WebElement getPipeLineViewAll_Lighting(String environment,int timeOut) {
		return isDisplayed(driver, pipeLineViewAll_Lighting, "Visibility", timeOut, "PipeLine View All");
	
	}
	
	@FindBy(xpath="//span[text()='Deals Sourced']/ancestor::article//span[text()='View All']")
	private WebElement dealSourcedViewAll_Lighting;
	
	
	/**
	 * @param environment
	 * @param timeOut
	 * @return
	 */
	public WebElement getDealSourcedViewAll_Lighting(String environment,int timeOut) {
		scrollThroughOutWindow(driver);
		return isDisplayed(driver, dealSourcedViewAll_Lighting, "Visibility", timeOut, "Deal View All");
	
	}
	
	@FindBy(xpath="//span[text()='Office Locations']")
	private WebElement officeLocation;
	 
	
	
	@FindBy(xpath="//h3[text()='Office Locations']/../following-sibling::td/input[@title='New Office Location']")
	private WebElement newOfficeLocationBtn_Classic;
	
	@FindBy(xpath="//span[text()='Office Locations']/ancestor::article//a/div[@title='New']")
	private WebElement newofficeLocationBtn_Lighting;
	 
	
	
	@FindBy(xpath="//form[@id='editPage']//input[@title='Save']")
	private WebElement saveOfficeLocationBtn_Classic;
	
	@FindBy(xpath="//div[@class='modal-footer slds-modal__footer']//button[@title='Save']")
	private WebElement saveOfficeLocationBtn_Lighting;
	 
	public WebElement getSaveOfficeLocationButton(String environment,String mode,int timeOut){
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			return getSaveButton(timeOut);
		}else{
			return isDisplayed(driver, saveOfficeLocationBtn_Classic, "Visibility", timeOut, "Save office Location Button : "+mode);		
		}
		
	}
	
	@FindBy(xpath="//span[text()='Office Locations']/ancestor::article//span[text()='View All']")
	private WebElement officeLocationViewAll_Lighting;
	
	
	/**
	 * @param environment
	 * @param timeOut
	 * @return
	 */
	public WebElement getOfficeLocationViewAll_Lighting(String environment,int timeOut) {
		return isDisplayed(driver, officeLocationViewAll_Lighting, "Visibility", timeOut, "Office Location View All");
	
	}

	
	@FindBy(xpath="//div[@id='brandBand_1']//li/a/div[@title='Edit']")
	private WebElement editofficeLocationBtn_Lighting;
	
	public WebElement getEditLinkOnOfficeLocation_Lighting(String environment,int timeOut){
		return isDisplayed(driver, editofficeLocationBtn_Lighting, "Visibility", timeOut, "office Location Edit");	
		//return editofficeLocationBtn_Lighting;
	}
	
	
	public List<WebElement> getCommitmentIDList(String environment, String mode){
		return FindElements(driver, "//span[contains(@id,'grid_dealalert-cell-1-')]", "commitment id list");
	}
	
	public List<WebElement> getCommitmentAmountList(String environment, String mode){
		return FindElements(driver, "//span[contains(@id,'grid_dealalert-cell-2-')]", "commitment id list");
	}
	
	public List<WebElement> getLimitedPartnerList(String environment, String mode){
		return FindElements(driver, "//span[contains(@id,'grid_dealalert-cell-3-')]", "commitment id list");
	}
	
	public List<WebElement> getPartnerShipList(String environment, String mode){
		return FindElements(driver, "//span[contains(@id,'grid_dealalert-cell-4-')]", "commitment id list");
	}
	
	public List<WebElement> getCompanyNameList(String environment, String mode, YesNo companyNameEmptyOrNot){
		if(companyNameEmptyOrNot.toString().equalsIgnoreCase(YesNo.Yes.toString())) {
			return FindElements(driver, "//span[contains(@id,'grid_dealalert-cell-5-')]", "companyName");
		}else {
			return FindElements(driver, "//span[contains(@id,'grid_dealalert-cell-5-')]", "companyName");
		}
	}
	
	public List<WebElement> createdDateList(String environment, String mode){
		return FindElements(driver, "//span[contains(@id,'grid_dealalert-cell-6-')]", "created date list");
	}
	
	
	public List<WebElement> gettotalCommitmentAmount(String environment, String mode){
		return FindElements(driver, "//span[contains(@id,'grid_dealalert-cell-2-')]/span", "total commitment amount list");
	}
	
	@FindBy(xpath="//h3[text()='Commitment Details']")
	private WebElement commitmentDetailsLabelText;

	/**
	 * @return the commitmentDetailsLabelText
	 */
	public WebElement getCommitmentDetailsLabelText(String environment, String mode,int timeOut) {
		WebElement ele=null;
		String xpath="";
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			xpath="span";
		}else {
			xpath="h3";
		}
		return isDisplayed(driver, FindElement(driver, "//"+xpath+"[text()='Commitment Details']", "commitment details label text", action.SCROLLANDBOOLEAN,timeOut), "Visibility", timeOut, "commitment details label text");
	}
	
	@FindBy(xpath="//iframe[@title='FundView']")
	private WebElement commitmentDetailsFrame;

	@FindBy(xpath="(//iframe[@title='accessibility title'])[1]")
	private WebElement commitmentDetailsFrame_Lightning;
	
	/**
	 * @return the commitmentDetailsFrame
	 */
	public WebElement getCommitmentDetailsFrame(String environment, String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			return isDisplayed(driver, commitmentDetailsFrame_Lightning, "Visibility", timeOut, "commitmentDetailsFrame");
		}else {
			return isDisplayed(driver, commitmentDetailsFrame, "Visibility", timeOut, "commitmentDetailsFrame");
			
		}
	}
	
	/////////////////////////////////////////////////////////// Activity ASSOCIATION ////////////////////////////////////////////////////////
	
	
	
	@FindBy(xpath="//span[text()='Next']")
	private WebElement nextBtnLighting;
	
	/**
	 * @return the continueBtn
	 */
	public WebElement getContinueOrNextBtn(String projectName,int timeOut) {
		
			return isDisplayed(driver, nextBtnLighting, "Visibility", timeOut, "Next Button Lighting");
		
		
	}
	
	@FindBy(xpath="//label[@data-aura-class='uiLabel']//span[text()='Legal Name']/..//following-sibling::input")
	private WebElement legalNameTextBoxLighting;
	
	/**
	 * @return the legalNameTextBox
	 */
	public WebElement getLegalNameTextBox(String projectName,int timeOut) {
		return isDisplayed(driver, legalNameTextBoxLighting, "Visibility", timeOut, "Legal Name Text Box Lighting");
	}
	
	public WebElement getInstitutionPageTextBoxOrRichTextBoxWebElement(String projectName,String labelName, int timeOut) {
		WebElement ele=null;
		String xpath ="",inputXpath="", textAreaXpath="",finalXpath="",finalLabelName="";
		if(labelName.equalsIgnoreCase(excelLabel.Shipping_State.toString())) {
			labelName=InstitutionPageFieldLabelText.Shipping_State.toString();
		}else if (labelName.equalsIgnoreCase(excelLabel.Shipping_Zip.toString())) {
			labelName=InstitutionPageFieldLabelText.Shipping_Zip.toString();
		}
		if(labelName.contains("_")) {
			finalLabelName=labelName.replace("_", " ");
		}else {
			finalLabelName=labelName;
		}
		
			//span[text()='Description']/..//following-sibling::textarea
			xpath="//span[text()='"+finalLabelName+"']";
			inputXpath="/..//following-sibling::input";
			textAreaXpath="/..//following-sibling::textarea";
			if(labelName.toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Institution.toString())) {
				inputXpath="/..//following-sibling::div//input[@title='Search Institutions']";
			}
			
		
		
		if(labelName.equalsIgnoreCase(InstitutionPageFieldLabelText.Description.toString()) || labelName.equalsIgnoreCase(InstitutionPageFieldLabelText.Referral_Source_Description.toString()) 
				|| labelName.equalsIgnoreCase(InstitutionPageFieldLabelText.Street.toString()) || labelName.equalsIgnoreCase(excelLabel.Shipping_Street.toString())) {
			finalXpath=xpath+textAreaXpath;
		}else {
			finalXpath=xpath+inputXpath;
		}
		ele=isDisplayed(driver, FindElement(driver, finalXpath, finalLabelName+" text box in "+projectName, action.SCROLLANDBOOLEAN,30), "Visibility", timeOut, finalLabelName+"text box in "+projectName);
		return ele;
		
	}
	
	


	/**
	 * @return the legalNameLabelTextbox
	 */
	public WebElement getLegalNameLabelTextbox(String projectName,String institution,int timeOut) {
		
		
			return isDisplayed(driver, legalNameHeader, "Visibility", timeOut, "Legal Name Label Text Box Lighting");
		
	
	}
	
	
	
	@FindBy(xpath="//span[contains(text(),'Contacts')]/ancestor::header/following-sibling::div[@class='slds-no-flex']//a")
	private WebElement newContactBtn_Lighting;
	
	
	/**
	 * @param environment
	 * @param mode
	 * @param timeOut
	 * @return
	 */
	public WebElement getNewContactBtn(String projectName,int timeOut) {
		
			return isDisplayed(driver, newContactBtn_Lighting, "Visibility", timeOut, "new contact butto Lighting");
		}
	
	@FindBy(xpath="//span[text()='Account Name']/../following-sibling::input")
	private WebElement accountNameMNA;
	
	@FindBy(xpath="//span[text()='Legal Name']/../following-sibling::input")
	private WebElement legalNamePE;
	
	@FindBy(xpath="//span[text()='Firm']/../following-sibling::input")
	private WebElement FirmPEEdge;

	/**
	 * @return the legalName
	 */
	public WebElement getLegalName(String projectName,int timeOut) {
		
		if (ProjectName.MNA.toString().equals(projectName)) {
			return isDisplayed(driver, accountNameMNA, "Visibility", timeOut, "Account Name");
		} else if (ProjectName.PE.toString().equals(projectName)) {
			return isDisplayed(driver, legalNamePE, "Visibility", timeOut, "Legal Name");
		}else  {
			return isDisplayed(driver, FirmPEEdge, "Visibility", timeOut, "Firm");
		}
			
			
		
	
	} 
	

	@FindBy(xpath="//span[@class='custom-truncate uiOutputText']")
	private WebElement legalNameHeader;
	
	/**
	 * @return the legalNameLabelTextbox
	 */
	public WebElement getLegalNameHeader(String projectName,int timeOut) {
	
			return isDisplayed(driver, legalNameHeader, "Visibility", timeOut, "Legal Name Header");
	
	
	}
	@FindBy(xpath="//span[text()='Website']/../following-sibling::input")
	private WebElement websiteTextbox;
	
	/**
	 * @return the legalNameLabelTextbox
	 */
	public WebElement getwebsiteTextbox(String projectName,int timeOut) {
	
			return isDisplayed(driver, websiteTextbox, "Visibility", timeOut, "websiteTextbox");
	
	
	}
	
}