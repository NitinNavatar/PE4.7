/**
 * 
 */
package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.BaseLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.CSVLabel;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.AppListeners.*;
import static com.navatar.generic.CommonLib.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.management.ObjectName;

/**
 * @author Parul Singh
 *
 */
public class MarketingProspectPageBusinessLayer extends MarketingProspectPage  {
	
	/**
	 * @param driver
	 */
	public MarketingProspectPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public boolean createMPItem(String projectName,String miName ,String[][] labelWithValue,int timeOut) {
		String navigationTab="Marketing Prospects";
		boolean flag=false;
		if (clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
			
			String[] viewLists = {"All","Recently Viewed"};
			click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN);
			for (String viewList : viewLists) {
				WebElement ele = getViewListElement(viewList);;
				if (ele!=null) {
					log(LogStatus.INFO, viewList+" is present on "+navigationTab , YesNo.No);
				} else {
					log(LogStatus.ERROR, viewList+" should be present on "+navigationTab, YesNo.Yes);
					sa.assertTrue(false,  viewList+" should be present on "+navigationTab);
				}	
			}
						
			if(clickUsingJavaScript(driver, getNewButton(projectName, 10), "new button")) {
				log(LogStatus.INFO, "Click on new button going to create ", YesNo.No);
				enteringValueForMP(projectName, labelWithValue, action.BOOLEAN, timeOut);
				if (click(driver,  getNavigationTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.ERROR, "Click on save Button ", YesNo.No);
					WebElement ele = getCreatedConfirmationMsg(projectName, 15);
					if (ele!=null) {
						String actualValue = ele.getText().trim();
						String[] s = actualValue.split("\"");
						String expectedValue="Marketing Prospect MP -  was created.";
						if (s[0].equals("Marketing Prospect ") && s[1].contains("MP - ") &&s[2].equals(" was created.")) {
							log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);
						} else {
							log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
						}
					} else {
						sa.assertTrue(false,"Created Msg Ele not Found");
						log(LogStatus.SKIP,"Created Msg Ele not Found",YesNo.Yes);
					}
					ThreadSleep(5000);
					flag=true;
				} else {
					log(LogStatus.ERROR, "Not Able to Click on save Button ", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on save Button ");
				}
				
			}else {
				log(LogStatus.ERROR, "Not Able to Click on new button so cannot create", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on new button so cannot create");

			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
		}
		return flag;
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param navigationField
	 * @param action
	 * @param timeOut
	 * @return Navigation Field webElement
	 */
	public WebElement getCreationLabelField(String projectName,String navigationField,action action,int timeOut) {
		navigationField=navigationField.replace("_", " ");
		String xpath = "//*[text()='"+navigationField+"']/following-sibling::div//input";
		WebElement ele = FindElement(driver, xpath, navigationField, action, timeOut);
		scrollDownThroughWebelement(driver, ele, navigationField);
		return isDisplayed(driver, ele, "Visibility", timeOut, navigationField);
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param navigationFieldWithValues
	 * @param action
	 * @param timeOut
	 * method ebnter valu on navigation popup
	 */
	public void enteringValueForMP(String projectName,String[][] navigationFieldWithValues,action action,int timeOut) {
		String navigationField;
		String navigationvalue;
		WebElement ele;
		for (String[] navigationFieldAndvalue : navigationFieldWithValues) {
			navigationField=navigationFieldAndvalue[0];
			navigationvalue = navigationFieldAndvalue[1];
			if (navigationField.equalsIgnoreCase(excelLabel.Status.toString())) {
				ele = getDropdownOnCreationPopUp(projectName, PageName.Marketing_Prospect, navigationField, action.BOOLEAN, timeOut);
				if (click(driver,ele,navigationField+" with value "+navigationvalue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationField, YesNo.No);
					if (SelectDropDownValue(projectName, PageName.Marketing_Prospect, navigationField, navigationvalue, action, timeOut)) {
						log(LogStatus.INFO, "Selected "+navigationvalue+" for "+navigationField, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not Able to Select "+navigationvalue+" for "+navigationField, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Select "+navigationvalue+" for "+navigationField);

					}

				} else {
					log(LogStatus.ERROR, "Not ABle to Click on "+navigationField , YesNo.Yes);
					sa.assertTrue(false, "Not ABle to Click on "+navigationField);

				}
			}else{
				ele =getCreationLabelField(projectName, navigationField, action, 20);
				if (sendKeys(driver, ele, navigationvalue, navigationField, action)) {
					log(LogStatus.INFO, "Able to enter "+navigationField, YesNo.No);
					

					if (navigationField.equalsIgnoreCase(excelLabel.Marketing_Initiative.toString()) || navigationField.equalsIgnoreCase(excelLabel.Contact.toString())) {
						ThreadSleep(10000);
						if (click(driver,getItemInList(projectName, navigationvalue, action.BOOLEAN, 20),
								navigationvalue + "   :  Parent Name", action.BOOLEAN)) {
							log(LogStatus.INFO, navigationvalue+" is available", YesNo.No);
						} else {
							log(LogStatus.ERROR, navigationvalue+" is not available", YesNo.Yes);
							sa.assertTrue(false, navigationvalue+" is not available");

						}	
					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to enter "+navigationField, YesNo.Yes);
					sa.assertTrue(false,"Not Able to enter "+navigationField);
				}	
			}

		}

	}
}
