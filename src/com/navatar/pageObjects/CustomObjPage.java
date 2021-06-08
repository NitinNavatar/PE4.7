package com.navatar.pageObjects;
import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.isDisplayed;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.navatar.generic.CommonVariables.*;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ProjectName;
import com.navatar.generic.EnumConstants.action;

public class CustomObjPage extends BasePageBusinessLayer{

	public CustomObjPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	public WebElement getFieldTextBox(int timeOut) {
		String xpath="//label[text()='"+tabCustomObjField+"']/following-sibling::div//input";
		
		return isDisplayed(driver, FindElement(driver, xpath, "input name", action.SCROLLANDBOOLEAN,30), "Visibility", timeOut, "fieldTextBox");
	}

	public WebElement getRecordNameInViewMode(String projectName,int timeOut) {
		String xpath="";
		//if (projectName.equalsIgnoreCase(ProjectName.MNA.toString())) {
		//	xpath="//div[text()='"+SmokeCommonVariables.tabCustomObj+"']/following-sibling::div//span";
		//}
		//else
		xpath="//div[text()='"+tabCustomObj+"']/following-sibling::slot//lightning-formatted-text";
		return isDisplayed(driver, FindElement(driver, xpath, "record name", action.SCROLLANDBOOLEAN,30), "Visibility", timeOut, "record Name in View Mode Lighting");
		
	}
}
