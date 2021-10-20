package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
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
import static com.navatar.generic.CommonVariables.M4Ins1;

public class ContactsPageBusinessLayer extends ContactsPage implements ContactPageErrorMessage {

	public ContactsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	

	/**@author Azhar Alam
	 * @param projectName
	 * @param tabName
	 * @param labelName
	 * @param labelValue
	 * @return true/false
	 * @description verify all fields present on contact page
	 */
	public boolean fieldValueVerificationOnContactPage(String projectName, TabName tabName,String labelName,String labelValue) {
		String finalLabelName="";


		if (labelName.contains("_")) {
			if(labelName.equalsIgnoreCase(excelLabel.Asst_Phone.toString())) {
				finalLabelName= IndiviualInvestorFieldLabel.Asst_Phone.toString();
			}else {
				finalLabelName = labelName.replace("_", " ");
			}
		}else if (labelName.equalsIgnoreCase("Profile_Image")) {
			finalLabelName = labelName;
		}
		else {
			finalLabelName = labelName;
		}
		String xpath = "";
		WebElement ele = null;

			xpath = "//span[@class='test-id__field-label'][text()='" + finalLabelName
					+ "']/../following-sibling::div/span";

		
		if(finalLabelName.contains("Street") || finalLabelName.contains("City") || finalLabelName.contains("State") || finalLabelName.contains("Postal") || finalLabelName.contains("ZIP") || finalLabelName.contains("Zip")|| finalLabelName.contains("Country")) {

			
				//	xpath="//span[text()='Address Information']/../../following-sibling::div";
				if(finalLabelName.contains("Legal Name")){
					xpath="("+xpath+")[2]";
				}else if(finalLabelName.contains("Other Street") || finalLabelName.contains("Other City") || finalLabelName.contains("Other State") || finalLabelName.contains("Other Zip") || finalLabelName.contains("Other Country")) {
					xpath="//span[text()='Other Address']/../following-sibling::div";	
				}else{
					xpath="//span[text()='Mailing Address']/../following-sibling::div";
				}
			
		}else if(finalLabelName.contains(excelLabel.Phone.toString())) {
			xpath = "//*[@class='test-id__field-label'][starts-with(text(),'"+finalLabelName+"')]/../following-sibling::div/*//a";
		}
		/*if(labelName.equalsIgnoreCase(excelLabel.Region.toString()) || labelName.equalsIgnoreCase(excelLabel.Industry.toString())) {
			xpath = "//span[@class='test-id__field-label'][text()='" + finalLabelName
					+ "']/../following-sibling::div/span//a";

		}*/
		if(labelName.equalsIgnoreCase(excelLabel.Phone.toString())) {
			xpath = "//span[@class='test-id__field-label'][contains(text(),'" + finalLabelName+ "')]/../following-sibling::div/span//a";

		}
		//span[@class='test-id__field-label'][contains(text(),'Phone')]/../following-sibling::div/span//a
		ele = isDisplayed(driver,
				FindElement(driver, xpath, finalLabelName + " label text in " + projectName, action.SCROLLANDBOOLEAN, 5),
				"Visibility", 5, finalLabelName + " label text in " + projectName);
		if (ele != null) {
			String aa = ele.getText().trim();
			appLog.info("<<<<<<<<     "+finalLabelName+ " : Lable Value is: "+aa+"      >>>>>>>>>>>");

			if (aa.isEmpty()) {
				appLog.error(finalLabelName + " Value is Empty label Value "+labelValue);
				return false;
			}

			if (labelName.equalsIgnoreCase(excelLabel.Phone.toString()) || labelName.equalsIgnoreCase(excelLabel.Fax.toString())||
					labelName.equalsIgnoreCase(ContactPageFieldLabelText.Mobile.toString()) ||
					labelName.equalsIgnoreCase(excelLabel.Asst_Phone.toString())) {

				if(aa.contains(labelValue) || aa.contains(changeNumberIntoUSFormat(labelValue))) {
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
	
	//////////////////////////////////////////////////////////////  Activity Association /////////////////////////////////////
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param contactFirstName
	 * @param contactLastName
	 * @param legalName
	 * @param emailID
	 * @param recordType TODO
	 * @param otherLabelFields
	 * @param otherLabelValues
	 * @param creationPage
	 * @param title TODO
	 * @param tier TODO
	 * @return true/false
	 * @description This is used to create new contact with given arguments
	 */
	public boolean createContact(String projectName, String contactFirstName, String contactLastName,
			String legalName, String emailID, String recordType,String otherLabelFields,String otherLabelValues, CreationPage creationPage, String title, String tier) {
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		String labelNames[]=null;
		String labelValue[]=null;
		if(otherLabelFields!=null && otherLabelValues !=null) {
			labelNames= otherLabelFields.split(",");
			labelValue=otherLabelValues.split(",");
		}
		if(creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {
			
				
				if(ClickonRelatedTab_Lighting(projectName, RecordType.Contact)) {
					appLog.info("clicked on related list tab");
				}else {
					appLog.error("Not able to click on related list tab so cannot create contact: "+contactFirstName+" "+contactLastName);
					return false;
				}
		
			if(click(driver, ins.getNewContactBtn(projectName, 30), "new contact button in "+projectName, action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on new contact button in institution page");
			}else {
				appLog.error("Not able to click on new button on institution page so cannot create contact: "+contactFirstName+" "+contactLastName);
				return false;
			}
		}else {
			refresh(driver);
			ThreadSleep(3000);
				ThreadSleep(5000);
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
					
				}else {
					appLog.error("Not able to click on New Button so cannot create Contact: " + contactFirstName+" "+contactLastName);
					return false;
				}
			}
		WebElement ele=null;
			ThreadSleep(2000);
			if (sendKeys(driver, getContactFirstName(projectName, 60), contactFirstName, "Contact first Name",
					action.BOOLEAN)) {
				if (sendKeys(driver, getContactLastName(projectName, 60), contactLastName, "Contact Last Name",
						action.BOOLEAN)) {
					
					if(creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {
						
					}else {
						if (sendKeys(driver, getLegalName(projectName, 60), legalName, "Account Name",
								action.SCROLLANDBOOLEAN)) {
								ThreadSleep(1000);
								if (click(driver,
										FindElement(driver,
												"//*[@title='"+legalName+"']//strong[text()='"+legalName.split(" ")[0]+"']",
												"Legal Name List", action.THROWEXCEPTION, 30),
										legalName + "   :   Account Name", action.BOOLEAN)) {
									appLog.info(legalName + "  is present in list.");
								} else {
									appLog.info(legalName + "  is not present in the list.");
									return false;
								}
							
						} else {
							appLog.error("Not able to enter legal name");
							return false;
						}
					}
					ele = getLabelTextBox(projectName, PageName.Object2Page.toString(),PageLabel.Email.toString(), 10);
					
						if (sendKeys(driver,ele, emailID, "Email ID",
								action.SCROLLANDBOOLEAN)) {
							if(labelNames!=null && labelValue!=null) {
								for(int i=0; i<labelNames.length; i++) {
									ele = getContactPageTextBoxOrRichTextBoxWebElement(projectName, labelNames[i].trim(), 30);
									if(sendKeysAndPressEnter(driver, ele, labelValue[i], labelNames[i]+" text box", action.SCROLLANDBOOLEAN)) {
										appLog.info("passed value "+labelValue[i]+" in "+labelNames[i]+" field");
									}else {
										appLog.error("Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
										BaseLib.sa.assertTrue(false, "Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
									}
									if (labelNames[i].equalsIgnoreCase(excelLabel.Region.toString()) || labelNames[i].equalsIgnoreCase(excelLabel.Sector.toString())) {
										if (click(driver, FindElement(driver,
												"//*[text()='"+labelNames[i]+"']/following-sibling::div[@class='slds-form-element__control']//input[@type='text']", 
												"picklist "+labelNames[i], action.SCROLLANDBOOLEAN , 10), "picklist "+labelNames[i],  action.SCROLLANDBOOLEAN)) {
											
										
										if (click(driver,
												FindElement(driver,"//div[contains(@class,'listbox')]//*[@data-value='"+labelValue[i]+"']"
														,
														"Legal Name List", action.THROWEXCEPTION, 30),
												labelNames[i] + "   :   Account Name", action.BOOLEAN)) {
											appLog.info(labelNames[i] + "  is present in list.");
										} else {
											appLog.error("Not able to select "+labelValue[i]+" in "+labelNames[i]+" field");
											BaseLib.sa.assertTrue(false, "Not able to select "+labelValue[i]+" in "+labelNames[i]+" field");
										}
										}else {
											appLog.error("Not able to select "+labelValue[i]+" in "+labelNames[i]+" field");
											BaseLib.sa.assertTrue(false, "Not able to select "+labelValue[i]+" in "+labelNames[i]+" field");
										}
									}
								}
								
							}
							if (title!=null) {
							if (sendKeys(driver, getcontactTitle(projectName, 10), title, "title", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "passed value "+title+" to title", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not pass value "+title+" to title", YesNo.No);
								BaseLib.sa.assertTrue(false,"could not pass value "+title+" to title" );
							}
							}
							if(tier!=null){
								
								if(click(driver, getcontactTier(projectName, 30), "contact tier dropdown", action.SCROLLANDBOOLEAN)){
									log(LogStatus.INFO, "clicked contact tier dropdown", YesNo.No);
									ThreadSleep(2000);
									String xpath="//*[text()='Tier']/..//input/../following-sibling::div//span[@title='"+tier+"']/../..";
									WebElement dropdownValue=FindElement(driver, xpath, "", action.BOOLEAN, 30);
									if(click(driver, dropdownValue, "", action.BOOLEAN)){
										log(LogStatus.INFO, "Selected tier :"+tier+" value in teir dropdown", YesNo.No);
										
										
									}else{
										log(LogStatus.INFO, "Not able to Select tier :"+tier+" value in teir dropdown", YesNo.Yes);
										BaseLib.sa.assertTrue(false,"Not able to Select tier :"+tier+" value in teir dropdown" );
									}
									
								}else{
									log(LogStatus.INFO, "Not able to clicked contact tier dropdown", YesNo.Yes);
									BaseLib.sa.assertTrue(false,"Not able to clicked contact tier dropdown" );
									
								}
							}
							if (click(driver, getNavigationTabSaveBtn(projectName, 60), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on save button");
								ThreadSleep(3000);
								if (getNavigationTabSaveBtn(projectName, 5)!=null) {
									click(driver, getNavigationTabSaveBtn(projectName, 60), "save", action.BOOLEAN);
								}
								if(creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {
										if(clickOnGridSection_Lightning(projectName,RelatedList.Contacts, 30)) {
											ele = isDisplayed(driver, FindElement(driver, "//*[text()='Contact']/following-sibling::*//*[text()='"+contactFirstName+" "+contactLastName+"']", "Contact Name Text", action.SCROLLANDBOOLEAN, 30), "visibility", 20, "");
											if (ele != null) {
												String contactFullName = getText(driver,ele, "Contact Name",action.BOOLEAN);
												System.err.println("Contact Name : "+contactFullName);
												if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
													appLog.info("Contact Created Successfully :" + contactFirstName + " "+ contactLastName);
													return true;
												} else {
													appLog.error("Contact did not get created successfully :" + contactFirstName
															+ " " + contactLastName);
												}
											} else {
												appLog.error("Not able to find contact name label");
											}
										}else {
											log(LogStatus.ERROR, "Not able to click on Contacts related list view all section so cannot verify Created Contact "+contactFirstName+" "+contactLastName, YesNo.Yes);
										}
									
								}else {
									if(projectName.equalsIgnoreCase(Mode.Lightning.toString())) {
										ThreadSleep(2000);
										refresh(driver);
										ThreadSleep(5000);
									}
									ele=getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
									click(driver, ele, RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN);
									
									 ele = isDisplayed(driver, FindElement(driver, "//*[text()='Contact']/following-sibling::*//*[text()='"+contactFirstName+" "+contactLastName+"']", "Contact Name Text", action.SCROLLANDBOOLEAN, 30), "visibility", 20, "");

									if (ele != null) {
										
										String contactFullName = getText(driver,ele, "Contact Name",action.SCROLLANDBOOLEAN);
										System.err.println("Contact Name : "+contactFullName);
										if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
											appLog.info("Contact Created Successfully :" + contactFirstName + " "
													+ contactLastName);
											ThreadSleep(5000);
											if(labelNames!=null && labelValue!=null ) {
												for(int i=0; i<labelNames.length; i++) {
													if(fieldValueVerificationOnContactPage(projectName, null, labelNames[i].replace("_", " ").trim(),labelValue[i])){
														appLog.info(labelNames[i]+" label value "+labelValue[i]+" is matched successfully.");
													}else {
														appLog.info(labelNames[i]+" label value "+labelValue[i]+" is not matched successfully.");
														BaseLib.sa.assertTrue(false, labelNames[i]+" label value "+labelValue[i]+" is not matched.");
													}
												}
											}
											return true;
										} else {
											appLog.error("Contact did not get created successfully :" + contactFirstName
													+ " " + contactLastName);
										}
									} else {
										appLog.error("Not able to find contact name label");
									}
									
								}
								
							} else {
								appLog.info("Not able to click on save button");
							}

						} else {
							appLog.error("Not able to enter email id");
						}
					
				} else {
					appLog.error("Not able to enter last name in text box");
				}
			} else {
				appLog.error("Not able to enter first Name in text box");
			}
		return false;
	}
	

	
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param RecordType
	 * @return true/false
	 * @description This is used to click on related tab
	 */
	public boolean ClickonRelatedTab_Lighting(String projectName,RecordType RecordType) {
		
			for(int i=0;i<2; i++){
				refresh(driver);
				ThreadSleep(3000);
				List<WebElement> eleList = FindElements(driver, "//*[text()='Related']", "Related Tab");
				for (WebElement ele : eleList) {
					if(click(driver, ele, RecordType+" related tab", action.BOOLEAN)) {
						log(LogStatus.INFO, "clicked on "+RecordType+" related tab", YesNo.No);
						return true;
					}
				}
			}		
			log(LogStatus.ERROR,"Not able to click on related tab "+RecordType ,YesNo.Yes);
			return false;
	}
	

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param contactFirstName
	 * @param contactLastName
	 * @return true/false
	 * @description this method is used to click on already created contact
	 */
	public boolean clickOnCreatedContact(String projectName,String contactFirstName,String contactLastName){
		int i =1;
		String concatFullName;
		if(contactFirstName==null){
			concatFullName=contactLastName;
		} else {
			concatFullName=contactFirstName+" "+contactLastName;
		}
		if(clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, concatFullName, 20)){
			appLog.info("Clicked on Contact name : " + concatFullName);
			return true;
		}else{
			appLog.error("Contact Not Available : " + concatFullName);	
		}
	
		return false;
	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param accountName
	 * @param timeOut
	 * @return true if able to enter value for Legal Name on Contact Transfer Page
	 */
	public boolean enteringValueforLegalNameOnContactTransferPage(String projectName, String accountName, int timeOut) {
		WebElement ele;
		boolean flag = true;;
		NavatarSetupPageBusinessLayer np = new NavatarSetupPageBusinessLayer(driver);
		switchToFrame(driver, timeOut, np.getnavatarSetUpTabFrame_Lighting(projectName, timeOut));
		String AcctOrLegalName="";
		if (ProjectName.MNA.toString().equalsIgnoreCase(projectName)) {
			AcctOrLegalName="Account Name";
		}else if (ProjectName.PE.toString().contains(projectName)) {
			AcctOrLegalName="Legal Name";
		} else {
			AcctOrLegalName="Firm";
		}

		ele = isDisplayed(driver,
				FindElement(driver, "//label[text()='"+AcctOrLegalName+"']/../following-sibling::td/span/div/span//input",
						"Legal Name ", action.BOOLEAN, timeOut),
				"Visibility", timeOut, "Legal Name ");

		if (sendKeys(driver, ele, accountName, "Input Value : " + accountName, action.BOOLEAN)) {
			CommonLib.log(LogStatus.INFO, "Entered Value: " + accountName, YesNo.No);
			if (click(driver, getTransferButton(projectName, timeOut), "Transfer Button", action.BOOLEAN)) {
				CommonLib.log(LogStatus.INFO, "Clicked on Transfer Button", YesNo.No);

//				ele = getContactTransferConfirmationMsg(projectName, timeOut);
//				if (ele != null) {
//					CommonLib.log(LogStatus.INFO, "Confirmation PopUp Element is Present", YesNo.No);
//					String msg = ele.getText();
//
//					if (ContactPageErrorMessage.TransferConfirmationPopUpMessage.equals(msg)) {
//						CommonLib.log(LogStatus.INFO, "Confirmation Msg Verified : " + msg, YesNo.No);
//					} else {
//						flag = false;
//						CommonLib.log(LogStatus.ERROR, "Confirmation Msg Not Verified Actual : " + msg
//								+ " \t Expected : " + ContactPageErrorMessage.TransferConfirmationPopUpMessage,
//								YesNo.Yes);
//					}
//
//				} else {
//					flag = false;
//					CommonLib.log(LogStatus.ERROR, "Confirmation PopUp Element is null", YesNo.Yes);
//				}

				

				

			} else {
				flag = false;
				CommonLib.log(LogStatus.ERROR, "Not Able to Click on Transfer Button", YesNo.Yes);
			}
		} else {
			flag = false;
			CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + accountName, YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;

	}
	
	
	/**
	 * @author Akul Bhutani
	 * @param labelName
	 * @param value
	 * @return true if field set component verify successfully
	 */
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
	 * @param attachmentPath
	 * @return image id of updated photo in detail page
	 */
	public String updatePhotoInDetailPage(String projectName,String attachmentPath) {
		String imgId=null;
		Actions actions = new Actions(driver);
		scrollDownThroughWebelement(driver,getimgLink(projectName, 10) , "img");
		actions.moveToElement( getimgLink(projectName, 10)).click(getimgLink(projectName, 10)).build().perform();
		ThreadSleep(2000);
		//actions.release().build().perform();
			log(LogStatus.INFO, "click on img link", YesNo.No);
			/*WebElement ele=getupdatePhotoLink(projectName,ContactPagePhotoActions.Update_Photo, 10);
			actions.moveToElement(ele).click(ele).build().perform();
			ThreadSleep(2000);
			*/if (click(driver, getupdatePhotoLink(projectName,ContactPagePhotoActions.Update_Photo, 10), ContactPagePhotoActions.Update_Photo.toString(), action.SCROLLANDBOOLEAN)) {
				if (sendKeys(driver, getuploadPhotoButton(projectName,10), attachmentPath, "upload photo button", action.SCROLLANDBOOLEAN) ) {
					ThreadSleep(5000);
					/*if (uploadFileAutoIT(attachmentPath)) {
						log(LogStatus.INFO, "successfully uploaded file "+attachmentPath, YesNo.No);
						ThreadSleep(10000);
					*/	if (click(driver, getRecordPageSettingSave(10), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "successfully uploaded photo", YesNo.No);
							ThreadSleep(4000);
							imgId=getimgLink(projectName, 10).getAttribute("src");
							if (imgId!=null){
								log(LogStatus.INFO, "found id of img uploaded: "+imgId, YesNo.Yes);
								
								return imgId;
							}
							else {
								log(LogStatus.ERROR, "could not find id of img uploaded", YesNo.Yes);
								sa.assertTrue(false, "could not find id of img uploaded");
						
							}
						}else {
							log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
							sa.assertTrue(false, "save button is not clickable");
						}
					/*}else {
						log(LogStatus.ERROR, "could not upload file "+attachmentPath, YesNo.Yes);
						sa.assertTrue(false, "could not upload file "+attachmentPath);
					}*/
				}else {
					log(LogStatus.ERROR, "could not pass attachment path to image", YesNo.Yes);
					sa.assertTrue(false, "could not pass attachment path to image");
				}
			}else {
				log(LogStatus.ERROR, "update photo button is not clickable", YesNo.Yes);
				sa.assertTrue(false, "update photo button is not clickable");
			}
		/*}else {
			log(LogStatus.ERROR, "photo button on contact page is not clickable", YesNo.Yes);
			sa.assertTrue(false, "photo button on contact page is not clickable");
		}*/
		return null;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param recordName
	 * @return true if image deleted successfully
	 */
	public boolean deleteImage(String projectName, String recordName) {
		String imgId=null;
//		Actions actions = new Actions(driver);
//		scrollDownThroughWebelement(driver,getimgLink(projectName, 10) , "img");
//		actions.moveToElement( getimgLink(projectName, 10)).click(getimgLink(projectName, 10)).build().perform();
		WebElement ele=getUpdatePhotoCameraIcon(10);
		if(ele!=null) {
			if(click(driver, ele,"update photo camera icon", action.BOOLEAN)) {
				log(LogStatus.INFO, "clicked on update photo icon", YesNo.No);
				ThreadSleep(2000);
				if (click(driver, getupdatePhotoLink(projectName,ContactPagePhotoActions.Delete_Photo, 10), ContactPagePhotoActions.Update_Photo.toString(), action.SCROLLANDBOOLEAN)) {

					if (click(driver, getdeletePhotoButton(projectName,10) ,"delete photo button", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(4000);
						try {
							imgId=getimgLink(projectName, 10).getAttribute("src");
							if (imgId!=null) {
								if (imgId.contains(defaultPhotoText)) {
									log(LogStatus.INFO, "successfully delete image", YesNo.Yes);
									return true;
								}else {
									log(LogStatus.ERROR, "not able to delete image", YesNo.Yes);
									sa.assertTrue(false,"not able to delete image" );
								}
							}else {
								log(LogStatus.ERROR, "id of image not found so cannot verify delete image", YesNo.Yes);
								sa.assertTrue(false,"id of image not found so cannot verify delete image" );
							}
						}catch (Exception e) {
							log(LogStatus.ERROR, "id of image not found so image is deleted", YesNo.Yes);
							return true;
						}
					}else {
						log(LogStatus.ERROR, "delete photo button on popup is not clickable", YesNo.Yes);
						sa.assertTrue(false,"delete photo button on popup is not clickable" );
					}
				}else {
					log(LogStatus.ERROR, "delete photo button is not clickable", YesNo.Yes);
					sa.assertTrue(false,"delete photo button is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on update photo icon so cannot delete photo on "+recordName, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on update photo icon so cannot delete photo on "+recordName);
			}
		}else {
			log(LogStatus.ERROR, "camera photo icon is not displaying on "+recordName+" so cannot delete photo", YesNo.Yes);
			sa.assertTrue(false,"camera photo icon is not displaying on "+recordName+" so cannot delete photo");
		}
		return false;
	}
	
	public boolean createContactPopUp(String projectName, String contactFirstName, String contactLastName,
			String legalName, String emailID, String recordType,String otherLabelFields,String otherLabelValues, CreationPage creationPage, String title) {
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		String labelNames[]=null;
		String labelValue[]=null;
		if(otherLabelFields!=null && otherLabelValues !=null) {
			labelNames= otherLabelFields.split(",");
			labelValue=otherLabelValues.split(",");
		}
		if(creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {
			
				
				if(ClickonRelatedTab_Lighting(projectName, RecordType.Contact)) {
					appLog.info("clicked on related list tab");
				}else {
					appLog.error("Not able to click on related list tab so cannot create contact: "+contactFirstName+" "+contactLastName);
					return false;
				}
		
			if(click(driver, ins.getNewContactBtn(projectName, 30), "new contact button in "+projectName, action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on new contact button in institution page");
			}else {
				appLog.error("Not able to click on new button on institution page so cannot create contact: "+contactFirstName+" "+contactLastName);
				return false;
			}
		}else {
			//refresh(driver);
			ThreadSleep(3000);
				ThreadSleep(5000);
				clickUsingJavaScript(driver, getNewButton(projectName, 60), "new button");
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
					
				
			}
		WebElement ele=null;
			ThreadSleep(2000);
			if (sendKeys(driver, getContactFirstName(projectName, 60), contactFirstName, "Contact first Name",
					action.BOOLEAN)) {
				if (sendKeys(driver, getContactLastName(projectName, 60), contactLastName, "Contact Last Name",
						action.BOOLEAN)) {
					
					if(creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {
						
					}else {
						if (sendKeys(driver, getLegalName(projectName, 60), legalName, "Account Name",
								action.SCROLLANDBOOLEAN)) {
								ThreadSleep(1000);
								if (click(driver,
										FindElement(driver,
												"//*[@title='"+legalName+"']//strong[text()='"+legalName.split(" ")[0]+"']",
												"Legal Name List", action.THROWEXCEPTION, 30),
										legalName + "   :   Account Name", action.BOOLEAN)) {
									appLog.info(legalName + "  is present in list.");
								} else {
									appLog.info(legalName + "  is not present in the list.");
									return false;
								}
							
						} else {
							appLog.error("Not able to enter legal name");
							return false;
						}
					}
					ele = getLabelTextBox(projectName, PageName.Object2Page.toString(),PageLabel.Email.toString(), 10);
					
						if (sendKeys(driver,ele, emailID, "Email ID",
								action.SCROLLANDBOOLEAN)) {
							if(labelNames!=null && labelValue!=null) {
								for(int i=0; i<labelNames.length; i++) {
									ele = getContactPageTextBoxOrRichTextBoxWebElement(projectName, labelNames[i].trim(), 30);
									if(sendKeysAndPressEnter(driver, ele, labelValue[i], labelNames[i]+" text box", action.SCROLLANDBOOLEAN)) {
										appLog.info("passed value "+labelValue[i]+" in "+labelNames[i]+" field");
									}else {
										appLog.error("Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
										BaseLib.sa.assertTrue(false, "Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
									}
									if (labelNames[i].equalsIgnoreCase(excelLabel.Region.toString()) || labelNames[i].equalsIgnoreCase(excelLabel.Sector.toString())) {
										if (click(driver, FindElement(driver,
												"//*[text()='"+labelNames[i]+"']/following-sibling::div[@class='slds-form-element__control']//input[@type='text']", 
												"picklist "+labelNames[i], action.SCROLLANDBOOLEAN , 10), "picklist "+labelNames[i],  action.SCROLLANDBOOLEAN)) {
											
										
										if (click(driver,
												FindElement(driver,"//div[contains(@class,'listbox')]//*[@data-value='"+labelValue[i]+"']"
														,
														"Legal Name List", action.THROWEXCEPTION, 30),
												labelNames[i] + "   :   Account Name", action.BOOLEAN)) {
											appLog.info(labelNames[i] + "  is present in list.");
										} else {
											appLog.error("Not able to select "+labelValue[i]+" in "+labelNames[i]+" field");
											BaseLib.sa.assertTrue(false, "Not able to select "+labelValue[i]+" in "+labelNames[i]+" field");
										}
										}else {
											appLog.error("Not able to select "+labelValue[i]+" in "+labelNames[i]+" field");
											BaseLib.sa.assertTrue(false, "Not able to select "+labelValue[i]+" in "+labelNames[i]+" field");
										}
									}
								}
								
							}
							if (title!=null) {
							if (sendKeys(driver, getcontactTitle(projectName, 10), title, "title", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "passed value "+title+" to title", YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "could not pass value "+title+" to title", YesNo.No);
								BaseLib.sa.assertTrue(false,"could not pass value "+title+" to title" );
							}
							}
							if (click(driver, getNavigationTabSaveBtn(projectName, 60), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on save button");
								ThreadSleep(3000);
								if (getNavigationTabSaveBtn(projectName, 5)!=null) {
									click(driver, getNavigationTabSaveBtn(projectName, 60), "save", action.BOOLEAN);
								}
								if(creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {
										if(clickOnGridSection_Lightning(projectName,RelatedList.Contacts, 30)) {
											ele = isDisplayed(driver, FindElement(driver, "//*[text()='Contact']/following-sibling::*//*[text()='"+contactFirstName+" "+contactLastName+"']", "Contact Name Text", action.SCROLLANDBOOLEAN, 30), "visibility", 20, "");
											if (ele != null) {
												String contactFullName = getText(driver,ele, "Contact Name",action.BOOLEAN);
												System.err.println("Contact Name : "+contactFullName);
												if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
													appLog.info("Contact Created Successfully :" + contactFirstName + " "+ contactLastName);
													return true;
												} else {
													appLog.error("Contact did not get created successfully :" + contactFirstName
															+ " " + contactLastName);
												}
											} else {
												appLog.error("Not able to find contact name label");
											}
										}else {
											log(LogStatus.ERROR, "Not able to click on Contacts related list view all section so cannot verify Created Contact "+contactFirstName+" "+contactLastName, YesNo.Yes);
										}
									
								}else {
									if(projectName.equalsIgnoreCase(Mode.Lightning.toString())) {
										ThreadSleep(2000);
										refresh(driver);
										ThreadSleep(5000);
									}
									ele=getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
									click(driver, ele, RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN);
									
									 ele = isDisplayed(driver, FindElement(driver, "//*[text()='Contact']/following-sibling::*//*[text()='"+contactFirstName+" "+contactLastName+"']", "Contact Name Text", action.SCROLLANDBOOLEAN, 30), "visibility", 20, "");

									if (ele != null) {
										
										String contactFullName = getText(driver,ele, "Contact Name",action.SCROLLANDBOOLEAN);
										System.err.println("Contact Name : "+contactFullName);
										if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
											appLog.info("Contact Created Successfully :" + contactFirstName + " "
													+ contactLastName);
											ThreadSleep(5000);
											if(labelNames!=null && labelValue!=null ) {
												for(int i=0; i<labelNames.length; i++) {
													if(fieldValueVerificationOnContactPage(projectName, null, labelNames[i].replace("_", " ").trim(),labelValue[i])){
														appLog.info(labelNames[i]+" label value "+labelValue[i]+" is matched successfully.");
													}else {
														appLog.info(labelNames[i]+" label value "+labelValue[i]+" is not matched successfully.");
														BaseLib.sa.assertTrue(false, labelNames[i]+" label value "+labelValue[i]+" is not matched.");
													}
												}
											}
											return true;
										} else {
											appLog.error("Contact did not get created successfully :" + contactFirstName
													+ " " + contactLastName);
										}
									} else {
										appLog.error("Not able to find contact name label");
									}
									
								}
								
							} else {
								appLog.info("Not able to click on save button");
							}

						} else {
							appLog.error("Not able to enter email id");
						}
					
				} else {
					appLog.error("Not able to enter last name in text box");
				}
			} else {
				appLog.error("Not able to enter first Name in text box");
			}
		return false;
	}
	
	
}
