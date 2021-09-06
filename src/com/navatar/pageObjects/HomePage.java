package com.navatar.pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.MouseAction.Button;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomePage extends BasePageBusinessLayer {

	public HomePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath="//td[@class='pbTitle']//h2")
	private WebElement dashboardLabelOnHomePage;

	/**
	 * @return the dashboardLabelOnHomePage
	 */
	public WebElement getDashboardLabelOnHomePage(String environment, String mode,int timeOut) {
		return isDisplayed(driver, dashboardLabelOnHomePage, "Visibility", timeOut, "Dashboard label on home page");
	}
	
	@FindBy(xpath="//span[@id='userNavLabel']")
	private WebElement userMenuTab;

	/**
	 * @return the userMenuTab
	 */
	public WebElement getUserMenuTab(int timeOut) {
		return isDisplayed(driver, userMenuTab, "Visibility", timeOut, "user menu tab");
	}

	
	@FindBy(xpath="//a[@title='Setup']")
	private WebElement setupLink;

	/**
	 * @return the setupLink
	 */
	public WebElement getSetupLink(int timeOut) {
		return isDisplayed(driver, setupLink, "Visibility", timeOut, "setup link");
	}
	
	@FindBy(xpath="//div[@class='flexipageComponent']//span[text()='Navatar Quick Links']")
	private WebElement navatarQuickLink_Lighting;

	/**
	 * @return the navatarQuickLink_Lighting
	 */
	public WebElement getNavatarQuickLink_Lighting(String environment,int timeOut) {
		return isDisplayed(driver, navatarQuickLink_Lighting, "Visibility", timeOut, "Navatar Quick Link Lighting");
	}
	
	
	
	@FindBy(xpath="//div[@class='body_container']//iframe")
	private WebElement navatarQuickLinkFrame_Lighting;

	/**
	 * @return the navatarQuickLinkFrame_Lighting
	 */
	public WebElement getNavatarQuickLinkFrame_Lighting(String environment,int timeOut) {
		return isDisplayed(driver, navatarQuickLinkFrame_Lighting, "Visibility", timeOut, "Navatar Quick Link Frame Lighting");
	}
	
	@FindBy(xpath="//div[@class='sidebarModuleBody']//iframe")
	private WebElement navatarQuickLinkFrame_Classic;

	/**
	 * @return the navatarQuickLinkFrame_Lighting
	 */
	public WebElement getNavatarQuickLinkFrame_Classic(String environment,int timeOut) {
		return isDisplayed(driver, navatarQuickLinkFrame_Classic, "Visibility", timeOut, "Navatar Quick Link Frame Classic");
	}
	
	public List<WebElement> getNavatarQuickLinksList(String environment, String mode) {
		String xpath="";
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			xpath="NavatarQuickLink_Sec_Lightning";
		}else {
			xpath="NavatarQuickLink_Sec";
		}
		return FindElements(driver, "//div[@class='"+xpath+"']//li/a","navatar quick link list on "+mode+" page");
	}
	
	@FindBy(xpath="//div[contains(@class,'slds-template_iframe')]//iframe[@title='accessibility title']")
	private WebElement createFundraisingsFrame_Lighting;

	/**
	 * @return the createFundraisingsFrame_Lighting
	 */
	public WebElement getCreateFundraisingsFrame_Lighting(int timeOut) {
		return isDisplayed(driver, createFundraisingsFrame_Lighting, "Visibility", timeOut, "create fundraisings frame on lighting");
	}
	
	@FindBy(xpath="//div[@class='slds-page-header']//p[1]")
	private WebElement createFundraisingHeaderText;
	
	/**
	 * @return the createFundraisingHeaderText
	 */
	public WebElement getCreateFundraisingHeaderText(int timeOut) {
		return isDisplayed(driver, createFundraisingHeaderText, "Visibility", timeOut, "create Fundraising Header Text");
	}
	
	@FindBy(xpath="//div[@class='slds-page-header']//p[2]")
	private WebElement createFundraisingHeaderText1;
	
	/**
	 * @return the createFundraisingHeaderText
	 */
	public WebElement getCreateFundraisingHeaderText1(int timeOut) {
		return isDisplayed(driver, createFundraisingHeaderText1, "Visibility", timeOut, "create Fundraising Header Text for compnay name");
	}
	
	
	
	@FindBy(xpath="(//img[@title='Fund Name Lookup (New Window)'])[1]")
	private WebElement selectFundNameFromSelectFundPopUpLookUpIconOnCompanyPage;
	
	

	/**
	 * @return the selectFundNameFromSelectFundPopUpLookUpIconOnCompanyPage
	 */
	public WebElement getSelectFundNameFromSelectFundPopUpLookUpIconOnCompanyPage(int timeOut) {
		return isDisplayed(driver, selectFundNameFromSelectFundPopUpLookUpIconOnCompanyPage, "Visibility", timeOut, "select FundName From Select Fund Pop Up LookUp Icon On CompanyPage");
	}

	@FindBy(xpath="(//img[@title='Fund Name Lookup (New Window)'])[2]")
	private WebElement selectFundNameFromSelectFundPopUpLookUpIcon;

	/**
	 * @return the selectFundNameFromSelectFundPopUpLookUpIcon
	 */
	public WebElement getSelectFundNameFromSelectFundPopUpLookUpIcon(int timeOut) {
		return isDisplayed(driver, selectFundNameFromSelectFundPopUpLookUpIcon, "Visibility", timeOut, "select fund name look up in select fund pop up");
	}
	
	@FindBy(xpath="(//img[@title='Company Lookup (New Window)'])[3]")
	private WebElement selectCompanyNameFromSelectFundPopUpLookUpIcon;

	/**
	 * @return the selectCompanyNameFromSelectFundPopUpLookUpIcon
	 */
	public WebElement getSelectCompanyNameFromSelectFundPopUpLookUpIcon(int timeOut) {
		return isDisplayed(driver, selectCompanyNameFromSelectFundPopUpLookUpIcon, "Visibility", timeOut, "select company name look up in select fund pop up");
	}
	
	@FindBy(xpath="//select[contains(@id,'CreateFundraisingFormId:FundSelectListId')]")
	private WebElement selectFundNameDropDownListInSelectFundPopUp;
	
	
	/**
	 * @return the selectFundNameDropDownListInSelectFundPopUp
	 */
	public WebElement getSelectFundNameDropDownListInSelectFundPopUp(int timeOut) {
		return isDisplayed(driver, selectFundNameDropDownListInSelectFundPopUp, "Visibility", timeOut, "select fund name drop down list in select fund pop up");
	}

//	@FindBy(xpath="//button[contains(text(),'Continue')]")
//	private WebElement selectFundNamePopUpContinueBtn;
	
	public WebElement getSelectFundNamePopUpContinueBtn() {
		WebElement ele=null;
		List<WebElement> lst = FindElements(driver, "//button[contains(text(),'Continue')]", "continue button");
		if(!lst.isEmpty()) {
			for (int i = 0; i < lst.size(); i++) {
				ele=isDisplayed(driver, lst.get(i), "visibility", 1, "continue button");
				if(ele!=null) {
					return ele;
				}else {
					if(i==lst.size()-1) {
						return ele;
					}
				}
			}
		}
		return ele;
	}
	
	@FindBy(xpath="//div[@class='SelectFund2']//button[text()='Cancel']")
	private WebElement selectFundPopUpCancelBtn;
	
