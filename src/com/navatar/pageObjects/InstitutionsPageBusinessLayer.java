package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.navatar.generic.AppListeners.*;

public class InstitutionsPageBusinessLayer extends InstitutionsPage {

	public InstitutionsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	///////////////////////////////////////////////////////  Activity Association ///////////////////////////////////////////////////////////////////////////
	
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param institutionName
	 * @param recordType
	 * @param labelsWithValues
	 * @param timeOut
	 * @return true/false
	 * @description this method is used to create single entity if pe and account if mna
	 */
	public boolean createEntityOrAccount(String projectName,String institutionName,String recordType, String[][] labelsWithValues,int timeOut) {
		boolean flag=false;
		refresh(driver);
		ThreadSleep(3000);
			ThreadSleep(10000);
			if(clickUsingJavaScript(driver, getNewButton(projectName, timeOut), "new button")) {
				appLog.info("clicked on new button");
				
				if (!recordType.equals("") || !recordType.isEmpty()) {
					ThreadSleep(2000);
					if(click(driver, getRadioButtonforRecordType(recordType, timeOut), "Radio Button for : "+recordType, action.SCROLLANDBOOLEAN)){
						appLog.info("Clicked on radio Button for institution for record type : "+recordType);
						if (click(driver, getContinueOrNextButton(projectName,timeOut), "Continue Button", action.BOOLEAN)) {
							appLog.info("Clicked on Continue or Nxt Button");	
							ThreadSleep(1000);
						}else{
							appLog.error("Not Able to Clicked on Next Button");
							return false;	
						}
					}else{
						appLog.error("Not Able to Clicked on radio Button for record type : "+recordType);
						return false;
					}
					
				}
				
				if (sendKeys(driver, getLegalName(projectName,timeOut), institutionName, "leagl name text box",action.SCROLLANDBOOLEAN)) {
					appLog.info("passed data in text box: " + institutionName);
					
					FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
					if (labelsWithValues!=null) {
						for (String[] strings : labelsWithValues) {
							if (click(driver, fp.getDealStatus(projectName, timeOut), "Status : "+strings[1], action.SCROLLANDBOOLEAN)) {
								ThreadSleep(2000);
								appLog.error("Clicked on Deal Status");
								
								String xpath="//div[@class='select-options']//li/a[@title='"+strings[1]+"']";
								WebElement dealStatusEle = FindElement(driver,xpath, strings[1],action.SCROLLANDBOOLEAN, timeOut);
								ThreadSleep(2000);
								if (click(driver, dealStatusEle, strings[1], action.SCROLLANDBOOLEAN)) {
									appLog.info("Selected Status : "+strings[1]);
									ThreadSleep(2000);
								} else {
									appLog.error("Not able to Select on Status : "+strings[1]);
									flag=false;
								}
							} else {
								appLog.error("Not able to Click on Status : ");
								flag=false;
							}
						}
					}
					if (click(driver, getSaveButton(projectName,timeOut), "save button", action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on save button");
						
						String str = getText(driver, getLegalNameHeader(projectName,timeOut), "legal Name Label Text",action.SCROLLANDBOOLEAN);
						if (str != null) {
							if (str.contains(institutionName)) {
								appLog.info("created institution " + institutionName + " is verified successfully.");
								appLog.info(institutionName + " is created successfully.");
								flag=true;
							} else {
								appLog.error("Created  " + institutionName + " is not matched with " + str);
							}
						} else {
							appLog.error("Created  " + institutionName + " is not visible");
						}
					} else {
						appLog.error("Not able to click on save button so cannot create : "+ institutionName);
					}
				} else {
					appLog.error("Not able to pass data in legal name text box so cannot create : "+ institutionName);
				}
				
			}else {
				appLog.error("Not able to click on New Button so cannot create institution: " + institutionName);
			
			}
		
		
		
		return flag;
	}
	
	public WebElement toggleButton(String projectName,PageName pageName,String btnName,action action,int timeOut) {
		String xpath = "//button[@title='"+btnName+"']";
		WebElement ele = FindElement(driver, xpath,"Toggle Button : "+btnName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btnName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btnName);
		return ele;
	}
	
	public WebElement toggleSDGButtons(String projectName,PageName pageName,String toggleTab,ToggleButtonGroup btnName,action action,int timeOut) {
		String btname = btnName.toString();
		String xpath = "//*[text()='"+toggleTab+"']/../../..//following-sibling::div//button[@title='"+btname+"']";
		WebElement ele = FindElement(driver, xpath,toggleTab+" >> "+btname, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btname);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btname);
		return ele;
	}
	
	
	public WebElement getFundNameAtToggle(String projectName,PageName pageName,String fundName,action action,int timeOut) {
		String xpath = "//*[@data-label='Fund: ']//*[text()='"+fundName+"']";
		WebElement ele = FindElement(driver, xpath,fundName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Fund Name : "+fundName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Fund Name : "+fundName);
		return ele;
	}
	
	
	public WebElement getFundNameAtToggleToolTip(String projectName,PageName pageName,String fundName,action action,int timeOut) {
		String xpath = "//*[@data-label='Fund: ']//*[text()='"+fundName+"']/..";
		WebElement ele = FindElement(driver, xpath,fundName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Fund Name : "+fundName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Fund Name : "+fundName);
		return ele;
	}
	
	public WebElement getLegalEntityAtToggle(String projectName,PageName pageName,String entityName,action action,int timeOut) {
		String xpath = "//*[@data-label='Legal Entity: ']//*[text()='"+entityName+"']";
		WebElement ele = FindElement(driver, xpath,entityName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Fund Name : "+entityName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Fund Name : "+entityName);
		return ele;
	}
	
	public WebElement getInlineOrLockedAtToggle(String projectName,PageName pageName,String name,action action,int timeOut) {
		String xpath = "//a[text()='Fund Investments']/../../../../../..//*[@title='"+name+"']/../following-sibling::span/button";
		WebElement ele = FindElement(driver, xpath,name, action, timeOut);
		scrollDownThroughWebelement(driver, ele, name);
		return ele;
	}
	
}
