package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.navatar.generic.EnumConstants.AddProspectsTab;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.action;

public class MarketingInitiativesPage extends BasePageBusinessLayer {

	public MarketingInitiativesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath="(//iframe[@title='accessibility title'])[2]")
	private WebElement marketInitiativeFrame_Lightning;
	
	
	/**
	 * @return the marketInitiativeFrame_Lightning
	 */
	public WebElement getMarketInitiativeFrame_Lightning(int timeOut) {
		return isDisplayed(driver, marketInitiativeFrame_Lightning, "Visibility", timeOut, "market Initiative Frame Lightning");
	}


	/**
	 * @param marketInitiativeFrame_Lightning the marketInitiativeFrame_Lightning to set
	 */
	public void setMarketInitiativeFrame_Lightning(WebElement marketInitiativeFrame_Lightning) {
		this.marketInitiativeFrame_Lightning = marketInitiativeFrame_Lightning;
	}

	@FindBy(xpath="//input[@name='Name']")
	private WebElement marketInitiativeNameTextBox_Classic;
	
	@FindBy(xpath="//label[text()='Name']/following-sibling::div//input")
	private WebElement marketInitiativeNameTextBox_Lighting;
	
	public WebElement getMarketInitiativeNameTextBox(String environment, String mode, int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, marketInitiativeNameTextBox_Classic, "Visibility", timeOut, "market Initiative Name TextBox in Classic");	
		}else{
			return isDisplayed(driver, marketInitiativeNameTextBox_Lighting, "Visibility", timeOut, "market Initiative Name TextBox in Lighting");	
		}
	}
	
	@FindBy(xpath="//td[text()='Name']/../td/div[@id='Name_ileinner']")
	private WebElement marketInitiativeLabelText_Classic;
	
	@FindBy(xpath="//div//h1/div[contains(text(),'Marketing')]/..")
	private WebElement marketInitiativeLabelText_Lighting;
	
	public WebElement getmarketInitiativeLabelLabelText(String environment, String mode, int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, marketInitiativeLabelText_Classic, "Visibility", timeOut, "market Initiative label in Classic");	
		}else{
			return isDisplayed(driver, marketInitiativeLabelText_Lighting, "Visibility", timeOut, "market Initiative label in Lighting");	
		}
	}
	

	@FindBy(xpath="//p[@class='slds-page-header__title slds-truncate slds-align-middle']")
	private WebElement addProspectsHeaderText;
	
	
	/**
	 * @return the addProspectsHeaderText
	 */
	public WebElement getAddProspectsHeaderText(int timeOut) {
		return isDisplayed(driver, addProspectsHeaderText, "Visibility", timeOut, "Add Prospects Header Text");
	}

	
	public WebElement getAddProspectFieldAutoCompleteTextBox(AddProspectsTab addProspectsTab, int timeOut) {
		String Xpath="";
		WebElement ele=null;
		if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString())) {
			Xpath="dealinputleftcolmn1";
		}else {
			Xpath="inputrytcolmn1";
		}
		return isDisplayed(driver,FindElement(driver, "//input[@name='"+Xpath+"']", "add Prospect Field Auto Complete TextBox", action.BOOLEAN,timeOut), "Visibility", timeOut, "add Prospect Field Auto Complete TextBox");
	}
	
	
	public WebElement getAddProspectOperatorDropDownList(AddProspectsTab addProspectsTab, int timeOut) {
		String Xpath="";
		WebElement ele=null;
		if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString())) {
			Xpath="opt1";
		}else {
			Xpath="optb1";
		}
		return isDisplayed(driver,FindElement(driver, "//select[@id='"+Xpath+"']", "Add Prospect Operator DropDownList", action.BOOLEAN,timeOut), "Visibility", timeOut, "Add Prospect Operator DropDownList");
	}
	
	
	
	public WebElement getAddProspectValueTextBox(AddProspectsTab addProspectsTab, int timeOut) {
		String Xpath="";
		WebElement ele=null;
		if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString())) {
			Xpath="criteriatextBox1";
		}else {
			Xpath="criteriatextBoxMP1";
		}
		return isDisplayed(driver,FindElement(driver, "//input[@id='"+Xpath+"']", "add Prospect Value TextBox", action.BOOLEAN,timeOut), "Visibility", timeOut, "add Prospect Value TextBox");
	}
	
	
	
	@FindBy(xpath="//span[@title='Add Row']/a")
	private WebElement addProspectAddRowLink;

	/**
	 * @return the addProspectAddRowLink
	 */
	public WebElement getAddProspectAddRowLink(int timeOut) {
		return isDisplayed(driver, addProspectAddRowLink, "Visibility", timeOut, "add Prospect Add RowLink");
	}
	
	@FindBy(xpath="(//span[@id='addprospectid:add_prspct_frm:addfilter']/span/a)[1]")
	private WebElement addProspectAddFilterLoginBtn;

	/**
	 * @return the addProspectAddFilterLoginBtn
	 */
	public WebElement getAddProspectAddFilterLoginBtn(int timeOut) {
		return isDisplayed(driver, addProspectAddFilterLoginBtn, "Visibility", timeOut, "add Prospect Add Filter Login Btn");
	}
	
	@FindBy(xpath="//input[@value='Clear'][contains(@onclick,'ClearSearch()')]")
	private WebElement addProspectClearBtn;

	/**
	 * @return the addProspectClearBtn
	 */
	public WebElement getAddProspectClearBtn(int timeOut) {
		return isDisplayed(driver, addProspectClearBtn, "Visibility", timeOut, "add Prospect Clear Btn");
	}
	
	
	public WebElement getAddProspectSearchBtn(AddProspectsTab addProspectsTab, int timeOut) {
		String Xpath="";
		WebElement ele=null;
		if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString())) {
			Xpath="addprospectid:add_prspct_frm:Advancefilterapply";
		}else {
			Xpath="addprospectid:add_prspct_frm:AdvancefilterapplyMP";
		}
		return isDisplayed(driver,FindElement(driver, "//input[@id='"+Xpath+"']", "add Prospect Search Btn", action.BOOLEAN,timeOut), "Visibility", timeOut, "add Prospect Search Btn");
	}
	
	
	
	
	@FindBy(xpath="//input[@value='Add to Prospect List']")
	private WebElement addProspectsListBtn;

	/**
	 * @return the addProspectListBtn
	 */
	public WebElement getAddToProspectListBtn(int timeOut) {
		return isDisplayed(driver, addProspectsListBtn, "Visibility", timeOut, "add prospects list button");
	}
	
	@FindBy(xpath="//span[@id='Select_from_Search_ResultsA-scroll-box']")
	private WebElement selectProspectsGridScrollBox;

	@FindBy(xpath="//span[@id='Select_from_Search_ResultsArep-scroll-box']")
	private WebElement selectProspectGridScrollBox_ReportsTab;
	
	/**
	 * @return the selectProspectsGridScrollBox
	 */
	public WebElement getSelectProspectsGridScrollBox(AddProspectsTab addProspectsTab,int timeOut) {
		if(addProspectsTab.toString().equalsIgnoreCase(addProspectsTab.AccountAndContacts.toString()) || addProspectsTab.toString().equalsIgnoreCase(addProspectsTab.PastMarketingInitiatives.toString())) {

			return isDisplayed(driver, selectProspectsGridScrollBox, "Visibility", timeOut, "select prospects scroll box grid");
			
		}else {
			return isDisplayed(driver, selectProspectGridScrollBox_ReportsTab, "Visibility", timeOut, "select prospects scroll box grid");
		}
	}  
	
	@FindBy(xpath="//input[@title='Add Prospects to Marketing Initiative']")
	private WebElement addProspectMarketingInitiativeBtn;

	/**
	 * @return the addProspectMarketingInitiativeBtn
	 */
	public WebElement getAddProspectMarketingInitiativeBtn(int timeOut) {
		return isDisplayed(driver, addProspectMarketingInitiativeBtn, "Visibility", timeOut, "add Prospect Marketing Initiative button");
	}
	
	@FindBy(xpath="//div[@id='hideFilterText_SearchProspects']//div/img")
	private WebElement addProspectsHideSearchPopUp;

	/**
	 * @return the addProspectsHideSearchPopUp
	 */
	public WebElement getAddProspectsHideSearchPopUp(int timeOut) {
		return isDisplayed(driver, addProspectsHideSearchPopUp, "Visibility", timeOut, "add Prospects Hide Search PopUp");
	}
	
	@FindBy(xpath="//div[@id='displayFilterText_SearchProspects']//img")
	private WebElement addProspectsDisplaySearchPopUp;

	/**
	 * @return the addProspectsDisplaySearchPopUp
	 */
	public WebElement getAddProspectsDisplaySearchPopUp(int timeOut) {
		return isDisplayed(driver, addProspectsDisplaySearchPopUp, "Visibility", timeOut, "add Prospects Display Search PopUp");
	}
	
	@FindBy(xpath="//li[@title='Past Marketing Initiatives']/a")
	private WebElement pastMarketingInitiativeTab;

	/**
	 * @return the pastMarketingInitiativeTab
	 */
	public WebElement getPastMarketingInitiativeTab(int timeOut) {
		return isDisplayed(driver, pastMarketingInitiativeTab, "Visibility", timeOut, "past prospects initiative tab");
	}
	
	@FindBy(xpath="//li[@title='Accounts and Contacts']/a")
	private WebElement AccountAndContactTab;

	/**
	 * @return the accountAndContactTab
	 */
	public WebElement getAccountAndContactTab(int timeOut) {
		return isDisplayed(driver, AccountAndContactTab, "Visibility", timeOut, "account anfd contact tab");
	}
	
	@FindBy(xpath="//li[@title='Report']/a")
	private WebElement ReportTab;

	/**
	 * @return the reportTab
	 */
	public WebElement getReportTab(int timeOut) {
		return isDisplayed(driver, ReportTab, "Visibility", timeOut, "report tab");
	}
	
	@FindBy(xpath="//span[@id='recrds']")
	private WebElement selectFromSearchResultsRecord;

	/**
	 * @return the addProspectsrecords
	 */
	public WebElement getSelectFromSearchResultsRecord(int timeOut) {
		return isDisplayed(driver, selectFromSearchResultsRecord, "Visibility", timeOut, "Add prospects records");
	}
	
	@FindBy(xpath="//span[@id='secondgridrecrds']")
	private WebElement  reviewProspectListRecord;

	/**
	 * @return the reviewProspectListRecord
	 */
	public WebElement getReviewProspectListRecord(int timeOut) {
		return isDisplayed(driver, reviewProspectListRecord, "Visibility", timeOut, "review porspect list record");
	}
	
	@FindBy(xpath="//input[@title='Load Report']")
	private WebElement loadReportsBtn;

	/**
	 * @return the loadReportsBtn
	 */
	public WebElement getLoadReportsBtn(int timeOut) {
		return isDisplayed(driver, loadReportsBtn, "Visibility", timeOut, "load report button");
	}
	
	@FindBy(xpath="//div[contains(@class,'reportErrors_PopUp FancyboxContainer Popup')]//div[@class='PopupContentStart']")
	private WebElement selectAReportsErrorMsg;

	/**
	 * @return the selectAReportsErrorMsg
	 */
	public WebElement getSelectAReportsErrorMsg(int timeOut) {
		return isDisplayed(driver, selectAReportsErrorMsg, "Visibility", timeOut, "select a report error message");
	}
	
	@FindBy(xpath="//div[contains(@class,'reportErrors_PopUp FancyboxContainer')]//input[@title='OK']")
	private WebElement selectAReportErrorMsgOKBtn;

	/**
	 * @return the selectAReportErrorMsgOKBtn
	 */
	public WebElement getSelectAReportErrorMsgOKBtn(int timeOut) {
		return isDisplayed(driver, selectAReportErrorMsgOKBtn, "Visibility", timeOut, "select a reports error message ok button");
	}
	
	@FindBy(xpath="//img[@onclick='SelectReport_Fancybox(); return false;']")
	private WebElement selectAReportLookUpIcon;

	/**
	 * @return the selectAReportLookUpIcon
	 */
	public WebElement getSelectAReportLookUpIcon(int timeOut) {
		return isDisplayed(driver, selectAReportLookUpIcon, "Visibility", timeOut, "select a report look up icon");
	}
	
	public WebElement getSelectAReportPopUpFolderName(String folderName, int time) {
		String Xpath="//span[@id='BulkDownload_Tree']//li/div/a/span[text()='"+folderName+"']";
		return isDisplayed(driver, FindElement(driver, Xpath, "select a report pop up folder Name:  "+folderName, action.SCROLLANDBOOLEAN, time), "visibility", 20, "select a report pop up folder Name:  "+folderName);
		
	}
	
	public WebElement getSelectAReportPopUpFileName(String folderName,String fileName, int time) {
		String Xpath="//span[@id='BulkDownload_Tree']//li/div/a/span[text()='"+folderName+"']//ancestor::div/following-sibling::ul/li//a/span[text()='"+fileName+"']";
		return isDisplayed(driver, FindElement(driver, Xpath, "select a report pop up folder Name:  "+folderName, action.SCROLLANDBOOLEAN, time), "visibility", 20, "select a report pop up folder Name:  "+folderName);
		
	}
	
	@FindBy(xpath="//div[contains(@class,'SelectReport_Fancybox')]//input[@placeholder='Search']")
	private WebElement selectAReportSearchTextBox;

	/**
	 * @return the selectAReportSearchTextBox
	 */
	public WebElement getSelectAReportSearchTextBox(int timeOut) {
		return isDisplayed(driver, selectAReportSearchTextBox, "Visibility", timeOut, "select a reports search text box");
	}
	
	@FindBy(xpath="//div[contains(@class,'SelectReport_Fancybox')]//button")
	private WebElement selectAReportPopUpOKBtn;

	/**
	 * @return the selectAReportPopUpOKBtn
	 */
	public WebElement getSelectAReportPopUpOKBtn(int timeOut) {
		return isDisplayed(driver, selectAReportPopUpOKBtn, "Visibility", timeOut, "select a reports popup ok btn");
	}
	
	@FindBy(xpath="//div[contains(@class,'ContactIdmissing_popup')]//center")
	private WebElement contactIDMissingErrorMsgInReportTab;

	/**
	 * @return the contactIDMissingErrorMsgInReportTab
	 */
	public WebElement getContactIDMissingErrorMsgInReportTab(int timeOut) {
		return isDisplayed(driver, contactIDMissingErrorMsgInReportTab, "Visibility", timeOut, "contact ID Missing Error Msg In Report Tab");
	}
	
	@FindBy(xpath="//div[contains(@class,'ContactIdmissing_popup')]//button")
	private WebElement contactIDMissingErrorMsgPopUpOKBtnInReportTab;

	/**
	 * @return the contactIDMissingErrorMsgPopUpOKBtnInReportTab
	 */
	public WebElement getContactIDMissingErrorMsgPopUpOKBtnInReportTab(int timeOut) {
		return isDisplayed(driver, contactIDMissingErrorMsgPopUpOKBtnInReportTab, "Visibility", timeOut, "contact ID Missing Error Msg Pop Up OK Btn In ReportTab");
	}
	
	@FindBy(xpath="//input[@id='addprospectid:add_prspct_frm:removemiField']")
	private WebElement ExcludeAnyAccountsIncludedInThisMITextBox;

	/**
	 * @return the excludeAnyAccountsIncludedInThisMITextBox
	 */
	public WebElement getExcludeAnyAccountsIncludedInThisMITextBox(int timeOut) {
		return isDisplayed(driver, ExcludeAnyAccountsIncludedInThisMITextBox, "Visibility", timeOut, "Exclude Any Accounts Included In This MI TextBox");
	}
	
	@FindBy(xpath="//input[@id='addprospectid:add_prspct_frm:removeID']")
	private WebElement ExcludeAnyAccountsIncludedInThisMIRemoveBtn;

	/**
	 * @return the excludeAnyAccountsIncludedInThisMIRemoveBtn
	 */
	public WebElement getExcludeAnyAccountsIncludedInThisMIRemoveBtn(int timeOut) {
		return isDisplayed(driver, ExcludeAnyAccountsIncludedInThisMIRemoveBtn, "Visibility", timeOut, "Exclude Any Accounts Included In This MI Remove button");
	}
	
	@FindBy(xpath="//img[@title='Marketing Initiative Lookup (New Window)']")
	private WebElement ExcludeAnyAccountsIncludedInThisMILookUpIcon;
	
	
	/**
	 * @return the excludeAnyAccountsIncludedInThisMILookUpIcon
	 */
	public WebElement getExcludeAnyAccountsIncludedInThisMILookUpIcon(int timeOut) {
		return isDisplayed(driver, ExcludeAnyAccountsIncludedInThisMILookUpIcon, "Visibility", timeOut, "Exclude Any Accounts Included In This MI LookUp Icon");
	}

	@FindBy(xpath="//div[contains(@class,'removesuccessfull')]//input")
	private WebElement reomveContactConfirmationPopUpOkBtn;

	/**
	 * @return the reomveContactConfirmationPopUpOkBtn
	 */
	public WebElement getReomveContactConfirmationPopUpOkBtn(int timeOut) {
		return isDisplayed(driver, reomveContactConfirmationPopUpOkBtn, "Visibility", timeOut, "reomve Contact Confirmation PopUp Ok Btn");
	}
	
	@FindBy(xpath="//div[contains(@class,'removesuccessfull')]//center")
	private WebElement reomveContactConfirmationPopUpErrorMsg;

	/**
	 * @return the reomveContactConfirmationPopUpErrorMsg
	 */
	public WebElement getReomveContactConfirmationPopUpErrorMsg(int timeOut) {
		return isDisplayed(driver, reomveContactConfirmationPopUpErrorMsg, "Visibility", timeOut, "reomve Contact Confirmation PopUp Error Msg");
	}
	
	
	
	@FindBy(xpath="//input[@id='a1aa']")
	private WebElement emailProspectFieldTextBox;

	/**
	 * @return the emailProspectFieldTextBox
	 */
	public WebElement getEmailProspectFieldTextBox(int timeOut) {
		return isDisplayed(driver, emailProspectFieldTextBox, "Visibility", timeOut, "email prospects fields text box");
	}
	
	@FindBy(xpath="//select[@id='opt1']")
	private WebElement emailProspectOperatorDropDownList;

	/**
	 * @return the emailProspectOperatorDropDownList
	 */
	public WebElement getEmailProspectOperatorDropDownList(int timeOut) {
		return isDisplayed(driver, emailProspectOperatorDropDownList, "Visibility", timeOut, "email prospects operator drop downlist");
	}
	
	@FindBy(xpath="//input[@id='criteriatextbox1']")
	private WebElement emailProspectValueTextBox;

	/**
	 * @return the emailProspectValueTextBox
	 */
	public WebElement getEmailProspectValueTextBox(int timeOut) {
		return isDisplayed(driver, emailProspectValueTextBox, "Visibility", timeOut, "email prospect value text box");
	}
	
	@FindBy(xpath="//div[@id='filterGridContactDiv1']//a[@title='Apply'][contains(text(),'Apply')]")
	private WebElement emailProspectApplyBtn;

	/**
	 * @return the emailProspectApplyBtn
	 */
	public WebElement getEmailProspectApplyBtn(int timeOut) {
		return isDisplayed(driver, emailProspectApplyBtn, "Visibility", timeOut, "email prospect apply button");
	}
	
	@FindBy(xpath="//span[@class='aw-bars-box ']")
	private WebElement emailProspectSelectProspectsGridScrollBox;

	/**
	 * @return the emailProspectSelectProspectsGridScrollBox
	 */
	public WebElement getEmailProspectSelectProspectsGridScrollBox(int timeOut) {
		return isDisplayed(driver, emailProspectSelectProspectsGridScrollBox, "Visibility", timeOut, "email prospect select prospect grid scroll box");
	}
	
	@FindBy(xpath="//input[@id='searchcon_grid']")
	private WebElement emailProspectSearchTextBox;

	/**
	 * @return the emailProspectSearchTextBox
	 */
	public WebElement getEmailProspectSearchTextBox(int timeOut) {
		return isDisplayed(driver, emailProspectSearchTextBox, "Visibility", timeOut, "email prospect search text box");
	}
	
	@FindBy(xpath="//span[@id='clearsearchdsb']/div/img")
	private WebElement emailProspectSearchTextBoxCrossIcon;

	/**
	 * @return the emailProspectSearchTextBoxCrossIcon
	 */
	public WebElement getEmailProspectSearchTextBoxCrossIcon(int timeOut) {
		return isDisplayed(driver, emailProspectSearchTextBoxCrossIcon, "Visibility", timeOut, "email prospect search text box cross icon");
	}
	
	@FindBy(xpath="//label[@id='total_records']")
	private WebElement emailProspectSelctContactRecordCount;

	/**
	 * @return the emailProspectSelctContactRecordCount
	 */
	public WebElement getEmailProspectSelctContactRecordCount(int timeOut) {
		return isDisplayed(driver, emailProspectSelctContactRecordCount, "Visibility", timeOut, "email prospect select contact record count");
	}
	
	@FindBy(xpath="//select[contains(@id,'page') and contains(@id,'frm') and contains(@id,'sl1')]")
	private WebElement emailProspectFolderDropDownList;

	/**
	 * @return the emailProspectFolderDropDownList
	 */
	public WebElement getEmailProspectFolderDropDownList(int timeOut) {
		return isDisplayed(driver, emailProspectFolderDropDownList, "Visibility", timeOut, "email prospect folder drop downlist");
	}
	
	@FindBy(xpath="//div[@class='step_2']//a[@title='Next']")
	private WebElement emailProspectStep2NextBtn;

	/**
	 * @return the emailProspectStep2NextBtn
	 */
	public WebElement getEmailProspectStep2NextBtn(int timeOut) {
		return isDisplayed(driver, emailProspectStep2NextBtn, "Visibility", timeOut, "email prospect steps 2 nect button");
	}
	
	@FindBy(xpath="(//div[@class='step_1']//a[@title='Next'])[2]")
	private WebElement emailProspectStep1NextBtn;

	/**
	 * @return the emailProspectStep1NextBtn
	 */
	public WebElement getEmailProspectStep1NextBtn(int timeOut) {
		return isDisplayed(driver, emailProspectStep1NextBtn, "Visibility", timeOut, "email prospect steps 1 next button");
	}
	
	@FindBy(xpath="(//span[@class='aw-bars-box '])[2]")
	private WebElement emailProspectStep2CustomEmailtemplateScrollBox;

	/**
	 * @return the emailProspectStep2CustomEmailtemplateScrollBox
	 */
	public WebElement getEmailProspectStep2CustomEmailtemplateScrollBox(int timeOut) {
		return isDisplayed(driver, emailProspectStep2CustomEmailtemplateScrollBox, "Visibility", timeOut, "email prospect custom email template scroll box");
	}
	
	public WebElement emailProspectPreviewTemplateLink(String templateName, int timeOut) {
		String xpath="//span[text()='"+templateName+"']/following-sibling::span/a";
		return  isDisplayed(driver, FindElement(driver,xpath, "", action.SCROLLANDBOOLEAN,20), "visibility",20,templateName+" preview link");
	}
	
	@FindBy(xpath="//div[@class='step_3']/div[2]/span")
	private WebElement emailProspectsSelectedRecipientErrorMsg;
	
	@FindBy(xpath="//div[@class='step_3']/div[3]/span")
	private WebElement emailFundraisingContactSelectedRecipientErrorMsg;
	
	/**
	 * @return the emailProspectsSelectedRecipientErrorMsg
	 */
	public WebElement getEmailProspectsSelectedRecipientErrorMsg(PageName pageName,int timeOut) {
		if(pageName.toString().equalsIgnoreCase(PageName.emailFundraisingContact.toString())) {
			return isDisplayed(driver, emailFundraisingContactSelectedRecipientErrorMsg, "Visibility", timeOut, "email prospect step 3 selected Recipient error message");
		}else {
			return isDisplayed(driver, emailProspectsSelectedRecipientErrorMsg, "Visibility", timeOut, "email prospect step 3 selected Recipient error message");
		}
	}
	
	public List<WebElement> getEmailProspectProcessingOptionsLableTextList(){
		return FindElements(driver, "//div[@class='step_3']/div[2]/table[1]//tr/td[1]", "email prospect step 3 processing options lable list");
	}
	
	public List<WebElement> getEmailProspectProcessingOptionsCheckBoxList(){
		return FindElements(driver, "//div[@class='step_3']/div[2]/table[1]//tr/td[2]//input", "email prospect step 3 processing options check box list");
	}
	
