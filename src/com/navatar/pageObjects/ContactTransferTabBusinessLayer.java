package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElements;
import static com.navatar.generic.CommonLib.getSelectedOptionOfDropDown;
import static com.navatar.generic.CommonLib.isDisplayed;
import static com.navatar.generic.CommonLib.isSelected;
import static com.navatar.generic.CommonLib.log;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.EnumConstants.CheckBox;
import com.navatar.generic.EnumConstants.ClickOrCheckEnableDisableCheckBox;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTab;
import com.navatar.generic.EnumConstants.YesNo;
import com.relevantcodes.extentreports.LogStatus;
import com.navatar.generic.SoftAssert;

public class ContactTransferTabBusinessLayer extends ContactTransferTab implements ContactTransferTabErrorMessage{

	public ContactTransferTabBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the getKeepActivitiesAtSelectList
	 */
	public WebElement getKeepActivitiesAtSelectList(String environment, String mode, EditViewMode editviewMode, int timeOut) {

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
	public WebElement getIncludeActivitiesSelectList(String environment, String mode, EditViewMode editviewMode, int timeOut) {

		List<WebElement> keepactivitiesList = FindElements(driver,
				"//label[text()='Include activities related to']/../following-sibling::td//select",
				"Include activities related to Select List");

		if (EditViewMode.View.toString().equalsIgnoreCase(editviewMode.toString())) {
			return isDisplayed(driver, keepactivitiesList.get(0), "Visibility", timeOut,
					"Keep activities At Select");
		} else {
			return isDisplayed(driver, keepactivitiesList.get(1), "Visibility", timeOut,
					"Keep activities at select");
		}

	}
	
	public SoftAssert verifyingContactTransferTab(String environment,String mode,EditViewMode editViewMode,CheckBox checkBox,String keepActivityValue,String includeActivityValue){
		SoftAssert saa = new SoftAssert();
		
		if(checkBox.toString().equalsIgnoreCase(CheckBox.Checked.toString())){
			if (isSelected(driver, getEnableCheckBoxforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.ContactTransfer, editViewMode, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled CheckBox")) {
				log(LogStatus.INFO, "Enable Contact Transfer is checked",YesNo.No);
			} else {
				saa.assertTrue(false, "Enable Contact Transfer is Unchecked");
				log(LogStatus.SKIP, "Enable Contact Transfer is Unchecked",YesNo.Yes);	
			}
		}else{
			if (!isSelected(driver, getEnableCheckBoxforNavatarSetUpSideMenuTab(environment, mode, NavatarSetupSideMenuTab.ContactTransfer, editViewMode, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled CheckBox")) {
				log(LogStatus.INFO, "Enable Contact Transfer is Unchecked",YesNo.No);
			} else {
				saa.assertTrue(false, "Enable Contact Transfer is Already checked");
				log(LogStatus.SKIP, "Enable Contact Transfer is Already checked",YesNo.Yes);	
			}
		}
		
		
		String defaultvalue=getSelectedOptionOfDropDown(driver, getKeepActivitiesAtSelectList(environment, mode, editViewMode, 10), keepActivityValue, "Text");
		if(keepActivityValue.equalsIgnoreCase(defaultvalue)){
			log(LogStatus.INFO, "Keep Activities Value Matched: "+defaultvalue, YesNo.No);
		}
		else {
			saa.assertTrue(false, "Keep Activities value not matched Actual : "+defaultvalue+" \t Expected : "+keepActivityValue);
			log(LogStatus.INFO, "Keep Activities value not matched Actual : "+defaultvalue+" \t Expected : "+keepActivityValue, YesNo.Yes);
		}
		
		 defaultvalue=getSelectedOptionOfDropDown(driver, getIncludeActivitiesSelectList(environment, mode, editViewMode, 10), includeActivityValue, "Text");
			if(includeActivityValue.equalsIgnoreCase(defaultvalue)){
				log(LogStatus.INFO, "Include Activities Related to Value Matched: "+defaultvalue, YesNo.No);
			}
			else {
				saa.assertTrue(false, "Include Activities Related to value not matched Actual : "+defaultvalue+" \t Expected : "+includeActivityValue);
				log(LogStatus.INFO, "Include Activities Related to  value not matched Actual : "+defaultvalue+" \t Expected : "+includeActivityValue, YesNo.Yes);
			}
		
		return saa;
	}

}
