package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;

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
	
	@FindBy(xpath="//*[text()='Limited Partner']/following-sibling::div//input[contains(@placeholder,'Search')]")
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
	
	@FindBy(xpath="//*[text()='Partnership']/following-sibling::div//input[contains(@placeholder,'Search')]")
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
		String xpath="//*[text()='Commitment']/../*/*[@slot='primaryField']/*";
		WebElement ele = FindElement(driver,xpath, "commitment id xpath", action.SCROLLANDBOOLEAN, timeOut);
		return ele;
	}

	}
	
	@FindBy(xpath = "//*[text()='Final Commitment Date']/following-sibling::div/input")
	private WebElement finalCommimentDate;

	public WebElement getFinalCommimentDate(int timeOut) {
		return isDisplayed(driver, finalCommimentDate, "Visibility", timeOut, "final commitment date Text Box Lighting");
	}
	
	@FindBy(xpath = "//*[text()='Commitment Amount']/following-sibling::div/input")
	private WebElement CommimentAmount;

	public WebElement getCommimentAmount(int timeOut) {
		return isDisplayed(driver, CommimentAmount, "Visibility", timeOut, "commitment amount Text Box Lighting");
	}
}
