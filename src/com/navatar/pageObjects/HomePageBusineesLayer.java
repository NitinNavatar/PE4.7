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
				log(LogStatus.ERROR, "setting icon", YesNo.Yes);
				return flag;
			}
		if(click(driver, getUserMenuSetupLink(20), "setup link", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "setup link", YesNo.No);
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
	
	
	
	
}
