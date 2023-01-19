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
import org.openqa.selenium.interactions.Actions;
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
			sa.assertTrue(false, "Not able to click on New Button so cannot create sdg : " + sdgName);
			log(LogStatus.SKIP, "Not able to click on New Button so cannot create sdg : " + sdgName, YesNo.Yes);

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

	/**
	 * @author Sourabh saini
	 * @param projectName
	 * @param sdgName
	 * @return true if able to add Field on SDG
	 */

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
		refresh(driver);
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

	/**
	 * @author Sourabh saini
	 * @param projectName
	 * @param sdgName
	 * @param apiNameOrOverrideLabelName
	 * @param sequenceFilterOptionValue
	 * @return true if able to add Field on SDG
	 */

	public boolean sequenceFilter(String projectName, String sdgName, String apiNameOrOverrideLabelName,
			String sequenceFilterOptionValue) {
		String xpath = "";
		WebElement ele;
		if (openSDG(projectName, sdgName)) {
			if (click(driver, getrelatedTabOnSDG(40), "Related tab on SDG", action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the Related Button", YesNo.No);
				CommonLib.ThreadSleep(3000);

				if (CommonLib.clickUsingJavaScript(driver, getsortableDataGridFields(50), "View all button",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the View all button", YesNo.No);

					// xpath="//tbody//lst-formatted-text[text()='"++"']/ancestor::td/following-sibling::td//button";
					xpath = "//tbody//span[text()='" + apiNameOrOverrideLabelName
							+ "']/ancestor::td//following-sibling::td//a[@role='button']";
					ele = CommonLib.FindElement(driver, xpath, "Ero Button", action.SCROLLANDBOOLEAN, 50);
					if (click(driver, ele, "Ero button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the Ero button", YesNo.No);
						CommonLib.ThreadSleep(8000);
						if (click(driver, getsdgPageEditButton(40), "Edit button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the edit button", YesNo.No);
							CommonLib.ThreadSleep(3000);
							if (click(driver, getfilterSequenceButton(40), "Filter sequence button", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on the filter sequence button", YesNo.No);
								CommonLib.ThreadSleep(3000);
								if (CommonLib.getSelectedOptionOfDropDown(driver, getfilterSequenceDropdownList(),
										"Filter Sequence list", sequenceFilterOptionValue)) {
									log(LogStatus.INFO,
											"Option has been selected from the sequence filter field drop down",
											YesNo.No);
									if (click(driver, getsdgSaveBtn(40), "save button", action.BOOLEAN)) {
										log(LogStatus.INFO, "Clicked on the save button", YesNo.No);
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
					log(LogStatus.ERROR, "could not click on the view all button", YesNo.Yes);
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
		ele = CommonLib.FindElement(driver, xpath, elementName, action.SCROLLANDBOOLEAN, 20);
		if (ele != null) {
			log(LogStatus.INFO, elementName + " element has been Found", YesNo.No);
			return true;

		} else {
			log(LogStatus.INFO, "could not get the " + elementName + " element", YesNo.No);
			return false;
		}

	}

	/**
	 * @author Sourabh saini
	 * @param sdgName
	 * @param filterOptionValue
	 * @param fieldName
	 * @param elementName
	 * @return true if able to add Field on SDG
	 */

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
	 * @author Sourabh saini
	 * @param sdgName
	 * @param recordName
	 * @param icon
	 * @return true if able to add Field on SDG
	 */

	public boolean verifyEditOrLockedIconOnSDGData(String sdgName, String recordName, IconType icon) {
		String xPath = "";
		WebElement ele = null;
		WebElement verifyElement = null;
		boolean flag = false;
		CommonLib.ThreadSleep(2000);

		xPath = "//a[text()='" + sdgName + "']/ancestor::article//td[@data-label='" + recordName + ": ']";
		ele = CommonLib.FindElement(driver, xPath, recordName + " Record Name", action.BOOLEAN, 50);
		if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, recordName)) {
			if (mouseOverOperation(driver, ele)) {
				log(LogStatus.INFO, "Mouse has been hover to " + recordName, YesNo.No);

				if (icon.toString().equals("Edit")) {
					xPath = "(//a[text()='" + sdgName + "']/ancestor::article//td[@data-label='" + recordName
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
					xPath = "(//a[text()='" + sdgName + "']/ancestor::article//td[@data-label='" + recordName
							+ ": ']//span[text()='Locked']/preceding-sibling::lightning-primitive-icon)[1]";
					ele = CommonLib.FindElement(driver, xPath, "Locked icon on " + recordName, action.BOOLEAN, 50);

					verifyElement = CommonLib.isDisplayed(driver, ele, "Visibility", 30,
							"Locked icon on " + recordName);

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
		} else {
			log(LogStatus.ERROR, "Window has been scrolled to " + recordName, YesNo.No);
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

	/**
	 * @author Sourabh saini
	 * @param sdgName
	 * @param condition
	 * @return true if able to add Field on SDG
	 */

	public boolean CheckedOrUncheckedCheckox(String sdgGridName, Condition condition) {
		boolean flag = false;
		if (condition.toString().equals("SelectCheckbox")) {
			if (!CommonLib.isSelected(driver, getsdgGridCheckbox(sdgGridName, 50), sdgGridName)) {
				if (click(driver, getsdgGridCheckbox(sdgGridName, 50), sdgGridName + " checkbox Button",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the checkbox of " + sdgGridName, YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Could not click on the checkbox of " + sdgGridName, YesNo.Yes);

				}

			} else {
				log(LogStatus.INFO, sdgGridName + " checkbox is already selected", YesNo.No);
				flag = true;
			}
		}

		if (condition.toString().equals("UnSelectCheckbox")) {
			if (CommonLib.isSelected(driver, getsdgGridCheckbox(sdgGridName, 50), sdgGridName)) {
				if (click(driver, getsdgGridCheckbox(sdgGridName, 50), sdgGridName + " checkbox Button",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the checkbox of " + sdgGridName, YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Could not click on the checkbox of " + sdgGridName, YesNo.Yes);

				}

			} else {
				log(LogStatus.INFO, sdgGridName + " checkbox is already unselected", YesNo.No);
				flag = true;
			}
		}
		return flag;

	}

	/**
	 * @author Sourabh saini
	 * @param sdgName
	 * @param condition
	 * @return true if able to add Field on SDG
	 */

	public int CheckedOrUncheckedCheckboxCountOnSDGGrid(String sdgGridName, Condition condition) {
		String xPath = "";
		int count = 0;
		List<WebElement> elements = null;
		if (condition.toString().equals("SelectCheckbox")) {
			xPath = "//a[text()='" + sdgGridName
					+ "']/ancestor::article//lightning-input[@class='checkrow slds-form-element']//input[@type='checkbox']";
			elements = CommonLib.FindElements(driver, xPath, sdgGridName);
			for (int i = 0; i < elements.size(); i++) {
				if (isSelected(driver, elements.get(i), sdgGridName)) {
					count++;
				} else {
					continue;
				}
			}
			log(LogStatus.INFO, "Selected checkbox count is " + count, YesNo.No);

		}

		if (condition.toString().equals("UnSelectCheckbox")) {

			xPath = "//a[text()='" + sdgGridName
					+ "']/ancestor::article//lightning-input[@class='checkrow slds-form-element']//input[@type='checkbox']";
			elements = CommonLib.FindElements(driver, xPath, sdgGridName);
			for (int i = 0; i < elements.size(); i++) {
				if (isSelected(driver, elements.get(i), sdgGridName)) {
					count++;
				} else {
					continue;
				}
			}
			log(LogStatus.INFO, "Unselected checkbox count is " + count, YesNo.No);

		}
		return count;
	}

	/**
	 * @author Sourabh saini
	 * @param sdgName
	 * @param recordName
	 * @return boolean
	 */

	public boolean removeRecordAndVerifyErrorMessage(String sdgName, String recordName) {
		boolean flag = false;
		String xPath = "";
		WebElement ele = null;

		xPath = "(//a[text()='" + sdgName + "']//ancestor::article//td[@data-label='" + recordName + ": '])[3]";
		ele = FindElement(driver, xPath, recordName + " Record Name", action.BOOLEAN, 50);
		if (mouseOverOperation(driver, ele)) {
			log(LogStatus.INFO, "Mouse has been moved to " + recordName, YesNo.No);
			xPath = "(//a[text()='" + sdgName + "']//ancestor::article//td[@data-label='" + recordName + ": '])[1]";
			ele = FindElement(driver, xPath, recordName + " Record Name", action.BOOLEAN, 50);
			if (mouseOverOperation(driver, ele)) {
				log(LogStatus.INFO, "Mouse has been moved to " + recordName, YesNo.No);
				xPath = "(//td[@data-label='" + recordName
						+ ": ']//span[text()='Edit']/preceding-sibling::lightning-primitive-icon/parent::button)[1]";
				ele = FindElement(driver, xPath, "Edit icon on " + recordName, action.BOOLEAN, 50);
				mouseOverOperation(driver, ele);
				if (CommonLib.clickUsingJavaScript(driver, ele, "Edit icon of " + recordName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the Edit icon", YesNo.Yes);

					xPath = "//a[text()='" + sdgName + "']//ancestor::article//td[@data-label='" + recordName
							+ ": ']//input";
					ele = FindElement(driver, xPath, "Edit icon on " + recordName, action.BOOLEAN, 50);
					if (sendKeys(driver, ele, "", recordName, action.BOOLEAN)) {
						log(LogStatus.INFO, "blank value has been passed in " + recordName, YesNo.No);
						ThreadSleep(5000);
						xPath = "//a[text()='" + sdgName + "']/ancestor::article";
						ele = FindElement(driver, xPath, recordName, action.BOOLEAN, 50);
						if (CommonLib.doubleClickUsingAction(driver, ele)) {
							log(LogStatus.INFO, "Clicked on the " + sdgName + " body", YesNo.Yes);

							CommonLib.ThreadSleep(8000);
							xPath = "//a[text()='" + sdgName + "']//ancestor::article//button[text()='Save']";
							ele = FindElement(driver, xPath, "Save button of " + sdgName, action.BOOLEAN, 10);
							if (click(driver, ele, "Clicked on the Save button", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on the save button", YesNo.Yes);
								if (CommonLib.checkElementVisibility(driver, geterrorMessageAfterSaveBlankRecord(50),
										"Error Message", 10)) {
									log(LogStatus.INFO,
											"\"1 record has error. Kindly resolve them and try again.\" Error Message is visible",
											YesNo.Yes);
									flag = true;
								} else {
									log(LogStatus.INFO,
											"\"1 record has error. Kindly resolve them and try again.\" Error Message is not visible",
											YesNo.Yes);
								}
							} else {
								log(LogStatus.ERROR, "Could not click on save button", YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "Could not click on " + sdgName, YesNo.No);
						}
					} else {
						log(LogStatus.ERROR, "Could not pass the blank value in the " + recordName + "", YesNo.No);
					}
				} else {
					log(LogStatus.ERROR, "Could not click on the Edit icon of " + recordName + "", YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, "Not able to move to " + recordName, YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "Not able to move to " + recordName, YesNo.No);
		}

		return flag;

	}

	/**
	 * @author Sourabh saini
	 * @param sdgName
	 * @param recordName
	 * @return boolean
	 */

	public boolean verifyErrorMessage(String sdgName, String recordName) {
		boolean flag = false;
		String xPath = "";
		WebElement ele = null;

		xPath = "(//a[text()='" + sdgName + "']//ancestor::article//td[@data-label='" + recordName + ": '])[3]";
		ele = FindElement(driver, xPath, recordName + " Record Name", action.BOOLEAN, 50);
		mouseOverOperation(driver, ele);
		ThreadSleep(3000);

		xPath = "//a[text()=\"" + sdgName
				+ "\"]/ancestor::article//button[@class=\"slds-button slds-button_icon slds-button_icon-bare\" and @title=\"Help\"]";
		ele = FindElement(driver, xPath, "Error triangle icon", action.BOOLEAN, 50);
		if (mouseOverOperation(driver, ele)) {
			log(LogStatus.INFO, "Mouse has been moved to error triangle icon", YesNo.No);

			if (checkElementVisibility(driver, gettriangleErrorIcon(50), "Error Message", 50)) {
				log(LogStatus.INFO, "Error Message is visible", YesNo.No);
				String text = CommonLib.getText(driver, gettriangleErrorIcon(50), "Triangle  error message",
						action.BOOLEAN);
				System.out.println("text " + test);
				if (text.contains("[Name] field is required. Required fields are missing: [Legal Name].")) {
					log(LogStatus.INFO, "Error message has been verified on the hover of trianlge icon", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Error message is not verified visible on the hover of trianlge icon",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Error Message is not visible", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Mouse is not moved to the triangle icon", YesNo.No);
		}

		return flag;

	}

	/**
	 * @author Sourabh saini
	 * @param sdgName
	 * @param recordName
	 * @param value
	 * @return boolean
	 */

	public boolean updateRecordAndVerifyMessage(String sdgName, String recordName, String value) {
		boolean flag = false;
		String xPath = "";
		WebElement ele = null;

		xPath = "(//a[text()='" + sdgName + "']//ancestor::article//td[@data-label='" + recordName + ": '])[3]";
		ele = FindElement(driver, xPath, recordName + " Record Name", action.BOOLEAN, 50);
		if (mouseOverOperation(driver, ele)) {
			log(LogStatus.INFO, "Mouse has been moved to " + recordName, YesNo.No);
			xPath = "(//a[text()='" + sdgName + "']//ancestor::article//td[@data-label='" + recordName + ": '])[1]";
			ele = FindElement(driver, xPath, recordName + " Record Name", action.BOOLEAN, 50);
			if (mouseOverOperation(driver, ele)) {
				log(LogStatus.INFO, "Mouse has been moved to " + recordName, YesNo.No);
				xPath = "(//td[@data-label='" + recordName
						+ ": ']//span[text()='Edit']/preceding-sibling::lightning-primitive-icon/parent::button)[1]";
				ele = FindElement(driver, xPath, "Edit icon on " + recordName, action.BOOLEAN, 50);
				mouseOverOperation(driver, ele);
				if (CommonLib.clickUsingJavaScript(driver, ele, "Edit icon of " + recordName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the Edit icon", YesNo.Yes);

					xPath = "//a[text()='" + sdgName + "']//ancestor::article//td[@data-label='" + recordName
							+ ": ']//input";
					ele = FindElement(driver, xPath, "Edit icon on " + recordName, action.BOOLEAN, 50);
					if (sendKeys(driver, ele, value, recordName, action.BOOLEAN)) {
						log(LogStatus.INFO, "blank value has been passed in " + recordName, YesNo.No);
						ThreadSleep(5000);
						xPath = "//a[text()='" + sdgName + "']/ancestor::article";
						ele = FindElement(driver, xPath, recordName, action.BOOLEAN, 50);
						if (CommonLib.doubleClickUsingAction(driver, ele)) {
							log(LogStatus.INFO, "Clicked on the " + sdgName + " body", YesNo.Yes);

							xPath = "//a[text()='" + sdgName + "']//ancestor::article//button[text()='Save']";
							ele = FindElement(driver, xPath, "Save button of " + sdgName, action.BOOLEAN, 50);
							ThreadSleep(5000);
							if (click(driver, ele, "Save button", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on the save button", YesNo.Yes);
								if (CommonLib.checkElementVisibility(driver, getUpdateMessageAfterSaveRecord(50),
										"Update Message", 50)) {
									log(LogStatus.INFO, "\"Your changes are saved.\" Update Message is visible",
											YesNo.Yes);
									flag = true;
								} else {
									log(LogStatus.INFO, "\"Your changes are saved.\" Update Message is not visible",
											YesNo.Yes);
								}
							} else {
								log(LogStatus.ERROR, "Could not click on save button", YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "Could not click on " + sdgName, YesNo.No);
						}
					} else {
						log(LogStatus.ERROR, "Could not pass the blank value in the " + recordName + "", YesNo.No);
					}
				} else {
					log(LogStatus.ERROR, "Could not click on the Edit icon of " + recordName + "", YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, "Not able to move to " + recordName, YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "Not able to move to " + recordName, YesNo.No);
		}

		return flag;

	}

	/**
	 * @author Sourabh saini
	 * @param sdgName
	 * @param firstSDGRowName
	 * @param recordName
	 * @param value
	 * @param inputType
	 * @param multiPiclistValue
	 * @return boolean
	 */

	public boolean updateSDGRecordAndVerifySaveCancelButton(String sdgGridName, String firstSDGRowName,
			String recordName, String value, String inputType, ArrayList<String> multiPiclistValue) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;
		List<WebElement> elements = null;

		xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGRowName
				+ "']//ancestor::td//following-sibling::td[@data-Label='" + recordName + ": ']";
		ele = FindElement(driver, xPath, recordName + " Record Name", action.BOOLEAN, 50);
		if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
			ThreadSleep(2000);
			log(LogStatus.INFO, "Window has been scrolled to " + recordName, YesNo.No);
			if (mouseOverOperation(driver, ele)) {
				log(LogStatus.INFO, "Mouse has been moved to " + recordName, YesNo.No);
				xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGRowName
						+ "']//ancestor::td//following-sibling::td[@data-Label='" + recordName + ": ']//button";
				ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

				if (CommonLib.clickUsingJavaScript(driver, ele, "Edit icon of " + recordName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the Edit icon of " + recordName, YesNo.Yes);

					CommonLib.ThreadSleep(3000);

					if (inputType.equals("Text")) {
						xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGRowName
								+ "']//ancestor::td//following-sibling::td[@data-Label='" + recordName
								+ ": ']//input[@type='text']";
						ele = FindElement(driver, xPath, recordName + " Textbox", action.BOOLEAN, 50);
						if (sendKeys(driver, ele, value, recordName, action.BOOLEAN)) {
							CommonLib.scrollDownThroughWebelementInCenter(driver, ele, recordName + "record");

							log(LogStatus.INFO, value + " has been passed in " + recordName, YesNo.No);

							ThreadSleep(3000);

							xPath = "//a[text()='" + sdgGridName + "']/ancestor::article//span[text()='Page Size']";
							ele = FindElement(driver, xPath, "pagesize text of " + sdgGridName, action.BOOLEAN, 50);

							ThreadSleep(1000);
							CommonLib.scrollDownThroughWebelementInCenter(driver, ele,
									"pagesize text of " + sdgGridName);
							if (CommonLib.doubleClickUsingAction(driver, ele)) {
								log(LogStatus.INFO, "Clicked on the " + sdgGridName + " body", YesNo.No);
								ThreadSleep(5000);
								xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//button[text()='Save']";
								WebElement saveBtnElement = FindElement(driver, xPath, "Save button of " + sdgGridName,
										action.BOOLEAN, 50);
								xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//button[text()='Cancel']";
								WebElement cancelBtnElement = FindElement(driver, xPath,
										"Cancel button of " + sdgGridName, action.BOOLEAN, 50);
								CommonLib.scrollDownThroughWebelementInCenter(driver, saveBtnElement, recordName);
								if (CommonLib.checkElementVisibility(driver, saveBtnElement, "Save Button", 50)) {
									log(LogStatus.PASS, "Save button is visible after updating the record", YesNo.No);
									sa.assertTrue(true, "Save button is visible after updating the record");
								} else {
									log(LogStatus.FAIL, "Save button is not visible after updating the record",
											YesNo.No);
									sa.assertTrue(false, "Save button is not visible after updating the record");
								}

								if (CommonLib.checkElementVisibility(driver, cancelBtnElement, "Cancel Button", 50)) {
									log(LogStatus.PASS, "Cancel button is visible after updating the record", YesNo.No);
									sa.assertTrue(true, "Cancel button is visible after updating the record");
								} else {
									log(LogStatus.FAIL, "Cancel button is not visible after updating the record",
											YesNo.No);
									sa.assertTrue(false, "Cancel button is not visible after updating the record");
								}

								if (click(driver, saveBtnElement, "Save Button", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on the save button", YesNo.Yes);

									if (CommonLib.checkElementVisibility(driver, getUpdateMessageAfterSaveRecord(50),
											"Update Message", 50)) {
										log(LogStatus.INFO, "\"Your changes are saved.\" Update Message is visible",
												YesNo.Yes);
										flag = true;

									} else {
										log(LogStatus.ERROR,
												"\"Your changes are saved.\" Update Message is not visible", YesNo.Yes);
									}

								} else {
									log(LogStatus.ERROR, "Could not click on the save button", YesNo.Yes);
								}

							} else {
								log(LogStatus.ERROR, "Could not click on the " + sdgGridName + " body", YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "Could not enter the " + value + " in the field of " + recordName,
									YesNo.Yes);
						}
					}

					if (inputType.equals("Picklist")) {
						xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGRowName
								+ "']//ancestor::td//following-sibling::td[@data-Label='" + recordName
								+ ": ']//lightning-base-combobox//button";
						ele = FindElement(driver, xPath, recordName + " button", action.BOOLEAN, 50);

						CommonLib.scrollDownThroughWebelementInCenter(driver, ele, recordName + " record");
						CommonLib.ThreadSleep(2000);

						if (CommonLib.clickUsingJavaScript(driver, ele, recordName, action.BOOLEAN)) {
							log(LogStatus.INFO, recordName + " record has been clicked", YesNo.No);
							CommonLib.ThreadSleep(3000);
							xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
									+ firstSDGRowName + "']//ancestor::td//following-sibling::td[@data-Label='"
									+ recordName + ": ']//lightning-base-combobox-item";
							elements = CommonLib.FindElements(driver, xPath, recordName + " record dropdown list");
							if (getSelectedOptionOfDropDown(driver, elements, recordName + " record dropdown list",
									value)) {
								log(LogStatus.INFO,
										value + " has been selected from the " + recordName + " record list", YesNo.No);

								ThreadSleep(5000);
								xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
										+ firstSDGRowName + "']";
								ele = FindElement(driver, xPath, "Custom App Page Heading", action.BOOLEAN, 50);
								CommonLib.mouseOverOperation(driver, ele);
								ThreadSleep(1000);
								xPath = "//a[text()='" + sdgGridName + "']/ancestor::article//header";
								ele = FindElement(driver, xPath, "Custom App Page Heading", action.BOOLEAN, 50);
								CommonLib.click(driver, ele, xPath, action.SCROLLANDBOOLEAN);
								CommonLib.doubleClickUsingAction(driver, ele);
								ThreadSleep(1500);
								xPath = "//a[text()='" + sdgGridName + "']/ancestor::article//span[text()='Page Size']";
								ele = FindElement(driver, xPath, sdgGridName + " page Size text", action.BOOLEAN, 50);
								if (CommonLib.doubleClickUsingAction(driver, ele)) {
									log(LogStatus.INFO, "Clicked on the " + sdgGridName + " page size text", YesNo.No);
									ThreadSleep(3000);
									xPath = "//a[text()='" + sdgGridName
											+ "']//ancestor::article//button[text()='Save']";
									WebElement saveBtnElement = FindElement(driver, xPath,
											"Save button of " + sdgGridName, action.BOOLEAN, 50);
									xPath = "//a[text()='" + sdgGridName
											+ "']//ancestor::article//button[text()='Cancel']";
									WebElement cancelBtnElement = FindElement(driver, xPath,
											"Cancel button of " + sdgGridName, action.BOOLEAN, 50);
									CommonLib.scrollDownThroughWebelementInCenter(driver, saveBtnElement, recordName);
									if (CommonLib.checkElementVisibility(driver, saveBtnElement, "Save Button", 50)) {
										log(LogStatus.PASS, "Save button is visible after updating the record",
												YesNo.No);
										sa.assertTrue(true, "Save button is visible after updating the record");
									} else {
										log(LogStatus.FAIL, "Save button is not visible after updating the record",
												YesNo.No);
										sa.assertTrue(false, "Save button is not visible after updating the record");
									}

									if (CommonLib.checkElementVisibility(driver, cancelBtnElement, "Cancel Button",
											50)) {
										log(LogStatus.PASS, "Cancel button is visible after updating the record",
												YesNo.No);
										sa.assertTrue(true, "Cancel button is visible after updating the record");
									} else {
										log(LogStatus.FAIL, "Cancel button is not visible after updating the record",
												YesNo.No);
										sa.assertTrue(false, "Cancel button is not visible after updating the record");
									}

									if (click(driver, saveBtnElement, "Save Button", action.BOOLEAN)) {
										log(LogStatus.INFO, "Clicked on the save button", YesNo.Yes);

										if (CommonLib.checkElementVisibility(driver,
												getUpdateMessageAfterSaveRecord(50), "Update Message", 50)) {
											log(LogStatus.INFO, "\"Your changes are saved.\" Update Message is visible",
													YesNo.Yes);
											flag = true;

										} else {
											log(LogStatus.ERROR,
													"\"Your changes are saved.\" Update Message is not visible",
													YesNo.Yes);
										}

									} else {
										log(LogStatus.ERROR, "Could not click on the save button", YesNo.Yes);
									}

								} else {
									log(LogStatus.ERROR, "Could not click on the " + sdgGridName + " body", YesNo.No);
								}
							} else {
								log(LogStatus.ERROR, "Could not select the " + value + " from the drop down of "
										+ recordName + " record", YesNo.No);
							}

						} else {
							log(LogStatus.ERROR, "Could not click on the " + recordName + " record", YesNo.Yes);
						}

					}

					if (inputType.equals("Multipicklist")) {
						xPath = "//a[text()='" + sdgGridName
								+ "']//ancestor::article//div[span[text()='Available']]//li//span[text()='"
								+ multiPiclistValue.get(0) + "']";
						ele = FindElement(driver, xPath, recordName + " record picklist", action.BOOLEAN, 50);

						CommonLib.scrollDownThroughWebelementInCenter(driver, ele, recordName + " record picklist");
						CommonLib.ThreadSleep(2000);

						List<WebElement> element = new ArrayList<WebElement>();
						for (int i = 0; i < multiPiclistValue.size(); i++) {
							xPath = "//a[text()='" + sdgGridName
									+ "']//ancestor::article//div[span[text()='Available']]//li//span[text()='"
									+ multiPiclistValue.get(i) + "']";
							ele = FindElement(driver, xPath, multiPiclistValue.get(i) + " value", action.BOOLEAN, 50);
							element.add(ele);
						}

						Actions act = new Actions(driver);
						act.keyDown(Keys.CONTROL).build().perform();
						for (int i = 0; i < element.size(); i++) {

							CommonLib.ThreadSleep(2000);
							if (i == 0) {
								if (click(driver, element.get(i), recordName, action.BOOLEAN)) {
									log(LogStatus.INFO, element.get(i) + " has been clicked", YesNo.No);
								} else {
									log(LogStatus.ERROR, "Could not click on " + element.get(i), YesNo.No);
								}
								if (click(driver, element.get(i), recordName, action.BOOLEAN)) {
									log(LogStatus.INFO, element.get(i) + " has been clicked", YesNo.No);
								} else {
									log(LogStatus.ERROR, "Could not click on " + element.get(i), YesNo.No);
								}

							}

							else {
								if (click(driver, element.get(i), recordName, action.BOOLEAN)) {
									log(LogStatus.INFO, element.get(i) + " has been clicked", YesNo.No);
								} else {
									log(LogStatus.ERROR, element.get(i) + " is not clicked", YesNo.No);
								}
							}
						}
						act.keyUp(Keys.CONTROL).build().perform();

						CommonLib.ThreadSleep(3000);

						xPath = "//a[text()='" + sdgGridName
								+ "']//ancestor::article//button[@title='Move selection to Chosen']";
						ele = FindElement(driver, xPath, "Move Selection right icon", action.BOOLEAN, 50);
						if (click(driver, ele, "Move Selection right icon", action.BOOLEAN)) {
							log(LogStatus.INFO, "Move Selection right icon has been selected", YesNo.No);
							ThreadSleep(3000);
							xPath = "//a[text()='" + sdgGridName
									+ "']//ancestor::article//div[span[text()='Chosen']]//li//span[text()='"
									+ multiPiclistValue.get(0) + "']";
							ele = FindElement(driver, xPath, "Chosen value", action.BOOLEAN, 50);

							if (CommonLib.isElementPresent(ele)) {
								log(LogStatus.INFO, "values are persent in the chosen textarea", YesNo.No);
								ThreadSleep(3000);
								xPath = "//a[text()='" + sdgGridName + "']/ancestor::article//header";
								ele = FindElement(driver, xPath, "Custom App Page Heading", action.BOOLEAN, 50);
								CommonLib.click(driver, ele, xPath, action.SCROLLANDBOOLEAN);
								CommonLib.doubleClickUsingAction(driver, ele);
								xPath = "//a[text()='" + sdgGridName + "']/ancestor::article//span[text()='Page Size']";
								ele = FindElement(driver, xPath, recordName, action.BOOLEAN, 50);
								if (CommonLib.doubleClickUsingAction(driver, ele)) {
									log(LogStatus.INFO, "Clicked on the " + sdgGridName + " page size text", YesNo.No);
									ThreadSleep(4000);
									xPath = "//a[text()='" + sdgGridName
											+ "']//ancestor::article//button[text()='Save']";
									WebElement saveBtnElement = FindElement(driver, xPath,
											"Save button of " + sdgGridName, action.BOOLEAN, 50);
									xPath = "//a[text()='" + sdgGridName
											+ "']//ancestor::article//button[text()='Cancel']";
									WebElement cancelBtnElement = FindElement(driver, xPath,
											"Cancel button of " + sdgGridName, action.BOOLEAN, 50);
									CommonLib.scrollDownThroughWebelementInCenter(driver, saveBtnElement, recordName);
									if (CommonLib.checkElementVisibility(driver, saveBtnElement, "Save Button", 50)) {
										log(LogStatus.PASS, "Save button is visible after updating the record",
												YesNo.No);
										sa.assertTrue(true, "Save button is visible after updating the record");
									} else {
										log(LogStatus.FAIL, "Save button is not visible after updating the record",
												YesNo.No);
										sa.assertTrue(false, "Save button is not visible after updating the record");
									}

									if (CommonLib.checkElementVisibility(driver, cancelBtnElement, "Cancel Button",
											50)) {
										log(LogStatus.PASS, "Cancel button is visible after updating the record",
												YesNo.No);
										sa.assertTrue(true, "Cancel button is visible after updating the record");
									} else {
										log(LogStatus.FAIL, "Cancel button is not visible after updating the record",
												YesNo.No);
										sa.assertTrue(false, "Cancel button is not visible after updating the record");
									}

									if (click(driver, saveBtnElement, "Save Button", action.BOOLEAN)) {
										log(LogStatus.INFO, "Clicked on the save button", YesNo.Yes);

										if (CommonLib.checkElementVisibility(driver,
												getUpdateMessageAfterSaveRecord(50), "Update Message", 50)) {
											log(LogStatus.INFO, "\"Your changes are saved.\" Update Message is visible",
													YesNo.Yes);
											flag = true;

										} else {
											log(LogStatus.ERROR,
													"\"Your changes are saved.\" Update Message is not visible",
													YesNo.Yes);
										}

									} else {
										log(LogStatus.ERROR, "Could not click on the save button", YesNo.Yes);
									}

								} else {
									log(LogStatus.ERROR, "Could not click on the " + sdgGridName + " page size text",
											YesNo.No);
								}
							} else {
								log(LogStatus.ERROR, "values are not persent in the chosen textarea", YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Move Selection right icon is not clicked", YesNo.Yes);

						}
					}

					if (inputType.equals("Textarea")) {
						xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGRowName
								+ "']//ancestor::td//following-sibling::td[@data-Label='" + recordName
								+ ": ']//textarea";
						ele = FindElement(driver, xPath, recordName + " Textbox", action.BOOLEAN, 50);
						CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "TextArea");

						if (sendKeys(driver, ele, value, recordName, action.BOOLEAN)) {
							log(LogStatus.INFO, value + " has been passed in " + recordName, YesNo.No);
							ThreadSleep(5000);

							xPath = "//a[text()='" + sdgGridName + "']/ancestor::article//header";
							ele = FindElement(driver, xPath, "Custom App Page Heading", action.BOOLEAN, 50);
							CommonLib.click(driver, ele, xPath, action.SCROLLANDBOOLEAN);
							CommonLib.doubleClickUsingAction(driver, ele);

							xPath = "//a[text()='" + sdgGridName + "']/ancestor::article//span[text()='Page Size']";
							ele = FindElement(driver, xPath, recordName, action.BOOLEAN, 50);

							if (CommonLib.doubleClickUsingAction(driver, ele)) {
								log(LogStatus.INFO, "Clicked on the " + sdgGridName + " page size text", YesNo.No);
								ThreadSleep(5000);
								xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//button[text()='Save']";
								WebElement saveBtnElement = FindElement(driver, xPath, "Save button of " + sdgGridName,
										action.BOOLEAN, 50);
								xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//button[text()='Cancel']";
								WebElement cancelBtnElement = FindElement(driver, xPath,
										"Cancel button of " + sdgGridName, action.BOOLEAN, 50);
								CommonLib.scrollDownThroughWebelementInCenter(driver, saveBtnElement, recordName);
								if (CommonLib.checkElementVisibility(driver, saveBtnElement, "Save Button", 50)) {
									log(LogStatus.PASS, "Save button is visible after updating the record", YesNo.No);
									sa.assertTrue(true, "Save button is visible after updating the record");
								} else {
									log(LogStatus.FAIL, "Save button is not visible after updating the record",
											YesNo.No);
									sa.assertTrue(false, "Save button is not visible after updating the record");
								}

								if (CommonLib.checkElementVisibility(driver, cancelBtnElement, "Cancel Button", 50)) {
									log(LogStatus.PASS, "Cancel button is visible after updating the record", YesNo.No);
									sa.assertTrue(true, "Cancel button is visible after updating the record");
								} else {
									log(LogStatus.FAIL, "Cancel button is not visible after updating the record",
											YesNo.No);
									sa.assertTrue(false, "Cancel button is not visible after updating the record");
								}

								if (click(driver, saveBtnElement, "Save Button", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on the save button", YesNo.Yes);

									if (CommonLib.checkElementVisibility(driver, getUpdateMessageAfterSaveRecord(50),
											"Update Message", 50)) {
										log(LogStatus.INFO, "\"Your changes are saved.\" Update Message is visible",
												YesNo.Yes);
										flag = true;

									} else {
										log(LogStatus.ERROR,
												"\"Your changes are saved.\" Update Message is not visible", YesNo.Yes);
									}

								} else {
									log(LogStatus.ERROR, "Could not click on the save button", YesNo.Yes);
								}

							} else {
								log(LogStatus.ERROR, "Could not click on the " + sdgGridName + " body", YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "Could not enter the " + value + " in the inputox", YesNo.Yes);
						}
					}
				} else {
					log(LogStatus.ERROR, "Could not click on the edit icon " + recordName, YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to move to " + recordName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Window has been scrolled to " + firstSDGRowName, YesNo.Yes);
		}

		return flag;

	}

	/**
	 * @author Sourabh saini
	 * @param sdgGridName
	 * @param firstSDGColumnName
	 * @param legalfieldName
	 * @param legalfieldValue
	 * @param websiteFieldName
	 * @param websiteFieldValue
	 * @param revenueFieldName
	 * @param revenueFieldValue
	 * @return boolean
	 */

	public boolean upadateLegalNameWebsiteRevenueAndVerifyErrorMessageSaveCancelButton(String sdgGridName,
			String firstSDGColumnName, String legalfieldName, String legalfieldValue, String websiteFieldName,
			String websiteFieldValue, String revenueFieldName, String revenueFieldValue) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;
		List<WebElement> elements = null;

		xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
				+ "']//ancestor::td//following-sibling::td[@data-Label='" + websiteFieldName + ": ']";
		ele = FindElement(driver, xPath, websiteFieldName + " Record Name", action.BOOLEAN, 50);
		if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
			ThreadSleep(2000);
			log(LogStatus.INFO, "Window has been scrolled to " + websiteFieldName, YesNo.No);
			if (mouseOverOperation(driver, ele)) {
				log(LogStatus.INFO, "Mouse has been moved to " + websiteFieldName, YesNo.No);
				xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
						+ "']//ancestor::td//following-sibling::td[@data-Label='" + websiteFieldName + ": ']//button";
				ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

				if (CommonLib.clickUsingJavaScript(driver, ele, "Edit icon of " + websiteFieldName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the Edit icon of " + websiteFieldName, YesNo.Yes);
					CommonLib.ThreadSleep(3000);

					xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
							+ "']//ancestor::td//following-sibling::td[@data-Label='" + websiteFieldName
							+ ": ']//input[@type='text']";
					ele = FindElement(driver, xPath, websiteFieldName + " Textbox", action.BOOLEAN, 50);
					if (sendKeys(driver, ele, websiteFieldValue, websiteFieldName, action.BOOLEAN)) {
						log(LogStatus.INFO, websiteFieldValue + " has been passed in " + websiteFieldName, YesNo.No);

						ThreadSleep(3000);

						xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
								+ firstSDGColumnName + "']//ancestor::td//following-sibling::td[@data-Label='"
								+ revenueFieldName + ": ']";
						ele = FindElement(driver, xPath, revenueFieldName + " Record Name", action.BOOLEAN, 50);
						if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
							ThreadSleep(2000);
							log(LogStatus.INFO, "Window has been scrolled to " + revenueFieldName, YesNo.No);
							if (mouseOverOperation(driver, ele)) {
								log(LogStatus.INFO, "Mouse has been moved to " + revenueFieldName, YesNo.No);
								xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
										+ firstSDGColumnName + "']//ancestor::td//following-sibling::td[@data-Label='"
										+ revenueFieldName + ": ']//button";
								ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

								if (CommonLib.clickUsingJavaScript(driver, ele, "Edit icon of " + revenueFieldName,
										action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on the Edit icon of " + revenueFieldName, YesNo.Yes);
									CommonLib.ThreadSleep(3000);

									xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
											+ firstSDGColumnName
											+ "']//ancestor::td//following-sibling::td[@data-Label='" + revenueFieldName
											+ ": ']//input[@type='text']";
									ele = FindElement(driver, xPath, revenueFieldName + " Textbox", action.BOOLEAN, 50);
									if (sendKeys(driver, ele, revenueFieldValue, revenueFieldName, action.BOOLEAN)) {
										log(LogStatus.INFO,
												revenueFieldValue + " has been passed in " + revenueFieldName,
												YesNo.No);

										ThreadSleep(3000);

										xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
												+ firstSDGColumnName + "']";
										ele = FindElement(driver, xPath, legalfieldName + " Record Name",
												action.BOOLEAN, 50);
										if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
											ThreadSleep(2000);
											log(LogStatus.INFO, "Window has been scrolled to " + legalfieldName,
													YesNo.No);
											if (mouseOverOperation(driver, ele)) {
												log(LogStatus.INFO, "Mouse has been moved to " + legalfieldName,
														YesNo.No);
												xPath = "//a[text()='" + sdgGridName
														+ "']//ancestor::article//td//a[text()='" + firstSDGColumnName
														+ "']//ancestor::td//button";
												ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

												if (CommonLib.clickUsingJavaScript(driver, ele,
														"Edit icon of " + legalfieldName, action.BOOLEAN)) {
													log(LogStatus.INFO, "Clicked on the Edit icon of " + legalfieldName,
															YesNo.Yes);
													CommonLib.ThreadSleep(3000);

													xPath = "//a[text()='" + sdgGridName
															+ "']//ancestor::article//td//a[text()='"
															+ firstSDGColumnName
															+ "']//ancestor::td//input[@type='text']";
													ele = FindElement(driver, xPath, legalfieldName + " Textbox",
															action.BOOLEAN, 50);
													if (sendKeys(driver, ele, legalfieldValue, legalfieldName,
															action.BOOLEAN)) {
														log(LogStatus.INFO, legalfieldValue + " has been passed in "
																+ legalfieldName, YesNo.No);

														ThreadSleep(3000);
														xPath = "//a[text()='" + sdgGridName
																+ "']/ancestor::article//header";
														ele = FindElement(driver, xPath, "Custom App Page Heading",
																action.BOOLEAN, 50);
														CommonLib.click(driver, ele, xPath, action.SCROLLANDBOOLEAN);
														CommonLib.doubleClickUsingAction(driver, ele);
														ThreadSleep(1000);
														xPath = "//a[text()='" + sdgGridName
																+ "']/ancestor::article//span[text()='Page Size']";
														ele = FindElement(driver, xPath,
																"pagesize text of " + sdgGridName, action.BOOLEAN, 50);
														CommonLib.scrollDownThroughWebelementInCenter(driver, ele,
																"pagesize text of " + sdgGridName);
														if (CommonLib.doubleClickUsingAction(driver, ele)) {
															log(LogStatus.INFO,
																	"Clicked on the " + sdgGridName + " body",
																	YesNo.No);

															ThreadSleep(5000);
															xPath = "//a[text()='" + sdgGridName
																	+ "']//ancestor::article//button[text()='Save']";
															WebElement saveBtnElement = FindElement(driver, xPath,
																	"Save button of " + sdgGridName, action.BOOLEAN,
																	50);
															xPath = "//a[text()='" + sdgGridName
																	+ "']//ancestor::article//button[text()='Cancel']";
															WebElement cancelBtnElement = FindElement(driver, xPath,
																	"Cancel button of " + sdgGridName, action.BOOLEAN,
																	50);
															CommonLib.scrollDownThroughWebelementInCenter(driver,
																	saveBtnElement, revenueFieldName);
															if (CommonLib.checkElementVisibility(driver, saveBtnElement,
																	"Save Button", 50)) {
																log(LogStatus.PASS,
																		"Save button is visible after updating the record",
																		YesNo.No);
																sa.assertTrue(true,
																		"Save button is visible after updating the record");
															} else {
																log(LogStatus.FAIL,
																		"Save button is not visible after updating the record",
																		YesNo.No);
																sa.assertTrue(false,
																		"Save button is not visible after updating the record");
															}

															if (CommonLib.checkElementVisibility(driver,
																	cancelBtnElement, "Cancel Button", 50)) {
																log(LogStatus.PASS,
																		"Cancel button is visible after updating the record",
																		YesNo.No);
																sa.assertTrue(true,
																		"Cancel button is visible after updating the record");
															} else {
																log(LogStatus.FAIL,
																		"Cancel button is not visible after updating the record",
																		YesNo.No);
																sa.assertTrue(false,
																		"Cancel button is not visible after updating the record");
															}

															if (click(driver, saveBtnElement, "Save Button",
																	action.BOOLEAN)) {
																log(LogStatus.INFO, "Clicked on the save button",
																		YesNo.Yes);

																if (CommonLib.checkElementVisibility(driver,
																		geterrorMessageAfterSaveBlankRecord(50),
																		"Error Message", 50)) {
																	log(LogStatus.INFO,
																			"\"1 record has error. Kindly resolve them and try again.']\" Update Message is visible",
																			YesNo.Yes);
																	flag = true;

																} else {
																	log(LogStatus.ERROR,
																			"\"1 record has error. Kindly resolve them and try again.']\" Update Message is not visible",
																			YesNo.Yes);
																}

															} else {
																log(LogStatus.ERROR,
																		"Could not click on the save button",
																		YesNo.Yes);
															}

														} else {
															log(LogStatus.ERROR,
																	"Could not click on the " + sdgGridName + " body",
																	YesNo.No);
														}
													} else {
														log(LogStatus.ERROR, "Could not enter the " + legalfieldValue
																+ " in the inputox", YesNo.Yes);
													}
												}

												else {
													log(LogStatus.ERROR,
															"Could not click on the edit icon " + legalfieldName,
															YesNo.Yes);
												}

											} else {
												log(LogStatus.ERROR, "Not able to move to " + legalfieldName,
														YesNo.Yes);
											}
										} else {
											log(LogStatus.ERROR, "Window has been scrolled to " + firstSDGColumnName,
													YesNo.Yes);
										}

									}

									else {
										log(LogStatus.ERROR,
												"Could not enter the " + revenueFieldValue + " in the inputbox",
												YesNo.Yes);
									}
								}

								else {
									log(LogStatus.ERROR, "Could not click on the edit icon " + revenueFieldName,
											YesNo.Yes);
								}

							} else {
								log(LogStatus.ERROR, "Not able to move to " + revenueFieldName, YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Window has been scrolled to " + firstSDGColumnName, YesNo.Yes);
						}
					}

					else {
						log(LogStatus.ERROR, "Could not enter the " + websiteFieldName + " in the inputox", YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Could not click on the edit icon of " + websiteFieldName, YesNo.Yes);

				}
			} else {
				log(LogStatus.ERROR, "Not able to moved to " + websiteFieldName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Window has ben scrolled to " + websiteFieldName, YesNo.Yes);
		}
		return flag;

	}

	/**
	 * @author Sourabh saini
	 * @param sdgName
	 * @param recordName
	 * @return boolean
	 */

	public boolean verifyErrorMessageOnTriangleIcon(String sdgName, String recordName)

	{
		boolean flag = false;
		String xPath = "";
		WebElement ele = null;
		ThreadSleep(5000);

		xPath = "//a[text()=\"" + sdgName
				+ "\"]/ancestor::article//button[@class=\"slds-button slds-button_icon slds-button_icon-bare\" and @title=\"Help\"]";
		ele = FindElement(driver, xPath, "Error triangle icon", action.BOOLEAN, 50);
		scrollDownThroughWebelementInCenter(driver, ele, recordName + " Triangle error message");

		if (mouseOverOperation(driver, ele)) {
			log(LogStatus.INFO, "Mouse has been moved to error triangle icon", YesNo.No);

			if (checkElementVisibility(driver, gettriangleErrorIcon(50), "Error Message", 50)) {
				log(LogStatus.INFO, "Error Message is visible", YesNo.No);
				String text = CommonLib.getText(driver, gettriangleErrorIcon(50), "Triangle  error message",
						action.BOOLEAN);
				System.out.println("text " + test);
				if (text.contains("Required fields are missing: [Legal Name].")) {
					log(LogStatus.INFO, "Error message has been verified on the hover of trianlge icon", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Error message is not verified visible on the hover of trianlge icon",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Error Message is not visible", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Mouse is not moved to the triangle icon", YesNo.No);
		}

		return flag;

	}

	/**
	 * @author Sourabh saini
	 * @param sdgName
	 * @param firstSDGColumnName
	 * @param fieldName
	 * @param fieldValue
	 * @return boolean
	 */

	public boolean clickCancelBtnAndVerifyRecord(String sdgName, String firstSDGColumnName, String fieldName,
			String fieldValue) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;

		xPath = "//a[text()='" + sdgName + "']//ancestor::article//button[text()='Cancel']";
		WebElement cancelBtnElement = FindElement(driver, xPath, "Save button of " + sdgName, action.BOOLEAN, 50);
		CommonLib.scrollDownThroughWebelementInCenter(driver, cancelBtnElement, "Cancel button");

		if (click(driver, cancelBtnElement, "Cancel Button", action.BOOLEAN)) {
			log(LogStatus.INFO, "Cancel button has been clicked", YesNo.No);

			CommonLib.ThreadSleep(10000);

			xPath = "//a[text()='" + sdgName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
					+ "']//ancestor::td//following-sibling::td[@data-Label='" + fieldName + ": ']";
			ele = FindElement(driver, xPath, fieldName + " Record Name", action.BOOLEAN, 50);
			String text = CommonLib.getText(driver, ele, fieldName + " value", action.BOOLEAN);
			if (!text.equals(fieldValue)) {
				log(LogStatus.INFO, "Page has been refreash and data has been matched", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Page is not refreash and data is not matched", YesNo.No);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on the Cancel button", YesNo.No);
		}

		return flag;

	}

	/**
	 * @author Sourabh saini
	 * @param sdgGridName
	 * @param firstSDGColumnName
	 * @param phoneFieldName
	 * @param phoneFieldValue
	 * @param websiteFieldName
	 * @param websiteFieldValue
	 * @param revenueFieldName
	 * @param revenueFieldValue
	 * @return boolean
	 */

	public boolean upadatePhoneWebsiteRevenueAndVerifyErrorMessageSaveCancelButton(String sdgGridName,
			String firstSDGColumnName, String phoneFieldName, String phoneFieldValue, String websiteFieldName,
			String websiteFieldValue, String revenueFieldName, String revenueFieldValue) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;
		List<WebElement> elements = null;

		xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
				+ "']//ancestor::td//following-sibling::td[@data-Label='" + websiteFieldName + ": ']";
		ele = FindElement(driver, xPath, websiteFieldName + " Record Name", action.BOOLEAN, 50);
		if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
			ThreadSleep(2000);
			log(LogStatus.INFO, "Window has been scrolled to " + websiteFieldName, YesNo.No);
			if (mouseOverOperation(driver, ele)) {
				log(LogStatus.INFO, "Mouse has been moved to " + websiteFieldName, YesNo.No);
				xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
						+ "']//ancestor::td//following-sibling::td[@data-Label='" + websiteFieldName + ": ']//button";
				ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

				if (CommonLib.clickUsingJavaScript(driver, ele, "Edit icon of " + websiteFieldName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the Edit icon of " + websiteFieldName, YesNo.Yes);
					CommonLib.ThreadSleep(3000);

					xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
							+ "']//ancestor::td//following-sibling::td[@data-Label='" + websiteFieldName
							+ ": ']//input[@type='text']";
					ele = FindElement(driver, xPath, websiteFieldName + " Textbox", action.BOOLEAN, 50);
					if (sendKeys(driver, ele, websiteFieldValue, websiteFieldName, action.BOOLEAN)) {
						log(LogStatus.INFO, websiteFieldValue + " has been passed in " + websiteFieldName, YesNo.No);

						ThreadSleep(3000);

						xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
								+ firstSDGColumnName + "']//ancestor::td//following-sibling::td[@data-Label='"
								+ revenueFieldName + ": ']";
						ele = FindElement(driver, xPath, revenueFieldName + " Record Name", action.BOOLEAN, 50);
						if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
							ThreadSleep(2000);
							log(LogStatus.INFO, "Window has been scrolled to " + revenueFieldName, YesNo.No);
							if (mouseOverOperation(driver, ele)) {
								log(LogStatus.INFO, "Mouse has been moved to " + revenueFieldName, YesNo.No);
								xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
										+ firstSDGColumnName + "']//ancestor::td//following-sibling::td[@data-Label='"
										+ revenueFieldName + ": ']//button";
								ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

								if (CommonLib.clickUsingJavaScript(driver, ele, "Edit icon of " + revenueFieldName,
										action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on the Edit icon of " + revenueFieldName, YesNo.Yes);
									CommonLib.ThreadSleep(3000);

									xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
											+ firstSDGColumnName
											+ "']//ancestor::td//following-sibling::td[@data-Label='" + revenueFieldName
											+ ": ']//input[@type='text']";
									ele = FindElement(driver, xPath, revenueFieldName + " Textbox", action.BOOLEAN, 50);
									if (sendKeys(driver, ele, revenueFieldValue, revenueFieldName, action.BOOLEAN)) {
										log(LogStatus.INFO,
												revenueFieldValue + " has been passed in " + revenueFieldName,
												YesNo.No);

										ThreadSleep(3000);

										xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
												+ firstSDGColumnName + "']";
										ele = FindElement(driver, xPath, phoneFieldName + " Record Name",
												action.BOOLEAN, 50);
										if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
											ThreadSleep(2000);
											log(LogStatus.INFO, "Window has been scrolled to " + phoneFieldName,
													YesNo.No);
											if (mouseOverOperation(driver, ele)) {
												log(LogStatus.INFO, "Mouse has been moved to " + phoneFieldName,
														YesNo.No);
												xPath = "//a[text()='" + sdgGridName
														+ "']//ancestor::article//td//a[text()='" + firstSDGColumnName
														+ "']//ancestor::td//following-sibling::td[@data-Label='"
														+ phoneFieldName + ": ']//button";

												ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

												if (CommonLib.clickUsingJavaScript(driver, ele,
														"Edit icon of " + phoneFieldName, action.BOOLEAN)) {
													log(LogStatus.INFO, "Clicked on the Edit icon of " + phoneFieldName,
															YesNo.Yes);
													CommonLib.ThreadSleep(3000);

													xPath = "//a[text()='" + sdgGridName
															+ "']//ancestor::article//td//a[text()='"
															+ firstSDGColumnName
															+ "']//ancestor::td//following-sibling::td[@data-Label='"
															+ phoneFieldName + ": ']//input[@type='text']";

													ele = FindElement(driver, xPath, phoneFieldName + " Textbox",
															action.BOOLEAN, 50);
													if (sendKeys(driver, ele, phoneFieldValue, phoneFieldName,
															action.BOOLEAN)) {
														log(LogStatus.INFO, phoneFieldValue + " has been passed in "
																+ phoneFieldName, YesNo.No);

														ThreadSleep(3000);
														xPath = "//a[text()='" + sdgGridName
																+ "']/ancestor::article//header";
														ele = FindElement(driver, xPath, "Custom App Page Heading",
																action.BOOLEAN, 50);
														CommonLib.click(driver, ele, xPath, action.SCROLLANDBOOLEAN);
														CommonLib.doubleClickUsingAction(driver, ele);
														ThreadSleep(1000);
														xPath = "//a[text()='" + sdgGridName
																+ "']/ancestor::article//span[text()='Page Size']";
														ele = FindElement(driver, xPath,
																"pagesize text of " + sdgGridName, action.BOOLEAN, 50);
														CommonLib.scrollDownThroughWebelementInCenter(driver, ele,
																"pagesize text of " + sdgGridName);
														if (CommonLib.doubleClickUsingAction(driver, ele)) {
															log(LogStatus.INFO,
																	"Clicked on the " + sdgGridName + " body",
																	YesNo.No);

															ThreadSleep(5000);
															xPath = "//a[text()='" + sdgGridName
																	+ "']//ancestor::article//button[text()='Save']";
															WebElement saveBtnElement = FindElement(driver, xPath,
																	"Save button of " + sdgGridName, action.BOOLEAN,
																	50);
															xPath = "//a[text()='" + sdgGridName
																	+ "']//ancestor::article//button[text()='Cancel']";
															WebElement cancelBtnElement = FindElement(driver, xPath,
																	"Cancel button of " + sdgGridName, action.BOOLEAN,
																	50);
															CommonLib.scrollDownThroughWebelementInCenter(driver,
																	saveBtnElement, revenueFieldName);
															if (CommonLib.checkElementVisibility(driver, saveBtnElement,
																	"Save Button", 50)) {
																log(LogStatus.PASS,
																		"Save button is visible after updating the record",
																		YesNo.No);
																sa.assertTrue(true,
																		"Save button is visible after updating the record");
															} else {
																log(LogStatus.FAIL,
																		"Save button is not visible after updating the record",
																		YesNo.No);
																sa.assertTrue(false,
																		"Save button is not visible after updating the record");
															}

															if (CommonLib.checkElementVisibility(driver,
																	cancelBtnElement, "Cancel Button", 50)) {
																log(LogStatus.PASS,
																		"Cancel button is visible after updating the record",
																		YesNo.No);
																sa.assertTrue(true,
																		"Cancel button is visible after updating the record");
															} else {
																log(LogStatus.FAIL,
																		"Cancel button is not visible after updating the record",
																		YesNo.No);
																sa.assertTrue(false,
																		"Cancel button is not visible after updating the record");
															}

															if (click(driver, saveBtnElement, "Save Button",
																	action.BOOLEAN)) {
																log(LogStatus.INFO, "Clicked on the save button",
																		YesNo.Yes);

																if (CommonLib.checkElementVisibility(driver,
																		geterrorMessageAfterSaveBlankRecord(50),
																		"Error Message", 50)) {
																	log(LogStatus.INFO,
																			"\"1 record has error. Kindly resolve them and try again.']\" Update Message is visible",
																			YesNo.Yes);
																	flag = true;

																} else {
																	log(LogStatus.ERROR,
																			"\"1 record has error. Kindly resolve them and try again.']\" Update Message is not visible",
																			YesNo.Yes);
																}

															} else {
																log(LogStatus.ERROR,
																		"Could not click on the save button",
																		YesNo.Yes);
															}

														} else {
															log(LogStatus.ERROR,
																	"Could not click on the " + sdgGridName + " body",
																	YesNo.No);
														}
													} else {
														log(LogStatus.ERROR, "Could not enter the " + phoneFieldValue
																+ " in the inputox", YesNo.Yes);
													}
												}

												else {
													log(LogStatus.ERROR,
															"Could not click on the edit icon " + phoneFieldName,
															YesNo.Yes);
												}

											} else {
												log(LogStatus.ERROR, "Not able to move to " + phoneFieldName,
														YesNo.Yes);
											}
										} else {
											log(LogStatus.ERROR, "Window has been scrolled to " + firstSDGColumnName,
													YesNo.Yes);
										}

									}

									else {
										log(LogStatus.ERROR,
												"Could not enter the " + revenueFieldValue + " in the inputbox",
												YesNo.Yes);
									}
								}

								else {
									log(LogStatus.ERROR, "Could not click on the edit icon " + revenueFieldName,
											YesNo.Yes);
								}

							} else {
								log(LogStatus.ERROR, "Not able to move to " + revenueFieldName, YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Window has been scrolled to " + firstSDGColumnName, YesNo.Yes);
						}
					}

					else {
						log(LogStatus.ERROR, "Could not enter the " + websiteFieldValue + " in the inputox", YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Could not click on the edit icon of " + websiteFieldName, YesNo.Yes);

				}
			} else {
				log(LogStatus.ERROR, "Not able to moved to " + websiteFieldName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Window has ben scrolled to " + websiteFieldName, YesNo.Yes);
		}
		return flag;

	}

	public boolean selectCheckbox(String xPath, String sdgColumnName) {

		WebElement ele = null;
		boolean flag = false;
		ele = FindElement(driver, xPath, sdgColumnName + " checkbox button", action.BOOLEAN, 50);
		if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
			ThreadSleep(2000);
			log(LogStatus.INFO, "Window has been scrolled to " + sdgColumnName, YesNo.No);
			if (!isSelected(driver, ele, sdgColumnName + " checkbox button")) {
				if (CommonLib.clickUsingJavaScript(driver, ele, "checkbox of " + sdgColumnName, action.BOOLEAN)) {
					log(LogStatus.INFO, "checkbox has been clicked" + sdgColumnName, YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not able to click on the checkbox of " + sdgColumnName, YesNo.Yes);
				}
			} else {
				log(LogStatus.INFO, sdgColumnName + " checkbox is already selected", YesNo.Yes);
				flag = true;
			}

		} else {
			log(LogStatus.INFO, "Window is not able to scroll to " + sdgColumnName, YesNo.Yes);
		}
		return flag;

	}

	/**
	 * @author Sourabh saini
	 * @param sdgGridName
	 * @param firstSDGColumnNamerow1
	 * @param firstSDGColumnNamerow2
	 * @param phoneFieldName
	 * @param phoneFieldValue
	 * @param websiteFieldName
	 * @param websiteFieldValue
	 * @param revenueFieldName
	 * @param revenueFieldValue
	 * @return boolean
	 */

	public boolean clickTwoCheckboxupadatePhoneWebsiteRevenueAndVerifyErrorMessageSaveCancelButton(String sdgGridName,
			String firstSDGColumnNamerow1, String firstSDGColumnNamerow2, String phoneFieldName, String phoneFieldValue,
			String websiteFieldName, String websiteFieldValue, String revenueFieldName, String revenueFieldValue) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;

		xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnNamerow1
				+ "']//ancestor::tr//input";
		if (selectCheckbox(xPath, firstSDGColumnNamerow1)) {

			log(LogStatus.INFO, firstSDGColumnNamerow1 + " checkbox has been selected", YesNo.No);
			xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnNamerow2
					+ "']//ancestor::tr//input";

			if (selectCheckbox(xPath, firstSDGColumnNamerow2)) {
				log(LogStatus.INFO, firstSDGColumnNamerow2 + " checkbox has been selected", YesNo.No);
				xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnNamerow1
						+ "']//ancestor::td//following-sibling::td[@data-Label='" + websiteFieldName + ": ']";
				ele = FindElement(driver, xPath, websiteFieldName + " Record Name", action.BOOLEAN, 50);
				if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
					ThreadSleep(2000);
					log(LogStatus.INFO, "Window has been scrolled to " + websiteFieldName, YesNo.No);
					if (mouseOverOperation(driver, ele)) {
						log(LogStatus.INFO, "Mouse has been moved to " + websiteFieldName, YesNo.No);
						xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
								+ firstSDGColumnNamerow1 + "']//ancestor::td//following-sibling::td[@data-Label='"
								+ websiteFieldName + ": ']//button";
						ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

						if (CommonLib.clickUsingJavaScript(driver, ele, "Edit icon of " + websiteFieldName,
								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the Edit icon of " + websiteFieldName, YesNo.No);
							CommonLib.ThreadSleep(3000);

							xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
									+ firstSDGColumnNamerow1 + "']//ancestor::td//following-sibling::td[@data-Label='"
									+ websiteFieldName + ": ']//input[@type='text']";
							ele = FindElement(driver, xPath, websiteFieldName + " Textbox", action.BOOLEAN, 50);
							if (sendKeys(driver, ele, websiteFieldValue, websiteFieldName, action.BOOLEAN)) {
								log(LogStatus.INFO, websiteFieldValue + " has been passed in " + websiteFieldName,
										YesNo.No);

								ThreadSleep(3000);

								xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
										+ firstSDGColumnNamerow1
										+ "']//ancestor::tr//span[label[span[text()='Update 2 selected items']]]/input";
								if (selectCheckbox(xPath, firstSDGColumnNamerow1)) {
									log(LogStatus.INFO, firstSDGColumnNamerow1
											+ " Update 2 selected items checkbox has been selected", YesNo.No);
									xPath = "//a[text()='" + sdgGridName
											+ "']/ancestor::article//button[text()='Apply']";
									ele = FindElement(driver, xPath, "Checkbox button of Update 2 selected items",
											action.BOOLEAN, 50);
									if (click(driver, ele, "Checkbox button of Update 2 selected items",
											action.BOOLEAN)) {
										log(LogStatus.INFO, "Checkbox button of Update 2 selected items", YesNo.No);

										xPath = "//a[text()='" + sdgGridName
												+ "']/ancestor::article//span[text()='Page Size']";
										ele = FindElement(driver, xPath, "pagesize text of " + sdgGridName,
												action.BOOLEAN, 50);
										CommonLib.scrollDownThroughWebelementInCenter(driver, ele,
												"pagesize text of " + sdgGridName);
										if (CommonLib.doubleClickUsingAction(driver, ele)) {
											log(LogStatus.INFO, "Clicked on the " + sdgGridName + " body", YesNo.No);

											xPath = "//a[text()='" + sdgGridName
													+ "']//ancestor::article//td//a[text()='" + firstSDGColumnNamerow1
													+ "']//ancestor::td//following-sibling::td[@data-Label='"
													+ revenueFieldName + ": ']";
											ele = FindElement(driver, xPath, revenueFieldName + " Record Name",
													action.BOOLEAN, 50);
											if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
												ThreadSleep(2000);
												log(LogStatus.INFO, "Window has been scrolled to " + revenueFieldName,
														YesNo.No);
												if (mouseOverOperation(driver, ele)) {
													log(LogStatus.INFO, "Mouse has been moved to " + revenueFieldName,
															YesNo.No);
													xPath = "//a[text()='" + sdgGridName
															+ "']//ancestor::article//td//a[text()='"
															+ firstSDGColumnNamerow1
															+ "']//ancestor::td//following-sibling::td[@data-Label='"
															+ revenueFieldName + ": ']//button";
													ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

													if (CommonLib.clickUsingJavaScript(driver, ele,
															"Edit icon of " + revenueFieldName, action.BOOLEAN)) {
														log(LogStatus.INFO,
																"Clicked on the Edit icon of " + revenueFieldName,
																YesNo.Yes);
														CommonLib.ThreadSleep(3000);

														xPath = "//a[text()='" + sdgGridName
																+ "']//ancestor::article//td//a[text()='"
																+ firstSDGColumnNamerow1
																+ "']//ancestor::td//following-sibling::td[@data-Label='"
																+ revenueFieldName + ": ']//input[@type='text']";
														ele = FindElement(driver, xPath, revenueFieldName + " Textbox",
																action.BOOLEAN, 50);
														if (sendKeys(driver, ele, revenueFieldValue, revenueFieldName,
																action.BOOLEAN)) {
															log(LogStatus.INFO, revenueFieldValue
																	+ " has been passed in " + revenueFieldName,
																	YesNo.No);

															xPath = "//a[text()='" + sdgGridName
																	+ "']//ancestor::article//td//a[text()='"
																	+ firstSDGColumnNamerow1
																	+ "']//ancestor::tr//span[label[span[text()='Update 2 selected items']]]/input";
															if (selectCheckbox(xPath, firstSDGColumnNamerow1)) {
																log(LogStatus.INFO, firstSDGColumnNamerow1
																		+ " Update 2 selected items checkbox has been selected",
																		YesNo.No);
																xPath = "//a[text()='" + sdgGridName
																		+ "']/ancestor::article//button[text()='Apply']";
																ele = FindElement(driver, xPath,
																		"Checkbox button of Update 2 selected items",
																		action.BOOLEAN, 50);
																if (click(driver, ele,
																		"Checkbox button of Update 2 selected items",
																		action.BOOLEAN)) {
																	log(LogStatus.INFO,
																			"Checkbox button of Update 2 selected items",
																			YesNo.No);

																	ThreadSleep(3000);
																	xPath = "//a[text()='" + sdgGridName
																			+ "']/ancestor::article//span[text()='Page Size']";
																	ele = FindElement(driver, xPath,
																			"pagesize text of " + sdgGridName,
																			action.BOOLEAN, 50);
																	CommonLib.scrollDownThroughWebelementInCenter(
																			driver, ele,
																			"pagesize text of " + sdgGridName);
																	if (CommonLib.doubleClickUsingAction(driver, ele)) {
																		log(LogStatus.INFO, "Clicked on the "
																				+ sdgGridName + " body", YesNo.No);

																		xPath = "//a[text()='" + sdgGridName
																				+ "']//ancestor::article//td//a[text()='"
																				+ firstSDGColumnNamerow1 + "']";
																		ele = FindElement(driver, xPath,
																				phoneFieldName + " Record Name",
																				action.BOOLEAN, 50);
																		if (CommonLib
																				.scrollDownThroughWebelementInCenter(
																						driver, ele, "Element")) {
																			ThreadSleep(2000);
																			log(LogStatus.INFO,
																					"Window has been scrolled to "
																							+ phoneFieldName,
																					YesNo.No);
																			if (mouseOverOperation(driver, ele)) {
																				log(LogStatus.INFO,
																						"Mouse has been moved to "
																								+ phoneFieldName,
																						YesNo.No);
																				xPath = "//a[text()='" + sdgGridName
																						+ "']//ancestor::article//td//a[text()='"
																						+ firstSDGColumnNamerow1
																						+ "']//ancestor::td//following-sibling::td[@data-Label='"
																						+ phoneFieldName
																						+ ": ']//button";

																				ele = FindElement(driver, xPath,
																						"Value", action.BOOLEAN, 50);

																				if (CommonLib.clickUsingJavaScript(
																						driver, ele,
																						"Edit icon of "
																								+ phoneFieldName,
																						action.BOOLEAN)) {
																					log(LogStatus.INFO,
																							"Clicked on the Edit icon of "
																									+ phoneFieldName,
																							YesNo.Yes);
																					CommonLib.ThreadSleep(3000);

																					xPath = "//a[text()='" + sdgGridName
																							+ "']//ancestor::article//td//a[text()='"
																							+ firstSDGColumnNamerow1
																							+ "']//ancestor::td//following-sibling::td[@data-Label='"
																							+ phoneFieldName
																							+ ": ']//input[@type='text']";

																					ele = FindElement(driver, xPath,
																							phoneFieldName + " Textbox",
																							action.BOOLEAN, 50);
																					if (sendKeys(driver, ele,
																							phoneFieldValue,
																							phoneFieldName,
																							action.BOOLEAN)) {
																						log(LogStatus.INFO,
																								phoneFieldValue
																										+ " has been passed in "
																										+ phoneFieldName,
																								YesNo.No);

																						xPath = "//a[text()='"
																								+ sdgGridName
																								+ "']//ancestor::article//td//a[text()='"
																								+ firstSDGColumnNamerow1
																								+ "']//ancestor::tr//span[label[span[text()='Update 2 selected items']]]/input";
																						if (selectCheckbox(xPath,
																								firstSDGColumnNamerow1)) {
																							log(LogStatus.INFO,
																									firstSDGColumnNamerow1
																											+ " Update 2 selected items checkbox has been selected",
																									YesNo.No);
																							xPath = "//a[text()='"
																									+ sdgGridName
																									+ "']/ancestor::article//button[text()='Apply']";
																							ele = FindElement(driver,
																									xPath,
																									"Checkbox button of Update 2 selected items",
																									action.BOOLEAN, 50);
																							if (click(driver, ele,
																									"Checkbox button of Update 2 selected items",
																									action.BOOLEAN)) {
																								log(LogStatus.INFO,
																										"Checkbox button of Update 2 selected items",
																										YesNo.No);

																								ThreadSleep(3000);
																								xPath = "//a[text()='"
																										+ sdgGridName
																										+ "']/ancestor::article//span[text()='Page Size']";
																								ele = FindElement(
																										driver, xPath,
																										"pagesize text of "
																												+ sdgGridName,
																										action.BOOLEAN,
																										50);
																								CommonLib
																										.scrollDownThroughWebelementInCenter(
																												driver,
																												ele,
																												"pagesize text of "
																														+ sdgGridName);
																								if (CommonLib
																										.doubleClickUsingAction(
																												driver,
																												ele)) {
																									log(LogStatus.INFO,
																											"Clicked on the "
																													+ sdgGridName
																													+ " body",
																											YesNo.No);

																									ThreadSleep(5000);
																									xPath = "//a[text()='"
																											+ sdgGridName
																											+ "']//ancestor::article//button[text()='Save']";
																									WebElement saveBtnElement = FindElement(
																											driver,
																											xPath,
																											"Save button of "
																													+ sdgGridName,
																											action.BOOLEAN,
																											50);
																									xPath = "//a[text()='"
																											+ sdgGridName
																											+ "']//ancestor::article//button[text()='Cancel']";
																									WebElement cancelBtnElement = FindElement(
																											driver,
																											xPath,
																											"Cancel button of "
																													+ sdgGridName,
																											action.BOOLEAN,
																											50);
																									CommonLib
																											.scrollDownThroughWebelementInCenter(
																													driver,
																													saveBtnElement,
																													revenueFieldName);
																									if (CommonLib
																											.checkElementVisibility(
																													driver,
																													saveBtnElement,
																													"Save Button",
																													50)) {
																										log(LogStatus.PASS,
																												"Save button is visible after updating the record",
																												YesNo.No);
																										sa.assertTrue(
																												true,
																												"Save button is visible after updating the record");
																									} else {
																										log(LogStatus.FAIL,
																												"Save button is not visible after updating the record",
																												YesNo.No);
																										sa.assertTrue(
																												false,
																												"Save button is not visible after updating the record");
																									}

																									if (CommonLib
																											.checkElementVisibility(
																													driver,
																													cancelBtnElement,
																													"Cancel Button",
																													50)) {
																										log(LogStatus.PASS,
																												"Cancel button is visible after updating the record",
																												YesNo.No);
																										sa.assertTrue(
																												true,
																												"Cancel button is visible after updating the record");
																									} else {
																										log(LogStatus.FAIL,
																												"Cancel button is not visible after updating the record",
																												YesNo.No);
																										sa.assertTrue(
																												false,
																												"Cancel button is not visible after updating the record");
																									}

																									if (click(driver,
																											saveBtnElement,
																											"Save Button",
																											action.BOOLEAN)) {
																										log(LogStatus.INFO,
																												"Clicked on the save button",
																												YesNo.Yes);

																										if (CommonLib
																												.checkElementVisibility(
																														driver,
																														geterrorMessageAfterSaveTwoRecord(
																																50),
																														"Error Message",
																														50)) {
																											log(LogStatus.INFO,
																													"\"2 record has error. Kindly resolve them and try again.']\" Update Message is visible",
																													YesNo.Yes);
																											flag = true;

																										} else {
																											log(LogStatus.ERROR,
																													"\"2 record has error. Kindly resolve them and try again.']\" Update Message is not visible",
																													YesNo.Yes);
																										}

																									} else {
																										log(LogStatus.ERROR,
																												"Could not click on the save button",
																												YesNo.Yes);
																									}

																								} else {
																									log(LogStatus.ERROR,
																											"Could not click on the "
																													+ sdgGridName
																													+ " body",
																											YesNo.No);
																								}
																							} else {
																								log(LogStatus.ERROR,
																										"Could not click on the Apply button",
																										YesNo.No);
																							}
																						} else {
																							log(LogStatus.ERROR,
																									"Could not click on checkbox button of Update 2 selected items",
																									YesNo.No);
																						}
																					} else {
																						log(LogStatus.ERROR,
																								"Could not enter the "
																										+ phoneFieldValue
																										+ " in the inputox",
																								YesNo.Yes);
																					}
																				}

																				else {
																					log(LogStatus.ERROR,
																							"Could not click on the edit icon "
																									+ phoneFieldName,
																							YesNo.Yes);
																				}

																			} else {
																				log(LogStatus.ERROR,
																						"Not able to move to "
																								+ phoneFieldName,
																						YesNo.Yes);
																			}
																		} else {
																			log(LogStatus.ERROR,
																					"Window has been scrolled to "
																							+ firstSDGColumnNamerow1,
																					YesNo.Yes);
																		}

																	} else {
																		log(LogStatus.ERROR, "Could not click on the "
																				+ sdgGridName + " body", YesNo.No);
																	}
																} else {
																	log(LogStatus.ERROR,
																			"Could not click on the Apply button",
																			YesNo.No);
																}
															} else {
																log(LogStatus.ERROR,
																		"Could not click on checkbox button of Update 2 selected items",
																		YesNo.No);
															}
														} else {
															log(LogStatus.ERROR, "Could not enter the "
																	+ revenueFieldValue + " in the inputbox",
																	YesNo.Yes);
														}
													}

													else {
														log(LogStatus.ERROR,
																"Could not click on the edit icon " + revenueFieldName,
																YesNo.Yes);
													}

												} else {
													log(LogStatus.ERROR, "Not able to move to " + revenueFieldName,
															YesNo.Yes);
												}
											} else {
												log(LogStatus.ERROR,
														"Window has been scrolled to " + firstSDGColumnNamerow1,
														YesNo.Yes);
											}
										} else {
											log(LogStatus.ERROR, "Could not click on the " + sdgGridName + " body",
													YesNo.No);
										}
									} else {
										log(LogStatus.ERROR, "Could not click on the Apply button", YesNo.No);
									}
								} else {
									log(LogStatus.ERROR,
											"Could not click on checkbox button of Update 2 selected items", YesNo.No);
								}
							} else {
								log(LogStatus.ERROR, "Could not enter the " + websiteFieldValue + " in the inputox",
										YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Could not click on the edit icon of " + websiteFieldName, YesNo.Yes);

						}
					} else {
						log(LogStatus.ERROR, "Not able to moved to " + websiteFieldName, YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Window has ben scrolled to " + websiteFieldName, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, firstSDGColumnNamerow1 + " is not selected", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, firstSDGColumnNamerow2 + " is not selected", YesNo.Yes);
		}

		return flag;

	}

	public ArrayList<String> clickCancelBtnAndVerifyRecord(String sdgName, String firstSDGColumnName,
			ArrayList<String> fieldName, ArrayList<String> fieldValue) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;
		ArrayList<String> valueFromSDG = new ArrayList<String>();
		ArrayList<String> notMatchedData = new ArrayList<String>();

		xPath = "//a[text()='" + sdgName + "']//ancestor::article//button[text()='Cancel']";
		WebElement cancelBtnElement = FindElement(driver, xPath, "Save button of " + sdgName, action.BOOLEAN, 50);
		CommonLib.scrollDownThroughWebelementInCenter(driver, cancelBtnElement, "Cancel button");

		if (click(driver, cancelBtnElement, "Cancel Button", action.BOOLEAN)) {
			log(LogStatus.INFO, "Cancel button has been clicked", YesNo.No);

			CommonLib.ThreadSleep(10000);
			for (int i = 0; i < fieldName.size(); i++) {
				xPath = "//a[text()='" + sdgName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
						+ "']//ancestor::td//following-sibling::td[@data-Label='" + fieldName.get(i)
						+ ": ']//span[@class='slds-truncate']";
				ele = FindElement(driver, xPath, fieldName.get(i) + " Record Name", action.BOOLEAN, 50);
				CommonLib.scrollDownThroughWebelementInCenter(driver, ele, fieldName.get(i) + " value");
				String text = CommonLib.getText(driver, ele, fieldName.get(i) + " value", action.BOOLEAN);
				valueFromSDG.add(text);
			}

			for (int i = 0; i < fieldValue.size(); i++) {
				if (valueFromSDG.get(i).equals(fieldValue.get(i))) {
					log(LogStatus.INFO, valueFromSDG.get(i) + " has been matched with the " + fieldValue.get(i),
							YesNo.No);

				} else {
					log(LogStatus.ERROR, valueFromSDG.get(i) + " is not matched with the " + fieldValue.get(i),
							YesNo.Yes);
					notMatchedData.add(valueFromSDG.get(i) + " is not matched with the " + fieldValue.get(i));
				}
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on the Cancel button", YesNo.No);
		}

		return notMatchedData;

	}

	public ArrayList<String> verifySDGRecord(String sdgName, String firstSDGColumnName, ArrayList<String> fieldName,
			ArrayList<String> fieldValue) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;
		ArrayList<String> valueFromSDG = new ArrayList<String>();
		ArrayList<String> notMatchedData = new ArrayList<String>();

		for (int i = 0; i < fieldName.size(); i++) {
			xPath = "//a[text()='" + sdgName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
					+ "']//ancestor::td//following-sibling::td[@data-Label='" + fieldName.get(i)
					+ ": ']//span[@class='slds-truncate']";
			ele = FindElement(driver, xPath, fieldName.get(i) + " Record Name", action.BOOLEAN, 50);
			CommonLib.scrollDownThroughWebelementInCenter(driver, ele, fieldName.get(i) + " value");
			String text = CommonLib.getText(driver, ele, fieldName.get(i) + " value", action.BOOLEAN);
			valueFromSDG.add(text);
		}

		for (int i = 0; i < fieldValue.size(); i++) {
			if (valueFromSDG.get(i).equals(fieldValue.get(i))) {
				log(LogStatus.INFO, valueFromSDG.get(i) + " has been matched with the " + fieldValue.get(i), YesNo.No);

			} else {
				log(LogStatus.ERROR, valueFromSDG.get(i) + " is not matched with the " + fieldValue.get(i), YesNo.Yes);
				notMatchedData.add(valueFromSDG.get(i) + " is not matched with the " + fieldValue.get(i));
			}
		}
		return notMatchedData;

	}

	public boolean verifyErrorMessageOnTriangleIconForPermission(String sdgName, String recordName)

	{
		boolean flag = false;
		String xPath = "";
		WebElement ele = null;
		ThreadSleep(5000);

		xPath = "//a[text()=\"" + sdgName
				+ "\"]/ancestor::article//button[@class=\"slds-button slds-button_icon slds-button_icon-bare\" and @title=\"Help\"]";
		ele = FindElement(driver, xPath, "Error triangle icon", action.BOOLEAN, 50);
		scrollDownThroughWebelementInCenter(driver, ele, recordName + " Triangle error message");

		if (mouseOverOperation(driver, ele)) {
			log(LogStatus.INFO, "Mouse has been moved to error triangle icon", YesNo.No);

			if (checkElementVisibility(driver, gettriangleIconPermissionError(50), "Error Message", 50)) {
				log(LogStatus.INFO, "Error Message is visible", YesNo.No);
				String text = CommonLib.getText(driver, gettriangleIconPermissionError(50),
						"Triangle Permission error message", action.BOOLEAN);
				System.out.println("text " + test);
				if (text.contains("permission to edit")) {
					log(LogStatus.INFO, "Permission error message has been verified on the hover of trianlge icon",
							YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR,
							"Permission error message is not verified visible on the hover of trianlge icon",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Error Message is not visible", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Mouse is not moved to the triangle icon", YesNo.No);
		}

		return flag;
	}

	public boolean updatePhoneOnSDGRecordAndVerifyErrorMessage(String sdgGridName, String firstSDGColumnName,
			String phoneFieldName, String phoneFieldValue) {
		WebElement ele = null;
		boolean flag = false;
		String xPath;
		xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName + "']";
		ele = FindElement(driver, xPath, phoneFieldName + " Record Name", action.BOOLEAN, 50);
		if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
			ThreadSleep(2000);
			log(LogStatus.INFO, "Window has been scrolled to " + phoneFieldName, YesNo.No);
			if (mouseOverOperation(driver, ele)) {
				log(LogStatus.INFO, "Mouse has been moved to " + phoneFieldName, YesNo.No);
				xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
						+ "']//ancestor::td//following-sibling::td[@data-Label='" + phoneFieldName + ": ']//button";

				ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

				if (CommonLib.clickUsingJavaScript(driver, ele, "Edit icon of " + phoneFieldName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the Edit icon of " + phoneFieldName, YesNo.Yes);
					CommonLib.ThreadSleep(3000);

					xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
							+ "']//ancestor::td//following-sibling::td[@data-Label='" + phoneFieldName
							+ ": ']//input[@type='text']";

					ele = FindElement(driver, xPath, phoneFieldName + " Textbox", action.BOOLEAN, 50);
					if (sendKeys(driver, ele, phoneFieldValue, phoneFieldName, action.BOOLEAN)) {
						log(LogStatus.INFO, phoneFieldValue + " has been passed in " + phoneFieldName, YesNo.No);

						ThreadSleep(3000);
						xPath = "//a[text()='" + sdgGridName + "']/ancestor::article//span[text()='Page Size']";
						ele = FindElement(driver, xPath, "pagesize text of " + sdgGridName, action.BOOLEAN, 50);
						CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "pagesize text of " + sdgGridName);
						if (CommonLib.doubleClickUsingAction(driver, ele)) {
							log(LogStatus.INFO, "Clicked on the " + sdgGridName + " body", YesNo.No);

							ThreadSleep(5000);
							xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//button[text()='Save']";
							WebElement saveBtnElement = FindElement(driver, xPath, "Save button of " + sdgGridName,
									action.BOOLEAN, 50);
							xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//button[text()='Cancel']";
							WebElement cancelBtnElement = FindElement(driver, xPath, "Cancel button of " + sdgGridName,
									action.BOOLEAN, 50);
							CommonLib.scrollDownThroughWebelementInCenter(driver, saveBtnElement, phoneFieldName);
							if (CommonLib.checkElementVisibility(driver, saveBtnElement, "Save Button", 50)) {
								log(LogStatus.PASS, "Save button is visible after updating the record", YesNo.No);
								sa.assertTrue(true, "Save button is visible after updating the record");
							} else {
								log(LogStatus.FAIL, "Save button is not visible after updating the record", YesNo.No);
								sa.assertTrue(false, "Save button is not visible after updating the record");
							}

							if (CommonLib.checkElementVisibility(driver, cancelBtnElement, "Cancel Button", 50)) {
								log(LogStatus.PASS, "Cancel button is visible after updating the record", YesNo.No);
								sa.assertTrue(true, "Cancel button is visible after updating the record");
							} else {
								log(LogStatus.FAIL, "Cancel button is not visible after updating the record", YesNo.No);
								sa.assertTrue(false, "Cancel button is not visible after updating the record");
							}

							if (click(driver, saveBtnElement, "Save Button", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on the save button", YesNo.Yes);

								if (CommonLib.checkElementVisibility(driver, geterrorMessageAfterSaveTwoRecord(50),
										"Error Message", 50)) {
									log(LogStatus.INFO,
											"\"1 record has error. Kindly resolve them and try again.']\" Update Message is visible",
											YesNo.Yes);
									flag = true;

								} else {
									log(LogStatus.ERROR,
											"\"1 record has error. Kindly resolve them and try again.']\" Update Message is not visible",
											YesNo.Yes);
								}

							} else {
								log(LogStatus.ERROR, "Could not click on the save button", YesNo.Yes);
							}

						} else {
							log(LogStatus.ERROR, "Could not click on the " + sdgGridName + " body", YesNo.No);
						}

					} else {
						log(LogStatus.ERROR, "Could not enter the " + phoneFieldValue + " in the inputox", YesNo.Yes);
					}
				}

				else {
					log(LogStatus.ERROR, "Could not click on the edit icon " + phoneFieldName, YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to move to " + phoneFieldName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Window has been scrolled to " + phoneFieldName, YesNo.Yes);
		}
		return flag;

	}

	public boolean reloadSDG(String sdgName) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;

		xPath = "//a[text()='" + sdgName + "']//ancestor::article//button[@title='Reload.']";
		ele = FindElement(driver, xPath, sdgName + " reload button", action.BOOLEAN, 50);
		if (click(driver, ele, "Reload icon of " + sdgName, action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on the Reload icon of " + sdgName, YesNo.No);
			flag = true;
		} else {
			log(LogStatus.ERROR, "Could not click on the reload icon of " + sdgName, YesNo.Yes);
		}

		return flag;

	}

	public boolean verifyMultipleFieldWillNotEditOnDifferentPage(String sdgName, String firstSDGColumnNamerow1,
			String firstSDGColumnNamerow2, String firstSDGColumnNamerow3, String firstSDGColumnNamerow4) {
		LightningAppBuilderPageBusinessLayer appBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;

		xPath = "//a[text()='" + sdgName + "']//ancestor::article//td//a[text()='" + firstSDGColumnNamerow1
				+ "']//ancestor::tr//input";
		if (selectCheckbox(xPath, firstSDGColumnNamerow1)) {
			log(LogStatus.INFO, "checkbox has been selected against " + firstSDGColumnNamerow1, YesNo.No);

			xPath = "//a[text()='" + sdgName + "']//ancestor::article//td//a[text()='" + firstSDGColumnNamerow2
					+ "']//ancestor::tr//input";
			if (selectCheckbox(xPath, firstSDGColumnNamerow2)) {
				log(LogStatus.INFO, "checkbox has been selected against " + firstSDGColumnNamerow2, YesNo.No);

				if (appBuilder.pageSelect(sdgName, "2")) {
					log(LogStatus.INFO, "2 has been selected from the page", YesNo.No);
					CommonLib.ThreadSleep(5000);

					xPath = "//a[text()='" + sdgName + "']//ancestor::article//td//a[text()='" + firstSDGColumnNamerow3
							+ "']//ancestor::tr//input";
					if (selectCheckbox(xPath, firstSDGColumnNamerow3)) {
						log(LogStatus.INFO, "checkbox has been selected against " + firstSDGColumnNamerow3, YesNo.No);

						xPath = "//a[text()='" + sdgName + "']//ancestor::article//td//a[text()='"
								+ firstSDGColumnNamerow4 + "']//ancestor::tr//input";
						if (selectCheckbox(xPath, firstSDGColumnNamerow4)) {
							log(LogStatus.INFO, "checkbox has been selected against " + firstSDGColumnNamerow4,
									YesNo.No);

							if (appBuilder.pageSelect(sdgName, "1")) {
								log(LogStatus.INFO, "1 has been selected from the page", YesNo.No);
								CommonLib.ThreadSleep(5000);
								xPath = "//a[text()='" + sdgName + "']//ancestor::article//td//a[text()='"
										+ firstSDGColumnNamerow1 + "']//ancestor::tr//input";
								ele = FindElement(driver, xPath, "pagesize text of " + sdgName, action.BOOLEAN, 50);

								boolean val1 = CommonLib.isSelected(driver, ele, xPath);

								xPath = "//a[text()='" + sdgName + "']//ancestor::article//td//a[text()='"
										+ firstSDGColumnNamerow2 + "']//ancestor::tr//input";
								ele = FindElement(driver, xPath, "pagesize text of " + sdgName, action.BOOLEAN, 50);

								boolean val2 = CommonLib.isSelected(driver, ele, xPath);

								if (val1 == false && val2 == false) {
									log(LogStatus.INFO, "Checkbox is Unselected against the " + firstSDGColumnNamerow1
											+ " and " + firstSDGColumnNamerow2 + "", YesNo.No);
									flag = true;
								} else {
									log(LogStatus.INFO, "Checkbox is selected against the " + firstSDGColumnNamerow1
											+ " and " + firstSDGColumnNamerow2 + "", YesNo.No);

								}
							}

							else {
								log(LogStatus.INFO, "2 is not selected from the page", YesNo.No);
							}

						} else {
							log(LogStatus.ERROR, "Could not select the checkbox against the " + firstSDGColumnNamerow4,
									YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR, "Could not select the checkbox against the " + firstSDGColumnNamerow3,
								YesNo.Yes);
					}

				} else {
					log(LogStatus.INFO, "2 is not selected from the page", YesNo.No);
				}

			} else {
				log(LogStatus.ERROR, "Could not select the checkbox against the " + firstSDGColumnNamerow2, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Could not select the checkbox against the " + firstSDGColumnNamerow1, YesNo.Yes);
		}

		return flag;

	}

	/**
	 * @author Sourabh saini
	 * @param sdgGridName
	 * @param firstSDGRowName
	 * @param recordName
	 * @param value
	 * @return boolean
	 */

	public boolean VerifyValidationUpdateSDGRecordButValueshouldnotUpdate(String sdgGridName, String firstSDGRowName,
			String recordName, String value) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;
		String val1 = "";

		xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGRowName
				+ "']//ancestor::td//following-sibling::td[@data-Label='" + recordName + ": ']";
		ele = FindElement(driver, xPath, recordName + " Record Name", action.BOOLEAN, 50);
		if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
			ThreadSleep(2000);
			log(LogStatus.INFO, "Window has been scrolled to " + recordName, YesNo.No);
			if (mouseOverOperation(driver, ele)) {
				xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGRowName
						+ "']//ancestor::td//following-sibling::td[@data-Label='" + recordName
						+ ": ']//span[@class='slds-truncate']";
				ele = FindElement(driver, xPath, recordName, action.BOOLEAN, 50);
				val1 = CommonLib.getText(driver, ele, recordName, action.BOOLEAN);

				log(LogStatus.INFO, "Mouse has been moved to " + recordName, YesNo.No);
				xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGRowName
						+ "']//ancestor::td//following-sibling::td[@data-Label='" + recordName + ": ']//button";
				ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);
				if (CommonLib.clickUsingJavaScript(driver, ele, "Edit icon of " + recordName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the Edit icon of " + recordName, YesNo.Yes);
					CommonLib.ThreadSleep(3000);
					xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGRowName
							+ "']//ancestor::td//following-sibling::td[@data-Label='" + recordName
							+ ": ']//input[@type='text']";
					ele = FindElement(driver, xPath, recordName + " Textbox", action.BOOLEAN, 50);
					if (sendKeys(driver, ele, value, recordName, action.BOOLEAN)) {
						log(LogStatus.INFO, value + " has been passed in " + recordName, YesNo.No);

						ThreadSleep(3000);
						xPath = "//a[text()='" + sdgGridName + "']/ancestor::article//span[text()='Page Size']";
						ele = FindElement(driver, xPath, "pagesize text of " + sdgGridName, action.BOOLEAN, 50);
						CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "pagesize text of " + sdgGridName);
						if (CommonLib.doubleClickUsingAction(driver, ele)) {
							log(LogStatus.INFO, "Clicked on the " + sdgGridName + " body", YesNo.No);
							ThreadSleep(3000);

							xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
									+ firstSDGRowName + "']//ancestor::td//following-sibling::td[@data-Label='"
									+ recordName + ": ']//span[@class='slds-truncate']";
							ele = FindElement(driver, xPath, recordName + " Record Name", action.BOOLEAN, 50);
							CommonLib.scrollDownThroughWebelementInCenter(driver, ele, recordName + " value");
							String text = CommonLib.getText(driver, ele, recordName + " value", action.BOOLEAN);

							if (text.trim().equals("")) {
								log(LogStatus.INFO, "Value is not changed. So we are not able to enter the character",
										YesNo.No);
								flag = true;
							} else {
								log(LogStatus.ERROR, "Value has been changed. So we are able to enter the character",
										YesNo.No);

							}

						} else {
							log(LogStatus.ERROR, "Could not click on the " + sdgGridName + " body", YesNo.No);
						}
					} else {
						log(LogStatus.ERROR, "Could not enter the " + value + " in the inputox", YesNo.Yes);
					}
				}

				else {
					log(LogStatus.ERROR, "Could not click on the edit icon " + recordName, YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to move to " + recordName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Window has been scrolled to " + recordName, YesNo.Yes);
		}
		return flag;

	}

	public boolean upadateSingleRecordVerifyErrorMessageSaveCancelButton(String sdgGridName, String firstSDGColumnName,
			String recordName, String recordValue) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;
		List<WebElement> elements = null;

		xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
				+ "']//ancestor::td//following-sibling::td[@data-Label='" + recordName + ": ']";
		ele = FindElement(driver, xPath, recordName + " Record Name", action.BOOLEAN, 50);
		if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
			ThreadSleep(2000);
			log(LogStatus.INFO, "Window has been scrolled to " + recordName, YesNo.No);
			if (mouseOverOperation(driver, ele)) {
				log(LogStatus.INFO, "Mouse has been moved to " + recordName, YesNo.No);
				xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
						+ "']//ancestor::td//following-sibling::td[@data-Label='" + recordName + ": ']//button";
				ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

				if (CommonLib.clickUsingJavaScript(driver, ele, "Edit icon of " + recordName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the Edit icon of " + recordName, YesNo.Yes);
					CommonLib.ThreadSleep(3000);

					xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
							+ "']//ancestor::td//following-sibling::td[@data-Label='" + recordName
							+ ": ']//input[@type='text']";
					ele = FindElement(driver, xPath, recordName + " Textbox", action.BOOLEAN, 50);
					if (sendKeys(driver, ele, recordValue, recordName, action.BOOLEAN)) {
						log(LogStatus.INFO, recordValue + " has been passed in " + recordName, YesNo.No);
						ThreadSleep(3000);
						xPath = "//a[text()='" + sdgGridName + "']/ancestor::article//span[text()='Page Size']";
						ele = FindElement(driver, xPath, "pagesize text of " + sdgGridName, action.BOOLEAN, 50);
						CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "pagesize text of " + sdgGridName);
						if (CommonLib.doubleClickUsingAction(driver, ele)) {
							log(LogStatus.INFO, "Clicked on the " + sdgGridName + " body", YesNo.No);

							ThreadSleep(5000);
							xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//button[text()='Save']";
							WebElement saveBtnElement = FindElement(driver, xPath, "Save button of " + sdgGridName,
									action.BOOLEAN, 50);
							xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//button[text()='Cancel']";
							WebElement cancelBtnElement = FindElement(driver, xPath, "Cancel button of " + sdgGridName,
									action.BOOLEAN, 50);
							CommonLib.scrollDownThroughWebelementInCenter(driver, saveBtnElement, recordName);
							if (CommonLib.checkElementVisibility(driver, saveBtnElement, "Save Button", 50)) {
								log(LogStatus.PASS, "Save button is visible after updating the record", YesNo.No);
								sa.assertTrue(true, "Save button is visible after updating the record");
							} else {
								log(LogStatus.FAIL, "Save button is not visible after updating the record", YesNo.No);
								sa.assertTrue(false, "Save button is not visible after updating the record");
							}

							if (CommonLib.checkElementVisibility(driver, cancelBtnElement, "Cancel Button", 50)) {
								log(LogStatus.PASS, "Cancel button is visible after updating the record", YesNo.No);
								sa.assertTrue(true, "Cancel button is visible after updating the record");
							} else {
								log(LogStatus.FAIL, "Cancel button is not visible after updating the record", YesNo.No);
								sa.assertTrue(false, "Cancel button is not visible after updating the record");
							}

							if (click(driver, saveBtnElement, "Save Button", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on the save button", YesNo.Yes);

								if (CommonLib.checkElementVisibility(driver, geterrorMessageAfterSaveBlankRecord(50),
										"Error Message", 50)) {
									log(LogStatus.INFO,
											"\"1 record has error. Kindly resolve them and try again.']\" Update Message is visible",
											YesNo.Yes);
									flag = true;

								} else {
									log(LogStatus.ERROR,
											"\"1 record has error. Kindly resolve them and try again.']\" Update Message is not visible",
											YesNo.Yes);
								}

							} else {
								log(LogStatus.ERROR, "Could not click on the save button", YesNo.Yes);
							}

						}

						else {
							log(LogStatus.ERROR, "Could not click on the " + sdgGridName + " body", YesNo.No);
						}
					} else {
						log(LogStatus.ERROR, "Could not enter the " + recordValue + " in the inputbox", YesNo.Yes);
					}
				}

				else {
					log(LogStatus.ERROR, "Could not click on the edit icon " + recordName, YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to move to " + recordName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Window has been scrolled to " + firstSDGColumnName, YesNo.Yes);
		}
		return flag;

	}

	public boolean verifyErrorMessageOnTriangleIconForOutsideRange(String sdgName, String recordName)

	{
		boolean flag = false;
		String xPath = "";
		WebElement ele = null;
		ThreadSleep(5000);

		xPath = "//a[text()=\"" + sdgName
				+ "\"]/ancestor::article//button[@class=\"slds-button slds-button_icon slds-button_icon-bare\" and @title=\"Help\"]";
		ele = FindElement(driver, xPath, "Error triangle icon", action.BOOLEAN, 50);
		scrollDownThroughWebelementInCenter(driver, ele, recordName + " Triangle error message");

		if (mouseOverOperation(driver, ele)) {
			log(LogStatus.INFO, "Mouse has been moved to error triangle icon", YesNo.No);

			if (checkElementVisibility(driver, gettriangleIconOutsideRangeError(50), "Error Message", 50)) {
				log(LogStatus.INFO, "Error Message is visible", YesNo.No);
				String text = CommonLib.getText(driver, gettriangleIconOutsideRangeError(50),
						"Triangle outside range message", action.BOOLEAN);

				if (text.contains("value outside of valid range")) {
					log(LogStatus.INFO, "outside range message has been verified on the hover of trianlge icon",
							YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "outside range message is not verified visible on the hover of trianlge icon",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Error Message is not visible", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Mouse is not moved to the triangle icon", YesNo.No);
		}

		return flag;
	}

	public boolean verifyAllFieldEditAtSameTimeInWebsiteRevenue(String sdgGridName, String firstSDGColumnNamerow,
			String websiteFieldName, String websiteFieldValue, String revenueFieldName, String revenueFieldValue) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;

		xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//thead//input";

		if (selectCheckbox(xPath, sdgGridName + " checkbox")) {
			log(LogStatus.INFO, sdgGridName + " checkbox has been selected", YesNo.No);
			xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='" + firstSDGColumnNamerow
					+ "']//ancestor::td//following-sibling::td[@data-Label='" + websiteFieldName + ": ']";
			ele = FindElement(driver, xPath, websiteFieldName + " Record Name", action.BOOLEAN, 50);
			if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
				ThreadSleep(2000);
				log(LogStatus.INFO, "Window has been scrolled to " + websiteFieldName, YesNo.No);
				if (mouseOverOperation(driver, ele)) {
					log(LogStatus.INFO, "Mouse has been moved to " + websiteFieldName, YesNo.No);
					xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
							+ firstSDGColumnNamerow + "']//ancestor::td//following-sibling::td[@data-Label='"
							+ websiteFieldName + ": ']//button";
					ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

					if (CommonLib.clickUsingJavaScript(driver, ele, "Edit icon of " + websiteFieldName,
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the Edit icon of " + websiteFieldName, YesNo.No);
						CommonLib.ThreadSleep(3000);

						xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
								+ firstSDGColumnNamerow + "']//ancestor::td//following-sibling::td[@data-Label='"
								+ websiteFieldName + ": ']//input[@type='text']";
						ele = FindElement(driver, xPath, websiteFieldName + " Textbox", action.BOOLEAN, 50);
						if (sendKeys(driver, ele, websiteFieldValue, websiteFieldName, action.BOOLEAN)) {
							log(LogStatus.INFO, websiteFieldValue + " has been passed in " + websiteFieldName,
									YesNo.No);

							ThreadSleep(3000);

							xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
									+ firstSDGColumnNamerow
									+ "']//ancestor::tr//span[label[span[text()='Update 10 selected items']]]/input";
							if (selectCheckbox(xPath, firstSDGColumnNamerow)) {
								log(LogStatus.INFO,
										firstSDGColumnNamerow + " Update 10 selected items checkbox has been selected",
										YesNo.No);
								xPath = "//a[text()='" + sdgGridName + "']/ancestor::article//button[text()='Apply']";
								ele = FindElement(driver, xPath, "Checkbox button of Update 10 selected items",
										action.BOOLEAN, 50);
								if (click(driver, ele, "Checkbox button of Update 10 selected items", action.BOOLEAN)) {
									log(LogStatus.INFO, "Checkbox button of Update 10 selected items", YesNo.No);

									xPath = "//a[text()='" + sdgGridName
											+ "']/ancestor::article//span[text()='Page Size']";
									ele = FindElement(driver, xPath, "pagesize text of " + sdgGridName, action.BOOLEAN,
											50);
									CommonLib.scrollDownThroughWebelementInCenter(driver, ele,
											"pagesize text of " + sdgGridName);
									if (CommonLib.doubleClickUsingAction(driver, ele)) {
										log(LogStatus.INFO, "Clicked on the " + sdgGridName + " body", YesNo.No);

										xPath = "//a[text()='" + sdgGridName + "']//ancestor::article//td//a[text()='"
												+ firstSDGColumnNamerow
												+ "']//ancestor::td//following-sibling::td[@data-Label='"
												+ revenueFieldName + ": ']";
										ele = FindElement(driver, xPath, revenueFieldName + " Record Name",
												action.BOOLEAN, 50);
										if (CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Element")) {
											ThreadSleep(2000);
											log(LogStatus.INFO, "Window has been scrolled to " + revenueFieldName,
													YesNo.No);
											if (mouseOverOperation(driver, ele)) {
												log(LogStatus.INFO, "Mouse has been moved to " + revenueFieldName,
														YesNo.No);
												xPath = "//a[text()='" + sdgGridName
														+ "']//ancestor::article//td//a[text()='"
														+ firstSDGColumnNamerow
														+ "']//ancestor::td//following-sibling::td[@data-Label='"
														+ revenueFieldName + ": ']//button";
												ele = FindElement(driver, xPath, "Value", action.BOOLEAN, 50);

												if (CommonLib.clickUsingJavaScript(driver, ele,
														"Edit icon of " + revenueFieldName, action.BOOLEAN)) {
													log(LogStatus.INFO,
															"Clicked on the Edit icon of " + revenueFieldName,
															YesNo.Yes);
													CommonLib.ThreadSleep(3000);

													xPath = "//a[text()='" + sdgGridName
															+ "']//ancestor::article//td//a[text()='"
															+ firstSDGColumnNamerow
															+ "']//ancestor::td//following-sibling::td[@data-Label='"
															+ revenueFieldName + ": ']//input[@type='text']";
													ele = FindElement(driver, xPath, revenueFieldName + " Textbox",
															action.BOOLEAN, 50);
													if (sendKeys(driver, ele, revenueFieldValue, revenueFieldName,
															action.BOOLEAN)) {
														log(LogStatus.INFO, revenueFieldValue + " has been passed in "
																+ revenueFieldName, YesNo.No);

														xPath = "//a[text()='" + sdgGridName
																+ "']//ancestor::article//td//a[text()='"
																+ firstSDGColumnNamerow
																+ "']//ancestor::tr//span[label[span[text()='Update 10 selected items']]]/input";
														if (selectCheckbox(xPath, firstSDGColumnNamerow)) {
															log(LogStatus.INFO, firstSDGColumnNamerow
																	+ " Update 10 selected items checkbox has been selected",
																	YesNo.No);
															xPath = "//a[text()='" + sdgGridName
																	+ "']/ancestor::article//button[text()='Apply']";
															ele = FindElement(driver, xPath,
																	"Checkbox button of Update 10 selected items",
																	action.BOOLEAN, 50);
															if (click(driver, ele,
																	"Checkbox button of Update 10 selected items",
																	action.BOOLEAN)) {
																log(LogStatus.INFO,
																		"Checkbox button of Update 10 selected items",
																		YesNo.No);

																ThreadSleep(3000);
																xPath = "//a[text()='" + sdgGridName
																		+ "']/ancestor::article//span[text()='Page Size']";
																ele = FindElement(driver, xPath,
																		"pagesize text of " + sdgGridName,
																		action.BOOLEAN, 50);
																CommonLib.scrollDownThroughWebelementInCenter(driver,
																		ele, "pagesize text of " + sdgGridName);
																if (CommonLib.doubleClickUsingAction(driver, ele)) {
																	log(LogStatus.INFO,
																			"Clicked on the " + sdgGridName + " body",
																			YesNo.No);

																	ThreadSleep(5000);
																	xPath = "//a[text()='" + sdgGridName
																			+ "']//ancestor::article//button[text()='Save']";
																	WebElement saveBtnElement = FindElement(driver,
																			xPath, "Save button of " + sdgGridName,
																			action.BOOLEAN, 50);
																	xPath = "//a[text()='" + sdgGridName
																			+ "']//ancestor::article//button[text()='Cancel']";
																	WebElement cancelBtnElement = FindElement(driver,
																			xPath, "Cancel button of " + sdgGridName,
																			action.BOOLEAN, 50);
																	CommonLib.scrollDownThroughWebelementInCenter(
																			driver, saveBtnElement, revenueFieldName);
																	if (CommonLib.checkElementVisibility(driver,
																			saveBtnElement, "Save Button", 50)) {
																		log(LogStatus.PASS,
																				"Save button is visible after updating the record",
																				YesNo.No);
																		sa.assertTrue(true,
																				"Save button is visible after updating the record");
																	} else {
																		log(LogStatus.FAIL,
																				"Save button is not visible after updating the record",
																				YesNo.No);
																		sa.assertTrue(false,
																				"Save button is not visible after updating the record");
																	}

																	if (CommonLib.checkElementVisibility(driver,
																			cancelBtnElement, "Cancel Button", 50)) {
																		log(LogStatus.PASS,
																				"Cancel button is visible after updating the record",
																				YesNo.No);
																		sa.assertTrue(true,
																				"Cancel button is visible after updating the record");
																	} else {
																		log(LogStatus.FAIL,
																				"Cancel button is not visible after updating the record",
																				YesNo.No);
																		sa.assertTrue(false,
																				"Cancel button is not visible after updating the record");
																	}

																	if (click(driver, saveBtnElement, "Save Button",
																			action.BOOLEAN)) {
																		log(LogStatus.INFO,
																				"Clicked on the save button",
																				YesNo.Yes);

																		if (CommonLib.checkElementVisibility(driver,
																				getUpdateMessageAfterSaveRecord(50),
																				"Update Message", 50)) {
																			log(LogStatus.INFO,
																					"\"Your changes are saved.\" Update Message is visible",
																					YesNo.Yes);
																			flag = true;

																		} else {
																			log(LogStatus.ERROR,
																					"\"Your changes are saved.\" Update Message is not visible",
																					YesNo.Yes);
																		}

																	} else {
																		log(LogStatus.ERROR,
																				"Could not click on the save button",
																				YesNo.Yes);
																	}

																}

																else {
																	log(LogStatus.ERROR, "Could not click on the "
																			+ sdgGridName + " body", YesNo.No);
																}
															} else {
																log(LogStatus.ERROR,
																		"Could not click on the Apply button",
																		YesNo.No);
															}
														} else {
															log(LogStatus.ERROR,
																	"Could not click on checkbox button of Update 2 selected items",
																	YesNo.No);
														}
													} else {
														log(LogStatus.ERROR, "Could not enter the " + revenueFieldValue
																+ " in the inputbox", YesNo.Yes);
													}
												}

												else {
													log(LogStatus.ERROR,
															"Could not click on the edit icon " + revenueFieldName,
															YesNo.Yes);
												}

											} else {
												log(LogStatus.ERROR, "Not able to move to " + revenueFieldName,
														YesNo.Yes);
											}
										} else {
											log(LogStatus.ERROR, "Window has been scrolled to " + firstSDGColumnNamerow,
													YesNo.Yes);
										}
									} else {
										log(LogStatus.ERROR, "Could not click on the " + sdgGridName + " body",
												YesNo.No);
									}
								} else {
									log(LogStatus.ERROR, "Could not click on the Apply button", YesNo.No);
								}
							} else {
								log(LogStatus.ERROR, "Could not click on checkbox button of Update 2 selected items",
										YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "Could not enter the " + websiteFieldValue + " in the inputox",
									YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Could not click on the edit icon of " + websiteFieldName, YesNo.Yes);

					}
				} else {
					log(LogStatus.ERROR, "Not able to moved to " + websiteFieldName, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Window has ben scrolled to " + websiteFieldName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, firstSDGColumnNamerow + " is not selected", YesNo.Yes);
		}

		return flag;
	}

	public boolean sdgFilterSendDataAndFound(String SDGName, String labelName, String value, String dropdown) {
		boolean flag = false;

		String xPath = "//a[text()='" + SDGName + "']/ancestor::article//button[@title='Toggle Filters.']";
		WebElement ele = CommonLib.FindElement(driver, xPath, "Filter Icon", action.SCROLLANDBOOLEAN, 50);
		if (CommonLib.click(driver, ele, "Filter Icon", action.BOOLEAN)) {
			List<WebElement> filterLabelsList = getLabelsForFilters(SDGName);
			List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText())
					.collect(Collectors.toList()).stream().map(t -> t.trim()).collect(Collectors.toList());

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
						flag = true;

					} else {
						log(LogStatus.ERROR, "--------Not able to Pass the Value to Input Box: " + value + "--------",
								YesNo.Yes);
						sa.assertTrue(false, "--------Not able to Pass the Value to Input Box: " + value + "--------");
					}
				} else {
					log(LogStatus.ERROR, "--------Not able to Select the Value From DropDown: " + dropdown + "--------",
							YesNo.Yes);
					sa.assertTrue(false,
							"--------Not able to Select the Value From DropDown: " + dropdown + "--------");
				}
			} else {
				log(LogStatus.ERROR, "--------Filter Not available: " + value + "--------", YesNo.Yes);
				sa.assertTrue(false, "--------Filter Not available: " + value + "--------");
			}
		} else {
			log(LogStatus.ERROR, "--------Not able to click on the Filter Icon--------", YesNo.Yes);
			sa.assertTrue(false, "--------Not able to click on the Filter Icon--------");
		}

		return flag;
	}

	public boolean sdgFilterSendDataAndFound(String SDGName, String labelName, String dropdown) {
		boolean flag = false;

		String xPath = "//a[text()='" + SDGName + "']/ancestor::article//button[@title='Toggle Filters.']";
		WebElement ele = CommonLib.FindElement(driver, xPath, "Filter Icon", action.SCROLLANDBOOLEAN, 50);
		if (CommonLib.click(driver, ele, "Filter Icon", action.BOOLEAN)) {
			List<WebElement> filterLabelsList = getLabelsForFilters(SDGName);
			List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText())
					.collect(Collectors.toList()).stream().map(t -> t.trim()).collect(Collectors.toList());

			if (filterLabelsListText.contains(labelName)) {
				log(LogStatus.INFO, "--------Filter available: " + labelName + " --------", YesNo.No);
				if (CommonLib.selectVisibleTextFromDropDown(driver, selectTagForSDGFilterName(labelName, 25),
						dropdown + " Select DropDown", dropdown)) {
					log(LogStatus.INFO, "Select the Value From DropDown: " + dropdown, YesNo.No);
					CommonLib.ThreadSleep(20000);
					flag = true;

				} else {
					log(LogStatus.ERROR, "--------Not able to Select the Value From DropDown: " + dropdown + "--------",
							YesNo.Yes);
					sa.assertTrue(false,
							"--------Not able to Select the Value From DropDown: " + dropdown + "--------");
				}
			} else {
				log(LogStatus.ERROR, "--------Filter Not available: " + dropdown + "--------", YesNo.Yes);
				sa.assertTrue(false, "--------Filter Not available: " + dropdown + "--------");
			}
		} else {
			log(LogStatus.ERROR, "--------Not able to click on the Filter Icon--------", YesNo.Yes);
			sa.assertTrue(false, "--------Not able to click on the Filter Icon--------");
		}

		return flag;
	}

	public ArrayList<String> clickCancelBtnAndVerifyRecordNotMatched(String sdgName, String firstSDGColumnName,
			ArrayList<String> fieldName, ArrayList<String> fieldValue) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;
		ArrayList<String> valueFromSDG = new ArrayList<String>();
		ArrayList<String> notMatchedData = new ArrayList<String>();

		xPath = "//a[text()='" + sdgName + "']//ancestor::article//button[text()='Cancel']";
		WebElement cancelBtnElement = FindElement(driver, xPath, "Save button of " + sdgName, action.BOOLEAN, 50);
		CommonLib.scrollDownThroughWebelementInCenter(driver, cancelBtnElement, "Cancel button");

		if (click(driver, cancelBtnElement, "Cancel Button", action.BOOLEAN)) {
			log(LogStatus.INFO, "Cancel button has been clicked", YesNo.No);

			CommonLib.ThreadSleep(10000);
			for (int i = 0; i < fieldName.size(); i++) {
				xPath = "//a[text()='" + sdgName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
						+ "']//ancestor::td//following-sibling::td[@data-Label='" + fieldName.get(i)
						+ ": ']//span[@class='slds-truncate']";
				ele = FindElement(driver, xPath, fieldName.get(i) + " Record Name", action.BOOLEAN, 50);
				CommonLib.scrollDownThroughWebelementInCenter(driver, ele, fieldName.get(i) + " value");
				String text = CommonLib.getText(driver, ele, fieldName.get(i) + " value", action.BOOLEAN);
				valueFromSDG.add(text);
			}

			for (int i = 0; i < fieldValue.size(); i++) {
				if (!valueFromSDG.get(i).equals(fieldValue.get(i))) {
					log(LogStatus.INFO, valueFromSDG.get(i) + " is not matched with the " + fieldValue.get(i),
							YesNo.No);

				} else {
					log(LogStatus.ERROR, valueFromSDG.get(i) + " is matched with the " + fieldValue.get(i), YesNo.Yes);
					notMatchedData.add(valueFromSDG.get(i) + " is matched with the " + fieldValue.get(i));
				}
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on the Cancel button", YesNo.No);
		}

		return notMatchedData;

	}

	public ArrayList<String> VerifyRecordNotMatchedOnSDG(String sdgName, String firstSDGColumnName,
			ArrayList<String> fieldName, ArrayList<String> fieldValue) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;
		ArrayList<String> valueFromSDG = new ArrayList<String>();
		ArrayList<String> notMatchedData = new ArrayList<String>();

		CommonLib.ThreadSleep(10000);
		for (int i = 0; i < fieldName.size(); i++) {
			xPath = "//a[text()='" + sdgName + "']//ancestor::article//td//a[text()='" + firstSDGColumnName
					+ "']//ancestor::td//following-sibling::td[@data-Label='" + fieldName.get(i)
					+ ": ']//span[@class='slds-truncate']";
			ele = FindElement(driver, xPath, fieldName.get(i) + " Record Name", action.BOOLEAN, 50);
			CommonLib.scrollDownThroughWebelementInCenter(driver, ele, fieldName.get(i) + " value");
			String text = CommonLib.getText(driver, ele, fieldName.get(i) + " value", action.BOOLEAN);
			valueFromSDG.add(text);
		}

		for (int i = 0; i < fieldValue.size(); i++) {
			if (!valueFromSDG.get(i).equals(fieldValue.get(i))) {
				log(LogStatus.INFO, valueFromSDG.get(i) + " is not matched with the " + fieldValue.get(i), YesNo.No);

			} else {
				log(LogStatus.ERROR, valueFromSDG.get(i) + " is matched with the " + fieldValue.get(i), YesNo.Yes);
				notMatchedData.add(valueFromSDG.get(i) + " is matched with the " + fieldValue.get(i));
			}
		}

		return notMatchedData;

	}

	public boolean verifyFilter(String sdgName, String filterLabelName, String filerOptionValue) {
		boolean flag = false;
		String xPath = "//a[text()='" + sdgName + "']/ancestor::article//button[@title='Toggle Filters.']";
		WebElement ele = CommonLib.FindElement(driver, xPath, "Filter Icon", action.SCROLLANDBOOLEAN, 50);
		if (CommonLib.click(driver, ele, "Filter Icon", action.BOOLEAN)) {
			List<WebElement> filterLabelsList = getLabelsForFilters(sdgName);
			List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText())
					.collect(Collectors.toList()).stream().map(t -> t.trim()).collect(Collectors.toList());

			if (filterLabelsListText.contains(filterLabelName)) {
				log(LogStatus.INFO, "--------Filter available: " + filterLabelName + " --------", YesNo.No);

				String text = CommonLib.getAttribute(driver, inputBoxForSDGFilterName(filterLabelName, 20),
						filterLabelName + " value", "value");
				if (text.equals(filerOptionValue)) {
					log(LogStatus.INFO, "--------Filter available: " + filterLabelName + " --------", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "--------Filter is not available: " + filterLabelName + " --------",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "--------Filter Not available: " + filterLabelName + "--------", YesNo.Yes);
				sa.assertTrue(false, "--------Filter Not available: " + filterLabelName + "--------");
			}
		} else {
			log(LogStatus.ERROR, "--------Not able to click on the Filter Icon--------", YesNo.Yes);
			sa.assertTrue(false, "--------Not able to click on the Filter Icon--------");
		}

		return flag;

	}

}