//	/**
//	 * @return the selectFundNamePopUpContinueBtn
//	 */
//	public WebElement getSelectFundNamePopUpContinueBtn(int timeOut) {
//		return isDisplayed(driver, selectFundNamePopUpContinueBtn, "Visibility", timeOut, "select Fund Name Pop Up Continue Btn");
//	}
	
	/**
	 * @return the selectFundPopUpCancelBtn
	 */
	public WebElement getSelectFundPopUpCancelBtn(int timeOut) {
		return isDisplayed(driver, selectFundPopUpCancelBtn, "Visibility", timeOut, "select Fund PopUp Cancel Btn");
	}

	@FindBy(xpath="(//div[contains(@class,'SelectFund_Popup')]//button[text()='Cancel'])[2]")
	private WebElement selectFundNamePopUpCancelBtn;

	/**
	 * @return the selectFundNamePopUpCancelBtn
	 */
	public WebElement getSelectFundNamePopUpCancelBtn(int timeOut) {
		return isDisplayed(driver, selectFundNamePopUpCancelBtn, "Visibility", timeOut, "select Fund Name Pop Up Cancel Btn");
	}
	
	@FindBy(xpath="(//div[contains(@class,'SelectFund_Popup')]//button[text()='Cancel'])[1]")
	private WebElement selectFundNamePopUpCancelbtnOnCompanypage;
	
	
	
	/**
	 * @return the selectFundNamePopUpCancelbtnOnCompanypage
	 */
	public WebElement getSelectFundNamePopUpCancelbtnOnCompanypage(int timeOut) {
		return isDisplayed(driver, selectFundNamePopUpCancelbtnOnCompanypage, "Visibility", timeOut, "select FundName PopUp Cancel btn On Company page");
	}

	@FindBy(xpath="(//img[@title='Fund Name Lookup (New Window)'])[3]")
	private WebElement selectFundNameFromSearchBasedOnExistingFundLookUpIcon;
	
	/**
	 * @return the selectFundNameFromSearchBasedOnExistingFundLookUpIcon
	 */
	public WebElement getSelectFundNameFromSearchBasedOnExistingFundLookUpIcon(int timeOut) {
		return isDisplayed(driver, selectFundNameFromSearchBasedOnExistingFundLookUpIcon, "Visibility", timeOut, "select Fund Name From Search Based On Existing Fund LookUp Icon");
	}

	@FindBy(xpath="//a[@id='AccContTabId']")
	private WebElement searchBasedOnAccountsAndContactsTab;

	/**
	 * @return the searchBasedOnAccountsAndContactsTab
	 */
	public WebElement getSearchBasedOnAccountsAndContactsTab(int timeOut) {
		return isDisplayed(driver, searchBasedOnAccountsAndContactsTab, "Visibility", timeOut, "Search based on Accounts and Contacts tab");
	}
	
	@FindBy(xpath="//a[@id='AddRow2']")
	private WebElement addRowsLink;

	/**
	 * @return the addRowsLink
	 */
	public WebElement getAddRowsLink(int timeOut) {
		return isDisplayed(driver, addRowsLink, "Visibility", timeOut, "add rows link");
	}
	
	@FindBy(xpath="//input[@title='Search']")
	private WebElement searchBasedOnAccountsAndContactsSearchBtn;

	/**
	 * @return the searchBasedOnAccountsAndContactsSearchBtn
	 */
	public WebElement getSearchBasedOnAccountsAndContactsSearchBtn(int timeOut) {
		return isDisplayed(driver, searchBasedOnAccountsAndContactsSearchBtn, "Visibility", timeOut, "search Based On Accounts And Contacts Search Btn");
	}
	
	@FindBy(xpath="//button[@title='Add to Fudraising List']")
	private WebElement addToFundraisingListBtn;
	
	/**
	 * @return the addToFundraisingListBtn
	 */
	public WebElement getAddToFundraisingListBtn(int timeOut) {
		return isDisplayed(driver, addToFundraisingListBtn, "Visibility", timeOut, "Add to Fudraising List button");
	}

	@FindBy(xpath="//span[@id='Select_from_Search_Results-scroll-box']")
	private WebElement selectInvestorGridScrollBox;

	/**
	 * @return the selectInvestorGridScrollBox
	 */
	public WebElement getSelectInvestorGridScrollBox(int timeOut) {
		return isDisplayed(driver, selectInvestorGridScrollBox, "Visibility", timeOut, "select Investor Grid Scroll Box");
	}
	
	@FindBy(xpath="//div[contains(@class,'ContactAccess_fancybox')]//button[text()='Apply']")
	private WebElement fundraisingContactPopUpApplyBtn;

	/**
	 * @return the fundraisingContactPopUpApplyBtn
	 */
	public WebElement getFundraisingContactPopUpApplyBtn(int timeOut) {
		return isDisplayed(driver, fundraisingContactPopUpApplyBtn, "Visibility", timeOut, "Fundraising Contact PopUp Apply Btn");
	}
	
	@FindBy(xpath="//a[@title='Create Fundraisings']")
	private WebElement createFundraisingBtn;
	
	@FindBy(xpath="//input[@value='Create Fundraisings']")
	private WebElement createFundraisingBtnOnWarningPopUp;

	/**
	 * @return the createFundraisingBtn
	 */
	public WebElement getCreateFundraisingBtn(int timeOut) {
		return isDisplayed(driver, createFundraisingBtn, "Visibility", timeOut, "create fundraising button");
	}
	
	
	@FindBy(xpath="//div[contains(@class,'Confirmation_PopUp')]//button")
	private WebElement createFundraisingConfirmationOkBtn;

	/**
	 * @return the createFundraisingConfirmationOkBtn
	 */
	public WebElement getCreateFundraisingConfirmationOkBtn(int timeOut) {
		return isDisplayed(driver, createFundraisingConfirmationOkBtn, "Visibility", timeOut, "create fundraising confirmation popup ok button");
	}
	
	
	public List<WebElement> getFundraisingNameList(){
		return FindElements(driver, "//span[contains(@id,'Past_FundraisingsContact-cell-0')]/a","fundraising name list");
	}
	
	public List<WebElement> getStageList(){
		return FindElements(driver, "//span[contains(@id,'Past_FundraisingsContact-cell-1')]", "stage list");
	}
	
	public List<WebElement> getInvestmentLikelyAmountList(){
		return FindElements(driver, "//span[contains(@id,'Past_FundraisingsContact-cell-2')]/span", "investment Likely Amount List");
	}
	
	public List<WebElement> getRoleList(){
		return FindElements(driver, "//span[contains(@id,'Past_FundraisingsContact-cell-3')]", "role list");
	}
	
	public List<WebElement> getPrimaryList(){
		return FindElements(driver, "//span[contains(@id,'Past_FundraisingsContact-cell-4')]/img", "primary list");
	}
	
	public List<WebElement> getCreatedDateList(){
		return FindElements(driver, "//span[contains(@id,'Past_FundraisingsContact-cell-5')]", "created date list");
	}
	
	
	
	
	

	@FindBy(xpath="//div[contains(@class,'Past_FundraisingsContact_PopUp')]//a/img")
	private WebElement pastFundraisingPopUpCrossIcon;

	/**
	 * @return the pastFundraisingPopUpCrossIcon
	 */
	public WebElement getPastFundraisingPopUpCrossIcon(int timeOut) {
		return isDisplayed(driver, pastFundraisingPopUpCrossIcon, "Visibility", timeOut, "past Fundraising Pop Up Cross Icon");
	}
	
	@FindBy(xpath="(//img[@title='Company Lookup (New Window)'])[1]")
	private WebElement selectCompanyNameWarningPopUpLookUpIcon;

	/**
	 * @return the selectCompanyNameWarningPopUpLookUpIcon
	 */
	public WebElement getSelectCompanyNameWarningPopUpLookUpIcon(int timeOut) {
		return isDisplayed(driver, selectCompanyNameWarningPopUpLookUpIcon, "Visibility", timeOut, "select Company Name Warning PopUp Look Up Icon");
	}
	
	
	@FindBy(xpath="//span[@id='gridSelectedContact-cell-1-0']/span")
	private WebElement fundraisingContactErrorMsg;

	/**
	 * @return the fundraisingContactErrorMsg
	 */
	public WebElement getFundraisingContactErrorMsg(int timeOut) {
		return isDisplayed(driver, fundraisingContactErrorMsg, "Visibility", timeOut, "fundraising contact error message");
	}
	
	@FindBy(xpath="//span[@id='RcrdCntSlctFundr']")
	private WebElement fundraisingContactRecordCount;

	/**
	 * @return the fundraisingContactRecordCount
	 */
	public WebElement getFundraisingContactRecordCount(int timeOut) {
		return isDisplayed(driver, fundraisingContactRecordCount, "Visibility", timeOut, "fundraising contact record count");
	}
	
	@FindBy(xpath="//span[@id='ErrMsgFundTyp']")
	private WebElement selectCoInvestmentRelatedFundErrorMsg;

	/**
	 * @return the selectCoInvestmentRelatedFundErrorMsg
	 */
	public WebElement getSelectCoInvestmentRelatedFundErrorMsg(int timeOut) {
		return isDisplayed(driver, selectCoInvestmentRelatedFundErrorMsg, "Visibility", timeOut, "select Co Investment Related Fund Error Msg");
	}
	
	@FindBy(xpath="//a[@id='AllContTabId']")
	private WebElement allContactsOptionOnSearchBasedOnExistingFunds;

	/**
	 * @return the allContactsOptionOnSearchBasedOnExistingFundsOptions
	 */
	public WebElement getAllContactsOptionOnSearchBasedOnExistingFunds(int timeOut) {
		return isDisplayed(driver, allContactsOptionOnSearchBasedOnExistingFunds, "Visibility", timeOut, "all Contacts Option On Search Based On Existing Funds Options");
	}
	@FindBy(xpath="//a[@id='FundContTabId']")
	private WebElement onlyFundraisingContactOptionOnSearchBasedOnExistingFundsOptions;

	/**
	 * @return the onlyFundraisingContactOptionOnSearchBasedOnExistingFundsOptions
	 */
	public WebElement getOnlyFundraisingContactOptionOnSearchBasedOnExistingFunds(int timeOut) {
		return isDisplayed(driver, onlyFundraisingContactOptionOnSearchBasedOnExistingFundsOptions, "Visibility", timeOut,
				"only Fundraising Contact Option On Search Based On Existing Funds Options");
	}
	
	@FindBy(xpath="//li[@id='idSrchTabExstngFund']/a/span//img")
	private WebElement SearchBasedOnExistingFundsDownArrow;

	/**
	 * @return the searchBasedOnExistingFundsDownArrow
	 */
	public WebElement getSearchBasedOnExistingFundsDownArrow(int timeOut) {
		return isDisplayed(driver, SearchBasedOnExistingFundsDownArrow, "Visibility", timeOut, "Search Based On Existing Funds Down Arrow");
	}
	
	@FindBy(xpath="//img[@title='Fundraising Lookup (New Window)']")
	private WebElement fundRaisingNameLookUpIcon;

	/**
	 * @return the fundRaisingNameLookUpIcon
	 */
	public WebElement getFundRaisingNameLookUpIcon(int timeOut) {
		return isDisplayed(driver, fundRaisingNameLookUpIcon, "Visibility", timeOut, "fundraising Name look up icon on create commitment page");
	}
	
	@FindBy(xpath="//a[@title='Fund']")
	private WebElement fundTypeCommitment;

	/**
	 * @return the fundTypeCommitment
	 */
	public WebElement getFundTypeCommitment(int timeOut) {
		return isDisplayed(driver, fundTypeCommitment, "Visibility", timeOut, "fund type commitment");
	}
	
	@FindBy(xpath="//a[@title='Coinvestment']")
	private WebElement coInvesmentTypeCommitment;

	/**
	 * @return the coInvesmentTypeCommitment
	 */
	public WebElement getCoInvesmentTypeCommitment(int timeOut) {
		return isDisplayed(driver, coInvesmentTypeCommitment, "Visibility", timeOut, "coinvestment type commitment");
	}
	
