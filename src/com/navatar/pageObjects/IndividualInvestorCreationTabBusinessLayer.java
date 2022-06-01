package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_Assistant;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_Asst_Phone;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_Description;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_FName;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_Fax;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_LName;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_MailingCity;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_MailingCountry;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_MailingState;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_MailingStreet;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_MailingZip;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_Mobile;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_OtherCity;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_OtherCountry;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_OtherState;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_OtherStreet;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_OtherZip;
import static com.navatar.generic.SmokeCommonVariables.SmokeC7_Phone;
import static com.navatar.generic.SmokeCommonVariables.SmokeINDINV4_FundPreferences;
import static com.navatar.generic.SmokeCommonVariables.SmokeINDINV4_IndustryPreferences;

import java.util.ArrayList;
import java.util.List;

import com.navatar.generic.BaseLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.IndiviualInvestorFieldLabel;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavatarQuickLink;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

public class IndividualInvestorCreationTabBusinessLayer extends IndividualInvestorCreationTab{

	public IndividualInvestorCreationTabBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	public boolean enterValuesInCreateIndiviualInvestor(String environment, String mode, String[][] labelNamesAndValue) {
		boolean flag = true;
		String xpath="";
		String label="";
		WebElement ele= null;
		for (String[] labelAndValue : labelNamesAndValue) {
			if(labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Fund_Preferences.toString()) ||
					labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Industry_Preferences.toString()) ||
					labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Preferred_Mode_of_Contact.toString())) {
				label=labelAndValue[0].replace("_", " ");
				if(labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Preferred_Mode_of_Contact.toString())) {
					xpath="//label[contains(text(),'"+label+"')]/../following-sibling::td//select";
				}else {
					xpath="//label[contains(text(),'"+label+"')]/../following-sibling::td//select[@title='"+label+" - Available']";
				}
				ele=FindElement(driver, xpath, label+" text box", action.SCROLLANDBOOLEAN, 20);
				if(ele!=null) {
					if(selectVisibleTextFromDropDown(driver, ele, label+" multiselect drop down", labelAndValue[1])) {
						log(LogStatus.INFO, "select value : "+labelAndValue[1]+" in "+label, YesNo.No);
						if(!labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Preferred_Mode_of_Contact.toString())) {
							xpath="//select[@title='"+label+" - Available']/../following-sibling::td/a[@title='Add']";
							ele=FindElement(driver, xpath, label+" text box", action.SCROLLANDBOOLEAN, 20);
							if(ele!=null) {
								if(click(driver, ele, label+" add button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on add button label : "+label, YesNo.No);
									
								}else {
									log(LogStatus.ERROR, "Not able to click on  "+label+" add button so cannot  move value : "+labelAndValue[1],YesNo.Yes);
									flag=false;
								}
							}else {
								log(LogStatus.ERROR, label+" : label add button is not visible so cannot move value :"+labelAndValue[1] , YesNo.Yes);
								flag=false;
							}
						}
					}else {
						log(LogStatus.ERROR, "Not able to select value "+labelAndValue[1]+" in multiselect : "+label,YesNo.Yes);
						flag=false;
					}
				}else {
					log(LogStatus.ERROR, label+" : label is not visible so cannot select value : "+labelAndValue[1], YesNo.Yes);
					flag=false;
				}
				
			}else {
				if(labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Contact_Description.toString()) || 
						labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Mailing_Street .toString()) ||
						labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Other_Street .toString())) {
					label=labelAndValue[0].replace("_", " ");
					xpath="//label[contains(text(),'"+label+"')]/../following-sibling::td//textarea";
				}else {
					label=labelAndValue[0].replace("_", " ");
					xpath="//label[contains(text(),'"+label+"')]/../following-sibling::td//input";
				}
				
				ele=FindElement(driver, xpath, label+" text box", action.SCROLLANDBOOLEAN, 20);
				if(ele!=null) {
					if(sendKeys(driver, ele, labelAndValue[1], label+" text box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "passed value : "+labelAndValue[1]+" in "+label, YesNo.No);
					}else {
						log(LogStatus.ERROR, "Not able to pass value "+labelAndValue[1]+" in label : "+label,YesNo.Yes);
						flag=false;
					}
				}else {
					log(LogStatus.ERROR, label+" : label is not visible so cannot enter value : "+labelAndValue[1], YesNo.Yes);
					flag=false;
				}
			}
		}
		
		return flag;
		
	}
	
	
	public boolean createIndiviualInvestor(String environment, String mode,String[][] labelNamesAndValue, String clickOnCopyAddress, TopOrBottom topOrBottom) {
		boolean flag = false;
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String bulkActionNavigationLink=BulkActions_DefaultValues.Individual_Investor_Creation.toString();
		if (home.ClickOnItemOnNavatarEdge(NavigationMenuItems.Bulk_Actions.toString(), bulkActionNavigationLink, action.BOOLEAN, 20)) {
					appLog.info("Clicked On Create Individual Investor Creation Link");
			if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				switchToFrame(driver, 10,getCreateCommitmentFrame_Lighting(20));
			}
			ThreadSleep(10000);
			if(enterValuesInCreateIndiviualInvestor(environment, mode, labelNamesAndValue)) {
				log(LogStatus.INFO, "Enter Value Successfully in passed labels", YesNo.No);
				ThreadSleep(500);
				if(clickOnCopyAddress!=null && clickOnCopyAddress.equalsIgnoreCase("Yes")){
					click(driver, getCoptyMailingAddressToOther(30), "Copy link to mailing address link", action.SCROLLANDBOOLEAN);
				}
				if(click(driver, getCreateIndiviualInvestorBtn(environment,mode,topOrBottom,20), "create indiviual investor button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on create indiviual investor button", YesNo.No);
					flag=true;
				}else {
					log(LogStatus.ERROR, "Not able to click on create indiviual investor button", YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, "Not able to pass value in Label so cannot create indiviual investor", YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on  Create New Individual Investor link so cannot verify labels", YesNo.Yes);
		}
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToDefaultContent(driver);
		}
		return flag;
	}

	
	
	
}
