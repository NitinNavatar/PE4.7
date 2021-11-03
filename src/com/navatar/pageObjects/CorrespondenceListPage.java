package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.isDisplayed;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.Mode;

public class CorrespondenceListPage extends BasePageBusinessLayer{

	public CorrespondenceListPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	@FindBy(xpath = "//label[text()='Contact']/ancestor::td[contains(@class,'labelCol')]/following-sibling::td//span//input")
	private WebElement contactValue;
	
	@FindBy(xpath = "//label[text()='Commitment']/ancestor::td[contains(@class,'labelCol')]/following-sibling::td//span//input")
	private WebElement commitmentValue_Classic;
	@FindBy(xpath = "//input[contains(@placeholder,'Search Commitments')]")
	private WebElement commitment_Light;
	/**
	 * @return the commitmentValue
	 */
	public WebElement getCommitmentValue(String mode, String environment, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString()))
		return isDisplayed(driver, commitmentValue_Classic, "Visibility", timeOut, "Commitment in edit Mode Classic");
		else
			return isDisplayed(driver, commitment_Light, "Visibility", timeOut, "Commitment in edit Mode Lightning");
		}

	/**
	 * @return the contactValue
	 */
	public WebElement getContactValue(int timeOut) {
		return isDisplayed(driver, contactValue, "Visibility", timeOut, "contact in edit Mode Classic");
	}
	@FindBy(xpath = "//select[contains(@title,'Available')]")
	private WebElement availableSelectBox_Classic;
	
	@FindBy(xpath = "//span[text()='Available']/following-sibling::div//ul[@aria-multiselectable='true']")
	private WebElement availableSelectBox_Lighting;
	/**
	 * @return the availableSelectBox
	 */
	public WebElement getAvailableSelectBox(String environment, String mode, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString()))
		return isDisplayed(driver, availableSelectBox_Classic, "Visibility", timeOut, "available Select Box Mode Classic");
		else
			return isDisplayed(driver, availableSelectBox_Lighting, "Visibility", timeOut, "available Select Box Mode Lightning");
			
}
	
	@FindBy(xpath = "//img[@alt='Add']")
	private WebElement addButtonMultipleBox_Classic;
	
	@FindBy(xpath = "//button[@title='Move selection to Chosen']")
	private WebElement addButtonMultipleBox_Lightning;
	
	public WebElement getAddButtonMultipleBox(String environment, String mode,int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString()))
		return isDisplayed(driver, addButtonMultipleBox_Classic, "Visibility", timeOut, "addButtonMultipleBox Select Box Mode Classic");
		else
		return isDisplayed(driver, addButtonMultipleBox_Lightning, "Visibility", timeOut, "addButtonMultipleBox Select Box Mode Lightning");

	}
	
	@FindBy(xpath = "//img[@alt='Remove']")
	private WebElement removeButtonMultipleBox;
	
	public WebElement getRemoveButtonMultipleBox(int timeOut) {
		return isDisplayed(driver, removeButtonMultipleBox, "Visibility", timeOut, "removeButtonMultipleBox Select Box Mode Classic");
	}
}