//	@FindBy(xpath="//div[@class='slds-modal__footer popup1']//a[@title='Continue']")
//	private WebElement commitmentCreationContinueBtn;

	/**
	 * @return the commitmentCreationContinueBtn
	 */
	public List<WebElement> getCommitmentCreationContinueBtn(int timeOut) {
		return FindElements(driver, "//a[@title='Continue']", "create commitment continue button");
	}
	
	@FindBy(xpath="//div[@class='slds-modal__footer popup1']//a[text()='Cancel']")
	private WebElement commitmentCreationCancelBtn;

	/**
	 * @return the commitmentCreationCancelBtn
	 */
	public WebElement getCommitmentCreationCancelBtn(int timeOut) {
		return isDisplayed(driver, commitmentCreationCancelBtn, "Visibility", timeOut, "commitment Creation Cancel Btn");
	}
	
	@FindBy(xpath="//img[@title='Fund Name Lookup (New Window)']")
	private WebElement fundNameLookUpIconOnCreateCommitmentPopUp;

	/**
	 * @return the selectFundNameOnCreateCommitmentPopUp
	 */
	public WebElement getFundNameLookUpIconOnCreateCommitmentPopUp(int timeOut) {
		return isDisplayed(driver, fundNameLookUpIconOnCreateCommitmentPopUp, "Visibility", timeOut, "select FundName LookUp Icon On Create Commitment PopUp");
	}
	
	
	public WebElement getLegalNameLookUpIcon(PageName pageName, int timeOut) {
		int i=0;
		if(pageName.toString().equalsIgnoreCase(PageName.CreateCommitmentCoInvestmentType.toString())) {
			i=1;
		}else if (pageName.toString().equalsIgnoreCase(PageName.CreateCommitmentFundType.toString())) {
			i=2;
		} 
		String xpath="(//img[@title='Limited Partner Lookup (New Window)'])["+i+"]";
		return isDisplayed(driver, FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "select LegalName LookUpIcon On "+pageName.toString());
	}
	
	
	@FindBy(xpath="(//img[@title='Company Lookup (New Window)'])[2]")
	private WebElement selectCompanyNameLookUpIconOnCreateCommitmentPopUp;

	/**
	 * @return the selectCompanyNameLookUpIconOnCreateCommitmentPopUp
	 */
	public WebElement getSelectCompanyNameLookUpIconOnCreateCommitmentPopUp(int timeOut) {
		return isDisplayed(driver, selectCompanyNameLookUpIconOnCreateCommitmentPopUp, "Visibility", timeOut, "select Company Name LookUpIcon On Create Commitment PopUp");
	}
	
	@FindBy(xpath="//h2[text()='General Information']/..//table//label[text()='Company']/../following-sibling::td/span")
	private WebElement CompanyNameLabelTextOnCreateCommitment;
	
	
	/**
	 * @return the companyNameLabelTextOnCreateCommitment
	 */
	public WebElement getCompanyNameLabelTextOnCreateCommitment(int timeOut) {
		return isDisplayed(driver, CompanyNameLabelTextOnCreateCommitment, "Visibility", timeOut, "Company Name Label Text On Create Commitment");
	}

	@FindBy(xpath="//a[@title='Log Multiple Commitments']")
	private WebElement logMultipleCommitmentsLink;

	/**
	 * @return the logMultipleCommitmentsLink
	 */
	public WebElement getLogMultipleCommitmentsLink(int timeOut) {
		return isDisplayed(driver, logMultipleCommitmentsLink, "Visibility", timeOut, "Log Multiple Commitments Link");
	}
	
	@FindBy(xpath="//select[@class='navpeii__partner_type__c slds-select']")
	private WebElement partnerTypeDropDownList;

	/**
	 * @return the partnerTypeDropDownList
	 */
	public WebElement getPartnerTypeDropDownList(int timeOut) {
		return isDisplayed(driver, partnerTypeDropDownList, "Visibility", timeOut, "partner Type Drop Down List");
	}
	
	@FindBy(xpath="//select[@class='navpeii__tax_forms__c slds-select']")
	private WebElement taxformsDropDownList;

	/**
	 * @return the taxformsDropDownList
	 */
	public WebElement getTaxformsDropDownList(int timeOut) {
		return isDisplayed(driver, taxformsDropDownList, "Visibility", timeOut, "tax forms drop down list");
	}
	
	@FindBy(xpath="//input[@class='inputTextClass navpeii__placement_fee__c']")
	private WebElement placementFeeTextBox;
	
	
	/**
	 * @return the placementFreeTextBox
	 */
	public WebElement getPlacementFeeTextBox(int timeOut) {
		return isDisplayed(driver, placementFeeTextBox, "Visibility", timeOut, "placement fee text box");
	}

	@FindBy(xpath="//iframe")
	private WebElement createCommitmentFrame_Lightning;

	/**
	 * @return the createCommitmentFrame_Lightning
	 */
	public WebElement getCreateCommitmentFrame_Lightning(int timeOut) {
		return isDisplayed(driver, createCommitmentFrame_Lightning, "Visibility", timeOut, "create Commitment Frame Lightning");
	}
	
	@FindBy(xpath="//span[@id='mypage:CommitmentCreationFormId:totalCommitmentId']")
	private WebElement totalCommitmentAmount;

	/**
	 * @return the totalCommitmentAmount
	 */
	public WebElement getTotalCommitmentAmount(int timeOut) {
		return isDisplayed(driver, totalCommitmentAmount, "Visibility", timeOut, "total commitment amount");
	}
	
	@FindBy(xpath="(//a[text()='Create Commitment'])[2]")
	private WebElement createCommitmentBtn;

	/**
	 * @param topOrBottom TODO
	 * @return the createCommitmentBtn
	 */
	public WebElement getCreateCommitmentBtn(int timeOut, TopOrBottom topOrBottom) {
		
		String xpath ;
		WebElement ele;
		if (TopOrBottom.TOP.toString().equalsIgnoreCase(topOrBottom.toString())) {
			xpath = "(//a[text()='Create Commitment'])[1]";
		} else {
			xpath = "(//a[text()='Create Commitment'])[2]";
		}
		ele = FindElement(driver, xpath, "create commitment button : "+topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "create commitment button : "+topOrBottom);
	}
	
	@FindBy(xpath="//div[contains(@class,'Conformation_Fancybox')]//a")
	private WebElement createCommitmentOkBtn;

	/**
	 * @return the createCommitmentOkBtn
	 */
	public WebElement getCreateCommitmentOkBtn(int timeOut) {
		return isDisplayed(driver, createCommitmentOkBtn, "Visibility", timeOut, "create Commitment Ok Button");
	}
	
	@FindBy(xpath="(//span[@id='pageBulkEmail:frmBulkEmail:pgMsg']/..//div/div[@class='float_l'])[1]")
	private WebElement step1BuildYourOwnDistribution;

	/**
	 * @return the step1_BuildYourOwnDistribution
	 */
	public WebElement getStep1BuildYourOwnDistribution(String environment,String mode,int timeOut) {
		return isDisplayed(driver, step1BuildYourOwnDistribution, "Visibility", timeOut, "step1 Build Your Own Distribution");
	}
	
	@FindBy(xpath="//div[contains(@class,'ErrorMessage')]/div[@id='iderrordiv']")
	private WebElement bulkEmailErrorPopUp;

	/**
	 * @return the bulkEmailErrorPopUp
	 */
	public WebElement getBulkEmailErrorPopUp(String environment,String mode,int timeOut) {
		return isDisplayed(driver, bulkEmailErrorPopUp, "Visibility", timeOut, "Bulk Email Error PopUp");
	}
	
	@FindBy(xpath="//div[contains(@class,'ErrorMessage')]//div/button[text()='OK']")
	private WebElement bulkEmailErrorPopUpOkBtn;

	/**
	 * @return the bulkEmailErrorPopUpOkBtn
	 */
	public WebElement getBulkEmailErrorPopUpOkBtn(String environment,String mode,int timeOut) {
		return isDisplayed(driver, bulkEmailErrorPopUpOkBtn, "Visibility", timeOut, "Bulk Email Error PopUp Ok Button");
	}
	
	@FindBy(xpath="//input[@id='pageBulkEmail:frmBulkEmail:SearchContentReport']")
	private WebElement searchForAReportsTextBox;

	/**
	 * @return the searchForAReportsTextBox
	 */
	public WebElement getSearchForAReportsTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, searchForAReportsTextBox, "Visibility", timeOut, "search For A Reports TextBox");
	}
	
	@FindBy(xpath="//label[@id='total_records']")
	private WebElement recordsOnBulkEmail;

	/**
	 * @return the recordsOnBulkEmail
	 */
	public WebElement getRecordsOnBulkEmail(String environment,String mode,int timeOut) {
		return isDisplayed(driver, recordsOnBulkEmail, "Visibility", timeOut, "Records On BulkEmail");
	}
	
	@FindBy(xpath="//input[@id='pageBulkEmail:frmBulkEmail:SearchContent']")
	private WebElement searchForAContactTextBox;

	/**
	 * @return the searchForAContactTextBox
	 */
	public WebElement getSearchForAContactTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, searchForAContactTextBox, "Visibility", timeOut, "search For A Contact TextBox");
	}
	
	@FindBy(xpath="//input[@id='pageBulkEmail:frmBulkEmail:SearchContent']/../div/img")
	private WebElement searchIconForAContactTextBox;

	/**
	 * @return the searchIconForAContactTextBox
	 */
	public WebElement getSearchIconForAContactTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, searchIconForAContactTextBox, "Visibility", timeOut, "search Icon For A Contact TextBox");
	}
	
	@FindBy(xpath="(//div[@class='step_3']/div[2]/div)[1]")
	private WebElement step3_BulkEmailPage;

	/**
	 * @return the step3_BulkEmailPage
	 */
	public WebElement step3_BulkEmailPage(String environment,String mode,int timeOut) {
		return isDisplayed(driver, step3_BulkEmailPage, "Visibility", timeOut, "Step3 Bulk Email Page");
	}
	
	@FindBy(xpath="//div[@class='step_3']/div[3]/span")
	private WebElement bulkEmailSelectedRecipientErrorMsg;

	/**
	 * @return the bulkEmailSelectedRecipientErrorMsg
	 */
	public WebElement getBulkEmailSelectedRecipientErrorMsg(String environment,String mode,int timeOut) {
		return isDisplayed(driver, bulkEmailSelectedRecipientErrorMsg, "Visibility", timeOut, "Bulk Email step 3 selected Recipient error message");
	}
	
	public List<WebElement> getBulkEmailProcessingOptionsLableTextList(){
		return FindElements(driver, "(//h3[text()='Processing Options']/following-sibling::table)[1]//td[@class='td1']", "email prospect step 3 processing options lable list");
	}
	
	public List<WebElement> getBulkEmailProcessingOptionsCheckBoxList(){
		return FindElements(driver, "(//h3[text()='Processing Options']/following-sibling::table)[1]//td[@class='td2']//input", "email prospect step 3 processing options check box list");
	}
	
	@FindBy(xpath="//div[@class='step_4']/div[1]/div[1]")
	private WebElement step4_BulkEmailPage;

	/**
	 * @return the step3_BulkEmailPage
	 */
	public WebElement step4_BulkEmailPage(String environment,String mode,int timeOut) {
		return isDisplayed(driver, step4_BulkEmailPage, "Visibility", timeOut, "Step4 Bulk Email Page");
	}
	
	
	@FindBy(xpath="//div[@class='step_2']/div[1]/div[1]")
	private WebElement step2_BulkEmailPage;

	/**
	 * @return the step3_BulkEmailPage
	 */
	public WebElement step2_BulkEmailPage(String environment,String mode,int timeOut) {
		return isDisplayed(driver, step2_BulkEmailPage, "Visibility", timeOut, "Step2 Bulk Email Page");
	}
	
	@FindBy(xpath="//select[@id='pageBulkEmail:frmBulkEmail:sl1']")
	private WebElement bulkEmailFolderDropDownList;

	/**
	 * @return the bulkEmailFolderDropDownList
	 */
	public WebElement getBulkEmailFolderDropDownList(String environment,String mode,int timeOut) {
		return isDisplayed(driver, bulkEmailFolderDropDownList, "Visibility", timeOut, "Bulk email folder drop downlist");
	}
	
	@FindBy(xpath="//a[@id='LimitedPartnerAddId']")
	private WebElement newLimitedPartnerAddBtnInCreateCommitment;

	/**
	 * @return the newLimitedPartnerAddBtn
	 */
	public WebElement getNewLimitedPartnerAddBtnInCreateCommitment(int timeOut) {
		return isDisplayed(driver, newLimitedPartnerAddBtnInCreateCommitment, "Visibility", timeOut, "new limited partner add button in create commitment page");
	}
	
	@FindBy(xpath="//div[contains(@class,'LimitedPartner_Fancybox')]//a[text()='Cancel']")
	private WebElement newLimitedPartnerCancelBtnInCreateCommitment;
	
	/**
	 * @return the newLimitedPartnerCancelBtnInCreateCommitment
	 */
	public WebElement getNewLimitedPartnerCancelBtnInCreateCommitment(int timeOut) {
		return isDisplayed(driver, newLimitedPartnerCancelBtnInCreateCommitment, "Visibility", timeOut, "new limited partner cancel button in create commitment page");
	}

	@FindBy(xpath="//div[contains(@class,'NewPartnership_Fancybox')]//a[text()='Add']")
	private WebElement newPartnerShipAddbtnInCreateCommitment;

	/**
	 * @return the newPartnerShipAddbtnInCreateCommitment
	 */
	public WebElement getNewPartnerShipAddbtnInCreateCommitment(int timeOut) {
		return isDisplayed(driver, newPartnerShipAddbtnInCreateCommitment, "Visibility", timeOut, "new partnership add button in create commitment page");
	}
	
	@FindBy(xpath="//div[contains(@class,'NewPartnership_Fancybox')]//a[text()='Cancel']")
	private WebElement newPartnerShipCancelbtnInCreateCommitment;

	/**
	 * @return the newPartnerShipCancelbtnInCreateCommitment
	 */
	public WebElement getNewPartnerShipCancelbtnInCreateCommitment(int timeOut) {
		return isDisplayed(driver, newPartnerShipCancelbtnInCreateCommitment, "Visibility", timeOut, "new partnership cancel button in create commitment page");
	}
	
	@FindBy(xpath="(//form[@id='mypage:CommitmentCreationFormId']//div[contains(@class,'InsufficientPermissions')])[1]//div[2]")
	private WebElement InsufficientPermissionErrorMsgOnCreateCommitmentPage;

	/**
	 * @return the insufficientPermissionErrorMsgOnCreateCommitmentPage
	 */
	public WebElement getInsufficientPermissionErrorMsgOnCreateCommitmentPage(int timeOut) {
		return isDisplayed(driver, InsufficientPermissionErrorMsgOnCreateCommitmentPage, "Visibility", timeOut, "Insufficient Permission Error Msg On Create Commitment Page");
	}
	
	@FindBy(xpath="(//form[@id='mypage:CommitmentCreationFormId']//div[contains(@class,'InsufficientPermissions')])[1]//a[text()='OK']")
	private WebElement InsufficientPermissionOKBtnOnCreateCommitmentPage;

	/**
	 * @return the insufficientPermissionOKBtnOnCreateCommitmentPage
	 */
	public WebElement getInsufficientPermissionOKBtnOnCreateCommitmentPage(int timeOut) {
		return isDisplayed(driver, InsufficientPermissionOKBtnOnCreateCommitmentPage, "Visibility", timeOut, "Insufficient Permission OK button On Create Commitment Page");
	}
	
	@FindBy(xpath="//span[text()='Click to Open Sidebar']/following-sibling::span[1]")
	private WebElement openSideBar;

	/**
	 * @return the openSideBar
	 */
	public WebElement getOpenSideBar(int timeOut) {
		return isDisplayed(driver, openSideBar, "Visibility", timeOut, "Open side bar");
	}
	
	@FindBy(xpath="//span[text()='Click to Close Sidebar']/following-sibling::span[1]")
	private WebElement closeSideBar;

	/**
	 * @return the closeSideBar
	 */
	public WebElement getCloseSideBar(int timeOut) {
		return isDisplayed(driver, closeSideBar, "Visibility", timeOut, "Open side bar");
	}
	
	
	
	@FindBy(xpath="(//div[@class='step_3']/div[1]/div)[1]")
	private WebElement step3_EmailInvestorDistribution;

	/**
	 * @return the step3_BulkEmailPage
	 */
	public WebElement getStep3_EmailInvestorDistributione(String environment,String mode,int timeOut) {
		return isDisplayed(driver, step3_EmailInvestorDistribution, "Visibility", timeOut, "step3 Email Investor Distribution");
	}
	

	@FindBy(xpath="//h2[text()='Commitment Creation']/../div[@id='CloseIcon']")
	private WebElement commitmentCreationCloseIcon;

	/**
	 * @return the step3_BulkEmailPage
	 */
	public WebElement getCommitmentCreationCloseIcon(String environment,String mode,int timeOut) {
		return isDisplayed(driver, commitmentCreationCloseIcon, "Visibility", timeOut, "commitment Creation Close Icon");
	}
	
	@FindBy(xpath="//div[@class='slds-modal__footer popup1']//a[text()='Cancel']")
	private WebElement commitmentCreationCancelButton;

	/**
	 * @return the step3_BulkEmailPage
	 */
	public WebElement getCommitmentCreationCancelButton(String environment,String mode,int timeOut) {
		return isDisplayed(driver, commitmentCreationCancelButton, "Visibility", timeOut, "commitment Creation Cancel Button");
	}
	
	@FindBy(xpath="//div[contains(@class,'LimitedPartner_Fancybox')]//a[@title='Close']")
	private WebElement newLimitedPartnerCrossIconInCreateCommitment;
	
	/**
	 * @return the newLimitedPartnerCancelBtnInCreateCommitment
	 */
	public WebElement getNewLimitedPartnerCrossIconInCreateCommitment(int timeOut) {
		return isDisplayed(driver, newLimitedPartnerCrossIconInCreateCommitment, "Visibility", timeOut, "new limited partner Cross Iconin create commitment page");
	}

	
	
	
	public List<WebElement> getCreateFundraisingFilterRemoveBtn(){
		return FindElements(driver, "//a[@title='Remove Row']", "remove icon");
	}
	
	@FindBy(xpath="//a[@id='j_id0:CreateFundraisingFormId:cmdlink']")
	private WebElement addFilterLogicLinkOnCreateFundraising;

	/**
	 * @return the selectAReportsErrorMsg
	 */
	public WebElement getAddFilterLogicLinkOnCreateFundraising(int timeOut) {
		return isDisplayed(driver, addFilterLogicLinkOnCreateFundraising, "Visibility", timeOut, "Add Filter Logic Link");
	}
	
	
	
	
	@FindBy(xpath="//a[@title='Clear'][contains(@onclick,'rfrshPnlMthd(glblTabId)')]")
	private WebElement CreateFundraisingClearBtn;

	/**
	 * @return the addProspectClearBtn
	 */
	public WebElement getCreateFundraisingClearBtn(int timeOut) {
		return isDisplayed(driver, CreateFundraisingClearBtn, "Visibility", timeOut, "Email Prospect Clear Btn");
	}
	
	
	
	
	@FindBy(xpath="//div[@id='wrenchpopup1wrenchCMF']//button[@title='Cancel']")
	private WebElement CreateFundraisingwrenchIconCancelBtn;

	/**
	 * @return the infoLink
	 */
	public WebElement getCreateFundraisingwrenchIconCancelBtn(int timeOut) {
		return isDisplayed(driver, CreateFundraisingwrenchIconCancelBtn, "Visibility", timeOut, "Wrench Icon Cancel Btn");
	}
	

	@FindBy(xpath="//div[@id='HomePopup']//div[@class='slds-modal__footer popup3']/a[@title='Cancel']")
	private WebElement fundTypeCommitmentCreationCancelBtn;

	/**
	 * @return the fundTypeCommitment
	 */
	public WebElement getFundTypeCommitmentCreationCancelBtn(int timeOut) {
		return isDisplayed(driver, fundTypeCommitmentCreationCancelBtn, "Visibility", timeOut, "fund type commitment Creation Cancel Btn");
	}

	
	
	@FindBy(xpath="//div[@onclick='searchGridSelectContact();']/img")
	private WebElement fundraisingContactSearchTextSearchIcon;

	/**
	 * @return the fundraisingContactSearchTextSearchIcon
	 */
	public WebElement getFundraisingContactSearchTextSearchIcon(int timeOut) {
		return isDisplayed(driver, fundraisingContactSearchTextSearchIcon, "Visibility", timeOut, "fundraising Contact Search Text Search Icon");
	}
	
	
	@FindBy(id="j_id0:CreateFundraisingFormId:j_id37")
	private WebElement createFundraisingPopUpCancelBtn;

	/**
	 * @return the createFundraisingPopUpCancelBtn
	 */
	public WebElement getCreateFundraisingPopUpCancelBtn(int timeOut) {
		return isDisplayed(driver, createFundraisingPopUpCancelBtn, "Visibility", timeOut, "create Fundraising PopUp Cancel Btn");
	}
	
	@FindBy(xpath="//a[contains(@onclick,'j_id0:CreateFundraisingFormId:lookupIdComWrng')]/img")
	private WebElement createFundraisingPopUpCrossIcon;

	/**
	 * @return the createFundraisingPopUpCrossIcon
	 */
	public WebElement getCreateFundraisingPopUpCrossIcon(int timeOut) {
		return isDisplayed(driver, createFundraisingPopUpCrossIcon, "Visibility", timeOut, "create Fundraising PopUp CrossIcon");
	}
	
	@FindBy(id="AddRow1")
	private WebElement createFundraisingDefaultFundraisingValuesAddRowsLink;

	/**
	 * @return the createFundraisingDefaultFundraisingValuesAddRowsLink
	 */
	public WebElement getCreateFundraisingDefaultFundraisingValuesAddRowsLink(int timeOut) {
		return isDisplayed(driver, createFundraisingDefaultFundraisingValuesAddRowsLink, "Visibility", timeOut, "create Fundraising Default Fundraising Values AddRows Link");
	}
	
	@FindBy(xpath="//div[@class='flexipagePage']//span[text()='Minimize']")
	private WebElement navatarQuickLinkMinimize_Lighting;

	/**
	 * @return the navatarQuickLinkMinimize_Lighting
	 */
	public WebElement getNavatarQuickLinkMinimize_Lighting(String environment,int timeOut) {
		return isDisplayed(driver, navatarQuickLinkMinimize_Lighting, "Visibility", timeOut, "Navatar Quick Link Minimize Lighting");
	}
	

	public WebElement getCreateFundraisingBtn(PageName popNameOrPageName, int timeOut) {
		if(popNameOrPageName.toString().equalsIgnoreCase(PageName.WarningPopUp.toString())) {
			return isDisplayed(driver, createFundraisingBtnOnWarningPopUp, "Visibility", timeOut, "create fundraising button");
		}else {
			return isDisplayed(driver, createFundraisingBtn, "Visibility", timeOut, "create fundraising button");
		}
	}
	
	@FindBy(xpath="//input[@title='Create Deal']")
	private WebElement createDealBtn;

	/**
	 * @return the createDealBtn
	 */
	public WebElement getCreateDealBtn(String environment,String mode,int timeOut) {
		return isDisplayed(driver, createDealBtn, "Visibility", timeOut, "Create Deal Button");
	}
	
	
	
	@FindBy(xpath="//div[@class='slds-media__body']//h1//lightning-formatted-text")
	private WebElement pipeLineNameInViewMode_Lighting;

	/**
	 * @return the fundNameLabel
	 */
	public WebElement getPipelineNameInViewMode(String environment,String mode,int timeOut) {
		
			return isDisplayed(driver, pipeLineNameInViewMode_Lighting, "Visibility", timeOut, "Pipe Line Name in View Mode Lighting");
		
		
	}

	@FindBy(xpath="//label[text()='Company Name']/../following-sibling::td/input")
	private WebElement companyNameTextBox;

	/**
	 * @return the companyNameTextBox
	 */
	public WebElement getCompanyNameTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, companyNameTextBox, "Visibility", timeOut, "Company Name TextBox");
	}
	
	@FindBy(xpath="//label[text()='Deal Name']/../following-sibling::td/input")
	private WebElement dealNameTextBox;

	/**
	 * @return the dealNameTextBox
	 */
	public WebElement getDealNameTextBox(String environment,String mode,int timeOut) {
		return isDisplayed(driver, dealNameTextBox, "Visibility", timeOut, "Deal Name TextBox");
	}
	
	@FindBy(xpath="//select[@id='thePage:theForm:pipelinestage']")
	private WebElement stageDropDownList;

	/**
	 * @return the stageDropDownList
	 */
	public WebElement getStageDropDownList(String environment,String mode,int timeOut) {
		return isDisplayed(driver, stageDropDownList, "Visibility", timeOut, "Stage Drop Down List");
	}
	
	@FindBy(xpath="//iframe[@title='Individual Investor Creation']")
	private WebElement createCommitmentFrame_Lighting;

	/**
	 * @return the createCommitmentFrame_Lighting
	 */
	public WebElement getCreateCommitmentFrame_Lighting(int timeOut) {
		ThreadSleep(20000);
        String xpath ="//iframe";
        List<WebElement> elelist = FindElements(driver, xpath, "frame");
        for (WebElement webElement : elelist) {
            webElement = isDisplayed(driver, webElement, "Visibility", timeOut, "create commitment frame in lighting");;
            if (webElement!=null) {
                return webElement;
            }
        }
        
		return isDisplayed(driver, createCommitmentFrame_Lighting, "Visibility", timeOut, "create commitment frame in lighting");
	
	}
	
	
	@FindBy(xpath="//a[text()='Copy Mailing Address to Other Address']")
	private WebElement coptyMailingAddressToOther;

	/**
	 * @return the coptyMailingAddressToOther
	 */
	public WebElement getCoptyMailingAddressToOther(int timeOut) {
		return isDisplayed(driver, coptyMailingAddressToOther, "Visibility", timeOut, "Copy address to other");
	}
	
	public WebElement getCreateIndiviualInvestorBtn(String environment, String mode, TopOrBottom topOrBottom,int timeOut) {
		String xpath ;
		WebElement ele;
		if (TopOrBottom.TOP.toString().equalsIgnoreCase(topOrBottom.toString())) {
			xpath = "(//input[@value='Create Individual Investor'])[1]";
		} else {
			xpath = "(//input[@value='Create Individual Investor'])[2]";
		}
		ele = FindElement(driver, xpath, "create indiviual investor button : "+topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
				
		return isDisplayed(driver, ele, "Visibility", timeOut, " create indiviual investor button : "+topOrBottom);
	}
	

	public List<String> getNavigationMenuitem(){
		ThreadSleep(10000);
	List<String> NavigationMenuitem=new LinkedList<String>();
		 List<WebElement> eleList= FindElements(driver, "//div/button/span[contains(@class,'itemTitle')]", "Navigation Menu Item List");
		 if (!eleList.isEmpty()) {
			 for (WebElement webElement : eleList) {
				 NavigationMenuitem.add(webElement.getText().trim());
				 System.err.println(webElement.getText().trim());
			}
			
		} else {

		}
		return NavigationMenuitem;
	}
	

	
	public WebElement sdgGridHeaderName(SDGGridName sdgGridName, int timeOut) {
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']";
		WebElement ele = FindElement(driver, xpath, "SDG grid header label name "+sdgGridName, action.SCROLLANDBOOLEAN, timeOut);
		
		return isDisplayed(driver, ele, "Visibility", timeOut, "SDG grid header label name "+sdgGridName);
	}
	
	public WebElement sdgGridExpandCollpaseIcon(SDGGridName sdgGridName,CollapseExpandIcon collapseExpandIcon, int timeOut) {
		String a="";
		if(collapseExpandIcon.toString().equalsIgnoreCase(CollapseExpandIcon.Expand.toString())) {
			a ="Collapse";
		}else {
			a="Expand";
		}
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']/ancestor::div[contains(@class,'sdgborder')]/*[@title='"+a+"']";
		WebElement ele = FindElement(driver, xpath, "SDG grid "+collapseExpandIcon, action.SCROLLANDBOOLEAN, timeOut);
		
		return isDisplayed(driver, ele, "Visibility", timeOut, "SDG grid "+collapseExpandIcon);
	}
	
	public WebElement sdgGridSideIcons(SDGGridName sdgGridName,SDGGridSideIcons sdgGridSideIcons, int timeOut) {
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']/../../../following-sibling::div//*[contains(@title,'"+sdgGridSideIcons+"')]";
		WebElement ele = FindElement(driver, xpath, "SDG grid side icon "+sdgGridSideIcons, action.SCROLLANDBOOLEAN, timeOut);
		
		return isDisplayed(driver, ele, "Visibility", timeOut, "SDG grid side icon "+sdgGridSideIcons);
	}
	
	
	public List<WebElement> sdgGridHeadersLabelNameList(SDGGridName sdgGridName) {
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']/../../../../../following-sibling::div//table/thead/tr/th";
		List<WebElement> ele = FindElements(driver, xpath, "SDG grid header label name "+sdgGridName);
		
		return ele;
	}
	
	public List<WebElement> sdgGridHeadersLabelNameListForSorting(SDGGridName sdgGridName) {
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']/../../../../../following-sibling::div//table/thead/tr/th/div";
		List<WebElement> ele = FindElements(driver, xpath, "SDG grid header label name "+sdgGridName);
		
		return ele;
	}
	
	public List<WebElement> sdgGridHeadersDealsGridDealColumnsDataList(int DealsCloumnIndex) {
		//index start from 2 in Deal SDG grid.
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='Deals']/../../../../../following-sibling::div//table/tbody/tr/td["+DealsCloumnIndex+"]";
		List<WebElement> ele = FindElements(driver, xpath, "SDG grid Deal column data in deals grid ");
		
		return ele;
	}
	
	
	public List<WebElement> sdgGridHeadersDealsNameList() {
		//index start from 2 in Deal SDG grid.
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='Deals']/../../../../../following-sibling::div//table/tbody/tr/td[2]//a";
		List<WebElement> ele = FindElements(driver, xpath, "SDG grid Deal column data in deals grid ");
		
		return ele;
	}
	
	public WebElement sdgGridSideDealStageColumnDropDownListInEditMode(SDGGridName sdgGridName, int timeOut) {
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']/../../../../../following-sibling::div//table/tbody/tr/td[3]//section//input/../div/*";
		WebElement ele = FindElement(driver, xpath, "deal SDG grid stage drop down list in edit mode", action.SCROLLANDBOOLEAN, timeOut);
		
		return isDisplayed(driver, ele, "Visibility", timeOut, "deal SDG grid stage drop down list in edit mode");
	}
	
