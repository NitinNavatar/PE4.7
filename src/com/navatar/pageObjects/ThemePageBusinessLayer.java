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
			ele = getLabelTextBox(projectName, PageName.ThemesPage.toString(), PageLabel.Theme_Name.toString(), timeOut);
			if (sendKeys(driver, ele, themeName, "Theme Name", action.BOOLEAN)) {
				appLog.info("Successfully Entered value on Theme Name TextBox : " + themeName);
			} else {
				appLog.error("Not Able to Entered value on Theme Name TextBox : " + themeName);
			}
			ThreadSleep(1000);
			ThreadSleep(2000);
			if (click(driver, getCustomTabSaveBtn(projectName, 30), "Save Button", action.SCROLLANDBOOLEAN)) {
				appLog.error("Click on save Button");
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

}
