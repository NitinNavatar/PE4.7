package com.navatar.pageObjects;

import org.apache.poi.hslf.record.RecordTypes;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.DoubleClickAction;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import com.navatar.generic.AppListeners;
import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.Condition;
import com.navatar.generic.EnumConstants.ContactPagePhotoActions;
import com.navatar.generic.EnumConstants.LookUpIcon;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PermissionType;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.navatar.generic.AppListeners.*;
import static com.navatar.generic.BaseLib.sa;

public class SetupPageBusinessLayer extends SetupPage {
	// Scanner scn = new Scanner(System.in);
	public SetupPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Akul Bhutani
	 * @param environment
	 * @param mode
	 * @param objectName
	 * @return true/false
	 * @description this method is used to search object on setup page by entering
	 *              on textbox and click
	 */
	public boolean searchStandardOrCustomObject(String environment, String mode, object objectName) {
		String index = "[1]";
		String o = objectName.toString().replaceAll("_", " ");
		if (objectName == object.Global_Actions || objectName == object.Activity_Setting
				|| objectName == object.App_Manager || objectName == object.Lightning_App_Builder
				|| objectName == object.Profiles || objectName == object.Override || objectName == object.Tabs
				|| objectName == object.Users || objectName == object.Sharing_Settings
				|| objectName == object.Rename_Tabs_And_Labels || objectName == object.Custom_Metadata_Types) {
			if (objectName == object.Global_Actions || objectName == object.Tabs || objectName == object.Users) {
				index = "[2]";
			}
			ThreadSleep(3000);
			clickUsingJavaScript(driver, FindElement(driver, "//a[text()='Home' or @title='Home']",
					"home tsb link in setup", action.BOOLEAN, 10), "", action.BOOLEAN);
			if (sendKeys(driver, getQucikSearchInSetupPage(10), o, o, action.BOOLEAN)) {

				ThreadSleep(2000);
				if (clickUsingJavaScript(driver,
						FindElement(driver, "(//mark[text()='" + o + "'])" + index + "/parent::a",
								objectName.toString(), action.BOOLEAN, 10),
						objectName.toString(), action.BOOLEAN)) {
					return true;
				} else {
					log(LogStatus.ERROR, "could not click on " + objectName, YesNo.Yes);

				}
			} else {
				log(LogStatus.ERROR, "quick search textbox not visible", YesNo.Yes);
			}
			return false;
		}
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			if (sendKeys(driver, getQucikSearchInSetupPage(30), objectName.toString(),
					"quick search text box in setup page", action.SCROLLANDBOOLEAN)) {
				appLog.info("passed value in serach text box: " + objectName);
				return true;
			} else {
				appLog.error("Not able to search object in classic : " + objectName);
			}
		} else {
			ThreadSleep(10000);
			if (objectName == object.Create) {
				String xpath = "//a[@title='Create Menu']/span[text()='" + objectName.toString() + "']";
				if (click(driver, FindElement(driver, xpath, objectName.toString(), action.BOOLEAN, 10),
						"create Custom Object", action.BOOLEAN)) {
					appLog.info("clicked on " + objectName);
					ThreadSleep(2000);
					xpath = "//a[@title='Custom Object']//span[text()='Custom Object']";
					if (click(driver, FindElement(driver, xpath, "create Custom Object", action.BOOLEAN, 10),
							"create Custom Object", action.BOOLEAN)) {
						appLog.info("clicked on custom object");
						return true;
					} else {
						appLog.error("Not able to click on custom object link");
					}

				} else {
					appLog.error("Not able to click on create icon");
				}
			} else if (click(driver, getObjectManager_Lighting(30), "object manager tab", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on object manager tab");
				if (objectName == object.Custom_Object) {
					if (sendKeys(driver, getQuickSearchInObjectManager_Lighting(30), tabCustomObj,
							"quick search text box in lighting", action.SCROLLANDBOOLEAN)) {
						appLog.info("passed value in quick search text box: " + tabCustomObj);
						return true;
					} else {
						appLog.error("Not able to search object in lighting : " + tabCustomObj);
					}
				}
				if (sendKeys(driver, getQuickSearchInObjectManager_Lighting(30), objectName.toString(),
						"quick search text box in lighting", action.SCROLLANDBOOLEAN)) {
					appLog.info("passed value in quick search text box: " + objectName.toString());
					return true;
				} else {
					appLog.error("Not able to search object in lighting : " + objectName.toString());
				}
			} else {
				appLog.error(
						"Not able to click on object manager tab so cannot search object: " + objectName.toString());
			}
		}
		return false;
	}

