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

	public boolean clickOnCreatedCommitmentId(String projectName,String commitmentID) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
		int i =1;
		if (click(driver, getGoButton(60), "go button", action.BOOLEAN)) {
			WebElement commitment = FindElement(driver,
					"//div[@class='x-panel-bwrap']//span[text()='" + commitmentID + "']", "Commitment ID",
					action.BOOLEAN, 20);
			if (commitment != null) {
				if (click(driver, commitment, "Commitment Id", action.SCROLLANDBOOLEAN)) {
					appLog.error("Clicked on Commitment ID successfully." + commitmentID);
					return true;
				} else {
					appLog.error("Not able to click on commitment ID." + commitmentID);
				}
			} else {
				while (true) {
					appLog.error("Commitment is not Displaying on "+i+ " Page: " + commitmentID);
					if (click(driver, getNextImageonPage(10), "Commitment Page Next Button",
							action.SCROLLANDBOOLEAN)) {

						appLog.info("Clicked on Next Button");
						commitment = FindElement(driver,
					"//div[@class='x-panel-bwrap']//span[text()='" + commitmentID + "']", "Commitment ID",
					action.BOOLEAN, 60);
						if (commitment != null) {
							if (click(driver, commitment, commitmentID, action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on Commitment name : " + commitmentID);
								return true;
								
							}
						}

						

					} else {
						appLog.error("Commitment Not Available : " + commitmentID);
						return false;
					}
					i++;
				}
			}
		} else {
			appLog.error("Not able to click on go button so cannot click on commitment ID");
		}
		}else{
			if(clickOnAlreadyCreatedItem(projectName, TabName.Object7Tab, commitmentID, 30)){
				appLog.info("Clicked on Commitment name : " + commitmentID);
				return true;
			}else{
				appLog.error("Commitment Not Available : " + commitmentID);	
			}
		}
		return false;
	}
	
	public boolean FieldValueVerificationOnCommitmentPage(String environment, String mode,String labelName,String labelValue) {
		String xpath = "";
		WebElement ele = null;
		if(labelName.toString().equalsIgnoreCase(excelLabel.Commitment_Amount.toString())) {
			labelValue=convertNumberAccordingToFormatWithCurrencySymbol(labelValue, "0,000.0");
		}
		else if(labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Total_Amount_Called.toString()) || 
				labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Total_Amount_Received.toString()) ||
				labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Total_Distributions.toString()) || 
				labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Capital_Returned_Recallable.toString())
				) {
			labelValue=convertNumberAccordingToFormatWithCurrencySymbol(labelValue, "0,000.000");
			
		}
		else if(labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Capital_Returned_NonRecallable.toString()) )
			labelValue=convertNumberAccordingToFormatWithCurrencySymbol(labelValue, "0.000");
		
		else if(
			labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Total_Commitment_Due.toString()))
			labelValue=convertNumberAccordingToFormatWithCurrencySymbol(labelValue, "0.00");
			
		
		else if(labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Commitment_Called.toString())||
				labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Called_Due.toString())){
			
			labelValue=convertNumberAccordingToFormatWithoutCurrencySymbol(labelValue, "0.00")+"%";
		}
			else if(labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Total_Uncalled_Amount.toString()))
				labelValue=convertNumberAccordingToFormatWithCurrencySymbol(labelValue, "0,000.00");
			
		labelName=labelName.replace("_", " ");
		if(labelName.contains(excelLabel.Placement_Fee.toString().replace("_", " "))) {
			labelName=labelName+" %";
			labelValue=labelValue+"%";
		}

		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			
			xpath = "//td[text()='"+ labelName +"']/../td[2]/div";
			if (labelName.equalsIgnoreCase("Partner Type")||labelName.equalsIgnoreCase("Final Commitment Date"))
				xpath = "//td[text()='"+labelName+"']/following-sibling::td/div";
			
			else if(labelName.equalsIgnoreCase("Total Distributions") ||
					labelName.equalsIgnoreCase("Capital Returned (Recallable)") ||
					labelName.equalsIgnoreCase("Capital Returned (Non-Recallable)"))
				xpath = "//td[text()='"+labelName+"']/../td[4]/div";
		}
		
		else {
			if (labelName.equalsIgnoreCase("Limited Partner") || labelName.equalsIgnoreCase("Partnership"))
			
			/////////////////  Lighting New Start /////////////////////////////////////
		
			
			if (labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Commitment_Called.toString()) ||
					labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Called_Due.toString())) {
				xpath = "//span[text()='"+labelName+"']/../following-sibling::div/span/span";	
			} else {
				xpath = "//span[text()='"+labelName+"']/../following-sibling::div//*[text()='"+labelValue+"']";
			}
			xpath = "//span[text()='"+labelName+"']/../following-sibling::div//*[text()='"+labelValue+"']";
		ele = FindElement(driver, xpath, labelName + " label text with  " + labelValue, action.SCROLLANDBOOLEAN, 10);
		scrollDownThroughWebelement(driver, ele, labelName + " label text with  " + labelValue);
		ele = 	isDisplayed(driver,ele,"Visibility", 5, labelName + " label text with  " + labelValue);
		if (ele != null) {
			String aa = ele.getText().trim();
			
			if (labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Commitment_Called.toString()) ||
					labelName.toString().equalsIgnoreCase(CommitmentPageFieldLabelText.Called_Due.toString())) {
				if (aa.contains(labelValue)) {
					appLog.info(labelName + " label text with  " + labelValue+" verified");
				} else {
				
					appLog.error("<<<<<<   "+labelName + " label text with  " + labelValue+" not verified "+"   >>>>>> Actual : "+aa);
					return false;
				}
				appLog.info(labelName + " label text with  " + labelValue+" verified");
				
			}
			
			appLog.info(labelName + " label text with  " + labelValue+" verified");
			return true;

		} else {
			appLog.error("<<<<<<   "+labelName + " label text with  " + labelValue+" not verified "+"   >>>>>>");
		}
		return false;
		

		/////////////////  Lighting New End /////////////////////////////////////
	}
		
	
		
			ele = isDisplayed(driver,
				FindElement(driver, xpath, labelName + " label text in " + mode, action.SCROLLANDBOOLEAN, 60),
				"Visibility", 30, labelName + " label text in " + mode);
		
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

	public WebElement getCommitmentCancelBtn(TopOrBottom topOrBottom,int timeOut){
		
		WebElement ele=null;
		String xpath=null;

			
			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//a[text()='Create Commitment']/preceding-sibling::a[text()='Cancel'])[1]";
			} else {
				xpath = "(//a[text()='Create Commitment']/preceding-sibling::a[text()='Cancel'])[2]";
			}
		
		ele = FindElement(driver, xpath, "Step 3 Cancel Btn : "+topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
		return ele;
		

		
	}
	
	public static String convertNumberAccordingToFormatWithoutCurrencySymbol(String number,String format){

		double d = Double.parseDouble(number);
		DecimalFormat myFormatter = new DecimalFormat(format);
		String output = myFormatter.format(d);
		System.err.println(" outpurttt >>>> "+output);
		return output;

	}
}
