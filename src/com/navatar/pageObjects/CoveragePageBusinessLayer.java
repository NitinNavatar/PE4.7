package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SmokeCommonVariables;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.AddressAction;
import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.LimitedPartnerPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.SmokeCommonVariables.Smoke_DealClosureMeetingSubject;

public class CoveragePageBusinessLayer extends CoveragePage  {
	
	public CoveragePageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param recordType
	 * @param coverageName
	 * @return true if coverage item created successfully
	 */
	public boolean createCoverage(String projectName, String recordType, String coverageName) {
		refresh(driver);
		ThreadSleep(10000);
		if(clickUsingJavaScript(driver, getNewButton(projectName, 60), "new button")) {
			appLog.info("clicked on new button");
		}else {
			appLog.error("Not able to click on new button so cannot create coverage : "+coverageName+" of type "+recordType);
			return false;
		}

		if (!recordType.equals("") || !recordType.isEmpty()) {
			ThreadSleep(2000);
			if(click(driver, getRadioButtonforRecordType(recordType,false,5), "Radio Button for : "+recordType, action.SCROLLANDBOOLEAN)){
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

		if (sendKeys(driver, commonInputElement(projectName, excelLabel.Coverage_Name.toString(), action.BOOLEAN, 20), coverageName, excelLabel.Coverage_Name.toString(), action.BOOLEAN)) {
			appLog.info(coverageName+" value entered to "+excelLabel.Coverage_Name.toString());	
			if (click(driver, getCustomTabSaveBtn(projectName,60), "Save Button", action.BOOLEAN)) {
				appLog.info("Clicked on save Button");	
				WebElement ele = getCreatedConfirmationMsg(projectName, 15);
				if (ele!=null) {
					String actualValue = ele.getText().trim();
					String expectedValue=coverageCreatedMsg(projectName, coverageName);
					if (actualValue.contains(expectedValue)) {
						log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);
					} else {
						log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
					}
				} else {
					sa.assertTrue(false,"Created Coverage Msg Ele not Found");
					log(LogStatus.SKIP,"Created Coverage Msg Ele not Found",YesNo.Yes);

				}
				return true;
			}
			else {
				appLog.error("save button is not clickable so cannot create coverage");
			}
		}
		else {
			appLog.error("coverage Name text box is not visible so cannot create coverage");
		}
		return false;
	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param coverageName
	 * @return coverage created message
	 */
	public String coverageCreatedMsg(String projectName,String coverageName) {
		return "Coverage \""+coverageName+"\" was created.";
		
	}
}