public WebElement sdgGridSideDealStageColumnUpdateSelecteditemsCheckBox(SDGGridName sdgGridName,int numberOfRecordsUpdating,int columnIndexNumber, int timeOut) {
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']/../../../../../following-sibling::div//table/tbody/tr/td["+columnIndexNumber+"]//section/div/*//label/span[text()='Update "+numberOfRecordsUpdating+" selected items']/preceding-sibling::span";
		WebElement ele = FindElement(driver, xpath, "deal SDG grid stage Update 3 selected items check box in edit mode", action.SCROLLANDBOOLEAN, timeOut);
		
		return isDisplayed(driver, ele, "Visibility", timeOut, "deal SDG grid stage Update 3 selected items check box in edit mode");
	}
	
	
	
	
	
	public List<WebElement> sdgGridHeadersDealsAndFundRaisingStageColumnList(SDGGridName sdgGridName) {
		//index start from 2 in Deal SDG grid.
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']/../../../../../following-sibling::div//table/tbody/tr/td[3]/span/span[@class='slds-truncate']/*";
		List<WebElement> ele = FindElements(driver, xpath, "SDG grid Stage column data in deals grid ");
		
		return ele;
	}
	
	
	
	
	
	public List<WebElement> sdgGridCheckBoxList(SDGGridName sdgGridName) {
		//index start from 2 in Deal SDG grid.
		
//		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']/../../../../../following-sibling::div//table/tbody/tr/td[1]//div//label/span[1]";
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']/../../../../../following-sibling::div//table/tbody/tr/td[1]//div//input";
		
		
		
		List<WebElement> ele = FindElements(driver, xpath, "SDG grid column Check Box data in grid ");
		
		return ele;
	}
	
	public List<WebElement> sdgGridHeadersDealsNameListForToolTip() {
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='Deals']/../../../../../following-sibling::div//table/tbody/tr/td[2]//a/..";
		List<WebElement> ele = FindElements(driver, xpath, "SDG grid Deal column data in deals grid ");
		
		return ele;
	}
	
	
	
	public List<WebElement> sdgGridHeadersFundRaisingsFundraisingColumnsDataList(int DealsCloumnIndex) {
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='Fundraising']/../../../../../following-sibling::div//table/tbody/tr/td["+DealsCloumnIndex+"]";
		List<WebElement> ele = FindElements(driver, xpath, "SDG grid fundraising column data in fundraising grid ");
		
		return ele;
	}
	
