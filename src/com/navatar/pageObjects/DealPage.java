package com.navatar.pageObjects;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ProjectName;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;

import java.util.List;

public class DealPage extends BasePageBusinessLayer {

	public DealPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//*[text()='New Question']")
	private WebElement newRequestTrackerBtn;

	/**
	 * @return the newRequestTrackerBtn
	 */
	public WebElement getNewRequestTrackerBtn(String projectName, int timeOut) {
		return isDisplayed(driver, newRequestTrackerBtn, "Visibility", timeOut, "New Request Tracker");
	}

	@FindBy(xpath = "//*[text()='Status']/..//div//input")
	private WebElement statusDropDownList;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getStatus(String projectName, int timeOut) {
		return isDisplayed(driver, statusDropDownList, "Visibility", timeOut, "Status ");
	}

	@FindBy(xpath = "//div[contains(@class,'photoDropdown')]/*")
	private WebElement imgIcon;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getimgIcon(String projectName, int timeOut) {
		return isDisplayed(driver, imgIcon, "Visibility", timeOut, "imgIcon");
	}

	@FindBy(xpath = "//div[contains(@class,'entityPhotoSpecificity')]//span[contains(@class,'uiImage')]/img")
	private WebElement imgIconForPath;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getimgIconForPath(String projectName, int timeOut) {
		return isDisplayed(driver, imgIconForPath, "Visibility", timeOut, "imgIcon");
	}

	@FindBy(xpath = "//input[@name='file']")
	private WebElement uploadPhotoButton;

	public WebElement getuploadPhotoButton(String projectName, int timeOut) {
		return isDisplayed(driver, uploadPhotoButton, "Visibility", timeOut, "upload photo button");

	}

	@FindBy(xpath = "//textarea")
	private WebElement textArea;

	public WebElement getTextArea(int timeOut) {
		return isDisplayed(driver, textArea, "Visibility", timeOut, "textArea");

	}

	@FindBy(xpath = "//button[text()='Convert to Portfolio']")
	private WebElement convertToPortfolio;

	public WebElement getconvertToPortfolio(int timeOut) {
		CommonLib.refresh(driver);
		return isDisplayed(driver, convertToPortfolio, "Visibility", timeOut, "convertToPortfolio");

	}

	@FindBy(xpath = "//div[contains(@class,'windowViewMode-normal')]//button[contains(text(),'Convert')]")
	private WebElement convert;

	public WebElement getconvertButton(int timeOut) {
		return isDisplayed(driver, convert, "Visibility", timeOut, "convertToPortfolio");

	}

	@FindBy(xpath = "//button[text()='Next']")
	private WebElement nextButton;

	public WebElement getnextButton(int timeOut) {
		return isDisplayed(driver, nextButton, "Visibility", timeOut, "nextButton");

	}

	@FindBy(xpath = "//button[@title='Finish' or text()='Finish']")
	private WebElement finishButton;

	public WebElement getfinishButton(int timeOut) {
		return isDisplayed(driver, finishButton, "Visibility", timeOut, "finish Button");

	}

	public WebElement getconvertToPortfolioCrossButton(int timeOut) {
		String xpath = "//h2[text()='Convert to Portfolio']/ancestor::div//button[@title='Close this window']";
		WebElement ele = null;
		List<WebElement> list = FindElements(driver, xpath, "cross Button");
		for (WebElement element : list) {

			element = isDisplayed(driver, element, "Visibility", timeOut, "cross Button");
			if (element != null) {
				ele = element;
				break;
			}

		}

		return ele;
	}

