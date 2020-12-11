package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.isDisplayed;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.navatar.generic.EnumConstants.Mode;

public class CommitmentsPage extends BasePageBusinessLayer {

	public CommitmentsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath="//label[text()='Limited Partner']/../..//span[@class='lookupInput']/input")
	private WebElement limitedPartnerTextbox_Classic;
	
	@FindBy(xpath="//span[text()='Limited Partner']/../following-sibling::div//input[@title='Search Institutions']")
	private WebElement limitedPartnerTextbox_Lighting;

	/**
	 * @return the limitedPArtnerTextbox
	 */
	public WebElement getLimitedPartnerTextbox(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, limitedPartnerTextbox_Classic, "Visibility", timeOut, "Limited Partner Text Box Classic");
	}else{
			return isDisplayed(driver, limitedPartnerTextbox_Lighting, "Visibility", timeOut, "Limited Partner Text Box Lighting");
	}
		
	}
	
	@FindBy(xpath="//label[text()='Partnership']/../..//span[@class='lookupInput']/input")
	private WebElement partnershipTextBox_Classic;
	
	@FindBy(xpath="//span[text()='Partnership']/../following-sibling::div//input[@title='Search Partnerships']")
	private WebElement partnershipTextBox_Lighting;

	/**
	 * @return the partnershipTextBox
	 */
	public WebElement getPartnershipTextBox(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, partnershipTextBox_Classic, "Visibility", timeOut, "Partnership Text Box Classic");
	}else{
		return isDisplayed(driver, partnershipTextBox_Lighting, "Visibility", timeOut, "Partnership Text Box Lighting");
	}
	
	}
	
	@FindBy(xpath="//div[@id='Name_ileinner']")
	private WebElement commitmentIdInViewMode_Classic;
	
	@FindBy(xpath="//div[@class='slds-media__body']//h1//span[@data-aura-class='uiOutputText']")
	private WebElement commitmentIdInViewMode_Lighting;

	/**
	 * @return the commitmentIdInViewMode
	 */
	public WebElement getCommitmentIdInViewMode(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, commitmentIdInViewMode_Classic, "Visibility", timeOut, "Commitment ID in View Mode Classic");
	}else{
		return isDisplayed(driver, commitmentIdInViewMode_Lighting, "Visibility", timeOut, "Commitment ID in View Mode Classic");
	}

	}

}
