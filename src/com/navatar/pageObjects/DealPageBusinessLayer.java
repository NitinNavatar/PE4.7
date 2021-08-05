package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.ToggleDeal1;

import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.ContactPagePhotoActions;
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
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param requestID
	 * @param dateRequested
	 * @param request
	 * @param status
	 * @param timeOut
	 * @return true if successfully verified open request
	 */
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
			return flag;
		}
		log(LogStatus.INFO,"Not Verified Open Request with information as: "+requestID+" >> "+dateRequested+" >> "+request+" >> "+status,YesNo.No);
		BaseLib.sa.assertTrue(false, "Not Verified Open Request with information as: "+requestID+" >> "+dateRequested+" >> "+request+" >> "+status);
		return flag;
	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param requestID
	 * @param dateRequested
	 * @param request
	 * @param timeOut
	 * @return true if successfully verified closed request
	 */
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
			return flag;
		}
		log(LogStatus.INFO,"Not Verified Closed Request with information as: "+requestID+" >> "+dateRequested+" >> "+request,YesNo.No);
		BaseLib.sa.assertTrue(false, "Not Verified Closed Request with information as: "+requestID+" >> "+dateRequested+" >> "+request);
		return flag;
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param date
	 * @param action
	 * @param timeOut
	 * @return date webElement at Toggle
	 */
	public WebElement getDateAtToggle(String projectName,PageName pageName,String date,action action,int timeOut) {
		String xpath = "//*[@data-label='Date Requested: ']//*[text()='"+date+"']";
		WebElement ele = FindElement(driver, xpath,date, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "DATE : "+date);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "DATE : "+date);
		return ele;
	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param date
	 * @param action
	 * @param timeOut
	 * @return date tool tip 
	 */
	public WebElement getDateAtToggleToolTip(String projectName,PageName pageName,String date,action action,int timeOut) {
		String xpath = "//*[@data-label='Date Requested: ']//*[text()='"+date+"']/..";
		WebElement ele = FindElement(driver, xpath,date, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "DATE : "+date);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "DATE : "+date);
		return ele;
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param name
	 * @param action
	 * @param timeOut
	 * @return inLine
	 */
	public WebElement getInlineOrLockedAtToggle(String projectName,PageName pageName,String name,action action,int timeOut) {
		String xpath = "//a[text()='Fund Investments']/../../../../../..//*[@title='"+name+"']/../following-sibling::span/button";
		WebElement ele = FindElement(driver, xpath,name, action, timeOut);
		scrollDownThroughWebelement(driver, ele, name);
		return ele;
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param dealName
	 * @param requestInfo
	 * @param timeOut
	 * @return true if new request created successfully
	 */
	public boolean createNewRequest(String projectName,String dealName,String[][] requestInfo,int timeOut) {
		boolean flag=false;
		String label;
		String value;
		String xpath="";
		WebElement ele;
		if(clickUsingJavaScript(driver, getNewRequestTrackerBtn(projectName, timeOut), "New Request Tracker button")) {
			appLog.info("clicked on New Request Tracker button");
			log(LogStatus.INFO,"click on New Request Tracker Button",YesNo.Yes);

			for (String[] reuestData : requestInfo) {
				label=reuestData[0].replace("_", " ");
				value=reuestData[1];

				if (PageLabel.Request.toString().equals(reuestData[0])) {
					xpath="//*[text()='Request']//following-sibling::div//textarea";
					ele = FindElement(driver, xpath, label, action.BOOLEAN, timeOut);
					if (sendKeys(driver,ele, value, label+" : "+value, action.BOOLEAN)) {
						log(LogStatus.INFO,"Send "+value+" to label : "+label,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Send "+value+" to label : "+label);
						log(LogStatus.SKIP,"Not Able to Send "+value+" to label : "+label,YesNo.Yes);
					}

				}else if(PageLabel.Date_Requested.toString().equals(reuestData[0])){
					xpath="//*[text()='Date Requested']//following-sibling::div//input";
					ele = FindElement(driver, xpath, label, action.BOOLEAN, timeOut);
					if (sendKeys(driver,ele, value, label+" : "+value, action.BOOLEAN)) {
						log(LogStatus.INFO,"Send "+value+" to label : "+label,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Send "+value+" to label : "+label);
						log(LogStatus.SKIP,"Not Able to Send "+value+" to label : "+label,YesNo.Yes);
					}
				}else if(PageLabel.Status.toString().equals(reuestData[0])) {

					if (click(driver, getStatus(projectName, timeOut), label, action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						log(LogStatus.INFO,"Able to Click on "+label,YesNo.No);

						xpath="//span[@title='"+value+"']";
						ele = FindElement(driver,xpath, value,action.SCROLLANDBOOLEAN, timeOut);
						ThreadSleep(2000);
						if (click(driver, ele, value, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);	
						} else {
							sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
							log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,"Not Able to Click on "+label);
						log(LogStatus.SKIP,"Not Able to Click on "+label,YesNo.Yes);
					}

				}
			}

			xpath="//*[text()='Deal']/following-sibling::div//input[@placeholder='"+dealName+"']";
			ele = FindElement(driver, xpath, dealName, action.BOOLEAN, timeOut);
			if (ele!=null) {
				log(LogStatus.INFO,"Deal Label prefilled with value : "+dealName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Deal Label not prefilled with value : "+dealName);
				log(LogStatus.SKIP,"Deal Label not prefilled with value : "+dealName,YesNo.Yes);
			}

			if (click(driver, getCustomTabSaveBtn(projectName,timeOut), "save button", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on save button");
				ThreadSleep(3000);

			} else {
				sa.assertTrue(false,"Not Able to Click on save button so cannot create request");
				log(LogStatus.SKIP,"Not Able to Click on save button so cannot create request",YesNo.Yes);
			}


		}else {
			sa.assertTrue(false,"Not able to click on New Request Tracker Button so cannot create request");
			log(LogStatus.SKIP,"Not able to click on New Request Tracker Button so cannot create request",YesNo.Yes);

		}
		return flag;
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param attachmentPath
	 * @return image id of uploaded photo
	 */
	public String updatePhotoInUserPage(String projectName,String attachmentPath) {
		String imgId=null;
		ContactsPage cp = new ContactsPage(driver);
		Actions actions = new Actions(driver);
		scrollDownThroughWebelement(driver,getimgIcon(projectName, 10) , "img");
		click(driver, getimgIcon(projectName, 10), "img icon", action.SCROLLANDBOOLEAN);
		ThreadSleep(2000);
		log(LogStatus.INFO, "click on img link", YesNo.No);
		if (click(driver,cp. getupdatePhotoLink(projectName,ContactPagePhotoActions.Update_Photo, 10), ContactPagePhotoActions.Update_Photo.toString(), action.SCROLLANDBOOLEAN)) {
			if (sendKeys(driver, getuploadPhotoButton(projectName,10), attachmentPath, "upload photo button", action.SCROLLANDBOOLEAN) ) {
				ThreadSleep(5000);
				if (click(driver, getSaveButton(10), "save", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "successfully uploaded photo", YesNo.No);
					ThreadSleep(4000);
					imgId=getimgIconForPath(projectName, 10).getAttribute("src");
					if (imgId!=null){
						log(LogStatus.INFO, "found id of img uploaded: "+imgId, YesNo.Yes);

						return imgId;
					}
					else {
						log(LogStatus.ERROR, "could not find id of img uploaded", YesNo.Yes);
						sa.assertTrue(false, "could not find id of img uploaded");

					}
				}else {
					log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
					sa.assertTrue(false, "save button is not clickable");
				}
			}else {
				log(LogStatus.ERROR, "could not pass attachment path to image", YesNo.Yes);
				sa.assertTrue(false, "could not pass attachment path to image");
			}
		}else {
			log(LogStatus.ERROR, "update photo button is not clickable", YesNo.Yes);
			sa.assertTrue(false, "update photo button is not clickable");
		}
		return null;
	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param request
	 * @param action
	 * @param timeOut
	 * @return request webElement
	 */
	public WebElement getRequestAtToggle(String projectName,PageName pageName,String request,action action,int timeOut) {
		String xpath = "//*[@data-label='Request: ']//*[text()='"+request+"']";
		WebElement ele = FindElement(driver, xpath,request, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "request : "+request);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "request : "+request);
		return ele;
	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param request
	 * @param action
	 * @param timeOut
	 * @return true if reuest present
	 */
	public boolean isRequestAtToggleToolTip(String projectName,PageName pageName,String request,action action,int timeOut) {
		boolean flag=false;
		String xpath = "//*[@data-label='Request: ']//*[text()='"+request+"']";
		WebElement ele = FindElement(driver, xpath,request, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "request : "+request);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "request : "+request);
		if (ele!=null) {
			flag=ele.getAttribute("title").equalsIgnoreCase(request);
			return flag;
		} 
		return flag;
	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param itemValue
	 * @param action
	 * @param timeOut
	 * @return edit btn webElemnt
	 */
	public WebElement getEditBtn(String projectName,String itemValue,action action,int timeOut) {
		String xpath = "//*[text()='"+itemValue+"']/../following-sibling::*//*[@title='Edit']";
		WebElement ele = FindElement(driver, xpath,itemValue, action, timeOut);
		return ele;
	}
	
	public WebElement findDeactivateLink(String projectName, String stage) {
		String xpath = "//th[text()='"+stage+"']/preceding-sibling::td//a[contains(@title,'Deactivate')]";
		WebElement ele = FindElement(driver, xpath,"deactivate", action.SCROLLANDBOOLEAN, 10);
		scrollDownThroughWebelement(driver, ele, "deactivate link for "+stage);
		return isDisplayed(driver, ele, "Visibility", 10, "deactivate "+stage);
		
	}
	
	public WebElement findActivateLink(String projectName, String stage) {
		String xpath = "//th[text()='"+stage+"']/preceding-sibling::td//a[contains(@title,'Activate')]";
		WebElement ele = FindElement(driver, xpath,"Activate", action.SCROLLANDBOOLEAN, 10);
		scrollDownThroughWebelement(driver, ele, "Activate link for "+stage);
		return isDisplayed(driver, ele, "Visibility", 10, "Activate "+stage);
		
	}
	public String convertToPortfolioBeforeNext(String company) {
		return "Please click 'Next' to convert "+company+" to a Portfolio Company";
	}
	
	public String convertToPortfolioRepeat(String company) {
		return company+" is already a Portfolio.";
	}
	public String convertToPortfolioAfterNext(String company) {
		return "Congratulations!" + 
				""+company+" has been coverted to Portfolio Company successfully.";
	}
	
	
	
	public WebElement getconvertToPortfolioMessage(String company,int timeOut) {
		String xpath="//h2[text()='Convert to Portfolio']/../following-sibling::*//article//span[text()='"+convertToPortfolioBeforeNext(company)+"']";
		WebElement ele = FindElement(driver, xpath,"convert to portfolio", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", timeOut, "convertToPortfolio");
		
	}
	
	public WebElement getconvertToPortfolioMessageAfterNext(int timeOut) {
		String xpath="//h2[text()='Convert to Portfolio']/../following-sibling::*//article//span[contains(text(),'successfully')]/..";
		WebElement ele = FindElement(driver, xpath,"convert to portfolio", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", timeOut, "convertToPortfolio");
		
	}
	
	public WebElement getconvertToPortfolioMessageRepeat(int timeOut) {
		String xpath="//h2[text()='Convert to Portfolio']/../following-sibling::*//article//span[contains(text(),'already')]/..";
		WebElement ele = FindElement(driver, xpath,"convert to portfolio", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", timeOut, "convertToPortfolio");
		
	}
	
	public WebElement crossIconForCompanyName(String company,int timeOut) {
		String xpath="//label[text()='Company Name']/..//input[@placeholder='"+company+"']//following-sibling::*//button";
		WebElement ele = FindElement(driver, xpath,"cross icon for company", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", timeOut, "cross icon for company");

	}
}

