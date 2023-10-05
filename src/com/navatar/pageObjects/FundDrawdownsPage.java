package com.navatar.pageObjects;

import java.util.List;

import static com.navatar.generic.CommonLib.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.action;

public class FundDrawdownsPage extends BasePageBusinessLayer {

	public FundDrawdownsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	@FindBy(xpath = "//input[@value='Send Capital Call Notices']")
	private WebElement sendCapitalCallNotices_Classic;
	
	
	/**
	 * @return the capitalRecallableValue_Classic
	 */
	
	/**
	 * @return the sendCapitalCallNotices_Classic
	 */
	public WebElement getSendCapitalCallNotices_Classic(int timeOut) {
		return isDisplayed(driver,sendCapitalCallNotices_Classic , "visibility", timeOut, "send capital call notices button");
	}
	
	@FindBy(xpath="//div[contains(@class,'iframe')]//iframe[@title='accessibility title']")
	private WebElement accessibilityTitleFrame;

	/**
	 * @return the createfund distribution Frame_Lighting
	 */
	public WebElement getaccessibilityTitleFrame_Lighting(int timeOut) {
		return isDisplayed(driver, accessibilityTitleFrame, "Visibility", timeOut, "accessibilityTitle Frame on lighting");
	}

	@FindBy(xpath = "(//span[@id='contactGrid']//span[contains(@id,'aw') and contains(@id,'-scroll-box')])[1]")
	private WebElement scrollBoxFundDrawdownEmailingGrid;
	
	
	/**
	 * @return the scrollBoxFundDrawdownEmailingGrid
	 */
	public WebElement getScrollBoxStep1FundDrawdownEmailingGrid(int timeOut) {
		return isDisplayed(driver, scrollBoxFundDrawdownEmailingGrid, "Visibility", timeOut, "scrollBoxFundDrawdown");
	}
	
	@FindBy(xpath = "(//div[@class='step_2']//span[contains(@id,'scroll-box')])[1]")
	private WebElement scrollBoxStep2FundDrawdown;
	
	/**
	 * @return the scrollBoxStep2FundDrawdown
	 */
	public WebElement getScrollBoxStep2FundDrawdown(int timeOut) {
		return isDisplayed(driver, scrollBoxStep2FundDrawdown, "Visibility", timeOut, "scrollBoxStep2FundDrawdown");
	}
	public WebElement emailingPreviewTemplateLink(String templateName, int timeOut) {
		String xpath="//span[text()='"+templateName+"']/following-sibling::span/a";
		return  isDisplayed(driver, FindElement(driver,xpath, "", action.BOOLEAN,20), "visibility",20,templateName+" preview link");
	}
	@FindBy(xpath = "//span[contains(@id,'header-0-box')]/span[contains(@id,'header-0-box-marker')]")
	private WebElement step1HeaderCheckbox;
	
	
	/**
	 * @return the step1HeaderCheckbox
	 */
	public WebElement getStep1HeaderCheckbox(int timeOut) {
		return isDisplayed(driver, step1HeaderCheckbox, "Visibility", timeOut, "step1HeaderCheckbox");
	}
	
	@FindBy(xpath = "//div[@class='step_2']//div[contains(text(),'Step 2')]")
	private WebElement step2TextEmailing;
	
	
	/**
	 * @return the step2TextEmailing
	 */
	public WebElement getStep2TextEmailing(int timeOut) {
		return isDisplayed(driver, step2TextEmailing, "Visibility", timeOut, "step2TextEmailing");
			}
	
	@FindBy(xpath = "//select[@name='pageId:formId:pageBlockId:compSendMail:componentId:j_id12']")
	private WebElement fundDrawdownFolderDropDownList;
	
	
	/**
	 * @return the fundDrawdownFolderDropDownList
	 */
	public WebElement getFundDrawdownFolderDropDownList(int timeOut) {
		return isDisplayed(driver, fundDrawdownFolderDropDownList, "Visibility", timeOut, "fundDrawdownFolderDropDownList");
			}
	public List<WebElement> listOfContactNames(int timeOut) {
		return FindElements(driver, "//span[contains(@id,'aw') and contains(@id,'-cell-1')]//a", "contact names list");
	}

