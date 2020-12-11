package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;

import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;

public class FundsPageBusinessLayer extends FundsPage {

	public FundsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @param fundName
	 * @param fundType
	 * @param investmentCategory
	 * @param otherLabelFields
	 * @param otherLabelValues
	 * @return true/false
	 * @description this method is used to create new fund
	 */
	public boolean createFund(String projectName, String fundName, String fundType,
			String investmentCategory, String otherLabelFields,String otherLabelValues) {
		String labelNames[]=null;
		String labelValue[]=null;
		if(otherLabelFields!=null && otherLabelValues !=null) {
			labelNames= otherLabelFields.split(",");
			labelValue=otherLabelValues.split(",");
		}
		
	
			refresh(driver);
			ThreadSleep(10000);
			if(clickUsingJavaScript(driver, getNewButton(projectName, 60), "new button")) {
				appLog.info("clicked on new button");
			}else {
				appLog.error("Not able to click on new button so cannot create fund : "+fundName);
				return false;
			}
		
//		if (click(driver, getNewButton(environment, mode, 60), "New Button", action.BOOLEAN)) {
			ThreadSleep(500);
			if (sendKeys(driver, getFundName(projectName, 60), fundName, "Fund Name", action.BOOLEAN)) {
				ThreadSleep(500);
		
					if (click(driver, getFundType(projectName, 60), "Fund Type ", action.SCROLLANDBOOLEAN)) {
						WebElement fundTypeEle = FindElement(driver,
								"(//div[@class='select-options'])[2]//a[@title='" + fundType + "']", fundType,
								action.SCROLLANDBOOLEAN, 10);
						ThreadSleep(500);
						if (click(driver, fundTypeEle, fundType, action.SCROLLANDBOOLEAN)) {

						} else {
							appLog.error("Not able to Select Fund Type");
						}
					} else {
						appLog.error("Not able to Click on Fund Type");
					}

					if (click(driver, getInvestmentCategory(projectName, 60), "Investment Category",
							action.SCROLLANDBOOLEAN)) {
						WebElement InvsCatgEle = FindElement(driver,
								"(//div[@class='select-options'])[2]//a[@title='" + investmentCategory + "']",
								investmentCategory, action.SCROLLANDBOOLEAN, 10);
						ThreadSleep(500);
						if (click(driver, InvsCatgEle, investmentCategory, action.SCROLLANDBOOLEAN)) {
						
						} else {
							appLog.error("Not able to select Investment Category");
						}
					} else {
						appLog.error("Not able to Click on Investment Category");
					}
				
				if(labelNames!=null && labelValue!=null) {
					for(int i=0; i<labelNames.length; i++) {
						WebElement ele = getFundtPageTextBoxOrRichTextBoxWebElement(projectName, labelNames[i].trim(), 30);
						if(sendKeys(driver, ele, labelValue[i], labelNames[i]+" text box", action.SCROLLANDBOOLEAN)) {
							appLog.info("passed value "+labelValue[i]+" in "+labelNames[i]+" field");
						}else {
							appLog.error("Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
							BaseLib.sa.assertTrue(false, "Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
						}
					}
					
				}
				if (click(driver, getSaveButton(60), "Save Button", action.BOOLEAN)) {
					ThreadSleep(500);
					if (getFundNameInViewMode(projectName, 60) != null) {
						ThreadSleep(2000);
						String fundNameViewMode = getText(driver, getFundNameInViewMode(projectName, 60),
								"Fund Name", action.BOOLEAN);
						if (fundNameViewMode.contains(fundName)) {
							appLog.info("Fund is created successfully.:" + fundName);
							if(labelNames!=null && labelValue!=null ) {
								for(int i=0; i<labelNames.length; i++) {
									if(FieldValueVerificationOnFundPage(projectName, labelNames[i].replace("_", " ").trim(),labelValue[i])){
										appLog.info(labelNames[i]+" label value "+labelValue[i]+" is matched successfully.");
									}else {
										appLog.info(labelNames[i]+" label value "+labelValue[i]+" is not matched successfully.");
										BaseLib.sa.assertTrue(false, labelNames[i]+" label value "+labelValue[i]+" is not matched.");
									}
								}
							}
							return true;
						} else {
							appLog.error("Fund is not created successfully.:" + fundName);
						}
					} else {
						appLog.error("Not able to find Fund Name in View Mode");
					}
				} else {
					appLog.error("Not able to click on save button");
				}

			} else {
				appLog.error("Not able to enter fund name in text box");
			}
//		} else {
//			appLog.info("Not able to click on new button so we cannot create fund");
//		}
		return false;
	}

	/**
	 * @author Akul Bhutani
	 * @param environment
	 * @param mode
	 * @param tabName
	 * @param labelName
	 * @param labelValue
	 * @return true/false
	 * @description this method is used to verify fields present on fund page
	 */
	public boolean FieldValueVerificationOnFundPage(String projectName,
			String labelName,String labelValue) {
		String xpath = "";
		WebElement ele = null;
		if(labelName.contains(excelLabel.Target_Commitments.toString().replaceAll("_", " "))) {
			labelName=FundPageFieldLabelText.Target_Commitments.toString();
		}
		
			
			xpath = "//span[@class='test-id__field-label'][text()='" + labelName
					+ "']/../following-sibling::div//lightning-formatted-text";
		
		ele = isDisplayed(driver,
				FindElement(driver, xpath, labelName + " label text in " + projectName, action.SCROLLANDBOOLEAN, 60),
				"Visibility", 30, labelName + " label text in " + projectName);
		if (ele != null) {
			String aa = ele.getText().trim();
			appLog.info("Lable Value is: "+aa);
			if(labelName.contains("Date")) {
				if(verifyDate(aa,null, labelName)) {
					appLog.info("Dtae is verified Successfully");
					return true;
				}else {
					appLog.error(labelName+ " Date is not verified. /t Actual Result: "+aa);
				}
			}else {
				if(aa.contains(labelValue)) {
					appLog.info(labelValue + " Value is matched successfully.");
					return true;
					
				}else {
					appLog.error(labelValue + " Value is not matched. Expected: "+labelValue+" /t Actual : "+aa);
				}
			}
		} else {
			appLog.error(labelName + " Value is not visible so cannot matched  label Value "+labelValue);
		}
		return false;

	}

	/////////////////////////////////////////////////// Activity Association //////////////////////////////////////////////
	
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param fundName
	 * @param recordType TODO
	 * @param fundType
	 * @param investmentCategory
	 * @param labelswithValues
	 * @param timeOut
	 * @return true/false
	 * @description this method is used to create single fund in PE
	 */
	public boolean createFundPE(String projectName, String fundName, String recordType,String fundType, String investmentCategory,String[][] labelswithValues, int timeOut) {
		boolean flag=false;
		WebElement ele = null;
		String xpath="";
		refresh(driver);
		ThreadSleep(10000);
		if(clickUsingJavaScript(driver, getNewButton(projectName, 60), "new button")) {
			appLog.info("clicked on new button");
			
			if (!recordType.equals("") || !recordType.isEmpty()) {
				ThreadSleep(2000);
				if(click(driver, getRadioButtonforRecordType(recordType, 5), "Radio Button for : "+recordType, action.SCROLLANDBOOLEAN)){
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

			ThreadSleep(500);
			ele =getLabelTextBox(projectName, PageName.FundsPage.toString(), PageLabel.Fund_Name.toString(), timeOut);
			if (sendKeys(driver, ele, fundName, "Fund Name", action.BOOLEAN)) {
				ThreadSleep(500);
				appLog.info("Enter value on Fund Name Text Box : "+fundName);
				if (click(driver, getFundType(projectName, 60), "Fund Type ", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on Fund Type");
					xpath="//span[@title='"+fundType+"']/../..";
					WebElement fundTypeEle = FindElement(driver,xpath, fundType,action.SCROLLANDBOOLEAN, 10);
					ThreadSleep(500);
					if (click(driver, fundTypeEle, fundType, action.SCROLLANDBOOLEAN)) {
						appLog.info("Select Fund Type : "+fundType);

						if (click(driver, getInvestmentCategory(projectName, 60), "Investment Category",
								action.SCROLLANDBOOLEAN)) {
							appLog.info("clicked on Investment category");
							xpath="//label[text()='Investment Category']/following-sibling::div//span[@title='"+investmentCategory+"']/../..";
							WebElement InvsCatgEle = FindElement(driver,xpath,investmentCategory, action.SCROLLANDBOOLEAN, 10);
							ThreadSleep(500);
							if (click(driver, InvsCatgEle, investmentCategory, action.SCROLLANDBOOLEAN)) {
								appLog.info("Select Investment Category : "+investmentCategory);

								if (click(driver, getCustomTabSaveBtn(projectName,60), "Save Button", action.BOOLEAN)) {
									ThreadSleep(500);
									appLog.info("click on save button");
									if (getFundNameInViewMode(projectName, 60) != null) {
										ThreadSleep(2000);
										String fundNameViewMode = getText(driver, getFundNameInViewMode(projectName, 10),
												"Fund Name", action.BOOLEAN);
										appLog.info("fundNameViewMode : "+fundNameViewMode);
										xpath="//div//h1/div/..//*[text()='"+fundName+"']";
										ele = FindElement(driver, xpath, "Heeder : "+fundName, action.BOOLEAN, 30);
										if (ele!=null) {
											appLog.info("Fund is created and verified.:" + fundName);
											flag=true;
										} else {
											appLog.error("Fund is not created but not verified.:" + fundName);
										}
									} else {
										appLog.error("Not able to find Fund Name in View Mode");
									}
								} else {
									appLog.error("Not able to click on save button");
								}

							} else {
								appLog.error("Not able to select Investment Category");
							}
						} else {
							appLog.error("Not able to Click on Investment Category");
						}

					} else {
						appLog.error("Not able to Select Fund Type");
					}
				} else {
					appLog.error("Not able to Click on Fund Type");
				}
			} else {
				appLog.error("Not able to enter fund name in text box");
			}
		}else {
			appLog.error("Not able to click on new button so cannot create fund : "+fundName);
		}

		return flag;
	}

	/**@author Akul Bhutani
	 * @param projectName
	 * @param dealType
	 * @param dealName
	 * @param status
	 * @param stage
	 * @param labelswithValues
	 * @param timeOut
	 * @return true/false
	 * @description this is used to create a deal in MNA
	 */
	public boolean createDealMNA(String projectName,String dealType, String dealName,String status,String stage,String[][] labelswithValues,int timeOut) {
		WebElement ele;
		boolean flag = false;
		String xpath="";
//		status="Prospect";
//		stage="Prospect";
//		dealType="Sell-side Deal";
		ThreadSleep(2000);
		if(click(driver, getNewButton(projectName,timeOut), "New Button", action.BOOLEAN)) {
			appLog.info("Clicked on New Button");	
			ThreadSleep(1000);
				ele=getRadioButtonforRecordType(dealType, timeOut);
				//ele = getDealType(projectName,dealType,timeOut);
				if (click(driver, ele, dealType, action.SCROLLANDBOOLEAN)) {
					appLog.info(" Selected Deal type : "+dealType);	
					

					ThreadSleep(1000);
					if (click(driver, getContinueOrNextButton(projectName,timeOut), "Continue Button", action.BOOLEAN)) {
						appLog.info("Clicked on Continue or Nxt Button");	
						ThreadSleep(1000);
						
						ele = getLabelTextBox(projectName, PageName.DealPage.toString(), PageLabel.Deal_Name.toString(), timeOut);
						if (sendKeys(driver,ele, dealName, "Deal Name", action.BOOLEAN)) {
							appLog.info("Successfully Entered value on Deal Name TextBox : "+dealName);		
							ThreadSleep(1000);
							
							if (click(driver, getDealStatus(projectName, timeOut), "Deal Status : "+status, action.SCROLLANDBOOLEAN)) {
									ThreadSleep(2000);
									appLog.error("Clicked on Deal Status");
									
									xpath="//div[@class='select-options']//li/a[@title='"+status+"']";
									WebElement dealStatusEle = FindElement(driver,xpath, status,action.SCROLLANDBOOLEAN, timeOut);
									ThreadSleep(2000);
									if (click(driver, dealStatusEle, status, action.SCROLLANDBOOLEAN)) {
										appLog.info("Selected Deal Status : "+status);
										ThreadSleep(2000);

										if (click(driver, getDealStage(projectName, timeOut), "Deal Status : "+stage, action.SCROLLANDBOOLEAN)) {
											ThreadSleep(2000);
											appLog.error("Clicked on Deal stage");
											
											xpath="//div[@class='select-options']//li/a[@title='"+stage+"']";
											WebElement dealStageEle = FindElement(driver,xpath, stage,action.SCROLLANDBOOLEAN, timeOut);
											ThreadSleep(2000);
											if (click(driver, dealStageEle, stage, action.SCROLLANDBOOLEAN)) {
												appLog.info("Selected Deal stage : "+stage);
											} else {
												appLog.error("Not able to Select on Deal stage : "+stage);
											}
											
										} else {
											appLog.error("Not able to Click on Deal stage : ");
										}
										
										if (click(driver, getSaveButton(projectName,30), "Save Button", action.SCROLLANDBOOLEAN)) {
											appLog.error("Click on save Button");	
											flag = true;
										}else{
											appLog.error("Not Able to Click on save Button");	
										}

									} else {
										appLog.error("Not able to Select on Deal Status : "+status);
									}
								} else {
									appLog.error("Not able to Click on Deal Status : ");
								}


						} else {
							appLog.error("Not Able to Entered value on Deal Name TextBox : "+dealName);	
						}
					}else{
						appLog.error("Not Able to Click on Continue or Nxt Button");	
					}
					
				}else{
					appLog.error("Not Able to Select Deal type : "+dealType);	
				}
		

			
			
		}else {
			appLog.error("Not Able to Click on New Button");	
		}

	
		return flag;
	}
	
	
}
