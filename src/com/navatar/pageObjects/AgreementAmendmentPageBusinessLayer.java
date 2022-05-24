package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.changeNumberIntoUSFormat;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.clickUsingJavaScript;
import static com.navatar.generic.CommonLib.getText;
import static com.navatar.generic.CommonLib.isDisplayed;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.refresh;
import static com.navatar.generic.CommonLib.sendKeys;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.AttendeeLabels;
import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.IndiviualInvestorFieldLabel;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.RelatedList;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

public class AgreementAmendmentPageBusinessLayer extends AgreementAmendmentPage{

	public AgreementAmendmentPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param event
	 * @param staff
	 * @param status
	 * @param notes
	 * @return true if attendee created successfully
	 */
	public boolean createAgreementAmendment(String projectName, String recordType, String agreementAmendmentNumber,String commitment,String effectiveDate,String recieptDate,int timeOut) {
		
		WebElement ele;
		if(click(driver, getNewButton(projectName, timeOut), "new button"+projectName, action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on new agreement/amendment button ");
			
			
			if (!recordType.equals("") || !recordType.isEmpty()) {
				ThreadSleep(2000);
				
				if(recordType.equalsIgnoreCase("Agreement")) {
					ele = getRecordTypeAgreemetnRadioButton(projectName, timeOut);
					
				}else {
					ele = getRecordTypeAmendmentRadioButton(projectName, timeOut);
					
				}
				
				if(click(driver, ele, "Radio Button for : "+recordType, action.SCROLLANDBOOLEAN)){
					appLog.info("Clicked on radio Button for record type : "+recordType);
					if (click(driver, getContinueOrNextButton(projectName,timeOut), "Next Button", action.BOOLEAN)) {
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
			
			if (sendKeys(driver, getAgreementAmendmentNumberInput(projectName, timeOut), agreementAmendmentNumber, "Agreement/Amendment number input",action.SCROLLANDBOOLEAN)) {
				appLog.info("passed data in text box: " + agreementAmendmentNumber);
				ThreadSleep(1000);
				if (sendKeys(driver, getCommitmentInput(projectName, timeOut), commitment, "commitment input",action.SCROLLANDBOOLEAN)) {
					appLog.info("passed data in text box: " + commitment);
					ThreadSleep(3000);
					ele = isDisplayed(driver, FindElement(driver, "//*[@class='slds-listbox__item']//*[@title='"+commitment+"']/..", "", action.BOOLEAN, timeOut), "Visibility", timeOut, "commitement list");
				
					if(clickUsingJavaScript(driver, ele, "", action.BOOLEAN)) {
						appLog.info("Clicked on commitement:"+commitment);
						
						sendKeys(driver, getEffectiveDateInput(projectName, timeOut), effectiveDate, "effective date", action.BOOLEAN);
						appLog.info("passed data in text box: " + effectiveDate);

						ThreadSleep(1000);
						sendKeys(driver, getRecieptDateInput(projectName, timeOut), recieptDate, "reciept  date", action.BOOLEAN);
						appLog.info("passed data in text box: " + recieptDate);
						ThreadSleep(1000);
						
						if(click(driver, getNavigationTabSaveBtn(projectName, timeOut), "save button", action.BOOLEAN)) {
							appLog.info("Clicked on save button");
							ThreadSleep(4000);
							ele=null;
							ele=getRelatedTab(projectName, RelatedTab.Details.toString(), 10);

							 if(ele!=null) {
								 ele=null;
								 ele = isDisplayed(driver, FindElement(driver, "//*[text()='Agreement/Amendment']/..//lightning-formatted-text", "agreement/amendment id text", action.SCROLLANDBOOLEAN, 30), "visibility", 20, "");
								 log(LogStatus.PASS,"after clicking on save button page redirected to agreement/amendment record detail page", YesNo.No);
								
								 String agreementAmendment= getText(driver, ele, "agreement/amendment id",action.BOOLEAN);
								if(agreementAmendment!=null) {
									ExcelUtils.writeData(phase1DataSheetFilePath,agreementAmendment,"AgreementsAmendments", excelLabel.Variable_Name, "AA1", excelLabel.AgreementAmendmentID);
									log(LogStatus.PASS,agreementAmendment+":agreement/amendment successfully created", YesNo.No);
									return true;
								}
								
							 }else {
								 appLog.error("after clicking on save button page not redirected to agreement/amendment record detail page");
									return false;
							 }
							
						}else {
							appLog.error("Not able to Clicked on save button");
							return false;
						}

					}else {
						
						appLog.error("Not able to Clicked on commitement:" + commitment);
						return false;
					}
					
				}else {
					appLog.error("Not able to passed data in text box: " + commitment);
					return false;
				}
				
			}else {
				appLog.error("Not able to passed data in text box: " + agreementAmendmentNumber);
				return false;
			}
			
			
			
		}else {
			appLog.error("Not able to click on new agreement/amendment button ");
			return false;
		}
		
		
		return false;
	}
		
}
