package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;

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
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

public class SDGPageBusinessLayer extends SDGPage implements SDGPageErrorMessage {
	
	public SDGPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public WebElement getSDGElementForCreation(String projectName,String label,action action,int timeOut) {
		String xpath="";
		String fieldLabel=label.replace("_", " ");;
		switchToDefaultContent(driver);
		if (SDGCreationLabel.Filter.toString().equals(fieldLabel)) {
			xpath ="//*[text()='"+fieldLabel+"']/..//following-sibling::div//textarea";
		} else {
			xpath ="//*[text()='"+fieldLabel+"']/..//following-sibling::div//input[@type='text']";
		}
		WebElement ele = FindElement(driver, xpath,fieldLabel , action, timeOut);
		scrollDownThroughWebelement(driver, ele, fieldLabel);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, fieldLabel);
		return ele;
	}
	
	public boolean enterValueForSDGCreation(String projectName,String[][] labelWithValues,action action,int timeOut) {
		boolean flag=false;
		String label="";
		String value="";
		WebElement ele=null;
		for (String[] labelValues : labelWithValues) {
			label = labelValues[0].replace("_", " ");
			value = labelValues[1];
			ele = getSDGElementForCreation(projectName, label, action, timeOut);
			if (sendKeys(driver, ele, value, label+" : "+value, action)) {
				log(LogStatus.INFO,"Able to Enter Value : "+value+" to label : "+label,YesNo.No);
			} else {
				sa.assertTrue(false,"Not Able to Enter Value : "+value+" to label : "+label );
				log(LogStatus.SKIP,"Not Able to Enter Value : "+value+" to label : "+label,YesNo.Yes);
	
			}
		}
		return flag;
	}
	
	public boolean createCustomSDG(String projectName,String sdgName,String[][] labelWithValues,action action,int timeOut) {
		boolean flag =false;
		ThreadSleep(5000);
		refresh(driver);
		ThreadSleep(10000);
		if(clickUsingJavaScript(driver, getNewButton(projectName, timeOut), "new button")) {
			appLog.info("clicked on new button");
			enterValueForSDGCreation(projectName, labelWithValues, action, timeOut);
			if (click(driver, getRecordPageSettingSave(60), "Save Button",action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button  "+sdgName,YesNo.No);
				ThreadSleep(5000);
				if (getSDGHeaderValueInViewMode(projectName, sdgName, timeOut)!=null) {
					log(LogStatus.PASS,"Header verified for created  "+sdgName,YesNo.No);
					flag=true;
				} else {
					sa.assertTrue(false,"Header not verified for created  "+sdgName );
					log(LogStatus.SKIP,"Header not verified for created  "+sdgName,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Save Button Value so cannot create  "+sdgName );
				log(LogStatus.SKIP,"Not Able to Click on Save Button Value so cannot create  "+sdgName,YesNo.Yes);
			}
			
		} else {
			appLog.error("Not able to click on New Button so cannot create sdg : " + sdgName);
			
		}
	
		
		return flag;
	}
	
	public boolean addFieldOnSDG(String projectName, String names, String values) {
		names= names.replace("_", " ");
		String name[] = names.split(",");
		//values= values.replace("_", " ");
		String value[] = values.split(",");
		if (name.length!=value.length) {
			log(LogStatus.INFO, name.length + " does not match "+value.length, YesNo.No);
			return false;
		}
		WebElement ele;
		boolean flag=true;
		ele=getRelatedTab(projectName, RelatedTab.Related.toString(), 10);
		if (click(driver, ele, "related tab", action.BOOLEAN)) {
			int i=0;
			if (click(driver, getFieldNewButton(projectName, 10), "field new button", action.BOOLEAN)) {
				for (String f:name) {
					System.err.println(f);
					ele=getLabelTextBox(projectName, PageName.SDGPage.toString(),f, 10);
					if (sendKeys(driver, ele, value[i], f, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"successfully entered "+value[i]+" in "+f,YesNo.Yes);

					}
					else {
						log(LogStatus.SKIP,"could not enter "+value[i]+" in "+f,YesNo.Yes);

					}
					i++;
				}
				if (click(driver, getRecordPageSettingSave(10), "save", action.BOOLEAN)) {
					log(LogStatus.INFO,"successfully clicked on save button",YesNo.Yes);
				}
				else {
					log(LogStatus.SKIP,"could not click on save button, so could not add "+f,YesNo.Yes);
					flag=false;
				}

			}else {
				log(LogStatus.SKIP,"could not click on related tab, so cannot add "+f,YesNo.Yes);
				flag=false;
			}
		}
		return flag;
	}
}
