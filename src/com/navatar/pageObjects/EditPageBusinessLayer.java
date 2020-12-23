package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.AddressAction;
import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.LimitedPartnerPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

public class EditPageBusinessLayer extends EditPage implements EditPageErrorMessage {
	
	public EditPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	

	/**
	 * @return the editPageSeachTextBox
	 */
	public WebElement getEditPageSeachValueLink(String projectName,String searchValue,int timeOut) {
		String xpath = "//span[text()='"+searchValue+"']";
		WebElement ele = FindElement(driver, xpath, "Search Value : "+searchValue, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Search Value : "+searchValue);
	}
	
	
	public boolean clickOnELGSeachValueLink(String projectName,String searchValue,int timeOut) {
		String xpath = "//span[@title='"+searchValue+"' or text()='"+searchValue+"']";
		WebElement ele = FindElement(driver, xpath, "Search Value : "+searchValue, action.BOOLEAN, timeOut);
		ele =  isDisplayed(driver, ele, "Visibility", timeOut, "Search Value : "+searchValue);
		return click(driver, ele, searchValue, action.BOOLEAN);
	}
	
	
}
