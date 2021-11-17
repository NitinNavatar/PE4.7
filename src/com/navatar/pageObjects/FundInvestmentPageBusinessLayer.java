package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.FundDrawdownPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;
import com.navatar.generic.SoftAssert;

/**
 * @author Akul Bhutani
 *
 */
public class FundInvestmentPageBusinessLayer extends FundInvestmentPage {

	public FundInvestmentPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	

	
	/**@author Ravi Kumar
	 * @param projectName
	 * @param mode
	 * @param fund name
	 * @param company name
	 * @param investment amount
	 * @param date
	 * @return true/false
	 */
	
	
	public boolean createFundInvestment(String projectName, String mode, String  fundName,String companyName,String investmentAmount,String investmentDate,int timeOut) {
		boolean flag = false;
		WebElement ele;
		String xpath;
		
		if(click(driver, getNewButton(projectName, mode, timeOut), "New button", action.BOOLEAN)) {
			
			ThreadSleep(3000);
			log(LogStatus.PASS, "clicked on new button ", YesNo.No);
			ele=isDisplayed(driver, getFundName(timeOut), "Visibility", timeOut, "fund name input box on new fund investment poup");
			
			if(ele!=null) {
				
				if(sendKeys(driver, getFundName(timeOut), fundName, "Fund Name input", action.BOOLEAN)) {
					log(LogStatus.PASS, fundName+" fund name is enetered in new fund investment  popup", YesNo.No);

					ThreadSleep(1000);
					xpath="//*[@title='"+fundName+"']/..";
					ele=isDisplayed(driver, FindElement(driver, xpath, fundName, action.BOOLEAN, timeOut), "Visibility",timeOut,fundName);
					
					if(click(driver, ele,fundName, action.BOOLEAN)) {
						log(LogStatus.PASS, fundName+" fund is selected in new fund investment  popup", YesNo.No);
						
						if(sendKeys(driver, getCompanyName(timeOut), companyName, "company Name input", action.BOOLEAN)) {
							log(LogStatus.PASS, companyName+" company name is enetered in new fund investment  popup", YesNo.No);
							ThreadSleep(1000);
							ele=null;
							xpath="//*[@title='"+companyName+"']/..";
							ele=isDisplayed(driver, FindElement(driver, xpath, companyName, action.BOOLEAN, timeOut), "Visibility",timeOut, companyName);
							
							if(click(driver, ele,companyName, action.BOOLEAN)) {
								log(LogStatus.PASS, companyName+" company is selected in new fund investment  popup", YesNo.No);
								ThreadSleep(1000);
								
								sendKeys(driver, getInvestmentAmount(timeOut), investmentAmount, "investment amount input", action.BOOLEAN);
								sendKeys(driver,getInvestmentDate(timeOut), investmentDate, "investment date input", action.BOOLEAN);

								
								if(click(driver, getNavigationTabSaveBtn(projectName,timeOut), "Save button", action.BOOLEAN)) {
									log(LogStatus.PASS,"clicked on save button", YesNo.No);
									
									ThreadSleep(3000);
									ele=null;
									ele=getRelatedTab(projectName, RelatedTab.Details.toString(), 10);

									 if(ele!=null) {
										 flag=true;
										 ele=null;
										 ele = isDisplayed(driver, FindElement(driver, "//*[text()='Fund Investment']/..//lightning-formatted-text", "fund investment id text", action.SCROLLANDBOOLEAN, 30), "visibility", 20, "");
										 log(LogStatus.PASS,"after clicking on save button page redirected to fund investment record detail page", YesNo.No);
										
										 String investementId= getText(driver, ele, "investment id",action.BOOLEAN);
										if(investementId!=null) {
											ExcelUtils.writeData(phase1DataSheetFilePath,investementId,"FundInvestment", excelLabel.Variable_Name, "FI1", excelLabel.FundInvestmentID);
											log(LogStatus.PASS,investementId+":fund investment successfully created", YesNo.No);
											
											flag=true;
										}
										 
									 }else {
										 flag =false;
											log(LogStatus.ERROR, "after clicking on save button page not redirected to fund investment record detail page", YesNo.Yes);
											sa.assertTrue(false, "after clicking on save button page not redirected to fund investment record detail page");
											
									 }
								}else {
									flag=false;
									log(LogStatus.ERROR, "Not clicked on save button", YesNo.Yes);
									sa.assertTrue(false, "Not clicked on save button");
									
									
								}
							}else {
								
								flag=false;
								log(LogStatus.ERROR, companyName+" company is not selected in new fund investment  popup", YesNo.Yes);
								sa.assertTrue(false, companyName+" company is not selected in new fund investment  popup");
								
							}
						}else {
							flag=false;
							log(LogStatus.ERROR, companyName+" company name is not enetered in new fund investment  popup", YesNo.Yes);
							sa.assertTrue(false, companyName+" company name is not enetered in new fund investment  popup");
							
							
						}
						
					}else {
						flag=false;
						log(LogStatus.ERROR, fundName+" fund is not selected in new fund investment  popup", YesNo.Yes);
						sa.assertTrue(false, fundName+" fund is not selected in new fund investment  popup");
						
						
					}
						
					
				}else {
					flag=false;
					log(LogStatus.ERROR, fundName+" fund name is not enetered in new fund investment  popup", YesNo.Yes);
					sa.assertTrue(false, fundName+" fund name is not enetered in new fund investment  popup");
					
					
				}
				

			}else {
				flag=false;
				log(LogStatus.ERROR, "Fund name input not visibile on new fund investment popup", YesNo.Yes);
				sa.assertTrue(false, "Fund name input not visibile on new fund investment popup");
				
			}
			

		}else {
			flag=false;
			log(LogStatus.ERROR, "could not click on new button ", YesNo.Yes);
			sa.assertTrue(false, "could not click on new button ");
		}
		
		
		return flag;
	}
}
