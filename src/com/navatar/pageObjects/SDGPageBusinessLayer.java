package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import java.util.List;
import java.util.Random;

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
		if (SDGCreationLabel.Filter.toString().equals(fieldLabel) || SDGCreationLabel.My_Records.toString().equals(fieldLabel)) {
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
												appLog.error("Error Msg not Matched: " + errorMsg);
												log(LogStatus.FAIL, "Error Msg not Matched:" + errorMsg, YesNo.Yes);
												flag = false;

											}

										} else {
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
												log(LogStatus.SKIP, "Header not verified for created  " + sdgName,
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

					WebElement sdgNameAfterSearchEle = FindElement(driver, "//th//a[text()='" + sdgName + "']",
							"SDG Name After Search ", action.SCROLLANDBOOLEAN, 20);
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

}