public List<WebElement> sdgGridHeadersFundRaisingsFundraisingNameList() {
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='Fundraising']/../../../../../following-sibling::div//table/tbody/tr/td[2]//a";
		List<WebElement> ele = FindElements(driver, xpath, "SDG grid fundraising column data in fundraising grid ");
		
		return ele;
	}

public List<WebElement> sdgGridHeadersFundRaisingsFundraisingNameListForToolTip() {
	
	String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='Fundraising']/../../../../../following-sibling::div//table/tbody/tr/td[2]//a/..";
	List<WebElement> ele = FindElements(driver, xpath, "SDG grid fundraising column data in fundraising grid ");
	
	return ele;
}

public List<WebElement> sdgGridHeadersFundRaisingClosingColumnList(SDGGridName sdgGridName) {
	//index start from 2 in Deal SDG grid.
	
	String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']/../../../../../following-sibling::div//table/tbody/tr/td[4]/span/span[@class='slds-truncate']/*";
	List<WebElement> ele = FindElements(driver, xpath, "SDG grid Stage column data in deals grid ");
	
	return ele;
}

public WebElement sdgGridSideFundRaisingClosingColumnDropDownListInEditMode(SDGGridName sdgGridName, int timeOut) {
	
	String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']/../../../../../following-sibling::div//table/tbody/tr/td[4]//section//input/../div/*";
	WebElement ele = FindElement(driver, xpath, "deal SDG grid stage drop down list in edit mode", action.SCROLLANDBOOLEAN, timeOut);
	
	return isDisplayed(driver, ele, "Visibility", timeOut, "deal SDG grid stage drop down list in edit mode");
}
	
	
	public List<WebElement> sdgGridHeadersMyCallListNameColumnsDataList(int DealsCloumnIndex) {
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='My Call List']/../../../../../following-sibling::div//table/tbody/tr/td["+DealsCloumnIndex+"]";
		List<WebElement> ele = FindElements(driver, xpath, "SDG grid Name column data in My Call List grid ");
		
		return ele;
	}
	
	
