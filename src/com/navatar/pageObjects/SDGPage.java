package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.SDGCreationLabel;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;

import static com.navatar.generic.CommonLib.*;

import java.util.List;

public class SDGPage extends BasePageBusinessLayer {

	public SDGPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * @return the contactFullNameLabel
	 */
	public WebElement getSDGHeaderValueInViewMode(String projectName,String value,int timeOut) {
		
		String xpath ="//*[text()='Sortable Data Grid']/..//*[text()='"+value+"']";
		WebElement ele = FindElement(driver, xpath,value , action.SCROLLANDBOOLEAN, timeOut);
		scrollDownThroughWebelement(driver, ele, value);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, value);
		return isDisplayed(driver, ele, "Visibility", timeOut,value);
		
		
	}
	
}
