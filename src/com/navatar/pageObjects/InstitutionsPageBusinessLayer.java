package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.CommonVariables;
import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.navatar.generic.AppListeners.*;

/**
 * @author Akul Bhutani
 *
 */
public class InstitutionsPageBusinessLayer extends InstitutionsPage {

	public InstitutionsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	///////////////////////////////////////////////////////  Activity Association ///////////////////////////////////////////////////////////////////////////


	/**@author Azhar Alam
	 * @param projectName
	 * @param mode TODO
	 * @param institutionName
	 * @param recordType
	 * @param entityType TODO
	 * @param labelsWithValues
	 * @param timeOut
	 * @return true/false
	 * @description this method is used to create single entity if pe and account if mna
	 */
	public boolean createEntityOrAccount(String projectName,String mode,String institutionName, String recordType,String entityType, String[][] labelsWithValues, int timeOut) {
		boolean flag=false;
		refresh(driver);
		ThreadSleep(3000);
		ThreadSleep(10000);
		if(clickUsingJavaScript(driver, getNewButton(projectName, timeOut), "new button")) {
			appLog.info("clicked on new button");

			if (!recordType.equals("") || !recordType.isEmpty()) {
				ThreadSleep(2000);
				if(click(driver, getRadioButtonforRecordType(recordType, timeOut), "Radio Button for : "+recordType, action.SCROLLANDBOOLEAN)){
					appLog.info("Clicked on radio Button for institution for record type : "+recordType);
					if (click(driver, getContinueOrNextButton(projectName,timeOut), "Continue Button", action.BOOLEAN)) {
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




			if (sendKeys(driver, getLegalName(projectName,timeOut), institutionName, "leagl name text box",action.SCROLLANDBOOLEAN)) {
				appLog.info("passed data in text box: " + institutionName);

				if (entityType!=null) {
					WebElement ele;
					ThreadSleep(2000);
					if(click(driver, getEntityTypeDropdown(mode, timeOut), "entity dropdown", action.SCROLLANDBOOLEAN)){
						appLog.info("click on entity dropdown");

						String xpath = "//*[text()='Entity Type']/..//input/../..//span[text()='"+entityType+"'][@title='"+entityType+"']/../..";
						ele=FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, timeOut);
						ThreadSleep(2000);
						if(clickUsingJavaScript(driver, ele, entityType+" entity type")){
							appLog.info("click on entity dropdown value:"+entityType);

						}else{

							appLog.error("Not Able to click on entity dropdown value:"+entityType);
							return false;
						}


					}else{
						appLog.error("Not Able to click on entity dropdown");
						return false;
					}

				}
				FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
				if (labelsWithValues!=null) {
					for (String[] strings : labelsWithValues) {
						if (click(driver, fp.getDealStatus(projectName, timeOut), "Status : "+strings[1], action.SCROLLANDBOOLEAN)) {
							ThreadSleep(2000);
							appLog.error("Clicked on Deal Status");

							String xpath="//span[@title='"+strings[1]+"']";
							WebElement dealStatusEle = FindElement(driver,xpath, strings[1],action.SCROLLANDBOOLEAN, timeOut);
							ThreadSleep(2000);
							if (click(driver, dealStatusEle, strings[1], action.SCROLLANDBOOLEAN)) {
								appLog.info("Selected Status : "+strings[1]);
								ThreadSleep(2000);
							} else {
								appLog.error("Not able to Select on Status : "+strings[1]);
								flag=false;
							}
						} else {
							appLog.error("Not able to Click on Status : ");
							flag=false;
						}
					}
				}
				if (click(driver, getNavigationTabSaveBtn(projectName,timeOut), "save button", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on save button");

					String str = getText(driver, verifyCreatedItemOnPage(Header.Company, institutionName), "legal Name Label Text",action.SCROLLANDBOOLEAN);
					if (str != null) {
						if (str.contains(institutionName)) {
							appLog.info("created institution " + institutionName + " is verified successfully.");
							appLog.info(institutionName + " is created successfully.");
							flag=true;
						} else {
							appLog.error("Created  " + institutionName + " is not matched with " + str);
						}
					} else {
						appLog.error("Created  " + institutionName + " is not visible");
					}
				} else {
					appLog.error("Not able to click on save button so cannot create : "+ institutionName);
				}
			} else {
				appLog.error("Not able to pass data in legal name text box so cannot create : "+ institutionName);
			}

		}else {
			appLog.error("Not able to click on New Button so cannot create institution: " + institutionName);

		}



		return flag;
	}
	public boolean clickOnCreatedInstitution(String environment,String mode,String inst_name) {

		if(mode.equalsIgnoreCase(Mode.Classic.toString())){

			List<WebElement> optionsInDropDown = FindElements(driver, "//select[@id='fcf']/option[text()='All Institutions']", "");
			String[] options = {};
			if(optionsInDropDown.size()>1){
				String[] o = {optionsInDropDown.get(0).getAttribute("value"), optionsInDropDown.get(1).getAttribute("value")};
				options = o;
			} else {
				String[] o = {optionsInDropDown.get(0).getAttribute("value")};
				options = o;
			}

			int i =1;
			if (getSelectedOptionOfDropDown(driver, getViewDropdown(60), "View dropdown", "text")
					.equalsIgnoreCase("All Firms")) {
				if (click(driver, getGoButton(60), "Go button", action.BOOLEAN)) {

				} else {
					appLog.error("Go button not found");
				}
			} else {
				if (selectVisibleTextFromDropDown(driver, getViewDropdown(60), "View dropdown", options[0])) {
				} else {
					appLog.error("All institutions not found in dropdown");
				}

			}
			WebElement ele = isDisplayed(driver,
					FindElement(driver, "//div[@class='x-panel-bwrap']//span[text()='" + inst_name + "']/..",
							"Institution link", action.SCROLLANDBOOLEAN, 20),
					"visibility", 20, "");
			if(ele==null){
				if(options.length>1){
					if (selectVisibleTextFromDropDown(driver, getViewDropdown(60), "View dropdown", options[1])) {
						ele = isDisplayed(driver,
								FindElement(driver, "//div[@class='x-panel-bwrap']//span[text()='" + inst_name + "']/..",
										"Institution link", action.SCROLLANDBOOLEAN, 20),
								"visibility", 20, "");
					} else {
						appLog.error("All institutions not found in dropdown");
					}
				} else {
					appLog.error("All institutions not found in dropdown");
				}
			}
			if (ele != null) {
				scrollDownThroughWebelement(driver, ele, "");
				if (click(driver, ele, inst_name + " name text", action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on institution link");
					return true;
				} else {
					appLog.error("Not able to click on " + inst_name);
				}
			} else {
				while (true) {
					appLog.error("Institutions is not Displaying on "+i+ " Page: " + inst_name);
					if (click(driver, getNextImageonPage(10), "Institutions Page Next Button",
							action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						appLog.info("Clicked on Next Button");
						ele = FindElement(driver, "//div[@class='x-panel-bwrap']//span[text()='" + inst_name + "']/..",
								"Institution link", action.SCROLLANDBOOLEAN, 20);
						if (ele != null) {
							if (click(driver, ele, inst_name, action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on Institutions name : " + inst_name);
								return true;

							} else {
								appLog.error("Not able to click on " + inst_name);
							}
						}
					} else {
						appLog.error("Institutions Not Available : " + inst_name);
						return false;
					}
					i++;
				}
			}
		}else{
			if(clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, inst_name, 30)){
				appLog.info("Clicked on Institutions name : " + inst_name);
				return true;
			}else{
				appLog.error("Institutions Not Available : " + inst_name);
			}	
		}
		return false;
	}
	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param commitmentRowRecord
	 * @param fundName
	 * @param totalAmount
	 * @return
	 */
	public List<String> verifyCommitmentDetails(String environment, String mode,String[][] commitmentRowRecord, String fundName, String totalAmount){
		List<String> result = new ArrayList<String>();
		String xpath="";
		WebElement ele= null;

		scrollDownThroughWebelement(driver, getFundCommitmentDetailsLabelText(environment, mode, 30), "");
		//switchToFrame(driver, 10, getCommitmentDetailsFrame(environment, mode, 30));
		List<WebElement> commitmentIDList=getCommitmentIDList(environment, mode);
		List<WebElement> commitmentAmountList=getCommitmentAmountList(environment, mode);
		List<WebElement> LPList=getLimitedPartnerList(environment, mode);
		List<WebElement> partnershipList=getPartnerShipList(environment, mode);
		List<WebElement> createdDateList=createdDateList(environment, mode);
		if(!commitmentIDList.isEmpty()) {
			for (String[] commitmentRowData : commitmentRowRecord) {
				for(int i=0; i<commitmentIDList.size(); i++) {
					String id=commitmentIDList.get(i).getText().trim();
					String amount=	commitmentAmountList.get(i).getText().trim();
					String lp = LPList.get(i).getText().trim();
					String partnership = partnershipList.get(i).getText().trim();
					String createdDate=createdDateList.get(i).getText().trim();
					String CommitAmount=convertNumberAccordingToFormatWithCurrencySymbol(commitmentRowData[1],"0,000.00");
					if(commitmentRowData[0].contains(id) && CommitAmount.contains(amount) && commitmentRowData[2].contains(lp) ) {
						/*&& createdDate.contains(commitmentRowData[5]*/
						log(LogStatus.INFO, "Commitment ID : "+commitmentRowData[0]+", Commitment Amount :"+commitmentRowData[1]+", LP Name : "+commitmentRowData[2]
								+", created Date : "+commitmentRowData[3]+" is matched ", YesNo.No);
						break;

					}else {
						if(i==commitmentIDList.size()-1) {
							log(LogStatus.ERROR, "Commitment ID : "+commitmentRowData[0]+", Commitment Amount :"+commitmentRowData[1]+", LP Name : "+commitmentRowData[2]
									+" created Date : "+commitmentRowData[3]+" is not matched ", YesNo.Yes);
							result.add("Commitment ID : "+commitmentRowData[0]+", Commitment Amount :"+commitmentRowData[1]+", LP Name : "+commitmentRowData[2]
									+",created Date : "+commitmentRowData[3]+" is not matched ");
						}
					}
				}
				if(commitmentRowData[4]!=null&& !commitmentRowData[4].isEmpty() &&!commitmentRowData[4].isBlank()) {
					ele=FindElement(driver, xpath, "company name", action.SCROLLANDBOOLEAN, 20);
					if(ele!=null) {
						String aa = ele.getText().trim();
						if(aa.contains(commitmentRowData[4])) {
							log(LogStatus.INFO, "Company name "+commitmentRowData[4]+" is matched ", YesNo.No);
						}else {
							log(LogStatus.ERROR, "Company Name "+commitmentRowData[4]+" is not matched ", YesNo.Yes);
							result.add("Company Name "+commitmentRowData[4]+" is not matched");
						}
					}else {
						log(LogStatus.ERROR, "Company Name is not visible so cannot verify it ", YesNo.No);
						result.add("Company Name is not visible so cannot verify it ");
					}
				}

			}
			if(totalAmount!=null&& !totalAmount.isEmpty()&&!totalAmount.isBlank()) {
				totalAmount=convertNumberAccordingToFormatWithCurrencySymbol(totalAmount,"0,000.00");	
				String xPath="//span[contains(@id,'grid_dealalert-cell-0-')]//a[contains(text(),'"+fundName+"')]/../../following-sibling::span[2]/span[text()='"+totalAmount+"']";
				ele = FindElement(driver, xPath, "fund name and total amount", action.SCROLLANDBOOLEAN, 20);
				if(ele!=null) {
					log(LogStatus.INFO, "fund name "+fundName+" total commitment amount "+totalAmount+" is verified ", YesNo.No);
				}else {
					log(LogStatus.ERROR, "fund name "+fundName+" total commitment amount "+totalAmount+" is not verified ", YesNo.No);
					result.add("fund name "+fundName+" total commitment amount "+totalAmount+" is not verified ");
				}
			}else if (fundName!=null) {
				xpath="//a[text()='Fund Commitments']/ancestor::article//a[text()='"+fundName+"']";
				ele = FindElement(driver, xpath, "fund name", action.SCROLLANDBOOLEAN, 20);
				if(ele!=null) {
					log(LogStatus.INFO, "fund name "+fundName+" is verified ", YesNo.No);
				}else {
					log(LogStatus.ERROR, "fund name "+fundName+" is verified ", YesNo.No);
					result.add("fund name "+fundName+" is verified ");
				}
			}
		}else {
			log(LogStatus.ERROR, "Commitment ID list is not visible on institution Page so cannot verify commitment details", YesNo.Yes);
			result.add("Commitment ID list is not visible on institution Page so cannot verify commitment details");
		}
		switchToDefaultContent(driver);
		return result;
	}


	public List<String> verifyCommitmentDetailsLP(String environment,String mode,String fundName,String cmntAmmmountinMn,String date) {
		List<String> result = new ArrayList<String>();
		log(LogStatus.INFO,"Header Verified " , YesNo.No);

		ThreadSleep(5000);
		try {
			String headerxpath = "//thead//th//*[contains(text(),'Fund')]/../..//following-sibling::th//*[contains(text(),'Commitment Amount(M)')]/../..//following-sibling::th//*[contains(text(),'Commitment Date')]"; 
			WebElement ele = FindElement(driver, headerxpath, "Header", action.BOOLEAN, 10);
			if (ele!=null) {
				log(LogStatus.INFO,"Header Verified " , YesNo.No);
			} else {
				log(LogStatus.INFO,"Header not Verified " , YesNo.No);
				BaseLib.sa.assertTrue(false,"Header not Verified ");
				result.add("Header not Verified ");

			}

			//a[text()='TestM1Fund1']/../../../..//following-sibling::td//a[text()='TestM1Institution1-LP1']/../../../..//following-sibling::td//*[text()='$20.0']/../../..//following-sibling::td
			System.err.println("Value going to verified : "+fundName+" "+cmntAmmmountinMn+" "+date);
			appLog.info("Value going to verified : "+fundName+" "+cmntAmmmountinMn+" "+date);
			System.err.println("Value going to vrified");
			String fundXpath= "//*[text()='"+fundName+"']";

			float f= Float.parseFloat(cmntAmmmountinMn)/1000000;
			System.err.println(f);
			String monney=BasePageBusinessLayer.convertNumberAccordingToFormatWithCurrencySymbol(""+f,"0.0") ;
			String cmntXpath= "/ancestor::tr//*[text()='"+monney+"']";
			String dateXpath = "/ancestor::tr//td[contains(@class,'Commitment_Date')]";
			String detailsXpath= "//following-sibling::td[2]";

			System.err.println(monney);
			System.err.println("Value going to verified : "+fundName+" "+monney+" "+date);
			appLog.info("Value going to verified : "+fundName+" "+monney+" "+date);


			String fullXpath = fundXpath+cmntXpath+dateXpath;

			ele = FindElement(driver, fullXpath, "Value xpath", action.BOOLEAN, 10);
			if (ele!=null) {
				System.err.println(ele.getText().trim());
				String dated = ele.getText().trim();
				log(LogStatus.INFO,"Date Element Found "+dated , YesNo.No);

				if (dated.equalsIgnoreCase(date)) {

				} else {
					log(LogStatus.INFO,"Date not verified Actual : "+dated+"\t Expected : "+date , YesNo.No);
					result.add("Date not verified Actual : "+dated+"\t Expected : "+date);
					BaseLib.sa.assertTrue(false,"Date not verified Actual : "+dated+"\t Expected : "+date);
				}

			} else {
				result.add("Date Element Not Found ");
				log(LogStatus.INFO,"Date Element Not Found " , YesNo.No);
				BaseLib.sa.assertTrue(false,"Date Element Not Found ");

			}


		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;	
	}

	public boolean clickOnCreatedLP(String environment,String mode,String lp_name) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			int i =1;
			if (getSelectedOptionOfDropDown(driver, getViewDropdown(60), "View dropdown", "text")
					.equalsIgnoreCase("All Limited Partners")) {
				if (click(driver, getGoButton(60), "Go button", action.BOOLEAN)) {
					appLog.info("Clicked on Go button");
				} else {
					appLog.error("Go button not found");
				}
			} else {
				if (selectVisibleTextFromDropDown(driver, getViewDropdown(60), "View dropdown", "All Limited Partners")) {
					appLog.info("Select Limited Partners in View Dropdown");

				}

			}
			WebElement ele = isDisplayed(driver,
					FindElement(driver,
							"//*[@id='ext-gen12']/div/table/tbody/tr/td[4]/div/a/span[text()='" + lp_name + "']", "LP link",
							action.SCROLLANDBOOLEAN, 10),
					"visibility", 10, "");
			if (ele != null) {
				scrollDownThroughWebelement(driver, ele, "");
				if (click(driver, ele, lp_name + " name text", action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on limited partner link");
					return true;
				} else {
					appLog.error("Not able to click on " + lp_name);
				}
			} else {
				while (true) {
					appLog.error("limited partner is not Displaying on "+i+ " Page: " + lp_name);
					if (click(driver, getNextImageonPage(10), "limited partner Page Next Button",
							action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						appLog.info("Clicked on Next Button");
						ele = FindElement(driver,
								"//*[@id='ext-gen12']/div/table/tbody/tr/td[4]/div/a/span[text()='" + lp_name + "']", "LP link",
								action.SCROLLANDBOOLEAN, 10);
						if (ele != null) {
							if (click(driver, ele, lp_name, action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on limited partner name : " + lp_name);
								return true;

							}
						}



					} else {
						appLog.error("limited partner Not Available : " + lp_name);
						return false;
					}
					i++;
				}
			}
		}else{
			if(clickOnAlreadyCreated_Lighting(environment, mode, TabName.LimitedPartner, lp_name, 30)){
				appLog.info("Clicked on limited partner name : " + lp_name);
				return true;
			}else{
				appLog.error("limited partner Not Available : " + lp_name);
			}	
		}
		return false;
	}

	public boolean clickOnCreatedCompany(String environment,String mode,String company_name) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			int i =1;
			if (getSelectedOptionOfDropDown(driver, getViewDropdown(60), "View dropdown", "text")
					.equalsIgnoreCase("All Companies")) {
				if (click(driver, getGoButton(60), "Go button", action.BOOLEAN)) {

				} else {
					appLog.error("Go button not found");
				}
			} else {
				if (selectVisibleTextFromDropDown(driver, getViewDropdown(60), "View dropdown", "All Companies")) {
				} else {
					appLog.error("All Companies not found in dropdown");
				}

			}
			WebElement ele = isDisplayed(driver,
					FindElement(driver, "//div[@class='x-panel-bwrap']//span[text()='" + company_name + "']/..",
							"Company link", action.SCROLLANDBOOLEAN, 20),
					"visibility", 20, "");
			if (ele != null) {
				scrollDownThroughWebelement(driver, ele, "");
				if (click(driver, ele, company_name + " name text", action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on company link");
					return true;
				} else {
					appLog.error("Not able to click on " + company_name);
				}
			} else {
				while (true) {
					appLog.error("Company is not Displaying on "+i+ " Page: " + company_name);
					if (click(driver, getNextImageonPage(10), "Company Page Next Button",
							action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						appLog.info("Clicked on Next Button");
						ele = FindElement(driver, "//div[@class='x-panel-bwrap']//span[text()='" + company_name + "']/..",
								"Institution link", action.SCROLLANDBOOLEAN, 20);
						if (ele != null) {
							if (click(driver, ele, company_name, action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on Company name : " + company_name);
								return true;

							}
						}



					} else {
						appLog.error("Company Not Available : " + company_name);
						return false;
					}
					i++;
				}
			}
		}else{
			if(clickOnAlreadyCreated_Lighting(environment, mode, TabName.CompaniesTab, company_name, 30)){
				appLog.info("Clicked on Company name : " + company_name);
				return true;
			}else{
				appLog.error("Company Not Available : " + company_name);
			}	
		}
		return false;
	}
	public boolean verifyDealSourcedRelatedList(String environment,String mode,RecordType RecordType,String[][] headersWithValues){
		boolean flag=true;
		List<WebElement> header = new ArrayList<WebElement>();
		List<WebElement> values = new ArrayList<WebElement>();
		try {
			if(mode.equalsIgnoreCase(Mode.Classic.toString())){

				FindElement(driver, "//div[@class='bRelatedList']//h3[text()='Deals Sourced']", "", action.SCROLLANDBOOLEAN, 10);
				header = FindElements(driver, "//h3[text()='Deals Sourced']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr[1]/*", "Header");
				values = FindElements(driver, "//h3[text()='Deals Sourced']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr[2]/*", "Values");
				int i = 1;
				for (String[] headerWithValue : headersWithValues) {
					appLog.info("From PAGE    : "+header.get(i).getText()+"  <<<<<>>>>> "+values.get(i).getText());
					appLog.info("fROM tESTcASE  : "+headerWithValue[0].replace("_", " ")+"  <<<<<>>>>> "+headerWithValue[1]);
					if(header.get(i).getText().contains(headerWithValue[0].replace("_", " ")) && values.get(i).getText().contains(headerWithValue[1])){
						appLog.info("Value matched : "+headerWithValue[1]);
					}else{
						flag=false;
						appLog.error("Value Not matched : "+headerWithValue[1]);
						BaseLib.sa.assertTrue(false, "Value Not matched : "+headerWithValue[1]);	
					}
					i++;
				}
			}else{

				ThreadSleep(2000);
				driver.navigate().refresh();
				ThreadSleep(5000);
				flag=verifyRelatedListViewAllColumnAndValue(headersWithValues);


			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
			appLog.error("Exception Occur verifyDealSourcedRelatedList");
			BaseLib.sa.assertTrue(false, "Exception Occur verifyDealSourcedRelatedList");
		}
		return flag;

	}
	public SoftAssert EnterValueForLabelonOfficeLocation(String environment, String mode,String[][] labelsWithValues) {
		SoftAssert saa = new SoftAssert();
		String finalLabelName;
		String xpath = "";
		WebElement ele = null;
		for (String[] labelWithValue : labelsWithValues) {

			if (labelWithValue[0].contains("_")) {
				finalLabelName = labelWithValue[0].replace("_", " ");
			} else {
				finalLabelName = labelWithValue[0];
			}

			if (mode.equalsIgnoreCase(Mode.Classic.toString())) {

				if (finalLabelName.contains("Street")) {
					xpath = "//form[@id='editPage']//label[text()='Street']/../following-sibling::td//textarea";
				}else if(finalLabelName.contains("Primary")){
					xpath="//form[@id='editPage']//label[text()='Primary']/../../following-sibling::td//input";				}
				else{
					xpath = "//form[@id='editPage']//label[text()='"+finalLabelName+"']/../following-sibling::td//input";	
				}

			} else {

				if (finalLabelName.contains("Street")) {
					xpath = "//*[text()='Street']//following-sibling::div/textarea";
				}else if(finalLabelName.contains("Organization Name")) {
					xpath = "//*[text()='Organization Name']//following-sibling::div";
				}else if(finalLabelName.contains("Primary"))
					xpath="//label/span[text()='Primary']/..//following-sibling::div//input";
				else{
					xpath = "//*[text()='"+finalLabelName+"']//following-sibling::div//input";
				}

			}

			ele = FindElement(driver, xpath, finalLabelName, action.BOOLEAN, 10);
			if (finalLabelName.contains("Primary")) {

				if (labelWithValue[1].toString().contains("checked")) {

					if (click(driver, ele, finalLabelName, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked for Primary Label", YesNo.No);
					}else{
						saa.assertTrue(false, "Not Able to Click for Primary Label");
						log(LogStatus.FAIL, "Not Able to Click for Primary Label", YesNo.Yes);
					}

				} 

			}else{

				if (sendKeys(driver, ele, labelWithValue[1], labelWithValue[1], action.BOOLEAN)) {
					log(LogStatus.INFO, "Enter value : "+labelWithValue[1]+" For Label : "+labelWithValue[0], YesNo.No);
				}else{
					saa.assertTrue(false, "Enter value : "+labelWithValue[1]+" For Label : "+labelWithValue[0]);
					log(LogStatus.FAIL, "Enter value : "+labelWithValue[1]+" For Label : "+labelWithValue[0], YesNo.Yes);
				}
			}

		}

		if (click(driver, getSaveOfficeLocationButton(environment, mode, 10), "Save Button", action.BOOLEAN)) {
			log(LogStatus.FAIL, "CLicked on Save Button", YesNo.Yes);
		} else {
			saa.assertTrue(false, "Not Able to CLick on Save Button");
			log(LogStatus.FAIL, "Not Able to CLick on Save Button", YesNo.Yes);
		}


		return saa;

	}
	public boolean verifyOfficeLocationRelatedListGrid(String environment, String mode, String officeLocationName,
			String street, String city, String state, String country, String primarycheckBox) {

		log(LogStatus.INFO, "officeLocationName : " + officeLocationName, YesNo.No);
		log(LogStatus.INFO, "street : " + street, YesNo.No);
		log(LogStatus.INFO, "city : " + city, YesNo.No);
		log(LogStatus.INFO, "state : " + state, YesNo.No);
		log(LogStatus.INFO, "country : " + country, YesNo.No);
		log(LogStatus.INFO, "primarycheckBox : " + primarycheckBox, YesNo.No);
		String xpath;
		for(int i=1;i<3;i++){
			if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				xpath = "//div[contains(@class,'windowViewMode-normal')]//a[text()='Office Location']/ancestor::article//table//a[text()='"
						+ officeLocationName + "']/ancestor::tr//*[text()='" + street
						+ "']/ancestor::tr//*[text()='" + city
						+ "']/ancestor::tr//*[text()='" + state
						+ "']/ancestor::tr//*[text()='" + country
						+ "']/ancestor::tr//img[contains(@class,'"+primarycheckBox+"')]";
			} else {
				xpath = "//h3[text()='Office Locations']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th/a[text()='"
						+ officeLocationName + "']/../following-sibling::td[text()='" + street
						+ "']/following-sibling::td[text()='" + city + "']/following-sibling::td[text()='" + state
						+ "']/following-sibling::td[text()='" + country + "']/following-sibling::td/img[contains(@src,'"
						+ primarycheckBox + "')]";
			}
			WebElement ele = FindElement(driver, xpath, "Grid for : " + officeLocationName, action.BOOLEAN, 10);
			if (ele != null) {
				log(LogStatus.INFO, "Grid Verified for : " + officeLocationName, YesNo.No);
				return true;
			}
		}
		log(LogStatus.INFO, "Grid Not Verified for : " + officeLocationName, YesNo.No);
		return false;
	}

	public boolean clickOnLinkForOfficeLocation(String environment, String mode, RecordType recordType,
			String officeLocationName, int timeOut) {
		WebElement ele;
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			ThreadSleep(2000);
			ele = FindElement(driver, "//div[contains(@class,'windowViewMode-normal')]//a[text()='Office Location']/ancestor::article//table//a[text()='" + officeLocationName + "']", officeLocationName,
					action.SCROLLANDBOOLEAN, timeOut);
			ThreadSleep(2000);
			if (clickUsingJavaScript(driver, ele, officeLocationName, action.SCROLLANDBOOLEAN)) {
				ThreadSleep(2000);
				return true;	

				//refresh(driver);

			}
		} else {
			ele = FindElement(driver,
					"//a[text()='" + officeLocationName + "']/../preceding-sibling::td/a[text()='Edit']",
					"Edit Link : " + officeLocationName, action.SCROLLANDBOOLEAN, timeOut);
			if (click(driver, ele, "Edit Link : "+officeLocationName, action.SCROLLANDBOOLEAN)) {
				return true;
			}
		}
		return false;
	}
	public boolean  verifyDealSourcedRelatedList(String environment,String mode,String deal,String contact,String stage) {
		boolean flag=false;
		ThreadSleep(5000);
		try {
			String headerxpath = "//thead//th//*[contains(text(),'Deal')]/../..//following-sibling::th//*[contains(text(),'Contact')]/../..//following-sibling::th//*[contains(text(),'Date Received')]/../..//following-sibling::th//*[contains(text(),'Stage')]/../..//following-sibling::th//*[contains(text(),'Deal Size(M)')]"; 
			WebElement ele = FindElement(driver, headerxpath, "Header", action.BOOLEAN, 10);
			if (ele!=null) {
				log(LogStatus.INFO,"Header Verified for Deal Source Related List" , YesNo.No);
			} else {
				log(LogStatus.INFO,"Header not Verified for Deal Source Related List" , YesNo.No);
				BaseLib.sa.assertTrue(false,"Header not Verified for Deal Source Related List");

			}
			System.err.println("Value going to verified : "+deal+" "+contact+" "+stage);
			appLog.info("Value going to verified : "+deal+" "+contact+" "+stage);
			String fundXpath="//a[text()='"+deal+"']//ancestor::td";
			String cmntXpath="//following-sibling::td//*[text()='"+contact+"']//ancestor::td";
			String dateXpath="//following-sibling::td//*[text()='"+stage+"']";
			String fullXpath = fundXpath+cmntXpath+dateXpath;
			ele = FindElement(driver, fullXpath, "Value xpath", action.BOOLEAN, 10);
			if (ele!=null) {
				flag=true;
			} else {
				flag=false;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return flag;
	}


	public boolean verifyPipeLineRelatedList(String environment,String mode,RecordType RecordType,String[][] headersWithValues){
		boolean flag=true;
		List<WebElement> header = new ArrayList<WebElement>();
		List<WebElement> values = new ArrayList<WebElement>();
		try {
			if(mode.equalsIgnoreCase(Mode.Classic.toString())){

				FindElement(driver, "//div[@class='bRelatedList']//h3[text()='Pipelines']", "", action.SCROLLANDBOOLEAN, 10);
				header = FindElements(driver, "//h3[text()='Pipelines']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr[1]/*", "Header");
				values = FindElements(driver, "//h3[text()='Pipelines']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr[2]/*", "Values");
				int i = 1;
				for (String[] headerWithValue : headersWithValues) {
					appLog.info("From PAGE    : "+header.get(i).getText()+"  <<<<<>>>>> "+values.get(i).getText());
					appLog.info("fROM tESTcASE  : "+headerWithValue[0].replace("_", " ")+"  <<<<<>>>>> "+headerWithValue[1]);
					if(header.get(i).getText().contains(headerWithValue[0].replace("_", " ")) && values.get(i).getText().contains(headerWithValue[1])){
						appLog.info("Value matched : "+headerWithValue[1]);
					}else{
						flag=false;
						appLog.error("Value Not matched : "+headerWithValue[1]);
						BaseLib.sa.assertTrue(false, "Value Not matched : "+headerWithValue[1]);	
					}
					i++;
				}
			}else{

				if(click(driver, getRelatedTab_Lighting(environment,RecordType, 10), "Related Tab", action.SCROLLANDBOOLEAN)){
					ThreadSleep(10000);
					log(LogStatus.INFO, "Clicked on Related Tab", YesNo.No);
					if(clickUsingJavaScript(driver, getPipeLineViewAll_Lighting(environment, 10), "PipeLine View All", action.SCROLLANDBOOLEAN)){
						ThreadSleep(3000);
						driver.navigate().refresh();
						ThreadSleep(5000);
						flag=verifyRelatedListViewAllColumnAndValue(headersWithValues);
					}
				}else{
					flag=false;
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		return flag;

	}
	public boolean clickonDetails(String environment,String mode,String fundName,String cmntAmmmountinMn) {
		boolean flag=false;
		try {

			String fundXpath= "//a[text()='"+fundName+"']";

			float f= Float.parseFloat(cmntAmmmountinMn)/1000000;
			System.err.println(f);
			String monney=BasePageBusinessLayer.convertNumberAccordingToFormatWithCurrencySymbol(""+f,"0.0") ;
			String cmntXpath= "/ancestor::tr//*[text()='"+monney+"']";
			String detailsXpath= "/ancestor::tr//button[text()='Details']";
			String fullXpath = fundXpath+cmntXpath+detailsXpath;

			WebElement ele = FindElement(driver, fullXpath, "Value xpath", action.BOOLEAN, 10);
			if (ele!=null) {
				if (clickUsingJavaScript(driver, ele, "Details Image", action.BOOLEAN)) {
					click(driver, ele, "Details Image", action.BOOLEAN);
					flag=true;
					ThreadSleep(2000);
				}
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return flag;	
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param btnName
	 * @param action
	 * @param timeOut
	 * @return toggle button
	 */
	public WebElement toggleButton(String projectName,PageName pageName,String btnName,action action,int timeOut) {
		String xpath = "//button[@title='"+btnName+"']";
		WebElement ele = FindElement(driver, xpath,"Toggle Button : "+btnName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btnName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btnName);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param toggleTab
	 * @param btnName
	 * @param action
	 * @param timeOut
	 * @return SDG toggle button
	 */
	public WebElement toggleSDGButtons(String projectName,PageName pageName,String toggleTab,ToggleButtonGroup btnName,action action,int timeOut) {
		String btname = btnName.toString();
		String xpath = "//*[text()='"+toggleTab+"']/../../..//following-sibling::div//button[@title='"+btname+"']";
		WebElement ele = FindElement(driver, xpath,toggleTab+" >> "+btname, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btname);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btname);
		return ele;
	}


	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param fundName
	 * @param action
	 * @param timeOut
	 * @return fund name webElement of Toggle
	 */
	public WebElement getFundNameAtToggle(String projectName,PageName pageName,String fundName,action action,int timeOut) {
		String xpath = "//*[@data-label='Fund: ']//*[text()='"+fundName+"']";
		WebElement ele = FindElement(driver, xpath,fundName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Fund Name : "+fundName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Fund Name : "+fundName);
		return ele;
	}


	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param fundName
	 * @param action
	 * @param timeOut
	 * @return tool tip of fund name at toggle
	 */
	public WebElement getFundNameAtToggleToolTip(String projectName,PageName pageName,String fundName,action action,int timeOut) {
		String xpath = "//*[@data-label='Fund: ']//*[text()='"+fundName+"']/..";
		WebElement ele = FindElement(driver, xpath,fundName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Fund Name : "+fundName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Fund Name : "+fundName);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param entityName
	 * @param action
	 * @param timeOut
	 * @return Legal Entity At Toggle WebElement
	 */
	public WebElement getLegalEntityAtToggle(String projectName,PageName pageName,String entityName,action action,int timeOut) {
		String xpath = "//*[@data-label='Legal Entity: ']//*[text()='"+entityName+"']";
		WebElement ele = FindElement(driver, xpath,entityName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Fund Name : "+entityName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Fund Name : "+entityName);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param name
	 * @param action
	 * @param timeOut
	 * @return Inline webElement ofToggle
	 */
	public WebElement getInlineOrLockedAtToggle(String projectName,PageName pageName,String name,action action,int timeOut) {
		String xpath = "//a[text()='Fund Investments']/../../../../../..//*[@title='"+name+"']/../following-sibling::span/button";
		WebElement ele = FindElement(driver, xpath,name, action, timeOut);
		scrollDownThroughWebelement(driver, ele, name);
		return ele;
	}


	/**
	 * @author Ankit Jaiswal
	 * @param projectName
	 * @param environment
	 * @param mode
	 * @param institutionName
	 * @param recordType
	 * @param otherLabelFields
	 * @param otherLabelValues
	 * @return if institution created successfully
	 */
	public boolean createInstitution(String projectName,String environment,String mode,String institutionName,String recordType, String otherLabelFields,String otherLabelValues) {
		String labelNames[]=null;
		String labelValue[]=null;
		if(otherLabelFields!=null && otherLabelValues !=null) {
			labelNames= otherLabelFields.split(",");
			labelValue=otherLabelValues.split(",");
		}
		refresh(driver);
		ThreadSleep(3000);
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			ThreadSleep(10000);
			if(clickUsingJavaScript(driver, getNewButton(environment, mode, 60), "new button")) {
				appLog.info("clicked on new button");
			}else {
				appLog.error("Not able to click on New Button so cannot create institution: " + institutionName);
				return false;
			}
		}else {
			if (click(driver, getNewButton(environment,mode,60), "New Button", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on new button");
			} else {
				appLog.error("Not able to click on New Button so cannot create institution: " + institutionName);
				return false;
			}
		}
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			ThreadSleep(2000);
			if (selectVisibleTextFromDropDown(driver, getRecordTypeOfNewRecordDropDownList(60),
					"Record type of new record drop down list", recordType)) {
				appLog.info("selecte institution from record type of new record drop down list");
			}else{
				appLog.error("Not Able to selecte institution from record type of new record drop down list");
				return false;
			}
		}else{
			ThreadSleep(2000);
			if(click(driver, getRadioButtonforRecordType(recordType, 60), "Radio Button for New Institution", action.SCROLLANDBOOLEAN)){
				appLog.info("Clicked on radio Button for institution from record type");
			}else{
				appLog.info("Not Able to Clicked on radio Button for institution from record type");
				return false;
			}
		}

		if (click(driver, getContinueOrNextBtn(environment,mode,60), "Continue Button", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on continue button");
			if (sendKeys(driver, getLegalNameTextBox(environment,mode,30), institutionName, "leagl name text box",
					action.SCROLLANDBOOLEAN)) {
				appLog.info("passed data in text box: " + institutionName);
				if(labelNames!=null && labelValue!=null) {
					for(int i=0; i<labelNames.length; i++) {
						WebElement ele = getInstitutionPageTextBoxOrRichTextBoxWebElement(environment, mode, labelNames[i].trim(), 30);
						if(sendKeys(driver, ele, labelValue[i], labelNames[i]+" text box", action.SCROLLANDBOOLEAN)) {
							appLog.info("passed value "+labelValue[i]+" in "+labelNames[i]+" field");


							if (labelNames[i].toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Institution.toString()) || labelNames[i].toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Entity.toString())) {

								ThreadSleep(1000);
								if (click(driver,
										FindElement(driver,"//*[@title='"+labelValue[i]+"']//strong[text()='"+labelValue[i].split(" ")[0]+"']",
												"Legal Name List", action.SCROLLANDBOOLEAN, 30),
										labelValue[i] + "   :   Legal Name", action.SCROLLANDBOOLEAN)) {
									appLog.info(labelValue[i] + "  is present in list.");
								} else {
									appLog.info(labelValue[i] + "  is not present in the list.");
									BaseLib.sa.assertTrue(false,labelValue[i] + "  is not present in the list.");
								}
							}

						}else {
							appLog.error("Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
							BaseLib.sa.assertTrue(false, "Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
						}
					}

				}
				if (click(driver, getCustomTabSaveBtn(projectName,30), "save button", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on save button");
					ThreadSleep(5000);
					//							String	xpath="//span[@class='custom-truncate uiOutputText'][text()='"+institutionName+"']";
					//							WebElement ele = FindElement(driver, xpath, "Header : "+institutionName, action.BOOLEAN, 30);
					WebElement ele = verifyCreatedItemOnPage(Header.Company, institutionName);
					if (ele != null) {
						appLog.info("created institution " + institutionName + " is verified successfully.");
						appLog.info(institutionName + " is created successfully.");

						if(labelNames!=null && labelValue!=null ) {
							for(int i=0; i<labelNames.length; i++) {
								//											
								if(fieldValueVerificationOnInstitutionPage(environment, mode, null, labelNames[i].replace("_", " ").trim(),labelValue[i])){
									appLog.info(labelNames[i]+" label value "+labelValue[i]+" is matched successfully.");
								}else {
									appLog.info(labelNames[i]+" label value "+labelValue[i]+" is not matched successfully.");
									BaseLib.sa.assertTrue(false, labelNames[i]+" label value "+labelValue[i]+" is not matched.");
								}

							}
						}
						return true;

					} else {
						appLog.error("Created institution " + institutionName + " is not visible");
					}
				} else {
					appLog.error("Not able to click on save button so cannot create institution: "
							+ institutionName);
				}
			} else {
				appLog.error("Not able to pass data in legal name text box so cannot create institution: "
						+ institutionName);
			}
		} else {
			appLog.error(
					"Not able to click on continue button so cannot create institution: " + institutionName);
		}


		return false;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param tabName
	 * @param labelName
	 * @param labelValue
	 * @return true if field Value Verified On Institution Page successfully
	 */
	public boolean fieldValueVerificationOnInstitutionPage(String environment, String mode, TabName tabName,
			String labelName,String labelValue) {
		String finalLabelName;
		labelValue=labelValue.replace("_", " ");


		if(labelName.contains(excelLabel.Total_CoInvestment_Commitments.toString())) {
			labelName=LimitedPartnerPageFieldLabelText.Total_CoInvestment_Commitments.toString();
			labelValue=convertNumberIntoMillions(labelValue);

		}else if (labelName.contains(excelLabel.Total_Fund_Commitments.toString())) {
			labelName=LimitedPartnerPageFieldLabelText.Total_Fund_Commitments.toString();
			labelValue=convertNumberIntoMillions(labelValue);
		}/*else if (labelName.equalsIgnoreCase(excelLabel.Phone.toString()) || labelName.equalsIgnoreCase(excelLabel.Fax.toString())) {
			labelValue=changeNumberIntoUSFormat(labelValue);
		}*/

		if (labelName.contains("_")) {
			finalLabelName = labelName.replace("_", " ");
		} else {
			finalLabelName = labelName;
		}
		String xpath = "";
		WebElement ele = null;
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			if(finalLabelName.equalsIgnoreCase(excelLabel.Institution_Type.toString().replace("_", " "))) {

				xpath = "(//span[text()='" + finalLabelName + "']/../following-sibling::td/div)[1]";
			}else {
				xpath = "//td[text()='"+finalLabelName+"']/following-sibling::td/div";
			}

		} else {
			/////////////////  Lighting New Start /////////////////////////////////////
			if(finalLabelName.contains("Street") || finalLabelName.contains("City") || finalLabelName.contains("State") || finalLabelName.contains("Postal") || finalLabelName.contains("Zip") || finalLabelName.contains("Country")) {

				if(finalLabelName.contains("Shipping") ||finalLabelName.contains("Other Street") || finalLabelName.contains("Other City") || finalLabelName.contains("Other State") || finalLabelName.contains("Other Zip") || finalLabelName.contains("Other Country") ) {
					xpath="//span[text()='Shipping Address']/../following-sibling::div//a[contains(@title,'"+labelValue+"')]";	
				}else{
					xpath="//span[text()='Address']/../following-sibling::div//a[contains(@title,'"+labelValue+"')]";
				}

			}else if(labelName.equalsIgnoreCase("Deal Conversion Date") || labelName.equalsIgnoreCase("Conversion Date")){

				xpath="//span[text()='Deal Conversion Date']/../following-sibling::div//lightning-formatted-text";
			}else {

				if (labelName.equalsIgnoreCase(excelLabel.Phone.toString()) || labelName.equalsIgnoreCase(excelLabel.Fax.toString())) {
					xpath = "//span[text()='"+finalLabelName+"']/../following-sibling::div//*[contains(text(),'"+labelValue+"') or contains(text(),'"+changeNumberIntoUSFormat(labelValue)+"')]";	
				} else {
					xpath = "//span[text()='"+finalLabelName+"']/../following-sibling::div//*[text()='"+labelValue+"']";
				}


			}

			if (labelValue.isEmpty() || labelValue.equals("")) {
				xpath = "//span[text()='"+finalLabelName+"']/../following-sibling::div//*";
				ele = 		FindElement(driver, xpath, finalLabelName + " label text with  " + labelValue, action.SCROLLANDBOOLEAN, 10);
				scrollDownThroughWebelement(driver, ele, finalLabelName + " label text with  " + labelValue);
				if (ele!=null) {
					String aa = ele.getText().trim();
					System.err.println("Value  "+aa);

					if (aa.isEmpty() || aa.equals(labelValue)) {

						return true;	
					}else {
						return false;
					}

				}else {
					return false;
				}

			}
			List<WebElement> list =new ArrayList<>();

			list = FindElements(driver, xpath, "");
			for(WebElement element:list){

				element=isDisplayed(driver,element,"Visibility", 10, "");
				if(element!=null){
					ele =element;
					break;
				}
			}
			scrollDownThroughWebelement(driver, ele, finalLabelName + " label text with  " + labelValue);
			ele = 	isDisplayed(driver,ele,"Visibility", 10, finalLabelName + " label text with  " + labelValue);
			if (ele != null) {
				String aa = ele.getText().trim();
				System.err.println("Value  "+aa);

				appLog.info(finalLabelName + " label text with  " + labelValue+" verified");
				return true;

			} else {
				appLog.error("<<<<<<   "+finalLabelName + " label text with  " + labelValue+" not verified "+"   >>>>>>");
			}
			return false;


			/////////////////  Lighting New End /////////////////////////////////////
		}

		if(finalLabelName.contains("Street") || finalLabelName.contains("City") || finalLabelName.contains("State") || finalLabelName.contains("Postal") || finalLabelName.contains("Zip") || finalLabelName.contains("Country")) {

			if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				//	xpath="//span[text()='Address Information']/../../following-sibling::div";
				if(finalLabelName.contains("Legal Name")){
					xpath="("+xpath+")[2]";
				}else if(finalLabelName.contains("Other Street") || finalLabelName.contains("Other City") || finalLabelName.contains("Other State") || finalLabelName.contains("Other Zip") || finalLabelName.contains("Other Country") ||
						finalLabelName.contains("Shipping")) {
					xpath="(//span[text()='Address Information']/../../following-sibling::div/div/div/div/div)[2]";	
				}else{
					xpath="(//span[text()='Address Information']/../../following-sibling::div/div/div/div/div)[1]";
				}
			}else {
				if(finalLabelName.contains("Other Street") || finalLabelName.contains("Other City") || 
						finalLabelName.contains("Other State") || finalLabelName.contains("Other Zip") || finalLabelName.contains("Other Country") || 
						finalLabelName.contains("Shipping Street") || finalLabelName.contains("Shipping City") || finalLabelName.contains("Shipping State") || 
						finalLabelName.contains("Shipping Zip") || finalLabelName.contains("Shipping Country")) {
					xpath="(//h3[text()='Address Information']/../following-sibling::div[1]//td//tbody/tr[1]/td)[2]";	
				}else{
					xpath="(//h3[text()='Address Information']/../following-sibling::div[1]//td//tbody/tr[1]/td)[1]";
				}
			}
		}
		ele = isDisplayed(driver,
				FindElement(driver, xpath, finalLabelName + " label text in " + mode, action.SCROLLANDBOOLEAN, 60),
				"Visibility", 30, finalLabelName + " label text in " + mode);
		if (ele != null) {
			String aa = ele.getText().trim();
			appLog.info("Lable Value is: "+aa);

			if (labelName.equalsIgnoreCase(excelLabel.Phone.toString()) || labelName.equalsIgnoreCase(excelLabel.Fax.toString())) {
				if(aa.contains(labelValue) || aa.contains(changeNumberIntoUSFormat(labelValue)) ) {
					appLog.info(labelValue + " Value is matched successfully.");
					return true;
				}
			}else if(aa.contains(labelValue)) {
				appLog.info(labelValue + " Value is matched successfully.");
				return true;

			}else {
				appLog.info(labelValue + " Value is not matched. Expected: "+labelValue+" /t Actual : "+aa);
			}
		} else {
			appLog.error(finalLabelName + " Value is not visible so cannot matched  label Value "+labelValue);
		}
		return false;

	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param status
	 * @return if status of deal change successfully
	 */
	public boolean changeStatus(String projectName,String status) {
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		boolean flag=true;
		status=status.replace("_", " ");
		if (clickOnShowMoreActionDownArrow(projectName, PageName.Object1Page, ShowMoreActionDropDownList.Edit, 10)) {
			if (click(driver, fp.getDealStatus(projectName, 10), "Status : "+status, action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);
				appLog.error("Clicked on Deal Status");

				String xpath="//span[@title='"+status+"']";
				WebElement dealStatusEle = FindElement(driver,xpath, status,action.SCROLLANDBOOLEAN, 10);
				ThreadSleep(2000);
				if (clickUsingJavaScript(driver, dealStatusEle, status, action.SCROLLANDBOOLEAN)) {
					appLog.info("Selected Status : "+status);
					ThreadSleep(2000);
				} else {
					log(LogStatus.ERROR,"Not able to Select on Status : "+status,YesNo.No);
					flag=false;
				}
				if (click(driver, getNavigationTabSaveBtn(projectName,10), "save button", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on save button");
				}else {
					appLog.error("save button is not clickable so cannot change cmpany to watchlist");
					flag=false;
				}
			} else {
				log(LogStatus.ERROR,"Not able to Click on Status : "+status,YesNo.Yes);
				flag=false;
			}
		}else {
			log(LogStatus.ERROR,"edit button is not clickable so cannot change cmpany to "+status, YesNo.Yes);
			flag=false;
		}
		return flag;
	}


	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param institutionName
	 * @param recordType
	 * @param otherLabelFields
	 * @param otherLabelValues
	 * @return true if institution created successfully
	 */
	public boolean createInstitution(String environment,String mode,String institutionName,String recordType, String otherLabelFields,String otherLabelValues) {
		String labelNames[]=null;
		String labelValue[]=null;
		if(otherLabelFields!=null && otherLabelValues !=null) {
			labelNames= otherLabelFields.split(",");
			labelValue=otherLabelValues.split(",");
		}
		refresh(driver);
		ThreadSleep(3000);
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			ThreadSleep(10000);
			if(clickUsingJavaScript(driver, getNewButton(environment, mode, 60), "new button")) {
				appLog.info("clicked on new button");
			}else {
				appLog.error("Not able to click on New Button so cannot create institution: " + institutionName);
				return false;
			}
		}else {
			if (click(driver, getNewButton(environment,mode,60), "New Button", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on new button");
			} else {
				appLog.error("Not able to click on New Button so cannot create institution: " + institutionName);
				return false;
			}
		}
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			ThreadSleep(2000);
			if (selectVisibleTextFromDropDown(driver, getRecordTypeOfNewRecordDropDownList(60),
					"Record type of new record drop down list", recordType)) {
				appLog.info("selecte institution from record type of new record drop down list");
			}else{
				appLog.error("Not Able to selecte institution from record type of new record drop down list");
				return false;
			}
		}else{
			ThreadSleep(2000);
			if(click(driver, getRadioButtonforRecordType(recordType, 60), "Radio Button for New Institution", action.SCROLLANDBOOLEAN)){
				appLog.info("Clicked on radio Button for institution from record type");
			}else{
				appLog.info("Not Able to Clicked on radio Button for institution from record type");
				return false;
			}
		}

		if (click(driver, getContinueOrNextBtn(environment,mode,60), "Continue Button", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on continue button");
			if (sendKeys(driver, getLegalNameTextBox(environment,mode,30), institutionName, "leagl name text box",
					action.SCROLLANDBOOLEAN)) {
				appLog.info("passed data in text box: " + institutionName);
				if(labelNames!=null && labelValue!=null) {
					for(int i=0; i<labelNames.length; i++) {
						WebElement ele = getInstitutionPageTextBoxOrRichTextBoxWebElement(environment, mode, labelNames[i].trim(), 30);
						if(sendKeys(driver, ele, labelValue[i], labelNames[i]+" text box", action.SCROLLANDBOOLEAN)) {
							appLog.info("passed value "+labelValue[i]+" in "+labelNames[i]+" field");


							if (labelNames[i].toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Entity.toString()) || labelNames[i].toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Institution.toString())) {

								ThreadSleep(1000);
								if (clickUsingJavaScript(driver,getItemInList("", labelValue[i], action.BOOLEAN, 20),
										labelValue[i] + "   :  Parent Name", action.BOOLEAN)) {
									appLog.info(labelValue[i] + "  is present in list.");
								} else {
									appLog.info(labelValue[i] + "  is not present in the list.");
									BaseLib.sa.assertTrue(false,labelValue[i] + "  is not present in the list.");
								}
							}

						}else {
							appLog.error("Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
							BaseLib.sa.assertTrue(false, "Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
						}
					}

				}
				if (click(driver, getNavigationTabSaveBtn(mode,30), "save button", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on save button");
					ThreadSleep(5000);
					//							String	xpath="//span[@class='custom-truncate uiOutputText'][text()='"+institutionName+"']";
					//							WebElement ele = FindElement(driver, xpath, "Header : "+institutionName, action.BOOLEAN, 30);
					WebElement ele = verifyCreatedItemOnPage(Header.Company, institutionName);
					if (ele != null) {
						appLog.info("created institution " + institutionName + " is verified successfully.");
						appLog.info(institutionName + " is created successfully.");

						if(labelNames!=null && labelValue!=null ) {
							for(int i=0; i<labelNames.length; i++) {
								//											
								if(fieldValueVerificationOnInstitutionPage(environment, mode, null, labelNames[i].replace("_", " ").trim(),labelValue[i])){
									appLog.info(labelNames[i]+" label value "+labelValue[i]+" is matched successfully.");
								}else {
									appLog.info(labelNames[i]+" label value "+labelValue[i]+" is not matched successfully.");
									BaseLib.sa.assertTrue(false, labelNames[i]+" label value "+labelValue[i]+" is not matched.");
								}

							}
						}
						return true;

					} else {
						appLog.error("Created institution " + institutionName + " is not visible");
					}
				} else {
					appLog.error("Not able to click on save button so cannot create institution: "
							+ institutionName);
				}
			} else {
				appLog.error("Not able to pass data in legal name text box so cannot create institution: "
						+ institutionName);
			}
		} else {
			appLog.error(
					"Not able to click on continue button so cannot create institution: " + institutionName);
		}


		return false;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param partnershipLegalName
	 * @param fund
	 * @return true if partnership created successfully
	 */
	public boolean createPartnership(String environment,String mode,String partnershipLegalName, String fund) {
		refresh(driver);
		ThreadSleep(5000);
		if (click(driver, getNewButton(environment,mode,60), "New Button", action.BOOLEAN)) {
			if (sendKeys(driver, getPartnershipLegalName(environment,mode,60), partnershipLegalName, "Partnership Legal Name",
					action.BOOLEAN)) {
				if (sendKeys(driver, getFundTextBox(environment,mode,60), fund, "Fund Text Box", action.BOOLEAN)) {
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						ThreadSleep(1000);
						if (click(driver,
								FindElement(driver,
										"//*[contains(@class,'slds-listbox__option-text')]/*[@title='"+fund+"']",
										"fund Name List", action.THROWEXCEPTION, 30),
								fund + "   :   fund Name", action.BOOLEAN)) {
							appLog.info(fund + "  is present in list.");
						} else {
							appLog.info(fund + "  is not present in the list.");
						}
					}
					if (click(driver, getCustomTabSaveBtn(mode,60), "Save Button", action.BOOLEAN)) {
						if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							ThreadSleep(5000);
						}
						if (getPartnershipNameInViewMode(environment,mode,60,partnershipLegalName) != null) {
							String partnershipName = getText(driver, getPartnershipNameInViewMode(environment,mode,60,partnershipLegalName),
									"Partnership name in view mode", action.BOOLEAN);
							if (partnershipName.equalsIgnoreCase(partnershipLegalName)) {
								appLog.info("Partnership created successfully.:" + partnershipLegalName);
								return true;
							} else {
								appLog.error("Partnership is not created successfully." + partnershipLegalName);
							}
						} else {
							appLog.error("Partnership name is not displaying");
						}
					} else {
						appLog.error("Not able to click on save button");
					}
				} else {
					appLog.error("Not able to enter value in fund text box");
				}
			} else {
				appLog.error("Not able to enter value in partnershp legal name text box");
			}
		} else {
			appLog.error("Not able to click on new button so we cannot create partnership");
		}
		return false;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param LimitedPartner
	 * @param Partnership
	 * @param basedOnValue
	 * @param excelPath
	 * @return true if commitment created successfully
	 */
	public boolean createCommitment(String environment,String mode,String LimitedPartner, String Partnership, String basedOnValue,String excelPath) {
		refresh(driver);
		ThreadSleep(5000);
		if (click(driver, getNewButton(environment,mode,60), "New Button", action.BOOLEAN)) {
			ThreadSleep(5000);
			if (sendKeys(driver, getLimitedPartnerTextbox(mode,60), LimitedPartner, "Limited Partner Text Box",
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
					}
				}
				if (sendKeys(driver, getPartnershipTextBox(mode,60), Partnership, "Partnership Text Box", action.BOOLEAN)) {
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
						}
					}
					if (click(driver, getCustomTabSaveBtn(mode,60), "Save Button", action.SCROLLANDBOOLEAN)) {
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

	public WebElement eventOnCalender(String projectName, String event) {
		String xpath="//a[@title='"+event+"']";
		WebElement ele=FindElement(driver,xpath,"event on calender", action.BOOLEAN, 10);
		return ele;
	}

	public int findLocationOfEvent(String projectName, String event ) {
		String xpath="//td//a[@title='"+event+"']/../preceding-sibling::td";
		List<WebElement> li = FindElements(driver, xpath, "list of preceding dates");
		int size=li.size()+1;
		appLog.info("returning event "+event +" list size as "+size);
		return li.size()+1;
	}
	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param date
	 * @return column index of particular date on Calendar
	 */
	public int findLocationOfDate(String projectName, String date, String month) {
		String xpath="//a[text()='"+date+"']/../preceding-sibling::td";
		if (month!=null)
			xpath="//a[text()='"+date+"']/../preceding-sibling::td[contains(@data-date,'"+month+"')]";
		List<WebElement> li = FindElements(driver, xpath, "list of preceding dates");
		int size=li.size()+1;
		appLog.info("found date "+date +" at location "+size);

		return li.size()+1;
	}
	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param date
	 * @param location
	 * @param timeOut
	 * @return Event Present On Calender webElement
	 */
	public WebElement getEventPresentOnCalender(String projectName, String date, int location, int timeOut) {
		String xpath="//a[text()='"+date+"']/../../../following-sibling::tbody//td["+location+"]//a";
		WebElement ele=null;
		ele=FindElement(driver,xpath,"event on calender", action.BOOLEAN, timeOut);
		if (ele!=null) {
			ele=isDisplayed(driver, ele, "visibility", timeOut, "event on calender");
			return ele;
		}
		return null;

	}
	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param date
	 * @param location
	 * @return Event Present On Calender webElement
	 */
	public List<WebElement> getEventPresentOnCalender(String projectName, String date, int location) {
		String xpath="//a[text()='"+date+"']/../../../following-sibling::tbody//td["+location+"]//a";
		List<WebElement> ele=null;
		ele=FindElements(driver,xpath,"event on calender" );
		if (ele!=null) {
			return ele;
		}
		return null;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param date
	 * @param timeOut
	 * @return List<WebElement> of getListOfEvents
	 */
	public List<WebElement> getListOfEvents(String projectName, String date,  int timeOut) {
		String xpath="//span[text()='"+date+"']/../following-sibling::div//div[contains(@class,'fc-event-container')]//a";
		List<WebElement> ele=null;
		ele=FindElements(driver,xpath,"event on calender" );

		return ele;

	}
	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param dateHiphen
	 * @return true if able erform mouse hover on Calender Box
	 */
	public boolean calendarBox(String projectName, String dateHiphen) {
		String date=dateHiphen.split("-")[2];
		/*String xpath = "//a[text()='"+date+"']";
		WebElement ele=FindElement(driver,xpath,"date block on calender", action.THROWEXCEPTION, 10);
		ele=isDisplayed(driver, ele, "visibility", 10, "date block on calender");
		if (click(driver, ele, "date box", action.BOOLEAN)) {
		 */
		Scanner sc = new Scanner(System.in);
		String xpath = "//div[@id='calendar']//tbody//tr/td[@class='fc-day fc-widget-content fc-fri fc-past']";
		//String xpath = "//tbody//td[contains(@data-date,'"+dateHiphen+"')]";
		List<WebElement> li=FindElements(driver, xpath, "date on cal");
		try {
			for (WebElement el:li) {
				//WebElement ele=FindElement(driver,xpath,"date block on calender", action.BOOLEAN, 10);
				//ele=isDisplayed(driver, ele, "visibility",10, "date");
				clickUsingJavaScript(driver, el, "date on cal");
				appLog.error(">>>");
				sc.next();
				ThreadSleep(2000);
			}
			li=FindElements(driver, xpath, "date on cal");
			for (WebElement el:li) {
				//WebElement ele=FindElement(driver,xpath,"date block on calender", action.BOOLEAN, 10);
				//ele=isDisplayed(driver, ele, "visibility",10, "date");
				click(driver, el, "date on cal",action.BOOLEAN);
				appLog.error(">>>");
				sc.next();
				ThreadSleep(2000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Actions ac = new Actions(driver);
		ac.moveToElement(ele).build().perform();
		Robot robot;
		try {
			robot = new Robot();
			robot.mousePress(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return true;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param cb
	 * @return calende Buttons WebElement
	 */
	public WebElement calenderButtons(String projectName, CalenderButton cb) {
		String xpath="";
		if ((cb == CalenderButton.next) || (cb==CalenderButton.prev))
			xpath="//button[@aria-label='"+cb+"']";
		else
			xpath = "//button[text()='"+cb+"']";
		WebElement ele=FindElement(driver, xpath, "calender button", action.BOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", 10, "calender button");

	}
	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param month
	 * @return true if able to reach Desired Month On Calnder
	 */
	public boolean reachToDesiredMonthOnCalnder(String projectName, String month) {
		click(driver, calenderButtons(projectName, CalenderButton.Month), "calnder button", action.BOOLEAN);
		String xpathOfCalender = "//div[contains(@class,'Fullcalendar')]//h2";
		String xpathOfNext="";
		WebElement ele=FindElement(driver,xpathOfCalender,"selected month on calender", action.THROWEXCEPTION, 10);
		if (ele!=null) {
			while (!ele.getText().trim().equalsIgnoreCase(month)) {
				xpathOfNext = "//button[@aria-label='next']";
				ele=FindElement(driver,xpathOfNext,"selected month on calender", action.THROWEXCEPTION, 10);
				click(driver, ele, "next", action.SCROLLANDBOOLEAN);
				ele=FindElement(driver,xpathOfCalender,"selected month on calender", action.THROWEXCEPTION, 10);
				System.out.println(ele.getText().trim());
			}
			log(LogStatus.INFO, "successfully reached desired month "+month, YesNo.No);
			return true;
		}
		else {
			log(LogStatus.ERROR, "could not find calender month "+month, YesNo.Yes);

		}
		return false;
	}


	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param institutionName
	 * @param recordType
	 * @param labelsWithValues
	 * @param timeOut
	 * @return true if entity created successfully
	 */
	public boolean createEntityOrAccountPopUp(String projectName,String institutionName,String recordType, String[][] labelsWithValues,int timeOut) {
		boolean flag=false;
		ThreadSleep(5000);

		if (!recordType.equals("") || !recordType.isEmpty()) {
			ThreadSleep(2000);
			if(click(driver, getRadioButtonforRecordTypeNavigationPopup(recordType, timeOut), "Radio Button for : "+recordType, action.SCROLLANDBOOLEAN)){
				appLog.info("Clicked on radio Button for institution for record type : "+recordType);
				if (click(driver, getContinueOrNextButton(projectName,timeOut), "Continue Button", action.BOOLEAN)) {
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

		if (sendKeys(driver, getLegalName(projectName,timeOut), institutionName, "leagl name text box",action.SCROLLANDBOOLEAN)) {
			appLog.info("passed data in text box: " + institutionName);

			FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
			if (labelsWithValues!=null) {
				for (String[] strings : labelsWithValues) {
					if (click(driver, fp.getDealStatus(projectName, timeOut), "Status : "+strings[1], action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						appLog.error("Clicked on Deal Status");

						String xpath="//div[@class='select-options']//li/a[@title='"+strings[1]+"']";
						WebElement dealStatusEle = FindElement(driver,xpath, strings[1],action.SCROLLANDBOOLEAN, timeOut);
						ThreadSleep(2000);
						if (click(driver, dealStatusEle, strings[1], action.SCROLLANDBOOLEAN)) {
							appLog.info("Selected Status : "+strings[1]);
							ThreadSleep(2000);
						} else {
							appLog.error("Not able to Select on Status : "+strings[1]);
							flag=false;
						}
					} else {
						appLog.error("Not able to Click on Status : ");
						flag=false;
					}
				}
			}
			if (click(driver, getNavigationTabSaveBtn(projectName,timeOut), "save button", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on save button");

				String str = getText(driver, getLegalNameHeader(projectName,timeOut), "legal Name Label Text",action.SCROLLANDBOOLEAN);
				if (str != null) {
					if (str.contains(institutionName)) {
						appLog.info("created institution " + institutionName + " is verified successfully.");
						appLog.info(institutionName + " is created successfully.");
						flag=true;
					} else {
						appLog.error("Created  " + institutionName + " is not matched with " + str);
					}
				} else {
					appLog.error("Created  " + institutionName + " is not visible");
				}
			} else {
				appLog.error("Not able to click on save button so cannot create : "+ institutionName);
			}
		} else {
			appLog.error("Not able to pass data in legal name text box so cannot create : "+ institutionName);
		}
		return flag;
	}


	public boolean verifyFieldSetComponent(String labelName, String value) {
		String finalLabelName="";
		if(labelName.contains("_")) {
			finalLabelName = labelName.replace("_", " ");
		}else {
			finalLabelName = labelName;
		}
		String xpath="//*[@class='navpeIIDisplayFieldSet']//*[contains(text(),'"+finalLabelName+"')]/following-sibling::div/*";

		WebElement ele = FindElement(driver, xpath, finalLabelName + " label text", action.SCROLLANDBOOLEAN, 5);
		if (ele != null) {
			String aa = ele.getText().trim();
			appLog.info("<<<<<<<<     "+finalLabelName+ " : Lable Value is: "+aa+"      >>>>>>>>>>>");

			if (aa.isEmpty()) {
				appLog.error(finalLabelName + " Value is Empty label Value "+value);
				if(value.isEmpty() && aa.isEmpty()) {
					return true;
				}else {
					return false;
				}
			}
			if (labelName.equalsIgnoreCase(excelLabel.Phone.toString()) || labelName.equalsIgnoreCase(excelLabel.Fax.toString())||
					labelName.equalsIgnoreCase(ContactPageFieldLabelText.Mobile.toString()) ||
					labelName.equalsIgnoreCase(excelLabel.Asst_Phone.toString())) {

				if(aa.contains(value) || aa.contains(changeNumberIntoUSFormat(value))) {
					appLog.info(value + " Value is matched successfully.");
					return true;

				}
			}else if(aa.contains(value)) {
				appLog.info(value + " Value is matched successfully.");
				return true;

			}else {
				appLog.info(value + " Value is not matched. Expected: "+value+" /t Actual : "+aa);
			}
		} else {
			appLog.error(finalLabelName + " Value is not visible so cannot matched  label Value "+value);
		}
		return false;

	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param count
	 * @param timeOut
	 * @return webElement of plus More Button On Calendar
	 */
	public WebElement plusMoreButtonOnCalendar(String projectName,int count, int timeOut) {

		String xpath = "//td[contains(@class,'more-cell')]//a[text()='+"+count+" more']";
		WebElement ele=FindElement(driver,xpath,"selected month on calender", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", 10, "calender button");

	}

	public boolean createInstitutionPopUp(String projectName,String environment,String mode,String institutionName,String recordType, String otherLabelFields,String otherLabelValues) {
		String labelNames[]=null;
		String labelValue[]=null;
		if(otherLabelFields!=null && otherLabelValues !=null) {
			labelNames= otherLabelFields.split(",");
			labelValue=otherLabelValues.split(",");
		}
		//	refresh(driver);
		ThreadSleep(3000);
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			ThreadSleep(10000);

		}else {
			if (click(driver, getNewButton(environment,mode,60), "New Button", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on new button");
			} else {
				appLog.error("Not able to click on New Button so cannot create institution: " + institutionName);
				return false;
			}
		}
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			ThreadSleep(2000);
			if (selectVisibleTextFromDropDown(driver, getRecordTypeOfNewRecordDropDownList(60),
					"Record type of new record drop down list", recordType)) {
				appLog.info("selecte institution from record type of new record drop down list");
			}else{
				appLog.error("Not Able to selecte institution from record type of new record drop down list");
				return false;
			}
		}else{
			ThreadSleep(2000);
			if(click(driver, getRadioButtonforRecordType(recordType, 5), "Radio Button for New Institution", action.SCROLLANDBOOLEAN)){
				appLog.info("Clicked on radio Button for institution from record type");
			}else{
				appLog.info("Not Able to Clicked on radio Button for institution from record type");
				return false;
			}
		}

		if (click(driver, getContinueOrNextBtn(environment,mode,60), "Continue Button", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on continue button");
			if (sendKeys(driver, getLegalNameTextBox(environment,mode,30), institutionName, "leagl name text box",
					action.SCROLLANDBOOLEAN)) {
				appLog.info("passed data in text box: " + institutionName);
				if(labelNames!=null && labelValue!=null) {
					for(int i=0; i<labelNames.length; i++) {
						WebElement ele = getInstitutionPageTextBoxOrRichTextBoxWebElement(environment, mode, labelNames[i].trim(), 30);
						if(sendKeys(driver, ele, labelValue[i], labelNames[i]+" text box", action.SCROLLANDBOOLEAN)) {
							appLog.info("passed value "+labelValue[i]+" in "+labelNames[i]+" field");


							if (labelNames[i].toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Institution.toString()) || labelNames[i].toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Entity.toString())) {

								ThreadSleep(1000);
								if (click(driver,
										FindElement(driver,"//*[@title='"+labelValue[i]+"']//strong[text()='"+labelValue[i].split(" ")[0]+"']",
												"Legal Name List", action.SCROLLANDBOOLEAN, 30),
										labelValue[i] + "   :   Legal Name", action.SCROLLANDBOOLEAN)) {
									appLog.info(labelValue[i] + "  is present in list.");
								} else {
									appLog.info(labelValue[i] + "  is not present in the list.");
									BaseLib.sa.assertTrue(false,labelValue[i] + "  is not present in the list.");
								}
							}

						}else {
							appLog.error("Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
							BaseLib.sa.assertTrue(false, "Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
						}
					}

				}
				if (click(driver, getCustomTabSaveBtn(projectName,30), "save button", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on save button");
					ThreadSleep(5000);
					//							String	xpath="//span[@class='custom-truncate uiOutputText'][text()='"+institutionName+"']";
					//							WebElement ele = FindElement(driver, xpath, "Header : "+institutionName, action.BOOLEAN, 30);
					WebElement ele = verifyCreatedItemOnPage(Header.Company, institutionName);
					if (ele != null) {
						appLog.info("created institution " + institutionName + " is verified successfully.");
						appLog.info(institutionName + " is created successfully.");

						if(labelNames!=null && labelValue!=null ) {
							for(int i=0; i<labelNames.length; i++) {
								//											
								if(fieldValueVerificationOnInstitutionPage(environment, mode, null, labelNames[i].replace("_", " ").trim(),labelValue[i])){
									appLog.info(labelNames[i]+" label value "+labelValue[i]+" is matched successfully.");
								}else {
									appLog.info(labelNames[i]+" label value "+labelValue[i]+" is not matched successfully.");
									BaseLib.sa.assertTrue(false, labelNames[i]+" label value "+labelValue[i]+" is not matched.");
								}

							}
						}
						return true;

					} else {
						appLog.error("Created institution " + institutionName + " is not visible");
					}
				} else {
					appLog.error("Not able to click on save button so cannot create institution: "
							+ institutionName);
				}
			} else {
				appLog.error("Not able to pass data in legal name text box so cannot create institution: "
						+ institutionName);
			}
		} else {
			appLog.error(
					"Not able to click on continue button so cannot create institution: " + institutionName);
		}


		return false;
	}


	public WebElement getDetailPageFieldLabel(String projectName,String fieldName,int timeOut){

		String xpath = "//*[@class='test-id__field-label'][text()='"+fieldName+"']";

		return isDisplayed(driver, FindElement(driver, xpath, fieldName, action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, fieldName);		
	}

	public boolean verifyValueOnFirm(String labelName,String value)
	{
		boolean flag=false;
		String xPath="";
		WebElement ele=null;
		xPath="//a[text()='Details']/ancestor::div[@class='slds-tabs_default']//span[text()='"+labelName+"']/parent::div/following-sibling::div";
		ele =FindElement(driver, xPath, "field Value", action.SCROLLANDBOOLEAN, 50);

		CommonLib.scrollDownThroughWebelementInCenter(driver, ele, labelName);
		String val=CommonLib.getText(driver, ele, "field Value", action.SCROLLANDBOOLEAN);

		if(val.trim().toLowerCase().contains(value.toLowerCase()))
		{
			log(LogStatus.INFO, value+" has been matched", YesNo.No);
			flag=true;
		}
		else
		{
			log(LogStatus.ERROR, value+" is not matched", YesNo.Yes);
		}


		return flag;

	}


	public boolean verifyValueOnFirm(ArrayList<String> labelName,ArrayList<String> value)
	{

		boolean flag=false;
		String xPath="";
		WebElement ele=null;
		int status=0;
		if(labelName.size()==value.size())
		{
			for(int i=0; i<labelName.size();i++)
			{
				xPath="//a[text()='Details']/ancestor::div[@class='slds-tabs_default']//span[text()='"+labelName.get(i)+"']/parent::div/following-sibling::div";
				ele =FindElement(driver, xPath, "field Value", action.SCROLLANDBOOLEAN, 50);

				CommonLib.scrollDownThroughWebelementInCenter(driver, ele, labelName.get(i));
				String val=CommonLib.getText(driver, ele, "field Value", action.SCROLLANDBOOLEAN);

				if(val.trim().toLowerCase().contains(value.get(i).toLowerCase()))
				{
					log(LogStatus.INFO, value.get(i)+" has been matched", YesNo.No);

				}
				else
				{

					log(LogStatus.ERROR, value.get(i)+" is not matched", YesNo.Yes);
					status++;
				}
			}
			if(status==0)
			{
				flag=true;
			}
		}
		else
		{
			log(LogStatus.ERROR, "the size of label name and value is not matched. Either label name or value is missing", YesNo.Yes);
		}


		return flag;

	}


}
