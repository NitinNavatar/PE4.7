package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import java.util.Date;

import java.sql.DriverAction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;

public class ThemePageBusinessLayer extends ThemePage {

	public ThemePageBusinessLayer(WebDriver driver) {
		super(driver);

	}

	public boolean createTheme(String projectName, String tabName, String themeName, String themeDescription) {

		boolean flag = false;

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		if (lp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
			if (CommonLib.click(driver, newThemeButton(30), "newThemeButton", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the New theme Button", YesNo.No);

				if (CommonLib.click(driver, noButton(30), "noButton", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the No Button", YesNo.No);

					if (!themeName.isEmpty() || !themeName.equals("") || themeName != null) {

						if (CommonLib.sendKeys(driver, themeNameInputBox(30), themeName, "Theme Name: " + themeName,
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, themeName + " value has been passed in Theme Name", YesNo.No);
						} else {
							log(LogStatus.ERROR, themeName + " value is not passed in Theme Name", YesNo.No);
							return false;
						}
					}
					if (!themeDescription.isEmpty() || !themeDescription.equals("") || themeDescription != null) {

						if (sendKeys(driver, themeDescription(10), themeDescription,
								"themeDescription: " + themeDescription, action.SCROLLANDBOOLEAN)) {

							log(LogStatus.INFO, themeDescription + " value has been passed in Theme Description",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, themeDescription + " value is not passed in Theme Description",
									YesNo.No);
							return false;
						}

					}

					if (CommonLib.click(driver, saveButton(30), "Theme save button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on save button", YesNo.No);

						CommonLib.ThreadSleep(2000);
						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(2000);
						if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeName, "Theme Search Box ",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, themeName + " value has been passed in Theme Search Box", YesNo.No);

							if (recordInTableOfTheme(themeName, 15) != null) {
								log(LogStatus.INFO, "Verified Theme " + themeName + " Has Been Created", YesNo.No);

								flag = true;

							} else {
								log(LogStatus.ERROR, "Theme " + themeName + " is not created", YesNo.No);

							}

						} else {
							log(LogStatus.ERROR, themeName + " value is not passed in Theme Search Box", YesNo.No);

						}

					} else {
						log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on the No Button", YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the New Theme Button", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);

		}

		return flag;

	}

	public boolean UpdateThemeName(String projectName, String themeName, int timeOut) {
		boolean flag = true;
		WebElement ele;
		ThreadSleep(2000);
		if (clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage, ShowMoreActionDropDownList.Edit, 10)) {
			ThreadSleep(2000);
			ele = getLabelTextBox(projectName, PageName.ThemesPage.toString(), PageLabel.Theme_Name.toString(),
					timeOut);
			if (sendKeys(driver, ele, themeName, "Theme Name", action.BOOLEAN)) {
				appLog.info("Successfully Entered value on Theme Name TextBox : " + themeName);
			} else {
				appLog.error("Not Able to Entered value on Theme Name TextBox : " + themeName);
			}
			ThreadSleep(2000);
			if (click(driver, getCustomTabSaveBtn(projectName, 30), "Save Button", action.SCROLLANDBOOLEAN)) {
				appLog.info("Click on save Button");
				flag = true;
				ThreadSleep(2000);
			} else {
				appLog.error("Not Able to Click on save Button");
			}
		} else {
			appLog.error("Not Able to Click on edit Button");
		}
		return flag;
	}

	public boolean clickOnAlreadyCreatedItem(String projectName, String alreadyCreated, int timeout) {
		boolean flag = false;
		String xpath = "";
		WebElement ele;
		ele = null;

		refresh(driver);
		ThreadSleep(8000);

		if (sendKeys(driver, getSearchIcon(10), alreadyCreated + "\n", "Search Icon Text", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(5000);

			xpath = "//table[contains(@class,'slds-table')]//tbody//tr//span//*[text()='" + alreadyCreated + "']";
			ele = FindElement(driver, xpath, alreadyCreated, action.BOOLEAN, 10);
			ThreadSleep(2000);

			if (clickUsingJavaScript(driver, ele, alreadyCreated, action.BOOLEAN)) {
				flag = true;
			} else {
				appLog.error("Not able to Click on Already Created : " + alreadyCreated);
			}
		} else {
			appLog.error("Not able to enter value on Search Box");
		}
		return flag;
	}

	public boolean createAddToTheme(String projectName, String tabName, String themeName, String accountSectionName,
			String recordName) {
		boolean flag = false;
		String parentId = null;

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		if (lp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeName, "Theme Search Box ",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeName + " value has been passed in Theme Search Box", YesNo.No);

				if (recordInTableOfTheme(themeName, 5) != null) {
					log(LogStatus.INFO, "Verified Theme " + themeName + " Has Been Created", YesNo.No);

					if (CommonLib.clickUsingJavaScript(driver, recordInTableOfTheme(themeName, 5), "Theme Name: " + themeName,
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the Theme: " + themeName, YesNo.No);
						parentId = CommonLib.switchOnWindow(driver);
						if (parentId != null) {
							log(LogStatus.INFO, "Switched to New Window", YesNo.No);

							if (CommonLib.click(driver, plusIconButtonInThemeOfAccount(accountSectionName, 10),
									"Theme Name: " + themeName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Plus Icon Button for Account: " + accountSectionName,
										YesNo.No);

								if (sendKeys(driver, addToThemePopUpSearchBox(20), recordName, "Theme Search Box",
										action.BOOLEAN)) {
									log(LogStatus.INFO, "Able to Pass the Value:  " + recordName, YesNo.No);

									if (CommonLib.click(driver, addToThemePopUpSearchBoxDropDownValue(recordName, 10),
											"Dropdown Value: " + recordName, action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Dropdown Value: " + recordName, YesNo.No);

										if (CommonLib.clickUsingJavaScript(driver,
												addToThemePopUpSaveButton(10),
												"Dropdown Value: " + recordName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);

											if (successMsg(10) != null) {
												log(LogStatus.INFO,
														"Success Msg is showing, So Add to Theme Created for record: "
																+ recordName,
														YesNo.No);
												flag = true;
											} else {
												log(LogStatus.ERROR,
														"Success Msg is not showing, So Add to Theme is not Created for record: "
																+ recordName,
														YesNo.No);
											}

										} else {
											log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.No);

										}

									} else {
										log(LogStatus.ERROR, "Not Able to Click on Dropdown Value: " + recordName,
												YesNo.No);

									}

								} else {
									log(LogStatus.ERROR, "Not Able to Pass the Value: " + recordName, YesNo.No);
								}

							} else {
								log(LogStatus.ERROR,
										"Not Able to Click on Plus Icon Button for Account: " + accountSectionName,
										YesNo.No);

							}

						} else {
							log(LogStatus.ERROR, "Not Able to Switch to New Window", YesNo.No);
						}

					} else {
						log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeName, YesNo.No);

					}

				} else {
					log(LogStatus.ERROR, "Theme " + themeName + " is present there", YesNo.No);

				}

			} else {
				log(LogStatus.ERROR, themeName + " value is not passed in Theme Search Box", YesNo.No);

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);

		}

		return flag;
	}

}