	public WebElement getconvertToPortfolioCrossButton(String head, int timeOut) {
		String xpath = "//h2[text()='" + head + "']/ancestor::div//button[@title='Close this window']";
		WebElement ele = FindElement(driver, xpath, "cross", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", timeOut, "cross Button");

	}

	@FindBy(xpath = "//span[text()='Mark Stage as Complete']/..")
	private WebElement markStageAsCompleteButton;

	public WebElement getmarkStageAsCompleteButton(int timeOut) {
		return isDisplayed(driver, markStageAsCompleteButton, "Visibility", timeOut, "markStageAsCompleteButton");

	}

	@FindBy(xpath = "//span[text()='Mark as Current Stage']/..")
	private WebElement markAsCurrentStage;

	public WebElement getmarkAsCurrentStage(int timeOut) {
		return isDisplayed(driver, markAsCurrentStage, "Visibility", timeOut, "markStageAsCurrentButton");

	}

	@FindBy(xpath = "//div[@title='New']/parent::a")
	private WebElement NewButton;

	public WebElement getDealNewButton(int timeOut) {
		return isDisplayed(driver, NewButton, "Visibility", timeOut, "New deal button");

	}

	@FindBy(xpath = "//input[@name='Name']")
	private WebElement dealNameInput;

	public WebElement getNewDealPopupDealNameInput(int timeOut) {
		return isDisplayed(driver, dealNameInput, "Visibility", timeOut, " deal name input");

	}

	@FindBy(xpath = "//span[text()='Pipeline Name']/../..//input[@type='text']")
	private WebElement PopupdealNameInput;

	public WebElement getPopupdealNameInput(int timeOut) {
		return isDisplayed(driver, PopupdealNameInput, "Visibility", timeOut, "Popup deal Name Input");

	}

	@FindBy(xpath = "(//div[@class='requiredInput']//span[@class='lookupInput']//input)[2]")
	private WebElement company_Classic;

	@FindBy(xpath = "//*[text()='Company Name']/following-sibling::div//input[@title='Search Institutions' or contains(@placeholder,'Search Institutions')]")
	private WebElement company_Lighting;

	/**
	 * @return the legalName
	 */
	public WebElement getDealCompanyName(String environment, String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			return isDisplayed(driver, company_Classic, "Visibility", timeOut, "company Name Classic");
		} else {
			return isDisplayed(driver, company_Lighting, "Visibility", timeOut, "company Name Lighting");
		}

	}

	@FindBy(xpath = "//*[text()='Stage']/following-sibling::div//input")
	private WebElement stageDropdown;

	public WebElement getStage(int timeOut) {

		return isDisplayed(driver, stageDropdown, "Visibility", timeOut, " stage input");
	}

	@FindBy(xpath = "//*[text()='New Source Firm']/following-sibling::div//input[@title='Search Institutions' or contains(@placeholder,'Search Institutions')]")
	private WebElement newSourcefirm;

	public WebElement getNewSourceFirm(int timeOut) {

		return isDisplayed(driver, newSourcefirm, "Visibility", timeOut, " Source firm input");
	}

	@FindBy(xpath = "//*[text()='Pipeline Comments']/following-sibling::div/textarea")
	private WebElement pipelineComments;

	public WebElement getPipelineComments(int timeOut) {

		return isDisplayed(driver, pipelineComments, "Visibility", timeOut, "pipeline comments input");
	}

	@FindBy(xpath = "//button[@name='SaveEdit']")
	private WebElement saveButton;

	public WebElement getSaveButton(int timeOut) {

		return isDisplayed(driver, saveButton, "Visibility", timeOut, "save button ");
	}

	@FindBy(xpath = "//footer//span[text()='Save']")
	private WebElement popupsaveButton;

	public WebElement getpopupsaveButton(int timeOut) {

		return isDisplayed(driver, popupsaveButton, "Visibility", timeOut, "popup save Button ");
	}

	@FindBy(xpath = "//span[text()='Mark Stage as Complete']")
	private WebElement markStageCompleteButton;

	public WebElement getMarkStageCompleteButton(int timeOut) {

		return isDisplayed(driver, markStageCompleteButton, "Visibility", timeOut, "Mark Stage as Complete button ");
	}

	@FindBy(xpath = "//label[text()='Company Name']/following-sibling::div//input")
	private WebElement companyName;

	public WebElement getCompanyName(int timeOut) {

		return isDisplayed(driver, companyName, "Visibility", timeOut, "Company Name");
	}
	
	@FindBy(xpath = "//*[text()='Company']/..//../div//input")
	private WebElement companyName1;

	public WebElement getCompanyName1(int timeOut) {

		return isDisplayed(driver, companyName1, "Visibility", timeOut, "Company Name1");
	}
	
	@FindBy(xpath = "//label[text()='Stage']/../following-sibling::td//select")
	private WebElement dealStageDropDownList_Classic;

