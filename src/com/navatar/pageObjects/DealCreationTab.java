package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElements;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.navatar.generic.EnumConstants.Mode;

public class DealCreationTab extends NavatarSetupPageBusinessLayer {

	public DealCreationTab(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//label[text()='Company']/../following-sibling::td/input")
	private WebElement companyNameTextBox;

	/**
	 * @return the companyNameTextBox
	 */
	public WebElement getCompanyNameTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, companyNameTextBox, "Visibility", timeOut, "Company Name TextBox");
	}
	
	@FindBy(xpath="//label[text()='Deal Name']/../following-sibling::td/input")
	private WebElement pipeLineNameTextBox;

	/**
	 * @return the pipeLineNameTextBox
	 */
	public WebElement getPipeLineNameTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, pipeLineNameTextBox, "Visibility", timeOut, "PipeLine Name TextBox");
	}
	
	@FindBy(xpath="//select[@id='thePage:theForm:pipelinestage']")
	private WebElement stageDropDownList;

	/**
	 * @return the stageDropDownList
	 */
	public WebElement getStageDropDownList(String environment,String mode,int timeOut) {
		return isDisplayed(driver, stageDropDownList, "Visibility", timeOut, "Stage Drop Down List");
	}
	
	@FindBy(xpath="//select[@id='thePage:theForm:pipelinesource']")
	private WebElement sourceDropDownList;

	/**
	 * @return the sourceDropDownList
	 */
	public WebElement getSourceDropDownList(String environment,String mode,int timeOut) {
		return isDisplayed(driver, sourceDropDownList, "Visibility", timeOut, "Source Drop Down List");
	}
	
	@FindBy(xpath="//label[text()='Source Firm']/../following-sibling::td//input[@placeholder='Select Existing']")
	private WebElement sourceFirmSelectExistingBox;

	/**
	 * @return the sourceFirmSelectExistingBox
	 */
	public WebElement getSourceFirmSelectExistingBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, sourceFirmSelectExistingBox, "Visibility", timeOut, "Source Firm Select Existing Box");
	}
	
	@FindBy(xpath="//label[text()='Source Contact']/../following-sibling::td//input[@placeholder='Select Existing']")
	private WebElement sourceContactSelectExistingBox;

	/**
	 * @return the sourceContactSelectExistingBox
	 */
	public WebElement getSourceContactSelectExistingBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, sourceContactSelectExistingBox, "Visibility", timeOut, "Source Contact Select Existing Box");
	}
	
	@FindBy(xpath="//input[@title='Create Deal']")
	private WebElement createDealBtn;

	/**
	 * @return the createDealBtn
	 */
	public WebElement getCreateDealBtn(String environment,String mode,int timeOut) {
		return isDisplayed(driver, createDealBtn, "Visibility", timeOut, "Create Deal Button");
	}
	
	@FindBy(xpath="//div[@id='Name_ileinner']")
	private WebElement pipeLineNameInViewMode_Classic;
	
	@FindBy(xpath="//div[@class='slds-media__body']//h1//lightning-formatted-text")
	private WebElement pipeLineNameInViewMode_Lighting;

	/**
	 * @return the fundNameLabel
	 */
	public WebElement getPipelineNameInViewMode(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, pipeLineNameInViewMode_Classic, "Visibility", timeOut, "Pipe Line Name in View Mode Classic");
		}else{
			return isDisplayed(driver, pipeLineNameInViewMode_Lighting, "Visibility", timeOut, "Pipe Line Name in View Mode Lighting");
		}
		
	}
	
	
	@FindBy(xpath="//select[@id='pageId:mainFormId:slct2']")
	private WebElement dealInformationLayout_RecordType;

	/**
	 * @return the dealInformationLayout_RecordType
	 */
	public WebElement getDealInformationLayout_RecordType(String environment,int timeOut) {
		return isDisplayed(driver, dealInformationLayout_RecordType, "Visibility", timeOut, "Deal Information Layout Record Type");
	}
	
	@FindBy(xpath="//select[@id='pageId:mainFormId:slct1']")
	private WebElement newSourceFirmLayout_RecordType;

	/**
	 * @return the newSourceFirmLayout_RecordType
	 */
	public WebElement getNewSourceFirmLayout_RecordType(String environment,int timeOut) {
		return isDisplayed(driver, newSourceFirmLayout_RecordType, "Visibility", timeOut, "New Source Firm Layout RecordType");
	}
	
	@FindBy(xpath="//select[@name='pageId:mainFormId:j_id63']")
	private WebElement companyNameDropDown;

	/**
	 * @return the companyNameDropDown
	 */
	public WebElement getCompanyNameDropDown(String environment,int timeOut) {
		return isDisplayed(driver, companyNameDropDown, "Visibility", timeOut, "companyNameDropDown");
	}
	
	@FindBy(xpath="//select[@name='pageId:mainFormId:j_id66']")
	private WebElement pipelineNameDropDown;

	/**
	 * @return the PipelineNameDropDown
	 */
	public WebElement getPipelineNameDropDown(String environment,int timeOut) {
		return isDisplayed(driver, pipelineNameDropDown, "Visibility", timeOut, "Pipeline Name DropDown");
	}
	
	@FindBy(xpath="//select[@name='pageId:mainFormId:j_id69']")
	private WebElement stageDropDown;

	/**
	 * @return the stageDropDown
	 */
	public WebElement getStageDropDown(String environment,int timeOut) {
		return isDisplayed(driver, stageDropDown, "Visibility", timeOut, "Stage DropDown");
	}
	
	@FindBy(xpath="//select[@name='pageId:mainFormId:j_id72']")
	private WebElement sourceFirmDropDown;

	/**
	 * @return the sourceFirmDropDown
	 */
	public WebElement getSourceFirmDropDown(String environment,int timeOut) {
		return isDisplayed(driver, sourceFirmDropDown, "Visibility", timeOut, "source Firm DropDown");
	}
	
	@FindBy(xpath="//select[@name='pageId:mainFormId:j_id75']")
	private WebElement sourceContactDropDown;

	/**
	 * @return the sourceContactDropDown
	 */
	public WebElement getSourceContactDropDown(String environment,int timeOut) {
		return isDisplayed(driver, sourceContactDropDown, "Visibility", timeOut, "Source Contact DropDown");
	}
	
	@FindBy(xpath="//select[@name='pageId:mainFormId:j_id78']")
	private WebElement sourceDropDown;

	/**
	 * @return the sourceDropDown
	 */
	public WebElement getSourceDropDown(String environment,int timeOut) {
		return isDisplayed(driver, sourceDropDown, "Visibility", timeOut, "Source DropDown");
	}
	
	@FindBy(xpath="//select[@name='pageId:mainFormId:j_id114']")
	private WebElement legalNameDropDown;

	/**
	 * @return the legalNameDropDown
	 */
	public WebElement getLegalNameDropDown(String environment,int timeOut) {
		return isDisplayed(driver, legalNameDropDown, "Visibility", timeOut, "legal Name DropDown");
	}
	
	@FindBy(xpath="//select[@name='pageId:mainFormId:j_id133']")
	private WebElement lastNameDropDown;

	/**
	 * @return the lastNameDropDown
	 */
	public WebElement getLastNameDropDown(String environment,int timeOut) {
		return isDisplayed(driver, lastNameDropDown, "Visibility", timeOut, "Last Name DropDown");
	}
	
	@FindBy(xpath="//span[@class='clickCollapseAI']/a[@title='Required Fields List']")
	private WebElement requiredFieldListForDealInformation;

	/**
	 * @return the requiredFieldListForDealInformation
	 */
	public WebElement getRequiredFieldListForDealInformation(String environment,int timeOut) {
		return isDisplayed(driver, requiredFieldListForDealInformation, "Visibility", timeOut, "required FieldList For DealInformation");
	}
	
	@FindBy(xpath="//span[@class='clickCollapseAI2']/a[@title='Required Fields List']")
	private WebElement requiredFieldListForNewSourceFirm;

	/**
	 * @return the requiredFieldListForNewSourceFirm
	 */
	public WebElement getRequiredFieldListForNewSourceFirm(String environment,int timeOut) {
		return isDisplayed(driver, requiredFieldListForNewSourceFirm, "Visibility", timeOut, "Required Field List For NewSourceFirm");
	}
	
	@FindBy(xpath="//span[@class='clickCollapseAI3']/a[@title='Required Fields List']")
	private WebElement requiredFieldListForNewSourceContact;

	/**
	 * @return the requiredFieldListForNewSourceContact
	 */
	public WebElement getRequiredFieldListForNewSourceContact(String environment,int timeOut) {
		return isDisplayed(driver, requiredFieldListForNewSourceContact, "Visibility", timeOut, "Required Field List For NewSourceContact");
	}
	
	@FindBy(xpath="//select/following-sibling::input[contains(@id,'dealinputleftcolmn1')]/../following-sibling::a[@title='Add']")
	private WebElement leftAddIconforDealInformationLayout;
	
	/**
	 * @return the leftAddIconforDealInformationLayout
	 */
	public WebElement getLeftAddIconforDealInformationLayout(String environment,int timeOut) {
		return isDisplayed(driver, leftAddIconforDealInformationLayout, "Clickable", timeOut, "Add Left link for DealInformation Layout");
	}
	
	@FindBy(xpath="//select/following-sibling::input[contains(@id,'dealinputrightcolmn1')]/../following-sibling::a[@title='Add']")
	private WebElement rightAddIconforDealInformationLayout;
	
	/**
	 * @return the rightddIconforDealInformationLayout
	 */
	public WebElement getRightAddIconforDealInformationLayout(String environment,int timeOut) {
		return isDisplayed(driver, rightAddIconforDealInformationLayout, "Clickable", timeOut, "Add Right link for DealInformation Layout");
	}
	
	/**
	 * @return the rightddIconforDealInformationLayout
	 */
	public List<WebElement> getLeftAutoCompleteBoxforDealInformationLayout(String environment,int timeOut) {
		
		List<WebElement> ele = FindElements(driver, "//select/following-sibling::input[contains(@id,'dealinputleftcolmn')]", "Left Auto Complate Box for Deal Information Layout");
		return ele ;
	}
	
	/**
	 * @return the rightddIconforDealInformationLayout
	 */
	public List<WebElement> getRightAutoCompleteBoxforDealInformationLayout(String environment,int timeOut) {
		
		List<WebElement> ele = FindElements(driver, "//select/following-sibling::input[contains(@id,'dealinputrightcolmn')]", "Right Auto Complate Box for Deal Information Layout");
		return ele ;
	}
	
	
	
	
	
	/**
	 * @return the leftRemoveforDealInformationLayout
	 */
	public List<WebElement> getLeftRemoveforDealInformationLayout() {
		return FindElements(driver, "//a[@title='Delete']", "Left Remove");
	}

	@FindBy(xpath="//select/following-sibling::input[contains(@id,'accinputleftcolmn1')]/../following-sibling::a[@title='Add']")
	private WebElement addIconforNewSourceFirmLayout;
	
	/**
	 * @return the leftAddIconforDealInformationLayout
	 */
	public WebElement getAddIconforNewSourceFirmLayout(String environment,int timeOut) {
		return isDisplayed(driver, addIconforNewSourceFirmLayout, "Clickable", timeOut, "Add Icon for New Source Firm Layout");
	}
	
	 
	public List<WebElement> getAutoCompleteBoxforNewSourceFirmLayout(String environment,int timeOut) {
		List<WebElement> ele = FindElements(driver, "//select/following-sibling::input[contains(@id,'accinputleftcolmn')]", "Left Auto Complate Box for New Source Firm Layout");
			return ele ;
	}
	
	@FindBy(xpath="//select/following-sibling::input[contains(@id,'coninputleftcolmn1')]/../following-sibling::a[@title='Add']")
	private WebElement addIconforNewSourceContactLayout;
	
	/**
	 * @return the leftAddIconforDealInformationLayout
	 */
	public WebElement getAddIconforNewSourceContactLayout(String environment,int timeOut) {
		return isDisplayed(driver, addIconforNewSourceContactLayout, "Clickable", timeOut, "Add Icon for New Source Contact Layout");
	}
	
	 
	public List<WebElement> getAutoCompleteBoxforNewSourceContactLayout(String environment,int timeOut) {
		List<WebElement> ele = FindElements(driver, "//select/following-sibling::input[contains(@id,'coninputleftcolmn')]", " Auto Complate Box for New Source Contact Layout");
			return ele ;
	}
	
	@FindBy(xpath="//select[@id='pageLayoutSelectAccountRequireCompId']")
	private WebElement institution_DealInfo_DropDownList;

	/**
	 * @return the getInstitution_DealInfo_DropDownList
	 */
	public WebElement getInstitution_DealInfo_DropDownList(String environment,String mode,int timeOut) {
		return isDisplayed(driver, institution_DealInfo_DropDownList, "Visibility", timeOut, "institution DealInfo DropDownList");
	}
	
	@FindBy(xpath="//select[@id='pageLayoutSelectPipelineRequireCompId']")
	private WebElement pipeLine_DealInfo_DropDownList;

	/**
	 * @return the stageDropDownList
	 */
	public WebElement getPipeLine_DealInfo_DropDownList(String environment,String mode,int timeOut) {
		return isDisplayed(driver, pipeLine_DealInfo_DropDownList, "Visibility", timeOut, "PipeLine DealInfo DropDownList");
	}
	
	@FindBy(xpath="//select[@id='pageLayoutSelectSourceFirmRequireCompId']")
	private WebElement institution_NewSourceFirm_DropDownList;

	/**
	 * @return the institution_NewSourceFirm_DropDownList
	 */
	public WebElement getInstitution_NewSourceFirm_DropDownList(String environment,String mode,int timeOut) {
		return isDisplayed(driver, institution_NewSourceFirm_DropDownList, "Visibility", timeOut, "Institution New Source Firm DropDownList");
	}
	
	@FindBy(xpath="//select[@id='pageLayoutSelectSourceContactRequireCompId']")
	private WebElement contact_NewSourceContact_DropDownList;

	/**
	 * @return the contact_NewSourceContact_DropDownList
	 */
	public WebElement getContact_NewSourceContact_DropDownList(String environment,String mode,int timeOut) {
		return isDisplayed(driver, contact_NewSourceContact_DropDownList, "Visibility", timeOut, "Contact New Source Contact DropDownList");
	}
	
	@FindBy(xpath="//input[@id='thePage:theForm:FirmInputText']")
	private WebElement newSourceFirmTextBox;

	/**
	 * @return the newsourceFirmTextBox
	 */
	public WebElement getNewSourceFirmTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, newSourceFirmTextBox, "Visibility", timeOut, "New source Firm Text Box");
	}
	
	@FindBy(xpath="//input[@id='thePage:theForm:ContInputText']")
	private WebElement newSourceContactTextBox;

	/**
	 * @return the newSourceContactTextBox
	 */
	public WebElement getNewSourceContactTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, newSourceContactTextBox, "Visibility", timeOut, "New source Contact Text Box");
	}
	
	@FindBy(xpath="//div[contains(@class,'Additional_fields_for_new_Source_Firm')]//button[@title='Add']")
	private WebElement newSourceFirmPopUpAddButton;

	/**
	 * @return the newSourceFirmPopUpAddButton
	 */
	public WebElement getNewSourceFirmAddButton(String environment,String mode,int timeOut) {
		return isDisplayed(driver, newSourceFirmPopUpAddButton, "Visibility", timeOut, "New source Firm Pop Up Add Button");
	}
	
	@FindBy(xpath="//div[contains(@class,'Additional_fields_for_new_Source_Firm')]//button[@title='Cancel']")
	private WebElement newSourceFirmPopUpCancelButton;

	/**
	 * @return the newSourceFirmPopUpCancelButton
	 */
	public WebElement getNewSourceFirmCancelButton(String environment,String mode,int timeOut) {
		return isDisplayed(driver, newSourceFirmPopUpCancelButton, "Visibility", timeOut, "New source Firm Pop Up Cancel Button");
	}
	
	@FindBy(xpath="//div[contains(@class,'New_Source_Contact')]//button[@title='Add']")
	private WebElement newSourceContactPopUpAddButton;

	/**
	 * @return the newSourceContactPopUpAddButton
	 */
	public WebElement getNewSourceContactAddButton(String environment,String mode,int timeOut) {
		return isDisplayed(driver, newSourceContactPopUpAddButton, "Visibility", timeOut, "New source Contact Pop Up Add Button");
	}
	
	@FindBy(xpath="//div[contains(@class,'New_Source_Contact')]//button[@title='Cancel']")
	private WebElement newSourceContactPopUpCancelButton;

	/**
	 * @return the newSourceFirmPopUpCancelButton
	 */
	public WebElement getNewSourceContactCancelButton(String environment,String mode,int timeOut) {
		return isDisplayed(driver, newSourceContactPopUpCancelButton, "Visibility", timeOut, "New source Contact Pop Up Cancel Button");
	}
	
	
	public WebElement dealCreationCancelBtn(TopOrBottom topOrBottom,int timeOut) {
		String xpath="";
		if(topOrBottom.toString().equalsIgnoreCase(TopOrBottom.TOP.toString())) {
			xpath="thePage:theForm:cancelbtn1";
		}else {
			xpath="thePage:theForm:cancelbtn2";
		}
		return isDisplayed(driver, FindElement(driver, "//input[@id='"+xpath+"']", "deal creation cancel button", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "deal creation cancel button");
	}
	
	
	public WebElement dealCreationSourceFirmCancelCrossBtn(CancelOrCross cancelOrcross,int timeOut) {
		String xpath="";
		if(cancelOrcross.toString().equalsIgnoreCase(CancelOrCross.Cancel.toString())) {
			xpath="//div[contains(@class,'Additional_fields_for_new_Source_Firm')]//button[@title='Cancel']";
		}else {
			xpath="//div[contains(@class,'Additional_fields_for_new_Source_Firm')]//a[@title='Close']";
		}
		return isDisplayed(driver, FindElement(driver,xpath, cancelOrcross+" button", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, cancelOrcross+" button");
	}
	
	@FindBy(xpath="//img[@id='Source_Firm']")
	private WebElement sourceFirmPencilIcon;

	/**
	 * @return the sourceFirmPencilIcon
	 */
	public WebElement getSourceFirmPencilIcon(int timeOut) {
		return isDisplayed(driver, sourceFirmPencilIcon, "Visibility", timeOut, "source Firm Pencil Icon");
	}
	
	public WebElement dealCreationSourceContactCancelCrossBtn(CancelOrCross cancelOrcross,int timeOut) {
		String xpath="";
		if(cancelOrcross.toString().equalsIgnoreCase(CancelOrCross.Cancel.toString())) {
			xpath="//div[contains(@class,'New_Source_Contact')]//button[@title='Cancel']";
		}else {
			xpath="//div[contains(@class,'New_Source_Contact')]//a[@title='Close']";
		}
		return isDisplayed(driver, FindElement(driver,xpath, cancelOrcross+" button", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, cancelOrcross+" button");
	}
	
	
	@FindBy(xpath="//img[@id='Source_Contact_Icon']")
	private WebElement sourceContactPencilIcon;

	/**
	 * @return the sourceFirmPencilIcon
	 */
	public WebElement getSourceContactPencilIcon(int timeOut) {
		return isDisplayed(driver, sourceContactPencilIcon, "Visibility", timeOut, "source Firm Pencil Icon");
	}
	
	
	//div[contains(@class,'New_Source_Contact')]//button[@title='Cancel']
	
}