	@FindBy(xpath = "//a[@title='Columns to Display']")
	private WebElement wrenchIcon;
	
	
	/**
	 * @return the wrenchIcon
	 */
	public WebElement getWrenchIcon(int timeOut) {
		return isDisplayed(driver, wrenchIcon, "Visibility", timeOut, "wrenchIcon");
}

	
	
	public WebElement getFundDrawdownGridNameColumn(String headerName,int timeOut) {
		String xpath="//span[text()='"+headerName+"']";
		return  isDisplayed(driver, FindElement(driver,xpath, "", action.BOOLEAN,20), "visibility",20,"email prospect contact grid "+headerName+" header name");
	}
	/**
	 * @return the moveToSelected
	 */
	public WebElement getMoveToSelected(PageName pageName,int timeOut) {
		
		String  page ;
		if (PageName.FundDrawdown.toString().equalsIgnoreCase(pageName.toString())) {
			page="DD";
		} else {
			page="FD";
		}
		
		String xpath = "//img[@id='lefttorightenablebulkEmail"+page+"']";
		WebElement ele = FindElement(driver, xpath, "Move To Selected", action.SCROLLANDBOOLEAN, timeOut);
		
		return isDisplayed(driver, ele, "Visibility", timeOut, "moveToSelected");
	}

	@FindBy(xpath = "//a[@id='_applybulkEmailDD']")
	private WebElement applyColumnsToDisplay;
	
	
	/**
	 * @return the applyColumnsToDisplay
	 */
	public WebElement getApplyColumnsToDisplay(int timeOut) {
		return isDisplayed(driver, applyColumnsToDisplay, "Visibility", timeOut, "applyColumnsToDisplay");
	}
	
	@FindBy(xpath = "//a[@id='_applybulkEmailFD']")
	private WebElement applyColumnsToDisplay2;
	
	
	/**
	 * @return the applyColumnsToDisplay
	 */
	public WebElement getApplyColumnsToDisplay2(int timeOut) {
		return isDisplayed(driver, applyColumnsToDisplay2, "Visibility", timeOut, "applyColumnsToDisplay2");
	}

	@FindBy(xpath = "//input[@id='SearchContent']")
	private WebElement searchTextboxEmailingGrid;
	
	
	/**
	 * @return the searchTextboxEmailingGrid
	 */
	public WebElement getSearchTextboxEmailingGrid(int timeOut) {
		return isDisplayed(driver, searchTextboxEmailingGrid, "Visibility", timeOut, "searchTextboxEmailingGrid");
	}

	@FindBy(xpath = "//img[contains(@onclick,'getEnterEvent')]")
	private WebElement searchIconEmailingGrid;
	
	
	/**
	 * @return the searchIconEmailingGrid
	 */
	public WebElement getSearchIconEmailingGrid(int timeOut) {
		return isDisplayed(driver, searchIconEmailingGrid, "Visibility", timeOut, "searchIconEmailingGrid");
	}

	@FindBy(xpath = "//div[@class='step_1']//a[@title='Next']")
	private WebElement step1NextButtonEmailing;
	
	
	/**
	 * @return the step1NextButtonEmailing
	 */
	public WebElement getStep1NextButtonEmailing(int timeOut) {
		return isDisplayed(driver, step1NextButtonEmailing, "Visibility", timeOut, "step1NextButtonEmailing");
	}

	@FindBy(xpath = "//a[@title='Finished']")
	private WebElement finishedButtonEmailing;
	
	
	/**
	 * @return the finishedButtonEmailing
	 */
	public WebElement getFinishedButtonEmailing(int timeOut) {
		return isDisplayed(driver, finishedButtonEmailing, "Visibility", timeOut, "finishedButtonEmailing");
			}
	@FindBy(xpath = "//h2[@class='pageDescription']")
	private WebElement pageDescription;
	
	
	/**
	 * @return the pageDescription
	 */
	public WebElement getPageDescription(int timeOut) {
		return isDisplayed(driver, pageDescription, "Visibility", timeOut, "pageDescription");
			}
	@FindBy(xpath = "//div[@class='step_2']//a[@title='Next']")
	private WebElement step2NextButtonEmailing;
	
	
	/**
	 * @return the step1NextButtonEmailing
	 */
	public WebElement getStep2NextButtonEmailing(int timeOut) {
		return isDisplayed(driver, step2NextButtonEmailing, "Visibility", timeOut, "step2NextButtonEmailing");
	}
	public List<WebElement> getFundDrawdownEmailingProcessingOptionsLableTextList(){
		return FindElements(driver, "//div[@class='step_3']/div[2]/table[1]//tr/td[1]", "email prospect step 3 processing options lable list");
	}
	