public List<WebElement> sdgGridHeadersMyCallListNameList() {
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='My Call List']/../../../../../following-sibling::div//table/tbody/tr/td[2]//a";
		List<WebElement> ele = FindElements(driver, xpath, "SDG grid Name column data in My Call List grid ");
		
		return ele;
	}


public List<WebElement> sdgGridHeadersCreateButtonList(SDGGridName sdgName, String buttonName) {
	
	String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgName+"']/../../../../../following-sibling::div//table/tbody/tr/td//button[text()='"+buttonName+"']";
	List<WebElement> ele = FindElements(driver, xpath, "SDG grid Name column data in My Call List grid ");
	
	return ele;
}



public List<WebElement> sdgGridHeadersMyCallListFirmList() {
	
	String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='My Call List']/../../../../../following-sibling::div//table/tbody/tr/td[3]//a";
	List<WebElement> ele = FindElements(driver, xpath, "SDG grid Firm Name column data in My Call List grid ");
	
	return ele;
}

public List<WebElement> sdgGridHeadersMyCallListNameListForToolTip() {
	
	String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='My Call List']/../../../../../following-sibling::div//table/tbody/tr/td[2]//a/..";
	List<WebElement> ele = FindElements(driver, xpath, "SDG grid Firm Name column data in My Call List grid ");
	
	return ele;
}


