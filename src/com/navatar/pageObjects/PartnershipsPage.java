package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.isDisplayed;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.navatar.generic.EnumConstants.Mode;

public class PartnershipsPage extends BasePageBusinessLayer {

	public PartnershipsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath="//input[@name='Name']")
	private WebElement partnershipLegalName_Classic;
	
	@FindBy(xpath="//span[text()='Partnership Legal Name']/../following-sibling::input")
	private WebElement partnershipLegalName_Lighting;

	/**
	 * @return the partnershipLegalName
	 */
	public WebElement getPartnershipLegalName(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			 return isDisplayed(driver, partnershipLegalName_Classic, "Visibility", timeOut, "Partnership Legal Name Classic");
		}else{
			 return isDisplayed(driver, partnershipLegalName_Lighting, "Visibility", timeOut, "Partnership Legal Name Lighting");
		}
		
	}
	
	@FindBy(xpath="//span[@class='lookupInput']//input")
	private WebElement fundTextBox_Classic;
	
	@FindBy(xpath="//span[text()='Fund']/../following-sibling::div//input[@title='Search Funds']")
	private WebElement fundTextBox_Lighting;


	/**
	 * @return the fundTextBox
	 */
	public WebElement getFundTextBox(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
				return isDisplayed(driver, fundTextBox_Classic, "Visibility", timeOut, "Fund Name Text Box Classic");
		}else{
			return isDisplayed(driver, fundTextBox_Lighting, "Visibility", timeOut, "Fund Name Text Box Lighting");
		}
	
	}
	
	@FindBy(xpath="//div[@id='Name_ileinner']")
	private WebElement partnershipNameInViewMode_Classic;
	
	@FindBy(xpath="//div[@class='slds-media__body']//h1//span[@data-aura-class='uiOutputText']")
	private WebElement partnershipNameInViewMode_Lighting;

	/**
	 * @return the partnershipNameInViewMode
	 */
	public WebElement getPartnershipNameInViewMode(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, partnershipNameInViewMode_Classic, "Visibility", timeOut, "Partnership Name in View Mode Classic");
	}else{
		return isDisplayed(driver, partnershipNameInViewMode_Lighting, "Visibility", timeOut, "Partnership Name in View Mode Lighting");
	}

	}

}
