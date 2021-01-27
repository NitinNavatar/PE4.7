package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;
import org.openqa.selenium.support.FindBy;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.AddressAction;
import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.LimitedPartnerPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

public class EditPageBusinessLayer extends EditPage implements EditPageErrorMessage {
	
	public EditPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	

	/**
	 * @return the editPageSeachTextBox
	 */
	public WebElement getEditPageSeachValueLink(String projectName,String searchValue,int timeOut) {
		String xpath = "//span[text()='"+searchValue+"']";
		WebElement ele = FindElement(driver, xpath, "Search Value : "+searchValue, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Search Value : "+searchValue);
	}
	
	
	public boolean clickOnELGSeachValueLink(String projectName,String searchValue,int timeOut) {
		String xpath = "//span[@title='"+searchValue+"' or text()='"+searchValue+"']";
		WebElement ele = FindElement(driver, xpath, "Search Value : "+searchValue, action.BOOLEAN, timeOut);
		ele =  isDisplayed(driver, ele, "Visibility", timeOut, "Search Value : "+searchValue);
		return click(driver, ele, searchValue, action.BOOLEAN);
	}
	
	
	public boolean clickOnEditPageLink() {
		boolean flag = false;
		if(click(driver, getSettingLink_Lighting(20), "setting icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on setting icon", YesNo.No);
			ThreadSleep(1000);
			if(click(driver, getEditPageLink_Lighting(20), "edit page link", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on edit page link", YesNo.No);
				flag=true;
			}else {
				log(LogStatus.ERROR,"Not able to click on edit page link",YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on setting icon", YesNo.Yes);
		}
		return flag;
	}
	
	
	public boolean dragAndDropLayOutFromEditPage(String projectName,PageName pageName, RelatedTab relatedTab,String DropComponentName, String fieldSetApiName) {
		boolean flag = false;
		WebElement ele=null,dropComponentXpath=null,dropLocation=null;
		if(switchToFrame(driver, 30, getEditPageFrame(projectName,30))) {
			String related = relatedTab.toString().replace("_", " ");
			String relatedTabXpath="//*[@role='tablist']//li//*[@title='"+related+"' or text()='"+related+"']";
			ele = isDisplayed(driver, FindElement(driver, relatedTabXpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, 10)
					, "visiblity", 10, relatedTab.toString());
			if (ele!=null) {
				if (click(driver,ele,relatedTab.toString()+" tab xpath", action.BOOLEAN)) {
					log(LogStatus.INFO,"Click on Sub Tab : "+RelatedTab.Investment,YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					if(sendKeys(driver,getEditPageSeachTextBox(projectName, 10), DropComponentName,DropComponentName+" component xpath", action.BOOLEAN)) {
						log(LogStatus.INFO, "Enter component name in search box : "+DropComponentName, YesNo.No);
						
						String xpath = "//span[@title='"+DropComponentName+"' or text()='"+DropComponentName+"']";
						dropComponentXpath =  isDisplayed(driver, FindElement(driver, xpath, "Search Value : "+DropComponentName, action.BOOLEAN, 10), "Visibility", 10, "Search Value : "+DropComponentName);
						if(dropComponentXpath!=null) {
//							Actions builder = new Actions(driver);
//							builder.clickAndHold(dropComponentXpath).build().perform();
					        switchToFrame(driver, 30, getEditPageFrame(projectName,30));
					        String dropLocationXpath="(//a[@class='flexipageEditorContainerPlaceholder'])[1]";
					        dropLocation = FindElement(driver, dropLocationXpath,"header xpath",action.BOOLEAN, 10);
					    	if (dropLocation!=null) {
					    		switchToDefaultContent(driver);
                                Screen screen = new Screen();
                                try {
                                
//                                		screen.dropAt("\\AutoIT\\AddComponentHere.PNG");
                                		screen.dragDrop("\\AutoIT\\FIeldSet.PNG", "\\AutoIT\\AddComponentHere.PNG");
                                	
                                    ThreadSleep(500);
                                    if(sendKeys(driver, getFieldSetNameTextBox(10), fieldSetApiName, "field set name text box", action.BOOLEAN)) {
                                    	log(LogStatus.INFO, "field set name : "+fieldSetApiName, YesNo.No);
                                    	if(click(driver, getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
                                    		log(LogStatus.INFO, "clicked on save button", YesNo.No);
                                    		ThreadSleep(2000);
                                    		if(clickUsingJavaScript(driver, getBackButton(10), "back button", action.BOOLEAN)) {
                                    			log(LogStatus.PASS, "clicked on back button", YesNo.No);
                                    			flag=true;
                                    		}else {
												log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
											}
                                    	}else {
											log(LogStatus.ERROR, "Not able to click on save button so cannot add field set : "+fieldSetApiName, YesNo.No);
										}
                                    }else {
										log(LogStatus.ERROR, "Not able to enter field set name : "+fieldSetApiName+" so cannot add field set", YesNo.Yes);
									}
                                } catch (FindFailed e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
					    	}else {
					    		log(LogStatus.ERROR, "Drop location is not visible in list so cannot drag and drop component "+DropComponentName+" in "+relatedTab.toString(), YesNo.Yes);
							}
						}else {
							log(LogStatus.ERROR, "Searched component is not visible in list so cannot drag and drop component "+DropComponentName+" in "+relatedTab.toString(), YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to search on component so cannot drag and drop component "+DropComponentName+" in "+relatedTab.toString(), YesNo.Yes);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on related tab so cannot drag and drop component "+DropComponentName+" in "+relatedTab.toString(), YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, "Related tab is not present so cannot drag and drop component "+DropComponentName+" in "+relatedTab.toString(), YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, "Cannot switch in edit page iframe cannot drag and drop component "+DropComponentName+" in "+relatedTab.toString(), YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;
		
		
		
	}

	
	
}
