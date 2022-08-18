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
				"ClientTab");
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
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
