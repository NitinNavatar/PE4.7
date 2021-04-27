package com.navatar.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.FundPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import java.text.DecimalFormat;

import static com.navatar.generic.AppListeners.appLog;

public class CommitmentsPageBusinessLayer extends CommitmentsPage{

	public CommitmentsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param projectName
	 * @param LimitedPartner
	 * @param Partnership
	 * @param finalCommitmnateDate
	 * @param commitmentAmount
	 * @param excelPath
	 * @param basedOnValue
	 * @return true if commmitment created successfully
	 */
	public boolean createCommitment(String projectName,String LimitedPartner, String Partnership,String finalCommitmnateDate,String commitmentAmount,String excelPath, String basedOnValue) {
		refresh(driver);
		ThreadSleep(5000);
		if (click(driver, getNewButton(environment,mode,60), "New Button", action.BOOLEAN)) {
			if (sendKeys(driver, getLimitedPartnerTextbox(environment,mode,60), LimitedPartner, "Limited Partner Text Box",
					action.BOOLEAN)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					ThreadSleep(1000);
					if (click(driver,
							FindElement(driver,
									"//*[contains(@class,'slds-listbox__option-text')]/*[@title='"+LimitedPartner+"']",
									"LimitedPartner Name List", action.THROWEXCEPTION, 30),
							LimitedPartner + "   :   LimitedPartner Name", action.BOOLEAN)) {
						appLog.info(LimitedPartner + "  is present in list.");
					} else {
						appLog.error(LimitedPartner + "  is not present in the list.");
						return false;
					}
				}
				if (sendKeys(driver, getPartnershipTextBox(environment,mode,60), Partnership, "Partnership Text Box", action.BOOLEAN)) {
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						ThreadSleep(1000);
						if (click(driver,
								FindElement(driver,
										"//*[contains(@class,'slds-listbox__option-text')]/*[@title='"+Partnership+"']",
										"Partnership Name List", action.THROWEXCEPTION, 30),
								Partnership + "   :   Partnership Name", action.BOOLEAN)) {
							appLog.info(Partnership + "  is present in list.");
						} else {
							appLog.error(Partnership + "  is not present in the list.");
							return false;
						}
					}
					if(finalCommitmnateDate!=null) {
						if (sendKeys(driver, getFinalCommimentDate(10), finalCommitmnateDate, "commitment date Text Box", action.BOOLEAN)) {
							appLog.info("Enter final commitment date : "+finalCommitmnateDate);
						}else {
							appLog.error("Not able to enter final commitment date : "+finalCommitmnateDate);
							return false;
						}
							
					}
					if(commitmentAmount!=null) {
						if (sendKeys(driver, getCommimentAmount(10), commitmentAmount, "commitment amount Text Box", action.BOOLEAN)) {
							appLog.info("Enter final commitment amount : "+commitmentAmount);
						}else {
							appLog.error("Not able to enter final commitment amount : "+commitmentAmount);
							return false;
						}
							
					}
					if (click(driver, getCustomTabSaveBtn(projectName,60), "Save Button", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(5000);
						for(int i=0; i<2; i++) {
							if (getCommitmentIdInViewMode(environment,mode,20) != null) {
								String commitmentId = getText(driver, getCommitmentIdInViewMode(environment,mode,60), "Commitment ID",
										action.BOOLEAN);
								appLog.info(commitmentId  + " : commitment id is generated");
								if(excelPath!=null) {
									ExcelUtils.writeData(excelPath,commitmentId, "Commitments", excelLabel.Variable_Name, basedOnValue,
											excelLabel.Commitment_ID);
								}else {
									ExcelUtils.writeData(commitmentId, "Commitments", excelLabel.Variable_Name, basedOnValue,
											excelLabel.Commitment_ID);
								}
								return true;
							} else {
								if(i==1) {
									appLog.error("Not able to find Commitment id");
								}else {
									refresh(driver);
								}
							}
						}
					} else {
						appLog.error("Not able to click on save button");
					}
				} else {
					appLog.error("Not able to enter value in partnership text box");
				}
			} else {
				appLog.error("Not able to enter value in limited partner text box");
			}
		} else {
			appLog.error("Not able to click on new button so we cannot create commitment");
		}
		return false;
	}

}