/*	@FindBy(xpath="(//div[@class='step_3']//a[@title='Send'])[2]")
	private WebElement emailProspectSendBtn;*/

	/**
	 * @param topOrBottom TODO
	 * @return the emailProspectSendBtn
	 */
	public WebElement getEmailProspectSendBtn(TopOrBottom topOrBottom, int timeOut) {
		
		
		WebElement ele=null;
		String xpath=null;
			
			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//div[@class='step_3']//a[@title='Send'])[1]";
			} else {
				xpath = "(//div[@class='step_3']//a[@title='Send'])[2]";
			}
			
		
		ele = FindElement(driver, xpath, "Send Button : "+topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "send button : "+topOrBottom);
	}
	
	@FindBy(xpath="//div[@class='step_4']//p[1]")
	private WebElement emailProspectSendEmailCongratulationsErrorMsg;

	/**
	 * @return the emailProspectSendEmailCongratulationsErrorMsg
	 */
	public WebElement getEmailProspectSendEmailCongratulationsErrorMsg(int timeOut) {
		return isDisplayed(driver, emailProspectSendEmailCongratulationsErrorMsg, "Visibility", timeOut, "email Prospect Send Email Congratulations Error Msg");
	}
	
	@FindBy(xpath="//a[text()='Finished']")
	private WebElement emailProspectFinishBtn;

	/**
	 * @return the emailProspectFinishBtn
	 */
	public WebElement getEmailProspectFinishBtn(int timeOut) {
		return isDisplayed(driver, emailProspectFinishBtn, "Visibility", timeOut, "email prospect finish button");
	}
	

	/**
	 * @return the emailProspectContactNameColumn
	 */
	public WebElement getEmailProspectContactNameColumn(String headerName,int timeOut) {
		String xpath="//span[text()='"+headerName+"']";
		return  isDisplayed(driver, FindElement(driver,xpath, "", action.BOOLEAN,20), "visibility",20,"email prospect contact grid "+headerName+" header name");
	}
	
	
	@FindBy(xpath="//div[@id='displayFilterText1']//div/img")
	private WebElement emailProspectDisplaySearchPopUp;

	/**
	 * @return the emailProspectDisplaySearchPopUp
	 */
	public WebElement getEmailProspectDisplaySearchPopUp(int timeOut) {
		return isDisplayed(driver, emailProspectDisplaySearchPopUp, "Visibility", timeOut, "email prospect display search pop up");
	}
	
	@FindBy(xpath="//div[@id='hideFilterText1']//div/img")
	private WebElement emailProspectHideSearchPopUp;

	/**
	 * @return the emailProspectHideSearchPopUp
	 */
	public WebElement getEmailProspectHideSearchPopUp(int timeOut) {
		return isDisplayed(driver, emailProspectHideSearchPopUp, "Visibility", timeOut, "email prospect hide search pop up");
	}
	
	
	@FindBy(xpath="(//iframe[@title='accessibility title'])[4]")
	private WebElement BulkEmail2ndStepFrame_Lightning;
	
	
	/**
	 * @return the BulkEmail2ndStepFrame_Lightning
	 */
	public WebElement getBulkEmail2ndStepFrame_Lightning(int timeOut) {
		return isDisplayed(driver, BulkEmail2ndStepFrame_Lightning, "Visibility", timeOut, "Bulk Email 2ndStepFrame Lightning");
	}
	
	//div[@class='reportErr_PopUpremv FancyboxContainer Popup ui-draggable ui-draggable-handle']//center
	@FindBy(xpath="//div[@class='reportErr_PopUpremv FancyboxContainer Popup ui-draggable ui-draggable-handle']//center")
	private WebElement selectMIErrorMessage;


	/**
	 * @return the selectMIErrorMessage
	 */
	public WebElement getSelectMIErrorMessage(int timeOut) {
		return isDisplayed(driver, selectMIErrorMessage, "Visibility", timeOut, "Select MI Error Message");
	}
	
	@FindBy(xpath="//div[@class='reportErr_PopUpremv FancyboxContainer Popup ui-draggable ui-draggable-handle']//input")
	private WebElement selectMIErrorMessageOKButton;


	/**
	 * @return the selectMIErrorMessageOKButton
	 */
	public WebElement getSelectMIErrorMessageOKButton(int timeOut) {
		return isDisplayed(driver, selectMIErrorMessageOKButton, "Visibility", timeOut, "Ok Button");
	}
	
	@FindBy(xpath="//input[@id='addprospectid:add_prspct_frm:report_name']")
	private WebElement selectReportSearchBox;

	/**
	 * @return the selectAReportSearchTextBox
	 */
	public WebElement getSelectReportSearchBox(int timeOut) {
		return isDisplayed(driver, selectReportSearchBox, "Visibility", timeOut, "select a reports search text box");
	}

	@FindBy(xpath="//div[contains(@class,'reportErrors_PopUp_error FancyboxContainer')]//div[@class='PopupContentStart']")
	private WebElement reportDoesNotExistMsg;

	/**
	 * @return the selectAReportsErrorMsg
	 */
	public WebElement getReportDoesNotExistMsg(int timeOut) {
		return isDisplayed(driver, reportDoesNotExistMsg, "Visibility", timeOut, "Report Does not Exist");
	}
	
	@FindBy(xpath="//div[contains(@class,'reportErrors_PopUp_error FancyboxContainer')]//input[@title='OK']")
	private WebElement reportDoesNotExistMsgOKBtn;

	/**
	 * @return the selectAReportErrorMsgOKBtn
	 */
	public WebElement getReportDoesNotExistMsgOKBtn(int timeOut) {
		return isDisplayed(driver, reportDoesNotExistMsgOKBtn, "Visibility", timeOut, "Report Does not Exist PopUp ok button");
	}

	
	@FindBy(xpath="//img[@onclick='reportsearch();']")
	private WebElement selectAReportSearchLookUpIcon;

	/**
	 * @return the selectAReportLookUpIcon
	 */
	public WebElement getSelectAReportSearchLookUpIcon(int timeOut) {
		return isDisplayed(driver, selectAReportSearchLookUpIcon, "Visibility", timeOut, "select a report Search look up icon");
	}

	@FindBy(xpath="//div[contains(@class,'reportsearcherror_error')]//div/p")
	private WebElement pleaseEnterAValuetMsg;

	/**
	 * @return the selectAReportsErrorMsg
	 */
	public WebElement getPleaseEnterAValuetMsg(int timeOut) {
		return isDisplayed(driver, pleaseEnterAValuetMsg, "Visibility", timeOut, "Please Enter a value Error Msg");
	}
	
	@FindBy(xpath="//div[contains(@class,'reportsearcherror_error')]//a[@title='OK']")
	private WebElement pleaseEnterAValueOKBtn;

	/**
	 * @return the selectAReportErrorMsgOKBtn
	 */
	public WebElement getPleaseEnterAValueOKBtnOKBtn(int timeOut) {
		return isDisplayed(driver, pleaseEnterAValueOKBtn, "Visibility", timeOut, "please Enter A Value ok button");
	}
	
	@FindBy(xpath="//img[@onclick='Clear_Reporttree();']")
	private WebElement crossImgSelectAReportPopUp;

	/**
	 * @return the crossImgSelectAReportPopUp
	 */
	public WebElement getCrossImgSelectAReportPopUp(int timeOut) {
		return isDisplayed(driver, crossImgSelectAReportPopUp, "Visibility", timeOut, "cross Img Select A Report PopUp");
	}
	
	
	/**
	 * @return the selectAReportErrorMsgOKBtn
	 */
	public WebElement getSelectAReportPopUpCrossIcon(int timeOut) {
		String xpath ="//a[@id='submitButtonId']";
		return FindElement(driver, xpath,"Select A Report PopUp Cross Icon",action.BOOLEAN,timeOut);
	}
	

	@FindBy(xpath="//input[@id='SearchContent4']")
	private WebElement searchForAContactTextBoxOnReportTab;

	/**
	 * @return the searchForAContactTextBox
	 */
	public WebElement getSearchForAContactTextBoxOnReportTab(int timeOut) {
		return isDisplayed(driver, searchForAContactTextBoxOnReportTab, "Visibility", timeOut, "Search For A Contact Text Box On ReportTab");
	}
	
	@FindBy(xpath="//input[@id='SearchContent4']/preceding-sibling::div/img")
	private WebElement searchIconForAContactOnReportTab;

	/**
	 * @return the searchForAContactTextBox
	 */
	public WebElement getSearchIconForAContactOnReportTab(int timeOut) {
		return isDisplayed(driver, searchIconForAContactOnReportTab, "Visibility", timeOut, "search Icon ForA Contact On ReportTab");
	}
	

	@FindBy(xpath="//span[@id='ClearSearch4']//img")
	private WebElement clearIconForAContactOnReportTab;

	/**
	 * @return the searchForAContactTextBox
	 */
	public WebElement getClearIconForAContactOnReportTab(int timeOut) {
		return isDisplayed(driver, clearIconForAContactOnReportTab, "Visibility", timeOut, "Clear Icon ForA Contact On ReportTab");
	}
	
	@FindBy(xpath="(//div[@id='filterGridContactDiv']//center/input[@value='Search'])[1]")
	private WebElement searchBtnOnAddProspect;

	/**
	 * @return the searchForAContactTextBox
	 */
	public WebElement getSearchBtnOnAddProspect(int timeOut) {
		return isDisplayed(driver, searchBtnOnAddProspect, "Visibility", timeOut, "Search Button on Add Prospect");
	}
	
	@FindBy(xpath="//div[contains(@class,'BlankCritriaErr_PopUp ')]//div[@class='PopupContentStart']")
	private WebElement pleaseSelectASearchCriteriaMsg;

	/**
	 * @return the selectAReportsErrorMsg
	 */
	public WebElement getPleaseSelectASearchCriteriaMsg(int timeOut) {
		return isDisplayed(driver, pleaseSelectASearchCriteriaMsg, "Visibility", timeOut, "please Select A Search Criteria Msg");
	}

	
	public WebElement getPleaseSelectASearchCriteriaPopUpOKBtn(PageName pageName, int timeOut) {
		String Xpath="";
		WebElement ele=null;
		if(pageName.toString().equalsIgnoreCase(PageName.CreateFundraisingPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.MarketingInitiatives.toString())) {
			Xpath="//div[contains(@class,'BlankCritriaErr_PopUp FancyboxContainer ')]//input[@title='OK']";
		}else {
			Xpath="//div[contains(@class,'BlankCritriaErr_PopUp FancyboxContainer ')]//button[text()='OK']";
		}
		return isDisplayed(driver,FindElement(driver, Xpath, "please Select A Search Criteria PopUp ok button", action.BOOLEAN,timeOut), "Visibility", timeOut, "please Select A Search Criteria PopUp ok button");
	}
	
	
	
	public List<WebElement> getRowRemoveIcon(){
		return FindElements(driver, "//a[@title='Remove']", "remove icon");
	}
	
	@FindBy(xpath="//div[@id='addFilterlogic']//a[@class='helpLink']")
	private WebElement infoLink;
	
	/**
	 * @return the infoLink
	 */
	public WebElement getInfoLink(int timeOut) {
		return isDisplayed(driver, infoLink, "Visibility", timeOut, "Info Link");
	}
	
	
	
	
	public WebElement getAddProspectClearBtn(AddProspectsTab addProspectsTab, int timeOut) {
		String Xpath="";
		WebElement ele=null;
		if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString())) {
			Xpath="addprospectid:add_prspct_frm:Advancefilterapply";
		}else {
			Xpath="addprospectid:add_prspct_frm:AdvancefilterapplyMP";
		}
		return isDisplayed(driver,FindElement(driver, "//input[@id='"+Xpath+"']"+"/preceding-sibling::input", "add Prospect Clear Btn", action.BOOLEAN,timeOut), "Visibility", timeOut, "add Prospect Search Btn");
	}
	
	
	/**
	 * @return the infoLink
	 */
	public WebElement getWrenchIconCancelBtn(int timeOut) {
		String xpath="//button[@title='Cancel']";
		
		return FindElement(driver, xpath,  "Wrench Icon Cancel Btn",action.BOOLEAN,timeOut);
	}
	
	
	@FindBy(xpath="(//div[@class='step_1']//a[@title='Cancel'])[1]")
	private WebElement emailProspectStep1CancelBtn;

	/**
	 * @return the emailProspectStep1NextBtn
	 */
	public WebElement getEmailProspectStep1CancelBtn(int timeOut) {
		return isDisplayed(driver, emailProspectStep1CancelBtn, "Visibility", timeOut, "email prospect steps 1 Cancel button");
	}
	
	@FindBy(xpath="//div[@class='step_2']//a[@title='Previous']")
	private WebElement emailProspectStep2PreviousBtn;

	/**
	 * @return the emailProspectStep2NextBtn
	 */
	public WebElement getEmailProspectStep2PreviousBtn(int timeOut) {
		return isDisplayed(driver, emailProspectStep2PreviousBtn, "Visibility", timeOut, "email prospect steps 2 PREVIOUS button");
	}
	
	@FindBy(xpath="(//div[@class='step_1']//a[@title='Next'])[2]")
	private WebElement emailProspectStep1NextBottomBtn;

	/**
	 * @return the emailProspectStep1NextBtn
	 */
	public WebElement getEmailProspectStep1NextBottomBtn(int timeOut) {
		return isDisplayed(driver, emailProspectStep1NextBottomBtn, "Visibility", timeOut, "email prospect steps 1 next Bottom button");
	}
	
	@FindBy(xpath="(//div[@class='step_2']//a[@title='Previous'])[2]")
	private WebElement emailProspectStep2PreviousBottomBtn;

	/**
	 * @return the emailProspectStep2NextBtn
	 */
	public WebElement getEmailProspectStep2PreviousBottomBtn(int timeOut) {
		return isDisplayed(driver, emailProspectStep2PreviousBottomBtn, "Visibility", timeOut, "email prospect steps 2 PREVIOUS Bottom button");
	}
	
	@FindBy(xpath="(//div[@class='step_2']//a[@title='Next'])[2]")
	private WebElement emailProspectStep2NextBottomBtn;

	/**
	 * @return the emailProspectStep2NextBtn
	 */
	public WebElement getEmailProspectStep2NextBottomBtn(int timeOut) {
		return isDisplayed(driver, emailProspectStep2NextBottomBtn, "Visibility", timeOut, "email prospect steps 2 next Bottom button");
	}
	
	@FindBy(xpath="(//div[@class='step_3']//a[@title='Previous'])[2]")
	private WebElement emailProspectStep3PreviousBottomBtn;

	/**
	 * @return the emailProspectStep2NextBtn
	 */
	public WebElement getEmailProspectStep3PreviousBottomBtn(int timeOut) {
		return isDisplayed(driver, emailProspectStep3PreviousBottomBtn, "Visibility", timeOut, "email prospect steps 3 PREVIOUS Bottom button");
	}
	
	@FindBy(xpath="//div[@class='step_3']//a[@title='Previous']")
	private WebElement emailProspectStep3PreviousBtn;

	/**
	 * @return the emailProspectStep2NextBtn
	 */
	public WebElement getEmailProspectStep3PreviousBtn(int timeOut) {
		return isDisplayed(driver, emailProspectStep3PreviousBtn, "Visibility", timeOut, "email prospect steps 3 PREVIOUS button");
	}
	
	@FindBy(xpath="//div[contains(@class,'ErrorCriteria_Fancybox ')]//div[@class='PopupContentStart']")
	private WebElement pleaseSelectApplyCriteriaPopUpMsg;

	/**
	 * @return the selectAReportsErrorMsg
	 */
	public WebElement getPleaseSelectApplyCriteriaPopUpMsg(int timeOut) {
		return isDisplayed(driver, pleaseSelectApplyCriteriaPopUpMsg, "Visibility", timeOut, "please Select A Apply Criteria PopUp Msg");
	}
	
	@FindBy(xpath="//div[contains(@class,'ErrorCriteria_Fancybox ')]//div/input[@title='OK']")
	private WebElement pleaseSelectApplyCriteriaPopUpOkBtn;

	/**
	 * @return the selectAReportsErrorMsg
	 */
	public WebElement getPleaseSelectApplyCriteriaPopUpOkBtn(int timeOut) {
		return isDisplayed(driver, pleaseSelectApplyCriteriaPopUpOkBtn, "Visibility", timeOut, "please Select A Apply Criteria PopUp Ok Btn");
	}
	
	@FindBy(xpath="//a[@id='pageid:frm:cmdlink']")
	private WebElement addFilterLogicLinkOnEmailProspect;

	/**
	 * @return the selectAReportsErrorMsg
	 */
	public WebElement getAddFilterLogicLinkOnEmailProspect(int timeOut) {
		return isDisplayed(driver, addFilterLogicLinkOnEmailProspect, "Visibility", timeOut, "Add Filter Logic Link");
	}
	
	@FindBy(xpath="//a[@title='Clear'][contains(@onclick,'clearSearch()')]")
	private WebElement emailProspectClearBtn;

	/**
	 * @return the addProspectClearBtn
	 */
	public WebElement getEmailProspectClearBtn(int timeOut) {
		return isDisplayed(driver, emailProspectClearBtn, "Visibility", timeOut, "Email Prospect Clear Btn");
	}
	
	
	@FindBy(xpath="(//div[@class='step_1']//a[@title='Cancel'])[2]")
	private WebElement emailProspectStep1CancelBottomBtn;

	/**
	 * @return the emailProspectStep1NextBtn
	 */
	public WebElement getEmailProspectStep1CancelBottomBtn(int timeOut) {
		return isDisplayed(driver, emailProspectStep1CancelBottomBtn, "Visibility", timeOut, "email prospect steps 1 Cancel Bottom button");
	}
	

	@FindBy(xpath="//div[contains(text(),'Step 2. Select an email template')]")
	private WebElement step2_Msg;

	/**
	 * @return the step3_BulkEmailPage
	 */
	public WebElement getStep2_Msg(String environment,String mode,int timeOut) {
		return isDisplayed(driver, step2_Msg, "Visibility", timeOut, "Step2 Msg");
	}
	
	public WebElement getSelectProspectsWrenchIcon(PageName pageName, String mode, int timeOut) {
		String xpath="";
		if(pageName.toString().equalsIgnoreCase(PageName.MarketingInitiatives.toString())) {
			if(mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
				xpath="//a[@title='Columns to Display'][contains(@onclick,'show_wrenchpopup_pagewrench_addpros')]";
			}else {
				xpath="//a[@title='Columns to Display'][contains(@onclick,'showImz1wrench_addpros')]";
			}
		}else if(pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString())) {
			
			xpath="//div[@title='Columns to Display'][contains(@onclick,'show_wrenchpopup_pageMEP_Wrench')]";
			
		}else if (pageName.toString().equalsIgnoreCase(PageName.EmailTargets.toString())) {
			xpath="//a[@title='Columns to Display'][contains(@onclick,'show_wrenchpopup_pageMET_Wrench')]";
			
		}else if (pageName.toString().equalsIgnoreCase(PageName.CreateFundraisingPage.toString())) {
			xpath="//a[@id='wrenchMnGridId']//img";
		}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingContactPopUp.toString())) {
			
				xpath="//div[contains(@class,'Past_FundraisingsContact_PopUp FancyboxContainer')]//a[@title='Columns to Display']//img";
			
		}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingAccountPopUp.toString())) {
			
			xpath="//div[contains(@class,'Past_Fundraisings_PopUp')]//a/div/img";;
		}else if (pageName.toString().equalsIgnoreCase(PageName.FundDrawdown.toString())) {
			
			xpath="//a[contains(@onclick,'show_wrenchpopup_pagebulkEmailDD')]/div/img";
		}else if (pageName.toString().equalsIgnoreCase(PageName.Send_Distribution_Notices.toString())) {
			
			xpath="//a[@title='Columns to Display']";
		}
		else {
			xpath="//a[@title='Columns to Display'][contains(@onclick,'show_wrenchpopup_pagewrenchMEF')]";
		}
		return isDisplayed(driver, FindElement(driver,xpath, "select prospects wrench icon", action.BOOLEAN,10), "Visibility", timeOut, "select prospects wrench icon");
	}
	
	public WebElement getColumnToDisplayViewDropDownList(PageName pageName,int timeOut) {
		String xpath="";
		if(pageName.toString().equalsIgnoreCase(PageName.MarketingInitiatives.toString())) {

			xpath="objectSelectedwrench_addpros";
		}else if(pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString())) {
			
			xpath="objectSelectedMEP_Wrench";
		}else if(pageName.toString().equalsIgnoreCase(PageName.EmailTargets.toString())) {
			
			xpath="objectSelectedMET_Wrench";
		}else if(pageName.toString().equalsIgnoreCase(PageName.CreateFundraisingPage.toString())) {
			
			xpath="objectSelectedwrenchCMF";
		}else if(pageName.toString().equalsIgnoreCase(PageName.FundDrawdown.toString())){
			xpath="objectSelectedbulkEmailDD";
		}else if(pageName.toString().equalsIgnoreCase(PageName.Send_Distribution_Notices.toString())){
			xpath="objectSelectedbulkEmailFD";
		}
		else {
			xpath="objectSelectedwrenchMEF";
		}
		return isDisplayed(driver, FindElement(driver, "//select[@id='"+xpath+"']", "view drop down list", action.BOOLEAN,10), "Visibility", timeOut, "view drop down list");
	}
	
	public WebElement getColumnToDisplayApplyBtn(PageName pageName,int timeOut) {
		String xpath="";
		if(pageName.toString().equalsIgnoreCase(PageName.MarketingInitiatives.toString())) {
			xpath="_applywrench_addpros";
		}else if(pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString())) {
			xpath="_applyMEP_Wrench";
		}else if (pageName.toString().equalsIgnoreCase(PageName.EmailTargets.toString())) {
			xpath="_applyMET_Wrench";
		}else if (pageName.toString().equalsIgnoreCase(PageName.CreateFundraisingPage.toString())) {
			xpath="_applywrenchCMF";
		}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingAccountPopUp.toString())) {
			xpath="_applywrenchCMF_PFA";
		}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingContactPopUp.toString())) {
			xpath="_applywrenchCMF_PFC";
		}else if (pageName.toString().equalsIgnoreCase(PageName.FundDrawdown.toString())) {
			xpath="_applybulkEmailDD";
		}else if (pageName.toString().equalsIgnoreCase(PageName.Send_Distribution_Notices.toString())) {
			xpath="_applybulkEmailFD";
		}
		else {
			xpath="_applywrenchMEF";
		}
		return FindElement(driver, "//a[@id='"+xpath+"']", "apply button", action.BOOLEAN,10);
	}
	
	public List<WebElement> getColumnToDisplayAvailableFieldsTextList(PageName pageName){
		String xpath="";
		if(pageName.toString().equalsIgnoreCase(PageName.MarketingInitiatives.toString())) {
			xpath="avllistwrench_addpros-view";
			
		}else if (pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString())) {
			
			xpath="avllistMEP_Wrench-view";
			
		}else if (pageName.toString().equalsIgnoreCase(PageName.EmailTargets.toString())) {
			
			xpath="avllistMET_Wrench-view";
		}else if (pageName.toString().equalsIgnoreCase(PageName.CreateFundraisingPage.toString())) {
			
			xpath="avllistwrenchCMF-view";
		}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingAccountPopUp.toString())) {
			
			xpath="avllistwrenchCMF_PFA-view";
		}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingContactPopUp.toString())) {
			
			xpath="avllistwrenchCMF_PFC-view";
		}else if (pageName.toString().equalsIgnoreCase(PageName.FundDrawdown.toString())) {
			
			xpath="avllistbulkEmailDD-view";
		}else if (pageName.toString().equalsIgnoreCase(PageName.Send_Distribution_Notices.toString())) {
			
			xpath="avllistbulkEmailFD-view";
		}
		else {
			xpath="avllistwrenchMEF-view";
		}
		return FindElements(driver, "//span[@id='"+xpath+"']/span/span/span[3]", "column to display available field text list");
	}
	
	public List<WebElement> getColumnToDisplaySelectedFieldsTextList(PageName pageName){
		String xpath="";
		if(pageName.toString().equalsIgnoreCase(PageName.MarketingInitiatives.toString())) {
			xpath="sellistwrench_addpros-view";
		}else if(pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString())) {
			xpath="sellistMEP_Wrench-view";
		}else if (pageName.toString().equalsIgnoreCase(PageName.EmailTargets.toString())) {
			
			xpath="sellistMET_Wrench-view";
		}else if (pageName.toString().equalsIgnoreCase(PageName.CreateFundraisingPage.toString())) {
			
			xpath="sellistwrenchCMF-view";
		}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingAccountPopUp.toString())) {
			
			xpath="sellistwrenchCMF_PFA-view";
		}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingContactPopUp.toString())) {
			
			xpath="sellistwrenchCMF_PFC-view";
		}
		else if (pageName.toString().equalsIgnoreCase(PageName.FundDrawdown.toString())) {
			
			xpath="sellistbulkEmailDD-view";
		}else if (pageName.toString().equalsIgnoreCase(PageName.Send_Distribution_Notices.toString())) {
			
			xpath="sellistbulkEmailFD-view";
		}
		else {
			xpath="sellistwrenchMEF-view";
		}
		return FindElements(driver, "//span[@id='"+xpath+"']/span/span/span[3]", "column to display selected field text list");
		
	}
	
