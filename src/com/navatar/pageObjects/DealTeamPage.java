package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DealTeamPage extends BasePageBusinessLayer{

	public DealTeamPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	@FindBy(xpath="//*[text()='Team Member Role']/..//div//input")
	private WebElement teamMemberRoleDropDownList;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getteamMemberRoleDropDownList(String projectName,int timeOut) {
		return isDisplayed(driver, teamMemberRoleDropDownList, "Visibility", timeOut, "teamMemberRoleDropDownList");
	}
	
	@FindBy(xpath="//*[text()='Deal']/..//div//input")
	private WebElement dealTextbox;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getListTextbox(String projectName,String memberOrDeal,int timeOut) {
		String xpath = "//*[text()='"+memberOrDeal+"']/..//div//input";
		WebElement ele=FindElement(driver, xpath, memberOrDeal, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "memberOrDeal");
	}
	
	@FindBy(xpath="//*[text()='Member']/..//div//input")
	private WebElement memberTextbox;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getmemberTextbox(String projectName,int timeOut) {
		return isDisplayed(driver, memberTextbox, "Visibility", timeOut, "memberTextbox");
	}
	@FindBy(xpath = "//*[text()='DT Id']/../following-sibling::*//span")
	private WebElement getDtid;
	
	public WebElement getDtid(String projectName,int timeOut) {
		return isDisplayed(driver, getDtid, "Visibility", timeOut, "getDtid");
	}
}
