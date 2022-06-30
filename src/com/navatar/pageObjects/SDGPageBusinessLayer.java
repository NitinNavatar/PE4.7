package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.AddressAction;
import com.navatar.generic.EnumConstants.Condition;
import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.LimitedPartnerPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

public class SDGPageBusinessLayer extends SDGPage implements SDGPageErrorMessage {

	public SDGPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param label
	 * @param action
	 * @param timeOut
	 * @return getSDGElementForCreation
	 */
	public WebElement getSDGElementForCreation(String projectName, String label, action action, int timeOut) {
		String xpath = "";
		String fieldLabel = label.replace("_", " ");
		;
		switchToDefaultContent(driver);
		if (SDGCreationLabel.Filter.toString().equals(fieldLabel)
				|| SDGCreationLabel.My_Records.toString().equals(fieldLabel)) {
			xpath = "//*[text()='" + fieldLabel + "']/..//following-sibling::div//textarea";
		} else {
			xpath = "//*[text()='" + fieldLabel + "']/..//following-sibling::div//input[@type='text']";
		}
		WebElement ele = FindElement(driver, xpath, fieldLabel, action, timeOut);
		scrollDownThroughWebelement(driver, ele, fieldLabel);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, fieldLabel);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param labelWithValues
	 * @param action
	 * @param timeOut
	 * @return true if able to enter value for SDG creation
	 */
	public boolean enterValueForSDGCreation(String projectName, String[][] labelWithValues, action action,
			int timeOut) {
		boolean flag = false;
		String label = "";
		String value = "";
		WebElement ele = null;
		for (String[] labelValues : labelWithValues) {
			label = labelValues[0].replace("_", " ");
			value = labelValues[1];
			ele = getSDGElementForCreation(projectName, label, action, timeOut);
			if (sendKeys(driver, ele, value, label + " : " + value, action)) {
				log(LogStatus.INFO, "Able to Enter Value : " + value + " to label : " + label, YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Enter Value : " + value + " to label : " + label);
				log(LogStatus.SKIP, "Not Able to Enter Value : " + value + " to label : " + label, YesNo.Yes);

			}
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param sdgName
	 * @param labelWithValues
	 * @param action
	 * @param timeOut
	 * @return true if SDG created successfully
	 */
	public boolean createCustomSDG(String projectName, String sdgName, String[][] labelWithValues, action action,
			int timeOut) {
		boolean flag = false;
		ThreadSleep(5000);
		refresh(driver);
		ThreadSleep(10000);
		if (clickUsingJavaScript(driver, getNewButton(projectName, timeOut), "new button")) {
			appLog.info("clicked on new button");
			enterValueForSDGCreation(projectName, labelWithValues, action, timeOut);
			if (click(driver, getRecordPageSettingSave(60), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Click on Save Button  " + sdgName, YesNo.No);
				ThreadSleep(5000);
				WebElement duplicateValueEle = FindElement(driver, "//li[contains(text(),'duplicate value found')]",
						"Duplicate SDG Found ", action.SCROLLANDBOOLEAN, 10);
				if (duplicateValueEle != null) {
					appLog.info("Not able to create SDG as SDG already created: " + sdgName);
					sa.assertTrue(false, "Not able to create SDG as SDG already created: " + sdgName);
					log(LogStatus.SKIP, "Not able to create SDG as SDG already created: " + sdgName, YesNo.Yes);

					WebElement cancelButtonEle = FindElement(driver, "//button[text()='Cancel']", "Cancel Button ",
							action.SCROLLANDBOOLEAN, 10);
					click(driver, cancelButtonEle, "Cancel Button", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO, "Click on Cancel Button  ", YesNo.No);
				} else {
					if (getSDGHeaderValueInViewMode(projectName, sdgName, timeOut) != null) {
						log(LogStatus.PASS, "Header verified for created  " + sdgName, YesNo.No);
						flag = true;
					} else {
						sa.assertTrue(false, "Header not verified for created  " + sdgName);
						log(LogStatus.SKIP, "Header not verified for created  " + sdgName, YesNo.Yes);
					}
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Save Button Value so cannot create  " + sdgName);
				log(LogStatus.SKIP, "Not Able to Click on Save Button Value so cannot create  " + sdgName, YesNo.Yes);
			}

		} else {
			appLog.error("Not able to click on New Button so cannot create sdg : " + sdgName);
			sa.assertTrue(false,"Not able to click on New Button so cannot create sdg : " + sdgName);
			log(LogStatus.SKIP,"Not able to click on New Button so cannot create sdg : " + sdgName, YesNo.Yes);

		}

		return flag;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param names
	 * @param values
	 * @return true if able to add Field on SDG
	 */
	public boolean addFieldOnSDG(String projectName, String names, String values) {
		names = names.replace("_", " ");
		String name[] = names.split(",");
		// values= values.replace("_", " ");
		String value[] = values.split(",");
		if (name.length != value.length) {
			log(LogStatus.INFO, name.length + " does not match " + value.length, YesNo.No);
			return false;
		}
		WebElement ele;
		boolean flag = true;
		ele = getRelatedTab(projectName, RelatedTab.Related.toString(), 10);
		if (click(driver, ele, "related tab", action.BOOLEAN)) {
			int i = 0;
			if (click(driver, getFieldNewButton(projectName, 10), "field new button", action.BOOLEAN)) {
				for (String f : name) {
					System.err.println(f);
					ele = getLabelTextBox(projectName, PageName.SDGPage.toString(), f, 10);
					if (sendKeys(driver, ele, value[i], f, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "successfully entered " + value[i] + " in " + f, YesNo.Yes);

					} else {
						log(LogStatus.SKIP, "could not enter " + value[i] + " in " + f, YesNo.Yes);
						sa.assertTrue(false, "could not enter " + value[i] + " in " + f);

					}
					i++;
				}
				if (click(driver, getRecordPageSettingSave(10), "save", action.BOOLEAN)) {
					log(LogStatus.INFO, "successfully clicked on save button", YesNo.Yes);
				} else {
					log(LogStatus.SKIP, "could not click on save button, so could not add " + f, YesNo.Yes);
					flag = false;
				}

			} else {
				log(LogStatus.SKIP, "could not click on related tab, so cannot add " + f, YesNo.Yes);
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param sdgName
	 * @param labelWithValues
	 * @param action
	 * @param timeOut
	 * @return true if SDG created successfully
	 */

	/*
	 * NOTE: editCustomSDGandFoundErrorMsg: Method Validate Error Msg, Also if
	 * labelWithValue[0][1] !="" Then it will save SDG and validate the headers
	 */
	public boolean editCustomSDGandFoundErrorMsgAndAtLastWithoutError(String projectName, String sdgName,
			String[][][] labelWithValues, action action, int timeOut, String errorMsg) {
		boolean flag = false;
		ThreadSleep(5000);
		refresh(driver);
		ThreadSleep(5000);
		if (click(driver, getSelectListIcon(60), "List View Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on List Views Button  ", YesNo.No);
			WebElement allListViewEle = FindElement(driver, "//span[text()='All']/ancestor::a", "All List View Button ",
					action.SCROLLANDBOOLEAN, 10);
			if (click(driver, allListViewEle, "ALl List View Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on All List Views Button  ", YesNo.No);
				if (sendKeys(driver, sdgSearchBox(30), sdgName, "SDG Name: " + sdgName, action)) {
					log(LogStatus.INFO, "Able to Enter Value : " + sdgName + " to Search Box : " + sdgName, YesNo.No);

					sdgSearchBox(30).sendKeys(Keys.ENTER);

					CommonLib.ThreadSleep(5000);
					WebElement sdgNameAfterSearchEle = FindElement(driver, "//th//a[text()='" + sdgName + "']",
							"SDG Name After Search ", action.SCROLLANDBOOLEAN, 10);
					if (click(driver, sdgNameAfterSearchEle, "SDG Name After Search", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on SDG Name: " + sdgName, YesNo.No);
						if (clickUsingJavaScript(driver, sdgEditButton(timeOut), "Edit button")) {
							appLog.info("clicked on Edit button");
							for (String[][] labelWithValue : labelWithValues) {

								if (labelWithValue[0][1] != "") {
									enterValueForSDGCreation(projectName, labelWithValue, action, timeOut);
									if (click(driver, getRecordPageSettingSave(60), "Save Button",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Click on Save Button  " + sdgName, YesNo.No);
										ThreadSleep(5000);
										WebElement errorEle = FindElement(driver,
												"//strong[text()='Review the errors on this page.']/parent::div/following-sibling::ul",
												"Error:  " + errorMsg, action.SCROLLANDBOOLEAN, 10);
										if (errorEle != null) {
											if (errorEle.getAttribute("innerHTML").contains(errorMsg)) {
												appLog.info("Error Msg Found :  " + errorMsg);
												log(LogStatus.PASS, "Error Msg Found: " + errorMsg, YesNo.Yes);
												flag = true;

											} else {
												sa.assertTrue(false, "Error Msg not Matched: " + errorMsg);
												appLog.error("Error Msg not Matched: " + errorMsg);
												log(LogStatus.FAIL, "Error Msg not Matched:" + errorMsg, YesNo.Yes);
												flag = false;

											}

										} else {
											sa.assertTrue(false, "Error Msg not Found: " + errorMsg);
											appLog.error("Error Msg not Found: " + errorMsg);
											log(LogStatus.FAIL, "Error Msg not Found: " + errorMsg, YesNo.Yes);
											flag = false;

										}

									} else {
										sa.assertTrue(false,
												"Not Able to Click on Save Button Value so cannot create  " + sdgName);
										log(LogStatus.SKIP,
												"Not Able to Click on Save Button Value so cannot create  " + sdgName,
												YesNo.Yes);
									}

								}

								else {
									enterValueForSDGCreation(projectName, labelWithValue, action, timeOut);
									if (click(driver, getRecordPageSettingSave(60), "Save Button",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Click on Save Button  " + sdgName, YesNo.No);
										ThreadSleep(5000);
										WebElement errorEle = FindElement(driver,
												"//strong[text()='Review the errors on this page.']/parent::div/following-sibling::ul",
												"Error: You can either fill 'Filter' or 'List View Name' to save the record. ",
												action.SCROLLANDBOOLEAN, 10);
										if (errorEle != null) {
											if (errorEle.getAttribute("innerHTML").contains(errorMsg)) {
												appLog.error("Error Msg Found: " + errorMsg);
												log(LogStatus.FAIL, "Error Msg Found: " + errorMsg, YesNo.Yes);
												flag = false;

											}

										} else {
											if (getSDGHeaderValueInViewMode(projectName, sdgName, timeOut) != null) {
												log(LogStatus.PASS, "Header verified for created  " + sdgName,
														YesNo.No);
												flag = true;
											} else {
												sa.assertTrue(false, "Header not verified for created  " + sdgName);
												log(LogStatus.ERROR, "Header not verified for created  " + sdgName,
														YesNo.Yes);
												flag = false;
											}

										}

									} else {
										sa.assertTrue(false,
												"Not Able to Click on Save Button Value so cannot create  " + sdgName);
										log(LogStatus.SKIP,
												"Not Able to Click on Save Button Value so cannot create  " + sdgName,
												YesNo.Yes);
									}
								}
							}
						} else {
							sa.assertTrue(false, "Not able to click on Edit Button so cannot create sdg : " + sdgName);
							appLog.error("Not able to click on Edit Button so cannot create sdg : " + sdgName);

						}
					} else {
						sa.assertTrue(false, "Not Able to Click/Find : " + sdgName + " after Search : ");
						appLog.error("Not Able to Click/Find : " + sdgName + " after Search : ");

					}
				} else {
					sa.assertTrue(false, "Not Able to Enter Value : " + sdgName + " to Search Box ");
					appLog.error("Not Able to Enter Value : " + sdgName + " to Search Box ");

				}
			} else {
				sa.assertTrue(false, "Not able to click on All List View Button");
				appLog.error("Not able to click on All List View Button");

			}
		} else {
			sa.assertTrue(false, "Not able to click on List Views Button");
			appLog.error("Not able to click on List Views Button");

		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param sdgName
	 * @param labelWithValues
	 * @param action
	 * @param timeOut
	 * @return true if SDG created successfully
	 */

	public boolean editCustomSDGandFoundErrorMsg(String projectName, String sdgName, String[][][] labelWithValues,
			action action, int timeOut, String errorMsg) {
		boolean flag = false;
		ThreadSleep(5000);
		refresh(driver);
		ThreadSleep(5000);
		if (click(driver, getSelectListIcon(60), "List View Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on List Views Button  ", YesNo.No);
			WebElement allListViewEle = FindElement(driver, "//span[text()='All']/ancestor::a", "All List View Button ",
					action.SCROLLANDBOOLEAN, 10);
			if (click(driver, allListViewEle, "ALl List View Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on All List Views Button  ", YesNo.No);
				if (sendKeys(driver, sdgSearchBox(30), sdgName, "SDG Name: " + sdgName, action)) {
					log(LogStatus.INFO, "Able to Enter Value : " + sdgName + " to Search Box : " + sdgName, YesNo.No);

					sdgSearchBox(30).sendKeys(Keys.ENTER);
					CommonLib.ThreadSleep(5000);

					WebElement sdgNameAfterSearchEle = FindElement(driver, "//th//a[text()='" + sdgName + "']",
							"SDG Name After Search ", action.SCROLLANDBOOLEAN, 10);
					if (click(driver, sdgNameAfterSearchEle, "SDG Name After Search", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on SDG Name: " + sdgName, YesNo.No);
						if (clickUsingJavaScript(driver, sdgEditButton(timeOut), "Edit button")) {
							appLog.info("clicked on Edit button");
							for (String[][] labelWithValue : labelWithValues) {

								enterValueForSDGCreation(projectName, labelWithValue, action, timeOut);
								if (click(driver, getRecordPageSettingSave(60), "Save Button",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Click on Save Button  " + sdgName, YesNo.No);
									ThreadSleep(5000);
									WebElement errorEle = FindElement(driver,
											"//strong[text()='Review the errors on this page.']/parent::div/following-sibling::ul",
											"Error:  " + errorMsg, action.SCROLLANDBOOLEAN, 10);
									if (errorEle != null) {
										if (errorEle.getAttribute("innerHTML").contains(errorMsg)) {
											appLog.info("Error Msg Found :  " + errorMsg);
											log(LogStatus.PASS, "Error Msg Found: " + errorMsg, YesNo.Yes);
											flag = true;

										} else {
											appLog.error("Error Msg not Matched: " + errorMsg);
											log(LogStatus.FAIL, "Error Msg not Matched:" + errorMsg, YesNo.Yes);
											flag = false;
											break;

										}

									} else {
										appLog.error("Error Msg not Found: " + errorMsg);
										log(LogStatus.FAIL, "Error Msg not Found: " + errorMsg, YesNo.Yes);
										flag = false;
										break;

									}

								} else {
									sa.assertTrue(false,
											"Not Able to Click on Save Button Value so cannot create  " + sdgName);
									log(LogStatus.SKIP,
											"Not Able to Click on Save Button Value so cannot create  " + sdgName,
											YesNo.Yes);
								}

							}
						} else {
							appLog.error("Not able to click on Edit Button so cannot create sdg : " + sdgName);

						}
					} else {
						appLog.error("Not Able to Click/Find : " + sdgName + " after Search : ");

					}
				} else {
					appLog.error("Not Able to Enter Value : " + sdgName + " to Search Box ");

				}
			} else {
				appLog.error("Not able to click on All List View Button");

			}
		} else {
			appLog.error("Not able to click on List Views Button");

		}
		WebElement cancelButtonEle = FindElement(driver, "//button[text()='Cancel']", "Cancel Button ",
				action.SCROLLANDBOOLEAN, 10);
		if (cancelButtonEle != null) {
			click(driver, cancelButtonEle, "Cancel Button", action.SCROLLANDBOOLEAN);
			log(LogStatus.INFO, "Click on Cancel Button  ", YesNo.No);
		} else
			log(LogStatus.ERROR, "Not Able to Find Cancel Button  ", YesNo.Yes);

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param sdgName
	 * @param labelWithValues
	 * @param action
	 * @param timeOut
	 * @return true if SDG created successfully
	 */

	public boolean editCustomSDG(String projectName, String sdgName, String[][] labelWithValues, action action,
			int timeOut) {
		boolean flag = false;
		CommonLib.refresh(driver);
		ThreadSleep(5000);

		if (click(driver, getSelectListIcon(70), "List View Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on List Views Button  ", YesNo.No);
			WebElement allListViewEle = FindElement(driver, "//span[text()='All']/ancestor::a", "All List View Button ",
					action.SCROLLANDBOOLEAN, 10);
			if (click(driver, allListViewEle, "ALl List View Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on All List Views Button  ", YesNo.No);
				if (sendKeys(driver, sdgSearchBox(30), sdgName, "SDG Name: " + sdgName, action)) {
					log(LogStatus.INFO, "Able to Enter Value : " + sdgName + " to Search Box : " + sdgName, YesNo.No);

					sdgSearchBox(30).sendKeys(Keys.ENTER);
					CommonLib.ThreadSleep(3000);
					WebElement sdgNameAfterSearchEle = FindElement(driver, "//th//a[text()='" + sdgName + "']",
							"SDG Name After Search ", action.SCROLLANDBOOLEAN, 20);
					CommonLib.ThreadSleep(2000);
					if (click(driver, sdgNameAfterSearchEle, "SDG Name After Search", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on SDG Name: " + sdgName, YesNo.No);
						if (clickUsingJavaScript(driver, sdgEditButton(timeOut), "Edit button")) {
							appLog.info("clicked on Edit button");

							enterValueForSDGCreation(projectName, labelWithValues, action, timeOut);
							if (click(driver, getRecordPageSettingSave(60), "Save Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button  " + sdgName, YesNo.No);
								ThreadSleep(5000);
								if (getSDGHeaderValueInViewMode(projectName, sdgName, timeOut) != null) {
									log(LogStatus.PASS, "Header verified for created  " + sdgName, YesNo.No);
									String label;
									String value;
									WebElement ele;
									CommonLib.refresh(driver);
									for (String[] labelValues : labelWithValues) {
										label = labelValues[0].replace("_", " ");
										value = labelValues[1];
										ele = sdgLabelValueElement(25, label);

										if (ele.getText().equals(value)) {
											log(LogStatus.INFO, "Value Expected: " + value + " and Actual: "
													+ ele.getText() + " are same", YesNo.Yes);
											flag = true;
										} else {
											sa.assertTrue(false,
													"Value Expected: " + value + " but Actual: " + ele.getText());
											log(LogStatus.FAIL,
													"Value Expected: " + value + " but Actual: " + ele.getText(),
													YesNo.Yes);
											flag = false;

											break;
										}
									}
								} else {
									sa.assertTrue(false, "Header not verified for created  " + sdgName);
									log(LogStatus.SKIP, "Header not verified for created  " + sdgName, YesNo.Yes);
									flag = false;
								}

							} else {
								sa.assertTrue(false,
										"Not Able to Click on Save Button Value so cannot create  " + sdgName);
								log(LogStatus.SKIP,
										"Not Able to Click on Save Button Value so cannot create  " + sdgName,
										YesNo.Yes);
							}

						} else {
							appLog.error("Not able to click on Edit Button so cannot create sdg : " + sdgName);

						}
					} else {
						appLog.error("Not Able to Click/Find : " + sdgName + " after Search : ");

					}
				} else {
					appLog.error("Not Able to Enter Value : " + sdgName + " to Search Box ");

				}
			} else {
				appLog.error("Not able to click on All List View Button");

			}
		} else {
			appLog.error("Not able to click on List Views Button");

		}

		return flag;
	}

	public boolean openSDG(String projectName, String sdgName) {
		String xpath = "";
		if (click(driver, getSelectListIcon(60), "List View Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on List Views Button  ", YesNo.No);
			WebElement allListViewEle = FindElement(driver, "//span[text()='All']/ancestor::a", "All List View Button ",
					action.SCROLLANDBOOLEAN, 10);
			if (click(driver, allListViewEle, "ALl List View Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on All List Views Button  ", YesNo.No);
				if (sendKeysAndPressEnter(driver, getsdgSearchbox(projectName, 50), sdgName, "search text box",
						action.SCROLLANDBOOLEAN)) {
					appLog.info("Passed Value in Search Text box: " + sdgName);

					ThreadSleep(7000);
					xpath = "//a[@title='" + sdgName + "']";

					WebElement ele = FindElement(driver, xpath, sdgName, action.SCROLLANDBOOLEAN, 30);

					if (click(driver, ele, "SDG Name", action.BOOLEAN)) {

						log(LogStatus.INFO, "Clicked on the SDG Name", YesNo.Yes);
						return true;
					} else {
						log(LogStatus.ERROR, "Could not click on the SDG Name", YesNo.Yes);
						return false;
					}

				}

				else {
					log(LogStatus.ERROR, "Not able to pass value " + sdgName
							+ " in search text box so cannot search contact: " + sdgName, YesNo.Yes);
					return false;
				}
			} else {
				appLog.error("Not able to click on All List View Button");
				return false;
			}
		} else {
			appLog.error("Not able to click on List Views Button");
			return false;
		}

	}

	/**
	 * @author Sourabh saini
	 * @param projectName
	 * @param names
	 * @param values
	 * @return true if able to add Field on SDG
	 * @description pass Either SelectCheckbox or UnSelectCheckbox in the condition
	 *              parameter.
	 */
	public boolean editAllRowOnSDG(String projectName, String sdgName, Condition condition) {
		if (openSDG(projectName, sdgName)) {
			if (click(driver, sdgEditButton(40), "Edit Button", action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the Edit Button", YesNo.Yes);
				ThreadSleep(5000);
				boolean status = isSelected(driver, getAllRowCheckbox(), "All Row");

				if (condition.toString().equals("SelectCheckbox")) {
					if (status == false) {

						if (click(driver, getAllRowCheckbox(), "All Row cheeckbox", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the All Row checkbox", YesNo.Yes);
						} else {
							log(LogStatus.ERROR, "could not click on All row checkbox button", YesNo.Yes);
							return false;
						}
					}
				} else if (condition.toString().equals("UnSelectCheckbox")) {
					if (status == true) {
						if (click(driver, getAllRowCheckbox(), "All Row cheeckbox", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the All Row checkbox", YesNo.Yes);
						} else {
							log(LogStatus.ERROR, "could not click on All row checkbox button", YesNo.Yes);
							return false;
						}
					}
				} else {
					log(LogStatus.ERROR, "Please pass the correct value on condition. could not click on the checkbox",
							YesNo.Yes);
					return false;
				}

				if (click(driver, getSaveButton(projectName, 40), "Save Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the save button", YesNo.Yes);
					if (checkElementVisibility(driver, getconfirmationSaveMessage(projectName, 50),
							"Save Confirmation Message", 50)) {
						appLog.info("save confirmation Message is visible so All row checkbox has been clicked");
						ThreadSleep(7000);
						return true;
					} else {
						log(LogStatus.ERROR,
								"save confirmation message is not visible  so All row checkbox is not clicked",
								YesNo.Yes);
						return false;
					}
				} else {
					log(LogStatus.ERROR, "could not click on save button", YesNo.Yes);
					return false;
				}
			} else {
				log(LogStatus.ERROR, "could not click on edit button", YesNo.Yes);
				return false;
			}
		} else {
			log(LogStatus.ERROR, "could not Open the SDG", YesNo.Yes);
			return false;
		}
	}

	public boolean sequenceFilter(String projectName, String sdgName, String apiNameOrOverrideLabelName,
			String sequenceFilterOptionValue) {
		String xpath = "";
		WebElement ele;
		if (openSDG(projectName, sdgName)) {
			if (click(driver, getrelatedTabOnSDG(40), "Related tab on SDG", action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the Related Button", YesNo.Yes);
				CommonLib.ThreadSleep(3000);
				xpath = "//tbody//lst-formatted-text[text()='" + apiNameOrOverrideLabelName
						+ "']/ancestor::td/following-sibling::td//button";
				ele = CommonLib.FindElement(driver, xpath, "Ero Button", action.SCROLLANDBOOLEAN, 50);
				if (click(driver, ele, "Name button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the name button", YesNo.Yes);
					CommonLib.ThreadSleep(8000);
					if (click(driver, getsdgPageEditButton(40), "Edit button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the edit button", YesNo.Yes);
						CommonLib.ThreadSleep(3000);
						if (click(driver, getfilterSequenceButton(40), "Filter sequence button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the filter sequence button", YesNo.Yes);
							CommonLib.ThreadSleep(3000);
							if (CommonLib.getSelectedOptionOfDropDown(driver, getfilterSequenceDropdownList(),
									"Filter Sequence list", sequenceFilterOptionValue)) {
								log(LogStatus.INFO, "Option has been selected from the sequence filter field drop down",
										YesNo.Yes);
								if (click(driver, getsdgSaveBtn(40), "save button", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on the save button", YesNo.Yes);
									if (CommonLib.checkElementVisibility(driver, getsdgSaveConfirmationMsg(50),
											"Confirmation message after click on the save button", 50)) {
										log(LogStatus.INFO, "Sequence filter has been saved", YesNo.No);
										return true;
									} else {
										log(LogStatus.ERROR, "Sequence filter is not saved", YesNo.Yes);
										return false;
									}
								} else {
									log(LogStatus.ERROR, "could not click on the save button", YesNo.Yes);
									return false;
								}
							} else {
								log(LogStatus.ERROR,
										"could not select the option value from the sequence filter field drop down",
										YesNo.Yes);
								return false;
							}

						} else {
							log(LogStatus.ERROR, "could not click on the filter sequence button", YesNo.Yes);
							return false;
						}

					}

					else {
						log(LogStatus.ERROR, "could not click on the edit button", YesNo.Yes);
						return false;
					}
				} else {
					log(LogStatus.ERROR, "could not click on the name button", YesNo.Yes);
					return false;
				}

			} else {
				log(LogStatus.ERROR, "could not click on the Related tab button", YesNo.Yes);
				return false;
			}
		} else {
			log(LogStatus.ERROR, "could not open the SDG", YesNo.Yes);
			return false;
		}

	}

	public ArrayList<String> getListText(String xpath, String elementName) {
		ArrayList<String> list = new ArrayList<String>();
		if (xpath != null) {
			List<WebElement> elements = CommonLib.FindElements(driver, xpath, elementName);

			for (int i = 0; i < elements.size(); i++) {
				String val = CommonLib.getText(driver, elements.get(i), elementName, action.SCROLLANDBOOLEAN);
				list.add(val);
			}
		} else {
			log(LogStatus.ERROR, elementName + " xpath is null", YesNo.Yes);
			return null;
		}

		return list;
	}

	public boolean verifyRecordExistOrNotOnSDG(String sdgName, String elementName) {
		WebElement ele;
		String xpath;
		xpath = "//a[text()='" + sdgName
				+ "']/ancestor::div[contains(@class,'slds-card__header')]/following-sibling::div//span[text()='No data returned']";
		ele = CommonLib.FindElement(driver, xpath, elementName, action.SCROLLANDBOOLEAN, 30);
		if (ele != null) {
			log(LogStatus.INFO, elementName + " element has been Found", YesNo.No);
			return true;

		} else {
			log(LogStatus.INFO, "could not get the " + elementName + " element", YesNo.No);
			return false;
		}

	}

	public boolean verifyRecordAfterApplyingGlobalFilter(String sdgName, String filterOptionValue, String fieldName,
			String elementName) {
		int status = 0;
		boolean flag = false;
		ThreadSleep(3000);
		String xpath = "//a[text()='" + sdgName
				+ "']/ancestor::div[contains(@class,'slds-card__header')]/following-sibling::div//tbody//td[@data-label='"
				+ fieldName + ": ']";
		ArrayList<String> list = getListText(xpath, elementName);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(filterOptionValue)) {
					log(LogStatus.INFO, list.get(i) + " has been matched with " + filterOptionValue + "", YesNo.No);
				} else {
					log(LogStatus.ERROR, list.get(i) + " is not matched with the " + filterOptionValue + "", YesNo.No);
					status++;
				}
			}

			if (status == 0) {
				log(LogStatus.INFO, "Sequence filter has been Applied Records are matched", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.INFO, "Sequence filter has been Applied but Record is not matched", YesNo.No);
				flag = false;
			}

		} else {
			log(LogStatus.ERROR, "Either Sequence filter is not applied properly or Could not get the records",
					YesNo.No);
			sa.assertTrue(false, "Either Sequence filter is not applied properly or Could not get the records");
		}
		return flag;

	}

	public boolean verifyEditOrLockedIconOnSDGData(String recordName, IconType icon) {
		String xPath = "";
		WebElement ele = null;
		WebElement verifyElement = null;
		boolean flag = false;
		CommonLib.ThreadSleep(2000);

		xPath = "(//td[@data-label='" + recordName + ": '])[3]";
		ele = CommonLib.FindElement(driver, xPath, recordName + " Record Name", action.BOOLEAN, 50);
		mouseOverOperation(driver, ele);

		xPath = "(//td[@data-label='" + recordName + ": '])[1]";

		ele = CommonLib.FindElement(driver, xPath, recordName + " Record Name", action.BOOLEAN, 50);

		if (mouseOverOperation(driver, ele)) {
			log(LogStatus.INFO, "Mouse has been hover to " + recordName, YesNo.No);

			if (icon.toString().equals("Edit")) {
				xPath = "(//td[@data-label='" + recordName
						+ ": ']//span[text()='Edit']/preceding-sibling::lightning-primitive-icon)[1]";
				ele = CommonLib.FindElement(driver, xPath, "Edit icon on " + recordName, action.BOOLEAN, 50);

				verifyElement = CommonLib.isDisplayed(driver, ele, "Visibility", 30, "Edit icon on " + recordName);

				if (verifyElement != null) {
					log(LogStatus.INFO, "Edit icon is visible on " + recordName, YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Edit icon is not visible on " + recordName, YesNo.No);
				}

			} else if (icon.toString().equals("Locked")) {
				xPath = "(//td[@data-label='" + recordName
						+ ": ']//span[text()='Locked']/preceding-sibling::lightning-primitive-icon)[1]";
				ele = CommonLib.FindElement(driver, xPath, "Locked icon on " + recordName, action.BOOLEAN, 50);

				verifyElement = CommonLib.isDisplayed(driver, ele, "Visibility", 30, "Locked icon on " + recordName);

				if (verifyElement != null) {
					log(LogStatus.INFO, "Locked icon is visible on " + recordName, YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Locked icon is not visible on " + recordName, YesNo.No);
				}
			}
		} else {
			log(LogStatus.ERROR, "Mouse is not hover on the " + recordName, YesNo.No);
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param sdgName
	 * @param labelWithValues
	 * @param action
	 * @param timeOut
	 * @return true if SDG created successfully
	 */

	public boolean editCustomSDGAfterClickOnOpenSDGRecordButton(String projectName, String sdgName,
			String[][] labelWithValues, action action, int timeOut) {
		boolean flag = false;
		if (clickUsingJavaScript(driver, sdgEditButton(timeOut), "Edit button")) {
			appLog.info("clicked on Edit button");

			enterValueForSDGCreation(projectName, labelWithValues, action, timeOut);
			if (click(driver, getRecordPageSettingSave(60), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Click on Save Button  " + sdgName, YesNo.No);
				ThreadSleep(5000);
				if (getSDGHeaderValueInViewMode(projectName, sdgName, timeOut) != null) {
					log(LogStatus.PASS, "Header verified for created  " + sdgName, YesNo.No);
					String label;
					String value;
					WebElement ele;
					CommonLib.refresh(driver);
					for (String[] labelValues : labelWithValues) {
						label = labelValues[0].replace("_", " ");
						value = labelValues[1];
						ele = sdgLabelValueElement(25, label);

						if (ele.getText().equals(value)) {
							log(LogStatus.INFO,
									"Value Expected: " + value + " and Actual: " + ele.getText() + " are same",
									YesNo.Yes);
							flag = true;
						} else {
							sa.assertTrue(false, "Value Expected: " + value + " but Actual: " + ele.getText());
							log(LogStatus.FAIL, "Value Expected: " + value + " but Actual: " + ele.getText(),
									YesNo.Yes);
							flag = false;

							break;
						}
					}
				} else {
					sa.assertTrue(false, "Header not verified for created  " + sdgName);
					log(LogStatus.SKIP, "Header not verified for created  " + sdgName, YesNo.Yes);
					flag = false;
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Save Button Value so cannot create  " + sdgName);
				log(LogStatus.SKIP, "Not Able to Click on Save Button Value so cannot create  " + sdgName, YesNo.Yes);
			}

		} else {
			appLog.error("Not able to click on Edit Button so cannot create sdg : " + sdgName);

		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param names
	 * @param values
	 * @return true if able to add Field on SDG
	 * @description pass Either SelectCheckbox or UnSelectCheckbox in the condition
	 *              parameter.
	 */
	public boolean editCheckBoxOfSDGAfterClickOnOpenSDGRecord(String projectName, String sdgName, Condition condition,
			String editSDGCheckBoxLabel, int timeOut) {
		if (click(driver, sdgEditButton(40), "Edit Button", action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on the Edit Button", YesNo.Yes);
			ThreadSleep(5000);
			boolean status = isSelected(driver, editSDGCheckBox(editSDGCheckBoxLabel, timeOut), editSDGCheckBoxLabel);

			if (condition.toString().equals("SelectCheckbox")) {
				if (status == false) {

					if (click(driver, editSDGCheckBox(editSDGCheckBoxLabel, timeOut),
							editSDGCheckBoxLabel + " checkbox", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the " + editSDGCheckBoxLabel + " checkbox", YesNo.Yes);
					} else {
						log(LogStatus.ERROR, "could not click on " + editSDGCheckBoxLabel + " checkbox button",
								YesNo.Yes);
						return false;
					}
				}
			} else if (condition.toString().equals("UnSelectCheckbox")) {
				if (status == true) {
					if (click(driver, editSDGCheckBox(editSDGCheckBoxLabel, timeOut), editSDGCheckBoxLabel + "checkbox",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the " + editSDGCheckBoxLabel + " checkbox", YesNo.Yes);
					} else {
						log(LogStatus.ERROR, "could not click on " + editSDGCheckBoxLabel + " checkbox button",
								YesNo.Yes);
						return false;
					}
				}
			} else {
				log(LogStatus.ERROR, "Please pass the correct value on condition. could not click on the checkbox",
						YesNo.Yes);
				return false;
			}

			if (click(driver, getSaveButton(projectName, 40), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the save button", YesNo.Yes);
				if (checkElementVisibility(driver, getconfirmationSaveMessage(projectName, 50),
						"Save Confirmation Message", 50)) {
					appLog.info("save confirmation Message is visible so " + editSDGCheckBoxLabel
							+ " checkbox has been clicked");
					ThreadSleep(7000);
					return true;
				} else {
					log(LogStatus.ERROR, "save confirmation message is not visible  so " + editSDGCheckBoxLabel
							+ " checkbox is not clicked", YesNo.Yes);
					return false;
				}
			} else {
				log(LogStatus.ERROR, "could not click on save button", YesNo.Yes);
				return false;
			}
		} else {
			log(LogStatus.ERROR, "could not click on edit button", YesNo.Yes);
			return false;
		}

	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param sdgName
	 * @param labelWithValues
	 * @param action
	 * @param timeOut
	 * @return true if SDG created successfully
	 */

	public boolean addMultipleFieldsOnSDG(String projectName, String sdgName, String[] fields, String[] values) {
		boolean flag = false;

		int i = 0;
		for (String field : fields) {

			if (addFieldOnSDG(projectName, field, values[i])) {
				log(LogStatus.INFO, "Successfully added fields on " + sdgName, YesNo.Yes);
				flag = true;
			} else {
				sa.assertTrue(false, "Not Able to add fields on SDG : " + sdgName);
				log(LogStatus.SKIP, "Not Able to add fields on SDG : " + sdgName, YesNo.Yes);
			}
			i++;
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param sdgName
	 * @param labelWithValues
	 * @param action
	 * @param timeOut
	 * @return true if SDG created successfully
	 */
	public boolean addActionOnSDG(String projectName, String names, String values) {
		names = names.replace("_", " ");
		String name[] = names.split("<Break>");
		// values= values.replace("_", " ");
		String value[] = values.split("<Break>");
		if (name.length != value.length) {
			log(LogStatus.INFO, name.length + " does not match " + value.length, YesNo.No);
			return false;
		}
		WebElement ele;
		boolean flag = true;
		ele = getRelatedTab(projectName, RelatedTab.Related.toString(), 10);
		if (click(driver, ele, "related tab", action.BOOLEAN)) {
			int i = 0;
			if (click(driver, actionsNewButton(projectName, 10), "Action new button", action.BOOLEAN)) {
				for (String f : name) {
					System.err.println(f);
					if (f.equalsIgnoreCase("Action Type")) {
						String xpath = "//label[text()='Action Type']/parent::lightning-combobox//div[contains(@class,'slds-listbox_vertical')]//span[@class='slds-truncate']";
						if (dropDownHandle(driver, sdgActionAndFieldDropDownButton(f, 20), xpath, "Dropdown: " + f,
								value[i])) {
							log(LogStatus.INFO, "Successfully Select the value:  " + value[i] + " from " + f,
									YesNo.Yes);
						} else {
							log(LogStatus.SKIP, "Not Successfully Select the value:  " + value[i] + " from " + f,
									YesNo.Yes);
							sa.assertTrue(false, "Not Successfully Select the value:  " + value[i] + " from " + f);
						}

					} else {
						ele = getLabelTextBox(projectName, PageName.SDGPage.toString(), f, 10);
						if (ele != null) {
							if (sendKeys(driver, ele, value[i], f, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "successfully entered " + value[i] + " in " + f, YesNo.Yes);

							} else {
								log(LogStatus.SKIP, "could not enter " + value[i] + " in " + f, YesNo.Yes);
								sa.assertTrue(false, "could not enter " + value[i] + " in " + f);

							}
						} else {
							ele = sdgActionAndFieldTextArea(f, 10);
							if (sendKeys(driver, ele, value[i], f, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "successfully entered " + value[i] + " in " + f, YesNo.Yes);

							} else {
								log(LogStatus.SKIP, "could not enter " + value[i] + " in " + f, YesNo.Yes);
								sa.assertTrue(false, "could not enter " + value[i] + " in " + f);

							}

						}

					}

					i++;
				}
				if (click(driver, getRecordPageSettingSave(10), "save", action.BOOLEAN)) {
					log(LogStatus.INFO, "successfully clicked on save button", YesNo.Yes);

				} else {
					log(LogStatus.SKIP, "could not click on save button, so could not add " + f, YesNo.Yes);
					flag = false;
				}

			} else {
				log(LogStatus.SKIP, "could not click on related tab, so cannot add " + f, YesNo.Yes);
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param sdgName
	 * @param labelWithValues
	 * @param action
	 * @param timeOut
	 * @return true if SDG created successfully
	 */

	public boolean addMultipleActionsOnSDG(String projectName, String sdgName, String[] actions, String[] values) {
		boolean flag = false;

		int i = 0;
		for (String action : actions) {

			if (addActionOnSDG(projectName, action, values[i])) {
				log(LogStatus.INFO, "Successfully added fields on " + sdgName, YesNo.Yes);
				flag = true;
			} else {
				sa.assertTrue(false, "Not Able to add fields on SDG : " + sdgName);
				log(LogStatus.SKIP, "Not Able to add fields on SDG : " + sdgName, YesNo.Yes);
			}
			i++;
		}
		return flag;
	}

	public boolean editCustomSDGAfterClickOnEdiButton(String projectName, String sdgName, String[][] labelWithValues,
			action action, int timeOut) {
		boolean flag = false;
		enterValueForSDGCreation(projectName, labelWithValues, action, timeOut);
		if (click(driver, getRecordPageSettingSave(60), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Click on Save Button  " + sdgName, YesNo.No);
			if (CommonLib.checkElementVisibility(driver, getsdgSaveConfirmationMsg(50),
					"Confirmation message after click on the save button", 50)) {
				log(LogStatus.INFO, "Verified: Save Confirmation Message", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Not Verified: Save Confirmation Message", YesNo.Yes);

			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Save Button Value so cannot create  " + sdgName);
			log(LogStatus.SKIP, "Not Able to Click on Save Button Value so cannot create  " + sdgName, YesNo.Yes);
		}
		return flag;
	}
	
	public boolean sdgFilterSendDataAndFound(String SDGName,String labelName, String value,String dropdown) {
		boolean flag = false;


		String xPath="//a[text()='"+SDGName+"']/ancestor::article//button[@title='Toggle Filters.']";
		WebElement ele=CommonLib.FindElement(driver, xPath, "Filter Icon", action.SCROLLANDBOOLEAN,50);
		if(CommonLib.click(driver, ele, "Filter Icon", action.BOOLEAN))
		{
			List<WebElement> filterLabelsList = getLabelsForFilters(SDGName);
			List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText()).collect(Collectors.toList())
					.stream().map(t -> t.trim()).collect(Collectors.toList());

			if (filterLabelsListText.contains(labelName)) {
				log(LogStatus.INFO, "--------Filter available: " + labelName + " --------", YesNo.No);
				if (CommonLib.selectVisibleTextFromDropDown(driver, selectTagForSDGFilterName(labelName, 25),
						dropdown + " Select DropDown", dropdown)) {
					log(LogStatus.INFO, "Select the Value From DropDown: " + dropdown, YesNo.No);
					CommonLib.ThreadSleep(20000);
					if (sendKeysAndPressEnter(driver, inputBoxForSDGFilterName(labelName, 20), value,
							labelName + " Input Box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Pass the Value to Input Box: " + value, YesNo.No);
						CommonLib.ThreadSleep(2000);	
						flag=true;

					} else {
						log(LogStatus.ERROR, "--------Not able to Pass the Value to Input Box: " + value + "--------",
								YesNo.Yes);
						sa.assertTrue(false,
								"--------Not able to Pass the Value to Input Box: " + value + "--------");
					}
				} else {
					log(LogStatus.ERROR, "--------Not able to Select the Value From DropDown: " + dropdown + "--------",
							YesNo.Yes);
					sa.assertTrue(false, "--------Not able to Select the Value From DropDown: " + dropdown + "--------");
				}
			} else {
				log(LogStatus.ERROR, "--------Filter Not available: " + value + "--------", YesNo.Yes);
				sa.assertTrue(false, "--------Filter Not available: " +value + "--------");
			}
		}
		else
		{
			log(LogStatus.ERROR, "--------Not able to click on the Filter Icon--------", YesNo.Yes);
			sa.assertTrue(false, "--------Not able to click on the Filter Icon--------");
		}

		return flag;
	}

	
	public boolean sdgFilterSendDataAndFound(String SDGName,String labelName, String dropdown) {
		boolean flag = false;


		String xPath="//a[text()='"+SDGName+"']/ancestor::article//button[@title='Toggle Filters.']";
		WebElement ele=CommonLib.FindElement(driver, xPath, "Filter Icon", action.SCROLLANDBOOLEAN,50);
		if(CommonLib.click(driver, ele, "Filter Icon", action.BOOLEAN))
		{
			List<WebElement> filterLabelsList = getLabelsForFilters(SDGName);
			List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText()).collect(Collectors.toList())
					.stream().map(t -> t.trim()).collect(Collectors.toList());

			if (filterLabelsListText.contains(labelName)) {
				log(LogStatus.INFO, "--------Filter available: " + labelName + " --------", YesNo.No);
				if (CommonLib.selectVisibleTextFromDropDown(driver, selectTagForSDGFilterName(labelName, 25),
						dropdown + " Select DropDown", dropdown)) {
					log(LogStatus.INFO, "Select the Value From DropDown: " + dropdown, YesNo.No);
					CommonLib.ThreadSleep(20000);
					flag=true;
					
				} else {
					log(LogStatus.ERROR, "--------Not able to Select the Value From DropDown: " + dropdown + "--------",
							YesNo.Yes);
					sa.assertTrue(false, "--------Not able to Select the Value From DropDown: " + dropdown + "--------");
				}
			} else {
				log(LogStatus.ERROR, "--------Filter Not available: " + dropdown + "--------", YesNo.Yes);
				sa.assertTrue(false, "--------Filter Not available: " +dropdown + "--------");
			}
		}
		else
		{
			log(LogStatus.ERROR, "--------Not able to click on the Filter Icon--------", YesNo.Yes);
			sa.assertTrue(false, "--------Not able to click on the Filter Icon--------");
		}

		return flag;
	}
	public boolean verifyFilter(String sdgName, String filterLabelName,String filerOptionValue)
	{
		boolean flag=false;
		String xPath="//a[text()='"+sdgName+"']/ancestor::article//button[@title='Toggle Filters.']";
		WebElement ele=CommonLib.FindElement(driver, xPath, "Filter Icon", action.SCROLLANDBOOLEAN,50);
		if(CommonLib.click(driver, ele, "Filter Icon", action.BOOLEAN))
		{
			List<WebElement> filterLabelsList = getLabelsForFilters(sdgName);
			List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText()).collect(Collectors.toList())
					.stream().map(t -> t.trim()).collect(Collectors.toList());

			if (filterLabelsListText.contains(filterLabelName)) {
				log(LogStatus.INFO, "--------Filter available: " + filterLabelName + " --------", YesNo.No);
				
				String text=CommonLib.getText(driver, inputBoxForSDGFilterName(filterLabelName, 20), filterLabelName+" value", action.SCROLLANDBOOLEAN);
				if(text.equals("filerOptionValue"))
				{
					log(LogStatus.INFO, "--------Filter available: " + filterLabelName + " --------", YesNo.No);
					flag=true;
				}
				else
				{
					log(LogStatus.ERROR, "--------Filter is not available: " + filterLabelName + " --------", YesNo.Yes);
				}
				
				
			} else {
				log(LogStatus.ERROR, "--------Filter Not available: " + filterLabelName + "--------", YesNo.Yes);
				sa.assertTrue(false, "--------Filter Not available: " +filterLabelName + "--------");
			}
		}
		else
		{
			log(LogStatus.ERROR, "--------Not able to click on the Filter Icon--------", YesNo.Yes);
			sa.assertTrue(false, "--------Not able to click on the Filter Icon--------");
		}

		return flag;
	
	}

}
