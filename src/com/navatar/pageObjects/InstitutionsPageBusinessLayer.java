package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.navatar.generic.AppListeners.*;

public class InstitutionsPageBusinessLayer extends InstitutionsPage {

	public InstitutionsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	///////////////////////////////////////////////////////  Activity Association ///////////////////////////////////////////////////////////////////////////
	
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param institutionName
	 * @param recordType
	 * @param labelsWithValues
	 * @param timeOut
	 * @return true/false
	 * @description this method is used to create single entity if pe and account if mna
	 */
	public boolean createEntityOrAccount(String projectName,String institutionName,String recordType, String[][] labelsWithValues,int timeOut) {
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
					if (click(driver, getSaveButton(projectName,timeOut), "save button", action.SCROLLANDBOOLEAN)) {
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
				
			}else {
				appLog.error("Not able to click on New Button so cannot create institution: " + institutionName);
			
			}
		
		
		
		return flag;
	}
	
	public WebElement toggleButton(String projectName,PageName pageName,String btnName,action action,int timeOut) {
		String xpath = "//button[@title='"+btnName+"']";
		WebElement ele = FindElement(driver, xpath,"Toggle Button : "+btnName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btnName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btnName);
		return ele;
	}
	
	public WebElement toggleSDGButtons(String projectName,PageName pageName,String toggleTab,ToggleButtonGroup btnName,action action,int timeOut) {
		String btname = btnName.toString();
		String xpath = "//*[text()='"+toggleTab+"']/../../..//following-sibling::div//button[@title='"+btname+"']";
		WebElement ele = FindElement(driver, xpath,toggleTab+" >> "+btname, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btname);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btname);
		return ele;
	}
	
	
	public WebElement getFundNameAtToggle(String projectName,PageName pageName,String fundName,action action,int timeOut) {
		String xpath = "//*[@data-label='Fund: ']//*[text()='"+fundName+"']";
		WebElement ele = FindElement(driver, xpath,fundName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Fund Name : "+fundName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Fund Name : "+fundName);
		return ele;
	}
	
	
	public WebElement getFundNameAtToggleToolTip(String projectName,PageName pageName,String fundName,action action,int timeOut) {
		String xpath = "//*[@data-label='Fund: ']//*[text()='"+fundName+"']/..";
		WebElement ele = FindElement(driver, xpath,fundName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Fund Name : "+fundName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Fund Name : "+fundName);
		return ele;
	}
	
	public WebElement getLegalEntityAtToggle(String projectName,PageName pageName,String entityName,action action,int timeOut) {
		String xpath = "//*[@data-label='Legal Entity: ']//*[text()='"+entityName+"']";
		WebElement ele = FindElement(driver, xpath,entityName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Fund Name : "+entityName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Fund Name : "+entityName);
		return ele;
	}
	
	public WebElement getInlineOrLockedAtToggle(String projectName,PageName pageName,String name,action action,int timeOut) {
		String xpath = "//a[text()='Fund Investments']/../../../../../..//*[@title='"+name+"']/../following-sibling::span/button";
		WebElement ele = FindElement(driver, xpath,name, action, timeOut);
		scrollDownThroughWebelement(driver, ele, name);
		return ele;
	}
	
	
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
									

									if (mode.equalsIgnoreCase(Mode.Lightning.toString()) && labelNames[i].toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Institution.toString())) {
										
										ThreadSleep(1000);
										if (click(driver,
												FindElement(driver,
														"//div[contains(@class,'uiAutocomplete')]//a//div//div[contains(@class,'primary') and @title='"+labelValue[i]+"']",
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
						if (click(driver, getSaveButton(projectName,30), "save button", action.SCROLLANDBOOLEAN)) {
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
			
			ele = 		FindElement(driver, xpath, finalLabelName + " label text with  " + labelValue, action.SCROLLANDBOOLEAN, 10);
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
	
	public boolean changeStatus(String projectName,String status) {
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		boolean flag=true;
		status=status.replace("_", " ");
		if (clickOnShowMoreActionDownArrow(projectName, PageName.Object1Page, ShowMoreActionDropDownList.Edit, 10)) {
			if (click(driver, fp.getDealStatus(projectName, 10), "Status : "+status, action.SCROLLANDBOOLEAN)) {
				ThreadSleep(2000);
				appLog.error("Clicked on Deal Status");
				
				String xpath="//div[@class='select-options']//li/a[@title='"+status+"']";
				WebElement dealStatusEle = FindElement(driver,xpath, status,action.SCROLLANDBOOLEAN, 10);
				ThreadSleep(2000);
				if (click(driver, dealStatusEle, status, action.SCROLLANDBOOLEAN)) {
					appLog.info("Selected Status : "+status);
					ThreadSleep(2000);
				} else {
					log(LogStatus.ERROR,"Not able to Select on Status : "+status,YesNo.No);
					flag=false;
				}
				if (click(driver, getSaveButton(projectName,10), "save button", action.SCROLLANDBOOLEAN)) {
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
}
