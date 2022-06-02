package com.navatar.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.navatar.generic.EnumConstants.AddProspectsTab;
import com.navatar.generic.EnumConstants.CommitmentType;
import com.navatar.generic.EnumConstants.CreatedOrNot;
import com.navatar.generic.EnumConstants.FundraisingContactPageTab;
import com.navatar.generic.EnumConstants.HTMLTAG;
import com.navatar.generic.EnumConstants.IndiviualInvestorFieldLabel;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.SDGGridName;
import com.navatar.generic.EnumConstants.SearchBasedOnExistingFundsOptions;
import com.navatar.generic.EnumConstants.SortOrder;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.NavatarQuickLink;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.searchContactInEmailProspectGrid;
import com.navatar.generic.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
import static com.navatar.generic.EnumConstants.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.EmailTemplate1_FolderName;
import static com.navatar.generic.CommonVariables.EmailTemplate1_TemplateName;
import static com.navatar.generic.CommonVariables.SmokeReportFolderName;
import static com.navatar.generic.CommonVariables.SmokeReportName;
import static com.navatar.generic.CommonVariables.environment;
import static com.navatar.generic.CommonVariables.mode;
import static com.navatar.generic.AppListeners.*;
public class HomePageBusineesLayer extends HomePage {

	public HomePageBusineesLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	/***********************************Activity Association*********************************/
	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @return true/false
	 * @description this method is used to click on setup link on home page
	 */
	public boolean clickOnSetUpLink() {
		boolean flag = false;
			if(click(driver, getSettingLink_Lighting(20), "setting icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "setting icon", YesNo.No);
				
			}else {
				log(LogStatus.ERROR, "clicked on setting icon", YesNo.Yes);
				return flag;
			}
		if(click(driver, getUserMenuSetupLink(20), "setup link", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on setup link", YesNo.No);
			flag=true;
		}else {
			log(LogStatus.ERROR,"user setup link",YesNo.Yes);
		}
		return flag;
	}
	
	/**@author Akul Bhutani
	 * @param environment
	 * @param mode
	 * @return true/false
	 * @description this method is used to click on click on setup link either classic or lightning
	 */
	public boolean clickOnSetUpLink(String environment,String mode) {
		boolean flag = false;
		if(mode.equalsIgnoreCase(Mode.Classic.toString())) {
			if(click(driver, getUserMenuTab(20), "user menu tab", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on user menu tab");
				log(LogStatus.INFO, "user menu tab", YesNo.No);
				
			}else {
				log(LogStatus.ERROR, "user menu tab", YesNo.Yes);
				return flag;
			}
		}else {
			if(click(driver, getSettingLink_Lighting(20), "setting icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "setting icon", YesNo.No);
				
			}else {
				log(LogStatus.ERROR, "setting icon", YesNo.Yes);
				return flag;
			}
		}
		if(click(driver, getUserMenuSetupLink( mode, 20), "setup link", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "setup link", YesNo.No);
			flag=true;
		}else {
			log(LogStatus.ERROR,"user setup link",YesNo.Yes);
		}
		return flag;
	}
	
	/**
	 * @author Azhar Alam
	 * @return true if able to click on Edit Page Link
	 */
	public boolean clickOnEditPageLinkOnSetUpLink() {
		boolean flag = false;
			if(click(driver, getSettingLink_Lighting(20), "setting icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "setting icon", YesNo.No);
				
			}else {
				log(LogStatus.ERROR, "setting icon", YesNo.Yes);
				return flag;
			}
		if(click(driver, getEditPageOnSetUp(20), "Edit Page", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Edit Page", YesNo.No);
			flag=true;
		}else {
			log(LogStatus.ERROR,"Edit Page",YesNo.Yes);
		}
		return flag;
	}
	
	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @param navatarQuickLink
	 * @return true if successfully click on Navatar Quick Link
	 */
	public boolean clickOnLinkFromNavatarQuickLink(String environment,String mode,NavatarQuickLink navatarQuickLink){
		boolean flag =false;
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
		
			if (clickUsingJavaScript(driver, getNavatarQuickLink_Lighting(environment, 20), "Navatar Quik Link")) {
				ThreadSleep(1000);
				switchToFrame(driver, 10, getNavatarQuickLinkFrame_Lighting(environment, 10));
			}
		}else{
			if(getCloseSideBar(5)==null){
				if(click(driver, getOpenSideBar(30), "Open sied bar", action.BOOLEAN)){
					log(LogStatus.INFO, "Opened the side bar.", YesNo.No);
				} else {
//					BaseLib.sa.assertTrue(false, "cannot open the side bar, So cannot check the navatar quick link.");
//					log(LogStatus.ERROR, "cannot open the side bar, So cannot check the navatar quick link.", YesNo.Yes);
				}
			}
			ThreadSleep(1000);
			appLog.info("Inside Classic Frame");
			switchToFrame(driver, 10, getNavatarQuickLinkFrame_Classic(environment, 10));	
		}
		
		WebElement quickLink = FindElement(driver, "//a[contains(text(),'"+navatarQuickLink+"')]", "Navatar Quick Link : "+navatarQuickLink, action.SCROLLANDBOOLEAN, 20);
		if (click(driver, quickLink, "Navatar Quick Link : "+navatarQuickLink, action.SCROLLANDBOOLEAN)) {	
			flag = true;
		}
		
		switchToDefaultContent(driver);
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			if (click(driver, getNavatarQuickLinkMinimize_Lighting(environment, 20), "Navatar Quik Link Minimize Icon",
					action.SCROLLANDBOOLEAN)) {
				ThreadSleep(1000);
			}
		}
		
		return flag;
		
	}
	
/**
 * @author Azhar Alam
 * @param environment
 * @param mode
 * @param navatarQuickLink
 * @return true if successfully verify landing page after click on Navatar Setup Page 
 */
public boolean verifyLandingPageAfterClickingOnNavatarSetUpPage(String environment,String mode, NavatarQuickLink navatarQuickLink) {
		
		String landingPage = null;
		WebElement ele;
		switch (navatarQuickLink) {
		case CreateDeal:
			landingPage = "Deal Creation";
			break;
		case BulkEmail:
			landingPage = "Bulk E-mail";
			break;
		default:
			return false;
		}
		ThreadSleep(2000);
		System.err.println("Passed switch statement");
	
			
			ele = isDisplayed(driver, FindElement(driver, "//p[text()='"+landingPage+"']", landingPage,
					action.SCROLLANDBOOLEAN, 10), "visibility", 10, landingPage);
			if (ele != null) {
				return true;
			}
		return false;
	}

public boolean clickOnTemplateForReportOnBulkEmail(String environment,String mode,String reportName,String templateName) {
	WebElement ele;
	String xpath ="//span[text()='"+templateName+"']/ancestor::ul//span[contains(@id,'extd')][text()='"+reportName+"']";
	ele=FindElement(driver, xpath,reportName+" : "+templateName,action.SCROLLANDBOOLEAN,10);

	if(click(driver, ele, reportName+" : "+templateName, action.SCROLLANDBOOLEAN)) {
		log(LogStatus.INFO, "Clicked on >>>   "+reportName+" : "+templateName, YesNo.No);
		return true;
	}else {
		log(LogStatus.ERROR, "Not Able to Click on >>>   "+reportName+" : "+templateName, YesNo.Yes);
	}
	return false;
}
	
public List<String> selectContactAndVerifyInBulkEmail(String environment,String mode,String fname,String lname,String contactSearchValue,searchContactInEmailProspectGrid searchContactInEmailProspectGrid,int timeOut) {
	List<String> result = new ArrayList<String>();
	WebElement ele = null;
	
		if(searchContactInEmailProspectGrid.toString().equalsIgnoreCase(searchContactInEmailProspectGrid.Yes.toString())) {
			if(sendKeys(driver, getSearchForAContactTextBox(environment, mode, timeOut), contactSearchValue, "search text box", action.SCROLLANDBOOLEAN)) {
				appLog.info("Passed Value in Search Text box: "+contactSearchValue);
				ThreadSleep(2000);
				if (click(driver,getSearchIconForAContactTextBox(environment, mode, 10), "Search Icon", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked On Search Icon", YesNo.No);	
					ThreadSleep(2000);
					ele=getRecordsOnBulkEmail(environment, mode, timeOut);
					if(ele!=null) {
						int RecordCount=Integer.parseInt(ele.getText().trim().split(":")[1]);
						if(RecordCount==1) {
							appLog.info("Bulk Email Record Count is matched Successfully.");
						}else {
							appLog.error("Bulk Email Record Count is not Matched. Expected: 1"+"\t Actual :"+RecordCount);
							result.add("Bulk Email Record Count is not Matched. Expected: 1"+"\t Actual :"+RecordCount);
						}
					}else {
						appLog.error("Email Prospect Record Count is not visible so cannot verify record Count");
						result.add("Email Prospect Record Count is not visible so cannot verify record Count");
					}
				} else {
					log(LogStatus.SKIP, "Not Able to Click On Search Icon", YesNo.Yes);
				}
				
			}else {
				appLog.error("Not able to pass value "+contactSearchValue+" in search text box so cannot search contact: "+contactSearchValue);
				result.add("Not able to pass value "+contactSearchValue+" in search text box so cannot search contact: "+contactSearchValue);
			}
	
		if(ScrollAndClickOnContactNameCheckBoxInBulkEmail(fname,lname, 10)) {
			appLog.info("clicked on Contact Name Check Box: "+fname+" "+lname);
		}else {
			appLog.error("Not able to click on Contact Name :"+fname+" "+lname+" check box so cannot add contact in review prospect list");
			result.add("Not able to click on Contact Name :"+fname+" "+lname+" check box so cannot add contact in review prospect list");
		}
	}
	return result;
}

public boolean ScrollAndClickOnContactNameCheckBoxInBulkEmail(String fname,String lname,int timeout) {
	int j = 0;
	WebElement ele = null;
	String XpathelementTOSearch="";
	XpathelementTOSearch = "//span[contains(@class,'aw-hpanel-middle')]//span[contains(@class,'aw-grid-row')]//a[text()='"+lname+"']/../preceding-sibling::span//a[text()='"+fname+"']/../preceding-sibling::span/span/span[1]";
	By byelementToSearch = By.xpath(XpathelementTOSearch);
	int widgetTotalScrollingHeight = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
			.executeScript("return arguments[0].scrollHeight", getEmailProspectSelectProspectsGridScrollBox(10))));
	((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0,0)", getEmailProspectSelectProspectsGridScrollBox(10));
	for (int i = 0; i <= widgetTotalScrollingHeight / 25; i++) {
		if (!driver.findElements(byelementToSearch).isEmpty()
				&& driver.findElement(byelementToSearch).isDisplayed()) {
			appLog.info("Element Successfully Found and displayed");
			ThreadSleep(500);
			ele = FindElement(driver, XpathelementTOSearch, "", action.BOOLEAN, timeout);
			if (ele != null) {
				if (click(driver, ele, "", action.BOOLEAN)) {
					appLog.info("clicked on Contact Name : "+fname+" "+lname);
				} else {
					appLog.error("Not able to clicke on Contact Name: "+fname+" "+lname);
					return false;
				}
			}
			break;
		} else {
			System.out.println("Not FOund: " + byelementToSearch.toString());
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 45) + ")",
					getEmailProspectSelectProspectsGridScrollBox(10));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i == widgetTotalScrollingHeight / 50) {
				return false;
			}
		}
	}
	return true;
}

public boolean selectEmailTemplateFromEmailProspect(String folderName, String emailTemplateName) {
	int j = 0;
	WebElement ele=null;
	String XpathelementTOSearch="//span[text()='"+emailTemplateName+"']/preceding-sibling::span/input";
	if(folderName!=null) {
		if(selectVisibleTextFromDropDown(driver, getEmailProspectFolderDropDownList(20), "folder drop downlist", folderName)) {
			appLog.info("Folder Name is selected from folder drop down list : "+folderName);
		}else {
			appLog.error("Not able to select email prospects email template folder: "+folderName);
			return false;
		}
	}
	By byelementToSearch = By.xpath(XpathelementTOSearch);
	int widgetTotalScrollingHeight = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
			.executeScript("return arguments[0].scrollHeight", getEmailProspectStep2CustomEmailtemplateScrollBox(10))));
	((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0,0)", getEmailProspectStep2CustomEmailtemplateScrollBox(10));
	for (int i = 0; i <= widgetTotalScrollingHeight / 25; i++) {
		if (!driver.findElements(byelementToSearch).isEmpty() && driver.findElement(byelementToSearch).isDisplayed()) {
			appLog.info("Element Successfully Found and displayed");
			ThreadSleep(500);
			ele = FindElement(driver, XpathelementTOSearch, "", action.BOOLEAN, 10);
			if (ele != null) {
				if (click(driver, ele, "", action.BOOLEAN)) {
					appLog.info("clicked on Custom email template radio button : "+emailTemplateName);
					
				} else {
					appLog.error("Not able to clicked on email template radio button: "+emailTemplateName);
					return false;
				}
			}
			break;
		} else {
			System.out.println("Not FOund: " + byelementToSearch.toString());
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 45) + ")",
					getEmailProspectStep2CustomEmailtemplateScrollBox(10));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i == widgetTotalScrollingHeight / 50) {
				return false;
			}
		}
	}
	return true;
	
}

