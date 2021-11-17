package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;

import java.text.DecimalFormat;
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
import com.navatar.generic.SmokeCommonVariables;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.FundDrawdownPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;
import com.navatar.generic.SoftAssert;
public class FOFTransactionPageBusinessLayer extends FundDistributionsPage {

	public FOFTransactionPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean createNewFOFTransaction(String projectName,String mode,String fundName,String fundManagersFundName,String transactionType,String date ,String[][] otherlabel, int timeOut){
		boolean flag = true;
		WebElement ele;
		FundsPage fund=new FundsPage(driver);
		
		if(click(driver, getNewButton(projectName, timeOut), "fund distribution button"+projectName, action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on new fund distribution button ");
			
		
		
		///switchToFrame(driver, timeOut, getaccessibilityTitleFrame_Lighting(60));
		ThreadSleep(2000);
		if(sendKeys(driver, getSelectFundNameInputBox(timeOut), fundName, "select fund name input box", action.BOOLEAN)){
			
			String xpath1="";
			ele = isDisplayed(driver, FindElement(driver, xpath1, "", action.BOOLEAN, timeOut), "Visibility", timeOut, "");
			if(click(driver, getCreateDistributionButton(timeOut), "create distribution button", action.BOOLEAN)){
				ThreadSleep(2000);
				switchToDefaultContent(driver);
				refresh(driver);
				ThreadSleep(5000);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())){
					switchToFrame(driver, timeOut, getEmailingFrame_Lighting(60));
				}

				sendKeys(driver, getCapitalReturn( 30),fundManagersFundName, "capitalReturn", action.SCROLLANDBOOLEAN);
				sendKeys(driver,getDividends( 30),transactionType, "dividends", action.SCROLLANDBOOLEAN);
				sendKeys(driver, getDistributionDate(30),date , "distribution date", action.BOOLEAN);
				if (click(driver, getSetupInvestorDist(30), "setup investor distribution sbutton", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);


					if(click(driver, getGenerateInvDist(timeOut), "generate investor distribution button", action.SCROLLANDBOOLEAN)){
						
						ThreadSleep(3000);
						String xpath;
						
						if (fund.getFundNameInViewMode(projectName, 60) != null) {
							ThreadSleep(2000);
							String fundNameViewMode = getText(driver, fund.getFundNameInViewMode(projectName, 10),
									"Fund Name", action.BOOLEAN);
							appLog.info("fundNameViewMode : "+fundNameViewMode);
							xpath="//div//h1/div/..//*[text()='"+fundName+"']";
							ele = FindElement(driver, xpath, "Heeder : "+fundName, action.BOOLEAN, 30);
							if (ele!=null) {
								appLog.info("Fund distribution is created and verified.:" + fundName);
								flag=true;
							} else {
								appLog.error("Fund distribution is not created but not verified.:" + fundName);
							}
						} else {
							appLog.error("Not able to find Fund Name in View Mode");
						}
						
					}else{
						appLog.error("Not able to click on generate investors distribution button");
						return false;
						
					}

				}else{
					log(LogStatus.ERROR, "could not click on getSetupInvestorDist", YesNo.Yes);
					sa.assertTrue(false, "could not click on getSetupInvestorDist");
				}
		
				
			}else{
				
				appLog.error("Not able to click on create distribution button");
				return false;
			}
			
			
		}else{
			
			appLog.error("Not able to select fund :"+fundName);
			return false;
		}
		
		}else {
			appLog.error("Not able to click on new button on fund distribution button page so cannot fund distribution");
			return false;
		}
		
		switchToDefaultContent(driver);
		return flag;
		
	}
}
