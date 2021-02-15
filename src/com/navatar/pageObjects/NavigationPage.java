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
}