	public List<WebElement> getFundDrawdownEmailingProcessingOptionsCheckBoxList1(){
		return FindElements(driver, "//div[@class='step_3']/div[2]/table[1]//tr/td[2]//input", "email prospect step 3 processing options check box list");
	}
	
	public List<WebElement> getFundDrawdownEmailingProcessingOptionsCheckBoxList2(){
		return FindElements(driver, "//div[@class='step_3']/div[2]/table[1]//tr/td[2]//input/following-sibling::span", "email prospect step 3 processing options check box list");
	}
	
	
	
	/**
	 * @return the capitalAmountTextbox
	 */
	public WebElement getCapitalAmountTextboxCreateDrawdown(PercentOrValue pv,int timeOut) {
		String xpath = "";
		if (pv == PercentOrValue.Percent)
			xpath = "Percent";
		else
			xpath = "Amt";
		return isDisplayed(driver, FindElement(driver,"//input[@id='txtCapital"+xpath+"']" , "CapitalAmount textbox", action.BOOLEAN, 30), "visibility", 30, "CapitalAmount textbox");
		}
	
	
	
	/**
	 * @return the managementFeeTextbox
	 */
	public WebElement getManagementFeeTextboxCreateDrawdown(PercentOrValue pv,int timeOut) {
		String xpath = "";
		if (pv == PercentOrValue.Percent)
			xpath = "Percent";
		else
			xpath = "Amt";
		return isDisplayed(driver, FindElement(driver,"//input[@id='txtManagementFee"+xpath+"']" , "ManagementFee textbox", action.BOOLEAN, 30), "visibility", 30, "ManagementFee textbox");
	}
	

	/**
	 * @return the otherFeeTextBox
	 */
	public WebElement getOtherFeeTextBoxCreateDrawdown(PercentOrValue pv,int timeOut) {
		String xpath = "";
		if (pv == PercentOrValue.Percent)
			xpath = "Percent";
		else
			xpath = "Amt";
		return isDisplayed(driver, FindElement(driver,"//input[@id='txtOtherFee"+xpath+"']" , "other fee textbox", action.BOOLEAN, 30), "visibility", 30, "other fee textbox");
	}
	
	@FindBy(xpath = "//span[contains(@id,'capitalcallId')]")
	private WebElement capitalCallValue;
	
	
	/**
	 * @return the capitalCallValue
	 */
	public WebElement getCapitalCallValueCreateDrawdown(int timeOut) {
		return isDisplayed(driver, capitalCallValue, "Visibility", timeOut, "capitalCallValue");
	}
	
	@FindBy(xpath = "//input[contains(@id,'DueDate')]")
	private WebElement dueDateValue;
	
	
	/**
	 * @return the dueDate
	 */
	public WebElement getDueDateValue(int timeOut) {
		return isDisplayed(driver, dueDateValue, "Visibility", timeOut, "dueDateValue");
}
	
	@FindBy(xpath = "//input[contains(@id,'CallDate')]")
	private WebElement callDateTextbox;
	
	
	/**
	 * @return the callDateTextbox
	 */
	public WebElement getCallDateTextbox(int timeOut) {
		return isDisplayed(driver, callDateTextbox, "Visibility", timeOut, "callDateTextbox");
	}
	
	@FindBy(xpath = "//a[contains(@id,'scc')]")
	private WebElement setupCapitalCalls;
	
	
	/**
	 * @return the setupCapitalCalls
	 */
	public WebElement getSetupCapitalCalls(int timeOut) {
		return isDisplayed(driver, setupCapitalCalls, "Visibility", timeOut, "setupCapitalCalls");
			}
	