public List<WebElement> sdgGridHeadersMyCallListPhoneList() {
	
	String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='My Call List']/../../../../../following-sibling::div//table/tbody/tr/td[4]//a";
	List<WebElement> ele = FindElements(driver, xpath, "SDG grid Name column data in My Call List grid ");
	
	return ele;
}
	
public List<WebElement> sdgGridHeadersMyCallListNameCallLogList() {
		
		String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='My Call List']/../../../../../following-sibling::div//table/tbody/tr/td[5]//button";
		List<WebElement> ele = FindElements(driver, xpath, "SDG grid Call log column data in My Call List grid ");
		
		return ele;
	}
	

public WebElement sdgGridPageAndPageSizeofDealFundraisingAndMyCallList(SDGGridName sdgGridName,ActionType actionType, int timeOut) {
	String actions ="";
	if(actionType.toString().equalsIgnoreCase(ActionType.Page.toString())) {
		actions = "PagerPage";
	}else {
		actions = "PagerSize";
	}
	
	String xpath="//div[contains(@data-component-id,'navpeII_sdg')]//a[text()='"+sdgGridName+"']/../../../../../following-sibling::div/div[2]//select[@name='"+actions+"']";
	WebElement ele = FindElement(driver, xpath, "SDG grid page and page size "+sdgGridName, action.SCROLLANDBOOLEAN, timeOut);
	
	return isDisplayed(driver, ele, "Visibility", timeOut, "SDG grid side icon "+sdgGridName);
}

public WebElement sdgGridListInEditMode(SDGGridName sdgGridName, int timeOut) {
	
	
	String xpath="//div[@data-label='Enhanced Lightning Grid']//*[text()='"+sdgGridName+"']/../../../../../../../../../../div[@class='toolbox']";
	WebElement ele = FindElement(driver, xpath, "SDG grid in edit mode "+sdgGridName, action.SCROLLANDBOOLEAN, timeOut);
	
	return ele;
}

@FindBy(xpath = "//label[text()='Select Theme']/following-sibling::div//input")
private WebElement selectThemeinputBoxXpath;

public WebElement getSelectThemeinputBoxButton(int timeOut) {
	return isDisplayed(driver, selectThemeinputBoxXpath, "Visibility", timeOut, "select theme clear button");
}



@FindBy(xpath = "//label[text()='Select Theme']/following-sibling::div//button[@title='Clear Selection']")
private WebElement selectThemeinputBoxClearButtonXpath;

public WebElement getSelectThemeinputBoxClearButton(int timeOut) {
	return isDisplayed(driver, selectThemeinputBoxClearButtonXpath, "Visibility", timeOut, "select theme clear button");
}

public List<WebElement> sdgGridSelectThemeList() {
	
	
	String xpath="//label[text()='Select Theme']/following-sibling::div//*[@role='listbox']//span/span";
	List<WebElement> ele = FindElements(driver, xpath, "SDG grid select theme list ");
	
	return ele;
}



	

public List<WebElement> sdgGridSelectVisibleFieldsListInManageFieldPopUp() {
	
	
	String xpath="//ul[@id='allFields']/li/p";
	List<WebElement> ele = FindElements(driver, xpath, "sdgGridSelectFieldToDisplayInManageFieldPopUp");
	
	return ele;
}


public WebElement sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons saveCancelBtn, int timeOut) {
	String xpath="";
	if(saveCancelBtn.toString().equalsIgnoreCase(Buttons.close.toString())) {
		xpath="(//*[contains(text(),'Select Fields to Display')]/..//button[@title='Close' or text()='Close'])[2]";
	}else if (saveCancelBtn.toString().equalsIgnoreCase(Buttons.Move_Down.toString()) || saveCancelBtn.toString().equalsIgnoreCase(Buttons.Move_Up.toString()) ||
			saveCancelBtn.toString().equalsIgnoreCase(Buttons.Add.toString())) {
		xpath="//button[@title='"+saveCancelBtn+"']";
	}else {
		xpath="//*//button[@title='"+saveCancelBtn+"' or text()='"+saveCancelBtn+"']";
	}
	WebElement ele = FindElement(driver, xpath, "SDG grid SelectFieldToDisplay popup  "+saveCancelBtn, action.SCROLLANDBOOLEAN, timeOut);
	
	return isDisplayed(driver, ele, "Visibility", timeOut, "SDG grid SelectFieldToDisplay popup  "+saveCancelBtn);
}


public WebElement sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp( int timeOut) {
	String xpath="";
	xpath="//*[contains(text(),'Field Finder')]/..//select";
	WebElement ele = FindElement(driver, xpath, "sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp", action.SCROLLANDBOOLEAN, timeOut);
	
	return isDisplayed(driver, ele, "Visibility", timeOut, "sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp");
}






public boolean clickOnEditButtonOnSDGGridOnHomePage(String projectName, String dataName, String field,int timeOut) {
	String xpath="";
	if(field.equalsIgnoreCase("Stage") || field.equalsIgnoreCase("Closing")) {
		xpath="//td[contains(@data-label,'"+field+"')]//*[text()='"+dataName+"']/../following-sibling::span/button";
	}else {
		xpath ="//td[contains(@data-label,'"+field+"')]//*[text()='"+dataName+"']/../../following-sibling::span/button";
	}
	WebElement ele = FindElement(driver, xpath,"edit button for "+field, action.SCROLLANDBOOLEAN, timeOut);
	JavascriptExecutor js = (JavascriptExecutor)driver;
	js.executeScript("return arguments[0].setAttribute('Styles','display: inline-block;')", ele);
	click(driver, ele, "edit", action.BOOLEAN);
	return true;
}

public WebElement sdgGridSideIconsForLightTheme(SDGGridName sdgGridName,SDGGridSideIcons sdgGridSideIcons, int timeOut) {
	String xpath="";
	if(sdgGridSideIcons.toString().equalsIgnoreCase(SDGGridSideIcons.Toggle_Filters.toString())) {
		xpath="//div[contains(@data-component-id,'navpeII_sdg')]//div/div[contains(@class,'sdgborder')]//a[text()='"+sdgGridName+"']/../../../following-sibling::div//button[contains(@title,'"+sdgGridSideIcons+"')]";
	}else if(sdgGridSideIcons.toString().equalsIgnoreCase(SDGGridSideIcons.Side_DropDOwnButtonforLightTheme.toString())){
		xpath="//div[contains(@data-component-id,'navpeII_sdg')]//div/div[contains(@class,'sdgborder')]//a[text()='"+sdgGridName+"']/../../../following-sibling::div//span[text()='Show menu']/..";
	
	}else if(sdgGridSideIcons.toString().equalsIgnoreCase(SDGGridSideIcons.Side_DropDOwnButtonforStandardTheme.toString())){
		xpath="//div[contains(@data-component-id,'navpeII_sdg')]//div/div[contains(@class,'sdgborder')]//a[text()='"+sdgGridName+"']/../../../following-sibling::div//div//span[text()='More options']/..";
	
	}else {
		xpath="//div[contains(@data-component-id,'navpeII_sdg')]//div/div[contains(@class,'sdgborder')]//a[text()='"+sdgGridName+"']/../../../following-sibling::div//div//button/following-sibling::div//a/span[contains(text(),'"+sdgGridSideIcons+"')]";
	}
	WebElement ele = FindElement(driver, xpath, "SDG grid side icon "+sdgGridSideIcons, action.SCROLLANDBOOLEAN, timeOut);
	return isDisplayed(driver, ele, "Visibility", timeOut, "SDG grid side icon "+sdgGridSideIcons);
}


