package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.CommonVariables;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.List;

public class PartnershipsPageBusinessLayer extends PartnershipsPage {

	public PartnershipsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param projectName
	 * @param environment
	 * @param mode
	 * @param partnershipLegalName
	 * @param fund
	 * @return true if able to create partnership
	 */
	public boolean createPartnership(String projectName, String environment,String mode,String partnershipLegalName, String fund) {
		ThreadSleep(5000);
		if (click(driver, getNewButton(environment,mode,60), "New Button", action.BOOLEAN)) {
			if (sendKeys(driver, getPartnershipLegalName(environment,mode,60), partnershipLegalName, "Partnership Legal Name",
					action.BOOLEAN)) {
				if (sendKeys(driver, getFundTextBox(environment,mode,60), fund, "Fund Text Box", action.BOOLEAN)) {
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						ThreadSleep(1000);
						if (click(driver,
								FindElement(driver,
										"//*[contains(@class,'slds-listbox__option-text')]/*[@title='"+fund+"']",
										"fund Name List", action.THROWEXCEPTION, 30),
								fund + "   :   fund Name", action.BOOLEAN)) {
							appLog.info(fund + "  is present in list.");
						} else {
							appLog.info(fund + "  is not present in the list.");
						}
					}
					if (click(driver, getCustomTabSaveBtn(projectName,60), "Save Button", action.BOOLEAN)) {
						if (getPartnershipNameInViewMode(environment,mode,60,partnershipLegalName) != null) {
							String partnershipName = getText(driver, getPartnershipNameInViewMode(environment,mode,60,partnershipLegalName),
									"Partnership name in view mode", action.BOOLEAN);
							if (partnershipName.equalsIgnoreCase(partnershipLegalName)) {
								appLog.info("Partnership created successfully.:" + partnershipLegalName);
								return true;
							} else {
								appLog.error("Partnership is not created successfully." + partnershipLegalName);
							}
						} else {
							appLog.error("Partnership name is not displaying");
						}
					} else {
						appLog.error("Not able to click on save button");
					}
				} else {
					appLog.error("Not able to enter value in fund text box");
				}
			} else {
				appLog.error("Not able to enter value in partnershp legal name text box");
			}
		} else {
			appLog.error("Not able to click on new button so we cannot create partnership");
		}
		return false;
	}

	
	
}