public WebElement getColumnToDisplayLeftToRightBtn(PageName pageName,int timeOut) {
		
		String xpath="";
		if(pageName.toString().equalsIgnoreCase(PageName.MarketingInitiatives.toString())) {
			xpath="lefttorightenablewrench_addpros";
		}else if(pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString())) {
			
			xpath="lefttorightenableMEP_Wrench";
			
		}else if (pageName.toString().equalsIgnoreCase(PageName.EmailTargets.toString())) {
			
			xpath="lefttorightenableMET_Wrench";
		}else if (pageName.toString().equalsIgnoreCase(PageName.ManageTarget.toString())) {
			
			xpath="lefttorightenableManageTarget";
		}else if (pageName.toString().equalsIgnoreCase(PageName.CreateFundraisingPage.toString())) {
			
			xpath="lefttorightenablewrenchCMF";
		}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingAccountPopUp.toString())) {
			
			xpath="lefttorightenablewrenchCMF_PFA";
		}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingContactPopUp.toString())) {
			
			xpath="lefttorightenablewrenchCMF_PFC";
		}else if (pageName.toString().equalsIgnoreCase(PageName.FundDrawdown.toString())) {
			
			xpath="lefttorightenablebulkEmailDD";
		}else if (pageName.toString().equalsIgnoreCase(PageName.Send_Distribution_Notices.toString())) {
			
			xpath="lefttorightenablebulkEmailFD";
		}
		else {
			xpath="lefttorightenablewrenchMEF";
		}
		return isDisplayed(driver, FindElement(driver, "//img[@id='"+xpath+"']", "left to right move button", action.BOOLEAN,10), "Visibility", timeOut, "left to right move button");
	}
	
