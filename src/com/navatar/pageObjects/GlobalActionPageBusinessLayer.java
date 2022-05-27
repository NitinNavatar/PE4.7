package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.SmokeCommonVariables.Smoke_STDTask1Subject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
			}else if(PageLabel.All_Day_Event.toString().equals(label)) {
				HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
				click(driver, hp.getAllDayEventCheckBox(10), "all day event check box", action.SCROLLANDBOOLEAN);
			}
		}
		return flag;

	}

	/**
	 * @param projectName
	 * @param globalActionItem
	 * @param labelsWithValue
	 * @return true if able to click on Global action and enter value
	 */
	public boolean clickOnGlobalActionAndEnterValue(String projectName,GlobalActionItem globalActionItem,String[][] labelsWithValue){
		
		if (click(driver,getGlobalActionIcon(projectName, 60), "Global Action Related item", action.BOOLEAN)) {
			log(LogStatus.INFO,"Clicked on Global Action Related item",YesNo.No);
			if (clickUsingJavaScript(driver, getActionItem(projectName, globalActionItem, 60), globalActionItem.toString(), action.BOOLEAN)) {
				log(LogStatus.INFO,"Clicked on "+globalActionItem,YesNo.No);
				click(driver,getMaximizeIcon(projectName, 30), "Maximize Icon", action.BOOLEAN);
				
				boolean flag1 = enterValueForNewEvent(projectName, GlobalActionItem.New_Task , labelsWithValue, 10);;
			} else {
				sa.assertTrue(false,"Not Able to Click on "+globalActionItem);
				log(LogStatus.SKIP,"Not Able to Click on "+globalActionItem,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Global Action Related item");
			log(LogStatus.SKIP,"Not Able to Click on Global Action Related item",YesNo.Yes);
		}
		
		return true;
	}
	
	/**
	 * @param projectName
	 * @param pageName
	 * @param tabName
	 * @param relatedValue
	 * @return boolean
	 */
	public boolean enterValueOnRelatedTo(String projectName,PageName pageName,TabName tabName,String relatedValue){
		boolean flag=false;
		clickUsingJavaScript(driver, getrelatedAssociationsdropdownButton(projectName, pageName,PageLabel.Related_To.toString(), action.BOOLEAN, 10),"dropdown button", action.BOOLEAN);
		flag = selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, pageName, PageLabel.Related_To.toString(), tabName, relatedValue, action.SCROLLANDBOOLEAN,20);	
		if (flag) {
			log(LogStatus.INFO,"Selected "+relatedValue+" For Label "+PageLabel.Related_To,YesNo.No);
		} else {
			sa.assertTrue(false,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_To);
			log(LogStatus.SKIP,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_To,YesNo.Yes);

		}
		return flag;
		
	}
	
	/**
	 * @param projectName
	 * @param pageName
	 * @param labelsWithValues
	 * @param timeOut
	 * @return boolean
	 */
	public boolean enterValueForTask(String projectName,PageName pageName,String[][] labelsWithValues,int timeOut) {

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

				
				ele=getLabelTextBoxForTask(projectName, pageName, label,timeOut);
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
					log(LogStatus.ERROR, "Can not be created as not able to enter Value for : "+label, YesNo.Yes);
					BaseLib.sa.assertTrue(false,"Can not be created as not able to enter Value for : "+label );
				}
			}else if(PageLabel.Meeting_Type.toString().equals(label) || PageLabel.Status.toString().equals(label)) {
				
				if (selectDropDownValueonTaskPopUp(projectName, pageName, label, value, action.SCROLLANDBOOLEAN, timeOut)) {
					log(LogStatus.INFO, "Selected : "+value+" For Label : "+label, YesNo.No);	
					ThreadSleep(1000);	

				}else {
					flag=false;
					log(LogStatus.ERROR, "Not Able to Select : "+value+" For Label : "+label, YesNo.Yes);	
					BaseLib.sa.assertTrue(false, "Not Able to Select : "+value+" For Label : "+label);	
				}
			}else if(PageLabel.Name.toString().equals(label)) {
				
				flag=selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, pageName, label, TabName.Other, value, action.SCROLLANDBOOLEAN,timeOut);		
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
	
	
	/**
	 * @param projectName
	 * @param pageName
	 * @param labelFieldTextBox
	 * @param timeOut
	 * @return WebElement
	 */
	public WebElement getLabelTextBoxForTask(String projectName,PageName pageName,String labelFieldTextBox,int timeOut) {
		
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
	@FindBy(xpath="//button[@title='Add']")
	private WebElement Addbutton;

	/**
	 * @return the taskRayFrame
	 */
	public WebElement getAddbutton(String projectName,int timeOut) {
		return isDisplayed(driver, Addbutton, "Visibility", timeOut, "Addbutton");
	}

}
