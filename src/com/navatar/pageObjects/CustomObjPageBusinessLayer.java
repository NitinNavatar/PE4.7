package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;

import org.openqa.selenium.WebDriver;

import com.navatar.generic.SmokeCommonVariables;
import com.navatar.generic.EnumConstants.action;

public class CustomObjPageBusinessLayer extends CustomObjPage{

	public CustomObjPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**@author Akul Bhutani
	 * @param projectName
	 * @param recordType TODO
	 * @param recordName
	 * @param value
	 * @param clickNew TODO
	 * @return true/false
	 * @description this is used to create record of custom object page
	 */
	public boolean createRecord(String projectName, String recordType, String recordName, String value, boolean isCreatedFromTaskLayout) {
	//	refresh(driver);
		ThreadSleep(10000);
		if (!isCreatedFromTaskLayout) {
			if(clickUsingJavaScript(driver, getNewButton(projectName, 60), "new button")) {
				appLog.info("clicked on new button");
			}else {
				appLog.error("Not able to click on new button so cannot create new record : "+value);
				return false;
			}
		}
			if (!recordType.equals("") || !recordType.isEmpty()) {
				ThreadSleep(2000);
				if(click(driver, getRadioButtonforRecordType(recordType, isCreatedFromTaskLayout,5), "Radio Button for : "+recordType, action.SCROLLANDBOOLEAN)){
					appLog.info("Clicked on radio Button  for record type : "+recordType);
					if (click(driver, getContinueOrNextButton(projectName,5), "Continue Button", action.BOOLEAN)) {
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
		
		
		if (sendKeys(driver, getFieldTextBox(20), value, SmokeCommonVariables.tabCustomObjField, action.SCROLLANDBOOLEAN)) {

			if (click(driver, getCustomTabSaveBtn(projectName,60), "Save Button", action.BOOLEAN)) {
				if (getRecordNameInViewMode(projectName, 60) != null) {
					ThreadSleep(2000);
					String recordNameViewMode = getText(driver, getRecordNameInViewMode(projectName, 60),
							"Custom object Record Name", action.BOOLEAN);
					if (recordNameViewMode.contains(value)) {
						appLog.info("Record is created successfully.:" + value);
						return true;
					}
					else {
						appLog.error("record value does not match: "+recordNameViewMode+" and "+value);
					}
				}
				else {
					appLog.error("record value is not visible");
				}
			}
			else {
				appLog.error("save button is not clickable so cannot create record in custom object");
			}
		}
		else {
			appLog.error("field text box is not visible so cannot create new record");
		}
		return false;
	}
}
