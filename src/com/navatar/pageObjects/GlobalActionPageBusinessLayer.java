package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.SmokeCommonVariables.Smoke_STDTask1Subject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.TaskRayProjectButtons;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;

import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;
public class GlobalActionPageBusinessLayer extends GlobalActionPage {

	public GlobalActionPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param globalActionItem
	 * @param labelFieldTextBox
	 * @param timeOut
	 * @return WebElement
	 * @description this method is used to find textbox present on global action page and return element
	 */
	public WebElement getLabelTextBoxForGobalAction(String projectName,GlobalActionItem globalActionItem,String labelFieldTextBox,int timeOut) {
		
		WebElement ele=null;
		String labelTextBox = labelFieldTextBox.replace("_", " ");
		String xpath="";
		if (PageLabel.Subject.toString().equals(labelFieldTextBox)) {
			 xpath="//label[text()='"+labelTextBox+"']/..//input";	
		}else if (PageLabel.Start_Date.toString().equals(labelFieldTextBox)) {
			 xpath="(//label[contains(text(),'Date')]/following-sibling::div//input)[1]";	
		}else if (PageLabel.End_Date.toString().equals(labelFieldTextBox)) {
			 xpath="(//label[contains(text(),'Date')]/following-sibling::div//input)[2]";	
		}else if (PageLabel.Start_Time.toString().equals(labelFieldTextBox)) {
			 xpath="(//label[contains(text(),'Time')]/following-sibling::div//input)[1]";	
		}else if (PageLabel.End_Time.toString().equals(labelFieldTextBox)) {
			 xpath="(//label[contains(text(),'Time')]/following-sibling::div//input)[2]";	
		}else if (PageLabel.Description.toString().equals(labelFieldTextBox)) {
			 xpath="//span[text()='Description']/../following-sibling::div//textarea";	
		}else 	if (PageLabel.Due_Date.toString().equals(labelFieldTextBox)) {
			 xpath="//span[text()='"+labelTextBox+"']/../..//input";
		}else if (PageLabel.Location.toString().equals(labelFieldTextBox)) {
			 xpath="//span[text()='Location']/../following-sibling::input";
		}else {
			 xpath="//label[text()='"+labelTextBox+"']/..//input";		
		}
		ele = FindElement(driver, xpath, labelTextBox, action.SCROLLANDBOOLEAN, timeOut);
		ele =isDisplayed(driver, ele, "Visibility", timeOut, labelTextBox);		
		return ele;
	}
	
	/**@author Akul Bhutani
	 * @param projectName
	 * @param globalActionItem
	 * @param labelsWithValues
	 * @param timeOut
	 * @return true/false
	 * @description this method is used to enter values in fields for creating new event
	 */
	public boolean enterValueForNewEvent(String projectName,GlobalActionItem globalActionItem,String[][] labelsWithValues,int timeOut) {

		boolean flag=true;
		WebElement ele;
		String xpath="";
		String label="";
		String value="";

		for (String[] labelWithValue : labelsWithValues) {
			label=labelWithValue[0];
			value=labelWithValue[1];
			
			if (PageLabel.Subject.toString().equals(label) || PageLabel.Start_Date.toString().equals(label) || PageLabel.Start_Time.toString().equals(label) 
					|| PageLabel.End_Date.toString().equals(label) || PageLabel.End_Time.toString().equals(label)|| PageLabel.Description.toString().equals(label) ||  PageLabel.Due_Date.toString().equals(label)) {

				
				ele=getLabelTextBoxForGobalAction(projectName, globalActionItem, label,timeOut);
				try {
					ele.sendKeys(" ");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (sendKeys(driver, ele, value, label, action.BOOLEAN)) {
					log(LogStatus.INFO, "Entered value to : "+label, YesNo.Yes);
					ThreadSleep(1000);
				}
				else {
					flag=false;
					log(LogStatus.ERROR, globalActionItem+"Can not be created as not able to enter Value for : "+label, YesNo.Yes);
					BaseLib.sa.assertTrue(false,globalActionItem+"Can not be created as not able to enter Value for : "+label );
				}
			}else if(PageLabel.Meeting_Type.toString().equals(label) || PageLabel.Status.toString().equals(label)) {
				
				if (selectDropDownValueonTaskPopUp(projectName, PageName.NewEventPopUp, label, value, action.SCROLLANDBOOLEAN, timeOut)) {
					log(LogStatus.INFO, "Selected : "+value+" For Label : "+label, YesNo.No);	
					ThreadSleep(1000);	

				}else {
					flag=false;
					log(LogStatus.ERROR, "Not Able to Select : "+value+" For Label : "+label, YesNo.Yes);	
					BaseLib.sa.assertTrue(false, "Not Able to Select : "+value+" For Label : "+label);	
				}
			}else if(PageLabel.Name.toString().equals(label)) {
				
				flag=selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewEventPopUp, label, TabName.Other, value, action.SCROLLANDBOOLEAN,timeOut);		
				if (flag) {
					log(LogStatus.INFO,"Selected "+value+" For Label "+label,YesNo.No);

				} else {
					flag=false;
					BaseLib.sa.assertTrue(false,"Not Able to Select "+value+" For Label "+label);
					log(LogStatus.ERROR,"Not Able to Select "+value+" For Label "+label,YesNo.Yes);

				}
			}
		}
		return flag;

	}
}
