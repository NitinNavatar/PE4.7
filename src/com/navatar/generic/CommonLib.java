package com.navatar.generic;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.Value;
import org.apache.poi.ss.formula.ptg.ParenthesisPtg;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;

import static com.navatar.generic.EnumConstants.*;

import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.AppListeners.*;
import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.FindElements;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.previousOrForwardDateAccordingToTimeZone;
import static com.navatar.generic.CommonLib.scrollDownThroughWebelement;
import static org.testng.Assert.fail;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommonLib extends EnumConstants implements Comparator<String> {

	public static List<String> excludedMethods = null;
	public static String openFolderScriptPath = System.getProperty("user.dir") + "\\AutoIT\\OpenFolder.exe";
	public static String closeFolderScriptPath = System.getProperty("user.dir") + "\\AutoIT\\CloseFolder.exe";
	public static String errorMessage;
	public static int clickRetryCount = 0;

	/*********************************************
	 * ENUM
	 **************************************************************/

	/*****************************************
	 * Common Utilities
	 ***********************************************************/

	/**
	 * @author Ankur Rana
	 * @Description: Execute the test cases according to "Execute" column
	 */
	public static void execution() {
		excludedMethods = new ArrayList<String>();
		excludedMethods.addAll(ExcelUtils.getTcsToRun("testcases"));
		for (int i = 0; i < excludedMethods.size(); i++) {
			AppListeners.status.put(excludedMethods.get(i), "Skip: Disabled in excel.");
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @description wait till page get loaded
	 */
	public static void waitForPageLoad(WebDriver driver) {

		Wait<WebDriver> wait = new WebDriverWait(driver, 60);
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				if (isAlertPresent(driver)) {
					AppListeners.appLog
							.info("Message of the alert: " + switchToAlertAndGetMessage(driver, 10, action.GETTEXT));
					// switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
					// driver.navigate().refresh();
					return true;
				}
				return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param expectedTitle
	 * @param timeOut
	 * @return boolean
	 * @description match the expected and actual title of the page.
	 */
	public static boolean matchTitle(WebDriver driver, String expectedTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		try {
			wait.until(ExpectedConditions.titleContains(expectedTitle));
			appLog.info("Title Match Successfull. Expected: " + expectedTitle + "\tActual Title: " + driver.getTitle());
			return true;
		} catch (Exception e) {
			appLog.info("Title Match Failed. Expected: " + expectedTitle + "\tActual Title: " + driver.getTitle());
			return false;
		}

	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param webElement
	 * @param elementName
	 * @param value
	 * @return boolean
	 * @description selecte the value from the drop down
	 */
	public static boolean selectVisibleTextFromDropDown(WebDriver driver, WebElement webElement, String elementName,
			Object value) {
		try {
			checkElementVisibility(driver, webElement, elementName, 60);
			Select select = new Select(webElement);
			if (value instanceof Integer) {
				appLog.info("Selecting value by index: " + value);
				select.selectByIndex(Integer.parseInt(value.toString()));
			} else {
				try {
					select.selectByVisibleText(value.toString());
				} catch (Exception e) {
					select.selectByValue(value.toString());
				}
			}
			AppListeners.appLog.info("Selected " + value.toString() + " from the drop down.");
			return true;
		} catch (Exception e) {
			if (value instanceof Integer) {
				appLog.info("Index passed is not found.");
			} else {
				appLog.info("'" + value.toString() + "' is not available in drop down.");
			}
			return false;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param element
	 * @param elementName
	 * @param attributeOrText
	 * @return String
	 * @description Gets the selected value from the drop down.
	 */
	public static String getSelectedOptionOfDropDown(WebDriver driver, WebElement element, String elementName,
			String attributeOrText) {
		if (checkElementVisibility(driver, element, elementName, 60)) {
			Select select = new Select(element);
			if (attributeOrText.equalsIgnoreCase("text"))
				return select.getFirstSelectedOption().getText().trim();
			else
				return select.getFirstSelectedOption().getAttribute(attributeOrText);
		} else {
			return null;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param element
	 * @param elementName
	 * @return List<WebElement>
	 * @description Gets all the values present in the drop down
	 */
	public static List<WebElement> allOptionsInDropDrop(WebDriver driver, WebElement element, String elementName) {
		if (checkElementVisibility(driver, element, elementName, 60)) {
			Select select = new Select(element);
			return select.getOptions();
		} else {
			return null;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param yAxis1
	 * @param yAxis2
	 * @return void
	 * @description Scroll the window based on the pixels
	 */
	public static void windowScrollYAxis(WebDriver driver, int yAxis1, int yAxis2) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(" + yAxis1 + "," + yAxis2 + ")");
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param timeOut
	 * @param locator
	 * @return boolean
	 * @description switch to the frame based on the locator passed.
	 */
	public static boolean switchToFrame(WebDriver driver, int timeOut, Object locator) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		boolean returnValue = true;
		String xpath = "";
		try {
			xpath = locator.toString().split("->")[1].split(": ")[1]
					.substring(0, locator.toString().split("->")[1].split(": ")[1].length() - 1).trim();
		} catch (Exception e) {
			System.out.println("getting exception while switching in iframe");
			xpath = "ABC";
		}
		for (int i = 0; i < 2; i++)
			try {
				if (locator instanceof By) {
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By) locator));
				} else if (locator instanceof WebElement) {
					System.err.println(i);
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((WebElement) locator));
					System.err.println("Successfully switched to frame.");
				} else if (locator instanceof String) {
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((String) locator));
				} else if (locator instanceof Integer) {
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((Integer) locator));
				} else {
					appLog.info("Locator you have passed is invalid. Kindly pass By, WebElement, String, Int value.");
					returnValue = false;
				}
				if (returnValue == true) {
					WebElement ele = FindElement(driver, "//*[contains(*,'java.net.SocketExeption')]", null,
							action.BOOLEAN, 0);
					System.err.println("line 308");
					if (ele != null) {
						System.err.println("inside ele!=null");
						driver.navigate().refresh();
					} else if (xpath.equalsIgnoreCase("//iframe[@title='AlertHomeGateway']")) {
						System.err.println("inside xpath Match");
						System.out.println("Home page alert section code running");
						if (CommonLib.click(driver, CommonLib.FindElement(driver,
								"//*[contains(@id,'grid-header-0-box-text')][text()='Date']", null, action.BOOLEAN, 10),
								null, action.BOOLEAN))
							CommonLib.ThreadSleep(1000);
						if (CommonLib.click(driver, CommonLib.FindElement(driver,
								"//*[contains(@id,'grid-header-0-box-text')][text()='Date']", null, action.BOOLEAN, 10),
								null, action.BOOLEAN))
							CommonLib.ThreadSleep(1000);
						break;
					} else {
						break;
					}
				} else {
					break;
				}
			} catch (Exception e) {
				appLog.info("Required frame is not available on this page.");
				returnValue = false;
				break;
			}
		return returnValue;
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param webElement
	 * @description To move mouse focus to the particular element
	 */
	public static boolean mouseOverOperation(WebDriver driver, WebElement webElement) {
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(webElement).build().perform();
			appLog.info("Mouse over Successfull");
		} catch (Exception e) {
			appLog.info("Mouse over UnSuccessfull");
			return false;
		}
		return true;
	}

	public static boolean mouseOverClickOperation(WebDriver driver, WebElement webElement) {
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(webElement).click().build().perform();
			appLog.info("Mouse over and click Successfull");
		} catch (Exception e) {
			appLog.info("Mouse over UnSuccessfull");
			return false;
		}
		return true;
	}

	public static boolean mouseHoverJScript(WebDriver driver, WebElement HoverElement, String elementName,
			int timeOut) {
		boolean flag = true;
		try {
			if (isDisplayed(driver, HoverElement, "visibility", timeOut, elementName) != null) {
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				((JavascriptExecutor) driver).executeScript(mouseOverScript, HoverElement);

			} else {
				System.out.println("Element was not visible to hover " + "\n");
				flag = false;
			}
		} catch (StaleElementReferenceException e) {
			System.out.println(
					"Element with " + HoverElement + "is not attached to the page document" + e.getStackTrace());
			flag = false;
		} catch (NoSuchElementException e) {
			System.out.println("Element " + HoverElement + " was not found in DOM" + e.getStackTrace());
			flag = false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error occurred while hovering" + e.getStackTrace());
			flag = false;
		}
		return flag;
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param Element
	 * @param elementName
	 * @return boolean
	 * @description scrolls the window to the element view.
	 */
	public static boolean scrollDownThroughWebelement(WebDriver driver, WebElement Element, String elementName) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Element);
			if (elementName != "")
				System.out.println("Window Scrolled to " + elementName);
			return true;
		} catch (Exception e) {
			if (elementName != "")
				System.err.println("Can not scrolled Window to " + elementName);
			return false;
		}
	}

	public static boolean scrollDownThroughWebelementInCenter(WebDriver driver, WebElement Element,
			String elementName) {
		try {
			JavascriptExecutor j = (JavascriptExecutor) driver;
			j.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", Element);
			if (elementName != "")
				System.out.println("Window Scrolled to " + elementName);
			return true;
		} catch (Exception e) {
			if (elementName != "")
				System.err.println("Can not scrolled Window to " + elementName);
			return false;
		}
	}

	/**
	 * @author Ankit Jaiswal
	 * @param driver
	 * @param element
	 * @param elementName
	 * @return Text/null
	 */
	public static String getValueFromElementUsingJavaScript(WebDriver driver, WebElement element, String elementName) {
		String text = null;
		try {
			// text=(String) ((JavascriptExecutor) driver).executeScript("return
			// $('"+Jquery+"')[0].value");
			text = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", element);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Cannot get the value from Element: " + elementName);
		}
		return text;
	}

	/**
	 * @author Ankur Rana
	 * @param filesFromExcel
	 * @param listOfFileName
	 * @description compare the multiple string values with the list of values and
	 *              if not matched then terminates the execution then and there
	 */
	public static void compareMultipleListWithAssertion(String filesFromExcel, List<String> listOfFileName) {

		String[] fileName = filesFromExcel.split(",");
		for (int i = 0; i < fileName.length; i++) {
			for (int j = 0; j < listOfFileName.size(); j++) {
				AppListeners.appLog.info("Comparing:>>" + fileName[i] + ">>With:>>" + listOfFileName.get(j));
				if (fileName[i].equalsIgnoreCase(listOfFileName.get(j))) {
					Assert.assertTrue(true);
					AppListeners.appLog.info("Document: " + fileName[i] + " is matched.");
					break;
				} else if (j == listOfFileName.size() - 1) {
					Assert.assertTrue(false, "Document: " + fileName[i] + " is not matched.");
				}
			}
		}
	}

	/**
	 * @author Ankur Rana
	 * @param filesFromExcel
	 * @param listOfFileName
	 * @return boolean
	 * @description compare the multiple string values with the list of values and
	 *              return boolean based on that
	 */
	public static boolean compareMultipleListWithoutAssertion(String filesFromExcel, List<String> listOfFileName) {

		String[] fileName = filesFromExcel.split("<break>");
		int countFiles = 0;
		try {
			if (fileName.length != 0) {
				for (int i = 0; i < fileName.length; i++) {
					for (int j = 0; j < listOfFileName.size(); j++) {
						AppListeners.appLog.info("Comparing:>>" + fileName[i] + ">>With:>>" + listOfFileName.get(j));
						if (fileName[i].equalsIgnoreCase(listOfFileName.get(j))) {
							AppListeners.appLog.info("Document: " + fileName[i] + " is matched.");
							countFiles++;
							break;
						} else if (j == listOfFileName.size() - 1) {
							AppListeners.appLog.info("Document: " + fileName[i] + " is not matched.");
							// BaseLib.sa.assertTrue(false,"Document: " + fileName[i] + " is not matched.");
						}
					}
				}
				if (fileName.length == countFiles) {
					AppListeners.appLog.info("All the files are matched.");
					return true;
				} else {
					AppListeners.appLog.info("Files are not matched.");
					return false;
				}
			} else {
				AppListeners.appLog.info("No Data In Excel Cell.");
				return false;
			}
		} catch (Exception e) {
			AppListeners.appLog.info("There are no file to compare.");
			return false;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @return String
	 * @description switch to the very next window open and return the parent
	 *              session id.
	 */
	public static String switchOnWindow(WebDriver driver) {
		int limitForWait = 0;
		String parentWindowId = driver.getWindowHandle();
		String childWindowID = null;
		Set<String> s1 = null;
		while (true) {
			s1 = driver.getWindowHandles();
			if (s1.size() <= 1) {
				try {
					Thread.sleep(500);
					limitForWait++;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (limitForWait > 200) {
					appLog.info("No new window is open for switch.");
					return null;
				}
			} else {
				break;
			}
		}
		Iterator<String> I1 = s1.iterator();
		while (I1.hasNext()) {
			childWindowID = I1.next();
			System.out.println("parent window: " + parentWindowId + ">>>>> child window: " + childWindowID);
			if (!parentWindowId.equals(childWindowID)) {
				System.out.println("child window :" + childWindowID);
				try {
					driver.switchTo().window(childWindowID);
				} catch (NoSuchWindowException e) {
					appLog.info("Cannot switch to new window due to: ");
					e.printStackTrace();
					return null;
				}
				appLog.info("Successfully switched to new window.");
				break;
			}
		}
		return parentWindowId;
	}

	/**
	 * @author Ankit Jaiswal
	 * @return String
	 * @description Gets the Public IP Address of the system and return it into an
	 *              string
	 */
	public static String getPublicIPAddress() {
		String systemipaddress = "";
		try {
			URL url_name = new URL("http://bot.whatismyipaddress.com");

			BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));

			// reads system IPAddress
			systemipaddress = sc.readLine().trim();
		} catch (Exception e) {
			systemipaddress = "Cannot Execute Properly";
		}
		System.out.println("Public IP Address: " + systemipaddress + "\n");

		return systemipaddress;
	}

	/**
	 * @author Ankit Jaiswal
	 * @return String
	 * @description get the current system date in MM/dd/yyyy format.
	 */
	public static String getSystemDate() {
		Date myDate = new Date();
		String date = new SimpleDateFormat("MM/dd/yyyy").format(myDate);
		return date;

	}

	/**
	 * @author Ankur Rana
	 * @param format
	 * @return String
	 * @description get the current system date according the format passed.
	 */
	public static String getSystemDate(String format) {
		Date myDate = new Date();
		String date = new SimpleDateFormat(format).format(myDate);
		return date;

	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param pathToTraverse
	 * @param fileName
	 * @return boolean
	 * @description traverse through the folder structure present on the online
	 *              import window and selects the files required
	 */
	public static boolean traverseImport(WebDriver driver, String pathToTraverse, String fileName) {
		boolean flag = true;
		String folderStruct[] = pathToTraverse.split("/");
		String fileNames[] = fileName.split("<break>");
		String xpath1 = "//span[text()='";
		String xpath2 = "/../preceding-sibling::span[2]";
		String xpath3 = "/following-sibling::ul//span[text()='";
		String xpath4 = "']";
		String xpath5 = xpath1 + folderStruct[0] + xpath4 + xpath2;
		for (int i = 0; i < folderStruct.length; i++) {
			WebElement ele = FindElement(driver, xpath5, "+Sign In Front Of Folder: " + folderStruct[i], action.BOOLEAN,
					20);
			if (ele != null) {
				if (click(driver, ele, "Folder: " + folderStruct[i], action.SCROLLANDBOOLEAN)) {
					if (i == folderStruct.length - 1) {
						break;
					}
					xpath5 = xpath5 + xpath3 + folderStruct[i + 1] + xpath4 + xpath2;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		System.out.println("selecting files");
		for (int j = 0; j < fileNames.length; j++) {
			if (!click(driver, FindElement(driver, "//li[text()='" + fileNames[j] + "']/input", "File: " + fileNames[j],
					action.BOOLEAN, 40), "File: " + fileNames[j] + " CheckBox", action.SCROLLANDBOOLEAN)) {
				BaseLib.sa.assertTrue(false, "File Not Imported: " + fileNames[j]);
				flag = false;
			} else {
				appLog.info(fileNames[j] + " :File successfully selected.");
			}
		}
		return flag;
	}

	/**
	 * @author Ankur Rana
	 * @param fileName
	 * @return String
	 * @description Takes the screenshot and returns the path
	 */
	public static String screenshot(String fileName) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		Date date = new Date();
		TakesScreenshot tss = (TakesScreenshot) BaseLib.edriver;
		String screenshotPath = System.getProperty("user.dir") + "//screenshot//" + fileName + df.format(date) + ".png";
		File src = tss.getScreenshotAs(OutputType.FILE);
		File dest = new File(screenshotPath);

		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return screenshotPath;
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param element
	 * @param time
	 * @param scrollingToElement
	 * @return boolean
	 * @description check the element visiblity after scrolling to the passed
	 *              scrollingToElement
	 */
	public static boolean checkElementVisibility(WebDriver driver, WebElement element, int time,
			String scrollingToElement) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		WebElement ClickElement = null;
		try {
			AppListeners.appLog.info("Checking the visibility of: " + scrollingToElement);
			ClickElement = wait.until(ExpectedConditions.visibilityOf(element));
			if (ClickElement != null) {
				scrollDownThroughWebelement(driver, element, scrollingToElement);
				return true;
			} else {
				scrollDownThroughWebelement(driver, element, scrollingToElement);
				return false;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			failedMethod(e);
			// System.out.println("Kindly show the exception to Ankur.");
			return false;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param element
	 * @param time
	 * @return boolean
	 * @description check the element visiblity without scroll
	 */
	public static boolean checkElementVisibility(WebDriver driver, WebElement element, String elementName, int time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		WebElement ClickElement = null;
		try {
			// IPListeners.ipLog.info("Checking the visibility of:
			// "+elementName);
			ClickElement = wait.until(ExpectedConditions.visibilityOf(element));
			if (ClickElement != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			failedMethod(e);
			// System.out.println("Kindly show the exception to Ankur.");
			return false;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param element
	 * @param time
	 * @param scrollingToElement
	 * @return boolean
	 * @description check the visibility of list of webelement
	 */
	public static boolean checkElementsVisibility(WebDriver driver, List<WebElement> element, String elementName,
			int time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		List<WebElement> ClickElement = null;
		try {
			AppListeners.appLog.info("Checking the visibility of: " + elementName);
			ClickElement = wait.until(ExpectedConditions.visibilityOfAllElements(element));
			if (!ClickElement.isEmpty()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// System.out.println("Kindly show the exception to Ankur.");
			return false;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param element
	 * @param waitOn
	 * @param timeout
	 * @param elementName
	 * @return WebElement
	 * @description based on the waiton(Visibility,clickable) condition waits for
	 *              the element
	 */
	public static WebElement isDisplayed(WebDriver driver, WebElement element, String waitOn, int timeout,
			String elementName) {
		try {
			if (element != null) {
				WebDriverWait wait = new WebDriverWait(driver, timeout);
				WebElement ele = null;
				if (ExcelUtils.readDataFromPropertyFile("Browser").equalsIgnoreCase("IE Edge")) {
					System.out.println("broswer: IE");

					WebDriverWait waitIE = new WebDriverWait(driver, timeout / 4);
					try {
						waitIE.until(ExpectedConditions.visibilityOf(element));
						return element;
					} catch (TimeoutException | NoSuchElementException e) {
						if (wait.until(new Function<WebDriver, Boolean>() {

							@Override
							public Boolean apply(WebDriver t) {
								// TODO Auto-generated method stub
								return scrollDownThroughWebelement(driver, element, "");
							}
						})) {
							wait.until(ExpectedConditions.visibilityOf(element));
							return element;
						} else {
							return null;
						}
					}
				} else if (waitOn.equalsIgnoreCase("Visibility")) {
					try {
					ele = wait.until(ExpectedConditions.visibilityOf(element));
					}
					catch(Exception e)
					{
						ele = null;
					}
				} else if (waitOn.equalsIgnoreCase("Clickable")) {
					ele = wait.until(ExpectedConditions.elementToBeClickable(element));
				} else {
					ele = wait.until(ExpectedConditions.elementToBeClickable(element));
				}
				if (ele != null) {
					return ele;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			Exception e1 = e;
			// e.printStackTrace();
			BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
			if (bp.getScheduledMaintenancePopUp(5) != null) {
				bp.getScheduledMaintenanceContinueLink(20).click();
				if (isDisplayed(driver, element, waitOn, timeout, elementName) != null)
					return element;
			}
			if (e instanceof StaleElementReferenceException) {
				String xpath = getXpath(element);
				appLog.info(xpath);
				if (xpath != null) {
					WebElement ele = FindElement(driver, xpath, elementName, action.BOOLEAN, 10);
					if (ele != null) {
						AppListeners.appLog.info("\n\n\n*****Successfully recovered from the stale state.*****\n\n\n");
						return ele;
					} else {
						AppListeners.appLog.info(
								"\n\n\n\n*****Not able to recover from stale element reference error.******\n\n\n\n");
					}
				}
			}
			// String[] causes=e.getCause().getMessage().split("(");
			// String[] causes=e.getCause().getMessage().split("(");
			errorMessage = "Element not found: " + elementName + "\nReason: " + e.getMessage() + "\nCause: ";
			appLog.info("Element not found: " + elementName + "\nReason: " + e.getMessage() + "\nCause: "
			/* + e1.getCause().getMessage() */);
			failedMethod(e);
			// e.printStackTrace();
			// System.out.println("Kindly show this exception to ankur.");
			return null;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param element
	 * @param waitOn
	 * @param timeout
	 * @param elementName
	 * @return WebElement
	 * @description based on the waiton(Visibility,clickable) condition waits for
	 *              the element
	 */
	public static WebElement isDisplayed(WebDriver driver, WebElement element, String waitOn, int timeout,
			String elementName, action action) {
		try {
			scrollDownThroughWebelement(driver, element, elementName);
			if (element != null) {
				WebDriverWait wait = new WebDriverWait(driver, timeout);
				WebElement ele = null;
				if (waitOn.equalsIgnoreCase("Visibility")) {
					ele = wait.until(ExpectedConditions.visibilityOf(element));
				} else if (waitOn.equalsIgnoreCase("Clickable")) {
					ele = wait.until(ExpectedConditions.elementToBeClickable(element));
				} else {
					ele = wait.until(ExpectedConditions.elementToBeClickable(element));
				}
				if (ele != null) {
					scrollDownThroughWebelement(driver, element, elementName);
					return element;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			// AppListeners.appLog.info("Element not found: " + elementName + "\nReason: " +
			// e.getMessage() + "\nCause: "
			// + e.getCause().getMessage());
			failedMethod(e);
			// e.printStackTrace();
			// System.out.println("Kindly show this exception to ankur.");
			return null;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param element
	 * @param elementName
	 * @return boolean
	 * @description check if the element is enabled or not
	 */
	public static boolean isEnabled(WebDriver driver, WebElement element, String elementName) {
		try {
			if (element != null) {
				return element.isEnabled();
			} else {
				AppListeners.appLog.info(elementName + " is not visible on this page.");
				return false;
			}
		} catch (Exception e) {
			AppListeners.appLog.info(elementName + " is not in enabled state.");
			failedMethod(e);
			return false;
		}
	}

	/**
	 * 
	 * @author Parul Singh
	 * @description- This method is used to verify whether the button or checkbox is
	 *               selected or not.
	 */
	public static boolean isSelected(WebDriver driver, WebElement element, String elementName) {
		if (element != null) {
			return element.isSelected();
		} else {
			appLog.info(elementName + " is not visible on this page.");
			return false;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param widgetScrollingElement
	 * @param elementToSearch
	 * @return boolean
	 * @description scroll the active widget
	 */
	public static boolean scrollActiveWidget(WebDriver driver, WebElement widgetScrollingElement, By elementToSearch) {
		// ThreadSleep(5000);
		int widgetTotalScrollingHeight = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
				.executeScript("return arguments[0].scrollHeight", widgetScrollingElement)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0,0)", widgetScrollingElement);
		int j = 0;
		for (int i = 0; i <= widgetTotalScrollingHeight / 25; i++) {
			if (!driver.findElements(elementToSearch).isEmpty()) {
				// ThreadSleep(1000);
				CommonLib.scrollDownThroughWebelement(driver, driver.findElement(elementToSearch),
						elementToSearch.toString());
				ThreadSleep(500);
				break;
			} else {
				System.out.println("Not FOund: " + elementToSearch.toString());
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 25) + ")",
						widgetScrollingElement);
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
	 * @author Ankur Rana
	 * @param driver
	 * @param element
	 * @param elementName
	 * @param action
	 * @return boolean
	 * @description click on the element and based on the action return the boolean
	 *              or throw exception
	 */
	public static boolean click(WebDriver driver, WebElement element, String elementName, action action) {
		try {
			if (element != null) {
				if (action == CommonLib.action.SCROLLANDTHROWEXCEPTION || action == CommonLib.action.SCROLLANDBOOLEAN)
					scrollDownThroughWebelement(driver, element, elementName);
				// ThreadSleep(3000);
				element.click();
				if (elementName != null && !elementName.isEmpty())
					AppListeners.appLog.info("Clicked on element: " + elementName);
				return true;
			} else {
				if (elementName != null && !elementName.isEmpty())
					AppListeners.appLog.info(elementName + " is not present on this page.");
				if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
					throw new AppException(elementName + " is not present on this page.");
				return false;
			}
		} catch (Exception e) {
			if (elementName != null && !elementName.isEmpty())
				if (e instanceof StaleElementReferenceException) {
					String xpath = getXpath(element);
					if (xpath != null) {
						WebElement ele = FindElement(driver, xpath, elementName, action.BOOLEAN, 10);
						if (ele != null) {
							try {
								ele.click();
								AppListeners.appLog.info("Clicked on element: " + elementName);
								return true;
							} catch (Exception e1) {

							}
						}
					}
				} else if (e.getMessage().contains("is not clickable at point")
						|| e.getMessage().contains("Other element would receive the click")
						|| e.getMessage().contains("element not interactable")) {
					String xpath = getXpath(element);
					if (xpath != null) {
						WebElement ele = FindElement(driver, xpath, elementName, action, 10);
						if (ele != null) {
							try {
								((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
								AppListeners.appLog
										.info("************Recovered from element overlay problem*************");
								return true;
							} catch (Exception e1) {
								// System.out.println("Most Inner Catch");
							}
						} else {
							AppListeners.appLog.info("*******Cannot recover from overlay problem**********");
						}
					}
				}
			if (elementName != null && !elementName.isEmpty()) {
				AppListeners.appLog.info("Not able to click on: " + elementName + "\nReason: " + e.getMessage());
				failedMethod(e);
			}
			errorMessage = "Not able to click on: " + elementName + "\nReason: " + e.getMessage();
			if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
				throw new AppException(elementName + " is not clickable." + "\nReason: " + e.getMessage());
			return false;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param element
	 * @param value
	 * @param elementName
	 * @param action
	 * @return boolean
	 * @description Pass value to the text box and based on the action return the
	 *              boolean or throw exception
	 */
	public static boolean sendKeys(WebDriver driver, WebElement element, String value, String elementName,
			action action) {
		try {
			if (element != null) {
				if (action == CommonLib.action.SCROLLANDTHROWEXCEPTION || action == CommonLib.action.SCROLLANDBOOLEAN)
					scrollDownThroughWebelement(driver, element, elementName);
				try {
					element.clear();
					appLog.info("Successfully cleared the text box.");
				} catch (Exception e) {
					appLog.error("Not able to clear the text box.");
				}
				element.sendKeys(value);
				AppListeners.appLog.info("Passed value to element: " + elementName + "\nPassed Value: " + value);
				return true;
			} else {
				AppListeners.appLog.info(elementName + " Text box is not present on this page.");
				if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
					throw new AppException(elementName + " Text box is not present on this page.");
				return false;
			}
		} catch (Exception e) {
			// AppListeners.appLog.info("Cannot enter text in " + elementName + "\nReason: "
			// + e.getMessage());
			errorMessage = "Cannot enter text in " + elementName + "\nReason: " + e.getMessage();
			failedMethod(e);
			if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
				throw new AppException("Cannot enter text in ." + elementName + "\nReason: " + e.getMessage());
			return false;
		}
	}

	public static boolean sendKeysUsingActionClassWithoutClear(WebDriver driver, WebElement element, String value,
			String elementName, action action) {
		Actions act = new Actions(driver);
		try {
			if (element != null) {
				if (action == CommonLib.action.SCROLLANDTHROWEXCEPTION || action == CommonLib.action.SCROLLANDBOOLEAN)
					scrollDownThroughWebelement(driver, element, elementName);

				act.moveToElement(element).sendKeys(value).build().perform();
				;
				AppListeners.appLog.info("Passed value to element: " + elementName + "\nPassed Value: " + value);
				return true;
			} else {
				AppListeners.appLog.info(elementName + " Text box is not present on this page.");
				if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
					throw new AppException(elementName + " Text box is not present on this page.");
				return false;
			}
		} catch (Exception e) {
			// AppListeners.appLog.info("Cannot enter text in " + elementName + "\nReason: "
			// + e.getMessage());
			errorMessage = "Cannot enter text in " + elementName + "\nReason: " + e.getMessage();
			failedMethod(e);
			if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
				throw new AppException("Cannot enter text in ." + elementName + "\nReason: " + e.getMessage());
			return false;
		}
	}

	public static boolean sendKeysUsingJavaScript(WebDriver driver, WebElement element, String value,
			String elementName, action action) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			if (element != null) {
				if (action == CommonLib.action.SCROLLANDTHROWEXCEPTION || action == CommonLib.action.SCROLLANDBOOLEAN)
					scrollDownThroughWebelement(driver, element, elementName);

				js.executeScript("arguments[0].value='" + value + "';", element);
				AppListeners.appLog.info("Passed value to element: " + elementName + "\nPassed Value: " + value);
				return true;
			} else {
				AppListeners.appLog.info(elementName + " Text box is not present on this page.");
				if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
					throw new AppException(elementName + " Text box is not present on this page.");
				return false;
			}
		} catch (Exception e) {
			// AppListeners.appLog.info("Cannot enter text in " + elementName + "\nReason: "
			// + e.getMessage());
			errorMessage = "Cannot enter text in " + elementName + "\nReason: " + e.getMessage();
			failedMethod(e);
			if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
				throw new AppException("Cannot enter text in ." + elementName + "\nReason: " + e.getMessage());
			return false;
		}
	}

	public static boolean sendKeysAndPressEnter(WebDriver driver, WebElement element, String value, String elementName,
			action action) {
		try {
			if (element != null) {
				if (action == CommonLib.action.SCROLLANDTHROWEXCEPTION || action == CommonLib.action.SCROLLANDBOOLEAN)
					scrollDownThroughWebelement(driver, element, elementName);
				try {
					element.clear();
					appLog.info("Successfully cleared the text box.");
				} catch (Exception e) {
					appLog.error("Not able to clear the text box.");
				}
				element.sendKeys(value + "\n");
				AppListeners.appLog.info("Passed value to element: " + elementName + "\nPassed Value: " + value);
				return true;
			} else {
				AppListeners.appLog.info(elementName + " Text box is not present on this page.");
				if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
					throw new AppException(elementName + " Text box is not present on this page.");
				return false;
			}
		} catch (Exception e) {
			// AppListeners.appLog.info("Cannot enter text in " + elementName + "\nReason: "
			// + e.getMessage());
			errorMessage = "Cannot enter text in " + elementName + "\nReason: " + e.getMessage();
			failedMethod(e);
			if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
				throw new AppException("Cannot enter text in ." + elementName + "\nReason: " + e.getMessage());
			return false;
		}
	}

	public static boolean sendKeysWithoutClearingTextBox(WebDriver driver, WebElement element, String value,
			String elementName, action action) {
		try {
			if (element != null) {
				if (action == CommonLib.action.SCROLLANDTHROWEXCEPTION || action == CommonLib.action.SCROLLANDBOOLEAN)
					scrollDownThroughWebelement(driver, element, elementName);
				try {
					// element.clear();
					appLog.info("Successfully cleared the text box.");
				} catch (Exception e) {
					appLog.error("Not able to clear the text box.");
				}
				element.sendKeys(value);
				AppListeners.appLog.info("Passed value to element: " + elementName + "\nPassed Value: " + value);
				return true;
			} else {
				AppListeners.appLog.info(elementName + " Text box is not present on this page.");
				if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
					throw new AppException(elementName + " Text box is not present on this page.");
				return false;
			}
		} catch (Exception e) {
			// AppListeners.appLog.info("Cannot enter text in " + elementName + "\nReason: "
			// + e.getMessage());
			errorMessage = "Cannot enter text in " + elementName + "\nReason: " + e.getMessage();
			failedMethod(e);
			if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
				throw new AppException("Cannot enter text in ." + elementName + "\nReason: " + e.getMessage());
			return false;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param e
	 * @description Gets the exact line number and method failed.
	 */
	public static String failedMethod(Throwable e) {
		String failedMethodAndLineNumber = null;
		String allClassNames = ExcelUtils.readDataFromPropertyFile(
				System.getProperty("user.dir") + "//ConfigFiles//classes.properties", "Classes");
		String[] className = allClassNames.split(",");
		int flag = 0;
		for (int i = 0; i < e.getStackTrace().length; i++) {
			for (int j = 0; j < className.length; j++) {
				if (e.getStackTrace()[i].getFileName() == null) {
					continue;
				}
				if (e.getStackTrace()[i].getFileName().contains(className[j].trim())) {
					appLog.info("Method Failed: " + e.getStackTrace()[i].getMethodName());
					appLog.info("Line Number: " + "(" + e.getStackTrace()[i].getFileName() + ":"
							+ e.getStackTrace()[i].getLineNumber() + ")");
					failedMethodAndLineNumber = "(" + e.getStackTrace()[i].getFileName() + ":"
							+ e.getStackTrace()[i].getLineNumber() + ")";
					appLog.info("Calling Method: " + e.getStackTrace()[i + 1].getMethodName());
					appLog.info("Calling Line Number: " + "(" + e.getStackTrace()[i + 1].getFileName() + ":"
							+ e.getStackTrace()[i + 1].getLineNumber() + ")");
					flag++;
					if (flag > 0) {
						break;
					}
				}
			}
			if (flag > 0) {
				break;
			}
		}
		return failedMethodAndLineNumber;
	}

	/**
	 * @author Ankur Rana
	 * @param message
	 * @description Exit the execution.
	 */
	public static void exit(String message) {
		fail(message);
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @return boolean
	 * @description checks whether alert is present or not
	 */
	public static boolean isAlertPresent(WebDriver driver) {
		try {
			Alert alert = driver.switchTo().alert();
			// AppListeners.appLog.info("Message in alert: " + alert.getText());
			return true;
		} catch (NoAlertPresentException e) {
			// failedMethod(e);
			// AppListeners.appLog.info("There is no alert.");
			return false;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param timeForWait
	 * @param action
	 * @return boolean
	 * @description Switches to alert and based on the action Accepts or Declines
	 *              the alert
	 */
	public static boolean switchToAlertAndAcceptOrDecline(WebDriver driver, int timeForWait, action action) {
		int a = timeForWait * 2;
		while (true) {
			if (isAlertPresent(driver)) {
				if (action == CommonLib.action.ACCEPT) {
					ThreadSleep(500);
					driver.switchTo().alert().accept();
					AppListeners.appLog.info("Alert Successfully accepted.");
					return true;
				} else if (action == CommonLib.action.DECLINE) {
					driver.switchTo().alert().dismiss();
					AppListeners.appLog.info("Alert Successfully Declined.");
					return true;
				} else {
					AppListeners.appLog.info("Kindly check the key passed.");
					return false;
				}
			} else {
				try {
					Thread.sleep(500);
					a--;
					if (a <= 0) {
						AppListeners.appLog.info("Not able to accept the alert in given time.");
						return false;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param timeForWait
	 * @param action
	 * @return String
	 * @description Switches to alert and return the text in the alert
	 */
	public static String switchToAlertAndGetMessage(WebDriver driver, int timeForWait, action action) {
		int a = timeForWait * 2;
		while (true) {
			if (isAlertPresent(driver)) {
				if (action == CommonLib.action.GETTEXT) {
					String alertMsg = driver.switchTo().alert().getText();
					AppListeners.appLog.info("Alert message is successFully received.");
					return alertMsg;
				} else {
					AppListeners.appLog.info("Kindly check the key passed.");
					return null;
				}
			} else {
				try {
					Thread.sleep(500);
					a--;
					if (a <= 0) {
						AppListeners.appLog.info("Not able to accept the alert in given time.");
						return null;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param xpath
	 * @param elementName
	 * @param action
	 * @param timeOut
	 * @return WebElement
	 * @description finds the element based on the xpath without scroll
	 */
	public static WebElement FindElement(WebDriver driver, String xpath, String elementName, action action,
			int timeOut) {
		xpath = replaceApostrophyWithQuotes(xpath);
		appLog.info("String change: " + xpath);
		try {
			int timeout = 0;
			WebElement ele = null;
			while (true) {
				try {
					ele = driver.findElement(By.xpath(xpath));
					break;
				} catch (Exception e) {
					// System.out.println("nhi milra element");
					ThreadSleep(250);
					timeout++;
					if (timeout > timeOut * 4) {
						ele = driver.findElement(By.xpath(xpath));
						break;
					}
				}
			}
			if (elementName != null && !elementName.isEmpty())
				appLog.info("Element successfully found: " + elementName);
			return ele;
		} catch (Exception e) {
			if (elementName != null && !elementName.isEmpty())
				failedMethod(e);
			errorMessage = "Element not found: " + elementName + "\nReason: " + e.getStackTrace()[0].getMethodName()
					+ " " + e.getMessage();
			if (action == CommonLib.action.THROWEXCEPTION)
				throw new AppException("Element not found: " + elementName + "\nReason: "
						+ e.getStackTrace()[0].getMethodName() + " " + e.getMessage());
			if (elementName != null && !elementName.isEmpty())
				appLog.info("Element not found: " + elementName);
			return null;
		}
	}

	public static WebElement FindElementSingleQuotes(WebDriver driver, String xpath, String elementName, action action,
			int timeOut) {
		appLog.info("String change: " + xpath);
		try {
			int timeout = 0;
			WebElement ele = null;
			while (true) {
				try {
					ele = driver.findElement(By.xpath(xpath));
					break;
				} catch (Exception e) {
					ThreadSleep(250);
					timeout++;
					if (timeout > timeOut * 4) {
						ele = driver.findElement(By.xpath(xpath));
						break;
					}
				}
			}
			if (elementName != null && !elementName.isEmpty())
				appLog.info("Element successfully found: " + elementName);
			return ele;
		} catch (Exception e) {
			if (elementName != null && !elementName.isEmpty())
				failedMethod(e);
			errorMessage = "Element not found: " + elementName + "\nReason: " + e.getStackTrace()[0].getMethodName()
					+ " " + e.getMessage();
			if (action == CommonLib.action.THROWEXCEPTION)
				throw new AppException("Element not found: " + elementName + "\nReason: "
						+ e.getStackTrace()[0].getMethodName() + " " + e.getMessage());
			if (elementName != null && !elementName.isEmpty())
				appLog.info("Element not found: " + elementName);
			return null;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param elementXpath
	 * @param elementName
	 * @param action
	 * @param scrollToElement
	 * @param timeOut
	 * @return WebElement
	 * @description finds the element based on the xpath after scrolling till the
	 *              passed element
	 */
	public static WebElement FindElement(WebDriver driver, String elementXpath, String elementName, action action,
			WebElement scrollToElement, int timeOut) {
		try {
			scrollDownThroughWebelement(driver, scrollToElement, "");
			int timeout = 0;
			WebElement ele = null;
			while (true) {
				try {
					ele = driver.findElement(By.xpath(elementXpath));
					break;
				} catch (Exception e) {
					ThreadSleep(250);
					timeout++;
					if (timeout > timeOut * 4) {
						ele = driver.findElement(By.xpath(elementXpath));
						break;
					}
				}
			}
			appLog.info("Element successfully found: " + elementName);
			return ele;
		} catch (Exception e) {
			failedMethod(e);
			errorMessage = "Element not found: " + elementName + "\nReason: " + e.getStackTrace()[0].getMethodName()
					+ " " + e.getMessage();
			if (action == CommonLib.action.THROWEXCEPTION)
				throw new AppException("Element not found: " + elementName + "\nReason: "
						+ e.getStackTrace()[0].getMethodName() + " " + e.getMessage());
			appLog.info("Element not found: " + elementName);
			return null;
		}
	}

	/**
	 * @author Ankur Rana
	 * @param time
	 * @description hard code wait untill the time passed
	 */
	public static void ThreadSleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param xpath
	 * @param waitLimitInSeconds
	 * @description wait untill the element with the passed xpath is visible
	 */
	public static void checkForLoaderImage(WebDriver driver, String xpath, int waitLimitInSeconds) {
		int limit = waitLimitInSeconds * 2;
		while (true) {
			try {
				isAlertPresent(driver);
				if (driver.findElement(By.xpath(xpath)).isDisplayed()) {
					System.out.println("please wait image is appearing");
					ThreadSleep(500);
					limit--;
					if (limit <= 0) {
						exit("Kindly check your internet connection. This Test Case will be failed.");
					}
				} else {
					break;
				}
			} catch (Exception e) {
				break;
			}
		}
	}

	public static void checkForLoaderImage(WebDriver driver, List<WebElement> elements, int waitLimitInSeconds) {
		int limit = waitLimitInSeconds * 2;
		// System.err.println("size: "+elements.size());
		while (true) {
			try {
				for (int i = 0; i < elements.size(); i++) {
					isAlertPresent(driver);
					// System.err.println("iteration: "+i);
					if (elements.get(i).isDisplayed()) {
						System.out.println("please wait image is appearing");
						ThreadSleep(500);
						limit--;
						if (limit <= 0) {
							exit("Kindly check your internet connection. This Test Case will be failed.");
						}
					} else {
						return;
					}
				}
			} catch (Exception e) {
				break;
			}
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @description Switch to the default frame
	 */
	public static void switchToDefaultContent(WebDriver driver) {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {

		}
	}

	/**
	 * @author Ankur Rana
	 * @param sheetName
	 * @param label
	 * @return List<String>
	 * @description Gets the value from the excel based on the label passed
	 */
	public static List<String> getValueBasedOnLabel(String excelPath, String sheetName, excelLabel label,
			int startFrom) {
		int columnNumber = 0;
		List<String> value = new ArrayList<String>();
		System.err.println("path of excel " + excelPath);
		System.err.println(getSystemDate("hh:mm:ss"));

		for (int i = 0; i >= 0; i++) {
			if (ExcelUtils.readData(excelPath, sheetName, startFrom, i).equalsIgnoreCase(label.toString())) {
				columnNumber = i;
				break;
			} else if (ExcelUtils.readData(excelPath, sheetName, startFrom, i) == ""
					|| ExcelUtils.readData(excelPath, sheetName, startFrom, i) == null
					|| ExcelUtils.readData(excelPath, sheetName, startFrom, i).isEmpty()) {
				appLog.info("Invalid Attribute: " + label.toString());
				break;
			}
		}
		for (int j = startFrom + 1; j >= 0; j++) {
			if (ExcelUtils.readData(excelPath, sheetName, j, columnNumber) != null
					&& ExcelUtils.readData(excelPath, sheetName, j, columnNumber) != "") {
				System.err.println(j);
				value.add(ExcelUtils.readData(excelPath, sheetName, j, columnNumber));
			} else if (ExcelUtils.readData(excelPath, sheetName, j, columnNumber) == null) {
				System.err.println("Inside else: " + getSystemDate("hh:mm:ss") + "\tValue of j: " + j);
				break;
			}
		}
		System.err.println(getSystemDate("hh:mm:ss"));
		return value;
	}

	/**
	 * @author Ankur Rana
	 * @description scroll the webpage using PageDown Key
	 */
	public static void scrollWithRobotClass() {
		try {
			Robot robot = new Robot();
			for (int i = 0; i < 5; i++) {
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
				robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param timeOut
	 * @return String
	 * @description Gets the current URL of the webpage
	 */
	public static String getURL(WebDriver driver, int timeOut) {
		ThreadSleep(5000);
		String url;
		int time = 0;
		while (true) {
			url = driver.getCurrentUrl();
			System.out.println("iteration number: " + time + "\n\nURL: " + url);
			time++;
			if (url.isEmpty()) {
				ThreadSleep(250);
				if (time == timeOut * 4) {
					break;
				}
			} else if (url.equalsIgnoreCase("about:blank")) {
				ThreadSleep(250);
				if (time == timeOut * 4) {
					break;
				}
			} else {
				break;
			}
		}
		return url;
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param xpath
	 * @param elementName
	 * @return list<webelement>
	 * @description returns the list of webelement based on the xpath passed
	 */
	public static List<WebElement> FindElements(WebDriver driver, String xpath, String elementName) {
		// xpath=replaceApostrophyWithQuotes(xpath);
		for (int i = 0; i < 41; i++) {
			if (driver.findElements(By.xpath(xpath)).isEmpty()) {
				ThreadSleep(250);
				CommonLib.waitForPageLoad(driver);
				// CommonLib.checkForLoaderImage(driver,
				// "//img[@src='/resource/1499340792000/DR_CRMFinal/DR_CRMFinal/images/processing-image.gif']",
				// 120);
				// CommonLib.checkForLoaderImage(driver, "//div[@id='blurred']", 120);
				// CommonLib.checkForLoaderImage(driver,
				// "//img[contains(@src,'processing-image.gif')]", 120);
				// CommonLib.checkForLoaderImage(driver, "//div[@id='blurred_procss_imaz']",
				// 120);
				// CommonLib.checkForLoaderImage(driver, "//div[@class='waitingSearchDiv']",
				// 120);
				// CommonLib.checkForLoaderImage(driver,
				// "//img[contains(@src,'images/processing-image.gif')]", 120);
			} else {
				break;
			}
		}
		return driver.findElements(By.xpath(xpath));
	}

	/**
	 * @author Ankur Rana
	 * @description overriden method of the comparator interface, compares the
	 *              values.
	 */
	@Override
	public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		try {
			Float t = Float.valueOf(o1);
			Float y = Float.valueOf(o2);
			return t.compareTo(y);
		} catch (Exception e) {
			System.out.println("We are not comparing int values.");
		}
		return o1.compareToIgnoreCase(o2);
	}

	/**
	 * @author Ankur Rana
	 * @param string
	 * @description copy the data to the clipboard
	 */
	public static void setClipboardData(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	/**
	 * @author Ankur Rana
	 * @description extracts the xpath from the webelement
	 */
	public static String getXpath(WebElement ele) {
		try {
			String[] str = ele.toString().split("->");
			String[] s = str[1].split(": ");
			System.out.println(s);
			return s[1].substring(0, s[1].length() - 1).trim();
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 
	 * @author Parul Singh
	 * @description- This method is used to get date according to the time zone.
	 */
	public static String getDateAccToTimeZone(String timeZone, String format) {
		try {
			DateFormat formatter = new SimpleDateFormat(format);
			formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
			return (formatter.format(new Date()));

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @author- Ankit Jaiswal
	 * @param value - pass String value
	 * @description - This method is used to remove numbers from String value.
	 * @return - it will returns string array list after split numbers from passed
	 *         String
	 */
	public static String[] removeNumbersFromString(String value) {
		return value.split("(?<=\\D)(?=\\d)");

	}

	public static String replaceApostrophyWithQuotes(String xpath) {
		int len = xpath.length();
		StringBuilder str = new StringBuilder(xpath);
		for (int i = 0; i < len; i++) {
			char character = xpath.charAt(i);
			if (String.valueOf(character).equalsIgnoreCase("'")) {
				if (xpath.charAt(i - 1) == '=' || xpath.charAt(i - 1) == ',' || xpath.charAt(i + 1) == ')'
						|| (xpath.charAt(i + 1) == ' ' && xpath.charAt(i + 2) == ' ') || xpath.charAt(i + 1) == ']'
						|| (xpath.charAt(i + 1) == ' ' && xpath.charAt(i + 2) == 'o')) {
					str.setCharAt(i, '\"');
				}
			}
		}
		// appLog.info("Original Xpath: "+xpath+"\tString builder: "+str);
		System.out.println("Original Xpath: " + xpath + "\tString builder: " + str);
		return String.valueOf(str);
	}

	public static String getText(WebDriver driver, WebElement element, String elementName, action action) {
		try {
			if (element != null) {
				if (action == CommonLib.action.SCROLLANDTHROWEXCEPTION || action == CommonLib.action.SCROLLANDBOOLEAN)
					scrollDownThroughWebelement(driver, element, elementName);
				String ele = element.getText().trim();
				AppListeners.appLog.info("getText value to element: " + elementName + "\nPassed is: " + ele);
				return ele;
			} else {
				AppListeners.appLog.info(element + " Text is not present on this page.");
				if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
					throw new AppException(elementName + " Text is not present on this page.");
			}
		} catch (Exception e) {
			errorMessage = "Cannot getText in " + elementName + "\nReason: " + e.getMessage();
			failedMethod(e);
			if (action == CommonLib.action.THROWEXCEPTION || action == CommonLib.action.SCROLLANDTHROWEXCEPTION)
				throw new AppException("Cannot gettext in ." + elementName + "\nReason: " + e.getMessage());
		}
		return "";
	}

	public static String getAttribute(WebDriver driver, WebElement element, String elementName, String attributeName) {

		try {
			if (element != null) {
				scrollDownThroughWebelement(driver, element, elementName);
				String ele = element.getAttribute(attributeName);
				AppListeners.appLog.info("getAttribute value to element: " + elementName + "\nPassed is: " + ele);
				return ele;
			} else {
				AppListeners.appLog.info(element + "  is not present on this page.");
			}
		} catch (Exception e) {
			errorMessage = "Cannot getAttribute in " + elementName + "\nReason: " + e.getMessage();
			failedMethod(e);
		}
		return null;
	}

	public static String trim(String text) {
		try {
			String ele = text.trim();
			return ele;
		} catch (Exception e) {
			appLog.info("Text is null or empty");
			failedMethod(e);
		}
		return null;
	}

	public static void refresh(WebDriver driver) {
		ThreadSleep(1000);
		driver.navigate().refresh();
	}

	/**
	 * @author Ankit Jaiswal
	 * @param filesName
	 * @param listOfFileName
	 * @return empty list of String if all data is matched other wise return list of
	 *         false data list
	 */
	public static List<String> compareMultipleList(WebDriver driver, String filesName,
			List<WebElement> listOfFileName) {
		List<String> result = new ArrayList<String>();
		String[] fileName = filesName.split(",");
		List<WebElement> listofFileName = listOfFileName;
		int countFiles = 0;
		try {
			if (fileName.length != 0) {
				if (!listofFileName.isEmpty()) {
					for (int i = 0; i < fileName.length; i++) {
						for (int j = 0; j < listofFileName.size(); j++) {
							scrollDownThroughWebelement(driver, listofFileName.get(j), "");
							ThreadSleep(500);
							AppListeners.appLog.info("Comparing:>>" + fileName[i].trim() + ">>With:>>"
									+ listofFileName.get(j).getText().trim());
							if (fileName[i].trim().equalsIgnoreCase(listofFileName.get(j).getText().trim())) {
								AppListeners.appLog.info(fileName[i].trim() + " is matched successfully");
								countFiles++;
								break;
							} else if (j == listofFileName.size() - 1) {
								AppListeners.appLog.info(fileName[i].trim() + " is not matched.");
								result.add(fileName[i].trim() + " is not matched.");
							}
						}
					}
				} else {
					AppListeners.appLog.error("list of webelement is empty so cannot compare name: " + filesName);
					result.add("list of webelement is empty so cannot compare name: " + filesName);
				}
				if (fileName.length == countFiles) {
					AppListeners.appLog.info("All the files are matched.");

				} else {
					AppListeners.appLog.info("Files are not matched.");
					result.add("Files are not matched.");
				}
			} else {
				AppListeners.appLog.info("No Data In Excel Cell.");
				result.add("No Data In Excel Cell.");
			}
		} catch (Exception e) {
			AppListeners.appLog.info("There are no file to compare.");
			result.add("There are no file to compare.");
		}
		return result;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param filesName
	 * @param listOfFileName
	 * @return empty list of String if all data is matched other wise return list of
	 *         false data list
	 */
	public static List<String> compareMultipleListSepratedByBreak(WebDriver driver, String filesName,
			List<WebElement> listOfFileName) {
		List<String> result = new ArrayList<String>();
		String[] fileName = filesName.split("<break>");
		List<WebElement> listofFileName = listOfFileName;
		int countFiles = 0;
		try {
			if (fileName.length != 0) {
				if (!listofFileName.isEmpty()) {
					for (int i = 0; i < fileName.length; i++) {
						for (int j = 0; j < listofFileName.size(); j++) {
							scrollDownThroughWebelement(driver, listofFileName.get(j), "");
							ThreadSleep(500);
							AppListeners.appLog.info("Comparing:>>" + fileName[i].trim() + ">>With:>>"
									+ listofFileName.get(j).getText().trim());
							if (fileName[i].trim().equalsIgnoreCase(listofFileName.get(j).getText().trim())) {
								AppListeners.appLog.info(fileName[i].trim() + " is matched successfully");
								countFiles++;
								break;
							} else if (j == listofFileName.size() - 1) {
								AppListeners.appLog.info(fileName[i].trim() + " is not matched.");
								result.add(fileName[i].trim() + " is not matched.");
							}
						}
					}
				} else {
					AppListeners.appLog.error("list of webelement is empty so cannot compare name: " + filesName);
					result.add("list of webelement is empty so cannot compare name: " + filesName);
				}
				if (fileName.length == countFiles) {
					AppListeners.appLog.info("All the files are matched.");

				} else {
					AppListeners.appLog.info("Files are not matched.");
					result.add("Files are not matched.");
				}
			} else {
				AppListeners.appLog.info("No Data In Excel Cell.");
				result.add("No Data In Excel Cell.");
			}
		} catch (Exception e) {
			AppListeners.appLog.info("There are no file to compare.");
			result.add("There are no file to compare.");
		}
		return result;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param filesName
	 * @param listOfFileName
	 * @return empty list of String if all data is matched other wise return list of
	 *         false data list
	 */
	public static List<String> compareMultipleListOnBasisOfTitle(WebDriver driver, String filesName,
			List<WebElement> listOfFileName) {
		List<String> result = new ArrayList<String>();
		String[] fileName = filesName.split(",");
		List<WebElement> listofFileName = listOfFileName;
		int countFiles = 0;
		try {
			if (fileName.length != 0) {
				if (!listofFileName.isEmpty()) {
					for (int i = 0; i < fileName.length; i++) {
						for (int j = 0; j < listofFileName.size(); j++) {
							scrollDownThroughWebelement(driver, listofFileName.get(j), "");
							AppListeners.appLog.info("Comparing:>>" + fileName[i] + ">>With:>>"
									+ listofFileName.get(j).getAttribute("title").trim());
							String actualresult = listofFileName.get(j).getAttribute("title").trim();
							if (fileName[i].equalsIgnoreCase(actualresult)) {
								AppListeners.appLog.info(fileName[i] + " is matched successfully");
								countFiles++;
								break;
							} else if (j == listofFileName.size() - 1) {
								AppListeners.appLog.info(fileName[i] + " is not matched.");
								result.add(fileName[i] + " is not matched.");
							}
						}
					}
				} else {
					AppListeners.appLog.error("list of webelement is empty so cannot compare name: " + filesName);
					result.add("list of webelement is empty so cannot compare name: " + filesName);
				}
				if (fileName.length == countFiles) {
					AppListeners.appLog.info("All the files are matched.");

				} else {
					AppListeners.appLog.info("Files are not matched.");
					result.add("Files are not matched.");
				}
			} else {
				AppListeners.appLog.info("No Data In Excel Cell.");
				result.add("No Data In Excel Cell.");
			}
		} catch (Exception e) {
			AppListeners.appLog.info("There are no file to compare.");
			result.add("There are no file to compare.");
		}
		return result;
	}

	public static String createStringOutOfList(List<String> listOfString) {
		StringBuilder str = new StringBuilder();
		if (!listOfString.isEmpty()) {
			for (int i = 0; i < listOfString.size(); i++) {
				if (i == listOfString.size() - 1) {
					str.append(listOfString.get(i).trim());
				} else {
					str.append(listOfString.get(i).trim() + "<break>");
				}
			}
			return str.toString();
		} else {
			return null;
		}
	}

	public static List<String> createListOutOfString(String string) {
		List<String> list = new ArrayList<String>();
		if (!string.isEmpty()) {

			String[] listofString = string.split("<break>");

			for (int i = 0; i < listofString.length; i++) {
				list.add(listofString[i].toString().trim());
			}
			return list;
		} else {
			return null;
		}
	}

	public List<String> removeStringFromList(List<String> listOfString, String removeStringName) {
		String[] splitedremoveStringName = removeStringName.split("<break>");
		// List<String> lst =new ArrayList<String>();
		if (!listOfString.isEmpty()) {
			for (int i = 0; i < splitedremoveStringName.length; i++) {
				for (int j = 0; j < listOfString.size(); j++) {
					if (splitedremoveStringName[i].equalsIgnoreCase(listOfString.get(j))) {
						listOfString.remove(j);
					} else {
						if (j == listOfString.size() - 1) {
						}
					}
				}
			}
		} else {
			return null;
		}
		return listOfString;
	}

	public static String previousOrForwardDate(int howManyDaysBeforeOrAfter, String dateFormat) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, howManyDaysBeforeOrAfter);
		// return new SimpleDateFormat(dateFormat).format(cal.getTime());
		return previousOrForwardDateAccordingToTimeZone(howManyDaysBeforeOrAfter, dateFormat,
				BasePageErrorMessage.AmericaLosAngelesTimeZone);
	}

	// public static String unzipFolder(String src , String dest){
	// try {
	// ZipFile zipfile = new ZipFile(new File(src));
	// zipfile.extractAll(dest);
	// } catch (ZipException e) {
	// e.printStackTrace();
	// appLog.error("Kindly Check source and destination folder path.\nSource:
	// "+src+"\nDestination: "+dest);
	//// System.out.println("Kindly Check source and destination folder
	// path.\nSource: "+src+"\nDestination: "+dest);
	// return null;
	// }
	// return dest;
	// }
	//
	public static List<String> listFilesForFolder(File folder) {
		List<String> filesAndFolderPath = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				filesAndFolderPath.add(fileEntry.getName());
				// filesAndFolderPath.add(fileEntry.getAbsolutePath());
			}
		}
		return filesAndFolderPath;
	}

	public static List<Integer> getIntegerFromString(String value) {
		List<Integer> integerValue = new ArrayList<Integer>();
		String[] Value = value.split("[^0-9]");
		for (int i = 0; i < Value.length; i++) {
			if (!Value[i].isEmpty() && Value[i] != "") {
				integerValue.add(Integer.parseInt(Value[i]));
			}
		}
		return integerValue;

	}

	public static HashSet<String> scrollActiveWidgetforSetofFiles(WebDriver driver, WebElement widgetScrollingElement,
			By elementToSearch) {
		HashSet<String> abc = new HashSet<String>();

		int widgetTotalScrollingHeight = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
				.executeScript("return arguments[0].scrollHeight", widgetScrollingElement)));
		Actions act = new Actions(driver);
		int j = 0;
		for (int i = 0; i <= widgetTotalScrollingHeight / 10; i++) {
			ThreadSleep(2000);
			List<WebElement> files = driver.findElements(elementToSearch);
			;
			// System.out.println("iteration :" + i);
			if (!files.isEmpty()) {
				// System.out.println("Files size : " + files.size());
				for (int k = 0; k < files.size(); k++) {
					// System.out.println("Names " + files.get(k).getText());
					abc.add(files.get(k).getText());

				}
			}
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 5) + ")",
					widgetScrollingElement);

		}
		abc.remove("");
		((JavascriptExecutor) driver).executeScript("return arguments[0].scrollHeight", widgetScrollingElement);
		return abc;
	}

	public boolean recoverFromClickFaliures(WebDriver driver, WebElement element, Exception e, String elementName,
			int timeOut) {
		for (int i = 0; i <= clickRetryCount; i++)
			if (e instanceof StaleElementReferenceException) {
				String xpath = getXpath(element);
				System.out.println(xpath);
				if (xpath != null) {
					WebElement ele = FindElement(driver, xpath, elementName, action.BOOLEAN, 10);
					if (ele != null) {
						try {
							ele.click();
							if (elementName != null && !elementName.isEmpty())
								AppListeners.appLog.info("Clicked on element: " + elementName);
							return true;
						} catch (Exception e1) {

						}
					} else {
						System.out.println("**********Not able to recover from stale element**********");
					}
				}
			} else if (e.getMessage().contains("is not clickable at point")
					|| e.getMessage().contains("Other element would receive the click")) {
				String xpath = getXpath(element);
				if (xpath != null) {
					WebElement ele = FindElement(driver, xpath, elementName, action.BOOLEAN, 10);
					if (ele != null) {
						try {
							((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
							System.out.println("************Recovered from element overlay problem*************");
							return true;
						} catch (Exception e1) {
							System.out.println("*******Cannot recover from overlay problem**********");
						}
					} else {
						System.out.println("*******Cannot recover from overlay problem**********");
					}
				}
			}
		return false;
	}

	public static String getTitle(WebDriver driver) {
		return driver.getTitle();
	}

	/**
	 * @author Ankur Rana
	 * @return clipboard data as String
	 */
	public static String getClipBoardData() {
		String data = null;
		try {
			data = Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return data;
	}

	public static boolean checkSorting(WebDriver driver, SortOrder sortOrder, List<WebElement> elements) {

//		if date comparator if either of a or b is date

		List<String> ts = new ArrayList<String>();
		List<String> actual = new ArrayList<String>();
		CommonLib compare = new CommonLib();
		List<WebElement> ele = elements;
		boolean flag = true;
		int j = 0;
		for (int i = 0; i < ele.size(); i++) {
			scrollDownThroughWebelement(driver, ele.get(i), "");
			ts.add(ele.get(i).getText());
		}
		actual.addAll(ts);
		Collections.sort(ts, compare);
		Iterator<String> i = ts.iterator();
		if (sortOrder.toString().equalsIgnoreCase("Decending")) {
			j = ele.size() - 1;
		}
		while (i.hasNext()) {
			String a = i.next();
			if (a.equalsIgnoreCase(actual.get(j))) {
				appLog.info("Order of column is matched " + "Expected: " + a + "\tActual: " + actual.get(j));
			} else {
				appLog.info("Order of column din't match. " + "Expected: " + a + "\tActual: " + actual.get(j));
				BaseLib.sa.assertTrue(false, "Contact name coloumn is not sorted in " + sortOrder.toString() + " order"
						+ "Expected: " + a + "\tActual: " + actual.get(j));
				flag = false;
			}
			if (sortOrder.toString().equalsIgnoreCase("Decending")) {
				j--;
			} else {
				j++;
			}
		}
		return flag;
	}

	public static boolean clearTextBox(WebElement element) {
		try {
			element.clear();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static List<String> scrollActiveWidgetforListofFiles(WebDriver driver, WebElement widgetScrollingElement,
			By elementToSearch) {
		List<String> abc = new ArrayList<String>();
		List<String> parentTagId = new ArrayList<String>();

		int widgetTotalScrollingHeight = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
				.executeScript("return arguments[0].scrollHeight", widgetScrollingElement)));
		Actions act = new Actions(driver);
		int j = 0;
		for (int i = 0; i <= widgetTotalScrollingHeight / 15; i++) {
			ThreadSleep(2000);
			for (int k = 0; k >= 0; k++) {
				List<WebElement> eles = driver.findElements(elementToSearch);
				String xpath = null;
				try {
					xpath = "(" + getXpath(eles.get(k)) + ")[" + (k + 1) + "]";

				} catch (Exception e) {
					break;
				}
				WebElement ele = isDisplayed(driver, FindElement(driver, xpath, "", action.BOOLEAN, 0), "Visibility", 1,
						"File Name in grid");
				scrollDownThroughWebelement(driver, ele, "");
				if (ele != null) {
					if (parentTagId.isEmpty()) {
						String id = FindElement(driver, xpath + "/..", "Parent Tag ID", action.BOOLEAN, 0)
								.getAttribute("id");
						parentTagId.add(id);
						abc.add(ele.getText());
						continue;
					} else {
						String id = FindElement(driver, xpath + "/..", "Parent Tag ID", action.BOOLEAN, 0)
								.getAttribute("id");
						for (int m = 0; m < parentTagId.size(); m++) {
							if (id.equalsIgnoreCase(parentTagId.get(m))) {
								System.err.println("ID Found");
								break;
							} else {
								if (m == parentTagId.size() - 1) {
									parentTagId.add(id);
									abc.add(ele.getText());
									System.err.println("File Name: " + abc.get(abc.size() - 1));
								} else {
									continue;
								}
							}
						}
					}
				} else {
					break;
				}
			}
			// List<WebElement> files = driver.findElements(elementToSearch);;
			// // System.out.println("iteration :" + i);
			// if (!files.isEmpty()) {
			// // System.out.println("Files size : " + files.size());
			// for (int k = 0; k < files.size(); k++) {
			// // System.out.println("Names " + files.get(k).getText());
			// abc.add(files.get(k).getText());
			//
			// }
			// }
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 10) + ")",
					widgetScrollingElement);

		}
		// abc.remove("");
		((JavascriptExecutor) driver).executeScript("return arguments[0].scrollHeight", widgetScrollingElement);
		return abc;
	}

	public static Integer[] scrollActiveWidget(WebDriver driver, WebElement widgetScrollingElement, int pixelsToScroll,
			int iterationNumber) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollTo(" + iterationNumber + "," + (iterationNumber + pixelsToScroll) + ")",
				widgetScrollingElement);
		Integer[] widgetTotalScrollingHeightAndValueOfiterationNumber = {
				Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
						.executeScript("return arguments[0].scrollHeight", widgetScrollingElement))),
				iterationNumber };

		return widgetTotalScrollingHeightAndValueOfiterationNumber;
	}

	/**
	 * @author Ankur Rana Scans all classes accessible from the context class loader
	 *         which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public static Class[] getClasses(String packageName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources;
		List<Class> classes = null;
		String folderPath = null;
		try {
			resources = classLoader.getResources(path);
			List<File> dirs = new ArrayList<File>();
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(new File(resource.getFile()));
			}
			classes = new ArrayList<Class>();
			for (File directory : dirs) {
				try {
					if (directory.toString().contains("%20")) {
						folderPath = directory.toString().replace("%20", " ");
					} else {
						folderPath = directory.toString();
					}
					classes.addAll(findClasses(new File(folderPath), packageName));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * @author Ankur Rana Recursive method used to find all classes in a given
	 *         directory and subdirs.
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base
	 *                    directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			System.err.println("Directory not found: " + directory);
			AppListeners.appLog.fatal("Directory not found: " + directory);
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(
						Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	public static void log(LogStatus logStatus, String message, YesNo takeScreenshot) {
		if (takeScreenshot.toString().equalsIgnoreCase(YesNo.No.toString())) {
			extentLog.log(logStatus, message, "");
		} else {
			if (takeScreenshot.toString().equalsIgnoreCase(YesNo.YesWinium.toString())) {
				extentLog.log(logStatus, message,
						extentLog.addScreenCapture(CommonLib.screenshot(BaseLib.dDriver, currentlyExecutingTC)));
			} else {
				extentLog.log(logStatus, message,
						extentLog.addScreenCapture(CommonLib.screenshot(currentlyExecutingTC)));
			}
		}
		if (logStatus == LogStatus.PASS || logStatus == LogStatus.INFO) {
			appLog.info(message + " " + logLineNumber(new Throwable()));
		} else {
			appLog.error(message + " " + logLineNumber(new Throwable()));
		}
	}

	public static boolean dragNDropOperation(WebDriver driver, WebElement source, WebElement target) {
		Actions actions = new Actions(driver);
		try {
			actions.dragAndDrop(source, target).build().perform();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean dragNDropField(WebDriver driver, WebElement source, WebElement target) {
		Actions actions = new Actions(driver);
		try {
			actions.clickAndHold(source).moveToElement(target).build().perform();
			ThreadSleep(500);
			actions.click(target).release().build().perform();
			// actions.dragAndDrop(source, target).build().perform();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * @author Ankur Rana
	 * @param e
	 * @description Gets the exact line number of the log
	 */
	public static String logLineNumber(Throwable e) {
		String logLineNumber = "";
		String allClassNames = ExcelUtils.readDataFromPropertyFile(
				System.getProperty("user.dir") + "//ConfigFiles//classes.properties", "Classes");
		String[] className = allClassNames.split(",");
		int flag = 0;
		for (int i = 0; i < e.getStackTrace().length; i++) {
			for (int j = 0; j < className.length; j++) {
				if (e.getStackTrace()[i].getFileName() == null) {
					continue;
				}
				if (e.getStackTrace()[i].getFileName().contains(className[j].trim())) {
					logLineNumber = "(" + e.getStackTrace()[i].getFileName() + ":"
							+ e.getStackTrace()[i].getLineNumber() + ")";
					flag++;
					if (flag > 0) {
						break;
					}
				}
			}
			if (flag > 0) {
				break;
			}
		}
		return logLineNumber;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param driver
	 * @param element
	 * @param elementName
	 * @return Text/null
	 */
	public static boolean clickUsingJavaScript(WebDriver driver, WebElement element, String elementName,
			action action) {
		String text = null;
		try {
			// text=(String) ((JavascriptExecutor) driver).executeScript("return
			// $('"+Jquery+"')[0].value");
			scrollDownThroughWebelement(driver, element, "");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			appLog.info("Able to Clicked using JavaScript");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			appLog.error("Exception in Clicked using JavaScript");
			System.err.println("Cannot Click Element: " + elementName);
		}
		appLog.info("Not Able to Click using JavaScript");
		return false;
	}

	public static WebElement FindElement(WiniumDriver driver, Locator locator, String using) {
		WebElement ele = null;
		try {
			if (locator.toString().equalsIgnoreCase(Locator.Name.toString())) {
				ele = driver.findElementByName(using);
			} else {
				ele = driver.findElementByXPath(using);
			}
		} catch (Exception e) {

		}
		return ele;
	}

	public static WiniumDriver winiumDriverObject() {
		String appPath = System.getProperty("user.dir")
				+ "\\configFiles\\salesforce.com\\Data Loader\\dataloader-44.0.0.exe";
		try {
			DesktopOptions Do = new DesktopOptions();
			Do.setApplicationPath(appPath);
			File exefile = new File(System.getProperty("user.dir") + "\\exefiles\\Winium.Desktop.Driver.exe");
			WiniumDriverService service = new WiniumDriverService.Builder().usingDriverExecutable(exefile)
					.usingPort(9999).withVerbose(true).withSilent(false).buildDesktopService();
			try {
				service.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Inside catch");
				e.printStackTrace();
			}
			return new WiniumDriver(service, Do);
		} catch (Exception e) {
			System.out.println("inside cathc");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param filesName
	 * @param listOfFileName
	 * @return empty list of String if all data is matched other wise return list of
	 *         false data list
	 */
	public static List<String> compareMultipleListWithBreak(WebDriver driver, String filesName,
			List<WebElement> listOfFileName) {
		List<String> result = new ArrayList<String>();
		String[] fileName = filesName.split("<break>");
		List<WebElement> listofFileName = listOfFileName;
		int countFiles = 0;
		try {
			if (fileName.length != 0) {
				if (!listofFileName.isEmpty()) {
					for (int i = 0; i < fileName.length; i++) {
						for (int j = 0; j < listofFileName.size(); j++) {
							scrollDownThroughWebelement(driver, listofFileName.get(j), "");
							AppListeners.appLog.info("Comparing:>>" + fileName[i] + ">>With:>>"
									+ listofFileName.get(j).getText().trim());
							if (fileName[i].equalsIgnoreCase(listofFileName.get(j).getText().trim())) {
								AppListeners.appLog.info(fileName[i] + " is matched successfully");
								countFiles++;
								break;
							} else if (j == listofFileName.size() - 1) {
								AppListeners.appLog.info(fileName[i] + " is not matched.");
								result.add(fileName[i] + " is not matched.");
							}
						}
					}
				} else {
					AppListeners.appLog.error("list of webelement is empty so cannot compare name: " + filesName);
					result.add("list of webelement is empty so cannot compare name: " + filesName);
				}
				if (fileName.length == countFiles) {
					AppListeners.appLog.info("All the files are matched.");

				} else {
					AppListeners.appLog.info("Files are not matched.");
					result.add("Files are not matched.");
				}
			} else {
				AppListeners.appLog.info("No Data In Excel Cell.");
				result.add("No Data In Excel Cell.");
			}
		} catch (Exception e) {
			AppListeners.appLog.info("There are no file to compare.");
			result.add("There are no file to compare.");
		}
		return result;
	}

	public static Date convertStringIntoDate(String date, String formatOfTheDatePassed) {
		try {
			Date dateFormat = new SimpleDateFormat(formatOfTheDatePassed).parse(date);
			return dateFormat;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static List<WebElement> getAllDataUnderHeaderName(WebDriver driver, String relatedListName,
			String headerName, Mode mode) {
		String xpath = "//h3[text()='" + relatedListName
				+ "']/../../../../../following-sibling::div//tr[@class='headerRow']//th";
		if (mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
			xpath = "//span[@title='" + relatedListName + "']/../../../../../following-sibling::div//table/thead/tr/th";
		}
		List<WebElement> ele = FindElements(driver, xpath, "Headers");
		for (int i = 0; i < ele.size(); i++) {
			String header = ele.get(i).getText().trim();
			if (header.equalsIgnoreCase(headerName)) {
				if (mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
					xpath = "//span[@title='" + relatedListName
							+ "']/../../../../../following-sibling::div//table/tbody/tr/*[" + i + "]";
				} else {
					xpath = "//h3[text()='" + relatedListName
							+ "']/../../../../../following-sibling::div//tr[contains(@class,'dataRow')]/*[" + i + "]";
				}
				return FindElements(driver, xpath, "Data under header '" + header + "'");
			}
		}
		return null;
	}

	/**
	 * @author Ankur Rana
	 * @param fileName
	 * @return String
	 * @description Takes the screenshot and returns the path
	 */
	public static String screenshot(WiniumDriver driver, String fileName) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		Date date = new Date();
		TakesScreenshot tss = (TakesScreenshot) BaseLib.edriver;
		String screenshotPath = System.getProperty("user.dir") + "//screenshot//" + fileName + df.format(date) + ".png";
		File src = tss.getScreenshotAs(OutputType.FILE);
		File dest = new File(screenshotPath);

		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return screenshotPath;
	}

	/**
	 * @author Ankur Rana
	 * @param driver
	 * @param yAxis1
	 * @param yAxis2
	 * @return void
	 * @description Scroll the window based on the pixels
	 */
	public static void scrollThroughOutWindow(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		jse.executeScript("window.scrollTo(0,document.scrollingElement.scrollHeight/2)");
		ThreadSleep(1000);
		jse.executeScript("window.scrollTo(0,document.scrollingElement.scrollHeight)");
		ThreadSleep(1000);
	}

	/**
	 * @author Ankit Jaiswal
	 * @param number
	 * @return String
	 */
	public static String changeNumberIntoUSFormat(String number) {
		String s = String.valueOf(number).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
		System.out.println("Change US Number Formate >>>>> " + number);
		return s;
	}

	/**
	 * @author Ravi Kumar
	 * @param number
	 * @return String
	 */
	public static String changeNumberIntoUSDollarFormat(String number) {
		int amount = Integer.valueOf(number);
		String s = NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(amount);
		System.out.println("Change US Number Formate >>>>> " + number + "into :" + s);
		return s;
	}

	public static List<String> compareMultipleListContainsByTitle(WebDriver driver, String filesName,
			List<WebElement> listOfFileName) {
		List<String> result = new ArrayList<String>();
		String[] fileName = filesName.split(",");
		List<WebElement> listofFileName = listOfFileName;
		int countFiles = 0;
		try {
			if (fileName.length != 0) {
				if (!listofFileName.isEmpty()) {
					for (int i = 0; i < fileName.length; i++) {
						for (int j = 0; j < listofFileName.size(); j++) {
							scrollDownThroughWebelement(driver, listofFileName.get(j), "");
							ThreadSleep(500);
							AppListeners.appLog.info("Comparing:>>" + fileName[i] + ">>With:>>"
									+ listofFileName.get(j).getAttribute("title").trim());
							if (fileName[i].contains(listofFileName.get(j).getAttribute("title").trim())) {
								AppListeners.appLog.info(fileName[i] + " is matched successfully");
								countFiles++;
								break;
							} else if (j == listofFileName.size() - 1) {
								AppListeners.appLog.info(fileName[i] + " is not matched.");
								result.add(fileName[i] + " is not matched.");
							}
						}
					}
				} else {
					AppListeners.appLog.error("list of webelement is empty so cannot compare name: " + filesName);
					result.add("list of webelement is empty so cannot compare name: " + filesName);
				}
				if (fileName.length == countFiles) {
					AppListeners.appLog.info("All the files are matched.");

				} else {
					AppListeners.appLog.info("Files are not matched.");
					result.add("Files are not matched.");
				}
			} else {
				AppListeners.appLog.info("No Data In Excel Cell.");
				result.add("No Data In Excel Cell.");
			}
		} catch (Exception e) {
			AppListeners.appLog.info("There are no file to compare.");
			result.add("There are no file to compare.");
		}
		return result;
	}

	public static boolean clickUsingJavaScript(WebDriver driver, WebElement element, String elementName) {
		String text = null;
		ThreadSleep(5000);
		try {
			// text=(String) ((JavascriptExecutor) driver).executeScript("return
			// $('"+Jquery+"')[0].value");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			appLog.info("Able to Clicked using JavaScript");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			appLog.error("Exception in Clicked using JavaScript");
			System.err.println("Cannot Click Element: " + elementName);
		}
		appLog.info("Not Able to Click using JavaScript");
		return false;
	}

	public static boolean uploadFileAutoIT(String filepath) {
		String exePath = System.getProperty("user.dir") + "\\AutoIT\\NewUploadFile.exe";
		ProcessBuilder process = new ProcessBuilder(exePath, filepath);
		try {
			process.start();
			ThreadSleep(500);
			log(LogStatus.PASS, "File is uploaded successfully " + filepath, YesNo.No);
			return true;
		} catch (Exception e) {
			log(LogStatus.FAIL, "File is not uploaded " + filepath, YesNo.Yes);
		}
		return false;
	}

	public static boolean clickUsingActionClass(WebDriver driver, WebElement ele) {

		Actions ac = new Actions(driver);
		try {

			ac.clickAndHold(ele).build().perform();
			ThreadSleep(500);
			ac.release(ele).click().build().perform();
			appLog.info("successfully clicked ele using actions class");
		} catch (Exception e) {
			log(LogStatus.ERROR, e.toString(), YesNo.Yes);
			return false;
		}
		return true;
	}

	public static boolean doubleClickUsingAction(WebDriver driver, WebElement ele) {

		Actions ac = new Actions(driver);
		try {

			ac.moveToElement(ele).build().perform();
			ThreadSleep(500);
			ac.doubleClick().build().perform();
			appLog.info("successfully  double clicked on ele using actions class");
		} catch (Exception e) {
			log(LogStatus.ERROR, e.toString(), YesNo.Yes);
			return false;
		}
		return true;
	}

	public static String capitalize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();

	}

	public static String findThreeLetterMonthName(String date) {
		String mon = date.split("/")[0];
		int m = Integer.parseInt(mon);
		String a = Month.of(m).name();
		System.err.println("month >>>>" + a);
		a = a.substring(0, 3);
		a = capitalize(a);
		String ad = date.split("/")[1];
		ad.substring(0, 1);
		System.err.println(">>>>>> ad : " + ad.substring(0, 1));
		if (ad.substring(0, 1).equalsIgnoreCase("0")) {
			return a + " " + ad.substring(1, 2);
		} else {
			return a + " " + date.split("/")[1];
		}
		// return a+" "+date.split("/")[1];
	}

	public static String findMonthNameAndYear(String date) {
		String mon = date.split("/")[0];
		int m = Integer.parseInt(mon);
		String a = Month.of(m).name();
		System.err.println("month >>>>" + a);
		a = capitalize(a);
		a = a + " " + date.split("/")[2];
		return a;
	}

	public static String findMonthDateCommaYear(String date) {
		String mon = date.split("/")[0];
		int m = Integer.parseInt(mon);
		String a = Month.of(m).name();
		System.err.println("month >>>>" + a);
		a = capitalize(a);
		a = a + " " + date.split("/")[1] + ", " + date.split("/")[2];
		return a;
	}

	public static String dateAndTimeAccordingToTimeZone(int howManyMinuteBeforeOrAfter, String dateFormat,
			String zone) {

		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, howManyMinuteBeforeOrAfter);
		SimpleDateFormat date = new SimpleDateFormat(dateFormat);
		date.setTimeZone(TimeZone.getTimeZone(zone));
		return date.format(cal.getTime());
	}

	public static String findCurrentWeek(String dateFormat, String zone) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SimpleDateFormat date = new SimpleDateFormat(dateFormat);
		date.setTimeZone(TimeZone.getTimeZone(zone));
		String date1 = date.format(cal.getTime());
		System.out.println();

		cal.add(Calendar.DATE, 6);
		date = new SimpleDateFormat(dateFormat);
		date.setTimeZone(TimeZone.getTimeZone(zone));
		String date2 = date.format(cal.getTime());
		System.out.println(date2);
		String monthName1 = findThreeLetterMonthName(date1).split(" ")[0];
		String monthName2 = findThreeLetterMonthName(date2).split(" ")[0];
		if (monthName1.equalsIgnoreCase(monthName2))
			return findThreeLetterMonthName(date1) + " – " + date2.split("/")[1] + ", " + date1.split("/")[2];
		else
			return findThreeLetterMonthName(date1) + " – " + findThreeLetterMonthName(date2) + ", "
					+ date1.split("/")[2];
	}

	public static String previousOrForwardDateAccordingToTimeZone(int howManyDaysBeforeOrAfter, String dateFormat,
			String zone) {

		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, howManyDaysBeforeOrAfter);
		SimpleDateFormat date = new SimpleDateFormat(dateFormat);
		date.setTimeZone(TimeZone.getTimeZone(zone));
		return date.format(cal.getTime());
	}

	public static String previousOrForwardMonthAccordingToTimeZone(int howManyDaysBeforeOrAfter, String dateFormat,
			String zone) {

		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, howManyDaysBeforeOrAfter);
		SimpleDateFormat date = new SimpleDateFormat(dateFormat);
		date.setTimeZone(TimeZone.getTimeZone(zone));
		return date.format(cal.getTime());
	}

	public static ActivityType isPastDate(String date, String zone) {
		boolean flag = false;
		ActivityType activityType = null;
		SimpleDateFormat sdformat = new SimpleDateFormat("MM-dd-yyyy");
		try {
			Date d1 = sdformat.parse((date).replace("/", "-"));
			// appLog.info("Passed Date : "+sdformat.format(d1));
			System.out.println("Passed Date :              " + sdformat.format(d1));

			Date d2 = sdformat
					.parse((previousOrForwardDateAccordingToTimeZone(0, "MM/dd/YYYY", zone)).replace("/", "-"));
			// appLog.info(zone+" Date : "+sdformat.format(d2));
			System.out.println(zone + " Date : " + sdformat.format(d2));

			if (d1.compareTo(d2) == 0 || d1.compareTo(d2) > 0) {
				// appLog.info("Passed Date is equal/greater than "+zone+" date");
				activityType = ActivityType.Next;
				System.out.println("Passed Date is equal/greater than " + zone + " date");
			} else if (d1.compareTo(d2) < 0) {
				// appLog.info("Passed Date is smaller than "+zone+" date");
				System.out.println("Passed Date is smaller than " + zone + " date");
				activityType = ActivityType.Past;
				flag = true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activityType;
	}

	public static boolean mouseHoverJScript(WebDriver driver, WebElement HoverElement) {
		try {
			if (isElementPresent(HoverElement)) {
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				((JavascriptExecutor) driver).executeScript(mouseOverScript, HoverElement);
				return true;

			} else {
				System.out.println("Element was not visible to hover " + "\n");
			}
		} catch (StaleElementReferenceException e) {
			System.out.println(
					"Element with " + HoverElement + "is not attached to the page document" + e.getStackTrace());
		} catch (NoSuchElementException e) {
			System.out.println("Element " + HoverElement + " was not found in DOM" + e.getStackTrace());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error occurred while hovering" + e.getStackTrace());
		}
		return false;
	}

	public static boolean isElementPresent(WebElement element) {
		boolean flag = false;
		try {
			if (element.isDisplayed() || element.isEnabled())
				flag = true;
		} catch (NoSuchElementException e) {
			flag = false;
		} catch (StaleElementReferenceException e) {
			flag = false;
		}
		return flag;
	}

	public static String reverseString(String reverseString) {
		String[] myarraStrings = reverseString.split("<break>");
		String newString = "";
		for (int i = myarraStrings.length - 1; i >= 0; i--) {
			System.out.print(myarraStrings[i] + "  ");
			if (i == myarraStrings.length - 1) {
				newString = newString + myarraStrings[i];
			} else {
				newString = newString + "<break>" + myarraStrings[i];
			}
		}
		return newString;

	}

	public static boolean uploadFile(String filepath) {
		String exePath = System.getProperty("user.dir") + "\\AutoIT\\NewUploadFile.exe";
		ProcessBuilder process = new ProcessBuilder(exePath, filepath);
		try {
			process.start();
			ThreadSleep(500);
			log(LogStatus.PASS, "File is uploaded successfully " + filepath, YesNo.No);
			return true;
		} catch (Exception e) {
			log(LogStatus.FAIL, "File is not uploaded " + filepath, YesNo.Yes);
		}
		return false;
	}

	/**
	 * @author ANKIT JAISWAL
	 * @param imagePath
	 * @param textMessage
	 * @return true/false
	 */
	public static boolean mouseHoveAndClickAction(String imagePath, String textMessage) {
		Pattern fileInputTextBox = new Pattern(System.getProperty("user.dir") + imagePath);
		Screen scn = new Screen();
		try {
			scn.hover(fileInputTextBox);
			log(LogStatus.INFO, "Mouse hover on " + textMessage, YesNo.No);
			ThreadSleep(2000);
			try {
				scn.click(fileInputTextBox);
				log(LogStatus.INFO, "Clicked on " + textMessage, YesNo.No);
				return true;
			} catch (FindFailed e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log(LogStatus.INFO, "Not able to clicked on " + textMessage, YesNo.Yes);
			}
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log(LogStatus.INFO, "Not able to mouse hover on " + textMessage, YesNo.Yes);
		}
		return false;
	}

	/**
	 * @author ANKIT JAISWAL
	 * @param ls1
	 * @param ls2
	 * @return
	 */
	public static boolean compareList(List ls1, List ls2) {
		return ls1.containsAll(ls2) && ls1.size() == ls2.size() ? true : false;
	}

	public static boolean getSelectedOptionOfDropDown(WebDriver driver, WebElement dropDownElement,
			List<WebElement> listElements, String elementName, String textToSelect) {
		boolean flag = false;
		if (checkElementVisibility(driver, dropDownElement, elementName, 60)) {
			CommonLib.ThreadSleep(4000);
			if (click(driver, dropDownElement, elementName, action.BOOLEAN)) {
				log(LogStatus.INFO, "successfully click on " + elementName, YesNo.No);

				CommonLib.ThreadSleep(4000);

				for (WebElement val : listElements) {

					if (val.getText().trim().equalsIgnoreCase(textToSelect)) {
						clickUsingJavaScript(driver, val, val.getText(), action.BOOLEAN);
						flag = true;
						break;
					}

				}

			} else {
				log(LogStatus.ERROR, elementName + " button is not clickable", YesNo.No);

			}

		} else {
			log(LogStatus.ERROR, elementName + " is not visible", YesNo.No);
		}
		return flag;
	}

	public static boolean getSelectedOptionOfDropDown(WebDriver driver, List<WebElement> listElements,
			String elementName, String textToSelect) {
		boolean flag = false;
		for (WebElement val : listElements) {

			if (val.getText().trim().equalsIgnoreCase(textToSelect)) {
				clickUsingJavaScript(driver, val, val.getText(), action.BOOLEAN);
				flag = true;
				break;
			}

		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param driver
	 * @param xpath
	 * @param elementName
	 * @return list<webelement>
	 * @description returns the list of webelement based on the xpath passed
	 */
	public static List<WebElement> FindElements(WebDriver driver, List<WebElement> elements, String elementName) {

		try {
			if (elements != null) {
				Wait<WebDriver> wait = new WebDriverWait(driver, 25);
				wait.until(ExpectedConditions.visibilityOf(elements.get(1)));
				return elements;
			}
			return elements;
		} catch (Exception e) {
			log(LogStatus.ERROR, elementName + " is not visible", YesNo.No);
			return elements;
		}
	}

	public static boolean getSelectedOptionOfDropDown(WebDriver driver, WebElement dropDownElement,
			String elementToSelect, String elementName, String nothing) {
		boolean flag = false;
		if (dropDownElement != null) {
			if (click(driver, dropDownElement, "Show Icon", action.SCROLLANDBOOLEAN)) {
				appLog.info("Click on Range Icon");
				WebElement rangeValueEle = FindElement(driver, "//div[text()='" + elementToSelect + "']",
						"Range value : " + elementToSelect, action.SCROLLANDBOOLEAN, 10);
				if (click(driver, rangeValueEle, "Show value : " + elementToSelect, action.SCROLLANDBOOLEAN)) {
					appLog.info("Selected Range Value : " + elementToSelect);
					flag = true;
				} else {
					appLog.error("Not Able to Select on Value of Range drop down : " + elementToSelect);

				}

			} else {
				appLog.error("Not Able to Click on Show Value Icon");
			}
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param driver
	 * @param xpath
	 * @param elementName
	 * @return list<webelement>
	 * @description returns the list of webelement based on the xpath passed
	 */
	public static boolean elementToBeSelectFromDropDown(WebDriver driver, WebElement dropDownElement,
			String elementToSelect, String elementName) {
		boolean flag = false;
		if (dropDownElement != null) {
			if (click(driver, dropDownElement, "Show Icon", action.SCROLLANDBOOLEAN)) {
				appLog.info("Click on Range Icon");
				WebElement rangeValueEle = FindElement(driver, "//*[text()='" + elementToSelect + "']",
						"Range value : " + elementToSelect, action.SCROLLANDBOOLEAN, 10);
				if (click(driver, rangeValueEle, "Show value : " + elementToSelect, action.SCROLLANDBOOLEAN)) {
					appLog.info("Selected Range Value : " + elementToSelect);
					flag = true;
				} else {
					appLog.error("Not Able to Select on Value of Range drop down : " + elementToSelect);

				}

			} else {
				appLog.error("Not Able to Click on Show Value Icon");
			}
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param driver
	 * @param xpath
	 * @param elementName
	 * @return list<webelement>
	 * @description returns the list of webelement based on the xpath passed
	 */
	public static boolean dropDownHandle(WebDriver driver, WebElement dropDownElement, String xpath, String elementName,
			String textToSelect) {
		boolean flag = false;
		if (checkElementVisibility(driver, dropDownElement, elementName, 60)) {
			CommonLib.ThreadSleep(4000);
			if (clickUsingJavaScript(driver, dropDownElement, elementName, action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "successfully click on " + elementName, YesNo.No);

				CommonLib.ThreadSleep(4000);
				List<WebElement> listElements = FindElements(driver, xpath, "DropDown Values ");
				for (WebElement val : listElements) {

					if (val.getText().trim().equalsIgnoreCase(textToSelect)) {

						if (clickUsingJavaScript(driver, val, val.getText(), action.SCROLLANDBOOLEAN)) {
							flag = true;
							break;
						}
					}

				}

			} else {
				log(LogStatus.ERROR, elementName + " button is not clickable", YesNo.No);
				BaseLib.sa.assertTrue(false, elementName + " button is not clickable");

			}

		} else {
			log(LogStatus.ERROR, elementName + " is not visible", YesNo.No);
			BaseLib.sa.assertTrue(false, elementName + " is not visible");
		}
		return flag;
	}

	
	/**
	 * @author Ankur Huria
	 * @param driver
	 * @param xpath
	 * @param elementName
	 * @return list<webelement>
	 * @description returns the list of webelement based on the xpath passed
	 */
	public static boolean datePickerHandle(WebDriver driver, WebElement MonthSelector, WebElement MonthPreviousButton,
			String elementName, String Year, String Month, String Date) {
		boolean flag = false;
		try {

			int j = 0;
			for (int i = 0; i < 13; i++) {
				if (j < 12) {
					if (MonthSelector.getText().toLowerCase().contains(Month.toLowerCase())) {
						appLog.info("Month Selected : " + Month);
						break;
					} else {
						click(driver, MonthPreviousButton, "", action.SCROLLANDBOOLEAN);
						j++;
					}
				} else {
					appLog.error("Month Format Is Not Correct");
					return false;

				}
			}
			String xpath = "//lightning-select//select";
			WebElement YearSelector = FindElement(driver, xpath, "YearSelector", action.SCROLLANDBOOLEAN, 25);
			if (CommonLib.selectVisibleTextFromDropDown(driver, YearSelector, "YearSelector: " + Year, Year))

			{
				appLog.info("Select The Year: " + Year);
				CommonLib.ThreadSleep(8000);
				List<WebElement> DaySelector = FindElements(driver,
						"//table[@class='slds-datepicker__month']//tr//td[not(contains(@class,'slds-day_adjacent-month'))]/span",
						"dateSelector");
				for (WebElement day : DaySelector) {
					try {
						
						
						
						if (Integer.parseInt(Date) < 10 && Date.length()==2) {
							Date = Date.replace("0", "");
						}

						
						
					if (day.getText().trim().equalsIgnoreCase(Date)) {
						if (click(driver, day, "", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, Date + " Date is Selected", YesNo.No);
							flag = true;
						} else {
							log(LogStatus.INFO, Date + " Date is Not Selected", YesNo.No);
							flag = false;
						}
					}
				}
					catch(StaleElementReferenceException e)
					{
						e.getMessage();
					}
				}

			} else {
				appLog.error("Not ABle to Select Year: " + Year);
			}

		} catch (Exception e) {
			flag = false;

			log(LogStatus.ERROR, elementName + " Date is Not Selected", YesNo.No);
			log(LogStatus.ERROR, e.getMessage(), YesNo.No);
		}

		return flag;

	}
	
	
	/**
	 * @author Ankur Huria
	 * @param driver
	 * @param xpath
	 * @param elementName
	 * @return list<webelement>
	 * @description returns the list of webelement based on the xpath passed
	 */
	public static boolean datePickerHandle(WebDriver driver,
			String elementName, String Year, String Month, String Date) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		boolean flag = false;
		if(bp.monthInDatePicker(5)!=null)
		{
		try {

			int j = 0;
			for (int i = 0; i < 13; i++) {
				if (j < 12) {
					
					if (bp.monthInDatePicker(5).getText().toLowerCase().contains(Month.toLowerCase())) {
						appLog.info("Month Selected : " + Month);
						break;
					} else {
						click(driver, bp.previousMonthButtonInDatePicker(8), "", action.SCROLLANDBOOLEAN);
						j++;
					}
					
				} else {
					appLog.error("Month Format Is Not Correct");
					return false;

				}
			}
			String xpath = "//lightning-select//select";
			WebElement YearSelector = FindElement(driver, xpath, "YearSelector", action.SCROLLANDBOOLEAN, 25);
			if (CommonLib.selectVisibleTextFromDropDown(driver, YearSelector, "YearSelector: " + Year, Year))

			{
				appLog.info("Select The Year: " + Year);
				CommonLib.ThreadSleep(8000);
				List<WebElement> DaySelector = FindElements(driver,
						"//table[@class='slds-datepicker__month']//tr//td[not(contains(@class,'slds-day_adjacent-month'))]/span",
						"dateSelector");
				for (WebElement day : DaySelector) {
					try {
						
						
						
						if (Integer.parseInt(Date) < 10 && Date.length()==2) {
							Date = Date.replace("0", "");
						}

						
						
					if (day.getText().trim().equalsIgnoreCase(Date)) {
						if (click(driver, day, "", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, Date + " Date is Selected", YesNo.No);
							flag = true;
						} else {
							log(LogStatus.INFO, Date + " Date is Not Selected", YesNo.No);
							flag = false;
						}
					}
				}
					catch(StaleElementReferenceException e)
					{
						e.getMessage();
					}
				}

			} else {
				appLog.error("Not ABle to Select Year: " + Year);
			}

		} catch (Exception e) {
			flag = false;

			log(LogStatus.ERROR, elementName + " Date is Not Selected", YesNo.No);
			log(LogStatus.ERROR, e.getMessage(), YesNo.No);
		}
		
		
		}
		
		else
		{
		
		
		try {

			int j = 0;
			for (int i = 0; i < 13; i++) {
				if (j < 12) {
					if(bp.alternateMonthInDatePicker(5) != null) {
					if (bp.alternateMonthInDatePicker(5).getText().toLowerCase().contains(Month.toLowerCase())) {
						appLog.info("Month Selected : " + Month);
						break;
					} else {
						click(driver,bp.alteratePreviousMonthButtonInDatePicker(7), "", action.SCROLLANDBOOLEAN);
						j++;
					}
					}
				} else {
					appLog.error("Month Format Is Not Correct");
					return false;

				}
			}
			String xpath = "//div[@class='slds-shrink-none']/label/select";
			WebElement YearSelector = FindElement(driver, xpath, "YearSelector", action.SCROLLANDBOOLEAN, 25);
			if (CommonLib.selectVisibleTextFromDropDown(driver, YearSelector, "YearSelector: " + Year, Year))

			{
				appLog.info("Select The Year: " + Year);
				CommonLib.ThreadSleep(8000);
				List<WebElement> DaySelector = FindElements(driver,
						"//div[contains(@class,'uiDatePickerGrid--default')]//td[@class='uiDayInMonthCell' or contains(@class,'slds-is-today')]/span",
						"AlternateDateSelector");
				for (WebElement day : DaySelector) {
					try {
						
						
						
						if (Integer.parseInt(Date) < 10 && Date.length()==2) {
							Date = Date.replace("0", "");
						}

						
						
					if (day.getText().trim().equalsIgnoreCase(Date)) {
						if (click(driver, day, "", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, Date + " Date is Selected", YesNo.No);
							flag = true;
						} else {
							log(LogStatus.INFO, Date + " Date is Not Selected", YesNo.No);
							flag = false;
						}
					}
				}
					catch(StaleElementReferenceException e)
					{
						e.getMessage();
					}
				}

			} else {
				appLog.error("Not ABle to Select Year: " + Year);
			}

		} catch (Exception e) {
			flag = false;

			log(LogStatus.ERROR, elementName + " Date is Not Selected", YesNo.No);
			log(LogStatus.ERROR, e.getMessage(), YesNo.No);
		}
		
		
		}
		

		return flag;

	}
	
	
	/**
	 * 
	 * @author Ankur Huria
	 * @description- This method is used to get Future date according to the time zone.
	 */
	public static String getFutureDateAccToTimeZone(String timeZone, String format, int daysToAdd) {
		try {
			DateFormat formatter = new SimpleDateFormat(format);
			formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
			
			
			  Calendar cal = Calendar.getInstance();  
		        try{  
		           cal.setTime(formatter.parse((formatter.format(new Date()))));  
		        }catch(ParseException e){  
		            e.printStackTrace();  
		         }  
		             
		        // use add() method to add the days to the given date  
		        cal.add(Calendar.DAY_OF_MONTH, daysToAdd);  
		         return formatter.format(cal.getTime());  
			
			

		} catch (Exception e) {
			return null;
		}
	}
	
	
	

}