public WebElement getColumnToDisplaySearchTextCrossIcon(PageName pageName,int timeOut) {
	String xpath="";
	if(pageName.toString().equalsIgnoreCase(PageName.MarketingInitiatives.toString())) {
		
		xpath="clearsearchenb1wrench_addpros";
	}else if(pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString())) {
		
		xpath="clearsearchenb1MEP_Wrench";
	}else if (pageName.toString().equalsIgnoreCase(PageName.EmailTargets.toString())) {
		
		xpath="clearsearchenb1MET_Wrench";
	}else if (pageName.toString().equalsIgnoreCase(PageName.CreateFundraisingPage.toString())) {
		
		xpath="clearsearchenb1wrenchCMF";
	}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingAccountPopUp.toString())) {
		
		xpath="clearsearchenb1wrenchCMF_PFA";
	}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingContactPopUp.toString())) {
		
		xpath="clearsearchenb1wrenchCMF_PFC";
	}else if (pageName.toString().equalsIgnoreCase(PageName.FundDrawdown.toString())) {
		
		xpath="clearsearchenb1bulkEmailDD";
	}else if (pageName.toString().equalsIgnoreCase(PageName.Send_Distribution_Notices.toString())) {
		
		xpath="clearsearchenb1bulkEmailFD";
	}
	else {
		xpath="clearsearchenb1wrenchMEF";
	}
	return isDisplayed(driver, FindElement(driver, "//span[@id='"+xpath+"']/div", "cross icon", action.BOOLEAN,10), "Visibility", timeOut, "cross icon");
}

