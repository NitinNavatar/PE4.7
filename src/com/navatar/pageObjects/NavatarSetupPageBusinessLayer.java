package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.CheckBox;
import com.navatar.generic.EnumConstants.ClickOrCheckEnableDisableCheckBox;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTab;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTabLayoutSection;
import com.navatar.generic.EnumConstants.NotApplicable;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.List;
public class NavatarSetupPageBusinessLayer extends NavatarSetupPage implements NavatarSetUpPageErrorMessage{

	public NavatarSetupPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**@author Akul Bhutani
	 * @param projectName
	 * @param timeOut
	 * @return WebElement
	 * @description this is used to find checkbox to check for contact and return element
	 */
	public WebElement getRelateMultipleContactsToTaskAndEventCheckBox(String projectName,int timeOut) {
		String xpath="";
		xpath="//span[contains(text(),'"+RelateMultipleContactsToTaskAndEventsMsg+"')]/../preceding-sibling::td//input";
		WebElement ele = FindElement(driver, xpath, RelateMultipleContactsToTaskAndEventsMsg+" Check Box", action.SCROLLANDBOOLEAN, timeOut);
		return ele;
	}
	
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param timeOut
	 * @return checkbox for roll up activities to account
	 * @description returns checkbox for roll up activities to contact primary account
	 */
	public WebElement getRollUpActivitiesToContactPrimaryAccountCheckBox(String projectName,int timeOut) {
		String xpath="";
		xpath="//*[contains(text(),'Roll up activities to a contac')]/preceding-sibling::td/input";
		WebElement ele = FindElement(driver, xpath, RollUpActivitiesToContactPrimaryAccount+" Check Box", action.SCROLLANDBOOLEAN, timeOut);
		return ele;
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @param Menu
	 * @return true if able to click on NavatarSetup SideMenu Tab
	 */
	public boolean clickOnNavatarSetupSideMenusTab(String projectName,NavatarSetupSideMenuTab Menu) {
	
			if(switchToFrame(driver, 60, getnavatarSetUpTabFrame_Lighting(projectName, 120))){
				appLog.info("Inside Frame");
				System.err.println("Inside Frame");
			}
			
	
		
		boolean flag = false;
		String sideMenu = null;
		switch (Menu) {
		
		case ContactTransfer:
			sideMenu = "Contact Transfer";
			break;
		case BulkEmail:
			sideMenu = "Bulk Email";
			break;
		case OfficeLocations:
			sideMenu = "Office Locations";
			break;
		case CommitmentCreation:
			sideMenu = "Commitment Creation";
			break;
		case IndividualInvestorCreation:
			sideMenu = "Individual Investor Creation";
			break;
		case DealCreation:
			sideMenu = "Deal Creation";
			break;
		default:
			return false;
		}

		if (click(driver,
				isDisplayed(driver,
						FindElement(driver, "//div[@class='ContentStart']//li/a[contains(@title,'" + sideMenu + "')]", sideMenu,action.SCROLLANDBOOLEAN, 120),
						"visibility", 30, sideMenu),
				sideMenu, action.SCROLLANDBOOLEAN)) {
			appLog.info("Clicked on " + sideMenu);
			ThreadSleep(8000);
			refresh(driver);
			switchToDefaultContent(driver);
			ThreadSleep(8000);
			switchToFrame(driver, 60, getnavatarSetUpTabFrame_Lighting(projectName, 120));
			flag=true;

		}else{
			appLog.error("Not Able to Clicked on : " + sideMenu);	
		}
		
//		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
//			switchToDefaultContent(driver);
//		}
//		
		return flag;
	}
	
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param editviewMode
	 * @param timeOut
	 * @return dropdown webelement
	 * @description this method returns dropdown of keep activities at
	 */
	public WebElement getKeepActivitiesAtSelectList(String projectName, EditViewMode editviewMode, int timeOut) {

		List<WebElement> keepactivitiesList = FindElements(driver,
				"//label[text()='Keep activities at']/../following-sibling::td//select",
				"Keep Activities Select List");

		if (EditViewMode.View.toString().equalsIgnoreCase(editviewMode.toString())) {
			return isDisplayed(driver, keepactivitiesList.get(0), "Visibility", timeOut,
					"Keep activities At Select");
		} else {
			return isDisplayed(driver, keepactivitiesList.get(1), "Visibility", timeOut,
					"Keep activities at select");
		}

	}
	
	/**
	 * @return the getIncludeActivitiesSelectList
	 */
	public WebElement getIncludeActivitiesSelectList(String projectName, EditViewMode editviewMode, int timeOut) {

		List<WebElement> includeactivitiesList = FindElements(driver,
				"//label[text()='Include activities related to']/../following-sibling::td//select",
				"Include activities related to Select List");

		if (EditViewMode.View.toString().equalsIgnoreCase(editviewMode.toString())) {
			return isDisplayed(driver, includeactivitiesList.get(0), "Visibility", timeOut,
					"Include activities At Select");
		} else {
			return isDisplayed(driver, includeactivitiesList.get(1), "Visibility", timeOut,
					"Include activities at select");
		}

	}
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param editViewMode
	 * @param checkBox
	 * @param keepActivityValue
	 * @param includeActivityValue
	 * @return SoftAssert
	 * @description this method is used to verify contact transfer complete tab on navatar setup
	 */
	public SoftAssert verifyingContactTransferTab(String projectName,EditViewMode editViewMode,CheckBox checkBox,String keepActivityValue,String includeActivityValue){
		SoftAssert saa = new SoftAssert();
		
		if(checkBox.toString().equalsIgnoreCase(CheckBox.Checked.toString())){
			if (isSelected(driver, getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName, NavatarSetupSideMenuTab.ContactTransfer, editViewMode, ClickOrCheckEnableDisableCheckBox.EnableOrDisable, 10), "Enabled CheckBox")) {
				log(LogStatus.INFO, "verifyingContactTransferTab method output Enable Contact Transfer is checked",YesNo.No);
			} else {
				saa.assertTrue(false, "verifyingContactTransferTab method output Enable Contact Transfer is Unchecked");
				log(LogStatus.SKIP, "verifyingContactTransferTab method output Enable Contact Transfer is Unchecked",YesNo.Yes);	
			}
		}else{
			if (!isSelected(driver, getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName, NavatarSetupSideMenuTab.ContactTransfer, editViewMode, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled CheckBox")) {
				log(LogStatus.INFO, "verifyingContactTransferTab method output Enable Contact Transfer is Unchecked",YesNo.No);
			} else {
				saa.assertTrue(false, "verifyingContactTransferTab method output Enable Contact Transfer is Already checked");
				log(LogStatus.SKIP, "verifyingContactTransferTab method output Enable Contact Transfer is Already checked",YesNo.Yes);	
			}
		}
		
		
		String defaultvalue=getSelectedOptionOfDropDown(driver, getKeepActivitiesAtSelectList(projectName, editViewMode, 10), keepActivityValue, "Text");
		if(keepActivityValue.equalsIgnoreCase(defaultvalue)){
			log(LogStatus.INFO, "Keep Activities Value Matched: "+defaultvalue, YesNo.No);
		}
		else {
			saa.assertTrue(false, "Keep Activities value not matched Actual : "+defaultvalue+" \t Expected : "+keepActivityValue);
			log(LogStatus.INFO, "Keep Activities value not matched Actual : "+defaultvalue+" \t Expected : "+keepActivityValue, YesNo.Yes);
		}
		
		 defaultvalue=getSelectedOptionOfDropDown(driver, getIncludeActivitiesSelectList(projectName, editViewMode, 10), includeActivityValue, "Text");
			if(includeActivityValue.equalsIgnoreCase(defaultvalue)){
				log(LogStatus.INFO, "Include Activities Related to Value Matched: "+defaultvalue, YesNo.No);
			}
			else {
				saa.assertTrue(false, "Include Activities Related to value not matched Actual : "+defaultvalue+" \t Expected : "+includeActivityValue);
				log(LogStatus.INFO, "Include Activities Related to  value not matched Actual : "+defaultvalue+" \t Expected : "+includeActivityValue, YesNo.Yes);
			}
		
		return saa;
	}
	
	
	/**@author Akul Bhutani
	 * @param projectName
	 * @param timeOut
	 * @return WebElement
	 * @description this is used to find checkbox to check for contact and return element
	 */
	public WebElement getRelateMultipleContactsToTaskAndEventFeatureEnabled(String projectName,int timeOut) {
		String xpath="";
		xpath="//span[contains(text(),'"+RelateMultipleContactsToTaskAndEventsMsg+"')]//*[text()='Feature enabled.']";
		WebElement ele = FindElement(driver, xpath, RelateMultipleContactsToTaskAndEventsMsg+" Feature Enabled", action.SCROLLANDBOOLEAN, timeOut);
		return ele;
	}
	
	/**
	 * @param projectName
	 * @param keepActivityEnum
	 * @return keep Activities Value on the basis of KeepActivityEnum from contact Transfer SetUp Tab
	 */
	public String keepActivitiesValue(String projectName, KeepActivityEnum keepActivityEnum) {
		String value="";
		
		if (ProjectName.MNA.toString().equalsIgnoreCase(projectName)) {
			
			if (KeepActivityEnum.OldInstitutionOnly.equals(keepActivityEnum)) {
				value="Old Account Only";
			} else {
				value="Old and New Accounts";
			}
		} else {

			if (KeepActivityEnum.OldInstitutionOnly.equals(keepActivityEnum)) {
				value="Old Institution Only";
			} else {
				value="Old and New Institutions";
			}
			
		}
		return value;
	}
	
	/**
	 * @param projectName
	 * @param keepActivityEnum
	 * @return keep Include Value on the basis of InculdeActivityEnum from contact Transfer SetUp Tab
	 */
	public String includeActivitiesValue(String projectName, InculdeActivityEnum inculdeActivityEnum) {
		String value="";
		
		if (ProjectName.MNA.toString().equalsIgnoreCase(projectName)) {
			
			if (InculdeActivityEnum.ContactOnly.equals(inculdeActivityEnum)) {
				value="Contact Only";
			} else if (InculdeActivityEnum.ContactAndInstitution.equals(inculdeActivityEnum)) {
				value="Contact and Account";
			}else {
				value="Contact, Account and Custom Object";
			}
		} else {

			if (InculdeActivityEnum.ContactOnly.equals(inculdeActivityEnum)) {
				value="Contact Only";
			} else if (InculdeActivityEnum.ContactAndInstitution.equals(inculdeActivityEnum)) {
				value="Contact and Institution";
			}else {
				value="Contact, Institution and Custom Object";
			}
			
		}
		return value;
	}
	
}

