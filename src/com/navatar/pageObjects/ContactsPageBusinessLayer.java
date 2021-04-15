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
	public boolean fieldValueVerificationOnContactPage(String projectName, TabName tabName,
			String labelName,String labelValue) {
		String finalLabelName="";


		if (labelName.contains("_")) {
			if(labelName.equalsIgnoreCase(excelLabel.Asst_Phone.toString())) {
				finalLabelName= IndiviualInvestorFieldLabel.Asst_Phone.toString();
			}else {
				finalLabelName = labelName.replace("_", " ");
			}
		} else {
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
			
		}
		if(labelName.equalsIgnoreCase(excelLabel.Region.toString()) || labelName.equalsIgnoreCase(excelLabel.Industry.toString())) {
			xpath = "//span[@class='test-id__field-label'][text()='" + finalLabelName
					+ "']/../following-sibling::div/span//a";

		}
		else if(labelName.equalsIgnoreCase(excelLabel.Phone.toString())) {
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
	 * @return true/false
	 * @description This is used to create new contact with given arguments
	 */
	public boolean createContact(String projectName, String contactFirstName, String contactLastName,
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
								ele=FindElement(driver,
										"//*[@title='"+legalName+"']//strong[contains(text(),'"+legalName.split(" ")[0]+"')]",
										"Legal Name List", action.THROWEXCEPTION, 30);
								if (clickUsingJavaScript(driver,ele
										,
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
									if(sendKeys(driver, ele, labelValue[i], labelNames[i]+" text box", action.SCROLLANDBOOLEAN)) {
										appLog.info("passed value "+labelValue[i]+" in "+labelNames[i]+" field");
									}else {
										appLog.error("Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
										BaseLib.sa.assertTrue(false, "Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
									}
									if (labelNames[i].equalsIgnoreCase(excelLabel.Region.toString()) || labelNames[i].equalsIgnoreCase(excelLabel.Industry.toString())) {
										if (click(driver,
												FindElement(driver,
														"//*[@title='" + labelValue[i]+ "']",
														"Legal Name List", action.THROWEXCEPTION, 30),
												labelNames[i] + "   :   Account Name", action.BOOLEAN)) {
											appLog.info(labelNames[i] + "  is present in list.");
										} else {
											appLog.info(labelNames[i] + "  is not present in the list.");
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
											ele = isDisplayed(driver, FindElement(driver, "//span[@title='Contact Name']/ancestor::table/tbody/tr/th/span/a", "Contact Name Text", action.SCROLLANDBOOLEAN, 30), "visibility", 20, "");
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
									

									if (getContactFullNameInViewMode(projectName, 60) != null) {
										String contactFullName = getText(driver,
												getContactFullNameInViewMode(projectName, 60), "Contact Name",
												action.SCROLLANDBOOLEAN);
										System.err.println("Contact Name : "+contactFullName);
										if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
											appLog.info("Contact Created Successfully :" + contactFirstName + " "
													+ contactLastName);
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
	
	
	/**@author Akul Bhutani
	 * @param projectName
	 * @param contactFirstName
	 * @param contactLastName
	 * @param legalName
	 * @param emailID
	 * @param labelsWithValus
	 * @param timeOut
	 * @return true/false
	 * @description this is used to create contact contact with arguments having 2d array of data
	 */
	public boolean createContact(String projectName, String contactFirstName, String contactLastName,
			String legalName, String emailID,String recordType, String[][] labelsWithValus,int timeOut) {
		boolean flag=false;
		WebElement ele=null;
		String xpath="";
		refresh(driver);
		ThreadSleep(5000);
		if(clickUsingJavaScript(driver, getNewButton(projectName, 60), "new button")) {
			appLog.info("clicked on new button");
			ThreadSleep(2000);
			
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
			
			ele = getLabelTextBox(projectName, PageName.Object2Page.toString(),PageLabel.First_Name.toString(), timeOut);
			if (sendKeys(driver, ele, contactFirstName, "Contact first Name",action.BOOLEAN)) {
				appLog.info("Enter value on Contact First Name Text Box : "+contactFirstName);
				ele = getLabelTextBox(projectName, PageName.Object2Page.toString(),PageLabel.Last_Name.toString(), timeOut);
				
				if (sendKeys(driver, ele, contactLastName, "Contact Last Name",	action.BOOLEAN)) {
					appLog.info("Enter value on Contact Last Name Text Box : "+contactLastName);
					
					ele = getLabelTextBox(projectName, PageName.Object2Page.toString(),PageLabel.Last_Name.toString(), timeOut);
					if (sendKeys(driver, getLegalName(projectName, 60), legalName, "Legal Name",action.SCROLLANDBOOLEAN)) {
						appLog.info("Enter value on Legal Text Box : "+legalName);
						
						ThreadSleep(1000);
						xpath = "//div[contains(@class,'uiAutocomplete')]//a//div[@title='" + legalName+ "']";
						ele = FindElement(driver,xpath,"Legal Name List", action.SCROLLANDBOOLEAN, timeOut);
						if (click(driver,ele,legalName + "   :   Account Name", action.BOOLEAN)) {
							appLog.info(legalName + "  is present in list.");
							
							ele = getLabelTextBox(projectName, PageName.Object2Page.toString(),PageLabel.Email.toString(), timeOut);
							if (sendKeys(driver, ele, emailID, "Email ID",action.SCROLLANDBOOLEAN)) {
								appLog.info("Enter value on Contact Email Text Box : "+emailID);
								
								if (click(driver, getSaveButton(projectName, 60), "Save Button",action.SCROLLANDBOOLEAN)) {
									appLog.info("Clicked on save button");
									
									ThreadSleep(2000);
									refresh(driver);
									ThreadSleep(5000);
									
									if (getContactFullNameInViewMode(projectName, 20) != null) {
										String contactFullName = getText(driver,getContactFullNameInViewMode(projectName, 60), "Contact Name",action.BOOLEAN);
										appLog.info("Contact Name : "+contactFullName);
										if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
											appLog.info("Contact Created Successfully :" + contactFirstName + " "+ contactLastName);
											flag=true;
										} else {
											appLog.error("Contact created but not matched :" + contactFirstName+ " " + contactLastName);
										}
									} else {
										appLog.error("Not able to find contact name label");
									}
								} else {
									appLog.info("Not able to click on save button");
								}
							} else {
								appLog.error("Not able to enter email id");
							}
							
						} else {
							appLog.info(legalName + "  is not present in the list.");
						}
					} else {
						appLog.error("Not able to enter legal name");
					}
				}else {
					appLog.error("Not able to enter Last name in text box");

				}
			} else {
				appLog.error("Not able to enter first name in text box");
			}
		} else {
			appLog.error("Not able to click on New Button so cannot create Contact: " + contactFirstName+" "+contactLastName);
		}
		return flag;
	}
	
	/**@author Akul Bhutani
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
	
	/**@author Akul Bhutani
	 * @param environment
	 * @param mode
	 * @param contactFirstName
	 * @param contactLastName
	 * @return true/false
	 * @description this method is used to click on already created contact lightning/classic
	 */
	public boolean clickOnCreatedContact(String environment, String mode,String contactFirstName,String contactLastName){
		int i =1;
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			if (getSelectedOptionOfDropDown(driver, getViewDropdown(60), "View dropdown", "text").equalsIgnoreCase("All Contacts")) {
				if (click(driver, getGoButton(60), "Go button", action.BOOLEAN)) {
				}
				else {
					appLog.error("Go button not found");
					return false;
				}
			}
			else{
				if (selectVisibleTextFromDropDown(driver, getViewDropdown(60),"View dropdown","All Contacts") ){
				}
				else {
					appLog.error("All Contacts  not found in dropdown");
					return false;
				}

			}
			WebElement contactName=null;
			if(contactFirstName==null){
				contactName = FindElement(driver, "//div[@class='x-panel-bwrap']//a//span[contains(text(),'"+ contactLastName + "')]", "Contact Name", action.BOOLEAN, 10);
			} else {
				contactName = FindElement(driver, "//div[@class='x-panel-bwrap']//a//span[contains(text(),'"
						+ contactLastName + ", " + contactFirstName + "')]", "Contact Name", action.BOOLEAN, 10);
			}

			if (contactName != null) {
				if (click(driver, contactName, "Contact Name", action.SCROLLANDBOOLEAN)) {
					appLog.info(
							"Clicked on created contact successfully :" + contactFirstName + " " + contactLastName);
					return true;

				} else {
					appLog.error("Not able to click on created contact");
					return false;
				}
			} else {
				while (true) {
					appLog.error("Contact is not Displaying on "+i+ " Page: " + contactLastName + ", " + contactFirstName);
					if (click(driver, getNextImageonPage(10), "Contact Page Next Button",
							action.SCROLLANDBOOLEAN)) {

						appLog.info("Clicked on Next Button");
						if(contactFirstName==null){
							contactName = FindElement(driver, "//div[@class='x-panel-bwrap']//a//span[contains(text(),'"+ contactLastName + "')]", "Contact Name", action.BOOLEAN, 10);
						} else {
							contactName = FindElement(driver, "//div[@class='x-panel-bwrap']//a//span[contains(text(),'"
									+ contactLastName + ", " + contactFirstName + "')]", "Contact Name", action.BOOLEAN, 10);
						}
						if (contactName != null) {
							if (click(driver, contactName, contactLastName + ", " + contactFirstName, action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on Contact name : " + contactLastName + ", " + contactFirstName);
								return true;

							}
						}



					} else {
						appLog.error("Contact Not Available : " + contactLastName + ", " + contactFirstName);
						return false;
					}
					i++;
				}
			}

		}else{
			return true;
		}
	}

	/**@author Akul Bhutani
	 * @param projectName
	 * @param subject
	 * @param body
	 * @param attachmentYesOrNo
	 * @param attachment
	 * @param timeOut
	 * @return true/false
	 * @description this method is used to send email from contact page
	 */
	public boolean sendEmail(String mode,String projectName, String subject, String body,boolean attachmentYesOrNo,String attachment,int timeOut) {
		String parentID=null;
		boolean attachmentFlag=false,mailFlag=false;
		if (sendKeys(driver, getsubjectTextbox(projectName, timeOut), subject, "subject", action.SCROLLANDBOOLEAN)) {
			if (sendKeys(driver, getbodyTextbox(projectName, timeOut), body, "body", action.SCROLLANDBOOLEAN)) {
				if (attachmentYesOrNo) {
					if (click(driver, getattachFileButton(mode,projectName, timeOut), "attach file button", action.SCROLLANDBOOLEAN)) {
						parentID=switchOnWindow(driver);
						if (parentID!=null) {
							switchToFrame(driver,timeOut,getFrame(PageName.EmailUploadPage, timeOut) );
							if (sendKeys(driver, getuploadFileBrowseButton(projectName, timeOut), attachment, "browse button", action.SCROLLANDBOOLEAN)) {
								if (click(driver, getattachToEmail(projectName, timeOut), "attach button", action.BOOLEAN)) {
									attachmentFlag=true;
									ThreadSleep(4000);
								}
								else {
									log(LogStatus.ERROR, "attach button not clickable, so cannot send email", YesNo.Yes);
								}
							}
							else {
								log(LogStatus.ERROR, "file path could not be sent, so cannot send email", YesNo.Yes);
							}
							driver.close();
							driver.switchTo().window(parentID);
						}
					}else {
						log(LogStatus.ERROR, "file path could not be sent, so cannot send email", YesNo.Yes);
					}
				}
				else attachmentFlag=true;
				WebElement ele=BaseLib.edriver.findElement(By.cssSelector("input[title='Send']"));
				try {ele.click();
				log(LogStatus.INFO, "successfully clicked on send button", YesNo.No);
				}
				catch(Exception e) {
					e.printStackTrace();
					return false;
				}
				if (isAlertPresent(driver)) {
					switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
					log(LogStatus.INFO, "successfully accepted alert", YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "alert is not present", YesNo.Yes);
				}
				mailFlag=true;
			}else {
				log(LogStatus.ERROR, "body textbox is not visible, so cannot send mail", YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, "subject textbox is not visible, so cannot send mail", YesNo.Yes);
		}
		return mailFlag&&attachmentFlag;
	}
	
	/**@author Akul Bhutani
	 * @param projectName
	 * @param contactNames
	 * @param subject
	 * @param body
	 * @param attachmentPath
	 * @return true/false
	 * @description send email through "send list email" button from contact detail page lightning
	 */
	public boolean sendListEmail(String projectName, String contactNames,String subject,String body,String attachmentPath) {
		WebElement ele = null;
		String[] contacts=contactNames.split(",");
		String xpath="";
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			xpath="//div[@class='listContent']//li/a/span[text()='Automation All']";
			ele = FindElement(driver, xpath,"Select List View : Automation All", action.SCROLLANDBOOLEAN, 30);
			if (click(driver, ele, "select List View : Automation All", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(5000);
				for (String con:contacts) {
					if (clickUsingJavaScript(driver, returnCheckboxOnContactHomePage(projectName, con, 10), "checkbox", action.BOOLEAN)) {
						log(LogStatus.INFO, "successfully clicked on checkbox of "+con, YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "could not click on checkbox of "+con, YesNo.Yes);
						sa.assertTrue(false, "could not click on checkbox of "+con);

					}
				}
				if (click(driver, getsendListEmail(projectName, 10), "send list email button", action.BOOLEAN)) {
					if (sendKeys(driver, getsendListEmailSubject(projectName, 10), subject, "subject", action.BOOLEAN)) {
						switchToFrame(driver, 10, getcontainerFrameEmail(projectName, 10));
						switchToFrame(driver, 10, getemailBodyFrame(projectName, 10));
						if (sendKeys(driver, getemailBody(projectName, 10), body, "body", action.BOOLEAN)) {

						}else {
							log(LogStatus.ERROR, "email body textbox is not visible, so cannot enter text", YesNo.Yes);
							sa.assertTrue(false, "email body textbox is not visible, so cannot enter text");
						}
						switchToDefaultContent(driver);

						scrollDownThroughWebelement(driver, getattachFileButton(Mode.Lightning.toString(), projectName, 10), "attach");
						if (click(driver, getattachFileButton(Mode.Lightning.toString(), projectName, 10), "attach", action.BOOLEAN)) {
							if (clickUsingJavaScript(driver, getuploadFileButton(projectName, 10), "upload", action.BOOLEAN)) {
								ThreadSleep(5000);
								if (uploadFileAutoIT(attachmentPath)) {
									log(LogStatus.INFO, "successfully uploaded file "+attachmentPath, YesNo.No);
									ThreadSleep(4000);
									if (click(driver, getsendButtonListEmail(projectName, 10), "send", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "successfully sent email", YesNo.No);
										return true;

									}else {
										log(LogStatus.ERROR, "send button is not clickable", YesNo.Yes);
										sa.assertTrue(false, "send button is not clickable");
									}
								}else {
									log(LogStatus.ERROR, "could not upload file "+attachmentPath, YesNo.Yes);
									sa.assertTrue(false, "could not upload file "+attachmentPath);
								}
							}else {
								log(LogStatus.ERROR, "upload button is not clickable", YesNo.Yes);
								sa.assertTrue(false, "upload button is not clickable");
							}
						}else {
							log(LogStatus.ERROR, "attach file button is not clickable", YesNo.Yes);
							sa.assertTrue(false, "attach file button is not clickable");
						}
					}else {
						log(LogStatus.ERROR, "subject textbox is not visible", YesNo.Yes);
						sa.assertTrue(false, "subject textbox is not visible");
					}
				}else {
					log(LogStatus.ERROR, "send list email button is not clickable", YesNo.Yes);
					sa.assertTrue(false, "send list email button is not clickable");
				}
			}else {
				log(LogStatus.ERROR, "list view dropdown : Automation all could not be clicked, so cannot send list email", YesNo.Yes);
				sa.assertTrue(false, "list view dropdown : Automation all could not be clicked, so cannot send list email");
			}
		}
		return false;


	}
	
	/**@author Akul Bhutani
	 * @param projectName
	 * @param contactFullName
	 * @param timeOut
	 * @return WebElement
	 * @description return checkbox in front of contact on contact detail page
	 */
	public WebElement returnCheckboxOnContactHomePage(String projectName, String contactFullName, int timeOut) {
		String xpath="//a[@title='"+contactFullName+"']/../../preceding-sibling::td//input/following-sibling::span";
		return isDisplayed(driver, FindElement(driver, xpath, "checkbox", action.SCROLLANDBOOLEAN, timeOut), "visibility", timeOut, "checkbox");
	}
	
	/**
	 * @param projectName
	 * @param contactFirstName
	 * @param contactLastName
	 * @param legalName
	 * @param emailID
	 * @param recordType
	 * @param otherLabelFields
	 * @param otherLabelValues
	 * @param creationPage
	 * @return true if able to create contact
	 */
	public boolean createContactWithoutNew(String projectName, String contactFirstName, String contactLastName,
			String legalName, String emailID, String recordType,String otherLabelFields,String otherLabelValues, CreationPage creationPage) {
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		String labelNames[]=null;
		String labelValue[]=null;
		if(otherLabelFields!=null && otherLabelValues !=null) {
			labelNames= otherLabelFields.split(",");
			labelValue=otherLabelValues.split(",");
		}


		if (!recordType.equals("") || !recordType.isEmpty()) {
			ThreadSleep(2000);
			if(click(driver, getRadioButtonforRecordType(recordType,true, 5), "Radio Button for : "+recordType, action.SCROLLANDBOOLEAN)){
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
							ele=FindElement(driver,"//div[contains(@class,'uiAutocomplete')]//a//div[@title='" + legalName
									+ "']",	"Legal Name List", action.THROWEXCEPTION, 30);
							if (clickUsingJavaScript(driver,ele,legalName + " : Account Name", action.BOOLEAN)) {
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

				if (sendKeys(driver, getEmailId(projectName, 60), emailID, "Email ID",
						action.SCROLLANDBOOLEAN)) {
					if(labelNames!=null && labelValue!=null) {
						for(int i=0; i<labelNames.length; i++) {
							ele = getContactPageTextBoxOrRichTextBoxWebElement(projectName, labelNames[i].trim(), 30);
							if(sendKeys(driver, ele, labelValue[i], labelNames[i]+" text box", action.SCROLLANDBOOLEAN)) {
								appLog.info("passed value "+labelValue[i]+" in "+labelNames[i]+" field");
							}else {
								appLog.error("Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
								BaseLib.sa.assertTrue(false, "Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
							}
						}

					}
					if (click(driver, getSaveButton(projectName, 60), "Save Button",
							action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on save button");
						if(creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {
							if(clickOnGridSection_Lightning(projectName,RelatedList.Contacts, 30)) {
								ele = isDisplayed(driver, FindElement(driver, "//span[@title='Contact Name']/ancestor::table/tbody/tr/th/span/a", "Contact Name Text", action.SCROLLANDBOOLEAN, 30), "visibility", 20, "");
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

							if (getContactFullNameInViewMode(projectName, 60) != null) {
								String contactFullName = getText(driver,
										getContactFullNameInViewMode(projectName, 60), "Contact Name",
										action.BOOLEAN);
								System.err.println("Contact Name : "+contactFullName);
								if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
									appLog.info("Contact Created Successfully :" + contactFirstName + " "
											+ contactLastName);

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
	
	
	/**
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
		}else if (ProjectName.PE.toString().equalsIgnoreCase(projectName)) {
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
	
	
	public boolean verifyFieldSetComponent(String labelName, String value) {
		String finalLabelName="";
		if(labelName.contains("_")) {
			 finalLabelName = labelName.replace("_", " ");
		}else {
			 finalLabelName = labelName;
		}
		String xpath="//*[@id='parentDiv'][@class='cDisplayFieldSet']//*[contains(text(),'"+finalLabelName+"')]/following-sibling::div/*";
		
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
	
	
}
