/**
 * 
 */
package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.CSVLabel;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
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
public class AffiliationPageBusinessLayer extends AffiliationPage  {
	
	/**
	 * @param driver
	 */
	public AffiliationPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public boolean createAffiliationItem(String projectName,String[][] labelWithValue,int timeOut) {
		String navigationTab="Affiliations";
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
				enteringValueForAffiliation(projectName, labelWithValue, action.BOOLEAN, timeOut);
				if (click(driver,  getNavigationTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.ERROR, "Click on save Button ", YesNo.No);
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
	public void enteringValueForAffiliation(String projectName,String[][] navigationFieldWithValues,action action,int timeOut) {
		String navigationField;
		String navigationvalue;
		WebElement ele;
		for (String[] navigationFieldAndvalue : navigationFieldWithValues) {
			navigationField=navigationFieldAndvalue[0];
			navigationvalue = navigationFieldAndvalue[1];
			ele =getCreationLabelField(projectName, navigationField, action, 20);

			if (navigationField.equalsIgnoreCase(excelLabel.Role.toString())) {
				String xpath = "//div[@class='slds-dueling-list__column slds-dueling-list__column_responsive']//li//*[text()='"+navigationvalue+"']";
				 ele = FindElement(driver, xpath, "Role : "+navigationvalue, action, timeOut);
				 
					if (click(driver,ele,"Role : "+navigationvalue, action.BOOLEAN)) {
						log(LogStatus.INFO, "Role : "+navigationvalue+" is available", YesNo.No);
						
						if (click(driver,getAddButtonMultipleBox(timeOut),"Role : "+navigationvalue, action.BOOLEAN)) {
							log(LogStatus.INFO, "Role : "+navigationvalue+" is selected & moved", YesNo.No);
						} else {
							log(LogStatus.ERROR, "Role : "+navigationvalue+" is not selected & moved", YesNo.Yes);
							sa.assertTrue(false, "Role : "+navigationvalue+" is not selected & moved");

						}
						
					} else {
						log(LogStatus.ERROR, "Role : "+navigationvalue+" is not available", YesNo.Yes);
						sa.assertTrue(false, "Role : "+navigationvalue+" is not available");

					}
			}else{
				if (sendKeys(driver, ele, navigationvalue, navigationField, action)) {
					log(LogStatus.INFO, "Able to enter "+navigationField, YesNo.No);

					if (navigationField.equalsIgnoreCase(excelLabel.Institution.toString()) || navigationField.equalsIgnoreCase(excelLabel.Contact.toString())) {
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
