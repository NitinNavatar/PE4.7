package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ProjectName;
import com.navatar.generic.EnumConstants.RecordType;
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
	@FindBy(xpath="//h2//a[text()='Office Location']")
	private WebElement officeLocation1;
	 
	public WebElement getOfficeLocation(String environment,String mode,RecordType recordType,int timeOut){
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
//			if(click(driver, getRelatedTab_Lighting(environment,recordType, 10), "Related Tab", action.SCROLLANDBOOLEAN)){
//				log(LogStatus.INFO, "Clicked on Related Tab", YesNo.No);
//				ThreadSleep(3000);
//				scrollThroughOutWindow(driver);
//			}
			ThreadSleep(2000);
		}
		return isDisplayed(driver, officeLocation1, "Visibility", timeOut, "office Location");	
	}
	@FindBy(xpath="//input[@title='Continue']")
	private WebElement continueBtnClassic;

	
	/**
	 * @return the continueBtn
	 */
	public WebElement getContinueOrNextBtn(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, continueBtnClassic, "Visibility", timeOut, "Next Button Classic");	
		}else{
			
			List<WebElement> eleList = FindElements(driver, "//span[text()='Next']", "Next Button");
			for (WebElement webElement : eleList) {
			webElement=isDisplayed(driver, webElement, "Visibility", 2, "Next Button lightning");
			if (webElement!=null) {
					return webElement;
				} else {

				}
			}
			
			return isDisplayed(driver, nextBtnLighting, "Visibility", timeOut, "Next Button Lighting");
		}
		
	}
	
	
	 
	public WebElement getNewOfficeLocationButton(String environment,String mode,RecordType recordType,int timeOut){
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {

			scrollThroughOutWindow(driver);
			return isDisplayed(driver, newofficeLocationBtn_Lighting, "Visibility", timeOut, "office Location : "+mode);	
	
			
		}else{
			return isDisplayed(driver, newOfficeLocationBtn_Classic, "Visibility", timeOut, "office Location : "+mode);		
		}
		
		
	}
	/**
	 * @return the commitmentDetailsLabelText
	 */
	public WebElement getFundCommitmentDetailsLabelText(String environment, String mode,int timeOut) {
		WebElement ele=null;
		String xpath="";
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			xpath="a";
		}else {
			xpath="h3";
		}
		ele=FindElement(driver, "//"+xpath+"[text()='Fund Commitments']", "Fund commitment details label text", action.SCROLLANDBOOLEAN,timeOut);
		return isDisplayed(driver,ele , "Visibility", timeOut, "Fund commitment details label text");
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
	
	
	@FindBy(xpath="//*[text()='Entity Type']/..//input")
	private WebElement entityTypeDropdownLighting;
	
	@FindBy(xpath="//*[text()='Entity Type']/../..//select")
	private WebElement entityTypeDropdownClassic;


	/**
	 * @return the entity type text bx
	 */
	public WebElement getEntityTypeDropdown(String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, entityTypeDropdownClassic, "Visibility", timeOut, "Entity type dropdown Classic");
		}else{
			return isDisplayed(driver, entityTypeDropdownLighting, "Visibility", timeOut, "Entity type dropdown Lightning");
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
			xpath="//*[text()='"+finalLabelName+"']";
			inputXpath="/following-sibling::div/input";
			textAreaXpath="/following-sibling::div/textarea";
			if(labelName.toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Institution.toString())) {
				xpath="//*[contains(text(),'Parent Firm')]";
				inputXpath="/following-sibling::div//input[contains(@placeholder,'Search')]";
			}
			else if(labelName.toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Entity.toString()))
			{
				xpath="//*[contains(text(),'Parent Entity')]";
				inputXpath="/following-sibling::div//input[contains(@placeholder,'Search')]";
			
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
	
	
//	PUBLIC LIST<WEBELEMENT> GETCOMMITMENTIDLIST(STRING ENVIRONMENT, STRING MODE){
//		RETURN FINDELEMENTS(DRIVER, "//SPAN[CONTAINS(@ID,'GRID_DEALALERT-CELL-1-')]", "COMMITMENT ID LIST");
//	}
	
	public List<WebElement> getFundNameList(String environment, String mode){
		String xpath="//div[contains(@class,'windowViewMode-normal')]//a[text()='Fund Commitments']/ancestor::article//td[contains(@class,'Fund')]";
		return FindElements(driver,xpath, "fund name in commitment list");
	}
	
	public List<WebElement> getCommitmentAmountList(String environment, String mode){
		String xpath="//div[contains(@class,'windowViewMode-normal')]//a[text()='Fund Commitments']/ancestor::article//td[contains(@class,'Commitment_Amount')]";
		return FindElements(driver,xpath, "commitment amount in commitment list");
	}
	
	public List<WebElement> getLimitedPartnerList(String environment, String mode){
		String xpath="//div[contains(@class,'windowViewMode-normal')]//a[text()='Fund Commitments']/ancestor::article//td[contains(@class,'Limited_Partner')]";
		return FindElements(driver, xpath, "LP in commitment  list");
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
	
	@FindBy(xpath="//*[text()='Legal Name']/following-sibling::*/input")
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
	
	
	
	@FindBy(xpath="//a[contains(text(),'Contacts')]/ancestor::header//button[text()='New Contact']")
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
	
	@FindBy(xpath="//*[text()='Account Name']/following-sibling::*//input")
	private WebElement accountNameMNA;
	
	@FindBy(xpath="//*[text()='Legal Name']/following-sibling::*//input")
	private WebElement legalNamePE;
	
	@FindBy(xpath="//*[text()='Firm']/following-sibling::*//input")
	private WebElement FirmPEEdge;

	/**
	 * @return the legalName
	 */
	public WebElement getLegalName(String projectName,int timeOut) {
		
		if (ProjectName.MNA.toString().equals(projectName)) {
			return isDisplayed(driver, accountNameMNA, "Visibility", timeOut, "Account Name");
		} else if (projectName.contains(ProjectName.PE.toString())) {
			return isDisplayed(driver, legalNamePE, "Visibility", timeOut, "Legal Name");
		}else  {
			return isDisplayed(driver, legalNamePE, "Visibility", timeOut, "Firm");
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
	
	
	@FindBy(xpath="//*[text()='Partnership Legal Name']/following-sibling::div/input")
	private WebElement partnershipLegalName_Lighting;

	/**
	 * @return the partnershipLegalName
	 */
	public WebElement getPartnershipLegalName(String environment,String mode,int timeOut) {
		
			 return isDisplayed(driver, partnershipLegalName_Lighting, "Visibility", timeOut, "Partnership Legal Name Lighting");
		
		
	}
	
	@FindBy(xpath="//*[text()='Fund']/following-sibling::div//input[@title='Search Funds' or contains(@placeholder,'Search Funds')]")
	private WebElement fundTextBox_Lighting;


	/**
	 * @return the fundTextBox
	 */
	public WebElement getFundTextBox(String environment,String mode,int timeOut) {
		
			return isDisplayed(driver, fundTextBox_Lighting, "Visibility", timeOut, "Fund Name Text Box Lighting");
		
	
	}
	
	@FindBy(xpath="//div[@id='Name_ileinner']")
	private WebElement partnershipNameInViewMode_Classic;
	
	@FindBy(xpath="//div[@class='slds-media__body']//h1//span[@data-aura-class='uiOutputText']")
	private WebElement partnershipNameInViewMode_Lighting;

	/**
	 * @return the partnershipNameInViewMode
	 */
	public WebElement getPartnershipNameInViewMode(String environment,String mode,int timeOut,String partnershipName) {
	WebElement ele = FindElement(driver, "//*[contains(text(),'Partnership Legal Name')]/../..//*[text()='"+partnershipName+"']", "partnershipName", action.SCROLLANDBOOLEAN, timeOut);
		return ele;
	}
	
	public WebElement getCommitmentIdInViewMode(String environment,String mode,int timeOut) {
	
		String xpath="//*[text()='Commitment']/../*/*[@slot='primaryField']/*";
		WebElement ele = FindElement(driver,xpath, "commitment id xpath", action.SCROLLANDBOOLEAN, timeOut);
		return ele;
	}
	
	@FindBy(xpath="//*[text()='Limited Partner']/following-sibling::div//input")
	private WebElement limitedPartnerTextbox_Lighting;

	/**
	 * @return the limitedPArtnerTextbox
	 */
	public WebElement getLimitedPartnerTextbox(String mode,int timeOut) {
	return isDisplayed(driver, limitedPartnerTextbox_Lighting, "Visibility", timeOut, "Limited Partner Text Box Lighting");
	}
	
	
	@FindBy(xpath="//*[text()='Partnership']/following-sibling::div//input[@title='Search Partnerships' or contains(@placeholder,'Search Partnerships')]")
	private WebElement partnershipTextBox_Lighting;

	/**
	 * @return the partnershipTextBox
	 */
	public WebElement getPartnershipTextBox(String mode,int timeOut) {
		
		return isDisplayed(driver, partnershipTextBox_Lighting, "Visibility", timeOut, "Partnership Text Box Lighting");
	
	
	}
	@FindBy(xpath="//div[contains(@class,'Fullcalendar')]//h2")
	private WebElement calenderHeader;

	/**
	 * @return the partnershipTextBox
	 */
	public WebElement getcalenderHeader(String projectName,int timeOut) {
		
		return isDisplayed(driver, calenderHeader, "Visibility", timeOut, "calender Header");
	
	
	
	}
	@FindBy(xpath="//h2[text()='Event Invitees']")
	private WebElement eventInviteesHeader;

	/**
	 * @return the partnershipTextBox
	 */
	public WebElement geteventInviteesHeader(String projectName,int timeOut) {
		
		return isDisplayed(driver, eventInviteesHeader, "Visibility", timeOut, "eventInvitees Header");
	
	
	}
	
	@FindBy(xpath="//button[@aria-label='prev']")
	private WebElement previousButtonOnCalender;

	/**
	 * @return the partnershipTextBox
	 */
	public WebElement getpreviousButtonOnCalender(String projectName,int timeOut) {
		
		return isDisplayed(driver, previousButtonOnCalender, "Visibility", timeOut, "previous Button On Calender");
	
	
	}
	@FindBy(xpath = "//*[text()='Limited Partner']/following-sibling::*//input[contains(@placeholder,'Search Entities')]")
	private WebElement searchEntitiesTextbox;
public WebElement getsearchEntitiesTextbox(String projectName,int timeOut) {
		
		return isDisplayed(driver, searchEntitiesTextbox, "Visibility", timeOut, "previous Button On Calender");
	
	
	}

@FindBy(xpath = "//*[text()='Last Modified By']/../following-sibling::div//lightning-formatted-text")
private WebElement lastModifiedTime;

public WebElement getLastModifiedTime(String projectName,int timeOut) {
	
	return isDisplayed(driver, lastModifiedTime, "Visibility", timeOut, "lastModifiedTime");


}

	
	public List<WebElement> getAllInstituitionRecrdTypeList(String mode,int timeOut){
		String xpath;
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			xpath ="//select[@name='p3']/option";
			
		}else{
		 xpath="//div[@class='changeRecordTypeOptionRightColumn']/span";
		}
		return FindElements(driver, xpath,"institution record type list ");
	}

public WebElement getHighlightPanelFieldLabel(String projectName,String fieldName,int timeOut){
		
		WebElement element = null;
		String xpath = "//div[@class='secondaryFields']//*[@title='"+fieldName+"']";
		
		List<WebElement> list= FindElements(driver, xpath, "Highlight panel element");
		for(WebElement ele:list){
			
			element=isDisplayed(driver, ele, "visibility", timeOut, fieldName);
			
			if(element!=null){
						
				return element;
			}else{
				
			}
			
		}
		
		return null;
	}
	
}
