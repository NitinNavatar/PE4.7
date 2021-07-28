package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ProjectName;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;

public class DealPage extends BasePageBusinessLayer {

	public DealPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath="//*[text()='New Question']")
	private WebElement newRequestTrackerBtn;

	/**
	 * @return the newRequestTrackerBtn
	 */
	public WebElement getNewRequestTrackerBtn(String projectName,int timeOut) {
		return isDisplayed(driver, newRequestTrackerBtn, "Visibility", timeOut, "New Request Tracker");
	}

	@FindBy(xpath="//*[text()='Status']/..//div//input")
	private WebElement statusDropDownList;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getStatus(String projectName,int timeOut) {
		return isDisplayed(driver, statusDropDownList, "Visibility", timeOut, "Status ");
	}
	@FindBy(xpath="//div[contains(@class,'photoDropdown')]/*")
	private WebElement imgIcon;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getimgIcon(String projectName,int timeOut) {
		return isDisplayed(driver, imgIcon, "Visibility", timeOut, "imgIcon");
	}
	@FindBy(xpath="//div[contains(@class,'entityPhotoSpecificity')]//span[contains(@class,'uiImage')]/img")
	private WebElement imgIconForPath;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getimgIconForPath(String projectName,int timeOut) {
		return isDisplayed(driver, imgIconForPath, "Visibility", timeOut, "imgIcon");
	}
	
	@FindBy(xpath = "//input[@name='file']")
	private WebElement uploadPhotoButton;
	
	public WebElement getuploadPhotoButton(String projectName,int timeOut) {
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
		return isDisplayed(driver, convertToPortfolio, "Visibility", timeOut, "convertToPortfolio");
		
	}
	@FindBy(xpath = "//h2[text()='Convert to Portfolio']/../following-sibling::*//article//span[text()='"+DealPageErrorMessage.convertToPortfolio+"']")
	private WebElement convertToPortfolioMessage;
	
	public WebElement getconvertToPortfolioMessage(int timeOut) {
		return isDisplayed(driver, convertToPortfolioMessage, "Visibility", timeOut, "convertToPortfolio");
		
	}
	@FindBy(xpath = "//button[text()='Next']")
	private WebElement nextButton;
	
	public WebElement getnextButton(int timeOut) {
		return isDisplayed(driver, nextButton, "Visibility", timeOut, "nextButton");
		
	}
	
}