	@FindBy(xpath = "//a[contains(@id,'btnGCC')]")
	private WebElement generateCapitalCall;
	
	
	/**
	 * @return the generateCapitalCall
	 */
	public WebElement getGenerateCapitalCall(int timeOut) {
		return isDisplayed(driver, generateCapitalCall, "Visibility", timeOut, "generateCapitalCall");
			}
	@FindBy(xpath = "//input[@id='rdbPercent']")
	private WebElement percentageRadioBtn;
	
	
	/**
	 * @return the percentageRadioBtn
	 */
	public WebElement getPercentageRadioBtn(int timeOut) {
		return isDisplayed(driver, percentageRadioBtn, "Visibility", timeOut, "percentageRadioBtn");
	}
	
	@FindBy(xpath = "//input[@id='txtTotalPercent']")
	private WebElement percentageTextbox;
	
	
	/**
	 * @return the percentageTextbox
	 */
	public WebElement getPercentageTextboxCreateDrawdown(int timeOut) {
		return isDisplayed(driver, percentageTextbox, "Visibility", timeOut, "percentageTextbox");
			}
	@FindBy(xpath="(//div[@class='step_3']//a[@title='Send'])[2]")
	private WebElement fundDrawdownEmailingBottomSendBtn;
	
	@FindBy(xpath="(//div[@class='step_3']//a[@title='Send'])[1]")
	private WebElement fundDrawdownEmailingTopSendBtn;

	/**
	 * @param topOrBottom TODO
	 * @return the emailProspectSendBtn
	 */
	public WebElement getfundDrawdownEmailingSendBtn(TopOrBottom topOrBottom, int timeOut) {
			if (TopOrBottom.TOP.toString().equalsIgnoreCase(topOrBottom.toString())) {
				return isDisplayed(driver, fundDrawdownEmailingTopSendBtn, "Visibility", timeOut, "fundDrawdownEmailingSendBtn : "+topOrBottom);
	
			} else {
				return isDisplayed(driver, fundDrawdownEmailingBottomSendBtn, "Visibility", timeOut, "fundDrawdownEmailingSendBtn : "+topOrBottom);

			}
			}
	
	@FindBy(xpath = "//div[@class='step_4']//span[@id='counter4']")
	private WebElement fundDrawdownSendEmailCongratulationsErrorMsg;
	
	
	/**
	 * @return the fundDrawdownSendEmailCongratulationsErrorMsg
	 */
	public WebElement getFundDrawdownSendEmailCongratulationsErrorMsg(int timeOut) {
		return isDisplayed(driver, fundDrawdownSendEmailCongratulationsErrorMsg, "Visibility", timeOut, "fundDrawdownSendEmailCongratulationsErrorMsg");
}
	@FindBy(xpath="//div[@class='step_3']/div[2]/span")
	private WebElement fundDrawdownEmailingSelectedRecipientErrorMsg;
	
	
	/**
	 * @return the fundDrawdownEmailingSelectedRecipientErrorMsg
	 */
	public WebElement getFundDrawdownEmailingSelectedRecipientErrorMsg(int timeOut) {
		return isDisplayed(driver, fundDrawdownEmailingSelectedRecipientErrorMsg, "Visibility", timeOut, "fundDrawdownEmailingSelectedRecipientErrorMsg");
		}
	public WebElement getDrawdownIDAtFDPage(String DDID,int timeOut){
		WebElement ele = FindElement(driver,
				"//div[@class='x-panel-bwrap']//a//span[contains(text(),'" + DDID + "')]", "Drawdown ID",
				action.SCROLLANDBOOLEAN, 60);
		
		return isDisplayed(driver, ele, "Visibility", timeOut, "Select all check box");
	}
	/************************CapitalCallNotices******************************/
	@FindBy(xpath = "//p[contains(text(),'Email')]/following-sibling::p[@class='slds-text-body--small']")
	private WebElement emailing_text;


	/**
	 * @return the emailing_text
	 */
	public WebElement getEmailing_text(int timeOut) {
		return isDisplayed(driver, emailing_text, "visibility", timeOut, "emailing text on send capital call");
	}
	
	@FindBy(xpath = "//div[@class='step_1']/div/div[@class='float_l']")
	private WebElement specifyRecepients;


	/**
	 * @return the specifyRecepients
	 */
	public WebElement getSpecifyRecepients(int timeOut) {
		return isDisplayed(driver, specifyRecepients, "visibility", timeOut, "specify recepients text on send capital call");
	}
	
