package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ReportDashboardFolderType;
import com.navatar.generic.EnumConstants.ReportField;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.object;
import com.relevantcodes.extentreports.LogStatus;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import static com.navatar.generic.CommonLib.*;

import java.util.List;
import java.util.stream.Collectors;

public class ReportsTabBusinessLayer extends ReportsTab {

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
					if (click(driver, reportType, "Folder Icon : " + reportType, action.SCROLLANDBOOLEAN)) {
						appLog.info("Click on Folder type : " + folderType);

						if (sendKeys(driver, getFolderNameTextBox_Classic(environment, 30), folderName, folderName,
								action.SCROLLANDBOOLEAN)) {
							appLog.info("Entered Value on Folder Name Text bOX : " + folderName);

//							if (selectVisibleTextFromDropDown(driver,
//									epb.getpublicFolderAccesDropdown(environment, Mode.Classic.toString(), 10),
//									folderAccess + "   : Access dropdown", folderAccess)) {
//								appLog.info("Selected Access Type : " + folderAccess);

							if (click(driver, getSaveButton(environment, Mode.Classic.toString(), 60), "Save Button",
									action.SCROLLANDBOOLEAN)) {

								appLog.info("Click on Save button");
								WebElement ele = FindElement(driver, "//span[contains(text(),'" + folderName + "')]",
										"Header", action.SCROLLANDBOOLEAN, 30);

								if (ele != null) {
									appLog.info("Folder created and Matched :  " + folderName);
									mouseHoverJScript(driver, getCreateReportFolderNameInSideTree(folderName, 20));
									if (getCreateReportFolderNamePinIconInSideTree(folderName, 20) != null) {
										String FolderId = getCreateReportFolderNamePinIconInSideTree(folderName, 10)
												.getAttribute("id");
										if (clickOnAddPinIcon(FolderId, folderName)) {
											appLog.info("clicked on folder Name " + folderName + " Pin Icon");
											ThreadSleep(1000);
											if (click(driver, getReportFolderShareText(10), "share button",
													action.BOOLEAN)) {
												appLog.info("clicked On share Option");
												ThreadSleep(1000);
												if (click(driver, getUserLinkInSharePopUp(10), "User Link",
														action.BOOLEAN)) {
													appLog.info("clicked on Users Link");
													ThreadSleep(500);
													if (click(driver, getUserShareButton(), "CRM User share button",
															action.BOOLEAN)) {
														appLog.info("clicked on CRM User Share Button");
														if (click(driver, getReportFolderAccessDownArrow(),
																"Report folder access down arrow", action.BOOLEAN)) {
															appLog.info("Clicked on Access Down Arrow");
															ThreadSleep(500);
															if (click(driver, getReportFolderNameManagerText(10),
																	"Report Folder Name Manager Text",
																	action.BOOLEAN)) {
																appLog.info(
																		"Clicked on Report Folder Name Manager Text");
																ThreadSleep(500);
																if (click(driver,
																		getReportFolderSharePopUpDoneAndCloseButton(10),
																		"Report Folder Share PopUp Done And Close Button",
																		action.BOOLEAN)) {
																	appLog.info(
																			"Clicked on Report Folder Name Share Done button");
																	ThreadSleep(500);
																	if (click(driver,
																			getReportFolderSharePopUpDoneAndCloseButton(
																					10),
																			"Report Folder Share PopUp Done And Close Button",
																			action.BOOLEAN)) {
																		appLog.info(
																				"Clicked on Report Folder Name Share Close button");
																		flag = true;

																	} else {
																		appLog.error(
																				"Not able to click on report folder Share Close button But Report Folder Name is shared to CRM User");
																	}
																} else {
																	appLog.error(
																			"Not able to click on report folder Share Done button so cannot Share Report to CRM User");
																}

															} else {
																appLog.error(
																		"Not able to click on report folder Manager text so cannot Share Report to CRM User");
															}
														} else {
															appLog.error(
																	"Not able to click Access Down Arrow so cannot Share Report to CRM User");
														}
													} else {
														appLog.error(
																"Not able to click on CRM User share button so cannot share Report to CRM User");
													}

												} else {
													appLog.error(
															"Not able to click on Users Link in share PopUp so cannot share report to crm user");
												}
											} else {
												appLog.error(
														"Not able to click on Share text under report folder pin icon so cannot share report to crm user");
											}
										} else {
											appLog.error(
													"Not able to click report folder pin icon so cannot share icon");
										}
									} else {
										appLog.error("Not able to get create Report Folder " + folderName
												+ " Name from side tree so cannot share CRM User");
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
						appLog.error("Not Able to Click on Report Type : " + reportType);
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
		((JavascriptExecutor) driver).executeScript("document.getElementById('" + id
				+ "').setAttribute('class', 'x-btn folderButton pinningOption x-btn-noicon');");
		scrollDownThroughWebelement(driver,
				FindElement(driver, "//table[@id='" + id + "']", "Report Folder Name Pin Icon", action.BOOLEAN, 20),
				"Add Folder Button");
		return clickUsingJavaScript(driver,
				FindElement(driver, "//table[@id='" + id + "']", "folder Name pin Icon", action.BOOLEAN, 10),
				"folder name pin icon", action.BOOLEAN);
	}

	public boolean createCustomReportForFolder(String environment, String mode, String folderName,
			ReportFormatName reportFormatName, String reportName, String reportDescription, String reportType,
			ReportField[] reportField, String showValue, String dateField, String rangeValue, String fromDate,
			String toDate) {
		boolean flag = true;
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
								if (reportField != null) {
									for (ReportField report : reportField) {
										ThreadSleep(2000);
										String value = report.toString().replaceAll("_", " ");
										if (sendKeys(driver, getSearchBox_Classic(environment, 30), value,
												"search : " + value, action.SCROLLANDBOOLEAN)) {
											appLog.info("Entered value on Search Box for report Field : " + value);
											ThreadSleep(2000);
											WebElement dragEle = FindElement(driver,
													"//div[@id='fieldsTree']//span[text()='" + value + "']",
													"Drag Field : " + value.toString(), action.SCROLLANDBOOLEAN, 10);
											WebElement dropLocation = FindElement(driver,
													"(//div[@id=\"previewPanelGrid\"]//tr)[1]",
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
								} else {
									appLog.info("creating report without contact id");
								}
								if (!reportFormatName.toString().equalsIgnoreCase(ReportFormatName.Null.toString())) {
									if (click(driver, getReportFormatName(20), "report format drop down",
											action.SCROLLANDBOOLEAN)) {
										appLog.info("clicked on report format drop down");
										ThreadSleep(500);
										if (click(driver, getreportFormatName(reportFormatName), "report format name ",
												action.SCROLLANDBOOLEAN)) {
											appLog.info(
													"clicked on report format name : " + reportFormatName.toString());
										} else {
											appLog.error("Not able to select report format name "
													+ reportFormatName.toString() + " so cannot create report");
											return false;
										}
									} else {
										appLog.error(
												"Not able to click on report format drop down so cannot create report");
										return false;
									}
								}
								if (click(driver, getSaveBtn_Classic(environment, 10), "Save Button",
										action.SCROLLANDBOOLEAN)) {

									appLog.info("Clicked on Save Button");
									if (sendKeys(driver, getReportNameTextBox_Classic(environment, 10), reportName,
											"Report Name Text Box : " + reportName, action.SCROLLANDBOOLEAN)) {

										appLog.info("Entered value for Report Name : " + reportName);

										if (sendKeys(driver, getReportDescriptionTextBox_Classic(environment, 10),
												reportDescription, "Report Description : " + reportDescription,
												action.SCROLLANDBOOLEAN)) {

											appLog.info("Entered value for Report Description : " + reportName);
											if (click(driver, getReportFolderIconOnSaveReport_Classic(environment, 10),
													"Report Folder Icon", action.SCROLLANDBOOLEAN)) {

												ThreadSleep(3000);
												appLog.info("Clicked on Report Folder Icon on Save Report");

												WebElement reportFolderValueEle = FindElement(driver,
														"//div[text()='" + folderName + "']",
														"Folder value : " + folderName, action.SCROLLANDBOOLEAN, 10);

												if (click(driver, reportFolderValueEle, "Folder value : " + folderName,
														action.SCROLLANDBOOLEAN)) {

													appLog.info("Selected Report Folder Value : " + folderName);

													if (click(driver, getSaveBtnOnSaveReport_Classic(environment, 10),
															"Save Button on Save Report", action.SCROLLANDBOOLEAN)) {

														appLog.info("Clicked on Save Button on Save Report");

														WebElement reportNameHeaderEle = FindElement(driver,
																"//h2[contains(text(),'" + reportName + "')]",
																"Heading : " + reportName, action.SCROLLANDBOOLEAN, 30);

														if (reportNameHeaderEle != null) {
															appLog.info("Report Created and Matched: " + reportName);
															return flag;
														} else {
															appLog.error(
																	"Report Created but not Matched: " + reportName);
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
												appLog.error("Not Able to click on Report Folder Icon on Save Report ");
											}
										} else {
											appLog.error("Not Able to Enter value on Report Description Text Aread :  "
													+ reportDescription);
										}

									} else {
										appLog.error(
												"Not Able to Enter value on Report Name Text Box :  " + reportName);
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

	public boolean createCustomReportForFolderLightningMode(String environment, String mode, String folderName,
			ReportFormatName reportFormatName, String reportName, String reportDescription, String reportType,
			ReportField[] reportField, String showValue, String dateField, String rangeValue, String fromDate,
			String toDate) throws InterruptedException {
		boolean flag = false;

		if (clickOnTab(environment, Mode.Lightning.toString(), TabName.ReportsTab)) {
			appLog.info("Click on Report Tab");
			driver.navigate().refresh();
			if (click(driver, newReportBtn_Lightning(60), "New Report Button", action.SCROLLANDBOOLEAN)) {
				appLog.info("Click on New Report Button");

				if (switchToFrame(driver, 40, iFrameReportTypeLightning(40))) {
					appLog.info("Successfully Switched into frame : ");
					if (click(driver, allLinkInReportType(60), "All Link in Report Type", action.SCROLLANDBOOLEAN)) {
						appLog.info("Click on All Link in Report Type");
						if (sendKeys(driver, ReportTypeSearchBox_Lightning(30), reportType, "search : " + reportType,
								action.SCROLLANDBOOLEAN)) {
							appLog.info("Entered value on Search Box : " + reportType);
							ThreadSleep(3000);
							WebElement reportTypeEle = FindElement(driver, "//p[text()='" + reportType + "']/parent::a",
									reportType, action.SCROLLANDBOOLEAN, 20);
							if (click(driver, reportTypeEle, "Clicked : " + reportType, action.SCROLLANDBOOLEAN)) {
								ThreadSleep(2000);
								appLog.info("Click on Report Type : " + reportType);
								if (click(driver, startReportButton(10), "Start Report Button",
										action.SCROLLANDBOOLEAN)) {
									ThreadSleep(2000);
									appLog.info("Click on Start Report Button");
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
												sa.assertTrue(false, "Not Able to Select on Value of Show drop down : " + showValue);
												flag = false;
											}

										} else {
											appLog.error("Not Able to Click on Show Value Icon");
											sa.assertTrue(false, "Not Able to Click on Show Value Icon");
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
												appLog.error("Not Able to Select on Value of Range drop down : "
														+ rangeValue);
												sa.assertTrue(false, "Not Able to Select on Value of Range drop down : "
														+ rangeValue);
												flag = false;
											}

										} else {
											appLog.error("Not Able to Click on Show Value Icon");
											sa.assertTrue(false, "Not Able to Click on Show Value Icon");
											
										}
									}
									if (reportField != null) {
										for (ReportField report : reportField) {
											flag = false;
											ThreadSleep(2000);
											String value = report.toString().replaceAll("_", " ");

											if (FieldsAlreadyAdded(30).getAttribute("innerHTML").contains(value)) {
												appLog.info(value + " :Field Already there in report");
												flag = true;
											} else {
												flag = false;
												appLog.info(value + " :Field Not there in report");
												appLog.info(value + " :So, adding to report");

												if (sendKeys(driver, addColumnSearchBoxLightning(30), value,
														"search : " + value, action.SCROLLANDBOOLEAN)) {
													appLog.info("Entered value on Search Box : " + value);
													WebElement columnToSelect = FindElement(driver,
															"//span[@title='" + value + "']",
															"Column to Add : " + value, action.SCROLLANDBOOLEAN, 10);
													if (columnToSelect != null) {

														if (click(driver, columnToSelect, "Show value : " + value,
																action.SCROLLANDBOOLEAN)) {
															appLog.info("Selected Value : " + value);

															if (FieldsAlreadyAdded(30).getAttribute("innerHTML")
																	.contains(value)) {
																appLog.info("Column Successfully added: " + value);
																flag = true;
															} else {
																appLog.error("Column not Successfully added: " + value);
																sa.assertTrue(false, "Column not Successfully added: " + value);	
															}
														} else {
															appLog.error("Column not Clickable: " + value);
														sa.assertTrue(false, "Column not Clickable: " + value);	
														}
													} else {
														appLog.error("Column not there in drop down: " + value);
														sa.assertTrue(false, "Column not there in drop down: " + value);	
													}
												}
											}

										}
									}
									/*
									 * else { appLog.info("creating report without contact id"); }
									 */
									/*
									 * if
									 * (!reportFormatName.toString().equalsIgnoreCase(ReportFormatName.Null.toString
									 * ())) { if (click(driver, getReportFormatName(20), "report format drop down",
									 * action.SCROLLANDBOOLEAN)) {
									 * appLog.info("clicked on report format drop down"); ThreadSleep(500); if
									 * (click(driver, getreportFormatName(reportFormatName), "report format name ",
									 * action.SCROLLANDBOOLEAN)) { appLog.info( "clicked on report format name : " +
									 * reportFormatName.toString()); } else {
									 * appLog.error("Not able to select report format name " +
									 * reportFormatName.toString() + " so cannot create report"); return false; } }
									 * else { appLog.error(
									 * "Not able to click on report format drop down so cannot create report");
									 * return false; } }
									 */

									if (flag) {
										flag = false;

										if (click(driver, getSaveBtn_Classic(environment, 10), "Save Button",
												action.SCROLLANDBOOLEAN)) {

											appLog.info("Clicked on Save Button");
											Thread.sleep(3000);

											if (CommonLib.clearTextBox(reportNameInputBoxLightning(20))) {

												String s = Keys.chord(Keys.CONTROL, "a");
												reportNameInputBoxLightning(20).sendKeys(s);
												// sending DELETE key
												reportNameInputBoxLightning(20).sendKeys(Keys.DELETE);
												if (sendKeys(driver, reportNameInputBoxLightning(20), reportName,
														"Report Name Text Box : " + reportName,
														action.SCROLLANDBOOLEAN)) {

													appLog.info("Entered value for Report Name : " + reportName);

													reportUniqueNameInputBoxLightning(20).sendKeys(s);
													// sending DELETE key
													reportUniqueNameInputBoxLightning(20).sendKeys(Keys.DELETE);
													if (CommonLib.clearTextBox(reportUniqueNameInputBoxLightning(20))) {

														if (sendKeys(driver, reportDescriptionBoxLightning(10),
																reportDescription,
																"Report Description : " + reportDescription,
																action.SCROLLANDBOOLEAN)) {

															appLog.info("Entered value for Report Description : "
																	+ reportName);
															if (click(driver, selectReportFolderButtonLightning(10),
																	"Report Folder Icon", action.SCROLLANDBOOLEAN)) {

																ThreadSleep(3000);
																appLog.info(
																		"Clicked on Report Folder Icon on Save Report");

																CommonLib.switchToDefaultContent(driver);
																WebElement reportFolderValueEle = FindElement(driver,
																		"//button[@title='" + folderName + "']",
																		"Folder value : " + folderName,
																		action.SCROLLANDBOOLEAN, 10);

																if (click(driver, reportFolderValueEle,
																		"Folder value : " + folderName,
																		action.SCROLLANDBOOLEAN)) {

																	appLog.info("Selected Report Folder Value : "
																			+ folderName);

																	if (click(driver,
																			selectReportFolderButtonLightning(20),
																			"Select Folder Button ",
																			action.SCROLLANDBOOLEAN)) {

																		appLog.info(
																				"Clicked on Select Folder Button  ");
																		if (switchToFrame(driver, 30,
																				iFrameReportTypeLightning(30))) {
																			appLog.info(
																					"Successfully Switched into frame : ");

																			if (click(driver,
																					saveButtonInSaveReportPopUpLightning(
																							20),
																					"Save Button on Save Report",
																					action.SCROLLANDBOOLEAN)) {

																				appLog.info(
																						"Clicked on Save Button on Save Report");

																				WebElement reportNameHeaderEle = FindElement(
																						driver,
																						"//h1[contains(text(),'"
																								+ reportName + "')]",
																						"Heading : " + reportName,
																						action.SCROLLANDBOOLEAN, 30);

																				if (reportNameHeaderEle != null) {
																					appLog.info(
																							"Report Created and Matched: "
																									+ reportName);

																					flag = true;
																				} else {
																					appLog.error(
																							"Report Created but not Matched: "
																									+ reportName);
																					sa.assertTrue(false, "Report Created but not Matched: "
																							+ reportName);

																				}

																			} else {
																				appLog.error(
																						"Not Able to click on Save Button on Save Report ");
																				sa.assertTrue(false, "Not Able to click on Save Button on Save Report ");
																			}
																		} else {
																			appLog.error(
																					"Not Able to Switch to IFrame ");
																			sa.assertTrue(false, "Not Able to Switch to IFrame ");

																		}
																	} else {
																		appLog.error(
																				"Not Able to Select on 'Select Folder' Button ");
																		sa.assertTrue(false, "Not Able to Select on 'Select Folder' Button ");
																	}

																} else {
																	appLog.error(
																			"Not Able to Select on Report Folder Value of Show drop down : "
																					+ folderName);
																	sa.assertTrue(false, "Not Able to Select on Report Folder Value of Show drop down : "
																			+ folderName);
																}

															} else {
																appLog.error(
																		"Not Able to click on Report Folder Icon on Save Report ");
																sa.assertTrue(false, "Not Able to click on Report Folder Icon on Save Report ");
															}
														} else {
															appLog.error(
																	"Not Able to Enter value on Report Description Text Aread :  "
																			+ reportDescription);
															sa.assertTrue(false, "Not Able to Enter value on Report Description Text Aread :  "
																	+ reportDescription);
														}
													} else {
														appLog.error(
																"Not Able to CLear Report Unique Name Text Box   ");
														sa.assertTrue(false, "Not Able to CLear Report Unique Name Text Box   ");
													}

												} else {
													appLog.error("Not Able to Enter value on Report Name Text Box :  "
															+ reportName);
													sa.assertTrue(false, "Not Able to Enter value on Report Name Text Box :  "
															+ reportName);
												}

											} else {
												appLog.error("Not Able to CLear Report Name Text Box   ");
												sa.assertTrue(false, "Not Able to CLear Report Name Text Box   ");
											}
										}

										else {
											appLog.error("Not Able to click ON Save Btn ");
											sa.assertTrue(false, "Not Able to click ON Save Btn ");
										}
									} else {
										appLog.error("Not Able to Add Columns to report ");
									sa.assertTrue(false, "Not Able to Add Columns to report ");
									}

								} else {
									appLog.error("Not Able to Click on Start Report Button");
									sa.assertTrue(false,"Not Able to Click on Start Report Button");
								}

							} else {
								appLog.error("Not Able to Select Report Type : " + reportType);
								sa.assertTrue(false, "Not Able to Select Report Type : " + reportType);
							}
						} else {
							appLog.error("Not Able to enter value on search box so cannot create Report for Folder");
							sa.assertTrue(false, "Not Able to enter value on search box so cannot create Report for Folder ");
						}
					} else {
						appLog.error("Not Able to Click on All Link in Report Type");
						sa.assertTrue(false, "Not Able to Click on All Link in Report Type");
					}
				} else {
					appLog.error("Not able to Switched to Frame");
					sa.assertTrue(false, "Not able to Switched to Frame");
				}

			} else {
				appLog.error("Not Able to click on New Report Button so cannot create Report for Folder");
				sa.assertTrue(false, "Not Able to click on New Report Button so cannot create Report for Folder");
			}

		} else {
			appLog.error("Not Able to click on Report Tab so cannot create Report for Folder");
			sa.assertTrue(false, "Not Able to click on Report Tab so cannot create Report for Folder");
		}

		CommonLib.switchToDefaultContent(driver);
		return flag;
	}

	public boolean addFilterInCustomReportLightning(String showDropDownValue, String dateFieldDropDownValue,
			String rangeDropDownValue, String customFieldDrpDownValue, String operatorDrpDownValue,
			String fieldFilterValue, String textBoxType) {
		boolean flag = false;

		if (switchToFrame(driver, 30, iFrameReportTypeLightning(30))) {
			appLog.info("Successfully Switched into frame : ");

			if (click(driver, filterButtonLightning(30), "Filter Button", action.SCROLLANDBOOLEAN)) {

				appLog.info("Clicked on Filter Button");

				if (showDropDownValue != null) {

					flag = false;
					if (click(driver, showMeFilterButtonLightning(30), "Show Me Filter Button",
							action.SCROLLANDBOOLEAN)) {

						appLog.info("Clicked on Show Me Filter Button");

						if (click(driver, showMeFilterDropDownButtonLightning(30), "Show Me Filter Drop Down Button",
								action.SCROLLANDBOOLEAN)) {

							appLog.info("Clicked on Show Me Filter Drop Down Button");

							WebElement showMeFilterValueEle = FindElement(driver,
									"//span[text()='" + showDropDownValue + "']/parent::a",
									"Show Me Filter value : " + showDropDownValue, action.SCROLLANDBOOLEAN, 10);

							if (click(driver, showMeFilterValueEle, "Show Me Filter value : " + showDropDownValue,
									action.SCROLLANDBOOLEAN)) {

								appLog.info("Selected Show Me Filter value : " + showDropDownValue);

								if (click(driver, applyButtonLightning(30), "Apply Button ", action.SCROLLANDBOOLEAN)) {

									appLog.info("Clicked on Apply Button");
									appLog.info("Show Me Filter Applied");
									flag = true;

								} else {
									appLog.error("Not able to Click on Apply Button");
								sa.assertTrue(false, "Not able to Click on Apply Button");
							}
							} else {
								appLog.error("Not able to Click on Show Me Filter Value: " + showDropDownValue);
								sa.assertTrue(false, "Not able to Click on Show Me Filter Value: " + showDropDownValue);
							}
						} else {
							appLog.error("Not able to Click on Show Me Filter Drop Down Button");
							sa.assertTrue(false, "Not able to Click on Show Me Filter Drop Down Button");
							
						}
					} else {
						appLog.error("Not able to Click on Show Me Filter Button");
						sa.assertTrue(false, "Not able to Click on Show Me Filter Button");
					}
				}

				else
				{
					appLog.error("Show Me Filter Value null, So Not Performing action on Show Me Filter");
					sa.assertTrue(false, "Show Me Filter Value null, So Not Performing action on Show Me Filter");
				}

				if (dateFieldDropDownValue != null) {

					flag = false;
					if (click(driver, dateRangeFilterButtonLightning(30), "Date Filter Button",
							action.SCROLLANDBOOLEAN)) {

						appLog.info("Clicked on Date Filter Button");

						if (click(driver, dateFilterDropDownButtonLightning(30), "Date Filter Drop Down Button",
								action.SCROLLANDBOOLEAN)) {

							appLog.info("Clicked on Date Filter Drop Down Button");

							WebElement dateFilterValueEle = FindElement(driver,
									"//div[@id='report-main']/following-sibling::span/following-sibling::span//span[@title='"
											+ dateFieldDropDownValue + "']",
									"Date Filter value : " + dateFieldDropDownValue, action.SCROLLANDBOOLEAN, 10);

							if (click(driver, dateFilterValueEle, "Date Filter value : " + dateFieldDropDownValue,
									action.SCROLLANDBOOLEAN)) {

								appLog.info("Selected Date Filter value : " + dateFieldDropDownValue);

								if (click(driver, applyButtonLightning(30), "Apply Button ", action.SCROLLANDBOOLEAN)) {

									appLog.info("Clicked on Apply Button");
									appLog.info("Date Filter Applied SUccessfully to :" + dateFieldDropDownValue);
									flag = true;
								} else {
									appLog.error("Not able to Click on Apply Button");
									sa.assertTrue(false, "Not able to Click on Apply Button");
								}
							} else {
								appLog.error("Not able to Click on Date Filter Value: " + dateFieldDropDownValue);
								sa.assertTrue(false, "Not able to Click on Date Filter Value: " + dateFieldDropDownValue);
								
							}
						} else {
							appLog.error("Not able to Click on Date Filter Drop Down Button");
							sa.assertTrue(false, "Not able to Click on Date Filter Drop Down Button");
							
						}
					} else {
						appLog.error("Not able to Click on Date Filter Button");
						sa.assertTrue(false, "Not able to Click on Date Filter Button");
						
					}
				}

				else {
					appLog.error("Date Filter Value null, So Not Performing action on Filter");
					sa.assertTrue(false, "Date Filter Value null, So Not Performing action on Filter");	
				}

				if (rangeDropDownValue != null) {

					flag = false;
					if (click(driver, dateRangeFilterButtonLightning(30), "Range Filter Button",
							action.SCROLLANDBOOLEAN)) {

						appLog.info("Clicked on Range Filter Button");

						if (click(driver, rangeFilterDropDownButtonLightning(30), "Range Filter Drop Down Button",
								action.SCROLLANDBOOLEAN)) {

							appLog.info("Clicked on Range Filter Drop Down Button");

							WebElement rangeFilterValueEle = FindElement(driver,
									"//span[text()='" + rangeDropDownValue + "']/ancestor::a",
									"Range Filter value : " + rangeDropDownValue, action.SCROLLANDBOOLEAN, 10);

							if (click(driver, rangeFilterValueEle, "Range Filter value : " + rangeDropDownValue,
									action.SCROLLANDBOOLEAN)) {

								appLog.info("Selected Range Filter value : " + rangeDropDownValue);

								if (click(driver, applyButtonLightning(30), "Apply Button ", action.SCROLLANDBOOLEAN)) {

									appLog.info("Clicked on Apply Button");
									appLog.info("Range Filter Applied SUccessfully to :" + rangeDropDownValue);
									flag = true;
								} else {
									appLog.error("Not able to Click on Apply Button");
									sa.assertTrue(false, "Not able to Click on Apply Button");	
								}
							} else {
								appLog.error("Not able to Click on Range Filter Value: " + rangeDropDownValue);
								sa.assertTrue(false, "Not able to Click on Range Filter Value: " + rangeDropDownValue);	
								
							}
						} else {
							appLog.error("Not able to Click on Range Filter Drop Down Button");
							sa.assertTrue(false, "Not able to Click on Range Filter Drop Down Button");	
							
						}
					} else {
						appLog.error("Not able to Click on Range Filter Button");
						sa.assertTrue(false, "Not able to Click on Range Filter Button");	
						
					}
				}

				else {
					appLog.error("Range Filter Value null, So Not Performing action on Filter");
					sa.assertTrue(false, "Range Filter Value null, So Not Performing action on Filter");	
					
				}

				if (customFieldDrpDownValue != null) {

					String[] customFieldDrpDownVal = customFieldDrpDownValue.split("<Break>");
					String[] operatorDrpDownVal = operatorDrpDownValue.split("<Break>");
					String[] fieldFilterVal = fieldFilterValue.split("<Break>");
					String[] textBoxTyp = textBoxType.split("<Break>");

					int j = 0;
					for (String customFieldDrpDwnVal : customFieldDrpDownVal) {

						flag = false;
						CommonLib.ThreadSleep(2000);
						if (click(driver, adFilterSearchBoxLightning(30), "Add Filter Search Box",
								action.SCROLLANDBOOLEAN)) {
							CommonLib.ThreadSleep(2000);
							appLog.info("Clicked on Add Filter Search Box");

							WebElement cusomFilterValueEle = FindElement(driver,
									"//div[@id='report-main']/div/following::span//span[@title='" + customFieldDrpDwnVal
											+ "']",
									"Range Filter value : " + customFieldDrpDwnVal, action.SCROLLANDBOOLEAN, 15);
							CommonLib.ThreadSleep(2000);
							if (click(driver, cusomFilterValueEle, "Custom Field Filter Drop Down Value",
									action.SCROLLANDBOOLEAN)) {

								appLog.info("Custom Field Filter Drop Down Value: " + customFieldDrpDwnVal);
								CommonLib.ThreadSleep(2000);
								if (click(driver, operatorDropDownLinkLightning(20), "Operator Drop Down Link ",
										action.SCROLLANDBOOLEAN)) {

									appLog.info("Clicked on Operator DropDown Link ");

									WebElement operatorFilterValueEle = FindElement(driver,
											"//label[text()=\"Operator\"]/parent::div//ul//span[text()=\""
													+ operatorDrpDownVal[j]
													+ "\" and not(@class=\"slds-assistive-text\")]",

											"Range Filter value : " + operatorDrpDownVal[j], action.SCROLLANDBOOLEAN,
											10);
									CommonLib.ThreadSleep(2000);
									if (click(driver, operatorFilterValueEle,
											"Operator Filter value : " + operatorDrpDownVal[j],
											action.SCROLLANDBOOLEAN)) {

										appLog.info("Selected Operator Filter value : " + operatorDrpDownVal[j]);

										if (textBoxTyp[j].equalsIgnoreCase("DropDown"))

										{
											WebElement customFilterValueEle = FindElement(driver,
													"//button//div[@class=\"option-label\"][text()=\""
															+ fieldFilterVal[j] + "\"]/ancestor::button",

													"Field Filter Value Selection : " + fieldFilterVal[j],
													action.SCROLLANDBOOLEAN, 10);

											if (click(driver, customFilterValueEle,
													"Cilcked on Field Filter Value Selection : " + fieldFilterVal[j],
													action.SCROLLANDBOOLEAN)) {

												appLog.info("Select the Custom Filter value : " + fieldFilterVal[j]);
												if (click(driver, applyButtonLightning(30), "Apply Button ",
														action.SCROLLANDBOOLEAN)) {

													appLog.info("Clicked on Apply Button");
													appLog.info("Custom Filter " + customFieldDrpDwnVal
															+ " Applied SUccessfully to :" + fieldFilterVal[j]
															+ " with Operator: " + operatorDrpDownVal[j]);
													flag = true;
												} else {
													appLog.error("Not able to Click on Apply Button");
													sa.assertTrue(false, "Not able to Click on Apply Button");
													
												}
											} else {
												appLog.error(
														"Not able to Enter Custom Filter Value " + fieldFilterVal[j]);
												sa.assertTrue(false, "Not able to Enter Custom Filter Value " + fieldFilterVal[j]);
												
											}

										}
										
										else if(textBoxTyp[j].equalsIgnoreCase("Relative Date")) {

											if (click(driver, useRelativeDateLink(30), "Use relative Date Link ",
													action.SCROLLANDBOOLEAN)) {

												appLog.info("Clicked on Use Relative Date Link");
											
											if (sendKeys(driver, relativeDateInputBox(30),
													fieldFilterVal[j], "Field Filter Value Input Box",
													action.SCROLLANDBOOLEAN)) {

												appLog.info("Pass the Custom Filter value in text box : "
														+ fieldFilterVal[j]);
												if (click(driver, applyButtonLightning(30), "Apply Button ",
														action.SCROLLANDBOOLEAN)) {

													appLog.info("Clicked on Apply Button");
													appLog.info("Custom Filter " + customFieldDrpDwnVal
															+ " Applied SUccessfully to :" + fieldFilterVal[j]
															+ " with Operator: " + operatorDrpDownVal[j]);
													flag = true;
												} else {
													appLog.error("Not able to Click on Apply Button");
													sa.assertTrue(false, "Not able to Click on Apply Button");
												}
											} else {
												appLog.error(
														"Not able to Enter Custom Filter Value " + fieldFilterVal[j]);
												sa.assertTrue(false, "Not able to Enter Custom Filter Value " + fieldFilterVal[j]);
											}
											} else {
												appLog.error("Not able to Click on Use Relative Date Link");
												sa.assertTrue(false, "Not able to Click on Use Relative Date Link");
												
											}
											
										}

										else if (textBoxTyp[j].equalsIgnoreCase("TextBox")) {

											if (sendKeys(driver, customFilterFieldValueInputBoxLightning(30),
													fieldFilterVal[j], "Field Filter Value Input Box",
													action.SCROLLANDBOOLEAN)) {

												appLog.info("Pass the Custom Filter value in text box : "
														+ fieldFilterVal[j]);
												if (click(driver, applyButtonLightning(30), "Apply Button ",
														action.SCROLLANDBOOLEAN)) {

													appLog.info("Clicked on Apply Button");
													appLog.info("Custom Filter " + customFieldDrpDwnVal
															+ " Applied SUccessfully to :" + fieldFilterVal[j]
															+ " with Operator: " + operatorDrpDownVal[j]);
													flag = true;
												} else {
													appLog.error("Not able to Click on Apply Button");
													sa.assertTrue(false, "Not able to Click on Apply Button");
													
												}
											} else {
												appLog.error(
														"Not able to Enter Custom Filter Value " + fieldFilterVal[j]);
												sa.assertTrue(false, "Not able to Enter Custom Filter Value " + fieldFilterVal[j]);
												
											}

										}
										
										else
										{
											log(LogStatus.ERROR, "TextBox Type not Mention Properly in Test data: "+textBoxTyp[j], YesNo.No);
											sa.assertTrue(false, "TextBox Type not Mention Properly in Test data: "+textBoxTyp[j]);
										}

									} else {
										appLog.error(
												"Not Able to Select Operator Filter value : " + operatorDrpDownVal[j]);
										sa.assertTrue(false, "Not Able to Select Operator Filter value : " + operatorDrpDownVal[j]);
										
									}
								} else {
									appLog.error("Not Able to CLick on Operator Filter DropDown Button : ");
									sa.assertTrue(false, "Not Able to CLick on Operator Filter DropDown Button : ");
									
								}
							} else {
								appLog.error("Not able to click on Custom Field Filter Drop Down Value: "
										+ customFieldDrpDwnVal);
								sa.assertTrue(false, "Not able to click on Custom Field Filter Drop Down Value: "
										+ customFieldDrpDwnVal);
								
							}
						} else {
							appLog.error("Not able to Click on Add Filter Search Box");
							sa.assertTrue(false, "Not able to Click on Add Filter Search Box");
							
						}
						j++;
					}
				}

				else {
					appLog.error("Custom Field Filter Value null, So Not Performing action on Filter");
					sa.assertTrue(false, "Custom Field Filter Value null, So Not Performing action on Filter");
					
				}
			}

			else {
				appLog.error("Not able to Click on Filter Button");
				sa.assertTrue(false, "Not able to Click on Filter Button");
				
			}
		}

		else {
			appLog.error("Not able to Switch to Iframe");
			sa.assertTrue(false,"Not able to Switch to Iframe");
			
		}

		if (flag) {
			if (click(driver, getSaveBtn_Classic("", 20), "Save Button ", action.SCROLLANDBOOLEAN))

				appLog.info("Clicked on Save Button");
			else {
				appLog.error("Not able to Click on Save Button");
				sa.assertTrue(false, "Not able to Click on Save Button");
				
			}
		}

		CommonLib.switchToDefaultContent(driver);
		return flag;
	}

	public boolean addFilterInCustomReportClassic(String showDropDownValue, String dateFieldDropDownValue,
			String rangeDropDownValue, String fieldDrpDownValue, String operatorDrpDownValue, String fieldFilterValue) {
		boolean flag = false;

		if (getSelectedOptionOfDropDown(driver, showDrpDownLink(30), showDrpDownList(), "Show Drop Down Link",
				showDropDownValue)) {
			appLog.info(showDropDownValue + " has been selected from drop down");
			if (getSelectedOptionOfDropDown(driver, dateFieldDrpDownLink(30), dateFieldDrpDownList(),
					"Date Field Drop Down Link", dateFieldDropDownValue)) {
				appLog.info(dateFieldDropDownValue + " has been selected from drop down");
				if (getSelectedOptionOfDropDown(driver, rangeDrpDownLink(30), rangeDropDownValue,
						"Range Drop Down Link", "")) {
					appLog.info(rangeDropDownValue + " has been selected from drop down");

					String[] fieldDrpDownVal = fieldDrpDownValue.split("<Break>");
					String[] operatorDrpDownVal = operatorDrpDownValue.split("<Break>");
					String[] fieldFilterVal = fieldFilterValue.split("<Break>");

					int j = 0;
					for (String fieldDrpDwnVal : fieldDrpDownVal) {

						if (click(driver, addFilterBtn(40), "Add Filter Button", action.SCROLLANDBOOLEAN)) {
							appLog.info("Clicked on Add Filter Button");
							if (getSelectedOptionOfDropDown(driver, fieldDrpDownLink(30), fieldDrpDownList(),
									"Field Drop Down Link", fieldDrpDwnVal)) {
								appLog.info(fieldDrpDwnVal + " has been selected from drop down");

								if (getSelectedOptionOfDropDown(driver, operatorDrpDownLink(30), operatorDrpDownList(),
										"Field Drop Down Link", operatorDrpDownVal[j])) {
									appLog.info(operatorDrpDownVal[j] + " has been selected from drop down");

									if (sendKeys(driver, fieldFilterValueInputBox(30), fieldFilterVal[j],
											"Field Filter Value Input Box", action.SCROLLANDBOOLEAN)) {
										appLog.info("Field Filter Value Input Box passed value: " + fieldFilterVal[j]);
										if (click(driver, filterOKButton(40), "Filter OK Button",
												action.SCROLLANDBOOLEAN)) {
											appLog.info("Clicked on OK Button");

											flag = true;

										} else
											appLog.error("Not Able to Click on OK Filter Button");

									} else
										appLog.error(fieldFilterVal[j]
												+ " :Not able to pass value in Field Filter Value Input Box");

								}

								else
									appLog.error(operatorDrpDownVal[j] + " :Not Able to select from dropdown");

							} else
								appLog.error(fieldDrpDwnVal + " :Not Able to select from dropdown");
						}

						else
							appLog.error("Not Able to Click on Add Filter Button");

						j++;
					}

				} else
					appLog.error(rangeDropDownValue + " :Not Able to Select Date Range");
			} else
				appLog.error(dateFieldDropDownValue + ": Not able to select from drop down");

		} else
			appLog.error(showDropDownValue + ": Not able to select from drop down");

		if (flag) {
			if (click(driver, getSaveBtn_Classic("", 10), "Save Button on Save Report", action.SCROLLANDBOOLEAN)) {

				appLog.info("Clicked on Save Button on Save Report");
				if (click(driver, homeLink(40), "Home Link", action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on Home Link");

					flag = true;
				}
			} else {
				appLog.error("Not Able to click on Save Button on Save Report ");
				flag = false;
			}
		} else
			appLog.error("Not Able to Add Filters ");

		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param reportName
	 * @param reportCount
	 * @param reportColumns
	 */
	public boolean reportVerification(String reportName, int reportCount, List<String> reportColumns)

	{
		int status = 0;
		CommonLib.ThreadSleep(10000);
		List<String> columns = reportColumnHeaders().stream().map(s -> s.getText()).collect(Collectors.toList());
		int numberOfColumnsActual = columns.size();
		int numberOfColumnsExpected = reportColumns.size();
		if (CommonLib.getText(driver, getreportName(reportName), "Report Name:" + reportName, action.SCROLLANDBOOLEAN)
				.equals(reportName)) {
			log(LogStatus.PASS, "Report Name: " + reportName + " is verified", YesNo.No);
			if (reportRecordsRowsCount().size() == reportCount) {
				log(LogStatus.PASS, "Number of Records Verified", YesNo.No);
				status++;

			} else {
				log(LogStatus.ERROR, "Number of Records not Verified, Expected: " + reportCount + "but Actual: "
						+ reportRecordsRowsCount().size(), YesNo.No);
				sa.assertTrue(false, "Number of Records not Verified, Expected: " + reportCount + "but Actual: "
						+ reportRecordsRowsCount().size());
			}

			if (numberOfColumnsActual == numberOfColumnsExpected) {
				log(LogStatus.PASS, "Number of Columns Verified", YesNo.No);
				status++;
				if (columns.equals(reportColumns)) {
					log(LogStatus.PASS, "Column Headers Verified", YesNo.No);
					status++;

				} else {
					log(LogStatus.ERROR,
							"Column Headers not Verified, Expected: " + reportColumns + "but Actual: " + columns,
							YesNo.No);
					sa.assertTrue(false,
							"Column Headers not Verified, Expected: " + reportColumns + "but Actual: " + columns);

				}
			} else {
				log(LogStatus.ERROR, "Number of Columns not Verified, Expected: " + numberOfColumnsExpected
						+ "but Actual: " + numberOfColumnsActual, YesNo.No);
				sa.assertTrue(false, "Number of Columns not Verified, Expected: " + numberOfColumnsExpected
						+ "but Actual: " + numberOfColumnsActual);
			}

		} else {
			log(LogStatus.ERROR, "Report Name not verfied, Expected: " + reportName + "but Actual: " + CommonLib
					.getText(driver, getreportName(reportName), "Report Name:" + reportName, action.SCROLLANDBOOLEAN),
					YesNo.No);
			sa.assertTrue(false, "Report Name not verfied, Expected: " + reportName + "but Actual: " + CommonLib
					.getText(driver, getreportName(reportName), "Report Name:" + reportName, action.SCROLLANDBOOLEAN));
		}
		if (status == 3)

			return true;

		else
			return false;

	}
}
