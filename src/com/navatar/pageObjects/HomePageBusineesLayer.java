package com.navatar.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.navatar.generic.EnumConstants.AddProspectsTab;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.BaseLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.NavatarQuickLink;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.searchContactInEmailProspectGrid;
import com.navatar.generic.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
import static com.navatar.generic.EnumConstants.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.AppListeners.*;
public class HomePageBusineesLayer extends HomePage {

	public HomePageBusineesLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	/***********************************Activity Association*********************************/
	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @return true/false
	 * @description this method is used to click on setup link on home page
	 */
	public boolean clickOnSetUpLink() {
		boolean flag = false;
			if(click(driver, getSettingLink_Lighting(20), "setting icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "setting icon", YesNo.No);
				
			}else {
				log(LogStatus.ERROR, "clicked on setting icon", YesNo.Yes);
				return flag;
			}
		if(click(driver, getUserMenuSetupLink(20), "setup link", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on setup link", YesNo.No);
			flag=true;
		}else {
			log(LogStatus.ERROR,"user setup link",YesNo.Yes);
		}
		return flag;
	}
	
	/**@author Akul Bhutani
	 * @param environment
	 * @param mode
	 * @return true/false
	 * @description this method is used to click on click on setup link either classic or lightning
	 */
	public boolean clickOnSetUpLink(String environment,String mode) {
		boolean flag = false;
		if(mode.equalsIgnoreCase(Mode.Classic.toString())) {
			if(click(driver, getUserMenuTab(20), "user menu tab", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on user menu tab");
				log(LogStatus.INFO, "user menu tab", YesNo.No);
				
			}else {
				log(LogStatus.ERROR, "user menu tab", YesNo.Yes);
				return flag;
			}
		}else {
			if(click(driver, getSettingLink_Lighting(20), "setting icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "setting icon", YesNo.No);
				
			}else {
				log(LogStatus.ERROR, "setting icon", YesNo.Yes);
				return flag;
			}
		}
		if(click(driver, getUserMenuSetupLink( mode, 20), "setup link", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "setup link", YesNo.No);
			flag=true;
		}else {
			log(LogStatus.ERROR,"user setup link",YesNo.Yes);
		}
		return flag;
	}
	
	public boolean clickOnEditPageLinkOnSetUpLink() {
		boolean flag = false;
			if(click(driver, getSettingLink_Lighting(20), "setting icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "setting icon", YesNo.No);
				
			}else {
				log(LogStatus.ERROR, "setting icon", YesNo.Yes);
				return flag;
			}
		if(click(driver, getEditPageOnSetUp(20), "Edit Page", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Edit Page", YesNo.No);
			flag=true;
		}else {
			log(LogStatus.ERROR,"Edit Page",YesNo.Yes);
		}
		return flag;
	}
	
	public boolean clickOnLinkFromNavatarQuickLink(String environment,String mode,NavatarQuickLink navatarQuickLink){
		boolean flag =false;
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
		
			if (clickUsingJavaScript(driver, getNavatarQuickLink_Lighting(environment, 20), "Navatar Quik Link")) {
				ThreadSleep(1000);
				switchToFrame(driver, 10, getNavatarQuickLinkFrame_Lighting(environment, 10));
			}
		}else{
			if(getCloseSideBar(5)==null){
				if(click(driver, getOpenSideBar(30), "Open sied bar", action.BOOLEAN)){
					log(LogStatus.INFO, "Opened the side bar.", YesNo.No);
				} else {
//					BaseLib.sa.assertTrue(false, "cannot open the side bar, So cannot check the navatar quick link.");
//					log(LogStatus.ERROR, "cannot open the side bar, So cannot check the navatar quick link.", YesNo.Yes);
				}
			}
			ThreadSleep(1000);
			appLog.info("Inside Classic Frame");
			switchToFrame(driver, 10, getNavatarQuickLinkFrame_Classic(environment, 10));	
		}
		
		WebElement quickLink = FindElement(driver, "//a[contains(text(),'"+navatarQuickLink+"')]", "Navatar Quick Link : "+navatarQuickLink, action.SCROLLANDBOOLEAN, 20);
		if (click(driver, quickLink, "Navatar Quick Link : "+navatarQuickLink, action.SCROLLANDBOOLEAN)) {	
			flag = true;
		}
		
		switchToDefaultContent(driver);
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			if (click(driver, getNavatarQuickLinkMinimize_Lighting(environment, 20), "Navatar Quik Link Minimize Icon",
					action.SCROLLANDBOOLEAN)) {
				ThreadSleep(1000);
			}
		}
		
		return flag;
		
	}
	
public boolean verifyLandingPageAfterClickingOnNavatarSetUpPage(String environment,String mode, NavatarQuickLink navatarQuickLink) {
		
		String landingPage = null;
		WebElement ele;
		switch (navatarQuickLink) {
		case CreateDeal:
			landingPage = "Deal Creation";
			break;
		case BulkEmail:
			landingPage = "Bulk E-mail";
			break;
		default:
			return false;
		}
		ThreadSleep(2000);
		System.err.println("Passed switch statement");
	
			
			ele = isDisplayed(driver, FindElement(driver, "//p[text()='"+landingPage+"']", landingPage,
					action.SCROLLANDBOOLEAN, 10), "visibility", 10, landingPage);
			if (ele != null) {
				return true;
			}
		return false;
	}

	
}