public WebElement getColumnToDisplaySearchTextBox(PageName pageName,int timeOut) {
	String xpath="";
	if(pageName.toString().equalsIgnoreCase(PageName.MarketingInitiatives.toString())) {
		xpath="searchcon_grid1wrench_addpros";
	}else if(pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString())) {
		xpath="searchcon_grid1MEP_Wrench";
	}else if (pageName.toString().equalsIgnoreCase(PageName.EmailTargets.toString())) {
		
		xpath="searchcon_grid1MET_Wrench";
	} else if (pageName.toString().equalsIgnoreCase(PageName.CreateFundraisingPage.toString())) {
		
		xpath="searchcon_grid1wrenchCMF";
	} else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingAccountPopUp.toString())) {
		
		xpath="searchcon_grid1wrenchCMF_PFA";
	} else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingContactPopUp.toString())) {
		
		xpath="searchcon_grid1wrenchCMF_PFC";
	} else if (pageName.toString().equalsIgnoreCase(PageName.FundDrawdown.toString())) {
		
		xpath="searchcon_grid1bulkEmailDD";
	}else if (pageName.toString().equalsIgnoreCase(PageName.Send_Distribution_Notices.toString())) {
		
		xpath="searchcon_grid1bulkEmailFD";
	}
	else {
		xpath="searchcon_grid1wrenchMEF";
	}
	return isDisplayed(driver, FindElement(driver, "//input[@id='"+xpath+"']", "search text box", action.BOOLEAN,10), "Visibility", timeOut, "search text box");
}



