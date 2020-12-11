package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.isDisplayed;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static com.navatar.generic.CommonLib.*;

public class TaskPage extends BasePageBusinessLayer {

	public TaskPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath="//button[@title='Refresh']")
	private WebElement refreshIcon;

	/**
	 * @return the taskRayFrame
	 */
	public WebElement getRefreshIcon(String projectName,int timeOut) {
		return isDisplayed(driver, refreshIcon, "Visibility", timeOut, "Refresh Icon");
	}
	
	@FindBy(xpath="(//*[@role='dialog']//button)[1]")
	private WebElement taskPagePopUp;
	
	/**
	 * @return the taskRayFrame
	 */
	public WebElement getTaskPagePopUp(String projectName,int timeOut) {
		return isDisplayed(driver, taskPagePopUp, "Visibility", timeOut, "Task Page Pop Up");
	}
	

	/**
	 * @return the taskRayFrame
	 */
	public WebElement getTaskNameLinkInSideMMenu(String projectName,String taskName,int timeOut) {
		WebElement ele = getTaskPagePopUp(projectName, 10);
		if (ele!=null) {
			click(driver, ele, "Task Page Pop Up", action.BOOLEAN);
		} 
		
		String xpath="//div[@class='oneConsoleObjectHome']//div//span[text()='"+taskName+"']";
		ele = FindElement(driver, xpath, taskName, action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, taskName);
	}
	
	
	@FindBy(xpath = "//iframe[@title='accessibility title']")
	 private WebElement taskPageFrame;
	
	/**
	 * @return the taskPageFrame
	 */
	public WebElement getTaskPageFrame(String projectName,int timeOut) {
		return isDisplayed(driver, taskPageFrame, "Visibility", timeOut, "task Page Frame");
	}
	
	@FindBy(xpath = "//div[@id='popupOpenId']//h2")
	 private WebElement taskPoUpEditHeader;
	
	/**
	 * @return the taskPoUpEditHeader
	 */
	public WebElement getTaskPoUpEditHeader(String projectName,int timeOut) {
		return isDisplayed(driver, taskPoUpEditHeader, "Visibility", timeOut, "task PoUp EditHeader");
	}
	
	
	
	@FindBy(xpath = "//label[text()='Related Contacts']//following-sibling::div")
	 private WebElement relatedContactsLabel;
	
	/**
	 * @return the taskPoUpEditHeader
	 */
	public WebElement getRelatedContactsLabel(String projectName,int timeOut) {
		return relatedContactsLabel;
		//return isDisplayed(driver, relatedContactsLabel, "Visibility", timeOut, "Related Contact Label");
	}
	@FindBy(xpath = "//span[text()='Comments']/..//following-sibling::div")
	 private WebElement commentsLabel;
	
	/**
	 * @return the taskPoUpEditHeader
	 */
	public WebElement getCommentsLabel(String projectName,int timeOut) {
		return relatedContactsLabel;
		//return isDisplayed(driver, relatedContactsLabel, "Visibility", timeOut, "Related Contact Label");
	}
	
}