	@FindBy(xpath = "//span[contains(@id,'aw')and contains(@id,'cell-1')]/span")
	private WebElement noDataToDisplay;


	/**
	 * @return the noDataToDisplay
	 */
	public WebElement getNoDataToDisplayEmailing(int timeOut) {
		return isDisplayed(driver, noDataToDisplay, "visibility", timeOut, "noDataToDisplay text on send capital call");
	}
	@FindBy(xpath = "//div[@class='heading_box']/label")
	private WebElement recordsCount;


	/**
	 * @return the recordsCount
	 */
	public WebElement getRecordsCount(int timeOut) {
		return isDisplayed(driver, recordsCount, "visibility", timeOut, "recordsCount text on send capital call");
		}
	
	@FindBy(xpath="//div[contains(@class,'slds-template_iframe')]//iframe[@title='accessibility title']")
	private WebElement EmailingFrame_Lighting;

	/**
	 * @return the createFundraisingsFrame_Lighting
	 */
	public WebElement getEmailingFrame_Lighting(int timeOut) {
		return isDisplayed(driver, EmailingFrame_Lighting, "Visibility", timeOut, "EmailingFrame on lighting");
	}
	
	@FindBy(xpath = "//input[contains(@name,'txtDueDate')]")
	private WebElement dueDate;
	/**
	 * @return the capitalReturn
	 */
	public WebElement getDueDate(int timeOut) {
		return isDisplayed(driver, dueDate, "visibility", timeOut, "due date textbox");
		}


	@FindBy(xpath="//span[@aria-label='select-2']")
	private WebElement selectedField;


	/**
	 * @return the clearAddressButton
	 */
	public WebElement getSelectedField(String environment,String mode,int timeOut) {
		return isDisplayed(driver, selectedField, "Visibility", timeOut, "selected Field");
		
	}
	
	
	public List<WebElement> getCommitmentListFromCapitalCall(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[2]/a";
		return FindElements(driver, xpath, "Commitment List");
	}
	
	public List<WebElement> getCommitmentAmountListFromCapitalCall(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[3]";
		return FindElements(driver, xpath, "Commitment Amount List");
	}
	
	public List<WebElement> getCapitalAmountListFromCapitalCall(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[4]/input";
		return FindElements(driver, xpath, "Capital Amount List");
	}
	
	public List<WebElement> getManagementFeeAmountListFromCapitalCall(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[5]/input";
		return FindElements(driver, xpath, "Management Amount List");
	}
	
	public List<WebElement> getOtherFeeAmountListFromCapitalCall(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[6]/input";
		return FindElements(driver, xpath, "Other Amount List");
	}
	
	public List<WebElement> getTotalAmountListFromCapitalCall(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[7]/span";
		return FindElements(driver, xpath, "Total Amount List");
	}
	
//=====================================
	
	public List<WebElement> getCommitmentListFromInvestorDistribution(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[2]/a";
		return FindElements(driver, xpath, "Commitment List");
	}
	
	public List<WebElement> getCommitmentAmountListFromInvestorDistribution(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[3]";
		return FindElements(driver, xpath, "Commitment Amount List");
	}
	
	public List<WebElement> getCapitalReturnFromInvestorDistribution(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[4]/input";
		return FindElements(driver, xpath, "Capital Return List");
	}
	
	public List<WebElement> getDividendsFromInvestorDistribution(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[5]/input";
		return FindElements(driver, xpath, "Dividends");
	}
	
	public List<WebElement> getRealisedGainFromInvestorDistribution(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[6]/input";
		return FindElements(driver, xpath, "Other Amount List");
	}
	
	public List<WebElement> getOtherProceedsFromInvestorDistribution(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[7]/input";
		return FindElements(driver, xpath, "Total Amount List");
	}
	
	public List<WebElement> getTotalFromInvestorDistribution(String environment,String mode){
		String xpath = "//tr[contains(@class,'dataRow')]//td[8]/span";
		return FindElements(driver, xpath, "Total Amount List");
	}
	
