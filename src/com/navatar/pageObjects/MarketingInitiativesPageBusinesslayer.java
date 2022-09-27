package com.navatar.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.App;

import com.google.common.collect.MapMaker;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.Header;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.ExcelUtils;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.AppListeners.*;
import static com.navatar.generic.CommonLib.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MarketingInitiativesPageBusinesslayer extends MarketingInitiativesPage implements MarketingInitiativesPageErrorMsg {

	public MarketingInitiativesPageBusinesslayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param marketInitiativeName
	 * @return true/false
	 */
	public boolean createMarketInitiatives(String environment, String mode, String marketInitiativeName) {
		WebElement ele=null;
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			refresh(driver);
			ThreadSleep(5000);
			if(clickUsingJavaScript(driver, getNewButton(environment, mode, 60), "new button")) {
				appLog.info("clicked on new button");
			}else {
				appLog.error("Not able to click on new button so cannot create Market Initiatives : "+marketInitiativeName);
				return false;
			}
		}else {
			if (click(driver, getNewButton(environment,mode,60), "New Button", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on new button");
			} else {
				appLog.error("Not able to click on new button so cannot create Market Initiatives : "+marketInitiativeName);
				return false;
			}
		}
//		if(click(driver, getNewButton(environment, mode, 30), "new button", action.SCROLLANDBOOLEAN)) {
//			appLog.info("clicked on new button");
			if(sendKeys(driver, getMarketInitiativeNameTextBox(environment, mode, 60), marketInitiativeName, "market initiative name text box", action.SCROLLANDBOOLEAN)) {
				appLog.info("passed value in Name text box: "+marketInitiativeName);
				if(click(driver, getCustomTabSaveBtn(environment,  30), "save button", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on Save Button For market Initiative: "+marketInitiativeName);

					if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
//						String	xpath="//*[contains(text(),'Marketing Initiative')]/..//*[text()='"+marketInitiativeName+"']";
//						 ele = FindElement(driver, xpath, "Header : "+marketInitiativeName, action.BOOLEAN, 30);
							 ele = verifyCreatedItemOnPage(PageName.Marketing_Initiative.toString(), marketInitiativeName);
					
					} else {
						ele=getmarketInitiativeLabelLabelText(environment, mode, 60);
					}
					if(ele!=null) {
							appLog.info("Market initiative is created successfully: "+marketInitiativeName);
							return true;
						
					}else {
						appLog.error("Create market initiative label text is not visible so cannot verify created market initiative text: "+marketInitiativeName);
					}
					
				}else {
					appLog.error("Not able to click on save button so cannot create Market Initiatives : "+marketInitiativeName);
				}
			}else {
				appLog.error("Not able to pass value in Name text box: "+marketInitiativeName+"  so cannot create market Initiative");
			}
//		}else {
//			appLog.error("Not able to click on new button so cannot create Market Initiatives : "+marketInitiativeName);
//		}
		return false;
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param marketInitiativeName
	 * @return true/false
	 */
	public boolean clickOnCreatedMarketInitiatives(String environment, String mode,String marketInitiativeName){
		int i =1;
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			if (getSelectedOptionOfDropDown(driver, getViewDropdown(60), "View dropdown", "text").equalsIgnoreCase("All")) {
				if (click(driver, getGoButton(60), "Go button", action.BOOLEAN)) {
				} else {
					appLog.error("Go button not found");
					return false;
				}
			}else{
				if (selectVisibleTextFromDropDown(driver, getViewDropdown(60), "View dropdown", "All")) {
				} else {
					appLog.error("All Contacts  not found in dropdown");
					return false;
				}

			}
			WebElement MI = FindElement(driver, "//div[@class='x-panel-bwrap']//a//span[contains(text(),'"+marketInitiativeName+"')]", "marketInitiative Name", action.BOOLEAN, 10);
			if (MI != null) {
				if (click(driver, MI, "marketInitiative Name", action.SCROLLANDBOOLEAN)) {
					appLog.info(
							"Clicked on created marketInitiative Name successfully :" + marketInitiativeName);
					return true;

				} else {
					appLog.error("Not able to click on created marketInitiative Name: "+marketInitiativeName);
					return false;
				}
			} else {
				while (true) {
					appLog.error("marketInitiative Name is not Displaying on "+i+ " Page: " + marketInitiativeName);
					if (click(driver, getNextImageonPage(10), "MI Page Next Button",
							action.SCROLLANDBOOLEAN)) {

						appLog.info("Clicked on Next Button");
						MI = FindElement(driver, "//div[@class='x-panel-bwrap']//a//span[contains(text(),'"+marketInitiativeName+"')]", "marketInitiative Name", action.BOOLEAN, 10);
						if (MI != null) {
							if (click(driver, MI,marketInitiativeName, action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on MarketingInitiatives: " + marketInitiativeName);
								return true;

							}
						}

					} else {
						appLog.error("MarketingInitiatives Not Available : " + marketInitiativeName);
						return false;
					}
					i++;
				}
			}
		}else{
			if(clickOnAlreadyCreated_Lighting(environment, mode, TabName.MarketingInitiatives,marketInitiativeName, 10)){
				appLog.info("Clicked on created MarketingInitiatives : " +marketInitiativeName);
				return true;
			}else{
				appLog.error("Not able to click on created MarketingInitiatives: "+marketInitiativeName);	
			}
		}
		return false;
	}
	
	
	
	/**
	 * @author Ankit Jaiswal
	 * @param addProspectsTab
	 * @param fieldName
	 * @param operator
	 * @param value
	 * @return true/false
	 */
	public boolean SearchforProspects(AddProspectsTab addProspectsTab,String fieldName, String operator, String value) {
		WebElement ele= null;
		int i=1;
		if(sendKeys(driver, getAddProspectFieldAutoCompleteTextBox(addProspectsTab,30), fieldName, "field text box", action.SCROLLANDBOOLEAN)) {
			if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.PastMarketingInitiatives.toString())) {
				i=2;
			}
			ThreadSleep(2000);
			String xpath="(//ul[@class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content'])["+i+"]//li[text()='"+fieldName+"']";
			ele=isDisplayed(driver,FindElement(driver, xpath, "field auto complete text", action.SCROLLANDBOOLEAN,30),"visibility",30,"field auto complete text");
			
			if(ele!=null) {
				if(click(driver, ele, fieldName+" text", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on field name "+fieldName+" text box");
					if(selectVisibleTextFromDropDown(driver, getAddProspectOperatorDropDownList(addProspectsTab, 30), "operator drop down list", operator)) {
						appLog.info("Select Operator : "+operator);
						if(value!=null && !value.isEmpty()) {
							if(sendKeys(driver, getAddProspectValueTextBox(addProspectsTab, 30), value, "Value text box", action.SCROLLANDBOOLEAN)) {
								appLog.info("passed value in Value Text Box: "+value);
							}else {
								appLog.error("Not able to pass value in Value text box: "+value+" so cannot apply filter");
								return false;
							}
						}
						if(click(driver, getAddProspectSearchBtn(addProspectsTab, 30), "search button", action.SCROLLANDBOOLEAN)) {
							appLog.info("clicked on Search button successfully");
							return true;
						}else {
							appLog.error("Not able to click on search button so cannot apply filter");
						}
					}else {
						appLog.error("Not able to select operator: "+operator+" so cannot apply filter");
					}
				}else {
					appLog.error("Not able to click on field "+fieldName+" so cannot apply filter");
				}
			}else {
				appLog.error(fieldName+" is not visible in field auto complete text box so cannot apply filter.");
			}
		}else {
			appLog.error("Not able to pass value in field auto complete text box : "+fieldName+" so cannot apply filter");
		}
		return false;
		
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param reportFolderName
	 * @param reportName
	 * @return true/false
	 */
	public boolean selectReportFromReportsTab(String reportFolderName,String reportName) {
		WebElement ele= null;
		if(click(driver,getSelectAReportLookUpIcon(20), "select a report look up icon", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on select a report look up icon");
			ThreadSleep(2000);
			if(sendKeys(driver,getSelectAReportSearchTextBox(30),reportName, "select a reports search text box", action.SCROLLANDBOOLEAN)) {
				ele=getSelectAReportPopUpFileName(reportFolderName, reportName, 20);
				if(ele!=null) {
					appLog.info(reportName+" is visible in "+reportFolderName+" folder select a reports look up pop up");
					if(click(driver, ele, "Sample Report: # of Contacts report link", action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on report Name "+reportName);
						if(click(driver,getSelectAReportPopUpOKBtn(30),"select a reports pop up ok btn", action.SCROLLANDBOOLEAN)) {
							appLog.info("Clicked on OK button");
							if(click(driver,getLoadReportsBtn(10), "load reports button", action.SCROLLANDBOOLEAN)) {
								appLog.info("clicked on load report button successfully.");
								return true;
							}else {
								appLog.error("Not able to click on load reports button so cannot cannot load selected report :"+reportName);
							}
							
						}else {
							appLog.error("Not able to click on select a reports OK button so cannot select report :"+reportName);
						}
					}else {
						appLog.error("Not able to click on report Name "+reportName+" so cannot select it");
					}
				}else {
					appLog.error("searched report is not visible in select a report pop up : "+reportName+" so cannot select it");
				}
			}else {
				appLog.error("Not able to pass value in select a rpeort search text box : "+reportName+" so cannot select report: "+reportName);
			}
			
		}else {
			appLog.error("Not able to click select a report lookup icon so cannot select report Folder: "+reportFolderName+"  report Name: "+reportName);
		}
		return false;
	}
	
	
	
	/**
	 * @author Ankit Jaiswal
	 * @param ContactFullNameAndAccountName
	 * @param isCheckedforLink TODO
	 * @return true/false
	 */
	public List<String> selectProspectsContactAndVerifyReviewProspectList(String environment, String mode,AddProspectsTab addProspectsTab,HashMap<String, String> ContactFullNameAndAccountName, boolean isCheckedforLink) {
		List<String> result = new ArrayList<String>();
		WebElement ele = null;
		int count=0;
		String xpath = null;
		String parentId = null;
		ele=getSelectFromSearchResultsRecord(60);
		if(ele!=null) {
			String beforeRecord=ele.getText().trim();
			int beforeRecordDig = Integer.parseInt((beforeRecord.split(":")[1].trim()));
			if(beforeRecordDig!=0) {
				appLog.error("select  from search result record count is: "+beforeRecordDig);
				Set<String> contactFullName = ContactFullNameAndAccountName.keySet();
				Iterator<String> itr = contactFullName.iterator();
				while (itr.hasNext()) {
					String contactName = itr.next();
					String accountName=ContactFullNameAndAccountName.get(contactName);
					if(ScrollAndClickOnContactNameCheckBoxInAddProspect(addProspectsTab,contactName, accountName, 10)) {
						appLog.info("clicked on Contact Name Check Box: "+contactName);
						
						if (isCheckedforLink) {
							
							if (count==0) {
								
								String[] linkClick = {contactName,accountName};
								
								for (int j = 0; j < linkClick.length; j++) {
									
									xpath = "//div/a[text()='"+linkClick[j]+"']";
									ele = FindElement(driver, xpath, linkClick[j], action.BOOLEAN, 10);
									
									if (ele!=null) {
										
										if (click(driver, ele, linkClick[j], action.BOOLEAN)) {
											
										parentId = 	switchOnWindow(driver);
										
										if (parentId!=null) {
											ThreadSleep(2000);
											if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
												xpath = "//div/*[contains(text(),'"+linkClick[j]+"')]";
												}else{
													xpath = "//h2[contains(text(),'"+linkClick[j]+"')]";	
												}
											
											ele = FindElement(driver, xpath, "on new window : "+linkClick[j], action.BOOLEAN, 10);
											
											if (ele!=null) {
												appLog.info("Landing Page Verified : "+linkClick[j]);
											} else {
												appLog.error("Landing Page Not Verified : "+linkClick[j]);
												result.add("Landing Page Not Verified : "+linkClick[j]);
											}
											
											driver.close();
											driver.switchTo().window(parentId);
											switchToDefaultContent(driver);
											
										} else {
											appLog.error("Not New Window for "+linkClick[j]);
											result.add("Not New Window for "+linkClick[j]);
										}
										
									
										
										if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
											
											MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
											switchToFrame(driver, 5, market .getMarketInitiativeFrame_Lightning(10));	
											
										}
										
										
										
											
										} else {
											appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
											result.add("Not able to click on "+linkClick[j]+" so cannot verify landing page");
										}
									} else {
										appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
										result.add("Not able to click on "+linkClick[j]+" so cannot verify landing page");
									}
									
									
								}
								
								
							}
							
						}
						
						count++;
						
					}else {
						appLog.error("Not able to click on Contact Name :"+contactName+" check box so cannot add contact in review prospect list");
						result.add("Not able to click on Contact Name :"+contactName+" check box so cannot add contact in review prospect list");
					}
					
				}
				ele=getAddToProspectListBtn(5);
				if(ele!=null) {
					if(click(driver, getAddToProspectListBtn(5), "add prospect list button", action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on add prospect list button");
						ThreadSleep(5000);
						ele=getSelectFromSearchResultsRecord(60);
						if(ele!=null) {
							String afterRecordCount=ele.getText().trim();
							int AfterRecordDig = Integer.parseInt((afterRecordCount.split(":")[1].trim()));
							int ExpectedCount=beforeRecordDig-count;
							if(AfterRecordDig==ExpectedCount) {
								appLog.info("Select from search result record count is matched : "+AfterRecordDig);
							}else {
								appLog.error("Select from search result record count is not matched. Expected: "+ExpectedCount+" \t Actual Result: "+beforeRecordDig);
								result.add("Select from search result record count is not matched. Expected: "+ExpectedCount+" \t Actual Result: "+beforeRecordDig);
							}
						}else {
							appLog.error("Select from search result record is not visible after add contact in prospect list grid so cannot verify record number of select  from search result.");
							result.add("Select from search result record is not visible after add contact in prospect list grid so cannot verify record number of select  from search result.");
						}
						ThreadSleep(5000);
						ele=getReviewProspectListRecord(60);
						if(ele!=null) {
							String reviewListRecord=ele.getText().trim();
							int reviewListRecordDig = Integer.parseInt((reviewListRecord.split(":")[1].trim()));
							if(reviewListRecordDig!=0) {
								itr = contactFullName.iterator();
								while (itr.hasNext()) {
									String contactName = itr.next();
									String accountName=ContactFullNameAndAccountName.get(contactName);
									 xpath="//span[@id='gridReviewList-view-box']//span[contains(@id,'gridReviewList-row-')]/span[text()='"+contactName+"']/../span[text()='"+accountName+"']";
									ele = isDisplayed(driver, FindElement(driver,xpath, "", action.BOOLEAN,20), "visibility",20,contactName+" select Contact Name");
									if(ele!=null) {
										appLog.info(contactName+ " :Contact Name is selected successfully in add review prospect list");
									}else {
										appLog.error(contactName+ " :Contact Name is not available in add review prospect list");
										result.add(contactName+ " :Contact Name is not available in add review prospect list");
									}
								}
								if(reviewListRecordDig==count) {
									appLog.info("Select from search result record number is matched. Record Count is: "+count);
								}else {
									appLog.error("Select from search result record number is not matched.: "+count+"/t Actual Record Count :"+reviewListRecordDig);
									result.add("Select from search result record number is not matched.: "+count+"/t Actual Record Count :"+reviewListRecordDig);
								}
								
							}else {
								appLog.error("Review Prospect List record count is "+reviewListRecordDig+" that means there is no selected contact name is available on grid so we cannot verify contacts in review prospect list");
								result.add("Review Prospect List record count is "+reviewListRecordDig+" that means there is no selected contact name is available on grid so we cannot verify contacts in review prospect list");
							}
						}else {
							appLog.error("Review Prospect List count is not visible so we cannot verify contacts in review prospect list");
							result.add("Review Prospect List record count is not visible so we cannot verify contacts in review prospect list");
						}
					}else {
						appLog.error("Not able to click on selected contact Name so cannot add contact in review add prospect list and verify contact Name");
						result.add("Not able to click on selected contact Name so cannot add contact in review add prospect list and verify contact Name");
					}
				}else {
					appLog.error("Add prospects list is not enable so we cannot select any contact from select prospects contact list");
					result.add("Add prospects list is not enable so we cannot select any contact from select prospects contact list");
				}
			}else {
				appLog.error("Select From Search Results record count is "+beforeRecordDig+" that means there is no contact is visible on grid so we cannot add prospects contacts");
				result.add("Select From Search Results record count is "+beforeRecordDig+" that means there is no contact is visible on grid so we cannot add prospects contacts");
			}
		}else {
			appLog.error("Select From Search Results record count is not visible so we cannot add prospects contacts");
			result.add("Select From Search Results record count is not visible so we cannot add prospects contacts");
		}
		return result;
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param contactName
	 * @param accountName
	 * @param timeout
	 * @return true/false
	 */
	public boolean ScrollAndClickOnContactNameCheckBoxInAddProspect(AddProspectsTab addProspectsTab,String contactName, String accountName,int timeout) {
		int j = 0;
		WebElement ele = null;
		String XpathelementTOSearch="";
		if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString()) || addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.PastMarketingInitiatives.toString())) {
			XpathelementTOSearch = "//span[@id='Select_from_Search_ResultsA-view-box']//span[contains(@id,'Select_from_Search_ResultsA-row-')]/span[3]//a[text()='"
			+ contactName + "']/../../following-sibling::span[1]//a[text()='" + accountName
			+ "']/../../preceding-sibling::span[2]/span/span[1]";
		}else {
			String[] splitedContactName=contactName.split(" ");
			for(int i = 0; i < 15; i++){
				if(currentlyExecutingTC.contains("Smoke")){
					String Fname = ExcelUtils.readData(BaseLib.smokeFilePath, "Contacts", excelLabel.Variable_Name, "SmokeC"+i, excelLabel.Contact_FirstName);
					String Lname = ExcelUtils.readData(BaseLib.smokeFilePath, "Contacts", excelLabel.Variable_Name, "SmokeC"+i, excelLabel.Contact_LastName);
					if((Fname+" "+Lname).equalsIgnoreCase(contactName)){
						splitedContactName[0]=Fname;
						splitedContactName[1]=Lname;
						break;
					}
				} else {
					String Fname = ExcelUtils.readData(BaseLib.testCasesFilePath, "Contacts", excelLabel.Variable_Name, "SmokeC"+i, excelLabel.Contact_FirstName);
					String Lname = ExcelUtils.readData(BaseLib.testCasesFilePath, "Contacts", excelLabel.Variable_Name, "SmokeC"+i, excelLabel.Contact_LastName);
					if((Fname+" "+Lname).equalsIgnoreCase(contactName)){
						splitedContactName[0]=Fname;
						splitedContactName[1]=Lname;
						break;
					}
				}
			}
			XpathelementTOSearch = "//span[@id='Select_from_Search_ResultsArep-view-box-middle']//a[text()='"+splitedContactName[0]+"']/../../following-sibling::span//a[text()='"+splitedContactName[1]+"']/../../../span[2]/span/span[1]";
		}
		
		//By byelementToSearch = By.xpath(XpathelementTOSearch);
		int widgetTotalScrollingHeight = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
				.executeScript("return arguments[0].scrollHeight", getSelectProspectsGridScrollBox(addProspectsTab,10))));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0,0)", getSelectProspectsGridScrollBox(addProspectsTab,10));
		ThreadSleep(2000);
		for (int i = 0; i <= widgetTotalScrollingHeight / 25; i++) {
			
			if(sendKeysAndPressEnter(driver, getSearchForAContactTextBoxOnReportTab(10), accountName, accountName, action.BOOLEAN)) {
				appLog.info("enter value in search box:"+accountName);
				
			}
			ThreadSleep(2000);
			if (!driver.findElements(By.xpath(XpathelementTOSearch)).isEmpty()
					|| driver.findElement(By.xpath(XpathelementTOSearch)).isDisplayed()) {
				appLog.info("Element Successfully Found and displayed");
				ThreadSleep(500);
				ele = FindElement(driver, XpathelementTOSearch, "", action.BOOLEAN, timeout);
				if (ele != null) {
					if (click(driver, ele, "", action.BOOLEAN)) {
						appLog.info("clicked on Contact Name : "+contactName);
						sendKeysAndPressEnter(driver, getSearchForAContactTextBoxOnReportTab(10), "", "", action.BOOLEAN);
					} else {
						appLog.error("Not able to clicke on Contact Name: "+contactName);
						return false;
					}
				}
				break;
			} else {
				System.out.println("Not FOund: " + By.xpath(XpathelementTOSearch).toString());
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 45) + ")",
						getSelectProspectsGridScrollBox(addProspectsTab,10));
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

	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param fieldName
	 * @param operator
	 * @param value
	 * @param ContactFullNameAndAccountName
	 * @param isCheckedforLink TODO
	 * @return true/false
	 */
	public boolean addProspects(String environment, String mode,AddProspectsTab addProspectsTab , String fieldName,String operator, String value,HashMap<String, String> ContactFullNameAndAccountName, boolean isCheckedforLink) {
		WebElement ele= null;
			ThreadSleep(5000);
			ele=getAddProspectsHideSearchPopUp(5);
			if(ele!=null) {
				if(click(driver, ele, "add prospects hide pop up image", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on add prospects hide pop up image");
				}else {
					appLog.error("Not able to click on add prospects hide pop up so cannot apply filter");
					return false;
				}
			}
			if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.PastMarketingInitiatives.toString())) {
				if(click(driver, getPastMarketingInitiativeTab(60), "past marketing initiative tab", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on past Marketing initiative tab");
				}else {
					appLog.error("Not able to click on past Marketing Initiative tab");
					return false;
				}
			}else if (addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.Report.toString())) {
				if(click(driver, getReportTab(60), "reports tab", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on reports tab");
				}else {
					appLog.error("Not able to click on report tab so cannot apply filter: "+fieldName+" "+operator+" "+value);
					return false;
				}
			}
			if(SearchforProspects(addProspectsTab,fieldName, operator, value)) {
				appLog.info("Filter has been applied successfully.");
				ThreadSleep(5000);
				List<String> result = selectProspectsContactAndVerifyReviewProspectList(environment,mode,addProspectsTab,ContactFullNameAndAccountName, isCheckedforLink);
				if(result.isEmpty()) {
					if(click(driver, getAddProspectMarketingInitiativeBtn(30), "add propects market initiative button", action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on add propects market initiative button");
						return true;
					}else {
						appLog.error("Not able to click on Add propects initiative button so cannot add contacts");
					}
				}else {
					for(int i=0;i<result.size(); i++) {
						appLog.error(result.get(i));
					}
				}
			}else {
				appLog.error("Not able to apply filter so cannot add contact in review prospect list");
			}
		return false;
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param addProspectsTab
	 * @param contactName
	 * @param accountName
	 * @return true/false
	 */
	public boolean removeContactFromReviewPorspectList(String environment, String mode,AddProspectsTab addProspectsTab,String contactName, String accountName) {
		WebElement ele=null;
		String Xpath="//span[@id='gridReviewList-view-box']//span[contains(@id,'gridReviewList-row-')]/span[text()='"+contactName+"']/../span[text()='"+accountName+"']/../span[2]/a";
		ele=getReviewProspectListRecord(60);
		if(ele!=null) {
			String reviewListRecord=ele.getText().trim();
			int reviewListRecordDig = Integer.parseInt((reviewListRecord.split(":")[1].trim()));
			if(reviewListRecordDig!=0) {
				ele=isDisplayed(driver, FindElement(driver,Xpath, "", action.BOOLEAN,30), "visibility",20,contactName+" Contact remove Icon");
				if(ele!=null) {
					if(click(driver, ele, contactName+" remove icon", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						ele=getReviewProspectListRecord(60);
						if(ele!=null) {
							String reviewListRecordAfterRemoveContact=ele.getText().trim();
							int reviewListRecordDigAfterRemoveContact = Integer.parseInt((reviewListRecordAfterRemoveContact.split(":")[1].trim()));
							if(reviewListRecordDigAfterRemoveContact==reviewListRecordDig-1) {
								appLog.info("review porspect list record count "+(reviewListRecordDig-1)+" is matched after remove contact "+contactName);
								return true;
							}else {
								appLog.error("review porspect list record count "+(reviewListRecordDig-1)+" is not matched after remove contact "+contactName+" \t Actual : "+reviewListRecordDigAfterRemoveContact);
							}
						}else {
							appLog.error("Review Prospect List count is not visible after remove contact "+contactName+" so we cannot verify record count");
						}
					}else {
						appLog.error("Not able to click on contact "+contactName+" remove icon so cannot remove it from review prospect list");
					}
				}else {
					appLog.error(contactName+" remove icon is not visible in review porspects list so cannot remove contact :"+contactName);
				}
			}else {
				appLog.error("Review Prospect List record count is "+reviewListRecordDig+" that means there is no selected contact name is available on grid so we cannot remove contacts in review prospect list");
			}
		}else {
			appLog.error("Review Prospect List count is not visible so we cannot remove contacts in review prospect list");
		}
		return false;
	}


	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param fieldName
	 * @param operator
	 * @param value
	 * @return true/false
	 */
	public boolean SearchforEmailProspects(String environment, String mode,PageName pageName,String fieldName, String operator, String value) {
		WebElement ele= null;
		String xpath="";
		if(fieldName!=null && operator!=null && !fieldName.isEmpty() && !operator.isEmpty()) {
			if(sendKeys(driver, getEmailProspectFieldTextBox(30), fieldName, "field text box", action.SCROLLANDBOOLEAN)) {
				if(pageName.toString().equalsIgnoreCase(PageName.emailFundraisingContact.toString())) {
					xpath="//ul[@class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all']//a[text()='"+fieldName+"']";
				}else {
					xpath="//ul[@class='ui-menu ui-widget ui-widget-content ui-autocomplete ui-front']//div[text()='"+fieldName+"']";
				}
				ThreadSleep(2000);
				ele=isDisplayed(driver,FindElement(driver, xpath, "field auto complete text", action.SCROLLANDBOOLEAN,30),"visibility",30,"field auto complete text");
				if(ele!=null) {
					if(click(driver, ele, fieldName+" text", action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on field name "+fieldName+" text box");
						if(selectVisibleTextFromDropDown(driver, getEmailProspectOperatorDropDownList(30), "operator drop down list", operator)) {
							appLog.info("Select Operator : "+operator);
							if(value!=null && !value.isEmpty()) {
								if(sendKeys(driver, getEmailProspectValueTextBox(20), value, "Value text box", action.SCROLLANDBOOLEAN)) {
									appLog.info("passed value in Value Text Box: "+value);
								}else {
									appLog.error("Not able to pass value in Value text box: "+value+" so cannot apply filter");
									return false;
								}
							}
							if(click(driver, getEmailProspectApplyBtn(30), "search button", action.SCROLLANDBOOLEAN)) {
								appLog.info("clicked on Search button successfully");
								return true;
							}else {
								appLog.error("Not able to click on search button so cannot apply filter");
							}
						}else {
							appLog.error("Not able to select operator: "+operator+" so cannot apply filter");
						}
					}else {
						appLog.error("Not able to click on field "+fieldName+" so cannot apply filter");
					}
				}else {
					appLog.error(fieldName+" is not visible in field auto complete text box so cannot apply filter.");
				}
			}else {
				appLog.error("Not able to pass value in field auto complete text box : "+fieldName+" so cannot apply filter");
			}
		}else {
			return true;
		}
		return false;
		
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param contactName
	 * @param accountName
	 * @param timeout
	 * @return true/false
	 */
	public boolean ScrollAndClickOnContactNameCheckBoxInEmailProspect(PageName pageName,String contactName, String accountName,int timeout) {
		int j = 0;
		WebElement ele = null;
		String XpathelementTOSearch="";
		if(pageName.toString().equalsIgnoreCase(PageName.emailFundraisingContact.toString())) {
			XpathelementTOSearch ="//span[contains(@class,'aw-grid-row')]//a[text()='"+contactName+"']/../following-sibling::span/a[text()='"+accountName+"']/../../span[2]/span/span[1]";
		}else {
			XpathelementTOSearch = "//span[contains(@class,'aw-hpanel-middle')]//span[contains(@class,'aw-grid-row')]//a[text()='"+contactName+"']/../following-sibling::span[text()='"+accountName+"']/../span[2]/span/span[1]";
		}
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

	/**
	 * @author Ankit Jaiswal
	 * @param ContactFullNameAndAccountName
	 * @param searchContact
	 * @return true/false
	 */
	public List<String> selectProspectsContactAndVerifyReviewProspectListInEmailProspect(PageName pageName,HashMap<String, String> ContactFullNameAndAccountName, searchContactInEmailProspectGrid searchContact) {
		List<String> result = new ArrayList<String>();
		WebElement ele = null;
		Set<String> contactFullName = ContactFullNameAndAccountName.keySet();
		Iterator<String> itr = contactFullName.iterator();
		while (itr.hasNext()) {
			String contactName = itr.next();
			String accountName=ContactFullNameAndAccountName.get(contactName);
			if(searchContact.toString().equalsIgnoreCase(searchContactInEmailProspectGrid.Yes.toString())) {
				if(sendKeys(driver, getEmailProspectSearchTextBox(20), contactName, "search text box", action.SCROLLANDBOOLEAN)) {
					appLog.info("Passed Value in Search Text box: "+contactName);
					ThreadSleep(2000);
					try {
						getEmailProspectSearchTextBox(20).sendKeys(Keys.ENTER);
						log(LogStatus.INFO, "press ENTER key successfully", YesNo.No);
					}catch (Exception e) {
						// TODO: handle exception
						getEmailProspectSearchTextBox(20).sendKeys(Keys.ENTER);
						log(LogStatus.INFO, "press ENTER key successfully", YesNo.Yes);
					}
					ThreadSleep(2000);
					ele=getEmailProspectSelctContactRecordCount(20);
					int RecordCount=0;
					if(ele!=null) {
						if(pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString())) {
							 RecordCount=Integer.parseInt(ele.getText().trim());
						}else {
							 RecordCount=Integer.parseInt(ele.getText().trim().split(":")[1]);
						}
						if(RecordCount==1 && RecordCount!=0) {
							appLog.info("Email Prospect Record Count is matched Successfully.");
						}else {
							appLog.error("Email Prospect Record Count is not Matched. Expected: 1"+"\t Actual :"+RecordCount);
							result.add("Email Prospect Record Count is not Matched. Expected: 1"+"\t Actual :"+RecordCount);
						}
					}else {
						appLog.error("Email Prospect Record Count is not visible so cannot verify record Count");
						result.add("Email Prospect Record Count is not visible so cannot verify record Count");
					}
					
				}else {
					appLog.error("Not able to pass value "+contactName+" in search text box so cannot search contact: "+contactName);
					result.add("Not able to pass value "+contactName+" in search text box so cannot search contact: "+contactName);
				}
			}
			if(ScrollAndClickOnContactNameCheckBoxInEmailProspect(pageName,contactName, accountName, 10)) {
				appLog.info("clicked on Contact Name Check Box: "+contactName);
			}else {
				appLog.error("Not able to click on Contact Name :"+contactName+" check box so cannot add contact in review prospect list");
				result.add("Not able to click on Contact Name :"+contactName+" check box so cannot add contact in review prospect list");
			}
		}
		return result;
	}
	

	/**
	 * @author Ankit Jaiswal
	 * @param folderName
	 * @param emailTemplateName
	 * @return true/false
	 */
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

	public boolean EmailProspects(String environment,String mode,PageName pageName,String fieldName,String operator, String value,HashMap<String, String> ContactFullNameAndAccountName,searchContactInEmailProspectGrid searchContact,String folderName,String emailTemplateName,String processingOptionsName, boolean isToCheckButton) {
		boolean flag= true;
		WebElement ele= null;
		ThreadSleep(5000);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		String msg=null;
		ThreadSleep(30000);
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			if(pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString())) {
				switchToFrame(driver, 20, getMarketInitiativeFrame_Lightning(20));
			}else {
				FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
				switchToFrame(driver, 20,getEmailFundRaisingContact_Lightning(20));
			}
		}
		ele=getEmailProspectHideSearchPopUp(5);
		if(ele!=null) {
			if(click(driver, ele, "email prospects hide pop up image", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on email prospects hide pop up image",YesNo.No);
			}else {
				log(LogStatus.INFO, "Not able to click on email prospects hide pop up so cannot apply filter",YesNo.No);
				return false;
			}
		}
		if(SearchforEmailProspects(environment, mode, pageName, fieldName, operator, value)) {
			log(LogStatus.INFO, "Filter applied successfully", YesNo.No);
			
		
			
			if (isToCheckButton) {
				
				// Azhar Start
				if(sendKeys(driver, getEmailProspectSearchTextBox(20), value, "search text box", action.SCROLLANDBOOLEAN)) {
					appLog.info("Passed Value in Search Text box: "+value);
					ThreadSleep(2000);
					try {
						getEmailProspectSearchTextBox(20).sendKeys(Keys.ENTER);
						log(LogStatus.INFO, "press ENTER key successfully", YesNo.No);
					}catch (Exception e) {
						// TODO: handle exception
						getEmailProspectSearchTextBox(20).sendKeys(Keys.ENTER);
						log(LogStatus.INFO, "press ENTER key successfully", YesNo.Yes);
					}
					}else {
					appLog.error("Not able to pass value "+value+" in search text box so cannot search contact: "+value);
					flag=false;
				}
				
				// Azhar End
			}
						
			
			List<String> result=selectProspectsContactAndVerifyReviewProspectListInEmailProspect(pageName,ContactFullNameAndAccountName, searchContact);
			if(result.isEmpty()) {
				log(LogStatus.INFO, "Contacts is selected successfully", YesNo.No);
				if(click(driver,getEmailProspectStep1NextBtn(20), "step 1 next button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked in step 1 next button", YesNo.No);
					
					
					if (isToCheckButton) {
						
						// Azhar Start
						
						ele = hp.step2_BulkEmailPage(environment, mode, 10);
						
						if (ele!=null) {
							msg = ele.getText().trim();
							if (HomePageErrorMessage.Step2_SelectAnEmailTemplate.equals(msg)) {
								log(LogStatus.PASS, "Step2 Page Verified : "+msg, YesNo.No);
							} else {
								flag=false;
								log(LogStatus.FAIL, "Step2 Page Verified Not Verified Actual : "+msg+" \t Expected :"+HomePageErrorMessage.Step2_SelectAnEmailTemplate, YesNo.Yes);
							}
						} else {
							flag = false;
							log(LogStatus.FAIL, "Step2 Page Element is null", YesNo.Yes);
						
						}
						
						String expectedResult = "All,Others,"+folderName;
						List<WebElement> lst = allOptionsInDropDrop(driver,getEmailProspectFolderDropDownList(20), "folder drop down list");
						if (compareMultipleList(driver, expectedResult, lst).isEmpty()) {
							log(LogStatus.INFO, "Folder Drop Down list All options is verified", YesNo.No);
						} else {
							flag=false;
							log(LogStatus.ERROR, "Folder Drop Down list All options is not verified", YesNo.Yes);
						}
						
						
						if (click(driver, getEmailProspectStep2PreviousBtn(10), "Step 2 Previous Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on step 2 Previous button", YesNo.No);
							ThreadSleep(2000);
							
							if (click(driver, getEmailProspectStep1NextBottomBtn(10), "step 1 next Bottom button", action.BOOLEAN)) {
								log(LogStatus.INFO, "clicked on step 1 next Bottom button", YesNo.No);
								ThreadSleep(2000);
								
								if (click(driver, getEmailProspectStep2PreviousBottomBtn(10), "step 2 Previous Bottom button", action.BOOLEAN)) {
									log(LogStatus.INFO, "clicked on step 2 Previous Bottom button", YesNo.No);
									ThreadSleep(2000);
									
									if (click(driver, getEmailProspectStep1NextBottomBtn(10), "step 1 next Bottom button", action.BOOLEAN)) {
										log(LogStatus.INFO, "clicked on step 1 next Bottom button", YesNo.No);
										ThreadSleep(2000);
										
									} else {
										log(LogStatus.ERROR, "Not Able to clicked on step 1 next Bottom button", YesNo.Yes);
										flag= false;
									}
									
								} else {
									log(LogStatus.ERROR, "Not Able to clicked on step 2 Previous Bottom button", YesNo.Yes);
									flag= false;
								}
								
							} else {
								log(LogStatus.ERROR, "Not Able to clicked on step 1 next Bottom button", YesNo.Yes);
								flag= false;
							}
							
						} else {
							log(LogStatus.ERROR, "Not Able to clicked on step 2 Previous button", YesNo.Yes);
							flag= false;
						}
						// Azhar End
					}
					
					
					
					
					
					if(selectEmailTemplateFromEmailProspect(folderName, emailTemplateName)) {
						log(LogStatus.INFO, emailTemplateName+" email template is selected successfully", YesNo.No);
						if(clickOnEmailTemplatePreviewLink(emailTemplateName)) {
							log(LogStatus.INFO, emailTemplateName+" is open in preview mode successfully", YesNo.No);
						}else {
							log(LogStatus.ERROR, emailTemplateName+" is not open in preview mode", YesNo.Yes);
							flag=false;
						}

						ThreadSleep(30000);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							if(pageName.toString().equalsIgnoreCase(PageName.emailProspects.toString())) {
								switchToFrame(driver, 20, getMarketInitiativeFrame_Lightning(20));
							}else {
								FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
								switchToFrame(driver, 20,getEmailFundRaisingContact_Lightning(20));
							}
						}
						
						
						if (isToCheckButton) {
							
							// Azhar Start
							
							if(click(driver,getEmailProspectStep2NextBtn(30), "step 2 next button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Steps 2 next button", YesNo.No);
								
								if(click(driver,getEmailProspectStep3PreviousBtn(30), "step 3 Previous button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Steps 3 Previous button", YesNo.No);
									
									if(click(driver,getEmailProspectStep2NextBottomBtn(30), "step 2 next Bottom button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Steps 2 next Bottom button", YesNo.No);
										
										if(click(driver,getEmailProspectStep3PreviousBottomBtn(30), "step 3 Previous Bottom button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Steps 3 Previous Bottom button", YesNo.No);
										}else{
											log(LogStatus.INFO, "Not Able to Clicked on Steps 3 Previous button", YesNo.No);
											flag = false;
										}
										
									}else{
										log(LogStatus.INFO, "Not Able to Clicked on Steps 2 next Bottom button", YesNo.No);
										flag = false;
									}
									
								}else{
									log(LogStatus.INFO, "Not Able to Clicked on Steps 3 Previous button", YesNo.No);
									flag = false;
								}
								
							}else{
								log(LogStatus.INFO, "Not Able to Clicked on Steps 2 next button", YesNo.No);
								flag = false;
							}
							
							// Azhar End
							
						}
						
						
						if(click(driver,getEmailProspectStep2NextBtn(30), "step 2 next button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Steps 2 next button", YesNo.No);
							ele=getEmailProspectsSelectedRecipientErrorMsg(pageName,20);
							if(ele!=null) {
								String aa = ele.getText().trim();
								if(aa.contains(MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg(String.valueOf(ContactFullNameAndAccountName.size())))) {
									log(LogStatus.INFO, MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg(String.valueOf(ContactFullNameAndAccountName.size()))+" error message is verified", YesNo.No);
									if(processingOptionsName!=null) {
										String[] spltdProcessingOption=processingOptionsName.split(",");
										for(int i=0; i<spltdProcessingOption.length;i++) {
											if(ClickOnProcessingOptionCheckBox(processingOptionsName)) {
												log(LogStatus.INFO, "clicked on "+spltdProcessingOption[i]+" check box", YesNo.No);
												
											}else {
												log(LogStatus.ERROR, "Not able to click on "+spltdProcessingOption[i]+"check box",YesNo.Yes);
												flag=false;
											}
										}
									}
									if(flag) {
										if(click(driver,getEmailProspectSendBtn(TopOrBottom.BOTTOM, 30), "send button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on send button", YesNo.No);
											ele=getEmailProspectSendEmailCongratulationsErrorMsg(30);
											String errorMsg=MarketingInitiativesPageErrorMsg.congratulationErrorMsg+" "+MarketingInitiativesPageErrorMsg.generatedEmailedRecipientErrorMsg(String.valueOf(ContactFullNameAndAccountName.size()), "Email");
											if(ele!=null) {
												String aa1 =ele.getText().trim();
												if(aa1.contains(MarketingInitiativesPageErrorMsg.congratulationErrorMsg) && aa1.contains(MarketingInitiativesPageErrorMsg.generatedEmailedRecipientErrorMsg(String.valueOf(ContactFullNameAndAccountName.size()), "Email"))) {
													log(LogStatus.INFO, "Congratulation Error Message is verified", YesNo.No);
												}else {
													log(LogStatus.ERROR, "Congratulation Error Message is not verified. Expected: "+errorMsg+"\t Actual: "+aa1, YesNo.Yes);
												}
											}else {
												log(LogStatus.ERROR, "Congratulations Error Message is not visible so cannot verify it. Error Msg: "+errorMsg, YesNo.Yes);
											}
										}else {
											log(LogStatus.ERROR, "Not able to click on send button so cannot send email to contact", YesNo.Yes);
										}
									}
									if(flag) {
										if(click(driver,getEmailProspectFinishBtn(30), "finish button", action.BOOLEAN)) {
											log(LogStatus.INFO, "Clicked on finish button", YesNo.No);
											return true;
										}else {
											log(LogStatus.ERROR, "Not able to click on finish button", YesNo.Yes);
										}
									}
									
								}else {
									log(LogStatus.ERROR, "error message is not verified Expected: "+MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg(String.valueOf(ContactFullNameAndAccountName.size()))+" \t Actual : "+aa, YesNo.Yes);
								}
							}else {
								log(LogStatus.ERROR, MarketingInitiativesPageErrorMsg.selectRecipientOnStep3ErrorMsg(String.valueOf(ContactFullNameAndAccountName.size()))+" error message is not visible",YesNo.Yes);
							}
						}else {
							log(LogStatus.ERROR, "Not able to click on step 2 next button", YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to select email template : "+emailTemplateName+" so cannot send email", YesNo.Yes);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on step 1 next button so cannot send email to contact", YesNo.Yes);
				}
				
			}else {
				for(int i=0; i<result.size(); i++) {
					log(LogStatus.ERROR, result.get(i), YesNo.Yes);
				}
			}
		}else {
			log(LogStatus.ERROR, "Not able to apply filter "+fieldName+" "+operator+" "+value+" so cannot select contact and send mail",YesNo.Yes);
		}
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToDefaultContent(driver);
		}
		return false;
		
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param ele
	 * @return int
	 */
	public int labelColumnIndex(WebElement ele){
		if(ele!=null){
			String attributeValue = ele.getAttribute("id");
			appLog.info("getIntegerFromString(attributeValue).get(0) : "+getIntegerFromString(attributeValue).get(0));
			appLog.info("getIntegerFromString(attributeValue).get(1) : "+getIntegerFromString(attributeValue).get(1));
			return getIntegerFromString(attributeValue).get(1);
		}else{
			return 1;	
		}		
	}
		
	/**
	 * @author Ankit Jaiswal
	 * @return list of webelement
	 */
	public List<WebElement> getemailProspectContentGrid(String headerName,int timeout) {
		List<WebElement> eleList = new ArrayList<WebElement>();
		String xpath="";
		int i =labelColumnIndex(getEmailProspectContactNameColumn(headerName,timeout));
		appLog.info("Column Label Index : "+i);
		if(headerName.equalsIgnoreCase("Contact Name") || headerName.equalsIgnoreCase("Email")) {
			xpath="//span[contains(@id,'aw')][contains(@id,'cell-"+i+"')]/a";
		}else {
			xpath="//span[contains(@id,'aw')][contains(@id,'cell-"+i+"')]";
		}
		eleList=FindElements(driver,xpath, headerName+" List");
		return eleList;

	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param processingOptionsName
	 * @return true/false
	 */
	public boolean ClickOnProcessingOptionCheckBox(String processingOptionsName ) {
		WebElement ele=null;
		String xpath="//td[text()='"+processingOptionsName+"']/following-sibling::td//span";
		ele = isDisplayed(driver, FindElement(driver,xpath, "", action.BOOLEAN,10), "visibility",10,processingOptionsName+" check box");
		if(ele!=null) {
			if(click(driver, ele, processingOptionsName+" check box", action.BOOLEAN)) {
				log(LogStatus.INFO,"clicked on check box "+processingOptionsName, YesNo.No);
				return true;
			}else {
				log(LogStatus.ERROR, "Not able to click on check box "+processingOptionsName, YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, processingOptionsName+"check is not visible so cannot click on check box", YesNo.Yes);
		}
		return false;
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param emailTemplateName
	 * @return true/false
	 */
	public boolean clickOnEmailTemplatePreviewLink(String emailTemplateName) {
		boolean flag = false;
		WebElement ele=null;
		String parentWindow=null;
		ele=emailProspectPreviewTemplateLink(emailTemplateName, 10);
		if(ele!=null) {
			if(click(driver, ele, emailTemplateName+" preview link", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on "+emailTemplateName+" email template link",YesNo.No);
				parentWindow=switchOnWindow(driver);
				if(parentWindow!=null) {
					String xpath="//td[text()='"+emailTemplateName+"']";
					ele = isDisplayed(driver, FindElement(driver,xpath, "", action.BOOLEAN,20), "visibility",20,emailTemplateName+" template name");
					if(ele!=null) {
						log(LogStatus.INFO, emailTemplateName+" template is open in preview mode", YesNo.No);
						flag=true;
					}else {
						log(LogStatus.ERROR, emailTemplateName+" template is open in preview mode", YesNo.Yes);
					}
					driver.close();
					driver.switchTo().window(parentWindow);
				}else {
					log(LogStatus.ERROR, "No new window is open after click on Capital Call Notice template preview link", YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on "+emailTemplateName+" email template link so cannot check preview email template", YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, emailTemplateName+" email template preview link is not visible so cannot click on it", YesNo.Yes);
		}
		
		return flag;
		
	}

	public WebElement getAddRowLink(AddProspectsTab addProspectsTab) {
		String Xpath="";
		
		if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString())) {
			Xpath="AddRow2";
		}else {
			Xpath="AddRow4";
		}
		return isDisplayed(driver,FindElement(driver, "//a[@id='"+Xpath+"']", "Add row button", action.BOOLEAN,10), "Visibility", 10, "Add row button");
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param addProspectsTab
	 * @param fieldName
	 * @param operator
	 * @param value
	 * @param addFilterLogic TODO
	 * @return true/false
	 */
	public boolean SearchforProspects(AddProspectsTab addProspectsTab,String fieldName, String operator, String value, String addFilterLogic) {
		WebElement ele= null;
		String[] splitedFieldName=fieldName.split(",");
		String [] splitedOperator=operator.split(",");
		String[] Splitedvalue=value.split(",");
		String Xpath="";
		for(int i=0; i<splitedFieldName.length; i++) {
			
			if(i<splitedFieldName.length-1) {
				if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString())) {
					Xpath="AddRow2";
				}else {
					Xpath="AddRow4";
				}
				ele=isDisplayed(driver,FindElement(driver, "//a[@id='"+Xpath+"']", "Add row button", action.BOOLEAN,10), "Visibility", 10, "Add row button");
				if(ele!=null) {
					if(click(driver, ele, "add row button", action.BOOLEAN)) {
						appLog.info("clicked on add row link");
					}else {
						appLog.error("Not able to click on add row button so cannot add new row and enter filter data "+splitedFieldName[i]+" "+splitedOperator[i]+" "+Splitedvalue[i]);
						return false;
					}
				}else {
					appLog.error("Not able find all row link so cannot add new row and enter filter data "+splitedFieldName[i]+" "+splitedOperator[i]+" "+Splitedvalue[i]);
					return false;
				}
			}
			if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString())) {
				Xpath="dealinputleftcolmn"+(i+1)+"";
			}else {
				Xpath="inputrytcolmn"+(i+1)+"";
			}
			ele=isDisplayed(driver,FindElement(driver, "//input[@name='"+Xpath+"']", "add Prospect Field Auto Complete TextBox", action.BOOLEAN,10), "Visibility", 10, "add Prospect Field Auto Complete TextBox");
			if(sendKeys(driver,ele, splitedFieldName[i], "field text box", action.BOOLEAN)) {
				ele=null;
				ThreadSleep(2000);
				Xpath="//ul[@class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content'][contains(@style,'display: block;')]/li[text()='"+splitedFieldName[i]+"']";
				ele=FindElement(driver, Xpath, "field auto complete text", action.BOOLEAN,5);
				if(ele!=null) {
					ThreadSleep(2000);
					if(click(driver, ele, splitedFieldName[i]+" text", action.BOOLEAN)) {
						appLog.info("clicked on field name "+splitedFieldName[i]+" text box");
						if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString())) {
							Xpath="opt"+(i+1)+"";
						}else {
							Xpath="optb"+(i+1)+"";
						}
						ele=FindElement(driver, "//select[@id='"+Xpath+"']", "Add Prospect Operator DropDownList", action.BOOLEAN,10);
						if(selectVisibleTextFromDropDown(driver, ele, "operator drop down list", splitedOperator[i])) {
							appLog.info("Select Operator : "+splitedOperator[i]);
							if(Splitedvalue[i]!=null && !Splitedvalue[i].isEmpty()) {
								if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString())) {
									Xpath="criteriatextBox"+(i+1)+"";
								}else {
									Xpath="criteriatextBoxMP"+(i+1)+"";
								}
								ele=FindElement(driver, "//input[@id='"+Xpath+"']", "add Prospect Value TextBox", action.BOOLEAN,10);
								if(sendKeys(driver, ele, Splitedvalue[i], "Value text box", action.BOOLEAN)) {
									appLog.info("passed value in Value Text Box: "+Splitedvalue[i]);
								}else {
									appLog.error("Not able to pass value in Value text box: "+Splitedvalue[i]+" so cannot apply filter");
									return false;
								}
							}
						}else {
							appLog.error("Not able to select operator: "+splitedOperator[i]+" so cannot apply filter");
							return false;
						}
					}else {
						appLog.error("Not able to click on field "+splitedFieldName[i]+" so cannot apply filter");
						return false;
					}
				}else {
					appLog.error(splitedFieldName[i]+" is not visible in field auto complete text box so cannot apply filter.");
					return false;
				}
			}else {
				appLog.error("Not able to pass value in field auto complete text box : "+splitedFieldName[i]+" so cannot apply filter");
				return false;
			}
		}
		if(addFilterLogic!=null) {
			if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString())) {
				Xpath="addprospectid:add_prspct_frm:cmdlink";
			}else {
				Xpath="addprospectid:add_prspct_frm:cmdlink_MP";
			}
			ele=isDisplayed(driver,FindElement(driver, "//a[@id='"+Xpath+"']", "Add filter logic link", action.BOOLEAN,10), "Visibility", 10, "Add filter logic link");
			if(ele!=null) {
				if(click(driver, ele, "add row button", action.BOOLEAN)) {
					appLog.info("clicked on add row link");
					ThreadSleep(2000);
					if(addProspectsTab.toString().equalsIgnoreCase(AddProspectsTab.AccountAndContacts.toString())) {
						Xpath="addprospectid:add_prspct_frm:textfilt";
					}else {
						Xpath="addprospectid:add_prspct_frm:textfilt_MP";
					}
					ele=isDisplayed(driver,FindElement(driver, "//input[@id='"+Xpath+"']", "Add filter logic text box", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
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
		if(click(driver, getAddProspectSearchBtn(addProspectsTab, 30), "search button", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on Search button successfully");
			return true;
		}else {
			appLog.error("Not able to click on search button so cannot apply filter");
		}
		return false;
		
	}
	
	
	/**
	 * @author Ankit Jaiswal
	 * @param ContactFullNameAndAccountName
	 * @param isAllContact TODO
	 * @return true/false
	 */
	public List<String> selectProspectsContactAndVerifyReviewProspectList(String environment,String mode,AddProspectsTab addProspectsTab,HashMap<String, String> ContactFullNameAndAccountName, boolean isAllContact,boolean isCheckedforLink) {
		List<String> result = new ArrayList<String>();
		WebElement ele = null;
		int count=0;
		String xpath=null;
		String parentId=null;
		ele=getSelectFromSearchResultsRecord(60);
		if(ele!=null) {
			String beforeRecord=ele.getText().trim();
			int beforeRecordDig = Integer.parseInt((beforeRecord.split(":")[1].trim()));
			if(beforeRecordDig!=0) {
				appLog.error("select  from search result record count is: "+beforeRecordDig);
				Set<String> contactFullName = ContactFullNameAndAccountName.keySet();
				Iterator<String> itr = contactFullName.iterator();
				
				if (isAllContact) {
					 xpath = "//span[@id='Select_from_Search_ResultsA-header-0-box']/span[1]";
					 ele = FindElement(driver, xpath, "All CheckBox", action.SCROLLANDBOOLEAN, 10);
					if (click(driver, ele, "All CheckBox", action.SCROLLANDBOOLEAN)) {
						appLog.error("click on All check box in add contact in review prospect list");	
					} else {
						appLog.error("Not able to click on All check box so cannot add contact in review prospect list");
						result.add("Not able to click on All check box so cannot add contact in review prospect list");
					
					}
				} else {
				
					while (itr.hasNext()) {
						String contactName = itr.next();
						String accountName=ContactFullNameAndAccountName.get(contactName);
						
						
						if(ScrollAndClickOnContactNameCheckBoxInAddProspect(addProspectsTab,contactName, accountName, 10)) {
							appLog.info("clicked on Contact Name Check Box: "+contactName);
							
							if (isCheckedforLink) {
								
								if (count==0) {
									
									String[] linkClick = {contactName,accountName};
									
									for (int j = 0; j < linkClick.length; j++) {
										
										xpath = "//div/a[text()='"+linkClick[j]+"']";
										ele = FindElement(driver, xpath, linkClick[j], action.BOOLEAN, 10);
										
										if (ele!=null) {
											
											if (click(driver, ele, linkClick[j], action.BOOLEAN)) {
												
											parentId = 	switchOnWindow(driver);
											
											if (parentId!=null) {
												ThreadSleep(2000);
												if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
													xpath = "//div/span[contains(text(),'"+linkClick[j]+"')]";
													}else{
														xpath = "//h2[contains(text(),'"+linkClick[j]+"')]";	
													}
												
												ele = FindElement(driver, xpath, "on new window : "+linkClick[j], action.BOOLEAN, 10);
												
												if (ele!=null) {
													appLog.info("Landing Page Verified : "+linkClick[j]);
												} else {
													appLog.error("Landing Page Not Verified : "+linkClick[j]);
													result.add("Landing Page Not Verified : "+linkClick[j]);
												}
												
												driver.close();
												driver.switchTo().window(parentId);
												switchToDefaultContent(driver);
												
											} else {
												appLog.error("Not New Window for "+linkClick[j]);
												result.add("Not New Window for "+linkClick[j]);
											}
											
										
											
											if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
												
												MarketingInitiativesPageBusinesslayer market = new MarketingInitiativesPageBusinesslayer(driver);
												switchToFrame(driver, 5, market .getMarketInitiativeFrame_Lightning(10));	
											}
											
											
											
												
											} else {
												appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
												result.add("Not able to click on "+linkClick[j]+" so cannot verify landing page");
											}
										} else {
											appLog.error("Not able to click on "+linkClick[j]+" so cannot verify landing page");
											result.add("Not able to click on "+linkClick[j]+" so cannot verify landing page");
										}
										
										
									}
									
									
								}
								
							}
							
							count++;
						}else {
							appLog.error("Not able to click on Contact Name :"+contactName+" check box so cannot add contact in review prospect list");
							result.add("Not able to click on Contact Name :"+contactName+" check box so cannot add contact in review prospect list");
						}	
						
					}
					
				}
				
				
				ele=getAddToProspectListBtn(5);
				if(ele!=null) {
					if(click(driver, getAddToProspectListBtn(5), "add prospect list button", action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on add prospect list button");
						ThreadSleep(5000);
						ele=getSelectFromSearchResultsRecord(60);
						if(ele!=null) {
							String afterRecordCount=ele.getText().trim();
							int AfterRecordDig = Integer.parseInt((afterRecordCount.split(":")[1].trim()));
							int ExpectedCount=beforeRecordDig-count;
							
							if (!isAllContact) {
								if(AfterRecordDig==ExpectedCount) {
									appLog.info("Select from search result record count is matched : "+AfterRecordDig);
								}else {
									appLog.error("Select from search result record count is not matched. Expected: "+ExpectedCount+" \t Actual Result: "+beforeRecordDig);
									result.add("Select from search result record count is not matched. Expected: "+ExpectedCount+" \t Actual Result: "+beforeRecordDig);
								}	
							}
							
						}else {
							appLog.error("Select from search result record is not visible after add contact in prospect list grid so cannot verify record number of select  from search result.");
							result.add("Select from search result record is not visible after add contact in prospect list grid so cannot verify record number of select  from search result.");
						}
						ThreadSleep(5000);
						ele=getReviewProspectListRecord(60);
						if(ele!=null) {
							String reviewListRecord=ele.getText().trim();
							int reviewListRecordDig = Integer.parseInt((reviewListRecord.split(":")[1].trim()));
							if(reviewListRecordDig!=0) {
								itr = contactFullName.iterator();
								while (itr.hasNext()) {
									String contactName = itr.next();
									String accountName=ContactFullNameAndAccountName.get(contactName);
									 xpath="//span[@id='gridReviewList-view-box']//span[contains(@id,'gridReviewList-row-')]/span[text()='"+contactName+"']/../span[text()='"+accountName+"']";
									ele = isDisplayed(driver, FindElement(driver,xpath, "", action.BOOLEAN,20), "visibility",20,contactName+" select Contact Name");
									if(ele!=null) {
										appLog.info(contactName+ " :Contact Name is selected successfully in add review prospect list");
									}else {
										appLog.error(contactName+ " :Contact Name is not available in add review prospect list");
										result.add(contactName+ " :Contact Name is not available in add review prospect list");
									}
								}
								if(reviewListRecordDig==count) {
									appLog.info("Select from search result record number is matched. Record Count is: "+count);
								}else {
									appLog.error("Select from search result record number is not matched.: "+count+"/t Actual Record Count :"+reviewListRecordDig);
									result.add("Select from search result record number is not matched.: "+count+"/t Actual Record Count :"+reviewListRecordDig);
								}
								
							}else {
								appLog.error("Review Prospect List record count is "+reviewListRecordDig+" that means there is no selected contact name is available on grid so we cannot verify contacts in review prospect list");
								result.add("Review Prospect List record count is "+reviewListRecordDig+" that means there is no selected contact name is available on grid so we cannot verify contacts in review prospect list");
							}
						}else {
							appLog.error("Review Prospect List count is not visible so we cannot verify contacts in review prospect list");
							result.add("Review Prospect List record count is not visible so we cannot verify contacts in review prospect list");
						}
					}else {
						appLog.error("Not able to click on selected contact Name so cannot add contact in review add prospect list and verify contact Name");
						result.add("Not able to click on selected contact Name so cannot add contact in review add prospect list and verify contact Name");
					}
				}else {
					appLog.error("Add prospects list is not enable so we cannot select any contact from select prospects contact list");
					result.add("Add prospects list is not enable so we cannot select any contact from select prospects contact list");
				}
			}else {
				appLog.error("Select From Search Results record count is "+beforeRecordDig+" that means there is no contact is visible on grid so we cannot add prospects contacts");
				result.add("Select From Search Results record count is "+beforeRecordDig+" that means there is no contact is visible on grid so we cannot add prospects contacts");
			}
		}else {
			appLog.error("Select From Search Results record count is not visible so we cannot add prospects contacts");
			result.add("Select From Search Results record count is not visible so we cannot add prospects contacts");
		}
		return result;
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param addColumnFieldsName
	 * @param removeColumnFieldsName
	 * @param viewFieldsName
	 * @param searchtext
	 * @return empty list if pass
	 */
	public List<String> addAndRemoveCloumnInSelectProspectGrid(String mode,PageName pageName,String[] addColumnFieldsName, String[] removeColumnFieldsName,ViewFieldsName viewFieldsName,Boolean searchtext ) {
		List<String> result = new ArrayList<String>();
		if(click(driver, getSelectProspectsWrenchIcon(pageName,mode,60), "wrench icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(10000);
			if(viewFieldsName!=null) {
				if(selectVisibleTextFromDropDown(driver, getColumnToDisplayViewDropDownList(pageName,30), "view drop down list", viewFieldsName)) {
					appLog.info("Value selected from view drop down list : "+viewFieldsName);
					
				}else {
					appLog.error("Not able to select value from view drop down list so cannot add or remove column from column to display pop up");
					result.add("Not able to select value from view drop down list so cannot add or remove column from column to display pop up");
				}
			}
			List<WebElement> avalFieldsWebelement=getColumnToDisplayAvailableFieldsTextList(pageName);
			List<WebElement> selectedFieldsWebelement=getColumnToDisplaySelectedFieldsTextList(pageName);
			if(addColumnFieldsName!=null) {
				if(!avalFieldsWebelement.isEmpty()) {
					for(int i=0; i<addColumnFieldsName.length; i++) {
						avalFieldsWebelement=getColumnToDisplayAvailableFieldsTextList(pageName);
						selectedFieldsWebelement=getColumnToDisplaySelectedFieldsTextList(pageName);
						if(!selectedFieldsWebelement.isEmpty()) {
							for(int k=0; k<selectedFieldsWebelement.size(); k++) {
								if(addColumnFieldsName[i].equalsIgnoreCase(selectedFieldsWebelement.get(k).getText().trim())) {
									appLog.info(addColumnFieldsName[i]+" : Cloumn is already selected in column to display ");
									result.add(addColumnFieldsName[i]+" : Cloumn is already selected in column to display ");
									break;
								}
							}
						}
						for(int j=0; j<avalFieldsWebelement.size(); j++) {
							if(searchtext) {
								if(sendKeys(driver, getColumnToDisplaySearchTextBox(pageName,20), addColumnFieldsName[i],"search text box", action.BOOLEAN)) {
									appLog.info("passed value in search text box "+addColumnFieldsName[i]);
									ThreadSleep(5000);
//									Robot rob;
//									try {
//										rob = new Robot();
//										ThreadSleep(5000);
//										rob.keyPress(KeyEvent.VK_ENTER);
//										ThreadSleep(2000);
//										rob.keyRelease(KeyEvent.VK_ENTER);
//										ThreadSleep(2000);
//									}catch (Exception e) {
//										appLog.error("Not able to press ENTER keyboard opertaion");
//										result.add("Not able to press ENTER keyboard opertaion");
//									}
									if(click(driver, getSearchIconInDisplayToColumn(pageName, 10), "search icon", action.BOOLEAN)) {
										appLog.info("clicked on search icon");
									}else {
										appLog.info("Not able to click on search icon so cannot search : "+addColumnFieldsName[i]);
										result.add("Not able to click on search icon so cannot search : "+addColumnFieldsName[i]);
									}
								}else {
									appLog.info("Not able to pass value in search text box : "+addColumnFieldsName[i]);
									result.add("Not able to pass value in search text box : "+addColumnFieldsName[i]);
								}
							}
							ThreadSleep(2000);
							if(click(driver, avalFieldsWebelement.get(j), "available fields text", action.SCROLLANDBOOLEAN)) {
								ThreadSleep(1000);
								if(click(driver, getColumnToDisplayLeftToRightBtn(pageName,30), "left to right button", action.BOOLEAN)) {
									appLog.info("clicked on left to right button and column is  added on selected grid "+addColumnFieldsName[i]);
									if(searchtext) {
										if(click(driver, getColumnToDisplaySearchTextCrossIcon(pageName,20), "search text cross icon", action.BOOLEAN)) {
											appLog.info("clicked on search text box cross icon");
											
										}else {
											appLog.info("Not able to click on search text box cross icon so cannot remove value from text box  "+addColumnFieldsName[i]);
											result.add("Not able to click on search text box cross icon so cannot remove value from text box  "+addColumnFieldsName[i]);
										}
									}
									break;
									
								}else {
									appLog.error("Not able to click on left to right move button so cannot add column : "+addColumnFieldsName[i]);
									result.add("Not able to click on left to right move button so cannot add column : "+addColumnFieldsName[i]);
								}
							}else {
								appLog.error("Not able to select text from available fields : "+addColumnFieldsName[i]);
								result.add("Not able to select text from available fields : "+addColumnFieldsName[i]);
							}
						}
					}
					
				}else {
					appLog.error("Column To Display Available Fields Text list is not found so cannot add columns in select prospect grid");
					result.add("Column To Display Available Fields Text list is not found so cannot add columns in select prospect grid");
				}
			}
			if(removeColumnFieldsName!=null) {
				if(!selectedFieldsWebelement.isEmpty()) {
					for(int i=0; i<removeColumnFieldsName.length; i++) {
						for(int j=0; j<selectedFieldsWebelement.size(); j++) {
							if(click(driver, selectedFieldsWebelement.get(j), "selected fields text", action.SCROLLANDBOOLEAN)) {
								ThreadSleep(1000);
								if(click(driver, getColumnToDisplayRightToLeftBtn(pageName,30), "right to left button", action.BOOLEAN)) {
									appLog.info("clicked on right to left button and column is  remove on selected grid "+removeColumnFieldsName[i]);
									break;
									
								}else {
									appLog.error("Not able to click on left to right move button so cannot add column : "+removeColumnFieldsName[i]);
									result.add("Not able to click on left to right move button so cannot add column : "+removeColumnFieldsName[i]);
								}
							}else {
								appLog.error("Not able to select text from selected fields : "+removeColumnFieldsName[i]);
								result.add("Not able to select text from selected fields : "+removeColumnFieldsName[i]);
							}
						}
					}
					
				}else {
					appLog.error("Column To Display Selected Fields Text list is not found so cannot remove columns in select prospect grid");
					result.add("Column To Display Selected Fields Text list is not found so cannot remove columns in select prospect grid");
				}
			}
			if(click(driver, getColumnToDisplayApplyBtn(pageName,20), "apply button", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on save button");
			}else {
				appLog.error("Not able to click on save button so cannot close cloumn to display popup");
				result.add("Not able to click on save button so cannot close cloumn to display popup");
			}
		}else {
			appLog.error("Not able to click on wrench icon so cannot add or remove cloumns in selects prospect grid");
			result.add("Not able to click on wrench icon so cannot add or remove cloumns in selects prospect grid");
		}
		return result;
		
	}
	
	public boolean columnToDisplayRevertToDefaultsSettings(PageName pageName,String mode) {
		if(click(driver, getSelectProspectsWrenchIcon(pageName,mode,60), "wrench icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(10000);
			if(click(driver, getColumnToDisplayRevertToDefaultBtn(20), "revert to default button", action.BOOLEAN)) {
				appLog.info("Clicked on revert to default button");
				ThreadSleep(2000);
				if(click(driver, getColumnToDisplayApplyBtn(pageName,20), "apply button", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on save button");
					return true;
				}else {
					appLog.error("Not able to click on save button so cannot close cloumn to display popup");
				}
			}else {
				appLog.error("Not able to click on revert to default button");
				
			}
		}else {
			appLog.error("Not able to click on wrench icon so cannot add or remove cloumns in selects prospect grid");
		}
		return false;
	}


	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param fieldName
	 * @param operator
	 * @param value
	 * @return true/false
	 */
	public boolean SearchforEmailProspects(String environment, String mode,PageName pageName,String fieldName, String operator, String value,String addFilterLogic) {
		WebElement ele= null;
		String xpath="";
		String[] splitedFieldName=fieldName.split(",");
		String [] splitedOperator=operator.split(",");
		String[] Splitedvalue=value.split(",");
		for(int i=0; i<splitedFieldName.length; i++) {
			if(fieldName!=null && operator!=null && !fieldName.isEmpty() && !operator.isEmpty()) {
				if(i<splitedFieldName.length-1) {
					ele=isDisplayed(driver,FindElement(driver, "//a[@id='AddRow2']", "Add row button", action.BOOLEAN,10), "Visibility", 10, "Add row button");
					if(ele!=null) {
						if(click(driver, ele, "add row button", action.BOOLEAN)) {
							appLog.info("clicked on add row link");
						}else {
							appLog.error("Not able to click on add row button so cannot add new row and enter filter data "+splitedFieldName[i]+" "+splitedOperator[i]+" "+Splitedvalue[i]);
							return false;
						}
					}else {
						appLog.error("Not able find all row link so cannot add new row and enter filter data "+splitedFieldName[i]+" "+splitedOperator[i]+" "+Splitedvalue[i]);
						return false;
					}
				}
				ThreadSleep(2000);
				ele = FindElement(driver, "//input[@id='a"+(i+1)+"aa']", "field text box", action.BOOLEAN,10);
				if(sendKeys(driver, ele, splitedFieldName[i], "field text box", action.BOOLEAN)) {
					ThreadSleep(5000);
					if(pageName.toString().equalsIgnoreCase(PageName.emailFundraisingContact.toString())) {
						xpath="//ul[contains(@style,'block;')]//a[text()='"+splitedFieldName[i]+"']";
					}else {
						xpath="//ul[@class='ui-menu ui-widget ui-widget-content ui-autocomplete ui-front'][contains(@style,'block;')]//div[text()='"+splitedFieldName[i]+"']";
					}
					ele=FindElement(driver, xpath, "field auto complete text", action.BOOLEAN,30);
					if(ele!=null) {
						if(click(driver, ele, splitedFieldName[i]+" text", action.BOOLEAN)) {
							appLog.info("clicked on field name "+splitedFieldName[i]+" text box");
							ThreadSleep(2000);
							ele = FindElement(driver, "//select[@id='opt"+(i+1)+"']", "operator drop down list", action.BOOLEAN,5);
							if(selectVisibleTextFromDropDown(driver,ele, "operator drop down list", splitedOperator[i])) {
								appLog.info("Select Operator : "+splitedOperator[i]);
								if(Splitedvalue[i]!=null && !Splitedvalue[i].isEmpty()) {
									ele = FindElement(driver, "//input[@id='criteriatextbox"+(i+1)+"']", "Value text box", action.BOOLEAN,5);
									if(sendKeys(driver, ele, Splitedvalue[i], "Value text box", action.BOOLEAN)) {
										appLog.info("passed value in Value Text Box: "+Splitedvalue[i]);
									}else {
										appLog.error("Not able to pass value in Value text box: "+Splitedvalue[i]+" so cannot apply filter");
										return false;
									}
								}
							}else {
								appLog.error("Not able to select operator: "+splitedOperator[i]+" so cannot apply filter");
								return false;
							}
						}else {
							appLog.error("Not able to click on field "+splitedFieldName[i]+" so cannot apply filter");
							return false;
						}
					}else {
						appLog.error(splitedFieldName[i]+" is not visible in field auto complete text box so cannot apply filter.");
						return false;
					}
				}else {
					appLog.error("Not able to pass value in field auto complete text box : "+splitedFieldName[i]+" so cannot apply filter");
					return false;
				}
			}else {
				appLog.error("Field Name and Operator cannot be empty or null so cannot apply filter please pass value in field and operator");
				return false;
			}
		}
		if(addFilterLogic!=null) {
			ele=isDisplayed(driver,FindElement(driver, "//a[@id='pageid:frm:cmdlink']", "Add filter logic link", action.BOOLEAN,10), "Visibility", 10, "Add filter logic link");
			if(ele!=null) {
				if(click(driver, ele, "add row button", action.BOOLEAN)) {
					appLog.info("clicked on add row link");
					ThreadSleep(2000);
					ele=isDisplayed(driver,FindElement(driver, "//input[@id='pageid:frm:textfilt']", "Add filter logic text box", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
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
		if(click(driver, getEmailProspectApplyBtn(30), "search button", action.BOOLEAN)) {
			appLog.info("clicked on Search button successfully");
			return true;
		}else {
			appLog.error("Not able to click on search button so cannot apply filter");
		}
		return false;
	}
	


}