public boolean VerifyBulkEmailFunctionality(String environment,String mode,String reportName,String templateName,String fname,String lname,String contactSearchValue,searchContactInEmailProspectGrid searchContactInEmailProspectGrid,String folderName, String emailTemplateName){
	boolean flag=false;
	HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	switchToFrame(driver, 30, getCreateFundraisingsFrame_Lighting(20));
	String SmokeReportFolderName=reportName;
	String SmokeReportName=templateName;
	if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, SmokeReportFolderName, SmokeReportName)) {
		log(LogStatus.INFO, "Clicked On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.No);	
		List<String> result = hp.selectContactAndVerifyInBulkEmail(environment, mode, fname,lname,contactSearchValue,searchContactInEmailProspectGrid, 10);
		if (result.isEmpty()) {
			log(LogStatus.PASS, "Able to Search/Check Contact : "+fname+" "+lname, YesNo.No);	
			if (click(driver, bp.getEmailProspectStep1NextBtn(20), "step 1 next button",action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Steps 1 Next button", YesNo.No);
				String EmailTemplate1_FolderName=folderName;
				String EmailTemplate1_TemplateName=emailTemplateName;
				if (hp.selectEmailTemplateFromEmailProspect(EmailTemplate1_FolderName, EmailTemplate1_TemplateName)) {
					log(LogStatus.INFO, "PE Test Custom Email Template is selected successfully",YesNo.No);
							if (click(driver, bp.getEmailProspectStep2NextBtn(30), "step 2 next button",action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on step 2 next button", YesNo.No);
								if (click(driver, bp.getEmailProspectSendBtn(TopOrBottom.TOP, 30), "send button",action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on send button", YesNo.No);	
									WebElement ele = hp.step4_BulkEmailPage(environment, mode, 10);
										
										if (ele!=null) {
											String msg = ele.getText().trim();
											if (HomePageErrorMessage.step4_YourEmail.equals(msg)) {
												log(LogStatus.PASS, "Step4 Page Verified : "+msg, YesNo.No);
											} else {
												sa.assertTrue(false, "Step4 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step4_YourEmail);
												log(LogStatus.FAIL, "Step4 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.step4_YourEmail, YesNo.Yes);
											}
										} else {
											sa.assertTrue(false, "Step4 Page Element is null");
											log(LogStatus.FAIL, "Step4 Page Element is null", YesNo.Yes);
										
										}
										
										if (click(driver, bp.getEmailProspectFinishBtn(30),"finish button", action.BOOLEAN)) {
											log(LogStatus.INFO, "Clicked on finish button", YesNo.No);
											flag=true;
										} else {
											sa.assertTrue(false, "Not able to click on finish button");
											log(LogStatus.ERROR, "Not able to click on finish button",YesNo.Yes);
										}
									} else {
										sa.assertTrue(false,"Not able to click on send button so cannot send email to contact: "+ fname + " " + lname);
										log(LogStatus.ERROR,"Not able to click on send button so cannot send email to contact: "+ fname + " " + lname,YesNo.Yes);
									}


							} else {
								sa.assertTrue(false,"Not able to click on steps 2 nect button so cannot send email to contact : "+ fname + " " + lname);
								log(LogStatus.ERROR,"Not able to click on steps 2 nect button so cannot send email to contact : "+ fname + " " + lname,YesNo.Yes);
							}
				
				} else {
					sa.assertTrue(false,"Not able to select Email Template from Bulk Email so cannot send email to contact "+ fname + " " + lname);
					log(LogStatus.ERROR,"Not able to select Email Template from Bulk Email so cannot send email to contact "+ fname + " " + lname,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not able to click on Steps 1 Next button so cannot select email template and send email to contact "+ fname + " " + lname);
				log(LogStatus.ERROR,"Not able to click on Steps 1 Next button so cannot select email template and send email to contact "+ fname + " " + lname,YesNo.Yes);
			}
			
		} else {
			sa.assertTrue(false, "Not Able to Search/Check Contact : "+fname+" "+lname);
			log(LogStatus.FAIL, "Not Able to Search/Check Contact : "+fname+" "+lname, YesNo.Yes);
		}
		
	} else {
		sa.assertTrue(false, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName);
		log(LogStatus.SKIP, "Not Able to Click On "+SmokeReportFolderName+" : "+SmokeReportName, YesNo.Yes);	
	}
	return flag;
}

public boolean selectFundNameOrCompanyNameOnCreateFundraisings(String environment, String mode, PopUpName selectFundOrCompanyNamePopName, String fundName, String companyName) {
	boolean flag = false;
	if(selectFundOrCompanyNamePopName.toString().equalsIgnoreCase(PopUpName.selectFundPopUp.toString())) {
		if(click(driver, getSelectFundNameFromSelectFundPopUpLookUpIcon(60), "fund name look up icon", action.BOOLEAN)) {
			log(LogStatus.INFO, "clicked on fund name look up icon", YesNo.No);
			if(selectValueFromLookUpWindow(fundName)) {
				log(LogStatus.INFO, fundName+" fund Name is select successfully", YesNo.No);
				if(companyName!=null) {
					if(fundName!=null) {
						if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 30, getCreateFundraisingsFrame_Lighting(20));
						}
					}
					if(click(driver,getSelectCompanyNameFromSelectFundPopUpLookUpIcon(30), "company name look up icon", action.BOOLEAN)) {
						if(selectValueFromLookUpWindow(companyName)) {
							log(LogStatus.INFO, companyName+" company Name is select successfully", YesNo.No);
							flag= true;
						}else {
							log(LogStatus.ERROR, "Not able to select company Name "+companyName+" from look up pop up", YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on company name look up icon so cannot select compnay name : "+companyName,YesNo.Yes);
					}
				}else {
					flag = true;
				}
			}else {
				log(LogStatus.ERROR, "Not able to select fund Name "+fundName+" from look up pop up", YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on fund name look up icon so cannot select fund name: "+fundName, YesNo.Yes);
		}
	}else if (selectFundOrCompanyNamePopName.toString().equalsIgnoreCase(PopUpName.SelectFundPopUpFromCompmayPage.toString())){
		if(selectVisibleTextFromDropDown(driver, getSelectFundNameDropDownListInSelectFundPopUp(30), "select fund name drop down list in select fund pop up", fundName)) {
			log(LogStatus.INFO, "fund Name "+fundName+" is selected from fund name drop down list", YesNo.No);
			flag=true;
		}else {
			log(LogStatus.ERROR, "Not able to select fund name "+fundName+" from select fund pop up", YesNo.Yes);
		}
	}else {
		if(click(driver,getSelectCompanyNameWarningPopUpLookUpIcon(30), "company name look up icon", action.BOOLEAN)) {
			if(selectValueFromLookUpWindow(companyName)) {
				log(LogStatus.INFO, companyName+" company Name is select successfully", YesNo.No);
				flag= true;
			}else {
				log(LogStatus.ERROR, "Not able to select company Name "+companyName+" from look up pop up", YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on company name look up icon so cannot select compnay name : "+companyName,YesNo.Yes);
		}
	}
	return flag;
}


/**
 * @author Ankit Jaiswal
 * @param tabName
 * @param environment TODO
 * @param mode TODO
 * @param fundName
 * @param fieldName
 * @param operator
 * @param Value
 * @param addFilterLogic TODO
 * @return
 */
public boolean applyFilterOnSearchBasedOnAccountsandContacts(FundraisingContactPageTab tabName, SearchBasedOnExistingFundsOptions searchBasedOnExistingFundsOptions, String environment, String mode, String fundName, String fieldName, String operator, String Value, String addFilterLogic) {
	List<String> splitedValue = new ArrayList<String>();
	if(searchBasedOnExistingFundsOptions.toString().equalsIgnoreCase(SearchBasedOnExistingFundsOptions.OnlyFundraisingContacts.toString())) {
		ThreadSleep(2000);
		if(mouseOverOperation(driver, getSearchBasedOnExistingFundsDownArrow(20))) {
			ThreadSleep(2000);
			if(clickUsingJavaScript(driver, getOnlyFundraisingContactOptionOnSearchBasedOnExistingFunds(20), "Only Fundraising Contact text")) {
				log(LogStatus.INFO, "clicked on Only Fundraising Contact text", YesNo.No);
			}else {
				log(LogStatus.ERROR, "Not able to click on Only Fundraising Contact option so cannot applied filter logic", YesNo.Yes);
				return false;
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on Search Based On Existing Funds Down Arrow", YesNo.Yes);
			return false;
		}
	}
	if(tabName.toString().equalsIgnoreCase(FundraisingContactPageTab.SearchBasedOnExistingFunds.toString())) {
		if(fundName!=null) {
			if(click(driver, getSelectFundNameFromSearchBasedOnExistingFundLookUpIcon(20), "Select FundName From Search Based On Existing Fund LookUp Icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on fund name look up icon", YesNo.No);
				if(selectValueFromLookUpWindow(fundName)) {
					log(LogStatus.INFO, fundName+" fund Name is select successfully", YesNo.No);
					if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 30, getCreateFundraisingsFrame_Lighting(20));
					}
				}else {
					log(LogStatus.ERROR, "Not able to select fund Name "+fundName+" from look up pop up", YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on fund Name look up icon so cannot select fund Name", YesNo.Yes);
				return false;
			}
		}
	}
	if(fieldName!=null && operator!=null && !fieldName.isEmpty() && !operator.isEmpty()) {
		String[] splitedFieldName=fieldName.split("<break>");
		String [] splitedOperator=operator.split("<break>");
		if(Value!=null) {
			for(int i=0; i<Value.split("<break>").length; i++) {
				splitedValue.add(Value.split("<break>")[i]);
			}
		}
		WebElement ele=null;
		for(int i=0; i<splitedFieldName.length; i++) {
			if(i<splitedFieldName.length-1) {
				if(click(driver, getAddRowsLink(30),"add rows link", action.SCROLLANDBOOLEAN)){
					log(LogStatus.INFO, "clicked on add rows link", YesNo.No);
				}else {
					log(LogStatus.ERROR, "Not able to click on add rows link so cannot add filter logic", YesNo.Yes);
					return false;
				}
			}
			ele=isDisplayed(driver,FindElement(driver, "//input[@id='a"+(i+1)+"aa']", "field text box", action.SCROLLANDBOOLEAN, 10), "Visibility",10, "field Text Box");
			if(ele!=null) {
				if(sendKeys(driver, ele, splitedFieldName[i], "field text box", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Passed Value in field Name Text box "+splitedFieldName[i], YesNo.No);
					ele=isDisplayed(driver,FindElement(driver, "//ul[contains(@style,'block;')]//li//a[text()='"+splitedFieldName[i]+"']", "field text box", action.SCROLLANDBOOLEAN, 10), "Visibility",10, "field Text Box");
					ThreadSleep(500);
					if(click(driver, ele, splitedFieldName[i]+" autocomplete text box", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on field Name "+splitedFieldName[i]+" from autocomplete text box",YesNo.No);
						ele=isDisplayed(driver,FindElement(driver, "//select[@id='opt"+(i+1)+"']", "operator drop down list", action.SCROLLANDBOOLEAN, 30), "Visibility",10, "operator drop down list");
						if(selectVisibleTextFromDropDown(driver,ele, "operator drop down list", splitedOperator[i])) {
							log(LogStatus.INFO, "select Operator : "+splitedOperator[i],YesNo.No);
							if(Value!=null) {
								ele=isDisplayed(driver,FindElement(driver, "//input[@id='criteriatextbox"+(i+1)+"']", "Value text box", action.SCROLLANDBOOLEAN, 10), "Visibility",10, "Value Text Box");
								if(ele!=null) {
									if(sendKeys(driver, ele, splitedValue.get(i), "Value Text Box", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Passed Value "+splitedValue.get(i)+" in value text box", YesNo.No);
										
									}else {
										log(LogStatus.ERROR, "Not able to pass Value "+splitedValue.get(i)+" in Text box so cannot apply filter logic", YesNo.Yes);
										return false;
									}
								}else {
									log(LogStatus.ERROR, "Value Text is not visible so cannot apply filter logic", YesNo.Yes);
									return false;
								}
							}
						}else {
							log(LogStatus.ERROR, "Not able to select Operator "+splitedOperator[i]+" from Drop Down List so cannot apply filter logic", YesNo.Yes);
							return false;
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on field Name "+splitedFieldName[i]+" form autocomplete text box so cannot apply filter", YesNo.Yes);
						return false;
					}
				}else {
					log(LogStatus.ERROR, "Not able to pass value in Field Name text Box : "+splitedFieldName[i]+" so cannot apply filter logic",YesNo.Yes);
					return false;
				}
			}else {
				log(LogStatus.ERROR, "Field Text Box is not visible so cannot apply filter logic",YesNo.Yes);
				return false;
			}
		}
	}
	if(addFilterLogic!=null) {
		WebElement ele=getAddFilterLogicLinkOnCreateFundraising(10);
		if(ele!=null) {
			if(click(driver, ele, "add row button", action.BOOLEAN)) {
				appLog.info("clicked on add row link");
				ThreadSleep(2000);
				ele=isDisplayed(driver,FindElement(driver, "//input[@id='j_id0:CreateFundraisingFormId:textfilt']", "Add filter logic text box", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
				if(ele!=null) {
					if(sendKeys(driver, ele,addFilterLogic, "add filter logic text box", action.SCROLLANDBOOLEAN)) {
						appLog.info("pass value in filter logic text box : "+addFilterLogic);
						
					}else {
						appLog.error("Not able to pass value on add filter logic text box so cannot add filter logic "+addFilterLogic);
						return false;
					}
				}else {
					appLog.error("Not able find add filter logic text box so cannot add filter logic "+addFilterLogic);
					return false;
				}
				
			}else {
				appLog.error("Not able to click on add filter logic so cannot add filter logic "+addFilterLogic);
				return false;
			}
		}else {
			appLog.error("Not able find add filter logic link so cannot add filter logic "+addFilterLogic);
			return false;
		}
	}
	if(click(driver, getSearchBasedOnAccountsAndContactsSearchBtn(30), "search button", action.SCROLLANDBOOLEAN)) {
		log(LogStatus.INFO, "clicked on Search Button", YesNo.No);
		return true;
	}else {
		log(LogStatus.ERROR, "Not able to click on Search Button so cannot apply filter", YesNo.Yes);
	}
	return false;
}

public List<String> selectInvestorsContactFromCreateFundRaising(List<String> contactName,List<String> accountName ){
	List<String> result = new ArrayList<String>();
	if(!contactName.isEmpty() && !accountName.isEmpty()) {
		for (int i = 0; i < contactName.size(); i++) {
			if(ScrollAndClickOnContactNameCheckBoxAddInvestor(contactName.get(i), accountName.get(i))) {
				log(LogStatus.INFO, "clicked on Contact Name Check Box: "+contactName.get(i), YesNo.No);
			}else {
				log(LogStatus.ERROR, "Not able to click on Contact Name :"+contactName.get(i)+" check box so cannot add contact in review investor list", YesNo.Yes);
				result.add("Not able to click on Contact Name :"+contactName.get(i)+" check box so cannot add contact in review investor list");
			}
		}
	}else {
		log(LogStatus.ERROR, "Contact Name and Account Name list is empty so cannot select contact Name in select investor grid", YesNo.Yes);
		result.add("Contact Name and Account Name list is empty so cannot select contact Name in select investor grid");
		
	}
	return result;
}

/**
 * @author Ankit Jaiswal
 * @param contactName
 * @param accountName
 * @param timeout
 * @return
 */
public boolean ScrollAndClickOnContactNameCheckBoxAddInvestor(String contactName, String accountName) {
	int j = 0;
	WebElement ele = null;
	String XpathelementTOSearch="";
	int widgetTotalScrollingHeight=0;
	XpathelementTOSearch = xpathOfSelectInvestorsCheckBox(contactName, accountName);
//	System.err.println("XpathelementTOSearch "+XpathelementTOSearch);
	By byelementToSearch = By.xpath(XpathelementTOSearch);
//	System.err.println("byelementToSearch "+byelementToSearch);
	widgetTotalScrollingHeight = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
			.executeScript("return arguments[0].scrollHeight", getSelectInvestorGridScrollBox(30))));
	((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0,0)",getSelectInvestorGridScrollBox(30));
//	System.err.println("Height :"+widgetTotalScrollingHeight);
	for (int i = 0; i <= widgetTotalScrollingHeight / 25; i++) {
		try {
			if (!driver.findElements(byelementToSearch).isEmpty()
					&& driver.findElement(byelementToSearch).isDisplayed()) {
				appLog.info("Element Successfully Found and displayed");
				ThreadSleep(500);
				ele = FindElement(driver, XpathelementTOSearch, "", action.BOOLEAN, 30);
				if (ele != null) {
					if (click(driver, ele, "", action.BOOLEAN)) {
						appLog.info("clicked on Contact Name : "+contactName);
					} else {
						appLog.error("Not able to clicke on Contact Name: "+contactName);
						return false;
					}
				}
				break;
			} else {
				System.out.println("Not FOund: " + byelementToSearch.toString());
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 45) + ")",
						getSelectInvestorGridScrollBox(20));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (i == widgetTotalScrollingHeight / 50) {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.err.println("Inside : "+i);
		}
	}
	return true;
}

/**
 * @author Ankit Jaiswal
 * @param ContactName
 * @param AccountName
 * @return String xpath
 */
public static String xpathOfSelectInvestorsCheckBox(String ContactName,String AccountName) {
	String xpath="//span[@id='Select_from_Search_Results-view-box-middle']//span/div/a";
	String xpath1="/../../following-sibling::span/div/a";
	for(int i=0; i<ContactName.split(" ").length; i++) {
		xpath=xpath+"[contains(text(),'"+ContactName.split(" ")[i]+"')]";
	}
	for(int i=0; i<AccountName.split(" ").length; i++) {
		xpath1=xpath1+"[contains(text(),'"+AccountName.split(" ")[i]+"')]";
	}
	return xpath+xpath1+"/../../preceding-sibling::span/span/span[1]";
}

/**
 * @author Ankit Jaiswal
 * @param environment
 * @param mode
 * @param fundraisingName
 * @param fundName
 * @param companyName
 * @param legalName
 * @param commitmentType
 * @return true/false
 */
public boolean selectFundraisingNameOrCommitmentType(String environment,String mode,String fundraisingName, String fundName, String companyName,String legalName,CommitmentType commitmentType) {
	WebElement ele=null;
	boolean flag=false;
	if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
		switchToFrame(driver, 30, getCreateCommitmentFrame_Lightning(60));
		
	}
	ThreadSleep(5000);
	if(fundraisingName!=null) {
		if(click(driver, getFundRaisingNameLookUpIcon(120), "fundraising name look up icon", action.SCROLLANDBOOLEAN)) {
			if(selectValueFromLookUpWindow(fundraisingName)) {
				log(LogStatus.INFO, fundraisingName+" fundraising name is selected", YesNo.No);
				flag=true;
			}else {
				log(LogStatus.ERROR, fundraisingName+" fundraising name is not selected from fundraising name look up", YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on fundraising name look up icon so cannot select fundraising name "+fundraisingName, YesNo.Yes);
		}
	}else {
		if(commitmentType.toString().equalsIgnoreCase(CommitmentType.fund.toString())) {
			if(click(driver, getFundTypeCommitment(120), "fund type text", action.SCROLLANDBOOLEAN)) {
				if(click(driver, getFundNameLookUpIconOnCreateCommitmentPopUp(20), "fund name look up icon", action.SCROLLANDBOOLEAN)) {
					if(selectValueFromLookUpWindow(fundName)) {
						log(LogStatus.INFO, "fund name is selected "+fundName, YesNo.No);
						if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 30,getCreateCommitmentFrame_Lightning(20));
							
						}
						if(click(driver, getLegalNameLookUpIcon(PageName.CreateCommitmentFundType, 20), "legal name look up icon", action.SCROLLANDBOOLEAN)) {
							if(selectValueFromLookUpWindow(legalName)) {
								log(LogStatus.INFO, "legal name is selected "+legalName, YesNo.No);
								flag=true;
							}else {
								log(LogStatus.ERROR, "Not able to select institutionName "+legalName, YesNo.Yes);
								
							}
						}else {
							log(LogStatus.ERROR, "Not able to select institutionName "+legalName, YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to select Fund Name "+fundName+" so cannot select institutionName "+legalName, YesNo.Yes);
					}
					
				}else {
					log(LogStatus.ERROR, "Not click on fund Name look up icon so cannot select fund Name "+fundName+" so cannot select institutionName "+legalName,YesNo.Yes);
				}
				
			}else {
				log(LogStatus.ERROR, "Not able to click on fund type on create commitment text so cannot select fundName "+fundName+" institutionName "+legalName,YesNo.Yes);
			}
		}else {
			if(click(driver, getCoInvesmentTypeCommitment(120), "co investment type text", action.SCROLLANDBOOLEAN)) {
				if(companyName!=null) {
					if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 30, getCreateCommitmentFrame_Lightning(20));
						
					}
					if(click(driver, getSelectCompanyNameLookUpIconOnCreateCommitmentPopUp(20), "company name look up icon", action.SCROLLANDBOOLEAN)) {
						if(selectValueFromLookUpWindow(companyName)) {
							log(LogStatus.INFO, "Company name is selected "+fundName, YesNo.No);
							flag=true;
							
						}else {
							log(LogStatus.ERROR, "Not able to select Company Name "+companyName+" so cannot select institutionName "+legalName, YesNo.Yes);
						}
						
					}else {
						log(LogStatus.ERROR, "Not able to click on company Name look up icon so cannot select Company Name "+companyName+" so cannot select institutionName "+legalName,YesNo.Yes);
					}
				}else {
					flag=true;
				}
				if(flag) {
					if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToFrame(driver, 30, getCreateCommitmentFrame_Lightning(20));
						
					}
					if(click(driver, getLegalNameLookUpIcon(PageName.CreateCommitmentCoInvestmentType, 20), "legal name look up icon", action.SCROLLANDBOOLEAN)) {
						if(selectValueFromLookUpWindow(legalName)) {
							log(LogStatus.INFO, "legal name is selected "+legalName, YesNo.No);
							flag=true;
						}else {
							log(LogStatus.ERROR, "Not able to select institutionName "+legalName, YesNo.Yes);
							flag=false;
							
						}
					}else {
						log(LogStatus.ERROR, "Not able to select institutionName "+legalName, YesNo.Yes);
						flag=false;
					}
				}
				
				log(LogStatus.ERROR, "Not able to click on co investment type on create commitment text so cannot select Company Name "+companyName+" institutionName "+legalName,YesNo.Yes);
			}
		}
	}
	if(flag) {
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToFrame(driver, 60, getCreateCommitmentFrame_Lightning(120));
			
		}
		List<WebElement> ele1=getCommitmentCreationContinueBtn(2);
		for(int i=0; i<ele1.size(); i++) {
			if(click(driver, ele1.get(i), "continue button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on continue button", YesNo.No);
				break;
			}else {
				if(i==ele1.size()-1) {
					log(LogStatus.ERROR, "Not able to click on create commitment pop up continue button", YesNo.Yes);
					flag=false;
				}
			}
		}
	}
	if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
		switchToDefaultContent(driver);
		
	}
	return flag;
}

/**
 * @author Ankit Jaiswal
 * @param environment
 * @param mode
 * @param commitmentInformation
 * @param partnerType
 * @param taxForms
 * @param PlacementFee
 * @return
 */
public boolean commitmentInfoAndAdditionalInfo(String environment,String mode, String[][] commitmentInformation,String partnerType, String taxForms, String PlacementFee) {
	boolean flag = true;
	List<WebElement> ele = new ArrayList<WebElement>();
	WebElement ele1 = null;
	String xpath="",xpath1="";
	if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
		switchToFrame(driver, 30, getCreateCommitmentFrame_Lightning(20));
		
	}
			int i=0;
			for (String[] a : commitmentInformation) {
				
				if(i>0 && i<=commitmentInformation.length-1) {
					if(click(driver, getLogMultipleCommitmentsLink(20), "Log Multiple Commitments Link", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Log Multiple Commitments Link", YesNo.No);
					}else {
						log(LogStatus.ERROR, "Not able to click on Log Multiple Commitments Link so cannot add more rows", YesNo.Yes);
						flag = false;
					}
				}
				
				xpath="//input[contains(@name,'mypage:CommitmentCreationFormId') and contains(@name,':"+i+":')]";
				ele=FindElements(driver, xpath, "LP, CommitmentAmount,partnership,FinalCommitmentDate Text Box list");
				  if(!ele.isEmpty()) {
				for(int j=0; j<a.length;j++) {
					String[] aa=a[j].split("<break>");
					String value= null;
					if(aa.length>1) {
						if(aa[1].equalsIgnoreCase(CreatedOrNot.AlreadyCreated.toString())) {
							value=aa[0];
						}else {
							value=aa[0]+"\t";
						}
					}else {
						value=aa[0];
					}
					if(sendKeys(driver, ele.get(j), value, "commitment information text box" , action.SCROLLANDBOOLEAN)) {
						
						ThreadSleep(3000);
						if(j==0 || j==2) {
							if(aa.length==2) {
								if(aa[1].equalsIgnoreCase(CreatedOrNot.AlreadyCreated.toString())) {
									xpath1="//ul[@class='ui-menu ui-widget ui-widget-content ui-autocomplete ui-front'][contains(@style,'display: block;')]//div[text()='"+aa[0]+"']";
									ele1=FindElement(driver, xpath1, "auto complete drop downlist", action.SCROLLANDBOOLEAN, 5);
									if(ele1!=null) {
										if(click(driver, ele1, "auto complete drop down list", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on "+aa[0]+"name text", YesNo.No);
										}else {
											log(LogStatus.ERROR, "Not able to click on "+aa[0]+"name text", YesNo.Yes);
											flag = false;
										}
									}else {
										log(LogStatus.ERROR, "auto complete text is not visible for "+aa[0]+" text", YesNo.Yes);
										flag = false;
									}
								}
							}else {
								if(aa.length>=3) {
									for(int k=2; k<aa.length; k++) {
										String[] labelsWithValue =  aa[k].split(":");
										labelsWithValue[0] =labelsWithValue[0].replace("_", " ");
										xpath = "//label[text()='"+labelsWithValue[0]+"']/../following-sibling::td//label/";
										String tag= getTagElementForGivenXpath(xpath+"*[2]");
										if(tag!=null){
											appLog.info("Tag Element for : " + labelsWithValue[0]+ " >>>> "+tag);
											log(LogStatus.INFO, "Tag Element for : " + labelsWithValue[0]+ " >>>> "+tag,YesNo.No);
											if(labelsWithValue.length!=1) {
												String fullXpath = xpath+tag;
												if(enteringValuesOnTheBasisOfTag(fullXpath, labelsWithValue[0], labelsWithValue[1], tag)){
													appLog.info(" value added to "+ labelsWithValue[0] +"  : " + labelsWithValue[1]);
													
													
												}else{
													log(LogStatus.ERROR, "Not able to enter value in "+labelsWithValue[0], YesNo.Yes);
													flag = false;
												}
											}
											
										}else{
											log(LogStatus.ERROR, "Tag Element is null for : " + labelsWithValue[0], YesNo.Yes);
											flag = false;
										}
										
									}
									if(j==0) {
										if(click(driver, getNewLimitedPartnerAddBtnInCreateCommitment(10), "new limited partner add button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on add button in limited partner pop up", YesNo.No);
											String pencilIcon="//div[@id='LPEdit"+i+"']";
											WebElement pencilIconElement=FindElement(driver, pencilIcon, "lp pencil icon", action.SCROLLANDBOOLEAN, 5);
											if(pencilIconElement!=null) {
												if(click(driver, pencilIconElement, "LP pencil icon", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "clicked on LP pencil icon", YesNo.No);
													ThreadSleep(3000);
													for(int k=2; k<aa.length; k++) {
														String[] labelsWithValue =  aa[k].split(":");
														labelsWithValue[0] =labelsWithValue[0].replace("_", " ");
														xpath = "//label[text()='"+labelsWithValue[0]+"']/../following-sibling::td//label/";
														String tag= getTagElementForGivenXpath(xpath+"*[2]");
														if(tag!=null){
															appLog.info("Tag Element for : " + labelsWithValue[0]+ " >>>> "+tag);
															log(LogStatus.INFO, "Tag Element for : " + labelsWithValue[0]+ " >>>> "+tag,YesNo.No);
															if(labelsWithValue.length!=1) {
																String fullXpath = xpath+tag;
																if(verifyValuesOnTheBasisOfTag(fullXpath, labelsWithValue[0], labelsWithValue[1], tag)){
																	appLog.info("value is verified for "+ labelsWithValue[0] +" with value : " + labelsWithValue[1]);
																	
																	
																}else{
																	log(LogStatus.ERROR, "value is not verified for "+ labelsWithValue[0] +" with value : " + labelsWithValue[1], YesNo.Yes);
																	flag = false;
																}
															}
														}else{
															log(LogStatus.ERROR, "Tag Element is null for label : " + labelsWithValue[0]+" so cannot verify value", YesNo.Yes);
															flag = false;
														}
														
													}
													if(click(driver, getNewLimitedPartnerCancelBtnInCreateCommitment(10), "cancel button", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO, "clicked on new limited partner pop up cancel button", YesNo.No);
													}else {
														log(LogStatus.INFO, "Not able to click on new limited partner pop up cancel button", YesNo.Yes);
														flag = false;
													}
												}else {
													log(LogStatus.ERROR, "Not able to click on Lp pencil icon so cannot click on it and verify filled data", YesNo.Yes);
													flag = false;
												}
											}else {
												log(LogStatus.ERROR, "Lp pencil icon is not visible so cannot click on it and verify filled data", YesNo.Yes);
												flag = false;
											}
										}else {
											log(LogStatus.INFO, "Not able to click on add button in limited partner pop up", YesNo.Yes);
											flag = false;
										}
									}else if (j==2) {
										if(click(driver, getNewPartnerShipAddbtnInCreateCommitment(10), "new partnership add button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on add button in new partnership pop up", YesNo.No);
											String pencilIcon="//div[@id='PTEdit"+i+"']";
											WebElement pencilIconElement=FindElement(driver, pencilIcon, "partnership pencil icon", action.SCROLLANDBOOLEAN, 5);
											if(pencilIconElement!=null) {
												if(click(driver, pencilIconElement, "partnership pencil icon", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "clicked on partnership pencil icon", YesNo.No);
													
													for(int k=2; k<aa.length; k++) {
														String[] labelsWithValue =  aa[k].split(":");
														labelsWithValue[0] =labelsWithValue[0].replace("_", " ");
														xpath = "//label[text()='"+labelsWithValue[0]+"']/../following-sibling::td//label/";
														String tag= getTagElementForGivenXpath(xpath+"*[2]");
														if(tag!=null){
															appLog.info("Tag Element for : " + labelsWithValue[0]+ " >>>> "+tag);
															log(LogStatus.INFO, "Tag Element for : " + labelsWithValue[0]+ " >>>> "+tag,YesNo.No);
															if(labelsWithValue.length!=1) {
																String fullXpath = xpath+tag;
																if(verifyValuesOnTheBasisOfTag(fullXpath, labelsWithValue[0], labelsWithValue[1], tag)){
																	appLog.info("value is verified for "+ labelsWithValue[0] +" with value : " + labelsWithValue[1]);
																	
																	
																}else{
																	log(LogStatus.ERROR, "value is not verified for "+ labelsWithValue[0] +" with value : " + labelsWithValue[1], YesNo.Yes);
																	flag = false;
																}
															}
														}else{
															log(LogStatus.ERROR, "Tag Element is null for label : " + labelsWithValue[0]+" so cannot verify value", YesNo.Yes);
															flag = false;
														}
														
													}
													if(click(driver, getNewPartnerShipCancelbtnInCreateCommitment(10), "cancel button", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO, "clicked on new partnership pop up cancel button", YesNo.No);
													}else {
														log(LogStatus.INFO, "Not able to click on new partnership pop up cancel button", YesNo.Yes);
														flag = false;
													}
												}else {
													log(LogStatus.ERROR, "Not able to click on partnership pencil icon so cannot click on it and verify filled data", YesNo.Yes);
													flag = false;
												}
											}else {
												log(LogStatus.ERROR, "partnership pencil icon is not visible so cannot click on it and verify filled data", YesNo.Yes);
												flag = false;
											}
											
											
											
										}else {
											log(LogStatus.INFO, "Not able to click on add button in new partnership pop up", YesNo.Yes);
											flag = false;
										}
									}
								}
							}
						}
						log(LogStatus.INFO, "passed value in text box "+aa[0], YesNo.Yes);
						
						
					}else {
						log(LogStatus.ERROR, "Not able to pass value in text box "+aa[0]+" so cannot create commitment", YesNo.Yes);
						flag = false;
					}
				}
			}else { 
				log(LogStatus.ERROR,"Limited partner text box is not visible so cannot create cimmitments", YesNo.Yes); 
				flag = false; 
			}
				i++;
			}
	if(partnerType!=null) {
		if(selectVisibleTextFromDropDown(driver, getPartnerTypeDropDownList(20), "partner type drop down list", partnerType)) {
			log(LogStatus.INFO, partnerType+" is selected in partner type drop down list", YesNo.No);
		}else {
			log(LogStatus.ERROR,partnerType+" is not selected in partner type drop down list", YesNo.Yes);
			flag = false;
		}
	}
	if(taxForms!=null) {
		if(selectVisibleTextFromDropDown(driver, getTaxformsDropDownList(20), "tax forms drop down list", taxForms)) {
			log(LogStatus.INFO, taxForms+" is selected intax forms drop down list", YesNo.No);
		}else {
			log(LogStatus.ERROR,taxForms+" is not selected in tax forms drop down list", YesNo.Yes);
			flag = false;
		}
	}
	if(PlacementFee!=null) {
		if(sendKeys(driver, getPlacementFeeTextBox(10), PlacementFee, "placement fee text box", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "pass value in placement fee text box : "+PlacementFee, YesNo.No);
		}else {
			log(LogStatus.ERROR,"Not able to pass value in placement fee text box : "+PlacementFee, YesNo.Yes);
			flag = false;
		}
	}
	if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
		switchToDefaultContent(driver);
		
	}
	return flag;
}

public String getTagElementForGivenXpath(String xpath){
	WebElement ele=FindElement(driver, xpath, "label xpath", action.SCROLLANDBOOLEAN,30);
	String tagName = ele.getTagName();
	return tagName;
}

public boolean enteringValuesOnTheBasisOfTag(String xpath,String label,String value,String tag){
	WebElement ele ;
	ele = FindElement(driver, xpath, xpath, action.SCROLLANDBOOLEAN, 10);
	ThreadSleep(1000);
	if(tag.equalsIgnoreCase(HTMLTAG.select.toString())){
	
		if (selectVisibleTextFromDropDown(driver, ele,label+" Drop Down List", value)) {
			appLog.info("Selected value from "+ label +" Drop down List : " + value);
			ThreadSleep(2000);
			return true;
		}else{
			BaseLib.sa.assertTrue(false, "Not Able to Select value from "+ label +" Drop down List : " + value);
			appLog.error("Not Able to Select value from "+ label +" Drop down List : " + value);	
		}
		
	}else if(tag.equalsIgnoreCase(HTMLTAG.input.toString())) {
	
		if (sendKeys(driver, ele, value,label+" : " + value, action.BOOLEAN)) {
			appLog.info("Entered Value on "+label+" Text Box : " + value);
			ThreadSleep(2000);
			return true;
		}else{
			BaseLib.sa.assertTrue(false, "Not Able to entered value on "+label+" Text Box : " + value);
			appLog.error("Not Able to entered value on "+label+" Text Box : " + value);
		}
		
	}else{
		appLog.info("Tag Does not Exist on HTMLTag Enum : "+tag);	
	}
		
	return false;
	
}

public boolean verifyValuesOnTheBasisOfTag(String xpath,String label,String value,String tag){
	WebElement ele ;
	ele = FindElement(driver, xpath, xpath, action.SCROLLANDBOOLEAN, 10);
	ThreadSleep(1000);
	String s=null;
	if(tag.equalsIgnoreCase(HTMLTAG.select.toString())){
		
		s= getSelectedOptionOfDropDown(driver, ele, "drop down "+label, "text");
	
	}else if(tag.equalsIgnoreCase(HTMLTAG.input.toString())) {
		s=ele.getAttribute("value").trim();
		
	}else{
		appLog.info("Tag Does not Exist on HTMLTag Enum : "+tag);
		return false;
	}
	if (value.equalsIgnoreCase(s)) {
		appLog.info("value from "+ label +" is : " + value+" is matched");
		return true;
	}else{
		BaseLib.sa.assertTrue(false, "value from "+ label +" is : " + value+" is not matched. Expected: "+value+"\t Actual: "+s);
		appLog.error("value from "+ label +" is : " + value+" is not matched. Expected: "+value+"\t Actual: "+s);	
	}
	return false;
	
}

public boolean clickOnCreateDealButtonAndVerifyingLandingPage(String environment,String mode,String companyName){
	boolean flag = false;
	String monthAndYear = getSystemDate("MMM") + " " + getSystemDate("yyyy");
	String expectedPipeLineName = companyName + " " + "-" + " " + monthAndYear;

	if (click(driver, getCreateDealBtn(environment, mode, 10), "Create Deal Button", action.BOOLEAN)) {
		appLog.info("Clicked on Create Deal Button");	
		
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToDefaultContent(driver);
		}
		
		ThreadSleep(3000);
		flag = true;
		if (getPipelineNameInViewMode(environment, mode, 60) != null) {
			String pipeLineNameViewMode = getText(driver, getPipelineNameInViewMode(environment, mode, 60),
					"PipeLine Name", action.BOOLEAN);
			if (expectedPipeLineName.equalsIgnoreCase(pipeLineNameViewMode)) {
				appLog.info("PipeLine created successfully.:" + pipeLineNameViewMode);
		
			} else {
				BaseLib.sa.assertTrue(false, "PipeLine Created But not Not Verified - Actual : " + pipeLineNameViewMode+"  Expected  : "+expectedPipeLineName);
				appLog.error("PipeLine Created But not Not Verified - Actual : " + pipeLineNameViewMode+"  Expected  : "+expectedPipeLineName);
			}
		} else {
			BaseLib.sa.assertTrue(false, "Not able to find PipeLine Name in View Mode");
			appLog.error("Not able to find PipeLine Name in View Mode");
		}
		
		
	} else {
		BaseLib.sa.assertTrue(false, "Not Able to Click on Create Deal Button");
		appLog.error("Not Able to Click on Create Deal Button");
	}
	
	
	return flag;
	
}


public boolean createNewDealPipeLine(String environment, String mode, String companyName, String stage, String source) {
	
	boolean flag = true;
	
	
		switchToFrame(driver, 600, new NavatarSetupPageBusinessLayer(driver).getnavatarSetUpTabFrame_Lighting(environment, 120));
	
	
	WebElement ele = FindElement(driver, "//div[@class='ContentStart']//p[text()='Deal Creation']",
			"Deal Creation Page", action.SCROLLANDBOOLEAN, 60);
	if (ele != null) {
		appLog.info("Deal Creation Page is open");

		if (sendKeys(driver, getCompanyNameTextBox(environment, mode, 10), companyName,
				"Company Name : " + companyName, action.BOOLEAN)) {
			appLog.info("Entered Value on Company Name Text Box : " + companyName);
			ThreadSleep(1000);
			
			if (selectVisibleTextFromDropDown(driver, getStageDropDownList(environment, mode, 10),
					"Stage Drop Down List", stage)) {
				appLog.info("Selected value from Stage Deop down List : " + stage);
				
				
			} else {
				flag=false;
				BaseLib.sa.assertTrue(false, "Not Able to Select value from Stage Deop down List : " + stage);
				appLog.error("Not Able to Select value from Stage Deop down List : " + stage);
			}
			
			ThreadSleep(1000);
			String actualPipeLineName = getValueFromElementUsingJavaScript(driver,getDealNameTextBox(environment, mode, 10), "PipeLine Value");
			String monthAndYear = getSystemDate("MMM") + " " + getSystemDate("yyyy");
			String expectedPipeLineName = companyName + " " + "-" + " " + monthAndYear;

			if ((expectedPipeLineName).equals(actualPipeLineName)) {
				appLog.info("PipeLine Name Value Verified : " + actualPipeLineName);
			} else {
				flag=false;
				BaseLib.sa.assertTrue(false, "PipeLine Name Value Not Verified - Actual : " + actualPipeLineName
						+ "     Expected : " + expectedPipeLineName);
				appLog.error("PipeLine Name Value Not Verified - Actual : " + actualPipeLineName + "     Expected : "
						+ expectedPipeLineName);
			}
			

		} else {
			flag=false;
			BaseLib.sa.assertTrue(false, "Not Able to entered value on Company Name Text Box : " + companyName);
			appLog.error("Not Able to entered value on Company Name Text Box : " + companyName);
		}

	} else {
		flag=false;
		BaseLib.sa.assertTrue(false, "Deal Creation Page is not open");
		appLog.error("Deal Creation Page is not open");
	}
	
	return flag;

}


public boolean createIndiviualInvestor(String environment, String mode,String[][] labelNamesAndValue, String clickOnCopyAddress, TopOrBottom topOrBottom) {
	boolean flag = false;
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	
		
			switchToFrame(driver, 10,getCreateCommitmentFrame_Lighting(20));
		
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
	
	
		switchToDefaultContent(driver);
	
	return flag;
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
			
			ele=FindElement(driver, xpath, label+" text box", action.SCROLLANDBOOLEAN, 120);
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

public boolean verifyAddedTabsInHomepage(String tabs,String mode){
	List<String> result;
	List<WebElement> allTabs=getAllAddedTabList(mode);

	ThreadSleep(2000);
	if(!allTabs.isEmpty()){
		ThreadSleep(3000);
		
		//result=compareMultipleList(driver, tabs, allTabs);
		result=compareMultipleListOnBasisOfTitle(driver, tabs, allTabs);

		if(result.isEmpty()){
	
			log(LogStatus.PASS, "all homepage added tab successfully verified", YesNo.No);
			return true;
		}else{
	
			log(LogStatus.FAIL,"all homepage added tab is not verified rsult:"+result, YesNo.No);
			return false;
		}
		
	}else{
		log(LogStatus.FAIL, "Not abel to get the list of added tabs size:"+allTabs.size(), YesNo.No);
		return false;
	}
	
}

	public boolean verifyHomepageAppLogo(String AppName){
	
	if(getAppLogoHeaer(30).isDisplayed()){
		log(LogStatus.FAIL, "App logo is  visible on homepae", YesNo.No);
		ThreadSleep(2000);
		String text=getAppLogoHeaer(30).getText().trim();
		if(text.equalsIgnoreCase(AppName.trim())){
			log(LogStatus.PASS, "App logo is successfuly verified on homepage", YesNo.No);
			return true;
		}else{
			log(LogStatus.FAIL, "App logo is not verifid on homepae", YesNo.No);
			return false;
		}
		
	}else{
		log(LogStatus.FAIL, "App logo is not displayed on homepae", YesNo.No);
		return false;
		
	}
	
}

	public boolean verifyTodaayTaskandTodayEventSectionVisibleOnHomepage(int timeOut){
		
		boolean status =getTodayTaskSection(timeOut).isDisplayed();
		boolean status1 =getTodayEventSection(timeOut).isDisplayed();
		
		if(status && status1){
			
			log(LogStatus.PASS, "Today task and  today event section is visibile on homepage", YesNo.No);
			return true;
		}else{
			log(LogStatus.FAIL, "Today task and today event section is not visibile on homepage", YesNo.Yes);
			return false;
		}
	}
	
	
	
	
	/**
	 * @author Ankur Huria
	 * @param Title
	 * @param fieldsInComponent
	 */

	public boolean verifyColumnsOfSDG(String Title, List<String> fieldsInComponent) {
		boolean flag = false;
		WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + Title + "']",
				"Component Title ", action.SCROLLANDBOOLEAN, 10);
		if (alreadyAddedComponentToHomePage != null) {

			log(LogStatus.INFO, "Component Title Matched to Home Page " + Title, YesNo.Yes);

			if(!verifySDGExpandByDefault(Title)) {
				log(LogStatus.INFO, "Not Expanded By Default SDG: " + Title, YesNo.No);
				log(LogStatus.INFO, "Now Expanding  SDG: " + Title, YesNo.No);
				
				WebElement TooltipElement = FindElement(driver,
						"//a[text()='" + Title + "']/ancestor::article/preceding-sibling::lightning-icon", "Tooltip",
						action.SCROLLANDBOOLEAN, 20);
				if (click(driver, TooltipElement, "Collapse/Expand Element", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on Collapse/Expand");
				flag=true;
				}
			 else {
					log(LogStatus.ERROR, "Not Able to click on Expand Button of SDG :" + Title, YesNo.No);

				}
			
				
			}	
			
		 else {
			log(LogStatus.INFO, "Expanded By Default SDG :" + Title, YesNo.No);
			flag=true;

		}
				if(flag) {
			List<WebElement> columns = FindElements(driver,
					"//a[text()='" + Title + "']/ancestor::article//thead//th[contains(@class,'navpeI')]//span",
					"Records");
			List<String> columnsText = new ArrayList<String>();
			for (WebElement column : columns) {
				columnsText.add(column.getText());
			}
			System.out.println(columnsText);
			if (CommonLib.compareList(columnsText, fieldsInComponent)) {
				log(LogStatus.INFO, "All Fields are Matched ", YesNo.No);
				flag = true;

			} else {
				log(LogStatus.ERROR, "All Fields are not Matched", YesNo.No);

			}
		

		} else {
			log(LogStatus.ERROR, "Component Title Not Matched to Home Page :" + Title, YesNo.No);

		}
		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 */


	public boolean verifySDGExpandByDefault(String Title) {
		boolean flag = false;
		WebElement expandElement = FindElement(driver,
				"//a[text()='" + Title + "']/ancestor::article//div[@class='slds-hide']/following-sibling::div",
				"Expand Element of SDG: " + Title, action.SCROLLANDBOOLEAN, 10);
		if (expandElement != null) {

			log(LogStatus.INFO, "Expand Element Found of SDG: " + Title, YesNo.Yes);

			String display = CommonLib.getAttribute(driver, expandElement, "Expand Element of SDG: " + Title, "style");

			if (display.contains("block")) {
				log(LogStatus.INFO, "-------------SDG of Title:  " + Title + " is Expanded------------", YesNo.No);
				flag = true;

			} else {
				log(LogStatus.ERROR, "-------------SDG of Title:  " + Title + " is not Expanded------------", YesNo.No);

			}

		} else {
			log(LogStatus.ERROR, "Expand Element Not Found of SDG:  " + Title, YesNo.No);

		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 * @param rowNumber
	 */
	public boolean verifySDGTooltipForARecord(String Title, int rowNumber) {
		boolean flag = false;
		List<WebElement> TooltipElements = FindElements(driver, "//a[text()='" + Title
				+ "']/ancestor::article//tbody/tr[" + rowNumber + "]/td//lightning-formatted-url", "Tooltip");
		List<WebElement> TooltipAnchorElements = FindElements(driver, "//a[text()='" + Title
				+ "']/ancestor::article//tbody/tr[" + rowNumber + "]/td//lightning-formatted-url//a", "Tooltip Anchor");

		if (TooltipElements.size() != 0) {

			log(LogStatus.INFO, "1st Tooltip Element Found of SDG: " + Title, YesNo.Yes);
			int i = 0;
			for (WebElement ele : TooltipElements) {
				if (CommonLib.getAttribute(driver, ele, "", "title").equals(TooltipAnchorElements.get(i).getText())) {
					appLog.info("Toototip Verified : " + getAttribute(driver, ele, "", "title"));
					log(LogStatus.INFO, "Toototip Verified : " + getAttribute(driver, ele, "", "title"), YesNo.No);
					flag = true;
				} else {
					appLog.error("Toototip Not Verified : " + getAttribute(driver, ele, "", "title"));
					log(LogStatus.ERROR, "Toototip Not Verified : " + getAttribute(driver, ele, "", "title"), YesNo.No);
				}
				i++;
			}

		} else {
			appLog.error("1st type of Tooltip Elements Not Found of SDG: " + Title);
			log(LogStatus.ERROR, "1st type of Tooltip Elements Not Found of SDG: " + Title, YesNo.No);

		}

		List<WebElement> TooltipElements2 = FindElements(driver, "//a[text()='" + Title
				+ "']/ancestor::article//tbody/tr[" + rowNumber + "]/td//lightning-formatted-text", "Tooltip");

		if (TooltipElements2.size() != 0) {
			flag = false;
			log(LogStatus.INFO, "1st Tooltip Element Found of SDG: " + Title, YesNo.Yes);
			int i = 0;
			for (WebElement ele : TooltipElements2) {
				if (CommonLib.getAttribute(driver, ele, "", "title").equals(ele.getText())) {
					appLog.info("Toototip Verified : " + getAttribute(driver, ele, "", "title"));
					log(LogStatus.INFO, "Toototip Verified : " + getAttribute(driver, ele, "", "title"), YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Toototip Not Verified : " + getAttribute(driver, ele, "", "title"), YesNo.No);
					appLog.error("Toototip Not Verified : " + getAttribute(driver, ele, "", "title"));
				}
				i++;
			}

		} else {
			appLog.error("2nd type of Tooltip Elements Not Found of SDG: " + Title);
			log(LogStatus.ERROR, "2nd type of Tooltip Elements Not Found of SDG: " + Title, YesNo.No);

		}

		List<WebElement> TooltipElements3 = FindElements(driver,
				"//a[text()='" + Title + "']/ancestor::article//tbody/tr[" + rowNumber + "]/td//a[text()='0']",
				"Tooltip");

		if (TooltipElements3.size() != 0) {
			flag = false;
			log(LogStatus.INFO, "1st Tooltip Element Found of SDG: " + Title, YesNo.Yes);

			for (WebElement ele : TooltipElements3) {
				if (CommonLib.getAttribute(driver, ele, "", "title").equals(ele.getText())) {
					appLog.info("Toototip Verified : " + getAttribute(driver, ele, "", "title"));
					log(LogStatus.INFO, "Toototip Verified : " + getAttribute(driver, ele, "", "title"), YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Toototip Not Verified : " + getAttribute(driver, ele, "", "title"), YesNo.No);
					appLog.error("Toototip Not Verified : " + getAttribute(driver, ele, "", "title"));
				}

			}

		} else {
			appLog.error("3rd type of Tooltip Elements Not Found of SDG: " + Title);
			log(LogStatus.ERROR, "3rd type of Tooltip Elements Not Found of SDG: " + Title, YesNo.No);

		}
		return flag;

	}

	
	/**
	 * @author Ankur Huria
	 * @param Title
	 */
	public boolean verifySDGTooltipForExpandAndCollapse(String Title) {
		boolean flag = false;
		

		if (TooltipElement(Title) != null) {

			log(LogStatus.INFO, "Collapse/Expand Tooltip Element Found of SDG: " + Title, YesNo.Yes);
			if (CommonLib.getAttribute(driver, TooltipElement(Title), "", "title").equalsIgnoreCase("Collapse")) {
				appLog.info("Toototip Verified : " + getAttribute(driver, TooltipElement(Title), "", "title"));
				log(LogStatus.INFO, "Toototip Verified : " + getAttribute(driver, TooltipElement(Title), "", "title"), YesNo.No);
				if (click(driver, TooltipElement(Title), "Collapse/Expand Element", action.SCROLLANDBOOLEAN)) 
					appLog.info("clicked on Collapse/Expand");
				WebElement expandElement = FindElement(driver,
						"//a[text()='" + Title + "']/ancestor::article//div[@class='slds-hide']/following-sibling::div",
						"Expand Element of SDG: " + Title, action.SCROLLANDBOOLEAN, 10);
				if(expandElement!=null) {
				String display = CommonLib.getAttribute(driver, expandElement, "Expand Element of SDG: " + Title, "style");

				if (display.contains("none")) {
				
					appLog.info("----SDG gets Collapsed----");
				log(LogStatus.INFO, "----SDG gets Collapsed-----", YesNo.No);
					flag = true;
				}
				else 
				{
					appLog.error("----SDG not gets Collapsed----");
					log(LogStatus.ERROR, "----SDG not gets Collapsed-----", YesNo.No);
					
				}
			}} else if (CommonLib.getAttribute(driver, TooltipElement(Title), "", "title").equalsIgnoreCase("Expand")) {
				flag = false;
				if (click(driver, TooltipElement(Title), "Collapse/Expand Element", action.SCROLLANDBOOLEAN)) 
					appLog.info("clicked on Collapse/Expand");
				WebElement expandElement = FindElement(driver,
						"//a[text()='" + Title + "']/ancestor::article//div[@class='slds-hide']/following-sibling::div",
						"Expand Element of SDG: " + Title, action.SCROLLANDBOOLEAN, 10);
				if(expandElement!=null) {
				String display = CommonLib.getAttribute(driver, expandElement, "Expand Element of SDG: " + Title, "style");

				if (display.contains("block")) {
				
					appLog.info("----SDG gets Expanded----");
				log(LogStatus.INFO, "----SDG gets Expanded-----", YesNo.No);
					flag = true;
				}
				else 
				{
					appLog.error("----SDG not gets Expanded----");
					log(LogStatus.ERROR, "----SDG not gets Expanded-----", YesNo.No);
					
				}
				}} else {
				flag = false;
				if (click(driver, TooltipElement(Title), "Collapse/Expand Element", action.SCROLLANDBOOLEAN)) 
					appLog.info("clicked on Collapse/Expand");
				WebElement expandElement = FindElement(driver,
						"//a[text()='" + Title + "']/ancestor::article//div[@class='slds-hide']/following-sibling::div",
						"Expand Element of SDG: " + Title, action.SCROLLANDBOOLEAN, 10);
				if(expandElement!=null) {
				String display = CommonLib.getAttribute(driver, expandElement, "Expand Element of SDG: " + Title, "style");

				if (display.contains("none")) {
				
					appLog.info("----SDG gets Collapsed----");
				log(LogStatus.INFO, "----SDG gets Collapsed-----", YesNo.No);
					flag = true;
				}
				else 
				{
					appLog.error("----SDG not gets Collapsed----");
					log(LogStatus.ERROR, "----SDG not gets Collapsed-----", YesNo.No);
					
				}
				
				}}

		}

		else {
			appLog.error("Collapse/Expand Tooltip Element Not Found of SDG: " + Title);
			log(LogStatus.ERROR, "Collapse/Expand Tooltip Element Not Found of SDG: " + Title, YesNo.No);

		}
		return flag;
	}
	
	/**
	 * @author Ankur Huria
	 * @param Title
	 */
	public boolean verifyCollapseTooltipAFterGoingToInstitutionPageAndComingBack(String Title) 
	{
boolean flag = false;
		

		
		if (TooltipElement(Title) != null) {

			log(LogStatus.INFO, "Collapse/Expand Tooltip Element Found of SDG: " + Title, YesNo.Yes);
			if (CommonLib.getAttribute(driver, TooltipElement(Title), "", "title").equalsIgnoreCase("Collapse")) {
				appLog.info("Toototip Verified : " + getAttribute(driver, TooltipElement(Title), "", "title"));
				log(LogStatus.INFO, "Toototip Verified : " + getAttribute(driver, TooltipElement(Title), "", "title"), YesNo.No);
				flag=true;
	}
			else 
			{
				log(LogStatus.ERROR, "Toototip is Not Collapsed ", YesNo.No);
				appLog.error("Toototip is Not Collapsed : ");
			}
			}
		
		else {
			appLog.error("Collapse/Expand Tooltip Element Not Found of SDG: " + Title);
			log(LogStatus.ERROR, "Collapse/Expand Tooltip Element Not Found of SDG: " + Title, YesNo.No);

		}
		return flag;
		
		
		}
	
	
	/**
	 * @author Ankur Huria
	 * @param Title
	 */
	public boolean verifyGearIconPresentAndVerifyTooltip(String Title) 
	{
boolean flag = false;
		

		
		if (gearIcon(Title) != null) {

			log(LogStatus.INFO, "Gear Icon Element Found of SDG: " + Title, YesNo.Yes);
			if (CommonLib.getAttribute(driver, gearIcon(Title), "", "title").equalsIgnoreCase("Open SDG record.")) {
				appLog.info("Toototip Verified : " + getAttribute(driver, gearIcon(Title), "", "title"));
				log(LogStatus.INFO, "Toototip Verified : " + getAttribute(driver, gearIcon(Title), "", "title"), YesNo.No);
				flag=true;
	}
			else 
			{
				log(LogStatus.ERROR, "Toototip is Not Verified "+getAttribute(driver, gearIcon(Title), "", "title"), YesNo.No);
				appLog.error("Toototip is Not Verified : "+ getAttribute(driver, gearIcon(Title), "", "title"));
			}
			}
		
		else {
			appLog.error("Gear Icon Element Not Found of SDG: " + Title);
			log(LogStatus.ERROR, "Gear Icon Element Not Found of SDG: " + Title, YesNo.No);

		}
		return flag;
		
		
		}
	
	/**
	 * @author Ankur Huria
	 * @param SDGGridName
	 */
	
	public void verifyColumnAscendingDescendingOrder(SDGGridName sdgGridName, List<String> columnNames) 
	{
		
		List<WebElement> headerList = sdgGridAllHeadersLabelNameList(sdgGridName);
		List<String> columnDataText = headerList.stream().map(s -> s.getText())
				.collect(Collectors.toList()).stream().map(t -> t.trim()).collect(Collectors.toList());
		if(!headerList.isEmpty()) {
			int i=0;
			for(String columnName : columnNames) {
				int columnIndex = columnDataText.indexOf(columnName);
				if(i==0) {
					if(CommonLib.checkSorting(driver, SortOrder.Decending, sdgGridColumnsDataList(sdgGridName.toString(),columnIndex+1))){
						log(LogStatus.PASS, SortOrder.Decending+ "Check Sorting on "+columnName+" Columns ", YesNo.No);
					}else {
						log(LogStatus.FAIL, "Not Checked "+SortOrder.Decending+"Sorting on "+columnName+" Columns ", YesNo.No);
						sa.assertTrue(false, "Not Checked "+SortOrder.Decending+"Sorting on "+columnName+" Columns ");
					}
				}else {
					if(clickUsingJavaScript(driver, headerList.get(columnIndex), sdgGridName.toString()+" SDG Grid header column "+columnName, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "Clicked on Header"+columnName+" Clomun "+(columnIndex+1)+" for "+SortOrder.Assecending, YesNo.No);
						ThreadSleep(35000);
						if(CommonLib.checkSorting(driver, SortOrder.Assecending, sdgGridColumnsDataList(sdgGridName.toString(),columnIndex+1))){
							log(LogStatus.PASS, SortOrder.Assecending+" Check Sorting on "+sdgGridName.toString()+" Columns "+columnName, YesNo.No);
						}else {
							log(LogStatus.FAIL, SortOrder.Assecending+" Not Checked Sorting on "+sdgGridName.toString()+" Columns "+columnName, YesNo.No);
							sa.assertTrue(false, SortOrder.Assecending+" Not Checked Sorting on "+sdgGridName.toString()+" Columns "+columnName);
						}
					}else {
						log(LogStatus.PASS, "Not able to click on "+sdgGridName.toString()+" SDG Grid header "+columnName+" so cannot check Sorting "+SortOrder.Assecending, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on "+sdgGridName.toString()+" SDG Grid header "+columnName+" so cannot check Sorting "+SortOrder.Assecending);
					}
					headerList=sdgGridAllHeadersLabelNameList(sdgGridName);
				}
				if(i==0) {
					if(clickUsingJavaScript(driver, headerList.get(columnIndex), sdgGridName.toString()+" SDG Grid header column "+columnName, action.SCROLLANDBOOLEAN)) {
						ThreadSleep(35000);
						log(LogStatus.PASS, "Clicked on Header"+columnName+" Clomun "+(i+1)+SortOrder.Assecending, YesNo.No);
						if(CommonLib.checkSorting(driver, SortOrder.Assecending, sdgGridColumnsDataList(sdgGridName.toString(),columnIndex+1))){
							log(LogStatus.PASS, SortOrder.Assecending+" Check Sorting on "+columnName+" Column on "+sdgGridName.toString()+" SDG Grid", YesNo.No);
						}else {
							log(LogStatus.FAIL, "Not Checked "+SortOrder.Assecending+" Sorting on "+sdgGridName.toString()+" Columns "+columnName, YesNo.No);
							sa.assertTrue(false, "Not Checked "+SortOrder.Assecending+" Sorting on "+sdgGridName.toString()+" Columns "+columnName);
						}
					}else { 
						log(LogStatus.PASS, "Not able to click on "+sdgGridName.toString()+" SDG Grid header "+columnName+" so cannot check Sorting "+SortOrder.Decending, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on "+sdgGridName.toString()+" SDG Grid header "+columnName+" so cannot check Sorting "+SortOrder.Decending);
					}
				}else {
					if(clickUsingJavaScript(driver, headerList.get(columnIndex), sdgGridName.toString()+" SDG Grid header column", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(35000);
						log(LogStatus.PASS, "Clicked on Header "+columnName+" Clomun "+(columnIndex+1)+SortOrder.Decending, YesNo.No);
						if(CommonLib.checkSorting(driver, SortOrder.Decending, sdgGridColumnsDataList(sdgGridName.toString(),columnIndex+1))){
							log(LogStatus.PASS, SortOrder.Decending+" Check Sorting on "+columnName+" Columns on SDG Grid "+sdgGridName.toString(), YesNo.No);
						}else {
							log(LogStatus.FAIL, "Not Checked "+SortOrder.Decending+" Sorting on "+sdgGridName.toString()+" Columns "+columnName, YesNo.No);
							sa.assertTrue(false, "Not Checked "+SortOrder.Decending+" Sorting on "+sdgGridName.toString()+" Columns "+columnName);
						}
					}else { 
						log(LogStatus.PASS, "Not able to click on "+sdgGridName.toString()+" SDG Grid header "+columnName+" so cannot check Sorting "+SortOrder.Assecending, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on "+sdgGridName.toString()+" SDG Grid header "+columnName+" so cannot check Sorting "+SortOrder.Assecending);
					}
				}
				headerList=sdgGridAllHeadersLabelNameList(sdgGridName);
				i++;
			}
		}else {
			log(LogStatus.PASS, sdgGridName.toString()+" SDG Grid header cloumns list is not visible so cannot check Sorting ", YesNo.Yes);
			sa.assertTrue(false, sdgGridName.toString()+" SDG Grid header cloumns list is not visible so cannot check Sorting ");
		}
	}
	
	/**
	 * @author Ankur Huria
	 * @param Title
	 */
	public boolean sdgGridExpandedByDefaultIfNotThenExpand(String Title) 
	{
		boolean flag = false;
		WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + Title + "']",
				"Component Title ", action.SCROLLANDBOOLEAN, 10);
		if (alreadyAddedComponentToHomePage != null) {

			log(LogStatus.INFO, "Component Title Matched to Home Page " + Title, YesNo.Yes);

			if(!verifySDGExpandByDefault(Title)) {
				log(LogStatus.INFO, "Not Expanded By Default SDG: " + Title, YesNo.No);
				log(LogStatus.INFO, "Now Expanding  SDG: " + Title, YesNo.No);
				
				WebElement TooltipElement = FindElement(driver,
						"//a[text()='" + Title + "']/ancestor::article/preceding-sibling::lightning-icon", "Tooltip",
						action.SCROLLANDBOOLEAN, 20);
				if (click(driver, TooltipElement, "Collapse/Expand Element", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on Collapse/Expand");
				flag=true;
				}
			 else {
					log(LogStatus.ERROR, "Not Able to click on Expand Button of SDG :" + Title, YesNo.No);

				}			
			}	
			
		 else {
			log(LogStatus.INFO, "Expanded By Default SDG :" + Title, YesNo.No);
			flag=true;

		}
	}
		else {
			log(LogStatus.ERROR, "Component Title Not Matched to Home Page :" + Title, YesNo.No);

		}
				
		return flag;
	}
	
	
	/**
	 * @author Ankur Huria
	 * @param Title
	 * @param PageSize
	 */
	
	public boolean pageSizeSelect(String Title, String pageSize)
	{
		boolean flag = false;
		WebElement pageSizeSelect = FindElement(driver,
				"//a[text()='" + Title
						+ "']/ancestor::article//span[text()='Page Size']/../parent::div//select",
				"Page Size Select ", action.SCROLLANDBOOLEAN, 10);
		if (CommonLib.selectVisibleTextFromDropDown(driver, pageSizeSelect, "Page Size Select", pageSize)) {
			log(LogStatus.INFO, "Selected the Page Size", YesNo.No);
			CommonLib.ThreadSleep(30000);
			flag=true;
		}
		 else {
				log(LogStatus.ERROR, "Not Able To Select Page Size ", YesNo.No);
				return flag;

			}
		
		return flag;
	}
	
	
	/**
	 * @author Ankur Huria
	 * @param Title
	 * @param NoOfRecordsLessThanEqualHundred
	 */
	public boolean numberOfRecordsMatch(String Title, int NoOfRecordsLessThanEqualHundred) 
	{
		boolean flag = false;
		List<WebElement> records = FindElements(driver,
				"//a[text()='" + Title + "']/ancestor::article//tbody/tr", "Records");
		System.out.println("No. of Records Present: " + records.size());
		if (records.size() == NoOfRecordsLessThanEqualHundred) {
			log(LogStatus.INFO, "No. of Records Matched: " + NoOfRecordsLessThanEqualHundred, YesNo.No);
			flag=true;

		} else {
			log(LogStatus.ERROR, "No. of Records not Matched: " + NoOfRecordsLessThanEqualHundred,
					YesNo.No);

			sa.assertTrue(false, "-----------No. of Records not Matched-----> "
					+ "Actual: "+records.size() + "but Expected: "+NoOfRecordsLessThanEqualHundred+"--------------");

		}
		return flag;
	}


}