	@FindBy(xpath = "//label[text()='Stage']//following-sibling::div//button")
	private WebElement dealStageDropDownList_Lighting;

	/**
	 * @return the fundType
	 */
	public WebElement getDealStage(String environment, String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			return isDisplayed(driver, dealStageDropDownList_Classic, "Visibility", timeOut, "Deal Stage Classic");
		} else {
			return isDisplayed(driver, dealStageDropDownList_Lighting, "Visibility", timeOut, "Deal Stage Lighting");
		}

	}
	
	public WebElement getSourceFirmAndSourceContactTextBox(String fieldLabelName, int timeOut) {
		String xpath = "//*[text()='" + fieldLabelName + "']/following-sibling::div//input";
		WebElement ele = FindElement(driver, xpath, fieldLabelName + " text box ", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "text box : " + fieldLabelName);

	}
	
	public WebElement getDealType(String projectName, int timeOut) {

		String xpath = "//*[text()='Deal Type']/..//div//button";
		WebElement ele = FindElement(driver, xpath, "Deal Type", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Deal Type : " );

	}
	
	@FindBy(xpath = "//button[@name=\"SaveEdit\"]")

	private WebElement editSaveButton;

	/**
	 * @return the customTabSelectedList
	 */
	public WebElement getEditSaveButton(int timeOut) {
		return isDisplayed(driver, editSaveButton, "Visibility", timeOut, "Edit Save Button");
	}

	@FindBy(xpath = "//button[contains(@aria-label,'Stage')]")
	private WebElement stageField;

	public WebElement getStageField(int timeOut) {

		return isDisplayed(driver, stageField, "Visibility", timeOut, "Stage field");
	}

	@FindBy(xpath = "//a[@class='select']")
	private WebElement popupstageField;

	public WebElement getpopupstageField(int timeOut) {

		return isDisplayed(driver, popupstageField, "Visibility", timeOut, "popup stage Field");
	}

	public WebElement dealRecordTypeRadioButton(String dealRecordType, int timeOut) {
		String xpath = "//span[text()='" + dealRecordType + "']/../preceding-sibling::div/input";

		try {
			return FindElement(driver, xpath, "dealRecordTypeRadioButton: " + dealRecordType, action.SCROLLANDBOOLEAN,
					timeOut);

		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "dealRecordTypeRadioButton: " + dealRecordType, action.SCROLLANDBOOLEAN,
					timeOut);
		}
	}

	@FindBy(xpath = "//span[text()='Next']/parent::button")
	private WebElement nextButtonOnForm;

	public WebElement nextButtonOnForm(int timeOut) {

		return isDisplayed(driver, nextButtonOnForm, "Visibility", timeOut, "nextButtonOnForm");
	}

	public WebElement dropDownWithLabelName(String labelName, int timeOut) {
		String xpath = "//*[text()='" + labelName + "']/..//button";

		try {
			return FindElement(driver, xpath, "dropDownWithLabelName: " + labelName, action.SCROLLANDBOOLEAN, timeOut);

		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "dropDownWithLabelName: " + labelName, action.SCROLLANDBOOLEAN, timeOut);
		}
	}

	public WebElement textBoxBasedOnLabelName(String labelName, int timeOut) {
		String xpath = "//label[text()='" + labelName + "']/ancestor::lightning-input/div//input";

		try {
			return FindElement(driver, xpath, "textBoxBasedOnLabelName: " + labelName, action.SCROLLANDBOOLEAN,
					timeOut);

		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "textBoxBasedOnLabelName: " + labelName, action.SCROLLANDBOOLEAN,
					timeOut);
		}
	}

	public WebElement textAreaBoxBasedOnLabelName(String labelName, int timeOut) {
		String xpath = "//label[text()=\"" + labelName + "\"]/..//div/textarea";

		try {
			return FindElement(driver, xpath, "textAreaBoxBasedOnLabelName: " + labelName, action.SCROLLANDBOOLEAN,
					timeOut);

		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "textAreaBoxBasedOnLabelName: " + labelName, action.SCROLLANDBOOLEAN,
					timeOut);
		}
	}

	@FindBy(xpath = "//button[@name='CancelEdit']")
	private WebElement cancelButton;

	public WebElement getCancelButton(int timeOut) {

		return isDisplayed(driver, cancelButton, "Visibility", timeOut, "Cancel button ");
	}

	@FindBy(xpath = "//label[text()='Source Firm']/following-sibling::div//button")
	private WebElement SourceFirmCrossIcon;

	public WebElement getSourceFirmCrossIcon(String projectName, int timeOut) {
		return isDisplayed(driver, SourceFirmCrossIcon, "Visibility", timeOut, "SourceFirmCrossIcon");
	}

	@FindBy(xpath = "//*[text()='Replace value on records with ']/../..//select")
	private WebElement replacevalueforstage;

	public WebElement getreplacevalueforstage(String projectName, int timeOut) {
		return isDisplayed(driver, replacevalueforstage, "Visibility", timeOut, "replacevalueforstage");
	}

	@FindBy(xpath = "//input[@id='ReplaceValueWithNullValue']")
	private WebElement replacevaluewithNullforstage;

	public WebElement getreplacevaluewithNullforstage(String projectName, int timeOut) {
		return isDisplayed(driver, replacevaluewithNullforstage, "Visibility", timeOut, "replacevaluewithNullforstage");
	}

	
	@FindBy(xpath = "//h2[text()='New Deal']/../..//label[text()='Deal Name']/..//input")
	private WebElement dealNameInputFromPopup;

	public WebElement getDealNameInputFromPopup( int timeOut) {
		return isDisplayed(driver, dealNameInputFromPopup, "Visibility", timeOut, "Deal Name");
	}
	
	
	@FindBy(xpath = "//button[text()='Yes']")
	private WebElement yesButton;

	public WebElement getYesButton(int timeOut) {
		return isDisplayed(driver, yesButton, "Visibility", timeOut, "Yes Button");
	}
	
	@FindBy(xpath = "//h2[text()='New Deal']/../..//label[text()='Company Name']/..//input[@type='search']")
	private WebElement companyNameSearch;

	public WebElement getCompanyNameSearch(int timeOut) {
		return isDisplayed(driver, companyNameSearch, "Visibility", timeOut, "Companyname search");
	}
	
	
	public WebElement getCompanyNameSearch(String companyName, int timeOut) {
		String xpath = "//h2[text()='New Deal']/../..//label[text()='Company Name']/..//ul//li[@role='listitem' and text()='"+companyName+"']";

		try {
			return FindElement(driver, xpath, "company name : " + companyName, action.SCROLLANDBOOLEAN,
					timeOut);

		} catch (StaleElementReferenceException e) {
			return FindElement(driver, xpath, "company name : " + companyName, action.SCROLLANDBOOLEAN,
					timeOut);
		}
	}

	
	@FindBy(xpath = "//span[contains(@class,'toastMessage')]")
	private WebElement createdConfirmationMsg;

	public WebElement getCreatedConfirmationMsg(String projectName, int timeOut) {

		return isDisplayed(driver, createdConfirmationMsg, "Visibility", timeOut, "Created Confirmation Msg");
	}
	
	
	@FindBy(xpath = "//label[text()='Status']/..//button")
	private WebElement statusLabel;

	public WebElement getStatusfield(int timeOut) {
		return isDisplayed(driver, statusLabel, "Visibility", timeOut, "status field");

	}
	
	@FindBy(xpath = "//label[text()='Stage']/..//button")
	private WebElement stageLabel;

	public WebElement getStagefield(int timeOut) {
		return isDisplayed(driver, stageLabel, "Visibility", timeOut, "status field");

	}

	@FindBy(xpath = "//h2[text()='New Deal']/../..//button[text()='Save']")
	private WebElement dealPopupSaveBtn;

	public WebElement getDealPopupSaveBtn(int timeOut) {

		return isDisplayed(driver, dealPopupSaveBtn, "Visibility", timeOut, "deal popup save button");
	}
	
	@FindBy(xpath = "//a[@title='Declined/Dead']/span[text()='Declined/Dead']")
	private WebElement declinedDeadStage;

	public WebElement getDeclinedDeadStage(int timeOut) {
		return isDisplayed(driver, declinedDeadStage, "Visibility", timeOut, "declined dead stage");
	}
	
}
