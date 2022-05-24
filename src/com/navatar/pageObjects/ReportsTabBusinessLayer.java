package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ReportDashboardFolderType;
import com.navatar.generic.EnumConstants.ReportField;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import static com.navatar.generic.CommonLib.*;

public class ReportsTabBusinessLayer extends ReportsTab{

	public ReportsTabBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public boolean createCustomReportOrDashboardFolder(String environment, String folderName,
			ReportDashboardFolderType folderType, FolderAccess folderAccess) {
		boolean flag = false;
		EmailMyTemplatesPageBusinessLayer epb = new EmailMyTemplatesPageBusinessLayer(driver);

		if (switchToClassic()) {
			appLog.info("Switch To Classic");
			ThreadSleep(3000);
			
			if (clickOnTab(environment, Mode.Classic.toString(), TabName.ReportsTab)) {
				appLog.info("Click on Report Tab");
				
				if (click(driver, getReportFolderIcon_Classic(environment, 30), "Folder Icon",
						action.SCROLLANDBOOLEAN)) {
					appLog.info("Click on Report Folder Icon");
					
					WebElement reportType = FindElement(driver, "//span[text()='" + folderType + "']", "Folder Type",
							action.SCROLLANDBOOLEAN, 10);
					if (click(driver, reportType, "Folder Icon : "+reportType, action.SCROLLANDBOOLEAN)) {
						appLog.info("Click on Folder type : " + folderType);
						
						if (sendKeys(driver, getFolderNameTextBox_Classic(environment, 30), folderName, folderName,
								action.SCROLLANDBOOLEAN)) {
							appLog.info("Entered Value on Folder Name Text bOX : " + folderName);
							
//							if (selectVisibleTextFromDropDown(driver,
//									epb.getpublicFolderAccesDropdown(environment, Mode.Classic.toString(), 10),
//									folderAccess + "   : Access dropdown", folderAccess)) {
//								appLog.info("Selected Access Type : " + folderAccess);
								
								if (click(driver, getSaveButton(environment, Mode.Classic.toString(), 60),
										"Save Button", action.SCROLLANDBOOLEAN)) {
									
									appLog.info("Click on Save button");
									WebElement ele = FindElement(driver,
											"//span[contains(text(),'" + folderName + "')]", "Header",
											action.SCROLLANDBOOLEAN, 30);
									
									if (ele != null) {
										appLog.info("Folder created and Matched :  " + folderName);
										mouseHoverJScript(driver, getCreateReportFolderNameInSideTree(folderName, 20));
										if(getCreateReportFolderNamePinIconInSideTree(folderName, 20)!=null) {
											String FolderId=getCreateReportFolderNamePinIconInSideTree(folderName, 10).getAttribute("id");
											if(clickOnAddPinIcon(FolderId, folderName)) {
												appLog.info("clicked on folder Name "+folderName+" Pin Icon");
												ThreadSleep(1000);
												if(click(driver, getReportFolderShareText(10), "share button", action.BOOLEAN)) {
													appLog.info("clicked On share Option");
													ThreadSleep(1000);
													if(click(driver, getUserLinkInSharePopUp(10),"User Link", action.BOOLEAN)) {
														appLog.info("clicked on Users Link");
														ThreadSleep(500);
														if(click(driver, getUserShareButton(), "CRM User share button", action.BOOLEAN)) {
															appLog.info("clicked on CRM User Share Button");
															if(click(driver, getReportFolderAccessDownArrow(), "Report folder access down arrow", action.BOOLEAN)) {
																appLog.info("Clicked on Access Down Arrow");
																ThreadSleep(500);
																if(click(driver, getReportFolderNameManagerText(10), "Report Folder Name Manager Text", action.BOOLEAN)) {
																	appLog.info("Clicked on Report Folder Name Manager Text");
																	ThreadSleep(500);
																	if(click(driver, getReportFolderSharePopUpDoneAndCloseButton(10), "Report Folder Share PopUp Done And Close Button", action.BOOLEAN)) {
																		appLog.info("Clicked on Report Folder Name Share Done button");
																		ThreadSleep(500);
																		if(click(driver, getReportFolderSharePopUpDoneAndCloseButton(10), "Report Folder Share PopUp Done And Close Button", action.BOOLEAN)) {
																			appLog.info("Clicked on Report Folder Name Share Close button");
																			flag = true;
																			
																		}else {
																			appLog.error("Not able to click on report folder Share Close button But Report Folder Name is shared to CRM User");
																		}
																	}else {
																		appLog.error("Not able to click on report folder Share Done button so cannot Share Report to CRM User");
																	}
																	
																}else {
																	appLog.error("Not able to click on report folder Manager text so cannot Share Report to CRM User");
																}
															}else {
																appLog.error("Not able to click Access Down Arrow so cannot Share Report to CRM User");
															}
														}else {
															appLog.error("Not able to click on CRM User share button so cannot share Report to CRM User");
														}
														
													}else {
														appLog.error("Not able to click on Users Link in share PopUp so cannot share report to crm user");
													}
												}else {
													appLog.error("Not able to click on Share text under report folder pin icon so cannot share report to crm user");
												}
											}else {
												appLog.error("Not able to click report folder pin icon so cannot share icon");
											}
										}else {
											appLog.error("Not able to get create Report Folder "+folderName+" Name from side tree so cannot share CRM User");
										}
									} else {
										appLog.error("Folder created But Not Matched :  " + folderName);
									}
									
								} else {
									appLog.error("Not Able to Click on Save Button");
								}
//							} else {
//								appLog.error("Not Able to Select Access from drop down : "+folderAccess);
//							}
						} else {
							appLog.error("Not Able to Enter value on Email Template Folder Label Text Box");
						}
					} else {
						appLog.error("Not Able to Click on Report Type : "+reportType);
					}
				} else {
					appLog.error("Not Able to Click on Create New Folder Link");
				}
			} else {
				appLog.error("Not Able to Click on Report Tab so cannot create Folder");
			}
		} else {
			appLog.error("Not Able to swich Classic so cannot create Folder");
		}
		return flag;
	}
	
	
	public boolean clickOnAddPinIcon(String id, String folderName) {
		((JavascriptExecutor) driver).executeScript("document.getElementById('"+id+"').setAttribute('class', 'x-btn folderButton pinningOption x-btn-noicon');");
		scrollDownThroughWebelement(driver, FindElement(driver,
				"//table[@id='"+id+"']", "Report Folder Name Pin Icon", action.BOOLEAN, 20),
				"Add Folder Button");
		return clickUsingJavaScript(driver,FindElement(driver, "//table[@id='"+id+"']", "folder Name pin Icon", action.BOOLEAN, 10), "folder name pin icon", action.BOOLEAN);
	}
	