public WebElement getSearchIconInDisplayToColumn(PageName pageName,int timeOut) {
	
	String xpath="";
	if(pageName.toString().equalsIgnoreCase(PageName.pastFundraisingAccountPopUp.toString())) {
		xpath="(//img[@title='Search'])[3]";
	}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingContactPopUp.toString())) {
		xpath="(//img[@title='Search'])[2]";
	}
	else {
		xpath="//img[@title='Search']";
	}
	return isDisplayed(driver, FindElement(driver, xpath, "search text box", action.BOOLEAN,10), "Visibility", timeOut, "search text box");
	
}

public WebElement getColumnToDisplayRightToLeftBtn(PageName pageName,int timeOut) {
	String xpath="";
	if(pageName.toString().equalsIgnoreCase(PageName.MarketingInitiatives.toString())) {
		xpath="righttoleftenablewrench_addpros";
	}else if(pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString())) {
		xpath="righttoleftenableMEP_Wrench";
	}else if (pageName.toString().equalsIgnoreCase(PageName.EmailTargets.toString())) {
		xpath="righttoleftenableMET_Wrench";
	}else if (pageName.toString().equalsIgnoreCase(PageName.EmailTargets.toString())) {
		xpath="righttoleftenablewrenchCMF";
	}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingAccountPopUp.toString())) {
		xpath="righttoleftenablewrenchCMF_PFA";
	}else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingContactPopUp.toString())) {
		xpath="righttoleftenablewrenchCMF_PFC";
	}else if (pageName.toString().equalsIgnoreCase(PageName.FundDrawdown.toString())) {
		xpath="righttoleftenablebulkEmailDD";
	}else if (pageName.toString().equalsIgnoreCase(PageName.Send_Distribution_Notices.toString())) {
		xpath="righttoleftenablebulkEmailFD";
	}
	else {
		xpath="righttoleftenablewrenchMEF";
	}
	return isDisplayed(driver, FindElement(driver, "//img[@id='"+xpath+"']", "right to left move button", action.BOOLEAN,10), "Visibility", timeOut, "right to left move button");
}

