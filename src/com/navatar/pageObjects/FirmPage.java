package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.isDisplayed;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
	
	
	
	
	
	
	
	
	
	
	
	

}