public WebElement sdgGridSideIconsForLightTheme(SDGGridName sdgGridName,String sdgGridSideIcons, int timeOut) {
	String xpath="";
	if(sdgGridSideIcons.toString().equalsIgnoreCase(SDGGridSideIcons.Toggle_Filters.toString())) {
		xpath="//div[contains(@data-component-id,'navpeII_sdg')]//div/div[contains(@class,'sdgborder')]//a[text()='"+sdgGridName+"']/../../../following-sibling::div//button[contains(@title,'"+sdgGridSideIcons+"')]";
	}else if(sdgGridSideIcons.toString().equalsIgnoreCase(SDGGridSideIcons.Side_DropDOwnButtonforLightTheme.toString())){
		xpath="//div[contains(@data-component-id,'navpeII_sdg')]//div/div[contains(@class,'sdgborder')]//a[text()='"+sdgGridName+"']/../../../following-sibling::div//div//span[text()='More options']/..";
	}else {
		xpath="//div[contains(@data-component-id,'navpeII_sdg')]//div/div[contains(@class,'sdgborder')]//a[text()='"+sdgGridName+"']/../../../following-sibling::div//div//button/following-sibling::div//a/span[contains(text(),'"+sdgGridSideIcons+"')]";
	}
	WebElement ele = FindElement(driver, xpath, "SDG grid side icon "+sdgGridSideIcons, action.SCROLLANDBOOLEAN, timeOut);
	return isDisplayed(driver, ele, "Visibility", timeOut, "SDG grid side icon "+sdgGridSideIcons);
}


public WebElement todayTasksAndTodayEventsLabelText(Task taskName, int timeOut) {
	String xpath="";
	if(taskName.toString().equalsIgnoreCase(Task.TodayTasks.toString())) {
		xpath="//h2/span[contains(text(),'Today') and contains(text(),'Tasks')]";
	}else {
		xpath="//h2/span[contains(text(),'Today') and contains(text(),'Events')]";
	}
	WebElement ele = FindElement(driver, xpath, "text label of "+taskName, action.SCROLLANDBOOLEAN, timeOut);
	return isDisplayed(driver, ele, "Visibility", timeOut, "text label of "+taskName);
}

public WebElement todayTasksDownArrow(Task taskName, int timeOut) {
	String xpath="";
	xpath="//h2/span[contains(text(),'Today') and contains(text(),'Tasks')]/../../../..//a";
	WebElement ele = FindElement(driver, xpath, "today tasks down arrow", action.SCROLLANDBOOLEAN, timeOut);
	if(isDisplayed(driver, ele, "Visibility", timeOut, "today tasks down arrow")!=null) {
		return ele;
	}else {
		xpath="//h2/span[contains(text(),'Today') and contains(text(),'Tasks')]/../../../..//button";
		ele = FindElement(driver, xpath, "today tasks down arrow", action.SCROLLANDBOOLEAN, 5);
		return isDisplayed(driver, ele, "Visibility", timeOut, "today tasks down arrow");
	}
}


public WebElement todayTasksDownArrowListValues(TodayTaskDownArrowValues todayTaskDownArrowValues, int timeOut) {
	String xpath="";
	xpath="//div[contains(@class,'uiMenuList--default visible positioned')]//a[text()='"+todayTaskDownArrowValues+"']";
	WebElement ele = FindElement(driver, xpath, "today tasks down arrow value "+todayTaskDownArrowValues, action.SCROLLANDBOOLEAN, timeOut);
	return isDisplayed(driver, ele, "Visibility", timeOut, "today tasks down arrow value "+todayTaskDownArrowValues);
}


public WebElement viewAllAndviewClendarLink(Task taskName, ViewAllAndViewCalendarLink viewAllAndViewCalendarLink, int timeOut) {
	String xpath="";
	if(taskName.toString().equalsIgnoreCase(Task.TodayTasks.toString())) {
		xpath="Tasks";
	}else {
		xpath="Events";
	}
	xpath="//h2/span[contains(text(),'Today') and contains(text(),'"+xpath+"')]/../../../../following-sibling::div/a[@aria-label='"+viewAllAndViewCalendarLink+"']";
	WebElement ele = FindElement(driver, xpath, taskName+" link "+viewAllAndViewCalendarLink, action.SCROLLANDBOOLEAN, timeOut);
	return isDisplayed(driver, ele, "Visibility", timeOut, taskName+" link "+viewAllAndViewCalendarLink);
}
	
@FindBy(xpath = "//span[text()='Status']/../following-sibling::div//a")
private WebElement createTaskStatusDropDown;

public WebElement getCreateTaskStatusDropDown(int timeOut) {
	return isDisplayed(driver, createTaskStatusDropDown, "Visibility", timeOut, "createTaskStatusDropDown");
}

public WebElement createTaskDropDownValue(String value, int timeOut) {
	String xpath="";
	xpath="//div[contains(@class,'visible positioned')]//a[text()='"+value+"']";
	WebElement ele = FindElement(driver, xpath, "create task down arrow value "+value, action.SCROLLANDBOOLEAN, timeOut);
	return isDisplayed(driver, ele, "Visibility", timeOut, "create task down arrow value "+value);
}


public List<WebElement> ctreatedTaskListOnHomePage() {
	
	
	String xpath="//div[@data-aura-class='uiAbstractList']//a/span";
	List<WebElement> ele = FindElements(driver, xpath, "ctreatedTaskListOnHomePage");
	
	return ele;
}

public List<WebElement> ctreatedTaskAndEventListOnHomePage(Task task) {
	String xpath="";
	if(task.toString().equalsIgnoreCase(Task.TodayTasks.toString())) {
		xpath ="//div[@data-aura-class='uiAbstractList']//li/div/a/div//span[contains(@class,'primaryField')]/a";
	}else {
		xpath ="//div[@data-aura-class='uiAbstractList']//li//div[@class='subject columnItem']//a";
	}
	List<WebElement> ele = FindElements(driver, xpath, "ctreatedTaskAndEventListOnHomePage");
	
	return ele;
}



@FindBy(xpath = "//*[text()='All-Day Event']/../following-sibling::input")
private WebElement allDayEventCheckBox;

public WebElement getAllDayEventCheckBox(int timeOut) {
	return isDisplayed(driver, allDayEventCheckBox, "Visibility", timeOut, "allDayEventCheckBox");
}

@FindBy(xpath = "//h1/span[text()='Recently Viewed']")
private WebElement taskPageRecentView;

public WebElement getTaskPageRecentView(int timeOut) {
	return isDisplayed(driver, taskPageRecentView, "Visibility", timeOut, "taskPageRecentView");
}


public List<WebElement> CreateEventsListOnViewClendarPage() {
	
	
	String xpath="//h2[text()='All-Day Events']/following-sibling::ul/li";
	List<WebElement> ele = FindElements(driver, xpath, "ctreatedTaskListOnHomePage");
	
	return ele;
}

@FindBy(xpath = "//h1//div[contains(@class,'slds-page-header__title')]/span")
private WebElement headerNameText;

public WebElement getHeaderNameText(int timeOut) {
	return isDisplayed(driver, headerNameText, "Visibility", timeOut, "headerNameText");
}


public WebElement openSDGRecordOrSettingsPageNewBtnInActions(String GridLabelName,String buttonName,int timeOut) {
	String xpath="//*[text()='"+GridLabelName+"']/../../../following-sibling::div//button[text()='"+buttonName+"']";
	WebElement ele = FindElement(driver, xpath, "new button", action.SCROLLANDBOOLEAN, timeOut);
	return isDisplayed(driver, ele, "Visibility", timeOut, "new button");
}


public WebElement openSDGRecordOrSettingsPageActionPopUptextBoxXpath(String labelName,int timeOut) {
	String xpath="";
	String finalLabel = labelName.replace("_", " ");
	if(finalLabel.equalsIgnoreCase("Event Payload")) {
		xpath="//*[text()='"+finalLabel+"']/following-sibling::*//textarea";
	}else {
		xpath="//*[text()='"+finalLabel+"']/following-sibling::*//input";
	}
	WebElement ele = FindElement(driver, xpath, "new button", action.SCROLLANDBOOLEAN, timeOut);
	return isDisplayed(driver, ele, "Visibility", timeOut, "new button");
}

public WebElement ActionTypeDropDownList(String labelName, int timeOut) {
	
	String finalLabel = labelName.replace("_", " ");
	String xpath="//span[@title='"+finalLabel+"' or text()='"+finalLabel+"']";
	WebElement ele = FindElement(driver, xpath, "new button", action.SCROLLANDBOOLEAN, timeOut);
	return isDisplayed(driver, ele, "Visibility", timeOut, "new button");
}

public WebElement createdButtonEditAndDeleteBtn(String createActionName,String buttonName,int timeOut) {
	String xpath="//*[text()='"+createActionName+"']/../../../../following-sibling::td[@class='actions']//button[text()='"+buttonName+"']";
	WebElement ele = FindElement(driver, xpath, "new button", action.SCROLLANDBOOLEAN, timeOut);
	return isDisplayed(driver, ele, "Visibility", timeOut, "new button");
}


}

