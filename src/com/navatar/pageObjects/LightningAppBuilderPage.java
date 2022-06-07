package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.isDisplayed;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.action;

public class LightningAppBuilderPage extends BasePageBusinessLayer {

	public LightningAppBuilderPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}



	@FindBy(xpath="//iframe[@title='Salesforce - Enterprise Edition']")
	private WebElement locator;

	public WebElement getLocator() {
		return locator;
	}




	public WebElement getspinnericon()
	{
		WebElement spinnericon=driver.findElement(By.xpath("//div[@class='forceDotsSpinner']/div[@role='status']"));
		return spinnericon;
	}



	@FindBy(xpath="//input[@name='new']")
	private WebElement newButton;

	public WebElement getnewButton(int timeOut) {
		return isDisplayed(driver, newButton, "Visibility", timeOut, "New Button");

	}

	/*
	public WebElement getnewButton() {
		WebElement newButton= driver.findElement(By.xpath("//input[@name='new']"));
		return newButton;
	}
	 */


	@FindBy(xpath="//label[text()='Label']/following-sibling::div/input")
	private WebElement labelName;

	public WebElement getlabelName(int timeOut) {
		return isDisplayed(driver, labelName, "Visibility", timeOut, "label Name");

	}

	/*
	public WebElement getlabelName() {
		WebElement labelName=driver.findElement(By.xpath("//label[text()='Label']/following-sibling::div/input"));	
		return labelName;
	}
	 */

	@FindBy(xpath="//a[text()='Next' and contains(@class,'primary-button')]")
	private WebElement nextButton;

	public WebElement getnextButton(int timeOut) {
		return isDisplayed(driver, nextButton, "Visibility", timeOut, "Next Button");		
	}


	/*		
	public WebElement getnextButton() {
		WebElement nextButton=driver.findElement(By.xpath("//a[text()='Next' and contains(@class,'primary-button')]"));
		return nextButton;
	}
	 */

	@FindBy(xpath="//a[text()='Finish']")
	private WebElement finishButton;

	public WebElement getfinishButton(int timeOut) {
		return isDisplayed(driver, finishButton, "Visibility", timeOut, "Finish Button");		
	}

	/*	

	public WebElement getfinishButton() {
		WebElement finishButton=driver.findElement(By.xpath("//a[text()='Finish']"));
		return finishButton;

	} */

	@FindBy(xpath="//input[@placeholder='Search...']")
	private WebElement SearchonAppBuilder;

	public WebElement getSearchonAppBuilder(int timeOut) {
		return isDisplayed(driver, SearchonAppBuilder, "Visibility", timeOut, "object manage");
	}



	@FindBy(xpath="//iframe[@title='Surface']")
	private WebElement AppBuilderIframe;

	public WebElement getAppBuilderIframe(int timeOut) {
		return AppBuilderIframe;
	}

	@FindBy(xpath="//span[text()='Navatar SDG']")
	private WebElement NavatarSDGBtn;

	public WebElement getNavatarSDGBtn(int timeOut) {
		return isDisplayed(driver, NavatarSDGBtn, "Visibility", timeOut, "object manage");

	}


	@FindBy(xpath="//a[text()='Add Component(s) Here']")

	private WebElement AddComponentButton;

	public WebElement getAddComponentButton(int timeOut) {
		return isDisplayed(driver, AddComponentButton, "Visibility", timeOut, "object manage");

	}


	@FindBy(xpath="//input[@name='Title']")

	private WebElement Title;

	public WebElement getTitle(int timeOut) {
		return isDisplayed(driver, Title, "Visibility", timeOut, "object manage");

	}


	@FindBy(xpath="//label[text()='Data Provider']/parent::lightning-grouped-combobox//input[@role='combobox']")
	private WebElement DataProvider;

	public WebElement getDataProvider(int timeOut) {
		return isDisplayed(driver, DataProvider, "Visibility", timeOut, "object manage");

	}

	@FindBy(xpath="//button[text()='Save']")
	private WebElement SaveButton;

	public WebElement getSaveButton(int timeOut) {
		return isDisplayed(driver, SaveButton, "Visibility", timeOut, "object manage");

	}

	@FindBy(xpath="//label[text()='Data Provider']/parent::lightning-grouped-combobox//lightning-base-combobox-formatted-text")
	private List<WebElement> DataProviderDropDownList;

	public List<WebElement> getDataProviderDropDownList(int timeOut) {
		return DataProviderDropDownList;
	}

	@FindBy(xpath="//button[text()='Activate']")
	private WebElement AvtivateButton;

	public WebElement getAvtivateButton(int timeOut) {
		return isDisplayed(driver, AvtivateButton, "Visibility", timeOut, "object manage");

	}

	@FindBy(xpath="//button[text()='Save' and contains(@class,'activateButton')]")
	private WebElement AvtivatesaveButton;

	public WebElement getAvtivatesaveButton(int timeOut) {
		return isDisplayed(driver, AvtivatesaveButton, "Visibility", timeOut, "object manage");

	}

	@FindBy(xpath="//button[text()='Finish']")
	private WebElement AvtivateFinishButton;

	public WebElement getAvtivateFinishButton(int timeOut) {
		return isDisplayed(driver, AvtivateFinishButton, "Visibility", timeOut, "object manage");

	}



	@FindBy(xpath="//button[text()='Activation...']")
	private WebElement AvtivationButton;

	public WebElement getAvtivationButton(int timeOut) {
		return isDisplayed(driver, AvtivationButton, "Visibility", timeOut, "object manage");

	}
	
	@FindBy(xpath="//h2[@class='slds-card__header-title']")
	private WebElement sldHeader;

	public WebElement getsldHeader(int timeOut) {
		return isDisplayed(driver, sldHeader, "Visibility", timeOut, "SLD Headers");

	}
	
	
	
	@FindBy(xpath="//label[text()='Fund']/parent::lightning-combobox//button/span[text()='All']")
	private WebElement FundDropDown;

	public WebElement getFundDropDown(int timeOut) {
		return isDisplayed(driver, FundDropDown, "Visibility", timeOut, "Fund drop down");

	}
	
	
	@FindBy(xpath="//label[text()='Fund']/parent::lightning-combobox//lightning-base-combobox-item")
	private List<WebElement> FundDropDownList;

	public List<WebElement> getFundDropDownList() {
		return FundDropDownList;

	}
	
	
	
	@FindBy(xpath="//label[text()='Show']")
	private WebElement recordFilter;

	public WebElement getrecordFilter(int timeOut) {
		return isDisplayed(driver, recordFilter, "Visibility", timeOut, "Record Filter");

	}
	
	
	
	
	























}