package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.SearchBasedOnExistingFundsOptions;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.List;

public class NavigationPage extends BasePageBusinessLayer {
	
	public NavigationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath="//div[@class='flexipagePage']//span[text()='Minimize']")
	private WebElement navatarQuickLinkMinimize_Lighting;

	/**
	 * @return the navatarQuickLinkMinimize_Lighting
	 */
	public WebElement getNavatarQuickLinkMinimize_Lighting(String projectName,int timeOut) {
		return isDisplayed(driver, navatarQuickLinkMinimize_Lighting, "Visibility", timeOut, "Navatar Quick Link Minimize Lighting");
	}
	
	/**
	 * @return the getExpandIcon
	 */
	public List<WebElement> getExpandIcon(String projectName,int timeOut) {
		return FindElements(driver, "//span[@class='icon expand-icon glyphicon glyphicon-plus']", "> icon");
	}
	
	@FindBy(xpath="//div/a[text()='Next']")
	private WebElement lightningPageNextBtn;

	/**
	 * @return the lightningPageNextBtn
	 */
	public WebElement getLightningPageNextBtn(String projectName,int timeOut) {
		return isDisplayed(driver, lightningPageNextBtn, "Visibility", timeOut, "lightning Page Next Btn");
	}
	
	@FindBy(xpath="(//div/a[text()='Next'])[2]")
	private WebElement lightningPageNextBtn2;

	/**
	 * @return the lightningPageNextBtn
	 */
	public WebElement getLightningPageNextBtn2(String projectName,int timeOut) {
		return isDisplayed(driver, lightningPageNextBtn2, "Visibility", timeOut, "lightning Page Next Btn2");
	}
	
	@FindBy(xpath="//div/a[text()='Finish']")
	private WebElement lightningPageFinishBtn;

	/**
	 * @return the lightningPageNextBtn
	 */
	public WebElement getLightningPagFinishBtn(String projectName,int timeOut) {
		return isDisplayed(driver, lightningPageFinishBtn, "Visibility", timeOut, "lightning Page Finish Btn");
	}
	

	@FindBy (xpath = "//*[text()='Lightning Experience']")
	private WebElement lightningExperienceTab;

	/**
	 * @return the lightningExperience
	 */
	public WebElement getLightningExperienceTab(String projectName,int timeOut) {
		return isDisplayed(driver, lightningExperienceTab, "Visibility", timeOut, "lightning Experience Tab");
	}
	
}

