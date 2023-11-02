package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.getSelectedOptionOfDropDown;
import static com.navatar.generic.CommonLib.isDisplayed;
import static com.navatar.generic.CommonLib.scrollDownThroughWebelement;
import static com.navatar.generic.CommonLib.selectVisibleTextFromDropDown;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.CapitalCallPageFieldLabelText;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.model.Log;

public class CapitalCallsPageBusinessLayer extends CapitalCallsPage {

	public CapitalCallsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public boolean clickOnCreatedCapitalCall(String environment, String mode,String CCName) {
		
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
		int i=1;
		if (getSelectedOptionOfDropDown(driver, getViewDropdown(60), "View dropdown", "text").equalsIgnoreCase("All")) {
			if (click(driver, getGoButton(60), "Go button", action.BOOLEAN)) {

			}
			else {
				appLog.error("Go button not found");
				return false;
			}
		}
		else{
			if (selectVisibleTextFromDropDown(driver, getViewDropdown(60),"View dropdown","All") ){
			}
			else {
				appLog.error("All Funds not found in dropdown");
				return false;
			}

		}
	
			WebElement CC = getCCNameAtCCPage(CCName, 20);
			if (CC != null) {
				if (click(driver, CC, "CC/ID Name", action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on CC/ID name : " + CCName);
					return true;
					} 
			} else {
				while (true) {
					appLog.error("Capital Call is not Displaying on "+i+ " Page:" + CCName);
					if (click(driver, getNextImageonPage(10), "Fund Page Next Button",
							action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on Next Button");
						CC = getCCNameAtCCPage(CCName, 20);
						if (CC != null) {
							if (click(driver, CC, "Fund Name", action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on fund name : " + CCName);
								return true;
							}
						}
					} else {
						appLog.error("Fund Not Available : " + CCName);
						return false;
					}
					i++;
				}
			}
	}else{
		if(clickOnAlreadyCreated_Lighting(environment, mode, TabName.CapitalCalls, CCName, 30)){
			appLog.info("Clicked on fund name : " + CCName);
			return true;
		}else{
			appLog.error("Fund Not Available : " + CCName);	
		}
	}
			return false;
	}
	
	public boolean verifyDataOnCapitalCallsPageView(String environment, String mode,String capitalAmount, String ManagementFee, String OtherFee, String CallAmount, String callDate, String dueDate, String callAmountReceived, String receivedDate, String amountDue) {
		boolean flag = true;
		if(!fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls, CapitalCallPageFieldLabelText.CC_No.toString(), "$"+capitalAmount, EditViewMode.View)) {
			flag = false;
			appLog.error("capital amount value does not match");
		}
		else {
			appLog.info("capital amount value is correct");
		}
		if(!fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls, CapitalCallPageFieldLabelText.Management_Fee.toString(), "$"+ManagementFee, EditViewMode.View)) {
			flag = false;
		}
		if(!fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls, CapitalCallPageFieldLabelText.Other_Fee.toString(), "$"+OtherFee, EditViewMode.View)) {
			flag = false;
			appLog.error("other fee value does not match");
		}
		else {
			appLog.info("other fee value is found successfully correct");
		}
		if(!fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls, CapitalCallPageFieldLabelText.Call_Amount.toString(), "$"+CallAmount, EditViewMode.View)) {
			flag = false;
			appLog.error("call amount value does not match");
		}
		else {
			appLog.info("call amount value is correct");
		}
		if(!fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls, CapitalCallPageFieldLabelText.Call_Date.toString(), callDate, EditViewMode.View)) {
			flag = false;
			appLog.error("callDate value does not match");
		}
		else {
			appLog.info("callDate value is found successfully correct");
		}
		if(!fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls, CapitalCallPageFieldLabelText.Due_Date.toString(), dueDate, EditViewMode.View)) {
			flag = false;
			appLog.error("dueDate value does not match");
		}
		else {
			appLog.info("dueDate value is found successfully correct");
		}
		if(!fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls, CapitalCallPageFieldLabelText.Call_Amount_Received.toString(), "$"+callAmountReceived, EditViewMode.View)) {
			flag = false;
			appLog.error("callAmountReceived value does not match");
		}
		else {
			appLog.info("callAmountReceived value is found successfully correct");
		}
		if(!fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls, CapitalCallPageFieldLabelText.Received_Date.toString(), receivedDate, EditViewMode.View)) {
			flag = false;
			appLog.error("receivedDate value does not match");
		}
		else {
			appLog.info("receivedDate value is found successfully correct");
		}
		if(!fieldValueVerificationOnCapitalCalls(environment, mode, TabName.CapitalCalls, CapitalCallPageFieldLabelText.Amount_Due.toString(), "$"+amountDue, EditViewMode.View)) {
			flag = false;
			appLog.error("amountDue value does not match");
		}
		else {
			appLog.info("amountDue value is found successfully correct");
		}
		return flag;
	}
	
	public boolean fieldValueVerificationOnCapitalCalls(String environment, String mode, TabName tabName,
			String labelName,String labelValue, EditViewMode ev) {
		CommonLib.ThreadSleep(2000);
		try {
			String finalLabelName;
			if (labelName.equalsIgnoreCase(CapitalCallPageFieldLabelText.Capital_Amount.toString()) ||
				labelName.equalsIgnoreCase(CapitalCallPageFieldLabelText.Management_Fee.toString()) ||
				labelName.equalsIgnoreCase(CapitalCallPageFieldLabelText.Other_Fee.toString()) ||
				labelName.equalsIgnoreCase(CapitalCallPageFieldLabelText.Call_Amount.toString()) ||
				labelName.equalsIgnoreCase(CapitalCallPageFieldLabelText.Call_Amount_Received.toString())) {
				if (ev == EditViewMode.View)
				labelValue=convertNumberAccordingToFormatWithCurrencySymbol(labelValue, "0,000.000");
				else
					labelValue=convertNumberAccordingToFormatWithoutCurrencySymbol(labelValue, "0,000.000");
				
			}
			//if (labelName.equalsIgnoreCase(CapitalCallPageFieldLabelText.Amount_Due.toString()))
			//	labelValue = convertNumberAccordingToFormatWithCurrencySymbol(labelValue, "0.000");
			if (labelName.contains("_")) {
				finalLabelName = labelName.replace("_", " ");
			} else {
				finalLabelName = labelName;
			}
			String xpath = "";
			WebElement ele = null;
			if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
				if (ev == EditViewMode.View) {
					xpath = "//td[text()='" + finalLabelName + "']/../td[2]/div";
					if (finalLabelName.contains("Fund Drawdown")||finalLabelName.contains("Commitment")) {
						xpath = "//td[text()='"+finalLabelName+"']/following-sibling::td/div/a";
					} else {
						xpath = "//td[text()='" + finalLabelName + "']/../td[2]/div";
					}
				}
				else {//edit
					if (finalLabelName.contains("Fund Drawdown")||finalLabelName.contains("Commitment")||finalLabelName.contains("CC No")) {
						xpath = "//td[text()='"+finalLabelName+"']/../td[2]";
					}
					else {
						xpath = "//label[text()='"+finalLabelName+"']/../following-sibling::td//input";
					}
						
				}

			} else {//lightning
				if (ev == EditViewMode.View){
		
					////////////////////////////////////
					
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
					xpath = "//span[text()='"+finalLabelName+"']/../following-sibling::div//*[contains(text(),'"+labelValue+"')]";
				
			ele = FindElement(driver, xpath, finalLabelName + " label text with  " + labelValue, action.SCROLLANDBOOLEAN, 10);
			scrollDownThroughWebelement(driver, ele, finalLabelName + " label text with  " + labelValue);
			ele = 	isDisplayed(driver,ele,"Visibility", 10, finalLabelName + " label text with  " + labelValue);
			if (ele != null) {
				String aa = ele.getText().trim();
				appLog.info(finalLabelName + " label text with  " + labelValue+" verified");
				return true;

			} else {
				appLog.error("<<<<<<   "+finalLabelName + " label text with  " + labelValue+" not verified "+"   >>>>>>");
			}
			return false;
					
					
					////////////////////////////
				}
				else {//edit
					if (finalLabelName.contains("Call Date")|| finalLabelName.contains("Due Date")||finalLabelName.contains("Received Date")){
						xpath = "//*[text()='Capital Call Information']/../..//*[text()='"+finalLabelName+"']/following-sibling::div//input";
					System.err.println("finalLabelName: "+ finalLabelName);
					}else if (finalLabelName.contains("Capital Amount")||finalLabelName.contains("Management Fee")||finalLabelName.contains("Other Fee")||finalLabelName.contains("Call Amount Received"))
						xpath = "//*[text()='Capital Call Information']/../..//*[text()='"+finalLabelName+"']/following-sibling::div//input";
					else if(finalLabelName.contains("Drawdown")||finalLabelName.contains("Commitment") || finalLabelName.contains("CC No") || finalLabelName.contains("Call Amount"))
						xpath = "//*[text()='Capital Call Information']/../..//span[text()='"+finalLabelName+"']/../following-sibling::div//*[text()='"+labelValue+"']";
				}
				
			}
			ele = FindElement(driver, xpath, finalLabelName + " label text in " + mode, action.SCROLLANDBOOLEAN, 5);
			if (ele != null) {
				String aa = ele.getText().trim();
				if (aa.equals("")) {
					aa = CommonLib.getValueFromElementUsingJavaScript(driver,ele, finalLabelName);
				}
				appLog.info("Lable Value is: "+aa);
				if(aa.contains(labelValue)) {
					appLog.info(labelValue + " Value is matched successfully.");
					return true;
					
				}else {
					appLog.info(labelValue + " Value is not matched. Expected: "+labelValue+" /t Actual : "+aa);
				}
			} else {
				appLog.error(finalLabelName + " Value is not visible so cannot matched  label Value "+labelValue);
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			appLog.info("Inside Exception");
			WebElement  ele;
			String finalLabelName = labelName.replace("_", " ");

			if (labelValue.isEmpty() || labelValue.equals("")) {
			String	xpath = "//span[text()='"+finalLabelName+"']/../following-sibling::div//*";
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
			
			return false;
		}

	}

	public WebElement getCapitalCallPageTextBoxOrRichTextBoxWebElement(String environment,String mode, String labelName, int timeOut) {
		WebElement ele=null;
		String xpath ="",inputXpath="", dateXpath="",finalXpath="",finalLabelName="";
		if(labelName.contains("_")) {
			finalLabelName=labelName.replace("_", " ");
		}else {
			finalLabelName=labelName;
		}
		if(mode.equalsIgnoreCase(Mode.Classic.toString())) {
			xpath="//label[text()='"+finalLabelName+"']";
			inputXpath="/../following-sibling::td//input";
		
		}else {
			//span[text()='Description']/..//following-sibling::textarea
			//*[text()='Capital Call Information']/../..//*[text()='"+finalLabelName+"']/following-sibling::div//input
			
			xpath="//*[text()='Capital Call Information']/../..//*[text()='"+finalLabelName+"']/following-sibling::div//input";
			inputXpath="";
			dateXpath="";
		}
		
		if(mode.equals(Mode.Lightning.toString())) {
			System.err.println("label name"+ labelName);
			if (labelName.equalsIgnoreCase(CapitalCallPageFieldLabelText.Call_Date.toString()) || labelName.equalsIgnoreCase(CapitalCallPageFieldLabelText.Due_Date.toString()) || labelName.equalsIgnoreCase(CapitalCallPageFieldLabelText.Received_Date.toString()))
			finalXpath=xpath+dateXpath;
			else
				finalXpath=xpath+inputXpath;
		}else {//classic
			finalXpath=xpath+inputXpath;
		}
		ele=isDisplayed(driver, FindElement(driver, finalXpath, finalLabelName+" text box in "+mode, action.SCROLLANDBOOLEAN,30), "Visibility", timeOut, finalLabelName+"text box in "+mode);
		return ele;
		
	}
}