	public boolean createCustomReportForFolder(String environment, String mode, String folderName,ReportFormatName reportFormatName,
			String reportName, String reportDescription, String reportType, ReportField[] reportField, String showValue,
			String dateField, String rangeValue, String fromDate, String toDate) {
		boolean flag=true;
		if (switchToClassic()) {
			appLog.info("Switch To Classic");
			ThreadSleep(3000);
			if (clickOnTab(environment, Mode.Classic.toString(), TabName.ReportsTab)) {
				appLog.info("Click on Report Tab");
				if (click(driver, getNewReportBtn_Classic(environment, 60), "New Report Button",
						action.SCROLLANDBOOLEAN)) {
					appLog.info("Click on New Report Button");
					if (sendKeys(driver, getSearchBox_Classic(environment, 30), reportType, "search : " + reportType,
							action.SCROLLANDBOOLEAN)) {
						appLog.info("Entered value on Search Box : " + reportType);
						ThreadSleep(3000);
						WebElement reportTypeEle = FindElement(driver, "//span[text()='" + reportType + "']",
								reportType, action.SCROLLANDBOOLEAN, 20);
						if (click(driver, reportTypeEle, "Clicked : " + reportType, action.SCROLLANDBOOLEAN)) {
							ThreadSleep(2000);
							appLog.info("Click on Report Type : " + reportType);
							if (click(driver, getCreateBtn_Classic(environment, 10), "Create Button",
									action.SCROLLANDBOOLEAN)) {
								ThreadSleep(2000);
								appLog.info("Click on Create Button");
								if (showValue != null) {
									if (click(driver, getShowIcon_Classic(environment, 10), "Show Icon",
											action.SCROLLANDBOOLEAN)) {
										appLog.info("Click on Shown Icon");
										WebElement showValueEle = FindElement(driver,
												"//div[text()='" + showValue + "']", "Show value : " + showValue,
												action.SCROLLANDBOOLEAN, 10);
										if (click(driver, showValueEle, "Show value : " + showValue,
												action.SCROLLANDBOOLEAN)) {
											appLog.info("Selected Shown Value : " + showValue);
										} else {
											appLog.error(
													"Not Able to Select on Value of Show drop down : " + showValue);
											flag = false;
										}

									} else {
										appLog.error("Not Able to Click on Show Value Icon");
									}
								}

								if (rangeValue != null) {
									if (click(driver, getRangeIcon_Classic(environment, 10), "Show Icon",
											action.SCROLLANDBOOLEAN)) {
										appLog.info("Click on Range Icon");
										WebElement rangeValueEle = FindElement(driver,
												"//div[text()='" + rangeValue + "']", "Range value : " + rangeValue,
												action.SCROLLANDBOOLEAN, 10);
										if (click(driver, rangeValueEle, "Show value : " + rangeValue,
												action.SCROLLANDBOOLEAN)) {
											appLog.info("Selected Range Value : " + rangeValue);
										} else {
											appLog.error(
													"Not Able to Select on Value of Range drop down : " + rangeValue);
											flag = false;
										}

									} else {
										appLog.error("Not Able to Click on Show Value Icon");
									}
								}
								if (reportField!=null) {
									for(ReportField report:reportField){
										ThreadSleep(2000);
									String value=	report.toString().replaceAll("_", " ");
									if (sendKeys(driver, getSearchBox_Classic(environment, 30), value,
											"search : " + value, action.SCROLLANDBOOLEAN)) {
										appLog.info("Entered value on Search Box for report Field : " + value);
										ThreadSleep(2000);
										WebElement dragEle = FindElement(driver,
												"//div[@id='fieldsTree']//span[text()='" + value + "']",
												"Drag Field : " + value.toString(), action.SCROLLANDBOOLEAN, 10);
										WebElement dropLocation = FindElement(driver,
												"//div[@id='previewPanelGrid']//tr//td//div[text()='Salutation']",
												"Drop Location Salutation", action.SCROLLANDBOOLEAN, 10);

										if (dragNDropOperation(driver, dragEle, dropLocation)) {

											appLog.info("Drag & Drop Successfully");
										} else {
											appLog.error("Not Able to Drag and Drop Element ");
										}

									} else {
										appLog.error("Not Able to enter value on search box for field : " + value);
									}
									}
								}
								else {
									appLog.info("creating report without contact id");
								}
								if(!reportFormatName.toString().equalsIgnoreCase(ReportFormatName.Null.toString())) {
									if(click(driver, getReportFormatName(20), "report format drop down", action.SCROLLANDBOOLEAN)) {
										appLog.info("clicked on report format drop down");
										ThreadSleep(500);
										if(click(driver, getreportFormatName(reportFormatName), "report format name ", action.SCROLLANDBOOLEAN)) {
											appLog.info("clicked on report format name : "+reportFormatName.toString());
										}else {
											appLog.error("Not able to select report format name "+reportFormatName.toString()+" so cannot create report");
											return false;
										}
									}else {
										appLog.error("Not able to click on report format drop down so cannot create report");
										return false;
									}
								}
								if (click(driver, getSaveBtn_Classic(environment, 10), "Save Button",
										action.SCROLLANDBOOLEAN)) {

									appLog.info("Clicked on Save Button");
									if (sendKeys(driver, getReportNameTextBox_Classic(environment, 10),
											reportName, "Report Name Text Box : " + reportName,
											action.SCROLLANDBOOLEAN)) {

										appLog.info("Entered value for Report Name : " + reportName);

										if (sendKeys(driver,
												getReportDescriptionTextBox_Classic(environment, 10),
												reportDescription, "Report Description : " + reportDescription,
												action.SCROLLANDBOOLEAN)) {

											appLog.info("Entered value for Report Description : " + reportName);
											if (click(driver,
													getReportFolderIconOnSaveReport_Classic(environment, 10),
													"Report Folder Icon", action.SCROLLANDBOOLEAN)) {

												ThreadSleep(3000);
												appLog.info("Clicked on Report Folder Icon on Save Report");

												WebElement reportFolderValueEle = FindElement(driver,
														"//div[text()='" + folderName + "']",
														"Folder value : " + folderName, action.SCROLLANDBOOLEAN,
														10);

												if (click(driver, reportFolderValueEle,
														"Folder value : " + folderName,
														action.SCROLLANDBOOLEAN)) {

													appLog.info("Selected Report Folder Value : "+folderName);

													if (click(driver,
															getSaveBtnOnSaveReport_Classic(environment, 10),
															"Save Button on Save Report",
															action.SCROLLANDBOOLEAN)) {

														appLog.info("Clicked on Save Button on Save Report");

														WebElement reportNameHeaderEle = FindElement(driver,
																"//h2[contains(text(),'" + reportName + "')]",
																"Heading : " + reportName,
																action.SCROLLANDBOOLEAN, 30);

														if (reportNameHeaderEle != null) {
															appLog.info("Report Created and Matched: "
																	+ reportName);
															return flag;
														} else {
															appLog.error("Report Created but not Matched: "
																	+ reportName);
														}

													} else {
														appLog.error(
																"Not Able to click on Save Button on Svae Report ");
													}

												} else {
													appLog.error(
															"Not Able to Select on Report Folder Value of Show drop down : "
																	+ folderName);
												}
											} else {
												appLog.error(
														"Not Able to click on Report Folder Icon on Save Report ");
											}
										} else {
											appLog.error(
													"Not Able to Enter value on Report Description Text Aread :  "
															+ reportDescription);
										}

									} else {
										appLog.error("Not Able to Enter value on Report Name Text Box :  "
												+ reportName);
									}

								} else {
									appLog.error("Not Able to click ON Save Btn ");
								}



							} else {
								appLog.error("Not Able to Click on Create Button");
							}

						} else {
							appLog.error("Not Able to Select Report Type : " + reportType);
						}
					} else {
						appLog.error("Not Able to enter value on search box so cannot create Report for Folder");
					}

				} else {
					appLog.error("Not Able to click on New Report Button so cannot create Report for Folder");
				}
			} else {
				appLog.error("Not Able to click on Report Tab so cannot create Report for Folder");
			}

		} else {
			appLog.error("Not Able to swich Classic so cannot create Report for Folder");
		}
		return flag;

	}
}
