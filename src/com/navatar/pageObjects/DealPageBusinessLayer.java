package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;

import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;

public class DealPageBusinessLayer extends DealPage implements DealPageErrorMessage{
	
	public DealPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public boolean verifyingOpenRequest(String projectName,String requestID, String dateRequested,String request,String status,int timeOut) {

		log(LogStatus.INFO,"Going to verify Open Request with information as: "+requestID+" >> "+dateRequested+" >> "+request+" >> "+status,YesNo.No);
		boolean flag = false;
		String requestIdXpath = "//*[contains(text(),'"+requestID+"')]/../../../..";
		String dateRequestedXpath = "/following-sibling::td//*[text()='"+dateRequested+"']/../../..";
		String requestXpath = "/following-sibling::td//*[text()='"+request+"']/../../..";
		String statusXpath = "/following-sibling::td//*[text()='"+status+"']";
		String fullXpath = requestIdXpath+dateRequestedXpath+requestXpath+statusXpath;
		
		WebElement ele = FindElement(driver, fullXpath, "Open Request", action.BOOLEAN, timeOut);
		if (ele!=null) {
			ele = isDisplayed(driver, ele, "Visibility", timeOut, "Open Request");
			log(LogStatus.INFO,"Verified Open Request with information as: "+requestID+" >> "+dateRequested+" >> "+request+" >> "+status,YesNo.No);
			flag=true;
		}
		log(LogStatus.INFO,"Not Verified Open Request with information as: "+requestID+" >> "+dateRequested+" >> "+request+" >> "+status,YesNo.No);
		BaseLib.sa.assertTrue(false, "Not Verified Open Request with information as: "+requestID+" >> "+dateRequested+" >> "+request+" >> "+status);
		return flag;
	}
	
	public boolean verifyingClosedRequest(String projectName,String requestID, String dateRequested,String request,int timeOut) {

		log(LogStatus.INFO,"Going to verify Closed Request with information as: "+requestID+" >> "+dateRequested+" >> "+request,YesNo.No);
		boolean flag = false;
		String requestIdXpath = "//*[contains(text(),'"+requestID+"')]/../../../..";
		String dateRequestedXpath = "/following-sibling::td//*[text()='"+dateRequested+"']/../../..";
		String requestXpath = "/following-sibling::td//*[text()='"+request+"']";
		String fullXpath = requestIdXpath+dateRequestedXpath+requestXpath;
		
		WebElement ele = FindElement(driver, fullXpath, "Closed Request", action.BOOLEAN, timeOut);
		if (ele!=null) {
			ele = isDisplayed(driver, ele, "Visibility", timeOut, "Closed Request");
			log(LogStatus.INFO,"Verified Closed Request with information as: "+requestID+" >> "+dateRequested+" >> "+request,YesNo.No);
			flag=true;
		}
		log(LogStatus.INFO,"Not Verified Closed Request with information as: "+requestID+" >> "+dateRequested+" >> "+request,YesNo.No);
		BaseLib.sa.assertTrue(false, "Not Verified Closed Request with information as: "+requestID+" >> "+dateRequested+" >> "+request);
		return flag;
	}
	
	
	public WebElement getDateAtToggle(String projectName,PageName pageName,String date,action action,int timeOut) {
		String xpath = "//*[@data-label='Date Requested: ']//*[text()='"+date+"']";
		WebElement ele = FindElement(driver, xpath,date, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "DATE : "+date);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "DATE : "+date);
		return ele;
	}
	
	public WebElement getDateAtToggleToolTip(String projectName,PageName pageName,String date,action action,int timeOut) {
		String xpath = "//*[@data-label='Date Requested: ']//*[text()='"+date+"']/..";
		WebElement ele = FindElement(driver, xpath,date, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "DATE : "+date);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "DATE : "+date);
		return ele;
	}
	
	public WebElement getInlineOrLockedAtToggle(String projectName,PageName pageName,String name,action action,int timeOut) {
		String xpath = "//a[text()='Fund Investments']/../../../../../..//*[@title='"+name+"']/../following-sibling::span/button";
		WebElement ele = FindElement(driver, xpath,name, action, timeOut);
		scrollDownThroughWebelement(driver, ele, name);
		return ele;
	}
	
	
	
	
	
}