public List<WebElement> getSelectProspectsHeaderTextList(PageName pageName){
	String xpath="";
	if(pageName.toString().equalsIgnoreCase(PageName.MarketingInitiatives.toString())) {
		xpath="//span[contains(@id,'Select_from_Search_ResultsA-header-')]/span[3]";
		
	}else if(pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString()) || pageName.toString().equalsIgnoreCase(PageName.EmailTargets.toString()) || pageName.toString().equalsIgnoreCase(PageName.emailFundraisingContact.toString()) 
			|| pageName.toString().equalsIgnoreCase(PageName.FundDrawdown.toString())
			|| pageName.toString().equalsIgnoreCase(PageName.Send_Distribution_Notices.toString())) {
		
		xpath="//span[contains(@class,'aw-grid-header aw-column-')]/span/span[3]";
	}else if(pageName.toString().equalsIgnoreCase(PageName.CreateFundraisingPage.toString())) {
		
		xpath="//span[contains(@id,'Select_from_Search_Results-header-')]/span/span[3]";
	}else if(pageName.toString().equalsIgnoreCase(PageName.pastFundraisingAccountPopUp.toString())) {
		
		xpath="//span[contains(@id,'Past_Fundraisings-header-')]/span/span[3]";
	}else if(pageName.toString().equalsIgnoreCase(PageName.pastFundraisingContactPopUp.toString())) {
		
		xpath="//span[contains(@id,'Past_FundraisingsContact-header-')]/span/span[3]";
	}
	else {
		xpath="//span[@id='grid_addtargets']//span[contains(@class,'aw-grid-header aw-column-')]/span/span[3]";
	}
	return FindElements(driver,xpath, "header text list");
}