	public WebElement VFPagePreviewLink(String page, int timeout) {
		String xpath = "//a[text()='" + page + "']/../preceding-sibling::td//img[contains(@title,'Preview')]/..";

		WebElement ele = FindElement(driver, xpath, "vfpage preview link", action.SCROLLANDBOOLEAN, timeout);
		scrollDownThroughWebelement(driver, ele, "vfpage preview link");
		ThreadSleep(2000);
		return isDisplayed(driver, ele, "visibility", 10, "vfpage preview link");

	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param labelWithValue
	 * @param timeOut
	 * @return true if successfully created navigation item
	 */

	/**
	 * @author Akul Bhutani
	 * @param environment
	 * @param mode
	 * @param object
	 * @param objectfeatureName
	 * @return true/false
	 * @description this method is used to click on object name on setup page
	 */
	public boolean clickOnObjectFeature(String environment, String mode, object object,
			ObjectFeatureName objectFeatureName) {
		WebElement ele = null;
		if (object == object.Global_Actions) {
			return true;
		}

		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			ele = isDisplayed(driver,
					FindElement(driver, "//a[text()='" + object + "']/../div/div/a[text()='" + objectFeatureName + "']",
							"", action.BOOLEAN, 20),
					"visibility", 20, "page layout link");
			if (ele != null) {
				if (click(driver, ele, objectFeatureName + " link", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on " + object + " object feature : " + objectFeatureName);
					return true;
				} else {
					appLog.error("Not able to click on " + object + " object feature: " + objectFeatureName);
				}
			} else {
				appLog.error(object + " object " + objectFeatureName + " feature is not visible so cannot click on it");
			}
		} else {
			ele = isDisplayed(driver,
					FindElement(driver,
							"//table[@data-aura-class='uiVirtualDataGrid--default uiVirtualDataGrid']//a[text()='"
									+ object + "']",
							"", action.BOOLEAN, 20),
					"visibility", 20, "page layout link");
			if (ele != null) {
				if (click(driver, ele, object + " object link", action.SCROLLANDBOOLEAN)) {
					appLog.info("click on object link : " + object);
					ele = isDisplayed(driver, FindElement(driver, "//a[contains(text(),'" + objectFeatureName + "')]",
							"", action.BOOLEAN, 20), "visibility", 20, objectFeatureName + " feature link");
					if (ele != null) {
						if (click(driver, ele, objectFeatureName + " object feature link", action.SCROLLANDBOOLEAN)) {
							return true;
						} else {
							appLog.error("Not able to click on object " + object + " feature " + objectFeatureName);
						}
					} else {
						appLog.error(object + " object feature " + objectFeatureName
								+ " is not visible so cannot click on it");
					}
				} else {
					appLog.error("Not able to click on object link : " + object + " so cannot click on it's feature: "
							+ objectFeatureName);
				}
			} else {
				appLog.error(
						object + " object link is not visible so cannot click on it's feature : " + objectFeatureName);
				ele = isDisplayed(driver, FindElement(driver, "//a[contains(text(),'" + objectFeatureName + "')]", "",
						action.BOOLEAN, 20), "visibility", 20, objectFeatureName + " feature link");
				if (ele != null) {
					if (click(driver, ele, objectFeatureName + " object feature link", action.SCROLLANDBOOLEAN)) {
						return true;
					} else {
						appLog.error("Not able to click on object " + object + " feature " + objectFeatureName);
					}
				} else {
					appLog.error(
							object + " object feature " + objectFeatureName + " is not visible so cannot click on it");
				}
			}
		}
		return false;

	}

	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @param object
	 * @param objectFeatureName
	 * @param layoutName
	 * @param sourceANDDestination
	 * @return List<String>
	 * @description this method is used to drag and drop fields on page layout page
	 */
	public List<String> DragNDrop(String environment, String mode, object obj, ObjectFeatureName objectFeatureName,
			List<String> layoutName, HashMap<String, String> sourceANDDestination) {
		WebElement ele = null;
		List<String> result = new ArrayList<String>();
		boolean flag = false;
		if (searchStandardOrCustomObject(environment, mode, obj)) {
			if (clickOnObjectFeature(environment, mode, obj, objectFeatureName)) {
				for (int i = 0; i < layoutName.size(); i++) {
					if (obj == object.Global_Actions) {
						switchToFrame(driver, 10, getEditPageLayoutFrame_Lighting(20));
						ele = isDisplayed(driver,
								FindElement(driver,
										"//a[text()='" + layoutName.get(i)
												+ "']/../preceding-sibling::td//a[contains(@title,'Layout')]",
										"", action.BOOLEAN, 20),
								"visibility", 20, obj + " page layout link");
					} else {
						if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
							ele = isDisplayed(driver,
									FindElement(driver,
											"//div[@id='LayoutList_body']//tr/th[text()='" + layoutName.get(i)
													+ "']/../td/a[contains(@title,'Edit')]",
											"", action.BOOLEAN, 20),
									"visibility", 20, layoutName.get(i) + " page layout link");
						} else {
							ele = isDisplayed(driver,
									FindElement(driver, "//span[contains(text(),'" + layoutName.get(i) + "')]", "",
											action.BOOLEAN, 20),
									"visibility", 20, layoutName.get(i) + " page layout link");
						}
					}
					if (ele != null) {
						if (click(driver, ele, layoutName.get(i) + " layout name edit icon", action.BOOLEAN)) {
							appLog.info("click on pagelayout " + layoutName.get(i) + " Edit Icon");
							ThreadSleep(20000);
							if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
							}

							Set<String> Sources = sourceANDDestination.keySet();
							Iterator<String> itr = Sources.iterator();
							while (itr.hasNext()) {
								String src = itr.next();
								String trgt = sourceANDDestination.get(src);
								if (PageLabel.Is_Touchpoint.toString().equalsIgnoreCase(src)) {

								}

								else {
									src = src.replace("_", " ");
								}

								if (PageLabel.Is_Touchpoint.toString().equalsIgnoreCase(trgt)) {

								} else {
									trgt = trgt.replace("_", " ");
								}

								// src=src.replace("_", " ");
								// trgt=trgt.replace("_", " ");

								WebElement targetElement = null;
								if (src.split("<break>")[0].contains("Related List")) {
									if (click(driver, FindElement(driver, "//div[text()='Related Lists']", "",
											action.SCROLLANDBOOLEAN, 30), "", action.SCROLLANDBOOLEAN)) {
										if (trgt.split("<break>")[0].equalsIgnoreCase("Above")) {
											trgt = trgt.split("<break>")[trgt.split("<break>").length - 1];
											targetElement = FindElement(driver,
													"//h3[text()='" + trgt
															+ "']/../../../../../../../../preceding-sibling::div[1]",
													"", action.BOOLEAN, 20);
										} else {
											trgt = trgt.split("<break>")[trgt.split("<break>").length - 1];
											targetElement = FindElement(driver,
													"//h3[text()='" + trgt
															+ "']/../../../../../../../../following-sibling::div[1]",
													"", action.BOOLEAN, 20);
										}
										src = src.split("<break>")[src.split("<break>").length - 1];
									} else {
										appLog.error(src + " is not visible so cannot dragNdrop " + src);
										result.add(src + " is not visible so cannot dragNdrop " + src);
									}
									flag = true;
								} else if (src.split("<break>")[0].contains("Mobile")) {
									if (click(driver, FindElement(driver, "//div[text()='Mobile & Lightning Actions']",
											"", action.SCROLLANDBOOLEAN, 30), "", action.SCROLLANDBOOLEAN)) {
										src = src.split("<break>")[1];
										sendKeys(driver, getquickFindSearch(10), src, src, action.BOOLEAN);
										targetElement = FindElement(driver,
												"//div[contains(@id,'item_QuickAction')][text()='" + trgt + "']", "",
												action.BOOLEAN, 20);
										flag = true;
									}
								}

								else {
									sendKeys(driver, getquickFindSearch(10), src, src, action.BOOLEAN);
									targetElement = FindElement(driver,
											"//span[@class='labelText'][text()='" + trgt + "']", "", action.BOOLEAN,
											20);

								}
								ele = isDisplayed(driver,
										FindElement(driver, " //span[text()='" + src + "']", "", action.BOOLEAN, 20),
										"visibility", 20, src + " field");
								if (ele != null) {
								}

								else
									ele = isDisplayed(driver, FindElement(driver,
											"(//table[@class='troughItems ']//div/div)[3]", "", action.BOOLEAN, 20),
											"visibility", 20, src + " field");

								if (ele != null) {
									WebElement ele1 = isDisplayed(driver, targetElement, "visibility", 20,
											trgt + " field");

									ThreadSleep(5000);
									if (ele1 != null) {
										if (dragNDropField(driver, ele, ele1)) {
											ThreadSleep(5000);
											appLog.info("Successfully dragNDrop " + src + " at " + trgt + " location");
											if (src.equalsIgnoreCase(PageLabel.Convert_to_Portfolio.toString())) {
												if (FindElement(driver,
														"//div[contains(@id,'QuickAction')][text()='" + src + "']", "",
														action.BOOLEAN, 20) != null) {
													appLog.info("successfully verified drag and drop of " + src);
												} else {
													appLog.error("Not able to dragNDrop " + src + " at " + trgt
															+ " location");
													result.add("Not able to dragNDrop " + src + " at " + trgt
															+ " location");
												}

											} else {
												if (FindElement(driver,
														"//span[@class='labelText'][text()='" + src + "']", "",
														action.BOOLEAN, 20) != null) {
													appLog.info("successfully verified drag and drop of " + src);
												} else {
													appLog.error("Not able to dragNDrop " + src + " at " + trgt
															+ " location");
													result.add("Not able to dragNDrop " + src + " at " + trgt
															+ " location");
												}
											}
											appLog.info("Successfully dragNDrop " + src + " at " + trgt + " location");
										} else {
											appLog.error("Not able to dragNDrop " + src + " at " + trgt + " location");
											result.add("Not able to dragNDrop " + src + " at " + trgt + " location");
										}
									} else {
										appLog.error(trgt + " location is not visible so cannot dragNDrop " + src
												+ " at location " + trgt);
										result.add(trgt + " location is not visible so cannot dragNDrop " + src
												+ " at location " + trgt);
									}
								} else {
									appLog.error(src + " is not visible so cannot dragNdrop " + src);
									result.add(src + " is not visible so cannot dragNdrop " + src);
								}

							}

							if (click(driver, getPageLayoutSaveBtn(obj, 30), "page layouts save button",
									action.SCROLLANDBOOLEAN)) {
								appLog.info("clicked on save button");

								if (flag && obj != object.Global_Actions) {
									ThreadSleep(5000);
									click(driver, FindElement(driver, "//button[text()='Yes']", "Yes Button",
											action.BOOLEAN, 30), "", action.SCROLLANDBOOLEAN);

								}
							} else {
								appLog.error(
										"Not able to click on Save button cannot save pagelayout dragged object or section");
								result.add(
										"Not able to click on Save button cannot save pagelayout dragged object or section");
							}
						} else {
							appLog.error("Not able to click on " + layoutName.get(i)
									+ "layout edit icon so cannot dargNdrop.");
							result.add("Not able to click on " + layoutName.get(i)
									+ "layout edit icon so cannot dargNdrop.");
						}

					} else {
						appLog.error(layoutName.get(i) + " Layout name is not visible so cannot click on edit icon");
						result.add(layoutName.get(i) + " Layout name is not visible so cannot click on edit icon");
					}
				}
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					ThreadSleep(5000);
					switchToDefaultContent(driver);

				}
			} else {
				appLog.error(
						"Not able to click on Object feature: " + objectFeatureName + " so cannot dragNdrop source.");
				result.add(
						"Not able to click on Object feature: " + objectFeatureName + " so cannot dragNdrop source.");
			}
		} else {
			appLog.error("Not able to search Object: " + obj + " so cannot dragNdrop source.");
			result.add("Not able to search Object: " + obj + " so cannot dragNdrop source.");
		}

		return result;
	}

	/*******************************************************
	 * Activity Association
	 ******************************/
	/**
	 * @author Azhar Alam
	 * @param userfirstname
	 * @param userlastname
	 * @param email
	 * @param userLicense
	 * @param userProfile
	 * @param title         TODO
	 * @return true/false
	 * @description this method is used to create new user in pe or mna
	 */
	public boolean createPEUser(String userfirstname, String userlastname, String email, String userLicense,
			String userProfile, String title) {
		if (click(driver, getExpandUserIcon(30), "expand User Icon", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on user expand icon");
			if (click(driver, getUsersLink(30), "User Link", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on users link");
				switchToFrame(driver, 20, getSetUpPageIframe(20));
				if (click(driver, getNewUserLink(20), "New User Button", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on new users button");
					switchToDefaultContent(driver);
					switchToFrame(driver, 20, getSetUpPageIframe(20));
					if (sendKeys(driver, getUserFirstName(60), userfirstname, "User First Name",
							action.SCROLLANDBOOLEAN)) {
						if (sendKeys(driver, getUserLastName(60), userlastname, "User Last Name",
								action.SCROLLANDBOOLEAN)) {
							if (sendKeys(driver, getUserEmailId(60), email, "User Email Id", action.SCROLLANDBOOLEAN)) {
								if (selectVisibleTextFromDropDown(driver, getUserUserLicenseDropDownList(60),
										"User License drop down list", userLicense)) {
									appLog.info("select user license from drop downlist: " + userLicense);
									if (selectVisibleTextFromDropDown(driver, getUserProfileDropDownList(60),
											"User profile drop down list", userProfile)) {
										appLog.info("select user profile from drop downlist: " + userProfile);
										if (click(driver, getSalesforceCRMContentUserCheckBox(60),
												"Salesforce CRM Content User check Box", action.SCROLLANDBOOLEAN)) {
											ThreadSleep(2000);

											if (title != null && title != "") {
												if (sendKeys(driver, getUserTitle(20), title, "User title",
														action.SCROLLANDBOOLEAN)) {
													appLog.info(title + " value has been passed in user title");

												} else {
													appLog.error(title + " value is not passed in user title");
												}
											}
											ThreadSleep(2000);
											if (clickUsingJavaScript(driver, getCreateUserSaveBtn_Lighting(30),
													"Save Button", action.SCROLLANDBOOLEAN)) {
												appLog.info("clicked on save button");
												appLog.info("CRM User is created successfully: " + userfirstname + " "
														+ userlastname);
												return true;
											} else {
												appLog.error("Not able to click on save buttton so cannot create user: "
														+ userfirstname + " " + userlastname);
											}

										} else {
											appLog.info("Not able to click on content user checkbox");
										}
									} else {
										appLog.error("Not able to select profile from drop downlist: " + userProfile
												+ " so cannot create user: " + userfirstname + " " + userlastname);
									}

								} else {
									appLog.error("Not able to select user license from drop downlist: " + userLicense
											+ " so cannot create user: " + userfirstname + " " + userlastname);
								}

							} else {
								appLog.error("Not able to pass email id in text box: " + email
										+ " so cannot create user: " + userfirstname + " " + userlastname);
							}

						} else {
							appLog.error("Not able to pass user last name in text box: " + userlastname
									+ " so cannot create user: " + userfirstname + " " + userlastname);
						}
					} else {
						appLog.error("Not able pass user first name in text box: " + userfirstname
								+ " so cannot create user: " + userfirstname + " " + userlastname);
					}

				} else {
					appLog.error("Not able to click on new user button so cannot create user: " + userfirstname + " "
							+ userlastname);
				}

			} else {
				appLog.error(
						"Not able to click on users link so cannot create user: " + userfirstname + " " + userlastname);
			}

		} else {
			appLog.error("Not able to click on manage user expand icon so cannot create user: " + userfirstname + " "
					+ userlastname);
		}
		switchToDefaultContent(driver);
		return false;
	}

	/**
	 * @author Akul Bhutani
	 * @param firstName
	 * @param lastName
	 * @return true/false
	 * @description this method is used to add PE package to user name in arguments
	 */
	public boolean installedPackages(String firstName, String lastName) {
		if (sendKeys(driver, getQucikSearchInSetupPage(30), "Installed package", "quick search text box in setup page",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "passed value in serach text box installed package ", YesNo.No);

		} else {
			log(LogStatus.INFO,
					"Not able to search installed package in search text box so cannot click on installed package link in lightning",
					YesNo.Yes);
			return false;
		}
		if (click(driver, getInstalledPackageLink(30), "Installed Package link", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on installed package link", YesNo.No);
			switchToFrame(driver, 30, getSetUpPageIframe(30));
			if (click(driver, getManageLicensesLink(60), "Manage licenses link", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on manage licenses link", YesNo.No);
				switchToDefaultContent(driver);
				ThreadSleep(10000);
				switchToFrame(driver, 30, getSetUpPageIframe(30));
				ThreadSleep(5000);
				if (clickUsingJavaScript(driver, getAddUsersbutton(60), "Add Users link", action.BOOLEAN)) {
					log(LogStatus.INFO, "clicked on add users button", YesNo.No);
					switchToDefaultContent(driver);
					switchToFrame(driver, 30, getInstalledPackageParentFrame_Lighting(20));
					if (switchToFrame(driver, 30, getInstalledPackageFrame(20))) {
						for (int i = 0; i < 2; i++) {
							if (click(driver, getActiveUserTab(60), "Active Users Tab", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on active user tab ", YesNo.No);
							} else {
								if (i == 1) {
									switchToDefaultContent(driver);
									log(LogStatus.INFO, "Not able to click on active user tab", YesNo.Yes);
								}
							}
						}
						WebElement ele = FindElement(
								driver, "//img[@title='Checked']/../..//span[contains(text(),'" + lastName + ", "
										+ firstName + "')]/../..//input",
								"Activate User Check Box", action.BOOLEAN, 20);
						if (ele != null) {
							for (int i = 0; i < 2; i++) {
								if (click(driver, ele, firstName + " " + lastName + " check box", action.BOOLEAN)) {
									ThreadSleep(2000);
									WebElement checkBox = FindElement(driver,
											"//img[@title='Checked']/../..//span[contains(text(),'" + lastName + ", "
													+ firstName + "')]/../..//input",
											"Activate User Check Box", action.BOOLEAN, 20);
									if (isSelected(driver, checkBox, firstName + " " + lastName + " check box")) {
										log(LogStatus.INFO, "clicked on user check box: " + firstName + " " + lastName,
												YesNo.No);
										switchToDefaultContent(driver);
										switchToFrame(driver, 30, getInstalledPackageParentFrame_Lighting(20));
										if (click(driver, getActiveUserAddButton(60), "Active User Add Button",
												action.BOOLEAN)) {
											log(LogStatus.INFO, "clicked on add button", YesNo.No);
											log(LogStatus.INFO,
													"package is installed successfully: " + firstName + " " + lastName,
													YesNo.No);
											CommonLib.ThreadSleep(5000);
											switchToDefaultContent(driver);
											return true;

										} else {
											switchToDefaultContent(driver);
											log(LogStatus.INFO,
													"Not able to click on add button so cannot install user package: "
															+ firstName + " " + lastName,
													YesNo.Yes);
										}
									} else {
										if (i == 1) {
											switchToDefaultContent(driver);
											log(LogStatus.INFO,
													"username checkbox is not selected in istalled package : "
															+ firstName + " " + lastName,
													YesNo.Yes);
										}
									}
								} else {
									if (i == 1) {
										switchToDefaultContent(driver);
										log(LogStatus.INFO,
												"Not able to click on user check box: " + firstName + " " + lastName,
												YesNo.Yes);
									}
								}
							}
						} else {
							switchToDefaultContent(driver);
							log(LogStatus.INFO, "create user " + firstName + " " + lastName
									+ " is not visible so cannot istall user package: " + firstName + " " + lastName,
									YesNo.Yes);
						}
					} else {
						switchToDefaultContent(driver);
						log(LogStatus.INFO, "installed package frame is not loaded so cannot install user package: "
								+ firstName + " " + lastName, YesNo.Yes);
					}
				} else {
					log(LogStatus.INFO, "Not able to click on add users button so cannot install user package: "
							+ firstName + " " + lastName, YesNo.Yes);
					switchToDefaultContent(driver);
				}
			} else {
				log(LogStatus.INFO, "Not able to click on manage licenses link so cannot install user package: "
						+ firstName + " " + lastName, YesNo.Yes);
				switchToDefaultContent(driver);
			}
		} else {
			log(LogStatus.INFO, "Not able to click on installed packages link so cannot istall user package: "
					+ firstName + " " + lastName, YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return false;
	}

	public boolean checkInstalledPackageVersion(String packageVersion, int timeout) {
		if (sendKeys(driver, getQucikSearchInSetupPage(timeout), "Installed package",
				"quick search text box in setup page", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "passed value in serach text box installed package ", YesNo.No);

		} else {
			log(LogStatus.INFO,
					"Not able to search installed package in search text box so cannot click on installed package link in lightning",
					YesNo.Yes);
			return false;
		}
		if (click(driver, getInstalledPackageLink(timeout), "Installed Package link", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on installed package link", YesNo.No);
			switchToFrame(driver, timeout, getSetUpPageIframe(60));

			String version = getText(driver, getInstalledPackageVersion(), "", action.SCROLLANDBOOLEAN);
			if (version != null) {

				if (version.trim().equals(packageVersion.trim())) {
					log(LogStatus.PASS, "Installed pacakge verison:" + packageVersion + " is matched successfully",
							YesNo.No);
					return true;
				} else {
					log(LogStatus.FAIL, "Installed pacakge verison:" + packageVersion + " is  not matched: Expected"
							+ packageVersion + " Actual:" + version, YesNo.No);
					return false;
				}

			} else {

				log(LogStatus.FAIL, "Not able to get text of installed package version ", YesNo.No);
				return false;
			}

		} else {
			log(LogStatus.INFO, "Not able to clicked on installed package link", YesNo.Yes);
			return false;
		}

	}

	/**
	 * @author Ankit Jaiswal
	 * @param layoutName
	 * @return true if able to click on already created layout
	 */
	public boolean clickOnAlreadyCreatedLayout(String layoutName) {
		String xpath = "//td//a//span[text()='" + layoutName + "']";
		WebElement ele = FindElement(driver, xpath, layoutName, action.SCROLLANDBOOLEAN, 60);
		ele = isDisplayed(driver, ele, "visibility", 30, layoutName);
		if (click(driver, ele, layoutName, action.SCROLLANDBOOLEAN)) {
			return true;
		} else {
			log(LogStatus.ERROR, "could not click on layout " + layoutName, YesNo.Yes);
		}
		return false;
	}

	/**
	 * @author Ravi Kumar
	 * @param layoutName
	 * @param timeout
	 * @return true if able to open already created page layout
	 */
	public boolean openAlreadyCreatedPageLayout(String layoutName, ObjectFeatureName objectFeatureName, int timeOut) {
		boolean flag = false;
		WebElement ele;
		ele = isDisplayed(driver,
				FindElement(driver, "//a[contains(text(),'" + objectFeatureName + "')]", "", action.BOOLEAN, 20),
				"visibility", 20, objectFeatureName + " feature link");

		if (ele != null) {

			if (click(driver, ele, objectFeatureName + " object feature link", action.SCROLLANDBOOLEAN)) {
				ele = null;
				if (sendKeys(driver, getQuickSearchInObjectManager_Lighting(timeOut), layoutName,
						layoutName + " page layout", action.BOOLEAN)) {
					log(LogStatus.PASS, "entered page layout name in quick search" + layoutName, YesNo.Yes);

					String xpath = "//td//a//span[text()='" + layoutName + "']";
					ele = FindElement(driver, xpath, layoutName, action.SCROLLANDBOOLEAN, timeOut);

					try {
						ele = isDisplayed(driver, ele, "visibility", timeOut, layoutName);
						if (click(driver, ele, layoutName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on page layout:" + layoutName, YesNo.Yes);
							flag = true;
						} else {
							log(LogStatus.ERROR, "could not click on layout :" + layoutName, YesNo.Yes);
							return flag;
						}
					} catch (StaleElementReferenceException e) {
						ele = isDisplayed(driver, ele, "visibility", timeOut, layoutName);
						if (click(driver, ele, layoutName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on page layout:" + layoutName, YesNo.Yes);
							flag = true;
						} else {
							log(LogStatus.ERROR, "could not click on layout :" + layoutName, YesNo.Yes);
							return flag;
						}
					}

				} else {
					log(LogStatus.FAIL, "cannot search page layout name in quick search" + layoutName, YesNo.Yes);
					return flag;

				}

			} else {
				appLog.error("Not able to click on object feature " + objectFeatureName);
			}

		} else {

			appLog.error(" object feature " + objectFeatureName + " is not visible so cannot click on it");
			return flag;

		}

		return flag;
	}

	/**
	 * @author Ravi Kumar
	 * @param layoutName
	 * @param timeout
	 * @return empty list if all field matched in page layout
	 */
	public List<String> verifyFieldsAvailabilityOnPageLayout(String fieldsAndPageLayout, int timeOut) {
		List<String> result = new ArrayList<>();

		String layoutName = "";
		String fieldName = "";
		String[] allFieldsAndLayout = fieldsAndPageLayout.split("<break>");
		String[] fieldsAndLayout;
		String[] allFields;

		for (int a = 0; a < allFieldsAndLayout.length; a++) {
			fieldsAndLayout = allFieldsAndLayout[a].trim().split("<pl>");
			layoutName = fieldsAndLayout[0];
			allFields = fieldsAndLayout[1].split("#");

			if (openAlreadyCreatedPageLayout(layoutName, ObjectFeatureName.pageLayouts, 60)) {
				log(LogStatus.PASS, "Successfully Open page layout :" + layoutName, YesNo.No);

				ThreadSleep(5000);
				switchToFrame(driver, 60, getSetUpPageIframe(120));

				for (int i = 0; i < allFields.length; i++) {

					fieldName = allFields[i];

					if (sendKeysAndPressEnter(driver, getQuickFindInPageLayout_Lighting(timeOut), fieldName,
							fieldName + " field", action.BOOLEAN)) {
						ThreadSleep(1000);
						List<WebElement> lst = getFieldsInPageLayoutList();
						List<WebElement> lst2 = FindElements(driver,
								"//div[@id='fieldTrough']//div[contains(@class,'item')]/span", "");
						int size = lst.size();
						for (int b = 0; b < size; b++) {
							String at = lst.get(b).getAttribute("class");
							String at2 = lst2.get(b).getText().replace("...", "");

							if (fieldName.contains(at2)) {

								log(LogStatus.PASS,
										fieldName + " field successfully found in the page layout :" + layoutName,
										YesNo.No);

								if (at.contains("used")) {

									log(LogStatus.PASS,
											fieldName + " is enable/present in the page layout :" + layoutName,
											YesNo.No);

								} else {
									log(LogStatus.FAIL,
											fieldName + " Field not enable in the page layout :" + layoutName,
											YesNo.No);
									result.add(fieldName + " Field not enable in the page layout :" + layoutName);

								}
								break;

							} else {
								if (b == size - 1) {

									log(LogStatus.FAIL,
											fieldName + " field  not found in the page layout :" + layoutName,
											YesNo.No);
									result.add(fieldName + " field not found in the page layout :" + layoutName);

								}

							}

						}

					} else {
						log(LogStatus.FAIL, "Not able to search field: " + fieldName + " in quick find input",
								YesNo.No);
						result.add("Not able to search field: " + fieldName + " in quick find input");

					}

				}

			} else {
				log(LogStatus.FAIL, "Not able to Open page layout :" + layoutName, YesNo.No);
				result.add("Not able to Open page layout :" + layoutName);

			}
			switchToDefaultContent(driver);

		}
		return result;
	}

	/**
	 * @author Ravi Kumar
	 * @param compactLayoutName
	 * @param timeout
	 * @return empty list if all compact layout matched
	 */
	public List<String> verifyFieldsAvailabilityOnCompactLayout(String fieldsAndPageLayout, int timeOut) {
		List<String> result = new ArrayList<>();

		String compactLayoutName = "";
		String[] allFieldsAndLayout = fieldsAndPageLayout.split("<break>");
		String[] fieldsAndLayout;
		String[] allFields;

		for (int a = 0; a < allFieldsAndLayout.length; a++) {
			fieldsAndLayout = allFieldsAndLayout[a].trim().split("<cl>");
			compactLayoutName = fieldsAndLayout[0];
			allFields = fieldsAndLayout[1].split("#");

			if (openAlreadyCreatedPageLayout(compactLayoutName, ObjectFeatureName.compactLayouts, 60)) {
				log(LogStatus.PASS, "Successfully Open compact layout :" + compactLayoutName, YesNo.No);

				ThreadSleep(5000);
				switchToFrame(driver, 60, getSetUpPageIframe(120));

				String fileName = Arrays.toString(allFields).replace("[", "").replace("]", "");

				List<WebElement> lst = getFieldsListInCompactLayout();
				List<String> result2 = compareMultipleList(driver, fileName, lst);

				if (result2.isEmpty()) {
					log(LogStatus.FAIL, "All Files: " + fileName + " Is matched successfully in compact layout: "
							+ compactLayoutName, YesNo.No);

				} else {

					log(LogStatus.FAIL,
							"Files: " + result2 + " are not matched in compact layout: " + compactLayoutName, YesNo.No);
					result.add("Files: " + result2 + " are not matched in compact layout: " + compactLayoutName);

				}

			} else {
				log(LogStatus.FAIL, "Not able to Open compact layout :" + compactLayoutName, YesNo.No);
				result.add("Not able to Open compact layout :" + compactLayoutName);

			}
			switchToDefaultContent(driver);

		}
		return result;
	}

	/**
	 * @author ANKIT JAISWAL
	 * @param environment
	 * @param mode
	 * @param objectName
	 * @param objectLeftSideActions
	 * @param dataType
	 * @param fieldLabelName
	 * @param formulaReturnType
	 * @param formulaText
	 * @return true/false
	 */
	public boolean addCustomFieldforFormula(String environment, String mode, object objectName,
			ObjectFeatureName objectLeftSideActions, String dataType, String fieldLabelName,
			String[][] labelsWithValues, String formulaReturnType, String formulaText) {
		WebElement ele = null;
		tabCustomObj = tabCustomObj;
		if (searchStandardOrCustomObject(environment, mode, objectName)) {
			log(LogStatus.PASS, "object searched : " + objectName.toString(), YesNo.No);
			ThreadSleep(5000);
			String xpath = "//div[@data-aura-class='uiScroller']//a[text()='" + objectName.toString() + "']";
			if (objectName == object.Custom_Object) {
				xpath = "//div[@data-aura-class='uiScroller']//a[text()='" + tabCustomObj.toString() + "']";
			} else {
				xpath = "//div[@data-aura-class='uiScroller']//a[text()='" + objectName.toString() + "']";
			}

			ele = FindElement(driver, xpath, objectName.toString() + " xpath", action.SCROLLANDBOOLEAN, 30);
			if (ele != null) {
				if (click(driver, ele, objectName.toString() + " xpath ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "clicked on object Name " + objectName, YesNo.No);
					ThreadSleep(5000);
					xpath = "//div[@id='setupComponent']/div[@class='setupcontent']//ul/li/a[@data-list='"
							+ objectLeftSideActions + "']";
					ele = FindElement(driver, xpath, objectLeftSideActions + " xpath", action.SCROLLANDBOOLEAN, 20);
					if (click(driver, ele, objectLeftSideActions + " xpath ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on object side action :  " + objectLeftSideActions, YesNo.No);
						ThreadSleep(5000);
						if (click(driver, getCustomFieldNewButton(30), "new button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on new button", YesNo.No);
							ThreadSleep(1000);
							if (switchToFrame(driver, 30, getNewCustomFieldFrame(objectName.toString(), 30))) {
								if (click(driver, getNewCustomFieldDataTypeOrFormulaReturnType(dataType, 30),
										"data type radio button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, dataType + " : data type radio button ", YesNo.No);
									if (click(driver, getCustomFieldNextBtn(30), "next button",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.PASS, "Clicked on Step 1 Next button", YesNo.No);
										ThreadSleep(5000);
										if (dataType.equalsIgnoreCase("Lookup Relationship")) {
											String relatedTovalue = "";
											if (labelsWithValues[0][1] != null) {
												for (String[] objects : labelsWithValues) {
													System.err.println(objects[0]);
													if (objects[0] == "Related To") {
														System.err.println(objects[1]);
														relatedTovalue = objects[1];
														break;
													}
												}
												if (!relatedTovalue.isEmpty()) {
													if (selectVisibleTextFromDropDown(driver,
															getRelatedToDropDownList(10), "getRelatedToDropDownList",
															relatedTovalue)) {
														log(LogStatus.PASS,
																"select related to drop down value : " + relatedTovalue,
																YesNo.No);
														if (click(driver, getCustomFieldNextBtn(30), "next button",
																action.SCROLLANDBOOLEAN)) {
															log(LogStatus.PASS, "Clicked on Step 1 Next button",
																	YesNo.No);
															ThreadSleep(1000);

														} else {
															log(LogStatus.FAIL,
																	"Not able to select to next button so cannot create  object "
																			+ fieldLabelName,
																	YesNo.Yes);
															return false;
														}
													} else {
														log(LogStatus.FAIL,
																"Not able to select to related drop down value : "
																		+ relatedTovalue,
																YesNo.Yes);
														return false;
													}
												} else {
													log(LogStatus.FAIL,
															"drop down value is not present for look relation object so cannot create look object "
																	+ fieldLabelName,
															YesNo.Yes);
													return false;
												}
											} else {
												log(LogStatus.FAIL,
														"related to drop down value is not present for look relation object so cannot create look object "
																+ fieldLabelName,
														YesNo.Yes);
												return false;
											}

										}
										ThreadSleep(1000);
										if (sendKeys(driver, getFieldLabelTextBox(30), fieldLabelName,
												"field label name ", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.PASS,
													"passed value in field label text box : " + fieldLabelName,
													YesNo.No);

											if (dataType.equalsIgnoreCase("Picklist")
													|| dataType.equalsIgnoreCase("Picklist (Multi-Select)")) {
												xpath = "//label[text()='Enter values, with each value separated by a new line']//preceding-sibling::input[@name='picklistType']";
												ele = FindElement(driver, xpath, "", action.BOOLEAN, 5);

												if (click(driver, ele, "Radio Button", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.PASS, "Click on radio button", YesNo.No);
													ThreadSleep(2000);

													xpath = "//textarea[@id='ptext']";
													ele = FindElement(driver, xpath, "", action.BOOLEAN, 5);
													String val = labelsWithValues[0][1];
													String[] data = val.split(",");
													for (String vals : data) {
														try {
															ele.sendKeys(vals);
															ele.sendKeys(Keys.ENTER);
															log(LogStatus.PASS, "Passed Value in "
																	+ labelsWithValues[0][0] + " " + vals, YesNo.No);
														} catch (Exception ex) {
															ex.printStackTrace();
															log(LogStatus.FAIL, "Not able to pass "
																	+ labelsWithValues[0][0] + " " + vals, YesNo.Yes);
														}
													}

												} else {
													log(LogStatus.FAIL, "Not Able to Click on radio button", YesNo.Yes);

												}

											} else {
												if (labelsWithValues[0][1] != null
														|| labelsWithValues[0][1] == "Length") {
													for (String[] labelWithValue : labelsWithValues) {
														xpath = "//label[contains(text(),'" + labelWithValue[0]
																+ "')]/../following-sibling::td//input";
														ele = FindElement(driver, xpath,
																labelWithValue[0] + " " + labelWithValue[1],
																action.BOOLEAN, 5);
														if (sendKeys(driver, ele, labelWithValue[1],
																labelWithValue[0] + " " + labelWithValue[1],
																action.SCROLLANDBOOLEAN)) {
															log(LogStatus.PASS, "Passed Value in " + labelWithValue[0]
																	+ " " + labelWithValue[1], YesNo.No);
														} else {
															log(LogStatus.FAIL, "Not able to pass " + labelWithValue[0]
																	+ " " + labelWithValue[1], YesNo.Yes);
														}
														ThreadSleep(500);

													}
												}
											}
										}
										if (click(driver, getCustomFieldNextBtn(30), "next button",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.PASS, "Clicked on Step 2 Next button", YesNo.No);
											ThreadSleep(2000);
											// if(sendKeys(driver, getFormulaTextBox(30), formulaText,"formula text
											// area", action.SCROLLANDBOOLEAN)) {
											// log(LogStatus.PASS, "Passed Value in formula Text Box "+formulaText,
											// YesNo.No);
											if (click(driver, getCustomFieldNextBtn(30), "next button",
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.PASS, "Clicked on Step 3 Next button", YesNo.No);
												ThreadSleep(1000);
												// if(click(driver, getCustomFieldNextBtn(30),"next button",
												// action.SCROLLANDBOOLEAN)) {
												// log(LogStatus.PASS, "Clicked on Step 4 Next button", YesNo.No);
												// ThreadSleep(1000);
												click(driver, getCustomFieldNextBtn(10), "next button",
														action.SCROLLANDBOOLEAN);
												ThreadSleep(1000);
												if (click(driver, getCustomFieldSaveBtn(30), "save button",
														action.SCROLLANDBOOLEAN)) {
													log(LogStatus.PASS, "clicked on save button", YesNo.No);
													ThreadSleep(5000);
													switchToDefaultContent(driver);
													return true;

												} else {
													log(LogStatus.FAIL,
															"Not able to click on save button so cannot create custom field "
																	+ objectName,
															YesNo.Yes);
												}

												// }else {
												// log(LogStatus.FAIL, "Not able to click on Step 4 next button so
												// cannot create custom field : "+objectName,YesNo.Yes);
												// }

											} else {
												log(LogStatus.FAIL,
														"Not able to click on Step 3 next button so cannot create custom field : "
																+ objectName,
														YesNo.Yes);
											}

											// }else {
											// log(LogStatus.FAIL, "Not able to click on Step 2 next button so cannot
											// create custom field : "+objectName,YesNo.Yes);
											// }
										} else {
											log(LogStatus.FAIL,
													"Not able to enter value in field label text box : "
															+ fieldLabelName + " so cannot create custom field",
													YesNo.Yes);
										}
									} else {
										log(LogStatus.FAIL,
												"Not able to click on Step 1 next button so cannot create custom field",
												YesNo.Yes);
									}
								} else {
									log(LogStatus.FAIL, "Not able to click on data type radio button " + dataType
											+ " so cannot create custom field", YesNo.Yes);
								}
							} else {
								log(LogStatus.FAIL, "Not able to switch in " + objectName
										+ " new object frame so cannot add custom object", YesNo.Yes);
							}
						} else {
							log(LogStatus.FAIL, "Not able to click on New button so cannot add custom field in "
									+ objectName.toString(), YesNo.Yes);
						}

					} else {
						log(LogStatus.FAIL, "Not able to click on object side action " + objectLeftSideActions
								+ " so cannot add custom object ", YesNo.Yes);
					}

				} else {
					log(LogStatus.FAIL,
							"Not able to click on object Name " + objectName + " so cannot add custom object ",
							YesNo.Yes);
				}
			} else {
				log(LogStatus.FAIL,
						"Not able to found object : " + objectName.toString() + " so cannot add custom object",
						YesNo.Yes);
			}
		} else {
			log(LogStatus.FAIL, "Not able to search object " + objectName.toString() + " so cannot add custom object",
					YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return false;
	}

	/**
	 * @author ANKIT JAISWAL
	 * @param objectName
	 * @param objectFeatureName
	 * @param fieldSetLabel
	 * @param fieldSetWhereisThisUsed
	 * @return true/false
	 */
	public boolean createFieldSetComponent(object objectName, ObjectFeatureName objectFeatureName, String fieldSetLabel,
			String fieldSetWhereisThisUsed, String DragComponentName) {
		boolean flag = false;
		WebElement ele = null, sourceElement = null;
		int count = 0;
		if (searchStandardOrCustomObject(environment, mode, objectName)) {
			log(LogStatus.INFO, "click on Object : " + objectName, YesNo.No);
			ThreadSleep(2000);
			if (clickOnObjectFeature(environment, mode, objectName, objectFeatureName)) {
				log(LogStatus.INFO, "Clicked on feature : " + objectFeatureName, YesNo.No);
				ThreadSleep(1000);
				if (sendKeys(driver, getQuickSearchInObjectManager_Lighting(10), fieldSetLabel, "search text box",
						action.BOOLEAN)) {
					String xpath = "//span[text()='" + fieldSetLabel + "']";
					ele = isDisplayed(driver, FindElement(driver, xpath, "field set label text", action.BOOLEAN, 3),
							"visibility", 3, "field set label text");
					if (ele != null) {
						log(LogStatus.INFO, "Field Set Label " + fieldSetLabel + " is already created ", YesNo.No);
						return true;
					}
				}
				if (click(driver, getObjectFeatureNewButton(objectFeatureName, 10), "new button", action.BOOLEAN)) {
					log(LogStatus.INFO, "clicked on New button", YesNo.No);
					ThreadSleep(2000);
					switchToFrame(driver, 20, getFieldSetComponentFrame(20));
					if (sendKeys(driver, getFieldSetLabelTextBox(10), fieldSetLabel, "field set label text box",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Entering value in field set text box : " + fieldSetLabel, YesNo.No);
						if (sendKeys(driver, getFieldSetWhereIsThisUsedTextArea(10), fieldSetWhereisThisUsed,
								"where is this used text area", action.BOOLEAN)) {
							log(LogStatus.INFO, "Entering value in where is this used text area : " + fieldSetLabel,
									YesNo.No);
							if (click(driver, getSaveButton(10), "save button", action.BOOLEAN)) {
								log(LogStatus.ERROR, "Clicked on save button and create field set label : "
										+ fieldSetLabel + " successfully", YesNo.Yes);
								ThreadSleep(2000);
								switchToDefaultContent(driver);
								if (DragComponentName != null) {
									String[] splitedDragComponent = DragComponentName.split("<break>");
									switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
									for (int i = 0; i < splitedDragComponent.length; i++) {
										sendKeys(driver, getQuickFindSearchBox(environment, mode, 10),
												splitedDragComponent[i], "Search Value : " + splitedDragComponent[i],
												action.BOOLEAN);
										if (splitedDragComponent[i].equalsIgnoreCase("Highest Stage Reached")
												|| splitedDragComponent[i]
														.equalsIgnoreCase("Total Fund Commitments (mn)")
												|| splitedDragComponent[i]
														.equalsIgnoreCase("Total Co-investment Commitments (mn)")) {
											String DragComponent = splitedDragComponent[i].split(" ")[0];
											sourceElement = isDisplayed(driver,
													FindElement(driver,
															"//span[starts-with(text(),'" + DragComponent + "')]", "",
															action.BOOLEAN, 10),
													"visibility", 10, splitedDragComponent[i] + " page layout link");
										} else {
											sourceElement = isDisplayed(driver,
													FindElement(driver,
															"//span[starts-with(text(),'" + splitedDragComponent[i]
																	+ "')]",
															"", action.BOOLEAN, 10),
													"visibility", 10, splitedDragComponent[i] + " page layout link");
										}
										ThreadSleep(2000);
										if (dragNDropOperation(driver, sourceElement,
												getFieldSetdefaultViewDragAndDropTextLabel(5))) {
											log(LogStatus.INFO, "Dragged Successfully : " + splitedDragComponent[i],
													YesNo.No);
											count++;
										} else {
											log(LogStatus.ERROR,
													"Not able to drag and drop field " + splitedDragComponent[i]
															+ " in created field set component " + fieldSetLabel,
													YesNo.Yes);
										}
									}
									if (count == splitedDragComponent.length) {
										flag = true;
									}
								} else {
									flag = true;
								}
								if (click(driver, getPageLayoutSaveBtn(object.Global_Actions, 10),
										"page layouts save button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Save button", YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Not able to click on Save button cannot save pagelayout dragged object or section in field set component "
													+ fieldSetLabel,
											YesNo.Yes);
									flag = false;
								}
							} else {
								log(LogStatus.ERROR,
										"Not able to click on save button so cannot create field set label : "
												+ fieldSetLabel,
										YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Not able to enter the value in where is this used text area : "
									+ fieldSetLabel + " so cannot create field set component", YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Not able to enter the value in field set label text box : "
								+ fieldSetLabel + " so cannot create field set component", YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR,
							"Not able to click on new button so cannot create field set compnent : " + fieldSetLabel,
							YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on object feature : " + objectFeatureName, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR,
					"Not able to click on Object : " + objectName + " so cannot create field set component", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param objectName
	 * @param objectFeatureName
	 * @param permissionType
	 * @param fieldLabel
	 * @param profileName
	 * @return true if able to give/Remove Object Permission From Object Manager
	 */
	public boolean giveAndRemoveObjectPermissionFromObjectManager(object objectName,
			ObjectFeatureName objectFeatureName, PermissionType permissionType, String fieldLabel, String profileName) {
		boolean flag = false;
		WebElement ele = null;
		if (searchStandardOrCustomObject(environment, mode, objectName)) {
			log(LogStatus.INFO, "click on Object : " + objectName, YesNo.No);
			ThreadSleep(2000);
			if (clickOnObjectFeature(environment, mode, objectName, objectFeatureName)) {
				log(LogStatus.INFO, "Clicked on feature : " + objectFeatureName, YesNo.No);
				ThreadSleep(1000);
				if (sendKeys(driver, getQuickSearchInObjectManager_Lighting(10), fieldLabel, "search text box",
						action.BOOLEAN)) {
					String xpath = "//span[text()='" + fieldLabel + "']/..";
					ele = isDisplayed(driver, FindElement(driver, xpath, "field set label text", action.BOOLEAN, 3),
							"visibility", 3, "field set label text");
					if (ele != null) {
						if (click(driver, ele, "field label text link", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on field label " + fieldLabel, YesNo.No);
							switchToFrame(driver, 20, getFieldAndRelationShipFrame(20));
							ThreadSleep(1000);
							if (click(driver,
									getObjectEditOrSetFieldSecurityOrViewFieldAccessbilityBtn(
											"View Field Accessibility", 10),
									"view field accessbility button xpath", action.BOOLEAN)) {
								log(LogStatus.INFO, "clicked on view field accessbility of field label : " + fieldLabel,
										YesNo.No);
								switchToFrame(driver, 20, getFieldAndRelationShipFrame(20));
								ThreadSleep(1000);
								if (selectVisibleTextFromDropDown(driver, getFieldAccessbilityDropDown(10),
										"field accessbility drop down", fieldLabel)) {
									log(LogStatus.INFO, "select field label accessbility drop down " + fieldLabel,
											YesNo.No);
									ThreadSleep(1000);
									if (clickUsingJavaScript(driver, getfieldAccessOptionLink(profileName, 10),
											"profile link name", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "clicked on " + profileName + " link", YesNo.No);
										ThreadSleep(5000);
										switchToFrame(driver, 20, getFieldAndRelationShipFrame(20));
										if (click(driver, getFieldLevelSecurityVisibleCheckBox(10), "check box",
												action.BOOLEAN)) {
											log(LogStatus.INFO, "Clicked on field level security check box", YesNo.No);
											if (click(driver, getViewAccessbilityDropDownSaveButton(10), "save button",
													action.BOOLEAN)) {
												log(LogStatus.INFO, "save button", YesNo.No);
												return true;

											} else {
												log(LogStatus.ERROR,
														"Not able to click on save button field accessbility of field label "
																+ fieldLabel + " in object " + objectName
																+ " so cannot " + permissionType,
														YesNo.Yes);
											}
										} else {
											log(LogStatus.ERROR,
													"Not able to click on visible field accessbility of field label "
															+ fieldLabel + " in object " + objectName + " so cannot "
															+ permissionType,
													YesNo.Yes);
										}
									} else {
										log(LogStatus.ERROR,
												" Not able to click on profile link from view field accessbility of field label "
														+ fieldLabel + " in object " + objectName + " so cannot "
														+ permissionType,
												YesNo.Yes);
									}
								} else {
									log(LogStatus.ERROR,
											"Not able to select value from view field accessbility of field label "
													+ fieldLabel + " in object " + objectName + " so cannot "
													+ permissionType,
											YesNo.Yes);
								}
							} else {
								log(LogStatus.ERROR,
										"Not able to click on view field accessbility of field label " + fieldLabel
												+ " in object " + objectName + " so cannot " + permissionType,
										YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on field label " + fieldLabel + " in object "
									+ objectName + " so cannot " + permissionType, YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Search field label " + fieldLabel + " is not found in object "
								+ objectName + " so cannot " + permissionType, YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Not able to search field label " + fieldLabel + " in object " + objectName
							+ " so cannot " + permissionType, YesNo.Yes);
				}

			} else {
				log(LogStatus.FAIL,
						"Not able to found object : " + objectName.toString() + " so cannot " + permissionType,
						YesNo.Yes);
			}
		} else {
			log(LogStatus.FAIL, "Not able to search object " + objectName.toString() + " so cannot " + permissionType,
					YesNo.Yes);
		}

		return flag;
	}

	/**
	 * @author Ravi Kumar
	 * @param objectName
	 * @param objectFeatureName
	 * @param permissionType
	 * @param fieldLabel
	 * @param oldFieldLabel
	 * @param profileName
	 * @return true if able to give/Remove Object Permission From Object Manager
	 */
	public boolean giveAndRemoveObjectPermissionFromObjectManager(object objectName,
			ObjectFeatureName objectFeatureName, PermissionType permissionType, String fieldLabel, String oldFieldLabel,
			String profileName) {
		boolean flag = false;
		WebElement ele = null;
		if (searchStandardOrCustomObject(environment, mode, objectName)) {
			log(LogStatus.INFO, "click on Object : " + objectName, YesNo.No);
			ThreadSleep(2000);
			if (clickOnObjectFeature(environment, mode, objectName, objectFeatureName)) {
				log(LogStatus.INFO, "Clicked on feature : " + objectFeatureName, YesNo.No);
				ThreadSleep(1000);
				if (sendKeys(driver, getQuickSearchInObjectManager_Lighting(10), fieldLabel, "search text box",
						action.BOOLEAN)) {
					String xpath = "//span[text()='" + fieldLabel + "']/..";
					ele = isDisplayed(driver, FindElement(driver, xpath, "field set label text", action.BOOLEAN, 3),
							"visibility", 3, "field set label text");
					if (ele != null) {
						if (click(driver, ele, "field label text link", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on field label " + fieldLabel, YesNo.No);
							switchToFrame(driver, 20, getFieldAndRelationShipFrame(20));
							ThreadSleep(1000);
							if (click(driver,
									getObjectEditOrSetFieldSecurityOrViewFieldAccessbilityBtn(
											"View Field Accessibility", 10),
									"view field accessbility button xpath", action.BOOLEAN)) {
								log(LogStatus.INFO, "clicked on view field accessbility of field label : " + fieldLabel,
										YesNo.No);
								switchToFrame(driver, 20, getFieldAndRelationShipFrame(20));
								ThreadSleep(1000);
								if (selectVisibleTextFromDropDown(driver, getFieldAccessbilityDropDown(10),
										"field accessbility drop down", oldFieldLabel)) {
									log(LogStatus.INFO, "select field label accessbility drop down " + oldFieldLabel,
											YesNo.No);
									ThreadSleep(1000);
									if (clickUsingJavaScript(driver, getfieldAccessOptionLink(profileName, 10),
											"profile link name", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "clicked on " + profileName + " link", YesNo.No);
										ThreadSleep(5000);
										switchToFrame(driver, 20, getFieldAndRelationShipFrame(20));
										if (click(driver, getFieldLevelSecurityVisibleCheckBox(10), "check box",
												action.BOOLEAN)) {
											log(LogStatus.INFO, "Clicked on field level security check box", YesNo.No);
											if (click(driver, getViewAccessbilityDropDownSaveButton(10), "save button",
													action.BOOLEAN)) {
												log(LogStatus.INFO, "save button", YesNo.No);
												return true;

											} else {
												log(LogStatus.ERROR,
														"Not able to click on save button field accessbility of field label "
																+ fieldLabel + " in object " + objectName
																+ " so cannot " + permissionType,
														YesNo.Yes);
											}
										} else {
											log(LogStatus.ERROR,
													"Not able to click on visible field accessbility of field label "
															+ fieldLabel + " in object " + objectName + " so cannot "
															+ permissionType,
													YesNo.Yes);
										}
									} else {
										log(LogStatus.ERROR,
												" Not able to click on profile link from view field accessbility of field label "
														+ fieldLabel + " in object " + objectName + " so cannot "
														+ permissionType,
												YesNo.Yes);
									}
								} else {
									log(LogStatus.ERROR,
											"Not able to select value from view field accessbility of field label "
													+ fieldLabel + " in object " + objectName + " so cannot "
													+ permissionType,
											YesNo.Yes);
								}
							} else {
								log(LogStatus.ERROR,
										"Not able to click on view field accessbility of field label " + fieldLabel
												+ " in object " + objectName + " so cannot " + permissionType,
										YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on field label " + fieldLabel + " in object "
									+ objectName + " so cannot " + permissionType, YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Search field label " + fieldLabel + " is not found in object "
								+ objectName + " so cannot " + permissionType, YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Not able to search field label " + fieldLabel + " in object " + objectName
							+ " so cannot " + permissionType, YesNo.Yes);
				}

			} else {
				log(LogStatus.FAIL,
						"Not able to found object : " + objectName.toString() + " so cannot " + permissionType,
						YesNo.Yes);
			}
		} else {
			log(LogStatus.FAIL, "Not able to search object " + objectName.toString() + " so cannot " + permissionType,
					YesNo.Yes);
		}

		return flag;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param objectName
	 * @param objectFeatureName
	 * @param fieldSetLabel
	 * @param DragComponentName
	 * @param removableObjectName
	 * @param removeSomeFields
	 * @param ChangePosition      TODO
	 * @return true if able to change Position Of Field Set Component
	 */
	public boolean changePositionOfFieldSetComponent(object objectName, ObjectFeatureName objectFeatureName,
			String fieldSetLabel, String DragComponentName, String removableObjectName, YesNo removeSomeFields,
			YesNo ChangePosition) {
		boolean flag = false;
		WebElement ele = null, sourceElement = null, destination = null;
		int count = 0;
		if (searchStandardOrCustomObject(environment, mode, objectName)) {
			log(LogStatus.INFO, "click on Object : " + objectName, YesNo.No);
			ThreadSleep(2000);
			if (clickOnObjectFeature(environment, mode, objectName, objectFeatureName)) {
				log(LogStatus.INFO, "Clicked on feature : " + objectFeatureName, YesNo.No);
				ThreadSleep(1000);
				if (sendKeys(driver, getQuickSearchInObjectManager_Lighting(10), fieldSetLabel, "search text box",
						action.BOOLEAN)) {
					String xpath = "//span[text()='" + fieldSetLabel + "']/..";
					ele = isDisplayed(driver, FindElement(driver, xpath, "field set label text", action.BOOLEAN, 3),
							"visibility", 3, "field set label text");
					if (ele != null) {
						if (click(driver, ele, "create field set " + fieldSetLabel, action.BOOLEAN)) {
							log(LogStatus.INFO, "Field Set Label " + fieldSetLabel + " is already created ", YesNo.No);
							ThreadSleep(5000);
							switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
							if (removableObjectName != null
									&& ChangePosition.toString().equalsIgnoreCase(YesNo.No.toString())) {
								String[] removeObject = removableObjectName.split("<break>");
								List<WebElement> lst = getDraggedObjectListInCreateFieldSet();
								if (!lst.isEmpty()) {
									for (int i = 0; i < removeObject.length; i++) {
										xpath = "//div[@id='defaultView']//div[starts-with(text(),'" + removeObject[i]
												+ "')]/ancestor::div[contains(@class,'field-source')]";
										ele = FindElement(driver, xpath, removeObject[i] + " xpath", action.BOOLEAN,
												10);

										String id = ele.getAttribute("id");
										ThreadSleep(1000);
										((JavascriptExecutor) driver).executeScript("document.getElementById('" + id
												+ "').setAttribute('class', 'field-source field-selected field-hover');");
										ThreadSleep(2000);
										if (click(
												driver, FindElement(driver, xpath + "//div[@class='remove']",
														"remove Icon", action.BOOLEAN, 10),
												"remove icon", action.BOOLEAN)) {
											log(LogStatus.INFO, "Clicked on reomve icon of object : " + removeObject[i],
													YesNo.No);
										} else {
											log(LogStatus.INFO, "Not able to click on reomve icon of object : "
													+ removeObject[i]
													+ " so cannot remove old object and dragged new objects in field set",
													YesNo.No);
											return false;
										}
									}
								} else {
									log(LogStatus.ERROR, "Object is not present in create field set " + fieldSetLabel
											+ " so cannot remove old object and dragged new objects in field set",
											YesNo.Yes);
									return false;
								}

							} else if (removableObjectName != null
									&& ChangePosition.toString().equalsIgnoreCase(YesNo.Yes.toString())) {

								String[] sourceComponent = DragComponentName.split("<break>");
								String[] destinationComponent = removableObjectName.split("<break>");
								for (int i = 0; i < sourceComponent.length; i++) {
									sendKeys(driver, getQuickFindSearchBox(environment, mode, 10), sourceComponent[i],
											"Search Value : " + sourceComponent[i], action.BOOLEAN);
									if (sourceComponent[i].equalsIgnoreCase("Highest Stage Reached")
											|| sourceComponent[i].equalsIgnoreCase("Average Deal Quality Score")
											|| sourceComponent[i].equalsIgnoreCase("Contact Referral Source")
											|| sourceComponent[i].equalsIgnoreCase("Last Stay-in-Touch Request Date")
											|| sourceComponent[i].equalsIgnoreCase("Total Fund Commitments (mn)")
											|| sourceComponent[i]
													.equalsIgnoreCase("Total Co-investment Commitments (mn)")) {
										String DragComponent = sourceComponent[i].split(" ")[0];
										sourceElement = isDisplayed(driver,
												FindElement(driver,
														"//span[starts-with(text(),'" + DragComponent + "')]", "",
														action.BOOLEAN, 10),
												"visibility", 10, sourceComponent[i] + " page layout link");
									} else {
										sourceElement = isDisplayed(driver,
												FindElement(driver,
														"//div[@id='defaultView']/div//*[contains(text(),'"
																+ sourceComponent[i] + "')]",
														"", action.BOOLEAN, 10),
												"visibility", 10, sourceComponent[i] + " component in field set");

										destination = isDisplayed(driver, FindElement(driver,
												"//div[@id='defaultView']/div//*[contains(text(),'"
														+ destinationComponent[i] + "')]",
												"", action.BOOLEAN, 10), "visibility", 10,
												destinationComponent[i] + " component in field set");

									}
									ThreadSleep(2000);
									if (dragNDropField(driver, sourceElement, destination)) {
										log(LogStatus.INFO, "Dragged Successfully : " + sourceComponent[i], YesNo.No);
										count++;
									} else {
										log(LogStatus.ERROR,
												"Not able to drag and drop field " + sourceComponent[i]
														+ " in created field set component " + fieldSetLabel,
												YesNo.Yes);
									}
								}
								if (count == sourceComponent.length) {
									flag = true;
								}
							}
							if (removeSomeFields.toString().equalsIgnoreCase(YesNo.Yes.toString())) {
								xpath = "//div[@id='defaultView']/div";
								List<WebElement> fieldSetList = FindElements(driver, xpath,
										"all dragged field set list xpath");
								if (!fieldSetList.isEmpty()) {
									for (int i = 0; i < fieldSetList.size() - 2; i++) {
										ThreadSleep(1000);
										String id = fieldSetList.get(i).getAttribute("id");
										ThreadSleep(1000);
										((JavascriptExecutor) driver).executeScript("document.getElementById('" + id
												+ "').setAttribute('class','field-source');");
										ThreadSleep(1000);
										mouseOverOperation(driver, fieldSetList.get(i));
										ThreadSleep(3000);
										WebElement ele1 = FindElement(driver,
												"//div[@id='" + id + "']//div[@class='remove']", "remove Icon",
												action.BOOLEAN, 10);
										mouseOverClickOperation(driver, ele1);
										fieldSetList = FindElements(driver, xpath, "all dragged field set list xpath");
										if (fieldSetList.size() != 2) {
											continue;
										} else {
											break;
										}
									}

								} else {
									log(LogStatus.ERROR,
											"Field Set objects is not available so cannot remove some field set",
											YesNo.Yes);
								}
							}
							if (DragComponentName != null
									&& ChangePosition.toString().equalsIgnoreCase(YesNo.No.toString())) {
								String[] splitedDragComponent = DragComponentName.split("<break>");
								for (int i = 0; i < splitedDragComponent.length; i++) {
									sendKeys(driver, getQuickFindSearchBox(environment, mode, 10),
											splitedDragComponent[i], "Search Value : " + splitedDragComponent[i],
											action.BOOLEAN);
									if (splitedDragComponent[i].equalsIgnoreCase("Highest Stage Reached")
											|| splitedDragComponent[i].equalsIgnoreCase("Average Deal Quality Score")
											|| splitedDragComponent[i].equalsIgnoreCase("Contact Referral Source")
											|| splitedDragComponent[i]
													.equalsIgnoreCase("Last Stay-in-Touch Request Date")
											|| splitedDragComponent[i].equalsIgnoreCase("Total Fund Commitments (mn)")
											|| splitedDragComponent[i]
													.equalsIgnoreCase("Total Co-investment Commitments (mn)")) {
										String DragComponent = splitedDragComponent[i].split(" ")[0];
										sourceElement = isDisplayed(driver,
												FindElement(driver,
														"//span[starts-with(text(),'" + DragComponent + "')]", "",
														action.BOOLEAN, 10),
												"visibility", 10, splitedDragComponent[i] + " page layout link");
									} else {
										sourceElement = isDisplayed(driver,
												FindElement(driver,
														"//span[starts-with(text(),'" + splitedDragComponent[i] + "')]",
														"", action.BOOLEAN, 10),
												"visibility", 10, splitedDragComponent[i] + " page layout link");
									}
									ThreadSleep(2000);
									if (dragNDropField(driver, sourceElement,
											getFieldSetdefaultViewDragAndDropTextLabel(5))) {
										log(LogStatus.INFO, "Dragged Successfully : " + splitedDragComponent[i],
												YesNo.No);
										count++;
									} else {
										log(LogStatus.ERROR,
												"Not able to drag and drop field " + splitedDragComponent[i]
														+ " in created field set component " + fieldSetLabel,
												YesNo.Yes);
									}
								}
								if (count == splitedDragComponent.length) {
									flag = true;
								}
							} else {
								flag = true;
							}
							if (click(driver, getPageLayoutSaveBtn(object.Global_Actions, 10),
									"page layouts save button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Save button", YesNo.No);
								ThreadSleep(5000);

							} else {
								log(LogStatus.ERROR,
										"Not able to click on Save button cannot save pagelayout dragged object or section in field set component "
												+ fieldSetLabel,
										YesNo.Yes);
								flag = false;
							}
						} else {
							log(LogStatus.INFO, "Not able to click on created Field Set Label " + fieldSetLabel
									+ " is not visible so cannot change position of labels", YesNo.Yes);
						}
					} else {
						log(LogStatus.INFO, "created Field Set Label " + fieldSetLabel
								+ " is not visible so cannot change position of labels", YesNo.Yes);
					}
				} else {
					log(LogStatus.INFO, "Not able to search created Field Set Label " + fieldSetLabel, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on object feature : " + objectFeatureName, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR,
					"Not able to click on Object : " + objectName + " so cannot create field set component", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param objectName
	 * @param objectFeatureName
	 * @param fieldSetLabel
	 * @param removeSomeFields
	 * @return true if able to delete field set component
	 */
	public boolean deleteFieldSetComponent(object objectName, ObjectFeatureName objectFeatureName, String fieldSetLabel,
			YesNo removeSomeFields) {
		boolean flag = false;
		WebElement ele = null;
		List<WebElement> fieldSetList = new ArrayList<WebElement>();
		if (searchStandardOrCustomObject(environment, mode, objectName)) {
			log(LogStatus.INFO, "click on Object : " + objectName, YesNo.No);
			ThreadSleep(2000);
			if (clickOnObjectFeature(environment, mode, objectName, objectFeatureName)) {
				log(LogStatus.INFO, "Clicked on feature : " + objectFeatureName, YesNo.No);
				ThreadSleep(1000);
				while (true) {
					if (sendKeys(driver, getQuickSearchInObjectManager_Lighting(10), fieldSetLabel, "search text box",
							action.BOOLEAN)) {
						String xpath = "//span[text()='" + fieldSetLabel + "']/..";
						ele = isDisplayed(driver, FindElement(driver, xpath, "field set label text", action.BOOLEAN, 3),
								"visibility", 3, "field set label text");
						if (ele != null) {
							if (click(driver, ele, "create field set " + fieldSetLabel, action.BOOLEAN)) {
								log(LogStatus.INFO, "Field Set Label " + fieldSetLabel + " is already created ",
										YesNo.No);
								ThreadSleep(5000);
								switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
								if (removeSomeFields.toString().equalsIgnoreCase(YesNo.Yes.toString())) {
									xpath = "//div[@id='defaultView']/div";
									fieldSetList = FindElements(driver, xpath, "all dragged field set list xpath");
									if (!fieldSetList.isEmpty()) {
										for (int i = 0; i < fieldSetList.size(); i++) {
											ThreadSleep(1000);
											String id = fieldSetList.get(i).getAttribute("id");
											ThreadSleep(1000);
											((JavascriptExecutor) driver).executeScript("document.getElementById('" + id
													+ "').setAttribute('class', 'field-source field-selected field-hover');");
											ThreadSleep(2000);
											if (click(
													driver, FindElement(driver, xpath + "//div[@class='remove']",
															"remove Icon", action.BOOLEAN, 10),
													"remove icon", action.BOOLEAN)) {
												log(LogStatus.INFO, "Clicked on reomve icon of object", YesNo.No);
											} else {
												log(LogStatus.INFO,
														"Not able to click on reomve icon of object so cannot remove object from field set component",
														YesNo.No);
												return false;
											}
											ele = isDisplayed(driver,
													FindElement(driver,
															"//div[@id='defaultView']/div[@class='dataview-empty']",
															"field set label text", action.BOOLEAN, 3),
													"visibility", 3, "field set label text");
											// fieldSetList=FindElements(driver, xpath, "all dragged field set list
											// xpath");
											if (ele != null) {
												if (click(driver, getPageLayoutSaveBtn(object.Global_Actions, 10),
														"page layouts save button", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "Clicked on Save button", YesNo.No);
													ThreadSleep(5000);
													flag = true;
													break;

												} else {
													log(LogStatus.ERROR,
															"Not able to click on Save button cannot save pagelayout dragged object or section in field set component "
																	+ fieldSetLabel,
															YesNo.Yes);
													return false;
												}
											} else {
												if (click(driver, getPageLayoutSaveBtn(object.Global_Actions, 10),
														"page layouts save button", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "Clicked on Save button", YesNo.No);
													ThreadSleep(5000);
													switchToDefaultContent(driver);
													break;
												} else {
													log(LogStatus.ERROR,
															"Not able to click on Save button cannot save pagelayout dragged object or section in field set component "
																	+ fieldSetLabel,
															YesNo.Yes);
													flag = false;
												}
											}
										}
									} else {
										log(LogStatus.ERROR, "more then 2 fields are not available in field set object",
												YesNo.Yes);
									}
								}
							} else {
								log(LogStatus.INFO, "Not able to click on created Field Set Label " + fieldSetLabel
										+ " is not visible so cannot change position of labels", YesNo.Yes);
							}
						} else {
							log(LogStatus.INFO, "created Field Set Label " + fieldSetLabel
									+ " is not visible so cannot change position of labels", YesNo.Yes);
						}
					} else {
						log(LogStatus.INFO, "Not able to search created Field Set Label " + fieldSetLabel, YesNo.Yes);
					}

					if (ele != null)
						break;
					switchToDefaultContent(driver);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on object feature : " + objectFeatureName, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR,
					"Not able to click on Object : " + objectName + " so cannot create field set component", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param status
	 * @return EditInFrontOfFieldValues
	 */
	public WebElement clickOnEditInFrontOfFieldValues(String projectName, String status) {
		status = status.replace("_", " ");
		String xpath = "//th[text()='" + status + "']/preceding-sibling::td//a[contains(@title,'Edit')]";
		WebElement ele = isDisplayed(driver, FindElement(driver, xpath, "edit", action.SCROLLANDBOOLEAN, 10),
				"visibility", 10, "edit");
		return ele;
	}

	public WebElement clickOnEditInFrontOfFieldValues(String projectName, String status, boolean removeUnderscore) {
		if (removeUnderscore)
			status = status.replace("_", " ");
		String xpath = "//th[text()='" + status + "']/preceding-sibling::td//a[contains(@title,'Edit')]";
		WebElement ele = isDisplayed(driver, FindElement(driver, xpath, "edit", action.SCROLLANDBOOLEAN, 10),
				"visibility", 10, "edit");
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param driver
	 * @param appName
	 * @param developerName
	 * @param description
	 * @param timeOut
	 * @return true if clcik on edit for App
	 */
	public boolean clickOnEditForApp(WebDriver driver, String appName, String developerName, String description,
			int timeOut) {
		boolean flag = false;
		;
		String xpath = "";
		xpath = "//*[text()='" + appName + "']/../../following-sibling::*//*[text()='" + developerName
				+ "']/../../following-sibling::*//*[text()='Show Actions']/..";
		WebElement scrollEle = FindElement(driver,
				"//div[@class='uiScroller scroller-wrapper scroll-bidirectional native']", "Widget scroll",
				action.SCROLLANDBOOLEAN, 60);
		scrollActiveWidget(driver, scrollEle, By.xpath(xpath));
		ThreadSleep(2000);
		WebElement ele = isDisplayed(driver,
				FindElement(driver, xpath, "show more action", action.SCROLLANDBOOLEAN, timeOut), "visibility", timeOut,
				"show more action");
		if (click(driver, ele, "Show more action against " + appName + " : " + developerName + " " + description,
				action.BOOLEAN)) {
			log(LogStatus.INFO,
					"click on Show more action against " + appName + " : " + developerName + " " + description,
					YesNo.No);
			ThreadSleep(1000);
			xpath = "//li/a[@title='Edit']";
			ele = isDisplayed(driver, FindElement(driver, xpath, "edit", action.SCROLLANDBOOLEAN, timeOut),
					"visibility", timeOut, "edit");
			if (click(driver, ele, "Show more action against " + appName + " : " + developerName + " " + description,
					action.BOOLEAN)) {
				log(LogStatus.INFO,
						"able to click on edit button against " + appName + " : " + developerName + " " + description,
						YesNo.No);
				ThreadSleep(1000);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Not able to click on edit button against " + appName + " : " + developerName + " "
						+ description, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on Show more action against " + appName + " : " + developerName
					+ " " + description, YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @param driver
	 * @param appSetting
	 * @param timeOut
	 * @return true if able to click on particular app setting
	 */
	public boolean clickOnAppSettingList(WebDriver driver, AppSetting appSetting, int timeOut) {
		boolean flag = false;
		;
		String xpath = "";
		xpath = "//*[contains(text(),'" + appSetting + "')]";
		WebElement ele = isDisplayed(driver,
				FindElement(driver, xpath, "show more action", action.SCROLLANDBOOLEAN, timeOut), "visibility", timeOut,
				"show more action");
		if (click(driver, ele, appSetting.toString(), action.BOOLEAN)) {
			log(LogStatus.INFO, "able to click on " + appSetting, YesNo.No);
			flag = true;
		} else {
			log(LogStatus.ERROR, "Not able to click on " + appSetting, YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param addRemoveTabName
	 * @param customTabActionType
	 */
	public void addRemoveAppSetingData(String projectName, String addRemoveTabName,
			customTabActionType customTabActionType) {
		String[] splitedTabs = addRemoveTabName.split(",");
		WebElement ele;
		String xpath;
		int count = 0;
		if (customTabActionType.toString().equalsIgnoreCase("Add")) {
			System.err.println("inside Add");
			for (int i = 0; i < splitedTabs.length; i++) {
				//////////////////////////////////////////////////////
				xpath = "//div[contains(text(),'Selected')]/..//following-sibling::*//*[text()='" + splitedTabs[i]
						+ "']";
				ele = FindElement(driver, xpath, "selected item : " + splitedTabs[i], action.BOOLEAN, 10);
				if (ele != null) {
					log(LogStatus.INFO, splitedTabs[i] + " Already added", YesNo.No);
				} else {
					log(LogStatus.INFO, "going to add " + splitedTabs[i], YesNo.No);
					xpath = "//div[@class='search-form']//input";
					ele = FindElement(driver, xpath, "available item search box", action.BOOLEAN, 10);
					if (sendKeys(driver, ele, splitedTabs[i], "available item search box", action.BOOLEAN)) {
						log(LogStatus.INFO, "send value to available item search box : " + splitedTabs[i], YesNo.No);
						ThreadSleep(500);
						xpath = "//div[contains(text(),'Available')]/..//following-sibling::*//*[text()='"
								+ splitedTabs[i] + "']";
						ele = FindElement(driver, xpath, splitedTabs[i], action.BOOLEAN, 10);
						if (click(driver, ele, splitedTabs[i] + " from available item", action.BOOLEAN)) {
							log(LogStatus.INFO, "Able to select " + splitedTabs[i] + " from available item", YesNo.No);
							ThreadSleep(500);
							xpath = "//*[@title='Add']";
							ele = FindElement(driver, xpath, "Add icon for : " + splitedTabs[i], action.BOOLEAN, 10);
							if (click(driver, ele, "Add icon for : " + splitedTabs[i], action.BOOLEAN)) {
								log(LogStatus.INFO, "Able to clcik on Add icon for : " + splitedTabs[i], YesNo.No);
								ThreadSleep(500);
								count++;
							} else {
								sa.assertTrue(false, "Not Able to click on Add icon for : " + splitedTabs[i]);
								log(LogStatus.FAIL, "Not Able to click on Add icon for : " + splitedTabs[i], YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "Not Able to select " + splitedTabs[i] + " from available item");
							log(LogStatus.FAIL, "Not Able to select " + splitedTabs[i] + " from available item",
									YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to send value to available item search box : " + splitedTabs[i]);
						log(LogStatus.FAIL, "Not Able to send value to available item search box : " + splitedTabs[i],
								YesNo.Yes);
					}

				}
			}

		} else if (customTabActionType.toString().equalsIgnoreCase("Remove")) {
			System.err.println("inside remove");
			for (int i = 0; i < splitedTabs.length; i++) {
				//////////////////////////////////////////////////////
				xpath = "//div[contains(text(),'Selected')]/..//following-sibling::*//*[text()='" + splitedTabs[i]
						+ "']";
				ele = FindElement(driver, xpath, "selected item : " + splitedTabs[i], action.BOOLEAN, 10);
				if (ele == null) {
					log(LogStatus.INFO, splitedTabs[i] + " Already removed", YesNo.No);
				} else {
					log(LogStatus.INFO, "going to remove " + splitedTabs[i], YesNo.No);
					xpath = "//*[@title='Remove']";
					ele = FindElement(driver, xpath, "Add icon for : " + splitedTabs[i], action.BOOLEAN, 10);
					if (click(driver, ele, "Add icon for : " + splitedTabs[i], action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to click on remove icon for : " + splitedTabs[i], YesNo.No);
						ThreadSleep(500);
						count++;
					} else {
						sa.assertTrue(false, "Not Able to click on remove icon for : " + splitedTabs[i]);
						log(LogStatus.FAIL, "Not Able to click on remove icon for : " + splitedTabs[i], YesNo.Yes);
					}

				}
			}
		}

		if (count > 0) {
			if (click(driver, getCustomTabSaveBtn(projectName, 60), "Custom Tab Save Button",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "click on save button", YesNo.No);
				ThreadSleep(5000);
			} else {
				sa.assertTrue(false, "Not Able to click on save button");
				log(LogStatus.FAIL, "Not Able to click on save button", YesNo.Yes);
			}
		}

	}

	/**
	 * @author Azhar Alam
	 * @param driver
	 * @param userName
	 * @param recordType
	 * @param timeOut
	 * @return true if Record Type Setting changed
	 */
	public boolean changeRecordTypeSetting(WebDriver driver, String userName, String recordType, int timeOut) {

		switchToDefaultContent(driver);
		switchToFrame(driver, 60, getSetUpPageIframe(120));
		ThreadSleep(5000);
		boolean flag = false;
		;
		String xpath = "";
		xpath = "//th//a[text()='" + userName + "']";
		WebElement ele = FindElement(driver, xpath, userName, action.SCROLLANDBOOLEAN, timeOut);
		ele = isDisplayed(driver, ele, "visibility", timeOut, userName);
		if (click(driver, ele, userName.toString(), action.BOOLEAN)) {
			log(LogStatus.INFO, "able to click on " + userName, YesNo.No);
			ThreadSleep(6000);
			CommonLib.refresh(driver);
			ThreadSleep(6000);
			switchToFrame(driver, 60, getSetUpPageIframe(120));

			/*
			 * xpath = "//select[@id='p5']"; ele = FindElement(driver, xpath,
			 * "Record dropdown", action.SCROLLANDBOOLEAN, timeOut);
			 * scrollDownThroughWebelement(driver, ele, "Record dropdown");
			 * ThreadSleep(1000); if (selectVisibleTextFromDropDown(driver, ele, recordType,
			 * recordType)) { log(LogStatus.INFO, "selected default record Type : " +
			 * recordType, YesNo.No); ThreadSleep(2000); if (clickUsingJavaScript(driver,
			 * getCreateUserSaveBtn_Lighting(30), "Save Button", action.SCROLLANDBOOLEAN)) {
			 * log(LogStatus.INFO, "clicked on save button for record type settiing",
			 * YesNo.No); ThreadSleep(10000); flag = true; } }
			 */
			xpath = "//*[contains(text(),'" + recordType + "')]/parent::tr//a";
			ele = FindElement(driver, xpath, "Edit Button", action.SCROLLANDBOOLEAN, timeOut);
			if (click(driver, ele, "Edit Button", action.BOOLEAN)) {
				log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);

				ThreadSleep(6000);
				CommonLib.refresh(driver);
				ThreadSleep(6000);
				switchToFrame(driver, 60, getSetUpPageIframe(120));
				xpath = "//select[@id='p5']";
				ele = FindElement(driver, xpath, "Record dropdown", action.SCROLLANDBOOLEAN, timeOut);
				scrollDownThroughWebelement(driver, ele, "Record dropdown");
				ThreadSleep(1000);
				if (selectVisibleTextFromDropDown(driver, ele, recordType, recordType)) {
					log(LogStatus.INFO, "selected default record Type : " + recordType, YesNo.No);
					ThreadSleep(2000);
					if (click(driver, getCreateUserSaveBtn_Lighting(30), "Save Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
						ThreadSleep(10000);
						flag = true;
					} else {
						log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "not able to select default record Type : " + recordType, YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "not able to click on edit button for record type settiing", YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + userName, YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param recordTypeLabel
	 * @param timeOut
	 * @return getRecordTypeLabel
	 */
	public WebElement getRecordTypeLabel(String projectName, String recordTypeLabel, int timeOut) {
		String xpath = "//*[text()='" + recordTypeLabel + "']/..//following-sibling::td//input";
		WebElement ele = isDisplayed(driver, FindElement(driver, xpath, recordTypeLabel, action.BOOLEAN, 10),
				"visibility", 10, recordTypeLabel);
		return ele;
	}

	public WebElement getRecordTypeLabelWithoutEditMode(String projectName, String recordTypeLabel, String checkedValue,
			int timeOut) {
		String xpath = "//*[text()='" + recordTypeLabel + "']/..//following-sibling::td//img[@title='" + checkedValue
				+ "']";
		WebElement ele = isDisplayed(driver, FindElement(driver, xpath, recordTypeLabel, action.BOOLEAN, 10),
				"visibility", 10, recordTypeLabel);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param labelWithValue
	 * @param isMakeAvailable
	 * @param profileForSelection TODO
	 * @param isMakeDefault
	 * @param layOut
	 * @param timeOut
	 * @return true if record type is created for Object
	 */
	public boolean createRecordTypeForObject(String projectName, String[][] labelWithValue, boolean isMakeAvailable,
			String[] profileForSelection, boolean isMakeDefault, String layOut, int timeOut) {
		WebElement ele;
		String label;
		String value;
		boolean flag = false;
		switchToDefaultContent(driver);
		if (click(driver, getRecordTypeNewButton(120), "Record Type New Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Click on Record Type New Button", YesNo.No);
			ThreadSleep(5000);
			CommonLib.refresh(driver);
			ThreadSleep(5000);
			switchToFrame(driver, 20, getSetUpPageIframe(60));
			for (String[] lv : labelWithValue) {
				label = lv[0];
				value = lv[1];
				ele = getRecordTypeLabel(projectName, label, 60);
				ThreadSleep(2000);
				if (label.equals(recordTypeLabel.Active.toString())) {
					if (click(driver, ele, "Active CheckBox", action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on Active CheckBox", YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Active CheckBox", YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on Active CheckBox");
					}
				} else {
					if (sendKeys(driver, ele, value, label, action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to enter " + label, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not Able to enter " + value + " to label " + label, YesNo.Yes);
						sa.assertTrue(false, "Not Able to enter " + value + " to label " + label);
					}

				}
				ThreadSleep(1000);
			}

			if (isMakeAvailable) {
				ele = getMakeAvailableCheckBox(10);
				if (!isSelected(driver, ele, "make available")) {
					if (click(driver, ele, "make Available CheckBox", action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on make Available CheckBox", YesNo.No);
						ThreadSleep(1000);
					} else {
						log(LogStatus.ERROR, "Not Able to Click on make Available CheckBox", YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on make Available CheckBox");
					}
				}
			} else if (!isMakeAvailable && profileForSelection != null) {

				ele = getMakeAvailableCheckBox(10);
				if (isSelected(driver, ele, "make available")) {
					if (click(driver, ele, "make Available CheckBox", action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on make Available CheckBox", YesNo.No);
						ThreadSleep(1000);
					} else {
						log(LogStatus.ERROR, "Not Able to Click on make Available CheckBox", YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on make Available CheckBox");
					}
				}
				for (String profile : profileForSelection) {
					ele = getProfileMakeAvailableCheckbox(profile, 10);
					ThreadSleep(1000);
					if (click(driver, ele, profile + ": profile checkbox", action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on on" + profile + ": profile CheckBox", YesNo.No);
						ThreadSleep(1000);
					} else {
						log(LogStatus.ERROR, "Not Able to Click on" + profile + ": profile CheckBox", YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on" + profile + ": profile CheckBox");
					}

				}

			}

			if (isMakeDefault) {
				ele = getMakeDefaultCheckBoxCheckBox(10);
				if (click(driver, ele, "make Default CheckBox", action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on make Default CheckBox", YesNo.No);
					ThreadSleep(1000);
				} else {
					log(LogStatus.ERROR, "Not Able to Click on make Default CheckBox", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on make Default CheckBox");
				}
			}
			if (click(driver, getCustomFieldNextBtn2(30), "next button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Next button", YesNo.No);
				ThreadSleep(1000);

				if (errorMsgRecordType(7) == null) {
					if (layOut != null) {
						if (selectVisibleTextFromDropDown(driver, getApplyOneLayoutToAllProfiles(120), "Page Layout",
								layOut)) {
							log(LogStatus.INFO, "Select Existing Page Layout drop down " + layOut, YesNo.No);
							ThreadSleep(1000);
						} else {
							log(LogStatus.ERROR,
									"Not able to select value from Existing Page Layout drop down " + layOut,
									YesNo.Yes);
						}
					}
					if (clickUsingJavaScript(driver, getCustomTabSaveBtn(10), "save button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on save Button ", YesNo.No);
						ThreadSleep(10000);
						flag = true;
					} else {
						log(LogStatus.ERROR, "Not Able to Click on save Button ", YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on save Button ");
					}
				} else {
					log(LogStatus.INFO, "Duplicate Record Found: ", YesNo.No);
					flag = true;
				}
			} else {
				log(LogStatus.FAIL, "Not able to click on next button so cannot record Type", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on next button so cannot record Type");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Record Type New Button", YesNo.Yes);
			sa.assertTrue(false, "Not Able to Click on Record Type New Button");

		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param labelWithValue
	 * @param timeOut
	 * @return true if able to click on edit button for Object
	 */
	public boolean editRecordTypeForObject(String projectName, String[][] labelWithValue, int timeOut) {
		WebElement ele;
		String label;
		String value;
		boolean flag = false;
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		CommonLib.refresh(driver);
		ThreadSleep(5000);
		switchToFrame(driver, 60, getSetUpPageIframe(120));
		if (click(driver, getEditButton(environment, "Classic", 10), "edit", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Click on edit Button", YesNo.No);
			try {
				if (isAlertPresent(driver)) {
					Screen screen = new Screen();
					screen.click(".\\AutoIT\\AlertOk.PNG");

				}

			} catch (Exception e1) {
			}
			ThreadSleep(5000);
			CommonLib.refresh(driver);
			ThreadSleep(5000);

			switchToFrame(driver, 60, getSetUpPageIframe(120));
			for (String[] lv : labelWithValue) {
				label = lv[0];
				value = lv[1];
				ele = getRecordTypeLabel(projectName, label, 20);
				// ThreadSleep(2000);
				// try {
				// if (isAlertPresent(driver)) {
				// Screen screen = new Screen();
				// screen.click(".\\AutoIT\\AlertOk.PNG");
				//
				// }
				// } catch (Exception e) {
				// try {
				// if (isAlertPresent(driver)) {
				// Screen screen = new Screen();
				// screen.click(".\\AutoIT\\AlertOk.PNG");
				//
				// }
				// } catch (Exception e1) {
				// }
				// }
				/* driver.switchTo().alert().accept(); */
				if (label.equals(recordTypeLabel.Active.toString())) {
					if (click(driver, ele, "Active CheckBox", action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on Active CheckBox", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Active CheckBox", YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on Active CheckBox");
					}
				} else {

					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].setAttribute('value', arguments[1])", ele, value);

				}

				ThreadSleep(1000);
			}

			if (click(driver, getCreateUserSaveBtn_Lighting(30), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on save button", YesNo.No);
				flag = true;
				ThreadSleep(10000);
				recordTypeVerification(labelWithValue);
			} else {
				log(LogStatus.ERROR, "not able to click on save button", YesNo.Yes);
				sa.assertTrue(false, "not able to click on save button");
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on Edit Button", YesNo.Yes);
			sa.assertTrue(false, "Not Able to Click on Edit Button");
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param labelWithValue
	 */
	public boolean recordTypeVerification(String[][] labelWithValue) {
		String xpath = "";
		WebElement ele;
		boolean flag = false;
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		CommonLib.refresh(driver);
		ThreadSleep(5000);
		switchToFrame(driver, 60, getSetUpPageIframe(120));
		for (String[] labelValue : labelWithValue) {
			// xpath = "//*[text()='" + labelValue[0] +
			// "']/..//following-sibling::td[text()='" + labelValue[1] + "']";
			// xpath = "//*[text()='" + labelValue[0] +
			// "']/..//following-sibling::td/img[@title='" + labelValue[1] + "']";
			xpath = "//*[text()='" + labelValue[0] + "']/..//*[@title='" + labelValue[1] + "' or text()='"
					+ labelValue[1] + "']";

			ele = FindElement(driver, xpath, labelValue[0] + " with Value " + labelValue[1], action.BOOLEAN, 10);
			if (ele != null) {
				log(LogStatus.PASS, labelValue[0] + " with Value " + labelValue[1] + " verified", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, labelValue[0] + " with Value " + labelValue[1] + " not verified", YesNo.Yes);
				sa.assertTrue(false, labelValue[0] + " with Value " + labelValue[1] + " not verified");
			}
		}
		return flag;
	}

	/**
	 * @author Ravi Kumar
	 * @param labelWithValue
	 */
	public boolean verifyFieldLabelWithDataType(String[] labelValue) {

		boolean flag = false;
		String xpath = "";
		WebElement ele;

		xpath = "//*[text()='" + labelValue[0] + "']/../../..//td//span[text()='" + labelValue[1] + "']";
		ele = FindElement(driver, xpath, labelValue[0] + " with data type " + labelValue[1], action.BOOLEAN, 10);
		if (ele != null) {
			flag = true;
			log(LogStatus.PASS, "Field label " + labelValue[0] + " with data type " + labelValue[1] + " verified",
					YesNo.No);
		} else {
			// result.add("Field label "+labelValue[0]+" with data type "+labelValue[1]+"
			// not verified");
			log(LogStatus.ERROR, "Field label " + labelValue[0] + " with data type " + labelValue[1] + " not verified",
					YesNo.Yes);
			sa.assertTrue(false, "Field label " + labelValue[0] + " with data type " + labelValue[1] + " not verified");
		}

		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param field
	 * @return API name of String
	 */
	public String returnAPINameOfField(String projectName, String field) {
		String xpath = "//span[text()='" + field + "']/../../following-sibling::td[1]/span";
		WebElement ele = isDisplayed(driver, FindElement(driver, xpath, field, action.SCROLLANDBOOLEAN, 10),
				"visibility", 10, field);
		if (ele != null)
			return ele.getText();
		return "";

	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param label
	 * @param timeOut
	 * @return label iput box Element
	 */
	public WebElement getLabelInputBoxwithCommonXpath(String projectName, String label, int timeOut) {
		String xpath = "//*[text()='" + label + "']/..//following-sibling::td//input";
		WebElement ele = isDisplayed(driver, FindElement(driver, xpath, label, action.BOOLEAN, 10), "visibility", 10,
				label);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param labelWithValue
	 * @param timeOut
	 * @return true if custom object created successfully
	 */
	public boolean createCustomObject(String projectName, String[][] labelWithValue, int timeOut) {
		WebElement ele;
		String label;
		String value;
		boolean flag = false;
		switchToFrame(driver, 60, getSetUpPageIframe(120));
		for (String[] lv : labelWithValue) {
			label = lv[0];
			value = lv[1];
			ele = getLabelInputBoxwithCommonXpath(projectName, label, 20);
			ThreadSleep(2000);
			if (sendKeys(driver, ele, value, label, action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to enter " + label, YesNo.No);
				ThreadSleep(1000);
			} else {
				log(LogStatus.ERROR, "Not Able to enter " + value + " to label " + label, YesNo.Yes);
				sa.assertTrue(false, "Not Able to enter " + value + " to label " + label);
			}
		}
		flag = false;
		if (click(driver, getCustomTabSaveBtn(10), "save button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Click on save Button ", YesNo.No);
			ThreadSleep(10000);
			flag = true;
		} else {
			log(LogStatus.ERROR, "Not Able to Click on save Button ", YesNo.Yes);
			sa.assertTrue(false, "Not Able to Click on save Button ");
		}

		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @param projectName
	 * @param objectName
	 * @param ObjecttoAddedOnTab
	 * @param styleType
	 * @param parentId
	 * @return true if object added to Tab
	 */
	public boolean addObjectToTab(String environment, String mode, String projectName, object objectName,
			String ObjecttoAddedOnTab, String styleType, String parentId) {
		WebElement ele = null;
		boolean flag = false;
		if (searchStandardOrCustomObject(environment, mode, objectName)) {
			log(LogStatus.PASS, "object searched : " + objectName.toString(), YesNo.No);

			ThreadSleep(5000);
			switchToDefaultContent(driver);
			switchToFrame(driver, 60, getSetUpPageIframe(120));
			ThreadSleep(5000);
			if (click(driver, getCustomObjectTabNewBtn(40), "New", action.BOOLEAN)) {
				log(LogStatus.PASS, "clicked on new button ", YesNo.No);
				;
				switchToFrame(driver, 60, getSetUpPageIframe(120));
				if (selectVisibleTextFromDropDown(driver, getObjectDropDown(120), ObjecttoAddedOnTab,
						ObjecttoAddedOnTab)) {
					log(LogStatus.INFO, "selected OBJECT : " + ObjecttoAddedOnTab, YesNo.No);
					if (tabStyleSelector(styleType, parentId)) {
						log(LogStatus.INFO, "selected style : " + styleType, YesNo.No);
						ThreadSleep(1000);
						for (String window : driver.getWindowHandles()) {
							if (!window.equals(parentId)) {
								try {
									driver.switchTo().window(window);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}

						switchToDefaultContent(driver);
						switchToFrame(driver, 60, getSetUpPageIframe(120));
						if (click(driver, getCustomFieldNextBtn2(60), "next button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "Clicked on Next button", YesNo.No);
							// switchToFrame(driver, 20, getSetUpPageIframe(120));
							if (click(driver, getCustomFieldNextBtn2(120), "next button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Clicked on Next button", YesNo.No);
								// switchToFrame(driver, 20, getSetUpPageIframe(120));
								if (click(driver, getCustomTabSaveBtn(projectName, 120), "save button",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, "Click on save Button & succeessfully add " + ObjecttoAddedOnTab
											+ " to tab", YesNo.No);
									ThreadSleep(10000);
									flag = true;
								} else {
									log(LogStatus.ERROR,
											"Not Able to Click on save Button so cannot add add custom object on Tab",
											YesNo.Yes);
									sa.assertTrue(false,
											"Not Able to Click on save Button so cannot add add custom object on Tab");
								}

							} else {
								log(LogStatus.FAIL,
										"Not able to click on next button so cannot add custom object on Tab",
										YesNo.Yes);
								sa.assertTrue(false,
										"Not able to click on next button so cannot add custom object on Tab");
							}

						} else {
							log(LogStatus.FAIL, "Not able to click on next button so cannot add custom object on Tab",
									YesNo.Yes);
							sa.assertTrue(false, "Not able to click on next button so cannot add custom object on Tab");
						}

					} else {
						log(LogStatus.ERROR, "not able to select style : " + styleType, YesNo.Yes);
						sa.assertTrue(false, "not able to select style : " + styleType);
					}
				} else {
					log(LogStatus.ERROR, "not able to select OBJECT : " + objectName, YesNo.Yes);
					sa.assertTrue(false, "not able to select OBJECT : " + objectName);
				}

			} else {
				log(LogStatus.FAIL, "Not able to click on new button so cannot add custom object on Tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on new button so cannot add custom object on Tab");

			}

		} else {
			log(LogStatus.FAIL,
					"Not able to search object " + objectName.toString() + " so cannot add custom object on Tab",
					YesNo.Yes);
			sa.assertTrue(false,
					"Not able to search object " + objectName.toString() + " so cannot add custom object on Tab");
		}
		switchToDefaultContent(driver);
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param styleType
	 * @param parentId
	 * @return true if able to select style for tab
	 */
	public boolean tabStyleSelector(String styleType, String parentId) {
		boolean flag = false;
		boolean windowFlag = false;
		if (click(driver, getTabObjectLookUpIcon(30), "tab Style look up icon", action.BOOLEAN)) {
			WebElement ele = null;
			String currentWindow = driver.getWindowHandle();
			log(LogStatus.INFO, "Tab window " + currentWindow, YesNo.No);
			log(LogStatus.INFO, "First window " + parentId, YesNo.No);
			for (String window : driver.getWindowHandles()) {
				if (!window.equals(currentWindow) && !window.equals(parentId)) {
					driver.switchTo().window(window);
					windowFlag = true;
				}
			}

			if (windowFlag) {
				try {
					ele = FindElement(driver, "//*[text()='" + styleType + "']", styleType, action.SCROLLANDBOOLEAN,
							20);
					ele = isDisplayed(driver, ele, "visibility", 20, styleType);
					if (click(driver, ele, styleType, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "selected " + styleType, YesNo.No);
						flag = true;
					} else {
						log(LogStatus.ERROR, "cannot select " + styleType, YesNo.Yes);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				flag = true;
				// driver.close();
				try {
					driver.switchTo().window(currentWindow);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				log(LogStatus.ERROR, "No new window is open so cannot select value " + styleType + " from look up",
						YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on tab Style look up icon", YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param attachmentPath
	 * @param imgName
	 * @return true if upload/update img
	 */
	public boolean updateEdgeIcon(String projectName, String attachmentPath, String imgName) {
		boolean flag = false;
		attachmentPath = attachmentPath + imgName;
		if (click(driver, getImgClearButton(projectName, 10), "Img Clear button", action.BOOLEAN)) {
			log(LogStatus.INFO, "Click on Img Clear Button", YesNo.No);
			ThreadSleep(5000);

			if (sendKeys(driver, getuploadPhotoButton(projectName, 10), attachmentPath, attachmentPath,
					action.BOOLEAN)) {
				ThreadSleep(500);
				log(LogStatus.INFO, "send value to " + attachmentPath, YesNo.No);
				if (click(driver, getCustomTabSaveBtn(projectName, 10), " Save Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on Save Button", YesNo.No);
					ThreadSleep(5000);
					flag = true;
					// String xpath =
					// "//div[@aria-labelledby='appImageLabel']//span[text()='"+imgName+"']";
					// WebElement ele= FindElement(driver, xpath, imgName, action.BOOLEAN, 30);
					// if (ele!=null) {
					// log(LogStatus.INFO,"File uploaded successfully "+imgName,YesNo.No);
					// flag=true;
					// } else {
					// sa.assertTrue(false, "File not uploaded "+imgName);
					// log(LogStatus.FAIL,"File not uploaded "+imgName,YesNo.Yes);
					// }
				} else {
					sa.assertTrue(false, "Not Able to Click on Save Button");
					log(LogStatus.FAIL, "Not Able to Click on Save Button", YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to send value to " + attachmentPath);
				log(LogStatus.FAIL, "Not Able to send value to " + attachmentPath, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Img Clear Button");
			log(LogStatus.FAIL, "Not Able to Click on Img Clear Button", YesNo.Yes);
		}
		return flag;

	}

	/**
	 * @author Azhar Alam
	 * @param driver
	 * @param userName
	 * @param LabelswithCheck
	 * @param timeOut
	 * @return true if able to change permission for particular object for
	 *         particular type for particular user
	 */
	public boolean permissionChangeForUserONObject(WebDriver driver, String userName, String[][] LabelswithCheck,
			int timeOut) {

		switchToDefaultContent(driver);
		switchToFrame(driver, 60, getSetUpPageIframe(120));
		boolean flag = false;
		;
		String xpath = "";
		xpath = "//th//a[text()='" + userName + "']";
		ThreadSleep(2000);
		WebElement ele = FindElement(driver, xpath, userName, action.SCROLLANDBOOLEAN, timeOut);
		ele = isDisplayed(driver, ele, "visibility", timeOut, userName);
		if (clickUsingJavaScript(driver, ele, userName.toString(), action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "able to click on " + userName, YesNo.No);
			switchToDefaultContent(driver);
			switchToFrame(driver, 60, getSetUpPageIframe(120));
			xpath = "//*[@id='topButtonRow']//input[@name='edit']";
			ele = FindElement(driver, xpath, "Edit Button", action.SCROLLANDBOOLEAN, timeOut);
			ThreadSleep(5000);
			if (clickUsingJavaScript(driver, ele, "Edit Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "able to click on edit button", YesNo.No);
				switchToDefaultContent(driver);
				switchToFrame(driver, 60, getSetUpPageIframe(120));
				String OnObject = "";
				String permission = "";
				for (String[] strings : LabelswithCheck) {

					OnObject = strings[0];
					permission = strings[1];
					xpath = "//*[text()='" + OnObject + "']/following-sibling::*//td/input[contains(@title,'"
							+ permission + "')]";
					ele = FindElement(driver, xpath, OnObject + " with permission " + permission,
							action.SCROLLANDBOOLEAN, timeOut);
					CommonLib.ThreadSleep(4000);
					if (clickUsingJavaScript(driver, ele, OnObject + " with permission " + permission,
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on checkbox " + permission + " for " + OnObject, YesNo.No);
						CommonLib.ThreadSleep(4000);

					} else {
						log(LogStatus.ERROR, "Not Able clicked on checkbox " + permission + " for " + OnObject,
								YesNo.Yes);
						sa.assertTrue(false,
								permission + " permission not change for " + userName + " on object " + OnObject);
						log(LogStatus.FAIL,
								permission + " permission not change for " + userName + " on object " + OnObject,
								YesNo.Yes);

					}

				}
				switchToDefaultContent(driver);
				switchToFrame(driver, 60, getSetUpPageIframe(120));
				CommonLib.ThreadSleep(5000);
				if (clickUsingJavaScript(driver, getCreateUserSaveBtn_Lighting(30), "Save Button",
						action.SCROLLANDBOOLEAN)) {
					flag = true;
					log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);

					ThreadSleep(12000);
					switchToDefaultContent(driver);
					CommonLib.refresh(driver);
				} else {
					log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "not able to click on edit button", YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + userName, YesNo.Yes);
		}
		return flag;
	}

	public boolean permissionChangeForUserONObject(WebDriver driver, PermissionType pt, String userName,
			String[][] LabelswithCheck, int timeOut) {

		ThreadSleep(10000);
		switchToDefaultContent(driver);
		switchToFrame(driver, 60, getSetUpPageIframe(60));
		ThreadSleep(5000);
		boolean flag = false;
		;
		String xpath = "";
		xpath = "//th//a[text()='" + userName + "']";
		WebElement ele = FindElement(driver, xpath, userName, action.SCROLLANDBOOLEAN, timeOut);
		ele = isDisplayed(driver, ele, "visibility", timeOut, userName);
		if (clickUsingJavaScript(driver, ele, userName.toString(), action.BOOLEAN)) {
			log(LogStatus.INFO, "able to click on " + userName, YesNo.No);
			ThreadSleep(10000);
			switchToDefaultContent(driver);
			ThreadSleep(5000);
			switchToFrame(driver, 60, getSetUpPageIframe(60));
			xpath = "//*[@id='topButtonRow']//input[@name='edit']";
			ele = FindElement(driver, xpath, "Edit Button", action.SCROLLANDBOOLEAN, timeOut);
			ThreadSleep(5000);
			if (clickUsingJavaScript(driver, ele, "Edit Button", action.BOOLEAN)) {
				log(LogStatus.INFO, "able to click on edit button", YesNo.No);
				ThreadSleep(10000);
				switchToDefaultContent(driver);
				ThreadSleep(5000);
				switchToFrame(driver, 60, getSetUpPageIframe(60));
				String OnObject = "";
				String permission = "";
				for (String[] strings : LabelswithCheck) {
					OnObject = strings[0];
					permission = strings[1];
					xpath = "//*[text()='" + OnObject + "']/following-sibling::*//td/input[contains(@title,'"
							+ permission + "')]";
					ele = FindElement(driver, xpath, OnObject + " with permission " + permission,
							action.SCROLLANDBOOLEAN, timeOut);
					if (pt == PermissionType.givePermission) {
						if (!isSelected(driver, ele, OnObject + " with permission " + permission)) {
							if (click(driver, ele, OnObject + " with permission " + permission,
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on checkbox " + permission + " for " + OnObject, YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able clicked on checkbox " + permission + " for " + OnObject,
										YesNo.Yes);
								sa.assertTrue(false, permission + " permission not change for " + userName
										+ " on object " + OnObject);
								log(LogStatus.FAIL, permission + " permission not change for " + userName
										+ " on object " + OnObject, YesNo.Yes);

							}
						} else {
							log(LogStatus.INFO, "already permission present " + OnObject, YesNo.Yes);

						}
					} else {
						if (isSelected(driver, ele, OnObject + " with permission " + permission)) {
							if (click(driver, ele, OnObject + " with permission " + permission,
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on checkbox " + permission + " for " + OnObject, YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able clicked on checkbox " + permission + " for " + OnObject,
										YesNo.Yes);
								sa.assertTrue(false, permission + " permission not change for " + userName
										+ " on object " + OnObject);
								log(LogStatus.FAIL, permission + " permission not change for " + userName
										+ " on object " + OnObject, YesNo.Yes);

							}
						}
					}
				}

				if (click(driver, getCreateUserSaveBtn_Lighting(30), "Save Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
					ThreadSleep(10000);
					flag = true;
					ThreadSleep(5000);
				} else {
					log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "not able to click on edit button", YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + userName, YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param driver
	 * @param userLastName
	 * @param userFirstName
	 * @param timeOut
	 * @return true if able to click on Edit button for CRM User
	 */
	public boolean clickOnEditBtnForCRMUser(WebDriver driver, String userLastName, String userFirstName, int timeOut) {
		boolean flag = false;
		switchToDefaultContent(driver);
		ThreadSleep(10000);
		switchToFrame(driver, 60, getSetUpPageIframe(60));
		WebElement ele = FindElement(driver, "//a[text()='" + userLastName + "," + " " + userFirstName + "']",
				userLastName + ", " + userFirstName, action.SCROLLANDBOOLEAN, 10);
		if (ele != null) {
			if (click(driver, ele, userLastName + ", " + userFirstName, action.SCROLLANDBOOLEAN)) {
				ThreadSleep(10000);
				switchToFrame(driver, 40, getSetUpPageIframe(40));
				ele = getEditButton("", "Classic", timeOut);
				if (click(driver, ele, "Edit icon", action.SCROLLANDBOOLEAN)) {
					switchToDefaultContent(driver);
					ThreadSleep(10000);
					switchToFrame(driver, 20, getSetUpPageIframe(20));
					flag = true;
				} else {
					appLog.info("Not able to click on edit icon");
				}

			} else {
				appLog.info("Not able to click on " + userLastName + ", " + userFirstName);
			}
		} else {
			appLog.info("Element is not present");
		}
		return flag;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param object
	 * @param at
	 * @return drop down object
	 */
	public WebElement selectObjectDropdownOnSharingSettings(String projectName, String object, accessType at) {
		object = object.replace("_", " ");
		int i = 0;
		if (at == accessType.InternalUserAccess)
			i = 1;
		else if (at == accessType.ExternalUserAccess)
			i = 2;
		String xpath = "(//*[text()='" + object + "']/following-sibling::*//select)[" + i + "]";
		WebElement ele = FindElement(driver, xpath, "dropdown", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "visibility", 10, "dropdown");
	}

	/**
	 * @author Azhar Alam
	 * @param driver
	 * @param labelsWithValues
	 * @param timeOut
	 * @return true if global action created successfully
	 */
	public boolean createNewAction(WebDriver driver, String[][] labelsWithValues, int timeOut) {
		boolean flag = false;
		switchToDefaultContent(driver);
		ThreadSleep(10000);
		switchToFrame(driver, 60, getSetUpPageIframe(60));
		ThreadSleep(5000);
		if (click(driver, getNewActionBtnNewBtn(timeOut), "New Action Button", action.BOOLEAN)) {
			log(LogStatus.INFO, "click on New Action Button", YesNo.No);
			switchToDefaultContent(driver);
			ThreadSleep(10000);
			switchToFrame(driver, 60, getSetUpPageIframe(60));
			ThreadSleep(5000);
			String label = "";
			String value = "";
			String xpath = "";
			WebElement ele;
			for (String[] labelWithValue : labelsWithValues) {
				label = labelWithValue[0].replace("_", " ");
				value = labelWithValue[1];
				xpath = "//*[text()='" + label + "']/../following-sibling::td//input";
				ele = FindElement(driver, xpath, label, action.BOOLEAN, 10);
				if (ele != null) {
					log(LogStatus.INFO, "Label not found : " + label, YesNo.No);
					if (sendKeys(driver, ele, value, label, action.BOOLEAN)) {
						log(LogStatus.INFO, "enter value " + value + " to Label : " + label, YesNo.No);
						flag = true;
					} else {
						log(LogStatus.ERROR, "Not Able to enter value " + value + " to Label : " + label, YesNo.Yes);
						sa.assertTrue(false, "Not Able to enter value " + value + " to Label : " + label);
					}
				} else {
					log(LogStatus.ERROR, "Label not found : " + label, YesNo.Yes);
					sa.assertTrue(false, "Label not found : " + label);
				}
			}

			if (click(driver, getSaveButton(20), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Click on Save Button ", YesNo.No);

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on Save Button");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on New Action Button", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on New Action Button");
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param driver
	 * @param actionName
	 * @param labelsWithValues
	 * @param timeOut          create predefined value for Global Action
	 */
	public void createPredefinedValueForGlobalAction(WebDriver driver, String actionName, String[][] labelsWithValues,
			int timeOut) {
		boolean flag = false;
		switchToDefaultContent(driver);
		ThreadSleep(10000);
		switchToFrame(driver, 60, getSetUpPageIframe(60));
		ThreadSleep(5000);
		String xpath = "";
		WebElement ele;
		String label = "";
		String value = "";
		xpath = "//a[text()='" + actionName + "']";
		ele = FindElement(driver, xpath, actionName, action.SCROLLANDBOOLEAN, timeOut);
		if (click(driver, ele, actionName, action.BOOLEAN)) {
			log(LogStatus.INFO, "click on " + actionName, YesNo.No);
			for (String[] labelWithValue : labelsWithValues) {

				switchToDefaultContent(driver);
				ThreadSleep(20000);
				switchToFrame(driver, 60, getSetUpPageIframe(60));
				ThreadSleep(5000);
				label = labelWithValue[0].replace("_", " ");
				value = labelWithValue[1];
				log(LogStatus.INFO, "Going to fill value : " + value + " for label : " + label, YesNo.No);
				if (click(driver, getpredefinedFieldValuesNewButtonn(timeOut), "Predefined Field Values New Button",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "click on Predefined Field Values New Button", YesNo.No);

					switchToDefaultContent(driver);
					ThreadSleep(20000);
					switchToFrame(driver, 60, getSetUpPageIframe(60));
					ThreadSleep(5000);

					if (click(driver, getSelectFieldName(10), label,
							action.SCROLLANDBOOLEAN)/*
													 * selectVisibleTextFromDropDown(driver,getSelectFieldName(10),
													 * label,label)
													 */) {
						log(LogStatus.INFO, "selected visbible text from the Field Name dropdown " + label, YesNo.No);
						ThreadSleep(2000);
						xpath = "//select[@id='ColumnEnumOrId']//*[text()='" + label + "']";
						ele = FindElement(driver, xpath, label, action.SCROLLANDBOOLEAN, timeOut);
						click(driver, ele, label, action.SCROLLANDBOOLEAN);
						ThreadSleep(2000);
						if (sendKeys(driver, getFormulaValueTextArea(20), value, "Formula Field : " + value,
								action.BOOLEAN)) {
							log(LogStatus.INFO, "enter value for " + label, YesNo.Yes);

							if (click(driver, getSaveButton(20), "Save Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button for  " + label, YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  " + label, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  " + label);
							}

						} else {
							log(LogStatus.ERROR, "Not Able to enter value for " + label, YesNo.Yes);
							sa.assertTrue(false, "Not Able to enter value for " + label);
						}

					} else {
						log(LogStatus.ERROR, "Not able to select visbible text from the Field Name dropdown " + label,
								YesNo.Yes);
						sa.assertTrue(false, "Not able to select visbible text from the Field Name dropdown " + label);
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Predefined Field Values New Button : " + actionName,
							YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Predefined Field Values New Button : " + actionName);
				}

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + actionName, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + actionName);
		}
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param labelWithValue
	 * @param existingPageLayout
	 * @param src
	 * @param trgt
	 * @param timeOut
	 * @return true if page layout created successfully
	 */
	public boolean createPageLayout(String projectName, String[][] labelWithValue, String existingPageLayout,
			String src, String trgt, int timeOut) {
		WebElement ele;
		String label;
		String value;
		boolean flag = false;
		switchToDefaultContent(driver);
		if (click(driver, getPageLayoutNewButton(10), "Page Layout New Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Click on Page Layout New Button", YesNo.No);
			ThreadSleep(5000);
			switchToFrame(driver, 20, getSetUpPageIframe(60));
			for (String[] lv : labelWithValue) {
				label = lv[0];
				value = lv[1];
				ele = getRecordTypeLabel(projectName, "Page Layout Name", 20);
				if (sendKeys(driver, ele, value, label, action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to enter " + label, YesNo.No);
					ThreadSleep(2000);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to enter " + value + " to label " + label, YesNo.Yes);
					sa.assertTrue(false, "Not Able to enter " + value + " to label " + label);
				}
			}
			if (selectVisibleTextFromDropDown(driver, getSelectExistingPageLayout(10), "field accessbility drop down",
					existingPageLayout)) {
				log(LogStatus.INFO, "Select Existing Page Layout drop down " + existingPageLayout, YesNo.No);
				ThreadSleep(1000);

				if (click(driver, pageLayoutSaveButton(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.ERROR, "Click on save Button ", YesNo.No);
					ThreadSleep(5000);
					ThreadSleep(5000);
					switchToFrame(driver, 20, getSetUpPageIframe(60));
					if ((src != null) && (trgt != null)) {
						if (dragDropOnPageLayout(src, trgt)) {
							log(LogStatus.INFO, "Able to dragNDrop " + src + " at " + trgt + " location", YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR, "Not able to dragNDrop " + src + " at " + trgt + " location",
									YesNo.Yes);
							sa.assertTrue(false, "Not able to dragNDrop " + src + " at " + trgt + " location");
						}
					}
					switchToDefaultContent(driver);
					switchToFrame(driver, 20, getSetUpPageIframe(60));
					if (click(driver, getPageLayoutSaveBtn(object.Apps, 10), "save", action.BOOLEAN)) {
						log(LogStatus.ERROR, "Clicked on page layout save Button ", YesNo.Yes);

					} else {
						log(LogStatus.ERROR, "Not Able to Click on page layout save Button ", YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on page layout save Button ");
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on save Button ", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on save Button ");
				}

			} else {
				log(LogStatus.ERROR,
						"Not able to select value from Existing Page Layout drop down " + existingPageLayout,
						YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on Page Layout New Button", YesNo.Yes);
			sa.assertTrue(false, "Not Able to Click on Page Layout New Button");
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param src
	 * @param trgt
	 * @return true if successfully drag n drop & save layout
	 */
	public boolean dragDropOnPageLayout(String src, String trgt) {
		boolean flag = false;
		sendKeys(driver, getquickFindSearch(10), src, src, action.BOOLEAN);
		WebElement targetElement = FindElement(driver, "//span[@class='labelText'][text()='" + trgt + "']", "",
				action.BOOLEAN, 20);
		WebElement ele = isDisplayed(driver,
				FindElement(driver, " //span[text()='" + src + "']", "", action.BOOLEAN, 20), "visibility", 20,
				src + " field");
		if (ele != null) {
			WebElement ele1 = isDisplayed(driver, targetElement, "visibility", 20, trgt + " field");
			ThreadSleep(5000);
			if (ele1 != null) {
				if (dragNDropField(driver, ele, ele1)) {
					ThreadSleep(5000);
					appLog.info("Successfully dragNDrop " + src + " at " + trgt + " location");
					if (FindElement(driver, "//span[@class='labelText'][text()='" + src + "']", "", action.BOOLEAN,
							20) != null) {
						appLog.info("successfully verified drag and drop of " + src);
						if (click(driver, getPageLayoutSaveBtn(object.Apps, 30), "page layouts save button",
								action.SCROLLANDBOOLEAN)) {
							appLog.info("clicked on save button");
							flag = true;
						} else {
							appLog.error(
									"Not able to click on Save button cannot save pagelayout dragged object or section");
						}
					} else {
						appLog.error("Not able to dragNDrop " + src + " at " + trgt + " location");
					}
					appLog.info("Successfully dragNDrop " + src + " at " + trgt + " location");
				} else {
					appLog.error("Not able to dragNDrop " + src + " at " + trgt + " location");
				}
			} else {
				appLog.error(trgt + " location is not visible so cannot dragNDrop " + src + " at location " + trgt);
			}
		} else {
			appLog.error(src + " is not visible so cannot dragNdrop " + src);
		}
		return flag;
	}

	public WebElement VFPagePreviewLink(String projectName, String page) {
		String xpath = "//a[text()='" + page + "']/../preceding-sibling::td//img[contains(@title,'Preview')]/..";

		WebElement ele = FindElement(driver, xpath, "vfpage preview link", action.SCROLLANDBOOLEAN, 10);
		scrollDownThroughWebelement(driver, ele, "vfpage preview link");
		ThreadSleep(2000);
		return isDisplayed(driver, ele, "visibility", 10, "vfpage preview link");

	}

	/**
	 * @author Akul Bhutani
	 * @param environment
	 * @param mode
	 * @param objectName
	 * @return true/false
	 * @description this method is used to search object on setup page by entering
	 *              on textbox and click
	 */
	public boolean searchAndClickOnAddUtilityItem(String environment, String mode, String objectName) {
		String index = "[1]";
		String o = objectName.toString().replace("_", " ");

		ThreadSleep(3000);
		if (sendKeys(driver, getSearchIconOnUtilityItem(10), o, o, action.BOOLEAN)) {

			ThreadSleep(2000);
			String xpath = "//span//span[text()='" + o + "']";
			if (click(driver, FindElement(driver, xpath, objectName.toString(), action.BOOLEAN, 10),
					objectName.toString(), action.BOOLEAN)) {
				return true;
			} else {
				log(LogStatus.ERROR, "could not click on " + objectName, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "quick search textbox not visible", YesNo.Yes);
		}

		return false;
	}

	public boolean ClickAndRemoveUtilityItem(String environment, String mode, String objectName) {
		String index = "[1]";
		String o = objectName.toString().replace("_", " ");

		ThreadSleep(3000);
		String xpath = "//span[text()='" + o + "']";
		WebElement ele = FindElement(driver, xpath, o, action.BOOLEAN, 60);
		if (click(driver, ele, o, action.BOOLEAN)) {
			ThreadSleep(2000);
			if (click(driver, getRemoveUtilityTheList(20), o + " Remove Button", action.BOOLEAN)) {
				return true;
			} else {
				log(LogStatus.ERROR, "could not click Remove Button on " + o, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "quick search textbox not visible", YesNo.Yes);
		}

		return false;
	}

	public int getCountOfColumnRecordType(String projectName, String recordType) {
		String xpath = "//table[@id='plaHeaderTable']//div[@title='" + recordType + "']/../preceding-sibling::th";
		List<WebElement> ele = FindElements(driver, xpath, "no of columns before");
		return ele.size();
	}

	public WebElement clickOnRecordTypePageLayout(String projectName, String profile, int loc) {
		String xpath = "//table[@id='plaBodyTable']//td[text()='" + profile + "']/following-sibling::td[" + loc + "]";
		WebElement ele = FindElement(driver, xpath, "payge layout table element", action.BOOLEAN, 20);
		return isDisplayed(driver, ele, "visibility", 10, "record type");
	}

	public boolean updateCreatedCustomFieldforFormula(String environment, String mode, object objectName,
			ObjectFeatureName objectLeftSideActions, String fieldLabelName, String updatedFieldName) {

		WebElement ele = null;
		tabCustomObj = tabCustomObj;
		if (searchStandardOrCustomObject(environment, mode, objectName)) {
			log(LogStatus.PASS, "object searched : " + objectName.toString(), YesNo.No);
			ThreadSleep(5000);
			String xpath = "//div[@data-aura-class='uiScroller']//a[text()='" + objectName.toString() + "']";
			if (objectName == object.Custom_Object) {
				xpath = "//div[@data-aura-class='uiScroller']//a[text()='" + tabCustomObj.toString() + "']";
			} else {
				xpath = "//div[@data-aura-class='uiScroller']//a[text()='" + objectName.toString() + "']";
			}

			ele = FindElement(driver, xpath, objectName.toString() + " xpath", action.SCROLLANDBOOLEAN, 30);
			if (ele != null) {
				if (click(driver, ele, objectName.toString() + " xpath ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "clicked on object Name " + objectName, YesNo.No);
					ThreadSleep(5000);
					xpath = "//div[@id='setupComponent']/div[@class='setupcontent']//ul/li/a[@data-list='"
							+ objectLeftSideActions + "']";
					ele = FindElement(driver, xpath, objectLeftSideActions + " xpath", action.SCROLLANDBOOLEAN, 20);
					if (click(driver, ele, objectLeftSideActions + " xpath ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on object side action :  " + objectLeftSideActions, YesNo.No);
						ThreadSleep(5000);

						if (sendKeys(driver, getsearchTextboxFieldsAndRelationships(10), fieldLabelName + Keys.ENTER,
								"search text box", action.BOOLEAN)) {
							log(LogStatus.PASS, "search " + fieldLabelName, YesNo.No);
							ThreadSleep(2000);
							xpath = "//span[text()='" + fieldLabelName + "']/..";
							ele = FindElement(driver, xpath, fieldLabelName + " xpath", action.SCROLLANDBOOLEAN, 20);
							ThreadSleep(2000);
							if (clickUsingJavaScript(driver, ele, fieldLabelName + " xpath ",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "clicked on object name :  " + fieldLabelName, YesNo.No);
								ThreadSleep(5000);
								if (switchToFrame(driver, 30, getSetUpPageIframe(60))) {
									ThreadSleep(1000);

									if (click(driver, getEditButtonOfCreatedFieldAndRelationShip(10),
											fieldLabelName + " edit button ", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.PASS,
												"clicked on edit button of object name :  " + fieldLabelName, YesNo.No);
										ThreadSleep(5000);
										switchToFrame(driver, 30, getSetUpPageIframe(60));
										ThreadSleep(2000);
										JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
										// set the text
										jsExecutor.executeScript("arguments[0].value='" + updatedFieldName + "'",
												getFieldLabelTextBox(30));

										log(LogStatus.PASS,
												"passed value in field label using Java script in text box : "
														+ updatedFieldName,
												YesNo.No);
										ThreadSleep(2000);
										//
										if (click(driver, getViewAccessbilityDropDownSaveButton(30),
												"save button for custom field", action.BOOLEAN)) {
											log(LogStatus.PASS, "clicked on save button", YesNo.No);
											ThreadSleep(5000);

											return true;

										} else {
											log(LogStatus.FAIL,
													"Not able to click on save button so cannot create custom field "
															+ objectName,
													YesNo.Yes);
										}

									} else {
										log(LogStatus.PASS,
												"not able to click on edit button of object name " + fieldLabelName,
												YesNo.Yes);
									}

								} else {
									log(LogStatus.FAIL,
											"Not able to switch in setup page frame so cannot on edit button of obejct",
											YesNo.Yes);
								}

							} else {
								log(LogStatus.PASS, "not able to click on object name " + fieldLabelName, YesNo.Yes);
							}
						} else {
							log(LogStatus.PASS, "not able to search " + fieldLabelName, YesNo.Yes);
						}

					} else {
						log(LogStatus.FAIL, "Not able to click on object side action " + objectLeftSideActions
								+ " so cannot add custom object ", YesNo.Yes);
					}

				} else {
					log(LogStatus.FAIL,
							"Not able to click on object Name " + objectName + " so cannot add custom object ",
							YesNo.Yes);
				}
			} else {
				log(LogStatus.FAIL,
						"Not able to found object : " + objectName.toString() + " so cannot add custom object",
						YesNo.Yes);
			}
		} else {
			log(LogStatus.FAIL, "Not able to search object " + objectName.toString() + " so cannot add custom object",
					YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return false;
	}

	public boolean updateFieldLabelInOverridePage(WebDriver driver, String fieldName, String UpdatedfieldName,
			action action) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		WebElement ele;
		WebElement ele2;
		String fieldLabelOverride = "//div[text()='" + fieldName + "']/../following-sibling::td[1]";
		String masterFieldLabel = "//div[text()='" + fieldName + "']";

		ThreadSleep(2000);
		boolean status = false;
		int count = 10;
		ele2 = FindElement(driver, masterFieldLabel, "", action.SCROLLANDBOOLEAN, 10);

		do {
			clickUsingJavaScript(driver, setup.getOverrideSetupFieldNextBtn(20), "override field next button",
					action.SCROLLANDBOOLEAN);
			log(LogStatus.INFO, "Successfully click on override next button going to find field label:" + fieldName
					+ " on next page", YesNo.No);
			ThreadSleep(2000);
			ele2 = FindElement(driver, masterFieldLabel, "", action.SCROLLANDBOOLEAN, 10);
			count++;
		} while (!setup.getOverrideSetupFieldNextBtn(20).getAttribute("class").contains("disabled") && ele2 == null
				&& count < 5);

		if (ele2 != null) {
			ele = FindElement(driver, fieldLabelOverride, fieldName, action.SCROLLANDBOOLEAN, 10);
			ThreadSleep(2000);

			if (doubleClickUsingAction(driver, ele)) {
				log(LogStatus.INFO, "going for edit override field label of field:" + fieldName, YesNo.No);
				ThreadSleep(2000);
				Actions ac = new Actions(driver);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				// js.executeScript("arguments[0].setAttribute(arguments[1],arguments[2])",
				// ele,"Value","");
				doubleClickUsingAction(driver, ele);
				ac.moveToElement(ele).sendKeys(UpdatedfieldName).sendKeys(Keys.ENTER).build().perform();
				log(LogStatus.INFO, "Pass value:" + UpdatedfieldName + " to override field label of field:" + fieldName,
						YesNo.No);
				ThreadSleep(2000);
				if (click(driver, setup.getPageLayoutSaveBtn(object.Global_Actions, 10), Buttons.Save.toString(),
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Successfully click on override save button", YesNo.No);
					status = true;
					return true;
				} else {
					log(LogStatus.FAIL,
							"Not able to  click on save button name so cannot update field name" + fieldName,
							YesNo.Yes);
					sa.assertTrue(false, "Not able to  click on fsave button so cannot update field name" + fieldName);

				}

			} else {
				log(LogStatus.FAIL, "Not able to double click on field name so cannot update field name" + fieldName,
						YesNo.Yes);
				sa.assertTrue(false, "Not able to double click on field name so cannot update field name" + fieldName);
			}

		} else {
			log(LogStatus.INFO, "Successfully click on override next button going to find field label:" + fieldName
					+ " on next page", YesNo.No);

		}

		switchToDefaultContent(driver);
		return false;

	}

	public WebElement getValuesElementAtFieldRelationShip(String projectName, String value) {
		String xpath = "//h3[text()='Values']//ancestor::div/following-sibling::div//table//td[text()='" + value + "']";
		WebElement ele = FindElement(driver, xpath, "PickList Value : " + value, action.BOOLEAN, 20);
		return isDisplayed(driver, ele, "visibility", 10, "PickList Value : " + value);
	}

	public void createFieldsForCustomObject(String projectName, String[][] labelAndValues) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object Marketing Event");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object Marketing Event",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object Marketing Event");
			}
			ThreadSleep(3000);

			for (String[] objects : labelAndValues) {
				String[][] valuesandLabel = { { objects[2], objects[3] } };

				if (setup.addCustomFieldforFormula(environment, mode, objects[4],
						ObjectFeatureName.FieldAndRelationShip, objects[0], objects[1], valuesandLabel, null, null)) {
					log(LogStatus.PASS, "Field Object is created for :" + objects[1], YesNo.No);
				} else {
					log(LogStatus.PASS, "Field Object is not created for :" + objects[1], YesNo.Yes);
					sa.assertTrue(false, "Field Object is not created for :" + objects[1]);
				}
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		} else {
			log(LogStatus.ERROR,
					"Not able to click on setup link so cannot create Fields Objects for custom object Marketing Event",
					YesNo.Yes);
			sa.assertTrue(false,
					"Not able to click on setup link so cannot create Fields Objects for custom object Marketing Event");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	public boolean addCustomFieldforFormula(String environment, String mode, String objectName,
			ObjectFeatureName objectLeftSideActions, String dataType, String fieldLabelName,
			String[][] labelsWithValues, String formulaReturnType, String formulaText) {
		WebElement ele = null;
		tabCustomObj = tabCustomObj;
		if (searchStandardOrCustomObject(environment, mode, objectName)) {
			log(LogStatus.PASS, "object searched : " + objectName.toString(), YesNo.No);
			ThreadSleep(5000);
			String xpath = "//div[@data-aura-class='uiScroller']//a[text()='" + objectName.toString() + "']";
			if (objectName == object.Custom_Object.toString()) {
				xpath = "//div[@data-aura-class='uiScroller']//a[text()='" + tabCustomObj.toString() + "']";
			} else {
				xpath = "//div[@data-aura-class='uiScroller']//a[text()='" + objectName.toString() + "']";
			}

			ele = FindElement(driver, xpath, objectName.toString() + " xpath", action.SCROLLANDBOOLEAN, 30);
			if (ele != null) {
				if (click(driver, ele, objectName.toString() + " xpath ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "clicked on object Name " + objectName, YesNo.No);
					ThreadSleep(5000);
					xpath = "//div[@id='setupComponent']/div[@class='setupcontent']//ul/li/a[@data-list='"
							+ objectLeftSideActions + "']";
					ele = FindElement(driver, xpath, objectLeftSideActions + " xpath", action.SCROLLANDBOOLEAN, 20);
					if (click(driver, ele, objectLeftSideActions + " xpath ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on object side action :  " + objectLeftSideActions, YesNo.No);
						ThreadSleep(5000);
						if (click(driver, getCustomFieldNewButton(30), "new button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on new button", YesNo.No);
							ThreadSleep(1000);
							if (switchToFrame(driver, 30, getNewCustomFieldFrame(objectName.toString(), 30))) {
								if (click(driver, getNewCustomFieldDataTypeOrFormulaReturnType(dataType, 30),
										"data type radio button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, dataType + " : data type radio button ", YesNo.No);
									if (click(driver, getCustomFieldNextBtn(30), "next button",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.PASS, "Clicked on Step 1 Next button", YesNo.No);
										ThreadSleep(5000);
										if (dataType.equalsIgnoreCase("Lookup Relationship")
												|| dataType.equalsIgnoreCase("Master-Detail Relationship")) {
											String relatedTovalue = "";
											if (labelsWithValues[0][1] != null) {
												for (String[] objects : labelsWithValues) {
													System.err.println(objects[0]);
													if (objects[0] == "Related_To") {
														System.err.println(objects[1]);
														relatedTovalue = objects[1];
														break;
													}
												}
												if (!relatedTovalue.isEmpty()) {
													if (selectVisibleTextFromDropDown(driver,
															getRelatedToDropDownList(10), "getRelatedToDropDownList",
															relatedTovalue)) {
														log(LogStatus.PASS,
																"select related to drop down value : " + relatedTovalue,
																YesNo.No);
														if (click(driver, getCustomFieldNextBtn(30), "next button",
																action.SCROLLANDBOOLEAN)) {
															log(LogStatus.PASS, "Clicked on Step 1 Next button",
																	YesNo.No);
															ThreadSleep(1000);

														} else {
															log(LogStatus.FAIL,
																	"Not able to select to next button so cannot create  object "
																			+ fieldLabelName,
																	YesNo.Yes);
															return false;
														}
													} else {
														log(LogStatus.FAIL,
																"Not able to select to related drop down value : "
																		+ relatedTovalue,
																YesNo.Yes);
														return false;
													}
												} else {
													log(LogStatus.FAIL,
															"drop down value is not present for look relation object so cannot create look object "
																	+ fieldLabelName,
															YesNo.Yes);
													return false;
												}
											} else {
												log(LogStatus.FAIL,
														"related to drop down value is not present for look relation object so cannot create look object "
																+ fieldLabelName,
														YesNo.Yes);
												return false;
											}

										}
										ThreadSleep(1000);
										if (sendKeys(driver, getFieldLabelTextBox(30), fieldLabelName,
												"field label name ", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.PASS,
													"passed value in field label text box : " + fieldLabelName,
													YesNo.No);

											if (dataType.equalsIgnoreCase("Picklist")
													|| dataType.equalsIgnoreCase("Picklist (Multi-Select)")) {
												xpath = "//label[text()='Enter values, with each value separated by a new line']//preceding-sibling::input[@name='picklistType']";
												ele = FindElement(driver, xpath, "", action.BOOLEAN, 5);

												if (click(driver, ele, "Radio Button", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.PASS, "Click on radio button", YesNo.No);
													ThreadSleep(2000);

													xpath = "//textarea[@id='ptext']";
													ele = FindElement(driver, xpath, "", action.BOOLEAN, 5);
													String val = labelsWithValues[0][1];
													String[] data = val.split(",");
													for (String vals : data) {
														try {
															ele.sendKeys(vals);
															ele.sendKeys(Keys.ENTER);
															log(LogStatus.PASS, "Passed Value in "
																	+ labelsWithValues[0][0] + " " + vals, YesNo.No);
														} catch (Exception ex) {
															ex.printStackTrace();
															log(LogStatus.FAIL, "Not able to pass "
																	+ labelsWithValues[0][0] + " " + vals, YesNo.Yes);
														}
													}

												} else {
													log(LogStatus.FAIL, "Not Able to Click on radio button", YesNo.Yes);

												}

											} else if (dataType.equalsIgnoreCase("Currency")) {

												for (String[] labelWithValue : labelsWithValues) {
													xpath = "//label[contains(text(),'" + labelWithValue[0]
															+ "')]/../following-sibling::td//input";
													ele = FindElement(driver, xpath,
															labelWithValue[0] + " " + labelWithValue[1], action.BOOLEAN,
															5);

													String Data = labelWithValue[1];
													String[] val = Data.split(",");

													if (sendKeys(driver, ele, val[0],
															labelWithValue[0] + " " + labelWithValue[1],
															action.SCROLLANDBOOLEAN)) {
														log(LogStatus.PASS,
																"Passed Value in " + labelWithValue[0] + " " + val[0],
																YesNo.No);
													} else {
														log(LogStatus.FAIL,
																"Not able to pass " + labelWithValue[0] + " " + val[0],
																YesNo.Yes);
													}

													xpath = "//label[contains(text(),'Decimal Places')]/../following-sibling::td//input";
													ele = FindElement(driver, xpath,
															labelWithValue[0] + " " + labelWithValue[1], action.BOOLEAN,
															5);

													if (sendKeys(driver, ele, val[1],
															labelWithValue[0] + " " + labelWithValue[1],
															action.SCROLLANDBOOLEAN)) {
														log(LogStatus.PASS,
																"Passed Value in " + labelWithValue[0] + " " + val[1],
																YesNo.No);
													} else {
														log(LogStatus.FAIL,
																"Not able to pass " + labelWithValue[0] + " " + val[1],
																YesNo.Yes);
													}
													ThreadSleep(500);
												}

											}

											else {
												if (labelsWithValues[0][1] != null
														|| labelsWithValues[0][1] == "Length") {
													for (String[] labelWithValue : labelsWithValues) {
														xpath = "//label[contains(text(),'" + labelWithValue[0]
																+ "')]/../following-sibling::td//input";
														ele = FindElement(driver, xpath,
																labelWithValue[0] + " " + labelWithValue[1],
																action.BOOLEAN, 5);
														if (sendKeys(driver, ele, labelWithValue[1],
																labelWithValue[0] + " " + labelWithValue[1],
																action.SCROLLANDBOOLEAN)) {
															log(LogStatus.PASS, "Passed Value in " + labelWithValue[0]
																	+ " " + labelWithValue[1], YesNo.No);
														} else {
															log(LogStatus.FAIL, "Not able to pass " + labelWithValue[0]
																	+ " " + labelWithValue[1], YesNo.Yes);
														}
														ThreadSleep(500);

													}
												}
											}
										}
										if (click(driver, getCustomFieldNextBtn(30), "next button",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.PASS, "Clicked on Step 2 Next button", YesNo.No);
											ThreadSleep(2000);
											// if(sendKeys(driver, getFormulaTextBox(30), formulaText,"formula text
											// area", action.SCROLLANDBOOLEAN)) {
											// log(LogStatus.PASS, "Passed Value in formula Text Box "+formulaText,
											// YesNo.No);
											if (click(driver, getCustomFieldNextBtn(30), "next button",
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.PASS, "Clicked on Step 3 Next button", YesNo.No);
												ThreadSleep(1000);
												// if(click(driver, getCustomFieldNextBtn(30),"next button",
												// action.SCROLLANDBOOLEAN)) {
												// log(LogStatus.PASS, "Clicked on Step 4 Next button", YesNo.No);
												// ThreadSleep(1000);
												click(driver, getCustomFieldNextBtn(10), "next button",
														action.SCROLLANDBOOLEAN);
												ThreadSleep(1000);
												if (click(driver, getCustomFieldSaveBtn(30), "save button",
														action.SCROLLANDBOOLEAN)) {
													log(LogStatus.PASS, "clicked on save button", YesNo.No);
													ThreadSleep(5000);
													switchToDefaultContent(driver);
													return true;

												} else {
													log(LogStatus.FAIL,
															"Not able to click on save button so cannot create custom field "
																	+ objectName,
															YesNo.Yes);
												}

												// }else {
												// log(LogStatus.FAIL, "Not able to click on Step 4 next button so
												// cannot create custom field : "+objectName,YesNo.Yes);
												// }

											} else {
												log(LogStatus.FAIL,
														"Not able to click on Step 3 next button so cannot create custom field : "
																+ objectName,
														YesNo.Yes);
											}

											// }else {
											// log(LogStatus.FAIL, "Not able to click on Step 2 next button so cannot
											// create custom field : "+objectName,YesNo.Yes);
											// }
										} else {
											log(LogStatus.FAIL,
													"Not able to enter value in field label text box : "
															+ fieldLabelName + " so cannot create custom field",
													YesNo.Yes);
										}
									} else {
										log(LogStatus.FAIL,
												"Not able to click on Step 1 next button so cannot create custom field",
												YesNo.Yes);
									}
								} else {
									log(LogStatus.FAIL, "Not able to click on data type radio button " + dataType
											+ " so cannot create custom field", YesNo.Yes);
								}
							} else {
								log(LogStatus.FAIL, "Not able to switch in " + objectName
										+ " new object frame so cannot add custom object", YesNo.Yes);
							}
						} else {
							log(LogStatus.FAIL, "Not able to click on New button so cannot add custom field in "
									+ objectName.toString(), YesNo.Yes);
						}

					} else {
						log(LogStatus.FAIL, "Not able to click on object side action " + objectLeftSideActions
								+ " so cannot add custom object ", YesNo.Yes);
					}

				} else {
					log(LogStatus.FAIL,
							"Not able to click on object Name " + objectName + " so cannot add custom object ",
							YesNo.Yes);
				}
			} else {
				log(LogStatus.FAIL,
						"Not able to found object : " + objectName.toString() + " so cannot add custom object",
						YesNo.Yes);
			}
		} else {
			log(LogStatus.FAIL, "Not able to search object " + objectName.toString() + " so cannot add custom object",
					YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return false;
	}

	public boolean searchStandardOrCustomObject(String environment, String mode, String objectName) {
		String index = "[1]";
		String o = objectName.toString().replaceAll("_", " ");
		if (objectName == object.Global_Actions.toString() || objectName == object.Activity_Setting.toString()
				|| objectName == object.App_Manager.toString() || objectName == object.Lightning_App_Builder.toString()
				|| objectName == object.Profiles.toString() || objectName == object.Override.toString()
				|| objectName == object.Tabs.toString() || objectName == object.Users.toString()
				|| objectName == object.Sharing_Settings.toString()) {
			if (objectName == object.Global_Actions.toString() || objectName == object.Tabs.toString()
					|| objectName == object.Users.toString()) {
				index = "[2]";
			}
			ThreadSleep(3000);
			if (sendKeys(driver, getQucikSearchInSetupPage(10), o, o, action.BOOLEAN)) {

				ThreadSleep(2000);
				if (click(driver, FindElement(driver, "(//mark[text()='" + o + "'])" + index, objectName.toString(),
						action.BOOLEAN, 10), objectName.toString(), action.BOOLEAN)) {
					return true;
				} else {
					log(LogStatus.ERROR, "could not click on " + objectName, YesNo.Yes);

				}
			} else {
				log(LogStatus.ERROR, "quick search textbox not visible", YesNo.Yes);
			}
			return false;
		}
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			if (sendKeys(driver, getQucikSearchInSetupPage(30), objectName.toString(),
					"quick search text box in setup page", action.SCROLLANDBOOLEAN)) {
				appLog.info("passed value in serach text box: " + objectName);
				return true;
			} else {
				appLog.error("Not able to search object in classic : " + objectName);
			}
		} else {
			ThreadSleep(10000);
			if (objectName == object.Create.toString()) {
				String xpath = "//a[@title='Create Menu']/span[text()='" + objectName.toString() + "']";
				if (click(driver, FindElement(driver, xpath, objectName.toString(), action.BOOLEAN, 10),
						"create Custom Object", action.BOOLEAN)) {
					appLog.info("clicked on " + objectName);
					ThreadSleep(2000);
					xpath = "//a[@title='Custom Object']//span[text()='Custom Object']";
					if (click(driver, FindElement(driver, xpath, "create Custom Object", action.BOOLEAN, 10),
							"create Custom Object", action.BOOLEAN)) {
						appLog.info("clicked on custom object");
						return true;
					} else {
						appLog.error("Not able to click on custom object link");
					}

				} else {
					appLog.error("Not able to click on create icon");
				}
			} else if (click(driver, getObjectManager_Lighting(30), "object manager tab", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on object manager tab");
				if (objectName == object.Custom_Object.toString()) {
					if (sendKeys(driver, getQuickSearchInObjectManager_Lighting(30), tabCustomObj,
							"quick search text box in lighting", action.SCROLLANDBOOLEAN)) {
						appLog.info("passed value in quick search text box: " + tabCustomObj);
						return true;
					} else {
						appLog.error("Not able to search object in lighting : " + tabCustomObj);
					}
				}
				if (sendKeys(driver, getQuickSearchInObjectManager_Lighting(30), objectName.toString(),
						"quick search text box in lighting", action.SCROLLANDBOOLEAN)) {
					appLog.info("passed value in quick search text box: " + objectName.toString());
					return true;
				} else {
					appLog.error("Not able to search object in lighting : " + objectName.toString());
				}
			} else {
				appLog.error(
						"Not able to click on object manager tab so cannot search object: " + objectName.toString());
			}
		}
		return false;
	}

	/**
	 * @author Sourabh Kumar
	 * @param objectName
	 * @param objectFeatureName
	 * @param permissionType
	 * @param fieldLabel
	 * @param profileName
	 * @return true if able to give/Remove Object Permission From Object Manager
	 */
	public boolean giveAndRemoveObjectPermissionFromObjectManager(object objectName,
			ObjectFeatureName objectFeatureName, String fieldLabel, PermissionType permissionType, String profileName) {
		boolean flag = false;
		WebElement ele = null;
		if (searchStandardOrCustomObject(environment, mode, objectName)) {
			log(LogStatus.INFO, "click on Object : " + objectName, YesNo.No);
			ThreadSleep(2000);
			if (clickOnObjectFeature(environment, mode, objectName, objectFeatureName)) {
				log(LogStatus.INFO, "Clicked on feature : " + objectFeatureName, YesNo.No);
				ThreadSleep(1000);
				if (sendKeys(driver, getQuickSearchInObjectManager_Lighting(50), fieldLabel, "search text box",
						action.BOOLEAN)) {
					String xpath = "//a//span[text()='" + fieldLabel + "']/..";
					ele = isDisplayed(driver, FindElement(driver, xpath, "field set label text", action.BOOLEAN, 3),
							"visibility", 3, "field set label text");
					if (ele != null) {
						if (click(driver, ele, "field label text link", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on field label " + fieldLabel, YesNo.No);
							switchToFrame(driver, 50, getFieldAndRelationShipFrame(50));
							ThreadSleep(4000);
							if (click(driver,
									getObjectEditOrSetFieldSecurityOrViewFieldAccessbilityBtn(
											"View Field Accessibility", 50),
									"view field accessbility button xpath", action.BOOLEAN)) {
								log(LogStatus.INFO, "clicked on view field accessbility of field label : " + fieldLabel,
										YesNo.No);
								ThreadSleep(2000);
								switchToFrame(driver, 50, getFieldAndRelationShipFrame(50));
								ThreadSleep(5000);
								if (selectVisibleTextFromDropDown(driver, getFieldAccessbilityDropDown(50),
										"field accessbility drop down", fieldLabel)) {
									log(LogStatus.INFO, "select field label accessbility drop down " + fieldLabel,
											YesNo.No);
									ThreadSleep(1000);
									if (clickUsingJavaScript(driver, getfieldAccessOptionLink(profileName, 10),
											"profile link name", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "clicked on " + profileName + " link", YesNo.No);
										CommonLib.switchToDefaultContent(driver);
										switchToFrame(driver, 50, getFieldAndRelationShipFrame(50));
										if (permissionType.toString().equals("givePermission")) {
											if (!CommonLib.isSelected(driver, getFieldLevelSecurityVisibleCheckBox(40),
													"Field-Level Security checkbox")) {
												if (click(driver, getFieldLevelSecurityVisibleCheckBox(40), "check box",
														action.BOOLEAN)) {
													log(LogStatus.INFO, "Clicked on field level security check box",
															YesNo.No);
													ThreadSleep(5000);
													if (clickUsingJavaScript(driver, getsaveButtonHeader(50),
															"save button", action.BOOLEAN)) {
														log(LogStatus.INFO, "save button", YesNo.No);
														flag = true;

													} else {
														log(LogStatus.ERROR,
																"Not able to click on save button field accessbility of field label "
																		+ fieldLabel + " in object " + objectName
																		+ " so cannot " + permissionType,
																YesNo.Yes);
														flag = false;
													}
												} else {
													log(LogStatus.ERROR,
															"Not able to click on visible field accessbility of field label "
																	+ fieldLabel + " in object " + objectName
																	+ " so cannot " + permissionType,
															YesNo.Yes);
													flag = false;
												}
											} else {
												log(LogStatus.INFO, "Permission is already given", YesNo.No);
												flag = true;
											}
										} else if (permissionType.toString().equals("removePermission")) {
											if (CommonLib.isSelected(driver, getFieldLevelSecurityVisibleCheckBox(40),
													"Field-Level Security checkbox")) {
												if (click(driver, getFieldLevelSecurityVisibleCheckBox(40), "check box",
														action.BOOLEAN)) {
													log(LogStatus.INFO, "Clicked on field level security check box",
															YesNo.No);
													ThreadSleep(5000);
													if (clickUsingJavaScript(driver, getsaveButtonHeader(50),
															"save button", action.BOOLEAN)) {
														log(LogStatus.INFO, "save button", YesNo.No);
														flag = true;

													} else {
														log(LogStatus.ERROR,
																"Not able to click on save button field accessbility of field label "
																		+ fieldLabel + " in object " + objectName
																		+ " so cannot " + permissionType,
																YesNo.Yes);
														flag = false;
													}
												} else {
													log(LogStatus.ERROR,
															"Not able to click on visible field accessbility of field label "
																	+ fieldLabel + " in object " + objectName
																	+ " so cannot " + permissionType,
															YesNo.Yes);
													flag = false;
												}
											} else {
												log(LogStatus.INFO, "Permission is already removed", YesNo.No);
												flag = true;
											}

										}

										else {
											log(LogStatus.ERROR,
													" Not able to click on profile link from view field accessbility of field label "
															+ fieldLabel + " in object " + objectName + " so cannot "
															+ permissionType,
													YesNo.Yes);
											flag = false;
										}
									} else {
										log(LogStatus.ERROR,
												"Not able to select value from view field accessbility of field label "
														+ fieldLabel + " in object " + objectName + " so cannot "
														+ permissionType,
												YesNo.Yes);
										flag = false;
									}
								} else {
									log(LogStatus.ERROR,
											"Not able to click on view field accessbility of field label " + fieldLabel
													+ " in object " + objectName + " so cannot " + permissionType,
											YesNo.Yes);
									flag = false;
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on field label " + fieldLabel + " in object "
										+ objectName + " so cannot " + permissionType, YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Search field label " + fieldLabel + " is not found in object "
									+ objectName + " so cannot " + permissionType, YesNo.Yes);
							flag = false;
						}
					} else {
						log(LogStatus.ERROR, "Not able to search field label " + fieldLabel + " in object " + objectName
								+ " so cannot " + permissionType, YesNo.Yes);
						flag = false;
					}

				} else {
					log(LogStatus.FAIL,
							"Not able to found object : " + objectName.toString() + " so cannot " + permissionType,
							YesNo.Yes);
					flag = false;
				}
			} else {
				log(LogStatus.FAIL,
						"Not able to search object " + objectName.toString() + " so cannot " + permissionType,
						YesNo.Yes);
				flag = false;
			}

		}

		return flag;
	}

	/*******************************************************
	 * Activity Association
	 ******************************/
	/**
	 * @author Sourabh Kumar
	 * @param email
	 * @param userLicense
	 * @param userProfile
	 * @return true/false
	 * @description this method is used to edit the PE User details
	 */
	public boolean EditPEUser(String email, String labelName, HTMLTAG tag, String value) {
		boolean flag = false;
		WebElement ele = null;
		if (click(driver, getHomeTab(30), "home tab", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on the home tab");
			if (CommonLib.checkElementVisibility(driver, getuserarialextendedicon(20), "User Tab arial Extended", 20)) {
				if (click(driver, getExpandUserIcon(30), "expand User Icon", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on user expand icon");
				} else {
					log(LogStatus.ERROR, "Not able to click on expand user icon button", YesNo.Yes);
					flag = false;
				}
			}
			if (click(driver, getUsersLink(30), "User Link", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on users link");
				switchToFrame(driver, 20, getSetUpPageIframe(20));
				CommonLib.ThreadSleep(3000);
				try {
					ele = new WebDriverWait(driver, 50)
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='" + email
									+ "']/parent::td//preceding-sibling::td[@class='actionColumn']//a[text()='Edit']")));
				} catch (Exception ex) {
					ex.printStackTrace();
					log(LogStatus.ERROR, "Could not found the Element of the edit button", YesNo.Yes);
					flag = false;
				}
				if (click(driver, ele, "Edit Button", action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on the edit button against " + email);
					switchToDefaultContent(driver);
					switchToFrame(driver, 50, getuserEditPageIframe(50));
					ThreadSleep(3000);

					if (tag.toString().equals("select")) {
						try {
							ele = new WebDriverWait(driver, 50)
									.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[text()='"
											+ labelName + "']/parent::td//following-sibling::td//select")));
						} catch (Exception ex) {
							ex.printStackTrace();
							log(LogStatus.ERROR, "Could not found the Element of the Select tag", YesNo.Yes);
							flag = false;
						}

						if (CommonLib.selectVisibleTextFromDropDown(driver, ele, labelName + " Dropdown list", value)) {
							log(LogStatus.INFO, value + " has been selected from the " + labelName, YesNo.No);
						} else {
							log(LogStatus.ERROR, "Could not select the value from the drop down list", YesNo.Yes);
							flag = false;
						}

					} else if (tag.toString().equals("input")) {

						try {
							ele = new WebDriverWait(driver, 50)
									.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[text()='"
											+ labelName + "']/parent::td//following-sibling::td//input")));
						} catch (Exception ex) {
							ex.printStackTrace();
							log(LogStatus.ERROR, "Could not found the Element of the input tag", YesNo.Yes);
							flag = false;
						}
						if (CommonLib.sendKeys(driver, ele, value, labelName + " input ", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " has been entered in the " + labelName, YesNo.No);
						}

					}
					CommonLib.ThreadSleep(1000);
					CommonLib.scrollDownThroughWebelementInCenter(driver, geteditPageSaveButton(50), "save Button");
					CommonLib.ThreadSleep(2000);
					if (CommonLib.clickUsingJavaScript(driver, geteditPageSaveButton(50), "save Button",
							action.BOOLEAN)) {
						CommonLib.ThreadSleep(20000);
						appLog.info("Clicked on the save button against " + email);
						flag = true;
						CommonLib.switchToDefaultContent(driver);

					} else {
						log(LogStatus.ERROR, "Not able to click on the save button against " + email, YesNo.Yes);
						flag = false;
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on the edit button against " + email, YesNo.Yes);
					flag = false;
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the User link button", YesNo.Yes);
				flag = false;
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on the home tab button", YesNo.Yes);
			flag = false;
		}
		return flag;
	}

	/**
	 * @author Sourabh Kumar
	 * @param objectName
	 * @param objectFeatureName
	 * @param fieldLabel
	 * @param permissionType
	 * @param profileName
	 * @param recordType
	 * @return true if able to give/Remove Object Permission From Object Manager
	 * @description This method is used to give the page layout level Permission.
	 */
	public boolean giveAndRemoveObjectPermissionFromObjectManager(object objectName,
			ObjectFeatureName objectFeatureName, String fieldLabel, PermissionType permissionType, String profileName,
			RecordType recordType) {
		boolean flag = false;
		WebElement ele = null;
		int recordindex = 0;
		if (recordType.toString() != null) {

			switch (recordType.toString()) {
			case "Master": {
				recordindex = 1;
				break;
			}
			case "Advisor": {
				recordindex = 2;
				break;
			}
			case "Company": {
				recordindex = 3;
				break;
			}
			case "Fund_Manager": {
				recordindex = 4;
				break;
			}
			case "Fund_Manager_Fund": {
				recordindex = 5;
				break;
			}
			case "Indivisual_investor": {
				recordindex = 6;
				break;
			}
			case "Institution": {
				recordindex = 7;
				break;
			}
			case "Intermediary": {
				recordindex = 8;
				break;
			}
			case "Lender": {
				recordindex = 9;
				break;
			}
			case "Limited_Partner": {
				recordindex = 10;
				break;
			}
			case "Portfolio_Company": {
				recordindex = 11;
				break;
			}

			}
		}

		if (searchStandardOrCustomObject(environment, mode, objectName)) {
			log(LogStatus.INFO, "click on Object : " + objectName, YesNo.No);
			ThreadSleep(2000);
			if (clickOnObjectFeature(environment, mode, objectName, objectFeatureName)) {
				log(LogStatus.INFO, "Clicked on feature : " + objectFeatureName, YesNo.No);
				ThreadSleep(2000);
				if (sendKeys(driver, getQuickSearchInObjectManager_Lighting(50), fieldLabel, "search text box",
						action.BOOLEAN)) {
					String xpath = "//span[text()='" + fieldLabel + "']/..";
					ele = isDisplayed(driver, FindElement(driver, xpath, "field set label text", action.BOOLEAN, 3),
							"visibility", 3, "field set label text");
					if (ele != null) {
						if (click(driver, ele, "field label text link", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on field label " + fieldLabel, YesNo.No);
							switchToFrame(driver, 50, getFieldAndRelationShipFrame(50));
							ThreadSleep(6000);
							if (CommonLib.clickUsingJavaScript(driver,
									getObjectEditOrSetFieldSecurityOrViewFieldAccessbilityBtn(
											"View Field Accessibility", 50),
									"view field accessbility button xpath", action.BOOLEAN)) {
								log(LogStatus.INFO, "clicked on view field accessbility of field label : " + fieldLabel,
										YesNo.No);
								ThreadSleep(2000);
								switchToFrame(driver, 50, getFieldAndRelationShipFrame(50));
								ThreadSleep(5000);
								if (selectVisibleTextFromDropDown(driver, getFieldAccessbilityDropDown(50),
										"field accessbility drop down", fieldLabel)) {
									log(LogStatus.INFO, "select field label accessbility drop down " + fieldLabel,
											YesNo.No);
									ThreadSleep(1000);
									if (clickUsingJavaScript(driver,
											getfieldAccessOptionLinkcell(profileName, recordindex, 10),
											"profile link name", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "clicked on " + profileName + " link", YesNo.No);
										CommonLib.switchToDefaultContent(driver);
										switchToFrame(driver, 50, getFieldAndRelationShipFrame(50));
										ThreadSleep(3000);
										if (permissionType.toString().equals("givePermission")) {
											if (!CommonLib.isSelected(driver, getpageLayourSecurityVisibleCheckBox(40),
													"Field-Level Security checkbox")) {
												if (click(driver, getpageLayourSecurityVisibleCheckBox(40), "check box",
														action.BOOLEAN)) {
													log(LogStatus.INFO, "Clicked on field level security check box",
															YesNo.No);
													ThreadSleep(4000);
													if (clickUsingJavaScript(driver, getsaveButtonHeader(50),
															"save button", action.BOOLEAN)) {
														log(LogStatus.INFO, "save button", YesNo.No);
														ThreadSleep(8000);
														flag = true;

													} else {
														log(LogStatus.ERROR,
																"Not able to click on save button field accessbility of field label "
																		+ fieldLabel + " in object " + objectName
																		+ " so cannot " + permissionType,
																YesNo.Yes);
														flag = false;
													}
												} else {
													log(LogStatus.ERROR,
															"Not able to click on visible field accessbility of field label "
																	+ fieldLabel + " in object " + objectName
																	+ " so cannot " + permissionType,
															YesNo.Yes);
													flag = false;
												}
											} else {
												log(LogStatus.INFO, "Permission is already given", YesNo.No);
												ThreadSleep(2000);
												flag = true;
											}
										} else if (permissionType.toString().equals("removePermission")) {
											if (CommonLib.isSelected(driver, getpageLayourSecurityVisibleCheckBox(40),
													"Field-Level Security checkbox")) {
												if (click(driver, getpageLayourSecurityVisibleCheckBox(40), "check box",
														action.BOOLEAN)) {
													log(LogStatus.INFO, "Clicked on field level security check box",
															YesNo.No);
													ThreadSleep(5000);
													if (clickUsingJavaScript(driver, getsaveButtonHeader(50),
															"save button", action.BOOLEAN)) {
														log(LogStatus.INFO, "save button", YesNo.No);
														ThreadSleep(8000);
														flag = true;

													} else {
														log(LogStatus.ERROR,
																"Not able to click on save button field accessbility of field label "
																		+ fieldLabel + " in object " + objectName
																		+ " so cannot " + permissionType,
																YesNo.Yes);
														flag = false;
													}
												} else {
													log(LogStatus.ERROR,
															"Not able to click on visible field accessbility of field label "
																	+ fieldLabel + " in object " + objectName
																	+ " so cannot " + permissionType,
															YesNo.Yes);
													flag = false;
												}
											} else {
												log(LogStatus.INFO, "Permission is already removed", YesNo.No);
												ThreadSleep(2000);
												flag = true;
											}

										}

										else {
											log(LogStatus.ERROR,
													" Not able to click on profile link from view field accessbility of field label "
															+ fieldLabel + " in object " + objectName + " so cannot "
															+ permissionType,
													YesNo.Yes);
											flag = false;
										}
									} else {
										log(LogStatus.ERROR,
												"Not able to select value from view field accessbility of field label "
														+ fieldLabel + " in object " + objectName + " so cannot "
														+ permissionType,
												YesNo.Yes);
										flag = false;
									}
								} else {
									log(LogStatus.ERROR,
											"Not able to click on view field accessbility of field label " + fieldLabel
													+ " in object " + objectName + " so cannot " + permissionType,
											YesNo.Yes);
									flag = false;
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on field label " + fieldLabel + " in object "
										+ objectName + " so cannot " + permissionType, YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Search field label " + fieldLabel + " is not found in object "
									+ objectName + " so cannot " + permissionType, YesNo.Yes);
							flag = false;
						}
					} else {
						log(LogStatus.ERROR, "Not able to search field label " + fieldLabel + " in object " + objectName
								+ " so cannot " + permissionType, YesNo.Yes);
						flag = false;
					}

				} else {
					log(LogStatus.FAIL,
							"Not able to found object : " + objectName.toString() + " so cannot " + permissionType,
							YesNo.Yes);
					flag = false;
				}
			} else {
				log(LogStatus.FAIL,
						"Not able to search object " + objectName.toString() + " so cannot " + permissionType,
						YesNo.Yes);
				flag = false;
			}

		}

		return flag;
	}

	public boolean fieldDependencies(String fieldName, String controllervalue, String dependentValue,
			ArrayList<String> dependencyField) {
		String xPath = "";
		WebElement ele = null;
		boolean flag = false;

		if (CommonLib.sendKeysAndPressEnter(driver, getfieldandRelationshipQuickSearch(50), fieldName, "Field",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Field value has been passed in " + fieldName, YesNo.No);
			CommonLib.ThreadSleep(6000);
			xPath = "//span[text()='" + fieldName + "']";
			ele = FindElement(driver, xPath, fieldName + " xpath", action.SCROLLANDBOOLEAN, 30);
			if (CommonLib.click(driver, ele, fieldName + " field", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Field" + fieldName, YesNo.No);
				CommonLib.ThreadSleep(7000);

				if (CommonLib.switchToFrame(driver, 50, getSetUpPageIframe(70))) {
					ThreadSleep(2000);
					log(LogStatus.INFO, "sucessfully swithed to the iframe", YesNo.No);

					if (click(driver, getfieldDependenciesNewBtn(40), "Field dependency new button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on field dependency new button", YesNo.No);

						CommonLib.switchToDefaultContent(driver);
						if (CommonLib.switchToFrame(driver, 50, getSetUpPageIframe(70))) {
							ThreadSleep(2000);
							log(LogStatus.INFO, "sucessfully swithed to field dependency iframe", YesNo.No);

							if (CommonLib.selectVisibleTextFromDropDown(driver, getcontrollerDropDown(50),
									"Controller drop down field", controllervalue)) {
								log(LogStatus.INFO, "Source has been selected from the Controller Drop down", YesNo.No);

								if (CommonLib.selectVisibleTextFromDropDown(driver, getdependentDropDown(50),
										"Dependent drop down field", dependentValue)) {
									log(LogStatus.INFO, "Status has been selected from the Dependent Drop down",
											YesNo.No);
									ThreadSleep(2000);
									if (CommonLib.clickUsingJavaScript(driver, getdependencyContinueBtn(40),
											"Field dependency Continue button", action.BOOLEAN)) {
										ThreadSleep(15000);
										log(LogStatus.INFO, "Clicked on field dependency continue button", YesNo.No);
										CommonLib.switchToDefaultContent(driver);
										ThreadSleep(3000);
										if (CommonLib.switchToFrame(driver, 50, getSetUpPageIframe(70))) {
											ThreadSleep(10000);
											log(LogStatus.INFO, "sucessfully swithed to Edit Field Dependency iframe",
													YesNo.No);

											for (int i = 0; i < dependencyField.size(); i++) {

												xPath = "//td[contains(text(),'" + dependencyField.get(i) + "')]";
												ele = FindElement(driver, xPath, dependencyField.get(i), action.BOOLEAN,
														50);
												if (click(driver, ele, dependencyField.get(i) + " value",
														action.BOOLEAN)) {
													log(LogStatus.INFO, "Clicked on " + dependencyField.get(i) + "",
															YesNo.No);

													if (click(driver, getincludeValueBtn(50), "Include value button",
															action.BOOLEAN)) {
														log(LogStatus.INFO, "Clicked on " + dependencyField.get(i)
																+ "Include value button", YesNo.No);

													} else {
														log(LogStatus.ERROR, "Could not click on "
																+ dependencyField.get(i) + " include value btn",
																YesNo.Yes);
													}
												} else {
													log(LogStatus.ERROR,
															"Could not clicked on " + dependencyField.get(i) + "",
															YesNo.Yes);
												}

											}

											if (click(driver, getfooterSaveBtn(50), "save button", action.BOOLEAN)) {
												log(LogStatus.INFO, "Clicked on save button", YesNo.No);

												flag = true;

											} else {
												log(LogStatus.ERROR, "Not able to click on save button", YesNo.Yes);

											}

										} else {
											log(LogStatus.ERROR, "Not able to switch to Edit Field Dependency iframe",
													YesNo.Yes);

										}

									} else {
										log(LogStatus.ERROR, "Not able to click on the dependency continue button",
												YesNo.Yes);

									}

								}

								else {
									log(LogStatus.ERROR, "Not able to select the Status from the Dependent drop down",
											YesNo.Yes);
								}
							}

							else {
								log(LogStatus.ERROR, "Not able to select the Source from the controller drop down",
										YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Not able to switch to field dependency iframe", YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Could not click on the field dependency new button", YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not able to switch to iframe", YesNo.Yes);
				}
			} else {

				log(LogStatus.ERROR, "Clicked on the " + fieldName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "passed the value in the quick searchbox", YesNo.Yes);
		}

		return flag;

	}

	public List<String> DragNDropIfNoDestination(String environment, String mode, object obj,
			ObjectFeatureName objectFeatureName, List<String> layoutName,
			HashMap<String, String> sourceANDDestination) {
		WebElement ele = null;
		List<String> result = new ArrayList<String>();
		boolean flag = false;
		if (searchStandardOrCustomObject(environment, mode, obj)) {
			if (clickOnObjectFeature(environment, mode, obj, objectFeatureName)) {
				for (int i = 0; i < layoutName.size(); i++) {
					if (obj == object.Global_Actions) {
						switchToFrame(driver, 10, getEditPageLayoutFrame_Lighting(20));
						ele = isDisplayed(driver,
								FindElement(driver,
										"//a[text()='" + layoutName.get(i)
												+ "']/../preceding-sibling::td//a[contains(@title,'Layout')]",
										"", action.BOOLEAN, 20),
								"visibility", 20, obj + " page layout link");
					} else {
						if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
							ele = isDisplayed(driver,
									FindElement(driver,
											"//div[@id='LayoutList_body']//tr/th[text()='" + layoutName.get(i)
													+ "']/../td/a[contains(@title,'Edit')]",
											"", action.BOOLEAN, 20),
									"visibility", 20, layoutName.get(i) + " page layout link");
						} else {
							ele = isDisplayed(driver,
									FindElement(driver, "//span[contains(text(),'" + layoutName.get(i) + "')]", "",
											action.BOOLEAN, 20),
									"visibility", 20, layoutName.get(i) + " page layout link");
						}
					}
					if (ele != null) {
						if (click(driver, ele, layoutName.get(i) + " layout name edit icon", action.BOOLEAN)) {
							appLog.info("click on pagelayout " + layoutName.get(i) + " Edit Icon");
							ThreadSleep(20000);
							if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
							}

							Set<String> Sources = sourceANDDestination.keySet();
							Iterator<String> itr = Sources.iterator();
							while (itr.hasNext()) {
								String src = itr.next();
								String trgt = sourceANDDestination.get(src);
								if (PageLabel.Is_Touchpoint.toString().equalsIgnoreCase(src)) {

								}

								else {
									src = src.replace("_", " ");
								}

								if (PageLabel.Is_Touchpoint.toString().equalsIgnoreCase(trgt)) {

								} else {
									trgt = trgt.replace("_", " ");
								}

								// src=src.replace("_", " ");
								// trgt=trgt.replace("_", " ");

								WebElement targetElement = null;
								if (src.split("<break>")[0].contains("Related List")) {
									if (click(driver, FindElement(driver, "//div[text()='Related Lists']", "",
											action.SCROLLANDBOOLEAN, 30), "", action.SCROLLANDBOOLEAN)) {
										if (trgt.split("<break>")[0].equalsIgnoreCase("Above")) {
											trgt = trgt.split("<break>")[trgt.split("<break>").length - 1];
											targetElement = FindElement(driver,
													"//h3[text()='" + trgt
															+ "']/../../../../../../../../preceding-sibling::div[1]",
													"", action.BOOLEAN, 20);
										} else {
											trgt = trgt.split("<break>")[trgt.split("<break>").length - 1];
											targetElement = FindElement(driver,
													"//h3[text()='" + trgt
															+ "']/../../../../../../../../following-sibling::div[1]",
													"", action.BOOLEAN, 20);
										}
										src = src.split("<break>")[src.split("<break>").length - 1];
									} else {
										appLog.error(src + " is not visible so cannot dragNdrop " + src);
										result.add(src + " is not visible so cannot dragNdrop " + src);
									}
									flag = true;
								} else if (src.split("<break>")[0].contains("Mobile")) {
									if (click(driver, FindElement(driver, "//div[text()='Mobile & Lightning Actions']",
											"", action.SCROLLANDBOOLEAN, 30), "", action.SCROLLANDBOOLEAN)) {
										src = src.split("<break>")[1];
										sendKeys(driver, getquickFindSearch(10), src, src, action.BOOLEAN);
										targetElement = FindElement(driver,
												"//div[contains(@id,'item_QuickAction')][text()='" + trgt + "']", "",
												action.BOOLEAN, 20);
										flag = true;
									}
								}

								else {
									sendKeys(driver, getquickFindSearch(10), src, src, action.BOOLEAN);
									/*
									 * targetElement = FindElement(driver, "//span[@class='labelText'][text()='" +
									 * trgt + "']", "", action.BOOLEAN, 20);
									 */
									targetElement = FindElement(driver, "//table[contains(@id,'ext-gen')]//td", "",
											action.BOOLEAN, 20);

								}
								ele = isDisplayed(driver,
										FindElement(driver, " //span[text()='" + src + "']", "", action.BOOLEAN, 20),
										"visibility", 20, src + " field");
								if (ele != null) {
								}

								else
									ele = isDisplayed(driver, FindElement(driver,
											"(//table[@class='troughItems ']//div/div)[3]", "", action.BOOLEAN, 20),
											"visibility", 20, src + " field");

								if (ele != null) {
									WebElement ele1 = isDisplayed(driver, targetElement, "visibility", 20,
											trgt + " field");

									ThreadSleep(5000);
									if (ele1 != null) {
										if (dragNDropField(driver, ele, ele1)) {
											ThreadSleep(5000);
											appLog.info("Successfully dragNDrop " + src + " at " + trgt + " location");
											if (src.equalsIgnoreCase(PageLabel.Convert_to_Portfolio.toString())) {
												if (FindElement(driver,
														"//div[contains(@id,'QuickAction')][text()='" + src + "']", "",
														action.BOOLEAN, 20) != null) {
													appLog.info("successfully verified drag and drop of " + src);
												} else {
													appLog.error("Not able to dragNDrop " + src + " at " + trgt
															+ " location");
													result.add("Not able to dragNDrop " + src + " at " + trgt
															+ " location");
												}

											} else {
												if (FindElement(driver,
														"//span[@class='labelText'][text()='" + src + "']", "",
														action.BOOLEAN, 20) != null) {
													appLog.info("successfully verified drag and drop of " + src);
												} else {
													appLog.error("Not able to dragNDrop " + src + " at " + trgt
															+ " location");
													result.add("Not able to dragNDrop " + src + " at " + trgt
															+ " location");
												}
											}
											appLog.info("Successfully dragNDrop " + src + " at " + trgt + " location");
										} else {
											appLog.error("Not able to dragNDrop " + src + " at " + trgt + " location");
											result.add("Not able to dragNDrop " + src + " at " + trgt + " location");
										}
									} else {
										appLog.error(trgt + " location is not visible so cannot dragNDrop " + src
												+ " at location " + trgt);
										result.add(trgt + " location is not visible so cannot dragNDrop " + src
												+ " at location " + trgt);
									}
								} else {
									appLog.error(src + " is not visible so cannot dragNdrop " + src);
									result.add(src + " is not visible so cannot dragNdrop " + src);
								}

							}

							if (click(driver, getPageLayoutSaveBtn(obj, 30), "page layouts save button",
									action.SCROLLANDBOOLEAN)) {
								appLog.info("clicked on save button");

								if (flag && obj != object.Global_Actions) {
									ThreadSleep(5000);
									click(driver, FindElement(driver, "//button[text()='Yes']", "Yes Button",
											action.BOOLEAN, 30), "", action.SCROLLANDBOOLEAN);

								}
							} else {
								appLog.error(
										"Not able to click on Save button cannot save pagelayout dragged object or section");
								result.add(
										"Not able to click on Save button cannot save pagelayout dragged object or section");
							}
						} else {
							appLog.error("Not able to click on " + layoutName.get(i)
									+ "layout edit icon so cannot dargNdrop.");
							result.add("Not able to click on " + layoutName.get(i)
									+ "layout edit icon so cannot dargNdrop.");
						}

					} else {
						appLog.error(layoutName.get(i) + " Layout name is not visible so cannot click on edit icon");
						result.add(layoutName.get(i) + " Layout name is not visible so cannot click on edit icon");
					}
				}
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					ThreadSleep(5000);
					switchToDefaultContent(driver);

				}
			} else {
				appLog.error(
						"Not able to click on Object feature: " + objectFeatureName + " so cannot dragNdrop source.");
				result.add(
						"Not able to click on Object feature: " + objectFeatureName + " so cannot dragNdrop source.");
			}
		} else {
			appLog.error("Not able to search Object: " + obj + " so cannot dragNdrop source.");
			result.add("Not able to search Object: " + obj + " so cannot dragNdrop source.");
		}

		return result;
	}

	public ArrayList<String> verifyRecordTypeAndActivityStatusOnCompanyObject(ArrayList<String> recordName,
			ArrayList<String> status) {
		ArrayList<String> recordTypeName = new ArrayList<String>();
		ArrayList<String> activityStatus = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		if (CommonLib.checkElementVisibility(driver, getcompanyRecordTypeName(50), "Record type name on company object",
				50)) {
			for (int i = 0; i < getcompanyRecordTypeNamelist().size(); i++) {
				String text = CommonLib.getText(driver, getcompanyRecordTypeNamelist().get(i), "record name",
						action.SCROLLANDBOOLEAN);
				recordTypeName.add(text);

				String active = CommonLib.getText(driver, getcompanyRecordTypeActivityStatus().get(i), "record name",
						action.SCROLLANDBOOLEAN);
				activityStatus.add(active);
			}

			for (int i = 0; i < recordName.size(); i++) {

				if (recordName.get(i).equals(recordTypeName.get(i)) && status.get(i).equals(activityStatus.get(i))) {
					log(LogStatus.INFO, "Record Name: " + recordName.get(i) + " and Activity Status: " + status.get(i)
							+ " has been verified", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Either Record name :" + recordTypeName.get(i) + " or Activity status "
							+ activityStatus.get(i) + " is not matched", YesNo.Yes);
					result.add("Either Record name :" + recordTypeName.get(i) + " or Activity status "
							+ activityStatus.get(i) + " is not matched");
				}
			}
		} else {
			log(LogStatus.ERROR, "Record type name is not visible", YesNo.Yes);

		}
		return result;

	}

	/**
	 * @author Ankur Huria
	 * @param driver
	 * @param userName
	 * @param LabelswithCheck
	 * @param timeOut
	 * @return true if able to change permission for particular object for
	 *         particular type for particular user
	 */
	public boolean permissionChangeOfUserONObject(WebDriver driver, String userName, String[][] LabelswithCheck,
			int timeOut) {

		switchToDefaultContent(driver);
		switchToFrame(driver, 60, getSetUpPageIframe(120));
		boolean flag = false;
		;
		String xpath = "";
		xpath = "//th//a[text()='" + userName + "']";
		ThreadSleep(2000);
		WebElement ele = FindElement(driver, xpath, userName, action.SCROLLANDBOOLEAN, timeOut);
		ele = isDisplayed(driver, ele, "visibility", timeOut, userName);
		if (clickUsingJavaScript(driver, ele, userName.toString(), action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "able to click on " + userName, YesNo.No);
			switchToDefaultContent(driver);
			switchToFrame(driver, 60, getSetUpPageIframe(120));
			xpath = "//*[@id='topButtonRow']//input[@name='edit']";
			ele = FindElement(driver, xpath, "Edit Button", action.SCROLLANDBOOLEAN, timeOut);
			ThreadSleep(5000);
			if (clickUsingJavaScript(driver, ele, "Edit Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "able to click on edit button", YesNo.No);

				String OnObject = "";
				String[] permissions;
				String[] givenOrNot;
				for (String[] strings : LabelswithCheck) {

					switchToDefaultContent(driver);
					switchToFrame(driver, 60, getSetUpPageIframe(120));
					OnObject = strings[0];
					permissions = strings[1].split("<break>", -1);
					givenOrNot = strings[2].split("<break>", -1);

					int loopCount = 0;
					for (String permission : permissions) {
						xpath = "(//*[text()='" + OnObject + "']/following-sibling::*//td/input[contains(@title,'"
								+ permission + "')])[1]";
						ele = FindElement(driver, xpath, OnObject + " with permission " + permission,
								action.SCROLLANDBOOLEAN, timeOut);
						CommonLib.ThreadSleep(4000);

						String checked = CommonLib.getAttribute(driver, ele, "CheckBox", "checked");

						if (givenOrNot[loopCount].equals(PermissionType.givePermission.toString()))

						{

							if (!"true".equals(checked)) {

								if (click(driver, ele, OnObject + " with permission " + permission,
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on checkbox " + permission + " for " + OnObject,
											YesNo.No);
									CommonLib.ThreadSleep(4000);
									if (CommonLib.isAlertPresent(driver)) {
										CommonLib.switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
									}
									CommonLib.ThreadSleep(4000);

								} else {
									log(LogStatus.ERROR,
											"Not Able clicked on checkbox " + permission + " for " + OnObject,
											YesNo.Yes);
									sa.assertTrue(false, permission + " permission not change for " + userName
											+ " on object " + OnObject);
									log(LogStatus.FAIL, permission + " permission not change for " + userName
											+ " on object " + OnObject, YesNo.Yes);

								}

							} else {
								log(LogStatus.INFO, "Not clicked on checkbox " + permission + " for " + OnObject
										+ " as it is already Checked", YesNo.No);

							}
						} else if (givenOrNot[loopCount].equals(PermissionType.removePermission.toString()))

						{

							if ("true".equals(checked)) {

								if (click(driver, ele, OnObject + " with permission " + permission,
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on checkbox " + permission + " for " + OnObject,
											YesNo.No);
									CommonLib.ThreadSleep(4000);
									if (CommonLib.isAlertPresent(driver)) {
										CommonLib.switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
									}
									CommonLib.ThreadSleep(4000);

								} else {
									log(LogStatus.ERROR,
											"Not Able clicked on checkbox " + permission + " for " + OnObject,
											YesNo.Yes);
									sa.assertTrue(false, permission + " permission not change for " + userName
											+ " on object " + OnObject);
									log(LogStatus.FAIL, permission + " permission not change for " + userName
											+ " on object " + OnObject, YesNo.Yes);

								}

							} else {
								log(LogStatus.INFO, "Not clicked on checkbox " + permission + " for " + OnObject
										+ " as it is already UnChecked", YesNo.No);

							}
						}

						else {
							log(LogStatus.ERROR, "Please Provide the correct data for permission given or not",
									YesNo.Yes);
							sa.assertTrue(false, "Please Provide the correct data for permission given or not");
						}
						loopCount++;
					}

				}
				switchToDefaultContent(driver);
				if (CommonLib.isAlertPresent(driver)) {
					CommonLib.switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
				}
				switchToFrame(driver, 60, getSetUpPageIframe(120));
				CommonLib.ThreadSleep(5000);
				if (CommonLib.isAlertPresent(driver)) {
					CommonLib.switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
				}
				if (clickUsingJavaScript(driver, getCreateUserSaveBtn_Lighting(30), "Save Button",
						action.SCROLLANDBOOLEAN)) {
					flag = true;
					log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);

					ThreadSleep(12000);
					switchToDefaultContent(driver);
					CommonLib.refresh(driver);
				} else {
					log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "not able to click on edit button", YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + userName, YesNo.Yes);
		}
		return flag;
	}

	public ArrayList<String> verifyDescriptionOnFirm(ArrayList<String> recordName, ArrayList<String> des) {
		String xPath = "";
		WebElement ele;
		ArrayList<String> Description = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();

		for (int i = 0; i < des.size() - 1; i++) {
			xPath = "//section[@class='related-list-card']//tbody//span[text()='" + recordName.get(i)
					+ "']/ancestor::td/following-sibling::td[1]//span";
			ele = CommonLib.FindElement(driver, xPath, recordName.get(i) + " description", action.SCROLLANDBOOLEAN, 50);
			String text = CommonLib.getText(driver, ele, recordName.get(i) + " description : ",
					action.SCROLLANDBOOLEAN);
			Description.add(text);
		}

		for (int i = 0; i < des.size() - 1; i++) {

			if (Description.get(i).equals(des.get(i))) {
				log(LogStatus.INFO, "Description \"" + des.get(i) + "\" has been verified", YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"Description \"" + des.get(i) + "\"  is not matched with the \"" + Description.get(i) + "\"",
						YesNo.Yes);
				result.add(
						"Description \"" + des.get(i) + "\"  is not matched with the \"" + Description.get(i) + "\"");
			}
		}

		return result;
	}

	public boolean VerifyDefaultRecordTypeForObject(String profileName, String recordTypeName) {
		boolean flag = false;
		String xPath = "";
		WebElement ele = null;
		ThreadSleep(5000);
		if (CommonLib.switchToFrame(driver, 50, getuserProfileIframe(50))) {
			ThreadSleep(5000);
			log(LogStatus.INFO, "Successfully switched to User Profile Iframe", YesNo.No);
			xPath = "//div[@class='bRelatedList']//a[text()='" + profileName + "']";
			ele = CommonLib.FindElement(driver, xPath, profileName + " profile name", action.SCROLLANDBOOLEAN, 50);
			if (CommonLib.clickUsingJavaScript(driver, ele, profileName + " profile name", action.BOOLEAN)) {
				log(LogStatus.INFO, "Successfully clicked on the " + profileName + " profile name", YesNo.No);
				ThreadSleep(12000);
				CommonLib.switchToDefaultContent(driver);
				ThreadSleep(2000);
				if (CommonLib.switchToFrame(driver, 50, getProfileIframe(50))) {
					ThreadSleep(12000);
					log(LogStatus.INFO, "Successfully switched to Profile Iframe", YesNo.No);
					if (CommonLib.clickUsingJavaScript(driver, getRecordTypeEditButton(50), "Record type edit button",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Successfully click on record type edit button", YesNo.No);
						ThreadSleep(12000);
						CommonLib.switchToDefaultContent(driver);
						ThreadSleep(2000);
						if (CommonLib.switchToFrame(driver, 50, geteditRecordTypeIframe(50))) {
							ThreadSleep(12000);
							log(LogStatus.INFO, "Successfully switched to Profile Iframe", YesNo.No);

							String text = CommonLib.getText(driver, getdefaultRecordType(50), "default record type",
									action.SCROLLANDBOOLEAN);
							if (text.equals(recordTypeName)) {
								log(LogStatus.INFO, "Default record company has been verified", YesNo.No);
								flag = true;
							} else {
								log(LogStatus.ERROR, "Default record company is not verified", YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Not able to switched to edit record Iframe", YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on the record type edit button", YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not able to switched to Profile Iframe", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on the " + profileName + " profile name", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to switched to User profile Iframe", YesNo.Yes);
		}

		return flag;

	}

	public ArrayList<String> verifyPicklistOnDifferentRecordType(String data, int timeOut) {
		String xPath = "";
		WebElement ele;
		List<WebElement> elements;

		// ArrayList<String>recordType=new ArrayList<String>();
		// ArrayList<String>fieldName=new ArrayList<String>();
		ArrayList<String> pickListActualValue = new ArrayList<String>();
		ArrayList<String> pickListExpectedValue = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();

		String[] recordTypeFieldNamePickListValue = data.split("<break>");

		for (int i = 0; i < recordTypeFieldNamePickListValue.length; i++) {

			String[] recordTypeFieldName = recordTypeFieldNamePickListValue[i].split("<section>");
			String recordType = recordTypeFieldName[0];

			xPath = "//th[@title='Record Type Label']/ancestor::table//tbody//span[text()='" + recordType + "']";
			ele = CommonLib.FindElement(driver, xPath, recordType + " record type", action.SCROLLANDBOOLEAN, timeOut);
			if (CommonLib.click(driver, ele, recordType + " record type", action.BOOLEAN)) {
				log(LogStatus.INFO, "Successfully clicked on the " + recordType + " record type", YesNo.No);

				ThreadSleep(8000);
				if (CommonLib.switchToFrame(driver, 50, getrecordTypeIframe(50))) {
					ThreadSleep(12000);
					String[] fieldNamePickListValue = recordTypeFieldName[1].split("<picklistValueBreak>");
					String fieldName = fieldNamePickListValue[0];
					xPath = "//th[text()='" + fieldName + "']/preceding-sibling::td/a[text()='Edit']";
					ele = CommonLib.FindElement(driver, xPath, fieldName + " field name", action.SCROLLANDBOOLEAN,
							timeOut);
					if (CommonLib.clickUsingJavaScript(driver, ele, fieldName + " field name", action.BOOLEAN)) {
						log(LogStatus.INFO, "Successfully clicked on the " + fieldName + " field name", YesNo.No);

						ThreadSleep(8000);
						CommonLib.switchToDefaultContent(driver);
						ThreadSleep(2000);
						if (CommonLib.switchToFrame(driver, 50, getrecordTypeIframe(50))) {
							ThreadSleep(12000);
							String[] picklistExpectedValueArray = fieldNamePickListValue[1].split("<f>");
							for (int k = 0; k < picklistExpectedValueArray.length; k++) {
								pickListExpectedValue.add(picklistExpectedValueArray[k]);
							}
							xPath = "//select[@id='duel_select_1']//option";
							elements = CommonLib.FindElements(driver, xPath, "selected value");
							for (int j = 0; j < elements.size(); j++) {
								String value = CommonLib.getText(driver, elements.get(j), fieldName + " field value",
										action.SCROLLANDBOOLEAN);
								pickListActualValue.add(value);
							}

							for (int a = 0; a < pickListExpectedValue.size(); a++) {
								if (pickListActualValue.get(a).equals(pickListExpectedValue.get(a))) {
									log(LogStatus.INFO,
											"Expected picklist : \"" + pickListExpectedValue.get(a)
													+ "\" value has been matched with the Actual picklist \""
													+ pickListActualValue.get(a) + "\" value",
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Expected picklist : \"" + pickListExpectedValue.get(a)
													+ "\" value is not matched with the Actual picklist \""
													+ pickListActualValue.get(a) + "\" value",
											YesNo.Yes);
									result.add("Expected picklist : \"" + pickListExpectedValue.get(a)
											+ "\" value is not matched with the Actual picklist \""
											+ pickListActualValue.get(a) + "\" value");
								}
							}

							CommonLib.switchToDefaultContent(driver);
							ThreadSleep(2000);
							if (CommonLib.click(driver, getrecordTypeObjectManager(50), "Record type object manager",
									action.BOOLEAN)) {
								log(LogStatus.INFO, "Successfully clicked on the Record type object manager", YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not able to click on the Record type object manager", YesNo.Yes);
								result.add("Not able to click on the Record type object manager");
							}
						} else {
							log(LogStatus.ERROR, "Not able to switch to Iframe", YesNo.Yes);
							result.add("Not able to switch to Iframe");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + fieldName + " field name", YesNo.Yes);
						result.add("Not able to click on " + fieldName + " field name");
					}

				} else {
					log(LogStatus.ERROR, "Not able to switch to Iframe", YesNo.Yes);
					result.add("Not able to switch to Iframe");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the " + recordType + " record type button", YesNo.Yes);
				result.add("Not able to click on the " + recordType + " record type button");
			}
		}
		return result;

	}

	public ArrayList<String> verifyPageLayoutAssignment(ArrayList<String> recordTypes, ArrayList<String> userProfile,
			int timeOut) {
		String xPath = "";
		WebElement ele;
		List<WebElement> elements;
		String data = "";
		int num = 0;
		ArrayList<String> result = new ArrayList<String>();
		if (CommonLib.click(driver, getpageLayoutAssignment(timeOut), "Page Layout Assignment button",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Successfully clicked on the Page Layout Assignment button", YesNo.No);

			ThreadSleep(2000);
			if (CommonLib.switchToFrame(driver, timeOut, getpageLayoutIframe(timeOut))) {
				ThreadSleep(12000);
				log(LogStatus.INFO, "successfully switched to page layout iframe", YesNo.No);

				do {
					if (num != 0) {
						if (CommonLib.checkElementVisibility(driver, getnextBtn(timeOut), "Next button", timeOut)) {
							if (CommonLib.click(driver, getnextBtn(timeOut), "Page Layout Assignment button",
									action.BOOLEAN)) {
								ThreadSleep(5000);
								log(LogStatus.INFO, "Successfully clicked on the Next button", YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not able to click on the next button", YesNo.No);
								result.add("Not able to click on the next button");

							}
						}
					}
					xPath = "//table[@id='plaHeaderTable']//th[@class='rtHeader  ']";
					elements = CommonLib.FindElements(driver, xPath, "Header");
					for (int i = 0; i < elements.size(); i++) {
						String text = CommonLib.getText(driver, elements.get(i), xPath, action.BOOLEAN);

						for (int j = 0; j < recordTypes.size(); j++) {
							if (text.equals(recordTypes.get(j))) {

								for (int k = 0; k < userProfile.size(); k++) {

									xPath = "//table[@id='plaBodyTable']//a[text()='" + userProfile.get(k)
											+ "']/parent::td/following-sibling::td[" + (i + 1) + "]";
									ele = CommonLib.FindElement(driver, xPath, "Assignment list", action.BOOLEAN, 20);

									if (ele == null) {
										log(LogStatus.ERROR, "Not able to get the Element of " + text + " for "
												+ userProfile.get(k) + "", YesNo.No);
										result.add("Not able to get the Element of " + text + " for "
												+ userProfile.get(k) + "");
									}
									data = CommonLib.getText(driver, ele, text, action.BOOLEAN);

									if (data.equals(text)) {
										log(LogStatus.INFO, "The Assignment " + data + " has been verified for "
												+ userProfile.get(k) + "", YesNo.No);

									} else {
										log(LogStatus.ERROR, "The Assignment " + data + " is not verified  for "
												+ userProfile.get(k) + "", YesNo.No);
										result.add("The Assignment " + data + " is not verified  for "
												+ userProfile.get(k) + "");
									}

								}
								recordTypes.remove(text);
								break;
							}
						}
					}
					num++;

				} while (CommonLib.checkElementVisibility(driver, getnextBtn(20), "Next button", 20));
			} else {
				log(LogStatus.ERROR, "Not able to switch to page layout iframe", YesNo.No);
				result.add("Not able to switch to page layout iframe");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on the Page Layout Assignment button", YesNo.No);
			result.add("Not able to click on the Page Layout Assignment button");

		}

		if (!recordTypes.isEmpty()) {
			for (int i = 0; i < recordTypes.size(); i++) {
				result.add(recordTypes.get(i));
			}
		}

		switchToDefaultContent(driver);

		return result;

	}

	public ArrayList<String> VerifyLightningRecordPagesAssignment(String record, String profile, String tabName,
			int timeOut) {
		String xPath;
		WebElement ele;
		String text;
		ArrayList<String> recordTypeExpected = new ArrayList<String>();
		ArrayList<String> lightningPageExpected = new ArrayList<String>();
		ArrayList<String> recordTypeActual = new ArrayList<String>();
		ArrayList<String> lightningPageActual = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		String[] recordTypeLightningPage = record.split("<break>");

		for (int i = 0; i < recordTypeLightningPage.length; i++) {
			String[] bearkRecordTypeAndLightningPage = recordTypeLightningPage[i].split("<section>");
			recordTypeExpected.add(bearkRecordTypeAndLightningPage[0]);
			lightningPageExpected.add(bearkRecordTypeAndLightningPage[1]);
		}

		String[] userProfile = profile.split("<break>");

		// Record Type

		if (CommonLib.click(driver, getviewPageAssignments(timeOut), "View Page Assignment button", action.BOOLEAN)) {
			log(LogStatus.INFO, "Successfully clicked on the view page assignment button", YesNo.No);

			xPath = "//ul[@class='tabs__nav']//span[text()='" + tabName + "']";
			ele = CommonLib.FindElement(driver, xPath, tabName + " tab name", action.SCROLLANDBOOLEAN, 50);
			if (CommonLib.click(driver, ele, tabName, action.BOOLEAN)) {
				log(LogStatus.INFO, "Successfully clicked on the " + tabName + " button", YesNo.No);

				for (int i = 0; i < userProfile.length; i++) {
					log(LogStatus.INFO,
							"Verifying the Record type and Lightning page for " + userProfile[i] + " profile",
							YesNo.No);

					for (int j = 0; j < recordTypeExpected.size(); j++) {
						xPath = "//span[text()='Desktop']/../preceding-sibling::td/span[text()='" + userProfile[i]
								+ "']/../preceding-sibling::td/span[text()='" + recordTypeExpected.get(j) + "']";
						ele = CommonLib.FindElement(driver, xPath, recordTypeExpected.get(j) + " record type",
								action.SCROLLANDBOOLEAN, 30);
						if (ele == null) {
							log(LogStatus.ERROR, "not able to get the " + recordTypeExpected.get(j) + " locator",
									YesNo.No);
							result.add("not able to get the " + recordTypeExpected.get(j) + " locator");
						}
						text = CommonLib.getText(driver, ele, recordTypeExpected.get(j) + " record type",
								action.SCROLLANDBOOLEAN);
						recordTypeActual.add(text);

						xPath = "//span[text()='Desktop']/../preceding-sibling::td/span[text()='" + userProfile[i]
								+ "']/../following-sibling::td//span[text()='" + lightningPageExpected.get(j) + "']";

						ele = CommonLib.FindElement(driver, xPath, lightningPageExpected.get(j) + "record type",
								action.SCROLLANDBOOLEAN, 30);
						if (ele == null) {
							log(LogStatus.ERROR, "not able to get the " + lightningPageExpected.get(j) + " locator",
									YesNo.No);
							result.add("not able to get the " + lightningPageExpected.get(j) + " locator");
						}
						text = CommonLib.getText(driver, ele, recordTypeExpected.get(j) + " record type",
								action.SCROLLANDBOOLEAN);
						lightningPageActual.add(text);
					}

					for (int k = 0; k < recordTypeExpected.size(); k++) {
						if (recordTypeExpected.get(k).equals(recordTypeActual.get(k))
								&& lightningPageExpected.get(k).equals(lightningPageActual.get(k))) {
							log(LogStatus.INFO, "Expected record type : \"" + recordTypeExpected.get(k)
									+ "\" and Expected lightning Page : \"" + lightningPageExpected.get(k)
									+ "\" has been matched with the Actual Record type : " + recordTypeActual.get(k)
									+ " and Actual lightning page : " + lightningPageActual.get(k), YesNo.No);

						} else {
							log(LogStatus.ERROR, "Either Expected record type : \"" + recordTypeExpected.get(k)
									+ "\" or Expected lightning Page : \"" + lightningPageExpected.get(k)
									+ "\" is not matched with the Actual Record type : " + recordTypeActual.get(k)
									+ " and Actual lightning page : " + recordTypeActual.get(k), YesNo.No);
							result.add("Either Expected record type : \"" + recordTypeExpected.get(k)
									+ "\" or Expected lightning Page : \"" + lightningPageExpected.get(k)
									+ "\" is not matched with the Actual Record type : " + recordTypeActual.get(k)
									+ " and Actual lightning page : " + recordTypeActual.get(k));
						}
					}

					lightningPageActual.removeAll(lightningPageActual);
					recordTypeActual.removeAll(recordTypeActual);

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the " + tabName + " button", YesNo.No);
				result.add("Not able to click on the " + tabName + " button");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on the view page assignment button", YesNo.No);
			result.add("Not able to click on the view page assignment button");
		}

		return result;

	}

	/**
	 * @author Sourabh Kumar
	 * @param layoutName
	 * @param timeout
	 * @return empty list if all field matched in page layout
	 */

	public List<String> verifyFieldsAvailabilityAndNonAvailabilityOnPageLayout(String sectionsInPageLayout,
			String PageLayouts, String fieldsAlreadyAddedLayoutWise, String fieldsNotAlreadyAddedLayoutWise,
			int timeOut) {
		List<String> result = new ArrayList<>();

		String layoutName = "";
		String fieldName = "";
		String[] sectionsInPageLayoutList = sectionsInPageLayout.split("<break>");
		String[] PageLayoutsList = PageLayouts.split("<break>");
		String[] fieldsAlreadyAddedLayoutWiseList = fieldsAlreadyAddedLayoutWise.split("<break>");
		String[] fieldsNotAlreadyAddedLayoutWiseList = fieldsNotAlreadyAddedLayoutWise.split("<break>");

		String[] fieldAdded;
		String[] fieldNotAdded;

		if (sectionsInPageLayoutList.length == PageLayoutsList.length
				&& PageLayoutsList.length == fieldsAlreadyAddedLayoutWiseList.length
				&& fieldsAlreadyAddedLayoutWiseList.length == fieldsNotAlreadyAddedLayoutWiseList.length) {
			log(LogStatus.INFO, "Expected Data Size Matched: " + sectionsInPageLayoutList.length
					+ " So, Going for Further Process of Validations", YesNo.No);

			for (int a = 0; a < PageLayoutsList.length; a++) {

				layoutName = PageLayoutsList[a];
				fieldAdded = fieldsAlreadyAddedLayoutWiseList[a].split("<fieldAdded>");
				fieldNotAdded = fieldsNotAlreadyAddedLayoutWiseList[a].split("<fieldNotAdded>");

				if (openAlreadyCreatedPageLayout(layoutName, ObjectFeatureName.pageLayouts, 60)) {
					log(LogStatus.PASS, "Successfully Open page layout :" + layoutName, YesNo.No);

					ThreadSleep(5000);
					switchToFrame(driver, 60, getSetUpPageIframe(120));
					CommonLib.ThreadSleep(5000);
					if (clickUsingJavaScript(driver, sectionInPageLayoutButton(sectionsInPageLayoutList[a], 20),
							"Section in Page Layout: " + sectionsInPageLayoutList[a], action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on section: " + sectionsInPageLayoutList[a]
								+ " in the page layout :" + layoutName, YesNo.No);
						CommonLib.ThreadSleep(3000);

						if (fieldAdded.length != 0 && !fieldAdded[0].equals("")) {
							for (int i = 0; i < fieldAdded.length; i++) {

								fieldName = fieldAdded[i];

								if (sendKeysAndPressEnter(driver, getQuickFindInPageLayout_Lighting(timeOut), fieldName,
										fieldName + " field", action.BOOLEAN)) {
									ThreadSleep(1000);
									List<WebElement> lst = getFieldsInPageLayoutList();
									List<WebElement> lst2 = FindElements(driver,
											"//div[@id='fieldTrough']//div[contains(@class,'item')]/span", "");
									int size = lst.size();
									for (int b = 0; b < size; b++) {
										String at = lst.get(b).getAttribute("class");
										String at2 = lst2.get(b).getText().replace("...", "");

										if (fieldName.contains(at2)) {

											log(LogStatus.PASS, fieldName
													+ " field successfully found in the page layout :" + layoutName,
													YesNo.No);

											if (!at.contains("item unused")) {

												log(LogStatus.PASS,
														fieldName + " field is appearing in "
																+ sectionsInPageLayoutList[a]
																+ " on the page layout of: " + layoutName,
														YesNo.No);

											} else {
												log(LogStatus.FAIL,
														fieldName + " field is not appearing in "
																+ sectionsInPageLayoutList[a]
																+ " on the page layout of: " + layoutName,
														YesNo.No);
												result.add(fieldName + " field is not appearing in "
														+ sectionsInPageLayoutList[a] + " on the page layout of: "
														+ layoutName);

											}
											break;

										} else {
											if (b == size - 1) {

												log(LogStatus.FAIL, fieldName + " field  not found in the page layout :"
														+ layoutName, YesNo.No);
												result.add(fieldName + " field not found in the page layout :"
														+ layoutName);

											}

										}

									}

								} else {
									log(LogStatus.FAIL,
											"Not able to search field: " + fieldName + " in quick find input",
											YesNo.No);
									result.add("Not able to search field: " + fieldName + " in quick find input");

								}

							}
						} else {
							log(LogStatus.ERROR,
									"No data present in case of Field Already Added for Layout: " + layoutName,
									YesNo.No);

						}

						if (fieldNotAdded.length != 0 && !fieldNotAdded[0].equals("")) {
							for (int i = 0; i < fieldNotAdded.length; i++) {

								fieldName = fieldNotAdded[i];

								if (sendKeysAndPressEnter(driver, getQuickFindInPageLayout_Lighting(timeOut), fieldName,
										fieldName + " field", action.BOOLEAN)) {
									ThreadSleep(1000);
									List<WebElement> lst = getFieldsInPageLayoutList();
									List<WebElement> lst2 = FindElements(driver,
											"//div[@id='fieldTrough']//div[contains(@class,'item')]/span", "");
									int size = lst.size();
									for (int b = 0; b < size; b++) {
										String at = lst.get(b).getAttribute("class");
										String at2 = lst2.get(b).getText().replace("...", "");

										if (fieldName.contains(at2)) {

											log(LogStatus.PASS, fieldName
													+ " field successfully found in the page layout :" + layoutName,
													YesNo.No);

											if (!at.contains("item unused")) {

												log(LogStatus.FAIL,
														fieldName + " field is not appearing in "
																+ sectionsInPageLayoutList[a]
																+ " on the page layout of: " + layoutName,
														YesNo.No);
												result.add(fieldName + " field is not appearing in "
														+ sectionsInPageLayoutList[a] + " on the page layout of: "
														+ layoutName);

											} else {
												log(LogStatus.PASS,
														fieldName + " field is appearing in "
																+ sectionsInPageLayoutList[a]
																+ " on the page layout of: " + layoutName,
														YesNo.No);

											}
											break;

										} else {
											if (b == size - 1) {

												log(LogStatus.FAIL, fieldName + " field  not found in the page layout :"
														+ layoutName, YesNo.No);
												result.add(fieldName + " field not found in the page layout :"
														+ layoutName);

											}

										}

									}

								} else {
									log(LogStatus.FAIL,
											"Not able to search field: " + fieldName + " in quick find input",
											YesNo.No);
									result.add("Not able to search field: " + fieldName + " in quick find input");

								}

							}
						} else {
							log(LogStatus.ERROR,
									"No data present in case of Field Not Already Added for Layout: " + layoutName,
									YesNo.No);

						}

						if (fieldNotAdded.length == 1 && fieldNotAdded[0].equals("") && fieldAdded.length == 1
								&& fieldAdded[0].equals("")) {
							log(LogStatus.ERROR,
									"No data present in Both case of Field Not Already Added & Field Already Added for Layout: "
											+ layoutName,
									YesNo.No);
							result.add(
									"No data present in Both case of Field Not Already Added & Field Already Added for Layout: "
											+ layoutName);
						}

					}

					else {
						log(LogStatus.ERROR, "Not Able  to Click on section: " + sectionsInPageLayoutList[a]
								+ " in the page layout :" + layoutName, YesNo.No);
						result.add("Not Able  to Click on section: " + sectionsInPageLayoutList[a]
								+ " in the page layout :" + layoutName);

					}

				} else {
					log(LogStatus.FAIL, "Not able to Open page layout :" + layoutName, YesNo.No);
					result.add("Not able to Open page layout :" + layoutName);

				}
				switchToDefaultContent(driver);

			}
		} else {

			log(LogStatus.INFO,
					"Expected Data Size Not Matched, sectionsInPageLayoutList: " + sectionsInPageLayoutList.length
							+ " , PageLayouts: " + PageLayouts.length() + ", FieldsAlreadyAddedLayoutWise : "
							+ fieldsAlreadyAddedLayoutWise.length() + ", fieldsNotAlreadyAddedLayoutWise : "
							+ fieldsNotAlreadyAddedLayoutWise.length()
							+ " So, Not Going for Further Process of Validations",
					YesNo.No);

			result.add("Expected Data Size Not Matched, sectionsInPageLayoutList: " + sectionsInPageLayoutList.length
					+ " , PageLayouts: " + PageLayouts.length() + ", FieldsAlreadyAddedLayoutWise : "
					+ fieldsAlreadyAddedLayoutWise.length() + ", fieldsNotAlreadyAddedLayoutWise : "
					+ fieldsNotAlreadyAddedLayoutWise.length() + " So, Not Going for Further Process of Validations");
		}
		return result;
	}

	public boolean removeRecordTypeOfObject(String profileName, RecordType recordType) {
		boolean flag = false;
		String xPath = "";
		WebElement ele = null;
		ThreadSleep(5000);
		if (CommonLib.switchToFrame(driver, 50, getuserProfileIframe(50))) {
			ThreadSleep(5000);
			log(LogStatus.INFO, "Successfully switched to User Profile Iframe", YesNo.No);
			xPath = "//div[@class='bRelatedList']//a[text()='" + profileName + "']";
			ele = CommonLib.FindElement(driver, xPath, profileName + " profile name", action.SCROLLANDBOOLEAN, 50);
			if (CommonLib.clickUsingJavaScript(driver, ele, profileName + " profile name", action.BOOLEAN)) {
				log(LogStatus.INFO, "Successfully clicked on the " + profileName + " profile name", YesNo.No);
				ThreadSleep(12000);
				CommonLib.switchToDefaultContent(driver);
				ThreadSleep(2000);
				if (CommonLib.switchToFrame(driver, 50, getProfileIframe(50))) {
					ThreadSleep(5000);
					log(LogStatus.INFO, "Successfully switched to Profile Iframe", YesNo.No);
					xPath = "//h4[contains(text(),'Record Type Settings')]/ancestor::tbody//td[text()='"
							+ recordType.toString() + "s']/following-sibling::td/a";
					ele = FindElement(driver, xPath, recordType.toString() + " edit button", action.SCROLLANDBOOLEAN,
							20);
					if (click(driver, ele, recordType.toString() + " edit button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on edit button of " + recordType.toString(), YesNo.Yes);
						ThreadSleep(9000);
						CommonLib.switchToDefaultContent(driver);
						if (CommonLib.switchToFrame(driver, 50, geteditRecordTypeIframe(50))) {

							log(LogStatus.INFO, "Successfully switched to edit record Iframe", YesNo.No);
							ThreadSleep(5000);

							if (click(driver, getSelectedRecordTypeOption(30), "Selected record type options",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on the option value of selected record", YesNo.No);
								try {
									Robot rb = new Robot();
									rb.keyPress(KeyEvent.VK_CONTROL);
									rb.keyPress(KeyEvent.VK_A);
									Actions act = new Actions(driver);
									act.sendKeys(Keys.CONTROL + "a");
									if (click(driver, getLeftArrowIcon(10), "Left Arrow button",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "clicked on the left arrow button", YesNo.No);

										if (click(driver, getMasterOptionValueFromAvailabelRecord(10),
												"option value \"--Master--\" of available record",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on the option value of available record",
													YesNo.No);

											if (click(driver, getRightArrowIcon(20), "right arrow icon",
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "clicked on the right arrow icon", YesNo.No);
												if (getMasterOptionValueFromSelectedRecord(20) != null) {
													log(LogStatus.INFO,
															"\"--Master--\" value is availabel on Selected record",
															YesNo.No);

													if (click(driver, getSaveButton(30), "Save button",
															action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO, "clicked on the save button", YesNo.No);
														flag = true;
													} else {
														log(LogStatus.ERROR, "Not able to click on save button",
																YesNo.No);
													}

												} else {
													log(LogStatus.ERROR,
															"\"--Master--\" value is not availabel on Selected record",
															YesNo.No);
												}
											} else {
												log(LogStatus.ERROR, "Not able to clicked on right arrow icon",
														YesNo.No);
											}
										} else {
											log(LogStatus.ERROR,
													"Not able to click on the option value of availabel record",
													YesNo.No);
										}
									} else {
										log(LogStatus.ERROR, "Not able to click on the left arrow button", YesNo.No);
									}

								} catch (Exception ex) {
									log(LogStatus.ERROR, "Not able to select the record", YesNo.No);
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on the option value of selected record",
										YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "Not able to switch to edit record Iframe", YesNo.No);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on edit button of " + recordType.toString(), YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not able to switched to Profile Iframe", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on the " + profileName + " profile name", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to switched to User profile Iframe", YesNo.Yes);
		}
		return flag;
	}

	public ArrayList<String> inactiveRecordType(String projectName, object objectName) {
		String xPath;
		List<WebElement> elements;
		WebElement ele;

		ArrayList<String> recordTypelabel = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();

		xPath = "//lightning-icon[contains(@class,'slds-icon-utility-check')]/..//preceding-sibling::td[2]//span[@class='uiOutputText']";
		elements = FindElements(driver, xPath, "Record type era button");
		for (int i = 0; i < elements.size(); i++) {
			recordTypelabel.add(getText(driver, elements.get(i), "record type label", action.SCROLLANDBOOLEAN));
		}
		if (!recordTypelabel.isEmpty()) {
			for (int i = 0; i < recordTypelabel.size(); i++) {
				xPath = "//span[text()='" + recordTypelabel.get(i)
						+ "']/../../following-sibling::td//lightning-icon[contains(@class,'slds-icon-utility-down')]";
				ele = FindElement(driver, xPath, recordTypelabel.get(i) + " record ero button", action.SCROLLANDBOOLEAN,
						20);
				if (click(driver, ele, recordTypelabel.get(i) + " record ero button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on ero button of " + recordTypelabel.get(i), YesNo.No);
					xPath = "//span[text()='" + recordTypelabel.get(i)
							+ "']/../../following-sibling::td//div[@role='menu']//a[@title='Edit']";
					ele = FindElement(driver, xPath, recordTypelabel.get(i) + " record edit button",
							action.SCROLLANDBOOLEAN, 20);
					if (click(driver, ele, "Edit button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on edit button against " + recordTypelabel.get(i), YesNo.No);

						if (CommonLib.switchToFrame(driver, 30, getIframeEditRecordType(20))) {
							log(LogStatus.INFO, "Switched to edit record page iframe", YesNo.No);
							xPath = "//label[text()='Active']/../..//input";
							ele = FindElement(driver, xPath, "Active checkbox", action.SCROLLANDBOOLEAN, 20);
							if (isSelected(driver, ele, "Active checkbox")) {
								if (click(driver, ele, "Active checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on the active checkbox button", YesNo.No);

									if (click(driver, getViewAccessbilityDropDownSaveButton(20), "save button",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on the save button", YesNo.No);
										CommonLib.switchToDefaultContent(driver);
										if (CommonLib.switchToFrame(driver, 20, getIframeRecordType(20))) {
											log(LogStatus.INFO, "Switched to Record type iframe", YesNo.No);
											xPath = "//h1[text()='Record Type']";
											ele = FindElement(driver, xPath, "record type heading",
													action.SCROLLANDBOOLEAN, 20);
											if (ele != null) {
												log(LogStatus.INFO, "Record type has been inacive", YesNo.No);
											} else {
												log(LogStatus.ERROR, "Record type is not inacive", YesNo.No);
												result.add("Record type is not inacive");
											}
										} else {
											log(LogStatus.ERROR, "Not able to switch to Record type iframe", YesNo.No);
											result.add("Not able to switch to Record type iframe");
										}
									} else {
										log(LogStatus.ERROR, "Not able to click on the save button", YesNo.No);
										result.add("Not able to click on the save button");
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on the active checkbox button", YesNo.No);
									result.add("Not able to click on the active checkbox button");
								}
							} else {
								log(LogStatus.INFO, "The record is already inactive", YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "Not able to switch to edit record page iframe", YesNo.No);
							result.add("Not able to switch to edit record page iframe");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on edit button against " + recordTypelabel.get(i),
								YesNo.No);
						result.add("Not able to click on edit button against " + recordTypelabel.get(i));
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on ero button of " + recordTypelabel.get(i), YesNo.No);
					result.add("Not able to click on ero button of " + recordTypelabel.get(i));
				}

				CommonLib.switchToDefaultContent(driver);
				if (clickOnObjectFeature(projectName, mode, objectName, ObjectFeatureName.recordTypes)) {
					log(LogStatus.INFO,
							"clicked on Record type of object feature of " + objectName.toString() + " object",
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not able to click on Record type of object feature of "
							+ objectName.toString() + " object", YesNo.No);
					result.add("Not able to click on Record type of object feature of " + objectName.toString()
							+ " object");
				}
			}
		} else {
			log(LogStatus.INFO, "All records are already inactive", YesNo.No);
		}

		return result;
	}

	public ArrayList<String> deleteAllRecordTypeOfObject() {
		String xPath;
		WebElement ele;
		List<WebElement> elements;
		ArrayList<String> recordTypeName = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();

		xPath = "//table[contains(@class,'slds-table')]//td[1]//span";
		elements = FindElements(driver, xPath, "Record type name");
		for (int i = 0; i < elements.size(); i++) {
			recordTypeName.add(getText(driver, elements.get(i), "record type Name", action.SCROLLANDBOOLEAN));
		}
		for (int i = 0; i < recordTypeName.size(); i++) {
			xPath = "//span[text()='" + recordTypeName.get(i)
					+ "']/../../following-sibling::td//lightning-icon[contains(@class,'slds-icon-utility-down')]";
			ele = FindElement(driver, xPath, recordTypeName.get(i) + " record ero button", action.SCROLLANDBOOLEAN, 20);
			if (click(driver, ele, recordTypeName.get(i) + " record ero button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on ero button of " + recordTypeName.get(i), YesNo.No);
				xPath = "//span[text()='" + recordTypeName.get(i)
						+ "']/../../following-sibling::td//div[@role='menu']//a[@title='Delete']";
				ele = FindElement(driver, xPath, recordTypeName.get(i) + " record delete button",
						action.SCROLLANDBOOLEAN, 20);
				if (click(driver, ele, "delete button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on delete button against " + recordTypeName.get(i), YesNo.No);
					if (click(driver, getdeleteButton(20), "Delete button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on delete button", YesNo.No);

						CommonLib.switchToDefaultContent(driver);
						if (CommonLib.switchToFrame(driver, 20, getdeleteProblemIframe(20))) {
							log(LogStatus.INFO, "Switched to delete problem iframe", YesNo.No);
							xPath = "//a[text()='Delete']";
							ele = FindElement(driver, xPath, "delete button", action.SCROLLANDBOOLEAN, 15);
							if (click(driver, ele, "Delete button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on delete button", YesNo.No);
								CommonLib.switchToDefaultContent(driver);
								if (CommonLib.switchToFrame(driver, 20, getDeleteRecordTypeIframe(20))) {
									log(LogStatus.INFO, "Switched to delete record type iframe", YesNo.No);
									if (click(driver, getDoneDeleteRecordTypeBtn(20), "Done", null)) {
										log(LogStatus.INFO, "clicked on done button", YesNo.No);
										CommonLib.switchToDefaultContent(driver);
										xPath = "//span[text()='" + recordTypeName.get(i) + "']";
										ele = FindElement(driver, xPath, recordTypeName.get(i), action.SCROLLANDBOOLEAN,
												20);
										if (ele == null) {
											log(LogStatus.INFO, recordTypeName.get(i) + " record type has been deleted",
													YesNo.No);
										} else {
											log(LogStatus.ERROR, recordTypeName.get(i) + " record type is not deleted",
													YesNo.No);
											result.add(recordTypeName.get(i) + " record type is not deleted");
										}
									}

									else {
										log(LogStatus.ERROR, "Not able to click on done button", YesNo.No);
										result.add("Not able to click on done button");
									}
								} else {
									log(LogStatus.ERROR, "Not able to switch to delete record type iframe", YesNo.No);
									result.add("Not able to switch to delete record type iframe");
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on delete button", YesNo.No);
								result.add("Not able to click on delete button");
							}
						} else {
							log(LogStatus.ERROR, "Not able to switch to delete problem iframe", YesNo.No);
							result.add("Not able to switch to delete problem iframe");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on delete button", YesNo.No);
						result.add("Not able to click on delete button");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on delete button against " + recordTypeName.get(i),
							YesNo.No);
					result.add("Not able to click on delete button against " + recordTypeName.get(i));
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on ero button of " + recordTypeName.get(i), YesNo.No);
				result.add("Not able to click on ero button of " + recordTypeName.get(i));
			}
		}
		return result;
	}

	public boolean editPEUser(String userfirstname, String userlastname, String email) {
		boolean flag = false;
		WebElement ele = null;
		if (click(driver, getExpandUserIcon(30), "expand User Icon", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on user expand icon");
			if (click(driver, getUsersLink(30), "User Link", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on users link");
				switchToFrame(driver, 20, getSetUpPageIframe(20));
				CommonLib.ThreadSleep(3000);
				try {
					ele = new WebDriverWait(driver, 50)
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='" + email
									+ "']/parent::td//preceding-sibling::td[@class='actionColumn']//a[text()='Edit']")));
				} catch (Exception ex) {
					ex.printStackTrace();
					log(LogStatus.ERROR, "Could not found the Element of the edit button", YesNo.Yes);
					flag = false;
				}
				if (click(driver, ele, "Edit Button", action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on the edit button against " + email);
					switchToDefaultContent(driver);
					switchToFrame(driver, 50, getuserEditPageIframe(50));
					ThreadSleep(3000);

					switchToFrame(driver, 20, getSetUpPageIframe(20));
					if (sendKeys(driver, getUserFirstName(60), userfirstname, "User First Name",
							action.SCROLLANDBOOLEAN)) {
						if (sendKeys(driver, getUserLastName(60), userlastname, "User Last Name",
								action.SCROLLANDBOOLEAN)) {
							if (click(driver, getActiveUserCheckBox(60), "Active User check Box",
									action.SCROLLANDBOOLEAN)) {
								ThreadSleep(5000);
								if (click(driver, getpopupOKbutton(60), "pop up OK button", action.SCROLLANDBOOLEAN)) {
									if (clickUsingJavaScript(driver, getCreateUserSaveBtn_Lighting(30), "Save Button",
											action.SCROLLANDBOOLEAN)) {
										appLog.info("clicked on save button");
										appLog.info("CRM User is updated successfully: " + userfirstname + " "
												+ userlastname);
										return true;
									} else {
										appLog.error("Not able to click on save buttton so cannot create user: "
												+ userfirstname + " " + userlastname);
									}

								} else {
									appLog.info("Not able to click on Active user checkbox");
								}
							} else {
								appLog.info("Not able to click on pop up OK button");
							}

						} else {
							appLog.error("Not able to pass user last name in text box: " + userlastname
									+ " so cannot create user: " + userfirstname + " " + userlastname);
						}
					} else {
						appLog.error("Not able pass user first name in text box: " + userfirstname
								+ " so cannot create user: " + userfirstname + " " + userlastname);
					}

				} else {
					appLog.error("Not able to click on edit user button so cannot update user: " + userfirstname + " "
							+ userlastname);
				}

			} else {
				appLog.error(
						"Not able to click on users link so cannot update user: " + userfirstname + " " + userlastname);
			}

		} else {
			appLog.error("Not able to click on manage user expand icon so cannot update user: " + userfirstname + " "
					+ userlastname);
		}
		switchToDefaultContent(driver);
		return false;
	}

	public boolean objectPermissionGivenOrRemove(String[][] objectAndPermissionAndGivenOrGivenNot,
			String[] userTypesToGivePermissions) {

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);

		boolean flag = false;
		String parentID = null;
		for (String userName : userTypesToGivePermissions) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
				if (parentID != null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to set permission for objects"
							+ objectAndPermissionAndGivenOrGivenNot, YesNo.No);
					ThreadSleep(500);
					if (setup.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.permissionChangeOfUserONObject(driver, userName,
								objectAndPermissionAndGivenOrGivenNot, 20)) {
							log(LogStatus.PASS,
									"Permission Set for Object is: " + objectAndPermissionAndGivenOrGivenNot, YesNo.No);
							flag = true;

						} else {
							sa.assertTrue(false,
									"Permission not Set for Object is: " + objectAndPermissionAndGivenOrGivenNot);
							log(LogStatus.FAIL,
									"Permission not Set for Object is: " + objectAndPermissionAndGivenOrGivenNot,
									YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Not able to search/click on " + object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on " + object.Profiles);
					}

				} else {
					log(LogStatus.FAIL,
							"could not find new window to switch, so cannot to set permission for object: "
									+ "Permission not Set for Object is: " + objectAndPermissionAndGivenOrGivenNot,
							YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, so cannot to set permission for object: "
							+ "Permission not Set for Object is: " + objectAndPermissionAndGivenOrGivenNot);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");
			}
		}

		if (parentID != null) {
			driver.close();
			driver.switchTo().window(parentID);
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param driver
	 * @param userName
	 * @param LabelswithCheck
	 * @param timeOut
	 * @return true if able to change permission for particular object for
	 *         particular type for particular user
	 */
	public boolean renameLabelsOfFields(WebDriver driver, String tabName, String[] labelsWithValues, int timeOut) {

		switchToDefaultContent(driver);
		switchToFrame(driver, 60, getSetUpPageIframe(120));
		boolean flag = false;

		if (clickUsingJavaScript(driver, editButtonInRenameTabAndLabels(tabName, timeOut), tabName,
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "able to click on Edit Button of Tab: " + tabName, YesNo.No);
			switchToDefaultContent(driver);
			switchToFrame(driver, 60, getSetUpPageIframe(120));

			if (clickUsingJavaScript(driver, nextButton(timeOut), "Next Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "able to click on Next Button of Tab: " + tabName, YesNo.No);
				switchToDefaultContent(driver);
				switchToFrame(driver, 60, getSetUpPageIframe(120));

				for (String labelAndValue : labelsWithValues) {
					String[] labelValue = null;
					String labelName = "";
					String value = "";
					labelValue = labelAndValue.split("<break>", -1);
					labelName = labelValue[0];
					value = labelValue[1];
					CommonLib.ThreadSleep(1000);
					if (CommonLib.sendKeys(driver, renameLabelNameSingularTextBox(labelName, timeOut), value,
							labelName + " input ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Enter the Value: " + value + " to InputBox of Labeled: " + labelName,
								YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not able to Pass value: " + value + " to input box labeled: " + labelName,
								YesNo.Yes);
					}

				}

				switchToDefaultContent(driver);

				switchToFrame(driver, 60, getSetUpPageIframe(120));
				CommonLib.ThreadSleep(5000);

				if (clickUsingJavaScript(driver, getfooterSaveBtn(30), "Save Button", action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);

					ThreadSleep(8000);
					switchToDefaultContent(driver);
					CommonLib.refresh(driver);

					switchToFrame(driver, 60, getSetUpPageIframe(120));
					if (editButtonInRenameTabAndLabels(tabName, timeOut) != null) {
						flag = true;
					}

				} else {
					log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Next Button for tab: " + tabName, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on Edit button of tab: " + tabName, YesNo.Yes);
		}
		return flag;
	}

	public boolean renameLabelsOfFields(WebDriver driver, String[] tabNames, String[][] labelsWithValues2d,
			int timeOut) {

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);

		boolean flag = false;
		String parentID = null;
		int loopCount = 0;
		for (String[] labelsWithValues : labelsWithValues2d) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
				if (parentID != null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to set Label Names: " + labelsWithValues
							+ " for tab: " + tabNames[loopCount], YesNo.No);
					ThreadSleep(500);
					if (setup.searchStandardOrCustomObject(environment, mode, object.Rename_Tabs_And_Labels)) {
						log(LogStatus.INFO, "click on Object : " + object.Rename_Tabs_And_Labels, YesNo.No);
						ThreadSleep(2000);
						if (setup.renameLabelsOfFields(driver, tabNames[loopCount], labelsWithValues, timeOut)) {
							log(LogStatus.PASS, "Label Names: " + labelsWithValues + " for tab: " + tabNames[loopCount]
									+ " has been set", YesNo.No);
							flag = true;

						} else {
							sa.assertTrue(false, "Label Names: " + labelsWithValues + " for tab: " + tabNames[loopCount]
									+ " has not been set");
							log(LogStatus.FAIL, "Label Names: " + labelsWithValues + " for tab: " + tabNames[loopCount]
									+ " has not been set", YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_And_Labels, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_And_Labels);
					}

				} else {
					log(LogStatus.FAIL, "could not find new window to switch, so not able to set Label Names: "
							+ labelsWithValues + " for tab: " + tabNames[loopCount], YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, so not able to set Label Names: "
							+ labelsWithValues + " for tab: " + tabNames[loopCount]);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");
			}
			loopCount++;
		}

		if (parentID != null) {
			driver.close();
			driver.switchTo().window(parentID);
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param driver
	 * @param userName
	 * @param LabelswithCheck
	 * @param timeOut
	 * @return true if able to change permission for particular object for
	 *         particular type for particular user
	 */
	public boolean permissionChangeOfGeneralAndAdministrative(WebDriver driver, String userName,
			String[][] LabelswithCheck, int timeOut) {

		switchToDefaultContent(driver);
		switchToFrame(driver, 60, getSetUpPageIframe(120));
		boolean flag = false;
		;
		String xpath = "";
		xpath = "//th//a[text()='" + userName + "']";
		ThreadSleep(2000);
		WebElement ele = FindElement(driver, xpath, userName, action.SCROLLANDBOOLEAN, timeOut);
		ele = isDisplayed(driver, ele, "visibility", timeOut, userName);
		if (clickUsingJavaScript(driver, ele, userName.toString(), action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "able to click on " + userName, YesNo.No);
			switchToDefaultContent(driver);
			switchToFrame(driver, 60, getSetUpPageIframe(120));
			xpath = "//*[@id='topButtonRow']//input[@name='edit']";
			ele = FindElement(driver, xpath, "Edit Button", action.SCROLLANDBOOLEAN, timeOut);
			ThreadSleep(5000);
			if (clickUsingJavaScript(driver, ele, "Edit Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "able to click on edit button", YesNo.No);

				String OnLabel = "";

				String givenOrNot;
				for (String[] strings : LabelswithCheck) {

					switchToDefaultContent(driver);
					switchToFrame(driver, 60, getSetUpPageIframe(120));
					OnLabel = strings[0];

					givenOrNot = strings[1];

					xpath = "(//*[text()='" + OnLabel + "']/parent::td/following-sibling::td/input)[1]";
					ele = FindElement(driver, xpath, OnLabel + " with permission " + givenOrNot,
							action.SCROLLANDBOOLEAN, timeOut);
					CommonLib.ThreadSleep(4000);

					String checked = CommonLib.getAttribute(driver, ele, "CheckBox", "checked");

					if (givenOrNot.equals(PermissionType.givePermission.toString()))

					{

						if (!"true".equals(checked)) {

							if (click(driver, ele, OnLabel + " with permission " + givenOrNot,
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on checkbox " + givenOrNot + " for " + OnLabel, YesNo.No);

								CommonLib.ThreadSleep(4000);

							} else {
								log(LogStatus.ERROR, "Not Able clicked on checkbox " + givenOrNot + " for " + OnLabel,
										YesNo.Yes);
								sa.assertTrue(false, givenOrNot + " permission not change for " + userName
										+ " on object " + OnLabel);
								log(LogStatus.FAIL,
										givenOrNot + " permission not change for " + userName + " on object " + OnLabel,
										YesNo.Yes);

							}

						} else {
							log(LogStatus.INFO, "Not clicked on checkbox " + givenOrNot + " for " + OnLabel
									+ " as it is already Checked", YesNo.No);

						}
					} else if (givenOrNot.equals(PermissionType.removePermission.toString()))

					{

						if ("true".equals(checked)) {

							if (click(driver, ele, OnLabel + " with permission " + givenOrNot,
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on checkbox " + givenOrNot + " for " + OnLabel, YesNo.No);

								CommonLib.ThreadSleep(4000);

							} else {
								log(LogStatus.ERROR, "Not Able clicked on checkbox " + givenOrNot + " for " + OnLabel,
										YesNo.Yes);
								sa.assertTrue(false, givenOrNot + " permission not change for " + userName
										+ " on object " + OnLabel);
								log(LogStatus.FAIL,
										givenOrNot + " permission not change for " + userName + " on object " + OnLabel,
										YesNo.Yes);

							}

						} else {
							log(LogStatus.INFO, "Not clicked on checkbox " + givenOrNot + " for " + OnLabel
									+ " as it is already UnChecked", YesNo.No);

						}
					}

					else {
						log(LogStatus.ERROR, "Please Provide the correct data for permission given or not", YesNo.Yes);
						sa.assertTrue(false, "Please Provide the correct data for permission given or not");
					}

				}
				switchToDefaultContent(driver);

				switchToFrame(driver, 60, getSetUpPageIframe(120));
				CommonLib.ThreadSleep(5000);

				if (clickUsingJavaScript(driver, getCreateUserSaveBtn_Lighting(30), "Save Button",
						action.SCROLLANDBOOLEAN)) {
					flag = true;
					log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);

					ThreadSleep(12000);
					switchToDefaultContent(driver);
					CommonLib.refresh(driver);
				} else {
					log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "not able to click on edit button", YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + userName, YesNo.Yes);
		}
		return flag;
	}

	public boolean permissionChangeOfGeneralAndAdministrative(String[][] objectAndPermissionAndGivenOrGivenNot,
			String[] userTypesToGivePermissions) {

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);

		boolean flag = false;
		String parentID = null;
		for (String userName : userTypesToGivePermissions) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
				if (parentID != null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to set permission for objects"
							+ objectAndPermissionAndGivenOrGivenNot, YesNo.No);
					ThreadSleep(500);
					if (setup.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.permissionChangeOfGeneralAndAdministrative(driver, userName,
								objectAndPermissionAndGivenOrGivenNot, 20)) {
							log(LogStatus.PASS,
									"Permission Set for Object is: " + objectAndPermissionAndGivenOrGivenNot, YesNo.No);
							flag = true;

						} else {
							sa.assertTrue(false,
									"Permission not Set for Object is: " + objectAndPermissionAndGivenOrGivenNot);
							log(LogStatus.FAIL,
									"Permission not Set for Object is: " + objectAndPermissionAndGivenOrGivenNot,
									YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Not able to search/click on " + object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on " + object.Profiles);
					}

				} else {
					log(LogStatus.FAIL,
							"could not find new window to switch, so cannot to set permission for object: "
									+ "Permission not Set for Object is: " + objectAndPermissionAndGivenOrGivenNot,
							YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, so cannot to set permission for object: "
							+ "Permission not Set for Object is: " + objectAndPermissionAndGivenOrGivenNot);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");
			}
		}

		if (parentID != null) {
			driver.close();
			driver.switchTo().window(parentID);
		}
		return flag;
	}

	public boolean giveAndRemoveObjectPermissionFromProfiles(String profileForSelection, String[] objects,
			String[] permissionTypes, String status) {
		boolean flag = false;
		WebElement ele = null;
		String value = "Checked";
		if (searchStandardOrCustomObject(environment, mode, object.Profiles)) {
			log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
			ThreadSleep(2000);
			switchToDefaultContent(driver);
			switchToFrame(driver, 60, getSetUpPageIframe(60));

			String xpath = "";
			xpath = "//th//a[text()='" + profileForSelection + "']";
			ele = FindElement(driver, xpath, profileForSelection, action.SCROLLANDBOOLEAN, 10);
			ele = isDisplayed(driver, ele, "visibility", 10, profileForSelection);
			if (click(driver, ele, profileForSelection.toString(), action.BOOLEAN)) {
				log(LogStatus.INFO, "able to click on " + profileForSelection, YesNo.No);
				ThreadSleep(2000);
				switchToDefaultContent(driver);
				ThreadSleep(5000);
				switchToFrame(driver, 60, getSetUpPageIframe(60));
				ThreadSleep(5000);
				xpath = "//td[@id='topButtonRow']//input[@title='Edit']";
				ele = FindElement(driver, xpath, "Edit Button", action.SCROLLANDBOOLEAN, 10);
				// ele = isDisplayed(driver, ele, "visibility", 10, "Edit Button");
				if (click(driver, ele, "Edit Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,
							"able to click on edit button for " + profileForSelection + " Profiles settiing", YesNo.No);
					switchToDefaultContent(driver);
					ThreadSleep(5000);
					switchToFrame(driver, 60, getSetUpPageIframe(60));
					ThreadSleep(2000);
					for (int i = 0; i < objects.length; i++) {
						for (int j = 0; j < permissionTypes.length; j++) {
							// xpath = "(//h3[contains(text(),'Object
							// Permissions')]/..//following-sibling::div//th[text()='"+objects[i]+"']/..//input)[1]";
							xpath = "//h3[contains(text(),'Object Permissions')]/..//following-sibling::div//th[text()='"
									+ objects[i] + "s']/..//input[contains(@title,'" + permissionTypes[j] + " "
									+ objects[i] + "s')]";
							ele = FindElement(driver, xpath, "Edit Button", action.SCROLLANDBOOLEAN, 10);
							ele = isDisplayed(driver, ele, "visibility", 10, "Edit Button");
							// if(ele.getAttribute(status) != null) {
							if (value.equalsIgnoreCase(status)) {
								if (isSelected(driver, ele, "")) {
									// alreday checked
								} else {
									if (click(driver, ele, "Edit Button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "able to click on checkbox for edit ", YesNo.No);
										switchToDefaultContent(driver);
										ThreadSleep(5000);
										switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
										switchToFrame(driver, 60, getSetUpPageIframe(60));
										ThreadSleep(2000);
									} else {
										log(LogStatus.ERROR,
												"not able to click on edit button for record : " + objects[i],
												YesNo.Yes);
										sa.assertTrue(false,
												"not able to click on edit button for record : " + objects[i]);
									}
								}
							} else {

								if (isSelected(driver, ele, "")) {
									if (click(driver, ele, "Edit Button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "able to click on checkbox for edit ", YesNo.No);
										switchToDefaultContent(driver);
										ThreadSleep(5000);
										switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
										switchToFrame(driver, 60, getSetUpPageIframe(60));
										ThreadSleep(2000);
									} else {
										log(LogStatus.ERROR,
												"not able to click on edit button for record : " + objects[i],
												YesNo.Yes);
										sa.assertTrue(false,
												"not able to click on edit button for record : " + objects[i]);
									}
								} else {
									// already disable
								}

							}
						}

					}
					if (click(driver, getViewAccessbilityDropDownSaveButton(10), "save button", action.BOOLEAN)) {
						log(LogStatus.INFO, "save button", YesNo.No);
						recordTypeVerificationForProfiles(objects, status);
						return true;

					} else {
						log(LogStatus.ERROR, "Not able to click on save button", YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "not able to click on edit button for record type settiing", YesNo.Yes);
					sa.assertTrue(false, "not able to click on edit button for record type settiing");
				}
			} else {
				log(LogStatus.ERROR, profileForSelection + " profile is not clickable", YesNo.Yes);
				sa.assertTrue(false, profileForSelection + " profile is not clickable");
			}

		} else {
			log(LogStatus.ERROR, "profiles tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "profiles tab is not clickable");
		}

		return flag;
	}

	public boolean recordTypeVerificationForProfiles(String[] labels, String status) {
		String xpath = "";
		WebElement ele;
		boolean flag = false;
		switchToDefaultContent(driver);
		ThreadSleep(2000);
		switchToFrame(driver, 60, getSetUpPageIframe(120));
		for (String labelValue : labels) {
			// xpath = "//*[text()='" + labelValue[0] +
			// "']/..//following-sibling::td[text()='" + labelValue[1] + "']";
			xpath = "//h3[contains(text(),'Object Permissions')]/..//following-sibling::div//th[text()='" + labelValue
					+ "s']/..//img[contains(@id,'" + labelValue + "') and (@alt ='" + status + "')]";
			ele = FindElement(driver, xpath, labelValue + " with Value " + status, action.BOOLEAN, 10);
			if (ele != null) {
				log(LogStatus.PASS, labelValue + " with Value " + status + " verified", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, labelValue + " with Value " + status + " not verified", YesNo.Yes);
				sa.assertTrue(false, labelValue + " with Value " + status + " not verified");
			}
		}
		return flag;
	}

	public boolean userActiveOrInActive(String userfirstname, String userlastname, String email, String givenOrNot) {
		boolean flag = false;
		WebElement ele = null;
		if (click(driver, getExpandUserIcon(30), "expand User Icon", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on user expand icon");
			if (click(driver, getUsersLink(30), "User Link", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on users link");
				switchToFrame(driver, 20, getSetUpPageIframe(20));
				CommonLib.ThreadSleep(3000);

				try {
					CommonLib.selectVisibleTextFromDropDown(driver, viewUsers(20), "View Label DropDown", "All Users");
					ele = new WebDriverWait(driver, 50)
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='" + email
									+ "']/parent::td//preceding-sibling::td[@class='actionColumn']//a[text()='Edit']")));
				} catch (Exception ex) {
					ex.printStackTrace();
					log(LogStatus.ERROR, "Could not found the Element of the edit button", YesNo.Yes);
					flag = false;
				}
				if (click(driver, ele, "Edit Button", action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on the edit button against " + email);
					switchToDefaultContent(driver);
					switchToFrame(driver, 50, getuserEditPageIframe(50));
					ThreadSleep(3000);

					switchToFrame(driver, 20, getSetUpPageIframe(20));

					String checked = CommonLib.getAttribute(driver, getActiveUserCheckBox(20), "CheckBox", "checked");

					if (givenOrNot.equals(PermissionType.givePermission.toString()))

					{

						if (!"true".equals(checked)) {

							if (click(driver, getActiveUserCheckBox(30), "Active", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on checkbox Active", YesNo.No);

								if (clickUsingJavaScript(driver, getCreateUserSaveBtn_Lighting(30), "Save Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.info("clicked on save button");
									appLog.info(
											"CRM User is updated successfully: " + userfirstname + " " + userlastname);
									return true;
								} else {
									appLog.error("Not able to click on save buttton so cannot create user: "
											+ userfirstname + " " + userlastname);
								}

							} else {
								log(LogStatus.ERROR, "Not Able clicked on checkbox Active", YesNo.Yes);
								sa.assertTrue(false, "Not Able clicked on checkbox Active");

							}

						} else {
							log(LogStatus.INFO, "Not clicked on Active checkbox as it is already Checked", YesNo.No);

						}
					} else if (givenOrNot.equals(PermissionType.removePermission.toString()))

					{

						if ("true".equals(checked)) {

							if (click(driver, getActiveUserCheckBox(30), "Active", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on checkbox Active", YesNo.No);

								if (click(driver, getpopupOKbutton(60), "pop up OK button", action.SCROLLANDBOOLEAN)) {
									if (clickUsingJavaScript(driver, getCreateUserSaveBtn_Lighting(30), "Save Button",
											action.SCROLLANDBOOLEAN)) {
										appLog.info("clicked on save button");
										appLog.info("CRM User is updated successfully: " + userfirstname + " "
												+ userlastname);
										return true;
									} else {
										appLog.error("Not able to click on save buttton so cannot create user: "
												+ userfirstname + " " + userlastname);
									}

								} else {
									appLog.info("Not able to click on pop up OK button");
								}

							} else {
								log(LogStatus.ERROR, "Not Able clicked on checkbox Active", YesNo.Yes);
								sa.assertTrue(false, "Not Able clicked on checkbox Active");

							}

						} else {
							log(LogStatus.INFO, "Not clicked on Active checkbox  as it is already UnChecked", YesNo.No);

						}
					}

					else {
						log(LogStatus.ERROR, "Please Provide the correct data for permission given or not", YesNo.Yes);
						sa.assertTrue(false, "Please Provide the correct data for permission given or not");
					}

				} else {
					appLog.error("Not able to click on edit user button so cannot update user: " + userfirstname + " "
							+ userlastname);
				}

			} else {
				appLog.error(
						"Not able to click on users link so cannot update user: " + userfirstname + " " + userlastname);
			}

		} else {
			appLog.error("Not able to click on manage user expand icon so cannot update user: " + userfirstname + " "
					+ userlastname);
		}
		switchToDefaultContent(driver);
		return flag;
	}

	public boolean editPEUserAndUpdateTheName(String userfirstname, String userlastname, String email, String title) {
		boolean flag = false;
		WebElement ele = null;
		if (click(driver, getExpandUserIcon(30), "expand User Icon", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on user expand icon");
			if (click(driver, getUsersLink(30), "User Link", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on users link");
				switchToFrame(driver, 20, getSetUpPageIframe(20));
				CommonLib.ThreadSleep(3000);
				try {
					ele = new WebDriverWait(driver, 50)
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='" + email
									+ "']/parent::td//preceding-sibling::td[@class='actionColumn']//a[text()='Edit']")));
				} catch (Exception ex) {
					ex.printStackTrace();
					log(LogStatus.ERROR, "Could not found the Element of the edit button", YesNo.Yes);
					flag = false;
				}
				if (click(driver, ele, "Edit Button", action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on the edit button against " + email);
					switchToDefaultContent(driver);
					switchToFrame(driver, 50, getuserEditPageIframe(50));
					ThreadSleep(3000);

					switchToFrame(driver, 20, getSetUpPageIframe(20));

					if (userfirstname != null) {
						if (sendKeys(driver, getUserFirstName(60), userfirstname, "User First Name",
								action.SCROLLANDBOOLEAN)) {
							appLog.info("Able to pass user first name in text box: " + userfirstname);

						} else {
							appLog.error("Not able pass user first name in text box: " + userfirstname
									+ " so cannot update user: " + userfirstname + " " + userlastname);
							return false;
						}
					}

					if (userlastname != null) {

						if (sendKeys(driver, getUserLastName(60), userlastname, "User Last Name",
								action.SCROLLANDBOOLEAN)) {
							appLog.info("Able to pass user last name in text box: " + userlastname);

						} else {
							appLog.error("Not able to pass user last name in text box: " + userlastname
									+ " so cannot update user: " + userfirstname + " " + userlastname);
							return false;
						}

					}

					if (title != null) {

						if (sendKeys(driver, getUserTitle(20), title, "Title", action.SCROLLANDBOOLEAN)) {

							appLog.info("Able to pass user title  in text box: " + title);

						} else {
							appLog.error("Not able to pass user Title in text box: " + title);
							return false;
						}

					}

					if (clickUsingJavaScript(driver, getCreateUserSaveBtn_Lighting(30), "Save Button",
							action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on save button");
						appLog.info("CRM User is updated successfully: " + userfirstname + " " + userlastname);
						return true;
					} else {
						appLog.error("Not able to click on save buttton so cannot create user: " + userfirstname + " "
								+ userlastname);
					}

				} else {
					appLog.error("Not able to click on edit user button so cannot update user: " + userfirstname + " "
							+ userlastname);
				}

			} else {
				appLog.error(
						"Not able to click on users link so cannot update user: " + userfirstname + " " + userlastname);
			}

		} else {
			appLog.error("Not able to click on manage user expand icon so cannot update user: " + userfirstname + " "
					+ userlastname);
		}
		switchToDefaultContent(driver);
		return false;
	}

	public boolean UpdateValueInCustomMetaData(String type, String fieldName, String valueField, int timeOut) {
		boolean flag = false;
		String Name = fieldName.replace("_", " ");
		String SettingType = type.replace("_", " ");
		if (searchStandardOrCustomObject(environment, mode, object.Custom_Metadata_Types)) {
			log(LogStatus.INFO, "click on Object : " + object.Custom_Metadata_Types, YesNo.No);
			ThreadSleep(2000);
			switchToFrame(driver, 60, getSetUpPageIframe(120));
			if (clickUsingJavaScript(driver, settingTypeManageRecordsButton(SettingType, 10), "Manage Records",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "able to click on Manage Records link", YesNo.No);
				ThreadSleep(1000);
				switchToFrame(driver, 60, getSetUpPageIframe(60));
				if (LabelNameInCustomMetaData(Name, 10) != null) {
					log(LogStatus.INFO, "yes, we can find " + Name + "on Custom Meta Data Setup Page", YesNo.No);
					if (clickUsingJavaScript(driver, EditButtonOfAcuitySettings(Name, 30), "Edit button",
							action.SCROLLANDBOOLEAN)) {

						log(LogStatus.INFO, "click on edit button of " + Name, YesNo.No);
						ThreadSleep(5000);
						switchToFrame(driver, 60, getSetUpPageIframe(60));
						ExcelUtils.writeData(AcuityDataSheetFilePath, GetDataFromValueFieldInCustomMetaData(10),
								"CustomMetaData", excelLabel.FieldName, Name, excelLabel.Value);
						System.out.println(GetDataFromValueFieldInCustomMetaData(10));
						ThreadSleep(2000);
						if (sendKeys(driver, getValueTextBoxInAcuitySetting(30), valueField, "Value Text Box",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "enter the value in description : " + valueField, YesNo.No);
							if (click(driver, getViewAccessbilityDropDownSaveButton(20), "save button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "clicked on save button", YesNo.No);
								flag = true;
							} else {
								log(LogStatus.PASS, "not able to clicked on save button ", YesNo.No);
								sa.assertTrue(false, "not able to clicked on save button ");
							}
							switchToDefaultContent(driver);
							refresh(driver);
						} else {
							log(LogStatus.PASS, "not able to enter the value in description : " + valueField, YesNo.No);
							sa.assertTrue(false, "not able to enter the value in description : " + valueField);
						}
					} else {
						log(LogStatus.INFO, "not able to click on edit button of " + Name, YesNo.No);
						sa.assertTrue(false, "not able to click on edit button of " + Name);
					}
				} else {
					log(LogStatus.INFO, "not able to find " + Name, YesNo.No);
					refresh(driver);
				}
			} else {
				log(LogStatus.INFO, "not able to click on Manage Records link", YesNo.No);
				sa.assertTrue(false, "not able to click on Manage Records link");
			}

		} else {
			log(LogStatus.ERROR, "Not able to search/click on " + object.Custom_Metadata_Types, YesNo.Yes);
			sa.assertTrue(false, "Not able to search/click on " + object.Custom_Metadata_Types);
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param driver
	 * @param userName
	 * @param LabelswithCheck
	 * @param timeOut
	 * @return true if able to change permission for particular object for
	 *         particular type for particular user
	 *//*
		 * public boolean createValidationRule(object objectName, String fieldName,
		 * String validationRuleName, String validationRuleFormula, String
		 * validationRuleMessage, String validationRuleErrorMsgLocation) {
		 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver); boolean flag
		 * = false;
		 * 
		 * if (home.clickOnSetUpLink()) { String parentWindow = switchOnWindow(driver);
		 * if (parentWindow == null) { sa.assertTrue(false,
		 * "No new window is open after click on setup link in lighting mode so cannot create Valiation Rules for field: "
		 * + fieldName + " of Object: " + objectName); log(LogStatus.SKIP,
		 * "No new window is open after click on setup link in lighting mode so cannot create Valiation Rules for field: "
		 * + fieldName + " of Object: " + objectName, YesNo.Yes);
		 * exit("No new window is open after click on setup link in lighting mode so cannot create Valiation Rules for field: "
		 * + fieldName + " of Object: " + objectName); return false; }
		 * 
		 * if (searchStandardOrCustomObject(environment, mode, objectName)) {
		 * log(LogStatus.INFO, "click on Object : " + objectName, YesNo.No);
		 * ThreadSleep(2000); if (clickOnObjectFeature(environment, mode, objectName,
		 * ObjectFeatureName.validationRules)) { log(LogStatus.INFO,
		 * "Clicked on feature : " + ObjectFeatureName.validationRules, YesNo.No);
		 * ThreadSleep(2000); if (validationRuleAlreadyExist(validationRuleName, 8) !=
		 * null) { log(LogStatus.INFO, "Validation Rule named: " + validationRuleName +
		 * " already exist, So not able to Create a New one", YesNo.No); driver.close();
		 * driver.switchTo().window(parentWindow);
		 * 
		 * return true;
		 * 
		 * } else { log(LogStatus.INFO, "Validation Rule named: " + validationRuleName +
		 * " not already exist, So going to Create a New one", YesNo.No);
		 * 
		 * if (click(driver, vaidationRuleNewButton(10), "New Button", action.BOOLEAN))
		 * { log(LogStatus.INFO, "Clicked on New button", YesNo.No);
		 * 
		 * if (validationRuleIframe(30) != null) { log(LogStatus.INFO,
		 * "Validation Rule Iframe Found, So going to switch into it", YesNo.No); if
		 * (CommonLib.switchToFrame(driver, 15, validationRuleIframe(30))) {
		 * log(LogStatus.INFO, "Switched into Validation Rule Iframe", YesNo.No);
		 * 
		 * if (sendKeys(driver, validationRuleName(30), validationRuleName,
		 * "vaidationRuleName", action.SCROLLANDBOOLEAN)) { log(LogStatus.PASS,
		 * "enter the value in Validation Rule Name : " + validationRuleName, YesNo.No);
		 * if (sendKeys(driver, validationRuleFormula(30), validationRuleFormula,
		 * "validationRuleFormula", action.SCROLLANDBOOLEAN)) { log(LogStatus.PASS,
		 * "enter the value in Validation Rule Formula : " + validationRuleFormula,
		 * YesNo.No);
		 * 
		 * if (sendKeys(driver, validationRuleMessage(30), validationRuleMessage,
		 * "validationRuleMessage", action.SCROLLANDBOOLEAN)) { log(LogStatus.PASS,
		 * "enter the value in Validation Rule Error Msg : " + validationRuleMessage,
		 * YesNo.No);
		 * 
		 * if (validationRuleErrorMsgLocation.contains("Field<break>")) {
		 * 
		 * String[] labelAndvalue = validationRuleErrorMsgLocation .split("<break>",
		 * -1);
		 * 
		 * if (click(driver, validationRuleErrorMsgLocation(labelAndvalue[0], 10),
		 * "New Button", action.BOOLEAN)) { log(LogStatus.INFO,
		 * "Clicked on Error Msg Location: " + validationRuleErrorMsgLocation,
		 * YesNo.No);
		 * 
		 * if (CommonLib.selectVisibleTextFromDropDown(driver,
		 * validationRuleFieldSelect(10), labelAndvalue[1], labelAndvalue[1]))
		 * 
		 * {
		 * 
		 * if (click(driver, validationRuleSaveButton(10), "validationRuleSaveButton",
		 * action.BOOLEAN)) { log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
		 * 
		 * CommonLib.switchToDefaultContent(driver); CommonLib.refresh(driver);
		 * CommonLib.switchToFrame(driver, 15, validationRuleIframe(30)); if
		 * (validationRuleCreatedDetailName(validationRuleName, 10) != null) {
		 * log(LogStatus.INFO, "Validation rule has been Created", YesNo.No);
		 * CommonLib.switchToDefaultContent(driver); driver.close();
		 * driver.switchTo().window(parentWindow); flag = true;
		 * 
		 * } else { log(LogStatus.PASS, "Validation rule has not been Created",
		 * YesNo.No); sa.assertTrue(false, "Validation rule has not been Created"); }
		 * 
		 * } else { log(LogStatus.PASS, "Not able to Click on Save Button", YesNo.No);
		 * sa.assertTrue(false, "Not able to Click on Save Button"); }
		 * 
		 * } else {
		 * 
		 * log(LogStatus.PASS, "Not able to select the field: " + labelAndvalue[1] +
		 * " in which we want the error Msg", YesNo.No); sa.assertTrue(false,
		 * "Not able to select the field: " + labelAndvalue[1] +
		 * " in which we want the error Msg"); }
		 * 
		 * } else { log(LogStatus.PASS, "Not able to Click on Error Msg Location: " +
		 * validationRuleErrorMsgLocation, YesNo.No); sa.assertTrue(false,
		 * "Not able to Click on Error Msg Location: " +
		 * validationRuleErrorMsgLocation); }
		 * 
		 * } else {
		 * 
		 * if (click(driver, validationRuleErrorMsgLocation(
		 * validationRuleErrorMsgLocation, 10), "New Button", action.BOOLEAN)) {
		 * log(LogStatus.INFO, "Clicked on Error Msg Location: " +
		 * validationRuleErrorMsgLocation, YesNo.No);
		 * 
		 * if (click(driver, validationRuleSaveButton(10), "New Button",
		 * action.BOOLEAN)) { log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
		 * CommonLib.switchToDefaultContent(driver); CommonLib.refresh(driver);
		 * CommonLib.switchToFrame(driver, 15, validationRuleIframe(30)); if
		 * (validationRuleCreatedDetailName(validationRuleName, 10) != null) {
		 * log(LogStatus.INFO, "Validation rule has been Created", YesNo.No);
		 * CommonLib.switchToDefaultContent(driver); driver.close();
		 * driver.switchTo().window(parentWindow); flag = true;
		 * 
		 * } else { log(LogStatus.PASS, "Validation rule has not been Created",
		 * YesNo.No); sa.assertTrue(false, "Validation rule has not been Created"); } }
		 * else { log(LogStatus.PASS, "Not able to Click on Save Button", YesNo.No);
		 * sa.assertTrue(false, "Not able to Click on Save Button"); }
		 * 
		 * } else { log(LogStatus.PASS, "Not able to Click on Error Msg Location: " +
		 * validationRuleErrorMsgLocation, YesNo.No); sa.assertTrue(false,
		 * "Not able to Click on Error Msg Location: " +
		 * validationRuleErrorMsgLocation); }
		 * 
		 * }
		 * 
		 * } else { log(LogStatus.PASS,
		 * "not able to enter the value in Validation Rule Error Msg : " +
		 * validationRuleMessage, YesNo.No); sa.assertTrue(false,
		 * "not able to enter the value in Validation Rule Error Msg : " +
		 * validationRuleMessage); }
		 * 
		 * } else { log(LogStatus.PASS,
		 * "not able to enter the value in Validation Rule Formula : " +
		 * validationRuleFormula, YesNo.No); sa.assertTrue(false,
		 * "not able to enter the value in Validation Rule Formula : " +
		 * validationRuleFormula); }
		 * 
		 * } else { log(LogStatus.PASS,
		 * "not able to enter the value in Validation Rule Name : " +
		 * validationRuleName, YesNo.No); sa.assertTrue(false,
		 * "not able to enter the value in Validation Rule Name : " +
		 * validationRuleName); }
		 * 
		 * } else { log(LogStatus.PASS,
		 * "Not able to Switched into Validation Rule Iframe", YesNo.No);
		 * sa.assertTrue(false, "Not able to Switched into Validation Rule Iframe"); }
		 * 
		 * } else { log(LogStatus.PASS,
		 * "Validation Rule Iframe not Found, So not going to switch into it",
		 * YesNo.No); sa.assertTrue(false,
		 * "Validation Rule Iframe not Found, So not going to switch into it"); } } else
		 * { log(LogStatus.PASS, "Not able to click on New button", YesNo.No);
		 * sa.assertTrue(false, "Not able to click on New button"); }
		 * 
		 * }
		 * 
		 * } else
		 * 
		 * { log(LogStatus.FAIL, "Not able to search object " + objectName.toString(),
		 * YesNo.Yes); sa.assertTrue(false, "Not able to search object " +
		 * objectName.toString());
		 * 
		 * }
		 * 
		 * }
		 * 
		 * } else { log(LogStatus.ERROR,
		 * "Not able to click on setup link so cannot create Fields Objects for custom object Marketing Event"
		 * , YesNo.Yes); sa.assertTrue(false,
		 * "Not able to click on setup link so cannot create Fields Objects for custom object Marketing Event"
		 * ); }
		 * 
		 * return flag; }
		 */

	/**
	 * @author Ankur Huria
	 * @param driver
	 * @param userName
	 * @param LabelswithCheck
	 * @param timeOut
	 * @return true if able to change permission for particular object for
	 *         particular type for particular user
	 */
	public boolean createValidationRule(object objectName, String fieldName, String validationRuleName,
			String validationRuleFormula, String validationRuleMessage, String validationRuleErrorMsgLocation) {
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		boolean flag = false;

		if (home.clickOnSetUpLink()) {
			String parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Valiation Rules for field: "
								+ fieldName + " of Object: " + objectName);
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Valiation Rules for field: "
								+ fieldName + " of Object: " + objectName,
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Valiation Rules for field: "
						+ fieldName + " of Object: " + objectName);
				return false;
			}

			if (searchStandardOrCustomObject(environment, mode, objectName)) {
				log(LogStatus.INFO, "click on Object : " + objectName, YesNo.No);
				ThreadSleep(2000);
				if (clickOnObjectFeature(environment, mode, objectName, ObjectFeatureName.validationRules)) {
					log(LogStatus.INFO, "Clicked on feature : " + ObjectFeatureName.validationRules, YesNo.No);
					ThreadSleep(2000);
					if (validationRuleAlreadyExist(validationRuleName, 8) != null) {
						log(LogStatus.INFO, "Validation Rule named: " + validationRuleName
								+ " already exist, So not able to Create a New one", YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindow);

						return true;

					} else {
						log(LogStatus.INFO, "Validation Rule named: " + validationRuleName
								+ " not already exist, So going to Create a New one", YesNo.No);

						if (click(driver, vaidationRuleNewButton(10), "New Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on New button", YesNo.No);

							if (validationRuleIframe(30) != null) {
								log(LogStatus.INFO, "Validation Rule Iframe Found, So going to switch into it",
										YesNo.No);
								if (CommonLib.switchToFrame(driver, 15, validationRuleIframe(30))) {
									log(LogStatus.INFO, "Switched into Validation Rule Iframe", YesNo.No);

									if (sendKeys(driver, validationRuleName(30), validationRuleName,
											"vaidationRuleName", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.PASS,
												"enter the value in Validation Rule Name : " + validationRuleName,
												YesNo.No);
										if (sendKeys(driver, validationRuleFormula(30), validationRuleFormula,
												"validationRuleFormula", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.PASS, "enter the value in Validation Rule Formula : "
													+ validationRuleFormula, YesNo.No);

											if (sendKeys(driver, validationRuleMessage(30), validationRuleMessage,
													"validationRuleMessage", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.PASS, "enter the value in Validation Rule Error Msg : "
														+ validationRuleMessage, YesNo.No);

												if (validationRuleErrorMsgLocation.contains("Field<break>")) {

													String[] labelAndvalue = validationRuleErrorMsgLocation
															.split("<break>", -1);

													if (click(driver,
															validationRuleErrorMsgLocation(labelAndvalue[0], 10),
															"New Button", action.BOOLEAN)) {
														log(LogStatus.INFO, "Clicked on Error Msg Location: "
																+ validationRuleErrorMsgLocation, YesNo.No);

														if (CommonLib.selectVisibleTextFromDropDown(driver,
																validationRuleFieldSelect(10), labelAndvalue[1],
																labelAndvalue[1]))

														{

															if (click(driver, validationRuleSaveButton(10),
																	"validationRuleSaveButton", action.BOOLEAN)) {
																log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);

																CommonLib.switchToDefaultContent(driver);
																CommonLib.refresh(driver);

																if (validationRuleIframe(30) != null) {
																	log(LogStatus.INFO,
																			"Validation Rule Iframe Found, So going to switch into it",
																			YesNo.No);
																	if (CommonLib.switchToFrame(driver, 15,
																			validationRuleIframe(30))) {
																		log(LogStatus.INFO,
																				"Switched into Validation Rule Iframe",
																				YesNo.No);

																		if (validationRuleName(30).getAttribute("value")
																				.equals("")) {
																			log(LogStatus.INFO,
																					"Validation rule has been Created",
																					YesNo.No);
																			CommonLib.switchToDefaultContent(driver);
																			driver.close();
																			driver.switchTo().window(parentWindow);
																			flag = true;

																		} else {
																			log(LogStatus.PASS,
																					"Validation rule has not been Created",
																					YesNo.No);
																			sa.assertTrue(false,
																					"Validation rule has not been Created");
																		}

																	} else {
																		log(LogStatus.PASS,
																				"Not able to Switched into Validation Rule Iframe",
																				YesNo.No);
																		sa.assertTrue(false,
																				"Not able to Switched into Validation Rule Iframe");
																	}

																} else {
																	log(LogStatus.PASS,
																			"Validation Rule Iframe not Found, So not going to switch into it",
																			YesNo.No);
																	sa.assertTrue(false,
																			"Validation Rule Iframe not Found, So not going to switch into it");
																}

																/*
																 * CommonLib.refresh(driver);
																 * CommonLib.switchToFrame(driver, 15,
																 * validationRuleIframe(30)); if
																 * (validationRuleCreatedDetailName(validationRuleName,
																 * 10) != null) { log(LogStatus.INFO,
																 * "Validation rule has been Created", YesNo.No);
																 * CommonLib.switchToDefaultContent(driver);
																 * driver.close();
																 * driver.switchTo().window(parentWindow); flag = true;
																 * 
																 * } else { log(LogStatus.PASS,
																 * "Validation rule has not been Created", YesNo.No);
																 * sa.assertTrue(false,
																 * "Validation rule has not been Created"); }
																 */

															} else {
																log(LogStatus.PASS, "Not able to Click on Save Button",
																		YesNo.No);
																sa.assertTrue(false,
																		"Not able to Click on Save Button");
															}

														} else {

															log(LogStatus.PASS,
																	"Not able to select the field: " + labelAndvalue[1]
																			+ " in which we want the error Msg",
																	YesNo.No);
															sa.assertTrue(false,
																	"Not able to select the field: " + labelAndvalue[1]
																			+ " in which we want the error Msg");
														}

													} else {
														log(LogStatus.PASS, "Not able to Click on Error Msg Location: "
																+ validationRuleErrorMsgLocation, YesNo.No);
														sa.assertTrue(false, "Not able to Click on Error Msg Location: "
																+ validationRuleErrorMsgLocation);
													}

												} else {

													if (click(driver,
															validationRuleErrorMsgLocation(
																	validationRuleErrorMsgLocation, 10),
															"New Button", action.BOOLEAN)) {
														log(LogStatus.INFO, "Clicked on Error Msg Location: "
																+ validationRuleErrorMsgLocation, YesNo.No);

														if (click(driver, validationRuleSaveButton(10), "New Button",
																action.BOOLEAN)) {
															log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);

															CommonLib.switchToDefaultContent(driver);
															CommonLib.refresh(driver);

															if (validationRuleIframe(30) != null) {
																log(LogStatus.INFO,
																		"Validation Rule Iframe Found, So going to switch into it",
																		YesNo.No);
																if (CommonLib.switchToFrame(driver, 15,
																		validationRuleIframe(30))) {
																	log(LogStatus.INFO,
																			"Switched into Validation Rule Iframe",
																			YesNo.No);

																	if (validationRuleName(30).getAttribute("value")
																			.equals("")) {
																		log(LogStatus.INFO,
																				"Validation rule has been Created",
																				YesNo.No);
																		CommonLib.switchToDefaultContent(driver);
																		driver.close();
																		driver.switchTo().window(parentWindow);
																		flag = true;

																	} else {
																		log(LogStatus.PASS,
																				"Validation rule has not been Created",
																				YesNo.No);
																		sa.assertTrue(false,
																				"Validation rule has not been Created");
																	}

																} else {
																	log(LogStatus.PASS,
																			"Not able to Switched into Validation Rule Iframe",
																			YesNo.No);
																	sa.assertTrue(false,
																			"Not able to Switched into Validation Rule Iframe");
																}

															} else {
																log(LogStatus.PASS,
																		"Validation Rule Iframe not Found, So not going to switch into it",
																		YesNo.No);
																sa.assertTrue(false,
																		"Validation Rule Iframe not Found, So not going to switch into it");
															}

															/*
															 * CommonLib.refresh(driver);
															 * CommonLib.switchToFrame(driver, 15,
															 * validationRuleIframe(30)); if
															 * (validationRuleCreatedDetailName(validationRuleName, 10)
															 * != null) { log(LogStatus.INFO,
															 * "Validation rule has been Created", YesNo.No);
															 * CommonLib.switchToDefaultContent(driver); driver.close();
															 * driver.switchTo().window(parentWindow); flag = true;
															 * 
															 * } else { log(LogStatus.PASS,
															 * "Validation rule has not been Created", YesNo.No);
															 * sa.assertTrue(false,
															 * "Validation rule has not been Created"); }
															 */

														} else {
															log(LogStatus.PASS, "Not able to Click on Save Button",
																	YesNo.No);
															sa.assertTrue(false, "Not able to Click on Save Button");
														}

													} else {
														log(LogStatus.PASS, "Not able to Click on Error Msg Location: "
																+ validationRuleErrorMsgLocation, YesNo.No);
														sa.assertTrue(false, "Not able to Click on Error Msg Location: "
																+ validationRuleErrorMsgLocation);
													}

												}

											} else {
												log(LogStatus.PASS,
														"not able to enter the value in Validation Rule Error Msg : "
																+ validationRuleMessage,
														YesNo.No);
												sa.assertTrue(false,
														"not able to enter the value in Validation Rule Error Msg : "
																+ validationRuleMessage);
											}

										} else {
											log(LogStatus.PASS,
													"not able to enter the value in Validation Rule Formula : "
															+ validationRuleFormula,
													YesNo.No);
											sa.assertTrue(false,
													"not able to enter the value in Validation Rule Formula : "
															+ validationRuleFormula);
										}

									} else {
										log(LogStatus.PASS, "not able to enter the value in Validation Rule Name : "
												+ validationRuleName, YesNo.No);
										sa.assertTrue(false, "not able to enter the value in Validation Rule Name : "
												+ validationRuleName);
									}

								} else {
									log(LogStatus.PASS, "Not able to Switched into Validation Rule Iframe", YesNo.No);
									sa.assertTrue(false, "Not able to Switched into Validation Rule Iframe");
								}

							} else {
								log(LogStatus.PASS, "Validation Rule Iframe not Found, So not going to switch into it",
										YesNo.No);
								sa.assertTrue(false,
										"Validation Rule Iframe not Found, So not going to switch into it");
							}
						} else {
							log(LogStatus.PASS, "Not able to click on New button", YesNo.No);
							sa.assertTrue(false, "Not able to click on New button");
						}

					}

				} else

				{
					log(LogStatus.FAIL, "Not able to search object " + objectName.toString(), YesNo.Yes);
					sa.assertTrue(false, "Not able to search object " + objectName.toString());

				}

			}

		} else {
			log(LogStatus.ERROR,
					"Not able to click on setup link so cannot create Fields Objects for custom object Marketing Event",
					YesNo.Yes);
			sa.assertTrue(false,
					"Not able to click on setup link so cannot create Fields Objects for custom object Marketing Event");
		}

		return flag;
	}

	public boolean defaultRecordTypeSelect(String profileName, String objectName, String defaultRecordType) {
		boolean flag = false;
		String xPath = "";
		WebElement ele = null;

		ThreadSleep(5000);
		if (CommonLib.switchToFrame(driver, 50, getuserProfileIframe(50))) {
			ThreadSleep(5000);
			log(LogStatus.INFO, "Successfully switched to User Profile Iframe", YesNo.No);
			xPath = "//div[@class='bRelatedList']//a[text()='" + profileName + "']";
			ele = CommonLib.FindElement(driver, xPath, profileName + " profile name", action.SCROLLANDBOOLEAN, 50);
			if (CommonLib.clickUsingJavaScript(driver, ele, profileName + " profile name", action.BOOLEAN)) {
				log(LogStatus.INFO, "Successfully clicked on the " + profileName + " profile name", YesNo.No);
				ThreadSleep(12000);
				CommonLib.switchToDefaultContent(driver);
				ThreadSleep(2000);
				if (CommonLib.switchToFrame(driver, 50, getProfileIframe(50))) {
					ThreadSleep(5000);
					log(LogStatus.INFO, "Successfully switched to Profile Iframe", YesNo.No);
					xPath = "//h4[contains(text(),'Record Type Settings')]/ancestor::tbody//td[text()='" + objectName
							+ "s']/following-sibling::td/a";
					ele = FindElement(driver, xPath, objectName.toString() + " edit button", action.SCROLLANDBOOLEAN,
							20);
					if (click(driver, ele, objectName.toString() + " edit button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on edit button of " + objectName.toString(), YesNo.Yes);
						ThreadSleep(9000);
						CommonLib.switchToDefaultContent(driver);
						if (CommonLib.switchToFrame(driver, 50, geteditRecordTypeIframe(50))) {

							log(LogStatus.INFO, "Successfully switched to edit record Iframe", YesNo.No);
							ThreadSleep(5000);

							if (CommonLib.selectVisibleTextFromDropDown(driver, defaultRecordTypeOption(15),
									"Default Record Type", defaultRecordType)) {

								log(LogStatus.INFO, "Successfully Select the Dafault Record Type: " + defaultRecordType
										+ " for Object: " + objectName, YesNo.No);

								if (click(driver, getSaveButton(30), "Save button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on the save button", YesNo.No);

									ThreadSleep(5000);
									CommonLib.switchToDefaultContent(driver);
									CommonLib.switchToDefaultContent(driver);
									CommonLib.refresh(driver);

									if (CommonLib.switchToFrame(driver, 50, getProfileIframe(50))) {

										log(LogStatus.INFO, "Successfully switched to Profile Iframe", YesNo.No);
										ThreadSleep(5000);

										xPath = "//h4[contains(text(),'Record Type Settings')]/ancestor::tbody//td[text()='"
												+ objectName + "s']/following-sibling::td/a";
										ele = FindElement(driver, xPath, objectName.toString() + " edit button",
												action.SCROLLANDBOOLEAN, 20);
										if (ele != null) {
											log(LogStatus.INFO, "Successfully select the Default Record Type: "
													+ defaultRecordType + " for Object: " + objectName, YesNo.No);
											flag = true;
										} else {
											log(LogStatus.ERROR,
													"Not Successfully select the Default Record Type: "
															+ defaultRecordType + " for Object: " + objectName,
													YesNo.Yes);
										}

									} else {
										log(LogStatus.ERROR, "Not able to switched to Profile Iframe", YesNo.Yes);
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);
								}

							} else {
								log(LogStatus.ERROR, "Not able to Select default RecordType: " + defaultRecordType
										+ " as it is not available", YesNo.No);
							}

						} else {
							log(LogStatus.ERROR, "Not able to switch to edit record Iframe", YesNo.No);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on edit button of " + objectName.toString(), YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not able to switched to Profile Iframe", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on the " + profileName + " profile name", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to switched to User profile Iframe", YesNo.Yes);
		}

		return flag;
	}

}
