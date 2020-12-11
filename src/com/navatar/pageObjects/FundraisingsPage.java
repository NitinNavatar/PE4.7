package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class FundraisingsPage extends BasePageBusinessLayer {

	public FundraisingsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath="//input[@name='Name']")
	private WebElement fundraisingName_Classic;
	
	@FindBy(xpath="//span[text()='Fundraising Name']/../following-sibling::input")
	private WebElement fundraisingName_Lighting;

	/**
	 * @return the fundraisingName
	 */
	public WebElement getFundraisingName(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, fundraisingName_Classic, "Visibility", timeOut, "FundRaising Name Classic");
		}else{
			return isDisplayed(driver, fundraisingName_Lighting, "Visibility", timeOut, "FundRaising Name Lighting");
		}
		
	}
	
	@FindBy(xpath="(//div[@class='requiredInput']//span[@class='lookupInput']//input)[1]")
	private WebElement fundName_Classic;
	
	@FindBy(xpath="//span[text()='Fund Name']/../following-sibling::div//input[@title='Search Funds']")
	private WebElement fundName_Lighting;

	/**
	 * @return the fundName
	 */
	public WebElement getFundName(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, fundName_Classic, "Visibility", timeOut, "Fund Name Classic");
		}else{
			return isDisplayed(driver, fundName_Lighting, "Visibility", timeOut, "Fund Name Lighting");
		}
	
	}
	
	@FindBy(xpath="(//div[@class='requiredInput']//span[@class='lookupInput']//input)[2]")
	private WebElement legalName_Classic;
	
	@FindBy(xpath="//span[text()='Legal Name']/../following-sibling::div//input[@title='Search Institutions']")
	private WebElement legalName_Lighting;

	/**
	 * @return the legalName
	 */
	public WebElement getLegalName(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, legalName_Classic, "Visibility", timeOut, "Legal Name Classic");
		}else{
			return isDisplayed(driver, legalName_Lighting, "Visibility", timeOut, "Legal Name Lighting");
		}
	
	} 
	
	@FindBy(xpath="//div[@id='Name_ileinner']")
	private WebElement fundraisingNameInViewMode_Classic;
	
	@FindBy(xpath="//div[@class='slds-media__body']//h1/span")
	private WebElement fundraisingNameInViewMode_Lighting;

	/**
	 * @return the fundraisingNameInViewMode
	 */
	public WebElement getFundraisingNameInViewMode(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, fundraisingNameInViewMode_Classic, "Visibility", timeOut, "Fundraising Name in view Mode Classic");
		}else{
			return isDisplayed(driver, fundraisingNameInViewMode_Lighting, "Visibility", timeOut, "Fundraising Name in view Mode Lighting");
		}
	}

}