public WebElement getColumnToDisplayRevertToDefaultBtn(int timeOut) {
	String xpath="//a[@title='Revert to Defaults'][@style='display: inline-block;']";
	return FindElement(driver, xpath, "revert to default button",action.BOOLEAN,  timeOut);
}

@FindBy(xpath="//iframe[@title='Email Prospect']")
private WebElement emailProspectFrame;

public WebElement getEmailProspectFrame(int timeOut) {
	return isDisplayed(driver, emailProspectFrame, "Visibility", timeOut, "email prospect frame");
}


@FindBy(xpath="//a[@class='ShowHide_reportsearcherror_error']//img")
private WebElement crossImgPleaseEnterAValuePopUp;

/**
 * @return the crossImgSelectAReportPopUp
 */
public WebElement getCrossImgPleaseEnterAValuePopUp(int timeOut) {
	return isDisplayed(driver, crossImgPleaseEnterAValuePopUp, "Visibility", timeOut, "cross Img Please Enter A ValuePopUp");
}


@FindBy(xpath="//iframe[@title='Email Fundraisings']")
private WebElement emailFundRaisingContact_Lightning;


/**
 * @return the marketInitiativeFrame_Lightning
 */
public WebElement getEmailFundRaisingContact_Lightning(int timeOut) {
	ThreadSleep(30000);
	String xpath = "//iframe";
	List<WebElement> eleList = FindElements(driver, xpath, "frame");
	for (int i = eleList.size()-1; i >=0; i--) {
		WebElement ele = eleList.get(i);
		ele=isDisplayed(driver, ele, "Visibility", timeOut, "Email FundRaising Frame Lightning");
		if (ele!=null) {
			return ele;
		} else {

		}
	}
	return isDisplayed(driver, emailFundRaisingContact_Lightning, "Visibility", timeOut, "Email FundRaising Frame Lightning");
}


}
