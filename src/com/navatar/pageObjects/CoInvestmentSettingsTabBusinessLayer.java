package com.navatar.pageObjects;
import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;
import org.openqa.selenium.WebDriver;

import com.navatar.generic.EnumConstants.Mode;
import com.relevantcodes.extentreports.LogStatus;

public class CoInvestmentSettingsTabBusinessLayer extends NavatarSetupPageBusinessLayer{

	public CoInvestmentSettingsTabBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public boolean activateCoInvestmentSetting(String environment, String mode,String fundName) {
		boolean flag = false;
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			if(switchToFrame(driver, 10, getnavatarSetUpTabFrame_Lighting(environment, 10))){
				appLog.info("Inside Frame");
				System.err.println("Inside Frame");
			}
			
		}
		if(click(driver,getEditButtonforNavatarSetUpSideMenuTab(environment,  NavatarSetupSideMenuTab.CoInvestmentSettings, 30),"edit button", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(2000);
			if(click(driver, getEnableCheckBoxforClickNavatarSetUpSideMenuTab(environment,  NavatarSetupSideMenuTab.CoInvestmentSettings, EditViewMode.Edit, 30), "Automatically link any Co-investment Fundraisings or Commitments to this Fund check box", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Automatically link any Co-investment Fundraisings or Commitments to this Fund check box", YesNo.No);
				if(click(driver, getCoInvestmentFundLookUpIcon(20), "co investment look up icon", action.SCROLLANDBOOLEAN)) {
					if(selectValueFromLookUpWindow(fundName)) {
						log(LogStatus.INFO, fundName+" fund Name is selected successfully", YesNo.No);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 10, getnavatarSetUpTabFrame_Lighting(environment, 10));
							
						}
						if(click(driver, getSaveButtonforNavatarSetUpSideMenuTab(environment,  NavatarSetupSideMenuTab.CoInvestmentSettings, 30, TopOrBottom.TOP), "save button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on save button successfully", YesNo.Yes);
							flag=true;
							ThreadSleep(5000);
						}else {
							log(LogStatus.ERROR, "Not able to Click on save button so cannot activate co-investment settings", YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to select fund Name from look up PopUp so cannot activate co-investment settings", YesNo.Yes);
					}
					
				}else {
					log(LogStatus.ERROR, "Not able to click on co investment look up icon so cannot activate co investment setting", YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, "Not ale to click on Automatically link any Co-investment Fundraisings or Commitments to this Fund check box so cannot activate co investment setting",YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR,"Not able to click on co-investment edit button so cannot activate setting", YesNo.Yes);
		}
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToDefaultContent(driver);
		}
		return flag;
		
	}

}
