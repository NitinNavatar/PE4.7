/**
 * 
 */
package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.ExcelUtils;
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
public class FundRaisingPageBusinessLayer extends FundRaisingPage  {

	/**
	 * @param driver
	 */
	public FundRaisingPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	//Lightning Mthod....
		/**
		 * @author Azhar Alam
		 * @param environment
		 * @param mode
		 * @param fundraisingName
		 * @param fundName
		 * @param legalName
		 * @param closing TODO
		 * @param stage TODO
		 * @param investmentLikelyAmount TODO
		 * @param statusNote TODO
		 * @return true if able to create FundRaising
		 */
		public boolean createFundRaising(String environment,String mode,String fundraisingName, String fundName, String legalName, String closing, String stage, String investmentLikelyAmount, String statusNote) {
			refresh(driver);
			ThreadSleep(5000);
			if (click(driver, getNewButton(environment,mode,60), "New Button", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(500);
				if (sendKeys(driver, getFundraisingName(environment,mode,60), fundraisingName, "FundRaising Name", action.BOOLEAN)) {
					ThreadSleep(500);
					if (sendKeys(driver, getFundName(environment,mode,60), fundName, "Fund Name", action.BOOLEAN)) {
						ThreadSleep(500);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							ThreadSleep(2000);
							if (click(driver,
									FindElement(driver,
											"//*[contains(@class,'slds-listbox__option-text')]/*[@title='"+fundName+"']",
											"Fund Name List", action.THROWEXCEPTION, 30),
									fundName + "   :   Fund Name", action.BOOLEAN)) {
								appLog.info(fundName + "  is present in list.");
							} else {
								appLog.info(fundName + "  is not present in the list.");
							}
						}
						if (sendKeys(driver, getLegalName(environment,mode,60), legalName, "Legal Name", action.BOOLEAN)) {
							ThreadSleep(500);
							if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								ThreadSleep(1000);
								if (click(driver,
										FindElement(driver,
												"//*[contains(@class,'slds-listbox__option-text')]/*[@title='"+legalName+"']",
												"Legal Name List", action.THROWEXCEPTION, 30),
										legalName + "   :   Legal Name", action.SCROLLANDBOOLEAN)) {
									appLog.info(legalName + "  is present in list.");
								} else {
									appLog.info(legalName + "  is not present in the list.");
								}
							}
							
							try {
								if(!closing.equals("")||closing!=null){
									if (click(driver,
											FindElement(driver,
													"//*[text()='Closing']/..//input",
													"Clsoing input", action.THROWEXCEPTION, 30),
											"Clsoing input", action.BOOLEAN)) {
										appLog.info("Click on closing input");
										ThreadSleep(2000);
										click(driver,FindElement(driver,"//*[text()='Closing']/..//input/../..//*[@data-value='"+closing+"']","Clsoing list", action.THROWEXCEPTION, 30),
												"Clsoing list", action.BOOLEAN) ;
											appLog.info("Click on closing list item:"+closing);
										
										
									} else {
										appLog.info("Not able to Click on closing input");
									}
									
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							try {
								if(!stage.equals("")||stage!=null){
									if (click(driver,
											FindElement(driver,
													"//*[text()='Stage']/..//input",
													"Stage input", action.THROWEXCEPTION, 30),
											"Stage input", action.BOOLEAN)) {
										appLog.info("Click on stage input");
										ThreadSleep(2000);
										click(driver,FindElement(driver,"//*[text()='Stage']/..//input/../..//*[@data-value='"+stage+"']","stage list", action.THROWEXCEPTION, 30),
												"stage list", action.BOOLEAN) ;
											appLog.info("Click on stage list item:"+stage);
										
										
									} else {
										appLog.info("Not able to Click on stage input");
									}
									
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							try {
								if(!investmentLikelyAmount.equals("")||investmentLikelyAmount!=null){
									WebElement ele=FindElement(driver, "//input[@name='navpeII__Investment_Likely_Amount_USD_mn__c']", "investment likely amount input box", action.BOOLEAN, 20);
									if (sendKeys(driver, ele, investmentLikelyAmount, "investment likely amount input box", action.BOOLEAN)) {
										appLog.info("Enter investment likley amount:"+investmentLikelyAmount);

										
									} else {
										appLog.info("Not able to Enter investment likley amount:"+investmentLikelyAmount);
									}
									
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							try {
								if(!statusNote.equals("")||statusNote!=null){
									WebElement ele=FindElement(driver, "//*[text()='Status Notes']/..//textarea", "status note", action.BOOLEAN, 20);
									if (sendKeys(driver, ele, statusNote, "status note", action.BOOLEAN)) {
										appLog.info("Enter status note:"+statusNote);

										
									} else {
										appLog.info("not able to Enter status note:"+statusNote);
									}
									
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (click(driver, getCustomTabSaveBtn("",60), "Save Button", action.SCROLLANDBOOLEAN)) {
								ThreadSleep(500);
								
									ThreadSleep(2000);
									String fundraising=null;
									WebElement ele;
									if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
										String	xpath="//*[contains(text(),'Fundraising')]/..//*[text()='"+fundraisingName+"']";
										 ele = FindElement(driver, xpath, "Header : "+fundraisingName, action.BOOLEAN, 30);
									
									} else {
										ele=getFundraisingNameInViewMode(environment, mode, 60);
									}
									
									if (ele!=null) {
										appLog.info("Fundraising is created successfully.:" + fundraisingName);
										return true;
									} else {
										appLog.info("FundRaising is not created successfully.:" + fundraisingName);
									}
								
							} else {
								appLog.error("Not able to click on save button");
							}
						} else {
							appLog.error("Not able to enter legal Name");
						}
					} else {
						appLog.error("Not able to enter fund name");
					}
				} else {
					appLog.error("Not able to enter value in fundraiisng text box");
				}
			} else {
				appLog.error("Not able to click on new button so we cannot create fundraising");
			}
			return false;
		}
		
		public WebElement getEditBtn(String projectName,String stage,action action,int timeOut) {
			String xpath = "//a[contains(@title,'Edit')][contains(@title,'"+stage+"')]";
			WebElement ele = FindElement(driver, xpath,stage, action, timeOut);
			return ele;
		}
		
}
