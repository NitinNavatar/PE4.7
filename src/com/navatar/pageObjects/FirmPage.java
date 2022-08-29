package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;

import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.action;

public class FirmPage extends BasePageBusinessLayer{
	
	public FirmPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath = "//a[@title='New']")
	private WebElement newButton;

	public WebElement getnewButton(int timeOut) {
		return isDisplayed(driver, newButton, "Visibility", timeOut, "new button");
	}
	
	@FindBy(xpath = "//div[@class='changeRecordTypeRow']//span[@class='slds-form-element__label']")
	private List<WebElement> recordTypeLabelName;

	public List<WebElement> getrecordTypeLabelName() {
		return recordTypeLabelName;
	}
	
	@FindBy(xpath="//button[@role='button']/parent::div")
	private WebElement clickedOnRecentlyViewed;
	public WebElement getClickedOnRecentlyViewed(int timeOut) {
		return isDisplayed(driver, clickedOnRecentlyViewed, "Visibility", timeOut, "Recently Viewed");
	}
	
	@FindBy(xpath="//button[@title='Show filters']")
	private WebElement showFilter;
	
	public WebElement getshowFilter(int timeOut) {
		return isDisplayed(driver, showFilter, "Visibility", timeOut, "show filter");
	}
	
	@FindBy(xpath="//span[@class='currentScopeLabel']")
    private WebElement scopeLabelFilter;
	
	public WebElement getscopeLabelFilter(int timeOut) {
		return isDisplayed(driver, scopeLabelFilter, "Visibility", timeOut, "scope lebel filter");
	}
	
	@FindBy(xpath="//div[@id='filterPanelFieldCriterion0']//div[@class='fieldLabel']")
    private WebElement filterFieldLabel;
	
	public WebElement getfilterFieldLabel(int timeOut) {
		return isDisplayed(driver, filterFieldLabel, "Visibility", timeOut, "filter field label");
	}
	
	@FindBy(xpath="//div[@id='filterPanelFieldCriterion0']//span[@class='test-operatorWrapper']")
    private WebElement filterOperator;
	
	public WebElement getfilterOperator(int timeOut) {
		return isDisplayed(driver, filterOperator, "Visibility", timeOut, "filter Operator");
	}
	
	@FindBy(xpath="//div[@id='filterPanelFieldCriterion0']//span[@class='test-operandsWrapper']")
    private WebElement filterOperand;
	
	public WebElement getfilterOperand(int timeOut) {
		return isDisplayed(driver, filterOperand, "Visibility", timeOut, "filter Operand");
	}
	
	@FindBy(xpath="//div[@id='filterPanelFieldCriterion1']//div[@class='fieldLabel']")
    private WebElement filterFieldLabel1;
	
	public WebElement getfilterFieldLabel1(int timeOut) {
		return isDisplayed(driver, filterFieldLabel1, "Visibility", timeOut, "filter field label");
	}
	
	@FindBy(xpath="//div[@id='filterPanelFieldCriterion1']//span[@class='test-operatorWrapper']")
    private WebElement filterOperator1;
	
	public WebElement getfilterOperator1(int timeOut) {
		return isDisplayed(driver, filterOperator1, "Visibility", timeOut, "filter Operator");
	}
	
	@FindBy(xpath="//div[@id='filterPanelFieldCriterion1']//span[@class='test-operandsWrapper']")
    private WebElement filterOperand1;
	
	public WebElement getfilterOperand1(int timeOut) {
		return isDisplayed(driver, filterOperand1, "Visibility", timeOut, "filter Operand");
	}
	
	@FindBy(xpath="//label[text()='Filter Logic']")
    private WebElement filterLogic;
	
	public WebElement filterLogic(int timeOut) {
		return isDisplayed(driver, filterLogic, "Visibility", timeOut, "filter Logic");
	}
	
	@FindBy(xpath="//button[text()='Edit']")
    private WebElement editBtn;
	
	public WebElement geteditBtn(int timeOut) {
		return isDisplayed(driver, editBtn, "Visibility", timeOut, "Edit button");
	}
	
	
	@FindBy(xpath="//button[contains(@aria-label,'Entity Type')]")
    private WebElement entityTypeBtn;
	
	public WebElement getentityTypeBtn(int timeOut) {
		return isDisplayed(driver, entityTypeBtn, "Visibility", timeOut, "Entity type button");
	
	}
	
	@FindBy(xpath="//button[@class='slds-button slds-button_icon-border-filled']")
    private WebElement TabEroBtn;
	
	public WebElement getTabEroBtn(int timeOut) {
		return isDisplayed(driver, TabEroBtn, "Visibility", timeOut, "Tab ero button");
	}
	
	@FindBy(xpath="//span[contains(@class,'toastMessage')]")
    private WebElement toastMessage;
	
	public WebElement gettoastMessage(int timeOut) {
		return isDisplayed(driver, toastMessage, "Visibility", timeOut, "Save confirmation message");
	}
	
	@FindBy(xpath="//a[@data-label='Contacts']")
	private WebElement FirmsContactsTab;

	public WebElement getFirmsContactsTab(int timeOut) {
	return isDisplayed(driver, FirmsContactsTab, AddFolderInfoIconMessage, timeOut, AccessDeniedPopUpMessage);
	}

	@FindBy(xpath="//span[@class='slds-truncate slds-text-link_reset']//lightning-icon[@class='slds-icon-utility-arrowup slds-icon_container']")
	private WebElement ArrowUp;

	public WebElement getArrowUp(int timeOut) {
	return isDisplayed(driver, ArrowUp, "Visibility", timeOut, "ArrowUp");
	}

	
	public WebElement previousMonthButtonInDatePicker(int timeOut) {
        return FindElement(driver, "//lightning-calendar//button[@title='Previous Month']", "Previous button",
				action.BOOLEAN, 30);

    }
	
	public WebElement monthInDatePicker(int timeOut) {

        String xpath = "//lightning-calendar//h2";
        try {
            return FindElement(driver, xpath, "Month Element ", action.SCROLLANDBOOLEAN, timeOut);
        } catch (StaleElementReferenceException e) {
            return FindElement(driver, xpath, "Month Element ", action.SCROLLANDBOOLEAN, timeOut);
        }
    }
	
	public List<WebElement> getDetailsTab() {
		return FindElements(driver,
				"//slot//records-record-layout-section//div//span[@class='test-id__section-header-title']",
				"DetailsTab");
	}
	
	@FindBy(xpath="//div[contains(@class,'entityNameTitle')]")
	private WebElement pageTitle;

	public WebElement getpageTitle(int timeOut) {
	return isDisplayed(driver, pageTitle, "Visibility", timeOut, "pageTitle");
	}
	
	
	
	public List<WebElement> getClientList() {
		return FindElements(driver,
				"//a[contains(@class,'header-title')]",
				"SDG Name");
	}
	
	@FindBy(xpath = "//span[text()='Your changes are saved.']")
	private WebElement saveConfirmationMsg;

	public WebElement getsaveConfirmationMsg(int timeOut) {
		return isDisplayed(driver, saveConfirmationMsg, "Visibility", timeOut, "save confirmation message");
		}
		
	@FindBy(xpath = "//h2[contains(@class,'slds-modal__title')]")
	private WebElement popUpHeading;

	public WebElement getpopUpHeading(int timeOut) {
		return isDisplayed(driver, popUpHeading, "Visibility", timeOut, "popup heading");
		}
	
	@FindBy(xpath = "//h2[@class='slds-card__header-title']/a")
	private WebElement fileCountVisible;

	public WebElement getfileCountVisible(int timeOut) {
		return isDisplayed(driver, fileCountVisible, "Visibility", timeOut, "file Count visible");
		}
	
	@FindBy(xpath = "//span[text()='Upload Files']")
	private WebElement uploadFileVisible;

	public WebElement getuploadFileVisible(int timeOut) {
		return isDisplayed(driver, uploadFileVisible, "Visibility", timeOut, "Upload File Visible");
		}
	
	@FindBy(xpath = "//div[text()='Add Files']")
	private WebElement addFileVisible;

	public WebElement getaddFileVisible(int timeOut) {
		return isDisplayed(driver, addFileVisible, "Visibility", timeOut, "Add File button Visible");
	}

	
	@FindBy(xpath = "//span[text()='Or drop files']")
	private WebElement dropFileVisible;

	public WebElement getdropFileVisible(int timeOut) {
		return isDisplayed(driver, dropFileVisible, "Visibility", timeOut, "Or drop file button Visible");
		}
	
	@FindBy(xpath="//li[@title='Files']")
    private WebElement FilesTabPage;
	
	public WebElement getFilesTabPage(int timeOut) {
		return isDisplayed(driver, FilesTabPage, "Visibility", timeOut, "FilesTabPage");
	}
	
	@FindBy(xpath="//span[text()='Done']")
	private WebElement ClickedOnDoneButton;
	
	public WebElement getClickedOnDoneButton(int timeOut) {
		return isDisplayed(driver, ClickedOnDoneButton, "Visibility", timeOut, "ClickedOnDoneButton");
	}
    
	public WebElement getFileName(int timeOut) {
		
		return FindElement(driver, "//div[@class='slds-show_inline-block slds-float_left slds-p-left--x-small slds-truncate slds-m-right_x-small']//span", "FileName",
				action.SCROLLANDBOOLEAN, timeOut);
	
	}
	public WebElement getUploadFileButton(int timeOut) {
		return FindElement(driver, "//span[text()='Upload Files']", "UploadFileButton",action.SCROLLANDBOOLEAN, timeOut);
		
	}
	
	@FindBy(xpath="//h2[@class='slds-card__header-title']//span[contains(@class,'slds-shrink')]")
    private WebElement UploadedFileCount;
	
	public WebElement getUploadedFileCount(int timeOut) {
		return isDisplayed(driver, UploadedFileCount, "Visibility", timeOut, "Uploaded File Count");
	}
	
	@FindBy(xpath="//input[@class='slds-file-selector__input slds-assistive-text']")
    private WebElement fileUpload;
	
	public WebElement getfileUpload(int timeOut) {
		return isDisplayed(driver, fileUpload, "Visibility", timeOut, "File Upload");
	}
	

	@FindBy(xpath="//span[text()='1 file was added to the Firm.']")
    private WebElement fileUploadConfirmationMsg;
	
	public WebElement getfileUploadConfirmationMsg(int timeOut) {
		return isDisplayed(driver, fileUploadConfirmationMsg, "Visibility", timeOut, "File Upload Confirmation Message");
	}
	
	@FindBy(xpath="//p[contains(@class,'secondaryFields')]/span[@class='uiOutputDateTime']")
    private WebElement fileUploadDate;
	
	public WebElement getfileUploadDate(int timeOut) {
		return isDisplayed(driver, fileUploadDate, "Visibility", timeOut, "Uploaded File Date");
	}
	
	@FindBy(xpath="//p[contains(@class,'secondaryFields')]/span[@class='forceChatterFileSize']")
    private WebElement fileUploadSize;
	
	public WebElement getfileUploadSize(int timeOut) {
		return isDisplayed(driver, fileUploadSize, "Visibility", timeOut, "Uploaded file Size");
	}
	
	@FindBy(xpath="//p[contains(@class,'secondaryFields')]/span[@class='itemLabel slds-truncate uiOutputText']")
    private WebElement fileUploadType;
	
	public WebElement getfileUploadType(int timeOut) {
		return isDisplayed(driver, fileUploadType, "Visibility", timeOut, "Uploaded file type");
	}
	
	@FindBy(xpath="//span[text()='View All']")
    private WebElement viewAllButton;
	
	public WebElement getviewAllButton(int timeOut) {
		return isDisplayed(driver, viewAllButton, "Visibility", timeOut, "view all button");
	}
	
	
	public WebElement sectionBelowTaskAndEventSection(int timeOut) {
        String xpath = "//div[contains(@class,'slds-size_1-of-1 row row-main')]/div[contains(@class,'region-sidebar-right')][2]";
       try {
            return FindElement(driver, xpath, "sectionBelowTaskAndEventSection", action.SCROLLANDBOOLEAN, timeOut);
       } catch (StaleElementReferenceException e) {
            return FindElement(driver, xpath, "sectionBelowTaskAndEventSection", action.SCROLLANDBOOLEAN, timeOut);
        }
    }
	
	public WebElement pageLevelCreateRecordPopupSaveOrCancelButton(String buttonName, int timeOut) {
        String xpath = "//footer/button/span[text()='" + buttonName + "']/parent::button";



       try {
            return FindElement(driver, xpath, "pageLevelCreateRecordPopupSaveOrCancelButton", action.SCROLLANDBOOLEAN,
                    timeOut);



       } catch (StaleElementReferenceException e) {
            return FindElement(driver, xpath, "pageLevelCreateRecordPopupSaveOrCancelButton", action.SCROLLANDBOOLEAN,
                    timeOut);
        }
    }
	


}
