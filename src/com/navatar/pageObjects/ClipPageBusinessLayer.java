//package com.navatar.pageObjects;
//
//
//import static com.navatar.generic.CommonLib.*;
//import static com.navatar.generic.CommonVariables.*;
//
//import java.util.Date;
//
//import java.sql.DriverAction;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import com.navatar.generic.BaseLib;
//import com.navatar.generic.CommonLib;
//import com.navatar.generic.EnumConstants.PageLabel;
//import com.navatar.generic.EnumConstants.PageName;
//import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
//import com.navatar.generic.EnumConstants.YesNo;
//import com.navatar.generic.EnumConstants.action;
//import com.relevantcodes.extentreports.LogStatus;
//
//
//public class ClipPageBusinessLayer extends ClipPage {
//
//	public ClipPageBusinessLayer(WebDriver driver) {
//		super(driver);
//
//	}
//
//
//	public boolean UpdateClipName(String projectName, String clipName, int timeOut) {
//		boolean flag = true;
//		WebElement ele;
//		ThreadSleep(2000);
//		if (clickOnShowMoreActionDownArrow(projectName, PageName.ClipsPage, ShowMoreActionDropDownList.Edit, 10)) {
//			ThreadSleep(2000);
//			ele = getLabelTextBox(projectName, PageName.ThemesPage.toString(), PageLabel.Theme_Name.toString(), timeOut);
//			if (sendKeys(driver, ele, themeName, "Theme Name", action.BOOLEAN)) {
//				appLog.info("Successfully Entered value on Theme Name TextBox : " + themeName);
//			} else {
//				appLog.error("Not Able to Entered value on Theme Name TextBox : " + themeName);
//			}
//			ThreadSleep(1000);
//			ThreadSleep(2000);
//			if (click(driver, getCustomTabSaveBtn(projectName, 30), "Save Button", action.SCROLLANDBOOLEAN)) {
//				appLog.error("Click on save Button");
//				flag = true;
//				ThreadSleep(2000);
//			} else {
//				appLog.error("Not Able to Click on save Button");
//			}
//		} else {
//			appLog.error("Not Able to Click on edit Button");
//		}
//		return flag;
//	}
//
//	
//}
