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
import com.navatar.generic.EnumConstants.PageLabel;
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
public class FinancingPageBusinessLayer extends FinancingPage  {
	
	/**
	 * @param driver
	 */
	public FinancingPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public boolean createFinancingItem(String projectName,String[][] labelWithValue,int timeOut) {
		String navigationTab="Financing";
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
				enteringValueForFinancing(projectName, labelWithValue, action.BOOLEAN, timeOut);
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
	public void enteringValueForFinancing(String projectName,String[][] navigationFieldWithValues,action action,int timeOut) {
		String navigationField;
		String navigationvalue;
		WebElement ele;
		for (String[] navigationFieldAndvalue : navigationFieldWithValues) {
			navigationField=navigationFieldAndvalue[0];
			navigationvalue = navigationFieldAndvalue[1];
			if (navigationField.equalsIgnoreCase(excelLabel.Status.toString()) || navigationField.equalsIgnoreCase(excelLabel.Type.toString()) || 
					navigationField.equalsIgnoreCase(excelLabel.Type_Of_Debt.toString())) {
				 ele = getDropdownOnCreationPopUp(projectName, PageName.Financing, navigationField, action.BOOLEAN, timeOut);
				 if (click(driver,ele,navigationField+" with value "+navigationvalue, action.BOOLEAN)) {
					 log(LogStatus.INFO, "Click on "+navigationField, YesNo.No);
					 if (SelectDropDownValue(projectName, PageName.Financing, navigationField, navigationvalue, action, timeOut)) {
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

					if (navigationField.equalsIgnoreCase(excelLabel.Institution.toString()) || navigationField.equalsIgnoreCase(excelLabel.Company.toString())) {
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
	/**
	 * @author Sahil Bansal
	 * @param projectName
	 * @param dealName
	 * @param requestInfo
	 * @param basedOnValue
	 * @param action
	 * @param timeOut
	 * @return true if Deal Team created successfully
	 */
	public boolean createFinancing(String projectName,String dealName,String[][] requestInfo,String basedOnValue,action action,int timeOut) {
		boolean flag=true;
		String label;
		String value;
		String xpath="";
		WebElement ele;
		
		if(clickUsingJavaScript(driver, getNewButton(projectName, timeOut), "deal team")) {
			log(LogStatus.INFO,"click on New deal team Button",YesNo.Yes);

			for (String[] reuestData : requestInfo) {
				label=reuestData[0].replace("_", " ");
				value=reuestData[1];

				if(PageLabel.Company.toString().equals(reuestData[0]) || PageLabel.Deal.toString().equals(reuestData[0]) ||PageLabel.Firm.toString().equals(reuestData[0])){
					if (sendKeys(driver, getListTextbox(projectName,label, timeOut), value, label+" : "+value,action)) {
						ThreadSleep(3000);
						log(LogStatus.INFO,"Able to send "+value+" to label : "+label,YesNo.Yes);
						if (click(driver,FindElement(driver,"//span[contains(@class,'listbox')]//*[@title='"+value+"']","ATTENDEE Name List", action, 30),
								value + "   :   Company Name", action)) {
							log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);
						} else {
							sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
							log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
							flag=false;
						}

					} else {
						sa.assertTrue(false,"Not Able to send "+value+" to label : "+label);
						log(LogStatus.SKIP,"Not Able to send "+value+" to label : "+label,YesNo.Yes);
						return false;
					}
				}else if(PageLabel.Type_of_dept.toString().equals(reuestData[0]) || PageLabel.Status.toString().equals(reuestData[0])) {

					if (click(driver, getListTextbox(projectName, label, 10), label, action)) {
						ThreadSleep(2000);
						log(LogStatus.INFO,"Able to Click on "+label,YesNo.No);

						xpath="//span[@title='"+value+"']";
						ele = FindElement(driver,xpath, value,action, timeOut);
						ThreadSleep(4000);
						if (click(driver, ele, value, action)) {
							log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);	
						} else {
							sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
							log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
							flag=false;
						}

					} else {
						sa.assertTrue(false,"Not Able to Click on "+label);
						log(LogStatus.SKIP,"Not Able to Click on "+label,YesNo.Yes);
						flag=false;
					}

				}
				/*else if(AttendeeLabels.Notes.toString().equals(reuestData[0])) {
					AttendeePage ap = new AttendeePage(driver);
					if (sendKeys(driver, ap.labelTextBox(projectName,reuestData[0], 60), value, "notes",
							action.BOOLEAN)) {

					} else {
						sa.assertTrue(false,"not able to enter text to "+label);
						log(LogStatus.SKIP,"not able to enter text to "+label,YesNo.Yes);
						flag=false;
					}
				}*/
			}

			

			if (click(driver, getRecordPageSettingSave(timeOut), "save button", action)) {
				appLog.info("clicked on save button");
				
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
				xpath="//*[text()='Financing']/parent::h1//slot/lightning-formatted-text";
				ele = FindElement(driver, xpath, "dt id", action, timeOut);
				if (ele!=null) {
					String id=getText(driver, ele, "deal team id",action.SCROLLANDBOOLEAN);
					
				
					log(LogStatus.INFO,"successfully created and noted id of DT"+id+" and deal name "+dealName,YesNo.No);	
				} else {
					sa.assertTrue(false,"could not create DT"+dealName);
					log(LogStatus.SKIP,"could not create DT"+dealName,YesNo.Yes);
					flag=false;
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on save button so cannot create deal team");
				log(LogStatus.SKIP,"Not Able to Click on save button so cannot create deal team",YesNo.Yes);
				flag=false;
			}


		}else {
			sa.assertTrue(false,"Not able to click on deal team button");
			log(LogStatus.SKIP,"Not able to click on deal team button",YesNo.Yes);
			flag=false;

		}
		return flag;
	}

	
	
	/**
	 * @author Sahil Bansal
	 * @param projectName
	 * @param dealName
	 * @param requestInfo
	 * @param basedOnValue
	 * @param action
	 * @param timeOut
	 * @return true if Deal Team created successfully
	 */
	public boolean UpdateFinancingFirm(String projectName,String[][] requestInfo,String basedOnValue,action action,int timeOut) {
		boolean flag=true;
		String label;
		String value;
		String xpath="";
		WebElement ele;
		
		ThreadSleep(2000);
		if (clickOnShowMoreActionDownArrow(projectName, PageName.Financing, ShowMoreActionDropDownList.Edit, 10)) {
			ThreadSleep(2000);
			if (click(driver, getFirmCrossIcon(projectName, 60), "Firm Cross Icon", action.BOOLEAN)) {
				appLog.info("Clicked on Firm Cross icon");
				ThreadSleep(3000);
//			} else {
//				appLog.info("Not able to click on Cross Icon button");
//				log(LogStatus.INFO, "Not able to clicked on edit button so cannot Account Name ", YesNo.Yes);
//				BaseLib.sa.assertTrue(false, "Not able to clicked on edit button so cannot Account Name ");

			for (String[] reuestData : requestInfo) {
				label=reuestData[0].replace("_", " ");
				value=reuestData[1];

				if(PageLabel.Company.toString().equals(reuestData[0]) || PageLabel.Deal.toString().equals(reuestData[0]) ||PageLabel.Firm.toString().equals(reuestData[0])){
					if (sendKeys(driver, getListTextbox(projectName,label, timeOut), value, label+" : "+value,action)) {
						ThreadSleep(3000);
						log(LogStatus.INFO,"Able to send "+value+" to label : "+label,YesNo.Yes);
						if (click(driver,FindElement(driver,"//span[contains(@class,'listbox')]//*[@title='"+value+"']","ATTENDEE Name List", action, 30),
								value + "   :   Company Name", action)) {
							log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);
						} else {
							sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
							log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
							flag=false;
						}

					} else {
						sa.assertTrue(false,"Not Able to send "+value+" to label : "+label);
						log(LogStatus.SKIP,"Not Able to send "+value+" to label : "+label,YesNo.Yes);
						return false;
					}
				}
					if (click(driver, getRecordPageSettingSave(timeOut), "save button", action)) {
						appLog.info("clicked on save button");
					} else {
						sa.assertTrue(false,"Not Able to Click on save button so cannot create deal team");
						log(LogStatus.SKIP,"Not Able to Click on save button so cannot create deal team",YesNo.Yes);
						flag=false;
					


				}
			}}
		}else {
					sa.assertTrue(false,"Not able to click on deal team button");
					log(LogStatus.SKIP,"Not able to click on deal team button",YesNo.Yes);
					flag=false;

				}
				return flag;
			}
}