	@FindBy(xpath = "//div/img[@onclick='redirectpage()']")
	private WebElement fundDrawDownBackArrowLink;
	
	
	/**
	 * @return the fundDrawDownBackButton
	 */
	public WebElement getFundDrawDownBackArrowLink(String environment,String mode,int timeOut) {
		return isDisplayed(driver, fundDrawDownBackArrowLink, "Visibility", timeOut, "fund Draw Down Back Arrow Link");
			}
	
	@FindBy(xpath = "//a[contains(@id,'scc')]/../preceding-sibling::span/a")
	private WebElement fundDrawDownCancelButton;
	
	
	/**
	 * @return the fundDrawDownCancelButton
	 */
	public WebElement getfundDrawDownCancelButton(String environment,String mode,int timeOut) {
		return isDisplayed(driver, fundDrawDownCancelButton, "Visibility", timeOut, "fund Draw Down Cancel Button");
			}
	
	@FindBy(xpath = "(//div[@class='step_1']//a[@title='Cancel'])[1]")
	private WebElement step1TopCancelButtonEmailing;
	
	@FindBy(xpath = "(//div[@class='step_1']//a[@title='Cancel'])[2]")
	private WebElement step1BottomCancelButtonEmailing;
	
	/**
	 * @return the step1NextButtonEmailing
	 */
	public WebElement getStep1CancelButtonEmailing(TopOrBottom topOrBottom,int timeOut) {
		if (TopOrBottom.TOP.toString().equalsIgnoreCase(topOrBottom.toString())) {
			return isDisplayed(driver, step1TopCancelButtonEmailing, "Visibility", timeOut, "step1 Cancel Button Emailing : "+topOrBottom);	
		} else {
			return isDisplayed(driver, step1BottomCancelButtonEmailing, "Visibility", timeOut, "step1 Cancel Button Emailing : "+topOrBottom);	
		}
		
	}
	
	
	@FindBy(xpath = "//a[@id='_applybulkEmailDD']/preceding-sibling::button")
	private WebElement cancelButtonOnColumnsToDisplay;
	
	
	/**
	 * @return the applyColumnsToDisplay
	 */
	public WebElement getCancelButtonOnColumnsToDisplay(int timeOut) {
		return isDisplayed(driver, cancelButtonOnColumnsToDisplay, "Visibility", timeOut, "cancelButtonOnColumnsToDisplay");
	}
	
	
	@FindBy(xpath = "//a[contains(@onclick,'closepopupwrenchbulkEmailDD')]/img")
	private WebElement crossIconOnColumnsToDisplay;
	
	
	/**
	 * @return the applyColumnsToDisplay
	 */
	public WebElement getCrossIconOnColumnsToDisplay(int timeOut) {
		return isDisplayed(driver, crossIconOnColumnsToDisplay, "Visibility", timeOut, "crossIconOnColumnsToDisplay");
	}
	
	
	@FindBy(xpath = "//a[@onclick='redirectpage()']")
	private WebElement fundDistributionBackArrowLink;
	
	
	/**
	 * @return the fundDrawDownBackButton
	 */
	public WebElement getFundDistributionBackArrowLink(String environment,String mode,int timeOut) {
		return isDisplayed(driver, fundDistributionBackArrowLink, "Visibility", timeOut, "fund Distribution Back Arrow Link");
			}
	
	@FindBy(xpath = "//a[contains(@id,'sid')]/../preceding-sibling::span/a")
	private WebElement fundDistributionCancelButton;
	
	
	/**
	 * @return the fundDrawDownCancelButton
	 */
	public WebElement getfundDistributionCancelButton(String environment,String mode,int timeOut) {
		return isDisplayed(driver, fundDistributionCancelButton, "Visibility", timeOut, "fund Distribution Cancel Button");
			}
	
	@FindBy(xpath = "//input[@class='inputTextClass']")
	private WebElement fundInputBox;


	/**
	 * @return the fund name input box
	 */
	public WebElement getSelectFundNameInputBox(int timeOut) {
		return isDisplayed(driver, fundInputBox, "Visibility", timeOut, "fund name input box");
	}

	@FindBy(xpath = "//button[contains(@onclick,'onBtnClicked')]")
	private WebElement createDrawdownButton;


	/**
	 * @return the fund name input box
	 */
	public WebElement getCreatedrawdownButton(int timeOut) {
		return isDisplayed(driver, createDrawdownButton, "Visibility", timeOut, "create distribution  button");
	}

}
